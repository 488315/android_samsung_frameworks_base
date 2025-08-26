package androidx.compose.ui.graphics.layer;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class GraphicsLayerImpl$Companion$DefaultDrawBlock$1 extends Lambda implements Function1 {
    public static final GraphicsLayerImpl$Companion$DefaultDrawBlock$1 INSTANCE = new GraphicsLayerImpl$Companion$DefaultDrawBlock$1();

    public GraphicsLayerImpl$Companion$DefaultDrawBlock$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Color.Companion.getClass();
        DrawScope.m541drawRectnJ9OG0$default((DrawScope) obj, Color.Transparent, 0L, 0L, 0.0f, null, null, 0, 126);
        return Unit.INSTANCE;
    }
}
