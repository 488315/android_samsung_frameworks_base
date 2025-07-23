package android.hardware.input;

import android.hardware.BatteryState;
import android.hardware.SensorManager;
import android.hardware.input.IInputDeviceBatteryListener;
import android.hardware.input.IInputDevicesChangedListener;
import android.hardware.input.IInputManager;
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
import android.hardware.input.InputManager;
import android.hardware.input.InputManagerGlobal;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.hardware.lights.LightsManager;
import android.hardware.lights.LightsRequest;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import android.util.IntArray;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.KeyCharacterMap;
import android.view.PointerIcon;
import com.android.internal.os.SomeArgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class InputManagerGlobal {
    private static final int CONVERSION_TYPE_SPEN_TO_MOUSE = 10100;
    private static final int MSG_MULTI_FINGER_GESTURE = 1;
    private static final int MSG_POINTERICON_CHANGED = 1;
    private static final String TAG = "InputManagerGlobal";
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
    private IKeyEventActivityListener mKeyEventActivityListener;
    private ArrayList<InputManager.KeyEventActivityListener> mKeyEventActivityListeners;
    private IKeyGestureEventListener mKeyGestureEventListener;
    private ArrayList<KeyGestureEventListenerDelegate> mKeyGestureEventListeners;
    private IKeyGestureHandler mKeyGestureHandler;
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
    private final ArrayList<InputDeviceListenerDelegate> mInputDeviceListeners = new ArrayList<>();
    private final ArrayList<OnTabletModeChangedListenerDelegate> mOnTabletModeChangedListeners = new ArrayList<>();
    private final Object mLidStateLock = new Object();
    private final Object mMultiFingerGestureLock = new Object();
    private final Object mSwitchEventChangedLock = new Object();
    private final Object mBatteryListenersLock = new Object();
    private final Object mKeyboardBacklightListenerLock = new Object();
    private final Object mWirelessKeyboardShareLock = new Object();
    private List<OnWirelessKeyboardShareChangedListenerDelegate> mOnWirelessKeyboardShareChangedListeners = new ArrayList();
    private final Object mStickyModifierStateListenerLock = new Object();
    private final Object mKeyGestureEventListenerLock = new Object();
    private final Object mKeyEventActivityLock = new Object();
    private final SparseArray<InputManager.KeyGestureEventHandler> mKeyGesturesToHandlerMap = new SparseArray<>();
    private final Object mPointerIconLock = new Object();

    public interface OnWirelessKeyboardShareChangedListener {
        void onWirelessKeyboardShareChanged(long j, int i, String str);
    }

    public interface TestSession extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();
    }

    private int mappingToMousePointer(int i) {
        if (i == 20001) {
            return 10121;
        }
        switch (i) {
            case 20006:
                return 10122;
            case 20007:
                return 10123;
            case 20008:
                return 10124;
            case 20009:
                return 10125;
            case 20010:
                return 10121;
            default:
                return i > 20000 ? i - 9900 : i;
        }
    }

    public void setCurrentKeyboardLayoutForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) {
    }

    private boolean debug() {
        return Log.isLoggable(TAG, 3);
    }

    public InputManagerGlobal(IInputManager iInputManager) {
        String str;
        this.mIm = iInputManager;
        try {
            str = iInputManager.getVelocityTrackerStrategy();
        } catch (RemoteException e) {
            Log.w(TAG, "Could not get VelocityTracker strategy: " + e);
            str = null;
        }
        this.mVelocityTrackerStrategy = str;
    }

    public static InputManagerGlobal getInstance() {
        InputManagerGlobal inputManagerGlobal;
        IBinder service;
        synchronized (InputManagerGlobal.class) {
            if (sInstance == null && (service = ServiceManager.getService("input")) != null) {
                sInstance = new InputManagerGlobal(IInputManager.Stub.asInterface(service));
            }
            inputManagerGlobal = sInstance;
        }
        return inputManagerGlobal;
    }

    public IInputManager getInputManagerService() {
        return this.mIm;
    }

    public static TestSession createTestSession(IInputManager iInputManager) {
        TestSession testSession;
        synchronized (InputManagerGlobal.class) {
            final InputManagerGlobal inputManagerGlobal = sInstance;
            sInstance = new InputManagerGlobal(iInputManager);
            testSession = new TestSession() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda0
                @Override // android.hardware.input.InputManagerGlobal.TestSession, java.lang.AutoCloseable
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

    public InputDevice getInputDevice(int i) {
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int indexOfKey = this.mInputDevices.indexOfKey(i);
            if (indexOfKey < 0) {
                return null;
            }
            InputDevice valueAt = this.mInputDevices.valueAt(indexOfKey);
            if (valueAt == null) {
                try {
                    valueAt = this.mIm.getInputDevice(i);
                    if (valueAt != null) {
                        this.mInputDevices.setValueAt(indexOfKey, valueAt);
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return valueAt;
        }
    }

    private void populateInputDevicesLocked() {
        if (this.mInputDevicesChangedListener == null) {
            InputDevicesChangedListener inputDevicesChangedListener = new InputDevicesChangedListener();
            try {
                this.mIm.registerInputDevicesChangedListener(inputDevicesChangedListener);
                this.mInputDevicesChangedListener = inputDevicesChangedListener;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (this.mInputDevices == null) {
            try {
                int[] inputDeviceIds = this.mIm.getInputDeviceIds();
                this.mInputDevices = new SparseArray<>();
                for (int i : inputDeviceIds) {
                    this.mInputDevices.put(i, null);
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    private final class InputDevicesChangedListener extends IInputDevicesChangedListener.Stub {
        private InputDevicesChangedListener() {
        }

        @Override // android.hardware.input.IInputDevicesChangedListener
        public void onInputDevicesChanged(int[] iArr) throws RemoteException {
            InputManagerGlobal.this.onInputDevicesChanged(iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputDevicesChanged(int[] iArr) {
        boolean debug = debug();
        if (debug) {
            Log.d(TAG, "Received input devices changed: " + Arrays.toString(iArr));
        }
        synchronized (this.mInputDeviceListeners) {
            int size = this.mInputDevices.size();
            while (true) {
                size--;
                if (size <= 0) {
                    break;
                }
                int keyAt = this.mInputDevices.keyAt(size);
                if (!containsDeviceId(iArr, keyAt)) {
                    if (debug) {
                        InputDevice valueAt = this.mInputDevices.valueAt(size);
                        Log.d(TAG, "Device removed: " + keyAt + " (" + (valueAt != null ? valueAt.getName() : "<null>") + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    this.mInputDevices.removeAt(size);
                    InputDeviceSensorManager inputDeviceSensorManager = this.mInputDeviceSensorManager;
                    if (inputDeviceSensorManager != null) {
                        inputDeviceSensorManager.onInputDeviceRemoved(keyAt);
                    }
                    sendMessageToInputDeviceListenersLocked(2, keyAt);
                }
            }
            for (int i = 0; i < iArr.length; i += 2) {
                int i2 = iArr[i];
                int indexOfKey = this.mInputDevices.indexOfKey(i2);
                if (indexOfKey >= 0) {
                    InputDevice valueAt2 = this.mInputDevices.valueAt(indexOfKey);
                    if (valueAt2 != null) {
                        if (valueAt2.getGeneration() != iArr[i + 1]) {
                            if (debug) {
                                Log.d(TAG, "Device changed: " + i2 + " (" + valueAt2.getName() + NavigationBarInflaterView.KEY_CODE_END);
                            }
                            this.mInputDevices.setValueAt(indexOfKey, null);
                            InputDeviceSensorManager inputDeviceSensorManager2 = this.mInputDeviceSensorManager;
                            if (inputDeviceSensorManager2 != null) {
                                inputDeviceSensorManager2.onInputDeviceChanged(i2);
                            }
                            sendMessageToInputDeviceListenersLocked(3, i2);
                        }
                    }
                } else {
                    if (debug) {
                        Log.d(TAG, "Device added: " + i2);
                    }
                    this.mInputDevices.put(i2, null);
                    InputDeviceSensorManager inputDeviceSensorManager3 = this.mInputDeviceSensorManager;
                    if (inputDeviceSensorManager3 != null) {
                        inputDeviceSensorManager3.onInputDeviceAdded(i2);
                    }
                    sendMessageToInputDeviceListenersLocked(1, i2);
                }
            }
        }
    }

    private static final class InputDeviceListenerDelegate extends Handler {
        static final int MSG_DEVICE_ADDED = 1;
        static final int MSG_DEVICE_CHANGED = 3;
        static final int MSG_DEVICE_REMOVED = 2;
        public final InputManager.InputDeviceListener mListener;

        InputDeviceListenerDelegate(InputManager.InputDeviceListener inputDeviceListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = inputDeviceListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                this.mListener.onInputDeviceAdded(message.arg1);
            } else if (i == 2) {
                this.mListener.onInputDeviceRemoved(message.arg1);
            } else {
                if (i != 3) {
                    return;
                }
                this.mListener.onInputDeviceChanged(message.arg1);
            }
        }
    }

    private static boolean containsDeviceId(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            if (iArr[i2] == i) {
                return true;
            }
        }
        return false;
    }

    private void sendMessageToInputDeviceListenersLocked(int i, int i2) {
        int size = this.mInputDeviceListeners.size();
        for (int i3 = 0; i3 < size; i3++) {
            InputDeviceListenerDelegate inputDeviceListenerDelegate = this.mInputDeviceListeners.get(i3);
            inputDeviceListenerDelegate.sendMessage(inputDeviceListenerDelegate.obtainMessage(i, i2, 0));
        }
    }

    public void registerInputDeviceListener(InputManager.InputDeviceListener inputDeviceListener, Handler handler) {
        Objects.requireNonNull(inputDeviceListener, "listener must not be null");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            if (findInputDeviceListenerLocked(inputDeviceListener) < 0) {
                this.mInputDeviceListeners.add(new InputDeviceListenerDelegate(inputDeviceListener, handler));
            }
        }
    }

    public void unregisterInputDeviceListener(InputManager.InputDeviceListener inputDeviceListener) {
        if (inputDeviceListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputDeviceListeners) {
            int findInputDeviceListenerLocked = findInputDeviceListenerLocked(inputDeviceListener);
            if (findInputDeviceListenerLocked >= 0) {
                this.mInputDeviceListeners.get(findInputDeviceListenerLocked).removeCallbacksAndMessages(null);
                this.mInputDeviceListeners.remove(findInputDeviceListenerLocked);
            }
        }
    }

    private int findInputDeviceListenerLocked(InputManager.InputDeviceListener inputDeviceListener) {
        int size = this.mInputDeviceListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mInputDeviceListeners.get(i).mListener == inputDeviceListener) {
                return i;
            }
        }
        return -1;
    }

    public void semRegisterOnMultiFingerGestureListener(InputManager.SemOnMultiFingerGestureListener semOnMultiFingerGestureListener, Handler handler) {
        if (semOnMultiFingerGestureListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mMultiFingerGestureLock) {
            if (this.mMultiFingerGestureListener == null) {
                initializeMultiFingerGestureListenerLocked();
            }
            if (findOnMultiFingerGestureListenerLocked(semOnMultiFingerGestureListener) < 0) {
                this.mOnMultiFingerGestureListeners.add(new OnMultiFingerGestureListenerDelegate(semOnMultiFingerGestureListener, handler));
            }
        }
    }

    public void semUnregisterOnMultiFingerGestureListener(InputManager.SemOnMultiFingerGestureListener semOnMultiFingerGestureListener) {
        if (semOnMultiFingerGestureListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mMultiFingerGestureLock) {
            int findOnMultiFingerGestureListenerLocked = findOnMultiFingerGestureListenerLocked(semOnMultiFingerGestureListener);
            if (findOnMultiFingerGestureListenerLocked >= 0) {
                this.mOnMultiFingerGestureListeners.get(findOnMultiFingerGestureListenerLocked).removeCallbacksAndMessages(null);
                this.mOnMultiFingerGestureListeners.remove(findOnMultiFingerGestureListenerLocked);
            }
        }
    }

    public void notifyQuickAccess(int i, float f, float f2) {
        try {
            this.mIm.notifyQuickAccess(i, f, f2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnSwitchEventChangedListener(InputManager.SemOnSwitchEventChangedListener semOnSwitchEventChangedListener, Handler handler) {
        if (semOnSwitchEventChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mSwitchEventChangedLock) {
            if (this.mSwitchEventChangedListener == null) {
                initializeSwitchEventChangedListenerLocked();
            }
            if (findOnSwitchEventChangedListenerLocked(semOnSwitchEventChangedListener) < 0) {
                this.mOnSwitchEventChangedListeners.add(new OnSwitchEventChangedListenerDelegate(semOnSwitchEventChangedListener, handler));
            }
        }
    }

    public void semUnregisterOnSwitchEventChangedListener(InputManager.SemOnSwitchEventChangedListener semOnSwitchEventChangedListener) {
        if (semOnSwitchEventChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mSwitchEventChangedLock) {
            int findOnSwitchEventChangedListenerLocked = findOnSwitchEventChangedListenerLocked(semOnSwitchEventChangedListener);
            if (findOnSwitchEventChangedListenerLocked >= 0) {
                this.mOnSwitchEventChangedListeners.get(findOnSwitchEventChangedListenerLocked).removeCallbacksAndMessages(null);
                this.mOnSwitchEventChangedListeners.remove(findOnSwitchEventChangedListenerLocked);
            }
        }
    }

    public void updateDeviceToGamepadProfile(String str, int i) {
        try {
            this.mIm.updateDeviceToGamepadProfile(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeDeviceToGamepadProfile(String str) {
        try {
            this.mIm.removeDeviceToGamepadProfile(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllDeviceToGamepadProfile() {
        try {
            this.mIm.removeAllDeviceToGamepadProfile();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllGamepadProfiles() {
        try {
            this.mIm.removeAllGamepadProfiles();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeGamepadProfile(int i) {
        try {
            this.mIm.removeGamepadProfile(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setGamepadProfileName(int i, String str) {
        try {
            return this.mIm.setGamepadProfileName(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRemapGamepadButton(int i, int i2, int i3) {
        try {
            return this.mIm.setRemapGamepadButton(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRemapGamepadStick(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        try {
            return this.mIm.setRemapGamepadStick(i, i2, i3, z, z2, z3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getSupportButtonNStick() {
        try {
            return this.mIm.getSupportButtonNStick();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getGamepadProfile(int i) {
        try {
            return this.mIm.getGamepadProfile(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getGamepadProfileIds() {
        try {
            return this.mIm.getGamepadProfileIds();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void initializeSwitchEventChangedListenerLocked() {
        SwitchEventChangedListener switchEventChangedListener = new SwitchEventChangedListener();
        try {
            this.mIm.registerSwitchEventChangedListener(switchEventChangedListener);
            this.mSwitchEventChangedListener = switchEventChangedListener;
            this.mOnSwitchEventChangedListeners = new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findOnSwitchEventChangedListenerLocked(InputManager.SemOnSwitchEventChangedListener semOnSwitchEventChangedListener) {
        int size = this.mOnSwitchEventChangedListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnSwitchEventChangedListeners.get(i).mListener == semOnSwitchEventChangedListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchEventChanged(int i, int i2, int i3, int i4) {
        if (debug()) {
            Log.d(TAG, "switch event change");
        }
        synchronized (this.mSwitchEventChangedLock) {
            int size = this.mOnSwitchEventChangedListeners.size();
            for (int i5 = 0; i5 < size; i5++) {
                this.mOnSwitchEventChangedListeners.get(i5).sendSwitchEventChanged(i, i2, i3, i4);
            }
        }
    }

    private final class SwitchEventChangedListener extends ISwitchEventChangedListener.Stub {
        private SwitchEventChangedListener() {
        }

        @Override // android.hardware.input.ISwitchEventChangedListener
        public void onSwitchEventChanged(int i, int i2, int i3, int i4) {
            InputManagerGlobal.this.onSwitchEventChanged(i, i2, i3, i4);
        }
    }

    private static final class OnSwitchEventChangedListenerDelegate extends Handler {
        private static final int MSG_SWITCH_EVENT_CHANGED = 0;
        public final InputManager.SemOnSwitchEventChangedListener mListener;

        public OnSwitchEventChangedListenerDelegate(InputManager.SemOnSwitchEventChangedListener semOnSwitchEventChangedListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = semOnSwitchEventChangedListener;
        }

        public void sendSwitchEventChanged(int i, int i2, int i3, int i4) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            obtain.argi4 = i4;
            obtainMessage(0, obtain).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                this.mListener.onSwitchEventChanged(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4);
            }
        }
    }

    private void initializeMultiFingerGestureListenerLocked() {
        MultiFingerGestureListener multiFingerGestureListener = new MultiFingerGestureListener();
        try {
            this.mIm.registerMultiFingerGestureListener(multiFingerGestureListener);
            this.mMultiFingerGestureListener = multiFingerGestureListener;
            this.mOnMultiFingerGestureListeners = new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findOnMultiFingerGestureListenerLocked(InputManager.SemOnMultiFingerGestureListener semOnMultiFingerGestureListener) {
        List<OnMultiFingerGestureListenerDelegate> list = this.mOnMultiFingerGestureListeners;
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnMultiFingerGestureListeners.get(i).mListener == semOnMultiFingerGestureListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMultiFingerGesture(int i, int i2) {
        if (debug()) {
            Log.d(TAG, "multi finger gesture.");
        }
        synchronized (this.mMultiFingerGestureLock) {
            int size = this.mOnMultiFingerGestureListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnMultiFingerGestureListenerDelegate onMultiFingerGestureListenerDelegate = this.mOnMultiFingerGestureListeners.get(i3);
                onMultiFingerGestureListenerDelegate.sendMessage(onMultiFingerGestureListenerDelegate.obtainMessage(1, i, i2));
            }
        }
    }

    private final class MultiFingerGestureListener extends IMultiFingerGestureListener.Stub {
        private MultiFingerGestureListener() {
        }

        @Override // android.hardware.input.IMultiFingerGestureListener
        public void onMultiFingerGesture(int i, int i2) {
            InputManagerGlobal.this.onMultiFingerGesture(i, i2);
        }
    }

    private static final class OnMultiFingerGestureListenerDelegate extends Handler {
        public final InputManager.SemOnMultiFingerGestureListener mListener;

        public OnMultiFingerGestureListenerDelegate(InputManager.SemOnMultiFingerGestureListener semOnMultiFingerGestureListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = semOnMultiFingerGestureListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            this.mListener.onMultiFingerGesture(message.arg1, message.arg2);
        }
    }

    public void semSetWakeKeyDynamically(String str, boolean z, String str2) {
        try {
            this.mIm.setWakeKeyDynamically(str, z, str2);
        } catch (RemoteException unused) {
        }
    }

    public int semGetLidState() {
        try {
            return this.mIm.getLidState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnLidStateChangedListener(InputManager.SemOnLidStateChangedListener semOnLidStateChangedListener, Handler handler) {
        if (semOnLidStateChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mLidStateLock) {
            if (this.mSemOnLidStateChangedListeners == null) {
                initializeLidStateListenerLocked();
            }
            if (findSemOnLidStateChangedListenerLocked(semOnLidStateChangedListener) < 0) {
                this.mSemOnLidStateChangedListeners.add(new SemOnLidStateChangedListenerDelegate(semOnLidStateChangedListener, handler));
            }
        }
    }

    public void semUnregisterOnLidStateChangedListener(InputManager.SemOnLidStateChangedListener semOnLidStateChangedListener) {
        if (semOnLidStateChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mLidStateLock) {
            int findSemOnLidStateChangedListenerLocked = findSemOnLidStateChangedListenerLocked(semOnLidStateChangedListener);
            if (findSemOnLidStateChangedListenerLocked >= 0) {
                this.mSemOnLidStateChangedListeners.remove(findSemOnLidStateChangedListenerLocked).removeCallbacksAndMessages(null);
            }
        }
    }

    public int[] getInputDeviceIds() {
        int[] iArr;
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int size = this.mInputDevices.size();
            iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = this.mInputDevices.keyAt(i);
            }
        }
        return iArr;
    }

    public void enableInputDevice(int i) {
        try {
            this.mIm.enableInputDevice(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Could not enable input device with id = " + i);
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableInputDevice(int i) {
        try {
            this.mIm.disableInputDevice(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Could not disable input device with id = " + i);
            throw e.rethrowFromSystemServer();
        }
    }

    public void controlSpenWithToken(IBinder iBinder, boolean z) {
        try {
            this.mIm.controlSpenWithToken(iBinder, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Could not control sec_e-pen device with token = " + iBinder + " " + z);
            throw e.rethrowFromSystemServer();
        }
    }

    InputDevice getInputDeviceByDescriptor(String str) {
        Objects.requireNonNull(str, "descriptor must not be null.");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int size = this.mInputDevices.size();
            for (int i = 0; i < size; i++) {
                InputDevice valueAt = this.mInputDevices.valueAt(i);
                if (valueAt == null) {
                    try {
                        valueAt = this.mIm.getInputDevice(this.mInputDevices.keyAt(i));
                        if (valueAt == null) {
                            continue;
                        } else {
                            this.mInputDevices.setValueAt(i, valueAt);
                        }
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                if (str.equals(valueAt.getDescriptor())) {
                    return valueAt;
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
                InputDevice inputDevice = getInputDevice(this.mInputDevices.keyAt(i));
                if (inputDevice != null && inputDevice.getAssociatedDisplayId() == display.getDisplayId() && inputDevice.getHostUsiVersion() != null) {
                    return inputDevice.getHostUsiVersion();
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
        LidStateChangedListener lidStateChangedListener = new LidStateChangedListener();
        try {
            this.mIm.registerLidStateChangedListener(lidStateChangedListener);
            this.mLidStateChangedListener = lidStateChangedListener;
            this.mSemOnLidStateChangedListeners = new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findSemOnLidStateChangedListenerLocked(InputManager.SemOnLidStateChangedListener semOnLidStateChangedListener) {
        List<SemOnLidStateChangedListenerDelegate> list = this.mSemOnLidStateChangedListeners;
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (this.mSemOnLidStateChangedListeners.get(i).mListener == semOnLidStateChangedListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLidStateChanged(long j, boolean z) {
        if (debug()) {
            Log.d(TAG, "Received lid state changed: whenNanos=" + j + ", lidOpen=" + z);
        }
        synchronized (this.mLidStateLock) {
            int size = this.mSemOnLidStateChangedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mSemOnLidStateChangedListeners.get(i).sendLidStateChanged(j, z);
            }
        }
    }

    private final class LidStateChangedListener extends ISemLidStateChangedListener.Stub {
        private LidStateChangedListener() {
        }

        @Override // android.hardware.input.ISemLidStateChangedListener
        public void onLidStateChanged(long j, boolean z) {
            InputManagerGlobal.this.onLidStateChanged(j, z);
        }
    }

    private static final class SemOnLidStateChangedListenerDelegate extends Handler {
        private static final int MSG_LID_STATE_CHANGED = 0;
        public final InputManager.SemOnLidStateChangedListener mListener;

        public SemOnLidStateChangedListenerDelegate(InputManager.SemOnLidStateChangedListener semOnLidStateChangedListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = semOnLidStateChangedListener;
        }

        public void sendLidStateChanged(long j, boolean z) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = (int) j;
            obtain.argi2 = (int) (j >> 32);
            obtain.arg1 = Boolean.valueOf(z);
            obtainMessage(0, obtain).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            this.mListener.onLidStateChanged((r6.argi1 & 4294967295L) | (r6.argi2 << 32), !((Boolean) ((SomeArgs) message.obj).arg1).booleanValue() ? 1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTabletModeChanged(long j, boolean z) {
        if (debug()) {
            Log.d(TAG, "Received tablet mode changed: whenNanos=" + j + ", inTabletMode=" + z);
        }
        synchronized (this.mOnTabletModeChangedListeners) {
            int size = this.mOnTabletModeChangedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mOnTabletModeChangedListeners.get(i).sendTabletModeChanged(j, z);
            }
        }
    }

    private final class TabletModeChangedListener extends ITabletModeChangedListener.Stub {
        private TabletModeChangedListener() {
        }

        @Override // android.hardware.input.ITabletModeChangedListener
        public void onTabletModeChanged(long j, boolean z) {
            InputManagerGlobal.this.onTabletModeChanged(j, z);
        }
    }

    private static final class OnTabletModeChangedListenerDelegate extends Handler {
        private static final int MSG_TABLET_MODE_CHANGED = 0;
        public final InputManager.OnTabletModeChangedListener mListener;

        OnTabletModeChangedListenerDelegate(InputManager.OnTabletModeChangedListener onTabletModeChangedListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = onTabletModeChangedListener;
        }

        public void sendTabletModeChanged(long j, boolean z) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = (int) j;
            obtain.argi2 = (int) (j >> 32);
            obtain.arg1 = Boolean.valueOf(z);
            obtainMessage(0, obtain).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                this.mListener.onTabletModeChanged((r6.argi1 & 4294967295L) | (r6.argi2 << 32), ((Boolean) ((SomeArgs) message.obj).arg1).booleanValue());
            }
        }
    }

    void registerOnTabletModeChangedListener(InputManager.OnTabletModeChangedListener onTabletModeChangedListener, Handler handler) {
        Objects.requireNonNull(onTabletModeChangedListener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            if (this.mOnTabletModeChangedListeners.isEmpty()) {
                initializeTabletModeListenerLocked();
            }
            if (findOnTabletModeChangedListenerLocked(onTabletModeChangedListener) < 0) {
                this.mOnTabletModeChangedListeners.add(new OnTabletModeChangedListenerDelegate(onTabletModeChangedListener, handler));
            }
        }
    }

    void unregisterOnTabletModeChangedListener(InputManager.OnTabletModeChangedListener onTabletModeChangedListener) {
        Objects.requireNonNull(onTabletModeChangedListener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            int findOnTabletModeChangedListenerLocked = findOnTabletModeChangedListenerLocked(onTabletModeChangedListener);
            if (findOnTabletModeChangedListenerLocked >= 0) {
                this.mOnTabletModeChangedListeners.remove(findOnTabletModeChangedListenerLocked).removeCallbacksAndMessages(null);
            }
        }
    }

    private void initializeTabletModeListenerLocked() {
        try {
            this.mIm.registerTabletModeChangedListener(new TabletModeChangedListener());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findOnTabletModeChangedListenerLocked(InputManager.OnTabletModeChangedListener onTabletModeChangedListener) {
        int size = this.mOnTabletModeChangedListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnTabletModeChangedListeners.get(i).mListener == onTabletModeChangedListener) {
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

        InputDeviceBatteryListenerDelegate(InputManager.InputDeviceBatteryListener inputDeviceBatteryListener, Executor executor) {
            this.mListener = inputDeviceBatteryListener;
            this.mExecutor = executor;
        }

        void notifyBatteryStateChanged(final IInputDeviceBatteryState iInputDeviceBatteryState) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.input.InputManagerGlobal$InputDeviceBatteryListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputManagerGlobal.InputDeviceBatteryListenerDelegate.this.lambda$notifyBatteryStateChanged$0(iInputDeviceBatteryState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyBatteryStateChanged$0(IInputDeviceBatteryState iInputDeviceBatteryState) {
            this.mListener.onBatteryStateChanged(iInputDeviceBatteryState.deviceId, iInputDeviceBatteryState.updateTime, new LocalBatteryState(iInputDeviceBatteryState.isPresent, iInputDeviceBatteryState.status, iInputDeviceBatteryState.capacity));
        }
    }

    public void addInputDeviceBatteryListener(int i, Executor executor, InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(inputDeviceBatteryListener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
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
                    if (Objects.equals(inputDeviceBatteryListener, registeredBatteryListeners.mDelegates.get(i2).mListener)) {
                        throw new IllegalArgumentException("Attempting to register an InputDeviceBatteryListener that has already been registered for deviceId: " + i);
                    }
                }
            }
            InputDeviceBatteryListenerDelegate inputDeviceBatteryListenerDelegate = new InputDeviceBatteryListenerDelegate(inputDeviceBatteryListener, executor);
            registeredBatteryListeners.mDelegates.add(inputDeviceBatteryListenerDelegate);
            if (registeredBatteryListeners.mInputDeviceBatteryState != null) {
                inputDeviceBatteryListenerDelegate.notifyBatteryStateChanged(registeredBatteryListeners.mInputDeviceBatteryState);
            }
        }
    }

    void removeInputDeviceBatteryListener(int i, InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        Objects.requireNonNull(inputDeviceBatteryListener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
            SparseArray<RegisteredBatteryListeners> sparseArray = this.mBatteryListeners;
            if (sparseArray == null) {
                return;
            }
            RegisteredBatteryListeners registeredBatteryListeners = sparseArray.get(i);
            if (registeredBatteryListeners == null) {
                return;
            }
            List<InputDeviceBatteryListenerDelegate> list = registeredBatteryListeners.mDelegates;
            int i2 = 0;
            while (i2 < list.size()) {
                if (Objects.equals(inputDeviceBatteryListener, list.get(i2).mListener)) {
                    list.remove(i2);
                } else {
                    i2++;
                }
            }
            if (list.isEmpty()) {
                this.mBatteryListeners.remove(i);
                try {
                    this.mIm.unregisterBatteryListener(i, this.mInputDeviceBatteryListener);
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
        private LocalInputDeviceBatteryListener() {
        }

        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(IInputDeviceBatteryState iInputDeviceBatteryState) {
            synchronized (InputManagerGlobal.this.mBatteryListenersLock) {
                if (InputManagerGlobal.this.mBatteryListeners == null) {
                    return;
                }
                RegisteredBatteryListeners registeredBatteryListeners = (RegisteredBatteryListeners) InputManagerGlobal.this.mBatteryListeners.get(iInputDeviceBatteryState.deviceId);
                if (registeredBatteryListeners == null) {
                    return;
                }
                registeredBatteryListeners.mInputDeviceBatteryState = iInputDeviceBatteryState;
                int size = registeredBatteryListeners.mDelegates.size();
                for (int i = 0; i < size; i++) {
                    registeredBatteryListeners.mDelegates.get(i).notifyBatteryStateChanged(registeredBatteryListeners.mInputDeviceBatteryState);
                }
            }
        }
    }

    public BatteryState getInputDeviceBatteryState(int i, boolean z) {
        if (!z) {
            return new LocalBatteryState();
        }
        try {
            IInputDeviceBatteryState batteryState = this.mIm.getBatteryState(i);
            return new LocalBatteryState(batteryState.isPresent, batteryState.status, batteryState.capacity);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class LocalBatteryState extends BatteryState {
        private final float mCapacity;
        private final boolean mIsPresent;
        private final int mStatus;

        LocalBatteryState() {
            this(false, 1, Float.NaN);
        }

        LocalBatteryState(boolean z, int i, float f) {
            this.mIsPresent = z;
            this.mStatus = i;
            this.mCapacity = f;
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

        KeyboardBacklightListenerDelegate(InputManager.KeyboardBacklightListener keyboardBacklightListener, Executor executor) {
            this.mListener = keyboardBacklightListener;
            this.mExecutor = executor;
        }

        void notifyKeyboardBacklightChange(final int i, final IKeyboardBacklightState iKeyboardBacklightState, final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.input.InputManagerGlobal$KeyboardBacklightListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputManagerGlobal.KeyboardBacklightListenerDelegate.this.lambda$notifyKeyboardBacklightChange$0(i, iKeyboardBacklightState, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyKeyboardBacklightChange$0(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z) {
            this.mListener.onKeyboardBacklightChanged(i, new LocalKeyboardBacklightState(iKeyboardBacklightState.brightnessLevel, iKeyboardBacklightState.maxBrightnessLevel), z);
        }
    }

    private class LocalKeyboardBacklightListener extends IKeyboardBacklightListener.Stub {
        private LocalKeyboardBacklightListener() {
        }

        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z) {
            synchronized (InputManagerGlobal.this.mKeyboardBacklightListenerLock) {
                if (InputManagerGlobal.this.mKeyboardBacklightListeners == null) {
                    return;
                }
                int size = InputManagerGlobal.this.mKeyboardBacklightListeners.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((KeyboardBacklightListenerDelegate) InputManagerGlobal.this.mKeyboardBacklightListeners.get(i2)).notifyKeyboardBacklightChange(i, iKeyboardBacklightState, z);
                }
            }
        }
    }

    private static final class LocalKeyboardBacklightState extends KeyboardBacklightState {
        private final int mBrightnessLevel;
        private final int mMaxBrightnessLevel;

        LocalKeyboardBacklightState(int i, int i2) {
            this.mBrightnessLevel = i;
            this.mMaxBrightnessLevel = i2;
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

    void registerKeyboardBacklightListener(Executor executor, InputManager.KeyboardBacklightListener keyboardBacklightListener) throws IllegalArgumentException {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(keyboardBacklightListener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            if (this.mKeyboardBacklightListener == null) {
                this.mKeyboardBacklightListeners = new ArrayList<>();
                LocalKeyboardBacklightListener localKeyboardBacklightListener = new LocalKeyboardBacklightListener();
                this.mKeyboardBacklightListener = localKeyboardBacklightListener;
                try {
                    this.mIm.registerKeyboardBacklightListener(localKeyboardBacklightListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int size = this.mKeyboardBacklightListeners.size();
            for (int i = 0; i < size; i++) {
                if (this.mKeyboardBacklightListeners.get(i).mListener == keyboardBacklightListener) {
                    throw new IllegalArgumentException("Listener has already been registered!");
                }
            }
            this.mKeyboardBacklightListeners.add(new KeyboardBacklightListenerDelegate(keyboardBacklightListener, executor));
        }
    }

    void unregisterKeyboardBacklightListener(final InputManager.KeyboardBacklightListener keyboardBacklightListener) {
        Objects.requireNonNull(keyboardBacklightListener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            ArrayList<KeyboardBacklightListenerDelegate> arrayList = this.mKeyboardBacklightListeners;
            if (arrayList == null) {
                return;
            }
            arrayList.removeIf(new Predicate() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return InputManagerGlobal.lambda$unregisterKeyboardBacklightListener$1(InputManager.KeyboardBacklightListener.this, (InputManagerGlobal.KeyboardBacklightListenerDelegate) obj);
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

    static /* synthetic */ boolean lambda$unregisterKeyboardBacklightListener$1(InputManager.KeyboardBacklightListener keyboardBacklightListener, KeyboardBacklightListenerDelegate keyboardBacklightListenerDelegate) {
        return keyboardBacklightListenerDelegate.mListener == keyboardBacklightListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StickyModifierStateListenerDelegate {
        final Executor mExecutor;
        final InputManager.StickyModifierStateListener mListener;

        StickyModifierStateListenerDelegate(InputManager.StickyModifierStateListener stickyModifierStateListener, Executor executor) {
            this.mListener = stickyModifierStateListener;
            this.mExecutor = executor;
        }

        void notifyStickyModifierStateChange(final int i, final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.input.InputManagerGlobal$StickyModifierStateListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputManagerGlobal.StickyModifierStateListenerDelegate.this.lambda$notifyStickyModifierStateChange$0(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStickyModifierStateChange$0(int i, int i2) {
            this.mListener.onStickyModifierStateChanged(new LocalStickyModifierState(i, i2));
        }
    }

    private class LocalStickyModifierStateListener extends IStickyModifierStateListener.Stub {
        private LocalStickyModifierStateListener() {
        }

        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int i, int i2) {
            synchronized (InputManagerGlobal.this.mStickyModifierStateListenerLock) {
                if (InputManagerGlobal.this.mStickyModifierStateListeners == null) {
                    return;
                }
                int size = InputManagerGlobal.this.mStickyModifierStateListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((StickyModifierStateListenerDelegate) InputManagerGlobal.this.mStickyModifierStateListeners.get(i3)).notifyStickyModifierStateChange(i, i2);
                }
            }
        }
    }

    private static final class LocalStickyModifierState extends StickyModifierState {
        private final int mLockedModifierState;
        private final int mModifierState;

        LocalStickyModifierState(int i, int i2) {
            this.mModifierState = i;
            this.mLockedModifierState = i2;
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

    void registerStickyModifierStateListener(Executor executor, InputManager.StickyModifierStateListener stickyModifierStateListener) throws IllegalArgumentException {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(stickyModifierStateListener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            if (this.mStickyModifierStateListener == null) {
                this.mStickyModifierStateListeners = new ArrayList<>();
                LocalStickyModifierStateListener localStickyModifierStateListener = new LocalStickyModifierStateListener();
                this.mStickyModifierStateListener = localStickyModifierStateListener;
                try {
                    this.mIm.registerStickyModifierStateListener(localStickyModifierStateListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int size = this.mStickyModifierStateListeners.size();
            for (int i = 0; i < size; i++) {
                if (this.mStickyModifierStateListeners.get(i).mListener == stickyModifierStateListener) {
                    throw new IllegalArgumentException("Listener has already been registered!");
                }
            }
            this.mStickyModifierStateListeners.add(new StickyModifierStateListenerDelegate(stickyModifierStateListener, executor));
        }
    }

    void unregisterStickyModifierStateListener(final InputManager.StickyModifierStateListener stickyModifierStateListener) {
        Objects.requireNonNull(stickyModifierStateListener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            ArrayList<StickyModifierStateListenerDelegate> arrayList = this.mStickyModifierStateListeners;
            if (arrayList == null) {
                return;
            }
            arrayList.removeIf(new Predicate() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return InputManagerGlobal.lambda$unregisterStickyModifierStateListener$2(InputManager.StickyModifierStateListener.this, (InputManagerGlobal.StickyModifierStateListenerDelegate) obj);
                }
            });
            if (this.mStickyModifierStateListeners.isEmpty()) {
                try {
                    this.mIm.unregisterStickyModifierStateListener(this.mStickyModifierStateListener);
                    this.mStickyModifierStateListeners = null;
                    this.mStickyModifierStateListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterStickyModifierStateListener$2(InputManager.StickyModifierStateListener stickyModifierStateListener, StickyModifierStateListenerDelegate stickyModifierStateListenerDelegate) {
        return stickyModifierStateListenerDelegate.mListener == stickyModifierStateListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class KeyGestureEventListenerDelegate {
        final Executor mExecutor;
        final InputManager.KeyGestureEventListener mListener;

        KeyGestureEventListenerDelegate(InputManager.KeyGestureEventListener keyGestureEventListener, Executor executor) {
            this.mListener = keyGestureEventListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKeyGestureEvent$0(KeyGestureEvent keyGestureEvent) {
            this.mListener.onKeyGestureEvent(keyGestureEvent);
        }

        void onKeyGestureEvent(final KeyGestureEvent keyGestureEvent) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.input.InputManagerGlobal$KeyGestureEventListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputManagerGlobal.KeyGestureEventListenerDelegate.this.lambda$onKeyGestureEvent$0(keyGestureEvent);
                }
            });
        }
    }

    private class LocalKeyGestureEventListener extends IKeyGestureEventListener.Stub {
        private LocalKeyGestureEventListener() {
        }

        @Override // android.hardware.input.IKeyGestureEventListener
        public void onKeyGestureEvent(AidlKeyGestureEvent aidlKeyGestureEvent) {
            synchronized (InputManagerGlobal.this.mKeyGestureEventListenerLock) {
                if (InputManagerGlobal.this.mKeyGestureEventListeners == null) {
                    return;
                }
                int size = InputManagerGlobal.this.mKeyGestureEventListeners.size();
                KeyGestureEvent keyGestureEvent = new KeyGestureEvent(aidlKeyGestureEvent);
                for (int i = 0; i < size; i++) {
                    ((KeyGestureEventListenerDelegate) InputManagerGlobal.this.mKeyGestureEventListeners.get(i)).onKeyGestureEvent(keyGestureEvent);
                }
            }
        }
    }

    void registerKeyGestureEventListener(Executor executor, InputManager.KeyGestureEventListener keyGestureEventListener) throws IllegalArgumentException {
        Objects.requireNonNull(executor, "executor should not be null");
        Objects.requireNonNull(keyGestureEventListener, "listener should not be null");
        synchronized (this.mKeyGestureEventListenerLock) {
            if (this.mKeyGestureEventListener == null) {
                this.mKeyGestureEventListeners = new ArrayList<>();
                LocalKeyGestureEventListener localKeyGestureEventListener = new LocalKeyGestureEventListener();
                this.mKeyGestureEventListener = localKeyGestureEventListener;
                try {
                    this.mIm.registerKeyGestureEventListener(localKeyGestureEventListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int size = this.mKeyGestureEventListeners.size();
            for (int i = 0; i < size; i++) {
                if (this.mKeyGestureEventListeners.get(i).mListener == keyGestureEventListener) {
                    throw new IllegalArgumentException("Listener has already been registered!");
                }
            }
            this.mKeyGestureEventListeners.add(new KeyGestureEventListenerDelegate(keyGestureEventListener, executor));
        }
    }

    void unregisterKeyGestureEventListener(final InputManager.KeyGestureEventListener keyGestureEventListener) {
        Objects.requireNonNull(keyGestureEventListener, "listener should not be null");
        synchronized (this.mKeyGestureEventListenerLock) {
            ArrayList<KeyGestureEventListenerDelegate> arrayList = this.mKeyGestureEventListeners;
            if (arrayList == null) {
                return;
            }
            arrayList.removeIf(new Predicate() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return InputManagerGlobal.lambda$unregisterKeyGestureEventListener$3(InputManager.KeyGestureEventListener.this, (InputManagerGlobal.KeyGestureEventListenerDelegate) obj);
                }
            });
            if (this.mKeyGestureEventListeners.isEmpty()) {
                try {
                    this.mIm.unregisterKeyGestureEventListener(this.mKeyGestureEventListener);
                    this.mKeyGestureEventListeners = null;
                    this.mKeyGestureEventListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterKeyGestureEventListener$3(InputManager.KeyGestureEventListener keyGestureEventListener, KeyGestureEventListenerDelegate keyGestureEventListenerDelegate) {
        return keyGestureEventListenerDelegate.mListener == keyGestureEventListener;
    }

    private class LocalKeyGestureHandler extends IKeyGestureHandler.Stub {
        private LocalKeyGestureHandler() {
        }

        @Override // android.hardware.input.IKeyGestureHandler
        public void handleKeyGesture(AidlKeyGestureEvent aidlKeyGestureEvent, IBinder iBinder) {
            synchronized (InputManagerGlobal.this.mKeyGesturesToHandlerMap) {
                InputManager.KeyGestureEventHandler keyGestureEventHandler = (InputManager.KeyGestureEventHandler) InputManagerGlobal.this.mKeyGesturesToHandlerMap.get(aidlKeyGestureEvent.gestureType);
                if (keyGestureEventHandler == null) {
                    Log.w(InputManagerGlobal.TAG, "Key gesture event " + aidlKeyGestureEvent.gestureType + " occurred without a registered handler!");
                    return;
                }
                keyGestureEventHandler.handleKeyGestureEvent(new KeyGestureEvent(aidlKeyGestureEvent), iBinder);
            }
        }
    }

    void registerKeyGestureEventHandler(List<Integer> list, InputManager.KeyGestureEventHandler keyGestureEventHandler) throws IllegalArgumentException {
        Objects.requireNonNull(list, "List of gestures should not be null");
        Objects.requireNonNull(keyGestureEventHandler, "handler should not be null");
        if (list.isEmpty()) {
            throw new IllegalArgumentException("No key gestures provided!");
        }
        synchronized (this.mKeyGesturesToHandlerMap) {
            IntArray intArray = new IntArray(list.size() + this.mKeyGesturesToHandlerMap.size());
            for (int i = 0; i < this.mKeyGesturesToHandlerMap.size(); i++) {
                if (this.mKeyGesturesToHandlerMap.valueAt(i) == keyGestureEventHandler) {
                    throw new IllegalArgumentException("Handler has already been registered!");
                }
                intArray.add(this.mKeyGesturesToHandlerMap.keyAt(i));
            }
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (this.mKeyGesturesToHandlerMap.contains(intValue)) {
                    throw new IllegalArgumentException("Key gesture " + intValue + " is already registered by another handler!");
                }
                intArray.add(intValue);
            }
            try {
                IKeyGestureHandler iKeyGestureHandler = this.mKeyGestureHandler;
                if (iKeyGestureHandler != null) {
                    this.mIm.unregisterKeyGestureHandler(iKeyGestureHandler);
                } else {
                    this.mKeyGestureHandler = new LocalKeyGestureHandler();
                }
                this.mIm.registerKeyGestureHandler(intArray.toArray(), this.mKeyGestureHandler);
                Iterator<Integer> it2 = list.iterator();
                while (it2.hasNext()) {
                    this.mKeyGesturesToHandlerMap.put(it2.next().intValue(), keyGestureEventHandler);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    void unregisterKeyGestureEventHandler(InputManager.KeyGestureEventHandler keyGestureEventHandler) {
        Objects.requireNonNull(keyGestureEventHandler, "handler should not be null");
        synchronized (this.mKeyGesturesToHandlerMap) {
            if (this.mKeyGestureHandler == null) {
                return;
            }
            for (int size = this.mKeyGesturesToHandlerMap.size() - 1; size >= 0; size--) {
                if (this.mKeyGesturesToHandlerMap.valueAt(size) == keyGestureEventHandler) {
                    this.mKeyGesturesToHandlerMap.removeAt(size);
                }
            }
            if (this.mKeyGesturesToHandlerMap.size() == 0) {
                try {
                    this.mIm.unregisterKeyGestureHandler(this.mKeyGestureHandler);
                    this.mKeyGestureHandler = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private class LocalKeyEventActivityListener extends IKeyEventActivityListener.Stub {
        private LocalKeyEventActivityListener() {
        }

        @Override // android.hardware.input.IKeyEventActivityListener
        public void onKeyEventActivity() {
            synchronized (InputManagerGlobal.this.mKeyEventActivityLock) {
                int size = InputManagerGlobal.this.mKeyEventActivityListeners.size();
                for (int i = 0; i < size; i++) {
                    ((InputManager.KeyEventActivityListener) InputManagerGlobal.this.mKeyEventActivityListeners.get(i)).onKeyEventActivity();
                }
            }
        }
    }

    boolean registerKeyEventActivityListener(InputManager.KeyEventActivityListener keyEventActivityListener) {
        boolean z;
        Objects.requireNonNull(keyEventActivityListener, "listener should not be null");
        synchronized (this.mKeyEventActivityLock) {
            if (this.mKeyEventActivityListener == null) {
                this.mKeyEventActivityListeners = new ArrayList<>();
                LocalKeyEventActivityListener localKeyEventActivityListener = new LocalKeyEventActivityListener();
                this.mKeyEventActivityListener = localKeyEventActivityListener;
                try {
                    z = this.mIm.registerKeyEventActivityListener(localKeyEventActivityListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                z = false;
            }
            if (this.mKeyEventActivityListeners.contains(keyEventActivityListener)) {
                throw new IllegalArgumentException("Listener has already been registered!");
            }
            this.mKeyEventActivityListeners.add(keyEventActivityListener);
        }
        return z;
    }

    boolean unregisterKeyEventActivityListener(InputManager.KeyEventActivityListener keyEventActivityListener) {
        Objects.requireNonNull(keyEventActivityListener, "listener should not be null");
        synchronized (this.mKeyEventActivityLock) {
            ArrayList<InputManager.KeyEventActivityListener> arrayList = this.mKeyEventActivityListeners;
            boolean z = true;
            if (arrayList == null) {
                return true;
            }
            arrayList.remove(keyEventActivityListener);
            if (this.mKeyEventActivityListeners.isEmpty()) {
                try {
                    z = this.mIm.unregisterKeyEventActivityListener(this.mKeyEventActivityListener);
                    this.mKeyEventActivityListeners = null;
                    this.mKeyEventActivityListener = null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return z;
        }
    }

    public void setKeyboardLayoutOverrideForInputDevice(InputDeviceIdentifier inputDeviceIdentifier, String str) {
        Objects.requireNonNull(inputDeviceIdentifier, "identifier should not be null");
        Objects.requireNonNull(str, "keyboardLayoutDescriptor should not be null");
        try {
            this.mIm.setKeyboardLayoutOverrideForInputDevice(inputDeviceIdentifier, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public KeyboardLayout[] getKeyboardLayoutsForInputDevice(InputDeviceIdentifier inputDeviceIdentifier) {
        return new KeyboardLayout[0];
    }

    public SensorManager getInputDeviceSensorManager(int i) {
        SensorManager sensorManager;
        synchronized (this.mInputDeviceListeners) {
            if (this.mInputDeviceSensorManager == null) {
                this.mInputDeviceSensorManager = new InputDeviceSensorManager(this);
            }
            sensorManager = this.mInputDeviceSensorManager.getSensorManager(i);
        }
        return sensorManager;
    }

    InputSensorInfo[] getSensorList(int i) {
        try {
            return this.mIm.getSensorList(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean enableSensor(int i, int i2, int i3, int i4) {
        try {
            return this.mIm.enableSensor(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void disableSensor(int i, int i2) {
        try {
            this.mIm.disableSensor(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean flushSensor(int i, int i2) {
        try {
            return this.mIm.flushSensor(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean registerSensorListener(IInputSensorEventListener iInputSensorEventListener) {
        try {
            return this.mIm.registerSensorListener(iInputSensorEventListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterSensorListener(IInputSensorEventListener iInputSensorEventListener) {
        try {
            this.mIm.unregisterSensorListener(iInputSensorEventListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LightsManager getInputDeviceLightsManager(int i) {
        return new InputDeviceLightsManager(i);
    }

    List<Light> getLights(int i) {
        try {
            return this.mIm.getLights(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    LightState getLightState(int i, Light light) {
        try {
            return this.mIm.getLightState(i, light.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void requestLights(int i, LightsRequest lightsRequest, IBinder iBinder) {
        try {
            List<Integer> lights = lightsRequest.getLights();
            int size = lights.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = lights.get(i2).intValue();
            }
            this.mIm.setLightStates(i, iArr, (LightState[]) lightsRequest.getLightStates().toArray(new LightState[0]), iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void openLightSession(int i, String str, IBinder iBinder) {
        try {
            this.mIm.openLightSession(i, str, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void closeLightSession(int i, IBinder iBinder) {
        try {
            this.mIm.closeLightSession(i, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Vibrator getInputDeviceVibrator(int i, int i2) {
        return new InputDeviceVibrator(i, i2);
    }

    public VibratorManager getInputDeviceVibratorManager(int i) {
        return new InputDeviceVibratorManager(i);
    }

    int[] getVibratorIds(int i) {
        try {
            return this.mIm.getVibratorIds(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void vibrate(int i, VibrationEffect vibrationEffect, IBinder iBinder) {
        try {
            this.mIm.vibrate(i, vibrationEffect, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void vibrate(int i, CombinedVibration combinedVibration, IBinder iBinder) {
        try {
            this.mIm.vibrateCombined(i, combinedVibration, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void cancelVibrate(int i, IBinder iBinder) {
        try {
            this.mIm.cancelVibrate(i, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isVibrating(int i) {
        try {
            return this.mIm.isVibrating(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        try {
            return this.mIm.registerVibratorStateListener(i, iVibratorStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) {
        try {
            return this.mIm.unregisterVibratorStateListener(i, iVibratorStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean[] deviceHasKeys(int[] iArr) {
        return deviceHasKeys(-1, iArr);
    }

    public boolean[] deviceHasKeys(int i, int[] iArr) {
        boolean[] zArr = new boolean[iArr.length];
        try {
            this.mIm.hasKeys(i, -256, iArr, zArr);
            return zArr;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getKeyCodeForKeyLocation(int i, int i2) {
        try {
            return this.mIm.getKeyCodeForKeyLocation(i, i2);
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

    public boolean injectInputEvent(InputEvent inputEvent, int i, int i2) {
        Objects.requireNonNull(inputEvent, "event must not be null");
        if (i != 0 && i != 2 && i != 1) {
            throw new IllegalArgumentException("mode is invalid");
        }
        try {
            return this.mIm.injectInputEventToTarget(inputEvent, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(InputEvent inputEvent, int i) {
        return injectInputEvent(inputEvent, i, -1);
    }

    public void setPointerIconType(int i) {
        Log.e(TAG, "setPointerIconType: Unsupported app usage!");
    }

    public void setCustomPointerIcon(PointerIcon pointerIcon) {
        Log.e(TAG, "setCustomPointerIcon: Unsupported app usage!");
    }

    public boolean setPointerIcon(PointerIcon pointerIcon, int i, int i2, int i3, IBinder iBinder) {
        try {
            return this.mIm.setPointerIcon(pointerIcon, i, i2, i3, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPointerCapture(IBinder iBinder, boolean z) {
        try {
            this.mIm.requestPointerCapture(iBinder, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputMonitor monitorGestureInput(String str, int i) {
        try {
            return this.mIm.monitorGestureInput(new Binder(), str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputMonitor monitorGestureInput(String str, int i, int i2) {
        try {
            return this.mIm.monitorGestureInputFiltered(new Binder(), str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputChannel monitorInput(String str, int i, int i2) {
        try {
            return this.mIm.monitorInputForBinder(str, i, i2);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void addUniqueIdAssociationByPort(String str, String str2) {
        try {
            this.mIm.addUniqueIdAssociationByPort(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUniqueIdAssociationByPort(String str) {
        try {
            this.mIm.removeUniqueIdAssociationByPort(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUniqueIdAssociationByDescriptor(String str, String str2) {
        try {
            this.mIm.addUniqueIdAssociationByDescriptor(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUniqueIdAssociationByDescriptor(String str) {
        try {
            this.mIm.removeUniqueIdAssociationByDescriptor(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getInputDeviceBluetoothAddress(int i) {
        try {
            return this.mIm.getInputDeviceBluetoothAddress(i);
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

    public void pilferPointers(IBinder iBinder) {
        try {
            this.mIm.pilferPointers(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRegisterOnPointerIconChangedListener(InputManager.SemOnPointerIconChangedListener semOnPointerIconChangedListener, Handler handler) {
        if (semOnPointerIconChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mPointerIconLock) {
            if (this.mPointerIconChangedListener == null) {
                initializePointerIconChangedListenerLocked();
            }
            if (findOnPointerIconChangedListenerLocked(semOnPointerIconChangedListener) < 0) {
                this.mOnPointerIconChangedListeners.add(new OnPointerIconChangedListenerDelegate(semOnPointerIconChangedListener, handler));
            }
        }
    }

    public void semUnregisterOnPointerIconChangedListener(InputManager.SemOnPointerIconChangedListener semOnPointerIconChangedListener) {
        if (semOnPointerIconChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mPointerIconLock) {
            int findOnPointerIconChangedListenerLocked = findOnPointerIconChangedListenerLocked(semOnPointerIconChangedListener);
            if (findOnPointerIconChangedListenerLocked >= 0) {
                this.mOnPointerIconChangedListeners.get(findOnPointerIconChangedListenerLocked).removeCallbacksAndMessages(null);
                this.mOnPointerIconChangedListeners.remove(findOnPointerIconChangedListenerLocked);
            }
        }
    }

    private void initializePointerIconChangedListenerLocked() {
        PointerIconChangedListener pointerIconChangedListener = new PointerIconChangedListener();
        try {
            this.mIm.registerPointerIconChangedListener(pointerIconChangedListener);
            this.mPointerIconChangedListener = pointerIconChangedListener;
            this.mOnPointerIconChangedListeners = new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    final int findOnPointerIconChangedListenerLocked(InputManager.SemOnPointerIconChangedListener semOnPointerIconChangedListener) {
        int size = this.mOnPointerIconChangedListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnPointerIconChangedListeners.get(i).mListener == semOnPointerIconChangedListener) {
                return i;
            }
        }
        return -1;
    }

    private final class PointerIconChangedListener extends IPointerIconChangedListener.Stub {
        private PointerIconChangedListener() {
        }

        @Override // android.hardware.input.IPointerIconChangedListener
        public void onPointerIconChanged(int i, PointerIcon pointerIcon) {
            InputManagerGlobal.this.onPointerIconChanged(i, pointerIcon);
        }
    }

    private static final class OnPointerIconChangedListenerDelegate extends Handler {
        public final InputManager.SemOnPointerIconChangedListener mListener;

        public OnPointerIconChangedListenerDelegate(InputManager.SemOnPointerIconChangedListener semOnPointerIconChangedListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = semOnPointerIconChangedListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            int i = message.arg1;
            PointerIcon pointerIcon = message.obj instanceof PointerIcon ? (PointerIcon) message.obj : null;
            if (pointerIcon == null) {
                this.mListener.onPointerIconChanged(i, null, 0.0f, 0.0f);
            } else {
                this.mListener.onPointerIconChanged(i, pointerIcon.getBitmap(), pointerIcon.getHotSpotX(), pointerIcon.getHotSpotY());
            }
        }
    }

    public void setDisplayIdForPointerIcon(int i) {
        try {
            Log.d(TAG, "setDisplayIdForPointerIcon = " + i);
            this.mIm.setDisplayIdForPointerIcon(i);
        } catch (RemoteException unused) {
        }
    }

    public void setIsStylusFromTouchpad(boolean z) {
        if (this.mIsStylusFromTouchpad != z) {
            this.mIsStylusFromTouchpad = z;
        }
    }

    public int getPointerIconType() {
        try {
            this.mPointerIconType = this.mIm.getPointerIconType();
            Log.d(TAG, "getPointerIconType = " + this.mPointerIconType);
        } catch (RemoteException unused) {
        }
        return this.mPointerIconType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPointerIconChanged(int i, PointerIcon pointerIcon) {
        if (debug()) {
            Log.d(TAG, "Received pointer icon changed.");
        }
        synchronized (this.mPointerIconLock) {
            int size = this.mOnPointerIconChangedListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnPointerIconChangedListenerDelegate onPointerIconChangedListenerDelegate = this.mOnPointerIconChangedListeners.get(i2);
                onPointerIconChangedListenerDelegate.sendMessage(onPointerIconChangedListenerDelegate.obtainMessage(1, i, 0, pointerIcon));
            }
            this.mPointerIconType = i;
            this.mPointerIcon = pointerIcon;
        }
    }

    public void setDragPointerInfo(IBinder iBinder, int i, int i2) {
        this.mDragToken = iBinder;
        this.mDeviceId = i;
        this.mPointerId = i2;
    }

    public void setDragPointerIcon(PointerIcon pointerIcon) {
        this.mDragPointerIcon = pointerIcon;
    }

    public void clreaDragPointerInfo() {
        this.mDragToken = null;
        this.mDragPointerIcon = null;
    }

    public void updateDragPointerIcon(int i) {
        PointerIcon pointerIcon;
        IBinder iBinder = this.mDragToken;
        if (iBinder == null || (pointerIcon = this.mDragPointerIcon) == null) {
            return;
        }
        setPointerIcon(pointerIcon, i, this.mDeviceId, this.mPointerId, iBinder);
    }

    public void registerOnWirelessKeyboardShareChangedListener(OnWirelessKeyboardShareChangedListener onWirelessKeyboardShareChangedListener, Handler handler) {
        if (onWirelessKeyboardShareChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            if (this.mWirelessKeyboardShareChangedListener == null) {
                initializeWirelessKeyboardShareListenerLocked();
            }
            if (findOnWirelessKeyboardShareChangedListenerLocked(onWirelessKeyboardShareChangedListener) < 0) {
                this.mOnWirelessKeyboardShareChangedListeners.add(new OnWirelessKeyboardShareChangedListenerDelegate(onWirelessKeyboardShareChangedListener, handler));
            }
        }
    }

    public void unregisterOnWirelessKeyboardShareChangedListener(OnWirelessKeyboardShareChangedListener onWirelessKeyboardShareChangedListener) {
        if (onWirelessKeyboardShareChangedListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            int findOnWirelessKeyboardShareChangedListenerLocked = findOnWirelessKeyboardShareChangedListenerLocked(onWirelessKeyboardShareChangedListener);
            if (findOnWirelessKeyboardShareChangedListenerLocked >= 0) {
                this.mOnWirelessKeyboardShareChangedListeners.remove(findOnWirelessKeyboardShareChangedListenerLocked).removeCallbacksAndMessages(null);
            }
        }
    }

    private void initializeWirelessKeyboardShareListenerLocked() {
        WirelessKeyboardShareChangedListener wirelessKeyboardShareChangedListener = new WirelessKeyboardShareChangedListener();
        try {
            this.mIm.registerWirelessKeyboardShareChangedListener(wirelessKeyboardShareChangedListener);
            this.mWirelessKeyboardShareChangedListener = wirelessKeyboardShareChangedListener;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findOnWirelessKeyboardShareChangedListenerLocked(OnWirelessKeyboardShareChangedListener onWirelessKeyboardShareChangedListener) {
        int size = this.mOnWirelessKeyboardShareChangedListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnWirelessKeyboardShareChangedListeners.get(i).mListener == onWirelessKeyboardShareChangedListener) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWirelessKeyboardShareChanged(long j, int i, String str) {
        if (debug()) {
            Log.d(TAG, "Received wireless keyboard share changed: whenNanos=" + j + ", index = " + i + " " + str);
        }
        synchronized (this.mWirelessKeyboardShareLock) {
            int size = this.mOnWirelessKeyboardShareChangedListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mOnWirelessKeyboardShareChangedListeners.get(i2).sendWirelessKeyboardShareChanged(j, i, str);
            }
        }
    }

    private final class WirelessKeyboardShareChangedListener extends IWirelessKeyboardShareChangedListener.Stub {
        private WirelessKeyboardShareChangedListener() {
        }

        @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
        public void onWirelessKeyboardShareChanged(long j, int i, String str) {
            InputManagerGlobal.this.onWirelessKeyboardShareChanged(j, i, str);
        }
    }

    private static final class OnWirelessKeyboardShareChangedListenerDelegate extends Handler {
        private static final int MSG_WIRELESS_KEYBOARD_SHARE_CHANGED = 0;
        public final OnWirelessKeyboardShareChangedListener mListener;

        public OnWirelessKeyboardShareChangedListenerDelegate(OnWirelessKeyboardShareChangedListener onWirelessKeyboardShareChangedListener, Handler handler) {
            super(handler != null ? handler.getLooper() : Looper.myLooper());
            this.mListener = onWirelessKeyboardShareChangedListener;
        }

        public void sendWirelessKeyboardShareChanged(long j, int i, String str) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = (int) j;
            obtain.argi2 = (int) (j >> 32);
            obtain.argi3 = i;
            obtain.arg1 = str;
            obtainMessage(0, obtain).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            SomeArgs someArgs = (SomeArgs) message.obj;
            this.mListener.onWirelessKeyboardShareChanged((someArgs.argi1 & 4294967295L) | (someArgs.argi2 << 32), someArgs.argi3, (String) someArgs.arg1);
        }
    }
}
