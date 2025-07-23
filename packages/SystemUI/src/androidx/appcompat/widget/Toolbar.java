package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ToolbarActionBar;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuHostHelper;
import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.fragment.app.FragmentManager;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Toolbar extends ViewGroup implements MenuHost {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ToolbarActionBar.ActionMenuPresenterCallback mActionMenuPresenterCallback;
    public final boolean mAllowEatingTouch;
    public Toolbar$Api33Impl$$ExternalSyntheticLambda0 mBackInvokedCallback;
    public boolean mBackInvokedCallbackEnabled;
    public OnBackInvokedDispatcher mBackInvokedDispatcher;
    public final int mButtonGravity;
    public AppCompatImageButton mCollapseButtonView;
    public final CharSequence mCollapseDescription;
    public final Drawable mCollapseIcon;
    public final int mContentInsetEndWithActions;
    public final int mContentInsetStartWithNavigation;
    public RtlSpacingHelper mContentInsets;
    public boolean mEatingHover;
    public boolean mEatingTouch;
    public View mExpandedActionView;
    public ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    public final int mGravity;
    public final ArrayList mHiddenViews;
    public AppCompatImageView mLogoView;
    public int mMaxButtonHeight;
    public ToolbarActionBar.MenuBuilderCallback mMenuBuilderCallback;
    public final MenuHostHelper mMenuHostHelper;
    public ActionMenuView mMenuView;
    public final AnonymousClass1 mMenuViewItemClickListener;
    public Drawable mNavButtonIconDrawable;
    public AppCompatImageButton mNavButtonView;
    public final CharSequence mNavTooltipText;
    public Toolbar$$ExternalSyntheticLambda2 mOnGlobalLayoutListenerForTD;
    public ToolbarActionBar.AnonymousClass2 mOnMenuItemClickListener;
    public ActionMenuPresenter mOuterActionMenuPresenter;
    public Context mPopupContext;
    public int mPopupTheme;
    public ArrayList mProvidedMenuItems;
    public final AnonymousClass2 mShowOverflowMenuRunnable;
    public CharSequence mSubtitleText;
    public int mSubtitleTextAppearance;
    public final ColorStateList mSubtitleTextColor;
    public AppCompatTextView mSubtitleTextView;
    public final int[] mTempMargins;
    public final ArrayList mTempViews;
    public final int mTitleMarginBottom;
    public final int mTitleMarginEnd;
    public final int mTitleMarginStart;
    public final int mTitleMarginTop;
    public CharSequence mTitleText;
    public int mTitleTextAppearance;
    public ColorStateList mTitleTextColor;
    public AppCompatTextView mTitleTextView;
    public final int mUserTopPadding;
    public ToolbarWidgetWrapper mWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.widget.Toolbar$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.appcompat.widget.Toolbar.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public int expandedMenuItemId;
        public boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public static int getHorizontalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginEnd() + marginLayoutParams.getMarginStart();
    }

    public static int getVerticalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final void addCustomViewsWithGravity(int i, List list) {
        boolean z = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, getLayoutDirection());
        ArrayList arrayList = (ArrayList) list;
        arrayList.clear();
        if (!z) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.mViewType == 0 && shouldLayout(childAt)) {
                    int i3 = layoutParams.gravity;
                    int layoutDirection = getLayoutDirection();
                    int absoluteGravity2 = Gravity.getAbsoluteGravity(i3, layoutDirection) & 7;
                    if (absoluteGravity2 != 1 && absoluteGravity2 != 3 && absoluteGravity2 != 5) {
                        absoluteGravity2 = layoutDirection == 1 ? 5 : 3;
                    }
                    if (absoluteGravity2 == absoluteGravity) {
                        arrayList.add(childAt);
                    }
                }
            }
            return;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            View childAt2 = getChildAt(i4);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.mViewType == 0 && shouldLayout(childAt2)) {
                int i5 = layoutParams2.gravity;
                int layoutDirection2 = getLayoutDirection();
                int absoluteGravity3 = Gravity.getAbsoluteGravity(i5, layoutDirection2) & 7;
                if (absoluteGravity3 != 1 && absoluteGravity3 != 3 && absoluteGravity3 != 5) {
                    absoluteGravity3 = layoutDirection2 == 1 ? 5 : 3;
                }
                if (absoluteGravity3 == absoluteGravity) {
                    arrayList.add(childAt2);
                }
            }
        }
    }

    @Override // androidx.core.view.MenuHost
    public final void addMenuProvider(FragmentManager.AnonymousClass2 anonymousClass2) {
        MenuHostHelper menuHostHelper = this.mMenuHostHelper;
        menuHostHelper.mMenuProviders.add(anonymousClass2);
        menuHostHelper.mOnInvalidateMenuCallback.run();
    }

    public final void addSystemView(View view, boolean z) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            LayoutParams generateDefaultLayoutParams = layoutParams == null ? generateDefaultLayoutParams() : !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (LayoutParams) layoutParams;
            generateDefaultLayoutParams.mViewType = 1;
            if (z && this.mExpandedActionView != null) {
                view.setLayoutParams(generateDefaultLayoutParams);
                this.mHiddenViews.add(view);
            } else if (view.getParent() == null) {
                addView(view, generateDefaultLayoutParams);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            TooltipCompatHandler.sIsForceBelow = true;
            TooltipCompatHandler.sIsForceActionBarX = true;
        } else if (action == 10) {
            TooltipCompatHandler.sIsForceBelow = false;
            TooltipCompatHandler.sIsForceActionBarX = false;
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    public final void ensureMenu() {
        ensureMenuView();
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView.mMenu == null) {
            MenuBuilder menu = actionMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.mPresenter.mExpandedActionViewsExclusive = true;
            menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
            updateBackInvokedCallbackState();
        }
    }

    public final void ensureMenuView() {
        if (this.mMenuView == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext());
            this.mMenuView = actionMenuView;
            int i = this.mPopupTheme;
            if (actionMenuView.mPopupTheme != i) {
                actionMenuView.mPopupTheme = i;
                if (i == 0) {
                    actionMenuView.mPopupContext = actionMenuView.getContext();
                } else {
                    actionMenuView.mPopupContext = new ContextThemeWrapper(actionMenuView.getContext(), i);
                }
            }
            ActionMenuView actionMenuView2 = this.mMenuView;
            actionMenuView2.mOnMenuItemClickListener = this.mMenuViewItemClickListener;
            ToolbarActionBar.ActionMenuPresenterCallback actionMenuPresenterCallback = this.mActionMenuPresenterCallback;
            MenuBuilder.Callback callback = new MenuBuilder.Callback() { // from class: androidx.appcompat.widget.Toolbar.3
                @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
                public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                    ToolbarActionBar.MenuBuilderCallback menuBuilderCallback = Toolbar.this.mMenuBuilderCallback;
                    return false;
                }

                @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
                public final void onMenuModeChange(MenuBuilder menuBuilder) {
                    Toolbar toolbar = Toolbar.this;
                    ActionMenuPresenter actionMenuPresenter = toolbar.mMenuView.mPresenter;
                    if (actionMenuPresenter == null || !actionMenuPresenter.isOverflowMenuShowing()) {
                        toolbar.mMenuHostHelper.onPrepareMenu(menuBuilder);
                    }
                    ToolbarActionBar.MenuBuilderCallback menuBuilderCallback = toolbar.mMenuBuilderCallback;
                    if (menuBuilderCallback != null) {
                        menuBuilderCallback.onMenuModeChange(menuBuilder);
                    }
                }
            };
            actionMenuView2.mActionMenuPresenterCallback = actionMenuPresenterCallback;
            actionMenuView2.mMenuBuilderCallback = callback;
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (this.mButtonGravity & 112) | 8388613;
            this.mMenuView.setLayoutParams(generateDefaultLayoutParams);
            addSystemView(this.mMenuView, false);
        }
    }

    public final void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (this.mButtonGravity & 112) | 8388611;
            this.mNavButtonView.setLayoutParams(generateDefaultLayoutParams);
            SeslViewReflector.semSetHoverPopupType(this.mNavButtonView, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
            if (TextUtils.isEmpty(this.mNavTooltipText)) {
                return;
            }
            this.mNavButtonView.setTooltipText(this.mNavTooltipText);
        }
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return generateDefaultLayoutParams();
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    public final int getChildTop(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int i3 = layoutParams.gravity & 112;
        if (i3 != 16 && i3 != 48 && i3 != 80) {
            i3 = this.mGravity & 112;
        }
        if (i3 == 48) {
            return getPaddingTop();
        }
        if (i3 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int i4 = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        if (i4 < i5) {
            i4 = i5;
        } else {
            int i6 = (((height - paddingBottom) - measuredHeight) - i4) - paddingTop;
            int i7 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            if (i6 < i7) {
                i4 = Math.max(0, i4 - (i7 - i6));
            }
        }
        return paddingTop + i4;
    }

    public final int getCurrentContentInsetEnd() {
        MenuBuilder menuBuilder;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null && (menuBuilder = actionMenuView.mMenu) != null && menuBuilder.hasVisibleItems()) {
            RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
            return Math.max(rtlSpacingHelper != null ? rtlSpacingHelper.mIsRtl ? rtlSpacingHelper.mLeft : rtlSpacingHelper.mRight : 0, Math.max(this.mContentInsetEndWithActions, 0));
        }
        RtlSpacingHelper rtlSpacingHelper2 = this.mContentInsets;
        if (rtlSpacingHelper2 != null) {
            return rtlSpacingHelper2.mIsRtl ? rtlSpacingHelper2.mLeft : rtlSpacingHelper2.mRight;
        }
        return 0;
    }

    public final int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
            return Math.max(rtlSpacingHelper != null ? rtlSpacingHelper.mIsRtl ? rtlSpacingHelper.mRight : rtlSpacingHelper.mLeft : 0, Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        RtlSpacingHelper rtlSpacingHelper2 = this.mContentInsets;
        if (rtlSpacingHelper2 != null) {
            return rtlSpacingHelper2.mIsRtl ? rtlSpacingHelper2.mRight : rtlSpacingHelper2.mLeft;
        }
        return 0;
    }

    public final MenuBuilder getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public View getNavButtonView() {
        return this.mNavButtonView;
    }

    public final Drawable getNavigationIcon() {
        AppCompatImageButton appCompatImageButton = this.mNavButtonView;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getDrawable();
        }
        return null;
    }

    public final TextView getSubtitleTextView() {
        return this.mSubtitleTextView;
    }

    public final TextView getTitleTextView() {
        return this.mTitleTextView;
    }

    public void inflateMenu(int i) {
        new SupportMenuInflater(getContext()).inflate(i, getMenu());
    }

    public final boolean isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view);
    }

    public final boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.mMenuView;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.mPresenter) == null || !actionMenuPresenter.isOverflowMenuShowing()) ? false : true;
    }

    public final int layoutChildLeft(View view, int i, int i2, int[] iArr) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin - iArr[0];
        int max = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max, childTop, max + measuredWidth, view.getMeasuredHeight() + childTop);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + max;
    }

    public final int layoutChildRight(View view, int i, int i2, int[] iArr) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin - iArr[1];
        int max = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(max - measuredWidth, childTop, max, view.getMeasuredHeight() + childTop);
        return max - (measuredWidth + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
    }

    public final int measureChildCollapseMargins(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int max = Math.max(0, i6) + Math.max(0, i5);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + max + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + max;
    }

    public final void measureChildConstrained(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i4 >= 0) {
            if (mode != 0) {
                i4 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i4);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateBackInvokedCallbackState();
        int i = this.mUserTopPadding;
        if (i == -1) {
            i = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding);
        }
        setPadding(0, i, 0, 0);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(R$styleable.AppCompatTheme);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(16, 0);
        obtainStyledAttributes.recycle();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = dimensionPixelSize + i;
        setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuPresenter actionMenuPresenter2;
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(R$styleable.AppCompatTheme);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(16, 0);
        if (this.mNavButtonView != null) {
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.View, R.attr.actionOverflowButtonStyle, 0);
            this.mNavButtonView.setMinimumHeight(obtainStyledAttributes.getDimensionPixelSize(4, 0));
        }
        obtainStyledAttributes.recycle();
        int i = this.mUserTopPadding;
        if (i == -1) {
            i = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding);
        }
        setPadding(0, i, 0, 0);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = dimensionPixelSize + i;
        setLayoutParams(layoutParams);
        TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(null, R$styleable.Toolbar, android.R.attr.toolbarStyle, 0);
        int dimensionPixelSize2 = obtainStyledAttributes2.getDimensionPixelSize(14, -1);
        if (dimensionPixelSize2 >= -1) {
            this.mMaxButtonHeight = dimensionPixelSize2;
        }
        int dimensionPixelSize3 = obtainStyledAttributes2.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize3 >= -1) {
            setMinimumHeight(dimensionPixelSize3);
        }
        obtainStyledAttributes2.recycle();
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null || (actionMenuPresenter = actionMenuView.mPresenter) == null || !actionMenuPresenter.isOverflowMenuShowing() || (actionMenuPresenter2 = this.mMenuView.mPresenter) == null) {
            return;
        }
        actionMenuPresenter2.hideOverflowMenu();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
        updateBackInvokedCallbackState();
        if (this.mOnGlobalLayoutListenerForTD != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerForTD);
            this.mOnGlobalLayoutListenerForTD = null;
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.mEatingHover = true;
            }
        }
        if (actionMasked != 10 && actionMasked != 3) {
            return true;
        }
        this.mEatingHover = false;
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x02ca A[LOOP:0: B:50:0x02c8->B:51:0x02ca, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x02e9 A[LOOP:1: B:54:0x02e7->B:55:0x02e9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0310 A[LOOP:2: B:58:0x030e->B:59:0x0310, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0353  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0363 A[LOOP:3: B:67:0x0361->B:68:0x0363, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01da  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 888
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        char c;
        char c2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int[] iArr = this.mTempMargins;
        int i8 = 0;
        if (getLayoutDirection() == 1) {
            c2 = 1;
            c = 0;
        } else {
            c = 1;
            c2 = 0;
        }
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, i, 0, i2, this.mMaxButtonHeight);
            i3 = getHorizontalMargins(this.mNavButtonView) + this.mNavButtonView.getMeasuredWidth();
            int max = Math.max(0, getVerticalMargins(this.mNavButtonView) + this.mNavButtonView.getMeasuredHeight());
            int combineMeasuredStates = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
            Drawable drawable = this.mNavButtonView.getDrawable();
            Drawable background = this.mNavButtonView.getBackground();
            if (drawable != null && background != null) {
                int paddingLeft = (this.mNavButtonView.getPaddingLeft() - this.mNavButtonView.getPaddingRight()) / 2;
                background.setHotspotBounds(paddingLeft, 0, paddingLeft + i3, max);
            }
            i4 = max;
            i5 = combineMeasuredStates;
        } else {
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, i, 0, i2, this.mMaxButtonHeight);
            i3 = getHorizontalMargins(this.mCollapseButtonView) + this.mCollapseButtonView.getMeasuredWidth();
            i4 = Math.max(i4, getVerticalMargins(this.mCollapseButtonView) + this.mCollapseButtonView.getMeasuredHeight());
            i5 = View.combineMeasuredStates(i5, this.mCollapseButtonView.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int max2 = Math.max(currentContentInsetStart, i3);
        iArr[c2] = Math.max(0, currentContentInsetStart - i3);
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, i, max2, i2, this.mMaxButtonHeight);
            i6 = getHorizontalMargins(this.mMenuView) + this.mMenuView.getMeasuredWidth();
            i4 = Math.max(i4, getVerticalMargins(this.mMenuView) + this.mMenuView.getMeasuredHeight());
            i5 = View.combineMeasuredStates(i5, this.mMenuView.getMeasuredState());
        } else {
            i6 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int max3 = max2 + Math.max(currentContentInsetEnd, i6);
        iArr[c] = Math.max(0, currentContentInsetEnd - i6);
        if (shouldLayout(this.mExpandedActionView)) {
            max3 += measureChildCollapseMargins(this.mExpandedActionView, i, max3, i2, 0, iArr);
            i4 = Math.max(i4, getVerticalMargins(this.mExpandedActionView) + this.mExpandedActionView.getMeasuredHeight());
            i5 = View.combineMeasuredStates(i5, this.mExpandedActionView.getMeasuredState());
        }
        if (shouldLayout(this.mLogoView)) {
            max3 += measureChildCollapseMargins(this.mLogoView, i, max3, i2, 0, iArr);
            i4 = Math.max(i4, getVerticalMargins(this.mLogoView) + this.mLogoView.getMeasuredHeight());
            i5 = View.combineMeasuredStates(i5, this.mLogoView.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (((LayoutParams) childAt.getLayoutParams()).mViewType == 0 && shouldLayout(childAt)) {
                max3 += measureChildCollapseMargins(childAt, i, max3, i2, 0, iArr);
                int max4 = Math.max(i4, getVerticalMargins(childAt) + childAt.getMeasuredHeight());
                i5 = View.combineMeasuredStates(i5, childAt.getMeasuredState());
                i4 = max4;
            } else {
                max3 = max3;
            }
        }
        int i10 = max3;
        int i11 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int i12 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            Context context = getContext();
            int i13 = this.mTitleTextAppearance;
            int[] iArr2 = R$styleable.TextAppearance;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i13, iArr2);
            TypedValue peekValue = obtainStyledAttributes.peekValue(0);
            float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_toolbar_title_text_size);
            if (!TextUtils.isEmpty(this.mSubtitleText)) {
                dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_toolbar_title_text_size_with_subtitle);
            }
            if (peekValue != null && TextUtils.isEmpty(this.mSubtitleText)) {
                dimensionPixelSize = TypedValue.complexToFloat(peekValue.data);
            }
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(this.mSubtitleTextAppearance, iArr2);
            TypedValue peekValue2 = obtainStyledAttributes2.peekValue(0);
            obtainStyledAttributes2.recycle();
            float dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_toolbar_subtitle_text_size);
            if (peekValue2 != null) {
                dimensionPixelSize2 = TypedValue.complexToFloat(peekValue2.data);
            }
            float f = getContext().getResources().getConfiguration().fontScale;
            if (f > 1.2f) {
                f = 1.2f;
            }
            if (dimensionPixelSize == -1.0f || !TextUtils.isEmpty(this.mSubtitleText)) {
                this.mTitleTextView.setTextSize(0, dimensionPixelSize * f);
                this.mSubtitleTextView.setTextSize(1, dimensionPixelSize2 * f);
            } else {
                this.mTitleTextView.setTextSize(1, dimensionPixelSize * f);
            }
            measureChildCollapseMargins(this.mTitleTextView, i, i10 + i12, i2, i11, iArr);
            i8 = getHorizontalMargins(this.mTitleTextView) + this.mTitleTextView.getMeasuredWidth();
            int measuredHeight = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            i5 = View.combineMeasuredStates(i5, this.mTitleTextView.getMeasuredState());
            i7 = measuredHeight;
        } else {
            i7 = 0;
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            i8 = Math.max(i8, measureChildCollapseMargins(this.mSubtitleTextView, i, i10 + i12, i2, i11 + i7, iArr));
            i7 += getVerticalMargins(this.mSubtitleTextView) + this.mSubtitleTextView.getMeasuredHeight();
            i5 = View.combineMeasuredStates(i5, this.mSubtitleTextView.getMeasuredState());
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + i10 + i8, getSuggestedMinimumWidth()), i, (-16777216) & i5), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + Math.max(i4, i7), getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        ActionMenuView actionMenuView = this.mMenuView;
        MenuBuilder menuBuilder = actionMenuView != null ? actionMenuView.mMenu : null;
        int i = savedState.expandedMenuItemId;
        if (i != 0 && this.mExpandedMenuPresenter != null && menuBuilder != null && (findItem = menuBuilder.findItem(i)) != null) {
            findItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            removeCallbacks(this.mShowOverflowMenuRunnable);
            post(this.mShowOverflowMenuRunnable);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean z = i == 1;
        if (z == rtlSpacingHelper.mIsRtl) {
            return;
        }
        rtlSpacingHelper.mIsRtl = z;
        if (!rtlSpacingHelper.mIsRelative) {
            rtlSpacingHelper.mLeft = rtlSpacingHelper.mExplicitLeft;
            rtlSpacingHelper.mRight = rtlSpacingHelper.mExplicitRight;
            return;
        }
        if (z) {
            int i2 = rtlSpacingHelper.mEnd;
            if (i2 == Integer.MIN_VALUE) {
                i2 = rtlSpacingHelper.mExplicitLeft;
            }
            rtlSpacingHelper.mLeft = i2;
            int i3 = rtlSpacingHelper.mStart;
            if (i3 == Integer.MIN_VALUE) {
                i3 = rtlSpacingHelper.mExplicitRight;
            }
            rtlSpacingHelper.mRight = i3;
            return;
        }
        int i4 = rtlSpacingHelper.mStart;
        if (i4 == Integer.MIN_VALUE) {
            i4 = rtlSpacingHelper.mExplicitLeft;
        }
        rtlSpacingHelper.mLeft = i4;
        int i5 = rtlSpacingHelper.mEnd;
        if (i5 == Integer.MIN_VALUE) {
            i5 = rtlSpacingHelper.mExplicitRight;
        }
        rtlSpacingHelper.mRight = i5;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        MenuItemImpl menuItemImpl;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && (menuItemImpl = expandedActionViewMenuPresenter.mCurrentExpandedItem) != null) {
            savedState.expandedMenuItemId = menuItemImpl.mId;
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return this.mAllowEatingTouch;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, androidx.appcompat.widget.Toolbar$$ExternalSyntheticLambda2] */
    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            if (this.mOnGlobalLayoutListenerForTD != null) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListenerForTD);
                this.mOnGlobalLayoutListenerForTD = null;
                return;
            }
            return;
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver == 0 || this.mOnGlobalLayoutListenerForTD != null) {
            return;
        }
        ?? r0 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.Toolbar$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                final Toolbar toolbar = Toolbar.this;
                int i2 = Toolbar.$r8$clinit;
                toolbar.post(new Runnable() { // from class: androidx.appcompat.widget.Toolbar$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z;
                        View view;
                        Toolbar toolbar2 = Toolbar.this;
                        ViewGroup viewGroup = toolbar;
                        int i3 = Toolbar.$r8$clinit;
                        SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(viewGroup);
                        if (toolbar2.shouldLayout(toolbar2.mNavButtonView)) {
                            seslTouchTargetDelegate.addTouchDelegate(toolbar2.mNavButtonView, SeslTouchTargetDelegate.ExtraInsets.of(0, toolbar2.mNavButtonView.getTop(), 0, viewGroup.getHeight() - toolbar2.mNavButtonView.getBottom()));
                            z = true;
                        } else {
                            z = false;
                        }
                        int childCount = viewGroup.getChildCount();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= childCount) {
                                view = null;
                                break;
                            }
                            view = viewGroup.getChildAt(i4);
                            if (view instanceof ActionMenuView) {
                                break;
                            } else {
                                i4++;
                            }
                        }
                        if (view != null && view.getVisibility() == 0) {
                            ViewGroup viewGroup2 = (ViewGroup) view;
                            int childCount2 = viewGroup2.getChildCount();
                            int i5 = 0;
                            while (i5 < childCount2) {
                                View childAt = viewGroup2.getChildAt(i5);
                                if (childAt.getVisibility() == 0) {
                                    int measuredWidth = childAt.getMeasuredWidth() / 2;
                                    seslTouchTargetDelegate.addTouchDelegate(childAt, SeslTouchTargetDelegate.ExtraInsets.of((i5 != 0 || ((childAt instanceof ActionMenuItemView) && ((ActionMenuItemView) childAt).seslIsTextButtonVisible())) ? 0 : measuredWidth, measuredWidth, 0, measuredWidth));
                                    z = true;
                                }
                                i5++;
                            }
                        }
                        if (z) {
                            viewGroup.setTouchDelegate(seslTouchTargetDelegate);
                        }
                    }
                });
            }
        };
        this.mOnGlobalLayoutListenerForTD = r0;
        viewTreeObserver.addOnGlobalLayoutListener(r0);
    }

    @Override // androidx.core.view.MenuHost
    public final void removeMenuProvider(FragmentManager.AnonymousClass2 anonymousClass2) {
        this.mMenuHostHelper.removeMenuProvider(anonymousClass2);
    }

    public final void setLogo(Drawable drawable) {
        if (drawable != null) {
            if (this.mLogoView == null) {
                this.mLogoView = new AppCompatImageView(getContext());
            }
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else {
            AppCompatImageView appCompatImageView = this.mLogoView;
            if (appCompatImageView != null && isChildOrHidden(appCompatImageView)) {
                removeView(this.mLogoView);
                this.mHiddenViews.remove(this.mLogoView);
            }
        }
        AppCompatImageView appCompatImageView2 = this.mLogoView;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setImageDrawable(drawable);
        }
    }

    public final void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureNavButtonView();
        }
        AppCompatImageButton appCompatImageButton = this.mNavButtonView;
        if (appCompatImageButton != null) {
            appCompatImageButton.setContentDescription(charSequence);
            this.mNavButtonView.setTooltipText(charSequence);
        }
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else {
            AppCompatImageButton appCompatImageButton = this.mNavButtonView;
            if (appCompatImageButton != null && isChildOrHidden(appCompatImageButton)) {
                removeView(this.mNavButtonView);
                this.mHiddenViews.remove(this.mNavButtonView);
            }
        }
        AppCompatImageButton appCompatImageButton2 = this.mNavButtonView;
        if (appCompatImageButton2 != null) {
            appCompatImageButton2.setImageDrawable(drawable);
            this.mNavButtonIconDrawable = drawable;
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.mSubtitleTextView;
            if (appCompatTextView != null && isChildOrHidden(appCompatTextView)) {
                removeView(this.mSubtitleTextView);
                this.mHiddenViews.remove(this.mSubtitleTextView);
            }
        } else {
            if (this.mSubtitleTextView == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context);
                this.mSubtitleTextView = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.mSubtitleTextAppearance;
                if (i != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.mSubtitleTextColor;
                if (colorStateList != null) {
                    this.mSubtitleTextView.setTextColor(colorStateList);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.mSubtitleTextView;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.mTitleTextView;
            if (appCompatTextView != null && isChildOrHidden(appCompatTextView)) {
                removeView(this.mTitleTextView);
                this.mHiddenViews.remove(this.mTitleTextView);
            }
        } else {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context);
                this.mTitleTextView = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.mTitleTextAppearance;
                if (i != 0) {
                    this.mTitleTextView.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.mTitleTextColor;
                if (colorStateList != null) {
                    this.mTitleTextView.setTextColor(colorStateList);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.mTitleTextView;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public final void setTitleAccessibilityEnabled(boolean z) {
        if (z) {
            AppCompatTextView appCompatTextView = this.mTitleTextView;
            if (appCompatTextView != null) {
                appCompatTextView.setImportantForAccessibility(1);
                this.mTitleTextView.setFocusable(true);
            }
            AppCompatTextView appCompatTextView2 = this.mSubtitleTextView;
            if (appCompatTextView2 != null) {
                appCompatTextView2.setImportantForAccessibility(1);
                this.mSubtitleTextView.setFocusable(true);
                return;
            }
            return;
        }
        AppCompatTextView appCompatTextView3 = this.mTitleTextView;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setImportantForAccessibility(2);
            this.mTitleTextView.setFocusable(false);
        }
        AppCompatTextView appCompatTextView4 = this.mSubtitleTextView;
        if (appCompatTextView4 != null) {
            appCompatTextView4.setImportantForAccessibility(2);
            this.mSubtitleTextView.setFocusable(false);
        }
    }

    public final boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public final boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter;
        ActionMenuView actionMenuView = this.mMenuView;
        return (actionMenuView == null || (actionMenuPresenter = actionMenuView.mPresenter) == null || !actionMenuPresenter.showOverflowMenu()) ? false : true;
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [androidx.appcompat.widget.Toolbar$Api33Impl$$ExternalSyntheticLambda0] */
    public final void updateBackInvokedCallbackState() {
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = findOnBackInvokedDispatcher();
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        boolean z = (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null || findOnBackInvokedDispatcher == null || !isAttachedToWindow() || !this.mBackInvokedCallbackEnabled) ? false : true;
        if (z && this.mBackInvokedDispatcher == null) {
            if (this.mBackInvokedCallback == null) {
                final Toolbar$$ExternalSyntheticLambda0 toolbar$$ExternalSyntheticLambda0 = new Toolbar$$ExternalSyntheticLambda0(this, 0);
                this.mBackInvokedCallback = new OnBackInvokedCallback() { // from class: androidx.appcompat.widget.Toolbar$Api33Impl$$ExternalSyntheticLambda0
                    @Override // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        Toolbar$$ExternalSyntheticLambda0.this.run();
                    }
                };
            }
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, this.mBackInvokedCallback);
            this.mBackInvokedDispatcher = findOnBackInvokedDispatcher;
            return;
        }
        if (z || (onBackInvokedDispatcher = this.mBackInvokedDispatcher) == null) {
            return;
        }
        onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackInvokedCallback);
        this.mBackInvokedDispatcher = null;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends ActionBar.LayoutParams {
        public int mViewType;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mViewType = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mViewType = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.mViewType = 0;
            this.gravity = i3;
        }

        public LayoutParams(int i) {
            this(-2, -1, i);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ActionBar.LayoutParams) layoutParams);
            this.mViewType = 0;
            this.mViewType = layoutParams.mViewType;
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mViewType = 0;
            ((ViewGroup.MarginLayoutParams) this).leftMargin = marginLayoutParams.leftMargin;
            ((ViewGroup.MarginLayoutParams) this).topMargin = marginLayoutParams.topMargin;
            ((ViewGroup.MarginLayoutParams) this).rightMargin = marginLayoutParams.rightMargin;
            ((ViewGroup.MarginLayoutParams) this).bottomMargin = marginLayoutParams.bottomMargin;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = 0;
        }
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public static LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [androidx.appcompat.widget.Toolbar$2] */
    public Toolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList();
        this.mHiddenViews = new ArrayList();
        this.mTempMargins = new int[2];
        this.mMenuHostHelper = new MenuHostHelper(new Toolbar$$ExternalSyntheticLambda0(this, 1));
        this.mProvidedMenuItems = new ArrayList();
        this.mMenuViewItemClickListener = new AnonymousClass1();
        this.mShowOverflowMenuRunnable = new Runnable() { // from class: androidx.appcompat.widget.Toolbar.2
            @Override // java.lang.Runnable
            public final void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        this.mUserTopPadding = -1;
        this.mAllowEatingTouch = true;
        Context context2 = getContext();
        int[] iArr = R$styleable.Toolbar;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attributeSet, iArr, i, 0);
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        this.mTitleTextAppearance = obtainStyledAttributes.mWrapped.getResourceId(29, 0);
        this.mSubtitleTextAppearance = obtainStyledAttributes.mWrapped.getResourceId(20, 0);
        this.mGravity = obtainStyledAttributes.mWrapped.getInteger(0, 8388627);
        this.mButtonGravity = obtainStyledAttributes.mWrapped.getInteger(3, 48);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        this.mNavTooltipText = obtainStyledAttributes.mWrapped.getText(31);
        setBackground(drawable);
        int dimensionPixelOffset = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(23, 0);
        this.mTitleMarginBottom = dimensionPixelOffset;
        this.mTitleMarginTop = dimensionPixelOffset;
        this.mTitleMarginEnd = dimensionPixelOffset;
        this.mTitleMarginStart = dimensionPixelOffset;
        int dimensionPixelOffset2 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(26, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.mTitleMarginStart = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(25, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.mTitleMarginEnd = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(27, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.mTitleMarginTop = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(24, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.mTitleMarginBottom = dimensionPixelOffset5;
        }
        this.mMaxButtonHeight = obtainStyledAttributes.mWrapped.getDimensionPixelSize(14, -1);
        int dimensionPixelOffset6 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(10, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(6, Integer.MIN_VALUE);
        int dimensionPixelSize = obtainStyledAttributes.mWrapped.getDimensionPixelSize(8, 0);
        int dimensionPixelSize2 = obtainStyledAttributes.mWrapped.getDimensionPixelSize(9, 0);
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        rtlSpacingHelper.mIsRelative = false;
        if (dimensionPixelSize != Integer.MIN_VALUE) {
            rtlSpacingHelper.mExplicitLeft = dimensionPixelSize;
            rtlSpacingHelper.mLeft = dimensionPixelSize;
        }
        if (dimensionPixelSize2 != Integer.MIN_VALUE) {
            rtlSpacingHelper.mExplicitRight = dimensionPixelSize2;
            rtlSpacingHelper.mRight = dimensionPixelSize2;
        }
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            rtlSpacingHelper.setRelative(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.mContentInsetStartWithNavigation = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(11, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(7, Integer.MIN_VALUE);
        this.mCollapseIcon = obtainStyledAttributes.getDrawable(5);
        this.mCollapseDescription = obtainStyledAttributes.mWrapped.getText(4);
        CharSequence text = obtainStyledAttributes.mWrapped.getText(22);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = obtainStyledAttributes.mWrapped.getText(19);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.mPopupContext = getContext();
        int resourceId = obtainStyledAttributes.mWrapped.getResourceId(18, 0);
        if (this.mPopupTheme != resourceId) {
            this.mPopupTheme = resourceId;
            if (resourceId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resourceId);
            }
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(17);
        if (drawable2 != null) {
            setNavigationIcon(drawable2);
        }
        CharSequence text3 = obtainStyledAttributes.mWrapped.getText(16);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(12);
        if (drawable3 != null) {
            setLogo(drawable3);
        }
        CharSequence text4 = obtainStyledAttributes.mWrapped.getText(13);
        if (!TextUtils.isEmpty(text4)) {
            if (!TextUtils.isEmpty(text4) && this.mLogoView == null) {
                this.mLogoView = new AppCompatImageView(getContext());
            }
            AppCompatImageView appCompatImageView = this.mLogoView;
            if (appCompatImageView != null) {
                appCompatImageView.setContentDescription(text4);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(30)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(30);
            this.mTitleTextColor = colorStateList;
            AppCompatTextView appCompatTextView = this.mTitleTextView;
            if (appCompatTextView != null) {
                appCompatTextView.setTextColor(colorStateList);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(21)) {
            ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(21);
            this.mSubtitleTextColor = colorStateList2;
            AppCompatTextView appCompatTextView2 = this.mSubtitleTextView;
            if (appCompatTextView2 != null) {
                appCompatTextView2.setTextColor(colorStateList2);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(15)) {
            inflateMenu(obtainStyledAttributes.mWrapped.getResourceId(15, 0));
        }
        obtainStyledAttributes.recycle();
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExpandedActionViewMenuPresenter implements MenuPresenter {
        public MenuItemImpl mCurrentExpandedItem;
        public MenuBuilder mMenu;

        public ExpandedActionViewMenuPresenter() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
            Toolbar toolbar = Toolbar.this;
            KeyEvent.Callback callback = toolbar.mExpandedActionView;
            if (callback instanceof CollapsibleActionView) {
                ((CollapsibleActionView) callback).onActionViewCollapsed();
            }
            toolbar.removeView(toolbar.mExpandedActionView);
            toolbar.removeView(toolbar.mCollapseButtonView);
            toolbar.mExpandedActionView = null;
            for (int size = toolbar.mHiddenViews.size() - 1; size >= 0; size--) {
                toolbar.addView((View) toolbar.mHiddenViews.get(size));
            }
            toolbar.mHiddenViews.clear();
            this.mCurrentExpandedItem = null;
            toolbar.requestLayout();
            menuItemImpl.mIsActionViewExpanded = false;
            menuItemImpl.mMenu.onItemsChanged(false);
            toolbar.updateBackInvokedCallbackState();
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final boolean expandItemActionView(MenuItemImpl menuItemImpl) {
            ViewParent parent;
            final Toolbar toolbar = Toolbar.this;
            if (toolbar.mCollapseButtonView == null) {
                AppCompatImageButton appCompatImageButton = new AppCompatImageButton(toolbar.getContext(), null, R.attr.toolbarNavigationButtonStyle);
                toolbar.mCollapseButtonView = appCompatImageButton;
                appCompatImageButton.setImageDrawable(toolbar.mCollapseIcon);
                toolbar.mCollapseButtonView.setContentDescription(toolbar.mCollapseDescription);
                LayoutParams generateDefaultLayoutParams = Toolbar.generateDefaultLayoutParams();
                generateDefaultLayoutParams.gravity = (toolbar.mButtonGravity & 112) | 8388611;
                generateDefaultLayoutParams.mViewType = 2;
                toolbar.mCollapseButtonView.setLayoutParams(generateDefaultLayoutParams);
                toolbar.mCollapseButtonView.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.Toolbar.4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = Toolbar.this.mExpandedMenuPresenter;
                        MenuItemImpl menuItemImpl2 = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
                        if (menuItemImpl2 != null) {
                            menuItemImpl2.collapseActionView();
                        }
                    }
                });
                SeslViewReflector.semSetHoverPopupType(toolbar.mCollapseButtonView, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
                if (!TextUtils.isEmpty(toolbar.mCollapseDescription)) {
                    toolbar.mCollapseButtonView.setTooltipText(toolbar.mCollapseDescription);
                }
            }
            ViewParent parent2 = toolbar.mCollapseButtonView.getParent();
            if (parent2 != toolbar) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar.mCollapseButtonView);
                }
                toolbar.addView(toolbar.mCollapseButtonView);
            }
            View actionView = menuItemImpl.getActionView();
            toolbar.mExpandedActionView = actionView;
            this.mCurrentExpandedItem = menuItemImpl;
            if (actionView != null && (parent = actionView.getParent()) != toolbar) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar.mExpandedActionView);
                }
                LayoutParams generateDefaultLayoutParams2 = Toolbar.generateDefaultLayoutParams();
                generateDefaultLayoutParams2.gravity = (toolbar.mButtonGravity & 112) | 8388611;
                generateDefaultLayoutParams2.mViewType = 2;
                toolbar.mExpandedActionView.setLayoutParams(generateDefaultLayoutParams2);
                toolbar.addView(toolbar.mExpandedActionView);
            }
            for (int childCount = toolbar.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = toolbar.getChildAt(childCount);
                if (((LayoutParams) childAt.getLayoutParams()).mViewType != 2 && childAt != toolbar.mMenuView) {
                    toolbar.removeViewAt(childCount);
                    toolbar.mHiddenViews.add(childAt);
                }
            }
            toolbar.requestLayout();
            menuItemImpl.mIsActionViewExpanded = true;
            menuItemImpl.mMenu.onItemsChanged(false);
            KeyEvent.Callback callback = toolbar.mExpandedActionView;
            if (callback instanceof CollapsibleActionView) {
                ((CollapsibleActionView) callback).onActionViewExpanded();
            }
            toolbar.updateBackInvokedCallbackState();
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final boolean flagActionItems() {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (menuBuilder2 != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                if (menuBuilder != null) {
                    int size = menuBuilder.mItems.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            return;
                        }
                    }
                }
                collapseItemActionView(this.mCurrentExpandedItem);
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }
    }
}
