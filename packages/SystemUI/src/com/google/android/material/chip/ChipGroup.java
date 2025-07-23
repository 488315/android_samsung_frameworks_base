package com.google.android.material.chip;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.CheckableGroup.AnonymousClass1;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.MaterialCheckable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ChipGroup extends FlowLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CheckableGroup checkableGroup;
    public final int chipSpacingHorizontal;
    public final int chipSpacingVertical;
    public final int defaultCheckedId;
    public final PassThroughHierarchyChangeListener passThroughListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.chip.ChipGroup$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewAdded(View view, View view2) {
            if (view == ChipGroup.this && (view2 instanceof Chip)) {
                if (view2.getId() == -1) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    view2.setId(View.generateViewId());
                }
                CheckableGroup checkableGroup = ChipGroup.this.checkableGroup;
                Chip chip = (Chip) view2;
                ((HashMap) checkableGroup.checkables).put(Integer.valueOf(chip.getId()), chip);
                if (chip.isChecked()) {
                    checkableGroup.checkInternal(chip);
                }
                chip.onCheckedChangeListenerInternal = checkableGroup.new AnonymousClass1();
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.onHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewRemoved(View view, View view2) {
            ChipGroup chipGroup = ChipGroup.this;
            if (view == chipGroup && (view2 instanceof Chip)) {
                CheckableGroup checkableGroup = chipGroup.checkableGroup;
                Chip chip = (Chip) view2;
                checkableGroup.getClass();
                chip.onCheckedChangeListenerInternal = null;
                ((HashMap) checkableGroup.checkables).remove(Integer.valueOf(chip.getId()));
                ((HashSet) checkableGroup.checkedIds).remove(Integer.valueOf(chip.getId()));
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.onHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }

        public /* synthetic */ PassThroughHierarchyChangeListener(ChipGroup chipGroup, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public ChipGroup(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // com.google.android.material.internal.FlowLayout
    public final boolean isSingleLine() {
        return this.singleLine;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = this.defaultCheckedId;
        if (i != -1) {
            CheckableGroup checkableGroup = this.checkableGroup;
            MaterialCheckable materialCheckable = (MaterialCheckable) ((HashMap) checkableGroup.checkables).get(Integer.valueOf(i));
            if (materialCheckable != null && checkableGroup.checkInternal(materialCheckable)) {
                checkableGroup.onCheckedStateChanged();
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        if (this.singleLine) {
            i = 0;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                if ((getChildAt(i2) instanceof Chip) && getChildAt(i2).getVisibility() == 0) {
                    i++;
                }
            }
        } else {
            i = -1;
        }
        wrap.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCount(), i, this.checkableGroup.singleSelection ? 1 : 2));
    }

    @Override // android.view.ViewGroup
    public final void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.passThroughListener.onHierarchyChangeListener = onHierarchyChangeListener;
    }

    public ChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public ChipGroup(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_MaterialComponents_ChipGroup), attributeSet, i);
        CheckableGroup checkableGroup = new CheckableGroup();
        this.checkableGroup = checkableGroup;
        this.passThroughListener = new PassThroughHierarchyChangeListener(this, null);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R$styleable.ChipGroup, i, R.style.Widget_MaterialComponents_ChipGroup, new int[0]);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(2, dimensionPixelOffset);
        if (this.chipSpacingHorizontal != dimensionPixelOffset2) {
            this.chipSpacingHorizontal = dimensionPixelOffset2;
            this.itemSpacing = dimensionPixelOffset2;
            requestLayout();
        }
        int dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(3, dimensionPixelOffset);
        if (this.chipSpacingVertical != dimensionPixelOffset3) {
            this.chipSpacingVertical = dimensionPixelOffset3;
            this.lineSpacing = dimensionPixelOffset3;
            requestLayout();
        }
        this.singleLine = obtainStyledAttributes.getBoolean(5, false);
        boolean z = obtainStyledAttributes.getBoolean(6, false);
        if (checkableGroup.singleSelection != z) {
            checkableGroup.singleSelection = z;
            boolean isEmpty = ((HashSet) checkableGroup.checkedIds).isEmpty();
            Iterator it = ((HashMap) checkableGroup.checkables).values().iterator();
            while (it.hasNext()) {
                checkableGroup.uncheckInternal((MaterialCheckable) it.next(), false);
            }
            if (!isEmpty) {
                checkableGroup.onCheckedStateChanged();
            }
        }
        this.checkableGroup.selectionRequired = obtainStyledAttributes.getBoolean(4, false);
        this.defaultCheckedId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        this.checkableGroup.onCheckedStateChangeListener = new AnonymousClass1();
        super.setOnHierarchyChangeListener(this.passThroughListener);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(1);
    }
}
