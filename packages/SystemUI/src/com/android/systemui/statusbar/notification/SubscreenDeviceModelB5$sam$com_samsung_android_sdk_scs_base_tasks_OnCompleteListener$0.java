package com.android.systemui.statusbar.notification;

import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubscreenDeviceModelB5$sam$com_samsung_android_sdk_scs_base_tasks_OnCompleteListener$0 implements OnCompleteListener {
    public final /* synthetic */ Function1 function;

    public SubscreenDeviceModelB5$sam$com_samsung_android_sdk_scs_base_tasks_OnCompleteListener$0(Function1 function1) {
        this.function = function1;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
    public final /* synthetic */ void onComplete(Task task) {
        this.function.mo779invoke(task);
    }
}
