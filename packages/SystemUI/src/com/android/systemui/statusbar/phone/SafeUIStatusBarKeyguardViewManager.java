package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.FrameLayout;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.Rune;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
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
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.Lazy;
import java.io.PrintWriter;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SafeUIStatusBarKeyguardViewManager extends StatusBarKeyguardViewManager {
    public final ActivityStarter mActivityStarter;
    public ActivityStarter.OnDismissAction mAfterKeyguardGoneAction;
    public final ArrayList mAfterKeyguardGoneRunnables;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public BiometricUnlockController mBiometricUnlockController;
    public final Set mCallbacks;
    public CentralSurfaces mCentralSurfaces;
    public boolean mCentralSurfacesRegistered;
    public final ConfigurationController mConfigurationController;
    public boolean mDismissActionWillAnimateOnKeyguard;
    public final AnonymousClass3 mDockEventListener;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final AnonymousClass1 mExpansionCallback;
    public boolean mFirstUpdate;
    public final FoldAodAnimationController mFoldAodAnimationController;
    public float mFraction;
    public boolean mGesturalNav;
    public boolean mGlobalActionsVisible;
    public final boolean mIsBackAnimationEnabled;
    public boolean mIsBackCallbackRegistered;
    public final JavaAdapter mJavaAdapter;
    public Runnable mKeyguardGoneCancelAction;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
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
    public final Lazy mLegacyBouncerDependencies;
    public Job mListenForCanShowAlternateBouncer;
    public final NavigationModeController mNavigationModeController;
    public View mNotificationContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final AnonymousClass2 mOnBackInvokedCallback;
    public DismissWithActionRequest mPendingWakeupAction;
    public final SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 mPrepareBouncerRunnable;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final BouncerView mPrimaryBouncerView;
    public boolean mPulsing;
    public boolean mRemoteInputActive;
    public final ViewGroup mSafeUIBouncerContainer;
    public boolean mScreenOffAnimationPlaying;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final Lazy mShadeController;
    public ShadeLockscreenInteractor mShadeLockscreenInteractor;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mTracking;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public static boolean m2232$$Nest$mshouldPlayBackAnimation(SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager) {
        return !safeUIStatusBarKeyguardViewManager.needsFullscreenBouncer() && safeUIStatusBarKeyguardViewManager.mIsBackAnimationEnabled;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager$2] */
    public SafeUIStatusBarKeyguardViewManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, KeyguardMessageAreaController.Factory factory, Optional<SysUIUnfoldComponent> optional, Lazy lazy, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, Lazy lazy2, Lazy lazy3, SelectedUserInteractor selectedUserInteractor, JavaAdapter javaAdapter, Lazy lazy4, StatusBarKeyguardViewManagerInteractor statusBarKeyguardViewManagerInteractor, Lazy lazy5) {
        super(context, viewMediatorCallback, lockPatternUtils, sysuiStatusBarStateController, configurationController, keyguardUpdateMonitor, dreamOverlayStateController, navigationModeController, dockManager, notificationShadeWindowController, keyguardStateController, factory, optional, lazy, latencyTracker, keyguardSecurityModel, primaryBouncerCallbackInteractor, primaryBouncerInteractor, bouncerView, alternateBouncerInteractor, udfpsOverlayInteractor, activityStarter, keyguardTransitionInteractor, coroutineDispatcher, lazy2, lazy3, selectedUserInteractor, javaAdapter, lazy4, statusBarKeyguardViewManagerInteractor);
        this.mListenForCanShowAlternateBouncer = null;
        this.mFraction = -1.0f;
        this.mTracking = false;
        this.mExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager.1
            public boolean mPrimaryBouncerAnimating;

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onExpansionChanged(float f) {
                if (this.mPrimaryBouncerAnimating) {
                    ((CentralSurfacesImpl) SafeUIStatusBarKeyguardViewManager.this.mCentralSurfaces).setPrimaryBouncerHiddenFraction(f);
                }
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onFullyHidden() {
                this.mPrimaryBouncerAnimating = false;
                SafeUIStatusBarKeyguardViewManager.this.updateStates();
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToHide() {
                this.mPrimaryBouncerAnimating = true;
                SafeUIStatusBarKeyguardViewManager.this.updateStates();
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onStartingToShow() {
                this.mPrimaryBouncerAnimating = true;
                SafeUIStatusBarKeyguardViewManager.this.updateStates();
            }

            @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
            public final void onVisibilityChanged(boolean z) {
                ViewRootImpl viewRootImpl;
                ViewRootImpl viewRootImpl2;
                SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = SafeUIStatusBarKeyguardViewManager.this;
                if (z) {
                    if (safeUIStatusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl2 = safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer.getViewRootImpl()) == null) {
                        return;
                    }
                    viewRootImpl2.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, safeUIStatusBarKeyguardViewManager.mOnBackInvokedCallback);
                    safeUIStatusBarKeyguardViewManager.mIsBackCallbackRegistered = true;
                    return;
                }
                if (!safeUIStatusBarKeyguardViewManager.mIsBackCallbackRegistered || (viewRootImpl = safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer.getViewRootImpl()) == null) {
                    return;
                }
                viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(safeUIStatusBarKeyguardViewManager.mOnBackInvokedCallback);
                safeUIStatusBarKeyguardViewManager.mIsBackCallbackRegistered = false;
            }
        };
        this.mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager.2
            @Override // android.window.OnBackAnimationCallback
            public final void onBackCancelled() {
                if (!SafeUIStatusBarKeyguardViewManager.m2232$$Nest$mshouldPlayBackAnimation(SafeUIStatusBarKeyguardViewManager.this) || ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                    return;
                }
                ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackCancelled();
            }

            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                SafeUIStatusBarKeyguardViewManager.this.onBackPressed();
                if (!SafeUIStatusBarKeyguardViewManager.m2232$$Nest$mshouldPlayBackAnimation(SafeUIStatusBarKeyguardViewManager.this) || ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                    return;
                }
                ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback();
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackProgressed(BackEvent backEvent) {
                if (!SafeUIStatusBarKeyguardViewManager.m2232$$Nest$mshouldPlayBackAnimation(SafeUIStatusBarKeyguardViewManager.this) || ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                    return;
                }
                ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackProgressed(backEvent);
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackStarted(BackEvent backEvent) {
                if (!SafeUIStatusBarKeyguardViewManager.m2232$$Nest$mshouldPlayBackAnimation(SafeUIStatusBarKeyguardViewManager.this) || ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                    return;
                }
                ((BouncerViewImpl) SafeUIStatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback().onBackStarted(backEvent);
            }
        };
        this.mIsBackCallbackRegistered = false;
        new DockManager.DockEventListener(this) { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager.3
        };
        this.mGlobalActionsVisible = false;
        this.mLastGlobalActionsVisible = false;
        this.mFirstUpdate = true;
        this.mCallbacks = new HashSet();
        this.mAfterKeyguardGoneRunnables = new ArrayList();
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager.4
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onEmergencyCallAction() {
                SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = SafeUIStatusBarKeyguardViewManager.this;
                if (((KeyguardStateControllerImpl) safeUIStatusBarKeyguardViewManager.mKeyguardStateController).mOccluded) {
                    safeUIStatusBarKeyguardViewManager.reset(true);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
                SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = SafeUIStatusBarKeyguardViewManager.this;
                AlternateBouncerInteractor alternateBouncerInteractor2 = safeUIStatusBarKeyguardViewManager.mAlternateBouncerInteractor;
                safeUIStatusBarKeyguardViewManager.updateAlternateBouncerShowing(alternateBouncerInteractor2.isVisibleState() ? alternateBouncerInteractor2.hide() : false);
            }
        };
        new Runnable() { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager.8
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarView navigationBarView = ((CentralSurfacesImpl) SafeUIStatusBarKeyguardViewManager.this.mCentralSurfaces).getNavigationBarView();
                if (navigationBarView != null) {
                    navigationBarView.setVisibility(0);
                }
                ((NotificationShadeWindowControllerImpl) SafeUIStatusBarKeyguardViewManager.this.mNotificationShadeWindowController).mWindowRootView.getWindowInsetsController().show(WindowInsets.Type.navigationBars());
            }
        };
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mConfigurationController = configurationController;
        this.mNavigationModeController = navigationModeController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mShadeController = lazy;
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
        this.mSafeUIBouncerContainer = new FrameLayout(context);
        this.mLegacyBouncerDependencies = lazy5;
        this.mPrepareBouncerRunnable = new SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1(this);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void addAfterKeyguardGoneRunnable(Runnable runnable) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        this.mAfterKeyguardGoneRunnables.add(runnable);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean bouncerIsAnimatingAway() {
        return this.mPrimaryBouncerInteractor.isAnimatingAway();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean canHandleBackPressed() {
        return this.mPrimaryBouncerInteractor.isFullyShowing();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void cancelPendingWakeupAction() {
        Runnable runnable;
        DismissWithActionRequest dismissWithActionRequest = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (dismissWithActionRequest == null || (runnable = dismissWithActionRequest.cancelAction) == null) {
            return;
        }
        runnable.run();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void cancelPostAuthActions() {
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

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public void consumeFromAlternateBouncerTransitionSteps(TransitionStep transitionStep) {
        hideAlternateBouncer(false);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public void consumeKeyguardAuthenticatedBiometricsHandled(Unit unit) {
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            hideAlternateBouncer(false);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
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
        }
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean dispatchBackKeyEventPreIme() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && bouncerViewImpl.getDelegate().$securityContainerController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        shouldInterceptTouchEvent();
        shouldInterceptTouchEvent();
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void dump(PrintWriter printWriter) {
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
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            printWriter.println("      null");
        }
    }

    public final void executeAfterKeyguardGoneAction() {
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
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

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean getLastNavBarVisible() {
        boolean z = this.mLastShowing && !this.mLastOccluded;
        boolean z2 = this.mLastDozing;
        return !(z || z2 || this.mLastScreenOffAnimationPlaying) || this.mLastPrimaryBouncerShowing || this.mLastRemoteInputActive || (((z && !z2 && !this.mLastScreenOffAnimationPlaying) || (this.mLastPulsing && !this.mLastIsDocked)) && this.mLastGesturalNav) || this.mLastGlobalActionsVisible;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean getLastPrimaryBouncerShowing() {
        return this.mLastPrimaryBouncerShowing;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final long getNavBarShowDelay() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAway ? ((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDelay : isBouncerShowing() ? 320L : 0L;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final ViewRootImpl getViewRootImpl() {
        return this.mSafeUIBouncerContainer.getViewRootImpl();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void hide(long j, long j2) {
        Trace.beginSection("StatusBarKeyguardViewManager#hide");
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).notifyKeyguardState(false, ((KeyguardStateControllerImpl) keyguardStateController).mOccluded);
        launchPendingWakeupAction();
        boolean z = this.mKeyguardUpdateManager.mNeedsSlowUnlockTransition;
        Math.max(0L, (j - 48) - SystemClock.uptimeMillis());
        executeAfterKeyguardGoneAction();
        hideBouncer(true);
        updateStates();
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setKeyguardShowing(false);
        this.mViewMediatorCallback.keyguardGone();
        SysUiStatsLog.write(62, 1);
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void hideAlternateBouncer(boolean z) {
        updateAlternateBouncerShowing(this.mAlternateBouncerInteractor.hide() && z);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public void hideBouncer(boolean z) {
        this.mPrimaryBouncerInteractor.hide();
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            cancelPostAuthActions();
        }
        cancelPendingWakeupAction();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean interceptMediaKey(KeyEvent keyEvent) {
        BouncerView bouncerView = this.mPrimaryBouncerView;
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((BouncerViewImpl) bouncerView).getDelegate().$securityContainerController.interceptMediaKey(keyEvent);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final boolean isBouncerShowing() {
        return this.mPrimaryBouncerInteractor.isFullyShowing() || this.mAlternateBouncerInteractor.isVisibleState();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isBouncerShowingOverDream() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isFullscreenBouncer() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && SecurityUtils.checkFullscreenBouncer(bouncerViewImpl.getDelegate().$securityContainerController.mCurrentSecurityMode);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final boolean isGoingToNotificationShade() {
        return ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isNavBarVisible() {
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        boolean z = biometricUnlockController != null && biometricUnlockController.mMode == 2;
        boolean isVisible = this.mKeyguardStateController.isVisible();
        boolean z2 = this.mDozing;
        return !(isVisible || (z2 && !z) || this.mScreenOffAnimationPlaying) || this.mPrimaryBouncerInteractor.isFullyShowing() || this.mRemoteInputActive || (((isVisible && !z2 && !this.mScreenOffAnimationPlaying) || this.mPulsing) && this.mGesturalNav) || this.mGlobalActionsVisible;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isPrimaryBouncerInTransit() {
        return this.mPrimaryBouncerInteractor.isInTransit();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isSecure() {
        return this.mKeyguardSecurityModel.getSecurityMode(this.mSelectedUserInteractor.getSelectedUserId()) != KeyguardSecurityModel.SecurityMode.None;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final boolean isUnlockWithWallpaper() {
        return !((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.mediaBackdropShowing;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean isWakeAndUnlocking() {
        int i = this.mBiometricUnlockController.mMode;
        return i == 1 || i == 2;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void launchPendingWakeupAction() {
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

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean needsFullscreenBouncer() {
        return SecurityUtils.checkFullscreenBouncer(this.mKeyguardSecurityModel.getSecurityMode(this.mSelectedUserInteractor.getSelectedUserId()));
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void notifyKeyguardAuthenticated(boolean z) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardAuthenticatedBiometrics.setValue(Boolean.valueOf(z));
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            executeAfterKeyguardGoneAction();
        }
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onBackPressed() {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (primaryBouncerInteractor.isFullyShowing()) {
            if (!(isBouncerShowing() && this.mDreamOverlayStateController.isOverlayActive()) && (!primaryBouncerInteractor.isScrimmed() || needsFullscreenBouncer())) {
                reset(true);
                ((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide = false;
            } else {
                hideBouncer(false);
                updateStates();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        hideBouncer(true);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
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

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onFinishedGoingToSleep() {
        this.mPrimaryBouncerInteractor.hide();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.unfold.FoldAodAnimationController.FoldAodAnimationStatus
    public final void onFoldToAodAnimationChanged() {
        FoldAodAnimationController foldAodAnimationController = this.mFoldAodAnimationController;
        if (foldAodAnimationController != null) {
            this.mScreenOffAnimationPlaying = foldAodAnimationController.shouldPlayAnimation;
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
        if (isGesturalMode != this.mGesturalNav) {
            this.mGesturalNav = isGesturalMode;
            updateStates();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        float f = this.mFraction;
        float f2 = shadeExpansionChangeEvent.fraction;
        boolean z = shadeExpansionChangeEvent.tracking;
        if (f == f2 && this.mTracking == z) {
            return;
        }
        this.mFraction = f2;
        this.mTracking = z;
        boolean z2 = false;
        boolean z3 = this.mDreamOverlayStateController.isOverlayActive() && (this.mShadeLockscreenInteractor.isExpanded() || ((ShadeController) this.mShadeController.get()).isExpandingOrCollapsing());
        if (f2 != 1.0f && z) {
            z2 = true;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z4 = keyguardStateControllerImpl.mShowing;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (z4 && !primaryBouncerIsOrWillBeShowing() && !keyguardStateControllerImpl.mKeyguardGoingAway && z2 && !z3 && !keyguardStateControllerImpl.mOccluded && !keyguardStateControllerImpl.mCanDismissLockScreen && !primaryBouncerInteractor.isAnimatingAway()) {
            int i = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState;
        }
        if (primaryBouncerIsOrWillBeShowing() && !keyguardStateControllerImpl.mShowing) {
            primaryBouncerInteractor.setPanelExpansion(1.0f);
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.RemoteInputController.Callback
    public final void onRemoteInputActive(boolean z) {
        this.mRemoteInputActive = z;
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        updateResources$1();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onTouch() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
    }

    @Override // com.android.keyguard.KeyguardViewController
    public final void prepareSafeUIBouncer() {
        SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 safeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 = this.mPrepareBouncerRunnable;
        if (safeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 != null) {
            safeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1.run();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final boolean primaryBouncerIsOrWillBeShowing() {
        return isBouncerShowing() || this.mPrimaryBouncerInteractor.isInTransit();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean primaryBouncerIsScrimmed() {
        return this.mPrimaryBouncerInteractor.isScrimmed();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean primaryBouncerIsShowing() {
        return this.mPrimaryBouncerInteractor.isFullyShowing();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean primaryBouncerNeedsScrimming() {
        if (!((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded || this.mDreamOverlayStateController.isOverlayActive()) {
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (!primaryBouncerInteractor.willDismissWithAction() && ((!primaryBouncerInteractor.isFullyShowing() || !primaryBouncerInteractor.isScrimmed()) && !isFullscreenBouncer())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean primaryBouncerWillDismissWithAction() {
        return this.mPrimaryBouncerInteractor.willDismissWithAction();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void readyForKeyguardDone() {
        this.mViewMediatorCallback.readyForKeyguardDone();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void registerCentralSurfaces(CentralSurfaces centralSurfaces, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
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
        this.mGesturalNav = QuickStepContract.isGesturalMode(this.mNavigationModeController.addListener(this));
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
        this.mListenForCanShowAlternateBouncer = this.mJavaAdapter.alwaysCollectFlow(this.mAlternateBouncerInteractor.canShowAlternateBouncer, new Consumer() { // from class: com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = SafeUIStatusBarKeyguardViewManager.this;
                ((Boolean) obj).booleanValue();
                safeUIStatusBarKeyguardViewManager.getClass();
            }
        });
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        Flags.keyguardWmStateRefactor();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void requestFp(boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        keyguardUpdateMonitor.mOccludingAppRequestingFp = z;
        keyguardUpdateMonitor.updateFingerprintListeningState(2);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void reset(boolean z) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (!keyguardStateControllerImpl.mShowing || this.mPrimaryBouncerInteractor.isAnimatingAway()) {
            return;
        }
        if (!keyguardStateControllerImpl.mOccluded || this.mDozing) {
            showBouncerOrKeyguard(z);
        } else if (z || needsFullscreenBouncer()) {
            hideBouncer(false);
        }
        if (z) {
            hideAlternateBouncer(true);
        }
        this.mKeyguardUpdateManager.mHandler.obtainMessage(312).sendToTarget();
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void setGlobalActionsVisible(boolean z) {
        this.mGlobalActionsVisible = z;
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void setKeyguardGoingAwayState(boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        if (secNotificationShadeWindowControllerHelperImpl.getCurrentState().keyguardGoingAway != z) {
            Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("keyguardGoingAway ", z));
        }
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardGoingAway = z;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void setKeyguardMessage(String str, ColorStateList colorStateList) {
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            return;
        }
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        primaryBouncerInteractor.getClass();
        ((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository)._showMessage.setValue(new BouncerShowMessageModel(str, colorStateList));
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void setNeedsInput(boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardNeedsInput = z;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void setOccluded(boolean z, boolean z2) {
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
        boolean z7 = keyguardStateControllerImpl.mOccluded;
        if (z6 && z5) {
            SysUiStatsLog.write(62, 3);
        } else if (z6 && z4) {
            SysUiStatsLog.write(62, 2);
        }
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        notificationShadeWindowState.keyguardOccluded = z7;
        notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        if (this.mDozing || keyguardStateControllerImpl.mKeyguardGoingAway) {
            return;
        }
        reset(z5);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void setPulsing(boolean z) {
        if (this.mPulsing != z) {
            this.mPulsing = z;
            updateStates();
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void setQsExpansion(float f) {
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final boolean shouldDismissOnMenuPressed() {
        BouncerView bouncerView = this.mPrimaryBouncerView;
        return ((BouncerViewImpl) bouncerView).getDelegate() != null && ((BouncerViewImpl) bouncerView).getDelegate().$securityContainerController.shouldEnableMenuKey();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void shouldInterceptTouchEvent() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void show(Bundle bundle) {
        Trace.beginSection("StatusBarKeyguardViewManager#show");
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setKeyguardShowing(true);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardState(true, keyguardStateControllerImpl.mOccluded);
        reset(true);
        SysUiStatsLog.write(62, 2);
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void showBouncer() {
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        this.mAlternateBouncerInteractor.getClass();
        showPrimaryBouncer(true);
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void showBouncerOrKeyguard(boolean z) {
        if (needsFullscreenBouncer() && !this.mDozing) {
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (primaryBouncerInteractor.isFullyShowing()) {
                android.util.Log.e("SafeUIStatusBarKeyguardViewManager", "Attempted to show the sim bouncer when it is already showing.");
            } else {
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                primaryBouncerInteractor.show(true);
            }
        } else if (z) {
            hideBouncer(false);
        }
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void showPrimaryBouncer(boolean z) {
        hideAlternateBouncer(false);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && !isBouncerShowing()) {
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            this.mPrimaryBouncerInteractor.show(z);
        }
        updateStates();
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void startPreHideAnimation(Runnable runnable) {
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        if (!primaryBouncerInteractor.isFullyShowing()) {
            if (runnable != null) {
                runnable.run();
            }
        } else {
            primaryBouncerInteractor.startDisappearAnimation(runnable);
            int i = SceneContainerFlag.$r8$clinit;
            Flags.sceneContainer();
            if (this.mDismissActionWillAnimateOnKeyguard) {
                updateStates();
            }
        }
    }

    public final void updateAlternateBouncerShowing(boolean z) {
        if (this.mCentralSurfacesRegistered) {
            this.mKeyguardUpdateManager.setAlternateBouncerShowing(this.mAlternateBouncerInteractor.isVisibleState());
            if (z) {
                ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void updateKeyguardPosition(float f) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardPosition.updateState(null, Float.valueOf(f));
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void updateResources$1() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository;
        keyguardBouncerRepositoryImpl._resourceUpdateRequests.updateState(null, Boolean.TRUE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void updateStates() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        boolean z2 = keyguardStateControllerImpl.mOccluded;
        PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
        boolean isFullyShowing = primaryBouncerInteractor.isFullyShowing();
        boolean primaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing();
        boolean z3 = !isFullscreenBouncer();
        boolean z4 = this.mRemoteInputActive;
        if ((z3 || !z || z4) != (this.mLastBouncerDismissible || !this.mLastShowing || this.mLastRemoteInputActive) || this.mFirstUpdate) {
            if (z3 || !z || z4) {
                primaryBouncerInteractor.setBackButtonEnabled(true);
            } else {
                primaryBouncerInteractor.setBackButtonEnabled(false);
            }
        }
        if (isNavBarVisible() == getLastNavBarVisible()) {
            boolean z5 = this.mFirstUpdate;
        }
        boolean z6 = isFullyShowing != this.mLastPrimaryBouncerShowing;
        this.mLastPrimaryBouncerShowing = isFullyShowing;
        if (z6 || this.mFirstUpdate) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.bouncerShowing = isFullyShowing;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
        if (primaryBouncerIsOrWillBeShowing != this.mLastPrimaryBouncerIsOrWillBeShowing || this.mFirstUpdate || z6) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
            keyguardUpdateMonitor.mLogger.logSendPrimaryBouncerChanged(primaryBouncerIsOrWillBeShowing, isFullyShowing);
            Message obtainMessage = keyguardUpdateMonitor.mHandler.obtainMessage(322);
            obtainMessage.arg1 = primaryBouncerIsOrWillBeShowing ? 1 : 0;
            obtainMessage.arg2 = isFullyShowing ? 1 : 0;
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
        this.mLastGesturalNav = this.mGesturalNav;
        this.mLastIsDocked = false;
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void blockPanelExpansionFromCurrentTouch() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void dismissAndCollapse() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void keyguardGoingAway() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void onKeyguardFadedAway$1() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedGoingToSleep() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.KeyguardViewController
    public final void onStartedWakingUp() {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void setTaskbarDelegate(TaskbarDelegate taskbarDelegate) {
    }

    @Override // com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager
    public final void updateNavigationBarVisibility(boolean z) {
    }
}
