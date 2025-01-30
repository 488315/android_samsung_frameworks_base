package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 implements Runnable {
    public final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$0;

    public ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        this.this$0 = loadSubscriber;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // java.lang.Runnable
    public final void run() {
        ?? r2 = this.this$0._loadCancelInternal;
        if (r2 != 0) {
            Log.d("ControlsBindingControllerImpl", "Canceling loadSubscribtion");
            r2.invoke();
        }
    }
}
