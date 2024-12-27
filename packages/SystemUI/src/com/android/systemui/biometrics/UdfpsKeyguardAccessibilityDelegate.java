package com.android.systemui.biometrics;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

public final class UdfpsKeyguardAccessibilityDelegate extends View.AccessibilityDelegate {
    public final StatusBarKeyguardViewManager keyguardViewManager;
    public final Resources resources;

    public UdfpsKeyguardAccessibilityDelegate(Resources resources, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.resources = resources;
        this.keyguardViewManager = statusBarKeyguardViewManager;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), this.resources.getString(R.string.accessibility_bouncer)));
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (i != AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
            return super.performAccessibilityAction(view, i, bundle);
        }
        this.keyguardViewManager.showPrimaryBouncer(true);
        return true;
    }
}
