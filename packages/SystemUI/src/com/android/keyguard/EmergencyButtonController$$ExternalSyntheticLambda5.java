package com.android.keyguard;

/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda5(EmergencyButtonController emergencyButtonController, boolean z, boolean z2, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
        this.f$1 = z;
        this.f$2 = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws NumberFormatException {
        switch (this.$r8$classId) {
            case 0:
                EmergencyButtonController emergencyButtonController = this.f$0;
                emergencyButtonController.mMainExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda5(emergencyButtonController, this.f$1, this.f$2, 1));
                break;
            default:
                EmergencyButtonController.$r8$lambda$W1zTNNSQ2hmvDBkhwJsvlM1sA5k(this.f$0, this.f$1, this.f$2);
                break;
        }
    }
}
