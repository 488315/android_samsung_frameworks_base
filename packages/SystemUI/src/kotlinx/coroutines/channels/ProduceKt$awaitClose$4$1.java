package kotlinx.coroutines.channels;

import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;

/* loaded from: classes4.dex */
public final class ProduceKt$awaitClose$4$1 implements Function1 {
    public final /* synthetic */ CancellableContinuation $cont;

    public ProduceKt$awaitClose$4$1(CancellableContinuation cancellableContinuation) {
        this.$cont = cancellableContinuation;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = Result.$r8$clinit;
        Unit unit = Unit.INSTANCE;
        this.$cont.resumeWith(unit);
        return unit;
    }
}
