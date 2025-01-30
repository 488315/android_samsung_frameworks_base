package com.android.systemui.shade;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardPresentationDisabler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.compose.ComposeFacade;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.shared.animation.DisableSubpixelTextTransitionListener;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.Optional;
import java.util.function.Consumer;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShadeWindowViewController {
    public final AmbientState mAmbientState;
    public View mBrightnessMirror;
    public final SystemClock mClock;
    public final NotificationShadeDepthController mDepthController;
    public MotionEvent mDownEvent;
    public DragDownHelper mDragDownHelper;
    public boolean mExpandAnimationRunning;
    public boolean mExpandingBelowNotch;
    public final FalsingCollector mFalsingCollector;
    public final boolean mIsTrackpadCommonEnabled;
    public final KeyguardBouncerComponent.Factory mKeyguardBouncerComponentFactory;
    public final KeyguardBouncerViewModel mKeyguardBouncerViewModel;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SecLockIconViewController mLockIconViewController;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationShadeWindowViewController$$ExternalSyntheticLambda0 mLockscreenToDreamingTransition;
    public final NotificationInsetsController mNotificationInsetsController;
    public final NotificationPanelViewController mNotificationPanelViewController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final SecPanelBlockExpandingHelper mPanelBlockExpandingHelper;
    public KeyguardPresentationDisabler mPresentationDisabler;
    public final PrimaryBouncerToGoneTransitionViewModel mPrimaryBouncerToGoneTransitionViewModel;
    public final CentralSurfaces mService;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public NotificationStackScrollLayout mStackScrollLayout;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public PhoneStatusBarViewController mStatusBarViewController;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public boolean mTouchActive;
    public boolean mTouchCancelled;
    public final NotificationShadeWindowView mView;
    public boolean mIsTrackingBarGesture = false;
    public boolean mIsOcclusionTransitionRunning = false;
    public boolean mTouchedOnEmptyArea = false;
    public boolean mPluginLockTouchArea = false;
    public boolean mSecKeyguardStatusViewTouchArea = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.NotificationShadeWindowViewController$1 */
    public final class C24461 {
        public C24461() {
        }

        public final boolean isTouchableArea(MotionEvent motionEvent) {
            KeyguardBottomAreaView keyguardBottomAreaView;
            NotificationPanelViewController notificationPanelViewController = NotificationShadeWindowViewController.this.mNotificationPanelViewController;
            notificationPanelViewController.getClass();
            if (LsRune.LOCKUI_BOTTOM_USIM_TEXT && (keyguardBottomAreaView = notificationPanelViewController.mKeyguardBottomArea) != null && keyguardBottomAreaView.isInEmergencyButtonArea(motionEvent)) {
                return true;
            }
            KeyguardIndicationController keyguardIndicationController = notificationPanelViewController.mKeyguardIndicationController;
            return keyguardIndicationController != null && keyguardIndicationController.isInLifeStyleArea(motionEvent);
        }
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.shade.NotificationShadeWindowViewController$$ExternalSyntheticLambda0] */
    public NotificationShadeWindowViewController(PluginLockStarManager pluginLockStarManager, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, SysuiStatusBarStateController sysuiStatusBarStateController, DockManager dockManager, NotificationShadeDepthController notificationShadeDepthController, NotificationShadeWindowView notificationShadeWindowView, NotificationPanelViewController notificationPanelViewController, ShadeExpansionStateManager shadeExpansionStateManager, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, StatusBarWindowStateController statusBarWindowStateController, SecLockIconViewController secLockIconViewController, CentralSurfaces centralSurfaces, NotificationShadeWindowController notificationShadeWindowController, Optional<UnfoldTransitionProgressProvider> optional, KeyguardUnlockAnimationController keyguardUnlockAnimationController, NotificationInsetsController notificationInsetsController, AmbientState ambientState, PulsingGestureListener pulsingGestureListener, KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory, KeyguardTransitionInteractor keyguardTransitionInteractor, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, FeatureFlags featureFlags, Provider provider, SystemClock systemClock, Provider provider2) {
        ?? r9 = new Consumer() { // from class: com.android.systemui.shade.NotificationShadeWindowViewController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
                notificationShadeWindowViewController.getClass();
                notificationShadeWindowViewController.mIsOcclusionTransitionRunning = ((TransitionStep) obj).transitionState == TransitionState.RUNNING;
            }
        };
        this.mLockscreenToDreamingTransition = r9;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mFalsingCollector = falsingCollector;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mView = notificationShadeWindowView;
        this.mNotificationPanelViewController = notificationPanelViewController;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mDepthController = notificationShadeDepthController;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mLockIconViewController = secLockIconViewController;
        secLockIconViewController.init();
        this.mService = centralSurfaces;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.mAmbientState = ambientState;
        this.mNotificationInsetsController = notificationInsetsController;
        this.mIsTrackpadCommonEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.TRACKPAD_GESTURE_COMMON);
        if (LsRune.SECURITY_SIM_PERM_DISABLED) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
        this.mBrightnessMirror = notificationShadeWindowView.findViewById(R.id.brightness_mirror_container);
        new DisableSubpixelTextTransitionListener(notificationShadeWindowView);
        this.mKeyguardBouncerViewModel = keyguardBouncerViewModel;
        this.mPrimaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.mKeyguardBouncerComponentFactory = factory;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            FrameLayout keyguardBouncerContainer = z ? new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), centralSurfaces, sysuiStatusBarStateController) : (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
            if (z) {
                ((CentralSurfacesImpl) centralSurfaces).mBouncerContainer = keyguardBouncerContainer;
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(keyguardBouncerContainer);
            }
            KeyguardBouncerViewBinder.bind(keyguardBouncerContainer, keyguardBouncerViewModel, primaryBouncerToGoneTransitionViewModel, factory);
        }
        JavaAdapterKt.collectFlow(notificationShadeWindowView, keyguardTransitionInteractor.lockscreenToDreamingTransition, r9);
        this.mClock = systemClock;
        ComposeFacade.INSTANCE.getClass();
        pluginLockStarManager.getClass();
        Log.d("LStar|PluginLockStarManager", "onRootViewAttached :: " + notificationShadeWindowView.toString());
        pluginLockStarManager.mRootView = notificationShadeWindowView;
        pluginLockStarManager.mLockStarViewStub = (ViewStub) notificationShadeWindowView.findViewById(R.id.lockstar_view_container_stub);
        DumpManager dumpManager = pluginLockStarManager.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "PluginLockStar", pluginLockStarManager);
        if (pluginLockStarManager.mLockStarViewStub == null) {
            Log.e("LStar|PluginLockStarManager", "Illegal Access. view stub is null");
        }
        pluginLockStarManager.mDisplayLifecycle.addObserver(pluginLockStarManager);
        pluginLockStarManager.mWakefulnessLifecycle.addObserver(pluginLockStarManager);
        pluginLockStarManager.mStatusBarStateController.addCallback(pluginLockStarManager);
        SPluginManager sPluginManager = pluginLockStarManager.mSPluginManager;
        if (sPluginManager != null) {
            sPluginManager.addPluginListener(PluginLockStar.ACTION, pluginLockStarManager, PluginLockStar.class, false, true);
        }
        this.mPanelBlockExpandingHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);
    }

    public final void cancelCurrentTouch() {
        MotionEvent obtain;
        if (this.mTouchActive) {
            ((SystemClockImpl) this.mClock).getClass();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (this.mIsTrackpadCommonEnabled) {
                obtain = MotionEvent.obtain(this.mDownEvent);
                obtain.setDownTime(uptimeMillis);
                obtain.setAction(3);
                obtain.setLocation(0.0f, 0.0f);
            } else {
                obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                obtain.setSource(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL);
            }
            this.mView.dispatchTouchEvent(obtain);
            obtain.recycle();
            this.mTouchCancelled = true;
        }
        AmbientState ambientState = this.mAmbientState;
        if (ambientState.mIsSwipingUp) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = true;
        }
        ambientState.mIsSwipingUp = false;
    }

    public void setDragDownHelper(DragDownHelper dragDownHelper) {
        this.mDragDownHelper = dragDownHelper;
    }

    public final void setExpandAnimationRunning(boolean z) {
        if (this.mExpandAnimationRunning != z) {
            this.mExpandAnimationRunning = z;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.launchingActivityFromNotification = z;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
    }
}
