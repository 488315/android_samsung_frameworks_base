package com.android.server.policy;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IUiModeManager;
import android.app.NotificationManager;
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
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.hdmi.HdmiAudioSystemClient;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiPlaybackClient;
import android.media.AudioManagerInternal;
import android.media.AudioSystem;
import android.media.IAudioService;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeviceIdleManager;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
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
import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.p005os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.dreams.DreamManagerInternal;
import android.service.dreams.IDreamManager;
import android.service.vr.IPersistentVrStateCallbacks;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.IDisplayFoldListener;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillManagerInternal;
import android.widget.Toast;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IShortcutService;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.policy.LogDecelerateInterpolator;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AccessibilityManagerInternal;
import com.android.server.GestureLauncherService;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;
import com.android.server.UiThread;
import com.android.server.input.InputManagerInternal;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.UserManagerInternal;
import com.android.server.vr.VrManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.DisplayRotation;
import com.android.server.wm.StartingSurfaceController;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.policy.KeyCombinationManager;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.SingleKeyGestureDetector;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.policy.WindowManagerPolicyExt;
import com.android.server.policy.keyguard.KeyguardServiceDelegate;
import com.android.server.policy.keyguard.KeyguardStateMonitor;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.wallpaper.WallpaperManagerInternal;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class PhoneWindowManager implements WindowManagerPolicy {
    public AccessibilityManager mAccessibilityManager;
    public AccessibilityManagerInternal mAccessibilityManagerInternal;
    public AccessibilityShortcutController mAccessibilityShortcutController;
    public ActivityManagerInternal mActivityManagerInternal;
    public ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public boolean mAllowStartActivityForLongPressOnPowerDuringSetup;
    public boolean mAllowTheaterModeWakeFromCameraLens;
    public boolean mAllowTheaterModeWakeFromKey;
    public boolean mAllowTheaterModeWakeFromLidSwitch;
    public boolean mAllowTheaterModeWakeFromMotion;
    public boolean mAllowTheaterModeWakeFromMotionWhenNotDreaming;
    public boolean mAllowTheaterModeWakeFromPowerKey;
    public boolean mAllowTheaterModeWakeFromWakeGesture;
    public boolean mAltTabConsumedByDeX;
    public AppOpsManager mAppOpsManager;
    public AudioManagerInternal mAudioManagerInternal;
    public AutofillManagerInternal mAutofillManagerInternal;
    public volatile boolean mBackKeyHandled;
    public volatile boolean mBootAnimationDismissable;
    public boolean mBootMessageNeedsHiding;
    public PowerManager.WakeLock mBroadcastWakeLock;
    public BurnInProtectionHelper mBurnInProtectionHelper;
    public volatile boolean mCameraGestureTriggered;
    public volatile boolean mCameraGestureTriggeredDuringGoingToSleep;
    public Intent mCarDockIntent;
    public Context mContext;
    public DisplayPolicy mCoverViewDisplayPolicy;
    public int mCurrentUserId;
    public Display mDefaultDisplay;
    public DisplayPolicy mDefaultDisplayPolicy;
    public DisplayRotation mDefaultDisplayRotation;
    public Intent mDeskDockIntent;
    public volatile boolean mDeviceGoingToSleep;
    public Display mDexDisplay;
    public volatile boolean mDismissImeOnBackKeyPressed;
    public DisplayFoldController mDisplayFoldController;
    public DisplayManager mDisplayManager;
    public DisplayManagerInternal mDisplayManagerInternal;
    public int mDoublePressOnPowerBehavior;
    public int mDoublePressOnStemPrimaryBehavior;
    public int mDoubleTapOnHomeBehavior;
    public DreamManagerInternal mDreamManagerInternal;
    public volatile boolean mEndCallKeyHandled;
    public int mEndcallBehavior;
    public PhoneWindowManagerExt mExt;
    public GestureLauncherService mGestureLauncherService;
    public GlobalActions mGlobalActions;
    public Supplier mGlobalActionsFactory;
    public GlobalKeyManager mGlobalKeyManager;
    public boolean mGoToSleepOnButtonPressTheaterMode;
    public boolean mHandleVolumeKeysInWM;
    public Handler mHandler;
    public boolean mHapticTextHandleEnabled;
    public boolean mHasFeatureAuto;
    public boolean mHasFeatureHdmiCec;
    public boolean mHasFeatureLeanback;
    public boolean mHasFeatureWatch;
    public boolean mHaveBuiltInKeyboard;
    public boolean mHavePendingMediaKeyRepeatWithWakeLock;
    public HdmiControl mHdmiControl;
    public Intent mHomeIntent;
    public int mIncallBackBehavior;
    public int mIncallPowerBehavior;
    public InputManagerInternal mInputManagerInternal;
    public volatile boolean mIsGoingToSleepDefaultDisplay;
    public KeyCombinationManager mKeyCombinationManager;
    public boolean mKeyguardBound;
    public KeyguardServiceDelegate mKeyguardDelegate;
    public boolean mKeyguardDrawnOnce;
    public boolean mKeyguardOccludedChanged;
    public int mLidKeyboardAccessibility;
    public int mLidNavigationAccessibility;
    public boolean mLockAfterAppTransitionFinished;
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
    public PackageManager mPackageManager;
    public boolean mPendingCapsLockToggle;
    public boolean mPendingKeyguardOccluded;
    public boolean mPendingMetaAction;
    public volatile boolean mPictureInPictureVisible;
    public ComponentName mPowerDoublePressTargetActivity;
    public volatile boolean mPowerKeyHandled;
    public PowerManager.WakeLock mPowerKeyWakeLock;
    public PowerManager mPowerManager;
    public PowerManagerInternal mPowerManagerInternal;
    public int mPowerVolUpBehavior;
    public boolean mPreloadedRecentApps;
    public int mRecentAppsHeldModifiers;
    public volatile boolean mRecentsVisible;
    public volatile boolean mRequestedOrSleepingDefaultDisplay;
    public boolean mSafeMode;
    public long[] mSafeModeEnabledVibePattern;
    public ActivityTaskManagerInternal.SleepTokenAcquirer mScreenOffSleepTokenAcquirer;
    public int mSearchKeyBehavior;
    public ComponentName mSearchKeyTargetActivity;
    public SensorPrivacyManager mSensorPrivacyManager;
    public SettingsObserver mSettingsObserver;
    public int mShortPressOnPowerBehavior;
    public int mShortPressOnSleepBehavior;
    public int mShortPressOnStemPrimaryBehavior;
    public int mShortPressOnWindowBehavior;
    public SideFpsEventHandler mSideFpsEventHandler;
    public SingleKeyGestureDetector mSingleKeyGestureDetector;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public IStatusBarService mStatusBarService;
    public boolean mSupportLongPressPowerWhenNonInteractive;
    public boolean mSystemBooted;
    public boolean mSystemNavigationKeysEnabled;
    public boolean mSystemReady;
    public int mTriplePressOnPowerBehavior;
    public int mTriplePressOnStemPrimaryBehavior;
    public int mUiMode;
    public IUiModeManager mUiModeManager;
    public boolean mUseTvRouting;
    public UserManagerInternal mUserManagerInternal;
    public int mVeryLongPressOnPowerBehavior;
    public Vibrator mVibrator;
    public Intent mVrHeadsetHomeIntent;
    public volatile VrManagerInternal mVrManagerInternal;
    public boolean mWakeGestureEnabledSetting;
    public MyWakeGestureListener mWakeGestureListener;
    public boolean mWakeOnAssistKeyPress;
    public boolean mWakeOnBackKeyPress;
    public boolean mWakeOnDpadKeyPress;
    public long mWakeUpToLastStateTimeout;
    public WallpaperManagerInternal mWallpaperManagerInternal;
    public WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    public WindowManagerInternal mWindowManagerInternal;
    public static boolean DEBUG_INPUT = CoreRune.IS_FACTORY_BINARY;
    public static boolean DEBUG_KEYGUARD = true;
    public static boolean DEBUG_WAKEUP = true;
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public static final VibrationAttributes PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(34);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(50);
    public static final int[] WINDOW_TYPES_WHERE_HOME_DOESNT_WORK = {2003, 2010};
    public final Object mLock = new Object();
    public final SparseArray mScreenOnListeners = new SparseArray();
    public final Object mServiceAcquireLock = new Object();
    public boolean mEnableShiftMenuBugReports = false;
    public boolean mEnableCarDockHomeCapture = true;
    public final KeyguardServiceDelegate.DrawnListener mKeyguardDrawnCallback = new KeyguardServiceDelegate.DrawnListener() { // from class: com.android.server.policy.PhoneWindowManager.1
        @Override // com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener
        public void onDrawn() {
            if (PhoneWindowManager.DEBUG_WAKEUP) {
                Slog.d(StartingSurfaceController.TAG, "mKeyguardDelegate.ShowListener.onDrawn.");
            }
            PhoneWindowManager.this.mHandler.sendEmptyMessage(5);
        }
    };
    public volatile boolean mNavBarVirtualKeyHapticFeedbackEnabled = true;
    public volatile int mPendingWakeKey = -1;
    public int mCameraLensCoverState = -1;
    public boolean mStylusButtonsEnabled = true;
    public boolean mHasSoftInput = false;
    public HashSet mAllowLockscreenWhenOnDisplays = new HashSet();
    public int mRingerToggleChord = 0;
    public final SparseArray mFallbackActions = new SparseArray();
    public final LogDecelerateInterpolator mLogDecelerateInterpolator = new LogDecelerateInterpolator(100, 0);
    public volatile int mTopFocusedDisplayId = -1;
    public int mPowerButtonSuppressionDelayMillis = 800;
    public boolean mLockNowPending = false;
    public int mKeyguardDrawnTimeout = 1000;
    public boolean mSystemKeyRequested = false;
    public UEventObserver mHDMIObserver = new UEventObserver() { // from class: com.android.server.policy.PhoneWindowManager.2
        public void onUEvent(UEventObserver.UEvent uEvent) {
            PhoneWindowManager.this.mDefaultDisplayPolicy.setHdmiPlugged("1".equals(uEvent.get("SWITCH_STATE")));
        }
    };
    public final IPersistentVrStateCallbacks mPersistentVrModeListener = new IPersistentVrStateCallbacks.Stub() { // from class: com.android.server.policy.PhoneWindowManager.3
        public void onPersistentVrStateChanged(boolean z) {
            PhoneWindowManager.this.mDefaultDisplayPolicy.setPersistentVrModeEnabled(z);
        }
    };
    public final Runnable mEndCallLongPress = new Runnable() { // from class: com.android.server.policy.PhoneWindowManager.4
        @Override // java.lang.Runnable
        public void run() {
            PhoneWindowManager.this.mEndCallKeyHandled = true;
            PhoneWindowManager.this.performHapticFeedback(0, false, "End Call - Long Press - Show Global Actions");
            PhoneWindowManager.this.showGlobalActionsInternal();
        }
    };
    public final SparseArray mDisplayHomeButtonHandlers = new SparseArray();
    public BroadcastReceiver mDockReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManager.14
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                PhoneWindowManager.this.mDefaultDisplayPolicy.setDockMode(intent.getIntExtra("android.intent.extra.DOCK_STATE", 0));
            } else {
                try {
                    IUiModeManager asInterface = IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode"));
                    PhoneWindowManager.this.mUiMode = asInterface.getCurrentModeType();
                } catch (RemoteException unused) {
                }
            }
            PhoneWindowManager.this.updateRotation(true);
            PhoneWindowManager.this.mDefaultDisplayRotation.updateOrientationListener();
        }
    };
    public BroadcastReceiver mMultiuserReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManager.15
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                PhoneWindowManager.this.mSettingsObserver.onChange(false);
                PhoneWindowManager.this.mExt.mSettingsObserver.updateSettings();
                PhoneWindowManager.this.mExt.onUserSwitch(intent.getIntExtra("android.intent.extra.user_handle", 0));
                PhoneWindowManager.this.mDefaultDisplayRotation.onUserSwitch();
                PhoneWindowManager.this.mWindowManagerFuncs.onUserSwitched();
            }
        }
    };
    public ProgressDialog mBootMsgDialog = null;
    public final ScreenLockTimeout mScreenLockTimeout = new ScreenLockTimeout();

    public static String incallBackBehaviorToString(int i) {
        return (i & 1) != 0 ? "hangup" : "<nothing>";
    }

    public static String incallPowerBehaviorToString(int i) {
        return (i & 2) != 0 ? "hangup" : "sleep";
    }

    public static boolean isValidGlobalKey(int i) {
        return (i == 26 || i == 223 || i == 224) ? false : true;
    }

    public class PolicyHandler extends Handler {
        public PolicyHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 3:
                    PhoneWindowManager.this.dispatchMediaKeyWithWakeLock((KeyEvent) message.obj);
                    break;
                case 4:
                    PhoneWindowManager.this.dispatchMediaKeyRepeatWithWakeLock((KeyEvent) message.obj);
                    break;
                case 5:
                    if (PhoneWindowManager.DEBUG_WAKEUP) {
                        Slog.w(StartingSurfaceController.TAG, "Setting mKeyguardDrawComplete");
                    }
                    PhoneWindowManager.this.finishKeyguardDrawn();
                    break;
                case 6:
                    Slog.w(StartingSurfaceController.TAG, "Keyguard drawn timeout. Setting mKeyguardDrawComplete");
                    PhoneWindowManager.this.finishKeyguardDrawn();
                    break;
                case 7:
                    int i = message.arg1;
                    if (PhoneWindowManager.DEBUG_WAKEUP) {
                        Slog.w(StartingSurfaceController.TAG, "All windows drawn on display " + i);
                    }
                    Trace.asyncTraceEnd(32L, "waitForAllWindowsDrawn", i);
                    PhoneWindowManager.this.finishWindowsDrawn(i);
                    break;
                case 9:
                    PhoneWindowManager.this.showRecentApps(false);
                    break;
                case 10:
                    if (CoreRune.FW_GLOBAL_ACTION_BY_SIDE_KEY_COMBINATION) {
                        int i2 = 1;
                        if (message.arg1 == 1) {
                            PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                            phoneWindowManager.mExt.mGlobalActionsByKeyCombnation = true;
                            phoneWindowManager.performHapticFeedback(0, false, "Power - Long Press - Show Global Actions");
                        } else {
                            i2 = -1;
                        }
                        PhoneWindowManager.this.showGlobalActionsInternal(i2);
                        break;
                    } else {
                        PhoneWindowManager.this.showGlobalActionsInternal();
                        break;
                    }
                case 11:
                    PhoneWindowManager.this.handleHideBootMessage();
                    break;
                case 12:
                    PhoneWindowManager.this.launchVoiceAssistWithWakeLock();
                    break;
                case 15:
                    PhoneWindowManager.this.showPictureInPictureMenuInternal();
                    break;
                case 16:
                    PhoneWindowManager.this.handleScreenShot(message.arg1);
                    break;
                case 17:
                    PhoneWindowManager.this.accessibilityShortcutActivated();
                    break;
                case 18:
                    PhoneWindowManager.this.requestBugreportForTv();
                    break;
                case 19:
                    if (PhoneWindowManager.this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(false)) {
                        PhoneWindowManager.this.accessibilityShortcutActivated();
                        break;
                    }
                    break;
                case 20:
                    PhoneWindowManager.this.mAutofillManagerInternal.onBackKeyPressed();
                    break;
                case 21:
                    PhoneWindowManager.this.sendSystemKeyToStatusBar((KeyEvent) message.obj);
                    break;
                case 22:
                    PhoneWindowManager.this.launchAllAppsAction();
                    break;
                case 23:
                    PhoneWindowManager.this.launchAssistAction(null, message.arg1, ((Long) message.obj).longValue(), 7);
                    break;
                case 24:
                    PhoneWindowManager.this.handleRingerChordGesture();
                    break;
                case 25:
                    PhoneWindowManager.this.handleSwitchKeyboardLayout(message.arg1, message.arg2);
                    break;
            }
        }
    }

    public class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        public void observe() {
            ContentResolver contentResolver = PhoneWindowManager.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(Settings.System.getUriFor("end_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("incall_power_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("incall_back_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("wake_gesture_enabled"), false, this, -1);
            contentResolver.registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("default_input_method"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("volume_hush_gesture"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("system_navigation_keys_enabled"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("power_button_long_press"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("power_button_long_press_duration_ms"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("power_button_very_long_press"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("key_chord_power_volume_up"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("power_button_suppression_delay_after_gesture_wake"), false, this, -1);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("stylus_buttons_enabled"), false, this, -1);
            PhoneWindowManager.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            PhoneWindowManager.this.updateSettings();
            PhoneWindowManager.this.updateRotation(false);
        }
    }

    public class MyWakeGestureListener extends WakeGestureListener {
        public MyWakeGestureListener(Context context, Handler handler) {
            super(context, handler);
        }

        @Override // com.android.server.policy.WakeGestureListener
        public void onWakeUp() {
            synchronized (PhoneWindowManager.this.mLock) {
                if (PhoneWindowManager.this.shouldEnableWakeGestureLp()) {
                    PhoneWindowManager.this.performHapticFeedback(1, false, "Wake Up");
                    PhoneWindowManager.this.wakeUp(SystemClock.uptimeMillis(), PhoneWindowManager.this.mAllowTheaterModeWakeFromWakeGesture, 4, "android.policy:GESTURE");
                }
            }
        }
    }

    public final void handleRingerChordGesture() {
        if (this.mRingerToggleChord == 0) {
            return;
        }
        getAudioManagerInternal();
        this.mAudioManagerInternal.silenceRingerModeInternal("volume_hush");
        Settings.Secure.putInt(this.mContext.getContentResolver(), "hush_gesture_used", 1);
        this.mLogger.action(1440, this.mRingerToggleChord);
    }

    public IStatusBarService getStatusBarService() {
        IStatusBarService iStatusBarService;
        synchronized (this.mServiceAcquireLock) {
            if (this.mStatusBarService == null) {
                this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            }
            iStatusBarService = this.mStatusBarService;
        }
        return iStatusBarService;
    }

    public StatusBarManagerInternal getStatusBarManagerInternal() {
        StatusBarManagerInternal statusBarManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            if (this.mStatusBarManagerInternal == null) {
                this.mStatusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
            }
            statusBarManagerInternal = this.mStatusBarManagerInternal;
        }
        return statusBarManagerInternal;
    }

    public AudioManagerInternal getAudioManagerInternal() {
        AudioManagerInternal audioManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            if (this.mAudioManagerInternal == null) {
                this.mAudioManagerInternal = (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
            }
            audioManagerInternal = this.mAudioManagerInternal;
        }
        return audioManagerInternal;
    }

    public AccessibilityManagerInternal getAccessibilityManagerInternal() {
        AccessibilityManagerInternal accessibilityManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            if (this.mAccessibilityManagerInternal == null) {
                this.mAccessibilityManagerInternal = (AccessibilityManagerInternal) LocalServices.getService(AccessibilityManagerInternal.class);
            }
            accessibilityManagerInternal = this.mAccessibilityManagerInternal;
        }
        return accessibilityManagerInternal;
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
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(20));
        }
        return z;
    }

    public final void interceptPowerKeyDown(KeyEvent keyEvent, boolean z) {
        if (!this.mPowerKeyWakeLock.isHeld()) {
            this.mPowerKeyWakeLock.acquire(ViewConfiguration.get(this.mContext).getDeviceGlobalActionKeyTimeout() * 2);
        }
        this.mWindowManagerFuncs.onPowerKeyDown(z);
        getTelecommService();
        boolean interceptPowerKeyDown = this.mPowerManagerInternal.interceptPowerKeyDown(keyEvent);
        sendSystemKeyToStatusBarAsync(keyEvent);
        this.mPowerKeyHandled = this.mPowerKeyHandled || interceptPowerKeyDown || this.mKeyCombinationManager.isPowerKeyIntercepted();
        if (this.mPowerKeyHandled) {
            if (this.mSingleKeyGestureDetector.isKeyIntercepted(26)) {
                Slog.d(StartingSurfaceController.TAG, "Skip power key gesture for other policy has handled it.");
                this.mSingleKeyGestureDetector.reset();
            }
        } else if (!z) {
            wakeUpFromPowerKey(keyEvent.getDownTime());
        }
        if (interceptPowerKeyDown) {
            this.mSingleKeyGestureDetector.setBeganFromNonInteractive();
        }
        this.mExt.sendPowerKeyToCover();
    }

    public final void interceptPowerKeyUp(KeyEvent keyEvent, boolean z) {
        if (!(z || this.mPowerKeyHandled) && (keyEvent.getFlags() & 128) == 0) {
            Handler handler = this.mHandler;
            final WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs = this.mWindowManagerFuncs;
            Objects.requireNonNull(windowManagerFuncs);
            handler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerPolicy.WindowManagerFuncs.this.triggerAnimationFailsafe();
                }
            });
        }
        finishPowerKeyPress();
    }

    public final void finishPowerKeyPress() {
        this.mPowerKeyHandled = false;
        if (this.mPowerKeyWakeLock.isHeld()) {
            this.mPowerKeyWakeLock.release();
        }
    }

    public final void powerPress(final long j, int i, boolean z, KeyEvent keyEvent) {
        if (i == 1) {
            this.mSideFpsEventHandler.notifyPowerPressed();
        }
        if (this.mDefaultDisplayPolicy.isScreenOnEarly() && !this.mDefaultDisplayPolicy.isScreenOnFully() && this.mExt.skipPowerPress(i)) {
            Slog.i(StartingSurfaceController.TAG, "Suppressed redundant power key press while already in the process of turning the screen on.");
        }
        boolean isAwake = this.mDefaultDisplayPolicy.isAwake();
        if (i == 1 && this.mExt.powerPress(keyEvent, isAwake, z)) {
            Log.d(StartingSurfaceController.TAG, "consumed by powerPress, interactive=" + isAwake + " beganFromNonInteractive=" + z);
            return;
        }
        Slog.d(StartingSurfaceController.TAG, "powerPress: eventTime=" + j + " interactive=" + isAwake + " count=" + i + " beganFromNonInteractive=" + z + " mShortPressOnPowerBehavior=" + this.mShortPressOnPowerBehavior + " systemKeyRequested=" + this.mSystemKeyRequested);
        if (i == 2) {
            if (this.mExt.canRequestBugReport()) {
                this.mExt.requestCustomFullBugreport("key_combination");
                return;
            } else {
                powerMultiPressAction(j, isAwake, this.mDoublePressOnPowerBehavior, keyEvent, i);
                return;
            }
        }
        if (i == 3) {
            powerMultiPressAction(j, isAwake, this.mTriplePressOnPowerBehavior, keyEvent, i);
            return;
        }
        if (i == 4) {
            powerMultiPressAction(j, isAwake, this.mExt.mQuadruplePressOnPowerBehavior, keyEvent, i);
            return;
        }
        if (i == 5) {
            powerMultiPressAction(j, isAwake, this.mExt.mQuintuplePressOnPowerBehavior, keyEvent, i);
            return;
        }
        if (i > 5 && i <= getMaxMultiPressPowerCount()) {
            Slog.d(StartingSurfaceController.TAG, "No behavior defined for power press count " + i);
            return;
        }
        if (i == 1 && isAwake && !z) {
            if (this.mSideFpsEventHandler.shouldConsumeSinglePress(j)) {
                Slog.i(StartingSurfaceController.TAG, "Suppressing power key because the user is interacting with the fingerprint sensor");
                return;
            }
            if (this.mSystemKeyRequested || this.mExt.hasSystemKeyInfo(26, 3)) {
                Slog.d(StartingSurfaceController.TAG, "skip single press power, requestedSystemKey");
                return;
            }
            switch (this.mShortPressOnPowerBehavior) {
                case 1:
                    sleepDefaultDisplayFromPowerButton(j, 0);
                    break;
                case 2:
                    sleepDefaultDisplayFromPowerButton(j, 1);
                    break;
                case 3:
                    if (sleepDefaultDisplayFromPowerButton(j, 1)) {
                        launchHomeFromHotKey(0);
                        break;
                    }
                    break;
                case 4:
                    shortPressPowerGoHome();
                    break;
                case 5:
                    if (this.mDismissImeOnBackKeyPressed) {
                        InputMethodManagerInternal.get().hideCurrentInputMethod(17);
                        break;
                    } else {
                        shortPressPowerGoHome();
                        break;
                    }
                case 6:
                    KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                    if (keyguardServiceDelegate == null || !keyguardServiceDelegate.hasKeyguard() || !this.mKeyguardDelegate.isSecure(this.mCurrentUserId) || keyguardOn()) {
                        sleepDefaultDisplayFromPowerButton(j, 0);
                        break;
                    } else {
                        lockNow(null);
                        break;
                    }
                    break;
                case 7:
                    attemptToDreamFromShortPowerButtonPress(true, new Runnable() { // from class: com.android.server.policy.PhoneWindowManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneWindowManager.this.lambda$powerPress$0(j);
                        }
                    });
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$powerPress$0(long j) {
        sleepDefaultDisplayFromPowerButton(j, 0);
    }

    public final void attemptToDreamFromShortPowerButtonPress(boolean z, Runnable runnable) {
        if (this.mShortPressOnPowerBehavior != 7) {
            runnable.run();
            return;
        }
        DreamManagerInternal dreamManagerInternal = getDreamManagerInternal();
        if (dreamManagerInternal == null || !dreamManagerInternal.canStartDreaming(z)) {
            Slog.d(StartingSurfaceController.TAG, "Can't start dreaming when attempting to dream from short power press (isScreenOn=" + z + ")");
            runnable.run();
            return;
        }
        synchronized (this.mLock) {
            this.mLockAfterAppTransitionFinished = this.mLockPatternUtils.getPowerButtonInstantlyLocks(this.mCurrentUserId);
        }
        dreamManagerInternal.requestDream();
    }

    public final boolean sleepDefaultDisplayFromPowerButton(long j, int i) {
        int i2;
        PowerManager.WakeData lastWakeup = this.mPowerManagerInternal.getLastWakeup();
        if (lastWakeup != null && ((i2 = lastWakeup.wakeReason) == 4 || i2 == 16 || i2 == 17)) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i3 = this.mPowerButtonSuppressionDelayMillis;
            if (i3 > 0 && uptimeMillis < lastWakeup.wakeTime + i3) {
                Slog.i(StartingSurfaceController.TAG, "Sleep from power button suppressed. Time since gesture: " + (uptimeMillis - lastWakeup.wakeTime) + "ms");
                return false;
            }
        }
        sleepDefaultDisplay(j, 4, i);
        return true;
    }

    public void sleepDefaultDisplay(long j, int i, int i2) {
        this.mRequestedOrSleepingDefaultDisplay = true;
        this.mPowerManager.goToSleep(j, i, i2);
    }

    public final void shortPressPowerGoHome() {
        launchHomeFromHotKey(0, true, false);
        if (isKeyguardShowingAndNotOccluded()) {
            this.mKeyguardDelegate.onShortPowerPressedGoHome();
        }
    }

    public final void powerMultiPressAction(long j, boolean z, int i, KeyEvent keyEvent, int i2) {
        if (this.mExt.powerMultiPressAction(z, i, keyEvent, i2)) {
            return;
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                launchTargetActivityOnMultiPressPower();
                return;
            } else {
                Slog.i(StartingSurfaceController.TAG, "Starting brightness boost.");
                if (!z) {
                    wakeUpFromPowerKey(j);
                }
                this.mPowerManager.boostScreenBrightness(j);
                return;
            }
        }
        if (!isUserSetupComplete()) {
            Slog.i(StartingSurfaceController.TAG, "Ignoring toggling theater mode - device not setup.");
            return;
        }
        if (isTheaterModeEnabled()) {
            Slog.i(StartingSurfaceController.TAG, "Toggling theater mode off.");
            Settings.Global.putInt(this.mContext.getContentResolver(), "theater_mode_on", 0);
            if (z) {
                return;
            }
            wakeUpFromPowerKey(j);
            return;
        }
        Slog.i(StartingSurfaceController.TAG, "Toggling theater mode on.");
        Settings.Global.putInt(this.mContext.getContentResolver(), "theater_mode_on", 1);
        if (this.mGoToSleepOnButtonPressTheaterMode && z) {
            sleepDefaultDisplay(j, 4, 0);
        }
    }

    public final void launchTargetActivityOnMultiPressPower() {
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Executing the double press power action.");
        }
        if (this.mPowerDoublePressTargetActivity != null) {
            Intent intent = new Intent();
            intent.setComponent(this.mPowerDoublePressTargetActivity);
            boolean z = false;
            if (this.mContext.getPackageManager().resolveActivity(intent, 0) != null) {
                KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                if (keyguardServiceDelegate != null && keyguardServiceDelegate.isShowing()) {
                    z = true;
                }
                intent.addFlags(270532608);
                if (!z) {
                    startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
                    return;
                } else {
                    this.mKeyguardDelegate.dismissKeyguardToLaunch(intent);
                    return;
                }
            }
            Slog.e(StartingSurfaceController.TAG, "Could not resolve activity with : " + this.mPowerDoublePressTargetActivity.flattenToString() + " name.");
        }
    }

    public int getLidBehavior() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "lid_behavior", 0);
    }

    public int getMaxMultiPressPowerCount() {
        if (CoreRune.IS_FACTORY_BINARY) {
            return 1;
        }
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        if (phoneWindowManagerExt.mQuintuplePressOnPowerBehavior != 0 && phoneWindowManagerExt.isQuintuplePressPower()) {
            return 5;
        }
        PhoneWindowManagerExt phoneWindowManagerExt2 = this.mExt;
        if (phoneWindowManagerExt2.mQuadruplePressOnPowerBehavior != 0 && phoneWindowManagerExt2.isQuadruplePressPower()) {
            return 4;
        }
        if (this.mTriplePressOnPowerBehavior == 0 || !this.mExt.isTriplePressPower()) {
            return (this.mDoublePressOnPowerBehavior == 0 || !this.mExt.isDoublePressPower()) ? 1 : 2;
        }
        return 3;
    }

    public final void powerLongPress(long j, KeyEvent keyEvent, int i) {
        int resolvedLongPressOnPowerBehavior = getResolvedLongPressOnPowerBehavior();
        Slog.d(StartingSurfaceController.TAG, "powerLongPress: eventTime=" + j + " mLongPressOnPowerBehavior=" + this.mLongPressOnPowerBehavior + " behavior=" + resolvedLongPressOnPowerBehavior);
        if (this.mExt.powerLongPress(resolvedLongPressOnPowerBehavior, keyEvent, i)) {
            this.mPowerKeyHandled = true;
            return;
        }
        if (resolvedLongPressOnPowerBehavior == 1) {
            this.mPowerKeyHandled = true;
            performHapticFeedback(10003, false, "Power - Long Press - Global Actions");
            if (CoreRune.FW_LONG_PRESS_SIDE_KEY) {
                showGlobalActionsInternal(0);
            } else {
                showGlobalActions();
            }
            if (CoreRune.FW_SA_LOGGING) {
                this.mExt.sendCoreSaLoggingDimension("HWB1006", "Power off menu");
                return;
            }
            return;
        }
        if (resolvedLongPressOnPowerBehavior == 2 || resolvedLongPressOnPowerBehavior == 3) {
            this.mPowerKeyHandled = true;
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            performHapticFeedback(10003, false, "Power - Long Press - Shut Off");
            sendCloseSystemWindows("globalactions");
            this.mWindowManagerFuncs.shutdown(resolvedLongPressOnPowerBehavior == 2);
            return;
        }
        if (resolvedLongPressOnPowerBehavior == 4) {
            this.mPowerKeyHandled = true;
            performHapticFeedback(10003, false, "Power - Long Press - Go To Voice Assist");
            launchVoiceAssist(this.mAllowStartActivityForLongPressOnPowerDuringSetup);
        } else {
            if (resolvedLongPressOnPowerBehavior != 5) {
                return;
            }
            this.mPowerKeyHandled = true;
            performHapticFeedback(10002, false, "Power - Long Press - Go To Assistant");
            launchAssistAction(null, Integer.MIN_VALUE, j, 6);
        }
    }

    public final void powerVeryLongPress() {
        if (this.mVeryLongPressOnPowerBehavior != 1) {
            return;
        }
        this.mPowerKeyHandled = true;
        performHapticFeedback(10003, false, "Power - Very Long Press - Show Global Actions");
        showGlobalActions();
    }

    public final void accessibilityShortcutActivated() {
        Slog.d(StartingSurfaceController.TAG, "Accessibility Shortcut Volup + Voldown is performed");
        this.mAccessibilityShortcutController.performAccessibilityShortcut();
        if (CoreRune.FW_SA_LOGGING) {
            CoreSaLogger.logForBasic("HWB1011");
        }
    }

    public final void sleepPress() {
        if (this.mShortPressOnSleepBehavior == 1) {
            launchHomeFromHotKey(0, false, true);
        }
    }

    public final void sleepRelease(long j) {
        int i = this.mShortPressOnSleepBehavior;
        if (i == 0 || i == 1) {
            Slog.i(StartingSurfaceController.TAG, "sleepRelease() calling goToSleep(GO_TO_SLEEP_REASON_SLEEP_BUTTON)");
            sleepDefaultDisplay(j, 6, 0);
        }
    }

    public final int getResolvedLongPressOnPowerBehavior() {
        if (CoreRune.IS_FACTORY_BINARY && this.mExt.isBlockedPowerKey()) {
            return 0;
        }
        if (FactoryTest.isLongPressOnPowerOffEnabled() || FactoryTest.isAutomaticTestMode(this.mContext)) {
            return 3;
        }
        if (this.mLongPressOnPowerBehavior == 5 && !isDeviceProvisioned()) {
            return 1;
        }
        if (this.mLongPressOnPowerBehavior != 4 || isLongPressToAssistantEnabled(this.mContext)) {
            return this.mLongPressOnPowerBehavior;
        }
        return 0;
    }

    public final void stemPrimaryPress(int i) {
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "stemPrimaryPress: " + i);
        }
        if (i == 3) {
            stemPrimaryTriplePressAction(this.mTriplePressOnStemPrimaryBehavior);
        } else if (i == 2) {
            stemPrimaryDoublePressAction(this.mDoublePressOnStemPrimaryBehavior);
        } else if (i == 1) {
            stemPrimarySinglePressAction(this.mShortPressOnStemPrimaryBehavior);
        }
    }

    public final void stemPrimarySinglePressAction(int i) {
        if (i != 1) {
            return;
        }
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Executing stem primary short press action behavior.");
        }
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (!(keyguardServiceDelegate != null && keyguardServiceDelegate.isShowing())) {
            Intent intent = new Intent("android.intent.action.ALL_APPS");
            intent.addFlags(270532608);
            startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
            return;
        }
        this.mKeyguardDelegate.onSystemKeyPressed(264);
    }

    public final void stemPrimaryDoublePressAction(int i) {
        if (i != 1) {
            return;
        }
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Executing stem primary double press action behavior.");
        }
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null ? false : keyguardServiceDelegate.isShowing()) {
            return;
        }
        switchRecentTask();
    }

    public final void stemPrimaryTriplePressAction(int i) {
        if (i != 1) {
            return;
        }
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Executing stem primary triple press action behavior.");
        }
        toggleTalkBack();
    }

    public final void stemPrimaryLongPress() {
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Executing stem primary long press action behavior.");
        }
        if (this.mLongPressOnStemPrimaryBehavior != 1) {
            return;
        }
        launchVoiceAssist(false);
    }

    public final void toggleTalkBack() {
        ComponentName talkbackComponent = getTalkbackComponent();
        if (talkbackComponent == null) {
            return;
        }
        AccessibilityUtils.setAccessibilityServiceState(this.mContext, talkbackComponent, !AccessibilityUtils.getEnabledServicesFromSettings(this.mContext, this.mCurrentUserId).contains(talkbackComponent));
    }

    public final ComponentName getTalkbackComponent() {
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class)).getInstalledAccessibilityServiceList().iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
            if (isTalkback(serviceInfo)) {
                return new ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
        }
        return null;
    }

    public final boolean isTalkback(ServiceInfo serviceInfo) {
        return serviceInfo.loadLabel(this.mPackageManager).toString().equals("TalkBack");
    }

    public final void switchRecentTask() {
        ActivityManager.RecentTaskInfo mostRecentTaskFromBackground = this.mActivityTaskManagerInternal.getMostRecentTaskFromBackground();
        if (mostRecentTaskFromBackground == null) {
            if (DEBUG_INPUT) {
                Slog.w(StartingSurfaceController.TAG, "No recent task available! Show watch face.");
            }
            goHome();
            return;
        }
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "Starting task from recents. id=" + mostRecentTaskFromBackground.id + ", persistentId=" + mostRecentTaskFromBackground.persistentId + ", topActivity=" + mostRecentTaskFromBackground.topActivity + ", baseIntent=" + mostRecentTaskFromBackground.baseIntent);
        }
        try {
            ActivityManager.getService().startActivityFromRecents(mostRecentTaskFromBackground.persistentId, (Bundle) null);
        } catch (RemoteException | IllegalArgumentException e) {
            Slog.e(StartingSurfaceController.TAG, "Failed to start task " + mostRecentTaskFromBackground.persistentId + " from recents", e);
        }
    }

    public final int getMaxMultiPressStemPrimaryCount() {
        if (this.mTriplePressOnStemPrimaryBehavior == 1 && Settings.System.getIntForUser(this.mContext.getContentResolver(), "wear_accessibility_gesture_enabled", 0, -2) == 1) {
            return 3;
        }
        return this.mDoublePressOnStemPrimaryBehavior != 0 ? 2 : 1;
    }

    public final boolean hasLongPressOnPowerBehavior() {
        return getResolvedLongPressOnPowerBehavior() != 0;
    }

    public final boolean hasVeryLongPressOnPowerBehavior() {
        return this.mVeryLongPressOnPowerBehavior != 0;
    }

    public final boolean hasLongPressOnBackBehavior() {
        return this.mLongPressOnBackBehavior != 0;
    }

    public final boolean hasLongPressOnStemPrimaryBehavior() {
        return this.mLongPressOnStemPrimaryBehavior != 0;
    }

    public final boolean hasStemPrimaryBehavior() {
        return getMaxMultiPressStemPrimaryCount() > 1 || hasLongPressOnStemPrimaryBehavior() || this.mShortPressOnStemPrimaryBehavior != 0;
    }

    public final void interceptScreenshotChord(int i, long j) {
        this.mHandler.removeMessages(16);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(16, i, 0), j);
    }

    public final void interceptAccessibilityShortcutChord() {
        this.mHandler.removeMessages(17);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(17), getAccessibilityShortcutTimeout());
    }

    public final long getAccessibilityShortcutTimeout() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_shortcut_dialog_shown", 0, this.mCurrentUserId);
        Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 0, this.mCurrentUserId);
        return viewConfiguration.getAccessibilityShortcutKeyTimeout();
    }

    public final long getScreenshotChordLongPressDelay() {
        long j = DeviceConfig.getLong("systemui", "screenshot_keychord_delay", ViewConfiguration.get(this.mContext).getScreenshotChordKeyTimeout());
        return this.mKeyguardDelegate.isShowing() ? (long) (j * 2.5f) : j;
    }

    public final void cancelPendingScreenshotChordAction() {
        this.mHandler.removeMessages(16);
    }

    public final void cancelPendingAccessibilityShortcutAction() {
        this.mHandler.removeMessages(17);
    }

    public final void handleScreenShot(int i) {
        this.mDefaultDisplayPolicy.takeScreenshot(1, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void showGlobalActions() {
        this.mHandler.removeMessages(10);
        this.mHandler.sendEmptyMessage(10);
    }

    public void showGlobalActionsInternal() {
        showGlobalActionsInternal(-1);
    }

    public void showGlobalActionsInternal(int i) {
        Log.d(StartingSurfaceController.TAG, "show Global Action, type=" + PhoneWindowManagerExt.sideKeyGlobalActionSaLoggingTypeToString(i));
        if (this.mGlobalActions == null) {
            this.mGlobalActions = (GlobalActions) this.mGlobalActionsFactory.get();
        }
        this.mGlobalActions.showDialog(isKeyguardShowingAndNotOccluded(), isDeviceProvisioned(), i);
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
    }

    public boolean isDeviceProvisioned() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isUserSetupComplete() {
        boolean isAutoUserSetupComplete;
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) != 0;
        if (this.mHasFeatureLeanback) {
            isAutoUserSetupComplete = isTvUserSetupComplete();
        } else {
            if (!this.mHasFeatureAuto) {
                return z;
            }
            isAutoUserSetupComplete = isAutoUserSetupComplete();
        }
        return z & isAutoUserSetupComplete;
    }

    public final boolean isAutoUserSetupComplete() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "android.car.SETUP_WIZARD_IN_PROGRESS", 0, -2) == 0;
    }

    public final boolean isTvUserSetupComplete() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "tv_user_setup_complete", 0, -2) != 0;
    }

    public void handleShortPressOnHome(int i) {
        HdmiControl hdmiControl = getHdmiControl();
        if (hdmiControl != null) {
            hdmiControl.turnOnTv();
        }
        DreamManagerInternal dreamManagerInternal = getDreamManagerInternal();
        if (dreamManagerInternal != null && dreamManagerInternal.isDreaming()) {
            this.mDreamManagerInternal.stopDream(false, "short press on home");
        } else {
            launchHomeFromHotKey(i);
        }
    }

    public final HdmiControl getHdmiControl() {
        if (this.mHdmiControl == null) {
            if (!this.mHasFeatureHdmiCec) {
                return null;
            }
            HdmiControlManager hdmiControlManager = (HdmiControlManager) this.mContext.getSystemService("hdmi_control");
            this.mHdmiControl = new HdmiControl(hdmiControlManager != null ? hdmiControlManager.getPlaybackClient() : null);
        }
        return this.mHdmiControl;
    }

    public class HdmiControl {
        public final HdmiPlaybackClient mClient;

        public HdmiControl(HdmiPlaybackClient hdmiPlaybackClient) {
            this.mClient = hdmiPlaybackClient;
        }

        public void turnOnTv() {
            HdmiPlaybackClient hdmiPlaybackClient = this.mClient;
            if (hdmiPlaybackClient == null) {
                return;
            }
            hdmiPlaybackClient.oneTouchPlay(new HdmiPlaybackClient.OneTouchPlayCallback() { // from class: com.android.server.policy.PhoneWindowManager.HdmiControl.1
                public void onComplete(int i) {
                    if (i != 0) {
                        Log.w(StartingSurfaceController.TAG, "One touch play failed: " + i);
                    }
                }
            });
        }
    }

    public void launchAllAppsAction() {
        Intent intent = new Intent("android.intent.action.ALL_APPS");
        if (this.mHasFeatureLeanback) {
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.HOME");
            ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(intent2, 1048576, this.mCurrentUserId);
            if (resolveActivityAsUser != null) {
                intent.setPackage(resolveActivityAsUser.activityInfo.packageName);
            }
        }
        startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public final void launchAllAppsViaA11y() {
        getAccessibilityManagerInternal().performSystemAction(14);
    }

    public void toggleNotificationPanel() {
        toggleNotificationPanel(0);
    }

    public void toggleNotificationPanel(int i) {
        IStatusBarService statusBarService = getStatusBarService();
        if (!isUserSetupComplete() || statusBarService == null) {
            return;
        }
        try {
            statusBarService.expandNotificationsPanelToType(StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
        } catch (RemoteException unused) {
        }
    }

    public final void showSystemSettings() {
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.addFlags(67108864);
        intent.setComponent(ComponentName.unflattenFromString("com.android.settings/.homepage.SettingsHomepageActivity"));
        Intent fillInIntent = this.mExt.getFillInIntent();
        if (this.mExt.showCoverToast(fillInIntent, intent)) {
            if (keyguardOn()) {
                return;
            }
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            phoneWindowManagerExt.setPendingIntentAfterUnlock(phoneWindowManagerExt.getPendingIntentActivityAsUser(intent, UserHandle.CURRENT_OR_SELF), fillInIntent);
            return;
        }
        startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
    }

    public final void showPictureInPictureMenu(KeyEvent keyEvent) {
        if (DEBUG_INPUT) {
            Log.d(StartingSurfaceController.TAG, "showPictureInPictureMenu event=" + keyEvent);
        }
        this.mHandler.removeMessages(15);
        Message obtainMessage = this.mHandler.obtainMessage(15);
        obtainMessage.setAsynchronous(true);
        obtainMessage.sendToTarget();
    }

    public final void showPictureInPictureMenuInternal() {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.showPictureInPictureMenu();
        }
    }

    public class DisplayHomeButtonHandler {
        public final int mDisplayId;
        public boolean mHomeConsumed;
        public boolean mHomeDoubleTapPending;
        public final Runnable mHomeDoubleTapTimeoutRunnable = new Runnable() { // from class: com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.1
            @Override // java.lang.Runnable
            public void run() {
                if (DisplayHomeButtonHandler.this.mHomeDoubleTapPending) {
                    DisplayHomeButtonHandler.this.mHomeDoubleTapPending = false;
                    DisplayHomeButtonHandler displayHomeButtonHandler = DisplayHomeButtonHandler.this;
                    PhoneWindowManager.this.handleShortPressOnHome(displayHomeButtonHandler.mDisplayId);
                }
            }
        };
        public boolean mHomePressed;

        public DisplayHomeButtonHandler(int i) {
            this.mDisplayId = i;
        }

        public int handleHomeButton(IBinder iBinder, final KeyEvent keyEvent) {
            boolean keyguardOn = PhoneWindowManager.this.keyguardOn();
            int repeatCount = keyEvent.getRepeatCount();
            boolean z = keyEvent.getAction() == 0;
            boolean isCanceled = keyEvent.isCanceled();
            if (PhoneWindowManager.DEBUG_INPUT) {
                Log.d(StartingSurfaceController.TAG, String.format("handleHomeButton in display#%d mHomePressed = %b", Integer.valueOf(this.mDisplayId), Boolean.valueOf(this.mHomePressed)));
            }
            if (!z) {
                if (this.mDisplayId == 0) {
                    PhoneWindowManager.this.cancelPreloadRecentApps();
                }
                if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && PhoneWindowManager.this.mExt.isLongPressHomeSearcle()) {
                    PhoneWindowManager.this.mExt.startSearcleHomeKeyPress(false);
                }
                this.mHomePressed = false;
                if (this.mHomeConsumed) {
                    this.mHomeConsumed = false;
                    return -1;
                }
                if (isCanceled) {
                    Log.i(StartingSurfaceController.TAG, "Ignoring HOME; event canceled.");
                    return -1;
                }
                if (PhoneWindowManager.this.mDoubleTapOnHomeBehavior != 0 && (PhoneWindowManager.this.mDoubleTapOnHomeBehavior != 2 || PhoneWindowManager.this.mPictureInPictureVisible)) {
                    PhoneWindowManager.this.mHandler.removeCallbacks(this.mHomeDoubleTapTimeoutRunnable);
                    this.mHomeDoubleTapPending = true;
                    PhoneWindowManager.this.mHandler.postDelayed(this.mHomeDoubleTapTimeoutRunnable, ViewConfiguration.getDoubleTapTimeout());
                    return -1;
                }
                if (PhoneWindowManager.this.mExt.hasSystemKeyInfo(3, 3)) {
                    Log.i(StartingSurfaceController.TAG, "skip single press home, requestedSystemKey");
                    return 0;
                }
                PhoneWindowManager.this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManager$DisplayHomeButtonHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PhoneWindowManager.DisplayHomeButtonHandler.this.lambda$handleHomeButton$0();
                    }
                });
                return -1;
            }
            KeyInterceptionInfo keyInterceptionInfoFromToken = PhoneWindowManager.this.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
            if (keyInterceptionInfoFromToken != null) {
                int i = keyInterceptionInfoFromToken.layoutParamsType;
                if (i == 2009 || (i == 2040 && PhoneWindowManager.this.isKeyguardShowing())) {
                    return 0;
                }
                for (int i2 : PhoneWindowManager.WINDOW_TYPES_WHERE_HOME_DOESNT_WORK) {
                    if (keyInterceptionInfoFromToken.layoutParamsType == i2) {
                        return -1;
                    }
                }
            }
            if (repeatCount == 0) {
                this.mHomePressed = true;
                if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && PhoneWindowManager.this.mExt.isLongPressHomeSearcle()) {
                    PhoneWindowManager.this.mExt.startSearcleHomeKeyPress(true);
                }
                if (this.mHomeDoubleTapPending) {
                    this.mHomeDoubleTapPending = false;
                    PhoneWindowManager.this.mHandler.removeCallbacks(this.mHomeDoubleTapTimeoutRunnable);
                    PhoneWindowManager.this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManager$DisplayHomeButtonHandler$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneWindowManager.DisplayHomeButtonHandler.this.lambda$handleHomeButton$1();
                        }
                    });
                } else if (PhoneWindowManager.this.mDoubleTapOnHomeBehavior == 1 && this.mDisplayId == 0) {
                    PhoneWindowManager.this.preloadRecentApps();
                }
            } else if ((keyEvent.getFlags() & 128) != 0) {
                if (!keyguardOn) {
                    if (PhoneWindowManager.this.mExt.hasSystemKeyInfo(3, 4)) {
                        Log.i(StartingSurfaceController.TAG, "skip long press home, requestedSystemKey");
                        return 0;
                    }
                    PhoneWindowManager.this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManager$DisplayHomeButtonHandler$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneWindowManager.DisplayHomeButtonHandler.this.lambda$handleHomeButton$2(keyEvent);
                        }
                    });
                } else {
                    Log.d(StartingSurfaceController.TAG, "keyguardOn, isShowing=" + PhoneWindowManager.this.isKeyguardShowingAndNotOccluded() + " isInputRestricted=" + PhoneWindowManager.this.inKeyguardRestrictedKeyInputMode());
                    if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && !this.mHomeConsumed) {
                        PhoneWindowManager.this.mExt.showingSearcleToastIfNeeded();
                    }
                }
            }
            if (!PhoneWindowManager.this.mExt.canDispatchingSystemKeyEvent(3)) {
                return -1;
            }
            Log.i(StartingSurfaceController.TAG, "can dispatching home key event, requestedSystemKey");
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleHomeButton$0() {
            PhoneWindowManager.this.handleShortPressOnHome(this.mDisplayId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleHomeButton$1() {
            handleDoubleTapOnHome(this.mDisplayId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleHomeButton$2(KeyEvent keyEvent) {
            handleLongPressOnHome(keyEvent.getDeviceId(), keyEvent.getEventTime());
        }

        public final void handleDoubleTapOnHome(int i) {
            if (this.mHomeConsumed) {
                return;
            }
            int i2 = PhoneWindowManager.this.mDoubleTapOnHomeBehavior;
            if (i2 == 1) {
                this.mHomeConsumed = true;
                PhoneWindowManager.this.toggleRecentApps(i);
            } else if (i2 == 2) {
                this.mHomeConsumed = true;
                PhoneWindowManager.this.showPictureInPictureMenuInternal();
            } else {
                Log.w(StartingSurfaceController.TAG, "No action or undefined behavior for double tap home: " + PhoneWindowManager.this.mDoubleTapOnHomeBehavior);
            }
        }

        public final void handleLongPressOnHome(int i, long j) {
            if (!this.mHomeConsumed && PhoneWindowManager.this.mExt.handleLongPressOnHomeWithPolicy(i, j)) {
                this.mHomeConsumed = true;
            }
        }

        public String toString() {
            return String.format("mDisplayId = %d, mHomePressed = %b", Integer.valueOf(this.mDisplayId), Boolean.valueOf(this.mHomePressed));
        }
    }

    public final boolean isRoundWindow() {
        return this.mContext.getResources().getConfiguration().isScreenRound();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setDefaultDisplay(WindowManagerPolicy.DisplayContentInfo displayContentInfo) {
        this.mDefaultDisplay = displayContentInfo.getDisplay();
        DisplayRotation displayRotation = displayContentInfo.getDisplayRotation();
        this.mDefaultDisplayRotation = displayRotation;
        this.mDefaultDisplayPolicy = displayRotation.getDisplayPolicy();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setCoverViewDisplay(WindowManagerPolicy.DisplayContentInfo displayContentInfo) {
        this.mCoverViewDisplayPolicy = displayContentInfo.getDisplayRotation().getDisplayPolicy();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setDexDisplay(WindowManagerPolicy.DisplayContentInfo displayContentInfo) {
        this.mDexDisplay = displayContentInfo.getDisplay();
    }

    class Injector {
        public final Context mContext;
        public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

        public Injector(Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
            this.mContext = context;
            this.mWindowManagerFuncs = windowManagerFuncs;
        }

        public Context getContext() {
            return this.mContext;
        }

        public WindowManagerPolicy.WindowManagerFuncs getWindowManagerFuncs() {
            return this.mWindowManagerFuncs;
        }

        public AccessibilityShortcutController getAccessibilityShortcutController(Context context, Handler handler, int i) {
            return new AccessibilityShortcutController(context, handler, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ GlobalActions lambda$getGlobalActionsFactory$0() {
            return new GlobalActions(this.mContext, this.mWindowManagerFuncs);
        }

        public Supplier getGlobalActionsFactory() {
            return new Supplier() { // from class: com.android.server.policy.PhoneWindowManager$Injector$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    GlobalActions lambda$getGlobalActionsFactory$0;
                    lambda$getGlobalActionsFactory$0 = PhoneWindowManager.Injector.this.lambda$getGlobalActionsFactory$0();
                    return lambda$getGlobalActionsFactory$0;
                }
            };
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void init(Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        init(new Injector(context, windowManagerFuncs));
    }

    public void init(Injector injector) {
        int integer;
        int i;
        int i2;
        int i3;
        int i4;
        this.mContext = injector.getContext();
        this.mWindowManagerFuncs = injector.getWindowManagerFuncs();
        this.mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mActivityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mInputManagerInternal = (InputManagerInternal) LocalServices.getService(InputManagerInternal.class);
        this.mDreamManagerInternal = (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
        this.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        this.mSensorPrivacyManager = (SensorPrivacyManager) this.mContext.getSystemService(SensorPrivacyManager.class);
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        this.mDisplayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManager packageManager = this.mContext.getPackageManager();
        this.mPackageManager = packageManager;
        this.mHasFeatureWatch = packageManager.hasSystemFeature("android.hardware.type.watch");
        this.mHasFeatureLeanback = this.mPackageManager.hasSystemFeature("android.software.leanback");
        this.mHasFeatureAuto = this.mPackageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mHasFeatureHdmiCec = this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec");
        this.mAccessibilityShortcutController = injector.getAccessibilityShortcutController(this.mContext, new Handler(), this.mCurrentUserId);
        this.mGlobalActionsFactory = injector.getGlobalActionsFactory();
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mLogger = new MetricsLogger();
        this.mScreenOffSleepTokenAcquirer = this.mActivityTaskManagerInternal.createSleepTokenAcquirer("ScreenOff");
        Resources resources = this.mContext.getResources();
        this.mWakeOnDpadKeyPress = resources.getBoolean(R.bool.config_systemCaptionsServiceCallsEnabled);
        this.mWakeOnAssistKeyPress = resources.getBoolean(R.bool.config_switch_phone_on_voice_reg_state_change);
        this.mWakeOnBackKeyPress = resources.getBoolean(R.bool.config_syncstorageengine_masterSyncAutomatically);
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_dozePickupGestureEnabled);
        boolean z2 = SystemProperties.getBoolean("persist.debug.force_burn_in", false);
        if (z || z2) {
            if (z2) {
                integer = isRoundWindow() ? 6 : -1;
                i = -8;
                i3 = -8;
                i2 = 8;
                i4 = -4;
            } else {
                Resources resources2 = this.mContext.getResources();
                int integer2 = resources2.getInteger(R.integer.config_defaultBinderHeavyHitterAutoSamplerBatchSize);
                int integer3 = resources2.getInteger(R.integer.config_debugSystemServerPssThresholdBytes);
                int integer4 = resources2.getInteger(R.integer.config_defaultBinderHeavyHitterWatcherBatchSize);
                int integer5 = resources2.getInteger(R.integer.config_defaultAnalogClockSecondsHandFps);
                integer = resources2.getInteger(R.integer.config_defaultAlarmVibrationIntensity);
                i = integer2;
                i2 = integer3;
                i3 = integer4;
                i4 = integer5;
            }
            this.mBurnInProtectionHelper = new BurnInProtectionHelper(this.mContext, i, i2, i3, i4, integer);
        }
        PolicyHandler policyHandler = new PolicyHandler();
        this.mHandler = policyHandler;
        this.mWakeGestureListener = new MyWakeGestureListener(this.mContext, policyHandler);
        SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSettingsObserver = settingsObserver;
        settingsObserver.observe();
        this.mModifierShortcutManager = new ModifierShortcutManager(this.mContext, this.mExt);
        this.mUiMode = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceTransitionTimeIncrease);
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mHomeIntent = intent;
        intent.addCategory("android.intent.category.HOME");
        this.mHomeIntent.addFlags(270532608);
        this.mEnableCarDockHomeCapture = this.mContext.getResources().getBoolean(R.bool.config_dozePulsePickup);
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
        this.mBroadcastWakeLock = powerManager.newWakeLock(1, "PhoneWindowManager.mBroadcastWakeLock");
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "PhoneWindowManager.mPowerKeyWakeLock");
        this.mPowerKeyWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mEnableShiftMenuBugReports = "1".equals(SystemProperties.get("ro.debuggable"));
        this.mLidKeyboardAccessibility = this.mContext.getResources().getInteger(R.integer.config_maxNumVisibleRecentTasks);
        this.mLidNavigationAccessibility = this.mContext.getResources().getInteger(R.integer.config_maxNumVisibleRecentTasks_lowRam);
        boolean z3 = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromKey);
        this.mAllowTheaterModeWakeFromKey = z3;
        this.mAllowTheaterModeWakeFromPowerKey = z3 || this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromPowerKey);
        this.mAllowTheaterModeWakeFromMotion = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromMotion);
        this.mAllowTheaterModeWakeFromMotionWhenNotDreaming = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromMotionWhenNotDreaming);
        this.mAllowTheaterModeWakeFromCameraLens = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromCameraLens);
        this.mAllowTheaterModeWakeFromLidSwitch = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromLidSwitch);
        this.mAllowTheaterModeWakeFromWakeGesture = this.mContext.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromGesture);
        this.mGoToSleepOnButtonPressTheaterMode = this.mContext.getResources().getBoolean(R.bool.config_enableVirtualDeviceManager);
        this.mSupportLongPressPowerWhenNonInteractive = this.mContext.getResources().getBoolean(R.bool.config_sf_slowBlur);
        this.mShortPressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_sidefpsKeyguardPowerPressWindow);
        this.mLongPressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_mdc_initial_max_retry);
        this.mLongPressOnPowerAssistantTimeoutMs = this.mContext.getResources().getInteger(R.integer.config_mediaOutputSwitchDialogVersion);
        this.mVeryLongPressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_virtualKeyQuietTimeMillis);
        this.mDoublePressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_externalDisplayPeakHeight);
        this.mPowerDoublePressTargetActivity = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.deprecated_abi_message));
        this.mTriplePressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_veryLongPressOnPowerBehavior);
        if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
            this.mExt.mQuintuplePressOnPowerBehavior = this.mContext.getResources().getInteger(R.integer.config_safe_media_volume_usb_mB);
        }
        this.mShortPressOnSleepBehavior = this.mContext.getResources().getInteger(R.integer.config_sidefpsPostAuthDowntime);
        this.mAllowStartActivityForLongPressOnPowerDuringSetup = this.mContext.getResources().getBoolean(R.bool.config_allowStartActivityForLongPressOnPowerInSetup);
        this.mHapticTextHandleEnabled = this.mContext.getResources().getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser);
        this.mUseTvRouting = AudioSystem.getPlatformType(this.mContext) == 2;
        this.mHandleVolumeKeysInWM = this.mContext.getResources().getBoolean(R.bool.config_enable_cellular_on_boot_default);
        this.mWakeUpToLastStateTimeout = this.mContext.getResources().getInteger(R.integer.config_windowOutsetBottom);
        this.mSearchKeyBehavior = this.mContext.getResources().getInteger(R.integer.config_shutdownBatteryTemperature);
        this.mSearchKeyTargetActivity = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.ext_media_browse_action));
        readConfigurationDependentBehaviors();
        this.mDisplayFoldController = DisplayFoldController.create(this.mContext, 0);
        this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
        intentFilter.addAction(UiModeManager.ACTION_EXIT_CAR_MODE);
        intentFilter.addAction(UiModeManager.ACTION_ENTER_DESK_MODE);
        intentFilter.addAction(UiModeManager.ACTION_EXIT_DESK_MODE);
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        Intent registerReceiver = this.mContext.registerReceiver(this.mDockReceiver, intentFilter);
        if (registerReceiver != null) {
            this.mDefaultDisplayPolicy.setDockMode(registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", 0));
        }
        this.mContext.registerReceiver(this.mMultiuserReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"));
        this.mVibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        this.mSafeModeEnabledVibePattern = getLongIntArray(this.mContext.getResources(), 17236289);
        this.mGlobalKeyManager = new GlobalKeyManager(this.mContext);
        initializeHdmiState();
        if (!this.mPowerManager.isInteractive()) {
            startedGoingToSleep(0, 2);
            finishedGoingToSleep(0, 2);
        }
        this.mWindowManagerInternal.registerAppTransitionListener(new WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.policy.PhoneWindowManager.5
            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public int onAppTransitionStartingLocked(long j, long j2) {
                return PhoneWindowManager.this.handleTransitionForKeyguardLw(false, false);
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionCancelledLocked(boolean z4) {
                PhoneWindowManager.this.handleTransitionForKeyguardLw(z4, true);
                synchronized (PhoneWindowManager.this.mLock) {
                    PhoneWindowManager.this.mLockAfterAppTransitionFinished = false;
                }
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionFinishedLocked(IBinder iBinder) {
                synchronized (PhoneWindowManager.this.mLock) {
                    PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                    if (phoneWindowManager.mLockAfterAppTransitionFinished) {
                        phoneWindowManager.mLockAfterAppTransitionFinished = false;
                        phoneWindowManager.lockNow(null);
                    }
                }
            }
        });
        this.mKeyguardDrawnTimeout = this.mContext.getResources().getInteger(R.integer.config_longPressOnStemPrimaryBehavior);
        this.mKeyguardDelegate = new KeyguardServiceDelegate(this.mContext, new KeyguardStateMonitor.StateCallback() { // from class: com.android.server.policy.PhoneWindowManager.6
            @Override // com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback
            public void onTrustedChanged() {
                PhoneWindowManager.this.mWindowManagerFuncs.notifyKeyguardTrustedChanged();
            }

            @Override // com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback
            public void onShowingChanged() {
                PhoneWindowManager.this.mWindowManagerFuncs.onKeyguardShowingAndNotOccludedChanged();
            }
        });
        initKeyCombinationRules();
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        phoneWindowManagerExt.mKeyCustomizationPolicy = new KeyCustomizationManager(this.mContext, phoneWindowManagerExt);
        initSingleKeyGestureRules();
        this.mSideFpsEventHandler = new SideFpsEventHandler(this.mContext, this.mHandler, this.mPowerManager);
    }

    public final void initKeyCombinationRules() {
        this.mKeyCombinationManager = new KeyCombinationManager(this.mHandler, this.mInputManagerInternal);
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_enableAutoPowerModes);
        this.mExt.initKeyCombinationRules();
        int i = 25;
        if (z) {
            int i2 = 26;
            this.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(i, i2) { // from class: com.android.server.policy.PhoneWindowManager.7
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public boolean preCondition() {
                    boolean z2 = CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION;
                    PhoneWindowManager.this.mExt.setScreenshotEnabled();
                    return super.preCondition();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void execute() {
                    PhoneWindowManager.this.mPowerKeyHandled = true;
                    if (CoreRune.FW_GLOBAL_ACTION_BY_SIDE_KEY_COMBINATION) {
                        PhoneWindowManager.this.interceptGlobalActionChord();
                    }
                    boolean z2 = CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION;
                    PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManager.this.mExt;
                    if (phoneWindowManagerExt.mScreenshotEnabled) {
                        phoneWindowManagerExt.interceptKeyCombinationScreenshotChord();
                    }
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    if (CoreRune.FW_GLOBAL_ACTION_BY_SIDE_KEY_COMBINATION) {
                        PhoneWindowManager.this.cancelPendingGlobalActionChord();
                    }
                    PhoneWindowManager.this.mExt.mWindowManagerFuncs.cancelPendingTakeScreenshotRunnable();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel(KeyEvent keyEvent, boolean z2) {
                    if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
                        PhoneWindowManager.this.mExt.checkKeyCombinationScreenshotChord(keyEvent, z2);
                    }
                }
            });
            if (this.mHasFeatureWatch) {
                this.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(i2, 264) { // from class: com.android.server.policy.PhoneWindowManager.8
                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public void execute() {
                        PhoneWindowManager.this.mPowerKeyHandled = true;
                        PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                        phoneWindowManager.interceptScreenshotChord(1, phoneWindowManager.getScreenshotChordLongPressDelay());
                    }

                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public void cancel() {
                        PhoneWindowManager.this.cancelPendingScreenshotChordAction();
                    }
                });
            }
        }
        this.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(i, 24) { // from class: com.android.server.policy.PhoneWindowManager.9
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public boolean preCondition() {
                return PhoneWindowManager.this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(PhoneWindowManager.this.isKeyguardLocked());
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void execute() {
                PhoneWindowManager.this.interceptAccessibilityShortcutChord();
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void cancel() {
                PhoneWindowManager.this.cancelPendingAccessibilityShortcutAction();
            }
        });
        if (this.mHasFeatureLeanback) {
            int i3 = 4;
            this.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(i3, 20) { // from class: com.android.server.policy.PhoneWindowManager.11
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public long getKeyInterceptDelayMs() {
                    return 0L;
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void execute() {
                    PhoneWindowManager.this.mBackKeyHandled = true;
                    PhoneWindowManager.this.interceptAccessibilityGestureTv();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    PhoneWindowManager.this.cancelAccessibilityGestureTv();
                }
            });
            this.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(23, i3) { // from class: com.android.server.policy.PhoneWindowManager.12
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public long getKeyInterceptDelayMs() {
                    return 0L;
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void execute() {
                    PhoneWindowManager.this.mBackKeyHandled = true;
                    PhoneWindowManager.this.interceptBugreportGestureTv();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    PhoneWindowManager.this.cancelBugreportGestureTv();
                }
            });
        }
    }

    public final class PowerKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public PowerKeyRule() {
            super(26);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public boolean supportLongPress() {
            return PhoneWindowManager.this.hasLongPressOnPowerBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public boolean supportVeryLongPress() {
            return PhoneWindowManager.this.hasVeryLongPressOnPowerBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public int getMaxMultiPressCount() {
            return PhoneWindowManager.this.getMaxMultiPressPowerCount();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
            phoneWindowManager.powerPress(j, 1, phoneWindowManager.mSingleKeyGestureDetector.beganFromNonInteractive(), keyEvent);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public long getLongPressTimeoutMs() {
            if (PhoneWindowManager.this.getResolvedLongPressOnPowerBehavior() == 5) {
                return PhoneWindowManager.this.mLongPressOnPowerAssistantTimeoutMs;
            }
            return super.getLongPressTimeoutMs();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            if (PhoneWindowManager.this.mSingleKeyGestureDetector.beganFromNonInteractive() && !PhoneWindowManager.this.mSupportLongPressPowerWhenNonInteractive) {
                Slog.v(StartingSurfaceController.TAG, "Not support long press power when device is not interactive.");
            } else {
                this.mIsKeyLongPressed = true;
                PhoneWindowManager.this.powerLongPress(j, keyEvent, i);
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onVeryLongPress(long j) {
            PhoneWindowManager.this.mActivityManagerInternal.prepareForPossibleShutdown();
            PhoneWindowManager.this.powerVeryLongPress();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onMultiPress(long j, int i, KeyEvent keyEvent) {
            PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
            phoneWindowManager.powerPress(j, i, phoneWindowManager.mSingleKeyGestureDetector.beganFromNonInteractive(), keyEvent);
        }
    }

    public final class StemPrimaryKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public StemPrimaryKeyRule() {
            super(264);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public boolean supportLongPress() {
            return PhoneWindowManager.this.hasLongPressOnStemPrimaryBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public int getMaxMultiPressCount() {
            return PhoneWindowManager.this.getMaxMultiPressStemPrimaryCount();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            PhoneWindowManager.this.stemPrimaryPress(1);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            PhoneWindowManager.this.stemPrimaryLongPress();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onMultiPress(long j, int i, KeyEvent keyEvent) {
            PhoneWindowManager.this.stemPrimaryPress(i);
        }
    }

    public final void initSingleKeyGestureRules() {
        SingleKeyGestureDetector singleKeyGestureDetector = SingleKeyGestureDetector.get(this.mContext, this.mExt.mKeyCustomizationPolicy);
        this.mSingleKeyGestureDetector = singleKeyGestureDetector;
        singleKeyGestureDetector.addRule(new PowerKeyRule());
        if (hasStemPrimaryBehavior()) {
            this.mSingleKeyGestureDetector.addRule(new StemPrimaryKeyRule());
        }
    }

    public final void readConfigurationDependentBehaviors() {
        Resources resources = this.mContext.getResources();
        if (CoreRune.FW_SUPPORT_SEARCLE) {
            this.mLongPressOnHomeBehavior = 4;
        } else if (CoreRune.FW_SUPPORT_BIXBY_TOUCH_CHN) {
            this.mLongPressOnHomeBehavior = 101;
        } else {
            this.mLongPressOnHomeBehavior = resources.getInteger(R.integer.config_maximumScreenDimDuration);
        }
        int i = this.mLongPressOnHomeBehavior;
        if (i < 0 || i > 101) {
            this.mLongPressOnHomeBehavior = 0;
        }
        int integer = resources.getInteger(R.integer.config_externalDisplayPeakWidth);
        this.mDoubleTapOnHomeBehavior = integer;
        if (integer < 0 || integer > 2) {
            this.mDoubleTapOnHomeBehavior = 0;
        }
        this.mShortPressOnWindowBehavior = 0;
        if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
            this.mShortPressOnWindowBehavior = 1;
        }
        this.mShortPressOnStemPrimaryBehavior = this.mContext.getResources().getInteger(R.integer.config_sidefpsSkipWaitForPowerAcquireMessage);
        this.mLongPressOnStemPrimaryBehavior = this.mContext.getResources().getInteger(R.integer.config_mediaRouter_builtInSpeakerSuitability);
        this.mDoublePressOnStemPrimaryBehavior = this.mContext.getResources().getInteger(R.integer.config_externalDisplayPeakRefreshRate);
        this.mTriplePressOnStemPrimaryBehavior = this.mContext.getResources().getInteger(R.integer.config_veryLongPressTimeout);
    }

    public void updateSettings() {
        boolean z;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        synchronized (this.mLock) {
            this.mEndcallBehavior = Settings.System.getIntForUser(contentResolver, "end_button_behavior", 2, -2);
            this.mIncallPowerBehavior = Settings.Secure.getIntForUser(contentResolver, "incall_power_button_behavior", 1, -2);
            this.mIncallBackBehavior = Settings.Secure.getIntForUser(contentResolver, "incall_back_button_behavior", 0, -2);
            this.mSystemNavigationKeysEnabled = Settings.Secure.getIntForUser(contentResolver, "system_navigation_keys_enabled", 0, -2) == 1;
            this.mRingerToggleChord = Settings.Secure.getIntForUser(contentResolver, "volume_hush_gesture", 0, -2);
            this.mPowerButtonSuppressionDelayMillis = Settings.Global.getInt(contentResolver, "power_button_suppression_delay_after_gesture_wake", 800);
            if (!this.mContext.getResources().getBoolean(R.bool.config_swipeDisambiguation)) {
                this.mRingerToggleChord = 0;
            }
            boolean z2 = Settings.Secure.getIntForUser(contentResolver, "wake_gesture_enabled", 0, -2) != 0;
            if (this.mWakeGestureEnabledSetting != z2) {
                this.mWakeGestureEnabledSetting = z2;
                updateWakeGestureListenerLp();
            }
            this.mLockScreenTimeout = Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 0, -2);
            String stringForUser = Settings.Secure.getStringForUser(contentResolver, "default_input_method", -2);
            boolean z3 = stringForUser != null && stringForUser.length() > 0;
            if (this.mHasSoftInput != z3) {
                this.mHasSoftInput = z3;
                z = true;
            } else {
                z = false;
            }
            int i = Settings.Global.getInt(contentResolver, "power_button_long_press", this.mContext.getResources().getInteger(R.integer.config_mdc_initial_max_retry));
            int i2 = Settings.Global.getInt(contentResolver, "power_button_very_long_press", this.mContext.getResources().getInteger(R.integer.config_virtualKeyQuietTimeMillis));
            if (this.mLongPressOnPowerBehavior != i || this.mVeryLongPressOnPowerBehavior != i2) {
                this.mLongPressOnPowerBehavior = i;
                this.mVeryLongPressOnPowerBehavior = i2;
            }
            this.mLongPressOnPowerAssistantTimeoutMs = Settings.Global.getLong(this.mContext.getContentResolver(), "power_button_long_press_duration_ms", this.mContext.getResources().getInteger(R.integer.config_mediaOutputSwitchDialogVersion));
            this.mPowerVolUpBehavior = Settings.Global.getInt(contentResolver, "key_chord_power_volume_up", this.mContext.getResources().getInteger(R.integer.config_longPressOnPowerDurationMs));
            boolean z4 = Settings.Secure.getIntForUser(contentResolver, "stylus_buttons_enabled", 1, -2) == 1;
            this.mStylusButtonsEnabled = z4;
            this.mInputManagerInternal.setStylusButtonMotionEventsEnabled(z4);
        }
        if (z) {
            updateRotation(true);
        }
    }

    public final DreamManagerInternal getDreamManagerInternal() {
        if (this.mDreamManagerInternal == null) {
            this.mDreamManagerInternal = (DreamManagerInternal) LocalServices.getService(DreamManagerInternal.class);
        }
        return this.mDreamManagerInternal;
    }

    public final void updateWakeGestureListenerLp() {
        if (shouldEnableWakeGestureLp()) {
            this.mWakeGestureListener.requestWakeUpTrigger();
        } else {
            this.mWakeGestureListener.cancelWakeUpTrigger();
        }
    }

    public final boolean shouldEnableWakeGestureLp() {
        return this.mWakeGestureEnabledSetting && !this.mDefaultDisplayPolicy.isAwake() && !(getLidBehavior() == 1 && this.mDefaultDisplayPolicy.getLidState() == 0) && this.mWakeGestureListener.isSupported();
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.policy.WindowManagerPolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkAddPermission(int i, boolean z, String str, int[] iArr) {
        ApplicationInfo applicationInfo;
        int noteOpNoThrow;
        if (z && this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0) {
            return -8;
        }
        iArr[0] = -1;
        if ((i < 1 || i > 99) && ((i < 1000 || i > 1999) && (i < 2000 || i > 2999))) {
            return -10;
        }
        if (i < 2000 || i > 2999) {
            return 0;
        }
        if (!WindowManager.LayoutParams.isSystemAlertWindowType(i)) {
            if (i == 2005) {
                iArr[0] = 45;
                return 0;
            }
            if (i != 2011 && i != 2013 && i != 2024 && i != 2035 && i != 2037) {
                switch (i) {
                    case 2030:
                    case 2031:
                    case 2032:
                        break;
                    default:
                        return this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") == 0 ? 0 : -8;
                }
            }
            return 0;
        }
        iArr[0] = 24;
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) == 1000) {
            return 0;
        }
        try {
            try {
                applicationInfo = this.mPackageManager.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(callingUid));
            } catch (PackageManager.NameNotFoundException unused) {
                applicationInfo = null;
                if (applicationInfo != null) {
                }
                if (this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0) {
                }
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        if (applicationInfo != null || (i != 2038 && applicationInfo.targetSdkVersion >= 26)) {
            return this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0 ? 0 : -8;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.SYSTEM_APPLICATION_OVERLAY") == 0 || (noteOpNoThrow = this.mAppOpsManager.noteOpNoThrow(iArr[0], callingUid, str, (String) null, "check-add")) == 0 || noteOpNoThrow == 1) {
            return 0;
        }
        return noteOpNoThrow != 2 ? this.mContext.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0 ? 0 : -8 : applicationInfo.targetSdkVersion < 23 ? 0 : -8;
    }

    public void readLidState() {
        this.mDefaultDisplayPolicy.setLidState(this.mWindowManagerFuncs.getLidState());
    }

    public final void readCameraLensCoverState() {
        this.mCameraLensCoverState = this.mWindowManagerFuncs.getCameraLensCoverState();
    }

    public final boolean isHidden(int i) {
        int lidState = this.mDefaultDisplayPolicy.getLidState();
        return i != 1 ? i == 2 && lidState == 1 : lidState == 0;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void adjustConfigurationLw(Configuration configuration, int i, int i2) {
        this.mHaveBuiltInKeyboard = (i & 1) != 0;
        readConfigurationDependentBehaviors();
        readLidState();
        if (configuration.keyboard == 1 || (i == 1 && isHidden(this.mLidKeyboardAccessibility))) {
            configuration.hardKeyboardHidden = 2;
            if (!this.mHasSoftInput) {
                configuration.keyboardHidden = 2;
            }
        }
        if (configuration.navigation == 1 || (i2 == 1 && isHidden(this.mLidNavigationAccessibility))) {
            configuration.navigationHidden = 2;
        }
        this.mExt.adjustConfigurationLw(configuration);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardHostWindow(WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 2040;
    }

    public static void awakenDreams() {
        IDreamManager dreamManager = getDreamManager();
        if (dreamManager != null) {
            try {
                dreamManager.awaken();
            } catch (RemoteException unused) {
            }
        }
    }

    public static IDreamManager getDreamManager() {
        return IDreamManager.Stub.asInterface(ServiceManager.checkService("dreams"));
    }

    public TelecomManager getTelecommService() {
        return (TelecomManager) this.mContext.getSystemService("telecom");
    }

    public NotificationManager getNotificationService() {
        return (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
    }

    public static IAudioService getAudioService() {
        IAudioService asInterface = IAudioService.Stub.asInterface(ServiceManager.checkService("audio"));
        if (asInterface == null) {
            Log.w(StartingSurfaceController.TAG, "Unable to find IAudioService interface.");
        }
        return asInterface;
    }

    public boolean keyguardOn() {
        return isKeyguardShowingAndNotOccluded() || inKeyguardRestrictedKeyInputMode();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0540  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x054d  */
    @Override // com.android.server.policy.WindowManagerPolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i) {
        int handleHomeShortcuts;
        InputDevice device;
        int i2;
        int i3;
        boolean keyguardOn = keyguardOn();
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int metaState = keyEvent.getMetaState();
        int flags = keyEvent.getFlags();
        boolean z = keyEvent.getAction() == 0;
        boolean isCanceled = keyEvent.isCanceled();
        int adjustKeyEventDisplayIdForDex = this.mExt.adjustKeyEventDisplayIdForDex(keyEvent);
        if (DEBUG_INPUT || CoreRune.SAFE_DEBUG || PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST.contains(Integer.valueOf(keyCode))) {
            Log.d(StartingSurfaceController.TAG, "interceptKeyTi keyCode=" + keyCode + " down=" + z + " repeatCount=" + repeatCount + " keyguardOn=" + keyguardOn + " canceled=" + isCanceled);
        }
        if (this.mKeyCombinationManager.isKeyConsumed(keyEvent)) {
            return -1L;
        }
        if ((flags & 1024) == 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            long keyInterceptTimeout = this.mKeyCombinationManager.getKeyInterceptTimeout(keyCode);
            if (uptimeMillis < keyInterceptTimeout) {
                return keyInterceptTimeout - uptimeMillis;
            }
        }
        if (this.mPendingMetaAction && !KeyEvent.isMetaKey(keyCode)) {
            this.mPendingMetaAction = false;
        }
        if (this.mPendingCapsLockToggle && !KeyEvent.isMetaKey(keyCode) && !KeyEvent.isAltKey(keyCode)) {
            this.mPendingCapsLockToggle = false;
        }
        long interceptKeyBeforeDispatching = this.mExt.interceptKeyBeforeDispatching(iBinder, keyEvent, i);
        if (interceptKeyBeforeDispatching >= -1) {
            Log.d(StartingSurfaceController.TAG, "interceptKeyTi interceptKeyResult=" + interceptKeyBeforeDispatching);
            return interceptKeyBeforeDispatching;
        }
        if (isUserSetupComplete() && !keyguardOn && !this.mExt.hasMetaKeyPass() && this.mModifierShortcutManager.interceptKey(keyEvent)) {
            dismissKeyboardShortcutsMenu();
            this.mPendingMetaAction = false;
            this.mPendingCapsLockToggle = false;
            if (CoreRune.FW_KEY_SA_LOGGING) {
                String saLoggerEventId = this.mExt.mKeyboardShortcutPolicy.getSaLoggerEventId(keyCode);
                if (!TextUtils.isEmpty(saLoggerEventId)) {
                    PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
                    phoneWindowManagerExt.sendCoreSaLoggingDimension(saLoggerEventId, phoneWindowManagerExt.mKeyboardShortcutPolicy.getShortcutSettingsValue(keyCode));
                }
            }
            return -1L;
        }
        switch (keyCode) {
            case 3:
                if (!this.mSingleKeyGestureDetector.hasRule(keyCode)) {
                    handleHomeShortcuts = handleHomeShortcuts(adjustKeyEventDisplayIdForDex, iBinder, keyEvent);
                    return handleHomeShortcuts;
                }
                if (!isValidGlobalKey(keyCode) && this.mGlobalKeyManager.handleGlobalKey(this.mContext, keyCode, keyEvent)) {
                    return -1L;
                }
                if ((65536 & metaState) == 0 && !this.mExt.shouldDispatchMetaCombos(keyCode)) {
                    return -1L;
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                    this.mExt.sendSALoggingForDispatchingKey(keyEvent, i);
                }
                return 0L;
            case 19:
                if (z && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed() && repeatCount == 0) {
                    StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
                    if (statusBarManagerInternal != null) {
                        statusBarManagerInternal.goToFullscreenFromSplit();
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                    break;
                }
                if ((65536 & metaState) == 0) {
                    break;
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 21:
                if (z && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed() && repeatCount == 0) {
                    enterStageSplitFromRunningApp(true);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 22:
                if (z && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed() && repeatCount == 0) {
                    enterStageSplitFromRunningApp(false);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 24:
            case 25:
            case FrameworkStatsLog.f636xd07885aa /* 164 */:
                if (this.mUseTvRouting || this.mHandleVolumeKeysInWM) {
                    dispatchDirectAudioEvent(keyEvent);
                    return -1L;
                }
                if (this.mDefaultDisplayPolicy.isPersistentVrModeEnabled() && (device = keyEvent.getDevice()) != null && !device.isExternal()) {
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 29:
                if (z && keyEvent.isMetaPressed()) {
                    launchAssistAction("android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD", keyEvent.getDeviceId(), keyEvent.getEventTime(), 0);
                    if (CoreRune.FW_KEY_SA_LOGGING && repeatCount == 0) {
                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.mExt;
                        phoneWindowManagerExt2.sendCoreSaLoggingDimension("PKBD0056", phoneWindowManagerExt2.mKeyboardShortcutPolicy.getShortcutSettingsValue(keyCode));
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 36:
            case 66:
                if (keyEvent.isMetaPressed()) {
                    if (CoreRune.FW_KEY_SA_LOGGING) {
                        long handleHomeShortcuts2 = handleHomeShortcuts(adjustKeyEventDisplayIdForDex, iBinder, keyEvent);
                        if (handleHomeShortcuts2 == -1 && z && repeatCount == 0) {
                            if (keyCode == 36) {
                                PhoneWindowManagerExt phoneWindowManagerExt3 = this.mExt;
                                phoneWindowManagerExt3.sendCoreSaLoggingDimension("PKBD0060", phoneWindowManagerExt3.mKeyboardShortcutPolicy.getShortcutSettingsValue(keyCode));
                            } else if (keyCode == 66) {
                                CoreSaLogger.logForBasic("PKBD0054");
                            }
                        }
                        return handleHomeShortcuts2;
                    }
                    handleHomeShortcuts = handleHomeShortcuts(adjustKeyEventDisplayIdForDex, iBinder, keyEvent);
                    return handleHomeShortcuts;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 37:
                if (z && keyEvent.isMetaPressed()) {
                    showSystemSettings();
                    if (CoreRune.FW_KEY_SA_LOGGING && repeatCount == 0) {
                        PhoneWindowManagerExt phoneWindowManagerExt4 = this.mExt;
                        phoneWindowManagerExt4.sendCoreSaLoggingDimension("PKBD0061", phoneWindowManagerExt4.mKeyboardShortcutPolicy.getShortcutSettingsValue(keyCode));
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 42:
                if (z && keyEvent.isMetaPressed()) {
                    if (keyEvent.isCtrlPressed()) {
                        sendSystemKeyToStatusBarAsync(keyEvent);
                    } else {
                        toggleNotificationPanel(adjustKeyEventDisplayIdForDex);
                        if (CoreRune.FW_KEY_SA_LOGGING && repeatCount == 0) {
                            CoreSaLogger.logForBasic("PKBD0063");
                        }
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 47:
                if (z && keyEvent.isMetaPressed() && ((keyEvent.isCtrlPressed() || keyEvent.isShiftPressed()) && repeatCount == 0)) {
                    if (adjustKeyEventDisplayIdForDex == 1) {
                        i2 = 1;
                    } else {
                        i2 = keyEvent.isShiftPressed() ? 2 : 1;
                    }
                    this.mExt.mWindowManagerFuncs.sendTakeScreenshotRunnable(i2, adjustKeyEventDisplayIdForDex == -1 ? 0 : adjustKeyEventDisplayIdForDex);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 48:
                if (z && keyEvent.isMetaPressed()) {
                    toggleTaskbar();
                    if (CoreRune.FW_KEY_SA_LOGGING && repeatCount == 0) {
                        CoreSaLogger.logForBasic("PKBD0066");
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 57:
            case 58:
                if (!this.mExt.hasMetaKeyPass()) {
                    if (z) {
                        if (keyEvent.isMetaPressed()) {
                            this.mPendingCapsLockToggle = true;
                            this.mPendingMetaAction = false;
                        } else {
                            this.mPendingCapsLockToggle = false;
                        }
                    } else {
                        if (this.mAltTabConsumedByDeX) {
                            this.mAltTabConsumedByDeX = false;
                            this.mActivityTaskManagerInternal.releaseAltTabKeyConsumer();
                        }
                        int i4 = this.mRecentAppsHeldModifiers;
                        if (i4 != 0 && (i4 & metaState) == 0) {
                            this.mRecentAppsHeldModifiers = 0;
                            hideRecentApps(true, false, adjustKeyEventDisplayIdForDex);
                            return -1L;
                        }
                        if (this.mPendingCapsLockToggle) {
                            this.mInputManagerInternal.toggleCapsLock(keyEvent.getDeviceId());
                            this.mPendingCapsLockToggle = false;
                            return -1L;
                        }
                    }
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 61:
                if (!this.mExt.hasMetaKeyPass()) {
                    if (z && keyEvent.isMetaPressed()) {
                        if (!keyguardOn && isUserSetupComplete()) {
                            showRecentApps(false);
                            if (CoreRune.FW_KEY_SA_LOGGING && repeatCount == 0) {
                                CoreSaLogger.logForBasic("PKBD0055");
                            }
                            return -1L;
                        }
                    } else if (z && repeatCount == 0 && this.mRecentAppsHeldModifiers == 0 && !keyguardOn && isUserSetupComplete()) {
                        int modifiers = keyEvent.getModifiers();
                        boolean metaStateHasModifiers = KeyEvent.metaStateHasModifiers(modifiers, 4098);
                        if (KeyEvent.metaStateHasModifiers(modifiers, 2) || metaStateHasModifiers) {
                            dismissKeyboardShortcutsMenu();
                            if (this.mExt.getDexMode() != 3 && this.mActivityTaskManagerInternal.handleAltTabKeyIfNeeded()) {
                                this.mAltTabConsumedByDeX = true;
                                return -1L;
                            }
                            this.mRecentAppsHeldModifiers = modifiers;
                            showRecentApps(true, adjustKeyEventDisplayIdForDex);
                            if (CoreRune.FW_KEY_SA_LOGGING) {
                                if (metaStateHasModifiers) {
                                    CoreSaLogger.logForBasic("PKBD0007");
                                } else {
                                    CoreSaLogger.logForBasic("PKBD0052");
                                }
                            }
                            return -1L;
                        }
                    }
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 62:
                if ((458752 & metaState) == 0) {
                    return 0L;
                }
                if (z && repeatCount == 0) {
                    sendSwitchKeyboardLayout(keyEvent, (metaState & 193) != 0 ? -1 : 1);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 76:
                if (z && repeatCount == 0 && keyEvent.isMetaPressed() && !keyguardOn) {
                    toggleKeyboardShortcutsMenu(keyEvent.getDeviceId(), adjustKeyEventDisplayIdForDex);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 82:
                if (z && repeatCount == 0 && this.mEnableShiftMenuBugReports && (metaState & 1) == 1) {
                    this.mContext.sendOrderedBroadcastAsUser(new Intent("android.intent.action.BUG_REPORT"), UserHandle.CURRENT, null, null, null, 0, null, null);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 83:
                if (!z) {
                    toggleNotificationPanel();
                }
                return -1L;
            case 84:
                if (z && repeatCount == 0 && !keyguardOn() && this.mSearchKeyBehavior == 1) {
                    launchTargetSearchActivity();
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 113:
            case 114:
                if (!z && (i3 = this.mRecentAppsHeldModifiers) != 0 && (i3 & metaState) == 0) {
                    this.mRecentAppsHeldModifiers = 0;
                    hideRecentApps(true, false, adjustKeyEventDisplayIdForDex);
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case 117:
            case 118:
                if (!this.mExt.hasMetaKeyPass()) {
                    if (z) {
                        if (keyEvent.isAltPressed()) {
                            this.mPendingCapsLockToggle = true;
                            this.mPendingMetaAction = false;
                        } else {
                            this.mPendingCapsLockToggle = false;
                            this.mPendingMetaAction = true;
                        }
                    } else if (this.mPendingCapsLockToggle) {
                        this.mInputManagerInternal.toggleCapsLock(keyEvent.getDeviceId());
                        this.mPendingCapsLockToggle = false;
                    } else if (this.mPendingMetaAction) {
                        if (!isCanceled) {
                            launchAllAppsViaA11y();
                            if (CoreRune.FW_KEY_SA_LOGGING) {
                                CoreSaLogger.logForBasic("PKBD0024");
                            }
                        }
                        this.mPendingMetaAction = false;
                    }
                    return -1L;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
            case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED /* 187 */:
                if (!keyguardOn) {
                    if (this.mExt.hasSystemKeyInfo(keyCode, 3)) {
                        Log.i(StartingSurfaceController.TAG, "skip single press recent, requestedSystemKey");
                        if (!isValidGlobalKey(keyCode)) {
                        }
                        if ((65536 & metaState) == 0) {
                        }
                        if (CoreRune.FW_KEY_SA_LOGGING) {
                        }
                        return 0L;
                    }
                    if (z && repeatCount == 0) {
                        preloadRecentApps();
                    } else if (!z && !isCanceled && !this.mExt.mAppSwitchKeyConsumed) {
                        toggleRecentApps(adjustKeyEventDisplayIdForDex);
                    }
                    if (this.mExt.canDispatchingSystemKeyEvent(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED)) {
                        Log.i(StartingSurfaceController.TAG, "can dispatching recent key event, requestedSystemKey");
                        return 0L;
                    }
                }
                return -1L;
            case 219:
                Slog.wtf(StartingSurfaceController.TAG, "KEYCODE_ASSIST should be handled in interceptKeyBeforeQueueing");
                return -1L;
            case FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO /* 220 */:
            case 221:
                if (z) {
                    int i5 = keyCode == 221 ? 1 : -1;
                    if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -3) != 0) {
                        Settings.System.putIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -3);
                    }
                    if (adjustKeyEventDisplayIdForDex < 0) {
                        adjustKeyEventDisplayIdForDex = 0;
                    }
                    float brightnessConstraint = this.mPowerManager.getBrightnessConstraint(0);
                    float brightnessConstraint2 = this.mPowerManager.getBrightnessConstraint(1);
                    float max = Math.max(brightnessConstraint, Math.min(brightnessConstraint2, this.mDisplayManager.getBrightness(adjustKeyEventDisplayIdForDex) + (((brightnessConstraint2 - brightnessConstraint) / 10.0f) * i5)));
                    if (KeyCustomizationConstants.isSafeDebugInput()) {
                        Log.d(StartingSurfaceController.TAG, "setBrightness, " + max);
                    }
                    this.mDisplayManager.setBrightness(adjustKeyEventDisplayIdForDex, max);
                    if (adjustKeyEventDisplayIdForDex == 0 && this.mExt.getDexMode() == 2) {
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setLaunchDisplayId(0);
                        startActivityAsUser(new Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG"), makeBasic.toBundle(), UserHandle.CURRENT_OR_SELF);
                    } else {
                        startActivityAsUser(new Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG"), UserHandle.CURRENT_OR_SELF);
                    }
                }
                return -1L;
            case 231:
                Slog.wtf(StartingSurfaceController.TAG, "KEYCODE_VOICE_ASSIST should be handled in interceptKeyBeforeQueueing");
                return -1L;
            case 284:
                if (!z) {
                    this.mHandler.removeMessages(22);
                    Message obtainMessage = this.mHandler.obtainMessage(22);
                    obtainMessage.setAsynchronous(true);
                    obtainMessage.sendToTarget();
                }
                return -1L;
            case 289:
            case FrameworkStatsLog.f476x6292055 /* 290 */:
            case 291:
            case 292:
            case 293:
            case 294:
            case 295:
            case 296:
            case 297:
            case FrameworkStatsLog.BLOB_COMMITTED /* 298 */:
            case FrameworkStatsLog.BLOB_LEASED /* 299 */:
            case 300:
            case 301:
            case FrameworkStatsLog.f63x1c64b730 /* 302 */:
            case FrameworkStatsLog.f111x2b18bc4b /* 303 */:
            case FrameworkStatsLog.f85x3da64833 /* 304 */:
                Slog.wtf(StartingSurfaceController.TAG, "KEYCODE_APP_X should be handled in interceptKeyBeforeQueueing");
                return -1L;
            case 305:
                if (z) {
                    this.mInputManagerInternal.decrementKeyboardBacklight(keyEvent.getDeviceId());
                }
                return -1L;
            case 306:
                if (z) {
                    this.mInputManagerInternal.incrementKeyboardBacklight(keyEvent.getDeviceId());
                }
                return -1L;
            case FrameworkStatsLog.f76x6a63db72 /* 307 */:
                return -1L;
            case 308:
            case 309:
            case 310:
            case 311:
                Slog.wtf(StartingSurfaceController.TAG, "KEYCODE_STYLUS_BUTTON_* should be handled in interceptKeyBeforeQueueing");
                return -1L;
            case FrameworkStatsLog.f87x84af90cd /* 312 */:
                if (z && repeatCount == 0) {
                    showRecentApps(false);
                }
                return -1L;
            default:
                if (!isValidGlobalKey(keyCode)) {
                }
                if ((65536 & metaState) == 0) {
                }
                if (CoreRune.FW_KEY_SA_LOGGING) {
                }
                return 0L;
        }
    }

    public final int handleHomeShortcuts(int i, IBinder iBinder, KeyEvent keyEvent) {
        DisplayHomeButtonHandler displayHomeButtonHandler = (DisplayHomeButtonHandler) this.mDisplayHomeButtonHandlers.get(i);
        if (displayHomeButtonHandler == null) {
            displayHomeButtonHandler = new DisplayHomeButtonHandler(i);
            this.mDisplayHomeButtonHandlers.put(i, displayHomeButtonHandler);
        }
        return displayHomeButtonHandler.handleHomeButton(iBinder, keyEvent);
    }

    public final void toggleMicrophoneMuteFromKey() {
        if (this.mSensorPrivacyManager.supportsSensorToggle(1, 1)) {
            boolean isSensorPrivacyEnabled = this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 1);
            this.mSensorPrivacyManager.setSensorPrivacy(1, !isSensorPrivacyEnabled);
            Toast.makeText(this.mContext, UiThread.get().getLooper(), this.mContext.getString(isSensorPrivacyEnabled ? R.string.permlab_install_shortcut : R.string.permlab_imagesWrite), 0).show();
        }
    }

    public final void interceptBugreportGestureTv() {
        this.mHandler.removeMessages(18);
        Message obtain = Message.obtain(this.mHandler, 18);
        obtain.setAsynchronous(true);
        this.mHandler.sendMessageDelayed(obtain, 1000L);
    }

    public final void cancelBugreportGestureTv() {
        this.mHandler.removeMessages(18);
    }

    public final void interceptAccessibilityGestureTv() {
        this.mHandler.removeMessages(19);
        Message obtain = Message.obtain(this.mHandler, 19);
        obtain.setAsynchronous(true);
        this.mHandler.sendMessageDelayed(obtain, getAccessibilityShortcutTimeout());
    }

    public final void cancelAccessibilityGestureTv() {
        this.mHandler.removeMessages(19);
    }

    public final void requestBugreportForTv() {
        if ("1".equals(SystemProperties.get("ro.debuggable")) || Settings.Global.getInt(this.mContext.getContentResolver(), "development_settings_enabled", 0) == 1) {
            try {
                if (ActivityManager.getService().launchBugReportHandlerApp()) {
                    return;
                }
                ActivityManager.getService().requestInteractiveBugReport();
            } catch (RemoteException e) {
                Slog.e(StartingSurfaceController.TAG, "Error taking bugreport", e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0148  */
    @Override // com.android.server.policy.WindowManagerPolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KeyEvent dispatchUnhandledKey(IBinder iBinder, KeyEvent keyEvent, int i) {
        KeyEvent keyEvent2;
        KeyCharacterMap.FallbackAction fallbackAction;
        if (DEBUG_INPUT) {
            KeyInterceptionInfo keyInterceptionInfoFromToken = this.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
            Slog.d(StartingSurfaceController.TAG, "Unhandled key: inputToken=" + iBinder + ", title=" + (keyInterceptionInfoFromToken == null ? "<unknown>" : keyInterceptionInfoFromToken.windowTitle) + ", action=" + keyEvent.getAction() + ", flags=" + keyEvent.getFlags() + ", keyCode=" + keyEvent.getKeyCode() + ", scanCode=" + keyEvent.getScanCode() + ", metaState=" + keyEvent.getMetaState() + ", repeatCount=" + keyEvent.getRepeatCount() + ", policyFlags=" + i);
        }
        if (interceptUnhandledKey(keyEvent)) {
            return null;
        }
        if ((keyEvent.getFlags() & 1024) == 0) {
            KeyCharacterMap keyCharacterMap = keyEvent.getKeyCharacterMap();
            int keyCode = keyEvent.getKeyCode();
            int metaState = keyEvent.getMetaState();
            boolean z = keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0;
            if (z) {
                fallbackAction = keyCharacterMap.getFallbackAction(keyCode, metaState);
            } else {
                fallbackAction = (KeyCharacterMap.FallbackAction) this.mFallbackActions.get(keyCode);
            }
            if (fallbackAction != null) {
                if (DEBUG_INPUT) {
                    Slog.d(StartingSurfaceController.TAG, "Fallback: keyCode=" + fallbackAction.keyCode + " metaState=" + Integer.toHexString(fallbackAction.metaState));
                }
                keyEvent2 = KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), fallbackAction.keyCode, keyEvent.getRepeatCount(), fallbackAction.metaState, keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags() | 1024, keyEvent.getSource(), keyEvent.getDisplayId(), null);
                if (!interceptFallback(iBinder, keyEvent2, i)) {
                    keyEvent2.recycle();
                    keyEvent2 = null;
                }
                if (z) {
                    this.mFallbackActions.put(keyCode, fallbackAction);
                } else if (keyEvent.getAction() == 1) {
                    this.mFallbackActions.remove(keyCode);
                    fallbackAction.recycle();
                }
                if (DEBUG_INPUT) {
                    if (keyEvent2 == null) {
                        Slog.d(StartingSurfaceController.TAG, "No fallback.");
                    } else {
                        Slog.d(StartingSurfaceController.TAG, "Performing fallback: " + keyEvent2);
                    }
                }
                return keyEvent2;
            }
        }
        keyEvent2 = null;
        if (DEBUG_INPUT) {
        }
        return keyEvent2;
    }

    public final boolean interceptUnhandledKey(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean z = keyEvent.getAction() == 0;
        int modifiers = keyEvent.getModifiers();
        if (this.mExt.interceptUnhandledKey(keyEvent)) {
            Log.d(StartingSurfaceController.TAG, "Handled key. keyCode=" + keyCode);
            return true;
        }
        if (keyCode != 54) {
            if (keyCode != 62) {
                if (keyCode == 111) {
                    if (!keyEvent.hasNoModifiers()) {
                        return false;
                    }
                    if (z && repeatCount == 0) {
                        this.mContext.closeSystemDialogs();
                        if (CoreRune.FW_KEY_SA_LOGGING) {
                            CoreSaLogger.logForBasic("PKBD0001");
                        }
                    }
                    return true;
                }
                if (keyCode == 120) {
                    if (z && repeatCount == 0) {
                        keyEvent.getDisplayId();
                        int adjustKeyEventDisplayIdForDex = this.mExt.adjustKeyEventDisplayIdForDex(keyEvent);
                        this.mExt.mWindowManagerFuncs.sendTakeScreenshotRunnable(1, adjustKeyEventDisplayIdForDex != -1 ? adjustKeyEventDisplayIdForDex : 0);
                        if (CoreRune.FW_KEY_SA_LOGGING) {
                            CoreSaLogger.logForBasic("PKBD0051");
                        }
                    }
                    return true;
                }
            } else if (z && repeatCount == 0 && KeyEvent.metaStateHasModifiers(modifiers & (-194), IInstalld.FLAG_USE_QUOTA)) {
                sendSwitchKeyboardLayout(keyEvent, (modifiers & 193) == 0 ? 1 : -1);
                return true;
            }
        } else if (z && KeyEvent.metaStateHasModifiers(modifiers, 4098) && this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(isKeyguardLocked())) {
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(17));
            return true;
        }
        return false;
    }

    public final void sendSwitchKeyboardLayout(KeyEvent keyEvent, int i) {
        this.mHandler.obtainMessage(25, keyEvent.getDeviceId(), i).sendToTarget();
    }

    public final void handleSwitchKeyboardLayout(int i, int i2) {
        if (!isUserSetupComplete()) {
            Slog.i(StartingSurfaceController.TAG, "Ignoring switching keyboard layout - device not setup.");
        } else if (FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_ui")) {
            InputMethodManagerInternal.get().switchKeyboardLayout(i2);
        } else {
            this.mWindowManagerFuncs.switchKeyboardLayout(i, i2);
        }
    }

    public final boolean interceptFallback(IBinder iBinder, KeyEvent keyEvent, int i) {
        return ((interceptKeyBeforeQueueing(keyEvent, i) & 1) == 0 || interceptKeyBeforeDispatching(iBinder, keyEvent, i) != 0 || interceptUnhandledKey(keyEvent)) ? false : true;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setTopFocusedDisplay(int i) {
        this.mTopFocusedDisplayId = i;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.registerDisplayFoldListener(iDisplayFoldListener);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.unregisterDisplayFoldListener(iDisplayFoldListener);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setOverrideFoldedArea(Rect rect) {
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.setOverrideFoldedArea(rect);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public Rect getFoldedArea() {
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            return displayFoldController.getFoldedArea();
        }
        return new Rect();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void onDefaultDisplayFocusChangedLw(WindowManagerPolicy.WindowState windowState) {
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.onDefaultDisplayFocusChanged(windowState != null ? windowState.getOwningPackage() : null);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void registerShortcutKey(long j, IShortcutService iShortcutService) {
        synchronized (this.mLock) {
            this.mModifierShortcutManager.registerShortcutKey(j, iShortcutService);
        }
    }

    public void onKeyguardOccludedChangedLw(boolean z) {
        if (this.mKeyguardDelegate != null) {
            this.mPendingKeyguardOccluded = z;
            this.mKeyguardOccludedChanged = true;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int applyKeyguardOcclusionChange() {
        if (DEBUG_KEYGUARD) {
            Slog.d(StartingSurfaceController.TAG, "transition/occluded commit occluded=" + this.mPendingKeyguardOccluded + " changed=" + this.mKeyguardOccludedChanged);
        }
        return setKeyguardOccludedLw(this.mPendingKeyguardOccluded) ? 5 : 0;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedBootAnimation() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.finishedBootAnim();
        }
    }

    public int handleTransitionForKeyguardLw(boolean z, boolean z2) {
        int applyKeyguardOcclusionChange;
        if (z2 && (applyKeyguardOcclusionChange = applyKeyguardOcclusionChange()) != 0) {
            return applyKeyguardOcclusionChange;
        }
        if (!z) {
            return 0;
        }
        if (DEBUG_KEYGUARD) {
            Slog.d(StartingSurfaceController.TAG, "Starting keyguard exit animation");
        }
        startKeyguardExitAnimation(SystemClock.uptimeMillis());
        return 0;
    }

    public void launchAssistAction(String str, int i, long j, int i2) {
        sendCloseSystemWindows("assist");
        if (isUserSetupComplete()) {
            Bundle bundle = new Bundle();
            if (i > Integer.MIN_VALUE) {
                bundle.putInt("android.intent.extra.ASSIST_INPUT_DEVICE_ID", i);
            }
            if (str != null) {
                bundle.putBoolean(str, true);
            }
            bundle.putLong("android.intent.extra.TIME", j);
            bundle.putInt("invocation_type", i2);
            ((SearchManager) this.mContext.createContextAsUser(UserHandle.of(this.mCurrentUserId), 0).getSystemService("search")).launchAssist(bundle);
        }
    }

    public final void launchVoiceAssist(boolean z) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (!(keyguardServiceDelegate != null && keyguardServiceDelegate.isShowing())) {
            if (this.mHasFeatureWatch && isInRetailMode()) {
                launchRetailVoiceAssist(z);
                return;
            } else {
                startVoiceAssistIntent(z);
                return;
            }
        }
        this.mKeyguardDelegate.dismissKeyguardToLaunch(new Intent("android.intent.action.VOICE_ASSIST"));
    }

    public final void launchRetailVoiceAssist(boolean z) {
        Intent intent = new Intent("android.intent.action.VOICE_ASSIST_RETAIL");
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity != null) {
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
            startActivityAsUser(intent, null, UserHandle.CURRENT_OR_SELF, z);
        } else {
            Slog.w(StartingSurfaceController.TAG, "Couldn't find an app to process android.intent.action.VOICE_ASSIST_RETAIL. Fall back to start android.intent.action.VOICE_ASSIST");
            startVoiceAssistIntent(z);
        }
    }

    public final void startVoiceAssistIntent(boolean z) {
        startActivityAsUser(new Intent("android.intent.action.VOICE_ASSIST"), null, UserHandle.CURRENT_OR_SELF, z);
    }

    public final boolean isInRetailMode() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_demo_mode", 0) == 1;
    }

    public final void startActivityAsUser(Intent intent, UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        startActivityAsUser(intent, bundle, userHandle, false);
    }

    public final void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle, boolean z) {
        if (z || isUserSetupComplete()) {
            this.mContext.startActivityAsUser(intent, bundle, userHandle);
            return;
        }
        Slog.i(StartingSurfaceController.TAG, "Not starting activity because user setup is in progress: " + intent);
    }

    public final void preloadRecentApps() {
        this.mPreloadedRecentApps = true;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.preloadRecentApps();
        }
    }

    public final void cancelPreloadRecentApps() {
        if (this.mPreloadedRecentApps) {
            this.mPreloadedRecentApps = false;
            StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.cancelPreloadRecentApps();
            }
        }
    }

    public final void toggleTaskbar() {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleTaskbar();
        }
    }

    public void toggleRecentApps(int i) {
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            Slog.d(StartingSurfaceController.TAG, "toggleRecentApps displayId=" + i);
            statusBarManagerInternal.toggleRecentAppsToType(StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
        }
    }

    public final void showRecentApps(boolean z) {
        showRecentApps(z, 0);
    }

    public final void showRecentApps(boolean z, int i) {
        Log.d(StartingSurfaceController.TAG, "showRecentApps, altTab=" + z + " displayId=" + i);
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.showRecentAppsToType(z, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
        }
    }

    public final void toggleKeyboardShortcutsMenu(int i, int i2) {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleKeyboardShortcutsMenuToType(i, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i2));
        }
    }

    public void dismissKeyboardShortcutsMenu() {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.dismissKeyboardShortcutsMenu();
        }
    }

    public final void hideRecentApps(boolean z, boolean z2, int i) {
        this.mPreloadedRecentApps = false;
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.hideRecentAppsFromType(z, z2, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
        }
    }

    public final void enterStageSplitFromRunningApp(boolean z) {
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.enterStageSplitFromRunningApp(z);
        }
    }

    public void launchHomeFromHotKey(int i) {
        launchHomeFromHotKey(i, true, true);
    }

    public void launchHomeFromHotKey(final int i, final boolean z, boolean z2) {
        if (this.mExt.isInteractionControlEnabled(3)) {
            Log.d(StartingSurfaceController.TAG, "launchHomeFromHotKey : Key was blocked by interaction control");
            return;
        }
        if (z2) {
            if (this.mExt.isKeyguardShowingAndNotOccluded(i)) {
                return;
            }
            if (!this.mExt.checkKeyguardOccluded(i, true) && this.mKeyguardDelegate.isInputRestricted()) {
                this.mKeyguardDelegate.verifyUnlock(new WindowManagerPolicy.OnKeyguardExitResult() { // from class: com.android.server.policy.PhoneWindowManager.13
                    @Override // com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult
                    public void onKeyguardExitResult(boolean z3) {
                        if (z3) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                PhoneWindowManager.this.startDockOrHome(i, true, z);
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                });
                return;
            }
        }
        if (this.mRecentsVisible) {
            try {
                ActivityManager.getService().stopAppSwitches();
            } catch (RemoteException unused) {
            }
            if (z) {
                awakenDreams();
            }
            hideRecentApps(false, true, i);
            return;
        }
        startDockOrHome(i, true, z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setRecentsVisibilityLw(boolean z) {
        this.mRecentsVisible = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setPipVisibilityLw(boolean z) {
        this.mPictureInPictureVisible = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setNavBarVirtualKeyHapticFeedbackEnabledLw(boolean z) {
        this.mNavBarVirtualKeyHapticFeedbackEnabled = z;
    }

    public final boolean setKeyguardOccludedLw(boolean z) {
        if (DEBUG_KEYGUARD) {
            Slog.d(StartingSurfaceController.TAG, "setKeyguardOccluded occluded=" + z);
        }
        this.mKeyguardOccludedChanged = false;
        this.mPendingKeyguardOccluded = z;
        this.mKeyguardDelegate.setOccluded(z, true);
        return this.mKeyguardDelegate.isShowing();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void notifyLidSwitchChanged(long j, boolean z) {
        if (z == this.mDefaultDisplayPolicy.getLidState()) {
            return;
        }
        this.mDefaultDisplayPolicy.setLidState(z ? 1 : 0);
        applyLidSwitchState();
        updateRotation(true);
        if (z) {
            wakeUp(SystemClock.uptimeMillis(), this.mAllowTheaterModeWakeFromLidSwitch, 9, "android.policy:LID");
        } else if (getLidBehavior() != 1) {
            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), false);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        Intent intent;
        if (this.mCameraLensCoverState != z && this.mContext.getResources().getBoolean(R.bool.config_handleVolumeAliasesUsingVolumeGroups)) {
            if (this.mCameraLensCoverState == 1 && !z) {
                KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                if (keyguardServiceDelegate == null ? false : keyguardServiceDelegate.isShowing()) {
                    intent = new Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE");
                } else {
                    intent = new Intent("android.media.action.STILL_IMAGE_CAMERA");
                }
                wakeUp(j / 1000000, this.mAllowTheaterModeWakeFromCameraLens, 5, "android.policy:CAMERA_COVER");
                startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
            }
            this.mCameraLensCoverState = z ? 1 : 0;
        }
    }

    public void initializeHdmiState() {
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            initializeHdmiStateInternal();
        } finally {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
    
        if (r5 == null) goto L36;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.io.FileReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initializeHdmiStateInternal() {
        FileReader fileReader;
        NumberFormatException e;
        IOException e2;
        boolean z = false;
        if (new File("/sys/devices/virtual/switch/hdmi/state").exists()) {
            ?? r5 = "DEVPATH=/devices/virtual/switch/hdmi";
            this.mHDMIObserver.startObserving("DEVPATH=/devices/virtual/switch/hdmi");
            FileReader fileReader2 = null;
            try {
                try {
                    fileReader = new FileReader("/sys/class/switch/hdmi/state");
                    try {
                        char[] cArr = new char[15];
                        int read = fileReader.read(cArr);
                        r5 = fileReader;
                        if (read > 1) {
                            r5 = fileReader;
                            if (Integer.parseInt(new String(cArr, 0, read - 1)) != 0) {
                                z = true;
                                r5 = fileReader;
                            }
                        }
                    } catch (IOException e3) {
                        e2 = e3;
                        Slog.w(StartingSurfaceController.TAG, "Couldn't read hdmi state from /sys/class/switch/hdmi/state: " + e2);
                        r5 = fileReader;
                    } catch (NumberFormatException e4) {
                        e = e4;
                        Slog.w(StartingSurfaceController.TAG, "Couldn't read hdmi state from /sys/class/switch/hdmi/state: " + e);
                        if (fileReader != null) {
                            r5 = fileReader;
                            r5.close();
                        }
                        this.mDefaultDisplayPolicy.setHdmiPlugged(z, true);
                    }
                } catch (Throwable th) {
                    th = th;
                    fileReader2 = r5;
                    if (fileReader2 != null) {
                        try {
                            fileReader2.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                fileReader = null;
                e2 = e5;
            } catch (NumberFormatException e6) {
                fileReader = null;
                e = e6;
            } catch (Throwable th2) {
                th = th2;
                if (fileReader2 != null) {
                }
                throw th;
            }
            try {
                r5.close();
            } catch (IOException unused2) {
            }
        } else {
            this.mExt.mDrmEventObserver.startObserving("mdss_mdp/drm/card");
            this.mExt.mExtEventObserver.startObserving("displayport/extcon/extcon");
        }
        this.mDefaultDisplayPolicy.setHdmiPlugged(z, true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x04fe, code lost:
    
        if (r31.mBackKeyHandled != false) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:358:0x0099, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0507  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x051c  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0143  */
    @Override // com.android.server.policy.WindowManagerPolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
        int i2;
        int isWakeKey;
        TelecomManager telecommService;
        boolean z;
        int i3;
        TelecomManager telecommService2;
        int i4;
        boolean z2;
        HdmiControl hdmiControl;
        int keyCode = keyEvent.getKeyCode();
        boolean z3 = keyEvent.getAction() == 0;
        boolean z4 = (i & 1) != 0 || keyEvent.isWakeKey();
        if (!this.mSystemBooted) {
            if (z3 && (keyCode == 26 || keyCode == 177)) {
                wakeUpFromPowerKey(keyEvent.getDownTime());
            } else {
                if (!z3 || ((!z4 && keyCode != 224) || !isWakeKeyWhenScreenOff(keyCode))) {
                    z2 = false;
                    if (z2 && (hdmiControl = getHdmiControl()) != null) {
                        hdmiControl.turnOnTv();
                    }
                    return 0;
                }
                wakeUpFromWakeKey(keyEvent);
            }
            z2 = true;
            if (z2) {
                hdmiControl.turnOnTv();
            }
            return 0;
        }
        boolean z5 = (536870912 & i) != 0;
        boolean isCanceled = keyEvent.isCanceled();
        int displayId = keyEvent.getDisplayId();
        boolean z6 = (16777216 & i) != 0;
        if (DEBUG_INPUT || CoreRune.SAFE_DEBUG || PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST.contains(Integer.valueOf(keyCode))) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                if (z5) {
                }
                Log.d(StartingSurfaceController.TAG, "interceptKeyTq keycode=" + keyCode + " interactive=" + z5 + " keyguardActive=" + r4 + " policyFlags=" + Integer.toHexString(i));
            }
            boolean z7 = false;
            Log.d(StartingSurfaceController.TAG, "interceptKeyTq keycode=" + keyCode + " interactive=" + z5 + " keyguardActive=" + z7 + " policyFlags=" + Integer.toHexString(i));
        }
        if (z5 || (z6 && !z4)) {
            if (z5) {
                int i5 = (keyCode != this.mPendingWakeKey || z3) ? 1 : 0;
                this.mPendingWakeKey = -1;
                i2 = i5;
                z4 = false;
                isWakeKey = this.mExt.isWakeKey(keyEvent, z5);
                boolean z8 = (isWakeKey & 32) == 0 ? false : (isWakeKey & 16) != 0 ? true : z4;
                if ((isWakeKey & 2) == 0) {
                    if (z8) {
                        wakeUp(keyEvent.getEventTime(), this.mAllowTheaterModeWakeFromKey, 6, "android.policy:KEY(" + keyCode + ")");
                    }
                    Log.d(StartingSurfaceController.TAG, "interceptKeyTq : return condition of policyExt wakeKey");
                    return 0;
                }
                if (isValidGlobalKey(keyCode) && this.mGlobalKeyManager.shouldHandleGlobalKey(keyCode)) {
                    if (!z5 && z8 && z3 && this.mGlobalKeyManager.shouldDispatchFromNonInteractive(keyCode)) {
                        this.mGlobalKeyManager.setBeganFromNonInteractive();
                        this.mPendingWakeKey = -1;
                        i2 = 1;
                    }
                    if (z8) {
                        wakeUpFromWakeKey(keyEvent);
                    }
                    return i2;
                }
                HdmiControlManager hdmiControlManager = getHdmiControlManager();
                if (keyCode == 177 && this.mHasFeatureLeanback && (hdmiControlManager == null || !hdmiControlManager.shouldHandleTvPowerKey())) {
                    return interceptKeyBeforeQueueing(KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), 26, keyEvent.getRepeatCount(), keyEvent.getMetaState(), keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags(), keyEvent.getSource(), keyEvent.getDisplayId(), null), i);
                }
                boolean z9 = z5 && this.mDefaultDisplayPolicy.isAwake();
                if ((keyCode == 24 || keyCode == 25) && (telecommService = getTelecommService()) != null && telecommService.isInCall() && this.mPowerManagerInternal.isProximityPositive()) {
                    Log.d(StartingSurfaceController.TAG, "interceptKeyTq, interactive value has updated by proximity sensor during inCall");
                    z9 = false;
                }
                if ((keyEvent.getFlags() & 1024) == 0 && (handleKeyGesture(keyEvent, z9, i) & 2) != 0) {
                    Log.d(StartingSurfaceController.TAG, "interceptKeyTq : return condition of policyExt handleKeyGesture");
                    return 0;
                }
                int interceptKeyBeforeQueueing = this.mExt.interceptKeyBeforeQueueing(keyEvent, i);
                StringBuilder sb = new StringBuilder();
                boolean z10 = z8;
                sb.append("interceptKeyTq interceptKeyResult=");
                sb.append(Integer.toHexString(interceptKeyBeforeQueueing));
                Log.d(StartingSurfaceController.TAG, sb.toString());
                if ((interceptKeyBeforeQueueing & 2) != 0) {
                    Log.d(StartingSurfaceController.TAG, "interceptKeyTq : return condition of policyExt");
                    return 0;
                }
                if ((interceptKeyBeforeQueueing & 4) != 0) {
                    i2 |= 1;
                } else if ((interceptKeyBeforeQueueing & 8) != 0) {
                    i2 &= -2;
                }
                boolean z11 = z3 && (hasNavigationBar() ? (i & 2) != 0 : z5) && (!((keyEvent.getFlags() & 64) != 0) || this.mNavBarVirtualKeyHapticFeedbackEnabled) && keyEvent.getRepeatCount() == 0;
                if (keyCode == 4) {
                    if (z3) {
                        this.mBackKeyHandled = false;
                    } else if (!hasLongPressOnBackBehavior()) {
                        this.mBackKeyHandled |= backKeyPress();
                    }
                    i3 = i2;
                    z = z10;
                    if (z11) {
                    }
                    if (z) {
                    }
                    if ((i3 & 1) != 0) {
                    }
                    return i3;
                }
                if (keyCode == 5) {
                    if (z3 && (telecommService2 = getTelecommService()) != null && telecommService2.isRinging()) {
                        Log.i(StartingSurfaceController.TAG, "interceptKeyBeforeQueueing: CALL key-down while ringing: Answer the call!");
                        telecommService2.acceptRingingCall();
                        i2 &= -2;
                    }
                    i3 = i2;
                    z = z10;
                    if (z11) {
                    }
                    if (z) {
                    }
                    if ((i3 & 1) != 0) {
                        Log.i(StartingSurfaceController.TAG, "Attempting to move non-focused display " + displayId + " to top because a key is targeting it");
                        this.mWindowManagerFuncs.moveDisplayToTopIfAllowed(displayId);
                    }
                    return i3;
                }
                if (keyCode == 6) {
                    if (!this.mExt.hasSystemKeyInfo(keyCode, 3)) {
                        i2 &= -2;
                        if (z3) {
                            TelecomManager telecommService3 = getTelecommService();
                            boolean endCall = telecommService3 != null ? telecommService3.endCall() : false;
                            if (z5 && !endCall) {
                                this.mEndCallKeyHandled = false;
                                this.mHandler.postDelayed(this.mEndCallLongPress, ViewConfiguration.get(this.mContext).getDeviceGlobalActionKeyTimeout());
                            } else {
                                this.mEndCallKeyHandled = true;
                            }
                        } else if (!this.mEndCallKeyHandled) {
                            this.mHandler.removeCallbacks(this.mEndCallLongPress);
                            if (!isCanceled && (((this.mEndcallBehavior & 1) == 0 || !goHome()) && (this.mEndcallBehavior & 2) != 0)) {
                                sleepDefaultDisplay(keyEvent.getEventTime(), 4, 0);
                                i3 = i2;
                                z = false;
                            }
                        }
                    }
                    i3 = i2;
                    z = z10;
                } else {
                    if (keyCode != 79 && keyCode != 130) {
                        if (keyCode != 164) {
                            if (keyCode != 171) {
                                if (keyCode != 177) {
                                    if (keyCode == 219) {
                                        boolean z12 = keyEvent.getRepeatCount() > 0;
                                        if (z3 && !z12) {
                                            Message obtainMessage = this.mHandler.obtainMessage(23, keyEvent.getDeviceId(), 0, Long.valueOf(keyEvent.getEventTime()));
                                            obtainMessage.setAsynchronous(true);
                                            obtainMessage.sendToTarget();
                                        }
                                    } else if (keyCode != 231) {
                                        if (keyCode == 276) {
                                            i2 &= -2;
                                            if (!z3) {
                                                this.mPowerManagerInternal.setUserInactiveOverrideFromWindowManager();
                                            }
                                        } else if (keyCode != 126 && keyCode != 127) {
                                            switch (keyCode) {
                                                case 24:
                                                case 25:
                                                    break;
                                                case 26:
                                                    EventLogTags.writeInterceptPower(KeyEvent.actionToString(keyEvent.getAction()), this.mPowerKeyHandled ? 1 : 0, this.mSingleKeyGestureDetector.getKeyPressCounter(26));
                                                    if (z3) {
                                                        interceptPowerKeyDown(keyEvent, z9);
                                                    } else {
                                                        interceptPowerKeyUp(keyEvent, isCanceled);
                                                    }
                                                    if (CoreRune.FW_FOLD_SA_LOGGING) {
                                                        this.mExt.sendFoldSaLoggingCanceledIfNeeded("PowerKey Click", false);
                                                        break;
                                                    }
                                                    break;
                                                default:
                                                    switch (keyCode) {
                                                        case 85:
                                                        case 86:
                                                        case 87:
                                                        case 88:
                                                        case 89:
                                                        case 90:
                                                            break;
                                                        case 91:
                                                            i2 &= -2;
                                                            if (z3 && keyEvent.getRepeatCount() == 0) {
                                                                toggleMicrophoneMuteFromKey();
                                                            }
                                                            i3 = i2;
                                                            z = z10;
                                                            break;
                                                        default:
                                                            switch (keyCode) {
                                                                case 222:
                                                                    break;
                                                                case FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED /* 223 */:
                                                                    i2 &= -2;
                                                                    if (!this.mPowerManager.isInteractive()) {
                                                                        z11 = false;
                                                                    }
                                                                    if (z3) {
                                                                        sleepPress();
                                                                        break;
                                                                    } else {
                                                                        sleepRelease(keyEvent.getEventTime());
                                                                        break;
                                                                    }
                                                                case 224:
                                                                    if (!this.mExt.canDispatchingSystemKeyEvent(keyCode)) {
                                                                        i3 = i2 & (-2);
                                                                        z = true;
                                                                        break;
                                                                    } else {
                                                                        i2 |= 1;
                                                                        Slog.d(StartingSurfaceController.TAG, "skip wakeUp by KEYCODE_WAKEUP, requestedSystemKey");
                                                                        break;
                                                                    }
                                                                default:
                                                                    switch (keyCode) {
                                                                        case 280:
                                                                        case FrameworkStatsLog.ASSISTANT_INVOCATION_REPORTED /* 281 */:
                                                                        case FrameworkStatsLog.DISPLAY_WAKE_REPORTED /* 282 */:
                                                                        case 283:
                                                                            i2 &= -2;
                                                                            interceptSystemNavigationKey(keyEvent);
                                                                            break;
                                                                        default:
                                                                            switch (keyCode) {
                                                                                default:
                                                                                    switch (keyCode) {
                                                                                        case 308:
                                                                                        case 309:
                                                                                        case 310:
                                                                                        case 311:
                                                                                            if (z3 && this.mStylusButtonsEnabled) {
                                                                                                sendSystemKeyToStatusBarAsync(keyEvent);
                                                                                            }
                                                                                            break;
                                                                                        default:
                                                                                            switch (keyCode) {
                                                                                            }
                                                                                    }
                                                                                case 289:
                                                                                case FrameworkStatsLog.f476x6292055 /* 290 */:
                                                                                case 291:
                                                                                case 292:
                                                                                case 293:
                                                                                case 294:
                                                                                case 295:
                                                                                case 296:
                                                                                case 297:
                                                                                case FrameworkStatsLog.BLOB_COMMITTED /* 298 */:
                                                                                case FrameworkStatsLog.BLOB_LEASED /* 299 */:
                                                                                case 300:
                                                                                case 301:
                                                                                case FrameworkStatsLog.f63x1c64b730 /* 302 */:
                                                                                case FrameworkStatsLog.f111x2b18bc4b /* 303 */:
                                                                                case FrameworkStatsLog.f85x3da64833 /* 304 */:
                                                                                    i2 &= -2;
                                                                                    break;
                                                                            }
                                                                    }
                                                                    i3 = i2;
                                                                    z = z10;
                                                                    break;
                                                            }
                                                    }
                                            }
                                        }
                                    } else if (!z3) {
                                        this.mBroadcastWakeLock.acquire();
                                        Message obtainMessage2 = this.mHandler.obtainMessage(12);
                                        obtainMessage2.setAsynchronous(true);
                                        obtainMessage2.sendToTarget();
                                    }
                                    i2 &= -2;
                                    i3 = i2;
                                    z = z10;
                                } else {
                                    i2 &= -2;
                                    if (z3 && hdmiControlManager != null) {
                                        hdmiControlManager.toggleAndFollowTvPower();
                                    }
                                }
                                i3 = i2;
                                z = false;
                            } else {
                                if (this.mShortPressOnWindowBehavior == 1 && this.mPictureInPictureVisible) {
                                    if (!z3) {
                                        showPictureInPictureMenu(keyEvent);
                                    }
                                    i2 &= -2;
                                }
                                i3 = i2;
                                z = z10;
                            }
                        }
                        if (z3) {
                            sendSystemKeyToStatusBarAsync(keyEvent);
                            NotificationManager notificationService = getNotificationService();
                            if (notificationService != null && !this.mHandleVolumeKeysInWM) {
                                notificationService.silenceNotificationSound();
                            }
                            TelecomManager telecommService4 = getTelecommService();
                            if (telecommService4 != null && !this.mHandleVolumeKeysInWM && telecommService4.isRinging()) {
                                if (this.mExt.isVolumeKeyAnswerCallMode(keyCode)) {
                                    Log.i(StartingSurfaceController.TAG, "ringing: Accept the call (volume up)");
                                    if (CoreRune.FW_KEY_SA_LOGGING) {
                                        this.mExt.acceptRingingCall(keyCode);
                                    } else {
                                        telecommService4.acceptRingingCall();
                                    }
                                } else {
                                    Log.i(StartingSurfaceController.TAG, "interceptKeyBeforeQueueing: VOLUME key-down while ringing: Silence ringer!");
                                    if (CoreRune.FW_KEY_SA_LOGGING) {
                                        this.mExt.silenceRinger(keyCode);
                                    } else {
                                        telecommService4.silenceRinger();
                                    }
                                }
                                i2 &= -2;
                                i3 = i2;
                                z = z10;
                            } else {
                                try {
                                    i4 = getAudioService().getMode();
                                } catch (Exception e) {
                                    Log.e(StartingSurfaceController.TAG, "Error getting AudioService in interceptKeyBeforeQueueing.", e);
                                    i4 = 0;
                                }
                                if (((telecommService4 != null && telecommService4.isInCall()) || i4 == 3) && (i2 & 1) == 0) {
                                    MediaSessionLegacyHelper.getHelper(this.mContext).sendVolumeKeyEvent(keyEvent, Integer.MIN_VALUE, false);
                                    i3 = i2;
                                    z = z10;
                                }
                            }
                        }
                        if (!this.mUseTvRouting && !this.mHandleVolumeKeysInWM) {
                            if ((i2 & 1) == 0) {
                                if (this.mExt.mKeyCustomizationPolicy.getLastAction(3, keyCode) != 2) {
                                    MediaSessionLegacyHelper.getHelper(this.mContext).sendVolumeKeyEvent(keyEvent, Integer.MIN_VALUE, true);
                                }
                            }
                            i3 = i2;
                            z = z10;
                        }
                        i2 |= 1;
                        i3 = i2;
                        z = z10;
                    }
                    if (MediaSessionLegacyHelper.getHelper(this.mContext).isGlobalPriorityActive()) {
                        i2 &= -2;
                    }
                    if ((i2 & 1) == 0) {
                        this.mBroadcastWakeLock.acquire();
                        Message obtainMessage3 = this.mHandler.obtainMessage(3, new KeyEvent(keyEvent));
                        obtainMessage3.setAsynchronous(true);
                        obtainMessage3.sendToTarget();
                    }
                    i3 = i2;
                    z = z10;
                }
                if (z11) {
                    if (!hasNavigationBar()) {
                        this.mExt.performSystemKeyFeedback(keyEvent);
                    } else {
                        performHapticFeedback(1, false, "Virtual Key - Press");
                    }
                }
                if (z) {
                    if (!this.mExt.isDoubleTapToWakeUp(keyCode)) {
                        wakeUpFromWakeKey(keyEvent);
                    } else if (z3) {
                        int i6 = (i & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 ? 15 : 6;
                        InputDevice device = keyEvent.getDevice();
                        wakeUp(keyEvent.getEventTime(), this.mAllowTheaterModeWakeFromKey, i6, "android.policy:KEY(" + keyCode + "):" + (device != null ? device.getName() : ""));
                    }
                }
                if ((i3 & 1) != 0 && displayId != -1 && displayId != this.mTopFocusedDisplayId) {
                    Log.i(StartingSurfaceController.TAG, "Attempting to move non-focused display " + displayId + " to top because a key is targeting it");
                    this.mWindowManagerFuncs.moveDisplayToTopIfAllowed(displayId);
                }
                return i3;
            }
            z4 = false;
            i2 = 1;
            isWakeKey = this.mExt.isWakeKey(keyEvent, z5);
            if ((isWakeKey & 32) == 0) {
            }
            if ((isWakeKey & 2) == 0) {
            }
        } else if (shouldDispatchInputWhenNonInteractive(displayId, keyCode)) {
            this.mPendingWakeKey = -1;
            i2 = 1;
            isWakeKey = this.mExt.isWakeKey(keyEvent, z5);
            if ((isWakeKey & 32) == 0) {
            }
            if ((isWakeKey & 2) == 0) {
            }
        } else {
            if (z4 && (!z3 || !isWakeKeyWhenScreenOff(keyCode))) {
                z4 = false;
            }
            if (z4 && z3) {
                this.mPendingWakeKey = keyCode;
            }
            i2 = 0;
            isWakeKey = this.mExt.isWakeKey(keyEvent, z5);
            if ((isWakeKey & 32) == 0) {
            }
            if ((isWakeKey & 2) == 0) {
            }
        }
    }

    public final int handleKeyGesture(KeyEvent keyEvent, boolean z, int i) {
        int i2;
        if (CoreRune.IFW_AI_HOT_KEY && (i2 = SystemProperties.getInt("ro.product.first_api_level", 0)) < 34 && keyEvent.getKeyCode() == 1104) {
            if (keyEvent.getAction() == 0) {
                this.mExt.showToast(this.mContext, String.format(this.mContext.getString(R.string.profile_label_private), new Object[0]));
            }
            Log.d(StartingSurfaceController.TAG, "This Version doesn't Support AI key: " + i2);
            return 2;
        }
        boolean z2 = true;
        int hasRequestedActionBlockKeyEvent = this.mExt.hasRequestedActionBlockKeyEvent(keyEvent.getKeyCode(), keyEvent.getAction() == 0, keyEvent.getRepeatCount());
        if (hasRequestedActionBlockKeyEvent != -1) {
            Log.d(StartingSurfaceController.TAG, "handleKeyGesture, blockKeyPolicy");
            return hasRequestedActionBlockKeyEvent;
        }
        if (PhoneWindowManagerExt.KEYCODE_KEY_COMBINATION_ALLOWLIST.contains(Integer.valueOf(keyEvent.getKeyCode())) && this.mKeyCombinationManager.interceptKey(keyEvent, z)) {
            this.mSingleKeyGestureDetector.reset();
            Log.d(StartingSurfaceController.TAG, "handleKeyGesture, interceptKey");
        } else {
            z2 = false;
        }
        int checkPolicyBeforeInterceptKey = this.mExt.checkPolicyBeforeInterceptKey(keyEvent, z, z2);
        if (checkPolicyBeforeInterceptKey != -1) {
            Log.d(StartingSurfaceController.TAG, "handleKeyGesture, beforeInterceptKey policy=" + checkPolicyBeforeInterceptKey);
            return checkPolicyBeforeInterceptKey;
        }
        this.mSingleKeyGestureDetector.interceptKey(keyEvent, z, i);
        return 0;
    }

    public final void interceptSystemNavigationKey(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            if (!(this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.sendFingerprintGesture(keyEvent.getKeyCode())) && this.mSystemNavigationKeysEnabled) {
                sendSystemKeyToStatusBarAsync(keyEvent);
            }
        }
    }

    public final void sendSystemKeyToStatusBar(KeyEvent keyEvent) {
        IStatusBarService statusBarService = getStatusBarService();
        if (statusBarService != null) {
            try {
                statusBarService.handleSystemKey(keyEvent);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void sendSystemKeyToStatusBarAsync(KeyEvent keyEvent) {
        Message obtainMessage = this.mHandler.obtainMessage(21, keyEvent);
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    public final boolean isWakeKeyWhenScreenOff(int i) {
        if (i == 4) {
            return this.mWakeOnBackKeyPress;
        }
        if (i != 219) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    return this.mWakeOnDpadKeyPress;
                default:
                    switch (i) {
                        case 308:
                        case 309:
                        case 310:
                        case 311:
                            return this.mStylusButtonsEnabled;
                        default:
                            return true;
                    }
            }
        }
        return this.mWakeOnAssistKeyPress;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int interceptMotionBeforeQueueingNonInteractive(int i, long j, int i2) {
        int i3 = i2 & 1;
        if (i3 != 0 && wakeUp(j / 1000000, this.mAllowTheaterModeWakeFromMotion, 7, "android.policy:MOTION")) {
            return 0;
        }
        if (shouldDispatchInputWhenNonInteractive(i, 0)) {
            return 1;
        }
        if (isTheaterModeEnabled() && i3 != 0) {
            wakeUp(j / 1000000, this.mAllowTheaterModeWakeFromMotionWhenNotDreaming, 7, "android.policy:MOTION");
        }
        return 0;
    }

    public final boolean shouldDispatchInputWhenNonInteractive(int i, int i2) {
        Display display;
        IDreamManager dreamManager;
        boolean z = i == 0 || i == -1;
        if (z) {
            display = this.mDefaultDisplay;
        } else {
            display = this.mDisplayManager.getDisplay(i);
        }
        boolean z2 = display == null || display.getState() == 1;
        if (z2 && !this.mHasFeatureWatch) {
            return false;
        }
        if (isKeyguardShowingAndNotOccluded() && !z2) {
            return true;
        }
        if ((!this.mHasFeatureWatch || (i2 != 4 && i2 != 264 && i2 != 265 && i2 != 266 && i2 != 267)) && z && (dreamManager = getDreamManager()) != null) {
            try {
                if (dreamManager.isDreaming()) {
                    return true;
                }
            } catch (RemoteException e) {
                Slog.e(StartingSurfaceController.TAG, "RemoteException when checking if dreaming", e);
            }
        }
        return false;
    }

    public final void dispatchDirectAudioEvent(KeyEvent keyEvent) {
        HdmiAudioSystemClient audioSystemClient;
        HdmiControlManager hdmiControlManager = getHdmiControlManager();
        if (hdmiControlManager != null && !hdmiControlManager.getSystemAudioMode() && shouldCecAudioDeviceForwardVolumeKeysSystemAudioModeOff() && (audioSystemClient = hdmiControlManager.getAudioSystemClient()) != null) {
            audioSystemClient.sendKeyEvent(keyEvent.getKeyCode(), keyEvent.getAction() == 0);
            return;
        }
        try {
            getAudioService().handleVolumeKey(keyEvent, this.mUseTvRouting, this.mContext.getOpPackageName(), StartingSurfaceController.TAG);
        } catch (Exception e) {
            Log.e(StartingSurfaceController.TAG, "Error dispatching volume key in handleVolumeKey for event:" + keyEvent, e);
        }
    }

    public final HdmiControlManager getHdmiControlManager() {
        if (this.mHasFeatureHdmiCec) {
            return (HdmiControlManager) this.mContext.getSystemService(HdmiControlManager.class);
        }
        return null;
    }

    public final boolean shouldCecAudioDeviceForwardVolumeKeysSystemAudioModeOff() {
        return RoSystemProperties.CEC_AUDIO_DEVICE_FORWARD_VOLUME_KEYS_SYSTEM_AUDIO_MODE_OFF;
    }

    public void dispatchMediaKeyWithWakeLock(KeyEvent keyEvent) {
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "dispatchMediaKeyWithWakeLock: " + keyEvent);
        }
        if (this.mHavePendingMediaKeyRepeatWithWakeLock) {
            if (DEBUG_INPUT) {
                Slog.d(StartingSurfaceController.TAG, "dispatchMediaKeyWithWakeLock: canceled repeat");
            }
            this.mHandler.removeMessages(4);
            this.mHavePendingMediaKeyRepeatWithWakeLock = false;
            this.mBroadcastWakeLock.release();
        }
        dispatchMediaKeyWithWakeLockToAudioService(keyEvent);
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            this.mHavePendingMediaKeyRepeatWithWakeLock = true;
            Message obtainMessage = this.mHandler.obtainMessage(4, keyEvent);
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessageDelayed(obtainMessage, ViewConfiguration.getKeyRepeatTimeout());
            return;
        }
        this.mBroadcastWakeLock.release();
    }

    public void dispatchMediaKeyRepeatWithWakeLock(KeyEvent keyEvent) {
        this.mHavePendingMediaKeyRepeatWithWakeLock = false;
        KeyEvent changeTimeRepeat = KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 1, keyEvent.getFlags() | 128);
        if (DEBUG_INPUT) {
            Slog.d(StartingSurfaceController.TAG, "dispatchMediaKeyRepeatWithWakeLock: " + changeTimeRepeat);
        }
        dispatchMediaKeyWithWakeLockToAudioService(changeTimeRepeat);
        this.mBroadcastWakeLock.release();
    }

    public void dispatchMediaKeyWithWakeLockToAudioService(KeyEvent keyEvent) {
        if (this.mActivityManagerInternal.isSystemReady()) {
            MediaSessionLegacyHelper.getHelper(this.mContext).sendMediaButtonEvent(keyEvent, true);
        }
    }

    public void launchVoiceAssistWithWakeLock() {
        Intent intent;
        sendCloseSystemWindows("assist");
        if (!keyguardOn()) {
            intent = new Intent("android.speech.action.WEB_SEARCH");
        } else {
            DeviceIdleManager deviceIdleManager = (DeviceIdleManager) this.mContext.getSystemService(DeviceIdleManager.class);
            if (deviceIdleManager != null) {
                deviceIdleManager.endIdle("voice-search");
            }
            intent = new Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
            intent.putExtra("android.speech.extras.EXTRA_SECURE", true);
        }
        startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        this.mBroadcastWakeLock.release();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedWakingUpGlobal(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        Objects.requireNonNull(phoneWindowManagerExt);
        if (!phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(1) || (keyguardServiceDelegate = this.mKeyguardDelegate) == null) {
            return;
        }
        keyguardServiceDelegate.onStartedWakingUp(i, false);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedWakingUpGlobal(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        Objects.requireNonNull(phoneWindowManagerExt);
        if (!phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(2) || (keyguardServiceDelegate = this.mKeyguardDelegate) == null) {
            return;
        }
        keyguardServiceDelegate.onFinishedWakingUp();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedGoingToSleepGlobal(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate;
        this.mDeviceGoingToSleep = true;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        Objects.requireNonNull(phoneWindowManagerExt);
        if (!phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(4) || (keyguardServiceDelegate = this.mKeyguardDelegate) == null) {
            return;
        }
        keyguardServiceDelegate.onStartedGoingToSleep(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedGoingToSleepGlobal(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate;
        this.mDeviceGoingToSleep = false;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
        Objects.requireNonNull(phoneWindowManagerExt);
        if (!phoneWindowManagerExt.handleGlobalInteractiveIfNeeded(8) || (keyguardServiceDelegate = this.mKeyguardDelegate) == null) {
            return;
        }
        keyguardServiceDelegate.onFinishedGoingToSleep(i, false);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedGoingToSleep(int i, int i2) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Started going to sleep... (groupId=" + i + " why=" + WindowManagerPolicyConstants.offReasonToString(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2)) + ")");
        }
        if (i != 0) {
            return;
        }
        this.mRequestedOrSleepingDefaultDisplay = true;
        this.mIsGoingToSleepDefaultDisplay = true;
        if (!this.mDefaultDisplayPolicy.isScreenOnFully() && !this.mDefaultDisplayPolicy.isScreenOnEarly()) {
            updateScreenOffSleepToken(true, false);
        }
        if (this.mExt.getDexMode() == 2) {
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            Objects.requireNonNull(phoneWindowManagerExt);
            phoneWindowManagerExt.addPendingDualModeInteractive(4);
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onStartedGoingToSleep(i2);
            }
        }
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            this.mExt.startedGoingToSleep();
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedGoingToSleep(int i, int i2) {
        if (i != 0) {
            return;
        }
        EventLogTags.writeScreenToggled(0);
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Finished going to sleep... (groupId=" + i + " why=" + WindowManagerPolicyConstants.offReasonToString(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2)) + ")");
        }
        MetricsLogger.histogram(this.mContext, "screen_timeout", this.mLockScreenTimeout / 1000);
        this.mRequestedOrSleepingDefaultDisplay = false;
        this.mIsGoingToSleepDefaultDisplay = false;
        this.mDefaultDisplayPolicy.setAwake(false);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        if (this.mExt.getDexMode() == 2) {
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            Objects.requireNonNull(phoneWindowManagerExt);
            phoneWindowManagerExt.addPendingDualModeInteractive(8);
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onFinishedGoingToSleep(i2, this.mCameraGestureTriggeredDuringGoingToSleep);
            }
        }
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.finishedGoingToSleep();
        }
        this.mCameraGestureTriggeredDuringGoingToSleep = false;
        this.mCameraGestureTriggered = false;
        if (CoreRune.FW_APPLOCK) {
            this.mExt.clearAppLockedUnLockedApp();
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedWakingUp(int i, int i2) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Started waking up... (groupId=" + i + " why=" + WindowManagerPolicyConstants.onReasonToString(WindowManagerPolicyConstants.translateWakeReasonToOnReason(i2)) + ")");
        }
        if (i != 0) {
            return;
        }
        EventLogTags.writeScreenToggled(1);
        this.mIsGoingToSleepDefaultDisplay = false;
        this.mDefaultDisplayPolicy.setAwake(true);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        if (this.mExt.getDexMode() == 2) {
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            Objects.requireNonNull(phoneWindowManagerExt);
            phoneWindowManagerExt.addPendingDualModeInteractive(1);
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onStartedWakingUp(i2, this.mCameraGestureTriggered);
            }
        }
        this.mExt.startedWakingUp(i2);
        this.mCameraGestureTriggered = false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedWakingUp(int i, int i2) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Finished waking up... (groupId=" + i + " why=" + WindowManagerPolicyConstants.onReasonToString(WindowManagerPolicyConstants.translateWakeReasonToOnReason(i2)) + ")");
        }
        if (i != 0) {
            return;
        }
        if (this.mExt.getDexMode() == 2) {
            PhoneWindowManagerExt phoneWindowManagerExt = this.mExt;
            Objects.requireNonNull(phoneWindowManagerExt);
            phoneWindowManagerExt.addPendingDualModeInteractive(2);
        } else {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onFinishedWakingUp();
            }
        }
        DisplayFoldController displayFoldController = this.mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.finishedWakingUp();
        }
    }

    public final boolean shouldWakeUpWithHomeIntent() {
        if (this.mWakeUpToLastStateTimeout <= 0) {
            return false;
        }
        long j = this.mPowerManagerInternal.getLastWakeup().sleepDurationRealtime;
        if (DEBUG_WAKEUP) {
            Log.i(StartingSurfaceController.TAG, "shouldWakeUpWithHomeIntent: sleepDurationRealtime= " + j + " mWakeUpToLastStateTimeout= " + this.mWakeUpToLastStateTimeout);
        }
        return j > this.mWakeUpToLastStateTimeout;
    }

    public final void wakeUpFromPowerKey(long j) {
        if (wakeUp(j, this.mAllowTheaterModeWakeFromPowerKey, 1, "android.policy:POWER") && shouldWakeUpWithHomeIntent()) {
            startDockOrHome(0, false, true, PowerManager.wakeReasonToString(1));
        }
    }

    public final void wakeUpFromWakeKey(KeyEvent keyEvent) {
        if (wakeUp(keyEvent.getEventTime(), this.mAllowTheaterModeWakeFromKey, 6, "android.policy:KEY(" + keyEvent.getKeyCode() + ")") && shouldWakeUpWithHomeIntent() && keyEvent.getKeyCode() == 3) {
            startDockOrHome(0, true, true, PowerManager.wakeReasonToString(6));
        }
    }

    public boolean wakeUp(long j, boolean z, int i, String str) {
        boolean isTheaterModeEnabled = isTheaterModeEnabled();
        if (!z && isTheaterModeEnabled) {
            return false;
        }
        if (isTheaterModeEnabled) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "theater_mode_on", 0);
        }
        this.mExt.boostWakeUp();
        this.mPowerManager.wakeUp(j, i, str);
        return true;
    }

    public final void finishKeyguardDrawn() {
        if (this.mDefaultDisplayPolicy.finishKeyguardDrawn()) {
            synchronized (this.mLock) {
                if (this.mKeyguardDelegate != null) {
                    this.mHandler.removeMessages(6);
                }
            }
            Trace.asyncTraceBegin(32L, "waitForAllWindowsDrawn", -1);
            this.mWindowManagerInternal.waitForAllWindowsDrawn(this.mHandler.obtainMessage(7, -1, 0), 1000L, -1);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurnedOff(int i, boolean z) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Display" + i + " turned off...");
        }
        if (i == 0) {
            updateScreenOffSleepToken(true, z);
            this.mRequestedOrSleepingDefaultDisplay = false;
            this.mDefaultDisplayPolicy.screenTurnedOff();
            synchronized (this.mLock) {
                KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                if (keyguardServiceDelegate != null) {
                    keyguardServiceDelegate.onScreenTurnedOff();
                }
            }
            this.mDefaultDisplayRotation.updateOrientationListener();
            reportScreenStateToVrManager(false);
            return;
        }
        if (i == 2) {
            updateScreenOffSleepToken(true, 2);
            return;
        }
        if (i == 4) {
            updateScreenOffSleepToken(true, 4);
            DisplayPolicy displayPolicy = this.mCoverViewDisplayPolicy;
            if (displayPolicy != null) {
                displayPolicy.screenTurnedOff();
            }
        }
    }

    public final long getKeyguardDrawnTimeout() {
        if (((SystemServiceManager) LocalServices.getService(SystemServiceManager.class)).isBootCompleted()) {
            return this.mKeyguardDrawnTimeout;
        }
        return 5000L;
    }

    public final WallpaperManagerInternal getWallpaperManagerInternal() {
        if (this.mWallpaperManagerInternal == null) {
            this.mWallpaperManagerInternal = (WallpaperManagerInternal) LocalServices.getService(WallpaperManagerInternal.class);
        }
        return this.mWallpaperManagerInternal;
    }

    public final void reportScreenTurningOnToWallpaper(int i) {
        WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
        if (wallpaperManagerInternal != null) {
            wallpaperManagerInternal.onScreenTurningOn(i);
        }
    }

    public final void reportScreenTurnedOnToWallpaper(int i) {
        WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
        if (wallpaperManagerInternal != null) {
            wallpaperManagerInternal.onScreenTurnedOn(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurningOn(int i, WindowManagerPolicy.ScreenOnListener screenOnListener) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Display " + i + " turning on...");
        }
        reportScreenTurningOnToWallpaper(i);
        if (i == 0) {
            Trace.asyncTraceBegin(32L, "screenTurningOn", 0);
            updateScreenOffSleepToken(false, false);
            this.mDefaultDisplayPolicy.screenTurnedOn(screenOnListener);
            this.mBootAnimationDismissable = false;
            synchronized (this.mLock) {
                KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
                if (keyguardServiceDelegate != null && keyguardServiceDelegate.hasKeyguard()) {
                    this.mHandler.removeMessages(6);
                    this.mHandler.sendEmptyMessageDelayed(6, getKeyguardDrawnTimeout());
                    Slog.i(StartingSurfaceController.TAG, "screenTurningOn(+), before calling onScreenTurningOn to KeyguardDelegate");
                    this.mKeyguardDelegate.onScreenTurningOn(this.mKeyguardDrawnCallback);
                    Slog.i(StartingSurfaceController.TAG, "screenTurningOn(-), after calling onScreenTurningOn to KeyguardDelegate");
                } else {
                    if (DEBUG_WAKEUP) {
                        Slog.d(StartingSurfaceController.TAG, "null mKeyguardDelegate: setting mKeyguardDrawComplete.");
                    }
                    this.mHandler.sendEmptyMessage(5);
                }
            }
            return;
        }
        if (i == 2) {
            updateScreenOffSleepToken(false, 2);
        } else if (i == 4) {
            updateScreenOffSleepToken(false, 4);
            DisplayPolicy displayPolicy = this.mCoverViewDisplayPolicy;
            if (displayPolicy != null) {
                displayPolicy.screenTurnedOn(null);
            }
        }
        this.mScreenOnListeners.put(i, screenOnListener);
        Trace.asyncTraceBegin(32L, "waitForAllWindowsDrawn", i);
        this.mWindowManagerInternal.waitForAllWindowsDrawn(this.mHandler.obtainMessage(7, i, 0), 1000L, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurnedOn(int i) {
        if (DEBUG_WAKEUP) {
            Slog.i(StartingSurfaceController.TAG, "Display " + i + " turned on...");
        }
        reportScreenTurnedOnToWallpaper(i);
        if (i != 0) {
            return;
        }
        synchronized (this.mLock) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onScreenTurnedOn();
            }
        }
        reportScreenStateToVrManager(true);
        this.mExt.mWindowManagerFuncs.postRotationInfoForAudioManager();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurningOff(int i, WindowManagerPolicy.ScreenOffListener screenOffListener) {
        this.mWindowManagerFuncs.screenTurningOff(i, screenOffListener);
        if (i != 0) {
            return;
        }
        this.mRequestedOrSleepingDefaultDisplay = true;
        synchronized (this.mLock) {
            KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
            if (keyguardServiceDelegate != null) {
                keyguardServiceDelegate.onScreenTurningOff();
            }
        }
    }

    public final void reportScreenStateToVrManager(boolean z) {
        if (this.mVrManagerInternal == null) {
            return;
        }
        this.mVrManagerInternal.onScreenStateChanged(z);
    }

    public final void finishWindowsDrawn(int i) {
        if (i != 0 && i != -1) {
            WindowManagerPolicy.ScreenOnListener screenOnListener = (WindowManagerPolicy.ScreenOnListener) this.mScreenOnListeners.removeReturnOld(i);
            if (screenOnListener != null) {
                screenOnListener.onScreenOn();
                return;
            }
            return;
        }
        if (this.mDefaultDisplayPolicy.finishWindowsDrawn()) {
            finishScreenTurningOn();
        }
    }

    public final void finishScreenTurningOn() {
        this.mDefaultDisplayRotation.updateOrientationListener();
        WindowManagerPolicy.ScreenOnListener screenOnListener = this.mDefaultDisplayPolicy.getScreenOnListener();
        if (this.mDefaultDisplayPolicy.finishScreenTurningOn()) {
            Trace.asyncTraceEnd(32L, "screenTurningOn", 0);
            enableScreen(screenOnListener, true);
        }
    }

    public final void enableScreen(WindowManagerPolicy.ScreenOnListener screenOnListener, boolean z) {
        boolean z2;
        boolean isAwake = this.mDefaultDisplayPolicy.isAwake();
        synchronized (this.mLock) {
            z2 = false;
            if (!this.mKeyguardDrawnOnce && isAwake) {
                this.mKeyguardDrawnOnce = true;
                if (this.mBootMessageNeedsHiding) {
                    this.mBootMessageNeedsHiding = false;
                    hideBootMessages();
                }
                z2 = true;
            }
        }
        if (z && screenOnListener != null) {
            screenOnListener.onScreenOn();
        }
        if (z2) {
            this.mWindowManagerFuncs.enableScreenIfNeeded();
        }
    }

    public final void handleHideBootMessage() {
        synchronized (this.mLock) {
            if (!this.mKeyguardDrawnOnce) {
                this.mBootMessageNeedsHiding = true;
            } else if (this.mBootMsgDialog != null) {
                if (DEBUG_WAKEUP) {
                    Slog.d(StartingSurfaceController.TAG, "handleHideBootMessage: dismissing");
                }
                this.mBootMsgDialog.dismiss();
                this.mBootMsgDialog = null;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isScreenOn() {
        return this.mDefaultDisplayPolicy.isScreenOnEarly();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isScreenOn(int i) {
        DisplayPolicy displayPolicy;
        if (i == 2) {
            return this.mExt.getDexMode() == 2 && this.mDexDisplay.getState() == 2;
        }
        if (i == 4 && (displayPolicy = this.mCoverViewDisplayPolicy) != null) {
            return displayPolicy.isScreenOnEarly();
        }
        return isScreenOn();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean okToAnimate(boolean z) {
        return (z || isScreenOn()) && !this.mDeviceGoingToSleep;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void enableKeyguard(boolean z) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.setKeyguardEnabled(z);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void exitKeyguardSecurely(WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.verifyUnlock(onKeyguardExitResult);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardShowing() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isShowing();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardShowingAndNotOccluded() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        return (keyguardServiceDelegate == null || !keyguardServiceDelegate.isShowing() || isKeyguardOccluded()) ? false : true;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardTrustedLw() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isTrusted();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardLocked() {
        return keyguardOn();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardSecure(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isSecure(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardOccluded() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isOccluded();
    }

    public boolean inKeyguardRestrictedKeyInputMode() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        return keyguardServiceDelegate.isInputRestricted();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardUnoccluding() {
        return keyguardOn() && !this.mWindowManagerFuncs.isAppTransitionStateIdle();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dismissKeyguardLw(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null && keyguardServiceDelegate.isShowing()) {
            if (DEBUG_KEYGUARD) {
                Slog.d(StartingSurfaceController.TAG, "PWM.dismissKeyguardLw");
            }
            this.mKeyguardDelegate.dismiss(iKeyguardDismissCallback, charSequence);
        } else if (iKeyguardDismissCallback != null) {
            try {
                iKeyguardDismissCallback.onDismissError();
            } catch (RemoteException e) {
                Slog.w(StartingSurfaceController.TAG, "Failed to call callback", e);
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardDrawnLw() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mKeyguardDrawnOnce;
        }
        return z;
    }

    public void startKeyguardExitAnimation(long j) {
        if (this.mKeyguardDelegate != null) {
            if (DEBUG_KEYGUARD) {
                Slog.d(StartingSurfaceController.TAG, "PWM.startKeyguardExitAnimation");
            }
            this.mKeyguardDelegate.startKeyguardExitAnimation(j);
        }
    }

    public void sendCloseSystemWindows() {
        PhoneWindow.sendCloseSystemWindows(this.mContext, (String) null);
    }

    public void sendCloseSystemWindows(String str) {
        PhoneWindow.sendCloseSystemWindows(this.mContext, str);
    }

    public void sendCloseSystemWindowsInDisplay(String str, int i) {
        PhoneWindow.sendCloseSystemWindowsInDisplay(str, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setSafeMode(boolean z) {
        this.mSafeMode = z;
        if (z) {
            performHapticFeedback(10001, true, "Safe Mode Enabled");
        }
    }

    public static long[] getLongIntArray(Resources resources, int i) {
        return ArrayUtils.convertToLongArray(resources.getIntArray(i));
    }

    public final void bindKeyguard() {
        synchronized (this.mLock) {
            if (this.mKeyguardBound) {
                return;
            }
            this.mKeyguardBound = true;
            this.mKeyguardDelegate.bindService(this.mContext);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void onSystemUiStarted() {
        bindKeyguard();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void systemReady() {
        this.mKeyguardDelegate.onSystemReady();
        this.mVrManagerInternal = (VrManagerInternal) LocalServices.getService(VrManagerInternal.class);
        if (this.mVrManagerInternal != null) {
            this.mVrManagerInternal.addPersistentVrModeStateListener(this.mPersistentVrModeListener);
        }
        readCameraLensCoverState();
        updateUiMode();
        this.mDefaultDisplayRotation.updateOrientationListener();
        synchronized (this.mLock) {
            this.mSystemReady = true;
            this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManager.16
                @Override // java.lang.Runnable
                public void run() {
                    PhoneWindowManager.this.updateSettings();
                    PhoneWindowManager.this.mExt.mSettingsObserver.updateSettings();
                }
            });
            if (this.mSystemBooted) {
                this.mKeyguardDelegate.onBootCompleted();
            }
        }
        this.mAutofillManagerInternal = (AutofillManagerInternal) LocalServices.getService(AutofillManagerInternal.class);
        this.mGestureLauncherService = (GestureLauncherService) LocalServices.getService(GestureLauncherService.class);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void systemBooted() {
        bindKeyguard();
        synchronized (this.mLock) {
            this.mSystemBooted = true;
            if (this.mSystemReady) {
                this.mKeyguardDelegate.onBootCompleted();
            }
        }
        this.mSideFpsEventHandler.onFingerprintSensorReady();
        startedWakingUp(0, 0);
        finishedWakingUp(0, 0);
        boolean z = this.mDisplayManager.getDisplay(0).getState() == 2;
        boolean z2 = this.mDefaultDisplayPolicy.getScreenOnListener() != null;
        if (z || z2) {
            screenTurningOn(0, this.mDefaultDisplayPolicy.getScreenOnListener());
            screenTurnedOn(0);
        } else {
            this.mBootAnimationDismissable = true;
            enableScreen(null, false);
        }
        this.mExt.systemBooted();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean canDismissBootAnimation() {
        return this.mDefaultDisplayPolicy.isKeyguardDrawComplete() || this.mBootAnimationDismissable;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void hideBootMessages() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void userActivity(int i, int i2) {
        if (i == 0 && i2 == 2) {
            this.mDefaultDisplayPolicy.onUserActivityEventTouch();
        }
        synchronized (this.mScreenLockTimeout) {
            if (this.mLockScreenTimerActive) {
                this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                this.mHandler.postDelayed(this.mScreenLockTimeout, this.mLockScreenTimeout);
            }
        }
    }

    public class ScreenLockTimeout implements Runnable {
        public Bundle options;

        public ScreenLockTimeout() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                KeyguardServiceDelegate keyguardServiceDelegate = PhoneWindowManager.this.mKeyguardDelegate;
                if (keyguardServiceDelegate != null) {
                    keyguardServiceDelegate.doKeyguardTimeout(this.options);
                }
                PhoneWindowManager phoneWindowManager = PhoneWindowManager.this;
                phoneWindowManager.mLockScreenTimerActive = false;
                phoneWindowManager.mLockNowPending = false;
                this.options = null;
            }
        }

        public void setLockOptions(Bundle bundle) {
            this.options = bundle;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void lockNow(Bundle bundle) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
        if (bundle != null) {
            this.mScreenLockTimeout.setLockOptions(bundle);
        }
        this.mHandler.post(this.mScreenLockTimeout);
        Log.d(StartingSurfaceController.TAG, "lockNow, pid " + Binder.getCallingPid() + " , uid" + Binder.getCallingUid());
        synchronized (this.mScreenLockTimeout) {
            this.mLockNowPending = true;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setAllowLockscreenWhenOn(int i, boolean z) {
        if (z) {
            this.mAllowLockscreenWhenOnDisplays.add(Integer.valueOf(i));
        } else {
            this.mAllowLockscreenWhenOnDisplays.remove(Integer.valueOf(i));
        }
        updateLockScreenTimeout();
    }

    public final void updateLockScreenTimeout() {
        KeyguardServiceDelegate keyguardServiceDelegate;
        synchronized (this.mScreenLockTimeout) {
            if (this.mLockNowPending) {
                Log.w(StartingSurfaceController.TAG, "lockNow pending, ignore updating lockscreen timeout");
                return;
            }
            boolean z = !this.mAllowLockscreenWhenOnDisplays.isEmpty() && this.mDefaultDisplayPolicy.isAwake() && (keyguardServiceDelegate = this.mKeyguardDelegate) != null && keyguardServiceDelegate.isSecure(this.mCurrentUserId);
            if (this.mLockScreenTimerActive != z) {
                if (z) {
                    this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                    this.mHandler.postDelayed(this.mScreenLockTimeout, this.mLockScreenTimeout);
                } else {
                    this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                }
                this.mLockScreenTimerActive = z;
            }
        }
    }

    public final void updateScreenOffSleepToken(boolean z, boolean z2) {
        if (z) {
            this.mScreenOffSleepTokenAcquirer.acquire(0, z2);
        } else {
            this.mScreenOffSleepTokenAcquirer.release(0);
        }
    }

    public final void updateScreenOffSleepToken(boolean z, int i) {
        if (z) {
            this.mScreenOffSleepTokenAcquirer.acquire(i);
        } else {
            this.mScreenOffSleepTokenAcquirer.release(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void enableScreenAfterBoot() {
        readLidState();
        applyLidSwitchState();
        updateRotation(true);
    }

    public final void applyLidSwitchState() {
        if (this.mDefaultDisplayPolicy.getLidState() == 0) {
            int lidBehavior = getLidBehavior();
            if (lidBehavior == 1) {
                sleepDefaultDisplay(SystemClock.uptimeMillis(), 3, 1);
            } else if (lidBehavior == 2) {
                this.mWindowManagerFuncs.lockDeviceNow();
            }
        }
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
        }
    }

    public void updateUiMode() {
        if (this.mUiModeManager == null) {
            this.mUiModeManager = IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode"));
        }
        try {
            this.mUiMode = this.mUiModeManager.getCurrentModeType();
        } catch (RemoteException unused) {
        }
    }

    public void updateRotation(boolean z) {
        this.mWindowManagerFuncs.updateRotation(z, false);
    }

    public Intent createHomeDockIntent() {
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
                    int dockMode = this.mDefaultDisplayPolicy.getDockMode();
                    if (dockMode == 1 || dockMode == 4 || dockMode == 3) {
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
        ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(intent, 65664, this.mCurrentUserId);
        ActivityInfo activityInfo = resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
        if (activityInfo == null || (bundle = activityInfo.metaData) == null || !bundle.getBoolean("android.dock_home")) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setClassName(activityInfo.packageName, activityInfo.name);
        return intent2;
    }

    public void startDockOrHome(int i, boolean z, boolean z2, String str) {
        try {
            ActivityManager.getService().stopAppSwitches();
        } catch (RemoteException unused) {
        }
        sendCloseSystemWindowsInDisplay("homekey", i);
        if (z2) {
            awakenDreams();
        }
        if (!this.mHasFeatureAuto && !isUserSetupComplete()) {
            Slog.i(StartingSurfaceController.TAG, "Not going home because user setup is in progress.");
            return;
        }
        this.mExt.onHomeChangedBooster();
        this.mExt.startDockOrHome();
        Intent createHomeDockIntent = createHomeDockIntent();
        if (createHomeDockIntent != null) {
            if (z) {
                try {
                    createHomeDockIntent.putExtra("android.intent.extra.FROM_HOME_KEY", z);
                } catch (ActivityNotFoundException unused2) {
                }
            }
            startActivityAsUser(createHomeDockIntent, UserHandle.CURRENT);
            return;
        }
        if (DEBUG_WAKEUP) {
            Log.d(StartingSurfaceController.TAG, "startDockOrHome: startReason= " + str);
        }
        this.mActivityTaskManagerInternal.startHomeOnDisplay(this.mUserManagerInternal.getUserAssignedToDisplay(i), str, i, true, z);
    }

    public void startDockOrHome(int i, boolean z, boolean z2) {
        startDockOrHome(i, z, z2, "startDockOrHome");
    }

    public boolean goHome() {
        IActivityTaskManager service;
        String opPackageName;
        String attributionTag;
        Intent intent;
        if (!isUserSetupComplete()) {
            Slog.i(StartingSurfaceController.TAG, "Not going home because user setup is in progress.");
            return false;
        }
        try {
            if (SystemProperties.getInt("persist.sys.uts-test-mode", 0) == 1) {
                Log.d(StartingSurfaceController.TAG, "UTS-TEST-MODE");
            } else {
                ActivityManager.getService().stopAppSwitches();
                sendCloseSystemWindows();
                Intent createHomeDockIntent = createHomeDockIntent();
                if (createHomeDockIntent != null && ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), createHomeDockIntent, createHomeDockIntent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 1, (ProfilerInfo) null, (Bundle) null, -2) == 1) {
                    return false;
                }
            }
            service = ActivityTaskManager.getService();
            opPackageName = this.mContext.getOpPackageName();
            attributionTag = this.mContext.getAttributionTag();
            intent = this.mHomeIntent;
        } catch (RemoteException unused) {
        }
        return service.startActivityAsUser((IApplicationThread) null, opPackageName, attributionTag, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 1, (ProfilerInfo) null, (Bundle) null, -2) != 1;
    }

    public final boolean isTheaterModeEnabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1;
    }

    public boolean performHapticFeedback(int i, boolean z, String str) {
        return performHapticFeedback(Process.myUid(), this.mContext.getOpPackageName(), i, z, str);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isGlobalKey(int i) {
        return this.mGlobalKeyManager.shouldHandleGlobalKey(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean performHapticFeedback(int i, String str, int i2, boolean z, String str2) {
        return this.mExt.performHapticFeedbackLw(i, str, i2, z, str2);
    }

    public VibrationAttributes getVibrationAttributes(int i) {
        if (i == 14 || i == 15) {
            return PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES;
        }
        if (i != 10002 && i != 10003) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    return TOUCH_VIBRATION_ATTRIBUTES;
            }
        }
        return HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES;
    }

    public boolean hasNavigationBar() {
        return this.mDefaultDisplayPolicy.hasNavigationBar();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setDismissImeOnBackKeyPressed(boolean z) {
        this.mDismissImeOnBackKeyPressed = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setCurrentUserLw(int i) {
        this.mCurrentUserId = i;
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.setCurrentUser(i);
        }
        AccessibilityShortcutController accessibilityShortcutController = this.mAccessibilityShortcutController;
        if (accessibilityShortcutController != null) {
            accessibilityShortcutController.setCurrentUser(i);
        }
        StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.setCurrentUser(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setSwitchingUser(boolean z) {
        this.mKeyguardDelegate.setSwitchingUser(z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169922L, this.mDefaultDisplayRotation.getUserRotationMode());
        protoOutputStream.write(1159641169923L, this.mDefaultDisplayRotation.getUserRotation());
        protoOutputStream.write(1159641169924L, this.mDefaultDisplayRotation.getCurrentAppOrientation());
        protoOutputStream.write(1133871366149L, this.mDefaultDisplayPolicy.isScreenOnFully());
        protoOutputStream.write(1133871366150L, this.mDefaultDisplayPolicy.isKeyguardDrawComplete());
        protoOutputStream.write(1133871366151L, this.mDefaultDisplayPolicy.isWindowManagerDrawComplete());
        protoOutputStream.write(1133871366156L, isKeyguardOccluded());
        protoOutputStream.write(1133871366157L, this.mKeyguardOccludedChanged);
        protoOutputStream.write(1133871366158L, this.mPendingKeyguardOccluded);
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.dumpDebug(protoOutputStream, 1146756268052L);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dump(String str, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mSafeMode=");
        printWriter.print(this.mSafeMode);
        printWriter.print(" mSystemReady=");
        printWriter.print(this.mSystemReady);
        printWriter.print(" mSystemBooted=");
        printWriter.println(this.mSystemBooted);
        printWriter.print(str);
        printWriter.print("mCameraLensCoverState=");
        printWriter.println(WindowManagerPolicy.WindowManagerFuncs.cameraLensStateToString(this.mCameraLensCoverState));
        printWriter.print(str);
        printWriter.print("mWakeGestureEnabledSetting=");
        printWriter.println(this.mWakeGestureEnabledSetting);
        printWriter.print(str);
        printWriter.print("mUiMode=");
        printWriter.print(Configuration.uiModeToString(this.mUiMode));
        printWriter.print("mEnableCarDockHomeCapture=");
        printWriter.println(this.mEnableCarDockHomeCapture);
        printWriter.print(str);
        printWriter.print("mLidKeyboardAccessibility=");
        printWriter.print(this.mLidKeyboardAccessibility);
        printWriter.print(" mLidNavigationAccessibility=");
        printWriter.print(this.mLidNavigationAccessibility);
        printWriter.print(" getLidBehavior=");
        printWriter.println(lidBehaviorToString(getLidBehavior()));
        printWriter.print(str);
        printWriter.print("mLongPressOnBackBehavior=");
        printWriter.println(longPressOnBackBehaviorToString(this.mLongPressOnBackBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnHomeBehavior=");
        printWriter.println(longPressOnHomeBehaviorToString(this.mLongPressOnHomeBehavior));
        printWriter.print(str);
        printWriter.print("mDoubleTapOnHomeBehavior=");
        printWriter.println(doubleTapOnHomeBehaviorToString(this.mDoubleTapOnHomeBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnPowerBehavior=");
        printWriter.println(shortPressOnPowerBehaviorToString(this.mShortPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnPowerBehavior=");
        printWriter.println(longPressOnPowerBehaviorToString(this.mLongPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnPowerAssistantTimeoutMs=");
        printWriter.println(this.mLongPressOnPowerAssistantTimeoutMs);
        printWriter.print(str);
        printWriter.print("mVeryLongPressOnPowerBehavior=");
        printWriter.println(veryLongPressOnPowerBehaviorToString(this.mVeryLongPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mDoublePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mDoublePressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mTriplePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mTriplePressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mPowerVolUpBehavior=");
        printWriter.println(powerVolumeUpBehaviorToString(this.mPowerVolUpBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnSleepBehavior=");
        printWriter.println(shortPressOnSleepBehaviorToString(this.mShortPressOnSleepBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnWindowBehavior=");
        printWriter.println(shortPressOnWindowBehaviorToString(this.mShortPressOnWindowBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnStemPrimaryBehavior=");
        printWriter.println(shortPressOnStemPrimaryBehaviorToString(this.mShortPressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mDoublePressOnStemPrimaryBehavior=");
        printWriter.println(doublePressOnStemPrimaryBehaviorToString(this.mDoublePressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mTriplePressOnStemPrimaryBehavior=");
        printWriter.println(triplePressOnStemPrimaryBehaviorToString(this.mTriplePressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnStemPrimaryBehavior=");
        printWriter.println(longPressOnStemPrimaryBehaviorToString(this.mLongPressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mAllowStartActivityForLongPressOnPowerDuringSetup=");
        printWriter.println(this.mAllowStartActivityForLongPressOnPowerDuringSetup);
        printWriter.print(str);
        printWriter.print("mHasSoftInput=");
        printWriter.print(this.mHasSoftInput);
        printWriter.print(" mHapticTextHandleEnabled=");
        printWriter.println(this.mHapticTextHandleEnabled);
        printWriter.print(str);
        printWriter.print("mDismissImeOnBackKeyPressed=");
        printWriter.print(this.mDismissImeOnBackKeyPressed);
        printWriter.print(" mIncallPowerBehavior=");
        printWriter.println(incallPowerBehaviorToString(this.mIncallPowerBehavior));
        printWriter.print(str);
        printWriter.print("mIncallBackBehavior=");
        printWriter.print(incallBackBehaviorToString(this.mIncallBackBehavior));
        printWriter.print(" mEndcallBehavior=");
        printWriter.println(endcallBehaviorToString(this.mEndcallBehavior));
        printWriter.print(str);
        printWriter.print("mDisplayHomeButtonHandlers=");
        for (int i = 0; i < this.mDisplayHomeButtonHandlers.size(); i++) {
            printWriter.println(this.mDisplayHomeButtonHandlers.get(this.mDisplayHomeButtonHandlers.keyAt(i)));
        }
        printWriter.print(str);
        printWriter.print("mKeyguardOccluded=");
        printWriter.print(isKeyguardOccluded());
        printWriter.print(" mKeyguardOccludedChanged=");
        printWriter.print(this.mKeyguardOccludedChanged);
        printWriter.print(" mPendingKeyguardOccluded=");
        printWriter.println(this.mPendingKeyguardOccluded);
        printWriter.print(str);
        printWriter.print("mAllowLockscreenWhenOnDisplays=");
        printWriter.print(!this.mAllowLockscreenWhenOnDisplays.isEmpty());
        printWriter.print(" mLockScreenTimeout=");
        printWriter.print(this.mLockScreenTimeout);
        printWriter.print(" mLockScreenTimerActive=");
        printWriter.println(this.mLockScreenTimerActive);
        this.mGlobalKeyManager.dump(str, printWriter);
        this.mKeyCombinationManager.dump(str, printWriter);
        this.mSingleKeyGestureDetector.dump(str, printWriter);
        MyWakeGestureListener myWakeGestureListener = this.mWakeGestureListener;
        if (myWakeGestureListener != null) {
            myWakeGestureListener.dump(printWriter, str);
        }
        BurnInProtectionHelper burnInProtectionHelper = this.mBurnInProtectionHelper;
        if (burnInProtectionHelper != null) {
            burnInProtectionHelper.dump(str, printWriter);
        }
        KeyguardServiceDelegate keyguardServiceDelegate = this.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.dump(str, printWriter);
        }
        printWriter.print(str);
        printWriter.println("Looper state:");
        this.mHandler.getLooper().dump(new PrintWriterPrinter(printWriter), str + "  ");
        this.mExt.dump(str, printWriter);
    }

    public static String endcallBehaviorToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("home|");
        }
        if ((i & 2) != 0) {
            sb.append("sleep|");
        }
        int length = sb.length();
        return length == 0 ? "<nothing>" : sb.substring(0, length - 1);
    }

    public static String longPressOnBackBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "LONG_PRESS_BACK_GO_TO_VOICE_ASSIST" : "LONG_PRESS_BACK_NOTHING";
    }

    public static String longPressOnHomeBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 101 ? Integer.toString(i) : "LONG_PRESS_HOME_BIXBY_TOUCH" : "LONG_PRESS_HOME_SEARCLE" : "LONG_PRESS_HOME_NOTIFICATION_PANEL" : "LONG_PRESS_HOME_ASSIST" : "LONG_PRESS_HOME_ALL_APPS" : "LONG_PRESS_HOME_NOTHING";
    }

    public static String doubleTapOnHomeBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "DOUBLE_TAP_HOME_PIP_MENU" : "DOUBLE_TAP_HOME_RECENT_SYSTEM_UI" : "DOUBLE_TAP_HOME_NOTHING";
    }

    public static String shortPressOnPowerBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : "SHORT_PRESS_POWER_CLOSE_IME_OR_GO_HOME" : "SHORT_PRESS_POWER_GO_HOME" : "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP_AND_GO_HOME" : "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP" : "SHORT_PRESS_POWER_GO_TO_SLEEP" : "SHORT_PRESS_POWER_NOTHING";
    }

    public static String longPressOnPowerBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? i != 101 ? i != 102 ? Integer.toString(i) : "LONG_PRESS_POWER_KEY_CUSTOMIZATION" : "LONG_PRESS_POWER_WAKE_BIXBY" : "LONG_PRESS_POWER_ASSISTANT" : "LONG_PRESS_POWER_GO_TO_VOICE_ASSIST" : "LONG_PRESS_POWER_SHUT_OFF_NO_CONFIRM" : "LONG_PRESS_POWER_SHUT_OFF" : "LONG_PRESS_POWER_GLOBAL_ACTIONS" : "LONG_PRESS_POWER_NOTHING";
    }

    public static String veryLongPressOnPowerBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "VERY_LONG_PRESS_POWER_GLOBAL_ACTIONS" : "VERY_LONG_PRESS_POWER_NOTHING";
    }

    public static String powerVolumeUpBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "POWER_VOLUME_UP_BEHAVIOR_GLOBAL_ACTIONS" : "POWER_VOLUME_UP_BEHAVIOR_MUTE" : "POWER_VOLUME_UP_BEHAVIOR_NOTHING";
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

    public static String shortPressOnSleepBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "SHORT_PRESS_SLEEP_GO_TO_SLEEP_AND_GO_HOME" : "SHORT_PRESS_SLEEP_GO_TO_SLEEP";
    }

    public static String shortPressOnWindowBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "SHORT_PRESS_WINDOW_PICTURE_IN_PICTURE" : "SHORT_PRESS_WINDOW_NOTHING";
    }

    public static String shortPressOnStemPrimaryBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "SHORT_PRESS_PRIMARY_LAUNCH_ALL_APPS" : "SHORT_PRESS_PRIMARY_NOTHING";
    }

    public static String doublePressOnStemPrimaryBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "DOUBLE_PRESS_PRIMARY_SWITCH_RECENT_APP" : "DOUBLE_PRESS_PRIMARY_NOTHING";
    }

    public static String triplePressOnStemPrimaryBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "TRIPLE_PRESS_PRIMARY_TOGGLE_ACCESSIBILITY" : "TRIPLE_PRESS_PRIMARY_NOTHING";
    }

    public static String longPressOnStemPrimaryBehaviorToString(int i) {
        return i != 0 ? i != 1 ? Integer.toString(i) : "LONG_PRESS_PRIMARY_LAUNCH_VOICE_ASSISTANT" : "LONG_PRESS_PRIMARY_NOTHING";
    }

    public static String lidBehaviorToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "LID_BEHAVIOR_LOCK" : "LID_BEHAVIOR_SLEEP" : "LID_BEHAVIOR_NONE";
    }

    public static boolean isLongPressToAssistantEnabled(Context context) {
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "clockwork_long_press_to_assistant_enabled", 1, -2);
        if (Log.isLoggable(StartingSurfaceController.TAG, 3)) {
            Log.d(StartingSurfaceController.TAG, "longPressToAssistant = " + intForUser);
        }
        return intForUser == 1;
    }

    public final void launchTargetSearchActivity() {
        Intent intent;
        if (this.mSearchKeyTargetActivity != null) {
            intent = new Intent();
            intent.setComponent(this.mSearchKeyTargetActivity);
        } else {
            intent = new Intent("android.intent.action.WEB_SEARCH");
        }
        intent.addFlags(270532608);
        try {
            startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (ActivityNotFoundException unused) {
            Slog.e(StartingSurfaceController.TAG, "Could not resolve activity with : " + intent.getComponent().flattenToString() + " name.");
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public WindowManagerPolicyExt createPolicyExtension(Context context, WindowManagerPolicyExt.WindowManagerFuncs windowManagerFuncs) {
        return new PhoneWindowManagerExt(context, this, windowManagerFuncs);
    }

    public void setSamsungPolicy(PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mExt = phoneWindowManagerExt;
    }

    public final void interceptGlobalActionChord() {
        if (CoreRune.IS_FACTORY_BINARY) {
            Log.d(StartingSurfaceController.TAG, "Global Action is canceled because of factory mode");
            return;
        }
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(10, 1, 0), 1000L);
    }

    public final void cancelPendingGlobalActionChord() {
        this.mHandler.removeMessages(10);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedEarlyWakingUp(int i) {
        this.mExt.startedEarlyWakingUp(i);
    }
}
