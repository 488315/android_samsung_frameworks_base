package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Triple;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class Utils$Companion$sample$8 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$8(Object obj) {
        super(3, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Lkotlin/Triple;)Lcom/android/systemui/util/kotlin/Quad;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Triple<Object, Object, Object> triple, Continuation continuation) {
        Object sample$toQuad;
        sample$toQuad = Utils.Companion.sample$toQuad((Utils.Companion) this.receiver, obj, triple, continuation);
        return sample$toQuad;
    }
}
