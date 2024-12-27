package com.android.systemui.facewidget.plugin;

import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceWidgetKnoxStateMonitorWrapper extends KnoxStateMonitorCallback implements PluginKnoxStateMonitor {
    public final KnoxStateMonitor mKnoxStateMonitor;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;

    public FaceWidgetKnoxStateMonitorWrapper(KnoxStateMonitor knoxStateMonitor) {
        this.mKnoxStateMonitor = knoxStateMonitor;
        ((KnoxStateMonitorImpl) knoxStateMonitor).registerCallback(this);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenAllDisabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 1023) == 1023;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenBatteryInfoEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 2) == 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenClockEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 1) == 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenDateEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 16) == 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenOwnerInfoEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        return customSdkMonitor != null && (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 32) == 0;
    }

    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
    public final void onUpdateLockscreenHiddenItems() {
        PluginKeyguardStatusView pluginKeyguardStatusView = this.mPluginKeyguardStatusView;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.onUpdateLockscreenHiddenItems();
        }
    }
}
