package android.database.sqlite;

import java.io.File;

/* loaded from: classes.dex */
public class SQLiteUtils {
    private static final String TAG = "SQLiteUtils";
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String getHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            char[] cArr2 = hexArray;
            cArr[i2] = cArr2[(b & 255) >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public static long getFileSize(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.length();
        }
        return -1L;
    }
}
