package androidx.picker.helper;

import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CompountButtonHelperKt {
    public static final void setAccessibilityFocusable(CompoundButton compoundButton, boolean z) {
        Object systemService = compoundButton.getContext().getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        if (z) {
            compoundButton.setFocusable(true);
            compoundButton.setClickable(true);
        } else {
            compoundButton.setFocusable(false);
            compoundButton.setClickable(false);
        }
    }
}
