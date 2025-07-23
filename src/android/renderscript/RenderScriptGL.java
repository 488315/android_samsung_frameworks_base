package android.renderscript;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.renderscript.RenderScript;
import android.view.Surface;
import android.view.SurfaceHolder;

@Deprecated
/* loaded from: classes3.dex */
public class RenderScriptGL extends RenderScript {
    int mHeight;
    SurfaceConfig mSurfaceConfig;
    int mWidth;

    public static class SurfaceConfig {
        int mAlphaMin;
        int mAlphaPref;
        int mColorMin;
        int mColorPref;
        int mDepthMin;
        int mDepthPref;
        int mSamplesMin;
        int mSamplesPref;
        float mSamplesQ;
        int mStencilMin;
        int mStencilPref;

        public SurfaceConfig() {
            this.mDepthMin = 0;
            this.mDepthPref = 0;
            this.mStencilMin = 0;
            this.mStencilPref = 0;
            this.mColorMin = 8;
            this.mColorPref = 8;
            this.mAlphaMin = 0;
            this.mAlphaPref = 0;
            this.mSamplesMin = 1;
            this.mSamplesPref = 1;
            this.mSamplesQ = 1.0f;
        }

        public SurfaceConfig(SurfaceConfig surfaceConfig) {
            this.mDepthMin = 0;
            this.mDepthPref = 0;
            this.mStencilMin = 0;
            this.mStencilPref = 0;
            this.mColorMin = 8;
            this.mColorPref = 8;
            this.mAlphaMin = 0;
            this.mAlphaPref = 0;
            this.mSamplesMin = 1;
            this.mSamplesPref = 1;
            this.mSamplesQ = 1.0f;
            this.mDepthMin = surfaceConfig.mDepthMin;
            this.mDepthPref = surfaceConfig.mDepthPref;
            this.mStencilMin = surfaceConfig.mStencilMin;
            this.mStencilPref = surfaceConfig.mStencilPref;
            this.mColorMin = surfaceConfig.mColorMin;
            this.mColorPref = surfaceConfig.mColorPref;
            this.mAlphaMin = surfaceConfig.mAlphaMin;
            this.mAlphaPref = surfaceConfig.mAlphaPref;
            this.mSamplesMin = surfaceConfig.mSamplesMin;
            this.mSamplesPref = surfaceConfig.mSamplesPref;
            this.mSamplesQ = surfaceConfig.mSamplesQ;
        }

        private void validateRange(int i, int i2, int i3, int i4) {
            if (i < i3 || i > i4) {
                throw new RSIllegalArgumentException("Minimum value provided out of range.");
            }
            if (i2 < i) {
                throw new RSIllegalArgumentException("preferred must be >= Minimum.");
            }
        }

        public void setColor(int i, int i2) {
            validateRange(i, i2, 5, 8);
            this.mColorMin = i;
            this.mColorPref = i2;
        }

        public void setAlpha(int i, int i2) {
            validateRange(i, i2, 0, 8);
            this.mAlphaMin = i;
            this.mAlphaPref = i2;
        }

        public void setDepth(int i, int i2) {
            validateRange(i, i2, 0, 24);
            this.mDepthMin = i;
            this.mDepthPref = i2;
        }

        public void setSamples(int i, int i2, float f) {
            validateRange(i, i2, 1, 32);
            if (f < 0.0f || f > 1.0f) {
                throw new RSIllegalArgumentException("Quality out of 0-1 range.");
            }
            this.mSamplesMin = i;
            this.mSamplesPref = i2;
            this.mSamplesQ = f;
        }
    }

    public RenderScriptGL(Context context, SurfaceConfig surfaceConfig) {
        super(context);
        this.mSurfaceConfig = new SurfaceConfig(surfaceConfig);
        int i = context.getApplicationInfo().targetSdkVersion;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mContext = nContextCreateGL(nDeviceCreate(), 0, i, this.mSurfaceConfig.mColorMin, this.mSurfaceConfig.mColorPref, this.mSurfaceConfig.mAlphaMin, this.mSurfaceConfig.mAlphaPref, this.mSurfaceConfig.mDepthMin, this.mSurfaceConfig.mDepthPref, this.mSurfaceConfig.mStencilMin, this.mSurfaceConfig.mStencilPref, this.mSurfaceConfig.mSamplesMin, this.mSurfaceConfig.mSamplesPref, this.mSurfaceConfig.mSamplesQ, context.getResources().getDisplayMetrics().densityDpi);
        if (this.mContext == 0) {
            throw new RSDriverException("Failed to create RS context.");
        }
        this.mMessageThread = new RenderScript.MessageThread(this);
        this.mMessageThread.start();
    }

    public void setSurface(SurfaceHolder surfaceHolder, int i, int i2) {
        validate();
        Surface surface = surfaceHolder != null ? surfaceHolder.getSurface() : null;
        this.mWidth = i;
        this.mHeight = i2;
        nContextSetSurface(i, i2, surface);
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture, int i, int i2) {
        validate();
        Surface surface = surfaceTexture != null ? new Surface(surfaceTexture) : null;
        this.mWidth = i;
        this.mHeight = i2;
        nContextSetSurface(i, i2, surface);
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void pause() {
        validate();
        nContextPause();
    }

    public void resume() {
        validate();
        nContextResume();
    }

    public void bindRootScript(Script script) {
        validate();
        nContextBindRootScript((int) safeID(script));
    }

    public void bindProgramStore(ProgramStore programStore) {
        validate();
        nContextBindProgramStore((int) safeID(programStore));
    }

    public void bindProgramFragment(ProgramFragment programFragment) {
        validate();
        nContextBindProgramFragment((int) safeID(programFragment));
    }

    public void bindProgramRaster(ProgramRaster programRaster) {
        validate();
        nContextBindProgramRaster((int) safeID(programRaster));
    }

    public void bindProgramVertex(ProgramVertex programVertex) {
        validate();
        nContextBindProgramVertex((int) safeID(programVertex));
    }
}
