package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
