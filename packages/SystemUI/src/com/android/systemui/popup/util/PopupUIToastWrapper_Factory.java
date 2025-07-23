package com.android.systemui.popup.util;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PopupUIToastWrapper_Factory implements Provider {
    private final Provider contextProvider;

    public PopupUIToastWrapper_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PopupUIToastWrapper_Factory create(javax.inject.Provider provider) {
        return new PopupUIToastWrapper_Factory(Providers.asDaggerProvider(provider));
    }

    public static PopupUIToastWrapper newInstance(Context context) {
        return new PopupUIToastWrapper(context);
    }

    public static PopupUIToastWrapper_Factory create(Provider provider) {
        return new PopupUIToastWrapper_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PopupUIToastWrapper get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
