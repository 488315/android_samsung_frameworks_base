package com.android.systemui.screenshot.appclips;

import android.content.Context;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class AppClipsCrossProcessHelper_Factory implements Provider {
    public final Provider contextProvider;

    public AppClipsCrossProcessHelper_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static AppClipsCrossProcessHelper newInstance(Context context) {
        return new AppClipsCrossProcessHelper(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppClipsCrossProcessHelper((Context) this.contextProvider.get());
    }
}
