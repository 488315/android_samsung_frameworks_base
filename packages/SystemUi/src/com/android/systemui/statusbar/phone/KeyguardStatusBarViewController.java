package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.UserManager;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.CarrierText;
import com.android.keyguard.CarrierTextController;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogLevel;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewStateProvider;
import com.android.systemui.slimindicator.SlimIndicatorKeyguardCarrierTextHelper;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator;
import com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.ongoingcall.KeyguardCallChipController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallChronometer;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardStatusBarViewController extends ViewController implements IndicatorGarden, Dumpable {
    public static final AnimationProperties KEYGUARD_HUN_PROPERTIES;
    public final C30592 mAnimationCallback;
    public final SystemStatusAnimationScheduler mAnimationScheduler;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda7 mAnimatorUpdateListener;
    public final BatteryController mBatteryController;
    public boolean mBatteryListening;
    public final BatteryMeterViewController mBatteryMeterViewController;
    public final C30603 mBatteryStateChangeCallback;
    public final BiometricUnlockController mBiometricUnlockController;
    public final List mBlockedIcons;
    public final CarrierTextController mCarrierTextController;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final C30551 mConfigurationListener;
    public boolean mDelayShowingKeyguardStatusBar;
    public final C30658 mDesktopCallback;
    public final DesktopManager mDesktopManager;
    public final DisableStateTracker mDisableStateTracker;
    public boolean mDozing;
    public int mEssentialLeftWidth;
    public float mExplicitAlpha;
    public boolean mFirstBypassAttempt;
    public IndicatorGardenContainer mGardenLeftContainer;
    public final C305812 mGardener;
    public final AnimatableProperty.C27056 mHeadsUpShowingAmountAnimation;
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
    public final KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KnoxStatusBarControlViewModel mKnoxStatusBarControlViewModel;
    public final Object mLock;
    public final KeyguardLogger mLogger;
    public final Executor mMainExecutor;
    public final NetspeedViewController mNetspeedViewController;
    public final NotificationMediaManager mNotificationMediaManager;
    public final int mNotificationsHeaderCollideDistance;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda6 mOnUserInfoChangedListener;
    public final OngoingCallController mOngoingCallController;
    public final C30647 mOngoingCallListener;
    public final PluginLockMediator mPluginLockMediator;
    public final C30636 mPluginLockStatusBarCallback;
    public final SecureSettings mSecureSettings;
    public final ShadeViewStateProvider mShadeViewStateProvider;
    public boolean mShowingKeyguardHeadsUp;
    public final SlimIndicatorKeyguardCarrierTextHelper mSlimIndicatorKeyguardCarrierTextHelper;
    public final StatusBarIconController mStatusBarIconController;
    public int mStatusBarState;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final C30625 mStatusBarStateListener;
    public final StatusBarUserChipViewModel mStatusBarUserChipViewModel;
    public IndicatorGardenContainer mStatusIconArea;
    public ViewGroup mStatusIconAreaView;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda8 mStatusIconContainerCallback;
    public final StatusIconContainerController mStatusIconContainerController;
    public StatusBarSystemEventDefaultAnimator mSystemEventAnimator;
    public float mSystemEventAnimatorAlpha;
    public StatusBarIconController.TintedIconManager mTintedIconManager;
    public final StatusBarIconController.TintedIconManager.Factory mTintedIconManagerFactory;
    public final TwoPhoneModeIconController mTwoPhoneModeController;
    public final UserInfoController mUserInfoController;
    public final UserManager mUserManager;
    public final C305711 mVolumeSettingObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$1 */
    public final class C30551 implements ConfigurationController.ConfigurationListener {
        public C30551() {
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
            AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).loadDimens();
            keyguardStatusBarViewController.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(keyguardStatusBarViewController.getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda9(keyguardStatusBarViewController, 1), new KeyguardStatusBarViewController$$ExternalSyntheticLambda9(keyguardStatusBarViewController, 2), keyguardStatusBarViewController.mSystemEventAnimator.isAnimationRunning);
            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                IndicatorGardenInputProperties indicatorGardenInputProperties = keyguardStatusBarViewController.mIndicatorGardenPresenter.inputProperties;
                indicatorGardenInputProperties.updateWindowMetrics();
                indicatorGardenInputProperties.updatePaddingValues();
            }
            if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
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
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) KeyguardStatusBarViewController.this.mView;
                if (keyguardStatusBarView.mKeyguardUserSwitcherEnabled) {
                    return;
                }
                keyguardStatusBarView.mMultiUserAvatar.setContentDescription(keyguardStatusBarView.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView.mMultiUserName));
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).onOverlayChanged();
            ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).onThemeChanged(keyguardStatusBarViewController.mTintedIconManager);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$6 */
    public final class C30636 {
        public C30636() {
        }

        public final void onVisibilityUpdated(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onVisibilityUpdated() ", i, ", ", i2, "KeyguardStatusBarViewController");
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            if (keyguardStatusBarViewController.mStatusIconAreaView == null) {
                keyguardStatusBarViewController.mStatusIconAreaView = (ViewGroup) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.status_icon_area);
            }
            if (keyguardStatusBarViewController.mKeyguardLeftSideContainerView == null) {
                keyguardStatusBarViewController.mKeyguardLeftSideContainerView = (ViewGroup) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.keyguard_left_container);
            }
            ViewGroup viewGroup = keyguardStatusBarViewController.mStatusIconAreaView;
            if (viewGroup == null || keyguardStatusBarViewController.mKeyguardLeftSideContainerView == null) {
                Log.e("KeyguardStatusBarViewController", "onVisibilityUpdated() no views ");
                return;
            }
            if (i != -1 && i != viewGroup.getVisibility()) {
                keyguardStatusBarViewController.mStatusIconAreaView.setVisibility(i);
            }
            if (i2 == -1 || i2 == keyguardStatusBarViewController.mKeyguardLeftSideContainerView.getVisibility()) {
                return;
            }
            keyguardStatusBarViewController.mKeyguardLeftSideContainerView.setVisibility(i2);
        }
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360L;
        KEYGUARD_HUN_PROPERTIES = animationProperties;
    }

    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$5] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$2] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$3] */
    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$7] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$8] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$11] */
    /* JADX WARN: Type inference failed for: r6v7, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$12] */
    public KeyguardStatusBarViewController(KeyguardStatusBarView keyguardStatusBarView, CarrierTextController carrierTextController, ConfigurationController configurationController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, BatteryController batteryController, UserInfoController userInfoController, StatusBarIconController statusBarIconController, StatusBarIconController.TintedIconManager.Factory factory, BatteryMeterViewController batteryMeterViewController, ShadeViewStateProvider shadeViewStateProvider, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, BiometricUnlockController biometricUnlockController, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, UserManager userManager, StatusBarUserChipViewModel statusBarUserChipViewModel, SecureSettings secureSettings, CommandQueue commandQueue, Executor executor, KeyguardLogger keyguardLogger, NotificationMediaManager notificationMediaManager, IndicatorGardenPresenter indicatorGardenPresenter, PluginLockMediator pluginLockMediator, NetspeedViewController netspeedViewController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, StatusIconContainerController statusIconContainerController, OngoingCallController ongoingCallController, KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper, DumpManager dumpManager, DesktopManager desktopManager, TwoPhoneModeIconController twoPhoneModeIconController, SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil) {
        super(keyguardStatusBarView);
        this.mKeyguardHeadsUpShowingAmount = 0.0f;
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mKeyguardHeadsUpShowingAmount = ((Float) obj2).floatValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        Function function = new Function() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(KeyguardStatusBarViewController.this.mKeyguardHeadsUpShowingAmount);
            }
        };
        AnimatableProperty.C27067 c27067 = AnimatableProperty.f351Y;
        this.mHeadsUpShowingAmountAnimation = new AnimatableProperty.C27056(R.id.keyguard_hun_animator_end_tag, R.id.keyguard_hun_animator_start_tag, R.id.keyguard_hun_animator_tag, new AnimatableProperty.C27045("KEYGUARD_HEADS_UP_SHOWING_AMOUNT", function, biConsumer));
        this.mLock = new Object();
        this.mConfigurationListener = new C30551();
        this.mAnimationCallback = new SystemStatusAnimationCallback(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.2
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationBegin(boolean z) {
                return new AnimatorSet();
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
                return new AnimatorSet();
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) KeyguardStatusBarViewController.this.mView;
                if (keyguardStatusBarView2.mBatteryCharging != z2) {
                    keyguardStatusBarView2.mBatteryCharging = z2;
                    keyguardStatusBarView2.updateVisibilities();
                }
            }
        };
        this.mOnUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda6
            @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
            public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) KeyguardStatusBarViewController.this.mView;
                keyguardStatusBarView2.mMultiUserAvatar.setImageDrawable(drawable);
                if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                    keyguardStatusBarView2.mMultiUserName = str;
                    if (keyguardStatusBarView2.mKeyguardUserSwitcherEnabled) {
                        return;
                    }
                    keyguardStatusBarView2.mMultiUserAvatar.setContentDescription(keyguardStatusBarView2.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView2.mMultiUserName));
                }
            }
        };
        this.mAnimatorUpdateListener = new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda7
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
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (keyguardStatusBarViewController.mFirstBypassAttempt && keyguardStatusBarViewController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                    keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = true;
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                int i = keyguardStatusBarViewController.mStatusBarState;
                boolean z2 = true;
                if (i != 1 && i != 2) {
                    z2 = false;
                }
                if (z || !keyguardStatusBarViewController.mFirstBypassAttempt || !z2 || keyguardStatusBarViewController.mDozing || keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar || keyguardStatusBarViewController.mBiometricUnlockController.isBiometricUnlock()) {
                    return;
                }
                keyguardStatusBarViewController.mFirstBypassAttempt = false;
                keyguardStatusBarViewController.animateKeyguardStatusBarIn();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
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
            public final void onStateChanged(int i) {
                KeyguardStatusBarViewController.this.mStatusBarState = i;
            }
        };
        this.mBlockedIcons = new ArrayList();
        this.mKeyguardStatusBarAnimateAlpha = 1.0f;
        this.mSystemEventAnimatorAlpha = 1.0f;
        this.mExplicitAlpha = -1.0f;
        this.mPluginLockStatusBarCallback = new C30636();
        this.mHiddenByKnox = false;
        this.mOngoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.7
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.keyguard_ongoing_call_chip).setVisibility(keyguardStatusBarViewController.mOngoingCallController.hasOngoingCall() ? 0 : 8);
            }
        };
        this.mDesktopCallback = new DesktopManager.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.8
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                if (semDesktopModeState.getState() == 50 && semDesktopModeState.getDisplayType() == 101) {
                    boolean z = semDesktopModeState.getEnabled() == 4;
                    StringBuilder sb = new StringBuilder("Set keyguard status icons visibility (");
                    sb.append(!z);
                    sb.append(") ");
                    Log.d("KeyguardStatusBarViewController", sb.toString());
                    AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                    keyguardStatusBarViewController.getClass();
                    keyguardStatusBarViewController.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(keyguardStatusBarViewController, z));
                }
            }
        };
        this.mStatusIconContainerCallback = new IndicatorGardenPresenter.StatusIconContainerCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.statusbar.phone.IndicatorGardenPresenter.StatusIconContainerCallback
            public final void updateStatusIconContainer() {
                ((KeyguardStatusBarView) KeyguardStatusBarViewController.this.mView).findViewById(R.id.statusIcons).requestLayout();
            }
        };
        this.mVolumeSettingObserver = new ContentObserver(null) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.11
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardStatusBarViewController.this.updateBlockedIcons();
            }
        };
        this.mEssentialLeftWidth = -1;
        this.mGardener = new IndicatorBasicGardener(this, "KeyguardStatusBarViewController") { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.12
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
        this.mInsetsProvider = statusBarContentInsetsProvider;
        this.mUserManager = userManager;
        this.mStatusBarUserChipViewModel = statusBarUserChipViewModel;
        this.mSecureSettings = secureSettings;
        this.mCommandQueue = commandQueue;
        this.mMainExecutor = executor;
        this.mLogger = keyguardLogger;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedViewController = netspeedViewController;
        }
        this.mKnoxStatusBarControlViewModel = knoxStatusBarControlViewModel;
        knoxStatusBarControlViewModel.setHidden = new KeyguardStatusBarViewController$$ExternalSyntheticLambda9(this, 0);
        this.mStatusIconContainerController = statusIconContainerController;
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            statusIconContainerController.view.mSidelingCutoutContainerInfo = new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this);
        }
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mFirstBypassAttempt = keyguardBypassController.getBypassEnabled();
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.9
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
        this.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda9(this, 1), new KeyguardStatusBarViewController$$ExternalSyntheticLambda9(this, 2), false);
        this.mDisableStateTracker = new DisableStateTracker(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, 2, new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this));
        this.mNotificationMediaManager = notificationMediaManager;
        this.mPluginLockMediator = pluginLockMediator;
        this.mOngoingCallController = ongoingCallController;
        this.mKeyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        dumpManager.registerDumpable(this);
        this.mDesktopManager = desktopManager;
        this.mTwoPhoneModeController = twoPhoneModeIconController;
        this.mSlimIndicatorKeyguardCarrierTextHelper = slimIndicatorKeyguardCarrierTextHelper;
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
    }

    public final void animateKeyguardStatusBarIn() {
        this.mLogger.buffer.log("KeyguardStatusBarViewController", LogLevel.DEBUG, "animating status bar in", (Throwable) null);
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

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m81m = LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "KeyguardStatusBarView:", "  mBatteryListening: "), this.mBatteryListening, printWriter, "  mExplicitAlpha: "), this.mExplicitAlpha, printWriter, "  alpha: ");
        m81m.append(((KeyguardStatusBarView) this.mView).getAlpha());
        printWriter.println(m81m.toString());
        printWriter.println("  visibility: " + ((KeyguardStatusBarView) this.mView).getVisibility());
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("  mHiddenByKnox: "), this.mHiddenByKnox, printWriter);
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        keyguardStatusBarView.getClass();
        printWriter.println("KeyguardStatusBarView:");
        printWriter.println("  mBatteryCharging: " + keyguardStatusBarView.mBatteryCharging);
        printWriter.println("  mLayoutState: 0");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mKeyguardUserSwitcherEnabled: "), keyguardStatusBarView.mKeyguardUserSwitcherEnabled, printWriter, "  mHiddenByDeX: "), keyguardStatusBarView.mHiddenByDeX, printWriter);
        BatteryMeterView batteryMeterView = keyguardStatusBarView.mBatteryView;
        if (batteryMeterView != null) {
            SwitchableDoubleShadowTextView switchableDoubleShadowTextView = batteryMeterView.mBatteryPercentView;
            CharSequence text = switchableDoubleShadowTextView == null ? null : switchableDoubleShadowTextView.getText();
            printWriter.println("  BatteryMeterView:");
            printWriter.println("    mDrawable.getPowerSave: null");
            printWriter.println("    mBatteryPercentView.getText(): " + ((Object) text));
            printWriter.println("    mTextColor: #" + Integer.toHexString(batteryMeterView.mTextColor));
            printWriter.println("    mBatteryStateUnknown: false");
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("    mLevel: "), batteryMeterView.mLevel, printWriter, "    mMode: "), batteryMeterView.mShowPercentMode, printWriter, "    mShowPercentSamsungSetting: "), batteryMeterView.mShowPercentSamsungSetting, printWriter, "    mIsDirectPowerMode: "), batteryMeterView.mIsDirectPowerMode, printWriter);
        }
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarWallpaperHelper.getClass();
        printWriter.println();
        printWriter.println(" KeyguardStatusBarWallpaperHelper");
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("   fontColorFromWallPaper=", Integer.toHexString(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper), printWriter);
        SideFpsController$$ExternalSyntheticOutline0.m105m("   fontColorType=", keyguardStatusBarWallpaperHelper.fontColorType, printWriter);
        printWriter.println("   intensity=" + keyguardStatusBarWallpaperHelper.intensity);
        CarrierTextController carrierTextController = this.mCarrierTextController;
        carrierTextController.getClass();
        printWriter.println("CarrierTextController:");
        KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("    last: "), carrierTextController.mLastScaleEvent, printWriter);
        CarrierText carrierText = (CarrierText) carrierTextController.mView;
        carrierText.getClass();
        printWriter.println("CarrierText:");
        StringBuilder sb = new StringBuilder("    textSize: ");
        sb.append(carrierText.mFontSize);
        sb.append(" from: ");
        KeyboardUI$$ExternalSyntheticOutline0.m134m(sb, carrierText.mSetTextSizeCaller, printWriter);
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
        this.mCarrierTextController.init();
        this.mBatteryMeterViewController.init();
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedViewController.init();
        }
        this.mStatusIconContainerController.init();
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
        C30551 c30551 = this.mConfigurationListener;
        configurationControllerImpl.addCallback(c30551);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this.mAnimationCallback);
        ((UserInfoControllerImpl) this.mUserInfoController).addCallback(this.mOnUserInfoChangedListener);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        Integer valueOf = Integer.valueOf(((KeyguardStatusBarView) this.mView).getDisplay().getDisplayId());
        DisableStateTracker disableStateTracker = this.mDisableStateTracker;
        disableStateTracker.displayId = valueOf;
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) disableStateTracker);
        if (this.mTintedIconManager == null) {
            ViewGroup viewGroup = (ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.statusIcons);
            StatusBarLocation statusBarLocation = StatusBarLocation.KEYGUARD;
            StatusBarIconController.TintedIconManager.Factory factory = this.mTintedIconManagerFactory;
            StatusBarIconController.TintedIconManager tintedIconManager = new StatusBarIconController.TintedIconManager(viewGroup, statusBarLocation, factory.mStatusBarPipelineFlags, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileContextProvider, factory.mBTTetherUiAdapter);
            this.mTintedIconManager = tintedIconManager;
            ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(tintedIconManager);
        }
        ((KeyguardStatusBarView) this.mView).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                    keyguardStatusBarViewController.mIndicatorGardenPresenter.onGardenApplyWindowInsets(keyguardStatusBarViewController);
                }
                return ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).updateWindowInsets(windowInsets);
            }
        });
        IndicatorGardenPresenter indicatorGardenPresenter = this.mIndicatorGardenPresenter;
        indicatorGardenPresenter.updateGardenWithNewModel(this);
        final int i = 0;
        ((KeyguardStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                StatusIconContainer statusIconContainer;
                switch (i) {
                    case 0:
                        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                        if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                            IndicatorGardenPresenter indicatorGardenPresenter2 = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                            indicatorGardenPresenter2.getClass();
                            indicatorGardenPresenter2.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter2, keyguardStatusBarViewController));
                            break;
                        }
                        break;
                    default:
                        KeyguardStatusBarViewController keyguardStatusBarViewController2 = this.f$0;
                        if (keyguardStatusBarViewController2.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude() != null && (statusIconContainer = (StatusIconContainer) ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).findViewById(R.id.statusIcons)) != null) {
                            if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
                                statusIconContainer.requestLayout();
                                break;
                            }
                        }
                        break;
                }
            }
        });
        this.mSecureSettings.registerContentObserverForUser("status_bar_show_vibrate_icon", false, (ContentObserver) this.mVolumeSettingObserver, -1);
        updateUserSwitcher();
        ((KeyguardStatusBarView) this.mView).onThemeChanged(this.mTintedIconManager);
        ((PluginLockMediatorImpl) this.mPluginLockMediator).mStatusBarCallback = this.mPluginLockStatusBarCallback;
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
        DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) this.mDesktopManager;
        desktopManagerImpl.registerCallback(this.mDesktopCallback);
        final int i2 = 1;
        if (desktopManagerImpl.getSemDesktopModeState().getDisplayType() == 101) {
            Log.d("KeyguardStatusBarViewController", "Set keyguard status icons invisible from the beginning");
            this.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda11(this, true));
        }
        CarrierText carrierText = (CarrierText) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_carrier_text);
        StringBuilder sb = new StringBuilder("attach() mCarrierTextView:");
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextHelper;
        sb.append(slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView);
        sb.append(", view:");
        sb.append(carrierText);
        sb.append(", mOriginalVisibility:");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper");
        if (carrierText != null) {
            slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView = carrierText;
            slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility = carrierText.getVisibility();
            carrierText.mSlimIndicatorKeyguardCarrierTextInterface = slimIndicatorKeyguardCarrierTextHelper;
            ((SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator).registerSubscriber("KeyguardStatusBarCarrierText", slimIndicatorKeyguardCarrierTextHelper);
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            ((ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.system_icons)).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardStatusBarViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i22, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    StatusIconContainer statusIconContainer;
                    switch (i2) {
                        case 0:
                            KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                                IndicatorGardenPresenter indicatorGardenPresenter2 = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                                indicatorGardenPresenter2.getClass();
                                indicatorGardenPresenter2.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter2, keyguardStatusBarViewController));
                                break;
                            }
                            break;
                        default:
                            KeyguardStatusBarViewController keyguardStatusBarViewController2 = this.f$0;
                            if (keyguardStatusBarViewController2.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude() != null && (statusIconContainer = (StatusIconContainer) ((KeyguardStatusBarView) keyguardStatusBarViewController2.mView).findViewById(R.id.statusIcons)) != null) {
                                if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
                                    statusIconContainer.requestLayout();
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
        }
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            indicatorGardenPresenter.statusIconContainerCallbacks.add(this.mStatusIconContainerCallback);
        }
        c30551.onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).removeCallback(this.mAnimationCallback);
        ((UserInfoControllerImpl) this.mUserInfoController).removeCallback(this.mOnUserInfoChangedListener);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) this.mStatusBarStateListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        DisableStateTracker disableStateTracker = this.mDisableStateTracker;
        disableStateTracker.displayId = null;
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) disableStateTracker);
        this.mSecureSettings.unregisterContentObserver(this.mVolumeSettingObserver);
        StatusBarIconController.TintedIconManager tintedIconManager = this.mTintedIconManager;
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
        ((ArrayList) ((DesktopManagerImpl) this.mDesktopManager).mCallbacks).remove(this.mDesktopCallback);
        StringBuilder sb = new StringBuilder("detach() mCarrierTextView:");
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextHelper;
        sb.append(slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView);
        sb.append(", mOriginalVisibility:");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper");
        slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView = null;
        ((SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator).unregisterSubscriber("KeyguardStatusBarCarrierText");
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            this.mIndicatorGardenPresenter.statusIconContainerCallbacks.remove(this.mStatusIconContainerCallback);
        }
    }

    public void updateBlockedIcons() {
        Resources resources = getResources();
        SecureSettings secureSettings = this.mSecureSettings;
        ArraysKt___ArraysKt.toList(resources.getStringArray(R.array.config_collapsed_statusbar_icon_blocklist));
        resources.getString(17042949);
        int i = 0;
        secureSettings.getIntForUser(0, -2, "status_bar_show_vibrate_icon");
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            ((ArrayList) this.mBlockedIcons).clear();
            ((ArrayList) this.mBlockedIcons).addAll(arrayList);
        }
        this.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda3(this, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateForHeadsUp() {
        boolean z;
        if (this.mStatusBarState == 1) {
            HeadsUpAppearanceController headsUpAppearanceController = NotificationPanelViewController.this.mHeadsUpAppearanceController;
            if (headsUpAppearanceController != null && headsUpAppearanceController.shouldBeVisible()) {
                z = true;
                if (this.mShowingKeyguardHeadsUp == z) {
                    this.mShowingKeyguardHeadsUp = z;
                    boolean z2 = this.mStatusBarState == 1;
                    AnimatableProperty.C27056 c27056 = this.mHeadsUpShowingAmountAnimation;
                    if (z2) {
                        PropertyAnimator.setProperty((KeyguardStatusBarView) this.mView, c27056, z ? 1.0f : 0.0f, KEYGUARD_HUN_PROPERTIES, true);
                        return;
                    }
                    KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
                    PropertyAnimator.cancelAnimation(keyguardStatusBarView, c27056);
                    c27056.val$property.set(keyguardStatusBarView, Float.valueOf(0.0f));
                    return;
                }
                return;
            }
        }
        z = false;
        if (this.mShowingKeyguardHeadsUp == z) {
        }
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        updateGarden(indicatorGardenModel, indicatorGardenInputProperties);
    }

    public final void updateUserSwitcher() {
        ((KeyguardStatusBarView) this.mView).mIsUserSwitcherEnabled = this.mUserManager.isUserSwitcherEnabled(getResources().getBoolean(R.bool.qs_show_user_switcher_for_single_user));
    }

    public final void updateViewState() {
        float f;
        int height;
        boolean z = true;
        if (this.mStatusBarState == 1) {
            NotificationPanelViewController.C243316 c243316 = (NotificationPanelViewController.C243316) this.mShadeViewStateProvider;
            float min = 1.0f - Math.min(1.0f, NotificationPanelViewController.this.mQsController.computeExpansionFraction() * 2.0f);
            if (NotificationPanelViewController.this.mKeyguardTouchAnimator.isViRunning()) {
                return;
            }
            float f2 = this.mExplicitAlpha;
            if (f2 == -1.0f) {
                if (this.mStatusBarState == 1) {
                    f = NotificationPanelViewController.this.mExpandedHeight;
                    height = ((KeyguardStatusBarView) this.mView).getHeight() + this.mNotificationsHeaderCollideDistance;
                } else {
                    f = NotificationPanelViewController.this.mExpandedHeight;
                    height = ((KeyguardStatusBarView) this.mView).getHeight();
                }
                f2 = (1.0f - this.mKeyguardHeadsUpShowingAmount) * Math.min((float) Math.pow(MathUtils.saturate(f / height), 0.75d), min) * this.mKeyguardStatusBarAnimateAlpha;
            }
            if (!this.mSystemEventAnimator.isAnimationRunning || this.mNotificationMediaManager.isLockscreenWallpaperOnNotificationShade()) {
                ((KeyguardStatusBarView) this.mView).setTranslationX(0.0f);
            } else {
                f2 = Math.min(f2, this.mSystemEventAnimatorAlpha);
            }
            if ((!this.mFirstBypassAttempt || !this.mKeyguardUpdateMonitor.shouldListenForFace()) && !this.mDelayShowingKeyguardStatusBar) {
                z = false;
            }
            DisableStateTracker disableStateTracker = this.mDisableStateTracker;
            int i = disableStateTracker.isDisabled ? 4 : (f2 == 0.0f || this.mDozing || z || disableStateTracker.isDisabled || this.mHiddenByKnox) ? 4 : 0;
            ((KeyguardStatusBarView) this.mView).setAlpha(f2);
            ((KeyguardStatusBarView) this.mView).setVisibility(i);
        }
    }
}
