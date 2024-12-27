package com.android.systemui.keyguard;

import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
