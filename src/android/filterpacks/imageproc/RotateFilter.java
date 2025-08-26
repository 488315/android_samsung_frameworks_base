package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;

/* loaded from: classes.dex */
public class RotateFilter extends Filter {

    @GenerateFieldPort(name = "angle")
    private int mAngle;
    private int mHeight;
    private int mOutputHeight;
    private int mOutputWidth;
    private Program mProgram;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public RotateFilter(String str) {
        super(str);
        this.mTileSize = 640;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mTarget = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3));
        addOutputBasedOnInput("image", "image");
    }

    public void initProgram(FilterContext filterContext, int i) {
        if (i == 3) {
            ShaderProgram shaderProgramCreateIdentity = ShaderProgram.createIdentity(filterContext);
            shaderProgramCreateIdentity.setMaximumTileSize(this.mTileSize);
            shaderProgramCreateIdentity.setClearsOutput(true);
            this.mProgram = shaderProgramCreateIdentity;
            this.mTarget = i;
            return;
        }
        throw new RuntimeException("Filter Sharpen does not support frames of target " + i + "!");
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        if (this.mProgram != null) {
            updateParameters();
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("image");
        FrameFormat format = framePullInput.getFormat();
        if (this.mProgram == null || format.getTarget() != this.mTarget) {
            initProgram(filterContext, format.getTarget());
        }
        if (format.getWidth() != this.mWidth || format.getHeight() != this.mHeight) {
            this.mWidth = format.getWidth();
            int height = format.getHeight();
            this.mHeight = height;
            this.mOutputWidth = this.mWidth;
            this.mOutputHeight = height;
            updateParameters();
        }
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(ImageFormat.create(this.mOutputWidth, this.mOutputHeight, 3, 3));
        this.mProgram.process(framePullInput, frameNewFrame);
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
    }

    private void updateParameters() {
        float f;
        int i = this.mAngle;
        if (i % 90 == 0) {
            float f2 = 0.0f;
            if (i % 180 == 0) {
                f2 = i % 360 == 0 ? 1.0f : -1.0f;
                f = 0.0f;
            } else {
                f = (i + 90) % 360 != 0 ? 1.0f : -1.0f;
                this.mOutputWidth = this.mHeight;
                this.mOutputHeight = this.mWidth;
            }
            float f3 = -f2;
            float f4 = -f;
            float f5 = (f2 + f + 1.0f) * 0.5f;
            ((ShaderProgram) this.mProgram).setTargetRegion(new Quad(new Point((f3 + f + 1.0f) * 0.5f, ((f4 - f2) + 1.0f) * 0.5f), new Point(f5, ((f - f2) + 1.0f) * 0.5f), new Point(((f3 - f) + 1.0f) * 0.5f, (f4 + f2 + 1.0f) * 0.5f), new Point(((f2 - f) + 1.0f) * 0.5f, f5)));
            return;
        }
        throw new RuntimeException("degree has to be multiply of 90.");
    }
}
