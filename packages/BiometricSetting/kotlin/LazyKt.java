package kotlin;

import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class LazyKt extends LazyKt__LazyKt {
    public static Lazy lazy(Function0 function0) {
        return new SynchronizedLazyImpl(function0);
    }
}
