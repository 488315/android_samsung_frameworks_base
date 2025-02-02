package com.android.systemui.globalactions.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentView;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrontLargeCoverViewAnimator implements GlobalActionsAnimator {
    public FrontLargeCoverContentView.C14283 mCallback;
    public final ConditionChecker mConditionChecker;
    public TextView mConfirmDescriptionView;
    public ViewGroup mConfirmIconLabelView;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final RunnableC14291 mDismissDuringSecureConfirm = new Runnable() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator.1
        @Override // java.lang.Runnable
        public final void run() {
            FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
            if (frontLargeCoverContentView.mLastFoldedState) {
                frontLargeCoverContentView.mPresenter.onDismiss();
                frontLargeCoverContentView.mParentView.dismiss();
                frontLargeCoverContentView.mDialog.dismiss();
            }
        }
    };
    public final HandlerUtil mHandler;
    public float mOriginalConfirmLocationX;
    public float mOriginalConfirmLocationY;
    public final ResourceFactory mResourceFactory;
    public ViewGroup mRootView;
    public ViewGroup mSelectedActionView;
    public final ViewStateController mViewStateController;
    public FrontLargeCoverViewAnimator$$ExternalSyntheticLambda0 mViewTreeObserverListener;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator$1] */
    public FrontLargeCoverViewAnimator(Context context, ConditionChecker conditionChecker, LogWrapper logWrapper, HandlerUtil handlerUtil, ResourceFactory resourceFactory, ViewStateController viewStateController) {
        this.mContext = context;
        this.mConditionChecker = conditionChecker;
        this.mHandler = handlerUtil;
        this.mResourceFactory = resourceFactory;
        this.mViewStateController = viewStateController;
    }

    public final AnimatorSet getDefaultConfirmAnimatorSet(boolean z) {
        float f;
        float paddingLeft;
        AnimatorSet animatorSet = new AnimatorSet();
        ViewGroup viewGroup = this.mConfirmIconLabelView;
        float[] fArr = new float[2];
        fArr[0] = viewGroup.getY();
        if (z) {
            f = this.mOriginalConfirmLocationY;
        } else {
            int[] iArr = new int[2];
            this.mSelectedActionView.getLocationInWindow(iArr);
            f = iArr[1];
        }
        fArr[1] = f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "y", fArr);
        ViewGroup viewGroup2 = this.mConfirmIconLabelView;
        float[] fArr2 = new float[2];
        fArr2[0] = viewGroup2.getX();
        if (z) {
            paddingLeft = this.mOriginalConfirmLocationX - this.mRootView.getPaddingLeft();
        } else {
            int[] iArr2 = new int[2];
            this.mCallback.getConfirmIconLabelView(this.mSelectedActionView).getLocationInWindow(iArr2);
            paddingLeft = iArr2[0] - this.mRootView.getPaddingLeft();
        }
        fArr2[1] = paddingLeft;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(viewGroup2, "x", fArr2);
        TextView textView = this.mConfirmDescriptionView;
        float[] fArr3 = new float[2];
        fArr3[0] = z ? 0.0f : 1.0f;
        fArr3[1] = z ? 1.0f : 0.0f;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", fArr3);
        ofFloat3.setDuration(200L);
        if (FrontLargeCoverContentView.this.mIsIconOnly) {
            ViewGroup viewGroup3 = this.mConfirmIconLabelView;
            float[] fArr4 = new float[2];
            fArr4[0] = viewGroup3.getScaleX();
            fArr4[1] = z ? 0.75f : 1.0f;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(viewGroup3, "scaleX", fArr4);
            ViewGroup viewGroup4 = this.mConfirmIconLabelView;
            float[] fArr5 = new float[2];
            fArr5[0] = viewGroup4.getScaleY();
            fArr5[1] = z ? 0.75f : 1.0f;
            animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ObjectAnimator.ofFloat(viewGroup4, "scaleY", fArr5));
        } else {
            animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        }
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(200L);
        if (z) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FrontLargeCoverViewAnimator frontLargeCoverViewAnimator = FrontLargeCoverViewAnimator.this;
                    FrontLargeCoverContentView.this.mAdapter.mIsConfirmView = true;
                    frontLargeCoverViewAnimator.mSelectedActionView.setVisibility(0);
                    FrontLargeCoverContentView.this.mListView.setVisibility(4);
                    FrontLargeCoverViewAnimator frontLargeCoverViewAnimator2 = FrontLargeCoverViewAnimator.this;
                    frontLargeCoverViewAnimator2.getClass();
                    frontLargeCoverViewAnimator2.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    FrontLargeCoverViewAnimator.this.mConfirmIconLabelView.setAlpha(1.0f);
                    FrontLargeCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    FrontLargeCoverContentView.this.mListView.animate().alpha(0.0f).setDuration(200L).start();
                }
            });
        } else {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FrontLargeCoverViewAnimator.this.mSelectedActionView.setVisibility(0);
                    FrontLargeCoverViewAnimator.this.mConfirmView.removeAllViews();
                    FrontLargeCoverViewAnimator.this.mConfirmView.setVisibility(8);
                    View view = FrontLargeCoverContentView.this.mAdapter.mLastAnimatedView;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    FrontLargeCoverContentView.this.mSelectedViewModel.getActionInfo().setStateLabel("");
                    FrontLargeCoverViewAnimator frontLargeCoverViewAnimator = FrontLargeCoverViewAnimator.this;
                    frontLargeCoverViewAnimator.getClass();
                    frontLargeCoverViewAnimator.mViewStateController.setState(ViewAnimationState.IDLE);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    FrontLargeCoverContentView.this.mListView.setVisibility(0);
                    FrontLargeCoverViewAnimator.this.mSelectedActionView.setVisibility(4);
                    FrontLargeCoverContentView.this.mListView.animate().alpha(1.0f).setDuration(200L).start();
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
        FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
        frontLargeCoverContentView.mAdapter.mIsConfirmView = false;
        if (!z) {
            frontLargeCoverContentView.mParentView.setCoverSecureConfirmState(false);
            frontLargeCoverContentView.mForceDismiss = false;
            this.mHandler.removeCallbacks(this.mDismissDuringSecureConfirm);
            this.mViewStateController.setState(ViewAnimationState.IDLE);
            FrontLargeCoverContentView.C14283 c14283 = this.mCallback;
            c14283.getClass();
            FrontLargeCoverContentView frontLargeCoverContentView2 = FrontLargeCoverContentView.this;
            frontLargeCoverContentView2.mParentView.dismiss();
            frontLargeCoverContentView2.mDialog.dismiss();
            return;
        }
        frontLargeCoverContentView.mParentView.setCoverSecureConfirmState(true);
        frontLargeCoverContentView.mForceDismiss = true;
        ViewGroup viewGroup = (ViewGroup) this.mConfirmView.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_BTN_BACKGROUND));
        this.mConfirmIconLabelView = viewGroup;
        viewGroup.setVisibility(8);
        this.mConfirmDescriptionView = (TextView) this.mConfirmView.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_TEXT));
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED)) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.global_actions_camera_view_cover_noti_padding);
            this.mConfirmDescriptionView.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
            this.mConfirmDescriptionView.setTextSize(this.mContext.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_noti_font_size));
            this.mConfirmDescriptionView.setTextColor(this.mContext.getResources().getColor(R.color.sec_global_actions_camera_view_cover_noti_text));
            if (FrontLargeCoverContentView.this.mSelectedViewModel.getActionInfo().getName() == "power") {
                this.mConfirmDescriptionView.setText(this.mContext.getResources().getString(android.R.string.mediasize_iso_b7));
            } else {
                this.mConfirmDescriptionView.setText(this.mContext.getResources().getString(android.R.string.mediasize_iso_b8));
            }
        } else {
            this.mConfirmDescriptionView.setText(this.mContext.getResources().getString(android.R.string.mediasize_iso_b9));
            if (!FrontLargeCoverContentView.this.mIsIconOnly) {
                ((TextView) this.mConfirmView.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_TEXT))).setTextSize(0, 30.0f);
            }
            this.mHandler.postDelayed(this.mDismissDuringSecureConfirm, 3000L);
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FrontLargeCoverViewAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        }, 200L);
    }

    public final void startDismissConfirmAnimation() {
        FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
        if (!frontLargeCoverContentView.mForceDismiss) {
            this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
            getDefaultConfirmAnimatorSet(false).start();
            return;
        }
        frontLargeCoverContentView.mParentView.setCoverSecureConfirmState(false);
        frontLargeCoverContentView.mForceDismiss = false;
        FrontLargeCoverContentView.C14283 c14283 = this.mCallback;
        c14283.getClass();
        FrontLargeCoverContentView frontLargeCoverContentView2 = FrontLargeCoverContentView.this;
        frontLargeCoverContentView2.mParentView.dismiss();
        frontLargeCoverContentView2.mDialog.dismiss();
        this.mViewStateController.setState(ViewAnimationState.IDLE);
    }

    /* JADX WARN: Type inference failed for: r0v24, types: [com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator$$ExternalSyntheticLambda0] */
    public final void startShowConfirmAnimation() {
        FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
        FrontLargeCoverContentItemView frontLargeCoverContentItemView = new FrontLargeCoverContentItemView(frontLargeCoverContentView.mDialog.getContext(), frontLargeCoverContentView.mSelectedViewModel, frontLargeCoverContentView.mConfirmView, frontLargeCoverContentView.mIsIconOnly, frontLargeCoverContentView.mIsWhiteTheme, frontLargeCoverContentView.mIsCameraViewCover, frontLargeCoverContentView.mResourceFactory);
        frontLargeCoverContentView.mConfirmView.removeAllViews();
        ViewGroup viewGroup = frontLargeCoverContentView.mConfirmView;
        LayoutInflater from = LayoutInflater.from(frontLargeCoverContentItemView.mContext);
        ResourceType resourceType = ResourceType.LAYOUT_FRONT_LARGE_COVER_ITEM;
        ResourceFactory resourceFactory = frontLargeCoverContentItemView.mResourceFactory;
        View inflate = from.inflate(resourceFactory.get(resourceType), frontLargeCoverContentItemView.mParent, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        inflate.setLayoutParams(layoutParams);
        frontLargeCoverContentItemView.setViewAttrs(inflate, true);
        inflate.setOnClickListener(new FrontCoverContentItemView$$ExternalSyntheticLambda1());
        inflate.setClickable(false);
        ((TextView) inflate.findViewById(resourceFactory.get(ResourceType.ID_COVER_TEXT))).setVisibility(0);
        viewGroup.addView(inflate);
        frontLargeCoverContentView.mConfirmView.setVisibility(0);
        FrontLargeCoverContentView.C14283 c14283 = this.mCallback;
        ViewGroup viewGroup2 = FrontLargeCoverContentView.this.mConfirmView;
        this.mConfirmView = viewGroup2;
        this.mConfirmIconLabelView = c14283.getConfirmIconLabelView(viewGroup2);
        this.mConfirmDescriptionView = (TextView) this.mConfirmView.findViewById(FrontLargeCoverContentView.this.mResourceFactory.get(ResourceType.ID_COVER_TEXT));
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME)) {
            this.mRootView.setBackgroundColor(Color.parseColor("#fafafa"));
        } else {
            this.mRootView.setBackgroundColor(Color.parseColor(SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") ? "#000000" : "#0A0A0A"));
        }
        this.mRootView.getBackground().setAlpha(0);
        FrontLargeCoverContentView frontLargeCoverContentView2 = FrontLargeCoverContentView.this;
        this.mSelectedActionView = (ViewGroup) frontLargeCoverContentView2.mListView.getChildAt(frontLargeCoverContentView2.mSelectedViewModel.getActionInfo().getViewIndex());
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverViewAnimator$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                FrontLargeCoverViewAnimator frontLargeCoverViewAnimator = FrontLargeCoverViewAnimator.this;
                frontLargeCoverViewAnimator.mConfirmView.getViewTreeObserver().removeOnGlobalLayoutListener(frontLargeCoverViewAnimator.mViewTreeObserverListener);
                frontLargeCoverViewAnimator.mConfirmIconLabelView.getLocationInWindow(new int[2]);
                frontLargeCoverViewAnimator.mOriginalConfirmLocationX = r2[0];
                frontLargeCoverViewAnimator.mConfirmIconLabelView.getLocationInWindow(new int[2]);
                frontLargeCoverViewAnimator.mOriginalConfirmLocationY = r3[1];
                ViewGroup viewGroup3 = frontLargeCoverViewAnimator.mConfirmIconLabelView;
                frontLargeCoverViewAnimator.mSelectedActionView.getLocationInWindow(new int[2]);
                viewGroup3.setY(r5[1]);
                ViewGroup viewGroup4 = frontLargeCoverViewAnimator.mConfirmIconLabelView;
                frontLargeCoverViewAnimator.mCallback.getConfirmIconLabelView(frontLargeCoverViewAnimator.mSelectedActionView).getLocationInWindow(new int[2]);
                viewGroup4.setX(r1[0] - frontLargeCoverViewAnimator.mRootView.getPaddingLeft());
                frontLargeCoverViewAnimator.getDefaultConfirmAnimatorSet(true).start();
            }
        };
        this.mConfirmView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
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
