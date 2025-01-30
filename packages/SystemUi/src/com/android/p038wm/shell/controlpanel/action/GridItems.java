package com.android.p038wm.shell.controlpanel.action;

import com.android.p038wm.shell.controlpanel.action.ControlPanelAction;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GridItems {
    public static final ArrayList ACTIVITY_BASIC;
    public static final ArrayList ACTIVITY_EDIT_BASIC;
    public static final ArrayList ALL_ACTIONS;

    static {
        ControlPanelAction.Action action = ControlPanelAction.Action.SplitScreen;
        ControlPanelAction.Action action2 = ControlPanelAction.Action.QuickPanel;
        ControlPanelAction.Action action3 = ControlPanelAction.Action.ScreenCapture;
        ControlPanelAction.Action action4 = ControlPanelAction.Action.TouchPad;
        ControlPanelAction.Action action5 = ControlPanelAction.Action.EditPanel;
        ACTIVITY_BASIC = new ArrayList(Arrays.asList(action, action2, action3, action4, action5));
        ControlPanelAction.Action action6 = ControlPanelAction.Action.BrightnessControl;
        ControlPanelAction.Action action7 = ControlPanelAction.Action.VolumeControl;
        ControlPanelAction.Action action8 = ControlPanelAction.Action.FlexPanelSettings;
        ACTIVITY_EDIT_BASIC = new ArrayList(Arrays.asList(action6, action7, action8));
        ALL_ACTIONS = new ArrayList(Arrays.asList(action, action2, action3, action4, action5, action6, action7, action8));
    }
}
