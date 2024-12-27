package android.hardware.input;

import android.hardware.BatteryState;
import android.hardware.SensorManager;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.hardware.lights.LightsManager;
import android.hardware.lights.LightsRequest;
import android.os.Binder;
import android.os.CombinedVibration;
import android.os.Handler;
import android.os.IBinder;
import android.os.IVibratorStateListener;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.KeyCharacterMap;
import android.view.PointerIcon;
import android.window.TaskConstants;

import com.android.internal.os.SomeArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class InputManagerGlobal {
    private static final int CONVERSION_TYPE_SPEN_TO_MOUSE = 10100;
    private static final int MSG_MULTI_FINGER_GESTURE = 1;
    private static final int MSG_POINTERICON_CHANGED = 1;
    private static InputManagerGlobal sInstance;
    private SparseArray<RegisteredBatteryListeners> mBatteryListeners;
    private int mDeviceId;
    private PointerIcon mDragPointerIcon;
    private IBinder mDragToken;
    private final IInputManager mIm;
    private IInputDeviceBatteryListener mInputDeviceBatteryListener;
    private InputDeviceSensorManager mInputDeviceSensorManager;
    private SparseArray<InputDevice> mInputDevices;
    private InputDevicesChangedListener mInputDevicesChangedListener;
    private boolean mIsStylusFromTouchpad;
    private IKeyboardBacklightListener mKeyboardBacklightListener;
    private ArrayList<KeyboardBacklightListenerDelegate> mKeyboardBacklightListeners;
    private LidStateChangedListener mLidStateChangedListener;
    private MultiFingerGestureListener mMultiFingerGestureListener;
    private List<OnMultiFingerGestureListenerDelegate> mOnMultiFingerGestureListeners;
    private List<OnPointerIconChangedListenerDelegate> mOnPointerIconChangedListeners;
    private List<OnSwitchEventChangedListenerDelegate> mOnSwitchEventChangedListeners;
    private PointerIcon mPointerIcon;
    private PointerIconChangedListener mPointerIconChangedListener;
    private int mPointerIconType;
    private int mPointerId;
    private List<SemOnLidStateChangedListenerDelegate> mSemOnLidStateChangedListeners;
    private IStickyModifierStateListener mStickyModifierStateListener;
    private ArrayList<StickyModifierStateListenerDelegate> mStickyModifierStateListeners;
    private SwitchEventChangedListener mSwitchEventChangedListener;
    private final String mVelocityTrackerStrategy;
    private WirelessKeyboardShareChangedListener mWirelessKeyboardShareChangedListener;
    private static final String TAG = "InputManagerGlobal";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final ArrayList<InputDeviceListenerDelegate> mInputDeviceListeners = new ArrayList<>();
    private final ArrayList<OnTabletModeChangedListenerDelegate> mOnTabletModeChangedListeners =
            new ArrayList<>();
    private final Object mLidStateLock = new Object();
    private final Object mMultiFingerGestureLock = new Object();
    private final Object mSwitchEventChangedLock = new Object();
    private final Object mBatteryListenersLock = new Object();
    private final Object mKeyboardBacklightListenerLock = new Object();
    private final Object mWirelessKeyboardShareLock = new Object();
    private List<OnWirelessKeyboardShareChangedListenerDelegate>
            mOnWirelessKeyboardShareChangedListeners = new ArrayList();
    private final Object mStickyModifierStateListenerLock = new Object();
    private final Object mPointerIconLock = new Object();

    public interface OnWirelessKeyboardShareChangedListener {
        void onWirelessKeyboardShareChanged(long j, int i, String str);
    }

    public interface TestSession extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();
    }

    public InputManagerGlobal(IInputManager im) {
        this.mIm = im;
        String strategy = null;
        try {
            strategy = this.mIm.getVelocityTrackerStrategy();
        } catch (RemoteException ex) {
            Log.w(TAG, "Could not get VelocityTracker strategy: " + ex);
        }
        this.mVelocityTrackerStrategy = strategy;
    }

    public static InputManagerGlobal getInstance() {
        InputManagerGlobal inputManagerGlobal;
        IBinder b;
        synchronized (InputManagerGlobal.class) {
            if (sInstance == null && (b = ServiceManager.getService("input")) != null) {
                sInstance = new InputManagerGlobal(IInputManager.Stub.asInterface(b));
            }
            inputManagerGlobal = sInstance;
        }
        return inputManagerGlobal;
    }

    public IInputManager getInputManagerService() {
        return this.mIm;
    }

    public static TestSession createTestSession(IInputManager inputManagerService) {
        TestSession testSession;
        synchronized (InputManagerGlobal.class) {
            final InputManagerGlobal oldInstance = sInstance;
            sInstance = new InputManagerGlobal(inputManagerService);
            testSession =
                    new TestSession() { // from class:
                        // android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda0
                        @Override // android.hardware.input.InputManagerGlobal.TestSession,
                        // java.lang.AutoCloseable
                        public final void close() {
                            InputManagerGlobal.sInstance = InputManagerGlobal.this;
                        }
                    };
        }
        return testSession;
    }

    public String getVelocityTrackerStrategy() {
        return this.mVelocityTrackerStrategy;
    }

    public InputDevice getInputDevice(int id) {
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int index = this.mInputDevices.indexOfKey(id);
            if (index < 0) {
                return null;
            }
            InputDevice inputDevice = this.mInputDevices.valueAt(index);
            if (inputDevice == null) {
                try {
                    inputDevice = this.mIm.getInputDevice(id);
                    if (inputDevice != null) {
                        this.mInputDevices.setValueAt(index, inputDevice);
                    }
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            }
            return inputDevice;
        }
    }

    private void populateInputDevicesLocked() {
        if (this.mInputDevicesChangedListener == null) {
            InputDevicesChangedListener listener = new InputDevicesChangedListener();
            try {
                this.mIm.registerInputDevicesChangedListener(listener);
                this.mInputDevicesChangedListener = listener;
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
        if (this.mInputDevices == null) {
            try {
                int[] ids = this.mIm.getInputDeviceIds();
                this.mInputDevices = new SparseArray<>();
                for (int id : ids) {
                    this.mInputDevices.put(id, null);
                }
            } catch (RemoteException ex2) {
                throw ex2.rethrowFromSystemServer();
            }
        }
    }

    private final class InputDevicesChangedListener extends IInputDevicesChangedListener.Stub {
        private InputDevicesChangedListener() {}

        @Override // android.hardware.input.IInputDevicesChangedListener
        public void onInputDevicesChanged(int[] deviceIdAndGeneration) throws RemoteException {
            InputManagerGlobal.this.onInputDevicesChanged(deviceIdAndGeneration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputDevicesChanged(int[] deviceIdAndGeneration) {
        if (DEBUG) {
            Log.d(TAG, "Received input devices changed.");
        }
        synchronized (this.mInputDeviceListeners) {
            int i = this.mInputDevices.size();
            while (true) {
                i--;
                if (i <= 0) {
                    break;
                }
                int deviceId = this.mInputDevices.keyAt(i);
                if (!containsDeviceId(deviceIdAndGeneration, deviceId)) {
                    if (DEBUG) {
                        Log.d(TAG, "Device removed: " + deviceId);
                    }
                    this.mInputDevices.removeAt(i);
                    if (this.mInputDeviceSensorManager != null) {
                        this.mInputDeviceSensorManager.onInputDeviceRemoved(deviceId);
                    }
                    sendMessageToInputDeviceListenersLocked(2, deviceId);
                }
            }
            for (int i2 = 0; i2 < deviceIdAndGeneration.length; i2 += 2) {
                int deviceId2 = deviceIdAndGeneration[i2];
                int index = this.mInputDevices.indexOfKey(deviceId2);
                if (index >= 0) {
                    InputDevice device = this.mInputDevices.valueAt(index);
                    if (device != null) {
                        int generation = deviceIdAndGeneration[i2 + 1];
                        if (device.getGeneration() != generation) {
                            if (DEBUG) {
                                Log.d(TAG, "Device changed: " + deviceId2);
                            }
                            this.mInputDevices.setValueAt(index, null);
                            if (this.mInputDeviceSensorManager != null) {
                                this.mInputDeviceSensorManager.onInputDeviceChanged(deviceId2);
                            }
                            sendMessageToInputDeviceListenersLocked(3, deviceId2);
                        }
                    }
                } else {
                    if (DEBUG) {
                        Log.d(TAG, "Device added: " + deviceId2);
                    }
                    this.mInputDevices.put(deviceId2, null);
                    if (this.mInputDeviceSensorManager != null) {
                        this.mInputDeviceSensorManager.onInputDeviceAdded(deviceId2);
                    }
                    sendMessageToInputDeviceListenersLocked(1, deviceId2);
                }
            }
        }
    }

    private static final class InputDeviceListenerDelegate extends Handler {
        static final int MSG_DEVICE_ADDED = 1;
        static final int MSG_DEVICE_CHANGED = 3;
        static final int MSG_DEVICE_REMOVED = 2;
        public final InputManager.InputDeviceListener mListener;

        InputDeviceListenerDelegate(InputManager.InputDeviceListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.mListener.onInputDeviceAdded(msg.arg1);
                    break;
                case 2:
                    this.mListener.onInputDeviceRemoved(msg.arg1);
                    break;
                case 3:
                    this.mListener.onInputDeviceChanged(msg.arg1);
                    break;
            }
        }
    }

    private static boolean containsDeviceId(int[] deviceIdAndGeneration, int deviceId) {
        for (int i = 0; i < deviceIdAndGeneration.length; i += 2) {
            if (deviceIdAndGeneration[i] == deviceId) {
                return true;
            }
        }
        return false;
    }

    private void sendMessageToInputDeviceListenersLocked(int what, int deviceId) {
        int numListeners = this.mInputDeviceListeners.size();
        for (int i = 0; i < numListeners; i++) {
            InputDeviceListenerDelegate listener = this.mInputDeviceListeners.get(i);
            listener.sendMessage(listener.obtainMessage(what, deviceId, 0));
        }
    }

    public void registerInputDeviceListener(
            InputManager.InputDeviceListener listener, Handler handler) {
        Objects.requireNonNull(listener, "listener must not be null");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int index = findInputDeviceListenerLocked(listener);
            if (index < 0) {
                this.mInputDeviceListeners.add(new InputDeviceListenerDelegate(listener, handler));
            }
        }
    }

    public void unregisterInputDeviceListener(InputManager.InputDeviceListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputDeviceListeners) {
            int index = findInputDeviceListenerLocked(listener);
            if (index >= 0) {
                InputDeviceListenerDelegate d = this.mInputDeviceListeners.get(index);
                d.removeCallbacksAndMessages(null);
                this.mInputDeviceListeners.remove(index);
            }
        }
    }

    private int findInputDeviceListenerLocked(InputManager.InputDeviceListener listener) {
        int numListeners = this.mInputDeviceListeners.size();
        for (int i = 0; i < numListeners; i++) {
            if (this.mInputDeviceListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    public void semRegisterOnMultiFingerGestureListener(
            InputManager.SemOnMultiFingerGestureListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mMultiFingerGestureLock) {
            if (this.mMultiFingerGestureListener == null) {
                initializeMultiFingerGestureListenerLocked();
            }
            int index = findOnMultiFingerGestureListenerLocked(listener);
            if (index < 0) {
                this.mOnMultiFingerGestureListeners.add(
                        new OnMultiFingerGestureListenerDelegate(listener, handler));
            }
        }
    }

    public void semUnregisterOnMultiFingerGestureListener(
            InputManager.SemOnMultiFingerGestureListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mMultiFingerGestureLock) {
            int index = findOnMultiFingerGestureListenerLocked(listener);
            if (index >= 0) {
                OnMultiFingerGestureListenerDelegate d =
                        this.mOnMultiFingerGestureListeners.get(index);
                d.removeCallbacksAndMessages(null);
                this.mOnMultiFingerGestureListeners.remove(index);
            }
        }
    }

    public void notifyQuickAccess(int info, float x, float y) {
        try {
            this.mIm.notifyQuickAccess(info, x, y);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnSwitchEventChangedListener(
            InputManager.SemOnSwitchEventChangedListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mSwitchEventChangedLock) {
            if (this.mSwitchEventChangedListener == null) {
                initializeSwitchEventChangedListenerLocked();
            }
            int index = findOnSwitchEventChangedListenerLocked(listener);
            if (index < 0) {
                this.mOnSwitchEventChangedListeners.add(
                        new OnSwitchEventChangedListenerDelegate(listener, handler));
            }
        }
    }

    public void semUnregisterOnSwitchEventChangedListener(
            InputManager.SemOnSwitchEventChangedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mSwitchEventChangedLock) {
            int index = findOnSwitchEventChangedListenerLocked(listener);
            if (index >= 0) {
                OnSwitchEventChangedListenerDelegate d =
                        this.mOnSwitchEventChangedListeners.get(index);
                d.removeCallbacksAndMessages(null);
                this.mOnSwitchEventChangedListeners.remove(index);
            }
        }
    }

    public void updateDeviceToGamepadProfile(String btDevice, int id) {
        try {
            this.mIm.updateDeviceToGamepadProfile(btDevice, id);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void removeDeviceToGamepadProfile(String btDevice) {
        try {
            this.mIm.removeDeviceToGamepadProfile(btDevice);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void removeAllDeviceToGamepadProfile() {
        try {
            this.mIm.removeAllDeviceToGamepadProfile();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void removeAllGamepadProfiles() {
        try {
            this.mIm.removeAllGamepadProfiles();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void removeGamepadProfile(int id) {
        try {
            this.mIm.removeGamepadProfile(id);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean setGamepadProfileName(int id, String name) {
        try {
            return this.mIm.setGamepadProfileName(id, name);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean setRemapGamepadButton(int id, int fromButton, int toButton) {
        try {
            return this.mIm.setRemapGamepadButton(id, fromButton, toButton);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean setRemapGamepadStick(
            int id,
            int fromStick,
            int toStick,
            boolean inverseH,
            boolean inverseV,
            boolean inverseRot) {
        try {
            return this.mIm.setRemapGamepadStick(
                    id, fromStick, toStick, inverseH, inverseV, inverseRot);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public String getSupportButtonNStick() {
        try {
            return this.mIm.getSupportButtonNStick();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public String getGamepadProfile(int id) {
        try {
            return this.mIm.getGamepadProfile(id);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public int[] getGamepadProfileIds() {
        try {
            return this.mIm.getGamepadProfileIds();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private void initializeSwitchEventChangedListenerLocked() {
        SwitchEventChangedListener listener = new SwitchEventChangedListener();
        try {
            this.mIm.registerSwitchEventChangedListener(listener);
            this.mSwitchEventChangedListener = listener;
            this.mOnSwitchEventChangedListeners = new ArrayList();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private int findOnSwitchEventChangedListenerLocked(
            InputManager.SemOnSwitchEventChangedListener listener) {
        int N = this.mOnSwitchEventChangedListeners.size();
        for (int i = 0; i < N; i++) {
            if (this.mOnSwitchEventChangedListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchEventChanged(
            int switchValues, int switchMask, int extraValues, int extraMask) {
        if (DEBUG) {
            Log.d(TAG, "switch event change");
        }
        synchronized (this.mSwitchEventChangedLock) {
            int numListeners = this.mOnSwitchEventChangedListeners.size();
            for (int i = 0; i < numListeners; i++) {
                OnSwitchEventChangedListenerDelegate listener =
                        this.mOnSwitchEventChangedListeners.get(i);
                listener.sendSwitchEventChanged(switchValues, switchMask, extraValues, extraMask);
            }
        }
    }

    private final class SwitchEventChangedListener extends ISwitchEventChangedListener.Stub {
        private SwitchEventChangedListener() {}

        @Override // android.hardware.input.ISwitchEventChangedListener
        public void onSwitchEventChanged(
                int switchValues, int switchMask, int extraValues, int extraMask) {
            InputManagerGlobal.this.onSwitchEventChanged(
                    switchValues, switchMask, extraValues, extraMask);
        }
    }

    private static final class OnSwitchEventChangedListenerDelegate extends Handler {
        private static final int MSG_SWITCH_EVENT_CHANGED = 0;
        public final InputManager.SemOnSwitchEventChangedListener mListener;

        public OnSwitchEventChangedListenerDelegate(
                InputManager.SemOnSwitchEventChangedListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        public void sendSwitchEventChanged(
                int switchValues, int switchMask, int extraValues, int extraMask) {
            SomeArgs args = SomeArgs.obtain();
            args.argi1 = switchValues;
            args.argi2 = switchMask;
            args.argi3 = extraValues;
            args.argi4 = extraMask;
            obtainMessage(0, args).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                SomeArgs args = (SomeArgs) msg.obj;
                int switchValues = args.argi1;
                int switchMask = args.argi2;
                int extraValues = args.argi3;
                int extraMask = args.argi4;
                this.mListener.onSwitchEventChanged(
                        switchValues, switchMask, extraValues, extraMask);
            }
        }
    }

    private void initializeMultiFingerGestureListenerLocked() {
        MultiFingerGestureListener listener = new MultiFingerGestureListener();
        try {
            this.mIm.registerMultiFingerGestureListener(listener);
            this.mMultiFingerGestureListener = listener;
            this.mOnMultiFingerGestureListeners = new ArrayList();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private int findOnMultiFingerGestureListenerLocked(
            InputManager.SemOnMultiFingerGestureListener listener) {
        if (this.mOnMultiFingerGestureListeners == null) {
            return -1;
        }
        int N = this.mOnMultiFingerGestureListeners.size();
        for (int i = 0; i < N; i++) {
            if (this.mOnMultiFingerGestureListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMultiFingerGesture(int behavior, int reserved) {
        if (DEBUG) {
            Log.d(TAG, "multi finger gesture.");
        }
        synchronized (this.mMultiFingerGestureLock) {
            int numListeners = this.mOnMultiFingerGestureListeners.size();
            for (int i = 0; i < numListeners; i++) {
                OnMultiFingerGestureListenerDelegate listener =
                        this.mOnMultiFingerGestureListeners.get(i);
                listener.sendMessage(listener.obtainMessage(1, behavior, reserved));
            }
        }
    }

    private final class MultiFingerGestureListener extends IMultiFingerGestureListener.Stub {
        private MultiFingerGestureListener() {}

        @Override // android.hardware.input.IMultiFingerGestureListener
        public void onMultiFingerGesture(int behavior, int reserved) {
            InputManagerGlobal.this.onMultiFingerGesture(behavior, reserved);
        }
    }

    private static final class OnMultiFingerGestureListenerDelegate extends Handler {
        public final InputManager.SemOnMultiFingerGestureListener mListener;

        public OnMultiFingerGestureListenerDelegate(
                InputManager.SemOnMultiFingerGestureListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int behavior = msg.arg1;
                    int reserved = msg.arg2;
                    this.mListener.onMultiFingerGesture(behavior, reserved);
                    break;
            }
        }
    }

    public void semSetWakeKeyDynamically(String packageName, boolean isPut, String keyCodes) {
        try {
            this.mIm.setWakeKeyDynamically(packageName, isPut, keyCodes);
        } catch (RemoteException e) {
        }
    }

    public int semGetLidState() {
        try {
            return this.mIm.getLidState();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnLidStateChangedListener(
            InputManager.SemOnLidStateChangedListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mLidStateLock) {
            if (this.mSemOnLidStateChangedListeners == null) {
                initializeLidStateListenerLocked();
            }
            int idx = findSemOnLidStateChangedListenerLocked(listener);
            if (idx < 0) {
                SemOnLidStateChangedListenerDelegate d =
                        new SemOnLidStateChangedListenerDelegate(listener, handler);
                this.mSemOnLidStateChangedListeners.add(d);
            }
        }
    }

    public void semUnregisterOnLidStateChangedListener(
            InputManager.SemOnLidStateChangedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mLidStateLock) {
            int idx = findSemOnLidStateChangedListenerLocked(listener);
            if (idx >= 0) {
                SemOnLidStateChangedListenerDelegate d =
                        this.mSemOnLidStateChangedListeners.remove(idx);
                d.removeCallbacksAndMessages(null);
            }
        }
    }

    public int[] getInputDeviceIds() {
        int[] ids;
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int count = this.mInputDevices.size();
            ids = new int[count];
            for (int i = 0; i < count; i++) {
                ids[i] = this.mInputDevices.keyAt(i);
            }
        }
        return ids;
    }

    public void enableInputDevice(int id) {
        try {
            this.mIm.enableInputDevice(id);
        } catch (RemoteException ex) {
            Log.w(TAG, "Could not enable input device with id = " + id);
            throw ex.rethrowFromSystemServer();
        }
    }

    public void disableInputDevice(int id) {
        try {
            this.mIm.disableInputDevice(id);
        } catch (RemoteException ex) {
            Log.w(TAG, "Could not disable input device with id = " + id);
            throw ex.rethrowFromSystemServer();
        }
    }

    public void controlSpenWithToken(IBinder token, boolean enable) {
        try {
            this.mIm.controlSpenWithToken(token, enable);
        } catch (RemoteException ex) {
            Log.w(TAG, "Could not control sec_e-pen device with token = " + token + " " + enable);
            throw ex.rethrowFromSystemServer();
        }
    }

    InputDevice getInputDeviceByDescriptor(String descriptor) {
        Objects.requireNonNull(descriptor, "descriptor must not be null.");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int numDevices = this.mInputDevices.size();
            for (int i = 0; i < numDevices; i++) {
                InputDevice inputDevice = this.mInputDevices.valueAt(i);
                if (inputDevice == null) {
                    int id = this.mInputDevices.keyAt(i);
                    try {
                        inputDevice = this.mIm.getInputDevice(id);
                        if (inputDevice == null) {
                            continue;
                        } else {
                            this.mInputDevices.setValueAt(i, inputDevice);
                        }
                    } catch (RemoteException ex) {
                        throw ex.rethrowFromSystemServer();
                    }
                }
                if (descriptor.equals(inputDevice.getDescriptor())) {
                    return inputDevice;
                }
            }
            return null;
        }
    }

    HostUsiVersion getHostUsiVersion(Display display) {
        Objects.requireNonNull(display, "display should not be null");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            for (int i = 0; i < this.mInputDevices.size(); i++) {
                InputDevice device = getInputDevice(this.mInputDevices.keyAt(i));
                if (device != null
                        && device.getAssociatedDisplayId() == display.getDisplayId()
                        && device.getHostUsiVersion() != null) {
                    return device.getHostUsiVersion();
                }
            }
            try {
                return this.mIm.getHostUsiVersionFromDisplayConfig(display.getDisplayId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private void initializeLidStateListenerLocked() {
        LidStateChangedListener listener = new LidStateChangedListener();
        try {
            this.mIm.registerLidStateChangedListener(listener);
            this.mLidStateChangedListener = listener;
            this.mSemOnLidStateChangedListeners = new ArrayList();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private int findSemOnLidStateChangedListenerLocked(
            InputManager.SemOnLidStateChangedListener listener) {
        if (this.mSemOnLidStateChangedListeners != null) {
            int N = this.mSemOnLidStateChangedListeners.size();
            for (int i = 0; i < N; i++) {
                if (this.mSemOnLidStateChangedListeners.get(i).mListener == listener) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLidStateChanged(long whenNanos, boolean lidOpen) {
        if (DEBUG) {
            Log.d(
                    TAG,
                    "Received lid state changed: whenNanos=" + whenNanos + ", lidOpen=" + lidOpen);
        }
        synchronized (this.mLidStateLock) {
            int N = this.mSemOnLidStateChangedListeners.size();
            for (int i = 0; i < N; i++) {
                SemOnLidStateChangedListenerDelegate listener =
                        this.mSemOnLidStateChangedListeners.get(i);
                listener.sendLidStateChanged(whenNanos, lidOpen);
            }
        }
    }

    private final class LidStateChangedListener extends ISemLidStateChangedListener.Stub {
        private LidStateChangedListener() {}

        @Override // android.hardware.input.ISemLidStateChangedListener
        public void onLidStateChanged(long whenNanos, boolean lidOpen) {
            InputManagerGlobal.this.onLidStateChanged(whenNanos, lidOpen);
        }
    }

    private static final class SemOnLidStateChangedListenerDelegate extends Handler {
        private static final int MSG_LID_STATE_CHANGED = 0;
        public final InputManager.SemOnLidStateChangedListener mListener;

        public SemOnLidStateChangedListenerDelegate(
                InputManager.SemOnLidStateChangedListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        public void sendLidStateChanged(long whenNanos, boolean lidOpen) {
            SomeArgs args = SomeArgs.obtain();
            args.argi1 = (int) ((-1) & whenNanos);
            args.argi2 = (int) (whenNanos >> 32);
            args.arg1 = Boolean.valueOf(lidOpen);
            obtainMessage(0, args).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    this.mListener.onLidStateChanged(
                            (r0.argi1 & 4294967295L) | (r0.argi2 << 32),
                            !((Boolean) ((SomeArgs) message.obj).arg1).booleanValue() ? 1 : 0);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTabletModeChanged(long whenNanos, boolean inTabletMode) {
        if (DEBUG) {
            Log.d(
                    TAG,
                    "Received tablet mode changed: whenNanos="
                            + whenNanos
                            + ", inTabletMode="
                            + inTabletMode);
        }
        synchronized (this.mOnTabletModeChangedListeners) {
            int numListeners = this.mOnTabletModeChangedListeners.size();
            for (int i = 0; i < numListeners; i++) {
                OnTabletModeChangedListenerDelegate listener =
                        this.mOnTabletModeChangedListeners.get(i);
                listener.sendTabletModeChanged(whenNanos, inTabletMode);
            }
        }
    }

    private final class TabletModeChangedListener extends ITabletModeChangedListener.Stub {
        private TabletModeChangedListener() {}

        @Override // android.hardware.input.ITabletModeChangedListener
        public void onTabletModeChanged(long whenNanos, boolean inTabletMode) {
            InputManagerGlobal.this.onTabletModeChanged(whenNanos, inTabletMode);
        }
    }

    private static final class OnTabletModeChangedListenerDelegate extends Handler {
        private static final int MSG_TABLET_MODE_CHANGED = 0;
        public final InputManager.OnTabletModeChangedListener mListener;

        OnTabletModeChangedListenerDelegate(
                InputManager.OnTabletModeChangedListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        public void sendTabletModeChanged(long whenNanos, boolean inTabletMode) {
            SomeArgs args = SomeArgs.obtain();
            args.argi1 = (int) whenNanos;
            args.argi2 = (int) (whenNanos >> 32);
            args.arg1 = Boolean.valueOf(inTabletMode);
            obtainMessage(0, args).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                SomeArgs args = (SomeArgs) msg.obj;
                long whenNanos = (args.argi1 & 4294967295L) | (args.argi2 << 32);
                boolean inTabletMode = ((Boolean) args.arg1).booleanValue();
                this.mListener.onTabletModeChanged(whenNanos, inTabletMode);
            }
        }
    }

    void registerOnTabletModeChangedListener(
            InputManager.OnTabletModeChangedListener listener, Handler handler) {
        Objects.requireNonNull(listener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            if (this.mOnTabletModeChangedListeners == null) {
                initializeTabletModeListenerLocked();
            }
            int idx = findOnTabletModeChangedListenerLocked(listener);
            if (idx < 0) {
                OnTabletModeChangedListenerDelegate d =
                        new OnTabletModeChangedListenerDelegate(listener, handler);
                this.mOnTabletModeChangedListeners.add(d);
            }
        }
    }

    void unregisterOnTabletModeChangedListener(InputManager.OnTabletModeChangedListener listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            int idx = findOnTabletModeChangedListenerLocked(listener);
            if (idx >= 0) {
                OnTabletModeChangedListenerDelegate d =
                        this.mOnTabletModeChangedListeners.remove(idx);
                d.removeCallbacksAndMessages(null);
            }
        }
    }

    private void initializeTabletModeListenerLocked() {
        TabletModeChangedListener listener = new TabletModeChangedListener();
        try {
            this.mIm.registerTabletModeChangedListener(listener);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private int findOnTabletModeChangedListenerLocked(
            InputManager.OnTabletModeChangedListener listener) {
        int n = this.mOnTabletModeChangedListeners.size();
        for (int i = 0; i < n; i++) {
            if (this.mOnTabletModeChangedListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    private static final class RegisteredBatteryListeners {
        final List<InputDeviceBatteryListenerDelegate> mDelegates;
        IInputDeviceBatteryState mInputDeviceBatteryState;

        private RegisteredBatteryListeners() {
            this.mDelegates = new ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InputDeviceBatteryListenerDelegate {
        final Executor mExecutor;
        final InputManager.InputDeviceBatteryListener mListener;

        InputDeviceBatteryListenerDelegate(
                InputManager.InputDeviceBatteryListener listener, Executor executor) {
            this.mListener = listener;
            this.mExecutor = executor;
        }

        void notifyBatteryStateChanged(final IInputDeviceBatteryState state) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.input.InputManagerGlobal$InputDeviceBatteryListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InputManagerGlobal.InputDeviceBatteryListenerDelegate.this
                                    .lambda$notifyBatteryStateChanged$0(state);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyBatteryStateChanged$0(
                IInputDeviceBatteryState state) {
            this.mListener.onBatteryStateChanged(
                    state.deviceId,
                    state.updateTime,
                    new LocalBatteryState(state.isPresent, state.status, state.capacity));
        }
    }

    public void addInputDeviceBatteryListener(
            int i,
            Executor executor,
            InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(inputDeviceBatteryListener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
            byte b = 0;
            if (this.mBatteryListeners == null) {
                this.mBatteryListeners = new SparseArray<>();
                this.mInputDeviceBatteryListener = new LocalInputDeviceBatteryListener();
            }
            RegisteredBatteryListeners registeredBatteryListeners = this.mBatteryListeners.get(i);
            if (registeredBatteryListeners == null) {
                registeredBatteryListeners = new RegisteredBatteryListeners();
                this.mBatteryListeners.put(i, registeredBatteryListeners);
                try {
                    this.mIm.registerBatteryListener(i, this.mInputDeviceBatteryListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                int size = registeredBatteryListeners.mDelegates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (Objects.equals(
                            inputDeviceBatteryListener,
                            registeredBatteryListeners.mDelegates.get(i2).mListener)) {
                        throw new IllegalArgumentException(
                                "Attempting to register an InputDeviceBatteryListener that has"
                                        + " already been registered for deviceId: "
                                        + i);
                    }
                }
            }
            InputDeviceBatteryListenerDelegate inputDeviceBatteryListenerDelegate =
                    new InputDeviceBatteryListenerDelegate(inputDeviceBatteryListener, executor);
            registeredBatteryListeners.mDelegates.add(inputDeviceBatteryListenerDelegate);
            if (registeredBatteryListeners.mInputDeviceBatteryState != null) {
                inputDeviceBatteryListenerDelegate.notifyBatteryStateChanged(
                        registeredBatteryListeners.mInputDeviceBatteryState);
            }
        }
    }

    void removeInputDeviceBatteryListener(
            int deviceId, InputManager.InputDeviceBatteryListener listener) {
        Objects.requireNonNull(listener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
            if (this.mBatteryListeners == null) {
                return;
            }
            RegisteredBatteryListeners listenersForDevice = this.mBatteryListeners.get(deviceId);
            if (listenersForDevice == null) {
                return;
            }
            List<InputDeviceBatteryListenerDelegate> delegates = listenersForDevice.mDelegates;
            int i = 0;
            while (i < delegates.size()) {
                if (Objects.equals(listener, delegates.get(i).mListener)) {
                    delegates.remove(i);
                } else {
                    i++;
                }
            }
            if (delegates.isEmpty()) {
                this.mBatteryListeners.remove(deviceId);
                try {
                    this.mIm.unregisterBatteryListener(deviceId, this.mInputDeviceBatteryListener);
                    if (this.mBatteryListeners.size() == 0) {
                        this.mBatteryListeners = null;
                        this.mInputDeviceBatteryListener = null;
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private class LocalInputDeviceBatteryListener extends IInputDeviceBatteryListener.Stub {
        private LocalInputDeviceBatteryListener() {}

        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(IInputDeviceBatteryState state) {
            synchronized (InputManagerGlobal.this.mBatteryListenersLock) {
                if (InputManagerGlobal.this.mBatteryListeners == null) {
                    return;
                }
                RegisteredBatteryListeners entry =
                        (RegisteredBatteryListeners)
                                InputManagerGlobal.this.mBatteryListeners.get(state.deviceId);
                if (entry == null) {
                    return;
                }
                entry.mInputDeviceBatteryState = state;
                int numDelegates = entry.mDelegates.size();
                for (int i = 0; i < numDelegates; i++) {
                    entry.mDelegates
                            .get(i)
                            .notifyBatteryStateChanged(entry.mInputDeviceBatteryState);
                }
            }
        }
    }

    public BatteryState getInputDeviceBatteryState(int deviceId, boolean hasBattery) {
        if (!hasBattery) {
            return new LocalBatteryState();
        }
        try {
            IInputDeviceBatteryState state = this.mIm.getBatteryState(deviceId);
            return new LocalBatteryState(state.isPresent, state.status, state.capacity);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private static final class LocalBatteryState extends BatteryState {
        private final float mCapacity;
        private final boolean mIsPresent;
        private final int mStatus;

        LocalBatteryState() {
            this(false, 1, Float.NaN);
        }

        LocalBatteryState(boolean isPresent, int status, float capacity) {
            this.mIsPresent = isPresent;
            this.mStatus = status;
            this.mCapacity = capacity;
        }

        @Override // android.hardware.BatteryState
        public boolean isPresent() {
            return this.mIsPresent;
        }

        @Override // android.hardware.BatteryState
        public int getStatus() {
            return this.mStatus;
        }

        @Override // android.hardware.BatteryState
        public float getCapacity() {
            return this.mCapacity;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class KeyboardBacklightListenerDelegate {
        final Executor mExecutor;
        final InputManager.KeyboardBacklightListener mListener;

        KeyboardBacklightListenerDelegate(
                InputManager.KeyboardBacklightListener listener, Executor executor) {
            this.mListener = listener;
            this.mExecutor = executor;
        }

        void notifyKeyboardBacklightChange(
                final int deviceId,
                final IKeyboardBacklightState state,
                final boolean isTriggeredByKeyPress) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.input.InputManagerGlobal$KeyboardBacklightListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InputManagerGlobal.KeyboardBacklightListenerDelegate.this
                                    .lambda$notifyKeyboardBacklightChange$0(
                                            deviceId, state, isTriggeredByKeyPress);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyKeyboardBacklightChange$0(
                int deviceId, IKeyboardBacklightState state, boolean isTriggeredByKeyPress) {
            this.mListener.onKeyboardBacklightChanged(
                    deviceId,
                    new LocalKeyboardBacklightState(
                            state.brightnessLevel, state.maxBrightnessLevel),
                    isTriggeredByKeyPress);
        }
    }

    private class LocalKeyboardBacklightListener extends IKeyboardBacklightListener.Stub {
        private LocalKeyboardBacklightListener() {}

        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(
                int deviceId, IKeyboardBacklightState state, boolean isTriggeredByKeyPress) {
            synchronized (InputManagerGlobal.this.mKeyboardBacklightListenerLock) {
                if (InputManagerGlobal.this.mKeyboardBacklightListeners == null) {
                    return;
                }
                int numListeners = InputManagerGlobal.this.mKeyboardBacklightListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    ((KeyboardBacklightListenerDelegate)
                                    InputManagerGlobal.this.mKeyboardBacklightListeners.get(i))
                            .notifyKeyboardBacklightChange(deviceId, state, isTriggeredByKeyPress);
                }
            }
        }
    }

    private static final class LocalKeyboardBacklightState extends KeyboardBacklightState {
        private final int mBrightnessLevel;
        private final int mMaxBrightnessLevel;

        LocalKeyboardBacklightState(int brightnessLevel, int maxBrightnessLevel) {
            this.mBrightnessLevel = brightnessLevel;
            this.mMaxBrightnessLevel = maxBrightnessLevel;
        }

        @Override // android.hardware.input.KeyboardBacklightState
        public int getBrightnessLevel() {
            return this.mBrightnessLevel;
        }

        @Override // android.hardware.input.KeyboardBacklightState
        public int getMaxBrightnessLevel() {
            return this.mMaxBrightnessLevel;
        }
    }

    void registerKeyboardBacklightListener(
            Executor executor, InputManager.KeyboardBacklightListener listener)
            throws IllegalArgumentException {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(listener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            if (this.mKeyboardBacklightListener == null) {
                this.mKeyboardBacklightListeners = new ArrayList<>();
                this.mKeyboardBacklightListener = new LocalKeyboardBacklightListener();
                try {
                    this.mIm.registerKeyboardBacklightListener(this.mKeyboardBacklightListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int numListeners = this.mKeyboardBacklightListeners.size();
            for (int i = 0; i < numListeners; i++) {
                if (this.mKeyboardBacklightListeners.get(i).mListener == listener) {
                    throw new IllegalArgumentException("Listener has already been registered!");
                }
            }
            KeyboardBacklightListenerDelegate delegate =
                    new KeyboardBacklightListenerDelegate(listener, executor);
            this.mKeyboardBacklightListeners.add(delegate);
        }
    }

    void unregisterKeyboardBacklightListener(
            final InputManager.KeyboardBacklightListener listener) {
        Objects.requireNonNull(listener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            if (this.mKeyboardBacklightListeners == null) {
                return;
            }
            this.mKeyboardBacklightListeners.removeIf(
                    new Predicate() { // from class:
                        // android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return InputManagerGlobal.lambda$unregisterKeyboardBacklightListener$1(
                                    InputManager.KeyboardBacklightListener.this,
                                    (InputManagerGlobal.KeyboardBacklightListenerDelegate) obj);
                        }
                    });
            if (this.mKeyboardBacklightListeners.isEmpty()) {
                try {
                    this.mIm.unregisterKeyboardBacklightListener(this.mKeyboardBacklightListener);
                    this.mKeyboardBacklightListeners = null;
                    this.mKeyboardBacklightListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterKeyboardBacklightListener$1(
            InputManager.KeyboardBacklightListener listener,
            KeyboardBacklightListenerDelegate delegate) {
        return delegate.mListener == listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StickyModifierStateListenerDelegate {
        final Executor mExecutor;
        final InputManager.StickyModifierStateListener mListener;

        StickyModifierStateListenerDelegate(
                InputManager.StickyModifierStateListener listener, Executor executor) {
            this.mListener = listener;
            this.mExecutor = executor;
        }

        void notifyStickyModifierStateChange(
                final int modifierState, final int lockedModifierState) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.hardware.input.InputManagerGlobal$StickyModifierStateListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InputManagerGlobal.StickyModifierStateListenerDelegate.this
                                    .lambda$notifyStickyModifierStateChange$0(
                                            modifierState, lockedModifierState);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStickyModifierStateChange$0(
                int modifierState, int lockedModifierState) {
            this.mListener.onStickyModifierStateChanged(
                    new LocalStickyModifierState(modifierState, lockedModifierState));
        }
    }

    private class LocalStickyModifierStateListener extends IStickyModifierStateListener.Stub {
        private LocalStickyModifierStateListener() {}

        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int modifierState, int lockedModifierState) {
            synchronized (InputManagerGlobal.this.mStickyModifierStateListenerLock) {
                if (InputManagerGlobal.this.mStickyModifierStateListeners == null) {
                    return;
                }
                int numListeners = InputManagerGlobal.this.mStickyModifierStateListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    ((StickyModifierStateListenerDelegate)
                                    InputManagerGlobal.this.mStickyModifierStateListeners.get(i))
                            .notifyStickyModifierStateChange(modifierState, lockedModifierState);
                }
            }
        }
    }

    private static final class LocalStickyModifierState extends StickyModifierState {
        private final int mLockedModifierState;
        private final int mModifierState;

        LocalStickyModifierState(int modifierState, int lockedModifierState) {
            this.mModifierState = modifierState;
            this.mLockedModifierState = lockedModifierState;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isShiftModifierOn() {
            return (this.mModifierState & 1) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isShiftModifierLocked() {
            return (this.mLockedModifierState & 1) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isCtrlModifierOn() {
            return (this.mModifierState & 4096) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isCtrlModifierLocked() {
            return (this.mLockedModifierState & 4096) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isMetaModifierOn() {
            return (this.mModifierState & 65536) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isMetaModifierLocked() {
            return (this.mLockedModifierState & 65536) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltModifierOn() {
            return (this.mModifierState & 16) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltModifierLocked() {
            return (this.mLockedModifierState & 16) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltGrModifierOn() {
            return (this.mModifierState & 32) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltGrModifierLocked() {
            return (this.mLockedModifierState & 32) != 0;
        }
    }

    void registerStickyModifierStateListener(
            Executor executor, InputManager.StickyModifierStateListener listener)
            throws IllegalArgumentException {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(listener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            if (this.mStickyModifierStateListener == null) {
                this.mStickyModifierStateListeners = new ArrayList<>();
                this.mStickyModifierStateListener = new LocalStickyModifierStateListener();
                try {
                    this.mIm.registerStickyModifierStateListener(this.mStickyModifierStateListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int numListeners = this.mStickyModifierStateListeners.size();
            for (int i = 0; i < numListeners; i++) {
                if (this.mStickyModifierStateListeners.get(i).mListener == listener) {
                    throw new IllegalArgumentException("Listener has already been registered!");
                }
            }
            StickyModifierStateListenerDelegate delegate =
                    new StickyModifierStateListenerDelegate(listener, executor);
            this.mStickyModifierStateListeners.add(delegate);
        }
    }

    void unregisterStickyModifierStateListener(
            final InputManager.StickyModifierStateListener listener) {
        Objects.requireNonNull(listener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            if (this.mStickyModifierStateListeners == null) {
                return;
            }
            this.mStickyModifierStateListeners.removeIf(
                    new Predicate() { // from class:
                        // android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return InputManagerGlobal
                                    .lambda$unregisterStickyModifierStateListener$2(
                                            InputManager.StickyModifierStateListener.this,
                                            (InputManagerGlobal.StickyModifierStateListenerDelegate)
                                                    obj);
                        }
                    });
            if (this.mStickyModifierStateListeners.isEmpty()) {
                try {
                    this.mIm.unregisterStickyModifierStateListener(
                            this.mStickyModifierStateListener);
                    this.mStickyModifierStateListeners = null;
                    this.mStickyModifierStateListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterStickyModifierStateListener$2(
            InputManager.StickyModifierStateListener listener,
            StickyModifierStateListenerDelegate delegate) {
        return delegate.mListener == listener;
    }

    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier identifier) {
        return new KeyboardLayout[0];
    }

    public void setCurrentKeyboardLayoutForInputDevice(
            InputDeviceIdentifier identifier, String keyboardLayoutDescriptor) {}

    public SensorManager getInputDeviceSensorManager(int deviceId) {
        SensorManager sensorManager;
        synchronized (this.mInputDeviceListeners) {
            if (this.mInputDeviceSensorManager == null) {
                this.mInputDeviceSensorManager = new InputDeviceSensorManager(this);
            }
            sensorManager = this.mInputDeviceSensorManager.getSensorManager(deviceId);
        }
        return sensorManager;
    }

    InputSensorInfo[] getSensorList(int deviceId) {
        try {
            return this.mIm.getSensorList(deviceId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean enableSensor(
            int deviceId, int sensorType, int samplingPeriodUs, int maxBatchReportLatencyUs) {
        try {
            return this.mIm.enableSensor(
                    deviceId, sensorType, samplingPeriodUs, maxBatchReportLatencyUs);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void disableSensor(int deviceId, int sensorType) {
        try {
            this.mIm.disableSensor(deviceId, sensorType);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean flushSensor(int deviceId, int sensorType) {
        try {
            return this.mIm.flushSensor(deviceId, sensorType);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean registerSensorListener(IInputSensorEventListener listener) {
        try {
            return this.mIm.registerSensorListener(listener);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void unregisterSensorListener(IInputSensorEventListener listener) {
        try {
            this.mIm.unregisterSensorListener(listener);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public LightsManager getInputDeviceLightsManager(int deviceId) {
        return new InputDeviceLightsManager(deviceId);
    }

    List<Light> getLights(int deviceId) {
        try {
            return this.mIm.getLights(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    LightState getLightState(int deviceId, Light light) {
        try {
            return this.mIm.getLightState(deviceId, light.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void requestLights(int deviceId, LightsRequest request, IBinder token) {
        try {
            List<Integer> lightIdList = request.getLights();
            int[] lightIds = new int[lightIdList.size()];
            for (int i = 0; i < lightIds.length; i++) {
                lightIds[i] = lightIdList.get(i).intValue();
            }
            List<LightState> lightStateList = request.getLightStates();
            this.mIm.setLightStates(
                    deviceId,
                    lightIds,
                    (LightState[]) lightStateList.toArray(new LightState[0]),
                    token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void openLightSession(int deviceId, String opPkg, IBinder token) {
        try {
            this.mIm.openLightSession(deviceId, opPkg, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void closeLightSession(int deviceId, IBinder token) {
        try {
            this.mIm.closeLightSession(deviceId, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Vibrator getInputDeviceVibrator(int deviceId, int vibratorId) {
        return new InputDeviceVibrator(deviceId, vibratorId);
    }

    public VibratorManager getInputDeviceVibratorManager(int deviceId) {
        return new InputDeviceVibratorManager(deviceId);
    }

    int[] getVibratorIds(int deviceId) {
        try {
            return this.mIm.getVibratorIds(deviceId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void vibrate(int deviceId, VibrationEffect effect, IBinder token) {
        try {
            this.mIm.vibrate(deviceId, effect, token);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void vibrate(int deviceId, CombinedVibration effect, IBinder token) {
        try {
            this.mIm.vibrateCombined(deviceId, effect, token);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void cancelVibrate(int deviceId, IBinder token) {
        try {
            this.mIm.cancelVibrate(deviceId, token);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean isVibrating(int deviceId) {
        try {
            return this.mIm.isVibrating(deviceId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean registerVibratorStateListener(int deviceId, IVibratorStateListener listener) {
        try {
            return this.mIm.registerVibratorStateListener(deviceId, listener);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    boolean unregisterVibratorStateListener(int deviceId, IVibratorStateListener listener) {
        try {
            return this.mIm.unregisterVibratorStateListener(deviceId, listener);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean[] deviceHasKeys(int[] keyCodes) {
        return deviceHasKeys(-1, keyCodes);
    }

    public boolean[] deviceHasKeys(int id, int[] keyCodes) {
        boolean[] ret = new boolean[keyCodes.length];
        try {
            this.mIm.hasKeys(id, -256, keyCodes, ret);
            return ret;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getKeyCodeForKeyLocation(int deviceId, int locationKeyCode) {
        try {
            return this.mIm.getKeyCodeForKeyLocation(deviceId, locationKeyCode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public KeyCharacterMap getKeyCharacterMap(KeyboardLayout keyboardLayout) {
        if (keyboardLayout == null) {
            return KeyCharacterMap.load(-1);
        }
        try {
            return this.mIm.getKeyCharacterMap(keyboardLayout.getDescriptor());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(InputEvent event, int mode, int targetUid) {
        Objects.requireNonNull(event, "event must not be null");
        if (mode != 0 && mode != 2 && mode != 1) {
            throw new IllegalArgumentException("mode is invalid");
        }
        try {
            return this.mIm.injectInputEventToTarget(event, mode, targetUid);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(InputEvent event, int mode) {
        return injectInputEvent(event, mode, -1);
    }

    public void setPointerIconType(int iconId) {
        Log.e(TAG, "setPointerIconType: Unsupported app usage!");
    }

    public void setCustomPointerIcon(PointerIcon icon) {
        Log.e(TAG, "setCustomPointerIcon: Unsupported app usage!");
    }

    public boolean setPointerIcon(
            PointerIcon icon, int displayId, int deviceId, int pointerId, IBinder inputToken) {
        try {
            return this.mIm.setPointerIcon(icon, displayId, deviceId, pointerId, inputToken);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void requestPointerCapture(IBinder windowToken, boolean enable) {
        try {
            this.mIm.requestPointerCapture(windowToken, enable);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public InputMonitor monitorGestureInput(String name, int displayId) {
        try {
            return this.mIm.monitorGestureInput(new Binder(), name, displayId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public InputMonitor monitorGestureInput(String name, int displayId, int filter) {
        try {
            return this.mIm.monitorGestureInputFiltered(new Binder(), name, displayId, filter);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public InputChannel monitorInput(String inputChannelName, int displayId, int filter) {
        try {
            return this.mIm.monitorInputForBinder(inputChannelName, displayId, filter);
        } catch (RemoteException e) {
            return null;
        }
    }

    public void addUniqueIdAssociationByPort(String inputPort, String displayUniqueId) {
        try {
            this.mIm.addUniqueIdAssociationByPort(inputPort, displayUniqueId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUniqueIdAssociationByPort(String inputPort) {
        try {
            this.mIm.removeUniqueIdAssociationByPort(inputPort);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUniqueIdAssociationByDescriptor(
            String inputDeviceDescriptor, String displayUniqueId) {
        try {
            this.mIm.addUniqueIdAssociationByDescriptor(inputDeviceDescriptor, displayUniqueId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUniqueIdAssociationByDescriptor(String inputDeviceDescriptor) {
        try {
            this.mIm.removeUniqueIdAssociationByDescriptor(inputDeviceDescriptor);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getInputDeviceBluetoothAddress(int deviceId) {
        try {
            return this.mIm.getInputDeviceBluetoothAddress(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelCurrentTouch() {
        try {
            this.mIm.cancelCurrentTouch();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pilferPointers(IBinder inputChannelToken) {
        try {
            this.mIm.pilferPointers(inputChannelToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnPointerIconChangedListener(
            InputManager.SemOnPointerIconChangedListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mPointerIconLock) {
            if (this.mPointerIconChangedListener == null) {
                initializePointerIconChangedListenerLocked();
            }
            int index = findOnPointerIconChangedListenerLocked(listener);
            if (index < 0) {
                this.mOnPointerIconChangedListeners.add(
                        new OnPointerIconChangedListenerDelegate(listener, handler));
            }
        }
    }

    public void semUnregisterOnPointerIconChangedListener(
            InputManager.SemOnPointerIconChangedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mPointerIconLock) {
            int index = findOnPointerIconChangedListenerLocked(listener);
            if (index >= 0) {
                OnPointerIconChangedListenerDelegate d =
                        this.mOnPointerIconChangedListeners.get(index);
                d.removeCallbacksAndMessages(null);
                this.mOnPointerIconChangedListeners.remove(index);
            }
        }
    }

    private void initializePointerIconChangedListenerLocked() {
        PointerIconChangedListener listener = new PointerIconChangedListener();
        try {
            this.mIm.registerPointerIconChangedListener(listener);
            this.mPointerIconChangedListener = listener;
            this.mOnPointerIconChangedListeners = new ArrayList();
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    final int findOnPointerIconChangedListenerLocked(
            InputManager.SemOnPointerIconChangedListener listener) {
        int N = this.mOnPointerIconChangedListeners.size();
        for (int i = 0; i < N; i++) {
            if (this.mOnPointerIconChangedListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    private final class PointerIconChangedListener extends IPointerIconChangedListener.Stub {
        private PointerIconChangedListener() {}

        @Override // android.hardware.input.IPointerIconChangedListener
        public void onPointerIconChanged(int type, PointerIcon icon) {
            InputManagerGlobal.this.onPointerIconChanged(type, icon);
        }
    }

    private static final class OnPointerIconChangedListenerDelegate extends Handler {
        public final InputManager.SemOnPointerIconChangedListener mListener;

        public OnPointerIconChangedListenerDelegate(
                InputManager.SemOnPointerIconChangedListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int type = msg.arg1;
                    PointerIcon icon =
                            msg.obj instanceof PointerIcon ? (PointerIcon) msg.obj : null;
                    if (icon == null) {
                        this.mListener.onPointerIconChanged(type, null, 0.0f, 0.0f);
                        break;
                    } else {
                        this.mListener.onPointerIconChanged(
                                type, icon.getBitmap(), icon.getHotSpotX(), icon.getHotSpotY());
                        break;
                    }
            }
        }
    }

    public void setDisplayIdForPointerIcon(int displayId) {
        try {
            Log.d(TAG, "setDisplayIdForPointerIcon = " + displayId);
            this.mIm.setDisplayIdForPointerIcon(displayId);
        } catch (RemoteException e) {
        }
    }

    public void setIsStylusFromTouchpad(boolean isStylusFromTouchpad) {
        if (this.mIsStylusFromTouchpad != isStylusFromTouchpad) {
            this.mIsStylusFromTouchpad = isStylusFromTouchpad;
        }
    }

    public int getPointerIconType() {
        try {
            this.mPointerIconType = this.mIm.getPointerIconType();
            Log.d(TAG, "getPointerIconType = " + this.mPointerIconType);
        } catch (RemoteException e) {
        }
        return this.mPointerIconType;
    }

    private int mappingToMousePointer(int iconId) {
        switch (iconId) {
            case 20001:
            case 20010:
                return 10121;
            case 20002:
            case 20003:
            case 20004:
            case 20005:
            default:
                if (iconId > 20000) {
                    return iconId + TaskConstants.TASK_CHILD_LAYER_LETTERBOX_BACKGROUND + 10100;
                }
                return iconId;
            case 20006:
                return 10122;
            case 20007:
                return 10123;
            case 20008:
                return 10124;
            case 20009:
                return 10125;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPointerIconChanged(int type, PointerIcon icon) {
        if (DEBUG) {
            Log.d(TAG, "Received pointer icon changed.");
        }
        synchronized (this.mPointerIconLock) {
            int numListeners = this.mOnPointerIconChangedListeners.size();
            for (int i = 0; i < numListeners; i++) {
                OnPointerIconChangedListenerDelegate listener =
                        this.mOnPointerIconChangedListeners.get(i);
                listener.sendMessage(listener.obtainMessage(1, type, 0, icon));
            }
            this.mPointerIconType = type;
            this.mPointerIcon = icon;
        }
    }

    public void setDragPointerInfo(IBinder dragToken, int deviceId, int pointerId) {
        this.mDragToken = dragToken;
        this.mDeviceId = deviceId;
        this.mPointerId = pointerId;
    }

    public void setDragPointerIcon(PointerIcon dragPointerIcon) {
        this.mDragPointerIcon = dragPointerIcon;
    }

    public void clreaDragPointerInfo() {
        this.mDragToken = null;
        this.mDragPointerIcon = null;
    }

    public void updateDragPointerIcon(int displayId) {
        if (this.mDragToken != null && this.mDragPointerIcon != null) {
            setPointerIcon(
                    this.mDragPointerIcon,
                    displayId,
                    this.mDeviceId,
                    this.mPointerId,
                    this.mDragToken);
        }
    }

    public void registerOnWirelessKeyboardShareChangedListener(
            OnWirelessKeyboardShareChangedListener listener, Handler handler) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            if (this.mWirelessKeyboardShareChangedListener == null) {
                initializeWirelessKeyboardShareListenerLocked();
            }
            int idx = findOnWirelessKeyboardShareChangedListenerLocked(listener);
            if (idx < 0) {
                OnWirelessKeyboardShareChangedListenerDelegate d =
                        new OnWirelessKeyboardShareChangedListenerDelegate(listener, handler);
                this.mOnWirelessKeyboardShareChangedListeners.add(d);
            }
        }
    }

    public void unregisterOnWirelessKeyboardShareChangedListener(
            OnWirelessKeyboardShareChangedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            int idx = findOnWirelessKeyboardShareChangedListenerLocked(listener);
            if (idx >= 0) {
                OnWirelessKeyboardShareChangedListenerDelegate d =
                        this.mOnWirelessKeyboardShareChangedListeners.remove(idx);
                d.removeCallbacksAndMessages(null);
            }
        }
    }

    private void initializeWirelessKeyboardShareListenerLocked() {
        WirelessKeyboardShareChangedListener listener = new WirelessKeyboardShareChangedListener();
        try {
            this.mIm.registerWirelessKeyboardShareChangedListener(listener);
            this.mWirelessKeyboardShareChangedListener = listener;
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private int findOnWirelessKeyboardShareChangedListenerLocked(
            OnWirelessKeyboardShareChangedListener listener) {
        int N = this.mOnWirelessKeyboardShareChangedListeners.size();
        for (int i = 0; i < N; i++) {
            if (this.mOnWirelessKeyboardShareChangedListeners.get(i).mListener == listener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWirelessKeyboardShareChanged(long whenNanos, int index, String contents) {
        if (DEBUG) {
            Log.d(
                    TAG,
                    "Received wireless keyboard share changed: whenNanos="
                            + whenNanos
                            + ", index = "
                            + index
                            + " "
                            + contents);
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            int N = this.mOnWirelessKeyboardShareChangedListeners.size();
            for (int i = 0; i < N; i++) {
                OnWirelessKeyboardShareChangedListenerDelegate listener =
                        this.mOnWirelessKeyboardShareChangedListeners.get(i);
                listener.sendWirelessKeyboardShareChanged(whenNanos, index, contents);
            }
        }
    }

    private final class WirelessKeyboardShareChangedListener
            extends IWirelessKeyboardShareChangedListener.Stub {
        private WirelessKeyboardShareChangedListener() {}

        @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
        public void onWirelessKeyboardShareChanged(long whenNanos, int index, String contents) {
            InputManagerGlobal.this.onWirelessKeyboardShareChanged(whenNanos, index, contents);
        }
    }

    private static final class OnWirelessKeyboardShareChangedListenerDelegate extends Handler {
        private static final int MSG_WIRELESS_KEYBOARD_SHARE_CHANGED = 0;
        public final OnWirelessKeyboardShareChangedListener mListener;

        public OnWirelessKeyboardShareChangedListenerDelegate(
                OnWirelessKeyboardShareChangedListener listener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = listener;
        }

        public void sendWirelessKeyboardShareChanged(long whenNanos, int index, String contents) {
            SomeArgs args = SomeArgs.obtain();
            args.argi1 = (int) ((-1) & whenNanos);
            args.argi2 = (int) (whenNanos >> 32);
            args.argi3 = index;
            args.arg1 = contents;
            obtainMessage(0, args).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SomeArgs args = (SomeArgs) msg.obj;
                    long whenNanos = (args.argi1 & 4294967295L) | (args.argi2 << 32);
                    int index = args.argi3;
                    String contents = (String) args.arg1;
                    this.mListener.onWirelessKeyboardShareChanged(whenNanos, index, contents);
                    break;
            }
        }
    }
}
