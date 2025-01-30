package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import androidx.appcompat.R$styleable;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.TooltipCompatHandler;
import androidx.core.view.ViewCompat;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ActionMenuItemView extends AppCompatTextView implements MenuView.ItemView, View.OnClickListener, ActionMenuView.ActionMenuChildView, View.OnLongClickListener {
    public final Drawable initBackgroundDrawable;
    public boolean mAllowTextWithIcon;
    public final float mDefaultTextSize;
    public ActionMenuItemForwardingListener mForwardingListener;
    public Drawable mIcon;
    public boolean mIsChangedRelativePadding;
    public MenuItemImpl mItemData;
    public MenuBuilder.ItemInvoker mItemInvoker;
    public final int mMaxIconSize;
    public final int mMinWidth;
    public PopupCallback mPopupCallback;
    public int mSavedPaddingLeft;
    public CharSequence mTitle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        @Override // androidx.appcompat.widget.ForwardingListener
        public final ShowableListMenu getPopup() {
            PopupCallback popupCallback = ActionMenuItemView.this.mPopupCallback;
            if (popupCallback != null) {
                return popupCallback.getPopup();
            }
            return null;
        }

        @Override // androidx.appcompat.widget.ForwardingListener
        public final boolean onForwardingStarted() {
            ShowableListMenu popup;
            ActionMenuItemView actionMenuItemView = ActionMenuItemView.this;
            MenuBuilder.ItemInvoker itemInvoker = actionMenuItemView.mItemInvoker;
            return itemInvoker != null && itemInvoker.invokeItem(actionMenuItemView.mItemData) && (popup = getPopup()) != null && popup.isShowing();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PopupCallback {
        public abstract MenuPopup getPopup();
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public final boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0060  */
    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(MenuItemImpl menuItemImpl) {
        this.mItemData = menuItemImpl;
        Drawable icon = menuItemImpl.getIcon();
        this.mIcon = icon;
        if (icon != null) {
            int intrinsicWidth = icon.getIntrinsicWidth();
            int intrinsicHeight = icon.getIntrinsicHeight();
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
            icon.setBounds(0, 0, intrinsicWidth, i);
        }
        setCompoundDrawables(icon, null, null, null);
        if (hasText()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
                setCompoundDrawables(null, null, icon, null);
                updateTextButtonVisibility();
                CharSequence titleCondensed = menuItemImpl.getTitleCondensed();
                this.mTitle = titleCondensed;
                setContentDescription(titleCondensed);
                updateTextButtonVisibility();
                setId(menuItemImpl.mId);
                setVisibility(menuItemImpl.isVisible() ? 0 : 8);
                setEnabled(menuItemImpl.isEnabled());
                if (menuItemImpl.hasSubMenu() || this.mForwardingListener != null) {
                }
                this.mForwardingListener = new ActionMenuItemForwardingListener();
                return;
            }
        }
        setCompoundDrawables(icon, null, null, null);
        updateTextButtonVisibility();
        CharSequence titleCondensed2 = menuItemImpl.getTitleCondensed();
        this.mTitle = titleCondensed2;
        setContentDescription(titleCondensed2);
        updateTextButtonVisibility();
        setId(menuItemImpl.mId);
        setVisibility(menuItemImpl.isVisible() ? 0 : 8);
        setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu()) {
        }
    }

    @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
    public final boolean needsDividerAfter() {
        return hasText();
    }

    @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
    public final boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker != null) {
            itemInvoker.invokeItem(this.mItemData);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    @Override // android.view.View
    public final void onHoverChanged(boolean z) {
        TooltipCompatHandler.sIsForceActionBarX = true;
        TooltipCompatHandler.sIsForceBelow = true;
        super.onHoverChanged(z);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        return false;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean hasText = hasText();
        if (hasText && (i3 = this.mSavedPaddingLeft) >= 0) {
            super.setPadding(i3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int min = mode == Integer.MIN_VALUE ? Math.min(size, this.mMinWidth) : this.mMinWidth;
        if (mode != 1073741824 && this.mMinWidth > 0 && measuredWidth < min) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), i2);
        }
        if (hasText || this.mIcon == null) {
            return;
        }
        int measuredWidth2 = getMeasuredWidth();
        int width = this.mIcon.getBounds().width();
        if (this.mIsChangedRelativePadding) {
            return;
        }
        super.setPadding((measuredWidth2 - width) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.view.View
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence contentDescription = getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            return;
        }
        accessibilityEvent.getText().add(contentDescription);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ActionMenuItemForwardingListener actionMenuItemForwardingListener;
        if (this.mItemData.hasSubMenu() && (actionMenuItemForwardingListener = this.mForwardingListener) != null && actionMenuItemForwardingListener.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean performLongClick() {
        if (this.mIcon == null) {
            TooltipCompatHandler.sIsTooltipNull = true;
            return true;
        }
        TooltipCompatHandler.sIsForceActionBarX = true;
        TooltipCompatHandler.sIsForceBelow = true;
        return super.performLongClick();
    }

    @Override // android.view.View
    public final void setBackground(Drawable drawable) {
        super.setBackground(drawable);
    }

    @Override // android.widget.TextView
    public final boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (!this.mIsChangedRelativePadding) {
            return frame;
        }
        Drawable background = getBackground();
        if (this.mIcon != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
            background.setHotspotBounds(paddingLeft, 0, width + paddingLeft, height);
        } else if (background != null) {
            background.setHotspotBounds(0, 0, getWidth(), getHeight());
        }
        return frame;
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        super.setPadding(i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
        this.mSavedPaddingLeft = i;
        this.mIsChangedRelativePadding = true;
        super.setPaddingRelative(i, i2, i3, i4);
    }

    public final boolean shouldAllowTextWithIcon() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001c, code lost:
    
        if (r5.mAllowTextWithIcon != false) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTextButtonVisibility() {
        boolean z;
        boolean z2;
        CharSequence charSequence;
        CharSequence charSequence2;
        boolean z3 = !TextUtils.isEmpty(this.mTitle);
        if (this.mIcon != null) {
            z = false;
            if ((this.mItemData.mShowAsAction & 4) == 4) {
            }
            z2 = z3 & z;
            setText(!z2 ? this.mTitle : null);
            if (z2) {
                setBackground(this.initBackgroundDrawable);
            } else {
                setBackgroundResource(SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_action_bar_item_text_background_light : R.drawable.sesl_action_bar_item_text_background_dark);
            }
            charSequence = this.mItemData.mContentDescription;
            if (TextUtils.isEmpty(charSequence)) {
                setContentDescription(charSequence);
            } else {
                setContentDescription(z2 ? null : this.mItemData.mTitle);
            }
            charSequence2 = this.mItemData.mTooltipText;
            if (TextUtils.isEmpty(charSequence2)) {
                setTooltipText(charSequence2);
            } else {
                setTooltipText(z2 ? null : this.mItemData.mTitle);
            }
            if (this.mDefaultTextSize > 0.0f) {
                setTextSize(1, this.mDefaultTextSize * Math.min(getResources().getConfiguration().fontScale, 1.2f));
            }
            setText(z2 ? this.mTitle : null);
        }
        z = true;
        z2 = z3 & z;
        setText(!z2 ? this.mTitle : null);
        if (z2) {
        }
        charSequence = this.mItemData.mContentDescription;
        if (TextUtils.isEmpty(charSequence)) {
        }
        charSequence2 = this.mItemData.mTooltipText;
        if (TextUtils.isEmpty(charSequence2)) {
        }
        if (this.mDefaultTextSize > 0.0f) {
        }
        setText(z2 ? this.mTitle : null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsChangedRelativePadding = false;
        this.mDefaultTextSize = 0.0f;
        Resources resources = context.getResources();
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionMenuItemView, i, 0);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        obtainStyledAttributes.recycle();
        this.mMaxIconSize = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
        setSaveEnabled(false);
        Resources.Theme theme = context.getTheme();
        int[] iArr = R$styleable.AppCompatTheme;
        TypedArray obtainStyledAttributes2 = theme.obtainStyledAttributes(null, iArr, 0, 0);
        int resourceId = obtainStyledAttributes2.getResourceId(26, 0);
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(resourceId, R$styleable.TextAppearance);
        TypedValue peekValue = obtainStyledAttributes3.peekValue(0);
        obtainStyledAttributes3.recycle();
        if (peekValue != null) {
            this.mDefaultTextSize = TypedValue.complexToFloat(peekValue.data);
        }
        SeslTextViewReflector.semSetButtonShapeEnabled(this, true);
        TypedArray obtainStyledAttributes4 = context.getTheme().obtainStyledAttributes(null, iArr, 0, 0);
        int resourceId2 = obtainStyledAttributes4.getResourceId(24, 0);
        obtainStyledAttributes4.recycle();
        TypedArray obtainStyledAttributes5 = context.getTheme().obtainStyledAttributes(resourceId2, new int[]{android.R.attr.background});
        this.initBackgroundDrawable = obtainStyledAttributes5.getDrawable(0);
        obtainStyledAttributes5.recycle();
    }
}
