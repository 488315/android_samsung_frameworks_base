package com.android.systemui.navigationbar.views;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.MathUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InsetsFrameProvider;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.LatencyTracker;
import com.android.internal.view.AppearanceRegion;
import com.android.internal.view.RotationPolicy;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda1;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.BackAnimationPilferPointerCallbackManager;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.QuickswitchOrientedNavHandle;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.navigationbar.views.NavigationBarTransitions;
import com.android.systemui.navigationbar.views.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.views.buttons.ButtonInterface;
import com.android.systemui.navigationbar.views.buttons.ContextualButton;
import com.android.systemui.navigationbar.views.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.views.buttons.DeadZone;
import com.android.systemui.navigationbar.views.buttons.KeyButtonView;
import com.android.systemui.navigationbar.views.buttons.NavBarButtonClickLogger;
import com.android.systemui.navigationbar.views.buttons.NavBarButtonClickLogger$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.views.buttons.NavbarOrientationTrackingLogger;
import com.android.systemui.navigationbar.views.buttons.NearestTouchFrame;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.recents.Recents;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.FloatingRotationButtonPositionCalculator;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda0;
import com.android.systemui.shared.rotation.RotationPolicyUtil;
import com.android.systemui.shared.rotation.RotationUtil;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStoreImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;
import com.android.systemui.statusbar.phone.AutoHideControllerStore;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationController$1$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public class NavigationBar extends ViewController implements CommandQueue.Callbacks {
    public final AccessibilityManager mAccessibilityManager;
    public int mAppearance;
    public final Lazy mAssistManagerLazy;
    public final NavigationBar$$ExternalSyntheticLambda0 mAutoDim;
    public AutoHideControllerImpl mAutoHideController;
    public final AutoHideControllerImpl.Factory mAutoHideControllerFactory;
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
    public final NavigationBar$$ExternalSyntheticLambda0 mEnableLayoutTransitions;
    public NavigationBarFrame mFrame;
    public final Handler mHandler;
    public boolean mHomeBlockedThisTouch;
    public Optional mHomeButtonLongPressDurationMs;
    public boolean mHomeButtonLongPressHapticEnabled;
    public boolean mImeVisible;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public final InputMethodManager mInputMethodManager;
    public final Binder mInsetsSourceOwner;
    public boolean mIsOnDefaultDisplay;
    public final KeyguardStateController mKeyguardStateController;
    public final AnonymousClass9 mKeyguardStateControllerCallback;
    public long mLastLockToAppLongPress;
    public final AnonymousClass3 mLauncherProxyListener;
    public final LauncherProxyService mLauncherProxyService;
    public int mLayoutDirection;
    public LightBarController mLightBarController;
    public final LightBarController.Factory mLightBarControllerFactory;
    public Locale mLocale;
    public final LogWrapper mLogWrapper;
    public boolean mLongPressHomeEnabled;
    public AutoHideControllerImpl mMainAutoHideController;
    public LightBarController mMainLightBarController;
    public final MetricsLogger mMetricsLogger;
    public final AnonymousClass12 mModeChangedListener;
    public final NavBarButtonClickLogger mNavBarButtonClickLogger;
    public final NavBarHelper mNavBarHelper;
    public int mNavBarMode;
    public NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public final int mNavColorSampleMargin;
    public int mNavbarFlags;
    public final NavbarOrientationTrackingLogger mNavbarOrientationTrackingLogger;
    public final AnonymousClass2 mNavbarTaskbarStateUpdater;
    public final NavigationBarTransitions mNavigationBarTransitions;
    public int mNavigationBarWindowState;
    public final NavigationModeController mNavigationModeController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeDepthController mNotificationShadeDepthController;
    public final NavigationBar$$ExternalSyntheticLambda17 mOnComputeInternalInsetsListener;
    public final AnonymousClass5 mOnPropertiesChangedListener;
    public final NavigationBar$$ExternalSyntheticLambda0 mOnVariableDurationHomeLongClick;
    public final OneHandModeUtil mOneHandModeUtil;
    public QuickswitchOrientedNavHandle mOrientationHandle;
    public NavigationBar$$ExternalSyntheticLambda20 mOrientationHandleGlobalLayoutListener;
    public final AnonymousClass4 mOrientationHandleIntensityListener;
    public WindowManager.LayoutParams mOrientationParams;
    public Rect mOrientedHandleSamplingRegion;
    public Optional mOverrideHomeButtonLongPressDurationMs;
    public Optional mOverrideHomeButtonLongPressSlopMultiplier;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final Optional mPipOptional;
    public final Optional mRecentsOptional;
    public final RegionSamplingHelper mRegionSamplingHelper;
    public final Rect mSamplingBounds;
    public final Bundle mSavedState;
    public boolean mScreenPinningActive;
    private SettingsHelper mSettingsHelper;
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

    /* renamed from: com.android.systemui.navigationbar.views.NavigationBar$13, reason: invalid class name */
    public class AnonymousClass13 implements Gefingerpoken {
        public boolean mDeadZoneConsuming;

        public AnonymousClass13() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            NavigationBar navigationBar = NavigationBar.this;
            if (QuickStepContract.isGesturalMode(navigationBar.mNavBarMode) && navigationBar.mImeVisible && motionEvent.getAction() == 0) {
                SysUiStatsLog.write(304, (int) motionEvent.getX(), (int) motionEvent.getY());
            }
            return shouldDeadZoneConsumeTouchEvents(motionEvent);
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
                return true;
            }
            if (actionMasked != 1 && actionMasked != 3) {
                return true;
            }
            ((NavigationBarView) ((ViewController) navigationBar).mView).updateSlippery();
            this.mDeadZoneConsuming = false;
            return true;
        }
    }

    /* renamed from: com.android.systemui.navigationbar.views.NavigationBar$6, reason: invalid class name */
    public class AnonymousClass6 {
        public boolean mHasBlurs;

        public AnonymousClass6() {
        }
    }

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

    /* renamed from: $r8$lambda$HCBlCcLTUzLXTlWADh-tZKsEEs4, reason: not valid java name */
    public static void m2641$r8$lambda$HCBlCcLTUzLXTlWADhtZKsEEs4(NavigationBar navigationBar) {
        NavigationBarView navigationBarView = (NavigationBarView) navigationBar.mView;
        navigationBarView.mLayoutTransitionsEnabled = true;
        navigationBarView.updateLayoutTransitionsEnabled();
    }

    /* renamed from: $r8$lambda$PHzUD06Qyv-u_i_ArfJRIF89Blg, reason: not valid java name */
    public static void m2642$r8$lambda$PHzUD06Qyvu_i_ArfJRIF89Blg(NavigationBar navigationBar) {
        if (navigationBar.onHomeLongClick(((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView) && navigationBar.mHomeButtonLongPressHapticEnabled) {
            ((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView.performHapticFeedback(0, 1);
        }
    }

    public static /* synthetic */ void $r8$lambda$Wn0o0kPDQmSoM28iBhTM7ckQ3iw(NavigationBar navigationBar, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
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

    /* renamed from: -$$Nest$mcalculateSamplingRect, reason: not valid java name */
    public static Rect m2643$$Nest$mcalculateSamplingRect(NavigationBar navigationBar) throws Resources.NotFoundException {
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
                navigationBar.mSamplingBounds.set(new Rect(i12, i13 - (navigationBarView.mIsVertical ? navigationBarView.getResources().getDimensionPixelSize(android.R.dimen.slice_icon_size) : navigationBarView.getResources().getDimensionPixelSize(android.R.dimen.select_dialog_drawable_padding_start_material)), view2.getWidth() + iArr2[0] + i4, point2.y));
            }
        }
        return navigationBar.mSamplingBounds;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10, types: [com.android.systemui.navigationbar.views.NavigationBar$5] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.systemui.navigationbar.views.NavigationBar$7] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.navigationbar.views.NavigationBar$1] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.navigationbar.views.NavigationBar$2] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.navigationbar.views.NavigationBar$3] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.navigationbar.views.NavigationBar$4] */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.systemui.navigationbar.views.NavigationBar$8] */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.navigationbar.views.NavigationBar$9] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.navigationbar.NavigationModeController$ModeChangedListener, com.android.systemui.navigationbar.views.NavigationBar$12] */
    /* JADX WARN: Type inference failed for: r9v42, types: [com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda17] */
    public NavigationBar(NavigationBarView navigationBarView, NavigationBarFrame navigationBarFrame, Bundle bundle, Context context, WindowManager windowManager, Lazy lazy, AccessibilityManager accessibilityManager, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, LauncherProxyService launcherProxyService, NavigationModeController navigationModeController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, SysUiState sysUiState, UserTracker userTracker, CommandQueue commandQueue, Optional<Pip> optional, Optional<Recents> optional2, Lazy lazy2, KeyguardStateController keyguardStateController, ShadeViewController shadeViewController, PanelExpansionInteractor panelExpansionInteractor, NotificationRemoteInputManager notificationRemoteInputManager, NotificationShadeDepthController notificationShadeDepthController, Handler handler, Executor executor, Executor executor2, UiEventLogger uiEventLogger, NavBarHelper navBarHelper, LightBarController lightBarController, LightBarController.Factory factory, IndicatorGardenPresenter indicatorGardenPresenter, AutoHideControllerImpl autoHideControllerImpl, AutoHideControllerImpl.Factory factory2, AutoHideControllerStore autoHideControllerStore, Optional<TelecomManager> optional3, InputMethodManager inputMethodManager, DeadZone deadZone, DeviceConfigProxy deviceConfigProxy, NavigationBarTransitions navigationBarTransitions, Optional<BackAnimationController.BackAnimationImpl> optional4, UserContextProvider userContextProvider, WakefulnessLifecycle wakefulnessLifecycle, TaskStackChangeListeners taskStackChangeListeners, DisplayTracker displayTracker, NavBarButtonClickLogger navBarButtonClickLogger, NavbarOrientationTrackingLogger navbarOrientationTrackingLogger, LogWrapper logWrapper, SettingsHelper settingsHelper) {
        super(navigationBarView);
        this.mNavigationBarWindowState = 0;
        this.mOverrideHomeButtonLongPressDurationMs = Optional.empty();
        this.mOverrideHomeButtonLongPressSlopMultiplier = Optional.empty();
        this.mHomeButtonLongPressHapticEnabled = true;
        this.mNavBarMode = 0;
        this.mStartingQuickSwitchRotation = -1;
        this.mSamplingBounds = new Rect();
        this.mInsetsSourceOwner = new Binder();
        this.mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.views.NavigationBar.1
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
        this.mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.views.NavigationBar.2
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
                ILauncherProxy iLauncherProxy = navigationBar.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    try {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onAssistantAvailable(z, z2);
                    } catch (RemoteException unused) {
                        Log.w("NavigationBar", "Unable to send assistant availability data to launcher");
                    }
                }
                navigationBar.reconfigureHomeLongClick();
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateRotationWatcherState(int i, Boolean bool) {
                boolean z = BasicRune.NAVBAR_ENABLED;
                NavigationBar navigationBar = NavigationBar.this;
                if (z) {
                    navigationBar.mNavBarStore.handleEvent(navigationBar, new EventTypeFactory.EventType.OnRotationChanged(i), navigationBar.mDisplayId);
                }
                if (!navigationBar.mIsOnDefaultDisplay || ((ViewController) navigationBar).mView == null) {
                    return;
                }
                ((NavigationBarView) ((ViewController) navigationBar).mView).mRotationButtonController.onRotationWatcherChanged(i, bool);
                if (((NavigationBarView) ((ViewController) navigationBar).mView).mCurrentRotation != i) {
                    navigationBar.repositionNavigationBar(i);
                }
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public final void updateWallpaperVisibility(int i, boolean z) {
                NavigationBarTransitions navigationBarTransitions2 = NavigationBar.this.mNavigationBarTransitions;
                navigationBarTransitions2.mWallpaperVisible = z;
                navigationBarTransitions2.applyLightsOut(true, false);
            }
        };
        this.mLauncherProxyListener = new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar.3
            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void animateNavBarLongPress(boolean z, boolean z2, long j) {
                ButtonDispatcher homeHandle = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getHomeHandle();
                for (int i = 0; i < homeHandle.mViews.size(); i++) {
                    if (homeHandle.mViews.get(i) instanceof ButtonInterface) {
                        ((ButtonInterface) homeHandle.mViews.get(i)).animateLongPress(z, z2, j);
                    }
                }
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onConnectionChanged(boolean z) {
                NavigationBar navigationBar = NavigationBar.this;
                NavigationBarView navigationBarView2 = (NavigationBarView) ((ViewController) navigationBar).mView;
                LauncherProxyService launcherProxyService2 = navigationBar.mLauncherProxyService;
                navigationBarView2.mLauncherProxyEnabled = launcherProxyService2.mIsEnabled;
                NavigationBarView navigationBarView3 = (NavigationBarView) ((ViewController) navigationBar).mView;
                navigationBarView3.mShowSwipeUpUi = launcherProxyService2.shouldShowSwipeUpUI();
                navigationBarView3.updateStates();
                navigationBar.updateScreenPinningGestures();
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onHomeRotationEnabled(boolean z) {
                RotationButtonController rotationButtonController = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController;
                rotationButtonController.mHomeRotationEnabled = z;
                if (!rotationButtonController.mIsRecentsAnimationRunning || z) {
                    return;
                }
                rotationButtonController.setRotateSuggestionButtonState(false, true);
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onOverviewShown() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
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

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onTaskbarStatusUpdated$1(boolean z, boolean z2) {
                FloatingRotationButton floatingRotationButton = ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mFloatingRotationButton;
                floatingRotationButton.mIsTaskbarVisible = z;
                floatingRotationButton.mIsTaskbarStashed = z2;
                if (floatingRotationButton.mIsShowing) {
                    FloatingRotationButtonPositionCalculator.Position positionCalculatePosition = floatingRotationButton.mPositionCalculator.calculatePosition(floatingRotationButton.mDisplayRotation, z, z2);
                    FloatingRotationButtonPositionCalculator.Position position = floatingRotationButton.mPosition;
                    if (positionCalculatePosition.translationX == position.translationX && positionCalculatePosition.translationY == position.translationY) {
                        return;
                    }
                    floatingRotationButton.updateTranslation(positionCalculatePosition, true);
                    floatingRotationButton.mPosition = positionCalculatePosition;
                }
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onToggleRecentApps() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).mRotationButtonController.mSkipOverrideUserLockPrefsOnce = !r1.mIsRecentsAnimationRunning;
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void setAssistantOverridesRequested(int[] iArr) {
                ((AssistManager) NavigationBar.this.mAssistManagerLazy.get()).mAssistOverrideInvocationTypes = iArr;
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void setOverrideHomeButtonLongPress(float f, boolean z, long j) {
                Log.d("NavigationBar", "setOverrideHomeButtonLongPress receives: " + j + ";" + f + ";" + z);
                Optional optionalFilter = Optional.of(Long.valueOf(j)).filter(new NavigationBar$$ExternalSyntheticLambda3(1));
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.mOverrideHomeButtonLongPressDurationMs = optionalFilter;
                navigationBar.mOverrideHomeButtonLongPressSlopMultiplier = Optional.of(Float.valueOf(f)).filter(new NavigationBar$$ExternalSyntheticLambda3(2));
                navigationBar.mHomeButtonLongPressHapticEnabled = z;
                navigationBar.mOverrideHomeButtonLongPressDurationMs.ifPresent(new NavigationBar$$ExternalSyntheticLambda5(2));
                navigationBar.mOverrideHomeButtonLongPressSlopMultiplier.ifPresent(new NavigationBar$$ExternalSyntheticLambda5(3));
                if (((ViewController) navigationBar).mView != null) {
                    navigationBar.reconfigureHomeLongClick();
                }
            }
        };
        this.mOrientationHandleIntensityListener = new NavigationBarTransitions.DarkIntensityListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar.4
            @Override // com.android.systemui.navigationbar.views.NavigationBarTransitions.DarkIntensityListener
            public final void onDarkIntensity(float f) {
                QuickswitchOrientedNavHandle quickswitchOrientedNavHandle;
                if (!BasicRune.NAVBAR_AOSP_BUG_FIX || (quickswitchOrientedNavHandle = NavigationBar.this.mOrientationHandle) == null) {
                    return;
                }
                quickswitchOrientedNavHandle.setDarkIntensity(f);
            }
        };
        this.mAutoDim = new NavigationBar$$ExternalSyntheticLambda0(this, 1);
        this.mEnableLayoutTransitions = new NavigationBar$$ExternalSyntheticLambda0(this, 2);
        this.mOnVariableDurationHomeLongClick = new NavigationBar$$ExternalSyntheticLambda0(this, 3);
        this.mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar.5
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("home_button_long_press_duration_ms")) {
                    NavigationBar.this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(properties.getLong("home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda3(3));
                    if (((ViewController) NavigationBar.this).mView != null) {
                        NavigationBar.this.reconfigureHomeLongClick();
                    }
                }
            }
        };
        this.mDepthListener = new AnonymousClass6();
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.navigationbar.views.NavigationBar.7
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
        this.mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar.8
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onLockTaskModeChanged(int i) {
                boolean z = i == 2;
                NavigationBar navigationBar = NavigationBar.this;
                navigationBar.mScreenPinningActive = z;
                ((SysUiStateImpl) navigationBar.mSysUiFlagsContainer.setFlag(1L, z)).commitUpdate();
                ((NavigationBarView) ((ViewController) navigationBar).mView).mScreenPinningActive = navigationBar.mScreenPinningActive;
                navigationBar.updateScreenPinningGestures();
            }
        };
        this.mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.navigationbar.views.NavigationBar.9
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                NavigationBar navigationBar = NavigationBar.this;
                ((NavigationBarView) ((ViewController) navigationBar).mView).mRotationButtonController.setRotateSuggestionButtonState(false);
                navigationBar.mNavBarStore.handleEvent(navigationBar, new EventTypeFactory.EventType.OnKeyguardStateChanged(((KeyguardStateControllerImpl) navigationBar.mKeyguardStateController).mShowing), navigationBar.mDisplayId);
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.views.NavigationBar.11
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                NavigationBar.this.updateAccessibilityStateFlags();
            }
        };
        ?? r8 = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar.12
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) throws Resources.NotFoundException {
                NavigationBar navigationBar = NavigationBar.this;
                boolean z = navigationBar.mNavBarMode != i;
                navigationBar.mNavBarMode = i;
                boolean z2 = BasicRune.NAVBAR_ENABLED;
                if (z2) {
                    NavBarStore navBarStore = navigationBar.mNavBarStore;
                    NavBarStates navBarStates = ((NavBarStateManagerImpl) navigationBar.mNavBarStateManager).states;
                    navBarStore.handleEvent(navigationBar, new EventTypeFactory.EventType.OnNavBarConfigChanged(navBarStates.canMove, navBarStates.supportPhoneLayoutProvider, navBarStates.imeDownButtonForAllRotation, i), navigationBar.mDisplayId);
                }
                boolean zIsGesturalMode = QuickStepContract.isGesturalMode(i);
                NavigationBarTransitions navigationBarTransitions2 = navigationBar.mNavigationBarTransitions;
                if (!zIsGesturalMode && navigationBarTransitions2 != null) {
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
                navigationBarView2.mShowSwipeUpUi = navigationBar.mLauncherProxyService.shouldShowSwipeUpUI();
                navigationBarView2.updateStates();
            }
        };
        this.mModeChangedListener = r8;
        this.mTouchHandler = new AnonymousClass13();
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
        this.mLauncherProxyService = launcherProxyService;
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
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        this.mMainAutoHideController = autoHideControllerImpl;
        this.mAutoHideControllerFactory = factory2;
        this.mTelecomManagerOptional = optional3;
        this.mInputMethodManager = inputMethodManager;
        this.mUserContextProvider = userContextProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mTaskStackChangeListeners = taskStackChangeListeners;
        this.mDisplayTracker = displayTracker;
        if (BasicRune.NAVBAR_GESTURE) {
            navBarHelper.getClass();
            this.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context, windowManager) : navBarHelper.mEdgeBackGestureHandler;
        } else {
            Context context2 = navBarHelper.mContext;
            this.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context2.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context2, navBarHelper.mWindowManager) : navBarHelper.mEdgeBackGestureHandler;
        }
        this.mNavBarButtonClickLogger = navBarButtonClickLogger;
        this.mNavbarOrientationTrackingLogger = navbarOrientationTrackingLogger;
        this.mNavColorSampleMargin = getResources().getDimensionPixelSize(R.dimen.navigation_handle_sample_horizontal_margin);
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda17
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                NavigationBar.$r8$lambda$Wn0o0kPDQmSoM28iBhTM7ckQ3iw(this.f$0, internalInsetsInfo);
            }
        };
        this.mRegionSamplingHelper = new RegionSamplingHelper(this.mView, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.systemui.navigationbar.views.NavigationBar.10
            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final Rect getSampledRegion() {
                NavigationBar navigationBar = NavigationBar.this;
                Rect rect = navigationBar.mOrientedHandleSamplingRegion;
                return rect != null ? rect : NavigationBar.m2643$$Nest$mcalculateSamplingRect(navigationBar);
            }

            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final boolean isSamplingEnabled() {
                NavBarStateManager navBarStateManager;
                NavBarStateManager navBarStateManager2;
                boolean z = BasicRune.NAVBAR_SETUP_WIZARD;
                NavigationBar navigationBar = NavigationBar.this;
                if (z && (navBarStateManager2 = navigationBar.mNavBarStateManager) != null && ((NavBarStateManagerImpl) navBarStateManager2).shouldShowSUWStyle()) {
                    return false;
                }
                return (!BasicRune.NAVBAR_POLICY_VISIBILITY || (navBarStateManager = navigationBar.mNavBarStateManager) == null) ? (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && navigationBar.mContext.getDisplayId() == 1) ? QuickStepContract.isGesturalMode(navigationBar.mNavBarMode) : Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode) : !((NavBarStateManagerImpl) navBarStateManager).isTaskBarEnabled(false) && Utils.isGesturalModeOnDefaultDisplay(navigationBar.mContext, navigationBar.mDisplayTracker, navigationBar.mNavBarMode);
            }

            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final void onRegionDarknessChanged(boolean z) {
                NavigationBar.this.mNavigationBarTransitions.mLightTransitionsController.setIconsDark(!z, true);
            }

            @Override // com.android.wm.shell.shared.handles.RegionSamplingHelper.SamplingCallback
            public final void onUpdateSamplingListener(boolean z) {
                NavigationBar navigationBar = NavigationBar.this;
                ((NavBarStoreImpl) navigationBar.mNavBarStore).handleEvent(navigationBar, new EventTypeFactory.EventType.OnUpdateRegionSamplingListener(z));
            }
        }, executor, executor2);
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mBgExecutor = executor2;
        navigationBarView2.mRotationButtonController.mBgExecutor = executor2;
        navigationBarView2.mEdgeBackGestureHandler = this.mEdgeBackGestureHandler;
        navigationBarView2.mDisplayTracker = displayTracker;
        this.mNavBarMode = navigationModeController.addListener(r8);
        if (BasicRune.NAVBAR_ENABLED) {
            this.mCurrentRotation = context.getResources().getConfiguration().windowConfiguration.getRotation();
            this.mNavBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mLogWrapper = logWrapper;
            this.mOneHandModeUtil = new OneHandModeUtil((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class));
        }
        if (BasicRune.NAVBAR_PREDICTIVE_BACK_THREE_BUTTON) {
            this.mSettingsHelper = settingsHelper;
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
            HashMap map2 = (HashMap) map;
            if (map2.containsKey(view)) {
                region.op((Rect) map2.get(view), Region.Op.UNION);
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
        this.mNavigationBarTransitions.transitionTo(this.mTransitionMode, (BasicRune.NAVBAR_ENABLED || !((Boolean) ((Optional) this.mCentralSurfacesOptionalLazy.get()).map(new NavigationBar$$ExternalSyntheticLambda28()).orElse(Boolean.FALSE)).booleanValue() || this.mNavigationBarWindowState == 2) ? false : true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bc, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c0, code lost:
    
        throw r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void destroyView() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        Trace.beginSection("NavigationBar#destroyView");
        try {
            setAutoHideController(null);
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            Trace.beginSection("NavigationBar#removeViewImmediate");
            try {
                this.mWindowManager.removeViewImmediate(((NavigationBarView) this.mView).getRootView());
            } catch (IllegalArgumentException e) {
                Log.e("NavigationBar", "Failed to removed view from WindowManager. The View wasn't attached.", e);
            }
            Trace.endSection();
            this.mNavigationModeController.removeListener(this.mModeChangedListener);
            edgeBackGestureHandler.mStateChangeCallback = null;
            this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            NotificationShadeDepthController notificationShadeDepthController = this.mNotificationShadeDepthController;
            ((ArrayList) notificationShadeDepthController.listeners).remove(this.mDepthListener);
            this.mDeviceConfigProxy.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
            this.mTaskStackChangeListeners.unregisterTaskStackListener(this.mTaskStackListener);
            if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && this.mDisplayId == 1) {
                LightBarController lightBarController = this.mMainLightBarController;
                if (lightBarController != null) {
                    ((LightBarControllerImpl) lightBarController).stop();
                    this.mMainLightBarController = null;
                }
                LightBarController lightBarController2 = this.mLightBarController;
                if (lightBarController2 != null) {
                    ((LightBarControllerImpl) lightBarController2).stop();
                }
                edgeBackGestureHandler.onNavBarDetached();
                AutoHideControllerImpl autoHideControllerImpl = this.mMainAutoHideController;
                if (autoHideControllerImpl != null) {
                    autoHideControllerImpl.mHandler.removeCallbacks(autoHideControllerImpl.mAutoHide);
                    this.mMainAutoHideController = null;
                }
            }
            if (BasicRune.NAVBAR_ENABLED) {
                setLightBarController(null);
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
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        int i4;
        if (i != this.mDisplayId) {
            return;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnSetDisableFlags(i2, i3), i);
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

    public final void dump(PrintWriter printWriter) throws Resources.NotFoundException {
        String resourceName;
        printWriter.println("NavigationBar (displayId=" + this.mDisplayId + "):");
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mStartingQuickSwitchRotation="), this.mStartingQuickSwitchRotation, printWriter, "  mCurrentRotation="), this.mCurrentRotation, printWriter, "  mHomeButtonLongPressDurationMs=");
        sbM.append(this.mHomeButtonLongPressDurationMs);
        printWriter.println(sbM.toString());
        printWriter.println("  mOverrideHomeButtonLongPressDurationMs=" + this.mOverrideHomeButtonLongPressDurationMs);
        printWriter.println("  mOverrideHomeButtonLongPressSlopMultiplier=" + this.mOverrideHomeButtonLongPressSlopMultiplier);
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mLongPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mNavigationBarWindowState=");
        sbM2.append(StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
        printWriter.println(sbM2.toString());
        printWriter.println("  mNavbarFlags=" + StatusBarManager.navbarFlagsToString(this.mNavbarFlags));
        printWriter.println("  mTransitionMode=".concat(BarTransitions.modeToString(this.mTransitionMode)));
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mTransientShown="), this.mTransientShown, printWriter, "  mTransientShownFromGestureOnSystemBar="), this.mTransientShownFromGestureOnSystemBar, printWriter, "  mScreenPinningActive="), this.mScreenPinningActive, printWriter);
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
                resourceName = navigationBarView.getContext().getResources().getResourceName(id);
            } catch (Resources.NotFoundException unused) {
                resourceName = "(unknown)";
            }
        } else {
            resourceName = "(null)";
        }
        printWriter.println(String.format("      mCurrentView: id=%s (%dx%d) %s %f", resourceName, Integer.valueOf(navigationBarView.mCurrentView.getWidth()), Integer.valueOf(navigationBarView.mCurrentView.getHeight()), NavigationBarView.visibilityToString(navigationBarView.mCurrentView.getVisibility()), Float.valueOf(navigationBarView.mCurrentView.getAlpha())));
        printWriter.println(String.format("      disabled=0x%08x vertical=%s darkIntensity=%.2f", Integer.valueOf(navigationBarView.mDisabledFlags), navigationBarView.mIsVertical ? "true" : "false", Float.valueOf(navigationBarView.mBarTransitions.mLightTransitionsController.mDarkIntensity)));
        printWriter.println("    mScreenOn: " + navigationBarView.mScreenOn);
        NavigationBarView.dumpButton(printWriter, "back", navigationBarView.getBackButton());
        NavigationBarView.dumpButton(printWriter, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, navigationBarView.getHomeButton());
        NavigationBarView.dumpButton(printWriter, "handle", navigationBarView.getHomeHandle());
        NavigationBarView.dumpButton(printWriter, "rcnt", navigationBarView.getRecentsButton());
        NavigationBarView.dumpButton(printWriter, "a11y", navigationBarView.getAccessibilityButton());
        NavigationBarView.dumpButton(printWriter, "ime", (ButtonDispatcher) navigationBarView.mButtonDispatchers.get(R.id.ime_switcher));
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            if (navigationBarView.mLastWindowInsets != null) {
                printWriter.println("    mLastWindowInsets: " + navigationBarView.mLastWindowInsets);
            } else {
                printWriter.println("    mLastWindowInsets: null");
            }
        }
        NavigationBarInflaterView navigationBarInflaterView = navigationBarView.mNavigationInflaterView;
        if (navigationBarInflaterView != null) {
            CarrierTextController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavigationBarInflaterView", "  mCurrentLayout: "), navigationBarInflaterView.mCurrentLayout, printWriter);
        }
        NavigationBarTransitions navigationBarTransitions = navigationBarView.mBarTransitions;
        navigationBarTransitions.getClass();
        printWriter.println("NavigationBarTransitions:");
        printWriter.println("  mMode: " + navigationBarTransitions.mMode);
        printWriter.println("  mAlwaysOpaque: false");
        StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mAllowAutoDimWallpaperNotVisible: "), navigationBarTransitions.mAllowAutoDimWallpaperNotVisible, printWriter, "  mWallpaperVisible: "), navigationBarTransitions.mWallpaperVisible, printWriter, "  mLightsOut: "), navigationBarTransitions.mLightsOut, printWriter, "  mAutoDim: "), navigationBarTransitions.mAutoDim, printWriter, "  bg overrideAlpha: "), navigationBarTransitions.mBarBackground.mOverrideAlpha, printWriter, "  bg color: "), navigationBarTransitions.mBarBackground.mColor, printWriter, "  bg frame: ");
        sbM3.append(navigationBarTransitions.mBarBackground.mFrame);
        printWriter.println(sbM3.toString());
        ContextualButtonGroup contextualButtonGroup = navigationBarView.mContextualButtonGroup;
        View view = contextualButtonGroup.mCurrentView;
        StringBuilder sbM4 = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ContextualButtonGroup", "  getVisibleContextButton(): ");
        sbM4.append(contextualButtonGroup.getVisibleContextButton());
        printWriter.println(sbM4.toString());
        StringBuilder sbM5 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  isVisible(): "), contextualButtonGroup.getVisibility() == 0, printWriter, "  attached(): ");
        sbM5.append(view != null && view.isAttachedToWindow());
        printWriter.println(sbM5.toString());
        printWriter.println("  mButtonData [ ");
        for (int size = ((ArrayList) contextualButtonGroup.mButtonData).size() - 1; size >= 0; size--) {
            ContextualButtonGroup.ButtonData buttonData = (ContextualButtonGroup.ButtonData) ((ArrayList) contextualButtonGroup.mButtonData).get(size);
            View view2 = buttonData.button.mCurrentView;
            StringBuilder sbM6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "    ", ": markedVisible=");
            sbM6.append(buttonData.markedVisible);
            sbM6.append(" visible=");
            ContextualButton contextualButton = buttonData.button;
            sbM6.append(contextualButton.getVisibility());
            sbM6.append(" attached=");
            sbM6.append(view2 != null && view2.isAttachedToWindow());
            sbM6.append(" alpha=");
            sbM6.append(contextualButton.getAlpha());
            printWriter.println(sbM6.toString());
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
        StringBuilder sbM7 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("\tmSamplingEnabled: "), regionSamplingHelper.mSamplingEnabled, printWriter, "\tmSamplingListenerRegistered: "), regionSamplingHelper.mSamplingListenerRegistered, printWriter, "\tmSamplingRequestBounds: ");
        sbM7.append(regionSamplingHelper.mSamplingRequestBounds);
        printWriter.println(sbM7.toString());
        printWriter.println("\tmRegisteredSamplingBounds: " + regionSamplingHelper.mRegisteredSamplingBounds);
        StringBuilder sbM8 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("\tmLastMedianLuma: "), regionSamplingHelper.mLastMedianLuma, printWriter, "\tmCurrentMedianLuma: "), regionSamplingHelper.mCurrentMedianLuma, printWriter, "\tmWindowVisible: "), regionSamplingHelper.mWindowVisible, printWriter, "\tmWindowHasBlurs: "), regionSamplingHelper.mWindowHasBlurs, printWriter, "\tmWaitingOnDraw: "), regionSamplingHelper.mWaitingOnDraw, printWriter, "\tmRegisteredStopLayer: ");
        sbM8.append(regionSamplingHelper.mRegisteredStopLayer);
        printWriter.println(sbM8.toString());
        printWriter.println("\tmWrappedStopLayer: " + regionSamplingHelper.mWrappedStopLayer);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("\tmIsDestroyed: "), regionSamplingHelper.mIsDestroyed, printWriter);
        AutoHideControllerImpl autoHideControllerImpl = this.mAutoHideController;
        if (autoHideControllerImpl != null) {
            StringBuilder sbM9 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "AutoHideController:", "\tmAutoHideSuspended="), autoHideControllerImpl.mAutoHideSuspended, printWriter, "\tisAnyTransientBarShown=");
            sbM9.append(autoHideControllerImpl.isAnyTransientBarShown());
            printWriter.println(sbM9.toString());
            printWriter.println("\thasPendingAutoHide=" + autoHideControllerImpl.mHandler.hasCallbacks(autoHideControllerImpl.mAutoHide));
            printWriter.println("\tgetAutoHideTimeout=" + autoHideControllerImpl.mAccessibilityManager.getRecommendedTimeoutMillis(2250, 4));
            printWriter.println("\tgetUserAutoHideTimeout=" + autoHideControllerImpl.mAccessibilityManager.getRecommendedTimeoutMillis(350, 4));
        }
    }

    public final WindowManager.LayoutParams getBarLayoutParams(int i) throws Resources.NotFoundException {
        WindowManager.LayoutParams barLayoutParamsForRotation = getBarLayoutParamsForRotation(i);
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i2 = 0; i2 <= 3; i2++) {
            barLayoutParamsForRotation.paramsForRotation[i2] = getBarLayoutParamsForRotation(i2);
        }
        return barLayoutParamsForRotation;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowManager.LayoutParams getBarLayoutParamsForRotation(int i) throws Resources.NotFoundException {
        boolean z;
        int i2;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int i3;
        int i4;
        boolean z2;
        char c;
        char c2;
        int i5;
        int dimensionPixelSize3;
        WindowManager.LayoutParams layoutParams;
        NavBarStoreAction.NavBarLayoutInfo navBarLayoutInfo;
        Context contextCreateCurrentUserContext = ((UserTrackerImpl) this.mUserContextProvider).createCurrentUserContext(this.mContext);
        WindowManager windowManager = this.mWindowManager;
        if (windowManager == null || windowManager.getCurrentWindowMetrics() == null) {
            z = true;
        } else {
            Rect bounds = this.mWindowManager.getCurrentWindowMetrics().getBounds();
            if (bounds.width() == bounds.height() || !contextCreateCurrentUserContext.getResources().getBoolean(android.R.bool.config_safe_media_volume_enabled)) {
                z = false;
            }
        }
        boolean z3 = BasicRune.NAVBAR_ENABLED;
        NavBarStore navBarStore = this.mNavBarStore;
        if (!z3 || (navBarLayoutInfo = (NavBarStoreAction.NavBarLayoutInfo) ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetBarLayoutParams(i), this.mContext.getDisplayId(), null)) == null) {
            i2 = 80;
            dimensionPixelSize = -1;
            dimensionPixelSize2 = -1;
            i3 = -1;
            i4 = -1;
            z2 = false;
        } else {
            dimensionPixelSize = navBarLayoutInfo.height;
            dimensionPixelSize2 = navBarLayoutInfo.insetHeight;
            i3 = navBarLayoutInfo.insetWidth;
            i4 = navBarLayoutInfo.width;
            i2 = navBarLayoutInfo.gravity;
            z2 = true;
        }
        if (z3 && z2) {
            c = 2;
        } else {
            if (z) {
                if (i == -1 || i == 0) {
                    c = 2;
                    dimensionPixelSize = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_right_edge_size);
                    dimensionPixelSize2 = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.select_dialog_drawable_padding_start_material);
                } else if (i == 1) {
                    c2 = 2;
                    i5 = dimensionPixelSize;
                    dimensionPixelSize3 = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.snooze_and_bubble_gone_padding_end);
                    i2 = 5;
                } else if (i != 2) {
                    char c3 = 2;
                    c = c3;
                    if (i == 3) {
                        i5 = dimensionPixelSize;
                        dimensionPixelSize3 = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.snooze_and_bubble_gone_padding_end);
                        i2 = 3;
                        c2 = c3;
                    }
                }
                layoutParams = new WindowManager.LayoutParams(dimensionPixelSize3, i5, 2019, 537133096, -3);
                layoutParams.gravity = i2;
                EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
                if (z3) {
                    InsetsFrameProvider insetsFrameProvider = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.navigationBars());
                    if (dimensionPixelSize2 != -1 && !edgeBackGestureHandler.mIsButtonForcedVisible) {
                        insetsFrameProvider.setInsetsSize(Insets.of(0, 0, 0, dimensionPixelSize2));
                    }
                    insetsFrameProvider.setFlags(!contextCreateCurrentUserContext.getResources().getBoolean(android.R.bool.config_searchAllEntrypointsEnabledDefault) ? 1 : 0, 1);
                    InsetsFrameProvider insetsFrameProvider2 = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.tappableElement());
                    if (contextCreateCurrentUserContext.getResources().getBoolean(android.R.bool.config_sendAudioBecomingNoisy)) {
                        insetsFrameProvider2.setInsetsSize(Insets.NONE);
                    }
                    int dimensionPixelSize4 = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.seekbar_track_background_height_material);
                    boolean zIsHandlingGestures = edgeBackGestureHandler.isHandlingGestures();
                    InsetsFrameProvider insetsFrameProvider3 = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.mandatorySystemGestures());
                    if (zIsHandlingGestures) {
                        insetsFrameProvider3.setInsetsSize(Insets.of(0, 0, 0, dimensionPixelSize4));
                    }
                    int i6 = zIsHandlingGestures ? edgeBackGestureHandler.mEdgeWidthLeft : 0;
                    int i7 = zIsHandlingGestures ? edgeBackGestureHandler.mEdgeWidthRight : 0;
                    InsetsFrameProvider[] insetsFrameProviderArr = new InsetsFrameProvider[5];
                    insetsFrameProviderArr[0] = insetsFrameProvider;
                    insetsFrameProviderArr[1] = insetsFrameProvider2;
                    insetsFrameProviderArr[c2] = insetsFrameProvider3;
                    insetsFrameProviderArr[3] = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(i6, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(i6, 0, 0, 0));
                    insetsFrameProviderArr[4] = new InsetsFrameProvider(this.mInsetsSourceOwner, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, i7, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, i7, 0));
                    layoutParams.providedInsets = insetsFrameProviderArr;
                } else {
                    NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
                    InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.GetImeInsets(dimensionPixelSize2, i3, i, z), this.mDisplayId, null))});
                    insetsSizeOverrides.setInsetsSize((Insets) navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.GetNavBarInsets(dimensionPixelSize2, i3, i), this.mDisplayId, null));
                    insetsSizeOverrides.setFlags(!contextCreateCurrentUserContext.getResources().getBoolean(android.R.bool.config_searchAllEntrypointsEnabledDefault) ? 1 : 0, 1);
                    InsetsFrameProvider insetsFrameProvider4 = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.tappableElement());
                    if (contextCreateCurrentUserContext.getResources().getBoolean(android.R.bool.config_sendAudioBecomingNoisy)) {
                        insetsFrameProvider4.setInsetsSize(Insets.NONE);
                    }
                    InsetsFrameProvider insetsFrameProvider5 = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.mandatorySystemGestures());
                    insetsFrameProvider5.setInsetsSize((Insets) navBarStoreImpl.handleEvent(this, new EventTypeFactory.EventType.GetMandatoryInsets(i, z), this.mDisplayId, null));
                    edgeBackGestureHandler.updateCurrentUserResources();
                    Context context = this.mContext;
                    NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                    int i8 = Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0);
                    int i9 = Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
                    boolean z4 = (i8 == 0 || i9 == 0 || i9 != 1) ? false : true;
                    int i10 = z4 ? edgeBackGestureHandler.mEdgeWidthLeft : 0;
                    int i11 = z4 ? edgeBackGestureHandler.mEdgeWidthRight : 0;
                    InsetsFrameProvider[] insetsFrameProviderArr2 = new InsetsFrameProvider[5];
                    insetsFrameProviderArr2[0] = insetsSizeOverrides;
                    insetsFrameProviderArr2[1] = insetsFrameProvider4;
                    insetsFrameProviderArr2[c2] = insetsFrameProvider5;
                    insetsFrameProviderArr2[3] = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(i10, 0, 0, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(i10, 0, 0, 0));
                    insetsFrameProviderArr2[4] = new InsetsFrameProvider(this.mInsetsSourceOwner, 1, WindowInsets.Type.systemGestures()).setSource(0).setInsetsSize(Insets.of(0, 0, i11, 0)).setMinimalInsetsSizeInDisplayCutoutSafe(Insets.of(0, 0, i11, 0));
                    layoutParams.providedInsets = insetsFrameProviderArr2;
                }
                layoutParams.token = new Binder();
                if (z3) {
                    layoutParams.accessibilityTitle = contextCreateCurrentUserContext.getString(R.string.nav_bar);
                } else {
                    layoutParams.accessibilityTitle = contextCreateCurrentUserContext.getString(R.string.samsung_nav_bar);
                }
                layoutParams.privateFlags |= 16777216;
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || !((NavBarStateManagerImpl) this.mNavBarStateManager).supportLargeCoverScreenNavBar()) {
                    layoutParams.privateFlags |= 4096;
                }
                layoutParams.layoutInDisplayCutoutMode = 3;
                layoutParams.windowAnimations = 0;
                layoutParams.setTitle("NavigationBar" + contextCreateCurrentUserContext.getDisplayId());
                layoutParams.setFitInsetsTypes(0);
                layoutParams.setTrustedOverlay();
                return layoutParams;
            }
            dimensionPixelSize = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_right_edge_size);
            dimensionPixelSize2 = contextCreateCurrentUserContext.getResources().getDimensionPixelSize(android.R.dimen.select_dialog_drawable_padding_start_material);
            c = 2;
        }
        i5 = dimensionPixelSize;
        dimensionPixelSize3 = i4;
        c2 = c;
        layoutParams = new WindowManager.LayoutParams(dimensionPixelSize3, i5, 2019, 537133096, -3);
        layoutParams.gravity = i2;
        EdgeBackGestureHandler edgeBackGestureHandler2 = this.mEdgeBackGestureHandler;
        if (z3) {
        }
        layoutParams.token = new Binder();
        if (z3) {
        }
        layoutParams.privateFlags |= 16777216;
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            layoutParams.privateFlags |= 4096;
        }
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.windowAnimations = 0;
        layoutParams.setTitle("NavigationBar" + contextCreateCurrentUserContext.getDisplayId());
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }

    public final Region getButtonLocations(boolean z, boolean z2) {
        if (z2 && !z) {
            z2 = false;
        }
        Region region = new Region();
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        NearestTouchFrame nearestTouchFrame = (NearestTouchFrame) (navigationBarView.mIsVertical ? navigationBarView.mNavigationInflaterView.mVertical : navigationBarView.mNavigationInflaterView.mHorizontal).findViewById(R.id.nav_buttons);
        nearestTouchFrame.getClass();
        HashMap map = new HashMap(((HashMap) nearestTouchFrame.mTouchableRegions).size());
        nearestTouchFrame.getLocationOnScreen(nearestTouchFrame.mTmpInt);
        for (Map.Entry entry : ((HashMap) nearestTouchFrame.mTouchableRegions).entrySet()) {
            View view = (View) entry.getKey();
            Rect rect = new Rect((Rect) entry.getValue());
            int[] iArr = nearestTouchFrame.mTmpInt;
            rect.offset(iArr[0], iArr[1]);
            map.put(view, rect);
        }
        updateButtonLocation(region, map, ((NavigationBarView) this.mView).getBackButton(), z, z2);
        updateButtonLocation(region, map, ((NavigationBarView) this.mView).getHomeButton(), z, z2);
        updateButtonLocation(region, map, ((NavigationBarView) this.mView).getRecentsButton(), z, z2);
        updateButtonLocation(region, map, (ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher), z, z2);
        updateButtonLocation(region, map, ((NavigationBarView) this.mView).getAccessibilityButton(), z, z2);
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

    public int getNavbarFlags() {
        return this.mNavbarFlags;
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
        int iTransitionMode = NavBarHelper.transitionMode(this.mAppearance, z);
        if (!updateTransitionMode(iTransitionMode) || (lightBarController = this.mLightBarController) == null) {
            return;
        }
        LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) lightBarController;
        lightBarControllerImpl.mHasLightNavigationBar = LightBarControllerImpl.isLight(lightBarControllerImpl.mAppearance, iTransitionMode, 16);
        if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
            lightBarControllerImpl.mNavigationBarMode = iTransitionMode;
            lightBarControllerImpl.reevaluate();
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda20] */
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
        try {
            this.mWindowManager.addView(this.mOrientationHandle, layoutParams2);
        } catch (WindowManager.InvalidDisplayException e) {
            Log.e("NavigationBar", "Unable to add view to WindowManager. Display with id " + this.mDisplayId + " does not exist anymore", e);
        }
        this.mOrientationHandle.setVisibility(8);
        logNavbarOrientation("initSecondaryHomeHandleForRotation");
        this.mOrientationParams.setFitInsetsTypes(0);
        this.mOrientationHandleGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda20
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                NavigationBar navigationBar = this.f$0;
                if (navigationBar.mStartingQuickSwitchRotation == -1) {
                    return;
                }
                RectF rectFComputeHomeHandleBounds = navigationBar.mOrientationHandle.computeHomeHandleBounds();
                navigationBar.mOrientationHandle.mapRectFromViewToScreenCoords(rectFComputeHomeHandleBounds, true);
                Rect rect = new Rect();
                rectFComputeHomeHandleBounds.roundOut(rect);
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
        Function1 function1 = new Function1() { // from class: com.android.systemui.navigationbar.views.buttons.NavbarOrientationTrackingLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                navbarOrientationTrackingLogger.getClass();
                int i3 = int2 - int1;
                if (i3 < 0) {
                    i3 += 4;
                }
                String str2 = i3 != 1 ? i3 != 2 ? i3 != 3 ? "0" : "270" : "180" : DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD;
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Caller Method: ", str1, "\n\tNavbar Visible: ", "\n\tImmersive Mode: ", bool1);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, bool2, "\n\tSecondary Handle Visible: ", bool3, "\n\tDelta Rotation: ");
                sbM.append(str2);
                sbM.append("\n\tStarting QuickSwitch Rotation: ");
                sbM.append(int12);
                sbM.append("\n\tCurrent Rotation: ");
                return ReorderTile$$ExternalSyntheticOutline0.m(int22, "\n", sbM);
            }
        };
        LogBuffer logBuffer = navbarOrientationTrackingLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NavbarOrientationTracking", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z3;
        logMessageImpl.bool3 = z;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i;
        logBuffer.commit(logMessageObtain);
    }

    public final void notifyNavigationBarScreenOn() {
        ((NavigationBarView) this.mView).updateNavButtonIcons();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (this.mAutoHideController == null) {
            return;
        }
        this.mLogWrapper.dp("NavigationBar", KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("notifyRequestedGameToolsWin visible : ", z));
        AutoHideControllerImpl autoHideControllerImpl = this.mAutoHideController;
        autoHideControllerImpl.mGameToolsShown = z;
        if (z) {
            autoHideControllerImpl.suspendAutoHide();
        } else {
            autoHideControllerImpl.resumeSuspendedAutoHideImmediately();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        ((SysUiStateImpl) this.mSysUiFlagsContainer.setFlag(274877906944L, z).setFlag(549755813888L, z2)).commitUpdate();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_GESTURE && this.mDisplayId == i) {
            this.mLogWrapper.dp("NavigationBar", String.format("notifySamsungPayInfo displayId : %d, visible: %s", Integer.valueOf(i), Boolean.valueOf(z)));
            this.mDisplayTracker.getClass();
            if (i == 0) {
                int iWidth = rect.width();
                LauncherProxyService launcherProxyService = this.mLauncherProxyService;
                launcherProxyService.getClass();
                try {
                    ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
                    if (iLauncherProxy != null) {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).notifyPayInfo(iWidth, z);
                    }
                } catch (RemoteException e) {
                    Log.e("LauncherProxyService", "Failed to notify pay info.", e);
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

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda4] */
    public boolean onHomeLongClick(View view) throws PackageManager.NameNotFoundException {
        if (((NavigationBarView) this.mView).getRecentsButton().getVisibility() != 0 && this.mScreenPinningActive) {
            return onLongPressNavigationButtons(view, R.id.home);
        }
        if (!((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get() || (this.mDisabledFlags1 & 33554432) != 0) {
            return false;
        }
        this.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
        this.mUiEventLogger.log(NavBarActionEvent.NAVBAR_ASSIST_LONGPRESS);
        final Bundle bundle = new Bundle();
        bundle.putInt("invocation_type", 5);
        Lazy lazy = this.mAssistManagerLazy;
        if (((AssistManager) lazy.get()).shouldOverrideAssist(5) && (view instanceof KeyButtonView)) {
            ((KeyButtonView) view).mRipple.mOnInvisibleRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() throws PackageManager.NameNotFoundException {
                    NavigationBar navigationBar = this.f$0;
                    ((AssistManager) navigationBar.mAssistManagerLazy.get()).startAssist(bundle);
                }
            };
        } else {
            ((AssistManager) lazy.get()).startAssist(bundle);
        }
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda5(0));
        ((NavigationBarView) this.mView).abortCurrentGesture();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
    
        if (r1 != 3) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onHomeTouch(View view, MotionEvent motionEvent) {
        if (this.mHomeBlockedThisTouch && motionEvent.getActionMasked() != 0) {
            return true;
        }
        Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
        int action = motionEvent.getAction();
        NavigationBar$$ExternalSyntheticLambda0 navigationBar$$ExternalSyntheticLambda0 = this.mOnVariableDurationHomeLongClick;
        Handler handler = this.mHandler;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (handler.hasCallbacks(navigationBar$$ExternalSyntheticLambda0)) {
                        float fFloatValue = ((Float) this.mOverrideHomeButtonLongPressSlopMultiplier.orElse(Float.valueOf(1.0f))).floatValue() * 3.0f * ViewConfiguration.get(this.mContext).getScaledTouchSlop();
                        float x = motionEvent.getX() - this.mCurrentDownEvent.getX();
                        float y = motionEvent.getY() - this.mCurrentDownEvent.getY();
                        if ((y * y) + (x * x) > fFloatValue * fFloatValue) {
                            Log.i("NavigationBar", "Touch slop passed. Abort.");
                            ((NavigationBarView) this.mView).abortCurrentGesture();
                            handler.removeCallbacks(navigationBar$$ExternalSyntheticLambda0);
                            return false;
                        }
                    }
                }
            }
            handler.removeCallbacks(navigationBar$$ExternalSyntheticLambda0);
            optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda5(0));
            return false;
        }
        MotionEvent motionEvent2 = this.mCurrentDownEvent;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        this.mCurrentDownEvent = MotionEvent.obtain(motionEvent);
        this.mHomeBlockedThisTouch = false;
        if (this.mTelecomManagerOptional.isPresent() && ((TelecomManager) this.mTelecomManagerOptional.get()).isRinging() && ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            Log.i("NavigationBar", "Ignoring HOME; there's a ringing incoming call. No heads up");
            this.mHomeBlockedThisTouch = true;
            return true;
        }
        if (this.mLongPressHomeEnabled) {
            if (this.mOverrideHomeButtonLongPressDurationMs.isPresent()) {
                Log.d("NavigationBar", "ACTION_DOWN Launcher override duration: " + this.mOverrideHomeButtonLongPressDurationMs.get());
                handler.postDelayed(navigationBar$$ExternalSyntheticLambda0, ((Long) this.mOverrideHomeButtonLongPressDurationMs.get()).longValue());
                return false;
            }
            if (this.mOverrideHomeButtonLongPressSlopMultiplier.isPresent()) {
                Log.d("NavigationBar", "ACTION_DOWN default duration: " + ViewConfiguration.getLongPressTimeout());
                handler.postDelayed(navigationBar$$ExternalSyntheticLambda0, (long) ViewConfiguration.getLongPressTimeout());
                return false;
            }
            this.mHomeButtonLongPressDurationMs.ifPresent(new NavigationBar$$ExternalSyntheticLambda1(this, 1));
        }
        return false;
    }

    public void onImeSwitcherClick(View view) {
        NavBarButtonClickLogger navBarButtonClickLogger = this.mNavBarButtonClickLogger;
        navBarButtonClickLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda0 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = navBarButtonClickLogger.buffer;
        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$$ExternalSyntheticLambda0, null));
        this.mInputMethodManager.showInputMethodPickerFromSystem(true, this.mDisplayId);
        this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
    }

    public boolean onImeSwitcherLongClick(View view) {
        return false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() throws Resources.NotFoundException {
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
        edgeBackGestureHandler.mStateChangeCallback = new NavigationBar$$ExternalSyntheticLambda0(navigationBarView2, 0);
        edgeBackGestureHandler.mButtonForcedVisibleCallback = new NavigationBar$$ExternalSyntheticLambda1(this, 0);
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
        try {
            this.mWindowManager.addView(this.mFrame, getBarLayoutParams(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation()));
        } catch (WindowManager.InvalidDisplayException e) {
            Log.e("NavigationBar", "Unable to add view to WindowManager. Display with id " + this.mDisplayId + " does not exist anymore", e);
        }
        int displayId2 = this.mContext.getDisplayId();
        this.mDisplayId = displayId2;
        this.mDisplayTracker.getClass();
        this.mIsOnDefaultDisplay = displayId2 == 0;
        boolean z4 = BasicRune.NAVBAR_ENABLED;
        NavBarHelper navBarHelper = this.mNavBarHelper;
        if (z4) {
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
        this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(deviceConfigProxy.getLong("systemui", "home_button_long_press_duration_ms", 0L))).filter(new NavigationBar$$ExternalSyntheticLambda3(0));
        navBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new MobileStatusTracker$$ExternalSyntheticLambda1(handler), this.mOnPropertiesChangedListener);
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            this.mDisabledFlags1 = bundle.getInt("disabled_state", 0);
            this.mDisabledFlags2 = this.mSavedState.getInt("disabled2_state", 0);
            this.mAppearance = this.mSavedState.getInt("appearance", 0);
            this.mBehavior = this.mSavedState.getInt("behavior", 0);
            this.mTransientShown = this.mSavedState.getBoolean("transient_state", false);
            if (z4) {
                this.mNavbarFlags = this.mSavedState.getInt("icon_hints", 0);
            }
        }
        commandQueue.recomputeDisableFlags(this.mDisplayId, false);
        ((ArrayList) this.mNotificationShadeDepthController.listeners).add(this.mDepthListener);
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
        if (z4) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this.mKeyguardStateControllerCallback);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008e A[Catch: RemoteException -> 0x0033, TRY_ENTER, TryCatch #0 {RemoteException -> 0x0033, blocks: (B:3:0x0001, B:10:0x0028, B:11:0x002f, B:42:0x008e, B:44:0x009a, B:32:0x006b, B:7:0x001a, B:15:0x0036, B:18:0x003e, B:22:0x0051, B:26:0x005c, B:21:0x0049, B:27:0x005f, B:34:0x0073, B:38:0x007c), top: B:47:0x0001, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onLongPressNavigationButtons(View view, int i) {
        boolean z;
        NavigationBarView navigationBarView;
        try {
            IActivityTaskManager service = ActivityTaskManager.getService();
            boolean zIsTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
            boolean zIsInLockTaskMode = service.isInLockTaskMode();
            try {
                if (!zIsInLockTaskMode || zIsTouchExplorationEnabled) {
                    if (view.getId() == R.id.back) {
                        z = true;
                    } else {
                        if (zIsTouchExplorationEnabled && zIsInLockTaskMode) {
                            service.stopSystemLockTaskMode();
                            navigationBarView = (NavigationBarView) this.mView;
                            navigationBarView.updateNavButtonIcons();
                            return true;
                        }
                        if (view.getId() != i) {
                            z = false;
                        } else if (i != R.id.recent_apps) {
                            return onHomeLongClick(((NavigationBarView) this.mView).getHomeButton().mCurrentView);
                        }
                    }
                    if (z) {
                        KeyButtonView keyButtonView = (KeyButtonView) view;
                        keyButtonView.sendEvent(0, 128);
                        keyButtonView.sendAccessibilityEvent(2);
                        return true;
                    }
                } else {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.mLastLockToAppLongPress < 200) {
                        service.stopSystemLockTaskMode();
                        navigationBarView = (NavigationBarView) this.mView;
                        navigationBarView.updateNavButtonIcons();
                        return true;
                    }
                    if (view.getId() != R.id.back) {
                        z = false;
                        this.mLastLockToAppLongPress = jCurrentTimeMillis;
                        if (z) {
                        }
                    } else {
                        if (!(i == R.id.recent_apps ? ((NavigationBarView) this.mView).getRecentsButton() : ((NavigationBarView) this.mView).getHomeButton()).mCurrentView.isPressed()) {
                            z = true;
                        }
                        this.mLastLockToAppLongPress = jCurrentTimeMillis;
                        if (z) {
                        }
                    }
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

    /* JADX WARN: Removed duplicated region for block: B:128:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01ed A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0210  */
    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRotationProposal(int i, boolean z) {
        if (((NavigationBarView) this.mView).isAttachedToWindow()) {
            int i2 = 0;
            if (BasicRune.NAVBAR_POLICY_VISIBILITY && ((NavBarStateManagerImpl) this.mNavBarStateManager).isTaskBarEnabled(false)) {
                return;
            }
            int i3 = this.mDisabledFlags2;
            boolean z2 = RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW;
            boolean z3 = (i3 & 16) != 0;
            RotationButtonController rotationButtonController = ((NavigationBarView) this.mView).mRotationButtonController;
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                StringBuilder sb = new StringBuilder("onRotationProposal proposedRotation=");
                sb.append(Surface.rotationToString(i));
                sb.append(", isValid=");
                sb.append(z);
                sb.append(", mNavBarWindowState=");
                sb.append(StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
                sb.append(", rotateSuggestionsDisabled=");
                sb.append(z3);
                sb.append(", isRotateButtonVisible=");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, rotationButtonController.mRotationButton.mIsShowing, "NavigationBar");
            }
            if (z3) {
                return;
            }
            if (BasicRune.NAVBAR_ENABLED && ((NavBarStateManagerImpl) this.mNavBarStateManager).rotateDisabledByPolicy()) {
                Log.d("NavigationBar", "rotation button disabled by policy");
                return;
            }
            if (Settings.Secure.getInt(rotationButtonController.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) == 0 && (BasicRuneWrapper.NAVBAR_ENABLED || RotationButtonController.OEM_DISALLOW_ROTATION_IN_SUW)) {
                return;
            }
            int iIntValue = ((Integer) rotationButtonController.mWindowRotationProvider.get()).intValue();
            FloatingRotationButton floatingRotationButton = rotationButtonController.mRotationButton;
            if (floatingRotationButton.mKeyButtonView != null) {
                if (!rotationButtonController.mHomeRotationEnabled && rotationButtonController.mIsRecentsAnimationRunning) {
                    if (BasicRuneWrapper.NAVBAR_ENABLED) {
                        Log.d("RotationButtonController", "onRotationProposal home rotation disabled");
                        return;
                    }
                    return;
                }
                boolean z4 = BasicRuneWrapper.NAVBAR_ENABLED;
                if (z4 && i == -1) {
                    rotationButtonController.mLastUnknownRotationProposedTick = System.currentTimeMillis();
                    rotationButtonController.setRotateSuggestionButtonState(false);
                    Log.d("RotationButtonController", "onRotationProposal unknown rotation");
                    return;
                }
                if (!z) {
                    rotationButtonController.setRotateSuggestionButtonState(false);
                    if (BasicRuneWrapper.NAVBAR_ENABLED) {
                        Log.d("RotationButtonController", "onRotationProposal invalid");
                        return;
                    }
                    return;
                }
                Handler handler = rotationButtonController.mMainThreadHandler;
                RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0 = rotationButtonController.mCancelPendingRotationProposal;
                if (i == iIntValue) {
                    if (z4) {
                        rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                        if (rotationButtonController.mPendingRotationSuggestion && !floatingRotationButton.mIsShowing) {
                            rotationButtonController.mPendingRotationSuggestion = false;
                            handler.removeCallbacks(rotationButtonController$$ExternalSyntheticLambda0);
                        }
                        Log.d("RotationButtonController", "onRotationProposal rotation not changed");
                    }
                    handler.removeCallbacks(rotationButtonController.mRemoveRotationProposal);
                    rotationButtonController.setRotateSuggestionButtonState(false);
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    long j = rotationButtonController.mLastUnknownRotationProposedTick;
                    if (j != 0 && j + 1000 <= jCurrentTimeMillis) {
                        Log.d("RotationButtonController", "onRotationProposal rotation time over");
                        rotationButtonController.mLastUnknownRotationProposedTick = 0L;
                        return;
                    }
                }
                Log.i("RotationButtonController", "onRotationProposal(rotation=" + i + ")");
                rotationButtonController.mLastRotationSuggestion = i;
                boolean z5 = !(iIntValue == 0 && i == 1) && ((iIntValue == 0 && i == 2) || ((iIntValue == 0 && i == 3) || ((iIntValue == 1 && i == 0) || (!(iIntValue == 1 && i == 2) && ((iIntValue == 1 && i == 3) || ((iIntValue == 2 && i == 0) || ((iIntValue == 2 && i == 1) || (!(iIntValue == 2 && i == 3) && (!(iIntValue == 3 && i == 0) && ((iIntValue == 3 && i == 1) || (iIntValue == 3 && i == 2)))))))))));
                if (iIntValue == 0 || iIntValue == 2) {
                    rotationButtonController.mIconResId = z5 ? rotationButtonController.mIconCcwStart0ResId : rotationButtonController.mIconCwStart0ResId;
                } else {
                    rotationButtonController.mIconResId = z5 ? rotationButtonController.mIconCcwStart90ResId : rotationButtonController.mIconCwStart90ResId;
                }
                if (BasicRuneWrapper.NAVBAR_ENABLED) {
                    rotationButtonController.mIconResId = rotationButtonController.mSamsungRotateButtonResId;
                    RotationUtil.Companion.getClass();
                    boolean z6 = RotationUtil.ccwCheckArray[iIntValue][i];
                    int i4 = 83;
                    if (iIntValue == 0) {
                        if (i != 1) {
                            if (i != 2) {
                                if (i != 3) {
                                }
                            }
                            i4 = 51;
                        }
                        RotationUtil.floatingButtonPosition = i4;
                        if (iIntValue != 0) {
                        }
                    } else if (iIntValue == 1) {
                        if (i != 0) {
                            if (i != 2) {
                                if (i != 3) {
                                }
                            }
                        }
                        RotationUtil.floatingButtonPosition = i4;
                        if (iIntValue != 0) {
                        }
                    } else if (iIntValue == 2) {
                        if (i == 0) {
                            i4 = 51;
                        } else if (i != 1) {
                            if (i != 3) {
                            }
                        }
                        RotationUtil.floatingButtonPosition = i4;
                        if (iIntValue != 0) {
                            if (!z6) {
                            }
                            rotationButtonController.mStyleRes = i2;
                        }
                    } else if (iIntValue == 3) {
                        if (i != 0) {
                            if (i != 1) {
                                i4 = i != 2 ? 85 : 53;
                            }
                            i4 = 51;
                        }
                        RotationUtil.floatingButtonPosition = i4;
                        if (iIntValue != 0 || iIntValue == 2) {
                            i2 = !z6 ? rotationButtonController.mSamsungIconCCWStart90ResId : rotationButtonController.mSamsungIconCWStart90ResId;
                        } else if (i == 0 || i == 2) {
                            i2 = z6 ? rotationButtonController.mSamsungIconCCWStart0ResId : rotationButtonController.mSamsungIconCWStart0ResId;
                        } else if (iIntValue == 1 && i == 3) {
                            i2 = rotationButtonController.mSamsungIconCWStart180ResId;
                        } else if (iIntValue == 3 && i == 1) {
                            i2 = rotationButtonController.mSamsungIconCCWStart180ResId;
                        }
                        rotationButtonController.mStyleRes = i2;
                    }
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
                handler.postDelayed(rotationButtonController$$ExternalSyntheticLambda0, WakeLock.DEFAULT_MAX_TIMEOUT);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean zUpdateTransitionMode;
        if (i != this.mDisplayId) {
            return;
        }
        if (this.mAppearance != i2) {
            boolean z2 = BasicRune.NAVBAR_ADDITIONAL_LOG;
            if (z2 && z2) {
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
            zUpdateTransitionMode = updateTransitionMode(NavBarHelper.transitionMode(i2, this.mTransientShown));
        } else {
            zUpdateTransitionMode = false;
        }
        boolean z3 = zUpdateTransitionMode;
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            ((LightBarControllerImpl) lightBarController).onNavigationBarAppearanceChanged(i2, this.mTransitionMode, z3, z, str);
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
    public final void onViewAttached() throws Resources.NotFoundException {
        LightBarController lightBarControllerCreate;
        AutoHideControllerImpl autoHideControllerImpl;
        int i = 1;
        int i2 = 0;
        Display display = ((NavigationBarView) this.mView).getDisplay();
        ((NavigationBarView) this.mView).mRecentsOptional = this.mRecentsOptional;
        Lazy lazy = this.mCentralSurfacesOptionalLazy;
        boolean zIsPresent = ((Optional) lazy.get()).isPresent();
        ShadeViewController shadeViewController = this.mShadeViewController;
        if (zIsPresent) {
            NavigationBarView navigationBarView = (NavigationBarView) this.mView;
            navigationBarView.getClass();
            navigationBarView.mPanelExpansionInteractor = this.mPanelExpansionInteractor;
            if (shadeViewController != null) {
                shadeViewController.updateSystemUiStateFlags();
            }
        }
        ((NavigationBarView) this.mView).setDisabledFlags(this.mDisabledFlags1, this.mSysUiFlagsContainer);
        NavigationBarView navigationBarView2 = (NavigationBarView) this.mView;
        navigationBarView2.mOnVerticalChangedListener = new NavigationBar$$ExternalSyntheticLambda11(this);
        boolean z = navigationBarView2.mIsVertical;
        if (((Optional) lazy.get()).isPresent()) {
            shadeViewController.setQsScrimEnabled(!z);
        }
        ((NavigationBarView) this.mView).setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda10(this, i2));
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            LightBarTransitionsController lightBarTransitionsController = this.mNavigationBarTransitions.mLightTransitionsController;
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
        navigationBarView4.mUpdateActiveTouchRegionsCallback = new NavigationBar$$ExternalSyntheticLambda11(this);
        navigationBarView4.notifyActiveTouchRegions();
        ((NavigationBarView) this.mView).getViewTreeObserver().addOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        Optional optional = this.mPipOptional;
        NavigationBarView navigationBarView5 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView5);
        optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda12(navigationBarView5, i2));
        Optional optional2 = this.mBackAnimation;
        NavigationBarView navigationBarView6 = (NavigationBarView) this.mView;
        Objects.requireNonNull(navigationBarView6);
        optional2.ifPresent(new NavigationBar$$ExternalSyntheticLambda12(navigationBarView6, i));
        prepareNavigationBarView();
        checkNavBarModes();
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        notifyNavigationBarScreenOn();
        this.mLauncherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this.mLauncherProxyListener);
        updateSystemUiStateFlags();
        if (this.mIsOnDefaultDisplay) {
            RotationButtonController rotationButtonController3 = ((NavigationBarView) this.mView).mRotationButtonController;
            Boolean boolIsRotationLocked = RotationPolicyUtil.isRotationLocked(this.mContext);
            if (display != null && boolIsRotationLocked.booleanValue() && !DeviceType.isTablet() && !BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                RotationPolicy.setRotationLockAtAngle(rotationButtonController3.mContext, boolIsRotationLocked.booleanValue(), display.getRotation(), "NavigationBar#onViewAttached");
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
            lightBarControllerCreate = this.mMainLightBarController;
        } else {
            Context context = this.mContext;
            LightBarControllerImpl.LegacyFactory legacyFactory = (LightBarControllerImpl.LegacyFactory) this.mLightBarControllerFactory;
            legacyFactory.getClass();
            lightBarControllerCreate = legacyFactory.mFactory.create(context.getDisplayId(), legacyFactory.mApplicationScope, (DarkIconDispatcher) ((DarkIconDispatcherStoreImpl) legacyFactory.mDarkIconDispatcherStore).getDefaultDisplay(), (StatusBarModePerDisplayRepository) legacyFactory.mStatusBarModeRepositoryStore.getDefaultDisplay());
            lightBarControllerCreate.start();
        }
        setLightBarController(lightBarControllerCreate);
        int i5 = StatusBarConnectedDisplays.$r8$clinit;
        if (this.mIsOnDefaultDisplay) {
            autoHideControllerImpl = this.mMainAutoHideController;
        } else {
            Context context2 = this.mContext;
            AutoHideControllerImpl.Factory factory = this.mAutoHideControllerFactory;
            autoHideControllerImpl = new AutoHideControllerImpl(context2, factory.mHandler, factory.mIWindowManager);
        }
        setAutoHideController(autoHideControllerImpl);
        int iTransitionMode = NavBarHelper.transitionMode(this.mAppearance, this.mTransientShown);
        this.mTransitionMode = iTransitionMode;
        checkNavBarModes();
        AutoHideControllerImpl autoHideControllerImpl2 = this.mAutoHideController;
        if (autoHideControllerImpl2 != null) {
            autoHideControllerImpl2.touchAutoHide();
        }
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            ((LightBarControllerImpl) lightBarController).onNavigationBarAppearanceChanged(this.mAppearance, iTransitionMode, true, false, "restoreAppearanceAndTransientState");
        }
        if (BasicRune.NAVBAR_ENABLED) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(this.mTransitionMode));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        LightBarController lightBarController;
        EdgeBackGestureHandler edgeBackGestureHandler;
        BackAnimationPilferPointerCallbackManager backAnimationPilferPointerCallbackManager;
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
        this.mLauncherProxyService.removeCallback((LauncherProxyService.LauncherProxyListener) this.mLauncherProxyListener);
        ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
        this.mWakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        if (this.mOrientationHandle != null) {
            resetSecondaryHandle();
            ((ArrayList) navigationBarTransitions.mDarkIntensityListeners).remove(this.mOrientationHandleIntensityListener);
            try {
                this.mWindowManager.removeView(this.mOrientationHandle);
            } catch (IllegalArgumentException e) {
                Log.e("NavigationBar", "Trying to remove a View that is not attached", e);
            }
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
        optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda12(navigationBarView2, 2));
        this.mFrame = null;
        this.mOrientationHandle = null;
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && (backAnimationPilferPointerCallbackManager = (edgeBackGestureHandler = ((NavigationBarView) this.mView).mEdgeBackGestureHandler).mPilferPointerCallbackManager) != null) {
            BackAnimationPilferPointerCallbackManager.CompositeRunnable compositeRunnable = backAnimationPilferPointerCallbackManager.callbacks;
            compositeRunnable.runnables.remove(Integer.valueOf(edgeBackGestureHandler.mDisplayId));
            BackAnimationController.BackAnimationImpl backAnimationImpl = (BackAnimationController.BackAnimationImpl) backAnimationPilferPointerCallbackManager.backAnimation.orElse(null);
            if (backAnimationImpl != null) {
                BackAnimationController.this.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(1, backAnimationImpl, compositeRunnable));
            }
        }
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
        ((LightBarControllerImpl) lightBarController).mObserver.mList.remove(((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void orientSecondaryHomeHandle() {
        int iWidth;
        int height;
        if (this.mNavBarMode != 2 || this.mOrientationHandle == null) {
            return;
        }
        int i = this.mStartingQuickSwitchRotation;
        if (i == -1) {
            resetSecondaryHandle();
            return;
        }
        int i2 = i - this.mCurrentRotation;
        if (i2 < 0) {
            i2 += 4;
        }
        if (i == -1 || i2 == -1) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "secondary nav delta rotation: ", " current: ");
            sbM.append(this.mCurrentRotation);
            sbM.append(" starting: ");
            RecyclerView$$ExternalSyntheticOutline0.m(this.mStartingQuickSwitchRotation, "NavigationBar", sbM);
        }
        Rect bounds = this.mWindowManager.getCurrentWindowMetrics().getBounds();
        this.mOrientationHandle.mDeltaRotation = i2;
        int i3 = 3;
        if (i2 == 0) {
            if (!this.mShowOrientedHandleForImmersiveMode) {
                resetSecondaryHandle();
                return;
            } else {
                iWidth = bounds.width();
                height = ((NavigationBarView) this.mView).getHeight();
            }
        } else if (i2 == 1) {
            height = bounds.height();
            if (BasicRune.NAVBAR_GESTURE) {
                DisplayCutout displayCutout = this.mIndicatorGardenPresenter.inputProperties.displayCutout;
                if (displayCutout != null) {
                    Iterator<Rect> it = displayCutout.getBoundingRects().iterator();
                    int iHeight = it.hasNext() ? it.next().height() : 0;
                    height -= iHeight;
                }
            }
            iWidth = ((NavigationBarView) this.mView).getHeight();
        } else if (i2 != 2) {
            if (i2 != 3) {
                height = 0;
                iWidth = 0;
            }
        }
        WindowManager.LayoutParams layoutParams = this.mOrientationParams;
        if (i2 == 0) {
            i3 = 80;
        } else if (i2 != 1) {
            i3 = 5;
        }
        layoutParams.gravity = i3;
        layoutParams.height = height;
        layoutParams.width = iWidth;
        this.mWindowManager.updateViewLayout(this.mOrientationHandle, layoutParams);
        if (BasicRune.NAVBAR_GESTURE) {
            Rect rect = new Rect();
            ((NavigationBarView) this.mView).getHomeHandle().mCurrentView.getHitRect(rect);
            this.mOrientationHandle.mHomeHandleRect.set(rect);
            this.mOrientationHandle.setImageDrawable(((NavigationBarView) this.mView).getSecondaryHomeHandleDrawable(i2));
        }
        ((NavigationBarView) this.mView).setVisibility(8);
        this.mOrientationHandle.setVisibility(0);
        logNavbarOrientation("orientSecondaryHomeHandle");
    }

    public final void prepareNavigationBarView() {
        ((NavigationBarView) this.mView).reorient();
        ButtonDispatcher recentsButton = ((NavigationBarView) this.mView).getRecentsButton();
        final int i = 0;
        recentsButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda21
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int displayId;
                int i2 = i;
                NavigationBar navigationBar = this.f$0;
                switch (i2) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda0 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer = navBarButtonClickLogger.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$$ExternalSyntheticLambda0, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda5(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda02 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(3);
                        LogBuffer logBuffer2 = navBarButtonClickLogger2.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$$ExternalSyntheticLambda02, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            displayId = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            displayId = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(displayId);
                        break;
                    default:
                        navigationBar.onImeSwitcherClick(view);
                        break;
                }
            }
        });
        recentsButton.setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda10(this, 1));
        ButtonDispatcher homeButton = ((NavigationBarView) this.mView).getHomeButton();
        homeButton.setOnTouchListener(new NavigationBar$$ExternalSyntheticLambda10(this, 2));
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
        accessibilityButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda21
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int displayId;
                int i22 = i4;
                NavigationBar navigationBar = this.f$0;
                switch (i22) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda0 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer = navBarButtonClickLogger2.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$$ExternalSyntheticLambda0, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda5(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger22 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger22.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda02 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(3);
                        LogBuffer logBuffer2 = navBarButtonClickLogger22.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$$ExternalSyntheticLambda02, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            displayId = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            displayId = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(displayId);
                        break;
                    default:
                        navigationBar.onImeSwitcherClick(view);
                        break;
                }
            }
        });
        accessibilityButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda6(this, i4));
        updateAccessibilityStateFlags();
        ButtonDispatcher buttonDispatcher = (ButtonDispatcher) ((NavigationBarView) this.mView).mButtonDispatchers.get(R.id.ime_switcher);
        final int i5 = 2;
        buttonDispatcher.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda21
            public final /* synthetic */ NavigationBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int displayId;
                int i22 = i5;
                NavigationBar navigationBar = this.f$0;
                switch (i22) {
                    case 0:
                        NavBarButtonClickLogger navBarButtonClickLogger2 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda0 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer = navBarButtonClickLogger2.buffer;
                        logBuffer.commit(logBuffer.obtain("NavBarButtonClick", logLevel, navBarButtonClickLogger$$ExternalSyntheticLambda0, null));
                        if (LatencyTracker.isEnabled(navigationBar.mContext)) {
                            LatencyTracker.getInstance(navigationBar.mContext).onActionStart(1);
                        }
                        ((Optional) navigationBar.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda5(0));
                        navigationBar.mCommandQueue.toggleRecentApps();
                        break;
                    case 1:
                        NavBarButtonClickLogger navBarButtonClickLogger22 = navigationBar.mNavBarButtonClickLogger;
                        navBarButtonClickLogger22.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        NavBarButtonClickLogger$$ExternalSyntheticLambda0 navBarButtonClickLogger$$ExternalSyntheticLambda02 = new NavBarButtonClickLogger$$ExternalSyntheticLambda0(3);
                        LogBuffer logBuffer2 = navBarButtonClickLogger22.buffer;
                        logBuffer2.commit(logBuffer2.obtain("NavBarButtonClick", logLevel2, navBarButtonClickLogger$$ExternalSyntheticLambda02, null));
                        Display display = view.getDisplay();
                        AccessibilityManager accessibilityManager = navigationBar.mAccessibilityManager;
                        if (display != null) {
                            displayId = display.getDisplayId();
                        } else {
                            navigationBar.mDisplayTracker.getClass();
                            displayId = 0;
                        }
                        accessibilityManager.notifyAccessibilityButtonClicked(displayId);
                        break;
                    default:
                        navigationBar.onImeSwitcherClick(view);
                        break;
                }
            }
        });
        updateScreenPinningGestures();
        if (BasicRune.NAVBAR_ENABLED) {
            backButton.setOnClickListener(null);
            recentsButton.setOnClickListener(null);
            recentsButton.setOnTouchListener(null);
            backButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda22
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    this.f$0.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            recentsButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda22
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    this.f$0.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            homeButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda22
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    this.f$0.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            buttonDispatcher.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda22
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    this.f$0.getClass();
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 10) {
                        return false;
                    }
                    ((KeyButtonView) view).onTouchEvent(motionEvent);
                    return false;
                }
            });
            accessibilityButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda22
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    this.f$0.getClass();
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
            ((NavigationBarView) this.mView).getHomeButton().setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda6(this, 2));
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

    public final void setAutoHideController(AutoHideControllerImpl autoHideControllerImpl) {
        boolean z = BasicRune.NAVBAR_POLICY_VISIBILITY;
        AnonymousClass1 anonymousClass1 = this.mAutoHideUiElement;
        if (z) {
            if (autoHideControllerImpl != null) {
                autoHideControllerImpl.registerElementToObserver(anonymousClass1);
            } else {
                AutoHideControllerImpl autoHideControllerImpl2 = this.mAutoHideController;
                if (autoHideControllerImpl2 != null) {
                    AutoHideControllerImpl.AutoHideUiElementObserver autoHideUiElementObserver = autoHideControllerImpl2.mObserver;
                    autoHideUiElementObserver.getClass();
                    ((ArrayList) autoHideUiElementObserver.mList).remove(anonymousClass1);
                }
            }
        }
        this.mAutoHideController = autoHideControllerImpl;
        if (autoHideControllerImpl != null) {
            autoHideControllerImpl.mNavigationBar = anonymousClass1;
        }
        ((NavigationBarView) this.mView).mAutoHideController = autoHideControllerImpl;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, int i2, int i3, boolean z) {
        if (i != this.mDisplayId) {
            return;
        }
        NavBarHelper navBarHelper = this.mNavBarHelper;
        boolean zIsImeVisible = navBarHelper.isImeVisible(i2);
        boolean z2 = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        if (z2 && ((NavBarStateManagerImpl) this.mNavBarStateManager).supportLargeCoverScreenNavBar()) {
            zIsImeVisible |= this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
        }
        int iUpdateNavbarFlagsFromIme = Utilities.updateNavbarFlagsFromIme(this.mNavbarFlags, i3, zIsImeVisible, z);
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "setImeWindowStatus displayId=", " vis=", " backDisposition=");
            sbM.append(i3);
            sbM.append(" showImeSwitcher=");
            sbM.append(z);
            sbM.append(" isImeVisible=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, zIsImeVisible, "NavigationBar");
        }
        if (iUpdateNavbarFlagsFromIme == this.mNavbarFlags) {
            return;
        }
        boolean z3 = BasicRune.NAVBAR_ENABLED;
        NavBarStore navBarStore = this.mNavBarStore;
        if (z3) {
            navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarIconHintChanged(iUpdateNavbarFlagsFromIme), this.mDisplayId);
            navBarHelper.mLastIMEhints = iUpdateNavbarFlagsFromIme;
        }
        if (iUpdateNavbarFlagsFromIme != this.mNavbarFlags) {
            if (z3 || !Utilities.isLargeScreen(this.mContext)) {
                boolean z4 = (iUpdateNavbarFlagsFromIme & 1) != 0;
                if (z4 != ((this.mNavbarFlags & 1) != 0)) {
                    ((NavigationBarView) this.mView).onBackDismissImeChanged(z4);
                }
                this.mImeVisible = (iUpdateNavbarFlagsFromIme & 2) != 0;
                NavigationBarView navigationBarView = (NavigationBarView) this.mView;
                if (iUpdateNavbarFlagsFromIme != navigationBarView.mNavbarFlags) {
                    navigationBarView.mNavbarFlags = iUpdateNavbarFlagsFromIme;
                    navigationBarView.updateNavButtonIcons();
                }
            }
            this.mNavbarFlags = iUpdateNavbarFlagsFromIme;
        }
        if (this.mIsOnDefaultDisplay) {
            ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda5(1));
        } else {
            checkNavBarModes();
        }
        updateSystemUiStateFlags();
        if (z2 && ((NavBarStateManagerImpl) this.mNavBarStateManager).supportLargeCoverScreenNavBar()) {
            navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(zIsImeVisible, ((NavBarStateManagerImpl) this.mNavBarStateManager).isLargeCoverTaskEnabled()), this.mDisplayId);
        }
    }

    public final void setLightBarController(LightBarController lightBarController) {
        this.mLightBarController = lightBarController;
        if (lightBarController != null) {
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) lightBarController;
            lightBarControllerImpl.mNavigationBarController = this.mNavigationBarTransitions.mLightTransitionsController;
            lightBarControllerImpl.updateNavigation();
            if (BasicRune.NAVBAR_ENABLED) {
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnLightBarControllerCreated(this.mLightBarController), this.mDisplayId);
            }
            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                LightBarController lightBarController2 = this.mLightBarController;
                LightBarTransitionsController lightBarTransitionsController = ((NavigationBarView) this.mView).mBarTransitions.mLightTransitionsController;
                LightBarControllerImpl.LightBarTransientObserver lightBarTransientObserver = ((LightBarControllerImpl) lightBarController2).mObserver;
                lightBarTransientObserver.mList.remove(lightBarTransitionsController);
                if (lightBarTransitionsController != null) {
                    lightBarTransientObserver.mList.add(lightBarTransitionsController);
                }
            }
        }
    }

    public final void setNavBarMode(int i) throws Resources.NotFoundException {
        NavigationBarView navigationBarView = (NavigationBarView) this.mView;
        boolean z = this.mNavigationModeController.mCurrentUserContext.getResources().getBoolean(android.R.bool.config_maskMainBuiltInDisplayCutout);
        navigationBarView.mNavBarMode = i;
        navigationBarView.mImeDrawsImeNavBar = z;
        navigationBarView.mBarTransitions.mNavBarMode = i;
        navigationBarView.mEdgeBackGestureHandler.onNavigationModeChanged(i);
        navigationBarView.mRotationButtonController.mNavBarMode = navigationBarView.mNavBarMode;
        navigationBarView.updateRotationButton();
        boolean zIsGesturalMode = QuickStepContract.isGesturalMode(i);
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (zIsGesturalMode) {
            regionSamplingHelper.start(this.mSamplingBounds);
        } else {
            regionSamplingHelper.stop();
        }
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

    public final void updateNavBarStyle(boolean z) {
        RegionSamplingHelper regionSamplingHelper = this.mRegionSamplingHelper;
        if (z) {
            regionSamplingHelper.stop();
        } else if (((NavBarStateManagerImpl) this.mNavBarStateManager).isGestureMode()) {
            regionSamplingHelper.updateSamplingRect();
            regionSamplingHelper.start(this.mSamplingBounds);
        }
        ((NavigationBarView) this.mView).updateNavButtonIcons();
    }

    public final void updateScreenPinningGestures() {
        boolean z;
        if (!BasicRune.NAVBAR_ENABLED || BasicRune.NAVBAR_PREDICTIVE_BACK_THREE_BUTTON) {
            ButtonDispatcher backButton = ((NavigationBarView) this.mView).getBackButton();
            ButtonDispatcher recentsButton = ((NavigationBarView) this.mView).getRecentsButton();
            boolean zIsAccessControlEnabled = this.mSettingsHelper.isAccessControlEnabled();
            if (this.mScreenPinningActive && (z = BasicRune.NAVBAR_PREDICTIVE_BACK_THREE_BUTTON) && !zIsAccessControlEnabled) {
                backButton.setOnLongClickListener((((NavigationBarView) this.mView).getRecentsButton().getVisibility() == 0 || z) ? new NavigationBar$$ExternalSyntheticLambda6(this, 0) : new NavigationBar$$ExternalSyntheticLambda6(this, 3));
                recentsButton.setOnLongClickListener(new NavigationBar$$ExternalSyntheticLambda6(this, 0));
            } else {
                backButton.setOnLongClickListener(null);
                recentsButton.setOnLongClickListener(BasicRune.NAVBAR_PREDICTIVE_BACK_THREE_BUTTON ? new NavigationBar$$ExternalSyntheticLambda6(this, 4) : null);
            }
            backButton.setLongClickable(this.mScreenPinningActive);
            if (BasicRune.NAVBAR_PREDICTIVE_BACK_THREE_BUTTON) {
                return;
            }
            recentsButton.setLongClickable(this.mScreenPinningActive);
        }
    }

    public final void updateSystemUiStateFlags() {
        long j = this.mNavBarHelper.mA11yButtonState;
        boolean z = (j & 16) != 0;
        boolean z2 = (j & 32) != 0;
        SysUiState sysUiState = this.mSysUiFlagsContainer;
        sysUiState.setFlag(16L, z).setFlag(32L, z2).setFlag(2L, !(this.mNavigationBarWindowState == 0)).setFlag(262144L, (this.mNavbarFlags & 2) != 0).setFlag(1048576L, (this.mNavbarFlags & 4) != 0).setFlag(68719476736L, (this.mNavbarFlags & 1) != 0).setFlag(131072L, this.mBehavior != 2).commitUpdate();
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
        AutoHideControllerImpl autoHideControllerImpl = this.mAutoHideController;
        if (autoHideControllerImpl == null) {
            return true;
        }
        autoHideControllerImpl.touchAutoHide();
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
