package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.os.Trace;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.Rune;
import com.android.systemui.biometrics.C1079xbb37c88d;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsControllerOverlay;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$occludingAppBiometricUI$1;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.BouncerViewImpl;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenShadeTransitionController$goToLockedShadeInternal$1;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RunnableC2570x13216739;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.time.SystemClockImpl;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarKeyguardViewManager implements RemoteInputController.Callback, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, ShadeExpansionListener, NavigationModeController.ModeChangedListener, KeyguardViewController, FoldAodAnimationController.FoldAodAnimationStatus {
    public final ActivityStarter mActivityStarter;
    public ActivityStarter.OnDismissAction mAfterKeyguardGoneAction;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public BiometricUnlockController mBiometricUnlockController;
    public KeyguardBypassController mBypassController;
    public CentralSurfaces mCentralSurfaces;
    public boolean mCentralSurfacesRegistered;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public boolean mDismissActionWillAnimateOnKeyguard;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final FoldAodAnimationController mFoldAodAnimationController;
    public boolean mGesturalNav;
    public Runnable mKeyguardGoneCancelAction;
    public KeyguardMessageAreaController mKeyguardMessageAreaController;
    public final KeyguardMessageAreaController.Factory mKeyguardMessageAreaFactory;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public int mLastBiometricMode;
    public boolean mLastBouncerDismissible;
    public boolean mLastDozing;
    public boolean mLastGesturalNav;
    public boolean mLastIsDocked;
    public boolean mLastOccluded;
    public boolean mLastPrimaryBouncerIsOrWillBeShowing;
    public boolean mLastPrimaryBouncerShowing;
    public boolean mLastPulsing;
    public boolean mLastRemoteInputActive;
    public boolean mLastScreenOffAnimationPlaying;
    public boolean mLastShowing;
    public final LatencyTracker mLatencyTracker;
    public final NotificationMediaManager mMediaManager;
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
    public final Lazy mShadeController;
    public ShadeViewController mShadeViewController;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public TaskbarDelegate mTaskbarDelegate;
    public final boolean mUdfpsNewTouchDetectionEnabled;
    public final UdfpsOverlayInteractor mUdfpsOverlayInteractor;
    public final ViewMediatorCallback mViewMediatorCallback;
    public float mFraction = -1.0f;
    public boolean mTracking = false;
    public final C31381 mExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.1
        public boolean mPrimaryBouncerAnimating;

        @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onExpansionChanged(float f) {
            if (this.mPrimaryBouncerAnimating) {
                ScrimController scrimController = ((CentralSurfacesImpl) StatusBarKeyguardViewManager.this.mCentralSurfaces).mScrimController;
                if (scrimController.mBouncerHiddenFraction == f) {
                    return;
                }
                scrimController.mBouncerHiddenFraction = f;
                if (scrimController.mState == ScrimState.DREAMING) {
                    scrimController.applyAndDispatchState();
                }
            }
        }

        @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onFullyHidden() {
            this.mPrimaryBouncerAnimating = false;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onStartingToHide() {
            this.mPrimaryBouncerAnimating = true;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onStartingToShow() {
            this.mPrimaryBouncerAnimating = true;
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onVisibilityChanged(boolean z) {
            ViewRootImpl viewRootImpl;
            ViewRootImpl viewRootImpl2;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            statusBarKeyguardViewManager.mKeyguardUpdateManager.sendPrimaryBouncerVisibilityChanged(z);
            ((CentralSurfacesImpl) statusBarKeyguardViewManager.mCentralSurfaces).mBouncerShowingOverDream = z && statusBarKeyguardViewManager.mDreamOverlayStateController.isOverlayActive();
            if (!z) {
                ScrimController scrimController = ((CentralSurfacesImpl) statusBarKeyguardViewManager.mCentralSurfaces).mScrimController;
                if (scrimController.mBouncerHiddenFraction != 1.0f) {
                    scrimController.mBouncerHiddenFraction = 1.0f;
                    if (scrimController.mState == ScrimState.DREAMING) {
                        scrimController.applyAndDispatchState();
                    }
                }
            }
            C31392 c31392 = statusBarKeyguardViewManager.mOnBackInvokedCallback;
            if (z) {
                if (statusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl2 = statusBarKeyguardViewManager.getViewRootImpl()) == null) {
                    return;
                }
                viewRootImpl2.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(1000000, c31392);
                statusBarKeyguardViewManager.mIsBackCallbackRegistered = true;
                return;
            }
            if (!statusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl = statusBarKeyguardViewManager.getViewRootImpl()) == null) {
                return;
            }
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(c31392);
            statusBarKeyguardViewManager.mIsBackCallbackRegistered = false;
        }
    };
    public final C31392 mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.2
        @Override // android.window.OnBackAnimationCallback
        public final void onBackCancelled() {
            StatusBarKeyguardViewManager.this.needsFullscreenBouncer();
        }

        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            StatusBarKeyguardViewManager.this.onBackPressed();
            StatusBarKeyguardViewManager.this.needsFullscreenBouncer();
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackProgressed(BackEvent backEvent) {
            StatusBarKeyguardViewManager.this.needsFullscreenBouncer();
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackStarted(BackEvent backEvent) {
            StatusBarKeyguardViewManager.this.needsFullscreenBouncer();
        }
    };
    public boolean mIsBackCallbackRegistered = false;
    public final C31403 mDockEventListener = new DockManager.DockEventListener(this) { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.3
    };
    public boolean mGlobalActionsVisible = false;
    public boolean mLastGlobalActionsVisible = false;
    public boolean mFirstUpdate = true;
    public final Set mCallbacks = new HashSet();
    public final ArrayList mAfterKeyguardGoneRunnables = new ArrayList();
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.4
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEmergencyCallAction() {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            if (((KeyguardStateControllerImpl) statusBarKeyguardViewManager.mKeyguardStateController).mOccluded) {
                statusBarKeyguardViewManager.reset(true);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustGrantedForCurrentUser(boolean z, TrustGrantFlags trustGrantFlags, String str) {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            AlternateBouncerInteractor alternateBouncerInteractor = statusBarKeyguardViewManager.mAlternateBouncerInteractor;
            statusBarKeyguardViewManager.updateAlternateBouncerShowing(alternateBouncerInteractor.isVisibleState() ? alternateBouncerInteractor.hide() : false);
        }
    };
    public final RunnableC31428 mMakeNavigationBarVisibleRunnable = new RunnableC31428();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$8 */
    public final class RunnableC31428 implements Runnable {
        public RunnableC31428() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NavigationBarView navigationBarView = ((CentralSurfacesImpl) StatusBarKeyguardViewManager.this.mCentralSurfaces).getNavigationBarView();
            if (navigationBarView != null) {
                navigationBarView.setVisibility(0);
            }
            ((CentralSurfacesImpl) StatusBarKeyguardViewManager.this.mCentralSurfaces).mNotificationShadeWindowView.getWindowInsetsController().show(WindowInsets.Type.navigationBars());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$2] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$3] */
    public StatusBarKeyguardViewManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, NotificationMediaManager notificationMediaManager, KeyguardMessageAreaController.Factory factory, Optional<SysUIUnfoldComponent> optional, Lazy lazy, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, FeatureFlags featureFlags, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter) {
        this.mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mConfigurationController = configurationController;
        this.mNavigationModeController = navigationModeController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mMediaManager = notificationMediaManager;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDockManager = dockManager;
        this.mKeyguardMessageAreaFactory = factory;
        this.mShadeController = lazy;
        this.mLatencyTracker = latencyTracker;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mPrimaryBouncerView = bouncerView;
        this.mFoldAodAnimationController = (FoldAodAnimationController) optional.map(new SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda0()).orElse(null);
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mUdfpsNewTouchDetectionEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
        this.mUdfpsOverlayInteractor = udfpsOverlayInteractor;
        this.mActivityStarter = activityStarter;
    }

    public void addAfterKeyguardGoneRunnable(Runnable runnable) {
        this.mAfterKeyguardGoneRunnables.add(runnable);
    }

    public void blockPanelExpansionFromCurrentTouch() {
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeViewController;
        notificationPanelViewController.mBlockingExpansionForCurrentTouch = notificationPanelViewController.mTracking;
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

    public void dismissAndCollapse() {
        this.mActivityStarter.executeRunnableDismissingKeyguard(null, null, true, false, true);
    }

    public void dismissWithAction(LockscreenShadeTransitionController$goToLockedShadeInternal$1 lockscreenShadeTransitionController$goToLockedShadeInternal$1, RunnableC2570x13216739 runnableC2570x13216739) {
        dismissWithAction(lockscreenShadeTransitionController$goToLockedShadeInternal$1, runnableC2570x13216739, false, null);
    }

    public boolean dispatchBackKeyEventPreIme() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        if (bouncerViewImpl.getDelegate() != null) {
            return ((KeyguardBouncerViewBinder$bind$delegate$1) bouncerViewImpl.getDelegate()).$securityContainerController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password;
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (shouldInterceptTouchEvent() && !this.mUdfpsOverlayInteractor.isTouchWithinUdfpsArea(motionEvent)) {
            onTouch(motionEvent);
        }
        return shouldInterceptTouchEvent();
    }

    public void dump(PrintWriter printWriter) {
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "StatusBarKeyguardViewManager:", "  mRemoteInputActive: "), this.mRemoteInputActive, printWriter, "  mDozing: "), this.mDozing, printWriter, "  mAfterKeyguardGoneAction: ");
        m64m.append(this.mAfterKeyguardGoneAction);
        printWriter.println(m64m.toString());
        printWriter.println("  mAfterKeyguardGoneRunnables: " + this.mAfterKeyguardGoneRunnables);
        printWriter.println("  mPendingWakeupAction: " + this.mPendingWakeupAction);
        printWriter.println("  isBouncerShowing(): " + isBouncerShowing());
        printWriter.println("  bouncerIsOrWillBeShowing(): " + primaryBouncerIsOrWillBeShowing());
        printWriter.println("  Registered KeyguardViewManagerCallbacks:");
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            printWriter.println("      " + ((C1079xbb37c88d) it.next()));
        }
        if (this.mOccludingAppBiometricUI != null) {
            printWriter.println("mOccludingAppBiometricUI:");
            this.mOccludingAppBiometricUI.this$0.getClass();
            printWriter.println("UdfpsKeyguardViewController");
        }
    }

    public final void executeAfterKeyguardGoneAction() {
        Object[] objArr = new Object[2];
        ActivityStarter.OnDismissAction onDismissAction = this.mAfterKeyguardGoneAction;
        Map map = LogUtil.beginTimes;
        objArr[0] = Integer.valueOf(onDismissAction != null ? 1 : 0);
        ArrayList arrayList = this.mAfterKeyguardGoneRunnables;
        objArr[1] = Integer.valueOf(arrayList.size());
        LogUtil.m223d("KeyguardUnlockInfo", "executeAfterKeyguardGoneAction dismissAction=%d, goneRunnable=%d", objArr);
        ActivityStarter.OnDismissAction onDismissAction2 = this.mAfterKeyguardGoneAction;
        if (onDismissAction2 != null) {
            onDismissAction2.onDismiss();
            this.mAfterKeyguardGoneAction = null;
        }
        this.mKeyguardGoneCancelAction = null;
        this.mDismissActionWillAnimateOnKeyguard = false;
        for (int i = 0; i < arrayList.size(); i++) {
            ((Runnable) arrayList.get(i)).run();
        }
        arrayList.clear();
    }

    public boolean getLastNavBarVisible() {
        boolean z = this.mLastShowing && !this.mLastOccluded;
        boolean z2 = this.mLastDozing;
        return !(z || (z2 && this.mLastBiometricMode != 2) || this.mLastScreenOffAnimationPlaying) || this.mLastPrimaryBouncerShowing || this.mLastRemoteInputActive || (((z && !z2 && !this.mLastScreenOffAnimationPlaying) || (this.mLastPulsing && !this.mLastIsDocked)) && this.mLastGesturalNav) || this.mLastGlobalActionsVisible;
    }

    public long getNavBarShowDelay() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway ? ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDelay : isBouncerShowing() ? 320L : 0L;
    }

    public float getQsExpansion() {
        return this.mQsExpansion;
    }

    public ViewRootImpl getViewRootImpl() {
        ViewGroup viewGroup = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mNotificationShadeView;
        if (viewGroup != null) {
            return viewGroup.getViewRootImpl();
        }
        return null;
    }

    public void hide(long j, long j2) {
        Trace.beginSection("StatusBarKeyguardViewManager#hide");
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(false, keyguardStateControllerImpl.mOccluded);
        launchPendingWakeupAction();
        long j3 = this.mKeyguardUpdateManager.mNeedsSlowUnlockTransition ? 2000L : j2;
        long max = Math.max(0L, (j - 48) - SystemClock.uptimeMillis());
        executeAfterKeyguardGoneAction();
        boolean isEnabled = ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isEnabled();
        if (isEnabled) {
            j3 = 0;
            max = 0;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        centralSurfacesImpl.mCommandQueue.appTransitionStarting(centralSurfacesImpl.mDisplayId, (j + j3) - 120, 120L, true);
        centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, j3 > 0);
        centralSurfacesImpl.mCommandQueue.appTransitionStarting(centralSurfacesImpl.mDisplayId, j - 120, 120L, true);
        KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDelay = max;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDuration = j3;
        keyguardStateControllerImpl2.setKeyguardFadingAway(true);
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
        if (z || isEnabled || keyguardStateControllerImpl.mOccluded) {
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            ((CentralSurfacesImpl) this.mCentralSurfaces).finishKeyguardFadingAway();
            this.mBiometricUnlockController.finishKeyguardFadingAway();
        } else {
            ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).setKeyguardFadingAway(true);
            if (this.mBiometricUnlockController.isWakeAndUnlock()) {
                LatencyTracker latencyTracker = this.mLatencyTracker;
                if (latencyTracker.isEnabled()) {
                    latencyTracker.onActionEnd(this.mBiometricUnlockController.mBiometricType == BiometricSourceType.FACE ? 7 : 2);
                }
            }
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
        }
        updateStates();
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardShowing = false;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        this.mViewMediatorCallback.keyguardGone();
        SysUiStatsLog.write(62, 1);
        Trace.endSection();
    }

    public void hideAlternateBouncer(boolean z) {
        updateAlternateBouncerShowing(this.mAlternateBouncerInteractor.hide() && z);
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
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((KeyguardBouncerViewBinder$bind$delegate$1) ((BouncerViewImpl) bouncerView).getDelegate()).$securityContainerController.interceptMediaKey(keyEvent);
    }

    public boolean isBouncerShowing() {
        return primaryBouncerIsShowing() || this.mAlternateBouncerInteractor.isVisibleState();
    }

    public boolean isFullscreenBouncer() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && SecurityUtils.checkFullscreenBouncer(((KeyguardBouncerViewBinder$bind$delegate$1) bouncerViewImpl.getDelegate()).$securityContainerController.mCurrentSecurityMode);
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
        return this.mKeyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()) != KeyguardSecurityModel.SecurityMode.None;
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
        KeyguardSecurityModel.SecurityMode securityMode = this.mKeyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        return securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
    }

    public void notifyKeyguardAuthenticated(boolean z) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardAuthenticated.setValue(Boolean.valueOf(z));
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            hideAlternateBouncer(false);
            executeAfterKeyguardGoneAction();
        }
    }

    public void onBackPressed() {
        if (canHandleBackPressed()) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            centralSurfacesImpl.releaseGestureWakeLock();
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
            if (primaryBouncerIsScrimmed() && !needsFullscreenBouncer()) {
                hideBouncer(false);
                updateStates();
                return;
            }
            CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) this.mCentralSurfaces;
            boolean z = true;
            boolean z2 = centralSurfacesImpl2.mScrimController.mState == ScrimState.BOUNCER_SCRIMMED;
            boolean z3 = centralSurfacesImpl2.mBouncerShowingOverDream;
            if (!z2 && !z3) {
                z = false;
            }
            reset(z);
            if (z) {
                ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide = false;
            } else {
                ((NotificationPanelViewController) this.mShadeViewController).expandToNotifications();
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

    public void onKeyguardFadedAway() {
        this.mNotificationContainer.postDelayed(new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 3), 100L);
        ((NotificationPanelViewController) this.mShadeViewController).resetViewGroupFade();
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
        boolean z2 = this.mDreamOverlayStateController.isOverlayActive() && (((NotificationPanelViewController) this.mShadeViewController).isExpanded() || ((NotificationPanelViewController) this.mShadeViewController).isExpandingOrCollapsing());
        boolean z3 = f2 != 1.0f && z;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing && !primaryBouncerIsOrWillBeShowing() && !keyguardStateControllerImpl.mKeyguardGoingAway && z3 && !z2 && !keyguardStateControllerImpl.mOccluded && !keyguardStateControllerImpl.mCanDismissLockScreen && !bouncerIsAnimatingAway() && !((NotificationPanelViewController) this.mShadeViewController).mHintAnimationRunning) {
            int i = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState;
        }
        if (primaryBouncerIsOrWillBeShowing() && !keyguardStateControllerImpl.mShowing) {
            this.mPrimaryBouncerInteractor.setPanelExpansion(1.0f);
        }
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public void onRemoteInputActive(boolean z) {
        this.mRemoteInputActive = z;
        updateStates();
    }

    public void onStartedGoingToSleep() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.getWindowInsetsController().setAnimationsDisabled(true);
        NavigationBarView navigationBarView = ((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView();
        if (navigationBarView != null) {
            StatusBarKeyguardViewManager$$ExternalSyntheticLambda1 statusBarKeyguardViewManager$$ExternalSyntheticLambda1 = new StatusBarKeyguardViewManager$$ExternalSyntheticLambda1(0);
            View view = navigationBarView.mVertical;
            if (view != null) {
                statusBarKeyguardViewManager$$ExternalSyntheticLambda1.accept(view);
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                statusBarKeyguardViewManager$$ExternalSyntheticLambda1.accept(view2);
            }
        }
    }

    public void onStartedWakingUp() {
        ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.getWindowInsetsController().setAnimationsDisabled(false);
        NavigationBarView navigationBarView = ((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView();
        if (navigationBarView != null) {
            StatusBarKeyguardViewManager$$ExternalSyntheticLambda1 statusBarKeyguardViewManager$$ExternalSyntheticLambda1 = new StatusBarKeyguardViewManager$$ExternalSyntheticLambda1(1);
            View view = navigationBarView.mVertical;
            if (view != null) {
                statusBarKeyguardViewManager$$ExternalSyntheticLambda1.accept(view);
            }
            View view2 = navigationBarView.mHorizontal;
            if (view2 != null) {
                statusBarKeyguardViewManager$$ExternalSyntheticLambda1.accept(view2);
            }
        }
    }

    public void onThemeChanged() {
        updateResources();
    }

    public boolean onTouch(MotionEvent motionEvent) {
        UdfpsController udfpsController;
        UdfpsControllerOverlay udfpsControllerOverlay;
        boolean shouldInterceptTouchEvent = shouldInterceptTouchEvent();
        if (shouldInterceptTouchEvent) {
            boolean z = motionEvent.getActionMasked() == 0;
            AlternateBouncerInteractor alternateBouncerInteractor = this.mAlternateBouncerInteractor;
            boolean z2 = alternateBouncerInteractor.receivedDownTouch && motionEvent.getActionMasked() == 1;
            boolean z3 = motionEvent.getActionMasked() == 4 && !(this.mUdfpsNewTouchDetectionEnabled && this.mKeyguardUpdateManager.isUdfpsEnrolled());
            if (z) {
                alternateBouncerInteractor.receivedDownTouch = true;
            } else if (z2 || z3) {
                ((SystemClockImpl) alternateBouncerInteractor.systemClock).getClass();
                if (SystemClock.uptimeMillis() - ((KeyguardBouncerRepositoryImpl) alternateBouncerInteractor.bouncerRepository).lastAlternateBouncerVisibleTime > 200) {
                    showPrimaryBouncer(true);
                }
            }
        }
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = ((C1079xbb37c88d) it.next()).this$0;
            if ((udfpsKeyguardViewControllerLegacy.transitionToFullShadeProgress == 0.0f) && !udfpsKeyguardViewControllerLegacy.useExpandedOverlay && (udfpsControllerOverlay = (udfpsController = udfpsKeyguardViewControllerLegacy.udfpsController).mOverlay) != null) {
                if (!(udfpsControllerOverlay.overlayView == null)) {
                    udfpsController.onTouch(udfpsControllerOverlay.requestId, motionEvent, false);
                }
            }
        }
        return shouldInterceptTouchEvent;
    }

    public boolean primaryBouncerIsOrWillBeShowing() {
        return isBouncerShowing() || isPrimaryBouncerInTransit();
    }

    public boolean primaryBouncerIsScrimmed() {
        return ((Boolean) ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository).primaryBouncerScrimmed.getValue()).booleanValue();
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

    public void requestFace(boolean z) {
        this.mKeyguardUpdateManager.requestFaceAuthOnOccludingApp(z);
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
        if (keyguardStateControllerImpl.mShowing) {
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            ((NotificationPanelViewController) this.mShadeViewController).resetViews(!z2);
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
    }

    public void setGlobalActionsVisible(boolean z) {
        this.mGlobalActionsVisible = z;
        updateStates();
    }

    public void setKeyguardGoingAwayState(boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(secNotificationShadeWindowControllerHelperImpl, 1);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        notificationShadeWindowControllerImpl$$ExternalSyntheticLambda0.accept(Boolean.valueOf(z));
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardGoingAway = z;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
    }

    public void setKeyguardMessage(String str, ColorStateList colorStateList) {
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            KeyguardMessageAreaController keyguardMessageAreaController = this.mKeyguardMessageAreaController;
            if (keyguardMessageAreaController != null) {
                keyguardMessageAreaController.setMessage(str, true);
                return;
            }
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
        boolean z4 = !z3 && z;
        boolean z5 = z3 && !z;
        keyguardStateControllerImpl.notifyKeyguardState(keyguardStateControllerImpl.mShowing, z);
        updateStates();
        boolean z6 = keyguardStateControllerImpl.mShowing;
        final boolean z7 = keyguardStateControllerImpl.mOccluded;
        if (z6 && z4) {
            SysUiStatsLog.write(62, 3);
            if (((CentralSurfacesImpl) this.mCentralSurfaces).mIsLaunchingActivityOverLockscreen) {
                ((ShadeControllerImpl) ((ShadeController) this.mShadeController.get())).mPostCollapseRunnables.add(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$$ExternalSyntheticLambda2
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
                });
                return;
            }
        } else if (z6 && z5) {
            SysUiStatsLog.write(62, 2);
        }
        if (z6) {
            this.mMediaManager.updateMediaMetaData(false, z2 && !z7);
        }
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardOccluded = z7;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        postSetOccluded(z4);
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
            C1079xbb37c88d c1079xbb37c88d = (C1079xbb37c88d) it.next();
            float f2 = this.mQsExpansion;
            UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = c1079xbb37c88d.this$0;
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
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((KeyguardBouncerViewBinder$bind$delegate$1) ((BouncerViewImpl) bouncerView).getDelegate()).shouldDismissOnMenuPressed();
    }

    public boolean shouldInterceptTouchEvent() {
        return this.mAlternateBouncerInteractor.isVisibleState();
    }

    public void show(Bundle bundle) {
        Trace.beginSection("StatusBarKeyguardViewManager#show");
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardShowing = true;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(true, keyguardStateControllerImpl.mOccluded);
        reset(true);
        SysUiStatsLog.write(62, 2);
        Trace.endSection();
    }

    public void showBouncer() {
        AlternateBouncerInteractor alternateBouncerInteractor = this.mAlternateBouncerInteractor;
        ((KeyguardBouncerRepositoryImpl) alternateBouncerInteractor.bouncerRepository).setAlternateVisible();
        if (alternateBouncerInteractor.isVisibleState()) {
            updateAlternateBouncerShowing(alternateBouncerInteractor.isVisibleState());
        } else {
            showPrimaryBouncer(true);
        }
    }

    public void showBouncerOrKeyguard(boolean z) {
        if (!needsFullscreenBouncer() || this.mDozing) {
            ((CentralSurfacesImpl) this.mCentralSurfaces).showKeyguard();
            if (z) {
                hideBouncer(false);
            }
        } else {
            ((CentralSurfacesImpl) this.mCentralSurfaces).hideKeyguard();
            this.mPrimaryBouncerInteractor.show(true);
        }
        updateStates();
    }

    public void showPrimaryBouncer(boolean z) {
        hideAlternateBouncer(false);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && !isBouncerShowing()) {
            this.mPrimaryBouncerInteractor.show(z);
        }
        updateStates();
    }

    public void startPreHideAnimation(Runnable runnable) {
        if (primaryBouncerIsShowing()) {
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (primaryBouncerInteractor.willRunDismissFromKeyguard()) {
                runnable.run();
            } else {
                ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._primaryBouncerDisappearAnimation.setValue(runnable);
            }
            if (!LsRune.LOCKUI_MULTI_USER) {
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeViewController;
                KeyguardQsUserSwitchController keyguardQsUserSwitchController = notificationPanelViewController.mKeyguardQsUserSwitchController;
                if (keyguardQsUserSwitchController != null) {
                    int i = notificationPanelViewController.mBarState;
                    keyguardQsUserSwitchController.mKeyguardVisibilityHelper.setViewVisibility(i, i, true, false);
                }
                KeyguardUserSwitcherController keyguardUserSwitcherController = notificationPanelViewController.mKeyguardUserSwitcherController;
                if (keyguardUserSwitcherController != null) {
                    int i2 = notificationPanelViewController.mBarState;
                    keyguardUserSwitcherController.mKeyguardVisibilityHelper.setViewVisibility(i2, i2, true, false);
                }
            }
            if (this.mDismissActionWillAnimateOnKeyguard) {
                updateStates();
            }
        } else if (runnable != null) {
            runnable.run();
        }
        NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) this.mShadeViewController;
        notificationPanelViewController2.mBlockingExpansionForCurrentTouch = notificationPanelViewController2.mTracking;
    }

    public final void updateAlternateBouncerShowing(boolean z) {
        if (this.mCentralSurfacesRegistered) {
            boolean isVisibleState = this.mAlternateBouncerInteractor.isVisibleState();
            KeyguardMessageAreaController keyguardMessageAreaController = this.mKeyguardMessageAreaController;
            if (keyguardMessageAreaController != null) {
                keyguardMessageAreaController.setIsVisible(isVisibleState);
                this.mKeyguardMessageAreaController.setMessage("");
            }
            this.mBypassController.altBouncerShowing = isVisibleState;
            this.mKeyguardUpdateManager.setAlternateBouncerShowing(isVisibleState);
            if (z) {
                ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
            }
        }
    }

    public void updateKeyguardPosition(float f) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardPosition.setValue(Float.valueOf(f));
    }

    public void updateNavigationBarVisibility(boolean z) {
        TaskbarDelegate taskbarDelegate;
        if (((CentralSurfacesImpl) this.mCentralSurfaces).getNavigationBarView() != null || ((taskbarDelegate = this.mTaskbarDelegate) != null && taskbarDelegate.mInitialized)) {
            RunnableC31428 runnableC31428 = this.mMakeNavigationBarVisibleRunnable;
            if (!z) {
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.removeCallbacks(runnableC31428);
                } else {
                    this.mNotificationContainer.removeCallbacks(runnableC31428);
                }
                ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.getWindowInsetsController().hide(WindowInsets.Type.navigationBars());
                return;
            }
            long navBarShowDelay = getNavBarShowDelay();
            if (navBarShowDelay == 0) {
                runnableC31428.run();
            } else if (LsRune.SECURITY_BOUNCER_WINDOW) {
                ((CentralSurfacesImpl) this.mCentralSurfaces).mNotificationShadeWindowView.postOnAnimationDelayed(runnableC31428, navBarShowDelay);
            } else {
                this.mNotificationContainer.postOnAnimationDelayed(runnableC31428, navBarShowDelay);
            }
        }
    }

    public void updateResources() {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._resourceUpdateRequests.setValue(Boolean.TRUE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v6 */
    public void updateStates() {
        if (this.mCentralSurfacesRegistered) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            boolean z = keyguardStateControllerImpl.mShowing;
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            int i = 1;
            ?? r2 = (!primaryBouncerIsShowing() || (LsRune.SECURITY_BOUNCER_WINDOW && !z)) ? 0 : 1;
            boolean primaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing();
            boolean z3 = !isFullscreenBouncer();
            boolean z4 = this.mRemoteInputActive;
            Rune.runIf((Runnable) new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, r4), true);
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
            sendKeyguardViewState(z, z2, r2);
            r4 = r2 != this.mLastPrimaryBouncerShowing ? 1 : 0;
            this.mLastPrimaryBouncerShowing = r2;
            if (r4 != 0 || this.mFirstUpdate) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.bouncerShowing = r2;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
                centralSurfacesImpl.mBouncerShowing = r2;
                centralSurfacesImpl.mKeyguardBypassController.bouncerShowing = r2;
                centralSurfacesImpl.mPulseExpansionHandler.bouncerShowing = r2;
                centralSurfacesImpl.setBouncerShowingForStatusBarComponents(r2);
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = centralSurfacesImpl.mStatusBarHideIconsForBouncerManager;
                statusBarHideIconsForBouncerManager.bouncerShowing = r2;
                statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(true);
                centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, true);
                if (centralSurfacesImpl.mBouncerShowing) {
                    centralSurfacesImpl.wakeUpIfDozing(SystemClock.uptimeMillis(), "BOUNCER_VISIBLE", 4);
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
                            secQpBlurController.mIsBouncerShowing = r2;
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setBouncerShowing: ", r2, "SecQpBlurController");
                            boolean z6 = secQpBlurController.mPanelExpandedFraction != 0.0f;
                            secQpBlurController.mCapturedBlurController.mIsBouncerShowing = secQpBlurController.mIsBouncerShowing;
                            if (r2 != 0) {
                                secQpBlurController.doCaptureContainerAlpha(1.0f, secQpBlurController.mKeyguardUpdateMonitor.mKeyguardOccluded ? CapturedBlurContainerController.BlurType.QUICK_PANEL : CapturedBlurContainerController.BlurType.BOUNCER);
                            } else if (r2 == 0) {
                                if ((secQpBlurController.mStatusBarStateController.getState() == 1) && !z6) {
                                    secQpBlurController.doCaptureContainerAlpha(0.0f, CapturedBlurContainerController.BlurType.BOUNCER);
                                }
                            }
                        }
                    }
                    NotificationShadeWindowView notificationShadeWindowView = centralSurfacesImpl.mNotificationShadeWindowView;
                    if (notificationShadeWindowView.mBouncerShowing != r2) {
                        notificationShadeWindowView.mBouncerShowing = r2;
                        notificationShadeWindowView.applyBouncerMargins();
                    }
                }
                if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
                    secQpBlurController.mIsBouncerShowing = r2;
                    secQpBlurController.mAnimatedFraction = 0.0f;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setBouncerWindowShowing: ", r2, "SecQpBlurController");
                    if (r2 != 0) {
                        float f = secQpBlurController.mPanelExpandedFraction;
                        if (f != 0.0f) {
                            secQpBlurController.makeAnimationAndRun(f, 0.0f, 350);
                        }
                    }
                    if (r2 == 0) {
                        if ((secQpBlurController.mStatusBarStateController.getState() == 0) && secQpBlurController.mPanelExpandedFraction == 1.0f) {
                            secQpBlurController.makeAnimationAndRun(0.0f, 1.0f, 350);
                        }
                    }
                }
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    updateBouncerNavigationBar(WallpaperUtils.isWhiteKeyguardWallpaper("background"));
                }
            }
            if (primaryBouncerIsOrWillBeShowing != this.mLastPrimaryBouncerIsOrWillBeShowing || this.mFirstUpdate || r4 != 0) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
                keyguardUpdateMonitor.mLogger.logSendPrimaryBouncerChanged(primaryBouncerIsOrWillBeShowing, r2);
                Message obtainMessage = keyguardUpdateMonitor.mHandler.obtainMessage(322);
                obtainMessage.arg1 = primaryBouncerIsOrWillBeShowing ? 1 : 0;
                obtainMessage.arg2 = r2;
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
            Rune.runIf((Runnable) new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, i), true);
            Rune.runIf(new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 2), LsRune.COVER_SUPPORTED);
            ((CentralSurfacesImpl) this.mCentralSurfaces).logStateToEventlog();
        }
    }

    public void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            try {
                Trace.beginSection("StatusBarKeyguardViewManager#dismissWithAction");
                cancelPendingWakeupAction();
                if (this.mDozing && !isWakeAndUnlocking()) {
                    this.mPendingWakeupAction = new DismissWithActionRequest(onDismissAction, runnable, z, str);
                    return;
                }
                this.mAfterKeyguardGoneAction = onDismissAction;
                this.mKeyguardGoneCancelAction = runnable;
                this.mDismissActionWillAnimateOnKeyguard = onDismissAction != null && onDismissAction.willRunAnimationOnKeyguard();
                this.mAlternateBouncerInteractor.getClass();
                this.mViewMediatorCallback.setCustomMessage(str);
                PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
                if (z) {
                    primaryBouncerInteractor.show(true);
                } else {
                    primaryBouncerInteractor.setDismissAction(this.mAfterKeyguardGoneAction, this.mKeyguardGoneCancelAction);
                    primaryBouncerInteractor.show(true);
                    this.mAfterKeyguardGoneAction = null;
                    this.mKeyguardGoneCancelAction = null;
                }
            } finally {
                Trace.endSection();
            }
        }
        updateStates();
    }

    public void onCancelClicked() {
    }

    public void shouldSubtleWindowAnimationsForUnlock() {
    }
}
