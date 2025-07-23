package androidx.compose.runtime;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MovableContentKt {
    public static final ComposableLambdaImpl movableContentOf(ComposableLambdaImpl composableLambdaImpl) {
        final MovableContent movableContent = new MovableContent(composableLambdaImpl);
        return new ComposableLambdaImpl(-434707029, true, new Function3() { // from class: androidx.compose.runtime.MovableContentKt$movableContentOf$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Composer composer = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if ((intValue & 6) == 0) {
                    intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer).changed(obj) : ((ComposerImpl) composer).changedInstance(obj) ? 4 : 2;
                }
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.shouldExecute(intValue & 1, (intValue & 19) != 18)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.runtime.movableContentOf.<anonymous> (MovableContent.kt:59)");
                    }
                    composerImpl.invokeMovableContentLambda(movableContent, composerImpl.currentCompositionLocalScope(), obj, false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
