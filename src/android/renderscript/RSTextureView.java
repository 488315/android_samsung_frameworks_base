package android.renderscript;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.renderscript.RenderScriptGL;
import android.util.AttributeSet;
import android.view.TextureView;

@Deprecated
/* loaded from: classes3.dex */
public class RSTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    private RenderScriptGL mRS;
    private SurfaceTexture mSurfaceTexture;

    public RSTextureView(Context context) {
        super(context);
        init();
    }

    public RSTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.setSurfaceTexture(surfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.setSurfaceTexture(surfaceTexture, i, i2);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL == null) {
            return true;
        }
        renderScriptGL.setSurfaceTexture(null, 0, 0);
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
    }

    public void pause() {
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.pause();
        }
    }

    public void resume() {
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.resume();
        }
    }

    public RenderScriptGL createRenderScriptGL(RenderScriptGL.SurfaceConfig surfaceConfig) {
        RenderScriptGL renderScriptGL = new RenderScriptGL(getContext(), surfaceConfig);
        setRenderScriptGL(renderScriptGL);
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            this.mRS.setSurfaceTexture(surfaceTexture, getWidth(), getHeight());
        }
        return renderScriptGL;
    }

    public void destroyRenderScriptGL() {
        this.mRS.destroy();
        this.mRS = null;
    }

    public void setRenderScriptGL(RenderScriptGL renderScriptGL) {
        this.mRS = renderScriptGL;
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            renderScriptGL.setSurfaceTexture(surfaceTexture, getWidth(), getHeight());
        }
    }

    public RenderScriptGL getRenderScriptGL() {
        return this.mRS;
    }
}
