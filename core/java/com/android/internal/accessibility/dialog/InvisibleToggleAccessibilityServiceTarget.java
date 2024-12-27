package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;

import com.android.internal.accessibility.util.ShortcutUtils;

import java.util.Set;

/* loaded from: classes5.dex */
public class InvisibleToggleAccessibilityServiceTarget extends AccessibilityServiceTarget {
    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget
    public /* bridge */ /* synthetic */ AccessibilityServiceInfo getAccessibilityServiceInfo() {
        return super.getAccessibilityServiceInfo();
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget,
              // com.android.internal.accessibility.dialog.AccessibilityTarget,
              // com.android.internal.accessibility.dialog.TargetOperations
    public /* bridge */ /* synthetic */ void updateActionItem(
            TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
    }

    public InvisibleToggleAccessibilityServiceTarget(
            Context context, int shortcutType, AccessibilityServiceInfo serviceInfo) {
        super(context, shortcutType, 1, serviceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
              // com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean isChecked) {
        super.onCheckedChanged(isChecked);
        ComponentName componentName = ComponentName.unflattenFromString(getId());
        ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(
                getContext(), Set.of(componentName.flattenToString()), UserHandle.myUserId());
    }
}
