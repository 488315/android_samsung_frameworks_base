package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Triple;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class Utils$Companion$sample$5 extends AdaptedFunctionReference implements Function4 {
    public static final Utils$Companion$sample$5 INSTANCE = new Utils$Companion$sample$5();

    public Utils$Companion$sample$5() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Continuation continuation) {
        Object sample$lambda$4;
        sample$lambda$4 = Utils.Companion.sample$lambda$4(obj, obj2, obj3, continuation);
        return sample$lambda$4;
    }
}
