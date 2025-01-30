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
import androidx.core.animation.ValueAnimator;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.leak.RotationUtils;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemEventChipAnimationController implements SystemStatusAnimationCallback {
    public FrameLayout animationWindowView;
    public int bottomGapBetweenDotAndChip;
    public final int chipMinWidth;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public final Context context;
    public BackgroundAnimatableView currentAnimatedView;
    public int dotSize;
    public final FeatureFlags featureFlags;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final PrivacyLogger privacyLogger;
    public ContextThemeWrapper themedContext;
    public int animationDirection = 1;
    public Rect chipBounds = new Rect();
    public final Rect animRect = new Rect();

    public SystemEventChipAnimationController(Context context, StatusBarWindowController statusBarWindowController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, FeatureFlags featureFlags, PrivacyLogger privacyLogger, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.featureFlags = featureFlags;
        this.privacyLogger = privacyLogger;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.chipMinWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_min_animation_width);
        this.dotSize = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 2132018541);
        this.themedContext = contextThemeWrapper;
        this.animationWindowView = (FrameLayout) LayoutInflater.from(contextThemeWrapper).inflate(R.layout.system_event_animation_window, (ViewGroup) null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 8388629;
        FrameLayout frameLayout = this.animationWindowView;
        statusBarWindowController.mStatusBarWindowView.addView(frameLayout == null ? null : frameLayout, layoutParams);
        FrameLayout frameLayout2 = this.animationWindowView;
        (frameLayout2 == null ? null : frameLayout2).setClipToPadding(false);
        FrameLayout frameLayout3 = this.animationWindowView;
        (frameLayout3 != null ? frameLayout3 : null).setClipChildren(false);
    }

    public static final void access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController systemEventChipAnimationController, int i, int i2, float f) {
        Rect rect = systemEventChipAnimationController.animRect;
        try {
            float f2 = i;
            rect.set(rect.left, i2 - MathKt__MathJVMKt.roundToInt((1 - f) * f2), rect.right, MathKt__MathJVMKt.roundToInt(f2 * f) + i2);
        } catch (IllegalArgumentException unused) {
            float f3 = i / 2;
            rect.set(rect.left, i2 - MathKt__MathJVMKt.roundToInt(f3), rect.right, MathKt__MathJVMKt.roundToInt(f3) + i2);
        }
        BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationController.currentAnimatedView;
        if (backgroundAnimatableView != null) {
            Rect rect2 = systemEventChipAnimationController.animRect;
            backgroundAnimatableView.setBoundsForAnimation(rect2.left, rect2.top, rect2.right, rect2.bottom);
        }
    }

    public static final void access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController systemEventChipAnimationController, int i) {
        int i2 = systemEventChipAnimationController.animationDirection;
        Rect rect = systemEventChipAnimationController.animRect;
        if (i2 == 1) {
            int i3 = systemEventChipAnimationController.chipBounds.right;
            rect.set(i3 - i, rect.top, i3, rect.bottom);
        } else {
            int i4 = systemEventChipAnimationController.chipBounds.left;
            rect.set(i4, rect.top, i + i4, rect.bottom);
        }
        BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationController.currentAnimatedView;
        if (backgroundAnimatableView != null) {
            backgroundAnimatableView.setBoundsForAnimation(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z) {
        Flags.INSTANCE.getClass();
        ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.PLUG_IN_STATUS_BAR_CHIP);
        this.animRect.set(this.chipBounds);
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        AnimationUtil.Companion.getClass();
        ofFloat.setStartDelay(AnimationUtil.Companion.getFrames(7));
        ofFloat.setDuration(AnimationUtil.Companion.getFrames(5));
        ofFloat.setInterpolator(null);
        ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationBegin$alphaIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                BackgroundAnimatableView backgroundAnimatableView = SystemEventChipAnimationController.this.currentAnimatedView;
                View view = backgroundAnimatableView != null ? backgroundAnimatableView.getView() : null;
                if (view == null) {
                    return;
                }
                view.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
        View contentView = backgroundAnimatableView != null ? backgroundAnimatableView.getContentView() : null;
        if (contentView != null) {
            contentView.setAlpha(0.0f);
        }
        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setStartDelay(AnimationUtil.Companion.getFrames(10));
        ofFloat2.setDuration(AnimationUtil.Companion.getFrames(10));
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
        ofInt.setStartDelay(AnimationUtil.Companion.getFrames(7));
        ofInt.setDuration(AnimationUtil.Companion.getFrames(23));
        ofInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
        ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationBegin$moveIn$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt.getAnimatedValue()).intValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofInt);
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
        AnimatorSet animatorSet;
        Flags.INSTANCE.getClass();
        ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.PLUG_IN_STATUS_BAR_CHIP);
        this.animRect.set(this.chipBounds);
        int i = this.chipMinWidth;
        if (z) {
            final ValueAnimator ofInt = ValueAnimator.ofInt(this.chipBounds.width(), i);
            AnimationUtil.Companion.getClass();
            ofInt.setDuration(AnimationUtil.Companion.getFrames(9));
            ofInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_1);
            ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$width1$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt.getAnimatedValue()).intValue());
                }
            });
            final ValueAnimator ofInt2 = ValueAnimator.ofInt(i, this.dotSize);
            ofInt2.setStartDelay(AnimationUtil.Companion.getFrames(9));
            ofInt2.setDuration(AnimationUtil.Companion.getFrames(20));
            ofInt2.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_2);
            ofInt2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$width2$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationController.this, ((Integer) ofInt2.getAnimatedValue()).intValue());
                }
            });
            int i2 = this.dotSize * 2;
            BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
            Intrinsics.checkNotNull(backgroundAnimatableView);
            final int bottom = backgroundAnimatableView.getView().getBottom() - this.bottomGapBetweenDotAndChip;
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            float measuredHeight = this.bottomGapBetweenDotAndChip / r1.getMeasuredHeight();
            ref$FloatRef.element = measuredHeight;
            if (Float.isNaN(measuredHeight)) {
                ref$FloatRef.element = 0.0f;
            }
            BackgroundAnimatableView backgroundAnimatableView2 = this.currentAnimatedView;
            Intrinsics.checkNotNull(backgroundAnimatableView2);
            final ValueAnimator ofInt3 = ValueAnimator.ofInt(backgroundAnimatableView2.getView().getMeasuredHeight(), i2);
            ofInt3.setStartDelay(AnimationUtil.Companion.getFrames(8));
            ofInt3.setDuration(AnimationUtil.Companion.getFrames(6));
            ofInt3.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_1);
            ofInt3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$height1$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController.access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController.this, ((Integer) ofInt3.getAnimatedValue()).intValue(), bottom, ref$FloatRef.element);
                }
            });
            final ValueAnimator ofInt4 = ValueAnimator.ofInt(i2, this.dotSize);
            ofInt4.setStartDelay(AnimationUtil.Companion.getFrames(14));
            ofInt4.setDuration(AnimationUtil.Companion.getFrames(15));
            ofInt4.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_2);
            ofInt4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$height2$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController.access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationController.this, ((Integer) ofInt4.getAnimatedValue()).intValue(), bottom, ref$FloatRef.element);
                }
            });
            final ValueAnimator ofInt5 = ValueAnimator.ofInt(0, this.dotSize);
            ofInt5.setStartDelay(AnimationUtil.Companion.getFrames(3));
            ofInt5.setDuration(AnimationUtil.Companion.getFrames(11));
            ofInt5.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_MOVE_TO_DOT);
            ofInt5.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationForDot$moveOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                    int i3 = systemEventChipAnimationController.animationDirection;
                    ValueAnimator valueAnimator = ofInt5;
                    int intValue = i3 == 1 ? ((Integer) valueAnimator.getAnimatedValue()).intValue() : -((Integer) valueAnimator.getAnimatedValue()).intValue();
                    BackgroundAnimatableView backgroundAnimatableView3 = systemEventChipAnimationController.currentAnimatedView;
                    View view = backgroundAnimatableView3 != null ? backgroundAnimatableView3.getView() : null;
                    if (view == null) {
                        return;
                    }
                    view.setTranslationX(intValue);
                }
            });
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofInt, ofInt2, ofInt3, ofInt4, ofInt5);
        } else {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            AnimationUtil.Companion.getClass();
            ofFloat.setStartDelay(AnimationUtil.Companion.getFrames(6));
            ofFloat.setDuration(AnimationUtil.Companion.getFrames(6));
            ofFloat.setInterpolator(null);
            ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$alphaOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    BackgroundAnimatableView backgroundAnimatableView3 = SystemEventChipAnimationController.this.currentAnimatedView;
                    View view = backgroundAnimatableView3 != null ? backgroundAnimatableView3.getView() : null;
                    if (view == null) {
                        return;
                    }
                    view.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat2.setDuration(AnimationUtil.Companion.getFrames(5));
            ofFloat2.setInterpolator(null);
            ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$contentAlphaOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    BackgroundAnimatableView backgroundAnimatableView3 = SystemEventChipAnimationController.this.currentAnimatedView;
                    View contentView = backgroundAnimatableView3 != null ? backgroundAnimatableView3.getContentView() : null;
                    if (contentView == null) {
                        return;
                    }
                    contentView.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator ofInt6 = ValueAnimator.ofInt(this.chipBounds.width(), i);
            ofInt6.setDuration(AnimationUtil.Companion.getFrames(23));
            ofInt6.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
            ofInt6.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$createMoveOutAnimationDefault$moveOut$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                    if (systemEventChipAnimationController.currentAnimatedView != null) {
                        SystemEventChipAnimationController.access$updateAnimatedViewBoundsWidth(systemEventChipAnimationController, ((Integer) ofInt6.getAnimatedValue()).intValue());
                    }
                }
            });
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofFloat2, ofInt6);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationController$onSystemEventAnimationFinish$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd$1(Animator animator) {
                SystemEventChipAnimationController systemEventChipAnimationController = SystemEventChipAnimationController.this;
                FrameLayout frameLayout = systemEventChipAnimationController.animationWindowView;
                if (frameLayout == null) {
                    frameLayout = null;
                }
                BackgroundAnimatableView backgroundAnimatableView3 = systemEventChipAnimationController.currentAnimatedView;
                Intrinsics.checkNotNull(backgroundAnimatableView3);
                frameLayout.removeView(backgroundAnimatableView3.getView());
                systemEventChipAnimationController.privacyLogger.logChipCreateRemove(systemEventChipAnimationController.chipBounds.width(), false);
            }
        });
        return animatorSet;
    }

    public final void prepareChipAnimation(Function1 function1) {
        int calculateLeftPadding;
        int chipWidth;
        FrameLayout frameLayout = this.animationWindowView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        this.animationDirection = frameLayout.isLayoutRtl() ? 2 : 1;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.contentInsetsProvider;
        statusBarContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        Context context = this.context;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start);
        float f = this.indicatorScaleGardener.getLatestScaleModel(context).ratio;
        int roundToInt = MathKt__MathJVMKt.roundToInt(dimensionPixelSize * f);
        this.dotSize = MathKt__MathJVMKt.roundToInt(context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) * f);
        BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
        if (backgroundAnimatableView != null) {
            Log.d("SystemEventChipAnimationController", "Try to remove existing animationView=" + backgroundAnimatableView);
            FrameLayout frameLayout2 = this.animationWindowView;
            if (frameLayout2 == null) {
                frameLayout2 = null;
            }
            frameLayout2.removeView(backgroundAnimatableView.getView());
        }
        ContextThemeWrapper contextThemeWrapper = this.themedContext;
        if (contextThemeWrapper == null) {
            contextThemeWrapper = null;
        }
        BackgroundAnimatableView backgroundAnimatableView2 = (BackgroundAnimatableView) function1.invoke(contextThemeWrapper);
        FrameLayout frameLayout3 = this.animationWindowView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        View view = backgroundAnimatableView2.getView();
        FrameLayout frameLayout4 = this.animationWindowView;
        if (frameLayout4 == null) {
            frameLayout4 = null;
        }
        boolean isLayoutRtl = frameLayout4.isLayoutRtl();
        IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
        int calculateLeftPadding2 = isLayoutRtl ? indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding() : indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 8388629;
        layoutParams.setMarginEnd(calculateLeftPadding2 - roundToInt);
        frameLayout3.addView(view, layoutParams);
        backgroundAnimatableView2.getView().setAlpha(0.0f);
        View view2 = backgroundAnimatableView2.getView();
        FrameLayout frameLayout5 = this.animationWindowView;
        if (frameLayout5 == null) {
            frameLayout5 = null;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) frameLayout5.getParent()).getWidth(), VideoPlayer.MEDIA_ERROR_SYSTEM);
        FrameLayout frameLayout6 = this.animationWindowView;
        if (frameLayout6 == null) {
            frameLayout6 = null;
        }
        view2.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(((View) frameLayout6.getParent()).getHeight(), VideoPlayer.MEDIA_ERROR_SYSTEM));
        statusBarContentInsetsProvider.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProvider.context));
        FrameLayout frameLayout7 = this.animationWindowView;
        if (frameLayout7 == null) {
            frameLayout7 = null;
        }
        int height = (((View) frameLayout7.getParent()).getHeight() - backgroundAnimatableView2.getView().getMeasuredHeight()) / 2;
        int measuredHeight = backgroundAnimatableView2.getView().getMeasuredHeight() + height;
        if (this.animationDirection == 1) {
            FrameLayout frameLayout8 = this.animationWindowView;
            if (frameLayout8 == null) {
                frameLayout8 = null;
            }
            chipWidth = (frameLayout8.getWidth() - indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding()) + roundToInt;
            calculateLeftPadding = chipWidth - backgroundAnimatableView2.getChipWidth();
        } else {
            calculateLeftPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding() - roundToInt;
            chipWidth = backgroundAnimatableView2.getChipWidth() + calculateLeftPadding;
        }
        this.chipBounds = new Rect(calculateLeftPadding, height, chipWidth, measuredHeight);
        View view3 = backgroundAnimatableView2.getView();
        view3.setPivotX(this.animationDirection != 2 ? backgroundAnimatableView2.getChipWidth() : 0.0f);
        view3.setPivotY(backgroundAnimatableView2.getView().getMeasuredHeight() / 2.0f);
        view3.setScaleX(f);
        view3.setScaleY(f);
        this.currentAnimatedView = backgroundAnimatableView2;
        FrameLayout frameLayout9 = this.animationWindowView;
        if (frameLayout9 == null) {
            frameLayout9 = null;
        }
        float bottom = ((frameLayout9.getBottom() - (this.dotSize / 2.0f)) - statusBarContentInsetsProvider.getStatusBarPaddingTop()) / 2.0f;
        FrameLayout frameLayout10 = this.animationWindowView;
        float bottom2 = (frameLayout10 != null ? frameLayout10 : null).getBottom();
        Intrinsics.checkNotNull(this.currentAnimatedView);
        this.bottomGapBetweenDotAndChip = MathKt__MathJVMKt.roundToInt(bottom - ((bottom2 - (r1.getView().getMeasuredHeight() * f)) / 2.0f));
        this.privacyLogger.logChipCreateRemove(this.chipBounds.width(), true);
    }
}
