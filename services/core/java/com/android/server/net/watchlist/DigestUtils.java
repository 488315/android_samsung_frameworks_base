package com.android.server.net.watchlist;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public abstract class DigestUtils {
    public static byte[] getSha256Hash(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            byte[] bArr =
                    new byte[EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read < 0) {
                    byte[] digest = messageDigest.digest();
                    fileInputStream.close();
                    return digest;
                }
                messageDigest.update(bArr, 0, read);
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
