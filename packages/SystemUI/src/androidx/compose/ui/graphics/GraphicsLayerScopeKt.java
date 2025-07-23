package androidx.compose.ui.graphics;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.platform.CompositionLocalsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class GraphicsLayerScopeKt {
    public static final long DefaultShadowColor;

    static {
        Color.Companion.getClass();
        DefaultShadowColor = Color.Black;
    }

    public static final GraphicsLayer rememberGraphicsLayer(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.graphics.rememberGraphicsLayer (GraphicsLayerScope.kt:249)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        GraphicsContext graphicsContext = (GraphicsContext) composerImpl.consume(CompositionLocalsKt.LocalGraphicsContext);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new GraphicsContextObserver(graphicsContext);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        GraphicsLayer graphicsLayer = ((GraphicsContextObserver) rememberedValue).graphicsLayer;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return graphicsLayer;
    }
}
