package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.ValueAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.statusbar.events.BatteryChipAnimationUtils;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.IndicatorGardenModel;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Locale;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemEventChipAnimationControllerImpl extends KeyguardUpdateMonitorCallback implements SystemEventChipAnimationController {
    public final FrameLayout animationWindowView;
    public int animationWindowViewHeight;
    public final int chipMinWidth;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public final Context context;
    public BackgroundAnimatableView currentAnimatedView;
    public int dotMarginStart;
    public int dotSize;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isChipAnimationStarted;
    public final KeyguardStateController keyguardStateController;
    public final PrivacyLogger privacyLogger;
    private final SettingsHelper settingsHelper;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1 statusBarContentInsetsChangedListener;
    public final ContextThemeWrapper themedContext;
    public int animationDirection = 1;
    public Rect chipBounds = new Rect();
    public final Rect animRect = new Rect();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SystemEventChipAnimationControllerImpl create(Context context, StatusBarWindowController statusBarWindowController, StatusBarContentInsetsProvider statusBarContentInsetsProvider);
    }

    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1] */
    public SystemEventChipAnimationControllerImpl(Context context, StatusBarWindowController statusBarWindowController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, ShadeExpansionStateManager shadeExpansionStateManager, PrivacyLogger privacyLogger, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, SettingsHelper settingsHelper, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        this.context = context;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.privacyLogger = privacyLogger;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.settingsHelper = settingsHelper;
        this.keyguardStateController = keyguardStateController;
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
        ((StatusBarWindowControllerImpl) statusBarWindowController).mStatusBarWindowView.addView(frameLayout == null ? null : frameLayout, layoutParams);
        FrameLayout frameLayout2 = this.animationWindowView;
        (frameLayout2 == null ? null : frameLayout2).setClipToPadding(false);
        FrameLayout frameLayout3 = this.animationWindowView;
        (frameLayout3 == null ? null : frameLayout3).setClipChildren(false);
        indicatorGardenPresenter.addCallback(new IndicatorGardenPresenter.GardenListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$init$1
            @Override // com.android.systemui.statusbar.phone.IndicatorGardenPresenter.GardenListener
            public final void onGardenChanged(IndicatorGardenModel indicatorGardenModel) {
                SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                FrameLayout frameLayout4 = systemEventChipAnimationControllerImpl.animationWindowView;
                if (frameLayout4 == null) {
                    frameLayout4 = null;
                }
                if (frameLayout4.getLayoutParams().height != indicatorGardenModel.totalHeight) {
                    SystemEventChipAnimationControllerImpl.access$updateDimens(systemEventChipAnimationControllerImpl, new Rect(0, 0, 0, indicatorGardenModel.totalHeight));
                }
            }
        });
        FrameLayout frameLayout4 = this.animationWindowView;
        (frameLayout4 != null ? frameLayout4 : null).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$init$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                int calculateRightPadding;
                int i9;
                if (i == i5 && i3 == i7) {
                    return;
                }
                StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) SystemEventChipAnimationControllerImpl.this.contentInsetsProvider;
                Rect statusBarContentAreaForRotation = statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context));
                final SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationControllerImpl.currentAnimatedView;
                if (backgroundAnimatableView != 0) {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) ((View) backgroundAnimatableView).getLayoutParams();
                    FrameLayout frameLayout5 = systemEventChipAnimationControllerImpl.animationWindowView;
                    if (frameLayout5 == null) {
                        frameLayout5 = null;
                    }
                    boolean isLayoutRtl = frameLayout5.isLayoutRtl();
                    IndicatorGardenPresenter indicatorGardenPresenter2 = systemEventChipAnimationControllerImpl.indicatorGardenPresenter;
                    if (isLayoutRtl) {
                        calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding();
                        i9 = systemEventChipAnimationControllerImpl.dotMarginStart;
                    } else {
                        calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding();
                        i9 = systemEventChipAnimationControllerImpl.dotMarginStart;
                    }
                    layoutParams2.setMarginEnd(calculateRightPadding - i9);
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    obj.getClass();
                    ((View) obj).setLayoutParams(layoutParams2);
                    systemEventChipAnimationControllerImpl.updateChipBounds(backgroundAnimatableView, statusBarContentAreaForRotation);
                    ValueAnimator ofInt = ValueAnimator.ofInt(0, 1);
                    ofInt.setDuration(0L);
                    ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$init$2$1$1
                        @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                        public final void onAnimationUpdate(Animator animator) {
                            SystemEventChipAnimationControllerImpl.this.updateCurrentAnimatedView();
                        }
                    });
                    ofInt.start(false);
                }
            }
        });
        keyguardUpdateMonitor.registerCallback(this);
        if (DeviceType.isTablet()) {
            secPanelExpansionStateInteractor.registerListener(new SecPanelExpansionStateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl.1
                @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
                public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                    SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    if (obj == null || !((View) obj).isAttachedToWindow()) {
                        return;
                    }
                    if (secPanelExpansionStateChangeEvent.panelExpansionState != 0) {
                        Object obj2 = systemEventChipAnimationControllerImpl.currentAnimatedView;
                        if (obj2 != null) {
                            ((View) obj2).setVisibility(8);
                            return;
                        }
                        return;
                    }
                    Object obj3 = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    if (obj3 != null) {
                        ((View) obj3).setVisibility(0);
                    }
                }
            });
        }
        this.statusBarContentInsetsChangedListener = new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                int calculateRightPadding;
                int i;
                final SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) systemEventChipAnimationControllerImpl.contentInsetsProvider;
                Rect statusBarContentAreaForRotation = statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context));
                SystemEventChipAnimationControllerImpl.access$updateDimens(systemEventChipAnimationControllerImpl, statusBarContentAreaForRotation);
                BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationControllerImpl.currentAnimatedView;
                if (backgroundAnimatableView != 0) {
                    View view = (View) backgroundAnimatableView;
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.getLayoutParams();
                    if (backgroundAnimatableView instanceof SamsungBatteryStatusChip) {
                        BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
                        View batteryMeterView = systemEventChipAnimationControllerImpl.getBatteryMeterView();
                        companion.getClass();
                        Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView);
                        layoutParams2.topMargin = ((bounds.top + bounds.bottom) / 2) - (view.getMeasuredHeight() / 2);
                    } else {
                        FrameLayout frameLayout5 = systemEventChipAnimationControllerImpl.animationWindowView;
                        if (frameLayout5 == null) {
                            frameLayout5 = null;
                        }
                        boolean isLayoutRtl = frameLayout5.isLayoutRtl();
                        IndicatorGardenPresenter indicatorGardenPresenter2 = systemEventChipAnimationControllerImpl.indicatorGardenPresenter;
                        if (isLayoutRtl) {
                            calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding();
                            i = systemEventChipAnimationControllerImpl.dotMarginStart;
                        } else {
                            calculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding();
                            i = systemEventChipAnimationControllerImpl.dotMarginStart;
                        }
                        layoutParams2.setMarginEnd(calculateRightPadding - i);
                    }
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    obj.getClass();
                    ((View) obj).setLayoutParams(layoutParams2);
                    systemEventChipAnimationControllerImpl.updateChipBounds(backgroundAnimatableView, statusBarContentAreaForRotation);
                    ValueAnimator ofInt = ValueAnimator.ofInt(0, 1);
                    ofInt.setDuration(0L);
                    ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1$onStatusBarContentInsetsChanged$1$1
                        @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                        public final void onAnimationUpdate(Animator animator) {
                            SystemEventChipAnimationControllerImpl.this.updateCurrentAnimatedView();
                        }
                    });
                    ofInt.start(false);
                }
            }
        };
    }

    public static final void access$updateAnimatedViewBoundsHeight(SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl, int i, int i2) {
        Rect rect = systemEventChipAnimationControllerImpl.animRect;
        float f = i / 2;
        rect.set(rect.left, i2 - MathKt__MathJVMKt.roundToInt(f), systemEventChipAnimationControllerImpl.animRect.right, MathKt__MathJVMKt.roundToInt(f) + i2);
        systemEventChipAnimationControllerImpl.updateCurrentAnimatedView();
    }

    public static final void access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl, int i) {
        if (systemEventChipAnimationControllerImpl.animationDirection == 1) {
            Rect rect = systemEventChipAnimationControllerImpl.animRect;
            int i2 = systemEventChipAnimationControllerImpl.chipBounds.right;
            rect.set(i2 - i, rect.top, i2, rect.bottom);
        } else {
            Rect rect2 = systemEventChipAnimationControllerImpl.animRect;
            int i3 = systemEventChipAnimationControllerImpl.chipBounds.left;
            rect2.set(i3, rect2.top, i + i3, rect2.bottom);
        }
        systemEventChipAnimationControllerImpl.updateCurrentAnimatedView();
    }

    public static final void access$updateDimens(SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl, Rect rect) {
        FrameLayout frameLayout = systemEventChipAnimationControllerImpl.animationWindowView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = rect.height();
        systemEventChipAnimationControllerImpl.animationWindowViewHeight = rect.height();
        FrameLayout frameLayout2 = systemEventChipAnimationControllerImpl.animationWindowView;
        (frameLayout2 != null ? frameLayout2 : null).setLayoutParams(layoutParams);
    }

    public final Rect getBatteryBounds() {
        View batteryMeterView = getBatteryMeterView();
        BatteryChipAnimationUtils.Companion.getClass();
        Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView);
        return new Rect(batteryMeterView.getPaddingLeft() + bounds.left, batteryMeterView.getPaddingTop() + bounds.top, bounds.right - batteryMeterView.getPaddingRight(), bounds.bottom - batteryMeterView.getPaddingBottom());
    }

    public final View getBatteryMeterView() {
        FrameLayout frameLayout = this.animationWindowView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        return frameLayout.getRootView().requireViewById(R.id.battery);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardVisibilityChanged(boolean z) {
        Object obj = this.currentAnimatedView;
        if (obj == null || !((View) obj).isAttachedToWindow()) {
            return;
        }
        Object obj2 = this.currentAnimatedView;
        if (obj2 != null) {
            ((View) obj2).setVisibility(!z ? 0 : 8);
        }
        PrivacyLogger privacyLogger = this.privacyLogger;
        privacyLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(12);
        LogBuffer logBuffer = privacyLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).bool1 = !z;
        logBuffer.commit(obtain);
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
        View contentView;
        this.animRect.set(this.chipBounds);
        if (!z2) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            AnimationUtil.Companion companion = AnimationUtil.Companion;
            ofFloat.setStartDelay(companion.getFrames(7));
            ofFloat.setDuration(companion.getFrames(5));
            ofFloat.setInterpolator(null);
            ofFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$alphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    Object obj = SystemEventChipAnimationControllerImpl.this.currentAnimatedView;
                    if (obj != null) {
                        ((View) obj).setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                    }
                }
            });
            BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
            if (backgroundAnimatableView != null && (contentView = backgroundAnimatableView.getContentView()) != null) {
                contentView.setAlpha(0.0f);
            }
            final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.setStartDelay(companion.getFrames(10));
            ofFloat2.setDuration(companion.getFrames(10));
            ofFloat2.setInterpolator(null);
            ofFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$contentAlphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    View contentView2;
                    BackgroundAnimatableView backgroundAnimatableView2 = SystemEventChipAnimationControllerImpl.this.currentAnimatedView;
                    if (backgroundAnimatableView2 == null || (contentView2 = backgroundAnimatableView2.getContentView()) == null) {
                        return;
                    }
                    contentView2.setAlpha(((Float) ofFloat2.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator ofInt = ValueAnimator.ofInt(this.chipMinWidth, this.chipBounds.width());
            ofInt.setStartDelay(companion.getFrames(7));
            ofInt.setDuration(companion.getFrames(23));
            ofInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
            ofInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$moveIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsWidth(SystemEventChipAnimationControllerImpl.this, ((Integer) ofInt.getAnimatedValue()).intValue());
                }
            });
            SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
            springAnimatorSet.playTogether(ofFloat, ofFloat2, ofInt);
            return springAnimatorSet;
        }
        boolean isClosed = this.shadeExpansionStateManager.isClosed();
        HeadsUpAppearanceController headsUpAppearanceController = this.indicatorGardenPresenter.headsUpAppearanceController;
        if (headsUpAppearanceController != null && this.settingsHelper.isPopStyleDetail() && headsUpAppearanceController.shouldHeadsUpStatusBarBeVisible()) {
            isClosed = true;
        }
        if (!isClosed) {
            return new SpringAnimatorSet();
        }
        this.isChipAnimationStarted = true;
        SpringAnimatorSet springAnimatorSet2 = new SpringAnimatorSet();
        Object obj = this.currentAnimatedView;
        if (obj != null) {
            ((View) obj).setAlpha(1.0f);
        }
        Object obj2 = this.currentAnimatedView;
        SamsungBatteryStatusChip samsungBatteryStatusChip = (SamsungBatteryStatusChip) (obj2 != null ? (View) obj2 : null);
        int i = this.animationWindowViewHeight;
        float dimension = samsungBatteryStatusChip.getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_height);
        if (samsungBatteryStatusChip.getContext().getResources().getBoolean(R.bool.config_enableSmallChargingVI)) {
            float f = i;
            if (dimension > f) {
                float f2 = f / dimension;
                ViewGroup.LayoutParams layoutParams = samsungBatteryStatusChip.batteryChipContainer.getLayoutParams();
                layoutParams.width = (int) (samsungBatteryStatusChip.getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_width) * f2);
                layoutParams.height = (int) (samsungBatteryStatusChip.getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_height) * f2);
                samsungBatteryStatusChip.batteryChipContainer.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams2 = samsungBatteryStatusChip.chargingIcon.getLayoutParams();
                layoutParams2.height = (int) (samsungBatteryStatusChip.getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_charging_icon_height) * f2);
                samsungBatteryStatusChip.chargingIcon.setLayoutParams(layoutParams2);
                BatteryStatusChipClearTextView batteryStatusChipClearTextView = samsungBatteryStatusChip.batteryLevelText;
                float dimension2 = samsungBatteryStatusChip.getContext().getResources().getDimension(R.dimen.status_bar_battery_chip_level_text_size) * f2;
                batteryStatusChipClearTextView.clearTextPaint.setTextSize(dimension2);
                batteryStatusChipClearTextView.textPaint.setTextSize(dimension2);
            }
        }
        Object obj3 = this.currentAnimatedView;
        ((SamsungBatteryStatusChip) (obj3 != null ? (View) obj3 : null)).playProgressLottieAnimation(true);
        Object obj4 = this.currentAnimatedView;
        final SamsungBatteryStatusChip samsungBatteryStatusChip2 = (SamsungBatteryStatusChip) (obj4 != null ? (View) obj4 : null);
        Rect batteryBounds = getBatteryBounds();
        float f3 = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
        samsungBatteryStatusChip2.getClass();
        SpringAnimatorSet springAnimatorSet3 = new SpringAnimatorSet();
        SpringAnimatorSet systemIconAnimator = SamsungBatteryStatusChip.getSystemIconAnimator(samsungBatteryStatusChip2.getRootView().requireViewById(R.id.statusIcons), true);
        int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        float measuredWidth = samsungBatteryStatusChip2.batteryChipContainer.getMeasuredWidth();
        BatteryChipAnimationUtils.Companion.getClass();
        if (layoutDirectionFromLocale == 0) {
            measuredWidth = -measuredWidth;
        }
        SpringAnimatorSet springAnimatorSet4 = new SpringAnimatorSet();
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(samsungBatteryStatusChip2.batteryLevelProgress, "translationX", measuredWidth, 0.0f);
        ofFloat3.m894setDuration(1000L);
        ofFloat3.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        Unit unit = Unit.INSTANCE;
        final ValueAnimator ofFloat4 = ValueAnimator.ofFloat(10.0f, 0.0f);
        ofFloat4.setDuration(1000L);
        ofFloat4.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        ofFloat4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingWaveAnimation$1$2$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                LottieAnimationView lottieAnimationView = SamsungBatteryStatusChip.this.batteryLevelProgress;
                BatteryChipConstants.INSTANCE.getClass();
                KeyPath keyPath = BatteryChipConstants.WAVE_KEY_PATH;
                Float f4 = LottieProperty.BLUR_RADIUS;
                final ValueAnimator valueAnimator = ofFloat4;
                lottieAnimationView.addValueCallback(keyPath, (KeyPath) f4, new SimpleLottieValueCallback() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingWaveAnimation$1$2$1.1
                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final Object getValue() {
                        return (Float) ValueAnimator.this.getAnimatedValue();
                    }
                });
            }
        });
        springAnimatorSet4.playTogether(ofFloat3, ofFloat4);
        springAnimatorSet3.playTogether(systemIconAnimator, springAnimatorSet4, samsungBatteryStatusChip2.getContainerBackgroundAnimator(true, batteryBounds, f3, samsungBatteryStatusChip2.isLayoutRtl(), z), samsungBatteryStatusChip2.getBatteryBackgroundAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()), samsungBatteryStatusChip2.getBatteryLevelTextAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()), samsungBatteryStatusChip2.getChargingIconAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()));
        springAnimatorSet2.playTogether(springAnimatorSet3);
        return springAnimatorSet2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.events.SpringAnimatorSet onSystemEventAnimationFinish(boolean r11, boolean r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 628
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl.onSystemEventAnimationFinish(boolean, boolean, boolean):com.android.systemui.statusbar.events.SpringAnimatorSet");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
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
        StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) this.contentInsetsProvider;
        statusBarContentInsetsProviderImpl.getStatusBarContentInsetsForCurrentRotation();
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
        BackgroundAnimatableView backgroundAnimatableView = (BackgroundAnimatableView) function1.mo779invoke(contextThemeWrapper);
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
                i2 = getBatteryBounds().left;
                BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
                FrameLayout frameLayout5 = this.animationWindowView;
                if (frameLayout5 == null) {
                    frameLayout5 = null;
                }
                companion.getClass();
                i3 = BatteryChipAnimationUtils.Companion.getBounds(frameLayout5).left;
            } else {
                BatteryChipAnimationUtils.Companion companion2 = BatteryChipAnimationUtils.Companion;
                FrameLayout frameLayout6 = this.animationWindowView;
                if (frameLayout6 == null) {
                    frameLayout6 = null;
                }
                companion2.getClass();
                i2 = BatteryChipAnimationUtils.Companion.getBounds(frameLayout6).right;
                i3 = getBatteryBounds().right;
            }
            int i4 = i2 - i3;
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 8388661;
            layoutParams.setMarginEnd(i4);
        } else {
            FrameLayout frameLayout7 = this.animationWindowView;
            if (frameLayout7 == null) {
                frameLayout7 = null;
            }
            boolean isLayoutRtl = frameLayout7.isLayoutRtl();
            IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
            if (isLayoutRtl) {
                calculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding();
                i = this.dotMarginStart;
            } else {
                calculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
                i = this.dotMarginStart;
            }
            int i5 = calculateRightPadding - i;
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 8388629;
            layoutParams.setMarginEnd(i5);
        }
        frameLayout3.addView(view, layoutParams);
        view.setAlpha(0.0f);
        FrameLayout frameLayout8 = this.animationWindowView;
        if (frameLayout8 == null) {
            frameLayout8 = null;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) frameLayout8.getParent()).getWidth(), Integer.MIN_VALUE);
        FrameLayout frameLayout9 = this.animationWindowView;
        if (frameLayout9 == null) {
            frameLayout9 = null;
        }
        view.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(((View) frameLayout9.getParent()).getHeight(), Integer.MIN_VALUE));
        if (backgroundAnimatableView instanceof SamsungBatteryStatusChip) {
            SamsungBatteryStatusChip samsungBatteryStatusChip = (SamsungBatteryStatusChip) backgroundAnimatableView;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) samsungBatteryStatusChip.getLayoutParams();
            BatteryChipAnimationUtils.Companion companion3 = BatteryChipAnimationUtils.Companion;
            View batteryMeterView = getBatteryMeterView();
            companion3.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView);
            marginLayoutParams.topMargin = ((bounds.top + bounds.bottom) / 2) - (view.getMeasuredHeight() / 2);
            samsungBatteryStatusChip.setAlpha(0.0f);
            ViewGroup.LayoutParams layoutParams2 = samsungBatteryStatusChip.background.getLayoutParams();
            layoutParams2.width = samsungBatteryStatusChip.batteryChipContainer.getMeasuredWidth();
            layoutParams2.height = samsungBatteryStatusChip.batteryChipContainer.getMeasuredHeight();
        }
        updateChipBounds(backgroundAnimatableView, statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context)));
        view.setPivotX(this.animationDirection != 2 ? view.getMeasuredWidth() : 0.0f);
        view.setPivotY(view.getMeasuredHeight() / 2.0f);
        view.setScaleX(f);
        view.setScaleY(f);
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        view.setVisibility(!keyguardStateController.isVisible() ? 0 : 8);
        boolean isVisible = keyguardStateController.isVisible();
        PrivacyLogger privacyLogger = this.privacyLogger;
        if (isVisible) {
            privacyLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = privacyLogger.buffer;
            LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = false;
            logBuffer.commit(obtain);
        }
        this.currentAnimatedView = backgroundAnimatableView;
        int width = this.chipBounds.width();
        privacyLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda02 = new PrivacyLogger$$ExternalSyntheticLambda0(13);
        LogBuffer logBuffer2 = privacyLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("PrivacyLog", logLevel2, privacyLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
        logMessageImpl.bool1 = true;
        logMessageImpl.int1 = width;
        logBuffer2.commit(obtain2);
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void stop() {
        ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).removeCallback(this.statusBarContentInsetsChangedListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateChipBounds(BackgroundAnimatableView backgroundAnimatableView, Rect rect) {
        int measuredWidth;
        int i;
        int measuredWidth2;
        int i2;
        if (backgroundAnimatableView instanceof SamsungBatteryStatusChip) {
            BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
            View batteryMeterView = getBatteryMeterView();
            companion.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView);
            int i3 = (bounds.top + bounds.bottom) / 2;
            backgroundAnimatableView.getClass();
            View view = (View) backgroundAnimatableView;
            int measuredHeight = i3 - (view.getMeasuredHeight() / 2);
            int measuredHeight2 = view.getMeasuredHeight() + measuredHeight;
            if (this.animationDirection == 1) {
                int i4 = getBatteryBounds().right;
                FrameLayout frameLayout = this.animationWindowView;
                measuredWidth2 = i4 - (frameLayout != null ? frameLayout : null).getLeft();
                i2 = measuredWidth2 - view.getMeasuredWidth();
            } else {
                int i5 = getBatteryBounds().left;
                FrameLayout frameLayout2 = this.animationWindowView;
                int left = i5 - (frameLayout2 != null ? frameLayout2 : null).getLeft();
                measuredWidth2 = view.getMeasuredWidth() + left;
                i2 = left;
            }
            this.chipBounds = new Rect(i2, measuredHeight, measuredWidth2, measuredHeight2);
        } else {
            int i6 = rect.top;
            int height = rect.height();
            backgroundAnimatableView.getClass();
            View view2 = (View) backgroundAnimatableView;
            int measuredHeight3 = ((height - view2.getMeasuredHeight()) / 2) + i6;
            int measuredHeight4 = view2.getMeasuredHeight() + measuredHeight3;
            int i7 = this.animationDirection;
            IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
            if (i7 == 1) {
                FrameLayout frameLayout3 = this.animationWindowView;
                measuredWidth = ((frameLayout3 != null ? frameLayout3 : null).getWidth() - indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding()) + this.dotMarginStart;
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
