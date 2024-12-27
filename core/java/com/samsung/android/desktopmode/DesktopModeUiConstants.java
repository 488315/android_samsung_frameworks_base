package com.samsung.android.desktopmode;

import android.app.admin.DevicePolicyResources;

/* loaded from: classes6.dex */
public class DesktopModeUiConstants {
    public static final String ACTION_DESKTOP_MODE_UPDATE_REQUEST =
            "com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST";
    public static final String ACTION_DEX_RUNNING_NOTIFICATION_PRESSED =
            "com.samsung.android.desktopmode.action.DEX_RUNNING_NOTIFICATION_PRESSED";
    public static final String ACTION_POGO_KEYBOARD_CHANGED =
            "com.samsung.android.input.POGO_KEYBOARD_CHANGED";
    public static final String ACTION_SPEN_NOTIFICATION_CHANGE_MODE_PRESSED =
            "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED";
    public static final String ACTION_SPEN_NOTIFICATION_PRESSED =
            "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED";
    public static final String ACTION_TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED =
            "com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED";
    public static final String ACTION_TOUCHPAD_NOTIFICATION_PRESSED =
            "com.samsung.android.desktopmode.action.TOUCHPAD_NOTIFICATION_PRESSED";
    private static final int ACTIVITY_END = 399;
    private static final int ACTIVITY_START = 300;
    public static final int ACTIVITY_TYPE_TOUCHPAD = 300;
    public static final int ACTIVITY_TYPE_WELCOME = 301;
    public static final int COMMAND_DISMISS = 901;
    public static final int COMMAND_SHOW = 900;
    private static final int DIALOG_END = 99;
    private static final int DIALOG_START = 0;
    public static final int DIALOG_TYPE_ANY = 0;
    public static final int DIALOG_TYPE_DISPLAY_UNSUPPORT = 2;
    public static final int DIALOG_TYPE_DOCK_TA_WARNING_NOT_FAST_CHARGER = 3;
    public static final int DIALOG_TYPE_SECURED_APP = 8;
    public static final int DIALOG_TYPE_SPEN = 6;
    public static final int DIALOG_TYPE_STABILIZER_MODE = 10;
    public static final int DIALOG_TYPE_START_TOUCHPAD_NAVBAR = 9;
    public static final int DIALOG_TYPE_START_TOUCHPAD_NOTI = 7;
    public static final int DIALOG_TYPE_TABLET_INITIAL_HDMI_CONNECTION = 5;
    public static final int DIALOG_TYPE_TABLET_INITIAL_POGO_CONNECTION = 4;
    public static final int DIALOG_TYPE_TOUCHPAD = 1;
    public static final int DIALOG_TYPE_WIRELESS_DEX_CONNECTION_FREQUENCY = 11;
    public static final int DIALOG_TYPE_WIRELESS_DEX_LOW_PERFORMANCE = 12;
    public static final String EXTRA_DESKTOP_MODE_SOURCE =
            "com.samsung.android.desktopmode.extra.DESKTOP_MODE_SOURCE";
    public static final int EXTRA_DESKTOP_MODE_SOURCE_BIXBY = 8;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_BIXBY_ROUTINES = 9;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_CONNECTIVITY_ACTIVITY = 11;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_DEFAULT = -1;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_DEX_PANEL = 5;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_FMM = 10;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_HDMI_MODE = 7;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_KEYBOARD_SHORTCUT = 6;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_NOTIFICATION = 0;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_SETTINGS = 4;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_TILE = 1;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_TOGGLE = 3;
    public static final int EXTRA_DESKTOP_MODE_SOURCE_WELCOME = 2;
    public static final String EXTRA_DESKTOP_MODE_STATE =
            "com.samsung.android.desktopmode.extra.DESKTOP_MODE_STATE";
    public static final int EXTRA_DESKTOP_MODE_STATE_DEFAULT = -1;
    public static final int EXTRA_DESKTOP_MODE_STATE_DISABLED = 2;
    public static final int EXTRA_DESKTOP_MODE_STATE_ENABLED = 1;
    public static final int EXTRA_DESKTOP_MODE_STATE_UPDATE = 0;
    public static final String EXTRA_NEW_DEX_MODE_STATE =
            "com.samsung.android.desktopmode.extra.NEW_DEX_MODE_STATE";
    public static final String JIT_CONTENT_ID_DEX_MIRRORING = "DEXX_0004";
    public static final int MESSAGE_COMMAND_MINIMIZE_TASKS = 1000;
    public static final String MESSAGE_KEY_COMMAND = "command";
    private static final int NAVBAR_ICON_END = 499;
    private static final int NAVBAR_ICON_START = 400;
    public static final int NAVBAR_ICON_TYPE_TOUCHPAD = 400;
    private static final int NOTIFICATION_END = 299;
    public static final int NOTIFICATION_ID_OTHER = -1;
    public static final int NOTIFICATION_ID_TOUCHPAD = 1;
    private static final int NOTIFICATION_START = 200;
    public static final int NOTIFICATION_TYPE_SPEN = 203;
    public static final int NOTIFICATION_TYPE_TOUCHPAD = 200;
    public static final int NOTIFICATION_TYPE_TOUCHPAD_AVAILABLE = 201;
    public static final int NOTIFICATION_TYPE_TOUCHPAD_SPEN_AVAILABLE = 202;
    public static final int NOTIFICATION_TYPE_WIRELESS_DEX = 204;
    private static final int OVERLAY_END = 199;
    private static final int OVERLAY_START = 100;
    public static final int OVERLAY_TYPE_ANY = 111;
    public static final int OVERLAY_TYPE_BLACK_SCREEN = 115;
    public static final int OVERLAY_TYPE_INTRO_SCREEN = 112;
    public static final int OVERLAY_TYPE_LOADING_SCREEN_ENTER = 113;
    public static final int OVERLAY_TYPE_LOADING_SCREEN_EXIT = 114;
    public static final int OVERLAY_TYPE_MODE_CHANGE_SCREEN_MIRRORING_OFF = 117;
    public static final int OVERLAY_TYPE_MODE_CHANGE_SCREEN_MIRRORING_ON = 116;
    public static final int OVERLAY_WHERE_ALL = 100;
    public static final int OVERLAY_WHERE_ANY = 101;
    public static final int OVERLAY_WHERE_EXTERNAL = 103;
    public static final int OVERLAY_WHERE_INTERNAL = 102;
    public static final int STATE_DOCK_LOW_CHARGER_CONNECTED = 1;
    public static final int STATE_DOCK_LOW_CHARGER_NOT_CONNECTED = 2;
    public static final int STATE_DOCK_LOW_CHARGER_UNKNOWN = -1;
    public static final int TIPS_DASHBOARD_CONDITION_SHOW_IMMEDIATELY = 1;
    public static final String TIPS_DASHBOARD_ID_DEX_MIRRORING = "0008";
    public static final int UI_TYPE_NONE = -1;

    public static boolean isDialogType(int type) {
        return type >= 0 && type <= 99;
    }

    public static boolean isNotificationType(int type) {
        return 200 <= type && type <= 299;
    }

    public static int getNotificationId(int type) {
        switch (type) {
            case 200:
            case 201:
            case 202:
                return 1;
            default:
                return -1;
        }
    }

    public static String whereToString(int where) {
        switch (where) {
            case 100:
                return "OVERLAY_WHERE_ALL";
            case 101:
                return "OVERLAY_WHERE_ANY";
            case 102:
                return "OVERLAY_WHERE_INTERNAL";
            case 103:
                return "OVERLAY_WHERE_EXTERNAL";
            default:
                return "where=" + where;
        }
    }

    public static String typeToString(int type) {
        switch (type) {
            case -1:
                return "UI_TYPE_NONE";
            case 0:
                return "DIALOG_TYPE_ANY";
            case 1:
                return "DIALOG_TYPE_TOUCHPAD";
            case 2:
                return "DIALOG_TYPE_DISPLAY_UNSUPPORT";
            case 3:
                return "DIALOG_TYPE_DOCK_TA_WARNING_NOT_FAST_CHARGER";
            case 4:
                return "DIALOG_TYPE_TABLET_INITIAL_POGO_CONNECTION";
            case 5:
                return "DIALOG_TYPE_TABLET_INITIAL_HDMI_CONNECTION";
            case 7:
                return "DIALOG_TYPE_START_TOUCHPAD_NOTI";
            case 8:
                return "DIALOG_TYPE_SECURED_APP";
            case 9:
                return "DIALOG_TYPE_START_TOUCHPAD_NAVBAR";
            case 10:
                return "DIALOG_TYPE_STABILIZER_MODE";
            case 11:
                return "DIALOG_TYPE_WIRELESS_DEX_CONNECTION_FREQUENCY";
            case 12:
                return "DIALOG_TYPE_WIRELESS_DEX_LOW_PERFORMANCE";
            case 111:
                return "OVERLAY_TYPE_ANY";
            case 112:
                return "OVERLAY_TYPE_INTRO_SCREEN";
            case 113:
                return "OVERLAY_TYPE_LOADING_SCREEN_ENTER";
            case 114:
                return "OVERLAY_TYPE_LOADING_SCREEN_EXIT";
            case 115:
                return "OVERLAY_TYPE_BLACK_SCREEN";
            case 116:
                return "OVERLAY_TYPE_MODE_CHANGE_SCREEN_MIRRORING_ON";
            case 117:
                return "OVERLAY_TYPE_MODE_CHANGE_SCREEN_MIRRORING_OFF";
            case 200:
                return "NOTIFICATION_TYPE_TOUCHPAD";
            case 201:
                return "NOTIFICATION_TYPE_TOUCHPAD_AVAILABLE";
            case 202:
                return "NOTIFICATION_TYPE_TOUCHPAD_SPEN_AVAILABLE";
            case 203:
                return "NOTIFICATION_TYPE_SPEN";
            case 204:
                return "NOTIFICATION_TYPE_WIRELESS_DEX";
            case 300:
                return "ACTIVITY_TYPE_TOUCHPAD";
            case 301:
                return "ACTIVITY_TYPE_WELCOME";
            case 400:
                return "NAVBAR_ICON_TYPE_TOUCHPAD";
            default:
                return "Unknown=" + type;
        }
    }

    public static String commandToString(int command) {
        switch (command) {
            case 900:
                return "COMMAND_SHOW";
            case 901:
                return "COMMAND_DISMISS";
            default:
                return "Unknown=" + command;
        }
    }

    public static String sourceToString(int source) {
        switch (source) {
            case -1:
                return "DEFAULT";
            case 0:
                return DevicePolicyResources.Drawables.Source.NOTIFICATION;
            case 1:
                return "TILE";
            case 2:
                return "WELCOME";
            case 3:
                return "TOGGLE";
            case 4:
                return "SETTINGS";
            case 5:
                return "DEX_PANEL";
            case 6:
                return "KEYBOARD_SHORTCUT";
            case 7:
                return "HDMI_MODE";
            case 8:
                return "BIXBY";
            case 9:
                return "BIXBY_ROUTINES";
            case 10:
                return "FMM";
            case 11:
                return "CONNECTIVITY_ACTIVITY";
            default:
                return "Unknown=" + source;
        }
    }
}
