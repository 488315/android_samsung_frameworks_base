package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class ProgramRaster extends BaseObj {
    CullMode mCullMode;
    boolean mPointSprite;

    public enum CullMode {
        BACK(0),
        FRONT(1),
        NONE(2);

        int mID;

        CullMode(int i) {
            this.mID = i;
        }
    }

    ProgramRaster(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.mPointSprite = false;
        this.mCullMode = CullMode.BACK;
    }

    public boolean isPointSpriteEnabled() {
        return this.mPointSprite;
    }

    public CullMode getCullMode() {
        return this.mCullMode;
    }

    public static ProgramRaster CULL_BACK(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_BACK == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.BACK);
            renderScript.mProgramRaster_CULL_BACK = builder.create();
        }
        return renderScript.mProgramRaster_CULL_BACK;
    }

    public static ProgramRaster CULL_FRONT(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_FRONT == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.FRONT);
            renderScript.mProgramRaster_CULL_FRONT = builder.create();
        }
        return renderScript.mProgramRaster_CULL_FRONT;
    }

    public static ProgramRaster CULL_NONE(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_NONE == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.NONE);
            renderScript.mProgramRaster_CULL_NONE = builder.create();
        }
        return renderScript.mProgramRaster_CULL_NONE;
    }

    public static class Builder {
        RenderScript mRS;
        boolean mPointSprite = false;
        CullMode mCullMode = CullMode.BACK;

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public Builder setPointSpriteEnabled(boolean z) {
            this.mPointSprite = z;
            return this;
        }

        public Builder setCullMode(CullMode cullMode) {
            this.mCullMode = cullMode;
            return this;
        }

        public ProgramRaster create() {
            this.mRS.validate();
            ProgramRaster programRaster = new ProgramRaster(this.mRS.nProgramRasterCreate(this.mPointSprite, this.mCullMode.mID), this.mRS);
            programRaster.mPointSprite = this.mPointSprite;
            programRaster.mCullMode = this.mCullMode;
            return programRaster;
        }
    }
}
