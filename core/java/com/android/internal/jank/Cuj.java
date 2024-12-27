package com.android.internal.jank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class Cuj {
    public static final int CUJ_BACK_PANEL_ARROW = 88;
    public static final int CUJ_BIOMETRIC_PROMPT_TRANSITION = 56;
    public static final int CUJ_IME_INSETS_HIDE_ANIMATION = 81;
    public static final int CUJ_IME_INSETS_SHOW_ANIMATION = 80;
    public static final int CUJ_LAUNCHER_ALL_APPS_SCROLL = 26;
    public static final int CUJ_LAUNCHER_ALL_APPS_SEARCH_BACK = 95;
    public static final int CUJ_LAUNCHER_APP_CLOSE_TO_HOME = 9;
    public static final int CUJ_LAUNCHER_APP_CLOSE_TO_HOME_FALLBACK = 78;
    public static final int CUJ_LAUNCHER_APP_CLOSE_TO_PIP = 10;
    public static final int CUJ_LAUNCHER_APP_LAUNCH_FROM_ICON = 8;
    public static final int CUJ_LAUNCHER_APP_LAUNCH_FROM_RECENTS = 7;
    public static final int CUJ_LAUNCHER_APP_LAUNCH_FROM_WIDGET = 27;
    public static final int CUJ_LAUNCHER_APP_SWIPE_TO_RECENTS = 66;
    public static final int CUJ_LAUNCHER_CLOSE_ALL_APPS_BACK = 89;
    public static final int CUJ_LAUNCHER_CLOSE_ALL_APPS_SWIPE = 67;
    public static final int CUJ_LAUNCHER_CLOSE_ALL_APPS_TO_HOME = 68;
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_TASKBAR = 92;
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_WORKSPACE = 91;
    public static final int CUJ_LAUNCHER_OPEN_ALL_APPS = 25;
    public static final int CUJ_LAUNCHER_OPEN_SEARCH_RESULT = 71;
    public static final int CUJ_LAUNCHER_PRIVATE_SPACE_LOCK = 102;
    public static final int CUJ_LAUNCHER_PRIVATE_SPACE_UNLOCK = 103;
    public static final int CUJ_LAUNCHER_QUICK_SWITCH = 11;
    public static final int CUJ_LAUNCHER_SAVE_APP_PAIR = 93;
    public static final int CUJ_LAUNCHER_SEARCH_QSB_WEB_SEARCH = 90;
    public static final int CUJ_LAUNCHER_TASKBAR_ALL_APPS_CLOSE_BACK = 96;
    public static final int CUJ_LAUNCHER_TASKBAR_ALL_APPS_SEARCH_BACK = 97;
    public static final int CUJ_LAUNCHER_UNFOLD_ANIM = 83;
    public static final int CUJ_LAUNCHER_UNLOCK_ENTRANCE_ANIMATION = 63;
    public static final int CUJ_LAUNCHER_WIDGET_BOTTOM_SHEET_CLOSE_BACK = 100;
    public static final int CUJ_LAUNCHER_WIDGET_EDU_SHEET_CLOSE_BACK = 101;
    public static final int CUJ_LAUNCHER_WIDGET_PICKER_CLOSE_BACK = 98;
    public static final int CUJ_LAUNCHER_WIDGET_PICKER_SEARCH_BACK = 99;
    public static final int CUJ_LOCKSCREEN_CLOCK_MOVE_ANIMATION = 70;
    public static final int CUJ_LOCKSCREEN_LAUNCH_CAMERA = 51;
    public static final int CUJ_LOCKSCREEN_OCCLUSION = 64;
    public static final int CUJ_LOCKSCREEN_PASSWORD_APPEAR = 17;
    public static final int CUJ_LOCKSCREEN_PASSWORD_DISAPPEAR = 20;
    public static final int CUJ_LOCKSCREEN_PATTERN_APPEAR = 18;
    public static final int CUJ_LOCKSCREEN_PATTERN_DISAPPEAR = 21;
    public static final int CUJ_LOCKSCREEN_PIN_APPEAR = 19;
    public static final int CUJ_LOCKSCREEN_PIN_DISAPPEAR = 22;
    public static final int CUJ_LOCKSCREEN_TRANSITION_FROM_AOD = 23;
    public static final int CUJ_LOCKSCREEN_TRANSITION_TO_AOD = 24;
    public static final int CUJ_LOCKSCREEN_UNLOCK_ANIMATION = 29;
    public static final int CUJ_NOTIFICATION_ADD = 14;
    public static final int CUJ_NOTIFICATION_APP_START = 16;
    public static final int CUJ_NOTIFICATION_HEADS_UP_APPEAR = 12;
    public static final int CUJ_NOTIFICATION_HEADS_UP_DISAPPEAR = 13;
    public static final int CUJ_NOTIFICATION_REMOVE = 15;
    public static final int CUJ_NOTIFICATION_SHADE_EXPAND_COLLAPSE = 0;
    public static final int CUJ_NOTIFICATION_SHADE_QS_EXPAND_COLLAPSE = 5;
    public static final int CUJ_NOTIFICATION_SHADE_QS_SCROLL_SWIPE = 6;
    public static final int CUJ_NOTIFICATION_SHADE_ROW_EXPAND = 3;
    public static final int CUJ_NOTIFICATION_SHADE_ROW_SWIPE = 4;
    public static final int CUJ_NOTIFICATION_SHADE_SCROLL_FLING = 2;
    public static final int CUJ_ONE_HANDED_ENTER_TRANSITION = 42;
    public static final int CUJ_ONE_HANDED_EXIT_TRANSITION = 43;
    public static final int CUJ_PIP_TRANSITION = 35;
    public static final int CUJ_PREDICTIVE_BACK_CROSS_ACTIVITY = 84;
    public static final int CUJ_PREDICTIVE_BACK_CROSS_TASK = 85;
    public static final int CUJ_PREDICTIVE_BACK_HOME = 86;
    public static final int CUJ_RECENTS_SCROLLING = 65;
    public static final int CUJ_SCREEN_OFF = 40;
    public static final int CUJ_SCREEN_OFF_SHOW_AOD = 41;
    public static final int CUJ_SETTINGS_PAGE_SCROLL = 28;
    public static final int CUJ_SETTINGS_SLIDER = 53;
    public static final int CUJ_SETTINGS_TOGGLE = 57;
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_HISTORY_BUTTON = 30;
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_MEDIA_PLAYER = 31;
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_QS_TILE = 32;
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_SETTINGS_BUTTON = 33;
    public static final int CUJ_SHADE_CLEAR_ALL = 62;
    public static final int CUJ_SHADE_DIALOG_OPEN = 58;
    public static final int CUJ_SPLASHSCREEN_AVD = 38;
    public static final int CUJ_SPLASHSCREEN_EXIT_ANIM = 39;
    public static final int CUJ_SPLIT_SCREEN_DOUBLE_TAP_DIVIDER = 82;
    public static final int CUJ_SPLIT_SCREEN_ENTER = 49;
    public static final int CUJ_SPLIT_SCREEN_EXIT = 50;
    public static final int CUJ_SPLIT_SCREEN_RESIZE = 52;
    public static final int CUJ_STATUS_BAR_APP_LAUNCH_FROM_CALL_CHIP = 34;
    public static final int CUJ_SUW_LOADING_SCREEN_FOR_STATUS = 48;
    public static final int CUJ_SUW_LOADING_TO_NEXT_FLOW = 47;
    public static final int CUJ_SUW_LOADING_TO_SHOW_INFO_WITH_ACTIONS = 45;
    public static final int CUJ_SUW_SHOW_FUNCTION_SCREEN_WITH_ACTIONS = 46;
    public static final int CUJ_TAKE_SCREENSHOT = 54;
    public static final int CUJ_TASKBAR_COLLAPSE = 61;
    public static final int CUJ_TASKBAR_EXPAND = 60;
    private static final int[] CUJ_TO_STATSD_INTERACTION_TYPE = new int[104];
    public static final int CUJ_UNFOLD_ANIM = 44;
    public static final int CUJ_USER_DIALOG_OPEN = 59;
    public static final int CUJ_USER_SWITCH = 37;
    public static final int CUJ_VOLUME_CONTROL = 55;
    public static final int CUJ_WALLPAPER_TRANSITION = 36;
    static final int LAST_CUJ = 103;
    static final int LAST_SEC_CUJ = 10004;
    public static final int MAX_LENGTH_OF_CUJ_NAME = 82;
    private static final int NO_STATSD_LOGGING = -1;
    public static final int SEC_CUJ_EDGE_OPEN_PANEL = 10002;
    public static final int SEC_CUJ_NOTIFICATION_SHADE_QS_SHOW_DETAIL = 10001;
    public static final int SEC_CUJ_RESERVED3 = 10003;
    public static final int SEC_CUJ_RESERVED4 = 10004;
    private static final int[] SEC_CUJ_TO_STATSD_INTERACTION_TYPE;
    public static final int SEC_CUJ_UNKNOWN = 10000;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CujType {}

    static {
        Arrays.fill(CUJ_TO_STATSD_INTERACTION_TYPE, -1);
        CUJ_TO_STATSD_INTERACTION_TYPE[0] = 1;
        CUJ_TO_STATSD_INTERACTION_TYPE[2] = 3;
        CUJ_TO_STATSD_INTERACTION_TYPE[3] = 4;
        CUJ_TO_STATSD_INTERACTION_TYPE[4] = 5;
        CUJ_TO_STATSD_INTERACTION_TYPE[5] = 6;
        CUJ_TO_STATSD_INTERACTION_TYPE[6] = 7;
        CUJ_TO_STATSD_INTERACTION_TYPE[7] = 8;
        CUJ_TO_STATSD_INTERACTION_TYPE[8] = 9;
        CUJ_TO_STATSD_INTERACTION_TYPE[9] = 10;
        CUJ_TO_STATSD_INTERACTION_TYPE[10] = 11;
        CUJ_TO_STATSD_INTERACTION_TYPE[11] = 12;
        CUJ_TO_STATSD_INTERACTION_TYPE[12] = 13;
        CUJ_TO_STATSD_INTERACTION_TYPE[13] = 14;
        CUJ_TO_STATSD_INTERACTION_TYPE[14] = 15;
        CUJ_TO_STATSD_INTERACTION_TYPE[15] = 16;
        CUJ_TO_STATSD_INTERACTION_TYPE[16] = 17;
        CUJ_TO_STATSD_INTERACTION_TYPE[17] = 18;
        CUJ_TO_STATSD_INTERACTION_TYPE[18] = 19;
        CUJ_TO_STATSD_INTERACTION_TYPE[19] = 20;
        CUJ_TO_STATSD_INTERACTION_TYPE[20] = 21;
        CUJ_TO_STATSD_INTERACTION_TYPE[21] = 22;
        CUJ_TO_STATSD_INTERACTION_TYPE[22] = 23;
        CUJ_TO_STATSD_INTERACTION_TYPE[23] = 24;
        CUJ_TO_STATSD_INTERACTION_TYPE[24] = 25;
        CUJ_TO_STATSD_INTERACTION_TYPE[25] = 26;
        CUJ_TO_STATSD_INTERACTION_TYPE[26] = 27;
        CUJ_TO_STATSD_INTERACTION_TYPE[27] = 28;
        CUJ_TO_STATSD_INTERACTION_TYPE[28] = 29;
        CUJ_TO_STATSD_INTERACTION_TYPE[29] = 30;
        CUJ_TO_STATSD_INTERACTION_TYPE[30] = 31;
        CUJ_TO_STATSD_INTERACTION_TYPE[31] = 32;
        CUJ_TO_STATSD_INTERACTION_TYPE[32] = 33;
        CUJ_TO_STATSD_INTERACTION_TYPE[33] = 34;
        CUJ_TO_STATSD_INTERACTION_TYPE[34] = 35;
        CUJ_TO_STATSD_INTERACTION_TYPE[35] = 36;
        CUJ_TO_STATSD_INTERACTION_TYPE[36] = 37;
        CUJ_TO_STATSD_INTERACTION_TYPE[37] = 38;
        CUJ_TO_STATSD_INTERACTION_TYPE[38] = 39;
        CUJ_TO_STATSD_INTERACTION_TYPE[39] = 40;
        CUJ_TO_STATSD_INTERACTION_TYPE[40] = 41;
        CUJ_TO_STATSD_INTERACTION_TYPE[41] = 42;
        CUJ_TO_STATSD_INTERACTION_TYPE[42] = 43;
        CUJ_TO_STATSD_INTERACTION_TYPE[43] = 44;
        CUJ_TO_STATSD_INTERACTION_TYPE[44] = 45;
        CUJ_TO_STATSD_INTERACTION_TYPE[45] = 46;
        CUJ_TO_STATSD_INTERACTION_TYPE[46] = 47;
        CUJ_TO_STATSD_INTERACTION_TYPE[47] = 48;
        CUJ_TO_STATSD_INTERACTION_TYPE[48] = 49;
        CUJ_TO_STATSD_INTERACTION_TYPE[49] = 50;
        CUJ_TO_STATSD_INTERACTION_TYPE[50] = 51;
        CUJ_TO_STATSD_INTERACTION_TYPE[51] = 52;
        CUJ_TO_STATSD_INTERACTION_TYPE[52] = 53;
        CUJ_TO_STATSD_INTERACTION_TYPE[53] = 54;
        CUJ_TO_STATSD_INTERACTION_TYPE[54] = 55;
        CUJ_TO_STATSD_INTERACTION_TYPE[55] = 56;
        CUJ_TO_STATSD_INTERACTION_TYPE[56] = 57;
        CUJ_TO_STATSD_INTERACTION_TYPE[57] = 58;
        CUJ_TO_STATSD_INTERACTION_TYPE[58] = 59;
        CUJ_TO_STATSD_INTERACTION_TYPE[59] = 60;
        CUJ_TO_STATSD_INTERACTION_TYPE[60] = 61;
        CUJ_TO_STATSD_INTERACTION_TYPE[61] = 62;
        CUJ_TO_STATSD_INTERACTION_TYPE[62] = 63;
        CUJ_TO_STATSD_INTERACTION_TYPE[63] = 64;
        CUJ_TO_STATSD_INTERACTION_TYPE[64] = 65;
        CUJ_TO_STATSD_INTERACTION_TYPE[65] = 66;
        CUJ_TO_STATSD_INTERACTION_TYPE[66] = 67;
        CUJ_TO_STATSD_INTERACTION_TYPE[67] = 68;
        CUJ_TO_STATSD_INTERACTION_TYPE[68] = 69;
        CUJ_TO_STATSD_INTERACTION_TYPE[70] = 71;
        CUJ_TO_STATSD_INTERACTION_TYPE[71] = 72;
        CUJ_TO_STATSD_INTERACTION_TYPE[78] = 79;
        CUJ_TO_STATSD_INTERACTION_TYPE[80] = 81;
        CUJ_TO_STATSD_INTERACTION_TYPE[81] = 82;
        CUJ_TO_STATSD_INTERACTION_TYPE[82] = 83;
        CUJ_TO_STATSD_INTERACTION_TYPE[83] = 84;
        CUJ_TO_STATSD_INTERACTION_TYPE[84] = 85;
        CUJ_TO_STATSD_INTERACTION_TYPE[85] = 86;
        CUJ_TO_STATSD_INTERACTION_TYPE[86] = 87;
        CUJ_TO_STATSD_INTERACTION_TYPE[88] = 89;
        CUJ_TO_STATSD_INTERACTION_TYPE[89] = 90;
        CUJ_TO_STATSD_INTERACTION_TYPE[90] = 91;
        CUJ_TO_STATSD_INTERACTION_TYPE[91] = 92;
        CUJ_TO_STATSD_INTERACTION_TYPE[92] = 93;
        CUJ_TO_STATSD_INTERACTION_TYPE[93] = 94;
        CUJ_TO_STATSD_INTERACTION_TYPE[95] = 95;
        CUJ_TO_STATSD_INTERACTION_TYPE[96] = 96;
        CUJ_TO_STATSD_INTERACTION_TYPE[97] = 97;
        CUJ_TO_STATSD_INTERACTION_TYPE[98] = 98;
        CUJ_TO_STATSD_INTERACTION_TYPE[99] = 99;
        CUJ_TO_STATSD_INTERACTION_TYPE[100] = 100;
        CUJ_TO_STATSD_INTERACTION_TYPE[101] = 101;
        CUJ_TO_STATSD_INTERACTION_TYPE[102] = 102;
        CUJ_TO_STATSD_INTERACTION_TYPE[103] = 103;
        SEC_CUJ_TO_STATSD_INTERACTION_TYPE = new int[5];
        Arrays.fill(SEC_CUJ_TO_STATSD_INTERACTION_TYPE, -1);
        SEC_CUJ_TO_STATSD_INTERACTION_TYPE[0] = -1;
        SEC_CUJ_TO_STATSD_INTERACTION_TYPE[1] = 10001;
        SEC_CUJ_TO_STATSD_INTERACTION_TYPE[2] = 10002;
    }

    private Cuj() {}

    public static String getNameOfCuj(int cujType) {
        switch (cujType) {
            case 0:
                return "NOTIFICATION_SHADE_EXPAND_COLLAPSE";
            case 2:
                return "NOTIFICATION_SHADE_SCROLL_FLING";
            case 3:
                return "NOTIFICATION_SHADE_ROW_EXPAND";
            case 4:
                return "NOTIFICATION_SHADE_ROW_SWIPE";
            case 5:
                return "NOTIFICATION_SHADE_QS_EXPAND_COLLAPSE";
            case 6:
                return "NOTIFICATION_SHADE_QS_SCROLL_SWIPE";
            case 7:
                return "LAUNCHER_APP_LAUNCH_FROM_RECENTS";
            case 8:
                return "LAUNCHER_APP_LAUNCH_FROM_ICON";
            case 9:
                return "LAUNCHER_APP_CLOSE_TO_HOME";
            case 10:
                return "LAUNCHER_APP_CLOSE_TO_PIP";
            case 11:
                return "LAUNCHER_QUICK_SWITCH";
            case 12:
                return "NOTIFICATION_HEADS_UP_APPEAR";
            case 13:
                return "NOTIFICATION_HEADS_UP_DISAPPEAR";
            case 14:
                return "NOTIFICATION_ADD";
            case 15:
                return "NOTIFICATION_REMOVE";
            case 16:
                return "NOTIFICATION_APP_START";
            case 17:
                return "LOCKSCREEN_PASSWORD_APPEAR";
            case 18:
                return "LOCKSCREEN_PATTERN_APPEAR";
            case 19:
                return "LOCKSCREEN_PIN_APPEAR";
            case 20:
                return "LOCKSCREEN_PASSWORD_DISAPPEAR";
            case 21:
                return "LOCKSCREEN_PATTERN_DISAPPEAR";
            case 22:
                return "LOCKSCREEN_PIN_DISAPPEAR";
            case 23:
                return "LOCKSCREEN_TRANSITION_FROM_AOD";
            case 24:
                return "LOCKSCREEN_TRANSITION_TO_AOD";
            case 25:
                return "LAUNCHER_OPEN_ALL_APPS";
            case 26:
                return "LAUNCHER_ALL_APPS_SCROLL";
            case 27:
                return "LAUNCHER_APP_LAUNCH_FROM_WIDGET";
            case 28:
                return "SETTINGS_PAGE_SCROLL";
            case 29:
                return "LOCKSCREEN_UNLOCK_ANIMATION";
            case 30:
                return "SHADE_APP_LAUNCH_FROM_HISTORY_BUTTON";
            case 31:
                return "SHADE_APP_LAUNCH_FROM_MEDIA_PLAYER";
            case 32:
                return "SHADE_APP_LAUNCH_FROM_QS_TILE";
            case 33:
                return "SHADE_APP_LAUNCH_FROM_SETTINGS_BUTTON";
            case 34:
                return "STATUS_BAR_APP_LAUNCH_FROM_CALL_CHIP";
            case 35:
                return "PIP_TRANSITION";
            case 36:
                return "WALLPAPER_TRANSITION";
            case 37:
                return "USER_SWITCH";
            case 38:
                return "SPLASHSCREEN_AVD";
            case 39:
                return "SPLASHSCREEN_EXIT_ANIM";
            case 40:
                return "SCREEN_OFF";
            case 41:
                return "SCREEN_OFF_SHOW_AOD";
            case 42:
                return "ONE_HANDED_ENTER_TRANSITION";
            case 43:
                return "ONE_HANDED_EXIT_TRANSITION";
            case 44:
                return "UNFOLD_ANIM";
            case 45:
                return "SUW_LOADING_TO_SHOW_INFO_WITH_ACTIONS";
            case 46:
                return "SUW_SHOW_FUNCTION_SCREEN_WITH_ACTIONS";
            case 47:
                return "SUW_LOADING_TO_NEXT_FLOW";
            case 48:
                return "SUW_LOADING_SCREEN_FOR_STATUS";
            case 49:
                return "SPLIT_SCREEN_ENTER";
            case 50:
                return "SPLIT_SCREEN_EXIT";
            case 51:
                return "LOCKSCREEN_LAUNCH_CAMERA";
            case 52:
                return "SPLIT_SCREEN_RESIZE";
            case 53:
                return "SETTINGS_SLIDER";
            case 54:
                return "TAKE_SCREENSHOT";
            case 55:
                return "VOLUME_CONTROL";
            case 56:
                return "BIOMETRIC_PROMPT_TRANSITION";
            case 57:
                return "SETTINGS_TOGGLE";
            case 58:
                return "SHADE_DIALOG_OPEN";
            case 59:
                return "USER_DIALOG_OPEN";
            case 60:
                return "TASKBAR_EXPAND";
            case 61:
                return "TASKBAR_COLLAPSE";
            case 62:
                return "SHADE_CLEAR_ALL";
            case 63:
                return "LAUNCHER_UNLOCK_ENTRANCE_ANIMATION";
            case 64:
                return "LOCKSCREEN_OCCLUSION";
            case 65:
                return "RECENTS_SCROLLING";
            case 66:
                return "LAUNCHER_APP_SWIPE_TO_RECENTS";
            case 67:
                return "LAUNCHER_CLOSE_ALL_APPS_SWIPE";
            case 68:
                return "LAUNCHER_CLOSE_ALL_APPS_TO_HOME";
            case 70:
                return "LOCKSCREEN_CLOCK_MOVE_ANIMATION";
            case 71:
                return "LAUNCHER_OPEN_SEARCH_RESULT";
            case 78:
                return "LAUNCHER_APP_CLOSE_TO_HOME_FALLBACK";
            case 80:
                return "IME_INSETS_SHOW_ANIMATION";
            case 81:
                return "IME_INSETS_HIDE_ANIMATION";
            case 82:
                return "SPLIT_SCREEN_DOUBLE_TAP_DIVIDER";
            case 83:
                return "LAUNCHER_UNFOLD_ANIM";
            case 84:
                return "PREDICTIVE_BACK_CROSS_ACTIVITY";
            case 85:
                return "PREDICTIVE_BACK_CROSS_TASK";
            case 86:
                return "PREDICTIVE_BACK_HOME";
            case 88:
                return "BACK_PANEL_ARROW";
            case 89:
                return "LAUNCHER_CLOSE_ALL_APPS_BACK";
            case 90:
                return "LAUNCHER_SEARCH_QSB_WEB_SEARCH";
            case 91:
                return "LAUNCHER_LAUNCH_APP_PAIR_FROM_WORKSPACE";
            case 92:
                return "LAUNCHER_LAUNCH_APP_PAIR_FROM_TASKBAR";
            case 93:
                return "LAUNCHER_SAVE_APP_PAIR";
            case 95:
                return "LAUNCHER_ALL_APPS_SEARCH_BACK";
            case 96:
                return "LAUNCHER_TASKBAR_ALL_APPS_CLOSE_BACK";
            case 97:
                return "LAUNCHER_TASKBAR_ALL_APPS_SEARCH_BACK";
            case 98:
                return "LAUNCHER_WIDGET_PICKER_CLOSE_BACK";
            case 99:
                return "LAUNCHER_WIDGET_PICKER_SEARCH_BACK";
            case 100:
                return "LAUNCHER_WIDGET_BOTTOM_SHEET_CLOSE_BACK";
            case 101:
                return "LAUNCHER_WIDGET_EDU_SHEET_CLOSE_BACK";
            case 102:
                return "LAUNCHER_PRIVATE_SPACE_LOCK";
            case 103:
                return "LAUNCHER_PRIVATE_SPACE_UNLOCK";
            case 10001:
                return "NOTIFICATION_SHADE_QS_SHOW_DETAIL";
            case 10002:
                return "EDGE_OPEN_PANEL";
            default:
                return "UNKNOWN";
        }
    }

    public static int getStatsdInteractionType(int cujType) {
        if (cujType >= 10000) {
            return SEC_CUJ_TO_STATSD_INTERACTION_TYPE[cujType - 10000];
        }
        return CUJ_TO_STATSD_INTERACTION_TYPE[cujType];
    }

    public static boolean logToStatsd(int cujType) {
        return getStatsdInteractionType(cujType) != -1;
    }

    public static String getNameOfInteraction(int interactionType) {
        return getNameOfCuj(getCujTypeFromInteraction(interactionType));
    }

    private static int getCujTypeFromInteraction(int interactionType) {
        return interactionType - 1;
    }
}
