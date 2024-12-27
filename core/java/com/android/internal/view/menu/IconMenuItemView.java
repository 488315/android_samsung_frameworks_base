package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewDebug;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.IconMenuView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuView;

/* loaded from: classes5.dex */
public final class IconMenuItemView extends TextView implements MenuView.ItemView {
    private static final int NO_ALPHA = 255;
    private static String sPrependShortcutLabel;
    private float mDisabledAlpha;
    private Drawable mIcon;
    private IconMenuView mIconMenuView;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private Rect mPositionIconAvailable;
    private Rect mPositionIconOutput;
    private String mShortcutCaption;
    private boolean mShortcutCaptionMode;
    private int mTextAppearance;
    private Context mTextAppearanceContext;

    public IconMenuItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPositionIconAvailable = new Rect();
        this.mPositionIconOutput = new Rect();
        if (sPrependShortcutLabel == null) {
            sPrependShortcutLabel = getResources().getString(R.string.prepend_shortcut_label);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuView, defStyleAttr, defStyleRes);
        this.mDisabledAlpha = a.getFloat(6, 0.8f);
        this.mTextAppearance = a.getResourceId(1, -1);
        this.mTextAppearanceContext = context;
        a.recycle();
    }

    public IconMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public IconMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    void initialize(CharSequence title, Drawable icon) {
        setClickable(true);
        setFocusable(true);
        if (this.mTextAppearance != -1) {
            setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
        }
        setTitle(title);
        setIcon(icon);
        if (this.mItemData != null) {
            CharSequence contentDescription = this.mItemData.getContentDescription();
            if (TextUtils.isEmpty(contentDescription)) {
                setContentDescription(title);
            } else {
                setContentDescription(contentDescription);
            }
            setTooltipText(this.mItemData.getTooltipText());
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        initialize(itemData.getTitleForItemView(this), itemData.getIcon());
        setVisibility(itemData.isVisible() ? 0 : 8);
        setEnabled(itemData.isEnabled());
    }

    public void setItemData(MenuItemImpl data) {
        this.mItemData = data;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        if (this.mItemInvoker == null || !this.mItemInvoker.invokeItem(this.mItemData)) {
            return false;
        }
        playSoundEffect(0);
        return true;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setTitle(CharSequence title) {
        if (this.mShortcutCaptionMode) {
            setCaptionMode(true);
        } else if (title != null) {
            lambda$setTextAsync$0(title);
        }
    }

    void setCaptionMode(boolean shortcut) {
        if (this.mItemData == null) {
            return;
        }
        this.mShortcutCaptionMode = shortcut && this.mItemData.shouldShowShortcut();
        CharSequence text = this.mItemData.getTitleForItemView(this);
        if (this.mShortcutCaptionMode) {
            if (this.mShortcutCaption == null) {
                this.mShortcutCaption = this.mItemData.getShortcutLabel();
            }
            text = this.mShortcutCaption;
        }
        lambda$setTextAsync$0(text);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        if (icon != null) {
            icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            setCompoundDrawables(null, icon, null, null);
            setGravity(81);
            requestLayout();
            return;
        }
        setCompoundDrawables(null, null, null, null);
        setGravity(17);
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    @ViewDebug.CapturedViewProperty(retrieveReturn = true)
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // android.view.View
    public void setVisibility(int v) {
        super.setVisibility(v);
        if (this.mIconMenuView != null) {
            this.mIconMenuView.markStaleChildren();
        }
    }

    void setIconMenuView(IconMenuView iconMenuView) {
        this.mIconMenuView = iconMenuView;
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mItemData != null && this.mIcon != null) {
            boolean isInAlphaState = !this.mItemData.isEnabled() && (isPressed() || !isFocused());
            this.mIcon.setAlpha(isInAlphaState ? (int) (this.mDisabledAlpha * 255.0f) : 255);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        positionIcon();
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        setLayoutParams(getTextAppropriateLayoutParams());
    }

    IconMenuView.LayoutParams getTextAppropriateLayoutParams() {
        IconMenuView.LayoutParams lp = (IconMenuView.LayoutParams) getLayoutParams();
        if (lp == null) {
            lp = new IconMenuView.LayoutParams(-1, -1);
        }
        lp.desiredWidth = (int) Layout.getDesiredWidth(getText(), 0, getText().length(), getPaint(), getTextDirectionHeuristic());
        return lp;
    }

    private void positionIcon() {
        if (this.mIcon == null) {
            return;
        }
        Rect tmpRect = this.mPositionIconOutput;
        getLineBounds(0, tmpRect);
        this.mPositionIconAvailable.set(0, 0, getWidth(), tmpRect.top);
        int layoutDirection = getLayoutDirection();
        Gravity.apply(8388627, this.mIcon.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight(), this.mPositionIconAvailable, this.mPositionIconOutput, layoutDirection);
        this.mIcon.setBounds(this.mPositionIconOutput);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setCheckable(boolean checkable) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setChecked(boolean checked) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean showShortcut, char shortcutKey) {
        if (this.mShortcutCaptionMode) {
            this.mShortcutCaption = null;
            setCaptionMode(true);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }
}
