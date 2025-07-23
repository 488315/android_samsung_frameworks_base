package android.hardware;

import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class SensorAdditionalInfo {
    public static final int BITMASK_LIGHT_VERSION_HYSTERESIS_INFO = 2;
    private static final int SENSORHUB_INFO_CALL_PKG = 346049;
    public static final int SENSORHUB_INFO_CALL_SCREEN_BACKGROUND = 285231553;
    public static final int SENSORHUB_INFO_CALL_SCREEN_FOREGROUND = 302008769;
    public static final int SENSORHUB_INFO_DISPLAY_REFRESH_RATE = 268454081;
    private static final int SENSORHUB_INFO_INJECT_CALL_PKG = 477121;
    private static final int SENSORHUB_INFO_INJECT_PHYSICAL_PKG = 411585;
    private static final int SENSORHUB_INFO_INJECT_VM_PKG = 739265;
    public static final int SENSORHUB_INFO_MAIN_SCREEN_ON = 33572801;
    public static final int SENSORHUB_INFO_PALM_DOWN = 149441;
    public static final int SENSORHUB_INFO_PALM_UP = 214977;
    private static final int SENSORHUB_INFO_PHYSICAL_PKG = 280513;
    public static final int SENSORHUB_INFO_POWERSHARE_DISABLED = 20673;
    public static final int SENSORHUB_INFO_POWERSHARE_ENABLED = 16797889;
    private static final int SENSORHUB_INFO_PROXIMITY_TIMEOUT = 673729;
    private static final int SENSORHUB_INFO_RCV_CLOSE = 608193;
    private static final int SENSORHUB_INFO_RCV_OPEN = 542657;
    private static final int SENSORHUB_INFO_SCREEN_OFF = 18369;
    private static final int SENSORHUB_INFO_SCREEN_ON = 16795585;
    public static final int SENSORHUB_INFO_SUB_SCREEN_ON = 50350017;
    private static final int SENSORHUB_INFO_VM_PKG = 804801;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_IN = 117458881;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_INOUT = 151013313;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_NONE = 100681665;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_OUT = 134236097;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_SCAN_OFF = 83904449;

    @Deprecated
    public static final int SENSORHUB_INFO_WIFI_SCAN_ON = 67127233;
    public static final int TYPE_BRIGHTNESS_HYSTERESIS_INFO = 268435458;
    public static final int TYPE_CALIBRATED_LUX_INFO = 268435457;
    public static final int TYPE_CUSTOM_INFO = 268435456;
    public static final int TYPE_DEBUG_INFO = 1073741824;
    public static final int TYPE_DOCK_STATE = 196610;
    public static final int TYPE_FRAME_BEGIN = 0;
    public static final int TYPE_FRAME_END = 1;
    public static final int TYPE_HIGH_PERFORMANCE_MODE = 196611;
    public static final int TYPE_INTERNAL_TEMPERATURE = 65537;
    public static final int TYPE_LOCAL_GEOMAGNETIC_FIELD = 196608;
    public static final int TYPE_LOCAL_GRAVITY = 196609;
    public static final int TYPE_MAGNETIC_FIELD_CALIBRATION = 196612;
    public static final int TYPE_MOCCA_CORE_INFO = 268435462;
    private static final int TYPE_MOTIONRECOGNITION = 65559;
    public static final int TYPE_POCKET_SENSOR_INFO = 268435459;
    public static final int TYPE_SAMPLING = 65540;
    private static final int TYPE_SENSORHUB = 65586;
    private static final int TYPE_SENSORHUB_DATA = 1112885331;
    public static final int TYPE_SENSOR_PLACEMENT = 65539;
    public static final int TYPE_SEQ_FOLD_MONITOR_INFO = 268435463;
    public static final int TYPE_UNFOLDING_INFO = 268435460;
    public static final int TYPE_UNTRACKED_DELAY = 65536;
    public static final int TYPE_VEC3_CALIBRATION = 65538;
    public static final int TYPE_VIBRATOR_INFO = 268435461;
    public final float[] floatValues;
    public final int[] intValues;
    public final Sensor sensor;
    public final int serial;
    public final int type;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AdditionalInfoType {
    }

    SensorAdditionalInfo(Sensor sensor, int i, int i2, int[] iArr, float[] fArr) {
        this.sensor = sensor;
        this.type = i;
        this.serial = i2;
        this.intValues = iArr;
        this.floatValues = fArr;
    }

    public static SensorAdditionalInfo createLocalGeomagneticField(float f, float f2, float f3) {
        if (f >= 10.0f && f <= 100.0f) {
            double d = f2;
            if (d >= -1.5707963267948966d && d <= 1.5707963267948966d) {
                double d2 = f3;
                if (d2 >= -1.5707963267948966d && d2 <= 1.5707963267948966d) {
                    return new SensorAdditionalInfo(null, 196608, 0, null, new float[]{f, f2, f3});
                }
            }
        }
        throw new IllegalArgumentException("Geomagnetic field info out of range");
    }

    public static SensorAdditionalInfo createCustomInfo(Sensor sensor, int i, float[] fArr) {
        if (i < 268435456 || i >= 1073741824 || sensor == null) {
            throw new IllegalArgumentException("invalid parameter(s): type: " + i + "; sensor: " + sensor);
        }
        return new SensorAdditionalInfo(sensor, i, 0, null, fArr);
    }

    public static SensorAdditionalInfo createSamsungCustomInfo(Sensor sensor, int i, int i2, int[] iArr, float[] fArr) {
        if (i < 268435456 || i >= 1073741824 || sensor == null || (iArr == null && fArr == null)) {
            throw new IllegalArgumentException("invalid parameter(s): type: " + i + "; sensor: " + sensor);
        }
        return new SensorAdditionalInfo(sensor, i, i2, iArr, fArr);
    }

    public static SensorAdditionalInfo createSContextData(Sensor sensor, int[] iArr) {
        if (iArr == null || sensor == null) {
            Log.i("SensorAdditionalInfo", "skip createSContextData");
            return null;
        }
        if (sensor.getType() != 65586) {
            Log.i("SensorAdditionalInfo", "skip createSContextData");
            return null;
        }
        return new SensorAdditionalInfo(sensor, TYPE_SENSORHUB_DATA, 0, iArr, null);
    }

    public static SensorAdditionalInfo createMotionData(Sensor sensor, int[] iArr) {
        if (iArr == null || sensor == null) {
            throw new IllegalArgumentException("wrong motion data");
        }
        if (sensor.getType() != 65559) {
            throw new IllegalArgumentException("wrong motion sensor");
        }
        return new SensorAdditionalInfo(sensor, 65559, 0, iArr, null);
    }
}
