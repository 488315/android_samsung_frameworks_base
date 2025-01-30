package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.ScreenController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScreenControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider screenControllerProvider;

    public ScreenControlActionInteractor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.screenControllerProvider = provider2;
    }

    public static ScreenControlActionInteractor_Factory create(Provider provider, Provider provider2) {
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
