package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.Context;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes4.dex */
class AccessibilityActivityTarget extends AccessibilityTarget {
  AccessibilityActivityTarget(
      Context context, int shortcutType, AccessibilityShortcutInfo shortcutInfo) {
    super(
        context,
        shortcutType,
        3,
        ShortcutUtils.isShortcutContained(
            context, shortcutType, shortcutInfo.getComponentName().flattenToString()),
        shortcutInfo.getComponentName().flattenToString(),
        shortcutInfo.getActivityInfo().applicationInfo.uid,
        shortcutInfo.getActivityInfo().loadLabel(context.getPackageManager()),
        shortcutInfo.getActivityInfo().loadIcon(context.getPackageManager()),
        ShortcutUtils.convertToKey(ShortcutUtils.convertToUserType(shortcutType)));
  }

  @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
            // com.android.internal.accessibility.dialog.TargetOperations
  public void updateActionItem(TargetAdapter.ViewHolder holder, int shortcutMenuMode) {
    super.updateActionItem(holder, shortcutMenuMode);
  }
}
