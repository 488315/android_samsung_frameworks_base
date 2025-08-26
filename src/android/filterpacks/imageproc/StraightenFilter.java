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
public class StraightenFilter extends Filter {
    private static final float DEGREE_TO_RADIAN = 0.017453292f;

    @GenerateFieldPort(hasDefault = true, name = "angle")
    private float mAngle;
    private int mHeight;

    @GenerateFieldPort(hasDefault = true, name = "maxAngle")
    private float mMaxAngle;
    private Program mProgram;
    private int mTarget;

    @GenerateFieldPort(hasDefault = true, name = "tile_size")
    private int mTileSize;
    private int mWidth;

    public StraightenFilter(String str) {
        super(str);
        this.mAngle = 0.0f;
        this.mMaxAngle = 45.0f;
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
            this.mHeight = format.getHeight();
            updateParameters();
        }
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(format);
        this.mProgram.process(framePullInput, frameNewFrame);
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
    }

    private void updateParameters() {
        float fCos = (float) Math.cos(this.mAngle * DEGREE_TO_RADIAN);
        float fSin = (float) Math.sin(this.mAngle * DEGREE_TO_RADIAN);
        float f = this.mMaxAngle;
        if (f <= 0.0f) {
            throw new RuntimeException("Max angle is out of range (0-180).");
        }
        if (f > 90.0f) {
            f = 90.0f;
        }
        this.mMaxAngle = f;
        float f2 = -fCos;
        int i = this.mWidth;
        int i2 = this.mHeight;
        float f3 = -fSin;
        Point point = new Point((i * f2) + (i2 * fSin), (i * f3) - (i2 * fCos));
        int i3 = this.mWidth;
        int i4 = this.mHeight;
        Point point2 = new Point((i3 * fCos) + (i4 * fSin), (i3 * fSin) - (i4 * fCos));
        int i5 = this.mWidth;
        int i6 = this.mHeight;
        Point point3 = new Point((f2 * i5) - (i6 * fSin), (f3 * i5) + (i6 * fCos));
        int i7 = this.mWidth;
        int i8 = this.mHeight;
        Point point4 = new Point((i7 * fCos) - (i8 * fSin), (fSin * i7) + (fCos * i8));
        float fMin = Math.min(this.mWidth / Math.max(Math.abs(point.x), Math.abs(point2.x)), this.mHeight / Math.max(Math.abs(point.y), Math.abs(point2.y))) * 0.5f;
        point.set(((point.x * fMin) / this.mWidth) + 0.5f, ((point.y * fMin) / this.mHeight) + 0.5f);
        point2.set(((point2.x * fMin) / this.mWidth) + 0.5f, ((point2.y * fMin) / this.mHeight) + 0.5f);
        point3.set(((point3.x * fMin) / this.mWidth) + 0.5f, ((point3.y * fMin) / this.mHeight) + 0.5f);
        point4.set(((point4.x * fMin) / this.mWidth) + 0.5f, ((fMin * point4.y) / this.mHeight) + 0.5f);
        ((ShaderProgram) this.mProgram).setSourceRegion(new Quad(point, point2, point3, point4));
    }
}
