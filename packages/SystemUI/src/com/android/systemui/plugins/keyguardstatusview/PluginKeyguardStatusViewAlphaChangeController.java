package com.android.systemui.plugins.keyguardstatusview;

public interface PluginKeyguardStatusViewAlphaChangeController {
    void registerListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener);

    void unregisterAllListener();

    void unregisterListener(PluginKeyguardStatusViewAlphaChangeListener pluginKeyguardStatusViewAlphaChangeListener);
}
