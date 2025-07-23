package androidx.compose.animation.graphics.res;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.graphics.vector.VectorGroup;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$AnimatedVectorPainterResources_androidKt {
    public static final ComposableSingletons$AnimatedVectorPainterResources_androidKt INSTANCE = new ComposableSingletons$AnimatedVectorPainterResources_androidKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f0lambda1 = new ComposableLambdaImpl(-869223072, false, new Function4() { // from class: androidx.compose.animation.graphics.res.ComposableSingletons$AnimatedVectorPainterResources_androidKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            VectorGroup vectorGroup = (VectorGroup) obj;
            Map map = (Map) obj2;
            Composer composer = (Composer) obj3;
            int intValue = ((Number) obj4).intValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.graphics.res.ComposableSingletons$AnimatedVectorPainterResources_androidKt.lambda-1.<anonymous> (AnimatedVectorPainterResources.android.kt:46)");
            }
            VectorPainterKt.RenderVectorGroup(vectorGroup, map, composer, intValue & 126, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
