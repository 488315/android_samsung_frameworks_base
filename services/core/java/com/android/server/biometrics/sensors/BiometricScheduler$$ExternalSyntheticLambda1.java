package com.android.server.biometrics.sensors;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricScheduler$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ BiometricScheduler f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ BiometricScheduler$$ExternalSyntheticLambda1(
            BiometricScheduler biometricScheduler, long j, Consumer consumer) {
        this.f$0 = biometricScheduler;
        this.f$1 = j;
        this.f$2 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BiometricScheduler biometricScheduler = this.f$0;
        long j = this.f$1;
        Consumer consumer = this.f$2;
        BiometricSchedulerOperation biometricSchedulerOperation =
                biometricScheduler.mCurrentOperation;
        if (biometricSchedulerOperation == null
                || !biometricSchedulerOperation.isMatchingRequestId(j)) {
            consumer.accept(null);
        } else {
            consumer.accept(biometricScheduler.mCurrentOperation.mClientMonitor);
        }
    }
}
