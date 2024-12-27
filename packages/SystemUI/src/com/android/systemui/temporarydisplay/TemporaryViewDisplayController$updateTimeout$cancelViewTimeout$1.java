package com.android.systemui.temporarydisplay;

import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TemporaryViewDisplayController$updateTimeout$cancelViewTimeout$1 implements Runnable {
    public final /* synthetic */ TemporaryViewDisplayController.DisplayInfo $displayInfo;
    public final /* synthetic */ TemporaryViewDisplayController this$0;

    public TemporaryViewDisplayController$updateTimeout$cancelViewTimeout$1(TemporaryViewDisplayController temporaryViewDisplayController, TemporaryViewDisplayController.DisplayInfo displayInfo) {
        this.this$0 = temporaryViewDisplayController;
        this.$displayInfo = displayInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.removeView(this.$displayInfo.info.getId(), "TIMEOUT");
    }
}
