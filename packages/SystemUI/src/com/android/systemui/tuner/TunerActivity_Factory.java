package com.android.systemui.tuner;

import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TunerActivity_Factory implements Provider {
    public final Provider demoModeControllerProvider;
    public final Provider globalSettingsProvider;
    public final Provider tunerServiceProvider;

    public TunerActivity_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.demoModeControllerProvider = provider;
        this.tunerServiceProvider = provider2;
        this.globalSettingsProvider = provider3;
    }

    public static TunerActivity newInstance(DemoModeController demoModeController, TunerService tunerService, GlobalSettings globalSettings) {
        return new TunerActivity(demoModeController, tunerService, globalSettings);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TunerActivity((DemoModeController) this.demoModeControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (GlobalSettings) this.globalSettingsProvider.get());
    }
}
