package android.hardware;

import android.annotation.SystemApi;
import android.hardware.input.InputSensorInfo;
import android.os.SystemProperties;

import java.util.UUID;

/* loaded from: classes.dex */
public final class Sensor {
    private static final int ADDITIONAL_INFO_MASK = 64;
    private static final int ADDITIONAL_INFO_SHIFT = 6;
    private static final int DATA_INJECTION_MASK = 16;
    private static final int DATA_INJECTION_SHIFT = 4;
    private static final int DIRECT_CHANNEL_MASK = 3072;
    private static final int DIRECT_CHANNEL_SHIFT = 10;
    private static final int DIRECT_REPORT_MASK = 896;
    private static final int DIRECT_REPORT_SHIFT = 7;
    private static final int DYNAMIC_SENSOR_MASK = 32;
    private static final int DYNAMIC_SENSOR_SHIFT = 5;
    public static final int REPORTING_MODE_CONTINUOUS = 0;
    private static final int REPORTING_MODE_MASK = 14;
    public static final int REPORTING_MODE_ONE_SHOT = 2;
    public static final int REPORTING_MODE_ON_CHANGE = 1;
    private static final int REPORTING_MODE_SHIFT = 1;
    public static final int REPORTING_MODE_SPECIAL_TRIGGER = 3;
    public static final int SEM_ACCELEROMETER_HIGHG = 65650;
    public static final int SEM_ACCELEROMETER_SUB = 65687;
    public static final int SEM_ACCELEROMETER_UNCALIBRATED_SUB = 65688;
    public static final int SEM_FOLDING_ANGLE = 65686;
    public static final int SEM_GYROSCOPE_SUB = 65689;
    public static final int SEM_GYROSCOPE_UNCALIBRATED_SUB = 65690;
    public static final int SEM_TABLE_MODE = 65693;
    public static final int SEM_TRIMAGNETIC_FIELD = 65694;
    public static final int SEM_TYPE_ANGLE_SENSOR_STATUS_CHECK = 65700;
    public static final int SEM_TYPE_AUDIO_PROXIMITY = 65597;
    public static final int SEM_TYPE_AUTOBRIGHTNESS = 65601;
    public static final int SEM_TYPE_BACK_TAP = 65651;
    public static final int SEM_TYPE_CALLGESTURE = 65594;
    public static final int SEM_TYPE_CAMERA_LIGHT = 65604;
    public static final int SEM_TYPE_CAR_CRASH_DETECTION = 65648;
    public static final int SEM_TYPE_CHECK_PROXIMITY = 65595;
    public static final int SEM_TYPE_DEVICE_COMMON_INFO = 65649;
    public static final int SEM_TYPE_DROP_CLASSIFIER = 65644;
    public static final int SEM_TYPE_EARSENSE = 8;
    public static final int SEM_TYPE_FLAT_MOTION = 65737;
    public static final int SEM_TYPE_FLIP_COVER_DETECTOR = 65639;
    public static final int SEM_TYPE_FOLDING_ANGLE = 65686;
    public static final int SEM_TYPE_FOLDING_STATE = 65695;
    public static final int SEM_TYPE_FREEFALL_DETECT = 65602;
    public static final int SEM_TYPE_GRIP_NOTIFIER = 65645;
    public static final int SEM_TYPE_GRIP_SUB = 65636;
    public static final int SEM_TYPE_GRIP_SUB2 = 65637;
    public static final int SEM_TYPE_GRIP_SUB3 = 65638;
    public static final int SEM_TYPE_HALLIC = 65600;
    public static final int SEM_TYPE_HEART_RATE = 65562;
    public static final int SEM_TYPE_HOVER_PROXIMITY = 65599;
    public static final int SEM_TYPE_LED_COVER_EVENT = 65606;
    public static final int SEM_TYPE_LID_ANGLE_FUSION_LOGGING = 65696;
    public static final int SEM_TYPE_LID_FOLDING_STATE_LPM = 65697;
    public static final int SEM_TYPE_LIGHT_CCT = 65587;
    public static final int SEM_TYPE_LIGHT_SEAMLESS = 65614;
    public static final int SEM_TYPE_LIGHT_STRM_SUB = 65642;
    public static final int SEM_TYPE_LIGHT_SUB = 65641;
    public static final int SEM_TYPE_LP_SCAN_STATE = 65621;
    public static final int SEM_TYPE_MOCCA_CORE = 65653;
    public static final int SEM_TYPE_MOVE_DETECTOR = 65593;
    public static final int SEM_TYPE_PHYSICAL_PROXIMITY = 65592;
    public static final int SEM_TYPE_POCKET_DETECTOR = 65609;
    public static final int SEM_TYPE_POCKET_MODE = 65605;
    public static final int SEM_TYPE_POCKET_MODE_LITE = 65608;
    public static final int SEM_TYPE_POCKET_POS_MODE = 65698;
    public static final int SEM_TYPE_POWER_KEY_DETECTOR = 65603;
    public static final int SEM_TYPE_SAR_BACKOFF_MOTION = 65643;
    public static final int SEM_TYPE_SEQ_STEP = 65647;
    public static final int SEM_TYPE_SHAKE_TRACKER = 65612;
    public static final int SEM_TYPE_SMART_ALERT = 65736;
    public static final int SEM_TYPE_SUPERSTEADY_GYROSCOPE = 65646;
    public static final int SEM_TYPE_TAP_TRACKER = 65611;
    public static final int SEM_TYPE_TOUCH_LIGHT = 65640;
    public static final int SEM_TYPE_TOUCH_PROXIMITY = 65596;
    public static final int SEM_TYPE_TOUCH_PROXIMITY_POCKET = 65610;
    public static final int SEM_TYPE_VDIS_GYRO = 65607;
    public static final int SEM_WAKE_UP_MOTION = 65590;
    private static final int SENSOR_FLAG_WAKE_UP_SENSOR = 1;
    public static final String SENSOR_STRING_TYPE_TILT_DETECTOR = "android.sensor.tilt_detector";
    public static final int SENSOR_TYPE_BLOODGLUCOSE = 65567;
    public static final int SENSOR_TYPE_DEVICE_PRIVATE_BASE = 65536;
    public static final String STRING_TYPE_ACCELEROMETER = "android.sensor.accelerometer";
    public static final String STRING_TYPE_ACCELEROMETER_LIMITED_AXES =
            "android.sensor.accelerometer_limited_axes";
    public static final String STRING_TYPE_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED =
            "android.sensor.accelerometer_limited_axes_uncalibrated";
    public static final String STRING_TYPE_ACCELEROMETER_UNCALIBRATED =
            "android.sensor.accelerometer_uncalibrated";
    public static final String STRING_TYPE_AMBIENT_TEMPERATURE =
            "android.sensor.ambient_temperature";
    public static final String STRING_TYPE_DEVICE_ORIENTATION = "android.sensor.device_orientation";

    @SystemApi
    public static final String STRING_TYPE_DYNAMIC_SENSOR_META =
            "android.sensor.dynamic_sensor_meta";

    public static final String STRING_TYPE_GAME_ROTATION_VECTOR =
            "android.sensor.game_rotation_vector";
    public static final String STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR =
            "android.sensor.geomagnetic_rotation_vector";
    public static final String STRING_TYPE_GLANCE_GESTURE = "android.sensor.glance_gesture";
    public static final String STRING_TYPE_GRAVITY = "android.sensor.gravity";
    public static final String STRING_TYPE_GYROSCOPE = "android.sensor.gyroscope";
    public static final String STRING_TYPE_GYROSCOPE_LIMITED_AXES =
            "android.sensor.gyroscope_limited_axes";
    public static final String STRING_TYPE_GYROSCOPE_LIMITED_AXES_UNCALIBRATED =
            "android.sensor.gyroscope_limited_axes_uncalibrated";
    public static final String STRING_TYPE_GYROSCOPE_UNCALIBRATED =
            "android.sensor.gyroscope_uncalibrated";
    public static final String STRING_TYPE_HEADING = "android.sensor.heading";
    public static final String STRING_TYPE_HEAD_TRACKER = "android.sensor.head_tracker";
    public static final String STRING_TYPE_HEART_BEAT = "android.sensor.heart_beat";
    public static final String STRING_TYPE_HEART_RATE = "android.sensor.heart_rate";
    public static final String STRING_TYPE_HINGE_ANGLE = "android.sensor.hinge_angle";
    public static final String STRING_TYPE_LIGHT = "android.sensor.light";
    public static final String STRING_TYPE_LINEAR_ACCELERATION =
            "android.sensor.linear_acceleration";
    public static final String STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT =
            "android.sensor.low_latency_offbody_detect";
    public static final String STRING_TYPE_MAGNETIC_FIELD = "android.sensor.magnetic_field";
    public static final String STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED =
            "android.sensor.magnetic_field_uncalibrated";
    public static final String STRING_TYPE_MOTION_DETECT = "android.sensor.motion_detect";

    @Deprecated public static final String STRING_TYPE_ORIENTATION = "android.sensor.orientation";
    public static final String STRING_TYPE_PICK_UP_GESTURE = "android.sensor.pick_up_gesture";
    public static final String STRING_TYPE_POSE_6DOF = "android.sensor.pose_6dof";
    public static final String STRING_TYPE_PRESSURE = "android.sensor.pressure";
    public static final String STRING_TYPE_PROXIMITY = "android.sensor.proximity";
    public static final String STRING_TYPE_RELATIVE_HUMIDITY = "android.sensor.relative_humidity";
    public static final String STRING_TYPE_ROTATION_VECTOR = "android.sensor.rotation_vector";
    public static final String STRING_TYPE_SIGNIFICANT_MOTION = "android.sensor.significant_motion";
    public static final String STRING_TYPE_STATIONARY_DETECT = "android.sensor.stationary_detect";
    public static final String STRING_TYPE_STEP_COUNTER = "android.sensor.step_counter";
    public static final String STRING_TYPE_STEP_DETECTOR = "android.sensor.step_detector";

    @Deprecated public static final String STRING_TYPE_TEMPERATURE = "android.sensor.temperature";
    public static final String STRING_TYPE_WAKE_GESTURE = "android.sensor.wake_gesture";

    @SystemApi
    public static final String STRING_TYPE_WRIST_TILT_GESTURE = "android.sensor.wrist_tilt_gesture";

    public static final int TYPE_ACCELEROMETER = 1;
    public static final int TYPE_ACCELEROMETER_LIMITED_AXES = 38;
    public static final int TYPE_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED = 40;
    public static final int TYPE_ACCELEROMETER_UNCALIBRATED = 35;
    public static final int TYPE_ALL = -1;
    public static final int TYPE_AMBIENT_TEMPERATURE = 13;
    public static final int TYPE_BIO = 65561;
    public static final int TYPE_BIO_ALC = 65577;
    public static final int TYPE_BIO_LED_GREEN = 65573;
    public static final int TYPE_BIO_LED_IR = 65571;
    public static final int TYPE_BIO_LED_RED = 65572;
    public static final int TYPE_BIO_LED_VIOLET = 65574;
    public static final int TYPE_BODY_TEMPERATURE = 65566;
    public static final int TYPE_CONTROL_MOTIONRECOGNITION = 65563;
    public static final int TYPE_DEVICE_ORIENTATION = 27;
    public static final int TYPE_DEVICE_PRIVATE_BASE = 65536;

    @SystemApi public static final int TYPE_DYNAMIC_SENSOR_META = 32;
    public static final int TYPE_ELECTROCARDIOGRAM = 65570;
    public static final int TYPE_GAME_ROTATION_VECTOR = 15;
    public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 20;
    public static final int TYPE_GLANCE_GESTURE = 24;
    public static final int TYPE_GRAVITY = 9;
    public static final int TYPE_GRIP = 65560;
    public static final int TYPE_GRIP_WIFI = 65575;
    public static final int TYPE_GYROSCOPE = 4;
    public static final int TYPE_GYROSCOPE_LIMITED_AXES = 39;
    public static final int TYPE_GYROSCOPE_LIMITED_AXES_UNCALIBRATED = 41;
    public static final int TYPE_GYROSCOPE_UNCALIBRATED = 16;
    public static final int TYPE_HEADING = 42;
    public static final int TYPE_HEAD_TRACKER = 37;
    public static final int TYPE_HEART_BEAT = 31;
    public static final int TYPE_HEART_RATE = 21;
    public static final int TYPE_HINGE_ANGLE = 36;
    public static final int TYPE_INTERRUPT_GYRO = 65579;
    public static final int TYPE_LIGHT = 5;
    public static final int TYPE_LIGHT_CCT = 65587;
    public static final int TYPE_LIGHT_IR = 65578;
    public static final int TYPE_LINEAR_ACCELERATION = 10;
    public static final int TYPE_LOW_LATENCY_OFFBODY_DETECT = 34;
    public static final int TYPE_MAGNETIC_FIELD = 2;
    public static final int TYPE_MAGNETIC_FIELD_UNCALIBRATED = 14;
    public static final int TYPE_MOTIONRECOGNITION = 65559;
    public static final int TYPE_MOTION_DETECT = 30;
    public static final int TYPE_MOTOR_TEST = 65581;

    @Deprecated public static final int TYPE_ORIENTATION = 3;
    public static final int TYPE_PEDOMETER_LOGGING = 65569;
    public static final int TYPE_PEDOMETER_NORMAL = 65568;
    public static final int TYPE_PICK_UP_GESTURE = 25;
    public static final int TYPE_POSE_6DOF = 28;
    public static final int TYPE_PRESSURE = 6;
    public static final int TYPE_PROXIMITY = 8;
    public static final int TYPE_PROXIMITY_ALERT = 65582;
    public static final int TYPE_PROXIMITY_FLICKER = 65583;
    public static final int TYPE_PROXIMITY_POCKET = 65589;
    public static final int TYPE_REAR_LED_BLUE = 65629;
    public static final int TYPE_REAR_LED_GREEN = 65628;
    public static final int TYPE_REAR_LED_RED = 65627;
    public static final int TYPE_REAR_PROX_DETECT = 65580;
    public static final int TYPE_RELATIVE_HUMIDITY = 12;
    public static final int TYPE_ROTATION_VECTOR = 11;
    public static final int TYPE_SCONTEXT = 65586;
    public static final int TYPE_SCREEN_ORIENTATION = 65558;
    public static final int TYPE_SIGNIFICANT_MOTION = 17;
    public static final int TYPE_STATIONARY_DETECT = 29;
    public static final int TYPE_STEP_COUNTER = 19;
    public static final int TYPE_STEP_DETECTOR = 18;
    public static final int TYPE_TABLE_ROTATION = 65564;

    @Deprecated public static final int TYPE_TEMPERATURE = 7;
    public static final int TYPE_THERMISTOR = 65588;
    public static final int TYPE_TILT_DETECTOR = 22;
    public static final int TYPE_ULTRAVIOLET = 65557;
    public static final int TYPE_UV_RAY = 65565;
    public static final int TYPE_WAKE_GESTURE = 23;

    @SystemApi public static final int TYPE_WRIST_TILT_GESTURE = 26;
    private int mFifoMaxEventCount;
    private int mFifoReservedEventCount;
    private int mFlags;
    private int mHandle;
    private int mId;
    private int mMaxDelay;
    private float mMaxRange;
    private int mMinDelay;
    private String mName;
    private float mPower;
    private String mRequiredPermission;
    private float mResolution;
    private String mStringType;
    private int mType;
    private UUID mUuid;
    private String mVendor;
    private int mVersion;
    private static final int[] sSensorReportingModes = {
        0, 3, 3, 3, 3, 1, 1, 1, 1, 3, 3, 5, 1, 1, 6, 4, 6, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 16, 1,
        1, 1, 2, 16, 1, 6, 1, 6, 6, 6, 9, 9, 2
    };
    private static final int[] sSamsungSensorReportingModes = {
        3, 2, 3, 16, 3, 5, 3, 1, 3, 5, 5, 2, 13, 16, 3, 3, 3, 3, 16, 16, 16, 16, 3, 16, 16, 16, 16,
        16, 16, 16, 10, 16, 16, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };
    private static final int[] sSamsungDualSensorReportingModes = {1, 3, 6, 3, 6};

    public int getReportingMode() {
        return (this.mFlags & 14) >> 1;
    }

    public int getHighestDirectReportRateLevel() {
        int rateLevel = (this.mFlags & 896) >> 7;
        if (rateLevel > 3) {
            return 3;
        }
        return rateLevel;
    }

    public boolean isDirectChannelTypeSupported(int sharedMemType) {
        switch (sharedMemType) {
            case 1:
                if ((this.mFlags & 1024) <= 0) {
                    break;
                }
                break;
            case 2:
                if ((this.mFlags & 2048) <= 0) {
                    break;
                }
                break;
        }
        return false;
    }

    public static int getMaxLengthValuesArray(Sensor sensor, int sdkLevel) {
        int SENSOR_TYPE_PRIVATE_END = sSamsungSensorReportingModes.length + TYPE_ULTRAVIOLET;
        int SENSOR_TYPE_DUAL_PRIVATE_END = sSamsungDualSensorReportingModes.length + 65686;
        if (sensor.mType == 11 && sdkLevel <= 17) {
            return 3;
        }
        int offset = sensor.mType;
        if (offset < sSensorReportingModes.length) {
            return sSensorReportingModes[offset];
        }
        if (offset >= 65557 && offset < SENSOR_TYPE_PRIVATE_END) {
            return sSamsungSensorReportingModes[offset - TYPE_ULTRAVIOLET];
        }
        if (offset >= 65686 && offset < SENSOR_TYPE_DUAL_PRIVATE_END) {
            return sSamsungDualSensorReportingModes[offset - 65686];
        }
        return 16;
    }

    Sensor() {}

    public Sensor(InputSensorInfo sensorInfo) {
        this.mName = sensorInfo.getName();
        this.mVendor = sensorInfo.getVendor();
        this.mVersion = sensorInfo.getVersion();
        this.mHandle = sensorInfo.getHandle();
        this.mType = sensorInfo.getType();
        this.mMaxRange = sensorInfo.getMaxRange();
        this.mResolution = sensorInfo.getResolution();
        this.mPower = sensorInfo.getPower();
        this.mMinDelay = sensorInfo.getMinDelay();
        this.mFifoReservedEventCount = sensorInfo.getFifoReservedEventCount();
        this.mFifoMaxEventCount = sensorInfo.getFifoMaxEventCount();
        this.mStringType = sensorInfo.getStringType();
        this.mRequiredPermission = sensorInfo.getRequiredPermission();
        this.mMaxDelay = sensorInfo.getMaxDelay();
        this.mFlags = sensorInfo.getFlags();
        this.mId = sensorInfo.getId();
        this.mUuid = new UUID(this.mId, 0L);
    }

    public String getName() {
        return this.mName;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public int getType() {
        return this.mType;
    }

    public int getVersion() {
        if (this.mType == 1) {
            this.mVersion &= 65535;
        }
        return this.mVersion;
    }

    public float getMaximumRange() {
        return this.mMaxRange;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public int getFifoReservedEventCount() {
        return this.mFifoReservedEventCount;
    }

    public int getFifoMaxEventCount() {
        return this.mFifoMaxEventCount;
    }

    public String getStringType() {
        return this.mStringType;
    }

    @SystemApi
    public UUID getUuid() {
        return this.mUuid;
    }

    public int getId() {
        return this.mId;
    }

    public String getRequiredPermission() {
        return this.mRequiredPermission;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public boolean isWakeUpSensor() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isDynamicSensor() {
        return (this.mFlags & 32) != 0;
    }

    public boolean isAdditionalInfoSupported() {
        return (this.mFlags & 64) != 0;
    }

    @SystemApi
    public boolean isDataInjectionSupported() {
        return ((this.mFlags & 16) >> 4) != 0;
    }

    void setRange(float max, float res) {
        this.mMaxRange = max;
        this.mResolution = res;
    }

    public String toString() {
        return "{Sensor name=\""
                + this.mName
                + "\", vendor=\""
                + this.mVendor
                + "\", version="
                + this.mVersion
                + ", type="
                + this.mType
                + ", maxRange="
                + this.mMaxRange
                + ", resolution="
                + this.mResolution
                + ", power="
                + this.mPower
                + ", minDelay="
                + this.mMinDelay
                + "}";
    }

    public boolean semIsOnFoldingSide() {
        String model;
        return (this.mType == 8 || this.mType == 5 || this.mType == 65601)
                && (model = SystemProperties.get("ro.product.vendor.device")) != null
                && (model.contains("bloom") || model.contains("b2q"));
    }

    private boolean setType(int value) {
        this.mType = value;
        switch (this.mType) {
            case 1:
                this.mStringType = STRING_TYPE_ACCELEROMETER;
                break;
            case 2:
                this.mStringType = STRING_TYPE_MAGNETIC_FIELD;
                break;
            case 3:
                this.mStringType = STRING_TYPE_ORIENTATION;
                break;
            case 4:
                this.mStringType = STRING_TYPE_GYROSCOPE;
                break;
            case 5:
                this.mStringType = STRING_TYPE_LIGHT;
                break;
            case 6:
                this.mStringType = STRING_TYPE_PRESSURE;
                break;
            case 7:
                this.mStringType = STRING_TYPE_TEMPERATURE;
                break;
            case 8:
                this.mStringType = STRING_TYPE_PROXIMITY;
                break;
            case 9:
                this.mStringType = STRING_TYPE_GRAVITY;
                break;
            case 10:
                this.mStringType = STRING_TYPE_LINEAR_ACCELERATION;
                break;
            case 11:
                this.mStringType = STRING_TYPE_ROTATION_VECTOR;
                break;
            case 12:
                this.mStringType = STRING_TYPE_RELATIVE_HUMIDITY;
                break;
            case 13:
                this.mStringType = STRING_TYPE_AMBIENT_TEMPERATURE;
                break;
            case 14:
                this.mStringType = STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED;
                break;
            case 15:
                this.mStringType = STRING_TYPE_GAME_ROTATION_VECTOR;
                break;
            case 16:
                this.mStringType = STRING_TYPE_GYROSCOPE_UNCALIBRATED;
                break;
            case 17:
                this.mStringType = STRING_TYPE_SIGNIFICANT_MOTION;
                break;
            case 18:
                this.mStringType = STRING_TYPE_STEP_DETECTOR;
                break;
            case 19:
                this.mStringType = STRING_TYPE_STEP_COUNTER;
                break;
            case 20:
                this.mStringType = STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR;
                break;
            case 21:
                this.mStringType = STRING_TYPE_HEART_RATE;
                break;
            case 22:
                this.mStringType = SENSOR_STRING_TYPE_TILT_DETECTOR;
                break;
            case 23:
                this.mStringType = STRING_TYPE_WAKE_GESTURE;
                break;
            case 24:
                this.mStringType = STRING_TYPE_GLANCE_GESTURE;
                break;
            case 25:
                this.mStringType = STRING_TYPE_PICK_UP_GESTURE;
                break;
            case 27:
                this.mStringType = STRING_TYPE_DEVICE_ORIENTATION;
                break;
            case 32:
                this.mStringType = STRING_TYPE_DYNAMIC_SENSOR_META;
                break;
            case 34:
                this.mStringType = STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT;
                break;
            case 35:
                this.mStringType = STRING_TYPE_ACCELEROMETER_UNCALIBRATED;
                break;
            case 36:
                this.mStringType = STRING_TYPE_HINGE_ANGLE;
                break;
            case 37:
                this.mStringType = STRING_TYPE_HEAD_TRACKER;
                break;
            case 38:
                this.mStringType = STRING_TYPE_ACCELEROMETER_LIMITED_AXES;
                break;
            case 39:
                this.mStringType = STRING_TYPE_GYROSCOPE_LIMITED_AXES;
                break;
            case 40:
                this.mStringType = STRING_TYPE_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED;
                break;
            case 41:
                this.mStringType = STRING_TYPE_GYROSCOPE_LIMITED_AXES_UNCALIBRATED;
                break;
            case 42:
                this.mStringType = STRING_TYPE_HEADING;
                break;
        }
        return true;
    }

    private void setUuid(long msb, long lsb) {
        this.mUuid = new UUID(msb, lsb);
    }

    private void setId(int id) {
        this.mId = id;
    }
}
