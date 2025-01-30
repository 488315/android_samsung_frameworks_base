package com.android.systemui.shade;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeModule_Companion_ProvidesBatteryMeterViewControllerFactory implements Provider {
    public final Provider batteryControllerProvider;
    public final Provider batteryMeterViewProvider;
    public final Provider configurationControllerProvider;
    public final Provider contentResolverProvider;
    public final Provider featureFlagsProvider;
    public final Provider indicatorScaleGardenerProvider;
    public final Provider mainHandlerProvider;
    public final Provider settingsHelperProvider;
    public final Provider slimIndicatorViewMediatorProvider;
    public final Provider tunerServiceProvider;
    public final Provider userTrackerProvider;

    public ShadeModule_Companion_ProvidesBatteryMeterViewControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.batteryMeterViewProvider = provider;
        this.userTrackerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.contentResolverProvider = provider6;
        this.featureFlagsProvider = provider7;
        this.batteryControllerProvider = provider8;
        this.settingsHelperProvider = provider9;
        this.slimIndicatorViewMediatorProvider = provider10;
        this.indicatorScaleGardenerProvider = provider11;
    }

    public static BatteryMeterViewController providesBatteryMeterViewController(BatteryMeterView batteryMeterView, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, BatteryController batteryController, SettingsHelper settingsHelper, SlimIndicatorViewMediator slimIndicatorViewMediator, IndicatorScaleGardener indicatorScaleGardener) {
        ShadeModule.Companion.getClass();
        return new BatteryMeterViewController(batteryMeterView, userTracker, configurationController, tunerService, handler, contentResolver, featureFlags, batteryController, settingsHelper, slimIndicatorViewMediator, indicatorScaleGardener);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesBatteryMeterViewController((BatteryMeterView) this.batteryMeterViewProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (Handler) this.mainHandlerProvider.get(), (ContentResolver) this.contentResolverProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (SlimIndicatorViewMediator) this.slimIndicatorViewMediatorProvider.get(), (IndicatorScaleGardener) this.indicatorScaleGardenerProvider.get());
    }
}
