package com.android.wm.shell.sysui;

import android.content.res.Configuration;

/* loaded from: classes3.dex */
public interface ConfigurationChangeListener {
    void onConfigurationChanged(Configuration configuration);

    default void onDensityOrFontScaleChanged$1() {
    }

    default void onThemeChanged() {
    }
}
