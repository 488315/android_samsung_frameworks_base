package com.android.server.biometrics.log;

import java.util.concurrent.ConcurrentHashMap;

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
