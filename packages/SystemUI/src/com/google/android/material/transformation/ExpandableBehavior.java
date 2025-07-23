package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.expandable.ExpandableWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Deprecated
/* loaded from: classes4.dex */
public abstract class ExpandableBehavior extends CoordinatorLayout.Behavior {
    public int currentState;

    public ExpandableBehavior() {
        this.currentState = 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public abstract boolean layoutDependsOn(View view, View view2);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        Object obj = (ExpandableWidget) view2;
        boolean z = ((FloatingActionButton) obj).expandableWidgetHelper.expanded;
        if (z) {
            int i = this.currentState;
            if (i != 0 && i != 2) {
                return false;
            }
        } else if (this.currentState != 1) {
            return false;
        }
        this.currentState = z ? 1 : 2;
        onExpandedStateChange((View) obj, view, z, true);
        return true;
    }

    public abstract void onExpandedStateChange(View view, View view2, boolean z, boolean z2);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, final View view, int i) {
        final ExpandableWidget expandableWidget;
        boolean z;
        int i2;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!view.isLaidOut()) {
            List dependencies = coordinatorLayout.getDependencies(view);
            int size = dependencies.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    expandableWidget = null;
                    break;
                }
                View view2 = (View) dependencies.get(i3);
                if (layoutDependsOn(view, view2)) {
                    expandableWidget = (ExpandableWidget) view2;
                    break;
                }
                i3++;
            }
            if (expandableWidget != null && (!(z = ((FloatingActionButton) expandableWidget).expandableWidgetHelper.expanded) ? this.currentState == 1 : !((i2 = this.currentState) != 0 && i2 != 2))) {
                final int i4 = z ? 1 : 2;
                this.currentState = i4;
                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.transformation.ExpandableBehavior.1
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        ExpandableBehavior expandableBehavior = ExpandableBehavior.this;
                        if (expandableBehavior.currentState == i4) {
                            Object obj = expandableWidget;
                            expandableBehavior.onExpandedStateChange((View) obj, view, ((FloatingActionButton) obj).expandableWidgetHelper.expanded, false);
                        }
                        return false;
                    }
                });
            }
        }
        return false;
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentState = 0;
    }
}
