package com.android.systemui.statusbar;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationController$$ExternalSyntheticLambda0 implements Consumer, Action {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecIndicationController f$0;

    public /* synthetic */ KeyguardSecIndicationController$$ExternalSyntheticLambda0(KeyguardSecIndicationController keyguardSecIndicationController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecIndicationController;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.f$0.removeIndication((IndicationEventType) obj);
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        int i = this.$r8$classId;
        KeyguardSecIndicationController keyguardSecIndicationController = this.f$0;
        switch (i) {
            case 1:
                keyguardSecIndicationController.getClass();
                keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
                break;
            default:
                keyguardSecIndicationController.setVisible(true);
                break;
        }
    }
}
