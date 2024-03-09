package main;

import java.util.Arrays;

public class baseFuntion {
	private static byte[][] keyMatrixs;
	private static final int Nb = 4; // Số cột trong mảng trạng thái
    private static final int Nk = 4; // Số từ 32-bit trong khóa
    private static final int Nr = 10; // Số vòng lặp

	public static void main(String[] args) {
        try {
            String key = "0123456789abcdef";
            String data = "Hello, AES!";
            
            keyMatrixs = new byte[4][4];
            for (int i = 0; i < 16; i++) {
            	keyMatrixs[i/4][i%4] = keyBytes[i];
            }
            System.out.println("-- key --");
            for (int i = 0; i < keyMatrixs.length; i++) {
                for (int j = 0; j < keyMatrixs[i].length; j++) {
                    System.out.print(keyMatrixs[i][j] + " ");
                }
                System.out.println(); // Xuống dòng sau khi in một hàng
            }

            // Mã hóa
            long startTime = System.currentTimeMillis();
            byte[] encryptedData = encrypt(data, key);
            long encryptTime = System.currentTimeMillis() - startTime;

            // Giải mã
            startTime = System.currentTimeMillis();
            String decryptedData = decrypt(encryptedData, key);
            long decryptTime = System.currentTimeMillis() - startTime;

            System.out.println("\nDữ liệu đã mã hóa: " + Arrays.toString(encryptedData));
            System.out.println("Dữ liệu đã giải mã: " + decryptedData);
            System.out.println("Thời gian mã hóa: " + encryptTime + " ms");
            System.out.println("Thời gian giải mã: " + decryptTime + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Hằng số Rijndael S-box
    private static final byte[][] SBOX = {
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            // ... các dòng khác ...
    };

    // Hằng số Rijndael Rcon
    private static final byte[] RCON = {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36
    };

    public static void encrypt(byte[][] state, byte[][] roundKey) {
        addRoundKey(state, roundKey);

        for (int round = 1; round < Nr; ++round) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, generateRoundKey(round, roundKey));
        }

        subBytes(state);
        shiftRows(state);
        addRoundKey(state, generateRoundKey(Nr, roundKey));
    }

    public static void decrypt(byte[][] state, byte[][] roundKey) {
        addRoundKey(state, generateRoundKey(Nr, roundKey));

        for (int round = Nr - 1; round > 0; --round) {
            invShiftRows(state);
            invSubBytes(state);
            addRoundKey(state, generateRoundKey(round, roundKey));
            invMixColumns(state);
        }

        invShiftRows(state);
        invSubBytes(state);
        addRoundKey(state, roundKey);
    }

    private static void subBytes(byte[][] state) {
        // Thực hiện thay thế byte theo S-box
    }

    private static void shiftRows(byte[][] state) {
        // Dịch vòng trái các dòng trong state
    }

    private static void mixColumns(byte[][] state) {
        // Trộn cột trong state
    }

    private static void addRoundKey(byte[][] state, byte[][] roundKey) {
        // Thực hiện phép XOR giữa state và roundKey
    }

    private static byte[][] generateRoundKey(int round, byte[][] roundKey) {
        // Sinh khóa vòng mới từ khóa vòng trước đó
    }

    private static void invShiftRows(byte[][] state) {
        // Dịch vòng phải các dòng trong state
    }

    private static void invSubBytes(byte[][] state) {
        // Thực hiện thay thế byte theo S-box ngược
    }

    private static void invMixColumns(byte[][] state) {
        // Trộn cột ngược trong state
    }
}
