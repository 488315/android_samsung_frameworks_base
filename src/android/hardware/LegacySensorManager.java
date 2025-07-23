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
                IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
                sWindowManager = asInterface;
                if (asInterface != null) {
                    try {
                        sRotation = asInterface.watchRotation(new IRotationWatcher.Stub(this) { // from class: android.hardware.LegacySensorManager.1
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
        boolean registerListener;
        if ((i3 & i) == 0 || (defaultSensor = this.mSensorManager.getDefaultSensor(i2)) == null) {
            return false;
        }
        synchronized (this.mLegacyListenersMap) {
            LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
            if (legacyListener == null) {
                legacyListener = new LegacyListener(sensorListener);
                this.mLegacyListenersMap.put(sensorListener, legacyListener);
            }
            registerListener = legacyListener.registerSensor(i) ? this.mSensorManager.registerListener(legacyListener, defaultSensor, i4) : true;
        }
        return registerListener;
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
            boolean hasOrientationSensor = hasOrientationSensor(i2);
            this.mSensors |= i;
            return (hasOrientationSensor && hasOrientationSensor(i)) ? false : true;
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

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
        
            if (r9 != 128) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:5:0x0013, code lost:
        
            if (r9 != 128) goto L11;
         */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
        /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void mapSensorDataToWindow(int r9, float[] r10, int r11) {
            /*
                r8 = this;
                r8 = 0
                r0 = r10[r8]
                r1 = 1
                r2 = r10[r1]
                r3 = 2
                r4 = r10[r3]
                r5 = 128(0x80, float:1.8E-43)
                r6 = 8
                if (r9 == r1) goto L1b
                if (r9 == r3) goto L19
                if (r9 == r6) goto L16
                if (r9 == r5) goto L1b
                goto L1c
            L16:
                float r0 = -r0
                float r2 = -r2
                goto L1c
            L19:
                float r0 = -r0
                float r2 = -r2
            L1b:
                float r4 = -r4
            L1c:
                r10[r8] = r0
                r10[r1] = r2
                r10[r3] = r4
                r7 = 3
                r10[r7] = r0
                r7 = 4
                r10[r7] = r2
                r7 = 5
                r10[r7] = r4
                r7 = r11 & 1
                if (r7 == 0) goto L53
                if (r9 == r1) goto L40
                if (r9 == r3) goto L38
                if (r9 == r6) goto L38
                if (r9 == r5) goto L40
                goto L53
            L38:
                float r2 = -r2
                r10[r8] = r2
                r10[r1] = r0
                r10[r3] = r4
                goto L53
            L40:
                r7 = 1132920832(0x43870000, float:270.0)
                int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                if (r7 >= 0) goto L49
                r7 = 90
                goto L4b
            L49:
                r7 = -270(0xfffffffffffffef2, float:NaN)
            L4b:
                float r7 = (float) r7
                float r0 = r0 + r7
                r10[r8] = r0
                r10[r1] = r4
                r10[r3] = r2
            L53:
                r11 = r11 & r3
                if (r11 == 0) goto L7f
                r11 = r10[r8]
                r0 = r10[r1]
                r2 = r10[r3]
                if (r9 == r1) goto L6e
                if (r9 == r3) goto L65
                if (r9 == r6) goto L65
                if (r9 == r5) goto L6e
                goto L7f
            L65:
                float r9 = -r11
                r10[r8] = r9
                float r8 = -r0
                r10[r1] = r8
                r10[r3] = r2
                return
            L6e:
                r9 = 1127481344(0x43340000, float:180.0)
                int r4 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r4 < 0) goto L76
                float r11 = r11 - r9
                goto L77
            L76:
                float r11 = r11 + r9
            L77:
                r10[r8] = r11
                float r8 = -r0
                r10[r1] = r8
                float r8 = -r2
                r10[r3] = r8
            L7f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.LegacySensorManager.LegacyListener.mapSensorDataToWindow(int, float[], int):void");
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
            float f15 = (f14 + ((((f8 * f14) - f4) / f6) * PREDICTION_TIME)) * 0.0027777778f;
            if ((f15 >= 0.0f ? f15 : -f15) >= 0.5f) {
                f15 = (f15 - ((float) Math.ceil(0.5f + f15))) + 1.0f;
            }
            if (f15 < 0.0f) {
                f15 += 1.0f;
            }
            return f15 * 360.0f;
        }
    }
}
