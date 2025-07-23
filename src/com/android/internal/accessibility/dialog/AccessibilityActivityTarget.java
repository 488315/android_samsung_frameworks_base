package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes5.dex */
class AccessibilityActivityTarget extends AccessibilityTarget {
    AccessibilityActivityTarget(Context context, int i, AccessibilityShortcutInfo accessibilityShortcutInfo) {
        super(context, i, 3, ShortcutUtils.isShortcutContained(context, i, accessibilityShortcutInfo.getComponentName().flattenToString()), accessibilityShortcutInfo.getComponentName().flattenToString(), accessibilityShortcutInfo.getActivityInfo().applicationInfo.uid, accessibilityShortcutInfo.getActivityInfo().loadLabel(context.getPackageManager()), accessibilityShortcutInfo.getActivityInfo().loadIcon(context.getPackageManager()), ShortcutUtils.convertToKey(i));
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
    }
}
