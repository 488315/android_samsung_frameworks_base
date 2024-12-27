package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class Utils$Companion$sample$2 extends AdaptedFunctionReference implements Function3 {
    public static final Utils$Companion$sample$2 INSTANCE = new Utils$Companion$sample$2();

    public Utils$Companion$sample$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object sample$lambda$3;
        sample$lambda$3 = Utils.Companion.sample$lambda$3(obj, obj2, continuation);
        return sample$lambda$3;
    }
}
