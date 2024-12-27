package com.android.server.policy;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.BootProgressDialog;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
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
import android.os.Vibrator;
import android.os.VibratorManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.HapticFeedbackConstants;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.internal.accessibility.AccessibilityDirectAccessController;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.GestureLauncherService;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.knox.ContainerDependencyWrapper;
import com.android.server.pm.PersonaManagerService;
import com.android.server.policy.BixbyService;
import com.android.server.policy.KeyCustomizationConstants;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.SideKeyDoublePress;
import com.android.server.policy.SingleKeyGestureDetector;
import com.android.server.policy.SystemKeyManager;
import com.android.server.policy.keyguard.KeyguardServiceDelegate;
import com.android.server.policy.keyguard.KeyguardServiceWrapper;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.vibrator.HapticFeedbackVibrationProvider;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.TspStateController;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowManagerServiceExt;
import com.android.server.wm.WmCoverState;
import com.samsung.android.app.SemRoleManager;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.InputRune;
import com.samsung.android.server.util.SafetySystemService;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class PhoneWindowManagerExt {
    public static final Set KEYCODE_DEBUG_LOG_ALLOWLIST;
    public static final Set KEYCODE_KEY_COMBINATION_ALLOWLIST;
    public boolean mAcceptCallHomeConsumed;
    public AccessibilityDirectAccessController mAccessibilityDirectAccessController;
    public AppOpsManager mAppOpsManager;
    public boolean mAppSwitchKeyConsumed;
    public AudioManager mAudioManager;
    public BixbyService mBixbyService;
    public int mBoldFontEnabled;
    public final ArrayList mBootMsgDialogs;
    public int mButtonShapeEnabled;
    public final Context mContext;
    public float mCursorThicknessScale;
    public volatile boolean mDexKeyguardOccluded;
    public final AnonymousClass10 mDrmEventObserver;
    public final AnonymousClass10 mExtEventObserver;
    public final Object mFoldSaLock;
    public final AnonymousClass16 mFoldSaLoggingReceiver;
    public boolean mGlobalActionsByKeyCombnation;
    public PolicyExtHandler mHandler;
    public HapticFeedbackVibrationProvider mHapticFeedbackVibrationProvider;
    public HotKey mHotKey;
    public boolean mIsAccessibilityShortcutVolupPower;
    public boolean mIsAnyKeyMode;
    public boolean mIsAssistHapticEnabled;
    public boolean mIsCallOpenDictation;
    public boolean mIsCameraSensorToggleSupported;
    public boolean mIsCustomBugreportWriterEnabled;
    public boolean mIsDoubleTapPremiumWatchOn;
    public boolean mIsDoubleTapToWakeUp;
    public boolean mIsDoubleTapToWakeUpSupported;
    public boolean mIsHapticsEnabled;
    public boolean mIsHapticsSupported;
    public boolean mIsInteractionControlEnabled;
    public boolean mIsMicSensorToggleSupported;
    public boolean mIsPogoKeyboardConnected;
    public boolean mIsPowerKeyBlocked;
    public boolean mIsSamsungKeyboard;
    public boolean mIsScreenshotTriggered;
    public boolean mIsSktPhoneRelaxMode;
    public boolean mIsVolumeKeyBlocked;
    public boolean mIsVolumeUpKeyMode;
    public boolean mIsVolumeUpKeyPressed;
    public final KeyCustomizationManager mKeyCustomizationPolicy;
    public final long[] mKeyUpTime;
    public KeyboardShortcutManager mKeyboardShortcutPolicy;
    public LockPatternUtils mLockPatternUtils;
    public SparseIntArray mLockTaskFeatures;
    public int mLockTaskModeState;
    public final AnonymousClass12 mMultiFingerGestureListener;
    public final AnonymousClass2 mMultiuserReceiver;
    public boolean mNavBarImeBtnEnabled;
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda2 mPenDetachNotiConsumer;
    public Intent mPenInsertIntent;
    public boolean mPenSoundEnabled;
    public String mPenSoundFilePath;
    public PenSoundInfo mPenSoundInfo;
    public int mPenState;
    public final int mPenType;
    public boolean mPenVibrationEnabled;
    public int mPendingDualModeInteractive;
    public PersonaManagerService mPersonaManagerService;
    public final PhoneWindowManager mPolicy;
    public ProKioskManager mProKioskManager;
    public final AnonymousClass2 mProximityChangeReceiver;
    public int mQuadruplePressOnPowerBehavior;
    public int mQuickLaunchCameraBehavior;
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda2 mQuickLaunchCameraConsumer;
    public int mQuintuplePressOnPowerBehavior;
    public boolean mScreenOffMemoEnabled;
    public Intent mScreenOffMemoIntent;
    public boolean mScreenshotEnabled;
    public long mScreenshotTriggeredTime;
    public final Object mServiceAcquireLock;
    public SettingsObserver mSettingsObserver;
    public final AnonymousClass2 mSetupWizardGlobalActionReceiver;
    public boolean mShowKeyboardBtnEnabled;
    public SpenGestureManager mSpenGestureManager;
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda8 mStopLockTaskModePinnedChordLongPress;
    public SystemKeyManager mSystemKeyPolicy;
    public ComponentName mTopActivity;
    public TspStateController mTspStateController;
    public Vibrator mVibrator;
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda1 mWakeAndUnlockRunning;
    public boolean mWakeAndUnlockTriggered;
    public final WindowManagerServiceExt mWindowManagerFuncs;
    public final Object mLock = new Object();
    public int mLastDexMode = 0;
    public int mWakingUpReason = 0;
    public boolean mIssueTrackerLoggedIn = false;
    public boolean mIsLastGesture3FingerBottom = false;
    public boolean mShownEsc = false;
    public AlertDialog escDialog = null;
    public boolean mBootCompleted = false;
    public final AnonymousClass2 mBootCompleteReceiver = new AnonymousClass2(this, 0);
    public Toast mToast = null;
    public Thread mKeyEventInjectionThread = null;
    public final AnonymousClass2 mPackageChangeReceiver = new AnonymousClass2(this, 2);
    public boolean mTvModeEnabled = false;
    public boolean mTvModeDoublePressEnabled = false;
    public ComponentName mDoublePressLaunchComponentName = null;
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda2 mTvModeStateConsumer = new PhoneWindowManagerExt$$ExternalSyntheticLambda2(0, this);
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda2 mTvModeStateDoublePressConsumer = new PhoneWindowManagerExt$$ExternalSyntheticLambda2(1, this);
    public final PhoneWindowManagerExt$$ExternalSyntheticLambda2 mDoublePressLaunchComponentConsumer = new PhoneWindowManagerExt$$ExternalSyntheticLambda2(2, this);

    /* renamed from: com.android.server.policy.PhoneWindowManagerExt$15, reason: invalid class name */
    public final class AnonymousClass15 extends BootProgressDialog {
        public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
            return true;
        }

        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return true;
        }

        public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return true;
        }

        public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            return true;
        }

        public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return true;
        }

        public final boolean dispatchTrackballEvent(MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: com.android.server.policy.PhoneWindowManagerExt$16, reason: invalid class name */
    public final class AnonymousClass16 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            CoreSaLogger.logForBasic("W003", "No Action");
        }
    }

    /* renamed from: com.android.server.policy.PhoneWindowManagerExt$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PhoneWindowManagerExt this$0;

        public /* synthetic */ AnonymousClass2(PhoneWindowManagerExt phoneWindowManagerExt, int i) {
            this.$r8$classId = i;
            this.this$0 = phoneWindowManagerExt;
        }

        /* JADX WARN: Code restructure failed: missing block: B:64:0x014e, code lost:
        
            if (r4.queryIntentActivities(r0, 327680).size() > 0) goto L60;
         */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r18, android.content.Intent r19) {
            /*
                Method dump skipped, instructions count: 840
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public final class HomeKeyRule extends SingleKeyGestureDetector.SingleKeyRule {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PhoneWindowManagerExt this$0;

        public HomeKeyRule(PhoneWindowManagerExt phoneWindowManagerExt, int i) {
            super(3);
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = phoneWindowManagerExt;
                    super(4);
                    break;
                case 2:
                    this.this$0 = phoneWindowManagerExt;
                    super(79);
                    break;
                case 3:
                    this.this$0 = phoneWindowManagerExt;
                    super(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
                    break;
                case 4:
                    this.this$0 = phoneWindowManagerExt;
                    super(1015);
                    break;
                case 5:
                    this.this$0 = phoneWindowManagerExt;
                    super(1079);
                    break;
                case 6:
                    this.this$0 = phoneWindowManagerExt;
                    super(25);
                    break;
                case 7:
                    this.this$0 = phoneWindowManagerExt;
                    super(24);
                    break;
                default:
                    this.this$0 = phoneWindowManagerExt;
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyDown(KeyEvent keyEvent) {
            switch (this.$r8$classId) {
                case 4:
                    this.mIsKeyLongPressed = false;
                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0;
                    KeyCustomizationManager keyCustomizationManager = phoneWindowManagerExt.mKeyCustomizationPolicy;
                    int i = this.mKeyCode;
                    if (keyCustomizationManager.getLastAction(3, i) == 2) {
                        phoneWindowManagerExt.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, i, this.mIsKeyLongPressed);
                    }
                    if (InputRune.PWM_XCOVER_AND_TOP_KEY && phoneWindowManagerExt.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent, keyEvent.getKeyCode());
                        break;
                    }
                    break;
                case 5:
                    this.mIsKeyLongPressed = false;
                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                    KeyCustomizationManager keyCustomizationManager2 = phoneWindowManagerExt2.mKeyCustomizationPolicy;
                    int i2 = this.mKeyCode;
                    if (keyCustomizationManager2.getLastAction(3, i2) == 2) {
                        phoneWindowManagerExt2.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, i2, this.mIsKeyLongPressed);
                    }
                    if (InputRune.PWM_XCOVER_AND_TOP_KEY && phoneWindowManagerExt2.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt2.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent, keyEvent.getKeyCode());
                        break;
                    }
                    break;
                default:
                    super.onKeyDown(keyEvent);
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onKeyUp(KeyEvent keyEvent) {
            switch (this.$r8$classId) {
                case 4:
                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0;
                    KeyCustomizationManager keyCustomizationManager = phoneWindowManagerExt.mKeyCustomizationPolicy;
                    int i = this.mKeyCode;
                    if (keyCustomizationManager.getLastAction(3, i) == 2) {
                        phoneWindowManagerExt.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, i, this.mIsKeyLongPressed);
                    }
                    if (InputRune.PWM_XCOVER_AND_TOP_KEY && phoneWindowManagerExt.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent, keyEvent.getKeyCode());
                        break;
                    }
                    break;
                case 5:
                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                    KeyCustomizationManager keyCustomizationManager2 = phoneWindowManagerExt2.mKeyCustomizationPolicy;
                    int i2 = this.mKeyCode;
                    if (keyCustomizationManager2.getLastAction(3, i2) == 2) {
                        phoneWindowManagerExt2.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, i2, this.mIsKeyLongPressed);
                    }
                    if (InputRune.PWM_XCOVER_AND_TOP_KEY && phoneWindowManagerExt2.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt2.mKeyCustomizationPolicy.prepareVoiceToTextMessage(keyEvent, keyEvent.getKeyCode());
                        break;
                    }
                    break;
                default:
                    super.onKeyUp(keyEvent);
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            boolean z;
            switch (this.$r8$classId) {
                case 0:
                    this.mIsKeyLongPressed = true;
                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0;
                    if (!phoneWindowManagerExt.mSystemKeyPolicy.hasSystemKeyInfo(this.mKeyCode, 4)) {
                        if (!phoneWindowManagerExt.mKeyCustomizationPolicy.launchLongPressAction(keyEvent)) {
                            if (!phoneWindowManagerExt.mPolicy.keyguardOn()) {
                                phoneWindowManagerExt.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda17(this, keyEvent, 1));
                                break;
                            } else {
                                StringBuilder sb = new StringBuilder("keyguardOn, isShowing=");
                                sb.append(phoneWindowManagerExt.mPolicy.isKeyguardShowingAndNotOccluded());
                                sb.append(" isInputRestricted=");
                                KeyguardServiceDelegate keyguardServiceDelegate = phoneWindowManagerExt.mPolicy.mKeyguardDelegate;
                                if (keyguardServiceDelegate == null) {
                                    z = false;
                                } else {
                                    KeyguardServiceWrapper keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService;
                                    if (keyguardServiceWrapper != null) {
                                        keyguardServiceDelegate.mKeyguardState.inputRestricted = keyguardServiceWrapper.mKeyguardStateMonitor.mInputRestricted;
                                    }
                                    z = keyguardServiceDelegate.mKeyguardState.inputRestricted;
                                }
                                RCPManagerService$$ExternalSyntheticOutline0.m("PhoneWindowManagerExt", sb, z);
                                if (InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE) {
                                    phoneWindowManagerExt.showingSearcleToastIfNeeded();
                                    break;
                                }
                            }
                        }
                    } else {
                        Log.i("PhoneWindowManagerExt", "skip long press home, requestedSystemKey");
                        break;
                    }
                    break;
                case 1:
                case 2:
                default:
                    super.onLongPress(j, keyEvent, i);
                    break;
                case 3:
                    this.mIsKeyLongPressed = true;
                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                    if (!phoneWindowManagerExt2.mKeyCustomizationPolicy.launchLongPressAction(keyEvent)) {
                        phoneWindowManagerExt2.handleLongPressOnRecent(keyEvent.getDisplayId());
                        break;
                    }
                    break;
                case 4:
                    this.mIsKeyLongPressed = true;
                    boolean z2 = InputRune.PWM_ACTIVE_OR_XCOVER_KEY;
                    PhoneWindowManagerExt phoneWindowManagerExt3 = this.this$0;
                    if (!z2 || !phoneWindowManagerExt3.mKeyCustomizationPolicy.hasXCoverTopId(4, this.mKeyCode)) {
                        phoneWindowManagerExt3.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
                        break;
                    } else if (phoneWindowManagerExt3.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt3.mKeyCustomizationPolicy.launchXCoverLongActionIfNeeded(keyEvent, true);
                        break;
                    }
                    break;
                case 5:
                    this.mIsKeyLongPressed = true;
                    boolean z3 = InputRune.PWM_ACTIVE_OR_XCOVER_KEY;
                    PhoneWindowManagerExt phoneWindowManagerExt4 = this.this$0;
                    if (!z3 || !phoneWindowManagerExt4.mKeyCustomizationPolicy.hasXCoverTopId(4, this.mKeyCode)) {
                        phoneWindowManagerExt4.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
                        break;
                    } else if (phoneWindowManagerExt4.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt4.mKeyCustomizationPolicy.launchXCoverLongActionIfNeeded(keyEvent, true);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public void onMultiPress(long j, int i, int i2, KeyEvent keyEvent) {
            switch (this.$r8$classId) {
                case 0:
                    PhoneWindowManagerExt phoneWindowManagerExt = this.this$0;
                    if (i == 2) {
                        SystemKeyManager systemKeyManager = phoneWindowManagerExt.mSystemKeyPolicy;
                        int i3 = this.mKeyCode;
                        if (!systemKeyManager.hasSystemKeyInfo(i3, 8)) {
                            if (phoneWindowManagerExt.mKeyCustomizationPolicy.getLastAction(8, i3) != 4) {
                                if (!InputRune.PWM_QUICK_LAUNCH_CAMERA || !phoneWindowManagerExt.isQuickLaunchCameraEnabled(i3)) {
                                    if (phoneWindowManagerExt.mKeyCustomizationPolicy.getLastId(8, 3) == 1106) {
                                        phoneWindowManagerExt.mPolicy.mDefaultDisplayPolicy.mExt.mOneHandOpPolicy.startService(2);
                                        if (InputRune.PWM_KEY_SA_LOGGING) {
                                            PhoneWindowManagerExt.sendCoreSaLoggingDimension("NAVIB1002", phoneWindowManagerExt.mPolicy.mDefaultDisplayPolicy.mExt.mOneHandOpPolicy.mHasOneHandOpSpec ? "Disable one handed operation" : "Enable one handed operation");
                                            break;
                                        }
                                    }
                                } else {
                                    if (!phoneWindowManagerExt.doublePressLaunchPolicy(i3)) {
                                        phoneWindowManagerExt.launchPowerDoublePressCamera();
                                    }
                                    phoneWindowManagerExt.sendBroadcastDoubleClick(i3);
                                    if (InputRune.PWM_KEY_SA_LOGGING) {
                                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("NAVIB1002", "Quick launch camera");
                                        break;
                                    }
                                }
                            }
                        } else {
                            Log.i("PhoneWindowManagerExt", "skip double press home, requestedSystemKey");
                            break;
                        }
                    }
                    phoneWindowManagerExt.mKeyCustomizationPolicy.launchMultiPressAction(keyEvent, i);
                    break;
                default:
                    super.onMultiPress(j, i, i2, keyEvent);
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final void onPress(long j, KeyEvent keyEvent, int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (!keyEvent.isCanceled()) {
                        PhoneWindowManagerExt phoneWindowManagerExt = this.this$0;
                        if (!phoneWindowManagerExt.mSystemKeyPolicy.hasSystemKeyInfo(this.mKeyCode, 3)) {
                            phoneWindowManagerExt.mPolicy.postShortPressOnHome(keyEvent);
                            break;
                        } else {
                            Log.i("PhoneWindowManagerExt", "skip single press home, requestedSystemKey");
                            break;
                        }
                    } else {
                        Log.i("PhoneWindowManagerExt", "Ignoring HOME; event canceled.");
                        break;
                    }
                case 1:
                    if ((keyEvent.getFlags() & 268435456) == 0) {
                        this.this$0.injectionKeyEvent(this.mKeyCode, 268435456, -1);
                        break;
                    }
                    break;
                case 2:
                    PhoneWindowManagerExt phoneWindowManagerExt2 = this.this$0;
                    KeyCustomizationManager keyCustomizationManager = phoneWindowManagerExt2.mKeyCustomizationPolicy;
                    keyCustomizationManager.getClass();
                    int keyCode = keyEvent.getKeyCode();
                    int lastAction = keyCustomizationManager.getLastAction(3, keyCode);
                    if (!(lastAction != -1 ? keyCustomizationManager.launchPressAction(lastAction, keyEvent, keyCode, false) : false) && (keyEvent.getFlags() & 268435456) == 0) {
                        phoneWindowManagerExt2.injectionKeyEvent(this.mKeyCode, 268435456, -1);
                        break;
                    }
                    break;
                case 3:
                    PhoneWindowManagerExt phoneWindowManagerExt3 = this.this$0;
                    if (!phoneWindowManagerExt3.isInDexMode()) {
                        phoneWindowManagerExt3.mPolicy.toggleRecentApps(0);
                        break;
                    } else {
                        phoneWindowManagerExt3.mPolicy.toggleRecentApps(keyEvent.getDisplayId());
                        break;
                    }
                case 4:
                    boolean z = InputRune.PWM_ACTIVE_OR_XCOVER_KEY;
                    PhoneWindowManagerExt phoneWindowManagerExt4 = this.this$0;
                    if (!z || !phoneWindowManagerExt4.mKeyCustomizationPolicy.hasXCoverTopId(3, this.mKeyCode)) {
                        KeyCustomizationManager keyCustomizationManager2 = phoneWindowManagerExt4.mKeyCustomizationPolicy;
                        keyCustomizationManager2.getClass();
                        int keyCode2 = keyEvent.getKeyCode();
                        int lastAction2 = keyCustomizationManager2.getLastAction(3, keyCode2);
                        if (lastAction2 != -1) {
                            keyCustomizationManager2.launchPressAction(lastAction2, keyEvent, keyCode2, false);
                            break;
                        }
                    } else if (phoneWindowManagerExt4.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt4.mKeyCustomizationPolicy.launchXCoverPressActionIfNeeded(keyEvent, true);
                        break;
                    }
                    break;
                case 5:
                    boolean z2 = InputRune.PWM_ACTIVE_OR_XCOVER_KEY;
                    PhoneWindowManagerExt phoneWindowManagerExt5 = this.this$0;
                    if (!z2 || !phoneWindowManagerExt5.mKeyCustomizationPolicy.hasXCoverTopId(3, this.mKeyCode)) {
                        KeyCustomizationManager keyCustomizationManager3 = phoneWindowManagerExt5.mKeyCustomizationPolicy;
                        keyCustomizationManager3.getClass();
                        int keyCode3 = keyEvent.getKeyCode();
                        int lastAction3 = keyCustomizationManager3.getLastAction(3, keyCode3);
                        if (lastAction3 != -1) {
                            keyCustomizationManager3.launchPressAction(lastAction3, keyEvent, keyCode3, false);
                            break;
                        }
                    } else if (phoneWindowManagerExt5.mPolicy.mPowerManager.isInteractive()) {
                        phoneWindowManagerExt5.mKeyCustomizationPolicy.launchXCoverPressActionIfNeeded(keyEvent, true);
                        break;
                    }
                    break;
                case 6:
                    if ((keyEvent.getFlags() & 268435456) == 0) {
                        this.this$0.injectionKeyEvent(this.mKeyCode, 268435456, -1);
                        break;
                    }
                    break;
                default:
                    if ((keyEvent.getFlags() & 268435456) == 0) {
                        this.this$0.injectionKeyEvent(this.mKeyCode, 268435456, -1);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        public final boolean supportLongPress() {
            switch (this.$r8$classId) {
            }
            return true;
        }
    }

    public final class HotKey {
        public boolean isTriggered = false;
        public int keyCode = 0;
        public AlertDialog guideDialog = null;
        public final registerAppRunnable registerAppRunnable = new registerAppRunnable();

        public final class registerAppRunnable implements Runnable {
            public registerAppRunnable() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (HotKey.this.isTriggered) {
                    Slog.d("PhoneWindowManagerExt", "start registerHotKeyApp.");
                    HotKey hotKey = HotKey.this;
                    hotKey.isTriggered = false;
                    ComponentName componentName = PhoneWindowManagerExt.this.mTopActivity;
                    ApplicationInfo applicationInfo = null;
                    String packageName = componentName == null ? null : componentName.getPackageName();
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
                    if (launchIntentForPackage != null) {
                        HotKey hotKey2 = HotKey.this;
                        hotKey2.getClass();
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.addCategory("android.intent.category.LAUNCHER");
                        List<ResolveInfo> queryIntentActivities = PhoneWindowManagerExt.this.mContext.getPackageManager().queryIntentActivities(intent, 0);
                        if (queryIntentActivities != null) {
                            Iterator<ResolveInfo> it = queryIntentActivities.iterator();
                            while (it.hasNext()) {
                                if (packageName.equals(it.next().activityInfo.packageName)) {
                                    HotKey hotKey3 = HotKey.this;
                                    KeyCustomizationManager keyCustomizationManager = PhoneWindowManagerExt.this.mKeyCustomizationPolicy;
                                    int i = hotKey3.keyCode;
                                    ComponentName component = launchIntentForPackage.getComponent();
                                    if (component == null) {
                                        keyCustomizationManager.getClass();
                                        Log.d("KeyCustomizationManager", "componentName is empty. Can not set hot key info.");
                                    } else {
                                        KeyCustomizationInfoManager keyCustomizationInfoManager = keyCustomizationManager.mKeyCustomizationInfoManager;
                                        synchronized (keyCustomizationInfoManager.mLock) {
                                            keyCustomizationInfoManager.mHotKeyMap.put(i, component);
                                            keyCustomizationInfoManager.saveSettingsLocked();
                                        }
                                    }
                                    String format = String.format(PhoneWindowManagerExt.this.mContext.getString(R.string.minute_picker_description), packageManager.getApplicationLabel(applicationInfo).toString());
                                    PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                                    phoneWindowManagerExt.showToast(phoneWindowManagerExt.mContext, format);
                                    if (InputRune.PWM_KEY_SA_LOGGING) {
                                        HotKey hotKey4 = HotKey.this;
                                        hotKey4.saLogging(hotKey4.keyCode, "1" + packageName, false);
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m("Can not register hot key. packageName=", packageName, "PhoneWindowManagerExt");
                }
            }
        }

        public HotKey() {
        }

        public final void saLogging(int i, String str, boolean z) {
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
            PhoneWindowManagerExt.this.getClass();
            PhoneWindowManagerExt.sendCoreSaLoggingDimension(str2, str);
        }
    }

    class Injector {
        public final Context mContext;
        public final PhoneWindowManager mPhoneWindowManager;

        public Injector(Context context, PhoneWindowManager phoneWindowManager) {
            this.mContext = context;
            this.mPhoneWindowManager = phoneWindowManager;
        }
    }

    public final class OpeningApps extends SideKeyDoublePress.Behavior {
        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean preCondition(android.content.Intent r10, boolean r11) {
            /*
                r9 = this;
                com.android.server.policy.PhoneWindowManagerExt r0 = r9.mPolicyExt
                r0.getClass()
                r1 = 0
                r2 = 3
                r3 = 2
                r4 = 1
                android.content.Context r5 = r0.mContext     // Catch: java.lang.IllegalArgumentException -> L3d
                android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: java.lang.IllegalArgumentException -> L3d
                android.content.ComponentName r6 = r10.getComponent()     // Catch: java.lang.IllegalArgumentException -> L3d
                java.lang.String r6 = r6.getPackageName()     // Catch: java.lang.IllegalArgumentException -> L3d
                int r5 = r5.getApplicationEnabledSetting(r6)     // Catch: java.lang.IllegalArgumentException -> L3d
                if (r5 == r3) goto L3b
                if (r5 == r2) goto L3b
                android.content.Context r5 = r0.mContext     // Catch: java.lang.IllegalArgumentException -> L3d
                android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: java.lang.IllegalArgumentException -> L3d
                android.content.ComponentName r6 = r10.getComponent()     // Catch: java.lang.IllegalArgumentException -> L3d
                int r5 = r5.getComponentEnabledSetting(r6)     // Catch: java.lang.IllegalArgumentException -> L3d
                if (r5 == r3) goto L3b
                if (r5 == r2) goto L3b
                boolean r0 = r0.isArchived(r10)     // Catch: java.lang.IllegalArgumentException -> L3d
                if (r0 == 0) goto L39
                r0 = r2
                goto L3e
            L39:
                r0 = r1
                goto L3e
            L3b:
                r0 = r4
                goto L3e
            L3d:
                r0 = r3
            L3e:
                java.lang.String r5 = "target app state : "
                java.lang.String r6 = "PhoneWindowManagerExt"
                com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r0, r5, r6)
                if (r0 != r3) goto L57
                com.android.server.policy.PhoneWindowManagerExt r9 = r9.mPolicyExt
                android.content.Context r10 = r9.mContext
                r11 = 17043037(0x1040e5d, float:2.4254876E-38)
                java.lang.String r11 = r10.getString(r11)
                r9.showToast(r10, r11)
                return r4
            L57:
                if (r0 != r4) goto Lcf
                com.android.server.policy.PhoneWindowManagerExt r9 = r9.mPolicyExt
                android.content.Context r11 = r9.mContext
                android.content.pm.PackageManager r0 = r11.getPackageManager()
                android.content.pm.ResolveInfo r1 = r0.resolveActivity(r10, r1)
                r2 = 0
                if (r1 == 0) goto L6e
                android.content.pm.ActivityInfo r1 = r1.activityInfo
                if (r1 == 0) goto L6e
            L6c:
                r10 = r2
                goto La9
            L6e:
                java.lang.String r1 = "Can not launch app because app is not added in reserve battery mode"
                android.util.Log.d(r6, r1)
                java.lang.String r1 = r10.getPackage()
                boolean r3 = android.text.TextUtils.isEmpty(r1)
                if (r3 == 0) goto L8b
                android.content.ComponentName r3 = r10.getComponent()
                if (r3 == 0) goto L8b
                android.content.ComponentName r10 = r10.getComponent()
                java.lang.String r1 = r10.getPackageName()
            L8b:
                r7 = 0
                android.content.pm.PackageManager$ApplicationInfoFlags r10 = android.content.pm.PackageManager.ApplicationInfoFlags.of(r7)     // Catch: java.lang.Exception -> L96
                android.content.pm.ApplicationInfo r10 = r0.getApplicationInfo(r1, r10)     // Catch: java.lang.Exception -> L96
                goto La9
            L96:
                r10 = move-exception
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Can not get applictionInfo, "
                r0.<init>(r1)
                r0.append(r10)
                java.lang.String r10 = r0.toString()
                android.util.Log.d(r6, r10)
                goto L6c
            La9:
                if (r10 != 0) goto Lac
                goto Lcb
            Lac:
                android.content.Context r0 = r9.mContext
                r1 = 17043038(0x1040e5e, float:2.425488E-38)
                java.lang.String r0 = r0.getString(r1)
                android.content.Context r1 = r9.mContext
                android.content.pm.PackageManager r1 = r1.getPackageManager()
                java.lang.CharSequence r10 = r1.getApplicationLabel(r10)
                java.lang.String r10 = r10.toString()
                java.lang.Object[] r10 = new java.lang.Object[]{r10}
                java.lang.String r2 = java.lang.String.format(r0, r10)
            Lcb:
                r9.showToast(r11, r2)
                return r4
            Lcf:
                if (r0 != r2) goto Le0
                com.android.server.policy.PhoneWindowManagerExt r9 = r9.mPolicyExt
                android.content.Context r10 = r9.mContext
                r11 = 17043036(0x1040e5c, float:2.4254873E-38)
                java.lang.String r11 = r10.getString(r11)
                r9.showToast(r10, r11)
                return r4
            Le0:
                boolean r9 = super.preCondition(r10, r11)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.OpeningApps.preCondition(android.content.Intent, boolean):boolean");
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public final void setAction(int i) {
            if (i == 1 || i == 3) {
                this.mAction = i;
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public final void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            boolean isShowing = this.mPolicyExt.mPolicy.mKeyguardDelegate.isShowing();
            int i = this.mAction;
            if (i == 1) {
                if (isShowing || z2) {
                    PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                    phoneWindowManagerExt.setPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(phoneWindowManagerExt.mContext, 0, intent, 201326592, null, UserHandle.CURRENT), intent2);
                } else {
                    this.mPolicyExt.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                }
            } else if (i == 3) {
                this.mPolicyExt.mContext.startServiceAsUser(intent, UserHandle.CURRENT);
            }
            if (InputRune.PWM_KEY_SA_LOGGING) {
                PhoneWindowManagerExt phoneWindowManagerExt2 = this.mPolicyExt;
                String str = this.mTargetAppName;
                String[] split = str.split("/");
                if (split.length >= 2) {
                    str = split[0];
                }
                phoneWindowManagerExt2.getClass();
                PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", str);
            }
        }
    }

    public final class OpeningBixby extends SideKeyDoublePress.Behavior {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ OpeningBixby(String str, int i) {
            super(str);
            this.$r8$classId = i;
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean doublePressLaunchPolicy(boolean z) {
            switch (this.$r8$classId) {
            }
            return super.doublePressLaunchPolicy(z);
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public int getAction() {
            switch (this.$r8$classId) {
                case 0:
                    return 3;
                case 1:
                    return 1;
                case 2:
                    return 1;
                case 3:
                    return 1;
                default:
                    return super.getAction();
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public Intent getIntent() {
            switch (this.$r8$classId) {
                case 3:
                    return new Intent("android.intent.action.VIEW", Uri.parse(this.mTargetAppName)).addFlags(270532608);
                default:
                    return super.getIntent();
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public boolean showCoverToast(Intent intent) {
            switch (this.$r8$classId) {
                case 1:
                    return false;
                case 2:
                    return false;
                case 3:
                    if (WmCoverState.sIsEnabled) {
                        WmCoverState wmCoverState = WmCoverState.getInstance();
                        if (!((CoverState) wmCoverState).switchState) {
                            switch (((CoverState) wmCoverState).type) {
                                case 15:
                                case 16:
                                case 17:
                                    intent.putExtra("showCoverToast", true);
                                    Log.d("PhoneWindowManagerExt", "neededShowCoverToast for cover");
                                    return true;
                            }
                        }
                    }
                    return false;
                default:
                    return super.showCoverToast(intent);
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public final void startTargetApp(KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2) {
            SemWindowManager.KeyCustomizationInfo last;
            switch (this.$r8$classId) {
                case 0:
                    BixbyService bixbyService = this.mPolicyExt.mBixbyService;
                    BixbyService.Params params = new BixbyService.Params(keyEvent, z);
                    params.doublePress = true;
                    bixbyService.startService(new BixbyService.Params(params));
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "Bixby");
                        break;
                    }
                    break;
                case 1:
                    PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                    Set set = PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST;
                    phoneWindowManagerExt.getClass();
                    Slog.i("PhoneWindowManagerExt", "launch double press tv mode");
                    if (!PhoneWindowManagerExt.isCameraRunning() && (last = phoneWindowManagerExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.getLast(8, 26)) != null) {
                        int i = last.id;
                        if (i != 2002 && i != 1104) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "launchDoublePressPowerTvMode: keyCustomizationInfo ID: ", "PhoneWindowManagerExt");
                        } else if (phoneWindowManagerExt.mPolicy.mKeyguardDelegate.isShowing()) {
                            Intent intent3 = new Intent();
                            intent3.putExtra("afterKeyguardGone", true);
                            phoneWindowManagerExt.setPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(phoneWindowManagerExt.mContext, 0, last.intent, 201326592, null, UserHandle.CURRENT), intent3);
                        } else {
                            phoneWindowManagerExt.mContext.startActivityAsUser(last.intent, UserHandle.CURRENT);
                        }
                    }
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "TV mode");
                        break;
                    }
                    break;
                case 2:
                    this.mPolicyExt.launchPowerDoublePressCamera();
                    this.mPolicyExt.sendBroadcastDoubleClick(26);
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "Quick launch camera");
                        break;
                    }
                    break;
                case 3:
                    if (z2) {
                        intent2.putExtra("ignoreUnlock", true);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = this.mPolicyExt;
                        phoneWindowManagerExt2.setPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(phoneWindowManagerExt2.mContext, 0, intent, 201326592, null, UserHandle.CURRENT), intent2);
                    } else {
                        this.mPolicyExt.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                    }
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "Pay with Samsung Pay");
                        break;
                    }
                    break;
                case 4:
                    PhoneWindowManagerExt phoneWindowManagerExt3 = this.mPolicyExt;
                    Set set2 = PhoneWindowManagerExt.KEYCODE_DEBUG_LOG_ALLOWLIST;
                    if (phoneWindowManagerExt3.mPersonaManagerService == null) {
                        phoneWindowManagerExt3.mPersonaManagerService = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
                    }
                    if (phoneWindowManagerExt3.mPersonaManagerService != null) {
                        if (SemEmergencyManager.isMinimalBatteryUseMode(phoneWindowManagerExt3.mContext) || SemPersonaManager.getSecureFolderId(phoneWindowManagerExt3.mContext) > 0) {
                            if (phoneWindowManagerExt3.mPersonaManagerService == null) {
                                phoneWindowManagerExt3.mPersonaManagerService = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
                            }
                            PersonaManagerService personaManagerService = phoneWindowManagerExt3.mPersonaManagerService;
                            personaManagerService.getClass();
                            ContainerDependencyWrapper containerDependencyWrapper = ContainerDependencyWrapper.sInstance;
                            if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportPrivateMode", false)) {
                                personaManagerService.mPersonaHandler.sendMessage(personaManagerService.mPersonaHandler.obtainMessage(90));
                            }
                        } else {
                            Intent intent4 = new Intent("com.sec.knox.securefolder.CREATE_SECURE_FOLDER");
                            intent4.setFlags(268435456);
                            phoneWindowManagerExt3.mContext.startActivityAsUser(intent4, UserHandle.CURRENT);
                        }
                    }
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "Quick switch to Secure Folder");
                        break;
                    }
                    break;
                default:
                    this.mPolicyExt.onFlashlightKeyPressed(26);
                    if (InputRune.PWM_KEY_SA_LOGGING) {
                        this.mPolicyExt.getClass();
                        PhoneWindowManagerExt.sendCoreSaLoggingDimension("HWB1002", "Flashlight");
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.policy.SideKeyDoublePress.Behavior
        public void updateTargetComponent(Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    ComponentName componentName = this.mPolicyExt.mBixbyService.mDefaultComponentName;
                    if (componentName != null) {
                        intent.setComponent(componentName);
                        break;
                    }
                    break;
                case 1:
                    intent.setComponent(ComponentName.unflattenFromString(this.mTargetAppName));
                    break;
                case 2:
                    intent.setComponent(ComponentName.unflattenFromString(this.mTargetAppName));
                    break;
            }
        }
    }

    public final class PenSoundInfo {
        public String mAttachPenSoundPath;
        public String mDetachPenSoundPath;
        public int mPenAttachSoundId;
        public int mPenDetachSoundId;
        public String mPenSoundPath;
        public SoundPool mPenSoundPool;
        public int mPenSoundStreamId;

        public static String getOMCPenSoundPath(String str) {
            String str2 = SystemProperties.get("persist.sys.omc_respath");
            return str2 != null ? AnyMotionDetector$$ExternalSyntheticOutline0.m(str2, "/media/audio/spen/", str) : "";
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("PenSoundInfo{attachPath=");
            sb.append(this.mAttachPenSoundPath);
            sb.append(", detachPath=");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mDetachPenSoundPath, "}");
        }

        public final void updatePenSound(String str) {
            this.mPenSoundPath = str;
            String oMCPenSoundPath = getOMCPenSoundPath("Pen_att_noti1.ogg");
            String oMCPenSoundPath2 = getOMCPenSoundPath("Pen_det_noti1.ogg");
            File file = new File(oMCPenSoundPath);
            if (!file.exists() || file.length() <= 0) {
                file = null;
            }
            File file2 = new File(oMCPenSoundPath2);
            if (!file2.exists() || file2.length() <= 0) {
                file2 = null;
            }
            if (InputRune.PWM_OMC_SPEN_SOUND && file != null && file2 != null) {
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
            if (soundPool != null) {
                this.mPenAttachSoundId = soundPool.load(this.mAttachPenSoundPath, 1);
                this.mPenDetachSoundId = this.mPenSoundPool.load(this.mDetachPenSoundPath, 1);
                return;
            }
            try {
                SoundPool soundPool2 = new SoundPool(2, 1, 0);
                this.mPenSoundPool = soundPool2;
                this.mPenAttachSoundId = soundPool2.load(this.mAttachPenSoundPath, 1);
                this.mPenDetachSoundId = this.mPenSoundPool.load(this.mDetachPenSoundPath, 1);
            } catch (Exception unused) {
                Slog.e("PhoneWindowManagerExt", "Cannot load pen sound");
                this.mPenSoundPool = null;
            }
        }
    }

    public final class PolicyExtHandler extends Handler {
        public PolicyExtHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                PhoneWindowManagerExt.this.mIsVolumeUpKeyPressed = false;
                return;
            }
            if (i != 2) {
                return;
            }
            WindowManagerServiceExt windowManagerServiceExt = PhoneWindowManagerExt.this.mWindowManagerFuncs;
            int intValue = ((Integer) message.obj).intValue();
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = windowManagerServiceExt.mService.mRoot.getDisplayContentOrCreate(intValue);
                    if (displayContentOrCreate == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        displayContentOrCreate.reconfigureDisplayLocked();
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final class RoleObserver implements OnRoleHoldersChangedListener {
        public final Executor mExecutor;

        public RoleObserver() {
            Executor mainExecutor = PhoneWindowManagerExt.this.mContext.getMainExecutor();
            this.mExecutor = mainExecutor;
            RoleManager roleManager = (RoleManager) PhoneWindowManagerExt.this.mContext.getSystemService(RoleManager.class);
            if (roleManager != null) {
                roleManager.addOnRoleHoldersChangedListenerAsUser(mainExecutor, this, UserHandle.ALL);
                PhoneWindowManagerExt.this.setGoogleQuickSearchBoxAsDigitalAssistant();
            }
        }

        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if (str.equals("android.app.role.ASSISTANT")) {
                PhoneWindowManagerExt.this.setGoogleQuickSearchBoxAsDigitalAssistant();
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Map mSettingsUriToCallback;

        public SettingsObserver(PolicyExtHandler policyExtHandler) {
            super(policyExtHandler);
            this.mSettingsUriToCallback = new LinkedHashMap();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            updateSettings();
        }

        public final void registerSettings(Uri uri, Consumer consumer) {
            PhoneWindowManagerExt.this.mContext.getContentResolver().registerContentObserver(uri, false, this, -1);
            this.mSettingsUriToCallback.put(uri, consumer);
        }

        public final void updateSettings() {
            synchronized (PhoneWindowManagerExt.this.mLock) {
                try {
                    Iterator it = ((LinkedHashMap) this.mSettingsUriToCallback).entrySet().iterator();
                    while (it.hasNext()) {
                        ((Consumer) ((Map.Entry) it.next()).getValue()).accept(Boolean.FALSE);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
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

    public PhoneWindowManagerExt(Context context, WindowManagerPolicy windowManagerPolicy, WindowManagerServiceExt windowManagerServiceExt) {
        new AnonymousClass2(this, 3);
        this.mKeyUpTime = new long[2];
        this.mIsScreenshotTriggered = false;
        this.mScreenshotTriggeredTime = 0L;
        this.mScreenshotEnabled = true;
        this.mIsInteractionControlEnabled = false;
        this.mIsPowerKeyBlocked = false;
        this.mIsVolumeKeyBlocked = false;
        this.mIsAnyKeyMode = false;
        this.mAcceptCallHomeConsumed = false;
        this.mIsSktPhoneRelaxMode = false;
        this.mProximityChangeReceiver = new AnonymousClass2(this, 4);
        this.mIsAccessibilityShortcutVolupPower = false;
        this.mAccessibilityDirectAccessController = null;
        this.mSetupWizardGlobalActionReceiver = new AnonymousClass2(this, 5);
        this.mMultiuserReceiver = new AnonymousClass2(this, 6);
        this.mQuintuplePressOnPowerBehavior = 0;
        this.mQuadruplePressOnPowerBehavior = 0;
        this.mQuickLaunchCameraBehavior = 2;
        this.mQuickLaunchCameraConsumer = new PhoneWindowManagerExt$$ExternalSyntheticLambda2(3, this);
        final int i = 0;
        this.mDrmEventObserver = new UEventObserver(this) { // from class: com.android.server.policy.PhoneWindowManagerExt.10
            public final /* synthetic */ PhoneWindowManagerExt this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                switch (i) {
                    case 0:
                        if (uEvent != null) {
                            String str = uEvent.get(Constants.JSON_CLIENT_DATA_STATUS);
                            if (!TextUtils.isEmpty(str)) {
                                this.this$0.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("connected".equals(str), false);
                                break;
                            }
                        } else {
                            Log.d("PhoneWindowManagerExt", "The event of DrmEvent is null.");
                            break;
                        }
                        break;
                    default:
                        if (uEvent != null) {
                            String str2 = uEvent.get("STATE");
                            if (!TextUtils.isEmpty(str2)) {
                                this.this$0.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("DP=1".equals(str2), false);
                                break;
                            }
                        } else {
                            Log.d("PhoneWindowManagerExt", "The event of ExtEvent is null.");
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mExtEventObserver = new UEventObserver(this) { // from class: com.android.server.policy.PhoneWindowManagerExt.10
            public final /* synthetic */ PhoneWindowManagerExt this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                switch (i2) {
                    case 0:
                        if (uEvent != null) {
                            String str = uEvent.get(Constants.JSON_CLIENT_DATA_STATUS);
                            if (!TextUtils.isEmpty(str)) {
                                this.this$0.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("connected".equals(str), false);
                                break;
                            }
                        } else {
                            Log.d("PhoneWindowManagerExt", "The event of DrmEvent is null.");
                            break;
                        }
                        break;
                    default:
                        if (uEvent != null) {
                            String str2 = uEvent.get("STATE");
                            if (!TextUtils.isEmpty(str2)) {
                                this.this$0.mPolicy.mDefaultDisplayPolicy.setHdmiPlugged("DP=1".equals(str2), false);
                                break;
                            }
                        } else {
                            Log.d("PhoneWindowManagerExt", "The event of ExtEvent is null.");
                            break;
                        }
                        break;
                }
            }
        };
        this.mAppSwitchKeyConsumed = false;
        this.mIsHapticsEnabled = true;
        this.mIsAssistHapticEnabled = false;
        this.mIsHapticsSupported = false;
        this.mServiceAcquireLock = new Object();
        this.mPenType = InputRune.PWM_SPEN ? InputRune.PWM_SPEN_USP_LEVEL % 10 : -1;
        this.mPenState = -1;
        this.mPenSoundEnabled = false;
        this.mPenSoundFilePath = null;
        int i3 = 4;
        this.mPenDetachNotiConsumer = new PhoneWindowManagerExt$$ExternalSyntheticLambda2(i3, this);
        this.mIsDoubleTapPremiumWatchOn = false;
        this.mIsVolumeUpKeyMode = false;
        this.mWakeAndUnlockTriggered = false;
        this.mWakeAndUnlockRunning = new PhoneWindowManagerExt$$ExternalSyntheticLambda1(i3, this);
        this.mMultiFingerGestureListener = new InputManager.SemOnMultiFingerGestureListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.12
            public final void onMultiFingerGesture(int i4, int i5) {
                if (PhoneWindowManagerExt.this.isInDexMode()) {
                    Slog.d("PhoneWindowManagerExt", "Skip multi finger gesture in DeX mode.");
                    return;
                }
                PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                phoneWindowManagerExt.getClass();
                Slog.d("PhoneWindowManagerExt", "handleMultiFingerTap behavior=" + i4);
                if (i4 != 1) {
                    PhoneWindowManager phoneWindowManager = phoneWindowManagerExt.mPolicy;
                    try {
                        if (i4 == 5) {
                            IStatusBarService statusBarService = phoneWindowManager.getStatusBarService();
                            if (statusBarService == null) {
                                return;
                            } else {
                                statusBarService.expandNotificationsPanel();
                            }
                        } else {
                            if (i4 != 6) {
                                Slog.d("PhoneWindowManagerExt", "The MultiFingerTap type was wrong.");
                                return;
                            }
                            IStatusBarService statusBarService2 = phoneWindowManager.getStatusBarService();
                            if (statusBarService2 == null) {
                                return;
                            } else {
                                statusBarService2.expandSettingsPanel((String) null);
                            }
                        }
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                if (CoreRune.FW_FLEXIBLE_TABLE_MODE) {
                    Intent intent = new Intent();
                    intent.setComponent(ComponentName.unflattenFromString("com.sec.android.app.launcher/com.sec.android.app.launcher.overlayapps.OverlayAppsService"));
                    try {
                        phoneWindowManagerExt.mContext.startService(intent);
                        return;
                    } catch (SecurityException e) {
                        Log.e("PhoneWindowManagerExt", "failed to startService(), " + e);
                        return;
                    }
                }
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.addCategory("android.intent.category.HOME");
                intent2.setFlags(268435456);
                intent2.putExtra("sec.android.intent.extra.LAUNCHER_ACTION", "com.android.launcher2.ALL_APPS");
                try {
                    phoneWindowManagerExt.mContext.startActivity(intent2);
                } catch (ActivityNotFoundException e2) {
                    Log.w("PhoneWindowManagerExt", "No activity to launch launcher app list. ", e2);
                }
            }
        };
        this.mIsPogoKeyboardConnected = false;
        this.mLockTaskModeState = 0;
        this.mLockTaskFeatures = new SparseIntArray();
        this.mBootMsgDialogs = new ArrayList();
        this.mFoldSaLock = new Object();
        this.mFoldSaLoggingReceiver = new AnonymousClass16();
        this.mIsDoubleTapToWakeUp = false;
        this.mIsDoubleTapToWakeUpSupported = false;
        this.mStopLockTaskModePinnedChordLongPress = new PhoneWindowManagerExt$$ExternalSyntheticLambda8(0);
        this.mIsCallOpenDictation = false;
        this.mIsSamsungKeyboard = false;
        this.mButtonShapeEnabled = -1;
        this.mBoldFontEnabled = -1;
        this.mCursorThicknessScale = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mProKioskManager = null;
        this.mContext = context;
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) windowManagerPolicy;
        this.mPolicy = phoneWindowManager;
        this.mWindowManagerFuncs = windowManagerServiceExt;
        phoneWindowManager.mExt = this;
        this.mKeyCustomizationPolicy = new KeyCustomizationManager(context, this);
    }

    public static Intent getFillInIntent() {
        Intent intent = new Intent();
        intent.putExtra("afterKeyguardGone", true);
        intent.putExtra("ignoreKeyguardState", true);
        intent.putExtra("dismissIfInsecure", true);
        return intent;
    }

    public static boolean isCameraRunning() {
        boolean equals = SystemProperties.get("service.camera.running", "0").equals("1");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isCameraRunning=", "PhoneWindowManagerExt", equals);
        return equals;
    }

    public static void saLogForBasic(String str) {
        if (TextUtils.isEmpty(str)) {
            Slog.i("PhoneWindowManagerExt", "saLogForBasic - eventId is null");
        } else {
            CoreSaLogger.logForBasic(str);
        }
    }

    public static void sendCoreSaLoggingDimension(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Slog.i("PhoneWindowManagerExt", "saLogForBasic - eventId or details are null");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("det", str2);
        CoreSaLogger.logForBasic(str, hashMap);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addSingleKeyGestureRule(int r4) {
        /*
            r3 = this;
            com.android.server.policy.PhoneWindowManager r0 = r3.mPolicy
            com.android.server.policy.SingleKeyGestureDetector r1 = r0.mSingleKeyGestureDetector
            boolean r1 = r1.hasRule(r4)
            java.lang.String r2 = "PhoneWindowManagerExt"
            if (r1 == 0) goto L12
            java.lang.String r3 = "Already added rule of keyCode "
            com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r4, r3, r2)
            return
        L12:
            r1 = 3
            if (r4 == r1) goto L6a
            r1 = 4
            if (r4 == r1) goto L63
            r1 = 24
            if (r4 == r1) goto L5c
            r1 = 25
            if (r4 == r1) goto L55
            r1 = 79
            if (r4 == r1) goto L4e
            r1 = 187(0xbb, float:2.62E-43)
            if (r4 == r1) goto L47
            r1 = 1015(0x3f7, float:1.422E-42)
            if (r4 == r1) goto L40
            r1 = 1079(0x437, float:1.512E-42)
            if (r4 == r1) goto L38
            java.lang.String r3 = "updateSingleKeyGestureRule, keyCode was wrong. "
            com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r4, r3, r2)
            r3 = 0
            goto L71
        L38:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 5
            r4.<init>(r3, r1)
        L3e:
            r3 = r4
            goto L71
        L40:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 4
            r4.<init>(r3, r1)
            goto L3e
        L47:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 3
            r4.<init>(r3, r1)
            goto L3e
        L4e:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 2
            r4.<init>(r3, r1)
            goto L3e
        L55:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 6
            r4.<init>(r3, r1)
            goto L3e
        L5c:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 7
            r4.<init>(r3, r1)
            goto L3e
        L63:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 1
            r4.<init>(r3, r1)
            goto L3e
        L6a:
            com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule r4 = new com.android.server.policy.PhoneWindowManagerExt$HomeKeyRule
            r1 = 0
            r4.<init>(r3, r1)
            goto L3e
        L71:
            if (r3 == 0) goto L78
            com.android.server.policy.SingleKeyGestureDetector r4 = r0.mSingleKeyGestureDetector
            r4.addRule(r3)
        L78:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.addSingleKeyGestureRule(int):void");
    }

    public final void callDictation(int i, String str) {
        Log.d("PhoneWindowManagerExt", "callDictation, method=" + str + ", keyCode=" + i);
        Bundle bundle = new Bundle();
        bundle.putInt("keyCode", i);
        this.mContext.getContentResolver().call(KeyCustomizationConstants.UriTags.DICTATION, str, (String) null, bundle);
    }

    public final boolean canDispatchingSystemKeyEvent(int i) {
        return this.mSystemKeyPolicy.hasSystemKeyInfoWithFocusedWindow(i, 0, true);
    }

    public final int checkSystemKeyBeforeDispatching(int i, IBinder iBinder) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "system key requested code=", "PhoneWindowManagerExt");
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        KeyInterceptionInfo keyInterceptionInfoFromToken = phoneWindowManager.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
        if (keyInterceptionInfoFromToken == null || keyInterceptionInfoFromToken.layoutParamsType != 2040 || phoneWindowManager.isKeyguardShowingAndNotOccluded()) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "sec check system key before dispatching, keyCode=", "PhoneWindowManagerExt");
            return 0;
        }
        PhoneWindow.sendCloseSystemWindows(phoneWindowManager.mContext, "reason");
        Log.d("PhoneWindowManagerExt", "Can not dispatch key event because of expanded status bar, keyCode=" + i);
        return -1;
    }

    public final void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        boolean z;
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo;
        KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
        keyCustomizationManager.getClass();
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        KeyCustomizationInfoManager keyCustomizationInfoManager = keyCustomizationManager.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            try {
                z = false;
                for (int i4 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                    SparseArray sparseArray = (SparseArray) keyCustomizationInfoManager.getInfoMapLocked(i4).get(i2);
                    if (sparseArray != null && (keyCustomizationInfo = (SemWindowManager.KeyCustomizationInfo) sparseArray.get(i)) != null && keyCustomizationInfo.action == i3) {
                        sparseArray.remove(i);
                        keyCustomizationInfoManager.updateHigherPriorityInfoLocked(i4, i2, -1);
                        z = true;
                    }
                }
                if (z) {
                    keyCustomizationInfoManager.saveSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            keyCustomizationManager.initPowerBehaviorAndSingleKeyGestureDetectorRule(i2);
            return;
        }
        Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByAction, Requested info is empty. " + KeyCustomizationManager.idToString(i) + " keyCode=" + i2 + " " + KeyCustomizationManager.actionToString(i3));
    }

    public final void clearKeyCustomizationInfoByKeyCode(int i, int i2) {
        int i3;
        boolean z;
        KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
        keyCustomizationManager.getClass();
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        KeyCustomizationInfoManager keyCustomizationInfoManager = keyCustomizationManager.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            try {
                z = false;
                for (int i4 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                    SparseArray sparseArray = (SparseArray) keyCustomizationInfoManager.getInfoMapLocked(i4).get(i2);
                    if (sparseArray != null && sparseArray.get(i) != null) {
                        sparseArray.remove(i);
                        keyCustomizationInfoManager.updateHigherPriorityInfoLocked(i4, i2, -1);
                        z = true;
                    }
                }
                if (z) {
                    keyCustomizationInfoManager.saveSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z) {
            Slog.d("KeyCustomizationManager", "clearKeyCustomizationInfoByKeyCode, Requested info is empty. " + KeyCustomizationManager.idToString(i) + " keyCode=" + i2);
            return;
        }
        if (i2 == 26) {
            for (int i5 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
                if ((!InputRune.PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL || (i5 & 16) == 0) && (i5 & 64) == 0) {
                    keyCustomizationManager.updatePowerBehavior(i5);
                }
            }
        } else {
            keyCustomizationManager.mPolicyExt.updateSingleKeyGestureRule(i2);
        }
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule = (SingleKeyGestureDetector.SingleKeyRule) keyCustomizationManager.mPolicyExt.mPolicy.mSingleKeyGestureDetector.mCustomRules.get(i2);
        if (singleKeyRule != null) {
            singleKeyRule.getLongPressTimeoutMs();
        }
        SingleKeyGestureDetector.SingleKeyRule singleKeyRule2 = (SingleKeyGestureDetector.SingleKeyRule) keyCustomizationManager.mPolicyExt.mPolicy.mSingleKeyGestureDetector.mCustomRules.get(i2);
        if (singleKeyRule2 == null) {
            return;
        }
        long j = singleKeyRule2.extensionMultiPressTimeout;
        if (j == 0) {
            j = SingleKeyGestureDetector.sDefaultMultiPressTimeout;
        }
        if (0 == j) {
            return;
        }
        boolean z2 = SingleKeyGestureDetector.DEBUG;
        singleKeyRule2.extensionMultiPressTimeout = 0L;
    }

    public final boolean doublePressLaunchPolicy(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (!phoneWindowManager.isUserSetupComplete()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked because UserSetup isn't completed");
            return true;
        }
        if ("true".equals(SystemProperties.get("ril.domesticOtaStart"))) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by OTA status");
            return true;
        }
        if (phoneWindowManager.mKeyguardDelegate.isSimLocked()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by SimLock");
            return true;
        }
        if (isCarrierLocked()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked by CarrierLock");
            return true;
        }
        if (WmCoverState.sIsEnabled && WmCoverState.getInstance().isFlipTypeCoverClosed()) {
            Log.i("PhoneWindowManagerExt", "double press was blocked because cover was closed");
            return true;
        }
        if (!this.mSystemKeyPolicy.hasSystemKeyInfo(i, 8)) {
            return false;
        }
        Log.i("PhoneWindowManagerExt", "skip double press keyCode(" + i + "), requestedSystemKey");
        return true;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("");
        printWriter.print("    ");
        printWriter.println("--- PhoneWindowManagerExt ---");
        KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
        keyCustomizationManager.getClass();
        printWriter.println();
        printWriter.print("    ");
        printWriter.print("KeyCustomization info state: ");
        KeyCustomizationInfoManager keyCustomizationInfoManager = keyCustomizationManager.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            try {
                printWriter.println(keyCustomizationInfoManager.mXmlUtils.mXmlVersion);
                printWriter.print("    ");
                printWriter.println("All KeyCustomizationInfo");
                for (int i : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                    SparseArray infoMapLocked = keyCustomizationInfoManager.getInfoMapLocked(i);
                    if (infoMapLocked.size() != 0) {
                        printWriter.print("    ");
                        printWriter.print(KeyCustomizationManager.pressToString(i));
                        printWriter.println(" ---");
                        for (int i2 = 0; i2 < infoMapLocked.size(); i2++) {
                            SparseArray sparseArray = (SparseArray) infoMapLocked.valueAt(i2);
                            if (sparseArray.size() != 0) {
                                StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "    ", "    KEY_CODE(");
                                m.append(infoMapLocked.keyAt(i2));
                                m.append(") :");
                                printWriter.println(m.toString());
                                KeyCustomizationInfoManager.dumpKeyCustomizationInfoKeyCodeMap(printWriter, sparseArray);
                            }
                        }
                    }
                }
                printWriter.println();
                printWriter.print("    ");
                printWriter.println("Last KeyCustomizationInfo");
                for (int i3 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                    SparseArray lastInfoLocked = keyCustomizationInfoManager.getLastInfoLocked(i3);
                    if (lastInfoLocked.size() != 0) {
                        printWriter.print("    ");
                        printWriter.print(KeyCustomizationManager.pressToString(i3));
                        printWriter.println(" ---");
                        KeyCustomizationInfoManager.dumpKeyCustomizationInfoKeyCodeMap(printWriter, lastInfoLocked);
                    }
                }
                SparseArray sparseArray2 = keyCustomizationInfoManager.mHotKeyMap;
                if (sparseArray2.size() != 0) {
                    printWriter.println();
                    printWriter.print("    ");
                    printWriter.println("HotKeys=");
                    for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
                        int keyAt = sparseArray2.keyAt(i4);
                        ComponentName componentName = (ComponentName) sparseArray2.get(keyAt);
                        if (componentName != null) {
                            printWriter.print("    ");
                            printWriter.print(" KeyCode ");
                            printWriter.print(keyAt);
                            printWriter.print(", componentName: ");
                            printWriter.println(componentName);
                        }
                    }
                }
                if (keyCustomizationInfoManager.mOwnerPackageList.size() != 0) {
                    printWriter.println();
                    printWriter.print("    ");
                    printWriter.println("ownerPackageList:");
                    Iterator it = keyCustomizationInfoManager.mOwnerPackageList.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        printWriter.print("    ");
                        printWriter.println("    " + str);
                    }
                }
                printWriter.print("    ");
                printWriter.print("UserId:");
                printWriter.println(keyCustomizationInfoManager.mUserId);
            } finally {
            }
        }
        KeyCustomizationInfoXmlUtils keyCustomizationInfoXmlUtils = keyCustomizationInfoManager.mXmlUtils;
        keyCustomizationInfoXmlUtils.getClass();
        printWriter.print("    ");
        printWriter.print("XmlFileErrorCode=");
        printWriter.println(keyCustomizationInfoXmlUtils.xmlFileErrorCode);
        printWriter.println();
        printWriter.print("    ");
        printWriter.print("maxMultiPressPowerCount=");
        printWriter.println(this.mPolicy.getMaxMultiPressPowerCount());
        printWriter.print("    ");
        printWriter.print("mQuadruplePressOnPowerBehavior=");
        printWriter.println(PhoneWindowManager.multiPressOnPowerBehaviorToString(this.mQuadruplePressOnPowerBehavior));
        SystemKeyManager systemKeyManager = this.mSystemKeyPolicy;
        synchronized (systemKeyManager) {
            try {
                printWriter.print("    ");
                printWriter.print("mFocusedWindow=");
                printWriter.println(systemKeyManager.mFocusedWindow);
                printWriter.print("    ");
                printWriter.println("SystemKeyInfo=");
                int[] iArr = SystemKeyManager.SUPPORT_KEYCODE;
                for (int i5 = 0; i5 < 6; i5++) {
                    HashMap hashMap = (HashMap) systemKeyManager.mSystemKeyInfoMap.get(iArr[i5]);
                    if (hashMap != null) {
                        for (Map.Entry entry : hashMap.entrySet()) {
                            if (entry != null) {
                                SystemKeyManager.SystemKeyInfo systemKeyInfo = (SystemKeyManager.SystemKeyInfo) entry.getValue();
                                printWriter.print("    ");
                                printWriter.print("      ");
                                printWriter.println(systemKeyInfo.toString());
                            }
                        }
                    }
                }
                Iterator it2 = systemKeyManager.mMetaKeyRequestedComponents.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    printWriter.print("    ");
                    printWriter.print("      ");
                    printWriter.print("META_KEY=");
                    printWriter.println(str2);
                }
            } finally {
            }
        }
        if (InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY) {
            printWriter.print("    ");
            printWriter.print("mIsScreenshotTriggered=");
            printWriter.print(this.mIsScreenshotTriggered);
            printWriter.print(" mGlobalActionsByKeyCombination=");
            printWriter.println(this.mGlobalActionsByKeyCombnation);
        }
        printWriter.print("    ");
        printWriter.print("bixbyComponentName=");
        printWriter.println(this.mBixbyService.mDefaultComponentName);
        if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
            printWriter.print("    ");
            printWriter.print("mQuintuplePressOnPowerBehavior=");
            printWriter.println(PhoneWindowManager.multiPressOnPowerBehaviorToString(this.mQuintuplePressOnPowerBehavior));
        }
        printWriter.print("    ");
        printWriter.print("mIsHapticsEnabled=");
        printWriter.print(this.mIsHapticsEnabled);
        printWriter.print(" mIsAssistHapticEnabled=");
        printWriter.print(this.mIsAssistHapticEnabled);
        printWriter.print(" mIsHapticsSupported=");
        printWriter.print(this.mIsHapticsSupported);
        printWriter.print(" mNavBarVirtualKeyHapticFeedbackEnabled=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "cameraSensorToggleSupported=", this.mPolicy.mNavBarVirtualKeyHapticFeedbackEnabled);
        printWriter.print(this.mIsCameraSensorToggleSupported);
        printWriter.print("    ");
        printWriter.print("micSensorToggleSupported=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mIsInteractionControlEnabled=", this.mIsMicSensorToggleSupported);
        printWriter.print(this.mIsInteractionControlEnabled);
        printWriter.print(" mIsPowerKeyBlocked=");
        printWriter.print(this.mIsPowerKeyBlocked);
        printWriter.print(" mIsVolumeKeyBlocked=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mIsDoubleTapToWakeUpSupported=", this.mIsVolumeKeyBlocked);
        printWriter.print(this.mIsDoubleTapToWakeUpSupported);
        printWriter.print(" mIsDoubleTapToWakeUp=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mLockTaskModeState=", this.mIsDoubleTapToWakeUp);
        BroadcastStats$$ExternalSyntheticOutline0.m(this.mLockTaskModeState, printWriter, "    ", "mDexKeyguardOccluded=");
        printWriter.print(this.mDexKeyguardOccluded);
        printWriter.print(" mDexKeyguardOccludedChanged=");
        printWriter.print(false);
        printWriter.print(" mPendingDexKeyguardOccluded=");
        printWriter.println(false);
        if (InputRune.PWM_SPEN) {
            printWriter.print("    ");
            printWriter.print("SPen state=");
            printWriter.print(this.mPenState);
            printWriter.print(" type=");
            printWriter.print(this.mPenType);
            printWriter.print(" vibrationEnabled=");
            printWriter.print(this.mPenVibrationEnabled);
            printWriter.print(" soundEnabled=");
            printWriter.println(this.mPenSoundEnabled);
            if (this.mPenSoundInfo == null) {
                printWriter.print("    ");
                printWriter.println("SoundInfo is null");
            } else {
                printWriter.print("    ");
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
        if (InputRune.PWM_SPEN_SCREEN_OFF_MEMO) {
            printWriter.print("    ");
            printWriter.print("mScreenOffMemoEnabled=");
            printWriter.println(this.mScreenOffMemoEnabled);
        }
        if (InputRune.PWM_FINGERPRINT_SIDE_TOUCH) {
            printWriter.print("    ");
            printWriter.print("mWakeAndUnlockTriggered=");
            printWriter.print(this.mWakeAndUnlockTriggered);
            printWriter.print(" mWakeAndUnfoldedTriggered=");
            printWriter.println(false);
        }
        printWriter.print("    ");
        printWriter.print("mIsPogoKeyboardConnected=");
        printWriter.println(this.mIsPogoKeyboardConnected);
        if (InputRune.PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD) {
            printWriter.print("    ");
            printWriter.print("mIsCallOpenDictation=");
            printWriter.println(this.mIsCallOpenDictation);
        }
        printWriter.print("    ");
        printWriter.print("mWakingUpReason=");
        printWriter.println(PowerManager.wakeReasonToString(this.mWakingUpReason));
        if (InputRune.PWM_XCOVER_AND_TOP_KEY) {
            printWriter.print("    ");
            printWriter.print("mIsCalledOpenDictationXCoverTop=");
            printWriter.println(this.mKeyCustomizationPolicy.mIsCalledOpenDictationXCoverTop);
        }
        if (InputRune.PWM_DOUBLE_TAP_PREMIUM_WATCH) {
            printWriter.print("    ");
            printWriter.print("mIsDoubleTapPremiumWatchOn=");
            printWriter.println(this.mIsDoubleTapPremiumWatchOn);
        }
    }

    public final void enableOrDisableDexMode() {
        if (!this.mPolicy.isUserSetupComplete()) {
            Log.d("PhoneWindowManagerExt", "enableOrDisableDexMode : User setup is not completed");
            return;
        }
        boolean z = CoreRune.MT_NEW_DEX && DesktopModeFeature.SUPPORT_NEW_DEX && "new".equals(DesktopModeSettings.getSettingsAsUser(this.mContext.getContentResolver(), "dex_mode", DesktopModeSettings.DEX_MODE_DEFAULT_VALUE, DesktopModeSettings.sCurrentUserId));
        Intent intent = new Intent("com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST");
        intent.putExtra(z ? "com.samsung.android.desktopmode.extra.NEW_DEX_MODE_STATE" : "com.samsung.android.desktopmode.extra.DESKTOP_MODE_STATE", this.mLastDexMode != 0 ? 2 : 1);
        intent.putExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_SOURCE", 6);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public final boolean externalKeyboardPolicy() {
        if (!this.mBootCompleted) {
            Log.d("PhoneWindowManagerExt", "preCondition : Boot is not completed");
            return true;
        }
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (!phoneWindowManager.isUserSetupComplete()) {
            Log.d("PhoneWindowManagerExt", "preCondition : User setup is not completed");
            return true;
        }
        if (phoneWindowManager.keyguardOn()) {
            Log.d("PhoneWindowManagerExt", "preCondition : Keyguard is shown");
            return true;
        }
        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
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
        return WmCoverState.sIsEnabled && (((CoverState) WmCoverState.getInstance()).switchState ^ true) && this.mLastDexMode == 0;
    }

    public final AudioManager getAudioManager() {
        AudioManager audioManager;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mAudioManager == null) {
                    this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
                }
                audioManager = this.mAudioManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return audioManager;
    }

    public final int getDisplayId(KeyEvent keyEvent) {
        if (keyEvent == null) {
            if (keyEvent == null) {
                return 0;
            }
            return keyEvent.getDisplayId();
        }
        int displayId = keyEvent.getDisplayId();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 221 || keyCode == 220 || !isInDexMode() || displayId != -1) {
            return displayId;
        }
        int i = this.mWindowManagerFuncs.mService.mInputManager.mDisplayIdForPointerIcon;
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        return i;
    }

    public final void handleGlobalInteractiveIfNeeded(int i, int i2) {
        int i3 = this.mPendingDualModeInteractive;
        if ((i3 & i) == 0) {
            return;
        }
        this.mPendingDualModeInteractive = i3 & (~i);
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate == null) {
            return;
        }
        KeyguardServiceDelegate.KeyguardState keyguardState = keyguardServiceDelegate.mKeyguardState;
        if (i == 1) {
            if (keyguardServiceDelegate.mKeyguardService != null) {
                Log.v("KeyguardServiceDelegate", "onStartedWakingUp()");
                keyguardServiceDelegate.mKeyguardService.onStartedWakingUp(i2, false);
            }
            keyguardState.interactiveState = 1;
            return;
        }
        if (i == 2) {
            if (keyguardServiceDelegate.mKeyguardService != null) {
                Log.v("KeyguardServiceDelegate", "onFinishedWakingUp()");
                keyguardServiceDelegate.mKeyguardService.onFinishedWakingUp();
            }
            keyguardState.interactiveState = 2;
            return;
        }
        if (i == 4) {
            KeyguardServiceWrapper keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService;
            if (keyguardServiceWrapper != null) {
                keyguardServiceWrapper.onStartedGoingToSleep(i2);
            }
            keyguardState.offReason = WindowManagerPolicyConstants.translateSleepReasonToOffReason(i2);
            keyguardState.interactiveState = 3;
            return;
        }
        if (i != 8) {
            return;
        }
        KeyguardServiceWrapper keyguardServiceWrapper2 = keyguardServiceDelegate.mKeyguardService;
        if (keyguardServiceWrapper2 != null) {
            keyguardServiceWrapper2.onFinishedGoingToSleep(i2, false);
        }
        keyguardState.interactiveState = 0;
    }

    public final boolean handleLongPressOnHomeWithPolicy(int i, long j) {
        if (longPressOnHomePolicy()) {
            return false;
        }
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mLongPressOnHomeBehavior == 0) {
            return false;
        }
        boolean z = InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE;
        if (!z || !isLongPressHomeSearcle()) {
            if (this.mIsAssistHapticEnabled) {
                performHapticFeedback(Process.myUid(), 0, this.mContext.getOpPackageName(), "Home - Long Press", true, false);
            } else {
                Slog.d("PhoneWindowManagerExt", "home long press haptic disabled");
            }
        }
        PhoneWindowManager phoneWindowManager2 = this.mPolicy;
        int i2 = phoneWindowManager2.mLongPressOnHomeBehavior;
        if (i2 == 1) {
            phoneWindowManager2.launchAllAppsAction();
        } else if (i2 == 2) {
            phoneWindowManager2.launchAssistAction(i, 5, j, null);
            if (InputRune.PWM_KEY_SA_LOGGING) {
                if (!TextUtils.isEmpty(null)) {
                    throw null;
                }
                sendCoreSaLoggingDimension("NAVIB1003", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
            }
        } else if (i2 == 3) {
            phoneWindowManager2.toggleNotificationPanel(0);
        } else if (i2 != 4 && i2 != 101) {
            AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("Undefined long press on home behavior: "), phoneWindowManager.mLongPressOnHomeBehavior, "PhoneWindowManagerExt");
        } else if (z) {
            startSearcleByHomeKey(false, true);
        }
        return true;
    }

    public final void handleLongPressOnRecent(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (this.mKeyCustomizationPolicy.getLastAction(4, FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED) == 4) {
            return;
        }
        if (this.mLockTaskModeState != 0 && phoneWindowManager.mAccessibilityManager.isEnabled() && phoneWindowManager.mAccessibilityManager.isTouchExplorationEnabled()) {
            try {
                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                ActivityTaskManager.getService().stopSystemLockTaskMode();
                return;
            } catch (RemoteException e) {
                Log.d("PhoneWindowManagerExt", "Unable to reach activity manager", e);
                return;
            }
        }
        if (phoneWindowManager.keyguardOn()) {
            return;
        }
        if (isInDexMode()) {
            phoneWindowManager.toggleRecentApps(i);
        } else {
            phoneWindowManager.toggleRecentApps(0);
        }
    }

    public final boolean hasMetaKeyPass() {
        SystemKeyManager systemKeyManager = this.mSystemKeyPolicy;
        synchronized (systemKeyManager) {
            try {
                if (!systemKeyManager.mMetaKeyPass) {
                    return false;
                }
                String str = KeyCustomizationConstants.VOLD_DECRYPT;
                if (PhoneWindowManager.DEBUG_INPUT) {
                    Slog.i("SystemKeyManager", "hasMetaKeyPass() : MetaKey is blocked by componentName=" + systemKeyManager.mFocusedWindow);
                }
                return true;
            } finally {
            }
        }
    }

    public final int hasRequestedActionBlockKeyEvent(int i, int i2, boolean z) {
        SemWindowManager.KeyCustomizationInfo last = this.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.getLast(3, i);
        if (last == null || last.action != 4) {
            return -1;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Key was blocked by KeyCustomizationPolicy. keyCode=", "PhoneWindowManagerExt");
        if (!z || i2 != 0 || last.id != 10) {
            return 2;
        }
        Context context = this.mContext;
        showToast(context, context.getResources().getString(R.string.permdesc_nearby_wifi_devices));
        return 2;
    }

    public final boolean hasSingleKeyRule(int i) {
        return (i == 3 || i == 4 || i == 24 || i == 25 || i == 79 || i == 187 || i == 1015 || i == 1079) && this.mPolicy.mSingleKeyGestureDetector.hasRule(i);
    }

    public final void init() {
        init(new Injector(this.mContext, this.mPolicy));
    }

    public void init(Injector injector) {
        InputManager inputManager;
        this.mHandler = new PolicyExtHandler();
        final SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSettingsObserver = settingsObserver;
        final ContentResolver contentResolver = this.mContext.getContentResolver();
        if (InputRune.PWM_SUPPORT_DICTATION_SAMSUNG_KEYBOARD || InputRune.PWM_XCOVER_AND_TOP_KEY) {
            final int i = 0;
            settingsObserver.registerSettings(Settings.Secure.getUriFor("default_input_method"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i2 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i2 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i2;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i3 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i3 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i3;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        settingsObserver.registerSettings(Settings.System.getUriFor("any_screen_enabled"), new PhoneWindowManagerExt$$ExternalSyntheticLambda2(5, settingsObserver));
        final int i2 = 7;
        settingsObserver.registerSettings(Settings.Global.getUriFor("navigation_bar_button_to_hide_keyboard"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i3 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i3 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i3;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i3 = 8;
        settingsObserver.registerSettings(Settings.Secure.getUriFor("show_keyboard_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i3) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        boolean z = InputRune.PWM_SPEN;
        if (z) {
            final int i4 = 9;
            settingsObserver.registerSettings(Settings.System.getUriFor("pen_attach_detach_vibration"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i4) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
            final int i5 = 11;
            settingsObserver.registerSettings(Settings.System.getUriFor("spen_feedback_sound"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i5) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
            settingsObserver.registerSettings(Settings.System.getUriFor("pen_detachment_notification"), this.mPenDetachNotiConsumer);
        }
        boolean z2 = InputRune.PWM_SPEN_SCREEN_OFF_MEMO;
        if (z2) {
            final int i6 = 12;
            settingsObserver.registerSettings(Settings.System.getUriFor("screen_off_memo"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i6) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        if (InputRune.PWM_QUICK_LAUNCH_CAMERA) {
            settingsObserver.registerSettings(Settings.System.getUriFor("double_tab_launch"), this.mQuickLaunchCameraConsumer);
        }
        boolean z3 = InputRune.PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE;
        if (z3) {
            settingsObserver.registerSettings(Settings.System.getUriFor("tvmode_state"), this.mTvModeStateConsumer);
            settingsObserver.registerSettings(Settings.System.getUriFor("pwrkey_owner_status"), this.mTvModeStateDoublePressConsumer);
            settingsObserver.registerSettings(Settings.System.getUriFor("double_tab_launch_component"), this.mDoublePressLaunchComponentConsumer);
        }
        final int i7 = 13;
        settingsObserver.registerSettings(Settings.System.getUriFor("haptic_feedback_enabled"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i7) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i8 = 14;
        settingsObserver.registerSettings(Settings.System.getUriFor("default_assist_vibration_feedback"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i8) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i9 = 15;
        settingsObserver.registerSettings(Settings.System.getUriFor("anykey_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i9) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        if (InputRune.PWM_SKT_PHONE_RELAX_MODE) {
            final int i10 = 10;
            settingsObserver.registerSettings(Settings.System.getUriFor("skt_phone20_relax_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i10) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        final int i11 = 16;
        settingsObserver.registerSettings(Settings.System.getUriFor("access_control_enabled"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i11) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i12 = 17;
        settingsObserver.registerSettings(Settings.System.getUriFor("access_control_power_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i12) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i13 = 18;
        settingsObserver.registerSettings(Settings.System.getUriFor("access_control_volume_button"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i13) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i14 = 19;
        settingsObserver.registerSettings(Settings.System.getUriFor("double_tab_to_wake_up"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i14) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY) {
            final int i15 = 20;
            settingsObserver.registerSettings(Settings.System.getUriFor("active_key_on_lockscreen"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i15) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        if (InputRune.PWM_XCOVER_AND_TOP_KEY) {
            final int i16 = 21;
            settingsObserver.registerSettings(Settings.System.getUriFor("xcover_top_key_on_lockscreen"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i16) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        if (InputRune.PWM_DOUBLE_TAP_PREMIUM_WATCH) {
            final int i17 = 22;
            settingsObserver.registerSettings(Settings.System.getUriFor("premium_tap_for_watch_face_switch_on_off"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i17) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        final int i18 = 23;
        settingsObserver.registerSettings(Settings.Secure.getUriFor("development_custom_bugreport_writer"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i18) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i19 = 1;
        settingsObserver.registerSettings(Settings.System.getUriFor("issuetracker_logged_in"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i19) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        if (CoreRune.FW_CHN_PREMIUM_WATCH) {
            final int i20 = 2;
            settingsObserver.registerSettings(Settings.System.getUriFor("premium_watch_switch_onoff"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i20) {
                        case 0:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                            ContentResolver contentResolver2 = contentResolver;
                            settingsObserver2.getClass();
                            PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                            break;
                        case 1:
                            PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                            break;
                        case 2:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                            ContentResolver contentResolver3 = contentResolver;
                            PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                            Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                            phoneWindowManagerExt.getClass();
                            break;
                        case 3:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                            ContentResolver contentResolver4 = contentResolver;
                            settingsObserver4.getClass();
                            float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                            PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                            if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                                phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                                phoneWindowManagerExt2.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 4:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                            ContentResolver contentResolver5 = contentResolver;
                            settingsObserver5.getClass();
                            int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                            if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                                phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                                phoneWindowManagerExt3.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 5:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                            ContentResolver contentResolver6 = contentResolver;
                            settingsObserver6.getClass();
                            int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                            PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                            if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                                phoneWindowManagerExt4.mBoldFontEnabled = i32;
                                phoneWindowManagerExt4.mHandler.removeMessages(2);
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                                if (PhoneWindowManagerExt.this.isInDexMode()) {
                                    PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                    policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                    break;
                                }
                            }
                            break;
                        case 6:
                            PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                            break;
                        case 7:
                            PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                            break;
                        case 8:
                            PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                            break;
                        case 9:
                            PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                            break;
                        case 10:
                            PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                            break;
                        case 11:
                            PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                            break;
                        case 12:
                            PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                            break;
                        case 13:
                            PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                            break;
                        case 14:
                            PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                            break;
                        case 15:
                            PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                            break;
                        case 16:
                            PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                            break;
                        case 17:
                            PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                            break;
                        case 18:
                            PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                            break;
                        case 19:
                            PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                            break;
                        case 20:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 21:
                            PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                            break;
                        case 22:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                            PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                            break;
                        default:
                            PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                            ContentResolver contentResolver7 = contentResolver;
                            PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                            break;
                    }
                }
            });
        }
        final int i21 = 3;
        settingsObserver.registerSettings(Settings.System.getUriFor("cursor_thickness"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i21) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i22 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i22 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i22;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i22 = 4;
        settingsObserver.registerSettings(Settings.System.getUriFor("show_button_background"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i22) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i222 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i222 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i222;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i23 = 5;
        settingsObserver.registerSettings(Settings.Global.getUriFor("bold_text"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i23) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i222 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i222 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i222;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        final int i24 = 6;
        settingsObserver.registerSettings(Settings.System.getUriFor("volumekey_mode"), new Consumer() { // from class: com.android.server.policy.PhoneWindowManagerExt$SettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i24) {
                    case 0:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver2 = settingsObserver;
                        ContentResolver contentResolver2 = contentResolver;
                        settingsObserver2.getClass();
                        PhoneWindowManagerExt.this.mIsSamsungKeyboard = "com.samsung.android.honeyboard/.service.HoneyBoardService".equals(Settings.Secure.getString(contentResolver2, "default_input_method"));
                        break;
                    case 1:
                        PhoneWindowManagerExt.this.mIssueTrackerLoggedIn = Settings.System.getIntForUser(contentResolver, "issuetracker_logged_in", 0, -2) == 1;
                        break;
                    case 2:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver3 = settingsObserver;
                        ContentResolver contentResolver3 = contentResolver;
                        PhoneWindowManagerExt phoneWindowManagerExt = PhoneWindowManagerExt.this;
                        Settings.System.getInt(contentResolver3, "premium_watch_switch_onoff", 0);
                        phoneWindowManagerExt.getClass();
                        break;
                    case 3:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver4 = settingsObserver;
                        ContentResolver contentResolver4 = contentResolver;
                        settingsObserver4.getClass();
                        float floatForUser = Settings.System.getFloatForUser(contentResolver4, "cursor_thickness", 1.0f, -2);
                        PhoneWindowManagerExt phoneWindowManagerExt2 = PhoneWindowManagerExt.this;
                        if (floatForUser != phoneWindowManagerExt2.mCursorThicknessScale) {
                            phoneWindowManagerExt2.mCursorThicknessScale = floatForUser;
                            phoneWindowManagerExt2.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler.sendMessage(policyExtHandler.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler2 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler2.sendMessage(policyExtHandler2.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 4:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver5 = settingsObserver;
                        ContentResolver contentResolver5 = contentResolver;
                        settingsObserver5.getClass();
                        int i222 = Settings.System.getInt(contentResolver5, "show_button_background", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                        if (i222 != phoneWindowManagerExt3.mButtonShapeEnabled) {
                            phoneWindowManagerExt3.mButtonShapeEnabled = i222;
                            phoneWindowManagerExt3.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler3 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler3.sendMessage(policyExtHandler3.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler4 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler4.sendMessage(policyExtHandler4.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 5:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver6 = settingsObserver;
                        ContentResolver contentResolver6 = contentResolver;
                        settingsObserver6.getClass();
                        int i32 = Settings.Global.getInt(contentResolver6, "bold_text", 0);
                        PhoneWindowManagerExt phoneWindowManagerExt4 = PhoneWindowManagerExt.this;
                        if (i32 != phoneWindowManagerExt4.mBoldFontEnabled) {
                            phoneWindowManagerExt4.mBoldFontEnabled = i32;
                            phoneWindowManagerExt4.mHandler.removeMessages(2);
                            PhoneWindowManagerExt.PolicyExtHandler policyExtHandler5 = PhoneWindowManagerExt.this.mHandler;
                            policyExtHandler5.sendMessage(policyExtHandler5.obtainMessage(2, 0));
                            if (PhoneWindowManagerExt.this.isInDexMode()) {
                                PhoneWindowManagerExt.PolicyExtHandler policyExtHandler6 = PhoneWindowManagerExt.this.mHandler;
                                policyExtHandler6.sendMessage(policyExtHandler6.obtainMessage(2, 2));
                                break;
                            }
                        }
                        break;
                    case 6:
                        PhoneWindowManagerExt.this.mIsVolumeUpKeyMode = Settings.System.getIntForUser(contentResolver, "volumekey_mode", 0, -2) == 1;
                        break;
                    case 7:
                        PhoneWindowManagerExt.this.mNavBarImeBtnEnabled = Settings.Global.getInt(contentResolver, "navigation_bar_button_to_hide_keyboard", 1) == 1;
                        break;
                    case 8:
                        PhoneWindowManagerExt.this.mShowKeyboardBtnEnabled = Settings.Secure.getInt(contentResolver, "show_keyboard_button", 1) == 1;
                        break;
                    case 9:
                        PhoneWindowManagerExt.this.mPenVibrationEnabled = Settings.System.getIntForUser(contentResolver, "pen_attach_detach_vibration", 1, -2) == 1;
                        break;
                    case 10:
                        PhoneWindowManagerExt.this.mIsSktPhoneRelaxMode = Settings.System.getIntForUser(contentResolver, "skt_phone20_relax_mode", 0, -2) == 1;
                        break;
                    case 11:
                        PhoneWindowManagerExt.this.mPenSoundEnabled = Settings.System.getIntForUser(contentResolver, "spen_feedback_sound", 0, -2) == 1;
                        break;
                    case 12:
                        PhoneWindowManagerExt.this.mScreenOffMemoEnabled = Settings.System.getIntForUser(contentResolver, "screen_off_memo", 0, -2) == 1;
                        break;
                    case 13:
                        PhoneWindowManagerExt.this.mIsHapticsEnabled = Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 0, -2) == 1;
                        break;
                    case 14:
                        PhoneWindowManagerExt.this.mIsAssistHapticEnabled = Settings.System.getIntForUser(contentResolver, "default_assist_vibration_feedback", 0, -2) == 1;
                        break;
                    case 15:
                        PhoneWindowManagerExt.this.mIsAnyKeyMode = Settings.System.getIntForUser(contentResolver, "anykey_mode", 0, -2) == 1;
                        break;
                    case 16:
                        PhoneWindowManagerExt.this.mIsInteractionControlEnabled = Settings.System.getIntForUser(contentResolver, "access_control_enabled", 0, -2) == 1;
                        break;
                    case 17:
                        PhoneWindowManagerExt.this.mIsPowerKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_power_button", 1, -2) == 1;
                        break;
                    case 18:
                        PhoneWindowManagerExt.this.mIsVolumeKeyBlocked = Settings.System.getIntForUser(contentResolver, "access_control_volume_button", 1, -2) == 1;
                        break;
                    case 19:
                        PhoneWindowManagerExt.this.mIsDoubleTapToWakeUp = Settings.System.getIntForUser(contentResolver, "double_tab_to_wake_up", 0, -2) == 1;
                        break;
                    case 20:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsXCoverKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "active_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 21:
                        PhoneWindowManagerExt.this.mKeyCustomizationPolicy.mIsTopKeyOnLockScreen = Settings.System.getIntForUser(contentResolver, "xcover_top_key_on_lockscreen", 0, -3) == 1;
                        break;
                    case 22:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver7 = settingsObserver;
                        PhoneWindowManagerExt.this.mIsDoubleTapPremiumWatchOn = Settings.System.getInt(contentResolver, "premium_tap_for_watch_face_switch_on_off", 1) == 1;
                        break;
                    default:
                        PhoneWindowManagerExt.SettingsObserver settingsObserver8 = settingsObserver;
                        ContentResolver contentResolver7 = contentResolver;
                        PhoneWindowManagerExt.this.mIsCustomBugreportWriterEnabled = Settings.Secure.getIntForUser(contentResolver7, "development_custom_bugreport_writer", 0, -2) == 1;
                        break;
                }
            }
        });
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        boolean z4 = false;
        if (z3) {
            this.mTvModeEnabled = Settings.System.getInt(contentResolver2, "tvmode_state", 0) == 1;
            this.mTvModeDoublePressEnabled = Settings.System.getInt(contentResolver2, "pwrkey_owner_status", 0) == 1;
            updateDoublePressLaunchInfo(Settings.System.getString(contentResolver2, "double_tab_launch_component"));
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
        intentFilter.setPriority(1000);
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(this.mBootCompleteReceiver, userHandle, intentFilter, null, null);
        if (InputRune.PWM_SIDE_KEY_WAKE_UP_BIXBY) {
            this.mContext.registerReceiverAsUser(this.mSetupWizardGlobalActionReceiver, userHandle, BatteryService$$ExternalSyntheticOutline0.m("com.sec.android.app.secsetupwizard.GLOBAL_ACTION"), null, null, 2);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageChangeReceiver, userHandle, intentFilter2, null, null, 2);
        this.mContext.registerReceiver(this.mMultiuserReceiver, new IntentFilter("android.intent.action.USER_REMOVED"));
        if (CoreRune.SYSUI_GRADLE_BUILD) {
            IntentFilter intentFilter3 = new IntentFilter("android.intent.action.PACKAGE_REPLACED");
            intentFilter3.addDataScheme("package");
            this.mContext.registerReceiver(new AnonymousClass2(this, 1), intentFilter3);
        }
        this.mContext.registerReceiverAsUser(this.mProximityChangeReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY", "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY"), null, null, 4);
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            this.mContext.registerReceiver(this.mFoldSaLoggingReceiver, new IntentFilter("com.samsung.android.intent.action.WINNER_LOGGING"), 2);
        }
        if (z) {
            Intent intent = new Intent("com.samsung.pen.INSERT");
            this.mPenInsertIntent = intent;
            intent.addFlags(16777216);
            if (z2) {
                Intent intent2 = new Intent();
                this.mScreenOffMemoIntent = intent2;
                intent2.setComponent(ComponentName.unflattenFromString("com.samsung.android.app.notes/com.samsung.android.app.notes.screenoffmemo.ScreenOffMemoService"));
            }
        }
        this.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda1(2, this));
        injector.getClass();
        SystemKeyManager systemKeyManager = new SystemKeyManager();
        systemKeyManager.mFocusedWindow = null;
        systemKeyManager.mMetaKeyRequestedComponents = new HashSet();
        systemKeyManager.mMetaKeyPass = false;
        systemKeyManager.mSystemKeyInfoMap = new SparseArray();
        systemKeyManager.mIsActivatedRecentKey = false;
        systemKeyManager.mIsActivatedHomeKey = false;
        PhoneWindowManager phoneWindowManager = injector.mPhoneWindowManager;
        systemKeyManager.mPolicy = phoneWindowManager;
        this.mSystemKeyPolicy = systemKeyManager;
        this.mBixbyService = new BixbyService(injector.mContext, phoneWindowManager.mExt);
        boolean z5 = InputRune.PWM_SIDE_KEY_TRIPLE_PRESS_PANIC_CALL;
        PhoneWindowManager phoneWindowManager2 = this.mPolicy;
        if (z5) {
            phoneWindowManager2.mTriplePressOnPowerBehavior = 102;
        }
        this.mVibrator = ((VibratorManager) this.mContext.getSystemService("vibrator_manager")).getDefaultVibrator();
        this.mHapticFeedbackVibrationProvider = new HapticFeedbackVibrationProvider(this.mContext.getResources(), this.mVibrator);
        this.mIsHapticsSupported = this.mVibrator.semGetSupportedVibrationType() > 1;
        this.mKeyboardShortcutPolicy = new KeyboardShortcutManager(this.mContext, this);
        this.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda1(3, this));
        SensorPrivacyManager sensorPrivacyManager = SensorPrivacyManager.getInstance(this.mContext);
        this.mIsCameraSensorToggleSupported = sensorPrivacyManager.supportsSensorToggle(2) && DeviceConfig.getBoolean("privacy", "camera_toggle_enabled", true);
        if (sensorPrivacyManager.supportsSensorToggle(1) && DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true)) {
            z4 = true;
        }
        this.mIsMicSensorToggleSupported = z4;
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        final ?? r1 = new WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.13
            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public final void onAppTransitionCancelledLocked(boolean z6) {
                PhoneWindowManager phoneWindowManager3 = PhoneWindowManagerExt.this.mPolicy;
                if (z6) {
                    String str = KeyCustomizationConstants.VOLD_DECRYPT;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    if (phoneWindowManager3.mKeyguardDelegate != null) {
                        if (PhoneWindowManager.DEBUG_KEYGUARD) {
                            Slog.d("WindowManager", "PWM.startKeyguardExitAnimation");
                        }
                        KeyguardServiceWrapper keyguardServiceWrapper = phoneWindowManager3.mKeyguardDelegate.mKeyguardService;
                        if (keyguardServiceWrapper != null) {
                            keyguardServiceWrapper.startKeyguardExitAnimation(uptimeMillis, 0L);
                        }
                    }
                }
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public final void onAppTransitionStartingLocked(long j) {
                PhoneWindowManager phoneWindowManager3 = PhoneWindowManagerExt.this.mPolicy;
            }
        };
        phoneWindowManager2.mDisplayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.server.policy.PhoneWindowManagerExt.14
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i25) {
                if (i25 == 2) {
                    PhoneWindowManagerExt.this.mPolicy.mWindowManagerInternal.registerAppTransitionListener(r1, i25);
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i25) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i25) {
                if (i25 == 2) {
                    PhoneWindowManagerExt.this.mPolicy.mWindowManagerInternal.unregisterAppTransitionListener(r1, i25);
                }
            }
        }, this.mHandler);
        if (CoreRune.FW_FLEXIBLE_TABLE_MODE && (inputManager = InputManager.getInstance()) != null) {
            inputManager.semRegisterOnMultiFingerGestureListener(this.mMultiFingerGestureListener, null);
        }
        this.mProKioskManager = ProKioskManager.getInstance();
        this.mShownEsc = !SystemProperties.get("persist.service.esc.dialog", "null").equals("null");
        if (InputRune.PWM_SIDE_KEY_DIGITAL_ASSISTANT) {
            new RoleObserver();
        }
    }

    public final void injectionKeyEvent(final int i, final int i2, final int i3) {
        Thread thread = this.mKeyEventInjectionThread;
        if (thread != null && thread.isAlive()) {
            this.mKeyEventInjectionThread.interrupt();
        }
        Thread thread2 = new Thread(new Runnable() { // from class: com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                long uptimeMillis = SystemClock.uptimeMillis();
                try {
                    Instrumentation instrumentation = new Instrumentation();
                    KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, 0, i4, 0, 0, -1, 0, i5, 0);
                    keyEvent.semSetDisplayId(i6);
                    instrumentation.sendKeySync(keyEvent);
                    KeyEvent keyEvent2 = new KeyEvent(uptimeMillis, uptimeMillis, 1, i4, 0, 0, -1, 0, i5, 0);
                    keyEvent2.semSetDisplayId(i6);
                    instrumentation.sendKeySync(keyEvent2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.mKeyEventInjectionThread = thread2;
        thread2.start();
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
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long interceptKeyBeforeDispatching(android.os.IBinder r26, android.view.KeyEvent r27, int r28) {
        /*
            Method dump skipped, instructions count: 2952
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.interceptKeyBeforeDispatching(android.os.IBinder, android.view.KeyEvent, int):long");
    }

    public final boolean isArchived(Intent intent) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        int semGetCurrentUser = ActivityManager.semGetCurrentUser();
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        String str = intent.getPackage();
        if (TextUtils.isEmpty(str) && intent.getComponent() != null) {
            str = intent.getComponent().getPackageName();
        }
        try {
            packageInfo = packageManager.getPackageInfoAsUser(str, PackageManager.PackageInfoFlags.of(4294967296L), semGetCurrentUser);
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " is not archived : ");
            m.append(e.getMessage());
            Log.d("PhoneWindowManagerExt", m.toString());
            packageInfo = null;
        }
        return (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null || !applicationInfo.isArchived) ? false : true;
    }

    public final boolean isCarrierLocked() {
        int currentUser = ActivityManager.getCurrentUser();
        return this.mLockPatternUtils.isCarrierLockEnabled(currentUser) || this.mLockPatternUtils.isFMMLockEnabled(currentUser);
    }

    public final boolean isDisplayInDexMode(int i) {
        int i2 = this.mLastDexMode;
        if (i2 == 2 && i == 2) {
            return true;
        }
        return i2 == 1 && i == 0;
    }

    public final boolean isDoubleTapToWakeUp(int i) {
        return i == 224 && this.mIsDoubleTapToWakeUpSupported && this.mIsDoubleTapToWakeUp;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isGoogleQuickSearchBoxAsDigitalAssistant() {
        /*
            r3 = this;
            android.content.Context r3 = r3.mContext
            java.lang.String r0 = "role"
            java.lang.Object r3 = r3.getSystemService(r0)
            android.app.role.RoleManager r3 = (android.app.role.RoleManager) r3
            r0 = 0
            if (r3 == 0) goto L21
            java.lang.String r1 = "android.app.role.ASSISTANT"
            java.util.List r3 = r3.getRoleHolders(r1)
            int r1 = r3.size()
            if (r1 <= 0) goto L21
            java.lang.Object r3 = r3.get(r0)
            java.lang.String r3 = (java.lang.String) r3
            goto L23
        L21:
            java.lang.String r3 = ""
        L23:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "digital assistant ::"
            r1.<init>(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "PhoneWindowManagerExt"
            android.util.Slog.d(r2, r1)
            java.lang.String r1 = "com.google.android.googlequicksearchbox"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L40
            r3 = 1
            return r3
        L40:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.isGoogleQuickSearchBoxAsDigitalAssistant():boolean");
    }

    public final boolean isInDexMode() {
        return this.mLastDexMode != 0;
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

    public final boolean isKeyguardOccluded(int i) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mKeyguardDelegate == null) {
            return false;
        }
        return i == 2 ? this.mDexKeyguardOccluded : phoneWindowManager.isKeyguardOccluded();
    }

    public final boolean isLongPressHomeSearcle() {
        int i = this.mPolicy.mLongPressOnHomeBehavior;
        return i == 4 || i == 101;
    }

    public final boolean isQuickLaunchCameraEnabled(int i) {
        if (this.mKeyCustomizationPolicy.getLastId(8, i) != 2001) {
            if (isCameraRunning()) {
                boolean equals = SystemProperties.get("service.camera.client", "").equals("com.sec.android.app.camera");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("isCameraClient=", "PhoneWindowManagerExt", equals);
                if (equals) {
                }
            }
            return false;
        }
        return true;
    }

    public final boolean isSamsungKeyboardShown() {
        if (!this.mIsSamsungKeyboard) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) SafetySystemService.getSystemService(InputMethodManager.class);
        return inputMethodManager != null ? inputMethodManager.isInputMethodShown() : false;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean knoxCustomVolumeKeyAppSwitching(boolean r11) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.knoxCustomVolumeKeyAppSwitching(boolean):boolean");
    }

    public void launchPowerDoublePressCamera() {
        GestureLauncherService gestureLauncherService = (GestureLauncherService) LocalServices.getService(GestureLauncherService.class);
        if (gestureLauncherService == null) {
            return;
        }
        Log.i("PhoneWindowManagerExt", "launch double press camera");
        gestureLauncherService.handleCameraGesture(false, 1);
    }

    public final boolean longPressOnHomePolicy() {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (!phoneWindowManager.isUserSetupComplete()) {
            Log.i("PhoneWindowManagerExt", "Home long press is blocked because UserSetup isn't completed");
            return true;
        }
        TelecomManager telecommService = phoneWindowManager.getTelecommService();
        if (!InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE && telecommService != null && telecommService.isRinging()) {
            Log.i("PhoneWindowManagerExt", "Home long press is blocked by Ringing");
            return true;
        }
        if (!isInteractionControlEnabled(3, false)) {
            return false;
        }
        Log.i("PhoneWindowManagerExt", "Home long press is blocked by Interaction control");
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyPenSwitchChanged(long r21, boolean r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.notifyPenSwitchChanged(long, boolean, boolean):void");
    }

    public final void onDexModeChangedLw(int i) {
        if (this.mLastDexMode == i) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "onDexModeChangedLw: called same dexMode=", "PhoneWindowManagerExt");
            return;
        }
        this.mLastDexMode = i;
        if (i != 2) {
            List asList = Arrays.asList(1, 2);
            for (int i2 = 0; i2 < asList.size() && this.mPendingDualModeInteractive > 0; i2++) {
                handleGlobalInteractiveIfNeeded(((Integer) asList.get(i2)).intValue(), 114);
            }
            this.mPendingDualModeInteractive = 0;
        }
        this.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda0(this, i, 0));
    }

    public final void onFlashlightKeyPressed(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "onFlashlightKeyPressed, keyCode=", "PhoneWindowManagerExt");
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        StatusBarManagerService.AnonymousClass2 anonymousClass2 = (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
        StatusBarManagerService.this.enforceStatusBarService();
        Iterator it = StatusBarManagerService.this.mBarMap.entrySet().iterator();
        while (it.hasNext()) {
            IStatusBar iStatusBar = (IStatusBar) ((Map.Entry) it.next()).getValue();
            if (iStatusBar != null) {
                try {
                    iStatusBar.onFlashlightKeyPressed(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void onKeyguardOccludedChangedLw(int i, boolean z) {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        KeyguardServiceDelegate keyguardServiceDelegate = phoneWindowManager.mKeyguardDelegate;
        if (keyguardServiceDelegate == null || i != 2) {
            if (keyguardServiceDelegate != null) {
                phoneWindowManager.mPendingKeyguardOccluded = z;
                phoneWindowManager.mKeyguardOccludedChanged = true;
                return;
            }
            return;
        }
        if (keyguardServiceDelegate == null) {
            Slog.d("PhoneWindowManagerExt", "setKeyguardOccludedLw mKeyguardDelegate is null");
            return;
        }
        if (PhoneWindowManager.DEBUG_KEYGUARD) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("setKeyguardOccluded occluded=", "PhoneWindowManagerExt", z);
        }
        this.mPolicy.mKeyguardOccludedChanged = false;
        if (isKeyguardOccluded(i) == z) {
            return;
        }
        if (i == 2) {
            this.mDexKeyguardOccluded = z;
        }
        KeyguardServiceDelegate keyguardServiceDelegate2 = this.mPolicy.mKeyguardDelegate;
        if (i == 2) {
            if (keyguardServiceDelegate2.mKeyguardService != null) {
                Log.v("KeyguardServiceDelegate", "setOccluded(" + z + ") , dex displayId=" + i);
                keyguardServiceDelegate2.mKeyguardService.setDexOccluded(z);
            }
            keyguardServiceDelegate2.mKeyguardState.dexOccluded = z;
        } else {
            keyguardServiceDelegate2.setOccluded(z);
        }
        this.mPolicy.mKeyguardDelegate.isShowing();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performHapticFeedback(int r15, int r16, java.lang.String r17, java.lang.String r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManagerExt.performHapticFeedback(int, int, java.lang.String, java.lang.String, boolean, boolean):boolean");
    }

    public final void performSystemKeyFeedback(KeyEvent keyEvent) {
        InputDevice device = keyEvent.getDevice();
        if (keyEvent.getDeviceId() != -1) {
            if (device == null || !"SPC_Remote_Controller".equals(device.getName())) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 4) {
                    if (keyCode != 27) {
                        if (keyCode != 82) {
                            if (keyCode == 84) {
                                if (this.mIsHapticsSupported) {
                                    performHapticFeedback(Process.myUid(), HapticFeedbackConstants.semGetVibrationIndex(2), this.mContext.getOpPackageName(), "Search key - Press", false, false);
                                }
                                playSoundEffect();
                                return;
                            }
                            if (keyCode != 187) {
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
                    performHapticFeedback(Process.myUid(), HapticFeedbackConstants.semGetVibrationIndex(2), this.mContext.getOpPackageName(), BinaryTransparencyService$$ExternalSyntheticOutline0.m(keyCode, "keyCode(", ") - Press"), false, false);
                }
                playSoundEffect();
            }
        }
    }

    public final void playSoundEffect() {
        AudioManager audioManager = getAudioManager();
        if (audioManager == null) {
            Slog.w("PhoneWindowManagerExt", "Couldn't get audio manager");
        } else if (this.mPolicy.isKeyguardShowingAndNotOccluded() || "trigger_restart_min_framework".equals(KeyCustomizationConstants.VOLD_DECRYPT)) {
            Slog.w("PhoneWindowManagerExt", "keyguard - disable key sound");
        } else {
            audioManager.playSoundEffect(102, ActivityManager.getCurrentUser());
        }
    }

    public final void sendBroadcastDoubleClick(int i) {
        if (this.mWindowManagerFuncs.mService.mRecentsAnimationController != null) {
            Log.d("PhoneWindowManagerExt", "Can not sendBroadcast double click intent. RecentAnimation is running");
            return;
        }
        Log.d("PhoneWindowManagerExt", "broadcast double click intent keyCode=" + i);
        Intent intent = new Intent("com.samsung.android.action.DOUBLE_CLICK");
        intent.putExtra("KEYCODE", i);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permisson.DOUBLE_CLICK");
    }

    public final void sendFoldSaLoggingCanceledIfNeeded() {
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (phoneWindowManager.mSystemBooted && phoneWindowManager.mDefaultDisplayPolicy.mLidState == 0) {
            synchronized (this.mFoldSaLock) {
            }
        }
    }

    public final void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent, int i, boolean z) {
        StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal == null) {
            return;
        }
        Slog.d("PhoneWindowManagerExt", "sendThreeFingerGestureKeyEvent keyCode=" + keyEvent.getKeyCode());
        if (z) {
            ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal).sendKeyEventToDesktopTaskbarToType(keyEvent, StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, i));
            return;
        }
        StatusBarManagerService.AnonymousClass2 anonymousClass2 = (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
        Slog.d("StatusBarManagerService", "sendThreeFingerGestureKeyEvent");
        if (StatusBarManagerService.this.mBar == null) {
            return;
        }
        try {
            StatusBarManagerService.this.mBar.sendThreeFingerGestureKeyEvent(keyEvent);
        } catch (RemoteException unused) {
        }
    }

    public final boolean sensorToggleBehavior(int i, int i2) {
        boolean z;
        int i3;
        if (i == 2) {
            z = this.mIsCameraSensorToggleSupported;
            i3 = R.string.permdesc_accessImsCallService;
        } else if (i == 1) {
            z = this.mIsMicSensorToggleSupported;
            i3 = R.string.permdesc_accessLastKnownCellId;
        } else {
            z = false;
            i3 = 0;
        }
        if (externalKeyboardPolicy() || !z) {
            return false;
        }
        SensorPrivacyManager sensorPrivacyManager = SensorPrivacyManager.getInstance(this.mContext);
        boolean isSensorPrivacyEnabled = sensorPrivacyManager.isSensorPrivacyEnabled(i);
        sensorPrivacyManager.setSensorPrivacyForProfileGroupWithConfirmPopup(5, i, !isSensorPrivacyEnabled, i2);
        if (isSensorPrivacyEnabled) {
            Context context = this.mContext;
            showToast(context, context.getResources().getString(i3));
        }
        return true;
    }

    public final void setGoogleQuickSearchBoxAsDigitalAssistant() {
        if (isGoogleQuickSearchBoxAsDigitalAssistant()) {
            return;
        }
        new SemRoleManager(this.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", "com.google.android.googlequicksearchbox", 0, new UserHandle(this.mPolicy.mCurrentUserId), this.mContext.getMainExecutor(), new PhoneWindowManagerExt$$ExternalSyntheticLambda20());
    }

    public final void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
        KeyguardServiceWrapper keyguardServiceWrapper;
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate == null || (keyguardServiceWrapper = keyguardServiceDelegate.mKeyguardService) == null) {
            return;
        }
        keyguardServiceWrapper.setPendingIntentAfterUnlock(pendingIntent, intent);
    }

    public final void showToast(Context context, String str) {
        this.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda13(this, context, str));
    }

    public final void showingSearcleToastIfNeeded() {
        if (!isLongPressHomeSearcle() || longPressOnHomePolicy()) {
            return;
        }
        Resources resources = this.mContext.getResources();
        int i = this.mPolicy.mLongPressOnHomeBehavior;
        String string = i != 4 ? i != 101 ? null : resources.getString(17042512, resources.getString(17043250)) : resources.getString(R.string.config_mainBuiltInDisplayCutout);
        if (TextUtils.isEmpty(string)) {
            Log.d("PhoneWindowManagerExt", "The toast message is empty");
        } else {
            showToast(this.mContext, string);
        }
    }

    public final boolean startGameToolsService(int i, int i2, boolean z) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.game.gametools", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("PhoneWindowManagerExt", "GameBooster is not installed, " + e);
        }
        if (applicationInfo == null || !applicationInfo.enabled) {
            Log.d("PhoneWindowManagerExt", "GameBooster is disabled");
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

    public final void startLockscreenFingerprintAuth() {
        KeyguardServiceDelegate keyguardServiceDelegate = this.mPolicy.mKeyguardDelegate;
        if (keyguardServiceDelegate == null || keyguardServiceDelegate.mKeyguardService == null) {
            return;
        }
        Log.v("KeyguardServiceDelegate", "Start Fingerprint Authentication");
        keyguardServiceDelegate.mKeyguardService.startFingerprintAuthentication();
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
        StatusBarManagerService.AnonymousClass2 anonymousClass2 = (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
        Slog.d("StatusBarManagerService", "startSearcleByHomeKey down=" + z + " longPress=" + z2);
        if (StatusBarManagerService.this.mBar == null) {
            return;
        }
        try {
            StatusBarManagerService.this.mBar.startSearcleByHomeKey(z, z2);
        } catch (RemoteException unused) {
        }
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

    public final void updateDoublePressPowerBehavior() {
        KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
        int lastAction = keyCustomizationManager.getLastAction(8, 26);
        boolean z = InputRune.PWM_SIDE_KEY_DOUBLE_PRESS;
        PhoneWindowManager phoneWindowManager = this.mPolicy;
        if (z && keyCustomizationManager.getLastId(8, 26) == 1104) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 104;
        } else if (lastAction == 4) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 0;
        } else if (InputRune.PWM_QUICK_LAUNCH_CAMERA && keyCustomizationManager.getLastId(8, 26) == 2001) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 101;
        } else if (InputRune.PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE && keyCustomizationManager.getLastId(8, 26) == 2002) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 105;
        } else if (lastAction == 1 || lastAction == 3 || lastAction == 2) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 106;
        } else if (z) {
            phoneWindowManager.mDoublePressOnPowerBehavior = 0;
        }
        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("updateDoublePressPowerBehavior, behavior="), phoneWindowManager.mDoublePressOnPowerBehavior, "PhoneWindowManagerExt");
    }

    public final void updateKeyCustomizationInfoTvMode() {
        KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
        boolean z = keyCustomizationManager.mKeyCustomizationInfoManager.get(2002, 8, 26, null) != null;
        if (this.mTvModeEnabled && this.mTvModeDoublePressEnabled) {
            ComponentName componentName = this.mDoublePressLaunchComponentName;
            if (componentName != null ? "com.samsung.tvmode/com.samsung.tvmode.activity.MainActivity".equals(componentName.flattenToString()) : false) {
                if (!z) {
                    Intent m = PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m("android.intent.action.MAIN", "android.intent.category.LAUNCHER");
                    m.setComponent(this.mDoublePressLaunchComponentName);
                    keyCustomizationManager.putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(8, 2002, 26, 1, m));
                }
                updateDoublePressPowerBehavior();
            }
        }
        if (z) {
            keyCustomizationManager.removeKeyCustomizationInfo(2002, 8, 26, null);
        }
        updateDoublePressPowerBehavior();
    }

    public void updateSettings() {
        this.mSettingsObserver.updateSettings();
    }

    public final void updateSingleKeyGestureRule(int i) {
        int i2 = 0;
        for (int i3 : KeyCustomizationConstants.NEEDED_UPDATE_BEHAVIOR_MULTI_PRESS_TYPE) {
            KeyCustomizationManager keyCustomizationManager = this.mKeyCustomizationPolicy;
            if (keyCustomizationManager.hasLastInfo(i3, i) && keyCustomizationManager.getLastAction(i3, i) != 4) {
                if ((i3 & 4) != 0) {
                    SemWindowManager.KeyCustomizationInfo last = keyCustomizationManager.mKeyCustomizationInfoManager.getLast(i3, i);
                    if ((last == null ? 0L : last.longPressTimeout) == 0) {
                    }
                }
                i2++;
            }
        }
        if (i2 > 0) {
            addSingleKeyGestureRule(i);
        } else {
            if (i == 26) {
                return;
            }
            PhoneWindowManager phoneWindowManager = this.mPolicy;
            if (phoneWindowManager.mSingleKeyGestureDetector.hasRule(i)) {
                phoneWindowManager.mSingleKeyGestureDetector.mCustomRules.remove(i);
            }
        }
    }
}
