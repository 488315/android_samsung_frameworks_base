package androidx.compose.foundation.text;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$CoreTextFieldKt {
    public static final ComposableSingletons$CoreTextFieldKt INSTANCE = new ComposableSingletons$CoreTextFieldKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f2lambda1 = new ComposableLambdaImpl(-813639903, false, new Function3() { // from class: androidx.compose.foundation.text.ComposableSingletons$CoreTextFieldKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Function2 function2 = (Function2) obj;
            Composer composer = (Composer) obj2;
            int intValue = ((Number) obj3).intValue();
            if ((intValue & 6) == 0) {
                intValue |= ((ComposerImpl) composer).changedInstance(function2) ? 4 : 2;
            }
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.shouldExecute(intValue & 1, (intValue & 19) != 18)) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.text.ComposableSingletons$CoreTextFieldKt.lambda-1.<anonymous> (CoreTextField.kt:203)");
                }
                function2.invoke(composerImpl, Integer.valueOf(intValue & 14));
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
