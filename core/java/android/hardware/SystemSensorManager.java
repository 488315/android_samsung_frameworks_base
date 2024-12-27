package android.hardware;

import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.flags.Flags;
import android.compat.Compatibility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.MessageQueue;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import dalvik.system.CloseGuard;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SystemSensorManager extends SensorManager {
    private static final int CAPPED_SAMPLING_PERIOD_US = 5000;
    private static final int CAPPED_SAMPLING_RATE_LEVEL = 1;
    static final long CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION = 136069189;
    private static final boolean DEBUG_DYNAMIC_SENSOR = true;
    private static final String HIGH_SAMPLING_RATE_SENSORS_PERMISSION =
            "android.permission.HIGH_SAMPLING_RATE_SENSORS";
    private static final int MAX_LISTENER_COUNT = 128;
    private static final int MIN_DIRECT_CHANNEL_BUFFER_SIZE = 104;
    private CameraLightSensorManager mCameraLightManager;
    private final Context mContext;
    private BroadcastReceiver mDynamicSensorBroadcastReceiver;
    private final boolean mIsPackageDebuggable;
    private final Looper mMainLooper;
    private final long mNativeInstance;
    private BroadcastReceiver mRuntimeSensorBroadcastReceiver;
    private final int mTargetSdkLevel;
    private VirtualDeviceManager mVdm;
    private VirtualDeviceManager.VirtualDeviceListener mVirtualDeviceListener;
    private static final Object sLock = new Object();
    private static boolean sNativeClassInited = false;
    private static InjectEventQueue sInjectEventQueue = null;
    private final ArrayList<Sensor> mFullSensorsList = new ArrayList<>();
    private List<Sensor> mFullDynamicSensorsList = new ArrayList();
    private final SparseArray<List<Sensor>> mFullRuntimeSensorListByDevice = new SparseArray<>();
    private final SparseArray<SparseArray<List<Sensor>>> mRuntimeSensorListByDeviceByType =
            new SparseArray<>();
    private boolean mDynamicSensorListDirty = true;
    private final HashMap<Integer, Sensor> mHandleToSensor = new HashMap<>();
    private final HashMap<SensorEventListener, SensorEventQueue> mSensorListeners = new HashMap<>();
    private final HashMap<TriggerEventListener, TriggerEventQueue> mTriggerListeners =
            new HashMap<>();
    private HashMap<SensorManager.DynamicSensorCallback, Handler> mDynamicSensorCallbacks =
            new HashMap<>();
    private Optional<Boolean> mHasHighSamplingRateSensorsPermission = Optional.empty();

    private static native void nativeClassInit();

    private static native int nativeConfigDirectChannel(long j, int i, int i2, int i3);

    private static native long nativeCreate(String str);

    private static native int nativeCreateDirectChannel(
            long j, int i, long j2, int i2, int i3, HardwareBuffer hardwareBuffer);

    private static native void nativeDestroyDirectChannel(long j, int i);

    private static native boolean nativeGetDefaultDeviceSensorAtIndex(long j, Sensor sensor, int i);

    private static native void nativeGetDynamicSensors(long j, List<Sensor> list);

    private static native void nativeGetRuntimeSensors(long j, int i, List<Sensor> list);

    private static native boolean nativeGetSensorAtIndex(long j, Sensor sensor, int i);

    private static native boolean nativeIsDataInjectionEnabled(long j);

    private static native boolean nativeIsHalBypassReplayDataInjectionEnabled(long j);

    private static native boolean nativeIsReplayDataInjectionEnabled(long j);

    private static native int nativeSetOperationParameter(
            long j, int i, int i2, float[] fArr, int[] iArr);

    public SystemSensorManager(Context context, Looper mainLooper) {
        synchronized (sLock) {
            if (!sNativeClassInited) {
                sNativeClassInited = true;
                nativeClassInit();
            }
        }
        this.mMainLooper = mainLooper;
        ApplicationInfo appInfo = context.getApplicationInfo();
        this.mTargetSdkLevel = appInfo.targetSdkVersion;
        this.mContext = context;
        this.mNativeInstance = nativeCreate(context.getOpPackageName());
        this.mIsPackageDebuggable = (appInfo.flags & 2) != 0;
        int index = 0;
        while (true) {
            Sensor sensor = new Sensor();
            if (Flags.enableNativeVdm()) {
                if (!nativeGetDefaultDeviceSensorAtIndex(this.mNativeInstance, sensor, index)) {
                    return;
                }
            } else if (!nativeGetSensorAtIndex(this.mNativeInstance, sensor, index)) {
                return;
            }
            this.mFullSensorsList.add(sensor);
            this.mHandleToSensor.put(Integer.valueOf(sensor.getHandle()), sensor);
            index++;
        }
    }

    @Override // android.hardware.SensorManager
    public List<Sensor> getSensorList(int type) {
        List<Sensor> list;
        List<Sensor> list2;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return super.getSensorList(type);
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            List<Sensor> fullList = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (fullList == null) {
                fullList = createRuntimeSensorListLocked(deviceId);
            }
            SparseArray<List<Sensor>> deviceSensorListByType =
                    this.mRuntimeSensorListByDeviceByType.get(deviceId);
            list = deviceSensorListByType.get(type);
            if (list == null) {
                if (type == -1) {
                    list2 = fullList;
                } else {
                    list2 = new ArrayList();
                    for (Sensor i : fullList) {
                        if (i.getType() == type) {
                            list2.add(i);
                        }
                    }
                }
                list = Collections.unmodifiableList(list2);
                deviceSensorListByType.append(type, list);
            }
        }
        return list;
    }

    @Override // android.hardware.SensorManager
    protected List<Sensor> getFullSensorList() {
        List<Sensor> fullList;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return this.mFullSensorsList;
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            fullList = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (fullList == null) {
                fullList = createRuntimeSensorListLocked(deviceId);
            }
        }
        return fullList;
    }

    @Override // android.hardware.SensorManager
    public Sensor getSensorByHandle(int sensorHandle) {
        return this.mHandleToSensor.get(Integer.valueOf(sensorHandle));
    }

    @Override // android.hardware.SensorManager
    protected List<Sensor> getFullDynamicSensorList() {
        setupDynamicSensorBroadcastReceiver();
        updateDynamicSensorList();
        return this.mFullDynamicSensorsList;
    }

    @Override // android.hardware.SensorManager
    protected boolean registerListenerImpl(
            SensorEventListener listener,
            Sensor sensor,
            int delayUs,
            Handler handler,
            int maxBatchReportLatencyUs,
            int reservedFlags) {
        String name;
        if (listener == null || sensor == null) {
            Log.e("SensorManager", "sensor or listener is null");
            return false;
        }
        if (sensor.getReportingMode() == 2) {
            Log.e("SensorManager", "Trigger Sensors should use the requestTriggerSensor.");
            return false;
        }
        if (maxBatchReportLatencyUs < 0 || delayUs < 0) {
            Log.e("SensorManager", "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        boolean cameraLightSensorRet = false;
        if (sensor.getName().contains("Camera Light Sensor")) {
            Looper looper = handler != null ? handler.getLooper() : null;
            if (handler != null) {
                Log.d(
                        "SensorManager",
                        "[CameraLightSensor] Use handler looper= "
                                + handler.getLooper()
                                + " mainLooper= "
                                + this.mMainLooper);
            } else {
                Log.d(
                        "SensorManager",
                        "[CameraLightSensor] mainLooper= "
                                + this.mMainLooper
                                + ", Use CameraMangerThread looper.");
            }
            cameraLightSensorRet = requestCameraLightSensor(looper, listener, sensor, true);
        }
        if (this.mSensorListeners.size() >= 128) {
            throw new IllegalStateException(
                    "register failed, the sensor listeners size has exceeded the maximum limit"
                            + " 128");
        }
        String strlistener = listener.toString();
        int delayUs2 =
                (strlistener == null || !strlistener.startsWith("com.tencent") || delayUs >= 5000)
                        ? delayUs
                        : 5000;
        synchronized (this.mSensorListeners) {
            SensorEventQueue queue = this.mSensorListeners.get(listener);
            if (queue == null) {
                Looper looper2 = handler != null ? handler.getLooper() : this.mMainLooper;
                if (listener.getClass().getEnclosingClass() != null) {
                    name = listener.getClass().getEnclosingClass().getName();
                } else {
                    name = listener.getClass().getName();
                }
                String fullClassName = name;
                boolean proGuard = false;
                String proGuardPkg = "";
                int uid = Binder.getCallingUid();
                if (uid >= 10000) {
                    strlistener = "";
                }
                if (sensor.getName().contains("Palm")
                        || sensor.getName().contains("Ear Hover Proximity")) {
                    proGuard = true;
                    proGuardPkg = this.mContext.getPackageManager().getNameForUid(uid);
                }
                if (proGuard) {
                    fullClassName = fullClassName + "/" + proGuardPkg;
                }
                SensorEventQueue queue2 =
                        new SensorEventQueue(listener, looper2, this, fullClassName);
                if (queue2.addSensor(sensor, delayUs2, maxBatchReportLatencyUs)) {
                    this.mSensorListeners.put(listener, queue2);
                } else {
                    queue2.dispose();
                    Log.d(
                            "SensorManager",
                            "registerListener fail (1) :: "
                                    + sensor.getHandle()
                                    + ", "
                                    + sensor.getName()
                                    + ", "
                                    + delayUs2
                                    + ", "
                                    + maxBatchReportLatencyUs
                                    + ", "
                                    + strlistener);
                    return false;
                }
            } else if (!queue.addSensor(sensor, delayUs2, maxBatchReportLatencyUs)) {
                Log.d(
                        "SensorManager",
                        "registerListener fail (2) :: "
                                + sensor.getHandle()
                                + ", "
                                + sensor.getName()
                                + ", "
                                + delayUs2
                                + ", "
                                + maxBatchReportLatencyUs
                                + ", "
                                + strlistener);
                return false;
            }
            Log.d(
                    "SensorManager",
                    "registerListener :: "
                            + sensor.getHandle()
                            + ", "
                            + sensor.getName()
                            + ", "
                            + delayUs2
                            + ", "
                            + maxBatchReportLatencyUs
                            + ", "
                            + strlistener);
            if (sensor.getName().contains("Camera Light Sensor")) {
                return cameraLightSensorRet;
            }
            return true;
        }
    }

    @Override // android.hardware.SensorManager
    protected void unregisterListenerImpl(SensorEventListener listener, Sensor sensor) {
        boolean result;
        if (sensor != null && sensor.getReportingMode() == 2) {
            return;
        }
        if (listener != null
                && this.mCameraLightManager != null
                && this.mCameraLightManager.isAllowListedListener(listener.toString())) {
            requestCameraLightSensor(null, listener, sensor, false);
        }
        synchronized (this.mSensorListeners) {
            SensorEventQueue queue = this.mSensorListeners.get(listener);
            if (queue != null) {
                if (sensor == null) {
                    result = queue.removeAllSensors();
                } else {
                    result = queue.removeSensor(sensor, true);
                }
                if (result && !queue.hasSensors()) {
                    this.mSensorListeners.remove(listener);
                    queue.dispose();
                    int uid = Binder.getCallingUid();
                    if (uid < 10000) {
                        if (sensor != null && listener != null) {
                            Log.d(
                                    "SensorManager",
                                    "unregisterListener :: "
                                            + sensor.getHandle()
                                            + ", "
                                            + sensor.getName()
                                            + ", "
                                            + listener.toString());
                        } else if (listener != null) {
                            Log.d("SensorManager", "unregisterListener :: " + listener.toString());
                        } else {
                            Log.d("SensorManager", "unregisterListener :: listener is null");
                        }
                    } else {
                        Log.d("SensorManager", "unregisterListener");
                    }
                }
            }
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean requestTriggerSensorImpl(TriggerEventListener listener, Sensor sensor) {
        String fullClassName;
        if (sensor == null) {
            throw new IllegalArgumentException("sensor cannot be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (sensor.getReportingMode() != 2) {
            return false;
        }
        if (this.mTriggerListeners.size() >= 128) {
            throw new IllegalStateException(
                    "request failed, the trigger listeners size has exceeded the maximum limit"
                            + " 128");
        }
        synchronized (this.mTriggerListeners) {
            TriggerEventQueue queue = this.mTriggerListeners.get(listener);
            if (queue == null) {
                if (listener.getClass().getEnclosingClass() != null) {
                    fullClassName = listener.getClass().getEnclosingClass().getName();
                } else {
                    fullClassName = listener.getClass().getName();
                }
                TriggerEventQueue queue2 =
                        new TriggerEventQueue(listener, this.mMainLooper, this, fullClassName);
                if (!queue2.addSensor(sensor, 0, 0)) {
                    queue2.dispose();
                    Log.d(
                            "SensorManager",
                            "requestTrigger :: fail (1) :: "
                                    + sensor.getHandle()
                                    + ", "
                                    + sensor.getName()
                                    + ", "
                                    + listener.toString());
                    return false;
                }
                this.mTriggerListeners.put(listener, queue2);
            } else if (!queue.addSensor(sensor, 0, 0)) {
                Log.d(
                        "SensorManager",
                        "requestTrigger :: fail (2) :: "
                                + sensor.getHandle()
                                + ", "
                                + sensor.getName()
                                + ", "
                                + listener.toString());
                return false;
            }
            Log.d(
                    "SensorManager",
                    "requestTrigger :: "
                            + sensor.getHandle()
                            + ", "
                            + sensor.getName()
                            + ", "
                            + listener.toString());
            return true;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean cancelTriggerSensorImpl(
            TriggerEventListener listener, Sensor sensor, boolean disable) {
        boolean result;
        if (sensor != null && sensor.getReportingMode() != 2) {
            return false;
        }
        synchronized (this.mTriggerListeners) {
            TriggerEventQueue queue = this.mTriggerListeners.get(listener);
            if (queue == null) {
                return false;
            }
            if (sensor == null) {
                result = queue.removeAllSensors();
            } else {
                result = queue.removeSensor(sensor, disable);
            }
            if (result && !queue.hasSensors()) {
                this.mTriggerListeners.remove(listener);
                queue.dispose();
                if (sensor != null) {
                    Log.d(
                            "SensorManager",
                            "cancelTrigger :: "
                                    + sensor.getHandle()
                                    + ", "
                                    + sensor.getName()
                                    + ", "
                                    + listener.toString());
                } else {
                    Log.d("SensorManager", "cancelTrigger :: " + listener.toString());
                }
            }
            return result;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean flushImpl(SensorEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        synchronized (this.mSensorListeners) {
            SensorEventQueue queue = this.mSensorListeners.get(listener);
            if (queue == null) {
                return false;
            }
            return queue.flush() == 0;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean initDataInjectionImpl(boolean enable, int mode) {
        synchronized (sLock) {
            boolean isDataInjectionModeEnabled = false;
            boolean z = true;
            if (enable) {
                switch (mode) {
                    case 1:
                        isDataInjectionModeEnabled =
                                nativeIsDataInjectionEnabled(this.mNativeInstance);
                        break;
                    case 3:
                        isDataInjectionModeEnabled =
                                nativeIsReplayDataInjectionEnabled(this.mNativeInstance);
                        break;
                    case 4:
                        isDataInjectionModeEnabled =
                                nativeIsHalBypassReplayDataInjectionEnabled(this.mNativeInstance);
                        break;
                }
                if (!isDataInjectionModeEnabled) {
                    Log.e("SensorManager", "The correct Data Injection mode has not been enabled");
                    return false;
                }
                if (sInjectEventQueue != null && sInjectEventQueue.getDataInjectionMode() != mode) {
                    sInjectEventQueue.dispose();
                    sInjectEventQueue = null;
                }
                if (sInjectEventQueue == null) {
                    try {
                        sInjectEventQueue =
                                new InjectEventQueue(
                                        this.mMainLooper,
                                        this,
                                        mode,
                                        this.mContext.getPackageName());
                    } catch (RuntimeException e) {
                        Log.e("SensorManager", "Cannot create InjectEventQueue: " + e);
                    }
                }
                if (sInjectEventQueue == null) {
                    z = false;
                }
                return z;
            }
            if (sInjectEventQueue != null) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            return true;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean injectSensorDataImpl(
            Sensor sensor, float[] values, int accuracy, long timestamp) {
        synchronized (sLock) {
            if (sInjectEventQueue == null) {
                Log.e(
                        "SensorManager",
                        "Data injection mode not activated before calling injectSensorData");
                return false;
            }
            if (sInjectEventQueue.getDataInjectionMode() != 4
                    && !sensor.isDataInjectionSupported()) {
                throw new IllegalArgumentException("sensor does not support data injection");
            }
            int ret =
                    sInjectEventQueue.injectSensorData(
                            sensor.getHandle(), values, accuracy, timestamp);
            if (ret != 0) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            return ret == 0;
        }
    }

    private boolean requestCameraLightSensor(
            Looper cameraLooper, SensorEventListener listener, Sensor sensor, boolean enable) {
        if (enable) {
            if (this.mCameraLightManager == null) {
                this.mCameraLightManager = new CameraLightSensorManager(this.mContext);
            }
            if (this.mCameraLightManager != null
                    && this.mCameraLightManager.registerCameraLightSensor(
                            cameraLooper, listener, sensor)) {
                Log.i("SensorManager", "[CameraLight] registerListener : " + listener.toString());
                return true;
            }
            Log.e("SensorManager", "[CameraLight] registerListener fail : " + listener.toString());
            return false;
        }
        if (this.mCameraLightManager == null
                || !this.mCameraLightManager.unRegisterCameraLightSensor(listener)) {
            return false;
        }
        Log.i("SensorManager", "[CameraLight] unregisteListener : " + listener.toString());
        return true;
    }

    public void cleanupSensorConnection(Sensor sensor) {
        this.mHandleToSensor.remove(Integer.valueOf(sensor.getHandle()));
        if (sensor.getReportingMode() == 2) {
            synchronized (this.mTriggerListeners) {
                HashMap<TriggerEventListener, TriggerEventQueue> triggerListeners =
                        new HashMap<>(this.mTriggerListeners);
                for (TriggerEventListener l : triggerListeners.keySet()) {
                    Log.i(
                            "SensorManager",
                            "removed trigger listener"
                                    + l.toString()
                                    + " due to sensor disconnection");
                    cancelTriggerSensorImpl(l, sensor, true);
                }
            }
            return;
        }
        synchronized (this.mSensorListeners) {
            HashMap<SensorEventListener, SensorEventQueue> sensorListeners =
                    new HashMap<>(this.mSensorListeners);
            for (SensorEventListener l2 : sensorListeners.keySet()) {
                Log.i(
                        "SensorManager",
                        "removed event listener" + l2.toString() + " due to sensor disconnection");
                unregisterListenerImpl(l2, sensor);
            }
        }
    }

    public void updateDynamicSensorList() {
        synchronized (this.mFullDynamicSensorsList) {
            if (this.mDynamicSensorListDirty) {
                List<Sensor> list = new ArrayList<>();
                nativeGetDynamicSensors(this.mNativeInstance, list);
                List<Sensor> updatedList = new ArrayList<>();
                final List<Sensor> addedList = new ArrayList<>();
                final List<Sensor> removedList = new ArrayList<>();
                boolean changed =
                        diffSortedSensorList(
                                this.mFullDynamicSensorsList,
                                list,
                                updatedList,
                                addedList,
                                removedList);
                if (changed) {
                    Log.i("SensorManager", "DYNS dynamic sensor list cached should be updated");
                    this.mFullDynamicSensorsList = updatedList;
                    for (Sensor s : addedList) {
                        this.mHandleToSensor.put(Integer.valueOf(s.getHandle()), s);
                    }
                    Handler mainHandler = new Handler(this.mContext.getMainLooper());
                    synchronized (this.mDynamicSensorCallbacks) {
                        for (Map.Entry<SensorManager.DynamicSensorCallback, Handler> entry :
                                this.mDynamicSensorCallbacks.entrySet()) {
                            final SensorManager.DynamicSensorCallback callback = entry.getKey();
                            Handler handler =
                                    entry.getValue() == null ? mainHandler : entry.getValue();
                            handler.post(
                                    new Runnable() { // from class:
                                        // android.hardware.SystemSensorManager.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            for (Sensor s2 : addedList) {
                                                callback.onDynamicSensorConnected(s2);
                                            }
                                            for (Sensor s3 : removedList) {
                                                callback.onDynamicSensorDisconnected(s3);
                                            }
                                        }
                                    });
                        }
                    }
                    Iterator<Sensor> it = removedList.iterator();
                    while (it.hasNext()) {
                        cleanupSensorConnection(it.next());
                    }
                }
                this.mDynamicSensorListDirty = false;
            }
        }
    }

    private List<Sensor> createRuntimeSensorListLocked(int deviceId) {
        if (Flags.vdmPublicApis()) {
            setupVirtualDeviceListener();
        } else {
            setupRuntimeSensorBroadcastReceiver();
        }
        List<Sensor> list = new ArrayList<>();
        nativeGetRuntimeSensors(this.mNativeInstance, deviceId, list);
        this.mFullRuntimeSensorListByDevice.put(deviceId, list);
        this.mRuntimeSensorListByDeviceByType.put(deviceId, new SparseArray<>());
        for (Sensor s : list) {
            this.mHandleToSensor.put(Integer.valueOf(s.getHandle()), s);
        }
        return list;
    }

    private void setupRuntimeSensorBroadcastReceiver() {
        if (this.mRuntimeSensorBroadcastReceiver == null) {
            this.mRuntimeSensorBroadcastReceiver =
                    new BroadcastReceiver() { // from class: android.hardware.SystemSensorManager.2
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getAction()
                                    .equals(VirtualDeviceManager.ACTION_VIRTUAL_DEVICE_REMOVED)) {
                                synchronized (
                                        SystemSensorManager.this.mFullRuntimeSensorListByDevice) {
                                    int deviceId =
                                            intent.getIntExtra(
                                                    VirtualDeviceManager.EXTRA_VIRTUAL_DEVICE_ID,
                                                    0);
                                    List<Sensor> removedSensors =
                                            (List)
                                                    SystemSensorManager.this
                                                            .mFullRuntimeSensorListByDevice
                                                            .removeReturnOld(deviceId);
                                    if (removedSensors != null) {
                                        for (Sensor s : removedSensors) {
                                            SystemSensorManager.this.cleanupSensorConnection(s);
                                        }
                                    }
                                    SystemSensorManager.this.mRuntimeSensorListByDeviceByType
                                            .remove(deviceId);
                                }
                            }
                        }
                    };
            IntentFilter filter = new IntentFilter("virtual_device_removed");
            filter.addAction(VirtualDeviceManager.ACTION_VIRTUAL_DEVICE_REMOVED);
            this.mContext.registerReceiver(this.mRuntimeSensorBroadcastReceiver, filter, 4);
        }
    }

    private void setupVirtualDeviceListener() {
        if (this.mVirtualDeviceListener != null) {
            return;
        }
        if (this.mVdm == null) {
            this.mVdm =
                    (VirtualDeviceManager)
                            this.mContext.getSystemService(VirtualDeviceManager.class);
            if (this.mVdm == null) {
                return;
            }
        }
        this.mVirtualDeviceListener =
                new VirtualDeviceManager.VirtualDeviceListener() { // from class:
                    // android.hardware.SystemSensorManager.3
                    @Override // android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener
                    public void onVirtualDeviceClosed(int deviceId) {
                        synchronized (SystemSensorManager.this.mFullRuntimeSensorListByDevice) {
                            List<Sensor> removedSensors =
                                    (List)
                                            SystemSensorManager.this.mFullRuntimeSensorListByDevice
                                                    .removeReturnOld(deviceId);
                            if (removedSensors != null) {
                                for (Sensor s : removedSensors) {
                                    SystemSensorManager.this.cleanupSensorConnection(s);
                                }
                            }
                            SystemSensorManager.this.mRuntimeSensorListByDeviceByType.remove(
                                    deviceId);
                        }
                    }
                };
        this.mVdm.registerVirtualDeviceListener(
                this.mContext.getMainExecutor(), this.mVirtualDeviceListener);
    }

    private void setupDynamicSensorBroadcastReceiver() {
        if (this.mDynamicSensorBroadcastReceiver == null) {
            this.mDynamicSensorBroadcastReceiver =
                    new BroadcastReceiver() { // from class: android.hardware.SystemSensorManager.4
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getAction().equals(Intent.ACTION_DYNAMIC_SENSOR_CHANGED)) {
                                Log.i(
                                        "SensorManager",
                                        "DYNS received DYNAMIC_SENSOR_CHANGED broadcast");
                                SystemSensorManager.this.mDynamicSensorListDirty = true;
                                SystemSensorManager.this.updateDynamicSensorList();
                            }
                        }
                    };
            IntentFilter filter = new IntentFilter("dynamic_sensor_change");
            filter.addAction(Intent.ACTION_DYNAMIC_SENSOR_CHANGED);
            this.mContext.registerReceiver(this.mDynamicSensorBroadcastReceiver, filter, 4);
        }
    }

    @Override // android.hardware.SensorManager
    protected void registerDynamicSensorCallbackImpl(
            SensorManager.DynamicSensorCallback callback, Handler handler) {
        Log.i("SensorManager", "DYNS Register dynamic sensor callback");
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        synchronized (this.mDynamicSensorCallbacks) {
            if (this.mDynamicSensorCallbacks.containsKey(callback)) {
                return;
            }
            setupDynamicSensorBroadcastReceiver();
            this.mDynamicSensorCallbacks.put(callback, handler);
        }
    }

    @Override // android.hardware.SensorManager
    protected void unregisterDynamicSensorCallbackImpl(
            SensorManager.DynamicSensorCallback callback) {
        Log.i("SensorManager", "Removing dynamic sensor listener");
        synchronized (this.mDynamicSensorCallbacks) {
            this.mDynamicSensorCallbacks.remove(callback);
        }
    }

    private static boolean diffSortedSensorList(
            List<Sensor> oldList,
            List<Sensor> newList,
            List<Sensor> updated,
            List<Sensor> added,
            List<Sensor> removed) {
        boolean changed = false;
        int i = 0;
        int j = 0;
        while (true) {
            if (j < oldList.size()
                    && (i >= newList.size()
                            || newList.get(i).getHandle() > oldList.get(j).getHandle())) {
                changed = true;
                if (removed != null) {
                    removed.add(oldList.get(j));
                }
                j++;
            } else if (i < newList.size()
                    && (j >= oldList.size()
                            || newList.get(i).getHandle() < oldList.get(j).getHandle())) {
                changed = true;
                if (added != null) {
                    added.add(newList.get(i));
                }
                if (updated != null) {
                    updated.add(newList.get(i));
                }
                i++;
            } else {
                if (i >= newList.size()
                        || j >= oldList.size()
                        || newList.get(i).getHandle() != oldList.get(j).getHandle()) {
                    break;
                }
                if (updated != null) {
                    updated.add(oldList.get(j));
                }
                i++;
                j++;
            }
        }
        return changed;
    }

    @Override // android.hardware.SensorManager
    protected int configureDirectChannelImpl(SensorDirectChannel channel, Sensor sensor, int rate) {
        if (!channel.isOpen()) {
            throw new IllegalStateException("channel is closed");
        }
        if (rate < 0 || rate > 3) {
            throw new IllegalArgumentException("rate parameter invalid");
        }
        if (sensor == null && rate != 0) {
            throw new IllegalArgumentException(
                    "when sensor is null, rate can only be DIRECT_RATE_STOP");
        }
        int sensorHandle = sensor == null ? -1 : sensor.getHandle();
        if (sensor != null
                && isSensorInCappedSet(sensor.getType())
                && rate > 1
                && this.mIsPackageDebuggable
                && !hasHighSamplingRateSensorsPermission()
                && Compatibility.isChangeEnabled(CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
            throw new SecurityException(
                    "To use the sampling rate level "
                            + rate
                            + ", app needs to declare the normal permission"
                            + " HIGH_SAMPLING_RATE_SENSORS.");
        }
        int ret =
                nativeConfigDirectChannel(
                        this.mNativeInstance, channel.getNativeHandle(), sensorHandle, rate);
        if (rate == 0) {
            if (ret == 0) {
                return 1;
            }
            return 0;
        }
        if (ret > 0) {
            return ret;
        }
        return 0;
    }

    @Override // android.hardware.SensorManager
    protected SensorDirectChannel createDirectChannelImpl(
            MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        long size;
        int id;
        int type;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            deviceId = 0;
        }
        if (memoryFile != null) {
            try {
                int fd = memoryFile.getFileDescriptor().getInt$();
                if (memoryFile.length() < 104) {
                    throw new IllegalArgumentException(
                            "Size of MemoryFile has to be greater than 104");
                }
                size = memoryFile.length();
                id = nativeCreateDirectChannel(this.mNativeInstance, deviceId, size, 1, fd, null);
                if (id <= 0) {
                    throw new UncheckedIOException(
                            new IOException("create MemoryFile direct channel failed " + id));
                }
                type = 1;
            } catch (IOException e) {
                throw new IllegalArgumentException("MemoryFile object is not valid");
            }
        } else if (hardwareBuffer != null) {
            if (hardwareBuffer.getFormat() != 33) {
                throw new IllegalArgumentException("Format of HardwareBuffer must be BLOB");
            }
            if (hardwareBuffer.getHeight() == 1) {
                if (hardwareBuffer.getWidth() < 104) {
                    throw new IllegalArgumentException(
                            "Width if HardwareBuffer must be greater than 104");
                }
                if ((hardwareBuffer.getUsage() & 8388608) == 0) {
                    throw new IllegalArgumentException(
                            "HardwareBuffer must set usage flag USAGE_SENSOR_DIRECT_DATA");
                }
                size = hardwareBuffer.getWidth();
                id =
                        nativeCreateDirectChannel(
                                this.mNativeInstance, deviceId, size, 2, -1, hardwareBuffer);
                if (id <= 0) {
                    throw new UncheckedIOException(
                            new IOException("create HardwareBuffer direct channel failed " + id));
                }
                type = 2;
            } else {
                throw new IllegalArgumentException("Height of HardwareBuffer must be 1");
            }
        } else {
            throw new NullPointerException("shared memory object cannot be null");
        }
        return new SensorDirectChannel(this, id, type, size);
    }

    @Override // android.hardware.SensorManager
    protected void destroyDirectChannelImpl(SensorDirectChannel channel) {
        if (channel != null) {
            nativeDestroyDirectChannel(this.mNativeInstance, channel.getNativeHandle());
        }
    }

    private abstract static class BaseEventQueue {
        protected static final int OPERATING_MODE_DATA_INJECTION = 1;
        protected static final int OPERATING_MODE_HAL_BYPASS_REPLAY_DATA_INJECTION = 4;
        protected static final int OPERATING_MODE_NORMAL = 0;
        protected static final int OPERATING_MODE_REPLAY_DATA_INJECTION = 3;
        protected final SystemSensorManager mManager;
        private long mNativeSensorEventQueue;
        private final SparseBooleanArray mActiveSensors = new SparseBooleanArray();
        protected final SparseIntArray mSensorAccuracies = new SparseIntArray();
        protected final SparseIntArray mSensorDiscontinuityCounts = new SparseIntArray();
        private final CloseGuard mCloseGuard = CloseGuard.get();

        private static native void nativeDestroySensorEventQueue(long j);

        private static native int nativeDisableSensor(long j, int i);

        private static native int nativeEnableSensor(long j, int i, int i2, int i3);

        private static native int nativeFlushSensor(long j);

        private static native long nativeInitBaseEventQueue(
                long j,
                WeakReference<BaseEventQueue> weakReference,
                MessageQueue messageQueue,
                String str,
                int i,
                String str2,
                String str3);

        private static native int nativeInjectSensorData(
                long j, int i, float[] fArr, int i2, long j2);

        protected abstract void addSensorEvent(Sensor sensor);

        protected abstract void dispatchFlushCompleteEvent(int i);

        protected abstract void dispatchSensorEvent(int i, float[] fArr, int i2, long j);

        protected abstract void removeSensorEvent(Sensor sensor);

        BaseEventQueue(Looper looper, SystemSensorManager manager, int mode, String packageName) {
            this.mNativeSensorEventQueue =
                    nativeInitBaseEventQueue(
                            manager.mNativeInstance,
                            new WeakReference(this),
                            looper.getQueue(),
                            packageName == null ? "" : packageName,
                            mode,
                            manager.mContext.getOpPackageName(),
                            manager.mContext.getAttributionTag());
            this.mCloseGuard.open("BaseEventQueue.dispose");
            this.mManager = manager;
        }

        public void dispose() {
            dispose(false);
        }

        public boolean addSensor(Sensor sensor, int delayUs, int maxBatchReportLatencyUs) {
            int handle = sensor.getHandle();
            if (this.mActiveSensors.get(handle)) {
                return false;
            }
            this.mActiveSensors.put(handle, true);
            addSensorEvent(sensor);
            if (enableSensor(sensor, delayUs, maxBatchReportLatencyUs) == 0
                    || (maxBatchReportLatencyUs != 0
                            && (maxBatchReportLatencyUs <= 0
                                    || enableSensor(sensor, delayUs, 0) == 0))) {
                return true;
            }
            removeSensor(sensor, false);
            return false;
        }

        public boolean removeAllSensors() {
            for (int i = 0; i < this.mActiveSensors.size(); i++) {
                if (this.mActiveSensors.valueAt(i)) {
                    int handle = this.mActiveSensors.keyAt(i);
                    Sensor sensor =
                            (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(handle));
                    if (sensor != null) {
                        disableSensor(sensor);
                        this.mActiveSensors.put(handle, false);
                        removeSensorEvent(sensor);
                    }
                }
            }
            return true;
        }

        public boolean removeSensor(Sensor sensor, boolean disable) {
            int handle = sensor.getHandle();
            if (!this.mActiveSensors.get(handle)) {
                return false;
            }
            if (disable) {
                disableSensor(sensor);
            }
            this.mActiveSensors.put(sensor.getHandle(), false);
            removeSensorEvent(sensor);
            return true;
        }

        public int flush() {
            if (this.mNativeSensorEventQueue == 0) {
                throw new NullPointerException();
            }
            return nativeFlushSensor(this.mNativeSensorEventQueue);
        }

        public boolean hasSensors() {
            return this.mActiveSensors.indexOfValue(true) >= 0;
        }

        protected void finalize() throws Throwable {
            try {
                dispose(true);
            } finally {
                super.finalize();
            }
        }

        private void dispose(boolean finalized) {
            if (this.mCloseGuard != null) {
                if (finalized) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            if (this.mNativeSensorEventQueue != 0) {
                nativeDestroySensorEventQueue(this.mNativeSensorEventQueue);
                this.mNativeSensorEventQueue = 0L;
            }
        }

        private int enableSensor(Sensor sensor, int rateUs, int maxBatchReportLatencyUs) {
            if (this.mNativeSensorEventQueue == 0) {
                throw new NullPointerException();
            }
            if (sensor == null) {
                throw new NullPointerException();
            }
            if (this.mManager.isSensorInCappedSet(sensor.getType())
                    && rateUs < 5000
                    && this.mManager.mIsPackageDebuggable
                    && !this.mManager.hasHighSamplingRateSensorsPermission()
                    && Compatibility.isChangeEnabled(
                            SystemSensorManager.CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
                throw new SecurityException(
                        "To use the sampling rate of "
                                + rateUs
                                + " microseconds, app needs to declare the normal permission"
                                + " HIGH_SAMPLING_RATE_SENSORS.");
            }
            return nativeEnableSensor(
                    this.mNativeSensorEventQueue,
                    sensor.getHandle(),
                    rateUs,
                    maxBatchReportLatencyUs);
        }

        protected int injectSensorDataBase(
                int handle, float[] values, int accuracy, long timestamp) {
            return nativeInjectSensorData(
                    this.mNativeSensorEventQueue, handle, values, accuracy, timestamp);
        }

        private int disableSensor(Sensor sensor) {
            if (this.mNativeSensorEventQueue == 0) {
                throw new NullPointerException();
            }
            if (sensor == null) {
                throw new NullPointerException();
            }
            return nativeDisableSensor(this.mNativeSensorEventQueue, sensor.getHandle());
        }

        protected void dispatchAdditionalInfoEvent(
                int handle, int type, int serial, float[] floatValues, int[] intValues) {}
    }

    static final class SensorEventQueue extends BaseEventQueue {
        private final SensorEventListener mListener;
        private final SparseArray<SensorEvent> mSensorsEvents;

        public SensorEventQueue(
                SensorEventListener listener,
                Looper looper,
                SystemSensorManager manager,
                String packageName) {
            super(looper, manager, 0, packageName);
            this.mSensorsEvents = new SparseArray<>();
            this.mListener = listener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(Sensor sensor) {
            SensorEvent t =
                    new SensorEvent(
                            Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.put(sensor.getHandle(), t);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(Sensor sensor) {
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(
                int handle, float[] values, int inAccuracy, long timestamp) {
            SensorEvent t;
            Sensor sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(handle));
            if (sensor == null) {
                return;
            }
            synchronized (this.mSensorsEvents) {
                t = this.mSensorsEvents.get(handle);
            }
            if (t == null) {
                return;
            }
            System.arraycopy(values, 0, t.values, 0, t.values.length);
            t.timestamp = timestamp;
            t.accuracy = inAccuracy;
            t.sensor = sensor;
            int accuracy = this.mSensorAccuracies.get(handle);
            if (t.accuracy >= 0 && accuracy != t.accuracy) {
                this.mSensorAccuracies.put(handle, t.accuracy);
                this.mListener.onAccuracyChanged(t.sensor, t.accuracy);
            }
            t.firstEventAfterDiscontinuity = false;
            if (t.sensor.getType() == 37) {
                int lastCount = this.mSensorDiscontinuityCounts.get(handle);
                int curCount = Float.floatToIntBits(values[6]);
                if (lastCount >= 0 && lastCount != curCount) {
                    this.mSensorDiscontinuityCounts.put(handle, curCount);
                    t.firstEventAfterDiscontinuity = true;
                }
            }
            this.mListener.onSensorChanged(t);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int handle) {
            Sensor sensor;
            if (!(this.mListener instanceof SensorEventListener2)
                    || (sensor =
                                    (Sensor)
                                            this.mManager.mHandleToSensor.get(
                                                    Integer.valueOf(handle)))
                            == null) {
                return;
            }
            ((SensorEventListener2) this.mListener).onFlushCompleted(sensor);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchAdditionalInfoEvent(
                int handle, int type, int serial, float[] floatValues, int[] intValues) {
            Sensor sensor;
            if (!(this.mListener instanceof SensorEventCallback)
                    || (sensor =
                                    (Sensor)
                                            this.mManager.mHandleToSensor.get(
                                                    Integer.valueOf(handle)))
                            == null) {
                return;
            }
            SensorAdditionalInfo info =
                    new SensorAdditionalInfo(sensor, type, serial, intValues, floatValues);
            ((SensorEventCallback) this.mListener).onSensorAdditionalInfo(info);
        }
    }

    static final class TriggerEventQueue extends BaseEventQueue {
        private final TriggerEventListener mListener;
        private final SparseArray<TriggerEvent> mTriggerEvents;

        public TriggerEventQueue(
                TriggerEventListener listener,
                Looper looper,
                SystemSensorManager manager,
                String packageName) {
            super(looper, manager, 0, packageName);
            this.mTriggerEvents = new SparseArray<>();
            this.mListener = listener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(Sensor sensor) {
            TriggerEvent t =
                    new TriggerEvent(
                            Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.put(sensor.getHandle(), t);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(Sensor sensor) {
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(
                int handle, float[] values, int accuracy, long timestamp) {
            TriggerEvent t;
            Sensor sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(handle));
            if (sensor == null) {
                return;
            }
            synchronized (this.mTriggerEvents) {
                t = this.mTriggerEvents.get(handle);
            }
            if (t == null) {
                Log.e("SensorManager", "Error: Trigger Event is null for Sensor: " + sensor);
                return;
            }
            System.arraycopy(values, 0, t.values, 0, t.values.length);
            t.timestamp = timestamp;
            t.sensor = sensor;
            this.mManager.cancelTriggerSensorImpl(this.mListener, sensor, false);
            this.mListener.onTrigger(t);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int handle) {}
    }

    final class InjectEventQueue extends BaseEventQueue {
        private int mMode;

        public InjectEventQueue(
                Looper looper, SystemSensorManager manager, int mode, String packageName) {
            super(looper, manager, mode, packageName);
            this.mMode = mode;
        }

        int injectSensorData(int handle, float[] values, int accuracy, long timestamp) {
            return injectSensorDataBase(handle, values, accuracy, timestamp);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(
                int handle, float[] values, int accuracy, long timestamp) {}

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int handle) {}

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void addSensorEvent(Sensor sensor) {}

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void removeSensorEvent(Sensor sensor) {}

        int getDataInjectionMode() {
            return this.mMode;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean setOperationParameterImpl(SensorAdditionalInfo parameter) {
        int handle = parameter.sensor != null ? parameter.sensor.getHandle() : -1;
        return nativeSetOperationParameter(
                        this.mNativeInstance,
                        handle,
                        parameter.type,
                        parameter.floatValues,
                        parameter.intValues)
                == 0;
    }

    private boolean isDeviceSensorPolicyDefault(int deviceId) {
        if (deviceId == 0) {
            return true;
        }
        if (this.mVdm == null) {
            this.mVdm =
                    (VirtualDeviceManager)
                            this.mContext.getSystemService(VirtualDeviceManager.class);
        }
        if (this.mVdm == null || this.mVdm.getDevicePolicy(deviceId, 0) == 0) {
            return true;
        }
        return false;
    }

    public boolean isSensorInCappedSet(int sensorType) {
        return sensorType == 1
                || sensorType == 35
                || sensorType == 4
                || sensorType == 16
                || sensorType == 2
                || sensorType == 14;
    }

    public boolean hasHighSamplingRateSensorsPermission() {
        if (!this.mHasHighSamplingRateSensorsPermission.isPresent()) {
            boolean granted =
                    this.mContext
                                    .getPackageManager()
                                    .checkPermission(
                                            "android.permission.HIGH_SAMPLING_RATE_SENSORS",
                                            this.mContext.getApplicationInfo().packageName)
                            == 0;
            this.mHasHighSamplingRateSensorsPermission = Optional.of(Boolean.valueOf(granted));
        }
        return this.mHasHighSamplingRateSensorsPermission.get().booleanValue();
    }
}
