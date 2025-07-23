package kotlinx.coroutines.flow.internal;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SafeCollectorKt {
    public static final Function3 emitFun;

    static {
        SafeCollectorKt$emitFun$1 safeCollectorKt$emitFun$1 = SafeCollectorKt$emitFun$1.INSTANCE;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, safeCollectorKt$emitFun$1);
        emitFun = safeCollectorKt$emitFun$1;
    }
}
