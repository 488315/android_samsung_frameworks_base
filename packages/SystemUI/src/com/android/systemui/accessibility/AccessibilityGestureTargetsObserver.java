package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccessibilityGestureTargetsObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TargetsChangedListener {
    }

    public AccessibilityGestureTargetsObserver(Context context, UserTracker userTracker, SecureSettings secureSettings) {
        super(context, userTracker, secureSettings, "accessibility_gesture_targets");
    }

    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public final void onValueChanged(Object obj, String str) {
        ((NavBarHelper) ((TargetsChangedListener) obj)).updateA11yState();
    }
}
