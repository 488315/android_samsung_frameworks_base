package com.samsung.android.knox.appconfig;

import com.samsung.android.knox.EdmUtils;
import com.samsung.android.knox.appconfig.info.KeyInfo;

/* loaded from: classes4.dex */
public class ApplicationRestrictionsContract {

    public class Result {
        public static int ERROR_ALREADY_SET = 4;
        public static int ERROR_INVALID_KEY = 1;
        public static int ERROR_INVALID_VALUE = 2;
        public static int ERROR_NONE = 0;
        public static int ERROR_NOT_SUPPORTED = 5;
        public static int ERROR_OUT_OF_RANGE = 3;
        public static int ERROR_PERMISSION_DENIED = 6;
        public static int ERROR_UNKNOWN = -1;
    }

    public static int getResultCode(String str) {
        KeyInfo.KEY key = KeyInfo.KEY.NONE;
        KeyInfo.KEY key2 = KeyInfo.KEYMAP.get(str);
        return (key2 == null || EdmUtils.getAPILevelForInternal() < key2.getVersion()) ? Result.ERROR_INVALID_KEY : Result.ERROR_NONE;
    }
}
