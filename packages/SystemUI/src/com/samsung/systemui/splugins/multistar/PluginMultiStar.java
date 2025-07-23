package com.samsung.systemui.splugins.multistar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = PluginMultiStar.ACTION, version = 10000)
/* loaded from: classes4.dex */
public interface PluginMultiStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_MULTISTAR";
    public static final int VERSION = 10000;

    void init(PluginMultiStarSystemProxy pluginMultiStarSystemProxy);

    boolean onLongPressRecents();
}
