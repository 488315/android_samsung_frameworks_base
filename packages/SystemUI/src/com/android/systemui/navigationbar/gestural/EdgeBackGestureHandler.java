package com.android.systemui.navigationbar.gestural;

import android.R;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.icu.text.SimpleDateFormat;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.util.TypedValue;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.BackNavigationInfo;
import android.window.BackTouchTracker;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.internal.protolog.ProtoLogImpl_1636998151;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.BackPanelUiThread;
import com.android.systemui.util.concurrency.UiThreadContext;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda2;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeBackGestureHandler implements PluginListener {
    public static final int MAX_LONG_PRESS_TIMEOUT;
    public boolean mAllowGesture;
    public BackAnimationController.BackAnimationImpl mBackAnimation;
    public final AnonymousClass5 mBackCallback;
    public BackGestureTfClassifierProvider mBackGestureTfClassifierProvider;
    public final Provider mBackGestureTfClassifierProviderProvider;
    public final BackPanelController.Factory mBackPanelControllerFactory;
    public float mBackSwipeLinearThreshold;
    public final Executor mBackgroundExecutor;
    public float mBottomGestureHeight;
    public Consumer mButtonForcedVisibleCallback;
    public final Context mContext;
    public boolean mDeferSetIsOnLeftEdge;
    public final EdgeBackGestureHandler$$ExternalSyntheticLambda0 mDesktopCornersChangedListener;
    public final Region mDesktopModeExcludeRegion;
    public final Optional mDesktopModeOptional;
    public boolean mDisabledByPolicy;
    public boolean mDisabledForQuickstep;
    public final int mDisplayId;
    public final Point mDisplaySize;
    public final PointF mDownPoint;
    public NavigationEdgeBackPlugin mEdgeBackPlugin;
    public final EdgeBackSplitGestureHandler mEdgeBackSplitGestureHandler;
    public int mEdgeWidthLeft;
    public int mEdgeWidthRight;
    public final PointF mEndPoint;
    public final Region mExcludeRegion;
    public final FalsingManager mFalsingManager;
    public final List mGestureBlockingActivities;
    public final AtomicBoolean mGestureBlockingActivityRunning;
    public final LogArray mGestureLogInsideInsets;
    public final LogArray mGestureLogOutsideInsets;
    public final GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
    public boolean mInGestureNavMode;
    public boolean mInRejectedExclusion;
    public final AnonymousClass8 mInputDeviceListener;
    public InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public final InputManager mInputManager;
    public InputMonitorCompat mInputMonitor;
    public boolean mIsAttached;
    public boolean mIsBackGestureAllowed;
    public boolean mIsBlockGestureOnGame;
    public boolean mIsButtonForcedVisible;
    public boolean mIsEnabled;
    public boolean mIsGestureHandlingEnabled;
    public boolean mIsInPip;
    public boolean mIsLargeCoverBackGestureEnabled;
    public boolean mIsNavBarShownTransiently;
    public boolean mIsOnLeftEdge;
    public boolean mIsTrackpadThreeFingerSwipe;
    public final Configuration mLastReportedConfig;
    public int mLeftInset;
    public final Provider mLightBarControllerProvider;
    public final SimpleDateFormat mLogDateFormat;
    public boolean mLogGesture;
    public final int mLongPressTimeout;
    public int mMLEnableWidth;
    public boolean mMLModelIsLoading;
    public float mMLModelThreshold;
    public float mMLResults;
    public final MetricsLogger mMetricsLogger;
    public MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public final Rect mNavBarOverlayExcludedBounds;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public final NavigationModeController mNavigationModeController;
    public float mNonLinearFactor;
    public final EdgeBackGestureHandler$$ExternalSyntheticLambda0 mOnIsInPipStateChangedListener;
    public final OverviewProxyService mOverviewProxyService;
    public String mPackageName;
    public final Rect mPipExcludedBounds;
    public final Optional mPipOptional;
    public final PluginManager mPluginManager;
    public final LogArray mPredictionLog;
    public int mRightInset;
    private SettingsHelper mSettingsHelper;
    public int mStartingQuickstepRotation;
    public Runnable mStateChangeCallback;
    public long mSysUiFlags;
    public final SysUiState mSysUiState;
    public final AnonymousClass6 mSysUiStateCallback;
    public boolean mThresholdCrossed;
    public final Date mTmpLogDate;
    public float mTouchSlop;
    public final Set mTrackpadsConnected;
    public final UiThreadContext mUiThreadContext;
    public final Region mUnrestrictedExcludeRegion;
    public boolean mUseMLModel;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public boolean mUsingThreeButtonNav;
    public final ViewConfiguration mViewConfiguration;
    public Map mVocab;
    public final WindowManager mWindowManager;
    public final IWindowManager mWindowManagerService;
    public final AnonymousClass1 mGestureExclusionListener = new AnonymousClass1();
    public final AnonymousClass2 mQuickSwitchListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.2
        @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
        public final void onPrioritizedRotation(int i) {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            edgeBackGestureHandler.mStartingQuickstepRotation = i;
            int rotation = edgeBackGestureHandler.mLastReportedConfig.windowConfiguration.getRotation();
            int i2 = edgeBackGestureHandler.mStartingQuickstepRotation;
            edgeBackGestureHandler.mDisabledForQuickstep = i2 > -1 && i2 != rotation;
        }
    };
    public final AnonymousClass3 mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.3
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskCreated(ComponentName componentName) {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            if (componentName != null) {
                edgeBackGestureHandler.mPackageName = componentName.getPackageName();
            } else {
                edgeBackGestureHandler.mPackageName = "_UNKNOWN";
            }
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskStackChanged() {
            Flags.FEATURE_FLAGS.getClass();
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            AtomicBoolean atomicBoolean = edgeBackGestureHandler.mGestureBlockingActivityRunning;
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            ComponentName componentName = runningTask == null ? null : runningTask.topActivity;
            if (componentName != null) {
                edgeBackGestureHandler.mPackageName = componentName.getPackageName();
            } else {
                edgeBackGestureHandler.mPackageName = "_UNKNOWN";
            }
            atomicBoolean.set(componentName != null && ((ArrayList) edgeBackGestureHandler.mGestureBlockingActivities).contains(componentName));
        }
    };
    public final AnonymousClass4 mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.4
        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if ("systemui".equals(properties.getNamespace())) {
                if (properties.getKeyset().contains("back_gesture_ml_model_threshold") || properties.getKeyset().contains("use_back_gesture_ml_model") || properties.getKeyset().contains("back_gesture_ml_model_name")) {
                    EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                    int i = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                    edgeBackGestureHandler.updateMLModelState();
                }
            }
        }
    };
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        public AnonymousClass1() {
        }

        public final void onSystemGestureExclusionChanged(int i, final Region region, final Region region2) {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            if (i == edgeBackGestureHandler.mDisplayId) {
                edgeBackGestureHandler.mUiThreadContext.getExecutor().execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgeBackGestureHandler.AnonymousClass1 anonymousClass1 = EdgeBackGestureHandler.AnonymousClass1.this;
                        Region region3 = region;
                        Region region4 = region2;
                        EdgeBackGestureHandler.this.mExcludeRegion.set(region3);
                        Region region5 = EdgeBackGestureHandler.this.mUnrestrictedExcludeRegion;
                        if (region4 != null) {
                            region3 = region4;
                        }
                        region5.set(region3);
                        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
                            EdgeBackGestureHandler edgeBackGestureHandler2 = EdgeBackGestureHandler.this;
                            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = edgeBackGestureHandler2.mEdgeBackSplitGestureHandler;
                            edgeBackSplitGestureHandler.gestureDetector.setGestureExclusionRegion(edgeBackGestureHandler2.mExcludeRegion);
                        }
                    }
                });
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final Provider mBackGestureTfClassifierProviderProvider;
        public final BackPanelController.Factory mBackPanelControllerFactory;
        public final Executor mBackgroundExecutor;
        public final Handler mBgHandler;
        public final Optional mDesktopModeOptional;
        public final FalsingManager mFalsingManager;
        public final InputManager mInputManager;
        public final Provider mLightBarControllerProvider;
        public final NavigationModeController mNavigationModeController;
        public final OverviewProxyService mOverviewProxyService;
        public final Optional mPipOptional;
        public final PluginManager mPluginManager;
        public final SysUiState mSysUiState;
        public final UiThreadContext mUiThreadContext;
        public final UserTracker mUserTracker;
        public final ViewConfiguration mViewConfiguration;
        public final WindowManager mWindowManager;
        public final IWindowManager mWindowManagerService;

        public Factory(OverviewProxyService overviewProxyService, SysUiState sysUiState, PluginManager pluginManager, @BackPanelUiThread UiThreadContext uiThreadContext, Executor executor, Handler handler, UserTracker userTracker, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, InputManager inputManager, Optional<Pip> optional, Optional<DesktopMode> optional2, FalsingManager falsingManager, Provider provider, Provider provider2) {
            this.mOverviewProxyService = overviewProxyService;
            this.mSysUiState = sysUiState;
            this.mPluginManager = pluginManager;
            this.mUiThreadContext = uiThreadContext;
            this.mBackgroundExecutor = executor;
            this.mBgHandler = handler;
            this.mUserTracker = userTracker;
            this.mNavigationModeController = navigationModeController;
            this.mBackPanelControllerFactory = factory;
            this.mViewConfiguration = viewConfiguration;
            this.mWindowManager = windowManager;
            this.mWindowManagerService = iWindowManager;
            this.mInputManager = inputManager;
            this.mPipOptional = optional;
            this.mDesktopModeOptional = optional2;
            this.mFalsingManager = falsingManager;
            this.mBackGestureTfClassifierProviderProvider = provider;
            this.mLightBarControllerProvider = provider2;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class LogArray extends ArrayDeque<String> {
        private final int mLength;

        public LogArray(int i) {
            this.mLength = i;
        }

        public final void log(String str) {
            if (size() >= this.mLength) {
                removeFirst();
            }
            addLast(str);
        }
    }

    public static void $r8$lambda$BDwwaFS12IIc2CEG9o8I_AhrHGU(EdgeBackGestureHandler edgeBackGestureHandler, InputEvent inputEvent) {
        boolean z;
        edgeBackGestureHandler.getClass();
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            int actionMasked = motionEvent.getActionMasked();
            boolean z2 = BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE;
            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = edgeBackGestureHandler.mEdgeBackSplitGestureHandler;
            if (z2 && edgeBackSplitGestureHandler.enabled) {
                edgeBackSplitGestureHandler.gestureDetector.onInputEvent(motionEvent);
            }
            if (actionMasked == 0) {
                edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe = Utilities.isTrackpadThreeFingerSwipe(motionEvent);
                edgeBackGestureHandler.mVelocityTracker.clear();
                boolean z3 = BasicRune.NAVBAR_GESTURE;
                if (!z3) {
                    edgeBackGestureHandler.mInputEventReceiver.mReceiver.setBatchingEnabled(false);
                }
                if (edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe) {
                    edgeBackGestureHandler.mDeferSetIsOnLeftEdge = true;
                    edgeBackGestureHandler.mIsOnLeftEdge = false;
                } else if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                    edgeBackGestureHandler.mIsOnLeftEdge = ((int) motionEvent.getX()) <= edgeBackGestureHandler.mEdgeWidthLeft + edgeBackGestureHandler.mLeftInset;
                } else {
                    edgeBackGestureHandler.mIsOnLeftEdge = motionEvent.getX() <= ((float) (edgeBackGestureHandler.mEdgeWidthLeft + edgeBackGestureHandler.mLeftInset));
                }
                edgeBackGestureHandler.mMLResults = 0.0f;
                edgeBackGestureHandler.mLogGesture = false;
                edgeBackGestureHandler.mInRejectedExclusion = false;
                boolean isWithinInsets = edgeBackGestureHandler.isWithinInsets((int) motionEvent.getX(), (int) motionEvent.getY());
                boolean z4 = !edgeBackGestureHandler.mDisabledForQuickstep && edgeBackGestureHandler.mIsBackGestureAllowed && !edgeBackGestureHandler.mGestureBlockingActivityRunning.get() && ((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled) || !QuickStepContract.isBackGestureDisabled(edgeBackGestureHandler.mSysUiFlags, edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe)) && !Utilities.isTrackpadScroll(motionEvent) && z3 && motionEvent.getPalm() <= 0.0f && !edgeBackGestureHandler.isMultiWindowCornerGesture(motionEvent);
                if (z3) {
                    z4 &= (edgeBackGestureHandler.mDisabledByPolicy || edgeBackGestureHandler.isBlockSPenGesture(motionEvent)) ? false : true;
                }
                if (edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe) {
                    boolean z5 = (edgeBackGestureHandler.mSysUiFlags & SemWallpaperColorsWrapper.LOCKSCREEN_LOCK_ICON) == 0;
                    if (z4 && z5) {
                        Insets insets = edgeBackGestureHandler.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars());
                        Rect bounds = edgeBackGestureHandler.mExcludeRegion.getBounds();
                        int i = insets.left;
                        int i2 = insets.top;
                        Point point = edgeBackGestureHandler.mDisplaySize;
                        if (!bounds.contains(i, i2, point.x - insets.right, point.y - insets.bottom)) {
                            z = true;
                            edgeBackGestureHandler.mAllowGesture = z;
                        }
                    }
                    z = false;
                    edgeBackGestureHandler.mAllowGesture = z;
                } else {
                    edgeBackGestureHandler.mAllowGesture = z4 && ((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled) || !edgeBackGestureHandler.mUsingThreeButtonNav) && isWithinInsets && edgeBackGestureHandler.isWithinTouchRegion((int) motionEvent.getX(), (int) motionEvent.getY()) && ((InputManager.getInstance().getInputDevice(motionEvent.getDeviceId()).getSources() & 1056778) != 1056778 || motionEvent.getButtonState() == 0);
                }
                if (BasicRune.NAVBAR_REMOTEVIEW) {
                    edgeBackGestureHandler.mIsBlockGestureOnGame = false;
                    if (edgeBackGestureHandler.mAllowGesture) {
                        edgeBackGestureHandler.mIsBlockGestureOnGame = edgeBackGestureHandler.isBlockingGestureOnGame();
                        edgeBackGestureHandler.mDownPoint.set(motionEvent.getX(), motionEvent.getY());
                        edgeBackGestureHandler.mAllowGesture &= true ^ edgeBackGestureHandler.mIsBlockGestureOnGame;
                    }
                }
                if (edgeBackGestureHandler.mAllowGesture) {
                    edgeBackGestureHandler.mEdgeBackPlugin.setIsLeftPanel(edgeBackGestureHandler.mIsOnLeftEdge);
                    edgeBackGestureHandler.mEdgeBackPlugin.onMotionEvent(motionEvent);
                    edgeBackGestureHandler.dispatchToBackAnimation(motionEvent);
                }
                if (edgeBackGestureHandler.mLogGesture || edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe) {
                    edgeBackGestureHandler.mDownPoint.set(motionEvent.getX(), motionEvent.getY());
                    edgeBackGestureHandler.mEndPoint.set(-1.0f, -1.0f);
                    edgeBackGestureHandler.mThresholdCrossed = false;
                }
                long currentTimeMillis = System.currentTimeMillis();
                edgeBackGestureHandler.mTmpLogDate.setTime(currentTimeMillis);
                Object format = edgeBackGestureHandler.mLogDateFormat.format(edgeBackGestureHandler.mTmpLogDate);
                LogArray logArray = isWithinInsets ? edgeBackGestureHandler.mGestureLogInsideInsets : edgeBackGestureHandler.mGestureLogOutsideInsets;
                Long valueOf = Long.valueOf(currentTimeMillis);
                if (z3) {
                    format = new Date(currentTimeMillis);
                }
                logArray.log(String.format("Gesture [%d [%s],alw=%B, t3fs=%B, left=%B, defLeft=%B, backAlw=%B, disbld=%B, qsDisbld=%b, blkdAct=%B, pip=%B, disp=%s, wl=%d, il=%d, wr=%d, ir=%d, excl=%s]", valueOf, format, Boolean.valueOf(edgeBackGestureHandler.mAllowGesture), Boolean.valueOf(edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe), Boolean.valueOf(edgeBackGestureHandler.mIsOnLeftEdge), Boolean.valueOf(edgeBackGestureHandler.mDeferSetIsOnLeftEdge), Boolean.valueOf(edgeBackGestureHandler.mIsBackGestureAllowed), Boolean.valueOf(QuickStepContract.isBackGestureDisabled(edgeBackGestureHandler.mSysUiFlags, edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe)), Boolean.valueOf(edgeBackGestureHandler.mDisabledForQuickstep), Boolean.valueOf(edgeBackGestureHandler.mGestureBlockingActivityRunning.get()), Boolean.valueOf(edgeBackGestureHandler.mIsInPip), edgeBackGestureHandler.mDisplaySize, Integer.valueOf(edgeBackGestureHandler.mEdgeWidthLeft), Integer.valueOf(edgeBackGestureHandler.mLeftInset), Integer.valueOf(edgeBackGestureHandler.mEdgeWidthRight), Integer.valueOf(edgeBackGestureHandler.mRightInset), edgeBackGestureHandler.mExcludeRegion));
                return;
            }
            if (!edgeBackGestureHandler.mAllowGesture && !edgeBackGestureHandler.mLogGesture) {
                if (BasicRune.NAVBAR_GESTURE && edgeBackGestureHandler.mIsBlockGestureOnGame) {
                    float abs = Math.abs(motionEvent.getX() - edgeBackGestureHandler.mDownPoint.x);
                    if (abs <= Math.abs(motionEvent.getY() - edgeBackGestureHandler.mDownPoint.y) || abs <= edgeBackGestureHandler.mTouchSlop) {
                        return;
                    }
                    edgeBackGestureHandler.mSettingsHelper.setGameToolsEnabled(true);
                    edgeBackGestureHandler.mIsBlockGestureOnGame = false;
                    return;
                }
                return;
            }
            if (z2 && actionMasked == 5 && edgeBackSplitGestureHandler.gestureDetected) {
                Log.d("EdgeBackGestureHandler", "onMotionEvent(" + edgeBackGestureHandler.mDisplaySize + ") cancel reason [splitGesture]");
                edgeBackGestureHandler.cancelGesture(motionEvent);
                return;
            }
            boolean z6 = BasicRune.NAVBAR_GESTURE;
            if (z6 && motionEvent.getPalm() > 0.0f) {
                Log.d("EdgeBackGestureHandler", "onMotionEvent(" + edgeBackGestureHandler.mDisplaySize + ") cancel reason [palmTouch]");
                edgeBackGestureHandler.cancelGesture(motionEvent);
                return;
            }
            if (!edgeBackGestureHandler.mThresholdCrossed) {
                edgeBackGestureHandler.mEndPoint.x = (int) motionEvent.getX();
                edgeBackGestureHandler.mEndPoint.y = (int) motionEvent.getY();
                if (actionMasked == 5 && !edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe) {
                    if (edgeBackGestureHandler.mAllowGesture) {
                        edgeBackGestureHandler.logGesture(6);
                        if (z6) {
                            Log.d("EdgeBackGestureHandler", "onMotionEvent(" + edgeBackGestureHandler.mDisplaySize + ") cancel reason [multitouch]");
                        }
                        edgeBackGestureHandler.cancelGesture(motionEvent);
                    }
                    edgeBackGestureHandler.mLogGesture = false;
                    return;
                }
                if (actionMasked == 2) {
                    if (edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe && edgeBackGestureHandler.mDeferSetIsOnLeftEdge) {
                        boolean z7 = edgeBackGestureHandler.mEndPoint.x > edgeBackGestureHandler.mDownPoint.x;
                        edgeBackGestureHandler.mIsOnLeftEdge = z7;
                        edgeBackGestureHandler.mEdgeBackPlugin.setIsLeftPanel(z7);
                        edgeBackGestureHandler.mDeferSetIsOnLeftEdge = false;
                    }
                    if (motionEvent.getEventTime() - motionEvent.getDownTime() > edgeBackGestureHandler.mLongPressTimeout) {
                        if (edgeBackGestureHandler.mAllowGesture) {
                            edgeBackGestureHandler.logGesture(7);
                            edgeBackGestureHandler.cancelGesture(motionEvent);
                            if (z6) {
                                Log.d("EdgeBackGestureHandler", "onMotionEvent(" + edgeBackGestureHandler.mDisplaySize + ") cancel reason [longpress]");
                            }
                        }
                        edgeBackGestureHandler.mLogGesture = false;
                        return;
                    }
                    float abs2 = Math.abs(motionEvent.getX() - edgeBackGestureHandler.mDownPoint.x);
                    float abs3 = Math.abs(motionEvent.getY() - edgeBackGestureHandler.mDownPoint.y);
                    if (abs3 > abs2 && abs3 > edgeBackGestureHandler.mTouchSlop) {
                        if (edgeBackGestureHandler.mAllowGesture) {
                            edgeBackGestureHandler.logGesture(8);
                            edgeBackGestureHandler.cancelGesture(motionEvent);
                            if (z6) {
                                Log.d("EdgeBackGestureHandler", "onMotionEvent(" + edgeBackGestureHandler.mDisplaySize + ") cancel reason [vertical move]");
                            }
                        }
                        edgeBackGestureHandler.mLogGesture = false;
                        return;
                    }
                    if (abs2 > abs3 && abs2 > edgeBackGestureHandler.mTouchSlop) {
                        if (edgeBackGestureHandler.mAllowGesture) {
                            BackAnimationController.BackAnimationImpl backAnimationImpl = edgeBackGestureHandler.mBackAnimation;
                            if (backAnimationImpl != null) {
                                BackAnimationController.this.onThresholdCrossed();
                            } else {
                                edgeBackGestureHandler.mInputMonitor.mInputMonitor.pilferPointers();
                                edgeBackGestureHandler.mFalsingManager.isFalseTouch(16);
                                edgeBackGestureHandler.mInputEventReceiver.mReceiver.setBatchingEnabled(true);
                            }
                            edgeBackGestureHandler.mThresholdCrossed = true;
                        } else {
                            edgeBackGestureHandler.logGesture(5);
                        }
                    }
                }
            }
            if (edgeBackGestureHandler.mAllowGesture) {
                edgeBackGestureHandler.mEdgeBackPlugin.onMotionEvent(motionEvent);
                edgeBackGestureHandler.dispatchToBackAnimation(motionEvent);
            }
        }
    }

    /* renamed from: -$$Nest$msendEvent, reason: not valid java name */
    public static void m1987$$Nest$msendEvent(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        edgeBackGestureHandler.getClass();
        if (BasicRune.NAVBAR_GESTURE) {
            edgeBackGestureHandler.mMetricsLogger.write(new LogMaker(1931).setType(4).setSubtype(4).addTaggedData(933, Integer.valueOf(i)).addTaggedData(932, 0));
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257);
        keyEvent.setDisplayId(edgeBackGestureHandler.mContext.getDisplay().getDisplayId());
        ((InputManager) edgeBackGestureHandler.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0);
    }

    static {
        MAX_LONG_PRESS_TIMEOUT = BasicRune.NAVBAR_GESTURE ? 200 : SystemProperties.getInt("gestures.back_timeout", IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$2] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$3] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$4] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$5] */
    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$6] */
    public EdgeBackGestureHandler(Context context, OverviewProxyService overviewProxyService, SysUiState sysUiState, PluginManager pluginManager, @BackPanelUiThread UiThreadContext uiThreadContext, Executor executor, Handler handler, UserTracker userTracker, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, InputManager inputManager, Optional<Pip> optional, Optional<DesktopMode> optional2, FalsingManager falsingManager, Provider provider, Provider provider2) {
        Configuration configuration = new Configuration();
        this.mLastReportedConfig = configuration;
        this.mGestureBlockingActivities = new ArrayList();
        this.mDisplaySize = new Point();
        this.mPipExcludedBounds = new Rect();
        this.mNavBarOverlayExcludedBounds = new Rect();
        this.mExcludeRegion = new Region();
        this.mDesktopModeExcludeRegion = new Region();
        this.mUnrestrictedExcludeRegion = new Region();
        this.mStartingQuickstepRotation = -1;
        this.mDownPoint = new PointF();
        this.mEndPoint = new PointF();
        this.mGestureBlockingActivityRunning = new AtomicBoolean();
        this.mThresholdCrossed = false;
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        this.mTrackpadsConnected = new ArraySet();
        this.mPredictionLog = new LogArray(10);
        this.mGestureLogInsideInsets = new LogArray(10);
        this.mGestureLogOutsideInsets = new LogArray(10);
        this.mLogDateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);
        this.mTmpLogDate = new Date();
        this.mBackCallback = new NavigationEdgeBackPlugin.BackCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.5
            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public final void cancelBack() {
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                BackAnimationController.BackAnimationImpl backAnimationImpl = edgeBackGestureHandler.mBackAnimation;
                if (backAnimationImpl != null) {
                    ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda2(backAnimationImpl, false));
                }
                if (BasicRune.NAVBAR_GESTURE) {
                    Log.d("EdgeBackGestureHandler", "cancelBack");
                }
                edgeBackGestureHandler.logGesture(4);
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public final void setTriggerBack(boolean z) {
                BackAnimationController.BackAnimationImpl backAnimationImpl = EdgeBackGestureHandler.this.mBackAnimation;
                if (backAnimationImpl != null) {
                    ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda2(backAnimationImpl, z));
                }
                if (BasicRune.NAVBAR_GESTURE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setTriggerBack: triggerBack : ", "EdgeBackGestureHandler", z);
                }
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public final void triggerBack() {
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                edgeBackGestureHandler.mFalsingManager.isFalseTouch(16);
                BackAnimationController.BackAnimationImpl backAnimationImpl = edgeBackGestureHandler.mBackAnimation;
                boolean z = true;
                if (backAnimationImpl == null) {
                    EdgeBackGestureHandler.m1987$$Nest$msendEvent(edgeBackGestureHandler, 0);
                    EdgeBackGestureHandler.m1987$$Nest$msendEvent(edgeBackGestureHandler, 1);
                } else {
                    ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda2(backAnimationImpl, z ? 1 : 0));
                }
                if (BasicRune.NAVBAR_GESTURE) {
                    StringBuilder sb = new StringBuilder("triggerBack: mBackAnimation: ");
                    sb.append(edgeBackGestureHandler.mBackAnimation);
                    sb.append(", mDisplayId:");
                    RecyclerView$$ExternalSyntheticOutline0.m(edgeBackGestureHandler.mDisplayId, "EdgeBackGestureHandler", sb);
                }
                edgeBackGestureHandler.logGesture(edgeBackGestureHandler.mInRejectedExclusion ? 2 : 1);
            }
        };
        this.mSysUiStateCallback = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.6
            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public final void onSystemUiStateChanged(long j) {
                EdgeBackGestureHandler.this.mSysUiFlags = j;
            }
        };
        this.mOnIsInPipStateChangedListener = new EdgeBackGestureHandler$$ExternalSyntheticLambda0(this, 0);
        this.mDesktopCornersChangedListener = new EdgeBackGestureHandler$$ExternalSyntheticLambda0(this, 3);
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.7
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                int i2 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                edgeBackGestureHandler.updateIsEnabled();
                edgeBackGestureHandler.updateCurrentUserResources();
            }
        };
        this.mIsBlockGestureOnGame = false;
        this.mIsLargeCoverBackGestureEnabled = false;
        this.mInputDeviceListener = new AnonymousClass8();
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mUiThreadContext = uiThreadContext;
        this.mBackgroundExecutor = executor;
        this.mUserTracker = userTracker;
        this.mOverviewProxyService = overviewProxyService;
        this.mSysUiState = sysUiState;
        this.mPluginManager = pluginManager;
        this.mNavigationModeController = navigationModeController;
        this.mBackPanelControllerFactory = factory;
        this.mViewConfiguration = viewConfiguration;
        this.mWindowManager = windowManager;
        this.mWindowManagerService = iWindowManager;
        this.mInputManager = inputManager;
        this.mPipOptional = optional;
        this.mDesktopModeOptional = optional2;
        this.mFalsingManager = falsingManager;
        this.mBackGestureTfClassifierProviderProvider = provider;
        this.mLightBarControllerProvider = provider2;
        configuration.setTo(context.getResources().getConfiguration());
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.ext_media_status_mounted));
        if (unflattenFromString != null) {
            String packageName = unflattenFromString.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            try {
                Resources resourcesForApplication = packageManager.getResourcesForApplication(packageManager.getApplicationInfo(packageName, 9728));
                int identifier = resourcesForApplication.getIdentifier("back_gesture_blocking_activities", "array", packageName);
                if (identifier == 0) {
                    Log.e("EdgeBackGestureHandler", "No resource found for gesture-blocking activities");
                } else {
                    for (String str : resourcesForApplication.getStringArray(identifier)) {
                        ((ArrayList) this.mGestureBlockingActivities).add(ComponentName.unflattenFromString(str));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("EdgeBackGestureHandler", "Failed to add gesture blocking activities", e);
            }
        }
        if (BasicRune.NAVBAR_GESTURE) {
            NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(this.mContext.getDisplayId());
            this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            this.mMetricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
        }
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            this.mEdgeBackSplitGestureHandler = new EdgeBackSplitGestureHandler(this.mContext, this.mDisplayId, this.mSettingsHelper);
        }
        this.mLongPressTimeout = Math.min(MAX_LONG_PRESS_TIMEOUT, ViewConfiguration.getLongPressTimeout());
        this.mGestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(this.mUiThreadContext.getHandler(), handler, this.mContext, new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 0));
        updateCurrentUserResources();
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        this.mEdgeBackPlugin.onMotionEvent(obtain);
        dispatchToBackAnimation(obtain);
        obtain.recycle();
    }

    public final WindowManager.LayoutParams createLayoutParams() {
        Resources resources = this.mContext.getResources();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(resources.getDimensionPixelSize(com.android.systemui.R.dimen.navigation_edge_panel_width), resources.getDimensionPixelSize(com.android.systemui.R.dimen.navigation_edge_panel_height), 2024, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, -3);
        layoutParams.accessibilityTitle = this.mContext.getString(com.android.systemui.R.string.nav_bar_edge_panel);
        layoutParams.windowAnimations = 0;
        layoutParams.privateFlags |= 2097168;
        layoutParams.setTitle("EdgeBackGestureHandler" + this.mContext.getDisplayId());
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }

    public final void dispatchToBackAnimation(MotionEvent motionEvent) {
        final float f;
        final float f2;
        if (this.mBackAnimation != null) {
            this.mVelocityTracker.addMovement(motionEvent);
            if (motionEvent.getAction() == 1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mViewConfiguration.getScaledMaximumFlingVelocity());
                f = this.mVelocityTracker.getXVelocity();
                f2 = this.mVelocityTracker.getYVelocity();
            } else {
                f = Float.NaN;
                f2 = Float.NaN;
            }
            final BackAnimationController.BackAnimationImpl backAnimationImpl = this.mBackAnimation;
            final float x = motionEvent.getX();
            final float y = motionEvent.getY();
            final int actionMasked = motionEvent.getActionMasked();
            final int i = !this.mIsOnLeftEdge ? 1 : 0;
            ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BackTouchTracker backTouchTracker;
                    BackAnimationController.BackAnimationImpl backAnimationImpl2 = BackAnimationController.BackAnimationImpl.this;
                    float f3 = x;
                    float f4 = y;
                    float f5 = f;
                    float f6 = f2;
                    int i2 = actionMasked;
                    int i3 = i;
                    BackAnimationController backAnimationController = BackAnimationController.this;
                    BackTouchTracker activeTracker = backAnimationController.getActiveTracker();
                    if (activeTracker != null) {
                        activeTracker.update(f3, f4, f5, f6);
                    }
                    if (backAnimationController.mCurrentTracker.isFinished() && backAnimationController.mQueuedTracker.isFinished()) {
                        if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2731934872007265096L, 0, "Ignoring MotionEvent because two gestures are already being queued.", null);
                            return;
                        }
                        return;
                    }
                    if (i2 == 0) {
                        if (backAnimationController.mBackGestureStarted) {
                            return;
                        }
                        backAnimationController.mShouldStartOnNextMoveEvent = true;
                        return;
                    }
                    BackAnimationController$$ExternalSyntheticLambda2 backAnimationController$$ExternalSyntheticLambda2 = backAnimationController.mAnimationTimeoutRunnable;
                    ShellExecutor shellExecutor = backAnimationController.mShellExecutor;
                    if (i2 == 2) {
                        if (!backAnimationController.mBackGestureStarted && backAnimationController.mShouldStartOnNextMoveEvent) {
                            boolean z = backAnimationController.mPostCommitAnimationInProgress && backAnimationController.mCurrentTracker.isFinished() && !backAnimationController.mCurrentTracker.getTriggerBack() && backAnimationController.mQueuedTracker.isInInitialState();
                            if (z) {
                                backAnimationController.resetTouchTracker();
                            }
                            if (backAnimationController.mCurrentTracker.isInInitialState()) {
                                backTouchTracker = backAnimationController.mCurrentTracker;
                            } else if (backAnimationController.mQueuedTracker.isInInitialState()) {
                                backTouchTracker = backAnimationController.mQueuedTracker;
                            } else {
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                    ProtoLogImpl_1636998151.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -4950819966474305888L, 0, "Cannot start tracking new gesture with neither tracker in initial state.", null);
                                }
                                backAnimationController.mShouldStartOnNextMoveEvent = false;
                            }
                            backTouchTracker.setGestureStartLocation(f3, f4, i3);
                            backTouchTracker.setState(BackTouchTracker.TouchTrackerState.ACTIVE);
                            backAnimationController.mBackGestureStarted = true;
                            if (z) {
                                backAnimationController.mPostCommitAnimationInProgress = false;
                                ((HandlerExecutor) shellExecutor).removeCallbacks(backAnimationController$$ExternalSyntheticLambda2);
                                backAnimationController.startSystemAnimation();
                            } else {
                                BackTouchTracker backTouchTracker2 = backAnimationController.mCurrentTracker;
                                if (backTouchTracker == backTouchTracker2) {
                                    try {
                                        if (backAnimationController.mTrackingLatency) {
                                            backAnimationController.cancelLatencyTracking();
                                        }
                                        backAnimationController.mLatencyTracker.onActionStart(25);
                                        backAnimationController.mTrackingLatency = true;
                                        BackNavigationInfo startBackNavigation = backAnimationController.mActivityTaskManager.startBackNavigation(backAnimationController.mNavigationObserver, backAnimationController.mEnableAnimations.get() ? backAnimationController.mBackAnimationAdapter : null);
                                        backAnimationController.mBackNavigationInfo = startBackNavigation;
                                        backAnimationController.onBackNavigationInfoReceived(startBackNavigation, backTouchTracker2);
                                    } catch (RemoteException e) {
                                        Log.e("ShellBackPreview", "Failed to initAnimation", e);
                                        backAnimationController.finishBackNavigation(backTouchTracker2.getTriggerBack());
                                    }
                                }
                            }
                            backAnimationController.mShouldStartOnNextMoveEvent = false;
                        }
                        if (!backAnimationController.mBackGestureStarted || backAnimationController.mBackNavigationInfo == null || backAnimationController.mActiveCallback == null || !backAnimationController.mOnBackStartDispatched || backAnimationController.mQueuedTracker.isActive()) {
                            return;
                        }
                        backAnimationController.dispatchOnBackProgressed(backAnimationController.mActiveCallback, backAnimationController.mCurrentTracker.createProgressEvent());
                        return;
                    }
                    if (i2 == 1 || i2 == 3) {
                        if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 8967565580839694775L, 1, "Finishing gesture with event action: %d", Long.valueOf(i2));
                        }
                        if (i2 == 3) {
                            backAnimationController.setTriggerBack(false);
                        }
                        BackTouchTracker activeTracker2 = backAnimationController.getActiveTracker();
                        if (!backAnimationController.mBackGestureStarted || activeTracker2 == null) {
                            if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                                ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -250489720305009514L, 0, "onGestureFinished called while no gesture is started", null);
                                return;
                            }
                            return;
                        }
                        boolean triggerBack = activeTracker2.getTriggerBack();
                        if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1940238233150763812L, 0, "onGestureFinished() mTriggerBack == %s", String.valueOf(triggerBack));
                        }
                        backAnimationController.mThresholdCrossed = false;
                        backAnimationController.mPointersPilfered = false;
                        backAnimationController.mBackGestureStarted = false;
                        activeTracker2.setState(BackTouchTracker.TouchTrackerState.FINISHED);
                        if (backAnimationController.mPostCommitAnimationInProgress) {
                            if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                ProtoLogImpl_1636998151.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 6833772699392816046L, 0, "Animation is still running", null);
                                return;
                            }
                            return;
                        }
                        BackNavigationInfo backNavigationInfo = backAnimationController.mBackNavigationInfo;
                        if (backNavigationInfo == null) {
                            if (!backAnimationController.mQueuedTracker.isInInitialState() && ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                                ProtoLogImpl_1636998151.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 5250769310321425521L, 0, "mBackNavigationInfo is null AND there is another back animation in progress", null);
                            }
                            backAnimationController.mCurrentTracker.reset();
                            if (triggerBack) {
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                                    ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1127091700925973460L, 0, "injectBackKey", null);
                                }
                                backAnimationController.sendBackEvent(0);
                                backAnimationController.sendBackEvent(1);
                            }
                            backAnimationController.finishBackNavigation(triggerBack);
                            return;
                        }
                        int type = backNavigationInfo.getType();
                        if (backAnimationController.shouldDispatchToAnimator()) {
                            ShellBackAnimationRegistry shellBackAnimationRegistry = backAnimationController.mShellBackAnimationRegistry;
                            BackAnimationRunner backAnimationRunner = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type);
                            if (!(backAnimationRunner != null ? backAnimationRunner.mAnimationCancelled : true)) {
                                BackAnimationRunner backAnimationRunner2 = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type);
                                if (!(backAnimationRunner2 != null ? backAnimationRunner2.mWaitingAnimation : false)) {
                                    backAnimationController.startPostCommitAnimation();
                                    return;
                                }
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                    ProtoLogImpl_1636998151.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8669768627275025840L, 0, "Gesture released, but animation didn't ready.", null);
                                }
                                ((HandlerExecutor) shellExecutor).executeDelayed(backAnimationController$$ExternalSyntheticLambda2, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                                return;
                            }
                        }
                        if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 7659773614139456476L, 0, "Trigger back without dispatching to animator.", null);
                        }
                        backAnimationController.invokeOrCancelBack(backAnimationController.mCurrentTracker);
                        backAnimationController.mCurrentTracker.reset();
                    }
                }
            });
        }
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "EdgeBackGestureHandler:", "  mIsEnabled="), this.mIsEnabled, printWriter, "  mIsAttached="), this.mIsAttached, printWriter, "  mIsBackGestureAllowed="), this.mIsBackGestureAllowed, printWriter, "  mIsGestureHandlingEnabled="), this.mIsGestureHandlingEnabled, printWriter, "  mIsNavBarShownTransiently="), this.mIsNavBarShownTransiently, printWriter, "  mGestureBlockingActivityRunning=");
        m.append(this.mGestureBlockingActivityRunning.get());
        printWriter.println(m.toString());
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mAllowGesture="), this.mAllowGesture, printWriter, "  mUseMLModel="), this.mUseMLModel, printWriter, "  mDisabledForQuickstep="), this.mDisabledForQuickstep, printWriter, "  mStartingQuickstepRotation="), this.mStartingQuickstepRotation, printWriter, "  mInRejectedExclusion="), this.mInRejectedExclusion, printWriter, "  mExcludeRegion=");
        m2.append(this.mExcludeRegion);
        printWriter.println(m2.toString());
        printWriter.println("  mUnrestrictedExcludeRegion=" + this.mUnrestrictedExcludeRegion);
        StringBuilder m3 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsInPip="), this.mIsInPip, printWriter, "  mPipExcludedBounds=");
        m3.append(this.mPipExcludedBounds);
        printWriter.println(m3.toString());
        printWriter.println("  mDesktopModeExclusionRegion=" + this.mDesktopModeExcludeRegion);
        printWriter.println("  mNavBarOverlayExcludedBounds=" + this.mNavBarOverlayExcludedBounds);
        StringBuilder m4 = KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mEdgeWidthLeft="), this.mEdgeWidthLeft, printWriter, "  mEdgeWidthRight="), this.mEdgeWidthRight, printWriter, "  mLeftInset="), this.mLeftInset, printWriter, "  mRightInset="), this.mRightInset, printWriter, "  mMLEnableWidth="), this.mMLEnableWidth, printWriter, "  mMLModelThreshold="), this.mMLModelThreshold, printWriter, "  mTouchSlop="), this.mTouchSlop, printWriter, "  mBottomGestureHeight="), this.mBottomGestureHeight, printWriter, "  mPredictionLog=");
        m4.append(String.join("\n", this.mPredictionLog));
        printWriter.println(m4.toString());
        printWriter.println("  mGestureLogInsideInsets=" + String.join("\n", this.mGestureLogInsideInsets));
        printWriter.println("  mGestureLogOutsideInsets=" + String.join("\n", this.mGestureLogOutsideInsets));
        printWriter.println("  mTrackpadsConnected=" + ((String) this.mTrackpadsConnected.stream().map(new EdgeBackGestureHandler$$ExternalSyntheticLambda3()).collect(Collectors.joining())));
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mUsingThreeButtonNav="), this.mUsingThreeButtonNav, printWriter);
        if (BasicRune.NAVBAR_GESTURE) {
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mDisabledByPolicy="), this.mDisabledByPolicy, printWriter);
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsLargeCoverBackGestureEnabled="), this.mIsLargeCoverBackGestureEnabled, printWriter);
        }
        printWriter.println("  mEdgeBackPlugin=" + this.mEdgeBackPlugin);
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.dump(printWriter);
        }
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = this.mEdgeBackSplitGestureHandler;
            edgeBackSplitGestureHandler.getClass();
            printWriter.println("EdgeBackSplitGestureHandler :");
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  enabled=", edgeBackSplitGestureHandler.enabled, printWriter);
            printWriter.println("  tmpBounds=" + edgeBackSplitGestureHandler.tmpBounds);
            printWriter.println("  displayController=" + edgeBackSplitGestureHandler.displayController);
            printWriter.println("  splitScreenController=" + edgeBackSplitGestureHandler.splitScreenController);
        }
    }

    public final boolean isBackGestureAllowed(MotionEvent motionEvent) {
        boolean z = false;
        boolean z2 = (this.mDisabledForQuickstep || !this.mIsBackGestureAllowed || !isWithinInsets((int) motionEvent.getX(), (int) motionEvent.getY()) || this.mGestureBlockingActivityRunning.get() || QuickStepContract.isBackGestureDisabled(this.mSysUiFlags, this.mIsTrackpadThreeFingerSwipe) || !isWithinTouchRegion((int) motionEvent.getX(), (int) motionEvent.getY()) || isMultiWindowCornerGesture(motionEvent)) ? false : true;
        if (BasicRune.NAVBAR_GESTURE) {
            if (!this.mDisabledByPolicy && !isBlockSPenGesture(motionEvent)) {
                z = true;
            }
            z2 &= z;
        }
        return BasicRune.NAVBAR_REMOTEVIEW ? z2 & (!isBlockingGestureOnGame()) : z2;
    }

    public final boolean isBlockSPenGesture(MotionEvent motionEvent) {
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            if ((motionEvent.getToolType(i) & 6) != 0) {
                return this.mSettingsHelper.isBlockGesturesWithSpenEnabled() || ((motionEvent.getButtonState() & 32) != 0);
            }
        }
        return false;
    }

    public final boolean isBlockingGestureOnGame() {
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.mNavBarStateManager;
        boolean z = false;
        if (navBarStateManagerImpl.canShowFloatingGameTools(false) && (navBarStateManagerImpl.states.iconHint & 1) == 0) {
            z = true;
        }
        navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z), "isBlockingGestureOnGame");
        return z ? !QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN : z;
    }

    public final boolean isHandlingGestures() {
        return BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN ? this.mIsAttached && this.mIsGestureHandlingEnabled && this.mIsBackGestureAllowed : this.mIsEnabled && this.mIsBackGestureAllowed;
    }

    public final boolean isMultiWindowCornerGesture(MotionEvent motionEvent) {
        MultiWindowEdgeDetector multiWindowEdgeDetector;
        if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED) {
            return (!CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) && (multiWindowEdgeDetector = this.mMultiWindowEdgeDetector) != null && multiWindowEdgeDetector.onTouchEvent(motionEvent);
        }
        return false;
    }

    public final boolean isWithinInsets(int i, int i2) {
        float f = i2;
        Point point = this.mDisplaySize;
        if (f >= point.y - this.mBottomGestureHeight) {
            return false;
        }
        return i <= (this.mEdgeWidthLeft + this.mLeftInset) * 2 || i >= point.x - ((this.mEdgeWidthRight + this.mRightInset) * 2);
    }

    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v8 */
    public final boolean isWithinTouchRegion(int i, int i2) {
        boolean z = this.mIsInPip && this.mPipExcludedBounds.contains(i, i2);
        boolean contains = this.mDesktopModeExcludeRegion.contains(i, i2);
        if (z || contains || this.mNavBarOverlayExcludedBounds.contains(i, i2)) {
            return false;
        }
        Map map = this.mVocab;
        int intValue = map != null ? ((Integer) map.getOrDefault(this.mPackageName, -1)).intValue() : -1;
        int i3 = this.mEdgeWidthLeft;
        int i4 = this.mLeftInset;
        ?? r7 = (i < i3 + i4 || i >= (this.mDisplaySize.x - this.mEdgeWidthRight) - this.mRightInset) ? 1 : 0;
        if (r7 != 0) {
            int i5 = this.mMLEnableWidth;
            if (i >= i4 + i5 && i < (this.mDisplaySize.x - i5) - this.mRightInset && this.mUseMLModel && !this.mMLModelIsLoading && this.mBackGestureTfClassifierProvider != null && intValue != -1) {
                this.mMLResults = -1.0f;
            }
        }
        this.mPredictionLog.log(String.format("Prediction [%d,%d,%d,%d,%f,%d]", Long.valueOf(System.currentTimeMillis()), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(intValue), Float.valueOf(this.mMLResults), Integer.valueOf((int) r7)));
        if (!BasicRune.NAVBAR_GESTURE && this.mIsNavBarShownTransiently) {
            this.mLogGesture = true;
            return r7;
        }
        if (!this.mExcludeRegion.contains(i, i2)) {
            this.mInRejectedExclusion = this.mUnrestrictedExcludeRegion.contains(i, i2);
            this.mLogGesture = true;
            return r7;
        }
        if (r7 != 0) {
            PointF pointF = this.mEndPoint;
            pointF.x = -1.0f;
            pointF.y = -1.0f;
            this.mLogGesture = true;
            logGesture(3);
        }
        return false;
    }

    public final void logGesture(int i) {
        if (this.mLogGesture) {
            this.mLogGesture = false;
            Map map = this.mVocab;
            String str = (!this.mUseMLModel || map == null || !map.containsKey(this.mPackageName) || ((Integer) map.get(this.mPackageName)).intValue() >= 100) ? "" : this.mPackageName;
            PointF pointF = this.mDownPoint;
            float f = pointF.y;
            int i2 = (int) f;
            int i3 = this.mIsOnLeftEdge ? 1 : 2;
            int i4 = (int) pointF.x;
            int i5 = (int) f;
            PointF pointF2 = this.mEndPoint;
            int i6 = (int) pointF2.x;
            int i7 = (int) pointF2.y;
            int i8 = this.mEdgeWidthLeft + this.mLeftInset;
            int i9 = this.mDisplaySize.x - (this.mEdgeWidthRight + this.mRightInset);
            float f2 = this.mUseMLModel ? this.mMLResults : -2.0f;
            int i10 = this.mIsTrackpadThreeFingerSwipe ? 2 : 1;
            StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
            newBuilder.setAtomId(IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
            newBuilder.writeInt(i);
            newBuilder.writeInt(i2);
            newBuilder.writeInt(i3);
            newBuilder.writeInt(i4);
            newBuilder.writeInt(i5);
            newBuilder.writeInt(i6);
            newBuilder.writeInt(i7);
            newBuilder.writeInt(i8);
            newBuilder.writeInt(i9);
            newBuilder.writeFloat(f2);
            newBuilder.writeString(str);
            newBuilder.writeInt(i10);
            newBuilder.usePooledBuffer();
            StatsLog.write(newBuilder.build());
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mStartingQuickstepRotation > -1) {
            int rotation = configuration.windowConfiguration.getRotation();
            int i = this.mStartingQuickstepRotation;
            this.mDisabledForQuickstep = i > -1 && i != rotation;
        }
        Log.i("NoBackGesture", "Config changed: newConfig=" + configuration + " lastReportedConfig=" + this.mLastReportedConfig);
        int diff = configuration.diff(this.mLastReportedConfig);
        if ((1073741824 & diff) != 0 || (diff & 4096) != 0) {
            updateCurrentUserResources();
        }
        this.mLastReportedConfig.updateFrom(configuration);
        updateDisplaySize();
        if (BasicRune.NAVBAR_TASKBAR) {
            Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
            if (this.mDisplaySize.x != maxBounds.width() || this.mDisplaySize.y != maxBounds.height()) {
                Log.d("EdgeBackGestureHandler", "Force update display size as windowConfiguration: " + maxBounds);
                this.mDisplaySize.set(maxBounds.width(), maxBounds.height());
                if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && this.mDisplayId == 1) {
                    this.mContext.getDisplay().getRealSize(this.mDisplaySize);
                }
                NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
                if (navigationEdgeBackPlugin != null) {
                    navigationEdgeBackPlugin.setDisplaySize(this.mDisplaySize);
                }
            }
        }
        MultiWindowEdgeDetector multiWindowEdgeDetector = this.mMultiWindowEdgeDetector;
        if (multiWindowEdgeDetector != null) {
            multiWindowEdgeDetector.onConfigurationChanged();
        }
    }

    public final void onNavBarAttached() {
        if (BasicRune.NAVBAR_GESTURE) {
            this.mStartingQuickstepRotation = -1;
            this.mDisabledForQuickstep = false;
            this.mDisabledByPolicy = ((NavBarStateManagerImpl) this.mNavBarStateManager).states.gestureDisabledByPolicy;
        }
        this.mIsAttached = true;
        this.mOverviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.mQuickSwitchListener);
        this.mSysUiState.addCallback(this.mSysUiStateCallback);
        InputManager inputManager = this.mInputManager;
        UiThreadContext uiThreadContext = this.mUiThreadContext;
        Handler handler = uiThreadContext.getHandler();
        AnonymousClass8 anonymousClass8 = this.mInputDeviceListener;
        inputManager.registerInputDeviceListener(anonymousClass8, handler);
        for (int i : this.mInputManager.getInputDeviceIds()) {
            anonymousClass8.onInputDeviceAdded(i);
        }
        updateIsEnabled();
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, uiThreadContext.getExecutor());
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            this.mEdgeBackSplitGestureHandler.onNavBarAttached();
        }
    }

    public final void onNavBarDetached() {
        this.mIsAttached = false;
        ((ArrayList) this.mOverviewProxyService.mConnectionCallbacks).remove(this.mQuickSwitchListener);
        ((ArrayList) this.mSysUiState.mCallbacks).remove(this.mSysUiStateCallback);
        this.mInputManager.unregisterInputDeviceListener(this.mInputDeviceListener);
        ((ArraySet) this.mTrackpadsConnected).clear();
        updateIsEnabled();
        ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            this.mEdgeBackSplitGestureHandler.onNavBarDetached();
        }
    }

    public final void onNavigationModeChanged(int i) {
        Trace.beginSection("EdgeBackGestureHandler#onNavigationModeChanged");
        try {
            boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
            this.mUsingThreeButtonNav = i == 0;
            boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
            this.mInGestureNavMode = isGesturalMode;
            if (BasicRune.NAVBAR_GESTURE && isGesturalMode) {
                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                this.mInGestureNavMode = i == 2;
                this.mDisabledByPolicy = ((NavBarStateManagerImpl) this.mNavBarStateManager).states.gestureDisabledByPolicy;
            }
            updateIsEnabled();
            updateCurrentUserResources();
            Trace.endSection();
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        setEdgeBackPlugin((NavigationEdgeBackPlugin) plugin);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        resetEdgeBackPlugin();
    }

    public final void resetEdgeBackPlugin() {
        Context context = this.mContext;
        BackPanelController.Factory factory = this.mBackPanelControllerFactory;
        UiThreadContext uiThreadContext = factory.uiThreadContext;
        uiThreadContext.isCurrentThread();
        WindowManager windowManager = BasicRune.NAVBAR_ENABLED ? (WindowManager) context.getSystemService("window") : factory.windowManager;
        WindowManager windowManager2 = windowManager;
        BackPanelController backPanelController = new BackPanelController(context, windowManager2, factory.viewConfiguration, uiThreadContext.getHandler(), factory.systemClock, factory.vibratorHelper, factory.configurationController, factory.latencyTracker, factory.interactionJankMonitor);
        backPanelController.init();
        setEdgeBackPlugin(backPanelController);
    }

    public final void setBackAnimation(final BackAnimationController.BackAnimationImpl backAnimationImpl) {
        Log.d("EdgeBackGestureHandler", "setBackAnimation for displayId = " + this.mDisplayId + " : " + backAnimationImpl);
        this.mBackAnimation = backAnimationImpl;
        final EdgeBackGestureHandler$$ExternalSyntheticLambda2 edgeBackGestureHandler$$ExternalSyntheticLambda2 = new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 2);
        ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BackAnimationController.this.mPilferPointerCallback = edgeBackGestureHandler$$ExternalSyntheticLambda2;
            }
        });
        updateBackAnimationThresholds();
        if (this.mLightBarControllerProvider.get() != null) {
            BackAnimationController.BackAnimationImpl backAnimationImpl2 = this.mBackAnimation;
            EdgeBackGestureHandler$$ExternalSyntheticLambda6 edgeBackGestureHandler$$ExternalSyntheticLambda6 = new EdgeBackGestureHandler$$ExternalSyntheticLambda6(this);
            BackAnimationController backAnimationController = BackAnimationController.this;
            backAnimationController.mCustomizer = edgeBackGestureHandler$$ExternalSyntheticLambda6;
            backAnimationController.mAnimationBackground.mCustomizer = edgeBackGestureHandler$$ExternalSyntheticLambda6;
        }
    }

    public final void setEdgeBackPlugin(NavigationEdgeBackPlugin navigationEdgeBackPlugin) {
        try {
            Trace.beginSection("setEdgeBackPlugin");
            this.mEdgeBackPlugin = navigationEdgeBackPlugin;
            navigationEdgeBackPlugin.setBackCallback(this.mBackCallback);
            this.mEdgeBackPlugin.setLayoutParams(createLayoutParams());
            updateDisplaySize();
        } finally {
            Trace.endSection();
        }
    }

    public final void updateBackAnimationThresholds() {
        if (this.mBackAnimation == null) {
            return;
        }
        final float f = this.mDisplaySize.x;
        final float min = Math.min(f, this.mBackSwipeLinearThreshold);
        final BackAnimationController.BackAnimationImpl backAnimationImpl = this.mBackAnimation;
        final float f2 = this.mNonLinearFactor;
        ((HandlerExecutor) BackAnimationController.this.mShellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BackAnimationController.BackAnimationImpl backAnimationImpl2 = BackAnimationController.BackAnimationImpl.this;
                float f3 = min;
                float f4 = f;
                float f5 = f2;
                BackAnimationController backAnimationController = BackAnimationController.this;
                backAnimationController.mCurrentTracker.setProgressThresholds(f3, f4, f5);
                backAnimationController.mQueuedTracker.setProgressThresholds(f3, f4, f5);
            }
        });
    }

    public final void updateCurrentUserResources() {
        Consumer consumer;
        int i = this.mEdgeWidthLeft;
        int i2 = this.mEdgeWidthRight;
        boolean z = BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY;
        int i3 = this.mDisplayId;
        Resources resources = (z && i3 == 1) ? this.mContext.getResources() : this.mNavigationModeController.getCurrentUserContext().getResources();
        this.mEdgeWidthLeft = this.mGestureNavigationSettingsObserver.getLeftSensitivity(resources);
        this.mEdgeWidthRight = this.mGestureNavigationSettingsObserver.getRightSensitivity(resources);
        boolean z2 = this.mIsButtonForcedVisible;
        boolean areNavigationButtonForcedVisible = this.mGestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        this.mIsButtonForcedVisible = areNavigationButtonForcedVisible;
        this.mIsBackGestureAllowed = !areNavigationButtonForcedVisible;
        if (z2 != areNavigationButtonForcedVisible && (consumer = this.mButtonForcedVisibleCallback) != null) {
            consumer.accept(Boolean.valueOf(areNavigationButtonForcedVisible));
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float f = DeviceConfig.getFloat("systemui", "back_gesture_bottom_height", resources.getDimension(R.dimen.resolver_icon_margin) / displayMetrics.density);
        if (BasicRune.NAVBAR_BOTTOM_GESTURE_SENSITIVITY) {
            this.mBottomGestureHeight = this.mGestureNavigationSettingsObserver.getBottomSensitivity(resources);
        } else {
            this.mBottomGestureHeight = TypedValue.applyDimension(1, f, displayMetrics);
        }
        int applyDimension = (int) TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mMLEnableWidth = applyDimension;
        int i4 = this.mEdgeWidthRight;
        if (applyDimension > i4) {
            this.mMLEnableWidth = i4;
        }
        int i5 = this.mMLEnableWidth;
        int i6 = this.mEdgeWidthLeft;
        if (i5 > i6) {
            this.mMLEnableWidth = i6;
        }
        this.mTouchSlop = this.mViewConfiguration.getScaledTouchSlop() * DeviceConfig.getFloat("systemui", "back_gesture_slop_multiplier", 0.75f);
        this.mBackSwipeLinearThreshold = resources.getDimension(R.dimen.resolver_tab_text_size);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.car_action1_size, typedValue, true);
        this.mNonLinearFactor = typedValue.getFloat();
        updateBackAnimationThresholds();
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mIsLargeCoverBackGestureEnabled) {
            Context context = this.mContext;
            NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
            this.mEdgeWidthLeft = (int) (context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_back_gesture_insets) * Settings.Secure.getFloatForUser(context.getContentResolver(), "back_gesture_inset_scale_left", 1.0f, -2));
            this.mEdgeWidthRight = (int) (r2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.large_cover_back_gesture_insets) * Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "back_gesture_inset_scale_right", 1.0f, -2));
        }
        if (BasicRune.NAVBAR_TASKBAR && ((NavBarStateManagerImpl) this.mNavBarStateManager).isTaskBarEnabled(false)) {
            int i7 = this.mEdgeWidthLeft;
            if (i == i7 && i2 == this.mEdgeWidthRight) {
                return;
            }
            this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateSideBackGestureInsets(i7, this.mEdgeWidthRight), i3);
        }
    }

    public final void updateDisplaySize() {
        Rect maxBounds = this.mLastReportedConfig.windowConfiguration.getMaxBounds();
        this.mDisplaySize.set(maxBounds.width(), maxBounds.height());
        if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && this.mDisplayId == 1) {
            this.mContext.getDisplay().getRealSize(this.mDisplaySize);
        }
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.setDisplaySize(this.mDisplaySize);
        }
        updateBackAnimationThresholds();
    }

    public final void updateIsEnabled() {
        this.mUiThreadContext.runWithScissors(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 1));
    }

    public final void updateMLModelState() {
        boolean z = false;
        if (this.mIsGestureHandlingEnabled && this.mContext.getResources().getBoolean(com.android.systemui.R.bool.config_useBackGestureML) && DeviceConfig.getBoolean("systemui", "use_back_gesture_ml_model", false)) {
            z = true;
        }
        if (z == this.mUseMLModel) {
            return;
        }
        this.mUseMLModel = z;
        if (z) {
            this.mUiThreadContext.isCurrentThread();
            if (this.mMLModelIsLoading) {
                Log.d("EdgeBackGestureHandler", "Model tried to load while already loading.");
                return;
            } else {
                this.mMLModelIsLoading = true;
                this.mBackgroundExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 3));
                return;
            }
        }
        BackGestureTfClassifierProvider backGestureTfClassifierProvider = this.mBackGestureTfClassifierProvider;
        if (backGestureTfClassifierProvider != null) {
            backGestureTfClassifierProvider.getClass();
            this.mBackGestureTfClassifierProvider = null;
            this.mVocab = null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$8, reason: invalid class name */
    public final class AnonymousClass8 implements InputManager.InputDeviceListener {
        public AnonymousClass8() {
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceAdded(int i) {
            InputDevice inputDevice = EdgeBackGestureHandler.this.mInputManager.getInputDevice(i);
            if (inputDevice != null && inputDevice.getSources() == 1056778) {
                boolean isEmpty = ((ArraySet) EdgeBackGestureHandler.this.mTrackpadsConnected).isEmpty();
                ((ArraySet) EdgeBackGestureHandler.this.mTrackpadsConnected).add(Integer.valueOf(i));
                if (isEmpty) {
                    EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                    if (!edgeBackGestureHandler.mIsEnabled || ((ArraySet) edgeBackGestureHandler.mTrackpadsConnected).isEmpty()) {
                        EdgeBackGestureHandler.this.updateIsEnabled();
                        EdgeBackGestureHandler.this.updateCurrentUserResources();
                    }
                }
            }
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceRemoved(int i) {
            ((ArraySet) EdgeBackGestureHandler.this.mTrackpadsConnected).remove(Integer.valueOf(i));
            if (((ArraySet) EdgeBackGestureHandler.this.mTrackpadsConnected).isEmpty()) {
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                if (!edgeBackGestureHandler.mIsEnabled || ((ArraySet) edgeBackGestureHandler.mTrackpadsConnected).isEmpty()) {
                    EdgeBackGestureHandler.this.updateIsEnabled();
                    EdgeBackGestureHandler.this.updateCurrentUserResources();
                }
            }
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceChanged(int i) {
        }
    }
}
