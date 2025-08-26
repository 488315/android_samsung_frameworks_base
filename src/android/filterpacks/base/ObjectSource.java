package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.format.ObjectFormat;
import android.provider.Telephony;

/* loaded from: classes.dex */
public class ObjectSource extends Filter {
    private Frame mFrame;

    @GenerateFieldPort(name = "object")
    private Object mObject;

    @GenerateFinalPort(hasDefault = true, name = Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private FrameFormat mOutputFormat;

    @GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    boolean mRepeatFrame;

    public ObjectSource(String str) {
        super(str);
        this.mOutputFormat = FrameFormat.unspecified();
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addOutputPort("frame", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        if (this.mFrame == null) {
            Object obj = this.mObject;
            if (obj == null) {
                throw new NullPointerException("ObjectSource producing frame with no object set!");
            }
            Frame frameNewFrame = filterContext.getFrameManager().newFrame(ObjectFormat.fromObject(obj, 1));
            this.mFrame = frameNewFrame;
            frameNewFrame.setObjectValue(this.mObject);
            this.mFrame.setTimestamp(-1L);
        }
        pushOutput("frame", this.mFrame);
        if (this.mRepeatFrame) {
            return;
        }
        closeOutputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void tearDown(FilterContext filterContext) {
        this.mFrame.release();
    }

    @Override // android.filterfw.core.Filter
    public void fieldPortValueUpdated(String str, FilterContext filterContext) {
        Frame frame;
        if (!str.equals("object") || (frame = this.mFrame) == null) {
            return;
        }
        frame.release();
        this.mFrame = null;
    }
}
