package com.airbnb.lottie;

import android.content.Context;
import android.util.Pair;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.network.DefaultLottieFetchResult;
import com.airbnb.lottie.network.DefaultLottieNetworkFetcher;
import com.airbnb.lottie.network.FileExtension;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.network.NetworkFetcher;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public final /* synthetic */ class LottieCompositionFactory$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ LottieCompositionFactory$$ExternalSyntheticLambda0(Context context, String str, String str2, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
        this.f$1 = str;
        this.f$2 = str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0096  */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v21, types: [com.airbnb.lottie.LottieResult, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r10v22, types: [com.airbnb.lottie.LottieResult] */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r10v30 */
    /* JADX WARN: Type inference failed for: r10v31 */
    /* JADX WARN: Type inference failed for: r10v4 */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object call() throws Throwable {
        LottieComposition lottieComposition;
        Throwable th;
        Exception exc;
        DefaultLottieFetchResult defaultLottieFetchResultFetchSync;
        Object obj;
        NetworkCache networkCache;
        Pair pair;
        File file;
        FileExtension fileExtension;
        switch (this.$r8$classId) {
            case 0:
                Context context = this.f$0;
                String str = this.f$1;
                String str2 = this.f$2;
                Map map = LottieCompositionFactory.taskCache;
                NetworkFetcher networkFetcher = L.networkFetcher;
                if (networkFetcher == null) {
                    synchronized (NetworkFetcher.class) {
                        try {
                            networkFetcher = L.networkFetcher;
                            if (networkFetcher == null) {
                                networkFetcher = new NetworkFetcher(L.networkCache(context), new DefaultLottieNetworkFetcher());
                                L.networkFetcher = networkFetcher;
                            }
                        } finally {
                        }
                    }
                }
                NetworkFetcher networkFetcher2 = networkFetcher;
                ?? lottieResult = 0;
                lottieResult = 0;
                DefaultLottieFetchResult defaultLottieFetchResult = null;
                if (str2 == null || (networkCache = networkFetcher2.networkCache) == null) {
                    lottieComposition = null;
                } else {
                    try {
                        File fileParentDir = networkCache.parentDir();
                        fileExtension = FileExtension.JSON;
                        file = new File(fileParentDir, NetworkCache.filenameForUrl(str, fileExtension, false));
                        if (!file.exists()) {
                            file = new File(networkCache.parentDir(), NetworkCache.filenameForUrl(str, FileExtension.ZIP, false));
                            if (!file.exists()) {
                                file = null;
                            }
                        }
                    } catch (FileNotFoundException unused) {
                    }
                    if (file == null) {
                        pair = null;
                    } else {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        if (file.getAbsolutePath().endsWith(".zip")) {
                            fileExtension = FileExtension.ZIP;
                        }
                        file.getAbsolutePath();
                        Logger.debug();
                        pair = new Pair(fileExtension, fileInputStream);
                    }
                    if (pair != null) {
                        FileExtension fileExtension2 = (FileExtension) pair.first;
                        InputStream inputStream = (InputStream) pair.second;
                        Object obj2 = (fileExtension2 == FileExtension.ZIP ? LottieCompositionFactory.fromZipStreamSync(context, new ZipInputStream(inputStream), str2) : LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str2)).value;
                        if (obj2 != null) {
                            lottieComposition = (LottieComposition) obj2;
                        }
                    }
                }
                if (lottieComposition != null) {
                    lottieResult = new LottieResult(lottieComposition);
                } else {
                    Logger.debug();
                    Logger.debug();
                    try {
                        try {
                            defaultLottieFetchResultFetchSync = ((DefaultLottieNetworkFetcher) networkFetcher2.fetcher).fetchSync(str);
                        } catch (Exception e) {
                            exc = e;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    try {
                        try {
                            if (defaultLottieFetchResultFetchSync.connection.getResponseCode() / 100 == 2) {
                                lottieResult = 1;
                            }
                        } catch (IOException unused2) {
                        }
                        try {
                            if (lottieResult != 0) {
                                LottieResult lottieResultFromInputStream = networkFetcher2.fromInputStream(context, str, defaultLottieFetchResultFetchSync.connection.getInputStream(), defaultLottieFetchResultFetchSync.connection.getContentType(), str2);
                                Object obj3 = lottieResultFromInputStream.value;
                                Logger.debug();
                                defaultLottieFetchResultFetchSync.close();
                                lottieResult = lottieResultFromInputStream;
                            } else {
                                LottieResult lottieResult2 = new LottieResult((Throwable) new IllegalArgumentException(defaultLottieFetchResultFetchSync.error()));
                                defaultLottieFetchResultFetchSync.close();
                                lottieResult = lottieResult2;
                            }
                        } catch (IOException e2) {
                            Logger.warning("LottieFetchResult close failed ", e2);
                        }
                    } catch (Exception e3) {
                        exc = e3;
                        defaultLottieFetchResult = defaultLottieFetchResultFetchSync;
                        LottieResult lottieResult3 = new LottieResult((Throwable) exc);
                        if (defaultLottieFetchResult != null) {
                            try {
                                defaultLottieFetchResult.close();
                            } catch (IOException e4) {
                                Logger.warning("LottieFetchResult close failed ", e4);
                            }
                        }
                        lottieResult = lottieResult3;
                        if (str2 != null) {
                            LottieCompositionCache.INSTANCE.cache.put(str2, (LottieComposition) obj);
                        }
                        return lottieResult;
                    } catch (Throwable th3) {
                        th = th3;
                        defaultLottieFetchResult = defaultLottieFetchResultFetchSync;
                        if (defaultLottieFetchResult == null) {
                            throw th;
                        }
                        try {
                            defaultLottieFetchResult.close();
                            throw th;
                        } catch (IOException e5) {
                            Logger.warning("LottieFetchResult close failed ", e5);
                            throw th;
                        }
                    }
                }
                if (str2 != null && (obj = lottieResult.value) != null) {
                    LottieCompositionCache.INSTANCE.cache.put(str2, (LottieComposition) obj);
                }
                return lottieResult;
            default:
                return LottieCompositionFactory.fromAssetSync(this.f$0, this.f$1, this.f$2);
        }
    }
}
