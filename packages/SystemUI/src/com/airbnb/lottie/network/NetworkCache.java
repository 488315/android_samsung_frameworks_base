package com.airbnb.lottie.network;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.airbnb.lottie.L$$ExternalSyntheticLambda0;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NetworkCache {
    public final LottieNetworkCacheProvider cacheProvider;

    public NetworkCache(LottieNetworkCacheProvider lottieNetworkCacheProvider) {
        this.cacheProvider = lottieNetworkCacheProvider;
    }

    public static String filenameForUrl(String str, FileExtension fileExtension, boolean z) {
        String str2 = z ? ".temp" + fileExtension.extension : fileExtension.extension;
        String replaceAll = str.replaceAll("\\W+", "");
        int length = 242 - str2.length();
        if (replaceAll.length() > length) {
            try {
                byte[] digest = MessageDigest.getInstance("MD5").digest(replaceAll.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", Byte.valueOf(b)));
                }
                replaceAll = sb.toString();
            } catch (NoSuchAlgorithmException unused) {
                replaceAll = replaceAll.substring(0, length);
            }
        }
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("lottie_cache_", replaceAll, str2);
    }

    public final File parentDir() {
        File file = new File(((L$$ExternalSyntheticLambda0) this.cacheProvider).f$0.getCacheDir(), "lottie_network_cache");
        if (file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public final File writeTempCacheFile(String str, InputStream inputStream, FileExtension fileExtension) {
        File file = new File(parentDir(), filenameForUrl(str, fileExtension, true));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return file;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
            } catch (Throwable th) {
                fileOutputStream.close();
                throw th;
            }
        } finally {
            inputStream.close();
        }
    }
}
