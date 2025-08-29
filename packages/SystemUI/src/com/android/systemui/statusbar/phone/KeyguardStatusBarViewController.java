package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Debug;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.ValueAnimator;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.CarrierText;
import com.android.keyguard.CarrierTextController;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToGlanceableHubTransitionViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBuffer$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
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
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.core.NewStatusBarIcons;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewControllerImpl;
import com.android.systemui.statusbar.events.SpringAnimatorSet;
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
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.ui.viewmodel.KeyguardStatusBarViewModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.ui.binder.StatusBarUserChipViewBinder;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
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
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public class KeyguardStatusBarViewController extends ViewController implements IndicatorGarden, Dumpable {
    public static final AnimationProperties KEYGUARD_HUN_PROPERTIES;
    public final AnonymousClass2 mAnimationCallback;
    public final SystemStatusAnimationScheduler mAnimationScheduler;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda8 mAnimatorUpdateListener;
    public final Executor mBackgroundExecutor;
    public final BatteryController mBatteryController;
    public boolean mBatteryListening;
    public final BatteryMeterViewController mBatteryMeterViewController;
    public final AnonymousClass3 mBatteryStateChangeCallback;
    public final BiometricUnlockController mBiometricUnlockController;
    public final List mBlockedIcons;
    public String mCallerExplicitAlpha;
    public final CarrierTextController mCarrierTextController;
    public final CommandQueue mCommandQueue;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda9 mCommunalConsumer;
    public final CommunalSceneInteractor mCommunalSceneInteractor;
    public boolean mCommunalShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final Context mContext;
    public final CoroutineDispatcher mCoroutineDispatcher;
    public boolean mDelayShowingKeyguardStatusBar;
    public final DisableStateTracker mDisableStateTracker;
    public boolean mDozing;
    public float mDraggedFraction;
    public int mEssentialLeftWidth;
    public float mExplicitAlpha;
    public boolean mFirstBypassAttempt;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda9 mFromGlanceableHubStatusBarAlphaConsumer;
    public IndicatorGardenContainer mGardenLeftContainer;
    public final AnonymousClass12 mGardener;
    public final AnimatableProperty.AnonymousClass6 mHeadsUpShowingAmountAnimation;
    public boolean mHiddenByKnox;
    public final GlanceableHubToLockscreenTransitionViewModel mHubToLockscreenTransitionViewModel;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final StatusBarContentInsetsProviderStore mInsetsProviderStore;
    public final KeyguardBypassController mKeyguardBypassController;
    public float mKeyguardHeadsUpShowingAmount;
    public ViewGroup mKeyguardLeftSideContainerView;
    public final KeyguardStateController mKeyguardStateController;
    public float mKeyguardStatusBarAnimateAlpha;
    public ViewGroup mKeyguardStatusBarAreaView;
    public final KeyguardStatusBarNioLayoutRepository mKeyguardStatusBarNioLayoutRepository;
    public ViewGroup mKeyguardStatusBarNotifContainer;
    public final KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KnoxStatusBarControlViewModel mKnoxStatusBarControlViewModel;
    public final Object mLock;
    public final LockscreenToGlanceableHubTransitionViewModel mLockscreenToHubTransitionViewModel;
    public final KeyguardLogger mLogger;
    public final Executor mMainExecutor;
    public final NetspeedViewController mNetspeedViewController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final int mNotificationsHeaderCollideDistance;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda7 mOnUserInfoChangedListener;
    public final OngoingCallController mOngoingCallController;
    public final PluginLockMediator mPluginLockMediator;
    public final AnonymousClass7 mPluginLockStarCallback;
    public final PluginLockStarManager mPluginLockStarManager;
    public final PrivacyDotViewController mPrivacyDotViewController;
    public float mRecentAlpha;
    public int mRecentVisibility;
    public final SecureSettings mSecureSettings;
    public final ShadeViewStateProvider mShadeViewStateProvider;
    public boolean mShowingKeyguardHeadsUp;
    public SlimIndicatorKeyguardCarrierTextHelper mSlimIndicatorKeyguardCarrierTextHelper;
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
    public ViewGroup mSystemIconsContainer;
    public TintedIconManager mTintedIconManager;
    public final TintedIconManager.Factory mTintedIconManagerFactory;
    public final KeyguardStatusBarViewController$$ExternalSyntheticLambda9 mToGlanceableHubStatusBarAlphaConsumer;
    public final TwoPhoneModeIconController mTwoPhoneModeController;
    public final UserInfoController mUserInfoController;
    public final UserManager mUserManager;
    public final AnonymousClass11 mVolumeSettingObserver;

    /* renamed from: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$1, reason: invalid class name */
    public class AnonymousClass1 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            keyguardStatusBarViewController.mBackgroundExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda15(keyguardStatusBarViewController, 2));
            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                keyguardStatusBarViewController.mIndicatorGardenPresenter.onGardenConfigurationChanged(keyguardStatusBarViewController, configuration);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() throws Resources.NotFoundException {
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).loadDimens();
            keyguardStatusBarViewController.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(keyguardStatusBarViewController.getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda12(keyguardStatusBarViewController, 1), new KeyguardStatusBarViewController$$ExternalSyntheticLambda12(keyguardStatusBarViewController, 2), keyguardStatusBarViewController.mSystemEventAnimator.isAnimationRunning);
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
        public final void onDisplayDeviceTypeChanged() throws Resources.NotFoundException {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                onDensityOrFontScaleChanged();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
                KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) ((ViewController) KeyguardStatusBarViewController.this).mView;
                keyguardStatusBarView.getClass();
                keyguardStatusBarView.mMultiUserAvatar.setContentDescription(keyguardStatusBarView.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView.mMultiUserName));
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() throws Resources.NotFoundException {
            KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
            ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).onOverlayChanged(keyguardStatusBarViewController.mIndicatorScaleGardener.getLatestScaleModel(keyguardStatusBarViewController.getContext()).ratio);
            keyguardStatusBarViewController.onThemeChanged();
        }
    }

    public static void $r8$lambda$LWsNDpOIXrKQmKLzeTePDCTTMzI(KeyguardStatusBarViewController keyguardStatusBarViewController) throws Resources.NotFoundException {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.isMultiUserAvatarHidden = ((SlimIndicatorViewMediatorImpl) keyguardStatusBarViewController.mSlimIndicatorViewMediator).isHiddenLockScreenMum();
        keyguardStatusBarView.updateVisibilities();
    }

    /* renamed from: $r8$lambda$SDjYX36TdSthdVP17CFw-x9k5IE, reason: not valid java name */
    public static void m3089$r8$lambda$SDjYX36TdSthdVP17CFwx9k5IE(KeyguardStatusBarViewController keyguardStatusBarViewController, String str, Drawable drawable) {
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.mMultiUserAvatar.setImageDrawable(drawable);
        if (BasicRune.STATUS_LAYOUT_MUM_ICON) {
            keyguardStatusBarView.mMultiUserName = str;
            keyguardStatusBarView.mMultiUserAvatar.setContentDescription(keyguardStatusBarView.getContext().getString(R.string.accessibility_quick_settings_user, keyguardStatusBarView.mMultiUserName));
        }
    }

    public static /* synthetic */ Unit $r8$lambda$gNuEL1YaK596T1XKmoXC3pJXlLE(KeyguardStatusBarViewController keyguardStatusBarViewController, Float f) {
        ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).setTranslationX(f.floatValue());
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void $r8$lambda$mhSmafSY8MAGHOxdxpbLCebrL0Y(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        StatusIconContainer statusIconContainer;
        if (keyguardStatusBarViewController.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude() == null || (statusIconContainer = (StatusIconContainer) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.statusIcons)) == null) {
            return;
        }
        if (statusIconContainer.getWidth() != statusIconContainer.getMeasuredWidth() || statusIconContainer.getX() < 0.0f) {
            statusIconContainer.requestLayout();
        }
    }

    public static WindowInsets $r8$lambda$uRu3BdOjY_vdtOtpXqUuJMlQ2MU(KeyguardStatusBarViewController keyguardStatusBarViewController, WindowInsets windowInsets) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        IndicatorGardenPresenter indicatorGardenPresenter = keyguardStatusBarViewController.mIndicatorGardenPresenter;
        if (z) {
            indicatorGardenPresenter.onGardenApplyWindowInsets(keyguardStatusBarViewController);
        }
        if (keyguardStateControllerImpl.mShowing) {
            ((PrivacyDotViewControllerImpl) keyguardStatusBarViewController.mPrivacyDotViewController).updateGarden(indicatorGardenPresenter.gardenAlgorithm.calculateLeftPadding(), indicatorGardenPresenter.gardenAlgorithm.calculateRightPadding(), windowInsets);
        }
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        return keyguardStatusBarView.updateWindowInsets(windowInsets);
    }

    public static int $r8$lambda$z2zgdSVI2vZwFMhzcuLHD6QN16w(KeyguardStatusBarViewController keyguardStatusBarViewController, Rect rect) {
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.battery);
        int paddingEnd = ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.statusIcons).getPaddingEnd();
        int paddingRight = keyguardStatusBarViewController.getSidePaddingContainer().getPaddingRight();
        int iWidth = keyguardStatusBarViewController.getResources().getConfiguration().windowConfiguration.getBounds().width();
        TwoPhoneModeIconController twoPhoneModeIconController = keyguardStatusBarViewController.mTwoPhoneModeController;
        return (iWidth - (((batteryMeterView.getMeasuredWidth() + paddingRight) + paddingEnd) + ((!twoPhoneModeIconController.featureEnabled() || twoPhoneModeIconController.getViewWidth() <= 0) ? 0 : twoPhoneModeIconController.getViewWidth()))) - (keyguardStatusBarViewController.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right);
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 360L;
        KEYGUARD_HUN_PROPERTIES = animationProperties;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r7v12, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9] */
    /* JADX WARN: Type inference failed for: r7v13, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9] */
    /* JADX WARN: Type inference failed for: r7v16, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$7] */
    /* JADX WARN: Type inference failed for: r7v21, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$11] */
    /* JADX WARN: Type inference failed for: r7v23, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$12] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$2] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$3] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8] */
    /* JADX WARN: Type inference failed for: r7v8, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$5] */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9] */
    public KeyguardStatusBarViewController(CoroutineDispatcher coroutineDispatcher, Context context, KeyguardStatusBarView keyguardStatusBarView, CarrierTextController carrierTextController, ConfigurationController configurationController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, BatteryController batteryController, UserInfoController userInfoController, StatusBarIconController statusBarIconController, TintedIconManager.Factory factory, BatteryMeterViewController batteryMeterViewController, BatteryViewModel.Factory factory2, ShadeViewStateProvider shadeViewStateProvider, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStatusBarViewModel keyguardStatusBarViewModel, BiometricUnlockController biometricUnlockController, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, UserManager userManager, StatusBarUserChipViewModel statusBarUserChipViewModel, SecureSettings secureSettings, CommandQueue commandQueue, Executor executor, Executor executor2, KeyguardLogger keyguardLogger, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, CommunalSceneInteractor communalSceneInteractor, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, IndicatorGardenPresenter indicatorGardenPresenter, KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository, IndicatorScaleGardener indicatorScaleGardener, StatusIconContainerController statusIconContainerController, OngoingCallController ongoingCallController, KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper, DumpManager dumpManager, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, NetspeedViewController netspeedViewController, SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper, NotificationIconAreaController notificationIconAreaController, PluginLockMediator pluginLockMediator, SlimIndicatorViewMediator slimIndicatorViewMediator, PluginLockStarManager pluginLockStarManager, PrivacyDotViewController privacyDotViewController) throws Resources.NotFoundException {
        super(keyguardStatusBarView);
        final int i = 2;
        final int i2 = 1;
        final int i3 = 0;
        this.mKeyguardHeadsUpShowingAmount = 0.0f;
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Resources.NotFoundException {
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mKeyguardHeadsUpShowingAmount = ((Float) obj2).floatValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        Function function = new Function() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(this.f$0.mKeyguardHeadsUpShowingAmount);
            }
        };
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        this.mHeadsUpShowingAmountAnimation = new AnimatableProperty.AnonymousClass6(R.id.keyguard_hun_animator_end_tag, R.id.keyguard_hun_animator_start_tag, R.id.keyguard_hun_animator_tag, new AnimatableProperty.AnonymousClass5("KEYGUARD_HEADS_UP_SHOWING_AMOUNT", function, biConsumer));
        this.mLock = new Object();
        this.mDraggedFraction = 0.0f;
        this.mConfigurationListener = new AnonymousClass1();
        this.mAnimationCallback = new SystemStatusAnimationCallback(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.2
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
                return new SpringAnimatorSet();
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
                return new SpringAnimatorSet();
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.3
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i4, boolean z, boolean z2) throws Resources.NotFoundException {
                KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) ((ViewController) KeyguardStatusBarViewController.this).mView;
                if (keyguardStatusBarView2.mBatteryCharging != z2) {
                    keyguardStatusBarView2.mBatteryCharging = z2;
                    keyguardStatusBarView2.updateVisibilities();
                }
            }
        };
        this.mOnUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda7
            @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
            public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                KeyguardStatusBarViewController.m3089$r8$lambda$SDjYX36TdSthdVP17CFwx9k5IE(this.f$0, str, drawable);
            }
        };
        this.mAnimatorUpdateListener = new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda8
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) throws Resources.NotFoundException {
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                keyguardStatusBarViewController.mKeyguardStatusBarAnimateAlpha = ((Float) ((ValueAnimator) animator).getAnimatedValue()).floatValue();
                keyguardStatusBarViewController.updateViewState();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i4, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                if (keyguardStatusBarViewController.mFirstBypassAttempt && keyguardStatusBarViewController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                    keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = true;
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) throws Resources.NotFoundException {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                int i4 = keyguardStatusBarViewController.mStatusBarState;
                boolean z2 = true;
                if (i4 != 1 && i4 != 2) {
                    z2 = false;
                }
                if (z || !keyguardStatusBarViewController.mFirstBypassAttempt || !z2 || keyguardStatusBarViewController.mDozing || keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar || keyguardStatusBarViewController.mBiometricUnlockController.isBiometricUnlock()) {
                    return;
                }
                keyguardStatusBarViewController.mFirstBypassAttempt = false;
                keyguardStatusBarViewController.animateKeyguardStatusBarIn();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i4) {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                keyguardStatusBarViewController.mFirstBypassAttempt = keyguardStatusBarViewController.mKeyguardBypassController.getBypassEnabled();
                keyguardStatusBarViewController.mDelayShowingKeyguardStatusBar = false;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                    keyguardStatusBarViewController.mBackgroundExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda15(keyguardStatusBarViewController, 2));
                }
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.5
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i4) {
                KeyguardStatusBarViewController.this.mStatusBarState = i4;
            }
        };
        this.mCommunalConsumer = new Consumer(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                int i4 = i3;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                switch (i4) {
                    case 0:
                        AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalShowing(((Boolean) obj).booleanValue());
                        break;
                    case 1:
                        AnimationProperties animationProperties2 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                    default:
                        AnimationProperties animationProperties3 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                }
            }
        };
        this.mBlockedIcons = new ArrayList();
        this.mKeyguardStatusBarAnimateAlpha = 1.0f;
        this.mSystemEventAnimatorAlpha = 1.0f;
        this.mToGlanceableHubStatusBarAlphaConsumer = new Consumer(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                int i4 = i2;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                switch (i4) {
                    case 0:
                        AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalShowing(((Boolean) obj).booleanValue());
                        break;
                    case 1:
                        AnimationProperties animationProperties2 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                    default:
                        AnimationProperties animationProperties3 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                }
            }
        };
        this.mFromGlanceableHubStatusBarAlphaConsumer = new Consumer(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda9
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                int i4 = i;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                switch (i4) {
                    case 0:
                        AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalShowing(((Boolean) obj).booleanValue());
                        break;
                    case 1:
                        AnimationProperties animationProperties2 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                    default:
                        AnimationProperties animationProperties3 = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                        keyguardStatusBarViewController.getClass();
                        keyguardStatusBarViewController.updateCommunalAlphaTransition(((Float) obj).floatValue());
                        break;
                }
            }
        };
        this.mExplicitAlpha = -1.0f;
        new PluginLockStatusBarCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.6
            @Override // com.android.systemui.pluginlock.component.PluginLockStatusBarCallback
            public final void onVisibilityUpdated(int i4, int i5) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i4, i5, "onVisibilityUpdated() ", ", ", "KeyguardStatusBarViewController");
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
                if (i4 != -1 && i4 != viewGroup.getVisibility()) {
                    keyguardStatusBarViewController.mStatusIconAreaView.setVisibility(i4);
                }
                if (i5 == -1 || i5 == keyguardStatusBarViewController.mKeyguardLeftSideContainerView.getVisibility()) {
                    return;
                }
                keyguardStatusBarViewController.mKeyguardLeftSideContainerView.setVisibility(i5);
            }
        };
        this.mPluginLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.7
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) throws Resources.NotFoundException {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("LockStarCallback: onChangedLockStarEnabled: ", "KeyguardStatusBarViewController", z);
                KeyguardStatusBarViewController.this.updateViewState();
            }

            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final Bundle request(Bundle bundle) throws Resources.NotFoundException {
                Log.d("KeyguardStatusBarViewController", "LockStarCallback: request: " + bundle);
                if (!TextUtils.equals(bundle.getString("type", ""), PluginLockStar.STATUS_BAR_TYPE)) {
                    return null;
                }
                KeyguardStatusBarViewController.this.updateViewState();
                return null;
            }
        };
        this.mHiddenByKnox = false;
        new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.8
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                View viewFindViewById = ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).findViewById(R.id.keyguard_ongoing_call_chip);
                View viewFindViewById2 = viewFindViewById.findViewById(R.id.ongoing_call_chip);
                OngoingCallController ongoingCallController2 = keyguardStatusBarViewController.mOngoingCallController;
                viewFindViewById2.setVisibility(ongoingCallController2.hasOngoingCall() ? 0 : 8);
                viewFindViewById.setVisibility(ongoingCallController2.hasOngoingCall() ? 0 : 8);
            }
        };
        this.mRecentAlpha = -2.0f;
        this.mRecentVisibility = 8;
        this.mCallerExplicitAlpha = "-";
        this.mVolumeSettingObserver = new ContentObserver(null) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.11
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) throws Resources.NotFoundException {
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
        this.mCoroutineDispatcher = coroutineDispatcher;
        this.mContext = context;
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
        this.mInsetsProviderStore = statusBarContentInsetsProviderStore;
        this.mUserManager = userManager;
        this.mStatusBarUserChipViewModel = statusBarUserChipViewModel;
        this.mSecureSettings = secureSettings;
        this.mCommandQueue = commandQueue;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mLogger = keyguardLogger;
        this.mCommunalSceneInteractor = communalSceneInteractor;
        this.mHubToLockscreenTransitionViewModel = glanceableHubToLockscreenTransitionViewModel;
        this.mLockscreenToHubTransitionViewModel = lockscreenToGlanceableHubTransitionViewModel;
        this.mStatusBarState = sysuiStatusBarStateController.getState();
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.mKeyguardStatusBarNioLayoutRepository = keyguardStatusBarNioLayoutRepository;
        this.mKnoxStatusBarControlViewModel = knoxStatusBarControlViewModel;
        knoxStatusBarControlViewModel.setHidden = new KeyguardStatusBarViewController$$ExternalSyntheticLambda12(this, i3);
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
        keyguardStatusBarView2.mKeyguardUserAvatarEnabled = !((UserRepositoryImpl) statusBarUserChipViewModel.interactor.repository).isStatusBarUserChipEnabled;
        keyguardStatusBarView2.updateVisibilities();
        this.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(getResources(), new KeyguardStatusBarViewController$$ExternalSyntheticLambda12(this, i2), new KeyguardStatusBarViewController$$ExternalSyntheticLambda12(this, i), false);
        this.mDisableStateTracker = new DisableStateTracker(1048576, 2, new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this));
        this.mTwoPhoneModeController = twoPhoneModeIconController;
        this.mKeyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        dumpManager.registerDumpable(this);
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
        this.mSlimIndicatorKeyguardCarrierTextHelper = slimIndicatorKeyguardCarrierTextHelper;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mPluginLockMediator = pluginLockMediator;
        this.mPluginLockStarManager = pluginLockStarManager;
        this.mPrivacyDotViewController = privacyDotViewController;
    }

    public final void animateKeyguardStatusBarIn() throws Resources.NotFoundException {
        int i = SceneContainerFlag.$r8$clinit;
        LogLevel logLevel = LogLevel.DEBUG;
        LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.mLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardStatusBarViewController", logLevel, logBuffer$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = "animating status bar in";
        logBuffer.commit(logMessageObtain);
        if (this.mDisableStateTracker.isDisabled) {
            return;
        }
        ((KeyguardStatusBarView) this.mView).setVisibility(0);
        ((KeyguardStatusBarView) this.mView).setAlpha(0.0f);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(this.mAnimatorUpdateListener);
        valueAnimatorOfFloat.setDuration(360L);
        valueAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN);
        valueAnimatorOfFloat.start(false);
    }

    public final void animateKeyguardStatusBarOut(long j, long j2) {
        int i = SceneContainerFlag.$r8$clinit;
        LogLevel logLevel = LogLevel.DEBUG;
        LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.mLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardStatusBarViewController", logLevel, logBuffer$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = "animating status bar out";
        logBuffer.commit(logMessageObtain);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(((KeyguardStatusBarView) this.mView).getAlpha(), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(this.mAnimatorUpdateListener);
        valueAnimatorOfFloat.setStartDelay(j);
        valueAnimatorOfFloat.setDuration(j2);
        valueAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN);
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController.10
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                KeyguardStatusBarViewController keyguardStatusBarViewController = KeyguardStatusBarViewController.this;
                ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).setVisibility(4);
                ((KeyguardStatusBarView) ((ViewController) keyguardStatusBarViewController).mView).setAlpha(1.0f);
                keyguardStatusBarViewController.mKeyguardStatusBarAnimateAlpha = 1.0f;
            }
        });
        valueAnimatorOfFloat.start(false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sbM = MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardStatusBarView:", "  mBatteryListening: "), this.mBatteryListening, printWriter, "  mExplicitAlpha: "), this.mExplicitAlpha, printWriter, "  mCallerExplicitAlpha: ");
        sbM.append(this.mCallerExplicitAlpha);
        printWriter.println(sbM.toString());
        printWriter.println("  alpha: " + ((KeyguardStatusBarView) this.mView).getAlpha());
        printWriter.println("  visibility: " + ((KeyguardStatusBarView) this.mView).getVisibility());
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mHiddenByKnox: "), this.mHiddenByKnox, printWriter);
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        keyguardStatusBarView.getClass();
        printWriter.println("KeyguardStatusBarView:");
        printWriter.println("  mBatteryCharging: " + keyguardStatusBarView.mBatteryCharging);
        printWriter.println("  mLayoutState: 0");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mKeyguardUserSwitcherEnabled: "), false, printWriter);
        BatteryMeterView batteryMeterView = keyguardStatusBarView.mBatteryView;
        if (batteryMeterView != null) {
            batteryMeterView.dump(printWriter, strArr);
        }
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarWallpaperHelper.getClass();
        printWriter.println();
        printWriter.println(" KeyguardStatusBarWallpaperHelper");
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "   fontColorFromWallPaper=", Integer.toHexString(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper));
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("   fontColorType=", keyguardStatusBarWallpaperHelper.fontColorType, printWriter);
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
        View viewFindViewById;
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) this.mView).findViewById(R.id.battery);
        int viewWidth = 0;
        int measuredWidth = (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (viewFindViewById = ((KeyguardStatusBarView) this.mView).findViewById(R.id.networkSpeed)) != null && viewFindViewById.getVisibility() == 0) ? viewFindViewById.getMeasuredWidth() : 0;
        TwoPhoneModeIconController twoPhoneModeIconController = this.mTwoPhoneModeController;
        if (twoPhoneModeIconController.featureEnabled() && twoPhoneModeIconController.getViewWidth() > 0) {
            viewWidth = twoPhoneModeIconController.getViewWidth();
        }
        return batteryMeterView.getMeasuredWidth() + measuredWidth + viewWidth;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final WindowInsets getGardenWindowInsets() {
        return ((KeyguardStatusBarView) this.mView).getRootWindowInsets();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getHeightContainer() {
        return (ViewGroup) this.mView;
    }

    public final float getKeyguardContentsAlpha() {
        float panelViewExpandedHeight;
        float height;
        boolean z = this.mStatusBarState == 1;
        ShadeViewStateProvider shadeViewStateProvider = this.mShadeViewStateProvider;
        if (z) {
            panelViewExpandedHeight = shadeViewStateProvider.getPanelViewExpandedHeight();
            height = ((KeyguardStatusBarView) this.mView).getHeight() + this.mNotificationsHeaderCollideDistance;
        } else {
            panelViewExpandedHeight = shadeViewStateProvider.getPanelViewExpandedHeight();
            height = ((KeyguardStatusBarView) this.mView).getHeight();
        }
        return (float) Math.pow(MathUtils.saturate(panelViewExpandedHeight / height), 0.75d);
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

    public final boolean isLockStarStatusBarEnabled() {
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager == null) {
            return true;
        }
        try {
            if (!pluginLockStarManager.isLockStarEnabled()) {
                return true;
            }
            LockStarValues lockStarValues = pluginLockStarManager.getLockStarValues();
            if (lockStarValues == null) {
                Log.w("KeyguardStatusBarViewController", "isLockStarStatusBarEnabled: values are null");
                return true;
            }
            boolean statusBarVisible = lockStarValues.getStatusBarVisible();
            Log.i("KeyguardStatusBarViewController", "isLockStarStatusBarEnabled: ret=" + statusBarVisible);
            return statusBarVisible;
        } catch (Throwable th) {
            Log.e("KeyguardStatusBarViewController", "isLockStarStatusBarEnabled: error - " + th.getMessage());
            return true;
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.mCarrierTextController.init();
        BatteryMeterViewController batteryMeterViewController = this.mBatteryMeterViewController;
        batteryMeterViewController.init();
        int i = NewStatusBarIcons.$r8$clinit;
        batteryMeterViewController.init();
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedViewController.init();
        }
        int i2 = SceneContainerFlag.$r8$clinit;
        this.mStatusIconContainerController.init();
        ViewGroup viewGroup = (ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_notification_icon_container);
        this.mKeyguardStatusBarNotifContainer = viewGroup;
        this.mNotificationIconAreaController.setKeyguardNotifIcon((NotificationIconContainer) viewGroup.findViewById(R.id.notificationIcons));
        this.mKeyguardStatusBarNotifContainer.setVisibility(0);
    }

    public final void onThemeChanged() {
        ((KeyguardStatusBarView) this.mView).onThemeChanged(this.mTintedIconManager);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0] */
    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        final int i = 0;
        final KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) this.mView;
        StatusBarUserChipViewBinder.bind(keyguardStatusBarView.mUserSwitcherContainer, this.mStatusBarUserChipViewModel, new Function1() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                int iIntValue = ((Integer) obj).intValue();
                KeyguardStatusBarView keyguardStatusBarView2 = keyguardStatusBarView;
                keyguardStatusBarView2.mUserCount = iIntValue;
                keyguardStatusBarView2.updateVisibilities();
                return Unit.INSTANCE;
            }
        });
        AnonymousClass1 anonymousClass1 = this.mConfigurationListener;
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(anonymousClass1);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this.mAnimationCallback);
        ((UserInfoControllerImpl) this.mUserInfoController).addCallback(this.mOnUserInfoChangedListener);
        AnonymousClass5 anonymousClass5 = this.mStatusBarStateListener;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        sysuiStatusBarStateController.addCallback(anonymousClass5);
        this.mStatusBarState = sysuiStatusBarStateController.getState();
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        Integer numValueOf = Integer.valueOf(((KeyguardStatusBarView) this.mView).getDisplay().getDisplayId());
        DisableStateTracker disableStateTracker = this.mDisableStateTracker;
        disableStateTracker.displayId = numValueOf;
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) disableStateTracker);
        if (this.mTintedIconManager == null) {
            TintedIconManager tintedIconManagerCreate = this.mTintedIconManagerFactory.create((ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.statusIcons), StatusBarLocation.KEYGUARD);
            this.mTintedIconManager = tintedIconManagerCreate;
            ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(tintedIconManagerCreate);
        } else {
            int i2 = SceneContainerFlag.$r8$clinit;
        }
        this.mSystemIconsContainer = (ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.system_icons);
        ((KeyguardStatusBarView) this.mView).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return KeyguardStatusBarViewController.$r8$lambda$uRu3BdOjY_vdtOtpXqUuJMlQ2MU(this.f$0, windowInsets);
            }
        });
        this.mIndicatorGardenPresenter.updateGardenWithNewModel(this);
        ((KeyguardStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ KeyguardStatusBarViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                int i11 = i;
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                switch (i11) {
                    case 0:
                        if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                            IndicatorGardenPresenter indicatorGardenPresenter = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                            indicatorGardenPresenter.getClass();
                            indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, keyguardStatusBarViewController));
                            break;
                        }
                        break;
                    default:
                        KeyguardStatusBarViewController.$r8$lambda$mhSmafSY8MAGHOxdxpbLCebrL0Y(keyguardStatusBarViewController);
                        break;
                }
            }
        });
        View viewFindViewById = ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_carrier_text_nio_container);
        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = this.mKeyguardStatusBarNioLayoutRepository;
        keyguardStatusBarNioLayoutRepository.getClass();
        keyguardStatusBarNioLayoutRepository.printLog("injectNioContainer(" + viewFindViewById + ")");
        keyguardStatusBarNioLayoutRepository.nioContainerView = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.addOnLayoutChangeListener(keyguardStatusBarNioLayoutRepository);
            keyguardStatusBarNioLayoutRepository.updateNioLayoutMargin();
        }
        this.mSecureSettings.registerContentObserverForUserSync("status_bar_show_vibrate_icon", false, (ContentObserver) this.mVolumeSettingObserver, -1);
        this.mBackgroundExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda15(this, 2));
        onThemeChanged();
        T t = this.mView;
        ReadonlyStateFlow readonlyStateFlow = this.mCommunalSceneInteractor.isCommunalVisible;
        KeyguardStatusBarViewController$$ExternalSyntheticLambda9 keyguardStatusBarViewController$$ExternalSyntheticLambda9 = this.mCommunalConsumer;
        CoroutineDispatcher coroutineDispatcher = this.mCoroutineDispatcher;
        JavaAdapterKt.collectFlow(t, readonlyStateFlow, keyguardStatusBarViewController$$ExternalSyntheticLambda9, coroutineDispatcher);
        JavaAdapterKt.collectFlow(this.mView, this.mLockscreenToHubTransitionViewModel.statusBarAlpha, this.mToGlanceableHubStatusBarAlphaConsumer, coroutineDispatcher);
        JavaAdapterKt.collectFlow(this.mView, this.mHubToLockscreenTransitionViewModel.statusBarAlpha, this.mFromGlanceableHubStatusBarAlphaConsumer, coroutineDispatcher);
        int i3 = NewStatusBarIcons.$r8$clinit;
        this.mStatusBarState = sysuiStatusBarStateController.getState();
        KnoxStatusBarControlBinder.bind(this.mKnoxStatusBarControlViewModel, (KnoxStatusBarViewControl) this.mView);
        KeyguardStatusBarView keyguardStatusBarView2 = (KeyguardStatusBarView) this.mView;
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarView2.mKeyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        KeyguardStatusBarViewController$$ExternalSyntheticLambda2 keyguardStatusBarViewController$$ExternalSyntheticLambda2 = new KeyguardStatusBarViewController$$ExternalSyntheticLambda2(this);
        keyguardStatusBarWallpaperHelper.wakefulnessLifecycle.addObserver(keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.wallpaperEventNotifier.registerCallback(false, keyguardStatusBarWallpaperHelper, 17L);
        keyguardStatusBarWallpaperHelper.listener = keyguardStatusBarViewController$$ExternalSyntheticLambda2;
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            final int i4 = 1;
            ((ViewGroup) ((KeyguardStatusBarView) this.mView).findViewById(R.id.system_icons)).addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardStatusBarViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i32, int i42, int i5, int i6, int i7, int i8, int i9, int i10) {
                    int i11 = i4;
                    KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                    switch (i11) {
                        case 0:
                            if (((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
                                IndicatorGardenPresenter indicatorGardenPresenter = keyguardStatusBarViewController.mIndicatorGardenPresenter;
                                indicatorGardenPresenter.getClass();
                                indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, keyguardStatusBarViewController));
                                break;
                            }
                            break;
                        default:
                            KeyguardStatusBarViewController.$r8$lambda$mhSmafSY8MAGHOxdxpbLCebrL0Y(keyguardStatusBarViewController);
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
            public final void updateQuickStarStyle() throws Resources.NotFoundException {
                KeyguardStatusBarViewController.$r8$lambda$LWsNDpOIXrKQmKLzeTePDCTTMzI(this.f$0);
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
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
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
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        keyguardStatusBarWallpaperHelper.wakefulnessLifecycle.removeObserver(keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.wallpaperEventNotifier.removeCallback(false, keyguardStatusBarWallpaperHelper);
        keyguardStatusBarWallpaperHelper.listener = null;
        StringBuilder sb = new StringBuilder("detach() mCarrierTextView:");
        SlimIndicatorKeyguardCarrierTextHelper slimIndicatorKeyguardCarrierTextHelper = this.mSlimIndicatorKeyguardCarrierTextHelper;
        sb.append(slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView);
        sb.append(", mOriginalVisibility:");
        RecyclerView$$ExternalSyntheticOutline0.m(slimIndicatorKeyguardCarrierTextHelper.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper", sb);
        slimIndicatorKeyguardCarrierTextHelper.mCarrierTextView = null;
        ((SlimIndicatorViewMediatorImpl) slimIndicatorKeyguardCarrierTextHelper.mSlimIndicatorViewMediator).unregisterSubscriber("KeyguardStatusBarCarrierText");
        ((SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator).unregisterSubscriber("KeyguardStatusBarViewController");
        View viewFindViewById = ((KeyguardStatusBarView) this.mView).findViewById(R.id.keyguard_carrier_text_nio_container);
        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = this.mKeyguardStatusBarNioLayoutRepository;
        keyguardStatusBarNioLayoutRepository.getClass();
        keyguardStatusBarNioLayoutRepository.printLog("removeNioContainer(" + viewFindViewById + ")");
        View view = keyguardStatusBarNioLayoutRepository.nioContainerView;
        if (view != null) {
            view.removeOnLayoutChangeListener(keyguardStatusBarNioLayoutRepository);
        }
        keyguardStatusBarNioLayoutRepository.nioContainerView = null;
    }

    public final void setAlpha(float f) throws Resources.NotFoundException {
        int i = SceneContainerFlag.$r8$clinit;
        if (Float.compare(this.mExplicitAlpha, f) != 0 && Float.compare(this.mExplicitAlpha, -1.0f) == 0 && Float.compare(f, -0.01f) > 0) {
            this.mCallerExplicitAlpha = Debug.getCallers(0, 3);
            StringBuilder sb = new StringBuilder("mExplicitAlpha(");
            sb.append(this.mExplicitAlpha);
            sb.append(" >> ");
            sb.append(f);
            sb.append(") caller:");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, this.mCallerExplicitAlpha, "KeyguardStatusBarViewController");
        }
        this.mExplicitAlpha = f;
        updateViewState();
    }

    public void updateBlockedIcons() throws Resources.NotFoundException {
        Resources resources = getResources();
        SecureSettings secureSettings = this.mSecureSettings;
        ArraysKt___ArraysKt.toList(resources.getStringArray(R.array.config_collapsed_statusbar_icon_blocklist));
        resources.getString(17043307);
        secureSettings.getIntForUser("status_bar_show_vibrate_icon", 0, -2);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            ((ArrayList) this.mBlockedIcons).clear();
            ((ArrayList) this.mBlockedIcons).addAll(arrayList);
        }
        this.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda15(this, 0));
    }

    public void updateCommunalAlphaTransition(float f) throws Resources.NotFoundException {
        if (!this.mCommunalShowing || f == 0.0f) {
            f = -1.0f;
        }
        setAlpha(f);
    }

    public void updateCommunalShowing(boolean z) throws Resources.NotFoundException {
        this.mCommunalShowing = z;
        if (!z) {
            setAlpha(-1.0f);
        }
        updateViewState();
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

    public final void updateViewState() throws Resources.NotFoundException {
        boolean z = true;
        if (this.mStatusBarState == 1) {
            int i = SceneContainerFlag.$r8$clinit;
            ShadeViewStateProvider shadeViewStateProvider = this.mShadeViewStateProvider;
            float fMin = 1.0f - Math.min(1.0f, shadeViewStateProvider.getLockscreenShadeDragProgress() * 2.0f);
            if (shadeViewStateProvider.getKeyguardTouchAnimator().isViRunning()) {
                if (this.mView != 0) {
                    Log.d("KeyguardStatusBarViewController", "updateViewState() KeyguardTouchAnimator isViRunning is true, so return.. v:" + ((KeyguardStatusBarView) this.mView).getVisibility() + ", a:" + ((KeyguardStatusBarView) this.mView).getAlpha());
                    return;
                }
                return;
            }
            float fMin2 = this.mExplicitAlpha;
            if (fMin2 == -1.0f) {
                fMin2 = Math.min(getKeyguardContentsAlpha(), fMin) * this.mKeyguardStatusBarAnimateAlpha * (1.0f - this.mKeyguardHeadsUpShowingAmount);
            }
            if (this.mSystemEventAnimator.isAnimationRunning) {
                fMin2 = Math.min(fMin2, this.mSystemEventAnimatorAlpha);
            } else {
                ((KeyguardStatusBarView) this.mView).setTranslationX(0.0f);
            }
            boolean z2 = this.mFirstBypassAttempt;
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if ((!z2 || !keyguardUpdateMonitor.shouldListenForFace()) && !this.mDelayShowingKeyguardStatusBar) {
                z = false;
            }
            DisableStateTracker disableStateTracker = this.mDisableStateTracker;
            int i2 = (fMin2 == 0.0f || this.mDozing || z || disableStateTracker.isDisabled || (this.mCommunalShowing && this.mExplicitAlpha == -1.0f) || this.mHiddenByKnox || !isLockStarStatusBarEnabled()) ? 4 : 0;
            if (this.mRecentAlpha != fMin2 && (fMin2 == 0.0f || fMin2 == 1.0f)) {
                this.mRecentAlpha = fMin2;
                StringBuilder sb = new StringBuilder("Alpha changed! - mExplicitAlpha=");
                sb.append(this.mExplicitAlpha);
                sb.append(", getKeyguardContentsAlpha()=");
                sb.append(getKeyguardContentsAlpha());
                sb.append(", alphaQsExpansion=");
                sb.append(fMin);
                sb.append(", mKeyguardStatusBarAnimateAlpha=");
                sb.append(this.mKeyguardStatusBarAnimateAlpha);
                sb.append(", mKeyguardHeadsUpShowingAmount=");
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m(this.mKeyguardHeadsUpShowingAmount, "KeyguardStatusBarViewController", sb);
            }
            if (this.mRecentVisibility != i2) {
                this.mRecentVisibility = i2;
                Log.d("KeyguardStatusBarViewController", "Visibility changed! - mDozing=" + this.mDozing + ", mFirstBypassAttempt=" + this.mFirstBypassAttempt + ", mKeyguardUpdateMonitor.shouldListenForFace()=" + keyguardUpdateMonitor.shouldListenForFace() + ", mDelayShowingKeyguardStatusBar=" + this.mDelayShowingKeyguardStatusBar + ", mDisableStateTracker.isDisabled()=" + disableStateTracker.isDisabled + ", Flags.glanceableHubV2()=false, mCommunalShowing=" + this.mCommunalShowing + ", mExplicitAlpha=" + this.mExplicitAlpha + ", mHiddenByKnox=" + this.mHiddenByKnox + ", isLockStarStatusBarEnabled()=" + isLockStarStatusBarEnabled());
            }
            updateViewState(fMin2, i2);
        }
    }

    public final void updateViewState(float f, int i) throws Resources.NotFoundException {
        int i2 = SceneContainerFlag.$r8$clinit;
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
}
