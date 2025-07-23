package com.android.wm.shell.pip.phone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipInputConsumer$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipInputConsumer f$0;

    public /* synthetic */ PipInputConsumer$$ExternalSyntheticLambda1(PipInputConsumer pipInputConsumer, int i) {
        this.$r8$classId = i;
        this.f$0 = pipInputConsumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PipInputConsumer pipInputConsumer = this.f$0;
        switch (i) {
            case 0:
                PipController$$ExternalSyntheticLambda14 pipController$$ExternalSyntheticLambda14 = pipInputConsumer.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda14 != null) {
                    pipController$$ExternalSyntheticLambda14.f$0.onRegistrationChanged(pipInputConsumer.mInputEventReceiver != null);
                    break;
                }
                break;
            default:
                PipController$$ExternalSyntheticLambda14 pipController$$ExternalSyntheticLambda142 = pipInputConsumer.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda142 != null) {
                    pipController$$ExternalSyntheticLambda142.f$0.onRegistrationChanged(false);
                    break;
                }
                break;
        }
    }
}
