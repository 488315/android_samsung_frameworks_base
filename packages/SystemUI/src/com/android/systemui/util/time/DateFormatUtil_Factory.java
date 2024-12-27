package com.android.systemui.util.time;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DateFormatUtil_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider userTrackerProvider;

    public DateFormatUtil_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static DateFormatUtil_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DateFormatUtil_Factory(provider, provider2);
    }

    public static DateFormatUtil newInstance(Context context, UserTracker userTracker) {
        return new DateFormatUtil(context, userTracker);
    }

    @Override // javax.inject.Provider
    public DateFormatUtil get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
