package com.android.wm.shell.pip.phone;

import com.android.wm.shell.pip.phone.PipController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda6(boolean z, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = z;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                boolean z = this.f$0;
                int i = this.f$1;
                int i2 = PipController.IPipImpl.$r8$clinit;
                int i3 = PipController.$r8$clinit;
                ((PipController) obj).setLauncherKeepClearAreaHeight(i, z);
                break;
            default:
                boolean z2 = this.f$0;
                int i4 = this.f$1;
                int i5 = PipController.IPipImpl.$r8$clinit;
                int i6 = PipController.$r8$clinit;
                ((PipController) obj).setLauncherKeepClearAreaHeight(i4, z2);
                break;
        }
    }
}
