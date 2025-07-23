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
import com.google.android.msdl.domain.MSDLPlayer;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeModule_ProvideVolumeDialogFactory implements Provider {
    public final Provider accessibilityManagerWrapperProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider csdFactoryProvider;
    public final Provider devicePostureControllerProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider interactorProvider;
    public final Provider mediaOutputDialogManagerProvider;
    public final Provider msdlPlayerProvider;
    public final Provider secureSettingsProvider;
    public final Provider systemClockProvider;
    public final Provider vibratorHelperProvider;
    public final Provider volumeDialogControllerProvider;
    public final Provider volumeDialogProvider;
    public final Provider volumeNavigatorProvider;
    public final Provider volumePanelFlagProvider;
    public final Provider volumePanelNavigationInteractorProvider;

    public VolumeModule_ProvideVolumeDialogFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.volumeDialogProvider = provider;
        this.contextProvider = provider2;
        this.volumeDialogControllerProvider = provider3;
        this.accessibilityManagerWrapperProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.mediaOutputDialogManagerProvider = provider7;
        this.interactionJankMonitorProvider = provider8;
        this.volumePanelNavigationInteractorProvider = provider9;
        this.volumeNavigatorProvider = provider10;
        this.csdFactoryProvider = provider11;
        this.devicePostureControllerProvider = provider12;
        this.volumePanelFlagProvider = provider13;
        this.dumpManagerProvider = provider14;
        this.secureSettingsProvider = provider15;
        this.vibratorHelperProvider = provider16;
        this.msdlPlayerProvider = provider17;
        this.systemClockProvider = provider18;
        this.interactorProvider = provider19;
    }

    public static VolumeDialogImpl provideVolumeDialog(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogManager mediaOutputDialogManager, InteractionJankMonitor interactionJankMonitor, VolumePanelNavigationInteractor volumePanelNavigationInteractor, VolumeNavigator volumeNavigator, CsdWarningDialog.Factory factory, DevicePostureController devicePostureController, VolumePanelFlag volumePanelFlag, DumpManager dumpManager, Lazy lazy, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, VolumeDialogInteractor volumeDialogInteractor) {
        VolumeDialogImpl volumeDialogImpl = new VolumeDialogImpl(context, volumeDialogController, accessibilityManagerWrapper, deviceProvisionedController, configurationController, mediaOutputDialogManager, interactionJankMonitor, volumePanelNavigationInteractor, volumeNavigator, true, factory, devicePostureController, Looper.getMainLooper(), volumePanelFlag, dumpManager, lazy, vibratorHelper, mSDLPlayer, systemClock, volumeDialogInteractor);
        volumeDialogImpl.mHandler.obtainMessage(5, 1, 0).sendToTarget();
        if (!volumeDialogImpl.mAutomute) {
            volumeDialogImpl.mAutomute = true;
            volumeDialogImpl.mHandler.sendEmptyMessage(4);
        }
        if (!volumeDialogImpl.mSilentMode) {
            return volumeDialogImpl;
        }
        volumeDialogImpl.mSilentMode = false;
        volumeDialogImpl.mHandler.sendEmptyMessage(4);
        return volumeDialogImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DoubleCheck.lazy(this.volumeDialogProvider);
        return provideVolumeDialog((Context) this.contextProvider.get(), (VolumeDialogController) this.volumeDialogControllerProvider.get(), (AccessibilityManagerWrapper) this.accessibilityManagerWrapperProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaOutputDialogManager) this.mediaOutputDialogManagerProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (VolumePanelNavigationInteractor) this.volumePanelNavigationInteractorProvider.get(), (VolumeNavigator) this.volumeNavigatorProvider.get(), (CsdWarningDialog.Factory) this.csdFactoryProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (VolumePanelFlag) this.volumePanelFlagProvider.get(), (DumpManager) this.dumpManagerProvider.get(), DoubleCheck.lazy(this.secureSettingsProvider), (VibratorHelper) this.vibratorHelperProvider.get(), (MSDLPlayer) this.msdlPlayerProvider.get(), (SystemClock) this.systemClockProvider.get(), (VolumeDialogInteractor) this.interactorProvider.get());
    }
}
