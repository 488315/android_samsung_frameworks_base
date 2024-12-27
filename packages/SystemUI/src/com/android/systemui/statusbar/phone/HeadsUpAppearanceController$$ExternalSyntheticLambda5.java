package com.android.systemui.statusbar.phone;

public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda5(HeadsUpAppearanceController headsUpAppearanceController, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpAppearanceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
        switch (i) {
            case 0:
                headsUpAppearanceController.updateParentClipping(true);
                break;
            default:
                headsUpAppearanceController.updateIsolatedIconLocation(true);
                break;
        }
    }
}
