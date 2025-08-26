package com.android.wm.shell.desktopmode;

import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class DesktopModeTransitionTypes {
    public static final /* synthetic */ int $r8$clinit = 0;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DesktopModeTransitionSource.values().length];
            try {
                iArr[DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DesktopModeTransitionSource.APP_FROM_OVERVIEW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DesktopModeTransitionSource.KEYBOARD_SHORTCUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DesktopModeTransitionSource.TASK_DRAG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new DesktopModeTransitionTypes();
    }

    private DesktopModeTransitionTypes() {
    }

    public static final boolean isExitDesktopModeTransition(int i) {
        return Arrays.asList(Integer.valueOf(VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE), Integer.valueOf(VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE), Integer.valueOf(VolteConstants.ErrorCode.CALL_END_CALL_NW_HANDOVER), Integer.valueOf(VolteConstants.ErrorCode.CALL_REJECT_REASON_USR_BUSY_CS_CALL)).contains(Integer.valueOf(i));
    }
}
