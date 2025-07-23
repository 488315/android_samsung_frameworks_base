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
public class ToRGBAFilter extends Filter {
    private int mInputBPP;
    private FrameFormat mLastFormat;
    private Program mProgram;

    public ToRGBAFilter(String str) {
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
        MutableFrameFormat mutableCopy = frameFormat.mutableCopy();
        mutableCopy.setMetaValue(ImageFormat.COLORSPACE_KEY, 3);
        mutableCopy.setBytesPerSample(4);
        return mutableCopy;
    }

    public void createProgram(FilterContext filterContext, FrameFormat frameFormat) {
        this.mInputBPP = frameFormat.getBytesPerSample();
        FrameFormat frameFormat2 = this.mLastFormat;
        if (frameFormat2 == null || frameFormat2.getBytesPerSample() != this.mInputBPP) {
            this.mLastFormat = frameFormat;
            int i = this.mInputBPP;
            if (i == 1) {
                this.mProgram = new NativeProgram("filterpack_imageproc", "gray_to_rgba");
            } else {
                if (i == 3) {
                    this.mProgram = new NativeProgram("filterpack_imageproc", "rgb_to_rgba");
                    return;
                }
                throw new RuntimeException("Unsupported BytesPerPixel: " + this.mInputBPP + "!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame pullInput = pullInput("image");
        createProgram(filterContext, pullInput.getFormat());
        Frame newFrame = filterContext.getFrameManager().newFrame(getConvertedFormat(pullInput.getFormat()));
        this.mProgram.process(pullInput, newFrame);
        pushOutput("image", newFrame);
        newFrame.release();
    }
}
