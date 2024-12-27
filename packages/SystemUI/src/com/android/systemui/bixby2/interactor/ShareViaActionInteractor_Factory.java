package com.android.systemui.bixby2.interactor;

import android.content.Context;
import dagger.internal.Provider;

public final class ShareViaActionInteractor_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public ShareViaActionInteractor_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static ShareViaActionInteractor_Factory create(javax.inject.Provider provider) {
        return new ShareViaActionInteractor_Factory(provider);
    }

    public static ShareViaActionInteractor newInstance(Context context) {
        return new ShareViaActionInteractor(context);
    }

    @Override // javax.inject.Provider
    public ShareViaActionInteractor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
