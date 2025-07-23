package com.android.systemui.temporarydisplay;

import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
