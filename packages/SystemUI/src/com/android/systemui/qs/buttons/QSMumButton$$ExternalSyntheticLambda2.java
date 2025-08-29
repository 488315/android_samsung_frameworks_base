package com.android.systemui.qs.buttons;

import com.android.systemui.qs.buttons.QSMumButton;

/* compiled from: qb/100414692 4322fc59b788332aad98beead1998ffd9b673ada786966d91cea3cb6d4467f0e */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSMumButton$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSMumButton$$ExternalSyntheticLambda2(Object obj, int i) {
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
