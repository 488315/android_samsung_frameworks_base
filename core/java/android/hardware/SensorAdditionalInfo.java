package android.hardware;

import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class SensorAdditionalInfo {
  public static final int LIGHT_VERSION_HYSTERESIS_INFO = 2;
  private static final int SENSORHUB_INFO_CALL_PKG = 346049;
  public static final int SENSORHUB_INFO_CALL_SCREEN_BACKGROUND = 285231553;
  public static final int SENSORHUB_INFO_CALL_SCREEN_FOREGROUND = 302008769;
  public static final int SENSORHUB_INFO_DISPLAY_REFRESH_RATE = 268454081;
  private static final int SENSORHUB_INFO_INJECT_CALL_PKG = 477121;
  private static final int SENSORHUB_INFO_INJECT_PHYSICAL_PKG = 411585;
  private static final int SENSORHUB_INFO_INJECT_VM_PKG = 739265;
  private static final int SENSORHUB_INFO_MAIN_SCREEN_ON = 33572801;
  private static final int SENSORHUB_INFO_PALM_DOWN = 149441;
  private static final int SENSORHUB_INFO_PALM_UP = 214977;
  private static final int SENSORHUB_INFO_PHYSICAL_PKG = 280513;
  private static final int SENSORHUB_INFO_PROXIMITY_TIMEOUT = 673729;
  private static final int SENSORHUB_INFO_RCV_CLOSE = 608193;
  private static final int SENSORHUB_INFO_RCV_OPEN = 542657;
  private static final int SENSORHUB_INFO_SCREEN_OFF = 18369;
  private static final int SENSORHUB_INFO_SCREEN_ON = 16795585;
  private static final int SENSORHUB_INFO_SUB_SCREEN_ON = 50350017;
  private static final int SENSORHUB_INFO_VM_PKG = 804801;
  public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_IN = 117458881;
  public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_INOUT = 151013313;
  public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_NONE = 100681665;
  public static final int SENSORHUB_INFO_WIFI_DATA_ACTIVITY_OUT = 134236097;
  public static final int SENSORHUB_INFO_WIFI_SCAN_OFF = 83904449;
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
  private static final int TYPE_MOTIONRECOGNITION = 65559;
  public static final int TYPE_POCKET_SENSOR_INFO = 268435459;
  public static final int TYPE_SAMPLING = 65540;
  private static final int TYPE_SENSORHUB = 65586;
  private static final int TYPE_SENSORHUB_DATA = 1112885331;
  public static final int TYPE_SENSOR_PLACEMENT = 65539;
  public static final int TYPE_UNFOLDING_INFO = 268435460;
  public static final int TYPE_UNTRACKED_DELAY = 65536;
  public static final int TYPE_VEC3_CALIBRATION = 65538;
  public final float[] floatValues;
  public final int[] intValues;
  public final Sensor sensor;
  public final int serial;
  public final int type;

  @Retention(RetentionPolicy.SOURCE)
  public @interface AdditionalInfoType {}

  SensorAdditionalInfo(
      Sensor aSensor, int aType, int aSerial, int[] aIntValues, float[] aFloatValues) {
    this.sensor = aSensor;
    this.type = aType;
    this.serial = aSerial;
    this.intValues = aIntValues;
    this.floatValues = aFloatValues;
  }

  public static SensorAdditionalInfo createLocalGeomagneticField(
      float strength, float declination, float inclination) {
    if (strength < 10.0f
        || strength > 100.0f
        || declination < 0.0f
        || declination > 3.141592653589793d
        || inclination < -1.5707963267948966d
        || inclination > 1.5707963267948966d) {
      throw new IllegalArgumentException("Geomagnetic field info out of range");
    }
    return new SensorAdditionalInfo(
        null, 196608, 0, null, new float[] {strength, declination, inclination});
  }

  public static SensorAdditionalInfo createCustomInfo(Sensor aSensor, int type, float[] data) {
    if (type < 268435456 || type >= 1073741824 || aSensor == null) {
      throw new IllegalArgumentException(
          "invalid parameter(s): type: " + type + "; sensor: " + aSensor);
    }
    return new SensorAdditionalInfo(aSensor, type, 0, null, data);
  }

  public static SensorAdditionalInfo createSamsungCustomInfo(
      Sensor aSensor, int aType, int aSerial, int[] aIntValues, float[] aFloatValues) {
    if (aType < 268435456
        || aType >= 1073741824
        || aSensor == null
        || (aIntValues == null && aFloatValues == null)) {
      throw new IllegalArgumentException(
          "invalid parameter(s): type: " + aType + "; sensor: " + aSensor);
    }
    return new SensorAdditionalInfo(aSensor, aType, aSerial, aIntValues, aFloatValues);
  }

  public static SensorAdditionalInfo createSContextData(Sensor sensor, int[] data) {
    if (data == null || sensor == null) {
      Log.m98i("SensorAdditionalInfo", "skip createSContextData");
      return null;
    }
    if (sensor.getType() != 65586) {
      Log.m98i("SensorAdditionalInfo", "skip createSContextData");
      return null;
    }
    return new SensorAdditionalInfo(sensor, TYPE_SENSORHUB_DATA, 0, data, null);
  }

  public static SensorAdditionalInfo createMotionData(Sensor sensor, int[] data) {
    if (data == null || sensor == null) {
      throw new IllegalArgumentException("wrong motion data");
    }
    if (sensor.getType() != 65559) {
      throw new IllegalArgumentException("wrong motion sensor");
    }
    return new SensorAdditionalInfo(sensor, 65559, 0, data, null);
  }
}
