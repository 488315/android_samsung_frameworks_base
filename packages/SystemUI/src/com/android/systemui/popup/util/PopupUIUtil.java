package com.android.systemui.popup.util;

import android.os.SystemProperties;
import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
    public static final boolean SIM_CARD_TRAY_STYLE_B6_JAPAN_MODEL;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL_B6;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL_B7;
    public static final boolean SIM_CARD_TRAY_STYLE_FLIP_TYPE;
    public static final boolean SIM_CARD_TRAY_STYLE_FOLD_A_TYPE;
    public static final boolean SIM_CARD_TRAY_STYLE_FOLD_TYPE;
    public static final int SIM_CARD_TRAY_STYLE_NORMAL_TYPE = 1;
    public static final boolean SIM_CARD_TRAY_STYLE_Q6_JAPAN_MODEL;
    public static final boolean SIM_CARD_TRAY_WATERPROOF_FALSE = false;
    public static final boolean SIM_CARD_TRAY_WATERPROOF_TRUE = true;
    public static final int SIM_CARD_TRAY_WATER_PROTECTION_POPUP = 1;

    static {
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        boolean z2 = false;
        boolean z3 = z && SystemProperties.get("ro.product.name", "").startsWith("q6aq");
        SIM_CARD_TRAY_STYLE_FOLD_A_TYPE = z3;
        boolean z4 = SystemProperties.get("ro.product.name", "").startsWith("SC-55E") || SystemProperties.get("ro.product.name", "").startsWith("SCG28");
        SIM_CARD_TRAY_STYLE_Q6_JAPAN_MODEL = z4;
        SIM_CARD_TRAY_STYLE_FOLD_TYPE = z && (SystemProperties.get("ro.product.name", "").startsWith("q6q") || z4) && !z3;
        boolean z5 = SystemProperties.get("ro.product.name", "").startsWith("b6qzcx") || SystemProperties.get("ro.product.name", "").startsWith("b6qzhx") || SystemProperties.get("ro.product.name", "").startsWith("b6qctcx");
        SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL_B6 = z5;
        boolean z6 = SystemProperties.get("ro.product.name", "").startsWith("b7szcx") || SystemProperties.get("ro.product.name", "").startsWith("b7szhx") || SystemProperties.get("ro.product.name", "").startsWith("b7rzcx");
        SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL_B7 = z6;
        SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL = z5 || z6;
        boolean z7 = SystemProperties.get("ro.product.name", "").startsWith("SC-54E") || SystemProperties.get("ro.product.name", "").startsWith("SCG29");
        SIM_CARD_TRAY_STYLE_B6_JAPAN_MODEL = z7;
        if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP && (BasicRune.SIM_CARD_TRAY_STYLE_FLIP_COMMON_MODEL || z7)) {
            z2 = true;
        }
        SIM_CARD_TRAY_STYLE_FLIP_TYPE = z2;
    }

    public boolean isNoReadySim() {
        return DeviceState.getReadySimCount() == 0;
    }
}
