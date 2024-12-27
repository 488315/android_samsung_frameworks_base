package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class Utils$Companion$sampleFilter$2 extends AdaptedFunctionReference implements Function3 {
    public static final Utils$Companion$sampleFilter$2 INSTANCE = new Utils$Companion$sampleFilter$2();

    public Utils$Companion$sampleFilter$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object sampleFilter$lambda$0;
        sampleFilter$lambda$0 = Utils.Companion.sampleFilter$lambda$0(obj, obj2, continuation);
        return sampleFilter$lambda$0;
    }
}
