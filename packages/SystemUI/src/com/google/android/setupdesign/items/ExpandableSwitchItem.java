package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.view.CheckableLinearLayout;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExpandableSwitchItem extends SwitchItem implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public final AnonymousClass1 accessibilityDelegate;
    public final boolean canExpanded;
    public final CharSequence collapsedSummary;
    public final CharSequence expandedSummary;
    public boolean isExpanded;
    public final boolean isSwitchItem;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem() {
        this.isExpanded = false;
        this.canExpanded = true;
        this.isSwitchItem = true;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableSwitchItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(ExpandableSwitchItem.this.isExpanded ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableSwitchItem expandableSwitchItem = ExpandableSwitchItem.this;
                boolean z = expandableSwitchItem.isExpanded;
                boolean z2 = !z;
                if (z != z2) {
                    expandableSwitchItem.isExpanded = z2;
                    ArrayList arrayList = expandableSwitchItem.observers;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
                    }
                }
                return true;
            }
        };
        this.iconGravity = 48;
    }

    @Override // com.google.android.setupdesign.items.SwitchItem, com.google.android.setupdesign.items.Item
    public final int getDefaultLayoutResource() {
        return R.layout.sud_items_expandable_switch;
    }

    @Override // com.google.android.setupdesign.items.Item
    public final CharSequence getSummary() {
        return this.isExpanded ? this.expandedSummary : this.collapsedSummary;
    }

    @Override // com.google.android.setupdesign.items.SwitchItem, com.google.android.setupdesign.items.Item, com.google.android.setupdesign.items.AbstractItem
    public final void onBindView(View view) {
        super.onBindView(view);
        view.setClickable(false);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(view.getContext())) {
            View findViewById = view.findViewById(R.id.sud_items_more_info);
            if (findViewById != null) {
                if (this.canExpanded) {
                    findViewById.setOnClickListener(this);
                } else {
                    findViewById.setVisibility(8);
                }
            }
            View findViewById2 = view.findViewById(R.id.sud_items_switch);
            if (!this.isSwitchItem && findViewById2 != null) {
                findViewById2.setVisibility(8);
            }
        } else {
            View findViewById3 = view.findViewById(R.id.sud_items_expandable_switch_content);
            findViewById3.setOnClickListener(this);
            if (findViewById3 instanceof CheckableLinearLayout) {
                CheckableLinearLayout checkableLinearLayout = (CheckableLinearLayout) findViewById3;
                checkableLinearLayout.setChecked(this.isExpanded);
                checkableLinearLayout.setAccessibilityLiveRegion(this.isExpanded ? 1 : 0);
                ViewCompat.setAccessibilityDelegate(checkableLinearLayout, this.accessibilityDelegate);
            }
            LayoutStyler.applyPartnerCustomizationLayoutPaddingStyle(findViewById3);
        }
        TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary});
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(0);
        obtainStyledAttributes.recycle();
        if (colorStateList != null) {
            TextView textView = (TextView) view.findViewById(R.id.sud_items_title);
            for (Drawable drawable : textView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(colorStateList.getDefaultColor(), PorterDuff.Mode.SRC_IN);
                }
            }
            for (Drawable drawable2 : textView.getCompoundDrawablesRelative()) {
                if (drawable2 != null) {
                    drawable2.setColorFilter(colorStateList.getDefaultColor(), PorterDuff.Mode.SRC_IN);
                }
            }
        }
        view.setFocusable(false);
        TextView textView2 = (TextView) view.findViewById(R.id.sud_items_more_info);
        if (textView2 != null) {
            if (this.isExpanded) {
                textView2.setText(R.string.sud_less_info);
            } else {
                textView2.setText(R.string.sud_more_info);
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(view.getContext())) {
            boolean z = this.isExpanded;
            boolean z2 = !z;
            if (z == z2) {
                return;
            }
            this.isExpanded = z2;
            ArrayList arrayList = this.observers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
            }
            return;
        }
        if (view.getId() == R.id.sud_items_more_info) {
            boolean z3 = this.isExpanded;
            boolean z4 = !z3;
            if (z3 != z4) {
                this.isExpanded = z4;
                ArrayList arrayList2 = this.observers;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    ((RecyclerItemAdapter) obj2).mObservable.notifyItemRangeChanged(0, 1, null);
                }
            }
            TextView textView = (TextView) view.findViewById(R.id.sud_items_more_info);
            if (textView != null) {
                if (this.isExpanded) {
                    textView.setText(R.string.sud_less_info);
                } else {
                    textView.setText(R.string.sud_more_info);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem(Context context) {
        this.isExpanded = false;
        this.canExpanded = true;
        this.isSwitchItem = true;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableSwitchItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(ExpandableSwitchItem.this.isExpanded ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableSwitchItem expandableSwitchItem = ExpandableSwitchItem.this;
                boolean z = expandableSwitchItem.isExpanded;
                boolean z2 = !z;
                if (z != z2) {
                    expandableSwitchItem.isExpanded = z2;
                    ArrayList arrayList = expandableSwitchItem.observers;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
                    }
                }
                return true;
            }
        };
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
            this.iconGravity = 48;
            return;
        }
        this.layoutRes = R.layout.sud_items_expandable_switch_expressive;
        ArrayList arrayList = this.observers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
        }
        this.iconGravity = 16;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isExpanded = false;
        this.canExpanded = true;
        this.isSwitchItem = true;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableSwitchItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(ExpandableSwitchItem.this.isExpanded ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableSwitchItem expandableSwitchItem = ExpandableSwitchItem.this;
                boolean z = expandableSwitchItem.isExpanded;
                boolean z2 = !z;
                if (z != z2) {
                    expandableSwitchItem.isExpanded = z2;
                    ArrayList arrayList = expandableSwitchItem.observers;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
                    }
                }
                return true;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudExpandableSwitchItem);
        this.collapsedSummary = obtainStyledAttributes.getText(0);
        this.expandedSummary = obtainStyledAttributes.getText(1);
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
            this.iconGravity = obtainStyledAttributes.getInt(7, 48);
        } else {
            this.layoutRes = R.layout.sud_items_expandable_switch_expressive;
            ArrayList arrayList = this.observers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
            }
            this.iconGravity = obtainStyledAttributes.getInt(7, 16);
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        super(context, attributeSet);
        this.isExpanded = false;
        this.canExpanded = true;
        this.isSwitchItem = true;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableSwitchItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(ExpandableSwitchItem.this.isExpanded ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableSwitchItem expandableSwitchItem = ExpandableSwitchItem.this;
                boolean z3 = expandableSwitchItem.isExpanded;
                boolean z22 = !z3;
                if (z3 != z22) {
                    expandableSwitchItem.isExpanded = z22;
                    ArrayList arrayList = expandableSwitchItem.observers;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
                    }
                }
                return true;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudExpandableSwitchItem);
        this.collapsedSummary = obtainStyledAttributes.getText(0);
        this.expandedSummary = obtainStyledAttributes.getText(1);
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
            this.iconGravity = obtainStyledAttributes.getInt(7, 48);
        } else {
            this.layoutRes = R.layout.sud_items_expandable_switch_expressive;
            ArrayList arrayList = this.observers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((RecyclerItemAdapter) obj).mObservable.notifyItemRangeChanged(0, 1, null);
            }
            this.iconGravity = obtainStyledAttributes.getInt(7, 16);
        }
        obtainStyledAttributes.recycle();
        this.isSwitchItem = z;
        this.canExpanded = z2;
    }
}
