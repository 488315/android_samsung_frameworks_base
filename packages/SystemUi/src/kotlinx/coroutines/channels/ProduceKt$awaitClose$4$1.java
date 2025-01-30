package kotlinx.coroutines.channels;

import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
final class ProduceKt$awaitClose$4$1 extends Lambda implements Function1 {
    final /* synthetic */ CancellableContinuation $cont;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProduceKt$awaitClose$4$1(CancellableContinuation cancellableContinuation) {
        super(1);
        this.$cont = cancellableContinuation;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        CancellableContinuation cancellableContinuation = this.$cont;
        int i = Result.$r8$clinit;
        Unit unit = Unit.INSTANCE;
        ((CancellableContinuationImpl) cancellableContinuation).resumeWith(unit);
        return unit;
    }
}
