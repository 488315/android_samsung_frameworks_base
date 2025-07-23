package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccessibilityButtonTargetsObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TargetsChangedListener {
        void onAccessibilityButtonTargetsChanged(String str);
    }

    public AccessibilityButtonTargetsObserver(Context context, UserTracker userTracker, SecureSettings secureSettings) {
        super(context, userTracker, secureSettings, "accessibility_button_targets");
    }

    @Override // com.android.systemui.accessibility.SecureSettingsContentObserver
    public final void onValueChanged(Object obj, String str) {
        ((TargetsChangedListener) obj).onAccessibilityButtonTargetsChanged(str);
    }
}
