package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.Program;
import android.filterfw.format.ImageFormat;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public abstract class ImageCombineFilter extends Filter {
    protected int mCurrentTarget;
    protected String[] mInputNames;
    protected String mOutputName;
    protected String mParameterName;
    protected Program mProgram;

    protected abstract Program getNativeProgram(FilterContext filterContext);

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        return frameFormat;
    }

    protected abstract Program getShaderProgram(FilterContext filterContext);

    public ImageCombineFilter(String str, String[] strArr, String str2, String str3) {
        super(str);
        this.mCurrentTarget = 0;
        this.mInputNames = strArr;
        this.mOutputName = str2;
        this.mParameterName = str3;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        ImageCombineFilter imageCombineFilter;
        if (this.mParameterName != null) {
            try {
                Field declaredField = ImageCombineFilter.class.getDeclaredField("mProgram");
                String str = this.mParameterName;
                imageCombineFilter = this;
                imageCombineFilter.addProgramPort(str, str, declaredField, Float.TYPE, false);
            } catch (NoSuchFieldException unused) {
                throw new RuntimeException("Internal Error: mProgram field not found!");
            }
        } else {
            imageCombineFilter = this;
        }
        for (String str2 : imageCombineFilter.mInputNames) {
            imageCombineFilter.addMaskedInputPort(str2, ImageFormat.create(3));
        }
        imageCombineFilter.addOutputBasedOnInput(imageCombineFilter.mOutputName, imageCombineFilter.mInputNames[0]);
    }

    private void assertAllInputTargetsMatch() {
        int target = getInputFormat(this.mInputNames[0]).getTarget();
        for (String str : this.mInputNames) {
            if (target != getInputFormat(str).getTarget()) {
                throw new RuntimeException("Type mismatch of input formats in filter " + this + ". All input frames must have the same target!");
            }
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        String[] strArr = this.mInputNames;
        Frame[] frameArr = new Frame[strArr.length];
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            frameArr[i2] = pullInput(strArr[i]);
            i++;
            i2++;
        }
        Frame newFrame = filterContext.getFrameManager().newFrame(frameArr[0].getFormat());
        updateProgramWithTarget(frameArr[0].getFormat().getTarget(), filterContext);
        this.mProgram.process(frameArr, newFrame);
        pushOutput(this.mOutputName, newFrame);
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
