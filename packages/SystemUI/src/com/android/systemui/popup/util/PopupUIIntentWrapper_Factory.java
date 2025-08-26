package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PopupUIIntentWrapper_Factory implements Provider {
    private final Provider contextProvider;

    public PopupUIIntentWrapper_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PopupUIIntentWrapper_Factory create(javax.inject.Provider provider) {
        return new PopupUIIntentWrapper_Factory(Providers.asDaggerProvider(provider));
    }

    public static PopupUIIntentWrapper newInstance(Context context) {
        return new PopupUIIntentWrapper(context);
    }

    public static PopupUIIntentWrapper_Factory create(Provider provider) {
        return new PopupUIIntentWrapper_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PopupUIIntentWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
