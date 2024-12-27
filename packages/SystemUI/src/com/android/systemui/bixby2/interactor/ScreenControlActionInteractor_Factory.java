package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.ScreenController;
import dagger.internal.Provider;

public final class ScreenControlActionInteractor_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider screenControllerProvider;

    public ScreenControlActionInteractor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.screenControllerProvider = provider2;
    }

    public static ScreenControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ScreenControlActionInteractor_Factory(provider, provider2);
    }

    public static ScreenControlActionInteractor newInstance(Context context, ScreenController screenController) {
        return new ScreenControlActionInteractor(context, screenController);
    }

    @Override // javax.inject.Provider
    public ScreenControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (ScreenController) this.screenControllerProvider.get());
    }
}
