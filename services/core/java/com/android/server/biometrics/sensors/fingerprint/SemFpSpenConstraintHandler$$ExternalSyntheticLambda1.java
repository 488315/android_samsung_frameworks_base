package com.android.server.biometrics.sensors.fingerprint;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemFpSpenConstraintHandler$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpSpenConstraintHandler f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SemFpSpenConstraintHandler$$ExternalSyntheticLambda1(
            SemFpSpenConstraintHandler semFpSpenConstraintHandler, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = semFpSpenConstraintHandler;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.notifyTspBlockStatusToHal(this.f$1);
                break;
            default:
                this.f$0.notifyTspBlockStatusToHal(this.f$1);
                break;
        }
    }
}
