package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
