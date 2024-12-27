package com.android.server.broadcastradio.hal1;

import android.hardware.radio.RadioManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class TunerCallback$$ExternalSyntheticLambda5
        implements TunerCallback.RunnableThrowingRemoteException {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ TunerCallback f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TunerCallback$$ExternalSyntheticLambda5(
            TunerCallback tunerCallback, RadioManager.BandConfig bandConfig) {
        this.f$0 = tunerCallback;
        this.f$1 = bandConfig;
    }

    public /* synthetic */ TunerCallback$$ExternalSyntheticLambda5(
            TunerCallback tunerCallback, RadioManager.ProgramInfo programInfo) {
        this.f$0 = tunerCallback;
        this.f$1 = programInfo;
    }

    @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onConfigurationChanged$1((RadioManager.BandConfig) this.f$1);
                break;
            default:
                this.f$0.lambda$onCurrentProgramInfoChanged$2((RadioManager.ProgramInfo) this.f$1);
                break;
        }
    }
}
