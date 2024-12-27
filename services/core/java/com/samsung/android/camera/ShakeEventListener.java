package com.samsung.android.camera;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Slog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ShakeEventListener extends BroadcastReceiver implements SensorEventListener {
    public final CameraServiceWorker mCameraServiceWorker;
    public final Handler mHandler;
    public boolean mInCall;
    public final String mLastEventMessage;
    public final Object mLock = new Object();
    public boolean mShakeDetected;
    public final AnonymousClass1 mShakeOffRunnable;
    public final PowerManager.WakeLock mWakeLock;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.camera.ShakeEventListener$1] */
    public ShakeEventListener(
            CameraServiceWorker cameraServiceWorker, Context context, Handler handler) {
        new ArrayList();
        this.mInCall = false;
        this.mShakeDetected = false;
        this.mLastEventMessage = "No event.";
        this.mShakeOffRunnable =
                new Runnable() { // from class: com.samsung.android.camera.ShakeEventListener.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        synchronized (ShakeEventListener.this) {
                        }
                    }
                };
        ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(65612, true);
        ((PowerManager) context.getSystemService("power")).newWakeLock(1, "ShakeEventListener");
        this.mHandler = handler;
        PendingIntent.getBroadcast(
                context,
                0,
                new Intent("com.samsung.android.intent.ACTION_CAMERA_SERVICE_WORKER_LOGGING"),
                67108864);
    }

    public final void handleShakeEventChanged() {
        synchronized (this.mLock) {
            String str =
                    "Shake event changed now(false) -> next(false), enable("
                            + Boolean.FALSE
                            + ") call("
                            + this.mInCall
                            + ") shake("
                            + this.mShakeDetected
                            + ")";
            Slog.w("ShakeEventListener", str);
            Logger.log(Logger.ID.SHAKE_EVENT_LISTENER, str);
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036 A[Catch: all -> 0x0026, TryCatch #0 {all -> 0x0026, blocks: (B:4:0x0003, B:9:0x000b, B:19:0x0036, B:26:0x0056, B:28:0x005a, B:29:0x0060, B:31:0x0064, B:32:0x001c, B:35:0x0028), top: B:3:0x0003 }] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onReceive(
            android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            java.lang.String r6 = "Audio mode changed: "
            monitor-enter(r5)
            java.lang.String r0 = r7.getAction()     // Catch: java.lang.Throwable -> L26
            if (r0 != 0) goto Lb
            monitor-exit(r5)
            return
        Lb:
            int r1 = r0.hashCode()     // Catch: java.lang.Throwable -> L26
            r2 = -1966727609(0xffffffff8ac61e47, float:-1.9078096E-32)
            r3 = 1
            r4 = 0
            if (r1 == r2) goto L28
            r2 = 154725547(0x938ecab, float:2.2259475E-33)
            if (r1 == r2) goto L1c
            goto L32
        L1c:
            java.lang.String r1 = "com.samsung.android.intent.ACTION_CAMERA_SERVICE_WORKER_LOGGING"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Throwable -> L26
            if (r0 == 0) goto L32
            r0 = r3
            goto L33
        L26:
            r6 = move-exception
            goto L6b
        L28:
            java.lang.String r1 = "android.samsung.media.action.AUDIO_MODE"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Throwable -> L26
            if (r0 == 0) goto L32
            r0 = r4
            goto L33
        L32:
            r0 = -1
        L33:
            if (r0 == 0) goto L36
            goto L69
        L36:
            java.lang.String r0 = "android.samsung.media.extra.AUDIO_MODE"
            int r7 = r7.getIntExtra(r0, r4)     // Catch: java.lang.Throwable -> L26
            java.lang.String r0 = "ShakeEventListener"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L26
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L26
            r1.append(r7)     // Catch: java.lang.Throwable -> L26
            java.lang.String r6 = r1.toString()     // Catch: java.lang.Throwable -> L26
            android.util.Slog.d(r0, r6)     // Catch: java.lang.Throwable -> L26
            if (r7 == 0) goto L60
            r6 = 2
            if (r7 == r6) goto L56
            r6 = 3
            if (r7 == r6) goto L56
            goto L69
        L56:
            boolean r6 = r5.mInCall     // Catch: java.lang.Throwable -> L26
            if (r6 != 0) goto L69
            r5.mInCall = r3     // Catch: java.lang.Throwable -> L26
            r5.handleShakeEventChanged()     // Catch: java.lang.Throwable -> L26
            goto L69
        L60:
            boolean r6 = r5.mInCall     // Catch: java.lang.Throwable -> L26
            if (r6 == 0) goto L69
            r5.mInCall = r4     // Catch: java.lang.Throwable -> L26
            r5.handleShakeEventChanged()     // Catch: java.lang.Throwable -> L26
        L69:
            monitor-exit(r5)
            return
        L6b:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.camera.ShakeEventListener.onReceive(android.content.Context,"
                    + " android.content.Intent):void");
    }

    @Override // android.hardware.SensorEventListener
    public final synchronized void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 65612) {
            int i = (int) sensorEvent.values[0];
            if (i == 1) {
                this.mShakeDetected = true;
                handleShakeEventChanged();
                this.mHandler.removeCallbacks(this.mShakeOffRunnable);
                this.mHandler.postDelayed(this.mShakeOffRunnable, TimeUnit.SECONDS.toMillis(10L));
            } else if (i != 2) {
                Slog.w("ShakeEventListener", "Unknown shake event. ignore");
            } else {
                this.mShakeDetected = false;
                handleShakeEventChanged();
                this.mHandler.removeCallbacks(this.mShakeOffRunnable);
            }
        }
    }
}
