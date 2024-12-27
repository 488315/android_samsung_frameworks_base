package com.android.systemui.volume.dagger;

import android.content.Context;
import android.os.Looper;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.domain.interactor.VolumeDialogInteractor;
import com.android.systemui.volume.domain.interactor.VolumePanelNavigationInteractor;
import com.android.systemui.volume.panel.shared.flag.VolumePanelFlag;
import com.android.systemui.volume.ui.navigation.VolumeNavigator;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

public final class VolumeModule_ProvideVolumeDialogFactory implements Provider {
    public final javax.inject.Provider accessibilityManagerWrapperProvider;
    public final javax.inject.Provider configurationControllerProvider;
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider csdFactoryProvider;
    public final javax.inject.Provider devicePostureControllerProvider;
    public final javax.inject.Provider deviceProvisionedControllerProvider;
    public final javax.inject.Provider dumpManagerProvider;
    public final javax.inject.Provider interactionJankMonitorProvider;
    public final javax.inject.Provider interactorProvider;
    public final javax.inject.Provider mediaOutputDialogManagerProvider;
    public final javax.inject.Provider secureSettingsProvider;
    public final javax.inject.Provider systemClockProvider;
    public final javax.inject.Provider vibratorHelperProvider;
    public final javax.inject.Provider volumeDialogControllerProvider;
    public final javax.inject.Provider volumeNavigatorProvider;
    public final javax.inject.Provider volumePanelFlagProvider;
    public final javax.inject.Provider volumePanelNavigationInteractorProvider;

    public VolumeModule_ProvideVolumeDialogFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16, javax.inject.Provider provider17) {
        this.contextProvider = provider;
        this.volumeDialogControllerProvider = provider2;
        this.accessibilityManagerWrapperProvider = provider3;
        this.deviceProvisionedControllerProvider = provider4;
        this.configurationControllerProvider = provider5;
        this.mediaOutputDialogManagerProvider = provider6;
        this.interactionJankMonitorProvider = provider7;
        this.volumePanelNavigationInteractorProvider = provider8;
        this.volumeNavigatorProvider = provider9;
        this.csdFactoryProvider = provider10;
        this.devicePostureControllerProvider = provider11;
        this.volumePanelFlagProvider = provider12;
        this.dumpManagerProvider = provider13;
        this.secureSettingsProvider = provider14;
        this.vibratorHelperProvider = provider15;
        this.systemClockProvider = provider16;
        this.interactorProvider = provider17;
    }

    public static VolumeDialogImpl provideVolumeDialog(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogManager mediaOutputDialogManager, InteractionJankMonitor interactionJankMonitor, VolumePanelNavigationInteractor volumePanelNavigationInteractor, VolumeNavigator volumeNavigator, CsdWarningDialog.Factory factory, DevicePostureController devicePostureController, VolumePanelFlag volumePanelFlag, DumpManager dumpManager, Lazy lazy, VibratorHelper vibratorHelper, SystemClock systemClock, VolumeDialogInteractor volumeDialogInteractor) {
        VolumeDialogImpl volumeDialogImpl = new VolumeDialogImpl(context, volumeDialogController, accessibilityManagerWrapper, deviceProvisionedController, configurationController, mediaOutputDialogManager, interactionJankMonitor, volumePanelNavigationInteractor, volumeNavigator, true, factory, devicePostureController, Looper.getMainLooper(), volumePanelFlag, dumpManager, lazy, vibratorHelper, systemClock, volumeDialogInteractor);
        volumeDialogImpl.mHandler.obtainMessage(5, 1, 0).sendToTarget();
        if (!volumeDialogImpl.mAutomute) {
            volumeDialogImpl.mAutomute = true;
            volumeDialogImpl.mHandler.sendEmptyMessage(4);
        }
        if (volumeDialogImpl.mSilentMode) {
            volumeDialogImpl.mSilentMode = false;
            volumeDialogImpl.mHandler.sendEmptyMessage(4);
        }
        return volumeDialogImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideVolumeDialog((Context) this.contextProvider.get(), (VolumeDialogController) this.volumeDialogControllerProvider.get(), (AccessibilityManagerWrapper) this.accessibilityManagerWrapperProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaOutputDialogManager) this.mediaOutputDialogManagerProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (VolumePanelNavigationInteractor) this.volumePanelNavigationInteractorProvider.get(), (VolumeNavigator) this.volumeNavigatorProvider.get(), (CsdWarningDialog.Factory) this.csdFactoryProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (VolumePanelFlag) this.volumePanelFlagProvider.get(), (DumpManager) this.dumpManagerProvider.get(), DoubleCheck.lazy(this.secureSettingsProvider), (VibratorHelper) this.vibratorHelperProvider.get(), (SystemClock) this.systemClockProvider.get(), (VolumeDialogInteractor) this.interactorProvider.get());
    }
}
