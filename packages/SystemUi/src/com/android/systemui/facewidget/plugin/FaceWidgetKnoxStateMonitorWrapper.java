package com.android.systemui.facewidget.plugin;

import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetKnoxStateMonitorWrapper implements PluginKnoxStateMonitor {
    public final C13841 mKnoxStateCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.facewidget.plugin.FaceWidgetKnoxStateMonitorWrapper$1, com.android.systemui.knox.KnoxStateMonitorCallback] */
    public FaceWidgetKnoxStateMonitorWrapper(KnoxStateMonitor knoxStateMonitor) {
        ?? r0 = new KnoxStateMonitorCallback() { // from class: com.android.systemui.facewidget.plugin.FaceWidgetKnoxStateMonitorWrapper.1
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateLockscreenHiddenItems() {
                PluginKeyguardStatusView pluginKeyguardStatusView = FaceWidgetKnoxStateMonitorWrapper.this.mPluginKeyguardStatusView;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onUpdateLockscreenHiddenItems();
                }
            }
        };
        this.mKnoxStateCallback = r0;
        this.mKnoxStateMonitor = knoxStateMonitor;
        ((KnoxStateMonitorImpl) knoxStateMonitor).registerCallback(r0);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenAllDisabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
            return (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 1023) == 1023;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenClockEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
            return (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 1) == 0;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenDateEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
            return (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 16) == 0;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginKnoxStateMonitor
    public final boolean isLockscreenOwnerInfoEnabled() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).mCustomSdkMonitor;
        if (customSdkMonitor != null) {
            return (customSdkMonitor.mKnoxCustomLockScreenHiddenItems & 32) == 0;
        }
        return false;
    }
}
