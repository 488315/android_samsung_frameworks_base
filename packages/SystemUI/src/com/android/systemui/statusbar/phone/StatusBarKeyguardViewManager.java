package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.Rune;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.model.BouncerShowMessageModel;
import com.android.systemui.bouncer.ui.BouncerView;
import com.android.systemui.bouncer.ui.BouncerViewImpl;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Job;

public class StatusBarKeyguardViewManager implements RemoteInputController.Callback, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, ShadeExpansionListener, NavigationModeController.ModeChangedListener, KeyguardViewController, FoldAodAnimationController.FoldAodAnimationStatus {
    public final ActivityStarter mActivityStarter;
    public ActivityStarter.OnDismissAction mAfterKeyguardGoneAction;
    public final ArrayList mAfterKeyguardGoneRunnables;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public BiometricUnlockController mBiometricUnlockController;
    public boolean mBouncerShowingOverDream;
    public final Set mCallbacks;
    public CentralSurfaces mCentralSurfaces;
    public boolean mCentralSurfacesRegistered;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public boolean mDismissActionWillAnimateOnKeyguard;
    public final AnonymousClass3 mDockEventListener;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mFirstUpdate;
    public final FoldAodAnimationController mFoldAodAnimationController;
    public boolean mGesturalNav;
    public boolean mGlobalActionsVisible;
    public final boolean mIsBackAnimationEnabled;
    public final JavaAdapter mJavaAdapter;
    public Runnable mKeyguardGoneCancelAction;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public int mLastBiometricMode;
    public boolean mLastBouncerDismissible;
    public boolean mLastDozing;
    public boolean mLastGesturalNav;
    public boolean mLastGlobalActionsVisible;
    public boolean mLastIsDocked;
    public boolean mLastOccluded;
    public boolean mLastPrimaryBouncerIsOrWillBeShowing;
    public boolean mLastPrimaryBouncerShowing;
    public boolean mLastPulsing;
    public boolean mLastRemoteInputActive;
    public boolean mLastScreenOffAnimationPlaying;
    public boolean mLastShowing;
    public final LatencyTracker mLatencyTracker;
    public final AnonymousClass8 mMakeNavigationBarVisibleRunnable;
    public final NavigationModeController mNavigationModeController;
    public View mNotificationContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 mOccludingAppBiometricUI;
    public DismissWithActionRequest mPendingWakeupAction;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final BouncerView mPrimaryBouncerView;
    public boolean mPulsing;
    public float mQsExpansion;
    public boolean mRemoteInputActive;
    public boolean mScreenOffAnimationPlaying;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final Lazy mShadeController;
    public ShadeLockscreenInteractor mShadeLockscreenInteractor;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public TaskbarDelegate mTaskbarDelegate;
    public final UdfpsOverlayInteractor mUdfpsOverlayInteractor;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public final ViewMediatorCallback mViewMediatorCallback;
    public Job mListenForCanShowAlternateBouncer = null;
    public float mFraction = -1.0f;
    public boolean mTracking = false;
    public final AnonymousClass1 mExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.1
        public boolean mPrimaryBouncerAnimating;

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onExpansionChanged(float f) {
            if (this.mPrimaryBouncerAnimating) {
                ((CentralSurfacesImpl) StatusBarKeyguardViewManager.this.mCentralSurfaces).setPrimaryBouncerHiddenFraction(f);
            }
        }

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onFullyHidden() {
            this.mPrimaryBouncerAnimating = false;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onStartingToHide() {
            this.mPrimaryBouncerAnimating = true;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onStartingToShow() {
            this.mPrimaryBouncerAnimating = true;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onVisibilityChanged(boolean z) {
            ViewRootImpl viewRootImpl;
            ViewRootImpl viewRootImpl2;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            statusBarKeyguardViewManager.mKeyguardUpdateManager.sendPrimaryBouncerVisibilityChanged(z);
            statusBarKeyguardViewManager.mBouncerShowingOverDream = z && statusBarKeyguardViewManager.mDreamOverlayStateController.isOverlayActive();
            if (!z) {
                ((CentralSurfacesImpl) statusBarKeyguardViewManager.mCentralSurfaces).setPrimaryBouncerHiddenFraction(1.0f);
            }
            AnonymousClass2 anonymousClass2 = statusBarKeyguardViewManager.mOnBackInvokedCallback;
            if (z) {
                if (statusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl2 = statusBarKeyguardViewManager.getViewRootImpl()) == null) {
                    return;
                }
                viewRootImpl2.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, anonymousClass2);
                statusBarKeyguardViewManager.mIsBackCallbackRegistered = true;
                return;
            }
            if (!statusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl = statusBarKeyguardViewManager.getViewRootImpl()) == null) {
                return;
            }
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(anonymousClass2);
            statusBarKeyguardViewManager.mIsBackCallbackRegistered = false;
        }
    };
    public final AnonymousClass2 mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.2
        @Override // android.window.OnBackAnimationCallback
        public final void onBackCancelled() {
            if (!StatusBarKeyguardViewManager.m2233$$Nest$mshouldPlayBackAnimation(StatusBarKeyguardViewManager.this) || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackCancelled();
        }

        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            StatusBarKeyguardViewManager.this.onBackPressed();
            if (!StatusBarKeyguardViewManager.m2233$$Nest$mshouldPlayBackAnimation(StatusBarKeyguardViewManager.this) || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback();
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackProgressed(BackEvent backEvent) {
            if (!StatusBarKeyguardViewManager.m2233$$Nest$mshouldPlayBackAnimation(StatusBarKeyguardViewManager.this) || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackProgressed(backEvent);
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackStarted(BackEvent backEvent) {
            if (!StatusBarKeyguardViewManager.m2233$$Nest$mshouldPlayBackAnimation(StatusBarKeyguardViewManager.this) || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback().onBackStarted(backEvent);
        }
    };
    public boolean mIsBackCallbackRegistered = false;

    /* renamed from: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$8, reason: invalid class name */
    public final class AnonymousClass8 implements Runnable {
        public AnonymousClass8() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NavigationBarView navigationBarView = ((CentralSurfacesImpl) StatusBarKeyguardViewManager.this.mCentralSurfaces).getNavigationBarView();
            if (navigationBarView != null) {
                navigationBarView.setVisibility(0);
            }
            ((NotificationShadeWindowControllerImpl) StatusBarKeyguardViewManager.this.mNotificationShadeWindowController).mWindowRootView.getWindowInsetsController().show(WindowInsets.Type.navigationBars());
        }
    }

    public final class DismissWithActionRequest {
        public final boolean afterKeyguardGone;
        public final Runnable cancelAction;
        public final ActivityStarter.OnDismissAction dismissAction;
        public final String message;

        public DismissWithActionRequest(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
            this.dismissAction = onDismissAction;
            this.cancelAction = runnable;
            this.afterKeyguardGone = z;
            this.message = str;
        }
    }

    /* renamed from: -$$Nest$mshouldPlayBackAnimation, reason: not valid java name */
    public static boolean m2233$$Nest$mshouldPlayBackAnimation(StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        return !statusBarKeyguardViewManager.needsFullscreenBouncer() && statusBarKeyguardViewManager.mIsBackAnimationEnabled;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$2] */
    public StatusBarKeyguardViewManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, KeyguardMessageAreaController.Factory factory, Optional<SysUIUnfoldComponent> optional, Lazy lazy, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, Lazy lazy2, Lazy lazy3, SelectedUserInteractor selectedUserInteractor, JavaAdapter javaAdapter, Lazy lazy4, StatusBarKeyguardViewManagerInteractor statusBarKeyguardViewManagerInteractor) {
        new DockManager.DockEventListener(this) { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.3
        };
        this.mGlobalActionsVisible = false;
        this.mLastGlobalActionsVisible = false;
        this.mFirstUpdate = true;
        this.mCallbacks = new HashSet();
        this.mAfterKeyguardGoneRunnables = new ArrayList();
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onEmergencyCallAction() {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                if (((KeyguardStateControllerImpl) statusBarKeyguardViewManager.mKeyguardStateController).mOccluded) {
                    statusBarKeyguardViewManager.reset(true);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                AlternateBouncerInteractor alternateBouncerInteractor2 = statusBarKeyguardViewManager.mAlternateBouncerInteractor;
                statusBarKeyguardViewManager.updateAlternateBouncerShowing$1(alternateBouncerInteractor2.isVisibleState() ? alternateBouncerInteractor2.hide() : false);
            }
        };
        this.mMakeNavigationBarVisibleRunnable = new AnonymousClass8();
        this.mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mConfigurationController = configurationController;
        this.mNavigationModeController = navigationModeController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mShadeController = lazy;
        this.mLatencyTracker = latencyTracker;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mPrimaryBouncerView = bouncerView;
        this.mFoldAodAnimationController = (FoldAodAnimationController) optional.map(new SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda0()).orElse(null);
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        Flags.FEATURE_FLAGS.getClass();
        this.mIsBackAnimationEnabled = true;
        this.mActivityStarter = activityStarter;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mJavaAdapter = javaAdapter;
    }

    public void addAfterKeyguardGoneRunnable(Runnable runnable) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mAfterKeyguardGoneRunnables.add(runnable);
    }

    public void blockPanelExpansionFromCurrentTouch() {
        this.mShadeLockscreenInteractor.blockExpansionForCurrentTouch();
    }

    public boolean bouncerIsAnimatingAway() {
        return this.mPrimaryBouncerInteractor.isAnimatingAway();
    }

    public boolean canHandleBackPressed() {
        return primaryBouncerIsShowing();
    }

    public void cancelPendingWakeupAction() {
        Runnable runnable;
        DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (dismissWithActionRequest == null || (runnable = dismissWithActionRequest.cancelAction) == null) {
            return;
        }
        runnable.run();
    }

    public void cancelPostAuthActions() {
        if (primaryBouncerIsOrWillBeShowing()) {
            return;
        }
        this.mAfterKeyguardGoneAction = null;
        this.mDismissActionWillAnimateOnKeyguard = false;
        Runnable runnable = this.mKeyguardGoneCancelAction;
        if (runnable != null) {
            runnable.run();
            this.mKeyguardGoneCancelAction = null;
        }
    }

    public void consumeFromAlternateBouncerTransitionSteps(TransitionStep transitionStep) {
        hideAlternateBouncer(false);
    }

    public void consumeKeyguardAuthenticatedBiometricsHandled(Unit unit) {
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            hideAlternateBouncer(false);
        }
    }

    public void dismissAndCollapse() {
        this.mActivityStarter.executeRunnableDismissingKeyguard(null, null, true, false, true);
    }

    public void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
            try {
                Trace.beginSection("StatusBarKeyguardViewManager#dismissWithAction");
                cancelPendingWakeupAction();
                if (this.mDozing && !isWakeAndUnlocking()) {
                    this.mPendingWakeupAction = new DismissWithActionRequest(onDismissAction, runnable, z, str);
                    return;
                }
                Flags.sceneContainer();
                this.mAfterKeyguardGoneAction = onDismissAction;
                this.mKeyguardGoneCancelAction = runnable;
                this.mDismissActionWillAnimateOnKeyguard = onDismissAction != null && onDismissAction.willRunAnimationOnKeyguard();
                this.mAlternateBouncerInteractor.getClass();
                this.mViewMediatorCallback.setCustomMessage(str);
                PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
                if (z) {
                    Flags.sceneContainer();
                    primaryBouncerInteractor.show(true);
                } else {
                    primaryBouncerInteractor.setDismissAction(this.mAfterKeyguardGoneAction, this.mKeyguardGoneCancelAction);
                    Flags.sceneContainer();
                    primaryBouncerInteractor.show(true);
                    this.mAfterKeyguardGoneAction = null;
                    this.mKeyguardGoneCancelAction = null;
                }
            } finally {
                Trace.endSection();
            }
        } else {
            Log.w("StatusBarKeyguardViewManager", "Ignoring request to dismiss, dumping state: ");
            StringWriter stringWriter = new StringWriter();
            keyguardStateControllerImpl.dump(new PrintWriter(stringWriter), null);
            Log.w("StatusBarKeyguardViewManager", stringWriter.toString());
        }
        updateStates();
    }

    public boolean dispatchBackKeyEventPreIme() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && bouncerViewImpl.getDelegate().$securityContainerController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        shouldInterceptTouchEvent();
        shouldInterceptTouchEvent();
        return false;
    }

    public void dump(PrintWriter printWriter) {
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "StatusBarKeyguardViewManager:", "  mRemoteInputActive: "), this.mRemoteInputActive, printWriter, "  mDozing: "), this.mDozing, printWriter, "  mAfterKeyguardGoneAction: ");
        m.append(this.mAfterKeyguardGoneAction);
        printWriter.println(m.toString());
        printWriter.println("  mAfterKeyguardGoneRunnables: " + this.mAfterKeyguardGoneRunnables);
        printWriter.println("  mPendingWakeupAction: " + this.mPendingWakeupAction);
        printWriter.println("  isBouncerShowing(): " + isBouncerShowing());
        printWriter.println("  bouncerIsOrWillBeShowing(): " + primaryBouncerIsOrWillBeShowing());
        printWriter.println("  Registered KeyguardViewManagerCallbacks:");
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        printWriter.println(" SceneContainerFlag enabled:false");
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            printWriter.println("      " + ((UdfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1) it.next()));
        }
        if (this.mOccludingAppBiometricUI != null) {
            printWriter.println("mOccludingAppBiometricUI:");
            this.mOccludingAppBiometricUI.this$0.getClass();
            printWriter.println("UdfpsKeyguardViewController");
        }
    }

    public final void executeAfterKeyguardGoneAction$1() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        LogUtil.d("KeyguardUnlockInfo", "executeAfterKeyguardGoneAction dismissAction=%d, goneRunnable=%d", Integer.valueOf(LogUtil.getInt(this.mAfterKeyguardGoneAction)), Integer.valueOf(this.mAfterKeyguardGoneRunnables.size()));
        ActivityStarter.OnDismissAction onDismissAction = this.mAfterKeyguardGoneAction;
        if (onDismissAction != null) {
            onDismissAction.onDismiss();
            this.mAfterKeyguardGoneAction = null;
        }
        this.mKeyguardGoneCancelAction = null;
        this.mDismissActionWillAnimateOnKeyguard = false;
        for (int i2 = 0; i2 < this.mAfterKeyguardGoneRunnables.size(); i2++) {
            ((Runnable) this.mAfterKeyguardGoneRunnables.get(i2)).run();
        }
        this.mAfterKeyguardGoneRunnables.clear();
    }

    public boolean getLastNavBarVisible() {
        boolean z = this.mLastShowing && !this.mLastOccluded;
        boolean z2 = this.mLastDozing;
        return !(z || (z2 && this.mLastBiometricMode != 2) || this.mLastScreenOffAnimationPlaying) || this.mLastPrimaryBouncerShowing || this.mLastRemoteInputActive || (((z && !z2 && !this.mLastScreenOffAnimationPlaying) || (this.mLastPulsing && !this.mLastIsDocked)) && this.mLastGesturalNav) || this.mLastGlobalActionsVisible;
    }

    public boolean getLastPrimaryBouncerShowing() {
        return this.mLastPrimaryBouncerShowing;
    }

    public long getNavBarShowDelay() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway ? ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDelay : isBouncerShowing() ? 320L : 0L;
    }

    public ViewRootImpl getViewRootImpl() {
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        if (windowRootView != null) {
            return windowRootView.getViewRootImpl();
        }
        return null;
    }

    public void hide(long j, long j2) {
        Trace.beginSection("StatusBarKeyguardViewManager#hide");
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(false, keyguardStateControllerImpl.mOccluded);
        launchPendingWakeupAction();
        long j3 = this.mKeyguardUpdateManager.mNeedsSlowUnlockTransition ? DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY : j2;
        long max = Math.max(0L, (j - 48) - SystemClock.uptimeMillis());
        executeAfterKeyguardGoneAction$1();
        boolean isMode = ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED);
        if (isMode) {
            j3 = 0;
            max = 0;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        centralSurfacesImpl.mCommandQueue.appTransitionStarting((j + j3) - 120, 120L, true, centralSurfacesImpl.mDisplayId);
        centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, j3 > 0);
        centralSurfacesImpl.mCommandQueue.appTransitionStarting(j - 120, 120L, true, centralSurfacesImpl.mDisplayId);
        KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDelay = max;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDuration = j3;
        if (!keyguardStateControllerImpl2.mKeyguardFadingAway) {
            Trace.traceCounter(4096L, "keyguardFadingAway", 1);
            keyguardStateControllerImpl2.mKeyguardFadingAway = true;
            keyguardStateControllerImpl2.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(0));
        }
        final BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        biometricUnlockController.getClass();
        biometricUnlockController.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.2
            @Override // java.lang.Runnable
            public final void run() {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) BiometricUnlockController.this.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.forceDozeBrightness) {
                    notificationShadeWindowState.forceDozeBrightness = false;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
            }
        }, 96L);
        hideBouncer(true);
        boolean z = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide;
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        if (z || isMode || keyguardStateControllerImpl.mOccluded) {
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            ((CentralSurfacesImpl) this.mCentralSurfaces).finishKeyguardFadingAway();
            this.mBiometricUnlockController.finishKeyguardFadingAway();
        } else {
            ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).setKeyguardFadingAway(true);
            if (this.mBiometricUnlockController.isWakeAndUnlock() && this.mLatencyTracker.isEnabled()) {
                this.mLatencyTracker.onActionEnd(this.mBiometricUnlockController.mBiometricType == BiometricSourceType.FACE ? 7 : 2);
            }
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
        }
        updateStates();
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).setKeyguardShowing(false);
        this.mViewMediatorCallback.keyguardGone();
        SysUiStatsLog.write(62, 1);
        Trace.endSection();
    }

    public void hideAlternateBouncer(boolean z) {
        updateAlternateBouncerShowing$1(this.mAlternateBouncerInteractor.hide() && z);
    }

    public void hideBouncer(boolean z) {
        this.mPrimaryBouncerInteractor.hide();
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            cancelPostAuthActions();
        }
        cancelPendingWakeupAction();
    }

    public boolean interceptMediaKey(KeyEvent keyEvent) {
        BouncerView bouncerView = this.mPrimaryBouncerView;
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((BouncerViewImpl) bouncerView).getDelegate().$securityContainerController.interceptMediaKey(keyEvent);
    }

    public boolean isBouncerShowing() {
        return primaryBouncerIsShowing() || this.mAlternateBouncerInteractor.isVisibleState();
    }

    public boolean isBouncerShowingOverDream() {
        return this.mBouncerShowingOverDream;
    }

    public boolean isFullscreenBouncer() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && SecurityUtils.checkFullscreenBouncer(bouncerViewImpl.getDelegate().$securityContainerController.mCurrentSecurityMode);
    }

    public boolean isGoingToNotificationShade() {
        return ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide;
    }

    public boolean isNavBarVisible() {
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        boolean z = biometricUnlockController != null && biometricUnlockController.mMode == 2;
        boolean isVisible = this.mKeyguardStateController.isVisible();
        boolean z2 = this.mDozing;
        return !(isVisible || (z2 && !z) || this.mScreenOffAnimationPlaying) || primaryBouncerIsShowing() || this.mRemoteInputActive || (((isVisible && !z2 && !this.mScreenOffAnimationPlaying) || this.mPulsing) && this.mGesturalNav) || this.mGlobalActionsVisible;
    }

    public boolean isPrimaryBouncerInTransit() {
        return this.mPrimaryBouncerInteractor.isInTransit();
    }

    public boolean isSecure() {
        return this.mKeyguardSecurityModel.getSecurityMode(this.mSelectedUserInteractor.getSelectedUserId()) != KeyguardSecurityModel.SecurityMode.None;
    }

    public boolean isUnlockWithWallpaper() {
        return !((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.mediaBackdropShowing;
    }

    public boolean isWakeAndUnlocking() {
        int i = this.mBiometricUnlockController.mMode;
        return i == 1 || i == 2;
    }

    public void keyguardGoingAway() {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).notifyKeyguardGoingAway(true);
        CommandQueue commandQueue = centralSurfacesImpl.mCommandQueue;
        int i = centralSurfacesImpl.mDisplayId;
        synchronized (commandQueue.mLock) {
            commandQueue.mHandler.obtainMessage(1245184, i, 1).sendToTarget();
        }
        centralSurfacesImpl.updateScrimController();
    }

    public void launchPendingWakeupAction() {
        DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (dismissWithActionRequest != null) {
            boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
            ActivityStarter.OnDismissAction onDismissAction = dismissWithActionRequest.dismissAction;
            if (z) {
                dismissWithAction(onDismissAction, dismissWithActionRequest.cancelAction, dismissWithActionRequest.afterKeyguardGone, dismissWithActionRequest.message);
            } else if (onDismissAction != null) {
                onDismissAction.onDismiss();
            }
        }
    }

    public boolean needsFullscreenBouncer() {
        KeyguardSecurityModel.SecurityMode securityMode = this.mKeyguardSecurityModel.getSecurityMode(this.mSelectedUserInteractor.getSelectedUserId());
        return securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
    }

    public void notifyKeyguardAuthenticated(boolean z) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardAuthenticatedBiometrics.setValue(Boolean.valueOf(z));
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            executeAfterKeyguardGoneAction$1();
        }
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
    }

    public void onBackPressed() {
        if (canHandleBackPressed()) {
            boolean z = isBouncerShowing() && this.mDreamOverlayStateController.isOverlayActive();
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            centralSurfacesImpl.releaseGestureWakeLock();
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
            if (z || (primaryBouncerIsScrimmed() && !needsFullscreenBouncer())) {
                hideBouncer(false);
                updateStates();
                return;
            }
            boolean z2 = ((CentralSurfacesImpl) this.mCentralSurfaces).mScrimController.mState == ScrimState.BOUNCER_SCRIMMED;
            reset(z2);
            if (z2) {
                ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide = false;
            } else {
                this.mShadeLockscreenInteractor.expandToNotifications();
            }
        }
    }

    public void onDensityOrFontScaleChanged() {
        hideBouncer(true);
    }

    public void onDozingChanged(boolean z) {
        if (this.mDozing != z) {
            this.mDozing = z;
            if (z || needsFullscreenBouncer() || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded) {
                reset(z);
            }
            updateStates();
            if (z) {
                return;
            }
            launchPendingWakeupAction();
        }
    }

    public void onFinishedGoingToSleep() {
        this.mPrimaryBouncerInteractor.hide();
    }

    public void onFoldToAodAnimationChanged() {
        FoldAodAnimationController foldAodAnimationController = this.mFoldAodAnimationController;
        if (foldAodAnimationController != null) {
            this.mScreenOffAnimationPlaying = foldAodAnimationController.shouldPlayAnimation;
        }
    }

    public void onKeyguardFadedAway$1() {
        this.mNotificationContainer.postDelayed(new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 0), 100L);
        this.mShadeLockscreenInteractor.resetViewGroupFade();
        ((CentralSurfacesImpl) this.mCentralSurfaces).finishKeyguardFadingAway();
        this.mBiometricUnlockController.finishKeyguardFadingAway();
    }

    public void onNavigationModeChanged(int i) {
        boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
        if (isGesturalMode != this.mGesturalNav) {
            this.mGesturalNav = isGesturalMode;
            updateStates();
        }
    }

    public void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        float f = this.mFraction;
        float f2 = shadeExpansionChangeEvent.fraction;
        boolean z = shadeExpansionChangeEvent.tracking;
        if (f == f2 && this.mTracking == z) {
            return;
        }
        this.mFraction = f2;
        this.mTracking = z;
        if (primaryBouncerIsOrWillBeShowing() && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            this.mPrimaryBouncerInteractor.setPanelExpansion(1.0f);
        }
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public void onRemoteInputActive(boolean z) {
        this.mRemoteInputActive = z;
        updateStates();
    }

    public void onStartedGoingToSleep() {
        WindowInsetsController windowInsetsController;
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        if (windowRootView != null && (windowInsetsController = windowRootView.getWindowInsetsController()) != null) {
            windowInsetsController.setAnimationsDisabled(true);
        }
        NavigationBarView navigationBarView = ((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView();
        if (navigationBarView != null) {
            View view = navigationBarView.mVertical;
            if (view != null) {
                view.animate().alpha(0.0f).setDuration(125L).start();
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                view2.animate().alpha(0.0f).setDuration(125L).start();
            }
        }
    }

    public void onStartedWakingUp() {
        WindowInsetsController windowInsetsController;
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        if (windowRootView != null && (windowInsetsController = windowRootView.getWindowInsetsController()) != null) {
            windowInsetsController.setAnimationsDisabled(false);
        }
        NavigationBarView navigationBarView = ((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView();
        if (navigationBarView != null) {
            View view = navigationBarView.mVertical;
            if (view != null) {
                view.animate().alpha(1.0f).setDuration(125L).start();
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                view2.animate().alpha(1.0f).setDuration(125L).start();
            }
        }
    }

    public void onThemeChanged() {
        updateResources$1();
    }

    public void onTouch() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
    }

    public boolean primaryBouncerIsOrWillBeShowing() {
        return isBouncerShowing() || isPrimaryBouncerInTransit();
    }

    public boolean primaryBouncerIsScrimmed() {
        return this.mPrimaryBouncerInteractor.isScrimmed();
    }

    public boolean primaryBouncerIsShowing() {
        return this.mPrimaryBouncerInteractor.isFullyShowing();
    }

    public boolean primaryBouncerNeedsScrimming() {
        return (((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded && !this.mDreamOverlayStateController.isOverlayActive()) || primaryBouncerWillDismissWithAction() || (primaryBouncerIsShowing() && primaryBouncerIsScrimmed()) || isFullscreenBouncer();
    }

    public boolean primaryBouncerWillDismissWithAction() {
        return this.mPrimaryBouncerInteractor.willDismissWithAction();
    }

    public void readyForKeyguardDone() {
        this.mViewMediatorCallback.readyForKeyguardDone();
    }

    public void registerCentralSurfaces(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mCentralSurfaces = centralSurfaces;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(this.mExpansionCallback);
        this.mShadeLockscreenInteractor = shadeLockscreenInteractor;
        if (shadeExpansionStateManager != null) {
            onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(this));
        }
        this.mNotificationContainer = view;
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        this.mCentralSurfacesRegistered = true;
        this.mKeyguardUpdateManager.registerCallback(this.mUpdateMonitorCallback);
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).addCallback((StatusBarStateController.StateListener) this);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        this.mGesturalNav = !BasicRune.NAVBAR_GESTURE && QuickStepContract.isGesturalMode(this.mNavigationModeController.addListener(this));
        FoldAodAnimationController foldAodAnimationController = this.mFoldAodAnimationController;
        if (foldAodAnimationController != null) {
            foldAodAnimationController.statusListeners.add(this);
        }
        Job job = this.mListenForCanShowAlternateBouncer;
        if (job != null) {
            job.cancel(null);
        }
        this.mListenForCanShowAlternateBouncer = null;
        Flags.deviceEntryUdfpsRefactor();
        this.mListenForCanShowAlternateBouncer = this.mJavaAdapter.alwaysCollectFlow(this.mAlternateBouncerInteractor.canShowAlternateBouncer, new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                ((Boolean) obj).booleanValue();
                statusBarKeyguardViewManager.getClass();
            }
        });
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
    }

    public void requestFp(boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        keyguardUpdateMonitor.mOccludingAppRequestingFp = z;
        keyguardUpdateMonitor.updateFingerprintListeningState(2);
        UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 = this.mOccludingAppBiometricUI;
        if (udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1 != null) {
            UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = udfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1.this$0;
            udfpsKeyguardViewControllerLegacy.udfpsRequested = z;
            udfpsKeyguardViewControllerLegacy.view.mUdfpsRequested = z;
            udfpsKeyguardViewControllerLegacy.updateAlpha();
            udfpsKeyguardViewControllerLegacy.updatePauseAuth();
        }
    }

    public void reset(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (!keyguardStateControllerImpl.mShowing || bouncerIsAnimatingAway()) {
            return;
        }
        boolean z2 = keyguardStateControllerImpl.mOccluded;
        this.mShadeLockscreenInteractor.resetViews(!z2);
        if (!z2 || this.mDozing) {
            showBouncerOrKeyguard(z);
        } else {
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            if (z || needsFullscreenBouncer()) {
                hideBouncer(false);
            }
        }
        if (z) {
            hideAlternateBouncer(true);
        }
        this.mKeyguardUpdateManager.mHandler.obtainMessage(312).sendToTarget();
        updateStates();
    }

    public void setGlobalActionsVisible(boolean z) {
        this.mGlobalActionsVisible = z;
        updateStates();
    }

    public void setKeyguardGoingAwayState(boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        if (secNotificationShadeWindowControllerHelperImpl.getCurrentState().keyguardGoingAway != z) {
            com.android.systemui.keyguard.Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("keyguardGoingAway ", z));
        }
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardGoingAway = z;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
    }

    public void setKeyguardMessage(String str, ColorStateList colorStateList) {
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            return;
        }
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        primaryBouncerInteractor.getClass();
        ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._showMessage.setValue(new BouncerShowMessageModel(str, colorStateList));
    }

    public void setNeedsInput(boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardNeedsInput = z;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
    }

    public void setOccluded(boolean z, boolean z2) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mOccluded;
        boolean z4 = false;
        boolean z5 = !z3 && z;
        if (z3 && !z) {
            z4 = true;
        }
        keyguardStateControllerImpl.notifyKeyguardState(keyguardStateControllerImpl.mShowing, z);
        updateStates();
        boolean z6 = keyguardStateControllerImpl.mShowing;
        final boolean z7 = keyguardStateControllerImpl.mOccluded;
        if (z6 && z5) {
            SysUiStatsLog.write(62, 3);
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.mIsLaunchingActivityOverLockscreen) {
                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                        boolean z8 = z7;
                        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) statusBarKeyguardViewManager.mNotificationShadeWindowController;
                        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                        notificationShadeWindowState.keyguardOccluded = z8;
                        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                        statusBarKeyguardViewManager.reset(true);
                    }
                };
                if (centralSurfacesImpl.mDismissingShadeForActivityLaunch) {
                    ((BaseShadeControllerImpl) ((ShadeController) this.mShadeController.get())).postCollapseActions.add(runnable);
                    return;
                } else {
                    runnable.run();
                    return;
                }
            }
        } else if (z6 && z4) {
            SysUiStatsLog.write(62, 2);
        }
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardOccluded = z7;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        postSetOccluded(z5);
    }

    public void setPulsing(boolean z) {
        if (this.mPulsing != z) {
            this.mPulsing = z;
            updateStates();
        }
    }

    public void setQsExpansion(float f) {
        this.mQsExpansion = f;
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            UdfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1 udfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1 = (UdfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1) it.next();
            float f2 = this.mQsExpansion;
            UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = udfpsKeyguardViewControllerLegacy$statusBarKeyguardViewManagerCallback$1.this$0;
            udfpsKeyguardViewControllerLegacy.qsExpansion = f2;
            udfpsKeyguardViewControllerLegacy.updateAlpha();
            udfpsKeyguardViewControllerLegacy.updatePauseAuth();
        }
    }

    public void setTaskbarDelegate(TaskbarDelegate taskbarDelegate) {
        this.mTaskbarDelegate = taskbarDelegate;
    }

    public boolean shouldDismissOnMenuPressed() {
        BouncerView bouncerView = this.mPrimaryBouncerView;
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((BouncerViewImpl) bouncerView).getDelegate().$securityContainerController.shouldEnableMenuKey();
    }

    public void shouldInterceptTouchEvent() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
    }

    public void show(Bundle bundle) {
        Trace.beginSection("StatusBarKeyguardViewManager#show");
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setKeyguardShowing(true);
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).notifyKeyguardState(true, ((KeyguardStateControllerImpl) keyguardStateController).mOccluded);
        reset(true);
        SysUiStatsLog.write(62, 2);
        Trace.endSection();
    }

    public void showBouncer() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        this.mAlternateBouncerInteractor.getClass();
        showPrimaryBouncer(true);
    }

    public void showBouncerOrKeyguard(boolean z) {
        if (!needsFullscreenBouncer() || this.mDozing) {
            ((CentralSurfacesImpl) this.mCentralSurfaces).showKeyguard();
            if (z) {
                hideBouncer(false);
            }
        } else if (primaryBouncerIsShowing()) {
            Log.e("StatusBarKeyguardViewManager", "Attempted to show the sim bouncer when it is already showing.");
        } else {
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            this.mPrimaryBouncerInteractor.show(true);
        }
        updateStates();
    }

    public void showPrimaryBouncer(boolean z) {
        hideAlternateBouncer(false);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && !isBouncerShowing()) {
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            this.mPrimaryBouncerInteractor.show(z);
        }
        updateStates();
    }

    public void startPreHideAnimation(Runnable runnable) {
        if (primaryBouncerIsShowing()) {
            this.mPrimaryBouncerInteractor.startDisappearAnimation(runnable);
            if (!LsRune.LOCKUI_MULTI_USER) {
                this.mShadeLockscreenInteractor.startBouncerPreHideAnimation();
            }
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            if (this.mDismissActionWillAnimateOnKeyguard) {
                updateStates();
            }
        } else if (runnable != null) {
            runnable.run();
        }
        this.mShadeLockscreenInteractor.blockExpansionForCurrentTouch();
    }

    public final void updateAlternateBouncerShowing$1(boolean z) {
        if (this.mCentralSurfacesRegistered) {
            this.mKeyguardUpdateManager.setAlternateBouncerShowing(this.mAlternateBouncerInteractor.isVisibleState());
            if (z) {
                ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
            }
        }
    }

    public void updateKeyguardPosition(float f) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardPosition.updateState(null, Float.valueOf(f));
    }

    public void updateNavigationBarVisibility(boolean z) {
        TaskbarDelegate taskbarDelegate;
        if (((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView() != null || ((taskbarDelegate = this.mTaskbarDelegate) != null && taskbarDelegate.mInitialized)) {
            NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
            if (!z) {
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mWindowRootView.removeCallbacks(this.mMakeNavigationBarVisibleRunnable);
                } else {
                    this.mNotificationContainer.removeCallbacks(this.mMakeNavigationBarVisibleRunnable);
                }
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mWindowRootView.getWindowInsetsController().hide(WindowInsets.Type.navigationBars());
                return;
            }
            long navBarShowDelay = getNavBarShowDelay();
            if (navBarShowDelay == 0) {
                this.mMakeNavigationBarVisibleRunnable.run();
            } else if (LsRune.SECURITY_BOUNCER_WINDOW) {
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mWindowRootView.postOnAnimationDelayed(this.mMakeNavigationBarVisibleRunnable, navBarShowDelay);
            } else {
                this.mNotificationContainer.postOnAnimationDelayed(this.mMakeNavigationBarVisibleRunnable, navBarShowDelay);
            }
        }
    }

    public void updateResources$1() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository;
        keyguardBouncerRepositoryImpl._resourceUpdateRequests.updateState(null, Boolean.TRUE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r13v1, types: [com.android.systemui.shade.ShadeSurface] */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.android.keyguard.KeyguardViewController, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8, types: [com.android.keyguard.logging.KeyguardUpdateMonitorLogger] */
    public void updateStates() {
        if (this.mCentralSurfacesRegistered) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            boolean z = keyguardStateControllerImpl.mShowing;
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            ?? r3 = (!primaryBouncerIsShowing() || (LsRune.SECURITY_BOUNCER_WINDOW && !z)) ? 0 : 1;
            boolean primaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing();
            boolean z3 = !isFullscreenBouncer();
            boolean z4 = this.mRemoteInputActive;
            Rune.runIf((Runnable) new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 1), true);
            if ((z3 || !z || z4) != (this.mLastBouncerDismissible || !this.mLastShowing || this.mLastRemoteInputActive) || this.mFirstUpdate) {
                PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
                if (z3 || !z || z4) {
                    primaryBouncerInteractor.setBackButtonEnabled(true);
                } else {
                    primaryBouncerInteractor.setBackButtonEnabled(false);
                }
            }
            boolean isNavBarVisible = isNavBarVisible();
            if (isNavBarVisible != getLastNavBarVisible() || this.mFirstUpdate) {
                updateNavigationBarVisibility(isNavBarVisible);
            }
            sendKeyguardViewState(z, z2, r3);
            ?? r9 = r3 != this.mLastPrimaryBouncerShowing;
            this.mLastPrimaryBouncerShowing = r3;
            if (r9 != false || this.mFirstUpdate) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.bouncerShowing = r3;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
                centralSurfacesImpl.mBouncerShowing = r3;
                centralSurfacesImpl.mKeyguardBypassController.bouncerShowing = r3;
                centralSurfacesImpl.mPulseExpansionHandler.bouncerShowing = r3;
                int i = r3 != 0 ? 4 : 0;
                PhoneStatusBarViewController phoneStatusBarViewController = centralSurfacesImpl.mPhoneStatusBarViewController;
                if (phoneStatusBarViewController != null) {
                    phoneStatusBarViewController.setImportantForAccessibility(i);
                }
                ?? r13 = centralSurfacesImpl.mShadeSurface;
                r13.setImportantForAccessibility(i);
                r13.setBouncerShowing(r3);
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = centralSurfacesImpl.mStatusBarHideIconsForBouncerManager;
                statusBarHideIconsForBouncerManager.bouncerShowing = r3;
                statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(true);
                centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, true);
                if (centralSurfacesImpl.mBouncerShowing) {
                    centralSurfacesImpl.mPowerInteractor.wakeUpIfDozing(4, "BOUNCER_VISIBLE");
                }
                centralSurfacesImpl.updateScrimController();
                if (!centralSurfacesImpl.mBouncerShowing) {
                    centralSurfacesImpl.updatePanelExpansionForKeyguard();
                }
                boolean z5 = LsRune.SECURITY_CAPTURED_BLUR;
                SecQpBlurController secQpBlurController = centralSurfacesImpl.mBlurController;
                if (z5) {
                    if (z5 && DeviceState.isCapturedBlurAllowed()) {
                        secQpBlurController.getClass();
                        if (z5) {
                            secQpBlurController.mIsBouncerShowing = r3;
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setBouncerShowing: ", "SecQpBlurController", r3);
                            ?? r11 = secQpBlurController.mPanelExpandedFraction != 0.0f;
                            CapturedBlurContainerController capturedBlurContainerController = secQpBlurController.mCapturedBlurController;
                            capturedBlurContainerController.mIsBouncerShowing = secQpBlurController.mIsBouncerShowing;
                            capturedBlurContainerController.updateContainerVisibility();
                            if (r3 != 0) {
                                secQpBlurController.doCaptureContainerAlpha(1.0f, secQpBlurController.mKeyguardUpdateMonitor.mKeyguardOccluded ? CapturedBlurContainerController.BlurType.QUICK_PANEL : CapturedBlurContainerController.BlurType.BOUNCER);
                            } else if (r3 == 0 && secQpBlurController.mStatusBarStateController.getState() == 1 && r11 == false) {
                                secQpBlurController.doCaptureContainerAlpha(0.0f, CapturedBlurContainerController.BlurType.BOUNCER);
                            }
                        }
                    }
                    NotificationShadeWindowView notificationShadeWindowView = centralSurfacesImpl.getNotificationShadeWindowViewController().mView;
                    if (notificationShadeWindowView.mBouncerShowing != r3) {
                        notificationShadeWindowView.mBouncerShowing = r3;
                        notificationShadeWindowView.applyBouncerMargins();
                    }
                }
                if (QpRune.QUICK_PANEL_BLUR_DEFAULT && secQpBlurController != null) {
                    secQpBlurController.mIsBouncerShowing = r3;
                    secQpBlurController.mAnimatedFraction = 0.0f;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setBouncerWindowShowing: ", "SecQpBlurController", r3);
                    if (r3 != 0) {
                        float f = secQpBlurController.mPanelExpandedFraction;
                        if (f != 0.0f) {
                            secQpBlurController.makeAnimationAndRun(f, 0.0f, 350);
                        }
                    }
                    if (r3 == 0 && secQpBlurController.mStatusBarStateController.getState() == 0 && secQpBlurController.mPanelExpandedFraction == 1.0f) {
                        secQpBlurController.makeAnimationAndRun(0.0f, 1.0f, 350);
                    }
                }
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    updateBouncerNavigationBar(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
                }
            }
            if (primaryBouncerIsOrWillBeShowing != this.mLastPrimaryBouncerIsOrWillBeShowing || this.mFirstUpdate || r9 != false) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
                keyguardUpdateMonitor.mLogger.logSendPrimaryBouncerChanged(primaryBouncerIsOrWillBeShowing, r3);
                Message obtainMessage = keyguardUpdateMonitor.mHandler.obtainMessage(322);
                obtainMessage.arg1 = primaryBouncerIsOrWillBeShowing ? 1 : 0;
                obtainMessage.arg2 = r3;
                obtainMessage.sendToTarget();
            }
            this.mFirstUpdate = false;
            this.mLastShowing = z;
            this.mLastGlobalActionsVisible = this.mGlobalActionsVisible;
            this.mLastOccluded = z2;
            this.mLastPrimaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing;
            this.mLastBouncerDismissible = z3;
            this.mLastRemoteInputActive = z4;
            this.mLastDozing = this.mDozing;
            this.mLastPulsing = this.mPulsing;
            this.mLastScreenOffAnimationPlaying = this.mScreenOffAnimationPlaying;
            this.mLastBiometricMode = this.mBiometricUnlockController.mMode;
            this.mLastGesturalNav = this.mGesturalNav;
            this.mLastIsDocked = false;
            Rune.runIf((Runnable) new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 2), true);
            ((CentralSurfacesImpl) this.mCentralSurfaces).logStateToEventlog();
        }
    }

    public void shouldSubtleWindowAnimationsForUnlock() {
    }
}
