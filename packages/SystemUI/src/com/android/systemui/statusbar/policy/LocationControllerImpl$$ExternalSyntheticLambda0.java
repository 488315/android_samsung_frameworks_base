package com.android.systemui.statusbar.policy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                int i2 = LocationControllerImpl.$r8$clinit;
                locationControllerImpl.updateActiveLocationRequests();
                break;
        }
    }
}
