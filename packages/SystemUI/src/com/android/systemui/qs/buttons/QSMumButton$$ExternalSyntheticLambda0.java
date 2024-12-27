package com.android.systemui.qs.buttons;

import com.android.systemui.qs.buttons.QSMumButton;

public final /* synthetic */ class QSMumButton$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSMumButton$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((QSMumButton.MumAndDexHelper) obj).updateMumSwitchVisibility();
                break;
            default:
                ((QSMumButton.MumAndDexHelper.AnonymousClass1) obj).this$1.updateMumSwitchVisibility();
                break;
        }
    }
}
