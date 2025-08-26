package com.android.systemui.shade;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadeWindowConfigurationForwarderFactory implements Provider {
    public final Provider shadeConfigurationControllerProvider;

    public ShadeDisplayAwareModule_ProvideShadeWindowConfigurationForwarderFactory(Provider provider) {
        this.shadeConfigurationControllerProvider = provider;
    }

    public static void provideShadeWindowConfigurationForwarder(ConfigurationController configurationController) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        configurationController.getClass();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        ConfigurationController configurationController = (ConfigurationController) this.shadeConfigurationControllerProvider.get();
        provideShadeWindowConfigurationForwarder(configurationController);
        return configurationController;
    }
}
