package com.android.systemui.util.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisplayHelper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider displayManagerProvider;

    public DisplayHelper_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.displayManagerProvider = provider2;
    }

    public static DisplayHelper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DisplayHelper_Factory(provider, provider2);
    }

    public static DisplayHelper newInstance(Context context, DisplayManager displayManager) {
        return new DisplayHelper(context, displayManager);
    }

    @Override // javax.inject.Provider
    public DisplayHelper get() {
        return newInstance((Context) this.contextProvider.get(), (DisplayManager) this.displayManagerProvider.get());
    }
}
