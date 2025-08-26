package android.media.effect;

import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.format.ImageFormat;

/* loaded from: classes3.dex */
public abstract class FilterEffect extends Effect {
    protected EffectContext mEffectContext;
    private String mName;

    protected FilterEffect(EffectContext effectContext, String str) {
        this.mEffectContext = effectContext;
        this.mName = str;
    }

    @Override // android.media.effect.Effect
    public String getName() {
        return this.mName;
    }

    protected void beginGLEffect() {
        this.mEffectContext.assertValidGLState();
        this.mEffectContext.saveGLState();
    }

    protected void endGLEffect() {
        this.mEffectContext.restoreGLState();
    }

    protected FilterContext getFilterContext() {
        return this.mEffectContext.mFilterContext;
    }

    protected Frame frameFromTexture(int i, int i2, int i3) {
        Frame frameNewBoundFrame = getFilterContext().getFrameManager().newBoundFrame(ImageFormat.create(i2, i3, 3, 3), 100, i);
        frameNewBoundFrame.setTimestamp(-1L);
        return frameNewBoundFrame;
    }
}
