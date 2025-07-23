package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.window.BackEvent;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.Rune;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.animation.back.FlingOnBackAnimationCallback;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.shared.model.BouncerShowMessageModel;
import com.android.systemui.bouncer.ui.BouncerView;
import com.android.systemui.bouncer.ui.BouncerViewImpl;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.DismissAction;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.views.NavigationBarView;
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
import com.android.systemui.statusbar.LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.domain.interactor.StatusBarKeyguardViewManagerInteractor;
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
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarKeyguardViewManager implements RemoteInputController.Callback, StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener, ShadeExpansionListener, NavigationModeController.ModeChangedListener, KeyguardViewController, FoldAodAnimationController.FoldAodAnimationStatus {
    public final ActivityStarter mActivityStarter;
    public ActivityStarter.OnDismissAction mAfterKeyguardGoneAction;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public BiometricUnlockController mBiometricUnlockController;
    public boolean mBouncerShowingOverDream;
    public CentralSurfacesImpl mCentralSurfaces;
    public boolean mCentralSurfacesRegistered;
    public final CommunalSceneInteractor mCommunalSceneInteractor;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public boolean mDismissActionWillAnimateOnKeyguard;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final DelayableExecutor mExecutor;
    public final FoldAodAnimationController mFoldAodAnimationController;
    public boolean mGesturalNav;
    public final JavaAdapter mJavaAdapter;
    public final Lazy mKeyguardDismissActionInteractor;
    public Runnable mKeyguardGoneCancelAction;
    public final KeyguardSecurityModel mKeyguardSecurityModel;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateManager;
    public int mLastBiometricMode;
    public boolean mLastBouncerDismissible;
    public boolean mLastDozing;
    public boolean mLastGesturalNav;
    public boolean mLastOccluded;
    public boolean mLastPrimaryBouncerIsOrWillBeShowing;
    public boolean mLastPrimaryBouncerShowing;
    public boolean mLastPulsing;
    public boolean mLastRemoteInputActive;
    public boolean mLastScreenOffAnimationPlaying;
    public boolean mLastShowing;
    public final LatencyTracker mLatencyTracker;
    public final NavigationModeController mNavigationModeController;
    public View mNotificationContainer;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public DismissWithActionRequest mPendingWakeupAction;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final BouncerView mPrimaryBouncerView;
    public boolean mPulsing;
    public boolean mRemoteInputActive;
    public boolean mScreenOffAnimationPlaying;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final Lazy mShadeController;
    public ShadeLockscreenInteractor mShadeLockscreenInteractor;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public TaskbarDelegate mTaskbarDelegate;
    public final ViewMediatorCallback mViewMediatorCallback;
    public Job mListenForCanShowAlternateBouncer = null;
    public float mFraction = -1.0f;
    public boolean mTracking = false;
    public int mAttemptsToShowBouncer = 0;
    public boolean mIsSleeping = false;
    public final AnonymousClass1 mExpansionCallback = new PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.1
        public boolean mPrimaryBouncerAnimating;

        @Override // com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback
        public final void onExpansionChanged(float f) {
            if (this.mPrimaryBouncerAnimating) {
                StatusBarKeyguardViewManager.this.mCentralSurfaces.setPrimaryBouncerHiddenFraction(f);
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
            statusBarKeyguardViewManager.mBouncerShowingOverDream = z && statusBarKeyguardViewManager.mDreamOverlayStateController.containsState(1);
            if (!z) {
                statusBarKeyguardViewManager.mCentralSurfaces.setPrimaryBouncerHiddenFraction(1.0f);
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
    public final AnonymousClass2 mOnBackInvokedCallback = new FlingOnBackAnimationCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.2
        @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
        public final void onBackCancelledCompat() {
            ComposeBouncerFlags.INSTANCE.getClass();
            if (StatusBarKeyguardViewManager.this.needsFullscreenBouncer() || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackCancelled();
        }

        @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
        public final void onBackInvokedCompat() {
            StatusBarKeyguardViewManager.this.onBackPressed();
            if (StatusBarKeyguardViewManager.this.needsFullscreenBouncer() || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback().getClass();
        }

        @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
        public final void onBackProgressedCompat(BackEvent backEvent) {
            ComposeBouncerFlags.INSTANCE.getClass();
            if (StatusBarKeyguardViewManager.this.needsFullscreenBouncer() || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((KeyguardSecurityContainer.AnonymousClass2) ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback()).onBackProgressed(backEvent);
        }

        @Override // com.android.systemui.animation.back.FlingOnBackAnimationCallback
        public final void onBackStartedCompat(BackEvent backEvent) {
            if (StatusBarKeyguardViewManager.this.needsFullscreenBouncer() || ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate() == null) {
                return;
            }
            ((BouncerViewImpl) StatusBarKeyguardViewManager.this.mPrimaryBouncerView).getDelegate().$securityContainerController.getBackCallback().onBackStarted(backEvent);
        }
    };
    public boolean mIsBackCallbackRegistered = false;
    public final AnonymousClass3 mDockEventListener = new Object(this) { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.3
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
        public final void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
            AlternateBouncerInteractor alternateBouncerInteractor = statusBarKeyguardViewManager.mAlternateBouncerInteractor;
            boolean hide = alternateBouncerInteractor.isVisibleState() ? alternateBouncerInteractor.hide() : false;
            if (statusBarKeyguardViewManager.mCentralSurfacesRegistered) {
                boolean isVisibleState = statusBarKeyguardViewManager.mAlternateBouncerInteractor.isVisibleState();
                int i = SceneContainerFlag.$r8$clinit;
                statusBarKeyguardViewManager.mKeyguardUpdateManager.setAlternateBouncerShowing(isVisibleState);
                if (hide) {
                    statusBarKeyguardViewManager.mCentralSurfaces.updateScrimController();
                }
            }
        }
    };
    public final AnonymousClass8 mMakeNavigationBarVisibleRunnable = new AnonymousClass8();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$8, reason: invalid class name */
    public class AnonymousClass8 implements Runnable {
        public AnonymousClass8() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NavigationBarView navigationBarView = StatusBarKeyguardViewManager.this.mCentralSurfaces.getNavigationBarView();
            if (navigationBarView != null) {
                navigationBarView.setVisibility(0);
            }
            ((NotificationShadeWindowControllerImpl) StatusBarKeyguardViewManager.this.mNotificationShadeWindowController).mWindowRootView.getWindowInsetsController().show(WindowInsets.Type.navigationBars());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DismissWithActionRequest {
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

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$3] */
    public StatusBarKeyguardViewManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, Optional<SysUIUnfoldComponent> optional, Lazy lazy, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, ActivityStarter activityStarter, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor, CoroutineDispatcher coroutineDispatcher, Lazy lazy2, SelectedUserInteractor selectedUserInteractor, JavaAdapter javaAdapter, Lazy lazy3, StatusBarKeyguardViewManagerInteractor statusBarKeyguardViewManagerInteractor, DelayableExecutor delayableExecutor, Lazy lazy4, DismissCallbackRegistry dismissCallbackRegistry, Lazy lazy5, CommunalSceneInteractor communalSceneInteractor) {
        this.mContext = context;
        this.mExecutor = delayableExecutor;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mConfigurationController = configurationController;
        this.mNavigationModeController = navigationModeController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateManager = keyguardUpdateMonitor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDockManager = dockManager;
        this.mShadeController = lazy;
        this.mLatencyTracker = latencyTracker;
        this.mKeyguardSecurityModel = keyguardSecurityModel;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mPrimaryBouncerView = bouncerView;
        this.mFoldAodAnimationController = (FoldAodAnimationController) optional.map(new SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda0()).orElse(null);
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mActivityStarter = activityStarter;
        this.mKeyguardDismissActionInteractor = lazy2;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mCommunalSceneInteractor = communalSceneInteractor;
    }

    public void addAfterKeyguardGoneRunnable(Runnable runnable) {
        ComposeBouncerFlags.INSTANCE.getClass();
        this.mAfterKeyguardGoneRunnables.add(runnable);
    }

    public void blockPanelExpansionFromCurrentTouch() {
        this.mShadeLockscreenInteractor.blockExpansionForCurrentTouch();
    }

    public boolean bouncerIsAnimatingAway() {
        return ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository).isPrimaryBouncerStartingDisappearAnimation();
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
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        hideAlternateBouncer(false);
    }

    public void consumeKeyguardAuthenticatedBiometricsHandled(Unit unit) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mAlternateBouncerInteractor.isVisibleState()) {
            hideAlternateBouncer(false);
        }
    }

    public void dismissAndCollapse() {
        this.mActivityStarter.executeRunnableDismissingKeyguard(null, null, true, false, true);
    }

    public void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1 lockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1) {
        dismissWithAction(onDismissAction, lockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1, false, null);
    }

    public boolean dispatchBackKeyEventPreIme() {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.mPrimaryBouncerView;
        return bouncerViewImpl.getDelegate() != null && bouncerViewImpl.getDelegate().$securityContainerController.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.Password;
    }

    public void dump(PrintWriter printWriter) {
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "StatusBarKeyguardViewManager:", "  mRemoteInputActive: "), this.mRemoteInputActive, printWriter, "  mDozing: "), this.mDozing, printWriter, "  mAfterKeyguardGoneAction: ");
        m.append(this.mAfterKeyguardGoneAction);
        printWriter.println(m.toString());
        printWriter.println("  mAfterKeyguardGoneRunnables: " + this.mAfterKeyguardGoneRunnables);
        printWriter.println("  mPendingWakeupAction: " + this.mPendingWakeupAction);
        printWriter.println("  isBouncerShowing(): " + isBouncerShowing());
        printWriter.println("  bouncerIsOrWillBeShowing(): " + primaryBouncerIsOrWillBeShowing());
        printWriter.println("  Registered KeyguardViewManagerCallbacks:");
        int i = SceneContainerFlag.$r8$clinit;
        printWriter.println(" SceneContainerFlag enabled:false");
        ComposeBouncerFlags.INSTANCE.getClass();
        printWriter.println(" ComposeBouncerFlags enabled:false");
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            printWriter.println("      null");
        }
    }

    public final void executeAfterKeyguardGoneAction$1() {
        ComposeBouncerFlags.INSTANCE.getClass();
        LogUtil.d("KeyguardUnlockInfo", "executeAfterKeyguardGoneAction dismissAction=%d, goneRunnable=%d", Integer.valueOf(LogUtil.getInt(this.mAfterKeyguardGoneAction)), Integer.valueOf(this.mAfterKeyguardGoneRunnables.size()));
        ActivityStarter.OnDismissAction onDismissAction = this.mAfterKeyguardGoneAction;
        if (onDismissAction != null) {
            onDismissAction.onDismiss();
            this.mAfterKeyguardGoneAction = null;
        }
        this.mKeyguardGoneCancelAction = null;
        this.mDismissActionWillAnimateOnKeyguard = false;
        for (int i = 0; i < this.mAfterKeyguardGoneRunnables.size(); i++) {
            ((Runnable) this.mAfterKeyguardGoneRunnables.get(i)).run();
        }
        this.mAfterKeyguardGoneRunnables.clear();
    }

    public boolean getLastNavBarVisible() {
        boolean z = this.mLastShowing && !this.mLastOccluded;
        boolean z2 = this.mLastDozing;
        return !(z || (z2 && this.mLastBiometricMode != 2) || this.mLastScreenOffAnimationPlaying) || this.mLastPrimaryBouncerShowing || this.mLastRemoteInputActive || (((z && !z2 && !this.mLastScreenOffAnimationPlaying) || this.mLastPulsing) && this.mLastGesturalNav) || this.mLastGlobalActionsVisible;
    }

    public boolean getLastPrimaryBouncerShowing() {
        return this.mLastPrimaryBouncerShowing;
    }

    public long getNavBarShowDelay() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        return keyguardStateControllerImpl.mKeyguardFadingAway ? keyguardStateControllerImpl.mKeyguardFadingAwayDelay : isBouncerShowing() ? 320L : 0L;
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
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        DejankUtils.notifyRendererOfExpensiveFrame(notificationShadeWindowControllerImpl.mWindowRootView, "StatusBarKeyguardViewManager#hide");
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
        CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
        centralSurfacesImpl.mCommandQueue.appTransitionStarting(centralSurfacesImpl.mDisplayId, (j + j3) - 120, 120L, true);
        centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, j3 > 0);
        centralSurfacesImpl.mCommandQueue.appTransitionStarting(centralSurfacesImpl.mDisplayId, j - 120, 120L, true);
        KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDelay = max;
        keyguardStateControllerImpl2.mKeyguardFadingAwayDuration = j3;
        if (!keyguardStateControllerImpl2.mKeyguardFadingAway) {
            TrackTracer.instantForGroup(1, "keyguard", "FadingAway");
            keyguardStateControllerImpl2.mKeyguardFadingAway = true;
            keyguardStateControllerImpl2.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(0));
        }
        final BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        biometricUnlockController.getClass();
        biometricUnlockController.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.2
            @Override // java.lang.Runnable
            public final void run() {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl2 = (NotificationShadeWindowControllerImpl) BiometricUnlockController.this.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl2.mCurrentState;
                if (notificationShadeWindowState.forceDozeBrightness) {
                    notificationShadeWindowState.forceDozeBrightness = false;
                    notificationShadeWindowControllerImpl2.apply(notificationShadeWindowState);
                }
            }
        }, 96L);
        hideBouncer(true);
        if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mLeaveOpenOnKeyguardHide || isMode || keyguardStateControllerImpl.mOccluded) {
            this.mCentralSurfaces.hideKeyguard();
            this.mCentralSurfaces.finishKeyguardFadingAway();
            this.mBiometricUnlockController.finishKeyguardFadingAway();
        } else {
            notificationShadeWindowControllerImpl.setKeyguardFadingAway(true);
            if (this.mBiometricUnlockController.isWakeAndUnlock() && this.mLatencyTracker.isEnabled()) {
                this.mLatencyTracker.onActionEnd(this.mBiometricUnlockController.mBiometricType == BiometricSourceType.FACE ? 7 : 2);
            }
            this.mCentralSurfaces.hideKeyguard();
            this.mCentralSurfaces.updateScrimController();
        }
        updateStates();
        notificationShadeWindowControllerImpl.setKeyguardShowing(false);
        this.mViewMediatorCallback.keyguardGone();
        SysUiStatsLog.write(62, 1);
        Trace.endSection();
    }

    public void hideAlternateBouncer(boolean z) {
        hideAlternateBouncer$1(z);
    }

    public final void hideAlternateBouncer$1(boolean z) {
        KeyguardDismissActionInteractor keyguardDismissActionInteractor = (KeyguardDismissActionInteractor) this.mKeyguardDismissActionInteractor.get();
        keyguardDismissActionInteractor.getClass();
        ((KeyguardRepositoryImpl) keyguardDismissActionInteractor.repository)._dismissAction.setValue(DismissAction.None.INSTANCE);
        AlternateBouncerInteractor alternateBouncerInteractor = this.mAlternateBouncerInteractor;
        boolean z2 = alternateBouncerInteractor.hide() && z;
        if (this.mCentralSurfacesRegistered) {
            boolean isVisibleState = alternateBouncerInteractor.isVisibleState();
            int i = SceneContainerFlag.$r8$clinit;
            this.mKeyguardUpdateManager.setAlternateBouncerShowing(isVisibleState);
            if (z2) {
                this.mCentralSurfaces.updateScrimController();
            }
        }
    }

    public void hideBouncer(boolean z) {
        this.mPrimaryBouncerInteractor.hide();
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            cancelPostAuthActions();
        }
        cancelPendingWakeupAction();
    }

    public boolean interceptMediaKey(KeyEvent keyEvent) {
        ComposeBouncerFlags composeBouncerFlags = ComposeBouncerFlags.INSTANCE;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ComposeBouncerFlags.INSTANCE.getClass();
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
        CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
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
    }

    public void onBackPressed() {
        if (canHandleBackPressed()) {
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
            statusBarStateControllerImpl.setLeaveOpenOnKeyguardHide(false);
            boolean z = isBouncerShowing() && (this.mDreamOverlayStateController.containsState(1) || ((Boolean) this.mCommunalSceneInteractor.isIdleOnCommunal.$$delegate_0.getValue()).booleanValue());
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            centralSurfacesImpl.releaseGestureWakeLock();
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).setLaunchingAffordance(false);
            if (z || (primaryBouncerIsScrimmed() && !needsFullscreenBouncer())) {
                hideBouncer(false);
                updateStates();
                return;
            }
            boolean z2 = this.mCentralSurfaces.mScrimController.mState == ScrimState.BOUNCER_SCRIMMED;
            reset(z2);
            if (z2) {
                statusBarStateControllerImpl.setLeaveOpenOnKeyguardHide(false);
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

    public void onKeyguardFadedAway() {
        this.mNotificationContainer.postDelayed(new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, 0), 100L);
        this.mShadeLockscreenInteractor.resetViewGroupFade();
        this.mCentralSurfaces.finishKeyguardFadingAway();
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
        this.mIsSleeping = true;
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        if (windowRootView != null && (windowInsetsController = windowRootView.getWindowInsetsController()) != null) {
            windowInsetsController.setAnimationsDisabled(true);
        }
        NavigationBarView navigationBarView = this.mCentralSurfaces.getNavigationBarView();
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
        this.mIsSleeping = false;
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        if (windowRootView != null && (windowInsetsController = windowRootView.getWindowInsetsController()) != null) {
            windowInsetsController.setAnimationsDisabled(false);
        }
        NavigationBarView navigationBarView = this.mCentralSurfaces.getNavigationBarView();
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
        return (((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded && !this.mDreamOverlayStateController.containsState(1)) || primaryBouncerWillDismissWithAction() || (primaryBouncerIsShowing() && primaryBouncerIsScrimmed()) || isFullscreenBouncer();
    }

    public boolean primaryBouncerWillDismissWithAction() {
        return this.mPrimaryBouncerInteractor.willDismissWithAction();
    }

    public void readyForKeyguardDone() {
        this.mViewMediatorCallback.readyForKeyguardDone();
    }

    public void registerCentralSurfaces(CentralSurfacesImpl centralSurfacesImpl, ShadeLockscreenInteractor shadeLockscreenInteractor, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view) {
        this.mCentralSurfaces = centralSurfacesImpl;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(this.mExpansionCallback);
        this.mShadeLockscreenInteractor = shadeLockscreenInteractor;
        if (shadeExpansionStateManager != null) {
            onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(this));
        }
        this.mNotificationContainer = view;
        this.mCentralSurfacesRegistered = true;
        this.mKeyguardUpdateManager.registerCallback(this.mUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this);
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
        this.mListenForCanShowAlternateBouncer = this.mJavaAdapter.alwaysCollectFlow(this.mAlternateBouncerInteractor.canShowAlternateBouncer, new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = StatusBarKeyguardViewManager.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                statusBarKeyguardViewManager.getClass();
                int i = SceneContainerFlag.$r8$clinit;
                if (booleanValue) {
                    return;
                }
                Log.d("StatusBarKeyguardViewManager", "canShowAlternateBouncer turned false, maybe try hiding the alternate bouncer if it is already visible");
                AlternateBouncerInteractor alternateBouncerInteractor = statusBarKeyguardViewManager.mAlternateBouncerInteractor;
                if (alternateBouncerInteractor.isVisibleState()) {
                    alternateBouncerInteractor.hide();
                }
            }
        });
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
    }

    public void requestFp(boolean z) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateManager;
        keyguardUpdateMonitor.mOccludingAppRequestingFp = z;
        keyguardUpdateMonitor.updateFingerprintListeningState(2);
    }

    public void reset(boolean z) {
        reset(z, false);
    }

    public void setAttemptsToShowBouncer(int i) {
        this.mAttemptsToShowBouncer = i;
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
            com.android.systemui.keyguard.Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("keyguardGoingAway ", z));
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
        boolean z7 = keyguardStateControllerImpl.mOccluded;
        if (z6 && z5) {
            SysUiStatsLog.write(62, 3);
            CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
            if (centralSurfacesImpl.mIsLaunchingActivityOverLockscreen) {
                StatusBarKeyguardViewManager$$ExternalSyntheticLambda0 statusBarKeyguardViewManager$$ExternalSyntheticLambda0 = new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, z7);
                if (centralSurfacesImpl.mDismissingShadeForActivityLaunch) {
                    ((BaseShadeControllerImpl) ((ShadeController) this.mShadeController.get())).postCollapseActions.add(statusBarKeyguardViewManager$$ExternalSyntheticLambda0);
                    return;
                } else {
                    statusBarKeyguardViewManager$$ExternalSyntheticLambda0.run();
                    return;
                }
            }
        } else if (z6 && z4) {
            SysUiStatsLog.write(62, 2);
        }
        postSetOccluded(z5);
    }

    public void setPulsing(boolean z) {
        if (this.mPulsing != z) {
            this.mPulsing = z;
            updateStates();
        }
    }

    public void setQsExpansion(float f) {
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        if (it.hasNext()) {
            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
        }
    }

    public void setTaskbarDelegate(TaskbarDelegate taskbarDelegate) {
        this.mTaskbarDelegate = taskbarDelegate;
    }

    public boolean shouldDismissOnMenuPressed() {
        BouncerView bouncerView = this.mPrimaryBouncerView;
        if (((BouncerViewImpl) bouncerView).getDelegate() != null && ((BouncerViewImpl) bouncerView).getDelegate().$securityContainerController.shouldEnableMenuKey()) {
            return true;
        }
        ComposeBouncerFlags.INSTANCE.getClass();
        return false;
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

    public final void showBouncer(String str) {
        int i = SceneContainerFlag.$r8$clinit;
        this.mAlternateBouncerInteractor.getClass();
        showPrimaryBouncer(str, true);
    }

    public void showBouncerOrKeyguard(String str, final boolean z, final boolean z2) {
        if (!needsFullscreenBouncer() || this.mDozing || this.mIsSleeping) {
            this.mCentralSurfaces.showKeyguard();
            if (z) {
                hideBouncer(false);
            }
        } else {
            boolean primaryBouncerIsShowing = primaryBouncerIsShowing();
            PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
            if (!primaryBouncerIsShowing) {
                int i = SceneContainerFlag.$r8$clinit;
                if (primaryBouncerInteractor.show(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, true)) {
                    this.mAttemptsToShowBouncer = 0;
                    this.mCentralSurfaces.hideKeyguard();
                } else {
                    int i2 = this.mAttemptsToShowBouncer;
                    if (i2 > 6) {
                        this.mAttemptsToShowBouncer = 0;
                        Log.e("StatusBarKeyguardViewManager", "Too many failed attempts to show bouncer, showing keyguard instead");
                        this.mCentralSurfaces.showKeyguard();
                    } else {
                        this.mAttemptsToShowBouncer = i2 + 1;
                        this.mExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                StatusBarKeyguardViewManager.this.showBouncerOrKeyguard(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, z, z2);
                            }
                        }, 500L);
                    }
                }
            } else if (!z2) {
                Log.i("StatusBarKeyguardViewManager", "Sim bouncer is already showing, issuing a refresh");
                primaryBouncerInteractor.show(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, true);
            }
        }
        updateStates();
    }

    public final void showPrimaryBouncer(String str, boolean z) {
        int i = SceneContainerFlag.$r8$clinit;
        hideAlternateBouncer$1(false);
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && !isBouncerShowing()) {
            this.mPrimaryBouncerInteractor.show(str, z);
        }
        updateStates();
    }

    public void startPreHideAnimation(Runnable runnable) {
        if (primaryBouncerIsShowing()) {
            this.mPrimaryBouncerInteractor.startDisappearAnimation(runnable);
            ComposeBouncerFlags.INSTANCE.getClass();
            if (this.mDismissActionWillAnimateOnKeyguard) {
                updateStates();
            }
        } else if (runnable != null) {
            runnable.run();
        }
        this.mShadeLockscreenInteractor.blockExpansionForCurrentTouch();
    }

    public void updateKeyguardPosition(float f) {
        ((KeyguardBouncerRepositoryImpl) this.mPrimaryBouncerInteractor.repository)._keyguardPosition.updateState(null, Float.valueOf(f));
    }

    public void updateNavigationBarVisibility(boolean z) {
        TaskbarDelegate taskbarDelegate;
        if (this.mCentralSurfaces.getNavigationBarView() != null || ((taskbarDelegate = this.mTaskbarDelegate) != null && taskbarDelegate.mInitialized)) {
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

    public void updateStates() {
        int i = 1;
        if (this.mCentralSurfacesRegistered) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            boolean z = keyguardStateControllerImpl.mShowing;
            boolean z2 = keyguardStateControllerImpl.mOccluded;
            boolean z3 = primaryBouncerIsShowing() && (!LsRune.SECURITY_BOUNCER_WINDOW || z);
            boolean primaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing();
            boolean isFullscreenBouncer = isFullscreenBouncer();
            boolean z4 = !isFullscreenBouncer;
            boolean z5 = this.mRemoteInputActive;
            boolean z6 = Rune.SYSUI_MULTI_SIM;
            updateKeyguardUnlocking();
            if (((isFullscreenBouncer && z && !z5) ? false : true) != (this.mLastBouncerDismissible || !this.mLastShowing || this.mLastRemoteInputActive) || this.mFirstUpdate) {
                PrimaryBouncerInteractor primaryBouncerInteractor = this.mPrimaryBouncerInteractor;
                if (isFullscreenBouncer && z && !z5) {
                    primaryBouncerInteractor.setBackButtonEnabled(false);
                } else {
                    primaryBouncerInteractor.setBackButtonEnabled(true);
                }
            }
            boolean isNavBarVisible = isNavBarVisible();
            if (isNavBarVisible != getLastNavBarVisible() || this.mFirstUpdate) {
                updateNavigationBarVisibility(isNavBarVisible);
            }
            sendKeyguardViewState(z, z2, z3);
            boolean z7 = z3 != this.mLastPrimaryBouncerShowing;
            this.mLastPrimaryBouncerShowing = z3;
            if (z7 || this.mFirstUpdate) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.bouncerShowing = z3;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                CentralSurfacesImpl centralSurfacesImpl = this.mCentralSurfaces;
                centralSurfacesImpl.mBouncerShowing = z3;
                centralSurfacesImpl.mKeyguardBypassController.bouncerShowing = z3;
                centralSurfacesImpl.mPulseExpansionHandler.bouncerShowing = z3;
                centralSurfacesImpl.setBouncerShowingForStatusBarComponents(z3);
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = centralSurfacesImpl.mStatusBarHideIconsForBouncerManager;
                statusBarHideIconsForBouncerManager.bouncerShowing = z3;
                statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(true);
                centralSurfacesImpl.mCommandQueue.recomputeDisableFlags(centralSurfacesImpl.mDisplayId, true);
                if (centralSurfacesImpl.mBouncerShowing) {
                    centralSurfacesImpl.mPowerInteractor.wakeUpIfDozing(4, "BOUNCER_VISIBLE");
                }
                centralSurfacesImpl.updateScrimController();
                if (!centralSurfacesImpl.mBouncerShowing) {
                    centralSurfacesImpl.updatePanelExpansionForKeyguard();
                }
                boolean z8 = LsRune.SECURITY_CAPTURED_BLUR;
                SecQpBlurController secQpBlurController = centralSurfacesImpl.mBlurController;
                if (z8) {
                    if (z8 && DeviceState.isCapturedBlurAllowed()) {
                        secQpBlurController.getClass();
                        if (z8) {
                            secQpBlurController.isBouncerShowing = z3;
                            Log.d(SecQpBlurController.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setBouncerShowing: ", z3));
                            boolean z9 = ((int) secQpBlurController.panelExpandedFraction) != 0;
                            if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                                KeyguardInteractor keyguardInteractor = secQpBlurController.keyguardInteractor;
                                if (z3) {
                                    secQpBlurController.doBlur(1.0f, ((Boolean) keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue() ? SecPanelBlurBinding.BlurType.QUICK_PANEL : SecPanelBlurBinding.BlurType.BOUNCER);
                                } else if (keyguardInteractor.isKeyguardShowing() && !z9) {
                                    secQpBlurController.doBlur(0.0f, SecPanelBlurBinding.BlurType.BOUNCER);
                                }
                            }
                        }
                    }
                    NotificationShadeWindowView notificationShadeWindowView = centralSurfacesImpl.getNotificationShadeWindowViewController().mView;
                    if (notificationShadeWindowView.mBouncerShowing != z3) {
                        notificationShadeWindowView.mBouncerShowing = z3;
                        notificationShadeWindowView.applyBouncerMargins();
                    }
                }
                if (QpRune.QUICK_PANEL_BLUR_DEFAULT && secQpBlurController != null) {
                    secQpBlurController.isBouncerShowing = z3;
                    secQpBlurController.animatedFraction = 0.0f;
                    Log.d(SecQpBlurController.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setBouncerWindowShowing: ", z3));
                    if (z3) {
                        float f = secQpBlurController.panelExpandedFraction;
                        if (((int) f) != 0) {
                            secQpBlurController.makeAnimationAndRun(f, 0.0f, 350);
                        }
                    }
                    if (!z3 && secQpBlurController.secPanelExpansionStateInteractor.getstatusBarState() == 0 && secQpBlurController.panelExpandedFraction == 1.0f) {
                        secQpBlurController.makeAnimationAndRun(0.0f, 1.0f, 350);
                    }
                }
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    updateBouncerNavigationBar(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY));
                }
            }
            int i2 = SceneContainerFlag.$r8$clinit;
            if (primaryBouncerIsOrWillBeShowing != this.mLastPrimaryBouncerIsOrWillBeShowing || this.mFirstUpdate || z7) {
                this.mKeyguardUpdateManager.sendPrimaryBouncerChanged(primaryBouncerIsOrWillBeShowing, z3);
            }
            this.mFirstUpdate = false;
            this.mLastShowing = z;
            this.mLastGlobalActionsVisible = this.mGlobalActionsVisible;
            this.mLastOccluded = z2;
            this.mLastPrimaryBouncerIsOrWillBeShowing = primaryBouncerIsOrWillBeShowing;
            this.mLastBouncerDismissible = z4;
            this.mLastRemoteInputActive = z5;
            this.mLastDozing = this.mDozing;
            this.mLastPulsing = this.mPulsing;
            this.mLastScreenOffAnimationPlaying = this.mScreenOffAnimationPlaying;
            this.mLastBiometricMode = this.mBiometricUnlockController.mMode;
            this.mLastGesturalNav = this.mGesturalNav;
            updateLastKeyguardUnlocking();
            Rune.runIf(new StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(this, i), LsRune.COVER_SUPPORTED);
            this.mCentralSurfaces.logStateToEventlog();
        }
    }

    public void dismissWithAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, String str) {
        ComposeBouncerFlags.INSTANCE.getClass();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (keyguardStateControllerImpl.mShowing) {
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
                    int i = SceneContainerFlag.$r8$clinit;
                    primaryBouncerInteractor.show("StatusBarKeyguardViewManager#dismissWithAction, afterKeyguardGone", true);
                } else {
                    primaryBouncerInteractor.setDismissAction(this.mAfterKeyguardGoneAction, this.mKeyguardGoneCancelAction);
                    int i2 = SceneContainerFlag.$r8$clinit;
                    primaryBouncerInteractor.show("StatusBarKeyguardViewManager#dismissWithAction", true);
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

    public void reset(boolean z, boolean z2) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        if (!keyguardStateControllerImpl.mShowing || bouncerIsAnimatingAway()) {
            return;
        }
        boolean z3 = keyguardStateControllerImpl.mOccluded;
        this.mShadeLockscreenInteractor.resetViews(!z3);
        if (!z3 || this.mDozing) {
            showBouncerOrKeyguard(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, z, z2);
        } else {
            this.mCentralSurfaces.hideKeyguard();
            if (z || needsFullscreenBouncer()) {
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                hideBouncer(false);
            }
        }
        int i = SceneContainerFlag.$r8$clinit;
        if (z && isBouncerShowing()) {
            hideAlternateBouncer(true);
            this.mDismissCallbackRegistry.notifyDismissCancelled();
            this.mPrimaryBouncerInteractor.setDismissAction(null, null);
        }
        this.mKeyguardUpdateManager.mHandler.obtainMessage(312).sendToTarget();
        updateStates();
    }

    public void shouldSubtleWindowAnimationsForUnlock() {
    }
}
