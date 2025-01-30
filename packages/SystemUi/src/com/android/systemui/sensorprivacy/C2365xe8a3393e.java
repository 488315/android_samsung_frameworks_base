package com.android.systemui.sensorprivacy;

import android.window.OnBackInvokedCallback;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.sensorprivacy.SensorUseStartedActivity$sam$android_window_OnBackInvokedCallback$0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2365xe8a3393e implements OnBackInvokedCallback {
    public final /* synthetic */ Function0 function;

    public C2365xe8a3393e(Function0 function0) {
        this.function = function0;
    }

    @Override // android.window.OnBackInvokedCallback
    public final /* synthetic */ void onBackInvoked() {
        this.function.invoke();
    }
}
