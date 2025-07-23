package com.android.wm.shell.controlpanel.action;

import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GridItems {
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
        ControlPanelAction.Action action6 = ControlPanelAction.Action.QuickSettings;
        ControlPanelAction.Action action7 = ControlPanelAction.Action.ScreenRecord;
        ControlPanelAction.Action action8 = ControlPanelAction.Action.BrightnessControl;
        ControlPanelAction.Action action9 = ControlPanelAction.Action.VolumeControl;
        ControlPanelAction.Action action10 = ControlPanelAction.Action.FlexPanelSettings;
        ACTIVITY_EDIT_BASIC = new ArrayList(Arrays.asList(action6, action7, action8, action9, action10));
        ALL_ACTIONS = new ArrayList(Arrays.asList(action, action2, action3, action4, action5, action6, action7, action8, action9, action10));
    }
}
