package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;
import android.filterfw.format.ImageFormat;

/* loaded from: classes.dex */
public class ToRGBFilter extends Filter {
    private int mInputBPP;
    private FrameFormat mLastFormat;
    private Program mProgram;

    public ToRGBFilter(String str) {
        super(str);
        this.mLastFormat = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(2, 2);
        mutableFrameFormat.setDimensionCount(2);
        addMaskedInputPort("image", mutableFrameFormat);
        addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return getConvertedFormat(frameFormat);
    }

    public FrameFormat getConvertedFormat(FrameFormat frameFormat) {
        MutableFrameFormat mutableFrameFormatMutableCopy = frameFormat.mutableCopy();
        mutableFrameFormatMutableCopy.setMetaValue(ImageFormat.COLORSPACE_KEY, 2);
        mutableFrameFormatMutableCopy.setBytesPerSample(3);
        return mutableFrameFormatMutableCopy;
    }

    public void createProgram(FilterContext filterContext, FrameFormat frameFormat) {
        this.mInputBPP = frameFormat.getBytesPerSample();
        FrameFormat frameFormat2 = this.mLastFormat;
        if (frameFormat2 == null || frameFormat2.getBytesPerSample() != this.mInputBPP) {
            this.mLastFormat = frameFormat;
            int i = this.mInputBPP;
            if (i == 1) {
                this.mProgram = new NativeProgram("filterpack_imageproc", "gray_to_rgb");
            } else {
                if (i == 4) {
                    this.mProgram = new NativeProgram("filterpack_imageproc", "rgba_to_rgb");
                    return;
                }
                throw new RuntimeException("Unsupported BytesPerPixel: " + this.mInputBPP + "!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("image");
        createProgram(filterContext, framePullInput.getFormat());
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(getConvertedFormat(framePullInput.getFormat()));
        this.mProgram.process(framePullInput, frameNewFrame);
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
    }
}
