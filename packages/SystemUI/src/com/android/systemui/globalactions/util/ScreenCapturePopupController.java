package com.android.systemui.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.globalactions.util.LogWrapper;

public final class ScreenCapturePopupController {
    public final LogWrapper mLogWrapper;
    public final SharedPreferences mPrefrerences;

    public ScreenCapturePopupController(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        this.mPrefrerences = context.getSharedPreferences("device_options_screen_capture", 0);
    }
}
