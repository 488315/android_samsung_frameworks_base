package android.sec.enterprise.certificate;

import com.android.internal.midi.MidiConstants;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public final class Base64 {
    private static final byte[] BASE_64_ALPHABET = initializeBase64Alphabet();
    private static final byte END_OF_INPUT = -3;
    private static final int FIRST_OUTPUT_BYTE_MASK = 16515072;
    private static final int FOURTH_OUTPUT_BYTE_MASK = 63;
    private static final byte PAD_AS_BYTE = -1;
    private static final int SECOND_OUTPUT_BYTE_MASK = 258048;
    private static final int THIRD_OUTPUT_BYTE_MASK = 4032;
    private static final byte WHITESPACE_AS_BYTE = -2;

    private static byte[] initializeBase64Alphabet() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                .getBytes(StandardCharsets.US_ASCII);
    }

    private Base64() {}

    public static String encode(byte[] in) {
        int byteTripletAsInt;
        int len = in.length;
        int outputLen = computeEncodingOutputLen(len);
        byte[] output = new byte[outputLen];
        int outputIndex = 0;
        for (int i = 0; i < len; i += 3) {
            int byteTripletAsInt2 = in[i] & 255;
            if (i + 1 < len) {
                int byteTripletAsInt3 = (byteTripletAsInt2 << 8) | (in[i + 1] & 255);
                if (i + 2 < len) {
                    byteTripletAsInt = (byteTripletAsInt3 << 8) | (in[i + 2] & 255);
                } else {
                    byteTripletAsInt = byteTripletAsInt3 << 2;
                }
            } else {
                byteTripletAsInt = byteTripletAsInt2 << 4;
            }
            if (i + 2 < len) {
                output[outputIndex] =
                        BASE_64_ALPHABET[(FIRST_OUTPUT_BYTE_MASK & byteTripletAsInt) >>> 18];
                outputIndex++;
            }
            int outputIndex2 = i + 1;
            if (outputIndex2 < len) {
                output[outputIndex] =
                        BASE_64_ALPHABET[(SECOND_OUTPUT_BYTE_MASK & byteTripletAsInt) >>> 12];
                outputIndex++;
            }
            int outputIndex3 = outputIndex + 1;
            output[outputIndex] =
                    BASE_64_ALPHABET[(byteTripletAsInt & THIRD_OUTPUT_BYTE_MASK) >>> 6];
            outputIndex = outputIndex3 + 1;
            output[outputIndex3] = BASE_64_ALPHABET[byteTripletAsInt & 63];
        }
        int i2 = len % 3;
        if (i2 > 0) {
            int outputIndex4 = outputIndex + 1;
            output[outputIndex] = 61;
            if (i2 == 1) {
                int i3 = outputIndex4 + 1;
                output[outputIndex4] = 61;
            }
        }
        return new String(output, StandardCharsets.US_ASCII);
    }

    private static int computeEncodingOutputLen(int inLength) {
        int inLengthMod3 = inLength % 3;
        int outputLen = (inLength / 3) * 4;
        if (inLengthMod3 == 2) {
            return outputLen + 4;
        }
        if (inLengthMod3 == 1) {
            return outputLen + 4;
        }
        return outputLen;
    }

    public static byte[] decode(byte[] in) {
        return decode(in, in.length);
    }

    public static byte[] decode(byte[] in, int len) {
        int inLength = Math.min(in.length, len);
        ByteArrayOutputStream output = new ByteArrayOutputStream(((inLength / 4) * 3) + 3);
        int[] pos = new int[1];
        while (pos[0] < inLength) {
            try {
                int byteTripletAsInt = 0;
                for (int j = 0; j < 4; j++) {
                    byte c = getNextByte(in, pos, inLength);
                    if (c != -3 && c != -1) {
                        byteTripletAsInt = (byteTripletAsInt << 6) + (c & 255);
                        pos[0] = pos[0] + 1;
                    }
                    switch (j) {
                        case 0:
                        case 1:
                            if (c == -3) {
                                return output.toByteArray();
                            }
                            return null;
                        case 2:
                            if (c != -3) {
                                pos[0] = pos[0] + 1;
                                byte c2 = getNextByte(in, pos, inLength);
                                if (c2 == -3) {
                                    return checkNoTrailingAndReturn(output, in, pos[0], inLength);
                                }
                                if (c2 != -1) {
                                    return null;
                                }
                                output.write(byteTripletAsInt >> 4);
                                return checkNoTrailingAndReturn(output, in, pos[0], inLength);
                            }
                            return checkNoTrailingAndReturn(output, in, pos[0], inLength);
                        case 3:
                            if (c == -1) {
                                int byteTripletAsInt2 = byteTripletAsInt >> 2;
                                output.write(byteTripletAsInt2 >> 8);
                                output.write(byteTripletAsInt2 & 255);
                            }
                            return checkNoTrailingAndReturn(output, in, pos[0], inLength);
                    }
                }
                output.write(byteTripletAsInt >> 16);
                output.write((byteTripletAsInt >> 8) & 255);
                output.write(byteTripletAsInt & 255);
            } catch (InvalidBase64ByteException e) {
                return null;
            }
        }
        return checkNoTrailingAndReturn(output, in, pos[0], inLength);
    }

    private static class InvalidBase64ByteException extends Exception {
        private InvalidBase64ByteException() {}
    }

    private static byte getNextByte(byte[] in, int[] pos, int inLength)
            throws InvalidBase64ByteException {
        while (pos[0] < inLength) {
            byte c = base64AlphabetToNumericalValue(in[pos[0]]);
            if (c != -2) {
                return c;
            }
            pos[0] = pos[0] + 1;
        }
        return END_OF_INPUT;
    }

    private static byte[] checkNoTrailingAndReturn(
            ByteArrayOutputStream output, byte[] in, int i, int inLength)
            throws InvalidBase64ByteException {
        while (i < inLength) {
            byte c = base64AlphabetToNumericalValue(in[i]);
            if (c != -2 && c != -1) {
                return null;
            }
            i++;
        }
        return output.toByteArray();
    }

    private static byte base64AlphabetToNumericalValue(byte c) throws InvalidBase64ByteException {
        if (65 <= c && c <= 90) {
            return (byte) (c - 65);
        }
        if (97 <= c && c <= 122) {
            return (byte) ((c - 97) + 26);
        }
        if (48 <= c && c <= 57) {
            return (byte) (c + MidiConstants.STATUS_CHANNEL_PRESSURE + 52);
        }
        if (c == 43) {
            return (byte) 62;
        }
        if (c == 47) {
            return (byte) 63;
        }
        if (c == 61) {
            return (byte) -1;
        }
        if (c == 32 || c == 9 || c == 13 || c == 10) {
            return (byte) -2;
        }
        throw new InvalidBase64ByteException();
    }
}
