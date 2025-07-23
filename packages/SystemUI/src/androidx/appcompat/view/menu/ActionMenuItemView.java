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
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ActionMenuItemForwardingListener extends ForwardingListener {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initialize(androidx.appcompat.view.menu.MenuItemImpl r7) {
        /*
            r6 = this;
            r6.mItemData = r7
            android.graphics.drawable.Drawable r0 = r7.getIcon()
            r6.mIcon = r0
            r1 = 0
            if (r0 == 0) goto L2b
            int r2 = r0.getIntrinsicWidth()
            int r3 = r0.getIntrinsicHeight()
            int r4 = r6.mMaxIconSize
            if (r2 <= r4) goto L1e
            float r5 = (float) r4
            float r2 = (float) r2
            float r5 = r5 / r2
            float r2 = (float) r3
            float r2 = r2 * r5
            int r3 = (int) r2
            r2 = r4
        L1e:
            if (r3 <= r4) goto L27
            float r5 = (float) r4
            float r3 = (float) r3
            float r5 = r5 / r3
            float r2 = (float) r2
            float r2 = r2 * r5
            int r2 = (int) r2
            goto L28
        L27:
            r4 = r3
        L28:
            r0.setBounds(r1, r1, r2, r4)
        L2b:
            r2 = 0
            r6.setCompoundDrawables(r0, r2, r2, r2)
            boolean r3 = r6.hasText()
            if (r3 == 0) goto L42
            java.util.WeakHashMap r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r3 = r6.getLayoutDirection()
            r4 = 1
            if (r3 != r4) goto L42
            r6.setCompoundDrawables(r2, r2, r0, r2)
            goto L45
        L42:
            r6.setCompoundDrawables(r0, r2, r2, r2)
        L45:
            r6.updateTextButtonVisibility()
            java.lang.CharSequence r0 = r7.getTitleCondensed()
            r6.mTitle = r0
            r6.setContentDescription(r0)
            r6.updateTextButtonVisibility()
            int r0 = r7.mId
            r6.setId(r0)
            boolean r0 = r7.isVisible()
            if (r0 == 0) goto L60
            goto L62
        L60:
            r1 = 8
        L62:
            r6.setVisibility(r1)
            boolean r0 = r7.isEnabled()
            r6.setEnabled(r0)
            boolean r7 = r7.hasSubMenu()
            if (r7 == 0) goto L7d
            androidx.appcompat.view.menu.ActionMenuItemView$ActionMenuItemForwardingListener r7 = r6.mForwardingListener
            if (r7 != 0) goto L7d
            androidx.appcompat.view.menu.ActionMenuItemView$ActionMenuItemForwardingListener r7 = new androidx.appcompat.view.menu.ActionMenuItemView$ActionMenuItemForwardingListener
            r7.<init>()
            r6.mForwardingListener = r7
        L7d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.ActionMenuItemView.initialize(androidx.appcompat.view.menu.MenuItemImpl):void");
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
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, 1073741824), i2);
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

    public final boolean seslIsTextButtonVisible() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.mTitle);
        if (this.mIcon != null && ((this.mItemData.mShowAsAction & 4) != 4 || !this.mAllowTextWithIcon)) {
            z = false;
        }
        return z2 & z;
    }

    @Override // android.widget.TextView
    public final boolean setFrame(int i, int i2, int i3, int i4) {
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
        int i2 = configuration.screenHeightDp;
        if (i < 480) {
            return (i >= 640 && i2 >= 480) || configuration.orientation == 2;
        }
        return true;
    }

    public final void updateTextButtonVisibility() {
        boolean seslIsTextButtonVisible = seslIsTextButtonVisible();
        setText(seslIsTextButtonVisible ? this.mTitle : null);
        if (seslIsTextButtonVisible) {
            setBackgroundResource(SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_action_bar_item_text_background_light : R.drawable.sesl_action_bar_item_text_background_dark);
        } else {
            super.setBackground(this.initBackgroundDrawable);
        }
        CharSequence charSequence = this.mItemData.mContentDescription;
        if (TextUtils.isEmpty(charSequence)) {
            setContentDescription(seslIsTextButtonVisible ? null : this.mItemData.mTitle);
        } else {
            setContentDescription(charSequence);
        }
        CharSequence charSequence2 = this.mItemData.mTooltipText;
        if (TextUtils.isEmpty(charSequence2)) {
            setTooltipText(seslIsTextButtonVisible ? null : this.mItemData.mTitle);
        } else {
            setTooltipText(charSequence2);
        }
        if (this.mDefaultTextSize > 0.0f) {
            setTextSize(1, this.mDefaultTextSize * Math.min(getResources().getConfiguration().fontScale, 1.2f));
        }
        setText(seslIsTextButtonVisible ? this.mTitle : null);
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
