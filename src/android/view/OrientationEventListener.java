package android.view;

import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.window.DesktopModeFlags;

/* loaded from: classes4.dex */
public abstract class OrientationEventListener {
    private static final boolean DEBUG = false;
    private static final int DEVICEINFO_COVER_DISPLAY_ON = 1;
    private static final int DEVICEINFO_FOLDER_OPEN = 0;
    private static final int DEVICEINFO_TABLE_MODE = 1;
    private static final int DEVICEINFO_TYPE_FLIP_COVERDISP = 3;
    public static final int ORIENTATION_UNKNOWN = -1;
    private static final String TAG = "OrientationEventListener";
    private static final boolean localLOGV = false;
    private Context mContext;
    private SensorEventListener mDeviceInfoListener;
    private Sensor mDeviceInfoSensor;
    private boolean mEnabled;
    private boolean mNotSupportReversePortrait;
    private OrientationListener mOldListener;
    private int mOrientation;
    private int mRate;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private SensorManager mSensorManager;
    private boolean mTableMode;

    public abstract void onOrientationChanged(int i);

    public OrientationEventListener(Context context) {
        this(context, 3);
    }

    public OrientationEventListener(Context context, int i) {
        SensorEventListener sensorEventListenerImpl;
        this.mOrientation = -1;
        this.mEnabled = false;
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mSensorManager = sensorManager;
        this.mRate = i;
        this.mTableMode = false;
        this.mDeviceInfoSensor = null;
        this.mDeviceInfoListener = null;
        Sensor defaultSensor = sensorManager.getDefaultSensor(1);
        this.mSensor = defaultSensor;
        this.mNotSupportReversePortrait = false;
        if (defaultSensor != null) {
            if (DesktopModeFlags.ENABLE_CAMERA_COMPAT_SIMULATE_REQUESTED_ORIENTATION.isTrue()) {
                sensorEventListenerImpl = new CompatSensorEventListenerImpl(new SensorEventListenerImpl());
            } else {
                sensorEventListenerImpl = new SensorEventListenerImpl();
            }
            this.mSensorEventListener = sensorEventListenerImpl;
            Sensor defaultSensor2 = this.mSensorManager.getDefaultSensor(Sensor.SEM_TYPE_DEVICE_COMMON_INFO);
            this.mDeviceInfoSensor = defaultSensor2;
            if (defaultSensor2 != null) {
                Log.d(TAG, "supports device_common_info");
                if (context.getPackageName().contains("whatsapp")) {
                    Log.d(TAG, "Package does not support reverse-portrait");
                    this.mNotSupportReversePortrait = true;
                }
                this.mDeviceInfoListener = new SensorEventListener() { // from class: android.view.OrientationEventListener.1
                    @Override // android.hardware.SensorEventListener
                    public void onAccuracyChanged(Sensor sensor, int i2) {
                    }

                    @Override // android.hardware.SensorEventListener
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if (sensorEvent.values[0] == 3.0f) {
                            if (sensorEvent.values[1] != 1.0f) {
                                if (sensorEvent.values[1] == 0.0f) {
                                    OrientationEventListener.this.mTableMode = false;
                                }
                            } else {
                                if (!OrientationEventListener.this.mNotSupportReversePortrait || OrientationEventListener.this.mTableMode) {
                                    return;
                                }
                                Log.d(OrientationEventListener.TAG, "onOrientationChanged 0");
                                OrientationEventListener.this.onOrientationChanged(0);
                                OrientationEventListener.this.mTableMode = true;
                            }
                        }
                    }
                };
            }
        }
        this.mContext = context;
    }

    void registerListener(OrientationListener orientationListener) {
        this.mOldListener = orientationListener;
    }

    public void enable() {
        Sensor sensor = this.mSensor;
        if (sensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        if (this.mEnabled) {
            return;
        }
        this.mTableMode = false;
        this.mSensorManager.registerListener(this.mSensorEventListener, sensor, this.mRate);
        Sensor sensor2 = this.mDeviceInfoSensor;
        if (sensor2 != null) {
            this.mSensorManager.registerListener(this.mDeviceInfoListener, sensor2, 3);
        }
        this.mEnabled = true;
    }

    public void disable() {
        if (this.mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Invalid disable");
            return;
        }
        if (this.mEnabled) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            SensorEventListener sensorEventListener = this.mDeviceInfoListener;
            if (sensorEventListener != null) {
                this.mSensorManager.unregisterListener(sensorEventListener);
            }
            this.mEnabled = false;
        }
    }

    class SensorEventListenerImpl implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        SensorEventListenerImpl() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (OrientationEventListener.this.isInAppCastingDisplay()) {
                return;
            }
            float[] fArr = sensorEvent.values;
            int i = -1;
            if (OrientationEventListener.this.mTableMode) {
                if (-1 != OrientationEventListener.this.mOrientation) {
                    OrientationEventListener.this.mOrientation = -1;
                    OrientationEventListener.this.onOrientationChanged(-1);
                    return;
                }
                return;
            }
            float f = -fArr[0];
            float f2 = -fArr[1];
            float f3 = -fArr[2];
            if (((f * f) + (f2 * f2)) * 4.0f >= f3 * f3) {
                int round = 90 - Math.round(((float) Math.atan2(-f2, f)) * 57.29578f);
                while (round >= 360) {
                    round -= 360;
                }
                i = round;
                while (i < 0) {
                    i += 360;
                }
            }
            if (OrientationEventListener.this.mOldListener != null) {
                OrientationEventListener.this.mOldListener.onSensorChanged(1, sensorEvent.values);
            }
            if (i != OrientationEventListener.this.mOrientation) {
                OrientationEventListener.this.mOrientation = i;
                OrientationEventListener.this.onOrientationChanged(i);
            }
        }
    }

    class CompatSensorEventListenerImpl implements SensorEventListener {
        final SensorEventListenerImpl mSensorEventListener;

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        CompatSensorEventListenerImpl(SensorEventListenerImpl sensorEventListenerImpl) {
            this.mSensorEventListener = sensorEventListenerImpl;
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (CompatibilityInfo.getOverrideDisplayRotation() != -1) {
                int overrideDisplayRotation = (360 - (CompatibilityInfo.getOverrideDisplayRotation() * 90)) % 360;
                if (overrideDisplayRotation != OrientationEventListener.this.mOrientation) {
                    OrientationEventListener.this.mOrientation = overrideDisplayRotation;
                    OrientationEventListener.this.onOrientationChanged(overrideDisplayRotation);
                    return;
                }
                return;
            }
            this.mSensorEventListener.onSensorChanged(sensorEvent);
        }
    }

    public boolean canDetectOrientation() {
        return this.mSensor != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInAppCastingDisplay() {
        Display displayNoVerify;
        Context context = this.mContext;
        return (context == null || (displayNoVerify = context.getDisplayNoVerify()) == null || (displayNoVerify.getFlags() & 33554432) == 0) ? false : true;
    }
}
