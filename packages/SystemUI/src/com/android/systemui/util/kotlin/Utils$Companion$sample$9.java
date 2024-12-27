package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class Utils$Companion$sample$9 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$9(Object obj) {
        super(3, obj, Utils.Companion.class, "toQuint", "toQuint(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Quad;)Lcom/android/systemui/util/kotlin/Quint;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Quad<Object, Object, Object, Object> quad, Continuation continuation) {
        Object sample$toQuint;
        sample$toQuint = Utils.Companion.sample$toQuint((Utils.Companion) this.receiver, obj, quad, continuation);
        return sample$toQuint;
    }
}
