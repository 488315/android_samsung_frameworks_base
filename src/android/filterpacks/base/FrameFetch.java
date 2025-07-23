package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.provider.Telephony;

/* loaded from: classes.dex */
public class FrameFetch extends Filter {

    @GenerateFinalPort(hasDefault = true, name = Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private FrameFormat mFormat;

    @GenerateFieldPort(name = "key")
    private String mKey;

    @GenerateFieldPort(hasDefault = true, name = "repeatFrame")
    private boolean mRepeatFrame;

    public FrameFetch(String str) {
        super(str);
        this.mRepeatFrame = false;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        FrameFormat frameFormat = this.mFormat;
        if (frameFormat == null) {
            frameFormat = FrameFormat.unspecified();
        }
        addOutputPort("frame", frameFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame fetchFrame = filterContext.fetchFrame(this.mKey);
        if (fetchFrame != null) {
            pushOutput("frame", fetchFrame);
            if (this.mRepeatFrame) {
                return;
            }
            closeOutputPort("frame");
            return;
        }
        delayNextProcess(250);
    }
}
