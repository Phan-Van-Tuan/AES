package main;

import java.util.Arrays;

public class baseFuntion {
	private static byte[][] keyMatrixs;
	private static final int Nb = 4; // Số cột trong mảng trạng thái
    private static final int Nk = 4; // Số từ 32-bit trong khóa
    private static final int Nr = 10; // Số vòng lặp

	public static void main(String[] args) {
        try {
//            String key = "0123456789abcdef";
//            String data = "Hello, AES!";
//            
//            keyMatrixs = new byte[4][4];
//            for (int i = 0; i < 16; i++) {
//            	keyMatrixs[i/4][i%4] = keyBytes[i];
//            }
//            System.out.println("-- key --");
//            for (int i = 0; i < keyMatrixs.length; i++) {
//                for (int j = 0; j < keyMatrixs[i].length; j++) {
//                    System.out.print(keyMatrixs[i][j] + " ");
//                }
//                System.out.println(); // Xuống dòng sau khi in một hàng
//            }

            // Mã hóa
//            long startTime = System.currentTimeMillis();
//            byte[] encryptedData = encrypt(data, key);
//            long encryptTime = System.currentTimeMillis() - startTime;
//
//            // Giải mã
//            startTime = System.currentTimeMillis();
//            String decryptedData = decrypt(encryptedData, key);
//            long decryptTime = System.currentTimeMillis() - startTime;
//
//            System.out.println("\nDữ liệu đã mã hóa: " + Arrays.toString(encryptedData));
//            System.out.println("Dữ liệu đã giải mã: " + decryptedData);
//            System.out.println("Thời gian mã hóa: " + encryptTime + " ms");
//            System.out.println("Thời gian giải mã: " + decryptTime + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	// Hằng số Rijndael S-box
	private static final byte[][] SBOX = { 
		{ 
			(byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, 
			(byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5, 
			(byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, 
			(byte) 0xfe, (byte) 0xd7, (byte) 0xab, (byte) 0x76 
		},
			// ... các dòng khác ...
			// Điền đầy đủ các giá trị còn lại của S-box theo chuẩn AES
	};

	// Hằng số Rijndael Rcon
	private static final byte[] RCON = { 
			(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, 
			(byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80, 
			(byte) 0x1b, (byte) 0x36
			// ... các giá trị tiếp theo nếu cần ...
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
    	for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int row = (state[i][j] & 0xf0) >> 4;
                int col = state[i][j] & 0x0f;
                state[i][j] = SBOX[row][col];
            }
        }
    }

    private static void shiftRows(byte[][] state) {
    	byte temp;
        // Dịch chuyển hàng thứ hai sang trái 1 vị trí
        temp = state[1][0];
        state[1][0] = state[1][1];
        state[1][1] = state[1][2];
        state[1][2] = state[1][3];
        state[1][3] = temp;

        // Dịch chuyển hàng thứ ba sang trái 2 vị trí
        temp = state[2][0];
        state[2][0] = state[2][2];
        state[2][2] = temp;
        temp = state[2][1];
        state[2][1] = state[2][3];
        state[2][3] = temp;

        // Dịch chuyển hàng thứ tư sang trái 3 vị trí
        temp = state[3][0];
        state[3][0] = state[3][3];
        state[3][3] = state[3][2];
        state[3][2] = state[3][1];
        state[3][1] = temp;
    }

    private static void mixColumns(byte[][] state) {
        // Trộn cột trong state
    }

    private static void addRoundKey(byte[][] state, byte[][] roundKey) {
        // Thực hiện phép XOR giữa state và roundKey
    }

    private static byte[][] generateRoundKey(int round, byte[][] roundKey) {
		return roundKey;
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
