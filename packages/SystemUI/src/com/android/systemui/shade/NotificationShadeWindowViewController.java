package com.android.systemui.shade;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPresentationDisabler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconView;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.binder.BouncerViewBinder;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shared.animation.DisableSubpixelTextTransitionListener;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.Optional;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationShadeWindowViewController implements Dumpable {
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AmbientState mAmbientState;
    public final BouncerViewBinder mBouncerViewBinder;
    public View mBrightnessMirror;
    public final SystemClock mClock;
    public final NotificationShadeDepthController mDepthController;
    public MotionEvent mDownEvent;
    public final DozeScrimController mDozeScrimController;
    public final DozeServiceHost mDozeServiceHost;
    public DragDownHelper mDragDownHelper;
    public boolean mExpandAnimationRunning;
    public boolean mExpandingBelowNotch;
    public final FalsingCollector mFalsingCollector;
    public final FeatureFlagsClassic mFeatureFlagsClassic;
    public final GlanceableHubContainerController mGlanceableHubContainerController;
    public final KeyguardSysDumpTrigger mKeyguardSysDumpTrigger;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public long mLaunchAnimationTimeout;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationShadeWindowViewController$$ExternalSyntheticLambda5 mLockscreenToDreamingTransition;
    public final NotificationInsetsController mNotificationInsetsController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final SecPanelTouchBlockHelper mPanelTouchBlockHelper;
    public KeyguardPresentationDisabler mPresentationDisabler;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public boolean mSecKeyguardStatusViewTouchArea;
    public final CentralSurfaces mService;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeLogger mShadeLogger;
    public final ShadeViewController mShadeViewController;
    public NotificationStackScrollLayout mStackScrollLayout;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public PhoneStatusBarViewController mStatusBarViewController;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final SysUIKeyEventHandler mSysUIKeyEventHandler;
    public boolean mTouchActive;
    public boolean mTouchCancelled;
    public final NotificationShadeWindowView mView;
    public boolean mIsTrackingBarGesture = false;
    public boolean mIsOcclusionTransitionRunning = false;
    public boolean mPluginLockTouchArea = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.shade.NotificationShadeWindowViewController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* renamed from: -$$Nest$mlogDownDispatch, reason: not valid java name */
    public static void m2107$$Nest$mlogDownDispatch(NotificationShadeWindowViewController notificationShadeWindowViewController, MotionEvent motionEvent, String str, final Boolean bool) {
        notificationShadeWindowViewController.getClass();
        if (motionEvent.getAction() == 0) {
            ShadeLogger shadeLogger = notificationShadeWindowViewController.mShadeLogger;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$logShadeWindowDispatch$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    String str2;
                    LogMessage logMessage = (LogMessage) obj;
                    Boolean bool2 = bool;
                    if (Intrinsics.areEqual(bool2, Boolean.TRUE)) {
                        str2 = "SHADE TOUCH REROUTED";
                    } else if (Intrinsics.areEqual(bool2, Boolean.FALSE)) {
                        str2 = "SHADE TOUCH BLOCKED";
                    } else {
                        if (bool2 != null) {
                            throw new NoWhenBranchMatchedException();
                        }
                        str2 = "SHADE TOUCH DISPATCHED";
                    }
                    return str2 + ": eventTime=" + logMessage.getLong1() + ",downTime=" + logMessage.getLong2() + ", reason=" + logMessage.getStr1();
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, function1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.long1 = motionEvent.getEventTime();
            logMessageImpl.long2 = motionEvent.getDownTime();
            logBuffer.commit(obtain);
        }
    }

    public NotificationShadeWindowViewController(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginLockStarManager pluginLockStarManager, KeyguardSysDumpTrigger keyguardSysDumpTrigger, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, SysuiStatusBarStateController sysuiStatusBarStateController, DockManager dockManager, NotificationShadeDepthController notificationShadeDepthController, NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, ShadeExpansionStateManager shadeExpansionStateManager, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, StatusBarWindowStateController statusBarWindowStateController, final SecLockIconViewController secLockIconViewController, CentralSurfaces centralSurfaces, DozeServiceHost dozeServiceHost, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, Optional<UnfoldTransitionProgressProvider> optional, Optional<SysUIUnfoldComponent> optional2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, NotificationInsetsController notificationInsetsController, AmbientState ambientState, ShadeLogger shadeLogger, DumpManager dumpManager, PulsingGestureListener pulsingGestureListener, LockscreenHostedDreamGestureListener lockscreenHostedDreamGestureListener, KeyguardTransitionInteractor keyguardTransitionInteractor, GlanceableHubContainerController glanceableHubContainerController, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, FeatureFlagsClassic featureFlagsClassic, SystemClock systemClock, SysUIKeyEventHandler sysUIKeyEventHandler, QuickSettingsController quickSettingsController, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, BouncerViewBinder bouncerViewBinder) {
        int i = 0;
        NotificationShadeWindowViewController$$ExternalSyntheticLambda5 notificationShadeWindowViewController$$ExternalSyntheticLambda5 = new NotificationShadeWindowViewController$$ExternalSyntheticLambda5(this, 1);
        this.mSecKeyguardStatusViewTouchArea = false;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mFalsingCollector = falsingCollector;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mView = notificationShadeWindowView;
        this.mShadeViewController = shadeViewController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mDepthController = notificationShadeDepthController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mShadeLogger = shadeLogger;
        this.mService = centralSurfaces;
        this.mDozeServiceHost = dozeServiceHost;
        this.mDozeScrimController = dozeScrimController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.mAmbientState = ambientState;
        this.mNotificationInsetsController = notificationInsetsController;
        this.mGlanceableHubContainerController = glanceableHubContainerController;
        this.mFeatureFlagsClassic = featureFlagsClassic;
        this.mSysUIKeyEventHandler = sysUIKeyEventHandler;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mBrightnessMirror = notificationShadeWindowView.findViewById(R.id.brightness_mirror_container);
        new DisableSubpixelTextTransitionListener(notificationShadeWindowView);
        this.mKeyguardSysDumpTrigger = keyguardSysDumpTrigger;
        this.mBouncerViewBinder = bouncerViewBinder;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            FrameLayout keyguardBouncerContainer = z ? new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), sysUIKeyEventHandler, keyguardSysDumpTrigger) : (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
            if (z) {
                ((CentralSurfacesImpl) centralSurfaces).mBouncerContainer = keyguardBouncerContainer;
                Log.d("NotifShadeWindowVC", "addBouncer!");
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(keyguardBouncerContainer);
            }
            bouncerViewBinder.bind(keyguardBouncerContainer);
        }
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        KeyguardState keyguardState2 = KeyguardState.DREAMING;
        Edge.Companion.getClass();
        JavaAdapterKt.collectFlow(notificationShadeWindowView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState, keyguardState2)), notificationShadeWindowViewController$$ExternalSyntheticLambda5);
        JavaAdapterKt.collectFlow(notificationShadeWindowView, notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, new NotificationShadeWindowViewController$$ExternalSyntheticLambda5(this, 2));
        Optional<U> map = optional2.map(new NotificationPanelViewController$$ExternalSyntheticLambda23(i));
        Optional<U> map2 = optional2.map(new NotificationShadeWindowViewController$$ExternalSyntheticLambda2());
        map.ifPresent(new NotificationShadeWindowViewController$$ExternalSyntheticLambda3());
        map2.ifPresent(new NotificationShadeWindowViewController$$ExternalSyntheticLambda5(this, 3));
        this.mClock = systemClock;
        Flags flags = Flags.INSTANCE;
        featureFlagsClassic.getClass();
        SecLockIconView secLockIconView = (SecLockIconView) notificationShadeWindowView.findViewById(R.id.sec_lock_icon_view);
        secLockIconViewController.mView = secLockIconView;
        secLockIconViewController.mFeatureFlags.getClass();
        secLockIconViewController.updateKeyguardShowing();
        secLockIconViewController.mIsBouncerShowing = secLockIconViewController.mKeyguardViewController.isBouncerShowing();
        secLockIconViewController.mIsDozing = secLockIconViewController.mStatusBarStateController.isDozing();
        secLockIconViewController.mRunningFPS = secLockIconViewController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning();
        secLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
        Log.d("SecLockIconViewController", "setSecLockIconView : mIsBouncerShowing = " + secLockIconViewController.mIsBouncerShowing + " mIsDozing = " + secLockIconViewController.mIsDozing);
        Log.d("SecLockIconViewController", "setSecLockIconView : mRunningFPS = " + secLockIconViewController.mRunningFPS + " mCanDismissLockScreen = " + secLockIconViewController.mCanDismissLockScreen);
        secLockIconViewController.updateVisibility$1();
        secLockIconView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.SecLockIconViewController.2
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                SecLockIconViewController secLockIconViewController2 = SecLockIconViewController.this;
                int i2 = SecLockIconViewController.$r8$clinit;
                secLockIconViewController2.registerCallbacks$2();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                SecLockIconViewController.m850$$Nest$munregisterCallbacks(SecLockIconViewController.this);
            }
        });
        if (secLockIconView.isAttachedToWindow()) {
            secLockIconViewController.registerCallbacks$2();
        }
        secLockIconView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SecLockIconViewController.this.getClass();
                return false;
            }
        });
        pluginLockStarManager.getClass();
        Log.d("LStar|PluginLockStarManager", "onRootViewAttached :: " + notificationShadeWindowView.toString());
        pluginLockStarManager.mRootView = notificationShadeWindowView;
        pluginLockStarManager.mLockStarViewStub = (ViewStub) notificationShadeWindowView.findViewById(R.id.lockstar_view_container_stub);
        pluginLockStarManager.mDumpManager.registerNormalDumpable("PluginLockStar", pluginLockStarManager);
        if (pluginLockStarManager.mLockStarViewStub == null) {
            Log.e("LStar|PluginLockStarManager", "Illegal Access. view stub is null");
        }
        pluginLockStarManager.mDisplayLifecycle.addObserver(pluginLockStarManager);
        pluginLockStarManager.mWakefulnessLifecycle.addObserver(pluginLockStarManager);
        pluginLockStarManager.mStatusBarStateController.addCallback(pluginLockStarManager);
        pluginLockStarManager.mGoodLockLifecycle.addObserver(pluginLockStarManager.mObserver);
        pluginLockStarManager.checkGoodLockInstalledState();
        this.mPanelTouchBlockHelper = (SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class);
        dumpManager.registerDumpable(this);
    }

    public final void cancelCurrentTouch() {
        this.mShadeLogger.d("NSWVC: cancelling current touch");
        if (this.mTouchActive) {
            long uptimeMillis = this.mClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(this.mDownEvent);
            obtain.setDownTime(uptimeMillis);
            obtain.setAction(3);
            obtain.setLocation(0.0f, 0.0f);
            Log.w("NotifShadeWindowVC", "Canceling current touch event (should be very rare)");
            this.mView.dispatchTouchEvent(obtain);
            obtain.recycle();
            this.mTouchCancelled = true;
        }
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mIsSwipingUp) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = true;
        }
        ambientState.mIsSwipingUp = false;
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        com.android.systemui.Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mExpandAnimationRunning=");
        printWriter.println(this.mExpandAnimationRunning);
        printWriter.print("  mTouchCancelled=");
        printWriter.println(this.mTouchCancelled);
        printWriter.print("  mTouchActive=");
        printWriter.println(this.mTouchActive);
    }

    public void setDragDownHelper(DragDownHelper dragDownHelper) {
        this.mDragDownHelper = dragDownHelper;
    }

    public void setExpandAnimationRunning(boolean z) {
        if (this.mExpandAnimationRunning != z) {
            if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Setting mExpandAnimationRunning=", "NotifShadeWindowVC", z);
            }
            if (z) {
                this.mLaunchAnimationTimeout = this.mClock.uptimeMillis() + 5000;
            }
            this.mExpandAnimationRunning = z;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.launchingActivityFromNotification = z;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
    }
}
