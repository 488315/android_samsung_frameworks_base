package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityButtonTargetsObserver extends SecureSettingsContentObserver {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
