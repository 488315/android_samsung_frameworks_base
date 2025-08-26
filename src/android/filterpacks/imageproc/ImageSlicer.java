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
public class ImageSlicer extends Filter {
    private int mInputHeight;
    private int mInputWidth;
    private Frame mOriginalFrame;
    private int mOutputHeight;
    private int mOutputWidth;

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

    public ImageSlicer(String str) {
        super(str);
        this.mSliceIndex = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addOutputBasedOnInput("image", "image");
    }

    private void calcOutputFormatForInput(Frame frame) {
        this.mInputWidth = frame.getFormat().getWidth();
        this.mInputHeight = frame.getFormat().getHeight();
        int i = this.mInputWidth;
        int i2 = ((i + r1) - 1) / this.mXSlices;
        this.mSliceWidth = i2;
        int i3 = ((r4 + r1) - 1) / this.mYSlices;
        this.mSliceHeight = i3;
        int i4 = this.mPadSize;
        this.mOutputWidth = i2 + (i4 * 2);
        this.mOutputHeight = i3 + (i4 * 2);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        if (this.mSliceIndex == 0) {
            Frame framePullInput = pullInput("image");
            this.mOriginalFrame = framePullInput;
            calcOutputFormatForInput(framePullInput);
        }
        MutableFrameFormat mutableFrameFormatMutableCopy = this.mOriginalFrame.getFormat().mutableCopy();
        mutableFrameFormatMutableCopy.setDimensions(this.mOutputWidth, this.mOutputHeight);
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(mutableFrameFormatMutableCopy);
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(filterContext);
        }
        int i = this.mSliceIndex;
        int i2 = this.mXSlices;
        int i3 = i % i2;
        int i4 = i / i2;
        int i5 = i3 * this.mSliceWidth;
        int i6 = this.mPadSize;
        int i7 = this.mInputWidth;
        float f = (i5 - i6) / i7;
        float f2 = (i4 * this.mSliceHeight) - i6;
        int i8 = this.mInputHeight;
        ((ShaderProgram) this.mProgram).setSourceRect(f, f2 / i8, this.mOutputWidth / i7, this.mOutputHeight / i8);
        this.mProgram.process(this.mOriginalFrame, frameNewFrame);
        int i9 = this.mSliceIndex + 1;
        this.mSliceIndex = i9;
        if (i9 == this.mXSlices * this.mYSlices) {
            this.mSliceIndex = 0;
            this.mOriginalFrame.release();
            setWaitsOnInputPort("image", true);
        } else {
            this.mOriginalFrame.retain();
            setWaitsOnInputPort("image", false);
        }
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
    }
}
