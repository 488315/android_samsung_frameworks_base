package android.hardware.input;

import android.Manifest;
import android.app.ActivityThread;
import android.bluetooth.BluetoothDevice;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.os.Binder;
import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVibratorStateListener;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.VibrationEffect;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.PointerIcon;
import android.view.VerifiedInputEvent;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.samsung.android.edge.EdgeManagerInternal;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IInputManager extends IInterface {
  boolean addDeviceWirelessKeyboardShare(int i) throws RemoteException;

  void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str)
      throws RemoteException;

  void addPortAssociation(String str, int i) throws RemoteException;

  void addUniqueIdAssociation(String str, String str2) throws RemoteException;

  void cancelCurrentTouch() throws RemoteException;

  void cancelVibrate(int i, IBinder iBinder) throws RemoteException;

  void changeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

  int checkInputFeature() throws RemoteException;

  void clearAllModifierKeyRemappings() throws RemoteException;

  void closeLightSession(int i, IBinder iBinder) throws RemoteException;

  void connectByBtDevice(BluetoothDevice bluetoothDevice) throws RemoteException;

  void disableInputDevice(int i) throws RemoteException;

  void disableSensor(int i, int i2) throws RemoteException;

  void enableInputDevice(int i) throws RemoteException;

  boolean enableSensor(int i, int i2, int i3, int i4) throws RemoteException;

  boolean flushSensor(int i, int i2) throws RemoteException;

  void forceFadeIcon(int i) throws RemoteException;

  IInputDeviceBatteryState getBatteryState(int i) throws RemoteException;

  String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier)
      throws RemoteException;

  int getCurrentSwitchEventState(int i, boolean z) throws RemoteException;

  PointerIcon getDefaultPointerIcon() throws RemoteException;

  int getDisplayIdForPointerIcon() throws RemoteException;

  String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier)
      throws RemoteException;

  PointerIcon getForcedDefaultPointerIcon() throws RemoteException;

  int getGlobalMetaState(int i) throws RemoteException;

  HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws RemoteException;

  int getInboundQueueLength() throws RemoteException;

  InputDevice getInputDevice(int i) throws RemoteException;

  String getInputDeviceBluetoothAddress(int i) throws RemoteException;

  int[] getInputDeviceIds() throws RemoteException;

  int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException;

  KeyboardLayout getKeyboardLayout(String str) throws RemoteException;

  String getKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype)
      throws RemoteException;

  KeyboardLayout[] getKeyboardLayoutListForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype)
      throws RemoteException;

  KeyboardLayout[] getKeyboardLayouts() throws RemoteException;

  KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier)
      throws RemoteException;

  long getLastLidEventTimeNanos() throws RemoteException;

  int getLidState() throws RemoteException;

  LightState getLightState(int i, int i2) throws RemoteException;

  List<Light> getLights(int i) throws RemoteException;

  Map getModifierKeyRemapping() throws RemoteException;

  int getPointerIconType() throws RemoteException;

  int getScanCodeState(int i, int i2, int i3) throws RemoteException;

  InputSensorInfo[] getSensorList(int i) throws RemoteException;

  int getToolTypeForDefaultPointerIcon() throws RemoteException;

  TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException;

  String getVelocityTrackerStrategy() throws RemoteException;

  int[] getVibratorIds(int i) throws RemoteException;

  boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException;

  boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException;

  boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) throws RemoteException;

  boolean isDefaultPointerIconChanged() throws RemoteException;

  int isInTabletMode() throws RemoteException;

  boolean isInputDeviceEnabled(int i) throws RemoteException;

  int isMicMuted() throws RemoteException;

  boolean isUidTouched(int i) throws RemoteException;

  boolean isVibrating(int i) throws RemoteException;

  InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) throws RemoteException;

  InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2)
      throws RemoteException;

  InputChannel monitorInputForBinder(String str, int i, int i2) throws RemoteException;

  void notifyQuickAccess(int i, float f, float f2) throws RemoteException;

  void openLightSession(int i, String str, IBinder iBinder) throws RemoteException;

  void pilferPointers(IBinder iBinder) throws RemoteException;

  void registerBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener)
      throws RemoteException;

  void registerInputDevicesChangedListener(
      IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException;

  void registerKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener)
      throws RemoteException;

  void registerLidStateChangedListener(ISemLidStateChangedListener iSemLidStateChangedListener)
      throws RemoteException;

  void registerMultiFingerGestureListener(IMultiFingerGestureListener iMultiFingerGestureListener)
      throws RemoteException;

  void registerPointerIconChangedListener(IPointerIconChangedListener iPointerIconChangedListener)
      throws RemoteException;

  boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener)
      throws RemoteException;

  void registerSwitchEventChangedListener(ISwitchEventChangedListener iSwitchEventChangedListener)
      throws RemoteException;

  void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener)
      throws RemoteException;

  boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener)
      throws RemoteException;

  void registerWirelessKeyboardShareChangedListener(
      IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener)
      throws RemoteException;

  void remapModifierKey(int i, int i2) throws RemoteException;

  void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

  void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str)
      throws RemoteException;

  void removePortAssociation(String str) throws RemoteException;

  void removeUniqueIdAssociation(String str) throws RemoteException;

  void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException;

  long semGetMotionIdleTimeMillis(boolean z) throws RemoteException;

  void setCurrentKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException;

  void setCustomHoverIcon(PointerIcon pointerIcon) throws RemoteException;

  void setCustomPointerIcon(PointerIcon pointerIcon) throws RemoteException;

  void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException;

  void setDisplayIdForPointerIcon(int i) throws RemoteException;

  void setHostRoleWirelessKeyboardShare() throws RemoteException;

  void setKeyboardLayoutForInputDevice(
      InputDeviceIdentifier inputDeviceIdentifier,
      int i,
      InputMethodInfo inputMethodInfo,
      InputMethodSubtype inputMethodSubtype,
      String str)
      throws RemoteException;

  void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder)
      throws RemoteException;

  void setPointerIconType(int i) throws RemoteException;

  void setShowAllTouches(boolean z) throws RemoteException;

  void setStartedShutdown(boolean z) throws RemoteException;

  void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration)
      throws RemoteException;

  boolean setTspEnabled(int i, boolean z) throws RemoteException;

  void setWakeKeyDynamically(String str, boolean z, String str2) throws RemoteException;

  boolean supportPogoDevice() throws RemoteException;

  boolean switchDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

  void tryPointerSpeed(int i) throws RemoteException;

  void unregisterBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener)
      throws RemoteException;

  void unregisterKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener)
      throws RemoteException;

  void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener)
      throws RemoteException;

  boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener)
      throws RemoteException;

  void updateWirelessKeyboardShareStatus() throws RemoteException;

  VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) throws RemoteException;

  void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) throws RemoteException;

  void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder)
      throws RemoteException;

  public static class Default implements IInputManager {
    @Override // android.hardware.input.IInputManager
    public String getVelocityTrackerStrategy() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public InputDevice getInputDevice(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public int[] getInputDeviceIds() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void enableInputDevice(int deviceId) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void disableInputDevice(int deviceId) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public int getKeyCodeForKeyLocation(int deviceId, int locationKeyCode) throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void tryPointerSpeed(int speed) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public boolean injectInputEventToTarget(InputEvent ev, int mode, int targetUid)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public VerifiedInputEvent verifyInputEvent(InputEvent ev) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public TouchCalibration getTouchCalibrationForInputDevice(
        String inputDeviceDescriptor, int rotation) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void setTouchCalibrationForInputDevice(
        String inputDeviceDescriptor, int rotation, TouchCalibration calibration)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void setCurrentKeyboardLayoutForInputDevice(
        InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void addKeyboardLayoutForInputDevice(
        InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void removeKeyboardLayoutForInputDevice(
        InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public String getKeyboardLayoutForInputDevice(
        InputDeviceIdentifier identifier,
        int userId,
        InputMethodInfo imeInfo,
        InputMethodSubtype imeSubtype)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void setKeyboardLayoutForInputDevice(
        InputDeviceIdentifier identifier,
        int userId,
        InputMethodInfo imeInfo,
        InputMethodSubtype imeSubtype,
        String keyboardLayoutDescriptor)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public KeyboardLayout[] getKeyboardLayoutListForInputDevice(
        InputDeviceIdentifier identifier,
        int userId,
        InputMethodInfo imeInfo,
        InputMethodSubtype imeSubtype)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void remapModifierKey(int fromKey, int toKey) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void clearAllModifierKeyRemappings() throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public Map getModifierKeyRemapping() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int isInTabletMode() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public int getLidState() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void registerLidStateChangedListener(ISemLidStateChangedListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public long getLastLidEventTimeNanos() throws RemoteException {
      return 0L;
    }

    @Override // android.hardware.input.IInputManager
    public int checkInputFeature() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public boolean supportPogoDevice() throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void registerMultiFingerGestureListener(IMultiFingerGestureListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void registerSwitchEventChangedListener(ISwitchEventChangedListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int getCurrentSwitchEventState(int mask, boolean isSwitch) throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void registerTabletModeChangedListener(ITabletModeChangedListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int isMicMuted() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void vibrate(int deviceId, VibrationEffect effect, IBinder token)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void vibrateCombined(int deviceId, CombinedVibration vibration, IBinder token)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int[] getVibratorIds(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public boolean isVibrating(int deviceId) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public boolean registerVibratorStateListener(int deviceId, IVibratorStateListener listener)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public boolean unregisterVibratorStateListener(int deviceId, IVibratorStateListener listener)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public IInputDeviceBatteryState getBatteryState(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void setPointerIconType(int typeId) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void setCustomPointerIcon(PointerIcon icon) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void setDefaultPointerIcon(int toolType, PointerIcon icon, boolean forced)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public PointerIcon getDefaultPointerIcon() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public PointerIcon getForcedDefaultPointerIcon() throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public boolean isDefaultPointerIconChanged() throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public int getToolTypeForDefaultPointerIcon() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void setShowAllTouches(boolean enabled) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void requestPointerCapture(IBinder inputChannelToken, boolean enabled)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public InputMonitor monitorGestureInput(IBinder token, String name, int displayId)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public InputMonitor monitorGestureInputFiltered(
        IBinder token, String name, int displayId, int filter) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public InputChannel monitorInputForBinder(String inputChannelName, int displayId, int filter)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void registerPointerIconChangedListener(IPointerIconChangedListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void setCustomHoverIcon(PointerIcon icon) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void setDisplayIdForPointerIcon(int displayId) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int getDisplayIdForPointerIcon() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public int getPointerIconType() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public boolean setTspEnabled(int cmdtype, boolean enable) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public int getInboundQueueLength() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public int getScanCodeState(int deviceId, int sourceMask, int scanCode) throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public void setWakeKeyDynamically(String packageName, boolean isPut, String keyCodes)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public int getGlobalMetaState(int type) throws RemoteException {
      return 0;
    }

    @Override // android.hardware.input.IInputManager
    public long semGetMotionIdleTimeMillis(boolean useOnlyActionDown) throws RemoteException {
      return 0L;
    }

    @Override // android.hardware.input.IInputManager
    public void setStartedShutdown(boolean isStarted) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean isUidTouched(int uid) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void forceFadeIcon(int type) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void notifyQuickAccess(int info, float x, float y) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void registerWirelessKeyboardShareChangedListener(
        IWirelessKeyboardShareChangedListener listener) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void updateWirelessKeyboardShareStatus() throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void removeDeviceWirelessKeyboardShare(String device, int index)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void changeDeviceWirelessKeyboardShare(String device, int index)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean addDeviceWirelessKeyboardShare(int index) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public boolean switchDeviceWirelessKeyboardShare(String device, int index)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void setHostRoleWirelessKeyboardShare() throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void connectByBtDevice(BluetoothDevice addr) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void addPortAssociation(String inputPort, int displayPort) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void removePortAssociation(String inputPort) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void addUniqueIdAssociation(String inputPort, String displayUniqueId)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void removeUniqueIdAssociation(String inputPort) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public InputSensorInfo[] getSensorList(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public boolean registerSensorListener(IInputSensorEventListener listener)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void unregisterSensorListener(IInputSensorEventListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean enableSensor(
        int deviceId, int sensorType, int samplingPeriodUs, int maxBatchReportLatencyUs)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public void disableSensor(int deviceId, int sensorType) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public boolean flushSensor(int deviceId, int sensorType) throws RemoteException {
      return false;
    }

    @Override // android.hardware.input.IInputManager
    public List<Light> getLights(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public LightState getLightState(int deviceId, int lightId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void setLightStates(int deviceId, int[] lightIds, LightState[] states, IBinder token)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void openLightSession(int deviceId, String opPkg, IBinder token)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void closeLightSession(int deviceId, IBinder token) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void cancelCurrentTouch() throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void registerBatteryListener(int deviceId, IInputDeviceBatteryListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void unregisterBatteryListener(int deviceId, IInputDeviceBatteryListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public String getInputDeviceBluetoothAddress(int deviceId) throws RemoteException {
      return null;
    }

    @Override // android.hardware.input.IInputManager
    public void pilferPointers(IBinder inputChannelToken) throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void registerKeyboardBacklightListener(IKeyboardBacklightListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public void unregisterKeyboardBacklightListener(IKeyboardBacklightListener listener)
        throws RemoteException {}

    @Override // android.hardware.input.IInputManager
    public HostUsiVersion getHostUsiVersionFromDisplayConfig(int displayId) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInputManager {
    public static final String DESCRIPTOR = "android.hardware.input.IInputManager";
    static final int TRANSACTION_addDeviceWirelessKeyboardShare = 80;
    static final int TRANSACTION_addKeyboardLayoutForInputDevice = 21;
    static final int TRANSACTION_addPortAssociation = 84;
    static final int TRANSACTION_addUniqueIdAssociation = 86;
    static final int TRANSACTION_cancelCurrentTouch = 99;
    static final int TRANSACTION_cancelVibrate = 43;
    static final int TRANSACTION_changeDeviceWirelessKeyboardShare = 79;
    static final int TRANSACTION_checkInputFeature = 34;
    static final int TRANSACTION_clearAllModifierKeyRemappings = 27;
    static final int TRANSACTION_closeLightSession = 98;
    static final int TRANSACTION_connectByBtDevice = 83;
    static final int TRANSACTION_disableInputDevice = 6;
    static final int TRANSACTION_disableSensor = 92;
    static final int TRANSACTION_enableInputDevice = 5;
    static final int TRANSACTION_enableSensor = 91;
    static final int TRANSACTION_flushSensor = 93;
    static final int TRANSACTION_forceFadeIcon = 74;
    static final int TRANSACTION_getBatteryState = 48;
    static final int TRANSACTION_getCurrentKeyboardLayoutForInputDevice = 18;
    static final int TRANSACTION_getCurrentSwitchEventState = 38;
    static final int TRANSACTION_getDefaultPointerIcon = 52;
    static final int TRANSACTION_getDisplayIdForPointerIcon = 64;
    static final int TRANSACTION_getEnabledKeyboardLayoutsForInputDevice = 20;
    static final int TRANSACTION_getForcedDefaultPointerIcon = 53;
    static final int TRANSACTION_getGlobalMetaState = 70;
    static final int TRANSACTION_getHostUsiVersionFromDisplayConfig = 106;
    static final int TRANSACTION_getInboundQueueLength = 67;
    static final int TRANSACTION_getInputDevice = 2;
    static final int TRANSACTION_getInputDeviceBluetoothAddress = 102;
    static final int TRANSACTION_getInputDeviceIds = 3;
    static final int TRANSACTION_getKeyCodeForKeyLocation = 8;
    static final int TRANSACTION_getKeyboardLayout = 17;
    static final int TRANSACTION_getKeyboardLayoutForInputDevice = 23;
    static final int TRANSACTION_getKeyboardLayoutListForInputDevice = 25;
    static final int TRANSACTION_getKeyboardLayouts = 15;
    static final int TRANSACTION_getKeyboardLayoutsForInputDevice = 16;
    static final int TRANSACTION_getLastLidEventTimeNanos = 33;
    static final int TRANSACTION_getLidState = 31;
    static final int TRANSACTION_getLightState = 95;
    static final int TRANSACTION_getLights = 94;
    static final int TRANSACTION_getModifierKeyRemapping = 28;
    static final int TRANSACTION_getPointerIconType = 65;
    static final int TRANSACTION_getScanCodeState = 68;
    static final int TRANSACTION_getSensorList = 88;
    static final int TRANSACTION_getToolTypeForDefaultPointerIcon = 55;
    static final int TRANSACTION_getTouchCalibrationForInputDevice = 13;
    static final int TRANSACTION_getVelocityTrackerStrategy = 1;
    static final int TRANSACTION_getVibratorIds = 44;
    static final int TRANSACTION_hasKeys = 7;
    static final int TRANSACTION_injectInputEvent = 10;
    static final int TRANSACTION_injectInputEventToTarget = 11;
    static final int TRANSACTION_isDefaultPointerIconChanged = 54;
    static final int TRANSACTION_isInTabletMode = 30;
    static final int TRANSACTION_isInputDeviceEnabled = 4;
    static final int TRANSACTION_isMicMuted = 40;
    static final int TRANSACTION_isUidTouched = 73;
    static final int TRANSACTION_isVibrating = 45;
    static final int TRANSACTION_monitorGestureInput = 58;
    static final int TRANSACTION_monitorGestureInputFiltered = 59;
    static final int TRANSACTION_monitorInputForBinder = 60;
    static final int TRANSACTION_notifyQuickAccess = 75;
    static final int TRANSACTION_openLightSession = 97;
    static final int TRANSACTION_pilferPointers = 103;
    static final int TRANSACTION_registerBatteryListener = 100;
    static final int TRANSACTION_registerInputDevicesChangedListener = 29;
    static final int TRANSACTION_registerKeyboardBacklightListener = 104;
    static final int TRANSACTION_registerLidStateChangedListener = 32;
    static final int TRANSACTION_registerMultiFingerGestureListener = 36;
    static final int TRANSACTION_registerPointerIconChangedListener = 61;
    static final int TRANSACTION_registerSensorListener = 89;
    static final int TRANSACTION_registerSwitchEventChangedListener = 37;
    static final int TRANSACTION_registerTabletModeChangedListener = 39;
    static final int TRANSACTION_registerVibratorStateListener = 46;
    static final int TRANSACTION_registerWirelessKeyboardShareChangedListener = 76;
    static final int TRANSACTION_remapModifierKey = 26;
    static final int TRANSACTION_removeDeviceWirelessKeyboardShare = 78;
    static final int TRANSACTION_removeKeyboardLayoutForInputDevice = 22;
    static final int TRANSACTION_removePortAssociation = 85;
    static final int TRANSACTION_removeUniqueIdAssociation = 87;
    static final int TRANSACTION_requestPointerCapture = 57;
    static final int TRANSACTION_semGetMotionIdleTimeMillis = 71;
    static final int TRANSACTION_setCurrentKeyboardLayoutForInputDevice = 19;
    static final int TRANSACTION_setCustomHoverIcon = 62;
    static final int TRANSACTION_setCustomPointerIcon = 50;
    static final int TRANSACTION_setDefaultPointerIcon = 51;
    static final int TRANSACTION_setDisplayIdForPointerIcon = 63;
    static final int TRANSACTION_setHostRoleWirelessKeyboardShare = 82;
    static final int TRANSACTION_setKeyboardLayoutForInputDevice = 24;
    static final int TRANSACTION_setLightStates = 96;
    static final int TRANSACTION_setPointerIconType = 49;
    static final int TRANSACTION_setShowAllTouches = 56;
    static final int TRANSACTION_setStartedShutdown = 72;
    static final int TRANSACTION_setTouchCalibrationForInputDevice = 14;
    static final int TRANSACTION_setTspEnabled = 66;
    static final int TRANSACTION_setWakeKeyDynamically = 69;
    static final int TRANSACTION_supportPogoDevice = 35;
    static final int TRANSACTION_switchDeviceWirelessKeyboardShare = 81;
    static final int TRANSACTION_tryPointerSpeed = 9;
    static final int TRANSACTION_unregisterBatteryListener = 101;
    static final int TRANSACTION_unregisterKeyboardBacklightListener = 105;
    static final int TRANSACTION_unregisterSensorListener = 90;
    static final int TRANSACTION_unregisterVibratorStateListener = 47;
    static final int TRANSACTION_updateWirelessKeyboardShareStatus = 77;
    static final int TRANSACTION_verifyInputEvent = 12;
    static final int TRANSACTION_vibrate = 41;
    static final int TRANSACTION_vibrateCombined = 42;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static IInputManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IInputManager)) {
        return (IInputManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "getVelocityTrackerStrategy";
        case 2:
          return "getInputDevice";
        case 3:
          return "getInputDeviceIds";
        case 4:
          return "isInputDeviceEnabled";
        case 5:
          return "enableInputDevice";
        case 6:
          return "disableInputDevice";
        case 7:
          return "hasKeys";
        case 8:
          return "getKeyCodeForKeyLocation";
        case 9:
          return "tryPointerSpeed";
        case 10:
          return "injectInputEvent";
        case 11:
          return "injectInputEventToTarget";
        case 12:
          return "verifyInputEvent";
        case 13:
          return "getTouchCalibrationForInputDevice";
        case 14:
          return "setTouchCalibrationForInputDevice";
        case 15:
          return "getKeyboardLayouts";
        case 16:
          return "getKeyboardLayoutsForInputDevice";
        case 17:
          return "getKeyboardLayout";
        case 18:
          return "getCurrentKeyboardLayoutForInputDevice";
        case 19:
          return "setCurrentKeyboardLayoutForInputDevice";
        case 20:
          return "getEnabledKeyboardLayoutsForInputDevice";
        case 21:
          return "addKeyboardLayoutForInputDevice";
        case 22:
          return "removeKeyboardLayoutForInputDevice";
        case 23:
          return "getKeyboardLayoutForInputDevice";
        case 24:
          return "setKeyboardLayoutForInputDevice";
        case 25:
          return "getKeyboardLayoutListForInputDevice";
        case 26:
          return "remapModifierKey";
        case 27:
          return "clearAllModifierKeyRemappings";
        case 28:
          return "getModifierKeyRemapping";
        case 29:
          return "registerInputDevicesChangedListener";
        case 30:
          return "isInTabletMode";
        case 31:
          return "getLidState";
        case 32:
          return "registerLidStateChangedListener";
        case 33:
          return "getLastLidEventTimeNanos";
        case 34:
          return "checkInputFeature";
        case 35:
          return "supportPogoDevice";
        case 36:
          return "registerMultiFingerGestureListener";
        case 37:
          return "registerSwitchEventChangedListener";
        case 38:
          return "getCurrentSwitchEventState";
        case 39:
          return "registerTabletModeChangedListener";
        case 40:
          return "isMicMuted";
        case 41:
          return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
        case 42:
          return "vibrateCombined";
        case 43:
          return "cancelVibrate";
        case 44:
          return "getVibratorIds";
        case 45:
          return "isVibrating";
        case 46:
          return "registerVibratorStateListener";
        case 47:
          return "unregisterVibratorStateListener";
        case 48:
          return "getBatteryState";
        case 49:
          return "setPointerIconType";
        case 50:
          return "setCustomPointerIcon";
        case 51:
          return "setDefaultPointerIcon";
        case 52:
          return "getDefaultPointerIcon";
        case 53:
          return "getForcedDefaultPointerIcon";
        case 54:
          return "isDefaultPointerIconChanged";
        case 55:
          return "getToolTypeForDefaultPointerIcon";
        case 56:
          return "setShowAllTouches";
        case 57:
          return "requestPointerCapture";
        case 58:
          return "monitorGestureInput";
        case 59:
          return "monitorGestureInputFiltered";
        case 60:
          return "monitorInputForBinder";
        case 61:
          return "registerPointerIconChangedListener";
        case 62:
          return "setCustomHoverIcon";
        case 63:
          return "setDisplayIdForPointerIcon";
        case 64:
          return "getDisplayIdForPointerIcon";
        case 65:
          return "getPointerIconType";
        case 66:
          return "setTspEnabled";
        case 67:
          return "getInboundQueueLength";
        case 68:
          return "getScanCodeState";
        case 69:
          return "setWakeKeyDynamically";
        case 70:
          return "getGlobalMetaState";
        case 71:
          return "semGetMotionIdleTimeMillis";
        case 72:
          return "setStartedShutdown";
        case 73:
          return "isUidTouched";
        case 74:
          return "forceFadeIcon";
        case 75:
          return "notifyQuickAccess";
        case 76:
          return "registerWirelessKeyboardShareChangedListener";
        case 77:
          return "updateWirelessKeyboardShareStatus";
        case 78:
          return "removeDeviceWirelessKeyboardShare";
        case 79:
          return "changeDeviceWirelessKeyboardShare";
        case 80:
          return "addDeviceWirelessKeyboardShare";
        case 81:
          return "switchDeviceWirelessKeyboardShare";
        case 82:
          return "setHostRoleWirelessKeyboardShare";
        case 83:
          return "connectByBtDevice";
        case 84:
          return "addPortAssociation";
        case 85:
          return "removePortAssociation";
        case 86:
          return "addUniqueIdAssociation";
        case 87:
          return "removeUniqueIdAssociation";
        case 88:
          return "getSensorList";
        case 89:
          return "registerSensorListener";
        case 90:
          return "unregisterSensorListener";
        case 91:
          return "enableSensor";
        case 92:
          return "disableSensor";
        case 93:
          return "flushSensor";
        case 94:
          return "getLights";
        case 95:
          return "getLightState";
        case 96:
          return "setLightStates";
        case 97:
          return "openLightSession";
        case 98:
          return "closeLightSession";
        case 99:
          return "cancelCurrentTouch";
        case 100:
          return "registerBatteryListener";
        case 101:
          return "unregisterBatteryListener";
        case 102:
          return "getInputDeviceBluetoothAddress";
        case 103:
          return "pilferPointers";
        case 104:
          return "registerKeyboardBacklightListener";
        case 105:
          return "unregisterKeyboardBacklightListener";
        case 106:
          return "getHostUsiVersionFromDisplayConfig";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      boolean[] _arg3;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _result = getVelocityTrackerStrategy();
              reply.writeNoException();
              reply.writeString(_result);
              return true;
            case 2:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              InputDevice _result2 = getInputDevice(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 3:
              int[] _result3 = getInputDeviceIds();
              reply.writeNoException();
              reply.writeIntArray(_result3);
              return true;
            case 4:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = isInputDeviceEnabled(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              enableInputDevice(_arg03);
              reply.writeNoException();
              return true;
            case 6:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              disableInputDevice(_arg04);
              reply.writeNoException();
              return true;
            case 7:
              int _arg05 = data.readInt();
              int _arg1 = data.readInt();
              int[] _arg2 = data.createIntArray();
              int _arg3_length = data.readInt();
              if (_arg3_length < 0) {
                _arg3 = null;
              } else {
                _arg3 = new boolean[_arg3_length];
              }
              data.enforceNoDataAvail();
              boolean _result5 = hasKeys(_arg05, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              reply.writeBooleanArray(_arg3);
              return true;
            case 8:
              int _arg06 = data.readInt();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              int _result6 = getKeyCodeForKeyLocation(_arg06, _arg12);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 9:
              int _arg07 = data.readInt();
              data.enforceNoDataAvail();
              tryPointerSpeed(_arg07);
              reply.writeNoException();
              return true;
            case 10:
              InputEvent _arg08 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result7 = injectInputEvent(_arg08, _arg13);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 11:
              InputEvent _arg09 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
              int _arg14 = data.readInt();
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result8 = injectInputEventToTarget(_arg09, _arg14, _arg22);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 12:
              InputEvent _arg010 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
              data.enforceNoDataAvail();
              VerifiedInputEvent _result9 = verifyInputEvent(_arg010);
              reply.writeNoException();
              reply.writeTypedObject(_result9, 1);
              return true;
            case 13:
              String _arg011 = data.readString();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              TouchCalibration _result10 = getTouchCalibrationForInputDevice(_arg011, _arg15);
              reply.writeNoException();
              reply.writeTypedObject(_result10, 1);
              return true;
            case 14:
              String _arg012 = data.readString();
              int _arg16 = data.readInt();
              TouchCalibration _arg23 =
                  (TouchCalibration) data.readTypedObject(TouchCalibration.CREATOR);
              data.enforceNoDataAvail();
              setTouchCalibrationForInputDevice(_arg012, _arg16, _arg23);
              reply.writeNoException();
              return true;
            case 15:
              KeyboardLayout[] _result11 = getKeyboardLayouts();
              reply.writeNoException();
              reply.writeTypedArray(_result11, 1);
              return true;
            case 16:
              InputDeviceIdentifier _arg013 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              data.enforceNoDataAvail();
              KeyboardLayout[] _result12 = getKeyboardLayoutsForInputDevice(_arg013);
              reply.writeNoException();
              reply.writeTypedArray(_result12, 1);
              return true;
            case 17:
              String _arg014 = data.readString();
              data.enforceNoDataAvail();
              KeyboardLayout _result13 = getKeyboardLayout(_arg014);
              reply.writeNoException();
              reply.writeTypedObject(_result13, 1);
              return true;
            case 18:
              InputDeviceIdentifier _arg015 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              data.enforceNoDataAvail();
              String _result14 = getCurrentKeyboardLayoutForInputDevice(_arg015);
              reply.writeNoException();
              reply.writeString(_result14);
              return true;
            case 19:
              InputDeviceIdentifier _arg016 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              setCurrentKeyboardLayoutForInputDevice(_arg016, _arg17);
              reply.writeNoException();
              return true;
            case 20:
              InputDeviceIdentifier _arg017 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              data.enforceNoDataAvail();
              String[] _result15 = getEnabledKeyboardLayoutsForInputDevice(_arg017);
              reply.writeNoException();
              reply.writeStringArray(_result15);
              return true;
            case 21:
              InputDeviceIdentifier _arg018 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              addKeyboardLayoutForInputDevice(_arg018, _arg18);
              reply.writeNoException();
              return true;
            case 22:
              InputDeviceIdentifier _arg019 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              String _arg19 = data.readString();
              data.enforceNoDataAvail();
              removeKeyboardLayoutForInputDevice(_arg019, _arg19);
              reply.writeNoException();
              return true;
            case 23:
              InputDeviceIdentifier _arg020 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              int _arg110 = data.readInt();
              InputMethodInfo _arg24 =
                  (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
              InputMethodSubtype _arg32 =
                  (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
              data.enforceNoDataAvail();
              String _result16 = getKeyboardLayoutForInputDevice(_arg020, _arg110, _arg24, _arg32);
              reply.writeNoException();
              reply.writeString(_result16);
              return true;
            case 24:
              InputDeviceIdentifier _arg021 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              int _arg111 = data.readInt();
              InputMethodInfo _arg25 =
                  (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
              InputMethodSubtype _arg33 =
                  (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
              String _arg4 = data.readString();
              data.enforceNoDataAvail();
              setKeyboardLayoutForInputDevice(_arg021, _arg111, _arg25, _arg33, _arg4);
              reply.writeNoException();
              return true;
            case 25:
              InputDeviceIdentifier _arg022 =
                  (InputDeviceIdentifier) data.readTypedObject(InputDeviceIdentifier.CREATOR);
              int _arg112 = data.readInt();
              InputMethodInfo _arg26 =
                  (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
              InputMethodSubtype _arg34 =
                  (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
              data.enforceNoDataAvail();
              KeyboardLayout[] _result17 =
                  getKeyboardLayoutListForInputDevice(_arg022, _arg112, _arg26, _arg34);
              reply.writeNoException();
              reply.writeTypedArray(_result17, 1);
              return true;
            case 26:
              int _arg023 = data.readInt();
              int _arg113 = data.readInt();
              data.enforceNoDataAvail();
              remapModifierKey(_arg023, _arg113);
              reply.writeNoException();
              return true;
            case 27:
              clearAllModifierKeyRemappings();
              reply.writeNoException();
              return true;
            case 28:
              Map _result18 = getModifierKeyRemapping();
              reply.writeNoException();
              reply.writeMap(_result18);
              return true;
            case 29:
              IInputDevicesChangedListener _arg024 =
                  IInputDevicesChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerInputDevicesChangedListener(_arg024);
              reply.writeNoException();
              return true;
            case 30:
              int _result19 = isInTabletMode();
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 31:
              int _result20 = getLidState();
              reply.writeNoException();
              reply.writeInt(_result20);
              return true;
            case 32:
              ISemLidStateChangedListener _arg025 =
                  ISemLidStateChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerLidStateChangedListener(_arg025);
              reply.writeNoException();
              return true;
            case 33:
              long _result21 = getLastLidEventTimeNanos();
              reply.writeNoException();
              reply.writeLong(_result21);
              return true;
            case 34:
              int _result22 = checkInputFeature();
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 35:
              boolean _result23 = supportPogoDevice();
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 36:
              IMultiFingerGestureListener _arg026 =
                  IMultiFingerGestureListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerMultiFingerGestureListener(_arg026);
              reply.writeNoException();
              return true;
            case 37:
              ISwitchEventChangedListener _arg027 =
                  ISwitchEventChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerSwitchEventChangedListener(_arg027);
              reply.writeNoException();
              return true;
            case 38:
              int _arg028 = data.readInt();
              boolean _arg114 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result24 = getCurrentSwitchEventState(_arg028, _arg114);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 39:
              ITabletModeChangedListener _arg029 =
                  ITabletModeChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerTabletModeChangedListener(_arg029);
              reply.writeNoException();
              return true;
            case 40:
              int _result25 = isMicMuted();
              reply.writeNoException();
              reply.writeInt(_result25);
              return true;
            case 41:
              int _arg030 = data.readInt();
              VibrationEffect _arg115 =
                  (VibrationEffect) data.readTypedObject(VibrationEffect.CREATOR);
              IBinder _arg27 = data.readStrongBinder();
              data.enforceNoDataAvail();
              vibrate(_arg030, _arg115, _arg27);
              reply.writeNoException();
              return true;
            case 42:
              int _arg031 = data.readInt();
              CombinedVibration _arg116 =
                  (CombinedVibration) data.readTypedObject(CombinedVibration.CREATOR);
              IBinder _arg28 = data.readStrongBinder();
              data.enforceNoDataAvail();
              vibrateCombined(_arg031, _arg116, _arg28);
              reply.writeNoException();
              return true;
            case 43:
              int _arg032 = data.readInt();
              IBinder _arg117 = data.readStrongBinder();
              data.enforceNoDataAvail();
              cancelVibrate(_arg032, _arg117);
              reply.writeNoException();
              return true;
            case 44:
              int _arg033 = data.readInt();
              data.enforceNoDataAvail();
              int[] _result26 = getVibratorIds(_arg033);
              reply.writeNoException();
              reply.writeIntArray(_result26);
              return true;
            case 45:
              int _arg034 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result27 = isVibrating(_arg034);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 46:
              int _arg035 = data.readInt();
              IVibratorStateListener _arg118 =
                  IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result28 = registerVibratorStateListener(_arg035, _arg118);
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            case 47:
              int _arg036 = data.readInt();
              IVibratorStateListener _arg119 =
                  IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result29 = unregisterVibratorStateListener(_arg036, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 48:
              int _arg037 = data.readInt();
              data.enforceNoDataAvail();
              IInputDeviceBatteryState _result30 = getBatteryState(_arg037);
              reply.writeNoException();
              reply.writeTypedObject(_result30, 1);
              return true;
            case 49:
              int _arg038 = data.readInt();
              data.enforceNoDataAvail();
              setPointerIconType(_arg038);
              reply.writeNoException();
              return true;
            case 50:
              PointerIcon _arg039 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
              data.enforceNoDataAvail();
              setCustomPointerIcon(_arg039);
              reply.writeNoException();
              return true;
            case 51:
              int _arg040 = data.readInt();
              PointerIcon _arg120 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
              boolean _arg29 = data.readBoolean();
              data.enforceNoDataAvail();
              setDefaultPointerIcon(_arg040, _arg120, _arg29);
              reply.writeNoException();
              return true;
            case 52:
              PointerIcon _result31 = getDefaultPointerIcon();
              reply.writeNoException();
              reply.writeTypedObject(_result31, 1);
              return true;
            case 53:
              PointerIcon _result32 = getForcedDefaultPointerIcon();
              reply.writeNoException();
              reply.writeTypedObject(_result32, 1);
              return true;
            case 54:
              boolean _result33 = isDefaultPointerIconChanged();
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 55:
              int _result34 = getToolTypeForDefaultPointerIcon();
              reply.writeNoException();
              reply.writeInt(_result34);
              return true;
            case 56:
              boolean _arg041 = data.readBoolean();
              data.enforceNoDataAvail();
              setShowAllTouches(_arg041);
              reply.writeNoException();
              return true;
            case 57:
              IBinder _arg042 = data.readStrongBinder();
              boolean _arg121 = data.readBoolean();
              data.enforceNoDataAvail();
              requestPointerCapture(_arg042, _arg121);
              return true;
            case 58:
              IBinder _arg043 = data.readStrongBinder();
              String _arg122 = data.readString();
              int _arg210 = data.readInt();
              data.enforceNoDataAvail();
              InputMonitor _result35 = monitorGestureInput(_arg043, _arg122, _arg210);
              reply.writeNoException();
              reply.writeTypedObject(_result35, 1);
              return true;
            case 59:
              IBinder _arg044 = data.readStrongBinder();
              String _arg123 = data.readString();
              int _arg211 = data.readInt();
              int _arg35 = data.readInt();
              data.enforceNoDataAvail();
              InputMonitor _result36 =
                  monitorGestureInputFiltered(_arg044, _arg123, _arg211, _arg35);
              reply.writeNoException();
              reply.writeTypedObject(_result36, 1);
              return true;
            case 60:
              String _arg045 = data.readString();
              int _arg124 = data.readInt();
              int _arg212 = data.readInt();
              data.enforceNoDataAvail();
              InputChannel _result37 = monitorInputForBinder(_arg045, _arg124, _arg212);
              reply.writeNoException();
              reply.writeTypedObject(_result37, 1);
              return true;
            case 61:
              IPointerIconChangedListener _arg046 =
                  IPointerIconChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerPointerIconChangedListener(_arg046);
              reply.writeNoException();
              return true;
            case 62:
              PointerIcon _arg047 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
              data.enforceNoDataAvail();
              setCustomHoverIcon(_arg047);
              reply.writeNoException();
              return true;
            case 63:
              int _arg048 = data.readInt();
              data.enforceNoDataAvail();
              setDisplayIdForPointerIcon(_arg048);
              reply.writeNoException();
              return true;
            case 64:
              int _result38 = getDisplayIdForPointerIcon();
              reply.writeNoException();
              reply.writeInt(_result38);
              return true;
            case 65:
              int _result39 = getPointerIconType();
              reply.writeNoException();
              reply.writeInt(_result39);
              return true;
            case 66:
              int _arg049 = data.readInt();
              boolean _arg125 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result40 = setTspEnabled(_arg049, _arg125);
              reply.writeNoException();
              reply.writeBoolean(_result40);
              return true;
            case 67:
              int _result41 = getInboundQueueLength();
              reply.writeNoException();
              reply.writeInt(_result41);
              return true;
            case 68:
              int _arg050 = data.readInt();
              int _arg126 = data.readInt();
              int _arg213 = data.readInt();
              data.enforceNoDataAvail();
              int _result42 = getScanCodeState(_arg050, _arg126, _arg213);
              reply.writeNoException();
              reply.writeInt(_result42);
              return true;
            case 69:
              String _arg051 = data.readString();
              boolean _arg127 = data.readBoolean();
              String _arg214 = data.readString();
              data.enforceNoDataAvail();
              setWakeKeyDynamically(_arg051, _arg127, _arg214);
              reply.writeNoException();
              return true;
            case 70:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              int _result43 = getGlobalMetaState(_arg052);
              reply.writeNoException();
              reply.writeInt(_result43);
              return true;
            case 71:
              boolean _arg053 = data.readBoolean();
              data.enforceNoDataAvail();
              long _result44 = semGetMotionIdleTimeMillis(_arg053);
              reply.writeNoException();
              reply.writeLong(_result44);
              return true;
            case 72:
              boolean _arg054 = data.readBoolean();
              data.enforceNoDataAvail();
              setStartedShutdown(_arg054);
              reply.writeNoException();
              return true;
            case 73:
              int _arg055 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result45 = isUidTouched(_arg055);
              reply.writeNoException();
              reply.writeBoolean(_result45);
              return true;
            case 74:
              int _arg056 = data.readInt();
              data.enforceNoDataAvail();
              forceFadeIcon(_arg056);
              reply.writeNoException();
              return true;
            case 75:
              int _arg057 = data.readInt();
              float _arg128 = data.readFloat();
              float _arg215 = data.readFloat();
              data.enforceNoDataAvail();
              notifyQuickAccess(_arg057, _arg128, _arg215);
              reply.writeNoException();
              return true;
            case 76:
              IWirelessKeyboardShareChangedListener _arg058 =
                  IWirelessKeyboardShareChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerWirelessKeyboardShareChangedListener(_arg058);
              reply.writeNoException();
              return true;
            case 77:
              updateWirelessKeyboardShareStatus();
              reply.writeNoException();
              return true;
            case 78:
              String _arg059 = data.readString();
              int _arg129 = data.readInt();
              data.enforceNoDataAvail();
              removeDeviceWirelessKeyboardShare(_arg059, _arg129);
              reply.writeNoException();
              return true;
            case 79:
              String _arg060 = data.readString();
              int _arg130 = data.readInt();
              data.enforceNoDataAvail();
              changeDeviceWirelessKeyboardShare(_arg060, _arg130);
              reply.writeNoException();
              return true;
            case 80:
              int _arg061 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result46 = addDeviceWirelessKeyboardShare(_arg061);
              reply.writeNoException();
              reply.writeBoolean(_result46);
              return true;
            case 81:
              String _arg062 = data.readString();
              int _arg131 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result47 = switchDeviceWirelessKeyboardShare(_arg062, _arg131);
              reply.writeNoException();
              reply.writeBoolean(_result47);
              return true;
            case 82:
              setHostRoleWirelessKeyboardShare();
              reply.writeNoException();
              return true;
            case 83:
              BluetoothDevice _arg063 =
                  (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
              data.enforceNoDataAvail();
              connectByBtDevice(_arg063);
              reply.writeNoException();
              return true;
            case 84:
              String _arg064 = data.readString();
              int _arg132 = data.readInt();
              data.enforceNoDataAvail();
              addPortAssociation(_arg064, _arg132);
              reply.writeNoException();
              return true;
            case 85:
              String _arg065 = data.readString();
              data.enforceNoDataAvail();
              removePortAssociation(_arg065);
              reply.writeNoException();
              return true;
            case 86:
              String _arg066 = data.readString();
              String _arg133 = data.readString();
              data.enforceNoDataAvail();
              addUniqueIdAssociation(_arg066, _arg133);
              reply.writeNoException();
              return true;
            case 87:
              String _arg067 = data.readString();
              data.enforceNoDataAvail();
              removeUniqueIdAssociation(_arg067);
              reply.writeNoException();
              return true;
            case 88:
              int _arg068 = data.readInt();
              data.enforceNoDataAvail();
              InputSensorInfo[] _result48 = getSensorList(_arg068);
              reply.writeNoException();
              reply.writeTypedArray(_result48, 1);
              return true;
            case 89:
              IInputSensorEventListener _arg069 =
                  IInputSensorEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result49 = registerSensorListener(_arg069);
              reply.writeNoException();
              reply.writeBoolean(_result49);
              return true;
            case 90:
              IInputSensorEventListener _arg070 =
                  IInputSensorEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterSensorListener(_arg070);
              reply.writeNoException();
              return true;
            case 91:
              int _arg071 = data.readInt();
              int _arg134 = data.readInt();
              int _arg216 = data.readInt();
              int _arg36 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result50 = enableSensor(_arg071, _arg134, _arg216, _arg36);
              reply.writeNoException();
              reply.writeBoolean(_result50);
              return true;
            case 92:
              int _arg072 = data.readInt();
              int _arg135 = data.readInt();
              data.enforceNoDataAvail();
              disableSensor(_arg072, _arg135);
              reply.writeNoException();
              return true;
            case 93:
              int _arg073 = data.readInt();
              int _arg136 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result51 = flushSensor(_arg073, _arg136);
              reply.writeNoException();
              reply.writeBoolean(_result51);
              return true;
            case 94:
              int _arg074 = data.readInt();
              data.enforceNoDataAvail();
              List<Light> _result52 = getLights(_arg074);
              reply.writeNoException();
              reply.writeTypedList(_result52, 1);
              return true;
            case 95:
              int _arg075 = data.readInt();
              int _arg137 = data.readInt();
              data.enforceNoDataAvail();
              LightState _result53 = getLightState(_arg075, _arg137);
              reply.writeNoException();
              reply.writeTypedObject(_result53, 1);
              return true;
            case 96:
              int _arg076 = data.readInt();
              int[] _arg138 = data.createIntArray();
              LightState[] _arg217 = (LightState[]) data.createTypedArray(LightState.CREATOR);
              IBinder _arg37 = data.readStrongBinder();
              data.enforceNoDataAvail();
              setLightStates(_arg076, _arg138, _arg217, _arg37);
              reply.writeNoException();
              return true;
            case 97:
              int _arg077 = data.readInt();
              String _arg139 = data.readString();
              IBinder _arg218 = data.readStrongBinder();
              data.enforceNoDataAvail();
              openLightSession(_arg077, _arg139, _arg218);
              reply.writeNoException();
              return true;
            case 98:
              int _arg078 = data.readInt();
              IBinder _arg140 = data.readStrongBinder();
              data.enforceNoDataAvail();
              closeLightSession(_arg078, _arg140);
              reply.writeNoException();
              return true;
            case 99:
              cancelCurrentTouch();
              reply.writeNoException();
              return true;
            case 100:
              int _arg079 = data.readInt();
              IInputDeviceBatteryListener _arg141 =
                  IInputDeviceBatteryListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerBatteryListener(_arg079, _arg141);
              reply.writeNoException();
              return true;
            case 101:
              int _arg080 = data.readInt();
              IInputDeviceBatteryListener _arg142 =
                  IInputDeviceBatteryListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterBatteryListener(_arg080, _arg142);
              reply.writeNoException();
              return true;
            case 102:
              int _arg081 = data.readInt();
              data.enforceNoDataAvail();
              String _result54 = getInputDeviceBluetoothAddress(_arg081);
              reply.writeNoException();
              reply.writeString(_result54);
              return true;
            case 103:
              IBinder _arg082 = data.readStrongBinder();
              data.enforceNoDataAvail();
              pilferPointers(_arg082);
              reply.writeNoException();
              return true;
            case 104:
              IKeyboardBacklightListener _arg083 =
                  IKeyboardBacklightListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerKeyboardBacklightListener(_arg083);
              reply.writeNoException();
              return true;
            case 105:
              IKeyboardBacklightListener _arg084 =
                  IKeyboardBacklightListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterKeyboardBacklightListener(_arg084);
              reply.writeNoException();
              return true;
            case 106:
              int _arg085 = data.readInt();
              data.enforceNoDataAvail();
              HostUsiVersion _result55 = getHostUsiVersionFromDisplayConfig(_arg085);
              reply.writeNoException();
              reply.writeTypedObject(_result55, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInputManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.hardware.input.IInputManager
      public String getVelocityTrackerStrategy() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public InputDevice getInputDevice(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          InputDevice _result = (InputDevice) _reply.readTypedObject(InputDevice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int[] getInputDeviceIds() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean isInputDeviceEnabled(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void enableInputDevice(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void disableInputDevice(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(sourceMask);
          _data.writeIntArray(keyCodes);
          _data.writeInt(keyExists.length);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          _reply.readBooleanArray(keyExists);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getKeyCodeForKeyLocation(int deviceId, int locationKeyCode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(locationKeyCode);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void tryPointerSpeed(int speed) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(speed);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean injectInputEvent(InputEvent ev, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(ev, 0);
          _data.writeInt(mode);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean injectInputEventToTarget(InputEvent ev, int mode, int targetUid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(ev, 0);
          _data.writeInt(mode);
          _data.writeInt(targetUid);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public VerifiedInputEvent verifyInputEvent(InputEvent ev) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(ev, 0);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          VerifiedInputEvent _result =
              (VerifiedInputEvent) _reply.readTypedObject(VerifiedInputEvent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public TouchCalibration getTouchCalibrationForInputDevice(
          String inputDeviceDescriptor, int rotation) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputDeviceDescriptor);
          _data.writeInt(rotation);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          TouchCalibration _result =
              (TouchCalibration) _reply.readTypedObject(TouchCalibration.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setTouchCalibrationForInputDevice(
          String inputDeviceDescriptor, int rotation, TouchCalibration calibration)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputDeviceDescriptor);
          _data.writeInt(rotation);
          _data.writeTypedObject(calibration, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          KeyboardLayout[] _result =
              (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          KeyboardLayout[] _result =
              (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(keyboardLayoutDescriptor);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          KeyboardLayout _result = (KeyboardLayout) _reply.readTypedObject(KeyboardLayout.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier identifier)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setCurrentKeyboardLayoutForInputDevice(
          InputDeviceIdentifier identifier, String keyboardLayoutDescriptor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeString(keyboardLayoutDescriptor);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void addKeyboardLayoutForInputDevice(
          InputDeviceIdentifier identifier, String keyboardLayoutDescriptor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeString(keyboardLayoutDescriptor);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void removeKeyboardLayoutForInputDevice(
          InputDeviceIdentifier identifier, String keyboardLayoutDescriptor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeString(keyboardLayoutDescriptor);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public String getKeyboardLayoutForInputDevice(
          InputDeviceIdentifier identifier,
          int userId,
          InputMethodInfo imeInfo,
          InputMethodSubtype imeSubtype)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeInt(userId);
          _data.writeTypedObject(imeInfo, 0);
          _data.writeTypedObject(imeSubtype, 0);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setKeyboardLayoutForInputDevice(
          InputDeviceIdentifier identifier,
          int userId,
          InputMethodInfo imeInfo,
          InputMethodSubtype imeSubtype,
          String keyboardLayoutDescriptor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeInt(userId);
          _data.writeTypedObject(imeInfo, 0);
          _data.writeTypedObject(imeSubtype, 0);
          _data.writeString(keyboardLayoutDescriptor);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public KeyboardLayout[] getKeyboardLayoutListForInputDevice(
          InputDeviceIdentifier identifier,
          int userId,
          InputMethodInfo imeInfo,
          InputMethodSubtype imeSubtype)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(identifier, 0);
          _data.writeInt(userId);
          _data.writeTypedObject(imeInfo, 0);
          _data.writeTypedObject(imeSubtype, 0);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          KeyboardLayout[] _result =
              (KeyboardLayout[]) _reply.createTypedArray(KeyboardLayout.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void remapModifierKey(int fromKey, int toKey) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(fromKey);
          _data.writeInt(toKey);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void clearAllModifierKeyRemappings() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public Map getModifierKeyRemapping() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerInputDevicesChangedListener(IInputDevicesChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int isInTabletMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getLidState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerLidStateChangedListener(ISemLidStateChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public long getLastLidEventTimeNanos() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int checkInputFeature() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean supportPogoDevice() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerMultiFingerGestureListener(IMultiFingerGestureListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerSwitchEventChangedListener(ISwitchEventChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getCurrentSwitchEventState(int mask, boolean isSwitch) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mask);
          _data.writeBoolean(isSwitch);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerTabletModeChangedListener(ITabletModeChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int isMicMuted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void vibrate(int deviceId, VibrationEffect effect, IBinder token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeTypedObject(effect, 0);
          _data.writeStrongBinder(token);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void vibrateCombined(int deviceId, CombinedVibration vibration, IBinder token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeTypedObject(vibration, 0);
          _data.writeStrongBinder(token);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void cancelVibrate(int deviceId, IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongBinder(token);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int[] getVibratorIds(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean isVibrating(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean registerVibratorStateListener(int deviceId, IVibratorStateListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean unregisterVibratorStateListener(int deviceId, IVibratorStateListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public IInputDeviceBatteryState getBatteryState(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          IInputDeviceBatteryState _result =
              (IInputDeviceBatteryState) _reply.readTypedObject(IInputDeviceBatteryState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setPointerIconType(int typeId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(typeId);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setCustomPointerIcon(PointerIcon icon) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(icon, 0);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setDefaultPointerIcon(int toolType, PointerIcon icon, boolean forced)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(toolType);
          _data.writeTypedObject(icon, 0);
          _data.writeBoolean(forced);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public PointerIcon getDefaultPointerIcon() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          PointerIcon _result = (PointerIcon) _reply.readTypedObject(PointerIcon.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public PointerIcon getForcedDefaultPointerIcon() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          PointerIcon _result = (PointerIcon) _reply.readTypedObject(PointerIcon.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean isDefaultPointerIconChanged() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getToolTypeForDefaultPointerIcon() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setShowAllTouches(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void requestPointerCapture(IBinder inputChannelToken, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(inputChannelToken);
          _data.writeBoolean(enabled);
          this.mRemote.transact(57, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public InputMonitor monitorGestureInput(IBinder token, String name, int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeString(name);
          _data.writeInt(displayId);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
          InputMonitor _result = (InputMonitor) _reply.readTypedObject(InputMonitor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public InputMonitor monitorGestureInputFiltered(
          IBinder token, String name, int displayId, int filter) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeString(name);
          _data.writeInt(displayId);
          _data.writeInt(filter);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
          InputMonitor _result = (InputMonitor) _reply.readTypedObject(InputMonitor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public InputChannel monitorInputForBinder(String inputChannelName, int displayId, int filter)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputChannelName);
          _data.writeInt(displayId);
          _data.writeInt(filter);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
          InputChannel _result = (InputChannel) _reply.readTypedObject(InputChannel.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerPointerIconChangedListener(IPointerIconChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setCustomHoverIcon(PointerIcon icon) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(icon, 0);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setDisplayIdForPointerIcon(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getDisplayIdForPointerIcon() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getPointerIconType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean setTspEnabled(int cmdtype, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(cmdtype);
          _data.writeBoolean(enable);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getInboundQueueLength() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getScanCodeState(int deviceId, int sourceMask, int scanCode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(sourceMask);
          _data.writeInt(scanCode);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setWakeKeyDynamically(String packageName, boolean isPut, String keyCodes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeBoolean(isPut);
          _data.writeString(keyCodes);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public int getGlobalMetaState(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public long semGetMotionIdleTimeMillis(boolean useOnlyActionDown) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(useOnlyActionDown);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setStartedShutdown(boolean isStarted) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(isStarted);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean isUidTouched(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void forceFadeIcon(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void notifyQuickAccess(int info, float x, float y) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(info);
          _data.writeFloat(x);
          _data.writeFloat(y);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerWirelessKeyboardShareChangedListener(
          IWirelessKeyboardShareChangedListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void updateWirelessKeyboardShareStatus() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void removeDeviceWirelessKeyboardShare(String device, int index)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(device);
          _data.writeInt(index);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void changeDeviceWirelessKeyboardShare(String device, int index)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(device);
          _data.writeInt(index);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean addDeviceWirelessKeyboardShare(int index) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(index);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean switchDeviceWirelessKeyboardShare(String device, int index)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(device);
          _data.writeInt(index);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setHostRoleWirelessKeyboardShare() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void connectByBtDevice(BluetoothDevice addr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(addr, 0);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void addPortAssociation(String inputPort, int displayPort) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputPort);
          _data.writeInt(displayPort);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void removePortAssociation(String inputPort) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputPort);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void addUniqueIdAssociation(String inputPort, String displayUniqueId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputPort);
          _data.writeString(displayUniqueId);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void removeUniqueIdAssociation(String inputPort) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(inputPort);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public InputSensorInfo[] getSensorList(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
          InputSensorInfo[] _result =
              (InputSensorInfo[]) _reply.createTypedArray(InputSensorInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean registerSensorListener(IInputSensorEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void unregisterSensorListener(IInputSensorEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean enableSensor(
          int deviceId, int sensorType, int samplingPeriodUs, int maxBatchReportLatencyUs)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(sensorType);
          _data.writeInt(samplingPeriodUs);
          _data.writeInt(maxBatchReportLatencyUs);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void disableSensor(int deviceId, int sensorType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(sensorType);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public boolean flushSensor(int deviceId, int sensorType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(sensorType);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public List<Light> getLights(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
          List<Light> _result = _reply.createTypedArrayList(Light.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public LightState getLightState(int deviceId, int lightId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeInt(lightId);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
          LightState _result = (LightState) _reply.readTypedObject(LightState.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void setLightStates(int deviceId, int[] lightIds, LightState[] states, IBinder token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeIntArray(lightIds);
          _data.writeTypedArray(states, 0);
          _data.writeStrongBinder(token);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void openLightSession(int deviceId, String opPkg, IBinder token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeString(opPkg);
          _data.writeStrongBinder(token);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void closeLightSession(int deviceId, IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongBinder(token);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void cancelCurrentTouch() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerBatteryListener(int deviceId, IInputDeviceBatteryListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void unregisterBatteryListener(int deviceId, IInputDeviceBatteryListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public String getInputDeviceBluetoothAddress(int deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(deviceId);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void pilferPointers(IBinder inputChannelToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(inputChannelToken);
          this.mRemote.transact(103, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void registerKeyboardBacklightListener(IKeyboardBacklightListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(104, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public void unregisterKeyboardBacklightListener(IKeyboardBacklightListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(105, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.input.IInputManager
      public HostUsiVersion getHostUsiVersionFromDisplayConfig(int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(106, _data, _reply, 0);
          _reply.readException();
          HostUsiVersion _result = (HostUsiVersion) _reply.readTypedObject(HostUsiVersion.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void setCurrentKeyboardLayoutForInputDevice_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
    }

    protected void addKeyboardLayoutForInputDevice_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
    }

    protected void removeKeyboardLayoutForInputDevice_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
    }

    protected void setKeyboardLayoutForInputDevice_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
    }

    protected void remapModifierKey_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
    }

    protected void clearAllModifierKeyRemappings_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
    }

    protected void getModifierKeyRemapping_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
    }

    protected void getInputDeviceBluetoothAddress_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.BLUETOOTH, getCallingPid(), getCallingUid());
    }

    protected void pilferPointers_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MONITOR_INPUT, getCallingPid(), getCallingUid());
    }

    protected void registerKeyboardBacklightListener_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
    }

    protected void unregisterKeyboardBacklightListener_enforcePermission()
        throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 105;
    }
  }
}
