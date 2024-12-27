package android.hardware.input;

import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
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
import java.util.List;
import java.util.Map;

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
    private final ArrayList<InputSensorEventListenerDelegate> mInputSensorEventListeners =
            new ArrayList<>();

    public InputDeviceSensorManager(InputManagerGlobal inputManagerGlobal) {
        this.mGlobal = inputManagerGlobal;
        initializeSensors();
    }

    SensorManager getSensorManager(int deviceId) {
        return new InputSensorManager(deviceId);
    }

    private void updateInputDeviceSensorInfoLocked(int deviceId) {
        InputDevice inputDevice = InputDevice.getDevice(deviceId);
        if (inputDevice != null && inputDevice.hasSensor()) {
            InputSensorInfo[] sensorInfos = this.mGlobal.getSensorList(deviceId);
            populateSensorsForInputDeviceLocked(deviceId, sensorInfos);
        }
    }

    public void onInputDeviceAdded(int deviceId) {
        synchronized (this.mInputSensorLock) {
            if (!this.mSensors.containsKey(Integer.valueOf(deviceId))) {
                updateInputDeviceSensorInfoLocked(deviceId);
            } else {
                Slog.e(
                        TAG,
                        "Received 'device added' notification for device "
                                + deviceId
                                + ", but it is already in the list");
            }
        }
    }

    public void onInputDeviceRemoved(int deviceId) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(Integer.valueOf(deviceId));
        }
    }

    public void onInputDeviceChanged(int deviceId) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(Integer.valueOf(deviceId));
            updateInputDeviceSensorInfoLocked(deviceId);
        }
    }

    public static boolean sensorEquals(Sensor lhs, Sensor rhs) {
        return lhs.getType() == rhs.getType() && lhs.getId() == rhs.getId();
    }

    private void populateSensorsForInputDeviceLocked(int deviceId, InputSensorInfo[] sensorInfos) {
        List<Sensor> sensors = new ArrayList<>();
        for (InputSensorInfo inputSensorInfo : sensorInfos) {
            Sensor sensor = new Sensor(inputSensorInfo);
            sensors.add(sensor);
        }
        this.mSensors.put(Integer.valueOf(deviceId), sensors);
    }

    private void initializeSensors() {
        synchronized (this.mInputSensorLock) {
            this.mSensors.clear();
            int[] deviceIds = this.mGlobal.getInputDeviceIds();
            for (int deviceId : deviceIds) {
                updateInputDeviceSensorInfoLocked(deviceId);
            }
        }
    }

    private Sensor getInputDeviceSensorLocked(int deviceId, int sensorType) {
        List<Sensor> sensors = this.mSensors.get(Integer.valueOf(deviceId));
        for (Sensor sensor : sensors) {
            if (sensor.getType() == sensorType) {
                return sensor;
            }
        }
        return null;
    }

    private int findSensorEventListenerLocked(SensorEventListener listener) {
        for (int i = 0; i < this.mInputSensorEventListeners.size(); i++) {
            if (this.mInputSensorEventListeners.get(i).getListener() == listener) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    public void onInputSensorChanged(
            int deviceId, int sensorType, int accuracy, long timestamp, float[] values) {
        synchronized (this.mInputSensorLock) {
            Sensor sensor = getInputDeviceSensorLocked(deviceId, sensorType);
            if (sensor == null) {
                Slog.wtf(
                        TAG,
                        "onInputSensorChanged: Got sensor update for device "
                                + deviceId
                                + " but the sensor was not found.");
                return;
            }
            for (int i = 0; i < this.mInputSensorEventListeners.size(); i++) {
                InputSensorEventListenerDelegate listener = this.mInputSensorEventListeners.get(i);
                if (listener.hasSensorRegistered(deviceId, sensorType)) {
                    SensorEvent event = listener.getSensorEvent(sensor);
                    if (event == null) {
                        Slog.wtf(TAG, "Failed to get SensorEvent.");
                        return;
                    }
                    event.sensor = sensor;
                    event.accuracy = accuracy;
                    event.timestamp = timestamp;
                    System.arraycopy(values, 0, event.values, 0, event.values.length);
                    listener.sendSensorChanged(event);
                }
            }
        }
    }

    public void onInputSensorAccuracyChanged(int deviceId, int sensorType, int accuracy) {
        synchronized (this.mInputSensorLock) {
            for (int i = 0; i < this.mInputSensorEventListeners.size(); i++) {
                InputSensorEventListenerDelegate listener = this.mInputSensorEventListeners.get(i);
                if (listener.hasSensorRegistered(deviceId, sensorType)) {
                    listener.sendSensorAccuracyChanged(deviceId, sensorType, accuracy);
                }
            }
        }
    }

    private final class InputSensorEventListener extends IInputSensorEventListener.Stub {
        private InputSensorEventListener() {}

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorChanged(
                int deviceId, int sensorType, int accuracy, long timestamp, float[] values)
                throws RemoteException {
            InputDeviceSensorManager.this.onInputSensorChanged(
                    deviceId, sensorType, accuracy, timestamp, values);
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorAccuracyChanged(int deviceId, int sensorType, int accuracy)
                throws RemoteException {
            InputDeviceSensorManager.this.onInputSensorAccuracyChanged(
                    deviceId, sensorType, accuracy);
        }
    }

    private static final class InputSensorEventListenerDelegate extends Handler {
        private final SensorEventListener mListener;
        private final SparseArray<SensorEvent> mSensorEvents;
        private final List<Sensor> mSensors;

        InputSensorEventListenerDelegate(
                SensorEventListener listener, Sensor sensor, Looper looper) {
            super(looper);
            this.mSensors = new ArrayList();
            this.mSensorEvents = new SparseArray<>();
            this.mListener = listener;
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
            for (Sensor s : this.mSensors) {
                if (InputDeviceSensorManager.sensorEquals(s, sensor)) {
                    this.mSensors.remove(sensor);
                    this.mSensorEvents.remove(sensor.getType());
                }
            }
        }

        public void addSensor(Sensor sensor) {
            for (Sensor s : this.mSensors) {
                if (InputDeviceSensorManager.sensorEquals(s, sensor)) {
                    Slog.w(
                            InputDeviceSensorManager.TAG,
                            "Adding sensor " + sensor + " already exist!");
                    return;
                }
            }
            this.mSensors.add(sensor);
            int vecLength = Sensor.getMaxLengthValuesArray(sensor, Build.VERSION.SDK_INT);
            SensorEvent event = new SensorEvent(sensor, -1, 0L, new float[vecLength]);
            this.mSensorEvents.put(sensor.getType(), event);
        }

        public boolean hasSensorRegistered(int deviceId, int sensorType) {
            for (Sensor sensor : this.mSensors) {
                if (sensor.getType() == sensorType && sensor.getId() == deviceId) {
                    return true;
                }
            }
            return false;
        }

        public SensorEventListener getListener() {
            return this.mListener;
        }

        public SensorEvent getSensorEvent(Sensor sensor) {
            return this.mSensorEvents.get(sensor.getType());
        }

        public void sendSensorChanged(SensorEvent event) {
            obtainMessage(2, event).sendToTarget();
        }

        public void sendSensorAccuracyChanged(int deviceId, int sensorType, int accuracy) {
            obtainMessage(1, deviceId, sensorType, Integer.valueOf(accuracy)).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int deviceId = msg.arg1;
                    int sensorType = msg.arg2;
                    int accuracy = ((Integer) msg.obj).intValue();
                    for (Sensor sensor : this.mSensors) {
                        if (sensor.getId() == deviceId && sensor.getType() == sensorType) {
                            this.mListener.onAccuracyChanged(sensor, accuracy);
                        }
                    }
                    break;
                case 2:
                    SensorEvent event = (SensorEvent) msg.obj;
                    this.mListener.onSensorChanged(event);
                    break;
            }
        }
    }

    public Sensor getSensorForInputDevice(int deviceId, int type) {
        synchronized (this.mInputSensorLock) {
            for (Map.Entry<Integer, List<Sensor>> entry : this.mSensors.entrySet()) {
                for (Sensor sensor : entry.getValue()) {
                    if (sensor.getId() == deviceId && sensor.getType() == type) {
                        return sensor;
                    }
                }
            }
            return null;
        }
    }

    public List<Sensor> getFullSensorListForDevice(int deviceId) {
        List<Sensor> sensors = new ArrayList<>();
        synchronized (this.mInputSensorLock) {
            for (Map.Entry<Integer, List<Sensor>> entry : this.mSensors.entrySet()) {
                for (Sensor sensor : entry.getValue()) {
                    if (sensor.getId() == deviceId) {
                        sensors.add(sensor);
                    }
                }
            }
        }
        return sensors;
    }

    public boolean registerListenerInternal(
            SensorEventListener listener,
            Sensor sensor,
            int delayUs,
            int maxBatchReportLatencyUs,
            Handler handler) {
        if (listener == null) {
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
        if (maxBatchReportLatencyUs < 0 || delayUs < 0) {
            Slog.e(TAG, "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        synchronized (this.mInputSensorLock) {
            if (getSensorForInputDevice(sensor.getId(), sensor.getType()) != null) {
                int deviceId = sensor.getId();
                InputDevice inputDevice = this.mGlobal.getInputDevice(deviceId);
                if (inputDevice == null) {
                    Slog.e(TAG, "input device not found for sensor " + sensor.getId());
                    return false;
                }
                if (!inputDevice.hasSensor()) {
                    Slog.e(TAG, "The device doesn't have the sensor:" + sensor);
                    return false;
                }
                if (!this.mGlobal.enableSensor(
                        deviceId, sensor.getType(), delayUs, maxBatchReportLatencyUs)) {
                    Slog.e(TAG, "Can't enable the sensor:" + sensor);
                    return false;
                }
            }
            if (this.mInputServiceSensorListener == null) {
                this.mInputServiceSensorListener = new InputSensorEventListener();
                if (!this.mGlobal.registerSensorListener(this.mInputServiceSensorListener)) {
                    Slog.e(TAG, "Failed registering the sensor listener");
                    return false;
                }
            }
            int idx = findSensorEventListenerLocked(listener);
            if (idx < 0) {
                InputSensorEventListenerDelegate d =
                        new InputSensorEventListenerDelegate(
                                listener, sensor, getLooperForListenerLocked(handler));
                this.mInputSensorEventListeners.add(d);
            } else {
                this.mInputSensorEventListeners.get(idx).addSensor(sensor);
            }
            return true;
        }
    }

    private Looper getLooperForListenerLocked(Handler requestedHandler) {
        if (requestedHandler != null) {
            return requestedHandler.getLooper();
        }
        if (this.mSensorThread == null) {
            this.mSensorThread = new HandlerThread("SensorThread");
            this.mSensorThread.start();
        }
        return this.mSensorThread.getLooper();
    }

    public void unregisterListenerInternal(SensorEventListener listener, Sensor sensor) {
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputSensorLock) {
            int idx = findSensorEventListenerLocked(listener);
            if (idx >= 0) {
                InputSensorEventListenerDelegate delegate =
                        this.mInputSensorEventListeners.get(idx);
                List<Sensor> sensorsRegistered = new ArrayList<>(delegate.getSensors());
                delegate.removeSensor(sensor);
                if (delegate.isEmpty()) {
                    this.mInputSensorEventListeners.remove(idx);
                }
                if (this.mInputServiceSensorListener != null
                        && this.mInputSensorEventListeners.isEmpty()) {
                    this.mGlobal.unregisterSensorListener(this.mInputServiceSensorListener);
                    this.mInputServiceSensorListener = null;
                }
                for (Sensor s : sensorsRegistered) {
                    int deviceId = s.getId();
                    int sensorType = s.getType();
                    boolean enableSensor = false;
                    int i = 0;
                    while (true) {
                        if (i >= this.mInputSensorEventListeners.size()) {
                            break;
                        }
                        if (!this.mInputSensorEventListeners
                                .get(i)
                                .hasSensorRegistered(deviceId, sensorType)) {
                            i++;
                        } else {
                            enableSensor = true;
                            Slog.w(TAG, "device " + deviceId + " still uses sensor " + sensorType);
                            break;
                        }
                    }
                    if (!enableSensor) {
                        this.mGlobal.disableSensor(deviceId, sensorType);
                    }
                }
                return;
            }
            Slog.e(TAG, "Listener is not registered");
        }
    }

    public boolean flushInternal(SensorEventListener listener) {
        synchronized (this.mInputSensorLock) {
            int idx = findSensorEventListenerLocked(listener);
            if (idx < 0) {
                return false;
            }
            for (Sensor sensor : this.mInputSensorEventListeners.get(idx).getSensors()) {
                int deviceId = sensor.getId();
                if (!this.mGlobal.flushSensor(deviceId, sensor.getType())) {
                    return false;
                }
            }
            return true;
        }
    }

    public class InputSensorManager extends SensorManager {
        final int mId;

        InputSensorManager(int deviceId) {
            this.mId = deviceId;
        }

        @Override // android.hardware.SensorManager
        public Sensor getDefaultSensor(int type) {
            return InputDeviceSensorManager.this.getSensorForInputDevice(this.mId, type);
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
        protected boolean registerListenerImpl(
                SensorEventListener listener,
                Sensor sensor,
                int delayUs,
                Handler handler,
                int maxBatchReportLatencyUs,
                int reservedFlags) {
            return InputDeviceSensorManager.this.registerListenerInternal(
                    listener, sensor, delayUs, maxBatchReportLatencyUs, handler);
        }

        @Override // android.hardware.SensorManager
        protected void unregisterListenerImpl(SensorEventListener listener, Sensor sensor) {
            InputDeviceSensorManager.this.unregisterListenerInternal(listener, sensor);
        }

        @Override // android.hardware.SensorManager
        protected boolean flushImpl(SensorEventListener listener) {
            return InputDeviceSensorManager.this.flushInternal(listener);
        }

        @Override // android.hardware.SensorManager
        protected SensorDirectChannel createDirectChannelImpl(
                MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
            return null;
        }

        @Override // android.hardware.SensorManager
        protected void destroyDirectChannelImpl(SensorDirectChannel channel) {}

        @Override // android.hardware.SensorManager
        protected int configureDirectChannelImpl(SensorDirectChannel channel, Sensor s, int rate) {
            return 0;
        }

        @Override // android.hardware.SensorManager
        protected void registerDynamicSensorCallbackImpl(
                SensorManager.DynamicSensorCallback callback, Handler handler) {}

        @Override // android.hardware.SensorManager
        protected void unregisterDynamicSensorCallbackImpl(
                SensorManager.DynamicSensorCallback callback) {}

        @Override // android.hardware.SensorManager
        protected boolean requestTriggerSensorImpl(TriggerEventListener listener, Sensor sensor) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected boolean cancelTriggerSensorImpl(
                TriggerEventListener listener, Sensor sensor, boolean disable) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected boolean initDataInjectionImpl(boolean enable, int mode) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected boolean injectSensorDataImpl(
                Sensor sensor, float[] values, int accuracy, long timestamp) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected boolean setOperationParameterImpl(SensorAdditionalInfo parameter) {
            return false;
        }
    }
}
