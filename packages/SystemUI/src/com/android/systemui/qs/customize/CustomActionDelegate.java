package com.android.systemui.qs.customize;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomActionDelegate extends View.AccessibilityDelegate {
    public CustomActionManager mCustomActionManager;
    public final CustomActionView mCustomActionView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomActionDelegate(CustomActionView customActionView) {
        this.mCustomActionView = customActionView;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        ArrayList arrayList = ((SecCustomizeTileView) this.mCustomActionView).mIds;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CustomActionId customActionId = (CustomActionId) obj;
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(customActionId.getId(), customActionId.getName(view.getResources())));
        }
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        ArrayList arrayList = ((SecCustomizeTileView) this.mCustomActionView).mIds;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            CustomActionId customActionId = (CustomActionId) obj;
            if (customActionId.getId() == i) {
                Log.d("CustomActionDelegate", "performAccessibilityAction host=" + view + ", action=" + i);
                CustomActionManager customActionManager = this.mCustomActionManager;
                if (customActionManager == null) {
                    return true;
                }
                customActionManager.performAction(view, customActionId);
                return true;
            }
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
