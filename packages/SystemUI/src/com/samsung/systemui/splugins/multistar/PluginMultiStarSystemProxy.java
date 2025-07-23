package com.samsung.systemui.splugins.multistar;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface PluginMultiStarSystemProxy {
    void exitSplitScreen();

    Rect getStableInsets();

    void setDividerResizeMode(boolean z);

    void setLongLiveApp(String str);

    void toggleSplitScreen();
}
