package com.android.systemui;

import com.android.internal.logging.MetricsLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ForegroundServicesDialog_Factory implements Provider {
    public final Provider metricsLoggerProvider;

    public ForegroundServicesDialog_Factory(Provider provider) {
        this.metricsLoggerProvider = provider;
    }

    public static ForegroundServicesDialog newInstance(MetricsLogger metricsLogger) {
        return new ForegroundServicesDialog(metricsLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ForegroundServicesDialog((MetricsLogger) this.metricsLoggerProvider.get());
    }
}
