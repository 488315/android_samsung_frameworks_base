package com.android.server.biometrics.log;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricContextProvider$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ BiometricContextProvider f$0;

    public /* synthetic */ BiometricContextProvider$$ExternalSyntheticLambda0(
            BiometricContextProvider biometricContextProvider) {
        this.f$0 = biometricContextProvider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BiometricContextProvider biometricContextProvider = this.f$0;
        ((ConcurrentHashMap) biometricContextProvider.mSubscribers)
                .forEach(
                        new BiometricContextProvider$$ExternalSyntheticLambda1(
                                biometricContextProvider));
    }
}
