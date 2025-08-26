package androidx.compose.ui.draw;

import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class ShadowGraphicsLayerElement$createBlock$1 extends Lambda implements Function1 {
    final /* synthetic */ ShadowGraphicsLayerElement this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadowGraphicsLayerElement$createBlock$1(ShadowGraphicsLayerElement shadowGraphicsLayerElement) {
        super(1);
        this.this$0 = shadowGraphicsLayerElement;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
        reusableGraphicsLayerScope.setShadowElevation(reusableGraphicsLayerScope.graphicsDensity.getDensity() * this.this$0.elevation);
        reusableGraphicsLayerScope.setShape(this.this$0.shape);
        reusableGraphicsLayerScope.setClip(this.this$0.clip);
        reusableGraphicsLayerScope.m495setAmbientShadowColor8_81llA(this.this$0.ambientColor);
        reusableGraphicsLayerScope.m497setSpotShadowColor8_81llA(this.this$0.spotColor);
        return Unit.INSTANCE;
    }
}
