package com.android.systemui.tuner;

import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.util.settings.GlobalSettings;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
