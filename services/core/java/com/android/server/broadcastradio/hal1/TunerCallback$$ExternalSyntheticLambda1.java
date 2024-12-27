package com.android.server.broadcastradio.hal1;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TunerCallback$$ExternalSyntheticLambda1
        implements TunerCallback.RunnableThrowingRemoteException {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TunerCallback f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ TunerCallback$$ExternalSyntheticLambda1(
            TunerCallback tunerCallback, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = tunerCallback;
        this.f$1 = z;
    }

    @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onBackgroundScanAvailabilityChange$6(this.f$1);
                break;
            case 1:
                this.f$0.lambda$onEmergencyAnnouncement$4(this.f$1);
                break;
            case 2:
                this.f$0.lambda$onAntennaState$5(this.f$1);
                break;
            default:
                this.f$0.lambda$onTrafficAnnouncement$3(this.f$1);
                break;
        }
    }
}
