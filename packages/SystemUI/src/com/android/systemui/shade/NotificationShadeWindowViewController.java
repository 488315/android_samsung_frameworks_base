package com.android.systemui.shade;

import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBouncerContainer;
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
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.ui.binder.BouncerViewBinder;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.ui.view.WindowRootViewKeyEventHandler;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.animation.DisableSubpixelTextTransitionListener;
import com.android.systemui.statusbar.BlurUtils;
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
import com.android.systemui.statusbar.phone.ConfigurationForwarder;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.window.ui.WindowRootViewBinder;
import com.android.systemui.window.ui.viewmodel.WindowRootViewModel;
import java.io.PrintWriter;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public class NotificationShadeWindowViewController implements Dumpable {
    public final AmbientState mAmbientState;
    public final ViewGroup mBouncerParentView;
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
    public final GlanceableHubContainerController mGlanceableHubContainerController;
    public float mInitialExpandX;
    public float mInitialExpandY;
    public final KeyguardSysDumpTrigger mKeyguardSysDumpTrigger;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public long mLaunchAnimationTimeout;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationShadeWindowViewController$$ExternalSyntheticLambda6 mLockscreenToDreamingTransition;
    public final NotificationInsetsController mNotificationInsetsController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final SecPanelTouchBlockHelper mPanelTouchBlockHelper;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final QuickSettingsController mQuickSettingsController;
    public boolean mSecKeyguardStatusViewTouchArea;
    public final CentralSurfaces mService;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeLogger mShadeLogger;
    public final ShadeViewController mShadeViewController;
    public NotificationStackScrollLayout mStackScrollLayout;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public PhoneStatusBarViewController mStatusBarViewController;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final SysUIKeyEventHandler mSysUIKeyEventHandler;
    public boolean mTouchActive;
    public boolean mTouchCancelled;
    public final NotificationShadeWindowView mView;
    public final WindowRootViewKeyEventHandler mWindowRootViewKeyEventHandler;
    public boolean mExternalTouchIntercepted = false;
    public boolean mIsTrackingBarGesture = false;
    public boolean mIsOcclusionTransitionRunning = false;
    public boolean mPluginLockTouchArea = false;

    /* renamed from: com.android.systemui.shade.NotificationShadeWindowViewController$1, reason: invalid class name */
    public class AnonymousClass1 {
        public boolean mLastInterceptWasDragDownHelper = false;

        public AnonymousClass1() {
        }
    }

    /* renamed from: -$$Nest$mdidNotificationPanelInterceptEvent, reason: not valid java name */
    public static boolean m2947$$Nest$mdidNotificationPanelInterceptEvent(NotificationShadeWindowViewController notificationShadeWindowViewController, MotionEvent motionEvent) {
        if (!notificationShadeWindowViewController.mShadeViewController.handleExternalInterceptTouch(motionEvent)) {
            return false;
        }
        notificationShadeWindowViewController.mShadeLogger.d("NSWVC: NPVC intercepted");
        return true;
    }

    /* renamed from: -$$Nest$mlogDownDispatch, reason: not valid java name */
    public static void m2948$$Nest$mlogDownDispatch(NotificationShadeWindowViewController notificationShadeWindowViewController, MotionEvent motionEvent, String str, final Boolean bool) {
        notificationShadeWindowViewController.getClass();
        if (motionEvent.getAction() == 0) {
            ShadeLogger shadeLogger = notificationShadeWindowViewController.mShadeLogger;
            shadeLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            Function1 function1 = new Function1() { // from class: com.android.systemui.shade.ShadeLogger$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    String str2;
                    LogMessage logMessage = (LogMessage) obj;
                    Boolean bool2 = Boolean.TRUE;
                    Boolean bool3 = bool;
                    if (Intrinsics.areEqual(bool3, bool2)) {
                        str2 = "SHADE TOUCH REROUTED";
                    } else if (Intrinsics.areEqual(bool3, Boolean.FALSE)) {
                        str2 = "SHADE TOUCH BLOCKED";
                    } else {
                        if (bool3 != null) {
                            throw new NoWhenBranchMatchedException();
                        }
                        str2 = "SHADE TOUCH DISPATCHED";
                    }
                    return str2 + ": eventTime=" + logMessage.getLong1() + ",downTime=" + logMessage.getLong2() + ", reason=" + logMessage.getStr1();
                }
            };
            LogBuffer logBuffer = shadeLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, function1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.long1 = motionEvent.getEventTime();
            logMessageImpl.long2 = motionEvent.getDownTime();
            logBuffer.commit(logMessageObtain);
        }
    }

    public NotificationShadeWindowViewController(BlurUtils blurUtils, WindowRootViewModel.Factory factory, Choreographer choreographer, KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory2, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginLockStarManager pluginLockStarManager, KeyguardSysDumpTrigger keyguardSysDumpTrigger, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, SysuiStatusBarStateController sysuiStatusBarStateController, DockManager dockManager, NotificationShadeDepthController notificationShadeDepthController, NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, ShadeAnimationInteractor shadeAnimationInteractor, PanelExpansionInteractor panelExpansionInteractor, ShadeExpansionStateManager shadeExpansionStateManager, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarWindowStateController statusBarWindowStateController, final SecLockIconViewController secLockIconViewController, CentralSurfaces centralSurfaces, DozeServiceHost dozeServiceHost, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, Optional<UnfoldTransitionProgressProvider> optional, Optional<SysUIUnfoldComponent> optional2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, NotificationInsetsController notificationInsetsController, AmbientState ambientState, ShadeLogger shadeLogger, DumpManager dumpManager, PulsingGestureListener pulsingGestureListener, KeyguardTransitionInteractor keyguardTransitionInteractor, GlanceableHubContainerController glanceableHubContainerController, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, FeatureFlagsClassic featureFlagsClassic, SystemClock systemClock, WindowRootViewKeyEventHandler windowRootViewKeyEventHandler, QuickSettingsController quickSettingsController, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, BouncerViewBinder bouncerViewBinder, Provider provider, BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, SysUIKeyEventHandler sysUIKeyEventHandler, CoroutineDispatcher coroutineDispatcher) {
        int i = 0;
        NotificationShadeWindowViewController$$ExternalSyntheticLambda6 notificationShadeWindowViewController$$ExternalSyntheticLambda6 = new NotificationShadeWindowViewController$$ExternalSyntheticLambda6(this, 1);
        this.mLockscreenToDreamingTransition = notificationShadeWindowViewController$$ExternalSyntheticLambda6;
        this.mSecKeyguardStatusViewTouchArea = false;
        this.mInitialExpandX = 0.0f;
        this.mInitialExpandY = 0.0f;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mFalsingCollector = falsingCollector;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mView = notificationShadeWindowView;
        this.mShadeViewController = shadeViewController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mDepthController = notificationShadeDepthController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
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
        this.mWindowRootViewKeyEventHandler = windowRootViewKeyEventHandler;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mQuickSettingsController = quickSettingsController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mBrightnessMirror = notificationShadeWindowView.findViewById(R.id.brightness_mirror_container);
        new DisableSubpixelTextTransitionListener(notificationShadeWindowView);
        this.mKeyguardSysDumpTrigger = keyguardSysDumpTrigger;
        this.mBouncerViewBinder = bouncerViewBinder;
        this.mSysUIKeyEventHandler = sysUIKeyEventHandler;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            FrameLayout keyguardBouncerContainer = z ? new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), sysUIKeyEventHandler, keyguardSysDumpTrigger) : (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
            if (z) {
                ((CentralSurfacesImpl) centralSurfaces).mBouncerContainer = keyguardBouncerContainer;
                Log.d("NotifShadeWindowVC", "addBouncer!");
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(keyguardBouncerContainer);
            }
            if (z) {
                bouncerViewBinder.bind(keyguardBouncerContainer);
            } else {
                ViewGroup viewGroup = (ViewGroup) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
                this.mBouncerParentView = viewGroup;
                bouncerViewBinder.bind(viewGroup);
            }
            ComposeBouncerFlags.INSTANCE.getClass();
        }
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        KeyguardState keyguardState2 = KeyguardState.DREAMING;
        Edge.Companion.getClass();
        JavaAdapterKt.collectFlow(notificationShadeWindowView, keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState, keyguardState2)), notificationShadeWindowViewController$$ExternalSyntheticLambda6);
        JavaAdapterKt.collectFlow(notificationShadeWindowView, JavaAdapterKt.combineFlows(notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, shadeAnimationInteractor.isLaunchingActivity, new NotificationShadeWindowViewController$$ExternalSyntheticLambda1()), new NotificationShadeWindowViewController$$ExternalSyntheticLambda6(this, 2));
        int i2 = QSComposeFragment.$r8$clinit;
        Optional<U> map = optional2.map(new NotificationPanelViewController$$ExternalSyntheticLambda10(i));
        Optional<U> map2 = optional2.map(new NotificationShadeWindowViewController$$ExternalSyntheticLambda3());
        map.ifPresent(new NotificationShadeWindowViewController$$ExternalSyntheticLambda4());
        map2.ifPresent(new NotificationShadeWindowViewController$$ExternalSyntheticLambda6(this, 3));
        this.mClock = systemClock;
        Flags flags = Flags.INSTANCE;
        featureFlagsClassic.getClass();
        if (ShadeWindowGoesAround.isEnabled()) {
            ConfigurationForwarder configurationForwarder = (ConfigurationForwarder) provider.get();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            ShadeWindowGoesAround.INSTANCE.getClass();
            if (!ShadeWindowGoesAround.FLAG.isTrue()) {
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
            }
            notificationShadeWindowView.mConfigurationForwarder = configurationForwarder;
        }
        int i3 = SceneContainerFlag.$r8$clinit;
        WindowRootViewBinder.INSTANCE.getClass();
        SecLockIconView secLockIconView = (SecLockIconView) notificationShadeWindowView.findViewById(R.id.sec_lock_icon_view);
        secLockIconViewController.mView = secLockIconView;
        secLockIconViewController.updateKeyguardShowing();
        secLockIconViewController.mIsBouncerShowing = secLockIconViewController.mKeyguardViewController.isBouncerShowing();
        secLockIconViewController.mIsDozing = secLockIconViewController.mStatusBarStateController.isDozing();
        secLockIconViewController.mRunningFPS = secLockIconViewController.mKeyguardUpdateMonitor.isFingerprintDetectionRunning();
        secLockIconViewController.mCanDismissLockScreen = ((KeyguardStateControllerImpl) secLockIconViewController.mKeyguardStateController).mCanDismissLockScreen;
        Log.d("SecLockIconViewController", "setSecLockIconView : mIsBouncerShowing = " + secLockIconViewController.mIsBouncerShowing + " mIsDozing = " + secLockIconViewController.mIsDozing);
        Log.d("SecLockIconViewController", "setSecLockIconView : mRunningFPS = " + secLockIconViewController.mRunningFPS + " mCanDismissLockScreen = " + secLockIconViewController.mCanDismissLockScreen);
        secLockIconViewController.updateVisibility$4();
        secLockIconView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.SecLockIconViewController.3
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                SecLockIconViewController.this.registerCallbacks$3();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                SecLockIconViewController.m972$$Nest$munregisterCallbacks(SecLockIconViewController.this);
            }
        });
        if (secLockIconView.isAttachedToWindow()) {
            secLockIconViewController.registerCallbacks$3();
        }
        secLockIconView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.SecLockIconViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                secLockIconViewController.getClass();
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
            long jUptimeMillis = this.mClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(this.mDownEvent);
            motionEventObtain.setDownTime(jUptimeMillis);
            motionEventObtain.setAction(3);
            motionEventObtain.setLocation(0.0f, 0.0f);
            Log.w("NotifShadeWindowVC", "Canceling current touch event (should be very rare)");
            this.mView.dispatchTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
            this.mTouchCancelled = true;
        }
        int i = SceneContainerFlag.$r8$clinit;
        this.mAmbientState.setSwipingUp(false);
        this.mDragDownHelper.stopDragging();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mExpandingBelowNotch=");
        printWriter.println(this.mExpandingBelowNotch);
        printWriter.print("  mExpandAnimationRunning=");
        printWriter.println(this.mExpandAnimationRunning);
        printWriter.print("  mExternalTouchIntercepted=");
        printWriter.println(this.mExternalTouchIntercepted);
        printWriter.print("  mIsOcclusionTransitionRunning=");
        printWriter.println(this.mIsOcclusionTransitionRunning);
        printWriter.print("  mIsTrackingBarGesture=");
        printWriter.println(this.mIsTrackingBarGesture);
        printWriter.print("  mLaunchAnimationTimeout=");
        printWriter.println(this.mLaunchAnimationTimeout);
        printWriter.print("  mTouchActive=");
        printWriter.println(this.mTouchActive);
        printWriter.print("  mTouchCancelled=");
        printWriter.println(this.mTouchCancelled);
    }

    public void setDragDownHelper(DragDownHelper dragDownHelper) {
        this.mDragDownHelper = dragDownHelper;
    }

    public void setExpandAnimationRunning(boolean z) {
        if (this.mExpandAnimationRunning != z) {
            if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("Setting mExpandAnimationRunning=", "NotifShadeWindowVC", z);
            }
            if (z) {
                this.mLaunchAnimationTimeout = this.mClock.uptimeMillis() + 5000;
            }
            this.mView.mAnimatingContentLaunch = z;
            this.mExpandAnimationRunning = z;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.launchingActivityFromNotification = z;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
    }
}
