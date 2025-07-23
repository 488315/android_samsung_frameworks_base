package com.android.systemui.uithreadmonitor;

import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BinderCallMonitorImpl_Factory implements Provider {
    public final Provider mLoggerProvider;
    public final Provider settingsHelperProvider;

    public BinderCallMonitorImpl_Factory(Provider provider, Provider provider2) {
        this.settingsHelperProvider = provider;
        this.mLoggerProvider = provider2;
    }

    public static BinderCallMonitorImpl newInstance(SettingsHelper settingsHelper) {
        return new BinderCallMonitorImpl(settingsHelper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        BinderCallMonitorImpl binderCallMonitorImpl = new BinderCallMonitorImpl((SettingsHelper) this.settingsHelperProvider.get());
        binderCallMonitorImpl.mLogger = (SamsungServiceLogger) this.mLoggerProvider.get();
        return binderCallMonitorImpl;
    }
}
