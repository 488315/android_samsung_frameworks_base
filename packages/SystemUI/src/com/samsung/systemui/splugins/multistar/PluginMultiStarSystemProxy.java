package com.samsung.systemui.splugins.multistar;

import android.graphics.Rect;

/* loaded from: classes4.dex */
public interface PluginMultiStarSystemProxy {
    void exitSplitScreen();

    Rect getStableInsets();

    void setDividerResizeMode(boolean z);

    void setLongLiveApp(String str);

    void toggleSplitScreen();
}
