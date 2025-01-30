package com.android.server.desktopmode;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Pair;
import android.util.Range;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputDevice;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class HardwareManager {
  public static final String TAG = "[DMS]" + HardwareManager.class.getSimpleName();
  public static final List sSupportedDockUsbpdIdRanges;
  public static final Map sSupportedDockUsbpdIds;
  public BatteryChangedListener mBatteryChangedListener;
  public DisplayInfo mConnectedDisplay;
  public InputDevice mConnectedMouse;
  public final Context mContext;
  public final DisplayManager.DisplayListener mDisplayListener;
  public final DisplayManager mDisplayManager;
  public final Handler mHandler;
  public final IDisplayManager mIDisplayManager;
  public final InputManager.InputDeviceListener mInputDeviceListener;
  public final InputManager mInputManager;
  public PogoKeyboardChangedListener mPogoKeyboardChangedListener;
  public final PowerManagerInternal mPowerManagerInternal;
  public IRefreshRateToken mRefreshRateToken;
  public final ContentResolver mResolver;
  public final SettingsHelper mSettingsHelper;
  public final StateManager.StateListener mStateListener;
  public final IStateManager mStateManager;
  public final UEventObserver mUEventHostDeviceObserver;
  public final UEventObserver mUEventObserver;
  public final WindowManagerService mWindowManager;
  public final Object mLock = new Object();
  public boolean mForcedInternalScreenModeEnabled = false;
  public boolean mIsExternalDisplayConnected = false;
  public boolean mIsMouseConnected = false;
  public boolean mIsPogoKeyboardConnected = false;
  public boolean mIsBtMouseDeepSleep = false;
  public int mRawDockState = -1;
  public String mRawDockUsbpdIds = "";
  public SparseArray mDisplays = new SparseArray();
  public DockState mDockState = new DockState();

  public static boolean isSupportedDisplayType(int i) {
    return i == 2 || i == 1000 || i == 1001;
  }

  static {
    ArrayMap arrayMap = new ArrayMap();
    sSupportedDockUsbpdIds = arrayMap;
    arrayMap.put("04e8:a027", 10008);
    arrayMap.put("04e8:a029", 10004);
    arrayMap.put("04e8:a025", Integer.valueOf(FrameworkStatsLog.CPU_TIME_PER_UID));
    arrayMap.put("04e8:a020", 10001);
    arrayMap.put("04b4:2122", Integer.valueOf(FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO));
    arrayMap.put("04b4:f645", Integer.valueOf(FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER));
    arrayMap.put("04e8:a048", Integer.valueOf(FrameworkStatsLog.CPU_TIME_PER_UID_FREQ));
    arrayMap.put("04e8:a056", Integer.valueOf(FrameworkStatsLog.WIFI_ACTIVITY_INFO));
    arrayMap.put("04e8:a066", Integer.valueOf(FrameworkStatsLog.PROCESS_MEMORY_STATE));
    ArrayList arrayList = new ArrayList();
    sSupportedDockUsbpdIdRanges = arrayList;
    arrayList.add(
        new Pair(
            new Range("04e8:a02a", "04e8:a02e"),
            Integer.valueOf(FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE)));
    arrayList.add(
        new Pair(
            new Range("04e8:a02f", "04e8:a033"),
            Integer.valueOf(FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME)));
    Range range = new Range("1048:4007", "1048:4012");
    Integer valueOf = Integer.valueOf(FrameworkStatsLog.MODEM_ACTIVITY_INFO);
    arrayList.add(new Pair(range, valueOf));
    arrayList.add(new Pair(new Range("1048:5006", "1048:5013"), valueOf));
    arrayList.add(new Pair(new Range("1048:6000", "1048:6fff"), valueOf));
  }

  public HardwareManager(
      Context context,
      IStateManager iStateManager,
      SettingsHelper settingsHelper,
      InputManager inputManager,
      DisplayManager displayManager,
      PowerManagerInternal powerManagerInternal,
      WindowManagerService windowManagerService,
      IDisplayManager iDisplayManager) {
    DisplayManager.DisplayListener displayListener =
        new DisplayManager
            .DisplayListener() { // from class: com.android.server.desktopmode.HardwareManager.1
          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayAdded(int i) {
            if (DesktopModeFeature.DEBUG) {
              Log.m7d(HardwareManager.TAG, "onDisplayAdded displayId=" + i);
            }
            HardwareManager.this.updateExternalDisplayStatus(true, i);
          }

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayRemoved(int i) {
            if (DesktopModeFeature.DEBUG) {
              Log.m7d(HardwareManager.TAG, "onDisplayRemoved displayId=" + i);
            }
            HardwareManager.this.updateExternalDisplayStatus(false, i);
          }

          @Override // android.hardware.display.DisplayManager.DisplayListener
          public void onDisplayChanged(int i) {
            Display display;
            if (HardwareManager.this.mStateManager.getState().getDesktopDisplayId() != i
                || (display = HardwareManager.this.mDisplayManager.getDisplay(i)) == null) {
              return;
            }
            DisplayInfo displayInfo = new DisplayInfo(display);
            synchronized (HardwareManager.this.mLock) {
              if (!displayInfo.equals(HardwareManager.this.mDisplays.get(i))) {
                Log.m7d(HardwareManager.TAG, "onDisplayChanged, DisplayInfo=" + displayInfo);
                HardwareManager.this.mDisplays.put(i, displayInfo);
              }
            }
          }
        };
    this.mDisplayListener = displayListener;
    InputManager.InputDeviceListener inputDeviceListener =
        new InputManager
            .InputDeviceListener() { // from class: com.android.server.desktopmode.HardwareManager.2
          @Override // android.hardware.input.InputManager.InputDeviceListener
          public void onInputDeviceChanged(int i) {}

          @Override // android.hardware.input.InputManager.InputDeviceListener
          public void onInputDeviceAdded(int i) {
            synchronized (HardwareManager.this.mLock) {
              HardwareManager.this.updateInputDeviceStatusLocked();
            }
          }

          @Override // android.hardware.input.InputManager.InputDeviceListener
          public void onInputDeviceRemoved(int i) {
            synchronized (HardwareManager.this.mLock) {
              HardwareManager.this.updateInputDeviceStatusLocked();
            }
          }
        };
    this.mInputDeviceListener = inputDeviceListener;
    UEventObserver uEventObserver =
        new UEventObserver() { // from class: com.android.server.desktopmode.HardwareManager.3
          public void onUEvent(UEventObserver.UEvent uEvent) {
            synchronized (HardwareManager.this.mLock) {
              try {
                HardwareManager.this.setRawDockStateLocked(
                    Integer.parseInt(uEvent.get("SWITCH_STATE")));
                HardwareManager.this.setRawDockUsbpdIdsLocked(uEvent.get("USBPD_IDS"));
                HardwareManager.this.updateDockStatusLocked();
              } catch (NumberFormatException unused) {
                Log.m8e(HardwareManager.TAG, "Could not parse switch state from event " + uEvent);
              }
            }
          }
        };
    this.mUEventObserver = uEventObserver;
    UEventObserver uEventObserver2 =
        new UEventObserver() { // from class: com.android.server.desktopmode.HardwareManager.4
          public void onUEvent(UEventObserver.UEvent uEvent) {
            String str = uEvent.get("ACTION");
            String str2 = uEvent.get("DEVTYPE");
            String str3 = uEvent.get("PRODUCT");
            if ("4b4/f645".equals(str3)) {
              if (DesktopModeFeature.DEBUG) {
                Log.m12w(
                    HardwareManager.TAG,
                    "onUEvent(device) :: action = "
                        + str
                        + ", devType = "
                        + str2
                        + ", product = "
                        + str3);
              }
              synchronized (HardwareManager.this.mLock) {
                if ("add".equals(str)) {
                  Log.m12w(HardwareManager.TAG, "Attached US bootmode for dex pad");
                  HardwareManager.this.setRawDockStateLocked(114);
                  HardwareManager.this.setRawDockUsbpdIdsLocked("04b4:f645");
                  HardwareManager.this.updateDockStatusLocked();
                } else if ("remove".equals(str)) {
                  Log.m12w(HardwareManager.TAG, "Detached US bootmode for dex pad");
                  HardwareManager.this.setRawDockStateLocked(0);
                  HardwareManager.this.setRawDockUsbpdIdsLocked("04b4:f645");
                  HardwareManager.this.updateDockStatusLocked();
                }
              }
            }
          }
        };
    this.mUEventHostDeviceObserver = uEventObserver2;
    StateManager.StateListener stateListener =
        new StateManager
            .StateListener() { // from class: com.android.server.desktopmode.HardwareManager.5
          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onStartLoadingScreen(boolean z) {
            HardwareManager.this.mPowerManagerInternal.setGoToSleepPrevention(true);
            HardwareManager.this.mWindowManager.setEventDispatching(false);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onStopLoadingScreen(boolean z) {
            HardwareManager.this.mPowerManagerInternal.setGoToSleepPrevention(false);
            HardwareManager.this.mWindowManager.setEventDispatching(true);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDualModeStartLoadingScreen(boolean z) {
            HardwareManager.this.setLowRefreshRate(z);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDualModeStopLoadingScreen(boolean z) {
            synchronized (HardwareManager.this.mLock) {
              if (!DesktopModeFeature.IS_TABLET
                  && z
                  && HardwareManager.this.mIsExternalDisplayConnected) {
                HardwareManager hardwareManager = HardwareManager.this;
                hardwareManager.logConnectedAccessoryInformationLocked(
                    hardwareManager.mStateManager.getState());
              }
            }
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDisplayDisconnectionRequested(int i) {
            synchronized (HardwareManager.this.mLock) {
              IntArray intArray = new IntArray(1);
              int size = HardwareManager.this.mDisplays.size();
              for (int i2 = 0; i2 < size; i2++) {
                DisplayInfo displayInfo = (DisplayInfo) HardwareManager.this.mDisplays.valueAt(i2);
                if (displayInfo.getType() == i) {
                  intArray.add(displayInfo.getDisplayId());
                }
              }
              int size2 = intArray.size();
              for (int i3 = 0; i3 < size2; i3++) {
                HardwareManager.this.updateExternalDisplayStatus(false, intArray.get(i3));
              }
            }
          }
        };
    this.mStateListener = stateListener;
    this.mContext = context;
    HandlerThread handlerThread = new HandlerThread("desktopmode_hw", -2);
    handlerThread.start();
    Handler handler = new Handler(handlerThread.getLooper());
    this.mHandler = handler;
    this.mStateManager = iStateManager;
    iStateManager.registerListener(stateListener);
    this.mPowerManagerInternal = powerManagerInternal;
    this.mResolver = context.getContentResolver();
    this.mWindowManager = windowManagerService;
    this.mInputManager = inputManager;
    inputManager.registerInputDeviceListener(inputDeviceListener, handler);
    this.mDisplayManager = displayManager;
    displayManager.registerDisplayListener(displayListener, handler);
    this.mIDisplayManager = iDisplayManager;
    uEventObserver.startObserving("DEVPATH=/devices/virtual/sec/ccic");
    uEventObserver2.startObserving("DEVTYPE=usb_interface");
    this.mSettingsHelper = settingsHelper;
    if (DesktopModeFeature.SUPPORT_STANDALONE
        && context.getPackageManager().hasSystemFeature("com.sec.feature.cover")) {
      PogoKeyboardChangedListener pogoKeyboardChangedListener = new PogoKeyboardChangedListener();
      this.mPogoKeyboardChangedListener = pogoKeyboardChangedListener;
      pogoKeyboardChangedListener.register();
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
    context.registerReceiverAsUser(
        new BroadcastReceiver() { // from class: com.android.server.desktopmode.HardwareManager.6
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            BluetoothClass bluetoothClass;
            if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction())) {
              int intExtra =
                  intent.getIntExtra("com.samsung.bluetooth.device.extra.DISCONNECTION_REASON", 0);
              BluetoothDevice bluetoothDevice =
                  (BluetoothDevice)
                      intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
              if (bluetoothDevice == null
                  || (bluetoothClass = bluetoothDevice.getBluetoothClass()) == null) {
                return;
              }
              int semGetPeripheralMinorClass = bluetoothClass.semGetPeripheralMinorClass();
              if (DesktopModeFeature.DEBUG) {
                Log.m7d(
                    HardwareManager.TAG,
                    "minorClass="
                        + Integer.toHexString(semGetPeripheralMinorClass)
                        + ", reason="
                        + intExtra);
              }
              if (intExtra == 19 && semGetPeripheralMinorClass == 1408) {
                synchronized (HardwareManager.this.mLock) {
                  HardwareManager.this.mIsBtMouseDeepSleep = true;
                  if (DesktopModeFeature.DEBUG) {
                    Log.m7d(HardwareManager.TAG, "mIsBtMouseDeepSleep=true");
                  }
                  if (!HardwareManager.this.mIsMouseConnected) {
                    HardwareManager.this.updateInputDeviceStatusLocked();
                  }
                }
              }
            }
          }
        },
        UserHandle.ALL,
        intentFilter,
        null,
        null);
  }

  public void initialize() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "initialize()");
    }
    Display[] displays = this.mDisplayManager.getDisplays();
    synchronized (this.mLock) {
      updateDockStatusLocked();
      initializeExternalDisplayStatusLocked(displays);
      updateInputDeviceStatusLocked();
      DesktopModeSettings.setSettings(this.mResolver, "dock_usbpd_ids", this.mDockState.getType());
    }
  }

  public final void setRawDockStateLocked(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setRawDockStateLocked(), state=" + Utils.dockStateToString(i));
    }
    this.mRawDockState = i;
  }

  public final void setRawDockUsbpdIdsLocked(String str) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setRawDockUsbpdIdsLocked(), usbpdIds=" + str);
    }
    this.mRawDockUsbpdIds = str;
  }

  public final boolean updateDockStatusLocked() {
    DockState dockState;
    DisplayInfo displayInfo;
    int currentUserId;
    if (this.mRawDockState == -1) {
      setRawDockStateLocked(Utils.readFile("/sys/class/sec/ccic/usbpd_type", 0));
      setRawDockUsbpdIdsLocked(Utils.readFile("/sys/class/sec/ccic/usbpd_ids", ""));
    }
    DockState dockState2 = this.mDockState;
    int resolveDockType = this.mRawDockState == 0 ? -1 : resolveDockType(this.mRawDockUsbpdIds);
    if (resolveDockType != -1) {
      if (resolveDockType == 10001 || resolveDockType == 10004 || resolveDockType == 10006) {
        dockState = new DockState(resolveDockType, true, this.mRawDockUsbpdIds);
      } else {
        dockState = new DockState(resolveDockType, true, this.mRawDockUsbpdIds, true);
      }
    } else if (this.mIsExternalDisplayConnected
        && ((dockState2.isUndocked() || dockState2.getType() == 10002)
            && (displayInfo = this.mConnectedDisplay) != null
            && displayInfo.getType() != 1001)) {
      dockState = new DockState(10002, true, this.mRawDockUsbpdIds, true);
    } else {
      dockState = new DockState();
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateDockStatusLocked(), new=" + dockState + ", old=" + dockState2);
    }
    if (dockState2.getType() == dockState.getType()) {
      return false;
    }
    this.mDockState = dockState;
    this.mStateManager.setDockState(dockState);
    if (DesktopModeFeature.SUPPORT_SFC
        && (currentUserId = this.mStateManager.getState().getCurrentUserId()) != -10000) {
      this.mSettingsHelper.backupOrRestoreSuperFastCharging(dockState.isDexPad(), currentUserId);
    }
    if (dockState.isUndocked()) {
      BatteryChangedListener batteryChangedListener = this.mBatteryChangedListener;
      if (batteryChangedListener != null) {
        batteryChangedListener.unregister();
        this.mBatteryChangedListener = null;
      }
    } else {
      BatteryChangedListener batteryChangedListener2 = new BatteryChangedListener();
      this.mBatteryChangedListener = batteryChangedListener2;
      batteryChangedListener2.register();
    }
    DesktopModeSettings.setSettings(this.mResolver, "dock_usbpd_ids", dockState.getType());
    return true;
  }

  public final void initializeExternalDisplayStatusLocked(Display[] displayArr) {
    DisplayInfo displayInfo = null;
    boolean z = false;
    for (Display display : displayArr) {
      DisplayInfo displayInfo2 = new DisplayInfo(display);
      this.mDisplays.put(displayInfo2.getDisplayId(), displayInfo2);
      if (isSupportedDisplayType(displayInfo2.getType())) {
        z = true;
        displayInfo = displayInfo2;
      }
    }
    if (z) {
      this.mIsExternalDisplayConnected = true;
      this.mConnectedDisplay = displayInfo;
      this.mStateManager.setExternalDisplayConnected(true, displayInfo);
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "initializeExternalDisplayStatusLocked(), mDisplays=" + this.mDisplays);
    }
  }

  public final void updateExternalDisplayStatus(boolean z, int i) {
    DisplayInfo displayInfo;
    boolean z2 = true;
    if (z) {
      Display display = this.mDisplayManager.getDisplay(i);
      synchronized (this.mLock) {
        if (display != null) {
          DisplayInfo displayInfo2 = new DisplayInfo(display);
          this.mDisplays.put(i, displayInfo2);
          if (isSupportedDisplayType(displayInfo2.getType())) {
            this.mConnectedDisplay = displayInfo2;
            if (!this.mIsExternalDisplayConnected) {
              setExternalDisplayConnectedLocked(true, displayInfo2);
            } else {
              this.mStateManager.setExternalDisplayUpdated(displayInfo2);
            }
          }
        }
      }
    } else {
      synchronized (this.mLock) {
        DisplayInfo displayInfo3 = (DisplayInfo) this.mDisplays.removeReturnOld(i);
        if (displayInfo3 != null
            && isSupportedDisplayType(displayInfo3.getType())
            && this.mIsExternalDisplayConnected) {
          int size = this.mDisplays.size();
          int i2 = 0;
          while (true) {
            if (i2 >= size) {
              z2 = false;
              displayInfo = null;
              break;
            } else {
              displayInfo = (DisplayInfo) this.mDisplays.valueAt(i2);
              if (isSupportedDisplayType(displayInfo.getType())) {
                break;
              } else {
                i2++;
              }
            }
          }
          this.mConnectedDisplay = displayInfo;
          if (z2) {
            this.mStateManager.setExternalDisplayUpdated(displayInfo);
          } else if (displayInfo3.getType() == 2) {
            checkExternalDisplayConnectedLocked();
          } else {
            setExternalDisplayConnectedLocked(false, null);
          }
        }
      }
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateExternalDisplayStatus(), mDisplays=" + this.mDisplays);
    }
  }

  public final void updateInputDeviceStatusLocked() {
    boolean z = false;
    for (int i : this.mInputManager.getInputDeviceIds()) {
      InputDevice inputDevice = this.mInputManager.getInputDevice(i);
      if (inputDevice != null && inputDevice.isExternal()) {
        boolean isMouse = isMouse(inputDevice);
        z |= isMouse;
        if (isMouse) {
          if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "Connected mouse=" + inputDevice);
          }
          this.mConnectedMouse = inputDevice;
        }
      }
    }
    boolean z2 = this.mIsMouseConnected != z;
    this.mIsMouseConnected = z;
    if (this.mIsBtMouseDeepSleep) {
      this.mIsMouseConnected = true;
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "Enter the BT mouse deep sleep routine in updateInputDeviceStatusLocked()");
      }
      this.mIsBtMouseDeepSleep = false;
    }
    if (z2) {
      this.mStateManager.setMouseConnected(z);
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateInputDeviceStatusLocked(), mIsMouseConnected=" + this.mIsMouseConnected);
    }
  }

  public final boolean isMouse(InputDevice inputDevice) {
    return (inputDevice.getSources() & 14) != 0;
  }

  public final void updatePogoKeyboardStatus(boolean z) {
    this.mIsPogoKeyboardConnected = z;
    this.mStateManager.setPogoKeyboardConnected(z);
  }

  public void logConnectedAccessoryInformationLocked(State state) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.mRawDockUsbpdIds);
    sb.append("#");
    sb.append(getCoverType(state));
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "Log extra data = " + ((Object) sb));
    }
    DesktopModeLogger.log(this.mContext, "LCAI", sb.toString());
  }

  public final int getCoverType(State state) {
    if (state.getCoverState().attached) {
      return state.getCoverState().type;
    }
    return -1;
  }

  public final void setForcedInternalScreenModeLocked(boolean z, PrintWriter printWriter) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "ADB command received; setForcedInternalScreenModeLocked(), enabled=" + z);
    }
    if (printWriter != null) {
      if (z == this.mForcedInternalScreenModeEnabled) {
        StringBuilder sb = new StringBuilder();
        sb.append("Internal screen DeX mode is already turned ");
        sb.append(z ? "on!" : "off!");
        printWriter.println(sb.toString());
        return;
      }
      StringBuilder sb2 = new StringBuilder();
      sb2.append(z ? "Entering" : "Exiting");
      sb2.append(" internal screen DeX mode...");
      printWriter.println(sb2.toString());
    }
    this.mForcedInternalScreenModeEnabled = z;
    this.mStateManager.setForcedInternalScreenModeEnabled(z);
    updateDockStatusLocked();
  }

  public final void setExternalDisplayConnectedLocked(boolean z, DisplayInfo displayInfo) {
    this.mIsExternalDisplayConnected = z;
    updateDockStatusLocked();
    this.mStateManager.setExternalDisplayConnected(z, displayInfo);
  }

  public final void checkExternalDisplayConnectedLocked() {
    if (Utils.readFile("/sys/class/dp_sec/dex") == 0) {
      setExternalDisplayConnectedLocked(false, null);
    } else {
      this.mHandler.postDelayed(
          new Runnable() { // from class:
            // com.android.server.desktopmode.HardwareManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              HardwareManager.this.lambda$checkExternalDisplayConnectedLocked$0();
            }
          },
          2000L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$checkExternalDisplayConnectedLocked$0() {
    int size = this.mDisplays.size();
    for (int i = 0; i < size; i++) {
      if (isSupportedDisplayType(((DisplayInfo) this.mDisplays.valueAt(i)).getType())) {
        return;
      }
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m12w(TAG, "checkExternalDisplayConnectedLocked(), Reconnection time out!");
    }
    checkExternalDisplayConnectedLocked();
  }

  public final void setLowRefreshRate(boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setLowRefreshRate(), enter=" + z);
    }
    if (z) {
      try {
        this.mRefreshRateToken =
            this.mIDisplayManager.acquireLowRefreshRateToken((IBinder) null, TAG);
        return;
      } catch (RemoteException e) {
        Log.m9e(TAG, "RemoteException is occurred", e);
        return;
      }
    }
    try {
      IRefreshRateToken iRefreshRateToken = this.mRefreshRateToken;
      if (iRefreshRateToken != null) {
        iRefreshRateToken.release();
        this.mRefreshRateToken = null;
      }
    } catch (RemoteException e2) {
      Log.m9e(TAG, "LowRefreshRate() release failed", e2);
    }
  }

  public static int resolveDockType(String str) {
    if (TextUtils.isEmpty(str)) {
      return -1;
    }
    Integer num = (Integer) sSupportedDockUsbpdIds.get(str);
    if (num != null) {
      return num.intValue();
    }
    for (Pair pair : sSupportedDockUsbpdIdRanges) {
      if (((Range) pair.first).contains((Range) str)) {
        return ((Integer) pair.second).intValue();
      }
    }
    return -1;
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println("Current " + HardwareManager.class.getSimpleName() + " state:");
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.println("mConnectedDisplay=" + this.mConnectedDisplay);
      indentingPrintWriter.println("mConnectedMouse=" + this.mConnectedMouse);
      indentingPrintWriter.println("mDisplays=" + this.mDisplays);
      indentingPrintWriter.println("mDockState=" + this.mDockState);
      indentingPrintWriter.println(
          "mForcedInternalScreenModeEnabled=" + this.mForcedInternalScreenModeEnabled);
      indentingPrintWriter.println("mIsBtMouseDeepSleep=" + this.mIsBtMouseDeepSleep);
      indentingPrintWriter.println(
          "mIsExternalDisplayConnected=" + this.mIsExternalDisplayConnected);
      indentingPrintWriter.println("mIsMouseConnected=" + this.mIsMouseConnected);
      indentingPrintWriter.println("mIsPogoKeyboardConnected=" + this.mIsPogoKeyboardConnected);
      indentingPrintWriter.println("mRawDockState=" + Utils.dockStateToString(this.mRawDockState));
      indentingPrintWriter.println("mRawDockUsbpdIds=" + this.mRawDockUsbpdIds);
      indentingPrintWriter.decreaseIndent();
    }
  }

  public int command(PrintWriter printWriter, String str) {
    synchronized (this.mLock) {
      boolean z = true;
      if ("on".equals(str)) {
        setForcedInternalScreenModeLocked(true, printWriter);
        return 0;
      }
      if ("off".equals(str)) {
        setForcedInternalScreenModeLocked(false, printWriter);
        return 0;
      }
      if (!"toggle".equals(str)) {
        return -1;
      }
      if (this.mForcedInternalScreenModeEnabled) {
        z = false;
      }
      setForcedInternalScreenModeLocked(z, printWriter);
      return 0;
    }
  }

  public class PogoKeyboardChangedListener extends BroadcastReceiver {
    public PogoKeyboardChangedListener() {}

    public final void register() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
      Intent registerReceiverAsUser =
          HardwareManager.this.mContext.registerReceiverAsUser(
              this, UserHandle.ALL, intentFilter, null, null);
      if (registerReceiverAsUser != null) {
        String action = registerReceiverAsUser.getAction();
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(HardwareManager.TAG, "sticky intent action=" + action);
        }
        if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(action)) {
          HardwareManager.this.updatePogoKeyboardStatus(
              registerReceiverAsUser.getBooleanExtra("status", false));
        }
      }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(HardwareManager.TAG, "onReceive(), action=" + action);
      }
      if ("com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(action)) {
        HardwareManager.this.updatePogoKeyboardStatus(intent.getBooleanExtra("status", false));
      }
    }
  }

  public class BatteryChangedListener extends BroadcastReceiver {
    public BatteryChangedListener() {}

    public void register() {
      updateWiredChargingStatus(
          HardwareManager.this.mStateManager.getState(),
          HardwareManager.this.mContext.registerReceiverAsUser(
              this,
              UserHandle.ALL,
              new IntentFilter("android.intent.action.BATTERY_CHANGED"),
              null,
              null));
    }

    public void unregister() {
      HardwareManager.this.mContext.unregisterReceiver(this);
      HardwareManager.this.mStateManager.setWiredCharging(false);
    }

    public final void updateWiredChargingStatus(State state, Intent intent) {
      if (intent == null) {
        return;
      }
      int intExtra = intent.getIntExtra("plugged", -1);
      boolean z = true;
      if (intExtra != 1 && intExtra != 2) {
        z = false;
      }
      if (state.isWiredCharging() != z) {
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(HardwareManager.TAG, "isWiredCharging=" + z);
        }
        HardwareManager.this.mStateManager.setWiredCharging(z);
      }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      updateWiredChargingStatus(HardwareManager.this.mStateManager.getState(), intent);
    }
  }

  public class DockState {
    public final boolean mDesktopModeSupported;
    public final boolean mIsAdapter;
    public final String mRawDockUsbpdIds;
    public final int mType;

    public DockState() {
      this(10000, false, "");
    }

    public DockState(int i, boolean z, String str) {
      this(i, z, str, false);
    }

    public DockState(int i, boolean z, String str, boolean z2) {
      this.mType = i;
      this.mDesktopModeSupported = z;
      this.mRawDockUsbpdIds = str;
      this.mIsAdapter = z2;
    }

    public int getType() {
      return this.mType;
    }

    public boolean isDesktopModeSupported() {
      return this.mDesktopModeSupported;
    }

    public boolean isUndocked() {
      return this.mType == 10000;
    }

    public boolean isDexStation() {
      return this.mType == 10001;
    }

    public boolean isDexPad() {
      int i = this.mType;
      return i == 10004 || i == 10006;
    }

    public boolean isAdapter() {
      return this.mIsAdapter;
    }

    public boolean isRawDockUsbpdIdSupported() {
      return HardwareManager.resolveDockType(this.mRawDockUsbpdIds) != -1;
    }

    public String toString() {
      return "DockState(mType="
          + dockTypeToString(this.mType)
          + ", mDesktopModeSupported="
          + this.mDesktopModeSupported
          + ", mRawDockUsbpdIds="
          + this.mRawDockUsbpdIds
          + ", mIsAdapter="
          + this.mIsAdapter
          + ")";
    }

    public static String dockTypeToString(int i) {
      switch (i) {
        case 10000:
          return "TYPE_UNDOCKED";
        case 10001:
          return "TYPE_DEX_STATION";
        case 10002:
          return "TYPE_UNOFFICIAL_ADAPTER";
        case 10003:
          return "TYPE_INTERNAL_SCREEN";
        case 10004:
          return "TYPE_DEX_PAD";
        case FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE /* 10005 */:
          return "TYPE_DEX_RESERVED";
        case FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER /* 10006 */:
          return "TYPE_DEX_PAD_RECOVERY";
        case FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO /* 10007 */:
          return "TYPE_MULTIPORT_ADAPTER_1";
        case 10008:
          return "TYPE_MULTIPORT_ADAPTER_2";
        case FrameworkStatsLog.CPU_TIME_PER_UID /* 10009 */:
          return "TYPE_HDMI_ADAPTER";
        case FrameworkStatsLog.CPU_TIME_PER_UID_FREQ /* 10010 */:
          return "TYPE_DEX_CABLE";
        case FrameworkStatsLog.WIFI_ACTIVITY_INFO /* 10011 */:
          return "TYPE_MULTIPORT_ADAPTER_3";
        case FrameworkStatsLog.MODEM_ACTIVITY_INFO /* 10012 */:
          return "TYPE_TARGUS_ADAPTER";
        case FrameworkStatsLog.PROCESS_MEMORY_STATE /* 10013 */:
          return "TYPE_MULTIPORT_ADAPTER_EE_P5400";
        case FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME /* 10014 */:
          return "TYPE_DEX_RESERVED_DONGLE";
        default:
          return "Unknown=" + i;
      }
    }
  }
}
