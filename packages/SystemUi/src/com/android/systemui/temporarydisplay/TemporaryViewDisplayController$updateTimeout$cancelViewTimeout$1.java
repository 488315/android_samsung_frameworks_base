package com.android.systemui.temporarydisplay;

import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
