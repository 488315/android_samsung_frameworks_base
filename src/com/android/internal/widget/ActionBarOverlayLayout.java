package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.Log;
import android.util.Property;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import android.widget.Toolbar;
import com.android.internal.R;
import com.android.internal.view.menu.MenuPresenter;

/* loaded from: classes6.dex */
public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent {
    private static final String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY;
    private ActionBarContainer mActionBarBottom;
    private boolean mActionBarExtendsIntoSystemInsets;
    private int mActionBarHeight;
    private ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset;
    private boolean mAnimatingForFling;
    private final Rect mBaseContentInsets;
    private WindowInsets mBaseInnerInsets;
    private final Animator.AnimatorListener mBottomAnimatorListener;
    private View mContent;
    private final Rect mContentInsets;
    private ViewPropertyAnimator mCurrentActionBarBottomAnimator;
    private ViewPropertyAnimator mCurrentActionBarTopAnimator;
    private boolean mDecorFitsSystemWindows;
    private DecorToolbar mDecorToolbar;
    private OverScroller mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private WindowInsets mInnerInsets;
    private final Rect mLastBaseContentInsets;
    private WindowInsets mLastBaseInnerInsets;
    private WindowInsets mLastInnerInsets;
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final Runnable mRemoveActionBarHideOffset;
    private final Rect mSystemInsets;
    private final Animator.AnimatorListener mTopAnimatorListener;
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility;
    private static final Rect EMPTY_RECT = new Rect();
    public static final Property<ActionBarOverlayLayout, Integer> ACTION_BAR_HIDE_OFFSET = new IntProperty<ActionBarOverlayLayout>("actionBarHideOffset") { // from class: com.android.internal.widget.ActionBarOverlayLayout.5
        @Override // android.util.IntProperty
        public void setValue(ActionBarOverlayLayout actionBarOverlayLayout, int i) {
            actionBarOverlayLayout.setActionBarHideOffset(i);
        }

        @Override // android.util.Property
        public Integer get(ActionBarOverlayLayout actionBarOverlayLayout) {
            return Integer.valueOf(actionBarOverlayLayout.getActionBarHideOffset());
        }
    };
    static final int[] ATTRS = {16843499, 16842841};

    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z);

        void hideForSystem();

        void onContentScrollStarted();

        void onContentScrollStopped();

        void onWindowVisibilityChanged(int i);

        void showForSystem();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarOverlayLayout(Context context) {
        super(context);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mSystemInsets = new Rect();
        this.mBaseInnerInsets = WindowInsets.CONSUMED;
        this.mLastBaseInnerInsets = WindowInsets.CONSUMED;
        this.mInnerInsets = WindowInsets.CONSUMED;
        this.mLastInnerInsets = WindowInsets.CONSUMED;
        this.mDecorFitsSystemWindows = true;
        this.mActionBarExtendsIntoSystemInsets = false;
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.mTopAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mBottomAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (ActionBarOverlayLayout.this.mActionBarBottom == null || ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() == 8) {
                    return;
                }
                ActionBarOverlayLayout actionBarOverlayLayout2 = ActionBarOverlayLayout.this;
                actionBarOverlayLayout2.mCurrentActionBarBottomAnimator = actionBarOverlayLayout2.mActionBarBottom.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        };
        this.mAddActionBarHideOffset = new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (ActionBarOverlayLayout.this.mActionBarBottom == null || ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() == 8) {
                    return;
                }
                ActionBarOverlayLayout actionBarOverlayLayout2 = ActionBarOverlayLayout.this;
                actionBarOverlayLayout2.mCurrentActionBarBottomAnimator = actionBarOverlayLayout2.mActionBarBottom.animate().translationY(ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        };
        init(context);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mSystemInsets = new Rect();
        this.mBaseInnerInsets = WindowInsets.CONSUMED;
        this.mLastBaseInnerInsets = WindowInsets.CONSUMED;
        this.mInnerInsets = WindowInsets.CONSUMED;
        this.mLastInnerInsets = WindowInsets.CONSUMED;
        this.mDecorFitsSystemWindows = true;
        this.mActionBarExtendsIntoSystemInsets = false;
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.mTopAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mBottomAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (ActionBarOverlayLayout.this.mActionBarBottom == null || ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() == 8) {
                    return;
                }
                ActionBarOverlayLayout actionBarOverlayLayout2 = ActionBarOverlayLayout.this;
                actionBarOverlayLayout2.mCurrentActionBarBottomAnimator = actionBarOverlayLayout2.mActionBarBottom.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        };
        this.mAddActionBarHideOffset = new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
            @Override // java.lang.Runnable
            public void run() {
                ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (ActionBarOverlayLayout.this.mActionBarBottom == null || ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() == 8) {
                    return;
                }
                ActionBarOverlayLayout actionBarOverlayLayout2 = ActionBarOverlayLayout.this;
                actionBarOverlayLayout2.mCurrentActionBarBottomAnimator = actionBarOverlayLayout2.mActionBarBottom.animate().translationY(ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        };
        init(context);
    }

    private void init(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(ATTRS);
        this.mActionBarHeight = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        this.mWindowContentOverlay = drawable;
        setWillNotDraw(drawable == null);
        obtainStyledAttributes.recycle();
        this.mIgnoreWindowContentOverlay = context.getApplicationInfo().targetSdkVersion < 19;
        this.mFlingEstimator = new OverScroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback;
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            int i = this.mLastSystemUiVisibility;
            if (i != 0) {
                onWindowSystemUiVisibilityChanged(i);
                requestApplyInsets();
            }
        }
    }

    public void setOverlayMode(boolean z) {
        this.mOverlayMode = z;
        this.mIgnoreWindowContentOverlay = z && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.mHasNonEmbeddedTabs = z;
    }

    public void setShowingForActionMode(boolean z) {
        if (z) {
            if ((getWindowSystemUiVisibility() & 1280) == 1280) {
                setDisabledSystemUiVisibility(4);
                return;
            }
            return;
        }
        setDisabledSystemUiVisibility(0);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        init(getContext());
        requestApplyInsets();
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        super.onWindowSystemUiVisibilityChanged(i);
        pullChildren();
        int i2 = this.mLastSystemUiVisibility ^ i;
        this.mLastSystemUiVisibility = i;
        boolean z = (i & 4) == 0;
        boolean z2 = (i & 256) != 0;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.enableContentAnimations((z2 || this.mActionBarExtendsIntoSystemInsets) ? false : true);
            if (z || !z2) {
                this.mActionBarVisibilityCallback.showForSystem();
            } else {
                this.mActionBarVisibilityCallback.hideForSystem();
            }
        }
        if ((i2 & 256) == 0 || this.mActionBarVisibilityCallback == null) {
            return;
        }
        requestApplyInsets();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mWindowVisibility = i;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onWindowVisibilityChanged(i);
        }
    }

    private boolean applyInsets(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (z) {
            return setMargin(view, EMPTY_RECT, z2, z3, z4, z5) | setPadding(view, rect, z2, z3, z4, z5);
        }
        return setMargin(view, rect, z2, z3, z4, z5) | setPadding(view, EMPTY_RECT, z2, z3, z4, z5);
    }

    private boolean setPadding(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        if ((!z || view.getPaddingLeft() == rect.left) && ((!z2 || view.getPaddingTop() == rect.top) && ((!z3 || view.getPaddingRight() == rect.right) && (!z4 || view.getPaddingBottom() == rect.bottom)))) {
            return false;
        }
        view.setPadding(z ? rect.left : view.getPaddingLeft(), z2 ? rect.top : view.getPaddingTop(), z3 ? rect.right : view.getPaddingRight(), z4 ? rect.bottom : view.getPaddingBottom());
        return true;
    }

    private boolean setMargin(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!z || layoutParams.leftMargin == rect.left) {
            z5 = false;
        } else {
            layoutParams.leftMargin = rect.left;
            z5 = true;
        }
        if (z2 && layoutParams.topMargin != rect.top) {
            layoutParams.topMargin = rect.top;
            z5 = true;
        }
        if (z3 && layoutParams.rightMargin != rect.right) {
            layoutParams.rightMargin = rect.right;
            z5 = true;
        }
        if (!z4 || layoutParams.bottomMargin == rect.bottom) {
            return z5;
        }
        layoutParams.bottomMargin = rect.bottom;
        return true;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        pullChildren();
        int windowSystemUiVisibility = getWindowSystemUiVisibility();
        boolean z = false;
        boolean z2 = true;
        boolean z3 = (windowSystemUiVisibility & 256) != 0;
        boolean z4 = (windowSystemUiVisibility & 1536) != 0;
        boolean hasContentOnApplyWindowInsetsListener = hasContentOnApplyWindowInsetsListener();
        this.mDecorFitsSystemWindows = hasContentOnApplyWindowInsetsListener;
        boolean z5 = !hasContentOnApplyWindowInsetsListener || (z3 && z4);
        this.mActionBarExtendsIntoSystemInsets = z5;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            if (!z3 && !z5) {
                z = true;
            }
            actionBarVisibilityCallback.enableContentAnimations(z);
        }
        Insets systemWindowInsets = windowInsets.getSystemWindowInsets();
        this.mSystemInsets.set(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
        boolean applyInsets = applyInsets(this.mActionBarTop, this.mSystemInsets, this.mActionBarExtendsIntoSystemInsets, true, true, true, false);
        ActionBarContainer actionBarContainer = this.mActionBarBottom;
        if (actionBarContainer != null) {
            applyInsets |= applyInsets(actionBarContainer, this.mSystemInsets, this.mActionBarExtendsIntoSystemInsets, true, false, true, true);
        }
        computeSystemWindowInsets(windowInsets, this.mBaseContentInsets);
        WindowInsets inset = windowInsets.inset(this.mBaseContentInsets);
        this.mBaseInnerInsets = inset;
        if (!this.mLastBaseInnerInsets.equals(inset)) {
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
            applyInsets = true;
        }
        if (this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            z2 = applyInsets;
        } else {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (z2) {
            requestLayout();
        }
        return WindowInsets.CONSUMED;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int measuredHeight;
        int i5;
        ActionBarContainer actionBarContainer;
        pullChildren();
        measureChildWithMargins(this.mActionBarTop, i, 0, i2, 0);
        LayoutParams layoutParams = (LayoutParams) this.mActionBarTop.getLayoutParams();
        int max = Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int max2 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int combineMeasuredStates = combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
        ActionBarContainer actionBarContainer2 = this.mActionBarBottom;
        if (actionBarContainer2 != null) {
            measureChildWithMargins(actionBarContainer2, i, 0, i2, 0);
            i3 = i;
            i4 = i2;
            LayoutParams layoutParams2 = (LayoutParams) this.mActionBarBottom.getLayoutParams();
            max = Math.max(max, this.mActionBarBottom.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
            max2 = Math.max(max2, this.mActionBarBottom.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
            combineMeasuredStates = combineMeasuredStates(combineMeasuredStates, this.mActionBarBottom.getMeasuredState());
        } else {
            i3 = i;
            i4 = i2;
        }
        boolean z = (getWindowSystemUiVisibility() & 256) != 0;
        if (z) {
            measuredHeight = this.mActionBarHeight;
            if (this.mActionBarExtendsIntoSystemInsets) {
                measuredHeight += this.mSystemInsets.top;
            }
            if (this.mHasNonEmbeddedTabs && this.mActionBarTop.getTabContainer() != null) {
                measuredHeight += this.mActionBarHeight;
            }
        } else {
            measuredHeight = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0;
        }
        if (!this.mDecorToolbar.isSplit() || (actionBarContainer = this.mActionBarBottom) == null) {
            i5 = 0;
        } else if (z) {
            i5 = this.mActionBarHeight;
            if (this.mActionBarExtendsIntoSystemInsets) {
                i5 += this.mSystemInsets.bottom;
            }
        } else {
            i5 = actionBarContainer.getMeasuredHeight();
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        WindowInsets windowInsets = this.mBaseInnerInsets;
        this.mInnerInsets = windowInsets;
        if (!this.mOverlayMode && !z && this.mDecorFitsSystemWindows) {
            if (this.mActionBarExtendsIntoSystemInsets) {
                Rect rect = this.mContentInsets;
                rect.top = Math.max(rect.top, measuredHeight);
                Rect rect2 = this.mContentInsets;
                rect2.bottom = Math.max(rect2.bottom, i5);
            } else {
                this.mContentInsets.top += measuredHeight;
                this.mContentInsets.bottom += i5;
            }
            this.mInnerInsets = this.mInnerInsets.inset(0, measuredHeight, 0, i5);
        } else if (this.mActionBarExtendsIntoSystemInsets) {
            this.mInnerInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), Math.max(this.mInnerInsets.getSystemWindowInsetTop(), measuredHeight), this.mInnerInsets.getSystemWindowInsetRight(), Math.max(this.mInnerInsets.getSystemWindowInsetBottom(), i5));
        } else {
            this.mInnerInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + measuredHeight, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + i5);
        }
        setMargin(this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            WindowInsets windowInsets2 = this.mInnerInsets;
            this.mLastInnerInsets = windowInsets2;
            this.mContent.dispatchApplyWindowInsets(windowInsets2);
        }
        int i6 = i3;
        int i7 = i4;
        measureChildWithMargins(this.mContent, i6, 0, i7, 0);
        LayoutParams layoutParams3 = (LayoutParams) this.mContent.getLayoutParams();
        int max3 = Math.max(max, this.mContent.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin);
        int max4 = Math.max(max2, this.mContent.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin);
        int combineMeasuredStates2 = combineMeasuredStates(combineMeasuredStates, this.mContent.getMeasuredState());
        setMeasuredDimension(resolveSizeAndState(Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i6, combineMeasuredStates2), resolveSizeAndState(Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i7, combineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = layoutParams.leftMargin + paddingLeft;
                if (childAt == this.mActionBarBottom) {
                    i5 = (paddingBottom - measuredHeight) - layoutParams.bottomMargin;
                } else {
                    i5 = paddingTop + layoutParams.topMargin;
                }
                childAt.layout(i7, i5, measuredWidth + i7, measuredHeight + i5);
            }
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mWindowContentOverlay == null || this.mIgnoreWindowContentOverlay) {
            return;
        }
        int bottom = this.mActionBarTop.getVisibility() == 0 ? (int) (this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0;
        this.mWindowContentOverlay.setBounds(0, bottom, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + bottom);
        this.mWindowContentOverlay.draw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if ((i & 2) == 0 || this.mActionBarTop.getVisibility() != 0) {
            return false;
        }
        return this.mHideOnContentScroll;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        this.mHideOnContentScrollReference = getActionBarHideOffset();
        haltActionBarHideOffsetAnimations();
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int i5 = this.mHideOnContentScrollReference + i2;
        this.mHideOnContentScrollReference = i5;
        setActionBarHideOffset(i5);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View view) {
        super.onStopNestedScroll(view);
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                postRemoveActionBarHideOffset();
            } else {
                postAddActionBarHideOffset();
            }
        }
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.mHideOnContentScroll || !z) {
            return false;
        }
        if (shouldHideActionBarOnFling(f, f2)) {
            addActionBarHideOffset();
        } else {
            removeActionBarHideOffset();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    void pullChildren() {
        if (this.mContent == null) {
            this.mContent = findViewById(16908290);
            this.mActionBarTop = (ActionBarContainer) findViewById(R.id.action_bar_container);
            this.mDecorToolbar = getDecorToolbar(findViewById(R.id.action_bar));
            this.mActionBarBottom = (ActionBarContainer) findViewById(R.id.split_action_bar);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = z;
            if (z) {
                return;
            }
            stopNestedScroll();
            haltActionBarHideOffsetAnimations();
            setActionBarHideOffset(0);
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    public void setActionBarHideOffset(int i) {
        haltActionBarHideOffsetAnimations();
        int max = Math.max(0, Math.min(i, this.mActionBarTop.getHeight()));
        this.mActionBarTop.setTranslationY(-max);
        ActionBarContainer actionBarContainer = this.mActionBarBottom;
        if (actionBarContainer == null || actionBarContainer.getVisibility() == 8) {
            return;
        }
        this.mActionBarBottom.setTranslationY((int) (this.mActionBarBottom.getHeight() * (max / r0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void haltActionBarHideOffsetAnimations() {
        removeCallbacks(this.mRemoveActionBarHideOffset);
        removeCallbacks(this.mAddActionBarHideOffset);
        ViewPropertyAnimator viewPropertyAnimator = this.mCurrentActionBarTopAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        ViewPropertyAnimator viewPropertyAnimator2 = this.mCurrentActionBarBottomAnimator;
        if (viewPropertyAnimator2 != null) {
            viewPropertyAnimator2.cancel();
        }
    }

    private void postRemoveActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mRemoveActionBarHideOffset, 600L);
    }

    private void postAddActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mAddActionBarHideOffset, 600L);
    }

    private void removeActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private void addActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f, float f2) {
        this.mFlingEstimator.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowCallback(Window.Callback callback) {
        pullChildren();
        this.mDecorToolbar.setWindowCallback(callback);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowTitle(CharSequence charSequence) {
        pullChildren();
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public CharSequence getTitle() {
        pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void initFeature(int i) {
        pullChildren();
        if (i == 2) {
            this.mDecorToolbar.initProgress();
        } else if (i == 5) {
            this.mDecorToolbar.initIndeterminateProgress();
        } else {
            if (i != 9) {
                return;
            }
            setOverlayMode(true);
        }
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setUiOptions(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = z ? getContext().getResources().getBoolean(R.bool.split_action_bar_is_narrow) : false;
        if (z2) {
            pullChildren();
            if (this.mActionBarBottom == null || !this.mDecorToolbar.canSplit()) {
                if (z2) {
                    Log.e(TAG, "Requested split action bar with incompatible window decor! Ignoring request.");
                    return;
                }
                return;
            }
            this.mDecorToolbar.setSplitView(this.mActionBarBottom);
            this.mDecorToolbar.setSplitToolbar(z2);
            this.mDecorToolbar.setSplitWhenNarrow(z);
            ActionBarContextView actionBarContextView = (ActionBarContextView) findViewById(R.id.action_context_bar);
            actionBarContextView.setSplitView(this.mActionBarBottom);
            actionBarContextView.setSplitToolbar(z2);
            actionBarContextView.setSplitWhenNarrow(z);
        }
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hasIcon() {
        pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hasLogo() {
        pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setIcon(int i) {
        pullChildren();
        this.mDecorToolbar.setIcon(i);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setIcon(Drawable drawable) {
        pullChildren();
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setLogo(int i) {
        pullChildren();
        this.mDecorToolbar.setLogo(i);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean canShowOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean isOverflowMenuShowing() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean isOverflowMenuShowPending() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean showOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hideOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setMenuPrepared() {
        pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        pullChildren();
        this.mDecorToolbar.setMenu(menu, callback);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        pullChildren();
        this.mDecorToolbar.saveHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        pullChildren();
        this.mDecorToolbar.restoreHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void dismissPopups() {
        pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void notifyContentChanged() {
        this.mLastBaseContentInsets.setEmpty();
        this.mLastBaseInnerInsets = WindowInsets.CONSUMED;
        this.mLastInnerInsets = WindowInsets.CONSUMED;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
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
}
