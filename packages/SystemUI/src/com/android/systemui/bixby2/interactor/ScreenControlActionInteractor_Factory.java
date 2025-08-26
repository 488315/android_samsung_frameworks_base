package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.ScreenController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class ScreenControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider screenControllerProvider;

    public ScreenControlActionInteractor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.screenControllerProvider = provider2;
    }

    public static ScreenControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ScreenControlActionInteractor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ScreenControlActionInteractor newInstance(Context context, ScreenController screenController) {
        return new ScreenControlActionInteractor(context, screenController);
    }

    public static ScreenControlActionInteractor_Factory create(Provider provider, Provider provider2) {
        return new ScreenControlActionInteractor_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ScreenControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (ScreenController) this.screenControllerProvider.get());
    }
}
