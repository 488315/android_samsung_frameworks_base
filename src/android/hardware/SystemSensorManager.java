package android.hardware;

import android.companion.virtual.VirtualDeviceManager;
import android.compat.Compatibility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.hardware.SensorManager;
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

/* loaded from: classes2.dex */
public class SystemSensorManager extends SensorManager {
    private static final int CAPPED_SAMPLING_PERIOD_US = 5000;
    private static final int CAPPED_SAMPLING_RATE_LEVEL = 1;
    static final long CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION = 136069189;
    private static final boolean DEBUG_DYNAMIC_SENSOR = true;
    private static final String HIGH_SAMPLING_RATE_SENSORS_PERMISSION = "android.permission.HIGH_SAMPLING_RATE_SENSORS";
    private static final int MAX_LISTENER_COUNT = 128;
    private static final int MIN_DIRECT_CHANNEL_BUFFER_SIZE = 104;
    private static InjectEventQueue sInjectEventQueue = null;
    private static final Object sLock = new Object();
    private static boolean sNativeClassInited = false;
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
    private final ArrayList<Sensor> mFullSensorsList = new ArrayList<>();
    private List<Sensor> mFullDynamicSensorsList = new ArrayList();
    private final SparseArray<List<Sensor>> mFullRuntimeSensorListByDevice = new SparseArray<>();
    private final SparseArray<SparseArray<List<Sensor>>> mRuntimeSensorListByDeviceByType = new SparseArray<>();
    private boolean mDynamicSensorListDirty = true;
    private final HashMap<Integer, Sensor> mHandleToSensor = new HashMap<>();
    private final HashMap<SensorEventListener, SensorEventQueue> mSensorListeners = new HashMap<>();
    private final HashMap<TriggerEventListener, TriggerEventQueue> mTriggerListeners = new HashMap<>();
    private HashMap<SensorManager.DynamicSensorCallback, Handler> mDynamicSensorCallbacks = new HashMap<>();
    private Optional<Boolean> mHasHighSamplingRateSensorsPermission = Optional.empty();

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSensorInCappedSet(int i) {
        return i == 1 || i == 35 || i == 4 || i == 16 || i == 2 || i == 14;
    }

    private static native void nativeClassInit();

    private static native int nativeConfigDirectChannel(long j, int i, int i2, int i3);

    private static native long nativeCreate(String str);

    private static native int nativeCreateDirectChannel(long j, int i, long j2, int i2, int i3, HardwareBuffer hardwareBuffer);

    private static native void nativeDestroyDirectChannel(long j, int i);

    private static native boolean nativeGetDefaultDeviceSensorAtIndex(long j, Sensor sensor, int i);

    private static native void nativeGetDynamicSensors(long j, List<Sensor> list);

    private static native void nativeGetRuntimeSensors(long j, int i, List<Sensor> list);

    private static native boolean nativeGetSensorAtIndex(long j, Sensor sensor, int i);

    private static native boolean nativeIsDataInjectionEnabled(long j);

    private static native boolean nativeIsHalBypassReplayDataInjectionEnabled(long j);

    private static native boolean nativeIsReplayDataInjectionEnabled(long j);

    private static native int nativeSetOperationParameter(long j, int i, int i2, float[] fArr, int[] iArr);

    public SystemSensorManager(Context context, Looper looper) {
        synchronized (sLock) {
            if (!sNativeClassInited) {
                sNativeClassInited = true;
                nativeClassInit();
            }
        }
        this.mMainLooper = looper;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        this.mTargetSdkLevel = applicationInfo.targetSdkVersion;
        this.mContext = context;
        this.mNativeInstance = nativeCreate(context.getOpPackageName());
        int i = applicationInfo.flags & 2;
        int i2 = 0;
        this.mIsPackageDebuggable = i != 0;
        while (true) {
            Sensor sensor = new Sensor();
            if (!nativeGetDefaultDeviceSensorAtIndex(this.mNativeInstance, sensor, i2)) {
                return;
            }
            this.mFullSensorsList.add(sensor);
            this.mHandleToSensor.put(Integer.valueOf(sensor.getHandle()), sensor);
            i2++;
        }
    }

    @Override // android.hardware.SensorManager
    public List<Sensor> getSensorList(int i) {
        List<Sensor> list;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return super.getSensorList(i);
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            List<Sensor> list2 = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (list2 == null) {
                list2 = createRuntimeSensorListLocked(deviceId);
            }
            SparseArray<List<Sensor>> sparseArray = this.mRuntimeSensorListByDeviceByType.get(deviceId);
            list = sparseArray.get(i);
            if (list == null) {
                if (i != -1) {
                    ArrayList arrayList = new ArrayList();
                    for (Sensor sensor : list2) {
                        if (sensor.getType() == i) {
                            arrayList.add(sensor);
                        }
                    }
                    list2 = arrayList;
                }
                list = Collections.unmodifiableList(list2);
                sparseArray.append(i, list);
            }
        }
        return list;
    }

    @Override // android.hardware.SensorManager
    protected List<Sensor> getFullSensorList() {
        List<Sensor> list;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return this.mFullSensorsList;
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            list = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (list == null) {
                list = createRuntimeSensorListLocked(deviceId);
            }
        }
        return list;
    }

    @Override // android.hardware.SensorManager
    public Sensor getSensorByHandle(int i) {
        return this.mHandleToSensor.get(Integer.valueOf(i));
    }

    @Override // android.hardware.SensorManager
    protected List<Sensor> getFullDynamicSensorList() {
        setupDynamicSensorBroadcastReceiver();
        updateDynamicSensorList();
        return this.mFullDynamicSensorsList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x009f, code lost:
    
        if (r20 < 5000) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0113 A[Catch: all -> 0x0204, TryCatch #0 {, blocks: (B:29:0x00a6, B:32:0x00b2, B:33:0x00b9, B:35:0x00c3, B:36:0x00d8, B:39:0x00e7, B:41:0x00f5, B:46:0x0113, B:47:0x0127, B:49:0x0132, B:50:0x016e, B:52:0x0170, B:53:0x01bb, B:55:0x0200, B:57:0x0202, B:59:0x0105, B:60:0x00d0, B:61:0x00b7, B:62:0x0176, B:64:0x0180, B:65:0x01b9), top: B:28:0x00a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0132 A[Catch: all -> 0x0204, TryCatch #0 {, blocks: (B:29:0x00a6, B:32:0x00b2, B:33:0x00b9, B:35:0x00c3, B:36:0x00d8, B:39:0x00e7, B:41:0x00f5, B:46:0x0113, B:47:0x0127, B:49:0x0132, B:50:0x016e, B:52:0x0170, B:53:0x01bb, B:55:0x0200, B:57:0x0202, B:59:0x0105, B:60:0x00d0, B:61:0x00b7, B:62:0x0176, B:64:0x0180, B:65:0x01b9), top: B:28:0x00a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0170 A[Catch: all -> 0x0204, TryCatch #0 {, blocks: (B:29:0x00a6, B:32:0x00b2, B:33:0x00b9, B:35:0x00c3, B:36:0x00d8, B:39:0x00e7, B:41:0x00f5, B:46:0x0113, B:47:0x0127, B:49:0x0132, B:50:0x016e, B:52:0x0170, B:53:0x01bb, B:55:0x0200, B:57:0x0202, B:59:0x0105, B:60:0x00d0, B:61:0x00b7, B:62:0x0176, B:64:0x0180, B:65:0x01b9), top: B:28:0x00a6 }] */
    @Override // android.hardware.SensorManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean registerListenerImpl(android.hardware.SensorEventListener r18, android.hardware.Sensor r19, int r20, android.os.Handler r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 547
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.SystemSensorManager.registerListenerImpl(android.hardware.SensorEventListener, android.hardware.Sensor, int, android.os.Handler, int, int):boolean");
    }

    @Override // android.hardware.SensorManager
    protected void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
        boolean removeSensor;
        CameraLightSensorManager cameraLightSensorManager;
        if (sensor == null || sensor.getReportingMode() != 2) {
            if (sensorEventListener != null && (cameraLightSensorManager = this.mCameraLightManager) != null && cameraLightSensorManager.isAllowListedListener(sensorEventListener.toString())) {
                requestCameraLightSensor(null, sensorEventListener, sensor, false);
            }
            synchronized (this.mSensorListeners) {
                SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
                if (sensorEventQueue != null) {
                    if (sensor == null) {
                        removeSensor = sensorEventQueue.removeAllSensors();
                    } else {
                        removeSensor = sensorEventQueue.removeSensor(sensor, true);
                    }
                    if (removeSensor && !sensorEventQueue.hasSensors()) {
                        this.mSensorListeners.remove(sensorEventListener);
                        sensorEventQueue.dispose();
                        if (Binder.getCallingUid() >= 10000) {
                            Log.d("SensorManager", "unregisterListener");
                        } else if (sensor != null && sensorEventListener != null) {
                            Log.d("SensorManager", "unregisterListener :: " + sensor.getHandle() + ", " + sensor.getName() + ", " + sensorEventListener.toString());
                        } else if (sensorEventListener != null) {
                            Log.d("SensorManager", "unregisterListener :: " + sensorEventListener.toString());
                        } else {
                            Log.d("SensorManager", "unregisterListener :: listener is null");
                        }
                    }
                }
            }
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
        String name;
        if (sensor == null) {
            throw new IllegalArgumentException("sensor cannot be null");
        }
        if (triggerEventListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (sensor.getReportingMode() != 2) {
            return false;
        }
        if (this.mTriggerListeners.size() >= 128) {
            throw new IllegalStateException("request failed, the trigger listeners size has exceeded the maximum limit 128");
        }
        synchronized (this.mTriggerListeners) {
            TriggerEventQueue triggerEventQueue = this.mTriggerListeners.get(triggerEventListener);
            if (triggerEventQueue == null) {
                if (triggerEventListener.getClass().getEnclosingClass() != null) {
                    name = triggerEventListener.getClass().getEnclosingClass().getName();
                } else {
                    name = triggerEventListener.getClass().getName();
                }
                TriggerEventQueue triggerEventQueue2 = new TriggerEventQueue(triggerEventListener, this.mMainLooper, this, name);
                if (!triggerEventQueue2.addSensor(sensor, 0, 0)) {
                    triggerEventQueue2.dispose();
                    Log.d("SensorManager", "requestTrigger :: fail (1) :: " + sensor.getHandle() + ", " + sensor.getName() + ", " + triggerEventListener.toString());
                    return false;
                }
                this.mTriggerListeners.put(triggerEventListener, triggerEventQueue2);
            } else if (!triggerEventQueue.addSensor(sensor, 0, 0)) {
                Log.d("SensorManager", "requestTrigger :: fail (2) :: " + sensor.getHandle() + ", " + sensor.getName() + ", " + triggerEventListener.toString());
                return false;
            }
            Log.d("SensorManager", "requestTrigger :: " + sensor.getHandle() + ", " + sensor.getName() + ", " + triggerEventListener.toString());
            return true;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z) {
        boolean removeSensor;
        if (sensor != null && sensor.getReportingMode() != 2) {
            return false;
        }
        synchronized (this.mTriggerListeners) {
            TriggerEventQueue triggerEventQueue = this.mTriggerListeners.get(triggerEventListener);
            if (triggerEventQueue == null) {
                return false;
            }
            if (sensor == null) {
                removeSensor = triggerEventQueue.removeAllSensors();
            } else {
                removeSensor = triggerEventQueue.removeSensor(sensor, z);
            }
            if (removeSensor && !triggerEventQueue.hasSensors()) {
                this.mTriggerListeners.remove(triggerEventListener);
                triggerEventQueue.dispose();
                if (sensor != null) {
                    Log.d("SensorManager", "cancelTrigger :: " + sensor.getHandle() + ", " + sensor.getName() + ", " + triggerEventListener.toString());
                } else {
                    Log.d("SensorManager", "cancelTrigger :: " + triggerEventListener.toString());
                }
            }
            return removeSensor;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean flushImpl(SensorEventListener sensorEventListener) {
        if (sensorEventListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        synchronized (this.mSensorListeners) {
            SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
            if (sensorEventQueue == null) {
                return false;
            }
            return sensorEventQueue.flush() == 0;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean initDataInjectionImpl(boolean z, int i) {
        boolean nativeIsDataInjectionEnabled;
        synchronized (sLock) {
            boolean z2 = true;
            if (!z) {
                InjectEventQueue injectEventQueue = sInjectEventQueue;
                if (injectEventQueue != null) {
                    injectEventQueue.dispose();
                    sInjectEventQueue = null;
                }
                return true;
            }
            if (i == 1) {
                nativeIsDataInjectionEnabled = nativeIsDataInjectionEnabled(this.mNativeInstance);
            } else if (i == 3) {
                nativeIsDataInjectionEnabled = nativeIsReplayDataInjectionEnabled(this.mNativeInstance);
            } else {
                nativeIsDataInjectionEnabled = i != 4 ? false : nativeIsHalBypassReplayDataInjectionEnabled(this.mNativeInstance);
            }
            if (!nativeIsDataInjectionEnabled) {
                Log.e("SensorManager", "The correct Data Injection mode has not been enabled");
                return false;
            }
            InjectEventQueue injectEventQueue2 = sInjectEventQueue;
            if (injectEventQueue2 != null && injectEventQueue2.getDataInjectionMode() != i) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            if (sInjectEventQueue == null) {
                try {
                    sInjectEventQueue = new InjectEventQueue(this, this.mMainLooper, this, i, this.mContext.getPackageName());
                } catch (RuntimeException e) {
                    Log.e("SensorManager", "Cannot create InjectEventQueue: " + e);
                }
            }
            if (sInjectEventQueue == null) {
                z2 = false;
            }
            return z2;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
        synchronized (sLock) {
            InjectEventQueue injectEventQueue = sInjectEventQueue;
            if (injectEventQueue == null) {
                Log.e("SensorManager", "Data injection mode not activated before calling injectSensorData");
                return false;
            }
            if (injectEventQueue.getDataInjectionMode() != 4 && !sensor.isDataInjectionSupported()) {
                throw new IllegalArgumentException("sensor does not support data injection");
            }
            int injectSensorData = sInjectEventQueue.injectSensorData(sensor.getHandle(), fArr, i, j);
            if (injectSensorData != 0) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            return injectSensorData == 0;
        }
    }

    private boolean requestCameraLightSensor(Looper looper, SensorEventListener sensorEventListener, Sensor sensor, boolean z) {
        if (z) {
            if (this.mCameraLightManager == null) {
                this.mCameraLightManager = new CameraLightSensorManager(this.mContext);
            }
            CameraLightSensorManager cameraLightSensorManager = this.mCameraLightManager;
            if (cameraLightSensorManager != null && cameraLightSensorManager.registerCameraLightSensor(looper, sensorEventListener, sensor)) {
                Log.i("SensorManager", "[CameraLight] registerListener : " + sensorEventListener.toString());
                return true;
            }
            Log.e("SensorManager", "[CameraLight] registerListener fail : " + sensorEventListener.toString());
            return false;
        }
        CameraLightSensorManager cameraLightSensorManager2 = this.mCameraLightManager;
        if (cameraLightSensorManager2 == null || !cameraLightSensorManager2.unRegisterCameraLightSensor(sensorEventListener)) {
            return false;
        }
        Log.i("SensorManager", "[CameraLight] unregisteListener : " + sensorEventListener.toString());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupSensorConnection(Sensor sensor) {
        this.mHandleToSensor.remove(Integer.valueOf(sensor.getHandle()));
        if (sensor.getReportingMode() == 2) {
            synchronized (this.mTriggerListeners) {
                for (TriggerEventListener triggerEventListener : new HashMap(this.mTriggerListeners).keySet()) {
                    Log.i("SensorManager", "removed trigger listener" + triggerEventListener.toString() + " due to sensor disconnection");
                    cancelTriggerSensorImpl(triggerEventListener, sensor, true);
                }
            }
            return;
        }
        synchronized (this.mSensorListeners) {
            for (SensorEventListener sensorEventListener : new HashMap(this.mSensorListeners).keySet()) {
                Log.i("SensorManager", "removed event listener" + sensorEventListener.toString() + " due to sensor disconnection");
                unregisterListenerImpl(sensorEventListener, sensor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDynamicSensorList() {
        synchronized (this.mFullDynamicSensorsList) {
            if (this.mDynamicSensorListDirty) {
                ArrayList arrayList = new ArrayList();
                nativeGetDynamicSensors(this.mNativeInstance, arrayList);
                ArrayList arrayList2 = new ArrayList();
                final ArrayList<Sensor> arrayList3 = new ArrayList();
                final ArrayList arrayList4 = new ArrayList();
                if (diffSortedSensorList(this.mFullDynamicSensorsList, arrayList, arrayList2, arrayList3, arrayList4)) {
                    Log.i("SensorManager", "DYNS dynamic sensor list cached should be updated");
                    this.mFullDynamicSensorsList = arrayList2;
                    for (Sensor sensor : arrayList3) {
                        this.mHandleToSensor.put(Integer.valueOf(sensor.getHandle()), sensor);
                    }
                    Handler handler = new Handler(this.mContext.getMainLooper());
                    synchronized (this.mDynamicSensorCallbacks) {
                        for (Map.Entry<SensorManager.DynamicSensorCallback, Handler> entry : this.mDynamicSensorCallbacks.entrySet()) {
                            final SensorManager.DynamicSensorCallback key = entry.getKey();
                            (entry.getValue() == null ? handler : entry.getValue()).post(new Runnable(this) { // from class: android.hardware.SystemSensorManager.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    Iterator it = arrayList3.iterator();
                                    while (it.hasNext()) {
                                        key.onDynamicSensorConnected((Sensor) it.next());
                                    }
                                    Iterator it2 = arrayList4.iterator();
                                    while (it2.hasNext()) {
                                        key.onDynamicSensorDisconnected((Sensor) it2.next());
                                    }
                                }
                            });
                        }
                    }
                    Iterator it = arrayList4.iterator();
                    while (it.hasNext()) {
                        cleanupSensorConnection((Sensor) it.next());
                    }
                }
                this.mDynamicSensorListDirty = false;
            }
        }
    }

    private List<Sensor> createRuntimeSensorListLocked(int i) {
        setupVirtualDeviceListener();
        ArrayList<Sensor> arrayList = new ArrayList();
        nativeGetRuntimeSensors(this.mNativeInstance, i, arrayList);
        this.mFullRuntimeSensorListByDevice.put(i, arrayList);
        this.mRuntimeSensorListByDeviceByType.put(i, new SparseArray<>());
        for (Sensor sensor : arrayList) {
            this.mHandleToSensor.put(Integer.valueOf(sensor.getHandle()), sensor);
        }
        return arrayList;
    }

    private void setupVirtualDeviceListener() {
        if (this.mVirtualDeviceListener != null) {
            return;
        }
        if (this.mVdm == null) {
            VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
            this.mVdm = virtualDeviceManager;
            if (virtualDeviceManager == null) {
                return;
            }
        }
        this.mVirtualDeviceListener = new VirtualDeviceManager.VirtualDeviceListener() { // from class: android.hardware.SystemSensorManager.2
            @Override // android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener
            public void onVirtualDeviceClosed(int i) {
                synchronized (SystemSensorManager.this.mFullRuntimeSensorListByDevice) {
                    List list = (List) SystemSensorManager.this.mFullRuntimeSensorListByDevice.removeReturnOld(i);
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            SystemSensorManager.this.cleanupSensorConnection((Sensor) it.next());
                        }
                    }
                    SystemSensorManager.this.mRuntimeSensorListByDeviceByType.remove(i);
                }
            }
        };
        this.mVdm.registerVirtualDeviceListener(this.mContext.getMainExecutor(), this.mVirtualDeviceListener);
    }

    private void setupDynamicSensorBroadcastReceiver() {
        if (this.mDynamicSensorBroadcastReceiver == null) {
            this.mDynamicSensorBroadcastReceiver = new BroadcastReceiver() { // from class: android.hardware.SystemSensorManager.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_DYNAMIC_SENSOR_CHANGED)) {
                        Log.i("SensorManager", "DYNS received DYNAMIC_SENSOR_CHANGED broadcast");
                        SystemSensorManager.this.mDynamicSensorListDirty = true;
                        SystemSensorManager.this.updateDynamicSensorList();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter("dynamic_sensor_change");
            intentFilter.addAction(Intent.ACTION_DYNAMIC_SENSOR_CHANGED);
            this.mContext.registerReceiver(this.mDynamicSensorBroadcastReceiver, intentFilter, 4);
        }
    }

    @Override // android.hardware.SensorManager
    protected void registerDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        Log.i("SensorManager", "DYNS Register dynamic sensor callback");
        if (dynamicSensorCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        synchronized (this.mDynamicSensorCallbacks) {
            if (this.mDynamicSensorCallbacks.containsKey(dynamicSensorCallback)) {
                return;
            }
            setupDynamicSensorBroadcastReceiver();
            this.mDynamicSensorCallbacks.put(dynamicSensorCallback, handler);
        }
    }

    @Override // android.hardware.SensorManager
    protected void unregisterDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        Log.i("SensorManager", "Removing dynamic sensor listener");
        synchronized (this.mDynamicSensorCallbacks) {
            this.mDynamicSensorCallbacks.remove(dynamicSensorCallback);
        }
    }

    private static boolean diffSortedSensorList(List<Sensor> list, List<Sensor> list2, List<Sensor> list3, List<Sensor> list4, List<Sensor> list5) {
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (true) {
            if (i < list.size() && (i2 >= list2.size() || list2.get(i2).getHandle() > list.get(i).getHandle())) {
                if (list5 != null) {
                    list5.add(list.get(i));
                }
                i++;
            } else if (i2 < list2.size() && (i >= list.size() || list2.get(i2).getHandle() < list.get(i).getHandle())) {
                if (list4 != null) {
                    list4.add(list2.get(i2));
                }
                if (list3 != null) {
                    list3.add(list2.get(i2));
                }
                i2++;
            } else {
                if (i2 >= list2.size() || i >= list.size() || list2.get(i2).getHandle() != list.get(i).getHandle()) {
                    break;
                }
                if (list3 != null) {
                    list3.add(list.get(i));
                }
                i2++;
                i++;
            }
            z = true;
        }
        return z;
    }

    @Override // android.hardware.SensorManager
    protected int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
        if (!sensorDirectChannel.isOpen()) {
            throw new IllegalStateException("channel is closed");
        }
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("rate parameter invalid");
        }
        if (sensor == null && i != 0) {
            throw new IllegalArgumentException("when sensor is null, rate can only be DIRECT_RATE_STOP");
        }
        int handle = sensor == null ? -1 : sensor.getHandle();
        if (sensor != null && isSensorInCappedSet(sensor.getType()) && i > 1 && this.mIsPackageDebuggable && !hasHighSamplingRateSensorsPermission() && Compatibility.isChangeEnabled(CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
            throw new SecurityException("To use the sampling rate level " + i + ", app needs to declare the normal permission HIGH_SAMPLING_RATE_SENSORS.");
        }
        int nativeConfigDirectChannel = nativeConfigDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle(), handle, i);
        if (i == 0) {
            return nativeConfigDirectChannel == 0 ? 1 : 0;
        }
        if (nativeConfigDirectChannel > 0) {
            return nativeConfigDirectChannel;
        }
        return 0;
    }

    @Override // android.hardware.SensorManager
    protected SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        long length;
        int nativeCreateDirectChannel;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            deviceId = 0;
        }
        int i = deviceId;
        int i2 = 1;
        if (memoryFile != null) {
            try {
                int int$ = memoryFile.getFileDescriptor().getInt$();
                if (memoryFile.length() < 104) {
                    throw new IllegalArgumentException("Size of MemoryFile has to be greater than 104");
                }
                length = memoryFile.length();
                nativeCreateDirectChannel = nativeCreateDirectChannel(this.mNativeInstance, i, length, 1, int$, null);
                if (nativeCreateDirectChannel <= 0) {
                    throw new UncheckedIOException(new IOException("create MemoryFile direct channel failed " + nativeCreateDirectChannel));
                }
            } catch (IOException unused) {
                throw new IllegalArgumentException("MemoryFile object is not valid");
            }
        } else if (hardwareBuffer != null) {
            if (hardwareBuffer.getFormat() != 33) {
                throw new IllegalArgumentException("Format of HardwareBuffer must be BLOB");
            }
            if (hardwareBuffer.getHeight() != 1) {
                throw new IllegalArgumentException("Height of HardwareBuffer must be 1");
            }
            if (hardwareBuffer.getWidth() < 104) {
                throw new IllegalArgumentException("Width if HardwareBuffer must be greater than 104");
            }
            if ((hardwareBuffer.getUsage() & 8388608) == 0) {
                throw new IllegalArgumentException("HardwareBuffer must set usage flag USAGE_SENSOR_DIRECT_DATA");
            }
            length = hardwareBuffer.getWidth();
            nativeCreateDirectChannel = nativeCreateDirectChannel(this.mNativeInstance, i, length, 2, -1, hardwareBuffer);
            if (nativeCreateDirectChannel <= 0) {
                throw new UncheckedIOException(new IOException("create HardwareBuffer direct channel failed " + nativeCreateDirectChannel));
            }
            i2 = 2;
        } else {
            throw new NullPointerException("shared memory object cannot be null");
        }
        return new SensorDirectChannel(this, nativeCreateDirectChannel, i2, length);
    }

    @Override // android.hardware.SensorManager
    protected void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        if (sensorDirectChannel != null) {
            nativeDestroyDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle());
        }
    }

    private static abstract class BaseEventQueue {
        protected static final int OPERATING_MODE_DATA_INJECTION = 1;
        protected static final int OPERATING_MODE_HAL_BYPASS_REPLAY_DATA_INJECTION = 4;
        protected static final int OPERATING_MODE_NORMAL = 0;
        protected static final int OPERATING_MODE_REPLAY_DATA_INJECTION = 3;
        private final CloseGuard mCloseGuard;
        protected final SystemSensorManager mManager;
        private long mNativeSensorEventQueue;
        private final SparseBooleanArray mActiveSensors = new SparseBooleanArray();
        protected final SparseIntArray mSensorAccuracies = new SparseIntArray();
        protected final SparseIntArray mSensorDiscontinuityCounts = new SparseIntArray();

        private static native void nativeDestroySensorEventQueue(long j);

        private static native int nativeDisableSensor(long j, int i);

        private static native int nativeEnableSensor(long j, int i, int i2, int i3);

        private static native int nativeFlushSensor(long j);

        private static native long nativeInitBaseEventQueue(long j, WeakReference<BaseEventQueue> weakReference, MessageQueue messageQueue, String str, int i, String str2, String str3);

        private static native int nativeInjectSensorData(long j, int i, float[] fArr, int i2, long j2);

        protected abstract void addSensorEvent(Sensor sensor);

        protected void dispatchAdditionalInfoEvent(int i, int i2, int i3, float[] fArr, int[] iArr) {
        }

        protected abstract void dispatchFlushCompleteEvent(int i);

        protected abstract void dispatchSensorEvent(int i, float[] fArr, int i2, long j);

        protected abstract void removeSensorEvent(Sensor sensor);

        BaseEventQueue(Looper looper, SystemSensorManager systemSensorManager, int i, String str) {
            CloseGuard closeGuard = CloseGuard.get();
            this.mCloseGuard = closeGuard;
            this.mNativeSensorEventQueue = nativeInitBaseEventQueue(systemSensorManager.mNativeInstance, new WeakReference(this), looper.getQueue(), str == null ? "" : str, i, systemSensorManager.mContext.getOpPackageName(), systemSensorManager.mContext.getAttributionTag());
            closeGuard.open("BaseEventQueue.dispose");
            this.mManager = systemSensorManager;
        }

        public void dispose() {
            dispose(false);
        }

        public boolean addSensor(Sensor sensor, int i, int i2) {
            int handle = sensor.getHandle();
            if (this.mActiveSensors.get(handle)) {
                return false;
            }
            this.mActiveSensors.put(handle, true);
            addSensorEvent(sensor);
            if (enableSensor(sensor, i, i2) == 0 || (i2 != 0 && (i2 <= 0 || enableSensor(sensor, i, 0) == 0))) {
                return true;
            }
            removeSensor(sensor, false);
            return false;
        }

        public boolean removeAllSensors() {
            for (int i = 0; i < this.mActiveSensors.size(); i++) {
                if (this.mActiveSensors.valueAt(i)) {
                    int keyAt = this.mActiveSensors.keyAt(i);
                    Sensor sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(keyAt));
                    if (sensor != null) {
                        disableSensor(sensor);
                        this.mActiveSensors.put(keyAt, false);
                        removeSensorEvent(sensor);
                    }
                }
            }
            return true;
        }

        public boolean removeSensor(Sensor sensor, boolean z) {
            if (!this.mActiveSensors.get(sensor.getHandle())) {
                return false;
            }
            if (z) {
                disableSensor(sensor);
            }
            this.mActiveSensors.put(sensor.getHandle(), false);
            removeSensorEvent(sensor);
            return true;
        }

        public int flush() {
            long j = this.mNativeSensorEventQueue;
            if (j == 0) {
                throw null;
            }
            return nativeFlushSensor(j);
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

        private void dispose(boolean z) {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                if (z) {
                    closeGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            long j = this.mNativeSensorEventQueue;
            if (j != 0) {
                nativeDestroySensorEventQueue(j);
                this.mNativeSensorEventQueue = 0L;
            }
        }

        private int enableSensor(Sensor sensor, int i, int i2) {
            if (this.mNativeSensorEventQueue == 0) {
                throw null;
            }
            sensor.getClass();
            if (this.mManager.isSensorInCappedSet(sensor.getType()) && i < 5000 && this.mManager.mIsPackageDebuggable && !this.mManager.hasHighSamplingRateSensorsPermission() && Compatibility.isChangeEnabled(SystemSensorManager.CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
                throw new SecurityException("To use the sampling rate of " + i + " microseconds, app needs to declare the normal permission HIGH_SAMPLING_RATE_SENSORS.");
            }
            return nativeEnableSensor(this.mNativeSensorEventQueue, sensor.getHandle(), i, i2);
        }

        protected int injectSensorDataBase(int i, float[] fArr, int i2, long j) {
            return nativeInjectSensorData(this.mNativeSensorEventQueue, i, fArr, i2, j);
        }

        private int disableSensor(Sensor sensor) {
            if (this.mNativeSensorEventQueue == 0) {
                throw null;
            }
            sensor.getClass();
            return nativeDisableSensor(this.mNativeSensorEventQueue, sensor.getHandle());
        }
    }

    static final class SensorEventQueue extends BaseEventQueue {
        private final SensorEventListener mListener;
        private final SparseArray<SensorEvent> mSensorsEvents;

        public SensorEventQueue(SensorEventListener sensorEventListener, Looper looper, SystemSensorManager systemSensorManager, String str) {
            super(looper, systemSensorManager, 0, str);
            this.mSensorsEvents = new SparseArray<>();
            this.mListener = sensorEventListener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(Sensor sensor) {
            SensorEvent sensorEvent = new SensorEvent(Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.put(sensor.getHandle(), sensorEvent);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(Sensor sensor) {
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
            SensorEvent sensorEvent;
            Sensor sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(i));
            if (sensor == null) {
                return;
            }
            synchronized (this.mSensorsEvents) {
                sensorEvent = this.mSensorsEvents.get(i);
            }
            if (sensorEvent == null) {
                return;
            }
            System.arraycopy(fArr, 0, sensorEvent.values, 0, sensorEvent.values.length);
            sensorEvent.timestamp = j;
            sensorEvent.accuracy = i2;
            sensorEvent.sensor = sensor;
            int i3 = this.mSensorAccuracies.get(i);
            if (sensorEvent.accuracy >= 0 && i3 != sensorEvent.accuracy) {
                this.mSensorAccuracies.put(i, sensorEvent.accuracy);
                this.mListener.onAccuracyChanged(sensorEvent.sensor, sensorEvent.accuracy);
            }
            sensorEvent.firstEventAfterDiscontinuity = false;
            if (sensorEvent.sensor.getType() == 37) {
                int i4 = this.mSensorDiscontinuityCounts.get(i);
                int floatToIntBits = Float.floatToIntBits(fArr[6]);
                if (i4 >= 0 && i4 != floatToIntBits) {
                    this.mSensorDiscontinuityCounts.put(i, floatToIntBits);
                    sensorEvent.firstEventAfterDiscontinuity = true;
                }
            }
            this.mListener.onSensorChanged(sensorEvent);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
            Sensor sensor;
            if (!(this.mListener instanceof SensorEventListener2) || (sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(i))) == null) {
                return;
            }
            ((SensorEventListener2) this.mListener).onFlushCompleted(sensor);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchAdditionalInfoEvent(int i, int i2, int i3, float[] fArr, int[] iArr) {
            Sensor sensor;
            if (!(this.mListener instanceof SensorEventCallback) || (sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(i))) == null) {
                return;
            }
            ((SensorEventCallback) this.mListener).onSensorAdditionalInfo(new SensorAdditionalInfo(sensor, i2, i3, iArr, fArr));
        }
    }

    static final class TriggerEventQueue extends BaseEventQueue {
        private final TriggerEventListener mListener;
        private final SparseArray<TriggerEvent> mTriggerEvents;

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
        }

        public TriggerEventQueue(TriggerEventListener triggerEventListener, Looper looper, SystemSensorManager systemSensorManager, String str) {
            super(looper, systemSensorManager, 0, str);
            this.mTriggerEvents = new SparseArray<>();
            this.mListener = triggerEventListener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(Sensor sensor) {
            TriggerEvent triggerEvent = new TriggerEvent(Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.put(sensor.getHandle(), triggerEvent);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(Sensor sensor) {
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
            TriggerEvent triggerEvent;
            Sensor sensor = (Sensor) this.mManager.mHandleToSensor.get(Integer.valueOf(i));
            if (sensor == null) {
                return;
            }
            synchronized (this.mTriggerEvents) {
                triggerEvent = this.mTriggerEvents.get(i);
            }
            if (triggerEvent == null) {
                Log.e("SensorManager", "Error: Trigger Event is null for Sensor: " + sensor);
            } else {
                System.arraycopy(fArr, 0, triggerEvent.values, 0, triggerEvent.values.length);
                triggerEvent.timestamp = j;
                triggerEvent.sensor = sensor;
                this.mManager.cancelTriggerSensorImpl(this.mListener, sensor, false);
                this.mListener.onTrigger(triggerEvent);
            }
        }
    }

    final class InjectEventQueue extends BaseEventQueue {
        private int mMode;

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void addSensorEvent(Sensor sensor) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void removeSensorEvent(Sensor sensor) {
        }

        public InjectEventQueue(SystemSensorManager systemSensorManager, Looper looper, SystemSensorManager systemSensorManager2, int i, String str) {
            super(looper, systemSensorManager2, i, str);
            this.mMode = i;
        }

        int injectSensorData(int i, float[] fArr, int i2, long j) {
            return injectSensorDataBase(i, fArr, i2, j);
        }

        int getDataInjectionMode() {
            return this.mMode;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
        return nativeSetOperationParameter(this.mNativeInstance, sensorAdditionalInfo.sensor != null ? sensorAdditionalInfo.sensor.getHandle() : -1, sensorAdditionalInfo.type, sensorAdditionalInfo.floatValues, sensorAdditionalInfo.intValues) == 0;
    }

    private boolean isDeviceSensorPolicyDefault(int i) {
        if (i == 0) {
            return true;
        }
        if (this.mVdm == null) {
            this.mVdm = (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
        }
        VirtualDeviceManager virtualDeviceManager = this.mVdm;
        return virtualDeviceManager == null || virtualDeviceManager.getDevicePolicy(i, 0) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasHighSamplingRateSensorsPermission() {
        if (!this.mHasHighSamplingRateSensorsPermission.isPresent()) {
            this.mHasHighSamplingRateSensorsPermission = Optional.of(Boolean.valueOf(this.mContext.getPackageManager().checkPermission("android.permission.HIGH_SAMPLING_RATE_SENSORS", this.mContext.getApplicationInfo().packageName) == 0));
        }
        return this.mHasHighSamplingRateSensorsPermission.get().booleanValue();
    }
}
