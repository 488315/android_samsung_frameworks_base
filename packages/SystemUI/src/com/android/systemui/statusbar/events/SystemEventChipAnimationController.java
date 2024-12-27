package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import com.android.systemui.R;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.events.BatteryChipAnimationUtils;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.leak.RotationUtils;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

public final class SystemEventChipAnimationController implements SystemStatusAnimationCallback {
    public final FrameLayout animationWindowView;
    public final int chipMinWidth;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public final Context context;
    public BackgroundAnimatableView currentAnimatedView;
    public int dotMarginStart;
    public int dotSize;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isChipAnimationStarted;
    public final PrivacyLogger privacyLogger;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ContextThemeWrapper themedContext;
    public int animationDirection = 1;
    public Rect chipBounds = new Rect();
    public final Rect animRect = new Rect();

    public SystemEventChipAnimationController(Context context, StatusBarWindowController statusBarWindowController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, ShadeExpansionStateManager shadeExpansionStateManager, PrivacyLogger privacyLogger, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.privacyLogger = privacyLogger;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.chipMinWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_min_animation_width);
        this.dotSize = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter);
        this.dotMarginStart = context.getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
        this.themedContext = contextThemeWrapper;
        this.animationWindowView = (FrameLayout) LayoutInflater.from(contextThemeWrapper).inflate(R.layout.system_event_animation_window, (ViewGroup) null);
        ContextThemeWrapper contextThemeWrapper2 = this.themedContext;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, (contextThemeWrapper2 == null ? null : contextThemeWrapper2).getResources().getDimensionPixelSize(R.dimen.status_bar_height));
        layoutParams.gravity = 8388661;
        FrameLayout frameLayout = this.animationWindowView;
        statusBarWindowController.mStatusBarWindowView.addView(frameLayout == null ? null : frameLayout, layoutParams);
        FrameLayout frameLayout2 = this.animationWindowView;
        (frameLayout2 == null ? null : frameLayout2).setClipToPadding(false);
        FrameLayout frameLayout3 = this.animationWindowView;
        (frameLayout3 == null ? null : frameLayout3).setClipChildren(false);
        FrameLayout frameLayout4 = this.animationWindowView;
        (frameLayout4 != null ? frameLayout4 : null).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$init$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                int calculateRightPadding;
                int i9;
                if (i == i5 && i3 == i7) {
                    return;
                }
                StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = SystemEventChipAnimationController.this.contentInsetsProvider;
                Rect statusBarContentAreaForRotation = statusBarContentInsetsProvider2.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProvider2.context));
                SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                FrameLayout frameLayout5 = systemEventChipAnimationController.animationWindowView;
                if (frameLayout5 == null) {
                    frameLayout5 = null;
                }
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) frameLayout5.getLayoutParams();
                layoutParams2.height = statusBarContentAreaForRotation.height();
                FrameLayout frameLayout6 = systemEventChipAnimationController.animationWindowView;
                if (frameLayout6 == null) {
                    frameLayout6 = null;
                }
                frameLayout6.setLayoutParams(layoutParams2);
                final SystemEventChipAnimationController systemEventChipAnimationController2 = SystemEventChipAnimationController.this;
                BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationController2.currentAnimatedView;
                if (backgroundAnimatableView != 0) {
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) ((View) backgroundAnimatableView).getLayoutParams();
                    FrameLayout frameLayout7 = systemEventChipAnimationController2.animationWindowView;
                    boolean isLayoutRtl = (frameLayout7 != null ? frameLayout7 : null).isLayoutRtl();
                    IndicatorGardenPresenter indicatorGardenPresenter2 = systemEventChipAnimationController2.indicatorGardenPresenter;
                    if (isLayoutRtl) {
                        calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding();
                        i9 = systemEventChipAnimationController2.dotMarginStart;
                    } else {
                        calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding();
                        i9 = systemEventChipAnimationController2.dotMarginStart;
                    }
                    layoutParams3.setMarginEnd(calculateRightPadding - i9);
                    Object obj = systemEventChipAnimationController2.currentAnimatedView;
                    Intrinsics.checkNotNull(obj);
                    ((View) obj).setLayoutParams(layoutParams3);
                    systemEventChipAnimationController2.updateChipBounds(backgroundAnimatableView, statusBarContentAreaForRotation);
                    ValueAnimator ofInt = ValueAnimator.ofInt(0, 1);
                    ofInt.setDuration(0L);
                    ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$init$1$1$1
                        @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                        public final void onAnimationUpdate(Animator animator) {
                            SystemEventChipAnimationController.this.updateCurrentAnimatedView();
                        }
                    });
                    ofInt.start(false);
                }
            }
        });
    }

    public static final void access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController systemEventChipAnimationController, int i, int i2) {
        Rect rect = systemEventChipAnimationController.animRect;
        float f = i / 2;
        rect.set(rect.left, i2 - MathKt__MathJVMKt.roundToInt(f), systemEventChipAnimationController.animRect.right, MathKt__MathJVMKt.roundToInt(f) + i2);
        systemEventChipAnimationController.updateCurrentAnimatedView();
    }

    public static final void access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController systemEventChipAnimationController, int i) {
        if (systemEventChipAnimationController.animationDirection == 1) {
            Rect rect = systemEventChipAnimationController.animRect;
            int i2 = systemEventChipAnimationController.chipBounds.right;
            rect.set(i2 - i, rect.top, i2, rect.bottom);
        } else {
            Rect rect2 = systemEventChipAnimationController.animRect;
            int i3 = systemEventChipAnimationController.chipBounds.left;
            rect2.set(i3, rect2.top, i + i3, rect2.bottom);
        }
        systemEventChipAnimationController.updateCurrentAnimatedView();
    }

    public final View getBatteryMeterView$1() {
        FrameLayout frameLayout = this.animationWindowView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        return frameLayout.getRootView().requireViewById(R.id.battery);
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z, boolean z2) {
        this.animRect.set(this.chipBounds);
        if (z2) {
            boolean z3 = !this.shadeExpansionStateManager.isClosed();
            if (z3) {
                return new AnimatorSet();
            }
            if (!z3) {
                this.isChipAnimationStarted = true;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            Object obj = this.currentAnimatedView;
            View view = obj != null ? (View) obj : null;
            if (view != null) {
                view.setAlpha(1.0f);
            }
            Object obj2 = this.currentAnimatedView;
            SamsungBatteryStatusChip samsungBatteryStatusChip = (SamsungBatteryStatusChip) (obj2 != null ? (View) obj2 : null);
            BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
            View batteryMeterView$1 = getBatteryMeterView$1();
            companion.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView$1);
            Rect rect = this.chipBounds;
            float f = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(samsungBatteryStatusChip.getBackgroundAnimator(true), samsungBatteryStatusChip.getContentAnimator(true, bounds, rect, f));
            animatorSet.playTogether(animatorSet2);
            return animatorSet;
        }
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion companion2 = AnimationUtil.Companion;
        ofFloat.setStartDelay(companion2.getFrames(7));
        ofFloat.setDuration(companion2.getFrames(5));
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationBegin$alphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                Object obj3 = SystemEventChipAnimationController.this.currentAnimatedView;
                View view2 = obj3 != null ? (View) obj3 : null;
                if (view2 == null) {
                    return;
                }
                view2.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
        View contentView = backgroundAnimatableView != null ? backgroundAnimatableView.getContentView() : null;
        if (contentView != null) {
            contentView.setAlpha(0.0f);
        }
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setStartDelay(companion2.getFrames(10));
        ofFloat2.setDuration(companion2.getFrames(10));
        ofFloat2.setInterpolator(null);
        ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationBegin$contentAlphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                BackgroundAnimatableView backgroundAnimatableView2 = SystemEventChipAnimationController.this.currentAnimatedView;
                View contentView2 = backgroundAnimatableView2 != null ? backgroundAnimatableView2.getContentView() : null;
                if (contentView2 == null) {
                    return;
                }
                contentView2.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
            }
        });
        final ValueAnimator ofInt = ValueAnimator.ofInt(this.chipMinWidth, this.chipBounds.width());
        ofInt.setStartDelay(companion2.getFrames(7));
        ofInt.setDuration(companion2.getFrames(23));
        ofInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
        ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationBegin$moveIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt.getAnimatedValue()).intValue());
            }
        });
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(ofFloat, ofFloat2, ofInt);
        return animatorSet3;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        AnimatorSet animatorSet;
        byte b = (!z3 || this.shadeExpansionStateManager.isClosed() || this.isChipAnimationStarted) ? false : true;
        if (b == true) {
            return new AnimatorSet();
        }
        if (b == false) {
            this.isChipAnimationStarted = false;
        }
        this.animRect.set(this.chipBounds);
        if (z3) {
            animatorSet = new AnimatorSet();
            BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
            SamsungBatteryStatusChip samsungBatteryStatusChip = (SamsungBatteryStatusChip) (backgroundAnimatableView != null ? (View) backgroundAnimatableView : null);
            BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
            View batteryMeterView$1 = getBatteryMeterView$1();
            companion.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView$1);
            Rect rect = this.chipBounds;
            float f = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(samsungBatteryStatusChip.getBackgroundAnimator(false), samsungBatteryStatusChip.getContentAnimator(false, bounds, rect, f));
            animatorSet.playTogether(animatorSet2);
        } else {
            int i = this.chipMinWidth;
            if (z) {
                final ValueAnimator ofInt = ValueAnimator.ofInt(this.chipBounds.width(), i);
                AnimationUtil.Companion companion2 = AnimationUtil.Companion;
                ofInt.setDuration(companion2.getFrames(9));
                ofInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_1);
                ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$width1$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt.getAnimatedValue()).intValue());
                    }
                });
                final ValueAnimator ofInt2 = ValueAnimator.ofInt(i, this.dotSize);
                ofInt2.setStartDelay(companion2.getFrames(9));
                ofInt2.setDuration(companion2.getFrames(20));
                ofInt2.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_2);
                ofInt2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$width2$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt2.getAnimatedValue()).intValue());
                    }
                });
                int i2 = this.dotSize * 2;
                Rect rect2 = this.chipBounds;
                final int height = (rect2.height() / 2) + rect2.top;
                final ValueAnimator ofInt3 = ValueAnimator.ofInt(this.chipBounds.height(), i2);
                ofInt3.setStartDelay(companion2.getFrames(8));
                ofInt3.setDuration(companion2.getFrames(6));
                ofInt3.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_1);
                ofInt3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$height1$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController.access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController.this, ((Integer) ofInt3.getAnimatedValue()).intValue(), height);
                    }
                });
                final ValueAnimator ofInt4 = ValueAnimator.ofInt(i2, this.dotSize);
                ofInt4.setStartDelay(companion2.getFrames(14));
                ofInt4.setDuration(companion2.getFrames(15));
                ofInt4.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_2);
                ofInt4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$height2$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController.access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController.this, ((Integer) ofInt4.getAnimatedValue()).intValue(), height);
                    }
                });
                final ValueAnimator ofInt5 = ValueAnimator.ofInt(0, this.dotSize);
                ofInt5.setStartDelay(companion2.getFrames(3));
                ofInt5.setDuration(companion2.getFrames(11));
                PathInterpolator pathInterpolator = SystemStatusAnimationSchedulerKt.STATUS_CHIP_MOVE_TO_DOT;
                ofInt5.setInterpolator(pathInterpolator);
                ofInt5.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$moveOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                        int i3 = systemEventChipAnimationController.animationDirection;
                        ValueAnimator valueAnimator = ofInt5;
                        int intValue = i3 == 1 ? ((Integer) valueAnimator.getAnimatedValue()).intValue() : -((Integer) valueAnimator.getAnimatedValue()).intValue();
                        Object obj = systemEventChipAnimationController.currentAnimatedView;
                        View view = obj != null ? (View) obj : null;
                        if (view == null) {
                            return;
                        }
                        view.setTranslationX(intValue);
                    }
                });
                Rect rect3 = this.chipBounds;
                final ValueAnimator ofInt6 = ValueAnimator.ofInt(0, MathKt__MathJVMKt.roundToInt(((this.contentInsetsProvider.getStatusBarPaddingTop() + (this.animationWindowView != null ? r5 : null).getHeight()) / 2.0f) - ((rect3.top + rect3.bottom) / 2.0f)));
                ofInt6.setStartDelay(companion2.getFrames(3));
                ofInt6.setDuration(companion2.getFrames(26));
                ofInt6.setInterpolator(pathInterpolator);
                ofInt6.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$moveOutY$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int intValue = ((Integer) ofInt6.getAnimatedValue()).intValue();
                        Object obj = SystemEventChipAnimationController.this.currentAnimatedView;
                        View view = obj != null ? (View) obj : null;
                        if (view == null) {
                            return;
                        }
                        view.setTranslationY(intValue);
                    }
                });
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofInt, ofInt2, ofInt3, ofInt4, ofInt5, ofInt6);
            } else {
                final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                AnimationUtil.Companion companion3 = AnimationUtil.Companion;
                ofFloat.setStartDelay(companion3.getFrames(6));
                ofFloat.setDuration(companion3.getFrames(6));
                ofFloat.setInterpolator(null);
                ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$alphaOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        Object obj = SystemEventChipAnimationController.this.currentAnimatedView;
                        View view = obj != null ? (View) obj : null;
                        if (view == null) {
                            return;
                        }
                        view.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                    }
                });
                final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
                ofFloat2.setDuration(companion3.getFrames(5));
                ofFloat2.setInterpolator(null);
                ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$contentAlphaOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        BackgroundAnimatableView backgroundAnimatableView2 = SystemEventChipAnimationController.this.currentAnimatedView;
                        View contentView = backgroundAnimatableView2 != null ? backgroundAnimatableView2.getContentView() : null;
                        if (contentView == null) {
                            return;
                        }
                        contentView.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
                    }
                });
                final ValueAnimator ofInt7 = ValueAnimator.ofInt(this.chipBounds.width(), i);
                ofInt7.setDuration(companion3.getFrames(23));
                ofInt7.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
                ofInt7.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$moveOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                        if (systemEventChipAnimationController.currentAnimatedView != null) {
                            SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(systemEventChipAnimationController, ((Integer) ofInt7.getAnimatedValue()).intValue());
                        }
                    }
                });
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(ofFloat, ofFloat2, ofInt7);
                animatorSet = animatorSet3;
            }
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationFinish$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                FrameLayout frameLayout = systemEventChipAnimationController.animationWindowView;
                if (frameLayout == null) {
                    frameLayout = null;
                }
                Object obj = systemEventChipAnimationController.currentAnimatedView;
                Intrinsics.checkNotNull(obj);
                frameLayout.removeView((View) obj);
                systemEventChipAnimationController.privacyLogger.logChipCreateRemove(systemEventChipAnimationController.chipBounds.width(), false);
            }
        });
        return animatorSet;
    }

    public final void prepareChipAnimation(Function1 function1, boolean z) {
        int calculateRightPadding;
        int i;
        FrameLayout.LayoutParams layoutParams;
        int i2;
        int i3;
        FrameLayout frameLayout = this.animationWindowView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        this.animationDirection = frameLayout.isLayoutRtl() ? 2 : 1;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.contentInsetsProvider;
        statusBarContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        float f = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
        this.dotMarginStart = MathKt__MathJVMKt.roundToInt(this.context.getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start) * f);
        this.dotSize = MathKt__MathJVMKt.roundToInt(this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) * f);
        Object obj = this.currentAnimatedView;
        if (obj != null) {
            Log.d("SystemEventChipAnimationController", "Try to remove existing animationView=" + obj);
            FrameLayout frameLayout2 = this.animationWindowView;
            if (frameLayout2 == null) {
                frameLayout2 = null;
            }
            frameLayout2.removeView((View) obj);
        }
        ContextThemeWrapper contextThemeWrapper = this.themedContext;
        if (contextThemeWrapper == null) {
            contextThemeWrapper = null;
        }
        BackgroundAnimatableView backgroundAnimatableView = (BackgroundAnimatableView) function1.invoke(contextThemeWrapper);
        FrameLayout frameLayout3 = this.animationWindowView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        backgroundAnimatableView.getClass();
        View view = (View) backgroundAnimatableView;
        if (z) {
            FrameLayout frameLayout4 = this.animationWindowView;
            if (frameLayout4 == null) {
                frameLayout4 = null;
            }
            if (frameLayout4.isLayoutRtl()) {
                BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
                View batteryMeterView$1 = getBatteryMeterView$1();
                companion.getClass();
                i2 = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView$1).left;
                FrameLayout frameLayout5 = this.animationWindowView;
                if (frameLayout5 == null) {
                    frameLayout5 = null;
                }
                i3 = BatteryChipAnimationUtils.Companion.getBounds(frameLayout5).left;
            } else {
                BatteryChipAnimationUtils.Companion companion2 = BatteryChipAnimationUtils.Companion;
                FrameLayout frameLayout6 = this.animationWindowView;
                if (frameLayout6 == null) {
                    frameLayout6 = null;
                }
                companion2.getClass();
                i2 = BatteryChipAnimationUtils.Companion.getBounds(frameLayout6).right;
                i3 = BatteryChipAnimationUtils.Companion.getBounds(getBatteryMeterView$1()).right;
            }
            int i4 = i2 - i3;
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 8388629;
            BatteryChipAnimationUtils.Companion companion3 = BatteryChipAnimationUtils.Companion;
            View batteryMeterView$12 = getBatteryMeterView$1();
            companion3.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView$12);
            int i5 = (bounds.top + bounds.bottom) / 2;
            FrameLayout frameLayout7 = this.animationWindowView;
            if (frameLayout7 == null) {
                frameLayout7 = null;
            }
            Rect bounds2 = BatteryChipAnimationUtils.Companion.getBounds(frameLayout7);
            layoutParams.topMargin = i5 - ((bounds2.top + bounds2.bottom) / 2);
            layoutParams.setMarginEnd(i4);
        } else {
            FrameLayout frameLayout8 = this.animationWindowView;
            if (frameLayout8 == null) {
                frameLayout8 = null;
            }
            boolean isLayoutRtl = frameLayout8.isLayoutRtl();
            IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
            if (isLayoutRtl) {
                calculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding();
                i = this.dotMarginStart;
            } else {
                calculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
                i = this.dotMarginStart;
            }
            int i6 = calculateRightPadding - i;
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 8388629;
            layoutParams.setMarginEnd(i6);
        }
        frameLayout3.addView(view, layoutParams);
        view.setAlpha(0.0f);
        FrameLayout frameLayout9 = this.animationWindowView;
        if (frameLayout9 == null) {
            frameLayout9 = null;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) frameLayout9.getParent()).getWidth(), Integer.MIN_VALUE);
        FrameLayout frameLayout10 = this.animationWindowView;
        view.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(((View) (frameLayout10 != null ? frameLayout10 : null).getParent()).getHeight(), Integer.MIN_VALUE));
        updateChipBounds(backgroundAnimatableView, statusBarContentInsetsProvider.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProvider.context)));
        view.setPivotX(this.animationDirection != 2 ? view.getMeasuredWidth() : 0.0f);
        view.setPivotY(view.getMeasuredHeight() / 2.0f);
        view.setScaleX(f);
        view.setScaleY(f);
        this.currentAnimatedView = backgroundAnimatableView;
        this.privacyLogger.logChipCreateRemove(this.chipBounds.width(), true);
    }

    public final void updateChipBounds(BackgroundAnimatableView backgroundAnimatableView, Rect rect) {
        int measuredWidth;
        int i;
        int measuredWidth2;
        int i2;
        if (backgroundAnimatableView instanceof SamsungBatteryStatusChip) {
            BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
            View batteryMeterView$1 = getBatteryMeterView$1();
            companion.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView$1);
            int i3 = (bounds.top + bounds.bottom) / 2;
            backgroundAnimatableView.getClass();
            View view = (View) backgroundAnimatableView;
            int measuredHeight = i3 - (view.getMeasuredHeight() / 2);
            int measuredHeight2 = view.getMeasuredHeight() + measuredHeight;
            if (this.animationDirection == 1) {
                measuredWidth2 = BatteryChipAnimationUtils.Companion.getBounds(getBatteryMeterView$1()).right;
                i2 = measuredWidth2 - view.getMeasuredWidth();
            } else {
                int i4 = BatteryChipAnimationUtils.Companion.getBounds(getBatteryMeterView$1()).left;
                measuredWidth2 = view.getMeasuredWidth() + i4;
                i2 = i4;
            }
            this.chipBounds = new Rect(i2, measuredHeight, measuredWidth2, measuredHeight2);
        } else {
            int i5 = rect.top;
            int height = rect.height();
            backgroundAnimatableView.getClass();
            View view2 = (View) backgroundAnimatableView;
            int measuredHeight3 = ((height - view2.getMeasuredHeight()) / 2) + i5;
            int measuredHeight4 = view2.getMeasuredHeight() + measuredHeight3;
            int i6 = this.animationDirection;
            IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
            if (i6 == 1) {
                FrameLayout frameLayout = this.animationWindowView;
                if (frameLayout == null) {
                    frameLayout = null;
                }
                measuredWidth = (frameLayout.getWidth() - indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding()) + this.dotMarginStart;
                i = measuredWidth - view2.getMeasuredWidth();
            } else {
                int calculateLeftPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding() - this.dotMarginStart;
                measuredWidth = view2.getMeasuredWidth() + calculateLeftPadding;
                i = calculateLeftPadding;
            }
            this.chipBounds = new Rect(i, measuredHeight3, measuredWidth, measuredHeight4);
        }
        this.animRect.set(this.chipBounds);
    }

    public final void updateCurrentAnimatedView() {
        BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
        if (backgroundAnimatableView != null) {
            Rect rect = this.animRect;
            backgroundAnimatableView.setBoundsForAnimation(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public static /* synthetic */ void getChipBounds$annotations() {
    }

    public static /* synthetic */ void getInitialized$annotations() {
    }
}
