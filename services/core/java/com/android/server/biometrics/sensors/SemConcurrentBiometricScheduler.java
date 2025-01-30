package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemConcurrentBiometricScheduler extends BiometricScheduler {
    public SemConcurrentBiometricScheduler(String str, int i, Handler handler, GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
        this(str, i, handler, gestureAvailabilityDispatcher, IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")));
    }

    public SemConcurrentBiometricScheduler(String str, int i, Handler handler, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, IBiometricService iBiometricService) {
        super(str, handler, i, gestureAvailabilityDispatcher, iBiometricService, 50);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    /* renamed from: startPreparedClient, reason: merged with bridge method [inline-methods] */
    public void lambda$startPreparedClient$0(final int i) {
        if (!isRunOnMyThread()) {
            Slog.w(getTag(), "startPreparedClient: it's not running on main thread, " + Looper.myLooper());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SemConcurrentBiometricScheduler.this.lambda$startPreparedClient$0(i);
                }
            });
            return;
        }
        super.lambda$startPreparedClient$0(i);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    /* renamed from: scheduleClientMonitor, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleClientMonitor$1(final BaseClientMonitor baseClientMonitor, final ClientMonitorCallback clientMonitorCallback) {
        if (!isRunOnMyThread()) {
            Slog.w(getTag(), "scheduleClientMonitor: it's not running on main thread, " + Looper.myLooper());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemConcurrentBiometricScheduler.this.lambda$scheduleClientMonitor$1(baseClientMonitor, clientMonitorCallback);
                }
            });
            return;
        }
        super.lambda$scheduleClientMonitor$1(baseClientMonitor, clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    /* renamed from: cancelEnrollment, reason: merged with bridge method [inline-methods] */
    public void lambda$cancelEnrollment$2(final IBinder iBinder, final long j) {
        if (!isRunOnMyThread()) {
            Slog.w(getTag(), "cancelEnrollment: it's not running on main thread, " + Looper.myLooper());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SemConcurrentBiometricScheduler.this.lambda$cancelEnrollment$2(iBinder, j);
                }
            });
            return;
        }
        super.lambda$cancelEnrollment$2(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    /* renamed from: cancelAuthenticationOrDetection, reason: merged with bridge method [inline-methods] */
    public void lambda$cancelAuthenticationOrDetection$3(final IBinder iBinder, final long j) {
        if (!isRunOnMyThread()) {
            Slog.w(getTag(), "cancelAuthenticationOrDetection: it's not running on main thread, " + Looper.myLooper());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    SemConcurrentBiometricScheduler.this.lambda$cancelAuthenticationOrDetection$3(iBinder, j);
                }
            });
            return;
        }
        super.lambda$cancelAuthenticationOrDetection$3(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    public void reset() {
        if (!isRunOnMyThread()) {
            Slog.w(getTag(), "reset: it's not running on main thread, " + Looper.myLooper());
            this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemConcurrentBiometricScheduler.this.reset();
                }
            });
            return;
        }
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation != null) {
            biometricSchedulerOperation.destroy();
        }
        Iterator it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            ((BiometricSchedulerOperation) it.next()).destroy();
        }
        super.reset();
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    public BaseClientMonitor getCurrentClient() {
        BiometricSchedulerOperation biometricSchedulerOperation = this.mCurrentOperation;
        if (biometricSchedulerOperation != null) {
            return biometricSchedulerOperation.getClientMonitor();
        }
        return null;
    }

    public void cancelInterruptableOperation() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            BaseClientMonitor currentClient = getCurrentClient();
            if (currentClient != null && currentClient.isInterruptable()) {
                currentClient.cancel();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isRunOnMyThread() {
        Looper myLooper = Looper.myLooper();
        return myLooper == null || myLooper == this.mHandler.getLooper();
    }
}
