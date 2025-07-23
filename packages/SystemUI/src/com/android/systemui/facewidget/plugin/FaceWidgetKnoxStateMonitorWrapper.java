package com.android.systemui.facewidget.plugin;

import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetKnoxStateMonitorWrapper extends KnoxStateMonitorCallback implements PluginKnoxStateMonitor {
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

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isStatusBarHidden() {
        return ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).isStatusBarHidden();
    }

    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
    public final void onUpdateLockscreenHiddenItems() {
        PluginKeyguardStatusView pluginKeyguardStatusView = this.mPluginKeyguardStatusView;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.onUpdateLockscreenHiddenItems();
        }
    }
}
