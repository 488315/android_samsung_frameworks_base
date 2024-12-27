package com.android.server.broadcastradio.hal1;


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
