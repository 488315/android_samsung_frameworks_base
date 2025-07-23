package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 implements Runnable {
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = loadSubscriber;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Function0 function0 = this.this$0._loadCancelInternal;
        if (function0 != null) {
            Log.d("ControlsBindingControllerImpl", "Canceling loadSubscribtion");
            function0.invoke();
        }
        this.this$0.callback.error("Load cancelled");
    }
}
