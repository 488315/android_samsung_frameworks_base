package android.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

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
        this.mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mRate = rate;
        this.mTableMode = false;
        this.mDeviceInfoSensor = null;
        this.mDeviceInfoListener = null;
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
        this.mNotSupportReversePortrait = false;
        if (this.mSensor != null) {
            this.mSensorEventListener = new SensorEventListenerImpl();
            this.mDeviceInfoSensor =
                    this.mSensorManager.getDefaultSensor(Sensor.SEM_TYPE_DEVICE_COMMON_INFO);
            if (this.mDeviceInfoSensor != null) {
                Log.d(TAG, "supports device_common_info");
                if (context.getPackageName().contains("whatsapp")) {
                    Log.d(TAG, "Package does not support reverse-portrait");
                    this.mNotSupportReversePortrait = true;
                }
                this.mDeviceInfoListener =
                        new SensorEventListener() { // from class:
                                                    // android.view.OrientationEventListener.1
                            @Override // android.hardware.SensorEventListener
                            public void onSensorChanged(SensorEvent event) {
                                if (event.values[0] == 3.0f) {
                                    if (event.values[1] != 1.0f) {
                                        if (event.values[1] == 0.0f) {
                                            OrientationEventListener.this.mTableMode = false;
                                        }
                                    } else if (OrientationEventListener.this
                                                    .mNotSupportReversePortrait
                                            && !OrientationEventListener.this.mTableMode) {
                                        Log.d(
                                                OrientationEventListener.TAG,
                                                "onOrientationChanged 0");
                                        OrientationEventListener.this.onOrientationChanged(0);
                                        OrientationEventListener.this.mTableMode = true;
                                    }
                                }
                            }

                            @Override // android.hardware.SensorEventListener
                            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
                        };
            }
        }
        this.mContext = context;
    }

    void registerListener(OrientationListener lis) {
        this.mOldListener = lis;
    }

    public void enable() {
        if (this.mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        if (!this.mEnabled) {
            this.mTableMode = false;
            this.mSensorManager.registerListener(
                    this.mSensorEventListener, this.mSensor, this.mRate);
            if (this.mDeviceInfoSensor != null) {
                this.mSensorManager.registerListener(
                        this.mDeviceInfoListener, this.mDeviceInfoSensor, 3);
            }
            this.mEnabled = true;
        }
    }

    public void disable() {
        if (this.mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Invalid disable");
        } else if (this.mEnabled) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            if (this.mDeviceInfoListener != null) {
                this.mSensorManager.unregisterListener(this.mDeviceInfoListener);
            }
            this.mEnabled = false;
        }
    }

    class SensorEventListenerImpl implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        SensorEventListenerImpl() {}

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
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }

    public boolean canDetectOrientation() {
        return this.mSensor != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInAppCastingDisplay() {
        Display display;
        return (this.mContext == null
                        || (display = this.mContext.getDisplayNoVerify()) == null
                        || (display.getFlags() & 33554432) == 0)
                ? false
                : true;
    }
}
