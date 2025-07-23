package com.android.systemui.accessibility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Size;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowMagnificationFrameSizePrefs {
    public final Context mContext;
    public final SharedPreferences mWindowMagnificationSizePreferences;

    public WindowMagnificationFrameSizePrefs(Context context) {
        this.mContext = context;
        this.mWindowMagnificationSizePreferences = context.getSharedPreferences("window_magnification_preferences", 0);
    }

    public final int getIndexForCurrentDensity() {
        String string = this.mWindowMagnificationSizePreferences.getString(getKey(), null);
        if (string == null) {
            return 2;
        }
        try {
            return WindowMagnificationFrameSpec.deserialize(string).index;
        } catch (NumberFormatException unused) {
            return 2;
        }
    }

    public final String getKey() {
        return String.valueOf(this.mContext.getResources().getConfiguration().smallestScreenWidthDp);
    }

    public final void saveIndexAndSizeForCurrentDensity(int i, Size size) {
        SharedPreferences.Editor edit = this.mWindowMagnificationSizePreferences.edit();
        String key = getKey();
        WindowMagnificationFrameSpec.Companion.getClass();
        edit.putString(key, i + "," + size).apply();
    }
}
