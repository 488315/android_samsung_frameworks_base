package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.view.CheckableLinearLayout;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ExpandableSwitchItem extends SwitchItem implements View.OnClickListener {
    public final C44191 accessibilityDelegate;
    public final CharSequence collapsedSummary;
    public final CharSequence expandedSummary;
    public boolean isExpanded;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem() {
        this.isExpanded = false;
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
                    expandableSwitchItem.notifyItemRangeChanged(0, 1);
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
        View findViewById = view.findViewById(R.id.sud_items_expandable_switch_content);
        findViewById.setOnClickListener(this);
        if (findViewById instanceof CheckableLinearLayout) {
            CheckableLinearLayout checkableLinearLayout = (CheckableLinearLayout) findViewById;
            checkableLinearLayout.setChecked(this.isExpanded);
            boolean z = this.isExpanded;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api19Impl.setAccessibilityLiveRegion(checkableLinearLayout, z ? 1 : 0);
            ViewCompat.setAccessibilityDelegate(checkableLinearLayout, this.accessibilityDelegate);
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
        LayoutStyler.applyPartnerCustomizationLayoutPaddingStyle(findViewById);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        boolean z = this.isExpanded;
        boolean z2 = !z;
        if (z == z2) {
            return;
        }
        this.isExpanded = z2;
        notifyItemRangeChanged(0, 1);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.setupdesign.items.ExpandableSwitchItem$1] */
    public ExpandableSwitchItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isExpanded = false;
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
                    expandableSwitchItem.notifyItemRangeChanged(0, 1);
                }
                return true;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudExpandableSwitchItem);
        this.collapsedSummary = obtainStyledAttributes.getText(0);
        this.expandedSummary = obtainStyledAttributes.getText(1);
        this.iconGravity = obtainStyledAttributes.getInt(7, 48);
        obtainStyledAttributes.recycle();
    }
}
