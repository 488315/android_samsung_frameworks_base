package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class Utils$Companion$sample$8 extends AdaptedFunctionReference implements Function5 {
    public static final Utils$Companion$sample$8 INSTANCE = new Utils$Companion$sample$8();

    public Utils$Companion$sample$8() {
        super(5, Quad.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
        Object sample$lambda$5;
        sample$lambda$5 = Utils.Companion.sample$lambda$5(obj, obj2, obj3, obj4, continuation);
        return sample$lambda$5;
    }
}
