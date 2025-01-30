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
import android.database.ContentObserver;
import android.graphics.PointF;
import android.graphics.drawable.Icon;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.hardware.input.ISwitchEventChangedListener;
import android.hardware.input.ITabletModeChangedListener;
import android.hardware.input.IWirelessKeyboardShareChangedListener;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.InputSensorInfo;
import android.hardware.input.KeyboardLayout;
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
import android.os.VibrationEffect;
import android.os.vibrator.StepSegment;
import android.os.vibrator.VibrationEffectSegment;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
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
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.SurfaceControl;
import android.view.VerifiedInputEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.InputMethodSubtypeHandle;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.DisplayThread;
import com.android.server.LocalServices;
import com.android.server.Watchdog;
import com.android.server.display.DisplayPowerController2;
import com.android.server.location.gnss.hal.GnssNative;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.sepunion.SemUnionManagerLocal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class InputManagerService extends IInputManager.Stub implements Watchdog.Monitor {
  public static final Uri DEX_SETTINGS_URI;
  public static boolean SEP_FULL;
  public static final boolean mMultiFingerGestureEnable;
  public final int CLICK_BEHAVIOR_APPS;
  public final int CLICK_BEHAVIOR_BACK;
  public final int CLICK_BEHAVIOR_HOME;
  public final int CLICK_BEHAVIOR_NONE;
  public final int CLICK_BEHAVIOR_OPEN_QUICK_SETTINGS;
  public final int CLICK_BEHAVIOR_RECENT;
  public final int CLICK_BEHAVIOR_VIEW_NOTIFICATION;
  public final int GESTURE_3_FINGER_TAP;
  public final int GESTURE_3_FINGER_TAP_ON_FLEXMODE;
  public final int GESTURE_4_FINGER_TAP;
  public final int GESTURE_4_FINGER_TAP_ON_FLEXMODE;
  public int mAcknowledgedPointerDisplayId;
  public boolean mAddingPogoKeyboardIntentPending;
  public final SparseArray mAdditionalDisplayInputProperties;
  public final Object mAdditionalDisplayInputPropertiesLock;
  public final Object mAssociationsLock;
  public boolean mBackKeyDownAdjusted;
  public final BatteryController mBatteryController;
  public int mBlockDeviceMode;
  public Vector mBlockTkeyCallerList;
  public Vector mBlockTspCallerList;
  public boolean mBootCompleted;
  public final BroadcastReceiver mBroadcastReceiver;
  public final Context mContext;
  public ControlWakeKey mControlWakeKey;
  public final AdditionalDisplayInputProperties mCurrentDisplayProperties;
  public int mCurrentFourTapBehavior;
  public int mCurrentThreeTapBehavior;
  public final PersistentDataStore mDataStore;
  public PointerIcon mDefaultPointerIcon;
  public boolean mDefaultPointerIconChanged;
  public DesktopModeServiceCallbacks mDesktopModeServiceCallbacks;
  public final BroadcastReceiver mDeviceBlockReceiver;
  public final Map mDeviceTypeAssociations;
  public boolean mDexImeWindowVisibleInDefaultDisplay;
  public final IDisplayFoldListener.Stub mDisplayFoldListener;
  public int mDisplayIdForPointerIcon;
  public DisplayManagerInternal mDisplayManagerInternal;
  public final File mDoubleTouchGestureEnableFile;
  public FocusEventDebugView mFocusEventDebugView;
  public final Object mFocusEventDebugViewLock;
  public final SensorEventListener mFoldingAngleListener;
  public boolean mFoldingAngleRegistered;
  public Sensor mFoldingAngleSensor;
  public int mFoldingState;
  public PointerIcon mForcedDefaultPointerIcon;
  public boolean mForcedDefaultPointerIconChanged;
  public final InputManagerHandler mHandler;
  public InputDevice[] mInputDevices;
  public final SparseArray mInputDevicesChangedListeners;
  public boolean mInputDevicesChangedPending;
  public final Object mInputDevicesLock;
  public IInputFilter mInputFilter;
  public InputFilterHost mInputFilterHost;
  public final Object mInputFilterLock;
  public InputKeyCounter mInputKeyCounter;
  public InputMethodManagerCallbacks mInputMethodManagerCallbacks;
  public final Map mInputMonitors;
  public final Object mInputWirelessKeyboardMouseShareLock;
  public boolean mIsKidsMode;
  public final SparseBooleanArray mIsVibrating;
  public final KeyRemapper mKeyRemapper;
  public final KeyboardBacklightControllerInterface mKeyboardBacklightController;
  public final Map mKeyboardLayoutAssociations;
  public final KeyboardLayoutManager mKeyboardLayoutManager;
  public final BroadcastReceiver mKidsModeReceiver;
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
  public final NativeInputManagerService mNative;
  public int mNextVibratorTokenValue;
  public NotificationManager mNotificationManager;
  public boolean mNotifyPogoKeyboardNotMatchPending;
  public int mOverriddenPointerDisplayId;
  public boolean mPaperCoverNotificationPending;
  public boolean mPogoKeyboardConnected;
  public PointerIcon mPointerIcon;
  public final SparseArray mPointerIconChangedListeners;
  public Context mPointerIconDisplayContext;
  public final Object mPointerIconLock;
  public int mPointerIconType;
  public int mRequestedPointerDisplayId;
  public final Map mRuntimeAssociations;
  public SecAccessoryManagerCallbacks mSecAccessoryManagerCallbacks;
  public final List mSensorAccuracyListenersToNotify;
  public final SparseArray mSensorEventListeners;
  public final List mSensorEventListenersToNotify;
  public final Object mSensorEventLock;
  public SensorManager mSensorManager;
  public final InputSettingsObserver mSettingsObserver;
  public PowerManager.WakeLock mSharedKeyWakeLock;
  public final BroadcastReceiver mShowingTouchSensitivityNotiActionReceiver;
  public int mShowingTouchSensitivityNotiCount;
  public int mShowingTouchSensitivityNotiCountOld;
  public boolean mSpenCoverAttached;
  public final Map mStaticAssociations;
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
  public ToastDialog mToastDialog;
  public int mToolTypeForDefaultPointerIcon;
  public int mToolTypeForForcedDefaultPointerIcon;
  public int mTspFeatures;
  public SemUnionManagerLocal mUnionManagerLocal;
  public final Map mUniqueIdAssociations;
  public final boolean mUseDevInputEventForAudioJack;
  public final String mVelocityTrackerStrategy;
  public final Object mVibratorLock;
  public final SparseArray mVibratorStateListeners;
  public final Map mVibratorTokens;
  public WindowManagerCallbacks mWindowManagerCallbacks;
  public WiredAccessoryCallbacks mWiredAccessoryCallbacks;
  public WirelessKeyboardMouseShare mWirelessKeyboardMouseShare;
  public final SparseArray mWirelessKeyboardShareChangedListeners;
  public final Object mWirelessKeyboardShareLock;
  public static final boolean DEBUG = Log.isLoggable("InputManager", 3);
  public static final AdditionalDisplayInputProperties DEFAULT_ADDITIONAL_DISPLAY_INPUT_PROPERTIES =
      new AdditionalDisplayInputProperties();
  public static final boolean KEYBOARD_BACKLIGHT_CONTROL_ENABLED =
      SystemProperties.getBoolean("persist.input.keyboard.backlight_control.enabled", true);
  public static final boolean IS_TABLET_DEVICE =
      SystemProperties.get("ro.build.characteristics", "phone").contains("tablet");
  public static int mSharedKeyReferenceCount = 0;
  public static final float[] mHighHysteresis = {30.0f, 160.0f, 360.0f};
  public static final float[] mLowHysteresis = {
    DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 20.0f, 150.0f
  };

  public interface DesktopModeServiceCallbacks {
    void notifyUnverifiedCoverAttachChanged(long j, boolean z);
  }

  public interface InputMethodManagerCallbacks {
    void notifyKeyboardCoverBackfolded(long j, boolean z);
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

  public interface SecAccessoryManagerCallbacks {
    void notifyCoverSwitchStateChanged(long j, boolean z);

    void notifyUnverifiedCoverAttachChanged(long j, boolean z);
  }

  public interface WindowManagerCallbacks extends InputManagerInternal.LidSwitchCallback {
    SurfaceControl createSurfaceForGestureMonitor(String str, int i);

    KeyEvent dispatchUnhandledKey(IBinder iBinder, KeyEvent keyEvent, int i);

    SurfaceControl getParentSurfaceForPointers(int i);

    int getPointerDisplayId();

    int getPointerLayer();

    long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i);

    int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i);

    int interceptMotionBeforeQueueingNonInteractive(int i, long j, int i2);

    boolean interceptQuickAccess(int i, float f, float f2);

    void notifyCameraLensCoverSwitchChanged(long j, boolean z);

    void notifyConfigurationChanged();

    void notifyDropWindow(IBinder iBinder, float f, float f2);

    void notifyFocusChanged(IBinder iBinder, IBinder iBinder2);

    void notifyInputChannelBroken(IBinder iBinder);

    void notifyNoFocusedWindowAnr(InputApplicationHandle inputApplicationHandle);

    void notifyPenSwitchChanged(long j, boolean z, boolean z2);

    void notifyPogoKeyboardStatus(boolean z);

    void notifyPointerDisplayIdChanged(int i, float f, float f2);

    void notifyWindowResponsive(IBinder iBinder, OptionalInt optionalInt);

    void notifyWindowUnresponsive(IBinder iBinder, OptionalInt optionalInt, String str);

    void onPointerDownOutsideFocus(IBinder iBinder);

    void onPointerDownUpCancelOutsideFocus(IBinder iBinder, int i, int i2, int i3);

    void startGameToolsService(int i, int i2, boolean z);
  }

  public interface WiredAccessoryCallbacks {
    void notifyWiredAccessoryChanged(long j, int i, int i2);

    void systemReady();
  }

  public final int getKeyRepeatTimeout() {
    return 400;
  }

  static {
    mMultiFingerGestureEnable = Build.VERSION.SEM_PLATFORM_INT >= 110100;
    DEX_SETTINGS_URI =
        Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
  }

  public class KeyCountRunnable implements Runnable {
    public KeyCountRunnable() {}

    @Override // java.lang.Runnable
    public void run() {
      InputManagerService.this.mInputKeyCounter.sendBroadcastKeyCount(
          InputManagerService.this.mContext);
    }
  }

  class Injector {
    public final Context mContext;
    public final Looper mLooper;

    public Injector(Context context, Looper looper) {
      this.mContext = context;
      this.mLooper = looper;
    }

    public Context getContext() {
      return this.mContext;
    }

    public Looper getLooper() {
      return this.mLooper;
    }

    public NativeInputManagerService getNativeService(InputManagerService inputManagerService) {
      return new NativeInputManagerService.NativeImpl(inputManagerService, this.mLooper.getQueue());
    }

    public void registerLocalService(InputManagerInternal inputManagerInternal) {
      LocalServices.addService(InputManagerInternal.class, inputManagerInternal);
    }
  }

  public InputManagerService(Context context) {
    this(new Injector(context, DisplayThread.get().getLooper()));
  }

  public InputManagerService(Injector injector) {
    KeyboardBacklightControllerInterface keyboardBacklightControllerInterface;
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
    PersistentDataStore persistentDataStore = new PersistentDataStore();
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
    this.mUniqueIdAssociations = new ArrayMap();
    this.mKeyboardLayoutAssociations = new ArrayMap();
    this.mDeviceTypeAssociations = new ArrayMap();
    this.mAdditionalDisplayInputPropertiesLock = new Object();
    this.mOverriddenPointerDisplayId = -1;
    this.mAcknowledgedPointerDisplayId = -1;
    this.mRequestedPointerDisplayId = -1;
    this.mAdditionalDisplayInputProperties = new SparseArray();
    this.mCurrentDisplayProperties = new AdditionalDisplayInputProperties();
    this.mPointerIconType = 1;
    this.mInputMonitors = new HashMap();
    this.mInputKeyCounter = new InputKeyCounter();
    this.mWirelessKeyboardShareLock = new Object();
    this.mWirelessKeyboardShareChangedListeners = new SparseArray();
    this.mTempWirelessKeyboardShareChangedListenersToNotify = new ArrayList();
    this.mInputWirelessKeyboardMouseShareLock = new Object();
    this.mFoldingState = 2;
    this.mFoldingAngleRegistered = false;
    this.mBlockDeviceMode = 0;
    this.mBlockTspCallerList = new Vector();
    this.mBlockTkeyCallerList = new Vector();
    this.mPogoKeyboardConnected = false;
    this.mAddingPogoKeyboardIntentPending = false;
    this.mNotifyPogoKeyboardNotMatchPending = false;
    this.CLICK_BEHAVIOR_NONE = 0;
    this.CLICK_BEHAVIOR_APPS = 1;
    this.CLICK_BEHAVIOR_HOME = 2;
    this.CLICK_BEHAVIOR_RECENT = 3;
    this.CLICK_BEHAVIOR_BACK = 4;
    this.CLICK_BEHAVIOR_VIEW_NOTIFICATION = 5;
    this.CLICK_BEHAVIOR_OPEN_QUICK_SETTINGS = 6;
    this.mCurrentThreeTapBehavior = 4;
    this.mCurrentFourTapBehavior = 1;
    this.GESTURE_3_FINGER_TAP = 0;
    this.GESTURE_4_FINGER_TAP = 1;
    this.GESTURE_3_FINGER_TAP_ON_FLEXMODE = 2;
    this.GESTURE_4_FINGER_TAP_ON_FLEXMODE = 3;
    this.mFocusEventDebugViewLock = new Object();
    this.mDefaultPointerIconChanged = false;
    this.mDefaultPointerIcon = null;
    this.mToolTypeForDefaultPointerIcon = 0;
    this.mForcedDefaultPointerIconChanged = false;
    this.mForcedDefaultPointerIcon = null;
    this.mToolTypeForForcedDefaultPointerIcon = 0;
    this.mIsKidsMode = false;
    this.mBackKeyDownAdjusted = false;
    this.mDexImeWindowVisibleInDefaultDisplay = false;
    this.mPaperCoverNotificationPending = false;
    this.mShowingTouchSensitivityNotiCount = 0;
    this.mShowingTouchSensitivityNotiCountOld = 0;
    this.mDisplayFoldListener =
        new IDisplayFoldListener
            .Stub() { // from class: com.android.server.input.InputManagerService.5
          public void onDisplayFoldChanged(int i, boolean z) {
            Log.d("InputManager", "onDisplayFoldChanged: folded = " + z);
            InputManagerService.this.mNative.setDisplayFolded(z);
          }
        };
    this.mTempGamePads = new ArrayList();
    this.mTspFeatures = -1;
    this.mSpenCoverAttached = false;
    this.mLastWacomMode = -1;
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.6
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI"
                .equals(intent.getAction())) {
              Settings.System.putIntForUser(
                  InputManagerService.this.mContext.getContentResolver(),
                  "auto_adjust_touch",
                  1,
                  -2);
              int intExtra = intent.getIntExtra("channelId", 0);
              if (intExtra == 0) {
                Log.d("InputManager", "channel id was wrong. id=" + intExtra);
                return;
              }
              InputManagerService.this.mNotificationManager.cancel(intExtra);
            }
          }
        };
    this.mShowingTouchSensitivityNotiActionReceiver = broadcastReceiver;
    this.mDisplayIdForPointerIcon = 0;
    this.mFoldingAngleListener =
        new SensorEventListener() { // from class: com.android.server.input.InputManagerService.12
          @Override // android.hardware.SensorEventListener
          public void onAccuracyChanged(Sensor sensor, int i) {}

          @Override // android.hardware.SensorEventListener
          public void onSensorChanged(SensorEvent sensorEvent) {
            boolean z = false;
            float f = sensorEvent.values[0];
            while (InputManagerService.this.mFoldingState > 0
                && f < InputManagerService.mLowHysteresis[InputManagerService.this.mFoldingState]) {
              InputManagerService.this.mFoldingState--;
              z = true;
            }
            while (InputManagerService.this.mFoldingState < 2
                && f
                    > InputManagerService.mHighHysteresis[InputManagerService.this.mFoldingState]) {
              InputManagerService.this.mFoldingState++;
              z = true;
            }
            if (z) {
              Log.d(
                  "InputManager",
                  "mFoldingAngleListener: state changed, angle = "
                      + f
                      + ", state = "
                      + InputManagerService.this.mFoldingState);
              InputManagerService.this.mNative.setFoldingState(
                  InputManagerService.this.mFoldingState);
            }
          }
        };
    BroadcastReceiver broadcastReceiver2 =
        new BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.14
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.samsung.android.intent.action.SET_INWATER_TOUCH".equals(action)) {
              try {
                boolean booleanExtra = intent.getBooleanExtra("set", false);
                boolean booleanExtra2 = intent.getBooleanExtra("force", false);
                String stringExtra = intent.getStringExtra("package");
                int intExtra = intent.getIntExtra("type", 3);
                StringBuilder sb = new StringBuilder();
                sb.append("received:");
                sb.append(action);
                sb.append(" packageName:");
                sb.append(stringExtra != null ? stringExtra : "null");
                Log.d("InputManager", sb.toString());
                if (TextUtils.isEmpty(stringExtra)) {
                  return;
                }
                InputManagerService.this.setBlockDeviceMode(
                    booleanExtra, intExtra, booleanExtra2, stringExtra);
              } catch (Exception e) {
                Slog.w("InputManager", "Could not set device block", e);
              }
            }
          }
        };
    this.mDeviceBlockReceiver = broadcastReceiver2;
    C166715 c166715 = new C166715();
    this.mBroadcastReceiver = c166715;
    BroadcastReceiver broadcastReceiver3 =
        new BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.16
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            if ("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE"
                .equals(intent.getAction())) {
              boolean booleanExtra = intent.getBooleanExtra("kids_home_mode", false);
              Log.d(
                  "InputManager",
                  "KidsMode : " + InputManagerService.this.mIsKidsMode + " -> " + booleanExtra);
              if (InputManagerService.this.mIsKidsMode != booleanExtra) {
                InputManagerService.this.mNative.updateInputMetaState(16, booleanExtra);
                InputManagerService.this.mIsKidsMode = booleanExtra;
              }
            }
          }
        };
    this.mKidsModeReceiver = broadcastReceiver3;
    this.mStaticAssociations = loadStaticInputPortAssociations();
    Context context = injector.getContext();
    this.mContext = context;
    InputManagerHandler inputManagerHandler = new InputManagerHandler(injector.getLooper());
    this.mHandler = inputManagerHandler;
    NativeInputManagerService nativeService = injector.getNativeService(this);
    this.mNative = nativeService;
    this.mSettingsObserver =
        new InputSettingsObserver(context, inputManagerHandler, this, nativeService);
    this.mKeyboardLayoutManager =
        new KeyboardLayoutManager(
            context, nativeService, persistentDataStore, injector.getLooper());
    this.mBatteryController = new BatteryController(context, nativeService, injector.getLooper());
    if (KEYBOARD_BACKLIGHT_CONTROL_ENABLED) {
      keyboardBacklightControllerInterface =
          new KeyboardBacklightController(
              context, nativeService, persistentDataStore, injector.getLooper());
    } else {
      keyboardBacklightControllerInterface =
          new KeyboardBacklightControllerInterface() { // from class:
                                                       // com.android.server.input.InputManagerService.1
          };
    }
    this.mKeyboardBacklightController = keyboardBacklightControllerInterface;
    this.mKeyRemapper =
        new KeyRemapper(context, nativeService, persistentDataStore, injector.getLooper());
    SEP_FULL =
        !context
            .getPackageManager()
            .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite");
    boolean z = context.getResources().getBoolean(R.bool.config_supportPreRebootSecurityLogs);
    this.mUseDevInputEventForAudioJack = z;
    Slog.i("InputManager", "Initializing input manager, mUseDevInputEventForAudioJack=" + z);
    String string = context.getResources().getString(R.string.deprecated_target_sdk_app_store);
    this.mDoubleTouchGestureEnableFile = TextUtils.isEmpty(string) ? null : new File(string);
    this.mVelocityTrackerStrategy =
        DeviceConfig.getProperty("input_native_boot", "velocitytracker_strategy");
    injector.registerLocalService(new LocalService());
    IntentFilter intentFilter = new IntentFilter();
    boolean z2 = CoreRune.IFW_KEY_COUNTER;
    intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
    context.registerReceiver(c166715, intentFilter, null, inputManagerHandler);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("com.samsung.android.intent.action.SET_INWATER_TOUCH");
    context.registerReceiver(
        broadcastReceiver2,
        intentFilter2,
        "com.samsung.android.permission.SEM_SET_DEVICE_BLOCK",
        inputManagerHandler);
    context.registerReceiver(
        broadcastReceiver3,
        new IntentFilter("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE"),
        "com.samsung.kidshome.broadcast.DEFAULT_HOME_CHANGE_PERMISSION",
        inputManagerHandler);
    context.registerReceiver(
        broadcastReceiver,
        new IntentFilter("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI"));
    this.mToastDialog = new ToastDialog(context);
    this.mControlWakeKey = new ControlWakeKey(context);
    if (IS_TABLET_DEVICE) {
      this.mWirelessKeyboardMouseShare =
          new WirelessKeyboardMouseShare(context, this, this.mToastDialog);
      PowerManager.WakeLock newWakeLock =
          ((PowerManager) context.getSystemService("power"))
              .newWakeLock(1, "InputManager.mSharedKeyWakeLock");
      this.mSharedKeyWakeLock = newWakeLock;
      newWakeLock.setReferenceCounted(false);
      mSharedKeyReferenceCount = 0;
    }
  }

  public void setWindowManagerCallbacks(WindowManagerCallbacks windowManagerCallbacks) {
    WindowManagerCallbacks windowManagerCallbacks2 = this.mWindowManagerCallbacks;
    if (windowManagerCallbacks2 != null) {
      unregisterLidSwitchCallbackInternal(windowManagerCallbacks2);
    }
    this.mWindowManagerCallbacks = windowManagerCallbacks;
    registerLidSwitchCallbackInternal(windowManagerCallbacks);
  }

  public void setWiredAccessoryCallbacks(WiredAccessoryCallbacks wiredAccessoryCallbacks) {
    this.mWiredAccessoryCallbacks = wiredAccessoryCallbacks;
  }

  public void registerLidSwitchCallbackInternal(
      InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
    synchronized (this.mLidSwitchLock) {
      this.mLidSwitchCallbacks.add(lidSwitchCallback);
      if (this.mSystemReady) {
        lidSwitchCallback.notifyLidSwitchChanged(0L, getSwitchState(-1, -256, 0) == 0);
      }
    }
  }

  public void unregisterLidSwitchCallbackInternal(
      InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
    synchronized (this.mLidSwitchLock) {
      this.mLidSwitchCallbacks.remove(lidSwitchCallback);
    }
  }

  public void start() {
    Slog.i("InputManager", "Starting input manager");
    this.mNative.start();
    Watchdog.getInstance().addMonitor(this);
    registerFlowPointerSettingObserver();
    registerFlowPointerDirectionSettingObserver();
    registerMultiFingerTapBehavior(4);
    registerMultiFingerTapBehavior(1);
    registerSetPenModeOnDexObserver();
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            InputManagerService.this.updateFlowPointerSettings();
            InputManagerService.this.updateFlowPointerDirectionSettings();
            InputManagerService.this.updateMultiFingerTapBehavior(4);
            InputManagerService.this.updateMultiFingerTapBehavior(1);
            InputManagerService.this.updateSetPenModeOnDex();
          }
        },
        new IntentFilter("android.intent.action.USER_SWITCHED"),
        null,
        this.mHandler);
  }

  public void systemRunning() {
    if (DEBUG) {
      Slog.d("InputManager", "System ready.");
    }
    this.mNotificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    this.mDisplayManagerInternal =
        (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
    this.mSettingsObserver.registerAndUpdate();
    synchronized (this.mLidSwitchLock) {
      this.mSystemReady = true;
      int switchState = getSwitchState(-1, -256, 0);
      for (int i = 0; i < this.mLidSwitchCallbacks.size(); i++) {
        ((InputManagerInternal.LidSwitchCallback) this.mLidSwitchCallbacks.get(i))
            .notifyLidSwitchChanged(0L, switchState == 0);
      }
    }
    if (getSwitchState(-1, -256, 14) == 1) {
      setSensorPrivacy(1, true);
    }
    if (getSwitchState(-1, -256, 9) == 1) {
      setSensorPrivacy(2, true);
    }
    if (getSwitchState(-1, -256, 30) == 1) {
      this.mSpenCoverAttached = true;
      updateWacomMode();
    }
    int touchSensitivityNotiCount = getTouchSensitivityNotiCount();
    this.mShowingTouchSensitivityNotiCount = touchSensitivityNotiCount;
    this.mShowingTouchSensitivityNotiCountOld = touchSensitivityNotiCount;
    if (getSwitchState(-1, -256, 29) == 1) {
      this.mPaperCoverNotificationPending = true;
    }
    this.mContext.registerReceiver(
        new BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            InputManagerService.this.reloadDeviceAliases();
          }
        },
        new IntentFilter("android.bluetooth.device.action.ALIAS_CHANGED"),
        null,
        this.mHandler);
    this.mHandler.sendEmptyMessage(2);
    WiredAccessoryCallbacks wiredAccessoryCallbacks = this.mWiredAccessoryCallbacks;
    if (wiredAccessoryCallbacks != null) {
      wiredAccessoryCallbacks.systemReady();
    }
    this.mNative.setTspFeatures(checkInputFeature());
    if (this.mAddingPogoKeyboardIntentPending) {
      sendPogoKeyboardStatus(this.mPogoKeyboardConnected);
      this.mAddingPogoKeyboardIntentPending = false;
    }
    registerDesktopModeStateChangedListener();
    initTspCmdForSpay();
    this.mKeyboardLayoutManager.systemRunning();
    this.mBatteryController.systemRunning();
    this.mKeyboardBacklightController.systemRunning();
    this.mKeyRemapper.systemRunning();
    NativeInputManagerService nativeInputManagerService = this.mNative;
    InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
    Objects.requireNonNull(inputManager);
    nativeInputManagerService.setStylusPointerIconEnabled(
        inputManager.isStylusPointerIconEnabled());
  }

  public final void reloadDeviceAliases() {
    if (DEBUG) {
      Slog.d("InputManager", "Reloading device names.");
    }
    this.mNative.reloadDeviceAliases();
  }

  public final void setDisplayViewportsInternal(List list) {
    setDisplayDpi();
    DisplayViewport[] displayViewportArr = new DisplayViewport[list.size()];
    for (int size = list.size() - 1; size >= 0; size--) {
      displayViewportArr[size] = (DisplayViewport) list.get(size);
    }
    this.mNative.setDisplayViewports(displayViewportArr);
    int pointerDisplayId = this.mWindowManagerCallbacks.getPointerDisplayId();
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      if (this.mOverriddenPointerDisplayId == -1) {
        updatePointerDisplayIdLocked(pointerDisplayId);
      }
    }
  }

  public int getKeyCodeState(int i, int i2, int i3) {
    return this.mNative.getKeyCodeState(i, i2, i3);
  }

  public int getScanCodeState(int i, int i2, int i3) {
    return this.mNative.getScanCodeState(i, i2, i3);
  }

  public int getSwitchState(int i, int i2, int i3) {
    return this.mNative.getSwitchState(i, i2, i3);
  }

  public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) {
    Objects.requireNonNull(iArr, "keyCodes must not be null");
    Objects.requireNonNull(zArr, "keyExists must not be null");
    if (zArr.length < iArr.length) {
      throw new IllegalArgumentException("keyExists must be at least as large as keyCodes");
    }
    return this.mNative.hasKeys(i, i2, iArr, zArr);
  }

  public int getKeyCodeForKeyLocation(int i, int i2) {
    if (i2 <= 0 || i2 > KeyEvent.getMaxKeyCode()) {
      return 0;
    }
    return this.mNative.getKeyCodeForKeyLocation(i, i2);
  }

  public boolean transferTouch(IBinder iBinder, int i) {
    Objects.requireNonNull(iBinder, "destChannelToken must not be null");
    return this.mNative.transferTouch(iBinder, i);
  }

  public InputChannel monitorInput(String str, int i) {
    return monitorInput(str, i, GnssNative.GNSS_AIDING_TYPE_ALL);
  }

  public InputChannel monitorInput(String str, int i, int i2) {
    Objects.requireNonNull(str, "inputChannelName not be null");
    if (i < 0) {
      throw new IllegalArgumentException("displayId must >= 0.");
    }
    return this.mNative.createInputMonitor(i, str, Binder.getCallingPid(), i2);
  }

  public final InputChannel createSpyWindowGestureMonitor(
      IBinder iBinder, String str, SurfaceControl surfaceControl, int i, int i2, int i3, int i4) {
    final InputChannel createInputChannel = createInputChannel(str, i4);
    try {
      iBinder.linkToDeath(
          new IBinder
              .DeathRecipient() { // from class:
                                  // com.android.server.input.InputManagerService$$ExternalSyntheticLambda11
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
              InputManagerService.this.lambda$createSpyWindowGestureMonitor$0(createInputChannel);
            }
          },
          0);
      synchronized (this.mInputMonitors) {
        this.mInputMonitors.put(
            createInputChannel.getToken(),
            new GestureMonitorSpyWindow(
                iBinder, str, i, i2, i3, surfaceControl, createInputChannel));
      }
      InputChannel inputChannel = new InputChannel();
      createInputChannel.copyTo(inputChannel);
      return inputChannel;
    } catch (RemoteException unused) {
      Slog.i("InputManager", "Client died before '" + str + "' could be created.");
      return null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createSpyWindowGestureMonitor$0(InputChannel inputChannel) {
    removeSpyWindowGestureMonitor(inputChannel.getToken());
  }

  public final void removeSpyWindowGestureMonitor(IBinder iBinder) {
    GestureMonitorSpyWindow gestureMonitorSpyWindow;
    synchronized (this.mInputMonitors) {
      gestureMonitorSpyWindow = (GestureMonitorSpyWindow) this.mInputMonitors.remove(iBinder);
    }
    removeInputChannel(iBinder);
    if (gestureMonitorSpyWindow == null) {
      return;
    }
    gestureMonitorSpyWindow.remove();
  }

  public InputChannel monitorInputForBinder(String str, int i, int i2) {
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    if (callingUid != 1000
        && this.mContext.checkPermission("android.permission.MONITOR_INPUT", callingPid, callingUid)
            != 0) {
      throw new SecurityException("can only call from system. " + callingUid);
    }
    return monitorInput(str, i, i2);
  }

  public InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) {
    return monitorGestureInputFiltered(iBinder, str, i, GnssNative.GNSS_AIDING_TYPE_ALL);
  }

  public InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2) {
    if (!checkCallingPermission("android.permission.MONITOR_INPUT", "monitorGestureInput()")) {
      throw new SecurityException("Requires MONITOR_INPUT permission");
    }
    Objects.requireNonNull(str, "name must not be null.");
    Objects.requireNonNull(iBinder, "token must not be null.");
    if (i < 0) {
      throw new IllegalArgumentException("displayId must >= 0.");
    }
    String str2 = "[Gesture Monitor] " + str;
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      SurfaceControl createSurfaceForGestureMonitor =
          this.mWindowManagerCallbacks.createSurfaceForGestureMonitor(str2, i);
      if (createSurfaceForGestureMonitor == null) {
        throw new IllegalArgumentException(
            "Could not create gesture monitor surface on display: " + i);
      }
      InputChannel createSpyWindowGestureMonitor =
          createSpyWindowGestureMonitor(
              iBinder, str2, createSurfaceForGestureMonitor, i, callingPid, callingUid, i2);
      return new InputMonitor(
          createSpyWindowGestureMonitor,
          new InputMonitorHost(createSpyWindowGestureMonitor.getToken()),
          new SurfaceControl(createSurfaceForGestureMonitor, "IMS.monitorGestureInput"));
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public InputChannel createInputChannel(String str) {
    return this.mNative.createInputChannel(str, GnssNative.GNSS_AIDING_TYPE_ALL);
  }

  public InputChannel createInputChannel(String str, int i) {
    return this.mNative.createInputChannel(str, i);
  }

  public void removeInputChannel(IBinder iBinder) {
    Objects.requireNonNull(iBinder, "connectionToken must not be null");
    this.mNative.removeInputChannel(iBinder);
  }

  public void setInputFilter(IInputFilter iInputFilter) {
    synchronized (this.mInputFilterLock) {
      IInputFilter iInputFilter2 = this.mInputFilter;
      if (iInputFilter2 == iInputFilter) {
        return;
      }
      if (iInputFilter2 != null) {
        this.mInputFilter = null;
        this.mInputFilterHost.disconnectLocked();
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
      this.mNative.setInputFilterEnabled(iInputFilter != null);
    }
  }

  public boolean setInTouchMode(boolean z, int i, int i2, boolean z2, int i3) {
    return this.mNative.setInTouchMode(z, i, i2, z2, i3);
  }

  public boolean injectInputEvent(InputEvent inputEvent, int i) {
    return injectInputEventToTarget(inputEvent, i, -1);
  }

  public boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) {
    if (!checkCallingPermission("android.permission.INJECT_EVENTS", "injectInputEvent()", true)) {
      throw new SecurityException(
          "Injecting input events requires the caller (or the source of the instrumentation, if"
              + " any) to have the INJECT_EVENTS permission.");
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
        if (keyEvent.getKeyCode() == 4
            && (this.mDexImeWindowVisibleInDefaultDisplay || this.mBackKeyDownAdjusted)) {
          inputEvent.setDisplayId(2);
          this.mBackKeyDownAdjusted = keyEvent.getAction() == 0;
        }
      }
      int injectInputEvent =
          this.mNative.injectInputEvent(
              inputEvent, z, i2, i, callingPid, callingUid, 30000, 134217728);
      if (injectInputEvent == 0) {
        return true;
      }
      if (injectInputEvent == 1) {
        if (!z) {
          throw new IllegalStateException(
              "Injection should not result in TARGET_MISMATCH when it is not targeted into to a"
                  + " specific uid.");
        }
        throw new IllegalArgumentException(
            "Targeted input event injection from pid "
                + callingPid
                + " was not directed at a window owned by uid "
                + i2
                + ".");
      }
      if (injectInputEvent == 3) {
        Slog.w("InputManager", "Input event injection from pid " + callingPid + " timed out.");
        return false;
      }
      Slog.w("InputManager", "Input event injection from pid " + callingPid + " failed.");
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) {
    Objects.requireNonNull(inputEvent, "event must not be null");
    return this.mNative.verifyInputEvent(inputEvent);
  }

  public String getVelocityTrackerStrategy() {
    return this.mVelocityTrackerStrategy;
  }

  public InputDevice getInputDevice(int i) {
    synchronized (this.mInputDevicesLock) {
      for (InputDevice inputDevice : this.mInputDevices) {
        if (inputDevice.getId() == i) {
          return inputDevice;
        }
      }
      return null;
    }
  }

  public boolean isInputDeviceEnabled(int i) {
    return this.mNative.isInputDeviceEnabled(i);
  }

  public void enableInputDevice(int i) {
    if (!checkCallingPermission("android.permission.DISABLE_INPUT_DEVICE", "enableInputDevice()")) {
      throw new SecurityException("Requires DISABLE_INPUT_DEVICE permission");
    }
    this.mNative.enableInputDevice(i);
  }

  public void disableInputDevice(int i) {
    if (!checkCallingPermission(
        "android.permission.DISABLE_INPUT_DEVICE", "disableInputDevice()")) {
      throw new SecurityException("Requires DISABLE_INPUT_DEVICE permission");
    }
    this.mNative.disableInputDevice(i);
  }

  public int[] getInputDeviceIds() {
    int[] iArr;
    synchronized (this.mInputDevicesLock) {
      int length = this.mInputDevices.length;
      iArr = new int[length];
      for (int i = 0; i < length; i++) {
        iArr[i] = this.mInputDevices[i].getId();
      }
    }
    return iArr;
  }

  public InputDevice[] getInputDevices() {
    InputDevice[] inputDeviceArr;
    synchronized (this.mInputDevicesLock) {
      inputDeviceArr = this.mInputDevices;
    }
    return inputDeviceArr;
  }

  public void registerInputDevicesChangedListener(
      IInputDevicesChangedListener iInputDevicesChangedListener) {
    Objects.requireNonNull(iInputDevicesChangedListener, "listener must not be null");
    synchronized (this.mInputDevicesLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mInputDevicesChangedListeners.get(callingPid) != null) {
        throw new SecurityException(
            "The calling process has already registered an InputDevicesChangedListener.");
      }
      InputDevicesChangedListenerRecord inputDevicesChangedListenerRecord =
          new InputDevicesChangedListenerRecord(callingPid, iInputDevicesChangedListener);
      try {
        iInputDevicesChangedListener.asBinder().linkToDeath(inputDevicesChangedListenerRecord, 0);
        this.mInputDevicesChangedListeners.put(callingPid, inputDevicesChangedListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onInputDevicesChangedListenerDied(int i) {
    synchronized (this.mInputDevicesLock) {
      this.mInputDevicesChangedListeners.remove(i);
    }
  }

  public final void deliverInputDevicesChanged(InputDevice[] inputDeviceArr) {
    this.mTempGamePads.clear();
    this.mTempInputDevicesChangedListenersToNotify.clear();
    synchronized (this.mInputDevicesLock) {
      if (this.mInputDevicesChangedPending) {
        this.mInputDevicesChangedPending = false;
        int size = this.mInputDevicesChangedListeners.size();
        for (int i = 0; i < size; i++) {
          this.mTempInputDevicesChangedListenersToNotify.add(
              (InputDevicesChangedListenerRecord) this.mInputDevicesChangedListeners.valueAt(i));
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
                "device " + inputDevice.getId() + " generation " + inputDevice.getGeneration());
          }
          if (InputUtils.isPogoKeyboard(
              inputDevice.getVendorId(), inputDevice.getProductId(), inputDevice.getName())) {
            z = true;
          }
          if (inputDevice.supportsSource(1025)
              && ((inputDevice.getName() == null || !inputDevice.getName().contains("Test)"))
                  && !containsInputDeviceWithDescriptor(
                      inputDeviceArr, inputDevice.getDescriptor()))) {
            this.mTempGamePads.add(inputDevice);
          }
        }
        for (int i4 = 0; i4 < size; i4++) {
          ((InputDevicesChangedListenerRecord)
                  this.mTempInputDevicesChangedListenersToNotify.get(i4))
              .notifyInputDevicesChanged(iArr);
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
          this.mWindowManagerCallbacks.startGameToolsService(
              inputDevice2.getVendorId(), inputDevice2.getProductId(), true);
        }
        this.mTempGamePads.clear();
      }
    }
  }

  public TouchCalibration getTouchCalibrationForInputDevice(String str, int i) {
    TouchCalibration touchCalibration;
    Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
    synchronized (this.mDataStore) {
      touchCalibration = this.mDataStore.getTouchCalibration(str, i);
    }
    return touchCalibration;
  }

  public void setTouchCalibrationForInputDevice(
      String str, int i, TouchCalibration touchCalibration) {
    if (!checkCallingPermission(
        "android.permission.SET_INPUT_CALIBRATION", "setTouchCalibrationForInputDevice()")) {
      throw new SecurityException("Requires SET_INPUT_CALIBRATION permission");
    }
    Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
    Objects.requireNonNull(touchCalibration, "calibration must not be null");
    if (i < 0 || i > 3) {
      throw new IllegalArgumentException("surfaceRotation value out of bounds");
    }
    synchronized (this.mDataStore) {
      try {
        if (this.mDataStore.setTouchCalibration(str, i, touchCalibration)) {
          this.mNative.reloadCalibration();
        }
      } finally {
        this.mDataStore.saveIfNeeded();
      }
    }
  }

  public long getLastLidEventTimeNanos() {
    return this.mLastLidEventTime;
  }

  public int getLidState() {
    return getSwitchState(-1, -256, 0);
  }

  public void registerLidStateChangedListener(
      ISemLidStateChangedListener iSemLidStateChangedListener) {
    if (!checkCallingPermission(
        "com.samsung.android.permission.LID_STATE", "registerLidStateChangedListener()")) {
      throw new SecurityException("Requires LID_STATE permission");
    }
    if (iSemLidStateChangedListener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mLidStateLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mLidStateChangedListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a LidStateChangedListener.");
      }
      LidStateChangedListenerRecord lidStateChangedListenerRecord =
          new LidStateChangedListenerRecord(callingPid, iSemLidStateChangedListener);
      try {
        iSemLidStateChangedListener.asBinder().linkToDeath(lidStateChangedListenerRecord, 0);
        this.mLidStateChangedListeners.put(callingPid, lidStateChangedListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onLidStateChangedListenerDied(int i) {
    synchronized (this.mLidStateLock) {
      this.mLidStateChangedListeners.remove(i);
    }
  }

  public final void deliverLidStateChanged(long j, boolean z) {
    int size;
    int i;
    this.mTempLidStateChangedListenersToNotify.clear();
    synchronized (this.mLidStateLock) {
      size = this.mLidStateChangedListeners.size();
      for (int i2 = 0; i2 < size; i2++) {
        this.mTempLidStateChangedListenersToNotify.add(
            (LidStateChangedListenerRecord) this.mLidStateChangedListeners.valueAt(i2));
      }
    }
    for (i = 0; i < size; i++) {
      ((LidStateChangedListenerRecord) this.mTempLidStateChangedListenersToNotify.get(i))
          .notifyLidStateChanged(j, z);
    }
  }

  public int isInTabletMode() {
    if (!checkCallingPermission("android.permission.TABLET_MODE", "isInTabletMode()")) {
      throw new SecurityException("Requires TABLET_MODE permission");
    }
    return getSwitchState(-1, -256, 1);
  }

  public int isMicMuted() {
    return getSwitchState(-1, -256, 14);
  }

  public void registerTabletModeChangedListener(
      ITabletModeChangedListener iTabletModeChangedListener) {
    if (!checkCallingPermission(
        "android.permission.TABLET_MODE", "registerTabletModeChangedListener()")) {
      throw new SecurityException("Requires TABLET_MODE_LISTENER permission");
    }
    Objects.requireNonNull(iTabletModeChangedListener, "event must not be null");
    synchronized (this.mTabletModeLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mTabletModeChangedListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a TabletModeChangedListener.");
      }
      TabletModeChangedListenerRecord tabletModeChangedListenerRecord =
          new TabletModeChangedListenerRecord(callingPid, iTabletModeChangedListener);
      try {
        iTabletModeChangedListener.asBinder().linkToDeath(tabletModeChangedListenerRecord, 0);
        this.mTabletModeChangedListeners.put(callingPid, tabletModeChangedListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onTabletModeChangedListenerDied(int i) {
    synchronized (this.mTabletModeLock) {
      this.mTabletModeChangedListeners.remove(i);
    }
  }

  public final void deliverTabletModeChanged(long j, boolean z) {
    int size;
    int i;
    this.mTempTabletModeChangedListenersToNotify.clear();
    synchronized (this.mTabletModeLock) {
      size = this.mTabletModeChangedListeners.size();
      for (int i2 = 0; i2 < size; i2++) {
        this.mTempTabletModeChangedListenersToNotify.add(
            (TabletModeChangedListenerRecord) this.mTabletModeChangedListeners.valueAt(i2));
      }
    }
    for (i = 0; i < size; i++) {
      ((TabletModeChangedListenerRecord) this.mTempTabletModeChangedListenersToNotify.get(i))
          .notifyTabletModeChanged(j, z);
    }
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

  public KeyboardLayout[] getKeyboardLayouts() {
    return this.mKeyboardLayoutManager.getKeyboardLayouts();
  }

  public KeyboardLayout[] getKeyboardLayoutsForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier) {
    return this.mKeyboardLayoutManager.getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
  }

  public KeyboardLayout getKeyboardLayout(String str) {
    return this.mKeyboardLayoutManager.getKeyboardLayout(str);
  }

  public String getCurrentKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier) {
    return this.mKeyboardLayoutManager.getCurrentKeyboardLayoutForInputDevice(
        inputDeviceIdentifier);
  }

  public void setCurrentKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier, String str) {
    super.setCurrentKeyboardLayoutForInputDevice_enforcePermission();
    this.mKeyboardLayoutManager.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
  }

  public String[] getEnabledKeyboardLayoutsForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier) {
    return this.mKeyboardLayoutManager.getEnabledKeyboardLayoutsForInputDevice(
        inputDeviceIdentifier);
  }

  public void addKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier, String str) {
    super.addKeyboardLayoutForInputDevice_enforcePermission();
    this.mKeyboardLayoutManager.addKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
  }

  public void removeKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier, String str) {
    super.removeKeyboardLayoutForInputDevice_enforcePermission();
    this.mKeyboardLayoutManager.removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
  }

  public String getKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype) {
    return this.mKeyboardLayoutManager.getKeyboardLayoutForInputDevice(
        inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
  }

  public void setKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype,
      String str) {
    super.setKeyboardLayoutForInputDevice_enforcePermission();
    this.mKeyboardLayoutManager.setKeyboardLayoutForInputDevice(
        inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype, str);
  }

  public KeyboardLayout[] getKeyboardLayoutListForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype) {
    return this.mKeyboardLayoutManager.getKeyboardLayoutListForInputDevice(
        inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
  }

  public void switchKeyboardLayout(int i, int i2) {
    this.mKeyboardLayoutManager.switchKeyboardLayout(i, i2);
  }

  public int checkInputFeature() {
    if (!this.mSystemReady) {
      int sysfsReadInt = InputUtils.sysfsReadInt("/sys/class/sec/tsp/support_feature", 0);
      Slog.d(
          "InputManager",
          "checkInputFeature: system not ready, features = "
              + String.format("0x%X", Integer.valueOf(sysfsReadInt))
              + ", caller="
              + Debug.getCallers(5)
              + ", pid="
              + Binder.getCallingPid());
      return sysfsReadInt;
    }
    if (this.mTspFeatures < 0) {
      this.mTspFeatures =
          ((SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService"))
              .getTspSupportFeature(1);
    }
    Slog.d(
        "InputManager",
        "checkInputFeature: features = "
            + String.format("0x%X", Integer.valueOf(this.mTspFeatures)));
    return this.mTspFeatures;
  }

  public boolean supportPogoDevice() {
    if (!this.mSystemReady) {
      Slog.d(
          "InputManager",
          "supportPogoDevice: system not ready, , caller="
              + Debug.getCallers(5)
              + ", pid="
              + Binder.getCallingPid());
      return false;
    }
    boolean z =
        ((SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService"))
                .getSupportDevice(31)
            == 0;
    Slog.d("InputManager", "supportPogoDevice: " + z);
    return z;
  }

  public void setFocusedApplication(int i, InputApplicationHandle inputApplicationHandle) {
    this.mNative.setFocusedApplication(i, inputApplicationHandle);
  }

  public void setFocusedDisplay(int i) {
    this.mNative.setFocusedDisplay(i);
  }

  public void onDisplayRemoved(int i) {
    Context context = this.mPointerIconDisplayContext;
    if (context != null && context.getDisplay().getDisplayId() == i) {
      this.mPointerIconDisplayContext = null;
    }
    updateAdditionalDisplayInputProperties(
        i,
        new Consumer() { // from class:
                         // com.android.server.input.InputManagerService$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((InputManagerService.AdditionalDisplayInputProperties) obj).reset();
          }
        });
    this.mNative.displayRemoved(i);
  }

  public void requestPointerCapture(IBinder iBinder, boolean z) {
    Objects.requireNonNull(iBinder, "event must not be null");
    this.mNative.requestPointerCapture(iBinder, z);
  }

  public void setInputDispatchMode(boolean z, boolean z2) {
    this.mNative.setInputDispatchMode(z, z2);
  }

  public void setSystemUiLightsOut(boolean z) {
    this.mNative.setSystemUiLightsOut(z);
  }

  public void setSystemUiLightsOut(boolean z, int i) {
    this.mNative.setSystemUiLightsOutForDisplay(z, i);
  }

  public boolean transferTouchFocus(
      InputChannel inputChannel, InputChannel inputChannel2, boolean z) {
    return this.mNative.transferTouchFocus(inputChannel.getToken(), inputChannel2.getToken(), z);
  }

  public boolean transferTouchFocus(IBinder iBinder, IBinder iBinder2) {
    Objects.requireNonNull(iBinder);
    Objects.requireNonNull(iBinder2);
    return this.mNative.transferTouchFocus(iBinder, iBinder2, false);
  }

  public void tryPointerSpeed(int i) {
    if (!checkCallingPermission("android.permission.SET_POINTER_SPEED", "tryPointerSpeed()")) {
      throw new SecurityException("Requires SET_POINTER_SPEED permission");
    }
    if (i < -7 || i > 7) {
      throw new IllegalArgumentException("speed out of range");
    }
    setPointerSpeedUnchecked(i);
  }

  public final void setPointerSpeedUnchecked(int i) {
    this.mNative.setPointerSpeed(Math.min(Math.max(i, -7), 7));
  }

  public final void setPointerAcceleration(final float f, int i) {
    updateAdditionalDisplayInputProperties(
        i,
        new Consumer() { // from class:
                         // com.android.server.input.InputManagerService$$ExternalSyntheticLambda7
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((InputManagerService.AdditionalDisplayInputProperties) obj).pointerAcceleration = f;
          }
        });
  }

  public final void setPointerIconVisible(final boolean z, int i) {
    updateAdditionalDisplayInputProperties(
        i,
        new Consumer() { // from class:
                         // com.android.server.input.InputManagerService$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((InputManagerService.AdditionalDisplayInputProperties) obj).pointerIconVisible = z;
          }
        });
  }

  public final boolean updatePointerDisplayIdLocked(int i) {
    if (this.mRequestedPointerDisplayId == i) {
      return false;
    }
    this.mRequestedPointerDisplayId = i;
    this.mNative.setPointerDisplayId(i);
    applyAdditionalDisplayInputProperties();
    return true;
  }

  public final void handlePointerDisplayIdChanged(
      PointerDisplayIdChangedArgs pointerDisplayIdChangedArgs) {
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      this.mAcknowledgedPointerDisplayId = pointerDisplayIdChangedArgs.mPointerDisplayId;
      this.mAdditionalDisplayInputPropertiesLock.notifyAll();
    }
    this.mWindowManagerCallbacks.notifyPointerDisplayIdChanged(
        pointerDisplayIdChangedArgs.mPointerDisplayId,
        pointerDisplayIdChangedArgs.mXPosition,
        pointerDisplayIdChangedArgs.mYPosition);
  }

  public final boolean setVirtualMousePointerDisplayIdBlocking(int i) {
    boolean z = i == -1;
    int pointerDisplayId = z ? this.mWindowManagerCallbacks.getPointerDisplayId() : i;
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      this.mOverriddenPointerDisplayId = i;
      if (!updatePointerDisplayIdLocked(pointerDisplayId)
          && this.mAcknowledgedPointerDisplayId == pointerDisplayId) {
        return true;
      }
      if (z && this.mAcknowledgedPointerDisplayId == -1) {
        return true;
      }
      try {
        this.mAdditionalDisplayInputPropertiesLock.wait(5000L);
      } catch (InterruptedException unused) {
      }
      return z || this.mAcknowledgedPointerDisplayId == i;
    }
  }

  public final int getVirtualMousePointerDisplayId() {
    int i;
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      i = this.mOverriddenPointerDisplayId;
    }
    return i;
  }

  public final void setDisplayEligibilityForPointerCapture(int i, boolean z) {
    this.mNative.setDisplayEligibilityForPointerCapture(i, z);
  }

  public class VibrationInfo {
    public final int[] mAmplitudes;
    public final long[] mPattern;
    public final int mRepeat;

    public long[] getPattern() {
      return this.mPattern;
    }

    public int[] getAmplitudes() {
      return this.mAmplitudes;
    }

    public int getRepeatIndex() {
      return this.mRepeat;
    }

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
          StepSegment stepSegment = (VibrationEffectSegment) composed.getSegments().get(i3);
          i = composed.getRepeatIndex() == i3 ? i2 : i;
          if (!(stepSegment instanceof StepSegment)) {
            Slog.w("InputManager", "Input devices don't support segment " + stepSegment);
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
        Slog.w("InputManager", "Only oneshot and step waveforms are supported on input devices");
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
      throw new ArrayIndexOutOfBoundsException(
          "Repeat index " + i + " must be within the bounds of the pattern.length " + jArr2.length);
    }
  }

  public final VibratorToken getVibratorToken(int i, IBinder iBinder) {
    VibratorToken vibratorToken;
    synchronized (this.mVibratorLock) {
      vibratorToken = (VibratorToken) this.mVibratorTokens.get(iBinder);
      if (vibratorToken == null) {
        int i2 = this.mNextVibratorTokenValue;
        this.mNextVibratorTokenValue = i2 + 1;
        vibratorToken = new VibratorToken(i, iBinder, i2);
        try {
          iBinder.linkToDeath(vibratorToken, 0);
          this.mVibratorTokens.put(iBinder, vibratorToken);
        } catch (RemoteException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return vibratorToken;
  }

  public void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) {
    VibrationInfo vibrationInfo = new VibrationInfo(vibrationEffect);
    VibratorToken vibratorToken = getVibratorToken(i, iBinder);
    synchronized (vibratorToken) {
      vibratorToken.mVibrating = true;
      this.mNative.vibrate(
          i,
          vibrationInfo.getPattern(),
          vibrationInfo.getAmplitudes(),
          vibrationInfo.getRepeatIndex(),
          vibratorToken.mTokenValue);
    }
  }

  public int[] getVibratorIds(int i) {
    return this.mNative.getVibratorIds(i);
  }

  public boolean isVibrating(int i) {
    return this.mNative.isVibrating(i);
  }

  public void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) {
    VibratorToken vibratorToken = getVibratorToken(i, iBinder);
    synchronized (vibratorToken) {
      if (!(combinedVibration instanceof CombinedVibration.Mono)
          && !(combinedVibration instanceof CombinedVibration.Stereo)) {
        Slog.e("InputManager", "Only Mono and Stereo effects are supported");
        return;
      }
      vibratorToken.mVibrating = true;
      if (combinedVibration instanceof CombinedVibration.Mono) {
        VibrationInfo vibrationInfo =
            new VibrationInfo(((CombinedVibration.Mono) combinedVibration).getEffect());
        this.mNative.vibrate(
            i,
            vibrationInfo.getPattern(),
            vibrationInfo.getAmplitudes(),
            vibrationInfo.getRepeatIndex(),
            vibratorToken.mTokenValue);
      } else if (combinedVibration instanceof CombinedVibration.Stereo) {
        SparseArray effects = ((CombinedVibration.Stereo) combinedVibration).getEffects();
        SparseArray sparseArray = new SparseArray(effects.size());
        long[] jArr = new long[0];
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < effects.size(); i3++) {
          VibrationInfo vibrationInfo2 = new VibrationInfo((VibrationEffect) effects.valueAt(i3));
          if (jArr.length == 0) {
            jArr = vibrationInfo2.getPattern();
          }
          if (i2 == Integer.MIN_VALUE) {
            i2 = vibrationInfo2.getRepeatIndex();
          }
          sparseArray.put(effects.keyAt(i3), vibrationInfo2.getAmplitudes());
        }
        this.mNative.vibrateCombined(i, jArr, sparseArray, i2, vibratorToken.mTokenValue);
      }
    }
  }

  public void cancelVibrate(int i, IBinder iBinder) {
    synchronized (this.mVibratorLock) {
      VibratorToken vibratorToken = (VibratorToken) this.mVibratorTokens.get(iBinder);
      if (vibratorToken != null && vibratorToken.mDeviceId == i) {
        cancelVibrateIfNeeded(vibratorToken);
      }
    }
  }

  public void onVibratorTokenDied(VibratorToken vibratorToken) {
    synchronized (this.mVibratorLock) {
      this.mVibratorTokens.remove(vibratorToken.mToken);
    }
    cancelVibrateIfNeeded(vibratorToken);
  }

  public final void cancelVibrateIfNeeded(VibratorToken vibratorToken) {
    synchronized (vibratorToken) {
      if (vibratorToken.mVibrating) {
        this.mNative.cancelVibrate(vibratorToken.mDeviceId, vibratorToken.mTokenValue);
        vibratorToken.mVibrating = false;
      }
    }
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

  public final void notifyVibratorStateListenerLocked(
      int i, IVibratorStateListener iVibratorStateListener) {
    try {
      iVibratorStateListener.onVibrating(this.mIsVibrating.get(i));
    } catch (RemoteException | RuntimeException e) {
      Slog.e("InputManager", "Vibrator state listener failed to call", e);
    }
  }

  public boolean registerVibratorStateListener(
      int i, IVibratorStateListener iVibratorStateListener) {
    RemoteCallbackList remoteCallbackList;
    Objects.requireNonNull(iVibratorStateListener, "listener must not be null");
    synchronized (this.mVibratorLock) {
      if (!this.mVibratorStateListeners.contains(i)) {
        remoteCallbackList = new RemoteCallbackList();
        this.mVibratorStateListeners.put(i, remoteCallbackList);
      } else {
        remoteCallbackList = (RemoteCallbackList) this.mVibratorStateListeners.get(i);
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!remoteCallbackList.register(iVibratorStateListener)) {
          Slog.e(
              "InputManager",
              "Could not register vibrator state listener " + iVibratorStateListener);
          return false;
        }
        notifyVibratorStateListenerLocked(i, iVibratorStateListener);
        return true;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public boolean unregisterVibratorStateListener(
      int i, IVibratorStateListener iVibratorStateListener) {
    synchronized (this.mVibratorLock) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!this.mVibratorStateListeners.contains(i)) {
          Slog.w("InputManager", "Vibrator state listener " + i + " doesn't exist");
          return false;
        }
        return ((RemoteCallbackList) this.mVibratorStateListeners.get(i))
            .unregister(iVibratorStateListener);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public IInputDeviceBatteryState getBatteryState(int i) {
    return this.mBatteryController.getBatteryState(i);
  }

  public void setPointerIconType(int i) {
    if (i == -1) {
      throw new IllegalArgumentException("Use setCustomPointerIcon to set custom pointers");
    }
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      this.mPointerIcon = null;
      this.mPointerIconType = i;
      if (this.mCurrentDisplayProperties.pointerIconVisible) {
        this.mNative.setPointerIconType(i);
        sendPointerIconChanged(i, null);
      }
    }
  }

  public void setCustomPointerIcon(PointerIcon pointerIcon) {
    Objects.requireNonNull(pointerIcon);
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      this.mPointerIconType = -1;
      this.mPointerIcon = pointerIcon;
      if (this.mCurrentDisplayProperties.pointerIconVisible) {
        this.mNative.setCustomPointerIcon(pointerIcon);
        sendPointerIconChanged(-1, pointerIcon);
      }
    }
  }

  public void setCustomHoverIcon(PointerIcon pointerIcon) {
    Objects.requireNonNull(pointerIcon);
    int virtualMousePointerDisplayId = getVirtualMousePointerDisplayId();
    if (virtualMousePointerDisplayId == -1) {
      virtualMousePointerDisplayId = this.mWindowManagerCallbacks.getPointerDisplayId();
    }
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      this.mNative.setHoverIcon(pointerIcon, virtualMousePointerDisplayId);
    }
    sendPointerIconChanged(-1, pointerIcon);
  }

  public void addPortAssociation(String str, int i) {
    if (!checkCallingPermission(
        "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "addPortAssociation()")) {
      throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
    }
    Objects.requireNonNull(str);
    synchronized (this.mAssociationsLock) {
      this.mRuntimeAssociations.put(str, Integer.valueOf(i));
    }
    this.mNative.notifyPortAssociationsChanged();
  }

  public void removePortAssociation(String str) {
    if (!checkCallingPermission(
        "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "removePortAssociation()")) {
      throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
    }
    Objects.requireNonNull(str);
    synchronized (this.mAssociationsLock) {
      this.mRuntimeAssociations.remove(str);
    }
    this.mNative.notifyPortAssociationsChanged();
  }

  public void addUniqueIdAssociation(String str, String str2) {
    if (!checkCallingPermission(
        "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "addUniqueIdAssociation()")) {
      throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
    }
    Objects.requireNonNull(str);
    Objects.requireNonNull(str2);
    synchronized (this.mAssociationsLock) {
      this.mUniqueIdAssociations.put(str, str2);
    }
    this.mNative.changeUniqueIdAssociation();
  }

  public void removeUniqueIdAssociation(String str) {
    if (!checkCallingPermission(
        "android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "removeUniqueIdAssociation()")) {
      throw new SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
    }
    Objects.requireNonNull(str);
    synchronized (this.mAssociationsLock) {
      this.mUniqueIdAssociations.remove(str);
    }
    this.mNative.changeUniqueIdAssociation();
  }

  public void setTypeAssociationInternal(String str, String str2) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(str2);
    synchronized (this.mAssociationsLock) {
      this.mDeviceTypeAssociations.put(str, str2);
    }
    this.mNative.changeTypeAssociation();
  }

  public void unsetTypeAssociationInternal(String str) {
    Objects.requireNonNull(str);
    synchronized (this.mAssociationsLock) {
      this.mDeviceTypeAssociations.remove(str);
    }
    this.mNative.changeTypeAssociation();
  }

  public final void addKeyboardLayoutAssociation(String str, String str2, String str3) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(str2);
    Objects.requireNonNull(str3);
    synchronized (this.mAssociationsLock) {
      this.mKeyboardLayoutAssociations.put(
          str, TextUtils.formatSimple("%s,%s", new Object[] {str2, str3}));
    }
    this.mNative.changeKeyboardLayoutAssociation();
  }

  public final void removeKeyboardLayoutAssociation(String str) {
    Objects.requireNonNull(str);
    synchronized (this.mAssociationsLock) {
      this.mKeyboardLayoutAssociations.remove(str);
    }
    this.mNative.changeKeyboardLayoutAssociation();
  }

  public InputSensorInfo[] getSensorList(int i) {
    return this.mNative.getSensorList(i);
  }

  public boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) {
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
    }
  }

  public void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) {
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
      int callingPid = Binder.getCallingPid();
      if (this.mSensorEventListeners.get(callingPid) != null) {
        if (((SensorEventListenerRecord) this.mSensorEventListeners.get(callingPid))
                .getListener()
                .asBinder()
            != iInputSensorEventListener.asBinder()) {
          throw new IllegalArgumentException("listener is not registered");
        }
        this.mSensorEventListeners.remove(callingPid);
      }
    }
  }

  public boolean flushSensor(int i, int i2) {
    synchronized (this.mSensorEventLock) {
      if (((SensorEventListenerRecord) this.mSensorEventListeners.get(Binder.getCallingPid()))
          == null) {
        return false;
      }
      return this.mNative.flushSensor(i, i2);
    }
  }

  public boolean enableSensor(int i, int i2, int i3, int i4) {
    boolean enableSensor;
    synchronized (this.mInputDevicesLock) {
      enableSensor = this.mNative.enableSensor(i, i2, i3, i4);
    }
    return enableSensor;
  }

  public void disableSensor(int i, int i2) {
    synchronized (this.mInputDevicesLock) {
      this.mNative.disableSensor(i, i2);
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Light token died.");
      }
      synchronized (InputManagerService.this.mLightLock) {
        InputManagerService.this.closeLightSession(this.mDeviceId, this.mToken);
        InputManagerService.this.mLightSessions.remove(this.mToken);
      }
    }
  }

  public List getLights(int i) {
    return this.mNative.getLights(i);
  }

  public final void setLightStateInternal(int i, Light light, LightState lightState) {
    Objects.requireNonNull(light, "light does not exist");
    if (DEBUG) {
      Slog.d(
          "InputManager",
          "setLightStateInternal device " + i + " light " + light + "lightState " + lightState);
    }
    if (light.getType() == 10002) {
      this.mNative.setLightPlayerId(i, light.getId(), lightState.getPlayerId());
    } else {
      this.mNative.setLightColor(i, light.getId(), lightState.getColor());
    }
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

  public void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) {
    boolean z = true;
    Preconditions.checkArgument(
        iArr.length == lightStateArr.length, "lights and light states are not same length");
    synchronized (this.mLightLock) {
      LightSession lightSession = (LightSession) this.mLightSessions.get(iBinder);
      Preconditions.checkArgument(lightSession != null, "not registered");
      if (lightSession.mDeviceId != i) {
        z = false;
      }
      Preconditions.checkState(z, "Incorrect device ID");
      lightSession.mLightIds = (int[]) iArr.clone();
      lightSession.mLightStates = (LightState[]) lightStateArr.clone();
      if (DEBUG) {
        Slog.d("InputManager", "setLightStates for " + lightSession.mOpPkg + " device " + i);
      }
    }
    setLightStatesInternal(i, iArr, lightStateArr);
  }

  public LightState getLightState(int i, int i2) {
    LightState lightState;
    synchronized (this.mLightLock) {
      lightState =
          new LightState(this.mNative.getLightColor(i, i2), this.mNative.getLightPlayerId(i, i2));
    }
    return lightState;
  }

  public void openLightSession(int i, String str, IBinder iBinder) {
    Objects.requireNonNull(iBinder);
    synchronized (this.mLightLock) {
      Preconditions.checkState(this.mLightSessions.get(iBinder) == null, "already registered");
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

  public void closeLightSession(int i, IBinder iBinder) {
    Objects.requireNonNull(iBinder);
    synchronized (this.mLightLock) {
      LightSession lightSession = (LightSession) this.mLightSessions.get(iBinder);
      Preconditions.checkState(lightSession != null, "not registered");
      Arrays.fill(lightSession.mLightStates, new LightState(0));
      setLightStatesInternal(i, lightSession.mLightIds, lightSession.mLightStates);
      this.mLightSessions.remove(iBinder);
      if (!this.mLightSessions.isEmpty()) {
        LightSession lightSession2 = (LightSession) this.mLightSessions.valueAt(0);
        setLightStatesInternal(i, lightSession2.mLightIds, lightSession2.mLightStates);
      }
    }
  }

  public void cancelCurrentTouch() {
    if (!checkCallingPermission("android.permission.MONITOR_INPUT", "cancelCurrentTouch()")) {
      throw new SecurityException("Requires MONITOR_INPUT permission");
    }
    this.mNative.cancelCurrentTouch();
  }

  public void registerBatteryListener(
      int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) {
    Objects.requireNonNull(iInputDeviceBatteryListener);
    this.mBatteryController.registerBatteryListener(
        i, iInputDeviceBatteryListener, Binder.getCallingPid());
  }

  public void unregisterBatteryListener(
      int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) {
    Objects.requireNonNull(iInputDeviceBatteryListener);
    this.mBatteryController.unregisterBatteryListener(
        i, iInputDeviceBatteryListener, Binder.getCallingPid());
  }

  public String getInputDeviceBluetoothAddress(int i) {
    super.getInputDeviceBluetoothAddress_enforcePermission();
    String bluetoothAddress = this.mNative.getBluetoothAddress(i);
    if (bluetoothAddress == null) {
      return null;
    }
    if (BluetoothAdapter.checkBluetoothAddress(bluetoothAddress)) {
      return bluetoothAddress;
    }
    throw new IllegalStateException(
        "The Bluetooth address of input device "
            + i
            + " should not be invalid: address="
            + bluetoothAddress);
  }

  public void pilferPointers(IBinder iBinder) {
    super.pilferPointers_enforcePermission();
    Objects.requireNonNull(iBinder);
    this.mNative.pilferPointers(iBinder);
  }

  public void registerKeyboardBacklightListener(
      IKeyboardBacklightListener iKeyboardBacklightListener) {
    super.registerKeyboardBacklightListener_enforcePermission();
    Objects.requireNonNull(iKeyboardBacklightListener);
    this.mKeyboardBacklightController.registerKeyboardBacklightListener(
        iKeyboardBacklightListener, Binder.getCallingPid());
  }

  public void unregisterKeyboardBacklightListener(
      IKeyboardBacklightListener iKeyboardBacklightListener) {
    super.unregisterKeyboardBacklightListener_enforcePermission();
    Objects.requireNonNull(iKeyboardBacklightListener);
    this.mKeyboardBacklightController.unregisterKeyboardBacklightListener(
        iKeyboardBacklightListener, Binder.getCallingPid());
  }

  public HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) {
    return this.mDisplayManagerInternal.getHostUsiVersion(i);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "InputManager", printWriter)) {
      final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
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
      this.mBatteryController.dump(indentingPrintWriter);
      this.mKeyboardBacklightController.dump(indentingPrintWriter);
      this.mKeyboardLayoutManager.dump(indentingPrintWriter);
      synchronized (this.mAssociationsLock) {
        if (!this.mKeyboardLayoutAssociations.isEmpty()) {
          indentingPrintWriter.println("Keyboard layout Associations:");
          this.mKeyboardLayoutAssociations.forEach(
              new BiConsumer() { // from class:
                                 // com.android.server.input.InputManagerService$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  InputManagerService.lambda$dump$3(
                      indentingPrintWriter, (String) obj, (String) obj2);
                }
              });
        }
      }
      synchronized (this) {
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
      }
    }
  }

  public static /* synthetic */ void lambda$dump$3(
      IndentingPrintWriter indentingPrintWriter, String str, String str2) {
    indentingPrintWriter.print("  port: " + str);
    indentingPrintWriter.println("  layout: " + str2);
  }

  public final void dumpAssociations(final IndentingPrintWriter indentingPrintWriter) {
    if (!this.mStaticAssociations.isEmpty()) {
      indentingPrintWriter.println("Static Associations:");
      this.mStaticAssociations.forEach(
          new BiConsumer() { // from class:
                             // com.android.server.input.InputManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              InputManagerService.lambda$dumpAssociations$4(
                  indentingPrintWriter, (String) obj, (Integer) obj2);
            }
          });
    }
    synchronized (this.mAssociationsLock) {
      if (!this.mRuntimeAssociations.isEmpty()) {
        indentingPrintWriter.println("Runtime Associations:");
        this.mRuntimeAssociations.forEach(
            new BiConsumer() { // from class:
                               // com.android.server.input.InputManagerService$$ExternalSyntheticLambda4
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                InputManagerService.lambda$dumpAssociations$5(
                    indentingPrintWriter, (String) obj, (Integer) obj2);
              }
            });
      }
      if (!this.mUniqueIdAssociations.isEmpty()) {
        indentingPrintWriter.println("Unique Id Associations:");
        this.mUniqueIdAssociations.forEach(
            new BiConsumer() { // from class:
                               // com.android.server.input.InputManagerService$$ExternalSyntheticLambda5
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                InputManagerService.lambda$dumpAssociations$6(
                    indentingPrintWriter, (String) obj, (String) obj2);
              }
            });
      }
      if (!this.mDeviceTypeAssociations.isEmpty()) {
        indentingPrintWriter.println("Type Associations:");
        this.mDeviceTypeAssociations.forEach(
            new BiConsumer() { // from class:
                               // com.android.server.input.InputManagerService$$ExternalSyntheticLambda6
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                InputManagerService.lambda$dumpAssociations$7(
                    indentingPrintWriter, (String) obj, (String) obj2);
              }
            });
      }
    }
  }

  public static /* synthetic */ void lambda$dumpAssociations$4(
      IndentingPrintWriter indentingPrintWriter, String str, Integer num) {
    indentingPrintWriter.print("  port: " + str);
    indentingPrintWriter.println("  display: " + num);
  }

  public static /* synthetic */ void lambda$dumpAssociations$5(
      IndentingPrintWriter indentingPrintWriter, String str, Integer num) {
    indentingPrintWriter.print("  port: " + str);
    indentingPrintWriter.println("  display: " + num);
  }

  public static /* synthetic */ void lambda$dumpAssociations$6(
      IndentingPrintWriter indentingPrintWriter, String str, String str2) {
    indentingPrintWriter.print("  port: " + str);
    indentingPrintWriter.println("  uniqueId: " + str2);
  }

  public static /* synthetic */ void lambda$dumpAssociations$7(
      IndentingPrintWriter indentingPrintWriter, String str, String str2) {
    indentingPrintWriter.print("  port: " + str);
    indentingPrintWriter.println("  type: " + str2);
  }

  public final void dumpSpyWindowGestureMonitors(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mInputMonitors) {
      if (this.mInputMonitors.isEmpty()) {
        return;
      }
      indentingPrintWriter.println("Gesture Monitors (implemented as spy windows):");
      Iterator it = this.mInputMonitors.values().iterator();
      int i = 0;
      while (it.hasNext()) {
        indentingPrintWriter
            .append("  " + i + ": ")
            .println(((GestureMonitorSpyWindow) it.next()).dump());
        i++;
      }
    }
  }

  public final void dumpDisplayInputPropertiesValues(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      if (this.mAdditionalDisplayInputProperties.size() != 0) {
        indentingPrintWriter.println("mAdditionalDisplayInputProperties:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mAdditionalDisplayInputProperties.size(); i++) {
          indentingPrintWriter.println(
              "displayId: " + this.mAdditionalDisplayInputProperties.keyAt(i));
          AdditionalDisplayInputProperties additionalDisplayInputProperties =
              (AdditionalDisplayInputProperties) this.mAdditionalDisplayInputProperties.valueAt(i);
          indentingPrintWriter.println(
              "pointerAcceleration: " + additionalDisplayInputProperties.pointerAcceleration);
          indentingPrintWriter.println(
              "pointerIconVisible: " + additionalDisplayInputProperties.pointerIconVisible);
        }
        indentingPrintWriter.decreaseIndent();
      }
      if (this.mOverriddenPointerDisplayId != -1) {
        indentingPrintWriter.println(
            "mOverriddenPointerDisplayId: " + this.mOverriddenPointerDisplayId);
      }
      indentingPrintWriter.println(
          "mAcknowledgedPointerDisplayId=" + this.mAcknowledgedPointerDisplayId);
      indentingPrintWriter.println("mRequestedPointerDisplayId=" + this.mRequestedPointerDisplayId);
      indentingPrintWriter.println(
          "mPointerIconType=" + PointerIcon.typeToString(this.mPointerIconType));
      indentingPrintWriter.println("mPointerIcon=" + this.mPointerIcon);
    }
  }

  public final boolean checkCallingPermission(String str, String str2) {
    return checkCallingPermission(str, str2, false);
  }

  public final boolean checkCallingPermission(String str, String str2, boolean z) {
    if (Binder.getCallingPid() == Process.myPid()
        || this.mContext.checkCallingPermission(str) == 0) {
      return true;
    }
    if (z) {
      ActivityManagerInternal activityManagerInternal =
          (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
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
    Slog.w(
        "InputManager",
        "Permission Denial: "
            + str2
            + " from pid="
            + Binder.getCallingPid()
            + ", uid="
            + Binder.getCallingUid()
            + " requires "
            + str);
    return false;
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
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
    this.mNative.monitor();
  }

  public void setCoverVerify(int i) {
    Log.d("InputManager", "setCoverVerify = " + i);
    this.mNative.setCoverVerify(i);
  }

  public void reloadPointerIcons() {
    Log.d("InputManager", "reloadPointerIcons");
    this.mNative.reloadPointerIcons();
  }

  public final void notifyConfigurationChanged(long j) {
    this.mWindowManagerCallbacks.notifyConfigurationChanged();
  }

  public final void notifyInputDevicesChanged(InputDevice[] inputDeviceArr) {
    synchronized (this.mInputDevicesLock) {
      if (!this.mInputDevicesChangedPending) {
        this.mInputDevicesChangedPending = true;
        this.mHandler.obtainMessage(1, this.mInputDevices).sendToTarget();
      }
      this.mInputDevices = inputDeviceArr;
    }
  }

  public final void notifySwitch(long j, int i, int i2) {
    Slog.d(
        "InputManager",
        "notifySwitch: values=" + Integer.toHexString(i) + ", mask=" + Integer.toHexString(i2));
    SomeArgs obtain = SomeArgs.obtain();
    obtain.argi1 = i;
    obtain.argi2 = i2;
    obtain.argi3 = 0;
    obtain.argi4 = 0;
    this.mHandler.obtainMessage(107, obtain).sendToTarget();
    if ((i2 & 1) != 0) {
      int i3 = i & 1;
      boolean z = i3 == 0;
      synchronized (this.mLidSwitchLock) {
        if (this.mSystemReady) {
          for (int i4 = 0; i4 < this.mLidSwitchCallbacks.size(); i4++) {
            ((InputManagerInternal.LidSwitchCallback) this.mLidSwitchCallbacks.get(i4))
                .notifyLidSwitchChanged(j, z);
          }
        }
      }
      SomeArgs obtain2 = SomeArgs.obtain();
      obtain2.argi1 = (int) (j & (-1));
      obtain2.argi2 = (int) (j >> 32);
      obtain2.arg1 = Boolean.valueOf(i3 == 0);
      this.mHandler.obtainMessage(103, obtain2).sendToTarget();
      this.mLastLidEventTime = j;
    }
    if ((i2 & 524288) != 0) {
      this.mWindowManagerCallbacks.notifyPenSwitchChanged(j, (524288 & i) == 0, false);
    }
    if ((i2 & 1048576) != 0) {
      this.mWindowManagerCallbacks.notifyPenSwitchChanged(j, (1048576 & i) == 0, true);
    }
    if ((i2 & 1073741824) != 0) {
      this.mSpenCoverAttached = (1073741824 & i) != 0;
      updateWacomMode();
    }
    if ((i2 & 512) != 0) {
      boolean z2 = (i & 512) != 0;
      this.mWindowManagerCallbacks.notifyCameraLensCoverSwitchChanged(j, z2);
      setSensorPrivacy(2, z2);
    }
    if (this.mUseDevInputEventForAudioJack && (i2 & 212) != 0) {
      this.mWiredAccessoryCallbacks.notifyWiredAccessoryChanged(j, i, i2);
    }
    if ((i2 & 2) != 0) {
      SomeArgs obtain3 = SomeArgs.obtain();
      obtain3.argi1 = (int) ((-1) & j);
      obtain3.argi2 = (int) (j >> 32);
      obtain3.arg1 = Boolean.valueOf((i & 2) != 0);
      this.mHandler.obtainMessage(3, obtain3).sendToTarget();
    }
    if ((i2 & 16384) != 0) {
      boolean z3 = (i & 16384) != 0;
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
      SecAccessoryManagerCallbacks secAccessoryManagerCallbacks =
          this.mSecAccessoryManagerCallbacks;
      if (secAccessoryManagerCallbacks != null) {
        secAccessoryManagerCallbacks.notifyCoverSwitchStateChanged(j, z4);
      }
    }
    if ((i2 & 134217728) != 0) {
      boolean z5 = (134217728 & i) != 0;
      SecAccessoryManagerCallbacks secAccessoryManagerCallbacks2 =
          this.mSecAccessoryManagerCallbacks;
      if (secAccessoryManagerCallbacks2 != null) {
        secAccessoryManagerCallbacks2.notifyUnverifiedCoverAttachChanged(j, z5);
      } else {
        Log.d("InputManager", "UnVerifiedCoverAttachCallbacks is not registered");
      }
      DesktopModeServiceCallbacks desktopModeServiceCallbacks = this.mDesktopModeServiceCallbacks;
      if (desktopModeServiceCallbacks != null) {
        desktopModeServiceCallbacks.notifyUnverifiedCoverAttachChanged(j, z5);
      } else {
        Log.d(
            "InputManager",
            "UnVerifiedCoverAttachCallbacks is not registered for DesktopModeService");
      }
    }
    if ((i2 & Integer.MIN_VALUE) != 0) {
      this.mInputMethodManagerCallbacks.notifyKeyboardCoverBackfolded(
          j, (Integer.MIN_VALUE & i) != 0);
    }
    if ((i2 & 536870912) != 0) {
      if ((536870912 & i) != 0) {
        showingTouchSensitivityNotificationIfNeeded();
      }
    }
  }

  public final void updateWacomMode() {
    int i;
    if (!this.mSystemReady) {
      Log.d("InputManager", "updateWacomMode: system not ready");
      return;
    }
    if (this.mPogoKeyboardConnected) {
      i = 2;
    } else {
      i = this.mSpenCoverAttached ? 1 : 0;
    }
    if (i != this.mLastWacomMode) {
      SemInputDeviceManager semInputDeviceManager =
          (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
      Log.d("InputManager", "updateWacomMode: " + this.mLastWacomMode + " -> " + i);
      semInputDeviceManager.setSpenCoverType(i);
      this.mLastWacomMode = i;
    }
  }

  public final void setSensorPrivacy(int i, boolean z) {
    ((SensorPrivacyManagerInternal) LocalServices.getService(SensorPrivacyManagerInternal.class))
        .setPhysicalToggleSensorPrivacy(-2, i, z);
  }

  public final void notifyInputChannelBroken(IBinder iBinder) {
    synchronized (this.mInputMonitors) {
      if (this.mInputMonitors.containsKey(iBinder)) {
        removeSpyWindowGestureMonitor(iBinder);
      }
    }
    this.mWindowManagerCallbacks.notifyInputChannelBroken(iBinder);
  }

  public final void notifyFocusChanged(IBinder iBinder, IBinder iBinder2) {
    this.mWindowManagerCallbacks.notifyFocusChanged(iBinder, iBinder2);
  }

  public final void notifyDropWindow(IBinder iBinder, float f, float f2) {
    this.mWindowManagerCallbacks.notifyDropWindow(iBinder, f, f2);
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
    this.mToastDialog.showUntrustedTouch(this.mContext.getResources().getString(17043263, str));
  }

  public final void notifyPogoKeyboardNotMatch() {
    if (this.mBootCompleted) {
      this.mToastDialog.showWarningWrongPogo(this.mContext.getResources().getString(17043264));
    } else {
      this.mNotifyPogoKeyboardNotMatchPending = true;
    }
  }

  public final void notifyNoFocusedWindowAnr(InputApplicationHandle inputApplicationHandle) {
    this.mWindowManagerCallbacks.notifyNoFocusedWindowAnr(inputApplicationHandle);
  }

  public final void notifyWindowUnresponsive(IBinder iBinder, int i, boolean z, String str) {
    this.mWindowManagerCallbacks.notifyWindowUnresponsive(
        iBinder, z ? OptionalInt.of(i) : OptionalInt.empty(), str);
  }

  public final void notifyWindowResponsive(IBinder iBinder, int i, boolean z) {
    this.mWindowManagerCallbacks.notifyWindowResponsive(
        iBinder, z ? OptionalInt.of(i) : OptionalInt.empty());
  }

  public final void notifySensorEvent(int i, int i2, int i3, long j, float[] fArr) {
    int size;
    if (DEBUG) {
      Slog.d(
          "InputManager",
          "notifySensorEvent: deviceId="
              + i
              + " sensorType="
              + i2
              + " values="
              + Arrays.toString(fArr));
    }
    this.mSensorEventListenersToNotify.clear();
    synchronized (this.mSensorEventLock) {
      size = this.mSensorEventListeners.size();
      for (int i4 = 0; i4 < size; i4++) {
        this.mSensorEventListenersToNotify.add(
            (SensorEventListenerRecord) this.mSensorEventListeners.valueAt(i4));
      }
    }
    for (int i5 = 0; i5 < size; i5++) {
      ((SensorEventListenerRecord) this.mSensorEventListenersToNotify.get(i5))
          .notifySensorEvent(i, i2, i3, j, fArr);
    }
    this.mSensorEventListenersToNotify.clear();
  }

  public final void notifySensorAccuracy(int i, int i2, int i3) {
    int size;
    int i4;
    this.mSensorAccuracyListenersToNotify.clear();
    synchronized (this.mSensorEventLock) {
      size = this.mSensorEventListeners.size();
      for (int i5 = 0; i5 < size; i5++) {
        this.mSensorAccuracyListenersToNotify.add(
            (SensorEventListenerRecord) this.mSensorEventListeners.valueAt(i5));
      }
    }
    for (i4 = 0; i4 < size; i4++) {
      ((SensorEventListenerRecord) this.mSensorAccuracyListenersToNotify.get(i4))
          .notifySensorAccuracy(i, i2, i3);
    }
    this.mSensorAccuracyListenersToNotify.clear();
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

  public final int interceptKeyBeforeQueueing(KeyEvent keyEvent, int i) {
    synchronized (this.mFocusEventDebugViewLock) {
      FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
      if (focusEventDebugView != null) {
        focusEventDebugView.reportEvent(keyEvent);
      }
    }
    int keyCode = keyEvent.getKeyCode();
    int action = keyEvent.getAction();
    if (CoreRune.IFW_KEY_COUNTER
        && ((keyCode == 24
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
            && keyEvent.getDeviceId() != -1)) {
      this.mInputKeyCounter.increaseCount(keyCode);
      if (this.mInputKeyCounter.getAllKeyCount() > 500) {
        this.mHandler.post(new KeyCountRunnable());
      }
    }
    return this.mWindowManagerCallbacks.interceptKeyBeforeQueueing(keyEvent, i);
  }

  public final int interceptMotionBeforeQueueingNonInteractive(int i, long j, int i2) {
    return this.mWindowManagerCallbacks.interceptMotionBeforeQueueingNonInteractive(i, j, i2);
  }

  public final long interceptKeyBeforeDispatching(IBinder iBinder, KeyEvent keyEvent, int i) {
    return this.mWindowManagerCallbacks.interceptKeyBeforeDispatching(iBinder, keyEvent, i);
  }

  public final KeyEvent dispatchUnhandledKey(IBinder iBinder, KeyEvent keyEvent, int i) {
    return this.mWindowManagerCallbacks.dispatchUnhandledKey(iBinder, keyEvent, i);
  }

  public final void onPointerDownOutsideFocus(IBinder iBinder) {
    this.mWindowManagerCallbacks.onPointerDownOutsideFocus(iBinder);
  }

  public final void onPointerDownUpCancelOutsideFocus(IBinder iBinder, int i, int i2, int i3) {
    this.mWindowManagerCallbacks.onPointerDownUpCancelOutsideFocus(iBinder, i, i2, i3);
  }

  public final int getVirtualKeyQuietTimeMillis() {
    return this.mContext.getResources().getInteger(R.integer.config_wakeUpToLastStateTimeoutMillis);
  }

  public static String[] getExcludedDeviceNames() {
    ArrayList arrayList = new ArrayList();
    File[] fileArr = {Environment.getRootDirectory(), Environment.getVendorDirectory()};
    for (int i = 0; i < 2; i++) {
      File file = new File(fileArr[i], "etc/excluded-input-devices.xml");
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          arrayList.addAll(ConfigurationProcessor.processExcludedDeviceNames(fileInputStream));
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

  public final boolean isPerDisplayTouchModeEnabled() {
    return this.mContext.getResources().getBoolean(R.bool.config_perDisplayFocusEnabled);
  }

  public final void notifyStylusGestureStarted(int i, long j) {
    this.mBatteryController.notifyStylusGestureStarted(i, j);
  }

  public static String[] flatten(Map map) {
    final ArrayList arrayList = new ArrayList(map.size() * 2);
    map.forEach(
        new BiConsumer() { // from class:
                           // com.android.server.input.InputManagerService$$ExternalSyntheticLambda2
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            InputManagerService.lambda$flatten$8(arrayList, (String) obj, obj2);
          }
        });
    return (String[]) arrayList.toArray(new String[0]);
  }

  public static /* synthetic */ void lambda$flatten$8(List list, String str, Object obj) {
    list.add(str);
    list.add(obj.toString());
  }

  public static Map loadStaticInputPortAssociations() {
    File file = new File(Environment.getVendorDirectory(), "etc/input-port-associations.xml");
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

  public final String[] getInputPortAssociations() {
    HashMap hashMap = new HashMap(this.mStaticAssociations);
    synchronized (this.mAssociationsLock) {
      hashMap.putAll(this.mRuntimeAssociations);
    }
    return flatten(hashMap);
  }

  public final String[] getInputUniqueIdAssociations() {
    HashMap hashMap;
    synchronized (this.mAssociationsLock) {
      hashMap = new HashMap(this.mUniqueIdAssociations);
    }
    return flatten(hashMap);
  }

  public String[] getDeviceTypeAssociations() {
    HashMap hashMap;
    synchronized (this.mAssociationsLock) {
      hashMap = new HashMap(this.mDeviceTypeAssociations);
    }
    return flatten(hashMap);
  }

  private String[] getKeyboardLayoutAssociations() {
    ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mAssociationsLock) {
      arrayMap.putAll(this.mKeyboardLayoutAssociations);
    }
    return flatten(arrayMap);
  }

  public boolean canDispatchToDisplay(int i, int i2) {
    return this.mNative.canDispatchToDisplay(i, i2);
  }

  public final int getKeyRepeatDelay() {
    return ViewConfiguration.getKeyRepeatDelay();
  }

  public final int getHoverTapTimeout() {
    return ViewConfiguration.getHoverTapTimeout();
  }

  public final int getHoverTapSlop() {
    return ViewConfiguration.getHoverTapSlop();
  }

  public final int getDoubleTapTimeout() {
    return ViewConfiguration.getDoubleTapTimeout();
  }

  public final int getLongPressTimeout() {
    return ViewConfiguration.getLongPressTimeout();
  }

  public final int getPointerLayer() {
    return this.mWindowManagerCallbacks.getPointerLayer();
  }

  public final PointerIcon getPointerIcon(int i) {
    return PointerIcon.getDefaultIcon(getContextForPointerIcon(i));
  }

  public final long getParentSurfaceForPointers(int i) {
    SurfaceControl parentSurfaceForPointers =
        this.mWindowManagerCallbacks.getParentSurfaceForPointers(i);
    if (parentSurfaceForPointers == null) {
      return 0L;
    }
    return parentSurfaceForPointers.mNativeObject;
  }

  public final Context getContextForPointerIcon(int i) {
    Context context = this.mPointerIconDisplayContext;
    if (context != null && context.getDisplay().getDisplayId() == i) {
      return this.mPointerIconDisplayContext;
    }
    Context contextForDisplay = getContextForDisplay(i);
    this.mPointerIconDisplayContext = contextForDisplay;
    if (contextForDisplay == null) {
      this.mPointerIconDisplayContext = getContextForDisplay(0);
    }
    return this.mPointerIconDisplayContext;
  }

  public final Context getContextForDisplay(int i) {
    if (i == -1) {
      return null;
    }
    if (this.mContext.getDisplay().getDisplayId() == i) {
      return this.mContext;
    }
    DisplayManager displayManager =
        (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
    Objects.requireNonNull(displayManager);
    Display display = displayManager.getDisplay(i);
    if (display == null) {
      return null;
    }
    return this.mContext.createDisplayContext(display);
  }

  public final String[] getKeyboardLayoutOverlay(InputDeviceIdentifier inputDeviceIdentifier) {
    if (this.mSystemReady) {
      return this.mKeyboardLayoutManager.getKeyboardLayoutOverlay(inputDeviceIdentifier);
    }
    return null;
  }

  public void remapModifierKey(int i, int i2) {
    super.remapModifierKey_enforcePermission();
    this.mKeyRemapper.remapKey(i, i2);
  }

  public void clearAllModifierKeyRemappings() {
    super.clearAllModifierKeyRemappings_enforcePermission();
    this.mKeyRemapper.clearAllKeyRemappings();
  }

  public Map getModifierKeyRemapping() {
    super.getModifierKeyRemapping_enforcePermission();
    return this.mKeyRemapper.getKeyRemapping();
  }

  public final String getDeviceAlias(String str) {
    BluetoothAdapter.checkBluetoothAddress(str);
    return null;
  }

  public class PointerDisplayIdChangedArgs {
    public final int mPointerDisplayId;
    public final float mXPosition;
    public final float mYPosition;

    public PointerDisplayIdChangedArgs(int i, float f, float f2) {
      this.mPointerDisplayId = i;
      this.mXPosition = f;
      this.mYPosition = f2;
    }
  }

  public void onPointerDisplayIdChanged(int i, float f, float f2) {
    this.mHandler.obtainMessage(4, new PointerDisplayIdChangedArgs(i, f, f2)).sendToTarget();
  }

  public void setSecAccessoryManagerCallbacks(
      SecAccessoryManagerCallbacks secAccessoryManagerCallbacks) {
    this.mSecAccessoryManagerCallbacks = secAccessoryManagerCallbacks;
  }

  public void setDesktopModeServiceCallbacks(
      DesktopModeServiceCallbacks desktopModeServiceCallbacks) {
    this.mDesktopModeServiceCallbacks = desktopModeServiceCallbacks;
  }

  public void setInputMethodManagerCallbacks(
      InputMethodManagerCallbacks inputMethodManagerCallbacks) {
    this.mInputMethodManagerCallbacks = inputMethodManagerCallbacks;
  }

  public final class InputManagerHandler extends Handler {
    public InputManagerHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        InputManagerService.this.deliverInputDevicesChanged((InputDevice[]) message.obj);
        return;
      }
      if (i == 2) {
        InputManagerService.this.reloadDeviceAliases();
        return;
      }
      if (i == 3) {
        InputManagerService.this.deliverTabletModeChanged(
            (r8.argi1 & 4294967295L) | (r8.argi2 << 32),
            ((Boolean) ((SomeArgs) message.obj).arg1).booleanValue());
        return;
      }
      if (i != 4) {
        switch (i) {
          case 103:
            InputManagerService.this.deliverLidStateChanged(
                (r8.argi1 & 4294967295L) | (r8.argi2 << 32),
                ((Boolean) ((SomeArgs) message.obj).arg1).booleanValue());
            break;
          case 104:
            int i2 = message.arg1;
            Object obj = message.obj;
            InputManagerService.this.deliverPointerIconChanged(
                i2, obj instanceof PointerIcon ? (PointerIcon) obj : null);
            break;
          case 105:
            InputManagerService.this.deliverMultiFingerGesture(message.arg1, message.arg2);
            break;
          case 106:
            SomeArgs someArgs = (SomeArgs) message.obj;
            InputManagerService.this.wakeUp(
                (someArgs.argi1 & 4294967295L) | (someArgs.argi2 << 32),
                someArgs.argi3,
                (String) someArgs.arg1);
            break;
          case 107:
            SomeArgs someArgs2 = (SomeArgs) message.obj;
            InputManagerService.this.deliverSwitchEventChanged(
                someArgs2.argi1, someArgs2.argi2, someArgs2.argi3, someArgs2.argi4);
            break;
          case 108:
            boolean z = message.arg1 == 1;
            if (InputManagerService.this.mSensorManager == null) {
              Log.d("InputManager", "mSensorManager is null.");
              break;
            } else if (InputManagerService.this.mFoldingAngleSensor != null) {
              if (z) {
                if (!InputManagerService.this.mFoldingAngleRegistered) {
                  InputManagerService.this.mSensorManager.registerListener(
                      InputManagerService.this.mFoldingAngleListener,
                      InputManagerService.this.mFoldingAngleSensor,
                      2,
                      InputManagerService.this.mHandler);
                  InputManagerService.this.mFoldingAngleRegistered = true;
                  Log.d("InputManager", "register mFoldingAngleListener");
                  break;
                } else {
                  Log.d("InputManager", "already registered mFoldingAngleListener");
                  break;
                }
              } else if (InputManagerService.this.mFoldingAngleRegistered) {
                InputManagerService.this.mSensorManager.unregisterListener(
                    InputManagerService.this.mFoldingAngleListener,
                    InputManagerService.this.mFoldingAngleSensor);
                InputManagerService.this.mFoldingAngleRegistered = false;
                Log.d("InputManager", "unregister mFoldingAngleListener");
                break;
              } else {
                Log.d("InputManager", "already unregistered mFoldingAngleListener");
                break;
              }
            } else {
              Log.d("InputManager", "mFoldingAngleSensor is null.");
              break;
            }
        }
        return;
      }
      InputManagerService.this.handlePointerDisplayIdChanged(
          (PointerDisplayIdChangedArgs) message.obj);
    }
  }

  public final class InputFilterHost extends IInputFilterHost.Stub {
    public boolean mDisconnected;

    public InputFilterHost() {}

    public void disconnectLocked() {
      this.mDisconnected = true;
    }

    public void sendInputEvent(InputEvent inputEvent, int i) {
      if (!InputManagerService.this.checkCallingPermission(
          "android.permission.INJECT_EVENTS", "sendInputEvent()")) {
        throw new SecurityException(
            "The INJECT_EVENTS permission is required for injecting input events.");
      }
      Objects.requireNonNull(inputEvent, "event must not be null");
      synchronized (InputManagerService.this.mInputFilterLock) {
        if (!this.mDisconnected) {
          InputManagerService.this.mNative.injectInputEvent(
              inputEvent, false, -1, 0, 0, 0, 0, i | 67108864);
        }
      }
    }
  }

  public final class InputMonitorHost extends IInputMonitorHost.Stub {
    public final IBinder mInputChannelToken;

    public InputMonitorHost(IBinder iBinder) {
      this.mInputChannelToken = iBinder;
    }

    public void pilferPointers() {
      InputManagerService.this.mNative.pilferPointers(this.mInputChannelToken);
    }

    public void dispose() {
      InputManagerService.this.removeSpyWindowGestureMonitor(this.mInputChannelToken);
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Input devices changed listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onInputDevicesChangedListenerDied(this.mPid);
    }

    public void notifyInputDevicesChanged(int[] iArr) {
      try {
        this.mListener.onInputDevicesChanged(iArr);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that input devices changed, assuming it died.",
            e);
        binderDied();
      }
    }
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Lid state changed listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onLidStateChangedListenerDied(this.mPid);
    }

    public void notifyLidStateChanged(long j, boolean z) {
      try {
        this.mListener.onLidStateChanged(j, z);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process " + this.mPid + " that lid state changed, assuming it died.",
            e);
        binderDied();
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "MultiFingerGesture listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onMultiFingerGestureListenerDied(this.mPid);
    }

    public void notifyMultiFingerGesture(int i, int i2) {
      try {
        this.mListener.onMultiFingerGesture(i, i2);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that multi finge gesture was made, assuming it died.",
            e);
        binderDied();
      }
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "SwitchEventChanged listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onSwitchEventChangedListenerDied(this.mPid);
    }

    public void notifySwitchEventChanged(int i, int i2, int i3, int i4) {
      try {
        this.mListener.onSwitchEventChanged(i, i2, i3, i4);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that switch event changed was made, assuming it died.",
            e);
        binderDied();
      }
    }
  }

  public void showingTouchSensitivityNotificationIfNeeded() {
    int touchSensitivity = getTouchSensitivity();
    Slog.d(
        "InputManager",
        "showingTouchSensitivityNotificationIfNeeded, mAutoAdjustTouch="
            + touchSensitivity
            + " count="
            + this.mShowingTouchSensitivityNotiCount
            + " old count="
            + this.mShowingTouchSensitivityNotiCountOld);
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
    String string = this.mContext.getString(17042822);
    String string2 = this.mContext.getString(17042821);
    this.mNotificationManager.createNotificationChannel(
        new NotificationChannel("TouchSensitivityNoti", string, 3));
    this.mNotificationManager.notify(
        17042821,
        new Notification.Builder(this.mContext, "TouchSensitivityNoti")
            .setSmallIcon(R.drawable.ic_qs_airplane)
            .setContentTitle(string)
            .setContentText(string2)
            .setStyle(new Notification.BigTextStyle().bigText(string2))
            .setContentIntent(createPendingIntent())
            .setAutoCancel(true)
            .setShowWhen(true)
            .setActions(buildTurnOnAction(17042821))
            .build());
    increaseTouchSensitivityNotiCount();
  }

  public final Notification.Action buildTurnOnAction(int i) {
    return new Notification.Action.Builder(
            (Icon) null, this.mContext.getString(17042820), createPendingIntentAction(i))
        .build();
  }

  public final PendingIntent createPendingIntent() {
    Bundle bundle = new Bundle();
    bundle.putString(":settings:fragment_args_key", "increse_touch_sensetivity");
    Intent intent = new Intent("android.settings.DISPLAY_SETTINGS");
    intent.setFlags(268468224);
    intent.putExtra(":settings:show_fragment_args", bundle);
    return PendingIntent.getActivity(this.mContext, 0, intent, 67108864);
  }

  public final PendingIntent createPendingIntentAction(int i) {
    Intent intent = new Intent("com.samsung.android.action.SHOWING_TOUCH_SENSITIVITY_NOTI");
    intent.putExtra("channelId", i);
    intent.setPackage(this.mContext.getPackageName());
    return PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
  }

  public final int getTouchSensitivity() {
    return Settings.System.getIntForUser(
        this.mContext.getContentResolver(), "auto_adjust_touch", 0, -2);
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

  public final int getTouchSensitivityNotiCount() {
    String str = SystemProperties.get("persist.service.touchsensitivity.noticount", "null");
    if (str.equals("null")) {
      return 0;
    }
    return Integer.parseInt(str);
  }

  public void registerWirelessKeyboardShareChangedListener(
      IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) {
    int callingUid = Binder.getCallingUid();
    if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
      throw new SecurityException(
          "only system signature can use registerWirelessKeyboardShareChangedListener(), but UID("
              + callingUid
              + ") has not system signature");
    }
    if (iWirelessKeyboardShareChangedListener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mWirelessKeyboardShareLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mWirelessKeyboardShareChangedListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a WirelessKeyboardShareChangedListener.");
      }
      WirelessKeyboardShareChangedListenerRecord wirelessKeyboardShareChangedListenerRecord =
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
    }
  }

  public final void onWirelessKeyboardShareChangedListenerDied(int i) {
    synchronized (this.mWirelessKeyboardShareLock) {
      this.mWirelessKeyboardShareChangedListeners.remove(i);
    }
  }

  public void deliverWirelessKeyboardShareChanged(int i, String str, boolean z) {
    int i2;
    int size;
    this.mTempWirelessKeyboardShareChangedListenersToNotify.clear();
    synchronized (this.mWirelessKeyboardShareLock) {
      if (i == 0 && z) {
        mSharedKeyReferenceCount = 0;
        this.mSharedKeyWakeLock.release();
      }
      size = this.mWirelessKeyboardShareChangedListeners.size();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempWirelessKeyboardShareChangedListenersToNotify.add(
            (WirelessKeyboardShareChangedListenerRecord)
                this.mWirelessKeyboardShareChangedListeners.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((WirelessKeyboardShareChangedListenerRecord)
              this.mTempWirelessKeyboardShareChangedListenersToNotify.get(i2))
          .notifyWirelessKeyboardShareChanged(SystemClock.uptimeMillis(), i, str);
    }
  }

  public final class WirelessKeyboardShareChangedListenerRecord implements IBinder.DeathRecipient {
    public final IWirelessKeyboardShareChangedListener mListener;
    public final int mPid;

    public WirelessKeyboardShareChangedListenerRecord(
        int i, IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) {
      this.mPid = i;
      this.mListener = iWirelessKeyboardShareChangedListener;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d(
            "InputManager",
            "Wireless Keyboard Share changed listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onWirelessKeyboardShareChangedListenerDied(this.mPid);
    }

    public void notifyWirelessKeyboardShareChanged(long j, int i, String str) {
      try {
        this.mListener.onWirelessKeyboardShareChanged(j, i, str);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that wireless keyboard share changed, assuming it died.",
            e);
        binderDied();
      }
    }
  }

  public void updateWirelessKeyboardShareStatus() {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
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
    }
  }

  public void switchWirelessKeyboardShare(boolean z) {
    this.mNative.enableWirelessKeyboardShare(!z);
  }

  public void removeDeviceWirelessKeyboardShare(String str, int i) {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
          Slog.d("InputManager", "removeDeviceWirelessKeyboardShare : called by not allowed app");
          return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mWirelessKeyboardMouseShare.removeHIDDevice(str, i);
          this.mNative.enableWirelessKeyboardShare(false);
          Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
      StringBuilder sb = new StringBuilder();
      sb.append("removeDeviceWirelessKeyboardShare : ");
      if (str == null) {
        str = null;
      }
      sb.append(str);
      Slog.d("InputManager", sb.toString());
    }
  }

  public void changeDeviceWirelessKeyboardShare(String str, int i) {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
          Slog.d("InputManager", "changeDeviceWirelessKeyboardShare : called by not allowed app");
          return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mWirelessKeyboardMouseShare.changeHIDDevice(str, i);
          this.mNative.enableWirelessKeyboardShare(false);
          Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
          Binder.restoreCallingIdentity(clearCallingIdentity);
          throw th;
        }
      }
      StringBuilder sb = new StringBuilder();
      sb.append("changeDeviceWirelessKeyboardShare : ");
      if (str == null) {
        str = null;
      }
      sb.append(str);
      Slog.d("InputManager", sb.toString());
    }
  }

  public boolean addDeviceWirelessKeyboardShare(int i) {
    boolean z;
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (!this.mSystemReady || this.mWirelessKeyboardMouseShare == null) {
        z = true;
      } else {
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
          Slog.d("InputManager", "addDeviceWirelessKeyboardShare : called by not allowed app");
          return false;
        }
        z = this.mWirelessKeyboardMouseShare.addDevice(i);
      }
      Slog.d("InputManager", "addDeviceWirelessKeyboardShare");
      return z;
    }
  }

  public boolean switchDeviceWirelessKeyboardShare(String str, int i) {
    boolean z;
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (!this.mSystemReady || this.mWirelessKeyboardMouseShare == null) {
        z = true;
      } else {
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
          Slog.d("InputManager", "switchDeviceWirelessKeyboardShare : called by not allowed app");
          return false;
        }
        z = this.mWirelessKeyboardMouseShare.switchDevice(str, i);
      }
      Slog.d("InputManager", "switchDeviceWirelessKeyboardShare : " + str);
      return z;
    }
  }

  public void setHostRoleWirelessKeyboardShare() {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (this.mSystemReady && this.mWirelessKeyboardMouseShare != null) {
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
          Slog.d("InputManager", "setHostRoleWirelessKeyboardShare : called by not allowed app");
          return;
        }
        this.mWirelessKeyboardMouseShare.setHostRoleWirelessKeyboardShare();
      }
      Slog.d("InputManager", "setHostRoleWirelessKeyboardShare");
    }
  }

  public void connectByBtDevice(BluetoothDevice bluetoothDevice) {
    WirelessKeyboardMouseShare wirelessKeyboardMouseShare;
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      if (this.mSystemReady
          && (wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare) != null) {
        wirelessKeyboardMouseShare.connectByBtDevice(bluetoothDevice);
        Slog.d("InputManager", "connectByBtDevice");
      }
    }
  }

  public final int sendTouchPadGestureWirelessKeyboardShare(int i, float f, float f2, int i2) {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      WirelessKeyboardMouseShare wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare;
      if (wirelessKeyboardMouseShare != null) {
        wirelessKeyboardMouseShare.notifyMouseAciton(i, f, f2, i2);
      }
    }
    return 0;
  }

  public final int sendKeyboardWirelessKeyboardShare(int i, int i2) {
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      WirelessKeyboardMouseShare wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare;
      if (wirelessKeyboardMouseShare != null) {
        wirelessKeyboardMouseShare.notifyKeyboardAciton(i, i2);
        if (i == 0) {
          mSharedKeyReferenceCount++;
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.input.InputManagerService$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                  InputManagerService.this.lambda$sendKeyboardWirelessKeyboardShare$9();
                }
              });
        } else {
          int i3 = mSharedKeyReferenceCount;
          if (i3 > 0) {
            mSharedKeyReferenceCount = i3 - 1;
          }
          if (mSharedKeyReferenceCount == 0) {
            this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.input.InputManagerService$$ExternalSyntheticLambda10
                  @Override // java.lang.Runnable
                  public final void run() {
                    InputManagerService.this.lambda$sendKeyboardWirelessKeyboardShare$10();
                  }
                });
          }
        }
      }
    }
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendKeyboardWirelessKeyboardShare$9() {
    this.mSharedKeyWakeLock.acquire(60000L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendKeyboardWirelessKeyboardShare$10() {
    this.mSharedKeyWakeLock.release();
  }

  public final int sendSwitchWirelessKeyboardShare(int i) {
    int i2;
    boolean z;
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      WirelessKeyboardMouseShare wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare;
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
        obtain.argi1 = (int) ((-1) & uptimeMillis);
        obtain.argi2 = (int) (uptimeMillis >> 32);
        obtain.argi3 = 6;
        obtain.arg1 = "android.policy:WirelessKeyboardShare";
        this.mHandler.obtainMessage(106, obtain).sendToTarget();
      }
      if (!z) {
        i2 = 2;
      }
    }
    return i2;
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Tablet mode changed listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onTabletModeChangedListenerDied(this.mPid);
    }

    public void notifyTabletModeChanged(long j, boolean z) {
      try {
        this.mListener.onTabletModeChanged(j, z);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that tablet mode changed, assuming it died.",
            e);
        binderDied();
      }
    }
  }

  public final void onSensorEventListenerDied(int i) {
    synchronized (this.mSensorEventLock) {
      this.mSensorEventListeners.remove(i);
    }
  }

  public final class SensorEventListenerRecord implements IBinder.DeathRecipient {
    public final IInputSensorEventListener mListener;
    public final int mPid;

    public SensorEventListenerRecord(int i, IInputSensorEventListener iInputSensorEventListener) {
      this.mPid = i;
      this.mListener = iInputSensorEventListener;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Sensor event listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onSensorEventListenerDied(this.mPid);
    }

    public IInputSensorEventListener getListener() {
      return this.mListener;
    }

    public void notifySensorEvent(int i, int i2, int i3, long j, float[] fArr) {
      try {
        this.mListener.onInputSensorChanged(i, i2, i3, j, fArr);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that sensor event notified, assuming it died.",
            e);
        binderDied();
      }
    }

    public void notifySensorAccuracy(int i, int i2, int i3) {
      try {
        this.mListener.onInputSensorAccuracyChanged(i, i2, i3);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that sensor accuracy notified, assuming it died.",
            e);
        binderDied();
      }
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
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "Vibrator token died.");
      }
      InputManagerService.this.onVibratorTokenDied(this);
    }
  }

  public final class LocalService extends InputManagerInternal {
    @Override // com.android.server.input.InputManagerInternal
    public void onInputMethodSubtypeChanged(
        int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {}

    public LocalService() {}

    @Override // com.android.server.input.InputManagerInternal
    public void setDisplayViewports(List list) {
      InputManagerService.this.setDisplayViewportsInternal(list);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setInteractive(boolean z) {
      InputManagerService.this.mNative.setInteractive(z);
      InputManagerService.this.mBatteryController.onInteractiveChanged(z);
      InputManagerService.this.mKeyboardBacklightController.onInteractiveChanged(z);
      if (!CoreRune.IFW_WIRELESS_KEYBOARD_SA_LOGGING || z) {
        return;
      }
      synchronized (InputManagerService.this.mInputWirelessKeyboardMouseShareLock) {
        if (InputManagerService.this.mWirelessKeyboardMouseShare != null) {
          InputManagerService.this.mWirelessKeyboardMouseShare.notifySALogging();
        }
      }
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setInteractiveForInternalDisplay(boolean z) {
      InputManagerService.this.mNative.setInteractiveForInternalDisplay(z);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void forceHideCursor(boolean z) {
      InputManagerService.this.mNative.forceHideCursor(z);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setCursorPosition(int i, int i2, int i3) {
      InputManagerService.this.mNative.setCursorPosition(i, i2, i3);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setMultiControlOutOfFocus(boolean z) {
      InputManagerService.this.mNative.setMultiControlOutOfFocus(z);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void toggleCapsLock(int i) {
      InputManagerService.this.mNative.toggleCapsLock(i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setPulseGestureEnabled(boolean z) {
      FileWriter fileWriter;
      if (InputManagerService.this.mDoubleTouchGestureEnableFile != null) {
        FileWriter fileWriter2 = null;
        try {
          try {
            fileWriter = new FileWriter(InputManagerService.this.mDoubleTouchGestureEnableFile);
          } catch (IOException e) {
            e = e;
          }
        } catch (Throwable th) {
          th = th;
        }
        try {
          fileWriter.write(z ? "1" : "0");
          IoUtils.closeQuietly(fileWriter);
        } catch (IOException e2) {
          e = e2;
          fileWriter2 = fileWriter;
          Log.wtf("InputManager", "Unable to setPulseGestureEnabled", e);
          IoUtils.closeQuietly(fileWriter2);
        } catch (Throwable th2) {
          th = th2;
          fileWriter2 = fileWriter;
          IoUtils.closeQuietly(fileWriter2);
          throw th;
        }
      }
    }

    @Override // com.android.server.input.InputManagerInternal
    public boolean transferTouchFocus(IBinder iBinder, IBinder iBinder2) {
      return InputManagerService.this.transferTouchFocus(iBinder, iBinder2);
    }

    @Override // com.android.server.input.InputManagerInternal
    public boolean setVirtualMousePointerDisplayId(int i) {
      return InputManagerService.this.setVirtualMousePointerDisplayIdBlocking(i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public int getVirtualMousePointerDisplayId() {
      return InputManagerService.this.getVirtualMousePointerDisplayId();
    }

    @Override // com.android.server.input.InputManagerInternal
    public PointF getCursorPosition() {
      float[] mouseCursorPosition = InputManagerService.this.mNative.getMouseCursorPosition();
      if (mouseCursorPosition == null || mouseCursorPosition.length != 2) {
        throw new IllegalStateException("Failed to get mouse cursor position");
      }
      return new PointF(mouseCursorPosition[0], mouseCursorPosition[1]);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setPointerAcceleration(float f, int i) {
      InputManagerService.this.setPointerAcceleration(f, i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setDisplayEligibilityForPointerCapture(int i, boolean z) {
      InputManagerService.this.setDisplayEligibilityForPointerCapture(i, z);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setPointerIconVisible(boolean z, int i) {
      InputManagerService.this.setPointerIconVisible(z, i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void registerLidSwitchCallback(
        InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
      InputManagerService.this.registerLidSwitchCallbackInternal(lidSwitchCallback);
    }

    @Override // com.android.server.input.InputManagerInternal
    public InputChannel createInputChannel(String str) {
      return InputManagerService.this.createInputChannel(str);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void onInputMethodSubtypeChangedForKeyboardLayoutMapping(
        int i,
        InputMethodSubtypeHandle inputMethodSubtypeHandle,
        InputMethodSubtype inputMethodSubtype) {
      InputManagerService.this.mKeyboardLayoutManager.onInputMethodSubtypeChanged(
          i, inputMethodSubtypeHandle, inputMethodSubtype);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void notifyUserActivity() {
      InputManagerService.this.mKeyboardBacklightController.notifyUserActivity();
    }

    @Override // com.android.server.input.InputManagerInternal
    public void incrementKeyboardBacklight(int i) {
      InputManagerService.this.mKeyboardBacklightController.incrementKeyboardBacklight(i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void decrementKeyboardBacklight(int i) {
      InputManagerService.this.mKeyboardBacklightController.decrementKeyboardBacklight(i);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setTypeAssociation(String str, String str2) {
      InputManagerService.this.setTypeAssociationInternal(str, str2);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void unsetTypeAssociation(String str) {
      InputManagerService.this.unsetTypeAssociationInternal(str);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void addKeyboardLayoutAssociation(String str, String str2, String str3) {
      InputManagerService.this.addKeyboardLayoutAssociation(str, str2, str3);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void removeKeyboardLayoutAssociation(String str) {
      InputManagerService.this.removeKeyboardLayoutAssociation(str);
    }

    @Override // com.android.server.input.InputManagerInternal
    public void setStylusButtonMotionEventsEnabled(boolean z) {
      InputManagerService.this.mNative.setStylusButtonMotionEventsEnabled(z);
    }

    @Override // com.android.server.input.InputManagerInternal
    public int getKeyCodeState(int i, int i2, int i3) {
      return InputManagerService.this.getKeyCodeState(i, i2, i3);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    new InputShellCommand()
        .exec(
            this,
            fileDescriptor,
            fileDescriptor2,
            fileDescriptor3,
            strArr,
            shellCallback,
            resultReceiver);
  }

  public void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) {
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

  public final void setForcedDefaultPointerIconInternal(int i, PointerIcon pointerIcon) {
    boolean z =
        this.mForcedDefaultPointerIcon == this.mDefaultPointerIcon
            && this.mToolTypeForForcedDefaultPointerIcon == this.mToolTypeForDefaultPointerIcon;
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

  public PointerIcon getDefaultPointerIcon() {
    return this.mDefaultPointerIcon;
  }

  public PointerIcon getForcedDefaultPointerIcon() {
    return this.mForcedDefaultPointerIcon;
  }

  public boolean isDefaultPointerIconChanged() {
    return this.mDefaultPointerIconChanged;
  }

  public int getToolTypeForDefaultPointerIcon() {
    return this.mToolTypeForDefaultPointerIcon;
  }

  public class AdditionalDisplayInputProperties {
    public float pointerAcceleration;
    public boolean pointerIconVisible;

    public AdditionalDisplayInputProperties() {
      reset();
    }

    public boolean allDefaults() {
      return Float.compare(this.pointerAcceleration, 3.0f) == 0 && this.pointerIconVisible;
    }

    public void reset() {
      this.pointerAcceleration = 3.0f;
      this.pointerIconVisible = true;
    }
  }

  public final void applyAdditionalDisplayInputProperties() {
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      AdditionalDisplayInputProperties additionalDisplayInputProperties =
          (AdditionalDisplayInputProperties)
              this.mAdditionalDisplayInputProperties.get(this.mRequestedPointerDisplayId);
      if (additionalDisplayInputProperties == null) {
        additionalDisplayInputProperties = DEFAULT_ADDITIONAL_DISPLAY_INPUT_PROPERTIES;
      }
      applyAdditionalDisplayInputPropertiesLocked(additionalDisplayInputProperties);
    }
  }

  public final void applyAdditionalDisplayInputPropertiesLocked(
      AdditionalDisplayInputProperties additionalDisplayInputProperties) {
    boolean z = additionalDisplayInputProperties.pointerIconVisible;
    AdditionalDisplayInputProperties additionalDisplayInputProperties2 =
        this.mCurrentDisplayProperties;
    if (z != additionalDisplayInputProperties2.pointerIconVisible) {
      additionalDisplayInputProperties2.pointerIconVisible = z;
      if (additionalDisplayInputProperties.pointerIconVisible) {
        int i = this.mPointerIconType;
        if (i == -1) {
          Objects.requireNonNull(this.mPointerIcon);
          this.mNative.setCustomPointerIcon(this.mPointerIcon);
        } else {
          this.mNative.setPointerIconType(i);
        }
      } else {
        this.mNative.setPointerIconType(0);
      }
    }
    float f = additionalDisplayInputProperties.pointerAcceleration;
    AdditionalDisplayInputProperties additionalDisplayInputProperties3 =
        this.mCurrentDisplayProperties;
    if (f != additionalDisplayInputProperties3.pointerAcceleration) {
      additionalDisplayInputProperties3.pointerAcceleration = f;
      this.mNative.setPointerAcceleration(additionalDisplayInputProperties.pointerAcceleration);
    }
  }

  public final void updateAdditionalDisplayInputProperties(int i, Consumer consumer) {
    synchronized (this.mAdditionalDisplayInputPropertiesLock) {
      AdditionalDisplayInputProperties additionalDisplayInputProperties =
          (AdditionalDisplayInputProperties) this.mAdditionalDisplayInputProperties.get(i);
      if (additionalDisplayInputProperties == null) {
        additionalDisplayInputProperties = new AdditionalDisplayInputProperties();
        this.mAdditionalDisplayInputProperties.put(i, additionalDisplayInputProperties);
      }
      consumer.accept(additionalDisplayInputProperties);
      if (additionalDisplayInputProperties.allDefaults()) {
        this.mAdditionalDisplayInputProperties.remove(i);
      }
      if (i != this.mRequestedPointerDisplayId) {
        Log.i(
            "InputManager",
            "Not applying additional properties for display "
                + i
                + " because the pointer is currently targeting display "
                + this.mRequestedPointerDisplayId
                + ".");
        return;
      }
      applyAdditionalDisplayInputPropertiesLocked(additionalDisplayInputProperties);
    }
  }

  public void updateFocusEventDebugViewEnabled(boolean z) {
    synchronized (this.mFocusEventDebugViewLock) {
      FocusEventDebugView focusEventDebugView = this.mFocusEventDebugView;
      if (z == (focusEventDebugView != null)) {
        return;
      }
      if (z) {
        focusEventDebugView = new FocusEventDebugView(this.mContext);
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
      layoutParams.setTitle("FocusEventDebugView - display " + this.mContext.getDisplayId());
      layoutParams.inputFeatures = layoutParams.inputFeatures | 1;
      windowManager2.addView(focusEventDebugView, layoutParams);
    }
  }

  public void setShowAllTouches(boolean z) {
    this.mNative.showAllTouches(z);
    Slog.d("InputManager", "show all touches : " + z);
  }

  public long semGetMotionIdleTimeMillis(boolean z) {
    return this.mNative.getMotionIdleTimeMillis(z);
  }

  public boolean isUidTouched(int i) {
    return this.mNative.isUidTouched(i);
  }

  public void setStartedShutdown(boolean z) {
    if (Binder.getCallingUid() == 1000) {
      this.mNative.updateInputMetaState(8, z);
      if (CoreRune.IFW_KEY_COUNTER) {
        this.mInputKeyCounter.saveCount();
      }
    }
  }

  public int getInboundQueueLength() {
    if (Binder.getCallingUid() == 1000) {
      return this.mNative.getInboundQueueLength();
    }
    return 0;
  }

  public int getGlobalMetaState(int i) {
    return this.mNative.getGlobalMetaState(i);
  }

  public void notifyQuickAccess(int i, float f, float f2) {
    if (!this.mSystemReady) {
      Log.d("InputManager", "notifyQuickAccess: system not ready");
      return;
    }
    if (Binder.getCallingUid() == 1000) {
      Log.d("InputManager", "notifyQuickAccess: info=" + i + ", x=" + f + ", y=" + f2);
      this.mWindowManagerCallbacks.interceptQuickAccess(i, f, f2);
    }
  }

  public final void registerFlowPointerSettingObserver() {
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Uri.withAppendedPath(DEX_SETTINGS_URI, "flow_pointer_is_on_dex"),
            true,
            new ContentObserver(
                this.mHandler) { // from class: com.android.server.input.InputManagerService.7
              @Override // android.database.ContentObserver
              public void onChange(boolean z) {
                super.onChange(z);
                InputManagerService.this.updateFlowPointerSettings();
              }
            });
  }

  public final void registerFlowPointerDirectionSettingObserver() {
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Uri.withAppendedPath(DEX_SETTINGS_URI, "flow_pointer_from_where_dex"),
            true,
            new ContentObserver(
                this.mHandler) { // from class: com.android.server.input.InputManagerService.8
              @Override // android.database.ContentObserver
              public void onChange(boolean z) {
                super.onChange(z);
                InputManagerService.this.updateFlowPointerDirectionSettings();
              }
            });
  }

  public void updateFlowPointerSettings() {
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
      Log.e("InputManager", "Failed to get settings SETTINGS_KEY_FLOW_POINTER_TO_PHONE_SCREEN", e);
    }
    this.mNative.enableFlowPointer(z);
    Log.d("InputManager", "updateFlowPointerSettings : " + z);
  }

  public void updateFlowPointerDirectionSettings() {
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
      String string = call != null ? call.getString("flow_pointer_from_where_dex", "left") : "";
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

  public final void notifyDisplayIdChangedByUser(int i) {
    Log.d("InputManager", "notifyDisplayIdChangedByUser: " + i);
    if (this.mDisplayIdForPointerIcon != i) {
      this.mDisplayIdForPointerIcon = i;
    }
  }

  public void updateSetPenModeOnDex() {
    int setPenModeOnDex = getSetPenModeOnDex(1);
    this.mNative.setPenModeOnDex(setPenModeOnDex);
    Log.d("InputManager", "updateSetPenModeOnDex : " + setPenModeOnDex);
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

  public final void registerSetPenModeOnDexObserver() {
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Uri.withAppendedPath(DEX_SETTINGS_URI, "spen_mode"),
            true,
            new ContentObserver(
                this.mHandler) { // from class: com.android.server.input.InputManagerService.9
              @Override // android.database.ContentObserver
              public void onChange(boolean z) {
                super.onChange(z);
                InputManagerService.this.updateSetPenModeOnDex();
              }
            });
  }

  public int getCurrentSwitchEventState(int i, boolean z) {
    if (!z) {
      if ((i & 1) != 0) {
        return 0 | (this.mPogoKeyboardConnected ? 1 : 0);
      }
      return 0;
    }
    int i2 = 0;
    for (int i3 = 0; i3 < 32; i3++) {
      int i4 = 1 << i3;
      if ((i & i4) != 0 && getSwitchState(-1, -256, i3) == 1) {
        i2 |= i4;
      }
    }
    return i2;
  }

  public void registerSwitchEventChangedListener(
      ISwitchEventChangedListener iSwitchEventChangedListener) {
    if (iSwitchEventChangedListener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mSwitchEventChangedLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mSwitchEventChangedListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a SwitchEventChangedListener.");
      }
      SwitchEventChangedListenerRecord switchEventChangedListenerRecord =
          new SwitchEventChangedListenerRecord(callingPid, iSwitchEventChangedListener);
      try {
        iSwitchEventChangedListener.asBinder().linkToDeath(switchEventChangedListenerRecord, 0);
        this.mSwitchEventChangedListeners.put(callingPid, switchEventChangedListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onSwitchEventChangedListenerDied(int i) {
    synchronized (this.mSwitchEventChangedLock) {
      this.mSwitchEventChangedListeners.remove(i);
    }
  }

  public final void deliverSwitchEventChanged(int i, int i2, int i3, int i4) {
    int size;
    int i5;
    this.mTempSwitchEventChangedListenersToNotify.clear();
    synchronized (this.mSwitchEventChangedLock) {
      size = this.mSwitchEventChangedListeners.size();
      for (int i6 = 0; i6 < size; i6++) {
        this.mTempSwitchEventChangedListenersToNotify.add(
            (SwitchEventChangedListenerRecord) this.mSwitchEventChangedListeners.valueAt(i6));
      }
    }
    for (i5 = 0; i5 < size; i5++) {
      ((SwitchEventChangedListenerRecord) this.mTempSwitchEventChangedListenersToNotify.get(i5))
          .notifySwitchEventChanged(i, i2, i3, i4);
    }
  }

  public void registerMultiFingerGestureListener(
      IMultiFingerGestureListener iMultiFingerGestureListener) {
    if (iMultiFingerGestureListener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mMultiFingerGestureLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mMultiFingerGestureListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a MultiFingerGestureListener.");
      }
      MultiFingerGestureListenerRecord multiFingerGestureListenerRecord =
          new MultiFingerGestureListenerRecord(callingPid, iMultiFingerGestureListener);
      try {
        iMultiFingerGestureListener.asBinder().linkToDeath(multiFingerGestureListenerRecord, 0);
        this.mMultiFingerGestureListeners.put(callingPid, multiFingerGestureListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onMultiFingerGestureListenerDied(int i) {
    synchronized (this.mMultiFingerGestureLock) {
      this.mMultiFingerGestureListeners.remove(i);
    }
  }

  public final void deliverMultiFingerGesture(int i, int i2) {
    int size;
    int i3;
    this.mTempMultiFingerGestureListenersToNotify.clear();
    synchronized (this.mMultiFingerGestureLock) {
      size = this.mMultiFingerGestureListeners.size();
      for (int i4 = 0; i4 < size; i4++) {
        this.mTempMultiFingerGestureListenersToNotify.add(
            (MultiFingerGestureListenerRecord) this.mMultiFingerGestureListeners.valueAt(i4));
      }
    }
    for (i3 = 0; i3 < size; i3++) {
      ((MultiFingerGestureListenerRecord) this.mTempMultiFingerGestureListenersToNotify.get(i3))
          .notifyMultiFingerGesture(i, i2);
    }
  }

  public final void sendMultiFingerGesture(int i, int i2) {
    this.mHandler.obtainMessage(105, i, i2).sendToTarget();
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

  public void updateMultiFingerTapBehavior(int i) {
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
    Log.d("InputManager", "updateMultiFingerTapBehavior : " + i2);
  }

  public final void registerMultiFingerTapBehavior(int i) {
    if (i == 4) {
      this.mContext
          .getContentResolver()
          .registerContentObserver(
              Uri.withAppendedPath(DEX_SETTINGS_URI, "touchpad_gestures_three_finger_tap"),
              true,
              new ContentObserver(
                  this.mHandler) { // from class: com.android.server.input.InputManagerService.10
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                  super.onChange(z);
                  InputManagerService.this.updateMultiFingerTapBehavior(4);
                }
              });
    } else if (i == 1) {
      this.mContext
          .getContentResolver()
          .registerContentObserver(
              Uri.withAppendedPath(DEX_SETTINGS_URI, "touchpad_gestures_four_finger_tap"),
              true,
              new ContentObserver(
                  this.mHandler) { // from class: com.android.server.input.InputManagerService.11
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                  super.onChange(z);
                  InputManagerService.this.updateMultiFingerTapBehavior(1);
                }
              });
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
      Log.d("InputManager", "notifyMultiFingerGesture: " + i + " " + i3);
    } else {
      Log.d(
          "InputManager",
          "Not support multi finger gesture " + Build.VERSION.SEM_PLATFORM_INT + " 0");
      i3 = 0;
    }
    sendMultiFingerGesture(i3, i2);
    return i3 == 4 ? 4 : 0;
  }

  public void registerPointerIconChangedListener(
      IPointerIconChangedListener iPointerIconChangedListener) {
    if (iPointerIconChangedListener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    synchronized (this.mPointerIconLock) {
      int callingPid = Binder.getCallingPid();
      if (this.mPointerIconChangedListeners.get(callingPid) != null) {
        throw new IllegalStateException(
            "The calling process has already registered a PointerIconChangedListener.");
      }
      PointerIconChangedListenerRecord pointerIconChangedListenerRecord =
          new PointerIconChangedListenerRecord(callingPid, iPointerIconChangedListener);
      try {
        iPointerIconChangedListener.asBinder().linkToDeath(pointerIconChangedListenerRecord, 0);
        this.mPointerIconChangedListeners.put(callingPid, pointerIconChangedListenerRecord);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onPointerIconChangedListenerDied(int i) {
    synchronized (this.mPointerIconLock) {
      this.mPointerIconChangedListeners.remove(i);
    }
  }

  public final void deliverPointerIconChanged(int i, PointerIcon pointerIcon) {
    int size;
    int i2;
    this.mTempPointerIconChangedListenersToNotify.clear();
    synchronized (this.mPointerIconLock) {
      size = this.mPointerIconChangedListeners.size();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempPointerIconChangedListenersToNotify.add(
            (PointerIconChangedListenerRecord) this.mPointerIconChangedListeners.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((PointerIconChangedListenerRecord) this.mTempPointerIconChangedListenersToNotify.get(i2))
          .notifyPointerIconChanged(i, pointerIcon);
    }
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

  public final class PointerIconChangedListenerRecord implements IBinder.DeathRecipient {
    public final IPointerIconChangedListener mListener;
    public final int mPid;

    public PointerIconChangedListenerRecord(
        int i, IPointerIconChangedListener iPointerIconChangedListener) {
      this.mPid = i;
      this.mListener = iPointerIconChangedListener;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      if (InputManagerService.DEBUG) {
        Slog.d("InputManager", "PointerIcon changed listener for pid " + this.mPid + " died.");
      }
      InputManagerService.this.onPointerIconChangedListenerDied(this.mPid);
    }

    public void notifyPointerIconChanged(int i, PointerIcon pointerIcon) {
      try {
        this.mListener.onPointerIconChanged(i, pointerIcon);
      } catch (RemoteException e) {
        Slog.w(
            "InputManager",
            "Failed to notify process "
                + this.mPid
                + " that pointer icon changed, assuming it died.",
            e);
        binderDied();
      }
    }
  }

  public void setDisplayIdForPointerIcon(int i) {
    if (this.mDisplayIdForPointerIcon != i) {
      PointerIcon.clearSystemIcons();
      this.mDisplayIdForPointerIcon = i;
    }
  }

  public int getDisplayIdForPointerIcon() {
    return this.mDisplayIdForPointerIcon;
  }

  public int getPointerIconType() {
    return this.mPointerIconType;
  }

  public boolean setTspEnabled(int i, boolean z) {
    if (!this.mSystemReady) {
      Log.d("InputManager", "setTspEnabled: system not ready");
      return false;
    }
    int callingUid = Binder.getCallingUid();
    if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
      throw new SecurityException(
          "only system signature can use setEnableTSP(), but UID("
              + callingUid
              + ") has not system signature");
    }
    SemInputDeviceManager semInputDeviceManager =
        (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
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

  public synchronized void setWakeKeyDynamically(String str, boolean z, String str2) {
    this.mControlWakeKey.setWakeKeyDynamically(str, z, str2);
  }

  public final void registerDesktopModeStateChangedListener() {
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    if (semDesktopModeManager == null) {
      return;
    }
    semDesktopModeManager.registerListener(
        new SemDesktopModeManager
            .DesktopModeListener() { // from class: com.android.server.input.InputManagerService.13
          public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            int i = semDesktopModeState.state;
            if ((i != 40 || semDesktopModeState.enabled != 4)
                && (i != 20 || semDesktopModeState.enabled != 1)) {
              if (i == 50) {
                Log.d(
                    "InputManager",
                    "STATE_CONFIG_CHANGE_FINISHED = " + semDesktopModeState.enabled);
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
                        LocalServices.getService(DesktopModeManagerInternal.class))
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
            InputManagerService.this.mNative.setDexMode(z, displayType, touchpadSupportedFeatures);
            Log.d(
                "InputManager",
                "set dexmode "
                    + z
                    + " displayType "
                    + displayType
                    + " dexFeature "
                    + touchpadSupportedFeatures);
          }
        });
  }

  public synchronized void setBlockDeviceMode(boolean z, int i, boolean z2, String str) {
    if (str != null) {
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
    }
    Slog.d("InputManager", "sBDM(): caller must be specified!");
  }

  public final void wakeUpWhenPogoConnected(boolean z) {
    if (z) {
      long uptimeMillis = SystemClock.uptimeMillis();
      SomeArgs obtain = SomeArgs.obtain();
      obtain.argi1 = (int) ((-1) & uptimeMillis);
      obtain.argi2 = (int) (uptimeMillis >> 32);
      obtain.argi3 = 105;
      obtain.arg1 = "android.policy:POGO_CONNECT";
      this.mHandler.obtainMessage(106, obtain).sendToTarget();
    }
  }

  public final void sendPogoKeyboardStatus(boolean z) {
    Intent intent = new Intent("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
    intent.putExtra("status", z);
    intent.addFlags(16777216);
    this.mContext.sendStickyBroadcast(intent);
    this.mWindowManagerCallbacks.notifyPogoKeyboardStatus(z);
    synchronized (this.mInputWirelessKeyboardMouseShareLock) {
      WirelessKeyboardMouseShare wirelessKeyboardMouseShare = this.mWirelessKeyboardMouseShare;
      if (wirelessKeyboardMouseShare != null) {
        wirelessKeyboardMouseShare.setPogoConnected(z);
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

  /* renamed from: com.android.server.input.InputManagerService$15 */
  public class C166715 extends BroadcastReceiver {
    public C166715() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        InputManagerService.this.mBootCompleted = true;
        if (CoreRune.IFW_KEY_COUNTER) {
          InputManagerService.this.mInputKeyCounter.kickOldies(InputManagerService.this.mContext);
        }
        InputManagerService.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.input.InputManagerService$15$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                InputManagerService.C166715.this.lambda$onReceive$0();
              }
            });
        if (InputManagerService.this.mNotifyPogoKeyboardNotMatchPending) {
          InputManagerService.this.notifyPogoKeyboardNotMatch();
          InputManagerService.this.mNotifyPogoKeyboardNotMatchPending = false;
        }
        if (InputManagerService.this.mPaperCoverNotificationPending) {
          InputManagerService.this.mPaperCoverNotificationPending = false;
          InputManagerService.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.input.InputManagerService$15$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  InputManagerService.C166715.this.lambda$onReceive$1();
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$0() {
      InputManagerService.this.updateMultiFingerTapBehavior(4);
      InputManagerService.this.updateMultiFingerTapBehavior(1);
      InputManagerService.this.updateFlowPointerSettings();
      InputManagerService.this.updateFlowPointerDirectionSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReceive$1() {
      InputManagerService.this.showingTouchSensitivityNotificationIfNeeded();
    }
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

  public final boolean interceptQuickAccess(boolean z) {
    if (!this.mSystemReady) {
      Log.d("InputManager", "QuickAccess: system not ready");
      return false;
    }
    String scrubPosition =
        ((SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService"))
            .getScrubPosition(z ? 1 : 2);
    try {
      String[] split = scrubPosition.split(" ");
      if (split.length < 3) {
        Log.d("InputManager", "invalid format for QuickAccess: " + scrubPosition);
        return false;
      }
      int parseInt = Integer.parseInt(split[0]);
      float parseFloat = Float.parseFloat(split[1]);
      float parseFloat2 = Float.parseFloat(split[2]);
      Log.d(
          "InputManager",
          "QuickAccess info: " + parseInt + ", (" + parseFloat + ", " + parseFloat2 + ")");
      return this.mWindowManagerCallbacks.interceptQuickAccess(parseInt, parseFloat, parseFloat2);
    } catch (NullPointerException unused) {
      Log.d("InputManager", "NPE on QuickAccess: " + scrubPosition);
      return false;
    } catch (NumberFormatException unused2) {
      Log.d("InputManager", "NFE on QuickAccess: " + scrubPosition);
      return false;
    }
  }

  public final void wakeUp(long j, int i, String str) {
    PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
    if (powerManager != null) {
      powerManager.wakeUp(SystemClock.uptimeMillis(), i, str);
      Log.d("InputManager", "wakeup -" + str);
    }
  }

  public final void initTspCmdForSpay() {
    if (SEP_FULL && "true".equals(SystemProperties.get("persist.service.tspcmd.spay"))) {
      setTspEnabled(InputManager.SemTspCommandType.SPAY.getvalue(), isSpayFullAppInstalled());
    }
  }

  public boolean isSpayFullAppInstalled() {
    try {
      if (this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.spay", 128)
          != null) {
        return !r3.metaData.getBoolean("com.samsung.android.spay.is_stub", false);
      }
      return false;
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public void forceFadeIcon(int i) {
    this.mNative.forceFadeIcon(i);
  }

  public void setDexImePolicy(boolean z) {
    this.mDexImeWindowVisibleInDefaultDisplay = z;
  }

  public void configureGestureMonitorSurfaces(final int i, final SurfaceControl surfaceControl) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.input.InputManagerService$$ExternalSyntheticLambda12
          @Override // java.lang.Runnable
          public final void run() {
            InputManagerService.this.lambda$configureGestureMonitorSurfaces$11(surfaceControl, i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$configureGestureMonitorSurfaces$11(
      SurfaceControl surfaceControl, int i) {
    synchronized (this.mInputMonitors) {
      if (!this.mInputMonitors.isEmpty() && surfaceControl != null && surfaceControl.isValid()) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        Iterator it = this.mInputMonitors.values().iterator();
        while (it.hasNext()) {
          ((GestureMonitorSpyWindow) it.next()).configureSurface(transaction, i, surfaceControl);
        }
        transaction.apply();
      }
    }
  }
}
