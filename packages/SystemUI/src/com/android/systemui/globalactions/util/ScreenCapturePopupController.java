package com.android.systemui.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ScreenCapturePopupController {
    public final LogWrapper mLogWrapper;
    public final SharedPreferences mPrefrerences;

    public ScreenCapturePopupController(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        this.mPrefrerences = context.getSharedPreferences("device_options_screen_capture", 0);
    }
}
