package android.drm;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@Deprecated
/* loaded from: classes.dex */
public class DrmUtils {
    static byte[] readBytes(String str) throws IOException {
        return readBytes(new File(str));
    }

    static byte[] readBytes(File file) throws IOException {
        byte[] bArr;
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        try {
            int iAvailable = bufferedInputStream.available();
            if (iAvailable > 0) {
                bArr = new byte[iAvailable];
                bufferedInputStream.read(bArr);
            } else {
                bArr = null;
            }
            return bArr;
        } finally {
            quietlyDispose(bufferedInputStream);
            quietlyDispose(fileInputStream);
        }
    }

    static void writeToFile(String str, byte[] bArr) throws Throwable {
        if (str == null || bArr == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                fileOutputStream2.write(bArr);
                quietlyDispose(fileOutputStream2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                quietlyDispose(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    static void removeFile(String str) throws IOException {
        new File(str).delete();
    }

    private static void quietlyDispose(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static ExtendedMetadataParser getExtendedMetadataParser(byte[] bArr) {
        return new ExtendedMetadataParser(bArr);
    }

    public static class ExtendedMetadataParser {
        HashMap<String, String> mMap;

        private int readByte(byte[] bArr, int i) {
            return bArr[i];
        }

        private String readMultipleBytes(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i];
            int i3 = 0;
            int i4 = i2;
            while (i4 < i2 + i) {
                bArr2[i3] = bArr[i4];
                i4++;
                i3++;
            }
            return new String(bArr2);
        }

        private ExtendedMetadataParser(byte[] bArr) {
            this.mMap = new HashMap<>();
            int i = 0;
            while (i < bArr.length) {
                int i2 = readByte(bArr, i);
                int i3 = readByte(bArr, i + 1);
                int i4 = i + 2;
                String multipleBytes = readMultipleBytes(bArr, i2, i4);
                int i5 = i4 + i2;
                String multipleBytes2 = readMultipleBytes(bArr, i3, i5);
                if (multipleBytes2.equals(" ")) {
                    multipleBytes2 = "";
                }
                i = i5 + i3;
                this.mMap.put(multipleBytes, multipleBytes2);
            }
        }

        public Iterator<String> iterator() {
            return this.mMap.values().iterator();
        }

        public Iterator<String> keyIterator() {
            return this.mMap.keySet().iterator();
        }

        public String get(String str) {
            return this.mMap.get(str);
        }
    }
}
