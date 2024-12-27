package com.android.systemui.facewidget.plugin;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceWidgetWakefulnessLifecycleWrapper {
    public final AnonymousClass1 mObserver;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;

    public FaceWidgetWakefulnessLifecycleWrapper(WakefulnessLifecycle wakefulnessLifecycle) {
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.facewidget.plugin.FaceWidgetWakefulnessLifecycleWrapper.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                PluginKeyguardStatusView pluginKeyguardStatusView = FaceWidgetWakefulnessLifecycleWrapper.this.mPluginKeyguardStatusView;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onFinishedGoingToSleep();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                PluginKeyguardStatusView pluginKeyguardStatusView = FaceWidgetWakefulnessLifecycleWrapper.this.mPluginKeyguardStatusView;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onFinishedWakingUp();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                PluginKeyguardStatusView pluginKeyguardStatusView = FaceWidgetWakefulnessLifecycleWrapper.this.mPluginKeyguardStatusView;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onStartedGoingToSleep();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                PluginKeyguardStatusView pluginKeyguardStatusView = FaceWidgetWakefulnessLifecycleWrapper.this.mPluginKeyguardStatusView;
                if (pluginKeyguardStatusView != null) {
                    pluginKeyguardStatusView.onStartedWakingUp();
                }
            }
        });
    }
}
