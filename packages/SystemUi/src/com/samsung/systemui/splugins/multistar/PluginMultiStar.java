package com.samsung.systemui.splugins.multistar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginMultiStar.ACTION, version = PluginMultiStar.VERSION)
/* loaded from: classes3.dex */
public interface PluginMultiStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_MULTISTAR";
    public static final int VERSION = 7000;

    PluginDockedStackListener getDockedStackListener();

    void init(PluginMultiStarSystemProxy pluginMultiStarSystemProxy);

    boolean onLongPressRecents();
}
