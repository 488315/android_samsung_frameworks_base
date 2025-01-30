package com.samsung.systemui.splugins.slimindicator;

import android.graphics.drawable.Drawable;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPluginSlimIndicatorModel {
    public static final String DB_KEY_BATTERY_ICON = "battery_icon";
    public static final String DB_KEY_BATTERY_PERCENT = "battery_percent";
    public static final String DB_KEY_DEFAULT_NULL = "rotate,headset";
    public static final String DB_KEY_HOME_CARRIER = "slimindicator_home_carrier";
    public static final String DB_KEY_LEFT_CLOCK_POSITION = "left_clock_position";
    public static final String DB_KEY_LOCK_CARRIER = "slimindicator_lock_carrier";
    public static final String DB_KEY_MIDDLE_CLOCK_POSITION = "middle_clock_position";
    public static final String DB_KEY_PANEL_CARRIER = "slimindicator_panel_carrier";
    public static final String DB_KEY_RIGHT_CLOCK_POSITION = "right_clock_position";
    public static final String DB_KEY_SHOW_AMPM = "slimindicator_show_ampm";
    public static final String DB_KEY_SHOW_DATE = "slimindicator_show_date";
    public static final String DB_KEY_SHOW_SECONDS = "slimindicator_show_seconds";
    public static final String DB_KEY_TWO_PHONE_MODE_ICON = "two_phone_mode_icon";
    public static final String ICON_BLACKLIST_SETTING = "icon_blacklist";
    public static final String INTENT_ACTION_ICON_BLACKLIST = "com.samsung.systemui.action.SPLUGIN_SLIMINDICATOR";
    public static final String INTENT_EXTRA_ICON_BLACKLIST = "iconblacklist";
    public static final int VERSION = 7005;
    public static final HashMap<String, Drawable> mIconDrawableList = null;
    public static final HashMap<String, Boolean> mIconEnableList = null;
    public static final boolean mIsShowHomeCarrier = true;
    public static final boolean mIsShowLockCarrier = true;

    boolean getIsLockCarrier();

    boolean getIsShowCarrier();
}
