package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes5.dex */
class AccessibilityServiceTarget extends AccessibilityTarget {
    private final AccessibilityServiceInfo mAccessibilityServiceInfo;

    AccessibilityServiceTarget(Context context, int i, int i2, AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, i2, ShortcutUtils.isShortcutContained(context, i, accessibilityServiceInfo.getComponentName().flattenToString()), accessibilityServiceInfo.getComponentName().flattenToString(), accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid, accessibilityServiceInfo.getResolveInfo().loadLabel(context.getPackageManager()), accessibilityServiceInfo.getResolveInfo().loadIcon(context.getPackageManager()), ShortcutUtils.convertToKey(i));
        this.mAccessibilityServiceInfo = accessibilityServiceInfo;
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
    }

    public AccessibilityServiceInfo getAccessibilityServiceInfo() {
        return this.mAccessibilityServiceInfo;
    }
}
