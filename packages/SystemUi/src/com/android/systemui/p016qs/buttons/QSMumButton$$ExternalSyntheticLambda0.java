package com.android.systemui.p016qs.buttons;

import com.android.systemui.p016qs.buttons.QSMumButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSMumButton$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSMumButton$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((QSMumButton) this.f$0).mMumAndDexHelper.updateMumSwitchVisibility();
                break;
            default:
                ((QSMumButton.MumAndDexHelper.C21201) this.f$0).this$1.updateMumSwitchVisibility();
                break;
        }
    }
}
