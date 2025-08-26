package com.samsung.systemui.splugins.multistar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

@ProvidesInterface(action = PluginMultiStar.ACTION, version = 10000)
/* loaded from: classes4.dex */
public interface PluginMultiStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_MULTISTAR";
    public static final int VERSION = 10000;

    void init(PluginMultiStarSystemProxy pluginMultiStarSystemProxy);

    boolean onLongPressRecents();
}
