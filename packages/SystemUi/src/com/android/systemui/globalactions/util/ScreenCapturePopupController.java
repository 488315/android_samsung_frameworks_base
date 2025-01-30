package com.android.systemui.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScreenCapturePopupController {
    public final LogWrapper mLogWrapper;
    public final SharedPreferences mPrefrerences;

    public ScreenCapturePopupController(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        this.mPrefrerences = context.getSharedPreferences("device_options_screen_capture", 0);
    }
}
