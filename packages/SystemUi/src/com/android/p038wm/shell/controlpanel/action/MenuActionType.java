package com.android.p038wm.shell.controlpanel.action;

import com.android.p038wm.shell.controlpanel.GridUIManager;
import com.android.p038wm.shell.controlpanel.action.ControlPanelAction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class MenuActionType {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.action.MenuActionType$1 */
    public abstract /* synthetic */ class AbstractC39081 {

        /* renamed from: $SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action */
        public static final /* synthetic */ int[] f440xc89be842;

        static {
            int[] iArr = new int[ControlPanelAction.Action.values().length];
            f440xc89be842 = iArr;
            try {
                iArr[ControlPanelAction.Action.ScreenCapture.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f440xc89be842[ControlPanelAction.Action.QuickPanel.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f440xc89be842[ControlPanelAction.Action.SplitScreen.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f440xc89be842[ControlPanelAction.Action.FlexPanelSettings.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f440xc89be842[ControlPanelAction.Action.TouchPad.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public abstract void doControlAction(String str, GridUIManager gridUIManager);
}
