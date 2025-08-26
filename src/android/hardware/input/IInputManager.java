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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputManager)) {
                return (IInputManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputDevice inputDevice = getInputDevice(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputDevice, 1);
                    return true;
                case 3:
                    int[] inputDeviceIds = getInputDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(inputDeviceIds);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableInputDevice(i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableInputDevice(i5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    controlSpenWithToken(strongBinder, z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int i8 = parcel.readInt();
                    if (i8 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i8);
                    }
                    boolean[] zArr = i8 < 0 ? null : new boolean[i8];
                    parcel.enforceNoDataAvail();
                    boolean zHasKeys = hasKeys(i6, i7, iArrCreateIntArray, zArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasKeys);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyCodeForKeyLocation = getKeyCodeForKeyLocation(i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyCodeForKeyLocation);
                    return true;
                case 9:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyCharacterMap keyCharacterMap = getKeyCharacterMap(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCharacterMap, 1);
                    return true;
                case 10:
                    int mousePointerSpeed = getMousePointerSpeed();
                    parcel2.writeNoException();
                    parcel2.writeInt(mousePointerSpeed);
                    return true;
                case 11:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tryPointerSpeed(i11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zInjectInputEvent = injectInputEvent(inputEvent, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectInputEvent);
                    return true;
                case 13:
                    InputEvent inputEvent2 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zInjectInputEventToTarget = injectInputEventToTarget(inputEvent2, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectInputEventToTarget);
                    return true;
                case 14:
                    InputEvent inputEvent3 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    VerifiedInputEvent verifiedInputEventVerifyInputEvent = verifyInputEvent(inputEvent3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifiedInputEventVerifyInputEvent, 1);
                    return true;
                case 15:
                    String string2 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TouchCalibration touchCalibrationForInputDevice = getTouchCalibrationForInputDevice(string2, i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(touchCalibrationForInputDevice, 1);
                    return true;
                case 16:
                    String string3 = parcel.readString();
                    int i16 = parcel.readInt();
                    TouchCalibration touchCalibration = (TouchCalibration) parcel.readTypedObject(TouchCalibration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchCalibrationForInputDevice(string3, i16, touchCalibration);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    KeyboardLayout[] keyboardLayouts = getKeyboardLayouts();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayouts, 1);
                    return true;
                case 18:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyboardLayout keyboardLayout = getKeyboardLayout(string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayout, 1);
                    return true;
                case 19:
                    InputDeviceIdentifier inputDeviceIdentifier = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int i17 = parcel.readInt();
                    InputMethodInfo inputMethodInfo = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyboardLayoutSelectionResult keyboardLayoutForInputDevice = getKeyboardLayoutForInputDevice(inputDeviceIdentifier, i17, inputMethodInfo, inputMethodSubtype);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyboardLayoutForInputDevice, 1);
                    return true;
                case 20:
                    InputDeviceIdentifier inputDeviceIdentifier2 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setKeyboardLayoutOverrideForInputDevice(inputDeviceIdentifier2, string5);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    InputDeviceIdentifier inputDeviceIdentifier3 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int i18 = parcel.readInt();
                    InputMethodInfo inputMethodInfo2 = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype2 = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setKeyboardLayoutForInputDevice(inputDeviceIdentifier3, i18, inputMethodInfo2, inputMethodSubtype2, string6);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    InputDeviceIdentifier inputDeviceIdentifier4 = (InputDeviceIdentifier) parcel.readTypedObject(InputDeviceIdentifier.CREATOR);
                    int i19 = parcel.readInt();
                    InputMethodInfo inputMethodInfo3 = (InputMethodInfo) parcel.readTypedObject(InputMethodInfo.CREATOR);
                    InputMethodSubtype inputMethodSubtype3 = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyboardLayout[] keyboardLayoutListForInputDevice = getKeyboardLayoutListForInputDevice(inputDeviceIdentifier4, i19, inputMethodInfo3, inputMethodSubtype3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyboardLayoutListForInputDevice, 1);
                    return true;
                case 23:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remapModifierKey(i20, i21);
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
                    IInputDevicesChangedListener iInputDevicesChangedListenerAsInterface = IInputDevicesChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerInputDevicesChangedListener(iInputDevicesChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int lidState = getLidState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lidState);
                    return true;
                case 28:
                    ISemLidStateChangedListener iSemLidStateChangedListenerAsInterface = ISemLidStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerLidStateChangedListener(iSemLidStateChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    long lastLidEventTimeNanos = getLastLidEventTimeNanos();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastLidEventTimeNanos);
                    return true;
                case 30:
                    ISwitchEventChangedListener iSwitchEventChangedListenerAsInterface = ISwitchEventChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSwitchEventChangedListener(iSwitchEventChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i22 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int currentSwitchEventState = getCurrentSwitchEventState(i22, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentSwitchEventState);
                    return true;
                case 32:
                    boolean zSupportPogoDevice = supportPogoDevice();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportPogoDevice);
                    return true;
                case 33:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String inputDevicePath = getInputDevicePath(i23);
                    parcel2.writeNoException();
                    parcel2.writeString(inputDevicePath);
                    return true;
                case 34:
                    IMultiFingerGestureListener iMultiFingerGestureListenerAsInterface = IMultiFingerGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMultiFingerGestureListener(iMultiFingerGestureListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int iIsInTabletMode = isInTabletMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsInTabletMode);
                    return true;
                case 36:
                    ITabletModeChangedListener iTabletModeChangedListenerAsInterface = ITabletModeChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTabletModeChangedListener(iTabletModeChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int iIsMicMuted = isMicMuted();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsMicMuted);
                    return true;
                case 38:
                    int i24 = parcel.readInt();
                    VibrationEffect vibrationEffect = (VibrationEffect) parcel.readTypedObject(VibrationEffect.CREATOR);
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(i24, vibrationEffect, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i25 = parcel.readInt();
                    CombinedVibration combinedVibration = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrateCombined(i25, combinedVibration, strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i26 = parcel.readInt();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(i26, strongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] vibratorIds = getVibratorIds(i27);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 42:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVibrating = isVibrating(i28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVibrating);
                    return true;
                case 43:
                    int i29 = parcel.readInt();
                    IVibratorStateListener iVibratorStateListenerAsInterface = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterVibratorStateListener = registerVibratorStateListener(i29, iVibratorStateListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterVibratorStateListener);
                    return true;
                case 44:
                    int i30 = parcel.readInt();
                    IVibratorStateListener iVibratorStateListenerAsInterface2 = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterVibratorStateListener = unregisterVibratorStateListener(i30, iVibratorStateListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterVibratorStateListener);
                    return true;
                case 45:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IInputDeviceBatteryState batteryState = getBatteryState(i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryState, 1);
                    return true;
                case 46:
                    PointerIcon pointerIcon = (PointerIcon) parcel.readTypedObject(PointerIcon.CREATOR);
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean pointerIcon2 = setPointerIcon(pointerIcon, i32, i33, i34, strongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pointerIcon2);
                    return true;
                case 47:
                    int i35 = parcel.readInt();
                    PointerIcon pointerIcon3 = (PointerIcon) parcel.readTypedObject(PointerIcon.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultPointerIcon(i35, pointerIcon3, z3);
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
                    boolean zIsDefaultPointerIconChanged = isDefaultPointerIconChanged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDefaultPointerIconChanged);
                    return true;
                case 51:
                    int toolTypeForDefaultPointerIcon = getToolTypeForDefaultPointerIcon();
                    parcel2.writeNoException();
                    parcel2.writeInt(toolTypeForDefaultPointerIcon);
                    return true;
                case 52:
                    IPointerIconChangedListener iPointerIconChangedListenerAsInterface = IPointerIconChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPointerIconChangedListener(iPointerIconChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayIdForPointerIcon(i36);
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
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowAllTouches(z4);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String string7 = parcel.readString();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateDeviceToGamepadProfile(string7, i37);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeDeviceToGamepadProfile(string8);
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
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeGamepadProfile(i38);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i39 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean gamepadProfileName = setGamepadProfileName(i39, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gamepadProfileName);
                    return true;
                case 63:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean remapGamepadButton = setRemapGamepadButton(i40, i41, i42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(remapGamepadButton);
                    return true;
                case 64:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean remapGamepadStick = setRemapGamepadStick(i43, i44, i45, z5, z6, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(remapGamepadStick);
                    return true;
                case 65:
                    String supportButtonNStick = getSupportButtonNStick();
                    parcel2.writeNoException();
                    parcel2.writeString(supportButtonNStick);
                    return true;
                case 66:
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String gamepadProfile = getGamepadProfile(i46);
                    parcel2.writeNoException();
                    parcel2.writeString(gamepadProfile);
                    return true;
                case 67:
                    int[] gamepadProfileIds = getGamepadProfileIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(gamepadProfileIds);
                    return true;
                case 68:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestPointerCapture(strongBinder6, z8);
                    return true;
                case 69:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    String string10 = parcel.readString();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMonitor inputMonitorMonitorGestureInput = monitorGestureInput(strongBinder7, string10, i47);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputMonitorMonitorGestureInput, 1);
                    return true;
                case 70:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string11 = parcel.readString();
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMonitor inputMonitorMonitorGestureInputFiltered = monitorGestureInputFiltered(strongBinder8, string11, i48, i49);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputMonitorMonitorGestureInputFiltered, 1);
                    return true;
                case 71:
                    String string12 = parcel.readString();
                    int i50 = parcel.readInt();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputChannel inputChannelMonitorInputForBinder = monitorInputForBinder(string12, i50, i51);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannelMonitorInputForBinder, 1);
                    return true;
                case 72:
                    int i52 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean tspEnabled = setTspEnabled(i52, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tspEnabled);
                    return true;
                case 73:
                    int inboundQueueLength = getInboundQueueLength();
                    parcel2.writeNoException();
                    parcel2.writeInt(inboundQueueLength);
                    return true;
                case 74:
                    int iCheckInputFeature = checkInputFeature();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckInputFeature);
                    return true;
                case 75:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStartedShutdown(z10);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int scanCodeState = getScanCodeState(i53, i54, i55);
                    parcel2.writeNoException();
                    parcel2.writeInt(scanCodeState);
                    return true;
                case 77:
                    String string13 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWakeKeyDynamically(string13, z11, string14);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int globalMetaState = getGlobalMetaState(i56);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalMetaState);
                    return true;
                case 79:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long jSemGetMotionIdleTimeMillis = semGetMotionIdleTimeMillis(z12);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSemGetMotionIdleTimeMillis);
                    return true;
                case 80:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidTouched = isUidTouched(i57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidTouched);
                    return true;
                case 81:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceFadeIcon(i58);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int i59 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    notifyQuickAccess(i59, f, f2);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListenerAsInterface = IWirelessKeyboardShareChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWirelessKeyboardShareChangedListener(iWirelessKeyboardShareChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    updateWirelessKeyboardShareStatus();
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String string15 = parcel.readString();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDeviceWirelessKeyboardShare(string15, i60);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String string16 = parcel.readString();
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeDeviceWirelessKeyboardShare(string16, i61);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddDeviceWirelessKeyboardShare = addDeviceWirelessKeyboardShare(i62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddDeviceWirelessKeyboardShare);
                    return true;
                case 88:
                    String string17 = parcel.readString();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSwitchDeviceWirelessKeyboardShare = switchDeviceWirelessKeyboardShare(string17, i63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSwitchDeviceWirelessKeyboardShare);
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
                    String string18 = parcel.readString();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortAssociation(string18, i64);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePortAssociation(string19);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUniqueIdAssociationByDescriptor(string20, string21);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUniqueIdAssociationByDescriptor(string22);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUniqueIdAssociationByPort(string23, string24);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUniqueIdAssociationByPort(string25);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputSensorInfo[] sensorList = getSensorList(i65);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(sensorList, 1);
                    return true;
                case 98:
                    IInputSensorEventListener iInputSensorEventListenerAsInterface = IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSensorListener = registerSensorListener(iInputSensorEventListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSensorListener);
                    return true;
                case 99:
                    IInputSensorEventListener iInputSensorEventListenerAsInterface2 = IInputSensorEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSensorListener(iInputSensorEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableSensor = enableSensor(i66, i67, i68, i69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableSensor);
                    return true;
                case 101:
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSensor(i70, i71);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int i72 = parcel.readInt();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zFlushSensor = flushSensor(i72, i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFlushSensor);
                    return true;
                case 103:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Light> lights = getLights(i74);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(lights, 1);
                    return true;
                case 104:
                    int i75 = parcel.readInt();
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    LightState lightState = getLightState(i75, i76);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lightState, 1);
                    return true;
                case 105:
                    int i77 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    LightState[] lightStateArr = (LightState[]) parcel.createTypedArray(LightState.CREATOR);
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setLightStates(i77, iArrCreateIntArray2, lightStateArr, strongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int i78 = parcel.readInt();
                    String string26 = parcel.readString();
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    openLightSession(i78, string26, strongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    int i79 = parcel.readInt();
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeLightSession(i79, strongBinder11);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    cancelCurrentTouch();
                    parcel2.writeNoException();
                    return true;
                case 109:
                    int i80 = parcel.readInt();
                    IInputDeviceBatteryListener iInputDeviceBatteryListenerAsInterface = IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBatteryListener(i80, iInputDeviceBatteryListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    int i81 = parcel.readInt();
                    IInputDeviceBatteryListener iInputDeviceBatteryListenerAsInterface2 = IInputDeviceBatteryListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBatteryListener(i81, iInputDeviceBatteryListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IKeyEventActivityListener iKeyEventActivityListenerAsInterface = IKeyEventActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterKeyEventActivityListener = registerKeyEventActivityListener(iKeyEventActivityListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterKeyEventActivityListener);
                    return true;
                case 112:
                    IKeyEventActivityListener iKeyEventActivityListenerAsInterface2 = IKeyEventActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterKeyEventActivityListener = unregisterKeyEventActivityListener(iKeyEventActivityListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterKeyEventActivityListener);
                    return true;
                case 113:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String inputDeviceBluetoothAddress = getInputDeviceBluetoothAddress(i82);
                    parcel2.writeNoException();
                    parcel2.writeString(inputDeviceBluetoothAddress);
                    return true;
                case 114:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pilferPointers(strongBinder12);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    IKeyboardBacklightListener iKeyboardBacklightListenerAsInterface = IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyboardBacklightListener(iKeyboardBacklightListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    IKeyboardBacklightListener iKeyboardBacklightListenerAsInterface2 = IKeyboardBacklightListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyboardBacklightListener(iKeyboardBacklightListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    HostUsiVersion hostUsiVersionFromDisplayConfig = getHostUsiVersionFromDisplayConfig(i83);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hostUsiVersionFromDisplayConfig, 1);
                    return true;
                case 118:
                    IStickyModifierStateListener iStickyModifierStateListenerAsInterface = IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStickyModifierStateListener(iStickyModifierStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    IStickyModifierStateListener iStickyModifierStateListenerAsInterface2 = IStickyModifierStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStickyModifierStateListener(iStickyModifierStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyGlyphMap keyGlyphMap = getKeyGlyphMap(i84);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyGlyphMap, 1);
                    return true;
                case 121:
                    IKeyGestureEventListener iKeyGestureEventListenerAsInterface = IKeyGestureEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyGestureEventListener(iKeyGestureEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    IKeyGestureEventListener iKeyGestureEventListenerAsInterface2 = IKeyGestureEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyGestureEventListener(iKeyGestureEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    IKeyGestureHandler iKeyGestureHandlerAsInterface = IKeyGestureHandler.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerKeyGestureHandler(iArrCreateIntArray3, iKeyGestureHandlerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    IKeyGestureHandler iKeyGestureHandlerAsInterface2 = IKeyGestureHandler.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterKeyGestureHandler(iKeyGestureHandlerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    int i85 = parcel.readInt();
                    AidlInputGestureData.Trigger trigger = (AidlInputGestureData.Trigger) parcel.readTypedObject(AidlInputGestureData.Trigger.CREATOR);
                    parcel.enforceNoDataAvail();
                    AidlInputGestureData inputGesture = getInputGesture(i85, trigger);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputGesture, 1);
                    return true;
                case 126:
                    int i86 = parcel.readInt();
                    AidlInputGestureData aidlInputGestureData = (AidlInputGestureData) parcel.readTypedObject(AidlInputGestureData.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddCustomInputGesture = addCustomInputGesture(i86, aidlInputGestureData);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddCustomInputGesture);
                    return true;
                case 127:
                    int i87 = parcel.readInt();
                    AidlInputGestureData aidlInputGestureData2 = (AidlInputGestureData) parcel.readTypedObject(AidlInputGestureData.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRemoveCustomInputGesture = removeCustomInputGesture(i87, aidlInputGestureData2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveCustomInputGesture);
                    return true;
                case 128:
                    int i88 = parcel.readInt();
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllCustomInputGestures(i88, i89);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    int i90 = parcel.readInt();
                    int i91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AidlInputGestureData[] customInputGestures = getCustomInputGestures(i90, i91);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputDevice getInputDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputDevice) parcelObtain2.readTypedObject(InputDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getInputDeviceIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void enableInputDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableInputDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void controlSpenWithToken(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(zArr.length);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readBooleanArray(zArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getKeyCodeForKeyLocation(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyCharacterMap getKeyCharacterMap(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyCharacterMap) parcelObtain2.readTypedObject(KeyCharacterMap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getMousePointerSpeed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void tryPointerSpeed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEvent(InputEvent inputEvent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean injectInputEventToTarget(InputEvent inputEvent, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifiedInputEvent) parcelObtain2.readTypedObject(VerifiedInputEvent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public TouchCalibration getTouchCalibrationForInputDevice(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TouchCalibration) parcelObtain2.readTypedObject(TouchCalibration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setTouchCalibrationForInputDevice(String str, int i, TouchCalibration touchCalibration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(touchCalibration, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayouts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyboardLayout[]) parcelObtain2.createTypedArray(KeyboardLayout.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout getKeyboardLayout(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyboardLayout) parcelObtain2.readTypedObject(KeyboardLayout.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputDeviceIdentifier, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(inputMethodInfo, 0);
                    parcelObtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyboardLayoutSelectionResult) parcelObtain2.readTypedObject(KeyboardLayoutSelectionResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setKeyboardLayoutOverrideForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputDeviceIdentifier, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputDeviceIdentifier, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(inputMethodInfo, 0);
                    parcelObtain.writeTypedObject(inputMethodSubtype, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyboardLayout[] getKeyboardLayoutListForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputDeviceIdentifier, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(inputMethodInfo, 0);
                    parcelObtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyboardLayout[]) parcelObtain2.createTypedArray(KeyboardLayout.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void remapModifierKey(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void clearAllModifierKeyRemappings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public Map getModifierKeyRemapping() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerInputDevicesChangedListener(IInputDevicesChangedListener iInputDevicesChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputDevicesChangedListener);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getLidState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerLidStateChangedListener(ISemLidStateChangedListener iSemLidStateChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemLidStateChangedListener);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public long getLastLidEventTimeNanos() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerSwitchEventChangedListener(ISwitchEventChangedListener iSwitchEventChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSwitchEventChangedListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getCurrentSwitchEventState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean supportPogoDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getInputDevicePath(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerMultiFingerGestureListener(IMultiFingerGestureListener iMultiFingerGestureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMultiFingerGestureListener);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isInTabletMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerTabletModeChangedListener(ITabletModeChangedListener iTabletModeChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTabletModeChangedListener);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int isMicMuted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(vibrationEffect, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void vibrateCombined(int i, CombinedVibration combinedVibration, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(combinedVibration, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getVibratorIds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isVibrating(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public IInputDeviceBatteryState getBatteryState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IInputDeviceBatteryState) parcelObtain2.readTypedObject(IInputDeviceBatteryState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointerIcon, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setDefaultPointerIcon(int i, PointerIcon pointerIcon, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pointerIcon, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public PointerIcon getDefaultPointerIcon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PointerIcon) parcelObtain2.readTypedObject(PointerIcon.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public PointerIcon getForcedDefaultPointerIcon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PointerIcon) parcelObtain2.readTypedObject(PointerIcon.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isDefaultPointerIconChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getToolTypeForDefaultPointerIcon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerPointerIconChangedListener(IPointerIconChangedListener iPointerIconChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPointerIconChangedListener);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setDisplayIdForPointerIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getDisplayIdForPointerIcon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getPointerIconType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setShowAllTouches(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void updateDeviceToGamepadProfile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeDeviceToGamepadProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllDeviceToGamepadProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllGamepadProfiles() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeGamepadProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setGamepadProfileName(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadButton(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getSupportButtonNStick() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getGamepadProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int[] getGamepadProfileIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void requestPointerCapture(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(68, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputMonitor monitorGestureInput(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMonitor) parcelObtain2.readTypedObject(InputMonitor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputMonitor monitorGestureInputFiltered(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMonitor) parcelObtain2.readTypedObject(InputMonitor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputChannel monitorInputForBinder(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputChannel) parcelObtain2.readTypedObject(InputChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean setTspEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getInboundQueueLength() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int checkInputFeature() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setStartedShutdown(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getScanCodeState(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setWakeKeyDynamically(String str, boolean z, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int getGlobalMetaState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public long semGetMotionIdleTimeMillis(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean isUidTouched(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void forceFadeIcon(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void notifyQuickAccess(int i, float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerWirelessKeyboardShareChangedListener(IWirelessKeyboardShareChangedListener iWirelessKeyboardShareChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWirelessKeyboardShareChangedListener);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void updateWirelessKeyboardShareStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void changeDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean addDeviceWirelessKeyboardShare(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean switchDeviceWirelessKeyboardShare(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setHostRoleWirelessKeyboardShare() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void connectByBtDevice(BluetoothDevice bluetoothDevice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bluetoothDevice, 0);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addPortAssociation(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removePortAssociation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByDescriptor(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByDescriptor(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void addUniqueIdAssociationByPort(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeUniqueIdAssociationByPort(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public InputSensorInfo[] getSensorList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputSensorInfo[]) parcelObtain2.createTypedArray(InputSensorInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputSensorEventListener);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean enableSensor(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void disableSensor(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean flushSensor(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public List<Light> getLights(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Light.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public LightState getLightState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LightState) parcelObtain2.readTypedObject(LightState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void setLightStates(int i, int[] iArr, LightState[] lightStateArr, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedArray(lightStateArr, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void openLightSession(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void closeLightSession(int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void cancelCurrentTouch() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterBatteryListener(int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iInputDeviceBatteryListener);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean registerKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyEventActivityListener);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public boolean unregisterKeyEventActivityListener(IKeyEventActivityListener iKeyEventActivityListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyEventActivityListener);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public String getInputDeviceBluetoothAddress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void pilferPointers(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyboardBacklightListener(IKeyboardBacklightListener iKeyboardBacklightListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyboardBacklightListener);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HostUsiVersion) parcelObtain2.readTypedObject(HostUsiVersion.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterStickyModifierStateListener(IStickyModifierStateListener iStickyModifierStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStickyModifierStateListener);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public KeyGlyphMap getKeyGlyphMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyGlyphMap) parcelObtain2.readTypedObject(KeyGlyphMap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyGestureEventListener);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyGestureEventListener(IKeyGestureEventListener iKeyGestureEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyGestureEventListener);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void registerKeyGestureHandler(int[] iArr, IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStrongInterface(iKeyGestureHandler);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void unregisterKeyGestureHandler(IKeyGestureHandler iKeyGestureHandler) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyGestureHandler);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData getInputGesture(int i, AidlInputGestureData.Trigger trigger) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(trigger, 0);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AidlInputGestureData) parcelObtain2.readTypedObject(AidlInputGestureData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int addCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(aidlInputGestureData, 0);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public int removeCustomInputGesture(int i, AidlInputGestureData aidlInputGestureData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(aidlInputGestureData, 0);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void removeAllCustomInputGestures(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData[] getCustomInputGestures(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AidlInputGestureData[]) parcelObtain2.createTypedArray(AidlInputGestureData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public AidlInputGestureData[] getAppLaunchBookmarks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AidlInputGestureData[]) parcelObtain2.createTypedArray(AidlInputGestureData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputManager
            public void resetLockedModifierState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
