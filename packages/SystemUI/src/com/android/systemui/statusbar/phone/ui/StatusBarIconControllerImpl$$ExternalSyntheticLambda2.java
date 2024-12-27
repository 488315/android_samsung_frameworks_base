package com.android.systemui.statusbar.phone.ui;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class StatusBarIconControllerImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ StatusBarIconControllerImpl$$ExternalSyntheticLambda2(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        IconManager iconManager = (IconManager) obj;
        switch (i) {
            case 0:
                iconManager.onRemoveIcon(i2);
                break;
            case 1:
                iconManager.onRemoveIcon(i2);
                break;
            default:
                iconManager.onRemoveIcon(i2);
                break;
        }
    }
}
