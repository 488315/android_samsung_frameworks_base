package com.android.systemui.wallpaper.accessory;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            int i = SmartCardController.$r8$clinit;
            this.this$0.updateState(booleanValue);
        }
    }
}
