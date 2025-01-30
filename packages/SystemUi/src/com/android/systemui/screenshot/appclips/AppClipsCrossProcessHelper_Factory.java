package com.android.systemui.screenshot.appclips;

import android.content.Context;
import android.os.UserManager;
import com.android.systemui.settings.DisplayTracker;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppClipsCrossProcessHelper_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider displayTrackerProvider;
    public final Provider userManagerProvider;

    public AppClipsCrossProcessHelper_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.userManagerProvider = provider2;
        this.displayTrackerProvider = provider3;
    }

    public static AppClipsCrossProcessHelper newInstance(Context context, UserManager userManager, DisplayTracker displayTracker) {
        return new AppClipsCrossProcessHelper(context, userManager, displayTracker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppClipsCrossProcessHelper((Context) this.contextProvider.get(), (UserManager) this.userManagerProvider.get(), (DisplayTracker) this.displayTrackerProvider.get());
    }
}
