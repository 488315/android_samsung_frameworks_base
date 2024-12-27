package com.android.systemui.navigationbar;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerImpl$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.LatencyTracker;
import com.android.internal.view.AppearanceRegion;
import com.android.internal.view.RotationPolicy;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.NearestTouchFrame;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.QuickswitchOrientedNavHandle;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.Recents;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.FloatingRotationButtonPositionCalculator;
import com.android.systemui.shared.rotation.RotationButtonController;
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
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.ViewController;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.pip.Pip;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavigationBar extends ViewController implements CommandQueue.Callbacks {
    public final AccessibilityManager mAccessibilityManager;
    public int mAppearance;
    public final Lazy mAssistManagerLazy;
    public final NavigationBar$$ExternalSyntheticLambda13 mAutoDim;
    public AutoHideController mAutoHideController;
    public final AutoHideController.Factory mAutoHideControllerFactory;
    public final AnonymousClass1 mAutoHideUiElement;
    public final Optional mBackAnimation;
    public int mBehavior;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public MotionEvent mCurrentDownEvent;
    public int mCurrentRotation;
    public final DeadZone mDeadZone;
    public final AnonymousClass6 mDepthListener;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags1;
    public int mDisabledFlags2;
    public int mDisplayId;
    public final DisplayTracker mDisplayTracker;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final NavigationBar$$ExternalSyntheticLambda13 mEnableLayoutTransitions;
    public NavigationBarFrame mFrame;
    public final Handler mHandler;
    public boolean mHomeBlockedThisTouch;
    public Optional mHomeButtonLongPressDurationMs;
    public boolean mHomeButtonLongPressHapticEnabled;
    public boolean mImeVisible;
    public final InputMethodManager mInputMethodManager;
    public final Binder mInsetsSourceOwner;
    public boolean mIsOnDefaultDisplay;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass9 mKeyguardStateControllerCallback;
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
    public final AnonymousClass12 mModeChangedListener;
    public final NavBarButtonClickLogger mNavBarButtonClickLogger;
    public final NavBarHelper mNavBarHelper;
    public int mNavBarMode;
    public NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public final int mNavColorSampleMargin;
    public final NavbarOrientationTrackingLogger mNavbarOrientationTrackingLogger;
    public final AnonymousClass2 mNavbarTaskbarStateUpdater;
    public final NavigationBarTransitions mNavigationBarTransitions;
    public int mNavigationBarWindowState;
    public int mNavigationIconHints;
    public final NavigationModeController mNavigationModeController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeDepthController mNotificationShadeDepthController;
    public final NavigationBar$$ExternalSyntheticLambda16 mOnComputeInternalInsetsListener;
    public final AnonymousClass5 mOnPropertiesChangedListener;
    public final NavigationBar$$ExternalSyntheticLambda13 mOnVariableDurationHomeLongClick;
    public final OneHandModeUtil mOneHandModeUtil;
    public QuickswitchOrientedNavHandle mOrientationHandle;
    public NavigationBar$$ExternalSyntheticLambda19 mOrientationHandleGlobalLayoutListener;
    public final AnonymousClass4 mOrientationHandleIntensityListener;
    public WindowManager.LayoutParams mOrientationParams;
    public Rect mOrientedHandleSamplingRegion;
    public Optional mOverrideHomeButtonLongPressDurationMs;
    public Optional mOverrideHomeButtonLongPressSlopMultiplier;
    public final AnonymousClass3 mOverviewProxyListener;
    public final OverviewProxyService mOverviewProxyService;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final Optional mPipOptional;
    public final Optional mRecentsOptional;
    public final RegionSamplingHelper mRegionSamplingHelper;
    public final Rect mSamplingBounds;
    public final Bundle mSavedState;
    public boolean mScreenPinningActive;
    public final ShadeViewController mShadeViewController;
    public boolean mShowOrientedHandleForImmersiveMode;
    public int mStartingQuickSwitchRotation;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public final SysUiState mSysUiFlagsContainer;
    public final TaskStackChangeListeners mTaskStackChangeListeners;
    public final AnonymousClass8 mTaskStackListener;
    public final Optional mTelecomManagerOptional;
    public final AnonymousClass13 mTouchHandler;
    public boolean mTransientShown;
    public boolean mTransientShownFromGestureOnSystemBar;
    public int mTransitionMode;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserContextProvider mUserContextProvider;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final AnonymousClass7 mWakefulnessObserver;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.NavigationBar$10, reason: invalid class name */
    public final class AnonymousClass10 implements RegionSamplingHelper.SamplingCallback {
        public AnonymousClass10() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.NavigationBar$6, reason: invalid class name */
    public final class AnonymousClass6 {
        public boolean mHasBlurs;

        public AnonymousClass6() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public static /* synthetic */ void $r8$lambda$l0AcVbdkOILVdcYnB8eAS8OBtSY(NavigationBar navigationBar, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
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
        internalInsetsInfo.touchableRegion.set(navigationBar.getButtonLocations(false, false));
    }

    /* renamed from: $r8$lambda$w-ga8QWBlGQNgzzuKsJs_6mEsNE, reason: not valid java name */
    public static void m1983$r8$lambda$wga8QWBlGQNgzzuKsJs_6mEsNE(NavigationBar navigationBar) {
        NavigationBarView navigationBarView = (NavigationBarView) navigationBar.mView;
        navigationBarView.mLayoutTransitionsEnabled = true;
        navigationBarView.updateLayoutTransitionsEnabled();
    }

    /* renamed from: $r8$lambda$zY6mSQB2R28kIk6-vWzi6hNheNA, reason: not valid java name */
    public static void m1984$r8$lambda$zY6mSQB2R28kIk6vWzi6hNheNA(NavigationBar navigationBar) {
        if (navigationBar.onHomeLongClick(((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView) && navigationBar.mHomeButtonLongPressHapticEnabled) {
            ((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView.performHapticFeedback(0, 1);
        }
    }

    /* renamed from: -$$Nest$mcalculateSamplingRect, reason: not valid java name */
    public static Rect m1985$$Nest$mcalculateSamplingRect(NavigationBar navigationBar) {
        int i;
        int i2;
        int i3;
        Rect rect;
        navigationBar.mSamplingBounds.setEmpty();
        boolean z = BasicRune.NAVBAR_GESTURE;
        int i4 = navigationBar.mNavColorSampleMargin;
        int i5 = 0;
        if (z) {
            View view = NavigationModeUtil.isBottomGesture(navigationBar.mNavBarMode) ? ((NavigationBarView) navigationBar.mView).getHintView().mCurrentView : ((NavigationBarView) navigationBar.mView).getHomeHandle().mCurrentView;
            if (view != null) {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                Point point = new Point();
                navigationBar.mContext.getDisplay().getRealSize(point);
                int dimensionPixelSize = navigationBar.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height);
                boolean z2 = ((NavBarStateManagerImpl) navigationBar.mNavBarStateManager).states.canMove;
                int i6 = iArr[0];
                int i7 = iArr[1];
                int rotation = navigationBar.mContext.getDisplay().getRotation();
                int width = view.getWidth();
                int height = view.getHeight();
                if (z2) {
                    if (rotation == 1) {
                        int i8 = point.x;
                        int i9 = i8 - dimensionPixelSize;
                        i2 = i7 - i4;
                        i = i7 + height + i4;
                        i3 = i8;
                        i5 = i9;
                    } else if (rotation == 3) {
                        int i10 = i7 - i4;
                        i = i7 + height + i4;
                        i3 = dimensionPixelSize;
                        i2 = i10;
                    } else {
                        i = point.y;
                        i2 = i - dimensionPixelSize;
                        i5 = i6 - i4;
                        i3 = i6 + width + i4;
                    }
                    rect = new Rect(i5, i2, i3, i);
                } else {
                    int i11 = point.y;
                    rect = new Rect(i6 - i4, i11 - dimensionPixelSize, i6 + width + i4, i11);
                }
                if (!rect.equals(navigationBar.mSamplingBounds)) {
                    navigationBar.mSamplingBounds.set(rect);
                }
                Rect rect2 = navigationBar.mSamplingBounds;
                rect2.set(navigationBar.mOneHandModeUtil.getRegionSamplingBounds(rect2));
            }
        } else {
            View view2 = ((NavigationBarView) navigationBar.mView).getHomeHandle().mCurrentView;
            if (view2 != null) {
                int[] iArr2 = new int[2];
                view2.getLocationOnScreen(iArr2);
                Point point2 = new Point();
                view2.getContext().getDisplay().getRealSize(point2);
                int i12 = iArr2[0] - i4;
                int i13 = point2.y;
                NavigationBarView navigationBarView = (NavigationBarView) navigationBar.mView;
                navigationBar.mSamplingBounds.set(new Rect(i12, i13 - (navigationBarView.mIsVertical ? navigationBarView.getResources().getDimensionPixelSize(android.R.dimen.resolver_max_collapsed_height_with_default_with_tabs) : navigationBarView.getResources().getDimensionPixelSize(android.R.dimen.resolver_max_collapsed_height)), view2.getWidth() + iArr2[0] + i4, point2.y));
            }
        }
        return navigationBar.mSamplingBounds;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda16] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.navigationbar.NavigationBar$8] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.navigationbar.NavigationBar$9] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.navigationbar.NavigationBar$12, com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener] */
    /* JADX WARN: Type inference failed for: r8v10, types: [com.android.systemui.navigationbar.NavigationBar$5] */
    /* JADX WARN: Type inference failed for: r8v12, types: [com.android.systemui.navigationbar.NavigationBar$7] */
    /* JADX WARN: Type inference failed for: r8v13, types: [com.android.systemui.navigationbar.NavigationBar$13] */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.navigationbar.NavigationBar$1] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.systemui.navigationbar.NavigationBar$2] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.navigationbar.NavigationBar$3] */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.android.systemui.navigationbar.NavigationBar$4] */
    public NavigationBar(NavigationBarView navigationBarView, NavigationBarFrame navigationBarFrame, Bundle bundle, Context context, WindowManager windowManager, Lazy lazy, AccessibilityManager accessibilityManager, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, SysUiState sysUiState, UserTracker userTracker, CommandQueue commandQueue, Optional<Pip> optional, Optional<Recents> optional2, Lazy lazy2, KeyguardStateController keyguardStateController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, NotificationRemoteInputManager notificationRemoteInputManager, NotificationShadeDepthController notificationShadeDepthController, Handler handler, Executor executor, Executor executor2, UiEventLogger uiEventLogger, NavBarHelper navBarHelper, LightBarController lightBarController, LightBarController.Factory factory, AutoHideController autoHideController, AutoHideController.Factory factory2, Optional<TelecomManager> optional3, InputMethodManager inputMethodManager, DeadZone deadZone, DeviceConfigProxy deviceConfigProxy, NavigationBarTransitions navigationBarTransitions, Optional<BackAnimationController.BackAnimationImpl> optional4, UserContextProvider userContextProvider, WakefulnessLifecycle wakefulnessLifecycle, TaskStackChangeListeners taskStackChangeListeners, DisplayTracker displayTracker, NavBarButtonClickLogger navBarButtonClickLogger, NavbarOrientationTrackingLogger navbarOrientationTrackingLogger, LogWrapper logWrapper) {
        super(navigationBarView);
        this.mNavigationBarWindowState = 0;
        this.mNavigationIconHints = 0;
        this.mOverrideHomeButtonLongPressDurationMs = Optional.empty();
        this.mOverrideHomeButtonLongPressSlopMultiplier = Optional.empty();
        this.mHomeButtonLongPressHapticEnabled = true;
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
                if (((ViewController) navigationBar).mView == null) {
                    return;
                }
                ((NavigationBarView) ((ViewController) navigationBar).mView).setSlippery(!z);
                NavigationBarView navigationBarView2 = (NavigationBarView) ((ViewController) navigationBar).mView;
                for (int i = 0; i < navigationBarView2.mButtonDispatchers.size(); i++) {
                    ButtonDispatcher buttonDispatcher = (ButtonDispatcher) navigationBarView2.mButtonDispatchers.valueAt(i);
                    int size = buttonDispatcher.mViews.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (buttonDispatcher.mViews.get(i2) instanceof ButtonInterface) {
                            ((ButtonInterface) buttonDispatcher.mViews.get(i2)).abortCurrentGestureByA11yGesture(z);
                        }
                    }
                }
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateAccessibilityServicesState() {
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.updateAccessibilityStateFlags();
                if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
                    ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnButtonOrderChanged());
                }
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateAssistantAvailable(boolean z, boolean z2) {
                NavigationBar navigationBar = NavigationBar.this;
                if (((ViewController) navigationBar).mView == null) {
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
                boolean z = BasicRune.NAVBAR_ENABLED;
                NavigationBar navigationBar = NavigationBar.this;
                if (z) {
                    navigationBar.mNavBarStore.handleEvent(navigationBar, new EventTypeFactory.EventType.OnRotationChanged(i), navigationBar.mDisplayId);
                }
                if (!navigationBar.mIsOnDefaultDisplay || ((ViewController) navigationBar).mView == null) {
                    return;
                }
                ((NavigationBarView) ((ViewController) navigationBar).mView).mRotationButtonController.onRotationWatcherChanged(i);
                if (((NavigationBarView) ((ViewController) navigationBar).mView).mCurrentRotation != i) {
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
            public final void animateNavBarLongPress(boolean z, boolean z2, long j) {
                ButtonDispatcher homeHandle = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getHomeHandle();
                for (int i = 0; i < homeHandle.mViews.size(); i++) {
                    if (homeHandle.mViews.get(i) instanceof ButtonInterface) {
                        ((ButtonInterface) homeHandle.mViews.get(i)).animateLongPress(z, z2, j);
                    }
                }
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onConnectionChanged(boolean z) {
                NavigationBar navigationBar = NavigationBar.this;
                NavigationBarView navigationBarView2 = (NavigationBarView) ((ViewController) navigationBar).mView;
                OverviewProxyService overviewProxyService2 = navigationBar.mOverviewProxyService;
                navigationBarView2.mOverviewProxyEnabled = overviewProxyService2.mIsEnabled;
                NavigationBarView navigationBarView3 = (NavigationBarView) ((ViewController) navigationBar).mView;
                navigationBarView3.mShowSwipeUpUi = overviewProxyService2.shouldShowSwipeUpUI();
                navigationBarView3.updateStates();
                navigationBar.updateScreenPinningGestures();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onHomeRotationEnabled(boolean z) {
                RotationButtonController rotationButtonController = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController;
                rotationButtonController.mHomeRotationEnabled = z;
                if (!rotationButtonController.mIsRecentsAnimationRunning || z) {
                    return;
                }
                rotationButtonController.setRotateSuggestionButtonState(false, true);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onOverviewShown() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onPrioritizedRotation(int i) {
                boolean z = BasicRune.NAVBAR_ADDITIONAL_LOG;
                NavigationBar navigationBar = NavigationBar.this;
                if (z) {
                    navigationBar.mLogWrapper.dp("NavigationBar", String.format("onPrioritizedRotation rotation : %d", Integer.valueOf(i)));
                }
                navigationBar.mStartingQuickSwitchRotation = i;
                if (i == -1) {
                    navigationBar.mShowOrientedHandleForImmersiveMode = false;
                }
                navigationBar.orientSecondaryHomeHandle();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
                FloatingRotationButton floatingRotationButton = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mFloatingRotationButton;
                floatingRotationButton.mIsTaskbarVisible = z;
                floatingRotationButton.mIsTaskbarStashed = z2;
                if (floatingRotationButton.mIsShowing) {
                    FloatingRotationButtonPositionCalculator.Position calculatePosition = floatingRotationButton.mPositionCalculator.calculatePosition(floatingRotationButton.mDisplayRotation, z, z2);
                    FloatingRotationButtonPositionCalculator.Position position = floatingRotationButton.mPosition;
                    if (calculatePosition.translationX == position.translationX && calculatePosition.translationY == position.translationY) {
                        return;
                    }
                    floatingRotationButton.updateTranslation(calculatePosition, true);
                    floatingRotationButton.mPosition = calculatePosition;
                }
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onToggleRecentApps() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void setAssistantOverridesRequested(int[] iArr) {
                ((AssistManager) NavigationBar.this.mAssistManagerLazy.get()).mAssistOverrideInvocationTypes = iArr;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void setOverrideHomeButtonLongPress(float f, boolean z, long j) {
                Log.d("NavigationBar", "setOverrideHomeButtonLongPress receives: " + j + ";" + f + ";" + z);
                Optional filter = Optional.of(Long.valueOf(j)).filter(new NavigationBar$$ExternalSyntheticLambda12(1));
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.mOverrideHomeButtonLongPressDurationMs = filter;
                navigationBar.mOverrideHomeButtonLongPressSlopMultiplier = Optional.of(Float.valueOf(f)).filter(new NavigationBar$$ExternalSyntheticLambda12(2));
                navigationBar.mHomeButtonLongPressHapticEnabled = z;
                navigationBar.mOverrideHomeButtonLongPressDurationMs.ifPresent(new NavigationBar$$ExternalSyntheticLambda7(2));
                navigationBar.mOverrideHomeButtonLongPressSlopMultiplier.ifPresent(new NavigationBar$$ExternalSyntheticLambda7(3));
                if (((ViewController) navigationBar).mView != null) {
                    navigationBar.reconfigureHomeLongClick();
                }
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
        this.mAutoDim = new NavigationBar$$ExternalSyntheticLambda13(this, 0);
        this.mEnableLayoutTransitions = new NavigationBar$$ExternalSyntheticLambda13(this, 1);
        this.mOnVariableDurationHomeLongClick = new NavigationBar$$ExternalSyntheticLambda13(this, 2);
        this.mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.navigationbar.NavigationBar.5
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("home_button_long_press_duration_ms")) {
                    NavigationBar.this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(properties.getLong("home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda12(3));
                    if (((ViewController) NavigationBar.this).mView != null) {
                        NavigationBar.this.reconfigureHomeLongClick();
                    }
                }
            }
        };
        this.mDepthListener = new AnonymousClass6();
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.navigationbar.NavigationBar.7
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.notifyNavigationBarScreenOn();
                ((NavigationBarView) ((ViewController) navigationBar).mView).onScreenStateChanged(false);
                navigationBar.mRegionSamplingHelper.stop();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.notifyNavigationBarScreenOn();
                ((NavigationBarView) ((ViewController) navigationBar).mView).onScreenStateChanged(true);
                if (Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode) || (BasicRune.NAVBAR_GESTURE && ((NavBarStateManagerImpl) navigationBar.mNavBarStateManager).isGestureMode())) {
                    navigationBar.mRegionSamplingHelper.start(navigationBar.mSamplingBounds);
                }
            }
        };
        this.mScreenPinningActive = false;
        this.mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.NavigationBar.8
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onLockTaskModeChanged(int i) {
                boolean z = i == 2;
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.mScreenPinningActive = z;
                SysUiState sysUiState2 = navigationBar.mSysUiFlagsContainer;
                sysUiState2.setFlag(1L, z);
                sysUiState2.commitUpdate(navigationBar.mDisplayId);
                ((NavigationBarView) ((ViewController) navigationBar).mView).mScreenPinningActive = navigationBar.mScreenPinningActive;
                navigationBar.updateScreenPinningGestures();
            }
        };
        this.mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.navigationbar.NavigationBar.9
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController.setRotateSuggestionButtonState(false);
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
                NavigationBar navigationBar = NavigationBar.this;
                boolean z = navigationBar.mNavBarMode != i;
                navigationBar.mNavBarMode = i;
                boolean z2 = BasicRune.NAVBAR_ENABLED;
                if (z2) {
                    NavBarStore navBarStore = navigationBar.mNavBarStore;
                    NavBarStates navBarStates = ((NavBarStateManagerImpl) navigationBar.mNavBarStateManager).states;
                    navBarStore.handleEvent(navigationBar, new EventTypeFactory.EventType.OnNavBarConfigChanged(navBarStates.canMove, navBarStates.supportPhoneLayoutProvider, navBarStates.imeDownButtonForAllRotation, i), navigationBar.mDisplayId);
                }
                boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
                NavigationBarTransitions navigationBarTransitions2 = navigationBar.mNavigationBarTransitions;
                if (!isGesturalMode && navigationBarTransitions2 != null) {
                    BarTransitions.BarBackgroundDrawable barBackgroundDrawable = navigationBarTransitions2.mBarBackground;
                    barBackgroundDrawable.mOverrideAlpha = 1.0f;
                    barBackgroundDrawable.invalidateSelf();
                }
                if (z2 && z) {
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
                    ButtonDispatcher accessibilityButton = ((NavigationBarView) ((ViewController) navigationBar).mView).getAccessibilityButton();
                    if (accessibilityButton != null && accessibilityButton.getVisibility() == 0) {
                        ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnShowA11YSwipeUpTipPopup());
                    }
                }
                navigationBar.setNavBarMode(i);
                navigationBar.repositionNavigationBar(navigationBar.mCurrentRotation);
                if (navigationBar.mNavBarMode != 2 || navigationBar.mOrientationHandle == null) {
                    navigationBar.resetSecondaryHandle();
                }
                NavigationBarView navigationBarView2 = (NavigationBarView) ((ViewController) navigationBar).mView;
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
                    ((NavigationBarView) ((ViewController) navigationBar).mView).setSlippery(true);
                    this.mDeadZoneConsuming = true;
                } else if (actionMasked == 1 || actionMasked == 3) {
                    ((NavigationBarView) ((ViewController) navigationBar).mView).updateSlippery();
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
        this.mKeyguardStateController = keyguardStateController;
        this.mShadeViewController = shadeViewController;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
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
        if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context.getDisplayId() == 1) {
            this.mBackAnimation = Optional.empty();
            Log.d("NavigationBar", "BackAnimation not used for displayId: " + context.getDisplayId());
        } else {
            this.mBackAnimation = optional4;
        }
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
        boolean z = BasicRune.NAVBAR_GESTURE;
        if (z) {
            this.mEdgeBackGestureHandler = navBarHelper.getEdgeBackGestureHandler(context);
        } else {
            this.mEdgeBackGestureHandler = navBarHelper.getEdgeBackGestureHandler(navBarHelper.mContext);
        }
        this.mNavBarButtonClickLogger = navBarButtonClickLogger;
        this.mNavbarOrientationTrackingLogger = navbarOrientationTrackingLogger;
        this.mNavColorSampleMargin = getResources().getDimensionPixelSize(R.dimen.navigation_handle_sample_horizontal_margin);
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda16
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                NavigationBar.$r8$lambda$l0AcVbdkOILVdcYnB8eAS8OBtSY(NavigationBar.this, internalInsetsInfo);
            }
        };
        RegionSamplingHelper regionSamplingHelper = new RegionSamplingHelper(this.mView, new AnonymousClass10(), executor, executor2);
        this.mRegionSamplingHelper = regionSamplingHelper;
        if (z) {
            SamsungNavigationBarProxy.Companion.getClass();
            regionSamplingHelper.mBarProxy = SamsungNavigationBarProxy.Companion.getInstance();
        }
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mBgExecutor = executor2;
        navigationBarView2.mRotationButtonController.mBgExecutor = executor2;
        navigationBarView2.mEdgeBackGestureHandler = this.mEdgeBackGestureHandler;
        navigationBarView2.mDisplayTracker = displayTracker;
        this.mNavBarMode = navigationModeController.addListener(r6);
        if (BasicRune.NAVBAR_ENABLED) {
            this.mCurrentRotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
            this.mNavBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mLogWrapper = logWrapper;
            this.mOneHandModeUtil = new OneHandModeUtil((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class));
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
        if (buttonDispatcher == null || (view = buttonDispatcher.mCurrentView) == null || buttonDispatcher.getVisibility() != 0) {
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

    public final void checkNavBarModes() {
        this.mNavigationBarTransitions.transitionTo(this.mTransitionMode, (BasicRune.NAVBAR_ENABLED || !((Boolean) ((Optional) this.mCentralSurfacesOptionalLazy.get()).map(new NavigationBar$$ExternalSyntheticLambda18()).orElse(Boolean.FALSE)).booleanValue() || this.mNavigationBarWindowState == 2) ? false : true);
    }

    public final void destroyView() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        Trace.beginSection("NavigationBar#destroyView");
        try {
            setAutoHideController(null);
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            Trace.beginSection("NavigationBar#removeViewImmediate");
            try {
                this.mWindowManager.removeViewImmediate(((NavigationBarView) this.mView).getRootView());
                Trace.endSection();
                this.mNavigationModeController.removeListener(this.mModeChangedListener);
                edgeBackGestureHandler.mStateChangeCallback = null;
                this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
                NotificationShadeDepthController notificationShadeDepthController = this.mNotificationShadeDepthController;
                ((ArrayList) notificationShadeDepthController.listeners).remove(this.mDepthListener);
                this.mDeviceConfigProxy.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
                this.mTaskStackChangeListeners.unregisterTaskStackListener(this.mTaskStackListener);
                if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && this.mDisplayId == 1) {
                    LightBarController lightBarController = this.mLightBarController;
                    if (lightBarController != null) {
                        ((BatteryControllerImpl) lightBarController.mBatteryController).removeCallback(lightBarController);
                        NavigationModeController navigationModeController = lightBarController.mNavigationModeController;
                        if (navigationModeController != null) {
                            navigationModeController.removeListener(lightBarController.mModeChangedListener);
                        }
                    }
                    edgeBackGestureHandler.onNavBarDetached();
                }
                if (BasicRune.NAVBAR_ENABLED) {
                    NavBarStore navBarStore = this.mNavBarStore;
                    int i = this.mDisplayId;
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                    if (i != 0) {
                        navBarStoreImpl.navDependencies.put(Integer.valueOf(i), null);
                        navBarStoreImpl.navStateManager.put(Integer.valueOf(i), null);
                    } else {
                        navBarStoreImpl.getClass();
                    }
                    ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateControllerCallback);
                }
            } finally {
                Trace.endSection();
            }
        } catch (Throwable th) {
            throw th;
        }
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
        RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
        boolean z2 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
        if ((i3 & 16) == 0) {
            rotationButtonController.getClass();
        } else {
            rotationButtonController.setRotateSuggestionButtonState(false, true);
            rotationButtonController.mMainThreadHandler.removeCallbacks(rotationButtonController.mRemoveRotationProposal);
        }
    }

    public final void disableAnimationsDuringHide(long j) {
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        navigationBarView.mLayoutTransitionsEnabled = false;
        navigationBarView.updateLayoutTransitionsEnabled();
        this.mHandler.postDelayed(this.mEnableLayoutTransitions, j + 448);
    }

    public final void dump(PrintWriter printWriter) {
        String str;
        printWriter.println("NavigationBar (displayId=" + this.mDisplayId + "):");
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mStartingQuickSwitchRotation="), this.mStartingQuickSwitchRotation, printWriter, "  mCurrentRotation="), this.mCurrentRotation, printWriter, "  mHomeButtonLongPressDurationMs=");
        m.append(this.mHomeButtonLongPressDurationMs);
        printWriter.println(m.toString());
        printWriter.println("  mOverrideHomeButtonLongPressDurationMs=" + this.mOverrideHomeButtonLongPressDurationMs);
        printWriter.println("  mOverrideHomeButtonLongPressSlopMultiplier=" + this.mOverrideHomeButtonLongPressSlopMultiplier);
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mLongPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mNavigationBarWindowState=");
        m2.append(StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
        printWriter.println(m2.toString());
        printWriter.println("  mTransitionMode=".concat(BarTransitions.modeToString(this.mTransitionMode)));
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mTransientShown="), this.mTransientShown, printWriter, "  mTransientShownFromGestureOnSystemBar="), this.mTransientShownFromGestureOnSystemBar, printWriter, "  mScreenPinningActive="), this.mScreenPinningActive, printWriter);
        CentralSurfaces.dumpBarTransitions(printWriter, "mNavigationBarView", this.mNavigationBarTransitions);
        printWriter.println("  mOrientedHandleSamplingRegion: " + this.mOrientedHandleSamplingRegion);
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        navigationBarView.getClass();
        Rect rect = new Rect();
        Point point = new Point();
        navigationBarView.getContext().getDisplay().getRealSize(point);
        printWriter.println("NavigationBarView:");
        printWriter.println(String.format("      this: " + CentralSurfaces.viewInfo(navigationBarView) + " " + NavigationBarView.visibilityToString(navigationBarView.getVisibility()), new Object[0]));
        navigationBarView.getWindowVisibleDisplayFrame(rect);
        boolean z = rect.right > point.x || rect.bottom > point.y;
        StringBuilder sb = new StringBuilder("      window: ");
        sb.append(rect.toShortString());
        sb.append(" ");
        sb.append(NavigationBarView.visibilityToString(navigationBarView.getWindowVisibility()));
        CarrierTextController$$ExternalSyntheticOutline0.m(sb, z ? " OFFSCREEN!" : "", printWriter);
        int id = navigationBarView.mCurrentView.getId();
        if (id != 0) {
            try {
                str = navigationBarView.getContext().getResources().getResourceName(id);
            } catch (Resources.NotFoundException unused) {
                str = "(unknown)";
            }
        } else {
            str = "(null)";
        }
        printWriter.println(String.format("      mCurrentView: id=%s (%dx%d) %s %f", str, Integer.valueOf(navigationBarView.mCurrentView.getWidth()), Integer.valueOf(navigationBarView.mCurrentView.getHeight()), NavigationBarView.visibilityToString(navigationBarView.mCurrentView.getVisibility()), Float.valueOf(navigationBarView.mCurrentView.getAlpha())));
        printWriter.println(String.format("      disabled=0x%08x vertical=%s darkIntensity=%.2f", Integer.valueOf(navigationBarView.mDisabledFlags), navigationBarView.mIsVertical ? "true" : "false", Float.valueOf(navigationBarView.mBarTransitions.mLightTransitionsController.mDarkIntensity)));
        printWriter.println("    mScreenOn: " + navigationBarView.mScreenOn);
        NavigationBarView.dumpButton(printWriter, "back", navigationBarView.getBackButton());
        NavigationBarView.dumpButton(printWriter, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, navigationBarView.getHomeButton());
        NavigationBarView.dumpButton(printWriter, "handle", navigationBarView.getHomeHandle());
        NavigationBarView.dumpButton(printWriter, "rcnt", navigationBarView.getRecentsButton());
        NavigationBarView.dumpButton(printWriter, "a11y", navigationBarView.getAccessibilityButton());
        NavigationBarView.dumpButton(printWriter, "ime", (ButtonDispatcher) navigationBarView.mButtonDispatchers.get(R.id.ime_switcher));
        NavigationBarInflaterView navigationBarInflaterView = navigationBarView.mNavigationInflaterView;
        if (navigationBarInflaterView != null) {
            CarrierTextController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavigationBarInflaterView", "  mCurrentLayout: "), navigationBarInflaterView.mCurrentLayout, printWriter);
        }
        NavigationBarTransitions navigationBarTransitions = navigationBarView.mBarTransitions;
        navigationBarTransitions.getClass();
        printWriter.println("NavigationBarTransitions:");
        printWriter.println("  mMode: " + navigationBarTransitions.mMode);
        printWriter.println("  mAlwaysOpaque: false");
        StringBuilder m3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mAllowAutoDimWallpaperNotVisible: "), navigationBarTransitions.mAllowAutoDimWallpaperNotVisible, printWriter, "  mWallpaperVisible: "), navigationBarTransitions.mWallpaperVisible, printWriter, "  mLightsOut: "), navigationBarTransitions.mLightsOut, printWriter, "  mAutoDim: "), navigationBarTransitions.mAutoDim, printWriter, "  bg overrideAlpha: "), navigationBarTransitions.mBarBackground.mOverrideAlpha, printWriter, "  bg color: "), navigationBarTransitions.mBarBackground.mColor, printWriter, "  bg frame: ");
        m3.append(navigationBarTransitions.mBarBackground.mFrame);
        printWriter.println(m3.toString());
        ContextualButtonGroup contextualButtonGroup = navigationBarView.mContextualButtonGroup;
        View view = contextualButtonGroup.mCurrentView;
        StringBuilder m4 = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ContextualButtonGroup", "  getVisibleContextButton(): ");
        m4.append(contextualButtonGroup.getVisibleContextButton());
        printWriter.println(m4.toString());
        StringBuilder m5 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  isVisible(): "), contextualButtonGroup.getVisibility() == 0, printWriter, "  attached(): ");
        m5.append(view != null && view.isAttachedToWindow());
        printWriter.println(m5.toString());
        printWriter.println("  mButtonData [ ");
        for (int size = ((ArrayList) contextualButtonGroup.mButtonData).size() - 1; size >= 0; size--) {
            ContextualButtonGroup.ButtonData buttonData = (ContextualButtonGroup.ButtonData) ((ArrayList) contextualButtonGroup.mButtonData).get(size);
            View view2 = buttonData.button.mCurrentView;
            StringBuilder m6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "    ", ": markedVisible=");
            m6.append(buttonData.markedVisible);
            m6.append(" visible=");
            ContextualButton contextualButton = buttonData.button;
            m6.append(contextualButton.getVisibility());
            m6.append(" attached=");
            m6.append(view2 != null && view2.isAttachedToWindow());
            m6.append(" alpha=");
            m6.append(contextualButton.getAlpha());
            printWriter.println(m6.toString());
        }
        printWriter.println("  ]");
        navigationBarView.mEdgeBackGestureHandler.dump(printWriter);
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        regionSamplingHelper.getClass();
        printWriter.println("RegionSamplingHelper:");
        printWriter.println("\tsampleView isAttached: " + regionSamplingHelper.mSampledView.isAttachedToWindow());
        StringBuilder sb2 = new StringBuilder("\tsampleView isScValid: ");
        sb2.append(regionSamplingHelper.mSampledView.isAttachedToWindow() ? Boolean.valueOf(regionSamplingHelper.mSampledView.getViewRootImpl().getSurfaceControl().isValid()) : "notAttached");
        printWriter.println(sb2.toString());
        StringBuilder m7 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("\tmSamplingEnabled: "), regionSamplingHelper.mSamplingEnabled, printWriter, "\tmSamplingListenerRegistered: "), regionSamplingHelper.mSamplingListenerRegistered, printWriter, "\tmSamplingRequestBounds: ");
        m7.append(regionSamplingHelper.mSamplingRequestBounds);
        printWriter.println(m7.toString());
        printWriter.println("\tmRegisteredSamplingBounds: " + regionSamplingHelper.mRegisteredSamplingBounds);
        StringBuilder m8 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(new StringBuilder("\tmLastMedianLuma: "), regionSamplingHelper.mLastMedianLuma, printWriter, "\tmCurrentMedianLuma: "), regionSamplingHelper.mCurrentMedianLuma, printWriter, "\tmWindowVisible: "), regionSamplingHelper.mWindowVisible, printWriter, "\tmWindowHasBlurs: "), regionSamplingHelper.mWindowHasBlurs, printWriter, "\tmWaitingOnDraw: "), regionSamplingHelper.mWaitingOnDraw, printWriter, "\tmRegisteredStopLayer: ");
        m8.append(regionSamplingHelper.mRegisteredStopLayer);
        printWriter.println(m8.toString());
        printWriter.println("\tmWrappedStopLayer: " + regionSamplingHelper.mWrappedStopLayer);
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("\tmIsDestroyed: "), regionSamplingHelper.mIsDestroyed, printWriter);
        if (BasicRuneWrapper.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("\tmIsWindowGone: "), regionSamplingHelper.mIsWindowGone, printWriter);
        }
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController != null) {
            StringBuilder m9 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "AutoHideController:", "\tmAutoHideSuspended="), autoHideController.mAutoHideSuspended, printWriter, "\tisAnyTransientBarShown=");
            m9.append(autoHideController.isAnyTransientBarShown());
            printWriter.println(m9.toString());
            printWriter.println("\thasPendingAutoHide=" + autoHideController.mHandler.hasCallbacks(autoHideController.mAutoHide));
            printWriter.println("\tgetAutoHideTimeout=" + autoHideController.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
            printWriter.println("\tgetUserAutoHideTimeout=" + autoHideController.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
        }
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
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.WindowManager.LayoutParams getBarLayoutParamsForRotation(int r23) {
        /*
            Method dump skipped, instructions count: 801
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBar.getBarLayoutParamsForRotation(int):android.view.WindowManager$LayoutParams");
    }

    public final Region getButtonLocations(boolean z, boolean z2) {
        if (z2 && !z) {
            z2 = false;
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
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getBackButton(), z, z2);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getHomeButton(), z, z2);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getRecentsButton(), z, z2);
        updateButtonLocation(region, hashMap, (ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher), z, z2);
        updateButtonLocation(region, hashMap, ((NavigationBarView) this.mView).getAccessibilityButton(), z, z2);
        FloatingRotationButton floatingRotationButton = ((NavigationBarView) this.mView).mFloatingRotationButton;
        if (floatingRotationButton.mIsShowing) {
            updateButtonLocation(region, floatingRotationButton.mKeyButtonView, z);
        }
        return region;
    }

    @Override // com.android.systemui.util.ViewController
    public final Context getContext() {
        return this.mContext;
    }

    public int getNavigationIconHints() {
        return this.mNavigationIconHints;
    }

    public final NavigationBarView getView() {
        return (NavigationBarView) this.mView;
    }

    public final void handleTransientChanged() {
        LightBarController lightBarController;
        if (BasicRune.NAVBAR_ENABLED && this.mView == 0) {
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

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda19] */
    public final void initSecondaryHomeHandleForRotation() {
        if (this.mNavBarMode != 2) {
            return;
        }
        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle = new QuickswitchOrientedNavHandle(this.mContext);
        this.mOrientationHandle = quickswitchOrientedNavHandle;
        quickswitchOrientedNavHandle.setId(R.id.secondary_home_handle);
        AnonymousClass4 anonymousClass4 = this.mOrientationHandleIntensityListener;
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        ((ArrayList) navigationBarTransitions.mDarkIntensityListeners).add(anonymousClass4);
        float f = navigationBarTransitions.mLightTransitionsController.mDarkIntensity;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(0, 0, 2024, 536871224, -3);
        this.mOrientationParams = layoutParams;
        layoutParams.setTitle("SecondaryHomeHandle" + this.mContext.getDisplayId());
        WindowManager.LayoutParams layoutParams2 = this.mOrientationParams;
        layoutParams2.privateFlags = layoutParams2.privateFlags | 4160;
        this.mWindowManager.addView(this.mOrientationHandle, layoutParams2);
        this.mOrientationHandle.setVisibility(8);
        logNavbarOrientation("initSecondaryHomeHandleForRotation");
        this.mOrientationParams.setFitInsetsTypes(0);
        this.mOrientationHandleGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda19
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                NavigationBar navigationBar = NavigationBar.this;
                if (navigationBar.mStartingQuickSwitchRotation == -1) {
                    return;
                }
                RectF computeHomeHandleBounds = navigationBar.mOrientationHandle.computeHomeHandleBounds();
                navigationBar.mOrientationHandle.mapRectFromViewToScreenCoords(computeHomeHandleBounds, true);
                Rect rect = new Rect();
                computeHomeHandleBounds.roundOut(rect);
                navigationBar.mOrientedHandleSamplingRegion = rect;
                navigationBar.mRegionSamplingHelper.updateSamplingRect();
            }
        };
        this.mOrientationHandle.getViewTreeObserver().addOnGlobalLayoutListener(this.mOrientationHandleGlobalLayoutListener);
    }

    public final void logNavbarOrientation(String str) {
        T t = this.mView;
        boolean z = false;
        boolean z2 = t != 0 && ((NavigationBarView) t).getVisibility() == 0;
        QuickswitchOrientedNavHandle quickswitchOrientedNavHandle = this.mOrientationHandle;
        if (quickswitchOrientedNavHandle != null && quickswitchOrientedNavHandle.getVisibility() == 0) {
            z = true;
        }
        boolean z3 = this.mShowOrientedHandleForImmersiveMode;
        int i = this.mCurrentRotation;
        int i2 = this.mStartingQuickSwitchRotation;
        final NavbarOrientationTrackingLogger navbarOrientationTrackingLogger = this.mNavbarOrientationTrackingLogger;
        navbarOrientationTrackingLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.NavbarOrientationTrackingLogger$logPrimaryAndSecondaryVisibility$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                NavbarOrientationTrackingLogger navbarOrientationTrackingLogger2 = NavbarOrientationTrackingLogger.this;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                navbarOrientationTrackingLogger2.getClass();
                int i3 = int2 - int1;
                if (i3 < 0) {
                    i3 += 4;
                }
                String str2 = i3 != 1 ? i3 != 2 ? i3 != 3 ? "0" : "270" : "180" : DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD;
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Caller Method: ", str1, "\n\tNavbar Visible: ", "\n\tImmersive Mode: ", bool1);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool2, "\n\tSecondary Handle Visible: ", bool3, "\n\tDelta Rotation: ");
                ComposerImpl$$ExternalSyntheticOutline0.m(m, str2, "\n\tStarting QuickSwitch Rotation: ", int12, "\n\tCurrent Rotation: ");
                return Anchor$$ExternalSyntheticOutline0.m(int22, "\n", m);
            }
        };
        LogBuffer logBuffer = navbarOrientationTrackingLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NavbarOrientationTracking", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z3;
        logMessageImpl.bool3 = z;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i;
        logBuffer.commit(obtain);
    }

    public final void notifyNavigationBarScreenOn() {
        ((NavigationBarView) this.mView).updateNavButtonIcons();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (this.mAutoHideController == null) {
            return;
        }
        this.mLogWrapper.dp("NavigationBar", KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("notifyRequestedGameToolsWin visible : ", z));
        this.mAutoHideController.notifyRequestedGameToolsWin(z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        SysUiState sysUiState = this.mSysUiFlagsContainer;
        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, z);
        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT, z2);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_GESTURE && this.mDisplayId == i) {
            this.mLogWrapper.dp("NavigationBar", String.format("notifySamsungPayInfo displayId : %d, visible: %s", Integer.valueOf(i), Boolean.valueOf(z)));
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
            this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateSpayVisibility(z, rect.width()), this.mDisplayId);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        int rotation = configuration.windowConfiguration.getRotation();
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(locale);
        if (!locale.equals(this.mLocale) || layoutDirectionFromLocale != this.mLayoutDirection) {
            if (BasicRune.NAVBAR_ACCESSIBILITY && this.mLocale != null) {
                T t = this.mView;
                if (t != 0 && ((NavigationBarView) t).isAttachedToWindow()) {
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((View) ((NavigationBarView) this.mView).getParent()).getLayoutParams();
                    layoutParams.accessibilityTitle = this.mContext.getString(R.string.samsung_nav_bar);
                    this.mWindowManager.updateViewLayout((View) ((NavigationBarView) this.mView).getParent(), layoutParams);
                }
                T t2 = this.mView;
                if (t2 != 0) {
                    ((NavigationBarView) t2).reInflateNavBarLayout();
                }
            }
            this.mLocale = locale;
            this.mLayoutDirection = layoutDirectionFromLocale;
            ((NavigationBarView) this.mView).setLayoutDirection(layoutDirectionFromLocale);
        }
        repositionNavigationBar(rotation);
        if (this.mNavBarMode != 2 || this.mOrientationHandle == null) {
            if (BasicRune.NAVBAR_GESTURE) {
                resetSecondaryHandle();
            }
        } else if (rotation != this.mCurrentRotation) {
            this.mCurrentRotation = rotation;
            orientSecondaryHomeHandle();
        }
    }

    public boolean onHomeLongClick(View view) {
        if (!((NavigationBarView) this.mView).isRecentsButtonVisible() && this.mScreenPinningActive) {
            return onLongPressNavigationButtons(view, R.id.home);
        }
        if (!((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get() || (this.mDisabledFlags1 & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0) {
            return false;
        }
        this.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
        this.mUiEventLogger.log(NavBarActionEvent.NAVBAR_ASSIST_LONGPRESS);
        final Bundle bundle = new Bundle();
        bundle.putInt("invocation_type", 5);
        Lazy lazy = this.mAssistManagerLazy;
        if (((AssistManager) lazy.get()).shouldOverrideAssist(5) && (view instanceof KeyButtonView)) {
            ((KeyButtonView) view).mRipple.mOnInvisibleRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBar navigationBar = NavigationBar.this;
                    ((AssistManager) navigationBar.mAssistManagerLazy.get()).startAssist(bundle);
                }
            };
        } else {
            ((AssistManager) lazy.get()).startAssist(bundle);
        }
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda7(0));
        ((NavigationBarView) this.mView).abortCurrentGesture();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
    
        if (r1 != 3) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onHomeTouch(android.view.View r7, android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBar.onHomeTouch(android.view.View, android.view.MotionEvent):boolean");
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
        if (z) {
            edgeBackGestureHandler.onConfigurationChanged(this.mContext.getResources().getConfiguration());
        }
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView2);
        edgeBackGestureHandler.mStateChangeCallback = new NavigationBar$$ExternalSyntheticLambda13(navigationBarView2, 3);
        edgeBackGestureHandler.mButtonForcedVisibleCallback = new NavigationBar$$ExternalSyntheticLambda8(this, 1);
        ((ArrayList) navigationBarTransitions.mListeners).add(new NavigationBar$$ExternalSyntheticLambda2(this));
        ((NavigationBarView) this.mView).updateRotationButton();
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (z) {
            EventTypeFactory.EventType.OnNavBarCreated onNavBarCreated = new EventTypeFactory.EventType.OnNavBarCreated(keyguardStateController, this);
            int displayId = this.mContext.getDisplayId();
            NavBarStore navBarStore = this.mNavBarStore;
            navBarStore.handleEvent(this, onNavBarCreated, displayId);
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(this.mContext.getDisplayId());
        }
        NavBarStates navBarStates = ((NavBarStateManagerImpl) this.mNavBarStateManager).states;
        boolean z2 = navBarStates.supportCoverScreen;
        boolean z3 = navBarStates.supportLargeCoverScreen;
        if (BasicRune.NAVBAR_GESTURE && z2) {
            edgeBackGestureHandler.onNavBarAttached();
        }
        if (!z || !z3) {
            NavigationBarView navigationBarView3 = (NavigationBarView) this.mView;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
            navigationBarView3.setVisibility((z2 || statusBarKeyguardViewManager.isNavBarVisible()) ? 0 : 4);
            if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                statusBarKeyguardViewManager.updateNavigationBarVisibility();
            }
        } else if (!((NavBarStateManagerImpl) this.mNavBarStateManager).isLargeCoverScreenSyncEnabled()) {
            this.mLogWrapper.d("NavigationBar", "onInit() Cover navbar hidden: sync option is off");
            ((NavigationBarView) this.mView).setVisibility(8);
        }
        if (z && ((NavBarStateManagerImpl) this.mNavBarStateManager).isNavBarHidden()) {
            ((NavigationBarView) this.mView).setVisibility(8);
        }
        this.mWindowManager.addView(this.mFrame, getBarLayoutParams(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation()));
        int displayId2 = this.mContext.getDisplayId();
        this.mDisplayId = displayId2;
        this.mDisplayTracker.getClass();
        this.mIsOnDefaultDisplay = displayId2 == 0;
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
        DeviceConfigProxy deviceConfigProxy = this.mDeviceConfigProxy;
        this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(deviceConfigProxy.getLong("systemui", "home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda12(0));
        navBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), this.mOnPropertiesChangedListener);
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            this.mDisabledFlags1 = bundle.getInt("disabled_state", 0);
            this.mDisabledFlags2 = this.mSavedState.getInt("disabled2_state", 0);
            this.mAppearance = this.mSavedState.getInt("appearance", 0);
            this.mBehavior = this.mSavedState.getInt("behavior", 0);
            this.mTransientShown = this.mSavedState.getBoolean("transient_state", false);
            if (z) {
                this.mNavigationIconHints = this.mSavedState.getInt("icon_hints", 0);
            }
        }
        commandQueue.recomputeDisableFlags(this.mDisplayId, false);
        ((ArrayList) this.mNotificationShadeDepthController.listeners).add(this.mDepthListener);
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
        if (z) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this.mKeyguardStateControllerCallback);
        }
    }

    public final boolean onLongPressNavigationButtons(View view, int i) {
        boolean z;
        try {
            IActivityTaskManager service = ActivityTaskManager.getService();
            boolean isTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
            boolean isInLockTaskMode = service.isInLockTaskMode();
            try {
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
            } finally {
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

    /* JADX WARN: Code restructure failed: missing block: B:102:0x017f, code lost:
    
        if (r18 != 2) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x018a, code lost:
    
        if (r18 != 3) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0191, code lost:
    
        if (r18 != 3) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0198, code lost:
    
        if (r18 != 3) goto L117;
     */
    /* JADX WARN: Removed duplicated region for block: B:83:0x019f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01c3  */
    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRotationProposal(int r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBar.onRotationProposal(int, boolean):void");
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        if (i != this.mDisplayId) {
            return;
        }
        if (this.mAppearance != i2) {
            boolean z3 = BasicRune.NAVBAR_ADDITIONAL_LOG;
            if (z3 && z3) {
                StringBuilder sb = new StringBuilder("onSystemBarAttributesChanged() -");
                sb.append("  displayId:" + i);
                sb.append(", appearance:" + i2);
                if (str != null && !str.contains("com.att")) {
                    sb.append(", packageName: ".concat(str));
                }
                if (i2 != 0) {
                    sb.append(" (");
                    sb.append((i2 & 1) != 0 ? "APPEARANCE_OPAQUE_STATUS_BARS " : "");
                    sb.append((i2 & 2) != 0 ? "APPEARANCE_OPAQUE_NAVIGATION_BARS " : "");
                    sb.append((i2 & 4) != 0 ? "APPEARANCE_LOW_PROFILE_BARS " : "");
                    sb.append((i2 & 8) != 0 ? "APPEARANCE_LIGHT_STATUS_BARS " : "");
                    sb.append((i2 & 16) != 0 ? "APPEARANCE_LIGHT_NAVIGATION_BARS " : "");
                    sb.append((1048576 & i2) != 0 ? "APPEARANCE_LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
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
            lightBarController.onNavigationBarAppearanceChanged(i2, this.mTransitionMode, z4, z, str);
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
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        LightBarController lightBarController;
        AutoHideController autoHideController;
        int i = 1;
        int i2 = 0;
        Display display = ((NavigationBarView) this.mView).getDisplay();
        ((NavigationBarView) this.mView).mRecentsOptional = this.mRecentsOptional;
        Lazy lazy = this.mCentralSurfacesOptionalLazy;
        boolean isPresent = ((Optional) lazy.get()).isPresent();
        ShadeViewController shadeViewController = this.mShadeViewController;
        if (isPresent) {
            NavigationBarView navigationBarView = (NavigationBarView) this.mView;
            navigationBarView.getClass();
            navigationBarView.mPanelExpansionInteractor = this.mPanelExpansionInteractor;
            if (shadeViewController != null) {
                shadeViewController.updateSystemUiStateFlags();
            }
        }
        ((NavigationBarView) this.mView).setDisabledFlags(this.mDisabledFlags1, this.mSysUiFlagsContainer);
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mOnVerticalChangedListener = new NavigationBar$$ExternalSyntheticLambda2(this);
        boolean z = navigationBarView2.mIsVertical;
        if (((Optional) lazy.get()).isPresent()) {
            shadeViewController.setQsScrimEnabled(!z);
        }
        ((NavigationBarView) this.mView).setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda3(this, i2));
        Bundle bundle = this.mSavedState;
        NavigationBarTransitions navigationBarTransitions = this.mNavigationBarTransitions;
        if (bundle != null) {
            LightBarTransitionsController lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController;
            lightBarTransitionsController.getClass();
            float f = bundle.getFloat("dark_intensity", 0.0f);
            lightBarTransitionsController.mDarkIntensity = f;
            boolean z2 = BasicRune.NAVBAR_ENABLED;
            LightBarTransitionsController.DarkIntensityApplier darkIntensityApplier = lightBarTransitionsController.mApplier;
            if (z2) {
                darkIntensityApplier.applyDarkIntensity(f);
            } else if (!z2) {
                darkIntensityApplier.applyDarkIntensity(MathUtils.lerp(f, 0.0f, lightBarTransitionsController.mDozeAmount));
            }
            lightBarTransitionsController.mNextDarkIntensity = lightBarTransitionsController.mDarkIntensity;
        }
        setNavigationIconHints(this.mNavigationIconHints);
        boolean z3 = this.mNavigationBarWindowState == 0;
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        regionSamplingHelper.mWindowVisible = z3;
        regionSamplingHelper.updateSamplingListener();
        RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
        if (rotationButtonController.mIsNavigationBarShowing != z3) {
            rotationButtonController.mIsNavigationBarShowing = z3;
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
        navigationBarView4.mUpdateActiveTouchRegionsCallback = new NavigationBar$$ExternalSyntheticLambda2(this);
        navigationBarView4.notifyActiveTouchRegions();
        ((NavigationBarView) this.mView).getViewTreeObserver().addOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        Optional optional = this.mPipOptional;
        NavigationBarView navigationBarView5 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView5);
        optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda4(navigationBarView5, i2));
        Optional optional2 = this.mBackAnimation;
        NavigationBarView navigationBarView6 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView6);
        optional2.ifPresent(new NavigationBar$$ExternalSyntheticLambda4(navigationBarView6, i));
        prepareNavigationBarView();
        checkNavBarModes();
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        notifyNavigationBarScreenOn();
        this.mOverviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.mOverviewProxyListener);
        updateSystemUiStateFlags();
        if (this.mIsOnDefaultDisplay) {
            RotationButtonController rotationButtonController3 = ((NavigationBarView) this.mView).mRotationButtonController;
            if (display != null && rotationButtonController3.isRotationLocked().booleanValue() && !DeviceType.isTablet() && !BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                int rotation = display.getRotation();
                Boolean isRotationLocked = rotationButtonController3.isRotationLocked();
                if (isRotationLocked != null) {
                    RotationPolicy.setRotationLockAtAngle(rotationButtonController3.mContext, isRotationLocked.booleanValue(), rotation, "NavigationBar#onViewAttached");
                }
            }
        } else {
            this.mDisabledFlags2 |= 16;
        }
        int i4 = this.mDisabledFlags2;
        RotationButtonController rotationButtonController4 = ((NavigationBarView) this.mView).mRotationButtonController;
        boolean z4 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
        if ((i4 & 16) != 0) {
            rotationButtonController4.setRotateSuggestionButtonState(false, true);
            rotationButtonController4.mMainThreadHandler.removeCallbacks(rotationButtonController4.mRemoveRotationProposal);
        } else {
            rotationButtonController4.getClass();
        }
        initSecondaryHomeHandleForRotation();
        if (this.mIsOnDefaultDisplay) {
            lightBarController = this.mMainLightBarController;
        } else {
            Context context = this.mContext;
            LightBarController.Factory factory = this.mLightBarControllerFactory;
            factory.getClass();
            lightBarController = new LightBarController(context, factory.mJavaAdapter, factory.mDarkIconDispatcher, factory.mBatteryController, factory.mNavModeController, factory.mStatusBarModeRepository, factory.mDumpManager, factory.mDisplayTracker, factory.mSamsungLightBarControlHelper, factory.mSamsungStatusBarGrayIconHelper);
        }
        this.mLightBarController = lightBarController;
        NavBarStore navBarStore = this.mNavBarStore;
        if (lightBarController != null) {
            lightBarController.mNavigationBarController = navigationBarTransitions.mLightTransitionsController;
            lightBarController.updateNavigation();
            if (BasicRune.NAVBAR_ENABLED) {
                navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnLightBarControllerCreated(this.mLightBarController), this.mDisplayId);
            }
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                LightBarController lightBarController2 = this.mLightBarController;
                LightBarTransitionsController lightBarTransitionsController2 = ((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController;
                LightBarController.LightBarTransientObserver lightBarTransientObserver = lightBarController2.mObserver;
                lightBarTransientObserver.mList.remove(lightBarTransitionsController2);
                if (lightBarTransitionsController2 != null) {
                    lightBarTransientObserver.mList.add(lightBarTransitionsController2);
                }
            }
        }
        if (this.mIsOnDefaultDisplay) {
            autoHideController = this.mMainAutoHideController;
        } else {
            Context context2 = this.mContext;
            AutoHideController.Factory factory2 = this.mAutoHideControllerFactory;
            autoHideController = new AutoHideController(context2, factory2.mHandler, factory2.mIWindowManager);
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
            lightBarController3.onNavigationBarAppearanceChanged(this.mAppearance, transitionMode, true, false, "restoreAppearanceAndTransientState");
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
        lightBarTransitionsController.mGestureNavigationSettingsObserver.unregister();
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
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mAutoDim);
        handler.removeCallbacks(this.mOnVariableDurationHomeLongClick);
        handler.removeCallbacks(this.mEnableLayoutTransitions);
        this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Optional optional = this.mPipOptional;
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView2);
        optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda4(navigationBarView2, 2));
        this.mFrame = null;
        this.mOrientationHandle = null;
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            T t = this.mView;
            ((NavigationBarView) t).mOnVerticalChangedListener = null;
            ((NavigationBarView) t).setOnTouchListener(null);
            resetButtonListener(((NavigationBarView) this.mView).getRecentsButton());
            resetButtonListener(((NavigationBarView) this.mView).getHomeButton());
            resetButtonListener(((NavigationBarView) this.mView).getBackButton());
            resetButtonListener(((NavigationBarView) this.mView).getAccessibilityButton());
        }
        if (!BasicRune.NAVBAR_POLICY_VISIBILITY || (lightBarController = this.mLightBarController) == null) {
            return;
        }
        lightBarController.mObserver.mList.remove(((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void orientSecondaryHomeHandle() {
        /*
            Method dump skipped, instructions count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationBar.orientSecondaryHomeHandle():void");
    }

    public final void prepareNavigationBarView() {
        ((NavigationBarView) this.mView).reorient();
        ButtonDispatcher recentsButton = ((NavigationBarView) this.mView).getRecentsButton();
        final int i = 0;
        recentsButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda20
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2;
                int i3 = i;
                NavigationBar navigationBar = this.f$0;
                switch (i3) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logRecentsButtonClick$2 navBarButtonClickLogger$logRecentsButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logRecentsButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Recents Button Triggered";
                            }
                        };
                        LogBuffer logBuffer = navBarButtonClickLogger.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$logRecentsButtonClick$2, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda7(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logAccessibilityButtonClick$2 navBarButtonClickLogger$logAccessibilityButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logAccessibilityButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Accessibility Button Triggered";
                            }
                        };
                        LogBuffer logBuffer2 = navBarButtonClickLogger2.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$logAccessibilityButtonClick$2, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            i2 = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            i2 = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(i2);
                        break;
                    default:
                        NavBarButtonClickLogger navBarButtonClickLogger3 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logImeSwitcherClick$2 navBarButtonClickLogger$logImeSwitcherClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logImeSwitcherClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Ime Switcher Triggered";
                            }
                        };
                        LogBuffer logBuffer3 = navBarButtonClickLogger3.buffer;
                        logBuffer3.commit(logBuffer3.obtain("NavBarButtonClick", logLevel3, navBarButtonClickLogger$logImeSwitcherClick$2, null));
                        navigationBar.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar.mDisplayId);
                        navigationBar.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        recentsButton.setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda3(this, 1));
        ButtonDispatcher homeButton = ((NavigationBarView) this.mView).getHomeButton();
        homeButton.setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda3(this, 2));
        NavBarButtonClickLogger navBarButtonClickLogger = this.mNavBarButtonClickLogger;
        if (navBarButtonClickLogger != null) {
            homeButton.mNavBarButtonClickLogger = navBarButtonClickLogger;
            int size = homeButton.mViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                homeButton.setNavBarButtonClickLoggerForViewChildren((View) homeButton.mViews.get(i2));
            }
        }
        ButtonDispatcher backButton = ((NavigationBarView) this.mView).getBackButton();
        if (navBarButtonClickLogger != null) {
            backButton.mNavBarButtonClickLogger = navBarButtonClickLogger;
            int size2 = backButton.mViews.size();
            for (int i3 = 0; i3 < size2; i3++) {
                backButton.setNavBarButtonClickLoggerForViewChildren((View) backButton.mViews.get(i3));
            }
        } else {
            backButton.getClass();
        }
        reconfigureHomeLongClick();
        ButtonDispatcher accessibilityButton = ((NavigationBarView) this.mView).getAccessibilityButton();
        final int i4 = 1;
        accessibilityButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda20
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22;
                int i32 = i4;
                NavigationBar navigationBar = this.f$0;
                switch (i32) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logRecentsButtonClick$2 navBarButtonClickLogger$logRecentsButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logRecentsButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Recents Button Triggered";
                            }
                        };
                        LogBuffer logBuffer = navBarButtonClickLogger2.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$logRecentsButtonClick$2, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda7(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger22 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger22.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logAccessibilityButtonClick$2 navBarButtonClickLogger$logAccessibilityButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logAccessibilityButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Accessibility Button Triggered";
                            }
                        };
                        LogBuffer logBuffer2 = navBarButtonClickLogger22.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$logAccessibilityButtonClick$2, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            i22 = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            i22 = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(i22);
                        break;
                    default:
                        NavBarButtonClickLogger navBarButtonClickLogger3 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logImeSwitcherClick$2 navBarButtonClickLogger$logImeSwitcherClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logImeSwitcherClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Ime Switcher Triggered";
                            }
                        };
                        LogBuffer logBuffer3 = navBarButtonClickLogger3.buffer;
                        logBuffer3.commit(logBuffer3.obtain("NavBarButtonClick", logLevel3, navBarButtonClickLogger$logImeSwitcherClick$2, null));
                        navigationBar.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar.mDisplayId);
                        navigationBar.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        accessibilityButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda0(this, 2));
        updateAccessibilityStateFlags();
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher);
        final int i5 = 2;
        buttonDispatcher.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda20
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22;
                int i32 = i5;
                NavigationBar navigationBar = this.f$0;
                switch (i32) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logRecentsButtonClick$2 navBarButtonClickLogger$logRecentsButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logRecentsButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Recents Button Triggered";
                            }
                        };
                        LogBuffer logBuffer = navBarButtonClickLogger2.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$logRecentsButtonClick$2, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda7(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger22 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger22.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logAccessibilityButtonClick$2 navBarButtonClickLogger$logAccessibilityButtonClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logAccessibilityButtonClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Accessibility Button Triggered";
                            }
                        };
                        LogBuffer logBuffer2 = navBarButtonClickLogger22.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$logAccessibilityButtonClick$2, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            i22 = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            i22 = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(i22);
                        break;
                    default:
                        NavBarButtonClickLogger navBarButtonClickLogger3 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$logImeSwitcherClick$2 navBarButtonClickLogger$logImeSwitcherClick$2 = new Function1() { // from class: com.android.systemui.navigationbar.NavBarButtonClickLogger$logImeSwitcherClick$2
                            @Override // kotlin.jvm.functions.Function1
                            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                return "Ime Switcher Triggered";
                            }
                        };
                        LogBuffer logBuffer3 = navBarButtonClickLogger3.buffer;
                        logBuffer3.commit(logBuffer3.obtain("NavBarButtonClick", logLevel3, navBarButtonClickLogger$logImeSwitcherClick$2, null));
                        navigationBar.mInputMethodManager.showInputMethodPickerFromSystem(true, navigationBar.mDisplayId);
                        navigationBar.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
                        break;
                }
            }
        });
        updateScreenPinningGestures();
        if (BasicRune.NAVBAR_ENABLED) {
            backButton.setLongClickable(false);
            backButton.setOnClickListener(null);
            recentsButton.setOnClickListener(null);
            recentsButton.setOnTouchListener(null);
            recentsButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda0(this, 3));
            backButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    NavigationBar.this.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            recentsButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    NavigationBar.this.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            homeButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    NavigationBar.this.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            buttonDispatcher.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    NavigationBar.this.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            accessibilityButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    NavigationBar.this.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
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
        } else if (this.mHomeButtonLongPressDurationMs.isPresent() || this.mOverrideHomeButtonLongPressDurationMs.isPresent() || this.mOverrideHomeButtonLongPressSlopMultiplier.isPresent() || !this.mLongPressHomeEnabled) {
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setLongClickable(false);
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setHapticFeedbackEnabled(false);
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(null);
        } else {
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setLongClickable(true);
            ((NavigationBarView) this.mView).getHomeButton().mCurrentView.setHapticFeedbackEnabled(this.mHomeButtonLongPressHapticEnabled);
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda0(this, 4));
        }
    }

    public final void repositionNavigationBar(int i) {
        T t = this.mView;
        if (t == 0 || !((NavigationBarView) t).isAttachedToWindow()) {
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
        logNavbarOrientation("resetSecondaryHandle");
        this.mOrientedHandleSamplingRegion = null;
        this.mRegionSamplingHelper.updateSamplingRect();
    }

    public final void setAutoHideController(AutoHideController autoHideController) {
        boolean z = BasicRune.NAVBAR_POLICY_VISIBILITY;
        AnonymousClass1 anonymousClass1 = this.mAutoHideUiElement;
        if (z) {
            if (autoHideController != null) {
                AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = autoHideController.mObserver;
                autoHideUiElementObserver.getClass();
                ((ArrayList) autoHideUiElementObserver.mList).remove(anonymousClass1);
                if (anonymousClass1 != null) {
                    ((ArrayList) autoHideUiElementObserver.mList).add(anonymousClass1);
                }
            } else {
                AutoHideController autoHideController2 = this.mAutoHideController;
                if (autoHideController2 != null) {
                    AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver2 = autoHideController2.mObserver;
                    autoHideUiElementObserver2.getClass();
                    ((ArrayList) autoHideUiElementObserver2.mList).remove(anonymousClass1);
                }
            }
        }
        this.mAutoHideController = autoHideController;
        if (autoHideController != null) {
            autoHideController.mNavigationBar = anonymousClass1;
        }
        ((NavigationBarView) this.mView).mAutoHideController = autoHideController;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        if (i != this.mDisplayId) {
            return;
        }
        NavBarHelper navBarHelper = this.mNavBarHelper;
        boolean isImeShown = navBarHelper.isImeShown(i2);
        boolean z2 = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        if (z2 && ((NavBarStateManagerImpl) this.mNavBarStateManager).supportLargeCoverScreenNavBar()) {
            isImeShown |= this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
        }
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "setImeWindowStatus displayId=", " vis=", " backDisposition=");
            m.append(i3);
            m.append(" showImeSwitcher=");
            m.append(z);
            m.append(" imeShown=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, isImeShown, "NavigationBar");
        }
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, isImeShown, isImeShown && z);
        if (calculateBackDispositionHints == this.mNavigationIconHints) {
            return;
        }
        boolean z3 = BasicRune.NAVBAR_ENABLED;
        NavBarStore navBarStore = this.mNavBarStore;
        if (z3) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconHintChanged(calculateBackDispositionHints));
            navBarHelper.mLastIMEhints = calculateBackDispositionHints;
        }
        setNavigationIconHints(calculateBackDispositionHints);
        if (this.mIsOnDefaultDisplay) {
            ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda7(1));
        } else {
            checkNavBarModes();
        }
        updateSystemUiStateFlags();
        if (z2 && ((NavBarStateManagerImpl) this.mNavBarStateManager).supportLargeCoverScreenNavBar()) {
            DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) ((NavBarStateManagerImpl) this.mNavBarStateManager).interactorFactory.get(DeviceStateInteractor.class);
            navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(isImeShown, deviceStateInteractor != null ? deviceStateInteractor.coverTaskCache : false), this.mDisplayId);
        }
    }

    public final void setNavBarMode(int i) {
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        boolean z = this.mNavigationModeController.mCurrentUserContext.getResources().getBoolean(android.R.bool.config_keyguardUserSwitcher);
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
        T t = this.mView;
        if (t != 0) {
            long j = navBarHelper.mA11yButtonState;
            boolean z = (16 & j) != 0;
            boolean z2 = (j & 32) != 0;
            ((NavigationBarView) t).setAccessibilityButtonState(z, z2);
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
            backButton.setOnLongClickListener(((NavigationBarView) this.mView).isRecentsButtonVisible() ? new NavigationBar$$ExternalSyntheticLambda0(this, 0) : new NavigationBar$$ExternalSyntheticLambda0(this, 1));
            recentsButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda0(this, 0));
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
