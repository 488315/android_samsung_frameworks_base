package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class Utils$Companion$sample$20 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$20(Object obj) {
        super(3, obj, Utils.Companion.class, "toSeptuple", "toSeptuple(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Sextuple;)Lcom/android/systemui/util/kotlin/Septuple;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Sextuple<Object, Object, Object, Object, Object, Object> sextuple, Continuation continuation) {
        Object sample$toSeptuple;
        sample$toSeptuple = Utils.Companion.sample$toSeptuple((Utils.Companion) this.receiver, obj, sextuple, continuation);
        return sample$toSeptuple;
    }
}
