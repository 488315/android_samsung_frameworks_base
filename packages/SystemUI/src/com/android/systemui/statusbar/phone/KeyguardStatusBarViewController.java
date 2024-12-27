package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.ValueAnimator;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.CarrierText;
import com.android.keyguard.CarrierTextController;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.component.PluginLockStatusBarCallback;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeViewStateProvider;
import com.android.systemui.slimindicator.SlimIndicatorKeyguardCarrierTextHelper;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator;
import com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.ongoingcall.KeyguardCallChipController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallChronometer;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.ui.viewmodel.KeyguardStatusBarViewModel;
import com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

public final class KeyguardStatusBarViewController extends ViewController implements IndicatorGarden, Dumpable {
    public static final AnimationProperties KEYGUARD_HUN_PROPERTIES;
    public final AnonymousClass2 mAnimationCallback;
    public final SystemStatusAnimationScheduler mAnimationScheduler;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda9 mAnimatorUpdateListener;
    public final Executor mBackgroundExecutor;
    public final BatteryController mBatteryController;
    public boolean mBatteryListening;
    public final BatteryMeterViewController mBatteryMeterViewController;
    public final AnonymousClass3 mBatteryStateChangeCallback;
    public final BiometricUnlockController mBiometricUnlockController;
    public final List mBlockedIcons;
    public final CarrierTextController mCarrierTextController;
    public final CommandQueue mCommandQueue;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda10 mCommunalConsumer;
    public final CommunalSceneInteractor mCommunalSceneInteractor;
    public boolean mCommunalShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public boolean mDelayShowingKeyguardStatusBar;
    public final AnonymousClass8 mDesktopCallback;
    public final DesktopManager mDesktopManager;
    public final DisableStateTracker mDisableStateTracker;
    public boolean mDozing;
    public float mDraggedFraction;
    public int mEssentialLeftWidth;
    public float mExplicitAlpha;
    public boolean mFirstBypassAttempt;
    public IndicatorGardenContainer mGardenLeftContainer;
    public final AnonymousClass13 mGardener;
    public final AnimatableProperty.AnonymousClass6 mHeadsUpShowingAmountAnimation;
    public boolean mHiddenByKnox;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final StatusBarContentInsetsProvider mInsetsProvider;
    public final KeyguardBypassController mKeyguardBypassController;
    public float mKeyguardHeadsUpShowingAmount;
    public ViewGroup mKeyguardLeftSideContainerView;
    public final KeyguardStateController mKeyguardStateController;
    public float mKeyguardStatusBarAnimateAlpha;
    public ViewGroup mKeyguardStatusBarAreaView;
    public ViewGroup mKeyguardStatusBarNotifContainer;
    public final KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KnoxStatusBarControlViewModel mKnoxStatusBarControlViewModel;
    public final Object mLock;
    public final KeyguardLogger mLogger;
    public final Executor mMainExecutor;
    public final NetspeedViewController mNetspeedViewController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final int mNotificationsHeaderCollideDistance;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda8 mOnUserInfoChangedListener;
    public final OngoingCallController mOngoingCallController;
    public final AnonymousClass9 mOngoingCallListener;
    public final PluginLockMediator mPluginLockMediator;
    public final AnonymousClass7 mPluginLockStarCallback;
    public final PluginLockStarManager mPluginLockStarManager;
    public final SecureSettings mSecureSettings;
    public final ShadeViewStateProvider mShadeViewStateProvider;
    public boolean mShowingKeyguardHeadsUp;
    public final SlimIndicatorKeyguardCarrierTextHelper mSlimIndicatorKeyguardCarrierTextHelper;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public final StatusBarIconController mStatusBarIconController;
    public int mStatusBarState;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final AnonymousClass5 mStatusBarStateListener;
    public final StatusBarUserChipViewModel mStatusBarUserChipViewModel;
    public IndicatorGardenContainer mStatusIconArea;
    public ViewGroup mStatusIconAreaView;
    public final StatusIconContainerController mStatusIconContainerController;
    public StatusBarSystemEventDefaultAnimator mSystemEventAnimator;
    public float mSystemEventAnimatorAlpha;
    public View mSystemIconsContainer;
    public TintedIconManager mTintedIconManager;
    public final TintedIconManager.Factory mTintedIconManagerFactory;
    public final TwoPhoneModeIconController mTwoPhoneModeController;
    public final UserInfoController mUserInfoController;
    public final UserManager mUserManager;
    public final AnonymousClass12 mVolumeSettingObserver;

    /* renamed from: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$1, reason: invalid class name */
    public final class AnonymousClass1 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            keyguardStatusBarViewController.updateUserSwitcher();
            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                keyguardStatusBarViewController.mIndicatorGardenPresenter.onGardenConfigurationChanged(keyguardStatusBarViewController, configuration);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).loadDimens();
            keyguardStatusBarViewController.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(keyguardStatusBarViewController.getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(keyguardStatusBarViewController, 1), new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(keyguardStatusBarViewController, 2), keyguardStatusBarViewController.mSystemEventAnimator.isAnimationRunning);
            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                IndicatorGardenInputProperties indicatorGardenInputProperties = keyguardStatusBarViewController.mIndicatorGardenPresenter.inputProperties;
                indicatorGardenInputProperties.updateWindowMetrics();
                indicatorGardenInputProperties.updatePaddingValues();
            }
            if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView;
                float f = keyguardStatusBarViewController.mIndicatorScaleGardener.getLatestScaleModel(keyguardStatusBarViewController.getContext()).ratio;
                keyguardStatusBarView.getClass();
                int dimensionPixelSize = DeviceType.isTablet() ? keyguardStatusBarView.getContext().getResources().getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_size) : keyguardStatusBarView.getContext().getResources().getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_size_phone);
                int dimensionPixelSize2 = keyguardStatusBarView.getContext().getResources().getDimensionPixelSize(R.dimen.multi_user_avatar_keyguard_margin_start);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardStatusBarView.mMultiUserAvatar.getLayoutParams();
                marginLayoutParams.height = dimensionPixelSize;
                marginLayoutParams.width = dimensionPixelSize;
                keyguardStatusBarView.mMultiUserAvatar.setScaleX(f);
                keyguardStatusBarView.mMultiUserAvatar.setScaleY(f);
                marginLayoutParams.setMarginStart((int) (dimensionPixelSize2 * f));
                keyguardStatusBarView.mMultiUserAvatar.setLayoutParams(marginLayoutParams);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                onDensityOrFontScaleChanged();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) ((ViewController) KeyguardStatusBarViewController.this).mView;
                if (keyguardStatusBarView.mKeyguardUserSwitcherEnabled) {
                    return;
                }
                keyguardStatusBarView.mMultiUserAvatar.setContentDescription(keyguardStatusBarView.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView.mMultiUserName));
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).onOverlayChanged();
            keyguardStatusBarViewController.onThemeChanged();
        }
    }

    public static WindowInsets $r8$lambda$7w5SQ8EAIsB6phwkiU0XFhO16b0(KeyguardStatusBarViewController keyguardStatusBarViewController, WindowInsets windowInsets) {
        if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
            keyguardStatusBarViewController.mIndicatorGardenPresenter.onGardenApplyWindowInsets(keyguardStatusBarViewController);
        }
        return ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).updateWindowInsets(windowInsets);
    }

    public static void $r8$lambda$PyURNFylWesnFtBP3URsrY4f6G0(KeyguardStatusBarViewController keyguardStatusBarViewController, boolean z) {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.mHiddenByDeX = z;
        if (z || !((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
            keyguardStatusBarView.setVisibility(keyguardStatusBarView.getVisibility());
        } else {
            keyguardStatusBarView.setVisibility(0);
        }
    }

    /* renamed from: $r8$lambda$SDjYX36TdSthdVP17CFw-x9k5IE, reason: not valid java name */
    public static void m2228$r8$lambda$SDjYX36TdSthdVP17CFwx9k5IE(KeyguardStatusBarViewController keyguardStatusBarViewController, String str, Drawable drawable) {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.mMultiUserAvatar.setImageDrawable(drawable);
        if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
            keyguardStatusBarView.mMultiUserName = str;
            if (keyguardStatusBarView.mKeyguardUserSwitcherEnabled) {
                return;
            }
            keyguardStatusBarView.mMultiUserAvatar.setContentDescription(keyguardStatusBarView.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView.mMultiUserName));
        }
    }

    public static void $r8$lambda$VT0xJSg7HWdegLHOKUTjxWLkC0k(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        keyguardStatusBarViewController.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda5(keyguardStatusBarViewController, 2));
        keyguardStatusBarViewController.mNotificationIconAreaController.setKeyguardNotifIconTint(((KeyguardStatusBarView) keyguardStatusBarViewController.mView).mNotifIconColor);
    }

    /* renamed from: $r8$lambda$YTsAQhIM-ZIR1qbmyZFXU-fHY0U, reason: not valid java name */
    public static /* synthetic */ Unit m2230$r8$lambda$YTsAQhIMZIR1qbmyZFXUfHY0U(KeyguardStatusBarViewController keyguardStatusBarViewController, Float f) {
        ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).setTranslationX(f.floatValue());
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void $r8$lambda$wGlIvEVAPs5MJk9Zk5mL4Xyfw3c(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        StatusIconContainer statusIconContainer;
        if (keyguardStatusBarViewController.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude() == null || (statusIconContainer = (StatusIconContainer) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.statusIcons)) == null) {
            return;
        }
        if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
            statusIconContainer.requestLayout();
        }
    }

    /* renamed from: $r8$lambda$xz00Wgoom64_pyrJyCsnl8d0-lo, reason: not valid java name */
    public static void m2231$r8$lambda$xz00Wgoom64_pyrJyCsnl8d0lo(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.isMultiUserAvatarHidden = ((SlimIndicatorViewMediatorImpl) keyguardStatusBarViewController.mSlimIndicatorViewMediator).isHiddenLockScreenMum();
        keyguardStatusBarView.updateVisibilities();
    }

    public static int $r8$lambda$z2zgdSVI2vZwFMhzcuLHD6QN16w(KeyguardStatusBarViewController keyguardStatusBarViewController, Rect rect) {
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.battery);
        int paddingEnd = ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.statusIcons).getPaddingEnd();
        int paddingRight = keyguardStatusBarViewController.getSidePaddingContainer().getPaddingRight();
        int width = keyguardStatusBarViewController.getResources().getConfiguration().windowConfiguration.getBounds().width();
        TwoPhoneModeIconController twoPhoneModeIconController = keyguardStatusBarViewController.mTwoPhoneModeController;
        return (width - (((batteryMeterView.getMeasuredWidth() + paddingRight) + paddingEnd) + ((!twoPhoneModeIconController.featureEnabled() || twoPhoneModeIconController.getViewWidth() <= 0) ? 0 : twoPhoneModeIconController.getViewWidth()))) - (keyguardStatusBarViewController.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right);
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360L;
        KEYGUARD_HUN_PROPERTIES = animationProperties;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r9v13, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$7] */
    /* JADX WARN: Type inference failed for: r9v14, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$8] */
    /* JADX WARN: Type inference failed for: r9v15, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$9] */
    /* JADX WARN: Type inference failed for: r9v16, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$12] */
    /* JADX WARN: Type inference failed for: r9v18, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$13] */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$2] */
    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$3] */
    /* JADX WARN: Type inference failed for: r9v4, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8] */
    /* JADX WARN: Type inference failed for: r9v5, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9] */
    /* JADX WARN: Type inference failed for: r9v7, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$5] */
    /* JADX WARN: Type inference failed for: r9v8, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda10] */
    public KeyguardStatusBarViewController(KeyguardStatusBarView keyguardStatusBarView, CarrierTextController carrierTextController, ConfigurationController configurationController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, BatteryController batteryController, UserInfoController userInfoController, StatusBarIconController statusBarIconController, TintedIconManager.Factory factory, BatteryMeterViewController batteryMeterViewController, ShadeViewStateProvider shadeViewStateProvider, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStatusBarViewModel keyguardStatusBarViewModel, BiometricUnlockController biometricUnlockController, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, UserManager userManager, StatusBarUserChipViewModel statusBarUserChipViewModel, SecureSettings secureSettings, CommandQueue commandQueue, Executor executor, Executor executor2, KeyguardLogger keyguardLogger, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, CommunalSceneInteractor communalSceneInteractor, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, StatusIconContainerController statusIconContainerController, OngoingCallController ongoingCallController, KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper, DumpManager dumpManager, DesktopManager desktopManager, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, NetspeedViewController netspeedViewController, SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper, NotificationIconAreaController notificationIconAreaController, PluginLockMediator pluginLockMediator, SlimIndicatorViewMediator slimIndicatorViewMediator, PluginLockStarManager pluginLockStarManager) {
        super(keyguardStatusBarView);
        int i = 2;
        int i2 = 1;
        this.mKeyguardHeadsUpShowingAmount = 0.0f;
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mKeyguardHeadsUpShowingAmount = ((Float) obj2).floatValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        Function function = new Function() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(KeyguardStatusBarViewController.this.mKeyguardHeadsUpShowingAmount);
            }
        };
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        this.mHeadsUpShowingAmountAnimation = new AnimatableProperty.AnonymousClass6(R.id.keyguard_hun_animator_end_tag, R.id.keyguard_hun_animator_start_tag, R.id.keyguard_hun_animator_tag, new AnimatableProperty.AnonymousClass5("KEYGUARD_HEADS_UP_SHOWING_AMOUNT", function, biConsumer));
        this.mLock = new Object();
        this.mDraggedFraction = 0.0f;
        this.mConfigurationListener = new AnonymousClass1();
        this.mAnimationCallback = new SystemStatusAnimationCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.2
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationBegin(boolean z, boolean z2) {
                return KeyguardStatusBarViewController.this.mSystemEventAnimator.onSystemEventAnimationBegin(z, z2);
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
                return KeyguardStatusBarViewController.this.mSystemEventAnimator.onSystemEventAnimationFinish(z, z2, z3);
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i3, boolean z, boolean z2) {
                KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) ((ViewController) KeyguardStatusBarViewController.this).mView;
                if (keyguardStatusBarView2.mBatteryCharging != z2) {
                    keyguardStatusBarView2.mBatteryCharging = z2;
                    keyguardStatusBarView2.updateVisibilities();
                }
            }
        };
        this.mOnUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
            public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                KeyguardStatusBarViewController.m2228$r8$lambda$SDjYX36TdSthdVP17CFwx9k5IE(KeyguardStatusBarViewController.this, str, drawable);
            }
        };
        this.mAnimatorUpdateListener = new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mKeyguardStatusBarAnimateAlpha = ((Float) ((ValueAnimator) animator).getAnimatedValue()).floatValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i3, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (keyguardStatusBarViewController.mFirstBypassAttempt && keyguardStatusBarViewController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                    keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = true;
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                int i3 = keyguardStatusBarViewController.mStatusBarState;
                boolean z2 = true;
                if (i3 != 1 && i3 != 2) {
                    z2 = false;
                }
                if (z || !keyguardStatusBarViewController.mFirstBypassAttempt || !z2 || keyguardStatusBarViewController.mDozing || keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar || keyguardStatusBarViewController.mBiometricUnlockController.isBiometricUnlock()) {
                    return;
                }
                keyguardStatusBarViewController.mFirstBypassAttempt = false;
                keyguardStatusBarViewController.animateKeyguardStatusBarIn();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i3) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.mFirstBypassAttempt = keyguardStatusBarViewController.mKeyguardBypassController.getBypassEnabled();
                keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = false;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                    KeyguardStatusBarViewController.this.updateUserSwitcher();
                }
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.5
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i3) {
                KeyguardStatusBarViewController.this.mStatusBarState = i3;
            }
        };
        this.mCommunalConsumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mCommunalShowing = ((Boolean) obj).booleanValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        this.mBlockedIcons = new ArrayList();
        this.mKeyguardStatusBarAnimateAlpha = 1.0f;
        this.mSystemEventAnimatorAlpha = 1.0f;
        this.mExplicitAlpha = -1.0f;
        new PluginLockStatusBarCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.6
            @Override // com.android.systemui.pluginlock.component.PluginLockStatusBarCallback
            public final void onVisibilityUpdated(int i3, int i4) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i3, i4, "onVisibilityUpdated() ", ", ", "KeyguardStatusBarViewController");
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (keyguardStatusBarViewController.mStatusIconAreaView == null) {
                    keyguardStatusBarViewController.mStatusIconAreaView = (ViewGroup) ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).findViewById(R.id.status_icon_area);
                }
                if (keyguardStatusBarViewController.mKeyguardLeftSideContainerView == null) {
                    keyguardStatusBarViewController.mKeyguardLeftSideContainerView = (ViewGroup) ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).findViewById(R.id.keyguard_left_container);
                }
                ViewGroup viewGroup = keyguardStatusBarViewController.mStatusIconAreaView;
                if (viewGroup == null || keyguardStatusBarViewController.mKeyguardLeftSideContainerView == null) {
                    Log.e("KeyguardStatusBarViewController", "onVisibilityUpdated() no views ");
                    return;
                }
                if (i3 != -1 && i3 != viewGroup.getVisibility()) {
                    keyguardStatusBarViewController.mStatusIconAreaView.setVisibility(i3);
                }
                if (i4 == -1 || i4 == keyguardStatusBarViewController.mKeyguardLeftSideContainerView.getVisibility()) {
                    return;
                }
                keyguardStatusBarViewController.mKeyguardLeftSideContainerView.setVisibility(i4);
            }
        };
        this.mPluginLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.7
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("LockStarCallback: onChangedLockStarEnabled: ", "KeyguardStatusBarViewController", z);
                KeyguardStatusBarViewController.this.updateViewState();
            }

            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final Bundle request(Bundle bundle) {
                Log.d("KeyguardStatusBarViewController", "LockStarCallback: request: " + bundle);
                if (!TextUtils.equals(bundle.getString("type", ""), PluginLockStar.STATUS_BAR_TYPE)) {
                    return null;
                }
                KeyguardStatusBarViewController.this.updateViewState();
                return null;
            }
        };
        this.mDesktopCallback = new DesktopManager.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.8
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                int i3 = 0;
                if (semDesktopModeState.getState() == 50 && semDesktopModeState.getDisplayType() == 101) {
                    boolean z = semDesktopModeState.getEnabled() == 4;
                    Log.d("KeyguardStatusBarViewController", "Set keyguard status icons visibility (" + (true ^ z) + ") ");
                    AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                    keyguardStatusBarViewController.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda16(keyguardStatusBarViewController, z, i3));
                }
            }
        };
        this.mHiddenByKnox = false;
        this.mOngoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.9
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                View findViewById = ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).findViewById(R.id.keyguard_ongoing_call_chip);
                View findViewById2 = findViewById.findViewById(R.id.ongoing_call_chip);
                OngoingCallController ongoingCallController2 = keyguardStatusBarViewController.mOngoingCallController;
                findViewById2.setVisibility(ongoingCallController2.hasOngoingCall() ? 0 : 8);
                findViewById.setVisibility(ongoingCallController2.hasOngoingCall() ? 0 : 8);
            }
        };
        this.mVolumeSettingObserver = new ContentObserver(null) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.12
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardStatusBarViewController.this.updateBlockedIcons();
            }
        };
        this.mEssentialLeftWidth = -1;
        this.mGardener = new IndicatorBasicGardener(this, "KeyguardStatusBarViewController") { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.13
            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams() {
                return (ViewGroup.MarginLayoutParams) KeyguardStatusBarViewController.this.getSidePaddingContainer().getLayoutParams();
            }
        };
        this.mCarrierTextController = carrierTextController;
        this.mConfigurationController = configurationController;
        this.mAnimationScheduler = systemStatusAnimationScheduler;
        this.mBatteryController = batteryController;
        this.mUserInfoController = userInfoController;
        this.mStatusBarIconController = statusBarIconController;
        this.mTintedIconManagerFactory = factory;
        this.mBatteryMeterViewController = batteryMeterViewController;
        this.mShadeViewStateProvider = shadeViewStateProvider;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mUserManager = userManager;
        this.mStatusBarUserChipViewModel = statusBarUserChipViewModel;
        this.mSecureSettings = secureSettings;
        this.mCommandQueue = commandQueue;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mLogger = keyguardLogger;
        this.mCommunalSceneInteractor = communalSceneInteractor;
        this.mStatusBarState = ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.mKnoxStatusBarControlViewModel = knoxStatusBarControlViewModel;
        knoxStatusBarControlViewModel.setHidden = new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(this, 0);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedViewController = netspeedViewController;
        }
        this.mStatusIconContainerController = statusIconContainerController;
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            statusIconContainerController.view.mSidelingCutoutContainerInfo = new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this);
        }
        this.mOngoingCallController = ongoingCallController;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mFirstBypassAttempt = keyguardBypassController.getBypassEnabled();
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.10
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mKeyguardFadingAway) {
                    return;
                }
                keyguardStatusBarViewController.mFirstBypassAttempt = false;
                keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = false;
            }
        });
        Resources resources = getResources();
        updateBlockedIcons();
        this.mNotificationsHeaderCollideDistance = resources.getDimensionPixelSize(R.dimen.header_notifications_collide_distance);
        KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) this.mView;
        keyguardStatusBarView2.mKeyguardUserAvatarEnabled = !statusBarUserChipViewModel.chipEnabled;
        keyguardStatusBarView2.updateVisibilities();
        this.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(this, i2), new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(this, i), false);
        this.mDisableStateTracker = new DisableStateTracker(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, 2, new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this));
        this.mTwoPhoneModeController = twoPhoneModeIconController;
        this.mKeyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        dumpManager.registerDumpable(this);
        this.mDesktopManager = desktopManager;
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mSlimIndicatorKeyguardCarrierTextHelper = slimIndicatorKeyguardCarrierTextHelper;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mPluginLockMediator = pluginLockMediator;
        this.mPluginLockStarManager = pluginLockStarManager;
    }

    public static void isMigrationEnabled() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
    }

    public final void animateKeyguardStatusBarIn() {
        isMigrationEnabled();
        this.mLogger.buffer.log("KeyguardStatusBarViewController", LogLevel.DEBUG, "animating status bar in", null);
        if (this.mDisableStateTracker.isDisabled) {
            return;
        }
        ((KeyguardStatusBarView) this.mView).setVisibility(0);
        ((KeyguardStatusBarView) this.mView).setAlpha(0.0f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(this.mAnimatorUpdateListener);
        ofFloat.setDuration(360L);
        ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN);
        ofFloat.start(false);
    }

    public final void animateKeyguardStatusBarOut(long j, long j2) {
        isMigrationEnabled();
        this.mLogger.buffer.log("KeyguardStatusBarViewController", LogLevel.DEBUG, "animating status bar out", null);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(((KeyguardStatusBarView) this.mView).getAlpha(), 0.0f);
        ofFloat.addUpdateListener(this.mAnimatorUpdateListener);
        ofFloat.setStartDelay(j);
        ofFloat.setDuration(j2);
        ofFloat.setInterpolator(InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.11
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).setVisibility(4);
                ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).setAlpha(1.0f);
                keyguardStatusBarViewController.mKeyguardStatusBarAnimateAlpha = 1.0f;
            }
        });
        ofFloat.start(false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardStatusBarView:", "  mBatteryListening: "), this.mBatteryListening, printWriter, "  mExplicitAlpha: "), this.mExplicitAlpha, printWriter, "  alpha: ");
        m.append(((KeyguardStatusBarView) this.mView).getAlpha());
        printWriter.println(m.toString());
        printWriter.println("  visibility: " + ((KeyguardStatusBarView) this.mView).getVisibility());
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mHiddenByKnox: "), this.mHiddenByKnox, printWriter);
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        keyguardStatusBarView.getClass();
        printWriter.println("KeyguardStatusBarView:");
        printWriter.println("  mBatteryCharging: " + keyguardStatusBarView.mBatteryCharging);
        printWriter.println("  mLayoutState: 0");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mKeyguardUserSwitcherEnabled: "), keyguardStatusBarView.mKeyguardUserSwitcherEnabled, printWriter, "  mHiddenByDeX: "), keyguardStatusBarView.mHiddenByDeX, printWriter);
        BatteryMeterView batteryMeterView = keyguardStatusBarView.mBatteryView;
        if (batteryMeterView != null) {
            batteryMeterView.dump(printWriter, strArr);
        }
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarWallpaperHelper.getClass();
        printWriter.println();
        printWriter.println(" KeyguardStatusBarWallpaperHelper");
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "   fontColorFromWallPaper=", Integer.toHexString(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper));
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("   fontColorType=", keyguardStatusBarWallpaperHelper.fontColorType, printWriter);
        printWriter.println("   intensity=" + keyguardStatusBarWallpaperHelper.intensity);
        this.mCarrierTextController.dump(printWriter);
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.mStatusIconContainerController.dump(printWriter);
        }
    }

    public List<String> getBlockedIcons() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mBlockedIcons);
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getCenterContainer() {
        return null;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialLeftWidth() {
        if (this.mEssentialLeftWidth < 0) {
            this.mEssentialLeftWidth = getResources().getDimensionPixelSize(R.dimen.carrier_label_portrait_max_width);
        }
        return this.mEssentialLeftWidth;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialRightWidth() {
        View findViewById;
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) this.mView).findViewById(R.id.battery);
        int i = 0;
        int measuredWidth = (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (findViewById = ((KeyguardStatusBarView) this.mView).findViewById(R.id.networkSpeed)) != null && findViewById.getVisibility() == 0) ? findViewById.getMeasuredWidth() : 0;
        TwoPhoneModeIconController twoPhoneModeIconController = this.mTwoPhoneModeController;
        if (twoPhoneModeIconController.featureEnabled() && twoPhoneModeIconController.getViewWidth() > 0) {
            i = twoPhoneModeIconController.getViewWidth();
        }
        return batteryMeterView.getMeasuredWidth() + measuredWidth + i;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final WindowInsets getGardenWindowInsets() {
        return ((KeyguardStatusBarView) this.mView).getRootWindowInsets();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getHeightContainer() {
        return (ViewGroup) this.mView;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getLeftContainer() {
        if (this.mGardenLeftContainer == null) {
            this.mGardenLeftContainer = (IndicatorGardenContainer) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_left_container);
        }
        return this.mGardenLeftContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getRightContainer() {
        if (this.mStatusIconArea == null) {
            this.mStatusIconArea = (IndicatorGardenContainer) ((KeyguardStatusBarView) this.mView).findViewById(R.id.status_icon_area);
        }
        return this.mStatusIconArea;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getSidePaddingContainer() {
        if (this.mKeyguardStatusBarAreaView == null) {
            this.mKeyguardStatusBarAreaView = (ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_status_bar_container);
        }
        return this.mKeyguardStatusBarAreaView;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.mCarrierTextController.init();
        this.mBatteryMeterViewController.init();
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedViewController.init();
        }
        isMigrationEnabled();
        this.mStatusIconContainerController.init();
        ViewGroup viewGroup = (ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_notification_icon_container);
        this.mKeyguardStatusBarNotifContainer = viewGroup;
        this.mNotificationIconAreaController.setKeyguardNotifIcon((NotificationIconContainer) viewGroup.findViewById(R.id.notificationIcons));
        this.mKeyguardStatusBarNotifContainer.setVisibility(0);
    }

    public final void onThemeChanged() {
        ((KeyguardStatusBarView) this.mView).onThemeChanged(this.mTintedIconManager);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0] */
    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        OngoingCallChronometer ongoingCallChronometer;
        final KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        StatusBarUserChipViewBinder.bind(keyguardStatusBarView.mUserSwitcherContainer, this.mStatusBarUserChipViewModel, new Function1() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Integer) obj).intValue();
                KeyguardStatusBarView keyguardStatusBarView2 = KeyguardStatusBarView.this;
                keyguardStatusBarView2.mUserCount = intValue;
                keyguardStatusBarView2.updateVisibilities();
                return Unit.INSTANCE;
            }
        });
        ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.mConfigurationController;
        AnonymousClass1 anonymousClass1 = this.mConfigurationListener;
        configurationControllerImpl.addCallback(anonymousClass1);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this.mAnimationCallback);
        ((UserInfoControllerImpl) this.mUserInfoController).addCallback(this.mOnUserInfoChangedListener);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.addCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        Integer valueOf = Integer.valueOf(((KeyguardStatusBarView) this.mView).getDisplay().getDisplayId());
        DisableStateTracker disableStateTracker = this.mDisableStateTracker;
        disableStateTracker.displayId = valueOf;
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) disableStateTracker);
        if (this.mTintedIconManager == null) {
            TintedIconManager create = this.mTintedIconManagerFactory.create((ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.statusIcons), StatusBarLocation.KEYGUARD);
            this.mTintedIconManager = create;
            ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(create);
        } else {
            isMigrationEnabled();
        }
        this.mSystemIconsContainer = ((KeyguardStatusBarView) this.mView).findViewById(R.id.system_icons);
        ((KeyguardStatusBarView) this.mView).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return KeyguardStatusBarViewController.$r8$lambda$7w5SQ8EAIsB6phwkiU0XFhO16b0(KeyguardStatusBarViewController.this, windowInsets);
            }
        });
        this.mIndicatorGardenPresenter.updateGardenWithNewModel(this);
        final int i = 0;
        ((KeyguardStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                int i10 = i;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                switch (i10) {
                    case 0:
                        if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                            IndicatorGardenPresenter indicatorGardenPresenter = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                            indicatorGardenPresenter.getClass();
                            indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, keyguardStatusBarViewController));
                            break;
                        }
                        break;
                    default:
                        KeyguardStatusBarViewController.$r8$lambda$wGlIvEVAPs5MJk9Zk5mL4Xyfw3c(keyguardStatusBarViewController);
                        break;
                }
            }
        });
        this.mSecureSettings.registerContentObserverForUserSync("status_bar_show_vibrate_icon", false, (ContentObserver) this.mVolumeSettingObserver, -1);
        updateUserSwitcher();
        onThemeChanged();
        JavaAdapterKt.collectFlow(this.mView, this.mCommunalSceneInteractor.isCommunalVisible, this.mCommunalConsumer);
        this.mStatusBarState = statusBarStateControllerImpl.mState;
        KnoxStatusBarControlBinder.bind(this.mKnoxStatusBarControlViewModel, (KnoxStatusBarViewControl) this.mView);
        OngoingCallController ongoingCallController = this.mOngoingCallController;
        ongoingCallController.addCallback((OngoingCallListener) this.mOngoingCallListener);
        View findViewById = ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_ongoing_call_chip);
        KeyguardCallChipController keyguardCallChipController = ongoingCallController.keyguardCallChipController;
        View view = keyguardCallChipController.chipView;
        if (view != null && (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) != null) {
            ongoingCallChronometer.stop();
        }
        keyguardCallChipController.chipView = findViewById;
        KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) this.mView;
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarView2.mKeyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        KeyguardStatusBarViewController$$ExternalSyntheticLambda2 keyguardStatusBarViewController$$ExternalSyntheticLambda2 = new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this);
        keyguardStatusBarWallpaperHelper.wakefulnessLifecycle.addObserver(keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.wallpaperEventNotifier.registerCallback(false, keyguardStatusBarWallpaperHelper, 17L);
        keyguardStatusBarWallpaperHelper.listener = keyguardStatusBarViewController$$ExternalSyntheticLambda2;
        DesktopManager desktopManager = this.mDesktopManager;
        desktopManager.registerCallback(this.mDesktopCallback);
        if (desktopManager.getSemDesktopModeState() != null && desktopManager.getSemDesktopModeState().getDisplayType() == 101) {
            Log.d("KeyguardStatusBarViewController", "Set keyguard status icons invisible from the beginning");
            this.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda16(this, true, 0));
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            final int i2 = 1;
            ((ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.system_icons)).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardStatusBarViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i22, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    int i10 = i2;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                    switch (i10) {
                        case 0:
                            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                                IndicatorGardenPresenter indicatorGardenPresenter = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                                indicatorGardenPresenter.getClass();
                                indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, keyguardStatusBarViewController));
                                break;
                            }
                            break;
                        default:
                            KeyguardStatusBarViewController.$r8$lambda$wGlIvEVAPs5MJk9Zk5mL4Xyfw3c(keyguardStatusBarViewController);
                            break;
                    }
                }
            });
        }
        anonymousClass1.onDensityOrFontScaleChanged();
        CarrierText carrierText = (CarrierText) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_carrier_text);
        StringBuilder sb = new StringBuilder("attach() mCarrierTextView:");
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextHelper;
        sb.append(slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView);
        sb.append(", view:");
        sb.append(carrierText);
        sb.append(", mOriginalVisibility:");
        RecyclerView$$ExternalSyntheticOutline0.m(slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper", sb);
        if (carrierText != null) {
            slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView = carrierText;
            slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility = carrierText.getVisibility();
            carrierText.mSlimIndicatorKeyguardCarrierTextInterface = slimIndicatorKeyguardCarrierTextHelper;
            ((SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator).registerSubscriber("KeyguardStatusBarCarrierText", slimIndicatorKeyguardCarrierTextHelper);
        }
        ((SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator).registerSubscriber("KeyguardStatusBarViewController", new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
            public final void updateQuickStarStyle() {
                KeyguardStatusBarViewController.m2231$r8$lambda$xz00Wgoom64_pyrJyCsnl8d0lo(KeyguardStatusBarViewController.this);
            }
        });
        this.mPluginLockStarManager.registerCallback(PluginLockStar.STATUS_BAR_TYPE, this.mPluginLockStarCallback);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mSystemIconsContainer.setOnHoverListener(null);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).removeCallback(this.mAnimationCallback);
        ((UserInfoControllerImpl) this.mUserInfoController).removeCallback(this.mOnUserInfoChangedListener);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        DisableStateTracker disableStateTracker = this.mDisableStateTracker;
        disableStateTracker.displayId = null;
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) disableStateTracker);
        this.mSecureSettings.unregisterContentObserverSync(this.mVolumeSettingObserver);
        TintedIconManager tintedIconManager = this.mTintedIconManager;
        if (tintedIconManager != null) {
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) this.mStatusBarIconController;
            statusBarIconControllerImpl.getClass();
            tintedIconManager.destroy();
            statusBarIconControllerImpl.mIconGroups.remove(tintedIconManager);
        }
        this.mOngoingCallController.removeCallback((OngoingCallListener) this.mOngoingCallListener);
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarWallpaperHelper.wakefulnessLifecycle.removeObserver(keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.wallpaperEventNotifier.removeCallback(false, keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.listener = null;
        this.mDesktopManager.unregisterCallback(this.mDesktopCallback);
        StringBuilder sb = new StringBuilder("detach() mCarrierTextView:");
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextHelper;
        sb.append(slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView);
        sb.append(", mOriginalVisibility:");
        RecyclerView$$ExternalSyntheticOutline0.m(slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper", sb);
        slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView = null;
        ((SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator).unregisterSubscriber("KeyguardStatusBarCarrierText");
        ((SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator).unregisterSubscriber("KeyguardStatusBarViewController");
    }

    public final void setKeyguardUserSwitcherEnabled(boolean z) {
        isMigrationEnabled();
        ((KeyguardStatusBarView) this.mView).mKeyguardUserSwitcherEnabled = z;
    }

    public void updateBlockedIcons() {
        Resources resources = getResources();
        SecureSettings secureSettings = this.mSecureSettings;
        ArraysKt___ArraysKt.toList(resources.getStringArray(R.array.config_collapsed_statusbar_icon_blocklist));
        resources.getString(17043164);
        secureSettings.getIntForUser("status_bar_show_vibrate_icon", 0, -2);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            ((ArrayList) this.mBlockedIcons).clear();
            ((ArrayList) this.mBlockedIcons).addAll(arrayList);
        }
        this.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda5(this, 0));
    }

    public void updateForHeadsUp(boolean z) {
        boolean z2 = this.mStatusBarState == 1 && this.mShadeViewStateProvider.shouldHeadsUpBeVisible();
        if (this.mShowingKeyguardHeadsUp != z2) {
            this.mShowingKeyguardHeadsUp = z2;
            boolean z3 = this.mStatusBarState == 1;
            AnimatableProperty.AnonymousClass6 anonymousClass6 = this.mHeadsUpShowingAmountAnimation;
            if (z3) {
                PropertyAnimator.setProperty((KeyguardStatusBarView) this.mView, anonymousClass6, z2 ? 1.0f : 0.0f, KEYGUARD_HUN_PROPERTIES, z);
                return;
            }
            KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
            PropertyAnimator.cancelAnimation(keyguardStatusBarView, anonymousClass6);
            anonymousClass6.val$property.set(keyguardStatusBarView, Float.valueOf(0.0f));
        }
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        updateGarden(indicatorGardenModel, indicatorGardenInputProperties);
    }

    public final void updateTopClipping(int i) {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        int top = i - keyguardStatusBarView.getTop();
        if (top != keyguardStatusBarView.mTopClipping) {
            keyguardStatusBarView.mTopClipping = top;
            keyguardStatusBarView.mClipRect.set(0, top, keyguardStatusBarView.getWidth(), keyguardStatusBarView.getHeight());
            keyguardStatusBarView.setClipBounds(keyguardStatusBarView.mClipRect);
        }
    }

    public final void updateUserSwitcher() {
        Flags.FEATURE_FLAGS.getClass();
        this.mBackgroundExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda5(this, 1));
    }

    public final void updateViewState(float f, int i) {
        isMigrationEnabled();
        if (this.mDisableStateTracker.isDisabled) {
            i = 4;
        }
        float f2 = this.mDraggedFraction;
        if (f2 > 0.0f) {
            ((KeyguardStatusBarView) this.mView).setAlpha(Math.max(0.0f, 1.0f - (f2 * 5.0f)));
        } else {
            ((KeyguardStatusBarView) this.mView).setAlpha(f);
            ((KeyguardStatusBarView) this.mView).setVisibility(i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x011f, code lost:
    
        if (r1 == false) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateViewState() {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.updateViewState():void");
    }
}
