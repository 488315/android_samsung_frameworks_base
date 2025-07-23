package androidx.compose.ui.layout;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$SubcomposeLayoutKt {
    public static final ComposableSingletons$SubcomposeLayoutKt INSTANCE = new ComposableSingletons$SubcomposeLayoutKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f19lambda1 = new ComposableLambdaImpl(-1741544742, false, new Function2() { // from class: androidx.compose.ui.layout.ComposableSingletons$SubcomposeLayoutKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            int intValue = ((Number) obj2).intValue();
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.ui.layout.ComposableSingletons$SubcomposeLayoutKt.lambda-1.<anonymous> (SubcomposeLayout.kt:509)");
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
