package androidx.compose.ui.graphics;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.platform.CompositionLocalsKt;

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
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = new GraphicsContextObserver(graphicsContext);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        GraphicsLayer graphicsLayer = ((GraphicsContextObserver) objRememberedValue).graphicsLayer;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return graphicsLayer;
    }
}
