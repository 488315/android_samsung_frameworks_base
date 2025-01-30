package android.hardware.input;

import android.app.ActivityThread;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.BatteryState;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;
import android.view.VerifiedInputEvent;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class InputManager {
  public static final String ACTION_QUERY_KEYBOARD_LAYOUTS =
      "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS";
  public static final long BLOCK_UNTRUSTED_TOUCHES = 158002302;
  private static final int EXTRA_SW_POGO_KEYBOARD = 0;
  public static final int EXTRA_SW_POGO_KEYBOARD_BIT = 1;
  public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
  public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH = 2;
  public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
  public static final String META_DATA_KEYBOARD_LAYOUTS =
      "android.hardware.input.metadata.KEYBOARD_LAYOUTS";
  public static final int MONITOR_FILTER_ALL = 65535;
  public static final int MONITOR_FILTER_FINGER = 1;
  public static final int MONITOR_FILTER_KEY = 16;
  public static final int MONITOR_FILTER_MOUSE = 4;
  public static final int MONITOR_FILTER_SPEN = 2;
  public static final int SEM_INJECT_INPUT_EVENT_MODE_ASYNC = 0;
  public static final int SEM_INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
  public static final int SEM_INPUT_FEATURE_AOT = 1;
  public static final int SEM_INPUT_FEATURE_MASK = -1;
  public static final int SEM_LID_STATE_CLOSED = 1;
  public static final int SEM_LID_STATE_OPEN = 0;
  public static final int SEM_LID_STATE_UNKNOWN = -1;
  public static final int SWITCH_STATE_OFF = 0;
  public static final int SWITCH_STATE_ON = 1;
  public static final int SWITCH_STATE_UNKNOWN = -1;
  private static final int SW_COVER_ATTACH = 27;
  public static final int SW_COVER_ATTACH_BIT = 134217728;
  private static final int SW_NOTE_PAPER_COVER_ATTACH = 29;
  public static final int SW_NOTE_PAPER_COVER_ATTACH_BIT = 536870912;
  private final Context mContext;
  private final InputManagerGlobal mGlobal;
  private final IInputManager mIm;
  private Boolean mIsStylusPointerIconEnabled = null;
  private static final String TAG = "InputManager";
  private static final boolean DEBUG = Log.isLoggable(TAG, 3);

  public interface InputDeviceBatteryListener {
    void onBatteryStateChanged(int i, long j, BatteryState batteryState);
  }

  public interface InputDeviceListener {
    void onInputDeviceAdded(int i);

    void onInputDeviceChanged(int i);

    void onInputDeviceRemoved(int i);
  }

  public interface KeyboardBacklightListener {
    void onKeyboardBacklightChanged(
        int i, KeyboardBacklightState keyboardBacklightState, boolean z);
  }

  public interface OnTabletModeChangedListener {
    void onTabletModeChanged(long j, boolean z);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface RemappableModifierKey {
    public static final int REMAPPABLE_MODIFIER_KEY_ALT_LEFT = 57;
    public static final int REMAPPABLE_MODIFIER_KEY_ALT_RIGHT = 58;
    public static final int REMAPPABLE_MODIFIER_KEY_CAPS_LOCK = 115;
    public static final int REMAPPABLE_MODIFIER_KEY_CTRL_LEFT = 113;
    public static final int REMAPPABLE_MODIFIER_KEY_CTRL_RIGHT = 114;
    public static final int REMAPPABLE_MODIFIER_KEY_META_LEFT = 117;
    public static final int REMAPPABLE_MODIFIER_KEY_META_RIGHT = 118;
    public static final int REMAPPABLE_MODIFIER_KEY_SHIFT_LEFT = 59;
    public static final int REMAPPABLE_MODIFIER_KEY_SHIFT_RIGHT = 60;
  }

  public interface SemOnLidStateChangedListener {
    void onLidStateChanged(long j, int i);
  }

  public interface SemOnMultiFingerGestureListener {
    void onMultiFingerGesture(int i, int i2);
  }

  public interface SemOnPointerIconChangedListener {
    void onPointerIconChanged(int i, Bitmap bitmap, float f, float f2);
  }

  public interface SemOnSwitchEventChangedListener {
    void onSwitchEventChanged(int i, int i2, int i3, int i4);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface SwitchState {}

  public InputManager(Context context) {
    InputManagerGlobal inputManagerGlobal = InputManagerGlobal.getInstance();
    this.mGlobal = inputManagerGlobal;
    this.mIm = inputManagerGlobal.getInputManagerService();
    this.mContext = context;
  }

  @Deprecated
  public static InputManager getInstance() {
    return (InputManager)
        ((Application) Objects.requireNonNull(ActivityThread.currentApplication()))
            .getSystemService(InputManager.class);
  }

  public String getVelocityTrackerStrategy() {
    return this.mGlobal.getVelocityTrackerStrategy();
  }

  public InputDevice getInputDevice(int id) {
    return this.mGlobal.getInputDevice(id);
  }

  public InputDevice getInputDeviceByDescriptor(String descriptor) {
    return this.mGlobal.getInputDeviceByDescriptor(descriptor);
  }

  public int[] getInputDeviceIds() {
    return this.mGlobal.getInputDeviceIds();
  }

  public boolean isInputDeviceEnabled(int id) {
    return this.mGlobal.isInputDeviceEnabled(id);
  }

  public void enableInputDevice(int id) {
    this.mGlobal.enableInputDevice(id);
  }

  public void disableInputDevice(int id) {
    this.mGlobal.disableInputDevice(id);
  }

  public void registerInputDeviceListener(InputDeviceListener listener, Handler handler) {
    this.mGlobal.registerInputDeviceListener(listener, handler);
  }

  public void unregisterInputDeviceListener(InputDeviceListener listener) {
    this.mGlobal.unregisterInputDeviceListener(listener);
  }

  public void semRegisterOnMultiFingerGestureListener(
      SemOnMultiFingerGestureListener listener, Handler handler) {
    this.mGlobal.semRegisterOnMultiFingerGestureListener(listener, handler);
  }

  public void semUnregisterOnMultiFingerGestureListener(SemOnMultiFingerGestureListener listener) {
    this.mGlobal.semUnregisterOnMultiFingerGestureListener(listener);
  }

  public void semRegisterOnSwitchEventChangedListener(
      SemOnSwitchEventChangedListener listener, Handler handler) {
    this.mGlobal.semRegisterOnSwitchEventChangedListener(listener, handler);
  }

  public void semUnregisterOnSwitchEventChangedListener(SemOnSwitchEventChangedListener listener) {
    this.mGlobal.semUnregisterOnSwitchEventChangedListener(listener);
  }

  public int isInTabletMode() {
    try {
      return this.mIm.isInTabletMode();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public int semCheckInputFeature() {
    try {
      return this.mIm.checkInputFeature();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public boolean semSupportPogoDevice() {
    try {
      return this.mIm.supportPogoDevice();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void registerOnTabletModeChangedListener(
      OnTabletModeChangedListener listener, Handler handler) {
    this.mGlobal.registerOnTabletModeChangedListener(listener, handler);
  }

  public void unregisterOnTabletModeChangedListener(OnTabletModeChangedListener listener) {
    this.mGlobal.unregisterOnTabletModeChangedListener(listener);
  }

  public int isMicMuted() {
    try {
      return this.mIm.isMicMuted();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public KeyboardLayout[] getKeyboardLayouts() {
    try {
      return this.mIm.getKeyboardLayouts();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public List<String> getKeyboardLayoutDescriptorsForInputDevice(InputDevice device) {
    KeyboardLayout[] layouts = getKeyboardLayoutsForInputDevice(device.getIdentifier());
    List<String> res = new ArrayList<>();
    for (KeyboardLayout kl : layouts) {
      res.add(kl.getDescriptor());
    }
    return res;
  }

  public String getKeyboardLayoutTypeForLayoutDescriptor(String layoutDescriptor) {
    KeyboardLayout[] layouts = getKeyboardLayouts();
    for (KeyboardLayout kl : layouts) {
      if (layoutDescriptor.equals(kl.getDescriptor())) {
        return kl.getLayoutType();
      }
    }
    return "";
  }

  public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) {
    return this.mGlobal.getKeyboardLayoutsForInputDevice(identifier);
  }

  public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor) {
    if (keyboardLayoutDescriptor == null) {
      throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
    }
    try {
      return this.mIm.getKeyboardLayout(keyboardLayoutDescriptor);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier) {
    try {
      return this.mIm.getCurrentKeyboardLayoutForInputDevice(identifier);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setCurrentKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) {
    this.mGlobal.setCurrentKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
  }

  public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) {
    if (identifier == null) {
      throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }
    try {
      return this.mIm.getEnabledKeyboardLayoutsForInputDevice(identifier);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void addKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) {
    if (identifier == null) {
      throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }
    if (keyboardLayoutDescriptor == null) {
      throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
    }
    try {
      this.mIm.addKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void removeKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) {
    if (identifier == null) {
      throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }
    if (keyboardLayoutDescriptor == null) {
      throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
    }
    try {
      this.mIm.removeKeyboardLayoutForInputDevice(identifier, keyboardLayoutDescriptor);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public KeyboardLayout getKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype) {
    return null;
  }

  public void setKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype,
      String keyboardLayoutDescriptor) {}

  public void remapModifierKey(int fromKey, int toKey) {
    try {
      this.mIm.remapModifierKey(fromKey, toKey);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void clearAllModifierKeyRemappings() {
    try {
      this.mIm.clearAllModifierKeyRemappings();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public Map<Integer, Integer> getModifierKeyRemapping() {
    try {
      return this.mIm.getModifierKeyRemapping();
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public TouchCalibration getTouchCalibration(String inputDeviceDescriptor, int surfaceRotation) {
    try {
      return this.mIm.getTouchCalibrationForInputDevice(inputDeviceDescriptor, surfaceRotation);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setTouchCalibration(
      String inputDeviceDescriptor, int surfaceRotation, TouchCalibration calibration) {
    try {
      this.mIm.setTouchCalibrationForInputDevice(
          inputDeviceDescriptor, surfaceRotation, calibration);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public String getKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier,
      int userId,
      InputMethodInfo imeInfo,
      InputMethodSubtype imeSubtype) {
    try {
      return this.mIm.getKeyboardLayoutForInputDevice(identifier, userId, imeInfo, imeSubtype);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setKeyboardLayoutForInputDevice(
      InputDeviceIdentifier identifier,
      int userId,
      InputMethodInfo imeInfo,
      InputMethodSubtype imeSubtype,
      String keyboardLayoutDescriptor) {
    if (identifier == null) {
      throw new IllegalArgumentException("identifier must not be null");
    }
    if (keyboardLayoutDescriptor == null) {
      throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
    }
    try {
      this.mIm.setKeyboardLayoutForInputDevice(
          identifier, userId, imeInfo, imeSubtype, keyboardLayoutDescriptor);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public KeyboardLayout[] getKeyboardLayoutListForInputDevice(
      InputDeviceIdentifier identifier,
      int userId,
      InputMethodInfo imeInfo,
      InputMethodSubtype imeSubtype) {
    if (identifier == null) {
      throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
    }
    try {
      return this.mIm.getKeyboardLayoutListForInputDevice(identifier, userId, imeInfo, imeSubtype);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void tryPointerSpeed(int speed) {
    if (speed < -7 || speed > 7) {
      throw new IllegalArgumentException("speed out of range");
    }
    try {
      this.mIm.tryPointerSpeed(speed);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public float getMaximumObscuringOpacityForTouch() {
    return InputSettings.getMaximumObscuringOpacityForTouch(this.mContext);
  }

  public boolean[] deviceHasKeys(int[] keyCodes) {
    return deviceHasKeys(-1, keyCodes);
  }

  public boolean[] deviceHasKeys(int id, int[] keyCodes) {
    return this.mGlobal.deviceHasKeys(id, keyCodes);
  }

  public int getKeyCodeForKeyLocation(int deviceId, int locationKeyCode) {
    return this.mGlobal.getKeyCodeForKeyLocation(deviceId, locationKeyCode);
  }

  public boolean injectInputEvent(InputEvent event, int mode, int targetUid) {
    return this.mGlobal.injectInputEvent(event, mode, targetUid);
  }

  public boolean injectInputEvent(InputEvent event, int mode) {
    return this.mGlobal.injectInputEvent(event, mode);
  }

  public boolean semInjectInputEvent(InputEvent event, int mode) {
    return this.mGlobal.injectInputEvent(event, mode);
  }

  public VerifiedInputEvent verifyInputEvent(InputEvent event) {
    try {
      return this.mIm.verifyInputEvent(event);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void setPointerIconType(int iconId) {
    this.mGlobal.setPointerIconType(iconId);
  }

  private void hidden_setPointerIconType(int iconId) {
    setPointerIconType(iconId);
  }

  public void setCustomPointerIcon(PointerIcon icon) {
    this.mGlobal.setCustomPointerIcon(icon);
  }

  public boolean isStylusPointerIconEnabled() {
    if (this.mIsStylusPointerIconEnabled == null) {
      this.mIsStylusPointerIconEnabled =
          Boolean.valueOf(InputSettings.isStylusPointerIconEnabled(this.mContext));
    }
    return this.mIsStylusPointerIconEnabled.booleanValue();
  }

  public void requestPointerCapture(IBinder windowToken, boolean enable) {
    this.mGlobal.requestPointerCapture(windowToken, enable);
  }

  public InputMonitor monitorGestureInput(String name, int displayId) {
    return this.mGlobal.monitorGestureInput(name, displayId, 65535);
  }

  public InputMonitor monitorGestureInput(String name, int displayId, int filter) {
    return this.mGlobal.monitorGestureInput(name, displayId, filter);
  }

  public InputChannel monitorInput(String inputChannelName, int displayId) {
    return monitorInput(inputChannelName, displayId, 65535);
  }

  public InputChannel monitorInput(String inputChannelName, int displayId, int filter) {
    return this.mGlobal.monitorInput(inputChannelName, displayId, filter);
  }

  public InputSensorInfo[] getSensorList(int deviceId) {
    return this.mGlobal.getSensorList(deviceId);
  }

  public boolean enableSensor(
      int deviceId, int sensorType, int samplingPeriodUs, int maxBatchReportLatencyUs) {
    return this.mGlobal.enableSensor(
        deviceId, sensorType, samplingPeriodUs, maxBatchReportLatencyUs);
  }

  public void disableSensor(int deviceId, int sensorType) {
    this.mGlobal.disableSensor(deviceId, sensorType);
  }

  public boolean flushSensor(int deviceId, int sensorType) {
    return this.mGlobal.flushSensor(deviceId, sensorType);
  }

  public boolean registerSensorListener(IInputSensorEventListener listener) {
    return this.mGlobal.registerSensorListener(listener);
  }

  public void unregisterSensorListener(IInputSensorEventListener listener) {
    this.mGlobal.unregisterSensorListener(listener);
  }

  public void addPortAssociation(String inputPort, int displayPort) {
    try {
      this.mIm.addPortAssociation(inputPort, displayPort);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void removePortAssociation(String inputPort) {
    try {
      this.mIm.removePortAssociation(inputPort);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void addUniqueIdAssociation(String inputPort, String displayUniqueId) {
    this.mGlobal.addUniqueIdAssociation(inputPort, displayUniqueId);
  }

  public void removeUniqueIdAssociation(String inputPort) {
    this.mGlobal.removeUniqueIdAssociation(inputPort);
  }

  public HostUsiVersion getHostUsiVersion(Display display) {
    return this.mGlobal.getHostUsiVersion(display);
  }

  public String getInputDeviceBluetoothAddress(int deviceId) {
    return this.mGlobal.getInputDeviceBluetoothAddress(deviceId);
  }

  public Vibrator getInputDeviceVibrator(int deviceId, int vibratorId) {
    return new InputDeviceVibrator(deviceId, vibratorId);
  }

  public void cancelCurrentTouch() {
    this.mGlobal.cancelCurrentTouch();
  }

  public void pilferPointers(IBinder inputChannelToken) {
    this.mGlobal.pilferPointers(inputChannelToken);
  }

  public void addInputDeviceBatteryListener(
      int deviceId, Executor executor, InputDeviceBatteryListener listener) {
    this.mGlobal.addInputDeviceBatteryListener(deviceId, executor, listener);
  }

  public void removeInputDeviceBatteryListener(int deviceId, InputDeviceBatteryListener listener) {
    this.mGlobal.removeInputDeviceBatteryListener(deviceId, listener);
  }

  public boolean areTouchpadGesturesAvailable(Context context) {
    return true;
  }

  public void registerKeyboardBacklightListener(
      Executor executor, KeyboardBacklightListener listener) throws IllegalArgumentException {
    this.mGlobal.registerKeyboardBacklightListener(executor, listener);
  }

  public void unregisterKeyboardBacklightListener(KeyboardBacklightListener listener) {
    this.mGlobal.unregisterKeyboardBacklightListener(listener);
  }

  public void setIsStylusFromTouchpad(boolean isStylusFromTouchpad) {
    this.mGlobal.setIsStylusFromTouchpad(isStylusFromTouchpad);
  }

  public int getCurrentSwitchEventState(int mask, boolean isSwitch) {
    try {
      return this.mIm.getCurrentSwitchEventState(mask, isSwitch);
    } catch (RemoteException e) {
      return 0;
    }
  }

  public void setStartedShutdown(boolean isStarted) {
    try {
      this.mIm.setStartedShutdown(isStarted);
    } catch (RemoteException e) {
    }
  }

  public long getLastLidEventTimeNanos() {
    try {
      return this.mIm.getLastLidEventTimeNanos();
    } catch (RemoteException e) {
      return -1L;
    }
  }

  public boolean isUidTouched(int uid) {
    try {
      return this.mIm.isUidTouched(uid);
    } catch (RemoteException e) {
      return false;
    }
  }

  public int semGetScanCodeState(int deviceId, int sourceMask, int scanCode) {
    try {
      return this.mIm.getScanCodeState(deviceId, sourceMask, scanCode);
    } catch (RemoteException e) {
      return -1;
    }
  }

  public int getInboundQueueLength() {
    try {
      return this.mIm.getInboundQueueLength();
    } catch (RemoteException e) {
      return -1;
    }
  }

  public int getGlobalMetaState(int type) {
    try {
      return this.mIm.getGlobalMetaState(type);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call getGlobalMetaState()");
      return 0;
    }
  }

  public void forceFadeIcon(int type) {
    try {
      this.mIm.forceFadeIcon(type);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call forceFadeIcon()");
    }
  }

  public void updateWirelessKeyboardShareStatus() {
    try {
      this.mIm.updateWirelessKeyboardShareStatus();
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call updateWirelessKeyboardShareStatus()");
    }
  }

  public void removeDeviceWirelessKeyboardShare(String device, int index) {
    try {
      this.mIm.removeDeviceWirelessKeyboardShare(device, index);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call removeDeviceWirelessKeyboardShare()");
    }
  }

  public void changeDeviceWirelessKeyboardShare(String device, int index) {
    try {
      this.mIm.changeDeviceWirelessKeyboardShare(device, index);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call changeDeviceWirelessKeyboardShare()");
    }
  }

  public boolean addDeviceWirelessKeyboardShare(int index) {
    try {
      return this.mIm.addDeviceWirelessKeyboardShare(index);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call addDeviceWirelessKeyboardShare()");
      return true;
    }
  }

  public boolean switchDeviceWirelessKeyboardShare(String device, int index) {
    try {
      return this.mIm.switchDeviceWirelessKeyboardShare(device, index);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call switchDeviceWirelessKeyboardShare()");
      return true;
    }
  }

  public void setHostRoleWirelessKeyboardShare() {
    try {
      this.mIm.setHostRoleWirelessKeyboardShare();
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call setHostRoleWirelessKeyboardShare()");
    }
  }

  public void connectByBtDevice(BluetoothDevice addr) {
    try {
      this.mIm.connectByBtDevice(addr);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call connectByBtDevice()");
    }
  }

  public void setShowAllTouches(boolean enabled) {
    try {
      this.mIm.setShowAllTouches(enabled);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call setShowAllTouches(boolean)");
    }
  }

  public long semGetMotionIdleTimeMillis() {
    try {
      return this.mIm.semGetMotionIdleTimeMillis(false);
    } catch (RemoteException ex) {
      throw ex.rethrowFromSystemServer();
    }
  }

  public void semSetWakeKeyDynamically(String packageName, boolean isPut, String keyCodes) {
    this.mGlobal.semSetWakeKeyDynamically(packageName, isPut, keyCodes);
  }

  public int semGetLidState() {
    return this.mGlobal.semGetLidState();
  }

  public void semRegisterOnLidStateChangedListener(
      SemOnLidStateChangedListener listener, Handler handler) {
    this.mGlobal.semRegisterOnLidStateChangedListener(listener, handler);
  }

  public void semUnregisterOnLidStateChangedListener(SemOnLidStateChangedListener listener) {
    this.mGlobal.semUnregisterOnLidStateChangedListener(listener);
  }

  public boolean semSetTspEnabled(SemTspCommandType cmdType, boolean enable) {
    try {
      return this.mIm.setTspEnabled(cmdType.getvalue(), enable);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Could not call setTspEnabled()");
      return false;
    } catch (NullPointerException e2) {
      Log.m102w(TAG, "SemTspCommandType should not be null");
      return false;
    }
  }

  public enum SemTspCommandType {
    EMPTY(0),
    SPAY(1),
    STYLUS(2),
    BRUSH(3);

    private int mValue;

    SemTspCommandType(int value) {
      this.mValue = value;
    }

    public int getvalue() {
      return this.mValue;
    }
  }

  public void semRegisterOnPointerIconChangedListener(
      SemOnPointerIconChangedListener listener, Handler handler) {
    this.mGlobal.semRegisterOnPointerIconChangedListener(listener, handler);
  }

  public void semUnregisterOnPointerIconChangedListener(SemOnPointerIconChangedListener listener) {
    this.mGlobal.semUnregisterOnPointerIconChangedListener(listener);
  }

  private int findOnPointerIconChangedListenerLocked(SemOnPointerIconChangedListener listener) {
    return this.mGlobal.findOnPointerIconChangedListenerLocked(listener);
  }
}
