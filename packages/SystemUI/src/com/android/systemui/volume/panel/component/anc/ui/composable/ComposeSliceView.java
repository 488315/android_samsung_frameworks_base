package com.android.systemui.volume.panel.component.anc.ui.composable;

import android.content.Context;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.slice.widget.SliceView;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ComposeSliceView extends SliceView {
    public boolean enableAccessibility;
    public OnWidthChangedLayoutListener layoutListener;

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
