package com.android.server.policy;

import android.net.Uri;
import android.os.SystemProperties;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public abstract class KeyCustomizationConstants {
    public static final String VOLD_DECRYPT = SystemProperties.get("vold.decrypt");
    public static int[] SUPPORT_PRESS_TYPE_ALL = {3, 4, 8, 16, 32, 64};
    public static int[] SUPPORT_PRESS_TYPE_BASIC = {3, 4, 8};
    public static int[] NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE = {64, 32, 16, 8, 4};
    public static int[] EXTERNAL_SUPPORTED_BEHAVIOR_PRESS_TYPE = {4, 8};
    public static int[] ALL_KEYCODE_TYPE = {26, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 3, 4, 24, 25, 1015, 1079, 79, 1093, 1094, 1095, 1096};
    public static int[] SUPPORT_PRESS_TYPE_XCOVER_TOP = {3, 4};
    public static String[] SUPPORT_PRELOAD_KODIAK_PTT = {"com.verizon.pushtotalkplus", "com.att.eptt", "com.sprint.sdcplus", "com.bell.ptt"};

    public abstract class UriTags {
        public static final Uri DICTATION = Uri.parse("content://com.samsung.android.honeyboard.DictationProvider");
    }

    public static boolean isDebugInput() {
        return PhoneWindowManager.DEBUG_INPUT || ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_INPUT);
    }

    public static boolean isSafeDebugInput() {
        return CoreRune.SAFE_DEBUG || ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_INPUT);
    }
}
