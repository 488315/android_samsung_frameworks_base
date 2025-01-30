package com.android.systemui.navigationbar;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InsetsFrameProvider;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.LatencyTracker;
import com.android.internal.view.AppearanceRegion;
import com.android.internal.view.RotationPolicy;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.pip.Pip;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.NearestTouchFrame;
import com.android.systemui.navigationbar.buttons.RotationContextButton;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.QuickswitchOrientedNavHandle;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.Recents;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.FloatingRotationButtonPositionCalculator;
import com.android.systemui.shared.rotation.RotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0;
import com.android.systemui.shared.rotation.RotationUtil;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.ViewController;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBar extends ViewController implements CommandQueue.Callbacks {
    public final AccessibilityManager mAccessibilityManager;
    public int mAppearance;
    public final Lazy mAssistManagerLazy;
    public final NavigationBar$$ExternalSyntheticLambda9 mAutoDim;
    public AutoHideController mAutoHideController;
    public final AutoHideController.Factory mAutoHideControllerFactory;
    public final C18441 mAutoHideUiElement;
    public final Optional mBackAnimation;
    public int mBehavior;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public int mCurrentRotation;
    public final DeadZone mDeadZone;
    public final C18536 mDepthListener;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags1;
    public int mDisabledFlags2;
    public int mDisplayId;
    public final DisplayTracker mDisplayTracker;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final NavigationBar$$ExternalSyntheticLambda9 mEnableLayoutTransitions;
    public NavigationBarFrame mFrame;
    public final Handler mHandler;
    public boolean mHomeBlockedThisTouch;
    public Optional mHomeButtonLongPressDurationMs;
    public boolean mImeVisible;
    public final InputMethodManager mInputMethodManager;
    public final Binder mInsetsSourceOwner;
    public boolean mIsOnDefaultDisplay;
    public long mLastLockToAppLongPress;
    public int mLayoutDirection;
    public LightBarController mLightBarController;
    public final LightBarController.Factory mLightBarControllerFactory;
    public Locale mLocale;
    public final LogWrapper mLogWrapper;
    public boolean mLongPressHomeEnabled;
    public final AutoHideController mMainAutoHideController;
    public final LightBarController mMainLightBarController;
    public final MetricsLogger mMetricsLogger;
    public final C184712 mModeChangedListener;
    public final NavBarHelper mNavBarHelper;
    public int mNavBarMode;
    public NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public final int mNavColorSampleMargin;
    public final C18492 mNavbarTaskbarStateUpdater;
    public final NavigationBarTransitions mNavigationBarTransitions;
    public int mNavigationBarWindowState;
    public int mNavigationIconHints;
    public final NavigationModeController mNavigationModeController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeDepthController mNotificationShadeDepthController;
    public final NavigationBar$$ExternalSyntheticLambda10 mOnComputeInternalInsetsListener;
    public final C18525 mOnPropertiesChangedListener;
    public final NavigationBar$$ExternalSyntheticLambda9 mOnVariableDurationHomeLongClick;
    public final OneHandModeUtil mOneHandModeUtil;
    public QuickswitchOrientedNavHandle mOrientationHandle;
    public NavigationBar$$ExternalSyntheticLambda12 mOrientationHandleGlobalLayoutListener;
    public final C18514 mOrientationHandleIntensityListener;
    public WindowManager.LayoutParams mOrientationParams;
    public Rect mOrientedHandleSamplingRegion;
    public final C18503 mOverviewProxyListener;
    public final OverviewProxyService mOverviewProxyService;
    public final Optional mPipOptional;
    public final Optional mRecentsOptional;
    public final RegionSamplingHelper mRegionSamplingHelper;
    public final Rect mSamplingBounds;
    public final Bundle mSavedState;
    public boolean mScreenPinningActive;
    public boolean mShowOrientedHandleForImmersiveMode;
    public int mStartingQuickSwitchRotation;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public final ViewRootImplSurfaceChangedCallbackC18558 mSurfaceChangedCallback;
    public final SysUiState mSysUiFlagsContainer;
    public final TaskStackChangeListeners mTaskStackChangeListeners;
    public final C18569 mTaskStackListener;
    public final Optional mTelecomManagerOptional;
    public final C184813 mTouchHandler;
    public boolean mTransientShown;
    public boolean mTransientShownFromGestureOnSystemBar;
    public int mTransitionMode;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserContextProvider mUserContextProvider;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final C18547 mWakefulnessObserver;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum NavBarActionEvent implements UiEventLogger.UiEventEnum {
        NAVBAR_ASSIST_LONGPRESS(550);

        private final int mId;

        NavBarActionEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda10] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.navigationbar.NavigationBar$9] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.navigationbar.NavigationBar$12, com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener] */
    /* JADX WARN: Type inference failed for: r7v10, types: [com.android.systemui.navigationbar.NavigationBar$5] */
    /* JADX WARN: Type inference failed for: r7v11, types: [com.android.systemui.navigationbar.NavigationBar$6] */
    /* JADX WARN: Type inference failed for: r7v12, types: [com.android.systemui.navigationbar.NavigationBar$7] */
    /* JADX WARN: Type inference failed for: r7v13, types: [com.android.systemui.navigationbar.NavigationBar$8] */
    /* JADX WARN: Type inference failed for: r7v14, types: [com.android.systemui.navigationbar.NavigationBar$13] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.navigationbar.NavigationBar$1] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.systemui.navigationbar.NavigationBar$2] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.systemui.navigationbar.NavigationBar$3] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.navigationbar.NavigationBar$4] */
    public NavigationBar(NavigationBarView navigationBarView, NavigationBarFrame navigationBarFrame, Bundle bundle, Context context, WindowManager windowManager, Lazy lazy, AccessibilityManager accessibilityManager, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, SysUiState sysUiState, UserTracker userTracker, CommandQueue commandQueue, Optional<Pip> optional, Optional<Recents> optional2, Lazy lazy2, ShadeController shadeController, NotificationRemoteInputManager notificationRemoteInputManager, NotificationShadeDepthController notificationShadeDepthController, Handler handler, Executor executor, Executor executor2, UiEventLogger uiEventLogger, NavBarHelper navBarHelper, LightBarController lightBarController, LightBarController.Factory factory, AutoHideController autoHideController, AutoHideController.Factory factory2, Optional<TelecomManager> optional3, InputMethodManager inputMethodManager, DeadZone deadZone, DeviceConfigProxy deviceConfigProxy, NavigationBarTransitions navigationBarTransitions, Optional<BackAnimationController.BackAnimationImpl> optional4, UserContextProvider userContextProvider, WakefulnessLifecycle wakefulnessLifecycle, TaskStackChangeListeners taskStackChangeListeners, DisplayTracker displayTracker, LogWrapper logWrapper) {
        super(navigationBarView);
        this.mNavigationBarWindowState = 0;
        this.mNavigationIconHints = 0;
        this.mNavBarMode = 0;
        this.mStartingQuickSwitchRotation = -1;
        this.mSamplingBounds = new Rect();
        this.mInsetsSourceOwner = new Binder();
        this.mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.NavigationBar.1
            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void hide() {
                NavigationBar navigationBar = NavigationBar.this;
                if (navigationBar.mTransientShown) {
                    navigationBar.mTransientShown = false;
                    navigationBar.mTransientShownFromGestureOnSystemBar = false;
                    navigationBar.handleTransientChanged();
                }
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean isVisible() {
                return NavigationBar.this.mTransientShown;
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean shouldHideOnTouch() {
                return !NavigationBar.this.mNotificationRemoteInputManager.isRemoteInputActive();
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void synchronizeState() {
                NavigationBar.this.checkNavBarModes();
            }
        };
        this.mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.NavigationBar.2
            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateAccessibilityGestureDetected(boolean z) {
                NavigationBar navigationBar = NavigationBar.this;
                View view = navigationBar.mView;
                if (view == null) {
                    return;
                }
                ((NavigationBarView) view).setSlippery(!z);
                NavigationBarView navigationBarView2 = (NavigationBarView) navigationBar.mView;
                for (int i = 0; i < navigationBarView2.mButtonDispatchers.size(); i++) {
                    ArrayList arrayList = ((ButtonDispatcher) navigationBarView2.mButtonDispatchers.valueAt(i)).mViews;
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (arrayList.get(i2) instanceof ButtonInterface) {
                            ((ButtonInterface) arrayList.get(i2)).abortCurrentGestureByA11yGesture(z);
                        }
                    }
                }
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateAccessibilityServicesState() {
                NavigationBar.this.updateAccessibilityStateFlags();
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateAssistantAvailable(boolean z, boolean z2) {
                NavigationBar navigationBar = NavigationBar.this;
                if (navigationBar.mView == null) {
                    return;
                }
                navigationBar.mLongPressHomeEnabled = z2;
                IOverviewProxy iOverviewProxy = navigationBar.mOverviewProxyService.mOverviewProxy;
                if (iOverviewProxy != null) {
                    try {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onAssistantAvailable(z, z2);
                    } catch (RemoteException unused) {
                        Log.w("NavigationBar", "Unable to send assistant availability data to launcher");
                    }
                }
                navigationBar.reconfigureHomeLongClick();
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateRotationWatcherState(int i) {
                View view;
                boolean z = BasicRune.NAVBAR_ENABLED;
                NavigationBar navigationBar = NavigationBar.this;
                if (z) {
                    ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnRotationChanged(i), navigationBar.mDisplayId);
                }
                if (!navigationBar.mIsOnDefaultDisplay || (view = navigationBar.mView) == null) {
                    return;
                }
                ((NavigationBarView) view).mRotationButtonController.onRotationWatcherChanged(i);
                if (((NavigationBarView) navigationBar.mView).mCurrentRotation != i) {
                    navigationBar.repositionNavigationBar(i);
                }
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateWallpaperVisibility(boolean z) {
                NavigationBarTransitions navigationBarTransitions2 = NavigationBar.this.mNavigationBarTransitions;
                navigationBarTransitions2.mWallpaperVisible = z;
                navigationBarTransitions2.applyLightsOut(true, false);
            }
        };
        this.mOverviewProxyListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.navigationbar.NavigationBar.3
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onConnectionChanged(boolean z) {
                NavigationBar navigationBar = NavigationBar.this;
                NavigationBarView navigationBarView2 = (NavigationBarView) navigationBar.mView;
                OverviewProxyService overviewProxyService2 = navigationBar.mOverviewProxyService;
                navigationBarView2.mOverviewProxyEnabled = overviewProxyService2.mIsEnabled;
                navigationBarView2.mShowSwipeUpUi = overviewProxyService2.shouldShowSwipeUpUI();
                navigationBarView2.updateStates();
                navigationBar.updateScreenPinningGestures();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onHomeRotationEnabled(boolean z) {
                RotationButtonController rotationButtonController = ((NavigationBarView) NavigationBar.this.mView).mRotationButtonController;
                rotationButtonController.mHomeRotationEnabled = z;
                if (!rotationButtonController.mIsRecentsAnimationRunning || z) {
                    return;
                }
                rotationButtonController.setRotateSuggestionButtonState(false, true);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onOverviewShown() {
                ((NavigationBarView) NavigationBar.this.mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onPrioritizedRotation(int i) {
                boolean z = BasicRune.NAVBAR_ENABLED;
                NavigationBar navigationBar = NavigationBar.this;
                if (z) {
                    navigationBar.mLogWrapper.m99dp("NavigationBar", String.format("onPrioritizedRotation rotation : %d", Integer.valueOf(i)));
                }
                navigationBar.mStartingQuickSwitchRotation = i;
                if (i == -1) {
                    navigationBar.mShowOrientedHandleForImmersiveMode = false;
                }
                navigationBar.orientSecondaryHomeHandle();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onTaskbarStatusUpdated(boolean z, boolean z2) {
                FloatingRotationButton floatingRotationButton = ((NavigationBarView) NavigationBar.this.mView).mFloatingRotationButton;
                floatingRotationButton.mIsTaskbarVisible = z;
                floatingRotationButton.mIsTaskbarStashed = z2;
                if (floatingRotationButton.mIsShowing) {
                    FloatingRotationButtonPositionCalculator.Position calculatePosition = floatingRotationButton.mPositionCalculator.calculatePosition(floatingRotationButton.mDisplayRotation, z, z2);
                    FloatingRotationButtonPositionCalculator.Position position = floatingRotationButton.mPosition;
                    if (calculatePosition.translationX == position.translationX) {
                        if (calculatePosition.translationY == position.translationY) {
                            return;
                        }
                    }
                    floatingRotationButton.updateTranslation(calculatePosition, true);
                    floatingRotationButton.mPosition = calculatePosition;
                }
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onToggleRecentApps() {
                ((NavigationBarView) NavigationBar.this.mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }
        };
        this.mOrientationHandleIntensityListener = new NavigationBarTransitions.DarkIntensityListener() { // from class: com.android.systemui.navigationbar.NavigationBar.4
            @Override // com.android.systemui.navigationbar.NavigationBarTransitions.DarkIntensityListener
            public final void onDarkIntensity(float f) {
                QuickswitchOrientedNavHandle quickswitchOrientedNavHandle;
                if (!BasicRune.NAVBAR_AOSP_BUG_FIX || (quickswitchOrientedNavHandle = NavigationBar.this.mOrientationHandle) == null) {
                    return;
                }
                quickswitchOrientedNavHandle.setDarkIntensity(f);
            }
        };
        this.mAutoDim = new NavigationBar$$ExternalSyntheticLambda9(this, 0);
        this.mEnableLayoutTransitions = new NavigationBar$$ExternalSyntheticLambda9(this, 1);
        this.mOnVariableDurationHomeLongClick = new NavigationBar$$ExternalSyntheticLambda9(this, 2);
        this.mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.navigationbar.NavigationBar.5
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("home_button_long_press_duration_ms")) {
                    NavigationBar.this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(properties.getLong("home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda8(1));
                    NavigationBar navigationBar = NavigationBar.this;
                    if (navigationBar.mView != null) {
                        navigationBar.reconfigureHomeLongClick();
                    }
                }
            }
        };
        this.mDepthListener = new Object(this) { // from class: com.android.systemui.navigationbar.NavigationBar.6
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.navigationbar.NavigationBar.7
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                NavigationBar navigationBar = NavigationBar.this;
                ((NavigationBarView) navigationBar.mView).updateNavButtonIcons();
                ((NavigationBarView) navigationBar.mView).onScreenStateChanged(false);
                navigationBar.mRegionSamplingHelper.stop();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                NavigationBar navigationBar = NavigationBar.this;
                ((NavigationBarView) navigationBar.mView).updateNavButtonIcons();
                ((NavigationBarView) navigationBar.mView).onScreenStateChanged(true);
                if ((BasicRune.NAVBAR_ENABLED && navigationBar.mNavBarStateManager.isGestureMode()) || Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode)) {
                    navigationBar.mRegionSamplingHelper.start(navigationBar.mSamplingBounds);
                }
            }
        };
        this.mSurfaceChangedCallback = new ViewRootImpl.SurfaceChangedCallback() { // from class: com.android.systemui.navigationbar.NavigationBar.8
            public final void surfaceCreated(SurfaceControl.Transaction transaction) {
                NavigationBar.this.notifyNavigationBarSurface();
            }

            public final void surfaceDestroyed() {
                NavigationBar.this.notifyNavigationBarSurface();
            }

            public final void surfaceReplaced(SurfaceControl.Transaction transaction) {
                NavigationBar.this.notifyNavigationBarSurface();
            }
        };
        this.mScreenPinningActive = false;
        this.mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.NavigationBar.9
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onLockTaskModeChanged(int i) {
                boolean z = i == 2;
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.mScreenPinningActive = z;
                SysUiState sysUiState2 = navigationBar.mSysUiFlagsContainer;
                sysUiState2.setFlag(1L, z);
                sysUiState2.commitUpdate(navigationBar.mDisplayId);
                ((NavigationBarView) navigationBar.mView).mScreenPinningActive = navigationBar.mScreenPinningActive;
                navigationBar.updateScreenPinningGestures();
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.NavigationBar.11
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                NavigationBar.this.updateAccessibilityStateFlags();
            }
        };
        ?? r6 = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.navigationbar.NavigationBar.12
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                ButtonDispatcher accessibilityButton;
                NavigationBar navigationBar = NavigationBar.this;
                boolean z = navigationBar.mNavBarMode != i;
                navigationBar.mNavBarMode = i;
                boolean z2 = BasicRune.NAVBAR_ENABLED;
                NavBarStore navBarStore = navigationBar.mNavBarStore;
                if (z2) {
                    NavBarStateManager.States states = navigationBar.mNavBarStateManager.states;
                    ((NavBarStoreImpl) navBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnNavBarConfigChanged(states.canMove, states.supportPhoneLayoutProvider, states.imeDownButtonForAllRotation, i), navigationBar.mDisplayId);
                }
                boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
                NavigationBarTransitions navigationBarTransitions2 = navigationBar.mNavigationBarTransitions;
                if (!isGesturalMode && navigationBarTransitions2 != null) {
                    BarTransitions.BarBackgroundDrawable barBackgroundDrawable = navigationBarTransitions2.mBarBackground;
                    barBackgroundDrawable.mOverrideAlpha = 1.0f;
                    barBackgroundDrawable.invalidateSelf();
                }
                if (z) {
                    if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle = navigationBar.mOrientationHandle;
                        if (quickswitchOrientedNavHandle != null && quickswitchOrientedNavHandle.isAttachedToWindow()) {
                            navigationBar.resetSecondaryHandle();
                            ((ArrayList) navigationBarTransitions2.mDarkIntensityListeners).remove(navigationBar.mOrientationHandleIntensityListener);
                            navigationBar.mWindowManager.removeView(navigationBar.mOrientationHandle);
                            navigationBar.mOrientationHandle.getViewTreeObserver().removeOnGlobalLayoutListener(navigationBar.mOrientationHandleGlobalLayoutListener);
                            navigationBar.mOrientationHandle = null;
                        }
                        navigationBar.initSecondaryHomeHandleForRotation();
                    }
                    if (z2 && (accessibilityButton = ((NavigationBarView) navigationBar.mView).getAccessibilityButton()) != null && accessibilityButton.getVisibility() == 0) {
                        ((NavBarStoreImpl) navBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup());
                    }
                }
                navigationBar.updateScreenPinningGestures();
                if (!navigationBar.canShowSecondaryHandle()) {
                    navigationBar.resetSecondaryHandle();
                }
                navigationBar.setNavBarMode(i);
                NavigationBarView navigationBarView2 = (NavigationBarView) navigationBar.mView;
                navigationBarView2.mShowSwipeUpUi = navigationBar.mOverviewProxyService.shouldShowSwipeUpUI();
                navigationBarView2.updateStates();
            }
        };
        this.mModeChangedListener = r6;
        this.mTouchHandler = new Gefingerpoken() { // from class: com.android.systemui.navigationbar.NavigationBar.13
            public boolean mDeadZoneConsuming;

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                NavigationBar navigationBar = NavigationBar.this;
                if (QuickStepContract.isGesturalMode(navigationBar.mNavBarMode) && navigationBar.mImeVisible && motionEvent.getAction() == 0) {
                    SysUiStatsLog.write(304, (int) motionEvent.getX(), (int) motionEvent.getY());
                }
                return shouldDeadZoneConsumeTouchEvents(motionEvent);
            }

            @Override // com.android.systemui.Gefingerpoken
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                shouldDeadZoneConsumeTouchEvents(motionEvent);
                return false;
            }

            public final boolean shouldDeadZoneConsumeTouchEvents(MotionEvent motionEvent) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mDeadZoneConsuming = false;
                }
                NavigationBar navigationBar = NavigationBar.this;
                if (!navigationBar.mDeadZone.onTouchEvent(motionEvent) && !this.mDeadZoneConsuming) {
                    return false;
                }
                if (actionMasked == 0) {
                    ((NavigationBarView) navigationBar.mView).setSlippery(true);
                    this.mDeadZoneConsuming = true;
                } else if (actionMasked == 1 || actionMasked == 3) {
                    ((NavigationBarView) navigationBar.mView).updateSlippery();
                    this.mDeadZoneConsuming = false;
                }
                return true;
            }
        };
        this.mFrame = navigationBarFrame;
        this.mContext = context;
        this.mSavedState = bundle;
        this.mWindowManager = windowManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mStatusBarStateController = statusBarStateController;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mSysUiFlagsContainer = sysUiState;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mOverviewProxyService = overviewProxyService;
        this.mNavigationModeController = navigationModeController;
        this.mUserTracker = userTracker;
        this.mCommandQueue = commandQueue;
        this.mPipOptional = optional;
        this.mRecentsOptional = optional2;
        this.mDeadZone = deadZone;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mNavigationBarTransitions = navigationBarTransitions;
        this.mBackAnimation = optional4;
        this.mHandler = handler;
        this.mUiEventLogger = uiEventLogger;
        this.mNavBarHelper = navBarHelper;
        this.mNotificationShadeDepthController = notificationShadeDepthController;
        this.mMainLightBarController = lightBarController;
        this.mLightBarControllerFactory = factory;
        this.mMainAutoHideController = autoHideController;
        this.mAutoHideControllerFactory = factory2;
        this.mTelecomManagerOptional = optional3;
        this.mInputMethodManager = inputMethodManager;
        this.mUserContextProvider = userContextProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mTaskStackChangeListeners = taskStackChangeListeners;
        this.mDisplayTracker = displayTracker;
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z) {
            navBarHelper.getClass();
            this.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context) : navBarHelper.mEdgeBackGestureHandler;
        } else {
            Context context2 = navBarHelper.mContext;
            this.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context2.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context2) : navBarHelper.mEdgeBackGestureHandler;
        }
        this.mNavColorSampleMargin = getResources().getDimensionPixelSize(R.dimen.navigation_handle_sample_horizontal_margin);
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda10
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.getClass();
                if (BasicRune.NAVBAR_GESTURE) {
                    if (((NavigationBarView) navigationBar.mView).needTouchableInsetsFrame()) {
                        internalInsetsInfo.setTouchableInsets(0);
                        return;
                    }
                } else if (!navigationBar.mEdgeBackGestureHandler.isHandlingGestures()) {
                    if (!navigationBar.mImeVisible) {
                        internalInsetsInfo.setTouchableInsets(0);
                        return;
                    } else if (!((NavigationBarView) navigationBar.mView).isImeRenderingNavButtons()) {
                        internalInsetsInfo.setTouchableInsets(0);
                        return;
                    }
                }
                internalInsetsInfo.setTouchableInsets(3);
                internalInsetsInfo.touchableRegion.set(navigationBar.getButtonLocations(false, false, false));
            }
        };
        RegionSamplingHelper regionSamplingHelper = new RegionSamplingHelper(this.mView, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.systemui.navigationbar.NavigationBar.10
            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final Rect getSampledRegion() {
                int i;
                int i2;
                Rect rect;
                NavigationBar navigationBar = NavigationBar.this;
                Rect rect2 = navigationBar.mOrientedHandleSamplingRegion;
                if (rect2 != null) {
                    return rect2;
                }
                Rect rect3 = navigationBar.mSamplingBounds;
                rect3.setEmpty();
                boolean z2 = BasicRune.NAVBAR_ENABLED;
                int i3 = navigationBar.mNavColorSampleMargin;
                int i4 = 0;
                if (z2) {
                    View view = NavigationModeUtil.isBottomGesture(navigationBar.mNavBarMode) ? ((NavigationBarView) navigationBar.mView).getHintView().mCurrentView : ((NavigationBarView) navigationBar.mView).getHomeHandle().mCurrentView;
                    if (view != null) {
                        int[] iArr = new int[2];
                        view.getLocationOnScreen(iArr);
                        Point point = new Point();
                        Context context3 = navigationBar.mContext;
                        context3.getDisplay().getRealSize(point);
                        int dimensionPixelSize = navigationBar.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height);
                        boolean z3 = navigationBar.mNavBarStateManager.states.canMove;
                        int i5 = iArr[0];
                        int i6 = iArr[1];
                        int rotation = context3.getDisplay().getRotation();
                        int width = view.getWidth();
                        int height = view.getHeight();
                        if (z3) {
                            if (rotation == 1) {
                                int i7 = point.x;
                                int i8 = i7 - dimensionPixelSize;
                                i2 = i6 - i3;
                                i = i6 + height + i3;
                                dimensionPixelSize = i7;
                                i4 = i8;
                            } else if (rotation == 3) {
                                i2 = i6 - i3;
                                i = i6 + height + i3;
                            } else {
                                i = point.y;
                                i2 = i - dimensionPixelSize;
                                i4 = i5 - i3;
                                dimensionPixelSize = i5 + width + i3;
                            }
                            rect = new Rect(i4, i2, dimensionPixelSize, i);
                        } else {
                            int i9 = point.y;
                            rect = new Rect(i5 - i3, i9 - dimensionPixelSize, i5 + width + i3, i9);
                        }
                        if (!rect.equals(rect3)) {
                            rect3.set(rect);
                        }
                        rect3.set(navigationBar.mOneHandModeUtil.getRegionSamplingBounds(rect3));
                    }
                } else {
                    View view2 = ((NavigationBarView) navigationBar.mView).getHomeHandle().mCurrentView;
                    if (view2 != null) {
                        int[] iArr2 = new int[2];
                        view2.getLocationOnScreen(iArr2);
                        Point point2 = new Point();
                        view2.getContext().getDisplay().getRealSize(point2);
                        int i10 = iArr2[0] - i3;
                        int i11 = point2.y;
                        NavigationBarView navigationBarView2 = (NavigationBarView) navigationBar.mView;
                        rect3.set(new Rect(i10, i11 - (navigationBarView2.mIsVertical ? navigationBarView2.getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_width_low_ram) : navigationBarView2.getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram)), view2.getWidth() + iArr2[0] + i3, point2.y));
                    }
                }
                return rect3;
            }

            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final boolean isSamplingEnabled() {
                NavBarStateManager navBarStateManager;
                boolean z2 = BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY;
                NavigationBar navigationBar = NavigationBar.this;
                return (!z2 || (navBarStateManager = navigationBar.mNavBarStateManager) == null) ? (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && navigationBar.mContext.getDisplayId() == 1) ? QuickStepContract.isGesturalMode(navigationBar.mNavBarMode) : Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode) : !navBarStateManager.isTaskBarEnabled(false) && Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode);
            }

            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final void onRegionDarknessChanged(boolean z2) {
                NavigationBar.this.mNavigationBarTransitions.mLightTransitionsController.setIconsDark(!z2, true);
            }

            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final void onUpdateSamplingListener(boolean z2) {
                NavigationBar navigationBar = NavigationBar.this;
                ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnUpdateRegionSamplingListener(z2));
            }
        }, executor, executor2);
        this.mRegionSamplingHelper = regionSamplingHelper;
        if (z) {
            SamsungNavigationBarProxy.Companion.getClass();
            SamsungNavigationBarProxy samsungNavigationBarProxy = SamsungNavigationBarProxy.INSTANCE;
            if (samsungNavigationBarProxy == null) {
                samsungNavigationBarProxy = new SamsungNavigationBarProxy();
                SamsungNavigationBarProxy.INSTANCE = samsungNavigationBarProxy;
            }
            regionSamplingHelper.mBarProxy = samsungNavigationBarProxy;
        }
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mBgExecutor = executor2;
        navigationBarView2.mEdgeBackGestureHandler = this.mEdgeBackGestureHandler;
        navigationBarView2.mDisplayTracker = displayTracker;
        this.mNavBarMode = navigationModeController.addListener(r6);
        if (z) {
            this.mCurrentRotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
            this.mNavBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
            this.mLogWrapper = logWrapper;
            this.mOneHandModeUtil = new OneHandModeUtil((SettingsHelper) Dependency.get(SettingsHelper.class));
        }
    }

    public static void resetButtonListener(ButtonDispatcher buttonDispatcher) {
        if (buttonDispatcher == null) {
            return;
        }
        buttonDispatcher.setOnClickListener(null);
        buttonDispatcher.setOnLongClickListener(null);
        buttonDispatcher.setOnTouchListener(null);
    }

    public static void updateButtonLocation(Region region, Map map, ButtonDispatcher buttonDispatcher, boolean z, boolean z2) {
        View view;
        if (buttonDispatcher == null || (view = buttonDispatcher.mCurrentView) == null || !buttonDispatcher.isVisible()) {
            return;
        }
        if (z2) {
            HashMap hashMap = (HashMap) map;
            if (hashMap.containsKey(view)) {
                region.op((Rect) hashMap.get(view), Region.Op.UNION);
                return;
            }
        }
        updateButtonLocation(region, view, z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void abortTransient(int i, int i2) {
        if (i == this.mDisplayId && (WindowInsets.Type.navigationBars() & i2) != 0 && this.mTransientShown) {
            this.mTransientShown = false;
            this.mTransientShownFromGestureOnSystemBar = false;
            handleTransientChanged();
        }
    }

    public final boolean canShowSecondaryHandle() {
        return this.mNavBarMode == 2 && this.mOrientationHandle != null && BasicRune.NAVBAR_GESTURE && this.mNavBarStateManager.isGestureHintEnabled();
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0027, code lost:
    
        if (r3.mNavigationBarWindowState != 2) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkNavBarModes() {
        boolean z;
        if (!BasicRune.NAVBAR_ENABLED) {
            z = true;
            if (((Boolean) ((Optional) this.mCentralSurfacesOptionalLazy.get()).map(new NavigationBar$$ExternalSyntheticLambda1(1 == true ? 1 : 0)).orElse(Boolean.FALSE)).booleanValue()) {
            }
        }
        z = false;
        this.mNavigationBarTransitions.transitionTo(this.mTransitionMode, z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        int i4;
        if (i != this.mDisplayId) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnSetDisableFlags(i2, i3));
        }
        int i5 = 56623104 & i2;
        if (i5 != this.mDisabledFlags1) {
            this.mDisabledFlags1 = i5;
            ((NavigationBarView) this.mView).setDisabledFlags(i2, this.mSysUiFlagsContainer);
            updateScreenPinningGestures();
            ((AssistManager) this.mAssistManagerLazy.get()).mDisabledFlags = i2;
        }
        if (!this.mIsOnDefaultDisplay || (i4 = i3 & 16) == this.mDisabledFlags2) {
            return;
        }
        this.mDisabledFlags2 = i4;
        setDisabled2Flags(i4);
    }

    public final WindowManager.LayoutParams getBarLayoutParams(int i) {
        WindowManager.LayoutParams barLayoutParamsForRotation = getBarLayoutParamsForRotation(i);
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i2 = 0; i2 <= 3; i2++) {
            barLayoutParamsForRotation.paramsForRotation[i2] = getBarLayoutParamsForRotation(i2);
        }
        return barLayoutParamsForRotation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowManager.LayoutParams getBarLayoutParamsForRotation(int i) {
        Context createContextAsUser;
        boolean z;
        boolean z2;
        int i2;
        Object[] objArr;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int dimensionPixelSize;
        WindowManager.LayoutParams layoutParams;
        char c;
        UserContextProvider userContextProvider = this.mUserContextProvider;
        Context context = this.mContext;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userContextProvider;
        synchronized (userTrackerImpl.mutex) {
            createContextAsUser = context.createContextAsUser(userTrackerImpl.getUserHandle(), 0);
        }
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null && windowManager.getCurrentWindowMetrics() != null) {
            Rect bounds = this.mWindowManager.getCurrentWindowMetrics().getBounds();
            if (bounds.width() == bounds.height() || !createContextAsUser.getResources().getBoolean(android.R.bool.config_letterboxIsEducationEnabled)) {
                z = false;
                z2 = BasicRune.NAVBAR_ENABLED;
                if (z2) {
                    NavBarStoreAction.NavBarLayoutInfo navBarLayoutInfo = (NavBarStoreAction.NavBarLayoutInfo) ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.GetBarLayoutParams(i), this.mContext.getDisplayId(), null);
                    if (navBarLayoutInfo != null) {
                        i3 = navBarLayoutInfo.height;
                        i4 = navBarLayoutInfo.insetHeight;
                        i5 = navBarLayoutInfo.insetWidth;
                        i6 = navBarLayoutInfo.width;
                        i2 = navBarLayoutInfo.gravity;
                        objArr = true;
                        if (z2 || objArr == false) {
                            if (z) {
                                if (i != -1 && i != 0) {
                                    if (i == 1) {
                                        i6 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_feedback_size);
                                        i2 = 5;
                                    } else if (i != 2) {
                                        if (i == 3) {
                                            i7 = i3;
                                            dimensionPixelSize = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_feedback_size);
                                            i2 = 3;
                                            layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, i7, 2019, 545521704, -3);
                                            layoutParams.gravity = i2;
                                            if (z2) {
                                                Binder binder = this.mInsetsSourceOwner;
                                                InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(binder, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) null)});
                                                EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
                                                if (i4 != -1 && !edgeBackGestureHandler.mIsButtonForcedVisible) {
                                                    insetsSizeOverrides.setInsetsSize(Insets.of(0, 0, 0, i4));
                                                }
                                                insetsSizeOverrides.setFlags(!createContextAsUser.getResources().getBoolean(android.R.bool.config_letterboxIsHorizontalReachabilityEnabled) ? 1 : 0, 1);
                                                InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(binder, 0, WindowInsets.Type.tappableElement());
                                                if (createContextAsUser.getResources().getBoolean(android.R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled)) {
                                                    insetsFrameProvider.setInsetsSize(Insets.NONE);
                                                }
                                                int dimensionPixelSize2 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_conversation_header_separating_margin);
                                                boolean isHandlingGestures = edgeBackGestureHandler.isHandlingGestures();
                                                InsetsFrameProvider insetsFrameProvider2 = new InsetsFrameProvider(binder, 0, WindowInsets.Type.mandatorySystemGestures());
                                                if (isHandlingGestures) {
                                                    insetsFrameProvider2.setInsetsSize(Insets.of(0, 0, 0, dimensionPixelSize2));
                                                }
                                                int i8 = isHandlingGestures ? edgeBackGestureHandler.mEdgeWidthLeft : 0;
                                                int i9 = isHandlingGestures ? edgeBackGestureHandler.mEdgeWidthRight : 0;
                                                layoutParams.providedInsets = new InsetsFrameProvider[]{insetsSizeOverrides, insetsFrameProvider, insetsFrameProvider2, new InsetsFrameProvider(binder, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(i8, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(i8, 0, 0, 0)), new InsetsFrameProvider(binder, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, i9, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, i9, 0))};
                                            } else {
                                                EventTypeFactory.EventType.GetImeInsets getImeInsets = new EventTypeFactory.EventType.GetImeInsets(i4, i5, i, z);
                                                int i10 = this.mDisplayId;
                                                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.mNavBarStore;
                                                Insets insets = (Insets) navBarStoreImpl.handleEvent(this, getImeInsets, i10, null);
                                                Binder binder2 = this.mInsetsSourceOwner;
                                                InsetsFrameProvider insetsSizeOverrides2 = new InsetsFrameProvider(binder2, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, insets)});
                                                insetsSizeOverrides2.setInsetsSize((Insets) navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.GetNavBarInsets(i4, i5, i), this.mDisplayId, null));
                                                insetsSizeOverrides2.setFlags(!createContextAsUser.getResources().getBoolean(android.R.bool.config_letterboxIsHorizontalReachabilityEnabled) ? 1 : 0, 1);
                                                InsetsFrameProvider insetsFrameProvider3 = new InsetsFrameProvider(binder2, 0, WindowInsets.Type.tappableElement());
                                                if (createContextAsUser.getResources().getBoolean(android.R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled)) {
                                                    insetsFrameProvider3.setInsetsSize(Insets.NONE);
                                                }
                                                InsetsFrameProvider insetsFrameProvider4 = new InsetsFrameProvider(binder2, 0, WindowInsets.Type.mandatorySystemGestures());
                                                insetsFrameProvider4.setInsetsSize((Insets) navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.GetMandatoryInsets(i, z), this.mDisplayId, null));
                                                EdgeBackGestureHandler edgeBackGestureHandler2 = this.mEdgeBackGestureHandler;
                                                edgeBackGestureHandler2.updateCurrentUserResources();
                                                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                                                Context context2 = this.mContext;
                                                int i11 = Settings.Global.getInt(context2.getContentResolver(), "navigation_bar_gesture_while_hidden", 0);
                                                int i12 = Settings.Global.getInt(context2.getContentResolver(), "navigation_bar_gesture_detail_type", !BasicRune.supportSamsungGesturalModeAsDefault() ? 1 : 0);
                                                if (i11 != 0) {
                                                    if (i12 == 0) {
                                                        c = 3;
                                                    } else if (i12 == 1) {
                                                        c = 2;
                                                    }
                                                    boolean z3 = c != 2;
                                                    int i13 = !z3 ? edgeBackGestureHandler2.mEdgeWidthLeft : 0;
                                                    int i14 = !z3 ? edgeBackGestureHandler2.mEdgeWidthRight : 0;
                                                    layoutParams.providedInsets = new InsetsFrameProvider[]{insetsSizeOverrides2, insetsFrameProvider3, insetsFrameProvider4, new InsetsFrameProvider(binder2, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(i13, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(i13, 0, 0, 0)), new InsetsFrameProvider(binder2, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, i14, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, i14, 0))};
                                                }
                                                c = 0;
                                                if (c != 2) {
                                                }
                                                if (!z3) {
                                                }
                                                if (!z3) {
                                                }
                                                layoutParams.providedInsets = new InsetsFrameProvider[]{insetsSizeOverrides2, insetsFrameProvider3, insetsFrameProvider4, new InsetsFrameProvider(binder2, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(i13, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(i13, 0, 0, 0)), new InsetsFrameProvider(binder2, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, i14, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, i14, 0))};
                                            }
                                            layoutParams.token = new Binder();
                                            if (z2) {
                                                layoutParams.accessibilityTitle = createContextAsUser.getString(R.string.nav_bar);
                                            } else {
                                                layoutParams.accessibilityTitle = createContextAsUser.getString(R.string.samsung_nav_bar);
                                            }
                                            layoutParams.privateFlags |= 16777216;
                                            if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || !this.mNavBarStateManager.supportLargeCoverScreenNavBar()) {
                                                layoutParams.privateFlags |= 4096;
                                            }
                                            layoutParams.layoutInDisplayCutoutMode = 3;
                                            layoutParams.windowAnimations = 0;
                                            layoutParams.setTitle("NavigationBar" + createContextAsUser.getDisplayId());
                                            layoutParams.setFitInsetsTypes(0);
                                            layoutParams.setTrustedOverlay();
                                            return layoutParams;
                                        }
                                    }
                                }
                                i3 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
                                i4 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram);
                            } else {
                                i3 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
                                i4 = createContextAsUser.getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram);
                            }
                        }
                        i7 = i3;
                        dimensionPixelSize = i6;
                        layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, i7, 2019, 545521704, -3);
                        layoutParams.gravity = i2;
                        if (z2) {
                        }
                        layoutParams.token = new Binder();
                        if (z2) {
                        }
                        layoutParams.privateFlags |= 16777216;
                        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                        }
                        layoutParams.privateFlags |= 4096;
                        layoutParams.layoutInDisplayCutoutMode = 3;
                        layoutParams.windowAnimations = 0;
                        layoutParams.setTitle("NavigationBar" + createContextAsUser.getDisplayId());
                        layoutParams.setFitInsetsTypes(0);
                        layoutParams.setTrustedOverlay();
                        return layoutParams;
                    }
                }
                i2 = 80;
                objArr = false;
                i3 = -1;
                i4 = -1;
                i5 = -1;
                i6 = -1;
                if (z2) {
                }
                if (z) {
                }
                i7 = i3;
                dimensionPixelSize = i6;
                layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, i7, 2019, 545521704, -3);
                layoutParams.gravity = i2;
                if (z2) {
                }
                layoutParams.token = new Binder();
                if (z2) {
                }
                layoutParams.privateFlags |= 16777216;
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                }
                layoutParams.privateFlags |= 4096;
                layoutParams.layoutInDisplayCutoutMode = 3;
                layoutParams.windowAnimations = 0;
                layoutParams.setTitle("NavigationBar" + createContextAsUser.getDisplayId());
                layoutParams.setFitInsetsTypes(0);
                layoutParams.setTrustedOverlay();
                return layoutParams;
            }
        }
        z = true;
        z2 = BasicRune.NAVBAR_ENABLED;
        if (z2) {
        }
        i2 = 80;
        objArr = false;
        i3 = -1;
        i4 = -1;
        i5 = -1;
        i6 = -1;
        if (z2) {
        }
        if (z) {
        }
        i7 = i3;
        dimensionPixelSize = i6;
        layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, i7, 2019, 545521704, -3);
        layoutParams.gravity = i2;
        if (z2) {
        }
        layoutParams.token = new Binder();
        if (z2) {
        }
        layoutParams.privateFlags |= 16777216;
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
        }
        layoutParams.privateFlags |= 4096;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.windowAnimations = 0;
        layoutParams.setTitle("NavigationBar" + createContextAsUser.getDisplayId());
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }

    public final Region getButtonLocations(boolean z, boolean z2, boolean z3) {
        if (z3 && !z2) {
            z3 = false;
        }
        Region region = new Region();
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        NearestTouchFrame nearestTouchFrame = (NearestTouchFrame) (navigationBarView.mIsVertical ? navigationBarView.mNavigationInflaterView.mVertical : navigationBarView.mNavigationInflaterView.mHorizontal).findViewById(R.id.nav_buttons);
        nearestTouchFrame.getClass();
        HashMap hashMap = new HashMap(((HashMap) nearestTouchFrame.mTouchableRegions).size());
        nearestTouchFrame.getLocationOnScreen(nearestTouchFrame.mTmpInt);
        for (Map.Entry entry : ((HashMap) nearestTouchFrame.mTouchableRegions).entrySet()) {
            View view = (View) entry.getKey();
            Rect rect = new Rect((Rect) entry.getValue());
            int[] iArr = nearestTouchFrame.mTmpInt;
            rect.offset(iArr[0], iArr[1]);
            hashMap.put(view, rect);
        }
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getBackButton(), z2, z3);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getHomeButton(), z2, z3);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getRecentsButton(), z2, z3);
        updateButtonLocation(region, hashMap, (ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher), z2, z3);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getAccessibilityButton(), z2, z3);
        if (z) {
            FloatingRotationButton floatingRotationButton = ((NavigationBarView) this.mView).mFloatingRotationButton;
            if (floatingRotationButton.mIsShowing) {
                updateButtonLocation(region, floatingRotationButton.mKeyButtonView, z2);
                return region;
            }
        }
        updateButtonLocation(region, hashMap, (RotationContextButton) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.rotate_suggestion), z2, z3);
        return region;
    }

    @Override // com.android.systemui.util.ViewController
    public final Context getContext() {
        throw null;
    }

    public int getNavigationIconHints() {
        return this.mNavigationIconHints;
    }

    public final void handleTransientChanged() {
        LightBarController lightBarController;
        if (BasicRune.NAVBAR_ENABLED && this.mView == null) {
            return;
        }
        boolean z = this.mTransientShown;
        this.mEdgeBackGestureHandler.mIsNavBarShownTransiently = z;
        int transitionMode = NavBarHelper.transitionMode(this.mAppearance, z);
        if (!updateTransitionMode(transitionMode) || (lightBarController = this.mLightBarController) == null) {
            return;
        }
        lightBarController.mHasLightNavigationBar = LightBarController.isLight(lightBarController.mAppearance, transitionMode, 16);
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            lightBarController.mNavigationBarMode = transitionMode;
            lightBarController.reevaluate();
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda12] */
    public final void initSecondaryHomeHandleForRotation() {
        if (this.mNavBarMode != 2) {
            return;
        }
        Context context = this.mContext;
        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle = new QuickswitchOrientedNavHandle(context);
        this.mOrientationHandle = quickswitchOrientedNavHandle;
        quickswitchOrientedNavHandle.setId(R.id.secondary_home_handle);
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        ((ArrayList) navigationBarTransitions.mDarkIntensityListeners).add(this.mOrientationHandleIntensityListener);
        float f = navigationBarTransitions.mLightTransitionsController.mDarkIntensity;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(0, 0, 2024, 536871224, -3);
        this.mOrientationParams = layoutParams;
        layoutParams.setTitle("SecondaryHomeHandle" + context.getDisplayId());
        WindowManager.LayoutParams layoutParams2 = this.mOrientationParams;
        layoutParams2.privateFlags = layoutParams2.privateFlags | 4160;
        this.mWindowManager.addView(this.mOrientationHandle, layoutParams2);
        this.mOrientationHandle.setVisibility(8);
        this.mOrientationParams.setFitInsetsTypes(0);
        this.mOrientationHandleGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda12
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                NavigationBar navigationBar = NavigationBar.this;
                if (navigationBar.mStartingQuickSwitchRotation == -1) {
                    return;
                }
                boolean z = BasicRune.NAVBAR_GESTURE;
                RegionSamplingHelper regionSamplingHelper = navigationBar.mRegionSamplingHelper;
                if (!z) {
                    RectF computeHomeHandleBounds = navigationBar.mOrientationHandle.computeHomeHandleBounds();
                    navigationBar.mOrientationHandle.mapRectFromViewToScreenCoords(computeHomeHandleBounds, true);
                    Rect rect = new Rect();
                    computeHomeHandleBounds.roundOut(rect);
                    navigationBar.mOrientedHandleSamplingRegion = rect;
                    regionSamplingHelper.updateSamplingRect();
                    return;
                }
                QuickswitchOrientedNavHandle quickswitchOrientedNavHandle2 = navigationBar.mOrientationHandle;
                int[] iArr = new int[2];
                quickswitchOrientedNavHandle2.mHandleView.getLocationOnScreen(iArr);
                int dimensionPixelSize = quickswitchOrientedNavHandle2.mContext.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height);
                Rect rect2 = quickswitchOrientedNavHandle2.mTmpBoundsRect;
                int i = iArr[0];
                int i2 = iArr[1];
                rect2.set(i, i2, dimensionPixelSize + i, quickswitchOrientedNavHandle2.mHandleView.getHeight() + i2);
                navigationBar.mOrientedHandleSamplingRegion = quickswitchOrientedNavHandle2.mTmpBoundsRect;
                regionSamplingHelper.updateSamplingRect();
            }
        };
        this.mOrientationHandle.getViewTreeObserver().addOnGlobalLayoutListener(this.mOrientationHandleGlobalLayoutListener);
    }

    public final void notifyNavigationBarSurface() {
        ViewRootImpl viewRootImpl = ((NavigationBarView) this.mView).getViewRootImpl();
        SurfaceControl surfaceControl = (((NavigationBarView) this.mView).getParent() == null || viewRootImpl == null || viewRootImpl.getSurfaceControl() == null || !viewRootImpl.getSurfaceControl().isValid()) ? null : viewRootImpl.getSurfaceControl();
        OverviewProxyService overviewProxyService = this.mOverviewProxyService;
        overviewProxyService.mNavigationBarSurface = surfaceControl;
        overviewProxyService.dispatchNavigationBarSurface();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (this.mAutoHideController == null) {
            return;
        }
        this.mLogWrapper.m99dp("NavigationBar", String.format("notifyRequestedGameToolsWin visible : %s", Boolean.valueOf(z)));
        this.mAutoHideController.notifyRequestedGameToolsWin(z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        SysUiState sysUiState = this.mSysUiFlagsContainer;
        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_LOCK_ICON, z);
        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_CLOCK, z2);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_GESTURE && this.mDisplayId == i) {
            this.mLogWrapper.m99dp("NavigationBar", String.format("notifySamsungPayInfo displayId : %d, visible: %s", Integer.valueOf(i), Boolean.valueOf(z)));
            this.mDisplayTracker.getClass();
            if (i == 0) {
                int width = rect.width();
                OverviewProxyService overviewProxyService = this.mOverviewProxyService;
                overviewProxyService.getClass();
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).notifyPayInfo(width, z);
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to notify pay info.", e);
                }
            }
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSpayVisibility(z, rect.width()), this.mDisplayId);
        }
    }

    public boolean onHomeLongClick(View view) {
        if (!((NavigationBarView) this.mView).isRecentsButtonVisible() && this.mScreenPinningActive) {
            return onLongPressNavigationButtons(view, R.id.home);
        }
        int i = 0;
        if ((((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isDeviceProvisioned() && (this.mDisabledFlags1 & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) == 0) ? false : true) {
            return false;
        }
        this.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
        this.mUiEventLogger.log(NavBarActionEvent.NAVBAR_ASSIST_LONGPRESS);
        Bundle bundle = new Bundle();
        bundle.putInt("invocation_type", 5);
        ((AssistManager) this.mAssistManagerLazy.get()).startAssist(bundle);
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda0(i));
        ArrayList arrayList = ((NavigationBarView) this.mView).getHomeButton().mViews;
        int size = arrayList.size();
        while (i < size) {
            if (arrayList.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) arrayList.get(i)).abortCurrentGesture();
            }
            i++;
        }
        return true;
    }

    public boolean onHomeTouch(View view, MotionEvent motionEvent) {
        int i = 1;
        if (this.mHomeBlockedThisTouch && motionEvent.getActionMasked() != 0) {
            return true;
        }
        Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
        int action = motionEvent.getAction();
        int i2 = 0;
        if (action == 0) {
            this.mHomeBlockedThisTouch = false;
            Optional optional2 = this.mTelecomManagerOptional;
            if (optional2.isPresent() && ((TelecomManager) optional2.get()).isRinging() && ((Boolean) optional.map(new NavigationBar$$ExternalSyntheticLambda1(i2)).orElse(Boolean.FALSE)).booleanValue()) {
                Log.i("NavigationBar", "Ignoring HOME; there's a ringing incoming call. No heads up");
                this.mHomeBlockedThisTouch = true;
                return true;
            }
            if (this.mLongPressHomeEnabled) {
                this.mHomeButtonLongPressDurationMs.ifPresent(new NavigationBar$$ExternalSyntheticLambda2(this, i2));
            }
        } else if (action == 1 || action == 3) {
            this.mHandler.removeCallbacks(this.mOnVariableDurationHomeLongClick);
            optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda0(i));
        }
        return false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        NavBarHelper.CurrentSysuiState currentSysuiState;
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        navigationBarView.mBarTransitions = navigationBarTransitions;
        navigationBarView.mTouchHandler = this.mTouchHandler;
        setNavBarMode(this.mNavBarMode);
        boolean z = BasicRune.NAVBAR_ENABLED;
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        Context context = this.mContext;
        if (z) {
            edgeBackGestureHandler.onConfigurationChanged(context.getResources().getConfiguration());
        }
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView2);
        edgeBackGestureHandler.mStateChangeCallback = new NavigationBar$$ExternalSyntheticLambda9(navigationBarView2, 3);
        edgeBackGestureHandler.mButtonForcedVisibleCallback = new NavigationBar$$ExternalSyntheticLambda2(this, 1 == true ? 1 : 0);
        ((ArrayList) navigationBarTransitions.mListeners).add(new NavigationBar$$ExternalSyntheticLambda7(this));
        ((NavigationBarView) this.mView).updateRotationButton();
        NavBarStore navBarStore = this.mNavBarStore;
        if (z) {
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.OnNavBarCreated((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get(), this), context.getDisplayId());
            this.mNavBarStateManager = navBarStoreImpl.getNavStateManager(context.getDisplayId());
        }
        NavBarStateManager.States states = this.mNavBarStateManager.states;
        boolean z2 = states.supportCoverScreen;
        boolean z3 = states.supportLargeCoverScreen;
        if (z && z2) {
            edgeBackGestureHandler.onNavBarAttached();
        }
        if (!z || !z3) {
            ((NavigationBarView) this.mView).setVisibility((z2 || this.mStatusBarKeyguardViewManager.isNavBarVisible()) ? 0 : 4);
        } else if (!this.mNavBarStateManager.isLargeCoverScreenSyncEnabled()) {
            this.mLogWrapper.m98d("NavigationBar", "onInit() Cover navbar hidden: sync option is off");
            ((NavigationBarView) this.mView).setVisibility(8);
        }
        if (z && this.mNavBarStateManager.isNavBarHidden()) {
            ((NavigationBarView) this.mView).setVisibility(8);
        }
        this.mWindowManager.addView(this.mFrame, getBarLayoutParams(context.getResources().getConfiguration().windowConfiguration.getRotation()));
        int displayId = context.getDisplayId();
        this.mDisplayId = displayId;
        this.mDisplayTracker.getClass();
        this.mIsOnDefaultDisplay = displayId == 0;
        NavBarHelper navBarHelper = this.mNavBarHelper;
        if (z) {
            int i = this.mDisplayId;
            navBarHelper.getClass();
            currentSysuiState = new NavBarHelper.CurrentSysuiState(navBarHelper, i);
        } else {
            navBarHelper.getClass();
            currentSysuiState = new NavBarHelper.CurrentSysuiState(navBarHelper);
        }
        if (currentSysuiState.mWindowStateDisplayId == this.mDisplayId) {
            this.mNavigationBarWindowState = currentSysuiState.mWindowState;
        }
        CommandQueue commandQueue = this.mCommandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mDeviceConfigProxy.getClass();
        this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(DeviceConfig.getLong("systemui", "home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda8(0));
        navBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), this.mOnPropertiesChangedListener);
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            this.mDisabledFlags1 = bundle.getInt("disabled_state", 0);
            this.mDisabledFlags2 = bundle.getInt("disabled2_state", 0);
            this.mAppearance = bundle.getInt("appearance", 0);
            this.mBehavior = bundle.getInt("behavior", 0);
            this.mTransientShown = bundle.getBoolean("transient_state", false);
            if (z) {
                this.mNavigationIconHints = bundle.getInt("icon_hints", 0);
            }
        }
        commandQueue.recomputeDisableFlags(this.mDisplayId, false);
        if (z) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnDeviceProvisionedChanged(((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isDeviceProvisioned()));
        }
        ((ArrayList) this.mNotificationShadeDepthController.listeners).add(this.mDepthListener);
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
    }

    public final boolean onLongPressNavigationButtons(View view, int i) {
        boolean z;
        try {
            IActivityTaskManager service = ActivityTaskManager.getService();
            boolean isTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
            boolean isInLockTaskMode = service.isInLockTaskMode();
            if (isInLockTaskMode && !isTouchExplorationEnabled) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.mLastLockToAppLongPress < 200) {
                    service.stopSystemLockTaskMode();
                    ((NavigationBarView) this.mView).updateNavButtonIcons();
                    return true;
                }
                if (view.getId() == R.id.back) {
                    if (!(i == R.id.recent_apps ? ((NavigationBarView) this.mView).getRecentsButton() : ((NavigationBarView) this.mView).getHomeButton()).mCurrentView.isPressed()) {
                        z = true;
                        this.mLastLockToAppLongPress = currentTimeMillis;
                    }
                }
                z = false;
                this.mLastLockToAppLongPress = currentTimeMillis;
            } else if (view.getId() == R.id.back) {
                z = true;
            } else {
                if (isTouchExplorationEnabled && isInLockTaskMode) {
                    service.stopSystemLockTaskMode();
                    ((NavigationBarView) this.mView).updateNavButtonIcons();
                    return true;
                }
                if (view.getId() == i) {
                    if (i == R.id.recent_apps) {
                        return false;
                    }
                    return onHomeLongClick(((NavigationBarView) this.mView).getHomeButton().mCurrentView);
                }
                z = false;
            }
            if (z) {
                KeyButtonView keyButtonView = (KeyButtonView) view;
                keyButtonView.sendEvent(0, 128);
                keyButtonView.sendAccessibilityEvent(2);
                return true;
            }
        } catch (RemoteException e) {
            Log.d("NavigationBar", "Unable to reach activity manager", e);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRecentsAnimationStateChanged(boolean z) {
        RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
        rotationButtonController.mIsRecentsAnimationRunning = z;
        if (!z || rotationButtonController.mHomeRotationEnabled) {
            return;
        }
        rotationButtonController.setRotateSuggestionButtonState(false, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01af, code lost:
    
        if (r18 != 3) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01b6, code lost:
    
        if (r18 != 3) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01bd, code lost:
    
        if (r18 != 3) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01a8, code lost:
    
        if (r18 != 2) goto L132;
     */
    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRotationProposal(int i, boolean z) {
        int i2;
        if (((NavigationBarView) this.mView).isAttachedToWindow()) {
            int i3 = 0;
            if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY && this.mNavBarStateManager.isTaskBarEnabled(false)) {
                return;
            }
            int i4 = this.mDisabledFlags2;
            Interpolator interpolator = RotationButtonController.LINEAR_INTERPOLATOR;
            boolean z2 = (i4 & 16) != 0;
            RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
            RotationButton rotationButton = rotationButtonController.mRotationButton;
            boolean z3 = BasicRune.NAVBAR_ENABLED;
            if (z3) {
                Log.i("NavigationBar", "onRotationProposal proposedRotation=" + Surface.rotationToString(i) + ", isValid=" + z + ", mNavBarWindowState=" + StatusBarManager.windowStateToString(this.mNavigationBarWindowState) + ", rotateSuggestionsDisabled=" + z2 + ", isRotateButtonVisible=" + rotationButton.isVisible());
            }
            if (z2) {
                return;
            }
            if (z3) {
                SettingsHelper settingsHelper = this.mNavBarStateManager.settingsHelper;
                if (!settingsHelper.isNavigationBarRotateSuggestionEnabled() || settingsHelper.isEmergencyMode()) {
                    return;
                }
            }
            int intValue = ((Integer) rotationButtonController.mWindowRotationProvider.get()).intValue();
            if (rotationButtonController.mRotationButton.acceptRotationProposal()) {
                if (rotationButtonController.mHomeRotationEnabled || !rotationButtonController.mIsRecentsAnimationRunning) {
                    boolean z4 = BasicRuneWrapper.NAVBAR_ENABLED;
                    if (z4 && i == -1) {
                        rotationButtonController.mLastUnknownRotationProposedTick = System.currentTimeMillis();
                        rotationButtonController.setRotateSuggestionButtonState(false);
                        return;
                    }
                    if (!z) {
                        rotationButtonController.setRotateSuggestionButtonState(false);
                        return;
                    }
                    Handler handler = rotationButtonController.mMainThreadHandler;
                    RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0 = rotationButtonController.mCancelPendingRotationProposal;
                    if (i == intValue) {
                        if (z4) {
                            rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                            if (rotationButtonController.mPendingRotationSuggestion && !rotationButtonController.mRotationButton.isVisible()) {
                                rotationButtonController.mPendingRotationSuggestion = false;
                                handler.removeCallbacks(rotationButtonController$$ExternalSyntheticLambda0);
                            }
                        }
                        handler.removeCallbacks(rotationButtonController.mRemoveRotationProposal);
                        rotationButtonController.setRotateSuggestionButtonState(false);
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (BasicRuneWrapper.NAVBAR_ENABLED) {
                        long j = rotationButtonController.mLastUnknownRotationProposedTick;
                        if (j != 0 && j + 1000 <= currentTimeMillis) {
                            Log.d("RotationButtonController", "onRotationProposal rotation time over");
                            rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                            return;
                        }
                    }
                    Log.i("RotationButtonController", "onRotationProposal(rotation=" + i + ")");
                    rotationButtonController.mLastRotationSuggestion = i;
                    boolean z5 = !(intValue == 0 && i == 1) && ((intValue == 0 && i == 2) || ((intValue == 0 && i == 3) || ((intValue == 1 && i == 0) || (!(intValue == 1 && i == 2) && ((intValue == 1 && i == 3) || ((intValue == 2 && i == 0) || ((intValue == 2 && i == 1) || (!(intValue == 2 && i == 3) && (!(intValue == 3 && i == 0) && ((intValue == 3 && i == 1) || (intValue == 3 && i == 2)))))))))));
                    if (intValue == 0 || intValue == 2) {
                        rotationButtonController.mIconResId = z5 ? rotationButtonController.mIconCcwStart0ResId : rotationButtonController.mIconCwStart0ResId;
                    } else {
                        rotationButtonController.mIconResId = z5 ? rotationButtonController.mIconCcwStart90ResId : rotationButtonController.mIconCwStart90ResId;
                    }
                    if (BasicRuneWrapper.NAVBAR_ENABLED) {
                        rotationButtonController.mIconResId = rotationButtonController.mSamsungRotateButtonResId;
                        RotationUtil.Companion.getClass();
                        boolean z6 = RotationUtil.ccwCheckArray[intValue][i];
                        if (intValue == 0) {
                            if (i != 1) {
                                if (i != 2) {
                                }
                                i2 = 51;
                            }
                            i2 = 83;
                        } else if (intValue == 1) {
                            if (i != 0) {
                                if (i != 2) {
                                }
                                i2 = 83;
                            }
                            i2 = 53;
                        } else if (intValue != 2) {
                            if (intValue == 3) {
                                if (i != 0) {
                                    if (i != 1) {
                                    }
                                    i2 = 51;
                                }
                                i2 = 83;
                            }
                            i2 = 85;
                        } else {
                            if (i != 0) {
                                if (i != 1) {
                                }
                                i2 = 53;
                            }
                            i2 = 51;
                        }
                        RotationUtil.floatingButtonPosition = i2;
                        if (intValue == 0 || intValue == 2) {
                            i3 = z6 ? rotationButtonController.mSamsungIconCCWStart90ResId : rotationButtonController.mSamsungIconCWStart90ResId;
                        } else if (i == 0 || i == 2) {
                            i3 = z6 ? rotationButtonController.mSamsungIconCCWStart0ResId : rotationButtonController.mSamsungIconCWStart0ResId;
                        } else if (intValue == 1 && i == 3) {
                            i3 = rotationButtonController.mSamsungIconCWStart180ResId;
                        } else if (intValue == 3 && i == 1) {
                            i3 = rotationButtonController.mSamsungIconCCWStart180ResId;
                        }
                        rotationButtonController.mStyleRes = i3;
                    }
                    rotationButtonController.mRotationButton.updateIcon(rotationButtonController.mLightIconColor, rotationButtonController.mDarkIconColor);
                    if (rotationButtonController.canShowRotationButton()) {
                        rotationButtonController.showAndLogRotationSuggestion();
                        return;
                    }
                    if (BasicRuneWrapper.NAVBAR_ENABLED && rotationButtonController.mKeyguardManager.semIsKeyguardShowingAndNotOccluded()) {
                        Log.d("RotationButtonController", "Drop rotation suggestion proposal while keyguard is showing");
                        return;
                    }
                    rotationButtonController.mPendingRotationSuggestion = true;
                    handler.removeCallbacks(rotationButtonController$$ExternalSyntheticLambda0);
                    handler.postDelayed(rotationButtonController$$ExternalSyntheticLambda0, 20000L);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        if (i != this.mDisplayId) {
            return;
        }
        if (this.mAppearance != i2) {
            boolean z3 = BasicRune.NAVBAR_ENABLED;
            if (z3 && z3) {
                StringBuilder sb = new StringBuilder("onSystemBarAttributesChanged() -");
                sb.append("  displayId:" + i);
                sb.append(", appearance:" + i2);
                if (!str.contains("com.att")) {
                    sb.append(", packageName: ".concat(str));
                }
                if (i2 != 0) {
                    sb.append(" (");
                    sb.append((i2 & 1) != 0 ? "APPEARANCE_OPAQUE_STATUS_BARS " : "");
                    sb.append((i2 & 2) != 0 ? "APPEARANCE_OPAQUE_NAVIGATION_BARS " : "");
                    sb.append((i2 & 4) != 0 ? "APPEARANCE_LOW_PROFILE_BARS " : "");
                    sb.append((i2 & 8) != 0 ? "APPEARANCE_LIGHT_STATUS_BARS " : "");
                    sb.append((i2 & 16) != 0 ? "APPEARANCE_LIGHT_NAVIGATION_BARS " : "");
                    sb.append((i2 & 128) != 0 ? "APPEARANCE_LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
                    sb.append(")");
                }
                sb.append(", navbarColorManagedByIme:" + z);
                Log.d("NavigationBar", sb.toString());
            }
            this.mAppearance = i2;
            z2 = updateTransitionMode(NavBarHelper.transitionMode(i2, this.mTransientShown));
        } else {
            z2 = false;
        }
        boolean z4 = z2;
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            lightBarController.onNavigationBarAppearanceChanged(i2, this.mTransitionMode, str, z4, z);
        }
        if (this.mBehavior != i3) {
            this.mBehavior = i3;
            NavigationBarView navigationBarView = (NavigationBarView) this.mView;
            RotationButtonController rotationButtonController = navigationBarView.mRotationButtonController;
            navigationBarView.mDisplayTracker.getClass();
            if (rotationButtonController.mBehavior != i3) {
                rotationButtonController.mBehavior = i3;
                if (rotationButtonController.canShowRotationButton() && rotationButtonController.mPendingRotationSuggestion) {
                    rotationButtonController.showAndLogRotationSuggestion();
                }
            }
            updateSystemUiStateFlags();
        }
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarStyleChanged());
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        LightBarController lightBarController;
        AutoHideController autoHideController;
        Display display = ((NavigationBarView) this.mView).getDisplay();
        ((NavigationBarView) this.mView).mRecentsOptional = this.mRecentsOptional;
        Lazy lazy = this.mCentralSurfacesOptionalLazy;
        if (((Optional) lazy.get()).isPresent()) {
            NavigationBarView navigationBarView = (NavigationBarView) this.mView;
            ShadeViewController shadeViewController = ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) lazy.get()).get())).getShadeViewController();
            navigationBarView.mPanelView = shadeViewController;
            if (shadeViewController != null) {
                ((NotificationPanelViewController) shadeViewController).updateSystemUiStateFlags();
            }
        }
        ((NavigationBarView) this.mView).setDisabledFlags(this.mDisabledFlags1, this.mSysUiFlagsContainer);
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mOnVerticalChangedListener = new NavigationBar$$ExternalSyntheticLambda11(this);
        navigationBarView2.notifyVerticalChangedListener(navigationBarView2.mIsVertical);
        int i = 2;
        ((NavigationBarView) this.mView).setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda5(this, i));
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            LightBarTransitionsController lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController;
            lightBarTransitionsController.getClass();
            float f = bundle.getFloat("dark_intensity", 0.0f);
            lightBarTransitionsController.mDarkIntensity = f;
            boolean z = BasicRune.NAVBAR_LIGHTBAR;
            LightBarTransitionsController.DarkIntensityApplier darkIntensityApplier = lightBarTransitionsController.mApplier;
            if (z) {
                darkIntensityApplier.applyDarkIntensity(f);
            } else if (!BasicRune.NAVBAR_ENABLED) {
                darkIntensityApplier.applyDarkIntensity(MathUtils.lerp(f, 0.0f, lightBarTransitionsController.mDozeAmount));
            }
            lightBarTransitionsController.mNextDarkIntensity = lightBarTransitionsController.mDarkIntensity;
        }
        setNavigationIconHints(this.mNavigationIconHints);
        int i2 = 1;
        boolean z2 = this.mNavigationBarWindowState == 0;
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        regionSamplingHelper.mWindowVisible = z2;
        regionSamplingHelper.updateSamplingListener();
        RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
        if (rotationButtonController.mIsNavigationBarShowing != z2) {
            rotationButtonController.mIsNavigationBarShowing = z2;
            if (rotationButtonController.canShowRotationButton() && rotationButtonController.mPendingRotationSuggestion) {
                rotationButtonController.showAndLogRotationSuggestion();
            }
        }
        NavigationBarView navigationBarView3 = (NavigationBarView) this.mView;
        int i3 = this.mBehavior;
        RotationButtonController rotationButtonController2 = navigationBarView3.mRotationButtonController;
        navigationBarView3.mDisplayTracker.getClass();
        if (rotationButtonController2.mBehavior != i3) {
            rotationButtonController2.mBehavior = i3;
            if (rotationButtonController2.canShowRotationButton() && rotationButtonController2.mPendingRotationSuggestion) {
                rotationButtonController2.showAndLogRotationSuggestion();
            }
        }
        setNavBarMode(this.mNavBarMode);
        repositionNavigationBar(this.mCurrentRotation);
        NavigationBarView navigationBarView4 = (NavigationBarView) this.mView;
        navigationBarView4.mUpdateActiveTouchRegionsCallback = new NavigationBar$$ExternalSyntheticLambda11(this);
        navigationBarView4.notifyActiveTouchRegions();
        ((NavigationBarView) this.mView).getViewTreeObserver().addOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        ((NavigationBarView) this.mView).getViewRootImpl().addSurfaceChangedCallback(this.mSurfaceChangedCallback);
        notifyNavigationBarSurface();
        NavigationBarView navigationBarView5 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView5);
        this.mPipOptional.ifPresent(new NavigationBar$$ExternalSyntheticLambda6(navigationBarView5, i2));
        NavigationBarView navigationBarView6 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView6);
        this.mBackAnimation.ifPresent(new NavigationBar$$ExternalSyntheticLambda6(navigationBarView6, i));
        prepareNavigationBarView();
        checkNavBarModes();
        UserTracker.Callback callback = this.mUserChangedCallback;
        Context context = this.mContext;
        ((UserTrackerImpl) this.mUserTracker).addCallback(callback, context.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        ((NavigationBarView) this.mView).updateNavButtonIcons();
        this.mOverviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.mOverviewProxyListener);
        updateSystemUiStateFlags();
        if (this.mIsOnDefaultDisplay) {
            RotationButtonController rotationButtonController3 = ((NavigationBarView) this.mView).mRotationButtonController;
            if (display != null && rotationButtonController3.isRotationLocked() && !DeviceType.isTablet() && !BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                RotationPolicy.setRotationLockAtAngle(rotationButtonController3.mContext, rotationButtonController3.isRotationLocked(), display.getRotation());
            }
        } else {
            this.mDisabledFlags2 |= 16;
        }
        setDisabled2Flags(this.mDisabledFlags2);
        initSecondaryHomeHandleForRotation();
        if (this.mIsOnDefaultDisplay) {
            lightBarController = this.mMainLightBarController;
        } else {
            Context context2 = this.mContext;
            LightBarController.Factory factory = this.mLightBarControllerFactory;
            factory.getClass();
            lightBarController = new LightBarController(context2, factory.mDarkIconDispatcher, factory.mBatteryController, factory.mNavModeController, factory.mFeatureFlags, factory.mDumpManager, factory.mDisplayTracker, factory.mSamsungLightBarControlHelper, factory.mSamsungStatusBarGrayIconHelper, factory.mKeyguardUpdateMonitor);
        }
        this.mLightBarController = lightBarController;
        NavBarStore navBarStore = this.mNavBarStore;
        if (lightBarController != null) {
            lightBarController.mNavigationBarController = navigationBarTransitions.mLightTransitionsController;
            lightBarController.updateNavigation();
            if (BasicRune.NAVBAR_ENABLED) {
                ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnLightBarControllerCreated(this.mLightBarController), this.mDisplayId);
            }
            if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                LightBarController lightBarController2 = this.mLightBarController;
                LightBarTransitionsController lightBarTransitionsController2 = ((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController;
                ArrayList arrayList = lightBarController2.mObserver.mList;
                arrayList.remove(lightBarTransitionsController2);
                if (lightBarTransitionsController2 != null) {
                    arrayList.add(lightBarTransitionsController2);
                }
            }
        }
        if (this.mIsOnDefaultDisplay) {
            autoHideController = this.mMainAutoHideController;
        } else {
            AutoHideController.Factory factory2 = this.mAutoHideControllerFactory;
            autoHideController = new AutoHideController(context, factory2.mHandler, factory2.mIWindowManager);
        }
        setAutoHideController(autoHideController);
        int transitionMode = NavBarHelper.transitionMode(this.mAppearance, this.mTransientShown);
        this.mTransitionMode = transitionMode;
        checkNavBarModes();
        AutoHideController autoHideController2 = this.mAutoHideController;
        if (autoHideController2 != null) {
            autoHideController2.touchAutoHide();
        }
        LightBarController lightBarController3 = this.mLightBarController;
        if (lightBarController3 != null) {
            lightBarController3.onNavigationBarAppearanceChanged(this.mAppearance, transitionMode, "restoreAppearanceAndTransientState", true, false);
        }
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(this.mTransitionMode));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        LightBarController lightBarController;
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        navigationBarView.mUpdateActiveTouchRegionsCallback = null;
        navigationBarView.notifyActiveTouchRegions();
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        LightBarTransitionsController lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController;
        CommandQueue commandQueue = lightBarTransitionsController.mCommandQueue;
        LightBarTransitionsController.Callback callback = lightBarTransitionsController.mCallback;
        commandQueue.removeCallback((CommandQueue.Callbacks) callback);
        lightBarTransitionsController.mStatusBarStateController.removeCallback(callback);
        ((ArrayList) this.mOverviewProxyService.mConnectionCallbacks).remove(this.mOverviewProxyListener);
        ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
        this.mWakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        if (this.mOrientationHandle != null) {
            resetSecondaryHandle();
            ((ArrayList) navigationBarTransitions.mDarkIntensityListeners).remove(this.mOrientationHandleIntensityListener);
            this.mWindowManager.removeView(this.mOrientationHandle);
            this.mOrientationHandle.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOrientationHandleGlobalLayoutListener);
        }
        ((NavigationBarView) this.mView).getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            RotationContextButton rotationContextButton = (RotationContextButton) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.rotate_suggestion);
            if (rotationContextButton != null) {
                rotationContextButton.mListener = null;
            }
            NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
            navigationBarView2.mOnVerticalChangedListener = null;
            navigationBarView2.notifyVerticalChangedListener(navigationBarView2.mIsVertical);
            ((NavigationBarView) this.mView).setOnTouchListener(null);
            resetButtonListener(((NavigationBarView) this.mView).getRecentsButton());
            resetButtonListener(((NavigationBarView) this.mView).getHomeButton());
            resetButtonListener(((NavigationBarView) this.mView).getBackButton());
            resetButtonListener(((NavigationBarView) this.mView).getAccessibilityButton());
        }
        if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY && (lightBarController = this.mLightBarController) != null) {
            lightBarController.mObserver.mList.remove(((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController);
        }
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mAutoDim);
        handler.removeCallbacks(this.mOnVariableDurationHomeLongClick);
        handler.removeCallbacks(this.mEnableLayoutTransitions);
        this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        NavigationBarView navigationBarView3 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView3);
        this.mPipOptional.ifPresent(new NavigationBar$$ExternalSyntheticLambda6(navigationBarView3, 0));
        ViewRootImpl viewRootImpl = ((NavigationBarView) this.mView).getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.removeSurfaceChangedCallback(this.mSurfaceChangedCallback);
        }
        this.mFrame = null;
        this.mOrientationHandle = null;
        notifyNavigationBarSurface();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void orientSecondaryHomeHandle() {
        int width;
        int dimensionPixelSize;
        int i;
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle;
        if (canShowSecondaryHandle()) {
            int i2 = this.mStartingQuickSwitchRotation;
            if (i2 == -1) {
                resetSecondaryHandle();
                return;
            }
            int i3 = i2 - this.mCurrentRotation;
            if (i3 < 0) {
                i3 += 4;
            }
            if (i2 == -1 || i3 == -1) {
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("secondary nav delta rotation: ", i3, " current: ");
                m1m.append(this.mCurrentRotation);
                m1m.append(" starting: ");
                RecyclerView$$ExternalSyntheticOutline0.m46m(m1m, this.mStartingQuickSwitchRotation, "NavigationBar");
            }
            WindowManager windowManager = this.mWindowManager;
            Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
            if (BasicRune.NAVBAR_AOSP_BUG_FIX && (quickswitchOrientedNavHandle = this.mOrientationHandle) != null) {
                quickswitchOrientedNavHandle.mDeltaRotation = i3;
            }
            this.mOrientationHandle.mDeltaRotation = i3;
            Context context = this.mContext;
            int i4 = 3;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 != 3) {
                            dimensionPixelSize = 0;
                            width = 0;
                            WindowManager.LayoutParams layoutParams = this.mOrientationParams;
                            if (i3 == 0) {
                                i4 = 80;
                            } else if (i3 != 1) {
                                i4 = 5;
                            }
                            layoutParams.gravity = i4;
                            layoutParams.height = dimensionPixelSize;
                            layoutParams.width = width;
                            windowManager.updateViewLayout(this.mOrientationHandle, layoutParams);
                            if (BasicRune.NAVBAR_GESTURE) {
                                Rect rect = new Rect();
                                ((NavigationBarView) this.mView).getHomeHandle().mCurrentView.getHitRect(rect);
                                this.mOrientationHandle.mHomeHandleRect.set(rect);
                                this.mOrientationHandle.setImageDrawable(((NavigationBarView) this.mView).getSecondaryHomeHandleDrawable(i3));
                            }
                            ((NavigationBarView) this.mView).setVisibility(8);
                            this.mOrientationHandle.setVisibility(0);
                        }
                    }
                }
                dimensionPixelSize = bounds.height();
                boolean z = BasicRune.NAVBAR_GESTURE;
                if (z) {
                    StatusBarWindowView statusBarWindowView = ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).mStatusBarWindowController.mStatusBarWindowView;
                    if (statusBarWindowView != null && (rootWindowInsets = statusBarWindowView.getRootWindowInsets()) != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                        Iterator<Rect> it = displayCutout.getBoundingRects().iterator();
                        if (it.hasNext()) {
                            i = it.next().height();
                            dimensionPixelSize -= i;
                        }
                    }
                    i = 0;
                    dimensionPixelSize -= i;
                }
                width = z ? context.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height) : ((NavigationBarView) this.mView).getHeight();
                WindowManager.LayoutParams layoutParams2 = this.mOrientationParams;
                if (i3 == 0) {
                }
                layoutParams2.gravity = i4;
                layoutParams2.height = dimensionPixelSize;
                layoutParams2.width = width;
                windowManager.updateViewLayout(this.mOrientationHandle, layoutParams2);
                if (BasicRune.NAVBAR_GESTURE) {
                }
                ((NavigationBarView) this.mView).setVisibility(8);
                this.mOrientationHandle.setVisibility(0);
            }
            if (!this.mShowOrientedHandleForImmersiveMode) {
                resetSecondaryHandle();
                return;
            }
            width = bounds.width();
            dimensionPixelSize = BasicRune.NAVBAR_GESTURE ? context.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height) : ((NavigationBarView) this.mView).getHeight();
            WindowManager.LayoutParams layoutParams22 = this.mOrientationParams;
            if (i3 == 0) {
            }
            layoutParams22.gravity = i4;
            layoutParams22.height = dimensionPixelSize;
            layoutParams22.width = width;
            windowManager.updateViewLayout(this.mOrientationHandle, layoutParams22);
            if (BasicRune.NAVBAR_GESTURE) {
            }
            ((NavigationBarView) this.mView).setVisibility(8);
            this.mOrientationHandle.setVisibility(0);
        }
    }

    public final void prepareNavigationBarView() {
        ((NavigationBarView) this.mView).reorient();
        ButtonDispatcher recentsButton = ((NavigationBarView) this.mView).getRecentsButton();
        final int i = 0;
        recentsButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda4
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2;
                switch (i) {
                    case 0:
                        NavigationBar navigationBar = this.f$0;
                        Context context = navigationBar.mContext;
                        if (LatencyTracker.isEnabled(context)) {
                            LatencyTracker.getInstance(context).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda0(3));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavigationBar navigationBar2 = this.f$0;
                        navigationBar2.getClass();
                        Display display = view.getDisplay();
                        if (display != null) {
                            i2 = display.getDisplayId();
                        } else {
                            navigationBar2.mDisplayTracker.getClass();
                            i2 = 0;
                        }
                        navigationBar2.mAccessibilityManager.notifyAccessibilityButtonClicked(i2);
                        break;
                    default:
                        NavigationBar navigationBar3 = this.f$0;
                        navigationBar3.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar3.mDisplayId);
                        navigationBar3.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        recentsButton.setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda5(this, i));
        final int i2 = 1;
        ((NavigationBarView) this.mView).getHomeButton().setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda5(this, i2));
        reconfigureHomeLongClick();
        ButtonDispatcher accessibilityButton = ((NavigationBarView) this.mView).getAccessibilityButton();
        accessibilityButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda4
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22;
                switch (i2) {
                    case 0:
                        NavigationBar navigationBar = this.f$0;
                        Context context = navigationBar.mContext;
                        if (LatencyTracker.isEnabled(context)) {
                            LatencyTracker.getInstance(context).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda0(3));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavigationBar navigationBar2 = this.f$0;
                        navigationBar2.getClass();
                        Display display = view.getDisplay();
                        if (display != null) {
                            i22 = display.getDisplayId();
                        } else {
                            navigationBar2.mDisplayTracker.getClass();
                            i22 = 0;
                        }
                        navigationBar2.mAccessibilityManager.notifyAccessibilityButtonClicked(i22);
                        break;
                    default:
                        NavigationBar navigationBar3 = this.f$0;
                        navigationBar3.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar3.mDisplayId);
                        navigationBar3.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        accessibilityButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda3(this, 3));
        updateAccessibilityStateFlags();
        final int i3 = 2;
        ((ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda4
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22;
                switch (i3) {
                    case 0:
                        NavigationBar navigationBar = this.f$0;
                        Context context = navigationBar.mContext;
                        if (LatencyTracker.isEnabled(context)) {
                            LatencyTracker.getInstance(context).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda0(3));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavigationBar navigationBar2 = this.f$0;
                        navigationBar2.getClass();
                        Display display = view.getDisplay();
                        if (display != null) {
                            i22 = display.getDisplayId();
                        } else {
                            navigationBar2.mDisplayTracker.getClass();
                            i22 = 0;
                        }
                        navigationBar2.mAccessibilityManager.notifyAccessibilityButtonClicked(i22);
                        break;
                    default:
                        NavigationBar navigationBar3 = this.f$0;
                        navigationBar3.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar3.mDisplayId);
                        navigationBar3.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        updateScreenPinningGestures();
        if (BasicRune.NAVBAR_ENABLED) {
            ButtonDispatcher backButton = ((NavigationBarView) this.mView).getBackButton();
            backButton.setLongClickable(false);
            backButton.setOnClickListener(null);
            recentsButton.setOnClickListener(null);
            recentsButton.setOnTouchListener(null);
            recentsButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda3(this, 4));
        }
    }

    public final void reconfigureHomeLongClick() {
        if (((NavigationBarView) this.mView).getHomeButton().mCurrentView == null) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(null);
            ((NavigationBarView) this.mView).getHomeButton().setLongClickable(false);
            ((NavigationBarView) this.mView).getHomeButton().setOnTouchListener(null);
        } else if (this.mHomeButtonLongPressDurationMs.isPresent() || !this.mLongPressHomeEnabled) {
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setLongClickable(false);
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setHapticFeedbackEnabled(false);
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(null);
        } else {
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setLongClickable(true);
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setHapticFeedbackEnabled(true);
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda3(this, 5));
        }
    }

    public final void repositionNavigationBar(int i) {
        View view = this.mView;
        if (view == null || !((NavigationBarView) view).isAttachedToWindow()) {
            return;
        }
        prepareNavigationBarView();
        this.mWindowManager.updateViewLayout(this.mFrame, getBarLayoutParams(i));
    }

    public final void resetSecondaryHandle() {
        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle = this.mOrientationHandle;
        if (quickswitchOrientedNavHandle != null) {
            quickswitchOrientedNavHandle.setVisibility(8);
        }
        ((NavigationBarView) this.mView).setVisibility(0);
        this.mOrientedHandleSamplingRegion = null;
        this.mRegionSamplingHelper.updateSamplingRect();
    }

    public final void setAutoHideController(AutoHideController autoHideController) {
        boolean z = BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY;
        C18441 c18441 = this.mAutoHideUiElement;
        if (z) {
            if (autoHideController != null) {
                AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = autoHideController.mObserver;
                autoHideUiElementObserver.getClass();
                ArrayList arrayList = (ArrayList) autoHideUiElementObserver.mList;
                arrayList.remove(c18441);
                if (c18441 != null) {
                    arrayList.add(c18441);
                }
            } else {
                AutoHideController autoHideController2 = this.mAutoHideController;
                if (autoHideController2 != null) {
                    AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver2 = autoHideController2.mObserver;
                    autoHideUiElementObserver2.getClass();
                    ((ArrayList) autoHideUiElementObserver2.mList).remove(c18441);
                }
            }
        }
        this.mAutoHideController = autoHideController;
        if (autoHideController != null) {
            autoHideController.mNavigationBar = c18441;
        }
        ((NavigationBarView) this.mView).mAutoHideController = autoHideController;
    }

    public final void setDisabled2Flags(int i) {
        RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
        Interpolator interpolator = RotationButtonController.LINEAR_INTERPOLATOR;
        if (!((i & 16) != 0)) {
            rotationButtonController.getClass();
        } else {
            rotationButtonController.setRotateSuggestionButtonState(false, true);
            rotationButtonController.mMainThreadHandler.removeCallbacks(rotationButtonController.mRemoveRotationProposal);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        if (i != this.mDisplayId) {
            return;
        }
        NavBarHelper navBarHelper = this.mNavBarHelper;
        boolean isImeShown = navBarHelper.isImeShown(i2);
        boolean z2 = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        if (z2 && this.mNavBarStateManager.supportLargeCoverScreenNavBar()) {
            isImeShown |= this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
        }
        boolean z3 = BasicRune.NAVBAR_ENABLED;
        if (z3) {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setImeWindowStatus displayId=", i, " vis=", i2, " backDisposition=");
            m45m.append(i3);
            m45m.append(" showImeSwitcher=");
            m45m.append(z);
            m45m.append(" imeShown=");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(m45m, isImeShown, "NavigationBar");
        }
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, isImeShown, isImeShown && z);
        if (calculateBackDispositionHints == this.mNavigationIconHints) {
            return;
        }
        NavBarStore navBarStore = this.mNavBarStore;
        if (z3) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconHintChanged(calculateBackDispositionHints));
            navBarHelper.mLastIMEhints = calculateBackDispositionHints;
        }
        setNavigationIconHints(calculateBackDispositionHints);
        if (this.mIsOnDefaultDisplay) {
            ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda0(2));
        } else {
            checkNavBarModes();
        }
        updateSystemUiStateFlags();
        if (z2 && this.mNavBarStateManager.supportLargeCoverScreenNavBar()) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(isImeShown, this.mNavBarStateManager.isLargeCoverTaskEnabled()), this.mDisplayId);
        }
    }

    public final void setNavBarMode(int i) {
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        boolean z = this.mNavigationModeController.mCurrentUserContext.getResources().getBoolean(android.R.bool.config_fillMainBuiltInDisplayCutout);
        navigationBarView.mNavBarMode = i;
        navigationBarView.mImeDrawsImeNavBar = z;
        navigationBarView.mBarTransitions.mNavBarMode = i;
        navigationBarView.mEdgeBackGestureHandler.onNavigationModeChanged(i);
        navigationBarView.mRotationButtonController.mNavBarMode = navigationBarView.mNavBarMode;
        navigationBarView.updateRotationButton();
        boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (isGesturalMode) {
            regionSamplingHelper.start(this.mSamplingBounds);
        } else {
            regionSamplingHelper.stop();
        }
    }

    public final void setNavigationIconHints(int i) {
        if (i == this.mNavigationIconHints) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED || !Utilities.isLargeScreen(this.mContext)) {
            boolean z = (i & 1) != 0;
            if (z != ((this.mNavigationIconHints & 1) != 0)) {
                ((NavigationBarView) this.mView).onImeVisibilityChanged(z);
                this.mImeVisible = z;
            }
            NavigationBarView navigationBarView = (NavigationBarView) this.mView;
            if (i != navigationBarView.mNavigationIconHints) {
                navigationBarView.mNavigationIconHints = i;
                navigationBarView.updateNavButtonIcons();
            }
        }
        this.mNavigationIconHints = i;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setWindowState(int i, int i2, int i3) {
        if (i == this.mDisplayId && i2 == 2 && this.mNavigationBarWindowState != i3) {
            this.mNavigationBarWindowState = i3;
            updateSystemUiStateFlags();
            this.mShowOrientedHandleForImmersiveMode = i3 == 2;
            if (this.mOrientationHandle != null && this.mStartingQuickSwitchRotation != -1) {
                orientSecondaryHomeHandle();
            }
            boolean z = this.mNavigationBarWindowState == 0;
            RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
            regionSamplingHelper.mWindowVisible = z;
            regionSamplingHelper.updateSamplingListener();
            RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
            if (rotationButtonController.mIsNavigationBarShowing != z) {
                rotationButtonController.mIsNavigationBarShowing = z;
                if (rotationButtonController.canShowRotationButton() && rotationButtonController.mPendingRotationSuggestion) {
                    rotationButtonController.showAndLogRotationSuggestion();
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showTransient(int i, int i2, boolean z) {
        if (i != this.mDisplayId || (WindowInsets.Type.navigationBars() & i2) == 0 || this.mTransientShown) {
            return;
        }
        this.mTransientShown = true;
        this.mTransientShownFromGestureOnSystemBar = z;
        handleTransientChanged();
    }

    public final void updateAccessibilityStateFlags() {
        NavBarHelper navBarHelper = this.mNavBarHelper;
        this.mLongPressHomeEnabled = navBarHelper.mLongPressHomeEnabled;
        View view = this.mView;
        if (view != null) {
            long j = navBarHelper.mA11yButtonState;
            boolean z = (16 & j) != 0;
            boolean z2 = (j & 32) != 0;
            ((NavigationBarView) view).setAccessibilityButtonState(z, z2);
            if (BasicRune.NAVBAR_ENABLED) {
                ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarUpdateA11YService(z, z2));
            }
        }
        updateSystemUiStateFlags();
    }

    public final void updateNavBarLayoutParams() {
        NavigationBarFrame navigationBarFrame = this.mFrame;
        if (navigationBarFrame != null) {
            this.mWindowManager.updateViewLayout(navigationBarFrame, getBarLayoutParams(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation()));
        }
    }

    public final void updateScreenPinningGestures() {
        if (BasicRune.NAVBAR_ENABLED) {
            return;
        }
        ButtonDispatcher backButton = ((NavigationBarView) this.mView).getBackButton();
        ButtonDispatcher recentsButton = ((NavigationBarView) this.mView).getRecentsButton();
        if (this.mScreenPinningActive) {
            backButton.setOnLongClickListener(((NavigationBarView) this.mView).isRecentsButtonVisible() ? new NavigationBar$$ExternalSyntheticLambda3(this, 0) : new NavigationBar$$ExternalSyntheticLambda3(this, 1));
            recentsButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda3(this, 2));
        } else {
            backButton.setOnLongClickListener(null);
            recentsButton.setOnLongClickListener(null);
        }
        backButton.setLongClickable(this.mScreenPinningActive);
        recentsButton.setLongClickable(this.mScreenPinningActive);
    }

    public final void updateSystemUiStateFlags() {
        long j = this.mNavBarHelper.mA11yButtonState;
        boolean z = (j & 16) != 0;
        boolean z2 = (j & 32) != 0;
        SysUiState sysUiState = this.mSysUiFlagsContainer;
        sysUiState.setFlag(16L, z);
        sysUiState.setFlag(32L, z2);
        sysUiState.setFlag(2L, !(this.mNavigationBarWindowState == 0));
        sysUiState.setFlag(262144L, (this.mNavigationIconHints & 1) != 0);
        sysUiState.setFlag(1048576L, (this.mNavigationIconHints & 4) != 0);
        sysUiState.setFlag(131072L, this.mBehavior != 2);
        sysUiState.commitUpdate(this.mDisplayId);
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            ((NavigationBarView) this.mView).updateDisabledSystemUiStateFlags(sysUiState);
        }
    }

    public final boolean updateTransitionMode(int i) {
        if (this.mTransitionMode == i) {
            return false;
        }
        this.mTransitionMode = i;
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(i));
        }
        checkNavBarModes();
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController == null) {
            return true;
        }
        autoHideController.touchAutoHide();
        return true;
    }

    public static void updateButtonLocation(Region region, View view, boolean z) {
        Rect rect = new Rect();
        if (z) {
            view.getBoundsOnScreen(rect);
        } else {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            rect.set(i, iArr[1], view.getWidth() + i, view.getHeight() + iArr[1]);
        }
        region.op(rect, Region.Op.UNION);
    }
}
