package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.internal.accessibility.dialog.TargetAdapter;
import com.android.internal.accessibility.util.ShortcutUtils;
import java.util.Set;

/* loaded from: classes5.dex */
public class InvisibleToggleAccessibilityServiceTarget extends AccessibilityServiceTarget {
    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget
    public /* bridge */ /* synthetic */ AccessibilityServiceInfo getAccessibilityServiceInfo() {
        return super.getAccessibilityServiceInfo();
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityServiceTarget, com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.TargetOperations
    public /* bridge */ /* synthetic */ void updateActionItem(TargetAdapter.ViewHolder viewHolder, int i) {
        super.updateActionItem(viewHolder, i);
    }

    public InvisibleToggleAccessibilityServiceTarget(Context context, int i, AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 1, accessibilityServiceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        super.onCheckedChanged(z);
        ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(getContext(), Set.of(ComponentName.unflattenFromString(getId()).flattenToString()), UserHandle.myUserId());
    }
}
