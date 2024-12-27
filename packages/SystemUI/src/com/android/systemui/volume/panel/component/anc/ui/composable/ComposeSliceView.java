package com.android.systemui.volume.panel.component.anc.ui.composable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.slice.widget.SliceView;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComposeSliceView extends SliceView {
    public boolean enableAccessibility;
    public View.OnLayoutChangeListener layoutListener;

    public ComposeSliceView(Context context) {
        super(context);
        this.enableAccessibility = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addChildrenForAccessibility(ArrayList arrayList) {
        if (this.enableAccessibility) {
            super.addChildrenForAccessibility(arrayList);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.enableAccessibility) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.enableAccessibility) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        if (this.enableAccessibility) {
            return super.performAccessibilityAction(i, bundle);
        }
        return false;
    }
}
