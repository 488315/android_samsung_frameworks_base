package com.android.server.biometrics.sensors;

import android.util.Slog;

import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricScheduler$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ClientMonitorCallback f$0;
    public final /* synthetic */ BaseClientMonitor f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BiometricScheduler$1$$ExternalSyntheticLambda0(
            ClientMonitorCallback clientMonitorCallback,
            BaseClientMonitor baseClientMonitor,
            boolean z,
            int i) {
        this.$r8$classId = i;
        this.f$0 = clientMonitorCallback;
        this.f$1 = baseClientMonitor;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BiometricScheduler.AnonymousClass1 anonymousClass1 =
                        (BiometricScheduler.AnonymousClass1) this.f$0;
                BaseClientMonitor baseClientMonitor = this.f$1;
                boolean z = this.f$2;
                BiometricScheduler biometricScheduler = BiometricScheduler.this;
                BiometricSchedulerOperation biometricSchedulerOperation =
                        biometricScheduler.mCurrentOperation;
                if (biometricSchedulerOperation != null) {
                    if (biometricSchedulerOperation.mClientMonitor != baseClientMonitor) {
                        Slog.e(
                                "BiometricScheduler",
                                "[Ignoring Finish] "
                                        + baseClientMonitor
                                        + " does not match current: "
                                        + biometricScheduler.mCurrentOperation);
                        break;
                    } else {
                        Slog.d(
                                "BiometricScheduler",
                                "[Finishing] " + baseClientMonitor + ", success: " + z);
                        GestureAvailabilityDispatcher gestureAvailabilityDispatcher =
                                biometricScheduler.mGestureAvailabilityDispatcher;
                        if (gestureAvailabilityDispatcher != null) {
                            gestureAvailabilityDispatcher.markSensorActive(
                                    biometricScheduler.mCurrentOperation.mClientMonitor.mSensorId,
                                    false);
                        }
                        if (((ArrayList) biometricScheduler.mRecentOperations).size()
                                >= biometricScheduler.mRecentOperationsLimit) {
                            ((ArrayList) biometricScheduler.mRecentOperations).remove(0);
                        }
                        ((ArrayList) biometricScheduler.mRecentOperations)
                                .add(
                                        Integer.valueOf(
                                                biometricScheduler.mCurrentOperation.mClientMonitor
                                                        .getProtoEnum()));
                        BaseClientMonitor baseClientMonitor2 =
                                biometricScheduler.mCurrentOperation.mClientMonitor;
                        if (baseClientMonitor2 != null) {
                            baseClientMonitor2.destroy();
                        }
                        biometricScheduler.mCurrentOperation = null;
                        biometricScheduler.mTotalOperationsHandled++;
                        biometricScheduler.checkCurrentUserAndStartNextOperation();
                        break;
                    }
                } else {
                    Slog.e(
                            "BiometricScheduler",
                            "[Finishing] "
                                    + baseClientMonitor
                                    + " but current operation is null, success: "
                                    + z
                                    + ", possible lifecycle bug in clientMonitor implementation?");
                    break;
                }
            default:
                BiometricScheduler.UserSwitchClientCallback userSwitchClientCallback =
                        (BiometricScheduler.UserSwitchClientCallback) this.f$0;
                BaseClientMonitor baseClientMonitor3 = this.f$1;
                boolean z2 = this.f$2;
                userSwitchClientCallback.getClass();
                Slog.d(
                        "BiometricScheduler",
                        "[Client finished] " + baseClientMonitor3 + ", success: " + z2);
                boolean z3 = baseClientMonitor3 instanceof StopUserClient;
                BiometricScheduler biometricScheduler2 = BiometricScheduler.this;
                if (z3) {
                    if (!z2) {
                        Slog.w(
                                "BiometricScheduler",
                                "StopUserClient failed(), is the HAL stuck? Clearing"
                                    + " mStopUserClient");
                    }
                    biometricScheduler2.mStopUserClient = null;
                }
                BiometricSchedulerOperation biometricSchedulerOperation2 =
                        biometricScheduler2.mCurrentOperation;
                if (biometricSchedulerOperation2 == null
                        || biometricSchedulerOperation2.mClientMonitor
                                != userSwitchClientCallback.mOwner) {
                    Slog.w(
                            "BiometricScheduler",
                            "operation is already null or different (reset?): "
                                    + biometricScheduler2.mCurrentOperation);
                } else {
                    biometricScheduler2.mCurrentOperation = null;
                }
                biometricScheduler2.checkCurrentUserAndStartNextOperation();
                break;
        }
    }
}
