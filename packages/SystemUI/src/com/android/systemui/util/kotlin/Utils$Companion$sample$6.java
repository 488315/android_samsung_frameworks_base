package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Triple;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class Utils$Companion$sample$6 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$6(Object obj) {
        super(3, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Lkotlin/Triple;)Lcom/android/systemui/util/kotlin/Quad;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Triple<Object, Object, Object> triple, Continuation continuation) {
        Object sample$toQuad;
        sample$toQuad = Utils.Companion.sample$toQuad((Utils.Companion) this.receiver, obj, triple, continuation);
        return sample$toQuad;
    }
}
