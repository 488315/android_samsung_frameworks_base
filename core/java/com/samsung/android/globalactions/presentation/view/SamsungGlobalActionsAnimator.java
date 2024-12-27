package com.samsung.android.globalactions.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;

/* loaded from: classes6.dex */
public class SamsungGlobalActionsAnimator implements GlobalActionsAnimator {
    private static final String TAG = "SamsungGlobalActionsAnimator";
    private ViewGroup mBackgroundView;
    private View mBottomView;
    private ViewUpdateCallback mCallback;
    private final ConditionChecker mConditionChecker;
    private View mConfirmDescriptionView;
    private ViewGroup mConfirmIconView;
    private ViewGroup mConfirmationView;
    private final Context mContext;
    private AnimatorSet mDismissConfirmAnimatorSet;
    private ViewGroup mLandListView;
    private ViewGroup mListView;
    private final LogWrapper mLogWrapper;
    private float mOriginalConfirmLocationX;
    private float mOriginalConfirmLocationY;
    private View mPowerOffIconView;
    private ViewGroup mRootView;
    private ViewGroup mSelectedActionView;
    private AnimatorSet mShowConfirmAnimatorSet;
    private ViewGroup mTargetListView;
    private ViewStateController mViewStateController;
    private ViewTreeObserver.OnGlobalLayoutListener mViewTreeObserverListener;
    private final float CONFIRM_ANIMATION_SCALE_ORIGIN = 1.0f;
    private final float CONFIRM_ANIMATION_SCALE = 1.3f;
    private final int CONFIRM_ANIMATION_DURATION_ICON = 300;
    private final int CONFIRM_ANIMATION_DURATION_DESCRIPTION = 400;
    private final int CONFIRM_ANIMATION_DURATION_LIST = 200;
    private final int SHOW_DISMISS_ANIMATION_DURATION = 300;
    private final int HIDE_DIALOG_WITHOUT_DISMISS_DURATION_WITH_LOCK = 200;
    private final int SAFE_MODE_CONFIRM_ANIMATION_DURATION_ALPHA = 200;
    private final int HIDE_DIALOG_WITHOUT_DISMISS_DURATION = 0;
    private final float ALPHA_HIDE = 0.0f;
    private final float ALPHA_SHOW = 1.0f;
    private final PathInterpolator CONFIRM_ANIMATION_INTERPOLATOR =
            new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f);
    private final int ALPHA_HIDE_INT = 0;
    private final int ALPHA_SHOW_INT = 255;

    public interface ViewUpdateCallback {
        GlobalActionsContentItemView createConfirmView();

        View getBottomView();

        boolean getClearCoverState();

        View getConfirmDescriptionView(ViewGroup viewGroup);

        ViewGroup getConfirmIconLabelView(ViewGroup viewGroup);

        ViewGroup getConfirmationView();

        Dialog getDialog();

        Runnable getDismissRunnable();

        boolean getForceDismissState();

        ViewGroup getLandscapeListView();

        ViewGroup getListView();

        ViewGroup getPowerOffViewForSafeModeVI(
                GlobalActionsContentItemView globalActionsContentItemView);

        ViewGroup getRootView();

        ViewGroup getSelectedActionView(ViewGroup viewGroup);

        boolean isSafeModeConfirm();

        void requestFocusFor(ViewGroup viewGroup, ViewGroup viewGroup2);

        void setFlagsForForceDismiss(boolean z);

        default ViewGroup getBackgroundView() {
            return null;
        }
    }

    public SamsungGlobalActionsAnimator(
            Context context,
            ConditionChecker conditionChecker,
            LogWrapper logWrapper,
            ViewStateController stateController) {
        this.mContext = context;
        this.mConditionChecker = conditionChecker;
        this.mLogWrapper = logWrapper;
        this.mViewStateController = stateController;
    }

    public void setCallback(ViewUpdateCallback callback) {
        this.mCallback = callback;
        initViews();
    }

    private void initViews() {
        this.mRootView = this.mCallback.getRootView();
        this.mBackgroundView = this.mCallback.getBackgroundView();
        this.mListView = this.mCallback.getListView();
        this.mLandListView = this.mCallback.getLandscapeListView();
        this.mBottomView = this.mCallback.getBottomView();
        this.mConfirmationView = this.mCallback.getConfirmationView();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startShowAnimation() {
        AnimatorSet animatorSet = getDefaultAnimatorSet(true);
        animatorSet.setDuration(300L);
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        SamsungGlobalActionsAnimator.this.mViewStateController.setState(
                                ViewAnimationState.IDLE);
                    }
                });
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        animatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startDismissAnimation(final boolean isSecureConfirming) {
        AnimatorSet animatorSet = getDefaultAnimatorSet(false);
        if (isSecureConfirming) {
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_POWER_OFF_LOCK)) {
                animatorSet.setDuration(0L);
            } else {
                animatorSet.setDuration(200L);
            }
        } else {
            animatorSet.setDuration(300L);
        }
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        Dialog dialog = SamsungGlobalActionsAnimator.this.mCallback.getDialog();
                        if (dialog != null) {
                            dialog.getWindow().getDecorView().setVisibility(8);
                        }
                        if (!isSecureConfirming) {
                            SamsungGlobalActionsAnimator.this.mCallback.getDismissRunnable().run();
                        }
                        if (isSecureConfirming
                                && SamsungGlobalActionsAnimator.this.mCallback
                                        .getClearCoverState()) {
                            SamsungGlobalActionsAnimator.this.mCallback.setFlagsForForceDismiss(
                                    true);
                        }
                        SamsungGlobalActionsAnimator.this.mViewStateController.setState(
                                ViewAnimationState.IDLE);
                    }
                });
        if (this.mCallback.getDialog() != null) {
            WindowManager.LayoutParams params =
                    this.mCallback.getDialog().getWindow().getAttributes();
            params.dimAmount = 0.0f;
            this.mCallback.getDialog().getWindow().setAttributes(params);
        }
        this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
        animatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startShowConfirmAnimation() {
        initializeConfirmView();
        initializeSelectedActionView();
        initializeConfirmBackgroundView();
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener =
                new ViewTreeObserver
                        .OnGlobalLayoutListener() { // from class:
                                                    // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda2
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        SamsungGlobalActionsAnimator.this.lambda$startShowConfirmAnimation$0();
                    }
                };
        this.mConfirmationView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowConfirmAnimation$0() {
        this.mConfirmationView
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        saveOriginalConfirmViewLocation();
        float deltaY = (this.mConfirmIconView.getHeight() * 0.3f) / 2.0f;
        setLocationForDescriptionView(deltaY);
        this.mConfirmIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mConfirmIconView.setX(
                getOriginalLocationX(
                                this.mCallback.getConfirmIconLabelView(this.mSelectedActionView))
                        - this.mRootView.getPaddingLeft());
        this.mShowConfirmAnimatorSet = getDefaultConfirmAnimatorSet(true);
        this.mShowConfirmAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startDismissConfirmAnimation() {
        if (this.mCallback.getForceDismissState()) {
            this.mCallback.setFlagsForForceDismiss(false);
            this.mCallback.getDismissRunnable().run();
            this.mViewStateController.setState(ViewAnimationState.IDLE);
        } else {
            this.mDismissConfirmAnimatorSet = getDefaultConfirmAnimatorSet(false);
            this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
            this.mDismissConfirmAnimatorSet.start();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startShowSafeModeAnimation() {
        initializeConfirmViewForSafeMode();
        initializeSelectedActionView();
        initializeConfirmBackgroundView();
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener =
                new ViewTreeObserver
                        .OnGlobalLayoutListener() { // from class:
                                                    // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        SamsungGlobalActionsAnimator.this.lambda$startShowSafeModeAnimation$1();
                    }
                };
        this.mConfirmationView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowSafeModeAnimation$1() {
        this.mConfirmationView
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        saveOriginalConfirmViewLocation();
        float deltaY = (this.mConfirmIconView.getHeight() * 0.3f) / 2.0f;
        setLocationForDescriptionView(deltaY);
        this.mConfirmIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mPowerOffIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mConfirmIconView.setX(
                getOriginalLocationX(
                                this.mCallback.getConfirmIconLabelView(this.mSelectedActionView))
                        - this.mRootView.getPaddingLeft());
        this.mPowerOffIconView.setX(
                getOriginalLocationX(
                                this.mCallback.getConfirmIconLabelView(this.mSelectedActionView))
                        - this.mRootView.getPaddingLeft());
        this.mShowConfirmAnimatorSet = getDefaultConfirmAnimatorSet(true);
        this.mShowConfirmAnimatorSet.playTogether(getSafeModeConfirmAnimation(true));
        this.mShowConfirmAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startDismissSafeModeAnimation() {
        this.mDismissConfirmAnimatorSet = getDefaultConfirmAnimatorSet(false);
        this.mDismissConfirmAnimatorSet.playTogether(getSafeModeConfirmAnimation(false));
        this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
        this.mDismissConfirmAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startSetSafeModeAnimation() {
        saveOriginalConfirmViewLocation();
        initializeConfirmViewForSafeMode();
        this.mViewTreeObserverListener =
                new ViewTreeObserver
                        .OnGlobalLayoutListener() { // from class:
                                                    // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        SamsungGlobalActionsAnimator.this.lambda$startSetSafeModeAnimation$2();
                    }
                };
        this.mConfirmationView
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSetSafeModeAnimation$2() {
        this.mConfirmationView
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        float deltaY = (this.mConfirmIconView.getHeight() * 0.3f) / 2.0f;
        startAnimationForSafeModeOnConfirm(deltaY);
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void showMainViewPort() {
        setListViewPort();
        this.mListView.setVisibility(0);
        this.mListView.setAlpha(1.0f);
        this.mLandListView.setVisibility(4);
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void showMainViewLand() {
        setListViewLand();
        this.mListView.setVisibility(4);
        this.mLandListView.setVisibility(0);
        this.mLandListView.setAlpha(1.0f);
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void initializeSelectedActionView() {
        this.mSelectedActionView = this.mCallback.getSelectedActionView(this.mTargetListView);
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void setListViewPort() {
        this.mTargetListView = this.mListView;
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void setListViewLand() {
        this.mTargetListView = this.mLandListView;
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public boolean isSafeModeConfirm() {
        return this.mCallback.isSafeModeConfirm();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public boolean isHideConfirmAnimationRunning() {
        return this.mDismissConfirmAnimatorSet != null
                && this.mDismissConfirmAnimatorSet.isRunning();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void cancelHideConfirmAnimation() {
        this.mDismissConfirmAnimatorSet.cancel();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public boolean isShowConfirmAnimationRunning() {
        return this.mShowConfirmAnimatorSet != null && this.mShowConfirmAnimatorSet.isRunning();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void cancelShowConfirmAnimation() {
        this.mShowConfirmAnimatorSet.cancel();
    }

    private void initializeConfirmBackgroundView() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME)) {
            this.mRootView.setBackgroundColor(Color.parseColor("#fafafa"));
        } else {
            this.mRootView.setBackgroundColor(Color.parseColor(getDarkThemeBackgroundColor()));
        }
        this.mRootView.getBackground().setAlpha(0);
    }

    private GlobalActionsContentItemView initializeConfirmView() {
        GlobalActionsContentItemView item = this.mCallback.createConfirmView();
        this.mConfirmationView = this.mCallback.getConfirmationView();
        this.mConfirmIconView = this.mCallback.getConfirmIconLabelView(this.mConfirmationView);
        this.mConfirmDescriptionView =
                this.mCallback.getConfirmDescriptionView(this.mConfirmationView);
        return item;
    }

    private AnimatorSet getDefaultAnimatorSet(boolean show) {
        ObjectAnimator backgroundAlpha;
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator contentsAlpha =
                ObjectAnimator.ofFloat(
                        this.mRootView, "alpha", show ? 0.0f : 1.0f, show ? 1.0f : 0.0f);
        if (this.mBackgroundView.getBackground() != null) {
            backgroundAlpha =
                    ObjectAnimator.ofInt(
                            this.mBackgroundView.getBackground().mutate(),
                            "alpha",
                            show ? 0 : 255,
                            show ? 255 : 0);
        } else {
            backgroundAlpha =
                    ObjectAnimator.ofFloat(
                            this.mBackgroundView, "alpha", show ? 0.0f : 1.0f, show ? 1.0f : 0.0f);
        }
        animatorSet.playTogether(backgroundAlpha, contentsAlpha);
        return animatorSet;
    }

    private AnimatorSet getDefaultConfirmAnimatorSet(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator iconScaleX =
                ObjectAnimator.ofFloat(
                        this.mConfirmIconView,
                        "scaleX",
                        this.mConfirmIconView.getScaleX(),
                        show ? 1.3f : 1.0f);
        ObjectAnimator iconScaleY =
                ObjectAnimator.ofFloat(
                        this.mConfirmIconView,
                        "scaleY",
                        this.mConfirmIconView.getScaleY(),
                        show ? 1.3f : 1.0f);
        ObjectAnimator iconTranslationY =
                ObjectAnimator.ofFloat(
                        this.mConfirmIconView,
                        "y",
                        this.mConfirmIconView.getY(),
                        show
                                ? this.mOriginalConfirmLocationY
                                : getOriginalLocationY(this.mSelectedActionView));
        ObjectAnimator iconTranslationX =
                ObjectAnimator.ofFloat(
                        this.mConfirmIconView,
                        "x",
                        this.mConfirmIconView.getX(),
                        show
                                ? this.mOriginalConfirmLocationX - this.mRootView.getPaddingLeft()
                                : getOriginalLocationX(
                                                this.mCallback.getConfirmIconLabelView(
                                                        this.mSelectedActionView))
                                        - this.mRootView.getPaddingLeft());
        ObjectAnimator descriptionAlpha =
                ObjectAnimator.ofFloat(
                        this.mConfirmDescriptionView,
                        "alpha",
                        this.mConfirmDescriptionView.getAlpha(),
                        show ? 1.0f : 0.0f);
        descriptionAlpha.setDuration(400L);
        ObjectAnimator backgroundAlpha =
                ObjectAnimator.ofInt(
                        this.mRootView.getBackground().mutate(),
                        "alpha",
                        this.mRootView.getBackground().mutate().getAlpha(),
                        show ? 255 : 0);
        animatorSet.playTogether(
                iconScaleX,
                iconScaleY,
                descriptionAlpha,
                backgroundAlpha,
                iconTranslationY,
                iconTranslationX);
        animatorSet.setInterpolator(this.CONFIRM_ANIMATION_INTERPOLATOR);
        animatorSet.setDuration(300L);
        addAnimatorListenerAdapter(animatorSet, show);
        return animatorSet;
    }

    private void addAnimatorListenerAdapter(AnimatorSet animatorSet, boolean show) {
        if (show) {
            animatorSet.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.3
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(
                                    SamsungGlobalActionsAnimator.TAG, "onAnimationStart() : show");
                            SamsungGlobalActionsAnimator.this.mCallback.requestFocusFor(
                                    SamsungGlobalActionsAnimator.this.mConfirmIconView,
                                    SamsungGlobalActionsAnimator.this.mSelectedActionView);
                            SamsungGlobalActionsAnimator.this.mConfirmIconView.setAlpha(1.0f);
                            SamsungGlobalActionsAnimator.this.mConfirmDescriptionView.setAlpha(
                                    0.0f);
                            SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleX(1.0f);
                            SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleY(1.0f);
                            SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(4);
                            SamsungGlobalActionsAnimator.this
                                    .mTargetListView
                                    .animate()
                                    .alpha(0.0f)
                                    .setDuration(200L)
                                    .start();
                            SamsungGlobalActionsAnimator.this
                                    .mBottomView
                                    .animate()
                                    .alpha(0.0f)
                                    .setDuration(200L)
                                    .start();
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(
                                    SamsungGlobalActionsAnimator.TAG, "onAnimationEnd() : show");
                            SamsungGlobalActionsAnimator.this.mConfirmDescriptionView.setAlpha(
                                    1.0f);
                            SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleX(1.3f);
                            SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleY(1.3f);
                            SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(0);
                            SamsungGlobalActionsAnimator.this.mTargetListView.setVisibility(4);
                            SamsungGlobalActionsAnimator.this.mBottomView.setVisibility(8);
                            SamsungGlobalActionsAnimator.this.mShowConfirmAnimatorSet = null;
                            SamsungGlobalActionsAnimator.this.mViewStateController.setState(
                                    ViewAnimationState.IDLE);
                        }
                    });
        } else {
            animatorSet.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.4
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(
                                    SamsungGlobalActionsAnimator.TAG, "onAnimationStart() : hide");
                            SamsungGlobalActionsAnimator.this.mTargetListView.setVisibility(0);
                            SamsungGlobalActionsAnimator.this.mBottomView.setVisibility(0);
                            SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(4);
                            SamsungGlobalActionsAnimator.this
                                    .mTargetListView
                                    .animate()
                                    .alpha(1.0f)
                                    .setDuration(200L)
                                    .start();
                            SamsungGlobalActionsAnimator.this
                                    .mBottomView
                                    .animate()
                                    .alpha(1.0f)
                                    .setDuration(200L)
                                    .start();
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(
                                    SamsungGlobalActionsAnimator.TAG, "onAnimationEnd() : hide");
                            SamsungGlobalActionsAnimator.this.mCallback.requestFocusFor(
                                    SamsungGlobalActionsAnimator.this.mSelectedActionView,
                                    SamsungGlobalActionsAnimator.this.mConfirmationView);
                            SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(0);
                            SamsungGlobalActionsAnimator.this.mConfirmationView.removeAllViews();
                            SamsungGlobalActionsAnimator.this.mConfirmationView.setVisibility(8);
                            SamsungGlobalActionsAnimator.this.mRootView.setDescendantFocusability(
                                    262144);
                            SamsungGlobalActionsAnimator.this.mDismissConfirmAnimatorSet = null;
                            SamsungGlobalActionsAnimator.this.mPowerOffIconView = null;
                            SamsungGlobalActionsAnimator.this.mViewStateController.setState(
                                    ViewAnimationState.IDLE);
                        }
                    });
        }
    }

    private void saveOriginalConfirmViewLocation() {
        this.mOriginalConfirmLocationX = getOriginalLocationX(this.mConfirmIconView);
        this.mOriginalConfirmLocationY = getOriginalLocationY(this.mConfirmIconView);
    }

    private void setLocationForDescriptionView(float deltaY) {
        float descriptionLocationY =
                (this.mOriginalConfirmLocationY - deltaY)
                        + (this.mConfirmIconView.getHeight() * 1.3f);
        this.mConfirmDescriptionView.setY((int) descriptionLocationY);
    }

    private void initializeConfirmViewForSafeMode() {
        GlobalActionsContentItemView item = initializeConfirmView();
        this.mPowerOffIconView = this.mCallback.getPowerOffViewForSafeModeVI(item);
    }

    private void startAnimationForSafeModeOnConfirm(float delta) {
        this.mPowerOffIconView.setScaleX(1.3f);
        this.mPowerOffIconView.setScaleY(1.3f);
        this.mPowerOffIconView.setY(this.mOriginalConfirmLocationY + delta);
        this.mConfirmIconView.setScaleX(1.3f);
        this.mConfirmIconView.setScaleY(1.3f);
        this.mConfirmIconView.setY(this.mOriginalConfirmLocationY + delta);
        float descriptionLocationY =
                this.mOriginalConfirmLocationY + (this.mConfirmIconView.getHeight() * 1.3f);
        this.mConfirmDescriptionView.setY((int) descriptionLocationY);
        ObjectAnimator safeModeIconShow =
                ObjectAnimator.ofFloat(this.mConfirmIconView, "alpha", 0.0f, 1.0f);
        ObjectAnimator powerOffIconHide =
                ObjectAnimator.ofFloat(this.mPowerOffIconView, "alpha", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200L);
        animatorSet.playTogether(safeModeIconShow, powerOffIconHide);
        animatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.5
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        SamsungGlobalActionsAnimator.this.mViewStateController.setState(
                                ViewAnimationState.IDLE);
                    }
                });
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        animatorSet.start();
    }

    private AnimatorSet getSafeModeConfirmAnimation(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator powerOffIconScaleX =
                ObjectAnimator.ofFloat(
                        this.mPowerOffIconView,
                        "scaleX",
                        this.mPowerOffIconView.getScaleX(),
                        show ? 1.3f : 1.0f);
        ObjectAnimator powerOffIconScaleY =
                ObjectAnimator.ofFloat(
                        this.mPowerOffIconView,
                        "scaleY",
                        this.mPowerOffIconView.getScaleY(),
                        show ? 1.3f : 1.0f);
        ObjectAnimator powerOffIconTranslationY =
                ObjectAnimator.ofFloat(
                        this.mPowerOffIconView,
                        "y",
                        this.mPowerOffIconView.getY(),
                        show
                                ? this.mOriginalConfirmLocationY
                                : getOriginalLocationY(this.mSelectedActionView));
        int deltaX = (this.mConfirmIconView.getWidth() - this.mPowerOffIconView.getWidth()) / 2;
        ObjectAnimator powerOffIconTranslationX =
                ObjectAnimator.ofFloat(
                        this.mPowerOffIconView,
                        "x",
                        this.mPowerOffIconView.getX(),
                        show
                                ? (this.mOriginalConfirmLocationX + deltaX)
                                        - this.mRootView.getPaddingLeft()
                                : getOriginalLocationX(
                                                this.mCallback.getConfirmIconLabelView(
                                                        this.mSelectedActionView))
                                        - this.mRootView.getPaddingLeft());
        animatorSet.playTogether(
                powerOffIconScaleX,
                powerOffIconScaleY,
                powerOffIconTranslationY,
                powerOffIconTranslationX);
        AnimatorSet animatorSetAlpha = new AnimatorSet();
        if (show) {
            ObjectAnimator safeModeIconShow =
                    ObjectAnimator.ofFloat(
                            this.mConfirmIconView, "alpha", this.mConfirmIconView.getAlpha(), 1.0f);
            animatorSetAlpha.playTogether(safeModeIconShow);
            this.mPowerOffIconView.setAlpha(0.0f);
        } else {
            ObjectAnimator powerOffIconShow =
                    ObjectAnimator.ofFloat(
                            this.mPowerOffIconView,
                            "alpha",
                            this.mPowerOffIconView.getAlpha(),
                            1.0f);
            animatorSetAlpha.playTogether(powerOffIconShow);
            this.mConfirmIconView.setAlpha(0.0f);
        }
        animatorSetAlpha.setDuration(200L);
        animatorSet.playTogether(animatorSetAlpha);
        return animatorSet;
    }

    private int getOriginalLocationX(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    private int getOriginalLocationY(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    private String getDarkThemeBackgroundColor() {
        return SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD")
                ? "#000000"
                : "#0A0A0A";
    }
}
