package androidx.compose.ui.graphics.layer;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GraphicsLayerImpl$Companion$DefaultDrawBlock$1 extends Lambda implements Function1 {
    public static final GraphicsLayerImpl$Companion$DefaultDrawBlock$1 INSTANCE = new GraphicsLayerImpl$Companion$DefaultDrawBlock$1();

    public GraphicsLayerImpl$Companion$DefaultDrawBlock$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Color.Companion.getClass();
        DrawScope.m539drawRectnJ9OG0$default((DrawScope) obj, Color.Transparent, 0L, 0L, 0.0f, null, null, 0, 126);
        return Unit.INSTANCE;
    }
}
