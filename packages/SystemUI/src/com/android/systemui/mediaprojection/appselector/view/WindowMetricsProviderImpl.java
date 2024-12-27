package com.android.systemui.mediaprojection.appselector.view;

import android.view.WindowManager;

public final class WindowMetricsProviderImpl implements WindowMetricsProvider {
    public final WindowManager windowManager;

    public WindowMetricsProviderImpl(WindowManager windowManager) {
        this.windowManager = windowManager;
    }
}
