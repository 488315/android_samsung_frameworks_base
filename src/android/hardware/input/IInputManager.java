package android.hardware.input;

import android.Manifest;
import android.app.ActivityThread;
import android.bluetooth.BluetoothDevice;
import android.hardware.input.AidlInputGestureData;
import android.hardware.input.IInputDeviceBatteryListener;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.IInputSensorEventListener;
import android.hardware.input.IKeyEventActivityListener;
import android.hardware.input.IKeyGestureEventListener;
import android.hardware.input.IKeyGestureHandler;
import android.hardware.input.IKeyboardBacklightListener;
import android.hardware.input.IMultiFingerGestureListener;
import android.hardware.input.IPointerIconChangedListener;
import android.hardware.input.ISemLidStateChangedListener;
import android.hardware.input.IStickyModifierStateListener;
import android.hardware.input.ISwitchEventChangedListener;
import android.hardware.input.ITabletModeChangedListener;
import android.hardware.input.IWirelessKeyboardShareChangedListener;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.os.BadParcelableException;
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

    public static class Default implements IInputManager {
        @Override // android.hardware.input.IInputManager
        public int addCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public boolean addDeviceWirelessKeyboardShare(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void addPortAssociation(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void addUniqueIdAssociationByDescriptor(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void addUniqueIdAssociationByPort(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void cancelCurrentTouch() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void changeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int checkInputFeature() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void clearAllModifierKeyRemappings() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void closeLightSession(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void connectByBtDevice(BluetoothDevice bluetoothDevice) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void controlSpenWithToken(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void disableInputDevice(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void disableSensor(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void enableInputDevice(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean enableSensor(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean flushSensor(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void forceFadeIcon(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public AidlInputGestureData[] getAppLaunchBookmarks() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public IInputDeviceBatteryState getBatteryState(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getCurrentSwitchEventState(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public AidlInputGestureData[] getCustomInputGestures(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public PointerIcon getDefaultPointerIcon() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getDisplayIdForPointerIcon() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public PointerIcon getForcedDefaultPointerIcon() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getGamepadProfile(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getGamepadProfileIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getGlobalMetaState(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getInboundQueueLength() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public InputDevice getInputDevice(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getInputDeviceBluetoothAddress(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getInputDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getInputDevicePath(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public AidlInputGestureData getInputGesture(int i, AidlInputGestureData.Trigger trigger) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyCharacterMap getKeyCharacterMap(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public KeyGlyphMap getKeyGlyphMap(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout getKeyboardLayout(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout[] getKeyboardLayoutListForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public long getLastLidEventTimeNanos() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.input.IInputManager
        public int getLidState() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public LightState getLightState(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public List<Light> getLights(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public Map getModifierKeyRemapping() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getMousePointerSpeed() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public int getPointerIconType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public int getScanCodeState(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public InputSensorInfo[] getSensorList(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getSupportButtonNStick() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int getToolTypeForDefaultPointerIcon() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public String getVelocityTrackerStrategy() throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public int[] getVibratorIds(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isDefaultPointerIconChanged() throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public int isInTabletMode() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public int isMicMuted() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isUidTouched(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean isVibrating(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public InputChannel monitorInputForBinder(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void notifyQuickAccess(int i, float f, float f2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void openLightSession(int i, String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void pilferPointers(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean registerKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void registerKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerKeyGestureHandler(int[] iArr, IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerLidStateChangedListener(ISemLidStateChangedListener iSemLidStateChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerMultiFingerGestureListener(IMultiFingerGestureListener iMultiFingerGestureListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerPointerIconChangedListener(IPointerIconChangedListener iPointerIconChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void registerStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerSwitchEventChangedListener(ISwitchEventChangedListener iSwitchEventChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void registerWirelessKeyboardShareChangedListener(IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void remapModifierKey(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeAllCustomInputGestures(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeAllDeviceToGamepadProfile() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeAllGamepadProfiles() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public int removeCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.input.IInputManager
        public void removeDeviceToGamepadProfile(String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeGamepadProfile(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removePortAssociation(String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeUniqueIdAssociationByDescriptor(String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void removeUniqueIdAssociationByPort(String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void resetLockedModifierState() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public long semGetMotionIdleTimeMillis(boolean z) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.input.IInputManager
        public void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setDisplayIdForPointerIcon(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean setGamepadProfileName(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void setHostRoleWirelessKeyboardShare() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype, String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setKeyboardLayoutOverrideForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean setRemapGamepadButton(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void setShowAllTouches(boolean z) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setStartedShutdown(boolean z) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean setTspEnabled(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void setWakeKeyDynamically(String str, boolean z, String str2) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean supportPogoDevice() throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public boolean switchDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void tryPointerSpeed(int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean unregisterKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterKeyGestureHandler(IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void unregisterStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.input.IInputManager
        public void updateDeviceToGamepadProfile(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void updateWirelessKeyboardShareStatus() throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) throws RemoteException {
            return null;
        }

        @Override // android.hardware.input.IInputManager
        public void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.input.IInputManager
        public void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) throws RemoteException {
        }
    }

    int addCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException;

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

    AidlInputGestureData[] getAppLaunchBookmarks() throws RemoteException;

    IInputDeviceBatteryState getBatteryState(int i) throws RemoteException;

    int getCurrentSwitchEventState(int i, boolean z) throws RemoteException;

    AidlInputGestureData[] getCustomInputGestures(int i, int i2) throws RemoteException;

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

    String getInputDevicePath(int i) throws RemoteException;

    AidlInputGestureData getInputGesture(int i, AidlInputGestureData.Trigger trigger) throws RemoteException;

    KeyCharacterMap getKeyCharacterMap(String str) throws RemoteException;

    int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException;

    KeyGlyphMap getKeyGlyphMap(int i) throws RemoteException;

    KeyboardLayout getKeyboardLayout(String str) throws RemoteException;

    KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException;

    KeyboardLayout[] getKeyboardLayoutListForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException;

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

    InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2) throws RemoteException;

    InputChannel monitorInputForBinder(String str, int i, int i2) throws RemoteException;

    void notifyQuickAccess(int i, float f, float f2) throws RemoteException;

    void openLightSession(int i, String str, IBinder iBinder) throws RemoteException;

    void pilferPointers(IBinder iBinder) throws RemoteException;

    void registerBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException;

    void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException;

    boolean registerKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException;

    void registerKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException;

    void registerKeyGestureHandler(int[] iArr, IKeyGestureHandler iKeyGestureHandler) throws RemoteException;

    void registerKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException;

    void registerLidStateChangedListener(ISemLidStateChangedListener iSemLidStateChangedListener) throws RemoteException;

    void registerMultiFingerGestureListener(IMultiFingerGestureListener iMultiFingerGestureListener) throws RemoteException;

    void registerPointerIconChangedListener(IPointerIconChangedListener iPointerIconChangedListener) throws RemoteException;

    boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException;

    void registerStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException;

    void registerSwitchEventChangedListener(ISwitchEventChangedListener iSwitchEventChangedListener) throws RemoteException;

    void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException;

    boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    void registerWirelessKeyboardShareChangedListener(IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) throws RemoteException;

    void remapModifierKey(int i, int i2) throws RemoteException;

    void removeAllCustomInputGestures(int i, int i2) throws RemoteException;

    void removeAllDeviceToGamepadProfile() throws RemoteException;

    void removeAllGamepadProfiles() throws RemoteException;

    int removeCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException;

    void removeDeviceToGamepadProfile(String str) throws RemoteException;

    void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

    void removeGamepadProfile(int i) throws RemoteException;

    void removePortAssociation(String str) throws RemoteException;

    void removeUniqueIdAssociationByDescriptor(String str) throws RemoteException;

    void removeUniqueIdAssociationByPort(String str) throws RemoteException;

    void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException;

    void resetLockedModifierState() throws RemoteException;

    long semGetMotionIdleTimeMillis(boolean z) throws RemoteException;

    void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException;

    void setDisplayIdForPointerIcon(int i) throws RemoteException;

    boolean setGamepadProfileName(int i, String str) throws RemoteException;

    void setHostRoleWirelessKeyboardShare() throws RemoteException;

    void setKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype, String str) throws RemoteException;

    void setKeyboardLayoutOverrideForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException;

    void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) throws RemoteException;

    boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) throws RemoteException;

    boolean setRemapGamepadButton(int i, int i2, int i3) throws RemoteException;

    boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) throws RemoteException;

    void setShowAllTouches(boolean z) throws RemoteException;

    void setStartedShutdown(boolean z) throws RemoteException;

    void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration) throws RemoteException;

    boolean setTspEnabled(int i, boolean z) throws RemoteException;

    void setWakeKeyDynamically(String str, boolean z, String str2) throws RemoteException;

    boolean supportPogoDevice() throws RemoteException;

    boolean switchDeviceWirelessKeyboardShare(String str, int i) throws RemoteException;

    void tryPointerSpeed(int i) throws RemoteException;

    void unregisterBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException;

    boolean unregisterKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException;

    void unregisterKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException;

    void unregisterKeyGestureHandler(IKeyGestureHandler iKeyGestureHandler) throws RemoteException;

    void unregisterKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException;

    void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException;

    void unregisterStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException;

    boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    void updateDeviceToGamepadProfile(String str, int i) throws RemoteException;

    void updateWirelessKeyboardShareStatus() throws RemoteException;

    VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) throws RemoteException;

    void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) throws RemoteException;

    void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputManager {
        public static final String DESCRIPTOR = "android.hardware.input.IInputManager";
        static final int TRANSACTION_addCustomInputGesture = 126;
        static final int TRANSACTION_addDeviceWirelessKeyboardShare = 87;
        static final int TRANSACTION_addPortAssociation = 91;
        static final int TRANSACTION_addUniqueIdAssociationByDescriptor = 93;
        static final int TRANSACTION_addUniqueIdAssociationByPort = 95;
        static final int TRANSACTION_cancelCurrentTouch = 108;
        static final int TRANSACTION_cancelVibrate = 40;
        static final int TRANSACTION_changeDeviceWirelessKeyboardShare = 86;
        static final int TRANSACTION_checkInputFeature = 74;
        static final int TRANSACTION_clearAllModifierKeyRemappings = 24;
        static final int TRANSACTION_closeLightSession = 107;
        static final int TRANSACTION_connectByBtDevice = 90;
        static final int TRANSACTION_controlSpenWithToken = 6;
        static final int TRANSACTION_disableInputDevice = 5;
        static final int TRANSACTION_disableSensor = 101;
        static final int TRANSACTION_enableInputDevice = 4;
        static final int TRANSACTION_enableSensor = 100;
        static final int TRANSACTION_flushSensor = 102;
        static final int TRANSACTION_forceFadeIcon = 81;
        static final int TRANSACTION_getAppLaunchBookmarks = 130;
        static final int TRANSACTION_getBatteryState = 45;
        static final int TRANSACTION_getCurrentSwitchEventState = 31;
        static final int TRANSACTION_getCustomInputGestures = 129;
        static final int TRANSACTION_getDefaultPointerIcon = 48;
        static final int TRANSACTION_getDisplayIdForPointerIcon = 54;
        static final int TRANSACTION_getForcedDefaultPointerIcon = 49;
        static final int TRANSACTION_getGamepadProfile = 66;
        static final int TRANSACTION_getGamepadProfileIds = 67;
        static final int TRANSACTION_getGlobalMetaState = 78;
        static final int TRANSACTION_getHostUsiVersionFromDisplayConfig = 117;
        static final int TRANSACTION_getInboundQueueLength = 73;
        static final int TRANSACTION_getInputDevice = 2;
        static final int TRANSACTION_getInputDeviceBluetoothAddress = 113;
        static final int TRANSACTION_getInputDeviceIds = 3;
        static final int TRANSACTION_getInputDevicePath = 33;
        static final int TRANSACTION_getInputGesture = 125;
        static final int TRANSACTION_getKeyCharacterMap = 9;
        static final int TRANSACTION_getKeyCodeForKeyLocation = 8;
        static final int TRANSACTION_getKeyGlyphMap = 120;
        static final int TRANSACTION_getKeyboardLayout = 18;
        static final int TRANSACTION_getKeyboardLayoutForInputDevice = 19;
        static final int TRANSACTION_getKeyboardLayoutListForInputDevice = 22;
        static final int TRANSACTION_getKeyboardLayouts = 17;
        static final int TRANSACTION_getLastLidEventTimeNanos = 29;
        static final int TRANSACTION_getLidState = 27;
        static final int TRANSACTION_getLightState = 104;
        static final int TRANSACTION_getLights = 103;
        static final int TRANSACTION_getModifierKeyRemapping = 25;
        static final int TRANSACTION_getMousePointerSpeed = 10;
        static final int TRANSACTION_getPointerIconType = 55;
        static final int TRANSACTION_getScanCodeState = 76;
        static final int TRANSACTION_getSensorList = 97;
        static final int TRANSACTION_getSupportButtonNStick = 65;
        static final int TRANSACTION_getToolTypeForDefaultPointerIcon = 51;
        static final int TRANSACTION_getTouchCalibrationForInputDevice = 15;
        static final int TRANSACTION_getVelocityTrackerStrategy = 1;
        static final int TRANSACTION_getVibratorIds = 41;
        static final int TRANSACTION_hasKeys = 7;
        static final int TRANSACTION_injectInputEvent = 12;
        static final int TRANSACTION_injectInputEventToTarget = 13;
        static final int TRANSACTION_isDefaultPointerIconChanged = 50;
        static final int TRANSACTION_isInTabletMode = 35;
        static final int TRANSACTION_isMicMuted = 37;
        static final int TRANSACTION_isUidTouched = 80;
        static final int TRANSACTION_isVibrating = 42;
        static final int TRANSACTION_monitorGestureInput = 69;
        static final int TRANSACTION_monitorGestureInputFiltered = 70;
        static final int TRANSACTION_monitorInputForBinder = 71;
        static final int TRANSACTION_notifyQuickAccess = 82;
        static final int TRANSACTION_openLightSession = 106;
        static final int TRANSACTION_pilferPointers = 114;
        static final int TRANSACTION_registerBatteryListener = 109;
        static final int TRANSACTION_registerInputDevicesChangedListener = 26;
        static final int TRANSACTION_registerKeyEventActivityListener = 111;
        static final int TRANSACTION_registerKeyGestureEventListener = 121;
        static final int TRANSACTION_registerKeyGestureHandler = 123;
        static final int TRANSACTION_registerKeyboardBacklightListener = 115;
        static final int TRANSACTION_registerLidStateChangedListener = 28;
        static final int TRANSACTION_registerMultiFingerGestureListener = 34;
        static final int TRANSACTION_registerPointerIconChangedListener = 52;
        static final int TRANSACTION_registerSensorListener = 98;
        static final int TRANSACTION_registerStickyModifierStateListener = 118;
        static final int TRANSACTION_registerSwitchEventChangedListener = 30;
        static final int TRANSACTION_registerTabletModeChangedListener = 36;
        static final int TRANSACTION_registerVibratorStateListener = 43;
        static final int TRANSACTION_registerWirelessKeyboardShareChangedListener = 83;
        static final int TRANSACTION_remapModifierKey = 23;
        static final int TRANSACTION_removeAllCustomInputGestures = 128;
        static final int TRANSACTION_removeAllDeviceToGamepadProfile = 59;
        static final int TRANSACTION_removeAllGamepadProfiles = 60;
        static final int TRANSACTION_removeCustomInputGesture = 127;
        static final int TRANSACTION_removeDeviceToGamepadProfile = 58;
        static final int TRANSACTION_removeDeviceWirelessKeyboardShare = 85;
        static final int TRANSACTION_removeGamepadProfile = 61;
        static final int TRANSACTION_removePortAssociation = 92;
        static final int TRANSACTION_removeUniqueIdAssociationByDescriptor = 94;
        static final int TRANSACTION_removeUniqueIdAssociationByPort = 96;
        static final int TRANSACTION_requestPointerCapture = 68;
        static final int TRANSACTION_resetLockedModifierState = 131;
        static final int TRANSACTION_semGetMotionIdleTimeMillis = 79;
        static final int TRANSACTION_setDefaultPointerIcon = 47;
        static final int TRANSACTION_setDisplayIdForPointerIcon = 53;
        static final int TRANSACTION_setGamepadProfileName = 62;
        static final int TRANSACTION_setHostRoleWirelessKeyboardShare = 89;
        static final int TRANSACTION_setKeyboardLayoutForInputDevice = 21;
        static final int TRANSACTION_setKeyboardLayoutOverrideForInputDevice = 20;
        static final int TRANSACTION_setLightStates = 105;
        static final int TRANSACTION_setPointerIcon = 46;
        static final int TRANSACTION_setRemapGamepadButton = 63;
        static final int TRANSACTION_setRemapGamepadStick = 64;
        static final int TRANSACTION_setShowAllTouches = 56;
        static final int TRANSACTION_setStartedShutdown = 75;
        static final int TRANSACTION_setTouchCalibrationForInputDevice = 16;
        static final int TRANSACTION_setTspEnabled = 72;
        static final int TRANSACTION_setWakeKeyDynamically = 77;
        static final int TRANSACTION_supportPogoDevice = 32;
        static final int TRANSACTION_switchDeviceWirelessKeyboardShare = 88;
        static final int TRANSACTION_tryPointerSpeed = 11;
        static final int TRANSACTION_unregisterBatteryListener = 110;
        static final int TRANSACTION_unregisterKeyEventActivityListener = 112;
        static final int TRANSACTION_unregisterKeyGestureEventListener = 122;
        static final int TRANSACTION_unregisterKeyGestureHandler = 124;
        static final int TRANSACTION_unregisterKeyboardBacklightListener = 116;
        static final int TRANSACTION_unregisterSensorListener = 99;
        static final int TRANSACTION_unregisterStickyModifierStateListener = 119;
        static final int TRANSACTION_unregisterVibratorStateListener = 44;
        static final int TRANSACTION_updateDeviceToGamepadProfile = 57;
        static final int TRANSACTION_updateWirelessKeyboardShareStatus = 84;
        static final int TRANSACTION_verifyInputEvent = 14;
        static final int TRANSACTION_vibrate = 38;
        static final int TRANSACTION_vibrateCombined = 39;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 130;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IInputManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputManager)) {
                return (IInputManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "setKeyboardLayoutOverrideForInputDevice";
                case 21:
                    return "setKeyboardLayoutForInputDevice";
                case 22:
                    return "getKeyboardLayoutListForInputDevice";
                case 23:
                    return "remapModifierKey";
                case 24:
                    return "clearAllModifierKeyRemappings";
                case 25:
                    return "getModifierKeyRemapping";
                case 26:
                    return "registerInputDevicesChangedListener";
                case 27:
                    return "getLidState";
                case 28:
                    return "registerLidStateChangedListener";
                case 29:
                    return "getLastLidEventTimeNanos";
                case 30:
                    return "registerSwitchEventChangedListener";
                case 31:
                    return "getCurrentSwitchEventState";
                case 32:
                    return "supportPogoDevice";
                case 33:
                    return "getInputDevicePath";
                case 34:
                    return "registerMultiFingerGestureListener";
                case 35:
                    return "isInTabletMode";
                case 36:
                    return "registerTabletModeChangedListener";
                case 37:
                    return "isMicMuted";
                case 38:
                    return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
                case 39:
                    return "vibrateCombined";
                case 40:
                    return "cancelVibrate";
                case 41:
                    return "getVibratorIds";
                case 42:
                    return "isVibrating";
                case 43:
                    return "registerVibratorStateListener";
                case 44:
                    return "unregisterVibratorStateListener";
                case 45:
                    return "getBatteryState";
                case 46:
                    return "setPointerIcon";
                case 47:
                    return "setDefaultPointerIcon";
                case 48:
                    return "getDefaultPointerIcon";
                case 49:
                    return "getForcedDefaultPointerIcon";
                case 50:
                    return "isDefaultPointerIconChanged";
                case 51:
                    return "getToolTypeForDefaultPointerIcon";
                case 52:
                    return "registerPointerIconChangedListener";
                case 53:
                    return "setDisplayIdForPointerIcon";
                case 54:
                    return "getDisplayIdForPointerIcon";
                case 55:
                    return "getPointerIconType";
                case 56:
                    return "setShowAllTouches";
                case 57:
                    return "updateDeviceToGamepadProfile";
                case 58:
                    return "removeDeviceToGamepadProfile";
                case 59:
                    return "removeAllDeviceToGamepadProfile";
                case 60:
                    return "removeAllGamepadProfiles";
                case 61:
                    return "removeGamepadProfile";
                case 62:
                    return "setGamepadProfileName";
                case 63:
                    return "setRemapGamepadButton";
                case 64:
                    return "setRemapGamepadStick";
                case 65:
                    return "getSupportButtonNStick";
                case 66:
                    return "getGamepadProfile";
                case 67:
                    return "getGamepadProfileIds";
                case 68:
                    return "requestPointerCapture";
                case 69:
                    return "monitorGestureInput";
                case 70:
                    return "monitorGestureInputFiltered";
                case 71:
                    return "monitorInputForBinder";
                case 72:
                    return "setTspEnabled";
                case 73:
                    return "getInboundQueueLength";
                case 74:
                    return "checkInputFeature";
                case 75:
                    return "setStartedShutdown";
                case 76:
                    return "getScanCodeState";
                case 77:
                    return "setWakeKeyDynamically";
                case 78:
                    return "getGlobalMetaState";
                case 79:
                    return "semGetMotionIdleTimeMillis";
                case 80:
                    return "isUidTouched";
                case 81:
                    return "forceFadeIcon";
                case 82:
                    return "notifyQuickAccess";
                case 83:
                    return "registerWirelessKeyboardShareChangedListener";
                case 84:
                    return "updateWirelessKeyboardShareStatus";
                case 85:
                    return "removeDeviceWirelessKeyboardShare";
                case 86:
                    return "changeDeviceWirelessKeyboardShare";
                case 87:
                    return "addDeviceWirelessKeyboardShare";
                case 88:
                    return "switchDeviceWirelessKeyboardShare";
                case 89:
                    return "setHostRoleWirelessKeyboardShare";
                case 90:
                    return "connectByBtDevice";
                case 91:
                    return "addPortAssociation";
                case 92:
                    return "removePortAssociation";
                case 93:
                    return "addUniqueIdAssociationByDescriptor";
                case 94:
                    return "removeUniqueIdAssociationByDescriptor";
                case 95:
                    return "addUniqueIdAssociationByPort";
                case 96:
                    return "removeUniqueIdAssociationByPort";
                case 97:
                    return "getSensorList";
                case 98:
                    return "registerSensorListener";
                case 99:
                    return "unregisterSensorListener";
                case 100:
                    return "enableSensor";
                case 101:
                    return "disableSensor";
                case 102:
                    return "flushSensor";
                case 103:
                    return "getLights";
                case 104:
                    return "getLightState";
                case 105:
                    return "setLightStates";
                case 106:
                    return "openLightSession";
                case 107:
                    return "closeLightSession";
                case 108:
                    return "cancelCurrentTouch";
                case 109:
                    return "registerBatteryListener";
                case 110:
                    return "unregisterBatteryListener";
                case 111:
                    return "registerKeyEventActivityListener";
                case 112:
                    return "unregisterKeyEventActivityListener";
                case 113:
                    return "getInputDeviceBluetoothAddress";
                case 114:
                    return "pilferPointers";
                case 115:
                    return "registerKeyboardBacklightListener";
                case 116:
                    return "unregisterKeyboardBacklightListener";
                case 117:
                    return "getHostUsiVersionFromDisplayConfig";
                case 118:
                    return "registerStickyModifierStateListener";
                case 119:
                    return "unregisterStickyModifierStateListener";
                case 120:
                    return "getKeyGlyphMap";
                case 121:
                    return "registerKeyGestureEventListener";
                case 122:
                    return "unregisterKeyGestureEventListener";
                case 123:
                    return "registerKeyGestureHandler";
                case 124:
                    return "unregisterKeyGestureHandler";
                case 125:
                    return "getInputGesture";
                case 126:
                    return "addCustomInputGesture";
                case 127:
                    return "removeCustomInputGesture";
                case 128:
                    return "removeAllCustomInputGestures";
                case 129:
                    return "getCustomInputGestures";
                case 130:
                    return "getAppLaunchBookmarks";
                case 131:
                    return "resetLockedModifierState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String velocityTrackerStrategy = getVelocityTrackerStrategy();
                    parcel2.writeNoException();
                    parcel2.writeString(velocityTrackerStrategy);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputDevice inputDevice = getInputDevice(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputDevice, 1);
                    return true;
                case 3:
                    int[] inputDeviceIds = getInputDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(inputDeviceIds);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableInputDevice(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableInputDevice(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    controlSpenWithToken(readStrongBinder, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt6 = parcel.readInt();
                    if (readInt6 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt6);
                    }
                    boolean[] zArr = readInt6 < 0 ? null : new boolean[readInt6];
                    parcel.enforceNoDataAvail();
                    boolean hasKeys = hasKeys(readInt4, readInt5, createIntArray, zArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasKeys);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyCodeForKeyLocation = getKeyCodeForKeyLocation(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyCodeForKeyLocation);
                    return true;
                case 9:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyCharacterMap keyCharacterMap = getKeyCharacterMap(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCharacterMap, 1);
                    return true;
                case 10:
                    int mousePointerSpeed = getMousePointerSpeed();
                    parcel2.writeNoException();
                    parcel2.writeInt(mousePointerSpeed);
                    return true;
                case 11:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tryPointerSpeed(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean injectInputEvent = injectInputEvent(inputEvent, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectInputEvent);
                    return true;
                case 13:
                    InputEvent inputEvent2 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean injectInputEventToTarget = injectInputEventToTarget(inputEvent2, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectInputEventToTarget);
                    return true;
                case 14:
                    InputEvent inputEvent3 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    VerifiedInputEvent verifyInputEvent = verifyInputEvent(inputEvent3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyInputEvent, 1);
                    return true;
                case 15:
                    String readString2 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TouchCalibration touchCalibrationForInputDevice = getTouchCalibrationForInputDevice(readString2, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(touchCalibrationForInputDevice, 1);
                    return true;
                case 16:
                    String readString3 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    TouchCalibration touchCalibration = (TouchCalibration) parcel.readTypedObject(TouchCalibration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchCalibrationForInputDevice(readString3, readInt14, touchCalibration);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    KeyboardLayout[] keyboardLayouts = getKeyboardLayouts();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayouts, 1);
                    return true;
                case 18:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyboardLayout keyboardLayout = getKeyboardLayout(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayout, 1);
                    return true;
                case 19:
                    InputDeviceIdentifier inputDeviceIdentifier = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int readInt15 = parcel.readInt();
                    InputMethodInfo inputMethodInfo = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyboardLayoutSelectionResult keyboardLayoutForInputDevice = getKeyboardLayoutForInputDevice(inputDeviceIdentifier, readInt15, inputMethodInfo, inputMethodSubtype);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayoutForInputDevice, 1);
                    return true;
                case 20:
                    InputDeviceIdentifier inputDeviceIdentifier2 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setKeyboardLayoutOverrideForInputDevice(inputDeviceIdentifier2, readString5);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    InputDeviceIdentifier inputDeviceIdentifier3 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int readInt16 = parcel.readInt();
                    InputMethodInfo inputMethodInfo2 = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype2 = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setKeyboardLayoutForInputDevice(inputDeviceIdentifier3, readInt16, inputMethodInfo2, inputMethodSubtype2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    InputDeviceIdentifier inputDeviceIdentifier4 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int readInt17 = parcel.readInt();
                    InputMethodInfo inputMethodInfo3 = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype3 = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyboardLayout[] keyboardLayoutListForInputDevice = getKeyboardLayoutListForInputDevice(inputDeviceIdentifier4, readInt17, inputMethodInfo3, inputMethodSubtype3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayoutListForInputDevice, 1);
                    return true;
                case 23:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remapModifierKey(readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    clearAllModifierKeyRemappings();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    Map modifierKeyRemapping = getModifierKeyRemapping();
                    parcel2.writeNoException();
                    parcel2.writeMap(modifierKeyRemapping);
                    return true;
                case 26:
                    IInputDevicesChangedListener asInterface = IInputDevicesChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerInputDevicesChangedListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int lidState = getLidState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lidState);
                    return true;
                case 28:
                    ISemLidStateChangedListener asInterface2 = ISemLidStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerLidStateChangedListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    long lastLidEventTimeNanos = getLastLidEventTimeNanos();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastLidEventTimeNanos);
                    return true;
                case 30:
                    ISwitchEventChangedListener asInterface3 = ISwitchEventChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSwitchEventChangedListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int currentSwitchEventState = getCurrentSwitchEventState(readInt20, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentSwitchEventState);
                    return true;
                case 32:
                    boolean supportPogoDevice = supportPogoDevice();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportPogoDevice);
                    return true;
                case 33:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String inputDevicePath = getInputDevicePath(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeString(inputDevicePath);
                    return true;
                case 34:
                    IMultiFingerGestureListener asInterface4 = IMultiFingerGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMultiFingerGestureListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int isInTabletMode = isInTabletMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(isInTabletMode);
                    return true;
                case 36:
                    ITabletModeChangedListener asInterface5 = ITabletModeChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTabletModeChangedListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int isMicMuted = isMicMuted();
                    parcel2.writeNoException();
                    parcel2.writeInt(isMicMuted);
                    return true;
                case 38:
                    int readInt22 = parcel.readInt();
                    VibrationEffect vibrationEffect = (VibrationEffect) parcel.readTypedObject(VibrationEffect.CREATOR);
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(readInt22, vibrationEffect, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt23 = parcel.readInt();
                    CombinedVibration combinedVibration = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrateCombined(readInt23, combinedVibration, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt24 = parcel.readInt();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(readInt24, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] vibratorIds = getVibratorIds(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 42:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVibrating = isVibrating(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVibrating);
                    return true;
                case 43:
                    int readInt27 = parcel.readInt();
                    IVibratorStateListener asInterface6 = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerVibratorStateListener = registerVibratorStateListener(readInt27, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerVibratorStateListener);
                    return true;
                case 44:
                    int readInt28 = parcel.readInt();
                    IVibratorStateListener asInterface7 = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterVibratorStateListener = unregisterVibratorStateListener(readInt28, asInterface7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterVibratorStateListener);
                    return true;
                case 45:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IInputDeviceBatteryState batteryState = getBatteryState(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryState, 1);
                    return true;
                case 46:
                    PointerIcon pointerIcon = (PointerIcon) parcel.readTypedObject(PointerIcon.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean pointerIcon2 = setPointerIcon(pointerIcon, readInt30, readInt31, readInt32, readStrongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pointerIcon2);
                    return true;
                case 47:
                    int readInt33 = parcel.readInt();
                    PointerIcon pointerIcon3 = (PointerIcon) parcel.readTypedObject(PointerIcon.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultPointerIcon(readInt33, pointerIcon3, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    PointerIcon defaultPointerIcon = getDefaultPointerIcon();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultPointerIcon, 1);
                    return true;
                case 49:
                    PointerIcon forcedDefaultPointerIcon = getForcedDefaultPointerIcon();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(forcedDefaultPointerIcon, 1);
                    return true;
                case 50:
                    boolean isDefaultPointerIconChanged = isDefaultPointerIconChanged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDefaultPointerIconChanged);
                    return true;
                case 51:
                    int toolTypeForDefaultPointerIcon = getToolTypeForDefaultPointerIcon();
                    parcel2.writeNoException();
                    parcel2.writeInt(toolTypeForDefaultPointerIcon);
                    return true;
                case 52:
                    IPointerIconChangedListener asInterface8 = IPointerIconChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPointerIconChangedListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayIdForPointerIcon(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int displayIdForPointerIcon = getDisplayIdForPointerIcon();
                    parcel2.writeNoException();
                    parcel2.writeInt(displayIdForPointerIcon);
                    return true;
                case 55:
                    int pointerIconType = getPointerIconType();
                    parcel2.writeNoException();
                    parcel2.writeInt(pointerIconType);
                    return true;
                case 56:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowAllTouches(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String readString7 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateDeviceToGamepadProfile(readString7, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeDeviceToGamepadProfile(readString8);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    removeAllDeviceToGamepadProfile();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    removeAllGamepadProfiles();
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeGamepadProfile(readInt36);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt37 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean gamepadProfileName = setGamepadProfileName(readInt37, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gamepadProfileName);
                    return true;
                case 63:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean remapGamepadButton = setRemapGamepadButton(readInt38, readInt39, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(remapGamepadButton);
                    return true;
                case 64:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean remapGamepadStick = setRemapGamepadStick(readInt41, readInt42, readInt43, readBoolean5, readBoolean6, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(remapGamepadStick);
                    return true;
                case 65:
                    String supportButtonNStick = getSupportButtonNStick();
                    parcel2.writeNoException();
                    parcel2.writeString(supportButtonNStick);
                    return true;
                case 66:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String gamepadProfile = getGamepadProfile(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeString(gamepadProfile);
                    return true;
                case 67:
                    int[] gamepadProfileIds = getGamepadProfileIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(gamepadProfileIds);
                    return true;
                case 68:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestPointerCapture(readStrongBinder6, readBoolean8);
                    return true;
                case 69:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    String readString10 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMonitor monitorGestureInput = monitorGestureInput(readStrongBinder7, readString10, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(monitorGestureInput, 1);
                    return true;
                case 70:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString11 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMonitor monitorGestureInputFiltered = monitorGestureInputFiltered(readStrongBinder8, readString11, readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(monitorGestureInputFiltered, 1);
                    return true;
                case 71:
                    String readString12 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputChannel monitorInputForBinder = monitorInputForBinder(readString12, readInt48, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(monitorInputForBinder, 1);
                    return true;
                case 72:
                    int readInt50 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean tspEnabled = setTspEnabled(readInt50, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tspEnabled);
                    return true;
                case 73:
                    int inboundQueueLength = getInboundQueueLength();
                    parcel2.writeNoException();
                    parcel2.writeInt(inboundQueueLength);
                    return true;
                case 74:
                    int checkInputFeature = checkInputFeature();
                    parcel2.writeNoException();
                    parcel2.writeInt(checkInputFeature);
                    return true;
                case 75:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStartedShutdown(readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int scanCodeState = getScanCodeState(readInt51, readInt52, readInt53);
                    parcel2.writeNoException();
                    parcel2.writeInt(scanCodeState);
                    return true;
                case 77:
                    String readString13 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWakeKeyDynamically(readString13, readBoolean11, readString14);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int globalMetaState = getGlobalMetaState(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalMetaState);
                    return true;
                case 79:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long semGetMotionIdleTimeMillis = semGetMotionIdleTimeMillis(readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeLong(semGetMotionIdleTimeMillis);
                    return true;
                case 80:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidTouched = isUidTouched(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidTouched);
                    return true;
                case 81:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceFadeIcon(readInt56);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int readInt57 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    notifyQuickAccess(readInt57, readFloat, readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IWirelessKeyboardShareChangedListener asInterface9 = IWirelessKeyboardShareChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWirelessKeyboardShareChangedListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    updateWirelessKeyboardShareStatus();
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String readString15 = parcel.readString();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDeviceWirelessKeyboardShare(readString15, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String readString16 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeDeviceWirelessKeyboardShare(readString16, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addDeviceWirelessKeyboardShare = addDeviceWirelessKeyboardShare(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addDeviceWirelessKeyboardShare);
                    return true;
                case 88:
                    String readString17 = parcel.readString();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean switchDeviceWirelessKeyboardShare = switchDeviceWirelessKeyboardShare(readString17, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchDeviceWirelessKeyboardShare);
                    return true;
                case 89:
                    setHostRoleWirelessKeyboardShare();
                    parcel2.writeNoException();
                    return true;
                case 90:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectByBtDevice(bluetoothDevice);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String readString18 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortAssociation(readString18, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePortAssociation(readString19);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUniqueIdAssociationByDescriptor(readString20, readString21);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUniqueIdAssociationByDescriptor(readString22);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUniqueIdAssociationByPort(readString23, readString24);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUniqueIdAssociationByPort(readString25);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputSensorInfo[] sensorList = getSensorList(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(sensorList, 1);
                    return true;
                case 98:
                    IInputSensorEventListener asInterface10 = IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSensorListener = registerSensorListener(asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSensorListener);
                    return true;
                case 99:
                    IInputSensorEventListener asInterface11 = IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSensorListener(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableSensor = enableSensor(readInt64, readInt65, readInt66, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableSensor);
                    return true;
                case 101:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSensor(readInt68, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean flushSensor = flushSensor(readInt70, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(flushSensor);
                    return true;
                case 103:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Light> lights = getLights(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(lights, 1);
                    return true;
                case 104:
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LightState lightState = getLightState(readInt73, readInt74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lightState, 1);
                    return true;
                case 105:
                    int readInt75 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    LightState[] lightStateArr = (LightState[]) parcel.createTypedArray(LightState.CREATOR);
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setLightStates(readInt75, createIntArray2, lightStateArr, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int readInt76 = parcel.readInt();
                    String readString26 = parcel.readString();
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    openLightSession(readInt76, readString26, readStrongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    int readInt77 = parcel.readInt();
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeLightSession(readInt77, readStrongBinder11);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    cancelCurrentTouch();
                    parcel2.writeNoException();
                    return true;
                case 109:
                    int readInt78 = parcel.readInt();
                    IInputDeviceBatteryListener asInterface12 = IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBatteryListener(readInt78, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    int readInt79 = parcel.readInt();
                    IInputDeviceBatteryListener asInterface13 = IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBatteryListener(readInt79, asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IKeyEventActivityListener asInterface14 = IKeyEventActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerKeyEventActivityListener = registerKeyEventActivityListener(asInterface14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerKeyEventActivityListener);
                    return true;
                case 112:
                    IKeyEventActivityListener asInterface15 = IKeyEventActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterKeyEventActivityListener = unregisterKeyEventActivityListener(asInterface15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterKeyEventActivityListener);
                    return true;
                case 113:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String inputDeviceBluetoothAddress = getInputDeviceBluetoothAddress(readInt80);
                    parcel2.writeNoException();
                    parcel2.writeString(inputDeviceBluetoothAddress);
                    return true;
                case 114:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pilferPointers(readStrongBinder12);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    IKeyboardBacklightListener asInterface16 = IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyboardBacklightListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    IKeyboardBacklightListener asInterface17 = IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyboardBacklightListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    HostUsiVersion hostUsiVersionFromDisplayConfig = getHostUsiVersionFromDisplayConfig(readInt81);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hostUsiVersionFromDisplayConfig, 1);
                    return true;
                case 118:
                    IStickyModifierStateListener asInterface18 = IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStickyModifierStateListener(asInterface18);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    IStickyModifierStateListener asInterface19 = IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStickyModifierStateListener(asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyGlyphMap keyGlyphMap = getKeyGlyphMap(readInt82);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyGlyphMap, 1);
                    return true;
                case 121:
                    IKeyGestureEventListener asInterface20 = IKeyGestureEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyGestureEventListener(asInterface20);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    IKeyGestureEventListener asInterface21 = IKeyGestureEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyGestureEventListener(asInterface21);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int[] createIntArray3 = parcel.createIntArray();
                    IKeyGestureHandler asInterface22 = IKeyGestureHandler.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyGestureHandler(createIntArray3, asInterface22);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    IKeyGestureHandler asInterface23 = IKeyGestureHandler.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyGestureHandler(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    int readInt83 = parcel.readInt();
                    AidlInputGestureData.Trigger trigger = (AidlInputGestureData.Trigger) parcel.readTypedObject(AidlInputGestureData.Trigger.CREATOR);
                    parcel.enforceNoDataAvail();
                    AidlInputGestureData inputGesture = getInputGesture(readInt83, trigger);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputGesture, 1);
                    return true;
                case 126:
                    int readInt84 = parcel.readInt();
                    AidlInputGestureData aidlInputGestureData = (AidlInputGestureData) parcel.readTypedObject(AidlInputGestureData.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addCustomInputGesture = addCustomInputGesture(readInt84, aidlInputGestureData);
                    parcel2.writeNoException();
                    parcel2.writeInt(addCustomInputGesture);
                    return true;
                case 127:
                    int readInt85 = parcel.readInt();
                    AidlInputGestureData aidlInputGestureData2 = (AidlInputGestureData) parcel.readTypedObject(AidlInputGestureData.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removeCustomInputGesture = removeCustomInputGesture(readInt85, aidlInputGestureData2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeCustomInputGesture);
                    return true;
                case 128:
                    int readInt86 = parcel.readInt();
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllCustomInputGestures(readInt86, readInt87);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    int readInt88 = parcel.readInt();
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AidlInputGestureData[] customInputGestures = getCustomInputGestures(readInt88, readInt89);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(customInputGestures, 1);
                    return true;
                case 130:
                    AidlInputGestureData[] appLaunchBookmarks = getAppLaunchBookmarks();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appLaunchBookmarks, 1);
                    return true;
                case 131:
                    resetLockedModifierState();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInputManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputDevice getInputDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputDevice) obtain2.readTypedObject(InputDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getInputDeviceIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void enableInputDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableInputDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void controlSpenWithToken(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(zArr.length);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readBooleanArray(zArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyCharacterMap getKeyCharacterMap(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyCharacterMap) obtain2.readTypedObject(KeyCharacterMap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getMousePointerSpeed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void tryPointerSpeed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifiedInputEvent) obtain2.readTypedObject(VerifiedInputEvent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TouchCalibration) obtain2.readTypedObject(TouchCalibration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(touchCalibration, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyboardLayout[]) obtain2.createTypedArray(KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout getKeyboardLayout(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyboardLayout) obtain2.readTypedObject(KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyboardLayoutSelectionResult) obtain2.readTypedObject(KeyboardLayoutSelectionResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setKeyboardLayoutOverrideForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayoutListForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputDeviceIdentifier, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputMethodInfo, 0);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyboardLayout[]) obtain2.createTypedArray(KeyboardLayout.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void remapModifierKey(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void clearAllModifierKeyRemappings() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public Map getModifierKeyRemapping() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputDevicesChangedListener);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getLidState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerLidStateChangedListener(ISemLidStateChangedListener iSemLidStateChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemLidStateChangedListener);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public long getLastLidEventTimeNanos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerSwitchEventChangedListener(ISwitchEventChangedListener iSwitchEventChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSwitchEventChangedListener);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getCurrentSwitchEventState(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean supportPogoDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getInputDevicePath(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerMultiFingerGestureListener(IMultiFingerGestureListener iMultiFingerGestureListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMultiFingerGestureListener);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isInTabletMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTabletModeChangedListener);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isMicMuted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(vibrationEffect, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getVibratorIds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isVibrating(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public IInputDeviceBatteryState getBatteryState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IInputDeviceBatteryState) obtain2.readTypedObject(IInputDeviceBatteryState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pointerIcon, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerIcon, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public PointerIcon getDefaultPointerIcon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PointerIcon) obtain2.readTypedObject(PointerIcon.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public PointerIcon getForcedDefaultPointerIcon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PointerIcon) obtain2.readTypedObject(PointerIcon.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isDefaultPointerIconChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getToolTypeForDefaultPointerIcon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerPointerIconChangedListener(IPointerIconChangedListener iPointerIconChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPointerIconChangedListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setDisplayIdForPointerIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getDisplayIdForPointerIcon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getPointerIconType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setShowAllTouches(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void updateDeviceToGamepadProfile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeDeviceToGamepadProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllDeviceToGamepadProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllGamepadProfiles() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeGamepadProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setGamepadProfileName(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadButton(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getSupportButtonNStick() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getGamepadProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getGamepadProfileIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(68, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMonitor) obtain2.readTypedObject(InputMonitor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMonitor) obtain2.readTypedObject(InputMonitor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputChannel monitorInputForBinder(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputChannel) obtain2.readTypedObject(InputChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setTspEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getInboundQueueLength() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int checkInputFeature() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setStartedShutdown(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getScanCodeState(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setWakeKeyDynamically(String str, boolean z, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getGlobalMetaState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public long semGetMotionIdleTimeMillis(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isUidTouched(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void forceFadeIcon(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void notifyQuickAccess(int i, float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerWirelessKeyboardShareChangedListener(IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWirelessKeyboardShareChangedListener);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void updateWirelessKeyboardShareStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void changeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean addDeviceWirelessKeyboardShare(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean switchDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setHostRoleWirelessKeyboardShare() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void connectByBtDevice(BluetoothDevice bluetoothDevice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothDevice, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addPortAssociation(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removePortAssociation(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByDescriptor(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByDescriptor(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByPort(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByPort(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputSensorInfo[] getSensorList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputSensorInfo[]) obtain2.createTypedArray(InputSensorInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean enableSensor(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableSensor(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean flushSensor(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public List<Light> getLights(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Light.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public LightState getLightState(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LightState) obtain2.readTypedObject(LightState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedArray(lightStateArr, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void openLightSession(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void closeLightSession(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelCurrentTouch() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyEventActivityListener);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyEventActivityListener);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getInputDeviceBluetoothAddress(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void pilferPointers(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HostUsiVersion) obtain2.readTypedObject(HostUsiVersion.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyGlyphMap getKeyGlyphMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyGlyphMap) obtain2.readTypedObject(KeyGlyphMap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyGestureEventListener);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyGestureEventListener);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyGestureHandler(int[] iArr, IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iKeyGestureHandler);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyGestureHandler(IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyGestureHandler);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData getInputGesture(int i, AidlInputGestureData.Trigger trigger) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(trigger, 0);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AidlInputGestureData) obtain2.readTypedObject(AidlInputGestureData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int addCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(aidlInputGestureData, 0);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int removeCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(aidlInputGestureData, 0);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllCustomInputGestures(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData[] getCustomInputGestures(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AidlInputGestureData[]) obtain2.createTypedArray(AidlInputGestureData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData[] getAppLaunchBookmarks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AidlInputGestureData[]) obtain2.createTypedArray(AidlInputGestureData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void resetLockedModifierState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setKeyboardLayoutOverrideForInputDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void setKeyboardLayoutForInputDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_KEYBOARD_LAYOUT, getCallingPid(), getCallingUid());
        }

        protected void remapModifierKey_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void clearAllModifierKeyRemappings_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void getModifierKeyRemapping_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REMAP_MODIFIER_KEYS, getCallingPid(), getCallingUid());
        }

        protected void registerKeyEventActivityListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LISTEN_FOR_KEY_ACTIVITY, getCallingPid(), getCallingUid());
        }

        protected void unregisterKeyEventActivityListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LISTEN_FOR_KEY_ACTIVITY, getCallingPid(), getCallingUid());
        }

        protected void getInputDeviceBluetoothAddress_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH, getCallingPid(), getCallingUid());
        }

        protected void pilferPointers_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MONITOR_INPUT, getCallingPid(), getCallingUid());
        }

        protected void registerKeyboardBacklightListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
        }

        protected void unregisterKeyboardBacklightListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MONITOR_KEYBOARD_BACKLIGHT, getCallingPid(), getCallingUid());
        }

        protected void registerStickyModifierStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MONITOR_STICKY_MODIFIER_STATE, getCallingPid(), getCallingUid());
        }

        protected void unregisterStickyModifierStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MONITOR_STICKY_MODIFIER_STATE, getCallingPid(), getCallingUid());
        }
    }
}
