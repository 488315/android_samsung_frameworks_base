package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.StatusBarIconController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatusBarIconControllerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ StatusBarIconControllerImpl$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((StatusBarIconController.IconManager) obj).onRemoveIcon(this.f$0);
                break;
            case 1:
                ((StatusBarIconController.IconManager) obj).onRemoveIcon(this.f$0);
                break;
            default:
                ((StatusBarIconController.IconManager) obj).onRemoveIcon(this.f$0);
                break;
        }
    }
}
