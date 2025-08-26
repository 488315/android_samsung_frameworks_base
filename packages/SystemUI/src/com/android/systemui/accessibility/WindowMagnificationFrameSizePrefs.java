package com.android.systemui.accessibility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Size;

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
        SharedPreferences.Editor editorEdit = this.mWindowMagnificationSizePreferences.edit();
        String key = getKey();
        WindowMagnificationFrameSpec.Companion.getClass();
        editorEdit.putString(key, i + "," + size).apply();
    }
}
