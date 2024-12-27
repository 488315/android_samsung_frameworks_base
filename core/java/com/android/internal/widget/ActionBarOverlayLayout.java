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
    public static final Property<ActionBarOverlayLayout, Integer> ACTION_BAR_HIDE_OFFSET =
            new IntProperty<ActionBarOverlayLayout>(
                    "actionBarHideOffset") { // from class:
                                             // com.android.internal.widget.ActionBarOverlayLayout.5
                @Override // android.util.IntProperty
                public void setValue(ActionBarOverlayLayout object, int value) {
                    object.setActionBarHideOffset(value);
                }

                @Override // android.util.Property
                public Integer get(ActionBarOverlayLayout object) {
                    return Integer.valueOf(object.getActionBarHideOffset());
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
        this.mTopAnimatorListener =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.ActionBarOverlayLayout.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }
                };
        this.mBottomAnimatorListener =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.ActionBarOverlayLayout.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }
                };
        this.mRemoveActionBarHideOffset =
                new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator =
                                ActionBarOverlayLayout.this
                                        .mActionBarTop
                                        .animate()
                                        .translationY(0.0f)
                                        .setListener(
                                                ActionBarOverlayLayout.this.mTopAnimatorListener);
                        if (ActionBarOverlayLayout.this.mActionBarBottom != null
                                && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility()
                                        != 8) {
                            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator =
                                    ActionBarOverlayLayout.this
                                            .mActionBarBottom
                                            .animate()
                                            .translationY(0.0f)
                                            .setListener(
                                                    ActionBarOverlayLayout.this
                                                            .mBottomAnimatorListener);
                        }
                    }
                };
        this.mAddActionBarHideOffset =
                new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator =
                                ActionBarOverlayLayout.this
                                        .mActionBarTop
                                        .animate()
                                        .translationY(
                                                -ActionBarOverlayLayout.this.mActionBarTop
                                                        .getHeight())
                                        .setListener(
                                                ActionBarOverlayLayout.this.mTopAnimatorListener);
                        if (ActionBarOverlayLayout.this.mActionBarBottom != null
                                && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility()
                                        != 8) {
                            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator =
                                    ActionBarOverlayLayout.this
                                            .mActionBarBottom
                                            .animate()
                                            .translationY(
                                                    ActionBarOverlayLayout.this.mActionBarBottom
                                                            .getHeight())
                                            .setListener(
                                                    ActionBarOverlayLayout.this
                                                            .mBottomAnimatorListener);
                        }
                    }
                };
        init(context);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        this.mTopAnimatorListener =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.ActionBarOverlayLayout.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }
                };
        this.mBottomAnimatorListener =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.internal.widget.ActionBarOverlayLayout.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                        ActionBarOverlayLayout.this.mAnimatingForFling = false;
                    }
                };
        this.mRemoveActionBarHideOffset =
                new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator =
                                ActionBarOverlayLayout.this
                                        .mActionBarTop
                                        .animate()
                                        .translationY(0.0f)
                                        .setListener(
                                                ActionBarOverlayLayout.this.mTopAnimatorListener);
                        if (ActionBarOverlayLayout.this.mActionBarBottom != null
                                && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility()
                                        != 8) {
                            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator =
                                    ActionBarOverlayLayout.this
                                            .mActionBarBottom
                                            .animate()
                                            .translationY(0.0f)
                                            .setListener(
                                                    ActionBarOverlayLayout.this
                                                            .mBottomAnimatorListener);
                        }
                    }
                };
        this.mAddActionBarHideOffset =
                new Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator =
                                ActionBarOverlayLayout.this
                                        .mActionBarTop
                                        .animate()
                                        .translationY(
                                                -ActionBarOverlayLayout.this.mActionBarTop
                                                        .getHeight())
                                        .setListener(
                                                ActionBarOverlayLayout.this.mTopAnimatorListener);
                        if (ActionBarOverlayLayout.this.mActionBarBottom != null
                                && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility()
                                        != 8) {
                            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator =
                                    ActionBarOverlayLayout.this
                                            .mActionBarBottom
                                            .animate()
                                            .translationY(
                                                    ActionBarOverlayLayout.this.mActionBarBottom
                                                            .getHeight())
                                            .setListener(
                                                    ActionBarOverlayLayout.this
                                                            .mBottomAnimatorListener);
                        }
                    }
                };
        init(context);
    }

    private void init(Context context) {
        boolean z;
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(ATTRS);
        boolean z2 = false;
        this.mActionBarHeight = ta.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = ta.getDrawable(1);
        if (this.mWindowContentOverlay != null) {
            z = false;
        } else {
            z = true;
        }
        setWillNotDraw(z);
        ta.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z2 = true;
        }
        this.mIgnoreWindowContentOverlay = z2;
        this.mFlingEstimator = new OverScroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback cb) {
        this.mActionBarVisibilityCallback = cb;
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                int newVis = this.mLastSystemUiVisibility;
                onWindowSystemUiVisibilityChanged(newVis);
                requestApplyInsets();
            }
        }
    }

    public void setOverlayMode(boolean overlayMode) {
        this.mOverlayMode = overlayMode;
        this.mIgnoreWindowContentOverlay =
                overlayMode && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    public void setHasNonEmbeddedTabs(boolean hasNonEmbeddedTabs) {
        this.mHasNonEmbeddedTabs = hasNonEmbeddedTabs;
    }

    public void setShowingForActionMode(boolean showing) {
        if (showing) {
            if ((getWindowSystemUiVisibility() & 1280) == 1280) {
                setDisabledSystemUiVisibility(4);
                return;
            }
            return;
        }
        setDisabledSystemUiVisibility(0);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        init(getContext());
        requestApplyInsets();
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int visible) {
        super.onWindowSystemUiVisibilityChanged(visible);
        pullChildren();
        int diff = this.mLastSystemUiVisibility ^ visible;
        this.mLastSystemUiVisibility = visible;
        boolean barVisible = (visible & 4) == 0;
        boolean stable = (visible & 256) != 0;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(
                    (stable || this.mActionBarExtendsIntoSystemInsets) ? false : true);
            if (barVisible || !stable) {
                this.mActionBarVisibilityCallback.showForSystem();
            } else {
                this.mActionBarVisibilityCallback.hideForSystem();
            }
        }
        if ((diff & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            requestApplyInsets();
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mWindowVisibility = visibility;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(visibility);
        }
    }

    private boolean applyInsets(
            View view,
            Rect insets,
            boolean toPadding,
            boolean left,
            boolean top,
            boolean right,
            boolean bottom) {
        if (toPadding) {
            boolean changed = setMargin(view, EMPTY_RECT, left, top, right, bottom);
            return changed | setPadding(view, insets, left, top, right, bottom);
        }
        boolean changed2 = setPadding(view, EMPTY_RECT, left, top, right, bottom);
        return changed2 | setMargin(view, insets, left, top, right, bottom);
    }

    private boolean setPadding(
            View view, Rect insets, boolean left, boolean top, boolean right, boolean bottom) {
        if ((left && view.getPaddingLeft() != insets.left)
                || ((top && view.getPaddingTop() != insets.top)
                        || ((right && view.getPaddingRight() != insets.right)
                                || (bottom && view.getPaddingBottom() != insets.bottom)))) {
            view.setPadding(
                    left ? insets.left : view.getPaddingLeft(),
                    top ? insets.top : view.getPaddingTop(),
                    right ? insets.right : view.getPaddingRight(),
                    bottom ? insets.bottom : view.getPaddingBottom());
            return true;
        }
        return false;
    }

    private boolean setMargin(
            View view, Rect insets, boolean left, boolean top, boolean right, boolean bottom) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        boolean changed = false;
        if (left && lp.leftMargin != insets.left) {
            changed = true;
            lp.leftMargin = insets.left;
        }
        if (top && lp.topMargin != insets.top) {
            changed = true;
            lp.topMargin = insets.top;
        }
        if (right && lp.rightMargin != insets.right) {
            changed = true;
            lp.rightMargin = insets.right;
        }
        if (bottom && lp.bottomMargin != insets.bottom) {
            lp.bottomMargin = insets.bottom;
            return true;
        }
        return changed;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        pullChildren();
        int vis = getWindowSystemUiVisibility();
        boolean stable = (vis & 256) != 0;
        boolean layoutIntoSystemInsets = (vis & 1536) != 0;
        this.mDecorFitsSystemWindows = hasContentOnApplyWindowInsetsListener();
        this.mActionBarExtendsIntoSystemInsets =
                !this.mDecorFitsSystemWindows || (stable && layoutIntoSystemInsets);
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(
                    (stable || this.mActionBarExtendsIntoSystemInsets) ? false : true);
        }
        Insets sysInsets = insets.getSystemWindowInsets();
        this.mSystemInsets.set(sysInsets.left, sysInsets.top, sysInsets.right, sysInsets.bottom);
        boolean changed =
                applyInsets(
                        this.mActionBarTop,
                        this.mSystemInsets,
                        this.mActionBarExtendsIntoSystemInsets,
                        true,
                        true,
                        true,
                        false);
        if (this.mActionBarBottom != null) {
            changed |=
                    applyInsets(
                            this.mActionBarBottom,
                            this.mSystemInsets,
                            this.mActionBarExtendsIntoSystemInsets,
                            true,
                            false,
                            true,
                            true);
        }
        computeSystemWindowInsets(insets, this.mBaseContentInsets);
        this.mBaseInnerInsets = insets.inset(this.mBaseContentInsets);
        if (!this.mLastBaseInnerInsets.equals(this.mBaseInnerInsets)) {
            changed = true;
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
        }
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            changed = true;
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (changed) {
            requestLayout();
        }
        return WindowInsets.CONSUMED;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childState;
        int childState2;
        int maxWidth;
        pullChildren();
        int topInset = 0;
        int bottomInset = 0;
        measureChildWithMargins(this.mActionBarTop, widthMeasureSpec, 0, heightMeasureSpec, 0);
        LayoutParams lp = (LayoutParams) this.mActionBarTop.getLayoutParams();
        int maxWidth2 =
                Math.max(0, this.mActionBarTop.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
        int maxHeight =
                Math.max(
                        0, this.mActionBarTop.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        int childState3 = combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
        if (this.mActionBarBottom != null) {
            measureChildWithMargins(
                    this.mActionBarBottom, widthMeasureSpec, 0, heightMeasureSpec, 0);
            LayoutParams lp2 = (LayoutParams) this.mActionBarBottom.getLayoutParams();
            int maxWidth3 =
                    Math.max(
                            maxWidth2,
                            this.mActionBarBottom.getMeasuredWidth()
                                    + lp2.leftMargin
                                    + lp2.rightMargin);
            int maxHeight2 =
                    Math.max(
                            maxHeight,
                            this.mActionBarBottom.getMeasuredHeight()
                                    + lp2.topMargin
                                    + lp2.bottomMargin);
            childState =
                    combineMeasuredStates(childState3, this.mActionBarBottom.getMeasuredState());
            childState2 = maxWidth3;
            maxWidth = maxHeight2;
        } else {
            childState = childState3;
            childState2 = maxWidth2;
            maxWidth = maxHeight;
        }
        int vis = getWindowSystemUiVisibility();
        boolean stable = (vis & 256) != 0;
        if (stable) {
            int topInset2 = this.mActionBarHeight;
            if (!this.mActionBarExtendsIntoSystemInsets) {
                topInset = topInset2;
            } else {
                topInset = topInset2 + this.mSystemInsets.top;
            }
            if (this.mHasNonEmbeddedTabs) {
                View tabs = this.mActionBarTop.getTabContainer();
                if (tabs != null) {
                    topInset += this.mActionBarHeight;
                }
            }
        } else if (this.mActionBarTop.getVisibility() != 8) {
            topInset = this.mActionBarTop.getMeasuredHeight();
        }
        if (this.mDecorToolbar.isSplit() && this.mActionBarBottom != null) {
            if (stable) {
                bottomInset = this.mActionBarHeight;
                if (this.mActionBarExtendsIntoSystemInsets) {
                    bottomInset += this.mSystemInsets.bottom;
                }
            } else {
                bottomInset = this.mActionBarBottom.getMeasuredHeight();
            }
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        this.mInnerInsets = this.mBaseInnerInsets;
        if (!this.mOverlayMode && !stable && this.mDecorFitsSystemWindows) {
            if (this.mActionBarExtendsIntoSystemInsets) {
                this.mContentInsets.top = Math.max(this.mContentInsets.top, topInset);
                this.mContentInsets.bottom = Math.max(this.mContentInsets.bottom, bottomInset);
            } else {
                this.mContentInsets.top += topInset;
                this.mContentInsets.bottom += bottomInset;
            }
            this.mInnerInsets = this.mInnerInsets.inset(0, topInset, 0, bottomInset);
        } else if (this.mActionBarExtendsIntoSystemInsets) {
            this.mInnerInsets =
                    this.mInnerInsets.replaceSystemWindowInsets(
                            this.mInnerInsets.getSystemWindowInsetLeft(),
                            Math.max(this.mInnerInsets.getSystemWindowInsetTop(), topInset),
                            this.mInnerInsets.getSystemWindowInsetRight(),
                            Math.max(this.mInnerInsets.getSystemWindowInsetBottom(), bottomInset));
        } else {
            this.mInnerInsets =
                    this.mInnerInsets.replaceSystemWindowInsets(
                            this.mInnerInsets.getSystemWindowInsetLeft(),
                            this.mInnerInsets.getSystemWindowInsetTop() + topInset,
                            this.mInnerInsets.getSystemWindowInsetRight(),
                            this.mInnerInsets.getSystemWindowInsetBottom() + bottomInset);
        }
        setMargin(this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            this.mLastInnerInsets = this.mInnerInsets;
            this.mContent.dispatchApplyWindowInsets(this.mInnerInsets);
        }
        measureChildWithMargins(this.mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);
        LayoutParams lp3 = (LayoutParams) this.mContent.getLayoutParams();
        int maxWidth4 =
                Math.max(
                        childState2,
                        this.mContent.getMeasuredWidth() + lp3.leftMargin + lp3.rightMargin);
        int maxHeight3 =
                Math.max(
                        maxWidth,
                        this.mContent.getMeasuredHeight() + lp3.topMargin + lp3.bottomMargin);
        int childState4 = combineMeasuredStates(childState, this.mContent.getMeasuredState());
        setMeasuredDimension(
                resolveSizeAndState(
                        Math.max(
                                maxWidth4 + getPaddingLeft() + getPaddingRight(),
                                getSuggestedMinimumWidth()),
                        widthMeasureSpec,
                        childState4),
                resolveSizeAndState(
                        Math.max(
                                maxHeight3 + getPaddingTop() + getPaddingBottom(),
                                getSuggestedMinimumHeight()),
                        heightMeasureSpec,
                        childState4 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childTop;
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int paddingRight = (right - left) - getPaddingRight();
        int parentTop = getPaddingTop();
        int parentBottom = (bottom - top) - getPaddingBottom();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int childLeft = lp.leftMargin + parentLeft;
                if (child == this.mActionBarBottom) {
                    childTop = (parentBottom - height) - lp.bottomMargin;
                } else {
                    int childTop2 = lp.topMargin;
                    childTop = childTop2 + parentTop;
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    @Override // android.view.View
    public void draw(Canvas c) {
        super.draw(c);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int top =
                    this.mActionBarTop.getVisibility() == 0
                            ? (int)
                                    (this.mActionBarTop.getBottom()
                                            + this.mActionBarTop.getTranslationY()
                                            + 0.5f)
                            : 0;
            this.mWindowContentOverlay.setBounds(
                    0, top, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + top);
            this.mWindowContentOverlay.draw(c);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int axes) {
        if ((axes & 2) == 0 || this.mActionBarTop.getVisibility() != 0) {
            return false;
        }
        return this.mHideOnContentScroll;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        this.mHideOnContentScrollReference = getActionBarHideOffset();
        haltActionBarHideOffsetAnimations();
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(
            View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        this.mHideOnContentScrollReference += dyConsumed;
        setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                postRemoveActionBarHideOffset();
            } else {
                postAddActionBarHideOffset();
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (!this.mHideOnContentScroll || !consumed) {
            return false;
        }
        if (shouldHideActionBarOnFling(velocityX, velocityY)) {
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

    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException(
                "Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean hideOnContentScroll) {
        if (hideOnContentScroll != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = hideOnContentScroll;
            if (!hideOnContentScroll) {
                stopNestedScroll();
                haltActionBarHideOffsetAnimations();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public int getActionBarHideOffset() {
        if (this.mActionBarTop != null) {
            return -((int) this.mActionBarTop.getTranslationY());
        }
        return 0;
    }

    public void setActionBarHideOffset(int offset) {
        haltActionBarHideOffsetAnimations();
        int topHeight = this.mActionBarTop.getHeight();
        int offset2 = Math.max(0, Math.min(offset, topHeight));
        this.mActionBarTop.setTranslationY(-offset2);
        if (this.mActionBarBottom != null && this.mActionBarBottom.getVisibility() != 8) {
            float fOffset = offset2 / topHeight;
            int bOffset = (int) (this.mActionBarBottom.getHeight() * fOffset);
            this.mActionBarBottom.setTranslationY(bOffset);
        }
    }

    public void haltActionBarHideOffsetAnimations() {
        removeCallbacks(this.mRemoveActionBarHideOffset);
        removeCallbacks(this.mAddActionBarHideOffset);
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel();
        }
        if (this.mCurrentActionBarBottomAnimator != null) {
            this.mCurrentActionBarBottomAnimator.cancel();
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

    private boolean shouldHideActionBarOnFling(float velocityX, float velocityY) {
        this.mFlingEstimator.fling(
                0, 0, 0, (int) velocityY, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int finalY = this.mFlingEstimator.getFinalY();
        return finalY > this.mActionBarTop.getHeight();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowCallback(Window.Callback cb) {
        pullChildren();
        this.mDecorToolbar.setWindowCallback(cb);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowTitle(CharSequence title) {
        pullChildren();
        this.mDecorToolbar.setWindowTitle(title);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public CharSequence getTitle() {
        pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void initFeature(int windowFeature) {
        pullChildren();
        switch (windowFeature) {
            case 2:
                this.mDecorToolbar.initProgress();
                break;
            case 5:
                this.mDecorToolbar.initIndeterminateProgress();
                break;
            case 9:
                setOverlayMode(true);
                break;
        }
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setUiOptions(int uiOptions) {
        boolean splitActionBar = false;
        boolean splitWhenNarrow = (uiOptions & 1) != 0;
        if (splitWhenNarrow) {
            splitActionBar =
                    getContext().getResources().getBoolean(R.bool.split_action_bar_is_narrow);
        }
        if (splitActionBar) {
            pullChildren();
            if (this.mActionBarBottom != null && this.mDecorToolbar.canSplit()) {
                this.mDecorToolbar.setSplitView(this.mActionBarBottom);
                this.mDecorToolbar.setSplitToolbar(splitActionBar);
                this.mDecorToolbar.setSplitWhenNarrow(splitWhenNarrow);
                ActionBarContextView cab =
                        (ActionBarContextView) findViewById(R.id.action_context_bar);
                cab.setSplitView(this.mActionBarBottom);
                cab.setSplitToolbar(splitActionBar);
                cab.setSplitWhenNarrow(splitWhenNarrow);
                return;
            }
            if (splitActionBar) {
                Log.e(
                        TAG,
                        "Requested split action bar with incompatible window decor! Ignoring"
                            + " request.");
            }
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
    public void setIcon(int resId) {
        pullChildren();
        this.mDecorToolbar.setIcon(resId);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setIcon(Drawable d) {
        pullChildren();
        this.mDecorToolbar.setIcon(d);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setLogo(int resId) {
        pullChildren();
        this.mDecorToolbar.setLogo(resId);
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
    public void setMenu(Menu menu, MenuPresenter.Callback cb) {
        pullChildren();
        this.mDecorToolbar.setMenu(menu, cb);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void saveToolbarHierarchyState(SparseArray<Parcelable> toolbarStates) {
        pullChildren();
        this.mDecorToolbar.saveHierarchyState(toolbarStates);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> toolbarStates) {
        pullChildren();
        this.mDecorToolbar.restoreHierarchyState(toolbarStates);
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
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }
}
