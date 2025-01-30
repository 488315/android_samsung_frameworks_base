package com.android.systemui.accessibility;

import android.content.Context;
import android.util.Log;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityButtonModeObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ModeChangedListener {
        void onAccessibilityButtonModeChanged(int i);
    }

    public AccessibilityButtonModeObserver(Context context, UserTracker userTracker) {
        super(context, userTracker, "accessibility_button_mode");
    }

    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public final void onValueChanged(Object obj, String str) {
        int i;
        ModeChangedListener modeChangedListener = (ModeChangedListener) obj;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            i = 0;
        }
        modeChangedListener.onAccessibilityButtonModeChanged(i);
    }
}
