package com.android.server.biometrics.sensors;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricSchedulerOperation {
    final Runnable mCancelWatchdog;
    public final ClientMonitorCallback mClientCallback;
    public final BaseClientMonitor mClientMonitor;
    public final BooleanSupplier mIsDebuggable;
    public ClientMonitorCallback mOnStartCallback;
    public int mState;

    public BiometricSchedulerOperation(
            int i,
            BaseClientMonitor baseClientMonitor,
            ClientMonitorCallback clientMonitorCallback) {
        this(
                baseClientMonitor,
                clientMonitorCallback,
                i,
                new BiometricSchedulerOperation$$ExternalSyntheticLambda1());
    }

    public BiometricSchedulerOperation(
            BaseClientMonitor baseClientMonitor,
            ClientMonitorCallback clientMonitorCallback,
            int i,
            BooleanSupplier booleanSupplier) {
        this.mClientMonitor = baseClientMonitor;
        this.mClientCallback = clientMonitorCallback;
        this.mState = i;
        this.mIsDebuggable = booleanSupplier;
        this.mCancelWatchdog =
                new Runnable() { // from class:
                    // com.android.server.biometrics.sensors.BiometricSchedulerOperation$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricSchedulerOperation biometricSchedulerOperation =
                                BiometricSchedulerOperation.this;
                        BaseClientMonitor baseClientMonitor2 =
                                biometricSchedulerOperation.mClientMonitor;
                        if (biometricSchedulerOperation.mState == 5) {
                            return;
                        }
                        Slog.e(
                                "BiometricSchedulerOperation",
                                "[Watchdog Triggered]: " + biometricSchedulerOperation);
                        try {
                            baseClientMonitor2.mListener.onError(
                                    baseClientMonitor2.mSensorId, baseClientMonitor2.mCookie, 5, 0);
                        } catch (RemoteException unused) {
                            Slog.e(
                                    "BiometricSchedulerOperation",
                                    "Remote exception when trying to send error in cancel"
                                        + " watchdog.");
                        }
                        biometricSchedulerOperation
                                .getWrappedCallback(biometricSchedulerOperation.mOnStartCallback)
                                .onClientFinished(baseClientMonitor2, false);
                    }
                };
    }

    public BiometricSchedulerOperation(
            BaseClientMonitor baseClientMonitor,
            ClientMonitorCallback clientMonitorCallback,
            BooleanSupplier booleanSupplier) {
        this(baseClientMonitor, clientMonitorCallback, 0, booleanSupplier);
    }

    public final void cancel(Handler handler, ClientMonitorCallback clientMonitorCallback) {
        boolean contains = ArrayUtils.contains(new int[] {5}, this.mState);
        if (contains) {
            String str = "cancel: mState must not be " + this.mState;
            if (this.mIsDebuggable.getAsBoolean()) {
                throw new IllegalStateException(str);
            }
            Slog.e("BiometricSchedulerOperation", str);
        }
        if (contains) {
            return;
        }
        int i = this.mState;
        if (i == 3) {
            Slog.w(
                    "BiometricSchedulerOperation",
                    "Cannot cancel - already invoked for operation: " + this);
            return;
        }
        this.mState = 3;
        BaseClientMonitor baseClientMonitor = this.mClientMonitor;
        if (i == 0 || i == 1 || i == 4) {
            Slog.d(
                    "BiometricSchedulerOperation",
                    "[Cancelling] Current client (without start): " + baseClientMonitor);
            baseClientMonitor.cancelWithoutStarting(getWrappedCallback(clientMonitorCallback));
        } else {
            Slog.d(
                    "BiometricSchedulerOperation",
                    "[Cancelling] Current client: " + baseClientMonitor);
            baseClientMonitor.cancel();
        }
        handler.postDelayed(this.mCancelWatchdog, 3000L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean doStart(BiometricScheduler.AnonymousClass1 anonymousClass1) {
        this.mOnStartCallback = anonymousClass1;
        ClientMonitorCompositeCallback wrappedCallback = getWrappedCallback(anonymousClass1);
        int i = this.mState;
        BaseClientMonitor baseClientMonitor = this.mClientMonitor;
        if (i == 1) {
            Slog.d(
                    "BiometricSchedulerOperation",
                    "Operation marked for cancellation, cancelling now: " + this);
            wrappedCallback.onClientFinished(baseClientMonitor, true);
            if (baseClientMonitor instanceof ErrorConsumer) {
                ((ErrorConsumer) baseClientMonitor).onError(5, 0);
            } else {
                Slog.w(
                        "BiometricSchedulerOperation",
                        "monitor cancelled but does not implement ErrorConsumer");
            }
            return false;
        }
        if (baseClientMonitor instanceof HalClientMonitor) {
            HalClientMonitor halClientMonitor = (HalClientMonitor) baseClientMonitor;
            if (halClientMonitor.mLazyDaemon.get() == null) {
                Slog.v("BiometricSchedulerOperation", "unable to start: " + this);
                halClientMonitor.unableToStart();
                wrappedCallback.onClientFinished(baseClientMonitor, false);
                return false;
            }
        }
        this.mState = 2;
        baseClientMonitor.start(wrappedCallback);
        Slog.v("BiometricSchedulerOperation", "started: " + this);
        return true;
    }

    public final boolean errorWhenNoneOf(String str, int... iArr) {
        boolean z = !ArrayUtils.contains(iArr, this.mState);
        if (z) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ": mState=");
            m.append(this.mState);
            m.append(" must be one of ");
            m.append(Arrays.toString(iArr));
            String sb = m.toString();
            if (this.mIsDebuggable.getAsBoolean()) {
                throw new IllegalStateException(sb);
            }
            Slog.e("BiometricSchedulerOperation", sb);
        }
        return z;
    }

    public final ClientMonitorCompositeCallback getWrappedCallback(
            ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(
                new ClientMonitorCallback() { // from class:
                                              // com.android.server.biometrics.sensors.BiometricSchedulerOperation.1
                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z) {
                        Slog.d(
                                "BiometricSchedulerOperation",
                                "[Finished / destroy]: " + baseClientMonitor);
                        BiometricSchedulerOperation biometricSchedulerOperation =
                                BiometricSchedulerOperation.this;
                        biometricSchedulerOperation.mClientMonitor.destroy();
                        biometricSchedulerOperation.mState = 5;
                    }
                },
                clientMonitorCallback,
                this.mClientCallback);
    }

    public final boolean isMatchingRequestId(long j) {
        long j2 = this.mClientMonitor.mRequestId;
        return j2 <= 0 || j2 == j;
    }

    public final boolean markCanceling() {
        if (this.mState != 0 || !this.mClientMonitor.isInterruptable()) {
            return false;
        }
        this.mState = 1;
        return true;
    }

    public void markCancelingForWatchdog() {
        this.mState = 1;
    }

    public final String toString() {
        return this.mClientMonitor + ", State: " + this.mState;
    }
}
