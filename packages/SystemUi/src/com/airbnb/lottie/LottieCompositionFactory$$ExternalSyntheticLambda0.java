package com.airbnb.lottie;

import android.content.Context;
import android.util.Pair;
import com.airbnb.lottie.model.LottieCompositionCache;
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
import java.util.concurrent.Callable;
import java.util.zip.ZipInputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d3  */
    /* JADX WARN: Type inference failed for: r4v11, types: [int] */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.airbnb.lottie.network.DefaultLottieFetchResult] */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.airbnb.lottie.network.DefaultLottieFetchResult] */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object call() {
        LottieComposition lottieComposition;
        ?? r8;
        LottieResult lottieResult;
        InputStream inputStream;
        Object obj;
        NetworkCache networkCache;
        Pair pair;
        File file;
        FileExtension fileExtension;
        NetworkCache networkCache2;
        switch (this.$r8$classId) {
            case 0:
                Context context = this.f$0;
                String str = this.f$1;
                String str2 = this.f$2;
                NetworkFetcher networkFetcher = C0586L.networkFetcher;
                if (networkFetcher == null) {
                    synchronized (NetworkFetcher.class) {
                        networkFetcher = C0586L.networkFetcher;
                        if (networkFetcher == null) {
                            Context applicationContext = context.getApplicationContext();
                            NetworkCache networkCache3 = C0586L.networkCache;
                            if (networkCache3 == null) {
                                synchronized (NetworkCache.class) {
                                    networkCache2 = C0586L.networkCache;
                                    if (networkCache2 == null) {
                                        networkCache2 = new NetworkCache(new L$$ExternalSyntheticLambda0(applicationContext));
                                        C0586L.networkCache = networkCache2;
                                    }
                                }
                                networkCache3 = networkCache2;
                            }
                            networkFetcher = new NetworkFetcher(networkCache3, new DefaultLottieNetworkFetcher());
                            C0586L.networkFetcher = networkFetcher;
                        }
                    }
                }
                NetworkFetcher networkFetcher2 = networkFetcher;
                boolean z = false;
                InputStream inputStream2 = null;
                ?? r4 = 0;
                InputStream inputStream3 = null;
                if (str2 != null && (networkCache = networkFetcher2.networkCache) != null) {
                    try {
                        File parentDir = networkCache.parentDir();
                        fileExtension = FileExtension.JSON;
                        file = new File(parentDir, NetworkCache.filenameForUrl(str, fileExtension, false));
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
                        if (pair != null) {
                            FileExtension fileExtension2 = (FileExtension) pair.first;
                            InputStream inputStream4 = (InputStream) pair.second;
                            Object obj2 = (fileExtension2 == FileExtension.ZIP ? LottieCompositionFactory.fromZipStreamSync(context, new ZipInputStream(inputStream4), str2) : LottieCompositionFactory.fromJsonInputStreamSync(inputStream4, str2)).value;
                            if (obj2 != null) {
                                lottieComposition = (LottieComposition) obj2;
                                if (lottieComposition == null) {
                                    lottieResult = new LottieResult(lottieComposition);
                                } else {
                                    Logger.debug();
                                    Logger.debug();
                                    try {
                                        try {
                                            r8 = ((DefaultLottieNetworkFetcher) networkFetcher2.fetcher).fetchSync(str);
                                        } catch (Throwable th) {
                                            th = th;
                                            r8 = inputStream2;
                                        }
                                    } catch (Exception e) {
                                        e = e;
                                    }
                                    try {
                                        try {
                                            r4 = r8.connection.getResponseCode() / 100;
                                            if (r4 == 2) {
                                                z = true;
                                            }
                                        } catch (IOException unused2) {
                                        }
                                        if (z) {
                                            InputStream inputStream5 = r8.connection.getInputStream();
                                            lottieResult = networkFetcher2.fromInputStream(context, str, inputStream5, r8.connection.getContentType(), str2);
                                            Object obj3 = lottieResult.value;
                                            Logger.debug();
                                            inputStream = inputStream5;
                                        } else {
                                            lottieResult = new LottieResult((Throwable) new IllegalArgumentException(r8.error()));
                                            inputStream = r4;
                                        }
                                        try {
                                            r8.close();
                                            inputStream2 = inputStream;
                                        } catch (IOException e2) {
                                            Logger.warning("LottieFetchResult close failed ", e2);
                                            inputStream2 = inputStream;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        inputStream3 = r8;
                                        LottieResult lottieResult2 = new LottieResult((Throwable) e);
                                        if (inputStream3 != null) {
                                            try {
                                                inputStream3.close();
                                            } catch (IOException e4) {
                                                Logger.warning("LottieFetchResult close failed ", e4);
                                            }
                                        }
                                        lottieResult = lottieResult2;
                                        inputStream2 = inputStream3;
                                        if (str2 != null) {
                                        }
                                        return lottieResult;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (r8 != null) {
                                            try {
                                                r8.close();
                                            } catch (IOException e5) {
                                                Logger.warning("LottieFetchResult close failed ", e5);
                                            }
                                        }
                                        throw th;
                                    }
                                }
                                if (str2 != null && (obj = lottieResult.value) != null) {
                                    LottieCompositionCache.INSTANCE.cache.put(str2, (LottieComposition) obj);
                                }
                                return lottieResult;
                            }
                        }
                    } else {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        if (file.getAbsolutePath().endsWith(".zip")) {
                            fileExtension = FileExtension.ZIP;
                        }
                        file.getAbsolutePath();
                        Logger.debug();
                        pair = new Pair(fileExtension, fileInputStream);
                        if (pair != null) {
                        }
                    }
                }
                lottieComposition = null;
                if (lottieComposition == null) {
                }
                if (str2 != null) {
                    LottieCompositionCache.INSTANCE.cache.put(str2, (LottieComposition) obj);
                }
                return lottieResult;
            default:
                return LottieCompositionFactory.fromAssetSync(this.f$0, this.f$1, this.f$2);
        }
    }
}
