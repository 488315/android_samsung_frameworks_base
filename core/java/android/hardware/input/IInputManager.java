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
import android.view.KeyCharacterMap;
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

    void addPortAssociation(String str, int i) throws RemoteException;

    void addUniqueIdAssociationByDescriptor(String str, String str2) throws RemoteException;

    void addUniqueIdAssociationByPort(String str, String str2) throws RemoteException;

    void cancelCurrentTouch() throws RemoteException;

    void cancelVibrate(int i, IBinder iBinder) throws RemoteException;

    void changeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

    int checkInputFeature() throws RemoteException;

    void clearAllModifierKeyRemappings() throws RemoteException;

    void closeLightSession(int i, IBinder iBinder) throws RemoteException;

    void connectByBtDevice(BluetoothDevice bluetoothDevice) throws RemoteException;

    void controlSpenWithToken(IBinder iBinder, boolean z) throws RemoteException;

    void disableInputDevice(int i) throws RemoteException;

    void disableSensor(int i, int i2) throws RemoteException;

    void enableInputDevice(int i) throws RemoteException;

    boolean enableSensor(int i, int i2, int i3, int i4) throws RemoteException;

    boolean flushSensor(int i, int i2) throws RemoteException;

    void forceFadeIcon(int i) throws RemoteException;

    IInputDeviceBatteryState getBatteryState(int i) throws RemoteException;

    int getCurrentSwitchEventState(int i, boolean z) throws RemoteException;

    PointerIcon getDefaultPointerIcon() throws RemoteException;

    int getDisplayIdForPointerIcon() throws RemoteException;

    PointerIcon getForcedDefaultPointerIcon() throws RemoteException;

    String getGamepadProfile(int i) throws RemoteException;

    int[] getGamepadProfileIds() throws RemoteException;

    int getGlobalMetaState(int i) throws RemoteException;

    HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws RemoteException;

    int getInboundQueueLength() throws RemoteException;

    InputDevice getInputDevice(int i) throws RemoteException;

    String getInputDeviceBluetoothAddress(int i) throws RemoteException;

    int[] getInputDeviceIds() throws RemoteException;

    KeyCharacterMap getKeyCharacterMap(String str) throws RemoteException;

    int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException;

    KeyboardLayout getKeyboardLayout(String str) throws RemoteException;

    KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(
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

    long getLastLidEventTimeNanos() throws RemoteException;

    int getLidState() throws RemoteException;

    LightState getLightState(int i, int i2) throws RemoteException;

    List<Light> getLights(int i) throws RemoteException;

    Map getModifierKeyRemapping() throws RemoteException;

    int getMousePointerSpeed() throws RemoteException;

    int getPointerIconType() throws RemoteException;

    int getScanCodeState(int i, int i2, int i3) throws RemoteException;

    InputSensorInfo[] getSensorList(int i) throws RemoteException;

    String getSupportButtonNStick() throws RemoteException;

    int getToolTypeForDefaultPointerIcon() throws RemoteException;

    TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException;

    String getVelocityTrackerStrategy() throws RemoteException;

    int[] getVibratorIds(int i) throws RemoteException;

    boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException;

    boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException;

    boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) throws RemoteException;

    boolean isDefaultPointerIconChanged() throws RemoteException;

    int isInTabletMode() throws RemoteException;

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

    void registerStickyModifierStateListener(
            IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException;

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

    void removeAllDeviceToGamepadProfile() throws RemoteException;

    void removeAllGamepadProfiles() throws RemoteException;

    void removeDeviceToGamepadProfile(String str) throws RemoteException;

    void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

    void removeGamepadProfile(int i) throws RemoteException;

    void removePortAssociation(String str) throws RemoteException;

    void removeUniqueIdAssociationByDescriptor(String str) throws RemoteException;

    void removeUniqueIdAssociationByPort(String str) throws RemoteException;

    void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException;

    long semGetMotionIdleTimeMillis(boolean z) throws RemoteException;

    void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException;

    void setDisplayIdForPointerIcon(int i) throws RemoteException;

    boolean setGamepadProfileName(int i, String str) throws RemoteException;

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

    boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder)
            throws RemoteException;

    boolean setRemapGamepadButton(int i, int i2, int i3) throws RemoteException;

    boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3)
            throws RemoteException;

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

    void unregisterStickyModifierStateListener(
            IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException;

    boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener)
            throws RemoteException;

    void updateDeviceToGamepadProfile(String str, int i) throws RemoteException;

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
        public void enableInputDevice(int deviceId) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void disableInputDevice(int deviceId) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void controlSpenWithToken(IBinder token, boolean enable) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public boolean hasKeys(int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public int getKeyCodeForKeyLocation(int deviceId, int locationKeyCode)
                throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public KeyCharacterMap getKeyCharacterMap(String layoutDescriptor) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getMousePointerSpeed() throws RemoteException {
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
        public KeyboardLayout getKeyboardLayout(String keyboardLayoutDescriptor)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(
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
        public void registerSwitchEventChangedListener(ISwitchEventChangedListener listener)
                throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public int getCurrentSwitchEventState(int mask, boolean isSwitch) throws RemoteException {
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
        public int isInTabletMode() throws RemoteException {
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
        public boolean unregisterVibratorStateListener(
                int deviceId, IVibratorStateListener listener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public IInputDeviceBatteryState getBatteryState(int deviceId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean setPointerIcon(
                PointerIcon icon, int displayId, int deviceId, int pointerId, IBinder inputToken)
                throws RemoteException {
            return false;
        }

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
        public void registerPointerIconChangedListener(IPointerIconChangedListener listener)
                throws RemoteException {}

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
        public void setShowAllTouches(boolean enabled) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void updateDeviceToGamepadProfile(String btDevice, int id) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeDeviceToGamepadProfile(String btDevice) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeAllDeviceToGamepadProfile() throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeAllGamepadProfiles() throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeGamepadProfile(int id) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public boolean setGamepadProfileName(int id, String name) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean setRemapGamepadButton(int id, int fromButton, int toButton)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean setRemapGamepadStick(
                int id,
                int fromStick,
                int toStick,
                boolean inverseH,
                boolean inverseV,
                boolean inverseRot)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public String getSupportButtonNStick() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getGamepadProfile(int id) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getGamepadProfileIds() throws RemoteException {
            return null;
        }

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
        public InputChannel monitorInputForBinder(
                String inputChannelName, int displayId, int filter) throws RemoteException {
            return null;
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
        public int checkInputFeature() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void setStartedShutdown(boolean isStarted) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public int getScanCodeState(int deviceId, int sourceMask, int scanCode)
                throws RemoteException {
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
        public void addUniqueIdAssociationByDescriptor(
                String inputDeviceDescriptor, String displayUniqueId) throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeUniqueIdAssociationByDescriptor(String inputDeviceDescriptor)
                throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void addUniqueIdAssociationByPort(String inputPort, String displayUniqueId)
                throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void removeUniqueIdAssociationByPort(String inputPort) throws RemoteException {}

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
        public HostUsiVersion getHostUsiVersionFromDisplayConfig(int displayId)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void registerStickyModifierStateListener(IStickyModifierStateListener listener)
                throws RemoteException {}

        @Override // android.hardware.input.IInputManager
        public void unregisterStickyModifierStateListener(IStickyModifierStateListener listener)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInputManager {
        public static final String DESCRIPTOR = "android.hardware.input.IInputManager";
        static final int TRANSACTION_addDeviceWirelessKeyboardShare = 85;
        static final int TRANSACTION_addPortAssociation = 89;
        static final int TRANSACTION_addUniqueIdAssociationByDescriptor = 91;
        static final int TRANSACTION_addUniqueIdAssociationByPort = 93;
        static final int TRANSACTION_cancelCurrentTouch = 106;
        static final int TRANSACTION_cancelVibrate = 38;
        static final int TRANSACTION_changeDeviceWirelessKeyboardShare = 84;
        static final int TRANSACTION_checkInputFeature = 72;
        static final int TRANSACTION_clearAllModifierKeyRemappings = 23;
        static final int TRANSACTION_closeLightSession = 105;
        static final int TRANSACTION_connectByBtDevice = 88;
        static final int TRANSACTION_controlSpenWithToken = 6;
        static final int TRANSACTION_disableInputDevice = 5;
        static final int TRANSACTION_disableSensor = 99;
        static final int TRANSACTION_enableInputDevice = 4;
        static final int TRANSACTION_enableSensor = 98;
        static final int TRANSACTION_flushSensor = 100;
        static final int TRANSACTION_forceFadeIcon = 79;
        static final int TRANSACTION_getBatteryState = 43;
        static final int TRANSACTION_getCurrentSwitchEventState = 30;
        static final int TRANSACTION_getDefaultPointerIcon = 46;
        static final int TRANSACTION_getDisplayIdForPointerIcon = 52;
        static final int TRANSACTION_getForcedDefaultPointerIcon = 47;
        static final int TRANSACTION_getGamepadProfile = 64;
        static final int TRANSACTION_getGamepadProfileIds = 65;
        static final int TRANSACTION_getGlobalMetaState = 76;
        static final int TRANSACTION_getHostUsiVersionFromDisplayConfig = 113;
        static final int TRANSACTION_getInboundQueueLength = 71;
        static final int TRANSACTION_getInputDevice = 2;
        static final int TRANSACTION_getInputDeviceBluetoothAddress = 109;
        static final int TRANSACTION_getInputDeviceIds = 3;
        static final int TRANSACTION_getKeyCharacterMap = 9;
        static final int TRANSACTION_getKeyCodeForKeyLocation = 8;
        static final int TRANSACTION_getKeyboardLayout = 18;
        static final int TRANSACTION_getKeyboardLayoutForInputDevice = 19;
        static final int TRANSACTION_getKeyboardLayoutListForInputDevice = 21;
        static final int TRANSACTION_getKeyboardLayouts = 17;
        static final int TRANSACTION_getLastLidEventTimeNanos = 28;
        static final int TRANSACTION_getLidState = 26;
        static final int TRANSACTION_getLightState = 102;
        static final int TRANSACTION_getLights = 101;
        static final int TRANSACTION_getModifierKeyRemapping = 24;
        static final int TRANSACTION_getMousePointerSpeed = 10;
        static final int TRANSACTION_getPointerIconType = 53;
        static final int TRANSACTION_getScanCodeState = 74;
        static final int TRANSACTION_getSensorList = 95;
        static final int TRANSACTION_getSupportButtonNStick = 63;
        static final int TRANSACTION_getToolTypeForDefaultPointerIcon = 49;
        static final int TRANSACTION_getTouchCalibrationForInputDevice = 15;
        static final int TRANSACTION_getVelocityTrackerStrategy = 1;
        static final int TRANSACTION_getVibratorIds = 39;
        static final int TRANSACTION_hasKeys = 7;
        static final int TRANSACTION_injectInputEvent = 12;
        static final int TRANSACTION_injectInputEventToTarget = 13;
        static final int TRANSACTION_isDefaultPointerIconChanged = 48;
        static final int TRANSACTION_isInTabletMode = 33;
        static final int TRANSACTION_isMicMuted = 35;
        static final int TRANSACTION_isUidTouched = 78;
        static final int TRANSACTION_isVibrating = 40;
        static final int TRANSACTION_monitorGestureInput = 67;
        static final int TRANSACTION_monitorGestureInputFiltered = 68;
        static final int TRANSACTION_monitorInputForBinder = 69;
        static final int TRANSACTION_notifyQuickAccess = 80;
        static final int TRANSACTION_openLightSession = 104;
        static final int TRANSACTION_pilferPointers = 110;
        static final int TRANSACTION_registerBatteryListener = 107;
        static final int TRANSACTION_registerInputDevicesChangedListener = 25;
        static final int TRANSACTION_registerKeyboardBacklightListener = 111;
        static final int TRANSACTION_registerLidStateChangedListener = 27;
        static final int TRANSACTION_registerMultiFingerGestureListener = 32;
        static final int TRANSACTION_registerPointerIconChangedListener = 50;
        static final int TRANSACTION_registerSensorListener = 96;
        static final int TRANSACTION_registerStickyModifierStateListener = 114;
        static final int TRANSACTION_registerSwitchEventChangedListener = 29;
        static final int TRANSACTION_registerTabletModeChangedListener = 34;
        static final int TRANSACTION_registerVibratorStateListener = 41;
        static final int TRANSACTION_registerWirelessKeyboardShareChangedListener = 81;
        static final int TRANSACTION_remapModifierKey = 22;
        static final int TRANSACTION_removeAllDeviceToGamepadProfile = 57;
        static final int TRANSACTION_removeAllGamepadProfiles = 58;
        static final int TRANSACTION_removeDeviceToGamepadProfile = 56;
        static final int TRANSACTION_removeDeviceWirelessKeyboardShare = 83;
        static final int TRANSACTION_removeGamepadProfile = 59;
        static final int TRANSACTION_removePortAssociation = 90;
        static final int TRANSACTION_removeUniqueIdAssociationByDescriptor = 92;
        static final int TRANSACTION_removeUniqueIdAssociationByPort = 94;
        static final int TRANSACTION_requestPointerCapture = 66;
        static final int TRANSACTION_semGetMotionIdleTimeMillis = 77;
        static final int TRANSACTION_setDefaultPointerIcon = 45;
        static final int TRANSACTION_setDisplayIdForPointerIcon = 51;
        static final int TRANSACTION_setGamepadProfileName = 60;
        static final int TRANSACTION_setHostRoleWirelessKeyboardShare = 87;
        static final int TRANSACTION_setKeyboardLayoutForInputDevice = 20;
        static final int TRANSACTION_setLightStates = 103;
        static final int TRANSACTION_setPointerIcon = 44;
        static final int TRANSACTION_setRemapGamepadButton = 61;
        static final int TRANSACTION_setRemapGamepadStick = 62;
        static final int TRANSACTION_setShowAllTouches = 54;
        static final int TRANSACTION_setStartedShutdown = 73;
        static final int TRANSACTION_setTouchCalibrationForInputDevice = 16;
        static final int TRANSACTION_setTspEnabled = 70;
        static final int TRANSACTION_setWakeKeyDynamically = 75;
        static final int TRANSACTION_supportPogoDevice = 31;
        static final int TRANSACTION_switchDeviceWirelessKeyboardShare = 86;
        static final int TRANSACTION_tryPointerSpeed = 11;
        static final int TRANSACTION_unregisterBatteryListener = 108;
        static final int TRANSACTION_unregisterKeyboardBacklightListener = 112;
        static final int TRANSACTION_unregisterSensorListener = 97;
        static final int TRANSACTION_unregisterStickyModifierStateListener = 115;
        static final int TRANSACTION_unregisterVibratorStateListener = 42;
        static final int TRANSACTION_updateDeviceToGamepadProfile = 55;
        static final int TRANSACTION_updateWirelessKeyboardShareStatus = 82;
        static final int TRANSACTION_verifyInputEvent = 14;
        static final int TRANSACTION_vibrate = 36;
        static final int TRANSACTION_vibrateCombined = 37;
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
                    return "enableInputDevice";
                case 5:
                    return "disableInputDevice";
                case 6:
                    return "controlSpenWithToken";
                case 7:
                    return "hasKeys";
                case 8:
                    return "getKeyCodeForKeyLocation";
                case 9:
                    return "getKeyCharacterMap";
                case 10:
                    return "getMousePointerSpeed";
                case 11:
                    return "tryPointerSpeed";
                case 12:
                    return "injectInputEvent";
                case 13:
                    return "injectInputEventToTarget";
                case 14:
                    return "verifyInputEvent";
                case 15:
                    return "getTouchCalibrationForInputDevice";
                case 16:
                    return "setTouchCalibrationForInputDevice";
                case 17:
                    return "getKeyboardLayouts";
                case 18:
                    return "getKeyboardLayout";
                case 19:
                    return "getKeyboardLayoutForInputDevice";
                case 20:
                    return "setKeyboardLayoutForInputDevice";
                case 21:
                    return "getKeyboardLayoutListForInputDevice";
                case 22:
                    return "remapModifierKey";
                case 23:
                    return "clearAllModifierKeyRemappings";
                case 24:
                    return "getModifierKeyRemapping";
                case 25:
                    return "registerInputDevicesChangedListener";
                case 26:
                    return "getLidState";
                case 27:
                    return "registerLidStateChangedListener";
                case 28:
                    return "getLastLidEventTimeNanos";
                case 29:
                    return "registerSwitchEventChangedListener";
                case 30:
                    return "getCurrentSwitchEventState";
                case 31:
                    return "supportPogoDevice";
                case 32:
                    return "registerMultiFingerGestureListener";
                case 33:
                    return "isInTabletMode";
                case 34:
                    return "registerTabletModeChangedListener";
                case 35:
                    return "isMicMuted";
                case 36:
                    return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
                case 37:
                    return "vibrateCombined";
                case 38:
                    return "cancelVibrate";
                case 39:
                    return "getVibratorIds";
                case 40:
                    return "isVibrating";
                case 41:
                    return "registerVibratorStateListener";
                case 42:
                    return "unregisterVibratorStateListener";
                case 43:
                    return "getBatteryState";
                case 44:
                    return "setPointerIcon";
                case 45:
                    return "setDefaultPointerIcon";
                case 46:
                    return "getDefaultPointerIcon";
                case 47:
                    return "getForcedDefaultPointerIcon";
                case 48:
                    return "isDefaultPointerIconChanged";
                case 49:
                    return "getToolTypeForDefaultPointerIcon";
                case 50:
                    return "registerPointerIconChangedListener";
                case 51:
                    return "setDisplayIdForPointerIcon";
                case 52:
                    return "getDisplayIdForPointerIcon";
                case 53:
                    return "getPointerIconType";
                case 54:
                    return "setShowAllTouches";
                case 55:
                    return "updateDeviceToGamepadProfile";
                case 56:
                    return "removeDeviceToGamepadProfile";
                case 57:
                    return "removeAllDeviceToGamepadProfile";
                case 58:
                    return "removeAllGamepadProfiles";
                case 59:
                    return "removeGamepadProfile";
                case 60:
                    return "setGamepadProfileName";
                case 61:
                    return "setRemapGamepadButton";
                case 62:
                    return "setRemapGamepadStick";
                case 63:
                    return "getSupportButtonNStick";
                case 64:
                    return "getGamepadProfile";
                case 65:
                    return "getGamepadProfileIds";
                case 66:
                    return "requestPointerCapture";
                case 67:
                    return "monitorGestureInput";
                case 68:
                    return "monitorGestureInputFiltered";
                case 69:
                    return "monitorInputForBinder";
                case 70:
                    return "setTspEnabled";
                case 71:
                    return "getInboundQueueLength";
                case 72:
                    return "checkInputFeature";
                case 73:
                    return "setStartedShutdown";
                case 74:
                    return "getScanCodeState";
                case 75:
                    return "setWakeKeyDynamically";
                case 76:
                    return "getGlobalMetaState";
                case 77:
                    return "semGetMotionIdleTimeMillis";
                case 78:
                    return "isUidTouched";
                case 79:
                    return "forceFadeIcon";
                case 80:
                    return "notifyQuickAccess";
                case 81:
                    return "registerWirelessKeyboardShareChangedListener";
                case 82:
                    return "updateWirelessKeyboardShareStatus";
                case 83:
                    return "removeDeviceWirelessKeyboardShare";
                case 84:
                    return "changeDeviceWirelessKeyboardShare";
                case 85:
                    return "addDeviceWirelessKeyboardShare";
                case 86:
                    return "switchDeviceWirelessKeyboardShare";
                case 87:
                    return "setHostRoleWirelessKeyboardShare";
                case 88:
                    return "connectByBtDevice";
                case 89:
                    return "addPortAssociation";
                case 90:
                    return "removePortAssociation";
                case 91:
                    return "addUniqueIdAssociationByDescriptor";
                case 92:
                    return "removeUniqueIdAssociationByDescriptor";
                case 93:
                    return "addUniqueIdAssociationByPort";
                case 94:
                    return "removeUniqueIdAssociationByPort";
                case 95:
                    return "getSensorList";
                case 96:
                    return "registerSensorListener";
                case 97:
                    return "unregisterSensorListener";
                case 98:
                    return "enableSensor";
                case 99:
                    return "disableSensor";
                case 100:
                    return "flushSensor";
                case 101:
                    return "getLights";
                case 102:
                    return "getLightState";
                case 103:
                    return "setLightStates";
                case 104:
                    return "openLightSession";
                case 105:
                    return "closeLightSession";
                case 106:
                    return "cancelCurrentTouch";
                case 107:
                    return "registerBatteryListener";
                case 108:
                    return "unregisterBatteryListener";
                case 109:
                    return "getInputDeviceBluetoothAddress";
                case 110:
                    return "pilferPointers";
                case 111:
                    return "registerKeyboardBacklightListener";
                case 112:
                    return "unregisterKeyboardBacklightListener";
                case 113:
                    return "getHostUsiVersionFromDisplayConfig";
                case 114:
                    return "registerStickyModifierStateListener";
                case 115:
                    return "unregisterStickyModifierStateListener";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
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
                    enableInputDevice(_arg02);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    disableInputDevice(_arg03);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg04 = data.readStrongBinder();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    controlSpenWithToken(_arg04, _arg1);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg05 = data.readInt();
                    int _arg12 = data.readInt();
                    int[] _arg2 = data.createIntArray();
                    int _arg3_length = data.readInt();
                    if (_arg3_length < 0) {
                        _arg3 = null;
                    } else {
                        _arg3 = new boolean[_arg3_length];
                    }
                    data.enforceNoDataAvail();
                    boolean _result4 = hasKeys(_arg05, _arg12, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    reply.writeBooleanArray(_arg3);
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getKeyCodeForKeyLocation(_arg06, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 9:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    KeyCharacterMap _result6 = getKeyCharacterMap(_arg07);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 10:
                    int _result7 = getMousePointerSpeed();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 11:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    tryPointerSpeed(_arg08);
                    reply.writeNoException();
                    return true;
                case 12:
                    InputEvent _arg09 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = injectInputEvent(_arg09, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 13:
                    InputEvent _arg010 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
                    int _arg15 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = injectInputEventToTarget(_arg010, _arg15, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 14:
                    InputEvent _arg011 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
                    data.enforceNoDataAvail();
                    VerifiedInputEvent _result10 = verifyInputEvent(_arg011);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 15:
                    String _arg012 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    TouchCalibration _result11 = getTouchCalibrationForInputDevice(_arg012, _arg16);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 16:
                    String _arg013 = data.readString();
                    int _arg17 = data.readInt();
                    TouchCalibration _arg23 =
                            (TouchCalibration) data.readTypedObject(TouchCalibration.CREATOR);
                    data.enforceNoDataAvail();
                    setTouchCalibrationForInputDevice(_arg013, _arg17, _arg23);
                    reply.writeNoException();
                    return true;
                case 17:
                    KeyboardLayout[] _result12 = getKeyboardLayouts();
                    reply.writeNoException();
                    reply.writeTypedArray(_result12, 1);
                    return true;
                case 18:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    KeyboardLayout _result13 = getKeyboardLayout(_arg014);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 19:
                    InputDeviceIdentifier _arg015 =
                            (InputDeviceIdentifier)
                                    data.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int _arg18 = data.readInt();
                    InputMethodInfo _arg24 =
                            (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype _arg32 =
                            (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
                    data.enforceNoDataAvail();
                    KeyboardLayoutSelectionResult _result14 =
                            getKeyboardLayoutForInputDevice(_arg015, _arg18, _arg24, _arg32);
                    reply.writeNoException();
                    reply.writeTypedObject(_result14, 1);
                    return true;
                case 20:
                    InputDeviceIdentifier _arg016 =
                            (InputDeviceIdentifier)
                                    data.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int _arg19 = data.readInt();
                    InputMethodInfo _arg25 =
                            (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype _arg33 =
                            (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    setKeyboardLayoutForInputDevice(_arg016, _arg19, _arg25, _arg33, _arg4);
                    reply.writeNoException();
                    return true;
                case 21:
                    InputDeviceIdentifier _arg017 =
                            (InputDeviceIdentifier)
                                    data.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int _arg110 = data.readInt();
                    InputMethodInfo _arg26 =
                            (InputMethodInfo) data.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype _arg34 =
                            (InputMethodSubtype) data.readTypedObject(InputMethodSubtype.CREATOR);
                    data.enforceNoDataAvail();
                    KeyboardLayout[] _result15 =
                            getKeyboardLayoutListForInputDevice(_arg017, _arg110, _arg26, _arg34);
                    reply.writeNoException();
                    reply.writeTypedArray(_result15, 1);
                    return true;
                case 22:
                    int _arg018 = data.readInt();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    remapModifierKey(_arg018, _arg111);
                    reply.writeNoException();
                    return true;
                case 23:
                    clearAllModifierKeyRemappings();
                    reply.writeNoException();
                    return true;
                case 24:
                    Map _result16 = getModifierKeyRemapping();
                    reply.writeNoException();
                    reply.writeMap(_result16);
                    return true;
                case 25:
                    IInputDevicesChangedListener _arg019 =
                            IInputDevicesChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerInputDevicesChangedListener(_arg019);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _result17 = getLidState();
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 27:
                    ISemLidStateChangedListener _arg020 =
                            ISemLidStateChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerLidStateChangedListener(_arg020);
                    reply.writeNoException();
                    return true;
                case 28:
                    long _result18 = getLastLidEventTimeNanos();
                    reply.writeNoException();
                    reply.writeLong(_result18);
                    return true;
                case 29:
                    ISwitchEventChangedListener _arg021 =
                            ISwitchEventChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerSwitchEventChangedListener(_arg021);
                    reply.writeNoException();
                    return true;
                case 30:
                    int _arg022 = data.readInt();
                    boolean _arg112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result19 = getCurrentSwitchEventState(_arg022, _arg112);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 31:
                    boolean _result20 = supportPogoDevice();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 32:
                    IMultiFingerGestureListener _arg023 =
                            IMultiFingerGestureListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerMultiFingerGestureListener(_arg023);
                    reply.writeNoException();
                    return true;
                case 33:
                    int _result21 = isInTabletMode();
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 34:
                    ITabletModeChangedListener _arg024 =
                            ITabletModeChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerTabletModeChangedListener(_arg024);
                    reply.writeNoException();
                    return true;
                case 35:
                    int _result22 = isMicMuted();
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 36:
                    int _arg025 = data.readInt();
                    VibrationEffect _arg113 =
                            (VibrationEffect) data.readTypedObject(VibrationEffect.CREATOR);
                    IBinder _arg27 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    vibrate(_arg025, _arg113, _arg27);
                    reply.writeNoException();
                    return true;
                case 37:
                    int _arg026 = data.readInt();
                    CombinedVibration _arg114 =
                            (CombinedVibration) data.readTypedObject(CombinedVibration.CREATOR);
                    IBinder _arg28 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    vibrateCombined(_arg026, _arg114, _arg28);
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg027 = data.readInt();
                    IBinder _arg115 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    cancelVibrate(_arg027, _arg115);
                    reply.writeNoException();
                    return true;
                case 39:
                    int _arg028 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result23 = getVibratorIds(_arg028);
                    reply.writeNoException();
                    reply.writeIntArray(_result23);
                    return true;
                case 40:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result24 = isVibrating(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 41:
                    int _arg030 = data.readInt();
                    IVibratorStateListener _arg116 =
                            IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result25 = registerVibratorStateListener(_arg030, _arg116);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 42:
                    int _arg031 = data.readInt();
                    IVibratorStateListener _arg117 =
                            IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result26 = unregisterVibratorStateListener(_arg031, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 43:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    IInputDeviceBatteryState _result27 = getBatteryState(_arg032);
                    reply.writeNoException();
                    reply.writeTypedObject(_result27, 1);
                    return true;
                case 44:
                    PointerIcon _arg033 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
                    int _arg118 = data.readInt();
                    int _arg29 = data.readInt();
                    int _arg35 = data.readInt();
                    IBinder _arg42 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result28 = setPointerIcon(_arg033, _arg118, _arg29, _arg35, _arg42);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 45:
                    int _arg034 = data.readInt();
                    PointerIcon _arg119 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
                    boolean _arg210 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDefaultPointerIcon(_arg034, _arg119, _arg210);
                    reply.writeNoException();
                    return true;
                case 46:
                    PointerIcon _result29 = getDefaultPointerIcon();
                    reply.writeNoException();
                    reply.writeTypedObject(_result29, 1);
                    return true;
                case 47:
                    PointerIcon _result30 = getForcedDefaultPointerIcon();
                    reply.writeNoException();
                    reply.writeTypedObject(_result30, 1);
                    return true;
                case 48:
                    boolean _result31 = isDefaultPointerIconChanged();
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 49:
                    int _result32 = getToolTypeForDefaultPointerIcon();
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 50:
                    IPointerIconChangedListener _arg035 =
                            IPointerIconChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerPointerIconChangedListener(_arg035);
                    reply.writeNoException();
                    return true;
                case 51:
                    int _arg036 = data.readInt();
                    data.enforceNoDataAvail();
                    setDisplayIdForPointerIcon(_arg036);
                    reply.writeNoException();
                    return true;
                case 52:
                    int _result33 = getDisplayIdForPointerIcon();
                    reply.writeNoException();
                    reply.writeInt(_result33);
                    return true;
                case 53:
                    int _result34 = getPointerIconType();
                    reply.writeNoException();
                    reply.writeInt(_result34);
                    return true;
                case 54:
                    boolean _arg037 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShowAllTouches(_arg037);
                    reply.writeNoException();
                    return true;
                case 55:
                    String _arg038 = data.readString();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    updateDeviceToGamepadProfile(_arg038, _arg120);
                    reply.writeNoException();
                    return true;
                case 56:
                    String _arg039 = data.readString();
                    data.enforceNoDataAvail();
                    removeDeviceToGamepadProfile(_arg039);
                    reply.writeNoException();
                    return true;
                case 57:
                    removeAllDeviceToGamepadProfile();
                    reply.writeNoException();
                    return true;
                case 58:
                    removeAllGamepadProfiles();
                    reply.writeNoException();
                    return true;
                case 59:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    removeGamepadProfile(_arg040);
                    reply.writeNoException();
                    return true;
                case 60:
                    int _arg041 = data.readInt();
                    String _arg121 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result35 = setGamepadProfileName(_arg041, _arg121);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 61:
                    int _arg042 = data.readInt();
                    int _arg122 = data.readInt();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result36 = setRemapGamepadButton(_arg042, _arg122, _arg211);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 62:
                    int _arg043 = data.readInt();
                    int _arg123 = data.readInt();
                    int _arg212 = data.readInt();
                    boolean _arg36 = data.readBoolean();
                    boolean _arg43 = data.readBoolean();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result37 =
                            setRemapGamepadStick(_arg043, _arg123, _arg212, _arg36, _arg43, _arg5);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 63:
                    String _result38 = getSupportButtonNStick();
                    reply.writeNoException();
                    reply.writeString(_result38);
                    return true;
                case 64:
                    int _arg044 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result39 = getGamepadProfile(_arg044);
                    reply.writeNoException();
                    reply.writeString(_result39);
                    return true;
                case 65:
                    int[] _result40 = getGamepadProfileIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result40);
                    return true;
                case 66:
                    IBinder _arg045 = data.readStrongBinder();
                    boolean _arg124 = data.readBoolean();
                    data.enforceNoDataAvail();
                    requestPointerCapture(_arg045, _arg124);
                    return true;
                case 67:
                    IBinder _arg046 = data.readStrongBinder();
                    String _arg125 = data.readString();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMonitor _result41 = monitorGestureInput(_arg046, _arg125, _arg213);
                    reply.writeNoException();
                    reply.writeTypedObject(_result41, 1);
                    return true;
                case 68:
                    IBinder _arg047 = data.readStrongBinder();
                    String _arg126 = data.readString();
                    int _arg214 = data.readInt();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMonitor _result42 =
                            monitorGestureInputFiltered(_arg047, _arg126, _arg214, _arg37);
                    reply.writeNoException();
                    reply.writeTypedObject(_result42, 1);
                    return true;
                case 69:
                    String _arg048 = data.readString();
                    int _arg127 = data.readInt();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    InputChannel _result43 = monitorInputForBinder(_arg048, _arg127, _arg215);
                    reply.writeNoException();
                    reply.writeTypedObject(_result43, 1);
                    return true;
                case 70:
                    int _arg049 = data.readInt();
                    boolean _arg128 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result44 = setTspEnabled(_arg049, _arg128);
                    reply.writeNoException();
                    reply.writeBoolean(_result44);
                    return true;
                case 71:
                    int _result45 = getInboundQueueLength();
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    return true;
                case 72:
                    int _result46 = checkInputFeature();
                    reply.writeNoException();
                    reply.writeInt(_result46);
                    return true;
                case 73:
                    boolean _arg050 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setStartedShutdown(_arg050);
                    reply.writeNoException();
                    return true;
                case 74:
                    int _arg051 = data.readInt();
                    int _arg129 = data.readInt();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result47 = getScanCodeState(_arg051, _arg129, _arg216);
                    reply.writeNoException();
                    reply.writeInt(_result47);
                    return true;
                case 75:
                    String _arg052 = data.readString();
                    boolean _arg130 = data.readBoolean();
                    String _arg217 = data.readString();
                    data.enforceNoDataAvail();
                    setWakeKeyDynamically(_arg052, _arg130, _arg217);
                    reply.writeNoException();
                    return true;
                case 76:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result48 = getGlobalMetaState(_arg053);
                    reply.writeNoException();
                    reply.writeInt(_result48);
                    return true;
                case 77:
                    boolean _arg054 = data.readBoolean();
                    data.enforceNoDataAvail();
                    long _result49 = semGetMotionIdleTimeMillis(_arg054);
                    reply.writeNoException();
                    reply.writeLong(_result49);
                    return true;
                case 78:
                    int _arg055 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result50 = isUidTouched(_arg055);
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 79:
                    int _arg056 = data.readInt();
                    data.enforceNoDataAvail();
                    forceFadeIcon(_arg056);
                    reply.writeNoException();
                    return true;
                case 80:
                    int _arg057 = data.readInt();
                    float _arg131 = data.readFloat();
                    float _arg218 = data.readFloat();
                    data.enforceNoDataAvail();
                    notifyQuickAccess(_arg057, _arg131, _arg218);
                    reply.writeNoException();
                    return true;
                case 81:
                    IWirelessKeyboardShareChangedListener _arg058 =
                            IWirelessKeyboardShareChangedListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerWirelessKeyboardShareChangedListener(_arg058);
                    reply.writeNoException();
                    return true;
                case 82:
                    updateWirelessKeyboardShareStatus();
                    reply.writeNoException();
                    return true;
                case 83:
                    String _arg059 = data.readString();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    removeDeviceWirelessKeyboardShare(_arg059, _arg132);
                    reply.writeNoException();
                    return true;
                case 84:
                    String _arg060 = data.readString();
                    int _arg133 = data.readInt();
                    data.enforceNoDataAvail();
                    changeDeviceWirelessKeyboardShare(_arg060, _arg133);
                    reply.writeNoException();
                    return true;
                case 85:
                    int _arg061 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result51 = addDeviceWirelessKeyboardShare(_arg061);
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 86:
                    String _arg062 = data.readString();
                    int _arg134 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result52 = switchDeviceWirelessKeyboardShare(_arg062, _arg134);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 87:
                    setHostRoleWirelessKeyboardShare();
                    reply.writeNoException();
                    return true;
                case 88:
                    BluetoothDevice _arg063 =
                            (BluetoothDevice) data.readTypedObject(BluetoothDevice.CREATOR);
                    data.enforceNoDataAvail();
                    connectByBtDevice(_arg063);
                    reply.writeNoException();
                    return true;
                case 89:
                    String _arg064 = data.readString();
                    int _arg135 = data.readInt();
                    data.enforceNoDataAvail();
                    addPortAssociation(_arg064, _arg135);
                    reply.writeNoException();
                    return true;
                case 90:
                    String _arg065 = data.readString();
                    data.enforceNoDataAvail();
                    removePortAssociation(_arg065);
                    reply.writeNoException();
                    return true;
                case 91:
                    String _arg066 = data.readString();
                    String _arg136 = data.readString();
                    data.enforceNoDataAvail();
                    addUniqueIdAssociationByDescriptor(_arg066, _arg136);
                    reply.writeNoException();
                    return true;
                case 92:
                    String _arg067 = data.readString();
                    data.enforceNoDataAvail();
                    removeUniqueIdAssociationByDescriptor(_arg067);
                    reply.writeNoException();
                    return true;
                case 93:
                    String _arg068 = data.readString();
                    String _arg137 = data.readString();
                    data.enforceNoDataAvail();
                    addUniqueIdAssociationByPort(_arg068, _arg137);
                    reply.writeNoException();
                    return true;
                case 94:
                    String _arg069 = data.readString();
                    data.enforceNoDataAvail();
                    removeUniqueIdAssociationByPort(_arg069);
                    reply.writeNoException();
                    return true;
                case 95:
                    int _arg070 = data.readInt();
                    data.enforceNoDataAvail();
                    InputSensorInfo[] _result53 = getSensorList(_arg070);
                    reply.writeNoException();
                    reply.writeTypedArray(_result53, 1);
                    return true;
                case 96:
                    IInputSensorEventListener _arg071 =
                            IInputSensorEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result54 = registerSensorListener(_arg071);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 97:
                    IInputSensorEventListener _arg072 =
                            IInputSensorEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterSensorListener(_arg072);
                    reply.writeNoException();
                    return true;
                case 98:
                    int _arg073 = data.readInt();
                    int _arg138 = data.readInt();
                    int _arg219 = data.readInt();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result55 = enableSensor(_arg073, _arg138, _arg219, _arg38);
                    reply.writeNoException();
                    reply.writeBoolean(_result55);
                    return true;
                case 99:
                    int _arg074 = data.readInt();
                    int _arg139 = data.readInt();
                    data.enforceNoDataAvail();
                    disableSensor(_arg074, _arg139);
                    reply.writeNoException();
                    return true;
                case 100:
                    int _arg075 = data.readInt();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result56 = flushSensor(_arg075, _arg140);
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 101:
                    int _arg076 = data.readInt();
                    data.enforceNoDataAvail();
                    List<Light> _result57 = getLights(_arg076);
                    reply.writeNoException();
                    reply.writeTypedList(_result57, 1);
                    return true;
                case 102:
                    int _arg077 = data.readInt();
                    int _arg141 = data.readInt();
                    data.enforceNoDataAvail();
                    LightState _result58 = getLightState(_arg077, _arg141);
                    reply.writeNoException();
                    reply.writeTypedObject(_result58, 1);
                    return true;
                case 103:
                    int _arg078 = data.readInt();
                    int[] _arg142 = data.createIntArray();
                    LightState[] _arg220 = (LightState[]) data.createTypedArray(LightState.CREATOR);
                    IBinder _arg39 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setLightStates(_arg078, _arg142, _arg220, _arg39);
                    reply.writeNoException();
                    return true;
                case 104:
                    int _arg079 = data.readInt();
                    String _arg143 = data.readString();
                    IBinder _arg221 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    openLightSession(_arg079, _arg143, _arg221);
                    reply.writeNoException();
                    return true;
                case 105:
                    int _arg080 = data.readInt();
                    IBinder _arg144 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    closeLightSession(_arg080, _arg144);
                    reply.writeNoException();
                    return true;
                case 106:
                    cancelCurrentTouch();
                    reply.writeNoException();
                    return true;
                case 107:
                    int _arg081 = data.readInt();
                    IInputDeviceBatteryListener _arg145 =
                            IInputDeviceBatteryListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerBatteryListener(_arg081, _arg145);
                    reply.writeNoException();
                    return true;
                case 108:
                    int _arg082 = data.readInt();
                    IInputDeviceBatteryListener _arg146 =
                            IInputDeviceBatteryListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterBatteryListener(_arg082, _arg146);
                    reply.writeNoException();
                    return true;
                case 109:
                    int _arg083 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result59 = getInputDeviceBluetoothAddress(_arg083);
                    reply.writeNoException();
                    reply.writeString(_result59);
                    return true;
                case 110:
                    IBinder _arg084 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    pilferPointers(_arg084);
                    reply.writeNoException();
                    return true;
                case 111:
                    IKeyboardBacklightListener _arg085 =
                            IKeyboardBacklightListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerKeyboardBacklightListener(_arg085);
                    reply.writeNoException();
                    return true;
                case 112:
                    IKeyboardBacklightListener _arg086 =
                            IKeyboardBacklightListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterKeyboardBacklightListener(_arg086);
                    reply.writeNoException();
                    return true;
                case 113:
                    int _arg087 = data.readInt();
                    data.enforceNoDataAvail();
                    HostUsiVersion _result60 = getHostUsiVersionFromDisplayConfig(_arg087);
                    reply.writeNoException();
                    reply.writeTypedObject(_result60, 1);
                    return true;
                case 114:
                    IStickyModifierStateListener _arg088 =
                            IStickyModifierStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStickyModifierStateListener(_arg088);
                    reply.writeNoException();
                    return true;
                case 115:
                    IStickyModifierStateListener _arg089 =
                            IStickyModifierStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterStickyModifierStateListener(_arg089);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
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
            public void enableInputDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(4, _data, _reply, 0);
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
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void controlSpenWithToken(IBinder token, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean hasKeys(
                    int deviceId, int sourceMask, int[] keyCodes, boolean[] keyExists)
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
            public KeyCharacterMap getKeyCharacterMap(String layoutDescriptor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(layoutDescriptor);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    KeyCharacterMap _result =
                            (KeyCharacterMap) _reply.readTypedObject(KeyCharacterMap.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getMousePointerSpeed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
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
                    this.mRemote.transact(11, _data, _reply, 0);
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
                    this.mRemote.transact(12, _data, _reply, 0);
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
                    this.mRemote.transact(13, _data, _reply, 0);
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
                    this.mRemote.transact(14, _data, _reply, 0);
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
                    this.mRemote.transact(15, _data, _reply, 0);
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
                    this.mRemote.transact(16, _data, _reply, 0);
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
                    this.mRemote.transact(17, _data, _reply, 0);
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
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    KeyboardLayout _result =
                            (KeyboardLayout) _reply.readTypedObject(KeyboardLayout.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(
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
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    KeyboardLayoutSelectionResult _result =
                            (KeyboardLayoutSelectionResult)
                                    _reply.readTypedObject(KeyboardLayoutSelectionResult.CREATOR);
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
                    this.mRemote.transact(20, _data, _reply, 0);
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
                    this.mRemote.transact(21, _data, _reply, 0);
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
                    this.mRemote.transact(22, _data, _reply, 0);
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
                    this.mRemote.transact(23, _data, _reply, 0);
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
                    this.mRemote.transact(24, _data, _reply, 0);
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
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(26, _data, _reply, 0);
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
                    this.mRemote.transact(27, _data, _reply, 0);
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
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
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
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getCurrentSwitchEventState(int mask, boolean isSwitch)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mask);
                    _data.writeBoolean(isSwitch);
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
            public boolean supportPogoDevice() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
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
                    this.mRemote.transact(32, _data, _reply, 0);
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
                    this.mRemote.transact(33, _data, _reply, 0);
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
                    this.mRemote.transact(34, _data, _reply, 0);
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
                    this.mRemote.transact(35, _data, _reply, 0);
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
                    this.mRemote.transact(36, _data, _reply, 0);
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
                    this.mRemote.transact(37, _data, _reply, 0);
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
                    this.mRemote.transact(38, _data, _reply, 0);
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
                    this.mRemote.transact(39, _data, _reply, 0);
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
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerVibratorStateListener(
                    int deviceId, IVibratorStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterVibratorStateListener(
                    int deviceId, IVibratorStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(42, _data, _reply, 0);
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
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    IInputDeviceBatteryState _result =
                            (IInputDeviceBatteryState)
                                    _reply.readTypedObject(IInputDeviceBatteryState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setPointerIcon(
                    PointerIcon icon,
                    int displayId,
                    int deviceId,
                    int pointerId,
                    IBinder inputToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(icon, 0);
                    _data.writeInt(displayId);
                    _data.writeInt(deviceId);
                    _data.writeInt(pointerId);
                    _data.writeStrongBinder(inputToken);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
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
                    this.mRemote.transact(45, _data, _reply, 0);
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
                    this.mRemote.transact(46, _data, _reply, 0);
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
                    this.mRemote.transact(47, _data, _reply, 0);
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
                    this.mRemote.transact(48, _data, _reply, 0);
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
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
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
                    this.mRemote.transact(50, _data, _reply, 0);
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
                    this.mRemote.transact(51, _data, _reply, 0);
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
                    this.mRemote.transact(52, _data, _reply, 0);
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
                    this.mRemote.transact(53, _data, _reply, 0);
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
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void updateDeviceToGamepadProfile(String btDevice, int id)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(btDevice);
                    _data.writeInt(id);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeDeviceToGamepadProfile(String btDevice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(btDevice);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllDeviceToGamepadProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllGamepadProfiles() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeGamepadProfile(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setGamepadProfileName(int id, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeString(name);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadButton(int id, int fromButton, int toButton)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(fromButton);
                    _data.writeInt(toButton);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadStick(
                    int id,
                    int fromStick,
                    int toStick,
                    boolean inverseH,
                    boolean inverseV,
                    boolean inverseRot)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(fromStick);
                    _data.writeInt(toStick);
                    _data.writeBoolean(inverseH);
                    _data.writeBoolean(inverseV);
                    _data.writeBoolean(inverseRot);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getSupportButtonNStick() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getGamepadProfile(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getGamepadProfileIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
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
                    this.mRemote.transact(66, _data, null, 1);
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
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    InputMonitor _result =
                            (InputMonitor) _reply.readTypedObject(InputMonitor.CREATOR);
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
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    InputMonitor _result =
                            (InputMonitor) _reply.readTypedObject(InputMonitor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputChannel monitorInputForBinder(
                    String inputChannelName, int displayId, int filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputChannelName);
                    _data.writeInt(displayId);
                    _data.writeInt(filter);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    InputChannel _result =
                            (InputChannel) _reply.readTypedObject(InputChannel.CREATOR);
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
                    this.mRemote.transact(70, _data, _reply, 0);
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
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
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
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
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
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(74, _data, _reply, 0);
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
                    this.mRemote.transact(75, _data, _reply, 0);
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
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public long semGetMotionIdleTimeMillis(boolean useOnlyActionDown)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(useOnlyActionDown);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
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
                    this.mRemote.transact(78, _data, _reply, 0);
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
                    this.mRemote.transact(79, _data, _reply, 0);
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
                    this.mRemote.transact(80, _data, _reply, 0);
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
                    this.mRemote.transact(81, _data, _reply, 0);
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
                    this.mRemote.transact(82, _data, _reply, 0);
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
                    this.mRemote.transact(83, _data, _reply, 0);
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
                    this.mRemote.transact(84, _data, _reply, 0);
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
                    this.mRemote.transact(85, _data, _reply, 0);
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
                    this.mRemote.transact(86, _data, _reply, 0);
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
                    this.mRemote.transact(87, _data, _reply, 0);
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
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addPortAssociation(String inputPort, int displayPort)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputPort);
                    _data.writeInt(displayPort);
                    this.mRemote.transact(89, _data, _reply, 0);
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
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByDescriptor(
                    String inputDeviceDescriptor, String displayUniqueId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputDeviceDescriptor);
                    _data.writeString(displayUniqueId);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByDescriptor(String inputDeviceDescriptor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputDeviceDescriptor);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByPort(String inputPort, String displayUniqueId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputPort);
                    _data.writeString(displayUniqueId);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByPort(String inputPort) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputPort);
                    this.mRemote.transact(94, _data, _reply, 0);
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
                    this.mRemote.transact(95, _data, _reply, 0);
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
                    this.mRemote.transact(96, _data, _reply, 0);
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
                    this.mRemote.transact(97, _data, _reply, 0);
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
                    this.mRemote.transact(98, _data, _reply, 0);
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
                    this.mRemote.transact(99, _data, _reply, 0);
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
                    this.mRemote.transact(100, _data, _reply, 0);
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
                    this.mRemote.transact(101, _data, _reply, 0);
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
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    LightState _result = (LightState) _reply.readTypedObject(LightState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setLightStates(
                    int deviceId, int[] lightIds, LightState[] states, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeIntArray(lightIds);
                    _data.writeTypedArray(states, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(103, _data, _reply, 0);
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
                    this.mRemote.transact(104, _data, _reply, 0);
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
                    this.mRemote.transact(105, _data, _reply, 0);
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
                    this.mRemote.transact(106, _data, _reply, 0);
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
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterBatteryListener(
                    int deviceId, IInputDeviceBatteryListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(108, _data, _reply, 0);
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
                    this.mRemote.transact(109, _data, _reply, 0);
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
                    this.mRemote.transact(110, _data, _reply, 0);
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
                    this.mRemote.transact(111, _data, _reply, 0);
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
                    this.mRemote.transact(112, _data, _reply, 0);
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
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    HostUsiVersion _result =
                            (HostUsiVersion) _reply.readTypedObject(HostUsiVersion.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerStickyModifierStateListener(IStickyModifierStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterStickyModifierStateListener(IStickyModifierStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setKeyboardLayoutForInputDevice_enforcePermission()
                throws SecurityException {
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

        protected void registerKeyboardBacklightListener_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void unregisterKeyboardBacklightListener_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void registerStickyModifierStateListener_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MONITOR_STICKY_MODIFIER_STATE,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void unregisterStickyModifierStateListener_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MONITOR_STICKY_MODIFIER_STATE,
                    getCallingPid(),
                    getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 114;
        }
    }
}
