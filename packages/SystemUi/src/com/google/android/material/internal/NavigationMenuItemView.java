package com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NavigationMenuItemView extends ForegroundLinearLayout implements MenuView.ItemView {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public final C42991 accessibilityDelegate;
    public FrameLayout actionArea;
    public boolean checkable;
    public Drawable emptyDrawable;
    public boolean hasIconTintList;
    public int iconSize;
    public ColorStateList iconTintList;
    public MenuItemImpl itemData;
    public boolean needsEmptyIcon;
    public final CheckedTextView textView;

    public NavigationMenuItemView(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.itemData;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final void initialize(MenuItemImpl menuItemImpl) {
        StateListDrawable stateListDrawable;
        this.itemData = menuItemImpl;
        int i = menuItemImpl.mId;
        if (i > 0) {
            setId(i);
        }
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(com.android.systemui.R.attr.colorControlHighlight, typedValue, true)) {
                stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(CHECKED_STATE_SET, new ColorDrawable(typedValue.data));
                stateListDrawable.addState(ViewGroup.EMPTY_STATE_SET, new ColorDrawable(0));
            } else {
                stateListDrawable = null;
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(this, stateListDrawable);
        }
        boolean isCheckable = menuItemImpl.isCheckable();
        refreshDrawableState();
        if (this.checkable != isCheckable) {
            this.checkable = isCheckable;
            sendAccessibilityEvent(this.textView, 2048);
        }
        boolean isChecked = menuItemImpl.isChecked();
        refreshDrawableState();
        this.textView.setChecked(isChecked);
        setEnabled(menuItemImpl.isEnabled());
        this.textView.setText(menuItemImpl.mTitle);
        setIcon(menuItemImpl.getIcon());
        View actionView = menuItemImpl.getActionView();
        if (actionView != null) {
            if (this.actionArea == null) {
                this.actionArea = (FrameLayout) ((ViewStub) findViewById(com.android.systemui.R.id.design_menu_item_action_area_stub)).inflate();
            }
            this.actionArea.removeAllViews();
            this.actionArea.addView(actionView);
        }
        setContentDescription(menuItemImpl.mContentDescription);
        setTooltipText(menuItemImpl.mTooltipText);
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2.mTitle == null && menuItemImpl2.getIcon() == null && this.itemData.getActionView() != null) {
            this.textView.setVisibility(8);
            FrameLayout frameLayout = this.actionArea;
            if (frameLayout != null) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) frameLayout.getLayoutParams();
                ((LinearLayout.LayoutParams) layoutParams).width = -1;
                this.actionArea.setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        this.textView.setVisibility(0);
        FrameLayout frameLayout2 = this.actionArea;
        if (frameLayout2 != null) {
            LinearLayoutCompat.LayoutParams layoutParams2 = (LinearLayoutCompat.LayoutParams) frameLayout2.getLayoutParams();
            ((LinearLayout.LayoutParams) layoutParams2).width = -2;
            this.actionArea.setLayoutParams(layoutParams2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            ViewGroup.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public final void setIcon(Drawable drawable) {
        if (drawable != null) {
            if (this.hasIconTintList) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = drawable.mutate();
                drawable.setTintList(this.iconTintList);
            }
            int i = this.iconSize;
            drawable.setBounds(0, 0, i, i);
        } else if (this.needsEmptyIcon) {
            if (this.emptyDrawable == null) {
                Resources resources = getResources();
                Resources.Theme theme = getContext().getTheme();
                ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                Drawable drawable2 = resources.getDrawable(com.android.systemui.R.drawable.navigation_empty_icon, theme);
                this.emptyDrawable = drawable2;
                if (drawable2 != null) {
                    int i2 = this.iconSize;
                    drawable2.setBounds(0, 0, i2, i2);
                }
            }
            drawable = this.emptyDrawable;
        }
        this.textView.setCompoundDrawablesRelative(drawable, null, null, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.core.view.AccessibilityDelegateCompat, com.google.android.material.internal.NavigationMenuItemView$1] */
    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ?? r4 = new AccessibilityDelegateCompat() { // from class: com.google.android.material.internal.NavigationMenuItemView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setCheckable(NavigationMenuItemView.this.checkable);
            }
        };
        this.accessibilityDelegate = r4;
        if (this.mOrientation != 0) {
            this.mOrientation = 0;
            requestLayout();
        }
        LayoutInflater.from(context).inflate(com.android.systemui.R.layout.design_navigation_menu_item, (ViewGroup) this, true);
        this.iconSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.design_navigation_icon_size);
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(com.android.systemui.R.id.design_menu_item_text);
        this.textView = checkedTextView;
        checkedTextView.setDuplicateParentStateEnabled(true);
        ViewCompat.setAccessibilityDelegate(checkedTextView, r4);
    }
}
