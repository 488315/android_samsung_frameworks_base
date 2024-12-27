package com.android.server.policy;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IUiModeManager;
import android.app.ProfilerInfo;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiPlaybackClient;
import android.hardware.input.InputManager;
import android.media.AudioManagerInternal;
import android.media.AudioSystem;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeviceIdleManager;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.os.Vibrator;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.service.dreams.IDreamManager;
import android.service.vr.IPersistentVrStateCallbacks;
import android.telecom.TelecomManager;
import android.util.EventLog;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillManagerInternal;

import com.android.internal.accessibility.AccessibilityDirectAccessController;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.Clock;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.policy.LogDecelerateInterpolator;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ScreenshotRequest;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AccessibilityManagerInternal;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayPowerController;
import com.android.server.input.InputManagerService;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.SideFpsEventHandler.AnonymousClass2;
import com.android.server.policy.keyguard.KeyguardServiceDelegate;
import com.android.server.policy.keyguard.KeyguardServiceWrapper;
import com.android.server.policy.keyguard.KeyguardStateMonitor;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.vibrator.HapticFeedbackVibrationProvider;
import com.android.server.vr.VrManagerService;
import com.android.server.wallpaper.WallpaperManagerService;
import com.android.server.wm.ActivityManagerPerformance;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.DisplayPolicyExt;
import com.android.server.wm.DisplayRotation;
import com.android.server.wm.TspStateController;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowManagerServiceExt;
import com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda2;
import com.android.server.wm.WindowState;
import com.android.server.wm.WmScreenshotController;

import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.InputRune;
import com.samsung.android.view.SemWindowManager;

import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class PhoneWindowManager implements WindowManagerPolicy {
    public static final boolean DEBUG_INPUT = InputRune.PWM_KEY_FACTORY_MODE_POLICY;
    public static final boolean DEBUG_KEYGUARD = true;
    public static final boolean DEBUG_WAKEUP = true;
    public static final int[] WINDOW_TYPES_WHERE_HOME_DOESNT_WORK = {2003, 2010};
    public AccessibilityManager mAccessibilityManager;
    public AccessibilityManagerInternal mAccessibilityManagerInternal;
    public AccessibilityShortcutController mAccessibilityShortcutController;
    public boolean mAcquireInProgress;
    public ActivityManagerInternal mActivityManagerInternal;
    public IActivityManager mActivityManagerService;
    public ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public boolean mAllowStartActivityForLongPressOnPowerDuringSetup;
    public boolean mAltTabConsumedByDeX;
    public AppOpsManager mAppOpsManager;
    public AudioManagerInternal mAudioManagerInternal;
    public AutofillManagerInternal mAutofillManagerInternal;
    public volatile boolean mBackKeyHandled;
    public ActivityManager.RecentTaskInfo mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp;
    public volatile boolean mBootAnimationDismissable;
    public boolean mBootMessageNeedsHiding;
    public PowerManager.WakeLock mBroadcastWakeLock;
    public BurnInProtectionHelper mBurnInProtectionHelper;
    public ButtonOverridePermissionChecker mButtonOverridePermissionChecker;
    public Intent mCarDockIntent;
    public Context mContext;
    public int mCurrentUserId;
    public Display mDefaultDisplay;
    public DisplayPolicy mDefaultDisplayPolicy;
    public DisplayRotation mDefaultDisplayRotation;
    public final DeferredKeyActionExecutor mDeferredKeyActionExecutor;
    public Intent mDeskDockIntent;
    public volatile boolean mDeviceGoingToSleep;
    public Display mDexDisplay;
    public volatile boolean mDismissImeOnBackKeyPressed;
    public DisplayFoldController mDisplayFoldController;
    public final SparseArray mDisplayHomeButtonHandlers;
    public DisplayManager mDisplayManager;
    public final AnonymousClass13 mDockReceiver;
    public int mDoublePressOnPowerBehavior;
    public int mDoublePressOnStemPrimaryBehavior;
    public int mDoubleTapOnHomeBehavior;
    public DreamManagerInternal mDreamManagerInternal;
    public volatile boolean mEndCallKeyHandled;
    public final AnonymousClass4 mEndCallLongPress;
    public int mEndcallBehavior;
    public PhoneWindowManagerExt mExt;
    public Display mExtraDisplay;
    public DisplayPolicy mExtraDisplayPolicy;
    public DisplayRotation mExtraDisplayRotation;
    public ActivityTaskManager.RootTaskInfo mFocusedTaskInfoOnStemPrimarySingleKeyUp;
    public GlobalActions mGlobalActions;
    public PhoneWindowManager$Injector$$ExternalSyntheticLambda0 mGlobalActionsFactory;
    public GlobalKeyManager mGlobalKeyManager;
    public boolean mGoToSleepOnButtonPressTheaterMode;
    public final AnonymousClass2 mHDMIObserver;
    public boolean mHandleVolumeKeysInWM;
    public PolicyHandler mHandler;
    public HapticFeedbackVibrationProvider mHapticFeedbackVibrationProvider;
    public boolean mHasFeatureAuto;
    public boolean mHasFeatureHdmiCec;
    public boolean mHasFeatureLeanback;
    public boolean mHasFeatureWatch;
    public boolean mHavePendingMediaKeyRepeatWithWakeLock;
    public AnonymousClass1 mHdmiControl;
    public Intent mHomeIntent;
    public int mIncallBackBehavior;
    public int mIncallPowerBehavior;
    public InputManager mInputManager;
    public InputManagerService.LocalService mInputManagerInternal;
    public volatile boolean mIsGoingToSleepDefaultDisplay;
    public KeyCombinationManager mKeyCombinationManager;
    public boolean mKeyguardBound;
    KeyguardServiceDelegate mKeyguardDelegate;
    public boolean mKeyguardDrawnOnce;
    public int mKeyguardDrawnTimeout;
    public boolean mKeyguardOccludedChanged;
    public boolean mKidsModeEnabled;
    public int mLastModifierState;
    public int mLidKeyboardAccessibility;
    public int mLidNavigationAccessibility;
    public boolean mLockAfterDreamingTransitionFinished;
    public boolean mLockNowPending;
    public LockPatternUtils mLockPatternUtils;
    public int mLockScreenTimeout;
    public boolean mLockScreenTimerActive;
    public MetricsLogger mLogger;
    public int mLongPressOnBackBehavior;
    public int mLongPressOnHomeBehavior;
    public long mLongPressOnPowerAssistantTimeoutMs;
    public int mLongPressOnPowerBehavior;
    public int mLongPressOnStemPrimaryBehavior;
    public ModifierShortcutManager mModifierShortcutManager;
    public final AnonymousClass13 mMultiuserReceiver;
    public PackageManager mPackageManager;
    public boolean mPendingCapsLockToggle;
    public boolean mPendingKeyguardOccluded;
    public boolean mPendingMetaAction;
    public final AnonymousClass3 mPersistentVrModeListener;
    public volatile boolean mPictureInPictureVisible;
    public int mPowerButtonSuppressionDelayMillis;
    public ComponentName mPowerDoublePressTargetActivity;
    public volatile boolean mPowerKeyHandled;
    public PowerManager.WakeLock mPowerKeyWakeLock;
    public PowerManager mPowerManager;
    public PowerManagerInternal mPowerManagerInternal;
    public int mPowerVolUpBehavior;
    public boolean mPreloadedRecentApps;
    public ComponentName mPrimaryShortPressTargetActivity;
    public int mRecentAppsHeldModifiers;
    public volatile boolean mRecentsVisible;
    public boolean mSafeMode;
    public final ScreenLockTimeout mScreenLockTimeout;
    public ActivityTaskManagerService.SleepTokenAcquirerImpl mScreenOffSleepTokenAcquirer;
    public int mSearchKeyBehavior;
    public ComponentName mSearchKeyTargetActivity;
    public SensorPrivacyManager mSensorPrivacyManager;
    public int mSettingsKeyBehavior;
    public SettingsObserver mSettingsObserver;
    public int mShortPressOnPowerBehavior;
    public int mShortPressOnSleepBehavior;
    public int mShortPressOnStemPrimaryBehavior;
    public int mShortPressOnWindowBehavior;
    public boolean mShouldEarlyShortPressOnPower;
    public boolean mShouldEarlyShortPressOnStemPrimary;
    public SideFpsEventHandler mSideFpsEventHandler;
    public boolean mSilenceRingerOnSleepKey;
    public SingleKeyGestureDetector mSingleKeyGestureDetector;
    public final Object mSleepTokenLock;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public IStatusBarService mStatusBarService;
    public boolean mSupportLongPressPowerWhenNonInteractive;
    public boolean mSupportShortPressPowerWhenDefaultDisplayOn;
    public boolean mSystemBooted;
    public boolean mSystemKeyRequested;
    public boolean mSystemNavigationKeysEnabled;
    public boolean mSystemReady;
    public TalkbackShortcutController mTalkbackShortcutController;
    public volatile int mTopFocusedDisplayId;
    public int mTriplePressOnPowerBehavior;
    public int mTriplePressOnStemPrimaryBehavior;
    public int mUiMode;
    public IUiModeManager mUiModeManager;
    public boolean mUseTvRouting;
    public UserManagerInternal mUserManagerInternal;
    public int mVeryLongPressOnPowerBehavior;
    public Vibrator mVibrator;
    public Intent mVrHeadsetHomeIntent;
    public volatile VrManagerService.LocalService mVrManagerInternal;
    public boolean mWakeGestureEnabledSetting;
    public MyWakeGestureListener mWakeGestureListener;
    public boolean mWakeOnAssistKeyPress;
    public boolean mWakeOnBackKeyPress;
    public boolean mWakeOnDpadKeyPress;
    public long mWakeUpToLastStateTimeout;
    public WallpaperManagerService.LocalService mWallpaperManagerInternal;
    public WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    public WindowManagerInternal mWindowManagerInternal;
    public WindowWakeUpPolicy mWindowWakeUpPolicy;
    public final Object mLock = new Object();
    public final SparseArray mScreenOnListeners = new SparseArray();
    public final Object mServiceAcquireLock = new Object();
    public boolean mEnableBugReportKeyboardShortcut = false;
    public boolean mEnableCarDockHomeCapture = true;
    public final AnonymousClass1 mKeyguardDrawnCallback = new AnonymousClass1(this);
    public volatile boolean mNavBarVirtualKeyHapticFeedbackEnabled = true;
    public volatile int mPendingWakeKey = -1;
    public int mCameraLensCoverState = -1;
    public boolean mStylusButtonsEnabled = true;
    public boolean mHasSoftInput = false;
    public final HashSet mAllowLockscreenWhenOnDisplays = new HashSet();
    public int mRingerToggleChord = 0;
    public final SparseArray mConsumedKeysForDevice = new SparseArray();
    public final SparseArray mFallbackActions = new SparseArray();

    /* renamed from: com.android.server.policy.PhoneWindowManager$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final Object this$0;

        public /* synthetic */ AnonymousClass1(Object obj) {
            this.this$0 = obj;
        }

        public void onShowingChanged() {
            ((Injector) this.this$0).mWindowManagerFuncs.onKeyguardShowingAndNotOccludedChanged();
        }

        public void onTrustedChanged() {
            ((Injector) this.this$0).mWindowManagerFuncs.notifyKeyguardTrustedChanged();
        }
    }

    /* renamed from: com.android.server.policy.PhoneWindowManager$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass4(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    ((PhoneWindowManager) this.this$0).mEndCallKeyHandled = true;
                    ((PhoneWindowManager) this.this$0)
                            .performHapticFeedback(
                                    0, "End Call - Long Press - Show Global Actions", false);
                    ((PhoneWindowManager) this.this$0).showGlobalActionsInternal(-1);
                    break;
                default:
                    DisplayHomeButtonHandler displayHomeButtonHandler =
                            (DisplayHomeButtonHandler) this.this$0;
                    KeyEvent keyEvent = displayHomeButtonHandler.mPendingHomeKeyEvent;
                    if (keyEvent != null) {
                        PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                        boolean z = PhoneWindowManager.DEBUG_INPUT;
                        phoneWindowManager.handleShortPressOnHome(keyEvent);
                        ((DisplayHomeButtonHandler) this.this$0).mPendingHomeKeyEvent = null;
                        break;
                    }
                    break;
            }
        }
    }

    public final class ButtonOverridePermissionChecker {}

    public final class DisplayHomeButtonHandler {
        public final int mDisplayId;
        public boolean mHomeConsumed;
        public final AnonymousClass4 mHomeDoubleTapTimeoutRunnable = new AnonymousClass4(1, this);
        public boolean mHomePressed;
        public KeyEvent mPendingHomeKeyEvent;

        public DisplayHomeButtonHandler(int i) {
            this.mDisplayId = i;
        }

        public final void handleDoubleTapOnHome(KeyEvent keyEvent, int i) {
            if (this.mHomeConsumed) {
                return;
            }
            PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
            int i2 = phoneWindowManager.mDoubleTapOnHomeBehavior;
            if (i2 == 1) {
                phoneWindowManager.logKeyboardSystemsEvent(
                        keyEvent, KeyboardMetricsCollector.KeyboardLogEvent.APP_SWITCH);
                this.mHomeConsumed = true;
                if (phoneWindowManager.mExt.isInDexMode()) {
                    phoneWindowManager.toggleRecentApps(i);
                    return;
                } else {
                    phoneWindowManager.toggleRecentApps(0);
                    return;
                }
            }
            if (i2 != 2) {
                AudioService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("No action or undefined behavior for double tap home: "),
                        phoneWindowManager.mDoubleTapOnHomeBehavior,
                        "WindowManager");
                return;
            }
            this.mHomeConsumed = true;
            StatusBarManagerInternal statusBarManagerInternal =
                    phoneWindowManager.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                        (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                if (StatusBarManagerService.this.mBar != null) {
                    try {
                        StatusBarManagerService.this.mBar.showPictureInPictureMenu();
                    } catch (RemoteException unused) {
                    }
                }
            }
        }

        public final String toString() {
            return String.format(
                    "mDisplayId = %d, mHomePressed = %b",
                    Integer.valueOf(this.mDisplayId), Boolean.valueOf(this.mHomePressed));
        }
    }

    class Injector {
        public final Context mContext;
        public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

        public Injector(
                Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
            this.mContext = context;
            this.mWindowManagerFuncs = windowManagerFuncs;
        }
    }

    public final class MyWakeGestureListener {
        public final Handler mHandler;
        public final Sensor mSensor;
        public final SensorManager mSensorManager;
        public boolean mTriggerRequested;
        public final Object mLock = new Object();
        public final WakeGestureListener$1 mListener =
                new TriggerEventListener() { // from class:
                                             // com.android.server.policy.WakeGestureListener$1
                    @Override // android.hardware.TriggerEventListener
                    public final void onTrigger(TriggerEvent triggerEvent) {
                        synchronized (PhoneWindowManager.MyWakeGestureListener.this.mLock) {
                            PhoneWindowManager.MyWakeGestureListener myWakeGestureListener =
                                    PhoneWindowManager.MyWakeGestureListener.this;
                            myWakeGestureListener.mTriggerRequested = false;
                            myWakeGestureListener.mHandler.post(
                                    myWakeGestureListener.mWakeUpRunnable);
                        }
                    }
                };
        public final WakeGestureListener$2 mWakeUpRunnable =
                new Runnable() { // from class: com.android.server.policy.WakeGestureListener$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PhoneWindowManager.MyWakeGestureListener myWakeGestureListener =
                                PhoneWindowManager.MyWakeGestureListener.this;
                        synchronized (PhoneWindowManager.this.mLock) {
                            try {
                                if (PhoneWindowManager.this.shouldEnableWakeGestureLp()) {
                                    PhoneWindowManager.this.performHapticFeedback(
                                            1, "Wake Up", false);
                                    WindowWakeUpPolicy windowWakeUpPolicy =
                                            PhoneWindowManager.this.mWindowWakeUpPolicy;
                                    if (windowWakeUpPolicy.canWakeUp(
                                            windowWakeUpPolicy
                                                    .mAllowTheaterModeWakeFromWakeGesture)) {
                                        windowWakeUpPolicy.wakeUp(
                                                windowWakeUpPolicy.mClock.uptimeMillis(),
                                                4,
                                                "GESTURE");
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                };

        public MyWakeGestureListener(Context context, PolicyHandler policyHandler) {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            this.mSensorManager = sensorManager;
            this.mHandler = policyHandler;
            this.mSensor = sensorManager.getDefaultSensor(23);
        }
    }

    public final class PolicyHandler extends Handler {
        public PolicyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Intent intent;
            AudioManagerInternal audioManagerInternal;
            int i = -1;
            switch (message.what) {
                case 3:
                    PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                    KeyEvent keyEvent = (KeyEvent) message.obj;
                    boolean z = PhoneWindowManager.DEBUG_INPUT;
                    if (z) {
                        phoneWindowManager.getClass();
                        Slog.d("WindowManager", "dispatchMediaKeyWithWakeLock: " + keyEvent);
                    }
                    if (phoneWindowManager.mHavePendingMediaKeyRepeatWithWakeLock) {
                        if (z) {
                            Slog.d(
                                    "WindowManager",
                                    "dispatchMediaKeyWithWakeLock: canceled repeat");
                        }
                        phoneWindowManager.mHandler.removeMessages(4);
                        phoneWindowManager.mHavePendingMediaKeyRepeatWithWakeLock = false;
                        phoneWindowManager.mBroadcastWakeLock.release();
                    }
                    if (phoneWindowManager.mActivityManagerInternal.isSystemReady()) {
                        MediaSessionLegacyHelper.getHelper(phoneWindowManager.mContext)
                                .sendMediaButtonEvent(keyEvent, true);
                    }
                    if (keyEvent.getAction() != 0 || keyEvent.getRepeatCount() != 0) {
                        phoneWindowManager.mBroadcastWakeLock.release();
                        return;
                    }
                    phoneWindowManager.mHavePendingMediaKeyRepeatWithWakeLock = true;
                    Message obtainMessage = phoneWindowManager.mHandler.obtainMessage(4, keyEvent);
                    obtainMessage.setAsynchronous(true);
                    phoneWindowManager.mHandler.sendMessageDelayed(
                            obtainMessage, ViewConfiguration.getKeyRepeatTimeout());
                    return;
                case 4:
                    PhoneWindowManager phoneWindowManager2 = PhoneWindowManager.this;
                    KeyEvent keyEvent2 = (KeyEvent) message.obj;
                    phoneWindowManager2.mHavePendingMediaKeyRepeatWithWakeLock = false;
                    KeyEvent changeTimeRepeat =
                            KeyEvent.changeTimeRepeat(
                                    keyEvent2,
                                    SystemClock.uptimeMillis(),
                                    1,
                                    keyEvent2.getFlags() | 128);
                    if (PhoneWindowManager.DEBUG_INPUT) {
                        Slog.d(
                                "WindowManager",
                                "dispatchMediaKeyRepeatWithWakeLock: " + changeTimeRepeat);
                    }
                    if (phoneWindowManager2.mActivityManagerInternal.isSystemReady()) {
                        MediaSessionLegacyHelper.getHelper(phoneWindowManager2.mContext)
                                .sendMediaButtonEvent(changeTimeRepeat, true);
                    }
                    phoneWindowManager2.mBroadcastWakeLock.release();
                    return;
                case 5:
                    if (PhoneWindowManager.DEBUG_WAKEUP) {
                        Slog.w("WindowManager", "Setting mKeyguardDrawComplete");
                    }
                    PhoneWindowManager.m790$$Nest$mfinishKeyguardDrawn(PhoneWindowManager.this);
                    return;
                case 6:
                    Slog.w(
                            "WindowManager",
                            "Keyguard drawn timeout. Setting mKeyguardDrawComplete");
                    PhoneWindowManager.m790$$Nest$mfinishKeyguardDrawn(PhoneWindowManager.this);
                    return;
                case 7:
                    int i2 = message.arg1;
                    if (PhoneWindowManager.DEBUG_WAKEUP) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                i2, "All windows drawn on display ", "WindowManager");
                    }
                    Trace.asyncTraceEnd(32L, "waitForAllWindowsDrawn", i2);
                    PhoneWindowManager phoneWindowManager3 = PhoneWindowManager.this;
                    phoneWindowManager3.getClass();
                    if (i2 != 0 && i2 != -1) {
                        DisplayPowerController.ScreenOnUnblocker screenOnUnblocker =
                                (DisplayPowerController.ScreenOnUnblocker)
                                        phoneWindowManager3.mScreenOnListeners.removeReturnOld(i2);
                        if (screenOnUnblocker != null) {
                            screenOnUnblocker.onScreenOn();
                            return;
                        }
                        return;
                    }
                    DisplayPolicy displayPolicy = phoneWindowManager3.mDefaultDisplayPolicy;
                    synchronized (displayPolicy.mLock) {
                        if (displayPolicy.mScreenOnEarly
                                && !displayPolicy.mWindowManagerDrawComplete) {
                            displayPolicy.mWindowManagerDrawComplete = true;
                            phoneWindowManager3.mDefaultDisplayRotation.updateOrientationListener();
                            DisplayPowerController.ScreenOnUnblocker screenOnUnblocker2 =
                                    phoneWindowManager3.mDefaultDisplayPolicy.mScreenOnListener;
                            DisplayPolicy displayPolicy2 =
                                    phoneWindowManager3.mDefaultDisplayPolicy;
                            synchronized (displayPolicy2.mLock) {
                                try {
                                    if (ProtoLogImpl_54989576.Cache
                                            .WM_FORCE_DEBUG_SCREEN_ON_enabled[0]) {
                                        ProtoLogImpl_54989576.d(
                                                ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON,
                                                3193384380507307212L,
                                                1023,
                                                "finishScreenTurningOn: mAwake=%b,"
                                                    + " mScreenOnEarly=%b, mScreenOnFully=%b,"
                                                    + " mKeyguardDrawComplete=%b,"
                                                    + " mWindowManagerDrawComplete=%b",
                                                Boolean.valueOf(displayPolicy2.mAwake),
                                                Boolean.valueOf(displayPolicy2.mScreenOnEarly),
                                                Boolean.valueOf(displayPolicy2.mScreenOnFully),
                                                Boolean.valueOf(
                                                        displayPolicy2.mKeyguardDrawComplete),
                                                Boolean.valueOf(
                                                        displayPolicy2.mWindowManagerDrawComplete));
                                    }
                                    if (!displayPolicy2.mScreenOnFully
                                            && displayPolicy2.mScreenOnEarly
                                            && displayPolicy2.mWindowManagerDrawComplete
                                            && (!displayPolicy2.mAwake
                                                    || displayPolicy2.mKeyguardDrawComplete)) {
                                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SCREEN_ON_enabled[
                                                2]) {
                                            ProtoLogImpl_54989576.i(
                                                    ProtoLogGroup.WM_DEBUG_SCREEN_ON,
                                                    -6028033043540330282L,
                                                    0,
                                                    null,
                                                    null);
                                        }
                                        displayPolicy2.mScreenOnListener = null;
                                        displayPolicy2.mScreenOnFully = true;
                                        if (CoreRune.FW_TSP_STATE_CONTROLLER
                                                && displayPolicy2
                                                        .mDisplayContent
                                                        .isDefaultDisplay) {
                                            DisplayPolicyExt displayPolicyExt = displayPolicy2.mExt;
                                            WindowState windowState =
                                                    displayPolicyExt
                                                            .mDisplayPolicy
                                                            .mDisplayContent
                                                            .mCurrentFocus;
                                            if (windowState != null) {
                                                WindowManagerService windowManagerService =
                                                        displayPolicyExt.mService;
                                                windowManagerService.mExt.mTspStateController
                                                        .updateWindowPolicy(windowState);
                                                if (CoreRune.FW_TSP_DEADZONE) {
                                                    TspStateController.H h =
                                                            windowManagerService
                                                                    .mExt
                                                                    .mTspStateController
                                                                    .mH;
                                                    if (h.hasMessages(3)) {
                                                        h.removeMessages(3);
                                                    }
                                                    h.sendMessage(Message.obtain(h, 3));
                                                }
                                            }
                                        }
                                        Trace.asyncTraceEnd(32L, "screenTurningOn", 0);
                                        phoneWindowManager3.enableScreen(screenOnUnblocker2, true);
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                    return;
                case 8:
                case 13:
                case 14:
                default:
                    return;
                case 9:
                    PhoneWindowManager.this.showRecentApps(0, false);
                    return;
                case 10:
                    if (!InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                        PhoneWindowManager.this.showGlobalActionsInternal(-1);
                        return;
                    }
                    if (message.arg1 == 1) {
                        PhoneWindowManager phoneWindowManager4 = PhoneWindowManager.this;
                        phoneWindowManager4.mExt.mGlobalActionsByKeyCombnation = true;
                        phoneWindowManager4.performHapticFeedback(
                                0, "Power - Long Press - Show Global Actions", false);
                        i = 1;
                    }
                    PhoneWindowManager.this.showGlobalActionsInternal(i);
                    return;
                case 11:
                    PhoneWindowManager phoneWindowManager5 = PhoneWindowManager.this;
                    synchronized (phoneWindowManager5.mLock) {
                        try {
                            if (phoneWindowManager5.mKeyguardDrawnOnce) {
                                PhoneWindowManagerExt phoneWindowManagerExt =
                                        phoneWindowManager5.mExt;
                                Iterator it = phoneWindowManagerExt.mBootMsgDialogs.iterator();
                                while (it.hasNext()) {
                                    ProgressDialog progressDialog = (ProgressDialog) it.next();
                                    Slog.d(
                                            "PhoneWindowManagerExt",
                                            "dismissBootDialogs: dismissing d"
                                                    + progressDialog.getContext().getDisplayId());
                                    progressDialog.dismiss();
                                }
                                phoneWindowManagerExt.mBootMsgDialogs.clear();
                            } else {
                                phoneWindowManager5.mBootMessageNeedsHiding = true;
                            }
                        } finally {
                        }
                    }
                    return;
                case 12:
                    PhoneWindowManager phoneWindowManager6 = PhoneWindowManager.this;
                    PhoneWindow.sendCloseSystemWindows(phoneWindowManager6.mContext, "assist");
                    if (phoneWindowManager6.keyguardOn()) {
                        DeviceIdleManager deviceIdleManager =
                                (DeviceIdleManager)
                                        phoneWindowManager6.mContext.getSystemService(
                                                DeviceIdleManager.class);
                        if (deviceIdleManager != null) {
                            deviceIdleManager.endIdle("voice-search");
                        }
                        intent = new Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
                        intent.putExtra("android.speech.extras.EXTRA_SECURE", true);
                    } else {
                        intent = new Intent("android.speech.action.WEB_SEARCH");
                    }
                    phoneWindowManager6.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
                    phoneWindowManager6.mBroadcastWakeLock.release();
                    return;
                case 15:
                    StatusBarManagerInternal statusBarManagerInternal =
                            PhoneWindowManager.this.getStatusBarManagerInternal();
                    if (statusBarManagerInternal != null) {
                        StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                                (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                        if (StatusBarManagerService.this.mBar != null) {
                            try {
                                StatusBarManagerService.this.mBar.showPictureInPictureMenu();
                                return;
                            } catch (RemoteException unused) {
                                return;
                            }
                        }
                        return;
                    }
                    return;
                case 16:
                    PhoneWindowManager phoneWindowManager7 = PhoneWindowManager.this;
                    int i3 = message.arg1;
                    DisplayPolicy displayPolicy3 = phoneWindowManager7.mDefaultDisplayPolicy;
                    if (displayPolicy3.mScreenshotHelper != null) {
                        displayPolicy3.mScreenshotHelper.takeScreenshot(
                                new ScreenshotRequest.Builder(1, i3).build(),
                                displayPolicy3.mHandler,
                                (Consumer) null);
                        return;
                    }
                    return;
                case 17:
                    PhoneWindowManager phoneWindowManager8 = PhoneWindowManager.this;
                    phoneWindowManager8.getClass();
                    Slog.d("WindowManager", "Accessibility Shortcut Volup + Voldown is performed");
                    phoneWindowManager8.mAccessibilityShortcutController
                            .performAccessibilityShortcut();
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        phoneWindowManager8.mExt.getClass();
                        PhoneWindowManagerExt.saLogForBasic("HWB1011");
                        return;
                    }
                    return;
                case 18:
                    PhoneWindowManager.this.getClass();
                    try {
                        if (ActivityManager.getService().launchBugReportHandlerApp()) {
                            return;
                        }
                        ActivityManager.getService().requestInteractiveBugReport();
                        return;
                    } catch (RemoteException e) {
                        Slog.e("WindowManager", "Error taking bugreport", e);
                        return;
                    }
                case 19:
                    if (PhoneWindowManager.this.mAccessibilityShortcutController
                            .isAccessibilityShortcutAvailable(false)) {
                        PhoneWindowManager phoneWindowManager9 = PhoneWindowManager.this;
                        phoneWindowManager9.getClass();
                        Slog.d(
                                "WindowManager",
                                "Accessibility Shortcut Volup + Voldown is performed");
                        phoneWindowManager9.mAccessibilityShortcutController
                                .performAccessibilityShortcut();
                        if (InputRune.PWM_KEY_SA_LOGGING) {
                            phoneWindowManager9.mExt.getClass();
                            PhoneWindowManagerExt.saLogForBasic("HWB1011");
                            return;
                        }
                        return;
                    }
                    return;
                case 20:
                    PhoneWindowManager.this.mAutofillManagerInternal.onBackKeyPressed();
                    return;
                case 21:
                    KeyEvent keyEvent3 = (KeyEvent) message.obj;
                    IStatusBarService statusBarService =
                            PhoneWindowManager.this.getStatusBarService();
                    if (statusBarService != null) {
                        try {
                            statusBarService.handleSystemKey(keyEvent3);
                        } catch (RemoteException unused2) {
                        }
                    }
                    keyEvent3.recycle();
                    return;
                case 22:
                    PhoneWindowManager.this.launchAllAppsAction();
                    return;
                case 23:
                    PhoneWindowManager.this.launchAssistAction(
                            message.arg1, 7, ((Long) message.obj).longValue(), null);
                    return;
                case 24:
                    PhoneWindowManager phoneWindowManager10 = PhoneWindowManager.this;
                    if (phoneWindowManager10.mRingerToggleChord == 0) {
                        return;
                    }
                    synchronized (phoneWindowManager10.mServiceAcquireLock) {
                        try {
                            if (phoneWindowManager10.mAudioManagerInternal == null) {
                                phoneWindowManager10.mAudioManagerInternal =
                                        (AudioManagerInternal)
                                                LocalServices.getService(
                                                        AudioManagerInternal.class);
                            }
                            audioManagerInternal = phoneWindowManager10.mAudioManagerInternal;
                        } finally {
                        }
                    }
                    audioManagerInternal.silenceRingerModeInternal("volume_hush");
                    Settings.Secure.putInt(
                            phoneWindowManager10.mContext.getContentResolver(),
                            "hush_gesture_used",
                            1);
                    phoneWindowManager10.mLogger.action(
                            1440, phoneWindowManager10.mRingerToggleChord);
                    return;
                case 25:
                    SwitchKeyboardLayoutMessageObject switchKeyboardLayoutMessageObject =
                            (SwitchKeyboardLayoutMessageObject) message.obj;
                    PhoneWindowManager phoneWindowManager11 = PhoneWindowManager.this;
                    KeyEvent keyEvent4 = switchKeyboardLayoutMessageObject.keyEvent;
                    int i4 = switchKeyboardLayoutMessageObject.direction;
                    IBinder iBinder = switchKeyboardLayoutMessageObject.focusedToken;
                    if (!phoneWindowManager11.isUserSetupComplete()) {
                        Slog.i(
                                "WindowManager",
                                "Ignoring switching keyboard layout - device not setup.");
                        return;
                    }
                    phoneWindowManager11.mWindowManagerInternal.getTargetWindowTokenFromInputToken(
                            iBinder);
                    InputMethodManagerInternal inputMethodManagerInternal =
                            InputMethodManagerInternal.get();
                    keyEvent4.getDisplayId();
                    inputMethodManagerInternal.onSwitchKeyboardLayoutShortcut(i4);
                    return;
                case 26:
                    PhoneWindowManager phoneWindowManager12 = PhoneWindowManager.this;
                    KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent =
                            (KeyboardMetricsCollector.KeyboardLogEvent)
                                    KeyboardMetricsCollector.KeyboardLogEvent.VALUE_TO_ENUM_MAP.get(
                                            message.arg1);
                    KeyEvent keyEvent5 = (KeyEvent) message.obj;
                    KeyboardMetricsCollector.logKeyboardSystemsEventReportedAtom(
                            phoneWindowManager12.mInputManager.getInputDevice(
                                    keyEvent5.getDeviceId()),
                            keyboardLogEvent,
                            keyEvent5.getMetaState(),
                            keyEvent5.getKeyCode());
                    keyEvent5.recycle();
                    return;
                case 27:
                    DeferredKeyActionExecutor.TimedActionsBuffer actionsBufferWithLazyCleanUp =
                            PhoneWindowManager.this.mDeferredKeyActionExecutor
                                    .getActionsBufferWithLazyCleanUp(
                                            message.arg1, ((Long) message.obj).longValue());
                    actionsBufferWithLazyCleanUp.mExecutable = true;
                    if (DeferredKeyActionExecutor.DEBUG
                            && !((ArrayList) actionsBufferWithLazyCleanUp.mActions).isEmpty()) {
                        Log.i(
                                "DeferredKeyAction",
                                "setExecutable: execute actions for key "
                                        + KeyEvent.keyCodeToString(
                                                actionsBufferWithLazyCleanUp.mKeyCode));
                    }
                    Iterator it2 = ((ArrayList) actionsBufferWithLazyCleanUp.mActions).iterator();
                    while (it2.hasNext()) {
                        ((Runnable) it2.next()).run();
                    }
                    ((ArrayList) actionsBufferWithLazyCleanUp.mActions).clear();
                    return;
            }
        }
    }

    public final class PowerKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PhoneWindowManager this$0;

        public PowerKeyRule(PhoneWindowManager phoneWindowManager, int i) {
            super(26);
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = phoneWindowManager;
                    super(264);
                    break;
                default:
                    this.this$0 = phoneWindowManager;
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public long getLongPressTimeoutMs() {
            switch (this.$r8$classId) {
                case 0:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    return phoneWindowManager.getResolvedLongPressOnPowerBehavior() == 5
                            ? phoneWindowManager.mLongPressOnPowerAssistantTimeoutMs
                            : SingleKeyGestureDetector.sDefaultLongPressTimeout;
                default:
                    return super.getLongPressTimeoutMs();
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final int getMaxMultiPressCount() {
            switch (this.$r8$classId) {
                case 0:
                    return this.this$0.getMaxMultiPressPowerCount();
                default:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (phoneWindowManager.mTriplePressOnStemPrimaryBehavior == 1
                            && phoneWindowManager.mTalkbackShortcutController
                                    .isTalkBackShortcutGestureEnabled()) {
                        return 3;
                    }
                    return phoneWindowManager.mDoublePressOnStemPrimaryBehavior != 0 ? 2 : 1;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final void onKeyUp(int i, int i2, long j) {
            switch (this.$r8$classId) {
                case 0:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (phoneWindowManager.mShouldEarlyShortPressOnPower && i == 1) {
                        phoneWindowManager.powerPress(j, 1, i2, null);
                        break;
                    }
                    break;
                default:
                    if (i == 1) {
                        PhoneWindowManager phoneWindowManager2 = this.this$0;
                        ActivityTaskManagerService activityTaskManagerService =
                                ActivityTaskManagerService.this;
                        ArrayList arrayList =
                                (ArrayList)
                                        activityTaskManagerService.getTasks(1, false, false, -1);
                        if (arrayList.size() > 0) {
                            ActivityManager.RunningTaskInfo runningTaskInfo =
                                    (ActivityManager.RunningTaskInfo) arrayList.get(0);
                            for (ActivityManager.RecentTaskInfo recentTaskInfo :
                                    activityTaskManagerService
                                            .getRecentTasks(
                                                    2,
                                                    2,
                                                    activityTaskManagerService.mContext.getUserId())
                                            .getList()) {
                                if (recentTaskInfo.id != runningTaskInfo.id) {
                                    phoneWindowManager2
                                                    .mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp =
                                            recentTaskInfo;
                                    phoneWindowManager2.mFocusedTaskInfoOnStemPrimarySingleKeyUp =
                                            null;
                                    if (!phoneWindowManager2.mShouldEarlyShortPressOnStemPrimary
                                            && phoneWindowManager2.mShortPressOnStemPrimaryBehavior
                                                    == 1) {
                                        phoneWindowManager2.mDeferredKeyActionExecutor
                                                .queueKeyAction(
                                                        new PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda0(
                                                                this, 0),
                                                        j);
                                        break;
                                    }
                                }
                            }
                        } else {
                            Slog.i("ActivityTaskManager", "No running task found!");
                        }
                        recentTaskInfo = null;
                        phoneWindowManager2.mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp =
                                recentTaskInfo;
                        phoneWindowManager2.mFocusedTaskInfoOnStemPrimarySingleKeyUp = null;
                        if (!phoneWindowManager2.mShouldEarlyShortPressOnStemPrimary) {}
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final void onLongPress(final long j, KeyEvent keyEvent, int i) {
            switch (this.$r8$classId) {
                case 0:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (phoneWindowManager.mSingleKeyGestureDetector.mBeganFromNonInteractive
                            && !phoneWindowManager.mSupportLongPressPowerWhenNonInteractive) {
                        Slog.v(
                                "WindowManager",
                                "Not support long press power when device is not interactive.");
                        break;
                    } else {
                        this.mIsKeyLongPressed = true;
                        int resolvedLongPressOnPowerBehavior =
                                phoneWindowManager.getResolvedLongPressOnPowerBehavior();
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        "powerLongPress: eventTime=",
                                        j,
                                        " mLongPressOnPowerBehavior=");
                        m.append(phoneWindowManager.mLongPressOnPowerBehavior);
                        m.append(" behavior=");
                        m.append(resolvedLongPressOnPowerBehavior);
                        Slog.d("WindowManager", m.toString());
                        boolean z = InputRune.PWM_SIDE_KEY_LONG_PRESS;
                        if (z) {
                            PhoneWindowManagerExt phoneWindowManagerExt = phoneWindowManager.mExt;
                            phoneWindowManagerExt.getClass();
                            boolean z2 = InputRune.PWM_SIDE_KEY_WAKE_UP_BIXBY;
                            PhoneWindowManager phoneWindowManager2 = phoneWindowManagerExt.mPolicy;
                            if (z2 && !phoneWindowManager2.isUserSetupComplete()) {
                                phoneWindowManagerExt.mContext.startActivityAsUser(
                                        new Intent(
                                                "com.sec.android.app.secsetupwizard.POWER_OFF_GUIDE"),
                                        UserHandle.CURRENT);
                                Slog.d("PhoneWindowManagerExt", "startActivity, power off guide");
                            } else if (phoneWindowManagerExt.mSystemKeyPolicy.hasSystemKeyInfo(
                                    26, 4)) {
                                Log.i(
                                        "PhoneWindowManagerExt",
                                        "skip long press power, requestedSystemKey");
                            } else {
                                boolean z3 = (i & 536870912) != 0;
                                if (resolvedLongPressOnPowerBehavior != 1) {
                                    switch (resolvedLongPressOnPowerBehavior) {
                                        case 101:
                                            if (!z2) {
                                                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                                            } else if (InputRune
                                                            .PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD
                                                    && phoneWindowManagerExt
                                                            .isSamsungKeyboardShown()) {
                                                phoneWindowManagerExt.mIsCallOpenDictation = true;
                                                phoneWindowManagerExt.callDictation(
                                                        26, "open_dictation");
                                            } else {
                                                BixbyService.Params params =
                                                        new BixbyService.Params(keyEvent, z3);
                                                params.longPress = true;
                                                params.showToast = true;
                                                phoneWindowManagerExt.mBixbyService.startService(
                                                        new BixbyService.Params(params));
                                                Log.d("PhoneWindowManagerExt", "run wake Bixby");
                                                if (InputRune.PWM_KEY_SA_LOGGING) {
                                                    PhoneWindowManagerExt
                                                            .sendCoreSaLoggingDimension(
                                                                    "HWB1006", "Wake Bixby");
                                                }
                                            }
                                            Slog.d(
                                                    "PhoneWindowManagerExt",
                                                    "consume powerLongPress");
                                            break;
                                        case 102:
                                            KeyCustomizationManager keyCustomizationManager =
                                                    phoneWindowManagerExt.mKeyCustomizationPolicy;
                                            SemWindowManager.KeyCustomizationInfo last =
                                                    keyCustomizationManager
                                                            .mKeyCustomizationInfoManager.getLast(
                                                            4, 26);
                                            if (last != null) {
                                                keyCustomizationManager.launchLongPressAction(
                                                        last.action, keyEvent, 26);
                                                Slog.d(
                                                        "PhoneWindowManagerExt",
                                                        "consume powerLongPress");
                                                break;
                                            } else {
                                                Log.d(
                                                        "PhoneWindowManagerExt",
                                                        "powerLongPress, info is null");
                                                break;
                                            }
                                        case 103:
                                            if (InputRune.PWM_SIDE_KEY_DIGITAL_ASSISTANT) {
                                                if (InputRune.PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD
                                                        && phoneWindowManagerExt
                                                                .isSamsungKeyboardShown()) {
                                                    phoneWindowManagerExt.mIsCallOpenDictation =
                                                            true;
                                                    phoneWindowManagerExt.callDictation(
                                                            26, "open_dictation");
                                                } else {
                                                    if (phoneWindowManagerExt
                                                            .isGoogleQuickSearchBoxAsDigitalAssistant()) {
                                                        StatusBarManagerInternal
                                                                statusBarManagerInternal =
                                                                        phoneWindowManager2
                                                                                .getStatusBarManagerInternal();
                                                        if (statusBarManagerInternal != null) {
                                                            Bundle m2 =
                                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    5,
                                                                                    "invocation_type");
                                                            IStatusBar iStatusBar =
                                                                    StatusBarManagerService.this
                                                                            .mBar;
                                                            if (iStatusBar != null) {
                                                                try {
                                                                    iStatusBar.startAssist(m2);
                                                                } catch (RemoteException unused) {
                                                                }
                                                            }
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "run digital assistant.");
                                                        } else {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "statusBar is null");
                                                        }
                                                    } else {
                                                        phoneWindowManagerExt
                                                                .setGoogleQuickSearchBoxAsDigitalAssistant();
                                                        Log.d(
                                                                "PhoneWindowManagerExt",
                                                                "setGoogleQuickSearchBoxAsDigitalAssistant");
                                                    }
                                                    if (InputRune.PWM_KEY_SA_LOGGING) {
                                                        PhoneWindowManagerExt
                                                                .sendCoreSaLoggingDimension(
                                                                        "HWB1006",
                                                                        "Digital Assistant");
                                                    }
                                                }
                                            }
                                            Slog.d(
                                                    "PhoneWindowManagerExt",
                                                    "consume powerLongPress");
                                            break;
                                        default:
                                            Slog.e(
                                                    "PhoneWindowManagerExt",
                                                    "Invalid side key long press info");
                                            break;
                                    }
                                } else {
                                    if (z3) {
                                        Slog.d(
                                                "PhoneWindowManagerExt",
                                                "Side key long press global action");
                                    }
                                    Slog.d("PhoneWindowManagerExt", "consume powerLongPress");
                                }
                            }
                            phoneWindowManager.mPowerKeyHandled = true;
                            break;
                        }
                        if (resolvedLongPressOnPowerBehavior == 1) {
                            phoneWindowManager.mPowerKeyHandled = true;
                            phoneWindowManager.performHapticFeedback(
                                    10003, "Power - Long Press - Global Actions", false);
                            if (z) {
                                phoneWindowManager.showGlobalActionsInternal(0);
                            } else {
                                phoneWindowManager.showGlobalActions();
                            }
                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                phoneWindowManager.mExt.getClass();
                                PhoneWindowManagerExt.sendCoreSaLoggingDimension(
                                        "HWB1006", "Power off menu");
                                break;
                            }
                        } else if (resolvedLongPressOnPowerBehavior != 2
                                && resolvedLongPressOnPowerBehavior != 3) {
                            if (resolvedLongPressOnPowerBehavior == 4) {
                                phoneWindowManager.mPowerKeyHandled = true;
                                phoneWindowManager.performHapticFeedback(
                                        10003, "Power - Long Press - Go To Voice Assist", false);
                                boolean z4 =
                                        phoneWindowManager
                                                .mAllowStartActivityForLongPressOnPowerDuringSetup;
                                KeyguardServiceDelegate keyguardServiceDelegate =
                                        phoneWindowManager.mKeyguardDelegate;
                                if (keyguardServiceDelegate != null
                                        && keyguardServiceDelegate.isShowing()) {
                                    KeyguardServiceDelegate keyguardServiceDelegate2 =
                                            phoneWindowManager.mKeyguardDelegate;
                                    Intent intent =
                                            new Intent("android.intent.action.VOICE_ASSIST");
                                    KeyguardServiceWrapper keyguardServiceWrapper =
                                            keyguardServiceDelegate2.mKeyguardService;
                                    if (keyguardServiceWrapper != null) {
                                        keyguardServiceWrapper.dismissKeyguardToLaunch(intent);
                                        break;
                                    }
                                } else {
                                    phoneWindowManager.startActivityAsUser(
                                            new Intent("android.intent.action.VOICE_ASSIST"),
                                            null,
                                            UserHandle.CURRENT_OR_SELF,
                                            z4);
                                    break;
                                }
                            } else if (resolvedLongPressOnPowerBehavior == 5) {
                                phoneWindowManager.mPowerKeyHandled = true;
                                phoneWindowManager.performHapticFeedback(
                                        10002, "Power - Long Press - Go To Assistant", false);
                                phoneWindowManager.launchAssistAction(-2, 6, j, null);
                                break;
                            }
                        } else if (!InputRune.PWM_KEY_FACTORY_MODE_POLICY
                                || !phoneWindowManager.mAcquireInProgress) {
                            phoneWindowManager.mPowerKeyHandled = true;
                            if (!ActivityManager.isUserAMonkey()) {
                                phoneWindowManager.performHapticFeedback(
                                        10003, "Power - Long Press - Shut Off", false);
                                PhoneWindow.sendCloseSystemWindows(
                                        phoneWindowManager.mContext, "globalactions");
                                phoneWindowManager.mWindowManagerFuncs.shutdown(
                                        resolvedLongPressOnPowerBehavior == 2);
                                break;
                            }
                        } else {
                            Slog.d(
                                    "WindowManager",
                                    "Do not shutdown. Maybe power wake lock is delayed.");
                            break;
                        }
                    }
                    break;
                default:
                    this.this$0.mDeferredKeyActionExecutor.queueKeyAction(
                            new Runnable() { // from class:
                                             // com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PhoneWindowManager.PowerKeyRule powerKeyRule =
                                            PhoneWindowManager.PowerKeyRule.this;
                                    long j2 = j;
                                    StringBuilder sb = new StringBuilder("stemPrimaryLongPress: ");
                                    PhoneWindowManager phoneWindowManager3 = powerKeyRule.this$0;
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            sb,
                                            phoneWindowManager3.mLongPressOnStemPrimaryBehavior,
                                            "WindowManager");
                                    if (phoneWindowManager3.mLongPressOnStemPrimaryBehavior != 1) {
                                        return;
                                    }
                                    phoneWindowManager3.launchAssistAction(-2, 0, j2, null);
                                }
                            },
                            j);
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final void onMultiPress(long j, final int i, int i2, KeyEvent keyEvent) {
            ActivityTaskManager.RootTaskInfo rootTaskInfo;
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.powerPress(j, i, i2, keyEvent);
                    break;
                default:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (i != 3 || phoneWindowManager.mTriplePressOnStemPrimaryBehavior != 1) {
                        phoneWindowManager.mDeferredKeyActionExecutor.queueKeyAction(
                                new Runnable() { // from class:
                                                 // com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        PhoneWindowManager.PowerKeyRule powerKeyRule =
                                                PhoneWindowManager.PowerKeyRule.this;
                                        PhoneWindowManager.m791$$Nest$mstemPrimaryPress(
                                                powerKeyRule.this$0, i);
                                    }
                                },
                                j);
                        break;
                    } else {
                        DeferredKeyActionExecutor.TimedActionsBuffer timedActionsBuffer =
                                (DeferredKeyActionExecutor.TimedActionsBuffer)
                                        phoneWindowManager.mDeferredKeyActionExecutor.mBuffers.get(
                                                264);
                        if (timedActionsBuffer != null) {
                            ((ArrayList) timedActionsBuffer.mActions).clear();
                        }
                        if (phoneWindowManager.mShouldEarlyShortPressOnStemPrimary
                                && phoneWindowManager.mShortPressOnStemPrimaryBehavior == 1
                                && (rootTaskInfo =
                                                phoneWindowManager
                                                        .mFocusedTaskInfoOnStemPrimarySingleKeyUp)
                                        != null) {
                            try {
                                phoneWindowManager.mActivityManagerService.startActivityFromRecents(
                                        rootTaskInfo.taskId, (Bundle) null);
                            } catch (RemoteException | IllegalArgumentException e) {
                                Slog.e(
                                        "WindowManager",
                                        "Failed to start task "
                                                + phoneWindowManager
                                                        .mFocusedTaskInfoOnStemPrimarySingleKeyUp
                                                        .taskId
                                                + " from recents",
                                        e);
                            }
                        }
                        PhoneWindowManager.m791$$Nest$mstemPrimaryPress(phoneWindowManager, i);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final void onPress(long j, KeyEvent keyEvent, int i) {
            switch (this.$r8$classId) {
                case 0:
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (!phoneWindowManager.mShouldEarlyShortPressOnPower) {
                        phoneWindowManager.powerPress(j, 1, i, keyEvent);
                        break;
                    }
                    break;
                default:
                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                    if (!phoneWindowManager2.mShouldEarlyShortPressOnStemPrimary
                            || phoneWindowManager2.mShortPressOnStemPrimaryBehavior != 1) {
                        phoneWindowManager2.mDeferredKeyActionExecutor.queueKeyAction(
                                new PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda0(
                                        this, 1),
                                j);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onVeryLongPress() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mActivityManagerInternal.prepareForPossibleShutdown();
                    PhoneWindowManager phoneWindowManager = this.this$0;
                    if (phoneWindowManager.mVeryLongPressOnPowerBehavior == 1) {
                        phoneWindowManager.mPowerKeyHandled = true;
                        phoneWindowManager.performHapticFeedback(
                                10003, "Power - Very Long Press - Show Global Actions", false);
                        phoneWindowManager.showGlobalActions();
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final boolean supportLongPress() {
            switch (this.$r8$classId) {
                case 0:
                    if (this.this$0.getResolvedLongPressOnPowerBehavior() != 0) {}
                    break;
                default:
                    if (this.this$0.mLongPressOnStemPrimaryBehavior != 0) {}
                    break;
            }
            return false;
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public boolean supportVeryLongPress() {
            switch (this.$r8$classId) {
                case 0:
                    return this.this$0.mVeryLongPressOnPowerBehavior != 0;
                default:
                    return super.supportVeryLongPress();
            }
        }
    }

    public final class ScreenLockTimeout implements Runnable {
        public Bundle options;

        public ScreenLockTimeout() {}

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (this) {
                try {
                    KeyguardServiceDelegate keyguardServiceDelegate =
                            PhoneWindowManager.this.mKeyguardDelegate;
                    if (keyguardServiceDelegate != null) {
                        Bundle bundle = this.options;
                        KeyguardServiceWrapper keyguardServiceWrapper =
                                keyguardServiceDelegate.mKeyguardService;
                        if (keyguardServiceWrapper != null) {
                            keyguardServiceWrapper.doKeyguardTimeout(bundle);
                        }
                        if (keyguardServiceDelegate.mPersonaManagerInternal == null) {
                            keyguardServiceDelegate.mPersonaManagerInternal =
                                    (PersonaManagerInternal)
                                            LocalServices.getService(PersonaManagerInternal.class);
                        }
                        PersonaManagerInternal personaManagerInternal =
                                keyguardServiceDelegate.mPersonaManagerInternal;
                        if (personaManagerInternal != null) {
                            if (personaManagerInternal == null) {
                                keyguardServiceDelegate.mPersonaManagerInternal =
                                        (PersonaManagerInternal)
                                                LocalServices.getService(
                                                        PersonaManagerInternal.class);
                            }
                            keyguardServiceDelegate.mPersonaManagerInternal.doKeyguardTimeout();
                        }
                    }
                    PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                    phoneWindowManager.mLockScreenTimerActive = false;
                    phoneWindowManager.mLockNowPending = false;
                    this.options = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(PolicyHandler policyHandler) {
            super(policyHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            PhoneWindowManager.this.updateSettings(null);
        }
    }

    public final class SwitchKeyboardLayoutMessageObject extends Record {
        public final int direction;
        public final IBinder focusedToken;
        public final KeyEvent keyEvent;

        public SwitchKeyboardLayoutMessageObject(IBinder iBinder, KeyEvent keyEvent, int i) {
            this.keyEvent = keyEvent;
            this.focusedToken = iBinder;
            this.direction = i;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE,
                                            SwitchKeyboardLayoutMessageObject.class,
                                            Object.class),
                                    SwitchKeyboardLayoutMessageObject.class,
                                    "keyEvent;focusedToken;direction",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I")
                            .dynamicInvoker()
                            .invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(
                                            Integer.TYPE, SwitchKeyboardLayoutMessageObject.class),
                                    SwitchKeyboardLayoutMessageObject.class,
                                    "keyEvent;focusedToken;direction",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(
                                            String.class, SwitchKeyboardLayoutMessageObject.class),
                                    SwitchKeyboardLayoutMessageObject.class,
                                    "keyEvent;focusedToken;direction",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;",
                                    "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }
    }

    /* renamed from: -$$Nest$mfinishKeyguardDrawn, reason: not valid java name */
    public static void m790$$Nest$mfinishKeyguardDrawn(PhoneWindowManager phoneWindowManager) {
        DisplayPolicy displayPolicy = phoneWindowManager.mDefaultDisplayPolicy;
        synchronized (displayPolicy.mLock) {
            if (displayPolicy.mScreenOnEarly && !displayPolicy.mKeyguardDrawComplete) {
                displayPolicy.mKeyguardDrawComplete = true;
                displayPolicy.mWindowManagerDrawComplete = false;
                synchronized (phoneWindowManager.mLock) {
                    try {
                        if (phoneWindowManager.mKeyguardDelegate != null) {
                            phoneWindowManager.mHandler.removeMessages(6);
                        }
                    } finally {
                    }
                }
                Trace.asyncTraceBegin(32L, "waitForAllWindowsDrawn", -1);
                phoneWindowManager.mWindowManagerInternal.waitForAllWindowsDrawn(
                        phoneWindowManager.mHandler.obtainMessage(7, -1, 0), 1000L, -1);
            }
        }
    }

    /* renamed from: -$$Nest$mstemPrimaryPress, reason: not valid java name */
    public static void m791$$Nest$mstemPrimaryPress(PhoneWindowManager phoneWindowManager, int i) {
        phoneWindowManager.getClass();
        Slog.d("WindowManager", "stemPrimaryPress: " + i);
        ComponentName componentName = null;
        if (i == 3) {
            int i2 = phoneWindowManager.mTriplePressOnStemPrimaryBehavior;
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i2, "stemPrimaryTriplePressAction: ", "WindowManager");
            if (i2 != 1) {
                return;
            }
            TalkbackShortcutController talkbackShortcutController =
                    phoneWindowManager.mTalkbackShortcutController;
            Set enabledServicesFromSettings =
                    AccessibilityUtils.getEnabledServicesFromSettings(
                            talkbackShortcutController.mContext, phoneWindowManager.mCurrentUserId);
            Iterator<AccessibilityServiceInfo> it =
                    ((AccessibilityManager)
                                    talkbackShortcutController.mContext.getSystemService(
                                            AccessibilityManager.class))
                            .getInstalledAccessibilityServiceList()
                            .iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
                if ("TalkBack"
                                .equals(
                                        serviceInfo
                                                .loadLabel(
                                                        talkbackShortcutController.mPackageManager)
                                                .toString())
                        && (serviceInfo.applicationInfo.isSystemApp()
                                || serviceInfo.applicationInfo.isUpdatedSystemApp())) {
                    componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                    break;
                }
            }
            if (componentName != null) {
                boolean contains = enabledServicesFromSettings.contains(componentName);
                if (talkbackShortcutController.isTalkBackShortcutGestureEnabled()) {
                    boolean z = !contains;
                    AccessibilityUtils.setAccessibilityServiceState(
                            talkbackShortcutController.mContext, componentName, z);
                    if (z) {
                        if (AccessibilityUtils.isUserSetupCompleted(
                                talkbackShortcutController.mContext)) {
                            AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(
                                    talkbackShortcutController.mContext, componentName, 7, true);
                        } else {
                            Settings.Secure.putInt(
                                    talkbackShortcutController.mContext.getContentResolver(),
                                    "wear_accessibility_gesture_enabled_during_oobe",
                                    1);
                        }
                    }
                }
            }
            if (phoneWindowManager.mTalkbackShortcutController.isTalkBackShortcutGestureEnabled()) {
                phoneWindowManager.performHapticFeedback(
                        16, "Stem primary - Triple Press - Toggle Accessibility", false);
                return;
            }
            return;
        }
        if (i == 2) {
            int i3 = phoneWindowManager.mDoublePressOnStemPrimaryBehavior;
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i3, "stemPrimaryDoublePressAction: ", "WindowManager");
            if (i3 != 1) {
                return;
            }
            KeyguardServiceDelegate keyguardServiceDelegate = phoneWindowManager.mKeyguardDelegate;
            if (keyguardServiceDelegate != null ? keyguardServiceDelegate.isShowing() : false) {
                return;
            }
            ActivityManager.RecentTaskInfo recentTaskInfo =
                    phoneWindowManager.mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp;
            if (recentTaskInfo == null) {
                if (DEBUG_INPUT) {
                    Slog.w("WindowManager", "No recent task available! Show wallpaper.");
                }
                phoneWindowManager.goHome();
                return;
            }
            if (DEBUG_INPUT) {
                Slog.d(
                        "WindowManager",
                        "Starting task from recents. id="
                                + recentTaskInfo.id
                                + ", persistentId="
                                + recentTaskInfo.persistentId
                                + ", topActivity="
                                + recentTaskInfo.topActivity
                                + ", baseIntent="
                                + recentTaskInfo.baseIntent);
            }
            try {
                phoneWindowManager.mActivityManagerService.startActivityFromRecents(
                        recentTaskInfo.persistentId, (Bundle) null);
                return;
            } catch (RemoteException | IllegalArgumentException e) {
                Slog.e(
                        "WindowManager",
                        "Failed to start task " + recentTaskInfo.persistentId + " from recents",
                        e);
                return;
            }
        }
        if (i == 1) {
            int i4 = phoneWindowManager.mShortPressOnStemPrimaryBehavior;
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i4, "stemPrimarySinglePressAction: behavior=", "WindowManager");
            if (i4 == 0) {
                return;
            }
            KeyguardServiceDelegate keyguardServiceDelegate2 = phoneWindowManager.mKeyguardDelegate;
            if (keyguardServiceDelegate2 != null && keyguardServiceDelegate2.isShowing()) {
                KeyguardServiceWrapper keyguardServiceWrapper =
                        phoneWindowManager.mKeyguardDelegate.mKeyguardService;
                if (keyguardServiceWrapper != null) {
                    keyguardServiceWrapper.onSystemKeyPressed(264);
                }
                Slog.d("WindowManager", "stemPrimarySinglePressAction: skip due to keyguard");
                return;
            }
            if (i4 == 1) {
                phoneWindowManager.startActivityAsUser(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                270532608, "android.intent.action.ALL_APPS"),
                        UserHandle.CURRENT_OR_SELF);
                return;
            }
            if (i4 != 2) {
                return;
            }
            if (phoneWindowManager.mPrimaryShortPressTargetActivity == null) {
                Slog.wtf(
                        "WindowManager",
                        "mPrimaryShortPressTargetActivity must not be null and correctly"
                            + " specified");
                return;
            }
            Intent intent = new Intent();
            intent.setComponent(phoneWindowManager.mPrimaryShortPressTargetActivity);
            if (phoneWindowManager.mContext.getPackageManager().resolveActivity(intent, 0)
                    != null) {
                intent.addFlags(270548992);
                phoneWindowManager.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
            } else {
                Slog.wtf(
                        "WindowManager",
                        "Could not resolve activity with : "
                                + phoneWindowManager.mPrimaryShortPressTargetActivity
                                        .flattenToString()
                                + " name.");
            }
        }
    }

    public PhoneWindowManager() {
        new LogDecelerateInterpolator(100, 0);
        this.mDeferredKeyActionExecutor = new DeferredKeyActionExecutor();
        this.mTopFocusedDisplayId = -1;
        this.mPowerButtonSuppressionDelayMillis = 800;
        this.mLockNowPending = false;
        this.mKeyguardDrawnTimeout = 1000;
        this.mSystemKeyRequested = false;
        this.mSleepTokenLock = new Object();
        this.mHDMIObserver =
                new UEventObserver() { // from class: com.android.server.policy.PhoneWindowManager.2
                    public final void onUEvent(UEventObserver.UEvent uEvent) {
                        PhoneWindowManager.this.mDefaultDisplayPolicy.setHdmiPlugged(
                                "1".equals(uEvent.get("SWITCH_STATE")), false);
                    }
                };
        this.mPersistentVrModeListener =
                new IPersistentVrStateCallbacks
                        .Stub() { // from class: com.android.server.policy.PhoneWindowManager.3
                    public final void onPersistentVrStateChanged(boolean z) {
                        PhoneWindowManager.this.mDefaultDisplayPolicy.mPersistentVrModeEnabled = z;
                    }
                };
        this.mAcquireInProgress = false;
        this.mEndCallLongPress = new AnonymousClass4(0, this);
        this.mDisplayHomeButtonHandlers = new SparseArray();
        this.mLastModifierState = 0;
        final int i = 0;
        this.mDockReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.policy.PhoneWindowManager.13
                    public final /* synthetic */ PhoneWindowManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                                    this.this$0.mDefaultDisplayPolicy.mDockMode =
                                            intent.getIntExtra(
                                                    "android.intent.extra.DOCK_STATE", 0);
                                } else {
                                    try {
                                        IUiModeManager asInterface =
                                                IUiModeManager.Stub.asInterface(
                                                        ServiceManager.getService("uimode"));
                                        this.this$0.mUiMode = asInterface.getCurrentModeType();
                                    } catch (RemoteException unused) {
                                    }
                                }
                                this.this$0.mWindowManagerFuncs.updateRotation(true, false);
                                this.this$0.mDefaultDisplayRotation.updateOrientationListener();
                                return;
                            default:
                                if ("android.intent.action.USER_SWITCHED"
                                        .equals(intent.getAction())) {
                                    this.this$0.mSettingsObserver.onChange(false);
                                    this.this$0.mExt.updateSettings();
                                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0.mExt;
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    WmScreenshotController wmScreenshotController =
                                            phoneWindowManagerExt
                                                    .mWindowManagerFuncs
                                                    .mScreenshotController;
                                    synchronized (wmScreenshotController.mScreenshotLock) {
                                        try {
                                            if (!wmScreenshotController.mScreenshotConnections
                                                    .isEmpty()) {
                                                Iterator it =
                                                        wmScreenshotController
                                                                .mScreenshotConnections
                                                                .keySet()
                                                                .iterator();
                                                while (it.hasNext()) {
                                                    wmScreenshotController.resetConnection(
                                                            (ServiceConnection) it.next(), false);
                                                }
                                                wmScreenshotController.mScreenshotConnections
                                                        .clear();
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    phoneWindowManagerExt.mHandler.post(
                                            new PhoneWindowManagerExt$$ExternalSyntheticLambda0(
                                                    phoneWindowManagerExt, intExtra, 1));
                                    DisplayRotation.SettingsObserver settingsObserver =
                                            this.this$0.mDefaultDisplayRotation.mSettingsObserver;
                                    if (settingsObserver != null) {
                                        settingsObserver.onChange(false);
                                    }
                                    this.this$0.mWindowManagerFuncs.onUserSwitched();
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mMultiuserReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.policy.PhoneWindowManager.13
                    public final /* synthetic */ PhoneWindowManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                                    this.this$0.mDefaultDisplayPolicy.mDockMode =
                                            intent.getIntExtra(
                                                    "android.intent.extra.DOCK_STATE", 0);
                                } else {
                                    try {
                                        IUiModeManager asInterface =
                                                IUiModeManager.Stub.asInterface(
                                                        ServiceManager.getService("uimode"));
                                        this.this$0.mUiMode = asInterface.getCurrentModeType();
                                    } catch (RemoteException unused) {
                                    }
                                }
                                this.this$0.mWindowManagerFuncs.updateRotation(true, false);
                                this.this$0.mDefaultDisplayRotation.updateOrientationListener();
                                return;
                            default:
                                if ("android.intent.action.USER_SWITCHED"
                                        .equals(intent.getAction())) {
                                    this.this$0.mSettingsObserver.onChange(false);
                                    this.this$0.mExt.updateSettings();
                                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0.mExt;
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    WmScreenshotController wmScreenshotController =
                                            phoneWindowManagerExt
                                                    .mWindowManagerFuncs
                                                    .mScreenshotController;
                                    synchronized (wmScreenshotController.mScreenshotLock) {
                                        try {
                                            if (!wmScreenshotController.mScreenshotConnections
                                                    .isEmpty()) {
                                                Iterator it =
                                                        wmScreenshotController
                                                                .mScreenshotConnections
                                                                .keySet()
                                                                .iterator();
                                                while (it.hasNext()) {
                                                    wmScreenshotController.resetConnection(
                                                            (ServiceConnection) it.next(), false);
                                                }
                                                wmScreenshotController.mScreenshotConnections
                                                        .clear();
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                    phoneWindowManagerExt.mHandler.post(
                                            new PhoneWindowManagerExt$$ExternalSyntheticLambda0(
                                                    phoneWindowManagerExt, intExtra, 1));
                                    DisplayRotation.SettingsObserver settingsObserver =
                                            this.this$0.mDefaultDisplayRotation.mSettingsObserver;
                                    if (settingsObserver != null) {
                                        settingsObserver.onChange(false);
                                    }
                                    this.this$0.mWindowManagerFuncs.onUserSwitched();
                                    return;
                                }
                                return;
                        }
                    }
                };
        this.mScreenLockTimeout = new ScreenLockTimeout();
    }

    public static String multiPressOnPowerBehaviorToString(int i) {
        if (i == 0) {
            return "MULTI_PRESS_POWER_NOTHING";
        }
        if (i == 1) {
            return "MULTI_PRESS_POWER_THEATER_MODE";
        }
        if (i == 2) {
            return "MULTI_PRESS_POWER_BRIGHTNESS_BOOST";
        }
        if (i == 3) {
            return "MULTI_PRESS_POWER_LAUNCH_TARGET_ACTIVITY";
        }
        switch (i) {
            case 101:
                return "MULTI_PRESS_POWER_QUICK_LAUNCH_CAMERA";
            case 102:
                return "MULTI_PRESS_POWER_PANIC_CALL";
            case 103:
                return "MULTI_PRESS_POWER_EMERGENCY_SOS";
            case 104:
                return "MULTI_PRESS_POWER_SIDE_KEY_BEHAVIOR";
            default:
                return Integer.toString(i);
        }
    }

    public final int applyKeyguardOcclusionChange() {
        boolean z = DEBUG_KEYGUARD;
        if (z) {
            StringBuilder sb = new StringBuilder("transition/occluded commit occluded=");
            sb.append(this.mPendingKeyguardOccluded);
            sb.append(" changed=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "WindowManager", sb, this.mKeyguardOccludedChanged);
        }
        boolean z2 = this.mPendingKeyguardOccluded;
        if (z) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "setKeyguardOccluded occluded=", "WindowManager", z2);
        }
        this.mKeyguardOccludedChanged = false;
        this.mPendingKeyguardOccluded = z2;
        this.mKeyguardDelegate.setOccluded(z2);
        return this.mKeyguardDelegate.isShowing() ? 5 : 0;
    }

    public final void applyLidSwitchState() {
        if (this.mDefaultDisplayPolicy.mLidState == 0) {
            int i = Settings.Global.getInt(this.mContext.getContentResolver(), "lid_behavior", 0);
            if (i == 1) {
                this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 3, 1);
            } else if (i == 2) {
                this.mWindowManagerFuncs.lockDeviceNow();
            }
        }
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
        }
    }

    public final boolean backKeyPress() {
        TelecomManager telecommService;
        this.mLogger.count("key_back_press", 1);
        boolean z = this.mBackKeyHandled;
        if (this.mHasFeatureWatch && (telecommService = getTelecommService()) != null) {
            if (telecommService.isRinging()) {
                telecommService.silenceRinger();
                return false;
            }
            if ((1 & this.mIncallBackBehavior) != 0 && telecommService.isInCall()) {
                return telecommService.endCall();
            }
        }
        if (this.mAutofillManagerInternal != null) {
            PolicyHandler policyHandler = this.mHandler;
            policyHandler.sendMessage(policyHandler.obtainMessage(20));
        }
        return z;
    }

    public final void bindKeyguard() {
        synchronized (this.mLock) {
            try {
                if (this.mKeyguardBound) {
                    return;
                }
                this.mKeyguardBound = true;
                this.mKeyguardDelegate.bindService(this.mContext);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int checkAddPermission(int r16, boolean r17, java.lang.String r18, int[] r19) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.checkAddPermission(int,"
                    + " boolean, java.lang.String, int[]):int");
    }

    public final Intent createHomeDockIntent() {
        Intent intent;
        Bundle bundle;
        int i = this.mUiMode;
        if (i == 3) {
            if (this.mEnableCarDockHomeCapture) {
                intent = this.mCarDockIntent;
            }
            intent = null;
        } else {
            if (i != 2) {
                if (i == 6) {
                    int i2 = this.mDefaultDisplayPolicy.mDockMode;
                    if (i2 == 1 || i2 == 4 || i2 == 3) {
                        intent = this.mDeskDockIntent;
                    }
                } else if (i == 7) {
                    intent = this.mVrHeadsetHomeIntent;
                }
            }
            intent = null;
        }
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveActivityAsUser =
                this.mPackageManager.resolveActivityAsUser(intent, 65664, this.mCurrentUserId);
        ActivityInfo activityInfo =
                resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
        if (activityInfo == null
                || (bundle = activityInfo.metaData) == null
                || !bundle.getBoolean("android.dock_home")) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setClassName(activityInfo.packageName, activityInfo.name);
        return intent2;
    }

    public final void dismissKeyboardShortcutsMenu() {
        IStatusBar iStatusBar;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal == null
                || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
            return;
        }
        try {
            iStatusBar.dismissKeyboardShortcutsMenu();
        } catch (RemoteException unused) {
        }
    }

    public final void dismissKeyguardLw(
            IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null || !keyguardServiceDelegate.isShowing()) {
            if (iKeyguardDismissCallback != null) {
                try {
                    iKeyguardDismissCallback.onDismissError();
                    return;
                } catch (RemoteException e) {
                    Slog.w("WindowManager", "Failed to call callback", e);
                    return;
                }
            }
            return;
        }
        if (DEBUG_KEYGUARD) {
            Slog.d("WindowManager", "PWM.dismissKeyguardLw");
        }
        KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardDelegate.mKeyguardService;
        if (keyguardServiceWrapper != null) {
            keyguardServiceWrapper.dismiss(iKeyguardDismissCallback, charSequence);
        }
    }

    public final void dump(final PrintWriter printWriter) {
        printWriter.print("    ");
        printWriter.print("mSafeMode=");
        printWriter.print(this.mSafeMode);
        printWriter.print(" mSystemReady=");
        printWriter.print(this.mSystemReady);
        printWriter.print(" mSystemBooted=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "mCameraLensCoverState=", this.mSystemBooted);
        int i = this.mCameraLensCoverState;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i != -1
                        ? i != 0
                                ? i != 1 ? Integer.toString(i) : "CAMERA_LENS_COVERED"
                                : "CAMERA_LENS_UNCOVERED"
                        : "CAMERA_LENS_COVER_ABSENT",
                "    ",
                "mWakeGestureEnabledSetting=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "mUiMode=", this.mWakeGestureEnabledSetting);
        printWriter.print(Configuration.uiModeToString(this.mUiMode));
        printWriter.print("mEnableCarDockHomeCapture=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "mLidKeyboardAccessibility=", this.mEnableCarDockHomeCapture);
        printWriter.print(this.mLidKeyboardAccessibility);
        printWriter.print(" mLidNavigationAccessibility=");
        printWriter.print(this.mLidNavigationAccessibility);
        printWriter.print(" getLidBehavior=");
        int i2 = Settings.Global.getInt(this.mContext.getContentResolver(), "lid_behavior", 0);
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i2 != 0
                        ? i2 != 1
                                ? i2 != 2 ? Integer.toString(i2) : "LID_BEHAVIOR_LOCK"
                                : "LID_BEHAVIOR_SLEEP"
                        : "LID_BEHAVIOR_NONE",
                "    ",
                "mLongPressOnBackBehavior=");
        int i3 = this.mLongPressOnBackBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i3 != 0
                        ? i3 != 1 ? Integer.toString(i3) : "LONG_PRESS_BACK_GO_TO_VOICE_ASSIST"
                        : "LONG_PRESS_BACK_NOTHING",
                "    ",
                "mLongPressOnHomeBehavior=");
        int i4 = this.mLongPressOnHomeBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i4 != 0
                        ? i4 != 1
                                ? i4 != 2
                                        ? i4 != 3
                                                ? i4 != 4
                                                        ? i4 != 101
                                                                ? Integer.toString(i4)
                                                                : "LONG_PRESS_HOME_BIXBY_TOUCH"
                                                        : "LONG_PRESS_HOME_SEARCLE"
                                                : "LONG_PRESS_HOME_NOTIFICATION_PANEL"
                                        : "LONG_PRESS_HOME_ASSIST"
                                : "LONG_PRESS_HOME_ALL_APPS"
                        : "LONG_PRESS_HOME_NOTHING",
                "    ",
                "mDoubleTapOnHomeBehavior=");
        int i5 = this.mDoubleTapOnHomeBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i5 != 0
                        ? i5 != 1
                                ? i5 != 2 ? Integer.toString(i5) : "DOUBLE_TAP_HOME_PIP_MENU"
                                : "DOUBLE_TAP_HOME_RECENT_SYSTEM_UI"
                        : "DOUBLE_TAP_HOME_NOTHING",
                "    ",
                "mShortPressOnPowerBehavior=");
        int i6 = this.mShortPressOnPowerBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i6 != 0
                        ? i6 != 1
                                ? i6 != 2
                                        ? i6 != 3
                                                ? i6 != 4
                                                        ? i6 != 5
                                                                ? Integer.toString(i6)
                                                                : "SHORT_PRESS_POWER_CLOSE_IME_OR_GO_HOME"
                                                        : "SHORT_PRESS_POWER_GO_HOME"
                                                : "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP_AND_GO_HOME"
                                        : "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP"
                                : "SHORT_PRESS_POWER_GO_TO_SLEEP"
                        : "SHORT_PRESS_POWER_NOTHING",
                "    ",
                "mLongPressOnPowerBehavior=");
        int i7 = this.mLongPressOnPowerBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i7 != 0
                        ? i7 != 1
                                ? i7 != 2
                                        ? i7 != 3
                                                ? i7 != 4
                                                        ? i7 != 5
                                                                ? Integer.toString(i7)
                                                                : "LONG_PRESS_POWER_ASSISTANT"
                                                        : "LONG_PRESS_POWER_GO_TO_VOICE_ASSIST"
                                                : "LONG_PRESS_POWER_SHUT_OFF_NO_CONFIRM"
                                        : "LONG_PRESS_POWER_SHUT_OFF"
                                : "LONG_PRESS_POWER_GLOBAL_ACTIONS"
                        : "LONG_PRESS_POWER_NOTHING",
                "    ",
                "mSettingsKeyBehavior=");
        int i8 = this.mSettingsKeyBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i8 != 0
                        ? i8 != 1
                                ? i8 != 2 ? Integer.toString(i8) : "SETTINGS_KEY_BEHAVIOR_NOTHING"
                                : "SETTINGS_KEY_BEHAVIOR_NOTIFICATION_PANEL"
                        : "SETTINGS_KEY_BEHAVIOR_SETTINGS_ACTIVITY",
                "    ",
                "mLongPressOnPowerAssistantTimeoutMs=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mLongPressOnPowerAssistantTimeoutMs,
                printWriter,
                "    ",
                "mVeryLongPressOnPowerBehavior=");
        int i9 = this.mVeryLongPressOnPowerBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i9 != 0
                        ? i9 != 1 ? Integer.toString(i9) : "VERY_LONG_PRESS_POWER_GLOBAL_ACTIONS"
                        : "VERY_LONG_PRESS_POWER_NOTHING",
                "    ",
                "mDoublePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mDoublePressOnPowerBehavior));
        printWriter.print("    ");
        printWriter.print("mTriplePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mTriplePressOnPowerBehavior));
        printWriter.print("    ");
        printWriter.print("mSupportShortPressPowerWhenDefaultDisplayOn=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "    ",
                "mPowerVolUpBehavior=",
                this.mSupportShortPressPowerWhenDefaultDisplayOn);
        int i10 = this.mPowerVolUpBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i10 != 0
                        ? i10 != 1
                                ? i10 != 2
                                        ? Integer.toString(i10)
                                        : "POWER_VOLUME_UP_BEHAVIOR_GLOBAL_ACTIONS"
                                : "POWER_VOLUME_UP_BEHAVIOR_MUTE"
                        : "POWER_VOLUME_UP_BEHAVIOR_NOTHING",
                "    ",
                "mShortPressOnSleepBehavior=");
        int i11 = this.mShortPressOnSleepBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i11 != 0
                        ? i11 != 1
                                ? Integer.toString(i11)
                                : "SHORT_PRESS_SLEEP_GO_TO_SLEEP_AND_GO_HOME"
                        : "SHORT_PRESS_SLEEP_GO_TO_SLEEP",
                "    ",
                "mShortPressOnWindowBehavior=");
        int i12 = this.mShortPressOnWindowBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i12 != 0
                        ? i12 != 1 ? Integer.toString(i12) : "SHORT_PRESS_WINDOW_PICTURE_IN_PICTURE"
                        : "SHORT_PRESS_WINDOW_NOTHING",
                "    ",
                "mShortPressOnStemPrimaryBehavior=");
        int i13 = this.mShortPressOnStemPrimaryBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i13 != 0
                        ? i13 != 1
                                ? i13 != 2
                                        ? Integer.toString(i13)
                                        : "SHORT_PRESS_PRIMARY_LAUNCH_TARGET_ACTIVITY"
                                : "SHORT_PRESS_PRIMARY_LAUNCH_ALL_APPS"
                        : "SHORT_PRESS_PRIMARY_NOTHING",
                "    ",
                "mDoublePressOnStemPrimaryBehavior=");
        int i14 = this.mDoublePressOnStemPrimaryBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i14 != 0
                        ? i14 != 1
                                ? Integer.toString(i14)
                                : "DOUBLE_PRESS_PRIMARY_SWITCH_RECENT_APP"
                        : "DOUBLE_PRESS_PRIMARY_NOTHING",
                "    ",
                "mTriplePressOnStemPrimaryBehavior=");
        int i15 = this.mTriplePressOnStemPrimaryBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i15 != 0
                        ? i15 != 1
                                ? Integer.toString(i15)
                                : "TRIPLE_PRESS_PRIMARY_TOGGLE_ACCESSIBILITY"
                        : "TRIPLE_PRESS_PRIMARY_NOTHING",
                "    ",
                "mLongPressOnStemPrimaryBehavior=");
        int i16 = this.mLongPressOnStemPrimaryBehavior;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i16 != 0
                        ? i16 != 1
                                ? Integer.toString(i16)
                                : "LONG_PRESS_PRIMARY_LAUNCH_VOICE_ASSISTANT"
                        : "LONG_PRESS_PRIMARY_NOTHING",
                "    ",
                "mAllowStartActivityForLongPressOnPowerDuringSetup=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "    ",
                "mHasSoftInput=",
                this.mAllowStartActivityForLongPressOnPowerDuringSetup);
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "mDismissImeOnBackKeyPressed=", this.mHasSoftInput);
        printWriter.print(this.mDismissImeOnBackKeyPressed);
        printWriter.print(" mIncallPowerBehavior=");
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                (this.mIncallPowerBehavior & 2) != 0 ? "hangup" : "sleep",
                "    ",
                "mIncallBackBehavior=");
        printWriter.print((this.mIncallBackBehavior & 1) != 0 ? "hangup" : "<nothing>");
        printWriter.print(" mEndcallBehavior=");
        int i17 = this.mEndcallBehavior;
        StringBuilder sb = new StringBuilder();
        if ((i17 & 1) != 0) {
            sb.append("home|");
        }
        if ((i17 & 2) != 0) {
            sb.append("sleep|");
        }
        int length = sb.length();
        printWriter.println(length == 0 ? "<nothing>" : sb.substring(0, length - 1));
        printWriter.print("    ");
        printWriter.println("mDisplayHomeButtonHandlers=");
        for (int i18 = 0; i18 < this.mDisplayHomeButtonHandlers.size(); i18++) {
            int keyAt = this.mDisplayHomeButtonHandlers.keyAt(i18);
            printWriter.print("    ");
            printWriter.print("  ");
            printWriter.println(this.mDisplayHomeButtonHandlers.get(keyAt));
        }
        printWriter.print("    ");
        printWriter.print("mKeyguardOccluded=");
        printWriter.print(isKeyguardOccluded());
        printWriter.print(" mKeyguardOccludedChanged=");
        printWriter.print(this.mKeyguardOccludedChanged);
        printWriter.print(" mPendingKeyguardOccluded=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "    ",
                "mAllowLockscreenWhenOnDisplays=",
                this.mPendingKeyguardOccluded);
        printWriter.print(!this.mAllowLockscreenWhenOnDisplays.isEmpty());
        printWriter.print(" mLockScreenTimeout=");
        printWriter.print(this.mLockScreenTimeout);
        printWriter.print(" mLockScreenTimerActive=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "    ", "mKidsModeEnabled=", this.mLockScreenTimerActive);
        printWriter.println(this.mKidsModeEnabled);
        HapticFeedbackVibrationProvider hapticFeedbackVibrationProvider =
                this.mHapticFeedbackVibrationProvider;
        hapticFeedbackVibrationProvider.getClass();
        printWriter.print("mHapticTextHandleEnabled=");
        printWriter.println(hapticFeedbackVibrationProvider.mHapticTextHandleEnabled);
        GlobalKeyManager globalKeyManager = this.mGlobalKeyManager;
        int size = globalKeyManager.mKeyMapping.size();
        if (size == 0) {
            printWriter.print("    ");
            printWriter.println("mKeyMapping.size=0");
        } else {
            printWriter.print("    ");
            printWriter.println("mKeyMapping={");
            for (int i19 = 0; i19 < size; i19++) {
                printWriter.print("  ");
                printWriter.print("    ");
                printWriter.print(
                        KeyEvent.keyCodeToString(globalKeyManager.mKeyMapping.keyAt(i19)));
                printWriter.print("=");
                printWriter.print(
                        ((GlobalKeyManager.GlobalKeyAction)
                                        globalKeyManager.mKeyMapping.valueAt(i19))
                                .mComponentName.flattenToString());
                printWriter.print(",dispatchWhenNonInteractive=");
                printWriter.println(
                        ((GlobalKeyManager.GlobalKeyAction)
                                        globalKeyManager.mKeyMapping.valueAt(i19))
                                .mDispatchWhenNonInteractive);
            }
            printWriter.print("    ");
            printWriter.println("}");
        }
        KeyCombinationManager keyCombinationManager = this.mKeyCombinationManager;
        keyCombinationManager.getClass();
        printWriter.println("    KeyCombination rules:");
        KeyCombinationManager.forAllRules(
                keyCombinationManager.mRules,
                new Consumer() { // from class:
                                 // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda7
                    public final /* synthetic */ String f$1 = "    ";

                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        printWriter.println(
                                this.f$1
                                        + "  "
                                        + ((KeyCombinationManager.TwoKeysCombinationRule) obj));
                    }
                });
        if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
            for (int i20 = 0; i20 < keyCombinationManager.mDownTimes.size(); i20++) {
                printWriter.print(
                        "     mDownTimes keyCode=" + keyCombinationManager.mDownTimes.keyAt(i20));
                printWriter.println(" time=" + keyCombinationManager.mDownTimes.valueAt(i20));
            }
            for (int i21 = 0; i21 < keyCombinationManager.mDownKeyEvent.size(); i21++) {
                printWriter.println(
                        "     mDownKeyEvent event="
                                + keyCombinationManager.mDownKeyEvent.valueAt(i21));
            }
        }
        SingleKeyGestureDetector singleKeyGestureDetector = this.mSingleKeyGestureDetector;
        singleKeyGestureDetector.getClass();
        printWriter.println("    SingleKey rules:");
        for (int i22 = 0; i22 < singleKeyGestureDetector.mCustomRules.size(); i22++) {
            printWriter.println(
                    "      "
                            + ((SingleKeyGestureDetector.SingleKeyRule)
                                    singleKeyGestureDetector.mCustomRules.valueAt(i22)));
        }
        DeferredKeyActionExecutor deferredKeyActionExecutor = this.mDeferredKeyActionExecutor;
        deferredKeyActionExecutor.getClass();
        printWriter.println("    Deferred key action executor:");
        if (deferredKeyActionExecutor.mBuffers.size() == 0) {
            printWriter.println("      empty");
        } else {
            for (int i23 = 0; i23 < deferredKeyActionExecutor.mBuffers.size(); i23++) {
                DeferredKeyActionExecutor.TimedActionsBuffer timedActionsBuffer =
                        (DeferredKeyActionExecutor.TimedActionsBuffer)
                                deferredKeyActionExecutor.mBuffers.valueAt(i23);
                boolean z = timedActionsBuffer.mExecutable;
                int i24 = timedActionsBuffer.mKeyCode;
                if (z) {
                    printWriter.println("      " + KeyEvent.keyCodeToString(i24) + ": executable");
                } else {
                    printWriter.println(
                            "      "
                                    + KeyEvent.keyCodeToString(i24)
                                    + ": "
                                    + ((ArrayList) timedActionsBuffer.mActions).size()
                                    + " actions queued");
                }
            }
        }
        MyWakeGestureListener myWakeGestureListener = this.mWakeGestureListener;
        if (myWakeGestureListener != null) {
            synchronized (myWakeGestureListener.mLock) {
                printWriter.println("    WakeGestureListener");
                String str = "      ";
                printWriter.println(
                        str + "mTriggerRequested=" + myWakeGestureListener.mTriggerRequested);
                printWriter.println(str + "mSensor=" + myWakeGestureListener.mSensor);
            }
        }
        BurnInProtectionHelper burnInProtectionHelper = this.mBurnInProtectionHelper;
        if (burnInProtectionHelper != null) {
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                    printWriter,
                                    "    BurnInProtection",
                                    "      mBurnInProtectionActive="),
                            burnInProtectionHelper.mBurnInProtectionActive,
                            printWriter,
                            "      mHorizontalBurnInOffsetsBounds=(");
            m.append(burnInProtectionHelper.mMinHorizontalBurnInOffset);
            m.append(", ");
            m.append(burnInProtectionHelper.mMaxHorizontalBurnInOffset);
            m.append(")");
            printWriter.println(m.toString());
            printWriter.println(
                    "      mVerticalBurnInOffsetsBounds=("
                            + burnInProtectionHelper.mMinVerticalBurnInOffset
                            + ", "
                            + burnInProtectionHelper.mMaxVerticalBurnInOffset
                            + ")");
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("      mBurnInRadiusMaxSquared="),
                            burnInProtectionHelper.mBurnInRadiusMaxSquared,
                            printWriter,
                            "      mLastBurnInOffset=(");
            m2.append(burnInProtectionHelper.mLastBurnInXOffset);
            m2.append(", ");
            m2.append(burnInProtectionHelper.mLastBurnInYOffset);
            m2.append(")");
            printWriter.println(m2.toString());
            printWriter.println(
                    "      mOfsetChangeDirections=("
                            + burnInProtectionHelper.mXOffsetDirection
                            + ", "
                            + burnInProtectionHelper.mYOffsetDirection
                            + ")");
        }
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            StringBuilder m3 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                            .m(
                                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                            .m$1(
                                                                                                                                    printWriter,
                                                                                                                                    "    KeyguardServiceDelegate",
                                                                                                                                    "     "
                                                                                                                                        + " showing="),
                                                                                                                    keyguardServiceDelegate
                                                                                                                            .mKeyguardState
                                                                                                                            .showing,
                                                                                                                    printWriter,
                                                                                                                    "     "
                                                                                                                        + " inputRestricted="),
                                                                                                    keyguardServiceDelegate
                                                                                                            .mKeyguardState
                                                                                                            .inputRestricted,
                                                                                                    printWriter,
                                                                                                    "     "
                                                                                                        + " occluded="),
                                                                                    keyguardServiceDelegate
                                                                                            .mKeyguardState
                                                                                            .occluded,
                                                                                    printWriter,
                                                                                    "     "
                                                                                        + " secure="),
                                                                    keyguardServiceDelegate
                                                                            .mKeyguardState
                                                                            .secure,
                                                                    printWriter,
                                                                    "      dreaming="),
                                                    keyguardServiceDelegate.mKeyguardState.dreaming,
                                                    printWriter,
                                                    "      systemIsReady="),
                                            keyguardServiceDelegate.mKeyguardState.systemIsReady,
                                            printWriter,
                                            "      deviceHasKeyguard="),
                                    keyguardServiceDelegate.mKeyguardState.deviceHasKeyguard,
                                    printWriter,
                                    "      enabled="),
                            keyguardServiceDelegate.mKeyguardState.enabled,
                            printWriter,
                            "      offReason=");
            m3.append(
                    WindowManagerPolicyConstants.offReasonToString(
                            keyguardServiceDelegate.mKeyguardState.offReason));
            printWriter.println(m3.toString());
            StringBuilder m4 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("      currentUser="),
                                    keyguardServiceDelegate.mKeyguardState.currentUser,
                                    printWriter,
                                    "      bootCompleted="),
                            keyguardServiceDelegate.mKeyguardState.bootCompleted,
                            printWriter,
                            "      screenState=");
            int i25 = keyguardServiceDelegate.mKeyguardState.screenState;
            StringBuilder m5 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            i25 != 0
                                    ? i25 != 1
                                            ? i25 != 2
                                                    ? i25 != 3
                                                            ? Integer.toString(i25)
                                                            : "SCREEN_STATE_TURNING_OFF"
                                                    : "SCREEN_STATE_ON"
                                            : "SCREEN_STATE_TURNING_ON"
                                    : "SCREEN_STATE_OFF",
                            "      interactiveState=",
                            m4);
            int i26 = keyguardServiceDelegate.mKeyguardState.interactiveState;
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    m5,
                    i26 != 0
                            ? i26 != 1
                                    ? i26 != 2
                                            ? i26 != 3
                                                    ? Integer.toString(i26)
                                                    : "INTERACTIVE_STATE_GOING_TO_SLEEP"
                                            : "INTERACTIVE_STATE_AWAKE"
                                    : "INTERACTIVE_STATE_WAKING"
                            : "INTERACTIVE_STATE_SLEEP",
                    printWriter);
            KeyguardServiceWrapper keyguardServiceWrapper =
                    keyguardServiceDelegate.mKeyguardService;
            if (keyguardServiceWrapper != null) {
                KeyguardStateMonitor keyguardStateMonitor =
                        keyguardServiceWrapper.mKeyguardStateMonitor;
                keyguardStateMonitor.getClass();
                printWriter.println("      KeyguardStateMonitor");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                new StringBuilder(
                                                                        "        mIsShowing="),
                                                                keyguardStateMonitor.mIsShowing,
                                                                printWriter,
                                                                "        mSimSecure="),
                                                keyguardStateMonitor.mSimSecure,
                                                printWriter,
                                                "        mInputRestricted="),
                                        keyguardStateMonitor.mInputRestricted,
                                        printWriter,
                                        "        mTrusted="),
                                keyguardStateMonitor.mTrusted,
                                printWriter,
                                "        mCurrentUserId="),
                        keyguardStateMonitor.mCurrentUserId,
                        printWriter);
            }
        }
        printWriter.print("    ");
        printWriter.println("Looper state:");
        this.mHandler.getLooper().dump(new PrintWriterPrinter(printWriter), "      ");
        this.mExt.dump(printWriter);
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1159641169922L, this.mDefaultDisplayRotation.mUserRotationMode);
        protoOutputStream.write(1159641169923L, this.mDefaultDisplayRotation.mUserRotation);
        protoOutputStream.write(
                1159641169924L, this.mDefaultDisplayRotation.mCurrentAppOrientation);
        protoOutputStream.write(1133871366149L, this.mDefaultDisplayPolicy.mScreenOnFully);
        protoOutputStream.write(1133871366150L, this.mDefaultDisplayPolicy.mKeyguardDrawComplete);
        protoOutputStream.write(
                1133871366151L, this.mDefaultDisplayPolicy.mWindowManagerDrawComplete);
        protoOutputStream.write(1133871366156L, isKeyguardOccluded());
        protoOutputStream.write(1133871366157L, this.mKeyguardOccludedChanged);
        protoOutputStream.write(1133871366158L, this.mPendingKeyguardOccluded);
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            long start2 = protoOutputStream.start(1146756268052L);
            protoOutputStream.write(1133871366145L, keyguardServiceDelegate.mKeyguardState.showing);
            protoOutputStream.write(
                    1133871366146L, keyguardServiceDelegate.mKeyguardState.occluded);
            protoOutputStream.write(1133871366147L, keyguardServiceDelegate.mKeyguardState.secure);
            protoOutputStream.write(
                    1159641169924L, keyguardServiceDelegate.mKeyguardState.screenState);
            protoOutputStream.write(
                    1159641169925L, keyguardServiceDelegate.mKeyguardState.interactiveState);
            protoOutputStream.end(start2);
        }
        protoOutputStream.end(start);
    }

    public final void enableKeyguard(boolean z) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            KeyguardServiceWrapper keyguardServiceWrapper =
                    keyguardServiceDelegate.mKeyguardService;
            if (keyguardServiceWrapper != null) {
                keyguardServiceWrapper.setKeyguardEnabled(z);
            }
            keyguardServiceDelegate.mKeyguardState.enabled = z;
        }
    }

    public final void enableScreen(
            DisplayPowerController.ScreenOnUnblocker screenOnUnblocker, boolean z) {
        boolean z2;
        boolean z3 = this.mDefaultDisplayPolicy.mAwake;
        synchronized (this.mLock) {
            try {
                z2 = false;
                if (!this.mKeyguardDrawnOnce && z3) {
                    this.mKeyguardDrawnOnce = true;
                    if (this.mBootMessageNeedsHiding) {
                        this.mBootMessageNeedsHiding = false;
                        this.mHandler.sendEmptyMessage(11);
                    }
                    z2 = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z && screenOnUnblocker != null) {
            screenOnUnblocker.onScreenOn();
        }
        if (z2) {
            this.mWindowManagerFuncs.enableScreenIfNeeded();
        }
    }

    public final void exitKeyguardSecurely(WindowManagerService.AnonymousClass3 anonymousClass3) {
        KeyguardServiceWrapper keyguardServiceWrapper;
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null
                || (keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService) == null) {
            return;
        }
        KeyguardServiceDelegate.KeyguardExitDelegate keyguardExitDelegate =
                new KeyguardServiceDelegate.KeyguardExitDelegate();
        keyguardExitDelegate.mOnKeyguardExitResult = anonymousClass3;
        keyguardServiceWrapper.verifyUnlock(keyguardExitDelegate);
    }

    public final void finishedBootAnimation() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            KeyguardServiceWrapper keyguardServiceWrapper =
                    keyguardServiceDelegate.mKeyguardService;
            if (keyguardServiceWrapper != null) {
                keyguardServiceWrapper.onFinishedBootAnim();
            } else {
                keyguardServiceDelegate.mKeyguardState.bootAnimFinished = true;
            }
        }
    }

    public final void finishedGoingToSleep(int i, int i2) {
        if (i != 0) {
            return;
        }
        EventLog.writeEvent(70000, 0);
        if (DEBUG_WAKEUP) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Finished going to sleep... (groupId=", " why=");
            m.append(
                    WindowManagerPolicyConstants.offReasonToString(
                            WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2)));
            m.append(")");
            Slog.i("WindowManager", m.toString());
        }
        MetricsLogger.histogram(this.mContext, "screen_timeout", this.mLockScreenTimeout / 1000);
        this.mIsGoingToSleepDefaultDisplay = false;
        this.mDefaultDisplayPolicy.setAwake(false);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mLastDexMode == 2) {
            phoneWindowManagerExt.mPendingDualModeInteractive |= 8;
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                KeyguardServiceWrapper keyguardServiceWrapper =
                        keyguardServiceDelegate.mKeyguardService;
                if (keyguardServiceWrapper != null) {
                    keyguardServiceWrapper.onFinishedGoingToSleep(i2, false);
                }
                keyguardServiceDelegate.mKeyguardState.interactiveState = 0;
            }
        }
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            DisplayFoldDurationLogger displayFoldDurationLogger =
                    displayFoldController.mDurationLogger;
            displayFoldDurationLogger.log();
            displayFoldDurationLogger.mScreenState = 0;
            displayFoldDurationLogger.mLastChanged = null;
        }
        if (CoreRune.FW_APPLOCK) {
            this.mExt.getClass();
            try {
                ActivityTaskManager.getService().clearAppLockedUnLockedApp();
            } catch (RemoteException e) {
                Log.e(
                        "PhoneWindowManagerExt",
                        " ClearAppLockedUnLockedApp failed , Remote exception ",
                        e);
            }
        }
    }

    public final void finishedWakingUp(int i, int i2) {
        if (DEBUG_WAKEUP) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Finished waking up... (groupId=", " why=");
            m.append(
                    WindowManagerPolicyConstants.onReasonToString(
                            WindowManagerPolicyConstants.translateWakeReasonToOnReason(i2)));
            m.append(")");
            Slog.i("WindowManager", m.toString());
        }
        if (i != 0) {
            return;
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mLastDexMode == 2) {
            phoneWindowManagerExt.mPendingDualModeInteractive |= 2;
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (keyguardServiceDelegate.mKeyguardService != null) {
                    Log.v("KeyguardServiceDelegate", "onFinishedWakingUp()");
                    keyguardServiceDelegate.mKeyguardService.onFinishedWakingUp();
                }
                keyguardServiceDelegate.mKeyguardState.interactiveState = 2;
            }
        }
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            DisplayFoldDurationLogger displayFoldDurationLogger =
                    displayFoldController.mDurationLogger;
            Boolean bool = displayFoldController.mFolded;
            if (bool == null) {
                displayFoldDurationLogger.mScreenState = -1;
            } else {
                displayFoldDurationLogger.getClass();
                if (bool.booleanValue()) {
                    displayFoldDurationLogger.mScreenState = 2;
                } else {
                    displayFoldDurationLogger.mScreenState = 1;
                }
            }
            displayFoldDurationLogger.mLastChanged = Long.valueOf(SystemClock.uptimeMillis());
        }
    }

    public final long getAccessibilityShortcutTimeout() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(),
                "accessibility_shortcut_dialog_shown",
                0,
                this.mCurrentUserId);
        Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(),
                "skip_accessibility_shortcut_dialog_timeout_restriction",
                0,
                this.mCurrentUserId);
        return viewConfiguration.getAccessibilityShortcutKeyTimeout();
    }

    public final AnonymousClass1 getHdmiControl() {
        if (this.mHdmiControl == null) {
            if (!this.mHasFeatureHdmiCec) {
                return null;
            }
            HdmiControlManager hdmiControlManager =
                    (HdmiControlManager) this.mContext.getSystemService("hdmi_control");
            this.mHdmiControl =
                    new AnonymousClass1(
                            hdmiControlManager != null
                                    ? hdmiControlManager.getPlaybackClient()
                                    : null);
        }
        return this.mHdmiControl;
    }

    public final KeyCombinationManager getKeyCombinationManager() {
        if (this.mKeyCombinationManager == null) {
            initKeyCombinationRules();
        }
        return this.mKeyCombinationManager;
    }

    public final int getMaxMultiPressPowerCount() {
        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
            return 1;
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mQuintuplePressOnPowerBehavior != 0) {
            phoneWindowManagerExt.getClass();
            if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS
                    && phoneWindowManagerExt.mQuintuplePressOnPowerBehavior == 103) {
                return 5;
            }
        }
        PhoneWindowManagerExt phoneWindowManagerExt2 = this.mExt;
        int i = phoneWindowManagerExt2.mQuadruplePressOnPowerBehavior;
        if (i != 0 && i == 106) {
            return 4;
        }
        if (this.mTriplePressOnPowerBehavior != 0) {
            phoneWindowManagerExt2.getClass();
            if (InputRune.PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL
                    && phoneWindowManagerExt2.mPolicy.mTriplePressOnPowerBehavior == 102) {
                return 3;
            }
        }
        if (this.mDoublePressOnPowerBehavior != 0) {
            PhoneWindowManagerExt phoneWindowManagerExt3 = this.mExt;
            phoneWindowManagerExt3.getClass();
            boolean z = InputRune.PWM_SIDE_KEY_DOUBLE_PRESS;
            PhoneWindowManager phoneWindowManager = phoneWindowManagerExt3.mPolicy;
            if ((z && phoneWindowManager.mDoublePressOnPowerBehavior == 104)
                    || phoneWindowManager.mDoublePressOnPowerBehavior == 106) {
                return 2;
            }
            if (InputRune.PWM_QUICK_LAUNCH_CAMERA
                    && phoneWindowManagerExt3.isQuickLaunchCameraEnabled(26)) {
                return 2;
            }
            if (InputRune.PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE
                    && phoneWindowManager.mDoublePressOnPowerBehavior == 105) {
                return 2;
            }
        }
        return 1;
    }

    public final int getResolvedLongPressOnPowerBehavior() {
        ComponentName componentName;
        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            if (phoneWindowManagerExt.mSystemKeyPolicy != null
                    && (FactoryTest.needBlockingPowerKey()
                            || ((componentName = phoneWindowManagerExt.mTopActivity) != null
                                    && "com.sec.facuifunction.app.ui.UIHardKey"
                                            .equals(componentName.getClassName())))) {
                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                return 0;
            }
        }
        if (FactoryTest.isLongPressOnPowerOffEnabled()
                || FactoryTest.isAutomaticTestMode(this.mContext)) {
            return 3;
        }
        if (this.mLongPressOnPowerBehavior == 5
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(), "device_provisioned", 0)
                        == 0) {
            return 1;
        }
        if (this.mLongPressOnPowerBehavior == 4) {
            int intForUser =
                    Settings.System.getIntForUser(
                            this.mContext.getContentResolver(),
                            "clockwork_long_press_to_assistant_enabled",
                            1,
                            -2);
            if (Log.isLoggable("WindowManager", 3)) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        intForUser, "longPressToAssistant = ", "WindowManager");
            }
            if (intForUser != 1) {
                return 0;
            }
        }
        return this.mLongPressOnPowerBehavior;
    }

    public final StatusBarManagerInternal getStatusBarManagerInternal() {
        StatusBarManagerInternal statusBarManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarManagerInternal == null) {
                    this.mStatusBarManagerInternal =
                            (StatusBarManagerInternal)
                                    LocalServices.getService(StatusBarManagerInternal.class);
                }
                statusBarManagerInternal = this.mStatusBarManagerInternal;
            } catch (Throwable th) {
                throw th;
            }
        }
        return statusBarManagerInternal;
    }

    public final IStatusBarService getStatusBarService() {
        IStatusBarService iStatusBarService;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarService == null) {
                    this.mStatusBarService =
                            IStatusBarService.Stub.asInterface(
                                    ServiceManager.getService("statusbar"));
                }
                iStatusBarService = this.mStatusBarService;
            } catch (Throwable th) {
                throw th;
            }
        }
        return iStatusBarService;
    }

    public final int getTargetDisplayIdForKeyEvent(KeyEvent keyEvent) {
        int displayId = keyEvent.getDisplayId();
        if (displayId == -1) {
            displayId = this.mTopFocusedDisplayId;
        }
        if (displayId == -1) {
            return 0;
        }
        return displayId;
    }

    public final TelecomManager getTelecommService() {
        return (TelecomManager) this.mContext.getSystemService("telecom");
    }

    public final boolean goHome() {
        IActivityTaskManager service;
        String opPackageName;
        String attributionTag;
        Intent intent;
        if (!isUserSetupComplete()) {
            Slog.i("WindowManager", "Not going home because user setup is in progress.");
            return false;
        }
        try {
            if (SystemProperties.getInt("persist.sys.uts-test-mode", 0) == 1) {
                Log.d("WindowManager", "UTS-TEST-MODE");
            } else {
                ActivityManager.getService().stopAppSwitches();
                PhoneWindow.sendCloseSystemWindows(this.mContext, (String) null);
                Intent createHomeDockIntent = createHomeDockIntent();
                if (createHomeDockIntent != null
                        && ActivityTaskManager.getService()
                                        .startActivityAsUser(
                                                (IApplicationThread) null,
                                                this.mContext.getOpPackageName(),
                                                this.mContext.getAttributionTag(),
                                                createHomeDockIntent,
                                                createHomeDockIntent.resolveTypeIfNeeded(
                                                        this.mContext.getContentResolver()),
                                                (IBinder) null,
                                                (String) null,
                                                0,
                                                1,
                                                (ProfilerInfo) null,
                                                (Bundle) null,
                                                -2)
                                == 1) {
                    return false;
                }
            }
            service = ActivityTaskManager.getService();
            opPackageName = this.mContext.getOpPackageName();
            attributionTag = this.mContext.getAttributionTag();
            intent = this.mHomeIntent;
        } catch (RemoteException unused) {
        }
        return service.startActivityAsUser(
                        (IApplicationThread) null,
                        opPackageName,
                        attributionTag,
                        intent,
                        intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                        (IBinder) null,
                        (String) null,
                        0,
                        1,
                        (ProfilerInfo) null,
                        (Bundle) null,
                        -2)
                != 1;
    }

    public final boolean handleHomeShortcuts(IBinder iBinder, KeyEvent keyEvent) {
        boolean z;
        IStatusBar iStatusBar;
        IStatusBar iStatusBar2;
        DisplayHomeButtonHandler displayHomeButtonHandler =
                (DisplayHomeButtonHandler)
                        this.mDisplayHomeButtonHandlers.get(keyEvent.getDisplayId());
        if (displayHomeButtonHandler == null) {
            displayHomeButtonHandler = new DisplayHomeButtonHandler(keyEvent.getDisplayId());
            this.mDisplayHomeButtonHandlers.put(keyEvent.getDisplayId(), displayHomeButtonHandler);
        }
        boolean keyguardOn = PhoneWindowManager.this.keyguardOn();
        int repeatCount = keyEvent.getRepeatCount();
        boolean z2 = keyEvent.getAction() == 0;
        boolean isCanceled = keyEvent.isCanceled();
        if (DEBUG_INPUT) {
            Log.d(
                    "WindowManager",
                    String.format(
                            "handleHomeButton in display#%d mHomePressed = %b",
                            Integer.valueOf(displayHomeButtonHandler.mDisplayId),
                            Boolean.valueOf(displayHomeButtonHandler.mHomePressed)));
        }
        if (z2) {
            KeyInterceptionInfo keyInterceptionInfoFromToken =
                    PhoneWindowManager.this.mWindowManagerInternal.getKeyInterceptionInfoFromToken(
                            iBinder);
            if (keyInterceptionInfoFromToken != null) {
                int i = keyInterceptionInfoFromToken.layoutParamsType;
                if (i == 2009) {
                    return false;
                }
                if (i == 2040 && PhoneWindowManager.this.isKeyguardShowing()) {
                    return false;
                }
                for (int i2 : WINDOW_TYPES_WHERE_HOME_DOESNT_WORK) {
                    if (keyInterceptionInfoFromToken.layoutParamsType == i2) {
                        break;
                    }
                }
            }
            if (repeatCount == 0) {
                displayHomeButtonHandler.mHomePressed = true;
                if (InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE
                        && PhoneWindowManager.this.mExt.isLongPressHomeSearcle()) {
                    PhoneWindowManager.this.mExt.startSearcleByHomeKey(true, false);
                }
                if (displayHomeButtonHandler.mPendingHomeKeyEvent != null) {
                    displayHomeButtonHandler.mPendingHomeKeyEvent = null;
                    PhoneWindowManager.this.mHandler.removeCallbacks(
                            displayHomeButtonHandler.mHomeDoubleTapTimeoutRunnable);
                    if (PhoneWindowManager.this.mExt.isInDexMode()) {
                        PhoneWindowManager.this.mHandler.post(
                                new PhoneWindowManager$$ExternalSyntheticLambda2(
                                        displayHomeButtonHandler, keyEvent, 2));
                    } else {
                        PhoneWindowManager.this.mHandler.post(
                                new PhoneWindowManager$$ExternalSyntheticLambda2(
                                        displayHomeButtonHandler, keyEvent, 3));
                    }
                } else {
                    PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                    if (phoneWindowManager.mDoubleTapOnHomeBehavior == 1
                            && displayHomeButtonHandler.mDisplayId == 0) {
                        phoneWindowManager.mPreloadedRecentApps = true;
                        StatusBarManagerInternal statusBarManagerInternal =
                                phoneWindowManager.getStatusBarManagerInternal();
                        if (statusBarManagerInternal != null
                                && (iStatusBar = StatusBarManagerService.this.mBar) != null) {
                            try {
                                iStatusBar.preloadRecentApps();
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                }
            } else if ((keyEvent.getFlags() & 128) != 0) {
                if (keyguardOn) {
                    StringBuilder sb = new StringBuilder("keyguardOn, isShowing=");
                    sb.append(PhoneWindowManager.this.isKeyguardShowingAndNotOccluded());
                    sb.append(" isInputRestricted=");
                    KeyguardServiceDelegate keyguardServiceDelegate =
                            PhoneWindowManager.this.mKeyguardDelegate;
                    if (keyguardServiceDelegate == null) {
                        z = false;
                    } else {
                        KeyguardServiceWrapper keyguardServiceWrapper =
                                keyguardServiceDelegate.mKeyguardService;
                        if (keyguardServiceWrapper != null) {
                            keyguardServiceDelegate.mKeyguardState.inputRestricted =
                                    keyguardServiceWrapper.mKeyguardStateMonitor.mInputRestricted;
                        }
                        z = keyguardServiceDelegate.mKeyguardState.inputRestricted;
                    }
                    RCPManagerService$$ExternalSyntheticOutline0.m("WindowManager", sb, z);
                    if (InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE
                            && !displayHomeButtonHandler.mHomeConsumed) {
                        PhoneWindowManager.this.mExt.showingSearcleToastIfNeeded();
                    }
                } else {
                    if (PhoneWindowManager.this.mExt.mSystemKeyPolicy.hasSystemKeyInfo(3, 4)) {
                        Log.i("WindowManager", "skip long press home, requestedSystemKey");
                        return false;
                    }
                    PhoneWindowManager.this.mHandler.post(
                            new PhoneWindowManager$$ExternalSyntheticLambda2(
                                    displayHomeButtonHandler, keyEvent, 4));
                }
            }
            if (PhoneWindowManager.this.mExt.canDispatchingSystemKeyEvent(3)) {
                Log.i("WindowManager", "can dispatching home key event, requestedSystemKey");
                return false;
            }
        } else {
            if (displayHomeButtonHandler.mDisplayId == 0) {
                PhoneWindowManager phoneWindowManager2 = PhoneWindowManager.this;
                if (phoneWindowManager2.mPreloadedRecentApps) {
                    phoneWindowManager2.mPreloadedRecentApps = false;
                    StatusBarManagerInternal statusBarManagerInternal2 =
                            phoneWindowManager2.getStatusBarManagerInternal();
                    if (statusBarManagerInternal2 != null
                            && (iStatusBar2 = StatusBarManagerService.this.mBar) != null) {
                        try {
                            iStatusBar2.cancelPreloadRecentApps();
                        } catch (RemoteException unused2) {
                        }
                    }
                }
            }
            if (InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE
                    && PhoneWindowManager.this.mExt.isLongPressHomeSearcle()) {
                PhoneWindowManager.this.mExt.startSearcleByHomeKey(false, false);
            }
            displayHomeButtonHandler.mHomePressed = false;
            if (displayHomeButtonHandler.mHomeConsumed) {
                displayHomeButtonHandler.mHomeConsumed = false;
            } else if (isCanceled) {
                Log.i("WindowManager", "Ignoring HOME; event canceled.");
            } else {
                PhoneWindowManager phoneWindowManager3 = PhoneWindowManager.this;
                int i3 = phoneWindowManager3.mDoubleTapOnHomeBehavior;
                if (i3 != 0 && (i3 != 2 || phoneWindowManager3.mPictureInPictureVisible)) {
                    PhoneWindowManager.this.mHandler.removeCallbacks(
                            displayHomeButtonHandler.mHomeDoubleTapTimeoutRunnable);
                    displayHomeButtonHandler.mPendingHomeKeyEvent = keyEvent;
                    PhoneWindowManager.this.mHandler.postDelayed(
                            displayHomeButtonHandler.mHomeDoubleTapTimeoutRunnable,
                            ViewConfiguration.getDoubleTapTimeout());
                } else {
                    if (PhoneWindowManager.this.mExt.mSystemKeyPolicy.hasSystemKeyInfo(3, 3)) {
                        Log.i("WindowManager", "skip single press home, requestedSystemKey");
                        return false;
                    }
                    PhoneWindowManager.this.mHandler.post(
                            new PhoneWindowManager$$ExternalSyntheticLambda2(
                                    displayHomeButtonHandler, keyEvent, 1));
                }
            }
        }
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int handleKeyGesture(
            android.view.KeyEvent r18, boolean r19, boolean r20, int r21) {
        /*
            Method dump skipped, instructions count: 1305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.handleKeyGesture(android.view.KeyEvent,"
                    + " boolean, boolean, int):int");
    }

    public final void handleShortPressOnHome(KeyEvent keyEvent) {
        HdmiPlaybackClient hdmiPlaybackClient;
        logKeyboardSystemsEvent(keyEvent, KeyboardMetricsCollector.KeyboardLogEvent.HOME);
        AnonymousClass1 hdmiControl = getHdmiControl();
        if (hdmiControl != null
                && (hdmiPlaybackClient = (HdmiPlaybackClient) hdmiControl.this$0) != null) {
            hdmiPlaybackClient.oneTouchPlay(new PhoneWindowManager$HdmiControl$1());
        }
        if (this.mDreamManagerInternal == null) {
            this.mDreamManagerInternal =
                    (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
        }
        DreamManagerInternal dreamManagerInternal = this.mDreamManagerInternal;
        if (dreamManagerInternal == null || !dreamManagerInternal.isDreaming()) {
            launchHomeFromHotKey(keyEvent.getDisplayId(), true, true);
        } else {
            this.mDreamManagerInternal.stopDream(false, "short press on home");
        }
    }

    public final void hideRecentApps(int i, boolean z, boolean z2) {
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            if (!this.mExt.isInDexMode()) {
                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                if (iStatusBar != null) {
                    try {
                        iStatusBar.hideRecentApps(z, z2);
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                return;
            }
            int naturalBarTypeByDisplayId =
                    StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i);
            StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                    (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
            synchronized (StatusBarManagerService.this.mBarLock) {
                if (StatusBarManagerService.this.mBarMap.get(
                                Integer.valueOf(naturalBarTypeByDisplayId))
                        != null) {
                    try {
                        ((IStatusBar)
                                        StatusBarManagerService.this.mBarMap.get(
                                                Integer.valueOf(naturalBarTypeByDisplayId)))
                                .hideRecentApps(z, z2);
                    } catch (RemoteException unused2) {
                        Slog.i("StatusBarManagerService", "RemoteException for HIDE_RECENT_APPS");
                    }
                }
            }
        }
    }

    public final void init(
            Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        init(new Injector(context, windowManagerFuncs));
    }

    public void init(Injector injector) {
        int integer;
        int i;
        int i2;
        int i3;
        int i4;
        this.mContext = injector.mContext;
        this.mWindowManagerFuncs = injector.mWindowManagerFuncs;
        this.mWindowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mActivityManagerService = ActivityManager.getService();
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mInputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        this.mInputManagerInternal =
                (InputManagerService.LocalService)
                        LocalServices.getService(InputManagerService.LocalService.class);
        this.mDreamManagerInternal =
                (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
        this.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mSensorPrivacyManager =
                (SensorPrivacyManager) this.mContext.getSystemService(SensorPrivacyManager.class);
        this.mDisplayManager =
                (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        this.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManager packageManager = this.mContext.getPackageManager();
        this.mPackageManager = packageManager;
        this.mHasFeatureWatch = packageManager.hasSystemFeature("android.hardware.type.watch");
        this.mHasFeatureLeanback =
                this.mPackageManager.hasSystemFeature("android.software.leanback");
        this.mHasFeatureAuto =
                this.mPackageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mHasFeatureHdmiCec =
                this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec");
        this.mAccessibilityShortcutController =
                new AccessibilityShortcutController(
                        this.mContext, new Handler(), this.mCurrentUserId);
        this.mGlobalActionsFactory =
                new PhoneWindowManager$Injector$$ExternalSyntheticLambda0(injector);
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mLogger = new MetricsLogger();
        this.mScreenOffSleepTokenAcquirer =
                ActivityTaskManagerService.this.new SleepTokenAcquirerImpl("ScreenOff");
        Resources resources = this.mContext.getResources();
        this.mWakeOnDpadKeyPress = resources.getBoolean(R.bool.device_idle_wait_for_unlock);
        this.mWakeOnAssistKeyPress = resources.getBoolean(R.bool.device_idle_use_mode_manager);
        this.mWakeOnBackKeyPress = resources.getBoolean(R.bool.device_idle_use_window_alarms);
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_enableGeocoderOverlay);
        boolean z2 = SystemProperties.getBoolean("persist.debug.force_burn_in", false);
        if (z || z2) {
            if (z2) {
                integer = this.mContext.getResources().getConfiguration().isScreenRound() ? 6 : -1;
                i = -8;
                i3 = -8;
                i2 = 8;
                i4 = -4;
            } else {
                Resources resources2 = this.mContext.getResources();
                int integer2 =
                        resources2.getInteger(R.integer.config_defaultNightDisplayCustomEndTime);
                int integer3 =
                        resources2.getInteger(R.integer.config_defaultMediaVibrationIntensity);
                int integer4 =
                        resources2.getInteger(R.integer.config_defaultNightDisplayCustomStartTime);
                int integer5 = resources2.getInteger(R.integer.config_defaultNightDisplayAutoMode);
                integer =
                        resources2.getInteger(
                                R.integer.config_defaultMinEmergencyGestureTapDurationMillis);
                i = integer2;
                i2 = integer3;
                i3 = integer4;
                i4 = integer5;
            }
            this.mBurnInProtectionHelper =
                    new BurnInProtectionHelper(this.mContext, i, i2, i3, i4, integer);
        }
        PolicyHandler policyHandler = new PolicyHandler(Looper.myLooper());
        this.mHandler = policyHandler;
        this.mWakeGestureListener = new MyWakeGestureListener(this.mContext, policyHandler);
        SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSettingsObserver = settingsObserver;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("end_button_behavior"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("incall_power_button_behavior"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("incall_back_button_behavior"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("wake_gesture_enabled"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_off_timeout"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("default_input_method"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("volume_hush_gesture"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("system_navigation_keys_enabled"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_short_press"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_double_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_triple_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_long_press"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_long_press_duration_ms"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_very_long_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("stem_primary_button_short_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("stem_primary_button_double_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("stem_primary_button_triple_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("stem_primary_button_long_press"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("key_chord_power_volume_up"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("power_button_suppression_delay_after_gesture_wake"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("stylus_buttons_enabled"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("nav_bar_kids_mode"), false, settingsObserver, -1);
        updateSettings(null);
        this.mModifierShortcutManager =
                new ModifierShortcutManager(this.mContext, this.mHandler, this.mExt);
        this.mUiMode =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.config_dreamCloseAnimationDuration);
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mHomeIntent = intent;
        intent.addCategory("android.intent.category.HOME");
        this.mHomeIntent.addFlags(270532608);
        this.mEnableCarDockHomeCapture =
                this.mContext.getResources().getBoolean(R.bool.config_enableGeofenceOverlay);
        Intent intent2 = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mCarDockIntent = intent2;
        intent2.addCategory("android.intent.category.CAR_DOCK");
        this.mCarDockIntent.addFlags(270532608);
        Intent intent3 = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mDeskDockIntent = intent3;
        intent3.addCategory("android.intent.category.DESK_DOCK");
        this.mDeskDockIntent.addFlags(270532608);
        Intent intent4 = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mVrHeadsetHomeIntent = intent4;
        intent4.addCategory("android.intent.category.VR_HOME");
        this.mVrHeadsetHomeIntent.addFlags(270532608);
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mBroadcastWakeLock =
                powerManager.newWakeLock(1, "PhoneWindowManager.mBroadcastWakeLock");
        PowerManager.WakeLock newWakeLock =
                this.mPowerManager.newWakeLock(1, "PhoneWindowManager.mPowerKeyWakeLock");
        this.mPowerKeyWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mEnableBugReportKeyboardShortcut = "1".equals(SystemProperties.get("ro.debuggable"));
        this.mLidKeyboardAccessibility =
                this.mContext.getResources().getInteger(R.integer.config_multiuserMaximumUsers);
        this.mLidNavigationAccessibility =
                this.mContext.getResources().getInteger(R.integer.config_navBarInteractionMode);
        this.mGoToSleepOnButtonPressTheaterMode =
                this.mContext.getResources().getBoolean(R.bool.config_ignoreUdfpsVote);
        this.mSupportLongPressPowerWhenNonInteractive =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_supportsSeamlessRefreshRateSwitching);
        this.mSupportShortPressPowerWhenDefaultDisplayOn =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_suspendWhenScreenOffDueToProximity);
        this.mLongPressOnBackBehavior =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.config_networkMeteredMultipathPreference);
        this.mLongPressOnPowerBehavior =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.config_networkPolicyDefaultWarning);
        this.mLongPressOnPowerAssistantTimeoutMs =
                this.mContext.getResources().getInteger(R.integer.config_networkWakeupPacketMark);
        this.mVeryLongPressOnPowerBehavior =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.device_idle_quick_doze_delay_to_ms);
        this.mPowerDoublePressTargetActivity =
                ComponentName.unflattenFromString(
                        this.mContext.getResources().getString(R.string.done_label));
        if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
            this.mExt.mQuintuplePressOnPowerBehavior =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_shutdownBatteryTemperature);
        }
        this.mPrimaryShortPressTargetActivity =
                ComponentName.unflattenFromString(
                        this.mContext
                                .getResources()
                                .getString(R.string.ext_media_nomedia_notification_title));
        this.mShortPressOnSleepBehavior =
                this.mContext.getResources().getInteger(R.integer.device_idle_idle_factor);
        this.mSilenceRingerOnSleepKey =
                this.mContext.getResources().getBoolean(R.bool.config_startDreamImmediatelyOnDock);
        this.mAllowStartActivityForLongPressOnPowerDuringSetup =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_allowTheaterModeWakeFromGesture);
        this.mUseTvRouting = AudioSystem.getPlatformType(this.mContext) == 2;
        this.mHandleVolumeKeysInWM =
                this.mContext.getResources().getBoolean(R.bool.config_isCompatFakeFocusEnabled);
        this.mWakeUpToLastStateTimeout =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.leanback_setup_alpha_activity_in_bkg_duration);
        this.mSearchKeyBehavior =
                this.mContext.getResources().getInteger(R.integer.db_wal_truncate_size);
        this.mSearchKeyTargetActivity =
                ComponentName.unflattenFromString(
                        this.mContext
                                .getResources()
                                .getString(R.string.face_acquired_insufficient));
        readConfigurationDependentBehaviors();
        Context context = this.mContext;
        WindowManagerInternal windowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        DisplayManagerInternal displayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        String string = context.getResources().getString(R.string.duration_hours_shortest);
        this.mDisplayFoldController =
                new DisplayFoldController(
                        context,
                        windowManagerInternal,
                        displayManagerInternal,
                        (string == null || string.isEmpty())
                                ? new Rect()
                                : Rect.unflattenFromString(string),
                        DisplayThread.getHandler());
        this.mAccessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
        intentFilter.addAction(UiModeManager.ACTION_EXIT_CAR_MODE);
        intentFilter.addAction(UiModeManager.ACTION_ENTER_DESK_MODE);
        intentFilter.addAction(UiModeManager.ACTION_EXIT_DESK_MODE);
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        Intent registerReceiver = this.mContext.registerReceiver(this.mDockReceiver, intentFilter);
        if (registerReceiver != null) {
            this.mDefaultDisplayPolicy.mDockMode =
                    registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", 0);
        }
        this.mContext.registerReceiver(
                this.mMultiuserReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"));
        this.mVibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        this.mHapticFeedbackVibrationProvider =
                new HapticFeedbackVibrationProvider(this.mContext.getResources(), this.mVibrator);
        this.mGlobalKeyManager = new GlobalKeyManager(this.mContext);
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            initializeHdmiStateInternal();
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
            if (!this.mPowerManager.isInteractive()) {
                startedGoingToSleep(0, 2);
                finishedGoingToSleep(0, 2);
            }
            this.mWindowManagerInternal.registerAppTransitionListener(
                    new WindowManagerInternal
                            .AppTransitionListener() { // from class:
                                                       // com.android.server.policy.PhoneWindowManager.5
                        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                        public final void onAppTransitionCancelledLocked(boolean z3) {
                            PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                            phoneWindowManager.applyKeyguardOcclusionChange();
                            if (z3) {
                                if (PhoneWindowManager.DEBUG_KEYGUARD) {
                                    Slog.d("WindowManager", "Starting keyguard exit animation");
                                }
                                long uptimeMillis = SystemClock.uptimeMillis();
                                if (phoneWindowManager.mKeyguardDelegate != null) {
                                    if (PhoneWindowManager.DEBUG_KEYGUARD) {
                                        Slog.d("WindowManager", "PWM.startKeyguardExitAnimation");
                                    }
                                    KeyguardServiceWrapper keyguardServiceWrapper =
                                            phoneWindowManager.mKeyguardDelegate.mKeyguardService;
                                    if (keyguardServiceWrapper != null) {
                                        keyguardServiceWrapper.startKeyguardExitAnimation(
                                                uptimeMillis, 0L);
                                    }
                                }
                            }
                            synchronized (PhoneWindowManager.this.mLock) {
                                PhoneWindowManager.this.mLockAfterDreamingTransitionFinished =
                                        false;
                            }
                        }

                        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                        public final void onAppTransitionFinishedLocked(IBinder iBinder) {
                            synchronized (PhoneWindowManager.this.mLock) {
                                try {
                                    PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                                    if (phoneWindowManager.mDreamManagerInternal == null) {
                                        phoneWindowManager.mDreamManagerInternal =
                                                (DreamManagerInternal)
                                                        LocalServices.getService(
                                                                DreamManagerInternal.class);
                                    }
                                    DreamManagerInternal dreamManagerInternal =
                                            phoneWindowManager.mDreamManagerInternal;
                                    if (dreamManagerInternal != null
                                            && dreamManagerInternal.isDreaming()) {
                                        PhoneWindowManager phoneWindowManager2 =
                                                PhoneWindowManager.this;
                                        if (phoneWindowManager2
                                                .mLockAfterDreamingTransitionFinished) {
                                            phoneWindowManager2.lockNow(null);
                                        }
                                    }
                                    PhoneWindowManager.this.mLockAfterDreamingTransitionFinished =
                                            false;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }

                        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
                        public final void onAppTransitionStartingLocked(long j) {
                            PhoneWindowManager.this.getClass();
                        }
                    });
            this.mKeyguardDrawnTimeout =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_minNumVisibleRecentTasks);
            this.mKeyguardDelegate =
                    new KeyguardServiceDelegate(injector.mContext, new AnonymousClass1(injector));
            this.mTalkbackShortcutController = new TalkbackShortcutController(injector.mContext);
            this.mWindowWakeUpPolicy =
                    new WindowWakeUpPolicy(injector.mContext, Clock.SYSTEM_CLOCK);
            initKeyCombinationRules();
            Looper myLooper = Looper.myLooper();
            Context context2 = this.mContext;
            KeyCustomizationManager keyCustomizationManager = this.mExt.mKeyCustomizationPolicy;
            SingleKeyGestureDetector singleKeyGestureDetector =
                    new SingleKeyGestureDetector(myLooper);
            SingleKeyGestureDetector.sDefaultLongPressTimeout =
                    context2.getResources().getInteger(R.integer.config_maxResolverActivityColumns);
            SingleKeyGestureDetector.sDefaultVeryLongPressTimeout =
                    context2.getResources().getInteger(R.integer.device_idle_sensing_to_ms);
            SingleKeyGestureDetector.mKeyCustomizationPolicy = keyCustomizationManager;
            SingleKeyGestureDetector.sDefaultMultiPressTimeout =
                    SingleKeyGestureDetector.MULTI_PRESS_TIMEOUT;
            this.mSingleKeyGestureDetector = singleKeyGestureDetector;
            singleKeyGestureDetector.addRule(new PowerKeyRule(this, 0));
            int integer6 =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.device_idle_idle_pending_factor);
            int integer7 =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_networkWakeupPacketMask);
            if ((this.mTriplePressOnStemPrimaryBehavior == 1
                            && this.mTalkbackShortcutController.isTalkBackShortcutGestureEnabled())
                    || this.mDoublePressOnStemPrimaryBehavior != 0
                    || integer7 != 0
                    || integer6 != 0) {
                this.mSingleKeyGestureDetector.addRule(new PowerKeyRule(this, 1));
            }
            this.mButtonOverridePermissionChecker = new ButtonOverridePermissionChecker();
            this.mSideFpsEventHandler =
                    new SideFpsEventHandler(
                            this.mContext,
                            this.mHandler,
                            this.mPowerManager,
                            new SideFpsEventHandler$$ExternalSyntheticLambda0());
        } catch (Throwable th) {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
            throw th;
        }
    }

    public final void initKeyCombinationRules() {
        this.mKeyCombinationManager =
                new KeyCombinationManager(this.mHandler, this.mInputManagerInternal);
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_enableWifiDisplay);
        final PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        PhoneWindowManager phoneWindowManager = phoneWindowManagerExt.mPolicy;
        final int i = 0;
        phoneWindowManager
                .getKeyCombinationManager()
                .addRule(
                        new KeyCombinationManager.TwoKeysCombinationRule(
                                phoneWindowManagerExt, i) { // from class:
                            // com.android.server.policy.PhoneWindowManagerExt.5
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ PhoneWindowManagerExt this$0;

                            public AnonymousClass5(
                                    final PhoneWindowManagerExt phoneWindowManagerExt2,
                                    final int i2) {
                                super(24, 26);
                                this.$r8$classId = i2;
                                switch (i2) {
                                    case 1:
                                        this.this$0 = phoneWindowManagerExt2;
                                        super(
                                                FrameworkStatsLog
                                                        .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED,
                                                4);
                                        break;
                                    default:
                                        this.this$0 = phoneWindowManagerExt2;
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void cancel() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                                        if (phoneWindowManagerExt2
                                                .mIsAccessibilityShortcutVolupPower) {
                                            phoneWindowManagerExt2
                                                            .mIsAccessibilityShortcutVolupPower =
                                                    false;
                                            if (phoneWindowManagerExt2.mAppOpsManager == null) {
                                                phoneWindowManagerExt2.mAppOpsManager =
                                                        (AppOpsManager)
                                                                phoneWindowManagerExt2.mContext
                                                                        .getSystemService(
                                                                                AppOpsManager
                                                                                        .class);
                                            }
                                            String opPackageName =
                                                    phoneWindowManagerExt2.mContext
                                                            .getOpPackageName();
                                            int checkOpNoThrow =
                                                    phoneWindowManagerExt2.mAppOpsManager
                                                            .checkOpNoThrow(
                                                                    23, 1000, opPackageName);
                                            NetworkScoreService$$ExternalSyntheticOutline0.m(
                                                    checkOpNoThrow,
                                                    "setAppOpsPermissionIfNeeded code=23 p_name=",
                                                    opPackageName,
                                                    " mode=",
                                                    "PhoneWindowManagerExt");
                                            if (checkOpNoThrow != 0) {
                                                phoneWindowManagerExt2.mAppOpsManager.setMode(
                                                        23, 1000, opPackageName, 0);
                                            }
                                            Log.d(
                                                    "PhoneWindowManagerExt",
                                                    "start Accessibility Shortcut Volup + Power");
                                            if (phoneWindowManagerExt2
                                                            .mAccessibilityDirectAccessController
                                                    == null) {
                                                phoneWindowManagerExt2
                                                                .mAccessibilityDirectAccessController =
                                                        new AccessibilityDirectAccessController(
                                                                phoneWindowManagerExt2.mContext);
                                            }
                                            phoneWindowManagerExt2
                                                    .mAccessibilityDirectAccessController
                                                    .performAccessibilityDirectAccess();
                                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                                PhoneWindowManagerExt.saLogForBasic("HWB1008");
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        PhoneWindowManagerExt phoneWindowManagerExt3 = this.this$0;
                                        phoneWindowManagerExt3.mHandler.removeCallbacks(
                                                phoneWindowManagerExt3
                                                        .mStopLockTaskModePinnedChordLongPress);
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void execute() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        this.this$0.mIsAccessibilityShortcutVolupPower = true;
                                        break;
                                    default:
                                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                                        boolean z2 = false;
                                        boolean z3 = phoneWindowManagerExt2.mLockTaskModeState != 0;
                                        PhoneWindowManager phoneWindowManager2 =
                                                phoneWindowManagerExt2.mPolicy;
                                        if (phoneWindowManager2.mAccessibilityManager.isEnabled()
                                                && phoneWindowManager2.mAccessibilityManager
                                                        .isTouchExplorationEnabled()) {
                                            z2 = true;
                                        }
                                        boolean z4 =
                                                phoneWindowManagerExt2.mIsInteractionControlEnabled;
                                        if (z3 && !z2 && !z4) {
                                            PolicyExtHandler policyExtHandler =
                                                    phoneWindowManagerExt2.mHandler;
                                            PhoneWindowManagerExt$$ExternalSyntheticLambda8
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8 =
                                                            phoneWindowManagerExt2
                                                                    .mStopLockTaskModePinnedChordLongPress;
                                            policyExtHandler.removeCallbacks(
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8);
                                            phoneWindowManagerExt2.mHandler.postDelayed(
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8,
                                                    500L);
                                            break;
                                        } else {
                                            RCPManagerService$$ExternalSyntheticOutline0.m(
                                                    "PhoneWindowManagerExt",
                                                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "Can not stop"
                                                                        + " SystemLockTaskMode."
                                                                        + " lockTaskModeEnabled=",
                                                                    z3,
                                                                    " touchExplorationEnabled=",
                                                                    z2,
                                                                    " interactionControlEnabled="),
                                                    z4);
                                            break;
                                        }
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public boolean preCondition() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        return true;
                                    default:
                                        return super.preCondition();
                                }
                            }
                        });
        final int i2 = 1;
        phoneWindowManager
                .getKeyCombinationManager()
                .addRule(
                        new KeyCombinationManager.TwoKeysCombinationRule(
                                phoneWindowManagerExt2, i2) { // from class:
                            // com.android.server.policy.PhoneWindowManagerExt.5
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ PhoneWindowManagerExt this$0;

                            public AnonymousClass5(
                                    final PhoneWindowManagerExt phoneWindowManagerExt2,
                                    final int i22) {
                                super(24, 26);
                                this.$r8$classId = i22;
                                switch (i22) {
                                    case 1:
                                        this.this$0 = phoneWindowManagerExt2;
                                        super(
                                                FrameworkStatsLog
                                                        .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED,
                                                4);
                                        break;
                                    default:
                                        this.this$0 = phoneWindowManagerExt2;
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void cancel() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                                        if (phoneWindowManagerExt2
                                                .mIsAccessibilityShortcutVolupPower) {
                                            phoneWindowManagerExt2
                                                            .mIsAccessibilityShortcutVolupPower =
                                                    false;
                                            if (phoneWindowManagerExt2.mAppOpsManager == null) {
                                                phoneWindowManagerExt2.mAppOpsManager =
                                                        (AppOpsManager)
                                                                phoneWindowManagerExt2.mContext
                                                                        .getSystemService(
                                                                                AppOpsManager
                                                                                        .class);
                                            }
                                            String opPackageName =
                                                    phoneWindowManagerExt2.mContext
                                                            .getOpPackageName();
                                            int checkOpNoThrow =
                                                    phoneWindowManagerExt2.mAppOpsManager
                                                            .checkOpNoThrow(
                                                                    23, 1000, opPackageName);
                                            NetworkScoreService$$ExternalSyntheticOutline0.m(
                                                    checkOpNoThrow,
                                                    "setAppOpsPermissionIfNeeded code=23 p_name=",
                                                    opPackageName,
                                                    " mode=",
                                                    "PhoneWindowManagerExt");
                                            if (checkOpNoThrow != 0) {
                                                phoneWindowManagerExt2.mAppOpsManager.setMode(
                                                        23, 1000, opPackageName, 0);
                                            }
                                            Log.d(
                                                    "PhoneWindowManagerExt",
                                                    "start Accessibility Shortcut Volup + Power");
                                            if (phoneWindowManagerExt2
                                                            .mAccessibilityDirectAccessController
                                                    == null) {
                                                phoneWindowManagerExt2
                                                                .mAccessibilityDirectAccessController =
                                                        new AccessibilityDirectAccessController(
                                                                phoneWindowManagerExt2.mContext);
                                            }
                                            phoneWindowManagerExt2
                                                    .mAccessibilityDirectAccessController
                                                    .performAccessibilityDirectAccess();
                                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                                PhoneWindowManagerExt.saLogForBasic("HWB1008");
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        PhoneWindowManagerExt phoneWindowManagerExt3 = this.this$0;
                                        phoneWindowManagerExt3.mHandler.removeCallbacks(
                                                phoneWindowManagerExt3
                                                        .mStopLockTaskModePinnedChordLongPress);
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void execute() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        this.this$0.mIsAccessibilityShortcutVolupPower = true;
                                        break;
                                    default:
                                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                                        boolean z2 = false;
                                        boolean z3 = phoneWindowManagerExt2.mLockTaskModeState != 0;
                                        PhoneWindowManager phoneWindowManager2 =
                                                phoneWindowManagerExt2.mPolicy;
                                        if (phoneWindowManager2.mAccessibilityManager.isEnabled()
                                                && phoneWindowManager2.mAccessibilityManager
                                                        .isTouchExplorationEnabled()) {
                                            z2 = true;
                                        }
                                        boolean z4 =
                                                phoneWindowManagerExt2.mIsInteractionControlEnabled;
                                        if (z3 && !z2 && !z4) {
                                            PolicyExtHandler policyExtHandler =
                                                    phoneWindowManagerExt2.mHandler;
                                            PhoneWindowManagerExt$$ExternalSyntheticLambda8
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8 =
                                                            phoneWindowManagerExt2
                                                                    .mStopLockTaskModePinnedChordLongPress;
                                            policyExtHandler.removeCallbacks(
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8);
                                            phoneWindowManagerExt2.mHandler.postDelayed(
                                                    phoneWindowManagerExt$$ExternalSyntheticLambda8,
                                                    500L);
                                            break;
                                        } else {
                                            RCPManagerService$$ExternalSyntheticOutline0.m(
                                                    "PhoneWindowManagerExt",
                                                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "Can not stop"
                                                                        + " SystemLockTaskMode."
                                                                        + " lockTaskModeEnabled=",
                                                                    z3,
                                                                    " touchExplorationEnabled=",
                                                                    z2,
                                                                    " interactionControlEnabled="),
                                                    z4);
                                            break;
                                        }
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public boolean preCondition() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        return true;
                                    default:
                                        return super.preCondition();
                                }
                            }
                        });
        if (z) {
            this.mKeyCombinationManager.addRule(
                    new KeyCombinationManager.TwoKeysCombinationRule(
                            this, 3) { // from class: com.android.server.policy.PhoneWindowManager.7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ PhoneWindowManager this$0;

                        {
                            super(26, 264);
                            this.$r8$classId = r2;
                            switch (r2) {
                                case 1:
                                    this.this$0 = this;
                                    super(4, 20);
                                    break;
                                case 2:
                                    this.this$0 = this;
                                    super(23, 4);
                                    break;
                                case 3:
                                    this.this$0 = this;
                                    super(25, 26);
                                    break;
                                case 4:
                                    this.this$0 = this;
                                    super(25, 24);
                                    break;
                                default:
                                    this.this$0 = this;
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void cancel() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mHandler.removeMessages(16);
                                    break;
                                case 1:
                                    this.this$0.mHandler.removeMessages(19);
                                    break;
                                case 2:
                                    this.this$0.mHandler.removeMessages(18);
                                    break;
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    if (z2) {
                                        phoneWindowManager2.mHandler.removeMessages(10);
                                    }
                                    WmScreenshotController wmScreenshotController =
                                            phoneWindowManager2
                                                    .mExt
                                                    .mWindowManagerFuncs
                                                    .mScreenshotController;
                                    wmScreenshotController.mHandler.removeCallbacks(
                                            wmScreenshotController.mTakeScreenshotRunnable);
                                    break;
                                default:
                                    this.this$0.mHandler.removeMessages(17);
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public void cancel(KeyEvent keyEvent, boolean z2) {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z3 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    if (z3) {
                                        PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                this.this$0.mExt;
                                        phoneWindowManagerExt2.getClass();
                                        if (z3) {
                                            if (!phoneWindowManagerExt2.mIsScreenshotTriggered) {
                                                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                                                break;
                                            } else {
                                                long[] jArr = phoneWindowManagerExt2.mKeyUpTime;
                                                if (z2) {
                                                    int keyCode = keyEvent.getKeyCode();
                                                    if (keyCode == 26) {
                                                        jArr[0] = keyEvent.getEventTime();
                                                    } else if (keyCode == 25) {
                                                        jArr[1] = keyEvent.getEventTime();
                                                    }
                                                    long j = jArr[0];
                                                    long j2 = jArr[1];
                                                    if (j != 0 && j2 != 0) {
                                                        String str2 =
                                                                KeyCustomizationConstants
                                                                        .VOLD_DECRYPT;
                                                        long uptimeMillis =
                                                                SystemClock.uptimeMillis();
                                                        long j3 =
                                                                uptimeMillis
                                                                        - phoneWindowManagerExt2
                                                                                .mScreenshotTriggeredTime;
                                                        if (uptimeMillis <= j + 150
                                                                && uptimeMillis <= 150 + j2
                                                                && j3 < 1000) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "take a screenshot, this is"
                                                                        + " triggered by"
                                                                        + " keyCombination");
                                                            phoneWindowManagerExt2
                                                                    .mWindowManagerFuncs
                                                                    .sendTakeScreenshotRunnable(
                                                                            1,
                                                                            phoneWindowManagerExt2
                                                                                    .getDisplayId(
                                                                                            null));
                                                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                                                PhoneWindowManagerExt.saLogForBasic(
                                                                        "HWB1007");
                                                            }
                                                        } else if (!phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "Screenshot by the key"
                                                                        + " combination is not"
                                                                        + " triggered, time="
                                                                            + Math.abs(j - j2));
                                                        }
                                                    } else if (!phoneWindowManagerExt2
                                                            .mGlobalActionsByKeyCombnation) {
                                                        RCPManagerService$$ExternalSyntheticOutline0
                                                                .m(
                                                                        new StringBuilder(
                                                                                "Waiting for "),
                                                                        j == 0
                                                                                ? "power"
                                                                                : "volume down",
                                                                        " key up event to take a"
                                                                            + " screenshot",
                                                                        "PhoneWindowManagerExt");
                                                        break;
                                                    }
                                                }
                                                if (phoneWindowManagerExt2
                                                        .mGlobalActionsByKeyCombnation) {
                                                    Log.d(
                                                            "PhoneWindowManagerExt",
                                                            "The key combination long press event"
                                                                + " was consumed by global action");
                                                }
                                                jArr[0] = 0;
                                                jArr[1] = 0;
                                                phoneWindowManagerExt2.mIsScreenshotTriggered =
                                                        false;
                                                phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                        0L;
                                                phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation =
                                                        false;
                                                break;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void execute() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mPowerKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    long j =
                                            DeviceConfig.getLong(
                                                    "systemui",
                                                    "screenshot_keychord_delay",
                                                    ViewConfiguration.get(
                                                                    phoneWindowManager2.mContext)
                                                            .getScreenshotChordKeyTimeout());
                                    if (phoneWindowManager2.mKeyguardDelegate.isShowing()) {
                                        j = (long) (j * 2.5f);
                                    }
                                    phoneWindowManager2.mHandler.removeMessages(16);
                                    PolicyHandler policyHandler = phoneWindowManager2.mHandler;
                                    policyHandler.sendMessageDelayed(
                                            policyHandler.obtainMessage(16, 1, 0), j);
                                    break;
                                case 1:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager3 = this.this$0;
                                    phoneWindowManager3.mHandler.removeMessages(19);
                                    Message obtain =
                                            Message.obtain(phoneWindowManager3.mHandler, 19);
                                    obtain.setAsynchronous(true);
                                    phoneWindowManager3.mHandler.sendMessageDelayed(
                                            obtain,
                                            phoneWindowManager3.getAccessibilityShortcutTimeout());
                                    break;
                                case 2:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager4 = this.this$0;
                                    phoneWindowManager4.mHandler.removeMessages(18);
                                    Message obtain2 =
                                            Message.obtain(phoneWindowManager4.mHandler, 18);
                                    obtain2.setAsynchronous(true);
                                    phoneWindowManager4.mHandler.sendMessageDelayed(obtain2, 1000L);
                                    break;
                                case 3:
                                    this.this$0.mPowerKeyHandled = true;
                                    if (InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                                        PhoneWindowManager phoneWindowManager5 = this.this$0;
                                        phoneWindowManager5.getClass();
                                        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
                                            Log.d(
                                                    "WindowManager",
                                                    "Global Action is canceled because of factory"
                                                        + " mode");
                                        } else {
                                            phoneWindowManager5.mHandler.removeMessages(10);
                                            phoneWindowManager5.mHandler.sendMessageDelayed(
                                                    phoneWindowManager5.mHandler.obtainMessage(
                                                            10, 1, 0),
                                                    1000L);
                                        }
                                    }
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    if (phoneWindowManagerExt2.mScreenshotEnabled) {
                                        if (!z2) {
                                            phoneWindowManagerExt2.mWindowManagerFuncs
                                                    .sendTakeScreenshotRunnable(
                                                            1,
                                                            phoneWindowManagerExt2.getDisplayId(
                                                                    null));
                                            break;
                                        } else {
                                            phoneWindowManagerExt2.mIsScreenshotTriggered = true;
                                            phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                    SystemClock.uptimeMillis();
                                            Log.d(
                                                    "PhoneWindowManagerExt",
                                                    "interceptKeyCombinationScreenshotChord"
                                                        + " triggered");
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    PhoneWindowManager phoneWindowManager6 = this.this$0;
                                    phoneWindowManager6.mHandler.removeMessages(17);
                                    PolicyHandler policyHandler2 = phoneWindowManager6.mHandler;
                                    policyHandler2.sendMessageDelayed(
                                            policyHandler2.obtainMessage(17),
                                            phoneWindowManager6.getAccessibilityShortcutTimeout());
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public long getKeyInterceptDelayMs() {
                            switch (this.$r8$classId) {
                                case 1:
                                    return 0L;
                                case 2:
                                    return 0L;
                                default:
                                    return super.getKeyInterceptDelayMs();
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public boolean preCondition() {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                            "setScreenshotEnabled, screenOnFully=",
                                            "PhoneWindowManagerExt",
                                            phoneWindowManagerExt2
                                                    .mPolicy
                                                    .mDefaultDisplayPolicy
                                                    .mScreenOnFully);
                                    phoneWindowManagerExt2.mScreenshotEnabled = true;
                                    return true;
                                case 4:
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    return phoneWindowManager2.mAccessibilityShortcutController
                                            .isAccessibilityShortcutAvailable(
                                                    phoneWindowManager2.keyguardOn());
                                default:
                                    return super.preCondition();
                            }
                        }
                    });
            if (this.mHasFeatureWatch) {
                this.mKeyCombinationManager.addRule(
                        new KeyCombinationManager.TwoKeysCombinationRule(
                                this,
                                0) { // from class: com.android.server.policy.PhoneWindowManager.7
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ PhoneWindowManager this$0;

                            {
                                super(26, 264);
                                this.$r8$classId = r2;
                                switch (r2) {
                                    case 1:
                                        this.this$0 = this;
                                        super(4, 20);
                                        break;
                                    case 2:
                                        this.this$0 = this;
                                        super(23, 4);
                                        break;
                                    case 3:
                                        this.this$0 = this;
                                        super(25, 26);
                                        break;
                                    case 4:
                                        this.this$0 = this;
                                        super(25, 24);
                                        break;
                                    default:
                                        this.this$0 = this;
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void cancel() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        this.this$0.mHandler.removeMessages(16);
                                        break;
                                    case 1:
                                        this.this$0.mHandler.removeMessages(19);
                                        break;
                                    case 2:
                                        this.this$0.mHandler.removeMessages(18);
                                        break;
                                    case 3:
                                        boolean z2 = InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION;
                                        PhoneWindowManager phoneWindowManager2 = this.this$0;
                                        if (z2) {
                                            phoneWindowManager2.mHandler.removeMessages(10);
                                        }
                                        WmScreenshotController wmScreenshotController =
                                                phoneWindowManager2
                                                        .mExt
                                                        .mWindowManagerFuncs
                                                        .mScreenshotController;
                                        wmScreenshotController.mHandler.removeCallbacks(
                                                wmScreenshotController.mTakeScreenshotRunnable);
                                        break;
                                    default:
                                        this.this$0.mHandler.removeMessages(17);
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public void cancel(KeyEvent keyEvent, boolean z2) {
                                switch (this.$r8$classId) {
                                    case 3:
                                        boolean z3 =
                                                InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                        if (z3) {
                                            PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                    this.this$0.mExt;
                                            phoneWindowManagerExt2.getClass();
                                            if (z3) {
                                                if (!phoneWindowManagerExt2
                                                        .mIsScreenshotTriggered) {
                                                    String str =
                                                            KeyCustomizationConstants.VOLD_DECRYPT;
                                                    break;
                                                } else {
                                                    long[] jArr = phoneWindowManagerExt2.mKeyUpTime;
                                                    if (z2) {
                                                        int keyCode = keyEvent.getKeyCode();
                                                        if (keyCode == 26) {
                                                            jArr[0] = keyEvent.getEventTime();
                                                        } else if (keyCode == 25) {
                                                            jArr[1] = keyEvent.getEventTime();
                                                        }
                                                        long j = jArr[0];
                                                        long j2 = jArr[1];
                                                        if (j != 0 && j2 != 0) {
                                                            String str2 =
                                                                    KeyCustomizationConstants
                                                                            .VOLD_DECRYPT;
                                                            long uptimeMillis =
                                                                    SystemClock.uptimeMillis();
                                                            long j3 =
                                                                    uptimeMillis
                                                                            - phoneWindowManagerExt2
                                                                                    .mScreenshotTriggeredTime;
                                                            if (uptimeMillis <= j + 150
                                                                    && uptimeMillis <= 150 + j2
                                                                    && j3 < 1000) {
                                                                Log.d(
                                                                        "PhoneWindowManagerExt",
                                                                        "take a screenshot, this is"
                                                                            + " triggered by"
                                                                            + " keyCombination");
                                                                phoneWindowManagerExt2
                                                                        .mWindowManagerFuncs
                                                                        .sendTakeScreenshotRunnable(
                                                                                1,
                                                                                phoneWindowManagerExt2
                                                                                        .getDisplayId(
                                                                                                null));
                                                                if (InputRune.PWM_KEY_SA_LOGGING) {
                                                                    PhoneWindowManagerExt
                                                                            .saLogForBasic(
                                                                                    "HWB1007");
                                                                }
                                                            } else if (!phoneWindowManagerExt2
                                                                    .mGlobalActionsByKeyCombnation) {
                                                                Log.d(
                                                                        "PhoneWindowManagerExt",
                                                                        "Screenshot by the key"
                                                                            + " combination is not"
                                                                            + " triggered, time="
                                                                                + Math.abs(j - j2));
                                                            }
                                                        } else if (!phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation) {
                                                            RCPManagerService$$ExternalSyntheticOutline0
                                                                    .m(
                                                                            new StringBuilder(
                                                                                    "Waiting for "),
                                                                            j == 0
                                                                                    ? "power"
                                                                                    : "volume down",
                                                                            " key up event to take"
                                                                                + " a screenshot",
                                                                            "PhoneWindowManagerExt");
                                                            break;
                                                        }
                                                    }
                                                    if (phoneWindowManagerExt2
                                                            .mGlobalActionsByKeyCombnation) {
                                                        Log.d(
                                                                "PhoneWindowManagerExt",
                                                                "The key combination long press"
                                                                    + " event was consumed by"
                                                                    + " global action");
                                                    }
                                                    jArr[0] = 0;
                                                    jArr[1] = 0;
                                                    phoneWindowManagerExt2.mIsScreenshotTriggered =
                                                            false;
                                                    phoneWindowManagerExt2
                                                                    .mScreenshotTriggeredTime =
                                                            0L;
                                                    phoneWindowManagerExt2
                                                                    .mGlobalActionsByKeyCombnation =
                                                            false;
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public final void execute() {
                                switch (this.$r8$classId) {
                                    case 0:
                                        this.this$0.mPowerKeyHandled = true;
                                        PhoneWindowManager phoneWindowManager2 = this.this$0;
                                        long j =
                                                DeviceConfig.getLong(
                                                        "systemui",
                                                        "screenshot_keychord_delay",
                                                        ViewConfiguration.get(
                                                                        phoneWindowManager2
                                                                                .mContext)
                                                                .getScreenshotChordKeyTimeout());
                                        if (phoneWindowManager2.mKeyguardDelegate.isShowing()) {
                                            j = (long) (j * 2.5f);
                                        }
                                        phoneWindowManager2.mHandler.removeMessages(16);
                                        PolicyHandler policyHandler = phoneWindowManager2.mHandler;
                                        policyHandler.sendMessageDelayed(
                                                policyHandler.obtainMessage(16, 1, 0), j);
                                        break;
                                    case 1:
                                        this.this$0.mBackKeyHandled = true;
                                        PhoneWindowManager phoneWindowManager3 = this.this$0;
                                        phoneWindowManager3.mHandler.removeMessages(19);
                                        Message obtain =
                                                Message.obtain(phoneWindowManager3.mHandler, 19);
                                        obtain.setAsynchronous(true);
                                        phoneWindowManager3.mHandler.sendMessageDelayed(
                                                obtain,
                                                phoneWindowManager3
                                                        .getAccessibilityShortcutTimeout());
                                        break;
                                    case 2:
                                        this.this$0.mBackKeyHandled = true;
                                        PhoneWindowManager phoneWindowManager4 = this.this$0;
                                        phoneWindowManager4.mHandler.removeMessages(18);
                                        Message obtain2 =
                                                Message.obtain(phoneWindowManager4.mHandler, 18);
                                        obtain2.setAsynchronous(true);
                                        phoneWindowManager4.mHandler.sendMessageDelayed(
                                                obtain2, 1000L);
                                        break;
                                    case 3:
                                        this.this$0.mPowerKeyHandled = true;
                                        if (InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                                            PhoneWindowManager phoneWindowManager5 = this.this$0;
                                            phoneWindowManager5.getClass();
                                            if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
                                                Log.d(
                                                        "WindowManager",
                                                        "Global Action is canceled because of"
                                                            + " factory mode");
                                            } else {
                                                phoneWindowManager5.mHandler.removeMessages(10);
                                                phoneWindowManager5.mHandler.sendMessageDelayed(
                                                        phoneWindowManager5.mHandler.obtainMessage(
                                                                10, 1, 0),
                                                        1000L);
                                            }
                                        }
                                        boolean z2 =
                                                InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                        PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                this.this$0.mExt;
                                        if (phoneWindowManagerExt2.mScreenshotEnabled) {
                                            if (!z2) {
                                                phoneWindowManagerExt2.mWindowManagerFuncs
                                                        .sendTakeScreenshotRunnable(
                                                                1,
                                                                phoneWindowManagerExt2.getDisplayId(
                                                                        null));
                                                break;
                                            } else {
                                                phoneWindowManagerExt2.mIsScreenshotTriggered =
                                                        true;
                                                phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                        SystemClock.uptimeMillis();
                                                Log.d(
                                                        "PhoneWindowManagerExt",
                                                        "interceptKeyCombinationScreenshotChord"
                                                            + " triggered");
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        PhoneWindowManager phoneWindowManager6 = this.this$0;
                                        phoneWindowManager6.mHandler.removeMessages(17);
                                        PolicyHandler policyHandler2 = phoneWindowManager6.mHandler;
                                        policyHandler2.sendMessageDelayed(
                                                policyHandler2.obtainMessage(17),
                                                phoneWindowManager6
                                                        .getAccessibilityShortcutTimeout());
                                        break;
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public long getKeyInterceptDelayMs() {
                                switch (this.$r8$classId) {
                                    case 1:
                                        return 0L;
                                    case 2:
                                        return 0L;
                                    default:
                                        return super.getKeyInterceptDelayMs();
                                }
                            }

                            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                            public boolean preCondition() {
                                switch (this.$r8$classId) {
                                    case 3:
                                        boolean z2 =
                                                InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                        PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                this.this$0.mExt;
                                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                                "setScreenshotEnabled, screenOnFully=",
                                                "PhoneWindowManagerExt",
                                                phoneWindowManagerExt2
                                                        .mPolicy
                                                        .mDefaultDisplayPolicy
                                                        .mScreenOnFully);
                                        phoneWindowManagerExt2.mScreenshotEnabled = true;
                                        return true;
                                    case 4:
                                        PhoneWindowManager phoneWindowManager2 = this.this$0;
                                        return phoneWindowManager2.mAccessibilityShortcutController
                                                .isAccessibilityShortcutAvailable(
                                                        phoneWindowManager2.keyguardOn());
                                    default:
                                        return super.preCondition();
                                }
                            }
                        });
            }
        }
        this.mKeyCombinationManager.addRule(
                new KeyCombinationManager.TwoKeysCombinationRule(
                        this, 4) { // from class: com.android.server.policy.PhoneWindowManager.7
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ PhoneWindowManager this$0;

                    {
                        super(26, 264);
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super(4, 20);
                                break;
                            case 2:
                                this.this$0 = this;
                                super(23, 4);
                                break;
                            case 3:
                                this.this$0 = this;
                                super(25, 26);
                                break;
                            case 4:
                                this.this$0 = this;
                                super(25, 24);
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public final void cancel() {
                        switch (this.$r8$classId) {
                            case 0:
                                this.this$0.mHandler.removeMessages(16);
                                break;
                            case 1:
                                this.this$0.mHandler.removeMessages(19);
                                break;
                            case 2:
                                this.this$0.mHandler.removeMessages(18);
                                break;
                            case 3:
                                boolean z2 = InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION;
                                PhoneWindowManager phoneWindowManager2 = this.this$0;
                                if (z2) {
                                    phoneWindowManager2.mHandler.removeMessages(10);
                                }
                                WmScreenshotController wmScreenshotController =
                                        phoneWindowManager2
                                                .mExt
                                                .mWindowManagerFuncs
                                                .mScreenshotController;
                                wmScreenshotController.mHandler.removeCallbacks(
                                        wmScreenshotController.mTakeScreenshotRunnable);
                                break;
                            default:
                                this.this$0.mHandler.removeMessages(17);
                                break;
                        }
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public void cancel(KeyEvent keyEvent, boolean z2) {
                        switch (this.$r8$classId) {
                            case 3:
                                boolean z3 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                if (z3) {
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    phoneWindowManagerExt2.getClass();
                                    if (z3) {
                                        if (!phoneWindowManagerExt2.mIsScreenshotTriggered) {
                                            String str = KeyCustomizationConstants.VOLD_DECRYPT;
                                            break;
                                        } else {
                                            long[] jArr = phoneWindowManagerExt2.mKeyUpTime;
                                            if (z2) {
                                                int keyCode = keyEvent.getKeyCode();
                                                if (keyCode == 26) {
                                                    jArr[0] = keyEvent.getEventTime();
                                                } else if (keyCode == 25) {
                                                    jArr[1] = keyEvent.getEventTime();
                                                }
                                                long j = jArr[0];
                                                long j2 = jArr[1];
                                                if (j != 0 && j2 != 0) {
                                                    String str2 =
                                                            KeyCustomizationConstants.VOLD_DECRYPT;
                                                    long uptimeMillis = SystemClock.uptimeMillis();
                                                    long j3 =
                                                            uptimeMillis
                                                                    - phoneWindowManagerExt2
                                                                            .mScreenshotTriggeredTime;
                                                    if (uptimeMillis <= j + 150
                                                            && uptimeMillis <= 150 + j2
                                                            && j3 < 1000) {
                                                        Log.d(
                                                                "PhoneWindowManagerExt",
                                                                "take a screenshot, this is"
                                                                    + " triggered by"
                                                                    + " keyCombination");
                                                        phoneWindowManagerExt2.mWindowManagerFuncs
                                                                .sendTakeScreenshotRunnable(
                                                                        1,
                                                                        phoneWindowManagerExt2
                                                                                .getDisplayId(
                                                                                        null));
                                                        if (InputRune.PWM_KEY_SA_LOGGING) {
                                                            PhoneWindowManagerExt.saLogForBasic(
                                                                    "HWB1007");
                                                        }
                                                    } else if (!phoneWindowManagerExt2
                                                            .mGlobalActionsByKeyCombnation) {
                                                        Log.d(
                                                                "PhoneWindowManagerExt",
                                                                "Screenshot by the key combination"
                                                                    + " is not triggered, time="
                                                                        + Math.abs(j - j2));
                                                    }
                                                } else if (!phoneWindowManagerExt2
                                                        .mGlobalActionsByKeyCombnation) {
                                                    RCPManagerService$$ExternalSyntheticOutline0.m(
                                                            new StringBuilder("Waiting for "),
                                                            j == 0 ? "power" : "volume down",
                                                            " key up event to take a screenshot",
                                                            "PhoneWindowManagerExt");
                                                    break;
                                                }
                                            }
                                            if (phoneWindowManagerExt2
                                                    .mGlobalActionsByKeyCombnation) {
                                                Log.d(
                                                        "PhoneWindowManagerExt",
                                                        "The key combination long press event was"
                                                            + " consumed by global action");
                                            }
                                            jArr[0] = 0;
                                            jArr[1] = 0;
                                            phoneWindowManagerExt2.mIsScreenshotTriggered = false;
                                            phoneWindowManagerExt2.mScreenshotTriggeredTime = 0L;
                                            phoneWindowManagerExt2.mGlobalActionsByKeyCombnation =
                                                    false;
                                            break;
                                        }
                                    }
                                }
                                break;
                        }
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public final void execute() {
                        switch (this.$r8$classId) {
                            case 0:
                                this.this$0.mPowerKeyHandled = true;
                                PhoneWindowManager phoneWindowManager2 = this.this$0;
                                long j =
                                        DeviceConfig.getLong(
                                                "systemui",
                                                "screenshot_keychord_delay",
                                                ViewConfiguration.get(phoneWindowManager2.mContext)
                                                        .getScreenshotChordKeyTimeout());
                                if (phoneWindowManager2.mKeyguardDelegate.isShowing()) {
                                    j = (long) (j * 2.5f);
                                }
                                phoneWindowManager2.mHandler.removeMessages(16);
                                PolicyHandler policyHandler = phoneWindowManager2.mHandler;
                                policyHandler.sendMessageDelayed(
                                        policyHandler.obtainMessage(16, 1, 0), j);
                                break;
                            case 1:
                                this.this$0.mBackKeyHandled = true;
                                PhoneWindowManager phoneWindowManager3 = this.this$0;
                                phoneWindowManager3.mHandler.removeMessages(19);
                                Message obtain = Message.obtain(phoneWindowManager3.mHandler, 19);
                                obtain.setAsynchronous(true);
                                phoneWindowManager3.mHandler.sendMessageDelayed(
                                        obtain,
                                        phoneWindowManager3.getAccessibilityShortcutTimeout());
                                break;
                            case 2:
                                this.this$0.mBackKeyHandled = true;
                                PhoneWindowManager phoneWindowManager4 = this.this$0;
                                phoneWindowManager4.mHandler.removeMessages(18);
                                Message obtain2 = Message.obtain(phoneWindowManager4.mHandler, 18);
                                obtain2.setAsynchronous(true);
                                phoneWindowManager4.mHandler.sendMessageDelayed(obtain2, 1000L);
                                break;
                            case 3:
                                this.this$0.mPowerKeyHandled = true;
                                if (InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                                    PhoneWindowManager phoneWindowManager5 = this.this$0;
                                    phoneWindowManager5.getClass();
                                    if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
                                        Log.d(
                                                "WindowManager",
                                                "Global Action is canceled because of factory"
                                                    + " mode");
                                    } else {
                                        phoneWindowManager5.mHandler.removeMessages(10);
                                        phoneWindowManager5.mHandler.sendMessageDelayed(
                                                phoneWindowManager5.mHandler.obtainMessage(
                                                        10, 1, 0),
                                                1000L);
                                    }
                                }
                                boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                if (phoneWindowManagerExt2.mScreenshotEnabled) {
                                    if (!z2) {
                                        phoneWindowManagerExt2.mWindowManagerFuncs
                                                .sendTakeScreenshotRunnable(
                                                        1,
                                                        phoneWindowManagerExt2.getDisplayId(null));
                                        break;
                                    } else {
                                        phoneWindowManagerExt2.mIsScreenshotTriggered = true;
                                        phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                SystemClock.uptimeMillis();
                                        Log.d(
                                                "PhoneWindowManagerExt",
                                                "interceptKeyCombinationScreenshotChord triggered");
                                        break;
                                    }
                                }
                                break;
                            default:
                                PhoneWindowManager phoneWindowManager6 = this.this$0;
                                phoneWindowManager6.mHandler.removeMessages(17);
                                PolicyHandler policyHandler2 = phoneWindowManager6.mHandler;
                                policyHandler2.sendMessageDelayed(
                                        policyHandler2.obtainMessage(17),
                                        phoneWindowManager6.getAccessibilityShortcutTimeout());
                                break;
                        }
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public long getKeyInterceptDelayMs() {
                        switch (this.$r8$classId) {
                            case 1:
                                return 0L;
                            case 2:
                                return 0L;
                            default:
                                return super.getKeyInterceptDelayMs();
                        }
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public boolean preCondition() {
                        switch (this.$r8$classId) {
                            case 3:
                                boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                        "setScreenshotEnabled, screenOnFully=",
                                        "PhoneWindowManagerExt",
                                        phoneWindowManagerExt2
                                                .mPolicy
                                                .mDefaultDisplayPolicy
                                                .mScreenOnFully);
                                phoneWindowManagerExt2.mScreenshotEnabled = true;
                                return true;
                            case 4:
                                PhoneWindowManager phoneWindowManager2 = this.this$0;
                                return phoneWindowManager2.mAccessibilityShortcutController
                                        .isAccessibilityShortcutAvailable(
                                                phoneWindowManager2.keyguardOn());
                            default:
                                return super.preCondition();
                        }
                    }
                });
        if (this.mHasFeatureLeanback) {
            this.mKeyCombinationManager.addRule(
                    new KeyCombinationManager.TwoKeysCombinationRule(
                            this, 1) { // from class: com.android.server.policy.PhoneWindowManager.7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ PhoneWindowManager this$0;

                        {
                            super(26, 264);
                            this.$r8$classId = r2;
                            switch (r2) {
                                case 1:
                                    this.this$0 = this;
                                    super(4, 20);
                                    break;
                                case 2:
                                    this.this$0 = this;
                                    super(23, 4);
                                    break;
                                case 3:
                                    this.this$0 = this;
                                    super(25, 26);
                                    break;
                                case 4:
                                    this.this$0 = this;
                                    super(25, 24);
                                    break;
                                default:
                                    this.this$0 = this;
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void cancel() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mHandler.removeMessages(16);
                                    break;
                                case 1:
                                    this.this$0.mHandler.removeMessages(19);
                                    break;
                                case 2:
                                    this.this$0.mHandler.removeMessages(18);
                                    break;
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    if (z2) {
                                        phoneWindowManager2.mHandler.removeMessages(10);
                                    }
                                    WmScreenshotController wmScreenshotController =
                                            phoneWindowManager2
                                                    .mExt
                                                    .mWindowManagerFuncs
                                                    .mScreenshotController;
                                    wmScreenshotController.mHandler.removeCallbacks(
                                            wmScreenshotController.mTakeScreenshotRunnable);
                                    break;
                                default:
                                    this.this$0.mHandler.removeMessages(17);
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public void cancel(KeyEvent keyEvent, boolean z2) {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z3 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    if (z3) {
                                        PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                this.this$0.mExt;
                                        phoneWindowManagerExt2.getClass();
                                        if (z3) {
                                            if (!phoneWindowManagerExt2.mIsScreenshotTriggered) {
                                                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                                                break;
                                            } else {
                                                long[] jArr = phoneWindowManagerExt2.mKeyUpTime;
                                                if (z2) {
                                                    int keyCode = keyEvent.getKeyCode();
                                                    if (keyCode == 26) {
                                                        jArr[0] = keyEvent.getEventTime();
                                                    } else if (keyCode == 25) {
                                                        jArr[1] = keyEvent.getEventTime();
                                                    }
                                                    long j = jArr[0];
                                                    long j2 = jArr[1];
                                                    if (j != 0 && j2 != 0) {
                                                        String str2 =
                                                                KeyCustomizationConstants
                                                                        .VOLD_DECRYPT;
                                                        long uptimeMillis =
                                                                SystemClock.uptimeMillis();
                                                        long j3 =
                                                                uptimeMillis
                                                                        - phoneWindowManagerExt2
                                                                                .mScreenshotTriggeredTime;
                                                        if (uptimeMillis <= j + 150
                                                                && uptimeMillis <= 150 + j2
                                                                && j3 < 1000) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "take a screenshot, this is"
                                                                        + " triggered by"
                                                                        + " keyCombination");
                                                            phoneWindowManagerExt2
                                                                    .mWindowManagerFuncs
                                                                    .sendTakeScreenshotRunnable(
                                                                            1,
                                                                            phoneWindowManagerExt2
                                                                                    .getDisplayId(
                                                                                            null));
                                                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                                                PhoneWindowManagerExt.saLogForBasic(
                                                                        "HWB1007");
                                                            }
                                                        } else if (!phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "Screenshot by the key"
                                                                        + " combination is not"
                                                                        + " triggered, time="
                                                                            + Math.abs(j - j2));
                                                        }
                                                    } else if (!phoneWindowManagerExt2
                                                            .mGlobalActionsByKeyCombnation) {
                                                        RCPManagerService$$ExternalSyntheticOutline0
                                                                .m(
                                                                        new StringBuilder(
                                                                                "Waiting for "),
                                                                        j == 0
                                                                                ? "power"
                                                                                : "volume down",
                                                                        " key up event to take a"
                                                                            + " screenshot",
                                                                        "PhoneWindowManagerExt");
                                                        break;
                                                    }
                                                }
                                                if (phoneWindowManagerExt2
                                                        .mGlobalActionsByKeyCombnation) {
                                                    Log.d(
                                                            "PhoneWindowManagerExt",
                                                            "The key combination long press event"
                                                                + " was consumed by global action");
                                                }
                                                jArr[0] = 0;
                                                jArr[1] = 0;
                                                phoneWindowManagerExt2.mIsScreenshotTriggered =
                                                        false;
                                                phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                        0L;
                                                phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation =
                                                        false;
                                                break;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void execute() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mPowerKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    long j =
                                            DeviceConfig.getLong(
                                                    "systemui",
                                                    "screenshot_keychord_delay",
                                                    ViewConfiguration.get(
                                                                    phoneWindowManager2.mContext)
                                                            .getScreenshotChordKeyTimeout());
                                    if (phoneWindowManager2.mKeyguardDelegate.isShowing()) {
                                        j = (long) (j * 2.5f);
                                    }
                                    phoneWindowManager2.mHandler.removeMessages(16);
                                    PolicyHandler policyHandler = phoneWindowManager2.mHandler;
                                    policyHandler.sendMessageDelayed(
                                            policyHandler.obtainMessage(16, 1, 0), j);
                                    break;
                                case 1:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager3 = this.this$0;
                                    phoneWindowManager3.mHandler.removeMessages(19);
                                    Message obtain =
                                            Message.obtain(phoneWindowManager3.mHandler, 19);
                                    obtain.setAsynchronous(true);
                                    phoneWindowManager3.mHandler.sendMessageDelayed(
                                            obtain,
                                            phoneWindowManager3.getAccessibilityShortcutTimeout());
                                    break;
                                case 2:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager4 = this.this$0;
                                    phoneWindowManager4.mHandler.removeMessages(18);
                                    Message obtain2 =
                                            Message.obtain(phoneWindowManager4.mHandler, 18);
                                    obtain2.setAsynchronous(true);
                                    phoneWindowManager4.mHandler.sendMessageDelayed(obtain2, 1000L);
                                    break;
                                case 3:
                                    this.this$0.mPowerKeyHandled = true;
                                    if (InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                                        PhoneWindowManager phoneWindowManager5 = this.this$0;
                                        phoneWindowManager5.getClass();
                                        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
                                            Log.d(
                                                    "WindowManager",
                                                    "Global Action is canceled because of factory"
                                                        + " mode");
                                        } else {
                                            phoneWindowManager5.mHandler.removeMessages(10);
                                            phoneWindowManager5.mHandler.sendMessageDelayed(
                                                    phoneWindowManager5.mHandler.obtainMessage(
                                                            10, 1, 0),
                                                    1000L);
                                        }
                                    }
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    if (phoneWindowManagerExt2.mScreenshotEnabled) {
                                        if (!z2) {
                                            phoneWindowManagerExt2.mWindowManagerFuncs
                                                    .sendTakeScreenshotRunnable(
                                                            1,
                                                            phoneWindowManagerExt2.getDisplayId(
                                                                    null));
                                            break;
                                        } else {
                                            phoneWindowManagerExt2.mIsScreenshotTriggered = true;
                                            phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                    SystemClock.uptimeMillis();
                                            Log.d(
                                                    "PhoneWindowManagerExt",
                                                    "interceptKeyCombinationScreenshotChord"
                                                        + " triggered");
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    PhoneWindowManager phoneWindowManager6 = this.this$0;
                                    phoneWindowManager6.mHandler.removeMessages(17);
                                    PolicyHandler policyHandler2 = phoneWindowManager6.mHandler;
                                    policyHandler2.sendMessageDelayed(
                                            policyHandler2.obtainMessage(17),
                                            phoneWindowManager6.getAccessibilityShortcutTimeout());
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public long getKeyInterceptDelayMs() {
                            switch (this.$r8$classId) {
                                case 1:
                                    return 0L;
                                case 2:
                                    return 0L;
                                default:
                                    return super.getKeyInterceptDelayMs();
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public boolean preCondition() {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                            "setScreenshotEnabled, screenOnFully=",
                                            "PhoneWindowManagerExt",
                                            phoneWindowManagerExt2
                                                    .mPolicy
                                                    .mDefaultDisplayPolicy
                                                    .mScreenOnFully);
                                    phoneWindowManagerExt2.mScreenshotEnabled = true;
                                    return true;
                                case 4:
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    return phoneWindowManager2.mAccessibilityShortcutController
                                            .isAccessibilityShortcutAvailable(
                                                    phoneWindowManager2.keyguardOn());
                                default:
                                    return super.preCondition();
                            }
                        }
                    });
            this.mKeyCombinationManager.addRule(
                    new KeyCombinationManager.TwoKeysCombinationRule(
                            this, 2) { // from class: com.android.server.policy.PhoneWindowManager.7
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ PhoneWindowManager this$0;

                        {
                            super(26, 264);
                            this.$r8$classId = r2;
                            switch (r2) {
                                case 1:
                                    this.this$0 = this;
                                    super(4, 20);
                                    break;
                                case 2:
                                    this.this$0 = this;
                                    super(23, 4);
                                    break;
                                case 3:
                                    this.this$0 = this;
                                    super(25, 26);
                                    break;
                                case 4:
                                    this.this$0 = this;
                                    super(25, 24);
                                    break;
                                default:
                                    this.this$0 = this;
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void cancel() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mHandler.removeMessages(16);
                                    break;
                                case 1:
                                    this.this$0.mHandler.removeMessages(19);
                                    break;
                                case 2:
                                    this.this$0.mHandler.removeMessages(18);
                                    break;
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    if (z2) {
                                        phoneWindowManager2.mHandler.removeMessages(10);
                                    }
                                    WmScreenshotController wmScreenshotController =
                                            phoneWindowManager2
                                                    .mExt
                                                    .mWindowManagerFuncs
                                                    .mScreenshotController;
                                    wmScreenshotController.mHandler.removeCallbacks(
                                            wmScreenshotController.mTakeScreenshotRunnable);
                                    break;
                                default:
                                    this.this$0.mHandler.removeMessages(17);
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public void cancel(KeyEvent keyEvent, boolean z2) {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z3 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    if (z3) {
                                        PhoneWindowManagerExt phoneWindowManagerExt2 =
                                                this.this$0.mExt;
                                        phoneWindowManagerExt2.getClass();
                                        if (z3) {
                                            if (!phoneWindowManagerExt2.mIsScreenshotTriggered) {
                                                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                                                break;
                                            } else {
                                                long[] jArr = phoneWindowManagerExt2.mKeyUpTime;
                                                if (z2) {
                                                    int keyCode = keyEvent.getKeyCode();
                                                    if (keyCode == 26) {
                                                        jArr[0] = keyEvent.getEventTime();
                                                    } else if (keyCode == 25) {
                                                        jArr[1] = keyEvent.getEventTime();
                                                    }
                                                    long j = jArr[0];
                                                    long j2 = jArr[1];
                                                    if (j != 0 && j2 != 0) {
                                                        String str2 =
                                                                KeyCustomizationConstants
                                                                        .VOLD_DECRYPT;
                                                        long uptimeMillis =
                                                                SystemClock.uptimeMillis();
                                                        long j3 =
                                                                uptimeMillis
                                                                        - phoneWindowManagerExt2
                                                                                .mScreenshotTriggeredTime;
                                                        if (uptimeMillis <= j + 150
                                                                && uptimeMillis <= 150 + j2
                                                                && j3 < 1000) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "take a screenshot, this is"
                                                                        + " triggered by"
                                                                        + " keyCombination");
                                                            phoneWindowManagerExt2
                                                                    .mWindowManagerFuncs
                                                                    .sendTakeScreenshotRunnable(
                                                                            1,
                                                                            phoneWindowManagerExt2
                                                                                    .getDisplayId(
                                                                                            null));
                                                            if (InputRune.PWM_KEY_SA_LOGGING) {
                                                                PhoneWindowManagerExt.saLogForBasic(
                                                                        "HWB1007");
                                                            }
                                                        } else if (!phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation) {
                                                            Log.d(
                                                                    "PhoneWindowManagerExt",
                                                                    "Screenshot by the key"
                                                                        + " combination is not"
                                                                        + " triggered, time="
                                                                            + Math.abs(j - j2));
                                                        }
                                                    } else if (!phoneWindowManagerExt2
                                                            .mGlobalActionsByKeyCombnation) {
                                                        RCPManagerService$$ExternalSyntheticOutline0
                                                                .m(
                                                                        new StringBuilder(
                                                                                "Waiting for "),
                                                                        j == 0
                                                                                ? "power"
                                                                                : "volume down",
                                                                        " key up event to take a"
                                                                            + " screenshot",
                                                                        "PhoneWindowManagerExt");
                                                        break;
                                                    }
                                                }
                                                if (phoneWindowManagerExt2
                                                        .mGlobalActionsByKeyCombnation) {
                                                    Log.d(
                                                            "PhoneWindowManagerExt",
                                                            "The key combination long press event"
                                                                + " was consumed by global action");
                                                }
                                                jArr[0] = 0;
                                                jArr[1] = 0;
                                                phoneWindowManagerExt2.mIsScreenshotTriggered =
                                                        false;
                                                phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                        0L;
                                                phoneWindowManagerExt2
                                                                .mGlobalActionsByKeyCombnation =
                                                        false;
                                                break;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public final void execute() {
                            switch (this.$r8$classId) {
                                case 0:
                                    this.this$0.mPowerKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    long j =
                                            DeviceConfig.getLong(
                                                    "systemui",
                                                    "screenshot_keychord_delay",
                                                    ViewConfiguration.get(
                                                                    phoneWindowManager2.mContext)
                                                            .getScreenshotChordKeyTimeout());
                                    if (phoneWindowManager2.mKeyguardDelegate.isShowing()) {
                                        j = (long) (j * 2.5f);
                                    }
                                    phoneWindowManager2.mHandler.removeMessages(16);
                                    PolicyHandler policyHandler = phoneWindowManager2.mHandler;
                                    policyHandler.sendMessageDelayed(
                                            policyHandler.obtainMessage(16, 1, 0), j);
                                    break;
                                case 1:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager3 = this.this$0;
                                    phoneWindowManager3.mHandler.removeMessages(19);
                                    Message obtain =
                                            Message.obtain(phoneWindowManager3.mHandler, 19);
                                    obtain.setAsynchronous(true);
                                    phoneWindowManager3.mHandler.sendMessageDelayed(
                                            obtain,
                                            phoneWindowManager3.getAccessibilityShortcutTimeout());
                                    break;
                                case 2:
                                    this.this$0.mBackKeyHandled = true;
                                    PhoneWindowManager phoneWindowManager4 = this.this$0;
                                    phoneWindowManager4.mHandler.removeMessages(18);
                                    Message obtain2 =
                                            Message.obtain(phoneWindowManager4.mHandler, 18);
                                    obtain2.setAsynchronous(true);
                                    phoneWindowManager4.mHandler.sendMessageDelayed(obtain2, 1000L);
                                    break;
                                case 3:
                                    this.this$0.mPowerKeyHandled = true;
                                    if (InputRune.PWM_KEY_COMBINATION_GLOBAL_ACTION) {
                                        PhoneWindowManager phoneWindowManager5 = this.this$0;
                                        phoneWindowManager5.getClass();
                                        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
                                            Log.d(
                                                    "WindowManager",
                                                    "Global Action is canceled because of factory"
                                                        + " mode");
                                        } else {
                                            phoneWindowManager5.mHandler.removeMessages(10);
                                            phoneWindowManager5.mHandler.sendMessageDelayed(
                                                    phoneWindowManager5.mHandler.obtainMessage(
                                                            10, 1, 0),
                                                    1000L);
                                        }
                                    }
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    if (phoneWindowManagerExt2.mScreenshotEnabled) {
                                        if (!z2) {
                                            phoneWindowManagerExt2.mWindowManagerFuncs
                                                    .sendTakeScreenshotRunnable(
                                                            1,
                                                            phoneWindowManagerExt2.getDisplayId(
                                                                    null));
                                            break;
                                        } else {
                                            phoneWindowManagerExt2.mIsScreenshotTriggered = true;
                                            phoneWindowManagerExt2.mScreenshotTriggeredTime =
                                                    SystemClock.uptimeMillis();
                                            Log.d(
                                                    "PhoneWindowManagerExt",
                                                    "interceptKeyCombinationScreenshotChord"
                                                        + " triggered");
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    PhoneWindowManager phoneWindowManager6 = this.this$0;
                                    phoneWindowManager6.mHandler.removeMessages(17);
                                    PolicyHandler policyHandler2 = phoneWindowManager6.mHandler;
                                    policyHandler2.sendMessageDelayed(
                                            policyHandler2.obtainMessage(17),
                                            phoneWindowManager6.getAccessibilityShortcutTimeout());
                                    break;
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public long getKeyInterceptDelayMs() {
                            switch (this.$r8$classId) {
                                case 1:
                                    return 0L;
                                case 2:
                                    return 0L;
                                default:
                                    return super.getKeyInterceptDelayMs();
                            }
                        }

                        @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                        public boolean preCondition() {
                            switch (this.$r8$classId) {
                                case 3:
                                    boolean z2 = InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY;
                                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0.mExt;
                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                            "setScreenshotEnabled, screenOnFully=",
                                            "PhoneWindowManagerExt",
                                            phoneWindowManagerExt2
                                                    .mPolicy
                                                    .mDefaultDisplayPolicy
                                                    .mScreenOnFully);
                                    phoneWindowManagerExt2.mScreenshotEnabled = true;
                                    return true;
                                case 4:
                                    PhoneWindowManager phoneWindowManager2 = this.this$0;
                                    return phoneWindowManager2.mAccessibilityShortcutController
                                            .isAccessibilityShortcutAvailable(
                                                    phoneWindowManager2.keyguardOn());
                                default:
                                    return super.preCondition();
                            }
                        }
                    });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006d, code lost:

       if (r5 == null) goto L36;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initializeHdmiStateInternal() {
        /*
            r9 = this;
            java.lang.String r0 = "WindowManager"
            java.lang.String r1 = "Couldn't read hdmi state from /sys/class/switch/hdmi/state: "
            java.lang.String r2 = "/sys/devices/virtual/switch/hdmi/state"
            boolean r2 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m45m(r2)
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L76
            com.android.server.policy.PhoneWindowManager$2 r2 = r9.mHDMIObserver
            java.lang.String r5 = "DEVPATH=/devices/virtual/switch/hdmi"
            r2.startObserving(r5)
            r2 = 0
            java.io.FileReader r5 = new java.io.FileReader     // Catch: java.lang.Throwable -> L40 java.lang.NumberFormatException -> L42 java.io.IOException -> L47
            java.lang.String r6 = "/sys/class/switch/hdmi/state"
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L40 java.lang.NumberFormatException -> L42 java.io.IOException -> L47
            r2 = 15
            char[] r2 = new char[r2]     // Catch: java.lang.Throwable -> L35 java.lang.NumberFormatException -> L38 java.io.IOException -> L3a
            int r6 = r5.read(r2)     // Catch: java.lang.Throwable -> L35 java.lang.NumberFormatException -> L38 java.io.IOException -> L3a
            if (r6 <= r3) goto L3c
            java.lang.String r7 = new java.lang.String     // Catch: java.lang.Throwable -> L35 java.lang.NumberFormatException -> L38 java.io.IOException -> L3a
            int r6 = r6 - r3
            r7.<init>(r2, r4, r6)     // Catch: java.lang.Throwable -> L35 java.lang.NumberFormatException -> L38 java.io.IOException -> L3a
            int r0 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.Throwable -> L35 java.lang.NumberFormatException -> L38 java.io.IOException -> L3a
            if (r0 == 0) goto L3c
            r4 = r3
            goto L3c
        L35:
            r9 = move-exception
            r2 = r5
            goto L70
        L38:
            r2 = move-exception
            goto L4c
        L3a:
            r2 = move-exception
            goto L5e
        L3c:
            r5.close()     // Catch: java.io.IOException -> L89
            goto L89
        L40:
            r9 = move-exception
            goto L70
        L42:
            r5 = move-exception
            r8 = r5
            r5 = r2
            r2 = r8
            goto L4c
        L47:
            r5 = move-exception
            r8 = r5
            r5 = r2
            r2 = r8
            goto L5e
        L4c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L35
            r6.append(r2)     // Catch: java.lang.Throwable -> L35
            java.lang.String r1 = r6.toString()     // Catch: java.lang.Throwable -> L35
            android.util.Slog.w(r0, r1)     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L89
            goto L3c
        L5e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L35
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L35
            r6.append(r2)     // Catch: java.lang.Throwable -> L35
            java.lang.String r1 = r6.toString()     // Catch: java.lang.Throwable -> L35
            android.util.Slog.w(r0, r1)     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto L89
            goto L3c
        L70:
            if (r2 == 0) goto L75
            r2.close()     // Catch: java.io.IOException -> L75
        L75:
            throw r9
        L76:
            com.android.server.policy.PhoneWindowManagerExt r0 = r9.mExt
            com.android.server.policy.PhoneWindowManagerExt$10 r0 = r0.mDrmEventObserver
            java.lang.String r1 = "mdss_mdp/drm/card"
            r0.startObserving(r1)
            com.android.server.policy.PhoneWindowManagerExt r0 = r9.mExt
            com.android.server.policy.PhoneWindowManagerExt$10 r0 = r0.mExtEventObserver
            java.lang.String r1 = "displayport/extcon/extcon"
            r0.startObserving(r1)
        L89:
            com.android.server.wm.DisplayPolicy r9 = r9.mDefaultDisplayPolicy
            r9.setHdmiPlugged(r4, r3)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.initializeHdmiStateInternal():void");
    }

    public final void injectBackGesture(long j) {
        KeyEvent keyEvent =
                new KeyEvent(
                        j,
                        j,
                        0,
                        4,
                        0,
                        0,
                        -1,
                        0,
                        72,
                        FrameworkStatsLog
                                .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
        this.mInputManager.injectInputEvent(keyEvent, 0);
        KeyEvent changeAction = KeyEvent.changeAction(keyEvent, 1);
        this.mInputManager.injectInputEvent(changeAction, 0);
        keyEvent.recycle();
        changeAction.recycle();
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x0b8d, code lost:

       if (r0 != false) goto L173;
    */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0c0e, code lost:

       if (r0 == false) goto L173;
    */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0024, code lost:

       if (((android.util.ArraySet) com.android.server.policy.PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST).contains(java.lang.Integer.valueOf(r10)) != false) goto L6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x09b3, code lost:

       if (r0.isExternal() == false) goto L173;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long interceptKeyBeforeDispatching(
            android.os.IBinder r35, final android.view.KeyEvent r36, int r37) {
        /*
            Method dump skipped, instructions count: 3422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.interceptKeyBeforeDispatching(android.os.IBinder,"
                    + " android.view.KeyEvent, int):long");
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x0316, code lost:

       if (((android.util.ArraySet) com.android.server.policy.PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST).contains(java.lang.Integer.valueOf(r8)) != false) goto L221;
    */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0644, code lost:

       if (com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.Flags.emojiAndScreenshotKeycodesAvailable() == false) goto L443;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a2, code lost:

       if (((android.util.ArraySet) com.android.server.policy.PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST).contains(java.lang.Integer.valueOf(r7)) != false) goto L52;
    */
    /* JADX WARN: Code restructure failed: missing block: B:518:0x0aee, code lost:

       if (r41.mBackKeyHandled != false) goto L497;
    */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x05e0, code lost:

       if ((r3 & 2) != 0) goto L398;
    */
    /* JADX WARN: Code restructure failed: missing block: B:575:0x0453, code lost:

       if (r0.knoxCustomVolumeKeyAppSwitching(true) != false) goto L287;
    */
    /* JADX WARN: Code restructure failed: missing block: B:621:0x053a, code lost:

       if (r0.hasSingleKeyRule(r8) != false) goto L275;
    */
    /* JADX WARN: Code restructure failed: missing block: B:626:0x054f, code lost:

       if (r0.hasSingleKeyRule(r8) != false) goto L275;
    */
    /* JADX WARN: Code restructure failed: missing block: B:633:0x056e, code lost:

       if (r0.hasSingleKeyRule(r8) != false) goto L275;
    */
    /* JADX WARN: Code restructure failed: missing block: B:665:0x017b, code lost:

       if (r5 != (-1)) goto L132;
    */
    /* JADX WARN: Code restructure failed: missing block: B:686:0x0112, code lost:

       if (r9 == false) goto L84;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int interceptKeyBeforeQueueing(android.view.KeyEvent r42, int r43) {
        /*
            Method dump skipped, instructions count: 3098
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.interceptKeyBeforeQueueing(android.view.KeyEvent,"
                    + " int):int");
    }

    public final boolean interceptUnhandledKey(IBinder iBinder, KeyEvent keyEvent) {
        InputDevice device;
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean z = keyEvent.getAction() == 0;
        int modifiers = keyEvent.getModifiers();
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        phoneWindowManagerExt.getClass();
        int keyCode2 = keyEvent.getKeyCode();
        boolean z2 = keyEvent.getAction() == 0;
        if (keyCode2 == 110
                && !z2
                && (device = keyEvent.getDevice()) != null
                && phoneWindowManagerExt.startGameToolsService(
                        device.getVendorId(), device.getProductId(), false)) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    keyCode, "Handled key. keyCode=", "WindowManager");
            return true;
        }
        if (keyCode != 54) {
            if (keyCode != 62) {
                if (keyCode != 111) {
                    if (keyCode != 120) {
                        if (keyCode == 264) {
                            if (!keyEvent.isSystem()) {
                                Log.wtf(
                                        "WindowManager",
                                        "Illegal keycode provided to handleUnhandledSystemKey: "
                                                + KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                            } else if (keyEvent.getAction() == 0
                                    && keyEvent.getRepeatCount() == 0) {
                                setDeferredKeyActionsExecutableAsync(
                                        keyEvent.getKeyCode(), keyEvent.getDownTime());
                            }
                            sendSystemKeyToStatusBarAsync(keyEvent);
                            return true;
                        }
                    } else if (z && repeatCount == 0) {
                        int displayId = this.mExt.getDisplayId(keyEvent);
                        this.mExt.mWindowManagerFuncs.sendTakeScreenshotRunnable(
                                1, displayId != -1 ? displayId : 0);
                        if (InputRune.PWM_KEY_SA_LOGGING) {
                            this.mExt.getClass();
                            PhoneWindowManagerExt.saLogForBasic("PKBD0051");
                        }
                        return true;
                    }
                } else if (keyEvent.hasNoModifiers()
                        && z
                        && KeyEvent.metaStateHasNoModifiers(modifiers)
                        && repeatCount == 0) {
                    this.mContext.closeSystemDialogs();
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mExt.getClass();
                        PhoneWindowManagerExt.saLogForBasic("PKBD0001");
                    }
                    return true;
                }
            } else if (z
                    && repeatCount == 0
                    && KeyEvent.metaStateHasModifiers(modifiers & (-194), 4096)) {
                this.mHandler
                        .obtainMessage(
                                25,
                                new SwitchKeyboardLayoutMessageObject(
                                        iBinder, keyEvent, (modifiers & 193) == 0 ? 1 : -1))
                        .sendToTarget();
                return true;
            }
        } else if (z
                && KeyEvent.metaStateHasModifiers(modifiers, 4098)
                && this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(
                        keyguardOn())) {
            PolicyHandler policyHandler = this.mHandler;
            policyHandler.sendMessage(policyHandler.obtainMessage(17));
            return true;
        }
        return false;
    }

    public final boolean isKeyguardHostWindow(WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 2040;
    }

    public final boolean isKeyguardOccluded() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.mKeyguardState.occluded;
    }

    public final boolean isKeyguardSecure(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        KeyguardServiceWrapper keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService;
        KeyguardServiceDelegate.KeyguardState keyguardState =
                keyguardServiceDelegate.mKeyguardState;
        if (keyguardServiceWrapper != null) {
            KeyguardStateMonitor keyguardStateMonitor =
                    keyguardServiceWrapper.mKeyguardStateMonitor;
            keyguardState.secure =
                    keyguardStateMonitor.mLockPatternUtils.isSecure(i)
                            || keyguardStateMonitor.mSimSecure;
        }
        return keyguardState.secure;
    }

    public final boolean isKeyguardShowing() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isShowing();
    }

    public final boolean isKeyguardShowingAndNotOccluded() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        return (keyguardServiceDelegate == null
                        || !keyguardServiceDelegate.isShowing()
                        || isKeyguardOccluded())
                ? false
                : true;
    }

    public final boolean isKeyguardTrustedLw() {
        KeyguardServiceWrapper keyguardServiceWrapper;
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null
                || (keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService) == null) {
            return false;
        }
        return keyguardServiceWrapper.mKeyguardStateMonitor.mTrusted;
    }

    public final boolean isScreenOn(int i) {
        if (i == 2) {
            return this.mExt.mLastDexMode == 2 && this.mDexDisplay.getState() == 2;
        }
        Display display = this.mExtraDisplay;
        return (display == null || i != display.getDisplayId())
                ? this.mDefaultDisplayPolicy.mScreenOnEarly
                : this.mExtraDisplayPolicy.mScreenOnEarly;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003e, code lost:

       return r0 & r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003b, code lost:

       if (android.provider.Settings.Secure.getIntForUser(r5.mContext.getContentResolver(), "android.car.SETUP_WIZARD_IN_PROGRESS", 0, -2) == 0) goto L10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0026, code lost:

       if (android.provider.Settings.Secure.getIntForUser(r5.mContext.getContentResolver(), "tv_user_setup_complete", 0, -2) != 0) goto L10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0028, code lost:

       r2 = true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isUserSetupComplete() {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "user_setup_complete"
            r2 = 0
            r3 = -2
            int r0 = android.provider.Settings.Secure.getIntForUser(r0, r1, r2, r3)
            r1 = 1
            if (r0 == 0) goto L14
            r0 = r1
            goto L15
        L14:
            r0 = r2
        L15:
            boolean r4 = r5.mHasFeatureLeanback
            if (r4 == 0) goto L2b
            android.content.Context r5 = r5.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r4 = "tv_user_setup_complete"
            int r5 = android.provider.Settings.Secure.getIntForUser(r5, r4, r2, r3)
            if (r5 == 0) goto L29
        L28:
            r2 = r1
        L29:
            r0 = r0 & r2
            goto L3e
        L2b:
            boolean r4 = r5.mHasFeatureAuto
            if (r4 == 0) goto L3e
            android.content.Context r5 = r5.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r4 = "android.car.SETUP_WIZARD_IN_PROGRESS"
            int r5 = android.provider.Settings.Secure.getIntForUser(r5, r4, r2, r3)
            if (r5 != 0) goto L29
            goto L28
        L3e:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.isUserSetupComplete():boolean");
    }

    public final boolean keyguardOn() {
        boolean z;
        if (!isKeyguardShowingAndNotOccluded()) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate == null) {
                z = false;
            } else {
                KeyguardServiceWrapper keyguardServiceWrapper =
                        keyguardServiceDelegate.mKeyguardService;
                if (keyguardServiceWrapper != null) {
                    keyguardServiceDelegate.mKeyguardState.inputRestricted =
                            keyguardServiceWrapper.mKeyguardStateMonitor.mInputRestricted;
                }
                z = keyguardServiceDelegate.mKeyguardState.inputRestricted;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public final void launchAllAppsAction() {
        Intent intent = new Intent("android.intent.action.ALL_APPS");
        if (this.mHasFeatureLeanback) {
            ResolveInfo resolveActivityAsUser =
                    this.mPackageManager.resolveActivityAsUser(
                            PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                                    "android.intent.action.MAIN", "android.intent.category.HOME"),
                            1048576,
                            this.mCurrentUserId);
            if (resolveActivityAsUser != null) {
                intent.setPackage(resolveActivityAsUser.activityInfo.packageName);
            }
        }
        startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public final void launchAllAppsViaA11y() {
        AccessibilityManagerInternal accessibilityManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mAccessibilityManagerInternal == null) {
                    this.mAccessibilityManagerInternal =
                            (AccessibilityManagerInternal)
                                    LocalServices.getService(AccessibilityManagerInternal.class);
                }
                accessibilityManagerInternal = this.mAccessibilityManagerInternal;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (accessibilityManagerInternal != null) {
            accessibilityManagerInternal.performSystemAction();
        }
        dismissKeyboardShortcutsMenu();
    }

    public final void launchAssistAction(int i, int i2, long j, String str) {
        IStatusBar iStatusBar;
        PhoneWindow.sendCloseSystemWindows(this.mContext, "assist");
        if (isUserSetupComplete()) {
            Bundle bundle = new Bundle();
            if (i != -2) {
                bundle.putInt("android.intent.extra.ASSIST_INPUT_DEVICE_ID", i);
            }
            if (str != null) {
                bundle.putBoolean(str, true);
            }
            bundle.putLong("android.intent.extra.TIME", j);
            bundle.putInt("invocation_type", i2);
            SearchManager searchManager =
                    (SearchManager) this.mContext.getSystemService(SearchManager.class);
            if (searchManager != null) {
                searchManager.launchAssist(bundle);
                return;
            }
            StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
            if (statusBarManagerInternal == null
                    || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
                return;
            }
            try {
                iStatusBar.startAssist(bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void launchHomeFromHotKey(final int r6, final boolean r7, boolean r8) {
        /*
            r5 = this;
            com.android.server.policy.PhoneWindowManagerExt r0 = r5.mExt
            r0.getClass()
            r0 = 1
            r1 = 0
            if (r8 == 0) goto L81
            com.android.server.policy.PhoneWindowManagerExt r8 = r5.mExt
            com.android.server.policy.PhoneWindowManager r2 = r8.mPolicy
            com.android.server.policy.keyguard.KeyguardServiceDelegate r3 = r2.mKeyguardDelegate
            r4 = 2
            if (r3 != 0) goto L14
        L12:
            r8 = r1
            goto L26
        L14:
            if (r6 != r4) goto L22
            boolean r2 = r3.isShowing()
            if (r2 == 0) goto L12
            boolean r8 = r8.mDexKeyguardOccluded
            if (r8 != 0) goto L12
            r8 = r0
            goto L26
        L22:
            boolean r8 = r2.isKeyguardShowingAndNotOccluded()
        L26:
            if (r8 == 0) goto L29
            return
        L29:
            if (r6 != r4) goto L51
            com.android.server.policy.PhoneWindowManagerExt r8 = r5.mExt
            r8.getClass()
            r2 = -1
            if (r6 != r2) goto L3a
            com.android.server.policy.PhoneWindowManager r8 = r8.mPolicy
            boolean r8 = r8.isKeyguardOccluded()
            goto L55
        L3a:
            if (r6 != r4) goto L3f
            boolean r8 = r8.mDexKeyguardOccluded
            goto L55
        L3f:
            if (r6 != 0) goto L4f
            com.android.server.policy.PhoneWindowManager r8 = r8.mPolicy
            boolean r2 = r8.mKeyguardOccludedChanged
            if (r2 == 0) goto L4a
            boolean r8 = r8.mPendingKeyguardOccluded
            goto L55
        L4a:
            boolean r8 = r8.isKeyguardOccluded()
            goto L55
        L4f:
            r8 = r1
            goto L55
        L51:
            boolean r8 = r5.isKeyguardOccluded()
        L55:
            if (r8 != 0) goto L81
            com.android.server.policy.keyguard.KeyguardServiceDelegate r8 = r5.mKeyguardDelegate
            com.android.server.policy.keyguard.KeyguardServiceWrapper r2 = r8.mKeyguardService
            if (r2 == 0) goto L65
            com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardState r3 = r8.mKeyguardState
            com.android.server.policy.keyguard.KeyguardStateMonitor r2 = r2.mKeyguardStateMonitor
            boolean r2 = r2.mInputRestricted
            r3.inputRestricted = r2
        L65:
            com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardState r8 = r8.mKeyguardState
            boolean r8 = r8.inputRestricted
            if (r8 == 0) goto L81
            com.android.server.policy.keyguard.KeyguardServiceDelegate r8 = r5.mKeyguardDelegate
            com.android.server.policy.PhoneWindowManager$12 r0 = new com.android.server.policy.PhoneWindowManager$12
            r0.<init>()
            com.android.server.policy.keyguard.KeyguardServiceWrapper r5 = r8.mKeyguardService
            if (r5 == 0) goto L80
            com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardExitDelegate r6 = new com.android.server.policy.keyguard.KeyguardServiceDelegate$KeyguardExitDelegate
            r6.<init>()
            r6.mOnKeyguardExitResult = r0
            r5.verifyUnlock(r6)
        L80:
            return
        L81:
            boolean r8 = r5.mRecentsVisible
            if (r8 == 0) goto Lad
            android.app.IActivityManager r8 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> L8c
            r8.stopAppSwitches()     // Catch: android.os.RemoteException -> L8c
        L8c:
            if (r7 == 0) goto L9d
            java.lang.String r7 = "dreams"
            android.os.IBinder r7 = android.os.ServiceManager.checkService(r7)
            android.service.dreams.IDreamManager r7 = android.service.dreams.IDreamManager.Stub.asInterface(r7)
            if (r7 == 0) goto L9d
            r7.awaken()     // Catch: android.os.RemoteException -> L9d
        L9d:
            com.android.server.policy.PhoneWindowManagerExt r7 = r5.mExt
            boolean r7 = r7.isInDexMode()
            if (r7 == 0) goto La9
            r5.hideRecentApps(r6, r1, r0)
            goto Lb3
        La9:
            r5.hideRecentApps(r1, r1, r0)
            goto Lb3
        Lad:
            java.lang.String r8 = "startDockOrHome"
            r5.startDockOrHome(r8, r0, r7, r6)
        Lb3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.launchHomeFromHotKey(int,"
                    + " boolean, boolean):void");
    }

    public final void lockNow(Bundle bundle) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
        if (bundle != null) {
            this.mScreenLockTimeout.options = bundle;
        }
        this.mHandler.post(this.mScreenLockTimeout);
        Log.d(
                "WindowManager",
                "lockNow, pid " + Binder.getCallingPid() + " , uid" + Binder.getCallingUid());
        synchronized (this.mScreenLockTimeout) {
            this.mLockNowPending = true;
        }
    }

    public final void logKeyboardSystemsEvent(
            KeyEvent keyEvent, KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        this.mHandler
                .obtainMessage(26, keyboardLogEvent.getIntValue(), 0, KeyEvent.obtain(keyEvent))
                .sendToTarget();
    }

    public final void logKeyboardSystemsEventOnActionUp(
            KeyEvent keyEvent, KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        if (keyEvent.getAction() != 1) {
            return;
        }
        logKeyboardSystemsEvent(keyEvent, keyboardLogEvent);
    }

    public final void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        if (this.mCameraLensCoverState != z
                && this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_localDisplaysMirrorContent)) {
            if (this.mCameraLensCoverState == 1 && !z) {
                KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                Intent intent =
                        keyguardServiceDelegate == null
                                ? false
                                : keyguardServiceDelegate.isShowing()
                                        ? new Intent(
                                                "android.media.action.STILL_IMAGE_CAMERA_SECURE")
                                        : new Intent("android.media.action.STILL_IMAGE_CAMERA");
                WindowWakeUpPolicy windowWakeUpPolicy = this.mWindowWakeUpPolicy;
                long j2 = j / 1000000;
                if (windowWakeUpPolicy.canWakeUp(
                        windowWakeUpPolicy.mAllowTheaterModeWakeFromCameraLens)) {
                    windowWakeUpPolicy.wakeUp(j2, 5, "CAMERA_COVER");
                }
                startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
            }
            this.mCameraLensCoverState = z ? 1 : 0;
        }
    }

    public final boolean okToAnimate(boolean z) {
        return (z || this.mDefaultDisplayPolicy.mScreenOnEarly) && !this.mDeviceGoingToSleep;
    }

    public final boolean performHapticFeedback(int i, String str, boolean z) {
        return this.mExt.performHapticFeedback(
                Process.myUid(), i, this.mContext.getOpPackageName(), str, z, false);
    }

    public final void postShortPressOnHome(KeyEvent keyEvent) {
        this.mHandler.post(
                new PhoneWindowManager$$ExternalSyntheticLambda2(
                        this, KeyEvent.obtain(keyEvent), 0));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void powerMultiPressAction(
            long r6, boolean r8, int r9, android.view.KeyEvent r10, int r11) {
        /*
            Method dump skipped, instructions count: 534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.powerMultiPressAction(long,"
                    + " boolean, int, android.view.KeyEvent, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0103, code lost:

       if (r12.mWakeAndUnlockTriggered != false) goto L81;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void powerPress(long r17, int r19, int r20, android.view.KeyEvent r21) {
        /*
            Method dump skipped, instructions count: 878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.PhoneWindowManager.powerPress(long, int, int,"
                    + " android.view.KeyEvent):void");
    }

    public final void readConfigurationDependentBehaviors() {
        Resources resources = this.mContext.getResources();
        if (InputRune.PWM_SUPPORT_SEARCLE) {
            this.mLongPressOnHomeBehavior = 4;
        } else if (InputRune.PWM_SUPPORT_BIXBY_TOUCH_CHN) {
            this.mLongPressOnHomeBehavior = 101;
        } else {
            this.mLongPressOnHomeBehavior =
                    resources.getInteger(R.integer.config_networkNotifySwitchType);
        }
        int i = this.mLongPressOnHomeBehavior;
        if (i < 0 || i > 101) {
            this.mLongPressOnHomeBehavior = 0;
        }
        int integer = resources.getInteger(R.integer.config_keepPreloadsMinDays);
        this.mDoubleTapOnHomeBehavior = integer;
        if (integer < 0 || integer > 2) {
            this.mDoubleTapOnHomeBehavior = 0;
        }
        this.mShortPressOnWindowBehavior = 0;
        if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
            this.mShortPressOnWindowBehavior = 1;
        }
        int integer2 = resources.getInteger(R.integer.device_idle_flex_time_short_ms);
        this.mSettingsKeyBehavior = integer2;
        if (integer2 < 0 || integer2 > 2) {
            this.mSettingsKeyBehavior = 0;
        }
    }

    public final void screenTurnedOff(int i, boolean z) {
        if (DEBUG_WAKEUP) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "Display", " turned off...", "WindowManager");
        }
        if (i != 0) {
            if (i == 2) {
                updateScreenOffSleepToken(2, true);
                return;
            }
            Display display = this.mExtraDisplay;
            if (display == null || i != display.getDisplayId()) {
                return;
            }
            updateScreenOffSleepToken(this.mExtraDisplay.getDisplayId(), true);
            this.mExtraDisplayPolicy.screenTurnedOff();
            this.mExtraDisplayRotation.updateOrientationListener();
            return;
        }
        synchronized (this.mSleepTokenLock) {
            if (z) {
                try {
                    if (!this.mIsGoingToSleepDefaultDisplay) {
                        if (!com.android.window.flags.Flags.skipSleepingWhenSwitchingDisplay()) {}
                        this.mDefaultDisplayPolicy.screenTurnedOff();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mScreenOffSleepTokenAcquirer.acquire(0, z);
            this.mDefaultDisplayPolicy.screenTurnedOff();
        }
        synchronized (this.mLock) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (keyguardServiceDelegate.mKeyguardService != null) {
                    Log.v("KeyguardServiceDelegate", "onScreenTurnedOff()");
                    keyguardServiceDelegate.mKeyguardService.onScreenTurnedOff();
                }
                keyguardServiceDelegate.mKeyguardState.screenState = 0;
            }
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        if (this.mVrManagerInternal == null) {
            return;
        }
        VrManagerService.this.setSystemState(2, false);
    }

    public final void screenTurnedOn(int i) {
        if (DEBUG_WAKEUP) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "Display ", " turned on...", "WindowManager");
        }
        if (this.mWallpaperManagerInternal == null) {
            this.mWallpaperManagerInternal =
                    (WallpaperManagerService.LocalService)
                            LocalServices.getService(WallpaperManagerService.LocalService.class);
        }
        WallpaperManagerService.LocalService localService = this.mWallpaperManagerInternal;
        if (localService != null) {
            localService.onScreenTurnedOn(i);
        }
        if (i != 0) {
            return;
        }
        synchronized (this.mLock) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (keyguardServiceDelegate.mKeyguardService != null) {
                    Log.v("KeyguardServiceDelegate", "onScreenTurnedOn()");
                    keyguardServiceDelegate.mKeyguardService.onScreenTurnedOn();
                }
                keyguardServiceDelegate.mKeyguardState.screenState = 2;
            }
        }
        this.mDefaultDisplayPolicy.onDisplaySwitchFinished();
        if (this.mVrManagerInternal == null) {
            return;
        }
        VrManagerService.this.setSystemState(2, true);
    }

    public final void screenTurningOff(
            int i, WindowManagerPolicy.ScreenOffListener screenOffListener) {
        this.mWindowManagerFuncs.screenTurningOff(i, screenOffListener);
        if (i != 0) {
            return;
        }
        synchronized (this.mLock) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (keyguardServiceDelegate.mKeyguardService != null) {
                    Log.v("KeyguardServiceDelegate", "onScreenTurningOff()");
                    keyguardServiceDelegate.mKeyguardService.onScreenTurningOff();
                }
                keyguardServiceDelegate.mKeyguardState.screenState = 3;
            }
        }
    }

    public final void screenTurningOn(
            int i, DisplayPowerController.ScreenOnUnblocker screenOnUnblocker) {
        boolean z = DEBUG_WAKEUP;
        if (z) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "Display ", " turning on...", "WindowManager");
        }
        if (this.mWallpaperManagerInternal == null) {
            this.mWallpaperManagerInternal =
                    (WallpaperManagerService.LocalService)
                            LocalServices.getService(WallpaperManagerService.LocalService.class);
        }
        WallpaperManagerService.LocalService localService = this.mWallpaperManagerInternal;
        if (localService != null) {
            localService.onScreenTurningOn(i);
        }
        if (i == 0) {
            synchronized (this.mSleepTokenLock) {
                Trace.asyncTraceBegin(32L, "screenTurningOn", 0);
                this.mScreenOffSleepTokenAcquirer.release(0);
                this.mDefaultDisplayPolicy.screenTurningOn(screenOnUnblocker);
            }
            this.mBootAnimationDismissable = false;
            synchronized (this.mLock) {
                try {
                    KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                    if (keyguardServiceDelegate == null
                            || !keyguardServiceDelegate.mKeyguardState.deviceHasKeyguard) {
                        if (z) {
                            Slog.d(
                                    "WindowManager",
                                    "null mKeyguardDelegate: setting mKeyguardDrawComplete.");
                        }
                        this.mHandler.sendEmptyMessage(5);
                    } else {
                        this.mHandler.removeMessages(6);
                        this.mHandler.sendEmptyMessageDelayed(
                                6,
                                ((SystemServiceManager)
                                                        LocalServices.getService(
                                                                SystemServiceManager.class))
                                                .isBootCompleted()
                                        ? this.mKeyguardDrawnTimeout
                                        : 5000L);
                        Slog.i(
                                "WindowManager",
                                "screenTurningOn(+), before calling onScreenTurningOn to"
                                    + " KeyguardDelegate");
                        this.mKeyguardDelegate.onScreenTurningOn(this.mKeyguardDrawnCallback);
                        Slog.i(
                                "WindowManager",
                                "screenTurningOn(-), after calling onScreenTurningOn to"
                                    + " KeyguardDelegate");
                    }
                } finally {
                }
            }
        } else {
            if (i == 2) {
                updateScreenOffSleepToken(2, false);
            } else {
                Display display = this.mExtraDisplay;
                if (display != null && i == display.getDisplayId()) {
                    updateScreenOffSleepToken(this.mExtraDisplay.getDisplayId(), false);
                    this.mExtraDisplayPolicy.screenTurningOn(null);
                }
            }
            this.mScreenOnListeners.put(i, screenOnUnblocker);
            Trace.asyncTraceBegin(32L, "waitForAllWindowsDrawn", i);
            this.mWindowManagerInternal.waitForAllWindowsDrawn(
                    this.mHandler.obtainMessage(7, i, 0), 1000L, i);
        }
        WindowManagerServiceExt windowManagerServiceExt = this.mExt.mWindowManagerFuncs;
        WindowManagerService windowManagerService = windowManagerServiceExt.mService;
        windowManagerService.mH.removeCallbacks(
                new WindowManagerServiceExt$$ExternalSyntheticLambda2(windowManagerServiceExt, 0));
        windowManagerService.mH.post(
                new WindowManagerServiceExt$$ExternalSyntheticLambda2(windowManagerServiceExt, 0));
    }

    public final void sendSystemKeyToStatusBarAsync(KeyEvent keyEvent) {
        Message obtainMessage = this.mHandler.obtainMessage(21, KeyEvent.obtain(keyEvent));
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public final void setCurrentUserLw(int i) {
        this.mCurrentUserId = i;
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            KeyguardServiceWrapper keyguardServiceWrapper =
                    keyguardServiceDelegate.mKeyguardService;
            if (keyguardServiceWrapper != null) {
                keyguardServiceWrapper.setCurrentUser(i);
            }
            keyguardServiceDelegate.mKeyguardState.currentUser = i;
        }
        AccessibilityShortcutController accessibilityShortcutController =
                this.mAccessibilityShortcutController;
        if (accessibilityShortcutController != null) {
            accessibilityShortcutController.setCurrentUser(i);
        }
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            StatusBarManagerService.this.mCurrentUserId = i;
        }
    }

    public final void setDeferredKeyActionsExecutableAsync(int i, long j) {
        Message obtain = Message.obtain(this.mHandler, 27);
        obtain.arg1 = i;
        obtain.obj = Long.valueOf(j);
        obtain.setAsynchronous(true);
        obtain.sendToTarget();
    }

    public final void setSwitchingUser(boolean z) {
        KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardDelegate.mKeyguardService;
        if (keyguardServiceWrapper != null) {
            keyguardServiceWrapper.setSwitchingUser(z);
        }
        if (z) {
            dismissKeyboardShortcutsMenu();
        }
    }

    public final boolean shouldDispatchInputWhenNonInteractive(int i) {
        IDreamManager asInterface;
        boolean z = i == 0 || i == -1;
        Display display = z ? this.mDefaultDisplay : this.mDisplayManager.getDisplay(i);
        if (display != null && display.getState() != 1) {
            if (isKeyguardShowingAndNotOccluded()) {
                return true;
            }
            if (z
                    && (asInterface =
                                    IDreamManager.Stub.asInterface(
                                            ServiceManager.checkService("dreams")))
                            != null) {
                try {
                    if (asInterface.isDreaming()) {
                        return true;
                    }
                } catch (RemoteException e) {
                    Slog.e("WindowManager", "RemoteException when checking if dreaming", e);
                }
            }
        }
        return false;
    }

    public final boolean shouldEnableWakeGestureLp() {
        boolean z;
        if (!this.mWakeGestureEnabledSetting || this.mDefaultDisplayPolicy.mAwake) {
            return false;
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "lid_behavior", 0) == 1
                && this.mDefaultDisplayPolicy.mLidState == 0) {
            return false;
        }
        MyWakeGestureListener myWakeGestureListener = this.mWakeGestureListener;
        synchronized (myWakeGestureListener.mLock) {
            z = myWakeGestureListener.mSensor != null;
        }
        return z;
    }

    public final void showDismissibleKeyguard() {
        KeyguardServiceWrapper keyguardServiceWrapper = this.mKeyguardDelegate.mKeyguardService;
        if (keyguardServiceWrapper != null) {
            keyguardServiceWrapper.showDismissibleKeyguard();
        }
    }

    public final void showGlobalActions() {
        this.mHandler.removeMessages(10);
        this.mHandler.sendEmptyMessage(10);
    }

    public final void showGlobalActionsInternal(int i) {
        if (InputRune.PWM_SIDE_KEY_LONG_PRESS) {
            StringBuilder sb = new StringBuilder("show Global Action, type=");
            Set set = PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST;
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    sb,
                    i != -1
                            ? i != 0
                                    ? i != 1 ? Integer.toString(i) : "KEY_COMBINATION"
                                    : "LONG_PRESS"
                            : "DEFAULT",
                    "WindowManager");
        }
        if (this.mGlobalActions == null) {
            this.mGlobalActions = (GlobalActions) this.mGlobalActionsFactory.get();
        }
        boolean isKeyguardShowingAndNotOccluded = isKeyguardShowingAndNotOccluded();
        GlobalActions globalActions = this.mGlobalActions;
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0)
                        != 0;
        StatusBarManagerService.AnonymousClass3 anonymousClass3 =
                globalActions.mGlobalActionsProvider;
        if (anonymousClass3 == null
                || (((StatusBarManagerService.UiState)
                                                StatusBarManagerService.this.mDisplayUiState.get(0))
                                        .mDisabled2
                                & 8)
                        == 0) {
            globalActions.mKeyguardShowing = isKeyguardShowingAndNotOccluded;
            globalActions.mDeviceProvisioned = z;
            globalActions.mShowing = true;
            if (globalActions.mGlobalActionsAvailable) {
                StatusBarManagerService.AnonymousClass3 anonymousClass32 =
                        globalActions.mGlobalActionsProvider;
                if (StatusBarManagerService.this.mBar != null) {
                    try {
                        StatusBarManagerService.this.mBar.showGlobalActionsMenu(i);
                    } catch (RemoteException unused) {
                    }
                }
            } else {
                globalActions.ensureLegacyCreated();
                globalActions.mSamsungGlobalActions.show(
                        globalActions.mKeyguardShowing, globalActions.mDeviceProvisioned, true, i);
            }
        }
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
    }

    public final void showRecentApps(int i, boolean z) {
        IStatusBar iStatusBar;
        IStatusBar iStatusBar2;
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            if (this.mExt.isInDexMode()) {
                int naturalBarTypeByDisplayId =
                        StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i);
                StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                        (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                synchronized (StatusBarManagerService.this.mBarLock) {
                    if (StatusBarManagerService.this.mBarMap.get(
                                    Integer.valueOf(naturalBarTypeByDisplayId))
                            != null) {
                        try {
                            ((IStatusBar)
                                            StatusBarManagerService.this.mBarMap.get(
                                                    Integer.valueOf(naturalBarTypeByDisplayId)))
                                    .showRecentApps(z);
                            for (Map.Entry entry :
                                    StatusBarManagerService.this.mBarMap.entrySet()) {
                                if (((Integer) entry.getKey()).intValue()
                                                != naturalBarTypeByDisplayId
                                        && (iStatusBar2 = (IStatusBar) entry.getValue()) != null) {
                                    iStatusBar2.hideRecentApps(false, true);
                                }
                            }
                        } catch (RemoteException unused) {
                            Slog.i(
                                    "StatusBarManagerService",
                                    "RemoteException for SHOW_RECENT_APPS");
                        }
                    }
                }
            } else {
                StatusBarManagerService.AnonymousClass2 anonymousClass22 =
                        (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                IStatusBar iStatusBar3 = StatusBarManagerService.this.mBar;
                if (iStatusBar3 != null) {
                    try {
                        iStatusBar3.showRecentApps(z);
                        for (Map.Entry entry2 : StatusBarManagerService.this.mBarMap.entrySet()) {
                            if (((Integer) entry2.getKey()).intValue() != 0
                                    && (iStatusBar = (IStatusBar) entry2.getValue()) != null) {
                                iStatusBar.hideRecentApps(false, true);
                            }
                        }
                    } catch (RemoteException unused2) {
                    }
                }
            }
        }
        dismissKeyboardShortcutsMenu();
    }

    public final void showSystemSettings() {
        Intent m =
                BatteryService$$ExternalSyntheticOutline0.m(67108864, "android.settings.SETTINGS");
        m.setComponent(
                ComponentName.unflattenFromString(
                        "com.android.settings/.homepage.SettingsHomepageActivity"));
        this.mExt.getClass();
        PhoneWindowManagerExt.getFillInIntent();
        this.mExt.getClass();
        startActivityAsUser(m, UserHandle.CURRENT_OR_SELF);
    }

    public final boolean sleepDefaultDisplayFromPowerButton(int i, long j) {
        int i2;
        PowerManager.WakeData lastWakeup = this.mPowerManagerInternal.getLastWakeup();
        if (lastWakeup != null && ((i2 = lastWakeup.wakeReason) == 4 || i2 == 16 || i2 == 17)) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i3 = this.mPowerButtonSuppressionDelayMillis;
            if (i3 > 0 && uptimeMillis < lastWakeup.wakeTime + i3) {
                Slog.i(
                        "WindowManager",
                        "Sleep from power button suppressed. Time since gesture: "
                                + (uptimeMillis - lastWakeup.wakeTime)
                                + "ms");
                return false;
            }
        }
        this.mPowerManager.goToSleep(j, 4, i);
        return true;
    }

    public final void startActivityAsUser(
            Intent intent, Bundle bundle, UserHandle userHandle, boolean z) {
        if (z || isUserSetupComplete()) {
            this.mContext.startActivityAsUser(intent, bundle, userHandle);
            dismissKeyboardShortcutsMenu();
        } else {
            Slog.i(
                    "WindowManager",
                    "Not starting activity because user setup is in progress: " + intent);
        }
    }

    public final void startActivityAsUser(Intent intent, UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle, false);
    }

    public final void startDockOrHome(String str, boolean z, boolean z2, int i) {
        IDreamManager asInterface;
        try {
            ActivityManager.getService().stopAppSwitches();
        } catch (RemoteException unused) {
        }
        PhoneWindow.sendCloseSystemWindowsInDisplay("homekey", i);
        if (z2
                && (asInterface =
                                IDreamManager.Stub.asInterface(
                                        ServiceManager.checkService("dreams")))
                        != null) {
            try {
                asInterface.awaken();
            } catch (RemoteException unused2) {
            }
        }
        if (!this.mHasFeatureAuto && !isUserSetupComplete()) {
            Slog.i("WindowManager", "Not going home because user setup is in progress.");
            return;
        }
        this.mExt.getClass();
        ActivityManagerPerformance activityManagerPerformance =
                ActivityManagerPerformance.AMP_ENABLE ? ActivityManagerPerformance.booster : null;
        if (activityManagerPerformance != null) {
            if (ActivityManagerPerformance.DEBUG) {
                Slog.d("ActivityManagerPerformance", "isHomeKeyPressed() called");
                if (ActivityManagerPerformance.DEBUG_TRACE) {
                    Slog.d(
                            "ActivityManagerPerformance",
                            "isHomeKeyPressed() Trace\n"
                                    + activityManagerPerformance.getCurBoostInfoStr());
                    new Exception().printStackTrace();
                }
            }
            if (ActivityManagerPerformance.AMP_ENABLE
                    && ActivityManagerPerformance.AMP_PERF_ENABLE) {
                activityManagerPerformance.setBoosterHome(null, true, false);
            }
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        phoneWindowManagerExt.getClass();
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                10, new StringBuilder("startDockOrHome, Callers="), "PhoneWindowManagerExt");
        phoneWindowManagerExt.mHandler.post(
                new PhoneWindowManagerExt$$ExternalSyntheticLambda1(0, phoneWindowManagerExt));
        Intent createHomeDockIntent = createHomeDockIntent();
        if (createHomeDockIntent != null) {
            if (z) {
                try {
                    createHomeDockIntent.putExtra("android.intent.extra.FROM_HOME_KEY", z);
                } catch (ActivityNotFoundException unused3) {
                }
            }
            startActivityAsUser(createHomeDockIntent, UserHandle.CURRENT);
            return;
        }
        if (DEBUG_WAKEUP) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "startDockOrHome: startReason= ", str, "WindowManager");
        }
        int userAssignedToDisplay = this.mUserManagerInternal.getUserAssignedToDisplay(i);
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) this.mActivityTaskManagerInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(
                        str, userAssignedToDisplay, i, true, z);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void startedEarlyWakingUp(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mExt.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate == null || keyguardServiceDelegate.mKeyguardService == null) {
            return;
        }
        Log.v("KeyguardServiceDelegate", "startedEarlyWakingUp reason=" + i);
        keyguardServiceDelegate.mKeyguardService.startedEarlyWakingUp(i);
    }

    public final void startedGoingToSleep(int i, int i2) {
        TspStateController tspStateController;
        if (DEBUG_WAKEUP) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Started going to sleep... (groupId=", " why=");
            m.append(
                    WindowManagerPolicyConstants.offReasonToString(
                            WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2)));
            m.append(")");
            Slog.i("WindowManager", m.toString());
        }
        if (i != 0) {
            return;
        }
        synchronized (this.mSleepTokenLock) {
            try {
                this.mIsGoingToSleepDefaultDisplay = true;
                if (!this.mDefaultDisplayPolicy.mScreenOnFully
                        && !this.mDefaultDisplayPolicy.mScreenOnEarly
                        && com.android.window.flags.Flags.skipSleepingWhenSwitchingDisplay()) {
                    this.mScreenOffSleepTokenAcquirer.acquire(0, false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mLastDexMode == 2) {
            phoneWindowManagerExt.mPendingDualModeInteractive |= 4;
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                KeyguardServiceWrapper keyguardServiceWrapper =
                        keyguardServiceDelegate.mKeyguardService;
                if (keyguardServiceWrapper != null) {
                    keyguardServiceWrapper.onStartedGoingToSleep(i2);
                }
                int translateSleepReasonToOffReason =
                        WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2);
                KeyguardServiceDelegate.KeyguardState keyguardState =
                        keyguardServiceDelegate.mKeyguardState;
                keyguardState.offReason = translateSleepReasonToOffReason;
                keyguardState.interactiveState = 3;
            }
        }
        boolean z = CoreRune.FW_TSP_STATE_CONTROLLER;
        if (z) {
            PhoneWindowManagerExt phoneWindowManagerExt2 = this.mExt;
            phoneWindowManagerExt2.getClass();
            if (!z || (tspStateController = phoneWindowManagerExt2.mTspStateController) == null) {
                return;
            }
            tspStateController.mAwake = false;
        }
    }

    public final void startedWakingUp(int i, int i2) {
        TspStateController tspStateController;
        if (DEBUG_WAKEUP) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Started waking up... (groupId=", " why=");
            m.append(
                    WindowManagerPolicyConstants.onReasonToString(
                            WindowManagerPolicyConstants.translateWakeReasonToOnReason(i2)));
            m.append(")");
            Slog.i("WindowManager", m.toString());
        }
        if (i != 0) {
            return;
        }
        EventLog.writeEvent(70000, 1);
        this.mIsGoingToSleepDefaultDisplay = false;
        this.mDefaultDisplayPolicy.setAwake(true);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mLastDexMode == 2) {
            phoneWindowManagerExt.mPendingDualModeInteractive |= 1;
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (keyguardServiceDelegate.mKeyguardService != null) {
                    Log.v("KeyguardServiceDelegate", "onStartedWakingUp()");
                    keyguardServiceDelegate.mKeyguardService.onStartedWakingUp(i2, false);
                }
                keyguardServiceDelegate.mKeyguardState.interactiveState = 1;
            }
        }
        PhoneWindowManagerExt phoneWindowManagerExt2 = this.mExt;
        phoneWindowManagerExt2.mWakingUpReason = i2;
        if (CoreRune.FW_TSP_STATE_CONTROLLER
                && (tspStateController = phoneWindowManagerExt2.mTspStateController) != null) {
            tspStateController.mAwake = true;
            if (CoreRune.FW_TSP_SIP_MODE) {
                tspStateController.writeTspCommandToSysfsInner(
                        5, tspStateController.mImeWindowVisible ? "1" : "0", false);
            }
            if (CoreRune.FW_TSP_NOTE_MODE && tspStateController.mFocusedWindow != null) {
                tspStateController.writeTspCommandToSysfsInner(
                        6, tspStateController.mLastNoteMode ? "1" : "0", false);
            }
            if (CoreRune.FW_TSP_DEADZONE) {
                tspStateController.setOrientation(tspStateController.mIsPortrait, true);
            }
        }
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            if (i2 == 111) {
                phoneWindowManagerExt2.sendFoldSaLoggingCanceledIfNeeded();
            } else if (i2 == 7) {
                phoneWindowManagerExt2.sendFoldSaLoggingCanceledIfNeeded();
            }
        }
    }

    public final void systemBooted() {
        bindKeyguard();
        synchronized (this.mLock) {
            try {
                this.mSystemBooted = true;
                if (this.mSystemReady) {
                    KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                    KeyguardServiceWrapper keyguardServiceWrapper =
                            keyguardServiceDelegate.mKeyguardService;
                    if (keyguardServiceWrapper != null) {
                        keyguardServiceWrapper.onBootCompleted();
                    }
                    keyguardServiceDelegate.mKeyguardState.bootCompleted = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        SideFpsEventHandler sideFpsEventHandler = this.mSideFpsEventHandler;
        if (sideFpsEventHandler
                .mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.fingerprint")) {
            FingerprintManager fingerprintManager =
                    (FingerprintManager)
                            sideFpsEventHandler.mContext.getSystemService(FingerprintManager.class);
            fingerprintManager.addAuthenticatorsRegisteredCallback(
                    sideFpsEventHandler.new AnonymousClass2(fingerprintManager));
        }
        startedWakingUp(0, 0);
        finishedWakingUp(0, 0);
        boolean z = this.mDisplayManager.getDisplay(0).getState() == 2;
        boolean z2 = this.mDefaultDisplayPolicy.mScreenOnListener != null;
        if (z || z2) {
            screenTurningOn(0, this.mDefaultDisplayPolicy.mScreenOnListener);
            screenTurnedOn(0);
        } else {
            this.mBootAnimationDismissable = true;
            enableScreen(null, false);
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        phoneWindowManagerExt.getClass();
        InputManager inputManager = InputManager.getInstance();
        if (inputManager != null) {
            phoneWindowManagerExt.mIsDoubleTapToWakeUpSupported =
                    (inputManager.semCheckInputFeature() & 1) == 1;
        }
    }

    public final void systemReady() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        KeyguardServiceWrapper keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService;
        if (keyguardServiceWrapper != null) {
            keyguardServiceWrapper.onSystemReady();
        } else {
            keyguardServiceDelegate.mKeyguardState.systemIsReady = true;
        }
        this.mVrManagerInternal =
                (VrManagerService.LocalService)
                        LocalServices.getService(VrManagerService.LocalService.class);
        if (this.mVrManagerInternal != null) {
            VrManagerService.LocalService localService = this.mVrManagerInternal;
            VrManagerService.this.mPersistentVrStateRemoteCallbacks.register(
                    this.mPersistentVrModeListener);
        }
        this.mCameraLensCoverState = this.mWindowManagerFuncs.getCameraLensCoverState();
        if (this.mUiModeManager == null) {
            this.mUiModeManager =
                    IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode"));
        }
        try {
            this.mUiMode = this.mUiModeManager.getCurrentModeType();
        } catch (RemoteException unused) {
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        synchronized (this.mLock) {
            try {
                this.mSystemReady = true;
                updateSettings(this.mHandler);
                this.mExt.updateSettings();
                if (this.mSystemBooted) {
                    KeyguardServiceDelegate keyguardServiceDelegate2 = this.mKeyguardDelegate;
                    KeyguardServiceWrapper keyguardServiceWrapper2 =
                            keyguardServiceDelegate2.mKeyguardService;
                    if (keyguardServiceWrapper2 != null) {
                        keyguardServiceWrapper2.onBootCompleted();
                    }
                    keyguardServiceDelegate2.mKeyguardState.bootCompleted = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mAutofillManagerInternal =
                (AutofillManagerInternal) LocalServices.getService(AutofillManagerInternal.class);
    }

    public final void toggleKeyboardShortcutsMenu(int i, int i2) {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            if (!this.mExt.isInDexMode()) {
                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                if (iStatusBar != null) {
                    try {
                        iStatusBar.toggleKeyboardShortcutsMenu(i);
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                return;
            }
            int naturalBarTypeByDisplayId =
                    StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i2);
            StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                    (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
            synchronized (StatusBarManagerService.this.mBarLock) {
                if (StatusBarManagerService.this.mBarMap.get(
                                Integer.valueOf(naturalBarTypeByDisplayId))
                        != null) {
                    try {
                        ((IStatusBar)
                                        StatusBarManagerService.this.mBarMap.get(
                                                Integer.valueOf(naturalBarTypeByDisplayId)))
                                .toggleKeyboardShortcutsMenu(i);
                    } catch (RemoteException unused2) {
                    }
                }
            }
        }
    }

    public final void toggleNotificationPanel(int i) {
        IStatusBarService statusBarService = getStatusBarService();
        if (!isUserSetupComplete() || statusBarService == null) {
            return;
        }
        try {
            if (this.mExt.isInDexMode()) {
                statusBarService.expandNotificationsPanelToType(
                        StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
            } else {
                statusBarService.togglePanel();
            }
        } catch (RemoteException unused) {
        }
    }

    public final void toggleRecentApps(int i) {
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "toggleRecentApps displayId=", "WindowManager");
            if (!this.mExt.isInDexMode()) {
                ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                        .toggleRecentApps();
            } else {
                ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                        .toggleRecentAppsToType(
                                StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
            }
        }
    }

    public final void updateLockScreenTimeout() {
        KeyguardServiceDelegate keyguardServiceDelegate;
        boolean z;
        synchronized (this.mScreenLockTimeout) {
            try {
                if (this.mLockNowPending) {
                    Log.w("WindowManager", "lockNow pending, ignore updating lockscreen timeout");
                    return;
                }
                boolean z2 = false;
                if (!this.mAllowLockscreenWhenOnDisplays.isEmpty()
                        && this.mDefaultDisplayPolicy.mAwake
                        && (keyguardServiceDelegate = this.mKeyguardDelegate) != null) {
                    int i = this.mCurrentUserId;
                    KeyguardServiceWrapper keyguardServiceWrapper =
                            keyguardServiceDelegate.mKeyguardService;
                    KeyguardServiceDelegate.KeyguardState keyguardState =
                            keyguardServiceDelegate.mKeyguardState;
                    if (keyguardServiceWrapper != null) {
                        KeyguardStateMonitor keyguardStateMonitor =
                                keyguardServiceWrapper.mKeyguardStateMonitor;
                        if (!keyguardStateMonitor.mLockPatternUtils.isSecure(i)
                                && !keyguardStateMonitor.mSimSecure) {
                            z = false;
                            keyguardState.secure = z;
                        }
                        z = true;
                        keyguardState.secure = z;
                    }
                    if (keyguardState.secure) {
                        z2 = true;
                    }
                }
                if (this.mLockScreenTimerActive != z2) {
                    if (z2) {
                        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                        this.mHandler.postDelayed(this.mScreenLockTimeout, this.mLockScreenTimeout);
                    } else {
                        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                    }
                    this.mLockScreenTimerActive = z2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateScreenOffSleepToken(int i, boolean z) {
        if (z) {
            this.mScreenOffSleepTokenAcquirer.acquire(i, false);
        } else {
            this.mScreenOffSleepTokenAcquirer.release(i);
        }
    }

    public final void updateSettings(PolicyHandler policyHandler) {
        boolean z;
        boolean z2;
        boolean z3;
        if (policyHandler != null) {
            policyHandler.post(new PhoneWindowManager$$ExternalSyntheticLambda0(1, this));
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        synchronized (this.mLock) {
            try {
                this.mEndcallBehavior =
                        Settings.System.getIntForUser(
                                contentResolver, "end_button_behavior", 2, -2);
                this.mIncallPowerBehavior =
                        Settings.Secure.getIntForUser(
                                contentResolver, "incall_power_button_behavior", 1, -2);
                this.mIncallBackBehavior =
                        Settings.Secure.getIntForUser(
                                contentResolver, "incall_back_button_behavior", 0, -2);
                this.mSystemNavigationKeysEnabled =
                        Settings.Secure.getIntForUser(
                                        contentResolver, "system_navigation_keys_enabled", 0, -2)
                                == 1;
                this.mRingerToggleChord =
                        Settings.Secure.getIntForUser(
                                contentResolver, "volume_hush_gesture", 0, -2);
                this.mPowerButtonSuppressionDelayMillis =
                        Settings.Global.getInt(
                                contentResolver,
                                "power_button_suppression_delay_after_gesture_wake",
                                800);
                if (!this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_wirelessConsentRequired)) {
                    this.mRingerToggleChord = 0;
                }
                boolean z4 =
                        Settings.Secure.getIntForUser(
                                        contentResolver, "wake_gesture_enabled", 0, -2)
                                != 0;
                if (this.mWakeGestureEnabledSetting != z4) {
                    this.mWakeGestureEnabledSetting = z4;
                    updateWakeGestureListenerLp();
                }
                this.mLockScreenTimeout =
                        Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 0, -2);
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                contentResolver, "default_input_method", -2);
                boolean z5 = stringForUser != null && stringForUser.length() > 0;
                if (this.mHasSoftInput != z5) {
                    this.mHasSoftInput = z5;
                    z = true;
                } else {
                    z = false;
                }
                this.mShortPressOnPowerBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "power_button_short_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer.device_idle_idle_after_inactive_to_ms));
                if (!InputRune.PWM_SIDE_KEY_DOUBLE_PRESS) {
                    this.mDoublePressOnPowerBehavior =
                            Settings.Global.getInt(
                                    contentResolver,
                                    "power_button_double_press",
                                    this.mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer
                                                            .config_jobSchedulerInactivityIdleThresholdOnStablePower));
                }
                if (!InputRune.PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL) {
                    this.mTriplePressOnPowerBehavior =
                            Settings.Global.getInt(
                                    contentResolver,
                                    "power_button_triple_press",
                                    this.mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer
                                                            .device_idle_min_light_maintenance_time_ms));
                }
                int i =
                        Settings.Global.getInt(
                                contentResolver,
                                "power_button_long_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_networkPolicyDefaultWarning));
                int i2 =
                        Settings.Global.getInt(
                                contentResolver,
                                "power_button_very_long_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.device_idle_quick_doze_delay_to_ms));
                if (this.mLongPressOnPowerBehavior != i
                        || this.mVeryLongPressOnPowerBehavior != i2) {
                    this.mLongPressOnPowerBehavior = i;
                    this.mVeryLongPressOnPowerBehavior = i2;
                }
                this.mLongPressOnPowerAssistantTimeoutMs =
                        Settings.Global.getLong(
                                this.mContext.getContentResolver(),
                                "power_button_long_press_duration_ms",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_networkWakeupPacketMark));
                this.mPowerVolUpBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "key_chord_power_volume_up",
                                this.mContext
                                        .getResources()
                                        .getInteger(
                                                R.integer
                                                        .config_minMillisBetweenInputUserActivityEvents));
                this.mShortPressOnStemPrimaryBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "stem_primary_button_short_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.device_idle_idle_pending_factor));
                this.mDoublePressOnStemPrimaryBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "stem_primary_button_double_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_jobSchedulerUserGracePeriod));
                this.mTriplePressOnStemPrimaryBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "stem_primary_button_triple_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.device_idle_min_time_to_alarm_ms));
                this.mLongPressOnStemPrimaryBehavior =
                        Settings.Global.getInt(
                                contentResolver,
                                "stem_primary_button_long_press",
                                this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_networkWakeupPacketMask));
                this.mShouldEarlyShortPressOnPower =
                        this.mContext.getResources().getBoolean(R.bool.config_skipSensorAvailable);
                this.mShouldEarlyShortPressOnStemPrimary =
                        this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_smart_battery_available);
                boolean z6 =
                        Settings.Secure.getIntForUser(
                                        contentResolver, "stylus_buttons_enabled", 1, -2)
                                == 1;
                this.mStylusButtonsEnabled = z6;
                InputManagerService.this.mNative.setStylusButtonMotionEventsEnabled(z6);
                z2 =
                        Settings.Secure.getIntForUser(contentResolver, "nav_bar_kids_mode", 0, -2)
                                == 1;
                if (this.mKidsModeEnabled != z2) {
                    this.mKidsModeEnabled = z2;
                    z3 = true;
                } else {
                    z3 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z3) {
            if (!z2) {
                this.mWindowManagerInternal.setOrientationRequestPolicy(false, null, null);
            } else if (this.mContext
                    .getResources()
                    .getBoolean(R.bool.config_shortPressEarlyOnStemPrimary)) {
                this.mWindowManagerInternal.setOrientationRequestPolicy(
                        true, new int[] {0, 8}, new int[] {6, 6});
            } else {
                this.mWindowManagerInternal.setOrientationRequestPolicy(true, null, null);
            }
        }
        if (z) {
            this.mWindowManagerFuncs.updateRotation(true, false);
        }
    }

    public final void updateWakeGestureListenerLp() {
        if (shouldEnableWakeGestureLp()) {
            MyWakeGestureListener myWakeGestureListener = this.mWakeGestureListener;
            synchronized (myWakeGestureListener.mLock) {
                try {
                    Sensor sensor = myWakeGestureListener.mSensor;
                    if (sensor != null && !myWakeGestureListener.mTriggerRequested) {
                        myWakeGestureListener.mTriggerRequested = true;
                        myWakeGestureListener.mSensorManager.requestTriggerSensor(
                                myWakeGestureListener.mListener, sensor);
                    }
                } finally {
                }
            }
            return;
        }
        MyWakeGestureListener myWakeGestureListener2 = this.mWakeGestureListener;
        synchronized (myWakeGestureListener2.mLock) {
            try {
                Sensor sensor2 = myWakeGestureListener2.mSensor;
                if (sensor2 != null && myWakeGestureListener2.mTriggerRequested) {
                    myWakeGestureListener2.mTriggerRequested = false;
                    myWakeGestureListener2.mSensorManager.cancelTriggerSensor(
                            myWakeGestureListener2.mListener, sensor2);
                }
            } finally {
            }
        }
    }

    public final void wakeUpFromWakeKey(int i, long j, boolean z) {
        WindowWakeUpPolicy windowWakeUpPolicy = this.mWindowWakeUpPolicy;
        if (windowWakeUpPolicy.canWakeUp(
                i == 26
                        ? windowWakeUpPolicy.mAllowTheaterModeWakeFromPowerKey
                        : windowWakeUpPolicy.mAllowTheaterModeWakeFromKey)) {
            WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate =
                    windowWakeUpPolicy.mInputWakeUpDelegate;
            if (inputWakeUpDelegate == null || !inputWakeUpDelegate.wakeUpFromKey(j, i, z)) {
                windowWakeUpPolicy.wakeUp(
                        j,
                        i == 26 ? 1 : 6,
                        i == 26
                                ? "POWER"
                                : BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        i, "KEY(", ")"));
            }
            boolean z2 = i == 3 || i == 26;
            if (this.mWakeUpToLastStateTimeout <= 0) {
                return;
            }
            long j2 = this.mPowerManagerInternal.getLastWakeup().sleepDurationRealtime;
            if (DEBUG_WAKEUP) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "shouldWakeUpWithHomeIntent: sleepDurationRealtime= ",
                                j2,
                                " mWakeUpToLastStateTimeout= ");
                m.append(this.mWakeUpToLastStateTimeout);
                Log.i("WindowManager", m.toString());
            }
            if (j2 <= this.mWakeUpToLastStateTimeout || !z2) {
                return;
            }
            startDockOrHome("Wake from " + KeyEvent.keyCodeToString(i), i == 3, true, 0);
        }
    }

    public final void wakeUpFromWakeKey(KeyEvent keyEvent) {
        wakeUpFromWakeKey(
                keyEvent.getKeyCode(), keyEvent.getEventTime(), keyEvent.getAction() == 0);
    }
}
