package com.airbnb.lottie.network;

import android.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public final class NetworkFetcher {
    private final DefaultLottieNetworkFetcher fetcher;
    private final NetworkCache networkCache;

    public NetworkFetcher(NetworkCache networkCache, DefaultLottieNetworkFetcher defaultLottieNetworkFetcher) {
        this.networkCache = networkCache;
        this.fetcher = defaultLottieNetworkFetcher;
    }

    private LottieResult<LottieComposition> fromInputStream(String str, InputStream inputStream, String str2, String str3) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> fromZipStreamSync;
        if (str2 == null) {
            str2 = "application/json";
        }
        boolean contains = str2.contains("application/zip");
        NetworkCache networkCache = this.networkCache;
        if (contains || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.debug();
            fileExtension = FileExtension.ZIP;
            fromZipStreamSync = str3 == null ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), null) : LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(networkCache.writeTempCacheFile(str, inputStream, fileExtension))), str);
        } else {
            Logger.debug();
            fileExtension = FileExtension.JSON;
            fromZipStreamSync = str3 == null ? LottieCompositionFactory.fromJsonInputStreamSync(inputStream, null) : LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(networkCache.writeTempCacheFile(str, inputStream, fileExtension).getAbsolutePath())), str);
        }
        if (str3 != null && fromZipStreamSync.getValue() != null) {
            networkCache.renameTempFile(str, fileExtension);
        }
        return fromZipStreamSync;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LottieResult<LottieComposition> fetchSync(String str, String str2) {
        LottieComposition lottieComposition;
        LottieResult<LottieComposition> lottieResult;
        Pair<FileExtension, InputStream> fetch;
        AutoCloseable autoCloseable = null;
        if (str2 != null && (fetch = this.networkCache.fetch(str)) != null) {
            FileExtension fileExtension = (FileExtension) fetch.first;
            InputStream inputStream = (InputStream) fetch.second;
            LottieResult<LottieComposition> fromZipStreamSync = fileExtension == FileExtension.ZIP ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), str) : LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
            if (fromZipStreamSync.getValue() != null) {
                lottieComposition = fromZipStreamSync.getValue();
                if (lottieComposition == null) {
                    return new LottieResult<>(lottieComposition);
                }
                Logger.debug();
                Logger.debug();
                try {
                    try {
                        this.fetcher.getClass();
                        DefaultLottieFetchResult fetchSync = DefaultLottieNetworkFetcher.fetchSync(str);
                        if (fetchSync.isSuccessful()) {
                            lottieResult = fromInputStream(str, fetchSync.bodyByteStream(), fetchSync.contentType(), str2);
                            lottieResult.getValue();
                            Logger.debug();
                        } else {
                            lottieResult = new LottieResult<>(new IllegalArgumentException(fetchSync.error()));
                        }
                        try {
                            fetchSync.close();
                            return lottieResult;
                        } catch (IOException e) {
                            Logger.warning("LottieFetchResult close failed ", e);
                            return lottieResult;
                        }
                    } catch (Exception e2) {
                        LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e2);
                        if (0 != 0) {
                            try {
                                autoCloseable.close();
                            } catch (IOException e3) {
                                Logger.warning("LottieFetchResult close failed ", e3);
                            }
                        }
                        return lottieResult2;
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            autoCloseable.close();
                        } catch (IOException e4) {
                            Logger.warning("LottieFetchResult close failed ", e4);
                        }
                    }
                    throw th;
                }
            }
        }
        lottieComposition = null;
        if (lottieComposition == null) {
        }
    }
}
