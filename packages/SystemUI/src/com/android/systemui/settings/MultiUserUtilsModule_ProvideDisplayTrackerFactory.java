package com.android.systemui.settings;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class MultiUserUtilsModule_ProvideDisplayTrackerFactory implements Provider {
    public final Provider displayManagerProvider;
    public final Provider handlerProvider;

    public MultiUserUtilsModule_ProvideDisplayTrackerFactory(Provider provider, Provider provider2) {
        this.displayManagerProvider = provider;
        this.handlerProvider = provider2;
    }

    public static DisplayTrackerImpl provideDisplayTracker(DisplayManager displayManager, Handler handler) {
        return new DisplayTrackerImpl(displayManager, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DisplayTrackerImpl((DisplayManager) this.displayManagerProvider.get(), (Handler) this.handlerProvider.get());
    }
}
