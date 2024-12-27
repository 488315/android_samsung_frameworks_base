package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
public interface DozeParameters {
    public static final int VERSION = 1;

    boolean shouldControlScreenOff();
}
