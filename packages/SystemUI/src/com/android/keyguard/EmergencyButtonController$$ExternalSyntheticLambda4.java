package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda4(EmergencyButtonController emergencyButtonController, boolean z, boolean z2, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
        this.f$1 = z;
        this.f$2 = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EmergencyButtonController emergencyButtonController = this.f$0;
                emergencyButtonController.mMainExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda4(emergencyButtonController, this.f$1, this.f$2, 1));
                break;
            default:
                EmergencyButtonController.m829$r8$lambda$xQ0QlP59a82QvETJ9i9UaMk1vM(this.f$0, this.f$1, this.f$2);
                break;
        }
    }
}
