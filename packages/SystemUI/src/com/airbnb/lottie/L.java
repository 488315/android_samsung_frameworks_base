package com.airbnb.lottie;

import android.content.Context;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.network.NetworkFetcher;

/* loaded from: classes.dex */
public class L {
    public static volatile NetworkCache networkCache;
    public static volatile NetworkFetcher networkFetcher;

    private L() {
    }

    public static NetworkCache networkCache(Context context) {
        NetworkCache networkCache2;
        Context applicationContext = context.getApplicationContext();
        NetworkCache networkCache3 = networkCache;
        if (networkCache3 != null) {
            return networkCache3;
        }
        synchronized (NetworkCache.class) {
            try {
                networkCache2 = networkCache;
                if (networkCache2 == null) {
                    networkCache2 = new NetworkCache(new L$$ExternalSyntheticLambda0(applicationContext));
                    networkCache = networkCache2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return networkCache2;
    }
}
