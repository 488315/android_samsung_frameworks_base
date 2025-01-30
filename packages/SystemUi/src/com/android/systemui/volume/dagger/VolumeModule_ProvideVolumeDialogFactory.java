package com.android.systemui.volume.dagger;

import android.content.Context;
import android.os.Looper;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.VolumePanelFactory;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeModule_ProvideVolumeDialogFactory implements Provider {
    public final Provider accessibilityManagerWrapperProvider;
    public final Provider activityStarterProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider csdFactoryProvider;
    public final Provider devicePostureControllerProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider mediaOutputDialogFactoryProvider;
    public final Provider volumeDialogControllerProvider;
    public final Provider volumePanelFactoryProvider;

    public VolumeModule_ProvideVolumeDialogFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.volumeDialogControllerProvider = provider2;
        this.accessibilityManagerWrapperProvider = provider3;
        this.deviceProvisionedControllerProvider = provider4;
        this.configurationControllerProvider = provider5;
        this.mediaOutputDialogFactoryProvider = provider6;
        this.volumePanelFactoryProvider = provider7;
        this.activityStarterProvider = provider8;
        this.interactionJankMonitorProvider = provider9;
        this.csdFactoryProvider = provider10;
        this.devicePostureControllerProvider = provider11;
        this.dumpManagerProvider = provider12;
    }

    public static VolumeDialogImpl provideVolumeDialog(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogFactory mediaOutputDialogFactory, VolumePanelFactory volumePanelFactory, ActivityStarter activityStarter, InteractionJankMonitor interactionJankMonitor, CsdWarningDialog.Factory factory, DevicePostureController devicePostureController, DumpManager dumpManager) {
        VolumeDialogImpl volumeDialogImpl = new VolumeDialogImpl(context, volumeDialogController, accessibilityManagerWrapper, deviceProvisionedController, configurationController, mediaOutputDialogFactory, volumePanelFactory, activityStarter, interactionJankMonitor, factory, devicePostureController, Looper.getMainLooper(), dumpManager);
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
        return provideVolumeDialog((Context) this.contextProvider.get(), (VolumeDialogController) this.volumeDialogControllerProvider.get(), (AccessibilityManagerWrapper) this.accessibilityManagerWrapperProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaOutputDialogFactory) this.mediaOutputDialogFactoryProvider.get(), (VolumePanelFactory) this.volumePanelFactoryProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (CsdWarningDialog.Factory) this.csdFactoryProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
