package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda3(HeadsUpAppearanceController headsUpAppearanceController, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpAppearanceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
        switch (i) {
            case 0:
                SourceType$Companion$from$1 sourceType$Companion$from$1 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.updateParentClipping(true);
                break;
            default:
                SourceType$Companion$from$1 sourceType$Companion$from$12 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.updateIsolatedIconLocation(true);
                break;
        }
    }
}
