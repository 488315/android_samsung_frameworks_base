package com.android.systemui.facewidget.plugin;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;

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
