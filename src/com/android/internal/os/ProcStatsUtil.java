package com.android.internal.os;

import android.os.StrictMode;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public final class ProcStatsUtil {
    private static final boolean DEBUG = false;
    private static final int READ_SIZE = 1024;
    private static final String TAG = "ProcStatsUtil";

    private ProcStatsUtil() {
    }

    public static String readNullSeparatedFile(String str) {
        String readSingleLineProcFile = readSingleLineProcFile(str);
        if (readSingleLineProcFile == null) {
            return null;
        }
        int indexOf = readSingleLineProcFile.indexOf("\u0000\u0000");
        if (indexOf != -1) {
            readSingleLineProcFile = readSingleLineProcFile.substring(0, indexOf);
        }
        return readSingleLineProcFile.replace("\u0000", " ");
    }

    public static String readSingleLineProcFile(String str) {
        return readTerminatedProcFile(str, (byte) 10);
    }

    public static String readTerminatedProcFile(String str, byte b) {
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            return readTerminatedProcFileInternal(str, b);
        } finally {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    private static String readTerminatedProcFileInternal(String str, byte b) {
        String byteArrayOutputStream;
        boolean z;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream2 = null;
                do {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        int i = 0;
                        while (true) {
                            if (i >= read) {
                                i = -1;
                                break;
                            }
                            if (bArr[i] == b) {
                                break;
                            }
                            i++;
                        }
                        z = i != -1;
                        if (z && byteArrayOutputStream2 == null) {
                            byteArrayOutputStream = new String(bArr, 0, i);
                            break;
                        }
                        if (byteArrayOutputStream2 == null) {
                            byteArrayOutputStream2 = new ByteArrayOutputStream(1024);
                        }
                        if (z) {
                            read = i;
                        }
                        byteArrayOutputStream2.write(bArr, 0, read);
                    } else {
                        break;
                    }
                } while (!z);
                if (byteArrayOutputStream2 == null) {
                    byteArrayOutputStream = "";
                } else {
                    byteArrayOutputStream = byteArrayOutputStream2.toString();
                }
                fileInputStream.close();
                return byteArrayOutputStream;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }
}
