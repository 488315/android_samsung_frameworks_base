package androidx.compose.runtime;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$CompositionKt {
    public static final ComposableSingletons$CompositionKt INSTANCE = new ComposableSingletons$CompositionKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f17lambda1 = new ComposableLambdaImpl(954879418, false, new Function2() { // from class: androidx.compose.runtime.ComposableSingletons$CompositionKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            int intValue = ((Number) obj2).intValue();
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.runtime.ComposableSingletons$CompositionKt.lambda-1.<anonymous> (Composition.kt:601)");
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f18lambda2 = new ComposableLambdaImpl(1918065384, false, new Function2() { // from class: androidx.compose.runtime.ComposableSingletons$CompositionKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            int intValue = ((Number) obj2).intValue();
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.runtime.ComposableSingletons$CompositionKt.lambda-2.<anonymous> (Composition.kt:816)");
                }
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
