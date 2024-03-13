package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class AES {
	private static final byte[][] Sbox = {
	        {(byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5, (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab, (byte) 0x76},
	        {(byte) 0xca, (byte) 0x82, (byte) 0xc9, (byte) 0x7d, (byte) 0xfa, (byte) 0x59, (byte) 0x47, (byte) 0xf0, (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4, (byte) 0x72, (byte) 0xc0},
	        {(byte) 0xb7, (byte) 0xfd, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3f, (byte) 0xf7, (byte) 0xcc, (byte) 0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1, (byte) 0x71, (byte) 0xd8, (byte) 0x31, (byte) 0x15},
	        {(byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3, (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9a, (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb, (byte) 0x27, (byte) 0xb2, (byte) 0x75},
	        {(byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a, (byte) 0x1b, (byte) 0x6e, (byte) 0x5a, (byte) 0xa0, (byte) 0x52, (byte) 0x3b, (byte) 0xd6, (byte) 0xb3, (byte) 0x29, (byte) 0xe3, (byte) 0x2f, (byte) 0x84},
	        {(byte) 0x53, (byte) 0xd1, (byte) 0x00, (byte) 0xed, (byte) 0x20, (byte) 0xfc, (byte) 0xb1, (byte) 0x5b, (byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39, (byte) 0x4a, (byte) 0x4c, (byte) 0x58, (byte) 0xcf},
	        {(byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb, (byte) 0x43, (byte) 0x4d, (byte) 0x33, (byte) 0x85, (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f, (byte) 0x50, (byte) 0x3c, (byte) 0x9f, (byte) 0xa8},
	        {(byte) 0x51, (byte) 0xa3, (byte) 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d, (byte) 0x38, (byte) 0xf5, (byte) 0xbc, (byte) 0xb6, (byte) 0xda, (byte) 0x21, (byte) 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2},
	        {(byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec, (byte) 0x5f, (byte) 0x97, (byte) 0x44, (byte) 0x17, (byte) 0xc4, (byte) 0xa7, (byte) 0x7e, (byte) 0x3d, (byte) 0x64, (byte) 0x5d, (byte) 0x19, (byte) 0x73},
	        {(byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc, (byte) 0x22, (byte) 0x2a, (byte) 0x90, (byte) 0x88, (byte) 0x46, (byte) 0xee, (byte) 0xb8, (byte) 0x14, (byte) 0xde, (byte) 0x5e, (byte) 0x0b, (byte) 0xdb},
	        {(byte) 0xe0, (byte) 0x32, (byte) 0x3a, (byte) 0x0a, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5c, (byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xe4, (byte) 0x79},
	        {(byte) 0xe7, (byte) 0xc8, (byte) 0x37, (byte) 0x6d, (byte) 0x8d, (byte) 0xd5, (byte) 0x4e, (byte) 0xa9, (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea, (byte) 0x65, (byte) 0x7a, (byte) 0xae, (byte) 0x08},
	        {(byte) 0xba, (byte) 0x78, (byte) 0x25, (byte) 0x2e, (byte) 0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6, (byte) 0xe8, (byte) 0xdd, (byte) 0x74, (byte) 0x1f, (byte) 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a},
	        {(byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xf6, (byte) 0x0e, (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1, (byte) 0x1d, (byte) 0x9e},
	        {(byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94, (byte) 0x9b, (byte) 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, (byte) 0x55, (byte) 0x28, (byte) 0xdf},
	        {(byte) 0x8c, (byte) 0xa1, (byte) 0x89, (byte) 0x0d, (byte) 0xbf, (byte) 0xe6, (byte) 0x42, (byte) 0x68, (byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f, (byte) 0xb0, (byte) 0x54, (byte) 0xbb, (byte) 0x16}
	};


	private static final byte[][] invSbox = {
	        {(byte) 0x52, (byte) 0x09, (byte) 0x6a, (byte) 0xd5, (byte) 0x30, (byte) 0x36, (byte) 0xa5, (byte) 0x38, (byte) 0xbf, (byte) 0x40, (byte) 0xa3, (byte) 0x9e, (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb},
	        {(byte) 0x7c, (byte) 0xe3, (byte) 0x39, (byte) 0x82, (byte) 0x9b, (byte) 0x2f, (byte) 0xff, (byte) 0x87, (byte) 0x34, (byte) 0x8e, (byte) 0x43, (byte) 0x44, (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb},
	        {(byte) 0x54, (byte) 0x7b, (byte) 0x94, (byte) 0x32, (byte) 0xa6, (byte) 0xc2, (byte) 0x23, (byte) 0x3d, (byte) 0xee, (byte) 0x4c, (byte) 0x95, (byte) 0x0b, (byte) 0x42, (byte) 0xfa, (byte) 0xc3, (byte) 0x4e},
	        {(byte) 0x08, (byte) 0x2e, (byte) 0xa1, (byte) 0x66, (byte) 0x28, (byte) 0xd9, (byte) 0x24, (byte) 0xb2, (byte) 0x76, (byte) 0x5b, (byte) 0xa2, (byte) 0x49, (byte) 0x6d, (byte) 0x8b, (byte) 0xd1, (byte) 0x25},
	        {(byte) 0x72, (byte) 0xf8, (byte) 0xf6, (byte) 0x64, (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16, (byte) 0xd4, (byte) 0xa4, (byte) 0x5c, (byte) 0xcc, (byte) 0x5d, (byte) 0x65, (byte) 0xb6, (byte) 0x92},
	        {(byte) 0x6c, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda, (byte) 0x5e, (byte) 0x15, (byte) 0x46, (byte) 0x57, (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84},
	        {(byte) 0x90, (byte) 0xd8, (byte) 0xab, (byte) 0x00, (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, (byte) 0x0a, (byte) 0xf7, (byte) 0xe4, (byte) 0x58, (byte) 0x05, (byte) 0xb8, (byte) 0xb3, (byte) 0x45, (byte) 0x06},
	        {(byte) 0xd0, (byte) 0x2c, (byte) 0x1e, (byte) 0x8f, (byte) 0xca, (byte) 0x3f, (byte) 0x0f, (byte) 0x02, (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, (byte) 0x03, (byte) 0x01, (byte) 0x13, (byte) 0x8a, (byte) 0x6b},
	        {(byte) 0x3a, (byte) 0x91, (byte) 0x11, (byte) 0x41, (byte) 0x4f, (byte) 0x67, (byte) 0xdc, (byte) 0xea, (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce, (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, (byte) 0x73},
	        {(byte) 0x96, (byte) 0xac, (byte) 0x74, (byte) 0x22, (byte) 0xe7, (byte) 0xad, (byte) 0x35, (byte) 0x85, (byte) 0xe2, (byte) 0xf9, (byte) 0x37, (byte) 0xe8, (byte) 0x1c, (byte) 0x75, (byte) 0xdf, (byte) 0x6e},
	        {(byte) 0x47, (byte) 0xf1, (byte) 0x1a, (byte) 0x71, (byte) 0x1d, (byte) 0x29, (byte) 0xc5, (byte) 0x89, (byte) 0x6f, (byte) 0xb7, (byte) 0x62, (byte) 0x0e, (byte) 0xaa, (byte) 0x18, (byte) 0xbe, (byte) 0x1b},
	        {(byte) 0xfc, (byte) 0x56, (byte) 0x3e, (byte) 0x4b, (byte) 0xc6, (byte) 0xd2, (byte) 0x79, (byte) 0x20, (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe, (byte) 0x78, (byte) 0xcd, (byte) 0x5a, (byte) 0xf4},
	        {(byte) 0x1f, (byte) 0xdd, (byte) 0xa8, (byte) 0x33, (byte) 0x88, (byte) 0x07, (byte) 0xc7, (byte) 0x31, (byte) 0xb1, (byte) 0x12, (byte) 0x10, (byte) 0x59, (byte) 0x27, (byte) 0x80, (byte) 0xec, (byte) 0x5f},
	        {(byte) 0x60, (byte) 0x51, (byte) 0x7f, (byte) 0xa9, (byte) 0x19, (byte) 0xb5, (byte) 0x4a, (byte) 0x0d, (byte) 0x2d, (byte) 0xe5, (byte) 0x7a, (byte) 0x9f, (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef},
	        {(byte) 0xa0, (byte) 0xe0, (byte) 0x3b, (byte) 0x4d, (byte) 0xae, (byte) 0x2a, (byte) 0xf5, (byte) 0xb0, (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, (byte) 0x3c, (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61},
	        {(byte) 0x17, (byte) 0x2b, (byte) 0x04, (byte) 0x7e, (byte) 0xba, (byte) 0x77, (byte) 0xd6, (byte) 0x26, (byte) 0xe1, (byte) 0x69, (byte) 0x14, (byte) 0x63, (byte) 0x55, (byte) 0x21, (byte) 0x0c, (byte) 0x7d}
	};


    private static final int[] Rcon = {
            0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36
    };

    private static final int Nb = 4;
    private static final int Nk = 4;
    private static final int Nr = 10;

    public static void main(String[] args) {
        try {
            String inputFilePath = "D:\\IDE Eclipse\\AES\\src\\main\\input.txt";
            String outputFilePath = "D:\\IDE Eclipse\\AES\\src\\main\\output.txt";
            String key = "Thats my Kung Fu";
            byte[] inputBytes = readFile(inputFilePath);
            byte[] keyBytes = key.getBytes();

            byte[] encryptedBytes = encrypt(inputBytes, keyBytes);
            writeFile(outputFilePath, encryptedBytes);

            System.out.println("Encryption completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static void writeFile(String filePath, byte[] data) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, data);
    }

    public static byte[] encrypt(byte[] input, byte[] key) {
		byte[][] state = new byte[4][Nb];
		byte[] roundKey = new byte[4 * Nb * (Nr + 1)];

		expandKey(key, roundKey);

		//convert a one-dimensional array to a two-dimensional array
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < Nb; j++) {
				state[i][j] = input[i + 4 * j];
			}
		}

		addRoundKey(state, roundKey, 0);

		for (int round = 1; round < Nr; ++round) {
			subBytes(state);
			shiftRows(state);
			mixColumns(state);
			addRoundKey(state, roundKey, round);
		}

		subBytes(state);
		shiftRows(state);
		addRoundKey(state, roundKey, Nr);

		byte[] output = new byte[4 * Nb];
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < Nb; ++j) {
				output[i + 4 * j] = state[i][j];
			}
		}
		return output;
	}

	private static void expandKey(byte[] key, byte[] roundKey) {
		int i, k;
		byte[] temp = new byte[4];

		// => i = null
		for (i = 0; i < Nk; ++i) {
			roundKey[i * 4] = key[i * 4];
			roundKey[i * 4 + 1] = key[i * 4 + 1];
			roundKey[i * 4 + 2] = key[i * 4 + 2];
			roundKey[i * 4 + 3] = key[i * 4 + 3];
		}
		// => i = 4

		while (i < (Nb * (Nr + 1))) {
			for (k = 0; k < 4; ++k) {
				temp[k] = roundKey[(i - 1) * 4 + k];
			}

			if (i % Nk == 0) {
				temp = subWord(rotWord(temp));
				
				//  ^  = XOR ( Similar things = false, different things = true)
				temp[0] ^= Rcon[i / Nk];
			}

			roundKey[i * 4 + 0] = (byte) (roundKey[(i - Nk) * 4 + 0] ^ temp[0]);
			roundKey[i * 4 + 1] = (byte) (roundKey[(i - Nk) * 4 + 1] ^ temp[1]);
			roundKey[i * 4 + 2] = (byte) (roundKey[(i - Nk) * 4 + 2] ^ temp[2]);
			roundKey[i * 4 + 3] = (byte) (roundKey[(i - Nk) * 4 + 3] ^ temp[3]);
			++i;
		}
	}

	// Get the corresponding value in SBox
	private static byte[] subWord(byte[] word) {
		for (int i = 0; i < 4; ++i) {
			/*	EX: word[1] = 01001101
			 * 	>>> 4 = shift right 4 bits
			 * 	& 0x0F = get (Lowest 4 bits)
			 * 	word[1] >>> 4 & 0x0F = 0100 (Highest 4 bits)
			 *  word[1] & 0x0F = 0100 (Highest 4 bits)
			 */
			word[i] = (byte) (Sbox[(word[i] >>> 4) & 0x0F][word[i] & 0x0F]);
		}
		return word;
	}

	// Move last bit to first. remaining bits index+1
	private static byte[] rotWord(byte[] word) {
		byte temp = word[0];
		word[0] = word[1];
		word[1] = word[2];
		word[2] = word[3];
		word[3] = temp;
		return word;
	}

    private static void subBytes(byte[][] state) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < Nb; ++j) {
            	// Same with subWord
                state[i][j] = (byte) (Sbox[(state[i][j] >>> 4) & 0x0F][state[i][j] & 0x0F]);
            }
        }
    }

    private static void shiftRows(byte[][] state) {
        byte[] temp = new byte[4];
        for (int i = 1; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                temp[j] = state[i][(j + i) % Nb];
            }
            for (int j = 0; j < 4; ++j) {
                state[i][j] = temp[j];
            }
        }
    }
    
    // Function to perform InvShiftRows transformation
    private static void invShiftRows(byte[][] state) {
        // row 1
        byte temp = state[1][3];
        state[1][3] = state[1][2];
        state[1][2] = state[1][1];
        state[1][1] = state[1][0];
        state[1][0] = temp;

        // row 2
        temp = state[2][2];
        state[2][2] = state[2][0];
        state[2][0] = temp;
        temp = state[2][3];
        state[2][3] = state[2][1];
        state[2][1] = temp;

        // row 3
        temp = state[3][0];
        state[3][0] = state[3][1];
        state[3][1] = state[3][2];
        state[3][2] = state[3][3];
        state[3][3] = temp;
    }

    private static void mixColumns(byte[][] state) {
        byte[][] temp = new byte[4][4];
        for (int i = 0; i < 4; ++i) {
            temp[0][i] = (byte) (gfMul(0x02, state[0][i]) ^ gfMul(0x03, state[1][i]) ^ state[2][i] ^ state[3][i]);
            temp[1][i] = (byte) (state[0][i] ^ gfMul(0x02, state[1][i]) ^ gfMul(0x03, state[2][i]) ^ state[3][i]);
            temp[2][i] = (byte) (state[0][i] ^ state[1][i] ^ gfMul(0x02, state[2][i]) ^ gfMul(0x03, state[3][i]));
            temp[3][i] = (byte) (gfMul(0x03, state[0][i]) ^ state[1][i] ^ state[2][i] ^ gfMul(0x02, state[3][i]));
        }
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[i][j] = temp[i][j];
            }
        }
    }

    private static void addRoundKey(byte[][] state, byte[] roundKey, int round) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < Nb; ++j) {
                state[i][j] ^= roundKey[round * Nb * 4 + i * Nb + j];
            }
        }
    }

    private static byte gfMul(int a, byte b) {
        byte result = 0;
        while (a != 0) {
            if ((a & 1) != 0) {
                result ^= b;
            }
            boolean highBitSet = (b & 0x80) != 0;
            b <<= 1;
            if (highBitSet) {
                b ^= 0x1b;
            }
            a >>= 1;
        }
        return result;
    }


    // Function to perform InvSubBytes transformation
    private static void invSubBytes(byte[][] state) {
        int Nb = 4;
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = invSbox[state[i][j] >> 4][state[i][j] & 0x0f];
            }
        }
    }

    private static void invMixColumns(byte[][] state) {
        int Nb = 4;
        for (int i = 0; i < Nb; i++) {
            byte a = state[0][i];
            byte b = state[1][i];
            byte c = state[2][i];
            byte d = state[3][i];
            state[0][i] = (byte) (mul0e(a) ^ mul0b(b) ^ mul0d(c) ^ mul09(d));
            state[1][i] = (byte) (mul09(a) ^ mul0e(b) ^ mul0b(c) ^ mul0d(d));
            state[2][i] = (byte) (mul0d(a) ^ mul09(b) ^ mul0e(c) ^ mul0b(d));
            state[3][i] = (byte) (mul0b(a) ^ mul0d(b) ^ mul09(c) ^ mul0e(d));
        }
    }
  //------------------Mul02-------------------------
    private static byte mul02(byte value) {
        if (value < 0x80) {
            return (byte) (value << 1);
        } else {
            return (byte) ((value << 1) ^ 0x1b);
        }
    }

    //------------------Mul03-------------------------
    private static byte mul03(byte value) {
        return (byte) (mul02(value) ^ value);
    }
    
  //------------------Mul09-------------------------
    private static byte mul09(byte value) {
        return (byte) (mul02(mul02(mul02(value))) ^ value);
    }

    //------------------Mul0b-------------------------
    private static byte mul0b(byte value) {
        return (byte) (mul02(mul02(mul02(value))) ^ mul02(value) ^ value);
    }

    //------------------Mul0d-------------------------
    private static byte mul0d(byte value) {
        return (byte) (mul02(mul02(mul02(value))) ^ mul02(mul02(value)) ^ (value));
    }

    //------------------Mul0e-------------------------
    private static byte mul0e(byte value) {
        return (byte) (mul02(mul02(mul02(value))) ^ mul02(mul02(value)) ^ mul02(value));
    }
    
    
}
