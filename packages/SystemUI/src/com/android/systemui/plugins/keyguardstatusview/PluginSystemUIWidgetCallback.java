package com.android.systemui.plugins.keyguardstatusview;

import com.android.systemui.plugins.annotations.SupportVersionChecker;

@SupportVersionChecker
public interface PluginSystemUIWidgetCallback {
    void updateStyle(int i);

    void updateStyle(int i, Object obj);
}
