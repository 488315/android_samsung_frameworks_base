package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.AppController;
import com.android.systemui.bixby2.controller.MWBixbyController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AppControlActionInteractor_Factory implements Provider {
    private final javax.inject.Provider appControllerProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider mwBixbyControllerProvider;

    public AppControlActionInteractor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.appControllerProvider = provider2;
        this.mwBixbyControllerProvider = provider3;
    }

    public static AppControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new AppControlActionInteractor_Factory(provider, provider2, provider3);
    }

    public static AppControlActionInteractor newInstance(Context context, AppController appController, MWBixbyController mWBixbyController) {
        return new AppControlActionInteractor(context, appController, mWBixbyController);
    }

    @Override // javax.inject.Provider
    public AppControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (AppController) this.appControllerProvider.get(), (MWBixbyController) this.mwBixbyControllerProvider.get());
    }
}
