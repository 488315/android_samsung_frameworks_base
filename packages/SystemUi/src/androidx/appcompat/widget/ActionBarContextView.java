package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ActionBarContextView extends AbsActionBarView {
    public boolean mCheckActionModeOn;
    public View mClose;
    public View mCloseButton;
    public final int mCloseItemLayout;
    public View mCustomView;
    public boolean mIsActionModeAccessibilityOn;
    public CharSequence mSubtitle;
    public final int mSubtitleStyleRes;
    public TextView mSubtitleView;
    public CharSequence mTitle;
    public LinearLayout mTitleLayout;
    public boolean mTitleOptional;
    public final int mTitleStyleRes;
    public TextView mTitleView;

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    public final void initForMode(final ActionMode actionMode) {
        this.mCheckActionModeOn = true;
        View view = this.mClose;
        if (view == null) {
            View inflate = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, (ViewGroup) this, false);
            this.mClose = inflate;
            addView(inflate);
        } else if (view.getParent() == null) {
            addView(this.mClose);
        }
        View findViewById = this.mClose.findViewById(R.id.action_mode_close_button);
        this.mCloseButton = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener(this) { // from class: androidx.appcompat.widget.ActionBarContextView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                actionMode.finish();
            }
        });
        MenuBuilder menu = actionMode.getMenu();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = actionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu != null && actionButtonSubmenu.isShowing()) {
                actionButtonSubmenu.mPopup.dismiss();
            }
        }
        ActionMenuPresenter actionMenuPresenter2 = new ActionMenuPresenter(getContext());
        this.mActionMenuPresenter = actionMenuPresenter2;
        actionMenuPresenter2.mReserveOverflow = true;
        actionMenuPresenter2.mReserveOverflowSet = true;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        menu.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
        ActionMenuPresenter actionMenuPresenter3 = this.mActionMenuPresenter;
        MenuView menuView = actionMenuPresenter3.mMenuView;
        if (menuView == null) {
            MenuView menuView2 = (MenuView) actionMenuPresenter3.mSystemInflater.inflate(actionMenuPresenter3.mMenuLayoutRes, (ViewGroup) this, false);
            actionMenuPresenter3.mMenuView = menuView2;
            menuView2.initialize(actionMenuPresenter3.mMenu);
            actionMenuPresenter3.updateMenuView(true);
        }
        MenuView menuView3 = actionMenuPresenter3.mMenuView;
        if (menuView != menuView3) {
            ActionMenuView actionMenuView = (ActionMenuView) menuView3;
            actionMenuView.mPresenter = actionMenuPresenter3;
            actionMenuPresenter3.mMenuView = actionMenuView;
            actionMenuView.mMenu = actionMenuPresenter3.mMenu;
        }
        ActionMenuView actionMenuView2 = (ActionMenuView) menuView3;
        this.mMenuView = actionMenuView2;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(actionMenuView2, null);
        addView(this.mMenuView, layoutParams);
    }

    public final void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.sesl_action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleLayout = linearLayout;
            this.mTitleView = (TextView) linearLayout.findViewById(R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean z = !TextUtils.isEmpty(this.mTitle);
        boolean z2 = !TextUtils.isEmpty(this.mSubtitle);
        int i = 0;
        this.mSubtitleView.setVisibility(z2 ? 0 : 8);
        LinearLayout linearLayout2 = this.mTitleLayout;
        if (!z && !z2) {
            i = 8;
        }
        linearLayout2.setVisibility(i);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    public final void killMode() {
        removeAllViews();
        this.mCustomView = null;
        this.mMenuView = null;
        this.mActionMenuPresenter = null;
        View view = this.mCloseButton;
        if (view != null) {
            view.setOnClickListener(null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding);
        setPadding(0, dimensionPixelSize, 0, 0);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.ActionMode, android.R.attr.actionModeStyle, 0);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(3, -1);
        obtainStyledAttributes.recycle();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = dimensionPixelSize2 + dimensionPixelSize;
        setLayoutParams(layoutParams);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.ActionMode, android.R.attr.actionModeStyle, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, -1);
        if (dimensionPixelSize >= 0) {
            this.mContentHeight = dimensionPixelSize;
        }
        setPadding(0, getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding), 0, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu();
            ActionMenuPresenter.ActionButtonSubmenu actionButtonSubmenu = this.mActionMenuPresenter.mActionButtonPopup;
            if (actionButtonSubmenu == null || !actionButtonSubmenu.isShowing()) {
                return;
            }
            actionButtonSubmenu.mPopup.dismiss();
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onInitializeAccessibilityEvent Check ActionMode :"), this.mCheckActionModeOn, "ActionBarContextView");
        if (this.mCheckActionModeOn) {
            this.mIsActionModeAccessibilityOn = true;
            this.mCheckActionModeOn = false;
        } else {
            this.mIsActionModeAccessibilityOn = false;
        }
        Log.d("ActionBarContextView", "onInitializeAccessibilityEvent mIsActionModeAccessibilityOn :" + this.mIsActionModeAccessibilityOn);
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        accessibilityEvent.setContentDescription(this.mTitle);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingRight = isLayoutRtl ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        View view = this.mClose;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            int i5 = isLayoutRtl ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = isLayoutRtl ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int i7 = isLayoutRtl ? paddingRight - i5 : paddingRight + i5;
            int positionChild = AbsActionBarView.positionChild(i7, paddingTop, paddingTop2, this.mClose, isLayoutRtl) + i7;
            paddingRight = isLayoutRtl ? positionChild - i6 : positionChild + i6;
        }
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null && this.mCustomView == null && linearLayout.getVisibility() != 8) {
            paddingRight += AbsActionBarView.positionChild(paddingRight, paddingTop, paddingTop2, this.mTitleLayout, isLayoutRtl);
        }
        View view2 = this.mCustomView;
        if (view2 != null) {
            AbsActionBarView.positionChild(paddingRight, paddingTop, paddingTop2, view2, isLayoutRtl);
        }
        int paddingLeft = isLayoutRtl ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            AbsActionBarView.positionChild(paddingLeft, paddingTop, paddingTop2, actionMenuView, !isLayoutRtl);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int i3 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        if (mode != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)"));
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_height=\"wrap_content\""));
        }
        int size = View.MeasureSpec.getSize(i);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding);
        int i4 = this.mContentHeight;
        int size2 = i4 > 0 ? i4 + dimensionPixelSize : View.MeasureSpec.getSize(i2);
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int i5 = size2 - paddingBottom;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, VideoPlayer.MEDIA_ERROR_SYSTEM);
        View view = this.mClose;
        if (view != null && view.getVisibility() == 0) {
            int measureChildView = AbsActionBarView.measureChildView(this.mClose, paddingLeft, makeMeasureSpec);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            paddingLeft = measureChildView - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null && actionMenuView.getParent() == this) {
            paddingLeft = AbsActionBarView.measureChildView(this.mMenuView, paddingLeft, makeMeasureSpec);
        }
        if (this.mTitleLayout != null && this.mCustomView == null) {
            Context context = getContext();
            if (this.mTitleView != null) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(this.mTitleStyleRes, R$styleable.TextAppearance);
                TypedValue peekValue = obtainStyledAttributes.peekValue(0);
                obtainStyledAttributes.recycle();
                float complexToFloat = TypedValue.complexToFloat(peekValue.data);
                if (TextUtils.isEmpty(this.mSubtitle)) {
                    this.mTitleView.setTextSize(1, complexToFloat * Math.min(context.getResources().getConfiguration().fontScale, 1.2f));
                } else {
                    this.mTitleView.setTextSize(1, complexToFloat);
                }
            }
            View view2 = this.mClose;
            if (view2 == null || view2.getVisibility() == 8) {
                int dimension = (int) context.getResources().getDimension(R.dimen.sesl_toolbar_content_inset);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                boolean z = ViewCompat.Api17Impl.getLayoutDirection(this) == 0;
                TextView textView = this.mTitleView;
                if (textView != null && textView.getVisibility() == 0) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTitleView.getLayoutParams();
                    if (z) {
                        layoutParams.leftMargin = dimension;
                    } else {
                        layoutParams.rightMargin = dimension;
                    }
                    this.mTitleView.setLayoutParams(layoutParams);
                }
                TextView textView2 = this.mSubtitleView;
                if (textView2 != null && textView2.getVisibility() == 0) {
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mSubtitleView.getLayoutParams();
                    if (z) {
                        layoutParams2.leftMargin = dimension;
                    } else {
                        layoutParams2.rightMargin = dimension;
                    }
                    this.mSubtitleView.setLayoutParams(layoutParams2);
                }
            }
            if (this.mTitleOptional) {
                this.mTitleLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), makeMeasureSpec);
                int measuredWidth = this.mTitleLayout.getMeasuredWidth();
                boolean z2 = measuredWidth <= paddingLeft;
                if (z2) {
                    paddingLeft -= measuredWidth;
                }
                this.mTitleLayout.setVisibility(z2 ? 0 : 8);
            } else {
                paddingLeft = AbsActionBarView.measureChildView(this.mTitleLayout, paddingLeft, makeMeasureSpec);
            }
        }
        View view3 = this.mCustomView;
        if (view3 != null) {
            ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
            int i6 = layoutParams3.width;
            int i7 = i6 != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (i6 >= 0) {
                paddingLeft = Math.min(i6, paddingLeft);
            }
            int i8 = layoutParams3.height;
            if (i8 == -2) {
                i3 = Integer.MIN_VALUE;
            }
            if (i8 >= 0) {
                i5 = Math.min(i8, i5);
            }
            this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i7), View.MeasureSpec.makeMeasureSpec(i5, i3));
        }
        if (this.mContentHeight > 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        int childCount = getChildCount();
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            int measuredHeight = getChildAt(i10).getMeasuredHeight() + paddingBottom;
            if (measuredHeight > i9) {
                i9 = measuredHeight;
            }
        }
        setMeasuredDimension(size, i9);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public final void setContentHeight(int i) {
        this.mContentHeight = i;
    }

    public final void setCustomView(View view) {
        LinearLayout linearLayout;
        View view2 = this.mCustomView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mCustomView = view;
        if (view != null && (linearLayout = this.mTitleLayout) != null) {
            removeView(linearLayout);
            this.mTitleLayout = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R$styleable.ActionMode, i, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
        this.mTitleStyleRes = obtainStyledAttributes.getResourceId(5, 0);
        this.mSubtitleStyleRes = obtainStyledAttributes.getResourceId(4, 0);
        this.mContentHeight = obtainStyledAttributes.mWrapped.getLayoutDimension(3, 0);
        this.mCloseItemLayout = obtainStyledAttributes.getResourceId(2, R.layout.sesl_action_mode_close_item);
        obtainStyledAttributes.recycle();
    }
}
