package com.android.systemui.util.leak;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LeakReporter_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider leakDetectorProvider;
    private final javax.inject.Provider leakReportEmailProvider;
    private final javax.inject.Provider userTrackerProvider;

    public LeakReporter_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.leakDetectorProvider = provider3;
        this.leakReportEmailProvider = provider4;
    }

    public static LeakReporter_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new LeakReporter_Factory(provider, provider2, provider3, provider4);
    }

    public static LeakReporter newInstance(Context context, UserTracker userTracker, LeakDetector leakDetector, String str) {
        return new LeakReporter(context, userTracker, leakDetector, str);
    }

    @Override // javax.inject.Provider
    public LeakReporter get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (LeakDetector) this.leakDetectorProvider.get(), (String) this.leakReportEmailProvider.get());
    }
}
