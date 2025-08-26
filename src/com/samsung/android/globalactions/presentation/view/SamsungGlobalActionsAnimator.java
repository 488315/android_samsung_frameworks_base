package com.samsung.android.globalactions.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
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
    private final PathInterpolator CONFIRM_ANIMATION_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f);
    private final int ALPHA_HIDE_INT = 0;
    private final int ALPHA_SHOW_INT = 255;

    public interface ViewUpdateCallback {
        GlobalActionsContentItemView createConfirmView();

        default ViewGroup getBackgroundView() {
            return null;
        }

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

        ViewGroup getPowerOffViewForSafeModeVI(GlobalActionsContentItemView globalActionsContentItemView);

        ViewGroup getRootView();

        ViewGroup getSelectedActionView(ViewGroup viewGroup);

        boolean isSafeModeConfirm();

        void requestFocusFor(ViewGroup viewGroup, ViewGroup viewGroup2);

        void setFlagsForForceDismiss(boolean z);
    }

    public SamsungGlobalActionsAnimator(Context context, ConditionChecker conditionChecker, LogWrapper logWrapper, ViewStateController viewStateController) {
        this.mContext = context;
        this.mConditionChecker = conditionChecker;
        this.mLogWrapper = logWrapper;
        this.mViewStateController = viewStateController;
    }

    public void setCallback(ViewUpdateCallback viewUpdateCallback) {
        this.mCallback = viewUpdateCallback;
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
        AnimatorSet defaultAnimatorSet = getDefaultAnimatorSet(true);
        defaultAnimatorSet.setDuration(300L);
        defaultAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SamsungGlobalActionsAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        });
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        defaultAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startDismissAnimation(final boolean z) {
        AnimatorSet defaultAnimatorSet = getDefaultAnimatorSet(false);
        if (z) {
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_POWER_OFF_LOCK)) {
                defaultAnimatorSet.setDuration(0L);
            } else {
                defaultAnimatorSet.setDuration(200L);
            }
        } else {
            defaultAnimatorSet.setDuration(300L);
        }
        defaultAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Dialog dialog = SamsungGlobalActionsAnimator.this.mCallback.getDialog();
                if (dialog != null) {
                    dialog.getWindow().getDecorView().setVisibility(8);
                }
                if (!z) {
                    SamsungGlobalActionsAnimator.this.mCallback.getDismissRunnable().run();
                }
                if (z && SamsungGlobalActionsAnimator.this.mCallback.getClearCoverState()) {
                    SamsungGlobalActionsAnimator.this.mCallback.setFlagsForForceDismiss(true);
                }
                SamsungGlobalActionsAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        });
        if (this.mCallback.getDialog() != null) {
            WindowManager.LayoutParams attributes = this.mCallback.getDialog().getWindow().getAttributes();
            attributes.dimAmount = 0.0f;
            this.mCallback.getDialog().getWindow().setAttributes(attributes);
        }
        this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
        defaultAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startShowConfirmAnimation() {
        initializeConfirmView();
        initializeSelectedActionView();
        initializeConfirmBackgroundView();
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                this.f$0.lambda$startShowConfirmAnimation$0();
            }
        };
        this.mConfirmationView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowConfirmAnimation$0() {
        this.mConfirmationView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        saveOriginalConfirmViewLocation();
        setLocationForDescriptionView((this.mConfirmIconView.getHeight() * 0.3f) / 2.0f);
        this.mConfirmIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mConfirmIconView.setX(getOriginalLocationX(this.mCallback.getConfirmIconLabelView(this.mSelectedActionView)) - this.mRootView.getPaddingLeft());
        AnimatorSet defaultConfirmAnimatorSet = getDefaultConfirmAnimatorSet(true);
        this.mShowConfirmAnimatorSet = defaultConfirmAnimatorSet;
        defaultConfirmAnimatorSet.start();
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
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                this.f$0.lambda$startShowSafeModeAnimation$1();
            }
        };
        this.mConfirmationView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowSafeModeAnimation$1() {
        this.mConfirmationView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        saveOriginalConfirmViewLocation();
        setLocationForDescriptionView((this.mConfirmIconView.getHeight() * 0.3f) / 2.0f);
        this.mConfirmIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mPowerOffIconView.setY(getOriginalLocationY(this.mSelectedActionView));
        this.mConfirmIconView.setX(getOriginalLocationX(this.mCallback.getConfirmIconLabelView(this.mSelectedActionView)) - this.mRootView.getPaddingLeft());
        this.mPowerOffIconView.setX(getOriginalLocationX(this.mCallback.getConfirmIconLabelView(this.mSelectedActionView)) - this.mRootView.getPaddingLeft());
        AnimatorSet defaultConfirmAnimatorSet = getDefaultConfirmAnimatorSet(true);
        this.mShowConfirmAnimatorSet = defaultConfirmAnimatorSet;
        defaultConfirmAnimatorSet.playTogether(getSafeModeConfirmAnimation(true));
        this.mShowConfirmAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startDismissSafeModeAnimation() {
        AnimatorSet defaultConfirmAnimatorSet = getDefaultConfirmAnimatorSet(false);
        this.mDismissConfirmAnimatorSet = defaultConfirmAnimatorSet;
        defaultConfirmAnimatorSet.playTogether(getSafeModeConfirmAnimation(false));
        this.mViewStateController.setState(ViewAnimationState.DISMISS_ANIMATE);
        this.mDismissConfirmAnimatorSet.start();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void startSetSafeModeAnimation() {
        saveOriginalConfirmViewLocation();
        initializeConfirmViewForSafeMode();
        this.mViewTreeObserverListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                this.f$0.lambda$startSetSafeModeAnimation$2();
            }
        };
        this.mConfirmationView.getViewTreeObserver().addOnGlobalLayoutListener(this.mViewTreeObserverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSetSafeModeAnimation$2() {
        this.mConfirmationView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mViewTreeObserverListener);
        startAnimationForSafeModeOnConfirm((this.mConfirmIconView.getHeight() * 0.3f) / 2.0f);
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
        AnimatorSet animatorSet = this.mDismissConfirmAnimatorSet;
        return animatorSet != null && animatorSet.isRunning();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public void cancelHideConfirmAnimation() {
        this.mDismissConfirmAnimatorSet.cancel();
    }

    @Override // com.samsung.android.globalactions.presentation.view.GlobalActionsAnimator
    public boolean isShowConfirmAnimationRunning() {
        AnimatorSet animatorSet = this.mShowConfirmAnimatorSet;
        return animatorSet != null && animatorSet.isRunning();
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
        GlobalActionsContentItemView globalActionsContentItemViewCreateConfirmView = this.mCallback.createConfirmView();
        ViewGroup confirmationView = this.mCallback.getConfirmationView();
        this.mConfirmationView = confirmationView;
        this.mConfirmIconView = this.mCallback.getConfirmIconLabelView(confirmationView);
        this.mConfirmDescriptionView = this.mCallback.getConfirmDescriptionView(this.mConfirmationView);
        return globalActionsContentItemViewCreateConfirmView;
    }

    private AnimatorSet getDefaultAnimatorSet(boolean z) {
        ObjectAnimator objectAnimatorOfFloat;
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mRootView, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        if (this.mBackgroundView.getBackground() != null) {
            objectAnimatorOfFloat = ObjectAnimator.ofInt(this.mBackgroundView.getBackground().mutate(), "alpha", z ? 0 : 255, z ? 255 : 0);
        } else {
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mBackgroundView, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        }
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        return animatorSet;
    }

    private AnimatorSet getDefaultConfirmAnimatorSet(boolean z) {
        float originalLocationX;
        AnimatorSet animatorSet = new AnimatorSet();
        ViewGroup viewGroup = this.mConfirmIconView;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewGroup, "scaleX", viewGroup.getScaleX(), z ? 1.3f : 1.0f);
        ViewGroup viewGroup2 = this.mConfirmIconView;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewGroup2, "scaleY", viewGroup2.getScaleY(), z ? 1.3f : 1.0f);
        ViewGroup viewGroup3 = this.mConfirmIconView;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(viewGroup3, "y", viewGroup3.getY(), z ? this.mOriginalConfirmLocationY : getOriginalLocationY(this.mSelectedActionView));
        ViewGroup viewGroup4 = this.mConfirmIconView;
        float x = viewGroup4.getX();
        if (z) {
            originalLocationX = this.mOriginalConfirmLocationX - this.mRootView.getPaddingLeft();
        } else {
            originalLocationX = getOriginalLocationX(this.mCallback.getConfirmIconLabelView(this.mSelectedActionView)) - this.mRootView.getPaddingLeft();
        }
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewGroup4, "x", x, originalLocationX);
        View view = this.mConfirmDescriptionView;
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), z ? 1.0f : 0.0f);
        objectAnimatorOfFloat5.setDuration(400L);
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat5, ObjectAnimator.ofInt(this.mRootView.getBackground().mutate(), "alpha", this.mRootView.getBackground().mutate().getAlpha(), z ? 255 : 0), objectAnimatorOfFloat3, objectAnimatorOfFloat4);
        animatorSet.setInterpolator(this.CONFIRM_ANIMATION_INTERPOLATOR);
        animatorSet.setDuration(300L);
        addAnimatorListenerAdapter(animatorSet, z);
        return animatorSet;
    }

    private void addAnimatorListenerAdapter(AnimatorSet animatorSet, boolean z) {
        if (z) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(SamsungGlobalActionsAnimator.TAG, "onAnimationStart() : show");
                    SamsungGlobalActionsAnimator.this.mCallback.requestFocusFor(SamsungGlobalActionsAnimator.this.mConfirmIconView, SamsungGlobalActionsAnimator.this.mSelectedActionView);
                    SamsungGlobalActionsAnimator.this.mConfirmIconView.setAlpha(1.0f);
                    SamsungGlobalActionsAnimator.this.mConfirmDescriptionView.setAlpha(0.0f);
                    SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleX(1.0f);
                    SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleY(1.0f);
                    SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(4);
                    SamsungGlobalActionsAnimator.this.mTargetListView.animate().alpha(0.0f).setDuration(200L).start();
                    SamsungGlobalActionsAnimator.this.mBottomView.animate().alpha(0.0f).setDuration(200L).start();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(SamsungGlobalActionsAnimator.TAG, "onAnimationEnd() : show");
                    SamsungGlobalActionsAnimator.this.mConfirmDescriptionView.setAlpha(1.0f);
                    SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleX(1.3f);
                    SamsungGlobalActionsAnimator.this.mConfirmIconView.setScaleY(1.3f);
                    SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(0);
                    SamsungGlobalActionsAnimator.this.mTargetListView.setVisibility(4);
                    SamsungGlobalActionsAnimator.this.mBottomView.setVisibility(8);
                    SamsungGlobalActionsAnimator.this.mShowConfirmAnimatorSet = null;
                    SamsungGlobalActionsAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
                }
            });
        } else {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(SamsungGlobalActionsAnimator.TAG, "onAnimationStart() : hide");
                    SamsungGlobalActionsAnimator.this.mTargetListView.setVisibility(0);
                    SamsungGlobalActionsAnimator.this.mBottomView.setVisibility(0);
                    SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(4);
                    SamsungGlobalActionsAnimator.this.mTargetListView.animate().alpha(1.0f).setDuration(200L).start();
                    SamsungGlobalActionsAnimator.this.mBottomView.animate().alpha(1.0f).setDuration(200L).start();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                    SamsungGlobalActionsAnimator.this.mLogWrapper.logDebug(SamsungGlobalActionsAnimator.TAG, "onAnimationEnd() : hide");
                    SamsungGlobalActionsAnimator.this.mCallback.requestFocusFor(SamsungGlobalActionsAnimator.this.mSelectedActionView, SamsungGlobalActionsAnimator.this.mConfirmationView);
                    SamsungGlobalActionsAnimator.this.mSelectedActionView.setVisibility(0);
                    SamsungGlobalActionsAnimator.this.mConfirmationView.removeAllViews();
                    SamsungGlobalActionsAnimator.this.mConfirmationView.setVisibility(8);
                    SamsungGlobalActionsAnimator.this.mRootView.setDescendantFocusability(262144);
                    SamsungGlobalActionsAnimator.this.mDismissConfirmAnimatorSet = null;
                    SamsungGlobalActionsAnimator.this.mPowerOffIconView = null;
                    SamsungGlobalActionsAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
                }
            });
        }
    }

    private void saveOriginalConfirmViewLocation() {
        this.mOriginalConfirmLocationX = getOriginalLocationX(this.mConfirmIconView);
        this.mOriginalConfirmLocationY = getOriginalLocationY(this.mConfirmIconView);
    }

    private void setLocationForDescriptionView(float f) {
        this.mConfirmDescriptionView.setY((int) ((this.mOriginalConfirmLocationY - f) + (this.mConfirmIconView.getHeight() * 1.3f)));
    }

    private void initializeConfirmViewForSafeMode() {
        this.mPowerOffIconView = this.mCallback.getPowerOffViewForSafeModeVI(initializeConfirmView());
    }

    private void startAnimationForSafeModeOnConfirm(float f) {
        this.mPowerOffIconView.setScaleX(1.3f);
        this.mPowerOffIconView.setScaleY(1.3f);
        this.mPowerOffIconView.setY(this.mOriginalConfirmLocationY + f);
        this.mConfirmIconView.setScaleX(1.3f);
        this.mConfirmIconView.setScaleY(1.3f);
        this.mConfirmIconView.setY(this.mOriginalConfirmLocationY + f);
        this.mConfirmDescriptionView.setY((int) (this.mOriginalConfirmLocationY + (this.mConfirmIconView.getHeight() * 1.3f)));
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mConfirmIconView, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mPowerOffIconView, "alpha", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200L);
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SamsungGlobalActionsAnimator.this.mViewStateController.setState(ViewAnimationState.IDLE);
            }
        });
        this.mViewStateController.setState(ViewAnimationState.SHOW_ANIMATE);
        animatorSet.start();
    }

    private AnimatorSet getSafeModeConfirmAnimation(boolean z) {
        float originalLocationX;
        AnimatorSet animatorSet = new AnimatorSet();
        View view = this.mPowerOffIconView;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "scaleX", view.getScaleX(), z ? 1.3f : 1.0f);
        View view2 = this.mPowerOffIconView;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, "scaleY", view2.getScaleY(), z ? 1.3f : 1.0f);
        View view3 = this.mPowerOffIconView;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view3, "y", view3.getY(), z ? this.mOriginalConfirmLocationY : getOriginalLocationY(this.mSelectedActionView));
        int width = (this.mConfirmIconView.getWidth() - this.mPowerOffIconView.getWidth()) / 2;
        View view4 = this.mPowerOffIconView;
        float x = view4.getX();
        if (z) {
            originalLocationX = (this.mOriginalConfirmLocationX + width) - this.mRootView.getPaddingLeft();
        } else {
            originalLocationX = getOriginalLocationX(this.mCallback.getConfirmIconLabelView(this.mSelectedActionView)) - this.mRootView.getPaddingLeft();
        }
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, ObjectAnimator.ofFloat(view4, "x", x, originalLocationX));
        AnimatorSet animatorSet2 = new AnimatorSet();
        if (z) {
            ViewGroup viewGroup = this.mConfirmIconView;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(viewGroup, "alpha", viewGroup.getAlpha(), 1.0f));
            this.mPowerOffIconView.setAlpha(0.0f);
        } else {
            View view5 = this.mPowerOffIconView;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(view5, "alpha", view5.getAlpha(), 1.0f));
            this.mConfirmIconView.setAlpha(0.0f);
        }
        animatorSet2.setDuration(200L);
        animatorSet.playTogether(animatorSet2);
        return animatorSet;
    }

    private int getOriginalLocationX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[0];
    }

    private int getOriginalLocationY(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[1];
    }

    private String getDarkThemeBackgroundColor() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") ? "#000000" : "#0A0A0A";
    }
}
