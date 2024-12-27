package com.android.systemui.accessibility;

import android.content.Context;
import android.content.SharedPreferences;

public final class WindowMagnificationFrameSizePrefs {
    public final Context mContext;
    public final SharedPreferences mWindowMagnificationSizePreferences;

    public WindowMagnificationFrameSizePrefs(Context context) {
        this.mContext = context;
        this.mWindowMagnificationSizePreferences = context.getSharedPreferences("window_magnification_preferences", 0);
    }
}
