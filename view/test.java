package main.view;

import main.fun.*;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFilePath = "input.txt";
//        String outputFilePath = "output_encrypted.txt";
        String password = "mypassword";
        byte[] keyBytes = password.getBytes();
		byte[] encryptedBytes = AES.encrypt(inputFilePath.getBytes(), keyBytes);
		System.out.print(encryptedBytes);
	}
	

}
