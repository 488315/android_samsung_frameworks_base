package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AccessibilityController implements AccessibilityManager.AccessibilityStateChangeListener, AccessibilityManager.TouchExplorationStateChangeListener {
    public final ArrayList mChangeCallbacks = new ArrayList();

    public AccessibilityController(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        accessibilityManager.addTouchExplorationStateChangeListener(this);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        accessibilityManager.isEnabled();
        accessibilityManager.isTouchExplorationEnabled();
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
    public final void onAccessibilityStateChanged(boolean z) {
        if (this.mChangeCallbacks.size() <= 0) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(this.mChangeCallbacks.get(0));
        throw null;
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        if (this.mChangeCallbacks.size() <= 0) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(this.mChangeCallbacks.get(0));
        throw null;
    }
}
