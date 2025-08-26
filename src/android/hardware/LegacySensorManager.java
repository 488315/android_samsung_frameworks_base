package android.hardware;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes2.dex */
final class LegacySensorManager {
    private static boolean sInitialized;
    private static int sRotation;
    private static IWindowManager sWindowManager;
    private final HashMap<SensorListener, LegacyListener> mLegacyListenersMap = new HashMap<>();
    private final SensorManager mSensorManager;

    public LegacySensorManager(SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
        synchronized (SensorManager.class) {
            if (!sInitialized) {
                IWindowManager iWindowManagerAsInterface = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
                sWindowManager = iWindowManagerAsInterface;
                if (iWindowManagerAsInterface != null) {
                    try {
                        sRotation = iWindowManagerAsInterface.watchRotation(new IRotationWatcher.Stub(this) { // from class: android.hardware.LegacySensorManager.1
                            @Override // android.view.IRotationWatcher
                            public void onRotationChanged(int i) {
                                LegacySensorManager.onRotationChanged(i);
                            }
                        }, 0);
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    public int getSensors() {
        Iterator<Sensor> it = this.mSensorManager.getFullSensorList().iterator();
        int i = 0;
        while (it.hasNext()) {
            int type = it.next().getType();
            if (type == 1) {
                i |= 2;
            } else if (type == 2) {
                i |= 8;
            } else if (type == 3) {
                i |= 129;
            }
        }
        return i;
    }

    public boolean registerListener(SensorListener sensorListener, int i, int i2) {
        if (sensorListener == null) {
            return false;
        }
        return registerLegacyListener(4, 7, sensorListener, i, i2) || (registerLegacyListener(1, 3, sensorListener, i, i2) || (registerLegacyListener(128, 3, sensorListener, i, i2) || (registerLegacyListener(8, 2, sensorListener, i, i2) || registerLegacyListener(2, 1, sensorListener, i, i2))));
    }

    private boolean registerLegacyListener(int i, int i2, SensorListener sensorListener, int i3, int i4) {
        Sensor defaultSensor;
        boolean zRegisterListener;
        if ((i3 & i) == 0 || (defaultSensor = this.mSensorManager.getDefaultSensor(i2)) == null) {
            return false;
        }
        synchronized (this.mLegacyListenersMap) {
            LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
            if (legacyListener == null) {
                legacyListener = new LegacyListener(sensorListener);
                this.mLegacyListenersMap.put(sensorListener, legacyListener);
            }
            zRegisterListener = legacyListener.registerSensor(i) ? this.mSensorManager.registerListener(legacyListener, defaultSensor, i4) : true;
        }
        return zRegisterListener;
    }

    public void unregisterListener(SensorListener sensorListener, int i) {
        if (sensorListener == null) {
            return;
        }
        unregisterLegacyListener(2, 1, sensorListener, i);
        unregisterLegacyListener(8, 2, sensorListener, i);
        unregisterLegacyListener(128, 3, sensorListener, i);
        unregisterLegacyListener(1, 3, sensorListener, i);
        unregisterLegacyListener(4, 7, sensorListener, i);
    }

    private void unregisterLegacyListener(int i, int i2, SensorListener sensorListener, int i3) {
        Sensor defaultSensor;
        if ((i3 & i) == 0 || (defaultSensor = this.mSensorManager.getDefaultSensor(i2)) == null) {
            return;
        }
        synchronized (this.mLegacyListenersMap) {
            LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
            if (legacyListener != null && legacyListener.unregisterSensor(i)) {
                this.mSensorManager.unregisterListener(legacyListener, defaultSensor);
                if (!legacyListener.hasSensors()) {
                    this.mLegacyListenersMap.remove(sensorListener);
                }
            }
        }
    }

    static void onRotationChanged(int i) {
        synchronized (SensorManager.class) {
            sRotation = i;
        }
    }

    static int getRotation() {
        int i;
        synchronized (SensorManager.class) {
            i = sRotation;
        }
        return i;
    }

    private static final class LegacyListener implements SensorEventListener {
        private SensorListener mTarget;
        private float[] mValues = new float[6];
        private final LmsFilter mYawfilter = new LmsFilter();
        private int mSensors = 0;

        private static int getLegacySensorType(int i) {
            if (i == 1) {
                return 2;
            }
            if (i == 2) {
                return 8;
            }
            if (i != 3) {
                return i != 7 ? 0 : 4;
            }
            return 128;
        }

        private static boolean hasOrientationSensor(int i) {
            return (i & 129) != 0;
        }

        LegacyListener(SensorListener sensorListener) {
            this.mTarget = sensorListener;
        }

        boolean registerSensor(int i) {
            int i2 = this.mSensors;
            if ((i2 & i) != 0) {
                return false;
            }
            boolean zHasOrientationSensor = hasOrientationSensor(i2);
            this.mSensors |= i;
            return (zHasOrientationSensor && hasOrientationSensor(i)) ? false : true;
        }

        boolean unregisterSensor(int i) {
            int i2 = this.mSensors;
            if ((i2 & i) == 0) {
                return false;
            }
            this.mSensors = i2 & (~i);
            return (hasOrientationSensor(i) && hasOrientationSensor(this.mSensors)) ? false : true;
        }

        boolean hasSensors() {
            return this.mSensors != 0;
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
            try {
                this.mTarget.onAccuracyChanged(getLegacySensorType(sensor.getType()), i);
            } catch (AbstractMethodError unused) {
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] fArr = this.mValues;
            fArr[0] = sensorEvent.values[0];
            fArr[1] = sensorEvent.values[1];
            fArr[2] = sensorEvent.values[2];
            int type = sensorEvent.sensor.getType();
            int legacySensorType = getLegacySensorType(type);
            mapSensorDataToWindow(legacySensorType, fArr, LegacySensorManager.getRotation());
            if (type == 3) {
                if ((this.mSensors & 128) != 0) {
                    this.mTarget.onSensorChanged(128, fArr);
                }
                if ((this.mSensors & 1) != 0) {
                    fArr[0] = this.mYawfilter.filter(sensorEvent.timestamp, fArr[0]);
                    this.mTarget.onSensorChanged(1, fArr);
                    return;
                }
                return;
            }
            this.mTarget.onSensorChanged(legacySensorType, fArr);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0040  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void mapSensorDataToWindow(int i, float[] fArr, int i2) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            if (i == 1) {
                f3 = -f3;
            } else {
                if (i == 2) {
                    f = -f;
                    f2 = -f2;
                } else if (i == 8) {
                    f = -f;
                    f2 = -f2;
                } else if (i == 128) {
                }
                f3 = -f3;
            }
            fArr[0] = f;
            fArr[1] = f2;
            fArr[2] = f3;
            fArr[3] = f;
            fArr[4] = f2;
            fArr[5] = f3;
            if ((i2 & 1) != 0) {
                if (i == 1) {
                    fArr[0] = f + (f < 270.0f ? 90 : -270);
                    fArr[1] = f3;
                    fArr[2] = f2;
                } else if (i == 2 || i == 8) {
                    fArr[0] = -f2;
                    fArr[1] = f;
                    fArr[2] = f3;
                } else if (i == 128) {
                }
            }
            if ((i2 & 2) != 0) {
                float f4 = fArr[0];
                float f5 = fArr[1];
                float f6 = fArr[2];
                if (i != 1) {
                    if (i == 2 || i == 8) {
                        fArr[0] = -f4;
                        fArr[1] = -f5;
                        fArr[2] = f6;
                        return;
                    } else if (i != 128) {
                        return;
                    }
                }
                fArr[0] = f4 >= 180.0f ? f4 - 180.0f : f4 + 180.0f;
                fArr[1] = -f5;
                fArr[2] = -f6;
            }
        }
    }

    private static final class LmsFilter {
        private static final int COUNT = 12;
        private static final float PREDICTION_RATIO = 0.33333334f;
        private static final float PREDICTION_TIME = 0.08f;
        private static final int SENSORS_RATE_MS = 20;
        private float[] mV = new float[24];
        private long[] mT = new long[24];
        private int mIndex = 12;

        public float filter(long j, float f) {
            float[] fArr = this.mV;
            int i = this.mIndex;
            float f2 = fArr[i];
            float f3 = f - f2 > 180.0f ? f - 360.0f : f2 - f > 180.0f ? f + 360.0f : f;
            int i2 = i + 1;
            this.mIndex = i2;
            if (i2 >= 24) {
                this.mIndex = 12;
            }
            int i3 = this.mIndex;
            fArr[i3] = f3;
            long[] jArr = this.mT;
            jArr[i3] = j;
            fArr[i3 - 12] = f3;
            jArr[i3 - 12] = j;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            for (int i4 = 0; i4 < 11; i4++) {
                int i5 = (this.mIndex - 1) - i4;
                float f9 = this.mV[i5];
                long[] jArr2 = this.mT;
                long j2 = jArr2[i5];
                long j3 = jArr2[i5 + 1];
                float f10 = (((j2 / 2) + (j3 / 2)) - j) * 1.0E-9f;
                float f11 = (j2 - j3) * 1.0E-9f;
                float f12 = f11 * f11;
                f4 += f9 * f12;
                float f13 = f10 * f12;
                f5 += f10 * f13;
                f6 += f13;
                f7 += f9 * f13;
                f8 += f12;
            }
            float f14 = ((f4 * f5) + (f7 * f6)) / ((f5 * f8) + (f6 * f6));
            float fCeil = (f14 + ((((f8 * f14) - f4) / f6) * PREDICTION_TIME)) * 0.0027777778f;
            if ((fCeil >= 0.0f ? fCeil : -fCeil) >= 0.5f) {
                fCeil = (fCeil - ((float) Math.ceil(0.5f + fCeil))) + 1.0f;
            }
            if (fCeil < 0.0f) {
                fCeil += 1.0f;
            }
            return fCeil * 360.0f;
        }
    }
}
