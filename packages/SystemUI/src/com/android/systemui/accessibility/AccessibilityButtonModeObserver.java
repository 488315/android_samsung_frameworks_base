package com.android.systemui.accessibility;

import android.content.Context;
import android.util.Log;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AccessibilityButtonModeObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
