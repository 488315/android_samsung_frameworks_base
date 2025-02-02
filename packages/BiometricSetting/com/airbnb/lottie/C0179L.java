package com.airbnb.lottie;

import android.content.Context;
import com.airbnb.lottie.network.DefaultLottieNetworkFetcher;
import com.airbnb.lottie.network.LottieNetworkCacheProvider;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.network.NetworkFetcher;
import java.io.File;

/* renamed from: com.airbnb.lottie.L */
/* loaded from: classes.dex */
public final class C0179L {
    private static int depthPastMaxDepth;
    private static volatile NetworkCache networkCache;
    private static volatile NetworkFetcher networkFetcher;

    public static void endSection() {
        int i = depthPastMaxDepth;
        if (i > 0) {
            depthPastMaxDepth = i - 1;
        }
    }

    public static NetworkFetcher networkFetcher(final Context context) {
        NetworkCache networkCache2;
        NetworkFetcher networkFetcher2 = networkFetcher;
        if (networkFetcher2 == null) {
            synchronized (NetworkFetcher.class) {
                networkFetcher2 = networkFetcher;
                if (networkFetcher2 == null) {
                    NetworkCache networkCache3 = networkCache;
                    if (networkCache3 == null) {
                        synchronized (NetworkCache.class) {
                            networkCache2 = networkCache;
                            if (networkCache2 == null) {
                                networkCache2 = new NetworkCache(new LottieNetworkCacheProvider() { // from class: com.airbnb.lottie.L.1
                                    @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
                                    public final File getCacheDir() {
                                        return new File(context.getCacheDir(), "lottie_network_cache");
                                    }
                                });
                                networkCache = networkCache2;
                            }
                        }
                        networkCache3 = networkCache2;
                    }
                    networkFetcher2 = new NetworkFetcher(networkCache3, new DefaultLottieNetworkFetcher());
                    networkFetcher = networkFetcher2;
                }
            }
        }
        return networkFetcher2;
    }
}
