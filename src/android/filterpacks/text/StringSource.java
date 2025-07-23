package android.filterpacks.text;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.ObjectFormat;

/* loaded from: classes.dex */
public class StringSource extends Filter {
    private FrameFormat mOutputFormat;

    @GenerateFieldPort(name = "stringValue")
    private String mString;

    public StringSource(String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        MutableFrameFormat fromClass = ObjectFormat.fromClass(String.class, 1);
        this.mOutputFormat = fromClass;
        addOutputPort("string", fromClass);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame newFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
        newFrame.setObjectValue(this.mString);
        newFrame.setTimestamp(-1L);
        pushOutput("string", newFrame);
        closeOutputPort("string");
    }
}
