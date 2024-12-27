package com.android.systemui.sensorprivacy;

import android.window.OnBackInvokedCallback;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
