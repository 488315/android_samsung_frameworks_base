package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MonotonicFrameClockKt$withFrameMillis$2 extends Lambda implements Function1 {
    final /* synthetic */ Function1 $onFrame;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MonotonicFrameClockKt$withFrameMillis$2(Function1 function1) {
        super(1);
        this.$onFrame = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return this.$onFrame.mo779invoke(Long.valueOf(((Number) obj).longValue() / 1000000));
    }
}
