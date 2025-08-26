package com.android.internal.telephony.uicc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.android.internal.R;
import com.android.internal.telephony.GsmAlphabet;
import com.android.telephony.Rlog;
import com.google.android.mms.pdu.CharacterSets;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/* loaded from: classes4.dex */
public class IccUtils {
    static final int FPLMN_BYTE_SIZE = 3;
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};
    public static final int ICCID_ALL_FF = 255;
    public static final int ICCID_HAS_CHAR = 1;
    public static final int ICCID_NO_HAS_CHAR = 0;
    static final String LOG_TAG = "IccUtils";
    public static final String TEST_ICCID = "FFFFFFFFFFFFFFFFFFFF";

    private static int bitToRGB(int i) {
        return i == 1 ? -1 : -16777216;
    }

    public static int cdmaBcdByteToInt(byte b) {
        int i = (b & 240) <= 144 ? ((b >> 4) & 15) * 10 : 0;
        int i2 = b & 15;
        return i2 <= 9 ? i + i2 : i;
    }

    public static int cdmaHexByteToInt(byte b) {
        int i = (b & 240) <= 240 ? ((b >> 4) & 15) * 16 : 0;
        int i2 = b & 15;
        return i2 <= 15 ? i + i2 : i;
    }

    private static byte charToByte(char c) {
        int i;
        if (c >= '0' && c <= '9') {
            i = c - '0';
        } else if (c >= 'A' && c <= 'F') {
            i = c - '7';
        } else {
            if (c < 'a' || c > 'f') {
                return (byte) 0;
            }
            i = c - 'W';
        }
        return (byte) i;
    }

    public static byte countTrailingZeros(byte b) {
        if (b == 0) {
            return (byte) 8;
        }
        byte b2 = (b & 15) != 0 ? (byte) 3 : (byte) 7;
        if ((b & 51) != 0) {
            b2 = (byte) (b2 - 2);
        }
        return (b & 85) != 0 ? (byte) (b2 - 1) : b2;
    }

    public static int gsmBcdByteToInt(byte b) {
        int i = (b & 240) <= 144 ? (b >> 4) & 15 : 0;
        int i2 = b & 15;
        return i2 <= 9 ? i + (i2 * 10) : i;
    }

    private static long unsigned32(byte b) {
        return b & 255;
    }

    public static String bcdToString(byte[] bArr, int i, int i2) {
        int i3;
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i4 = i; i4 < i + i2 && (i3 = bArr[i4] & 15) <= 9; i4++) {
            sb.append((char) (i3 + 48));
            int i5 = (bArr[i4] >> 4) & 15;
            if (i5 != 15) {
                if (i5 > 9) {
                    break;
                }
                sb.append((char) (i5 + 48));
            }
        }
        return sb.toString();
    }

    public static String bcdToString(byte[] bArr) {
        return bcdToString(bArr, 0, bArr.length);
    }

    public static byte[] bcdToBytes(String str) {
        byte[] bArr = new byte[(str.length() + 1) / 2];
        bcdToBytes(str, bArr);
        return bArr;
    }

    public static void bcdToBytes(String str, byte[] bArr) {
        bcdToBytes(str, bArr, 0);
    }

    public static void bcdToBytes(String str, byte[] bArr, int i) {
        if (str.length() % 2 != 0) {
            str = str + "0";
        }
        int iMin = Math.min((bArr.length - i) * 2, str.length());
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i3 >= iMin) {
                return;
            }
            bArr[i] = (byte) ((charToByte(str.charAt(i3)) << 4) | charToByte(str.charAt(i2)));
            i2 += 2;
            i++;
        }
    }

    public static String bcdPlmnToString(byte[] bArr, int i) {
        if (i + 3 > bArr.length) {
            return null;
        }
        byte b = bArr[i];
        byte b2 = bArr[i + 1];
        byte b3 = bArr[i + 2];
        String strBytesToHexString = bytesToHexString(new byte[]{(byte) (((b >> 4) & 15) | (b << 4)), (byte) ((b3 & 15) | (b2 << 4)), (byte) ((b3 & 240) | ((b2 >> 4) & 15))});
        return strBytesToHexString.contains("F") ? strBytesToHexString.replaceAll("F", "") : strBytesToHexString;
    }

    public static void stringToBcdPlmn(String str, byte[] bArr, int i) {
        char cCharAt = str.length() > 5 ? str.charAt(5) : 'F';
        bArr[i] = (byte) ((charToByte(str.charAt(1)) << 4) | charToByte(str.charAt(0)));
        bArr[i + 1] = (byte) ((charToByte(cCharAt) << 4) | charToByte(str.charAt(2)));
        bArr[i + 2] = (byte) (charToByte(str.charAt(3)) | (charToByte(str.charAt(4)) << 4));
    }

    public static String bchToString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i3 = i; i3 < i + i2; i3++) {
            int i4 = bArr[i3] & 15;
            char[] cArr = HEX_CHARS;
            sb.append(cArr[i4]);
            sb.append(cArr[(bArr[i3] >> 4) & 15]);
        }
        return sb.toString();
    }

    public static String cdmaBcdToString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2);
        int i3 = 0;
        while (i3 < i2) {
            int i4 = bArr[i] & 15;
            if (i4 > 9) {
                i4 = 0;
            }
            sb.append((char) (i4 + 48));
            if (i3 + 1 == i2) {
                break;
            }
            int i5 = (bArr[i] >> 4) & 15;
            if (i5 > 9) {
                i5 = 0;
            }
            sb.append((char) (i5 + 48));
            i3 += 2;
            i++;
        }
        return sb.toString();
    }

    public static byte[] stringToAdnStringField(String str) {
        int iCountGsmSeptetsUsingTables = GsmAlphabet.countGsmSeptetsUsingTables(str, false, 0, 0);
        if (iCountGsmSeptetsUsingTables != -1) {
            byte[] bArr = new byte[iCountGsmSeptetsUsingTables];
            GsmAlphabet.stringToGsm8BitUnpackedField(str, bArr, 0, iCountGsmSeptetsUsingTables);
            return bArr;
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_16BE);
        byte[] bArr2 = new byte[bytes.length + 1];
        bArr2[0] = Byte.MIN_VALUE;
        System.arraycopy(bytes, 0, bArr2, 1, bytes.length);
        return bArr2;
    }

    public static String adnStringFieldToString(byte[] bArr, int i, int i2) {
        int i3;
        char c;
        String str;
        String string = "";
        if (i2 == 0) {
            return "";
        }
        boolean z = true;
        if (i2 >= 1 && bArr[i] == Byte.MIN_VALUE) {
            try {
                str = new String(bArr, i + 1, ((i2 - 1) / 2) * 2, "utf-16be");
            } catch (UnsupportedEncodingException e) {
                Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = null;
            }
            if (str != null) {
                int length = str.length();
                while (length > 0 && str.charAt(length - 1) == 65535) {
                    length--;
                }
                return str.substring(0, length);
            }
        }
        if (i2 >= 3 && bArr[i] == -127) {
            i3 = bArr[i + 1] & 255;
            int i4 = i2 - 3;
            if (i3 > i4) {
                i3 = i4;
            }
            c = (char) ((bArr[i + 2] & 255) << 7);
            i += 3;
        } else if (i2 < 4 || bArr[i] != -126) {
            z = false;
            i3 = 0;
            c = 0;
        } else {
            i3 = bArr[i + 1] & 255;
            int i5 = i2 - 4;
            if (i3 > i5) {
                i3 = i5;
            }
            c = (char) (((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255));
            i += 4;
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            while (i3 > 0) {
                byte b = bArr[i];
                if (b < 0) {
                    sb.append((char) ((b & Byte.MAX_VALUE) + c));
                    i++;
                    i3--;
                }
                int i6 = 0;
                while (i6 < i3 && bArr[i + i6] >= 0) {
                    i6++;
                }
                sb.append(GsmAlphabet.gsm8BitUnpackedToString(bArr, i, i6));
                i += i6;
                i3 -= i6;
            }
            return sb.toString();
        }
        try {
            string = Resources.getSystem().getString(R.string.gsm_alphabet_default_charset);
        } catch (Resources.NotFoundException unused) {
        }
        return GsmAlphabet.gsm8BitUnpackedToString(bArr, i, i2, string.trim());
    }

    public static int hexCharToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'W';
        }
        throw new RuntimeException("invalid hex char '" + c + "'");
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((hexCharToInt(str.charAt(i)) << 4) | hexCharToInt(str.charAt(i + 1)));
        }
        return bArr;
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >> 4) & 15;
            char[] cArr = HEX_CHARS;
            sb.append(cArr[i2]);
            sb.append(cArr[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public static String networkNameToString(byte[] bArr, int i, int i2) {
        int i3 = bArr[i];
        String strGsm7BitPackedToString = "";
        if ((i3 & 128) == 128 && i2 >= 1) {
            int i4 = (i3 >>> 4) & 7;
            if (i4 == 0) {
                strGsm7BitPackedToString = GsmAlphabet.gsm7BitPackedToString(bArr, i + 1, (((i2 - 1) * 8) - (i3 & 7)) / 7);
            } else if (i4 == 1) {
                try {
                    strGsm7BitPackedToString = new String(bArr, i + 1, i2 - 1, CharacterSets.MIMENAME_UTF_16);
                } catch (UnsupportedEncodingException e) {
                    Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", e);
                }
            }
            byte b = bArr[i];
        }
        return strGsm7BitPackedToString;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    public static Bitmap parseToBnW(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = bArr[0] & 255;
        int i4 = bArr[1] & 255;
        int i5 = i3 * i4;
        int[] iArr = new int[i5];
        int i6 = 2;
        byte b = 7;
        byte b2 = 0;
        while (i2 < i5) {
            if (i2 % 8 == 0) {
                int i7 = i6 + 1;
                byte b3 = bArr[i6];
                i6 = i7;
                b2 = b3;
                b = 7;
            }
            iArr[i2] = bitToRGB((b2 >> b) & 1);
            i2++;
            b--;
        }
        if (i2 != i5) {
            Rlog.e(LOG_TAG, "parse end and size error");
        }
        return Bitmap.createBitmap(iArr, i3, i4, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap parseToRGB(byte[] bArr, int i, boolean z) {
        int[] iArrMapToNon2OrderBitColor;
        int i2 = bArr[0] & 255;
        int i3 = bArr[1] & 255;
        int i4 = bArr[2] & 255;
        int i5 = bArr[3] & 255;
        int[] clut = getCLUT(bArr, ((bArr[4] & 255) << 8) | (bArr[5] & 255), i5);
        if (true == z) {
            clut[i5 - 1] = 0;
        }
        if (8 % i4 == 0) {
            iArrMapToNon2OrderBitColor = mapTo2OrderBitColor(bArr, 6, i2 * i3, clut, i4);
        } else {
            iArrMapToNon2OrderBitColor = mapToNon2OrderBitColor(bArr, 6, i2 * i3, clut, i4);
        }
        return Bitmap.createBitmap(iArrMapToNon2OrderBitColor, i2, i3, Bitmap.Config.RGB_565);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int[] mapTo2OrderBitColor(byte[] bArr, int i, int i2, int[] iArr, int i3) {
        int i4;
        if (8 % i3 != 0) {
            Rlog.e(LOG_TAG, "not event number of color");
            return mapToNon2OrderBitColor(bArr, i, i2, iArr, i3);
        }
        if (i3 == 1) {
            i4 = 1;
        } else if (i3 == 2) {
            i4 = 3;
        } else if (i3 == 4) {
            i4 = 15;
        } else if (i3 == 8) {
            i4 = 255;
        }
        int[] iArr2 = new int[i2];
        int i5 = 8 / i3;
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i + 1;
            int i8 = bArr[i];
            int i9 = 0;
            while (i9 < i5) {
                iArr2[i6] = iArr[(i8 >> (((i5 - i9) - 1) * i3)) & i4];
                i9++;
                i6++;
            }
            i = i7;
        }
        return iArr2;
    }

    private static int[] mapToNon2OrderBitColor(byte[] bArr, int i, int i2, int[] iArr, int i3) {
        if (8 % i3 == 0) {
            Rlog.e(LOG_TAG, "not odd number of color");
            return mapTo2OrderBitColor(bArr, i, i2, iArr, i3);
        }
        return new int[i2];
    }

    private static int[] getCLUT(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return null;
        }
        int[] iArr = new int[i2];
        int i3 = (i2 * 3) + i;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 1;
            int i6 = i + 2;
            int i7 = ((bArr[i + 1] & 255) << 8) | ((bArr[i] & 255) << 16) | (-16777216);
            i += 3;
            iArr[i4] = i7 | (bArr[i6] & 255);
            if (i >= i3) {
                return iArr;
            }
            i4 = i5;
        }
    }

    public static String getDecimalSubstring(String str) {
        int i = 0;
        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            i++;
        }
        return str.substring(0, i);
    }

    public static int bytesToInt(byte[] bArr, int i, int i2) {
        if (i2 > 4) {
            throw new IllegalArgumentException("length must be <= 4 (only 32-bit integer supported): " + i2);
        }
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException("Out of the bounds: src=[" + bArr.length + "], offset=" + i + ", length=" + i2);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 << 8) | (bArr[i + i4] & 255);
        }
        if (i3 >= 0) {
            return i3;
        }
        throw new IllegalArgumentException("src cannot be parsed as a positive integer: " + i3);
    }

    public static long bytesToRawLong(byte[] bArr, int i, int i2) {
        if (i2 > 8) {
            throw new IllegalArgumentException("length must be <= 8 (only 64-bit long supported): " + i2);
        }
        if (i >= 0 && i2 >= 0 && i + i2 <= bArr.length) {
            long j = 0;
            for (int i3 = 0; i3 < i2; i3++) {
                j = (j << 8) | (bArr[i + i3] & 255);
            }
            return j;
        }
        throw new IndexOutOfBoundsException("Out of the bounds: src=[" + bArr.length + "], offset=" + i + ", length=" + i2);
    }

    public static byte[] unsignedIntToBytes(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("value must be 0 or positive: " + i);
        }
        byte[] bArr = new byte[byteNumForUnsignedInt(i)];
        unsignedIntToBytes(i, bArr, 0);
        return bArr;
    }

    public static byte[] signedIntToBytes(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("value must be 0 or positive: " + i);
        }
        byte[] bArr = new byte[byteNumForSignedInt(i)];
        signedIntToBytes(i, bArr, 0);
        return bArr;
    }

    public static int unsignedIntToBytes(int i, byte[] bArr, int i2) {
        return intToBytes(i, bArr, i2, false);
    }

    public static int signedIntToBytes(int i, byte[] bArr, int i2) {
        return intToBytes(i, bArr, i2, true);
    }

    public static int byteNumForUnsignedInt(int i) {
        return byteNumForInt(i, false);
    }

    public static int byteNumForSignedInt(int i) {
        return byteNumForInt(i, true);
    }

    private static int intToBytes(int i, byte[] bArr, int i2, boolean z) {
        int iByteNumForInt = byteNumForInt(i, z);
        if (i2 < 0 || i2 + iByteNumForInt > bArr.length) {
            throw new IndexOutOfBoundsException("Not enough space to write. Required bytes: " + iByteNumForInt);
        }
        int i3 = iByteNumForInt - 1;
        while (i3 >= 0) {
            bArr[i2 + i3] = (byte) (i & 255);
            i3--;
            i >>>= 8;
        }
        return iByteNumForInt;
    }

    private static int byteNumForInt(int i, boolean z) {
        if (i < 0) {
            throw new IllegalArgumentException("value must be 0 or positive: " + i);
        }
        if (z) {
            if (i <= 127) {
                return 1;
            }
            if (i <= 32767) {
                return 2;
            }
            return i <= 8388607 ? 3 : 4;
        }
        if (i <= 255) {
            return 1;
        }
        if (i <= 65535) {
            return 2;
        }
        return i <= 16777215 ? 3 : 4;
    }

    public static String byteToHex(byte b) {
        char[] cArr = HEX_CHARS;
        return new String(new char[]{cArr[(b & 255) >>> 4], cArr[b & 15]});
    }

    public static String stripTrailingFs(String str) {
        if ("FFFFFFFFFFFFFFFFFFFF".equals(str)) {
            return str;
        }
        if (str == null) {
            return null;
        }
        return str.replaceAll("(?i)f*$", "");
    }

    public static boolean compareIgnoreTrailingFs(String str, String str2) {
        return TextUtils.equals(str, str2) || TextUtils.equals(stripTrailingFs(str), stripTrailingFs(str2));
    }

    public static byte[] encodeFplmns(List<String> list, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (String str : list) {
            int i3 = i2 + 3;
            if (i3 > i) {
                break;
            }
            stringToBcdPlmn(str, bArr, i2);
            i2 = i3;
        }
        while (i2 < i) {
            bArr[i2] = -1;
            i2++;
        }
        return bArr;
    }

    public static byte[] stringToBytes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((hexCharToInt(str.charAt(i + 1)) << 4) | hexCharToInt(str.charAt(i)));
        }
        return bArr;
    }

    public static String setupCallbcdToString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        if ((bArr[i] & 255) == 145) {
            sb.append('+');
        }
        for (int i3 = i + 1; i3 < i + i2; i3++) {
            int i4 = bArr[i3] & 15;
            if (i4 == 10) {
                sb.append('*');
            } else if (i4 == 11) {
                sb.append('#');
            } else if (i4 == 12) {
                sb.append(',');
            } else {
                if (i4 > 9) {
                    break;
                }
                sb.append((char) (i4 + 48));
            }
            int i5 = (bArr[i3] >> 4) & 15;
            if (i5 == 10) {
                sb.append('*');
            } else if (i5 == 11) {
                sb.append('#');
            } else if (i5 == 12) {
                sb.append(',');
            } else {
                if (i5 > 9) {
                    break;
                }
                sb.append((char) (i5 + 48));
            }
        }
        return sb.toString();
    }

    public static String SSbcdToString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        int i3 = bArr[i] & 255;
        int i4 = i + 1;
        for (int i5 = i4; i5 < i + i2; i5++) {
            int i6 = bArr[i5] & 15;
            if (i6 == 10) {
                sb.append('*');
                if (i3 == 145 && i5 - i4 > 1) {
                    sb.append('+');
                    i3 = 0;
                }
            } else if (i6 == 11) {
                sb.append('#');
            } else {
                if (i6 > 9) {
                    break;
                }
                sb.append((char) (i6 + 48));
            }
            int i7 = (bArr[i5] >> 4) & 15;
            if (i7 == 10) {
                sb.append('*');
                if (i3 == 145 && i5 - i4 > 1) {
                    sb.append('+');
                    i3 = 0;
                }
            } else if (i7 == 11) {
                sb.append('#');
            } else {
                if (i7 > 9) {
                    break;
                }
                sb.append((char) (i7 + 48));
            }
        }
        return sb.toString();
    }

    public static byte cdmaIntToBcdByte(int i) {
        int i2 = i / 10;
        byte b = (((byte) i2) & 240) <= 144 ? (byte) (i2 << 4) : (byte) 0;
        int i3 = i % 10;
        return (((byte) i3) & 15) <= 9 ? (byte) (b + i3) : b;
    }

    public static String byteToHexString(byte b) {
        StringBuilder sb = new StringBuilder(2);
        sb.append("0123456789abcdef".charAt((b >> 4) & 15));
        sb.append("0123456789abcdef".charAt(b & 15));
        return sb.toString();
    }

    public static String mccMncConvert(String str) throws NumberFormatException {
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(str.charAt(1));
        int i = 0;
        sb.append(str.charAt(0));
        sb.append(str.charAt(3));
        if (sb.toString().compareToIgnoreCase("fff") == 0) {
            Rlog.i(LOG_TAG, "[MccMncConvert] MCC Value is invalid('fff')!");
            return null;
        }
        try {
            if (sb.toString().compareToIgnoreCase("ddd") != 0) {
                i = Integer.parseInt(sb.toString());
            }
        } catch (NumberFormatException e) {
            Rlog.e(LOG_TAG, "mccMncConvert Exception:", e);
        }
        sb.append(str.charAt(5));
        sb.append(str.charAt(4));
        if (str.charAt(2) != 'F' && str.charAt(2) != 'f') {
            sb.append(str.charAt(2));
        } else if (i >= 310 && i <= 316) {
            sb.append("0");
        }
        Rlog.i(LOG_TAG, "[MccMncConvert] Convert Result :" + sb.toString());
        return sb.toString();
    }

    private static String getStringMCC(long j) {
        StringBuilder sb = new StringBuilder(3);
        long j2 = j % 1000;
        sb.append((char) (j2 / 100 == 9 ? 48L : r3 + 49));
        long j3 = j2 % 100;
        sb.append((char) (j3 / 10 == 9 ? 48L : r3 + 49));
        sb.append((char) (j3 % 10 != 9 ? r12 + 49 : 48L));
        return sb.toString();
    }

    private static String getStringMNC(long j) {
        StringBuilder sb = new StringBuilder(2);
        long j2 = j % 100;
        sb.append((char) (j2 / 10 == 9 ? 48L : r3 + 49));
        sb.append((char) (j2 % 10 != 9 ? r12 + 49 : 48L));
        return sb.toString();
    }

    private static String getStringMIN1(long j) {
        long j2;
        long j3;
        StringBuilder sb = new StringBuilder(7);
        if (j == 0) {
            for (long j4 = 0; j4 < 7; j4++) {
                sb.append('0');
            }
        } else {
            long j5 = (j >> 14) % 1000;
            sb.append((char) (j5 / 100 == 9 ? 48L : r7 + 49));
            long j6 = j5 % 100;
            long j7 = j6 / 10;
            if (j7 == 9) {
                j2 = 1000;
                j3 = 48;
            } else {
                j2 = 1000;
                j3 = j7 + 49;
            }
            sb.append((char) j3);
            sb.append((char) (j6 % 10 == 9 ? 48L : r1 + 49));
            sb.append((char) ((((j & 16383) >> 10) & 15) == 10 ? 48L : r1 + 48));
            long j8 = (j & 1023) % j2;
            sb.append((char) (j8 / 100 == 9 ? 48L : r3 + 49));
            long j9 = j8 % 100;
            sb.append((char) (j9 / 10 == 9 ? 48L : r3 + 49));
            sb.append((char) (j9 % 10 != 9 ? r1 + 49 : 48L));
        }
        return sb.toString();
    }

    private static String getStringMIN2(long j) {
        StringBuilder sb = new StringBuilder(3);
        long j2 = j % 1000;
        sb.append((char) (j2 / 100 == 9 ? 48L : r3 + 49));
        long j3 = j2 % 100;
        sb.append((char) (j3 / 10 == 9 ? 48L : r3 + 49));
        sb.append((char) (j3 % 10 != 9 ? r12 + 49 : 48L));
        return sb.toString();
    }

    public static String setupMDNbcdToString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i3 = i + 1; i3 < i + i2; i3++) {
            int i4 = bArr[i3] & 15;
            if (i4 == 10) {
                sb.append('0');
            } else if (i4 == 11) {
                sb.append('*');
            } else if (i4 == 12) {
                sb.append('#');
            } else {
                if (i4 > 9) {
                    break;
                }
                sb.append((char) (i4 + 48));
            }
            int i5 = (bArr[i3] >> 4) & 15;
            if (i5 == 10) {
                sb.append('0');
            } else if (i5 == 11) {
                sb.append('*');
            } else if (i5 == 12) {
                sb.append('#');
            } else {
                if (i5 > 9) {
                    break;
                }
                sb.append((char) (i5 + 48));
            }
        }
        if (sb.toString().length() > i2) {
            return sb.toString().substring(0, i2);
        }
        return sb.toString();
    }

    public static int isIccIdHasChar(byte[] bArr, int i) {
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            byte b = bArr[i2];
            if ((b & 15) != 15 || ((b >> 4) & 15) != 15) {
                z = false;
                break;
            }
            i2++;
            z = true;
        }
        if (z) {
            return 255;
        }
        for (int i3 = 0; i3 < i; i3++) {
            byte b2 = bArr[i3];
            int i4 = b2 & 15;
            int i5 = (b2 >> 4) & 15;
            if (i4 > 9 || (i5 > 9 && i3 != i - 1)) {
                return 1;
            }
        }
        return 0;
    }

    public static String byteToBinaryString(byte b) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int i = 0; i < 8; i++) {
            if (((b >> i) & 1) > 0) {
                sb.setCharAt(7 - i, '1');
            }
        }
        return sb.toString();
    }

    public static String byteArrayToBinaryString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(byteToBinaryString(b));
        }
        return sb.toString();
    }

    public static String bcdToStringForIccId(byte[] bArr, int i, int i2) {
        if ("CN".equals(SystemProperties.get("ro.csc.countryiso_code", "")) || isIccIdHasChar(bArr, bArr.length) == 255) {
            return bchToString(bArr, i, i2);
        }
        return bcdToString(bArr, i, i2);
    }

    public static String stripTrailingFsForIccId(String str) {
        return "CN".equals(SystemProperties.get("ro.csc.countryiso_code", "")) ? str : stripTrailingFs(str);
    }

    public static int getIccType(int i) {
        String str;
        if (i != 1) {
            str = "ril.ICC_TYPE0";
        } else {
            str = "ril.ICC_TYPE1";
        }
        try {
            return Integer.parseInt(SystemProperties.get(str, "0"));
        } catch (NumberFormatException e) {
            Rlog.e(LOG_TAG, "getIccType Exception:", e);
            return 0;
        }
    }

    public static void setUiccProperty(int i, String str, String str2) {
        String str3;
        String str4 = SystemProperties.get(str);
        if (str2 == null) {
            str2 = "";
        }
        str2.replace(',', ' ');
        String[] strArrSplit = str4 != null ? str4.split(",") : null;
        if (i >= 0 && i < TelephonyManager.getDefault().getActiveModemCount()) {
            String str5 = "";
            for (int i2 = 0; i2 < i; i2++) {
                if (strArrSplit == null || i2 >= strArrSplit.length) {
                    str3 = "";
                } else {
                    str3 = strArrSplit[i2];
                }
                str5 = str5 + str3 + ",";
            }
            String str6 = str5 + str2;
            if (strArrSplit != null) {
                while (true) {
                    i++;
                    if (i >= strArrSplit.length) {
                        break;
                    }
                    str6 = str6 + "," + strArrSplit[i];
                }
            }
            int length = str6.length();
            try {
                length = str6.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException unused) {
                Rlog.d(LOG_TAG, "setUiccProperty: utf-8 not supported");
            }
            if (length > 91) {
                Rlog.d(LOG_TAG, "setUiccProperty: property too long property=" + str);
                return;
            }
            SystemProperties.set(str, str6);
            return;
        }
        Rlog.d(LOG_TAG, "setUiccProperty: invalid phoneId=" + i);
    }
}
