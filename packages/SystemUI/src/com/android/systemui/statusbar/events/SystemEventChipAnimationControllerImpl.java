package com.android.systemui.statusbar.events;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
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
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.leak.RotationUtils;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public final class SystemEventChipAnimationControllerImpl extends KeyguardUpdateMonitorCallback implements SystemEventChipAnimationController {
    public final Rect animRect;
    public final FrameLayout animationWindowView;
    public int animationWindowViewHeight;
    public final Queue batteryQueue;
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
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
    private final SettingsHelper settingsHelper;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1 statusBarContentInsetsChangedListener;
    public final ContextThemeWrapper themedContext;
    public int animationDirection = 1;
    public Rect chipBounds = new Rect();

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
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.chipMinWidth = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_min_animation_width);
        ArrayDeque arrayDeque = new ArrayDeque();
        this.batteryQueue = arrayDeque;
        this.dotSize = context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter);
        this.dotMarginStart = context.getResources().getDimensionPixelSize(R.dimen.privacy_dot_margin_start);
        this.animRect = new Rect();
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
                SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = this.this$0;
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
                int iCalculateRightPadding;
                int i9;
                if (i == i5 && i3 == i7) {
                    return;
                }
                StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) this.this$0.contentInsetsProvider;
                Rect statusBarContentAreaForRotation = statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context));
                final SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = this.this$0;
                BackgroundAnimatableView backgroundAnimatableView = systemEventChipAnimationControllerImpl.currentAnimatedView;
                if (backgroundAnimatableView != 0) {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) ((View) backgroundAnimatableView).getLayoutParams();
                    FrameLayout frameLayout5 = systemEventChipAnimationControllerImpl.animationWindowView;
                    if (frameLayout5 == null) {
                        frameLayout5 = null;
                    }
                    boolean zIsLayoutRtl = frameLayout5.isLayoutRtl();
                    IndicatorGardenPresenter indicatorGardenPresenter2 = systemEventChipAnimationControllerImpl.indicatorGardenPresenter;
                    if (zIsLayoutRtl) {
                        iCalculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding();
                        i9 = systemEventChipAnimationControllerImpl.dotMarginStart;
                    } else {
                        iCalculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding();
                        i9 = systemEventChipAnimationControllerImpl.dotMarginStart;
                    }
                    layoutParams2.setMarginEnd(iCalculateRightPadding - i9);
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    obj.getClass();
                    ((View) obj).setLayoutParams(layoutParams2);
                    systemEventChipAnimationControllerImpl.updateChipBounds(backgroundAnimatableView, statusBarContentAreaForRotation);
                    ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 1);
                    valueAnimatorOfInt.setDuration(0L);
                    valueAnimatorOfInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$init$2$1$1
                        @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                        public final void onAnimationUpdate(Animator animator) {
                            systemEventChipAnimationControllerImpl.updateCurrentAnimatedView();
                        }
                    });
                    valueAnimatorOfInt.start(false);
                }
            }
        });
        keyguardUpdateMonitor.registerCallback(this);
        if (DeviceType.isSupportModelPopOverStatusBar()) {
            secPanelExpansionStateInteractor.registerListener(new SecPanelExpansionStateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl.1
                @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
                public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                    SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    if (obj != null && ((View) obj).isAttachedToWindow() && DeviceState.isShowingPopOverStatusBar(systemEventChipAnimationControllerImpl.context)) {
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
                }
            });
        }
        arrayDeque.clear();
        this.statusBarContentInsetsChangedListener = new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                int iCalculateRightPadding;
                int i;
                final SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = this.this$0;
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
                        boolean zIsLayoutRtl = frameLayout5.isLayoutRtl();
                        IndicatorGardenPresenter indicatorGardenPresenter2 = systemEventChipAnimationControllerImpl.indicatorGardenPresenter;
                        if (zIsLayoutRtl) {
                            iCalculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateLeftPadding();
                            i = systemEventChipAnimationControllerImpl.dotMarginStart;
                        } else {
                            iCalculateRightPadding = indicatorGardenPresenter2.gardenAlgorithm.calculateRightPadding();
                            i = systemEventChipAnimationControllerImpl.dotMarginStart;
                        }
                        layoutParams2.setMarginEnd(iCalculateRightPadding - i);
                    }
                    Object obj = systemEventChipAnimationControllerImpl.currentAnimatedView;
                    obj.getClass();
                    ((View) obj).setLayoutParams(layoutParams2);
                    systemEventChipAnimationControllerImpl.updateChipBounds(backgroundAnimatableView, statusBarContentAreaForRotation);
                    ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 1);
                    valueAnimatorOfInt.setDuration(0L);
                    valueAnimatorOfInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$statusBarContentInsetsChangedListener$1$onStatusBarContentInsetsChanged$1$1
                        @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                        public final void onAnimationUpdate(Animator animator) {
                            systemEventChipAnimationControllerImpl.updateCurrentAnimatedView();
                        }
                    });
                    valueAnimatorOfInt.start(false);
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
        LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = !z;
        logBuffer.commit(logMessageObtain);
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) throws Resources.NotFoundException {
        View contentView;
        this.animRect.set(this.chipBounds);
        if (!z2) {
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            AnimationUtil.Companion companion = AnimationUtil.Companion;
            valueAnimatorOfFloat.setStartDelay(companion.getFrames(7));
            valueAnimatorOfFloat.setDuration(companion.getFrames(5));
            valueAnimatorOfFloat.setInterpolator(null);
            valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$alphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    Object obj = this.this$0.currentAnimatedView;
                    if (obj != null) {
                        ((View) obj).setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                    }
                }
            });
            BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
            if (backgroundAnimatableView != null && (contentView = backgroundAnimatableView.getContentView()) != null) {
                contentView.setAlpha(0.0f);
            }
            final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.setStartDelay(companion.getFrames(10));
            valueAnimatorOfFloat2.setDuration(companion.getFrames(10));
            valueAnimatorOfFloat2.setInterpolator(null);
            valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$contentAlphaIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    View contentView2;
                    BackgroundAnimatableView backgroundAnimatableView2 = this.this$0.currentAnimatedView;
                    if (backgroundAnimatableView2 == null || (contentView2 = backgroundAnimatableView2.getContentView()) == null) {
                        return;
                    }
                    contentView2.setAlpha(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.chipMinWidth, this.chipBounds.width());
            valueAnimatorOfInt.setStartDelay(companion.getFrames(7));
            valueAnimatorOfInt.setDuration(companion.getFrames(23));
            valueAnimatorOfInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_IN);
            valueAnimatorOfInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$onSystemEventAnimationBegin$moveIn$1$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsWidth(this.this$0, ((Integer) valueAnimatorOfInt.getAnimatedValue()).intValue());
                }
            });
            SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
            springAnimatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2, valueAnimatorOfInt);
            return springAnimatorSet;
        }
        boolean zIsClosed = this.shadeExpansionStateManager.isClosed();
        HeadsUpAppearanceController headsUpAppearanceController = this.indicatorGardenPresenter.headsUpAppearanceController;
        if (headsUpAppearanceController != null && this.settingsHelper.isPopStyleDetail() && headsUpAppearanceController.shouldHeadsUpStatusBarBeVisible()) {
            zIsClosed = true;
        }
        if (!zIsClosed) {
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
        SpringAnimatorSet systemIconAnimator = samsungBatteryStatusChip2.getSystemIconAnimator(samsungBatteryStatusChip2.getRootView().findViewById(R.id.statusIcons), true, samsungBatteryStatusChip2.isLayoutRtl());
        int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        float measuredWidth = samsungBatteryStatusChip2.batteryChipContainer.getMeasuredWidth();
        BatteryChipAnimationUtils.Companion.getClass();
        if (layoutDirectionFromLocale == 0) {
            measuredWidth = -measuredWidth;
        }
        SpringAnimatorSet springAnimatorSet4 = new SpringAnimatorSet();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(samsungBatteryStatusChip2.batteryLevelProgress, "translationX", measuredWidth, 0.0f);
        objectAnimatorOfFloat.m896setDuration(1000L);
        objectAnimatorOfFloat.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        Unit unit = Unit.INSTANCE;
        final ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(10.0f, 0.0f);
        valueAnimatorOfFloat3.setDuration(1000L);
        valueAnimatorOfFloat3.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        valueAnimatorOfFloat3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingWaveAnimation$1$2$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                LottieAnimationView lottieAnimationView = samsungBatteryStatusChip2.batteryLevelProgress;
                BatteryChipConstants.INSTANCE.getClass();
                KeyPath keyPath = BatteryChipConstants.WAVE_KEY_PATH;
                Float f4 = LottieProperty.BLUR_RADIUS;
                final ValueAnimator valueAnimator = valueAnimatorOfFloat3;
                lottieAnimationView.addValueCallback(keyPath, (KeyPath) f4, new SimpleLottieValueCallback() { // from class: com.android.systemui.statusbar.events.SamsungBatteryStatusChip$getChargingWaveAnimation$1$2$1.1
                    @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                    public final Object getValue() {
                        return (Float) valueAnimator.getAnimatedValue();
                    }
                });
            }
        });
        springAnimatorSet4.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat3);
        springAnimatorSet3.playTogether(systemIconAnimator, springAnimatorSet4, samsungBatteryStatusChip2.getContainerBackgroundAnimator(true, batteryBounds, f3, samsungBatteryStatusChip2.isLayoutRtl(), z), samsungBatteryStatusChip2.getBatteryBackgroundAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()), samsungBatteryStatusChip2.getBatteryLevelTextAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()), samsungBatteryStatusChip2.getChargingIconAnimator(f3, batteryBounds, true, samsungBatteryStatusChip2.isLayoutRtl()));
        springAnimatorSet2.playTogether(springAnimatorSet3);
        return springAnimatorSet2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0028  */
    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController, com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        Object[] objArr;
        SpringAnimatorSet springAnimatorSet;
        if (z3) {
            boolean zIsClosed = this.shadeExpansionStateManager.isClosed();
            HeadsUpAppearanceController headsUpAppearanceController = this.indicatorGardenPresenter.headsUpAppearanceController;
            if (headsUpAppearanceController != null && this.settingsHelper.isPopStyleDetail() && headsUpAppearanceController.shouldHeadsUpStatusBarBeVisible()) {
                zIsClosed = true;
            }
            objArr = (zIsClosed || this.isChipAnimationStarted) ? false : true;
        }
        if (objArr == true) {
            return new SpringAnimatorSet();
        }
        if (objArr == true) {
            throw new NoWhenBranchMatchedException();
        }
        this.isChipAnimationStarted = false;
        this.animRect.set(this.chipBounds);
        if (z3) {
            springAnimatorSet = new SpringAnimatorSet();
            Object obj = this.currentAnimatedView;
            if ((obj != null ? (View) obj : null) instanceof SamsungBatteryStatusChip) {
                SamsungBatteryStatusChip samsungBatteryStatusChip = (SamsungBatteryStatusChip) (obj != null ? (View) obj : null);
                Rect batteryBounds = getBatteryBounds();
                float f = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
                samsungBatteryStatusChip.getClass();
                SpringAnimatorSet springAnimatorSet2 = new SpringAnimatorSet();
                springAnimatorSet2.playTogether(samsungBatteryStatusChip.getSystemIconAnimator(samsungBatteryStatusChip.getRootView().findViewById(R.id.statusIcons), false, samsungBatteryStatusChip.isLayoutRtl()), samsungBatteryStatusChip.getContainerBackgroundAnimator(false, batteryBounds, f, samsungBatteryStatusChip.isLayoutRtl(), z2), samsungBatteryStatusChip.getBatteryBackgroundAnimator(f, batteryBounds, false, samsungBatteryStatusChip.isLayoutRtl()), samsungBatteryStatusChip.getBatteryLevelTextAnimator(f, batteryBounds, false, samsungBatteryStatusChip.isLayoutRtl()), samsungBatteryStatusChip.getChargingIconAnimator(f, batteryBounds, false, samsungBatteryStatusChip.isLayoutRtl()));
                springAnimatorSet.playTogether(springAnimatorSet2);
                BackgroundAnimatableView backgroundAnimatableView = this.currentAnimatedView;
                ((SamsungBatteryStatusChip) (backgroundAnimatableView != null ? (View) backgroundAnimatableView : null)).playProgressLottieAnimation(false);
            } else {
                Log.e("SystemEventChipAnimationController", "CurrentAnimatedView is not a BatteryStatusChip - currentAnimatedView: " + (obj != null ? (View) obj : null));
            }
        } else {
            int i = this.chipMinWidth;
            if (z) {
                final ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.chipBounds.width(), i);
                AnimationUtil.Companion companion = AnimationUtil.Companion;
                valueAnimatorOfInt.setDuration(companion.getFrames(9));
                valueAnimatorOfInt.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_1);
                valueAnimatorOfInt.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$width1$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsWidth(this.this$0, ((Integer) valueAnimatorOfInt.getAnimatedValue()).intValue());
                    }
                });
                final ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(i, this.dotSize);
                valueAnimatorOfInt2.setStartDelay(companion.getFrames(9));
                valueAnimatorOfInt2.setDuration(companion.getFrames(20));
                valueAnimatorOfInt2.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_WIDTH_TO_DOT_KEYFRAME_2);
                valueAnimatorOfInt2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$width2$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsWidth(this.this$0, ((Integer) valueAnimatorOfInt2.getAnimatedValue()).intValue());
                    }
                });
                int i2 = this.dotSize * 2;
                Rect rect = this.chipBounds;
                final int iHeight = (rect.height() / 2) + rect.top;
                final ValueAnimator valueAnimatorOfInt3 = ValueAnimator.ofInt(this.chipBounds.height(), i2);
                valueAnimatorOfInt3.setStartDelay(companion.getFrames(8));
                valueAnimatorOfInt3.setDuration(companion.getFrames(6));
                valueAnimatorOfInt3.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_1);
                valueAnimatorOfInt3.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$height1$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsHeight(this.this$0, ((Integer) valueAnimatorOfInt3.getAnimatedValue()).intValue(), iHeight);
                    }
                });
                final ValueAnimator valueAnimatorOfInt4 = ValueAnimator.ofInt(i2, this.dotSize);
                valueAnimatorOfInt4.setStartDelay(companion.getFrames(14));
                valueAnimatorOfInt4.setDuration(companion.getFrames(15));
                valueAnimatorOfInt4.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_CHIP_HEIGHT_TO_DOT_KEYFRAME_2);
                valueAnimatorOfInt4.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$height2$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsHeight(this.this$0, ((Integer) valueAnimatorOfInt4.getAnimatedValue()).intValue(), iHeight);
                    }
                });
                final ValueAnimator valueAnimatorOfInt5 = ValueAnimator.ofInt(0, this.dotSize);
                valueAnimatorOfInt5.setStartDelay(companion.getFrames(3));
                valueAnimatorOfInt5.setDuration(companion.getFrames(11));
                PathInterpolator pathInterpolator = SystemStatusAnimationSchedulerKt.STATUS_CHIP_MOVE_TO_DOT;
                valueAnimatorOfInt5.setInterpolator(pathInterpolator);
                valueAnimatorOfInt5.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$moveOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = this.this$0;
                        int i3 = systemEventChipAnimationControllerImpl.animationDirection;
                        ValueAnimator valueAnimator = valueAnimatorOfInt5;
                        int iIntValue = i3 == 1 ? ((Integer) valueAnimator.getAnimatedValue()).intValue() : -((Integer) valueAnimator.getAnimatedValue()).intValue();
                        Object obj2 = systemEventChipAnimationControllerImpl.currentAnimatedView;
                        if (obj2 != null) {
                            ((View) obj2).setTranslationX(iIntValue);
                        }
                    }
                });
                Rect rect2 = this.chipBounds;
                final ValueAnimator valueAnimatorOfInt6 = ValueAnimator.ofInt(0, MathKt__MathJVMKt.roundToInt(((((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).getStatusBarPaddingTop() + (this.animationWindowView != null ? r5 : null).getHeight()) / 2.0f) - ((rect2.top + rect2.bottom) / 2.0f)));
                valueAnimatorOfInt6.setStartDelay(companion.getFrames(3));
                valueAnimatorOfInt6.setDuration(companion.getFrames(26));
                valueAnimatorOfInt6.setInterpolator(pathInterpolator);
                valueAnimatorOfInt6.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationForDot$moveOutY$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        int iIntValue = ((Integer) valueAnimatorOfInt6.getAnimatedValue()).intValue();
                        Object obj2 = this.this$0.currentAnimatedView;
                        if (obj2 != null) {
                            ((View) obj2).setTranslationY(iIntValue);
                        }
                    }
                });
                springAnimatorSet = new SpringAnimatorSet();
                springAnimatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfInt2, valueAnimatorOfInt3, valueAnimatorOfInt4, valueAnimatorOfInt5, valueAnimatorOfInt6);
            } else {
                final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                AnimationUtil.Companion companion2 = AnimationUtil.Companion;
                valueAnimatorOfFloat.setStartDelay(companion2.getFrames(6));
                valueAnimatorOfFloat.setDuration(companion2.getFrames(6));
                valueAnimatorOfFloat.setInterpolator(null);
                valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationDefault$alphaOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        Object obj2 = this.this$0.currentAnimatedView;
                        if (obj2 != null) {
                            ((View) obj2).setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                        }
                    }
                });
                final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
                valueAnimatorOfFloat2.setDuration(companion2.getFrames(5));
                valueAnimatorOfFloat2.setInterpolator(null);
                valueAnimatorOfFloat2.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationDefault$contentAlphaOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        View contentView;
                        BackgroundAnimatableView backgroundAnimatableView2 = this.this$0.currentAnimatedView;
                        if (backgroundAnimatableView2 == null || (contentView = backgroundAnimatableView2.getContentView()) == null) {
                            return;
                        }
                        contentView.setAlpha(((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue());
                    }
                });
                final ValueAnimator valueAnimatorOfInt7 = ValueAnimator.ofInt(this.chipBounds.width(), i);
                valueAnimatorOfInt7.setDuration(companion2.getFrames(23));
                valueAnimatorOfInt7.setInterpolator(SystemStatusAnimationSchedulerKt.STATUS_BAR_X_MOVE_OUT);
                valueAnimatorOfInt7.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl$createMoveOutAnimationDefault$moveOut$1$1
                    @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                    public final void onAnimationUpdate(Animator animator) {
                        SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = this.this$0;
                        if (systemEventChipAnimationControllerImpl.currentAnimatedView != null) {
                            SystemEventChipAnimationControllerImpl.access$updateAnimatedViewBoundsWidth(systemEventChipAnimationControllerImpl, ((Integer) valueAnimatorOfInt7.getAnimatedValue()).intValue());
                        }
                    }
                });
                SpringAnimatorSet springAnimatorSet3 = new SpringAnimatorSet();
                springAnimatorSet3.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2, valueAnimatorOfInt7);
                springAnimatorSet = springAnimatorSet3;
            }
        }
        springAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemEventChipAnimationControllerImpl.onSystemEventAnimationFinish.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SystemEventChipAnimationControllerImpl systemEventChipAnimationControllerImpl = SystemEventChipAnimationControllerImpl.this;
                if (systemEventChipAnimationControllerImpl.animationWindowView == null) {
                    return;
                }
                int iWidth = systemEventChipAnimationControllerImpl.chipBounds.width();
                PrivacyLogger privacyLogger = systemEventChipAnimationControllerImpl.privacyLogger;
                privacyLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(13);
                LogBuffer logBuffer = privacyLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.bool1 = false;
                logMessageImpl.int1 = iWidth;
                logBuffer.commit(logMessageObtain);
                BackgroundAnimatableView backgroundAnimatableView2 = systemEventChipAnimationControllerImpl.currentAnimatedView;
                if (backgroundAnimatableView2 == null) {
                    return;
                }
                View view = (View) backgroundAnimatableView2;
                if (!(view instanceof SamsungBatteryStatusChip)) {
                    FrameLayout frameLayout = systemEventChipAnimationControllerImpl.animationWindowView;
                    (frameLayout != null ? frameLayout : null).removeView(view);
                } else {
                    if (((ArrayDeque) systemEventChipAnimationControllerImpl.batteryQueue).isEmpty()) {
                        return;
                    }
                    FrameLayout frameLayout2 = systemEventChipAnimationControllerImpl.animationWindowView;
                    if (frameLayout2 == null) {
                        frameLayout2 = null;
                    }
                    BackgroundAnimatableView backgroundAnimatableView3 = (BackgroundAnimatableView) ((ArrayDeque) systemEventChipAnimationControllerImpl.batteryQueue).poll();
                    frameLayout2.removeView(backgroundAnimatableView3 != null ? (View) backgroundAnimatableView3 : null);
                }
            }
        });
        return springAnimatorSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void prepareChipAnimation(Function1 function1, boolean z) {
        int iCalculateRightPadding;
        int i;
        FrameLayout.LayoutParams layoutParams;
        int i2;
        int i3;
        Object obj;
        if (!DeviceState.isShowingPopOverStatusBar(this.context) || ((Number) this.secPanelExpansionStateInteractor.shadeFraction.getValue()).floatValue() == 0.0f) {
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
            if (!z && (obj = this.currentAnimatedView) != null) {
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
            BackgroundAnimatableView backgroundAnimatableView = (BackgroundAnimatableView) function1.mo781invoke(contextThemeWrapper);
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
                boolean zIsLayoutRtl = frameLayout7.isLayoutRtl();
                IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
                if (zIsLayoutRtl) {
                    iCalculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding();
                    i = this.dotMarginStart;
                } else {
                    iCalculateRightPadding = indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding();
                    i = this.dotMarginStart;
                }
                int i5 = iCalculateRightPadding - i;
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
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) frameLayout8.getParent()).getWidth(), Integer.MIN_VALUE);
            FrameLayout frameLayout9 = this.animationWindowView;
            if (frameLayout9 == null) {
                frameLayout9 = null;
            }
            view.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(((View) frameLayout9.getParent()).getHeight(), Integer.MIN_VALUE));
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
            boolean zIsVisible = keyguardStateController.isVisible();
            PrivacyLogger privacyLogger = this.privacyLogger;
            if (zIsVisible) {
                privacyLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(12);
                LogBuffer logBuffer = privacyLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).bool1 = false;
                logBuffer.commit(logMessageObtain);
            }
            this.currentAnimatedView = backgroundAnimatableView;
            if (z) {
                ((ArrayDeque) this.batteryQueue).add(backgroundAnimatableView);
            }
            int iWidth = this.chipBounds.width();
            privacyLogger.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda02 = new PrivacyLogger$$ExternalSyntheticLambda0(13);
            LogBuffer logBuffer2 = privacyLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("PrivacyLog", logLevel2, privacyLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
            logMessageImpl.bool1 = true;
            logMessageImpl.int1 = iWidth;
            logBuffer2.commit(logMessageObtain2);
        }
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void removeBatteryAnim() {
        Object obj = this.currentAnimatedView;
        if (obj != null) {
            FrameLayout frameLayout = this.animationWindowView;
            if (frameLayout == null) {
                frameLayout = null;
            }
            frameLayout.removeView((View) obj);
            View batteryMeterView = getBatteryMeterView();
            if (batteryMeterView != null) {
                batteryMeterView.setAlpha(1.0f);
            }
        }
    }

    @Override // com.android.systemui.statusbar.events.SystemEventChipAnimationController
    public final void stop() {
        ((StatusBarContentInsetsProviderImpl) this.contentInsetsProvider).removeCallback(this.statusBarContentInsetsChangedListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateChipBounds(BackgroundAnimatableView backgroundAnimatableView, Rect rect) {
        int measuredWidth;
        int measuredWidth2;
        int measuredWidth3;
        int measuredWidth4;
        if (backgroundAnimatableView instanceof SamsungBatteryStatusChip) {
            BatteryChipAnimationUtils.Companion companion = BatteryChipAnimationUtils.Companion;
            View batteryMeterView = getBatteryMeterView();
            companion.getClass();
            Rect bounds = BatteryChipAnimationUtils.Companion.getBounds(batteryMeterView);
            int i = (bounds.top + bounds.bottom) / 2;
            backgroundAnimatableView.getClass();
            View view = (View) backgroundAnimatableView;
            int measuredHeight = i - (view.getMeasuredHeight() / 2);
            int measuredHeight2 = view.getMeasuredHeight() + measuredHeight;
            if (this.animationDirection == 1) {
                int i2 = getBatteryBounds().right;
                FrameLayout frameLayout = this.animationWindowView;
                measuredWidth3 = i2 - (frameLayout != null ? frameLayout : null).getLeft();
                measuredWidth4 = measuredWidth3 - view.getMeasuredWidth();
            } else {
                int i3 = getBatteryBounds().left;
                FrameLayout frameLayout2 = this.animationWindowView;
                int left = i3 - (frameLayout2 != null ? frameLayout2 : null).getLeft();
                measuredWidth3 = view.getMeasuredWidth() + left;
                measuredWidth4 = left;
            }
            this.chipBounds = new Rect(measuredWidth4, measuredHeight, measuredWidth3, measuredHeight2);
        } else {
            int i4 = rect.top;
            int iHeight = rect.height();
            backgroundAnimatableView.getClass();
            View view2 = (View) backgroundAnimatableView;
            int measuredHeight3 = ((iHeight - view2.getMeasuredHeight()) / 2) + i4;
            int measuredHeight4 = view2.getMeasuredHeight() + measuredHeight3;
            int i5 = this.animationDirection;
            IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
            if (i5 == 1) {
                FrameLayout frameLayout3 = this.animationWindowView;
                measuredWidth = ((frameLayout3 != null ? frameLayout3 : null).getWidth() - indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding()) + this.dotMarginStart;
                measuredWidth2 = measuredWidth - view2.getMeasuredWidth();
            } else {
                int iCalculateLeftPadding = indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding() - this.dotMarginStart;
                measuredWidth = view2.getMeasuredWidth() + iCalculateLeftPadding;
                measuredWidth2 = iCalculateLeftPadding;
            }
            this.chipBounds = new Rect(measuredWidth2, measuredHeight3, measuredWidth, measuredHeight4);
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
