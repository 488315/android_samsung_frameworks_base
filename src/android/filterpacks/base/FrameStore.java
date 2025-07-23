package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.GenerateFieldPort;

/* loaded from: classes.dex */
public class FrameStore extends Filter {

    @GenerateFieldPort(name = "key")
    private String mKey;

    public FrameStore(String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        filterContext.storeFrame(this.mKey, pullInput("frame"));
    }
}
