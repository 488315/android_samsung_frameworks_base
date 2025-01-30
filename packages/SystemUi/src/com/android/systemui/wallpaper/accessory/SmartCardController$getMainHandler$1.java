package com.android.systemui.wallpaper.accessory;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartCardController$getMainHandler$1 extends Handler {
    public final /* synthetic */ SmartCardController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartCardController$getMainHandler$1(SmartCardController smartCardController, Looper looper) {
        super(looper);
        this.this$0 = smartCardController;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what == 20230526) {
            SmartCardController smartCardController = this.this$0;
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            int i = SmartCardController.$r8$clinit;
            smartCardController.updateState(booleanValue);
        }
    }
}
