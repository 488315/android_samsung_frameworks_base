package com.android.internal.jank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class Cuj {
    public static final int CUJ_BACK_PANEL_ARROW = 88;
    public static final int CUJ_BIOMETRIC_PROMPT_TRANSITION = 56;
    public static final int CUJ_DEFAULT_TASK_TO_TASK_ANIMATION = 128;
    public static final int CUJ_DESKTOP_MODE_APP_LAUNCH_FROM_ICON = 124;
    public static final int CUJ_DESKTOP_MODE_APP_LAUNCH_FROM_INTENT = 123;
    public static final int CUJ_DESKTOP_MODE_CLOSE_TASK = 122;
    public static final int CUJ_DESKTOP_MODE_DRAG_WINDOW = 110;
    public static final int CUJ_DESKTOP_MODE_ENTER_APP_HANDLE_DRAG_HOLD = 107;
    public static final int CUJ_DESKTOP_MODE_ENTER_APP_HANDLE_DRAG_RELEASE = 116;
    public static final int CUJ_DESKTOP_MODE_ENTER_FROM_OVERVIEW_MENU = 120;
    public static final int CUJ_DESKTOP_MODE_ENTER_MODE_APP_HANDLE_MENU = 112;
    public static final int CUJ_DESKTOP_MODE_EXIT_MODE = 108;
    public static final int CUJ_DESKTOP_MODE_EXIT_MODE_ON_LAST_WINDOW_CLOSE = 117;
    public static final int CUJ_DESKTOP_MODE_KEYBOARD_QUICK_SWITCH_APP_LAUNCH = 125;
    public static final int CUJ_DESKTOP_MODE_MAXIMIZE_WINDOW = 104;
    public static final int CUJ_DESKTOP_MODE_MINIMIZE_WINDOW = 109;
    public static final int CUJ_DESKTOP_MODE_MOVE_WINDOW_TO_DISPLAY = 129;
    public static final int CUJ_DESKTOP_MODE_RESIZE_WINDOW = 106;
    public static final int CUJ_DESKTOP_MODE_SNAP_RESIZE = 118;
    public static final int CUJ_DESKTOP_MODE_UNMAXIMIZE_WINDOW = 119;
    public static final int CUJ_FOLD_ANIM = 105;
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
    public static final int CUJ_LAUNCHER_KEYBOARD_QUICK_SWITCH_APP_LAUNCH = 115;
    public static final int CUJ_LAUNCHER_KEYBOARD_QUICK_SWITCH_CLOSE = 114;
    public static final int CUJ_LAUNCHER_KEYBOARD_QUICK_SWITCH_OPEN = 113;
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_TASKBAR = 92;
    public static final int CUJ_LAUNCHER_LAUNCH_APP_PAIR_FROM_WORKSPACE = 91;
    public static final int CUJ_LAUNCHER_OPEN_ALL_APPS = 25;
    public static final int CUJ_LAUNCHER_OPEN_SEARCH_RESULT = 71;
    public static final int CUJ_LAUNCHER_OVERVIEW_TASK_DISMISS = 121;
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
    public static final int CUJ_LAUNCHER_WORK_UTILITY_VIEW_EXPAND = 126;
    public static final int CUJ_LAUNCHER_WORK_UTILITY_VIEW_SHRINK = 127;
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
    public static final int CUJ_STATUS_BAR_APP_RETURN_TO_CALL_CHIP = 130;
    public static final int CUJ_STATUS_BAR_LAUNCH_DIALOG_FROM_CHIP = 111;
    public static final int CUJ_SUW_LOADING_SCREEN_FOR_STATUS = 48;
    public static final int CUJ_SUW_LOADING_TO_NEXT_FLOW = 47;
    public static final int CUJ_SUW_LOADING_TO_SHOW_INFO_WITH_ACTIONS = 45;
    public static final int CUJ_SUW_SHOW_FUNCTION_SCREEN_WITH_ACTIONS = 46;
    public static final int CUJ_TAKE_SCREENSHOT = 54;
    public static final int CUJ_TASKBAR_COLLAPSE = 61;
    public static final int CUJ_TASKBAR_EXPAND = 60;
    private static final int[] CUJ_TO_STATSD_INTERACTION_TYPE;
    public static final int CUJ_UNFOLD_ANIM = 44;
    public static final int CUJ_USER_DIALOG_OPEN = 59;
    public static final int CUJ_USER_SWITCH = 37;
    public static final int CUJ_VOLUME_CONTROL = 55;
    public static final int CUJ_WALLPAPER_TRANSITION = 36;
    static final int LAST_CUJ = 130;
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
    public @interface CujType {
    }

    private static int getCujTypeFromInteraction(int i) {
        return i - 1;
    }

    static {
        int[] iArr = {1, 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 0, 71, 72, 0, 0, 0, 0, 0, 0, 79, 0, 81, 82, 83, 84, 85, 86, 87, 0, 89, 90, 91, 92, 93, 94, 0, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130};
        CUJ_TO_STATSD_INTERACTION_TYPE = iArr;
        Arrays.fill(iArr, -1);
        int[] iArr2 = {-1, 10001, 10002, 0, 0};
        SEC_CUJ_TO_STATSD_INTERACTION_TYPE = iArr2;
        Arrays.fill(iArr2, -1);
    }

    private Cuj() {
    }

    public static String getNameOfCuj(int i) {
        if (i == 0) {
            return "NOTIFICATION_SHADE_EXPAND_COLLAPSE";
        }
        if (i == 78) {
            return "LAUNCHER_APP_CLOSE_TO_HOME_FALLBACK";
        }
        if (i == 70) {
            return "LOCKSCREEN_CLOCK_MOVE_ANIMATION";
        }
        if (i == 71) {
            return "LAUNCHER_OPEN_SEARCH_RESULT";
        }
        if (i == 10001) {
            return "NOTIFICATION_SHADE_QS_SHOW_DETAIL";
        }
        if (i == 10002) {
            return "EDGE_OPEN_PANEL";
        }
        switch (i) {
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
            default:
                switch (i) {
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
                    default:
                        switch (i) {
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
                            default:
                                switch (i) {
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
                                    case 104:
                                        return "DESKTOP_MODE_MAXIMIZE_WINDOW";
                                    case 105:
                                        return "FOLD_ANIM";
                                    case 106:
                                        return "DESKTOP_MODE_RESIZE_WINDOW";
                                    case 107:
                                        return "DESKTOP_MODE_ENTER_APP_HANDLE_DRAG_HOLD";
                                    case 108:
                                        return "DESKTOP_MODE_EXIT_MODE";
                                    case 109:
                                        return "DESKTOP_MODE_MINIMIZE_WINDOW";
                                    case 110:
                                        return "DESKTOP_MODE_DRAG_WINDOW";
                                    case 111:
                                        return "STATUS_BAR_LAUNCH_DIALOG_FROM_CHIP";
                                    case 112:
                                        return "DESKTOP_MODE_ENTER_MODE_APP_HANDLE_MENU";
                                    case 113:
                                        return "LAUNCHER_KEYBOARD_QUICK_SWITCH_OPEN";
                                    case 114:
                                        return "LAUNCHER_KEYBOARD_QUICK_SWITCH_CLOSE";
                                    case 115:
                                        return "LAUNCHER_KEYBOARD_QUICK_SWITCH_APP_LAUNCH";
                                    case 116:
                                        return "DESKTOP_MODE_ENTER_APP_HANDLE_DRAG_RELEASE";
                                    case 117:
                                        return "DESKTOP_MODE_EXIT_MODE_ON_LAST_WINDOW_CLOSE";
                                    case 118:
                                        return "DESKTOP_MODE_SNAP_RESIZE";
                                    case 119:
                                        return "DESKTOP_MODE_UNMAXIMIZE_WINDOW";
                                    case 120:
                                        return "DESKTOP_MODE_ENTER_FROM_OVERVIEW_MENU";
                                    case 121:
                                        return "LAUNCHER_OVERVIEW_TASK_DISMISS";
                                    case 122:
                                        return "DESKTOP_MODE_CLOSE_TASK";
                                    case 123:
                                        return "DESKTOP_MODE_APP_LAUNCH_FROM_INTENT";
                                    case 124:
                                        return "DESKTOP_MODE_APP_LAUNCH_FROM_ICON";
                                    case 125:
                                        return "DESKTOP_MODE_KEYBOARD_QUICK_SWITCH_APP_LAUNCH";
                                    case 126:
                                        return "LAUNCHER_WORK_UTILITY_VIEW_EXPAND";
                                    case 127:
                                        return "LAUNCHER_WORK_UTILITY_VIEW_SHRINK";
                                    case 128:
                                        return "DEFAULT_TASK_TO_TASK_ANIMATION";
                                    case 129:
                                        return "DESKTOP_MODE_MOVE_WINDOW_TO_DISPLAY";
                                    case 130:
                                        return "STATUS_BAR_APP_RETURN_TO_CALL_CHIP";
                                    default:
                                        return "UNKNOWN";
                                }
                        }
                }
        }
    }

    public static int getStatsdInteractionType(int i) {
        if (i >= 10000) {
            return SEC_CUJ_TO_STATSD_INTERACTION_TYPE[i - 10000];
        }
        return CUJ_TO_STATSD_INTERACTION_TYPE[i];
    }

    public static boolean logToStatsd(int i) {
        return getStatsdInteractionType(i) != -1;
    }

    public static String getNameOfInteraction(int i) {
        return getNameOfCuj(getCujTypeFromInteraction(i));
    }
}
