package com.android.server.sensors;

import android.content.Context;
import android.util.ArrayMap;

import com.android.internal.util.ConcurrentUtils;
import com.android.server.LocalServices;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.SystemService;
import com.android.server.utils.TimingsTraceAndSlog;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class SensorService extends SystemService {
    public static final String START_NATIVE_SENSOR_SERVICE = "StartNativeSensorService";
    public final Object mLock;
    public final ArrayMap mProximityListeners;
    public long mPtr;
    public final Set mRuntimeSensorHandles;
    public Future mSensorServiceStart;

    public final class LocalService {
        public LocalService() {}
    }

    public final class ProximityListenerDelegate
            implements SensorManagerInternal$ProximityActiveListener {
        public ProximityListenerDelegate() {}

        @Override // com.android.server.sensors.SensorManagerInternal$ProximityActiveListener
        public final void onProximityActive(boolean z) {
            int i;
            ProximityListenerProxy[] proximityListenerProxyArr;
            synchronized (SensorService.this.mLock) {
                proximityListenerProxyArr =
                        (ProximityListenerProxy[])
                                SensorService.this
                                        .mProximityListeners
                                        .values()
                                        .toArray(new ProximityListenerProxy[0]);
            }
            for (ProximityListenerProxy proximityListenerProxy : proximityListenerProxyArr) {
                proximityListenerProxy.onProximityActive(z);
            }
        }
    }

    public final class ProximityListenerProxy
            implements SensorManagerInternal$ProximityActiveListener {
        public final Executor mExecutor;
        public final SensorManagerInternal$ProximityActiveListener mListener;

        public ProximityListenerProxy(
                Executor executor,
                SensorManagerInternal$ProximityActiveListener
                        sensorManagerInternal$ProximityActiveListener) {
            this.mExecutor = executor;
            this.mListener = sensorManagerInternal$ProximityActiveListener;
        }

        @Override // com.android.server.sensors.SensorManagerInternal$ProximityActiveListener
        public final void onProximityActive(final boolean z) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.sensors.SensorService$ProximityListenerProxy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SensorService.ProximityListenerProxy proximityListenerProxy =
                                    SensorService.ProximityListenerProxy.this;
                            proximityListenerProxy.mListener.onProximityActive(z);
                        }
                    });
        }
    }

    public SensorService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mProximityListeners = new ArrayMap();
        this.mRuntimeSensorHandles = new HashSet();
        synchronized (obj) {
            this.mSensorServiceStart =
                    SystemServerInitThreadPool.submit(
                            START_NATIVE_SENSOR_SERVICE,
                            new Runnable() { // from class:
                                             // com.android.server.sensors.SensorService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SensorService.this.lambda$new$0();
                                }
                            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin(START_NATIVE_SENSOR_SERVICE);
        long startSensorServiceNative = startSensorServiceNative(new ProximityListenerDelegate());
        synchronized (this.mLock) {
            this.mPtr = startSensorServiceNative;
        }
        newAsyncLog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void registerProximityActiveListenerNative(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int registerRuntimeSensorNative(
            long j,
            int i,
            int i2,
            String str,
            String str2,
            float f,
            float f2,
            float f3,
            int i3,
            int i4,
            int i5,
            SensorManagerInternal$RuntimeSensorCallback
                    sensorManagerInternal$RuntimeSensorCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean sendRuntimeSensorEventNative(
            long j, int i, int i2, long j2, float[] fArr);

    private static native long startSensorServiceNative(
            SensorManagerInternal$ProximityActiveListener
                    sensorManagerInternal$ProximityActiveListener);

    private static native void unregisterProximityActiveListenerNative(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void unregisterRuntimeSensorNative(long j, int i);

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 200) {
            ConcurrentUtils.waitForFutureNoInterrupt(
                    this.mSensorServiceStart, START_NATIVE_SENSOR_SERVICE);
            synchronized (this.mLock) {
                this.mSensorServiceStart = null;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        LocalServices.addService(LocalService.class, new LocalService());
    }
}
