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
import com.android.systemui.globalactions.presentation.view.MiniSViewCoverContentView;
import com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MiniSViewCoverViewAnimator implements GlobalActionsAnimator {
    public MiniSViewCoverContentView.AnonymousClass1 mCallback;
    public ViewGroup mConfirmIconLabelView;
    public ViewGroup mConfirmView;
    public final HandlerUtil mHandler;
    public float mOriginalConfirmLocationX;
    public ViewGroup mSelectedActionView;
    public final ViewStateController mViewStateController;
    public MiniSViewCoverViewAnimator$$ExternalSyntheticLambda0 mViewTreeObserverListener;

    public MiniSViewCoverViewAnimator(Context context, ConditionChecker conditionChecker, LogWrapper logWrapper, HandlerUtil handlerUtil, ViewStateController viewStateController) {
        this.mHandler = handlerUtil;
        this.mViewStateController = viewStateController;
    }

    public final AnimatorSet getDefaultConfirmAnimatorSet(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ViewGroup viewGroup = this.mConfirmIconLabelView;
        animatorSet.playTogether(ObjectAnimator.ofFloat(viewGroup, "x", viewGroup.getX(), z ? this.mOriginalConfirmLocationX : ((View) this.mSelectedActionView.getParent()).getLeft() + this.mSelectedActionView.getLeft()));
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(250L);
        if (z) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverViewAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    MiniSViewCoverViewAnimator.this.mSelectedActionView.setVisibility(0);
                    MiniSViewCoverContentView.this.mListView.setVisibility(4);
                    MiniSViewCoverViewAnimator miniSViewCoverViewAnimator = MiniSViewCoverViewAnimator.this;
                    miniSViewCoverViewAnimator.getClass();
                    miniSViewCoverViewAnimator.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    MiniSViewCoverViewAnimator.this.mConfirmIconLabelView.setAlpha(1.0f);
                    MiniSViewCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    MiniSViewCoverContentView.this.mListView.animate().alpha(0.0f).setDuration(250L).start();
                }
            });
        } else {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverViewAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    MiniSViewCoverViewAnimator.this.mSelectedActionView.setVisibility(0);
                    MiniSViewCoverViewAnimator.this.mConfirmView.removeAllViews();
                    MiniSViewCoverViewAnimator.this.mConfirmView.setVisibility(8);
                    View view = MiniSViewCoverContentView.this.mAdapter.mLastAnimatedView;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    MiniSViewCoverContentView.this.mSelectedViewModel.getActionInfo().setStateLabel("");
                    MiniSViewCoverViewAnimator miniSViewCoverViewAnimator = MiniSViewCoverViewAnimator.this;
                    miniSViewCoverViewAnimator.getClass();
                    miniSViewCoverViewAnimator.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    MiniSViewCoverContentView.this.mListView.setVisibility(0);
                    MiniSViewCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    MiniSViewCoverContentView.this.mListView.animate().alpha(1.0f).setDuration(250L).start();
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
            MiniSViewCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
            anonymousClass1.getClass();
            MiniSViewCoverContentView.this.mParentView.dismiss();
            return;
        }
        MiniSViewCoverContentView miniSViewCoverContentView = MiniSViewCoverContentView.this;
        miniSViewCoverContentView.mParentView.setCoverSecureConfirmState(true);
        miniSViewCoverContentView.mForceDismiss = true;
        MiniSViewCoverContentView.this.mListView.setVisibility(0);
        MiniSViewCoverContentView.this.mListView.animate().alpha(1.0f).setDuration(250L).start();
        this.mSelectedActionView.setVisibility(0);
        this.mConfirmView.removeAllViews();
        this.mConfirmView.setVisibility(8);
        this.mViewStateController.setState(ViewAnimationState.IDLE);
    }

    public final void startDismissConfirmAnimation() {
        MiniSViewCoverContentView miniSViewCoverContentView = MiniSViewCoverContentView.this;
        if (!miniSViewCoverContentView.mForceDismiss) {
            this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
            getDefaultConfirmAnimatorSet(false).start();
            return;
        }
        miniSViewCoverContentView.mParentView.setCoverSecureConfirmState(false);
        miniSViewCoverContentView.mForceDismiss = false;
        MiniSViewCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
        anonymousClass1.getClass();
        MiniSViewCoverContentView.this.mParentView.dismiss();
        this.mViewStateController.setState(ViewAnimationState.IDLE);
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.systemui.globalactions.presentation.view.MiniSViewCoverViewAnimator$$ExternalSyntheticLambda0] */
    public final void startShowConfirmAnimation() {
        MiniSViewCoverContentView miniSViewCoverContentView = MiniSViewCoverContentView.this;
        MiniSViewCoverContentItemView miniSViewCoverContentItemView = new MiniSViewCoverContentItemView(miniSViewCoverContentView.mDialog.getContext(), miniSViewCoverContentView.mSelectedViewModel, miniSViewCoverContentView.mConfirmView, miniSViewCoverContentView.mResourceFactory);
        miniSViewCoverContentView.mConfirmView.removeAllViews();
        ViewGroup viewGroup = miniSViewCoverContentView.mConfirmView;
        View inflateView = miniSViewCoverContentItemView.inflateView();
        miniSViewCoverContentItemView.setViewAttrs(inflateView);
        viewGroup.addView(inflateView);
        miniSViewCoverContentView.mConfirmView.setVisibility(0);
        MiniSViewCoverContentView miniSViewCoverContentView2 = MiniSViewCoverContentView.this;
        ViewGroup viewGroup2 = miniSViewCoverContentView2.mConfirmView;
        this.mConfirmView = viewGroup2;
        this.mConfirmIconLabelView = (ViewGroup) viewGroup2.findViewById(miniSViewCoverContentView2.mResourceFactory.get(ResourceType.ID_MINI_BACKGROUND));
        MiniSViewCoverContentView miniSViewCoverContentView3 = MiniSViewCoverContentView.this;
        this.mSelectedActionView = (ViewGroup) miniSViewCoverContentView3.mListView.getChildAt(miniSViewCoverContentView3.mSelectedViewModel.getActionInfo().getViewIndex());
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverViewAnimator$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MiniSViewCoverViewAnimator miniSViewCoverViewAnimator = MiniSViewCoverViewAnimator.this;
                miniSViewCoverViewAnimator.mConfirmView.getViewTreeObserver().removeOnGlobalLayoutListener(miniSViewCoverViewAnimator.mViewTreeObserverListener);
                miniSViewCoverViewAnimator.mOriginalConfirmLocationX = miniSViewCoverViewAnimator.mConfirmIconLabelView.getLeft();
                miniSViewCoverViewAnimator.mConfirmIconLabelView.setX(((View) miniSViewCoverViewAnimator.mSelectedActionView.getParent()).getLeft() + miniSViewCoverViewAnimator.mSelectedActionView.getLeft());
                miniSViewCoverViewAnimator.getDefaultConfirmAnimatorSet(true).start();
            }
        };
        this.mConfirmView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    public final void startToastAnimation() {
        MiniSViewCoverContentView.AnonymousClass1 anonymousClass1 = this.mCallback;
        MiniSViewCoverContentView.this.mSelectedViewModel.getActionInfo().setLabel(anonymousClass1.toastMessage);
        MiniSViewCoverContentView.this.mSelectedViewModel.getActionInfo().setViewType(ViewType.COVER_NOTI_VIEW);
        ((ArrayList) MiniSViewCoverContentView.this.mAdapter.mViewModelList).clear();
        MiniSViewCoverContentView miniSViewCoverContentView = MiniSViewCoverContentView.this;
        ((ArrayList) miniSViewCoverContentView.mAdapter.mViewModelList).add(miniSViewCoverContentView.mSelectedViewModel);
        MiniSViewCoverContentView.this.mListView.setNumColumns(1);
        MiniSViewCoverContentView.this.mAdapter.notifyDataSetChanged();
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverViewAnimator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MiniSViewCoverViewAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        }, 250L);
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
