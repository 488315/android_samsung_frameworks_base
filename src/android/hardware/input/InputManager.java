package android.hardware.input;

import android.app.ActivityThread;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.BatteryState;
import android.hardware.input.InputGestureData;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Vibrator;
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
import com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class InputManager {
    public static final String ACTION_QUERY_KEYBOARD_GLYPH_MAPS = "android.hardware.input.action.QUERY_KEYBOARD_GLYPH_MAPS";
    public static final String ACTION_QUERY_KEYBOARD_LAYOUTS = "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS";
    public static final long BLOCK_UNTRUSTED_TOUCHES = 158002302;
    public static final int CUSTOM_INPUT_GESTURE_RESULT_ERROR_ALREADY_EXISTS = 2;
    public static final int CUSTOM_INPUT_GESTURE_RESULT_ERROR_DOES_NOT_EXIST = 3;
    public static final int CUSTOM_INPUT_GESTURE_RESULT_ERROR_OTHER = 5;
    public static final int CUSTOM_INPUT_GESTURE_RESULT_ERROR_RESERVED_GESTURE = 4;
    public static final int CUSTOM_INPUT_GESTURE_RESULT_SUCCESS = 1;
    public static final int DESKTOP_WINDOWING_EXTERNAL_DISPLAY_EXTENDED = 2;
    public static final int DESKTOP_WINDOWING_EXTERNAL_DISPLAY_PROJECTED = 1;
    public static final int DESKTOP_WINDOWING_EXTERNAL_DISPLAY_UNDEFINED = 0;
    private static final int EXTRA_SW_POGO_KEYBOARD = 0;
    public static final int EXTRA_SW_POGO_KEYBOARD_BIT = 1;
    public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH = 2;
    public static final int INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT = 1;
    public static final String META_DATA_KEYBOARD_GLYPH_MAPS = "android.hardware.input.metadata.KEYBOARD_GLYPH_MAPS";
    public static final String META_DATA_KEYBOARD_LAYOUTS = "android.hardware.input.metadata.KEYBOARD_LAYOUTS";
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

    @Retention(RetentionPolicy.SOURCE)
    public @interface CustomInputGestureResult {
    }

    public interface InputDeviceBatteryListener {
        void onBatteryStateChanged(int i, long j, BatteryState batteryState);
    }

    public interface InputDeviceListener {
        void onInputDeviceAdded(int i);

        void onInputDeviceChanged(int i);

        void onInputDeviceRemoved(int i);
    }

    public interface KeyEventActivityListener {
        void onKeyEventActivity();
    }

    public interface KeyGestureEventHandler {
        void handleKeyGestureEvent(KeyGestureEvent keyGestureEvent, IBinder iBinder);
    }

    public interface KeyGestureEventListener {
        void onKeyGestureEvent(KeyGestureEvent keyGestureEvent);
    }

    public interface KeyboardBacklightListener {
        void onKeyboardBacklightChanged(int i, KeyboardBacklightState keyboardBacklightState, boolean z);
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

    public interface StickyModifierStateListener {
        void onStickyModifierStateChanged(StickyModifierState stickyModifierState);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SwitchState {
    }

    public void addKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) {
    }

    public boolean areTouchpadGesturesAvailable(Context context) {
        return true;
    }

    public String getCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) {
        return null;
    }

    public long getLastLidEventTimeNanos() {
        return -1L;
    }

    public void removeKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) {
    }

    public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) {
    }

    public InputManager(Context context) {
        InputManagerGlobal inputManagerGlobal = InputManagerGlobal.getInstance();
        this.mGlobal = inputManagerGlobal;
        this.mIm = inputManagerGlobal.getInputManagerService();
        this.mContext = context;
    }

    @Deprecated
    public static InputManager getInstance() {
        return (InputManager) ((Application) Objects.requireNonNull(ActivityThread.currentApplication())).getSystemService(InputManager.class);
    }

    public String getVelocityTrackerStrategy() {
        return this.mGlobal.getVelocityTrackerStrategy();
    }

    public InputDevice getInputDevice(int i) {
        return this.mGlobal.getInputDevice(i);
    }

    public InputDevice.ViewBehavior getInputDeviceViewBehavior(int i) {
        InputDevice inputDevice = getInputDevice(i);
        if (inputDevice == null) {
            return null;
        }
        return inputDevice.getViewBehavior();
    }

    public InputDevice getInputDeviceByDescriptor(String str) {
        return this.mGlobal.getInputDeviceByDescriptor(str);
    }

    public int[] getInputDeviceIds() {
        return this.mGlobal.getInputDeviceIds();
    }

    public void enableInputDevice(int i) {
        this.mGlobal.enableInputDevice(i);
    }

    public void disableInputDevice(int i) {
        this.mGlobal.disableInputDevice(i);
    }

    public void controlSpenWithToken(IBinder iBinder, boolean z) {
        this.mGlobal.controlSpenWithToken(iBinder, z);
    }

    public void registerInputDeviceListener(InputDeviceListener inputDeviceListener, Handler handler) {
        this.mGlobal.registerInputDeviceListener(inputDeviceListener, handler);
    }

    public void unregisterInputDeviceListener(InputDeviceListener inputDeviceListener) {
        this.mGlobal.unregisterInputDeviceListener(inputDeviceListener);
    }

    public void semRegisterOnMultiFingerGestureListener(SemOnMultiFingerGestureListener semOnMultiFingerGestureListener, Handler handler) {
        this.mGlobal.semRegisterOnMultiFingerGestureListener(semOnMultiFingerGestureListener, handler);
    }

    public void semUnregisterOnMultiFingerGestureListener(SemOnMultiFingerGestureListener semOnMultiFingerGestureListener) {
        this.mGlobal.semUnregisterOnMultiFingerGestureListener(semOnMultiFingerGestureListener);
    }

    public void semRegisterOnSwitchEventChangedListener(SemOnSwitchEventChangedListener semOnSwitchEventChangedListener, Handler handler) {
        this.mGlobal.semRegisterOnSwitchEventChangedListener(semOnSwitchEventChangedListener, handler);
    }

    public void semUnregisterOnSwitchEventChangedListener(SemOnSwitchEventChangedListener semOnSwitchEventChangedListener) {
        this.mGlobal.semUnregisterOnSwitchEventChangedListener(semOnSwitchEventChangedListener);
    }

    public int isInTabletMode() {
        try {
            return this.mIm.isInTabletMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int semCheckInputFeature() {
        try {
            return this.mIm.checkInputFeature();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semSupportPogoDevice() {
        try {
            return this.mIm.supportPogoDevice();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getInputDevicePath(int i) {
        try {
            return this.mIm.getInputDevicePath(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerOnTabletModeChangedListener(OnTabletModeChangedListener onTabletModeChangedListener, Handler handler) {
        this.mGlobal.registerOnTabletModeChangedListener(onTabletModeChangedListener, handler);
    }

    public void unregisterOnTabletModeChangedListener(OnTabletModeChangedListener onTabletModeChangedListener) {
        this.mGlobal.unregisterOnTabletModeChangedListener(onTabletModeChangedListener);
    }

    public int isMicMuted() {
        try {
            return this.mIm.isMicMuted();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public KeyboardLayout[] getKeyboardLayouts() {
        try {
            return this.mIm.getKeyboardLayouts();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getKeyboardLayoutDescriptors() {
        KeyboardLayout[] keyboardLayouts = getKeyboardLayouts();
        ArrayList arrayList = new ArrayList();
        for (KeyboardLayout keyboardLayout : keyboardLayouts) {
            arrayList.add(keyboardLayout.getDescriptor());
        }
        return arrayList;
    }

    public String getKeyboardLayoutTypeForLayoutDescriptor(String str) {
        KeyboardLayout keyboardLayout = getKeyboardLayout(str);
        return keyboardLayout == null ? "" : keyboardLayout.getLayoutType();
    }

    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) {
        return new KeyboardLayout[0];
    }

    public KeyboardLayout getKeyboardLayout(String str) {
        if (str == null) {
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            return this.mIm.getKeyboardLayout(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] getEnabledKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) {
        return new String[0];
    }

    public void remapModifierKey(int i, int i2) {
        try {
            this.mIm.remapModifierKey(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAllModifierKeyRemappings() {
        try {
            this.mIm.clearAllModifierKeyRemappings();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<Integer, Integer> getModifierKeyRemapping() {
        try {
            return this.mIm.getModifierKeyRemapping();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TouchCalibration getTouchCalibration(String str, int i) {
        try {
            return this.mIm.getTouchCalibrationForInputDevice(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTouchCalibration(String str, int i, TouchCalibration touchCalibration) {
        try {
            this.mIm.setTouchCalibrationForInputDevice(str, i, touchCalibration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
        try {
            return this.mIm.getKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype, String str) {
        if (inputDeviceIdentifier == null) {
            throw new IllegalArgumentException("identifier must not be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("keyboardLayoutDescriptor must not be null");
        }
        try {
            this.mIm.setKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public KeyboardLayout[] getKeyboardLayoutListForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
        if (inputDeviceIdentifier == null) {
            throw new IllegalArgumentException("inputDeviceDescriptor must not be null");
        }
        try {
            return this.mIm.getKeyboardLayoutListForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMousePointerSpeed() {
        try {
            return this.mIm.getMousePointerSpeed();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void tryPointerSpeed(int i) {
        if (i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        try {
            this.mIm.tryPointerSpeed(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getMaximumObscuringOpacityForTouch() {
        return InputSettings.getMaximumObscuringOpacityForTouch(this.mContext);
    }

    public boolean[] deviceHasKeys(int[] iArr) {
        return deviceHasKeys(-1, iArr);
    }

    public boolean[] deviceHasKeys(int i, int[] iArr) {
        return this.mGlobal.deviceHasKeys(i, iArr);
    }

    public int getKeyCodeForKeyLocation(int i, int i2) {
        return this.mGlobal.getKeyCodeForKeyLocation(i, i2);
    }

    public Drawable getKeyboardLayoutPreview(KeyboardLayout keyboardLayout, int i, int i2) {
        return new KeyboardLayoutPreviewDrawable(this.mContext, new PhysicalKeyLayout(this.mGlobal.getKeyCharacterMap(keyboardLayout), keyboardLayout), i, i2);
    }

    public KeyGlyphMap getKeyGlyphMap(int i) {
        if (!Flags.keyboardGlyphMap()) {
            return null;
        }
        try {
            return this.mIm.getKeyGlyphMap(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(InputEvent inputEvent, int i, int i2) {
        return this.mGlobal.injectInputEvent(inputEvent, i, i2);
    }

    public boolean injectInputEvent(InputEvent inputEvent, int i) {
        return this.mGlobal.injectInputEvent(inputEvent, i);
    }

    public boolean semInjectInputEvent(InputEvent inputEvent, int i) {
        return this.mGlobal.injectInputEvent(inputEvent, i);
    }

    public VerifiedInputEvent verifyInputEvent(InputEvent inputEvent) {
        try {
            return this.mIm.verifyInputEvent(inputEvent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPointerIconType(int i) {
        Log.e(TAG, "setPointerIcon: Unsupported app usage!");
    }

    public boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) {
        return this.mGlobal.setPointerIcon(pointerIcon, i, i2, i3, iBinder);
    }

    public boolean isStylusPointerIconEnabled() {
        if (this.mIsStylusPointerIconEnabled == null) {
            this.mIsStylusPointerIconEnabled = Boolean.valueOf(InputSettings.isStylusPointerIconEnabled(this.mContext));
        }
        return this.mIsStylusPointerIconEnabled.booleanValue();
    }

    public void requestPointerCapture(IBinder iBinder, boolean z) {
        this.mGlobal.requestPointerCapture(iBinder, z);
    }

    @Deprecated
    public InputMonitor monitorGestureInput(String str, int i) {
        return this.mGlobal.monitorGestureInput(str, i, 65535);
    }

    public InputMonitor monitorGestureInput(String str, int i, int i2) {
        return this.mGlobal.monitorGestureInput(str, i, i2);
    }

    public InputChannel monitorInput(String str, int i) {
        return monitorInput(str, i, 65535);
    }

    public InputChannel monitorInput(String str, int i, int i2) {
        return this.mGlobal.monitorInput(str, i, i2);
    }

    public void addPortAssociation(String str, int i) {
        try {
            this.mIm.addPortAssociation(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePortAssociation(String str) {
        try {
            this.mIm.removePortAssociation(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUniqueIdAssociationByPort(String str, String str2) {
        this.mGlobal.addUniqueIdAssociationByPort(str, str2);
    }

    public void removeUniqueIdAssociationByPort(String str) {
        this.mGlobal.removeUniqueIdAssociationByPort(str);
    }

    public void addUniqueIdAssociationByDescriptor(String str, String str2) {
        this.mGlobal.addUniqueIdAssociationByDescriptor(str, str2);
    }

    public void removeUniqueIdAssociationByDescriptor(String str) {
        this.mGlobal.removeUniqueIdAssociationByDescriptor(str);
    }

    public HostUsiVersion getHostUsiVersion(Display display) {
        return this.mGlobal.getHostUsiVersion(display);
    }

    public String getInputDeviceBluetoothAddress(int i) {
        return this.mGlobal.getInputDeviceBluetoothAddress(i);
    }

    public Vibrator getInputDeviceVibrator(int i, int i2) {
        return new InputDeviceVibrator(i, i2);
    }

    public void cancelCurrentTouch() {
        this.mGlobal.cancelCurrentTouch();
    }

    public void pilferPointers(IBinder iBinder) {
        this.mGlobal.pilferPointers(iBinder);
    }

    public void addInputDeviceBatteryListener(int i, Executor executor, InputDeviceBatteryListener inputDeviceBatteryListener) {
        this.mGlobal.addInputDeviceBatteryListener(i, executor, inputDeviceBatteryListener);
    }

    public void removeInputDeviceBatteryListener(int i, InputDeviceBatteryListener inputDeviceBatteryListener) {
        this.mGlobal.removeInputDeviceBatteryListener(i, inputDeviceBatteryListener);
    }

    public void registerKeyboardBacklightListener(Executor executor, KeyboardBacklightListener keyboardBacklightListener) throws IllegalArgumentException {
        this.mGlobal.registerKeyboardBacklightListener(executor, keyboardBacklightListener);
    }

    public void unregisterKeyboardBacklightListener(KeyboardBacklightListener keyboardBacklightListener) {
        this.mGlobal.unregisterKeyboardBacklightListener(keyboardBacklightListener);
    }

    public void registerStickyModifierStateListener(Executor executor, StickyModifierStateListener stickyModifierStateListener) throws IllegalArgumentException {
        this.mGlobal.registerStickyModifierStateListener(executor, stickyModifierStateListener);
    }

    public void unregisterStickyModifierStateListener(StickyModifierStateListener stickyModifierStateListener) {
        this.mGlobal.unregisterStickyModifierStateListener(stickyModifierStateListener);
    }

    public void registerKeyGestureEventListener(Executor executor, KeyGestureEventListener keyGestureEventListener) throws IllegalArgumentException {
        this.mGlobal.registerKeyGestureEventListener(executor, keyGestureEventListener);
    }

    public void unregisterKeyGestureEventListener(KeyGestureEventListener keyGestureEventListener) {
        this.mGlobal.unregisterKeyGestureEventListener(keyGestureEventListener);
    }

    public void registerKeyGestureEventHandler(List<Integer> list, KeyGestureEventHandler keyGestureEventHandler) throws IllegalArgumentException {
        this.mGlobal.registerKeyGestureEventHandler(list, keyGestureEventHandler);
    }

    public void unregisterKeyGestureEventHandler(KeyGestureEventHandler keyGestureEventHandler) {
        this.mGlobal.unregisterKeyGestureEventHandler(keyGestureEventHandler);
    }

    public InputGestureData getInputGesture(InputGestureData.Trigger trigger) {
        try {
            AidlInputGestureData inputGesture = this.mIm.getInputGesture(this.mContext.getUserId(), trigger.getAidlTrigger());
            if (inputGesture == null) {
                return null;
            }
            return new InputGestureData(inputGesture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int addCustomInputGesture(InputGestureData inputGestureData) {
        if (!Flags.enableCustomizableInputGestures()) {
            return 5;
        }
        try {
            return this.mIm.addCustomInputGesture(this.mContext.getUserId(), inputGestureData.getAidlData());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int removeCustomInputGesture(InputGestureData inputGestureData) {
        if (!Flags.enableCustomizableInputGestures()) {
            return 5;
        }
        try {
            return this.mIm.removeCustomInputGesture(this.mContext.getUserId(), inputGestureData.getAidlData());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllCustomInputGestures(InputGestureData.Filter filter) {
        if (Flags.enableCustomizableInputGestures()) {
            try {
                this.mIm.removeAllCustomInputGestures(this.mContext.getUserId(), filter == null ? -1 : filter.getTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<InputGestureData> getCustomInputGestures(InputGestureData.Filter filter) {
        ArrayList arrayList = new ArrayList();
        if (Flags.enableCustomizableInputGestures()) {
            try {
                for (AidlInputGestureData aidlInputGestureData : this.mIm.getCustomInputGestures(this.mContext.getUserId(), filter == null ? -1 : filter.getTag())) {
                    arrayList.add(new InputGestureData(aidlInputGestureData));
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return arrayList;
    }

    public List<InputGestureData> getAppLaunchBookmarks() {
        try {
            ArrayList arrayList = new ArrayList();
            for (AidlInputGestureData aidlInputGestureData : this.mIm.getAppLaunchBookmarks()) {
                arrayList.add(new InputGestureData(aidlInputGestureData));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetLockedModifierState() {
        try {
            this.mIm.resetLockedModifierState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean registerKeyEventActivityListener(KeyEventActivityListener keyEventActivityListener) {
        return this.mGlobal.registerKeyEventActivityListener(keyEventActivityListener);
    }

    public boolean unregisterKeyEventActivityListener(KeyEventActivityListener keyEventActivityListener) {
        return this.mGlobal.unregisterKeyEventActivityListener(keyEventActivityListener);
    }

    public int getCurrentSwitchEventState(int i, boolean z) {
        try {
            return this.mIm.getCurrentSwitchEventState(i, z);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public void setStartedShutdown(boolean z) {
        try {
            this.mIm.setStartedShutdown(z);
        } catch (RemoteException unused) {
        }
    }

    public boolean isUidTouched(int i) {
        try {
            return this.mIm.isUidTouched(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public int semGetScanCodeState(int i, int i2, int i3) {
        try {
            return this.mIm.getScanCodeState(i, i2, i3);
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public int getInboundQueueLength() {
        try {
            return this.mIm.getInboundQueueLength();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public int getGlobalMetaState(int i) {
        try {
            return this.mIm.getGlobalMetaState(i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call getGlobalMetaState()");
            return 0;
        }
    }

    public void forceFadeIcon(int i) {
        try {
            this.mIm.forceFadeIcon(i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call forceFadeIcon()");
        }
    }

    public void updateWirelessKeyboardShareStatus() {
        try {
            this.mIm.updateWirelessKeyboardShareStatus();
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call updateWirelessKeyboardShareStatus()");
        }
    }

    public void removeDeviceWirelessKeyboardShare(String str, int i) {
        try {
            this.mIm.removeDeviceWirelessKeyboardShare(str, i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call removeDeviceWirelessKeyboardShare()");
        }
    }

    public void changeDeviceWirelessKeyboardShare(String str, int i) {
        try {
            this.mIm.changeDeviceWirelessKeyboardShare(str, i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call changeDeviceWirelessKeyboardShare()");
        }
    }

    public boolean addDeviceWirelessKeyboardShare(int i) {
        try {
            return this.mIm.addDeviceWirelessKeyboardShare(i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call addDeviceWirelessKeyboardShare()");
            return true;
        }
    }

    public boolean switchDeviceWirelessKeyboardShare(String str, int i) {
        try {
            return this.mIm.switchDeviceWirelessKeyboardShare(str, i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call switchDeviceWirelessKeyboardShare()");
            return true;
        }
    }

    public void setHostRoleWirelessKeyboardShare() {
        try {
            this.mIm.setHostRoleWirelessKeyboardShare();
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call setHostRoleWirelessKeyboardShare()");
        }
    }

    public void connectByBtDevice(BluetoothDevice bluetoothDevice) {
        try {
            this.mIm.connectByBtDevice(bluetoothDevice);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call connectByBtDevice()");
        }
    }

    public void setShowAllTouches(boolean z) {
        try {
            this.mIm.setShowAllTouches(z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call setShowAllTouches(boolean)");
        }
    }

    public void updateDeviceToGamepadProfile(String str, int i) {
        this.mGlobal.updateDeviceToGamepadProfile(str, i);
    }

    public void removeDeviceToGamepadProfile(String str) {
        this.mGlobal.removeDeviceToGamepadProfile(str);
    }

    public void removeAllDeviceToGamepadProfile() {
        this.mGlobal.removeAllDeviceToGamepadProfile();
    }

    public void removeAllGamepadProfiles() {
        this.mGlobal.removeAllGamepadProfiles();
    }

    public void removeGamepadProfile(int i) {
        this.mGlobal.removeGamepadProfile(i);
    }

    public boolean setGamepadProfileName(int i, String str) {
        return this.mGlobal.setGamepadProfileName(i, str);
    }

    public boolean setRemapGamepadButton(int i, int i2, int i3) {
        return this.mGlobal.setRemapGamepadButton(i, i2, i3);
    }

    public boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        return this.mGlobal.setRemapGamepadStick(i, i2, i3, z, z2, z3);
    }

    public String getSupportButtonNStick() {
        return this.mGlobal.getSupportButtonNStick();
    }

    public String getGamepadProfile(int i) {
        return this.mGlobal.getGamepadProfile(i);
    }

    public int[] getGamepadProfileIds() {
        return this.mGlobal.getGamepadProfileIds();
    }

    public long semGetMotionIdleTimeMillis() {
        try {
            return this.mIm.semGetMotionIdleTimeMillis(false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetWakeKeyDynamically(String str, boolean z, String str2) {
        this.mGlobal.semSetWakeKeyDynamically(str, z, str2);
    }

    public int semGetLidState() {
        return this.mGlobal.semGetLidState();
    }

    public void semRegisterOnLidStateChangedListener(SemOnLidStateChangedListener semOnLidStateChangedListener, Handler handler) {
        this.mGlobal.semRegisterOnLidStateChangedListener(semOnLidStateChangedListener, handler);
    }

    public void semUnregisterOnLidStateChangedListener(SemOnLidStateChangedListener semOnLidStateChangedListener) {
        this.mGlobal.semUnregisterOnLidStateChangedListener(semOnLidStateChangedListener);
    }

    public boolean semSetTspEnabled(SemTspCommandType semTspCommandType, boolean z) {
        try {
            return this.mIm.setTspEnabled(semTspCommandType.getvalue(), z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Could not call setTspEnabled()");
            return false;
        } catch (NullPointerException unused2) {
            Log.w(TAG, "SemTspCommandType should not be null");
            return false;
        }
    }

    public enum SemTspCommandType {
        EMPTY(0),
        SPAY(1),
        STYLUS(2),
        BRUSH(3);

        private int mValue;

        SemTspCommandType(int i) {
            this.mValue = i;
        }

        public int getvalue() {
            return this.mValue;
        }
    }

    public void setIsStylusFromTouchpad(boolean z) {
        this.mGlobal.setIsStylusFromTouchpad(z);
    }

    public void semRegisterOnPointerIconChangedListener(SemOnPointerIconChangedListener semOnPointerIconChangedListener, Handler handler) {
        this.mGlobal.semRegisterOnPointerIconChangedListener(semOnPointerIconChangedListener, handler);
    }

    public void semUnregisterOnPointerIconChangedListener(SemOnPointerIconChangedListener semOnPointerIconChangedListener) {
        this.mGlobal.semUnregisterOnPointerIconChangedListener(semOnPointerIconChangedListener);
    }

    private int findOnPointerIconChangedListenerLocked(SemOnPointerIconChangedListener semOnPointerIconChangedListener) {
        return this.mGlobal.findOnPointerIconChangedListenerLocked(semOnPointerIconChangedListener);
    }
}
