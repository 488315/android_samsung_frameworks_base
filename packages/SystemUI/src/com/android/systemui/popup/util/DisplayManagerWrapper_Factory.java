package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

public final class DisplayManagerWrapper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public DisplayManagerWrapper_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static DisplayManagerWrapper_Factory create(javax.inject.Provider provider) {
        return new DisplayManagerWrapper_Factory(provider);
    }

    public static DisplayManagerWrapper newInstance(Context context) {
        return new DisplayManagerWrapper(context);
    }

    @Override // javax.inject.Provider
    public DisplayManagerWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
