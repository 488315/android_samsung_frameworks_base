package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class DisplayManagerWrapper_Factory implements Provider {
    private final Provider contextProvider;

    public DisplayManagerWrapper_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static DisplayManagerWrapper_Factory create(javax.inject.Provider provider) {
        return new DisplayManagerWrapper_Factory(Providers.asDaggerProvider(provider));
    }

    public static DisplayManagerWrapper newInstance(Context context) {
        return new DisplayManagerWrapper(context);
    }

    public static DisplayManagerWrapper_Factory create(Provider provider) {
        return new DisplayManagerWrapper_Factory(provider);
    }

    @Override // javax.inject.Provider
    public DisplayManagerWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
