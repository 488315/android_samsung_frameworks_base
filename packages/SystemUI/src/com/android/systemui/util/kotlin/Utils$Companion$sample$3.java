package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class Utils$Companion$sample$3 extends AdaptedFunctionReference implements Function3 {
    public Utils$Companion$sample$3(Object obj) {
        super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Pair<Object, Object> pair, Continuation continuation) {
        Object sample$toTriple;
        sample$toTriple = Utils.Companion.sample$toTriple((Utils.Companion) this.receiver, obj, pair, continuation);
        return sample$toTriple;
    }
}
