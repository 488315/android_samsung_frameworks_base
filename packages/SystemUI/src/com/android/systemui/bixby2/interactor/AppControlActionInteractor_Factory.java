package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.AppController;
import com.android.systemui.bixby2.controller.MWBixbyController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class AppControlActionInteractor_Factory implements Provider {
    private final Provider appControllerProvider;
    private final Provider contextProvider;
    private final Provider mwBixbyControllerProvider;

    public AppControlActionInteractor_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.appControllerProvider = provider2;
        this.mwBixbyControllerProvider = provider3;
    }

    public static AppControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new AppControlActionInteractor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static AppControlActionInteractor newInstance(Context context, AppController appController, MWBixbyController mWBixbyController) {
        return new AppControlActionInteractor(context, appController, mWBixbyController);
    }

    public static AppControlActionInteractor_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new AppControlActionInteractor_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public AppControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (AppController) this.appControllerProvider.get(), (MWBixbyController) this.mwBixbyControllerProvider.get());
    }
}
