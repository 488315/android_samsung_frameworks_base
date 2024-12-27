package com.android.server.location.provider;

import com.android.internal.listeners.ListenerExecutor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationProviderManager$Registration$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ LocationProviderManager.Registration f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LocationProviderManager$Registration$$ExternalSyntheticLambda0(
            LocationProviderManager.Registration registration, int i) {
        this.f$0 = registration;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LocationProviderManager.Registration registration = this.f$0;
        final int i = this.f$1;
        registration.getClass();
        registration.executeOperation(
                new ListenerExecutor
                        .ListenerOperation() { // from class:
                                               // com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda1
                    public final void operate(Object obj) {
                        ((LocationProviderManager.LocationTransport) obj).deliverOnFlushComplete(i);
                    }
                });
    }
}
