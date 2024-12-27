package com.android.systemui.keyguard;

import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0 implements IntConsumer {
    public final /* synthetic */ Function1 function;

    public KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.IntConsumer
    public final /* synthetic */ void accept(int i) {
        this.function.invoke(Integer.valueOf(i));
    }
}
