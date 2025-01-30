package com.android.systemui.statusbar.phone.fragment;

import android.app.Fragment;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.OperatorNameViewController$Factory;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.phone.CarrierHomeLogoViewController;
import com.android.systemui.statusbar.phone.CarrierLogoView;
import com.android.systemui.statusbar.phone.CarrierLogoVisibilityManager;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider$layoutListener$1;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent$Startable$State;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.shared.p028ui.binder.CollapsedStatusBarViewBinder;
import com.android.systemui.statusbar.pipeline.shared.p028ui.binder.CollapsedStatusBarViewBinderImpl;
import com.android.systemui.statusbar.pipeline.shared.p028ui.binder.StatusBarVisibilityChangeListener;
import com.android.systemui.statusbar.pipeline.shared.p028ui.viewmodel.CollapsedStatusBarViewModel;
import com.android.systemui.statusbar.pipeline.shared.p028ui.viewmodel.CollapsedStatusBarViewModelImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CollapsedStatusBarFragment extends Fragment implements CommandQueue.Callbacks, StatusBarStateController.StateListener, SystemStatusAnimationCallback, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SystemStatusAnimationScheduler mAnimationScheduler;
    public final List mBlockedIcons;
    public final C31812 mCarrierConfigCallback;
    public final CarrierConfigTracker mCarrierConfigTracker;
    public CarrierHomeLogoViewController mCarrierHomeLogoViewController;
    public final CarrierInfraMediator mCarrierInfraMediator;
    public final CarrierHomeLogoViewController.Factory mCarrierLogoViewControllerFactory;
    public View mClockView;
    public final CollapsedStatusBarFragmentLogger mCollapsedStatusBarFragmentLogger;
    public final CollapsedStatusBarViewBinder mCollapsedStatusBarViewBinder;
    public final CollapsedStatusBarViewModel mCollapsedStatusBarViewModel;
    public final CommandQueue mCommandQueue;
    public StatusBarIconController.DarkIconManager mDarkIconManager;
    public final StatusBarIconController.DarkIconManager.Factory mDarkIconManagerFactory;
    public final C31823 mDefaultDataListener;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DumpManager mDumpManager;
    public LinearLayout mEndSideContent;
    public final Handler mHandler;
    public boolean mIsFolderOpened;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public StatusBarVisibilityModel mLastModifiedVisibility;
    public StatusBarVisibilityModel mLastSystemVisibility;
    public final StatusBarLocationPublisher mLocationPublisher;
    public final Executor mMainExecutor;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public View mNotificationIconAreaInner;
    public View mOngoingCallChip;
    public final OngoingCallController mOngoingCallController;
    public final C31801 mOngoingCallListener;
    public final PrivacyLogger mPrivacyLogger;
    public final SecureSettings mSecureSettings;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeViewController mShadeViewController;
    public final Map mStartableStates;
    public PhoneStatusBarView mStatusBar;
    public StatusBarFragmentComponent mStatusBarFragmentComponent;
    public final StatusBarFragmentComponent.Factory mStatusBarFragmentComponentFactory;
    public final StatusBarHideChecker mStatusBarHideChecker;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarIconController mStatusBarIconController;
    public final CollapsedStatusBarFragment$$ExternalSyntheticLambda1 mStatusBarLayoutListener;
    public final StatusBarStateController mStatusBarStateController;
    public final C31845 mStatusBarVisibilityChangeListener;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final CollapsedStatusBarFragment$$ExternalSyntheticLambda0 mStatusBarWindowStateListener;
    public StatusBarSystemEventAnimator mSystemEventAnimator;
    public boolean mTransitionFromLockscreenToDreamStarted;
    public final C31867 mVolumeSettingObserver;
    public boolean mWaitingForWindowStateChangeAfterCameraLaunch;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$2 */
    public final class C31812 {
        public C31812() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$5 */
    public final class C31845 implements StatusBarVisibilityChangeListener {
        public C31845() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$6 */
    public final class C31856 extends AnimatorListenerAdapter {
        public final /* synthetic */ boolean val$statusBarHidden;

        public C31856(boolean z) {
            this.val$statusBarHidden = z;
        }

        @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
        public final void onAnimationEnd$1(Animator animator) {
            if (this.val$statusBarHidden) {
                CollapsedStatusBarFragment.this.mHandler.postDelayed(new CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0(this, 0), 390L);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatusBarHideChecker implements KeyguardStateController.Callback {
        public final CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0 mValidateStatusBarVisibility;

        public /* synthetic */ StatusBarHideChecker(CollapsedStatusBarFragment collapsedStatusBarFragment, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            printStatusBarInfoLog("onKeyguardShowingChanged()");
            CollapsedStatusBarFragment.this.updateStatusBarVisibilities(false);
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onLaunchTransitionFadingAwayChanged() {
            printStatusBarInfoLog("onLaunchTransitionFadingAwayChanged()");
            CollapsedStatusBarFragment.this.updateStatusBarVisibilities(false);
        }

        public final void postUpdateStatusBarVisibility() {
            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
            Handler handler = collapsedStatusBarFragment.mHandler;
            CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0 collapsedStatusBarFragment$6$$ExternalSyntheticLambda0 = this.mValidateStatusBarVisibility;
            if (handler.hasCallbacks(collapsedStatusBarFragment$6$$ExternalSyntheticLambda0)) {
                collapsedStatusBarFragment.mHandler.removeCallbacks(collapsedStatusBarFragment$6$$ExternalSyntheticLambda0);
            }
            collapsedStatusBarFragment.mHandler.postDelayed(collapsedStatusBarFragment$6$$ExternalSyntheticLambda0, 300L);
        }

        public final void printStatusBarInfoLog(String str) {
            StringBuilder sb = new StringBuilder(str);
            sb.append(", mIsFolderOpened:");
            CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
            sb.append(collapsedStatusBarFragment.mIsFolderOpened);
            sb.append(", windowIsShowing:");
            sb.append(collapsedStatusBarFragment.mStatusBarWindowStateController.windowState == 0);
            sb.append(", isOccluded:");
            sb.append(((KeyguardStateControllerImpl) collapsedStatusBarFragment.mKeyguardStateController).mOccluded);
            sb.append(", isDozing:");
            sb.append(collapsedStatusBarFragment.mStatusBarStateController.isDozing());
            sb.append(", isClosed:");
            sb.append(collapsedStatusBarFragment.mShadeExpansionStateManager.state == 0);
            sb.append(", isShowing:");
            sb.append(((KeyguardStateControllerImpl) collapsedStatusBarFragment.mKeyguardStateController).mShowing);
            sb.append(", SH0-Lock1-SHLock2:");
            sb.append(collapsedStatusBarFragment.mStatusBarStateController.getState());
            sb.append(", isSecureCameraLaunchedOverKeyguard:");
            sb.append(collapsedStatusBarFragment.mKeyguardUpdateMonitor.mSecureCameraLaunched);
            sb.append(", mTransitionFromLockscreenToDreamStarted:");
            sb.append(collapsedStatusBarFragment.mTransitionFromLockscreenToDreamStarted);
            sb.append(", mWaitingForWindowStateChangeAfterCameraLaunch:");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, collapsedStatusBarFragment.mWaitingForWindowStateChangeAfterCameraLaunch, "CollapsedStatusBarFragment");
        }

        private StatusBarHideChecker() {
            this.mValidateStatusBarVisibility = new CollapsedStatusBarFragment$6$$ExternalSyntheticLambda0(this, 1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$7] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$3] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0] */
    public CollapsedStatusBarFragment(StatusBarFragmentComponent.Factory factory, OngoingCallController ongoingCallController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, StatusBarLocationPublisher statusBarLocationPublisher, NotificationIconAreaController notificationIconAreaController, ShadeExpansionStateManager shadeExpansionStateManager, FeatureFlags featureFlags, StatusBarIconController statusBarIconController, StatusBarIconController.DarkIconManager.Factory factory2, CollapsedStatusBarViewModel collapsedStatusBarViewModel, CollapsedStatusBarViewBinder collapsedStatusBarViewBinder, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, KeyguardStateController keyguardStateController, ShadeViewController shadeViewController, StatusBarStateController statusBarStateController, CommandQueue commandQueue, CarrierConfigTracker carrierConfigTracker, CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger, OperatorNameViewController$Factory operatorNameViewController$Factory, SecureSettings secureSettings, Executor executor, DumpManager dumpManager, StatusBarWindowStateController statusBarWindowStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, PrivacyLogger privacyLogger, CarrierInfraMediator carrierInfraMediator, CarrierHomeLogoViewController.Factory factory3, DisplayLifecycle displayLifecycle) {
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
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.mStatusBarHideChecker.printStatusBarInfoLog("onOngoingCallStateChanged(animate:true)");
                collapsedStatusBarFragment.updateStatusBarVisibilities(true);
                collapsedStatusBarFragment.mNotificationIconAreaController.updateStatusBarIcons();
            }
        };
        this.mCarrierConfigCallback = new C31812();
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
        this.mTransitionFromLockscreenToDreamStarted = false;
        this.mStatusBarWindowStateListener = new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.mWaitingForWindowStateChangeAfterCameraLaunch = false;
                collapsedStatusBarFragment.mTransitionFromLockscreenToDreamStarted = false;
                collapsedStatusBarFragment.updateStatusBarVisibilities(false);
            }
        };
        this.mIsFolderOpened = true;
        this.mStatusBarVisibilityChangeListener = new C31845();
        this.mVolumeSettingObserver = new ContentObserver(null) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                CollapsedStatusBarFragment.this.updateBlockedIcons();
            }
        };
        this.mStatusBarLayoutListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.getClass();
                if (i == i5 && i3 == i7) {
                    return;
                }
                collapsedStatusBarFragment.updateStatusBarLocation(i, i3);
            }
        };
        this.mStatusBarHideChecker = new StatusBarHideChecker(this, 0);
        this.mStatusBarFragmentComponentFactory = factory;
        this.mOngoingCallController = ongoingCallController;
        this.mAnimationScheduler = systemStatusAnimationScheduler;
        this.mLocationPublisher = statusBarLocationPublisher;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mStatusBarIconController = statusBarIconController;
        this.mCollapsedStatusBarViewModel = collapsedStatusBarViewModel;
        this.mCollapsedStatusBarViewBinder = collapsedStatusBarViewBinder;
        this.mStatusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.mDarkIconManagerFactory = factory2;
        this.mKeyguardStateController = keyguardStateController;
        this.mShadeViewController = shadeViewController;
        this.mStatusBarStateController = statusBarStateController;
        this.mCommandQueue = commandQueue;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mCollapsedStatusBarFragmentLogger = collapsedStatusBarFragmentLogger;
        this.mSecureSettings = secureSettings;
        this.mMainExecutor = executor;
        this.mDumpManager = dumpManager;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mHandler = handler;
        this.mPrivacyLogger = privacyLogger;
        this.mCarrierInfraMediator = carrierInfraMediator;
        this.mCarrierLogoViewControllerFactory = factory3;
        this.mDisplayLifecycle = displayLifecycle;
    }

    public static void animateHiddenState(final View view, final int i, boolean z) {
        view.animate().cancel();
        if (z) {
            view.animate().alpha(0.0f).setDuration(160L).setStartDelay(0L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    view.setVisibility(i);
                }
            });
        } else {
            view.setAlpha(0.0f);
            view.setVisibility(i);
        }
    }

    public final void animateShow(View view, boolean z) {
        view.animate().cancel();
        view.setVisibility(0);
        if (!z) {
            view.setAlpha(1.0f);
            return;
        }
        view.animate().alpha(1.0f).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).setStartDelay(50L).withEndAction(null);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAway) {
            view.animate().setDuration(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAwayDuration).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setStartDelay(((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardFadingAwayDelay).start();
        }
    }

    public final int clockHiddenMode() {
        return ((this.mShadeExpansionStateManager.state == 0) || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing || this.mStatusBarStateController.isDozing()) ? 8 : 4;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i != getContext().getDisplayId()) {
            return;
        }
        final CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger = this.mCollapsedStatusBarFragmentLogger;
        DisableFlagsLogger.DisableState disableState = new DisableFlagsLogger.DisableState(i2, i3);
        collapsedStatusBarFragmentLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$logDisableFlagChange$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return CollapsedStatusBarFragmentLogger.this.disableFlagsLogger.getDisableFlagsString(null, new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), null);
            }
        };
        LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
        LogMessage obtain = logBuffer.obtain("CollapsedSbFragment", logLevel, function1, null);
        obtain.setInt1(disableState.disable1);
        obtain.setInt2(disableState.disable2);
        logBuffer.commit(obtain);
        StatusBarVisibilityModel.Companion.getClass();
        this.mLastSystemVisibility = StatusBarVisibilityModel.Companion.createModelFromFlags(i2, i3);
        updateStatusBarVisibilities(z);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        StatusBarFragmentComponent statusBarFragmentComponent = this.mStatusBarFragmentComponent;
        if (statusBarFragmentComponent == null) {
            indentingPrintWriter.println("StatusBarFragmentComponent is null");
            return;
        }
        Set<StatusBarBoundsProvider> startables = statusBarFragmentComponent.getStartables();
        indentingPrintWriter.println("Startables: " + startables.size());
        indentingPrintWriter.increaseIndent();
        for (StatusBarBoundsProvider statusBarBoundsProvider : startables) {
            indentingPrintWriter.println(statusBarBoundsProvider + ", state: " + ((StatusBarFragmentComponent$Startable$State) this.mStartableStates.getOrDefault(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.NONE)));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public List<String> getBlockedIcons() {
        return this.mBlockedIcons;
    }

    public final void hideCarrierLogo(boolean z, boolean z2) {
        if (this.mCarrierHomeLogoViewController != null) {
            int clockHiddenMode = clockHiddenMode();
            if (z2 || !this.mCarrierHomeLogoViewController.userSetup) {
                clockHiddenMode = 8;
            }
            animateHiddenState(this.mCarrierHomeLogoViewController.logoView, clockHiddenMode, z);
        }
    }

    public final void initOperatorName() {
        CollapsedStatusBarFragment collapsedStatusBarFragment = this;
        int slotIndex = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId());
        CarrierInfraMediator carrierInfraMediator = collapsedStatusBarFragment.mCarrierInfraMediator;
        CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN;
        if (carrierInfraMediator.isEnabled(conditions, slotIndex, new Object[0])) {
            ViewStub viewStub = (ViewStub) collapsedStatusBarFragment.mStatusBar.findViewById(R.id.carrier_logo_container);
            if (viewStub != null) {
                CarrierHomeLogoViewController.Factory factory = collapsedStatusBarFragment.mCarrierLogoViewControllerFactory;
                View inflate = viewStub.inflate();
                CarrierHomeLogoViewController carrierHomeLogoViewController = new CarrierHomeLogoViewController(inflate, factory.broadcastDispatcher, factory.dumpManager, factory.settingsHelper, factory.configurationController, new CarrierLogoVisibilityManager(factory.carrierInfraMediator, conditions, factory.simCardInfoUtil, factory.telephonyManager), slotIndex, factory.carrierInfraMediator, factory.darkIconDispatcher, factory.slimIndicatorViewMediator, (CarrierLogoView) inflate.findViewById(R.id.carrier_logo), factory.indicatorScaleGardener, factory.subscriptionManager, factory.simCardInfoUtil, factory.carrierConfigTracker, factory.deviceProvisionedController);
                collapsedStatusBarFragment = this;
                collapsedStatusBarFragment.mCarrierHomeLogoViewController = carrierHomeLogoViewController;
                carrierHomeLogoViewController.init();
            }
            if (((KeyguardStateControllerImpl) collapsedStatusBarFragment.mKeyguardStateController).mShowing) {
                collapsedStatusBarFragment.hideCarrierLogo(false, false);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        if (this.mIsFolderOpened) {
            this.mWaitingForWindowStateChangeAfterCameraLaunch = true;
        } else {
            Log.d("CollapsedStatusBarFragment", "Skip onCameraLaunchGestureDetected since it's launched from cover screen");
        }
        this.mStatusBarHideChecker.printStatusBarInfoLog("onCameraLaunchGestureDetected(source:" + i + ")");
        this.mStatusBarHideChecker.postUpdateStatusBarVisibility();
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StatusBarWindowStateController statusBarWindowStateController = this.mStatusBarWindowStateController;
        ((HashSet) statusBarWindowStateController.listeners).add(this.mStatusBarWindowStateListener);
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
    }

    @Override // android.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).removeIconGroup(this.mDarkIconManager);
        CarrierConfigTracker carrierConfigTracker = this.mCarrierConfigTracker;
        ((ArraySet) carrierConfigTracker.mListeners).remove(this.mCarrierConfigCallback);
        CarrierConfigTracker carrierConfigTracker2 = this.mCarrierConfigTracker;
        ((ArraySet) carrierConfigTracker2.mDataListeners).remove(this.mDefaultDataListener);
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mStatusBarFragmentComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STOPPING);
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            statusBarBoundsProvider.startSideContent.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            statusBarBoundsProvider.endSideContent.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STOPPED);
        }
        this.mDumpManager.unregisterDumpable("CollapsedStatusBarFragment");
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mStatusBarHideChecker);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        this.mStatusBarHideChecker.printStatusBarInfoLog("onDozingChanged(isDozing:" + z + ")");
        updateStatusBarVisibilities(false);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.removeCallback(this);
        this.mOngoingCallController.removeCallback((OngoingCallListener) this.mOngoingCallListener);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).removeCallback(this);
        this.mSecureSettings.unregisterContentObserver(this.mVolumeSettingObserver);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.addCallback(this);
        this.mOngoingCallController.addCallback((OngoingCallListener) this.mOngoingCallListener);
        this.mOngoingCallController.setChipView(this.mOngoingCallChip);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this);
        this.mSecureSettings.registerContentObserverForUser("status_bar_show_vibrate_icon", false, (ContentObserver) this.mVolumeSettingObserver, -1);
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
        this.mStatusBarHideChecker.printStatusBarInfoLog("onStateChanged(newState:" + i + ")");
        this.mStatusBarHideChecker.postUpdateStatusBarVisibility();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z) {
        if (!z) {
            return this.mSystemEventAnimator.onSystemEventAnimationBegin(z);
        }
        this.mPrivacyLogger.logStatusBarAlpha(0);
        StatusBarSystemEventAnimator statusBarSystemEventAnimator = this.mSystemEventAnimator;
        statusBarSystemEventAnimator.onAlphaChanged.invoke(Float.valueOf(0.0f));
        return new AnimatorSet();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
        Animator onSystemEventAnimationFinish = this.mSystemEventAnimator.onSystemEventAnimationFinish(z, z2);
        onSystemEventAnimationFinish.addListener(new C31856(z2));
        return onSystemEventAnimationFinish;
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "CollapsedStatusBarFragment", this);
        this.mStatusBarFragmentComponent = this.mStatusBarFragmentComponentFactory.create(this);
        PhoneStatusBarView phoneStatusBarView = (PhoneStatusBarView) view;
        this.mStatusBar = phoneStatusBarView;
        initOperatorName();
        this.mStatusBarFragmentComponent.init();
        ((ArrayMap) this.mStartableStates).clear();
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mStatusBarFragmentComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STARTING);
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            statusBarBoundsProvider.startSideContent.addOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            statusBarBoundsProvider.endSideContent.addOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STARTED);
        }
        this.mStatusBar = phoneStatusBarView;
        View findViewById = phoneStatusBarView.findViewById(R.id.status_bar_contents);
        findViewById.addOnLayoutChangeListener(this.mStatusBarLayoutListener);
        updateStatusBarLocation(findViewById.getLeft(), findViewById.getRight());
        if (bundle != null && bundle.containsKey("panel_state")) {
            this.mStatusBar.restoreHierarchyState(bundle.getSparseParcelableArray("panel_state"));
        }
        StatusBarIconController.DarkIconManager.Factory factory = this.mDarkIconManagerFactory;
        StatusBarIconController.DarkIconManager darkIconManager = new StatusBarIconController.DarkIconManager((LinearLayout) view.findViewById(R.id.statusIcons), StatusBarLocation.HOME, factory.mStatusBarPipelineFlags, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileContextProvider, factory.mBTTetherUiAdapter, factory.mDarkIconDispatcher);
        this.mDarkIconManager = darkIconManager;
        darkIconManager.mShouldLog = true;
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(darkIconManager);
        this.mEndSideContent = (LinearLayout) this.mStatusBar.findViewById(R.id.status_bar_end_side_content);
        this.mClockView = this.mStatusBar.findViewById(R.id.clock);
        this.mOngoingCallChip = this.mStatusBar.findViewById(R.id.ongoing_call_chip);
        ((Number) ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).animationState.getValue()).intValue();
        animateShow(this.mEndSideContent, false);
        animateShow(this.mClockView, false);
        ViewGroup viewGroup = (ViewGroup) this.mStatusBar.findViewById(R.id.notification_icon_area);
        View view2 = this.mNotificationIconAreaController.mNotificationIconArea;
        this.mNotificationIconAreaInner = view2;
        if (view2.getParent() != null) {
            ((ViewGroup) this.mNotificationIconAreaInner.getParent()).removeView(this.mNotificationIconAreaInner);
        }
        viewGroup.addView(this.mNotificationIconAreaInner);
        updateNotificationIconAreaAndCallChip(false);
        this.mSystemEventAnimator = new StatusBarSystemEventAnimator(this.mStatusBar, getResources());
        CarrierConfigTracker carrierConfigTracker = this.mCarrierConfigTracker;
        ((ArraySet) carrierConfigTracker.mListeners).add(this.mCarrierConfigCallback);
        CarrierConfigTracker carrierConfigTracker2 = this.mCarrierConfigTracker;
        ((ArraySet) carrierConfigTracker2.mDataListeners).add(this.mDefaultDataListener);
        ((CollapsedStatusBarViewBinderImpl) this.mCollapsedStatusBarViewBinder).bind(this.mStatusBar, this.mCollapsedStatusBarViewModel, this.mStatusBarVisibilityChangeListener);
        if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP) {
            this.mDisplayLifecycle.addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.4
                @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
                public final void onFolderStateChanged(boolean z) {
                    CollapsedStatusBarFragment.this.mIsFolderOpened = z;
                }
            });
        }
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mStatusBarHideChecker);
    }

    public void updateBlockedIcons() {
        ((ArrayList) this.mBlockedIcons).clear();
    }

    public final void updateNotificationIconAreaAndCallChip(boolean z) {
        StatusBarVisibilityModel statusBarVisibilityModel = this.mLastModifiedVisibility;
        boolean z2 = !statusBarVisibilityModel.showNotificationIcons;
        if (z2) {
            animateHiddenState(this.mNotificationIconAreaInner, 4, z);
        } else {
            animateShow(this.mNotificationIconAreaInner, z);
        }
        boolean z3 = statusBarVisibilityModel.showOngoingCallChip && !z2;
        if (z3) {
            animateShow(this.mOngoingCallChip, z);
        } else {
            animateHiddenState(this.mOngoingCallChip, 8, z);
        }
        OngoingCallLogger ongoingCallLogger = this.mOngoingCallController.logger;
        if (z3 && z3 != ongoingCallLogger.chipIsVisible) {
            ongoingCallLogger.logger.log(OngoingCallLogger.OngoingCallEvents.ONGOING_CALL_VISIBLE);
        }
        ongoingCallLogger.chipIsVisible = z3;
    }

    public final void updateStatusBarLocation(int i, int i2) {
        List<WeakReference> list;
        this.mStatusBar.getLeft();
        this.mStatusBar.getRight();
        StatusBarLocationPublisher statusBarLocationPublisher = this.mLocationPublisher;
        statusBarLocationPublisher.getClass();
        synchronized (statusBarLocationPublisher) {
            list = CollectionsKt___CollectionsKt.toList(statusBarLocationPublisher.listeners);
            Unit unit = Unit.INSTANCE;
        }
        for (WeakReference weakReference : list) {
            if (weakReference.get() == null) {
                statusBarLocationPublisher.listeners.remove(weakReference);
            }
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(weakReference.get());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0059, code lost:
    
        if ((r9.mStatusBarWindowStateController.windowState == 0) == false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateStatusBarVisibilities(boolean z) {
        StatusBarVisibilityModel statusBarVisibilityModel;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        CarrierHomeLogoViewController carrierHomeLogoViewController;
        boolean shouldHideStatusBarIconsForBouncer;
        boolean z6;
        StatusBarVisibilityModel statusBarVisibilityModel2 = this.mLastModifiedVisibility;
        StatusBarVisibilityModel statusBarVisibilityModel3 = this.mLastSystemVisibility;
        boolean shouldBeVisible = this.mStatusBarFragmentComponent.getHeadsUpAppearanceController().shouldBeVisible();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z7 = false;
        if (!keyguardStateControllerImpl.mLaunchTransitionFadingAway && !keyguardStateControllerImpl.mKeyguardFadingAway) {
            if (!(this.mShadeExpansionStateManager.state == 0)) {
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeViewController;
                if (notificationPanelViewController.mIsLaunchAnimationRunning) {
                    z6 = notificationPanelViewController.mHideIconsDuringLaunchAnimation;
                } else {
                    HeadsUpAppearanceController headsUpAppearanceController = notificationPanelViewController.mHeadsUpAppearanceController;
                    z6 = (headsUpAppearanceController == null || !headsUpAppearanceController.shouldBeVisible()) ? !notificationPanelViewController.mShowIconsWhenExpanded : false;
                }
                if (z6) {
                    this.mCollapsedStatusBarFragmentLogger.logInternalStatusBarHideState(StatusBarHideType.PANEL_OPEN);
                    shouldHideStatusBarIconsForBouncer = true;
                    if (shouldHideStatusBarIconsForBouncer && (this.mStatusBarStateController.getState() != 1 || !shouldBeVisible)) {
                        statusBarVisibilityModel = new StatusBarVisibilityModel(false, false, false, false);
                        CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger = this.mCollapsedStatusBarFragmentLogger;
                        collapsedStatusBarFragmentLogger.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        CollapsedStatusBarFragmentLogger$logVisibilityModel$2 collapsedStatusBarFragmentLogger$logVisibilityModel$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$logVisibilityModel$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                boolean bool1 = logMessage.getBool1();
                                boolean bool2 = logMessage.getBool2();
                                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("New visibilities calculated internally. showClock=", bool1, " showNotificationIcons=", bool2, " showOngoingCallChip="), logMessage.getBool3(), " showSystemInfo=", logMessage.getBool4());
                            }
                        };
                        LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("CollapsedSbFragment", logLevel, collapsedStatusBarFragmentLogger$logVisibilityModel$2, null);
                        z2 = statusBarVisibilityModel.showClock;
                        obtain.setBool1(z2);
                        z3 = statusBarVisibilityModel.showNotificationIcons;
                        obtain.setBool2(z3);
                        z4 = statusBarVisibilityModel.showOngoingCallChip;
                        obtain.setBool3(z4);
                        z5 = statusBarVisibilityModel.showSystemInfo;
                        obtain.setBool4(z5);
                        logBuffer.commit(obtain);
                        this.mLastModifiedVisibility = statusBarVisibilityModel;
                        if (z5 != statusBarVisibilityModel2.showSystemInfo) {
                            if (z5) {
                                ((Number) ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).animationState.getValue()).intValue();
                                animateShow(this.mEndSideContent, z);
                            } else {
                                animateHiddenState(this.mEndSideContent, 4, z);
                            }
                        }
                        if ((z4 && this.mOngoingCallChip.getVisibility() != 0) || (!z4 && this.mOngoingCallChip.getVisibility() == 0)) {
                            this.mClockView.setAlpha(0.0f);
                            carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
                            if (carrierHomeLogoViewController != null) {
                                carrierHomeLogoViewController.logoView.setAlpha(0.0f);
                            }
                            this.mOngoingCallChip.setAlpha(0.0f);
                            this.mNotificationIconAreaInner.setAlpha(0.0f);
                        }
                        if (z3 == statusBarVisibilityModel2.showNotificationIcons || z4 != statusBarVisibilityModel2.showOngoingCallChip) {
                            updateNotificationIconAreaAndCallChip(z);
                        }
                        if (z2 == statusBarVisibilityModel2.showClock || this.mClockView.getVisibility() != clockHiddenMode()) {
                            if (z2) {
                                animateHiddenState(this.mClockView, clockHiddenMode(), z);
                                hideCarrierLogo(z, z4);
                            } else {
                                animateShow(this.mClockView, z);
                                if (z4) {
                                    hideCarrierLogo(z, true);
                                } else {
                                    CarrierHomeLogoViewController carrierHomeLogoViewController2 = this.mCarrierHomeLogoViewController;
                                    if (carrierHomeLogoViewController2 != null) {
                                        animateShow(carrierHomeLogoViewController2.logoView, z);
                                    }
                                }
                            }
                        }
                        this.mStatusBarHideChecker.getClass();
                        if (statusBarVisibilityModel2.equals(statusBarVisibilityModel)) {
                            Log.d("CollapsedStatusBarFragment", "prv:" + statusBarVisibilityModel2.toString());
                            Log.d("CollapsedStatusBarFragment", "new:" + statusBarVisibilityModel.toString());
                            return;
                        }
                        return;
                    }
                }
            }
            if (!this.mWaitingForWindowStateChangeAfterCameraLaunch) {
            }
            if (this.mKeyguardUpdateMonitor.mSecureCameraLaunched) {
                this.mKeyguardStateController.getClass();
            }
            if (this.mTransitionFromLockscreenToDreamStarted && this.mKeyguardUpdateMonitor.mIsDreaming) {
                this.mKeyguardStateController.getClass();
            }
            ((Boolean) ((CollapsedStatusBarViewModelImpl) this.mCollapsedStatusBarViewModel).isTransitioningFromLockscreenToOccluded.getValue()).booleanValue();
            if (this.mStatusBarHideIconsForBouncerManager.getShouldHideStatusBarIconsForBouncer()) {
                this.mCollapsedStatusBarFragmentLogger.logInternalStatusBarHideState(StatusBarHideType.BOUNCER);
            }
            shouldHideStatusBarIconsForBouncer = this.mStatusBarHideIconsForBouncerManager.getShouldHideStatusBarIconsForBouncer();
            if (shouldHideStatusBarIconsForBouncer) {
                statusBarVisibilityModel = new StatusBarVisibilityModel(false, false, false, false);
                CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger2 = this.mCollapsedStatusBarFragmentLogger;
                collapsedStatusBarFragmentLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                CollapsedStatusBarFragmentLogger$logVisibilityModel$2 collapsedStatusBarFragmentLogger$logVisibilityModel$22 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$logVisibilityModel$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        boolean bool1 = logMessage.getBool1();
                        boolean bool2 = logMessage.getBool2();
                        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("New visibilities calculated internally. showClock=", bool1, " showNotificationIcons=", bool2, " showOngoingCallChip="), logMessage.getBool3(), " showSystemInfo=", logMessage.getBool4());
                    }
                };
                LogBuffer logBuffer2 = collapsedStatusBarFragmentLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("CollapsedSbFragment", logLevel2, collapsedStatusBarFragmentLogger$logVisibilityModel$22, null);
                z2 = statusBarVisibilityModel.showClock;
                obtain2.setBool1(z2);
                z3 = statusBarVisibilityModel.showNotificationIcons;
                obtain2.setBool2(z3);
                z4 = statusBarVisibilityModel.showOngoingCallChip;
                obtain2.setBool3(z4);
                z5 = statusBarVisibilityModel.showSystemInfo;
                obtain2.setBool4(z5);
                logBuffer2.commit(obtain2);
                this.mLastModifiedVisibility = statusBarVisibilityModel;
                if (z5 != statusBarVisibilityModel2.showSystemInfo) {
                }
                if (z4) {
                    this.mClockView.setAlpha(0.0f);
                    carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
                    if (carrierHomeLogoViewController != null) {
                    }
                    this.mOngoingCallChip.setAlpha(0.0f);
                    this.mNotificationIconAreaInner.setAlpha(0.0f);
                    if (z3 == statusBarVisibilityModel2.showNotificationIcons) {
                    }
                    updateNotificationIconAreaAndCallChip(z);
                    if (z2 == statusBarVisibilityModel2.showClock) {
                    }
                    if (z2) {
                    }
                    this.mStatusBarHideChecker.getClass();
                    if (statusBarVisibilityModel2.equals(statusBarVisibilityModel)) {
                    }
                }
                this.mClockView.setAlpha(0.0f);
                carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
                if (carrierHomeLogoViewController != null) {
                }
                this.mOngoingCallChip.setAlpha(0.0f);
                this.mNotificationIconAreaInner.setAlpha(0.0f);
                if (z3 == statusBarVisibilityModel2.showNotificationIcons) {
                }
                updateNotificationIconAreaAndCallChip(z);
                if (z2 == statusBarVisibilityModel2.showClock) {
                }
                if (z2) {
                }
                this.mStatusBarHideChecker.getClass();
                if (statusBarVisibilityModel2.equals(statusBarVisibilityModel)) {
                }
            }
        }
        boolean z8 = statusBarVisibilityModel3.showClock;
        if (this.mOngoingCallController.hasOngoingCall() && !shouldBeVisible) {
            z7 = true;
        }
        statusBarVisibilityModel = new StatusBarVisibilityModel(z8, statusBarVisibilityModel3.showNotificationIcons, z7, statusBarVisibilityModel3.showSystemInfo);
        CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger22 = this.mCollapsedStatusBarFragmentLogger;
        collapsedStatusBarFragmentLogger22.getClass();
        LogLevel logLevel22 = LogLevel.INFO;
        CollapsedStatusBarFragmentLogger$logVisibilityModel$2 collapsedStatusBarFragmentLogger$logVisibilityModel$222 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$logVisibilityModel$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("New visibilities calculated internally. showClock=", bool1, " showNotificationIcons=", bool2, " showOngoingCallChip="), logMessage.getBool3(), " showSystemInfo=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer22 = collapsedStatusBarFragmentLogger22.buffer;
        LogMessage obtain22 = logBuffer22.obtain("CollapsedSbFragment", logLevel22, collapsedStatusBarFragmentLogger$logVisibilityModel$222, null);
        z2 = statusBarVisibilityModel.showClock;
        obtain22.setBool1(z2);
        z3 = statusBarVisibilityModel.showNotificationIcons;
        obtain22.setBool2(z3);
        z4 = statusBarVisibilityModel.showOngoingCallChip;
        obtain22.setBool3(z4);
        z5 = statusBarVisibilityModel.showSystemInfo;
        obtain22.setBool4(z5);
        logBuffer22.commit(obtain22);
        this.mLastModifiedVisibility = statusBarVisibilityModel;
        if (z5 != statusBarVisibilityModel2.showSystemInfo) {
        }
        if (z4) {
        }
        this.mClockView.setAlpha(0.0f);
        carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
        if (carrierHomeLogoViewController != null) {
        }
        this.mOngoingCallChip.setAlpha(0.0f);
        this.mNotificationIconAreaInner.setAlpha(0.0f);
        if (z3 == statusBarVisibilityModel2.showNotificationIcons) {
        }
        updateNotificationIconAreaAndCallChip(z);
        if (z2 == statusBarVisibilityModel2.showClock) {
        }
        if (z2) {
        }
        this.mStatusBarHideChecker.getClass();
        if (statusBarVisibilityModel2.equals(statusBarVisibilityModel)) {
        }
    }
}
