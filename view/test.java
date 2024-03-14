package main.view;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import main.fun.*;
public class test {
	private static final int BLOCK_SIZE = 16;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFilePath = "input.txt123456789sấcceqefqw";
//        String outputFilePath = "output_encrypted.txt";
        String password = "123456789asdfghj";
        byte[] keyBytes = password.getBytes();
        byte[] inputFile = inputFilePath.getBytes();
        System.out.println(inputFile);
        byte[] input = HandleFile.addPadding(inputFile);
        byte[] block = new byte[BLOCK_SIZE];
        byte[] encryptedBytes = new byte[input.length];
        byte[] decryptedBytes = new byte[input.length];
        int numBlocks = input.length / BLOCK_SIZE;
        for (int i = 0; i < numBlocks; i++) {
            System.arraycopy(input, i * BLOCK_SIZE, block, 0, BLOCK_SIZE);
            byte[] temp = AES.encrypt(block, keyBytes);
            System.arraycopy(temp, 0, encryptedBytes, i * BLOCK_SIZE, BLOCK_SIZE);
        }
//		byte[] encryptedBytes = AES.encrypt(input, keyBytes);
		System.out.println(Arrays.toString(encryptedBytes));
		for (int i = 0; i < numBlocks; i++) {
            System.arraycopy(encryptedBytes, i * BLOCK_SIZE, block, 0, BLOCK_SIZE);
            byte[] temp = AES.decrypt(block, keyBytes);
            System.arraycopy(temp, 0, decryptedBytes, i * BLOCK_SIZE, BLOCK_SIZE);
        }
//		byte[] decryptedBytes = AES.decrypt(encryptedBytes, keyBytes);
		String byteString = new String(HandleFile.removePadding(decryptedBytes), StandardCharsets.UTF_8);
		System.out.println(byteString);
	}
	

}
