package android.filterpacks.imageproc;

import android.app.slice.Slice;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Quad;

/* loaded from: classes.dex */
public class DrawOverlayFilter extends Filter {
    private ShaderProgram mProgram;

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    public DrawOverlayFilter(String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        MutableFrameFormat mutableFrameFormatCreate = ImageFormat.create(3, 3);
        addMaskedInputPort(Slice.SUBTYPE_SOURCE, mutableFrameFormatCreate);
        addMaskedInputPort("overlay", mutableFrameFormatCreate);
        addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        addOutputBasedOnInput("image", Slice.SUBTYPE_SOURCE);
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        this.mProgram = ShaderProgram.createIdentity(filterContext);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput(Slice.SUBTYPE_SOURCE);
        Frame framePullInput2 = pullInput("overlay");
        this.mProgram.setTargetRegion(((Quad) pullInput("box").getObjectValue()).translated(1.0f, 1.0f).scaled(2.0f));
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(framePullInput.getFormat());
        frameNewFrame.setDataFromFrame(framePullInput);
        this.mProgram.process(framePullInput2, frameNewFrame);
        pushOutput("image", frameNewFrame);
        frameNewFrame.release();
    }
}
