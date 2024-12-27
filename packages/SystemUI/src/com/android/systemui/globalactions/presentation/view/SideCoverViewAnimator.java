package com.android.systemui.globalactions.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.globalactions.presentation.view.SideCoverContentView;
import com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import java.util.ArrayList;

public final class SideCoverViewAnimator implements GlobalActionsAnimator {
    public SideCoverContentView.AnonymousClass1 mCallback;
    public TextView mConfirmDescriptionView;
    public ViewGroup mConfirmIconLabelView;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final HandlerUtil mHandler;
    public float mOriginalConfirmLocationX;
    public float mOriginalConfirmLocationY;
    public ViewGroup mSelectedActionView;
    public final ViewStateController mViewStateController;
    public SideCoverViewAnimator$$ExternalSyntheticLambda1 mViewTreeObserverListener;

    public SideCoverViewAnimator(Context context, ResourceFactory resourceFactory, ConditionChecker conditionChecker, LogWrapper logWrapper, HandlerUtil handlerUtil, ViewStateController viewStateController) {
        this.mContext = context;
        this.mHandler = handlerUtil;
        this.mViewStateController = viewStateController;
    }

    public final AnimatorSet getDefaultConfirmAnimatorSet(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ViewGroup viewGroup = this.mConfirmIconLabelView;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "x", viewGroup.getX(), z ? this.mOriginalConfirmLocationX : ((View) this.mSelectedActionView.getParent()).getLeft() + this.mSelectedActionView.getLeft());
        ViewGroup viewGroup2 = this.mConfirmIconLabelView;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(viewGroup2, "y", viewGroup2.getY(), z ? this.mOriginalConfirmLocationY : ((View) this.mSelectedActionView.getParent()).getTop() + this.mSelectedActionView.getTop());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mConfirmDescriptionView, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat3.setDuration(125L);
        animatorSet.playTogether(ofFloat2, ofFloat, ofFloat3);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(125L);
        if (z) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverViewAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SideCoverViewAnimator.this.mSelectedActionView.setVisibility(0);
                    SideCoverContentView.this.mListView.setVisibility(4);
                    SideCoverViewAnimator sideCoverViewAnimator = SideCoverViewAnimator.this;
                    sideCoverViewAnimator.getClass();
                    sideCoverViewAnimator.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SideCoverViewAnimator.this.mConfirmIconLabelView.setAlpha(1.0f);
                    SideCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    SideCoverContentView.this.mListView.animate().alpha(0.0f).setDuration(125L).start();
                }
            });
        } else {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverViewAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SideCoverViewAnimator.this.mSelectedActionView.setVisibility(0);
                    SideCoverViewAnimator.this.mConfirmView.removeAllViews();
                    SideCoverViewAnimator.this.mConfirmView.setVisibility(8);
                    View view = SideCoverContentView.this.mGridViewAdapter.mLastAnimatedView;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    SideCoverContentView.this.mSelectedViewModel.getActionInfo().setStateLabel("");
                    SideCoverViewAnimator sideCoverViewAnimator = SideCoverViewAnimator.this;
                    sideCoverViewAnimator.getClass();
                    sideCoverViewAnimator.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SideCoverContentView.this.mListView.setVisibility(0);
                    SideCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    SideCoverContentView.this.mListView.animate().alpha(1.0f).setDuration(125L).start();
                }
            });
        }
        return animatorSet;
    }

    public final boolean isHideConfirmAnimationRunning() {
        return false;
    }

    public final boolean isSafeModeConfirm() {
        return false;
    }

    public final boolean isShowConfirmAnimationRunning() {
        return false;
    }

    public final void startDismissAnimation(boolean z) {
        this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
        if (!z) {
            this.mViewStateController.setState(ViewAnimationState.IDLE);
            SideCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
            anonymousClass1.getClass();
            SideCoverContentView.this.mParentView.dismiss();
            return;
        }
        SideCoverContentView sideCoverContentView = SideCoverContentView.this;
        sideCoverContentView.mParentView.setCoverSecureConfirmState(true);
        sideCoverContentView.mForceDismiss = true;
        SideCoverContentView.this.mListView.setVisibility(0);
        SideCoverContentView.this.mListView.animate().alpha(1.0f).setDuration(125L).start();
        this.mSelectedActionView.setVisibility(0);
        this.mConfirmView.removeAllViews();
        this.mConfirmView.setVisibility(8);
        this.mViewStateController.setState(ViewAnimationState.IDLE);
    }

    public final void startDismissConfirmAnimation() {
        SideCoverContentView sideCoverContentView = SideCoverContentView.this;
        if (!sideCoverContentView.mForceDismiss) {
            this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
            getDefaultConfirmAnimatorSet(false).start();
            return;
        }
        sideCoverContentView.mParentView.setCoverSecureConfirmState(false);
        sideCoverContentView.mForceDismiss = false;
        SideCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
        anonymousClass1.getClass();
        SideCoverContentView.this.mParentView.dismiss();
        this.mViewStateController.setState(ViewAnimationState.IDLE);
    }

    public final void startShowConfirmAnimation() {
        SideCoverContentView sideCoverContentView = SideCoverContentView.this;
        SideCoverContentItemView sideCoverContentItemView = new SideCoverContentItemView(sideCoverContentView.mDialog.getContext(), sideCoverContentView.mSelectedViewModel, sideCoverContentView.mConfirmView, sideCoverContentView.mResourceFactory, false, false);
        sideCoverContentView.mConfirmView.removeAllViews();
        ViewGroup viewGroup = sideCoverContentView.mConfirmView;
        View inflateView = sideCoverContentItemView.inflateView();
        sideCoverContentItemView.setViewAttrs(inflateView);
        viewGroup.addView(inflateView);
        sideCoverContentView.mConfirmView.setVisibility(0);
        SideCoverContentView sideCoverContentView2 = SideCoverContentView.this;
        ViewGroup viewGroup2 = sideCoverContentView2.mConfirmView;
        this.mConfirmView = viewGroup2;
        this.mConfirmIconLabelView = (ViewGroup) viewGroup2.findViewById(sideCoverContentView2.mResourceFactory.get(ResourceType.ID_SIDE_COVER_BACKGROUND));
        this.mConfirmDescriptionView = (TextView) this.mConfirmView.findViewById(SideCoverContentView.this.mResourceFactory.get(ResourceType.ID_COVER_TEXT));
        SideCoverContentView sideCoverContentView3 = SideCoverContentView.this;
        this.mSelectedActionView = (ViewGroup) sideCoverContentView3.mListView.getChildAt(sideCoverContentView3.mSelectedViewModel.getActionInfo().getViewIndex());
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverViewAnimator$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                SideCoverViewAnimator sideCoverViewAnimator = SideCoverViewAnimator.this;
                sideCoverViewAnimator.mConfirmView.getViewTreeObserver().removeOnGlobalLayoutListener(sideCoverViewAnimator.mViewTreeObserverListener);
                sideCoverViewAnimator.mOriginalConfirmLocationX = sideCoverViewAnimator.mConfirmIconLabelView.getLeft();
                sideCoverViewAnimator.mOriginalConfirmLocationY = sideCoverViewAnimator.mConfirmIconLabelView.getTop();
                sideCoverViewAnimator.mConfirmIconLabelView.setY(((View) sideCoverViewAnimator.mSelectedActionView.getParent()).getTop() + sideCoverViewAnimator.mSelectedActionView.getTop());
                sideCoverViewAnimator.mConfirmIconLabelView.setX(((View) sideCoverViewAnimator.mSelectedActionView.getParent()).getLeft() + sideCoverViewAnimator.mSelectedActionView.getLeft());
                sideCoverViewAnimator.getDefaultConfirmAnimatorSet(true).start();
            }
        };
        this.mConfirmView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    public final void startToastAnimation() {
        SideCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
        SideCoverContentView.this.mSelectedViewModel.getActionInfo().setLabel(anonymousClass1.toastMessage);
        SideCoverContentView.this.mSelectedViewModel.getActionInfo().setViewType(ViewType.COVER_NOTI_VIEW);
        ((ArrayList) SideCoverContentView.this.mGridViewAdapter.mViewModelList).clear();
        SideCoverContentView sideCoverContentView = SideCoverContentView.this;
        ((ArrayList) sideCoverContentView.mGridViewAdapter.mViewModelList).add(sideCoverContentView.mSelectedViewModel);
        SideCoverContentView.this.mListView.setNumColumns(1);
        SideCoverContentView.this.mGridViewAdapter.notifyDataSetChanged();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) SideCoverContentView.this.mListView.getLayoutParams();
        layoutParams.width = this.mContext.getResources().getConfiguration().windowConfiguration.getBounds().height();
        SideCoverContentView.this.mListView.setLayoutParams(layoutParams);
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverViewAnimator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SideCoverViewAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        }, 125L);
    }

    public final void cancelHideConfirmAnimation() {
    }

    public final void cancelShowConfirmAnimation() {
    }

    public final void initializeSelectedActionView() {
    }

    public final void setListViewLand() {
    }

    public final void setListViewPort() {
    }

    public final void showMainViewLand() {
    }

    public final void showMainViewPort() {
    }

    public final void startDismissSafeModeAnimation() {
    }

    public final void startSetSafeModeAnimation() {
    }

    public final void startShowAnimation() {
    }

    public final void startShowSafeModeAnimation() {
    }
}
