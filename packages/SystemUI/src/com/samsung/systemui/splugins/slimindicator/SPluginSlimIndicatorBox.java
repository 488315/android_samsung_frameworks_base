package com.samsung.systemui.splugins.slimindicator;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

@ProvidesInterface(action = "com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR", version = 9000)
/* loaded from: classes4.dex */
public interface SPluginSlimIndicatorBox extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR";
    public static final String BLACK_LIST_DB = "";
    public static final int MAJOR_VERSION = 9;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 9000;
    public static final SPluginSlimIndicatorModel mModel = null;

    String getBlackListDB();

    SPluginSlimIndicatorBoxCallback getBoxCallback();

    SPluginSlimIndicatorModel getModel();

    void onPluginConfigurationChanged();

    void onPluginConnected();

    void onPluginDisconnected();
}
