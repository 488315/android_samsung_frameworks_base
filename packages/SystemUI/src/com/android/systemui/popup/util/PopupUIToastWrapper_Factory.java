package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

public final class PopupUIToastWrapper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public PopupUIToastWrapper_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static PopupUIToastWrapper_Factory create(javax.inject.Provider provider) {
        return new PopupUIToastWrapper_Factory(provider);
    }

    public static PopupUIToastWrapper newInstance(Context context) {
        return new PopupUIToastWrapper(context);
    }

    @Override // javax.inject.Provider
    public PopupUIToastWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
