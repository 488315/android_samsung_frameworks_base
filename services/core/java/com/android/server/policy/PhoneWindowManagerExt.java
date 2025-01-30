package com.android.server.policy;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.BootProgressDialog;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.internal.accessibility.AccessibilityDirectAccessController;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.GestureLauncherService;
import com.android.server.LocalServices;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.PersonaManagerService;
import com.android.server.wm.ActivityManagerPerformance;
import com.android.server.wm.CoverPolicy;
import com.android.server.wm.DisplayPolicy;
import com.android.server.wm.TspStateController;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WmCoverState;
import com.android.server.policy.BixbyService;
import com.android.server.policy.KeyCombinationManager;
import com.android.server.policy.KeyCustomizationConstants;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.SideKeyDoublePress;
import com.android.server.policy.SingleKeyGestureDetector;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.policy.WindowManagerPolicyExt;
import com.android.server.policy.keyguard.KeyguardServiceDelegate;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import com.samsung.android.telecom.SemTelecomManager;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import com.samsung.android.view.SemWindowManager;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PhoneWindowManagerExt implements WindowManagerPolicyExt {
    public static final Set KEYCODE_DEBUG_LOG_ALLOWLIST;
    public static final Set KEYCODE_KEY_COMBINATION_ALLOWLIST;
    public static IBinder sdhmsBinder;
    public final int FINISHED_SLEEP;
    public final int FINISHED_WAKE;
    public final int STARTED_SLEEP;
    public final int STARTED_WAKE;
    public boolean mAcceptCallHomeConsumed;
    public AccessibilityDirectAccessController mAccessibilityDirectAccessController;
    public PendingIntent mAlarmPendingIntent;
    public AppOpsManager mAppOpsManager;
    public String mAssistantAppName;
    public AudioManager mAudioManager;
    public BixbyService mBixbyService;
    public int mBoldFontEnabled;
    public int mButtonShapeEnabled;
    public Display mCarLifeDisplay;
    public DisplayPolicy mCarLifeDisplayPolicy;
    public Context mContext;
    public float mCursorThicknessScale;
    public volatile boolean mDexKeyguardOccluded;
    public boolean mDexKeyguardOccludedChanged;
    public boolean mEnableReserveBatteryMode;
    public int mFoldOpenCount;
    public Object mFoldSaLock;
    public boolean mFoldSaLoggingCanceled;
    public BroadcastReceiver mFoldSaLoggingReceiver;
    public long mFoldedTime;
    public boolean mGlobalActionsByKeyCombnation;
    public Handler mHandler;
    public HotKey mHotKey;
    public Intent mIntentEmergencySos;
    public boolean mIsAccessibilityShortcutVolupPower;
    public boolean mIsAnyKeyMode;
    public boolean mIsCameraSensorToggleSupported;
    public boolean mIsCustomBugreportWriterEnabled;
    public boolean mIsDoubleTapPremiumWatchOn;
    public boolean mIsDoubleTapToWakeUp;
    public boolean mIsDoubleTapToWakeUpSupported;
    public boolean mIsInteractionControlEnabled;
    public boolean mIsMicSensorToggleSupported;
    public boolean mIsPalmTouchDownToSleep;
    public boolean mIsPogoKeyboardConnected;
    public boolean mIsPowerKeyBlocked;
    public boolean mIsPremiumWatchOn;
    public boolean mIsRequestedTentMode;
    public boolean mIsSktPhoneRelaxMode;
    public boolean mIsVolumeKeyBlocked;
    public boolean mIsVolumeUpKeyMode;
    public boolean mIsVolumeUpKeyPressed;
    public KeyCustomizationManager mKeyCustomizationPolicy;
    public KeyboardShortcutManager mKeyboardShortcutPolicy;
    public LockPatternUtils mLockPatternUtils;
    public SparseIntArray mLockTaskFeatures;
    public int mLockTaskModeState;
    public InputManager.SemOnMultiFingerGestureListener mMultiFingerGestureListener;
    public final BroadcastReceiver mMultiuserReceiver;
    public boolean mNavBarImeBtnEnabled;
    public boolean mNavGetureHintEnabled;
    public final BroadcastReceiver mPalmTouchReceiver;
    public final Consumer mPenDetachNotiConsumer;
    public Intent mPenInsertIntent;
    public boolean mPenSoundEnabled;
    public String mPenSoundFilePath;
    public PenSoundInfo mPenSoundInfo;
    public int mPenState;
    public final int mPenType;
    public boolean mPenVibrationEnabled;
    public boolean mPendingDexKeyguardOccluded;
    public int mPendingDualModeInteractive;
    public PersonaManagerService mPersonaManagerService;
    public PhoneWindowManager mPolicy;
    public ProKioskManager mProKioskManager;
    public BroadcastReceiver mProximityChangeReceiver;
    public boolean mReserveBatteryMode;
    public boolean mScreenOffMemoEnabled;
    public Intent mScreenOffMemoIntent;
    public SettingsObserver mSettingsObserver;
    public boolean mShowKeyboardBtnEnabled;
    public SpenGestureManager mSpenGestureManager;
    public final Runnable mStopLockTaskModePinnedChordLongPress;
    public ProgressDialog mSubBootMsgDialog;
    public SystemKeyManager mSystemKeyPolicy;
    public ComponentName mTopActivity;
    public TspStateController mTspStateController;
    public Vibrator mVibrator;
    public boolean mVolumeKeyLongPress;
    public final Runnable mWakeAndUnfoldedRunning;
    public boolean mWakeAndUnfoldedTriggered;
    public final Runnable mWakeAndUnlockRunning;
    public boolean mWakeAndUnlockTriggered;
    public WindowManagerPolicyExt.WindowManagerFuncs mWindowManagerFuncs;
    public final Object mLock = new Object();
    public int mLastDexMode = 0;
    public int mWakingUpReason = 0;
    public boolean mIssueTrackerLoggedIn = false;
    public boolean mIsLastGesture3FingerBottom = false;
    public boolean mBootCompleted = false;
    public final BroadcastReceiver mBootCompleteReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                phoneWindowManagerExt.mBootCompleted = true;
                if (CoreRune.FW_SPEN) {
                    int penState = phoneWindowManagerExt.mWindowManagerFuncs.getPenState();
                    boolean z = penState == 1 || penState == 2;
                    if (penState == PhoneWindowManagerExt.this.mPenState || PhoneWindowManagerExt.this.mPenType == 5) {
                        return;
                    }
                    PhoneWindowManagerExt.this.notifyPenSwitchChanged(0L, z, penState == 2);
                }
            }
        }
    };
    public Toast mToast = null;
    public final BroadcastReceiver mPackageChangeReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            if (!"android.intent.action.PACKAGE_REMOVED".equals(action) || booleanExtra) {
                return;
            }
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.hasOwnerPackage(schemeSpecificPart)) {
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.setPackage(schemeSpecificPart);
                if (!PhoneWindowManagerExt.this.isActivitiesAvailable(intent2)) {
                    Log.d("PhoneWindowManagerExt", "The package(" + schemeSpecificPart + ") has been removed. clearKeyCustomizationInfo.");
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.clearKeyCustomizationInfoByPackage(schemeSpecificPart);
                }
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.removeHotKey(schemeSpecificPart);
        }
    };
    public SemDvfsManager mSemRotationBooster = null;
    public SemDvfsManager mSemFoldingBooster = null;
    public SemDvfsManager mSemWakeUpBooster = null;
    public final Object mBoosterLock = new Object();
    public final BroadcastReceiver mSetupWizardGlobalActionReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!"com.sec.android.app.secsetupwizard.GLOBAL_ACTION".equals(intent.getAction()) || PhoneWindowManagerExt.this.mPolicy.isUserSetupComplete()) {
                return;
            }
            Slog.v("PhoneWindowManagerExt", "secSetupwizard POWER_OFF_GLOBAL_ACTION received.");
            PhoneWindowManagerExt.this.mPolicy.showGlobalActions();
        }
    };
    public boolean mIsCallOpenDictation = false;
    public boolean mIsSamsungKeyboard = false;
    public int mQuintuplePressOnPowerBehavior = 0;
    public int mQuadruplePressOnPowerBehavior = 0;
    public int mQuickLaunchCameraBehavior = 2;
    public final Consumer mQuickLaunchCameraConsumer = new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            PhoneWindowManagerExt.this.lambda$new$9((Boolean) obj);
        }
    };
    public boolean mTvModeEnabled = false;
    public boolean mTvModeDoublePressEnabled = false;
    public ComponentName mDoublePressLaunchComponentName = null;
    public final Consumer mTvModeStateConsumer = new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            PhoneWindowManagerExt.this.lambda$new$10((Boolean) obj);
        }
    };
    public final Consumer mTvModeStateDoublePressConsumer = new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda2
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            PhoneWindowManagerExt.this.lambda$new$11((Boolean) obj);
        }
    };
    public final Consumer mDoublePressLaunchComponentConsumer = new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda3
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            PhoneWindowManagerExt.this.lambda$new$12((Boolean) obj);
        }
    };
    public final long[] mKeyUpTime = new long[2];
    public boolean mIsScreenshotTriggered = false;
    public long mScreenshotTriggeredTime = 0;
    public boolean mScreenshotEnabled = true;
    public UEventObserver mDrmEventObserver = new UEventObserver() { // from class: com.android.server.policy.PhoneWindowManagerExt.6
        public void onUEvent(UEventObserver.UEvent uEvent) {
            if (uEvent == null) {
                Log.d("PhoneWindowManagerExt", "The event of DrmEvent is null.");
                return;
            }
            String str = uEvent.get("status");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            PhoneWindowManagerExt.this.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("connected".equals(str));
        }
    };
    public UEventObserver mExtEventObserver = new UEventObserver() { // from class: com.android.server.policy.PhoneWindowManagerExt.7
        public void onUEvent(UEventObserver.UEvent uEvent) {
            if (uEvent == null) {
                Log.d("PhoneWindowManagerExt", "The event of ExtEvent is null.");
                return;
            }
            String str = uEvent.get("STATE");
            if (TextUtils.isEmpty(str)) {
                return;
            }
            PhoneWindowManagerExt.this.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("DP=1".equals(str));
        }
    };
    public boolean mAppSwitchKeyConsumed = false;
    public boolean mIsHapticsEnabled = true;
    public boolean mIsAssistHapticEnabled = false;
    public boolean mIsHapticsSupported = false;
    public final Object mServiceAcquireLock = new Object();
    public Thread mKeyEventInjectionThread = null;

    public final int getDisplayId() {
        return 0;
    }

    public boolean showCoverToast(Intent intent, Intent intent2) {
        return false;
    }

    public PhoneWindowManagerExt(Context context, WindowManagerPolicy windowManagerPolicy, WindowManagerPolicyExt.WindowManagerFuncs windowManagerFuncs) {
        this.mPenType = CoreRune.FW_SPEN ? CoreRune.FW_SPEN_USP_LEVEL % 10 : -1;
        this.mPenState = -1;
        this.mPenSoundEnabled = false;
        this.mPenSoundFilePath = null;
        this.mPenDetachNotiConsumer = new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PhoneWindowManagerExt.this.lambda$new$14((Boolean) obj);
            }
        };
        this.mIsAccessibilityShortcutVolupPower = false;
        this.mAccessibilityDirectAccessController = null;
        this.mIsInteractionControlEnabled = false;
        this.mIsPowerKeyBlocked = false;
        this.mIsVolumeKeyBlocked = false;
        this.mIsVolumeUpKeyMode = false;
        this.mIsAnyKeyMode = false;
        this.mAcceptCallHomeConsumed = false;
        this.mIsSktPhoneRelaxMode = false;
        this.mProximityChangeReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY".equals(action)) {
                    Slog.v("PhoneWindowManagerExt", "Enabling listeners by proximity");
                    PhoneWindowManagerExt.this.mPolicy.mDefaultDisplayRotation.getOrientationListener().enable(true);
                } else if ("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY".equals(action)) {
                    Slog.v("PhoneWindowManagerExt", "Disabling listeners by proximity");
                    PhoneWindowManagerExt.this.mPolicy.mDefaultDisplayRotation.getOrientationListener().disable();
                }
            }
        };
        this.mIsDoubleTapToWakeUp = false;
        this.mIsDoubleTapToWakeUpSupported = false;
        this.mIsRequestedTentMode = false;
        this.STARTED_WAKE = 1;
        this.FINISHED_WAKE = 2;
        this.STARTED_SLEEP = 4;
        this.FINISHED_SLEEP = 8;
        this.mReserveBatteryMode = false;
        this.mEnableReserveBatteryMode = false;
        this.mWakeAndUnlockTriggered = false;
        this.mWakeAndUnlockRunning = new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$new$18();
            }
        };
        this.mWakeAndUnfoldedTriggered = false;
        this.mWakeAndUnfoldedRunning = new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$new$19();
            }
        };
        this.mMultiFingerGestureListener = new InputManager.SemOnMultiFingerGestureListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.9
            public void onMultiFingerGesture(int i, int i2) {
                if (PhoneWindowManagerExt.this.isInDexMode()) {
                    Slog.d("PhoneWindowManagerExt", "Skip multi finger gesture in DeX mode.");
                } else {
                    PhoneWindowManagerExt.this.handleMultiFingerTap(i);
                }
            }
        };
        this.mIsPogoKeyboardConnected = false;
        this.mIsPalmTouchDownToSleep = false;
        this.mPalmTouchReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.samsung.android.motion.PALM_SCREEN_OFF".equals(intent.getAction())) {
                    if (KeyCustomizationConstants.isSafeDebugInput()) {
                        Slog.d("PhoneWindowManagerExt", "mIsPalmTouchDownToSleep=" + PhoneWindowManagerExt.this.mIsPalmTouchDownToSleep);
                    }
                    if (PhoneWindowManagerExt.this.mIsPalmTouchDownToSleep) {
                        Slog.d("PhoneWindowManagerExt", "Going to sleep by palm touch down");
                        PhoneWindowManagerExt.this.mPolicy.sleepDefaultDisplay(SystemClock.uptimeMillis(), 24, 0);
                    }
                }
            }
        };
        this.mStopLockTaskModePinnedChordLongPress = new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.lambda$new$20();
            }
        };
        this.mLockTaskModeState = 0;
        this.mLockTaskFeatures = new SparseIntArray();
        this.mVolumeKeyLongPress = false;
        this.mIsPremiumWatchOn = false;
        this.mIsDoubleTapPremiumWatchOn = false;
        this.mMultiuserReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.onUserRemove(intent.getIntExtra("android.intent.extra.user_handle", 0));
                }
            }
        };
        this.mFoldOpenCount = 0;
        this.mSubBootMsgDialog = null;
        this.mFoldSaLock = new Object();
        this.mFoldSaLoggingReceiver = new BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManagerExt.15
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                CoreSaLogger.logForBasic("W003", "No Action");
            }
        };
        this.mButtonShapeEnabled = -1;
        this.mBoldFontEnabled = -1;
        this.mCursorThicknessScale = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mProKioskManager = null;
        this.mContext = context;
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) windowManagerPolicy;
        this.mPolicy = phoneWindowManager;
        this.mWindowManagerFuncs = windowManagerFuncs;
        phoneWindowManager.setSamsungPolicy(this);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void init() {
        this.mHandler = new PolicyExtHandler();
        SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSettingsObserver = settingsObserver;
        settingsObserver.observe();
        initSettingsValue();
        registerReceiver();
        initIntent();
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$init$0();
            }
        });
        this.mSystemKeyPolicy = new SystemKeyManager(this.mPolicy);
        this.mBixbyService = new BixbyService(this, this.mContext);
        if (CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL) {
            this.mPolicy.mTriplePressOnPowerBehavior = 102;
        }
        Vibrator defaultVibrator = ((VibratorManager) this.mContext.getSystemService("vibrator_manager")).getDefaultVibrator();
        this.mVibrator = defaultVibrator;
        boolean z = false;
        this.mIsHapticsSupported = defaultVibrator.semGetSupportedVibrationType() > 1;
        registerDisplayListener();
        this.mKeyboardShortcutPolicy = new KeyboardShortcutManager(this.mContext, this);
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$init$1();
            }
        });
        SensorPrivacyManager sensorPrivacyManager = SensorPrivacyManager.getInstance(this.mContext);
        this.mIsCameraSensorToggleSupported = sensorPrivacyManager.supportsSensorToggle(2) && DeviceConfig.getBoolean("privacy", "camera_toggle_enabled", true);
        if (sensorPrivacyManager.supportsSensorToggle(1) && DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true)) {
            z = true;
        }
        this.mIsMicSensorToggleSupported = z;
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mProKioskManager = ProKioskManager.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.mKeyCustomizationPolicy.init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        this.mKeyboardShortcutPolicy.init();
    }

    public class PolicyExtHandler extends Handler {
        public PolicyExtHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                PhoneWindowManagerExt.this.mIsVolumeUpKeyPressed = false;
                return;
            }
            if (i == 2) {
                PhoneWindowManagerExt.this.mWindowManagerFuncs.reconfigureDisplay(((Integer) message.obj).intValue());
                return;
            }
            if (i != 5) {
                return;
            }
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("PhoneWindowManagerExt", "Get MSG_BLOCK_WAKEUP_TIMEOUT, isFolded=" + PhoneWindowManagerExt.this.isFolded() + " mFoldOpenCount=" + PhoneWindowManagerExt.this.mFoldOpenCount);
            }
            if (!PhoneWindowManagerExt.this.isFolded() && PhoneWindowManagerExt.this.mFoldOpenCount > 3) {
                PhoneWindowManagerExt.this.mPolicy.wakeUp(SystemClock.uptimeMillis(), false, 9, "android.policy:LID");
            }
            PhoneWindowManagerExt.this.mFoldOpenCount = 0;
        }
    }

    public class SettingsObserver extends ContentObserver {
        public final LinkedHashMap mSettingsUriToCallback;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mSettingsUriToCallback = new LinkedHashMap();
        }

        public void observe() {
            final ContentResolver contentResolver = PhoneWindowManagerExt.this.mContext.getContentResolver();
            if (CoreRune.FW_SUPPORT_DICTATION_SAMSUNG_KEYBOARD || CoreRune.FW_XCOVER_AND_TOP_KEY) {
                registerSettings(Settings.Secure.getUriFor("default_input_method"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$0(contentResolver, (Boolean) obj);
                    }
                });
            }
            if (CoreRune.FW_QUICK_LAUNCH_CAMERA) {
                registerSettings(Settings.System.getUriFor("double_tab_launch"), PhoneWindowManagerExt.this.mQuickLaunchCameraConsumer);
            }
            if (CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE) {
                registerSettings(Settings.System.getUriFor("tvmode_state"), PhoneWindowManagerExt.this.mTvModeStateConsumer);
                registerSettings(Settings.System.getUriFor("pwrkey_owner_status"), PhoneWindowManagerExt.this.mTvModeStateDoublePressConsumer);
                registerSettings(Settings.System.getUriFor("double_tab_launch_component"), PhoneWindowManagerExt.this.mDoublePressLaunchComponentConsumer);
            }
            registerSettings(Settings.System.getUriFor("haptic_feedback_enabled"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$1(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("default_assist_vibration_feedback"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$2(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.Global.getUriFor("navigation_bar_button_to_hide_keyboard"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$3(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.Secure.getUriFor("show_keyboard_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda24
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$4(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.Global.getUriFor("navigation_bar_gesture_hint"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda25
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$5(contentResolver, (Boolean) obj);
                }
            });
            if (CoreRune.FW_SPEN) {
                registerSettings(Settings.System.getUriFor("pen_attach_detach_vibration"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda26
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$6(contentResolver, (Boolean) obj);
                    }
                });
                registerSettings(Settings.System.getUriFor("spen_feedback_sound"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda27
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$7(contentResolver, (Boolean) obj);
                    }
                });
                registerSettings(Settings.System.getUriFor("pen_detachment_notification"), PhoneWindowManagerExt.this.mPenDetachNotiConsumer);
            }
            if (CoreRune.FW_SPEN_SCREEN_OFF_MEMO) {
                registerSettings(Settings.System.getUriFor("screen_off_memo"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda28
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$8(contentResolver, (Boolean) obj);
                    }
                });
            }
            registerSettings(Settings.System.getUriFor("access_control_enabled"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda29
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$9(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("access_control_power_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$10(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("access_control_volume_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$11(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("volumekey_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$12(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("anykey_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$13(contentResolver, (Boolean) obj);
                }
            });
            if (CoreRune.FW_SKT_PHONE_RELAX_MODE) {
                registerSettings(Settings.System.getUriFor("skt_phone20_relax_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$14(contentResolver, (Boolean) obj);
                    }
                });
            }
            registerSettings(Settings.System.getUriFor("double_tab_to_wake_up"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$15(contentResolver, (Boolean) obj);
                }
            });
            if (CoreRune.FW_SUPPORT_DOWNLOADABLE_RESERVE_BATTERY_MODE) {
                registerSettings(Settings.Secure.getUriFor("reserve_battery_on"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$16(contentResolver, (Boolean) obj);
                    }
                });
                registerSettings(Settings.Secure.getUriFor("enable_reserve_max_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$17(contentResolver, (Boolean) obj);
                    }
                });
            } else if (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE) {
                registerSettings(Settings.System.getUriFor("reserve_battery_on"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$18(contentResolver, (Boolean) obj);
                    }
                });
                registerSettings(Settings.System.getUriFor("enable_reserve_max_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$19(contentResolver, (Boolean) obj);
                    }
                });
            }
            registerSettings(Settings.Secure.getUriFor("development_custom_bugreport_writer"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$22(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("issuetracker_logged_in"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$23(contentResolver, (Boolean) obj);
                }
            });
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY) {
                registerSettings(Settings.System.getUriFor("active_key_on_lockscreen"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda14
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$24(contentResolver, (Boolean) obj);
                    }
                });
            }
            if (CoreRune.FW_XCOVER_AND_TOP_KEY) {
                registerSettings(Settings.System.getUriFor("xcover_top_key_on_lockscreen"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda15
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$25(contentResolver, (Boolean) obj);
                    }
                });
            }
            if (CoreRune.FW_CHN_PREMIUM_WATCH) {
                registerSettings(Settings.System.getUriFor("premium_watch_switch_onoff"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$26(contentResolver, (Boolean) obj);
                    }
                });
                registerSettings(Settings.System.getUriFor("premium_tap_for_watch_face_switch_on_off"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$27(contentResolver, (Boolean) obj);
                    }
                });
            }
            if (CoreRune.FW_KEY_SA_LOGGING) {
                registerSettings(Settings.Secure.getUriFor("assistant"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$28(contentResolver, (Boolean) obj);
                    }
                });
            }
            registerSettings(Settings.System.getUriFor("cursor_thickness"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$29(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.System.getUriFor("show_button_background"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$30(contentResolver, (Boolean) obj);
                }
            });
            registerSettings(Settings.Global.getUriFor("bold_text"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda21
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PhoneWindowManagerExt.SettingsObserver.this.lambda$observe$31(contentResolver, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$0(ContentResolver contentResolver, Boolean bool) {
            String string = Settings.Secure.getString(contentResolver, "default_input_method");
            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(string);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$1(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$2(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$3(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$4(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$5(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mNavGetureHintEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_gesture_hint", 0) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$6(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$7(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$8(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$9(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$10(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$11(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$12(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$13(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$14(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$15(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$16(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mReserveBatteryMode = Settings.Secure.getInt(contentResolver, "reserve_battery_on", 0) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$17(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mEnableReserveBatteryMode = Settings.Secure.getInt(contentResolver, "enable_reserve_max_mode", 0) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$18(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mReserveBatteryMode = Settings.System.getInt(contentResolver, "reserve_battery_on", 0) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$19(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mEnableReserveBatteryMode = Settings.System.getInt(contentResolver, "enable_reserve_max_mode", 0) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$22(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver, "development_custom_bugreport_writer", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$23(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$24(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$25(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$26(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_watch_switch_onoff", 0) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$27(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$28(ContentResolver contentResolver, Boolean bool) {
            PhoneWindowManagerExt.this.mAssistantAppName = Settings.Secure.getString(contentResolver, "assistant");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$29(ContentResolver contentResolver, Boolean bool) {
            float floatForUser = Settings.System.getFloatForUser(contentResolver, "cursor_thickness", 1.0f, -2);
            if (floatForUser != PhoneWindowManagerExt.this.mCursorThicknessScale) {
                PhoneWindowManagerExt.this.mCursorThicknessScale = floatForUser;
                PhoneWindowManagerExt.this.mHandler.removeMessages(2);
                Handler handler = PhoneWindowManagerExt.this.mHandler;
                handler.sendMessage(handler.obtainMessage(2, 0));
                if (PhoneWindowManagerExt.this.isInDexMode()) {
                    Handler handler2 = PhoneWindowManagerExt.this.mHandler;
                    handler2.sendMessage(handler2.obtainMessage(2, 2));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$30(ContentResolver contentResolver, Boolean bool) {
            int i = Settings.System.getInt(contentResolver, "show_button_background", 0);
            if (i != PhoneWindowManagerExt.this.mButtonShapeEnabled) {
                PhoneWindowManagerExt.this.mButtonShapeEnabled = i;
                PhoneWindowManagerExt.this.mHandler.removeMessages(2);
                Handler handler = PhoneWindowManagerExt.this.mHandler;
                handler.sendMessage(handler.obtainMessage(2, 0));
                if (PhoneWindowManagerExt.this.isInDexMode()) {
                    Handler handler2 = PhoneWindowManagerExt.this.mHandler;
                    handler2.sendMessage(handler2.obtainMessage(2, 2));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$observe$31(ContentResolver contentResolver, Boolean bool) {
            int i = Settings.Global.getInt(contentResolver, "bold_text", 0);
            if (i != PhoneWindowManagerExt.this.mBoldFontEnabled) {
                PhoneWindowManagerExt.this.mBoldFontEnabled = i;
                PhoneWindowManagerExt.this.mHandler.removeMessages(2);
                Handler handler = PhoneWindowManagerExt.this.mHandler;
                handler.sendMessage(handler.obtainMessage(2, 0));
                if (PhoneWindowManagerExt.this.isInDexMode()) {
                    Handler handler2 = PhoneWindowManagerExt.this.mHandler;
                    handler2.sendMessage(handler2.obtainMessage(2, 2));
                }
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            synchronized (PhoneWindowManagerExt.this.mLock) {
                ((Consumer) this.mSettingsUriToCallback.get(uri)).accept(Boolean.FALSE);
                if (PhoneWindowManager.DEBUG_INPUT) {
                    Slog.d("PhoneWindowManagerExt", "updateSetting=" + uri);
                }
            }
        }

        public void registerSettings(Uri uri, Consumer consumer) {
            PhoneWindowManagerExt.this.mContext.getContentResolver().registerContentObserver(uri, false, this, -1);
            this.mSettingsUriToCallback.put(uri, consumer);
        }

        public void updateSettings() {
            updateSettings(false);
        }

        public void updateSettings(boolean z) {
            synchronized (PhoneWindowManagerExt.this.mLock) {
                Iterator it = this.mSettingsUriToCallback.entrySet().iterator();
                while (it.hasNext()) {
                    ((Consumer) ((Map.Entry) it.next()).getValue()).accept(Boolean.valueOf(z));
                }
            }
        }
    }

    public void onUserSwitch(final int i) {
        this.mWindowManagerFuncs.resetScreenshotConnections();
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$onUserSwitch$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitch$2(int i) {
        this.mKeyCustomizationPolicy.onUserSwitch(i);
    }

    public final void initSettingsValue() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE) {
            this.mTvModeEnabled = Settings.System.getInt(contentResolver, "tvmode_state", 0) == 1;
            this.mTvModeDoublePressEnabled = Settings.System.getInt(contentResolver, "pwrkey_owner_status", 0) == 1;
            updateDoublePressLaunchInfo(Settings.System.getString(contentResolver, "double_tab_launch_component"));
        }
    }

    public final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mBootCompleteReceiver, UserHandle.ALL, intentFilter, null, null);
        if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.sec.android.app.secsetupwizard.GLOBAL_ACTION");
            this.mContext.registerReceiverAsUser(this.mSetupWizardGlobalActionReceiver, UserHandle.ALL, intentFilter2, null, null);
        }
        if (CoreRune.SYSUI_GRADLE_BUILD) {
            registerSystemUIReceiver();
        }
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY");
        intentFilter3.addAction("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY");
        this.mContext.registerReceiverAsUser(this.mProximityChangeReceiver, UserHandle.ALL, intentFilter3, null, null);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter4.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageChangeReceiver, UserHandle.ALL, intentFilter4, null, null);
        this.mContext.registerReceiver(this.mMultiuserReceiver, new IntentFilter("android.intent.action.USER_REMOVED"));
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            this.mContext.registerReceiver(this.mFoldSaLoggingReceiver, new IntentFilter("com.samsung.android.intent.action.WINNER_LOGGING"));
        }
    }

    public final void initIntent() {
        if (CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL || CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
            this.mIntentEmergencySos = getEmergencySosIntent();
        }
        if (CoreRune.FW_SPEN) {
            Intent intent = new Intent("com.samsung.pen.INSERT");
            this.mPenInsertIntent = intent;
            intent.addFlags(16777216);
            if (CoreRune.FW_SPEN_SCREEN_OFF_MEMO) {
                Intent intent2 = new Intent();
                this.mScreenOffMemoIntent = intent2;
                intent2.setComponent(ComponentName.unflattenFromString("com.samsung.android.app.notes/com.samsung.android.app.notes.screenoffmemo.ScreenOffMemoService"));
            }
        }
    }

    public boolean isFolded() {
        return this.mPolicy.mWindowManagerFuncs.isFolded();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isScreenTurnedOn() {
        return this.mPolicy.mKeyguardDelegate.isScreenTurnedOn();
    }

    public void systemBooted() {
        InputManager inputManager = InputManager.getInstance();
        if (inputManager != null) {
            this.mIsDoubleTapToWakeUpSupported = (inputManager.semCheckInputFeature() & 1) == 1;
        }
    }

    public void startedWakingUp(int i) {
        TspStateController tspStateController;
        this.mWakingUpReason = i;
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            if (i == 111) {
                sendFoldSaLoggingCanceledIfNeeded("Fingerprint Sensor", false);
            } else if (i == 7) {
                sendFoldSaLoggingCanceledIfNeeded("Lift To Wake", false);
            }
        }
        if (!CoreRune.FW_TSP_STATE_CONTROLLER || (tspStateController = this.mTspStateController) == null) {
            return;
        }
        tspStateController.startedWakingUp();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public int getWakingUpReason() {
        return this.mWakingUpReason;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0242 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
        String string;
        SpenGestureManager spenGestureManager;
        String string2;
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int displayId = keyEvent.getDisplayId();
        boolean z = keyEvent.getAction() == 0;
        boolean z2 = (i & 536870912) != 0;
        if (PhoneWindowManager.DEBUG_INPUT || CoreRune.SAFE_DEBUG || KEYCODE_DEBUG_LOG_ALLOWLIST.contains(Integer.valueOf(keyCode))) {
            Log.d("PhoneWindowManagerExt", "interceptKeyTq code=" + keyCode + " down=" + z + " repeatCount=" + repeatCount + " displayId=" + displayId + " interactive=" + z2);
        }
        boolean z3 = CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY && this.mPolicy.mKeyCombinationManager.isPowerKeyIntercepted() && this.mPolicy.mKeyCombinationManager.isKeyPressed(26);
        if (keyCode != 3) {
            if (keyCode == 4) {
                return ((keyEvent.getFlags() & 268435456) == 0 && hasSingleKeyRule(keyCode)) ? 8 : 4;
            }
            if (keyCode == 6) {
                if (!canDispatchingSystemKeyEvent(keyCode)) {
                    return 8;
                }
                Log.d("PhoneWindowManagerExt", "systemKeyEventRequested ADD_PASS");
                return 4;
            }
            if (keyCode != 187) {
                if (keyCode != 1015) {
                    if (keyCode == 1049) {
                        if (CoreRune.FW_CHN_PREMIUM_WATCH && z) {
                            doubleTapLaunchPremiumWatch();
                        }
                        return 2;
                    }
                    if (keyCode == 1075 || keyCode == 1076) {
                        if (z) {
                            Resources resources = this.mContext.getResources();
                            if (keyCode == 1075) {
                                string = resources.getString(17043037);
                            } else {
                                string = resources.getString(17043036);
                            }
                            showToast(this.mContext, string);
                        }
                        return 2;
                    }
                    if (keyCode == 1078) {
                        if (!CoreRune.FW_SPEN || isInDexMode()) {
                            return 2;
                        }
                        if (z) {
                            Log.d("PhoneWindowManagerExt", "Press KEYCODE_DOUBLE_TAP");
                            if (CoreRune.FW_SPEN_SCREEN_OFF_MEMO && this.mScreenOffMemoEnabled) {
                                this.mScreenOffMemoIntent.putExtra("pen_intent_com", "pen_doubletab");
                                this.mContext.startServiceAsUser(this.mScreenOffMemoIntent, UserHandle.CURRENT);
                                if (CoreRune.FW_SPEN_SMART_CLIP && (spenGestureManager = this.mSpenGestureManager) != null) {
                                    spenGestureManager.setScreenOffDoubleTabTime();
                                }
                            }
                        }
                        return 4;
                    }
                    if (keyCode != 1079) {
                        if (keyCode == 1100 || keyCode == 1101) {
                            if (z) {
                                Resources resources2 = this.mContext.getResources();
                                if (keyCode == 1100) {
                                    string2 = resources2.getString(R.string.lockscreen_transport_play_description);
                                } else {
                                    string2 = resources2.getString(R.string.lockscreen_transport_prev_description);
                                }
                                showToast(this.mContext, string2);
                            }
                            return 2;
                        }
                        switch (keyCode) {
                            case 24:
                                if (repeatCount == 0) {
                                    this.mIsVolumeUpKeyPressed = z;
                                    this.mHandler.removeMessages(1);
                                    if (z) {
                                        Handler handler = this.mHandler;
                                        handler.sendMessageDelayed(handler.obtainMessage(1), 3000L);
                                    }
                                }
                                if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY && z3 && z && repeatCount == 0 && this.mPolicy.mLongPressOnPowerBehavior == 101) {
                                    this.mBixbyService.startService(new BixbyService.Params.Builder(keyEvent, z2).setPowerCombination(z3).build());
                                }
                                return (z && knoxCustomVolumeKeyAppSwitching(true)) ? 2 : 1;
                            case 25:
                                if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY && z3 && z && repeatCount == 0 && this.mPolicy.mLongPressOnPowerBehavior == 101) {
                                    this.mBixbyService.startService(new BixbyService.Params.Builder(keyEvent, z2).setPowerCombination(z3).build());
                                }
                                if (z && knoxCustomVolumeKeyAppSwitching(false)) {
                                    return 2;
                                }
                                break;
                            case 26:
                                if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY && repeatCount == 0 && this.mPolicy.mLongPressOnPowerBehavior == 101) {
                                    if (!z && this.mIsCallOpenDictation) {
                                        this.mIsCallOpenDictation = false;
                                        closeDictation(keyCode);
                                    } else if (!z || !isSamsungKeyboardShown()) {
                                        this.mBixbyService.startService(new BixbyService.Params.Builder(keyEvent, z2).setPowerCombination(z3).build());
                                    }
                                }
                                if (canDispatchingSystemKeyEvent(keyCode)) {
                                    Log.d("PhoneWindowManagerExt", "systemKeyEventRequested ADD_PASS");
                                    return 4;
                                }
                                Log.d("PhoneWindowManagerExt", "systemKeyEventRequested REMOVE_PASS");
                                return 8;
                        }
                    }
                }
                return hasSingleKeyRule(keyCode) ? 8 : 4;
            }
        } else if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && this.mPolicy.mSingleKeyGestureDetector.hasRule(keyCode)) {
            if (z && repeatCount == 0) {
                startSearcleHomeKeyPress(true);
            } else if (!z) {
                startSearcleHomeKeyPress(false);
            }
        }
        if (canDispatchingSystemKeyEvent(keyCode)) {
            Log.d("PhoneWindowManagerExt", "systemKeyEventRequested ADD_PASS");
            return 4;
        }
        if (hasSingleKeyRule(keyCode)) {
            return 8;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0313  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long interceptKeyBeforeDispatching(android.os.IBinder r22, android.view.KeyEvent r23, int r24) {
        /*
            Method dump skipped, instructions count: 1706
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.interceptKeyBeforeDispatching(android.os.IBinder, android.view.KeyEvent, int):long");
    }

    public static /* synthetic */ void lambda$interceptKeyBeforeDispatching$5(int i) {
        MultiWindowManager.getInstance().minimizeAllTasks(i);
    }

    static {
        ArraySet arraySet = new ArraySet();
        KEYCODE_DEBUG_LOG_ALLOWLIST = arraySet;
        arraySet.add(26);
        arraySet.add(3);
        Integer valueOf = Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
        arraySet.add(valueOf);
        arraySet.add(4);
        arraySet.add(24);
        arraySet.add(25);
        arraySet.add(1015);
        arraySet.add(1079);
        arraySet.add(1078);
        arraySet.add(85);
        arraySet.add(79);
        arraySet.add(120);
        sdhmsBinder = null;
        ArraySet arraySet2 = new ArraySet();
        KEYCODE_KEY_COMBINATION_ALLOWLIST = arraySet2;
        arraySet2.add(26);
        arraySet2.add(3);
        arraySet2.add(valueOf);
        arraySet2.add(4);
        arraySet2.add(24);
        arraySet2.add(25);
        arraySet2.add(20);
        arraySet2.add(23);
    }

    public int isWakeKey(KeyEvent keyEvent, boolean z) {
        int keyCode = keyEvent.getKeyCode();
        boolean z2 = keyEvent.getAction() == 0;
        int repeatCount = keyEvent.getRepeatCount();
        if (keyCode != 3) {
            if (keyCode == 26) {
                int hasRequestedActionBlockKeyEvent = hasRequestedActionBlockKeyEvent(26, z2, repeatCount);
                if (hasRequestedActionBlockKeyEvent != -1) {
                    return hasRequestedActionBlockKeyEvent;
                }
            } else if (keyCode == 187 && this.mLockTaskModeState == 1 && !isRecentsAllowed(ActivityManager.getCurrentUser())) {
                Log.d("PhoneWindowManagerExt", "Recent Key was blocked by LOCK_TASK_MODE_LOCKED");
                return 2;
            }
        } else {
            if (this.mLockTaskModeState == 1 && !isHomeAllowed(ActivityManager.getCurrentUser())) {
                Log.d("PhoneWindowManagerExt", "Home Key was blocked by LOCK_TASK_MODE_LOCKED");
                return 2;
            }
            if (z && z2 && this.mPolicy.mPowerManagerInternal.isProximityPositive()) {
                return 18;
            }
        }
        return 1;
    }

    public int checkPolicyBeforeInterceptKey(KeyEvent keyEvent, boolean z, boolean z2) {
        ProKioskManager proKioskManager;
        int keyCode = keyEvent.getKeyCode();
        boolean z3 = keyEvent.getAction() == 0;
        int repeatCount = keyEvent.getRepeatCount();
        if (z3) {
            this.mPolicy.mSystemKeyRequested = false;
        }
        if (isInteractionControlEnabled(keyCode, z)) {
            Log.d("PhoneWindowManagerExt", "interceptKeyTq : Key was blocked by interaction control");
            return 2;
        }
        if (z2) {
            return 1;
        }
        if (hasSystemKeyInfoWithKeyPressOld(keyCode)) {
            if (keyCode != 26) {
                return 1;
            }
            if (z3) {
                this.mPolicy.mSystemKeyRequested = true;
            }
            return -1;
        }
        if (!this.mPolicy.isUserSetupComplete() && keyCode >= 131 && keyCode <= 142) {
            Log.d("PhoneWindowManagerExt", "Ignoring function key - device is not setup. (" + keyCode + ")");
            return 2;
        }
        if ((keyCode != 25 && keyCode != 24) || ((repeatCount == 0 && z3) || (proKioskManager = this.mProKioskManager) == null || !proKioskManager.getProKioskState() || !this.mProKioskManager.getVolumeKeyAppState())) {
            return -1;
        }
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Log.d("PhoneWindowManagerExt", "knox: volume key is blocked");
        }
        return 2;
    }

    public final long checkPolicyBeforeDispatching(KeyEvent keyEvent, int i) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo;
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case 1085:
            case 1086:
            case 1087:
            case 1089:
                sendThreeFingerGestureKeyEvent(keyEvent, i, false);
                if (this.mIsLastGesture3FingerBottom && keyCode == 1089) {
                    shortPressOnHome(i);
                }
                this.mIsLastGesture3FingerBottom = false;
                return -1L;
            case 1088:
                this.mIsLastGesture3FingerBottom = true;
                return -1L;
            default:
                if ((!keyEvent.isLongPress() && keyEvent.getRepeatCount() <= 1) || (lastKeyCustomizationInfo = this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(4, keyCode)) == null || lastKeyCustomizationInfo.action != 4) {
                    return 0L;
                }
                if (KeyCustomizationConstants.isDebugInput()) {
                    Log.d("PhoneWindowManagerExt", "dispatchKeyEvent:long press, NO_DISPATCHING");
                }
                return -1L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a1  */
    @Override // com.android.server.policy.WindowManagerPolicyExt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean interceptQuickAccess(int i, float f, float f2) {
        Intent intent;
        Intent intent2;
        if (i == 4) {
            Log.d("PhoneWindowManagerExt", "interceptQuickAccess, quickpay");
            intent = new Intent("com.samsung.android.spay.quickpay");
            intent.addFlags(32);
            intent.putExtra("displayId", getDisplayId());
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.spay.permission.SIMPLE_PAY");
        } else {
            if (i != 225 && i != 226) {
                switch (i) {
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        Log.d("PhoneWindowManagerExt", "interceptQuickAccess, CHANGE_AOD_MODE");
                        intent = new Intent("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
                        intent.putExtra("info", i);
                        intent.putExtra("location", new float[]{f, f2});
                        intent.addFlags(32);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER");
                        if (CoreRune.FW_FOLD_SA_LOGGING && i == 8) {
                            sendFoldSaLoggingCanceledIfNeeded("Double Tab", false);
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                            case 15:
                            case 16:
                            case 17:
                                break;
                            default:
                                intent2 = null;
                                break;
                        }
                }
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Log.d("PhoneWindowManagerExt", "interceptQuickAccess: info=" + i + " x=" + f + " y=" + f2 + ", intent=" + intent2);
                }
                return false;
            }
            Log.d("PhoneWindowManagerExt", "interceptQuickAccess, FINGERPRINT_ON_DISPLAY");
            intent = new Intent("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
            intent.putExtra("info", i);
            intent.putExtra("location", new float[]{f, f2});
            intent.addFlags(268435488);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.permission.BROADCAST_QUICKACCESS");
        }
        intent2 = intent;
        if (KeyCustomizationConstants.isSafeDebugInput()) {
        }
        return false;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void updateTopActivity(ComponentName componentName) {
        this.mTopActivity = componentName;
    }

    public ComponentName getTopActivity() {
        return this.mTopActivity;
    }

    public boolean powerLongPress(int i, KeyEvent keyEvent, int i2) {
        if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY && !this.mPolicy.isUserSetupComplete()) {
            this.mContext.startActivityAsUser(new Intent("com.sec.android.app.secsetupwizard.POWER_OFF_GUIDE"), UserHandle.CURRENT);
            Slog.d("PhoneWindowManagerExt", "startActivity, power off guide");
            return true;
        }
        if (hasSystemKeyInfo(26, 4)) {
            Log.i("PhoneWindowManagerExt", "skip long press power, requestedSystemKey");
            return true;
        }
        boolean z = (i2 & 536870912) != 0;
        if (i != 1) {
            if (i != 101) {
                if (i == 102) {
                    SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(4, 26);
                    if (lastKeyCustomizationInfo == null) {
                        Log.d("PhoneWindowManagerExt", "powerLongPress, info is null");
                        return false;
                    }
                    this.mKeyCustomizationPolicy.launchLongPressAction(lastKeyCustomizationInfo.action, keyEvent, 26);
                } else {
                    Slog.e("PhoneWindowManagerExt", "Invalid side key long press info");
                    return false;
                }
            } else if (CoreRune.FW_WAKE_UP_BIXBY_SIDE_KEY) {
                if (isSamsungKeyboardShown()) {
                    this.mIsCallOpenDictation = true;
                    openDictation(26);
                } else {
                    this.mBixbyService.startService(new BixbyService.Params.Builder(keyEvent, z).setLongPress().showToast().build());
                    if (CoreRune.FW_KEY_SA_LOGGING) {
                        sendCoreSaLoggingDimension("HWB1006", "Wake Bixby");
                    }
                }
            } else if (KeyCustomizationConstants.isSafeDebugInput()) {
                Log.d("PhoneWindowManagerExt", "Bixby feature is not supported.");
            }
        } else if (z) {
            Slog.d("PhoneWindowManagerExt", "Side key long press global action");
            return false;
        }
        Slog.d("PhoneWindowManagerExt", "consume powerLongPress");
        return true;
    }

    public void updateLongPressPowerBehavior() {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(4, 26);
        if (lastKeyCustomizationInfo == null) {
            if (this.mKeyCustomizationPolicy.hasB2BDedicatedPower()) {
                Slog.d("PhoneWindowManagerExt", "updated long press power behavior by b2b dedicated app");
                Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 0);
                return;
            } else {
                Slog.d("PhoneWindowManagerExt", "Side key long press info was wrong.");
                Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 1);
                return;
            }
        }
        Intent intent = lastKeyCustomizationInfo.intent;
        if (intent == null) {
            Slog.d("PhoneWindowManagerExt", "Side key long press intent info was wrong.");
            Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 102);
            return;
        }
        ComponentName component = intent.getComponent();
        String flattenToString = component != null ? component.flattenToString() : null;
        if ("wakeBixby/wakeBixby".equals(flattenToString)) {
            Slog.d("PhoneWindowManagerExt", "updated long press power behavior as wake bixby");
            Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 101);
        } else if ("globalAction/globalAction".equals(flattenToString)) {
            Slog.d("PhoneWindowManagerExt", "updated long press power behavior as global action");
            Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 1);
        } else {
            Slog.d("PhoneWindowManagerExt", "updated long press power behavior as keyCustomizationInfo");
            Settings.Global.putInt(this.mContext.getContentResolver(), "power_button_long_press", 102);
        }
    }

    public boolean interceptUnhandledKey(KeyEvent keyEvent) {
        InputDevice device;
        return keyEvent.getKeyCode() == 110 && !(keyEvent.getAction() == 0) && (device = keyEvent.getDevice()) != null && startGameToolsService(device.getVendorId(), device.getProductId(), false);
    }

    public void startDockOrHome() {
        Slog.d("PhoneWindowManagerExt", "startDockOrHome, Callers=" + Debug.getCallers(10));
        performHomeBroadcast();
    }

    public void showToast(final Context context, final String str) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$showToast$6(context, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$6(Context context, String str) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(context, str, 0);
        this.mToast = makeText;
        makeText.show();
    }

    public boolean showToastIfNeeded(Intent intent) {
        if (intent == null) {
            return false;
        }
        String reserveBatteryModeToast = (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE && isReserveBatteryMode()) ? getReserveBatteryModeToast(intent) : null;
        if (TextUtils.isEmpty(reserveBatteryModeToast)) {
            return false;
        }
        showToast(this.mContext, reserveBatteryModeToast);
        return true;
    }

    public final String getToastString(ApplicationInfo applicationInfo, int i) {
        if (applicationInfo == null) {
            return null;
        }
        return String.format(this.mContext.getString(i), getApplicationLabel(applicationInfo));
    }

    public final String getApplicationLabel(ApplicationInfo applicationInfo) {
        return this.mContext.getPackageManager().getApplicationLabel(applicationInfo).toString();
    }

    public final ApplicationInfo getApplicationInfo(Intent intent) {
        PackageManager packageManager = this.mContext.getPackageManager();
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveActivity != null && resolveActivity.activityInfo != null) {
            return null;
        }
        Log.d("PhoneWindowManagerExt", "Can not launch app because app is not added in reserve battery mode");
        String str = intent.getPackage();
        if (TextUtils.isEmpty(str) && intent.getComponent() != null) {
            str = intent.getComponent().getPackageName();
        }
        try {
            return packageManager.getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L));
        } catch (Exception e) {
            Log.d("PhoneWindowManagerExt", "Can not get applictionInfo, " + e);
            return null;
        }
    }

    public void updateQuadruplePressPowerBehavior() {
        int lastAction = this.mKeyCustomizationPolicy.getLastAction(32, 26);
        if (lastAction == 1 || lastAction == 3 || lastAction == 2) {
            this.mQuadruplePressOnPowerBehavior = 106;
        }
    }

    public int hasRequestedActionBlockKeyEvent(int i, boolean z, int i2) {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo = this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(3, i);
        if (lastKeyCustomizationInfo == null || lastKeyCustomizationInfo.action != 4) {
            return -1;
        }
        Log.d("PhoneWindowManagerExt", "Key was blocked by KeyCustomizationPolicy. keyCode=" + i);
        if (!z || i2 != 0 || lastKeyCustomizationInfo.id != 10) {
            return 2;
        }
        Context context = this.mContext;
        showToast(context, context.getResources().getString(R.string.number_picker_increment_button));
        return 2;
    }

    public void updateSingleKeyGestureRule(int i) {
        int i2 = 0;
        for (int i3 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
            if (this.mKeyCustomizationPolicy.hasLastInfo(i3, i) && this.mKeyCustomizationPolicy.getLastAction(i3, i) != 4 && ((i3 & 4) == 0 || this.mKeyCustomizationPolicy.getLastLongPressTimeout(i3, i) != 0)) {
                i2++;
            }
        }
        if (i2 > 0) {
            addSingleKeyGestureRule(i);
        } else {
            removeSingleKeyGestureRule(i);
        }
    }

    public void addSingleKeyGestureRule(int i) {
        SingleKeyGestureDetector.SingleKeyRule homeKeyRule;
        if (this.mPolicy.mSingleKeyGestureDetector.hasRule(i)) {
            Slog.d("PhoneWindowManagerExt", "Already added rule of keyCode " + i);
            return;
        }
        if (i == 3) {
            homeKeyRule = new HomeKeyRule();
        } else if (i == 4) {
            homeKeyRule = new BackKeyRule();
        } else if (i == 24) {
            homeKeyRule = new VolumeUpKeyRule();
        } else if (i == 25) {
            homeKeyRule = new VolumeDownKeyRule();
        } else if (i == 79) {
            homeKeyRule = new HeadsetHookKeyRule();
        } else if (i == 187) {
            homeKeyRule = new RecentKeyRule();
        } else if (i == 1015) {
            homeKeyRule = new UserKeyRule();
        } else if (i == 1079) {
            homeKeyRule = new UserTopKeyRule();
        } else {
            Slog.d("PhoneWindowManagerExt", "updateSingleKeyGestureRule, keyCode was wrong. " + i);
            homeKeyRule = null;
        }
        if (homeKeyRule != null) {
            this.mPolicy.mSingleKeyGestureDetector.addRule(homeKeyRule);
        }
    }

    public void removeSingleKeyGestureRule(int i) {
        if (i != 26 && this.mPolicy.mSingleKeyGestureDetector.hasRule(i)) {
            this.mPolicy.mSingleKeyGestureDetector.removeRule(i);
        }
    }

    public final class HomeKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public HomeKeyRule() {
            super(3);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if (keyEvent.isCanceled()) {
                Log.i("PhoneWindowManagerExt", "Ignoring HOME; event canceled.");
            } else if (PhoneWindowManagerExt.this.hasSystemKeyInfo(this.mKeyCode, 3)) {
                Log.i("PhoneWindowManagerExt", "skip single press home, requestedSystemKey");
            } else {
                PhoneWindowManagerExt.this.shortPressOnHome(keyEvent.getDisplayId());
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, final KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            if (PhoneWindowManagerExt.this.hasSystemKeyInfo(this.mKeyCode, 4)) {
                Log.i("PhoneWindowManagerExt", "skip long press home, requestedSystemKey");
                return;
            }
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchLongPressAction(keyEvent)) {
                return;
            }
            if (!PhoneWindowManagerExt.this.mPolicy.keyguardOn()) {
                PhoneWindowManagerExt.this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PhoneWindowManagerExt.HomeKeyRule.this.lambda$onLongPress$0(keyEvent);
                    }
                });
                return;
            }
            Log.d("PhoneWindowManagerExt", "keyguardOn, isShowing=" + PhoneWindowManagerExt.this.mPolicy.isKeyguardShowingAndNotOccluded() + " isInputRestricted=" + PhoneWindowManagerExt.this.mPolicy.inKeyguardRestrictedKeyInputMode());
            if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG) {
                PhoneWindowManagerExt.this.showingSearcleToastIfNeeded();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLongPress$0(KeyEvent keyEvent) {
            PhoneWindowManagerExt.this.handleLongPressOnHomeWithPolicy(keyEvent.getDeviceId(), keyEvent.getEventTime());
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onMultiPress(long j, int i, KeyEvent keyEvent) {
            if (i == 2) {
                if (PhoneWindowManagerExt.this.hasSystemKeyInfo(this.mKeyCode, 8)) {
                    Log.i("PhoneWindowManagerExt", "skip double press home, requestedSystemKey");
                    return;
                }
                if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getLastAction(8, this.mKeyCode) == 4) {
                    return;
                }
                if (CoreRune.FW_QUICK_LAUNCH_CAMERA && PhoneWindowManagerExt.this.isQuickLaunchCameraEnabled(this.mKeyCode)) {
                    PhoneWindowManagerExt.this.handleDoublePressLaunchCamera(this.mKeyCode);
                    if (CoreRune.FW_KEY_SA_LOGGING) {
                        PhoneWindowManagerExt.this.sendCoreSaLoggingDimension("NAVIB1002", "Quick launch camera");
                        return;
                    }
                    return;
                }
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchMultiPressAction(keyEvent, i);
        }
    }

    public final class RecentKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public RecentKeyRule() {
            super(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            PhoneWindowManagerExt.this.mPolicy.toggleRecentApps(keyEvent.getDisplayId());
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchLongPressAction(keyEvent)) {
                return;
            }
            PhoneWindowManagerExt.this.handleLongPressOnRecent(keyEvent.getDisplayId());
        }
    }

    public final class BackKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public BackKeyRule() {
            super(4);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if ((keyEvent.getFlags() & 268435456) != 0) {
                return;
            }
            PhoneWindowManagerExt.this.injectionKeyEvent(this.mKeyCode, 268435456, -1);
        }
    }

    public final class VolumeUpKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public VolumeUpKeyRule() {
            super(24);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if ((keyEvent.getFlags() & 268435456) != 0) {
                return;
            }
            PhoneWindowManagerExt.this.injectionKeyEvent(this.mKeyCode, 268435456, -1);
        }
    }

    public final class VolumeDownKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public VolumeDownKeyRule() {
            super(25);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if ((keyEvent.getFlags() & 268435456) != 0) {
                return;
            }
            PhoneWindowManagerExt.this.injectionKeyEvent(this.mKeyCode, 268435456, -1);
        }
    }

    public final class HeadsetHookKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public HeadsetHookKeyRule() {
            super(79);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if (!PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressAction(keyEvent) && (keyEvent.getFlags() & 268435456) == 0) {
                PhoneWindowManagerExt.this.injectionKeyEvent(this.mKeyCode, 268435456, -1);
            }
        }
    }

    public final class UserKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public UserKeyRule() {
            super(1015);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && PhoneWindowManagerExt.this.mKeyCustomizationPolicy.hasXCoverTopId(3, this.mKeyCode)) {
                if (PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchXCoverPressActionIfNeeded(this.mKeyCode, true);
                    return;
                }
                return;
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressAction(keyEvent);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && PhoneWindowManagerExt.this.mKeyCustomizationPolicy.hasXCoverTopId(4, this.mKeyCode)) {
                if (PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchXCoverLongActionIfNeeded(this.mKeyCode, true);
                    return;
                }
                return;
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyDown(KeyEvent keyEvent) {
            this.mIsKeyLongPressed = false;
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getLastAction(3, this.mKeyCode) == 2) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, this.mKeyCode, this.mIsKeyLongPressed);
            }
            if (CoreRune.FW_XCOVER_AND_TOP_KEY && PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent.getKeyCode(), keyEvent);
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyUp(KeyEvent keyEvent) {
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getLastAction(3, this.mKeyCode) == 2) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, this.mKeyCode, this.mIsKeyLongPressed);
            }
            if (CoreRune.FW_XCOVER_AND_TOP_KEY && PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent.getKeyCode(), keyEvent);
            }
        }
    }

    public final class UserTopKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public UserTopKeyRule() {
            super(1079);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onPress(long j, KeyEvent keyEvent) {
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && PhoneWindowManagerExt.this.mKeyCustomizationPolicy.hasXCoverTopId(3, this.mKeyCode)) {
                if (PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchXCoverPressActionIfNeeded(this.mKeyCode, true);
                    return;
                }
                return;
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressAction(keyEvent);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY && PhoneWindowManagerExt.this.mKeyCustomizationPolicy.hasXCoverTopId(4, this.mKeyCode)) {
                if (PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                    PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchXCoverLongActionIfNeeded(this.mKeyCode, true);
                    return;
                }
                return;
            }
            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyDown(KeyEvent keyEvent) {
            this.mIsKeyLongPressed = false;
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getLastAction(3, this.mKeyCode) == 2) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, this.mKeyCode, this.mIsKeyLongPressed);
            }
            if (CoreRune.FW_XCOVER_AND_TOP_KEY && PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent.getKeyCode(), keyEvent);
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyUp(KeyEvent keyEvent) {
            if (PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getLastAction(3, this.mKeyCode) == 2) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, this.mKeyCode, this.mIsKeyLongPressed);
            }
            if (CoreRune.FW_XCOVER_AND_TOP_KEY && PhoneWindowManagerExt.this.mPolicy.mPowerManager.isInteractive()) {
                PhoneWindowManagerExt.this.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent.getKeyCode(), keyEvent);
            }
        }
    }

    public boolean hasSingleKeyRule(int i) {
        return (i == 3 || i == 4 || i == 24 || i == 25 || i == 79 || i == 187 || i == 1015 || i == 1079) && this.mPolicy.mSingleKeyGestureDetector.hasRule(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        this.mKeyCustomizationPolicy.putKeyCustomizationInfo(keyCustomizationInfo);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) {
        return this.mKeyCustomizationPolicy.getKeyCustomizationInfo(i, i2, i3);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) {
        return this.mKeyCustomizationPolicy.getKeyCustomizationInfoByPackage(str, i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) {
        return this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void removeKeyCustomizationInfo(int i, int i2, int i3) {
        this.mKeyCustomizationPolicy.removeKeyCustomizationInfo(i, i2, i3);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) {
        this.mKeyCustomizationPolicy.removeKeyCustomizationInfoByPackage(str, i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void clearKeyCustomizationInfoByKeyCode(int i, int i2) {
        this.mKeyCustomizationPolicy.clearKeyCustomizationInfoByKeyCode(i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        this.mKeyCustomizationPolicy.clearKeyCustomizationInfoByAction(i, i2, i3);
    }

    public final boolean isActivitiesAvailable(Intent intent) {
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
        } catch (NullPointerException e) {
            Log.d("PhoneWindowManagerExt", "isActivitiesAvailable : " + e);
        }
        return packageManager.queryIntentActivities(intent, 327680).size() > 0;
    }

    public class HotKey {
        public Runnable registerAppRunnable;
        public boolean isTriggered = false;
        public int keyCode = 0;
        public AlertDialog guideDialog = null;

        public HotKey() {
            this.registerAppRunnable = null;
            this.registerAppRunnable = new registerAppRunnable();
        }

        public void registerAppInfo(int i) {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Slog.d("PhoneWindowManagerExt", "registerAppHotKey isTriggered=" + this.isTriggered);
            }
            this.keyCode = i;
            this.isTriggered = true;
            cancelLaunchAppPendingAction();
            PhoneWindowManagerExt.this.mHandler.postDelayed(this.registerAppRunnable, 3000L);
        }

        public class registerAppRunnable implements Runnable {
            public registerAppRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (HotKey.this.isTriggered) {
                    Slog.d("PhoneWindowManagerExt", "start registerHotKeyApp.");
                    HotKey.this.isTriggered = false;
                    ApplicationInfo applicationInfo = null;
                    String packageName = PhoneWindowManagerExt.this.mTopActivity == null ? null : PhoneWindowManagerExt.this.mTopActivity.getPackageName();
                    if (TextUtils.isEmpty(packageName)) {
                        Slog.d("PhoneWindowManagerExt", "Can not register hot key. packageName is empty. mTopActivity=" + PhoneWindowManagerExt.this.mTopActivity);
                        return;
                    }
                    PackageManager packageManager = PhoneWindowManagerExt.this.mContext.getPackageManager();
                    try {
                        applicationInfo = packageManager.getApplicationInfo(packageName, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (applicationInfo == null) {
                        return;
                    }
                    Intent launchIntentForPackage = PhoneWindowManagerExt.this.mContext.getPackageManager().getLaunchIntentForPackage(packageName);
                    if (launchIntentForPackage != null && HotKey.this.isMatchWithLauncherApps(packageName)) {
                        HotKey hotKey = HotKey.this;
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.putHotKey(hotKey.keyCode, launchIntentForPackage.getComponent());
                        String format = String.format(PhoneWindowManagerExt.this.mContext.getString(R.string.mediasize_na_index_5x8), packageManager.getApplicationLabel(applicationInfo).toString());
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        phoneWindowManagerExt.showToast(phoneWindowManagerExt.mContext, format);
                        if (CoreRune.FW_KEY_SA_LOGGING) {
                            HotKey hotKey2 = HotKey.this;
                            hotKey2.saLogging(hotKey2.keyCode, false, "1" + packageName);
                            return;
                        }
                        return;
                    }
                    Slog.d("PhoneWindowManagerExt", "Can not register hot key. packageName=" + packageName);
                }
            }
        }

        public final void cancelLaunchAppPendingAction() {
            PhoneWindowManagerExt.this.mHandler.removeCallbacks(this.registerAppRunnable);
        }

        public final boolean isMatchWithLauncherApps(String str) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> queryIntentActivities = PhoneWindowManagerExt.this.mContext.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities == null) {
                return false;
            }
            Iterator<ResolveInfo> it = queryIntentActivities.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().activityInfo.packageName)) {
                    return true;
                }
            }
            return false;
        }

        public void launchApp(int i, int i2) {
            if (this.isTriggered) {
                cancelLaunchAppPendingAction();
                this.isTriggered = false;
                ComponentName hotKeyComponentName = PhoneWindowManagerExt.this.mKeyCustomizationPolicy.getHotKeyComponentName(i);
                if (hotKeyComponentName == null) {
                    Slog.d("PhoneWindowManagerExt", "Can not launch hotkey app. The package info is empty.");
                    PhoneWindowManagerExt.this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$HotKey$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PhoneWindowManagerExt.HotKey.this.showGuideDialog();
                        }
                    });
                    return;
                }
                String packageName = hotKeyComponentName.getPackageName();
                Intent launchIntentForPackage = PhoneWindowManagerExt.this.mContext.getPackageManager().getLaunchIntentForPackage(packageName);
                if (launchIntentForPackage == null) {
                    launchIntentForPackage = new Intent("android.intent.action.MAIN");
                    launchIntentForPackage.addCategory("android.intent.category.LAUNCHER");
                    launchIntentForPackage.addFlags(270532608);
                    launchIntentForPackage.setComponent(hotKeyComponentName);
                }
                if (PhoneWindowManagerExt.this.showToastIfNeeded(launchIntentForPackage)) {
                    return;
                }
                PhoneWindowManagerExt.this.mKeyboardShortcutPolicy.launch(i2, hotKeyComponentName.flattenToString());
                if (CoreRune.FW_KEY_SA_LOGGING) {
                    saLogging(i, true, packageName);
                }
            }
        }

        public final void showGuideDialog() {
            if (this.guideDialog != null) {
                Slog.d("PhoneWindowManagerExt", "showHotKeyGuideDialog is showing");
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(PhoneWindowManagerExt.this.mContext);
            builder.setTitle(R.string.mediasize_na_junior_legal);
            builder.setMessage(PhoneWindowManagerExt.this.mContext.getResources().getQuantityString(R.plurals.duration_hours_relative, 3, 3));
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.server.policy.PhoneWindowManagerExt$HotKey$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.server.policy.PhoneWindowManagerExt$HotKey$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    PhoneWindowManagerExt.HotKey.this.lambda$showGuideDialog$1(dialogInterface);
                }
            });
            AlertDialog create = builder.create();
            this.guideDialog = create;
            create.getWindow().getAttributes().setTitle("HotKeyGuideDialog");
            this.guideDialog.getWindow().setType(2008);
            this.guideDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showGuideDialog$1(DialogInterface dialogInterface) {
            this.guideDialog = null;
        }

        public final void saLogging(int i, boolean z, String str) {
            String str2;
            switch (i) {
                case 1090:
                    if (!z) {
                        str2 = "PKBD0027";
                        break;
                    } else {
                        str2 = "PKBD0026";
                        break;
                    }
                case 1091:
                    if (!z) {
                        str2 = "PKBD0029";
                        break;
                    } else {
                        str2 = "PKBD0028";
                        break;
                    }
                case 1092:
                    if (!z) {
                        str2 = "PKBD0031";
                        break;
                    } else {
                        str2 = "PKBD0030";
                        break;
                    }
                default:
                    return;
            }
            PhoneWindowManagerExt.this.sendCoreSaLoggingDimension(str2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shortPressOnHome$7(int i) {
        this.mPolicy.handleShortPressOnHome(i);
    }

    public void shortPressOnHome(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$shortPressOnHome$7(i);
            }
        });
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void onFocusChangedLw(WindowManagerPolicy.WindowState windowState, int i) {
        String str;
        int i2;
        if (windowState != null) {
            str = windowState.getAttrs().getTitle().toString();
            i2 = windowState.getAttrs().type;
        } else {
            str = "";
            i = -1;
            i2 = -1;
        }
        this.mSystemKeyPolicy.updateFocusedWindow(str, i2, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) {
        return this.mSystemKeyPolicy.requestSystemKeyEvent(i, componentName, z);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
        return this.mSystemKeyPolicy.isSystemKeyEventRequested(i, componentName);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) {
        this.mSystemKeyPolicy.registerSystemKeyEvent(i, componentName, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void unregisterSystemKeyEvent(int i, ComponentName componentName) {
        this.mSystemKeyPolicy.unregisterSystemKeyEvent(i, componentName);
    }

    public boolean hasSystemKeyInfo(int i, int i2) {
        return this.mSystemKeyPolicy.hasSystemKeyInfo(i, i2);
    }

    public boolean hasSystemKeyInfoWithKeyPressOld(int i) {
        return this.mSystemKeyPolicy.hasSystemKeyInfoWithKeyPressOld(i);
    }

    public boolean canDispatchingSystemKeyEvent(int i) {
        return this.mSystemKeyPolicy.canDispatchingKeyEvent(i);
    }

    public final int checkSystemKeyBeforeDispatching(int i, IBinder iBinder) {
        Log.d("PhoneWindowManagerExt", "system key requested code=" + i);
        KeyInterceptionInfo keyInterceptionInfoFromToken = this.mPolicy.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
        if (keyInterceptionInfoFromToken != null && keyInterceptionInfoFromToken.layoutParamsType == 2040 && !this.mPolicy.isKeyguardShowingAndNotOccluded()) {
            this.mPolicy.sendCloseSystemWindows("reason");
            Log.d("PhoneWindowManagerExt", "Can not dispatch key event because of expanded status bar, keyCode=" + i);
            return -1;
        }
        Log.d("PhoneWindowManagerExt", "sec check system key before dispatching, keyCode=" + i);
        return 0;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void requestMetaKeyEvent(ComponentName componentName, boolean z) {
        this.mSystemKeyPolicy.requestMetaKeyEvent(componentName, z);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isMetaKeyEventRequested(ComponentName componentName) {
        return this.mSystemKeyPolicy.isMetaKeyEventRequested(componentName);
    }

    public boolean hasMetaKeyPass() {
        return this.mSystemKeyPolicy.hasMetaKeyPass();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void setRotation(int i) {
        Slog.d("PhoneWindowManagerExt", "setRotation: rotation=" + i);
        synchronized (this.mBoosterLock) {
            if (this.mSemRotationBooster == null) {
                SemDvfsManager createInstance = SemDvfsManager.createInstance(this.mContext, "PWM_ROTATION");
                this.mSemRotationBooster = createInstance;
                if (createInstance != null) {
                    createInstance.setHint(15);
                }
            }
            boosterAcquireLocked(this.mSemRotationBooster, "HINT_PWM_ROTATION");
        }
    }

    public void boostWakeUp() {
        synchronized (this.mBoosterLock) {
            if (this.mSemWakeUpBooster == null) {
                SemDvfsManager createInstance = SemDvfsManager.createInstance(this.mContext, "DEVICE_WAKEUP");
                this.mSemWakeUpBooster = createInstance;
                if (createInstance != null) {
                    createInstance.setHint(19);
                }
            }
            boosterAcquireLocked(this.mSemWakeUpBooster, "HINT_DEVICE_WAKEUP");
            sendCommandToSamsungDeviceHealth();
        }
    }

    public final void boosterAcquireLocked(SemDvfsManager semDvfsManager, String str) {
        if (semDvfsManager == null) {
            return;
        }
        try {
            Log.d("PhoneWindowManagerExt", str + " acquire()");
            semDvfsManager.acquire();
        } catch (Exception unused) {
            Log.e("PhoneWindowManagerExt", str + " acquire is failed");
        }
    }

    public final void sendCommandToSamsungDeviceHealth() {
        ISamsungDeviceHealthManager asInterface;
        if (sdhmsBinder == null) {
            sdhmsBinder = ServiceManager.getService("sdhms");
        }
        IBinder iBinder = sdhmsBinder;
        if (iBinder == null || (asInterface = ISamsungDeviceHealthManager.Stub.asInterface(iBinder)) == null) {
            return;
        }
        try {
            asInterface.sendCommand("WAKEUP", "start");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isBlockedPowerKey() {
        ComponentName componentName;
        if (this.mSystemKeyPolicy == null) {
            return false;
        }
        if (!FactoryTest.needBlockingPowerKey() && ((componentName = this.mTopActivity) == null || !"com.sec.facuifunction.app.ui.UIHardKey".equals(componentName.getClassName()))) {
            return false;
        }
        if (!KeyCustomizationConstants.isSafeDebugInput()) {
            return true;
        }
        Slog.d("PhoneWindowManagerExt", "Skip power key behavior by FactoryTest application");
        return true;
    }

    public void initKeyCombinationRules() {
        this.mPolicy.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(24, 26) { // from class: com.android.server.policy.PhoneWindowManagerExt.3
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public boolean preCondition() {
                return super.preCondition();
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void execute() {
                PhoneWindowManagerExt.this.interceptAccessibilityShortcutVolupPowerChord();
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void cancel() {
                PhoneWindowManagerExt.this.checkAccessibilityShortcutVolupPowerTriggered();
            }
        });
        this.mPolicy.mKeyCombinationManager.addRule(new KeyCombinationManager.TwoKeysCombinationRule(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED, 4) { // from class: com.android.server.policy.PhoneWindowManagerExt.4
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public boolean preCondition() {
                return true;
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void execute() {
                PhoneWindowManagerExt.this.interceptStopLockTaskModePinnedChord();
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void cancel() {
                PhoneWindowManagerExt.this.cancelPendingLockTaskModePinnedChordAction();
            }
        });
    }

    public static String sideKeyGlobalActionSaLoggingTypeToString(int i) {
        return i != -1 ? i != 0 ? i != 1 ? Integer.toString(i) : "KEY_COMBINATION" : "LONG_PRESS" : "DEFAULT";
    }

    public void openDictation(int i) {
        callDictation("open_dictation", i);
    }

    public void closeDictation(int i) {
        callDictation("close_dictation", i);
    }

    public final void callDictation(String str, int i) {
        Log.d("PhoneWindowManagerExt", "callDictation, method=" + str + ", keyCode=" + i);
        Bundle bundle = new Bundle();
        bundle.putInt("keyCode", i);
        this.mContext.getContentResolver().call(KeyCustomizationConstants.UriTags.DICTATION, str, (String) null, bundle);
    }

    public final boolean predicateIfInputMethodManagerNonNull(Predicate predicate) {
        InputMethodManager inputMethodManager = (InputMethodManager) SafetySystemService.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            return predicate.test(inputMethodManager);
        }
        return false;
    }

    public boolean isSamsungKeyboardShown() {
        return this.mIsSamsungKeyboard && predicateIfInputMethodManagerNonNull(new Predicate() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isInputMethodShown;
                isInputMethodShown = ((InputMethodManager) obj).isInputMethodShown();
                return isInputMethodShown;
            }
        });
    }

    public boolean skipPowerPress(int i) {
        return i != 2 ? i != 3 ? (i == 5 && CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS && this.mQuintuplePressOnPowerBehavior == 103) ? false : true : (CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL && this.mPolicy.mTriplePressOnPowerBehavior == 102) ? false : true : ((CoreRune.FW_DOUBLE_PRESS_SIDE_KEY && this.mPolicy.mDoublePressOnPowerBehavior == 104) || (CoreRune.FW_QUICK_LAUNCH_CAMERA && this.mPolicy.mDoublePressOnPowerBehavior == 101)) ? false : true;
    }

    public boolean powerPress(KeyEvent keyEvent, boolean z, boolean z2) {
        if (keyEvent != null && !this.mPolicy.mKeyCombinationManager.isKeyConsumed(keyEvent) && isPowerKeyConsumedInCall(getEndCallPowerPolicy(z, z2))) {
            return true;
        }
        if (CoreRune.FW_FINGERPRINT_SIDE_TOUCH) {
            return this.mWakeAndUnlockTriggered || this.mWakeAndUnfoldedTriggered;
        }
        return false;
    }

    public boolean powerMultiPressAction(boolean z, int i, KeyEvent keyEvent, int i2) {
        switch (i) {
            case 101:
                if (CoreRune.FW_QUICK_LAUNCH_CAMERA && isQuickLaunchCameraEnabled(26)) {
                    Slog.i("PhoneWindowManagerExt", "Launch camera by power key double press");
                    handleDoublePressLaunchCamera(26);
                    return true;
                }
                Slog.i("PhoneWindowManagerExt", "Can not launch camera, maybe behavior was wrong. " + this.mQuickLaunchCameraBehavior);
                return true;
            case 102:
            case 103:
                if (!CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL && !CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
                    return true;
                }
                launchEmergencySos(i);
                return true;
            case 104:
                if (!CoreRune.FW_DOUBLE_PRESS_SIDE_KEY) {
                    return true;
                }
                Slog.i("PhoneWindowManagerExt", "Launch target app by side key double press");
                SideKeyDoublePress.launch(this, keyEvent, z);
                return true;
            case 105:
                if (!CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE || !this.mKeyCustomizationPolicy.hasDoublePowerTvModeId()) {
                    return true;
                }
                Slog.i("PhoneWindowManagerExt", "Launch TvMode by power key double press");
                SideKeyDoublePress.launch(this, keyEvent, z);
                return true;
            case 106:
                this.mKeyCustomizationPolicy.launchMultiPressAction(keyEvent, i2);
                return true;
            default:
                Slog.d("PhoneWindowManagerExt", "powerMultiPress behavior was wrong.");
                return false;
        }
    }

    public boolean isDoublePressPower() {
        if ((CoreRune.FW_DOUBLE_PRESS_SIDE_KEY && this.mPolicy.mDoublePressOnPowerBehavior == 104) || this.mPolicy.mDoublePressOnPowerBehavior == 106) {
            return true;
        }
        if (CoreRune.FW_QUICK_LAUNCH_CAMERA && isQuickLaunchCameraEnabled(26)) {
            return true;
        }
        return CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE && this.mPolicy.mDoublePressOnPowerBehavior == 105;
    }

    public boolean isTriplePressPower() {
        return CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL && this.mPolicy.mTriplePressOnPowerBehavior == 102;
    }

    public boolean isQuadruplePressPower() {
        return this.mQuadruplePressOnPowerBehavior == 106;
    }

    public boolean isQuintuplePressPower() {
        return CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS && this.mQuintuplePressOnPowerBehavior == 103;
    }

    public void updateDoublePressPowerBehavior() {
        int lastAction = this.mKeyCustomizationPolicy.getLastAction(8, 26);
        if (CoreRune.FW_DOUBLE_PRESS_SIDE_KEY && this.mKeyCustomizationPolicy.hasSideKeyDoublePressId()) {
            this.mPolicy.mDoublePressOnPowerBehavior = 104;
        } else if (lastAction == 4) {
            this.mPolicy.mDoublePressOnPowerBehavior = 0;
        } else if (CoreRune.FW_QUICK_LAUNCH_CAMERA && this.mKeyCustomizationPolicy.hasDoubleCameraId(26)) {
            this.mPolicy.mDoublePressOnPowerBehavior = 101;
        } else if (CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE && this.mKeyCustomizationPolicy.hasDoublePowerTvModeId()) {
            this.mPolicy.mDoublePressOnPowerBehavior = 105;
        } else if (lastAction == 1 || lastAction == 3 || lastAction == 2) {
            this.mPolicy.mDoublePressOnPowerBehavior = 106;
        } else if (CoreRune.FW_DOUBLE_PRESS_SIDE_KEY) {
            this.mPolicy.mDoublePressOnPowerBehavior = 0;
        }
        Log.d("PhoneWindowManagerExt", "updateDoublePressPowerBehavior, behavior=" + this.mPolicy.mDoublePressOnPowerBehavior);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return;
        }
        keyguardServiceDelegate.setPendingIntentAfterUnlock(pendingIntent, intent);
    }

    public PendingIntent getPendingIntentActivityAsUser(Intent intent, UserHandle userHandle) {
        return PendingIntent.getActivityAsUser(this.mContext, 0, intent, 201326592, null, userHandle);
    }

    public final boolean isQuickLaunchCameraEnabled(int i) {
        return this.mKeyCustomizationPolicy.hasDoubleCameraId(i) || (isCameraRunning() && isCameraClient());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(Boolean bool) {
        this.mQuickLaunchCameraBehavior = Settings.System.getInt(this.mContext.getContentResolver(), "double_tab_launch", 2);
        Log.d("PhoneWindowManagerExt", "updateSettings, mQuickLaunchCameraBehavior=" + this.mQuickLaunchCameraBehavior);
        updateKeyCustomizationInfoQuickLaunchCamera(this.mQuickLaunchCameraBehavior);
    }

    public final void updateKeyCustomizationInfoQuickLaunchCamera(int i) {
        int i2 = (i == 1 || i == 0) ? 3 : 26;
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = this.mKeyCustomizationPolicy.getKeyCustomizationInfo(2001, 8, i2);
        if (i == 3 || i == 1) {
            if (keyCustomizationInfo == null) {
                this.mKeyCustomizationPolicy.putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(8, 2001, i2, 1));
            }
        } else if ((i == 2 || i == 0) && keyCustomizationInfo != null) {
            this.mKeyCustomizationPolicy.removeKeyCustomizationInfo(2001, 8, i2);
        }
        if (i2 == 26) {
            updateDoublePressPowerBehavior();
        }
    }

    public final void handleDoublePressLaunchCamera(int i) {
        if (!doublePressLaunchPolicy(i)) {
            launchPowerDoublePressCamera();
        }
        sendBroadcastDoubleClick(i);
    }

    public class OpeningApps extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            return 1;
        }

        public OpeningApps(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean preCondition(Intent intent, boolean z) {
            int applicationState = this.mPolicyExt.getApplicationState(intent);
            Log.d("PhoneWindowManagerExt", "target app state : " + applicationState);
            if (applicationState == 2) {
                PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                Context context = phoneWindowManagerExt.mContext;
                phoneWindowManagerExt.showToast(context, context.getString(17042826));
                return true;
            }
            if (applicationState == 1) {
                PhoneWindowManagerExt phoneWindowManagerExt2 = this.mPolicyExt;
                phoneWindowManagerExt2.showToast(phoneWindowManagerExt2.mContext, phoneWindowManagerExt2.getToastString(phoneWindowManagerExt2.getApplicationInfo(intent), 17042827));
                return true;
            }
            return super.preCondition(intent, z);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            if (this.mPolicyExt.mPolicy.mKeyguardDelegate.isShowing() || z2) {
                PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                phoneWindowManagerExt.setPendingIntentAfterUnlock(phoneWindowManagerExt.getPendingIntentActivityAsUser(intent, UserHandle.CURRENT), intent2);
            } else {
                this.mPolicyExt.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
            }
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", getPackageName());
            }
        }

        public final String getPackageName() {
            String targetAppName = getTargetAppName();
            String[] split = targetAppName.split("/");
            return split.length >= 2 ? split[0] : targetAppName;
        }
    }

    public class OpeningSamsungPay extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            return 1;
        }

        public OpeningSamsungPay(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public Intent getIntent() {
            return new Intent("android.intent.action.VIEW", Uri.parse(getTargetAppName())).addFlags(270532608);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            if (z2) {
                intent2.putExtra("ignoreUnlock", true);
                PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                phoneWindowManagerExt.setPendingIntentAfterUnlock(phoneWindowManagerExt.getPendingIntentActivityAsUser(intent, UserHandle.CURRENT), intent2);
            } else {
                this.mPolicyExt.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
            }
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "Pay with Samsung Pay");
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean showCoverToast(Intent intent, Intent intent2) {
            if (!WmCoverState.isEnabled() || !WmCoverState.getInstance().isClearTypeCoverClosed()) {
                return false;
            }
            intent.putExtra("showCoverToast", true);
            Log.d("PhoneWindowManagerExt", "neededShowCoverToast for cover");
            return true;
        }
    }

    public class OpeningQuickLaunchCamera extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            return 1;
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean showCoverToast(Intent intent, Intent intent2) {
            return false;
        }

        public OpeningQuickLaunchCamera(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void updateTargetComponent(Intent intent) {
            intent.setComponent(ComponentName.unflattenFromString(getTargetAppName()));
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            this.mPolicyExt.launchPowerDoublePressCamera();
            this.mPolicyExt.sendBroadcastDoubleClick(26);
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "Quick launch camera");
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean doublePressLaunchPolicy(boolean z) {
            return this.mPolicyExt.doublePressLaunchPolicy(26);
        }
    }

    public boolean doublePressLaunchPolicy(int i) {
        if (!this.mPolicy.isUserSetupComplete()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked because UserSetup isn't completed");
            return true;
        }
        if (isDomesticOtaStart()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by OTA status");
            return true;
        }
        if (this.mPolicy.mKeyguardDelegate.isSimLocked()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by SimLock");
            return true;
        }
        if (isCarrierLocked()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by CarrierLock");
            return true;
        }
        if (WmCoverState.isEnabled() && WmCoverState.getInstance().isFlipTypeCoverClosed()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked because cover was closed");
            return true;
        }
        if (hasSystemKeyInfo(i, 8)) {
            Log.i("PhoneWindowManagerExt", "skip double press keyCode(" + i + "), requestedSystemKey");
            return true;
        }
        if (!isInteractionControlEnabled(3)) {
            return false;
        }
        Log.i("PhoneWindowManagerExt", "double press was blocked by interaction control");
        return true;
    }

    public final void launchPowerDoublePressCamera() {
        GestureLauncherService gestureLauncherService = (GestureLauncherService) LocalServices.getService(GestureLauncherService.class);
        if (gestureLauncherService == null) {
            return;
        }
        Log.i("PhoneWindowManagerExt", "launch double press camera");
        gestureLauncherService.handleCameraGesture(1);
    }

    public void sendBroadcastDoubleClick(int i) {
        if (this.mWindowManagerFuncs.isRunningRecentAnimation()) {
            Log.d("PhoneWindowManagerExt", "Can not sendBroadcast double click intent. RecentAnimation is running");
            return;
        }
        Log.d("PhoneWindowManagerExt", "broadcast double click intent keyCode=" + i);
        Intent intent = new Intent("com.samsung.android.action.DOUBLE_CLICK");
        intent.putExtra("KEYCODE", i);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permisson.DOUBLE_CLICK");
    }

    public boolean isCameraRunning() {
        boolean equals = SystemProperties.get("service.camera.running", "0").equals("1");
        Log.d("PhoneWindowManagerExt", "isCameraRunning=" + equals);
        return equals;
    }

    public boolean isCameraClient() {
        boolean equals = SystemProperties.get("service.camera.client", "").equals("com.sec.android.app.camera");
        Log.d("PhoneWindowManagerExt", "isCameraClient=" + equals);
        return equals;
    }

    public Intent getFillInIntent() {
        Intent intent = new Intent();
        intent.putExtra("afterKeyguardGone", true);
        intent.putExtra("ignoreKeyguardState", true);
        intent.putExtra("dismissIfInsecure", true);
        return intent;
    }

    public int getApplicationState(Intent intent) {
        try {
            int applicationEnabledSetting = this.mContext.getPackageManager().getApplicationEnabledSetting(intent.getComponent().getPackageName());
            if (applicationEnabledSetting != 2 && applicationEnabledSetting != 3) {
                int componentEnabledSetting = this.mContext.getPackageManager().getComponentEnabledSetting(intent.getComponent());
                if (componentEnabledSetting != 2 && componentEnabledSetting != 3) {
                    return 0;
                }
            }
            return 1;
        } catch (IllegalArgumentException unused) {
            return 2;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void setTspStateController(TspStateController tspStateController) {
        this.mTspStateController = tspStateController;
    }

    public void startedGoingToSleep() {
        TspStateController tspStateController;
        if (!CoreRune.FW_TSP_STATE_CONTROLLER || (tspStateController = this.mTspStateController) == null) {
            return;
        }
        tspStateController.startedGoingToSleep();
    }

    public class OpeningTvMode extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            return 1;
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean showCoverToast(Intent intent, Intent intent2) {
            return false;
        }

        public OpeningTvMode(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void updateTargetComponent(Intent intent) {
            intent.setComponent(ComponentName.unflattenFromString(getTargetAppName()));
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            this.mPolicyExt.launchDoublePressPowerTvMode();
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "TV mode");
            }
        }
    }

    public final void launchDoublePressPowerTvMode() {
        SemWindowManager.KeyCustomizationInfo lastKeyCustomizationInfo;
        Slog.i("PhoneWindowManagerExt", "launch double press tv mode");
        if (isCameraRunning() || (lastKeyCustomizationInfo = this.mKeyCustomizationPolicy.getLastKeyCustomizationInfo(8, 26)) == null) {
            return;
        }
        int i = lastKeyCustomizationInfo.id;
        if (i != 2002 && i != 1104) {
            Slog.d("PhoneWindowManagerExt", "launchDoublePressPowerTvMode: keyCustomizationInfo ID: " + i);
            return;
        }
        if (this.mPolicy.mKeyguardDelegate.isShowing()) {
            Intent intent = new Intent();
            intent.putExtra("afterKeyguardGone", true);
            setPendingIntentAfterUnlock(getPendingIntentActivityAsUser(lastKeyCustomizationInfo.intent, UserHandle.CURRENT), intent);
            return;
        }
        this.mContext.startActivityAsUser(lastKeyCustomizationInfo.intent, UserHandle.CURRENT);
    }

    public final void updateDoublePressLaunchInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] split = str.split("/");
        if (split.length >= 2) {
            ComponentName componentName = new ComponentName(split[0], split[1]);
            if (componentName.equals(this.mDoublePressLaunchComponentName)) {
                return;
            }
            this.mDoublePressLaunchComponentName = componentName;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(Boolean bool) {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "tvmode_state", 0) == 1;
        Log.d("PhoneWindowManagerExt", "updateSettings tvModeEnabled=" + z + ", " + this.mTvModeEnabled);
        if (z == this.mTvModeEnabled) {
            return;
        }
        this.mTvModeEnabled = z;
        updateKeyCustomizationInfoTvMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$11(Boolean bool) {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "pwrkey_owner_status", 0) == 1;
        Log.d("PhoneWindowManagerExt", "updateSettings tvModeDoublePressEnabled=" + z + ", " + this.mTvModeDoublePressEnabled);
        if (z == this.mTvModeDoublePressEnabled) {
            return;
        }
        this.mTvModeDoublePressEnabled = z;
        updateKeyCustomizationInfoTvMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(Boolean bool) {
        String string = Settings.System.getString(this.mContext.getContentResolver(), "double_tab_launch_component");
        Log.d("PhoneWindowManagerExt", "updateSettings doublePressLaunchComponent=" + string);
        updateDoublePressLaunchInfo(string);
        if (CoreRune.FW_DOUBLE_PRESS_POWER_ATT_TV_MODE && isTvModeComponentName(this.mDoublePressLaunchComponentName)) {
            updateKeyCustomizationInfoTvMode();
        }
    }

    public final void updateKeyCustomizationInfoTvMode() {
        boolean z = this.mKeyCustomizationPolicy.getKeyCustomizationInfo(2002, 8, 26) != null;
        if (canOpenTvModeByPowerDoublePress()) {
            if (!z) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(this.mDoublePressLaunchComponentName);
                this.mKeyCustomizationPolicy.putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(8, 2002, 26, 1, intent));
            }
        } else if (z) {
            this.mKeyCustomizationPolicy.removeKeyCustomizationInfo(2002, 8, 26);
        }
        updateDoublePressPowerBehavior();
    }

    public final boolean canOpenTvModeByPowerDoublePress() {
        return this.mTvModeEnabled && this.mTvModeDoublePressEnabled && isTvModeComponentName(this.mDoublePressLaunchComponentName);
    }

    public final boolean isTvModeComponentName(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return "com.samsung.tvmode/com.samsung.tvmode.activity.MainActivity".equals(componentName.flattenToString());
    }

    public void interceptKeyCombinationScreenshotChord() {
        if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
            this.mIsScreenshotTriggered = true;
            this.mScreenshotTriggeredTime = SystemClock.uptimeMillis();
            Log.d("PhoneWindowManagerExt", "interceptKeyCombinationScreenshotChord triggered");
            return;
        }
        this.mWindowManagerFuncs.sendTakeScreenshotRunnable(1, getDisplayId());
    }

    public void checkKeyCombinationScreenshotChord(KeyEvent keyEvent, boolean z) {
        if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
            if (!this.mIsScreenshotTriggered || keyEvent == null) {
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Log.d("PhoneWindowManagerExt", "Can not take a screenshot, triggered=" + this.mIsScreenshotTriggered + " event=" + keyEvent);
                    return;
                }
                return;
            }
            if (z) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 26) {
                    this.mKeyUpTime[0] = keyEvent.getEventTime();
                } else if (keyCode == 25) {
                    this.mKeyUpTime[1] = keyEvent.getEventTime();
                }
                long[] jArr = this.mKeyUpTime;
                long j = jArr[0];
                long j2 = jArr[1];
                if (j == 0 || j2 == 0) {
                    if (this.mGlobalActionsByKeyCombnation) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Waiting for ");
                    sb.append(j == 0 ? "power" : "volume down");
                    sb.append(" key up event to take a screenshot");
                    Log.d("PhoneWindowManagerExt", sb.toString());
                    return;
                }
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Log.d("PhoneWindowManagerExt", "checkKeyCombinationScreenshotChord triggered=" + this.mScreenshotTriggeredTime + " power=" + j + " volumeDown=" + j2);
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                long j3 = uptimeMillis - this.mScreenshotTriggeredTime;
                if (uptimeMillis <= j + 150 && uptimeMillis <= 150 + j2 && j3 < 1000) {
                    Log.d("PhoneWindowManagerExt", "take a screenshot, this is triggered by keyCombination");
                    this.mWindowManagerFuncs.sendTakeScreenshotRunnable(1, getDisplayId());
                    if (CoreRune.FW_KEY_SA_LOGGING) {
                        CoreSaLogger.logForBasic("HWB1007");
                    }
                } else if (!this.mGlobalActionsByKeyCombnation) {
                    Log.d("PhoneWindowManagerExt", "Screenshot by the key combination is not triggered, time=" + Math.abs(j - j2));
                }
            }
            if (this.mGlobalActionsByKeyCombnation) {
                Log.d("PhoneWindowManagerExt", "The key combination long press event was consumed by global action");
            }
            long[] jArr2 = this.mKeyUpTime;
            jArr2[0] = 0;
            jArr2[1] = 0;
            this.mIsScreenshotTriggered = false;
            this.mScreenshotTriggeredTime = 0L;
            this.mGlobalActionsByKeyCombnation = false;
        }
    }

    public void setScreenshotEnabled() {
        Log.d("PhoneWindowManagerExt", "setScreenshotEnabled, screenOnFully=" + this.mPolicy.mDefaultDisplayPolicy.isScreenOnFully());
        this.mScreenshotEnabled = true;
    }

    public class OpeningBixby extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            return 3;
        }

        public OpeningBixby(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void updateTargetComponent(Intent intent) {
            ComponentName componentName = this.mPolicyExt.mBixbyService.getComponentName();
            if (componentName != null) {
                intent.setComponent(componentName);
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            this.mPolicyExt.mBixbyService.startService(new BixbyService.Params.Builder(keyEvent, z).setDoublePress().build());
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "Bixby");
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean doublePressLaunchPolicy(boolean z) {
            return (!super.doublePressLaunchPolicy(z) && this.mPolicyExt.mBixbyService.showToast(z, true) == null && this.mPolicyExt.mBixbyService.isAvailable()) ? false : true;
        }
    }

    public void updateTriplePressPowerBehavior() {
        if (CoreRune.FW_TRIPLE_PRESS_POWER_LAUNCH_PANIC_CALL) {
            this.mPolicy.mTriplePressOnPowerBehavior = 102;
            return;
        }
        int lastAction = this.mKeyCustomizationPolicy.getLastAction(16, 26);
        if (lastAction == 1 || lastAction == 3 || lastAction == 2) {
            this.mPolicy.mTriplePressOnPowerBehavior = 106;
        }
    }

    public final void launchEmergencySos(int i) {
        if (this.mIntentEmergencySos == null) {
            Log.e("PhoneWindowManagerExt", "Can not launch emergency SOS, the intent is null");
            return;
        }
        int isOtherKeyPressed = this.mPolicy.mKeyCombinationManager.isOtherKeyPressed();
        if (isOtherKeyPressed != 0) {
            Log.d("PhoneWindowManagerExt", "Can not launch emergency SOS, other key is pressed " + isOtherKeyPressed);
            return;
        }
        Slog.d("PhoneWindowManagerExt", "launch emergency SOS, behavior=" + PhoneWindowManager.multiPressOnPowerBehaviorToString(i));
        this.mContext.startActivityAsUser(this.mIntentEmergencySos, UserHandle.CURRENT);
        if (CoreRune.FW_KEY_SA_LOGGING) {
            CoreSaLogger.logForBasic(i == 102 ? "HWB1003" : "HWB1005");
        }
    }

    public final Intent getEmergencySosIntent() {
        Intent intent = new Intent("com.android.systemui.action.LAUNCH_EMERGENCY");
        ResolveInfo topEmergencySosResolveInfo = getTopEmergencySosResolveInfo(this.mContext.getPackageManager().queryIntentActivities(intent, 1048576));
        if (topEmergencySosResolveInfo == null) {
            Log.e("PhoneWindowManagerExt", "Couldn't find an app to process the emergency intent.");
            return null;
        }
        ActivityInfo activityInfo = topEmergencySosResolveInfo.activityInfo;
        if (activityInfo == null) {
            Log.e("PhoneWindowManagerExt", "activityInfo is null, Can not update the emergency intent.");
            return null;
        }
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        intent.setFlags(268435456);
        return intent;
    }

    public final ResolveInfo getTopEmergencySosResolveInfo(List list) {
        ActivityInfo activityInfo;
        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if (resolveInfo != null && (activityInfo = resolveInfo.activityInfo) != null && "com.samsung.android.emergency".equals(activityInfo.packageName)) {
                return resolveInfo;
            }
        }
        return (ResolveInfo) list.get(0);
    }

    public class OpeningSecureFolder extends SideKeyDoublePress.Behavior {
        public OpeningSecureFolder(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            this.mPolicyExt.launchSecureFolder();
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "Quick switch to Secure Folder");
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean doublePressLaunchPolicy(boolean z) {
            return super.doublePressLaunchPolicy(z);
        }
    }

    public final PersonaManagerService getPersonaManagerService() {
        if (this.mPersonaManagerService == null) {
            this.mPersonaManagerService = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
        }
        return this.mPersonaManagerService;
    }

    public final void launchSecureFolder() {
        if (getPersonaManagerService() == null) {
            return;
        }
        if ((CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE && isReserveBatteryMode()) || SemEmergencyManager.isMinimalBatteryUseMode(this.mContext) || SemPersonaManager.getSecureFolderId(this.mContext) > 0) {
            getPersonaManagerService().launchSeamLessSf();
            return;
        }
        Intent intent = new Intent("com.sec.knox.securefolder.CREATE_SECURE_FOLDER");
        intent.setFlags(268435456);
        this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public final void handleLongPressOnRecent(int i) {
        if (this.mKeyCustomizationPolicy.getLastAction(4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED) == 4) {
            return;
        }
        if (isLockTaskModeEnabled() && isTouchExplorationEnabled()) {
            try {
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Log.d("PhoneWindowManagerExt", "Recent long press used in Lock task mode");
                }
                ActivityTaskManager.getService().stopSystemLockTaskMode();
                return;
            } catch (RemoteException e) {
                Log.d("PhoneWindowManagerExt", "Unable to reach activity manager", e);
                return;
            }
        }
        this.mPolicy.toggleRecentApps(i);
    }

    public final void performHapticFeedbackLw(int i, boolean z, String str) {
        performHapticFeedbackLw(Process.myUid(), this.mContext.getOpPackageName(), i, z, str);
    }

    public boolean performHapticFeedbackLw(int i, String str, int i2, boolean z, String str2) {
        boolean z2;
        VibrationEffect vibrationEffect;
        int i3;
        if (!this.mVibrator.hasVibrator()) {
            return false;
        }
        Log.d("PhoneWindowManagerExt", "f.b. a=" + z + " id=" + i2 + " he=" + this.mIsHapticsEnabled + " ksno=" + this.mPolicy.isKeyguardShowingAndNotOccluded() + " uid=" + i + " callingPackage=" + str + " reason=" + str2 + " Caller=" + Debug.getCallers(5));
        if (!z && (!this.mIsHapticsEnabled || (!this.mPolicy.hasNavigationBar() && this.mPolicy.isKeyguardShowingAndNotOccluded()))) {
            Slog.d("PhoneWindowManagerExt", "haptic disabled by policy");
            return false;
        }
        VibrationAttributes vibrationAttributes = this.mPolicy.getVibrationAttributes(i2);
        if (z) {
            vibrationAttributes = new VibrationAttributes.Builder(vibrationAttributes).setFlags(2, 2).build();
        }
        VibrationAttributes vibrationAttributes2 = vibrationAttributes;
        if (this.mIsHapticsSupported) {
            int hapticVibrationIndex = getHapticVibrationIndex(i2);
            if (hapticVibrationIndex == -1) {
                return false;
            }
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Slog.d("PhoneWindowManagerExt", "vibrate type=" + hapticVibrationIndex);
            }
            vibrationEffect = VibrationEffect.semCreateHaptic(hapticVibrationIndex, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            z2 = true;
        } else {
            if (CoreRune.FW_HAPTIC_FEEDBACK_ON_DC_MOTOR) {
                z2 = true;
                if (performHapticFeedbackOnDcMotor(z, i2, i, str, str2, vibrationAttributes2)) {
                    return true;
                }
            } else {
                z2 = true;
            }
            vibrationEffect = null;
            if (i2 == 0 || i2 == 13 || i2 == z2 || i2 == 3 || i2 == HapticFeedbackConstants.semGetVibrationIndex(z2 ? 1 : 0) || i2 == HapticFeedbackConstants.semGetVibrationIndex(2)) {
                i3 = 50;
            } else if (i2 == HapticFeedbackConstants.semGetVibrationIndex(3)) {
                i3 = 100;
            } else if (i2 == HapticFeedbackConstants.semGetVibrationIndex(9)) {
                i3 = FrameworkStatsLog.f446x58c6c32;
            } else if (i2 == HapticFeedbackConstants.semGetVibrationIndex(8)) {
                i3 = 500;
            } else if (i2 == HapticFeedbackConstants.semGetVibrationIndex(11)) {
                i3 = NetworkConstants.ETHER_MTU;
            } else {
                if (i2 != HapticFeedbackConstants.semGetVibrationIndex(10)) {
                    return false;
                }
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Slog.d("PhoneWindowManagerExt", "vibrate pattern");
                }
                vibrationEffect = VibrationEffect.createWaveform(new long[]{0, 250, 250, 250}, -1);
                i3 = 0;
            }
            if (vibrationEffect == null) {
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Slog.d("PhoneWindowManagerExt", "vibrate duration=" + i3);
                }
                vibrationEffect = VibrationEffect.createOneShot(i3, -1);
            }
        }
        this.mVibrator.vibrate(i, str, vibrationEffect, str2, vibrationAttributes2);
        return z2;
    }

    public final int getHapticVibrationIndex(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 10003) {
                    switch (i) {
                        case 3:
                        case 5:
                        case 12:
                        case 15:
                        case 16:
                            break;
                        case 4:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 13:
                            return HapticFeedbackConstants.semGetVibrationIndex(41);
                        case 14:
                            break;
                        case 17:
                            return HapticFeedbackConstants.semGetVibrationIndex(5);
                        default:
                            if (SemHapticFeedbackConstants.isValidatedVibeIndex(i)) {
                                return i;
                            }
                            return -1;
                    }
                }
            }
            return HapticFeedbackConstants.semGetVibrationIndex(14);
        }
        return HapticFeedbackConstants.semGetVibrationIndex(1);
    }

    public final boolean performHapticFeedbackOnDcMotor(boolean z, int i, int i2, String str, String str2, VibrationAttributes vibrationAttributes) {
        if (z || this.mVibrator.semGetSupportedVibrationType() != 1) {
            return false;
        }
        if (i == 0 || i == 50132) {
            this.mVibrator.vibrate(i2, str, VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH), str2, vibrationAttributes);
        } else {
            Slog.d("PhoneWindowManagerExt", "haptic generated by application");
        }
        return true;
    }

    public void performSystemKeyFeedback(KeyEvent keyEvent) {
        InputDevice device = keyEvent.getDevice();
        if (keyEvent.getDeviceId() != -1) {
            if (device == null || !"SPC_Remote_Controller".equals(device.getName())) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 4) {
                    if (keyCode != 27) {
                        if (keyCode != 82) {
                            if (keyCode == 84) {
                                if (this.mIsHapticsSupported) {
                                    performHapticFeedbackLw(HapticFeedbackConstants.semGetVibrationIndex(2), false, "Search key - Press");
                                }
                                playSoundEffect();
                                return;
                            } else if (keyCode != 187) {
                                if (keyCode != 207) {
                                    return;
                                }
                            }
                        }
                    }
                    playSoundEffect();
                    return;
                }
                if (device == null || device.getName() == null || device.getName().contains("gpio")) {
                    return;
                }
                if (this.mIsHapticsSupported) {
                    performHapticFeedbackLw(HapticFeedbackConstants.semGetVibrationIndex(2), false, "keyCode(" + keyCode + ") - Press");
                }
                playSoundEffect();
            }
        }
    }

    public final void playSoundEffect() {
        AudioManager audioManager = getAudioManager();
        if (audioManager == null) {
            Slog.w("PhoneWindowManagerExt", "Couldn't get audio manager");
        } else if (!this.mPolicy.isKeyguardShowingAndNotOccluded() && !"trigger_restart_min_framework".equals(KeyCustomizationConstants.VOLD_DECRYPT)) {
            audioManager.playSoundEffect(102, ActivityManager.getCurrentUser());
        } else {
            Slog.w("PhoneWindowManagerExt", "keyguard - disable key sound");
        }
    }

    public final AudioManager getAudioManager() {
        AudioManager audioManager;
        synchronized (this.mServiceAcquireLock) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
            }
            audioManager = this.mAudioManager;
        }
        return audioManager;
    }

    public boolean externalKeyboardPolicy() {
        if (!this.mBootCompleted) {
            Log.d("PhoneWindowManagerExt", "preCondition : Boot is not completed");
            return true;
        }
        if (!this.mPolicy.isUserSetupComplete()) {
            Log.d("PhoneWindowManagerExt", "preCondition : User setup is not completed");
            return true;
        }
        if (this.mPolicy.isKeyguardLocked()) {
            Log.d("PhoneWindowManagerExt", "preCondition : Keyguard is shown");
            return true;
        }
        if (CoreRune.IS_FACTORY_BINARY) {
            Log.d("PhoneWindowManagerExt", "preCondition : Factory binary");
            return true;
        }
        if (FactoryTest.isAutomaticTestMode(this.mContext)) {
            Log.d("PhoneWindowManagerExt", "preCondition : Automatic test mode");
            return true;
        }
        if (hasMetaKeyPass()) {
            return true;
        }
        return WmCoverState.isEnabled() && WmCoverState.getInstance().isCoverClosed() && getDexMode() == 0;
    }

    public boolean shouldDispatchMetaCombos(int i) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.d("PhoneWindowManagerExt", "shouldDispatchMetaCombos keyCode=" + i);
        }
        if (hasMetaKeyPass()) {
            return true;
        }
        return (i == 55 || i == 56) && CoreRune.FW_SUPPORT_TOOLBAR_SHORTCUT;
    }

    public void injectionKeyEvent(final int i, final int i2, final int i3) {
        Thread thread = this.mKeyEventInjectionThread;
        if (thread != null && thread.isAlive()) {
            this.mKeyEventInjectionThread.interrupt();
        }
        Thread thread2 = new Thread(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.lambda$injectionKeyEvent$13(i, i2, i3);
            }
        });
        this.mKeyEventInjectionThread = thread2;
        thread2.start();
    }

    public static /* synthetic */ void lambda$injectionKeyEvent$13(int i, int i2, int i3) {
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            Instrumentation instrumentation = new Instrumentation();
            KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, 0, i, 0, 0, -1, 0, i2, 0);
            keyEvent.semSetDisplayId(i3);
            instrumentation.sendKeySync(keyEvent);
            KeyEvent keyEvent2 = new KeyEvent(uptimeMillis, uptimeMillis, 1, i, 0, 0, -1, 0, i2, 0);
            keyEvent2.semSetDisplayId(i3);
            instrumentation.sendKeySync(keyEvent2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean sensorToggleBehavior(int i) {
        boolean z;
        int i2;
        if (i == 2) {
            z = this.mIsCameraSensorToggleSupported;
            i2 = R.string.network_logging_notification_title;
        } else if (i == 1) {
            z = this.mIsMicSensorToggleSupported;
            i2 = R.string.network_partial_connectivity;
        } else {
            z = false;
            i2 = 0;
        }
        if (externalKeyboardPolicy() || !z) {
            return false;
        }
        SensorPrivacyManager sensorPrivacyManager = SensorPrivacyManager.getInstance(this.mContext);
        boolean isSensorPrivacyEnabled = sensorPrivacyManager.isSensorPrivacyEnabled(i);
        sensorPrivacyManager.setSensorPrivacyForProfileGroupWithConfirmPopup(5, i, !isSensorPrivacyEnabled);
        if (isSensorPrivacyEnabled) {
            Context context = this.mContext;
            showToast(context, context.getResources().getString(i2));
        }
        return true;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void notifyPenSwitchChanged(long j, boolean z, boolean z2) {
        String str;
        if (!CoreRune.FW_SPEN || this.mPenType == 5 || z == this.mPenState) {
            Log.d("PhoneWindowManagerExt", "notifyPenSwitchChanged ignored, mPenType=" + this.mPenType + ", mPenState=" + this.mPenState + ", newPenState=" + (z ? 1 : 0));
            return;
        }
        boolean isScreenOn = this.mPolicy.isScreenOn();
        boolean canStartScreenOffMemo = canStartScreenOffMemo(z ? 1 : 0);
        if (!z && this.mPenState != -1) {
            if (isScreenOn && !this.mPolicy.mPowerManagerInternal.isInternalDisplayOff()) {
                this.mPolicy.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
            } else if (!CoreRune.FW_SPEN_SCREEN_OFF_MEMO || !canStartScreenOffMemo) {
                this.mPolicy.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 102, "penDetached");
            }
        }
        if (this.mPenSoundEnabled && this.mPenState != -1 && !getAudioManager().isStreamMute(1)) {
            if (this.mPenSoundInfo == null && (str = this.mPenSoundFilePath) != null) {
                this.mPenSoundInfo = new PenSoundInfo(str);
            }
            PenSoundInfo penSoundInfo = this.mPenSoundInfo;
            if (penSoundInfo != null) {
                penSoundInfo.playPenSound(z);
            } else {
                Log.e("PhoneWindowManagerExt", "mPenSoundInfo is null");
            }
        }
        if (this.mPenVibrationEnabled && this.mPenState != -1) {
            this.mVibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), vibrationEffect(z), z ? "SPEN_ATTACHED" : "SPEN_DETACHED", new VibrationAttributes.Builder(new AudioAttributes.Builder().build()).build());
        }
        this.mPenInsertIntent.putExtra("penInsert", z);
        this.mPenInsertIntent.putExtra("isScreenOn", isScreenOn);
        this.mPenInsertIntent.putExtra("isKeyguardLocked", this.mPolicy.isKeyguardLocked());
        this.mPenInsertIntent.putExtra("isBoot", j == 0);
        this.mPenInsertIntent.putExtra("isServiceOn", true);
        this.mPenInsertIntent.putExtra("isReversed", z2);
        this.mContext.sendStickyBroadcastAsUser(this.mPenInsertIntent, UserHandle.ALL);
        if (CoreRune.FW_SPEN_SMART_CLIP) {
            if (this.mSpenGestureManager == null) {
                this.mSpenGestureManager = (SpenGestureManager) this.mContext.getSystemService("spengestureservice");
            }
            SpenGestureManager spenGestureManager = this.mSpenGestureManager;
            if (spenGestureManager != null) {
                spenGestureManager.setSpenInsertionState(z);
            }
        }
        if (CoreRune.FW_SPEN_SCREEN_OFF_MEMO && canStartScreenOffMemo) {
            notifyPenStateToScreenOffMemo(z);
        }
        Slog.d("PhoneWindowManagerExt", "notifyPenSwitchChanged, penInsert=" + z + ", reversed =" + z2 + ", screenOn=" + isScreenOn + ", sound=" + this.mPenSoundEnabled + ", vibration=" + this.mPenVibrationEnabled + ", canStartScreenOffMemo=" + canStartScreenOffMemo);
        this.mPenState = z ? 1 : 0;
    }

    public final boolean canStartScreenOffMemo(int i) {
        if (!this.mScreenOffMemoEnabled) {
            Log.d("PhoneWindowManagerExt", "can not start ScreenOffMemo, the option is disabled");
            return false;
        }
        int i2 = this.mPenType;
        if (i2 == 2 && i == 1) {
            Log.d("PhoneWindowManagerExt", "can start ScreenOffMemo, pen attached.");
            return true;
        }
        if (i2 != 0) {
            Log.d("PhoneWindowManagerExt", "can not start ScreenOffMemo, pen type was wrong. " + this.mPenType);
            return false;
        }
        if (this.mPenState == -1) {
            Log.d("PhoneWindowManagerExt", "can not start ScreenOffMemo, pen state was wrong. " + this.mPenState);
            return false;
        }
        if (getDexMode() == 2) {
            Log.d("PhoneWindowManagerExt", "can not start ScreenOffMemo in dual dex mode");
            return false;
        }
        boolean isAwake = this.mPolicy.mDefaultDisplayPolicy.isAwake();
        if (i == 1 || (i == 0 && !isAwake)) {
            return true;
        }
        Log.d("PhoneWindowManagerExt", "can not start ScreenOffMemo, penState=" + i + " isAwake=" + isAwake);
        return false;
    }

    public final void notifyPenStateToScreenOffMemo(boolean z) {
        Log.d("PhoneWindowManagerExt", "startService, screenOffMemo");
        this.mScreenOffMemoIntent.putExtra("pen_intent_com", z ? "pen_attach" : "pen_detach");
        this.mContext.startServiceAsUser(this.mScreenOffMemoIntent, UserHandle.CURRENT);
    }

    public class PenSoundInfo {
        public int mPenAttachSoundId;
        public int mPenDetachSoundId;
        public SoundPool mPenSoundPool;
        public int mPenSoundStreamId;
        public String mAttachPenSoundPath = null;
        public String mDetachPenSoundPath = null;
        public String mPenSoundPath = null;

        public PenSoundInfo(String str) {
            updatePenSound(str);
        }

        public final void updatePenSound(String str) {
            this.mPenSoundPath = str;
            String oMCPenSoundPath = getOMCPenSoundPath("Pen_att_noti1.ogg");
            String oMCPenSoundPath2 = getOMCPenSoundPath("Pen_det_noti1.ogg");
            File file = getFile(oMCPenSoundPath);
            File file2 = getFile(oMCPenSoundPath2);
            if (CoreRune.FW_OMC_SPEN_SOUND && file != null && file2 != null) {
                this.mAttachPenSoundPath = oMCPenSoundPath;
                this.mDetachPenSoundPath = oMCPenSoundPath2;
            } else if (!TextUtils.isEmpty(this.mPenSoundPath)) {
                String[] split = this.mPenSoundPath.split(",");
                if (split.length > 1) {
                    this.mAttachPenSoundPath = split[0];
                    this.mDetachPenSoundPath = split[1];
                }
            }
            if (TextUtils.isEmpty(this.mAttachPenSoundPath) || TextUtils.isEmpty(this.mDetachPenSoundPath)) {
                Log.e("PhoneWindowManagerExt", "pen sound path was wrong. " + toString());
                return;
            }
            SoundPool soundPool = this.mPenSoundPool;
            if (soundPool == null) {
                try {
                    SoundPool soundPool2 = new SoundPool(2, 1, 0);
                    this.mPenSoundPool = soundPool2;
                    this.mPenAttachSoundId = soundPool2.load(this.mAttachPenSoundPath, 1);
                    this.mPenDetachSoundId = this.mPenSoundPool.load(this.mDetachPenSoundPath, 1);
                    return;
                } catch (Exception unused) {
                    Slog.e("PhoneWindowManagerExt", "Cannot load pen sound");
                    this.mPenSoundPool = null;
                    return;
                }
            }
            this.mPenAttachSoundId = soundPool.load(this.mAttachPenSoundPath, 1);
            this.mPenDetachSoundId = this.mPenSoundPool.load(this.mDetachPenSoundPath, 1);
        }

        public void playPenSound(boolean z) {
            SoundPool soundPool = this.mPenSoundPool;
            if (soundPool == null) {
                return;
            }
            int i = z ? this.mPenAttachSoundId : this.mPenDetachSoundId;
            if (i == 0) {
                Slog.e("PhoneWindowManagerExt", "Play pen sound failed, soundId=" + i);
                return;
            }
            try {
                soundPool.stop(this.mPenSoundStreamId);
                this.mPenSoundStreamId = this.mPenSoundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception unused) {
                Slog.e("PhoneWindowManagerExt", "Play pen sound failed, soundId=" + i);
            }
        }

        public final String getOMCPenSoundPath(String str) {
            String str2 = SystemProperties.get("persist.sys.omc_respath");
            if (str2 == null) {
                return "";
            }
            return str2 + "/media/audio/spen/" + str;
        }

        public final File getFile(String str) {
            File file = new File(str);
            if (!file.exists() || file.length() <= 0) {
                return null;
            }
            return file;
        }

        public void setPenSoundPath(String str) {
            updatePenSound(str);
        }

        public String getPenSoundPath() {
            return this.mPenSoundPath;
        }

        public String toString() {
            return "PenSoundInfo{attachPath=" + this.mAttachPenSoundPath + ", detachPath=" + this.mDetachPenSoundPath + "}";
        }
    }

    public final VibrationEffect vibrationEffect(boolean z) {
        int semGetVibrationIndex;
        if (CoreRune.FW_OMC_SPEN_VIBRATION) {
            if (z) {
                semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(63);
            } else {
                semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(62);
            }
        } else if (z) {
            semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(5);
        } else {
            semGetVibrationIndex = HapticFeedbackConstants.semGetVibrationIndex(1);
        }
        return VibrationEffect.semCreateHaptic(semGetVibrationIndex, -1, VibrationEffect.SemMagnitudeType.TYPE_MAX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$14(Boolean bool) {
        PenSoundInfo penSoundInfo;
        String stringForUser = Settings.System.getStringForUser(this.mContext.getContentResolver(), "pen_detachment_notification", -2);
        this.mPenSoundFilePath = stringForUser;
        if (TextUtils.isEmpty(stringForUser) || (penSoundInfo = this.mPenSoundInfo) == null || this.mPenSoundFilePath.equals(penSoundInfo.getPenSoundPath())) {
            return;
        }
        this.mPenSoundInfo.setPenSoundPath(this.mPenSoundFilePath);
    }

    public final boolean getEndCallPowerPolicy(boolean z, boolean z2) {
        if ((this.mPolicy.mIncallPowerBehavior & 2) == 0) {
            return false;
        }
        if (!z || z2) {
            return WmCoverState.isEnabled() && WmCoverState.getInstance().isFlipTypeCoverClosed();
        }
        return true;
    }

    public final boolean isPowerKeyConsumedInCall(boolean z) {
        TelecomManager telecommService = this.mPolicy.getTelecommService();
        if (telecommService == null) {
            return false;
        }
        if (telecommService.isRinging()) {
            Log.d("PhoneWindowManagerExt", "silenceRinger");
            silenceRinger(26);
            return false;
        }
        if (!z || !telecommService.isInCall()) {
            return false;
        }
        Log.d("PhoneWindowManagerExt", "endCall");
        endCall(26);
        return true;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean startGameToolsService(int i, int i2, boolean z) {
        if (!isSupportedGameBooster()) {
            return false;
        }
        Slog.d("PhoneWindowManagerExt", "start service game TOOLS: " + i + " " + i2);
        Intent intent = new Intent("com.samsung.android.game.gametools.GAMEPAD_INTENTSERVICE");
        intent.setPackage("com.samsung.android.game.gametools");
        intent.putExtra("DEVICE_VID", i);
        intent.putExtra("DEVICE_PID", i2);
        intent.putExtra("DEVICE_ACTION", z ? "CONNECTED" : "PRESS");
        this.mContext.startServiceAsUser(intent, UserHandle.SYSTEM);
        return true;
    }

    public final boolean isSupportedGameBooster() {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.game.gametools", 0);
            if (applicationInfo != null && applicationInfo.enabled) {
                return true;
            }
            Log.d("PhoneWindowManagerExt", "GameBooster is disabled");
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("PhoneWindowManagerExt", "GameBooster is not installed, " + e);
            return false;
        }
    }

    public final void startAccessibilityShortcutVolupPower() {
        Log.d("PhoneWindowManagerExt", "start Accessibility Shortcut Volup + Power");
        if (this.mAccessibilityDirectAccessController == null) {
            this.mAccessibilityDirectAccessController = new AccessibilityDirectAccessController(this.mContext);
        }
        this.mAccessibilityDirectAccessController.performAccessibilityDirectAccess();
    }

    public final void interceptAccessibilityShortcutVolupPowerChord() {
        this.mIsAccessibilityShortcutVolupPower = true;
    }

    public final void checkAccessibilityShortcutVolupPowerTriggered() {
        if (this.mIsAccessibilityShortcutVolupPower) {
            this.mIsAccessibilityShortcutVolupPower = false;
            setAppOpsPermissionIfNeeded(23);
            startAccessibilityShortcutVolupPower();
            if (CoreRune.FW_KEY_SA_LOGGING) {
                CoreSaLogger.logForBasic("HWB1008");
            }
        }
    }

    public final void setAppOpsPermissionIfNeeded(int i) {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        }
        String opPackageName = this.mContext.getOpPackageName();
        int checkOpNoThrow = this.mAppOpsManager.checkOpNoThrow(i, 1000, opPackageName);
        Log.d("PhoneWindowManagerExt", "setAppOpsPermissionIfNeeded code=" + i + " p_name=" + opPackageName + " mode=" + checkOpNoThrow);
        if (checkOpNoThrow != 0) {
            this.mAppOpsManager.setMode(i, 1000, opPackageName, 0);
        }
    }

    public boolean isInteractionControlEnabled(int i) {
        return isInteractionControlEnabled(i, false);
    }

    public final boolean isInteractionControlEnabled(int i, boolean z) {
        if (i == 3 || i == 224) {
            return false;
        }
        switch (i) {
            case 24:
            case 25:
                return this.mIsInteractionControlEnabled && this.mIsVolumeKeyBlocked;
            case 26:
                return this.mIsInteractionControlEnabled && this.mIsPowerKeyBlocked && z;
            default:
                return this.mIsInteractionControlEnabled;
        }
    }

    public void sendPowerKeyToCover() {
        CoverPolicy coverPolicy = getCoverPolicy();
        if (coverPolicy != null) {
            coverPolicy.sendPowerKeyToCover();
        }
    }

    public CoverPolicy getCoverPolicy() {
        return this.mPolicy.mDefaultDisplayPolicy.mExt.getCoverPolicy();
    }

    public boolean isVolumeKeyAnswerCallMode(int i) {
        return i == 24 && this.mIsVolumeUpKeyMode && this.mPolicy.hasNavigationBar();
    }

    public final boolean answerCallByHomeKey(boolean z) {
        if (!this.mIsAnyKeyMode) {
            return false;
        }
        if (z) {
            this.mAcceptCallHomeConsumed = false;
            TelecomManager telecommService = this.mPolicy.getTelecommService();
            if (telecommService != null && telecommService.isRinging() && (!CoreRune.FW_SKT_PHONE_RELAX_MODE || !this.mIsSktPhoneRelaxMode)) {
                Log.i("PhoneWindowManagerExt", "ringing: Accept the call!");
                acceptRingingCall(3);
                this.mAcceptCallHomeConsumed = true;
                Log.i("PhoneWindowManagerExt", "Ignoring HOME; there's a ringing incoming call and set anykey mode");
                return true;
            }
        } else if (this.mAcceptCallHomeConsumed) {
            Log.i("PhoneWindowManagerExt", "Ignoring HOME; consumed by accept call.");
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHomeBroadcast$15() {
        this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.action.START_DOCK_OR_HOME"), UserHandle.CURRENT, "com.samsung.android.permisson.START_DOCK_OR_HOME");
    }

    public final void performHomeBroadcast() {
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$performHomeBroadcast$15();
            }
        });
    }

    public boolean isCarrierLocked() {
        int currentUser = ActivityManager.getCurrentUser();
        return this.mLockPatternUtils.isCarrierLockEnabled(currentUser) || this.mLockPatternUtils.isFMMLockEnabled(currentUser) || this.mLockPatternUtils.isRMMLockEnabled(currentUser);
    }

    public boolean isDomesticOtaStart() {
        return "true".equals(SystemProperties.get("ril.domesticOtaStart"));
    }

    public boolean isDoubleTapToWakeUp(int i) {
        return i == 224 && this.mIsDoubleTapToWakeUpSupported && this.mIsDoubleTapToWakeUp;
    }

    public boolean isDisplayInDexMode(int i) {
        if (getDexMode() == 2 && i == 2) {
            return true;
        }
        return getDexMode() == 1 && i == 0;
    }

    public int adjustKeyEventDisplayIdForDex(KeyEvent keyEvent) {
        int displayId = keyEvent.getDisplayId();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 221 && keyCode != 220 && isInDexMode() && displayId == -1) {
            displayId = this.mWindowManagerFuncs.getDisplayIdForPointerIcon();
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Log.d("PhoneWindowManagerExt", "adjustKeyEventDisplayIdForDex, displayId=" + displayId);
            }
        }
        return displayId;
    }

    public final void enableOrDisableDexMode() {
        if (!this.mPolicy.isUserSetupComplete()) {
            Log.d("PhoneWindowManagerExt", "enableOrDisableDexMode : User setup is not completed");
        } else {
            startDex(CoreRune.MT_NEW_DEX && DesktopModeFeature.SUPPORT_NEW_DEX && "new".equals(DesktopModeSettings.getSettings(this.mContext.getContentResolver(), "dex_mode", DesktopModeSettings.DEX_MODE_DEFAULT_VALUE)));
        }
    }

    public final void startDex(boolean z) {
        Intent intent = new Intent("com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST");
        intent.putExtra(z ? "com.samsung.android.desktopmode.extra.NEW_DEX_MODE_STATE" : "com.samsung.android.desktopmode.extra.DESKTOP_MODE_STATE", getDexMode() == 0 ? 1 : 2);
        intent.putExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_SOURCE", 6);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public final boolean interceptKeyBeforeDispatchingForMT(KeyEvent keyEvent) {
        if (this.mPolicy.isKeyguardLocked()) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode != 46) {
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    break;
                default:
                    return false;
            }
        }
        if (z && repeatCount == 0 && !hasMetaKeyPass() && keyEvent.isMetaPressed() && (keyEvent.isCtrlPressed() || keyEvent.isShiftPressed())) {
            this.mWindowManagerFuncs.sendShortcutKey(keyEvent);
            return true;
        }
        return false;
    }

    public boolean isDisplayForShellShortcut(int i) {
        return (getDexMode() == 0 && i == -1) || (getDexMode() == 3 && i == 0);
    }

    public final boolean interceptKeyBeforeDispatchingForDex(IBinder iBinder, KeyEvent keyEvent, int i) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        int repeatCount = keyEvent.getRepeatCount();
        if (keyCode == 1084 && !z) {
            enableOrDisableDexMode();
            return true;
        }
        if (!isDisplayInDexMode(i)) {
            return false;
        }
        if (keyCode != 48) {
            if (keyCode != 61) {
                if (keyCode != 122) {
                    if (keyCode != 117 && keyCode != 118) {
                        switch (keyCode) {
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                                break;
                            default:
                                switch (keyCode) {
                                    case 19:
                                    case 20:
                                    case 21:
                                    case 22:
                                        if (keyEvent.isMetaPressed() && !hasMetaKeyPass() && z) {
                                            this.mWindowManagerFuncs.handleDexDpadShortcut(iBinder, keyEvent);
                                            return true;
                                        }
                                        break;
                                    default:
                                        switch (keyCode) {
                                            case 1085:
                                            case 1086:
                                            case 1087:
                                            case 1088:
                                            case 1089:
                                                sendThreeFingerGestureKeyEvent(keyEvent, i, true);
                                                return true;
                                        }
                                }
                        }
                    } else if (!z && !hasMetaKeyPass()) {
                        this.mWindowManagerFuncs.handleDexMetaKeyForSnapping();
                    }
                } else if (keyEvent.isMetaPressed() && !hasMetaKeyPass()) {
                    if (!z) {
                        return true;
                    }
                    MultiWindowManager.getInstance().minimizeAllTasks(i);
                    return true;
                }
            } else if (z && repeatCount == 0 && keyEvent.isMetaPressed() && !hasMetaKeyPass()) {
                this.mPolicy.toggleRecentApps(i);
                return true;
            }
            return false;
        }
        if (keyEvent.isMetaPressed()) {
            StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.mStatusBarManagerInternal;
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.sendKeyEventToDesktopTaskbarToType(keyEvent, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
            }
            if (!hasMetaKeyPass()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void onDexModeChangedLw(final int i) {
        if (this.mLastDexMode == i) {
            Slog.w("PhoneWindowManagerExt", "onDexModeChangedLw: called same dexMode=" + i);
            return;
        }
        this.mLastDexMode = i;
        if (i != 2) {
            this.mPendingDualModeInteractive = 0;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$onDexModeChangedLw$16(i);
            }
        });
    }

    /* renamed from: onPostDexModeChanged, reason: merged with bridge method [inline-methods] */
    public final void lambda$onDexModeChangedLw$16(int i) {
        InputManager inputManager;
        if (!this.mIsPogoKeyboardConnected || (inputManager = InputManager.getInstance()) == null) {
            return;
        }
        if (i == 0) {
            inputManager.semRegisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener, null);
        } else {
            inputManager.semUnregisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener);
        }
    }

    public int getDexMode() {
        return this.mLastDexMode;
    }

    public boolean isInDexMode() {
        return getDexMode() != 0;
    }

    public void addPendingDualModeInteractive(int i) {
        this.mPendingDualModeInteractive = i | this.mPendingDualModeInteractive;
    }

    public boolean handleGlobalInteractiveIfNeeded(int i) {
        int i2 = this.mPendingDualModeInteractive;
        boolean z = (i2 & i) != 0;
        this.mPendingDualModeInteractive = (~i) & i2;
        return z;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isImeBtnOnGestureEnabled() {
        return this.mNavBarImeBtnEnabled || this.mShowKeyboardBtnEnabled;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isNavGetureHintEnabled() {
        return this.mNavGetureHintEnabled;
    }

    public boolean isReserveBatteryMode() {
        return this.mReserveBatteryMode && this.mEnableReserveBatteryMode;
    }

    public final String getReserveBatteryModeToast(Intent intent) {
        return getToastString(getApplicationInfo(intent), 17042405);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void keyguardGoingAwayWithFingerprintUnlock(boolean z) {
        Slog.d("PhoneWindowManagerExt", "UnlockFP triggered. isWakeAndUnlock=" + z);
        if (z) {
            cancelWakeAndUnlockPendingAction();
            this.mWakeAndUnlockTriggered = true;
            this.mHandler.postDelayed(this.mWakeAndUnlockRunning, 1000L);
        }
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            sendFoldSaLoggingCanceledIfNeeded("Fingerprint Sensor", false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$18() {
        if (this.mWakeAndUnlockTriggered) {
            Slog.d("PhoneWindowManagerExt", "WakeAndUnlock not triggered");
            this.mWakeAndUnlockTriggered = false;
        }
    }

    public final void cancelWakeAndUnlockPendingAction() {
        this.mHandler.removeCallbacks(this.mWakeAndUnlockRunning);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$19() {
        if (this.mWakeAndUnfoldedTriggered) {
            Slog.d("PhoneWindowManagerExt", "WakeAndUnfolded not triggered");
            this.mWakeAndUnfoldedTriggered = false;
        }
    }

    public final void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent, int i, boolean z) {
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        Slog.d("PhoneWindowManagerExt", "sendThreeFingerGestureKeyEvent keyCode=" + keyEvent.getKeyCode());
        if (z) {
            statusBarManagerInternal.sendKeyEventToDesktopTaskbarToType(keyEvent, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
        } else {
            statusBarManagerInternal.sendThreeFingerGestureKeyEvent(keyEvent);
        }
    }

    public final void handleMultiFingerTap(int i) {
        Slog.d("PhoneWindowManagerExt", "handleMultiFingerTap behavior=" + i);
        if (i == 1) {
            startActivityAppsScreen();
            return;
        }
        try {
            if (i == 5) {
                IStatusBarService statusBarService = this.mPolicy.getStatusBarService();
                if (statusBarService == null) {
                } else {
                    statusBarService.expandNotificationsPanel();
                }
            } else if (i == 6) {
                IStatusBarService statusBarService2 = this.mPolicy.getStatusBarService();
                if (statusBarService2 == null) {
                } else {
                    statusBarService2.expandSettingsPanel((String) null);
                }
            } else {
                Slog.d("PhoneWindowManagerExt", "The MultiFingerTap type was wrong.");
            }
        } catch (RemoteException unused) {
        }
    }

    public final void startActivityAppsScreen() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        intent.putExtra("sec.android.intent.extra.LAUNCHER_ACTION", "com.android.launcher2.ALL_APPS");
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("PhoneWindowManagerExt", "No activity to launch launcher app list. ", e);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void handleNotifyPogoKeyboardStatus(boolean z) {
        Slog.d("PhoneWindowManagerExt", "handleNotifyPogoKeyboardStatus status=" + z);
        if (this.mIsPogoKeyboardConnected == z) {
            return;
        }
        this.mIsPogoKeyboardConnected = z;
        InputManager inputManager = InputManager.getInstance();
        if (inputManager == null) {
            return;
        }
        if (this.mIsPogoKeyboardConnected && !isInDexMode()) {
            inputManager.semRegisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener, null);
        } else {
            inputManager.semUnregisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener);
        }
    }

    public class OpeningTorch extends SideKeyDoublePress.Behavior {
        public OpeningTorch(String str) {
            super(str);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            this.mPolicyExt.onFlashlightKeyPressed(26);
            if (CoreRune.FW_KEY_SA_LOGGING) {
                this.mPolicyExt.sendCoreSaLoggingDimension("HWB1002", "Flashlight");
            }
        }
    }

    public void onFlashlightKeyPressed(int i) {
        Log.d("PhoneWindowManagerExt", "onFlashlightKeyPressed, keyCode=" + i);
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        statusBarManagerInternal.onFlashlightKeyPressed(i);
    }

    public boolean handleLongPressOnHomeWithPolicy(int i, long j) {
        if (longPressOnHomePolicy() || this.mPolicy.mLongPressOnHomeBehavior == 0) {
            return false;
        }
        if (!CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG || !isLongPressHomeSearcle()) {
            if (this.mIsAssistHapticEnabled) {
                this.mPolicy.performHapticFeedback(0, true, "Home - Long Press");
            } else {
                Slog.d("PhoneWindowManagerExt", "home long press haptic disabled");
            }
        }
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        int i2 = phoneWindowManager.mLongPressOnHomeBehavior;
        if (i2 == 1) {
            phoneWindowManager.launchAllAppsAction();
        } else if (i2 == 2) {
            phoneWindowManager.launchAssistAction(null, i, j, 5);
            if (CoreRune.FW_KEY_SA_LOGGING) {
                sendCoreSaLoggingDimension("NAVIB1003", assistantAppNameToString());
            }
        } else if (i2 == 3) {
            phoneWindowManager.toggleNotificationPanel();
        } else if (i2 == 4 || i2 == 101) {
            if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG) {
                startSearcleHomeLongPress();
            }
        } else {
            Log.w("PhoneWindowManagerExt", "Undefined long press on home behavior: " + this.mPolicy.mLongPressOnHomeBehavior);
        }
        return true;
    }

    public final boolean longPressOnHomePolicy() {
        if (!this.mPolicy.isUserSetupComplete()) {
            Log.i("PhoneWindowManagerExt", "Home long press is blocked because UserSetup isn't completed");
            return true;
        }
        TelecomManager telecommService = this.mPolicy.getTelecommService();
        if (!CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && telecommService != null && telecommService.isRinging()) {
            Log.i("PhoneWindowManagerExt", "Home long press is blocked by Ringing");
            return true;
        }
        if (!isInteractionControlEnabled(3)) {
            return false;
        }
        Log.i("PhoneWindowManagerExt", "Home long press is blocked by Interaction control");
        return true;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void notifyRequestedGameToolsWin(boolean z) {
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        statusBarManagerInternal.notifyRequestedGameToolsWin(z);
    }

    public void interceptStopLockTaskModePinnedChord() {
        boolean isLockTaskModeEnabled = isLockTaskModeEnabled();
        boolean isTouchExplorationEnabled = isTouchExplorationEnabled();
        boolean isInteractionControlEnabled = isInteractionControlEnabled();
        if (!isLockTaskModeEnabled || isTouchExplorationEnabled || isInteractionControlEnabled) {
            Log.d("PhoneWindowManagerExt", "Can not stop SystemLockTaskMode. lockTaskModeEnabled=" + isLockTaskModeEnabled + " touchExplorationEnabled=" + isTouchExplorationEnabled + " interactionControlEnabled=" + isInteractionControlEnabled);
            return;
        }
        this.mHandler.removeCallbacks(this.mStopLockTaskModePinnedChordLongPress);
        this.mHandler.postDelayed(this.mStopLockTaskModePinnedChordLongPress, 500L);
    }

    public static /* synthetic */ void lambda$new$20() {
        Log.d("PhoneWindowManagerExt", "Stop Lock Task Mode");
        try {
            ActivityTaskManager.getService().stopSystemLockTaskMode();
        } catch (RemoteException e) {
            Log.e("PhoneWindowManagerExt", "Unable to reach activity manager, " + e);
        }
    }

    public void cancelPendingLockTaskModePinnedChordAction() {
        this.mHandler.removeCallbacks(this.mStopLockTaskModePinnedChordLongPress);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isInteractionControlEnabled() {
        return this.mIsInteractionControlEnabled;
    }

    public final boolean isTouchExplorationEnabled() {
        return this.mPolicy.mAccessibilityManager.isEnabled() && this.mPolicy.mAccessibilityManager.isTouchExplorationEnabled();
    }

    public final void registerDisplayListener() {
        final WindowManagerInternal.AppTransitionListener appTransitionListener = new WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.11
            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public int onAppTransitionStartingLocked(long j, long j2) {
                return PhoneWindowManagerExt.this.handleStartTransitionForKeyguardLw(false, false, 2);
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionCancelledLocked(boolean z) {
                PhoneWindowManagerExt.this.handleStartTransitionForKeyguardLw(z, true, 2);
            }
        };
        this.mPolicy.mDisplayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.12
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
                if (i == 2) {
                    PhoneWindowManagerExt.this.mPolicy.mWindowManagerInternal.registerAppTransitionListener(appTransitionListener, i);
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
                if (i == 2) {
                    PhoneWindowManagerExt.this.mPolicy.mWindowManagerInternal.unregisterAppTransitionListener(appTransitionListener, i);
                }
            }
        }, this.mHandler);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isKeyguardOccluded(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mKeyguardDelegate == null) {
            return false;
        }
        if (i == 2) {
            return this.mDexKeyguardOccluded;
        }
        return phoneWindowManager.isKeyguardOccluded();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void onKeyguardOccludedChangedLw(boolean z, int i, boolean z2) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mKeyguardDelegate != null && i == 2) {
            setKeyguardOccludedLw(z, true, i);
        } else {
            phoneWindowManager.onKeyguardOccludedChangedLw(z);
        }
    }

    public boolean isKeyguardShowingAndNotOccluded(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        KeyguardServiceDelegate keyguardServiceDelegate = phoneWindowManager.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return false;
        }
        if (i == 2) {
            return keyguardServiceDelegate.isShowing() && !this.mDexKeyguardOccluded;
        }
        return phoneWindowManager.isKeyguardShowingAndNotOccluded();
    }

    public boolean checkKeyguardOccluded(int i, boolean z) {
        if (z && i == -1) {
            return this.mPolicy.isKeyguardOccluded();
        }
        if (i == 2) {
            return this.mDexKeyguardOccluded;
        }
        if (i != 0) {
            return false;
        }
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mKeyguardOccludedChanged) {
            return phoneWindowManager.mPendingKeyguardOccluded;
        }
        return phoneWindowManager.isKeyguardOccluded();
    }

    public final int handleStartTransitionForKeyguardLw(boolean z, boolean z2, int i) {
        if (i == 2) {
            if (this.mDexKeyguardOccludedChanged) {
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Slog.d("PhoneWindowManagerExt", "dex transition/occluded changed occluded=" + this.mPendingDexKeyguardOccluded);
                }
                this.mDexKeyguardOccludedChanged = false;
                if (setKeyguardOccludedLw(this.mPendingDexKeyguardOccluded, z2, i)) {
                    return 5;
                }
            }
            if (z) {
                if (KeyCustomizationConstants.isSafeDebugInput()) {
                    Slog.d("PhoneWindowManagerExt", "Starting dex keyguard exit animation");
                }
                this.mPolicy.startKeyguardExitAnimation(SystemClock.uptimeMillis());
            }
            return 0;
        }
        return this.mPolicy.handleTransitionForKeyguardLw(z, z2);
    }

    public final boolean setKeyguardOccludedLw(boolean z, boolean z2, int i) {
        if (this.mPolicy.mKeyguardDelegate == null) {
            Slog.d("PhoneWindowManagerExt", "setKeyguardOccludedLw mKeyguardDelegate is null");
            return false;
        }
        if (PhoneWindowManager.DEBUG_KEYGUARD) {
            Slog.d("PhoneWindowManagerExt", "setKeyguardOccluded occluded=" + z);
        }
        this.mPolicy.mKeyguardOccludedChanged = false;
        if (isKeyguardOccluded(i) == z) {
            return false;
        }
        if (i == 2) {
            this.mDexKeyguardOccluded = z;
        }
        this.mPolicy.mKeyguardDelegate.setOccluded(z, z2, i);
        return this.mPolicy.mKeyguardDelegate.isShowing();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void onLockTaskFeaturesChanged(SparseIntArray sparseIntArray) {
        this.mLockTaskFeatures = sparseIntArray;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void onLockTaskStateChanged(int i) {
        this.mLockTaskModeState = i;
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public boolean isLockTaskModePinned() {
        return this.mLockTaskModeState == 2;
    }

    public final boolean isLockTaskModeEnabled() {
        return this.mLockTaskModeState != 0;
    }

    public final int getLockTaskFeaturesForUser(int i) {
        return this.mLockTaskFeatures.get(i, 0);
    }

    public final boolean isRecentsAllowed(int i) {
        return (getLockTaskFeaturesForUser(i) & 8) != 0;
    }

    public final boolean isHomeAllowed(int i) {
        return this.mPolicy.hasNavigationBar() || (getLockTaskFeaturesForUser(i) & 4) != 0;
    }

    public void requestCustomFullBugreport(String str) {
        if (this.mIssueTrackerLoggedIn) {
            Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
            intent.putExtra("ERRNAME", "User pressed h/w key for logging");
            intent.putExtra("ERRCODE", -131);
            intent.putExtra("ERRMSG", "User pressed h/w key for logging");
            intent.putExtra("lockScreen", true);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            Slog.d("PhoneWindowManagerExt", "sendBroadcast to issuetracker.");
            return;
        }
        if ("1".equals(SystemProperties.get("dumpstate.is_running", "0"))) {
            Slog.d("PhoneWindowManagerExt", "Running dump due to requestCustomFullBugreport.");
            return;
        }
        Slog.d("PhoneWindowManagerExt", "Called requestCustomFullBugreport, reason=" + str);
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.lambda$requestCustomFullBugreport$21();
            }
        });
    }

    public static /* synthetic */ void lambda$requestCustomFullBugreport$21() {
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).requestCustomFullBugreport();
    }

    public boolean canRequestBugReport() {
        return (CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH || this.mIsCustomBugreportWriterEnabled || this.mIssueTrackerLoggedIn) && this.mIsVolumeUpKeyPressed;
    }

    public void acceptRingingCall(final int i) {
        consumeIfSemTelecomManagerNonNull(new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SemTelecomManager) obj).acceptRingingCall(i);
            }
        });
    }

    public void silenceRinger(final int i) {
        consumeIfSemTelecomManagerNonNull(new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SemTelecomManager) obj).silenceRinger(i);
            }
        });
    }

    public void endCall(final int i) {
        consumeIfSemTelecomManagerNonNull(new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SemTelecomManager) obj).endCall(i);
            }
        });
    }

    public final void consumeIfSemTelecomManagerNonNull(Consumer consumer) {
        SemTelecomManager semTelecomManager = (SemTelecomManager) SafetySystemService.getSystemService(SemTelecomManager.class);
        if (semTelecomManager == null) {
            return;
        }
        consumer.accept(semTelecomManager);
    }

    public final String assistantAppNameToString() {
        if (TextUtils.isEmpty(this.mAssistantAppName)) {
            return "None";
        }
        String str = this.mAssistantAppName;
        str.hashCode();
        switch (str) {
            case "com.sec.android.app.sbrowser/.SBrowserMainActivity":
                return "Samsung internet";
            case "com.samsung.android.bixby.agent/.mainui.voiceinteraction.MainVoiceInteractionService":
                return "Bixby voice";
            case "com.google.android.googlequicksearchbox/com.google.android.voiceinteraction.GsaVoiceInteractionService":
                return "Google assistant";
            case "com.sec.android.app.launcher/.search.SearchActivity":
                return "Finder";
            default:
                return this.mAssistantAppName;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0055 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void sendSALoggingForDispatchingKey(KeyEvent keyEvent, int i) {
        String str;
        int keyCode = keyEvent.getKeyCode();
        InputDevice device = keyEvent.getDevice();
        if (device == null || device.isExternal()) {
            return;
        }
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 4) {
            return;
        }
        boolean z2 = (i & 16777216) != 0;
        if ((keyCode != 24 && keyCode != 25) || z2) {
            return;
        }
        int repeatCount = keyEvent.getRepeatCount();
        boolean isLongPress = keyEvent.isLongPress();
        if (z) {
            if (repeatCount == 0) {
                this.mVolumeKeyLongPress = false;
            }
            if (isLongPress) {
                this.mVolumeKeyLongPress = true;
                str = keyCode == 24 ? "HWB1013" : "HWB1015";
                if (TextUtils.isEmpty(str)) {
                    CoreSaLogger.logForBasic(str);
                    return;
                }
                return;
            }
        }
        str = null;
        if (TextUtils.isEmpty(str)) {
        }
    }

    public void sendCoreSaLoggingDimension(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("det", str2);
        CoreSaLogger.logForBasic(str, hashMap);
    }

    public boolean handleTorchForXCoverKey(boolean z, int i) {
        if (this.mPolicy.isKeyguardLocked() && !z) {
            return false;
        }
        onFlashlightKeyPressed(i);
        return true;
    }

    public void clearAppLockedUnLockedApp() {
        try {
            ActivityTaskManager.getService().clearAppLockedUnLockedApp();
        } catch (RemoteException e) {
            Log.e("PhoneWindowManagerExt", " ClearAppLockedUnLockedApp failed , Remote exception ", e);
        }
    }

    public final void startActivityPremiumWatch(boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(268500992);
        intent.setComponent(ComponentName.unflattenFromString("com.sec.android.app.premiumwatch/com.sec.android.app.premiumwatch.activity.PremiumWatch"));
        if (z) {
            intent.putExtra("doubleTap", true);
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setSplashScreenStyle(0);
        makeBasic.setNoTransitionOcclusion();
        Slog.d("PhoneWindowManagerExt", "launch Premium watch" + intent + " isDoubleTapOnScreen=" + z);
        try {
            this.mPolicy.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Slog.w("PhoneWindowManagerExt", "No activity to launch Premium watch. ", e);
        }
    }

    public void doubleTapLaunchPremiumWatch() {
        if (this.mIsDoubleTapPremiumWatchOn) {
            if (!this.mPolicy.isUserSetupComplete()) {
                Log.v("PhoneWindowManagerExt", "Can not launch premium watch. UserSetup is not completed.");
            } else {
                startActivityPremiumWatch(true);
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public List getBackupKeyCustomizationInfoList() {
        return this.mKeyCustomizationPolicy.getBackupKeyCustomizationInfoList();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void restoreKeyCustomizationInfo(List list) {
        this.mKeyCustomizationPolicy.restoreKeyCustomizationInfo(list);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void showBootDialog(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$showBootDialog$26(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBootDialog$26(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mBootMsgDialog == null) {
            phoneWindowManager.mBootMsgDialog = createBootProgressDialog(this.mContext, false);
        }
        this.mPolicy.mBootMsgDialog.setMessage(i + "%");
        this.mPolicy.mBootMsgDialog.setProgress(i);
    }

    public final BootProgressDialog createBootProgressDialog(Context context, boolean z) {
        BootProgressDialog bootProgressDialog = new BootProgressDialog(context) { // from class: com.android.server.policy.PhoneWindowManagerExt.14
            public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
                return true;
            }

            public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                return true;
            }

            public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
                return true;
            }

            public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                return true;
            }

            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                return true;
            }

            public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
                return true;
            }
        };
        bootProgressDialog.setProgressStyle(0);
        bootProgressDialog.setIndeterminate(false);
        bootProgressDialog.getWindow().setType(2021);
        bootProgressDialog.getWindow().addFlags(258);
        bootProgressDialog.getWindow().setDimAmount(1.0f);
        WindowManager.LayoutParams attributes = bootProgressDialog.getWindow().getAttributes();
        attributes.screenOrientation = 0;
        attributes.setTitle("BootProgressDialog_d" + context.getDisplayId());
        attributes.layoutInDisplayCutoutMode = 1;
        bootProgressDialog.setMax(100);
        bootProgressDialog.getWindow().setAttributes(attributes);
        bootProgressDialog.setCancelable(false);
        bootProgressDialog.show();
        return bootProgressDialog;
    }

    public void sendFoldSaLoggingCanceledIfNeeded(String str, boolean z) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mSystemBooted) {
            if (z || phoneWindowManager.mDefaultDisplayPolicy.getLidState() == 0) {
                synchronized (this.mFoldSaLock) {
                    if (!this.mFoldSaLoggingCanceled && this.mFoldedTime != 0 && SystemClock.uptimeMillis() - this.mFoldedTime <= 10000) {
                        this.mFoldSaLoggingCanceled = true;
                        CoreSaLogger.logForBasic("W003", str);
                        sendFoldSaLogging(true);
                    }
                }
            }
        }
    }

    public final void sendFoldSaLogging(final boolean z) {
        final AlarmManager alarmManager = (AlarmManager) SafetySystemService.getSystemService(AlarmManager.class);
        if (alarmManager == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                PhoneWindowManagerExt.this.lambda$sendFoldSaLogging$27(z, alarmManager);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendFoldSaLogging$27(boolean z, AlarmManager alarmManager) {
        if (z) {
            PendingIntent pendingIntent = this.mAlarmPendingIntent;
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
                return;
            }
            return;
        }
        if (this.mAlarmPendingIntent == null) {
            this.mAlarmPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.intent.action.WINNER_LOGGING"), 67108864);
        }
        alarmManager.setExactAndAllowWhileIdle(0, System.currentTimeMillis() + 10000, this.mAlarmPendingIntent);
    }

    public void startSearcleHomeKeyPress(boolean z) {
        startSearcleByHomeKey(z, false);
    }

    public final void startSearcleHomeLongPress() {
        startSearcleByHomeKey(false, true);
    }

    public final void startSearcleByHomeKey(boolean z, boolean z2) {
        if (this.mPolicy.keyguardOn()) {
            Log.d("PhoneWindowManagerExt", "keyguardOn, can not start Searcle by Home");
            return;
        }
        if (this.mKeyCustomizationPolicy.hasLastInfo(4, 3)) {
            Log.d("PhoneWindowManagerExt", "hasLastInfo, can not start Searcle by Home");
            return;
        }
        if (this.mSystemKeyPolicy.hasSystemKeyInfo(3, 4)) {
            Log.d("PhoneWindowManagerExt", "hasSystemKeyInfo, can not start Searcle by Home");
            return;
        }
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        Log.d("PhoneWindowManagerExt", "startSearcleByHomeKey, down=" + z + " longPress=" + z2);
        statusBarManagerInternal.startSearcleByHomeKey(z, z2);
    }

    public boolean isLongPressHomeSearcle() {
        int i = this.mPolicy.mLongPressOnHomeBehavior;
        return i == 4 || i == 101;
    }

    public void showingSearcleToastIfNeeded() {
        if (!isLongPressHomeSearcle() || longPressOnHomePolicy()) {
            return;
        }
        String searcleToast = getSearcleToast();
        if (TextUtils.isEmpty(searcleToast)) {
            Log.d("PhoneWindowManagerExt", "The toast message is empty");
        } else {
            showToast(this.mContext, searcleToast);
        }
    }

    public final String getSearcleToast() {
        Resources resources = this.mContext.getResources();
        int i = this.mPolicy.mLongPressOnHomeBehavior;
        if (i == 4) {
            return resources.getString(R.string.config_hdmiCecSetMenuLanguageActivity);
        }
        if (i != 101) {
            return null;
        }
        return resources.getString(17042356, resources.getString(17043035));
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println("");
        printWriter.print(str);
        printWriter.println("--- PhoneWindowManagerExt ---");
        this.mKeyCustomizationPolicy.dump(str, printWriter);
        printWriter.print(str);
        printWriter.print("maxMultiPressPowerCount=");
        printWriter.println(this.mPolicy.getMaxMultiPressPowerCount());
        printWriter.print(str);
        printWriter.print("mQuadruplePressOnPowerBehavior=");
        printWriter.println(PhoneWindowManager.multiPressOnPowerBehaviorToString(this.mQuadruplePressOnPowerBehavior));
        this.mSystemKeyPolicy.dump(str, printWriter);
        if (CoreRune.FW_SUPPORT_DICTATION_SAMSUNG_KEYBOARD) {
            printWriter.print(str);
            printWriter.print("mIsCallOpenDictation=");
            printWriter.println(this.mIsCallOpenDictation);
        }
        if (CoreRune.FW_QUICK_LAUNCH_CAMERA) {
            printWriter.print(str);
            printWriter.print("mQuickLaunchCameraBehavior=");
            printWriter.println(this.mQuickLaunchCameraBehavior);
        }
        if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
            printWriter.print(str);
            printWriter.print("mIsScreenshotTriggered=");
            printWriter.print(this.mIsScreenshotTriggered);
            printWriter.print(" mGlobalActionsByKeyCombnation=");
            printWriter.println(this.mGlobalActionsByKeyCombnation);
        }
        printWriter.print(str);
        printWriter.print("bixbyComponentName=");
        printWriter.println(this.mBixbyService.getComponentName());
        if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
            printWriter.print(str);
            printWriter.print("mQuintuplePressOnPowerBehavior=");
            printWriter.println(PhoneWindowManager.multiPressOnPowerBehaviorToString(this.mQuintuplePressOnPowerBehavior));
        }
        printWriter.print(str);
        printWriter.print("mIsHapticsEnabled=");
        printWriter.print(this.mIsHapticsEnabled);
        printWriter.print(" mIsAssistHapticEnabled=");
        printWriter.print(this.mIsAssistHapticEnabled);
        printWriter.print(" mIsHapticsSupported=");
        printWriter.print(this.mIsHapticsSupported);
        printWriter.print(" mNavBarVirtualKeyHapticFeedbackEnabled=");
        printWriter.println(this.mPolicy.mNavBarVirtualKeyHapticFeedbackEnabled);
        printWriter.print(str);
        printWriter.print("cameraSensorToggleSupported=");
        printWriter.print(this.mIsCameraSensorToggleSupported);
        printWriter.print(str);
        printWriter.print("micSensorToggleSupported=");
        printWriter.println(this.mIsMicSensorToggleSupported);
        if (CoreRune.FW_SPEN) {
            printWriter.print(str);
            printWriter.print("SPen state=");
            printWriter.print(this.mPenState);
            printWriter.print(" type=");
            printWriter.print(this.mPenType);
            printWriter.print(" vibrationEnabled=");
            printWriter.print(this.mPenVibrationEnabled);
            printWriter.print(" soundEnabled=");
            printWriter.println(this.mPenSoundEnabled);
            if (this.mPenSoundInfo == null) {
                printWriter.print(str);
                printWriter.println("SoundInfo is null");
            } else {
                printWriter.print(str);
                printWriter.print("PenSoundInfo attachSoundPath=");
                printWriter.print(this.mPenSoundInfo.mAttachPenSoundPath);
                printWriter.print(" detachSoundPath=");
                printWriter.print(this.mPenSoundInfo.mDetachPenSoundPath);
                printWriter.print(" attachSoundId=");
                printWriter.print(this.mPenSoundInfo.mPenAttachSoundId);
                printWriter.print(" detachSoundId=");
                printWriter.println(this.mPenSoundInfo.mPenDetachSoundId);
            }
        }
        if (CoreRune.FW_SPEN_SCREEN_OFF_MEMO) {
            printWriter.print(str);
            printWriter.print("mScreenOffMemoEnabled=");
            printWriter.println(this.mScreenOffMemoEnabled);
        }
        printWriter.print(str);
        printWriter.print("mIsInteractionControlEnabled=");
        printWriter.print(this.mIsInteractionControlEnabled);
        printWriter.print(" mIsPowerKeyBlocked=");
        printWriter.print(this.mIsPowerKeyBlocked);
        printWriter.print(" mIsVolumeKeyBlocked=");
        printWriter.println(this.mIsVolumeKeyBlocked);
        printWriter.print(str);
        printWriter.print("mIsDoubleTapToWakeUpSupported=");
        printWriter.print(this.mIsDoubleTapToWakeUpSupported);
        printWriter.print(" mIsDoubleTapToWakeUp=");
        printWriter.println(this.mIsDoubleTapToWakeUp);
        if (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE) {
            printWriter.print(str);
            printWriter.print("mReserveBatteryMode=");
            printWriter.print(this.mReserveBatteryMode);
            printWriter.print(" mEnableReserveBatteryMode=");
            printWriter.println(this.mEnableReserveBatteryMode);
        }
        if (CoreRune.FW_FINGERPRINT_SIDE_TOUCH) {
            printWriter.print(str);
            printWriter.print("mWakeAndUnlockTriggered=");
            printWriter.print(this.mWakeAndUnlockTriggered);
            printWriter.print(" mWakeAndUnfoldedTriggered=");
            printWriter.println(this.mWakeAndUnfoldedTriggered);
        }
        printWriter.print(str);
        printWriter.print("mIsPogoKeyboardConnected=");
        printWriter.println(this.mIsPogoKeyboardConnected);
        printWriter.print(str);
        printWriter.print("mLockTaskModeState=");
        printWriter.println(this.mLockTaskModeState);
        printWriter.print(str);
        printWriter.print("mDexKeyguardOccluded=");
        printWriter.print(this.mDexKeyguardOccluded);
        printWriter.print(" mDexKeyguardOccludedChanged=");
        printWriter.print(this.mDexKeyguardOccludedChanged);
        printWriter.print(" mPendingDexKeyguardOccluded=");
        printWriter.println(this.mPendingDexKeyguardOccluded);
        printWriter.print(str);
        printWriter.print("mWakingUpReason=");
        printWriter.println(PowerManager.wakeReasonToString(this.mWakingUpReason));
        if (CoreRune.FW_XCOVER_AND_TOP_KEY) {
            printWriter.print(str);
            printWriter.print("mIsCalledOpenDictationXCoverTop=");
            printWriter.println(this.mKeyCustomizationPolicy.mIsCalledOpenDictationXCoverTop);
        }
    }

    public void adjustConfigurationLw(Configuration configuration) {
        int i = this.mButtonShapeEnabled;
        if (i != configuration.semButtonShapeEnabled) {
            configuration.semButtonShapeEnabled = i;
        }
        int i2 = this.mBoldFontEnabled;
        if (i2 != configuration.boldFont) {
            configuration.boldFont = i2;
        }
        float f = this.mCursorThicknessScale;
        if (f != configuration.semCursorThicknessScale) {
            configuration.semCursorThicknessScale = f;
        }
    }

    public void onHomeChangedBooster() {
        ActivityManagerPerformance booster = ActivityManagerPerformance.getBooster();
        if (booster != null) {
            booster.isHomeKeyPressed();
        }
    }

    public final void registerSystemUIReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(new C230116(), intentFilter);
    }

    /* renamed from: com.android.server.policy.PhoneWindowManagerExt$16 */
    public class C230116 extends BroadcastReceiver {
        public C230116() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, Intent intent) {
            if (intent == null || !"android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                return;
            }
            Uri data = intent.getData();
            final ComponentName systemUiServiceComponent = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getSystemUiServiceComponent();
            String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
            if (context == null || !systemUiServiceComponent.getPackageName().equals(schemeSpecificPart)) {
                return;
            }
            PhoneWindowManagerExt.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneWindowManagerExt.C230116.this.lambda$onReceive$0(context, systemUiServiceComponent);
                }
            }, 1000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Context context, ComponentName componentName) {
            context.startServiceAsUser(new Intent().setComponent(componentName).addFlags(256), UserHandle.SYSTEM);
            PhoneWindowManagerExt.this.bindKeyguardOnPkgChanged();
        }
    }

    public final void bindKeyguardOnPkgChanged() {
        if (this.mPolicy.mKeyguardDelegate == null) {
            return;
        }
        Log.d("PhoneWindowManagerExt", "bind KeyguardService due to updating SystemUI pkg");
        this.mPolicy.mKeyguardDelegate.bindService(this.mContext);
        this.mPolicy.mKeyguardDelegate.onBootCompleted();
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void launchHomeForDesktopMode(int i) {
        this.mPolicy.launchHomeFromHotKey(i, true, false);
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void startLockscreenFingerprintAuth() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.startFingerprintAuthentication();
        }
    }

    public void startedEarlyWakingUp(int i) {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate != null) {
            keyguardServiceDelegate.startedEarlyWakingUp(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicyExt
    public void setCarLifeDisplay(WindowManagerPolicy.DisplayContentInfo displayContentInfo) {
        this.mCarLifeDisplay = displayContentInfo.getDisplay();
        this.mCarLifeDisplayPolicy = displayContentInfo.getDisplayRotation().getDisplayPolicy();
    }

    public boolean isGlobalActionsDialogShowing() {
        GlobalActions globalActions = this.mPolicy.mGlobalActions;
        return globalActions != null && globalActions.isDialogShowing();
    }

    public boolean isGlobalActionsDialogPowerOptionHidden() {
        GlobalActions globalActions = this.mPolicy.mGlobalActions;
        return globalActions != null && globalActions.isDialogPowerOptionHidden();
    }

    public final boolean knoxCustomVolumeKeyAppSwitching(boolean z) {
        int size;
        ProKioskManager proKioskManager = this.mProKioskManager;
        if (proKioskManager == null || !proKioskManager.getProKioskState() || !this.mProKioskManager.getVolumeKeyAppState()) {
            return false;
        }
        if (isGlobalActionsDialogShowing()) {
            Log.d("PhoneWindowManagerExt", "Knox Custom: GlobalActions dialog showing; not doing Volume Key app switching");
            if (!z || !isGlobalActionsDialogPowerOptionHidden()) {
                return true;
            }
            Log.d("PhoneWindowManagerExt", "Knox Custom: GlobalActions dialog showing; forward the key for Power option display");
            return false;
        }
        Log.d("PhoneWindowManagerExt", "Knox Custom: Volume Key app switching starting");
        List<String> volumeKeyAppsList = this.mProKioskManager.getVolumeKeyAppsList();
        if (volumeKeyAppsList == null || volumeKeyAppsList.size() == 0) {
            Log.d("PhoneWindowManagerExt", "Knox Custom: no apps in list");
            return true;
        }
        Log.d("PhoneWindowManagerExt", "Knox Custom: " + volumeKeyAppsList.size() + " apps in list");
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        PackageManager packageManager = this.mContext.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (String str : volumeKeyAppsList) {
            if (packageManager.getLaunchIntentForPackage(str) != null) {
                Log.d("PhoneWindowManagerExt", "Knox Custom: " + str + " available");
                arrayList.add(str);
            } else {
                Log.d("PhoneWindowManagerExt", "Knox Custom: " + str + " not available");
            }
        }
        if (arrayList.size() == 0) {
            Log.d("PhoneWindowManagerExt", "Knox Custom: no available apps");
        } else {
            String str2 = activityManager.getRunningTasks(Integer.MAX_VALUE).get(0).topActivity.getPackageName().toString();
            Log.d("PhoneWindowManagerExt", "Knox Custom: " + str2 + " in foreground");
            int i = -1;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str2.equalsIgnoreCase((String) arrayList.get(i2))) {
                    Log.d("PhoneWindowManagerExt", "Knox Custom: " + str2 + " found at index " + i2);
                    i = i2;
                }
            }
            if (i == -1) {
                Log.d("PhoneWindowManagerExt", "Knox Custom: " + str2 + " not found in list; use first");
            }
            if (!z || i >= arrayList.size() - 1) {
                size = i > 0 ? i - 1 : arrayList.size() - 1;
            } else {
                size = i + 1;
            }
            String str3 = (String) arrayList.get(size);
            if (str3.equalsIgnoreCase(str2)) {
                Log.d("PhoneWindowManagerExt", "Knox Custom: " + str2 + " already in foreground");
            } else {
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str3);
                Log.d("PhoneWindowManagerExt", "Knox Custom: switching to " + str3);
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.addFlags(272629760);
                    try {
                        this.mContext.startActivityAsUser(launchIntentForPackage, UserHandle.CURRENT);
                    } catch (ActivityNotFoundException e) {
                        Slog.w("PhoneWindowManagerExt", "No activity to launch Knox Custom switching.", e);
                    }
                }
            }
        }
        Log.d("PhoneWindowManagerExt", "Knox Custom: Volume Key app switching done");
        return true;
    }
}
