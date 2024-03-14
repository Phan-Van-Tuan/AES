package main.fun;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

	
	public static void awaitEncrypt(String inputFilePath, String outputFilePath, String key) {
		try {
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
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
		
	}
	
	public static void awaitDecrypt(String inputFilePath, String outputFilePath, String key) {
	    try {
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
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
