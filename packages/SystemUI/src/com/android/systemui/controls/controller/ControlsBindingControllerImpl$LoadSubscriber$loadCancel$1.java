package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 implements Runnable {
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = loadSubscriber;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
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
