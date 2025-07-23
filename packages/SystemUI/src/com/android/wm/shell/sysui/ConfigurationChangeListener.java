package com.android.wm.shell.sysui;

import android.content.res.Configuration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ConfigurationChangeListener {
    void onConfigurationChanged(Configuration configuration);

    default void onDensityOrFontScaleChanged$1() {
    }

    default void onThemeChanged() {
    }
}
