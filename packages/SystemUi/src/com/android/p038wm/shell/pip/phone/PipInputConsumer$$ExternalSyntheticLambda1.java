package com.android.p038wm.shell.pip.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipInputConsumer$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipInputConsumer f$0;

    public /* synthetic */ PipInputConsumer$$ExternalSyntheticLambda1(PipInputConsumer pipInputConsumer, int i) {
        this.$r8$classId = i;
        this.f$0 = pipInputConsumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipInputConsumer pipInputConsumer = this.f$0;
                PipController$$ExternalSyntheticLambda9 pipController$$ExternalSyntheticLambda9 = pipInputConsumer.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda9 != null) {
                    pipController$$ExternalSyntheticLambda9.f$0.onRegistrationChanged(pipInputConsumer.mInputEventReceiver != null);
                    break;
                }
                break;
            default:
                PipController$$ExternalSyntheticLambda9 pipController$$ExternalSyntheticLambda92 = this.f$0.mRegistrationListener;
                if (pipController$$ExternalSyntheticLambda92 != null) {
                    pipController$$ExternalSyntheticLambda92.f$0.onRegistrationChanged(false);
                    break;
                }
                break;
        }
    }
}
