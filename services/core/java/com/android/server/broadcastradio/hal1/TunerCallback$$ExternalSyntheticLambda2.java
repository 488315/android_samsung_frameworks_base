package com.android.server.broadcastradio.hal1;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TunerCallback$$ExternalSyntheticLambda2
        implements TunerCallback.RunnableThrowingRemoteException {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TunerCallback f$0;

    public /* synthetic */ TunerCallback$$ExternalSyntheticLambda2(
            TunerCallback tunerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = tunerCallback;
    }

    @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
    public final void run() {
        int i = this.$r8$classId;
        TunerCallback tunerCallback = this.f$0;
        switch (i) {
            case 0:
                tunerCallback.lambda$onProgramListChanged$8();
                break;
            default:
                tunerCallback.lambda$onBackgroundScanComplete$7();
                break;
        }
    }
}
