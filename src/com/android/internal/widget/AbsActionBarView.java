package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import com.android.internal.R;

/* loaded from: classes6.dex */
public abstract class AbsActionBarView extends ViewGroup {
    private static final int FADE_DURATION = 200;
    private static final TimeInterpolator sAlphaInterpolator = new DecelerateInterpolator();
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected boolean mIsThemeDeviceDefaultFamily;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected boolean mSplitActionBar;
    protected ViewGroup mSplitView;
    protected boolean mSplitWhenNarrow;
    protected final VisibilityAnimListener mVisAnimListener;
    protected Animator mVisibilityAnim;

    protected static int next(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    public AbsActionBarView(Context context) {
        this(context, null);
    }

    public AbsActionBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsActionBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsActionBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mVisAnimListener = new VisibilityAnimListener();
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843917, typedValue, true) && typedValue.resourceId != 0) {
            this.mPopupContext = new ContextThemeWrapper(context, typedValue.resourceId);
        } else {
            this.mPopupContext = context;
        }
        TypedValue typedValue2 = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue2, true);
        this.mIsThemeDeviceDefaultFamily = typedValue2.data != 0;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        setContentHeight(obtainStyledAttributes.getLayoutDimension(4, 0));
        obtainStyledAttributes.recycle();
        if (this.mSplitWhenNarrow) {
            setSplitToolbar(getContext().getResources().getBoolean(R.bool.split_action_bar_is_narrow));
        }
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
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
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
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
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    public void setSplitToolbar(boolean z) {
        this.mSplitActionBar = z;
    }

    public void setSplitWhenNarrow(boolean z) {
        this.mSplitWhenNarrow = z;
    }

    public void setContentHeight(int i) {
        this.mContentHeight = i;
        requestLayout();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public void setSplitView(ViewGroup viewGroup) {
        this.mSplitView = viewGroup;
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return getVisibility();
    }

    public Animator setupAnimatorToVisibility(int i, long j) {
        ActionMenuView actionMenuView;
        Animator animator = this.mVisibilityAnim;
        if (animator != null) {
            animator.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
                if (this.mSplitView != null && (actionMenuView = this.mMenuView) != null) {
                    actionMenuView.setAlpha(0.0f);
                }
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<AbsActionBarView, Float>) View.ALPHA, 1.0f);
            ofFloat.setDuration(j);
            ofFloat.setInterpolator(sAlphaInterpolator);
            if (this.mSplitView != null && this.mMenuView != null) {
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mMenuView, (Property<ActionMenuView, Float>) View.ALPHA, 1.0f);
                ofFloat2.setDuration(j);
                animatorSet.addListener(this.mVisAnimListener.withFinalVisibility(i));
                animatorSet.play(ofFloat).with(ofFloat2);
                return animatorSet;
            }
            ofFloat.addListener(this.mVisAnimListener.withFinalVisibility(i));
            return ofFloat;
        }
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this, (Property<AbsActionBarView, Float>) View.ALPHA, 0.0f);
        ofFloat3.setDuration(j);
        ofFloat3.setInterpolator(sAlphaInterpolator);
        if (this.mSplitView != null && this.mMenuView != null) {
            AnimatorSet animatorSet2 = new AnimatorSet();
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mMenuView, (Property<ActionMenuView, Float>) View.ALPHA, 0.0f);
            ofFloat4.setDuration(j);
            animatorSet2.addListener(this.mVisAnimListener.withFinalVisibility(i));
            animatorSet2.play(ofFloat3).with(ofFloat4);
            return animatorSet2;
        }
        ofFloat3.addListener(this.mVisAnimListener.withFinalVisibility(i));
        return ofFloat3;
    }

    public void animateToVisibility(int i) {
        setupAnimatorToVisibility(i, 200L).start();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility()) {
            Animator animator = this.mVisibilityAnim;
            if (animator != null) {
                animator.end();
            }
            super.setVisibility(i);
        }
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public void postShowOverflowMenu() {
        post(new Runnable() { // from class: com.android.internal.widget.AbsActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowReserved();
    }

    public boolean canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    protected int measureChildView(View view, int i, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    protected int positionChild(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = i2 + ((i3 - measuredHeight) / 2);
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    protected class VisibilityAnimListener implements Animator.AnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        protected VisibilityAnimListener() {
        }

        public VisibilityAnimListener withFinalVisibility(int i) {
            this.mFinalVisibility = i;
            return this;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            AbsActionBarView.this.setVisibility(0);
            AbsActionBarView.this.mVisibilityAnim = animator;
            this.mCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            AbsActionBarView.this.mVisibilityAnim = null;
            AbsActionBarView.this.setVisibility(this.mFinalVisibility);
            if (AbsActionBarView.this.mSplitView == null || AbsActionBarView.this.mMenuView == null) {
                return;
            }
            AbsActionBarView.this.mMenuView.setVisibility(this.mFinalVisibility);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }
    }
}
