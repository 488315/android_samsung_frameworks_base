package com.android.systemui.uithreadmonitor;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SecVendorServices_Factory implements Provider {
    public final Provider binderCallMonitorProvider;
    public final Provider looperSlowLogControllerProvider;
    public final Provider uiThreadMonitorProvider;

    public SecVendorServices_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.binderCallMonitorProvider = provider;
        this.uiThreadMonitorProvider = provider2;
        this.looperSlowLogControllerProvider = provider3;
    }

    public static SecVendorServices newInstance() {
        return new SecVendorServices();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        SecVendorServices secVendorServices = new SecVendorServices();
        secVendorServices.binderCallMonitor = (BinderCallMonitor) this.binderCallMonitorProvider.get();
        secVendorServices.uiThreadMonitor = (UiThreadMonitor) this.uiThreadMonitorProvider.get();
        secVendorServices.looperSlowLogController = (LooperSlowLogController) this.looperSlowLogControllerProvider.get();
        return secVendorServices;
    }
}
