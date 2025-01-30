package com.android.systemui.uithreadmonitor;

import com.android.systemui.log.SamsungServiceLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BinderCallMonitorImpl_Factory implements Provider {
    public final Provider mLoggerProvider;

    public BinderCallMonitorImpl_Factory(Provider provider) {
        this.mLoggerProvider = provider;
    }

    public static BinderCallMonitorImpl newInstance() {
        return new BinderCallMonitorImpl();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        BinderCallMonitorImpl binderCallMonitorImpl = new BinderCallMonitorImpl();
        binderCallMonitorImpl.mLogger = (SamsungServiceLogger) this.mLoggerProvider.get();
        return binderCallMonitorImpl;
    }
}
