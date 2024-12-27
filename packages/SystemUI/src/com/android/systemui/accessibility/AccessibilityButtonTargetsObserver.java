package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.settings.UserTracker;

public final class AccessibilityButtonTargetsObserver extends SecureSettingsContentObserver {

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
