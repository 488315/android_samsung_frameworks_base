package com.android.systemui.statusbar.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class LocationControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationControllerImpl f$0;

    public /* synthetic */ LocationControllerImpl$$ExternalSyntheticLambda1(LocationControllerImpl locationControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = locationControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateActiveLocationRequests();
                break;
            default:
                this.f$0.areActiveLocationRequests();
                break;
        }
    }
}
