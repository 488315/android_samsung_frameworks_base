package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class Utils$Companion$sample$3 extends AdaptedFunctionReference implements Function3 {
    public static final Utils$Companion$sample$3 INSTANCE = new Utils$Companion$sample$3();

    public Utils$Companion$sample$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        return Utils.Companion.sample$lambda$3(obj, obj2, continuation);
    }
}
