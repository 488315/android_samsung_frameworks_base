package com.android.server.policy;

import android.net.Uri;
import android.os.SystemProperties;

import com.android.internal.util.FrameworkStatsLog;

public abstract class KeyCustomizationConstants {
    public static final String VOLD_DECRYPT = SystemProperties.get("vold.decrypt");
    public static final int[] SUPPORT_PRESS_TYPE_ALL = {3, 4, 8, 16, 32, 64};
    public static final int[] SUPPORT_PRESS_TYPE_BASIC = {3, 4, 8};
    public static final int[] NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE = {64, 32, 16, 8, 4};
    public static final int[] EXTERNAL_SUPPORTED_BEHAVIOR_PRESS_TYPE = {4, 8};
    public static final int[] ALL_KEYCODE_TYPE = {
        26,
        FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED,
        3,
        4,
        24,
        25,
        1015,
        1079,
        79,
        1093,
        1094,
        1095,
        1096
    };
    public static final int[] SUPPORT_PRESS_TYPE_XCOVER_TOP = {3, 4};
    public static final String[] SUPPORT_PRELOAD_KODIAK_PTT = {
        "com.verizon.pushtotalkplus", "com.att.eptt", "com.sprint.sdcplus", "com.bell.ptt"
    };

    public abstract class UriTags {
        public static final Uri DICTATION =
                Uri.parse("content://com.samsung.android.honeyboard.DictationProvider");
    }
}
