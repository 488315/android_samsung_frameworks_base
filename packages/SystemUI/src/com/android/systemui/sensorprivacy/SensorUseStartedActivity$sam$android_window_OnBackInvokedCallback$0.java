package com.android.systemui.sensorprivacy;

import android.window.OnBackInvokedCallback;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SensorUseStartedActivity$sam$android_window_OnBackInvokedCallback$0 implements OnBackInvokedCallback {
    public final /* synthetic */ Function0 function;

    public SensorUseStartedActivity$sam$android_window_OnBackInvokedCallback$0(Function0 function0) {
        this.function = function0;
    }

    @Override // android.window.OnBackInvokedCallback
    public final /* synthetic */ void onBackInvoked() {
        this.function.invoke();
    }
}
