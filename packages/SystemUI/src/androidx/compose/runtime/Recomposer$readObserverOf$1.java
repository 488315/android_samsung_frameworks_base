package androidx.compose.runtime;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class Recomposer$readObserverOf$1 extends Lambda implements Function1 {
    final /* synthetic */ ControlledComposition $composition;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Recomposer$readObserverOf$1(ControlledComposition controlledComposition) {
        super(1);
        this.$composition = controlledComposition;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((CompositionImpl) this.$composition).recordReadOf(obj);
        return Unit.INSTANCE;
    }
}
