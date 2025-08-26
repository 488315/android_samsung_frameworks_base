package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ForwardingListener;
import android.widget.TextView;
import android.widget.Toolbar;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuView;

/* loaded from: classes4.dex */
public class ActionMenuItemView extends TextView implements MenuView.ItemView, View.OnClickListener, ActionMenuView.ActionMenuChildView, View.OnLongClickListener {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private float mDefaultTextSize;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    private boolean mIsChangedRelativePadding;
    private boolean mIsDarkTheme;
    private boolean mIsThemeDeviceDefaultFamily;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private float mMaxFontScale;
    private int mMaxIconSize;
    private int mMinWidth;
    private int mNavigationBarHeight;
    private PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c) {
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mIsChangedRelativePadding = false;
        this.mIsDarkTheme = false;
        this.mNavigationBarHeight = 0;
        this.mDefaultTextSize = 0.0f;
        this.mMaxFontScale = 1.3f;
        Resources resources = context.getResources();
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, i, i2);
        this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mMaxIconSize = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.mSavedPaddingLeft = -1;
        setSaveEnabled(false);
        TypedValue typedValue = new TypedValue();
        setOnLongClickListener(this);
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsThemeDeviceDefaultFamily = typedValue.data != 0;
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
        this.mIsDarkTheme = typedValue.data != 0;
        if (this.mIsThemeDeviceDefaultFamily) {
            TypedArray typedArrayObtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(null, R.styleable.Theme, 0, 0);
            int resourceId = typedArrayObtainStyledAttributes2.getResourceId(187, 0);
            typedArrayObtainStyledAttributes2.recycle();
            TypedArray typedArrayObtainStyledAttributes3 = getContext().obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes3.peekValue(0);
            typedArrayObtainStyledAttributes3.recycle();
            if (typedValuePeekValue != null) {
                this.mDefaultTextSize = TypedValue.complexToFloat(typedValuePeekValue.data);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.View, 16843480, 0);
        setMinHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(37, -1));
        typedArrayObtainStyledAttributes.recycle();
        this.mMaxIconSize = (int) ((getContext().getResources().getDisplayMetrics().density * 32.0f) + 0.5f);
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        int i2 = configuration.screenHeightDp;
        if (i < 480) {
            return (i >= 640 && i2 >= 480) || configuration.orientation == 2;
        }
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        this.mIsChangedRelativePadding = true;
        super.setPaddingRelative(i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        super.setPadding(i, i2, i3, i4);
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        setIcon(menuItemImpl.getIcon());
        setTitle(menuItemImpl.getTitleForItemView(this));
        setId(menuItemImpl.getItemId());
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu() && !this.mIsThemeDeviceDefaultFamily && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener;
        if (this.mItemData.hasSubMenu() && (forwardingListener = this.mForwardingListener) != null && forwardingListener.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ShowableListMenu popup;
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker != null) {
            itemInvoker.invokeItem(this.mItemData);
        }
        PopupCallback popupCallback = this.mPopupCallback;
        if (popupCallback == null || (popup = popupCallback.getPopup()) == null || !popup.isShowing()) {
            return;
        }
        view.setTooltipNull(true);
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    public void setExpandedFormat(boolean z) {
        if (this.mExpandedFormat != z) {
            this.mExpandedFormat = z;
            MenuItemImpl menuItemImpl = this.mItemData;
            if (menuItemImpl != null) {
                menuItemImpl.actionFormatChanged();
            }
        }
    }

    private void updateTextButtonVisibility() {
        boolean z = (!TextUtils.isEmpty(this.mTitle)) & (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat)));
        if (this.mIsThemeDeviceDefaultFamily && this.mDefaultTextSize > 0.0f) {
            float f = getContext().getResources().getConfiguration().fontScale;
            float f2 = this.mMaxFontScale;
            if (f > f2) {
                f = f2;
            }
            setTextSize(1, this.mDefaultTextSize * f);
        }
        lambda$setTextAsync$0(z ? this.mTitle : null);
        if (z) {
            if (this.mIsDarkTheme) {
                setBackgroundResource(R.drawable.sem_action_item_background_text_material_dark);
            } else {
                setBackgroundResource(R.drawable.sem_action_item_background_text_material);
            }
        }
        semSetButtonShapeEnabled(z);
        CharSequence contentDescription = this.mItemData.getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            setContentDescription(z ? null : this.mItemData.getTitle());
        } else {
            setContentDescription(contentDescription);
        }
        CharSequence tooltipText = this.mItemData.getTooltipText();
        if (TextUtils.isEmpty(tooltipText)) {
            setTooltipText(z ? null : this.mItemData.getTitle());
        } else {
            setTooltipText(tooltipText);
        }
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i = this.mMaxIconSize;
            if (intrinsicWidth > i) {
                intrinsicHeight = (int) (intrinsicHeight * (i / intrinsicWidth));
                intrinsicWidth = i;
            }
            if (intrinsicHeight > i) {
                intrinsicWidth = (int) (intrinsicWidth * (i / intrinsicHeight));
            } else {
                i = intrinsicHeight;
            }
            drawable.setBounds(0, 0, intrinsicWidth, i);
        }
        if (this.mIsThemeDeviceDefaultFamily && hasText() && isLayoutRtl()) {
            setCompoundDrawables(null, null, drawable, null);
        } else {
            setCompoundDrawables(drawable, null, null, null);
        }
        updateTextButtonVisibility();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // com.android.internal.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        setContentDescription(charSequence);
        updateTextButtonVisibility();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Button");
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence contentDescription = getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            return;
        }
        accessibilityEvent.getText().add(contentDescription);
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return onHoverEvent(motionEvent);
    }

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerAfter() {
        return hasText();
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        setTooltipNull(false);
        setTooltipOffset();
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        setTooltipOffset();
        return false;
    }

    protected void setTooltipOffset() {
        int navigationBarHeight;
        if (hasText()) {
            return;
        }
        if (this.mItemData.getTooltipText() == null && this.mItemData.getTitle() == null) {
            return;
        }
        Context context = getContext();
        Resources resources = context.getResources();
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int width = getWidth();
        int height = getHeight();
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int[] iArr2 = new int[2];
        getLocationInWindow(iArr2);
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        View view = (View) getParent();
        View view2 = view != null ? (View) view.getParent() : null;
        int i = (!(view2 instanceof Toolbar) || view2.getWidth() >= rect.right - rect.left) ? 0 : (iArr[0] - iArr2[0]) - rect.left;
        int i2 = iArr2[1] + height;
        if (getLayoutDirection() == 0) {
            navigationBarHeight = (((rect.right - rect.left) - (iArr2[0] + width)) + (((width - paddingStart) - paddingEnd) / 2)) - i;
            if (checkNaviBarForLandscape()) {
                navigationBarHeight += (int) ((getNavigationBarHeight() / resources.getDisplayMetrics().density) * displayMetrics.density);
            }
        } else {
            navigationBarHeight = iArr2[0] + paddingStart + ((paddingEnd - paddingStart) / 2);
        }
        setTooltipPosition(navigationBarHeight, i2);
    }

    boolean checkNaviBarForLandscape() {
        Context context = getContext();
        Resources resources = context.getResources();
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(R.dimen.navigation_bar_height);
        if (rotation == 1 && rect.right + dimension >= point.x) {
            setNavigationBarHeight(point.x - rect.right);
            return true;
        }
        if (rotation != 3 || rect.left > dimension) {
            return false;
        }
        setNavigationBarHeight(rect.left);
        return true;
    }

    private void setNavigationBarHeight(int i) {
        this.mNavigationBarHeight = i;
    }

    private int getNavigationBarHeight() {
        return this.mNavigationBarHeight;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        int iMin;
        int i3;
        boolean zHasText = hasText();
        if (zHasText && (i3 = this.mSavedPaddingLeft) >= 0) {
            super.setPadding(i3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        if (mode == Integer.MIN_VALUE) {
            iMin = Math.min(size, this.mMinWidth);
        } else {
            iMin = this.mMinWidth;
        }
        if (mode != 1073741824 && this.mMinWidth > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2);
        }
        if (zHasText || this.mIcon == null) {
            return;
        }
        int measuredWidth2 = getMeasuredWidth();
        int iWidth = this.mIcon.getBounds().width();
        if (this.mIsChangedRelativePadding) {
            return;
        }
        super.setPadding((measuredWidth2 - iWidth) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    private class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        @Override // android.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        @Override // android.widget.ForwardingListener
        protected boolean onForwardingStarted() {
            ShowableListMenu popup;
            return ActionMenuItemView.this.mItemInvoker != null && ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData) && (popup = getPopup()) != null && popup.isShowing();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.TextView, android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (this.mIsChangedRelativePadding) {
            Drawable background = getBackground();
            if (this.mIcon != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
                background.setHotspotBounds(paddingLeft, 0, width + paddingLeft, height);
                return frame;
            }
            if (background != null) {
                background.setHotspotBounds(0, 0, getWidth(), getHeight());
            }
        }
        return frame;
    }
}
