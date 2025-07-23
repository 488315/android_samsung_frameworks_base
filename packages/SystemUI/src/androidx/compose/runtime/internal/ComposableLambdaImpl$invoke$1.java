package androidx.compose.runtime.internal;

import androidx.compose.runtime.Composer;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class ComposableLambdaImpl$invoke$1 extends AdaptedFunctionReference implements Function2 {
    public ComposableLambdaImpl$invoke$1(Object obj) {
        super(2, obj, ComposableLambdaImpl.class, "invoke", "invoke(Landroidx/compose/runtime/Composer;I)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int intValue = ((Number) obj2).intValue();
        ((ComposableLambdaImpl) this.receiver).invoke(intValue, (Composer) obj);
        return Unit.INSTANCE;
    }
}
