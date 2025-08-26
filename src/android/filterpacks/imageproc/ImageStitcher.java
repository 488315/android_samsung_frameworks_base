package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

/* loaded from: classes.dex */
public class ImageStitcher extends Filter {
    private int mImageHeight;
    private int mImageWidth;
    private int mInputHeight;
    private int mInputWidth;
    private Frame mOutputFrame;

    @GenerateFieldPort(name = "padSize")
    private int mPadSize;
    private Program mProgram;
    private int mSliceHeight;
    private int mSliceIndex;
    private int mSliceWidth;

    @GenerateFieldPort(name = "xSlices")
    private int mXSlices;

    @GenerateFieldPort(name = "ySlices")
    private int mYSlices;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public ImageStitcher(String str) {
        super(str);
        this.mSliceIndex = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addOutputBasedOnInput("image", "image");
    }

    private FrameFormat calcOutputFormatForInput(FrameFormat frameFormat) {
        MutableFrameFormat mutableFrameFormatMutableCopy = frameFormat.mutableCopy();
        this.mInputWidth = frameFormat.getWidth();
        int height = frameFormat.getHeight();
        this.mInputHeight = height;
        int i = this.mInputWidth;
        int i2 = this.mPadSize;
        int i3 = i - (i2 * 2);
        this.mSliceWidth = i3;
        int i4 = height - (i2 * 2);
        this.mSliceHeight = i4;
        int i5 = i3 * this.mXSlices;
        this.mImageWidth = i5;
        int i6 = i4 * this.mYSlices;
        this.mImageHeight = i6;
        mutableFrameFormatMutableCopy.setDimensions(i5, i6);
        return mutableFrameFormatMutableCopy;
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("image");
        FrameFormat format = framePullInput.getFormat();
        if (this.mSliceIndex == 0) {
            this.mOutputFrame = filterContext.getFrameManager().newFrame(calcOutputFormatForInput(format));
        } else if (format.getWidth() != this.mInputWidth || format.getHeight() != this.mInputHeight) {
            throw new RuntimeException("Image size should not change.");
        }
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(filterContext);
        }
        int i = this.mPadSize;
        int i2 = this.mSliceIndex;
        int i3 = this.mXSlices;
        int i4 = this.mSliceWidth;
        int i5 = (i2 % i3) * i4;
        int i6 = (i2 / i3) * this.mSliceHeight;
        float fMin = Math.min(i4, this.mImageWidth - i5);
        float fMin2 = Math.min(this.mSliceHeight, this.mImageHeight - i6);
        ((ShaderProgram) this.mProgram).setSourceRect(i / this.mInputWidth, i / this.mInputHeight, fMin / this.mInputWidth, fMin2 / this.mInputHeight);
        ShaderProgram shaderProgram = (ShaderProgram) this.mProgram;
        float f = i5;
        int i7 = this.mImageWidth;
        int i8 = this.mImageHeight;
        shaderProgram.setTargetRect(f / i7, i6 / i8, fMin / i7, fMin2 / i8);
        this.mProgram.process(framePullInput, this.mOutputFrame);
        int i9 = this.mSliceIndex + 1;
        this.mSliceIndex = i9;
        if (i9 == this.mXSlices * this.mYSlices) {
            pushOutput("image", this.mOutputFrame);
            this.mOutputFrame.release();
            this.mSliceIndex = 0;
        }
    }
}
