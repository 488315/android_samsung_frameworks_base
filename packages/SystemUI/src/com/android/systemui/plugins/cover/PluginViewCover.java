package com.android.systemui.plugins.cover;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = PluginViewCover.ACTION, version = 1)
public interface PluginViewCover extends PluginCover {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_VIEW_COVER";
    public static final int VERSION = 1;
}
