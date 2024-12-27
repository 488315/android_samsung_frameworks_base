package com.android.systemui.statusbar;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecIndicationController$$ExternalSyntheticLambda0 implements Action, Consumer {
    public final /* synthetic */ KeyguardSecIndicationController f$0;

    @Override // io.reactivex.functions.Consumer
    public void accept(Object obj) {
        this.f$0.removeIndication((IndicationEventType) obj);
    }

    @Override // io.reactivex.functions.Action
    public void run() {
        KeyguardSecIndicationController keyguardSecIndicationController = this.f$0;
        keyguardSecIndicationController.getClass();
        keyguardSecIndicationController.addIndication(IndicationEventType.UNLOCK_GUIDE, keyguardSecIndicationController.getUnlockGuideText());
    }
}
