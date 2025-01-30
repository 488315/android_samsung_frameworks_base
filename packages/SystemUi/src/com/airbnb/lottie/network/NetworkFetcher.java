package com.airbnb.lottie.network;

import android.content.Context;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NetworkFetcher {
    public final LottieNetworkFetcher fetcher;
    public final NetworkCache networkCache;

    public NetworkFetcher(NetworkCache networkCache, LottieNetworkFetcher lottieNetworkFetcher) {
        this.networkCache = networkCache;
        this.fetcher = lottieNetworkFetcher;
    }

    public final LottieResult fromInputStream(Context context, String str, InputStream inputStream, String str2, String str3) {
        LottieResult fromZipStreamSync;
        FileExtension fileExtension;
        if (str2 == null) {
            str2 = "application/json";
        }
        boolean contains = str2.contains("application/zip");
        NetworkCache networkCache = this.networkCache;
        if (contains || str2.contains("application/x-zip") || str2.contains("application/x-zip-compressed") || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.debug();
            FileExtension fileExtension2 = FileExtension.ZIP;
            fromZipStreamSync = (str3 == null || networkCache == null) ? LottieCompositionFactory.fromZipStreamSync(context, new ZipInputStream(inputStream), null) : LottieCompositionFactory.fromZipStreamSync(context, new ZipInputStream(new FileInputStream(networkCache.writeTempCacheFile(str, inputStream, fileExtension2))), str);
            fileExtension = fileExtension2;
        } else {
            Logger.debug();
            fileExtension = FileExtension.JSON;
            fromZipStreamSync = (str3 == null || networkCache == null) ? LottieCompositionFactory.fromJsonInputStreamSync(inputStream, null) : LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(networkCache.writeTempCacheFile(str, inputStream, fileExtension).getAbsolutePath()), str);
        }
        if (str3 != null && fromZipStreamSync.value != null && networkCache != null) {
            File file = new File(networkCache.parentDir(), NetworkCache.filenameForUrl(str, fileExtension, true));
            File file2 = new File(file.getAbsolutePath().replace(".temp", ""));
            boolean renameTo = file.renameTo(file2);
            file2.toString();
            Logger.debug();
            if (!renameTo) {
                Logger.warning("Unable to rename cache file " + file.getAbsolutePath() + " to " + file2.getAbsolutePath() + ".");
            }
        }
        return fromZipStreamSync;
    }
}
