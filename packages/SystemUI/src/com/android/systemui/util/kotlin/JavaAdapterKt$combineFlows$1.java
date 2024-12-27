package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

final /* synthetic */ class JavaAdapterKt$combineFlows$1 extends FunctionReferenceImpl implements Function3 {
    public JavaAdapterKt$combineFlows$1(Object obj) {
        super(3, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object combineFlows$suspendConversion0;
        combineFlows$suspendConversion0 = JavaAdapterKt.combineFlows$suspendConversion0((Function2) this.receiver, obj, obj2, continuation);
        return combineFlows$suspendConversion0;
    }
}
