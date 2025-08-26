package com.android.systemui.bixby2.interactor;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class ShareViaActionInteractor_Factory implements Provider {
    private final Provider contextProvider;

    public ShareViaActionInteractor_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ShareViaActionInteractor_Factory create(javax.inject.Provider provider) {
        return new ShareViaActionInteractor_Factory(Providers.asDaggerProvider(provider));
    }

    public static ShareViaActionInteractor newInstance(Context context) {
        return new ShareViaActionInteractor(context);
    }

    public static ShareViaActionInteractor_Factory create(Provider provider) {
        return new ShareViaActionInteractor_Factory(provider);
    }

    @Override // javax.inject.Provider
    public ShareViaActionInteractor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
