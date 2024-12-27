package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AccessibilityButtonTargetsObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface TargetsChangedListener {
        void onAccessibilityButtonTargetsChanged(String str);
    }

    public AccessibilityButtonTargetsObserver(Context context, UserTracker userTracker) {
        super(context, userTracker, "accessibility_button_targets");
    }

    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public final void onValueChanged(Object obj, String str) {
        ((TargetsChangedListener) obj).onAccessibilityButtonTargetsChanged(str);
    }
}
