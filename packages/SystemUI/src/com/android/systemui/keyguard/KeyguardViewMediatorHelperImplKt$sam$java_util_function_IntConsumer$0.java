package com.android.systemui.keyguard;

import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0 implements IntConsumer {
    public final /* synthetic */ Function1 function;

    public KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.IntConsumer
    public final /* synthetic */ void accept(int i) {
        this.function.mo779invoke(Integer.valueOf(i));
    }
}
