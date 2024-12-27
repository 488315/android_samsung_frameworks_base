package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class JavaAdapterKt$combineFlows$2 extends FunctionReferenceImpl implements Function4 {
    public JavaAdapterKt$combineFlows$2(Object obj) {
        super(4, obj, Intrinsics.Kotlin.class, "suspendConversion0", "combineFlows$suspendConversion0$0(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Continuation continuation) {
        Object combineFlows$suspendConversion0$0;
        combineFlows$suspendConversion0$0 = JavaAdapterKt.combineFlows$suspendConversion0$0((Function3) this.receiver, obj, obj2, obj3, continuation);
        return combineFlows$suspendConversion0$0;
    }
}
