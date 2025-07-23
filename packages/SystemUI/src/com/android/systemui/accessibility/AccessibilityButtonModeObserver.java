package com.android.systemui.accessibility;

import android.content.Context;
import android.util.Log;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccessibilityButtonModeObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ModeChangedListener {
        void onAccessibilityButtonModeChanged(int i);
    }

    public AccessibilityButtonModeObserver(Context context, UserTracker userTracker, SecureSettings secureSettings) {
        super(context, userTracker, secureSettings, "accessibility_button_mode");
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
