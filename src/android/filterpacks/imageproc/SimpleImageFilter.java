package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.Program;
import android.filterfw.format.ImageFormat;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public abstract class SimpleImageFilter extends Filter {
    protected int mCurrentTarget;
    protected String mParameterName;
    protected Program mProgram;

    protected abstract Program getNativeProgram(FilterContext filterContext);

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    protected abstract Program getShaderProgram(FilterContext filterContext);

    public SimpleImageFilter(String str, String str2) {
        super(str);
        this.mCurrentTarget = 0;
        this.mParameterName = str2;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        SimpleImageFilter simpleImageFilter;
        if (this.mParameterName != null) {
            try {
                Field declaredField = SimpleImageFilter.class.getDeclaredField("mProgram");
                String str = this.mParameterName;
                simpleImageFilter = this;
                simpleImageFilter.addProgramPort(str, str, declaredField, Float.TYPE, false);
            } catch (NoSuchFieldException unused) {
                throw new RuntimeException("Internal Error: mProgram field not found!");
            }
        } else {
            simpleImageFilter = this;
        }
        simpleImageFilter.addMaskedInputPort("image", ImageFormat.create(3));
        simpleImageFilter.addOutputBasedOnInput("image", "image");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame pullInput = pullInput("image");
        FrameFormat format = pullInput.getFormat();
        Frame newFrame = filterContext.getFrameManager().newFrame(format);
        updateProgramWithTarget(format.getTarget(), filterContext);
        this.mProgram.process(pullInput, newFrame);
        pushOutput("image", newFrame);
        newFrame.release();
    }

    protected void updateProgramWithTarget(int i, FilterContext filterContext) {
        if (i != this.mCurrentTarget) {
            if (i == 2) {
                this.mProgram = getNativeProgram(filterContext);
            } else if (i == 3) {
                this.mProgram = getShaderProgram(filterContext);
            } else {
                this.mProgram = null;
            }
            Program program = this.mProgram;
            if (program == null) {
                throw new RuntimeException("Could not create a program for image filter " + this + "!");
            }
            initProgramInputs(program, filterContext);
            this.mCurrentTarget = i;
        }
    }
}
