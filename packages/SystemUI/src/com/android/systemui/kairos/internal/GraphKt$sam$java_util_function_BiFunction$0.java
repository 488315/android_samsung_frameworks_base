package com.android.systemui.kairos.internal;

import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class GraphKt$sam$java_util_function_BiFunction$0 implements BiFunction {
    public final /* synthetic */ Function2 function;

    public GraphKt$sam$java_util_function_BiFunction$0(Function2 function2) {
        this.function = function2;
    }

    @Override // java.util.function.BiFunction
    public final /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.function.invoke(obj, obj2);
    }
}
