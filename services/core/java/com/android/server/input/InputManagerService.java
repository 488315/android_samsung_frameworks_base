package com.android.server.input;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.drawable.Icon;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorPrivacyManager;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.DisplayViewport;
import android.hardware.input.HostUsiVersion;
import android.hardware.input.IInputDeviceBatteryListener;
import android.hardware.input.IInputDeviceBatteryState;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.IInputManager;
import android.hardware.input.IInputSensorEventListener;
import android.hardware.input.IKeyboardBacklightListener;
import android.hardware.input.IMultiFingerGestureListener;
import android.hardware.input.IPointerIconChangedListener;
import android.hardware.input.ISemLidStateChangedListener;
import android.hardware.input.IStickyModifierStateListener;
import android.hardware.input.ISwitchEventChangedListener;
import android.hardware.input.ITabletModeChangedListener;
import android.hardware.input.IWirelessKeyboardShareChangedListener;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.InputSensorInfo;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.hardware.input.TouchCalibration;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CombinedVibration;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.IVibratorStateListener;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.FeatureFlagUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.IDisplayFoldListener;
import android.view.IInputFilter;
import android.view.IInputFilterHost;
import android.view.IInputMonitorHost;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.SurfaceControl;
import android.view.VerifiedInputEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.android.input.flags.Flags;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.WiredAccessoryManager;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.CoverStateManager;
import com.android.server.input.BatteryController.DeviceMonitor;
import com.android.server.input.BatteryController.ListenerRecord;
import com.android.server.input.StickyModifierStateController.StickyModifierStateListenerRecord;
import com.android.server.input.debug.FocusEventDebugView;
import com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda0;
import com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda1;
import com.android.server.inputmethod.SamsungIMMSHWKeyboard;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.wm.AnrController;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.DragDropController;
import com.android.server.wm.InputManagerCallback;
import com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda0;
import com.android.server.wm.InputTarget;
import com.android.server.wm.ViewServer;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowState;

import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.InputRune;
import com.samsung.android.sepunion.SemUnionManagerLocal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class InputManagerService extends IInputManager.Stub implements Watchdog.Monitor {
    public static final boolean DEBUG = Log.isLoggable("InputManager", 3);
    public static final Uri DEX_SETTINGS_URI;
    public static final boolean IS_TABLET_DEVICE;
    public static boolean SEP_FULL;
    public static final float[] mHighHysteresis;
    public static final float[] mLowHysteresis;
    public static final boolean mMultiFingerGestureEnable;
    public boolean mAddingPogoKeyboardIntentPending;
    public final SparseArray mAdditionalDisplayInputProperties;
    public final Object mAdditionalDisplayInputPropertiesLock;
    public final Object mAssociationsLock;
    public boolean mBackKeyDownAdjusted;
    public final BatteryController mBatteryController;
    public int mBlockDeviceMode;
    public final Vector mBlockTkeyCallerList;
    public final Vector mBlockTspCallerList;
    public boolean mBootCompleted;
    public final AnonymousClass3 mBroadcastReceiver;
    public final Context mContext;
    public final ControlWakeKey mControlWakeKey;
    public int mCurrentFourTapBehavior;
    public int mCurrentThreeTapBehavior;
    public final PersistentDataStore mDataStore;
    public PointerIcon mDefaultPointerIcon;
    public boolean mDefaultPointerIconChanged;
    public DesktopModeServiceCallbacks mDesktopModeServiceCallbacks;
    public final AnonymousClass3 mDeviceBlockReceiver;
    public final Map mDeviceTypeAssociations;
    public boolean mDexImeWindowVisibleInDefaultDisplay;
    public int mDisplayIdForPointerIcon;
    public DisplayManagerInternal mDisplayManagerInternal;
    public final File mDoubleTouchGestureEnableFile;
    public FocusEventDebugView mFocusEventDebugView;
    public final Object mFocusEventDebugViewLock;
    public final AnonymousClass12 mFoldingAngleListener;
    public int mFoldingState;
    public PointerIcon mForcedDefaultPointerIcon;
    public boolean mForcedDefaultPointerIconChanged;
    public final GamePadRemapper mGamePadRemapper;
    public final InputManagerHandler mHandler;
    public InputDevice[] mInputDevices;
    public final SparseArray mInputDevicesChangedListeners;
    public boolean mInputDevicesChangedPending;
    public final Object mInputDevicesLock;
    public IInputFilter mInputFilter;
    public InputFilterHost mInputFilterHost;
    public final Object mInputFilterLock;
    public final InputKeyCounter mInputKeyCounter;
    public InputMethodManagerCallbacks mInputMethodManagerCallbacks;
    public final Map mInputMonitors;
    public final Object mInputWirelessKeyboardMouseShareLock;
    public boolean mIsKidsMode;
    public final SparseBooleanArray mIsVibrating;
    public final KeyRemapper mKeyRemapper;
    public final KeyboardBacklightControllerInterface mKeyboardBacklightController;
    public final Map mKeyboardLayoutAssociations;
    public final KeyboardLayoutManager mKeyboardLayoutManager;
    public final KeyboardLedController mKeyboardLedController;
    public final AnonymousClass3 mKidsModeReceiver;
    public long mLastLidEventTime;
    public PointerIcon mLastPointerIcon;
    public int mLastPointerIconType;
    public int mLastWacomMode;
    public final SparseArray mLidStateChangedListeners;
    public final Object mLidStateLock;
    public final List mLidSwitchCallbacks;
    public final Object mLidSwitchLock;
    public final Object mLightLock;
    public final ArrayMap mLightSessions;
    public final SparseArray mMultiFingerGestureListeners;
    public final Object mMultiFingerGestureLock;
    public final NativeInputManagerService.NativeImpl mNative;
    public int mNextVibratorTokenValue;
    public NotificationManager mNotificationManager;
    public boolean mNotifyPogoKeyboardNotMatchPending;
    public boolean mPaperCoverNotificationPending;
    public boolean mPogoKeyboardConnected;
    public final PointerIconCache mPointerIconCache;
    public final SparseArray mPointerIconChangedListeners;
    public final Object mPointerIconLock;
    public final Map mRuntimeAssociations;
    public SecAccessoryManagerCallbacks mSecAccessoryManagerCallbacks;
    public final List mSensorAccuracyListenersToNotify;
    public final SparseArray mSensorEventListeners;
    public final List mSensorEventListenersToNotify;
    public final Object mSensorEventLock;
    public final InputSettingsObserver mSettingsObserver;
    public int mSharedKeyReferenceCount;
    public final PowerManager.WakeLock mSharedKeyWakeLock;
    public boolean mShowKeyPresses;
    public boolean mShowRotaryInput;
    public final AnonymousClass3 mShowingTouchSensitivityNotiActionReceiver;
    public int mShowingTouchSensitivityNotiCount;
    public int mShowingTouchSensitivityNotiCountOld;
    public IBinder mSpenControlToken;
    public boolean mSpenCoverAttached;
    public int mSpenDeviceId;
    public final Map mStaticAssociations;
    public final StickyModifierStateController mStickyModifierStateController;
    public final SparseArray mSwitchEventChangedListeners;
    public final Object mSwitchEventChangedLock;
    public boolean mSystemReady;
    public final SparseArray mTabletModeChangedListeners;
    public final Object mTabletModeLock;
    public final ArrayList mTempGamePads;
    public final ArrayList mTempInputDevicesChangedListenersToNotify;
    public final List mTempLidStateChangedListenersToNotify;
    public final List mTempMultiFingerGestureListenersToNotify;
    public final List mTempPointerIconChangedListenersToNotify;
    public final List mTempSwitchEventChangedListenersToNotify;
    public final List mTempTabletModeChangedListenersToNotify;
    public final List mTempWirelessKeyboardShareChangedListenersToNotify;
    public final ToastDialog mToastDialog;
    public int mToolTypeForDefaultPointerIcon;
    public int mToolTypeForForcedDefaultPointerIcon;
    public SemUnionManagerLocal mUnionManagerLocal;
    public final Map mUniqueIdAssociationsByDescriptor;
    public final Map mUniqueIdAssociationsByPort;
    public final boolean mUseDevInputEventForAudioJack;
    public final String mVelocityTrackerStrategy;
    public final Object mVibratorLock;
    public final SparseArray mVibratorStateListeners;
    public final Map mVibratorTokens;
    public WindowManagerCallbacks mWindowManagerCallbacks;
    public WiredAccessoryCallbacks mWiredAccessoryCallbacks;
    public final WirelessKeyboardMouseShare mWirelessKeyboardMouseShare;
    public final SparseArray mWirelessKeyboardShareChangedListeners;
    public final Object mWirelessKeyboardShareLock;

    /* renamed from: com.android.server.input.InputManagerService$1, reason: invalid class name */
    public final class AnonymousClass1
            implements UEventManager, KeyboardBacklightControllerInterface {}

    /* renamed from: com.android.server.input.InputManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ InputManagerService this$0;

        public /* synthetic */ AnonymousClass3(InputManagerService inputManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = inputManagerService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            final int i = 0;
            final int i2 = 1;
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.updateFlowPointerSettings();
                    this.this$0.updateFlowPointerDirectionSettings();
                    this.this$0.updateMultiFingerTapBehavior(4);
                    this.this$0.updateMultiFingerTapBehavior(1);
                    this.this$0.updateSetPenModeOnDex();
                    break;
                case 1:
                    String action = intent.getAction();
                    if ("com.samsung.android.intent.action.SET_INWATER_TOUCH".equals(action)) {
                        try {
                            boolean booleanExtra = intent.getBooleanExtra("set", false);
                            boolean booleanExtra2 = intent.getBooleanExtra("force", false);
                            String stringExtra = intent.getStringExtra("package");
                            int intExtra = intent.getIntExtra("type", 3);
                            StringBuilder sb = new StringBuilder("received:");
                            sb.append(action);
                            sb.append(" packageName:");
                            sb.append(stringExtra != null ? stringExtra : "null");
                            Log.d("InputManager", sb.toString());
                            if (!TextUtils.isEmpty(stringExtra)) {
                                this.this$0.setBlockDeviceMode(
                                        booleanExtra, intExtra, booleanExtra2, stringExtra);
                                break;
                            }
                        } catch (Exception e) {
                            Slog.w("InputManager", "Could not set device block", e);
                            return;
                        }
                    }
                    break;
                case 2:
                    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        InputManagerService inputManagerService = this.this$0;
                        inputManagerService.mBootCompleted = true;
                        if (InputRune.IFW_KEY_COUNTER) {
                            InputKeyCounter inputKeyCounter = inputManagerService.mInputKeyCounter;
                            Context context2 = inputManagerService.mContext;
                            inputKeyCounter.getClass();
                            boolean z = InputKeyCounter.DEBUG;
                            if (z) {
                                Log.i("InputKeyCounter", "read old data!");
                            }
                            String[] split =
                                    SystemProperties.get("persist.service.bgkeycount", "null")
                                            .split("/");
                            InputKeyCounter.HwKeyCount hwKeyCount =
                                    new InputKeyCounter.HwKeyCount();
                            try {
                                for (String str : split) {
                                    if (z) {
                                        Log.d(
                                                "InputKeyCounter",
                                                "read old saved keycount strings = " + str);
                                    }
                                    String[] split2 = str.split(",");
                                    if (split2.length != 2) {
                                        Log.w("InputKeyCounter", "throw up the data!");
                                    } else {
                                        hwKeyCount.add(
                                                Integer.parseInt(split2[0]),
                                                Integer.parseInt(split2[1]));
                                    }
                                }
                            } catch (NumberFormatException unused) {
                                Log.e(
                                        "InputKeyCounter",
                                        "NumberFormatException, throw up the data!");
                                SystemProperties.set("persist.service.bgkeycount", "");
                            }
                            InputKeyCounter.sendBroadcastKeyCountInternal(
                                    context2, hwKeyCount.getKeyCountMap());
                            SystemProperties.set("persist.service.bgkeycount", "");
                        }
                        this.this$0.mHandler.post(
                                new Runnable(
                                        this) { // from class:
                                                // com.android.server.input.InputManagerService$16$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputManagerService.AnonymousClass3
                                            f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i3 = i;
                                        InputManagerService.AnonymousClass3 anonymousClass3 =
                                                this.f$0;
                                        switch (i3) {
                                            case 0:
                                                anonymousClass3.this$0.updateMultiFingerTapBehavior(
                                                        4);
                                                anonymousClass3.this$0.updateMultiFingerTapBehavior(
                                                        1);
                                                anonymousClass3.this$0.updateFlowPointerSettings();
                                                anonymousClass3.this$0
                                                        .updateFlowPointerDirectionSettings();
                                                break;
                                            default:
                                                anonymousClass3.this$0
                                                        .showingTouchSensitivityNotificationIfNeeded();
                                                break;
                                        }
                                    }
                                });
                        InputManagerService inputManagerService2 = this.this$0;
                        if (inputManagerService2.mNotifyPogoKeyboardNotMatchPending) {
                            inputManagerService2.notifyPogoKeyboardNotMatch();
                            this.this$0.mNotifyPogoKeyboardNotMatchPending = false;
                        }
                        InputManagerService inputManagerService3 = this.this$0;
                        if (inputManagerService3.mPaperCoverNotificationPending) {
                            inputManagerService3.mPaperCoverNotificationPending = false;
                            inputManagerService3.mHandler.post(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.android.server.input.InputManagerService$16$$ExternalSyntheticLambda0
                                        public final /* synthetic */ InputManagerService
                                                        .AnonymousClass3
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i3 = i2;
                                            InputManagerService.AnonymousClass3 anonymousClass3 =
                                                    this.f$0;
                                            switch (i3) {
                                                case 0:
                                                    anonymousClass3.this$0
                                                            .updateMultiFingerTapBehavior(4);
                                                    anonymousClass3.this$0
                                                            .updateMultiFingerTapBehavior(1);
                                                    anonymousClass3.this$0
                                                            .updateFlowPointerSettings();
                                                    anonymousClass3.this$0
                                                            .updateFlowPointerDirectionSettings();
                                                    break;
                                                default:
                                                    anonymousClass3.this$0
                                                            .showingTouchSensitivityNotificationIfNeeded();
                                                    break;
                                            }
                                        }
                                    });
                            break;
                        }
                    }
                    break;
                case 3:
                    if ("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE"
                            .equals(intent.getAction())) {
                        boolean booleanExtra3 = intent.getBooleanExtra("kids_home_mode", false);
                        Log.d(
                                "InputManager",
                                "KidsMode : " + this.this$0.mIsKidsMode + " -> " + booleanExtra3);
                        InputManagerService inputManagerService4 = this.this$0;
                        if (inputManagerService4.mIsKidsMode != booleanExtra3) {
                            inputManagerService4.mNative.updateInputMetaState(16, booleanExtra3);
                            this.this$0.mIsKidsMode = booleanExtra3;
                            break;
                        }
                    }
                    break;
                case 4:
                    InputManagerService inputManagerService5 = this.this$0;
                    boolean z2 = InputManagerService.DEBUG;
                    inputManagerService5.reloadDeviceAliases();
                    break;
                default:
                    if ("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI"
                            .equals(intent.getAction())) {
                        Settings.System.putIntForUser(
                                this.this$0.mContext.getContentResolver(),
                                "auto_adjust_touch",
                                1,
                                -2);
                        int intExtra2 = intent.getIntExtra("channelId", 0);
                        if (intExtra2 == 0) {
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                    intExtra2, "channel id was wrong. id=", "InputManager");
                            break;
                        } else {
                            this.this$0.mNotificationManager.cancel(intExtra2);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* renamed from: com.android.server.input.InputManagerService$8, reason: invalid class name */
    public final class AnonymousClass8 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ InputManagerService this$0;

        public /* synthetic */ AnonymousClass8(
                InputManagerService inputManagerService, Handler handler, int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = inputManagerService;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    super.onChange(z);
                    this.this$0.updateFlowPointerSettings();
                    break;
                case 1:
                    super.onChange(z);
                    this.this$0.updateMultiFingerTapBehavior(4);
                    break;
                case 2:
                    super.onChange(z);
                    this.this$0.updateMultiFingerTapBehavior(1);
                    break;
                case 3:
                    super.onChange(z);
                    this.this$0.updateSetPenModeOnDex();
                    break;
                default:
                    super.onChange(z);
                    this.this$0.updateFlowPointerDirectionSettings();
                    break;
            }
        }
    }

    public final class AdditionalDisplayInputProperties {
        public boolean mousePointerAccelerationEnabled = true;
        public boolean pointerIconVisible = true;
    }

    public interface DesktopModeServiceCallbacks {}

    class Injector {
        public final Context mContext;
        public final Looper mLooper;
        public final UEventManager mUEventManager;

        public Injector(Context context, Looper looper, AnonymousClass1 anonymousClass1) {
            this.mContext = context;
            this.mLooper = looper;
            this.mUEventManager = anonymousClass1;
        }
    }

    public final class InputDevicesChangedListenerRecord implements IBinder.DeathRecipient {
        public final IInputDevicesChangedListener mListener;
        public final int mPid;

        public InputDevicesChangedListenerRecord(
                int i, IInputDevicesChangedListener iInputDevicesChangedListener) {
            this.mPid = i;
            this.mListener = iInputDevicesChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Input devices changed listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onInputDevicesChangedListenerDied(this.mPid);
        }
    }

    public final class InputFilterHost extends IInputFilterHost.Stub {
        public boolean mDisconnected;

        public InputFilterHost() {}

        public final void sendInputEvent(InputEvent inputEvent, int i) {
            InputManagerService inputManagerService = InputManagerService.this;
            boolean z = InputManagerService.DEBUG;
            if (!inputManagerService.checkCallingPermission(
                    "android.permission.INJECT_EVENTS", "sendInputEvent()", false)) {
                throw new SecurityException(
                        "The INJECT_EVENTS permission is required for injecting input events.");
            }
            Objects.requireNonNull(inputEvent, "event must not be null");
            synchronized (InputManagerService.this.mInputFilterLock) {
                try {
                    if (!this.mDisconnected) {
                        InputManagerService.this.mNative.injectInputEvent(
                                inputEvent, false, -1, 0, 0, 0, 0, i | 67108864);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class InputManagerHandler extends Handler {
        public InputManagerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            InputManagerService inputManagerService = InputManagerService.this;
            if (i == 1) {
                InputDevice[] inputDeviceArr = (InputDevice[]) message.obj;
                boolean z = InputManagerService.DEBUG;
                inputManagerService.deliverInputDevicesChanged(inputDeviceArr);
            }
            if (i == 2) {
                boolean z2 = InputManagerService.DEBUG;
                inputManagerService.reloadDeviceAliases();
                return;
            }
            if (i == 3) {
                long j = (r6.argi1 & 4294967295L) | (r6.argi2 << 32);
                boolean booleanValue = ((Boolean) ((SomeArgs) message.obj).arg1).booleanValue();
                boolean z3 = InputManagerService.DEBUG;
                inputManagerService.deliverTabletModeChanged(j, booleanValue);
                return;
            }
            switch (i) {
                case 103:
                    long j2 = (r6.argi1 & 4294967295L) | (r6.argi2 << 32);
                    boolean booleanValue2 =
                            ((Boolean) ((SomeArgs) message.obj).arg1).booleanValue();
                    boolean z4 = InputManagerService.DEBUG;
                    inputManagerService.deliverLidStateChanged(j2, booleanValue2);
                    break;
                case 104:
                    int i2 = message.arg1;
                    Object obj = message.obj;
                    PointerIcon pointerIcon = obj instanceof PointerIcon ? (PointerIcon) obj : null;
                    boolean z5 = InputManagerService.DEBUG;
                    inputManagerService.deliverPointerIconChanged(i2, pointerIcon);
                    break;
                case 105:
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    boolean z6 = InputManagerService.DEBUG;
                    inputManagerService.deliverMultiFingerGesture(i3, i4);
                    break;
                case 106:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    long j3 = (someArgs.argi1 & 4294967295L) | (someArgs.argi2 << 32);
                    int i5 = someArgs.argi3;
                    String str = (String) someArgs.arg1;
                    boolean z7 = InputManagerService.DEBUG;
                    inputManagerService.wakeUp(j3, i5, str);
                    break;
                case 107:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    int i6 = someArgs2.argi1;
                    int i7 = someArgs2.argi2;
                    int i8 = someArgs2.argi3;
                    int i9 = someArgs2.argi4;
                    boolean z8 = InputManagerService.DEBUG;
                    inputManagerService.deliverSwitchEventChanged(i6, i7, i8, i9);
                    break;
                case 108:
                    boolean z9 = InputManagerService.DEBUG;
                    inputManagerService.getClass();
                    Log.d("InputManager", "mSensorManager is null.");
                    break;
            }
        }
    }

    public interface InputMethodManagerCallbacks {}

    public final class InputMonitorHost extends IInputMonitorHost.Stub {
        public final IBinder mInputChannelToken;

        public InputMonitorHost(IBinder iBinder) {
            this.mInputChannelToken = iBinder;
        }

        public final void dispose() {
            InputManagerService inputManagerService = InputManagerService.this;
            IBinder iBinder = this.mInputChannelToken;
            boolean z = InputManagerService.DEBUG;
            inputManagerService.removeSpyWindowGestureMonitor(iBinder);
        }

        public final void pilferPointers() {
            InputManagerService.this.mNative.pilferPointers(this.mInputChannelToken);
        }
    }

    public final class KeyCountRunnable implements Runnable {
        public KeyCountRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            InputManagerService inputManagerService = InputManagerService.this;
            InputKeyCounter inputKeyCounter = inputManagerService.mInputKeyCounter;
            Context context = inputManagerService.mContext;
            ArrayMap keyCountMap = inputKeyCounter.mCurrentKeyCount.getKeyCountMap();
            InputKeyCounter.HwKeyCount hwKeyCount = inputKeyCounter.mCurrentKeyCount;
            synchronized (hwKeyCount.mKeyCountMap) {
                hwKeyCount.mKeyCountMap.clear();
                hwKeyCount.mAllKeyCount = 0L;
            }
            InputKeyCounter.sendBroadcastKeyCountInternal(context, keyCountMap);
        }
    }

    public interface KeyboardBacklightControllerInterface {
        default void decrementKeyboardBacklight(int i) {}

        default void dump(PrintWriter printWriter) {}

        default void incrementKeyboardBacklight(int i) {}

        default void notifyUserActivity() {}

        default void onInteractiveChanged(boolean z) {}

        default void registerKeyboardBacklightListener(
                IKeyboardBacklightListener iKeyboardBacklightListener, int i) {}

        default void systemRunning() {}

        default void unregisterKeyboardBacklightListener(
                IKeyboardBacklightListener iKeyboardBacklightListener, int i) {}
    }

    public final class LidStateChangedListenerRecord implements IBinder.DeathRecipient {
        public final ISemLidStateChangedListener mListener;
        public final int mPid;

        public LidStateChangedListenerRecord(
                int i, ISemLidStateChangedListener iSemLidStateChangedListener) {
            this.mPid = i;
            this.mListener = iSemLidStateChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Lid state changed listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onLidStateChangedListenerDied(this.mPid);
        }
    }

    public final class LightSession implements IBinder.DeathRecipient {
        public final int mDeviceId;
        public int[] mLightIds;
        public LightState[] mLightStates;
        public final String mOpPkg;
        public final IBinder mToken;

        public LightSession(int i, String str, IBinder iBinder) {
            this.mDeviceId = i;
            this.mOpPkg = str;
            this.mToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                Slog.d("InputManager", "Light token died.");
            }
            synchronized (InputManagerService.this.mLightLock) {
                InputManagerService.this.closeLightSession(this.mDeviceId, this.mToken);
                InputManagerService.this.mLightSessions.remove(this.mToken);
            }
        }
    }

    public final class LocalService {
        public LocalService() {}

        public final void setInteractive(boolean z) {
            InputManagerService.this.mNative.setInteractive(z);
            BatteryController batteryController = InputManagerService.this.mBatteryController;
            synchronized (batteryController.mLock) {
                batteryController.mIsInteractive = z;
                batteryController.updatePollingLocked(false);
            }
            InputManagerService.this.mKeyboardBacklightController.onInteractiveChanged(z);
            if (!InputRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING || z) {
                return;
            }
            synchronized (InputManagerService.this.mInputWirelessKeyboardMouseShareLock) {
                final WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                        InputManagerService.this.mWirelessKeyboardMouseShare;
                if (wirelessKeyboardMouseShare != null
                        && !((HashMap) wirelessKeyboardMouseShare.mLogInfos).isEmpty()) {
                    ((HashMap) wirelessKeyboardMouseShare.mLogInfos)
                            .entrySet()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // com.android.server.input.WirelessKeyboardMouseShare$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            WirelessKeyboardMouseShare wirelessKeyboardMouseShare2 =
                                                    WirelessKeyboardMouseShare.this;
                                            Map.Entry entry = (Map.Entry) obj;
                                            wirelessKeyboardMouseShare2.getClass();
                                            try {
                                                int intValue =
                                                        ((Integer) entry.getKey()).intValue();
                                                int intValue2 =
                                                        ((Integer) entry.getValue()).intValue();
                                                CoreSaLogger.logForBasic(
                                                        wirelessKeyboardMouseShare2
                                                                .CONN_ID[intValue - 1],
                                                        intValue2
                                                                + " "
                                                                + wirelessKeyboardMouseShare2
                                                                        .mPairedDevices[intValue]
                                                                        .getName());
                                            } catch (NullPointerException unused) {
                                                Slog.d(
                                                        "WirelessKeyboardMouseShare",
                                                        "notifySALogging nullpointer exception");
                                            }
                                        }
                                    });
                    ((HashMap) wirelessKeyboardMouseShare.mLogInfos).clear();
                }
            }
        }
    }

    public final class MultiFingerGestureListenerRecord implements IBinder.DeathRecipient {
        public final IMultiFingerGestureListener mListener;
        public final int mPid;

        public MultiFingerGestureListenerRecord(
                int i, IMultiFingerGestureListener iMultiFingerGestureListener) {
            this.mPid = i;
            this.mListener = iMultiFingerGestureListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("MultiFingerGesture listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onMultiFingerGestureListenerDied(this.mPid);
        }
    }

    public final class PointerIconChangedListenerRecord implements IBinder.DeathRecipient {
        public final IPointerIconChangedListener mListener;
        public final int mPid;

        public PointerIconChangedListenerRecord(
                int i, IPointerIconChangedListener iPointerIconChangedListener) {
            this.mPid = i;
            this.mListener = iPointerIconChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("PointerIcon changed listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onPointerIconChangedListenerDied(this.mPid);
        }
    }

    public interface SecAccessoryManagerCallbacks {}

    public final class SensorEventListenerRecord implements IBinder.DeathRecipient {
        public final IInputSensorEventListener mListener;
        public final int mPid;

        public SensorEventListenerRecord(
                int i, IInputSensorEventListener iInputSensorEventListener) {
            this.mPid = i;
            this.mListener = iInputSensorEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Sensor event listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onSensorEventListenerDied(this.mPid);
        }
    }

    public final class SwitchEventChangedListenerRecord implements IBinder.DeathRecipient {
        public final ISwitchEventChangedListener mListener;
        public final int mPid;

        public SwitchEventChangedListenerRecord(
                int i, ISwitchEventChangedListener iSwitchEventChangedListener) {
            this.mPid = i;
            this.mListener = iSwitchEventChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("SwitchEventChanged listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onSwitchEventChangedListenerDied(this.mPid);
        }
    }

    public final class TabletModeChangedListenerRecord implements IBinder.DeathRecipient {
        public final ITabletModeChangedListener mListener;
        public final int mPid;

        public TabletModeChangedListenerRecord(
                int i, ITabletModeChangedListener iTabletModeChangedListener) {
            this.mPid = i;
            this.mListener = iTabletModeChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Tablet mode changed listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onTabletModeChangedListenerDied(this.mPid);
        }
    }

    public final class VibrationInfo {
        public final int[] mAmplitudes;
        public final long[] mPattern;
        public final int mRepeat;

        public VibrationInfo(VibrationEffect vibrationEffect) {
            long[] jArr;
            int i;
            int i2;
            int[] iArr;
            if (vibrationEffect instanceof VibrationEffect.Composed) {
                VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
                int size = composed.getSegments().size();
                jArr = new long[size];
                iArr = new int[size];
                i = composed.getRepeatIndex();
                int i3 = 0;
                i2 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    StepSegment stepSegment =
                            (VibrationEffectSegment) composed.getSegments().get(i3);
                    i = composed.getRepeatIndex() == i3 ? i2 : i;
                    if (!(stepSegment instanceof StepSegment)) {
                        Slog.w(
                                "InputManager",
                                "Input devices don't support segment " + stepSegment);
                        i2 = -1;
                        break;
                    }
                    float amplitude = stepSegment.getAmplitude();
                    if (Float.compare(amplitude, -1.0f) == 0) {
                        iArr[i2] = 192;
                    } else {
                        iArr[i2] = (int) (amplitude * 255.0f);
                    }
                    jArr[i2] = stepSegment.getDuration();
                    i3++;
                    i2++;
                }
            } else {
                jArr = null;
                i = -1;
                i2 = -1;
                iArr = null;
            }
            if (i2 < 0) {
                Slog.w(
                        "InputManager",
                        "Only oneshot and step waveforms are supported on input devices");
                this.mPattern = new long[0];
                this.mAmplitudes = new int[0];
                this.mRepeat = -1;
                return;
            }
            this.mRepeat = i;
            long[] jArr2 = new long[i2];
            this.mPattern = jArr2;
            int[] iArr2 = new int[i2];
            this.mAmplitudes = iArr2;
            System.arraycopy(jArr, 0, jArr2, 0, i2);
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            if (i < jArr2.length) {
                return;
            }
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i,
                            "Repeat index ",
                            " must be within the bounds of the pattern.length ");
            m.append(jArr2.length);
            throw new ArrayIndexOutOfBoundsException(m.toString());
        }
    }

    public final class VibratorToken implements IBinder.DeathRecipient {
        public final int mDeviceId;
        public final IBinder mToken;
        public final int mTokenValue;
        public boolean mVibrating;

        public VibratorToken(int i, IBinder iBinder, int i2) {
            this.mDeviceId = i;
            this.mToken = iBinder;
            this.mTokenValue = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                Slog.d("InputManager", "Vibrator token died.");
            }
            InputManagerService.this.onVibratorTokenDied(this);
        }
    }

    public interface WindowManagerCallbacks extends InputManagerInternal$LidSwitchCallback {}

    public interface WiredAccessoryCallbacks {}

    public final class WirelessKeyboardShareChangedListenerRecord
            implements IBinder.DeathRecipient {
        public final IWirelessKeyboardShareChangedListener mListener;
        public final int mPid;

        public WirelessKeyboardShareChangedListenerRecord(
                int i,
                IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) {
            this.mPid = i;
            this.mListener = iWirelessKeyboardShareChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (InputManagerService.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Wireless Keyboard Share changed listener for pid "),
                        this.mPid,
                        " died.",
                        "InputManager");
            }
            InputManagerService.this.onWirelessKeyboardShareChangedListenerDied(this.mPid);
        }
    }

    static {
        new AdditionalDisplayInputProperties();
        IS_TABLET_DEVICE =
                SystemProperties.get("ro.build.characteristics", "phone").contains("tablet");
        mHighHysteresis = new float[] {30.0f, 160.0f, 360.0f};
        mLowHysteresis =
                new float[] {FullScreenMagnificationGestureHandler.MAX_SCALE, 20.0f, 150.0f};
        mMultiFingerGestureEnable = Build.VERSION.SEM_PLATFORM_INT >= 110100;
        DEX_SETTINGS_URI =
                Uri.parse(
                        "content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public InputManagerService(Context context) {
        this(new Injector(context, DisplayThread.get().getLooper(), new AnonymousClass1()));
    }

    public InputManagerService(Injector injector) {
        NativeInputManagerService.NativeImpl nativeImpl;
        InputManagerHandler inputManagerHandler;
        Context context;
        AnonymousClass3 anonymousClass3;
        AnonymousClass3 anonymousClass32;
        AnonymousClass3 anonymousClass33;
        KeyboardBacklightControllerInterface anonymousClass1;
        this.mLidStateLock = new Object();
        this.mLidStateChangedListeners = new SparseArray();
        this.mTempLidStateChangedListenersToNotify = new ArrayList();
        this.mLastLidEventTime = -1L;
        this.mMultiFingerGestureLock = new Object();
        this.mMultiFingerGestureListeners = new SparseArray();
        this.mTempMultiFingerGestureListenersToNotify = new ArrayList();
        this.mSwitchEventChangedLock = new Object();
        this.mSwitchEventChangedListeners = new SparseArray();
        this.mTempSwitchEventChangedListenersToNotify = new ArrayList();
        this.mTabletModeLock = new Object();
        this.mTabletModeChangedListeners = new SparseArray();
        this.mTempTabletModeChangedListenersToNotify = new ArrayList();
        this.mSensorEventLock = new Object();
        this.mSensorEventListeners = new SparseArray();
        this.mSensorEventListenersToNotify = new ArrayList();
        this.mSensorAccuracyListenersToNotify = new ArrayList();
        this.mPointerIconLock = new Object();
        this.mPointerIconChangedListeners = new SparseArray();
        this.mTempPointerIconChangedListenersToNotify = new ArrayList();
        PersistentDataStore persistentDataStore =
                new PersistentDataStore(new PersistentDataStore.Injector());
        this.mDataStore = persistentDataStore;
        this.mInputDevicesLock = new Object();
        this.mInputDevices = new InputDevice[0];
        this.mInputDevicesChangedListeners = new SparseArray();
        this.mTempInputDevicesChangedListenersToNotify = new ArrayList();
        this.mVibratorLock = new Object();
        this.mVibratorTokens = new ArrayMap();
        this.mVibratorStateListeners = new SparseArray();
        this.mIsVibrating = new SparseBooleanArray();
        this.mLightLock = new Object();
        this.mLightSessions = new ArrayMap();
        this.mLidSwitchLock = new Object();
        this.mLidSwitchCallbacks = new ArrayList();
        this.mInputFilterLock = new Object();
        this.mAssociationsLock = new Object();
        this.mRuntimeAssociations = new ArrayMap();
        this.mUniqueIdAssociationsByPort = new ArrayMap();
        this.mUniqueIdAssociationsByDescriptor = new ArrayMap();
        this.mKeyboardLayoutAssociations = new ArrayMap();
        this.mDeviceTypeAssociations = new ArrayMap();
        this.mAdditionalDisplayInputPropertiesLock = new Object();
        this.mAdditionalDisplayInputProperties = new SparseArray();
        this.mInputMonitors = new HashMap();
        this.mInputKeyCounter = new InputKeyCounter();
        this.mWirelessKeyboardShareLock = new Object();
        this.mWirelessKeyboardShareChangedListeners = new SparseArray();
        this.mTempWirelessKeyboardShareChangedListenersToNotify = new ArrayList();
        Object obj = new Object();
        this.mInputWirelessKeyboardMouseShareLock = obj;
        this.mSharedKeyReferenceCount = 0;
        this.mFoldingState = 2;
        this.mBlockDeviceMode = 0;
        this.mBlockTspCallerList = new Vector();
        this.mBlockTkeyCallerList = new Vector();
        this.mPogoKeyboardConnected = false;
        this.mAddingPogoKeyboardIntentPending = false;
        this.mNotifyPogoKeyboardNotMatchPending = false;
        this.mCurrentThreeTapBehavior = 4;
        this.mCurrentFourTapBehavior = 1;
        this.mDefaultPointerIconChanged = false;
        this.mDefaultPointerIcon = null;
        this.mToolTypeForDefaultPointerIcon = 0;
        this.mForcedDefaultPointerIconChanged = false;
        this.mForcedDefaultPointerIcon = null;
        this.mToolTypeForForcedDefaultPointerIcon = 0;
        this.mFocusEventDebugViewLock = new Object();
        this.mShowKeyPresses = false;
        this.mShowRotaryInput = false;
        this.mBackKeyDownAdjusted = false;
        this.mDexImeWindowVisibleInDefaultDisplay = false;
        this.mPaperCoverNotificationPending = false;
        this.mShowingTouchSensitivityNotiCount = 0;
        this.mShowingTouchSensitivityNotiCountOld = 0;
        new IDisplayFoldListener
                .Stub() { // from class: com.android.server.input.InputManagerService.6
            public final void onDisplayFoldChanged(int i, boolean z) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        "onDisplayFoldChanged: folded = ", "InputManager", z);
                InputManagerService.this.mNative.setDisplayFolded(z);
            }
        };
        this.mSpenControlToken = null;
        this.mSpenDeviceId = -1;
        this.mTempGamePads = new ArrayList();
        this.mSpenCoverAttached = false;
        this.mLastWacomMode = -1;
        AnonymousClass3 anonymousClass34 = new AnonymousClass3(this, 5);
        this.mDisplayIdForPointerIcon = 0;
        new SensorEventListener() { // from class: com.android.server.input.InputManagerService.12
            @Override // android.hardware.SensorEventListener
            public final void onAccuracyChanged(Sensor sensor, int i) {}

            @Override // android.hardware.SensorEventListener
            public final void onSensorChanged(SensorEvent sensorEvent) {
                boolean z = false;
                float f = sensorEvent.values[0];
                while (true) {
                    InputManagerService inputManagerService = InputManagerService.this;
                    int i = inputManagerService.mFoldingState;
                    if (i <= 0 || f >= InputManagerService.mLowHysteresis[i]) {
                        break;
                    }
                    inputManagerService.mFoldingState = i - 1;
                    z = true;
                }
                while (true) {
                    InputManagerService inputManagerService2 = InputManagerService.this;
                    int i2 = inputManagerService2.mFoldingState;
                    if (i2 >= 2 || f <= InputManagerService.mHighHysteresis[i2]) {
                        break;
                    }
                    inputManagerService2.mFoldingState = i2 + 1;
                    z = true;
                }
                if (z) {
                    StringBuilder sb =
                            new StringBuilder("mFoldingAngleListener: state changed, angle = ");
                    sb.append(f);
                    sb.append(", state = ");
                    GestureWakeup$$ExternalSyntheticOutline0.m(
                            sb, InputManagerService.this.mFoldingState, "InputManager");
                    InputManagerService inputManagerService3 = InputManagerService.this;
                    inputManagerService3.mNative.setFoldingState(
                            inputManagerService3.mFoldingState);
                }
            }
        };
        AnonymousClass3 anonymousClass35 = new AnonymousClass3(this, 1);
        AnonymousClass3 anonymousClass36 = new AnonymousClass3(this, 2);
        this.mIsKidsMode = false;
        AnonymousClass3 anonymousClass37 = new AnonymousClass3(this, 3);
        this.mStaticAssociations = loadStaticInputPortAssociations();
        Context context2 = injector.mContext;
        this.mContext = context2;
        InputManagerHandler inputManagerHandler2 = new InputManagerHandler(injector.mLooper);
        this.mHandler = inputManagerHandler2;
        NativeInputManagerService.NativeImpl nativeImpl2 =
                new NativeInputManagerService.NativeImpl(this, injector.mLooper.getQueue());
        this.mNative = nativeImpl2;
        this.mSettingsObserver =
                new InputSettingsObserver(context2, inputManagerHandler2, this, nativeImpl2);
        this.mKeyboardLayoutManager =
                new KeyboardLayoutManager(
                        context2, nativeImpl2, persistentDataStore, injector.mLooper);
        Looper looper = injector.mLooper;
        this.mBatteryController =
                new BatteryController(
                        context2,
                        nativeImpl2,
                        looper,
                        injector.mUEventManager,
                        new BatteryController.LocalBluetoothBatteryManager(context2, looper));
        if (((Boolean)
                        InputFeatureFlagProvider.sKeyboardBacklightControlOverride.orElse(
                                Boolean.valueOf(
                                        InputFeatureFlagProvider
                                                .KEYBOARD_BACKLIGHT_CONTROL_ENABLED)))
                .booleanValue()) {
            nativeImpl = nativeImpl2;
            inputManagerHandler = inputManagerHandler2;
            context = context2;
            anonymousClass3 = anonymousClass37;
            anonymousClass32 = anonymousClass36;
            anonymousClass33 = anonymousClass35;
            anonymousClass1 =
                    new KeyboardBacklightController(
                            context2,
                            nativeImpl,
                            persistentDataStore,
                            injector.mLooper,
                            new KeyboardBacklightController$$ExternalSyntheticLambda0(),
                            injector.mUEventManager);
        } else {
            nativeImpl = nativeImpl2;
            inputManagerHandler = inputManagerHandler2;
            context = context2;
            anonymousClass3 = anonymousClass37;
            anonymousClass32 = anonymousClass36;
            anonymousClass33 = anonymousClass35;
            anonymousClass1 = new AnonymousClass1();
        }
        this.mKeyboardBacklightController = anonymousClass1;
        this.mStickyModifierStateController = new StickyModifierStateController();
        NativeInputManagerService.NativeImpl nativeImpl3 = nativeImpl;
        this.mKeyboardLedController =
                new KeyboardLedController(context, injector.mLooper, nativeImpl3);
        this.mKeyRemapper =
                new KeyRemapper(context, nativeImpl3, persistentDataStore, injector.mLooper);
        this.mPointerIconCache = new PointerIconCache(context, nativeImpl3);
        this.mGamePadRemapper = new GamePadRemapper(persistentDataStore);
        SEP_FULL =
                !context.getPackageManager()
                        .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite");
        boolean z =
                context.getResources()
                        .getBoolean(R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled);
        this.mUseDevInputEventForAudioJack = z;
        Slog.i("InputManager", "Initializing input manager, mUseDevInputEventForAudioJack=" + z);
        String string = context.getResources().getString(R.string.dream_accessibility_action_click);
        this.mDoubleTouchGestureEnableFile = TextUtils.isEmpty(string) ? null : new File(string);
        this.mVelocityTrackerStrategy =
                DeviceConfig.getProperty("input_native_boot", "velocitytracker_strategy");
        LocalServices.addService(LocalService.class, new LocalService());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        InputManagerHandler inputManagerHandler3 = inputManagerHandler;
        context.registerReceiver(anonymousClass32, intentFilter, null, inputManagerHandler3);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.android.intent.action.SET_INWATER_TOUCH");
        context.registerReceiver(
                anonymousClass33,
                intentFilter2,
                "com.samsung.android.permission.SEM_SET_DEVICE_BLOCK",
                inputManagerHandler3,
                4);
        context.registerReceiver(
                anonymousClass3,
                new IntentFilter("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE"),
                "com.samsung.kidshome.broadcast.DEFAULT_HOME_CHANGE_PERMISSION",
                inputManagerHandler3,
                4);
        context.registerReceiver(
                anonymousClass34,
                new IntentFilter("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI"),
                4);
        ToastDialog toastDialog = new ToastDialog(context);
        this.mToastDialog = toastDialog;
        ControlWakeKey controlWakeKey = new ControlWakeKey();
        controlWakeKey.mWakeKeyRefCount = null;
        controlWakeKey.mContext = context;
        HashMap hashMap = new HashMap();
        controlWakeKey.mWakeKeyRefCount = hashMap;
        controlWakeKey.makeWakeKeyRefCount("116,172", true);
        boolean z2 = hashMap.containsKey("114") || hashMap.containsKey("115");
        ControlWakeKey.writeWakeKeyVolume("/sys/power/volkey_wakeup", z2);
        ControlWakeKey.writeWakeKeyVolume("/sys/class/sec/ap_pmic/volkey_wakeup", z2);
        controlWakeKey.writeWakeKeyString(controlWakeKey.makeWakeKeyString());
        this.mControlWakeKey = controlWakeKey;
        if (IS_TABLET_DEVICE) {
            this.mWirelessKeyboardMouseShare =
                    new WirelessKeyboardMouseShare(context, this, toastDialog, obj);
            PowerManager.WakeLock newWakeLock =
                    ((PowerManager) context.getSystemService("power"))
                            .newWakeLock(1, "InputManager.mSharedKeyWakeLock");
            this.mSharedKeyWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
            this.mSharedKeyReferenceCount = 0;
        }
    }

    private boolean checkCallingPermission(String str, String str2) {
        return checkCallingPermission(str, str2, false);
    }

    public static boolean containsInputDeviceWithDescriptor(
            InputDevice[] inputDeviceArr, String str) {
        for (InputDevice inputDevice : inputDeviceArr) {
            if (inputDevice.getDescriptor().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String[] flatten(Map map) {
        ArrayList arrayList = new ArrayList(map.size() * 2);
        map.forEach(new InputManagerService$$ExternalSyntheticLambda0(6, arrayList));
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static String[] getExcludedDeviceNames() {
        ArrayList arrayList = new ArrayList();
        File[] fileArr = {Environment.getRootDirectory(), Environment.getVendorDirectory()};
        for (int i = 0; i < 2; i++) {
            File file = new File(fileArr[i], "etc/excluded-input-devices.xml");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    ArrayList arrayList2 = new ArrayList();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    XmlUtils.beginDocument(resolvePullParser, "devices");
                    while (true) {
                        XmlUtils.nextElement(resolvePullParser);
                        if (!"device".equals(resolvePullParser.getName())) {
                            break;
                        }
                        String attributeValue =
                                resolvePullParser.getAttributeValue((String) null, "name");
                        if (attributeValue != null) {
                            arrayList2.add(attributeValue);
                        }
                    }
                    arrayList.addAll(arrayList2);
                    fileInputStream.close();
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused) {
                continue;
            } catch (Exception e) {
                Slog.e("InputManager", "Could not parse '" + file.getAbsolutePath() + "'", e);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private String[] getKeyboardLayoutAssociations() {
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mAssociationsLock) {
            arrayMap.putAll(this.mKeyboardLayoutAssociations);
        }
        return flatten(arrayMap);
    }

    public static /* synthetic */ void lambda$dump$4(
            IndentingPrintWriter indentingPrintWriter, String str, String str2) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  layout: " + str2);
    }

    public static /* synthetic */ void lambda$dumpAssociations$5(
            IndentingPrintWriter indentingPrintWriter, String str, Integer num) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  display: " + num);
    }

    public static /* synthetic */ void lambda$dumpAssociations$6(
            IndentingPrintWriter indentingPrintWriter, String str, Integer num) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  display: " + num);
    }

    public static /* synthetic */ void lambda$dumpAssociations$7(
            IndentingPrintWriter indentingPrintWriter, String str, String str2) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  uniqueId: " + str2);
    }

    public static /* synthetic */ void lambda$dumpAssociations$8(
            IndentingPrintWriter indentingPrintWriter, String str, String str2) {
        indentingPrintWriter.print("  descriptor: " + str);
        indentingPrintWriter.println("  uniqueId: " + str2);
    }

    public static /* synthetic */ void lambda$dumpAssociations$9(
            IndentingPrintWriter indentingPrintWriter, String str, String str2) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  type: " + str2);
    }

    public static /* synthetic */ void lambda$flatten$10(List list, String str, Object obj) {
        list.add(str);
        list.add(obj.toString());
    }

    public static Map loadStaticInputPortAssociations() {
        File file = new File(Environment.getOdmDirectory(), "etc/input-port-associations.xml");
        if (!file.exists()) {
            file = new File(Environment.getVendorDirectory(), "etc/input-port-associations.xml");
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                Map processInputPortAssociations =
                        ConfigurationProcessor.processInputPortAssociations(fileInputStream);
                fileInputStream.close();
                return processInputPortAssociations;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
            return new HashMap();
        } catch (Exception e) {
            Slog.e("InputManager", "Could not parse '" + file.getAbsolutePath() + "'", e);
            return new HashMap();
        }
    }

    public final boolean addDeviceWirelessKeyboardShare(int i) {
        boolean z;
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (!this.mSystemReady || this.mWirelessKeyboardMouseShare == null) {
                    z = true;
                } else {
                    if (this.mContext
                                    .getPackageManager()
                                    .checkSignatures(1000, Binder.getCallingUid())
                            != 0) {
                        Slog.d(
                                "InputManager",
                                "addDeviceWirelessKeyboardShare : called by not allowed app");
                        return false;
                    }
                    z = this.mWirelessKeyboardMouseShare.addDevice(i);
                }
                Slog.d("InputManager", "addDeviceWirelessKeyboardShare");
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addKeyboardLayoutAssociation(String str, String str2, String str3) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(str3);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mKeyboardLayoutAssociations)
                    .put(str, TextUtils.formatSimple("%s,%s", new Object[] {str2, str3}));
        }
        this.mNative.changeKeyboardLayoutAssociation();
    }

    public final void addPortAssociation(String str, int i) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "addPortAssociation()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mRuntimeAssociations).put(str, Integer.valueOf(i));
        }
        this.mNative.notifyPortAssociationsChanged();
    }

    public final void addUniqueIdAssociationByDescriptor(String str, String str2) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "addUniqueIdAssociationByDescriptor()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mUniqueIdAssociationsByDescriptor).put(str, str2);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    public final void addUniqueIdAssociationByPort(String str, String str2) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "addUniqueIdAssociation()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mUniqueIdAssociationsByPort).put(str, str2);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    public final Notification.Action buildTurnOnAction(int i) {
        return new Notification.Action.Builder(
                        (Icon) null,
                        this.mContext.getString(17043030),
                        createPendingIntentAction(i))
                .build();
    }

    public final boolean canDispatchToDisplay(int i, int i2) {
        return this.mNative.canDispatchToDisplay(i, i2);
    }

    public final void cancelCurrentTouch() {
        if (!checkCallingPermission(
                "android.permission.MONITOR_INPUT", "cancelCurrentTouch()", false)) {
            throw new SecurityException("Requires MONITOR_INPUT permission");
        }
        this.mNative.cancelCurrentTouch();
    }

    public final void cancelVibrate(int i, IBinder iBinder) {
        synchronized (this.mVibratorLock) {
            VibratorToken vibratorToken =
                    (VibratorToken) ((ArrayMap) this.mVibratorTokens).get(iBinder);
            if (vibratorToken != null && vibratorToken.mDeviceId == i) {
                cancelVibrateIfNeeded(vibratorToken);
            }
        }
    }

    public final void cancelVibrateIfNeeded(VibratorToken vibratorToken) {
        synchronized (vibratorToken) {
            try {
                if (vibratorToken.mVibrating) {
                    this.mNative.cancelVibrate(vibratorToken.mDeviceId, vibratorToken.mTokenValue);
                    vibratorToken.mVibrating = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void changeDeviceWirelessKeyboardShare(String str, int i) {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
                    if (this.mContext
                                    .getPackageManager()
                                    .checkSignatures(1000, Binder.getCallingUid())
                            != 0) {
                        Slog.d(
                                "InputManager",
                                "changeDeviceWirelessKeyboardShare : called by not allowed app");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mWirelessKeyboardMouseShare.changeHIDDevice(i, str);
                        this.mNative.enableWirelessKeyboardShare(false);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                StringBuilder sb = new StringBuilder("changeDeviceWirelessKeyboardShare : ");
                if (str == null) {
                    str = null;
                }
                BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "InputManager");
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final boolean checkCallingPermission(String str, String str2, boolean z) {
        if (Binder.getCallingPid() == Process.myPid()
                || this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        if (z) {
            ActivityManagerInternal activityManagerInternal =
                    (ActivityManagerInternal)
                            LocalServices.getService(ActivityManagerInternal.class);
            Objects.requireNonNull(
                    activityManagerInternal, "ActivityManagerInternal should not be null.");
            int instrumentationSourceUid =
                    activityManagerInternal.getInstrumentationSourceUid(Binder.getCallingUid());
            if (instrumentationSourceUid != -1) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (this.mContext.checkPermission(str, -1, instrumentationSourceUid) == 0) {
                        return true;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m("Permission Denial: ", str2, " from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        m.append(" requires ");
        m.append(str);
        Slog.w("InputManager", m.toString());
        return false;
    }

    public final int checkInputFeature() {
        if (this.mSystemReady) {
            int tspSupportFeature =
                    ((SemInputDeviceManager)
                                    this.mContext.getSystemService("SemInputDeviceManagerService"))
                            .getTspSupportFeature(1);
            Slog.d(
                    "InputManager",
                    "checkInputFeature: features = "
                            .concat(String.format("0x%X", Integer.valueOf(tspSupportFeature))));
            return tspSupportFeature;
        }
        Slog.d(
                "InputManager",
                "checkInputFeature: system not ready, return 0. caller="
                        + Debug.getCallers(5)
                        + ", pid="
                        + Binder.getCallingPid());
        return 0;
    }

    public final boolean checkSystemSignature(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
            return true;
        }
        Log.d(
                "InputManager",
                "uid(" + callingUid + ") does not match signature of system uid :" + str);
        return false;
    }

    public final void clearAllModifierKeyRemappings() {
        clearAllModifierKeyRemappings_enforcePermission();
        KeyRemapper keyRemapper = this.mKeyRemapper;
        if (FeatureFlagUtils.isEnabled(
                keyRemapper.mContext, "settings_new_keyboard_modifier_key")) {
            keyRemapper.mHandler.sendMessage(Message.obtain(keyRemapper.mHandler, 3));
        }
    }

    public final void closeLightSession(int i, IBinder iBinder) {
        Objects.requireNonNull(iBinder);
        synchronized (this.mLightLock) {
            try {
                LightSession lightSession = (LightSession) this.mLightSessions.get(iBinder);
                Preconditions.checkState(lightSession != null, "not registered");
                Arrays.fill(lightSession.mLightStates, new LightState(0));
                setLightStatesInternal(i, lightSession.mLightIds, lightSession.mLightStates);
                this.mLightSessions.remove(iBinder);
                if (!this.mLightSessions.isEmpty()) {
                    LightSession lightSession2 = (LightSession) this.mLightSessions.valueAt(0);
                    setLightStatesInternal(i, lightSession2.mLightIds, lightSession2.mLightStates);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void connectByBtDevice(BluetoothDevice bluetoothDevice) {
        WirelessKeyboardMouseShare wirelessKeyboardMouseShare;
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (this.mSystemReady
                        && (wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare)
                                != null) {
                    wirelessKeyboardMouseShare.connectByBtDevice(bluetoothDevice);
                    Slog.d("InputManager", "connectByBtDevice");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void controlSpenWithToken(IBinder iBinder, boolean z) {
        if (!checkCallingPermission(
                "android.permission.DISABLE_INPUT_DEVICE", "disableInputDevice()", false)) {
            throw new SecurityException("Requires DISABLE_INPUT_DEVICE permission spen");
        }
        Objects.requireNonNull(iBinder, "spenContolToken must not be null");
        if (this.mSpenDeviceId == -1) {
            int[] deviceIds = InputDevice.getDeviceIds();
            int length = deviceIds.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                int i2 = deviceIds[i];
                if ("sec_e-pen".equals(InputDevice.getDevice(i2).getName())) {
                    this.mSpenDeviceId = i2;
                    break;
                }
                i++;
            }
        }
        if (this.mSpenDeviceId == -1) {
            Log.d("InputManager", "not find spen device");
            return;
        }
        Log.d(
                "InputManager",
                "call controlSpenWithToken by pid " + Binder.getCallingPid() + ", " + z);
        if (z) {
            if (this.mSpenControlToken == null) {
                Log.d(
                        "InputManager",
                        "disable spen by other or already enabled or call first time after"
                            + " booting");
                return;
            } else {
                enableInputDevice(this.mSpenDeviceId);
                this.mSpenControlToken = null;
                return;
            }
        }
        try {
            iBinder.linkToDeath(
                    new IBinder
                            .DeathRecipient() { // from class:
                                                // com.android.server.input.InputManagerService$$ExternalSyntheticLambda12
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            InputManagerService inputManagerService = InputManagerService.this;
                            boolean z2 = InputManagerService.DEBUG;
                            inputManagerService.lambda$controlSpenWithToken$1();
                        }
                    },
                    0);
            disableInputDevice(this.mSpenDeviceId);
            this.mSpenControlToken = iBinder;
        } catch (RemoteException unused) {
            Log.w("InputManager", "Client died before control spen");
        }
    }

    public final InputChannel createInputChannel(String str) {
        return this.mNative.createInputChannel(str, GnssNative.GNSS_AIDING_TYPE_ALL);
    }

    public final InputChannel createInputChannel(String str, int i) {
        return this.mNative.createInputChannel(str, i);
    }

    public final PendingIntent createPendingIntent() {
        Bundle m142m =
                AccountManagerService$$ExternalSyntheticOutline0.m142m(
                        ":settings:fragment_args_key", "increse_touch_sensetivity");
        Intent intent = new Intent("android.settings.DISPLAY_SETTINGS");
        intent.setFlags(268468224);
        intent.putExtra(":settings:show_fragment_args", m142m);
        return PendingIntent.getActivity(this.mContext, 0, intent, 67108864);
    }

    public final PendingIntent createPendingIntentAction(int i) {
        Intent intent = new Intent("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI");
        intent.putExtra("channelId", i);
        intent.setPackage(this.mContext.getPackageName());
        return PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
    }

    public final InputChannel createSpyWindowGestureMonitor(
            IBinder iBinder,
            String str,
            SurfaceControl surfaceControl,
            int i,
            int i2,
            int i3,
            int i4) {
        final InputChannel createInputChannel = this.mNative.createInputChannel(str, i4);
        try {
            iBinder.linkToDeath(
                    new IBinder
                            .DeathRecipient() { // from class:
                                                // com.android.server.input.InputManagerService$$ExternalSyntheticLambda6
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            InputManagerService inputManagerService = InputManagerService.this;
                            InputChannel inputChannel = createInputChannel;
                            boolean z = InputManagerService.DEBUG;
                            inputManagerService.lambda$createSpyWindowGestureMonitor$0(
                                    inputChannel);
                        }
                    },
                    0);
            synchronized (this.mInputMonitors) {
                ((HashMap) this.mInputMonitors)
                        .put(
                                createInputChannel.getToken(),
                                new GestureMonitorSpyWindow(
                                        iBinder,
                                        str,
                                        i,
                                        i2,
                                        i3,
                                        surfaceControl,
                                        createInputChannel));
            }
            InputChannel inputChannel = new InputChannel();
            createInputChannel.copyTo(inputChannel);
            return inputChannel;
        } catch (RemoteException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m58m(
                    "Client died before '", str, "' could be created.", "InputManager");
            return null;
        }
    }

    public final void deliverInputDevicesChanged(InputDevice[] inputDeviceArr) {
        this.mTempGamePads.clear();
        this.mTempInputDevicesChangedListenersToNotify.clear();
        synchronized (this.mInputDevicesLock) {
            try {
                if (this.mInputDevicesChangedPending) {
                    this.mInputDevicesChangedPending = false;
                    int size = this.mInputDevicesChangedListeners.size();
                    for (int i = 0; i < size; i++) {
                        this.mTempInputDevicesChangedListenersToNotify.add(
                                (InputDevicesChangedListenerRecord)
                                        this.mInputDevicesChangedListeners.valueAt(i));
                    }
                    int length = this.mInputDevices.length;
                    int[] iArr = new int[length * 2];
                    boolean z = false;
                    for (int i2 = 0; i2 < length; i2++) {
                        InputDevice inputDevice = this.mInputDevices[i2];
                        int i3 = i2 * 2;
                        iArr[i3] = inputDevice.getId();
                        iArr[i3 + 1] = inputDevice.getGeneration();
                        if (DEBUG) {
                            Log.d(
                                    "InputManager",
                                    "device "
                                            + inputDevice.getId()
                                            + " generation "
                                            + inputDevice.getGeneration());
                        }
                        int vendorId = inputDevice.getVendorId();
                        int productId = inputDevice.getProductId();
                        inputDevice.getName();
                        if (vendorId == 1256 && productId == 41013) {
                            z = true;
                        }
                        if (inputDevice.supportsSource(1025)
                                && ((inputDevice.getName() == null
                                                || !inputDevice.getName().contains("Test)"))
                                        && !containsInputDeviceWithDescriptor(
                                                inputDeviceArr, inputDevice.getDescriptor()))) {
                            this.mTempGamePads.add(inputDevice);
                        }
                    }
                    for (int i4 = 0; i4 < size; i4++) {
                        InputDevicesChangedListenerRecord inputDevicesChangedListenerRecord =
                                (InputDevicesChangedListenerRecord)
                                        this.mTempInputDevicesChangedListenersToNotify.get(i4);
                        inputDevicesChangedListenerRecord.getClass();
                        try {
                            inputDevicesChangedListenerRecord.mListener.onInputDevicesChanged(iArr);
                        } catch (RemoteException e) {
                            Slog.w(
                                    "InputManager",
                                    "Failed to notify process "
                                            + inputDevicesChangedListenerRecord.mPid
                                            + " that input devices changed, assuming it died.",
                                    e);
                            inputDevicesChangedListenerRecord.binderDied();
                        }
                    }
                    this.mTempInputDevicesChangedListenersToNotify.clear();
                    if (z != this.mPogoKeyboardConnected) {
                        this.mPogoKeyboardConnected = z;
                        if (this.mSystemReady) {
                            wakeUpWhenPogoConnected(z);
                            sendPogoKeyboardStatus(this.mPogoKeyboardConnected);
                        } else {
                            this.mAddingPogoKeyboardIntentPending = true;
                        }
                    }
                    if (this.mTempGamePads.isEmpty()) {
                        return;
                    }
                    int size2 = this.mTempGamePads.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        InputDevice inputDevice2 = (InputDevice) this.mTempGamePads.get(i5);
                        ((InputManagerCallback) this.mWindowManagerCallbacks)
                                .mService.mExt.mPolicyExt.startGameToolsService(
                                        inputDevice2.getVendorId(),
                                        inputDevice2.getProductId(),
                                        true);
                    }
                    this.mTempGamePads.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void deliverLidStateChanged(long j, boolean z) {
        int size;
        int i;
        ((ArrayList) this.mTempLidStateChangedListenersToNotify).clear();
        synchronized (this.mLidStateLock) {
            try {
                size = this.mLidStateChangedListeners.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((ArrayList) this.mTempLidStateChangedListenersToNotify)
                            .add(
                                    (LidStateChangedListenerRecord)
                                            this.mLidStateChangedListeners.valueAt(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i = 0; i < size; i++) {
            LidStateChangedListenerRecord lidStateChangedListenerRecord =
                    (LidStateChangedListenerRecord)
                            ((ArrayList) this.mTempLidStateChangedListenersToNotify).get(i);
            lidStateChangedListenerRecord.getClass();
            try {
                lidStateChangedListenerRecord.mListener.onLidStateChanged(j, z);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + lidStateChangedListenerRecord.mPid
                                + " that lid state changed, assuming it died.",
                        e);
                lidStateChangedListenerRecord.binderDied();
            }
        }
    }

    public final void deliverMultiFingerGesture(int i, int i2) {
        int size;
        int i3;
        ((ArrayList) this.mTempMultiFingerGestureListenersToNotify).clear();
        synchronized (this.mMultiFingerGestureLock) {
            try {
                size = this.mMultiFingerGestureListeners.size();
                for (int i4 = 0; i4 < size; i4++) {
                    ((ArrayList) this.mTempMultiFingerGestureListenersToNotify)
                            .add(
                                    (MultiFingerGestureListenerRecord)
                                            this.mMultiFingerGestureListeners.valueAt(i4));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i3 = 0; i3 < size; i3++) {
            MultiFingerGestureListenerRecord multiFingerGestureListenerRecord =
                    (MultiFingerGestureListenerRecord)
                            ((ArrayList) this.mTempMultiFingerGestureListenersToNotify).get(i3);
            multiFingerGestureListenerRecord.getClass();
            try {
                multiFingerGestureListenerRecord.mListener.onMultiFingerGesture(i, i2);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + multiFingerGestureListenerRecord.mPid
                                + " that multi finge gesture was made, assuming it died.",
                        e);
                multiFingerGestureListenerRecord.binderDied();
            }
        }
    }

    public final void deliverPointerIconChanged(int i, PointerIcon pointerIcon) {
        int size;
        int i2;
        ((ArrayList) this.mTempPointerIconChangedListenersToNotify).clear();
        synchronized (this.mPointerIconLock) {
            try {
                size = this.mPointerIconChangedListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((ArrayList) this.mTempPointerIconChangedListenersToNotify)
                            .add(
                                    (PointerIconChangedListenerRecord)
                                            this.mPointerIconChangedListeners.valueAt(i3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i2 = 0; i2 < size; i2++) {
            PointerIconChangedListenerRecord pointerIconChangedListenerRecord =
                    (PointerIconChangedListenerRecord)
                            ((ArrayList) this.mTempPointerIconChangedListenersToNotify).get(i2);
            pointerIconChangedListenerRecord.getClass();
            try {
                pointerIconChangedListenerRecord.mListener.onPointerIconChanged(i, pointerIcon);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + pointerIconChangedListenerRecord.mPid
                                + " that pointer icon changed, assuming it died.",
                        e);
                pointerIconChangedListenerRecord.binderDied();
            }
        }
    }

    public final void deliverSwitchEventChanged(int i, int i2, int i3, int i4) {
        int size;
        int i5;
        ((ArrayList) this.mTempSwitchEventChangedListenersToNotify).clear();
        synchronized (this.mSwitchEventChangedLock) {
            try {
                size = this.mSwitchEventChangedListeners.size();
                for (int i6 = 0; i6 < size; i6++) {
                    ((ArrayList) this.mTempSwitchEventChangedListenersToNotify)
                            .add(
                                    (SwitchEventChangedListenerRecord)
                                            this.mSwitchEventChangedListeners.valueAt(i6));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i5 = 0; i5 < size; i5++) {
            SwitchEventChangedListenerRecord switchEventChangedListenerRecord =
                    (SwitchEventChangedListenerRecord)
                            ((ArrayList) this.mTempSwitchEventChangedListenersToNotify).get(i5);
            switchEventChangedListenerRecord.getClass();
            try {
                switchEventChangedListenerRecord.mListener.onSwitchEventChanged(i, i2, i3, i4);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + switchEventChangedListenerRecord.mPid
                                + " that switch event changed was made, assuming it died.",
                        e);
                switchEventChangedListenerRecord.binderDied();
            }
        }
    }

    public final void deliverTabletModeChanged(long j, boolean z) {
        int size;
        int i;
        ((ArrayList) this.mTempTabletModeChangedListenersToNotify).clear();
        synchronized (this.mTabletModeLock) {
            try {
                size = this.mTabletModeChangedListeners.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((ArrayList) this.mTempTabletModeChangedListenersToNotify)
                            .add(
                                    (TabletModeChangedListenerRecord)
                                            this.mTabletModeChangedListeners.valueAt(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i = 0; i < size; i++) {
            TabletModeChangedListenerRecord tabletModeChangedListenerRecord =
                    (TabletModeChangedListenerRecord)
                            ((ArrayList) this.mTempTabletModeChangedListenersToNotify).get(i);
            tabletModeChangedListenerRecord.getClass();
            try {
                tabletModeChangedListenerRecord.mListener.onTabletModeChanged(j, z);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + tabletModeChangedListenerRecord.mPid
                                + " that tablet mode changed, assuming it died.",
                        e);
                tabletModeChangedListenerRecord.binderDied();
            }
        }
    }

    public final void deliverWirelessKeyboardShareChanged(int i, String str, boolean z) {
        int i2;
        int size;
        ((ArrayList) this.mTempWirelessKeyboardShareChangedListenersToNotify).clear();
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            if (i == 0 && z) {
                try {
                    this.mSharedKeyReferenceCount = 0;
                    this.mSharedKeyWakeLock.release();
                } finally {
                }
            }
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            try {
                size = this.mWirelessKeyboardShareChangedListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((ArrayList) this.mTempWirelessKeyboardShareChangedListenersToNotify)
                            .add(
                                    (WirelessKeyboardShareChangedListenerRecord)
                                            this.mWirelessKeyboardShareChangedListeners.valueAt(
                                                    i3));
                }
            } finally {
            }
        }
        for (i2 = 0; i2 < size; i2++) {
            WirelessKeyboardShareChangedListenerRecord wirelessKeyboardShareChangedListenerRecord =
                    (WirelessKeyboardShareChangedListenerRecord)
                            ((ArrayList) this.mTempWirelessKeyboardShareChangedListenersToNotify)
                                    .get(i2);
            long uptimeMillis = SystemClock.uptimeMillis();
            wirelessKeyboardShareChangedListenerRecord.getClass();
            try {
                wirelessKeyboardShareChangedListenerRecord.mListener.onWirelessKeyboardShareChanged(
                        uptimeMillis, i, str);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + wirelessKeyboardShareChangedListenerRecord.mPid
                                + " that wireless keyboard share changed, assuming it died.",
                        e);
                wirelessKeyboardShareChangedListenerRecord.binderDied();
            }
        }
    }

    public final void disableInputDevice(int i) {
        if (!checkCallingPermission(
                "android.permission.DISABLE_INPUT_DEVICE", "disableInputDevice()", false)) {
            throw new SecurityException("Requires DISABLE_INPUT_DEVICE permission");
        }
        this.mNative.disableInputDevice(i);
        int i2 = this.mSpenDeviceId;
        if (i2 == -1 || i2 != i) {
            return;
        }
        this.mSpenControlToken = null;
        Log.d("InputManager", "disable spen after calling controlSpenWithToken");
    }

    public final void disableSensor(int i, int i2) {
        synchronized (this.mInputDevicesLock) {
            this.mNative.disableSensor(i, i2);
        }
    }

    public final KeyEvent dispatchUnhandledKey(IBinder iBinder, KeyEvent keyEvent, int i) {
        return ((InputManagerCallback) this.mWindowManagerCallbacks)
                .dispatchUnhandledKey(iBinder, keyEvent, i);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        if (DumpUtils.checkDumpPermission(this.mContext, "InputManager", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("INPUT MANAGER (dumpsys input)\n");
            String dump = this.mNative.dump();
            if (dump != null) {
                printWriter.println(dump);
            }
            indentingPrintWriter.println("Input Manager Service (Java) State:");
            indentingPrintWriter.increaseIndent();
            dumpAssociations(indentingPrintWriter);
            dumpSpyWindowGestureMonitors(indentingPrintWriter);
            dumpDisplayInputPropertiesValues(indentingPrintWriter);
            BatteryController batteryController = this.mBatteryController;
            batteryController.getClass();
            IndentingPrintWriter indentingPrintWriter2 =
                    new IndentingPrintWriter(indentingPrintWriter);
            synchronized (batteryController.mLock) {
                try {
                    indentingPrintWriter2.println("BatteryController:");
                    indentingPrintWriter2.increaseIndent();
                    indentingPrintWriter2.println(
                            "State: Polling = "
                                    + batteryController.mIsPolling
                                    + ", Interactive = "
                                    + batteryController.mIsInteractive);
                    StringBuilder sb = new StringBuilder("Listeners: ");
                    sb.append(batteryController.mListenerRecords.size());
                    sb.append(" battery listeners");
                    indentingPrintWriter2.println(sb.toString());
                    indentingPrintWriter2.increaseIndent();
                    for (int i2 = 0; i2 < batteryController.mListenerRecords.size(); i2++) {
                        indentingPrintWriter2.println(
                                i2 + ": " + batteryController.mListenerRecords.valueAt(i2));
                    }
                    indentingPrintWriter2.decreaseIndent();
                    indentingPrintWriter2.println(
                            "Device Monitors: "
                                    + batteryController.mDeviceMonitors.size()
                                    + " monitors");
                    indentingPrintWriter2.increaseIndent();
                    for (int i3 = 0; i3 < batteryController.mDeviceMonitors.size(); i3++) {
                        indentingPrintWriter2.println(
                                i3 + ": " + batteryController.mDeviceMonitors.valueAt(i3));
                    }
                    indentingPrintWriter2.decreaseIndent();
                    indentingPrintWriter2.decreaseIndent();
                } finally {
                }
            }
            this.mKeyboardBacklightController.dump(indentingPrintWriter);
            KeyboardLedController keyboardLedController = this.mKeyboardLedController;
            keyboardLedController.getClass();
            IndentingPrintWriter indentingPrintWriter3 =
                    new IndentingPrintWriter(indentingPrintWriter);
            indentingPrintWriter3.println(
                    "KeyboardLedController: "
                            + keyboardLedController.mKeyboardsWithMicMuteLed.size()
                            + " keyboard mic mute lights");
            indentingPrintWriter3.increaseIndent();
            for (i = 0; i < keyboardLedController.mKeyboardsWithMicMuteLed.size(); i++) {
                InputDevice inputDevice =
                        (InputDevice) keyboardLedController.mKeyboardsWithMicMuteLed.valueAt(i);
                indentingPrintWriter3.println(
                        i
                                + " "
                                + inputDevice.getName()
                                + ": "
                                + KeyboardLedController.getKeyboardMicMuteLight(inputDevice)
                                        .toString());
            }
            indentingPrintWriter3.decreaseIndent();
            this.mKeyboardLayoutManager.dump(indentingPrintWriter);
            synchronized (this.mAssociationsLock) {
                try {
                    if (!((ArrayMap) this.mKeyboardLayoutAssociations).isEmpty()) {
                        indentingPrintWriter.println("Keyboard layout Associations:");
                        ((ArrayMap) this.mKeyboardLayoutAssociations)
                                .forEach(
                                        new InputManagerService$$ExternalSyntheticLambda0(
                                                0, indentingPrintWriter));
                    }
                } finally {
                }
            }
            synchronized (this) {
                try {
                    printWriter.println("  mBlockDeviceMode=" + this.mBlockDeviceMode);
                    Vector vector = this.mBlockTspCallerList;
                    if (vector != null && vector.size() > 0) {
                        printWriter.println("      TSP blocked by:");
                        Iterator it = this.mBlockTspCallerList.iterator();
                        while (it.hasNext()) {
                            printWriter.println("          " + ((String) it.next()));
                        }
                    }
                    Vector vector2 = this.mBlockTkeyCallerList;
                    if (vector2 != null && vector2.size() > 0) {
                        printWriter.println("      TKEY blocked by:");
                        Iterator it2 = this.mBlockTkeyCallerList.iterator();
                        while (it2.hasNext()) {
                            printWriter.println("          " + ((String) it2.next()));
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void dumpAssociations(IndentingPrintWriter indentingPrintWriter) {
        if (!this.mStaticAssociations.isEmpty()) {
            indentingPrintWriter.println("Static Associations:");
            this.mStaticAssociations.forEach(
                    new InputManagerService$$ExternalSyntheticLambda0(3, indentingPrintWriter));
        }
        synchronized (this.mAssociationsLock) {
            try {
                if (!((ArrayMap) this.mRuntimeAssociations).isEmpty()) {
                    indentingPrintWriter.println("Runtime Associations:");
                    ((ArrayMap) this.mRuntimeAssociations)
                            .forEach(
                                    new InputManagerService$$ExternalSyntheticLambda0(
                                            4, indentingPrintWriter));
                }
                if (!((ArrayMap) this.mUniqueIdAssociationsByPort).isEmpty()) {
                    indentingPrintWriter.println("Unique Id Associations:");
                    ((ArrayMap) this.mUniqueIdAssociationsByPort)
                            .forEach(
                                    new InputManagerService$$ExternalSyntheticLambda0(
                                            5, indentingPrintWriter));
                }
                if (!((ArrayMap) this.mUniqueIdAssociationsByDescriptor).isEmpty()) {
                    indentingPrintWriter.println("Unique Id Associations:");
                    ((ArrayMap) this.mUniqueIdAssociationsByDescriptor)
                            .forEach(
                                    new InputManagerService$$ExternalSyntheticLambda0(
                                            1, indentingPrintWriter));
                }
                if (!((ArrayMap) this.mDeviceTypeAssociations).isEmpty()) {
                    indentingPrintWriter.println("Type Associations:");
                    ((ArrayMap) this.mDeviceTypeAssociations)
                            .forEach(
                                    new InputManagerService$$ExternalSyntheticLambda0(
                                            2, indentingPrintWriter));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpDisplayInputPropertiesValues(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            indentingPrintWriter.println("mAdditionalDisplayInputProperties:");
            indentingPrintWriter.increaseIndent();
            try {
                if (this.mAdditionalDisplayInputProperties.size() == 0) {
                    indentingPrintWriter.println("<none>");
                    return;
                }
                for (int i = 0; i < this.mAdditionalDisplayInputProperties.size(); i++) {
                    indentingPrintWriter.println(
                            "displayId: " + this.mAdditionalDisplayInputProperties.keyAt(i));
                    AdditionalDisplayInputProperties additionalDisplayInputProperties =
                            (AdditionalDisplayInputProperties)
                                    this.mAdditionalDisplayInputProperties.valueAt(i);
                    indentingPrintWriter.println(
                            "mousePointerAccelerationEnabled: "
                                    + additionalDisplayInputProperties
                                            .mousePointerAccelerationEnabled);
                    indentingPrintWriter.println(
                            "pointerIconVisible: "
                                    + additionalDisplayInputProperties.pointerIconVisible);
                }
            } finally {
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void dumpSpyWindowGestureMonitors(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mInputMonitors) {
            try {
                if (((HashMap) this.mInputMonitors).isEmpty()) {
                    return;
                }
                indentingPrintWriter.println("Gesture Monitors (implemented as spy windows):");
                Iterator it = ((HashMap) this.mInputMonitors).values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    indentingPrintWriter
                            .append("  " + i + ": ")
                            .println(((GestureMonitorSpyWindow) it.next()).dump());
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enableInputDevice(int i) {
        if (!checkCallingPermission(
                "android.permission.DISABLE_INPUT_DEVICE", "enableInputDevice()", false)) {
            throw new SecurityException("Requires DISABLE_INPUT_DEVICE permission");
        }
        this.mNative.enableInputDevice(i);
        int i2 = this.mSpenDeviceId;
        if (i2 == -1 || i2 != i) {
            return;
        }
        Log.d("InputManager", "enable spen after calling controlSpenWithToken");
    }

    public final boolean enableSensor(int i, int i2, int i3, int i4) {
        boolean enableSensor;
        synchronized (this.mInputDevicesLock) {
            enableSensor = this.mNative.enableSensor(i, i2, i3, i4);
        }
        return enableSensor;
    }

    public final boolean filterInputEvent(InputEvent inputEvent, int i) {
        synchronized (this.mInputFilterLock) {
            IInputFilter iInputFilter = this.mInputFilter;
            if (iInputFilter != null) {
                try {
                    iInputFilter.filterInputEvent(inputEvent, i);
                } catch (RemoteException unused) {
                }
                return false;
            }
            inputEvent.recycle();
            return true;
        }
    }

    public final boolean flushSensor(int i, int i2) {
        synchronized (this.mSensorEventLock) {
            try {
                if (((SensorEventListenerRecord)
                                this.mSensorEventListeners.get(Binder.getCallingPid()))
                        == null) {
                    return false;
                }
                return this.mNative.flushSensor(i, i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forceFadeIcon(int i) {
        this.mNative.forceFadeIcon(i);
    }

    public final IInputDeviceBatteryState getBatteryState(int i) {
        BatteryController.State batteryStateForReporting;
        BatteryController batteryController = this.mBatteryController;
        synchronized (batteryController.mLock) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                BatteryController.DeviceMonitor deviceMonitor =
                        (BatteryController.DeviceMonitor)
                                batteryController.mDeviceMonitors.get(Integer.valueOf(i));
                if (deviceMonitor == null) {
                    batteryStateForReporting =
                            batteryController.queryBatteryStateFromNative(
                                    i,
                                    uptimeMillis,
                                    ((Boolean)
                                                    batteryController.processInputDevice(
                                                            i,
                                                            Boolean.FALSE,
                                                            new BatteryController$$ExternalSyntheticLambda0(
                                                                    0)))
                                            .booleanValue());
                } else {
                    deviceMonitor.onPoll(uptimeMillis);
                    batteryStateForReporting = deviceMonitor.getBatteryStateForReporting();
                }
            } finally {
            }
        }
        return batteryStateForReporting;
    }

    public final int getCurrentSwitchEventState(int i, boolean z) {
        if (!z) {
            if ((i & 1) != 0) {
                return this.mPogoKeyboardConnected ? 1 : 0;
            }
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 32; i3++) {
            int i4 = 1 << i3;
            if ((i & i4) != 0 && this.mNative.getSwitchState(-1, -256, i3) == 1) {
                i2 |= i4;
            }
        }
        return i2;
    }

    public final PointerIcon getDefaultPointerIcon() {
        return this.mDefaultPointerIcon;
    }

    public final String getDeviceAlias(String str) {
        BluetoothAdapter.checkBluetoothAddress(str);
        return null;
    }

    public String[] getDeviceTypeAssociations() {
        HashMap hashMap;
        synchronized (this.mAssociationsLock) {
            hashMap = new HashMap(this.mDeviceTypeAssociations);
        }
        return flatten(hashMap);
    }

    public final int getDisplayIdForPointerIcon() {
        return this.mDisplayIdForPointerIcon;
    }

    public final int getDoubleTapTimeout() {
        return ViewConfiguration.getDoubleTapTimeout();
    }

    public final PointerIcon getForcedDefaultPointerIcon() {
        return this.mForcedDefaultPointerIcon;
    }

    public final int getGamePadRemapButtonAxisPolicy(String str, int i) {
        int i2;
        Integer num;
        int intValue;
        int stickForGamePadProfiles;
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        if (gamePadRemapper == null) {
            return i;
        }
        if (str != null) {
            synchronized (gamePadRemapper.mDeviceToProfileLock) {
                i2 = -1;
                num =
                        (Integer)
                                ((HashMap) gamePadRemapper.mDeviceToProfile)
                                        .getOrDefault(str.toUpperCase(), -1);
                intValue = num.intValue();
            }
            if (intValue != 0 && GamePadRemapper.isValidId(intValue)) {
                synchronized (gamePadRemapper.mDataStore) {
                    try {
                        if (i != 2048 && i != 2049 && i != 2059) {
                            switch (i) {
                                case 2062:
                                case 2063:
                                case 2064:
                                    break;
                                default:
                                    PersistentDataStore persistentDataStore =
                                            gamePadRemapper.mDataStore;
                                    persistentDataStore.loadIfNeededGamePadProfiles();
                                    stickForGamePadProfiles =
                                            ((Integer)
                                                            ((PersistentDataStore.GamePadProfile)
                                                                            ((HashMap)
                                                                                            persistentDataStore
                                                                                                    .mGamePadProfiles)
                                                                                    .get(num))
                                                                    .mSimpeButtonMap.getOrDefault(
                                                                            Integer.valueOf(i),
                                                                            Integer.valueOf(i)))
                                                    .intValue();
                                    break;
                            }
                            i = stickForGamePadProfiles;
                        }
                        if (i == 2048 || i == 2049) {
                            i2 = 2048;
                        } else {
                            if (i != 2059) {
                                switch (i) {
                                    case 2063:
                                    case 2064:
                                        i2 = 2063;
                                        break;
                                }
                            }
                            i2 = 2059;
                        }
                        stickForGamePadProfiles =
                                gamePadRemapper.mDataStore.getStickForGamePadProfiles(intValue, i2);
                        i = stickForGamePadProfiles;
                    } finally {
                    }
                }
            }
        }
        return i;
    }

    public final String getGamepadProfile(int i) {
        String str;
        if (this.mGamePadRemapper == null || !checkSystemSignature("getProfile")) {
            return "{}";
        }
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        gamePadRemapper.getClass();
        boolean isValidId = GamePadRemapper.isValidId(i);
        try {
            JSONObject jSONObject = new JSONObject();
            if (isValidId) {
                synchronized (gamePadRemapper.mDataStore) {
                    try {
                        String str2 =
                                ((PersistentDataStore.GamePadProfile)
                                                ((HashMap)
                                                                gamePadRemapper
                                                                        .mDataStore
                                                                        .mGamePadProfiles)
                                                        .get(Integer.valueOf(i)))
                                        .mName;
                        boolean z =
                                ((PersistentDataStore.GamePadProfile)
                                                ((HashMap)
                                                                gamePadRemapper
                                                                        .mDataStore
                                                                        .mGamePadProfiles)
                                                        .get(Integer.valueOf(i)))
                                        .mUsed;
                        jSONObject.put("ProfileName", str2);
                        jSONObject.put("ProfileUsed", z ? "true" : "false");
                        Iterator it = ((ArraySet) GamePadRemapper.SIMPLE_BUTTON_LIST).iterator();
                        while (it.hasNext()) {
                            Integer num = (Integer) it.next();
                            int intValue = num.intValue();
                            PersistentDataStore persistentDataStore = gamePadRemapper.mDataStore;
                            persistentDataStore.loadIfNeededGamePadProfiles();
                            int intValue2 =
                                    ((Integer)
                                                    ((PersistentDataStore.GamePadProfile)
                                                                    ((HashMap)
                                                                                    persistentDataStore
                                                                                            .mGamePadProfiles)
                                                                            .get(
                                                                                    Integer.valueOf(
                                                                                            i)))
                                                            .mSimpeButtonMap.getOrDefault(num, num))
                                            .intValue();
                            if (intValue != intValue2) {
                                jSONObject.put("ButtonCode=" + intValue, "ToCode=" + intValue2);
                            }
                        }
                        Iterator it2 = ((ArraySet) GamePadRemapper.SIMPLE_STICK_LIST).iterator();
                        while (it2.hasNext()) {
                            int intValue3 = ((Integer) it2.next()).intValue();
                            int stickForGamePadProfiles =
                                    gamePadRemapper.mDataStore.getStickForGamePadProfiles(
                                            i, intValue3);
                            if (intValue3 != stickForGamePadProfiles) {
                                jSONObject.put(
                                        "StickCode=" + intValue3,
                                        "ToCode="
                                                + (stickForGamePadProfiles & 4095)
                                                + " h="
                                                + ((stickForGamePadProfiles & 32768) == 32768
                                                        ? "true"
                                                        : "false")
                                                + " v="
                                                + ((stickForGamePadProfiles
                                                                        & EndpointMonitorConst
                                                                                .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                                                                == 16384
                                                        ? "true"
                                                        : "false")
                                                + " r="
                                                + ((stickForGamePadProfiles & 4096) == 4096
                                                        ? "true"
                                                        : "false"));
                            }
                        }
                    } finally {
                    }
                }
            }
            str = jSONObject.toString();
        } catch (JSONException e) {
            Log.e("InputManager-GamePadRemapper", "Json getProfile error: " + e.getMessage());
            str = "{}";
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m("getProfile ", str, "InputManager");
        return str;
    }

    public final int[] getGamepadProfileIds() {
        int[] iArr;
        if (this.mGamePadRemapper == null) {
            return new int[0];
        }
        if (!checkSystemSignature("getGamepadProfileIds")) {
            return new int[0];
        }
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        synchronized (gamePadRemapper.mDataStore) {
            Map map = gamePadRemapper.mDataStore.mGamePadProfiles;
            if (map != null && !((HashMap) map).isEmpty()) {
                iArr = new int[5];
                for (int i = 0; i < 5; i++) {
                    iArr[i] = i;
                }
            }
            iArr = new int[0];
        }
        Log.d("InputManager", "getGamepadProfileIds " + Arrays.toString(iArr));
        return iArr;
    }

    public final int getGlobalMetaState(int i) {
        return this.mNative.getGlobalMetaState(i);
    }

    public final HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) {
        return this.mDisplayManagerInternal.getHostUsiVersion(i);
    }

    public final int getHoverTapSlop() {
        return ViewConfiguration.getHoverTapSlop();
    }

    public final int getHoverTapTimeout() {
        return ViewConfiguration.getHoverTapTimeout();
    }

    public final int getInboundQueueLength() {
        if (Binder.getCallingUid() == 1000) {
            return this.mNative.getInboundQueueLength();
        }
        return 0;
    }

    public final InputDevice getInputDevice(int i) {
        synchronized (this.mInputDevicesLock) {
            try {
                for (InputDevice inputDevice : this.mInputDevices) {
                    if (inputDevice.getId() == i) {
                        return inputDevice;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getInputDeviceBluetoothAddress(int i) {
        getInputDeviceBluetoothAddress_enforcePermission();
        String bluetoothAddress = this.mNative.getBluetoothAddress(i);
        if (bluetoothAddress == null) {
            return null;
        }
        if (BluetoothAdapter.checkBluetoothAddress(bluetoothAddress)) {
            return bluetoothAddress;
        }
        throw new IllegalStateException(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "The Bluetooth address of input device ",
                        " should not be invalid: address=",
                        bluetoothAddress));
    }

    public final int[] getInputDeviceIds() {
        int[] iArr;
        synchronized (this.mInputDevicesLock) {
            try {
                int length = this.mInputDevices.length;
                iArr = new int[length];
                for (int i = 0; i < length; i++) {
                    iArr[i] = this.mInputDevices[i].getId();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    public final InputDevice[] getInputDevices() {
        InputDevice[] inputDeviceArr;
        synchronized (this.mInputDevicesLock) {
            inputDeviceArr = this.mInputDevices;
        }
        return inputDeviceArr;
    }

    public final String[] getInputPortAssociations() {
        HashMap hashMap = new HashMap(this.mStaticAssociations);
        synchronized (this.mAssociationsLock) {
            hashMap.putAll(this.mRuntimeAssociations);
        }
        return flatten(hashMap);
    }

    public final String[] getInputUniqueIdAssociationsByDescriptor() {
        HashMap hashMap;
        synchronized (this.mAssociationsLock) {
            hashMap = new HashMap(this.mUniqueIdAssociationsByDescriptor);
        }
        return flatten(hashMap);
    }

    public final String[] getInputUniqueIdAssociationsByPort() {
        HashMap hashMap;
        synchronized (this.mAssociationsLock) {
            hashMap = new HashMap(this.mUniqueIdAssociationsByPort);
        }
        return flatten(hashMap);
    }

    public final KeyCharacterMap getKeyCharacterMap(String str) {
        Objects.requireNonNull(str, "layoutDescriptor must not be null");
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        keyboardLayoutManager.getClass();
        String[] strArr = new String[1];
        keyboardLayoutManager.visitKeyboardLayout(
                str, new KeyboardLayoutManager$$ExternalSyntheticLambda1(1, strArr));
        return TextUtils.isEmpty(strArr[0])
                ? KeyCharacterMap.load(-1)
                : KeyCharacterMap.load(str, strArr[0]);
    }

    public final int getKeyCodeForKeyLocation(int i, int i2) {
        if (i2 <= 0 || i2 > KeyEvent.getMaxKeyCode()) {
            return 0;
        }
        return this.mNative.getKeyCodeForKeyLocation(i, i2);
    }

    public final int getKeyCodeState(int i, int i2, int i3) {
        return this.mNative.getKeyCodeState(i, i2, i3);
    }

    public final KeyboardLayout getKeyboardLayout(String str) {
        return this.mKeyboardLayoutManager.getKeyboardLayout(str);
    }

    public final KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(
            InputDeviceIdentifier inputDeviceIdentifier,
            int i,
            InputMethodInfo inputMethodInfo,
            InputMethodSubtype inputMethodSubtype) {
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        InputDevice inputDevice = keyboardLayoutManager.getInputDevice(inputDeviceIdentifier);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return KeyboardLayoutSelectionResult.FAILED;
        }
        KeyboardLayoutSelectionResult keyboardLayoutForInputDeviceInternal =
                keyboardLayoutManager.getKeyboardLayoutForInputDeviceInternal(
                        new KeyboardLayoutManager.KeyboardIdentifier(inputDevice),
                        new KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype));
        if (!KeyboardLayoutManager.DEBUG) {
            return keyboardLayoutForInputDeviceInternal;
        }
        Slog.d(
                "KeyboardLayoutManager",
                "getKeyboardLayoutForInputDevice() "
                        + inputDeviceIdentifier.toString()
                        + ", userId : "
                        + i
                        + ", subtype = "
                        + inputMethodSubtype
                        + " -> "
                        + keyboardLayoutForInputDeviceInternal);
        return keyboardLayoutForInputDeviceInternal;
    }

    public final KeyboardLayout[] getKeyboardLayoutListForInputDevice(
            InputDeviceIdentifier inputDeviceIdentifier,
            int i,
            InputMethodInfo inputMethodInfo,
            InputMethodSubtype inputMethodSubtype) {
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        InputDevice inputDevice = keyboardLayoutManager.getInputDevice(inputDeviceIdentifier);
        return (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard())
                ? new KeyboardLayout[0]
                : keyboardLayoutManager.getKeyboardLayoutListForInputDeviceInternal(
                        new KeyboardLayoutManager.KeyboardIdentifier(inputDevice),
                        new KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype));
    }

    public final String[] getKeyboardLayoutOverlay(
            InputDeviceIdentifier inputDeviceIdentifier, String str, String str2) {
        String layoutDescriptor;
        if (!this.mSystemReady) {
            return null;
        }
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        synchronized (keyboardLayoutManager.mImeInfoLock) {
            layoutDescriptor =
                    keyboardLayoutManager
                            .getKeyboardLayoutForInputDeviceInternal(
                                    new KeyboardLayoutManager.KeyboardIdentifier(
                                            inputDeviceIdentifier, str, str2),
                                    keyboardLayoutManager.mCurrentImeInfo)
                            .getLayoutDescriptor();
        }
        if (layoutDescriptor == null) {
            return null;
        }
        Log.d(
                "KeyboardLayoutManager",
                "Overlay KLD=" + layoutDescriptor + ", dev=" + inputDeviceIdentifier);
        String[] strArr = new String[2];
        keyboardLayoutManager.visitKeyboardLayout(
                layoutDescriptor, new KeyboardLayoutManager$$ExternalSyntheticLambda1(0, strArr));
        if (strArr[0] != null) {
            return strArr;
        }
        PinnerService$$ExternalSyntheticOutline0.m(
                "Could not get keyboard layout with descriptor '",
                layoutDescriptor,
                "'.",
                "KeyboardLayoutManager");
        return null;
    }

    public final KeyboardLayout[] getKeyboardLayouts() {
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        keyboardLayoutManager.getClass();
        ArrayList arrayList = new ArrayList();
        keyboardLayoutManager.visitAllKeyboardLayouts(
                new KeyboardLayoutManager$$ExternalSyntheticLambda0(1, arrayList));
        return (KeyboardLayout[]) arrayList.toArray(new KeyboardLayout[0]);
    }

    public final long getLastLidEventTimeNanos() {
        return this.mLastLidEventTime;
    }

    public final int getLidState() {
        return this.mNative.getSwitchState(-1, -256, 0);
    }

    public final LightState getLightState(int i, int i2) {
        LightState lightState;
        synchronized (this.mLightLock) {
            lightState =
                    new LightState(
                            this.mNative.getLightColor(i, i2),
                            this.mNative.getLightPlayerId(i, i2));
        }
        return lightState;
    }

    public final List getLights(int i) {
        return this.mNative.getLights(i);
    }

    public final PointerIcon getLoadedPointerIcon(int i, int i2) {
        PointerIcon pointerIcon;
        PointerIconCache pointerIconCache = this.mPointerIconCache;
        synchronized (pointerIconCache.mLoadedPointerIconsByDisplayAndType) {
            try {
                SparseArray sparseArray =
                        (SparseArray) pointerIconCache.mLoadedPointerIconsByDisplayAndType.get(i);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    pointerIconCache.mLoadedPointerIconsByDisplayAndType.put(i, sparseArray);
                }
                pointerIcon = (PointerIcon) sparseArray.get(i2);
                if (pointerIcon == null) {
                    Context contextForDisplayLocked =
                            pointerIconCache.getContextForDisplayLocked(i);
                    Resources.Theme newTheme = contextForDisplayLocked.getResources().newTheme();
                    newTheme.setTo(contextForDisplayLocked.getTheme());
                    newTheme.applyStyle(
                            PointerIcon.vectorFillStyleToResource(
                                    pointerIconCache.mPointerIconFillStyle),
                            true);
                    pointerIcon =
                            PointerIcon.getLoadedSystemIcon(
                                    new ContextThemeWrapper(contextForDisplayLocked, newTheme),
                                    i2,
                                    pointerIconCache.mUseLargePointerIcons,
                                    pointerIconCache.mPointerIconScale);
                    sparseArray.put(i2, pointerIcon);
                }
                Objects.requireNonNull(pointerIcon);
            } catch (Throwable th) {
                throw th;
            }
        }
        return pointerIcon;
    }

    public final int getLongPressTimeout() {
        return ViewConfiguration.getLongPressTimeout();
    }

    public final Map getModifierKeyRemapping() {
        getModifierKeyRemapping_enforcePermission();
        return this.mKeyRemapper.getKeyRemapping();
    }

    public final int getMousePointerSpeed() {
        return this.mNative.getMousePointerSpeed();
    }

    public final int getMultiFingerGestureBehavior(String str, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("def", String.valueOf(i));
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call == null) {
                return i;
            }
            int parseInt = Integer.parseInt(call.getString(str));
            return (parseInt < 0 || parseInt > 6) ? i : parseInt;
        } catch (IllegalArgumentException e) {
            Log.e("InputManager", "Failed to get settings " + str, e);
            return i;
        }
    }

    public final long getParentSurfaceForPointers(int i) {
        SurfaceControl surfaceControl;
        InputManagerCallback inputManagerCallback =
                (InputManagerCallback) this.mWindowManagerCallbacks;
        WindowManagerGlobalLock windowManagerGlobalLock = inputManagerCallback.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent =
                        inputManagerCallback.mService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e(
                            "WindowManager",
                            "Failed to get parent surface for pointers on display "
                                    + i
                                    + " - DisplayContent not found.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    surfaceControl = null;
                } else {
                    surfaceControl = displayContent.mOverlayLayer;
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        if (surfaceControl == null) {
            return 0L;
        }
        return surfaceControl.mNativeObject;
    }

    public final int getPointerIconType() {
        return this.mLastPointerIconType;
    }

    public final int getPointerLayer() {
        return ((InputManagerCallback) this.mWindowManagerCallbacks).getPointerLayer();
    }

    public final int getScanCodeState(int i, int i2, int i3) {
        return this.mNative.getScanCodeState(i, i2, i3);
    }

    public final InputSensorInfo[] getSensorList(int i) {
        return this.mNative.getSensorList(i);
    }

    public final int getSetPenModeOnDex(int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", "spen_mode");
        bundle.putString("def", i == 0 ? "pen" : "mouse");
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                return call.getString("spen_mode").equals("mouse") ? 1 : 0;
            }
        } catch (IllegalArgumentException e) {
            Log.e("InputManager", "Failed to get settings SETTINGS_KEY_SPEN_MODE", e);
        }
        return i;
    }

    public final String getSupportButtonNStick() {
        String str = "{}";
        if (this.mGamePadRemapper == null || !checkSystemSignature("getSupportButtonNStick")) {
            return "{}";
        }
        this.mGamePadRemapper.getClass();
        try {
            JSONObject jSONObject = new JSONObject();
            Iterator it = ((ArraySet) GamePadRemapper.SIMPLE_BUTTON_LIST).iterator();
            jSONObject.put("BUTTON_START", "");
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                jSONObject.put(
                        Integer.toString(intValue), GamePadRemapper.getButtonString(intValue));
            }
            jSONObject.put("BUTTON_END", "");
            jSONObject.put("STICK_START", "");
            Iterator it2 = ((ArraySet) GamePadRemapper.SIMPLE_STICK_LIST).iterator();
            while (it2.hasNext()) {
                int intValue2 = ((Integer) it2.next()).intValue();
                jSONObject.put(
                        Integer.toString(intValue2), GamePadRemapper.getButtonString(intValue2));
            }
            jSONObject.put("STICK_END", "");
            str = jSONObject.toString();
        } catch (JSONException e) {
            Log.e(
                    "InputManager-GamePadRemapper",
                    "Json getSupportButtonNStick error: " + e.getMessage());
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "getSupportButtonNStick ", str, "InputManager");
        return str;
    }

    public final int getSwitchState(int i, int i2, int i3) {
        return this.mNative.getSwitchState(i, i2, i3);
    }

    public final int getToolTypeForDefaultPointerIcon() {
        return this.mToolTypeForDefaultPointerIcon;
    }

    public final TouchCalibration getTouchCalibrationForInputDevice(String str, int i) {
        TouchCalibration touchCalibration;
        Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
        synchronized (this.mDataStore) {
            PersistentDataStore persistentDataStore = this.mDataStore;
            persistentDataStore.loadIfNeeded();
            PersistentDataStore.InputDeviceState inputDeviceState =
                    (PersistentDataStore.InputDeviceState)
                            persistentDataStore.mInputDevices.get(str);
            if (inputDeviceState == null) {
                touchCalibration = TouchCalibration.IDENTITY;
            } else {
                try {
                    touchCalibration = inputDeviceState.mTouchCalibration[i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    Slog.w("InputManager", "Cannot get touch calibration.", e);
                    touchCalibration = null;
                }
                if (touchCalibration == null) {
                    touchCalibration = TouchCalibration.IDENTITY;
                }
            }
        }
        return touchCalibration;
    }

    public final int getTouchSensitivity() {
        return Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "auto_adjust_touch", 0, -2);
    }

    public final int getTouchSensitivityNotiCount() {
        String str = SystemProperties.get("persist.service.touchsensitivity.noticount", "null");
        if (str.equals("null")) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    public final String getVelocityTrackerStrategy() {
        return this.mVelocityTrackerStrategy;
    }

    public final int[] getVibratorIds(int i) {
        return this.mNative.getVibratorIds(i);
    }

    public final VibratorToken getVibratorToken(int i, IBinder iBinder) {
        VibratorToken vibratorToken;
        synchronized (this.mVibratorLock) {
            try {
                vibratorToken = (VibratorToken) ((ArrayMap) this.mVibratorTokens).get(iBinder);
                if (vibratorToken == null) {
                    int i2 = this.mNextVibratorTokenValue;
                    this.mNextVibratorTokenValue = i2 + 1;
                    vibratorToken = new VibratorToken(i, iBinder, i2);
                    try {
                        iBinder.linkToDeath(vibratorToken, 0);
                        ((ArrayMap) this.mVibratorTokens).put(iBinder, vibratorToken);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vibratorToken;
    }

    public final int getVirtualKeyQuietTimeMillis() {
        return this.mContext.getResources().getInteger(R.integer.kg_glowpad_rotation_offset);
    }

    public final boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) {
        Objects.requireNonNull(iArr, "keyCodes must not be null");
        Objects.requireNonNull(zArr, "keyExists must not be null");
        if (zArr.length >= iArr.length) {
            return this.mNative.hasKeys(i, i2, iArr, zArr);
        }
        throw new IllegalArgumentException("keyExists must be at least as large as keyCodes");
    }

    public final void increaseTouchSensitivityNotiCount() {
        int touchSensitivityNotiCount = getTouchSensitivityNotiCount();
        if (touchSensitivityNotiCount > 2) {
            return;
        }
        int i = touchSensitivityNotiCount + 1;
        this.mShowingTouchSensitivityNotiCount = i;
        SystemProperties.set("persist.service.touchsensitivity.noticount", String.valueOf(i));
    }

    public final void initTspCmdForSpay() {
        if (SEP_FULL && "true".equals(SystemProperties.get("persist.service.tspcmd.spay"))) {
            setTspEnabled(InputManager.SemTspCommandType.SPAY.getvalue(), isSpayFullAppInstalled());
        }
    }

    public final boolean injectInputEvent(InputEvent inputEvent, int i) {
        return injectInputEventToTarget(inputEvent, i, -1);
    }

    public final boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) {
        if (!checkCallingPermission(
                "android.permission.INJECT_EVENTS", "injectInputEvent()", true)) {
            throw new SecurityException(
                    "Injecting input events requires the caller (or the source of the"
                        + " instrumentation, if any) to have the INJECT_EVENTS permission.");
        }
        Objects.requireNonNull(inputEvent, "event must not be null");
        if (i != 0 && i != 2 && i != 1) {
            throw new IllegalArgumentException("mode is invalid");
        }
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = i2 != -1;
        try {
            if (inputEvent instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) inputEvent;
                if (keyEvent.getKeyCode() == 4) {
                    if (!this.mDexImeWindowVisibleInDefaultDisplay) {
                        if (this.mBackKeyDownAdjusted) {}
                    }
                    inputEvent.setDisplayId(2);
                    this.mBackKeyDownAdjusted = keyEvent.getAction() == 0;
                }
            }
            int injectInputEvent =
                    this.mNative.injectInputEvent(
                            inputEvent, z, i2, i, callingPid, callingUid, 30000, 134217728);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (injectInputEvent == 0) {
                return true;
            }
            if (injectInputEvent == 1) {
                if (z) {
                    throw new IllegalArgumentException(
                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                    callingPid,
                                    i2,
                                    "Targeted input event injection from pid ",
                                    " was not directed at a window owned by uid ",
                                    "."));
                }
                throw new IllegalStateException(
                        "Injection should not result in TARGET_MISMATCH when it is not targeted"
                            + " into to a specific uid.");
            }
            if (injectInputEvent != 3) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        callingPid, "Input event injection from pid ", " failed.", "InputManager");
                return false;
            }
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    callingPid, "Input event injection from pid ", " timed out.", "InputManager");
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i) {
        return ((InputManagerCallback) this.mWindowManagerCallbacks)
                .interceptKeyBeforeDispatching(iBinder, keyEvent, i);
    }

    public final int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
        long j;
        synchronized (this.mFocusEventDebugViewLock) {
            FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
            if (focusEventDebugView != null) {
                focusEventDebugView.post(
                        new FocusEventDebugView$$ExternalSyntheticLambda1(
                                focusEventDebugView, KeyEvent.obtain(keyEvent)));
            }
        }
        if (InputRune.IFW_KEY_COUNTER) {
            int keyCode = keyEvent.getKeyCode();
            int action = keyEvent.getAction();
            if ((keyCode == 24
                            || keyCode == 25
                            || keyCode == 26
                            || keyCode == 1082
                            || keyCode == 187
                            || keyCode == 4
                            || keyCode == 3)
                    && action == 0
                    && keyEvent.getDevice() != null
                    && !keyEvent.getDevice().isExternal()
                    && keyEvent.getScanCode() != 0
                    && keyEvent.getDeviceId() != -1) {
                InputKeyCounter.HwKeyCount hwKeyCount = this.mInputKeyCounter.mCurrentKeyCount;
                synchronized (hwKeyCount.mKeyCountMap) {
                    try {
                        if (hwKeyCount.mKeyCountMap.containsKey(Integer.valueOf(keyCode))) {
                            int intValue =
                                    ((Integer)
                                                    hwKeyCount.mKeyCountMap.get(
                                                            Integer.valueOf(keyCode)))
                                            .intValue();
                            hwKeyCount.mKeyCountMap.remove(Integer.valueOf(keyCode));
                            int i2 = intValue + 1;
                            hwKeyCount.mKeyCountMap.put(
                                    Integer.valueOf(keyCode), Integer.valueOf(i2));
                            if (InputKeyCounter.DEBUG) {
                                Log.d(
                                        "InputKeyCounter",
                                        "Add keyCode: " + keyCode + ", currentCount= " + i2);
                            }
                        } else {
                            hwKeyCount.mKeyCountMap.put(Integer.valueOf(keyCode), 1);
                            if (InputKeyCounter.DEBUG) {
                                Log.d(
                                        "InputKeyCounter",
                                        "Add keyCode: " + keyCode + ", currentCount: 1");
                            }
                        }
                        hwKeyCount.mAllKeyCount++;
                    } finally {
                    }
                }
                InputKeyCounter.HwKeyCount hwKeyCount2 = this.mInputKeyCounter.mCurrentKeyCount;
                synchronized (hwKeyCount2.mKeyCountMap) {
                    j = hwKeyCount2.mAllKeyCount;
                }
                if (InputKeyCounter.DEBUG) {
                    Log.d("InputKeyCounter", "getAllKeyCount: " + j);
                }
                if (j > 500) {
                    this.mHandler.post(new KeyCountRunnable());
                }
            }
        }
        if (keyEvent.getKeyCode() == 143) {
            boolean z = keyEvent.getAction() == 0;
            boolean z2 = (16777216 & i) != 0;
            if (!z && !z2) {
                synchronized (this.mDataStore) {
                    try {
                        try {
                            boolean isNumLockOn = keyEvent.isNumLockOn();
                            Log.d("InputManager", "setNumLockState: " + isNumLockOn);
                            PersistentDataStore persistentDataStore = this.mDataStore;
                            persistentDataStore.mNumLockState = isNumLockOn;
                            persistentDataStore.mDirtyEtc = true;
                            persistentDataStore.saveIfNeededEtc();
                        } catch (Throwable th) {
                            this.mDataStore.saveIfNeededEtc();
                            throw th;
                        }
                    } finally {
                    }
                }
            }
        }
        return ((InputManagerCallback) this.mWindowManagerCallbacks)
                .interceptKeyBeforeQueueing(keyEvent, i);
    }

    public final int interceptMotionBeforeQueueingNonInteractive(
            int i, int i2, int i3, long j, int i4) {
        return ((InputManagerCallback) this.mWindowManagerCallbacks)
                .interceptMotionBeforeQueueingNonInteractive(i, i2, i3, j, i4);
    }

    public final boolean interceptQuickAccess(boolean z) {
        if (!this.mSystemReady) {
            Log.d("InputManager", "QuickAccess: system not ready");
            return false;
        }
        String scrubPosition =
                ((SemInputDeviceManager)
                                this.mContext.getSystemService("SemInputDeviceManagerService"))
                        .getScrubPosition(z ? 1 : 2);
        try {
            String[] split = scrubPosition.split(" ");
            if (split.length < 3) {
                Log.d("InputManager", "invalid format for QuickAccess: ".concat(scrubPosition));
                return false;
            }
            int parseInt = Integer.parseInt(split[0]);
            float parseFloat = Float.parseFloat(split[1]);
            float parseFloat2 = Float.parseFloat(split[2]);
            Log.d(
                    "InputManager",
                    "QuickAccess info: "
                            + parseInt
                            + ", ("
                            + parseFloat
                            + ", "
                            + parseFloat2
                            + ")");
            ((InputManagerCallback) this.mWindowManagerCallbacks)
                    .interceptQuickAccess(parseInt, parseFloat, parseFloat2);
            return false;
        } catch (NullPointerException unused) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "NPE on QuickAccess: ", scrubPosition, "InputManager");
            return false;
        } catch (NumberFormatException unused2) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "NFE on QuickAccess: ", scrubPosition, "InputManager");
            return false;
        }
    }

    public final boolean isDefaultPointerIconChanged() {
        return this.mDefaultPointerIconChanged;
    }

    public final int isInTabletMode() {
        if (checkCallingPermission("android.permission.TABLET_MODE", "isInTabletMode()", false)) {
            return this.mNative.getSwitchState(-1, -256, 1);
        }
        throw new SecurityException("Requires TABLET_MODE permission");
    }

    public final int isMicMuted() {
        return this.mNative.getSwitchState(-1, -256, 14);
    }

    public final boolean isPerDisplayTouchModeEnabled() {
        return this.mContext.getResources().getBoolean(R.bool.config_perDisplayFocusEnabled);
    }

    public final boolean isSpayFullAppInstalled() {
        try {
            if (this.mContext
                            .getPackageManager()
                            .getApplicationInfo("com.samsung.android.spay", 128)
                    != null) {
                return !r3.metaData.getBoolean("com.samsung.android.spay.is_stub", false);
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isUidTouched(int i) {
        return this.mNative.isUidTouched(i);
    }

    public final boolean isVibrating(int i) {
        return this.mNative.isVibrating(i);
    }

    public final /* synthetic */ void lambda$controlSpenWithToken$1() {
        Log.d("InputManager", "mSpenContolToken died");
        if (this.mSpenControlToken != null) {
            enableInputDevice(this.mSpenDeviceId);
            this.mSpenControlToken = null;
        }
    }

    public final /* synthetic */ void lambda$createSpyWindowGestureMonitor$0(
            InputChannel inputChannel) {
        removeSpyWindowGestureMonitor(inputChannel.getToken());
    }

    public final /* synthetic */ void lambda$sendKeyboardWirelessKeyboardShare$11() {
        this.mSharedKeyWakeLock.acquire(60000L);
    }

    public final /* synthetic */ void lambda$sendKeyboardWirelessKeyboardShare$12() {
        this.mSharedKeyWakeLock.release();
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mInputFilterLock) {
        }
        synchronized (this.mAssociationsLock) {
        }
        synchronized (this.mLidSwitchLock) {
        }
        synchronized (this.mInputMonitors) {
        }
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
        }
        this.mBatteryController.monitor();
        this.mPointerIconCache.monitor();
        this.mNative.monitor();
    }

    public final InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) {
        return monitorGestureInputFiltered(iBinder, str, i, GnssNative.GNSS_AIDING_TYPE_ALL);
    }

    public final InputMonitor monitorGestureInputFiltered(
            IBinder iBinder, String str, int i, int i2) {
        if (!checkCallingPermission(
                "android.permission.MONITOR_INPUT", "monitorGestureInput()", false)) {
            throw new SecurityException("Requires MONITOR_INPUT permission");
        }
        Objects.requireNonNull(str, "name must not be null.");
        Objects.requireNonNull(iBinder, "token must not be null.");
        if (i < 0) {
            throw new IllegalArgumentException("displayId must >= 0.");
        }
        String concat = "[Gesture Monitor] ".concat(str);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SurfaceControl createSurfaceForGestureMonitor =
                    ((InputManagerCallback) this.mWindowManagerCallbacks)
                            .createSurfaceForGestureMonitor(i, concat);
            if (createSurfaceForGestureMonitor != null) {
                InputChannel createSpyWindowGestureMonitor =
                        createSpyWindowGestureMonitor(
                                iBinder,
                                concat,
                                createSurfaceForGestureMonitor,
                                i,
                                callingPid,
                                callingUid,
                                i2);
                return new InputMonitor(
                        createSpyWindowGestureMonitor,
                        new InputMonitorHost(createSpyWindowGestureMonitor.getToken()),
                        new SurfaceControl(
                                createSurfaceForGestureMonitor, "IMS.monitorGestureInput"));
            }
            throw new IllegalArgumentException(
                    "Could not create gesture monitor surface on display: " + i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final InputChannel monitorInput(String str, int i) {
        return monitorInput(str, i, GnssNative.GNSS_AIDING_TYPE_ALL);
    }

    public final InputChannel monitorInput(String str, int i, int i2) {
        Objects.requireNonNull(str, "inputChannelName not be null");
        if (i >= 0) {
            return this.mNative.createInputMonitor(i, str, Binder.getCallingPid(), i2);
        }
        throw new IllegalArgumentException("displayId must >= 0.");
    }

    public final InputChannel monitorInputForBinder(String str, int i, int i2) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000
                || this.mContext.checkPermission(
                                "android.permission.MONITOR_INPUT", callingPid, callingUid)
                        == 0) {
            return monitorInput(str, i, i2);
        }
        throw new SecurityException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        callingUid, "can only call from system. "));
    }

    public final boolean needGamePadRemapping(String str) {
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        boolean z = false;
        if (gamePadRemapper == null) {
            return false;
        }
        if (str != null) {
            synchronized (gamePadRemapper.mDeviceToProfileLock) {
                int intValue =
                        ((Integer)
                                        ((HashMap) gamePadRemapper.mDeviceToProfile)
                                                .getOrDefault(str.toUpperCase(), -1))
                                .intValue();
                if (intValue != 0 && GamePadRemapper.isValidId(intValue)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final void notifyConfigurationChanged(long j) {
        InputManagerCallback inputManagerCallback =
                (InputManagerCallback) this.mWindowManagerCallbacks;
        WindowManagerGlobalLock windowManagerGlobalLock = inputManagerCallback.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                inputManagerCallback.mService.mRoot.forAllDisplays(
                        new InputManagerCallback$$ExternalSyntheticLambda0());
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (inputManagerCallback.mInputDevicesReadyMonitor) {
            try {
                if (!inputManagerCallback.mInputDevicesReady) {
                    inputManagerCallback.mInputDevicesReady = true;
                    inputManagerCallback.mInputDevicesReadyMonitor.notifyAll();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void notifyDisplayIdChangedByUser(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "notifyDisplayIdChangedByUser: ", "InputManager");
        if (this.mDisplayIdForPointerIcon != i) {
            this.mDisplayIdForPointerIcon = i;
        }
    }

    public final void notifyDropWindow(IBinder iBinder, float f, float f2) {
        WindowManagerService windowManagerService =
                ((InputManagerCallback) this.mWindowManagerCallbacks).mService;
        WindowManagerService.H h = windowManagerService.mH;
        final DragDropController dragDropController = windowManagerService.mDragDropController;
        Objects.requireNonNull(dragDropController);
        h.sendMessage(
                PooledLambda.obtainMessage(
                        new TriConsumer() { // from class:
                                            // com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda3
                            public final void accept(Object obj, Object obj2, Object obj3) {
                                DragDropController dragDropController2 = DragDropController.this;
                                IBinder iBinder2 = (IBinder) obj;
                                float floatValue = ((Float) obj2).floatValue();
                                float floatValue2 = ((Float) obj3).floatValue();
                                if (dragDropController2.mDragState == null) {
                                    Slog.w("WindowManager", "Drag state is closed.");
                                    return;
                                }
                                WindowManagerGlobalLock windowManagerGlobalLock =
                                        dragDropController2.mService.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        dragDropController2.mDragState.reportDropWindowLock(
                                                iBinder2, floatValue, floatValue2);
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        },
                        iBinder,
                        Float.valueOf(f),
                        Float.valueOf(f2)));
    }

    public final void notifyFocusChanged(IBinder iBinder, IBinder iBinder2) {
        final WindowManagerService windowManagerService =
                ((InputManagerCallback) this.mWindowManagerCallbacks).mService;
        windowManagerService.mH.sendMessage(
                PooledLambda.obtainMessage(
                        new BiConsumer() { // from class:
                            // com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                WindowManagerService windowManagerService2 =
                                        WindowManagerService.this;
                                IBinder iBinder3 = (IBinder) obj;
                                IBinder iBinder4 = (IBinder) obj2;
                                WindowManagerGlobalLock windowManagerGlobalLock =
                                        windowManagerService2.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        InputTarget inputTargetFromToken =
                                                windowManagerService2.getInputTargetFromToken(
                                                        iBinder3);
                                        InputTarget inputTargetFromToken2 =
                                                windowManagerService2.getInputTargetFromToken(
                                                        iBinder4);
                                        if (inputTargetFromToken2 == null
                                                && inputTargetFromToken == null) {
                                            Slog.v(
                                                    "WindowManager",
                                                    "Unknown focus tokens, dropping"
                                                        + " reportFocusChanged");
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            return;
                                        }
                                        windowManagerService2.mFocusedInputTarget =
                                                inputTargetFromToken2;
                                        windowManagerService2.mAccessibilityController
                                                .onFocusChanged(
                                                        inputTargetFromToken,
                                                        inputTargetFromToken2);
                                        if (ProtoLogImpl_54989576.Cache
                                                .WM_DEBUG_FOCUS_LIGHT_enabled[2]) {
                                            ProtoLogImpl_54989576.i(
                                                    ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                                                    -3428027271337724889L,
                                                    0,
                                                    null,
                                                    String.valueOf(inputTargetFromToken),
                                                    String.valueOf(inputTargetFromToken2));
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        WindowState windowState =
                                                inputTargetFromToken2 != null
                                                        ? inputTargetFromToken2.getWindowState()
                                                        : null;
                                        if (windowState != null
                                                && windowState.mInputChannelToken == iBinder4) {
                                            AnrController anrController =
                                                    windowManagerService2.mAnrController;
                                            WindowManagerGlobalLock windowManagerGlobalLock2 =
                                                    anrController.mService.mGlobalLock;
                                            WindowManagerService.boostPriorityForLockedSection();
                                            synchronized (windowManagerGlobalLock2) {
                                                try {
                                                    ActivityRecord activityRecord =
                                                            (ActivityRecord)
                                                                    anrController
                                                                            .mUnresponsiveAppByDisplay
                                                                            .get(
                                                                                    windowState
                                                                                            .getDisplayId());
                                                    if (activityRecord != null
                                                            && activityRecord
                                                                    == windowState
                                                                            .mActivityRecord) {
                                                        WindowManagerService
                                                                .resetPriorityAfterLockedSection();
                                                        anrController.mService.mAmInternal
                                                                .inputDispatchingResumed(
                                                                        activityRecord.getPid());
                                                        anrController.mUnresponsiveAppByDisplay
                                                                .remove(windowState.getDisplayId());
                                                    }
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                } finally {
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                }
                                            }
                                            windowState.reportFocusChangedSerialized(true);
                                            WindowManagerGlobalLock windowManagerGlobalLock3 =
                                                    windowManagerService2.mGlobalLock;
                                            WindowManagerService.boostPriorityForLockedSection();
                                            synchronized (windowManagerGlobalLock3) {
                                                try {
                                                    if (!windowManagerService2
                                                            .mWindowChangeListeners.isEmpty()) {
                                                        WindowManagerService.WindowChangeListener[]
                                                                windowChangeListenerArr =
                                                                        (WindowManagerService
                                                                                                .WindowChangeListener
                                                                                        [])
                                                                                windowManagerService2
                                                                                        .mWindowChangeListeners
                                                                                        .toArray(
                                                                                                new WindowManagerService
                                                                                                                .WindowChangeListener
                                                                                                        [windowManagerService2
                                                                                                                .mWindowChangeListeners
                                                                                                                .size()]);
                                                        WindowManagerService
                                                                .resetPriorityAfterLockedSection();
                                                        for (WindowManagerService
                                                                        .WindowChangeListener
                                                                windowChangeListener :
                                                                        windowChangeListenerArr) {
                                                            ViewServer.ViewServerWorker
                                                                    viewServerWorker =
                                                                            (ViewServer
                                                                                            .ViewServerWorker)
                                                                                    windowChangeListener;
                                                            synchronized (viewServerWorker) {
                                                                viewServerWorker
                                                                                .mNeedFocusedWindowUpdate =
                                                                        true;
                                                                viewServerWorker.notifyAll();
                                                            }
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                    throw th;
                                                }
                                            }
                                        }
                                        WindowState windowState2 =
                                                inputTargetFromToken != null
                                                        ? inputTargetFromToken.getWindowState()
                                                        : null;
                                        if (windowState2 == null
                                                || windowState2.mInputChannelToken != iBinder3) {
                                            return;
                                        }
                                        windowState2.reportFocusChangedSerialized(false);
                                    } finally {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                            }
                        },
                        iBinder,
                        iBinder2));
    }

    public final void notifyInputChannelBroken(IBinder iBinder) {
        synchronized (this.mInputMonitors) {
            try {
                if (((HashMap) this.mInputMonitors).containsKey(iBinder)) {
                    removeSpyWindowGestureMonitor(iBinder);
                }
            } finally {
            }
        }
        InputManagerCallback inputManagerCallback =
                (InputManagerCallback) this.mWindowManagerCallbacks;
        if (iBinder == null) {
            inputManagerCallback.getClass();
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = inputManagerCallback.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState =
                        (WindowState) inputManagerCallback.mService.mInputToWindowMap.get(iBinder);
                if (windowState != null) {
                    Slog.i("WindowManager", "WINDOW DIED " + windowState);
                    windowState.removeIfPossible();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void notifyInputDevicesChanged(InputDevice[] inputDeviceArr) {
        synchronized (this.mInputDevicesLock) {
            try {
                if (!this.mInputDevicesChangedPending) {
                    this.mInputDevicesChangedPending = true;
                    this.mHandler.obtainMessage(1, this.mInputDevices).sendToTarget();
                }
                this.mInputDevices = inputDeviceArr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int notifyMultiFingerGesture(int i, int i2) {
        int i3;
        if (i == 0) {
            i3 = this.mCurrentThreeTapBehavior;
        } else {
            i3 = 1;
            if (i == 1) {
                i3 = this.mCurrentFourTapBehavior;
            } else if (i == 2) {
                i3 = 4;
            } else if (i != 3) {
                i3 = 0;
            }
        }
        if (mMultiFingerGestureEnable) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i, i3, "notifyMultiFingerGesture: ", " ", "InputManager");
        } else {
            AudioService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Not support multi finger gesture "),
                    Build.VERSION.SEM_PLATFORM_INT,
                    " 0",
                    "InputManager");
            i3 = 0;
        }
        sendMultiFingerGesture(i3, i2);
        return i3 == 4 ? 4 : 0;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyNoFocusedWindowAnr(android.view.InputApplicationHandle r14) {
        /*
            Method dump skipped, instructions count: 352
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.input.InputManagerService.notifyNoFocusedWindowAnr(android.view.InputApplicationHandle):void");
    }

    public final void notifyPogoKeyboardNotMatch() {
        if (!this.mBootCompleted) {
            this.mNotifyPogoKeyboardNotMatchPending = true;
            return;
        }
        String string = this.mContext.getResources().getString(17043486);
        ToastDialog toastDialog = this.mToastDialog;
        toastDialog.getClass();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = string;
        ToastDialog.ToastDialogHandler toastDialogHandler = toastDialog.mHandler;
        toastDialogHandler.removeMessages(7);
        toastDialogHandler.obtainMessage(7, obtain).sendToTarget();
    }

    public final void notifyQuickAccess(int i, float f, float f2) {
        if (!this.mSystemReady) {
            Log.d("InputManager", "notifyQuickAccess: system not ready");
            return;
        }
        if (Binder.getCallingUid() == 1000) {
            Log.d("InputManager", "notifyQuickAccess: info=" + i + ", x=" + f + ", y=" + f2);
            ((InputManagerCallback) this.mWindowManagerCallbacks).interceptQuickAccess(i, f, f2);
        }
    }

    public final void notifySensorAccuracy(int i, int i2, int i3) {
        int size;
        int i4;
        ((ArrayList) this.mSensorAccuracyListenersToNotify).clear();
        synchronized (this.mSensorEventLock) {
            try {
                size = this.mSensorEventListeners.size();
                for (int i5 = 0; i5 < size; i5++) {
                    ((ArrayList) this.mSensorAccuracyListenersToNotify)
                            .add(
                                    (SensorEventListenerRecord)
                                            this.mSensorEventListeners.valueAt(i5));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i4 = 0; i4 < size; i4++) {
            SensorEventListenerRecord sensorEventListenerRecord =
                    (SensorEventListenerRecord)
                            ((ArrayList) this.mSensorAccuracyListenersToNotify).get(i4);
            sensorEventListenerRecord.getClass();
            try {
                sensorEventListenerRecord.mListener.onInputSensorAccuracyChanged(i, i2, i3);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + sensorEventListenerRecord.mPid
                                + " that sensor accuracy notified, assuming it died.",
                        e);
                sensorEventListenerRecord.binderDied();
            }
        }
        ((ArrayList) this.mSensorAccuracyListenersToNotify).clear();
    }

    public final void notifySensorEvent(int i, int i2, int i3, long j, float[] fArr) {
        int size;
        if (DEBUG) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "notifySensorEvent: deviceId=", " sensorType=", " values=");
            m.append(Arrays.toString(fArr));
            Slog.d("InputManager", m.toString());
        }
        ((ArrayList) this.mSensorEventListenersToNotify).clear();
        synchronized (this.mSensorEventLock) {
            try {
                size = this.mSensorEventListeners.size();
                for (int i4 = 0; i4 < size; i4++) {
                    ((ArrayList) this.mSensorEventListenersToNotify)
                            .add(
                                    (SensorEventListenerRecord)
                                            this.mSensorEventListeners.valueAt(i4));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            SensorEventListenerRecord sensorEventListenerRecord =
                    (SensorEventListenerRecord)
                            ((ArrayList) this.mSensorEventListenersToNotify).get(i5);
            sensorEventListenerRecord.getClass();
            try {
                sensorEventListenerRecord.mListener.onInputSensorChanged(i, i2, i3, j, fArr);
            } catch (RemoteException e) {
                Slog.w(
                        "InputManager",
                        "Failed to notify process "
                                + sensorEventListenerRecord.mPid
                                + " that sensor event notified, assuming it died.",
                        e);
                sensorEventListenerRecord.binderDied();
            }
        }
        ((ArrayList) this.mSensorEventListenersToNotify).clear();
    }

    public final void notifyStickyModifierStateChanged(int i, int i2) {
        StickyModifierStateController stickyModifierStateController =
                this.mStickyModifierStateController;
        if (StickyModifierStateController.DEBUG) {
            stickyModifierStateController.getClass();
            Slog.d(
                    "ModifierStateController",
                    "Sticky modifier state changed, modifierState = "
                            + i
                            + ", lockedModifierState = "
                            + i2);
        }
        synchronized (stickyModifierStateController.mStickyModifierStateListenerRecords) {
            for (int i3 = 0;
                    i3 < stickyModifierStateController.mStickyModifierStateListenerRecords.size();
                    i3++) {
                try {
                    StickyModifierStateController.StickyModifierStateListenerRecord
                            stickyModifierStateListenerRecord =
                                    (StickyModifierStateController
                                                    .StickyModifierStateListenerRecord)
                                            stickyModifierStateController
                                                    .mStickyModifierStateListenerRecords.valueAt(
                                                    i3);
                    stickyModifierStateListenerRecord.getClass();
                    try {
                        stickyModifierStateListenerRecord.mListener.onStickyModifierStateChanged(
                                i, i2);
                    } catch (RemoteException e) {
                        Slog.w(
                                "ModifierStateController",
                                "Failed to notify process "
                                        + stickyModifierStateListenerRecord.mPid
                                        + " that sticky modifier state changed, assuming it died.",
                                e);
                        stickyModifierStateListenerRecord.binderDied();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        ((InputManagerCallback) this.mWindowManagerCallbacks).notifyStickyModifierStateChanged(i);
    }

    public final void notifyStylusGestureStarted(int i, long j) {
        BatteryController batteryController = this.mBatteryController;
        synchronized (batteryController.mLock) {
            try {
                BatteryController.DeviceMonitor deviceMonitor =
                        (BatteryController.DeviceMonitor)
                                batteryController.mDeviceMonitors.get(Integer.valueOf(i));
                if (deviceMonitor == null) {
                    return;
                }
                deviceMonitor.onStylusGestureStarted(j);
            } finally {
            }
        }
    }

    public final void notifySwitch(long j, int i, int i2) {
        Slog.d(
                "InputManager",
                "notifySwitch: values="
                        + Integer.toHexString(i)
                        + ", mask="
                        + Integer.toHexString(i2));
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.argi3 = 0;
        obtain.argi4 = 0;
        this.mHandler.obtainMessage(107, obtain).sendToTarget();
        if ((i2 & 1) != 0) {
            boolean z = (i & 1) == 0;
            synchronized (this.mLidSwitchLock) {
                try {
                    if (this.mSystemReady) {
                        for (int i3 = 0; i3 < ((ArrayList) this.mLidSwitchCallbacks).size(); i3++) {
                            ((InputManagerInternal$LidSwitchCallback)
                                            ((ArrayList) this.mLidSwitchCallbacks).get(i3))
                                    .notifyLidSwitchChanged(z);
                        }
                    }
                } finally {
                }
            }
        }
        if ((8388608 & i2) != 0) {
            synchronized (this.mLidSwitchLock) {
                try {
                    if (this.mSystemReady) {
                        for (int i4 = 0; i4 < ((ArrayList) this.mLidSwitchCallbacks).size(); i4++) {
                            ((InputManagerInternal$LidSwitchCallback)
                                            ((ArrayList) this.mLidSwitchCallbacks).get(i4))
                                    .getClass();
                        }
                    }
                } finally {
                }
            }
        }
        SomeArgs obtain2 = SomeArgs.obtain();
        int i5 = (int) j;
        obtain2.argi1 = i5;
        int i6 = (int) (j >> 32);
        obtain2.argi2 = i6;
        obtain2.arg1 = Boolean.valueOf((i & 1) == 0);
        this.mHandler.obtainMessage(103, obtain2).sendToTarget();
        this.mLastLidEventTime = j;
        if ((i2 & 524288) != 0) {
            ((InputManagerCallback) this.mWindowManagerCallbacks)
                    .mService.mExt.mPolicyExt.notifyPenSwitchChanged(j, (524288 & i) == 0, false);
        }
        if ((i2 & 1048576) != 0) {
            ((InputManagerCallback) this.mWindowManagerCallbacks)
                    .mService.mExt.mPolicyExt.notifyPenSwitchChanged(j, (1048576 & i) == 0, true);
        }
        if ((i2 & 1073741824) != 0) {
            this.mSpenCoverAttached = (1073741824 & i) != 0;
            updateWacomMode();
        }
        if ((i2 & 512) != 0) {
            boolean z2 = (i & 512) != 0;
            ((InputManagerCallback) this.mWindowManagerCallbacks)
                    .notifyCameraLensCoverSwitchChanged(j, z2);
            setSensorPrivacy(2, z2);
        }
        if (this.mUseDevInputEventForAudioJack && (i2 & 212) != 0) {
            ((WiredAccessoryManager) this.mWiredAccessoryCallbacks)
                    .notifyWiredAccessoryChanged(i, i2);
        }
        if ((i2 & 2) != 0) {
            SomeArgs obtain3 = SomeArgs.obtain();
            obtain3.argi1 = i5;
            obtain3.argi2 = i6;
            obtain3.arg1 = Boolean.valueOf((i & 2) != 0);
            this.mHandler.obtainMessage(3, obtain3).sendToTarget();
        }
        if ((i2 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0) {
            boolean z3 =
                    (i & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0;
            ((AudioManager) this.mContext.getSystemService(AudioManager.class))
                    .setMicrophoneMuteFromSwitch(z3);
            setSensorPrivacy(1, z3);
        }
        if ((i2 & 2097152) != 0) {
            boolean z4 = (2097152 & i) == 0;
            if (this.mUnionManagerLocal == null) {
                this.mUnionManagerLocal =
                        (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
            }
            this.mUnionManagerLocal.notifyCoverSwitchStateChanged(j, z4);
        }
        if ((i2 & 134217728) != 0) {
            boolean z5 = (134217728 & i) != 0;
            if (this.mSecAccessoryManagerCallbacks != null) {
                Log.i(
                        "SAccessoryManager_SAccessoryManager",
                        "notifyUnverifiedCoverAttachChanged ignore whenNanos = "
                                + j
                                + ", attached = "
                                + z5);
            } else {
                Log.d("InputManager", "UnVerifiedCoverAttachCallbacks is not registered");
            }
            DesktopModeServiceCallbacks desktopModeServiceCallbacks =
                    this.mDesktopModeServiceCallbacks;
            if (desktopModeServiceCallbacks != null) {
                CoverStateManager.Authenticator.AnonymousClass1 anonymousClass1 =
                        (CoverStateManager.Authenticator.AnonymousClass1)
                                desktopModeServiceCallbacks;
                if (DesktopModeFeature.DEBUG) {
                    com.android.server.desktopmode.Log.d(
                            "[DMS]CoverStateManager",
                            "notifyUnverifiedCoverAttachChanged, attached=" + z5);
                }
                if (!z5) {
                    CoverStateManager.Authenticator authenticator =
                            CoverStateManager.Authenticator.this;
                    CoverStateManager.this.mHandler.removeCallbacksAndMessages(null);
                    authenticator.setAuthComplete();
                    CoverStateManager coverStateManager = CoverStateManager.this;
                    coverStateManager.mInputManagerService.mDesktopModeServiceCallbacks = null;
                    coverStateManager.mContext.unregisterReceiver(authenticator);
                }
            } else {
                Log.d(
                        "InputManager",
                        "UnVerifiedCoverAttachCallbacks is not registered for DesktopModeService");
            }
        }
        if ((i2 & Integer.MIN_VALUE) != 0) {
            boolean z6 = (Integer.MIN_VALUE & i) != 0;
            SamsungIMMSHWKeyboard.POGOKeyboardReceiver pOGOKeyboardReceiver =
                    SamsungIMMSHWKeyboard.POGOKeyboardReceiver.this;
            if (z6) {
                SamsungIMMSHWKeyboard.this.keyboardState |= 8;
            } else {
                SamsungIMMSHWKeyboard.this.keyboardState &= -9;
            }
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "notifyKeyboardCoverBackfolded: backfolded=", ", keyboardState=", z6),
                    SamsungIMMSHWKeyboard.this.keyboardState,
                    "InputMethodManagerServicePhysicalKey");
        }
        if ((i2 & 536870912) == 0 || (536870912 & i) == 0) {
            return;
        }
        showingTouchSensitivityNotificationIfNeeded();
    }

    public final void notifyUntrustedTouch(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
            if (applicationInfo != null) {
                str = packageManager.getApplicationLabel(applicationInfo).toString();
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        String string = this.mContext.getResources().getString(17043485, str);
        ToastDialog toastDialog = this.mToastDialog;
        toastDialog.getClass();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = string;
        ToastDialog.ToastDialogHandler toastDialogHandler = toastDialog.mHandler;
        toastDialogHandler.removeMessages(7);
        toastDialogHandler.obtainMessage(7, obtain).sendToTarget();
    }

    public final void notifyVibratorState(int i, boolean z) {
        if (DEBUG) {
            Slog.d("InputManager", "notifyVibratorState: deviceId=" + i + " isOn=" + z);
        }
        synchronized (this.mVibratorLock) {
            this.mIsVibrating.put(i, z);
            notifyVibratorStateListenersLocked(i);
        }
    }

    public final void notifyVibratorStateListenerLocked(
            int i, IVibratorStateListener iVibratorStateListener) {
        try {
            iVibratorStateListener.onVibrating(this.mIsVibrating.get(i));
        } catch (RemoteException | RuntimeException e) {
            Slog.e("InputManager", "Vibrator state listener failed to call", e);
        }
    }

    public final void notifyVibratorStateListenersLocked(int i) {
        if (!this.mVibratorStateListeners.contains(i)) {
            if (DEBUG) {
                Slog.v("InputManager", "Device " + i + " doesn't have vibrator state listener.");
                return;
            }
            return;
        }
        RemoteCallbackList remoteCallbackList =
                (RemoteCallbackList) this.mVibratorStateListeners.get(i);
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                notifyVibratorStateListenerLocked(
                        i, (IVibratorStateListener) remoteCallbackList.getBroadcastItem(i2));
            } finally {
                remoteCallbackList.finishBroadcast();
            }
        }
    }

    public final void notifyWindowResponsive(IBinder iBinder, int i, boolean z) {
        WindowManagerCallbacks windowManagerCallbacks = this.mWindowManagerCallbacks;
        OptionalInt of = z ? OptionalInt.of(i) : OptionalInt.empty();
        AnrController anrController =
                ((InputManagerCallback) windowManagerCallbacks).mService.mAnrController;
        WindowManagerGlobalLock windowManagerGlobalLock = anrController.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                InputTarget inputTargetFromToken =
                        anrController.mService.getInputTargetFromToken(iBinder);
                if (inputTargetFromToken != null) {
                    int pid = inputTargetFromToken.getPid();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    anrController.mService.mAmInternal.inputDispatchingResumed(pid);
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                if (of.isPresent()) {
                    anrController.mService.mAmInternal.inputDispatchingResumed(of.getAsInt());
                    return;
                }
                Slog.w(
                        "WindowManager",
                        "Failed to notify that window token=" + iBinder + " was responsive.");
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyWindowUnresponsive(IBinder iBinder, int i, boolean z, String str) {
        WindowManagerCallbacks windowManagerCallbacks = this.mWindowManagerCallbacks;
        OptionalInt of = z ? OptionalInt.of(i) : OptionalInt.empty();
        InputManagerCallback inputManagerCallback = (InputManagerCallback) windowManagerCallbacks;
        inputManagerCallback.getClass();
        TimeoutRecord forInputDispatchWindowUnresponsive =
                TimeoutRecord.forInputDispatchWindowUnresponsive(
                        InputManagerCallback.timeoutMessage(of, str));
        AnrController anrController = inputManagerCallback.mService.mAnrController;
        anrController.getClass();
        try {
            forInputDispatchWindowUnresponsive.mLatencyTracker.notifyWindowUnresponsiveStarted();
            if (!anrController.notifyWindowUnresponsive(
                    iBinder, forInputDispatchWindowUnresponsive)) {
                if (of.isPresent()) {
                    anrController.notifyWindowUnresponsive(
                            of.getAsInt(), forInputDispatchWindowUnresponsive);
                } else {
                    Slog.w(
                            "WindowManager",
                            "Failed to notify that window token=" + iBinder + " was unresponsive.");
                }
            }
            forInputDispatchWindowUnresponsive.mLatencyTracker.notifyWindowUnresponsiveEnded();
        } catch (Throwable th) {
            forInputDispatchWindowUnresponsive.mLatencyTracker.notifyWindowUnresponsiveEnded();
            throw th;
        }
    }

    public final void onDisplayRemoved(int i) {
        updateAdditionalDisplayInputProperties(
                i, new InputManagerService$$ExternalSyntheticLambda3());
        this.mNative.displayRemoved(i);
    }

    public final void onInputDevicesChangedListenerDied(int i) {
        synchronized (this.mInputDevicesLock) {
            this.mInputDevicesChangedListeners.remove(i);
        }
    }

    public final void onLidStateChangedListenerDied(int i) {
        synchronized (this.mLidStateLock) {
            this.mLidStateChangedListeners.remove(i);
        }
    }

    public final void onMultiFingerGestureListenerDied(int i) {
        synchronized (this.mMultiFingerGestureLock) {
            this.mMultiFingerGestureListeners.remove(i);
        }
    }

    public final void onPointerDownOutsideFocus(IBinder iBinder) {
        ((InputManagerCallback) this.mWindowManagerCallbacks)
                .mService
                .mH
                .obtainMessage(62, iBinder)
                .sendToTarget();
    }

    public final void onPointerDownUpCancelOutsideFocus(IBinder iBinder, int i, int i2, int i3) {
        InputManagerCallback inputManagerCallback =
                (InputManagerCallback) this.mWindowManagerCallbacks;
        inputManagerCallback.getClass();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = iBinder;
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.argi3 = i3;
        inputManagerCallback.mService.mH.obtainMessage(62, obtain).sendToTarget();
    }

    public final void onPointerIconChangedListenerDied(int i) {
        synchronized (this.mPointerIconLock) {
            this.mPointerIconChangedListeners.remove(i);
        }
    }

    public final void onSensorEventListenerDied(int i) {
        synchronized (this.mSensorEventLock) {
            this.mSensorEventListeners.remove(i);
        }
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new InputShellCommand(new InputShellCommand$$ExternalSyntheticLambda0())
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void onSwitchEventChangedListenerDied(int i) {
        synchronized (this.mSwitchEventChangedLock) {
            this.mSwitchEventChangedListeners.remove(i);
        }
    }

    public final void onTabletModeChangedListenerDied(int i) {
        synchronized (this.mTabletModeLock) {
            this.mTabletModeChangedListeners.remove(i);
        }
    }

    public final void onVibratorTokenDied(VibratorToken vibratorToken) {
        synchronized (this.mVibratorLock) {
            ((ArrayMap) this.mVibratorTokens).remove(vibratorToken.mToken);
        }
        cancelVibrateIfNeeded(vibratorToken);
    }

    public final void onWirelessKeyboardShareChangedListenerDied(int i) {
        synchronized (this.mWirelessKeyboardShareLock) {
            this.mWirelessKeyboardShareChangedListeners.remove(i);
        }
    }

    public final void openLightSession(int i, String str, IBinder iBinder) {
        Objects.requireNonNull(iBinder);
        synchronized (this.mLightLock) {
            Preconditions.checkState(
                    this.mLightSessions.get(iBinder) == null, "already registered");
            LightSession lightSession = new LightSession(i, str, iBinder);
            try {
                iBinder.linkToDeath(lightSession, 0);
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
            this.mLightSessions.put(iBinder, lightSession);
            if (DEBUG) {
                Slog.d("InputManager", "Open light session for " + str + " device " + i);
            }
        }
    }

    public final void pilferPointers(IBinder iBinder) {
        pilferPointers_enforcePermission();
        Objects.requireNonNull(iBinder);
        this.mNative.pilferPointers(iBinder);
    }

    public final void registerBatteryListener(
            int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) {
        Objects.requireNonNull(iInputDeviceBatteryListener);
        BatteryController batteryController = this.mBatteryController;
        int callingPid = Binder.getCallingPid();
        synchronized (batteryController.mLock) {
            try {
                BatteryController.ListenerRecord listenerRecord =
                        (BatteryController.ListenerRecord)
                                batteryController.mListenerRecords.get(Integer.valueOf(callingPid));
                if (listenerRecord == null) {
                    listenerRecord =
                            batteryController
                            .new ListenerRecord(callingPid, iInputDeviceBatteryListener);
                    try {
                        iInputDeviceBatteryListener
                                .asBinder()
                                .linkToDeath(listenerRecord.mDeathRecipient, 0);
                        batteryController.mListenerRecords.put(
                                Integer.valueOf(callingPid), listenerRecord);
                        if (BatteryController.DEBUG) {
                            Slog.d(
                                    "BatteryController",
                                    "Battery listener added for pid " + callingPid);
                        }
                    } catch (RemoteException unused) {
                        Slog.i(
                                "BatteryController",
                                "Client died before battery listener could be registered.");
                        return;
                    }
                }
                if (listenerRecord.mListener.asBinder() != iInputDeviceBatteryListener.asBinder()) {
                    throw new SecurityException(
                            "Cannot register a new battery listener when there is already another"
                                + " registered listener for pid "
                                    + callingPid);
                }
                if (!((ArraySet) listenerRecord.mMonitoredDevices).add(Integer.valueOf(i))) {
                    throw new IllegalArgumentException(
                            "The battery listener for pid "
                                    + callingPid
                                    + " is already monitoring deviceId "
                                    + i);
                }
                BatteryController.DeviceMonitor deviceMonitor =
                        (BatteryController.DeviceMonitor)
                                batteryController.mDeviceMonitors.get(Integer.valueOf(i));
                if (deviceMonitor == null) {
                    deviceMonitor = batteryController.new DeviceMonitor(i);
                    batteryController.mDeviceMonitors.put(Integer.valueOf(i), deviceMonitor);
                    batteryController.updateBluetoothBatteryMonitoring();
                }
                if (BatteryController.DEBUG) {
                    Slog.d(
                            "BatteryController",
                            "Battery listener for pid "
                                    + callingPid
                                    + " is monitoring deviceId "
                                    + i);
                }
                batteryController.updatePollingLocked(true);
                BatteryController.notifyBatteryListener(
                        listenerRecord, deviceMonitor.getBatteryStateForReporting());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerDesktopModeStateChangedListener() {
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        if (semDesktopModeManager == null) {
            return;
        }
        semDesktopModeManager.registerListener(
                new SemDesktopModeManager
                        .DesktopModeListener() { // from class:
                                                 // com.android.server.input.InputManagerService.13
                    public final void onDesktopModeStateChanged(
                            SemDesktopModeState semDesktopModeState) {
                        int i = semDesktopModeState.state;
                        if ((i != 40 || semDesktopModeState.enabled != 4)
                                && (i != 20 || semDesktopModeState.enabled != 1)) {
                            if (i == 50) {
                                GestureWakeup$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("STATE_CONFIG_CHANGE_FINISHED = "),
                                        semDesktopModeState.enabled,
                                        "InputManager");
                                int i2 = semDesktopModeState.enabled;
                                PointerIcon.setDexMode((i2 == 3) | (i2 == 4));
                                PointerIcon.clearSystemIcons();
                                InputManagerService.this.mNative.reloadPointerIcons();
                                return;
                            }
                            return;
                        }
                        boolean z = semDesktopModeState.enabled == 4;
                        int touchpadSupportedFeatures =
                                ((DesktopModeManagerInternal)
                                                LocalServices.getService(
                                                        DesktopModeManagerInternal.class))
                                        .getTouchpadSupportedFeatures();
                        int displayType = semDesktopModeState.getDisplayType();
                        if (Settings.System.getIntForUser(
                                        InputManagerService.this.mContext.getContentResolver(),
                                        "dexonpc_connection_state",
                                        0,
                                        -2)
                                == 3) {
                            displayType = 2001;
                        }
                        InputManagerService.this.mNative.setDexMode(
                                z, displayType, touchpadSupportedFeatures);
                        StringBuilder sb = new StringBuilder("set dexmode ");
                        sb.append(z);
                        sb.append(" displayType ");
                        sb.append(displayType);
                        sb.append(" dexFeature ");
                        GestureWakeup$$ExternalSyntheticOutline0.m(
                                sb, touchpadSupportedFeatures, "InputManager");
                    }
                });
    }

    public final void registerFlowPointerDirectionSettingObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Uri.withAppendedPath(DEX_SETTINGS_URI, "flow_pointer_from_where_dex"),
                        true,
                        new AnonymousClass8(this, this.mHandler, 4));
    }

    public final void registerFlowPointerSettingObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Uri.withAppendedPath(DEX_SETTINGS_URI, "flow_pointer_is_on_dex"),
                        true,
                        new AnonymousClass8(this, this.mHandler, 0));
    }

    public final void registerInputDevicesChangedListener(
            IInputDevicesChangedListener iInputDevicesChangedListener) {
        Objects.requireNonNull(iInputDevicesChangedListener, "listener must not be null");
        synchronized (this.mInputDevicesLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mInputDevicesChangedListeners.get(callingPid) != null) {
                    throw new SecurityException(
                            "The calling process has already registered an"
                                + " InputDevicesChangedListener.");
                }
                InputDevicesChangedListenerRecord inputDevicesChangedListenerRecord =
                        new InputDevicesChangedListenerRecord(
                                callingPid, iInputDevicesChangedListener);
                try {
                    iInputDevicesChangedListener
                            .asBinder()
                            .linkToDeath(inputDevicesChangedListenerRecord, 0);
                    this.mInputDevicesChangedListeners.put(
                            callingPid, inputDevicesChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerKeyboardBacklightListener(
            IKeyboardBacklightListener iKeyboardBacklightListener) {
        registerKeyboardBacklightListener_enforcePermission();
        Objects.requireNonNull(iKeyboardBacklightListener);
        this.mKeyboardBacklightController.registerKeyboardBacklightListener(
                iKeyboardBacklightListener, Binder.getCallingPid());
    }

    public final void registerLidStateChangedListener(
            ISemLidStateChangedListener iSemLidStateChangedListener) {
        if (!checkCallingPermission(
                "com.samsung.android.permission.LID_STATE",
                "registerLidStateChangedListener()",
                false)) {
            throw new SecurityException("Requires LID_STATE permission");
        }
        if (iSemLidStateChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mLidStateLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mLidStateChangedListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " LidStateChangedListener.");
                }
                LidStateChangedListenerRecord lidStateChangedListenerRecord =
                        new LidStateChangedListenerRecord(callingPid, iSemLidStateChangedListener);
                try {
                    iSemLidStateChangedListener
                            .asBinder()
                            .linkToDeath(lidStateChangedListenerRecord, 0);
                    this.mLidStateChangedListeners.put(callingPid, lidStateChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerLidSwitchCallbackInternal(
            InputManagerInternal$LidSwitchCallback inputManagerInternal$LidSwitchCallback) {
        synchronized (this.mLidSwitchLock) {
            try {
                ((ArrayList) this.mLidSwitchCallbacks).add(inputManagerInternal$LidSwitchCallback);
                if (this.mSystemReady) {
                    inputManagerInternal$LidSwitchCallback.notifyLidSwitchChanged(
                            this.mNative.getSwitchState(-1, -256, 0) == 0);
                    this.mNative.getSwitchState(-1, -256, 23);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerMultiFingerGestureListener(
            IMultiFingerGestureListener iMultiFingerGestureListener) {
        if (iMultiFingerGestureListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mMultiFingerGestureLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mMultiFingerGestureListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " MultiFingerGestureListener.");
                }
                MultiFingerGestureListenerRecord multiFingerGestureListenerRecord =
                        new MultiFingerGestureListenerRecord(
                                callingPid, iMultiFingerGestureListener);
                try {
                    iMultiFingerGestureListener
                            .asBinder()
                            .linkToDeath(multiFingerGestureListenerRecord, 0);
                    this.mMultiFingerGestureListeners.put(
                            callingPid, multiFingerGestureListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerMultiFingerTapBehavior(int i) {
        if (i == 4) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Uri.withAppendedPath(
                                    DEX_SETTINGS_URI, "touchpad_gestures_three_finger_tap"),
                            true,
                            new AnonymousClass8(this, this.mHandler, 1));
        } else if (i == 1) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Uri.withAppendedPath(
                                    DEX_SETTINGS_URI, "touchpad_gestures_four_finger_tap"),
                            true,
                            new AnonymousClass8(this, this.mHandler, 2));
        }
    }

    public final void registerPointerIconChangedListener(
            IPointerIconChangedListener iPointerIconChangedListener) {
        if (iPointerIconChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mPointerIconLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mPointerIconChangedListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " PointerIconChangedListener.");
                }
                PointerIconChangedListenerRecord pointerIconChangedListenerRecord =
                        new PointerIconChangedListenerRecord(
                                callingPid, iPointerIconChangedListener);
                try {
                    iPointerIconChangedListener
                            .asBinder()
                            .linkToDeath(pointerIconChangedListenerRecord, 0);
                    this.mPointerIconChangedListeners.put(
                            callingPid, pointerIconChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean registerSensorListener(
            IInputSensorEventListener iInputSensorEventListener) {
        if (DEBUG) {
            Slog.d(
                    "InputManager",
                    "registerSensorListener: listener="
                            + iInputSensorEventListener
                            + " callingPid="
                            + Binder.getCallingPid());
        }
        Objects.requireNonNull(iInputSensorEventListener, "listener must not be null");
        synchronized (this.mSensorEventLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mSensorEventListeners.get(callingPid) != null) {
                    Slog.e(
                            "InputManager",
                            "The calling process "
                                    + callingPid
                                    + " has already registered an InputSensorEventListener.");
                    return false;
                }
                SensorEventListenerRecord sensorEventListenerRecord =
                        new SensorEventListenerRecord(callingPid, iInputSensorEventListener);
                try {
                    iInputSensorEventListener.asBinder().linkToDeath(sensorEventListenerRecord, 0);
                    this.mSensorEventListeners.put(callingPid, sensorEventListenerRecord);
                    return true;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerSetPenModeOnDexObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Uri.withAppendedPath(DEX_SETTINGS_URI, "spen_mode"),
                        true,
                        new AnonymousClass8(this, this.mHandler, 3));
    }

    public final void registerStickyModifierStateListener(
            IStickyModifierStateListener iStickyModifierStateListener) {
        registerStickyModifierStateListener_enforcePermission();
        Objects.requireNonNull(iStickyModifierStateListener);
        StickyModifierStateController stickyModifierStateController =
                this.mStickyModifierStateController;
        int callingPid = Binder.getCallingPid();
        synchronized (stickyModifierStateController.mStickyModifierStateListenerRecords) {
            try {
                if (stickyModifierStateController.mStickyModifierStateListenerRecords.get(
                                callingPid)
                        != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " StickyModifierStateListener.");
                }
                StickyModifierStateController.StickyModifierStateListenerRecord
                        stickyModifierStateListenerRecord =
                                stickyModifierStateController
                                .new StickyModifierStateListenerRecord(
                                        callingPid, iStickyModifierStateListener);
                try {
                    iStickyModifierStateListener
                            .asBinder()
                            .linkToDeath(stickyModifierStateListenerRecord, 0);
                    stickyModifierStateController.mStickyModifierStateListenerRecords.put(
                            callingPid, stickyModifierStateListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerSwitchEventChangedListener(
            ISwitchEventChangedListener iSwitchEventChangedListener) {
        if (iSwitchEventChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mSwitchEventChangedLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mSwitchEventChangedListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " SwitchEventChangedListener.");
                }
                SwitchEventChangedListenerRecord switchEventChangedListenerRecord =
                        new SwitchEventChangedListenerRecord(
                                callingPid, iSwitchEventChangedListener);
                try {
                    iSwitchEventChangedListener
                            .asBinder()
                            .linkToDeath(switchEventChangedListenerRecord, 0);
                    this.mSwitchEventChangedListeners.put(
                            callingPid, switchEventChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerTabletModeChangedListener(
            ITabletModeChangedListener iTabletModeChangedListener) {
        if (!checkCallingPermission(
                "android.permission.TABLET_MODE", "registerTabletModeChangedListener()", false)) {
            throw new SecurityException("Requires TABLET_MODE_LISTENER permission");
        }
        Objects.requireNonNull(iTabletModeChangedListener, "event must not be null");
        synchronized (this.mTabletModeLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mTabletModeChangedListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " TabletModeChangedListener.");
                }
                TabletModeChangedListenerRecord tabletModeChangedListenerRecord =
                        new TabletModeChangedListenerRecord(callingPid, iTabletModeChangedListener);
                try {
                    iTabletModeChangedListener
                            .asBinder()
                            .linkToDeath(tabletModeChangedListenerRecord, 0);
                    this.mTabletModeChangedListeners.put(
                            callingPid, tabletModeChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean registerVibratorStateListener(
            int i, IVibratorStateListener iVibratorStateListener) {
        RemoteCallbackList remoteCallbackList;
        Objects.requireNonNull(iVibratorStateListener, "listener must not be null");
        synchronized (this.mVibratorLock) {
            try {
                if (this.mVibratorStateListeners.contains(i)) {
                    remoteCallbackList = (RemoteCallbackList) this.mVibratorStateListeners.get(i);
                } else {
                    remoteCallbackList = new RemoteCallbackList();
                    this.mVibratorStateListeners.put(i, remoteCallbackList);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (remoteCallbackList.register(iVibratorStateListener)) {
                        notifyVibratorStateListenerLocked(i, iVibratorStateListener);
                        return true;
                    }
                    Slog.e(
                            "InputManager",
                            "Could not register vibrator state listener " + iVibratorStateListener);
                    return false;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerWirelessKeyboardShareChangedListener(
            IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "only system signature can use"
                                + " registerWirelessKeyboardShareChangedListener(), but UID(",
                            ") has not system signature"));
        }
        if (iWirelessKeyboardShareChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mWirelessKeyboardShareChangedListeners.get(callingPid) != null) {
                    throw new IllegalStateException(
                            "The calling process has already registered a"
                                + " WirelessKeyboardShareChangedListener.");
                }
                WirelessKeyboardShareChangedListenerRecord
                        wirelessKeyboardShareChangedListenerRecord =
                                new WirelessKeyboardShareChangedListenerRecord(
                                        callingPid, iWirelessKeyboardShareChangedListener);
                try {
                    iWirelessKeyboardShareChangedListener
                            .asBinder()
                            .linkToDeath(wirelessKeyboardShareChangedListenerRecord, 0);
                    this.mWirelessKeyboardShareChangedListeners.put(
                            callingPid, wirelessKeyboardShareChangedListenerRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reloadDeviceAliases() {
        if (DEBUG) {
            Slog.d("InputManager", "Reloading device names.");
        }
        this.mNative.reloadDeviceAliases();
    }

    public final void reloadPointerIcons() {
        Log.d("InputManager", "reloadPointerIcons");
        this.mNative.reloadPointerIcons();
    }

    public final void remapModifierKey(int i, int i2) {
        remapModifierKey_enforcePermission();
        KeyRemapper keyRemapper = this.mKeyRemapper;
        if (FeatureFlagUtils.isEnabled(
                keyRemapper.mContext, "settings_new_keyboard_modifier_key")) {
            keyRemapper.mHandler.sendMessage(Message.obtain(keyRemapper.mHandler, 2, i, i2));
        }
        if (i == 111) {
            SystemProperties.set("persist.service.esc.dialog", "1");
        }
    }

    public final void removeAllDeviceToGamepadProfile() {
        if (this.mGamePadRemapper != null
                && checkSystemSignature("removeAllDeviceToGamepadProfile")) {
            Log.d("InputManager", "removeAllDeviceToGamepadProfile");
            GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
            synchronized (gamePadRemapper.mDeviceToProfileLock) {
                ((HashMap) gamePadRemapper.mDeviceToProfile).clear();
            }
            Log.d("InputManager-GamePadRemapper", "removeAllDeviceToGamepadProfile ");
        }
    }

    public final void removeAllGamepadProfiles() {
        if (this.mGamePadRemapper != null && checkSystemSignature("removeAllGamepadProfiles")) {
            Log.d("InputManager", "removeAllGamepadProfiles");
            GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
            synchronized (gamePadRemapper.mDataStore) {
                PersistentDataStore persistentDataStore = gamePadRemapper.mDataStore;
                persistentDataStore.clearStateGamePadProfiles();
                persistentDataStore.mDirtyGamePadProfiles = true;
                persistentDataStore.saveIfNeededGamePadProfiles();
            }
            Log.d("InputManager-GamePadRemapper", "removeAllGamepadProfiles ");
        }
    }

    public final void removeDeviceToGamepadProfile(String str) {
        if (this.mGamePadRemapper != null && checkSystemSignature("removeDeviceToGamepadProfile")) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "removeDeviceToGamepadProfile :", str, "InputManager");
            GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
            if (str == null) {
                gamePadRemapper.getClass();
                Log.d(
                        "InputManager-GamePadRemapper",
                        "removeDeviceToGamepadProfile : bt addr is null");
                return;
            }
            synchronized (gamePadRemapper.mDeviceToProfileLock) {
                ((HashMap) gamePadRemapper.mDeviceToProfile).remove(str.toUpperCase());
            }
            Log.d(
                    "InputManager-GamePadRemapper",
                    "removeDeviceToGamepadProfile : " + str.toUpperCase());
        }
    }

    public final void removeDeviceWirelessKeyboardShare(String str, int i) {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
                    if (this.mContext
                                    .getPackageManager()
                                    .checkSignatures(1000, Binder.getCallingUid())
                            != 0) {
                        Slog.d(
                                "InputManager",
                                "removeDeviceWirelessKeyboardShare : called by not allowed app");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mWirelessKeyboardMouseShare.removeHIDDevice(i, str);
                        this.mNative.enableWirelessKeyboardShare(false);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                StringBuilder sb = new StringBuilder("removeDeviceWirelessKeyboardShare : ");
                if (str == null) {
                    str = null;
                }
                BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "InputManager");
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void removeGamepadProfile(int i) {
        if (this.mGamePadRemapper != null && checkSystemSignature("removeGamepadProfile")) {
            Log.d("InputManager", "removeGamepadProfile");
            GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
            synchronized (gamePadRemapper.mDataStore) {
                PersistentDataStore persistentDataStore = gamePadRemapper.mDataStore;
                ((PersistentDataStore.GamePadProfile)
                                ((HashMap) persistentDataStore.mGamePadProfiles)
                                        .get(Integer.valueOf(i)))
                        .clearData();
                persistentDataStore.mDirtyGamePadProfiles = true;
                persistentDataStore.saveIfNeededGamePadProfiles();
            }
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "removeGamepadProfile : ", "InputManager-GamePadRemapper");
        }
    }

    public final void removeInputChannel(IBinder iBinder) {
        Objects.requireNonNull(iBinder, "connectionToken must not be null");
        this.mNative.removeInputChannel(iBinder);
    }

    public final void removeKeyboardLayoutAssociation(String str) {
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mKeyboardLayoutAssociations).remove(str);
        }
        this.mNative.changeKeyboardLayoutAssociation();
    }

    public final void removePortAssociation(String str) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "removePortAssociation()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mRuntimeAssociations).remove(str);
        }
        this.mNative.notifyPortAssociationsChanged();
    }

    public final void removeSpyWindowGestureMonitor(IBinder iBinder) {
        GestureMonitorSpyWindow gestureMonitorSpyWindow;
        synchronized (this.mInputMonitors) {
            gestureMonitorSpyWindow =
                    (GestureMonitorSpyWindow) ((HashMap) this.mInputMonitors).remove(iBinder);
        }
        removeInputChannel(iBinder);
        if (gestureMonitorSpyWindow == null) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.hide(gestureMonitorSpyWindow.mInputSurface);
        transaction.remove(gestureMonitorSpyWindow.mInputSurface);
        transaction.apply();
        gestureMonitorSpyWindow.mClientChannel.dispose();
    }

    public final void removeUniqueIdAssociationByDescriptor(String str) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "removeUniqueIdAssociationByDescriptor()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mUniqueIdAssociationsByDescriptor).remove(str);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    public final void removeUniqueIdAssociationByPort(String str) {
        if (!checkCallingPermission(
                "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY",
                "removeUniqueIdAssociation()",
                false)) {
            throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mUniqueIdAssociationsByPort).remove(str);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    public final void requestPointerCapture(IBinder iBinder, boolean z) {
        Objects.requireNonNull(iBinder, "event must not be null");
        this.mNative.requestPointerCapture(iBinder, z);
    }

    public final long semGetMotionIdleTimeMillis(boolean z) {
        return this.mNative.getMotionIdleTimeMillis(z);
    }

    public final int sendKeyboardWirelessKeyboardShare(int i, int i2) {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                        this.mWirelessKeyboardMouseShare;
                if (wirelessKeyboardMouseShare != null) {
                    wirelessKeyboardMouseShare.notifyKeyboardAciton(i, i2);
                    if (i == 0) {
                        this.mSharedKeyReferenceCount++;
                        final int i3 = 0;
                        this.mHandler.post(
                                new Runnable(
                                        this) { // from class:
                                                // com.android.server.input.InputManagerService$$ExternalSyntheticLambda4
                                    public final /* synthetic */ InputManagerService f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i4 = i3;
                                        InputManagerService inputManagerService = this.f$0;
                                        switch (i4) {
                                            case 0:
                                                boolean z = InputManagerService.DEBUG;
                                                inputManagerService
                                                        .lambda$sendKeyboardWirelessKeyboardShare$11();
                                                break;
                                            default:
                                                boolean z2 = InputManagerService.DEBUG;
                                                inputManagerService
                                                        .lambda$sendKeyboardWirelessKeyboardShare$12();
                                                break;
                                        }
                                    }
                                });
                    } else {
                        int i4 = this.mSharedKeyReferenceCount;
                        if (i4 > 0) {
                            this.mSharedKeyReferenceCount = i4 - 1;
                        }
                        if (this.mSharedKeyReferenceCount == 0) {
                            final int i5 = 1;
                            this.mHandler.post(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.android.server.input.InputManagerService$$ExternalSyntheticLambda4
                                        public final /* synthetic */ InputManagerService f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i5;
                                            InputManagerService inputManagerService = this.f$0;
                                            switch (i42) {
                                                case 0:
                                                    boolean z = InputManagerService.DEBUG;
                                                    inputManagerService
                                                            .lambda$sendKeyboardWirelessKeyboardShare$11();
                                                    break;
                                                default:
                                                    boolean z2 = InputManagerService.DEBUG;
                                                    inputManagerService
                                                            .lambda$sendKeyboardWirelessKeyboardShare$12();
                                                    break;
                                            }
                                        }
                                    });
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public final void sendMultiFingerGesture(int i, int i2) {
        this.mHandler.obtainMessage(105, i, i2).sendToTarget();
    }

    public final void sendPogoKeyboardStatus(boolean z) {
        Intent intent = new Intent("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
        intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, z);
        intent.addFlags(16777216);
        this.mContext.sendStickyBroadcast(intent);
        WindowManagerService windowManagerService =
                ((InputManagerCallback) this.mWindowManagerCallbacks).mService;
        windowManagerService.mH.removeMessages(200);
        windowManagerService.mH.sendMessage(
                windowManagerService.mH.obtainMessage(200, z ? 1 : 0, 0));
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                        this.mWirelessKeyboardMouseShare;
                if (wirelessKeyboardMouseShare != null) {
                    wirelessKeyboardMouseShare.setPogoConnected(z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        updateWacomMode();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = 0;
        obtain.argi2 = 0;
        obtain.argi3 = z ? 1 : 0;
        obtain.argi4 = 1;
        this.mHandler.obtainMessage(107, obtain).sendToTarget();
    }

    public final void sendPointerIconChanged(int i, PointerIcon pointerIcon) {
        if (this.mLastPointerIconType == i && this.mLastPointerIcon == pointerIcon) {
            return;
        }
        this.mLastPointerIconType = i;
        this.mLastPointerIcon = pointerIcon;
        if (DEBUG) {
            Log.d("InputManager", "PointerIcon type changed: " + this.mLastPointerIconType);
            Log.d("InputManager", "PointerIcon changed: " + this.mLastPointerIcon);
        }
        this.mHandler.obtainMessage(104, i, 0, pointerIcon).sendToTarget();
    }

    public final int sendSwitchWirelessKeyboardShare(int i) {
        int i2;
        boolean z;
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                        this.mWirelessKeyboardMouseShare;
                i2 = 1;
                if (wirelessKeyboardMouseShare != null) {
                    z = wirelessKeyboardMouseShare.switchRemoteDeviceByKey(i == 1);
                    Slog.d("InputManager", "switch device by key");
                } else {
                    z = true;
                }
                if (z) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    SomeArgs obtain = SomeArgs.obtain();
                    obtain.argi1 = (int) uptimeMillis;
                    obtain.argi2 = (int) (uptimeMillis >> 32);
                    obtain.argi3 = 6;
                    obtain.arg1 = "android.policy:WirelessKeyboardShare";
                    this.mHandler.obtainMessage(106, obtain).sendToTarget();
                }
                if (!z) {
                    i2 = 2;
                }
            } finally {
            }
        }
        return i2;
    }

    public final int sendTouchPadGestureWirelessKeyboardShare(int i, float f, float f2, int i2) {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                WirelessKeyboardMouseShare wirelessKeyboardMouseShare =
                        this.mWirelessKeyboardMouseShare;
                if (wirelessKeyboardMouseShare != null) {
                    wirelessKeyboardMouseShare.notifyMouseAciton(i, f, f2, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public final void setAccessibilityBounceKeysThreshold(int i) {
        this.mNative.setAccessibilityBounceKeysThreshold(i);
    }

    public final void setAccessibilitySlowKeysThreshold(int i) {
        this.mNative.setAccessibilitySlowKeysThreshold(i);
    }

    public final void setAccessibilityStickyKeysEnabled(boolean z) {
        this.mNative.setAccessibilityStickyKeysEnabled(z);
    }

    public final synchronized void setBlockDeviceMode(boolean z, int i, boolean z2, String str) {
        if (str != null) {
            try {
                if (!str.equals("")) {
                    if ((i & 1) != 0) {
                        if (z) {
                            if (!this.mBlockTspCallerList.contains(str)) {
                                this.mBlockTspCallerList.add(str);
                            }
                        } else if (this.mBlockTspCallerList.contains(str)) {
                            this.mBlockTspCallerList.remove(str);
                        }
                    }
                    if ((i & 2) != 0) {
                        if (z) {
                            if (!this.mBlockTkeyCallerList.contains(str)) {
                                this.mBlockTkeyCallerList.add(str);
                            }
                        } else if (this.mBlockTkeyCallerList.contains(str)) {
                            this.mBlockTkeyCallerList.remove(str);
                        }
                    }
                    this.mBlockDeviceMode = 0;
                    if (this.mBlockTspCallerList.size() > 0) {
                        this.mBlockDeviceMode |= 1;
                    }
                    if (this.mBlockTkeyCallerList.size() > 0) {
                        this.mBlockDeviceMode |= 2;
                    }
                    Slog.d(
                            "InputManager",
                            "sBDM(): isSet="
                                    + z
                                    + " deviceType="
                                    + i
                                    + " isForce="
                                    + z2
                                    + " caller="
                                    + str
                                    + " blockMode="
                                    + this.mBlockDeviceMode);
                    this.mNative.setInputMetaData(1, this.mBlockDeviceMode);
                    return;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slog.d("InputManager", "sBDM(): caller must be specified!");
    }

    public final void setCoverVerify(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "setCoverVerify = ", "InputManager");
        this.mNative.setCoverVerify(i);
    }

    public final void setCustomPointerIcons(final int i, final float f) {
        final PointerIconCache pointerIconCache = this.mPointerIconCache;
        pointerIconCache.mUiThreadHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.input.PointerIconCache$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PointerIconCache pointerIconCache2 = PointerIconCache.this;
                        int i2 = i;
                        float f2 = f;
                        synchronized (pointerIconCache2.mLoadedPointerIconsByDisplayAndType) {
                            try {
                                if (pointerIconCache2.mPointerIconSizeScale == f2
                                        && pointerIconCache2.mPointerIconColor == i2) {
                                    return;
                                }
                                pointerIconCache2.mPointerIconSizeScale = f2;
                                pointerIconCache2.mPointerIconColor = i2;
                                pointerIconCache2.mLoadedPointerIconsByDisplayAndType.clear();
                                pointerIconCache2.mNative.reloadPointerIcons();
                            } finally {
                            }
                        }
                    }
                });
    }

    public final void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) {
        if (z) {
            setForcedDefaultPointerIconInternal(i, pointerIcon);
        } else {
            setDefaultPointerIconInternal(i, pointerIcon);
        }
    }

    public final void setDefaultPointerIconInternal(int i, PointerIcon pointerIcon) {
        if (pointerIcon != null) {
            this.mDefaultPointerIcon = pointerIcon;
            this.mDefaultPointerIconChanged = true;
            this.mToolTypeForDefaultPointerIcon = i;
        } else if (this.mForcedDefaultPointerIconChanged) {
            this.mDefaultPointerIcon = this.mForcedDefaultPointerIcon;
            this.mDefaultPointerIconChanged = true;
            this.mToolTypeForDefaultPointerIcon = this.mToolTypeForForcedDefaultPointerIcon;
        } else {
            this.mDefaultPointerIcon = null;
            this.mDefaultPointerIconChanged = false;
            this.mToolTypeForDefaultPointerIcon = 0;
        }
    }

    public final void setDesktopModeServiceCallbacks(
            DesktopModeServiceCallbacks desktopModeServiceCallbacks) {
        this.mDesktopModeServiceCallbacks = desktopModeServiceCallbacks;
    }

    public final void setDexImePolicy(boolean z) {
        this.mDexImeWindowVisibleInDefaultDisplay = z;
    }

    public final void setDisplayDpi() {
        if (SEP_FULL) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.mContext.getSystemService("window"))
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
            this.mNative.setDisplayDpi(displayMetrics.xdpi, displayMetrics.ydpi);
        }
    }

    public final void setDisplayEligibilityForPointerCapture(int i, boolean z) {
        this.mNative.setDisplayEligibilityForPointerCapture(i, z);
    }

    public final void setDisplayIdForPointerIcon(int i) {
        if (this.mDisplayIdForPointerIcon != i) {
            PointerIcon.clearSystemIcons();
            this.mDisplayIdForPointerIcon = i;
        }
    }

    public final void setDisplayViewportsInternal(List list) {
        setDisplayDpi();
        DisplayViewport[] displayViewportArr = new DisplayViewport[list.size()];
        for (int size = list.size() - 1; size >= 0; size--) {
            displayViewportArr[size] = (DisplayViewport) list.get(size);
        }
        this.mNative.setDisplayViewports(displayViewportArr);
        this.mNative.setPointerDisplayId(
                ((InputManagerCallback) this.mWindowManagerCallbacks).getPointerDisplayId());
    }

    public final void setFocusedApplication(int i, InputApplicationHandle inputApplicationHandle) {
        this.mNative.setFocusedApplication(i, inputApplicationHandle);
    }

    public final void setFocusedDisplay(int i) {
        this.mNative.setFocusedDisplay(i);
    }

    public final void setForcedDefaultPointerIconInternal(int i, PointerIcon pointerIcon) {
        boolean z =
                this.mForcedDefaultPointerIcon == this.mDefaultPointerIcon
                        && this.mToolTypeForForcedDefaultPointerIcon
                                == this.mToolTypeForDefaultPointerIcon;
        this.mForcedDefaultPointerIcon = pointerIcon;
        if (pointerIcon != null) {
            this.mForcedDefaultPointerIconChanged = true;
            this.mToolTypeForForcedDefaultPointerIcon = i;
        } else {
            this.mForcedDefaultPointerIconChanged = false;
            this.mToolTypeForForcedDefaultPointerIcon = 0;
        }
        if (z) {
            setDefaultPointerIconInternal(i, pointerIcon);
        }
    }

    public final boolean setGamepadProfileName(int i, String str) {
        boolean z = false;
        if (this.mGamePadRemapper == null || !checkSystemSignature("setGamepadProfileName")) {
            return false;
        }
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        gamePadRemapper.getClass();
        if (GamePadRemapper.isValidId(i)) {
            synchronized (gamePadRemapper.mDataStore) {
                PersistentDataStore persistentDataStore = gamePadRemapper.mDataStore;
                PersistentDataStore.GamePadProfile gamePadProfile =
                        (PersistentDataStore.GamePadProfile)
                                ((HashMap) persistentDataStore.mGamePadProfiles)
                                        .get(Integer.valueOf(i));
                gamePadProfile.mName = str;
                gamePadProfile.mUsed = true;
                persistentDataStore.mDirtyGamePadProfiles = true;
                persistentDataStore.saveIfNeededGamePadProfiles();
            }
            z = true;
        }
        Log.d("InputManager-GamePadRemapper", "setGamepadProfileName : " + z + " " + str + " " + i);
        Log.d("InputManager", "setGamepadProfileName " + z + " : " + i + " " + str);
        return z;
    }

    public final void setHostRoleWirelessKeyboardShare() {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
                    if (this.mContext
                                    .getPackageManager()
                                    .checkSignatures(1000, Binder.getCallingUid())
                            != 0) {
                        Slog.d(
                                "InputManager",
                                "setHostRoleWirelessKeyboardShare : called by not allowed app");
                        return;
                    }
                    this.mWirelessKeyboardMouseShare.setHostRoleWirelessKeyboardShare();
                }
                Slog.d("InputManager", "setHostRoleWirelessKeyboardShare");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setInTouchMode(boolean z, int i, int i2, boolean z2, int i3) {
        return this.mNative.setInTouchMode(z, i, i2, z2, i3);
    }

    public final void setInputDispatchMode(boolean z, boolean z2) {
        this.mNative.setInputDispatchMode(z, z2);
    }

    public final void setInputFilter(IInputFilter iInputFilter) {
        synchronized (this.mInputFilterLock) {
            try {
                IInputFilter iInputFilter2 = this.mInputFilter;
                if (iInputFilter2 == iInputFilter) {
                    return;
                }
                boolean z = true;
                if (iInputFilter2 != null) {
                    this.mInputFilter = null;
                    this.mInputFilterHost.mDisconnected = true;
                    this.mInputFilterHost = null;
                    try {
                        iInputFilter2.uninstall();
                    } catch (RemoteException unused) {
                    }
                }
                if (iInputFilter != null) {
                    this.mInputFilter = iInputFilter;
                    InputFilterHost inputFilterHost = new InputFilterHost();
                    this.mInputFilterHost = inputFilterHost;
                    try {
                        iInputFilter.install(inputFilterHost);
                    } catch (RemoteException unused2) {
                    }
                }
                NativeInputManagerService.NativeImpl nativeImpl = this.mNative;
                if (iInputFilter == null) {
                    z = false;
                }
                nativeImpl.setInputFilterEnabled(z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setInputMethodManagerCallbacks(
            InputMethodManagerCallbacks inputMethodManagerCallbacks) {
        this.mInputMethodManagerCallbacks = inputMethodManagerCallbacks;
    }

    public final void setKeyboardLayoutForInputDevice(
            InputDeviceIdentifier inputDeviceIdentifier,
            int i,
            InputMethodInfo inputMethodInfo,
            InputMethodSubtype inputMethodSubtype,
            String str) {
        setKeyboardLayoutForInputDevice_enforcePermission();
        KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        keyboardLayoutManager.getClass();
        Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        InputDevice inputDevice = keyboardLayoutManager.getInputDevice(inputDeviceIdentifier);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier =
                new KeyboardLayoutManager.KeyboardIdentifier(inputDevice);
        KeyboardLayoutManager.ImeInfo imeInfo =
                new KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype);
        Objects.requireNonNull(imeInfo.mImeSubtypeHandle, "subtypeHandle must not be null");
        String str2 =
                "layoutDescriptor:"
                        + keyboardIdentifier
                        + ",userId:"
                        + i
                        + ",subtypeHandle:"
                        + imeInfo.mImeSubtypeHandle.toStringHandle();
        synchronized (keyboardLayoutManager.mDataStore) {
            try {
                try {
                    PersistentDataStore persistentDataStore = keyboardLayoutManager.mDataStore;
                    if (!Objects.equals(
                            ((ArrayMap)
                                            persistentDataStore.getOrCreateInputDeviceState(
                                                            keyboardIdentifier.toString())
                                                    .mKeyboardLayoutMap)
                                    .put(str2, str),
                            str)) {
                        persistentDataStore.mDirty = true;
                        Slog.d(
                                "KeyboardLayoutManager",
                                "setKeyboardLayoutForInputDevice() "
                                        + inputDeviceIdentifier
                                        + " key: "
                                        + str2
                                        + " keyboardLayoutDescriptor: "
                                        + str);
                        keyboardLayoutManager.mHandler.sendEmptyMessage(2);
                    }
                } finally {
                    keyboardLayoutManager.mDataStore.saveIfNeeded();
                }
            } finally {
            }
        }
    }

    public final void setLightStateInternal(int i, Light light, LightState lightState) {
        Objects.requireNonNull(light, "light does not exist");
        if (DEBUG) {
            Slog.d(
                    "InputManager",
                    "setLightStateInternal device "
                            + i
                            + " light "
                            + light
                            + "lightState "
                            + lightState);
        }
        if (light.getType() == 10002) {
            this.mNative.setLightPlayerId(i, light.getId(), lightState.getPlayerId());
        } else {
            this.mNative.setLightColor(i, light.getId(), lightState.getColor());
        }
    }

    public final void setLightStates(
            int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) {
        Preconditions.checkArgument(
                iArr.length == lightStateArr.length, "lights and light states are not same length");
        synchronized (this.mLightLock) {
            try {
                LightSession lightSession = (LightSession) this.mLightSessions.get(iBinder);
                Preconditions.checkArgument(lightSession != null, "not registered");
                Preconditions.checkState(lightSession.mDeviceId == i, "Incorrect device ID");
                lightSession.mLightIds = (int[]) iArr.clone();
                lightSession.mLightStates = (LightState[]) lightStateArr.clone();
                if (DEBUG) {
                    Slog.d(
                            "InputManager",
                            "setLightStates for " + lightSession.mOpPkg + " device " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        setLightStatesInternal(i, iArr, lightStateArr);
    }

    public final void setLightStatesInternal(int i, int[] iArr, LightState[] lightStateArr) {
        List lights = this.mNative.getLights(i);
        SparseArray sparseArray = new SparseArray();
        for (int i2 = 0; i2 < lights.size(); i2++) {
            sparseArray.put(((Light) lights.get(i2)).getId(), (Light) lights.get(i2));
        }
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (sparseArray.contains(iArr[i3])) {
                setLightStateInternal(i, (Light) sparseArray.get(iArr[i3]), lightStateArr[i3]);
            }
        }
    }

    public final void setMousePointerAccelerationEnabled(boolean z, int i) {
        updateAdditionalDisplayInputProperties(
                i, new InputManagerService$$ExternalSyntheticLambda1(1, z));
    }

    public final void setPointerFillStyle(final int i) {
        final PointerIconCache pointerIconCache = this.mPointerIconCache;
        pointerIconCache.mUiThreadHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.input.PointerIconCache$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PointerIconCache pointerIconCache2 = PointerIconCache.this;
                        int i2 = i;
                        synchronized (pointerIconCache2.mLoadedPointerIconsByDisplayAndType) {
                            try {
                                if (pointerIconCache2.mPointerIconFillStyle == i2) {
                                    return;
                                }
                                pointerIconCache2.mPointerIconFillStyle = i2;
                                pointerIconCache2.mLoadedPointerIconsByDisplayAndType.clear();
                                pointerIconCache2.mNative.reloadPointerIcons();
                            } finally {
                            }
                        }
                    }
                });
    }

    public final boolean setPointerIcon(
            PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) {
        Objects.requireNonNull(pointerIcon);
        if (!this.mNative.setPointerIcon(pointerIcon, i, i2, i3, iBinder)) {
            return false;
        }
        sendPointerIconChanged(pointerIcon.getType(), pointerIcon);
        return true;
    }

    public final void setPointerIconVisible(boolean z, int i) {
        updateAdditionalDisplayInputProperties(
                i, new InputManagerService$$ExternalSyntheticLambda1(0, z));
    }

    public final void setPointerScale(final float f) {
        final PointerIconCache pointerIconCache = this.mPointerIconCache;
        pointerIconCache.mUiThreadHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.input.PointerIconCache$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PointerIconCache pointerIconCache2 = PointerIconCache.this;
                        float f2 = f;
                        synchronized (pointerIconCache2.mLoadedPointerIconsByDisplayAndType) {
                            try {
                                if (pointerIconCache2.mPointerIconScale == f2) {
                                    return;
                                }
                                pointerIconCache2.mPointerIconScale = f2;
                                pointerIconCache2.mLoadedPointerIconsByDisplayAndType.clear();
                                pointerIconCache2.mNative.reloadPointerIcons();
                            } finally {
                            }
                        }
                    }
                });
    }

    public final void setPointerSpeedUnchecked(int i) {
        this.mNative.setPointerSpeed(Math.min(Math.max(i, -7), 7));
    }

    public final boolean setRemapGamepadButton(int i, int i2, int i3) {
        if (this.mGamePadRemapper == null || !checkSystemSignature("setRemapGamepadButton")) {
            return false;
        }
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        gamePadRemapper.getClass();
        boolean isValidId = GamePadRemapper.isValidId(i);
        if (i == 0) {
            isValidId = false;
        }
        if (!GamePadRemapper.isValidButton(i2)) {
            isValidId = false;
        }
        boolean z = GamePadRemapper.isValidButton(i3) ? isValidId : false;
        if (z) {
            synchronized (gamePadRemapper.mDataStore) {
                PersistentDataStore persistentDataStore = gamePadRemapper.mDataStore;
                PersistentDataStore.GamePadProfile gamePadProfile =
                        (PersistentDataStore.GamePadProfile)
                                ((HashMap) persistentDataStore.mGamePadProfiles)
                                        .get(Integer.valueOf(i));
                if (((Integer)
                                        gamePadProfile.mSimpeButtonMap.getOrDefault(
                                                Integer.valueOf(i2), Integer.valueOf(i2)))
                                .intValue()
                        != i3) {
                    ((ArrayMap) gamePadProfile.mSimpeButtonMap)
                            .put(Integer.valueOf(i2), Integer.valueOf(i3));
                    gamePadProfile.mUsed = true;
                    persistentDataStore.mDirtyGamePadProfiles = true;
                    persistentDataStore.saveIfNeededGamePadProfiles();
                }
            }
            z = true;
        }
        StringBuilder m =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i, "setRemapGamepadButton : ", " ", " ", z);
        m.append(i2);
        m.append(" ");
        m.append(i3);
        Log.d("InputManager-GamePadRemapper", m.toString());
        StringBuilder m2 =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i, "setRemapGamepadButton ", " : ", " ", z);
        m2.append(i2);
        m2.append(" ");
        m2.append(i3);
        Log.d("InputManager", m2.toString());
        return z;
    }

    public final boolean setRemapGamepadStick(
            int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        if (this.mGamePadRemapper == null || !checkSystemSignature("setRemapGamepadStick")) {
            return false;
        }
        GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
        gamePadRemapper.getClass();
        boolean isValidId = GamePadRemapper.isValidId(i);
        if (i == 0) {
            isValidId = false;
        }
        if (i2 != 2048 && i2 != 2059 && i2 != 2063) {
            isValidId = false;
        }
        boolean z4 = (i3 == 2048 || i3 == 2059 || i3 == 2063) ? isValidId : false;
        if (z4) {
            synchronized (gamePadRemapper.mDataStore) {
                gamePadRemapper.mDataStore.updateStickForGamePadProfiles(i, i2, i3, z, z2, z3);
            }
            z4 = true;
        }
        StringBuilder m =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i, "setRemapGamepadStick : ", " ", " ", z4);
        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, " ", " ", m);
        BatteryService$$ExternalSyntheticOutline0.m(m, z, " ", z2, " ");
        m.append(z3);
        Log.d("InputManager-GamePadRemapper", m.toString());
        StringBuilder sb = new StringBuilder("setRemapGamepadStick ");
        sb.append(z4);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i, i2, " : ", " ", sb);
        sb.append(" ");
        sb.append(i3);
        sb.append(" ");
        sb.append(z);
        sb.append(" ");
        sb.append(z2);
        sb.append(" ");
        sb.append(z3);
        Log.d("InputManager", sb.toString());
        return z4;
    }

    public final void setSecAccessoryManagerCallbacks(
            SecAccessoryManagerCallbacks secAccessoryManagerCallbacks) {
        this.mSecAccessoryManagerCallbacks = secAccessoryManagerCallbacks;
    }

    public final void setSensorPrivacy(int i, boolean z) {
        ((SensorPrivacyManagerInternal)
                        LocalServices.getService(SensorPrivacyManagerInternal.class))
                .setPhysicalToggleSensorPrivacy(-2, i, z);
    }

    public final void setShowAllTouches(boolean z) {
        this.mNative.showAllTouches(z);
        Slog.d("InputManager", "show all touches : " + z);
    }

    public final void setStartedShutdown(boolean z) {
        if (Binder.getCallingUid() == 1000) {
            this.mNative.updateInputMetaState(8, z);
            if (InputRune.IFW_KEY_COUNTER) {
                String str = "";
                for (Map.Entry entry :
                        this.mInputKeyCounter.mCurrentKeyCount.getKeyCountMap().entrySet()) {
                    str =
                            str.concat(Integer.toString(((Integer) entry.getKey()).intValue()))
                                    .concat(",")
                                    .concat(
                                            Integer.toString(
                                                    ((Integer) entry.getValue()).intValue()))
                                    .concat("/");
                }
                if (InputKeyCounter.DEBUG) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "saveCount - concat data :", str, "InputKeyCounter");
                }
                SystemProperties.set("persist.service.bgkeycount", str);
            }
        }
    }

    public final void setSystemUiLightsOut(boolean z) {
        this.mNative.setSystemUiLightsOut(z);
    }

    public final void setSystemUiLightsOut(boolean z, int i) {
        this.mNative.setSystemUiLightsOutForDisplay(z, i);
    }

    public final void setTouchCalibrationForInputDevice(
            String str, int i, TouchCalibration touchCalibration) {
        if (!checkCallingPermission(
                "android.permission.SET_INPUT_CALIBRATION",
                "setTouchCalibrationForInputDevice()",
                false)) {
            throw new SecurityException("Requires SET_INPUT_CALIBRATION permission");
        }
        Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
        Objects.requireNonNull(touchCalibration, "calibration must not be null");
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("surfaceRotation value out of bounds");
        }
        synchronized (this.mDataStore) {
            try {
                try {
                    PersistentDataStore persistentDataStore = this.mDataStore;
                    PersistentDataStore.InputDeviceState orCreateInputDeviceState =
                            persistentDataStore.getOrCreateInputDeviceState(str);
                    try {
                        if (!touchCalibration.equals(
                                orCreateInputDeviceState.mTouchCalibration[i])) {
                            orCreateInputDeviceState.mTouchCalibration[i] = touchCalibration;
                            persistentDataStore.mDirty = true;
                            this.mNative.reloadCalibration();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Slog.w("InputManager", "Cannot set touch calibration.", e);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final boolean setTspEnabled(int i, boolean z) {
        if (!this.mSystemReady) {
            Log.d("InputManager", "setTspEnabled: system not ready");
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "only system signature can use setEnableTSP(), but UID(",
                            ") has not system signature"));
        }
        SemInputDeviceManager semInputDeviceManager =
                (SemInputDeviceManager)
                        this.mContext.getSystemService("SemInputDeviceManagerService");
        if (i == InputManager.SemTspCommandType.SPAY.getvalue()) {
            semInputDeviceManager.setSpayEnable(z ? 1 : 0);
            SystemProperties.set("persist.service.tspcmd.spay", z ? "true" : "false");
            return true;
        }
        if (i == InputManager.SemTspCommandType.STYLUS.getvalue()) {
            semInputDeviceManager.setStylusEnable(z ? 1 : 0);
            return true;
        }
        if (i == InputManager.SemTspCommandType.BRUSH.getvalue()) {
            semInputDeviceManager.setBrushEnable(z ? 1 : 0);
            return true;
        }
        Log.w("InputManager", "setEnableTSP cmdtype: " + i + " enable: " + z);
        return false;
    }

    public final void setTypeAssociationInternal(String str, String str2) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mDeviceTypeAssociations).put(str, str2);
        }
        this.mNative.changeTypeAssociation();
    }

    public final void setUseLargePointerIcons(final boolean z) {
        final PointerIconCache pointerIconCache = this.mPointerIconCache;
        pointerIconCache.mUiThreadHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.input.PointerIconCache$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PointerIconCache pointerIconCache2 = PointerIconCache.this;
                        boolean z2 = z;
                        synchronized (pointerIconCache2.mLoadedPointerIconsByDisplayAndType) {
                            try {
                                if (pointerIconCache2.mUseLargePointerIcons == z2) {
                                    return;
                                }
                                pointerIconCache2.mUseLargePointerIcons = z2;
                                pointerIconCache2.mLoadedPointerIconsByDisplayAndType.clear();
                                pointerIconCache2.mNative.reloadPointerIcons();
                            } finally {
                            }
                        }
                    }
                });
    }

    public final synchronized void setWakeKeyDynamically(String str, boolean z, String str2) {
        this.mControlWakeKey.setWakeKeyDynamically(str, z, str2);
    }

    public final void setWindowManagerCallbacks(WindowManagerCallbacks windowManagerCallbacks) {
        WindowManagerCallbacks windowManagerCallbacks2 = this.mWindowManagerCallbacks;
        if (windowManagerCallbacks2 != null) {
            unregisterLidSwitchCallbackInternal(windowManagerCallbacks2);
        }
        this.mWindowManagerCallbacks = windowManagerCallbacks;
        registerLidSwitchCallbackInternal(windowManagerCallbacks);
    }

    public final void setWiredAccessoryCallbacks(WiredAccessoryCallbacks wiredAccessoryCallbacks) {
        this.mWiredAccessoryCallbacks = wiredAccessoryCallbacks;
    }

    public final void showingTouchSensitivityNotificationIfNeeded() {
        int touchSensitivity = getTouchSensitivity();
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        touchSensitivity,
                        "showingTouchSensitivityNotificationIfNeeded, mAutoAdjustTouch=",
                        " count=");
        m.append(this.mShowingTouchSensitivityNotiCount);
        m.append(" old count=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                m, this.mShowingTouchSensitivityNotiCountOld, "InputManager");
        if (touchSensitivity != 0) {
            return;
        }
        if (this.mNotificationManager == null) {
            Log.e("InputManager", "Notification manager is null");
            return;
        }
        int i = this.mShowingTouchSensitivityNotiCountOld;
        if (i != this.mShowingTouchSensitivityNotiCount || i >= 2) {
            return;
        }
        String string = this.mContext.getString(17043032);
        String string2 = this.mContext.getString(17043031);
        this.mNotificationManager.createNotificationChannel(
                new NotificationChannel("TouchSensitivityNoti", string, 3));
        this.mNotificationManager.notify(
                17043031,
                new Notification.Builder(this.mContext, "TouchSensitivityNoti")
                        .setSmallIcon(R.drawable.ic_vibrate_small)
                        .setContentTitle(string)
                        .setContentText(string2)
                        .setStyle(new Notification.BigTextStyle().bigText(string2))
                        .setContentIntent(createPendingIntent())
                        .setAutoCancel(true)
                        .setShowWhen(true)
                        .setActions(buildTurnOnAction(17043031))
                        .build());
        increaseTouchSensitivityNotiCount();
    }

    public final void start() {
        Slog.i("InputManager", "Starting input manager");
        this.mNative.start();
        Watchdog.getInstance().addMonitor(this);
        registerFlowPointerSettingObserver();
        registerFlowPointerDirectionSettingObserver();
        registerMultiFingerTapBehavior(4);
        registerMultiFingerTapBehavior(1);
        registerSetPenModeOnDexObserver();
        this.mContext.registerReceiver(
                new AnonymousClass3(this, 0),
                new IntentFilter("android.intent.action.USER_SWITCHED"),
                null,
                this.mHandler);
    }

    public final boolean startDragAndDrop(InputChannel inputChannel, InputChannel inputChannel2) {
        return this.mNative.transferTouchGesture(
                inputChannel.getToken(), inputChannel2.getToken(), true);
    }

    public final boolean supportPogoDevice() {
        if (this.mSystemReady) {
            boolean z =
                    ((SemInputDeviceManager)
                                            this.mContext.getSystemService(
                                                    "SemInputDeviceManagerService"))
                                    .getSupportDevice(31)
                            == 0;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "supportPogoDevice: ", "InputManager", z);
            return z;
        }
        Slog.d(
                "InputManager",
                "supportPogoDevice: system not ready, , caller="
                        + Debug.getCallers(5)
                        + ", pid="
                        + Binder.getCallingPid());
        return false;
    }

    public final boolean switchDeviceWirelessKeyboardShare(String str, int i) {
        boolean z;
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (!this.mSystemReady || this.mWirelessKeyboardMouseShare == null) {
                    z = true;
                } else {
                    if (this.mContext
                                    .getPackageManager()
                                    .checkSignatures(1000, Binder.getCallingUid())
                            != 0) {
                        Slog.d(
                                "InputManager",
                                "switchDeviceWirelessKeyboardShare : called by not allowed app");
                        return false;
                    }
                    z = this.mWirelessKeyboardMouseShare.switchDevice(i, str);
                }
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "switchDeviceWirelessKeyboardShare : ", str, "InputManager");
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void switchWirelessKeyboardShare(boolean z) {
        this.mNative.enableWirelessKeyboardShare(!z);
    }

    public final void systemRunning() {
        int i;
        if (DEBUG) {
            Slog.d("InputManager", "System ready.");
        }
        this.mNotificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        this.mDisplayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        final InputSettingsObserver inputSettingsObserver = this.mSettingsObserver;
        Iterator it = inputSettingsObserver.mObservers.keySet().iterator();
        while (it.hasNext()) {
            inputSettingsObserver
                    .mContext
                    .getContentResolver()
                    .registerContentObserver((Uri) it.next(), true, inputSettingsObserver, -1);
        }
        inputSettingsObserver.mContext.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.input.InputSettingsObserver.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Iterator it2 = InputSettingsObserver.this.mObservers.values().iterator();
                        while (it2.hasNext()) {
                            ((Consumer) it2.next()).accept("user switched");
                        }
                    }
                },
                new IntentFilter("android.intent.action.USER_SWITCHED"),
                null,
                inputSettingsObserver.mHandler);
        Iterator it2 = inputSettingsObserver.mObservers.values().iterator();
        while (it2.hasNext()) {
            ((Consumer) it2.next()).accept("just booted");
        }
        if (Flags.rateLimitUserActivityPokeInDispatcher()) {
            int integer =
                    inputSettingsObserver
                            .mContext
                            .getResources()
                            .getInteger(R.integer.config_num_physical_slots);
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    integer, "Setting user activity interval (ms) of ", "InputManager");
            inputSettingsObserver.mNative.setMinTimeBetweenUserActivityPokes(integer);
        }
        synchronized (this.mLidSwitchLock) {
            try {
                this.mSystemReady = true;
                int switchState = this.mNative.getSwitchState(-1, -256, 0);
                for (int i2 = 0; i2 < ((ArrayList) this.mLidSwitchCallbacks).size(); i2++) {
                    ((InputManagerInternal$LidSwitchCallback)
                                    ((ArrayList) this.mLidSwitchCallbacks).get(i2))
                            .notifyLidSwitchChanged(switchState == 0);
                }
                this.mNative.getSwitchState(-1, -256, 23);
                for (int i3 = 0; i3 < ((ArrayList) this.mLidSwitchCallbacks).size(); i3++) {
                    ((InputManagerInternal$LidSwitchCallback)
                                    ((ArrayList) this.mLidSwitchCallbacks).get(i3))
                            .getClass();
                }
            } finally {
            }
        }
        if (this.mNative.getSwitchState(-1, -256, 14) == 1) {
            setSensorPrivacy(1, true);
        }
        if (this.mNative.getSwitchState(-1, -256, 9) == 1) {
            setSensorPrivacy(2, true);
        }
        if (this.mNative.getSwitchState(-1, -256, 30) == 1) {
            this.mSpenCoverAttached = true;
            updateWacomMode();
        }
        int touchSensitivityNotiCount = getTouchSensitivityNotiCount();
        this.mShowingTouchSensitivityNotiCount = touchSensitivityNotiCount;
        this.mShowingTouchSensitivityNotiCountOld = touchSensitivityNotiCount;
        if (this.mNative.getSwitchState(-1, -256, 29) == 1) {
            this.mPaperCoverNotificationPending = true;
        }
        this.mContext.registerReceiver(
                new AnonymousClass3(this, 4),
                new IntentFilter("android.bluetooth.device.action.ALIAS_CHANGED"),
                null,
                this.mHandler);
        this.mHandler.sendEmptyMessage(2);
        WiredAccessoryCallbacks wiredAccessoryCallbacks = this.mWiredAccessoryCallbacks;
        if (wiredAccessoryCallbacks != null) {
            WiredAccessoryManager wiredAccessoryManager =
                    (WiredAccessoryManager) wiredAccessoryCallbacks;
            synchronized (wiredAccessoryManager.mLock) {
                wiredAccessoryManager.mWakeLock.acquire();
                wiredAccessoryManager.mHandler.sendMessage(
                        wiredAccessoryManager.mHandler.obtainMessage(2, 0, 0, null));
            }
        }
        this.mNative.setTspFeatures(checkInputFeature());
        if (this.mAddingPogoKeyboardIntentPending) {
            sendPogoKeyboardStatus(this.mPogoKeyboardConnected);
            this.mAddingPogoKeyboardIntentPending = false;
        }
        registerDesktopModeStateChangedListener();
        initTspCmdForSpay();
        synchronized (this.mDataStore) {
            boolean numLockState = this.mDataStore.getNumLockState();
            this.mNative.updateInputMetaState(32, numLockState);
            Log.d("InputManager", "readNumLock : " + numLockState);
        }
        final KeyboardLayoutManager keyboardLayoutManager = this.mKeyboardLayoutManager;
        keyboardLayoutManager.getClass();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        keyboardLayoutManager.mContext.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.input.KeyboardLayoutManager.1
                    public AnonymousClass1() {}

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        KeyboardLayoutManager.this.updateKeyboardLayouts();
                    }
                },
                intentFilter,
                null,
                keyboardLayoutManager.mHandler);
        keyboardLayoutManager.mHandler.sendEmptyMessage(3);
        InputManager inputManager =
                (InputManager) keyboardLayoutManager.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(
                keyboardLayoutManager, keyboardLayoutManager.mHandler);
        keyboardLayoutManager.mHandler.sendMessage(
                Message.obtain(
                        keyboardLayoutManager.mHandler, 1, inputManager.getInputDeviceIds()));
        BatteryController batteryController = this.mBatteryController;
        InputManager inputManager2 =
                (InputManager) batteryController.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager2);
        inputManager2.registerInputDeviceListener(
                batteryController.mInputDeviceListener, batteryController.mHandler);
        for (int i4 : inputManager2.getInputDeviceIds()) {
            batteryController.mInputDeviceListener.onInputDeviceAdded(i4);
        }
        this.mKeyboardBacklightController.systemRunning();
        KeyboardLedController keyboardLedController = this.mKeyboardLedController;
        SensorPrivacyManager sensorPrivacyManager =
                (SensorPrivacyManager)
                        keyboardLedController.mContext.getSystemService(SensorPrivacyManager.class);
        Objects.requireNonNull(sensorPrivacyManager);
        keyboardLedController.mSensorPrivacyManager = sensorPrivacyManager;
        InputManager inputManager3 =
                (InputManager) keyboardLedController.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager3);
        keyboardLedController.mInputManager = inputManager3;
        AudioManager audioManager =
                (AudioManager) keyboardLedController.mContext.getSystemService(AudioManager.class);
        Objects.requireNonNull(audioManager);
        keyboardLedController.mAudioManager = audioManager;
        keyboardLedController.mInputManager.registerInputDeviceListener(
                keyboardLedController, keyboardLedController.mHandler);
        keyboardLedController.mHandler.sendMessage(
                Message.obtain(
                        keyboardLedController.mHandler,
                        1,
                        keyboardLedController.mInputManager.getInputDeviceIds()));
        keyboardLedController.mContext.registerReceiverAsUser(
                keyboardLedController.mMicrophoneMuteChangedIntentReceiver,
                UserHandle.ALL,
                new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"),
                null,
                keyboardLedController.mHandler);
        KeyRemapper keyRemapper = this.mKeyRemapper;
        InputManager inputManager4 =
                (InputManager) keyRemapper.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager4);
        inputManager4.registerInputDeviceListener(keyRemapper, keyRemapper.mHandler);
        keyRemapper.mHandler.sendMessage(
                Message.obtain(keyRemapper.mHandler, 1, inputManager4.getInputDeviceIds()));
        PointerIconCache pointerIconCache = this.mPointerIconCache;
        DisplayManager displayManager =
                (DisplayManager) pointerIconCache.mContext.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        displayManager.registerDisplayListener(
                pointerIconCache.mDisplayListener, pointerIconCache.mUiThreadHandler);
        for (Display display : displayManager.getDisplays()) {
            pointerIconCache.mDisplayListener.onDisplayAdded(display.getDisplayId());
        }
        this.mGamePadRemapper.systemRunning();
    }

    @Deprecated
    public final boolean transferTouch(IBinder iBinder, int i) {
        Objects.requireNonNull(iBinder, "destChannelToken must not be null");
        return this.mNative.transferTouch(iBinder, i);
    }

    public final boolean transferTouchGesture(IBinder iBinder, IBinder iBinder2) {
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(iBinder2);
        return this.mNative.transferTouchGesture(iBinder, iBinder2, false);
    }

    public final void tryPointerSpeed(int i) {
        if (!checkCallingPermission(
                "android.permission.SET_POINTER_SPEED", "tryPointerSpeed()", false)) {
            throw new SecurityException("Requires SET_POINTER_SPEED permission");
        }
        if (i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        setPointerSpeedUnchecked(i);
    }

    public final void unregisterBatteryListener(
            int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) {
        Objects.requireNonNull(iInputDeviceBatteryListener);
        BatteryController batteryController = this.mBatteryController;
        int callingPid = Binder.getCallingPid();
        synchronized (batteryController.mLock) {
            try {
                BatteryController.ListenerRecord listenerRecord =
                        (BatteryController.ListenerRecord)
                                batteryController.mListenerRecords.get(Integer.valueOf(callingPid));
                if (listenerRecord == null) {
                    throw new IllegalArgumentException(
                            "Cannot unregister battery callback: No listener registered for pid "
                                    + callingPid);
                }
                if (listenerRecord.mListener.asBinder() != iInputDeviceBatteryListener.asBinder()) {
                    throw new IllegalArgumentException(
                            "Cannot unregister battery callback: The listener is not the one that"
                                + " is registered for pid "
                                    + callingPid);
                }
                if (!((ArraySet) listenerRecord.mMonitoredDevices).contains(Integer.valueOf(i))) {
                    throw new IllegalArgumentException(
                            "Cannot unregister battery callback: The device is not being monitored"
                                + " for deviceId "
                                    + i);
                }
                batteryController.unregisterRecordLocked(listenerRecord, i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterKeyboardBacklightListener(
            IKeyboardBacklightListener iKeyboardBacklightListener) {
        unregisterKeyboardBacklightListener_enforcePermission();
        Objects.requireNonNull(iKeyboardBacklightListener);
        this.mKeyboardBacklightController.unregisterKeyboardBacklightListener(
                iKeyboardBacklightListener, Binder.getCallingPid());
    }

    public final void unregisterLidSwitchCallbackInternal(
            InputManagerInternal$LidSwitchCallback inputManagerInternal$LidSwitchCallback) {
        synchronized (this.mLidSwitchLock) {
            ((ArrayList) this.mLidSwitchCallbacks).remove(inputManagerInternal$LidSwitchCallback);
        }
    }

    public final void unregisterSensorListener(
            IInputSensorEventListener iInputSensorEventListener) {
        if (DEBUG) {
            Slog.d(
                    "InputManager",
                    "unregisterSensorListener: listener="
                            + iInputSensorEventListener
                            + " callingPid="
                            + Binder.getCallingPid());
        }
        Objects.requireNonNull(iInputSensorEventListener, "listener must not be null");
        synchronized (this.mSensorEventLock) {
            try {
                int callingPid = Binder.getCallingPid();
                if (this.mSensorEventListeners.get(callingPid) != null) {
                    if (((SensorEventListenerRecord) this.mSensorEventListeners.get(callingPid))
                                    .mListener.asBinder()
                            != iInputSensorEventListener.asBinder()) {
                        throw new IllegalArgumentException("listener is not registered");
                    }
                    this.mSensorEventListeners.remove(callingPid);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterStickyModifierStateListener(
            IStickyModifierStateListener iStickyModifierStateListener) {
        unregisterStickyModifierStateListener_enforcePermission();
        Objects.requireNonNull(iStickyModifierStateListener);
        StickyModifierStateController stickyModifierStateController =
                this.mStickyModifierStateController;
        int callingPid = Binder.getCallingPid();
        synchronized (stickyModifierStateController.mStickyModifierStateListenerRecords) {
            try {
                StickyModifierStateController.StickyModifierStateListenerRecord
                        stickyModifierStateListenerRecord =
                                (StickyModifierStateController.StickyModifierStateListenerRecord)
                                        stickyModifierStateController
                                                .mStickyModifierStateListenerRecords.get(
                                                callingPid);
                if (stickyModifierStateListenerRecord == null) {
                    throw new IllegalStateException(
                            "The calling process has no registered StickyModifierStateListener.");
                }
                if (stickyModifierStateListenerRecord.mListener.asBinder()
                        != iStickyModifierStateListener.asBinder()) {
                    throw new IllegalStateException(
                            "The calling process has a different registered"
                                + " StickyModifierStateListener.");
                }
                stickyModifierStateListenerRecord
                        .mListener
                        .asBinder()
                        .unlinkToDeath(stickyModifierStateListenerRecord, 0);
                stickyModifierStateController.mStickyModifierStateListenerRecords.remove(
                        callingPid);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean unregisterVibratorStateListener(
            int i, IVibratorStateListener iVibratorStateListener) {
        synchronized (this.mVibratorLock) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mVibratorStateListeners.contains(i)) {
                    return ((RemoteCallbackList) this.mVibratorStateListeners.get(i))
                            .unregister(iVibratorStateListener);
                }
                Slog.w("InputManager", "Vibrator state listener " + i + " doesn't exist");
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void unsetTypeAssociationInternal(String str) {
        Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            ((ArrayMap) this.mDeviceTypeAssociations).remove(str);
        }
        this.mNative.changeTypeAssociation();
    }

    public final void updateAdditionalDisplayInputProperties(int i, Consumer consumer) {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                AdditionalDisplayInputProperties additionalDisplayInputProperties =
                        (AdditionalDisplayInputProperties)
                                this.mAdditionalDisplayInputProperties.get(i);
                if (additionalDisplayInputProperties == null) {
                    additionalDisplayInputProperties = new AdditionalDisplayInputProperties();
                    this.mAdditionalDisplayInputProperties.put(i, additionalDisplayInputProperties);
                }
                boolean z = additionalDisplayInputProperties.pointerIconVisible;
                boolean z2 = additionalDisplayInputProperties.mousePointerAccelerationEnabled;
                consumer.accept(additionalDisplayInputProperties);
                boolean z3 = additionalDisplayInputProperties.pointerIconVisible;
                if (z != z3) {
                    this.mNative.setPointerIconVisibility(i, z3);
                }
                boolean z4 = additionalDisplayInputProperties.mousePointerAccelerationEnabled;
                if (z2 != z4) {
                    this.mNative.setMousePointerAccelerationEnabled(i, z4);
                }
                if (additionalDisplayInputProperties.mousePointerAccelerationEnabled
                        && additionalDisplayInputProperties.pointerIconVisible) {
                    this.mAdditionalDisplayInputProperties.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateDeviceToGamepadProfile(String str, int i) {
        boolean z;
        if (this.mGamePadRemapper != null && checkSystemSignature("updateDeviceToGamepadProfile")) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "updateDeviceToGamepadProfile :", str, " ", "InputManager");
            GamePadRemapper gamePadRemapper = this.mGamePadRemapper;
            if (str == null) {
                gamePadRemapper.getClass();
                Log.d(
                        "InputManager-GamePadRemapper",
                        "removeDeviceToGamepadProfile : bt addr is null");
                return;
            }
            gamePadRemapper.getClass();
            if (GamePadRemapper.isValidId(i)) {
                synchronized (gamePadRemapper.mDeviceToProfileLock) {
                    ((HashMap) gamePadRemapper.mDeviceToProfile)
                            .put(str.toUpperCase(), Integer.valueOf(i));
                }
                z = true;
            } else {
                z = false;
            }
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "updateDeviceToGamepadProfile : ", " ", z);
            m.append(str.toUpperCase());
            m.append(" ");
            m.append(i);
            Log.d("InputManager-GamePadRemapper", m.toString());
        }
    }

    public final void updateFlowPointerDirectionSettings() {
        int i = 2;
        Bundle bundle = new Bundle(2);
        bundle.putString("key", "flow_pointer_from_where_dex");
        bundle.putString("def", "left");
        int i2 = 0;
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            String string =
                    call != null ? call.getString("flow_pointer_from_where_dex", "left") : "";
            if (string.equals("left")) {
                i = 0;
            } else if (string.equals("right")) {
                i = 1;
            }
            i2 = i;
        } catch (IllegalArgumentException e) {
            Log.e("InputManager", "Failed to get settings SETTINGS_KEY_POINTER_FLOW_DIRECTION", e);
        }
        this.mNative.setFlowPointerDirection(i2);
        Log.d("InputManager", "updateFlowPointerDirectionSettings : " + i2);
    }

    public final void updateFlowPointerSettings() {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", "flow_pointer_is_on_dex");
        bundle.putString("def", "false");
        boolean z = false;
        try {
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(DEX_SETTINGS_URI, "getSettings", (String) null, bundle);
            if (call != null) {
                if (call.getString("flow_pointer_is_on_dex").equals("true")) {
                    z = true;
                }
            }
        } catch (IllegalArgumentException e) {
            Log.e(
                    "InputManager",
                    "Failed to get settings SETTINGS_KEY_FLOW_POINTER_TO_PHONE_SCREEN",
                    e);
        }
        this.mNative.enableFlowPointer(z);
        Log.d("InputManager", "updateFlowPointerSettings : " + z);
    }

    public final void updateFocusEventDebugViewEnabled() {
        boolean z = this.mShowKeyPresses || this.mShowRotaryInput;
        synchronized (this.mFocusEventDebugViewLock) {
            try {
                FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
                if (z == (focusEventDebugView != null)) {
                    return;
                }
                if (z) {
                    final Context context = this.mContext;
                    final int i = 0;
                    final int i2 = 1;
                    focusEventDebugView =
                            new FocusEventDebugView(
                                    context,
                                    this,
                                    new Supplier() { // from class:
                                                     // com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            int i3 = i;
                                            Context context2 = context;
                                            switch (i3) {
                                                case 0:
                                                    return new RotaryInputValueView(context2);
                                                default:
                                                    return new RotaryInputGraphView(context2);
                                            }
                                        }
                                    },
                                    new Supplier() { // from class:
                                                     // com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            int i3 = i2;
                                            Context context2 = context;
                                            switch (i3) {
                                                case 0:
                                                    return new RotaryInputValueView(context2);
                                                default:
                                                    return new RotaryInputGraphView(context2);
                                            }
                                        }
                                    });
                    this.mFocusEventDebugView = focusEventDebugView;
                } else {
                    this.mFocusEventDebugView = null;
                }
                Objects.requireNonNull(focusEventDebugView);
                WindowManager windowManager =
                        (WindowManager) this.mContext.getSystemService(WindowManager.class);
                Objects.requireNonNull(windowManager);
                WindowManager windowManager2 = windowManager;
                if (!z) {
                    windowManager2.removeView(focusEventDebugView);
                    return;
                }
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.type = 2015;
                layoutParams.flags = 280;
                layoutParams.privateFlags |= 16;
                layoutParams.setFitInsetsTypes(0);
                layoutParams.layoutInDisplayCutoutMode = 3;
                layoutParams.format = -3;
                layoutParams.setTitle(
                        "FocusEventDebugView - display " + this.mContext.getDisplayId());
                layoutParams.inputFeatures = layoutParams.inputFeatures | 1;
                windowManager2.addView(focusEventDebugView, layoutParams);
            } finally {
            }
        }
    }

    public final void updateMultiFingerTapBehavior(int i) {
        int i2;
        if (i == 4) {
            i2 = getMultiFingerGestureBehavior("touchpad_gestures_three_finger_tap", i);
            this.mCurrentThreeTapBehavior = i2;
        } else if (i == 1) {
            i2 = getMultiFingerGestureBehavior("touchpad_gestures_four_finger_tap", i);
            this.mCurrentFourTapBehavior = i2;
        } else {
            i2 = 0;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i2, "updateMultiFingerTapBehavior : ", "InputManager");
    }

    public final void updatePointerLocationEnabled(boolean z) {
        final InputManagerCallback inputManagerCallback =
                (InputManagerCallback) this.mWindowManagerCallbacks;
        WindowManagerService windowManagerService = inputManagerCallback.mService;
        if (windowManagerService.mPointerLocationEnabled == z) {
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowManagerService windowManagerService2 = inputManagerCallback.mService;
                windowManagerService2.mPointerLocationEnabled = z;
                windowManagerService2.mRoot.forAllDisplayPolicies(
                        new Consumer() { // from class:
                                         // com.android.server.wm.InputManagerCallback$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((DisplayPolicy) obj)
                                        .setPointerLocationEnabled(
                                                InputManagerCallback.this
                                                        .mService
                                                        .mPointerLocationEnabled);
                            }
                        });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateSetPenModeOnDex() {
        int setPenModeOnDex = getSetPenModeOnDex(1);
        this.mNative.setPenModeOnDex(setPenModeOnDex);
        Log.d("InputManager", "updateSetPenModeOnDex : " + setPenModeOnDex);
    }

    public final void updateShowKeyPresses(boolean z) {
        if (this.mShowKeyPresses == z) {
            return;
        }
        this.mShowKeyPresses = z;
        updateFocusEventDebugViewEnabled();
        synchronized (this.mFocusEventDebugViewLock) {
            FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
            if (focusEventDebugView != null) {
                focusEventDebugView.getClass();
                focusEventDebugView.post(
                        new FocusEventDebugView$$ExternalSyntheticLambda0(
                                focusEventDebugView, z, 0));
            }
        }
    }

    public final void updateShowRotaryInput(boolean z) {
        if (this.mShowRotaryInput == z) {
            return;
        }
        this.mShowRotaryInput = z;
        updateFocusEventDebugViewEnabled();
        synchronized (this.mFocusEventDebugViewLock) {
            FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
            if (focusEventDebugView != null) {
                focusEventDebugView.getClass();
                focusEventDebugView.post(
                        new FocusEventDebugView$$ExternalSyntheticLambda0(
                                focusEventDebugView, z, 1));
            }
        }
    }

    public final void updateWacomMode() {
        if (!this.mSystemReady) {
            Log.d("InputManager", "updateWacomMode: system not ready");
            return;
        }
        int i = this.mPogoKeyboardConnected ? 2 : this.mSpenCoverAttached ? 1 : 0;
        if (i != this.mLastWacomMode) {
            SemInputDeviceManager semInputDeviceManager =
                    (SemInputDeviceManager)
                            this.mContext.getSystemService("SemInputDeviceManagerService");
            Log.d("InputManager", "updateWacomMode: " + this.mLastWacomMode + " -> " + i);
            semInputDeviceManager.setSpenCoverType(i);
            this.mLastWacomMode = i;
        }
    }

    public final void updateWirelessKeyboardShareStatus() {
        synchronized (this.mInputWirelessKeyboardMouseShareLock) {
            try {
                if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mWirelessKeyboardMouseShare.updateWirelessKeyboardShareStatus();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) {
        Objects.requireNonNull(inputEvent, "event must not be null");
        return this.mNative.verifyInputEvent(inputEvent);
    }

    public final void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) {
        VibrationInfo vibrationInfo = new VibrationInfo(vibrationEffect);
        VibratorToken vibratorToken = getVibratorToken(i, iBinder);
        synchronized (vibratorToken) {
            vibratorToken.mVibrating = true;
            this.mNative.vibrate(
                    i,
                    vibrationInfo.mPattern,
                    vibrationInfo.mAmplitudes,
                    vibrationInfo.mRepeat,
                    vibratorToken.mTokenValue);
        }
    }

    public final void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) {
        VibratorToken vibratorToken = getVibratorToken(i, iBinder);
        synchronized (vibratorToken) {
            try {
                if (!(combinedVibration instanceof CombinedVibration.Mono)
                        && !(combinedVibration instanceof CombinedVibration.Stereo)) {
                    Slog.e("InputManager", "Only Mono and Stereo effects are supported");
                    return;
                }
                vibratorToken.mVibrating = true;
                if (combinedVibration instanceof CombinedVibration.Mono) {
                    VibrationInfo vibrationInfo =
                            new VibrationInfo(
                                    ((CombinedVibration.Mono) combinedVibration).getEffect());
                    this.mNative.vibrate(
                            i,
                            vibrationInfo.mPattern,
                            vibrationInfo.mAmplitudes,
                            vibrationInfo.mRepeat,
                            vibratorToken.mTokenValue);
                } else if (combinedVibration instanceof CombinedVibration.Stereo) {
                    SparseArray effects =
                            ((CombinedVibration.Stereo) combinedVibration).getEffects();
                    SparseArray sparseArray = new SparseArray(effects.size());
                    long[] jArr = new long[0];
                    int i2 = Integer.MIN_VALUE;
                    for (int i3 = 0; i3 < effects.size(); i3++) {
                        VibrationInfo vibrationInfo2 =
                                new VibrationInfo((VibrationEffect) effects.valueAt(i3));
                        if (jArr.length == 0) {
                            jArr = vibrationInfo2.mPattern;
                        }
                        if (i2 == Integer.MIN_VALUE) {
                            i2 = vibrationInfo2.mRepeat;
                        }
                        sparseArray.put(effects.keyAt(i3), vibrationInfo2.mAmplitudes);
                    }
                    this.mNative.vibrateCombined(
                            i, jArr, sparseArray, i2, vibratorToken.mTokenValue);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void wakeUp(long j, int i, String str) {
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        if (powerManager != null) {
            powerManager.wakeUp(SystemClock.uptimeMillis(), i, str);
            Log.d("InputManager", "wakeup -" + str);
        }
    }

    public final void wakeUpWhenPogoConnected(boolean z) {
        if (z) {
            long uptimeMillis = SystemClock.uptimeMillis();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = (int) uptimeMillis;
            obtain.argi2 = (int) (uptimeMillis >> 32);
            obtain.argi3 = 105;
            obtain.arg1 = "android.policy:POGO_CONNECT";
            this.mHandler.obtainMessage(106, obtain).sendToTarget();
        }
    }
}
