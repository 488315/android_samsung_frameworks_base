package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class JavaAdapterKt$combineFlows$3 extends FunctionReferenceImpl implements Function5 {
    public JavaAdapterKt$combineFlows$3(Object obj) {
        super(5, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0$1(Lkotlin/jvm/functions/Function4;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
        Object combineFlows$suspendConversion0$1;
        combineFlows$suspendConversion0$1 = JavaAdapterKt.combineFlows$suspendConversion0$1((Function4) this.receiver, obj, obj2, obj3, obj4, continuation);
        return combineFlows$suspendConversion0$1;
    }
}
