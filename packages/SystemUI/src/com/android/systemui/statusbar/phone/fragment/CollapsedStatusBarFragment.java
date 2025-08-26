package com.android.systemui.statusbar.phone.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Trace;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.display.util.SamsungSecondScreenUtil;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.OperatorNameViewController$Factory;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.core.StatusBarRootModernization;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStore;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStoreImpl;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationControllerStore;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.events.SpringAnimatorSet;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.headsup.shared.StatusBarNoHunBehavior;
import com.android.systemui.statusbar.layout.StatusBarBoundsProvider;
import com.android.systemui.statusbar.layout.StatusBarBoundsProvider$layoutListener$1;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerStatusBarViewBinder;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent$Startable$State;
import com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController;
import com.android.systemui.statusbar.phone.logo.CarrierLogoView;
import com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallBackgroundContainer;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallChronometer;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerExt;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.phone.ongoingcall.StatusBarChipsModernization;
import com.android.systemui.statusbar.phone.ui.DarkIconManager;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinder;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinderImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.StatusBarVisibilityChangeListener;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.AnimationUtil;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class CollapsedStatusBarFragment extends Fragment implements CommandQueue.Callbacks, StatusBarStateController.StateListener, SystemStatusAnimationCallback, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SystemStatusAnimationScheduler mAnimationScheduler;
    public boolean mAnimationsEnabled;
    public final List mBlockedIcons;
    public final AnonymousClass2 mCarrierConfigCallback;
    public final CarrierConfigTracker mCarrierConfigTracker;
    public CarrierHomeLogoViewController mCarrierHomeLogoViewController;
    public final CarrierInfraMediator mCarrierInfraMediator;
    public final CarrierHomeLogoViewController.Factory mCarrierLogoViewControllerFactory;
    public ChipAnimationController mChipAnimationController;
    public View mClockView;
    public final CollapsedStatusBarFragmentLogger mCollapsedStatusBarFragmentLogger;
    public final CommandQueue mCommandQueue;
    public final DarkIconDispatcherStore mDarkIconDispatcherStore;
    public DarkIconManager mDarkIconManager;
    public final DarkIconManager.Factory mDarkIconManagerFactory;
    public final AnonymousClass3 mDefaultDataListener;
    public final AnonymousClass4 mDemoModeCallback;
    public final DemoModeController mDemoModeController;
    public final DumpManager mDumpManager;
    public MultiSourceMinAlphaController mEndSideAlphaController;
    public final Handler mHandler;
    public HomeStatusBarComponent mHomeStatusBarComponent;
    public final HomeStatusBarComponent.Factory mHomeStatusBarComponentFactory;
    public final HomeStatusBarViewBinder mHomeStatusBarViewBinder;
    public HomeStatusBarViewModelImpl mHomeStatusBarViewModel;
    public final HomeStatusBarViewModel.HomeStatusBarViewModelFactory mHomeStatusBarViewModelFactory;
    public final KeyguardStateController mKeyguardStateController;
    public StatusBarVisibilityModel mLastModifiedVisibility;
    public StatusBarVisibilityModel mLastSystemVisibility;
    public final Executor mMainExecutor;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public View mNotificationIconAreaInner;
    public final OngoingActivityController mOngoingActivityController;
    public View mOngoingCallChip;
    public final OngoingCallController mOngoingCallController;
    public final AnonymousClass1 mOngoingCallListener;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public View mPrimaryOngoingActivityChip;
    public final PrivacyLogger mPrivacyLogger;
    public final Lazy mSamsungExtLazy;
    public final SecureSettings mSecureSettings;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final Map mStartableStates;
    public PhoneStatusBarView mStatusBar;
    public final StatusBarConfigurationControllerStore mStatusBarConfigurationControllerStore;
    public final StatusBarIconController mStatusBarIconController;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass5 mStatusBarVisibilityChangeListener;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final CollapsedStatusBarFragment$$ExternalSyntheticLambda0 mStatusBarWindowStateListener;
    public StatusBarSystemEventDefaultAnimator mSystemEventAnimator;
    public final AnonymousClass7 mVolumeSettingObserver;
    public boolean mWaitingForWindowStateChangeAfterCameraLaunch;

    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$5, reason: invalid class name */
    public class AnonymousClass5 implements StatusBarVisibilityChangeListener {
        public AnonymousClass5() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$6, reason: invalid class name */
    public class AnonymousClass6 extends AnimatorListenerAdapter {
        public final /* synthetic */ boolean val$statusBarHidden;

        public AnonymousClass6(boolean z) {
            this.val$statusBarHidden = z;
        }

        @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            Log.d("CollapsedStatusBarFragment", "onSystemEventAnimationFinish() onAnimationCancel");
            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
            collapsedStatusBarFragment.mOngoingCallController.samsungExt.blockClickListener = false;
            collapsedStatusBarFragment.mOngoingActivityController.blockClickListener = false;
        }

        @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            Handler handler = CollapsedStatusBarFragment.this.mHandler;
            final boolean z = this.val$statusBarHidden;
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CollapsedStatusBarFragment.AnonymousClass6 anonymousClass6 = this.f$0;
                    boolean z2 = z;
                    CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                    if (z2) {
                        PrivacyLogger privacyLogger = collapsedStatusBarFragment.mPrivacyLogger;
                        privacyLogger.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer = privacyLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) logMessageObtain).int1 = 1;
                        logBuffer.commit(logMessageObtain);
                        StatusBarSystemEventDefaultAnimator statusBarSystemEventDefaultAnimator = collapsedStatusBarFragment.mSystemEventAnimator;
                        statusBarSystemEventDefaultAnimator.onAlphaChanged.mo781invoke(Float.valueOf(1.0f));
                    }
                    collapsedStatusBarFragment.mOngoingCallController.samsungExt.blockClickListener = false;
                    collapsedStatusBarFragment.mOngoingActivityController.blockClickListener = false;
                }
            }, 390L);
        }
    }

    public class OngoingActivityListenerImpl {
        public /* synthetic */ OngoingActivityListenerImpl(CollapsedStatusBarFragment collapsedStatusBarFragment, int i) {
            this();
        }

        public final void onNudgeClockRequired() {
            ChipAnimationController chipAnimationController = CollapsedStatusBarFragment.this.mChipAnimationController;
            chipAnimationController.getClass();
            Log.d("{ChipAnimationController}", "nudgeClockIfNeeded()");
            View view = chipAnimationController.onGoingActivityChip;
            if (view == null || view.getVisibility() != 0) {
                return;
            }
            View view2 = chipAnimationController.onGoingCallChip;
            if (view2 == null || view2.getVisibility() != 0) {
                chipAnimationController.clockViewNudgeAnimation();
            }
        }

        private OngoingActivityListenerImpl() {
        }
    }

    /* JADX WARN: Type inference failed for: r14v10, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$4] */
    /* JADX WARN: Type inference failed for: r14v12, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$7] */
    /* JADX WARN: Type inference failed for: r14v5, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$1] */
    /* JADX WARN: Type inference failed for: r14v6, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$2] */
    /* JADX WARN: Type inference failed for: r14v7, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$3] */
    /* JADX WARN: Type inference failed for: r14v8, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0] */
    public CollapsedStatusBarFragment(HomeStatusBarComponent.Factory factory, Lazy lazy, OngoingActivityController ongoingActivityController, OngoingCallController ongoingCallController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, NotificationIconAreaController notificationIconAreaController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarIconController statusBarIconController, DarkIconManager.Factory factory2, HomeStatusBarViewModel.HomeStatusBarViewModelFactory homeStatusBarViewModelFactory, HomeStatusBarViewBinder homeStatusBarViewBinder, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, KeyguardStateController keyguardStateController, PanelExpansionInteractor panelExpansionInteractor, StatusBarStateController statusBarStateController, NotificationIconContainerStatusBarViewBinder notificationIconContainerStatusBarViewBinder, CommandQueue commandQueue, CarrierConfigTracker carrierConfigTracker, CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger, OperatorNameViewController$Factory operatorNameViewController$Factory, SecureSettings secureSettings, Executor executor, DumpManager dumpManager, StatusBarWindowStateController statusBarWindowStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DemoModeController demoModeController, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarConfigurationControllerStore statusBarConfigurationControllerStore, DarkIconDispatcherStore darkIconDispatcherStore, Handler handler, PrivacyLogger privacyLogger, CarrierInfraMediator carrierInfraMediator, CarrierHomeLogoViewController.Factory factory3) {
        StatusBarVisibilityModel.Companion companion = StatusBarVisibilityModel.Companion;
        companion.getClass();
        this.mLastSystemVisibility = StatusBarVisibilityModel.Companion.createModelFromFlags(0, 0);
        companion.getClass();
        this.mLastModifiedVisibility = StatusBarVisibilityModel.Companion.createModelFromFlags(0, 0);
        this.mBlockedIcons = new ArrayList();
        this.mStartableStates = new ArrayMap();
        this.mOngoingCallListener = new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.1
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                int i = CollapsedStatusBarFragment.$r8$clinit;
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.updateStatusBarVisibilities(true);
                collapsedStatusBarFragment.mNotificationIconAreaController.updateStatusBarIcons();
            }
        };
        this.mCarrierConfigCallback = new CarrierConfigTracker.CarrierConfigChangedListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.2
            @Override // com.android.systemui.util.CarrierConfigTracker.CarrierConfigChangedListener
            public final void onCarrierConfigChanged() {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                if (collapsedStatusBarFragment.mCarrierHomeLogoViewController == null) {
                    collapsedStatusBarFragment.initOperatorName();
                }
            }
        };
        this.mDefaultDataListener = new CarrierConfigTracker.DefaultDataSubscriptionChangedListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.3
            @Override // com.android.systemui.util.CarrierConfigTracker.DefaultDataSubscriptionChangedListener
            public final void onDefaultSubscriptionChanged(int i) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                if (collapsedStatusBarFragment.mCarrierHomeLogoViewController == null) {
                    collapsedStatusBarFragment.initOperatorName();
                }
            }
        };
        this.mWaitingForWindowStateChangeAfterCameraLaunch = false;
        this.mStatusBarWindowStateListener = new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = this.f$0;
                collapsedStatusBarFragment.mWaitingForWindowStateChangeAfterCameraLaunch = false;
                collapsedStatusBarFragment.updateStatusBarVisibilities(false);
            }
        };
        this.mAnimationsEnabled = true;
        this.mDemoModeCallback = new DemoMode() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.4
            @Override // com.android.systemui.demomode.DemoMode
            public final List demoCommands() {
                return List.of("notifications");
            }

            @Override // com.android.systemui.demomode.DemoModeCommandReceiver
            public final void dispatchDemoCommand(Bundle bundle, String str) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                if (collapsedStatusBarFragment.mNotificationIconAreaInner == null) {
                    return;
                }
                if ("false".equals(bundle.getString("visible"))) {
                    collapsedStatusBarFragment.mNotificationIconAreaInner.setVisibility(4);
                } else {
                    collapsedStatusBarFragment.mNotificationIconAreaInner.setVisibility(0);
                }
            }

            @Override // com.android.systemui.demomode.DemoModeCommandReceiver
            public final void onDemoModeFinished() {
                View view = CollapsedStatusBarFragment.this.mNotificationIconAreaInner;
                if (view == null) {
                    return;
                }
                view.setVisibility(0);
            }
        };
        this.mStatusBarVisibilityChangeListener = new AnonymousClass5();
        this.mVolumeSettingObserver = new ContentObserver(null) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                CollapsedStatusBarFragment.this.updateBlockedIcons();
            }
        };
        this.mHomeStatusBarComponentFactory = factory;
        this.mSamsungExtLazy = lazy;
        this.mOngoingActivityController = ongoingActivityController;
        this.mOngoingCallController = ongoingCallController;
        this.mAnimationScheduler = systemStatusAnimationScheduler;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mStatusBarIconController = statusBarIconController;
        this.mHomeStatusBarViewModelFactory = homeStatusBarViewModelFactory;
        this.mHomeStatusBarViewBinder = homeStatusBarViewBinder;
        this.mDarkIconManagerFactory = factory2;
        this.mKeyguardStateController = keyguardStateController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mStatusBarStateController = statusBarStateController;
        this.mCommandQueue = commandQueue;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mCollapsedStatusBarFragmentLogger = collapsedStatusBarFragmentLogger;
        this.mSecureSettings = secureSettings;
        this.mMainExecutor = executor;
        this.mDumpManager = dumpManager;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mDemoModeController = demoModeController;
        this.mStatusBarWindowControllerStore = statusBarWindowControllerStore;
        this.mStatusBarConfigurationControllerStore = statusBarConfigurationControllerStore;
        this.mDarkIconDispatcherStore = darkIconDispatcherStore;
        this.mHandler = handler;
        this.mPrivacyLogger = privacyLogger;
        this.mCarrierInfraMediator = carrierInfraMediator;
        this.mCarrierLogoViewControllerFactory = factory3;
    }

    public final void animateHiddenState(final View view, final int i, boolean z) {
        int i2 = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (view == null) {
            return;
        }
        boolean z2 = z && this.mAnimationsEnabled;
        ChipAnimationController chipAnimationController = this.mChipAnimationController;
        if (chipAnimationController != null && (Intrinsics.areEqual(view, chipAnimationController.onGoingCallChip) || Intrinsics.areEqual(view, chipAnimationController.onGoingActivityChip))) {
            this.mChipAnimationController.animateChipHide(view, i, z2);
            return;
        }
        view.animate().cancel();
        if (z && this.mAnimationsEnabled) {
            view.animate().alpha(0.0f).setDuration(160L).setStartDelay(0L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    View view2 = view;
                    int i3 = i;
                    int i4 = CollapsedStatusBarFragment.$r8$clinit;
                    view2.setVisibility(i3);
                }
            });
        } else {
            view.setAlpha(0.0f);
            view.setVisibility(i);
        }
    }

    public final void animateShow(View view, boolean z) {
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (view == null) {
            return;
        }
        boolean z2 = z && this.mAnimationsEnabled;
        ChipAnimationController chipAnimationController = this.mChipAnimationController;
        if (chipAnimationController != null && (Intrinsics.areEqual(view, chipAnimationController.onGoingCallChip) || Intrinsics.areEqual(view, chipAnimationController.onGoingActivityChip))) {
            this.mChipAnimationController.animateChipShow(view, z2);
            return;
        }
        view.animate().cancel();
        view.setVisibility(0);
        if (!z || !this.mAnimationsEnabled) {
            view.setAlpha(1.0f);
            return;
        }
        view.animate().alpha(1.0f).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).setStartDelay(50L).withEndAction(null);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAway) {
            view.animate().setDuration(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAwayDuration).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setStartDelay(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAwayDelay).start();
        }
    }

    public final StatusBarVisibilityModel calculateInternalModel(StatusBarVisibilityModel statusBarVisibilityModel) {
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (((CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get()).shouldHideIconsForNextAppWindow) {
            StatusBarVisibilityModel.Companion.getClass();
            return new StatusBarVisibilityModel(false, false, false, false, false, false);
        }
        boolean zShouldHeadsUpStatusBarBeVisible = this.mHomeStatusBarComponent.getHeadsUpAppearanceController().shouldHeadsUpStatusBarBeVisible();
        int i2 = StatusBarNoHunBehavior.$r8$clinit;
        int i3 = SceneContainerFlag.$r8$clinit;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.getClass();
        if (!keyguardStateControllerImpl.mKeyguardFadingAway) {
            if (getContext() != null) {
                getContext().getDisplayId();
            }
            int i4 = StatusBarConnectedDisplays.$r8$clinit;
            if (!this.mShadeExpansionStateManager.isClosed() && this.mPanelExpansionInteractor.shouldHideStatusBarIconsWhenExpanded()) {
                if (this.mStatusBarStateController.getState() == 1) {
                }
                StatusBarVisibilityModel.Companion.getClass();
                return new StatusBarVisibilityModel(false, false, false, false, false, false);
            }
            CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
            boolean z = this.mWaitingForWindowStateChangeAfterCameraLaunch;
            boolean zBooleanValue = ((Boolean) this.mHomeStatusBarViewModel.isTransitioningFromLockscreenToOccluded.$$delegate_0.getValue()).booleanValue();
            if (collapsedStatusBarFragmentExt.shouldHideIconsForNextAppWindow) {
                collapsedStatusBarFragmentExt.printStatusBarInfoLog("shouldHideStatusBar(NextApp) waitingForWindowStateChangeAfterCameraLaunch:" + z);
            } else {
                Lazy lazy = collapsedStatusBarFragmentExt.keyguardStateControllerLazy;
                boolean z2 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mOccluded;
                boolean z3 = ((KeyguardStateControllerImpl) ((KeyguardStateController) lazy.get())).mShowing;
                if (z2 && zBooleanValue) {
                    collapsedStatusBarFragmentExt.printStatusBarInfoLog("shouldHideStatusBar(Occluded) isTransitioningFromLockscreenToOccluded:true");
                } else if (!z3 || z2) {
                    StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = collapsedStatusBarFragmentExt.statusBarHideIconsForBouncerManager;
                    boolean z4 = statusBarHideIconsForBouncerManager.hideIconsForBouncer;
                    if (z4 || statusBarHideIconsForBouncerManager.wereIconsJustHidden) {
                        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("hideIconsForBouncer=", " wereIconsJustHidden=", "StatusBarHideIconsForBouncerManager", z4, statusBarHideIconsForBouncerManager.wereIconsJustHidden);
                    }
                    if (statusBarHideIconsForBouncerManager.hideIconsForBouncer || statusBarHideIconsForBouncerManager.wereIconsJustHidden) {
                    }
                }
            }
            if (this.mStatusBarStateController.getState() == 1 || !zShouldHeadsUpStatusBarBeVisible) {
                StatusBarVisibilityModel.Companion.getClass();
                return new StatusBarVisibilityModel(false, false, false, false, false, false);
            }
        }
        boolean z5 = statusBarVisibilityModel.showClock && !zShouldHeadsUpStatusBarBeVisible;
        boolean zShouldVisible = this.mOngoingActivityController.shouldVisible();
        int i5 = StatusBarNotifChips.$r8$clinit;
        return new StatusBarVisibilityModel(z5, statusBarVisibilityModel.showNotificationIcons, this.mOngoingCallController.hasOngoingCall() && !zShouldHeadsUpStatusBarBeVisible, zShouldVisible && !zShouldHeadsUpStatusBarBeVisible, false, statusBarVisibilityModel.showSystemInfo);
    }

    public final int clockHiddenMode() {
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        boolean zIsClosed = this.mShadeExpansionStateManager.isClosed();
        boolean zIsDozing = this.mStatusBarStateController.isDozing();
        boolean z = ((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt.keyguardStateControllerLazy.get())).mShowing;
        boolean z2 = ((SamsungSecondScreenUtil) collapsedStatusBarFragmentExt.secondScreenUtil.get()).isConnectedState;
        if (zIsClosed || zIsDozing || z) {
            return (!z2 || !zIsClosed || zIsDozing || z) ? 8 : 4;
        }
        return 4;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        int i4 = StatusBarRootModernization.$r8$clinit;
        if (getContext() == null || i == getContext().getDisplayId()) {
            final CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger = this.mCollapsedStatusBarFragmentLogger;
            DisableFlagsLogger.DisableState disableState = new DisableFlagsLogger.DisableState(i2, i3);
            collapsedStatusBarFragmentLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return collapsedStatusBarFragmentLogger.disableFlagsLogger.getDisableFlagsString(new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), null);
                }
            };
            LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("CollapsedSbFragment", logLevel, function1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = disableState.disable1;
            logMessageImpl.int2 = disableState.disable2;
            logBuffer.commit(logMessageObtain);
            StatusBarVisibilityModel.Companion.getClass();
            this.mLastSystemVisibility = StatusBarVisibilityModel.Companion.createModelFromFlags(i2, i3);
            updateStatusBarVisibilities(z);
        }
    }

    public void disableAnimationsForTesting() {
        this.mAnimationsEnabled = false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("mHasPrimaryOngoingActivity=false");
        indentingPrintWriter.println("mHasSecondaryOngoingActivity=false");
        indentingPrintWriter.println("mAnimationsEnabled=" + this.mAnimationsEnabled);
        HomeStatusBarComponent homeStatusBarComponent = this.mHomeStatusBarComponent;
        if (homeStatusBarComponent == null) {
            indentingPrintWriter.println("StatusBarFragmentComponent is null");
            return;
        }
        Set<StatusBarBoundsProvider> startables = homeStatusBarComponent.getStartables();
        indentingPrintWriter.println("Startables: " + startables.size());
        indentingPrintWriter.increaseIndent();
        for (StatusBarBoundsProvider statusBarBoundsProvider : startables) {
            indentingPrintWriter.println(statusBarBoundsProvider + ", state: " + ((HomeStatusBarComponent$Startable$State) this.mStartableStates.getOrDefault(statusBarBoundsProvider, HomeStatusBarComponent$Startable$State.NONE)));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void enableAnimationsForTesting() {
        this.mAnimationsEnabled = true;
    }

    public List<String> getBlockedIcons() {
        return this.mBlockedIcons;
    }

    public final String getDumpableName() {
        if (getContext().getDisplayId() == 0) {
            return getClass().getSimpleName();
        }
        return getClass().getSimpleName() + getContext().getDisplayId();
    }

    public final void hideCarrierLogo(boolean z, boolean z2) {
        if (this.mCarrierHomeLogoViewController != null) {
            int iClockHiddenMode = clockHiddenMode();
            if (z2 || !this.mCarrierHomeLogoViewController.userSetup) {
                iClockHiddenMode = 8;
            }
            animateHiddenState(this.mCarrierHomeLogoViewController.logoView, iClockHiddenMode, z);
        }
    }

    public final void initOperatorName() {
        int slotIndex = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId());
        if (this.mCarrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN, slotIndex, new Object[0])) {
            ViewStub viewStub = (ViewStub) this.mStatusBar.findViewById(R.id.carrier_logo_container);
            if (viewStub != null) {
                CarrierHomeLogoViewController carrierHomeLogoViewControllerCreate = this.mCarrierLogoViewControllerFactory.create(viewStub.inflate(), slotIndex);
                this.mCarrierHomeLogoViewController = carrierHomeLogoViewControllerCreate;
                carrierHomeLogoViewControllerCreate.init();
            }
            if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                hideCarrierLogo(false, false);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        boolean z = this.mWaitingForWindowStateChangeAfterCameraLaunch;
        collapsedStatusBarFragmentExt.getClass();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP && ((DisplayLifecycle) collapsedStatusBarFragmentExt.displayLifecycleLazy.get()).mIsFolderOpened) {
            z = true;
        } else {
            collapsedStatusBarFragmentExt.postUpdateStatusBarVisibility();
        }
        this.mWaitingForWindowStateChangeAfterCameraLaunch = z;
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StatusBarWindowStateController statusBarWindowStateController = this.mStatusBarWindowStateController;
        ((HashSet) statusBarWindowStateController.listeners).add(this.mStatusBarWindowStateListener);
        this.mDemoModeController.addCallback((DemoMode) this.mDemoModeCallback);
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.status_bar, viewGroup, false);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        StatusBarWindowStateController statusBarWindowStateController = this.mStatusBarWindowStateController;
        ((HashSet) statusBarWindowStateController.listeners).remove(this.mStatusBarWindowStateListener);
        this.mDemoModeController.removeCallback((DemoMode) this.mDemoModeCallback);
    }

    @Override // android.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        if (this.mHomeStatusBarComponent == null) {
            return;
        }
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).removeIconGroup(this.mDarkIconManager);
        this.mCarrierConfigTracker.removeCallback((CarrierConfigTracker.CarrierConfigChangedListener) this.mCarrierConfigCallback);
        this.mCarrierConfigTracker.removeDataSubscriptionChangedListener(this.mDefaultDataListener);
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mHomeStatusBarComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, HomeStatusBarComponent$Startable$State.STOPPING);
            View view = statusBarBoundsProvider.startSideContent;
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            view.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            statusBarBoundsProvider.endSideContent.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, HomeStatusBarComponent$Startable$State.STOPPED);
        }
        this.mDumpManager.unregisterDumpable(getDumpableName());
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        ((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt.keyguardStateControllerLazy.get())).removeCallback(collapsedStatusBarFragmentExt);
        collapsedStatusBarFragmentExt.updateRunnable = null;
        collapsedStatusBarFragmentExt.cameraManager.unregisterAvailabilityCallback(collapsedStatusBarFragmentExt.cameraListener);
        this.mOngoingActivityController.ongoingActivityListener = null;
        this.mChipAnimationController = null;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        int i = StatusBarRootModernization.$r8$clinit;
        updateStatusBarVisibilities(false);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mHomeStatusBarComponent == null) {
            return;
        }
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.removeCallback(this);
        int i = StatusBarRootModernization.$r8$clinit;
        this.mOngoingCallController.removeCallback((OngoingCallListener) this.mOngoingCallListener);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).removeCallback(this);
        this.mSecureSettings.unregisterContentObserverSync(this.mVolumeSettingObserver);
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onPrepareSystemEventAnimation(boolean z) {
        if (!z) {
            return new AnimatorSet();
        }
        PrivacyLogger privacyLogger = this.mPrivacyLogger;
        privacyLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = privacyLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = 0;
        logBuffer.commit(logMessageObtain);
        final StatusBarSystemEventDefaultAnimator statusBarSystemEventDefaultAnimator = this.mSystemEventAnimator;
        statusBarSystemEventDefaultAnimator.getClass();
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(AnimationUtil.Companion.getFrames(1));
        valueAnimatorOfFloat.setInterpolator(null);
        valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.fragment.StatusBarSystemEventDefaultAnimator$hideAnimatedViewWithAlpha$1$1
            @Override // androidx.core.animation.Animator.AnimatorUpdateListener
            public final void onAnimationUpdate(Animator animator) {
                statusBarSystemEventDefaultAnimator.onAlphaChanged.mo781invoke((Float) valueAnimatorOfFloat.getAnimatedValue());
            }
        });
        return valueAnimatorOfFloat;
    }

    @Override // android.app.Fragment
    public final void onResume() throws Resources.NotFoundException {
        OngoingCallController.CallNotificationInfo callNotificationInfo;
        super.onResume();
        if (this.mHomeStatusBarComponent == null) {
            return;
        }
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.addCallback(this);
        int i = StatusBarRootModernization.$r8$clinit;
        this.mOngoingCallController.addCallback((OngoingCallListener) this.mOngoingCallListener);
        OngoingCallController ongoingCallController = this.mOngoingCallController;
        View view = this.mOngoingCallChip;
        ongoingCallController.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = StatusBarChipsModernization.$r8$clinit;
        ongoingCallController.tearDownChipView();
        ongoingCallController.chipView = view;
        OngoingCallBackgroundContainer ongoingCallBackgroundContainer = (OngoingCallBackgroundContainer) view.findViewById(R.id.ongoing_call_chip_background);
        OngoingCallChronometer ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time);
        OngoingCallControllerExt ongoingCallControllerExt = ongoingCallController.samsungExt;
        if (!Intrinsics.areEqual(ongoingCallControllerExt.timeView, ongoingCallChronometer)) {
            ongoingCallControllerExt.timeView = ongoingCallChronometer;
            if (ongoingCallChronometer != null) {
                boolean z = ongoingCallControllerExt.isShowingOAChip;
                if (ongoingCallChronometer.isShowingOAChip != z) {
                    ongoingCallChronometer.isShowingOAChip = z;
                    ongoingCallChronometer.requestLayout();
                }
                ongoingCallChronometer.indicatorGardenPresenter = ongoingCallControllerExt.indicatorGardenPresenter;
                ongoingCallChronometer.slimIndicatorViewMediator = ongoingCallControllerExt.slimIndicatorViewMediator;
            }
        }
        if (ongoingCallBackgroundContainer != null) {
            ongoingCallBackgroundContainer.maxHeightFetcher = new OngoingCallController$$ExternalSyntheticLambda0(ongoingCallController, 1);
        }
        if (ongoingCallController.hasOngoingCall() || ((callNotificationInfo = ongoingCallController.callNotificationInfo) != null && callNotificationInfo.isOngoing)) {
            ongoingCallController.updateChip();
        }
        this.mOngoingCallController.parent = this.mStatusBar;
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this);
        this.mOngoingActivityController.initCapsuleLayout(this.mPrimaryOngoingActivityChip, this.mStatusBar);
        this.mSecureSettings.registerContentObserverForUserSync("status_bar_show_vibrate_icon", false, (ContentObserver) this.mVolumeSettingObserver, -1);
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
        this.mStatusBar.saveHierarchyState(sparseArray);
        bundle.putSparseParcelableArray("panel_state", sparseArray);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        ((CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get()).postUpdateStatusBarVisibility();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
        if (!z) {
            return this.mSystemEventAnimator.onSystemEventAnimationBegin(z, z2);
        }
        this.mOngoingCallController.samsungExt.blockClickListener = true;
        this.mOngoingActivityController.blockClickListener = true;
        return new SpringAnimatorSet();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        SpringAnimatorSet springAnimatorSetOnSystemEventAnimationFinish = this.mSystemEventAnimator.onSystemEventAnimationFinish(z, z2, z3);
        springAnimatorSetOnSystemEventAnimationFinish.addListener(new AnonymousClass6(z2));
        return springAnimatorSetOnSystemEventAnimationFinish;
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        DarkIconDispatcher darkIconDispatcher;
        final int i = 0;
        final int i2 = 1;
        super.onViewCreated(view, bundle);
        DumpManager dumpManager = this.mDumpManager;
        String dumpableName = getDumpableName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, dumpableName, this);
        int displayId = view.getContext().getDisplayId();
        StatusBarConfigurationController statusBarConfigurationController = (StatusBarConfigurationController) this.mStatusBarConfigurationControllerStore.forDisplay(displayId);
        if (statusBarConfigurationController == null) {
            return;
        }
        PhoneStatusBarView phoneStatusBarView = (PhoneStatusBarView) view;
        this.mStatusBar = phoneStatusBarView;
        initOperatorName();
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.mStatusBarWindowControllerStore.forDisplay(displayId);
        if (statusBarWindowController == null || (darkIconDispatcher = (DarkIconDispatcher) ((DarkIconDispatcherStoreImpl) this.mDarkIconDispatcherStore).forDisplay(displayId)) == null) {
            return;
        }
        HomeStatusBarComponent homeStatusBarComponentCreate = this.mHomeStatusBarComponentFactory.create((PhoneStatusBarView) getView(), statusBarConfigurationController, statusBarWindowController, darkIconDispatcher);
        this.mHomeStatusBarComponent = homeStatusBarComponentCreate;
        ((DaggerReferenceGlobalRootComponent.HomeStatusBarComponentImpl) homeStatusBarComponentCreate).init();
        ((ArrayMap) this.mStartableStates).clear();
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mHomeStatusBarComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, HomeStatusBarComponent$Startable$State.STARTING);
            View view2 = statusBarBoundsProvider.startSideContent;
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            view2.addOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            statusBarBoundsProvider.endSideContent.addOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, HomeStatusBarComponent$Startable$State.STARTED);
        }
        this.mStatusBar = phoneStatusBarView;
        if (bundle != null && bundle.containsKey("panel_state")) {
            this.mStatusBar.restoreHierarchyState(bundle.getSparseParcelableArray("panel_state"));
        }
        DarkIconManager darkIconManagerCreate = this.mDarkIconManagerFactory.create((LinearLayout) view.findViewById(R.id.statusIcons), StatusBarLocation.HOME, this.mHomeStatusBarComponent.getDarkIconDispatcher());
        this.mDarkIconManager = darkIconManagerCreate;
        darkIconManagerCreate.mShouldLog = true;
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(darkIconManagerCreate);
        this.mEndSideAlphaController = new MultiSourceMinAlphaController((LinearLayout) this.mStatusBar.findViewById(R.id.status_bar_end_side_content));
        this.mClockView = this.mStatusBar.findViewById(R.id.clock);
        this.mOngoingCallChip = this.mStatusBar.findViewById(R.id.ongoing_call_chip);
        this.mPrimaryOngoingActivityChip = this.mStatusBar.findViewById(R.id.ongoing_activity_capsule);
        int i3 = StatusBarRootModernization.$r8$clinit;
        showEndSideContent(false);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        animateShow(this.mClockView, false);
        Trace.beginSection("CollapsedStatusBarFragment#initNotifIconArea");
        ViewGroup viewGroup = (ViewGroup) this.mStatusBar.requireViewById(R.id.notification_icon_area);
        int i4 = NotificationIconContainerRefactor.$r8$clinit;
        View notificationInnerAreaView = this.mNotificationIconAreaController.getNotificationInnerAreaView();
        this.mNotificationIconAreaInner = notificationInnerAreaView;
        if (notificationInnerAreaView.getParent() != null) {
            ((ViewGroup) this.mNotificationIconAreaInner.getParent()).removeView(this.mNotificationIconAreaInner);
        }
        viewGroup.addView(this.mNotificationIconAreaInner);
        updateNotificationIconAreaAndOngoingActivityChip(false);
        Trace.endSection();
        this.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(getResources(), new Function1(this) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda3
            public final /* synthetic */ CollapsedStatusBarFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        this.f$0.mStatusBar.setAlpha(f.floatValue());
                        break;
                    default:
                        this.f$0.mStatusBar.setTranslationX(f.floatValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }, new Function1(this) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda3
            public final /* synthetic */ CollapsedStatusBarFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        this.f$0.mStatusBar.setAlpha(f.floatValue());
                        break;
                    default:
                        this.f$0.mStatusBar.setTranslationX(f.floatValue());
                        break;
                }
                return Unit.INSTANCE;
            }
        }, false);
        this.mCarrierConfigTracker.addCallback((CarrierConfigTracker.CarrierConfigChangedListener) this.mCarrierConfigCallback);
        this.mCarrierConfigTracker.addDefaultDataSubscriptionChangedListener(this.mDefaultDataListener);
        this.mHomeStatusBarViewModel = ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass124) this.mHomeStatusBarViewModelFactory).create(displayId);
        ((HomeStatusBarViewBinderImpl) this.mHomeStatusBarViewBinder).bind(view.getContext().getDisplayId(), this.mStatusBar, this.mHomeStatusBarViewModel, null, null, this.mStatusBarVisibilityChangeListener);
        ((CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get()).updateRunnable = new CollapsedStatusBarFragment$$ExternalSyntheticLambda1(this);
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        ((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt.keyguardStateControllerLazy.get())).addCallback(collapsedStatusBarFragmentExt);
        collapsedStatusBarFragmentExt.cameraManager.registerAvailabilityCallback(collapsedStatusBarFragmentExt.bgExecutor, collapsedStatusBarFragmentExt.cameraListener);
        this.mChipAnimationController = new ChipAnimationController(this.mStatusBar, this.mNotificationIconAreaController, this.mOngoingActivityController, new Function0() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((SlimIndicatorViewMediatorImpl) ((CollapsedStatusBarFragmentExt) this.f$0.mSamsungExtLazy.get()).slimIndicatorViewMediator).isLeftClockPosition());
            }
        });
        this.mOngoingActivityController.ongoingActivityListener = new OngoingActivityListenerImpl(this, i);
    }

    public final void showEndSideContent(boolean z) {
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (z && this.mAnimationsEnabled) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            if (keyguardStateControllerImpl.mKeyguardFadingAway) {
                this.mEndSideAlphaController.animateToAlpha(1.0f, keyguardStateControllerImpl.mKeyguardFadingAwayDuration, InterpolatorsAndroidX.LINEAR_OUT_SLOW_IN, keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
                return;
            } else {
                this.mEndSideAlphaController.animateToAlpha(1.0f, 320L, InterpolatorsAndroidX.ALPHA_IN, 50L);
                return;
            }
        }
        MultiSourceMinAlphaController multiSourceMinAlphaController = this.mEndSideAlphaController;
        ValueAnimator valueAnimator = (ValueAnimator) ((LinkedHashMap) multiSourceMinAlphaController.animators).get(2);
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        multiSourceMinAlphaController.updateAlpha(1.0f, 2);
    }

    public void updateBlockedIcons() {
        ((ArrayList) this.mBlockedIcons).clear();
    }

    public final void updateNotificationIconAreaAndOngoingActivityChip(boolean z) {
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        StatusBarVisibilityModel statusBarVisibilityModel = this.mLastModifiedVisibility;
        boolean z2 = statusBarVisibilityModel.showNotificationIcons;
        boolean z3 = false;
        boolean z4 = this.mOngoingCallController.hasOngoingCall() && statusBarVisibilityModel.showOngoingCallChip;
        boolean z5 = statusBarVisibilityModel.showPrimaryOngoingActivityChip;
        if (!z2) {
            boolean z6 = (!z || z5 || z4) ? false : true;
            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() != 2) {
                animateHiddenState(this.mNotificationIconAreaInner, 4, z6);
            }
        } else if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() != 2) {
            animateShow(this.mNotificationIconAreaInner, z);
        }
        boolean z7 = z4 && z2;
        boolean z8 = z5 && z2;
        if (z7) {
            animateShow(this.mOngoingCallChip, z && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && this.mShadeExpansionStateManager.isClosed());
            this.mOngoingCallController.hideTimeViewByOngoingChip(z8);
        } else {
            animateHiddenState(this.mOngoingCallChip, 8, !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && this.mShadeExpansionStateManager.isClosed());
        }
        if (z8) {
            if (z && this.mOngoingActivityController.isScreenTurnedOn) {
                z3 = true;
            }
            animateShow(this.mPrimaryOngoingActivityChip, z3);
            if (this.mOngoingCallController.hasOngoingCall() && this.mLastModifiedVisibility.showOngoingCallChip) {
                this.mOngoingCallController.hideTimeViewByOngoingChip(true);
            }
        } else {
            OngoingCallController ongoingCallController = this.mOngoingCallController;
            OngoingCallController.CallNotificationInfo callNotificationInfo = ongoingCallController.callNotificationInfo;
            if (callNotificationInfo != null && callNotificationInfo.isOngoing && !ongoingCallController.hasOngoingCall()) {
                Log.d("CollapsedStatusBarFragment", "updateNotificationIconAreaAndOngoingActivityChip()  Skip hide animation as status bar is not visible");
                this.mPrimaryOngoingActivityChip.setVisibility(4);
                return;
            } else {
                animateHiddenState(this.mPrimaryOngoingActivityChip, 8, !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && this.mShadeExpansionStateManager.isClosed());
                if (this.mOngoingCallController.hasOngoingCall() && this.mLastModifiedVisibility.showOngoingCallChip) {
                    this.mOngoingCallController.hideTimeViewByOngoingChip(false);
                }
            }
        }
        int i2 = StatusBarNotifChips.$r8$clinit;
        animateHiddenState(null, 8, z);
    }

    public final void updateStatusBarVisibilities(boolean z) {
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        StatusBarVisibilityModel statusBarVisibilityModelCalculateInternalModel = calculateInternalModel(this.mLastSystemVisibility);
        collapsedStatusBarFragmentExt.updateStatusBarVisibilitiesCallers = Debug.getCallers(6);
        if (((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt.keyguardStateControllerLazy.get())).mOccluded && (statusBarVisibilityModelCalculateInternalModel.showClock || statusBarVisibilityModelCalculateInternalModel.showNotificationIcons || statusBarVisibilityModelCalculateInternalModel.showOngoingCallChip || statusBarVisibilityModelCalculateInternalModel.showPrimaryOngoingActivityChip || statusBarVisibilityModelCalculateInternalModel.showSecondaryOngoingActivityChip || statusBarVisibilityModelCalculateInternalModel.showSystemInfo)) {
            collapsedStatusBarFragmentExt.postUpdateStatusBarVisibility();
        } else {
            updateStatusBarVisibilitiesInner(z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateStatusBarVisibilitiesInner(boolean z) {
        View view;
        View view2;
        CarrierLogoView carrierLogoView;
        int i = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        StatusBarVisibilityModel statusBarVisibilityModel = this.mLastModifiedVisibility;
        StatusBarVisibilityModel statusBarVisibilityModelCalculateInternalModel = calculateInternalModel(this.mLastSystemVisibility);
        CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger = this.mCollapsedStatusBarFragmentLogger;
        collapsedStatusBarFragmentLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        CollapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1 collapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1 = new CollapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1();
        LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("CollapsedSbFragment", logLevel, collapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        boolean z2 = statusBarVisibilityModelCalculateInternalModel.showClock;
        logMessageImpl.bool1 = z2;
        boolean z3 = statusBarVisibilityModelCalculateInternalModel.showNotificationIcons;
        logMessageImpl.bool2 = z3;
        boolean z4 = statusBarVisibilityModelCalculateInternalModel.showPrimaryOngoingActivityChip;
        logMessageImpl.bool3 = z4;
        boolean z5 = statusBarVisibilityModelCalculateInternalModel.showSecondaryOngoingActivityChip;
        logMessageImpl.int1 = z5 ? 1 : 0;
        boolean z6 = statusBarVisibilityModelCalculateInternalModel.showSystemInfo;
        logMessageImpl.bool4 = z6;
        logBuffer.commit(logMessageObtain);
        this.mLastModifiedVisibility = statusBarVisibilityModelCalculateInternalModel;
        if (z6 != statusBarVisibilityModel.showSystemInfo) {
            if (z6) {
                showEndSideContent(z);
            } else if (z && this.mAnimationsEnabled) {
                this.mEndSideAlphaController.animateToAlpha(0.0f, 160L, InterpolatorsAndroidX.ALPHA_OUT, 0L);
            } else {
                MultiSourceMinAlphaController multiSourceMinAlphaController = this.mEndSideAlphaController;
                ValueAnimator valueAnimator = (ValueAnimator) ((LinkedHashMap) multiSourceMinAlphaController.animators).get(2);
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                multiSourceMinAlphaController.updateAlpha(0.0f, 2);
            }
        }
        Object[] objArr = z3 != statusBarVisibilityModel.showNotificationIcons;
        boolean z7 = statusBarVisibilityModel.showOngoingCallChip;
        boolean z8 = statusBarVisibilityModelCalculateInternalModel.showOngoingCallChip;
        Object[] objArr2 = z8 != z7;
        Object[] objArr3 = (z4 == statusBarVisibilityModel.showPrimaryOngoingActivityChip && z5 == statusBarVisibilityModel.showSecondaryOngoingActivityChip) ? false : true;
        if (objArr2 != false && (view2 = this.mOngoingCallChip) != null && ((z8 && view2.getVisibility() != 0) || (!z8 && this.mOngoingCallChip.getVisibility() == 0))) {
            this.mClockView.setAlpha(1.0f);
            CarrierHomeLogoViewController carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
            if (carrierHomeLogoViewController != null && (carrierLogoView = carrierHomeLogoViewController.logoView) != null) {
                carrierLogoView.setAlpha(0.0f);
            }
            this.mNotificationIconAreaInner.setAlpha(0.0f);
        }
        if (objArr != false || objArr3 != false || objArr2 != false) {
            updateNotificationIconAreaAndOngoingActivityChip(z);
        }
        if (z2 != statusBarVisibilityModel.showClock || ((view = this.mClockView) != null && view.getVisibility() != clockHiddenMode())) {
            if (z2) {
                animateShow(this.mClockView, z);
                if (z8) {
                    hideCarrierLogo(false, true);
                } else {
                    CarrierHomeLogoViewController carrierHomeLogoViewController2 = this.mCarrierHomeLogoViewController;
                    if (carrierHomeLogoViewController2 != null) {
                        animateShow(carrierHomeLogoViewController2.logoView, z);
                        this.mCarrierHomeLogoViewController.updateCarrierLogoVisibility();
                    }
                }
            } else {
                animateHiddenState(this.mClockView, clockHiddenMode(), z);
                hideCarrierLogo(z, z8);
            }
        }
        CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = (CollapsedStatusBarFragmentExt) this.mSamsungExtLazy.get();
        collapsedStatusBarFragmentExt.getClass();
        if (statusBarVisibilityModel.equals(statusBarVisibilityModelCalculateInternalModel)) {
            return;
        }
        Log.d("CollapsedStatusBarFragmentExt", "prv:" + statusBarVisibilityModel);
        Log.d("CollapsedStatusBarFragmentExt", "new:" + statusBarVisibilityModelCalculateInternalModel);
        Log.d("CollapsedStatusBarFragmentExt", collapsedStatusBarFragmentExt.updateStatusBarVisibilitiesCallers);
    }
}
