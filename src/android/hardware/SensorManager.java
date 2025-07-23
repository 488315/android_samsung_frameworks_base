package android.hardware;

import android.annotation.SystemApi;
import android.os.Build;
import android.os.Handler;
import android.os.MemoryFile;
import android.util.Log;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class SensorManager {
    public static final int AXIS_MINUS_X = 129;
    public static final int AXIS_MINUS_Y = 130;
    public static final int AXIS_MINUS_Z = 131;
    public static final int AXIS_X = 1;
    public static final int AXIS_Y = 2;
    public static final int AXIS_Z = 3;
    public static final int DATA_INJECTION = 1;

    @Deprecated
    public static final int DATA_X = 0;

    @Deprecated
    public static final int DATA_Y = 1;

    @Deprecated
    public static final int DATA_Z = 2;
    public static final float GRAVITY_DEATH_STAR_I = 3.5303614E-7f;
    public static final float GRAVITY_EARTH = 9.80665f;
    public static final float GRAVITY_JUPITER = 23.12f;
    public static final float GRAVITY_MARS = 3.71f;
    public static final float GRAVITY_MERCURY = 3.7f;
    public static final float GRAVITY_MOON = 1.6f;
    public static final float GRAVITY_NEPTUNE = 11.0f;
    public static final float GRAVITY_PLUTO = 0.6f;
    public static final float GRAVITY_SATURN = 8.96f;
    public static final float GRAVITY_SUN = 275.0f;
    public static final float GRAVITY_THE_ISLAND = 4.815162f;
    public static final float GRAVITY_URANUS = 8.69f;
    public static final float GRAVITY_VENUS = 8.87f;
    public static final int HAL_BYPASS_REPLAY_DATA_INJECTION = 4;

    @Deprecated
    private static final int INJECT_MAIN_SCREEN_ON = 33572801;

    @Deprecated
    private static final int INJECT_SUB_SCREEN_ON = 50350017;
    public static final float LIGHT_CLOUDY = 100.0f;
    public static final float LIGHT_FULLMOON = 0.25f;
    public static final float LIGHT_NO_MOON = 0.001f;
    public static final float LIGHT_OVERCAST = 10000.0f;
    public static final float LIGHT_SHADE = 20000.0f;
    public static final float LIGHT_SUNLIGHT = 110000.0f;
    public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
    public static final float LIGHT_SUNRISE = 400.0f;
    public static final float MAGNETIC_FIELD_EARTH_MAX = 60.0f;
    public static final float MAGNETIC_FIELD_EARTH_MIN = 30.0f;
    public static final float PRESSURE_STANDARD_ATMOSPHERE = 1013.25f;

    @Deprecated
    public static final int RAW_DATA_INDEX = 3;

    @Deprecated
    public static final int RAW_DATA_X = 3;

    @Deprecated
    public static final int RAW_DATA_Y = 4;

    @Deprecated
    public static final int RAW_DATA_Z = 5;
    public static final int REPLAY_DATA_INJECTION = 3;

    @Deprecated
    public static final int SENSOR_ACCELEROMETER = 2;

    @Deprecated
    public static final int SENSOR_ALL = 127;
    public static final int SENSOR_DELAY_FASTEST = 0;
    public static final int SENSOR_DELAY_GAME = 1;
    public static final int SENSOR_DELAY_NORMAL = 3;
    public static final int SENSOR_DELAY_UI = 2;

    @Deprecated
    public static final int SENSOR_LIGHT = 16;

    @Deprecated
    public static final int SENSOR_MAGNETIC_FIELD = 8;

    @Deprecated
    public static final int SENSOR_MAX = 64;

    @Deprecated
    public static final int SENSOR_MIN = 1;

    @Deprecated
    public static final int SENSOR_ORIENTATION = 1;

    @Deprecated
    public static final int SENSOR_ORIENTATION_RAW = 128;

    @Deprecated
    public static final int SENSOR_PROXIMITY = 32;
    public static final int SENSOR_STATUS_ACCURACY_HIGH = 3;
    public static final int SENSOR_STATUS_ACCURACY_LOW = 1;
    public static final int SENSOR_STATUS_ACCURACY_MEDIUM = 2;
    public static final int SENSOR_STATUS_NO_CONTACT = -1;
    public static final int SENSOR_STATUS_UNRELIABLE = 0;

    @Deprecated
    public static final int SENSOR_TEMPERATURE = 4;

    @Deprecated
    public static final int SENSOR_TRICORDER = 64;
    public static final float STANDARD_GRAVITY = 9.80665f;
    protected static final String TAG = "SensorManager";
    private static final float[] sTempMatrix = new float[16];
    private LegacySensorManager mLegacySensorManager;
    private final SparseArray<List<Sensor>> mSensorListByType = new SparseArray<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataInjectionMode {
    }

    public static abstract class DynamicSensorCallback {
        public void onDynamicSensorConnected(Sensor sensor) {
        }

        public void onDynamicSensorDisconnected(Sensor sensor) {
        }
    }

    private static int getDelay(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 20000;
        }
        if (i != 2) {
            return i != 3 ? i : Build.VERSION_CODES_FULL.BASE_1_1;
        }
        return 66667;
    }

    protected abstract boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z);

    protected abstract int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i);

    protected abstract SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer);

    protected abstract void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel);

    protected abstract boolean flushImpl(SensorEventListener sensorEventListener);

    protected abstract List<Sensor> getFullDynamicSensorList();

    protected abstract List<Sensor> getFullSensorList();

    protected abstract boolean initDataInjectionImpl(boolean z, int i);

    protected abstract boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j);

    protected abstract void registerDynamicSensorCallbackImpl(DynamicSensorCallback dynamicSensorCallback, Handler handler);

    protected abstract boolean registerListenerImpl(SensorEventListener sensorEventListener, Sensor sensor, int i, Handler handler, int i2, int i3);

    protected abstract boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor);

    protected abstract boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo);

    protected abstract void unregisterDynamicSensorCallbackImpl(DynamicSensorCallback dynamicSensorCallback);

    protected abstract void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor);

    @Deprecated
    public int getSensors() {
        return getLegacySensorManager().getSensors();
    }

    public List<Sensor> getSensorList(int i) {
        List<Sensor> list;
        List<Sensor> fullSensorList = getFullSensorList();
        synchronized (this.mSensorListByType) {
            list = this.mSensorListByType.get(i);
            if (list == null) {
                if (i != -1) {
                    ArrayList arrayList = new ArrayList();
                    for (Sensor sensor : fullSensorList) {
                        if (sensor.getType() == i) {
                            arrayList.add(sensor);
                        }
                    }
                    fullSensorList = arrayList;
                }
                list = Collections.unmodifiableList(fullSensorList);
                this.mSensorListByType.append(i, list);
            }
        }
        return list;
    }

    public Sensor getSensorByHandle(int i) {
        for (Sensor sensor : getFullSensorList()) {
            if (sensor.getHandle() == i) {
                return sensor;
            }
        }
        return null;
    }

    public List<Sensor> getDynamicSensorList(int i) {
        List<Sensor> fullDynamicSensorList = getFullDynamicSensorList();
        if (i == -1) {
            return Collections.unmodifiableList(fullDynamicSensorList);
        }
        ArrayList arrayList = new ArrayList();
        for (Sensor sensor : fullDynamicSensorList) {
            if (sensor.getType() == i) {
                arrayList.add(sensor);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Sensor getDefaultSensor(int i) {
        List<Sensor> sensorList = getSensorList(i);
        boolean z = i == 8 || i == 17 || i == 22 || i == 23 || i == 24 || i == 25 || i == 34 || i == 26 || i == 65651 || i == 65653 || i == 32 || i == 36 || i == 8;
        for (Sensor sensor : sensorList) {
            if (sensor.isWakeUpSensor() == z) {
                return sensor;
            }
        }
        if (i == 8) {
            for (Sensor sensor2 : getSensorList(8)) {
                if (sensor2.isWakeUpSensor() == z) {
                    Log.i(TAG, "get Physical proximity");
                    return sensor2;
                }
            }
            i = 8;
        }
        if (i == 65601) {
            i = 5;
            for (Sensor sensor3 : getSensorList(5)) {
                if (sensor3.isWakeUpSensor() == z) {
                    Log.i(TAG, "get light sensor instead of autobrightness");
                    return sensor3;
                }
            }
        }
        if (i <= 65536) {
            return null;
        }
        List<Sensor> sensorList2 = getSensorList(i);
        if (sensorList2.size() <= 0) {
            return null;
        }
        Sensor sensor4 = sensorList2.get(0);
        Log.i(TAG, "Get Sensor type = " + i);
        return sensor4;
    }

    public Sensor getDefaultSensor(int i, boolean z) {
        for (Sensor sensor : getSensorList(i)) {
            if (sensor.isWakeUpSensor() == z) {
                return sensor;
            }
        }
        return null;
    }

    @Deprecated
    public boolean registerListener(SensorListener sensorListener, int i) {
        return registerListener(sensorListener, i, 3);
    }

    @Deprecated
    public boolean registerListener(SensorListener sensorListener, int i, int i2) {
        return getLegacySensorManager().registerListener(sensorListener, i, i2);
    }

    @Deprecated
    public void unregisterListener(SensorListener sensorListener) {
        unregisterListener(sensorListener, 255);
    }

    @Deprecated
    public void unregisterListener(SensorListener sensorListener, int i) {
        getLegacySensorManager().unregisterListener(sensorListener, i);
    }

    public void unregisterListener(SensorEventListener sensorEventListener, Sensor sensor) {
        if (sensorEventListener == null || sensor == null) {
            return;
        }
        unregisterListenerImpl(sensorEventListener, sensor);
    }

    public void unregisterListener(SensorEventListener sensorEventListener) {
        if (sensorEventListener == null) {
            return;
        }
        unregisterListenerImpl(sensorEventListener, null);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int i) {
        return registerListener(sensorEventListener, sensor, i, (Handler) null);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int i, int i2) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), null, i2, 0);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int i, Handler handler) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), handler, 0, 0);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), handler, i2, 0);
    }

    public boolean flush(SensorEventListener sensorEventListener) {
        return flushImpl(sensorEventListener);
    }

    public SensorDirectChannel createDirectChannel(MemoryFile memoryFile) {
        return createDirectChannelImpl(memoryFile, null);
    }

    public SensorDirectChannel createDirectChannel(HardwareBuffer hardwareBuffer) {
        return createDirectChannelImpl(null, hardwareBuffer);
    }

    void destroyDirectChannel(SensorDirectChannel sensorDirectChannel) {
        destroyDirectChannelImpl(sensorDirectChannel);
    }

    public void registerDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback) {
        registerDynamicSensorCallback(dynamicSensorCallback, null);
    }

    public void registerDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        registerDynamicSensorCallbackImpl(dynamicSensorCallback, handler);
    }

    public void unregisterDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback) {
        unregisterDynamicSensorCallbackImpl(dynamicSensorCallback);
    }

    public boolean isDynamicSensorDiscoverySupported() {
        return getSensorList(32).size() > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean getRotationMatrix(float[] r32, float[] r33, float[] r34, float[] r35) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.SensorManager.getRotationMatrix(float[], float[], float[], float[]):boolean");
    }

    public static float getInclination(float[] fArr) {
        double atan2;
        if (fArr.length == 9) {
            atan2 = Math.atan2(fArr[5], fArr[4]);
        } else {
            atan2 = Math.atan2(fArr[6], fArr[5]);
        }
        return (float) atan2;
    }

    public static boolean remapCoordinateSystem(float[] fArr, int i, int i2, float[] fArr2) {
        if (fArr == fArr2) {
            float[] fArr3 = sTempMatrix;
            synchronized (fArr3) {
                if (remapCoordinateSystemImpl(fArr, i, i2, fArr3)) {
                    int length = fArr2.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        fArr2[i3] = fArr3[i3];
                    }
                    return true;
                }
            }
        }
        return remapCoordinateSystemImpl(fArr, i, i2, fArr2);
    }

    private static boolean remapCoordinateSystemImpl(float[] fArr, int i, int i2, float[] fArr2) {
        int i3;
        boolean z;
        int i4;
        float f;
        int length = fArr2.length;
        int i5 = 0;
        if (fArr.length != length) {
            return false;
        }
        if ((i & 124) != 0 || (i2 & 124) != 0) {
            return false;
        }
        int i6 = i & 3;
        if (i6 == 0 || (i3 = i2 & 3) == 0) {
            return false;
        }
        if (i6 == i3) {
            return false;
        }
        int i7 = i ^ i2;
        boolean z2 = true;
        int i8 = i6 - 1;
        int i9 = i3 - 1;
        int i10 = i7 & 3;
        int i11 = i10 - 1;
        int i12 = 3;
        if (((((i10 + 1) % 3) ^ i9) | ((i10 % 3) ^ i8)) != 0) {
            i7 ^= 128;
        }
        boolean z3 = i >= 128;
        boolean z4 = i2 >= 128;
        boolean z5 = i7 >= 128;
        int i13 = length == 16 ? 4 : 3;
        int i14 = 0;
        while (i14 < i12) {
            int i15 = i14 * i13;
            int i16 = i5;
            while (i5 < i12) {
                if (i8 == i5) {
                    z = z2;
                    fArr2[i15 + i5] = z3 ? -fArr[i15] : fArr[i15];
                } else {
                    z = z2;
                }
                if (i9 == i5) {
                    int i17 = i15 + i5;
                    if (z4) {
                        i4 = i12;
                        f = -fArr[i15 + 1];
                    } else {
                        i4 = i12;
                        f = fArr[i15 + 1];
                    }
                    fArr2[i17] = f;
                } else {
                    i4 = i12;
                }
                if (i11 == i5) {
                    int i18 = i15 + 2;
                    fArr2[i15 + i5] = z5 ? -fArr[i18] : fArr[i18];
                }
                i5++;
                z2 = z;
                i12 = i4;
            }
            i14++;
            i5 = i16;
        }
        boolean z6 = z2;
        int i19 = i12;
        if (length == 16) {
            fArr2[14] = 0.0f;
            fArr2[13] = 0.0f;
            fArr2[12] = 0.0f;
            fArr2[11] = 0.0f;
            fArr2[7] = 0.0f;
            fArr2[i19] = 0.0f;
            fArr2[15] = 1.0f;
        }
        return z6;
    }

    public static float[] getOrientation(float[] fArr, float[] fArr2) {
        if (fArr.length == 9) {
            fArr2[0] = (float) Math.atan2(fArr[1], fArr[4]);
            fArr2[1] = (float) Math.asin(-fArr[7]);
            fArr2[2] = (float) Math.atan2(-fArr[6], fArr[8]);
            return fArr2;
        }
        fArr2[0] = (float) Math.atan2(fArr[1], fArr[5]);
        fArr2[1] = (float) Math.asin(-fArr[9]);
        fArr2[2] = (float) Math.atan2(-fArr[8], fArr[10]);
        return fArr2;
    }

    public static float getAltitude(float f, float f2) {
        return (1.0f - ((float) Math.pow(f2 / f, 0.19029495120048523d))) * 44330.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void getAngleChange(float[] r25, float[] r26, float[] r27) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.SensorManager.getAngleChange(float[], float[], float[]):void");
    }

    public static void getRotationMatrixFromVector(float[] fArr, float[] fArr2) {
        float sqrt;
        float f = fArr2[0];
        float f2 = fArr2[1];
        float f3 = fArr2[2];
        if (fArr2.length >= 4) {
            sqrt = fArr2[3];
        } else {
            float f4 = ((1.0f - (f * f)) - (f2 * f2)) - (f3 * f3);
            sqrt = f4 > 0.0f ? (float) Math.sqrt(f4) : 0.0f;
        }
        float f5 = f * 2.0f;
        float f6 = f * f5;
        float f7 = f2 * 2.0f;
        float f8 = f7 * f2;
        float f9 = 2.0f * f3;
        float f10 = f9 * f3;
        float f11 = f2 * f5;
        float f12 = f9 * sqrt;
        float f13 = f5 * f3;
        float f14 = f7 * sqrt;
        float f15 = f7 * f3;
        float f16 = f5 * sqrt;
        if (fArr.length == 9) {
            fArr[0] = (1.0f - f8) - f10;
            fArr[1] = f11 - f12;
            fArr[2] = f13 + f14;
            fArr[3] = f11 + f12;
            float f17 = 1.0f - f6;
            fArr[4] = f17 - f10;
            fArr[5] = f15 - f16;
            fArr[6] = f13 - f14;
            fArr[7] = f15 + f16;
            fArr[8] = f17 - f8;
            return;
        }
        if (fArr.length == 16) {
            fArr[0] = (1.0f - f8) - f10;
            fArr[1] = f11 - f12;
            fArr[2] = f13 + f14;
            fArr[3] = 0.0f;
            fArr[4] = f11 + f12;
            float f18 = 1.0f - f6;
            fArr[5] = f18 - f10;
            fArr[6] = f15 - f16;
            fArr[7] = 0.0f;
            fArr[8] = f13 - f14;
            fArr[9] = f15 + f16;
            fArr[10] = f18 - f8;
            fArr[11] = 0.0f;
            fArr[14] = 0.0f;
            fArr[13] = 0.0f;
            fArr[12] = 0.0f;
            fArr[15] = 1.0f;
        }
    }

    public static void getQuaternionFromVector(float[] fArr, float[] fArr2) {
        if (fArr2.length >= 4) {
            fArr[0] = fArr2[3];
        } else {
            float f = fArr2[0];
            float f2 = 1.0f - (f * f);
            float f3 = fArr2[1];
            float f4 = f2 - (f3 * f3);
            float f5 = fArr2[2];
            float f6 = f4 - (f5 * f5);
            fArr[0] = f6;
            fArr[0] = f6 > 0.0f ? (float) Math.sqrt(f6) : 0.0f;
        }
        fArr[1] = fArr2[0];
        fArr[2] = fArr2[1];
        fArr[3] = fArr2[2];
    }

    public boolean requestTriggerSensor(TriggerEventListener triggerEventListener, Sensor sensor) {
        return requestTriggerSensorImpl(triggerEventListener, sensor);
    }

    public boolean cancelTriggerSensor(TriggerEventListener triggerEventListener, Sensor sensor) {
        return cancelTriggerSensorImpl(triggerEventListener, sensor, true);
    }

    @SystemApi
    public boolean initDataInjection(boolean z) {
        return initDataInjectionImpl(z, 1);
    }

    public boolean initDataInjection(boolean z, int i) {
        return initDataInjectionImpl(z, i);
    }

    @SystemApi
    public boolean injectSensorData(Sensor sensor, float[] fArr, int i, long j) {
        if (sensor == null) {
            throw new IllegalArgumentException("sensor cannot be null");
        }
        if (fArr == null) {
            throw new IllegalArgumentException("sensor data cannot be null");
        }
        int maxLengthValuesArray = Sensor.getMaxLengthValuesArray(sensor, 23);
        if (fArr.length == maxLengthValuesArray) {
            if (i < -1 || i > 3) {
                throw new IllegalArgumentException("Invalid sensor accuracy");
            }
            if (j <= 0) {
                throw new IllegalArgumentException("Negative or zero sensor timestamp");
            }
            return injectSensorDataImpl(sensor, fArr, i, j);
        }
        throw new IllegalArgumentException("Wrong number of values for sensor " + sensor.getName() + " actual=" + fArr.length + " expected=" + maxLengthValuesArray);
    }

    private LegacySensorManager getLegacySensorManager() {
        LegacySensorManager legacySensorManager;
        synchronized (this.mSensorListByType) {
            if (this.mLegacySensorManager == null) {
                Log.i(TAG, "This application is using deprecated SensorManager API which will be removed someday.  Please consider switching to the new API.");
                this.mLegacySensorManager = new LegacySensorManager(this);
            }
            legacySensorManager = this.mLegacySensorManager;
        }
        return legacySensorManager;
    }

    public boolean setOperationParameter(SensorAdditionalInfo sensorAdditionalInfo) {
        if (sensorAdditionalInfo == null) {
            return false;
        }
        return setOperationParameterImpl(sensorAdditionalInfo);
    }

    public void setActiveScreenParameter(int i) {
        Iterator<Sensor> it = getSensorList(Sensor.TYPE_SCONTEXT).iterator();
        if (it.hasNext()) {
            Sensor next = it.next();
            int[] iArr = new int[2];
            iArr[0] = 4;
            if (i == 0) {
                iArr[1] = 33572801;
            } else if (i == 1) {
                iArr[1] = 50350017;
            }
            SensorAdditionalInfo createSContextData = SensorAdditionalInfo.createSContextData(next, iArr);
            Log.d(TAG, "set parameter active screen = " + i + ", " + iArr[1]);
            if (setOperationParameterImpl(createSContextData)) {
                return;
            }
            Log.d(TAG, "set parameter active screen failed ");
        }
    }

    public void setBrightnessHysteresisParameter(float[] fArr) {
        for (Sensor sensor : getSensorList(5)) {
            if ((sensor.getVersion() & 2) != 0) {
                Log.d(TAG, "Inject hysteresis info");
                try {
                    if (setOperationParameterImpl(SensorAdditionalInfo.createCustomInfo(sensor, 268435458, fArr))) {
                        return;
                    }
                    Log.d(TAG, "Inject hysteresis info failed ");
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "IllegalArgumentException" + e);
                    return;
                }
            }
        }
    }
}
