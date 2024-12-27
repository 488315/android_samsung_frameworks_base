package com.android.systemui.statusbar.phone.fragment;

import android.app.Fragment;
import android.database.ContentObserver;
import android.os.Bundle;
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
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.OperatorNameViewController$Factory;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerStatusBarViewBinder;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider$layoutListener$1;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent$Startable$State;
import com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController;
import com.android.systemui.statusbar.phone.logo.CarrierLogoView;
import com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.phone.ui.DarkIconManager;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinder;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.CollapsedStatusBarViewBinderImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.StatusBarVisibilityChangeListener;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModel;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

public final class CollapsedStatusBarFragment extends Fragment implements CommandQueue.Callbacks, StatusBarStateController.StateListener, SystemStatusAnimationCallback, Dumpable {
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
    public final CollapsedStatusBarViewBinder mCollapsedStatusBarViewBinder;
    public final CollapsedStatusBarViewModel mCollapsedStatusBarViewModel;
    public final CommandQueue mCommandQueue;
    public DarkIconManager mDarkIconManager;
    public final DarkIconManager.Factory mDarkIconManagerFactory;
    public final AnonymousClass3 mDefaultDataListener;
    public final AnonymousClass4 mDemoModeCallback;
    public final DumpManager mDumpManager;
    public MultiSourceMinAlphaController mEndSideAlphaController;
    public final Handler mHandler;
    public final boolean mHasOngoingActivity;
    public final KeyguardStateController mKeyguardStateController;
    public StatusBarVisibilityModel mLastModifiedVisibility;
    public StatusBarVisibilityModel mLastSystemVisibility;
    public final StatusBarLocationPublisher mLocationPublisher;
    public final Executor mMainExecutor;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public View mNotificationIconAreaInner;
    public View mOngoingActivityChip;
    public final OngoingActivityController mOngoingActivityController;
    public View mOngoingCallChip;
    public final OngoingCallController mOngoingCallController;
    public final AnonymousClass1 mOngoingCallListener;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final PrivacyLogger mPrivacyLogger;
    public final SecureSettings mSecureSettings;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final Map mStartableStates;
    public PhoneStatusBarView mStatusBar;
    public StatusBarFragmentComponent mStatusBarFragmentComponent;
    public final StatusBarFragmentComponent.Factory mStatusBarFragmentComponentFactory;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarIconController mStatusBarIconController;
    public final CollapsedStatusBarFragment$$ExternalSyntheticLambda1 mStatusBarLayoutListener;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass5 mStatusBarVisibilityChangeListener;
    public final StatusBarVisibilityExt mStatusBarVisibilityExt;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final CollapsedStatusBarFragment$$ExternalSyntheticLambda0 mStatusBarWindowStateListener;
    public StatusBarSystemEventDefaultAnimator mSystemEventAnimator;
    public final AnonymousClass7 mVolumeSettingObserver;

    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$5, reason: invalid class name */
    public final class AnonymousClass5 implements StatusBarVisibilityChangeListener {
        public AnonymousClass5() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$6, reason: invalid class name */
    public final class AnonymousClass6 extends AnimatorListenerAdapter {
        public final /* synthetic */ boolean val$statusBarHidden;

        public AnonymousClass6(boolean z) {
            this.val$statusBarHidden = z;
        }

        @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.val$statusBarHidden) {
                CollapsedStatusBarFragment.this.mHandler.postDelayed(new CollapsedStatusBarFragment$$ExternalSyntheticLambda2(this, 1), 390L);
            }
        }
    }

    public final class OngoingActivityListenerImpl {
        public /* synthetic */ OngoingActivityListenerImpl(CollapsedStatusBarFragment collapsedStatusBarFragment, int i) {
            this();
        }

        public final void onNudgeClockRequired() {
            View view;
            ChipAnimationController chipAnimationController = CollapsedStatusBarFragment.this.mChipAnimationController;
            chipAnimationController.getClass();
            Log.d("{ChipAnimationController}", "nudgeClockIfNeeded()");
            View view2 = chipAnimationController.onGoingActivityChip;
            if (view2 == null || view2.getVisibility() != 0) {
                return;
            }
            View view3 = chipAnimationController.onGoingCallChip;
            if ((view3 == null || view3.getVisibility() != 0) && (view = chipAnimationController.clockView) != null) {
                chipAnimationController.nudgeAnimation(view, true);
            }
        }

        private OngoingActivityListenerImpl() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$7] */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$2] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$3] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0] */
    public CollapsedStatusBarFragment(StatusBarFragmentComponent.Factory factory, OngoingActivityController ongoingActivityController, OngoingCallController ongoingCallController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, StatusBarLocationPublisher statusBarLocationPublisher, NotificationIconAreaController notificationIconAreaController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarIconController statusBarIconController, DarkIconManager.Factory factory2, CollapsedStatusBarViewModel collapsedStatusBarViewModel, CollapsedStatusBarViewBinder collapsedStatusBarViewBinder, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, KeyguardStateController keyguardStateController, PanelExpansionInteractor panelExpansionInteractor, StatusBarStateController statusBarStateController, NotificationIconContainerStatusBarViewBinder notificationIconContainerStatusBarViewBinder, CommandQueue commandQueue, CarrierConfigTracker carrierConfigTracker, CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger, OperatorNameViewController$Factory operatorNameViewController$Factory, SecureSettings secureSettings, Executor executor, DumpManager dumpManager, StatusBarWindowStateController statusBarWindowStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DemoModeController demoModeController, Handler handler, PrivacyLogger privacyLogger, CarrierInfraMediator carrierInfraMediator, CarrierHomeLogoViewController.Factory factory3, StatusBarVisibilityExt statusBarVisibilityExt) {
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
        this.mStatusBarWindowStateListener = new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                CollapsedStatusBarFragment collapsedStatusBarFragment = CollapsedStatusBarFragment.this;
                collapsedStatusBarFragment.getClass();
                collapsedStatusBarFragment.updateStatusBarVisibilities(false);
            }
        };
        this.mAnimationsEnabled = true;
        new DemoMode() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.4
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
        this.mStatusBarFragmentComponentFactory = factory;
        this.mOngoingActivityController = ongoingActivityController;
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
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mStatusBarStateController = statusBarStateController;
        this.mCommandQueue = commandQueue;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mCollapsedStatusBarFragmentLogger = collapsedStatusBarFragmentLogger;
        this.mSecureSettings = secureSettings;
        this.mMainExecutor = executor;
        this.mDumpManager = dumpManager;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mHandler = handler;
        this.mPrivacyLogger = privacyLogger;
        this.mCarrierInfraMediator = carrierInfraMediator;
        this.mCarrierLogoViewControllerFactory = factory3;
        this.mStatusBarVisibilityExt = statusBarVisibilityExt;
        statusBarVisibilityExt.mUpdateRunnable = new CollapsedStatusBarFragment$$ExternalSyntheticLambda2(this, 0);
    }

    public final void animateHiddenState(final View view, final int i, boolean z) {
        if (view == null) {
            return;
        }
        boolean z2 = z && this.mAnimationsEnabled;
        ChipAnimationController chipAnimationController = this.mChipAnimationController;
        if (chipAnimationController != null && (view.equals(chipAnimationController.onGoingCallChip) || view.equals(chipAnimationController.onGoingActivityChip))) {
            this.mChipAnimationController.animateChipHide(view, i, z2);
            return;
        }
        view.animate().cancel();
        if (z && this.mAnimationsEnabled) {
            view.animate().alpha(0.0f).setDuration(160L).setStartDelay(0L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda6
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
        if (view == null) {
            return;
        }
        boolean z2 = z && this.mAnimationsEnabled;
        ChipAnimationController chipAnimationController = this.mChipAnimationController;
        if (chipAnimationController != null && (view.equals(chipAnimationController.onGoingCallChip) || view.equals(chipAnimationController.onGoingActivityChip))) {
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

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:
    
        if (r3.wereIconsJustHidden == false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel calculateInternalModel(com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel r13) {
        /*
            r12 = this;
            com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent r2 = r12.mStatusBarFragmentComponent
            com.android.systemui.statusbar.phone.HeadsUpAppearanceController r2 = r2.getHeadsUpAppearanceController()
            boolean r2 = r2.shouldBeVisible$1()
            com.android.systemui.statusbar.policy.KeyguardStateController r3 = r12.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r3 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r3
            boolean r4 = r3.mLaunchTransitionFadingAway
            r5 = 1
            if (r4 != 0) goto L70
            boolean r3 = r3.mKeyguardFadingAway
            if (r3 != 0) goto L70
            com.android.systemui.shade.ShadeExpansionStateManager r3 = r12.mShadeExpansionStateManager
            boolean r3 = r3.isClosed()
            if (r3 != 0) goto L28
            com.android.systemui.shade.domain.interactor.PanelExpansionInteractor r3 = r12.mPanelExpansionInteractor
            boolean r3 = r3.shouldHideStatusBarIconsWhenExpanded()
            if (r3 == 0) goto L28
            goto L5a
        L28:
            com.android.systemui.statusbar.policy.KeyguardStateController r3 = r12.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r3 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r3
            boolean r4 = r3.mShowing
            if (r4 == 0) goto L3d
            boolean r3 = r3.mOccluded
            if (r3 != 0) goto L3d
            java.lang.String r3 = "CollapsedStatusBarFragment"
            java.lang.String r4 = "shouldHideStatusBar() will return true for Keyguard"
            android.util.Log.d(r3, r4)
            goto L5a
        L3d:
            com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager r3 = r12.mStatusBarHideIconsForBouncerManager
            boolean r4 = r3.hideIconsForBouncer
            if (r4 != 0) goto L47
            boolean r6 = r3.wereIconsJustHidden
            if (r6 == 0) goto L52
        L47:
            boolean r6 = r3.wereIconsJustHidden
            java.lang.String r7 = "hideIconsForBouncer="
            java.lang.String r8 = " wereIconsJustHidden="
            java.lang.String r9 = "StatusBarHideIconsForBouncerManager"
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r7, r8, r9, r4, r6)
        L52:
            boolean r4 = r3.hideIconsForBouncer
            if (r4 != 0) goto L5a
            boolean r3 = r3.wereIconsJustHidden
            if (r3 == 0) goto L70
        L5a:
            com.android.systemui.plugins.statusbar.StatusBarStateController r3 = r12.mStatusBarStateController
            int r3 = r3.getState()
            if (r3 != r5) goto L64
            if (r2 != 0) goto L70
        L64:
            com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel r0 = new com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel
            r8 = 0
            r9 = 0
            r7 = 0
            r10 = 0
            r11 = 0
            r6 = r0
            r6.<init>(r7, r8, r9, r10, r11)
            return r0
        L70:
            boolean r3 = r13.showClock
            com.android.systemui.Flags.statusBarScreenSharingChips()
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r4 = r12.mOngoingCallController
            r4.hasOngoingCall()
            com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController r4 = r12.mOngoingActivityController
            boolean r4 = r4.shouldVisible()
            com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel r7 = new com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r0 = r12.mOngoingCallController
            boolean r0 = r0.hasOngoingCall()
            r6 = 0
            if (r0 == 0) goto L8f
            if (r2 != 0) goto L8f
            r0 = r5
            goto L90
        L8f:
            r0 = r6
        L90:
            if (r4 == 0) goto L95
            if (r2 != 0) goto L95
            goto L96
        L95:
            r5 = r6
        L96:
            boolean r4 = r13.showNotificationIcons
            boolean r6 = r13.showSystemInfo
            r1 = r7
            r2 = r3
            r3 = r4
            r4 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment.calculateInternalModel(com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel):com.android.systemui.statusbar.phone.fragment.StatusBarVisibilityModel");
    }

    public final int clockHiddenMode() {
        return (this.mShadeExpansionStateManager.isClosed() || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing || this.mStatusBarStateController.isDozing()) ? 8 : 4;
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
                return CollapsedStatusBarFragmentLogger.this.disableFlagsLogger.getDisableFlagsString(new DisableFlagsLogger.DisableState(logMessage.getInt1(), logMessage.getInt2()), null);
            }
        };
        LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
        LogMessage obtain = logBuffer.obtain("CollapsedSbFragment", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = disableState.disable1;
        logMessageImpl.int2 = disableState.disable2;
        logBuffer.commit(obtain);
        StatusBarVisibilityModel.Companion.getClass();
        this.mLastSystemVisibility = StatusBarVisibilityModel.Companion.createModelFromFlags(i2, i3);
        updateStatusBarVisibilities(z);
    }

    public void disableAnimationsForTesting() {
        this.mAnimationsEnabled = false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("mHasOngoingActivity=" + this.mHasOngoingActivity);
        indentingPrintWriter.println("mAnimationsEnabled=" + this.mAnimationsEnabled);
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

    public void enableAnimationsForTesting() {
        this.mAnimationsEnabled = true;
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
        int slotIndex = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId());
        if (this.mCarrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN, slotIndex, new Object[0])) {
            ViewStub viewStub = (ViewStub) this.mStatusBar.findViewById(R.id.carrier_logo_container);
            if (viewStub != null) {
                CarrierHomeLogoViewController create = this.mCarrierLogoViewControllerFactory.create(viewStub.inflate(), slotIndex);
                this.mCarrierHomeLogoViewController = create;
                create.init();
            }
            if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                hideCarrierLogo(false, false);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        StatusBarVisibilityExt statusBarVisibilityExt = this.mStatusBarVisibilityExt;
        statusBarVisibilityExt.getClass();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            return;
        }
        statusBarVisibilityExt.postUpdateStatusBarVisibility();
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mStatusBarWindowStateController.addListener(this.mStatusBarWindowStateListener);
        int i = NotificationIconContainerRefactor.$r8$clinit;
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
        int i = NotificationIconContainerRefactor.$r8$clinit;
    }

    @Override // android.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).removeIconGroup(this.mDarkIconManager);
        this.mCarrierConfigTracker.removeCallback((CarrierConfigTracker.CarrierConfigChangedListener) this.mCarrierConfigCallback);
        this.mCarrierConfigTracker.removeDataSubscriptionChangedListener(this.mDefaultDataListener);
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mStatusBarFragmentComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STOPPING);
            View view = statusBarBoundsProvider.startSideContent;
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            view.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            statusBarBoundsProvider.endSideContent.removeOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STOPPED);
        }
        this.mDumpManager.unregisterDumpable("CollapsedStatusBarFragment");
        int i = NotificationIconContainerRefactor.$r8$clinit;
        StatusBarVisibilityExt statusBarVisibilityExt = this.mStatusBarVisibilityExt;
        ((KeyguardStateControllerImpl) statusBarVisibilityExt.mKeyguardStateController).removeCallback(statusBarVisibilityExt);
        this.mOngoingActivityController.ongoingActivityListener = null;
        this.mChipAnimationController = null;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        updateStatusBarVisibilities(false);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.removeCallback(this);
        this.mOngoingCallController.removeCallback((OngoingCallListener) this.mOngoingCallListener);
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).removeCallback(this);
        this.mSecureSettings.unregisterContentObserverSync(this.mVolumeSettingObserver);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mStatusBarStateController.addCallback(this);
        this.mOngoingCallController.addCallback((OngoingCallListener) this.mOngoingCallListener);
        this.mOngoingCallController.setChipView(this.mOngoingCallChip);
        this.mOngoingCallController.parent = this.mStatusBar;
        ((SystemStatusAnimationSchedulerImpl) this.mAnimationScheduler).addCallback(this);
        this.mOngoingActivityController.initCapsuleLayout(this.mOngoingActivityChip, this.mStatusBar);
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
        this.mStatusBarVisibilityExt.postUpdateStatusBarVisibility();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationBegin(boolean z, boolean z2) {
        if (!z) {
            return this.mSystemEventAnimator.onSystemEventAnimationBegin(z, z2);
        }
        this.mPrivacyLogger.logStatusBarAlpha(0);
        StatusBarSystemEventDefaultAnimator statusBarSystemEventDefaultAnimator = this.mSystemEventAnimator;
        statusBarSystemEventDefaultAnimator.onAlphaChanged.invoke(Float.valueOf(0.0f));
        return new AnimatorSet();
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public final Animator onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        Animator onSystemEventAnimationFinish = this.mSystemEventAnimator.onSystemEventAnimationFinish(z, z2, z3);
        onSystemEventAnimationFinish.addListener(new AnonymousClass6(z2));
        return onSystemEventAnimationFinish;
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        final int i = 0;
        final int i2 = 1;
        super.onViewCreated(view, bundle);
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "CollapsedStatusBarFragment", this);
        this.mStatusBarFragmentComponent = this.mStatusBarFragmentComponentFactory.create((PhoneStatusBarView) getView());
        PhoneStatusBarView phoneStatusBarView = (PhoneStatusBarView) view;
        this.mStatusBar = phoneStatusBarView;
        initOperatorName();
        this.mStatusBarFragmentComponent.init();
        ((ArrayMap) this.mStartableStates).clear();
        for (StatusBarBoundsProvider statusBarBoundsProvider : this.mStatusBarFragmentComponent.getStartables()) {
            ((ArrayMap) this.mStartableStates).put(statusBarBoundsProvider, StatusBarFragmentComponent$Startable$State.STARTING);
            View view2 = statusBarBoundsProvider.startSideContent;
            StatusBarBoundsProvider$layoutListener$1 statusBarBoundsProvider$layoutListener$1 = statusBarBoundsProvider.layoutListener;
            view2.addOnLayoutChangeListener(statusBarBoundsProvider$layoutListener$1);
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
        DarkIconManager.Factory factory = this.mDarkIconManagerFactory;
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.statusIcons);
        StatusBarLocation statusBarLocation = StatusBarLocation.HOME;
        DarkIconDispatcher darkIconDispatcher = factory.mDarkIconDispatcher;
        DarkIconManager darkIconManager = new DarkIconManager(linearLayout, statusBarLocation, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileContextProvider, darkIconDispatcher, factory.mBTTetherUiAdapter);
        this.mDarkIconManager = darkIconManager;
        darkIconManager.mShouldLog = true;
        ((StatusBarIconControllerImpl) this.mStatusBarIconController).addIconGroup(darkIconManager);
        this.mEndSideAlphaController = new MultiSourceMinAlphaController((LinearLayout) this.mStatusBar.findViewById(R.id.status_bar_end_side_content));
        this.mClockView = this.mStatusBar.findViewById(R.id.clock);
        this.mOngoingCallChip = this.mStatusBar.findViewById(R.id.ongoing_call_chip);
        this.mOngoingActivityChip = this.mStatusBar.findViewById(R.id.ongoing_activity_capsule);
        showEndSideContent(false);
        animateShow(this.mClockView, false);
        Trace.beginSection("CollapsedStatusBarFragment#initNotifIconArea");
        ViewGroup viewGroup = (ViewGroup) this.mStatusBar.requireViewById(R.id.notification_icon_area);
        int i3 = NotificationIconContainerRefactor.$r8$clinit;
        View notificationInnerAreaView = this.mNotificationIconAreaController.getNotificationInnerAreaView();
        this.mNotificationIconAreaInner = notificationInnerAreaView;
        if (notificationInnerAreaView.getParent() != null) {
            ((ViewGroup) this.mNotificationIconAreaInner.getParent()).removeView(this.mNotificationIconAreaInner);
        }
        viewGroup.addView(this.mNotificationIconAreaInner);
        updateNotificationIconAreaAndOngoingActivityChip(false);
        Trace.endSection();
        this.mSystemEventAnimator = new StatusBarSystemEventDefaultAnimator(getResources(), new Function1(this) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda4
            public final /* synthetic */ CollapsedStatusBarFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
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
        }, new Function1(this) { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda4
            public final /* synthetic */ CollapsedStatusBarFragment f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
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
        ((CollapsedStatusBarViewBinderImpl) this.mCollapsedStatusBarViewBinder).bind(this.mStatusBar, this.mCollapsedStatusBarViewModel, this.mStatusBarVisibilityChangeListener);
        StatusBarVisibilityExt statusBarVisibilityExt = this.mStatusBarVisibilityExt;
        ((KeyguardStateControllerImpl) statusBarVisibilityExt.mKeyguardStateController).addCallback(statusBarVisibilityExt);
        this.mChipAnimationController = new ChipAnimationController(this.mStatusBar, this.mNotificationIconAreaController, this.mOngoingActivityController);
        this.mOngoingActivityController.ongoingActivityListener = new OngoingActivityListenerImpl(this, i);
    }

    public final void showEndSideContent(boolean z) {
        if (z) {
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
        StatusBarVisibilityModel statusBarVisibilityModel = this.mLastModifiedVisibility;
        boolean z2 = !statusBarVisibilityModel.showNotificationIcons;
        boolean z3 = this.mOngoingCallController.hasOngoingCall() && statusBarVisibilityModel.showOngoingCallChip;
        boolean z4 = statusBarVisibilityModel.showOngoingActivityChip;
        if (z2) {
            boolean z5 = (!z || z4 || z3) ? false : true;
            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() != 2) {
                animateHiddenState(this.mNotificationIconAreaInner, 4, z5);
            }
        } else if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() != 2) {
            animateShow(this.mNotificationIconAreaInner, z);
        }
        boolean z6 = z3 && !z2;
        boolean z7 = z4 && !z2;
        if (z6) {
            animateShow(this.mOngoingCallChip, z);
            this.mOngoingCallController.hideTimeViewByOngoingChip(z7);
        } else {
            animateHiddenState(this.mOngoingCallChip, 8, true);
        }
        if (z7) {
            animateShow(this.mOngoingActivityChip, z);
            if (this.mOngoingCallController.hasOngoingCall() && this.mLastModifiedVisibility.showOngoingCallChip) {
                this.mOngoingCallController.hideTimeViewByOngoingChip(true);
            }
        } else {
            animateHiddenState(this.mOngoingActivityChip, 8, true);
            if (this.mOngoingCallController.hasOngoingCall() && this.mLastModifiedVisibility.showOngoingCallChip) {
                this.mOngoingCallController.hideTimeViewByOngoingChip(false);
            }
        }
        Flags.statusBarScreenSharingChips();
        OngoingCallLogger ongoingCallLogger = this.mOngoingCallController.logger;
        if (z6 && z6 != ongoingCallLogger.chipIsVisible) {
            ongoingCallLogger.logger.log(OngoingCallLogger.OngoingCallEvents.ONGOING_CALL_VISIBLE);
        }
        ongoingCallLogger.chipIsVisible = z6;
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
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(weakReference.get());
        }
    }

    public final void updateStatusBarVisibilities(final boolean z) {
        StatusBarVisibilityExt statusBarVisibilityExt = this.mStatusBarVisibilityExt;
        StatusBarVisibilityModel calculateInternalModel = calculateInternalModel(this.mLastSystemVisibility);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CollapsedStatusBarFragment.this.updateStatusBarVisibilitiesInner(z);
            }
        };
        if (((KeyguardStateControllerImpl) statusBarVisibilityExt.mKeyguardStateController).mOccluded && (calculateInternalModel.showClock || calculateInternalModel.showNotificationIcons || calculateInternalModel.showOngoingCallChip || calculateInternalModel.showOngoingActivityChip || calculateInternalModel.showSystemInfo)) {
            statusBarVisibilityExt.postUpdateStatusBarVisibility();
        } else {
            runnable.run();
        }
    }

    public final void updateStatusBarVisibilitiesInner(boolean z) {
        View view;
        View view2;
        CarrierLogoView carrierLogoView;
        StatusBarVisibilityModel statusBarVisibilityModel = this.mLastModifiedVisibility;
        StatusBarVisibilityModel calculateInternalModel = calculateInternalModel(this.mLastSystemVisibility);
        CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger = this.mCollapsedStatusBarFragmentLogger;
        collapsedStatusBarFragmentLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        CollapsedStatusBarFragmentLogger$logVisibilityModel$2 collapsedStatusBarFragmentLogger$logVisibilityModel$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger$logVisibilityModel$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("New visibilities calculated internally. showClock=", " showNotificationIcons=", " showOngoingActivityChip=", bool1, bool2), logMessage.getBool3(), " showSystemInfo=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = collapsedStatusBarFragmentLogger.buffer;
        LogMessage obtain = logBuffer.obtain("CollapsedSbFragment", logLevel, collapsedStatusBarFragmentLogger$logVisibilityModel$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        boolean z2 = calculateInternalModel.showClock;
        logMessageImpl.bool1 = z2;
        boolean z3 = calculateInternalModel.showNotificationIcons;
        logMessageImpl.bool2 = z3;
        boolean z4 = calculateInternalModel.showOngoingActivityChip;
        logMessageImpl.bool3 = z4;
        boolean z5 = calculateInternalModel.showSystemInfo;
        logMessageImpl.bool4 = z5;
        logBuffer.commit(obtain);
        this.mLastModifiedVisibility = calculateInternalModel;
        if (z5 != statusBarVisibilityModel.showSystemInfo) {
            if (z5) {
                showEndSideContent(z);
            } else if (z) {
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
        boolean z6 = z3 != statusBarVisibilityModel.showNotificationIcons;
        boolean z7 = statusBarVisibilityModel.showOngoingCallChip;
        boolean z8 = calculateInternalModel.showOngoingCallChip;
        boolean z9 = z8 != z7;
        boolean z10 = z4 != statusBarVisibilityModel.showOngoingActivityChip;
        if (z9 && (view2 = this.mOngoingCallChip) != null && ((z8 && view2.getVisibility() != 0) || (!z8 && this.mOngoingCallChip.getVisibility() == 0))) {
            this.mClockView.setAlpha(1.0f);
            CarrierHomeLogoViewController carrierHomeLogoViewController = this.mCarrierHomeLogoViewController;
            if (carrierHomeLogoViewController != null && (carrierLogoView = carrierHomeLogoViewController.logoView) != null) {
                carrierLogoView.setAlpha(0.0f);
            }
            this.mNotificationIconAreaInner.setAlpha(0.0f);
        }
        if (z6 || z10 || z9) {
            updateNotificationIconAreaAndOngoingActivityChip(z);
        }
        if (z2 != statusBarVisibilityModel.showClock || ((view = this.mClockView) != null && view.getVisibility() != clockHiddenMode())) {
            if (z2) {
                animateShow(this.mClockView, z);
                if (z8) {
                    hideCarrierLogo(z, true);
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
        this.mStatusBarVisibilityExt.getClass();
        if (statusBarVisibilityModel.equals(calculateInternalModel)) {
            return;
        }
        Log.d("CollapsedStatusBarFragment_StatusBarVisibilityExt", "prv:" + statusBarVisibilityModel.toString());
        Log.d("CollapsedStatusBarFragment_StatusBarVisibilityExt", "new:" + calculateInternalModel.toString());
    }
}
