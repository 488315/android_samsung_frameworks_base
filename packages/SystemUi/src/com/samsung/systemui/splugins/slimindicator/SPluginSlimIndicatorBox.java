package com.samsung.systemui.splugins.slimindicator;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = "com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR", version = 7005)
/* loaded from: classes3.dex */
public interface SPluginSlimIndicatorBox extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR";
    public static final String BLACK_LIST_DB = "";
    public static final int MAJOR_VERSION = 7;
    public static final int MINOR_VERSION = 5;
    public static final int VERSION = 7005;
    public static final SPluginSlimIndicatorModel mModel = null;

    String getBlackListDB();

    SPluginSlimIndicatorBoxCallback getBoxCallback();

    SPluginSlimIndicatorModel getModel();

    void onPluginConfigurationChanged();

    void onPluginConnected();

    void onPluginDisconnected();
}
