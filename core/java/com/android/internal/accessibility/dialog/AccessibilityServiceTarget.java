package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import com.android.internal.accessibility.util.ShortcutUtils;

/* loaded from: classes4.dex */
class AccessibilityServiceTarget extends AccessibilityTarget {
  AccessibilityServiceTarget(
      Context context, int shortcutType, int fragmentType, AccessibilityServiceInfo serviceInfo) {
    super(
        context,
        shortcutType,
        fragmentType,
        ShortcutUtils.isShortcutContained(
            context, shortcutType, serviceInfo.getComponentName().flattenToString()),
        serviceInfo.getComponentName().flattenToString(),
        serviceInfo.getResolveInfo().serviceInfo.applicationInfo.uid,
        serviceInfo.getResolveInfo().loadLabel(context.getPackageManager()),
        serviceInfo.getResolveInfo().loadIcon(context.getPackageManager()),
        ShortcutUtils.convertToKey(ShortcutUtils.convertToUserType(shortcutType)));
  }

  @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
            // com.android.internal.accessibility.dialog.TargetOperations
  public void updateActionItem(TargetAdapter.ViewHolder holder, int shortcutMenuMode) {
    super.updateActionItem(holder, shortcutMenuMode);
  }
}
