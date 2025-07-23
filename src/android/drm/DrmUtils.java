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
            int available = bufferedInputStream.available();
            if (available > 0) {
                bArr = new byte[available];
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

    static void writeToFile(String str, byte[] bArr) throws IOException {
        FileOutputStream fileOutputStream;
        if (str == null || bArr == null) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(bArr);
            quietlyDispose(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            quietlyDispose(fileOutputStream2);
            throw th;
        }
    }

    static void removeFile(String str) throws IOException {
        new File(str).delete();
    }

    private static void quietlyDispose(Closeable closeable) {
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
                int readByte = readByte(bArr, i);
                int readByte2 = readByte(bArr, i + 1);
                int i2 = i + 2;
                String readMultipleBytes = readMultipleBytes(bArr, readByte, i2);
                int i3 = i2 + readByte;
                String readMultipleBytes2 = readMultipleBytes(bArr, readByte2, i3);
                if (readMultipleBytes2.equals(" ")) {
                    readMultipleBytes2 = "";
                }
                i = i3 + readByte2;
                this.mMap.put(readMultipleBytes, readMultipleBytes2);
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
