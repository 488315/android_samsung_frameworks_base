package com.android.systemui.util.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplayHelper_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider displayManagerProvider;
    private final Provider windowManagerProvider;

    public DisplayHelper_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.displayManagerProvider = provider2;
        this.windowManagerProvider = provider3;
    }

    public static DisplayHelper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new DisplayHelper_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static DisplayHelper newInstance(Context context, DisplayManager displayManager, WindowManagerProvider windowManagerProvider) {
        return new DisplayHelper(context, displayManager, windowManagerProvider);
    }

    public static DisplayHelper_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new DisplayHelper_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public DisplayHelper get() {
        return newInstance((Context) this.contextProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (WindowManagerProvider) this.windowManagerProvider.get());
    }
}
