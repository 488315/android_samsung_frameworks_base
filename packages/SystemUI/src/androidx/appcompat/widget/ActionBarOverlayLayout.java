package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.Toolbar.ExpandedActionViewMenuPresenter;
import androidx.core.graphics.Insets;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent, NestedScrollingParent2, NestedScrollingParent3 {
    public static final WindowInsetsCompat NON_EMPTY_SYSTEM_WINDOW_INSETS;
    public static final Rect ZERO_INSETS;
    public boolean mActionBarExtendsIntoSystemInsets;
    public int mActionBarHeight;
    public ActionBarContainer mActionBarTop;
    public WindowDecorActionBar mActionBarVisibilityCallback;
    public final AnonymousClass3 mAddActionBarHideOffset;
    public boolean mAnimatingForFling;
    public final Rect mBaseContentInsets;
    public WindowInsetsCompat mBaseInnerInsets;
    public ContentFrameLayout mContent;
    public final Rect mContentInsets;
    public ViewPropertyAnimator mCurrentActionBarTopAnimator;
    public boolean mDecorFitsSystemWindows;
    public ToolbarWidgetWrapper mDecorToolbar;
    public OverScroller mFlingEstimator;
    public boolean mHideOnContentScroll;
    public int mHideOnContentScrollReference;
    public WindowInsetsCompat mInnerInsets;
    public final Rect mLastBaseContentInsets;
    public WindowInsetsCompat mLastBaseInnerInsets;
    public WindowInsetsCompat mLastInnerInsets;
    public int mLastSystemUiVisibility;
    public final NoSystemUiLayoutFlagView mNoSystemUiLayoutFlagView;
    public boolean mOverlayMode;
    public final NestedScrollingParentHelper mParentHelper;
    public final AnonymousClass2 mRemoveActionBarHideOffset;
    public final Rect mSystemInsets;
    public final Rect mTmpRect;
    public final AnonymousClass1 mTopAnimatorListener;
    public Drawable mWindowContentOverlay;
    public int mWindowVisibility;
    public static final Rect EMPTY_RECT = new Rect();
    public static final int[] ATTRS = {R.attr.actionBarSize, android.R.attr.windowContentOverlay};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.widget.ActionBarOverlayLayout$2, reason: invalid class name */
    public class AnonymousClass2 implements Runnable {
        public AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.appcompat.widget.ActionBarOverlayLayout$3, reason: invalid class name */
    public class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NoSystemUiLayoutFlagView extends View {
        public NoSystemUiLayoutFlagView(Context context) {
            super(context);
            setWillNotDraw(true);
        }

        @Override // android.view.View
        public final int getWindowSystemUiVisibility() {
            return 0;
        }
    }

    static {
        WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder();
        Insets of = Insets.of(0, 1, 0, 1);
        WindowInsetsCompat.BuilderImpl30 builderImpl30 = builder.mImpl;
        builderImpl30.setSystemWindowInsets(of);
        NON_EMPTY_SYSTEM_WINDOW_INSETS = builderImpl30.build();
        ZERO_INSETS = new Rect();
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public static boolean setMargin(View view, Rect rect, boolean z) {
        boolean z2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        int i2 = rect.left;
        if (i != i2) {
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i2;
            z2 = true;
        } else {
            z2 = false;
        }
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        int i4 = rect.top;
        if (i3 != i4) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = i4;
            z2 = true;
        }
        int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int i6 = rect.right;
        if (i5 != i6) {
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i6;
            z2 = true;
        }
        if (z) {
            int i7 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
            int i8 = rect.bottom;
            if (i7 != i8) {
                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = i8;
                return true;
            }
        }
        return z2;
    }

    public static boolean setPadding(Rect rect, View view) {
        if (view.getPaddingLeft() == rect.left && view.getPaddingTop() == rect.top && view.getPaddingRight() == rect.right) {
            return false;
        }
        view.setPadding(rect.left, rect.top, rect.right, view.getPaddingBottom());
        return true;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int i;
        super.draw(canvas);
        if (this.mWindowContentOverlay != null) {
            if (this.mActionBarTop.getVisibility() == 0) {
                i = (int) (this.mActionBarTop.getTranslationY() + this.mActionBarTop.getBottom() + 0.5f);
            } else {
                i = 0;
            }
            this.mWindowContentOverlay.setBounds(0, i, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + i);
            this.mWindowContentOverlay.draw(canvas);
        }
    }

    @Override // android.view.View
    public final boolean fitSystemWindows(Rect rect) {
        return super.fitSystemWindows(rect);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    public final void haltActionBarHideOffsetAnimations() {
        removeCallbacks(this.mRemoveActionBarHideOffset);
        removeCallbacks(this.mAddActionBarHideOffset);
        ViewPropertyAnimator viewPropertyAnimator = this.mCurrentActionBarTopAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void init(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(ATTRS);
        this.mActionBarHeight = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        this.mWindowContentOverlay = drawable;
        setWillNotDraw(drawable == null);
        obtainStyledAttributes.recycle();
        this.mFlingEstimator = new OverScroller(context);
    }

    public final void initFeature(int i) {
        pullChildren();
        if (i == 2) {
            this.mDecorToolbar.getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else if (i == 5) {
            this.mDecorToolbar.getClass();
            Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
        } else {
            if (i != 109) {
                return;
            }
            this.mOverlayMode = true;
        }
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        pullChildren();
        int windowSystemUiVisibility = getWindowSystemUiVisibility();
        boolean z = true;
        boolean z2 = (windowSystemUiVisibility & 256) != 0;
        boolean z3 = (windowSystemUiVisibility & 1536) != 0;
        NoSystemUiLayoutFlagView noSystemUiLayoutFlagView = this.mNoSystemUiLayoutFlagView;
        WindowInsetsCompat windowInsetsCompat = NON_EMPTY_SYSTEM_WINDOW_INSETS;
        Rect rect = this.mTmpRect;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.computeSystemWindowInsets(noSystemUiLayoutFlagView, windowInsetsCompat, rect);
        boolean equals = this.mTmpRect.equals(ZERO_INSETS);
        this.mDecorFitsSystemWindows = !equals;
        boolean z4 = equals || (z2 && z3);
        this.mActionBarExtendsIntoSystemInsets = z4;
        WindowDecorActionBar windowDecorActionBar = this.mActionBarVisibilityCallback;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mContentAnimations = (z2 || z4) ? false : true;
        }
        WindowInsetsCompat windowInsetsCompat2 = WindowInsetsCompat.toWindowInsetsCompat(this, windowInsets);
        this.mSystemInsets.set(windowInsetsCompat2.getSystemWindowInsetLeft(), windowInsetsCompat2.getSystemWindowInsetTop(), windowInsetsCompat2.getSystemWindowInsetRight(), windowInsetsCompat2.getSystemWindowInsetBottom());
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        Rect rect2 = this.mSystemInsets;
        boolean padding = this.mActionBarExtendsIntoSystemInsets ? setPadding(rect2, actionBarContainer) | setMargin(actionBarContainer, EMPTY_RECT, false) : setMargin(actionBarContainer, rect2, false) | setPadding(EMPTY_RECT, actionBarContainer);
        ViewCompat.Api21Impl.computeSystemWindowInsets(this, windowInsetsCompat2, this.mBaseContentInsets);
        Rect rect3 = this.mBaseContentInsets;
        int i = rect3.left;
        int i2 = rect3.top;
        int i3 = rect3.right;
        int i4 = rect3.bottom;
        WindowInsetsCompat.Impl impl = windowInsetsCompat2.mImpl;
        WindowInsetsCompat inset = impl.inset(i, i2, i3, i4);
        this.mBaseInnerInsets = inset;
        if (!this.mLastBaseInnerInsets.equals(inset)) {
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
            padding = true;
        }
        if (this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            z = padding;
        } else {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (z) {
            requestLayout();
        }
        return impl.consumeDisplayCutout().mImpl.consumeSystemWindowInsets().mImpl.consumeStableInsets().toWindowInsets();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        init(getContext());
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api20Impl.requestApplyInsets(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + paddingLeft;
                int i7 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + paddingTop;
                childAt.layout(i6, i7, measuredWidth + i6, measuredHeight + i7);
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int measuredHeight;
        pullChildren();
        measureChildWithMargins(this.mActionBarTop, i, 0, i2, 0);
        LayoutParams layoutParams = (LayoutParams) this.mActionBarTop.getLayoutParams();
        int max = Math.max(0, this.mActionBarTop.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
        int max2 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        int combineMeasuredStates = View.combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z = (getWindowSystemUiVisibility() & 256) != 0;
        if (z) {
            measuredHeight = this.mActionBarHeight;
            if (this.mActionBarExtendsIntoSystemInsets) {
                measuredHeight += this.mSystemInsets.top;
            }
        } else {
            measuredHeight = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0;
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        WindowInsetsCompat windowInsetsCompat = this.mBaseInnerInsets;
        this.mInnerInsets = windowInsetsCompat;
        if (this.mOverlayMode || z || !this.mDecorFitsSystemWindows) {
            Insets of = this.mActionBarExtendsIntoSystemInsets ? Insets.of(windowInsetsCompat.getSystemWindowInsetLeft(), Math.max(this.mInnerInsets.getSystemWindowInsetTop(), measuredHeight), this.mInnerInsets.getSystemWindowInsetRight(), Math.max(this.mInnerInsets.getSystemWindowInsetBottom(), 0)) : Insets.of(windowInsetsCompat.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + measuredHeight, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom());
            WindowInsetsCompat.BuilderImpl30 builderImpl30 = new WindowInsetsCompat.Builder(this.mInnerInsets).mImpl;
            builderImpl30.setSystemWindowInsets(of);
            this.mInnerInsets = builderImpl30.build();
        } else {
            if (this.mActionBarExtendsIntoSystemInsets) {
                Rect rect = this.mContentInsets;
                rect.top = Math.max(rect.top, measuredHeight);
                Rect rect2 = this.mContentInsets;
                rect2.bottom = Math.max(rect2.bottom, 0);
            } else {
                Rect rect3 = this.mContentInsets;
                rect3.top += measuredHeight;
                rect3.bottom = rect3.bottom;
            }
            this.mInnerInsets = this.mInnerInsets.mImpl.inset(0, measuredHeight, 0, 0);
        }
        setMargin(this.mContent, this.mContentInsets, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            WindowInsetsCompat windowInsetsCompat2 = this.mInnerInsets;
            this.mLastInnerInsets = windowInsetsCompat2;
            ViewCompat.dispatchApplyWindowInsets(windowInsetsCompat2, this.mContent);
        }
        measureChildWithMargins(this.mContent, i, 0, i2, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.mContent.getLayoutParams();
        int max3 = Math.max(max, this.mContent.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin);
        int max4 = Math.max(max2, this.mContent.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin);
        int combineMeasuredStates2 = View.combineMeasuredStates(combineMeasuredStates, this.mContent.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + max3, getSuggestedMinimumWidth()), i, combineMeasuredStates2), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + max4, getSuggestedMinimumHeight()), i2, combineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.mHideOnContentScroll || !z) {
            return false;
        }
        this.mFlingEstimator.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight()) {
            haltActionBarHideOffsetAnimations();
            this.mAddActionBarHideOffset.run();
        } else {
            haltActionBarHideOffsetAnimations();
            this.mRemoveActionBarHideOffset.run();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        onNestedScroll(view, i, i2, i3, i4, i5);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        if (i2 == 0) {
            onNestedScrollAccepted(view, view2, i);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return i2 == 0 && onStartNestedScroll(view, view2, i);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        if (i == 0) {
            onStopNestedScroll(view);
        }
    }

    @Override // android.view.View
    public final void onWindowSystemUiVisibilityChanged(int i) {
        super.onWindowSystemUiVisibilityChanged(i);
        pullChildren();
        int i2 = this.mLastSystemUiVisibility ^ i;
        this.mLastSystemUiVisibility = i;
        boolean z = (i & 4) == 0;
        boolean z2 = (i & 256) != 0;
        WindowDecorActionBar windowDecorActionBar = this.mActionBarVisibilityCallback;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mContentAnimations = (z2 || this.mActionBarExtendsIntoSystemInsets) ? false : true;
            if (z || !z2) {
                if (windowDecorActionBar.mHiddenBySystem) {
                    windowDecorActionBar.mHiddenBySystem = false;
                    windowDecorActionBar.updateVisibility(true);
                }
            } else if (!windowDecorActionBar.mHiddenBySystem) {
                windowDecorActionBar.mHiddenBySystem = true;
                windowDecorActionBar.updateVisibility(true);
            }
        }
        if ((i2 & 256) == 0 || this.mActionBarVisibilityCallback == null) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api20Impl.requestApplyInsets(this);
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mWindowVisibility = i;
        WindowDecorActionBar windowDecorActionBar = this.mActionBarVisibilityCallback;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mCurWindowVisibility = i;
        }
    }

    public final void pullChildren() {
        if (this.mContent == null) {
            this.mContent = (ContentFrameLayout) findViewById(R.id.action_bar_activity_content);
            this.mActionBarTop = (ActionBarContainer) findViewById(R.id.action_bar_container);
            View findViewById = findViewById(R.id.action_bar);
            if (!(findViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(findViewById.getClass().getSimpleName()));
            }
            Toolbar toolbar = (Toolbar) findViewById;
            if (toolbar.mWrapper == null) {
                toolbar.mWrapper = new ToolbarWidgetWrapper(toolbar, true);
            }
            this.mDecorToolbar = toolbar.mWrapper;
        }
    }

    public final void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = z;
            if (z) {
                return;
            }
            haltActionBarHideOffsetAnimations();
            haltActionBarHideOffsetAnimations();
            this.mActionBarTop.setTranslationY(-Math.max(0, Math.min(0, this.mActionBarTop.getHeight())));
        }
    }

    public final void setMenu(MenuBuilder menuBuilder, MenuPresenter.Callback callback) {
        pullChildren();
        ToolbarWidgetWrapper toolbarWidgetWrapper = this.mDecorToolbar;
        ActionMenuPresenter actionMenuPresenter = toolbarWidgetWrapper.mActionMenuPresenter;
        Toolbar toolbar = toolbarWidgetWrapper.mToolbar;
        if (actionMenuPresenter == null) {
            toolbarWidgetWrapper.mActionMenuPresenter = new ActionMenuPresenter(toolbar.getContext());
        }
        ActionMenuPresenter actionMenuPresenter2 = toolbarWidgetWrapper.mActionMenuPresenter;
        actionMenuPresenter2.mCallback = callback;
        if (menuBuilder == null && toolbar.mMenuView == null) {
            return;
        }
        toolbar.ensureMenuView();
        MenuBuilder menuBuilder2 = toolbar.mMenuView.mMenu;
        if (menuBuilder2 == menuBuilder) {
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(toolbar.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(toolbar.mExpandedMenuPresenter);
        }
        if (toolbar.mExpandedMenuPresenter == null) {
            toolbar.mExpandedMenuPresenter = toolbar.new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter2.mExpandedActionViewsExclusive = true;
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter2, toolbar.mPopupContext);
            menuBuilder.addMenuPresenter(toolbar.mExpandedMenuPresenter, toolbar.mPopupContext);
        } else {
            actionMenuPresenter2.initForMenu(toolbar.mPopupContext, null);
            toolbar.mExpandedMenuPresenter.initForMenu(toolbar.mPopupContext, null);
            actionMenuPresenter2.updateMenuView(true);
            toolbar.mExpandedMenuPresenter.updateMenuView(true);
        }
        ActionMenuView actionMenuView = toolbar.mMenuView;
        int i = toolbar.mPopupTheme;
        if (actionMenuView.mPopupTheme != i) {
            actionMenuView.mPopupTheme = i;
            if (i == 0) {
                actionMenuView.mPopupContext = actionMenuView.getContext();
            } else {
                actionMenuView.mPopupContext = new ContextThemeWrapper(actionMenuView.getContext(), i);
            }
        }
        ActionMenuView actionMenuView2 = toolbar.mMenuView;
        actionMenuView2.mPresenter = actionMenuPresenter2;
        actionMenuPresenter2.mMenuView = actionMenuView2;
        actionMenuView2.mMenu = actionMenuPresenter2.mMenu;
        toolbar.mOuterActionMenuPresenter = actionMenuPresenter2;
        toolbar.updateBackInvokedCallbackState();
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.appcompat.widget.ActionBarOverlayLayout$1] */
    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mSystemInsets = new Rect();
        this.mTmpRect = new Rect();
        this.mDecorFitsSystemWindows = true;
        this.mActionBarExtendsIntoSystemInsets = false;
        new Rect();
        new Rect();
        new Rect();
        new Rect();
        WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.CONSUMED;
        this.mBaseInnerInsets = windowInsetsCompat;
        this.mLastBaseInnerInsets = windowInsetsCompat;
        this.mInnerInsets = windowInsetsCompat;
        this.mLastInnerInsets = windowInsetsCompat;
        this.mTopAnimatorListener = new AnimatorListenerAdapter() { // from class: androidx.appcompat.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = null;
                actionBarOverlayLayout.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = null;
                actionBarOverlayLayout.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new AnonymousClass2();
        this.mAddActionBarHideOffset = new AnonymousClass3();
        init(context);
        this.mParentHelper = new NestedScrollingParentHelper(this);
        NoSystemUiLayoutFlagView noSystemUiLayoutFlagView = new NoSystemUiLayoutFlagView(context);
        this.mNoSystemUiLayoutFlagView = noSystemUiLayoutFlagView;
        addView(noSystemUiLayoutFlagView);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            onNestedScroll(view, i, i2, i3, i4);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet;
        this.mParentHelper.mNestedScrollAxesTouch = i;
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        this.mHideOnContentScrollReference = actionBarContainer != null ? -((int) actionBarContainer.getTranslationY()) : 0;
        haltActionBarHideOffsetAnimations();
        WindowDecorActionBar windowDecorActionBar = this.mActionBarVisibilityCallback;
        if (windowDecorActionBar == null || (viewPropertyAnimatorCompatSet = windowDecorActionBar.mCurrentShowAnim) == null) {
            return;
        }
        viewPropertyAnimatorCompatSet.cancel();
        windowDecorActionBar.mCurrentShowAnim = null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.mActionBarTop.getVisibility() != 0) {
            return false;
        }
        return this.mHideOnContentScroll;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        if (!this.mHideOnContentScroll || this.mAnimatingForFling) {
            return;
        }
        if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
            haltActionBarHideOffsetAnimations();
            postDelayed(this.mRemoveActionBarHideOffset, 600L);
        } else {
            haltActionBarHideOffsetAnimations();
            postDelayed(this.mAddActionBarHideOffset, 600L);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        this.mHideOnContentScrollReference = this.mHideOnContentScrollReference + i2;
        haltActionBarHideOffsetAnimations();
        this.mActionBarTop.setTranslationY(-Math.max(0, Math.min(r1, this.mActionBarTop.getHeight())));
    }
}
