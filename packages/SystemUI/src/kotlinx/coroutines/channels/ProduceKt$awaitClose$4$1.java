package kotlinx.coroutines.channels;

import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ProduceKt$awaitClose$4$1 implements Function1 {
    public final /* synthetic */ CancellableContinuation $cont;

    public ProduceKt$awaitClose$4$1(CancellableContinuation cancellableContinuation) {
        this.$cont = cancellableContinuation;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = Result.$r8$clinit;
        Unit unit = Unit.INSTANCE;
        this.$cont.resumeWith(unit);
        return unit;
    }
}
