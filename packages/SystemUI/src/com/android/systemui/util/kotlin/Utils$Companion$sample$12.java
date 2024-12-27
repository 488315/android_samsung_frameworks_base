package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class Utils$Companion$sample$12 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$12(Object obj) {
        super(3, obj, Utils.Companion.class, "toSextuple", "toSextuple(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Quint;)Lcom/android/systemui/util/kotlin/Sextuple;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Quint<Object, Object, Object, Object, Object> quint, Continuation continuation) {
        Object sample$toSextuple;
        sample$toSextuple = Utils.Companion.sample$toSextuple((Utils.Companion) this.receiver, obj, quint, continuation);
        return sample$toSextuple;
    }
}
