package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class Utils$Companion$sample$12 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$12(Object obj) {
        super(3, obj, Utils.Companion.class, "toQuint", "toQuint(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Quad;)Lcom/android/systemui/util/kotlin/Quint;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Quad<Object, Object, Object, Object> quad, Continuation continuation) {
        return Utils.Companion.sample$toQuint((Utils.Companion) this.receiver, obj, quad, continuation);
    }
}
