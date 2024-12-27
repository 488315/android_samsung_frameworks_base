package com.android.systemui.statusbar.notification.collection.coordinator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class VisualStabilityCoordinator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VisualStabilityCoordinator f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ VisualStabilityCoordinator$$ExternalSyntheticLambda0(VisualStabilityCoordinator visualStabilityCoordinator, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = visualStabilityCoordinator;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$temporarilyAllowSectionChanges$1(this.f$1);
                break;
            default:
                this.f$0.lambda$temporarilyAllowSectionChanges$0(this.f$1);
                break;
        }
    }
}
