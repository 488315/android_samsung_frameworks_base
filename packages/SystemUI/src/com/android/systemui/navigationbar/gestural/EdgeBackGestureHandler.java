package com.android.systemui.navigationbar.gestural;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.BackAnimationPilferPointerCallbackManager;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.domain.GestureInteractor;
import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.BackPanelUiThread;
import com.android.systemui.util.concurrency.UiThreadContext;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationController$1$$ExternalSyntheticLambda0;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.windowdecor.DragResizeWindowGeometry;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.inject.Provider;
import kotlin.Unit;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
public class EdgeBackGestureHandler implements PluginListener {
    public static final int MAX_LONG_PRESS_TIMEOUT;
    public boolean mAllowGesture;
    public BackAnimationController.BackAnimationImpl mBackAnimation;
    public final AnonymousClass5 mBackCallback;
    public BackGestureTfClassifierProvider mBackGestureTfClassifierProvider;
    public final Provider mBackGestureTfClassifierProviderProvider;
    public final BackPanelController.Factory mBackPanelControllerFactory;
    public float mBackSwipeLinearThreshold;
    public final Executor mBackgroundExecutor;
    public final Handler mBgHandler;
    public final ArraySet mBlockedActivities;
    public Job mBlockedActivitiesJob;
    public float mBottomGestureHeight;
    public NavigationBar$$ExternalSyntheticLambda1 mButtonForcedVisibleCallback;
    public final Context mContext;
    public boolean mDeferSetIsOnLeftEdge;
    public final EdgeBackGestureHandler$$ExternalSyntheticLambda0 mDesktopCornersChangedListener;
    public final Region mDesktopModeExcludeRegion;
    public final Optional mDesktopModeOptional;
    public int mDisablePolicy;
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
    public final AtomicBoolean mGestureBlockingActivityRunning;
    public final GestureInteractor mGestureInteractor;
    public final LogArray mGestureLogInsideInsets;
    public final LogArray mGestureLogOutsideInsets;
    public final GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
    public boolean mInGestureNavMode;
    public boolean mInRejectedExclusion;
    public final AnonymousClass8 mInputDeviceListener;
    public final InputManager mInputManager;
    public final Map mInputMonitorResources;
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
    public final JavaAdapter mJavaAdapter;
    public final Configuration mLastReportedConfig;
    public final LauncherProxyService mLauncherProxyService;
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
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final EdgeBackGestureHandler$$ExternalSyntheticLambda0 mOnIsInPipStateChangedListener;
    public final OneHandOpGestureHandler mOneHandOpGestureHandler;
    public String mPackageName;
    public final BackAnimationPilferPointerCallbackManager mPilferPointerCallbackManager;
    public final Rect mPipExcludedBounds;
    public final Optional mPipOptional;
    public final PluginManager mPluginManager;
    public final LogArray mPredictionLog;
    public int mRightInset;
    private SettingsHelper mSettingsHelper;
    public int mStartingQuickstepRotation;
    public NavigationBar$$ExternalSyntheticLambda0 mStateChangeCallback;
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
    public final AnonymousClass2 mQuickSwitchListener = new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.2
        @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
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
        public final void onTaskCreated(int i, ComponentName componentName) {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            if (componentName != null) {
                edgeBackGestureHandler.mPackageName = componentName.getPackageName();
            } else {
                edgeBackGestureHandler.mPackageName = "_UNKNOWN";
            }
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskStackChanged() {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            edgeBackGestureHandler.mBackgroundExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(edgeBackGestureHandler, 3));
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

    /* renamed from: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        public AnonymousClass1() {
        }

        public final void onSystemGestureExclusionChanged(int i, final Region region, final Region region2) {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            if (i == edgeBackGestureHandler.mDisplayId) {
                edgeBackGestureHandler.mUiThreadContext.getExecutor().execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgeBackGestureHandler.AnonymousClass1 anonymousClass1 = this.f$0;
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

    public interface Factory {
        EdgeBackGestureHandler create(Context context, WindowManager windowManager);
    }

    public class InputMonitorResource {
        public final int mDisplayId;
        public final InputChannelCompat$InputEventReceiver mInputEventReceiver;
        public final InputMonitorCompat mInputMonitorCompat;

        public /* synthetic */ InputMonitorResource(EdgeBackGestureHandler edgeBackGestureHandler, int i, int i2) {
            this(edgeBackGestureHandler, i);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mDisplayId, ")", new StringBuilder("InputMonitorResource (displayId="));
        }

        private InputMonitorResource(final EdgeBackGestureHandler edgeBackGestureHandler, int i) {
            this.mDisplayId = i;
            InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("edge-swipe", i);
            this.mInputMonitorCompat = inputMonitorCompat;
            this.mInputEventReceiver = inputMonitorCompat.getInputReceiver(edgeBackGestureHandler.mUiThreadContext.getLooper(), edgeBackGestureHandler.mUiThreadContext.getChoreographer(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$InputMonitorResource$$ExternalSyntheticLambda0
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                public final void onInputEvent(InputEvent inputEvent) {
                    EdgeBackGestureHandler.m2639$$Nest$monInputEvent(edgeBackGestureHandler, inputEvent);
                }
            });
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:73:0x0131  */
    /* renamed from: -$$Nest$monInputEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2639$$Nest$monInputEvent(EdgeBackGestureHandler edgeBackGestureHandler, InputEvent inputEvent) {
        boolean z;
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
                boolean z3 = BasicRune.NAVBAR_GESTURE;
                if (!z3) {
                    InputMonitorResource inputMonitorResource = (InputMonitorResource) ((HashMap) edgeBackGestureHandler.mInputMonitorResources).get(Integer.valueOf(motionEvent.getDisplayId()));
                    if (inputMonitorResource != null) {
                        inputMonitorResource.mInputEventReceiver.mReceiver.setBatchingEnabled(false);
                    }
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
                boolean zIsWithinInsets = edgeBackGestureHandler.isWithinInsets((int) motionEvent.getX(), (int) motionEvent.getY());
                boolean z4 = !edgeBackGestureHandler.mDisabledForQuickstep && edgeBackGestureHandler.mIsBackGestureAllowed && !edgeBackGestureHandler.mGestureBlockingActivityRunning.get() && ((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled) || !QuickStepContract.isBackGestureDisabled(edgeBackGestureHandler.mSysUiFlags, edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe)) && z3 && motionEvent.getPalm() <= 0.0f && !edgeBackGestureHandler.isMultiWindowCornerGesture(motionEvent);
                if (z3) {
                    z4 &= (edgeBackGestureHandler.mOneHandOpGestureHandler.isGestureBlockedByPolicy(motionEvent) || edgeBackGestureHandler.isBlockSPenGesture(motionEvent)) ? false : true;
                }
                if (edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe) {
                    boolean z5 = (edgeBackGestureHandler.mSysUiFlags & 8589934592L) == 0;
                    if (z4 && z5) {
                        motionEvent.getDisplayId();
                        WindowInsets windowInsets = edgeBackGestureHandler.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
                        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT) {
                            IndicatorCutoutUtil.Companion.getClass();
                            windowInsets = IndicatorCutoutUtil.Companion.getHidWindowInsetsFromUDC(windowInsets);
                        }
                        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                        Rect bounds = edgeBackGestureHandler.mExcludeRegion.getBounds();
                        int i = insets.left;
                        int i2 = insets.top;
                        Point point = edgeBackGestureHandler.mDisplaySize;
                        if (!bounds.contains(i, i2, point.x - insets.right, point.y - insets.bottom)) {
                            z = true;
                        }
                        edgeBackGestureHandler.mAllowGesture = z;
                    } else {
                        z = false;
                        edgeBackGestureHandler.mAllowGesture = z;
                    }
                } else {
                    edgeBackGestureHandler.mAllowGesture = z4 && ((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled) || !edgeBackGestureHandler.mUsingThreeButtonNav) && z3 && !edgeBackGestureHandler.mSettingsHelper.isAccessControlEnabled() && zIsWithinInsets && edgeBackGestureHandler.isWithinTouchRegion(motionEvent) && !(motionEvent.getSource() == 1056778 && motionEvent.getToolType(motionEvent.getActionIndex()) == 1);
                }
                if (BasicRune.NAVBAR_REMOTEVIEW) {
                    edgeBackGestureHandler.mIsBlockGestureOnGame = false;
                    if (edgeBackGestureHandler.mAllowGesture) {
                        edgeBackGestureHandler.mIsBlockGestureOnGame = edgeBackGestureHandler.isBlockingGestureOnGame();
                        edgeBackGestureHandler.mDownPoint.set(motionEvent.getX(), motionEvent.getY());
                        edgeBackGestureHandler.mAllowGesture &= !edgeBackGestureHandler.mIsBlockGestureOnGame;
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
                long jCurrentTimeMillis = System.currentTimeMillis();
                edgeBackGestureHandler.mTmpLogDate.setTime(jCurrentTimeMillis);
                Object date = edgeBackGestureHandler.mLogDateFormat.format(edgeBackGestureHandler.mTmpLogDate);
                LogArray logArray = zIsWithinInsets ? edgeBackGestureHandler.mGestureLogInsideInsets : edgeBackGestureHandler.mGestureLogOutsideInsets;
                Long lValueOf = Long.valueOf(jCurrentTimeMillis);
                if (z3) {
                    date = new Date(jCurrentTimeMillis);
                }
                logArray.log(String.format("Gesture [%d [%s],alw=%B, t3fs=%B, left=%B, defLeft=%B, backAlw=%B, disbld=%B, qsDisbld=%b, blkdAct=%B, pip=%B, disp=%s, wl=%d, il=%d, wr=%d, ir=%d, excl=%s]", lValueOf, date, Boolean.valueOf(edgeBackGestureHandler.mAllowGesture), Boolean.valueOf(edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe), Boolean.valueOf(edgeBackGestureHandler.mIsOnLeftEdge), Boolean.valueOf(edgeBackGestureHandler.mDeferSetIsOnLeftEdge), Boolean.valueOf(edgeBackGestureHandler.mIsBackGestureAllowed), Boolean.valueOf(QuickStepContract.isBackGestureDisabled(edgeBackGestureHandler.mSysUiFlags, edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe)), Boolean.valueOf(edgeBackGestureHandler.mDisabledForQuickstep), Boolean.valueOf(edgeBackGestureHandler.mGestureBlockingActivityRunning.get()), Boolean.valueOf(edgeBackGestureHandler.mIsInPip), edgeBackGestureHandler.mDisplaySize, Integer.valueOf(edgeBackGestureHandler.mEdgeWidthLeft), Integer.valueOf(edgeBackGestureHandler.mLeftInset), Integer.valueOf(edgeBackGestureHandler.mEdgeWidthRight), Integer.valueOf(edgeBackGestureHandler.mRightInset), edgeBackGestureHandler.mExcludeRegion));
                return;
            }
            if (!edgeBackGestureHandler.mAllowGesture && !edgeBackGestureHandler.mLogGesture) {
                if (BasicRune.NAVBAR_GESTURE && edgeBackGestureHandler.mIsBlockGestureOnGame) {
                    float fAbs = Math.abs(motionEvent.getX() - edgeBackGestureHandler.mDownPoint.x);
                    if (fAbs <= Math.abs(motionEvent.getY() - edgeBackGestureHandler.mDownPoint.y) || fAbs <= edgeBackGestureHandler.mTouchSlop) {
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
                    float fAbs2 = Math.abs(motionEvent.getX() - edgeBackGestureHandler.mDownPoint.x);
                    float fAbs3 = Math.abs(motionEvent.getY() - edgeBackGestureHandler.mDownPoint.y);
                    if (fAbs3 > fAbs2 && fAbs3 > edgeBackGestureHandler.mTouchSlop) {
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
                    if (fAbs2 > fAbs3 && fAbs2 > edgeBackGestureHandler.mTouchSlop) {
                        if (edgeBackGestureHandler.mAllowGesture) {
                            BackAnimationController.BackAnimationImpl backAnimationImpl = edgeBackGestureHandler.mBackAnimation;
                            if (backAnimationImpl != null) {
                                BackAnimationController.this.onThresholdCrossed();
                            }
                            if (edgeBackGestureHandler.mBackAnimation == null) {
                                edgeBackGestureHandler.pilferPointers();
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
    public static void m2640$$Nest$msendEvent(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        edgeBackGestureHandler.getClass();
        if (BasicRune.NAVBAR_GESTURE) {
            edgeBackGestureHandler.mMetricsLogger.write(new LogMaker(1931).setType(4).setSubtype(4).addTaggedData(933, Integer.valueOf(i)).addTaggedData(932, 0));
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(jUptimeMillis, jUptimeMillis, i, 4, 0, 0, -1, 0, 8, 257);
        keyEvent.setDisplayId(edgeBackGestureHandler.mContext.getDisplay().getDisplayId());
        ((InputManager) edgeBackGestureHandler.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0);
    }

    static {
        MAX_LONG_PRESS_TIMEOUT = BasicRune.NAVBAR_GESTURE ? 200 : SystemProperties.getInt("gestures.back_timeout", IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$4] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$5] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$6] */
    public EdgeBackGestureHandler(Context context, LauncherProxyService launcherProxyService, SysUiState sysUiState, PluginManager pluginManager, @BackPanelUiThread UiThreadContext uiThreadContext, Executor executor, Handler handler, UserTracker userTracker, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, InputManager inputManager, Optional<Pip> optional, Optional<DesktopMode> optional2, FalsingManager falsingManager, Provider provider, Provider provider2, NotificationShadeWindowController notificationShadeWindowController, GestureInteractor gestureInteractor, JavaAdapter javaAdapter, BackAnimationPilferPointerCallbackManager backAnimationPilferPointerCallbackManager) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Configuration configuration = new Configuration();
        this.mLastReportedConfig = configuration;
        this.mDisplaySize = new Point();
        this.mPipExcludedBounds = new Rect();
        this.mNavBarOverlayExcludedBounds = new Rect();
        this.mExcludeRegion = new Region();
        this.mDesktopModeExcludeRegion = new Region();
        this.mUnrestrictedExcludeRegion = new Region();
        this.mBlockedActivities = new ArraySet();
        this.mBlockedActivitiesJob = null;
        this.mStartingQuickstepRotation = -1;
        this.mDownPoint = new PointF();
        this.mEndPoint = new PointF();
        this.mGestureBlockingActivityRunning = new AtomicBoolean();
        this.mThresholdCrossed = false;
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        this.mTrackpadsConnected = new ArraySet();
        this.mInputMonitorResources = new HashMap();
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
                    backAnimationImpl.setTriggerBack(false);
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
                    backAnimationImpl.setTriggerBack(z);
                }
                if (BasicRune.NAVBAR_GESTURE) {
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("setTriggerBack: triggerBack : ", "EdgeBackGestureHandler", z);
                }
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public final void triggerBack() {
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                edgeBackGestureHandler.mFalsingManager.isFalseTouch(16);
                BackAnimationController.BackAnimationImpl backAnimationImpl = edgeBackGestureHandler.mBackAnimation;
                if (backAnimationImpl == null) {
                    EdgeBackGestureHandler.m2640$$Nest$msendEvent(edgeBackGestureHandler, 0);
                    EdgeBackGestureHandler.m2640$$Nest$msendEvent(edgeBackGestureHandler, 1);
                } else {
                    backAnimationImpl.setTriggerBack(true);
                }
                if (BasicRune.NAVBAR_GESTURE) {
                    StringBuilder sb = new StringBuilder("triggerBack: mBackAnimation: ");
                    sb.append(edgeBackGestureHandler.mBackAnimation);
                    sb.append(", mDisplayId:");
                    RecyclerView$$ExternalSyntheticOutline0.m(edgeBackGestureHandler.mDisplayId, "EdgeBackGestureHandler", sb);
                }
                edgeBackGestureHandler.logGesture(edgeBackGestureHandler.mInRejectedExclusion ? 2 : 1);
                if (edgeBackGestureHandler.mInRejectedExclusion) {
                    return;
                }
                boolean z = edgeBackGestureHandler.mIsTrackpadThreeFingerSwipe;
                GestureType gestureType = GestureType.BACK;
                LauncherProxyService launcherProxyService2 = edgeBackGestureHandler.mLauncherProxyService;
                for (int size = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size)).updateContextualEduStats(z, gestureType);
                }
            }
        };
        this.mSysUiStateCallback = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.6
            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public final void onSystemUiStateChanged(int i, long j) {
                EdgeBackGestureHandler.this.mSysUiFlags = j;
            }
        };
        this.mOnIsInPipStateChangedListener = new EdgeBackGestureHandler$$ExternalSyntheticLambda0(this, 0);
        this.mDesktopCornersChangedListener = new EdgeBackGestureHandler$$ExternalSyntheticLambda0(this, 4);
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
        this.mBgHandler = handler;
        this.mUserTracker = userTracker;
        this.mLauncherProxyService = launcherProxyService;
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
        this.mGestureInteractor = gestureInteractor;
        this.mJavaAdapter = javaAdapter;
        configuration.setTo(context.getResources().getConfiguration());
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mPilferPointerCallbackManager = backAnimationPilferPointerCallbackManager;
        }
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.face_acquired_tilt_too_extreme));
        if (componentNameUnflattenFromString != null) {
            String packageName = componentNameUnflattenFromString.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            try {
                Resources resourcesForApplication = packageManager.getResourcesForApplication(packageManager.getApplicationInfo(packageName, 9728));
                int identifier = resourcesForApplication.getIdentifier("back_gesture_blocking_activities", "array", packageName);
                if (identifier == 0) {
                    Log.e("EdgeBackGestureHandler", "No resource found for gesture-blocking activities");
                } else {
                    for (String str : resourcesForApplication.getStringArray(identifier)) {
                        ComponentName componentNameUnflattenFromString2 = ComponentName.unflattenFromString(str);
                        if (componentNameUnflattenFromString2 != null) {
                            this.mGestureInteractor.addGestureBlockedMatcher(new TaskMatcher.TopActivityComponent(componentNameUnflattenFromString2), GestureInteractor.Scope.Local);
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("EdgeBackGestureHandler", "Failed to add gesture blocking activities", e);
            }
        }
        boolean z = BasicRune.NAVBAR_GESTURE;
        if (z) {
            NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(this.mContext.getDisplayId());
            this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            this.mMetricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
        }
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            this.mEdgeBackSplitGestureHandler = new EdgeBackSplitGestureHandler(this.mContext, this.mDisplayId, this.mSettingsHelper);
        }
        if (z) {
            this.mOneHandOpGestureHandler = new OneHandOpGestureHandler(this.mContext);
        }
        this.mLongPressTimeout = Math.min(MAX_LONG_PRESS_TIMEOUT, ViewConfiguration.getLongPressTimeout());
        this.mGestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(this.mUiThreadContext.getHandler(), handler, this.mContext, new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 0));
        updateCurrentUserResources();
        this.mNotificationShadeWindowController = notificationShadeWindowController;
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.setAction(3);
        this.mEdgeBackPlugin.onMotionEvent(motionEventObtain);
        dispatchToBackAnimation(motionEventObtain);
        motionEventObtain.recycle();
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
        BackAnimationController.BackAnimationImpl backAnimationImpl = this.mBackAnimation;
        if (backAnimationImpl != null) {
            backAnimationImpl.onBackMotion(motionEvent.getActionMasked(), !this.mIsOnLeftEdge ? 1 : 0, motionEvent.getDisplayId(), motionEvent.getX(), motionEvent.getY());
        }
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "EdgeBackGestureHandler:", "  mIsEnabled="), this.mIsEnabled, printWriter, "  mIsAttached="), this.mIsAttached, printWriter, "  mIsBackGestureAllowed="), this.mIsBackGestureAllowed, printWriter, "  mIsGestureHandlingEnabled="), this.mIsGestureHandlingEnabled, printWriter, "  mIsNavBarShownTransiently="), this.mIsNavBarShownTransiently, printWriter, "  mGestureBlockingActivityRunning=");
        sbM.append(this.mGestureBlockingActivityRunning.get());
        printWriter.println(sbM.toString());
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mAllowGesture="), this.mAllowGesture, printWriter, "  mUseMLModel="), this.mUseMLModel, printWriter, "  mDisabledForQuickstep="), this.mDisabledForQuickstep, printWriter, "  mStartingQuickstepRotation="), this.mStartingQuickstepRotation, printWriter, "  mInRejectedExclusion="), this.mInRejectedExclusion, printWriter, "  mExcludeRegion=");
        sbM2.append(this.mExcludeRegion);
        printWriter.println(sbM2.toString());
        printWriter.println("  mUnrestrictedExcludeRegion=" + this.mUnrestrictedExcludeRegion);
        StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsInPip="), this.mIsInPip, printWriter, "  mPipExcludedBounds=");
        sbM3.append(this.mPipExcludedBounds);
        printWriter.println(sbM3.toString());
        printWriter.println("  mDesktopModeExclusionRegion=" + this.mDesktopModeExcludeRegion);
        printWriter.println("  mNavBarOverlayExcludedBounds=" + this.mNavBarOverlayExcludedBounds);
        StringBuilder sbM4 = MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mEdgeWidthLeft="), this.mEdgeWidthLeft, printWriter, "  mEdgeWidthRight="), this.mEdgeWidthRight, printWriter, "  mLeftInset="), this.mLeftInset, printWriter, "  mRightInset="), this.mRightInset, printWriter, "  mMLEnableWidth="), this.mMLEnableWidth, printWriter, "  mMLModelThreshold="), this.mMLModelThreshold, printWriter, "  mTouchSlop="), this.mTouchSlop, printWriter, "  mBottomGestureHeight="), this.mBottomGestureHeight, printWriter, "  mPredictionLog=");
        sbM4.append(String.join("\n", this.mPredictionLog));
        printWriter.println(sbM4.toString());
        printWriter.println("  mGestureLogInsideInsets=" + String.join("\n", this.mGestureLogInsideInsets));
        printWriter.println("  mGestureLogOutsideInsets=" + String.join("\n", this.mGestureLogOutsideInsets));
        printWriter.println("  mTrackpadsConnected=" + ((String) this.mTrackpadsConnected.stream().map(new EdgeBackGestureHandler$$ExternalSyntheticLambda3()).collect(Collectors.joining())));
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mUsingThreeButtonNav="), this.mUsingThreeButtonNav, printWriter);
        if (BasicRune.NAVBAR_GESTURE) {
            MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mDisablePolicy="), this.mDisablePolicy, printWriter);
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsLargeCoverBackGestureEnabled="), this.mIsLargeCoverBackGestureEnabled, printWriter);
        }
        printWriter.println("  mEdgeBackPlugin=" + this.mEdgeBackPlugin);
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.dump(printWriter);
        }
        printWriter.println("  mInputMonitorResources=" + this.mInputMonitorResources);
        Iterator it = ((HashMap) this.mInputMonitorResources).entrySet().iterator();
        while (it.hasNext()) {
            InputMonitorResource inputMonitorResource = (InputMonitorResource) ((Map.Entry) it.next()).getValue();
            inputMonitorResource.getClass();
            printWriter.println("\t" + inputMonitorResource);
        }
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = this.mEdgeBackSplitGestureHandler;
            edgeBackSplitGestureHandler.getClass();
            printWriter.println("EdgeBackSplitGestureHandler :");
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  enabled=", edgeBackSplitGestureHandler.enabled);
            printWriter.println("  tmpBounds=" + edgeBackSplitGestureHandler.tmpBounds);
            printWriter.println("  displayController=" + edgeBackSplitGestureHandler.displayController);
            printWriter.println("  splitScreenController=" + edgeBackSplitGestureHandler.splitScreenController);
        }
    }

    public final boolean isBackGestureAllowed(MotionEvent motionEvent) {
        boolean z = false;
        boolean z2 = (this.mDisabledForQuickstep || !this.mIsBackGestureAllowed || !isWithinInsets((int) motionEvent.getX(), (int) motionEvent.getY()) || this.mGestureBlockingActivityRunning.get() || QuickStepContract.isBackGestureDisabled(this.mSysUiFlags, this.mIsTrackpadThreeFingerSwipe) || !isWithinTouchRegion(motionEvent) || isMultiWindowCornerGesture(motionEvent)) ? false : true;
        if (BasicRune.NAVBAR_GESTURE) {
            if (!this.mOneHandOpGestureHandler.isGestureBlockedByPolicy(motionEvent) && !isBlockSPenGesture(motionEvent)) {
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
    public final boolean isWithinTouchRegion(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean z = this.mIsInPip && this.mPipExcludedBounds.contains(x, y);
        boolean z2 = this.mDesktopModeExcludeRegion.contains(x, y) && DragResizeWindowGeometry.isEdgeResizePermitted(motionEvent);
        if (!z && !z2 && !this.mNavBarOverlayExcludedBounds.contains(x, y) && motionEvent.getDisplayId() == this.mDisplayId) {
            Map map = this.mVocab;
            int iIntValue = map != null ? ((Integer) map.getOrDefault(this.mPackageName, -1)).intValue() : -1;
            int i = this.mEdgeWidthLeft;
            int i2 = this.mLeftInset;
            ?? r7 = (x < i + i2 || x >= (this.mDisplaySize.x - this.mEdgeWidthRight) - this.mRightInset) ? 1 : 0;
            if (r7 != 0) {
                int i3 = this.mMLEnableWidth;
                if (x >= i2 + i3 && x < (this.mDisplaySize.x - i3) - this.mRightInset && this.mUseMLModel && !this.mMLModelIsLoading && this.mBackGestureTfClassifierProvider != null && iIntValue != -1) {
                    this.mMLResults = -1.0f;
                }
            }
            this.mPredictionLog.log(String.format("Prediction [%d,%d,%d,%d,%f,%d]", Long.valueOf(System.currentTimeMillis()), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(iIntValue), Float.valueOf(this.mMLResults), Integer.valueOf((int) r7)));
            if (!BasicRune.NAVBAR_GESTURE && this.mIsNavBarShownTransiently) {
                this.mLogGesture = true;
                return r7;
            }
            if (!this.mExcludeRegion.contains(x, y)) {
                this.mInRejectedExclusion = this.mUnrestrictedExcludeRegion.contains(x, y);
                this.mLogGesture = true;
                return r7;
            }
            if (r7 != 0) {
                PointF pointF = this.mEndPoint;
                pointF.x = -1.0f;
                pointF.y = -1.0f;
                this.mLogGesture = true;
                logGesture(3);
                return false;
            }
        }
        return false;
    }

    public final void logGesture(int i) {
        if (this.mLogGesture) {
            this.mLogGesture = false;
            Map map = this.mVocab;
            String str = (!this.mUseMLModel || map == null || !map.containsKey(this.mPackageName) || ((Integer) map.get(this.mPackageName)).intValue() >= 100) ? "" : this.mPackageName;
            PointF pointF = this.mDownPoint;
            int i2 = (int) pointF.y;
            int i3 = this.mIsOnLeftEdge ? 1 : 2;
            int i4 = (int) pointF.x;
            PointF pointF2 = this.mEndPoint;
            int i5 = (int) pointF2.x;
            int i6 = (int) pointF2.y;
            int i7 = this.mEdgeWidthLeft + this.mLeftInset;
            int i8 = this.mDisplaySize.x - (this.mEdgeWidthRight + this.mRightInset);
            float f = this.mUseMLModel ? this.mMLResults : -2.0f;
            int i9 = this.mIsTrackpadThreeFingerSwipe ? 2 : 1;
            StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
            builderNewBuilder.setAtomId(IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
            builderNewBuilder.writeInt(i);
            builderNewBuilder.writeInt(i2);
            builderNewBuilder.writeInt(i3);
            builderNewBuilder.writeInt(i4);
            builderNewBuilder.writeInt(i2);
            builderNewBuilder.writeInt(i5);
            builderNewBuilder.writeInt(i6);
            builderNewBuilder.writeInt(i7);
            builderNewBuilder.writeInt(i8);
            builderNewBuilder.writeFloat(f);
            builderNewBuilder.writeString(str);
            builderNewBuilder.writeInt(i9);
            builderNewBuilder.usePooledBuffer();
            StatsLog.write(builderNewBuilder.build());
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mStartingQuickstepRotation > -1) {
            int rotation = configuration.windowConfiguration.getRotation();
            int i = this.mStartingQuickstepRotation;
            this.mDisabledForQuickstep = i > -1 && i != rotation;
        }
        Log.i("NoBackGesture", "Config changed: newConfig=" + configuration + " lastReportedConfig=" + this.mLastReportedConfig);
        int iDiff = configuration.diff(this.mLastReportedConfig);
        if ((1073741824 & iDiff) != 0 || (iDiff & 4096) != 0) {
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
            updateDisablePolicy(((NavBarStateManagerImpl) this.mNavBarStateManager).states.gestureDisablePolicy);
        }
        this.mIsAttached = true;
        this.mLauncherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this.mQuickSwitchListener);
        ((SysUiStateImpl) this.mSysUiState).addCallback(this.mSysUiStateCallback);
        InputManager inputManager = this.mInputManager;
        AnonymousClass8 anonymousClass8 = this.mInputDeviceListener;
        inputManager.registerInputDeviceListener(anonymousClass8, this.mBgHandler);
        for (int i : this.mInputManager.getInputDeviceIds()) {
            anonymousClass8.onInputDeviceAdded(i);
        }
        updateIsEnabled();
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mUiThreadContext.getExecutor());
        if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
            this.mEdgeBackSplitGestureHandler.onNavBarAttached();
        }
    }

    public final void onNavBarDetached() {
        this.mIsAttached = false;
        this.mLauncherProxyService.removeCallback((LauncherProxyService.LauncherProxyListener) this.mQuickSwitchListener);
        ((SysUiStateImpl) this.mSysUiState).stateDispatcher.listeners.remove(this.mSysUiStateCallback);
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
            boolean zIsGesturalMode = QuickStepContract.isGesturalMode(i);
            this.mInGestureNavMode = zIsGesturalMode;
            if (BasicRune.NAVBAR_GESTURE && zIsGesturalMode) {
                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                this.mInGestureNavMode = i == 2;
                updateDisablePolicy(((NavBarStateManagerImpl) this.mNavBarStateManager).states.gestureDisablePolicy);
            }
            updateIsEnabled();
            updateCurrentUserResources();
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        setEdgeBackPlugin((NavigationEdgeBackPlugin) plugin);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        BackPanelController backPanelControllerCreate = this.mBackPanelControllerFactory.create(this.mContext, this.mWindowManager, this.mUiThreadContext.getHandler());
        backPanelControllerCreate.init();
        setEdgeBackPlugin(backPanelControllerCreate);
    }

    public final void pilferPointers() {
        InputMonitorResource inputMonitorResource = (InputMonitorResource) ((HashMap) this.mInputMonitorResources).get(Integer.valueOf(this.mDisplayId));
        if (inputMonitorResource != null) {
            inputMonitorResource.mInputMonitorCompat.mInputMonitor.pilferPointers();
            this.mFalsingManager.isFalseTouch(16);
            inputMonitorResource.mInputEventReceiver.mReceiver.setBatchingEnabled(true);
        }
    }

    public final void setBackAnimation(BackAnimationController.BackAnimationImpl backAnimationImpl) {
        StringBuilder sb = new StringBuilder("setBackAnimation for displayId = ");
        int i = this.mDisplayId;
        sb.append(i);
        sb.append(" : ");
        sb.append(backAnimationImpl);
        Log.d("EdgeBackGestureHandler", sb.toString());
        this.mBackAnimation = backAnimationImpl;
        if (backAnimationImpl != null) {
            Executor executor = this.mUiThreadContext.getExecutor();
            boolean z = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
            BackAnimationController backAnimationController = BackAnimationController.this;
            if (z) {
                EdgeBackGestureHandler$$ExternalSyntheticLambda6 edgeBackGestureHandler$$ExternalSyntheticLambda6 = new EdgeBackGestureHandler$$ExternalSyntheticLambda6(this, executor, 0);
                Integer numValueOf = Integer.valueOf(i);
                BackAnimationPilferPointerCallbackManager backAnimationPilferPointerCallbackManager = this.mPilferPointerCallbackManager;
                BackAnimationPilferPointerCallbackManager.CompositeRunnable compositeRunnable = backAnimationPilferPointerCallbackManager.callbacks;
                compositeRunnable.runnables.put(numValueOf, edgeBackGestureHandler$$ExternalSyntheticLambda6);
                BackAnimationController.BackAnimationImpl backAnimationImpl2 = (BackAnimationController.BackAnimationImpl) backAnimationPilferPointerCallbackManager.backAnimation.orElse(null);
                if (backAnimationImpl2 != null) {
                    BackAnimationController.this.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(1, backAnimationImpl2, compositeRunnable));
                }
                if (i == 0) {
                    backAnimationController.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(2, backAnimationImpl, new EdgeBackGestureHandler$$ExternalSyntheticLambda10(this, executor, 1)));
                }
            } else {
                backAnimationController.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(1, backAnimationImpl, new EdgeBackGestureHandler$$ExternalSyntheticLambda6(this, executor, 1)));
                backAnimationController.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(2, backAnimationImpl, new EdgeBackGestureHandler$$ExternalSyntheticLambda10(this, executor, 2)));
            }
            updateBackAnimationThresholds();
            if (this.mLightBarControllerProvider.get() != null) {
                BackAnimationController.BackAnimationImpl backAnimationImpl3 = this.mBackAnimation;
                EdgeBackGestureHandler$$ExternalSyntheticLambda10 edgeBackGestureHandler$$ExternalSyntheticLambda10 = new EdgeBackGestureHandler$$ExternalSyntheticLambda10(this, executor, 0);
                BackAnimationController backAnimationController2 = BackAnimationController.this;
                backAnimationController2.mCustomizer = edgeBackGestureHandler$$ExternalSyntheticLambda10;
                backAnimationController2.mAnimationBackground.mCustomizer = edgeBackGestureHandler$$ExternalSyntheticLambda10;
            }
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
        final float fMin = Math.min(f, this.mBackSwipeLinearThreshold);
        final BackAnimationController.BackAnimationImpl backAnimationImpl = this.mBackAnimation;
        final float f2 = this.mNonLinearFactor;
        BackAnimationController.this.mShellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BackAnimationController.BackAnimationImpl backAnimationImpl2 = backAnimationImpl;
                float f3 = fMin;
                float f4 = f;
                float f5 = f2;
                BackAnimationController backAnimationController = BackAnimationController.this;
                backAnimationController.mCurrentTracker.setProgressThresholds(f3, f4, f5);
                backAnimationController.mQueuedTracker.setProgressThresholds(f3, f4, f5);
            }
        });
    }

    public final void updateCurrentUserResources() {
        NavigationBar$$ExternalSyntheticLambda1 navigationBar$$ExternalSyntheticLambda1;
        int i = this.mEdgeWidthLeft;
        int i2 = this.mEdgeWidthRight;
        boolean z = BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY;
        int i3 = this.mDisplayId;
        Resources resources = (z && i3 == 1) ? this.mContext.getResources() : this.mNavigationModeController.getCurrentUserContext().getResources();
        this.mEdgeWidthLeft = this.mGestureNavigationSettingsObserver.getLeftSensitivity(resources);
        this.mEdgeWidthRight = this.mGestureNavigationSettingsObserver.getRightSensitivity(resources);
        boolean z2 = this.mIsButtonForcedVisible;
        boolean zAreNavigationButtonForcedVisible = this.mGestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        this.mIsButtonForcedVisible = zAreNavigationButtonForcedVisible;
        this.mIsBackGestureAllowed = !zAreNavigationButtonForcedVisible;
        if (z2 != zAreNavigationButtonForcedVisible && (navigationBar$$ExternalSyntheticLambda1 = this.mButtonForcedVisibleCallback) != null) {
            navigationBar$$ExternalSyntheticLambda1.accept(Boolean.valueOf(zAreNavigationButtonForcedVisible));
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float f = DeviceConfig.getFloat("systemui", "back_gesture_bottom_height", resources.getDimension(R.dimen.seekbar_track_background_height_material) / displayMetrics.density);
        if (BasicRune.NAVBAR_BOTTOM_GESTURE_SENSITIVITY) {
            this.mBottomGestureHeight = this.mGestureNavigationSettingsObserver.getBottomSensitivity(resources);
        } else {
            this.mBottomGestureHeight = TypedValue.applyDimension(1, f, displayMetrics);
        }
        int iApplyDimension = (int) TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mMLEnableWidth = iApplyDimension;
        int i4 = this.mEdgeWidthRight;
        if (iApplyDimension > i4) {
            this.mMLEnableWidth = i4;
        }
        int i5 = this.mMLEnableWidth;
        int i6 = this.mEdgeWidthLeft;
        if (i5 > i6) {
            this.mMLEnableWidth = i6;
        }
        this.mTouchSlop = this.mViewConfiguration.getScaledTouchSlop() * DeviceConfig.getFloat("systemui", "back_gesture_slop_multiplier", 0.75f);
        this.mBackSwipeLinearThreshold = resources.getDimension(R.dimen.starting_surface_default_icon_size);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.car_double_line_list_item_height, typedValue, true);
        this.mNonLinearFactor = typedValue.getFloat();
        updateBackAnimationThresholds();
        if (!BasicRune.NAVBAR_ENABLED) {
            this.mBackgroundExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 4));
        }
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

    public final void updateDisablePolicy(int i) {
        this.mDisablePolicy = i;
        OneHandOpGestureHandler oneHandOpGestureHandler = this.mOneHandOpGestureHandler;
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(oneHandOpGestureHandler.disablePolicy, i, "setDisablePolicy() old=", ", new=", "OneHandOpGestureHandler");
        oneHandOpGestureHandler.disablePolicy = i;
        if (i == 0) {
            oneHandOpGestureHandler.unbindOHOService();
            return;
        }
        if (i == 2 && !oneHandOpGestureHandler.isOneHandOpServiceConnected) {
            Log.d("OneHandOpGestureHandler", "bindOHOService()");
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.sidegesturepad", "com.samsung.android.sidegesturepad.SGPService");
            try {
                oneHandOpGestureHandler.context.bindService(intent, oneHandOpGestureHandler.mConnection, 1);
            } catch (Exception e) {
                e.printStackTrace();
                Unit unit = Unit.INSTANCE;
            }
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
        this.mUiThreadContext.runWithScissors(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 5));
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
        if (!z) {
            if (this.mBackGestureTfClassifierProvider != null) {
                this.mBackGestureTfClassifierProvider = null;
                this.mVocab = null;
                return;
            }
            return;
        }
        this.mUiThreadContext.isCurrentThread();
        if (this.mMLModelIsLoading) {
            Log.d("EdgeBackGestureHandler", "Model tried to load while already loading.");
        } else {
            this.mMLModelIsLoading = true;
            this.mBackgroundExecutor.execute(new EdgeBackGestureHandler$$ExternalSyntheticLambda2(this, 1));
        }
    }

    /* renamed from: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$8, reason: invalid class name */
    public class AnonymousClass8 implements InputManager.InputDeviceListener {
        public AnonymousClass8() {
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceAdded(int i) {
            InputDevice inputDevice = EdgeBackGestureHandler.this.mInputManager.getInputDevice(i);
            if (inputDevice != null && inputDevice.getSources() == 1056778) {
                EdgeBackGestureHandler.this.mUiThreadContext.getHandler().post(new EdgeBackGestureHandler$8$$ExternalSyntheticLambda0(this, i, 1));
            }
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceRemoved(int i) {
            EdgeBackGestureHandler.this.mUiThreadContext.getHandler().post(new EdgeBackGestureHandler$8$$ExternalSyntheticLambda0(this, i, 0));
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceChanged(int i) {
        }
    }
}
