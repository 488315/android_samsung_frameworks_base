package com.android.systemui.popup.util;

import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceState;

public class PopupUIUtil {
    public static final String ACTION_AIRPLANE_MODE_SETTINGS = "com.samsung.settings.AIRPLANE_MODE";
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";
    public static final String ACTION_DATA_CONNECTION_ERROR = "com.samsung.systemui.popup.intent.DATA_CONNECTION_ERROR";
    public static final String ACTION_MULTI_WINDOW_ENABLE_CHANGED = "com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED";
    public static final String ACTION_MULTI_WINDOW_ENABLE_VALID_REQUESTER = "SSRM";
    public static final String ACTION_SIM_CARD_TRAY_PROTECTION_POPUP = "com.samsung.systemui.popup.intent.SIM_CARD_TRAY_PROTECTION_POPUP";
    public static final int DATA_CONNECTION_DATAROAMING_OFF = 2;
    public static final int DATA_CONNECTION_FLIGHTMODE_ON = 0;
    public static final int DATA_CONNECTION_INVALID = -1;
    public static final int DATA_CONNECTION_MOBILEDATA_OFF = 1;
    public static final int DATA_CONNECTION_NO_SIGNAL = 4;
    public static final int DATA_CONNECTION_REACHED_DATALIMIT = 3;
    public static final String EXTRA_DATA_CONNECTION_ERROR_NO_SIGNAL_RETRY_ENABLE = "no_signal_retry_enable";
    public static final String EXTRA_DATA_CONNECTION_ERROR_NO_SIGNAL_RETRY_PENDING_INTENT = "no_signal_retry_intent";
    public static final String EXTRA_DATA_CONNECTION_ERROR_TYPE = "type";
    public static final String EXTRA_IN_MULTI_WINDOW_MODE = "com.samsung.android.extra.IN_MULTI_WINDOW_MODE";
    public static final String EXTRA_MULTI_WINDOW_ENABLED = "com.samsung.android.extra.MULTI_WINDOW_ENABLED";
    public static final String EXTRA_MULTI_WINDOW_ENABLE_REQUESTER = "com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER";
    public static final String EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS = "dismiss";
    public static final String EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_STYLE = "tray";
    public static final String EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_TYPE = "type";
    public static final String EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_WATERPROOF = "waterproof";
    public static final String POPUP_UI_PERMISSON = "com.samsung.systemui.POPUP_UI_PERMISSION";
    public static final int SIM_CARD_TRAY_EMPTY_WATER_PROTECTION_POPUP = 0;
    public static final int SIM_CARD_TRAY_STYLE_NORMAL_TYPE = 1;
    public static final boolean SIM_CARD_TRAY_WATERPROOF_FALSE = false;
    public static final boolean SIM_CARD_TRAY_WATERPROOF_TRUE = true;
    public static final int SIM_CARD_TRAY_WATER_PROTECTION_POPUP = 1;
    public static final boolean SIM_CARD_TRAY_STYLE_FOLD_TYPE = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_TYPE = BasicRune.BASIC_FOLDABLE_TYPE_FLIP;

    public boolean isNoReadySim() {
        return DeviceState.getReadySimCount() == 0;
    }
}
