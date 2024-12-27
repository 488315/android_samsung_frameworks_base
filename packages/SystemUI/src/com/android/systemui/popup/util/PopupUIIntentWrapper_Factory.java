package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

public final class PopupUIIntentWrapper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public PopupUIIntentWrapper_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static PopupUIIntentWrapper_Factory create(javax.inject.Provider provider) {
        return new PopupUIIntentWrapper_Factory(provider);
    }

    public static PopupUIIntentWrapper newInstance(Context context) {
        return new PopupUIIntentWrapper(context);
    }

    @Override // javax.inject.Provider
    public PopupUIIntentWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
