package com.android.wm.shell.controlpanel.action;

import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class MenuActionType {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.controlpanel.action.MenuActionType$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action;

        static {
            int[] iArr = new int[ControlPanelAction.Action.values().length];
            $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action = iArr;
            try {
                iArr[ControlPanelAction.Action.ScreenCapture.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.QuickPanel.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.SplitScreen.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.FlexPanelSettings.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.TouchPad.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.QuickSettings.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[ControlPanelAction.Action.ScreenRecord.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public abstract void doControlAction(String str, FlexPanelActivity flexPanelActivity);
}
