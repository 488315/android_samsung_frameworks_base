package com.android.systemui.p016qs.customize;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.HashMap;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomActionDelegate extends View.AccessibilityDelegate {
    public CustomActionManager mCustomActionManager;
    public final CustomActionView mCustomActionView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        for (CustomActionId customActionId : ((SecCustomizeTileView) this.mCustomActionView).mIds) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(customActionId.getId(), customActionId.getName(view.getResources())));
        }
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        for (CustomActionId customActionId : ((SecCustomizeTileView) this.mCustomActionView).mIds) {
            if (customActionId.getId() == i) {
                Log.d("CustomActionDelegate", "performAccessibilityAction host=" + view + ", action=" + i);
                CustomActionManager customActionManager = this.mCustomActionManager;
                if (customActionManager == null) {
                    return true;
                }
                HashMap hashMap = customActionManager.customActions;
                Consumer consumer = (Consumer) hashMap.get(customActionId);
                Log.d("CustomActionManager", "performAction actionId:" + customActionId + ", view=" + view + ", actions=" + hashMap);
                if (consumer == null) {
                    return true;
                }
                consumer.accept(view);
                return true;
            }
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
