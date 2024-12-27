package com.android.systemui.globalactions.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenCapturePopupController {
    public final LogWrapper mLogWrapper;
    public final SharedPreferences mPrefrerences;

    public ScreenCapturePopupController(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        this.mPrefrerences = context.getSharedPreferences("device_options_screen_capture", 0);
    }
}
