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
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BottomSheetDragHandleView extends AppCompatImageView implements AccessibilityManager.AccessibilityStateChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public boolean accessibilityServiceEnabled;
    public BottomSheetBehavior bottomSheetBehavior;
    public final C42381 bottomSheetCallback;
    public final String clickFeedback;
    public final String clickToCollapseActionLabel;
    public boolean clickToExpand;
    public final String clickToExpandActionLabel;
    public boolean interactable;

    public BottomSheetDragHandleView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
    
        if (r1 != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean expandOrCollapseBottomSheetIfPossible() {
        boolean z = false;
        if (!this.interactable) {
            return false;
        }
        String str = this.clickFeedback;
        if (this.accessibilityManager != null) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
            obtain.getText().add(str);
            this.accessibilityManager.sendAccessibilityEvent(obtain);
        }
        BottomSheetBehavior bottomSheetBehavior = this.bottomSheetBehavior;
        if (!bottomSheetBehavior.fitToContents) {
            bottomSheetBehavior.getClass();
            z = true;
        }
        BottomSheetBehavior bottomSheetBehavior2 = this.bottomSheetBehavior;
        int i = bottomSheetBehavior2.state;
        int i2 = 6;
        int i3 = 3;
        if (i != 4) {
            if (i == 3) {
                if (!z) {
                    i2 = 4;
                }
                bottomSheetBehavior2.setState(i2);
                return true;
            }
            if (!this.clickToExpand) {
                i3 = 4;
            }
            i2 = i3;
            bottomSheetBehavior2.setState(i2);
            return true;
        }
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
    public final void onAccessibilityStateChanged(boolean z) {
        this.accessibilityServiceEnabled = z;
        updateInteractableState();
    }

    @Override // android.widget.ImageView, android.view.View
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
        }
        this.bottomSheetBehavior = bottomSheetBehavior;
        if (bottomSheetBehavior != null) {
            onBottomSheetStateChanged(bottomSheetBehavior.state);
            BottomSheetBehavior bottomSheetBehavior3 = this.bottomSheetBehavior;
            C42381 c42381 = this.bottomSheetCallback;
            ArrayList arrayList = bottomSheetBehavior3.callbacks;
            if (!arrayList.contains(c42381)) {
                arrayList.add(c42381);
            }
        }
        updateInteractableState();
    }

    public final void updateInteractableState() {
        this.interactable = this.accessibilityServiceEnabled && this.bottomSheetBehavior != null;
        int i = this.bottomSheetBehavior == null ? 2 : 1;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, i);
        setClickable(this.interactable);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomSheetDragHandleStyle);
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [com.google.android.material.bottomsheet.BottomSheetDragHandleView$1] */
    public BottomSheetDragHandleView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018930), attributeSet, i);
        this.clickToExpandActionLabel = getResources().getString(R.string.bottomsheet_action_expand);
        this.clickToCollapseActionLabel = getResources().getString(R.string.bottomsheet_action_collapse);
        this.clickFeedback = getResources().getString(R.string.bottomsheet_drag_handle_clicked);
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDragHandleView.1
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public final void onStateChanged(View view, int i2) {
                int i3 = BottomSheetDragHandleView.$r8$clinit;
                BottomSheetDragHandleView.this.onBottomSheetStateChanged(i2);
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public final void onSlide(View view) {
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
