package com.android.systemui.plugins.keyguardstatusview;

/* loaded from: classes2.dex */
public interface PluginKeyguardStatusViewAlphaChangeController {
    void registerListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener);

    void unregisterAllListener();

    void unregisterListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener);
}
