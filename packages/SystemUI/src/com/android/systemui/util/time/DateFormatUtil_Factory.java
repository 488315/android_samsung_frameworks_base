package com.android.systemui.util.time;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class DateFormatUtil_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider userTrackerProvider;

    public DateFormatUtil_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static DateFormatUtil_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DateFormatUtil_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static DateFormatUtil newInstance(Context context, UserTracker userTracker) {
        return new DateFormatUtil(context, userTracker);
    }

    public static DateFormatUtil_Factory create(Provider provider, Provider provider2) {
        return new DateFormatUtil_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public DateFormatUtil get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
