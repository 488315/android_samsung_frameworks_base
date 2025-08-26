package android.filterpacks.numeric;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.ObjectFormat;

/* loaded from: classes.dex */
public class SinWaveFilter extends Filter {
    private FrameFormat mOutputFormat;

    @GenerateFieldPort(hasDefault = true, name = "stepSize")
    private float mStepSize;
    private float mValue;

    public SinWaveFilter(String str) {
        super(str);
        this.mStepSize = 0.05f;
        this.mValue = 0.0f;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        MutableFrameFormat mutableFrameFormatFromClass = ObjectFormat.fromClass(Float.class, 1);
        this.mOutputFormat = mutableFrameFormatFromClass;
        addOutputPort("value", mutableFrameFormatFromClass);
    }

    @Override // android.filterfw.core.Filter
    public void open(FilterContext filterContext) {
        this.mValue = 0.0f;
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame frameNewFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        frameNewFrame.setObjectValue(Float.valueOf((((float) Math.sin(this.mValue)) + 1.0f) / 2.0f));
        pushOutput("value", frameNewFrame);
        this.mValue += this.mStepSize;
        frameNewFrame.release();
    }
}
