package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class Utils$Companion$sample$15 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$15(Object obj) {
        super(3, obj, Utils.Companion.class, "toSeptuple", "toSeptuple(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Sextuple;)Lcom/android/systemui/util/kotlin/Septuple;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Sextuple<Object, Object, Object, Object, Object, Object> sextuple, Continuation continuation) {
        Object sample$toSeptuple;
        sample$toSeptuple = Utils.Companion.sample$toSeptuple((Utils.Companion) this.receiver, obj, sextuple, continuation);
        return sample$toSeptuple;
    }
}
