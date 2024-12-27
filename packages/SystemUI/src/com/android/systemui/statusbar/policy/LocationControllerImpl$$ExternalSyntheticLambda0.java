package com.android.systemui.statusbar.policy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class LocationControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationControllerImpl f$0;

    public /* synthetic */ LocationControllerImpl$$ExternalSyntheticLambda0(LocationControllerImpl locationControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = locationControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        LocationControllerImpl locationControllerImpl = this.f$0;
        switch (i) {
            case 0:
                locationControllerImpl.areActiveLocationRequests();
                break;
            default:
                locationControllerImpl.updateActiveLocationRequests();
                break;
        }
    }
}
