package com.android.systemui.kairos;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
final /* synthetic */ class BuildScope$scanToState$1 extends FunctionReferenceImpl implements Function3 {
    public BuildScope$scanToState$1(Object obj) {
        super(3, obj, Intrinsics.Kotlin.class, "suspendConversion0", "scanToState$suspendConversion0(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return ((Function2) this.receiver).invoke(obj, obj2);
    }
}
