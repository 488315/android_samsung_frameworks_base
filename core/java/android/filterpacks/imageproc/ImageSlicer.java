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

    public ImageSlicer(String name) {
        super(name);
        this.mSliceIndex = 0;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 3));
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String portName, FrameFormat inputFormat) {
        return inputFormat;
    }

    private void calcOutputFormatForInput(Frame frame) {
        this.mInputWidth = frame.getFormat().getWidth();
        this.mInputHeight = frame.getFormat().getHeight();
        int i = this.mInputWidth;
        int i2 = ((i + r2) - 1) / this.mXSlices;
        this.mSliceWidth = i2;
        int i3 = ((r0 + r2) - 1) / this.mYSlices;
        this.mSliceHeight = i3;
        int i4 = this.mPadSize;
        this.mOutputWidth = i2 + (i4 * 2);
        this.mOutputHeight = i3 + (i4 * 2);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext context) {
        if (this.mSliceIndex == 0) {
            Frame pullInput = pullInput("image");
            this.mOriginalFrame = pullInput;
            calcOutputFormatForInput(pullInput);
        }
        FrameFormat inputFormat = this.mOriginalFrame.getFormat();
        MutableFrameFormat outputFormat = inputFormat.mutableCopy();
        outputFormat.setDimensions(this.mOutputWidth, this.mOutputHeight);
        Frame output = context.getFrameManager().newFrame(outputFormat);
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(context);
        }
        int i = this.mSliceIndex;
        int i2 = this.mXSlices;
        int xSliceIndex = i % i2;
        int ySliceIndex = i / i2;
        int i3 = this.mSliceWidth * xSliceIndex;
        int i4 = this.mPadSize;
        int i5 = this.mInputWidth;
        float x0 = (i3 - i4) / i5;
        float f = (this.mSliceHeight * ySliceIndex) - i4;
        int i6 = this.mInputHeight;
        float y0 = f / i6;
        ((ShaderProgram) this.mProgram).setSourceRect(x0, y0, this.mOutputWidth / i5, this.mOutputHeight / i6);
        this.mProgram.process(this.mOriginalFrame, output);
        int i7 = this.mSliceIndex + 1;
        this.mSliceIndex = i7;
        if (i7 == this.mXSlices * this.mYSlices) {
            this.mSliceIndex = 0;
            this.mOriginalFrame.release();
            setWaitsOnInputPort("image", true);
        } else {
            this.mOriginalFrame.retain();
            setWaitsOnInputPort("image", false);
        }
        pushOutput("image", output);
        output.release();
    }
}
