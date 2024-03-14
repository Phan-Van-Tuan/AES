package main.fun;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class HandleFile {
	private static final int BLOCK_SIZE = 16;
	
	public static byte[] readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readAllBytes(path);	
	}

	public static void writeFile(String filePath, byte[] data) throws IOException {
		Path path = Paths.get(filePath);
		Files.write(path, data);
	}
	
	public static byte[] addPadding(byte[] input) {
	    int paddingLength = BLOCK_SIZE - (input.length % BLOCK_SIZE);
	    byte paddingByte = (byte) paddingLength;
	    byte[] paddedInput = Arrays.copyOf(input, input.length + paddingLength);
	    Arrays.fill(paddedInput, input.length, paddedInput.length, paddingByte);
	    return paddedInput;
	}

	public static byte[] removePadding(byte[] input) {
	    int paddingLength = input[input.length - 1];
	    return Arrays.copyOf(input, input.length - paddingLength);
	}

	
	public static String awaitEncrypt(String inputFilePath, String outputFilePath, String key) {
		if (!validateString(inputFilePath) || !validateString(outputFilePath) || !validateString(key)) {
			return "";
		}
		try {
			long startTime = System.currentTimeMillis();
            byte[] inputBytes = addPadding(readFile(inputFilePath));
            byte[] encryptedBytes = new byte[inputBytes.length];
            byte[] keyBytes = key.getBytes();
            byte[] block = new byte[BLOCK_SIZE];

            int numBlocks = inputBytes.length / BLOCK_SIZE;
            for (int i = 0; i < numBlocks; i++) {
                System.arraycopy(inputBytes, i * BLOCK_SIZE, block, 0, BLOCK_SIZE);
                byte[] temp = AES.encrypt(block, keyBytes);
                System.arraycopy(temp, 0, encryptedBytes, i * BLOCK_SIZE, BLOCK_SIZE);
            }
            HandleFile.writeFile(outputFilePath, encryptedBytes);
            long endTime = System.currentTimeMillis();
		    long executionTime = endTime - startTime;
		    String executionTimeString = String.format("%d milliseconds", executionTime);
		    return executionTimeString;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString() , "ERROR", JOptionPane.ERROR_MESSAGE);
        }
		return "";
		
	}
	
	public static String awaitDecrypt(String inputFilePath, String outputFilePath, String key) {
		if (!validateString(inputFilePath) || !validateString(outputFilePath) || !validateString(key)) {
			return "";
		}
	    try {
	    	long startTime = System.currentTimeMillis();
	        byte[] inputBytes = readFile(inputFilePath);
	        byte[] decryptedBytes = new byte[inputBytes.length];
	        byte[] keyBytes = key.getBytes();
	        byte[] block = new byte[BLOCK_SIZE];

	        int numBlocks = inputBytes.length / BLOCK_SIZE;
	        for (int i = 0; i < numBlocks; i++) {
	            System.arraycopy(inputBytes, i * BLOCK_SIZE, block, 0, BLOCK_SIZE);
	            byte[] temp = AES.decrypt(block, keyBytes);
	            System.arraycopy(temp, 0, decryptedBytes, i * BLOCK_SIZE, BLOCK_SIZE);
	        }
	        HandleFile.writeFile(outputFilePath, removePadding(decryptedBytes));
	        long endTime = System.currentTimeMillis();
		    long executionTime = endTime - startTime;
		    String executionTimeString = String.format("%d milliseconds", executionTime);
		    return executionTimeString;
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, e.toString() , "ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	    return "";
	}
	
	private static boolean validateString(String text) {
		if(text.isEmpty()) {
			return false;
		}
		return true;
	}

}
