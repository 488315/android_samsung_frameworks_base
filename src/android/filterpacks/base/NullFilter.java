package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;

/* loaded from: classes.dex */
public class NullFilter extends Filter {
    public NullFilter(String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        pullInput("frame");
    }
}
