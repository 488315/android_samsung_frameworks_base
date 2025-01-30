package android.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

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

    public OrientationEventListener(Context context, int rate) {
        this.mOrientation = -1;
        this.mEnabled = false;
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mSensorManager = sensorManager;
        this.mRate = rate;
        this.mTableMode = false;
        this.mDeviceInfoSensor = null;
        this.mDeviceInfoListener = null;
        Sensor defaultSensor = sensorManager.getDefaultSensor(1);
        this.mSensor = defaultSensor;
        this.mNotSupportReversePortrait = false;
        if (defaultSensor != null) {
            this.mSensorEventListener = new SensorEventListenerImpl();
            Sensor defaultSensor2 = this.mSensorManager.getDefaultSensor(Sensor.SEM_TYPE_DEVICE_COMMON_INFO);
            this.mDeviceInfoSensor = defaultSensor2;
            if (defaultSensor2 != null) {
                Log.m94d(TAG, "supports device_common_info");
                if (context.getPackageName().contains("whatsapp")) {
                    Log.m94d(TAG, "Package does not support reverse-portrait");
                    this.mNotSupportReversePortrait = true;
                }
                this.mDeviceInfoListener = new SensorEventListener() { // from class: android.view.OrientationEventListener.1
                    @Override // android.hardware.SensorEventListener
                    public void onSensorChanged(SensorEvent event) {
                        if (event.values[0] == 3.0f) {
                            if (event.values[1] != 1.0f) {
                                if (event.values[1] == 0.0f) {
                                    OrientationEventListener.this.mTableMode = false;
                                }
                            } else if (OrientationEventListener.this.mNotSupportReversePortrait && !OrientationEventListener.this.mTableMode) {
                                Log.m94d(OrientationEventListener.TAG, "onOrientationChanged 0");
                                OrientationEventListener.this.onOrientationChanged(0);
                                OrientationEventListener.this.mTableMode = true;
                            }
                        }
                    }

                    @Override // android.hardware.SensorEventListener
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };
            }
        }
        this.mContext = context;
    }

    void registerListener(OrientationListener lis) {
        this.mOldListener = lis;
    }

    public void enable() {
        Sensor sensor = this.mSensor;
        if (sensor == null) {
            Log.m102w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        if (!this.mEnabled) {
            this.mTableMode = false;
            this.mSensorManager.registerListener(this.mSensorEventListener, sensor, this.mRate);
            Sensor sensor2 = this.mDeviceInfoSensor;
            if (sensor2 != null) {
                this.mSensorManager.registerListener(this.mDeviceInfoListener, sensor2, 3);
            }
            this.mEnabled = true;
        }
    }

    public void disable() {
        if (this.mSensor == null) {
            Log.m102w(TAG, "Cannot detect sensors. Invalid disable");
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

        SensorEventListenerImpl() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent event) {
            if (OrientationEventListener.this.isInAppCastingDisplay()) {
                return;
            }
            float[] values = event.values;
            int orientation = -1;
            if (OrientationEventListener.this.mTableMode) {
                if (-1 != OrientationEventListener.this.mOrientation) {
                    OrientationEventListener.this.mOrientation = -1;
                    OrientationEventListener.this.onOrientationChanged(-1);
                    return;
                }
                return;
            }
            float X = -values[0];
            float Y = -values[1];
            float Z = -values[2];
            float magnitude = (X * X) + (Y * Y);
            if (4.0f * magnitude >= Z * Z) {
                float angle = ((float) Math.atan2(-Y, X)) * 57.29578f;
                orientation = 90 - Math.round(angle);
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (OrientationEventListener.this.mOldListener != null) {
                OrientationEventListener.this.mOldListener.onSensorChanged(1, event.values);
            }
            if (orientation != OrientationEventListener.this.mOrientation) {
                OrientationEventListener.this.mOrientation = orientation;
                OrientationEventListener.this.onOrientationChanged(orientation);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInAppCastingDisplay() {
        Display display;
        Context context = this.mContext;
        return (context == null || (display = context.getDisplayNoVerify()) == null || (display.getFlags() & 16384) == 0) ? false : true;
    }

    public boolean canDetectOrientation() {
        return this.mSensor != null;
    }
}
