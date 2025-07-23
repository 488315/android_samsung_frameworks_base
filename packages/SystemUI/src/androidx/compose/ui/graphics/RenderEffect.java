package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RenderEffect {
    public android.graphics.RenderEffect internalRenderEffect;

    public /* synthetic */ RenderEffect(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final android.graphics.RenderEffect asAndroidRenderEffect() {
        android.graphics.RenderEffect renderEffect = this.internalRenderEffect;
        if (renderEffect != null) {
            return renderEffect;
        }
        android.graphics.RenderEffect createRenderEffect = createRenderEffect();
        this.internalRenderEffect = createRenderEffect;
        return createRenderEffect;
    }

    public abstract android.graphics.RenderEffect createRenderEffect();

    private RenderEffect() {
    }
}
