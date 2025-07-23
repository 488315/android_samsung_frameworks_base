package com.google.android.material.bottomsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.android.systemui.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BottomSheetDragHandleView extends AppCompatImageView implements AccessibilityManager.AccessibilityStateChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public boolean accessibilityServiceEnabled;
    public BottomSheetBehavior bottomSheetBehavior;
    public final AnonymousClass1 bottomSheetCallback;
    public final String clickFeedback;
    public final String clickToCollapseActionLabel;
    public boolean clickToExpand;
    public final String clickToExpandActionLabel;
    public boolean interactable;

    public BottomSheetDragHandleView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002a, code lost:
    
        if (r1 == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean expandOrCollapseBottomSheetIfPossible() {
        /*
            r6 = this;
            boolean r0 = r6.interactable
            if (r0 != 0) goto L6
            r6 = 0
            return r6
        L6:
            java.lang.String r0 = r6.clickFeedback
            android.view.accessibility.AccessibilityManager r1 = r6.accessibilityManager
            if (r1 != 0) goto Ld
            goto L1f
        Ld:
            r1 = 16384(0x4000, float:2.2959E-41)
            android.view.accessibility.AccessibilityEvent r1 = android.view.accessibility.AccessibilityEvent.obtain(r1)
            java.util.List r2 = r1.getText()
            r2.add(r0)
            android.view.accessibility.AccessibilityManager r0 = r6.accessibilityManager
            r0.sendAccessibilityEvent(r1)
        L1f:
            com.google.android.material.bottomsheet.BottomSheetBehavior r0 = r6.bottomSheetBehavior
            boolean r1 = r0.fitToContents
            int r2 = r0.state
            r3 = 6
            r4 = 3
            r5 = 4
            if (r2 != r5) goto L2d
            if (r1 != 0) goto L3a
            goto L3b
        L2d:
            if (r2 != r4) goto L34
            if (r1 != 0) goto L32
            goto L3b
        L32:
            r3 = r5
            goto L3b
        L34:
            boolean r6 = r6.clickToExpand
            if (r6 == 0) goto L39
            goto L3a
        L39:
            r4 = r5
        L3a:
            r3 = r4
        L3b:
            r0.setState$1(r3)
            r6 = 1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetDragHandleView.expandOrCollapseBottomSheetIfPossible():boolean");
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
    public final void onAccessibilityStateChanged(boolean z) {
        this.accessibilityServiceEnabled = z;
        updateInteractableState();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        BottomSheetBehavior bottomSheetBehavior;
        super.onAttachedToWindow();
        View view = this;
        while (true) {
            Object parent = view.getParent();
            bottomSheetBehavior = null;
            view = parent instanceof View ? (View) parent : null;
            if (view == null) {
                break;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior;
                if (behavior instanceof BottomSheetBehavior) {
                    bottomSheetBehavior = (BottomSheetBehavior) behavior;
                    break;
                }
            }
        }
        setBottomSheetBehavior(bottomSheetBehavior);
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.addAccessibilityStateChangeListener(this);
            onAccessibilityStateChanged(this.accessibilityManager.isEnabled());
        }
    }

    public final void onBottomSheetStateChanged(int i) {
        if (i == 4) {
            this.clickToExpand = true;
        } else if (i == 3) {
            this.clickToExpand = false;
        }
        ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.clickToExpand ? this.clickToExpandActionLabel : this.clickToCollapseActionLabel, new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetDragHandleView$$ExternalSyntheticLambda0
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public final boolean perform(View view) {
                int i2 = BottomSheetDragHandleView.$r8$clinit;
                return BottomSheetDragHandleView.this.expandOrCollapseBottomSheetIfPossible();
            }
        });
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.removeAccessibilityStateChangeListener(this);
        }
        setBottomSheetBehavior(null);
        super.onDetachedFromWindow();
    }

    public final void setBottomSheetBehavior(BottomSheetBehavior bottomSheetBehavior) {
        BottomSheetBehavior bottomSheetBehavior2 = this.bottomSheetBehavior;
        if (bottomSheetBehavior2 != null) {
            bottomSheetBehavior2.callbacks.remove(this.bottomSheetCallback);
            this.bottomSheetBehavior.setAccessibilityDelegateView(null);
        }
        this.bottomSheetBehavior = bottomSheetBehavior;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setAccessibilityDelegateView(this);
            onBottomSheetStateChanged(this.bottomSheetBehavior.state);
            BottomSheetBehavior bottomSheetBehavior3 = this.bottomSheetBehavior;
            AnonymousClass1 anonymousClass1 = this.bottomSheetCallback;
            if (!bottomSheetBehavior3.callbacks.contains(anonymousClass1)) {
                bottomSheetBehavior3.callbacks.add(anonymousClass1);
            }
        }
        updateInteractableState();
    }

    public final void updateInteractableState() {
        this.interactable = this.accessibilityServiceEnabled && this.bottomSheetBehavior != null;
        int i = this.bottomSheetBehavior == null ? 2 : 1;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(i);
        setClickable(this.interactable);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomSheetDragHandleStyle);
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [com.google.android.material.bottomsheet.BottomSheetDragHandleView$1] */
    public BottomSheetDragHandleView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Material3_BottomSheet_DragHandle), attributeSet, i);
        this.clickToExpandActionLabel = getResources().getString(R.string.bottomsheet_action_expand);
        this.clickToCollapseActionLabel = getResources().getString(R.string.bottomsheet_action_collapse);
        this.clickFeedback = getResources().getString(R.string.bottomsheet_drag_handle_clicked);
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDragHandleView.1
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public final void onStateChanged(int i2) {
                int i3 = BottomSheetDragHandleView.$r8$clinit;
                BottomSheetDragHandleView.this.onBottomSheetStateChanged(i2);
            }
        };
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        updateInteractableState();
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.google.android.material.bottomsheet.BottomSheetDragHandleView.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                super.onPopulateAccessibilityEvent(view, accessibilityEvent);
                if (accessibilityEvent.getEventType() == 1) {
                    int i2 = BottomSheetDragHandleView.$r8$clinit;
                    BottomSheetDragHandleView.this.expandOrCollapseBottomSheetIfPossible();
                }
            }
        });
    }
}
