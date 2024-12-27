package android.media.effect;

import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.format.ImageFormat;

public abstract class FilterEffect extends Effect {
    protected EffectContext mEffectContext;
    private String mName;

    protected FilterEffect(EffectContext context, String name) {
        this.mEffectContext = context;
        this.mName = name;
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

    protected Frame frameFromTexture(int texId, int width, int height) {
        FrameManager manager = getFilterContext().getFrameManager();
        FrameFormat format = ImageFormat.create(width, height, 3, 3);
        Frame frame = manager.newBoundFrame(format, 100, texId);
        frame.setTimestamp(-1L);
        return frame;
    }
}
