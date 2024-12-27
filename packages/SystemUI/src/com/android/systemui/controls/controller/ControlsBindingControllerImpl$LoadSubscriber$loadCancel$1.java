package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

public final class ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 implements Runnable {
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = loadSubscriber;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ?? r0 = this.this$0._loadCancelInternal;
        if (r0 != 0) {
            Log.d("ControlsBindingControllerImpl", "Canceling loadSubscribtion");
            r0.invoke();
        }
        this.this$0.callback.error("Load cancelled");
    }
}
