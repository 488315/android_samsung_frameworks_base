package android.hardware.input;

import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.hardware.input.IInputSensorEventListener;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class InputDeviceSensorManager {
    private static final boolean DEBUG = false;
    private static final int MSG_SENSOR_ACCURACY_CHANGED = 1;
    private static final int MSG_SENSOR_CHANGED = 2;
    private static final String TAG = "InputDeviceSensorManager";
    private final InputManagerGlobal mGlobal;
    private InputSensorEventListener mInputServiceSensorListener;
    private HandlerThread mSensorThread;
    private final Map<Integer, List<Sensor>> mSensors = new HashMap();
    private final Object mInputSensorLock = new Object();
    private final ArrayList<InputSensorEventListenerDelegate> mInputSensorEventListeners = new ArrayList<>();

    public InputDeviceSensorManager(InputManagerGlobal inputManagerGlobal) {
        this.mGlobal = inputManagerGlobal;
        initializeSensors();
    }

    SensorManager getSensorManager(int i) {
        return new InputSensorManager(i);
    }

    private void updateInputDeviceSensorInfoLocked(int i) {
        InputDevice device = InputDevice.getDevice(i);
        if (device == null || !device.hasSensor()) {
            return;
        }
        populateSensorsForInputDeviceLocked(i, this.mGlobal.getSensorList(i));
    }

    public void onInputDeviceAdded(int i) {
        synchronized (this.mInputSensorLock) {
            if (!this.mSensors.containsKey(Integer.valueOf(i))) {
                updateInputDeviceSensorInfoLocked(i);
            } else {
                Slog.e(TAG, "Received 'device added' notification for device " + i + ", but it is already in the list");
            }
        }
    }

    public void onInputDeviceRemoved(int i) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(Integer.valueOf(i));
        }
    }

    public void onInputDeviceChanged(int i) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(Integer.valueOf(i));
            updateInputDeviceSensorInfoLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean sensorEquals(Sensor sensor, Sensor sensor2) {
        return sensor.getType() == sensor2.getType() && sensor.getId() == sensor2.getId();
    }

    private void populateSensorsForInputDeviceLocked(int i, InputSensorInfo[] inputSensorInfoArr) {
        ArrayList arrayList = new ArrayList();
        for (InputSensorInfo inputSensorInfo : inputSensorInfoArr) {
            arrayList.add(new Sensor(inputSensorInfo));
        }
        this.mSensors.put(Integer.valueOf(i), arrayList);
    }

    private void initializeSensors() {
        synchronized (this.mInputSensorLock) {
            this.mSensors.clear();
            for (int i : this.mGlobal.getInputDeviceIds()) {
                updateInputDeviceSensorInfoLocked(i);
            }
        }
    }

    private Sensor getInputDeviceSensorLocked(int i, int i2) {
        for (Sensor sensor : this.mSensors.get(Integer.valueOf(i))) {
            if (sensor.getType() == i2) {
                return sensor;
            }
        }
        return null;
    }

    private int findSensorEventListenerLocked(SensorEventListener sensorEventListener) {
        for (int i = 0; i < this.mInputSensorEventListeners.size(); i++) {
            if (this.mInputSensorEventListeners.get(i).getListener() == sensorEventListener) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) {
        synchronized (this.mInputSensorLock) {
            Sensor inputDeviceSensorLocked = getInputDeviceSensorLocked(i, i2);
            if (inputDeviceSensorLocked == null) {
                Slog.wtf(TAG, "onInputSensorChanged: Got sensor update for device " + i + " but the sensor was not found.");
                return;
            }
            for (int i4 = 0; i4 < this.mInputSensorEventListeners.size(); i4++) {
                InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(i4);
                if (inputSensorEventListenerDelegate.hasSensorRegistered(i, i2)) {
                    SensorEvent sensorEvent = inputSensorEventListenerDelegate.getSensorEvent(inputDeviceSensorLocked);
                    if (sensorEvent == null) {
                        Slog.wtf(TAG, "Failed to get SensorEvent.");
                        return;
                    }
                    sensorEvent.sensor = inputDeviceSensorLocked;
                    sensorEvent.accuracy = i3;
                    sensorEvent.timestamp = j;
                    System.arraycopy(fArr, 0, sensorEvent.values, 0, sensorEvent.values.length);
                    inputSensorEventListenerDelegate.sendSensorChanged(sensorEvent);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputSensorAccuracyChanged(int i, int i2, int i3) {
        synchronized (this.mInputSensorLock) {
            for (int i4 = 0; i4 < this.mInputSensorEventListeners.size(); i4++) {
                InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(i4);
                if (inputSensorEventListenerDelegate.hasSensorRegistered(i, i2)) {
                    inputSensorEventListenerDelegate.sendSensorAccuracyChanged(i, i2, i3);
                }
            }
        }
    }

    private final class InputSensorEventListener extends IInputSensorEventListener.Stub {
        private InputSensorEventListener() {
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) throws RemoteException {
            InputDeviceSensorManager.this.onInputSensorChanged(i, i2, i3, j, fArr);
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorAccuracyChanged(int i, int i2, int i3) throws RemoteException {
            InputDeviceSensorManager.this.onInputSensorAccuracyChanged(i, i2, i3);
        }
    }

    private static final class InputSensorEventListenerDelegate extends Handler {
        private final SensorEventListener mListener;
        private final SparseArray<SensorEvent> mSensorEvents;
        private final List<Sensor> mSensors;

        InputSensorEventListenerDelegate(SensorEventListener sensorEventListener, Sensor sensor, Looper looper) {
            super(looper);
            this.mSensors = new ArrayList();
            this.mSensorEvents = new SparseArray<>();
            this.mListener = sensorEventListener;
            addSensor(sensor);
        }

        public List<Sensor> getSensors() {
            return this.mSensors;
        }

        public boolean isEmpty() {
            return this.mSensors.isEmpty();
        }

        public void removeSensor(Sensor sensor) {
            if (sensor == null) {
                this.mSensors.clear();
                this.mSensorEvents.clear();
                return;
            }
            Iterator<Sensor> it = this.mSensors.iterator();
            while (it.hasNext()) {
                if (InputDeviceSensorManager.sensorEquals(it.next(), sensor)) {
                    this.mSensors.remove(sensor);
                    this.mSensorEvents.remove(sensor.getType());
                }
            }
        }

        public void addSensor(Sensor sensor) {
            Iterator<Sensor> it = this.mSensors.iterator();
            while (it.hasNext()) {
                if (InputDeviceSensorManager.sensorEquals(it.next(), sensor)) {
                    Slog.w(InputDeviceSensorManager.TAG, "Adding sensor " + sensor + " already exist!");
                    return;
                }
            }
            this.mSensors.add(sensor);
            this.mSensorEvents.put(sensor.getType(), new SensorEvent(sensor, -1, 0L, new float[Sensor.getMaxLengthValuesArray(sensor, Build.VERSION.SDK_INT)]));
        }

        public boolean hasSensorRegistered(int i, int i2) {
            for (Sensor sensor : this.mSensors) {
                if (sensor.getType() == i2 && sensor.getId() == i) {
                    return true;
                }
            }
            return false;
        }

        public SensorEventListener getListener() {
            return this.mListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public SensorEvent getSensorEvent(Sensor sensor) {
            return this.mSensorEvents.get(sensor.getType());
        }

        public void sendSensorChanged(SensorEvent sensorEvent) {
            obtainMessage(2, sensorEvent).sendToTarget();
        }

        public void sendSensorAccuracyChanged(int i, int i2, int i3) {
            obtainMessage(1, i, i2, Integer.valueOf(i3)).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                this.mListener.onSensorChanged((SensorEvent) message.obj);
                return;
            }
            int i2 = message.arg1;
            int i3 = message.arg2;
            int intValue = ((Integer) message.obj).intValue();
            for (Sensor sensor : this.mSensors) {
                if (sensor.getId() == i2 && sensor.getType() == i3) {
                    this.mListener.onAccuracyChanged(sensor, intValue);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Sensor getSensorForInputDevice(int i, int i2) {
        synchronized (this.mInputSensorLock) {
            Iterator<Map.Entry<Integer, List<Sensor>>> it = this.mSensors.entrySet().iterator();
            while (it.hasNext()) {
                for (Sensor sensor : it.next().getValue()) {
                    if (sensor.getId() == i && sensor.getType() == i2) {
                        return sensor;
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Sensor> getFullSensorListForDevice(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mInputSensorLock) {
            Iterator<Map.Entry<Integer, List<Sensor>>> it = this.mSensors.entrySet().iterator();
            while (it.hasNext()) {
                for (Sensor sensor : it.next().getValue()) {
                    if (sensor.getId() == i) {
                        arrayList.add(sensor);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean registerListenerInternal(SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        if (sensorEventListener == null) {
            Slog.e(TAG, "listener is null");
            return false;
        }
        if (sensor == null) {
            Slog.e(TAG, "sensor is null");
            return false;
        }
        if (sensor.getReportingMode() == 2) {
            Slog.e(TAG, "Trigger Sensors should use the requestTriggerSensor.");
            return false;
        }
        if (i2 < 0 || i < 0) {
            Slog.e(TAG, "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        synchronized (this.mInputSensorLock) {
            if (getSensorForInputDevice(sensor.getId(), sensor.getType()) != null) {
                int id = sensor.getId();
                InputDevice inputDevice = this.mGlobal.getInputDevice(id);
                if (inputDevice == null) {
                    Slog.e(TAG, "input device not found for sensor " + sensor.getId());
                    return false;
                }
                if (!inputDevice.hasSensor()) {
                    Slog.e(TAG, "The device doesn't have the sensor:" + sensor);
                    return false;
                }
                if (!this.mGlobal.enableSensor(id, sensor.getType(), i, i2)) {
                    Slog.e(TAG, "Can't enable the sensor:" + sensor);
                    return false;
                }
            }
            if (this.mInputServiceSensorListener == null) {
                InputSensorEventListener inputSensorEventListener = new InputSensorEventListener();
                this.mInputServiceSensorListener = inputSensorEventListener;
                if (!this.mGlobal.registerSensorListener(inputSensorEventListener)) {
                    Slog.e(TAG, "Failed registering the sensor listener");
                    return false;
                }
            }
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked < 0) {
                this.mInputSensorEventListeners.add(new InputSensorEventListenerDelegate(sensorEventListener, sensor, getLooperForListenerLocked(handler)));
            } else {
                this.mInputSensorEventListeners.get(findSensorEventListenerLocked).addSensor(sensor);
            }
            return true;
        }
    }

    private Looper getLooperForListenerLocked(Handler handler) {
        if (handler != null) {
            return handler.getLooper();
        }
        if (this.mSensorThread == null) {
            HandlerThread handlerThread = new HandlerThread("SensorThread");
            this.mSensorThread = handlerThread;
            handlerThread.start();
        }
        return this.mSensorThread.getLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterListenerInternal(SensorEventListener sensorEventListener, Sensor sensor) {
        if (sensorEventListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputSensorLock) {
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked >= 0) {
                InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(findSensorEventListenerLocked);
                ArrayList<Sensor> arrayList = new ArrayList(inputSensorEventListenerDelegate.getSensors());
                inputSensorEventListenerDelegate.removeSensor(sensor);
                if (inputSensorEventListenerDelegate.isEmpty()) {
                    this.mInputSensorEventListeners.remove(findSensorEventListenerLocked);
                }
                if (this.mInputServiceSensorListener != null && this.mInputSensorEventListeners.isEmpty()) {
                    this.mGlobal.unregisterSensorListener(this.mInputServiceSensorListener);
                    this.mInputServiceSensorListener = null;
                }
                for (Sensor sensor2 : arrayList) {
                    int id = sensor2.getId();
                    int type = sensor2.getType();
                    int i = 0;
                    while (true) {
                        if (i < this.mInputSensorEventListeners.size()) {
                            if (this.mInputSensorEventListeners.get(i).hasSensorRegistered(id, type)) {
                                Slog.w(TAG, "device " + id + " still uses sensor " + type);
                                break;
                            }
                            i++;
                        } else {
                            this.mGlobal.disableSensor(id, type);
                            break;
                        }
                    }
                }
                return;
            }
            Slog.e(TAG, "Listener is not registered");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean flushInternal(SensorEventListener sensorEventListener) {
        synchronized (this.mInputSensorLock) {
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked < 0) {
                return false;
            }
            for (Sensor sensor : this.mInputSensorEventListeners.get(findSensorEventListenerLocked).getSensors()) {
                if (!this.mGlobal.flushSensor(sensor.getId(), sensor.getType())) {
                    return false;
                }
            }
            return true;
        }
    }

    public class InputSensorManager extends SensorManager {
        final int mId;

        @Override // android.hardware.SensorManager
        protected boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
            return 0;
        }

        @Override // android.hardware.SensorManager
        protected SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
            return null;
        }

        @Override // android.hardware.SensorManager
        protected void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        }

        @Override // android.hardware.SensorManager
        protected boolean initDataInjectionImpl(boolean z, int i) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected void registerDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        }

        @Override // android.hardware.SensorManager
        protected boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected void unregisterDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        }

        InputSensorManager(int i) {
            this.mId = i;
        }

        @Override // android.hardware.SensorManager
        public Sensor getDefaultSensor(int i) {
            return InputDeviceSensorManager.this.getSensorForInputDevice(this.mId, i);
        }

        @Override // android.hardware.SensorManager
        protected List<Sensor> getFullSensorList() {
            return InputDeviceSensorManager.this.getFullSensorListForDevice(this.mId);
        }

        @Override // android.hardware.SensorManager
        protected List<Sensor> getFullDynamicSensorList() {
            return new ArrayList();
        }

        @Override // android.hardware.SensorManager
        protected boolean registerListenerImpl(SensorEventListener sensorEventListener, Sensor sensor, int i, Handler handler, int i2, int i3) {
            return InputDeviceSensorManager.this.registerListenerInternal(sensorEventListener, sensor, i, i2, handler);
        }

        @Override // android.hardware.SensorManager
        protected void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
            InputDeviceSensorManager.this.unregisterListenerInternal(sensorEventListener, sensor);
        }

        @Override // android.hardware.SensorManager
        protected boolean flushImpl(SensorEventListener sensorEventListener) {
            return InputDeviceSensorManager.this.flushInternal(sensorEventListener);
        }
    }
}
