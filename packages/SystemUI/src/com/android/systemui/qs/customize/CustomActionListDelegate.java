package com.android.systemui.qs.customize;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class CustomActionListDelegate extends View.AccessibilityDelegate {
    public final String TAG = "CustomActionDelegate";
    public final List customActionList;
    public CustomActionManager customActionManager;

    public CustomActionListDelegate(List<? extends CustomActionId> list) {
        this.customActionList = list;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        for (CustomActionId customActionId : this.customActionList) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(customActionId.getId(), customActionId.getName(view.getResources())));
        }
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        Object next;
        Iterator it = this.customActionList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((CustomActionId) next).getId() == i) {
                break;
            }
        }
        CustomActionId customActionId = (CustomActionId) next;
        if (customActionId != null) {
            Log.d(this.TAG, "performAccessibilityAction host=" + view + ", action=" + i);
            CustomActionManager customActionManager = this.customActionManager;
            if (customActionManager != null) {
                customActionManager.performAction(view, customActionId);
            }
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
