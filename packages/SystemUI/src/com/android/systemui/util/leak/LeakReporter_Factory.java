package com.android.systemui.util.leak;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class LeakReporter_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider leakDetectorProvider;
    private final Provider leakReportEmailProvider;
    private final Provider userTrackerProvider;

    public LeakReporter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.leakDetectorProvider = provider3;
        this.leakReportEmailProvider = provider4;
    }

    public static LeakReporter_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new LeakReporter_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static LeakReporter newInstance(Context context, UserTracker userTracker, LeakDetector leakDetector, String str) {
        return new LeakReporter(context, userTracker, leakDetector, str);
    }

    public static LeakReporter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new LeakReporter_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public LeakReporter get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (LeakDetector) this.leakDetectorProvider.get(), (String) this.leakReportEmailProvider.get());
    }
}
