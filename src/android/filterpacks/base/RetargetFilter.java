package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;

/* loaded from: classes.dex */
public class RetargetFilter extends Filter {
    private MutableFrameFormat mOutputFormat;
    private int mTarget;

    @GenerateFinalPort(hasDefault = false, name = "target")
    private String mTargetString;

    public RetargetFilter(String str) {
        super(str);
        this.mTarget = -1;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        this.mTarget = FrameFormat.readTargetString(this.mTargetString);
        addInputPort("frame");
        addOutputBasedOnInput("frame", "frame");
    }

    @Override // android.filterfw.core.Filter
    public FrameFormat getOutputFormat(String str, FrameFormat frameFormat) {
        MutableFrameFormat mutableFrameFormatMutableCopy = frameFormat.mutableCopy();
        mutableFrameFormatMutableCopy.setTarget(this.mTarget);
        return mutableFrameFormatMutableCopy;
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame frameDuplicateFrameToTarget = filterContext.getFrameManager().duplicateFrameToTarget(pullInput("frame"), this.mTarget);
        pushOutput("frame", frameDuplicateFrameToTarget);
        frameDuplicateFrameToTarget.release();
    }
}
