package android.filterfw.core;

import java.util.Set;

/* loaded from: classes.dex */
public class RoundRobinScheduler extends Scheduler {
    private int mLastPos;

    public RoundRobinScheduler(FilterGraph filterGraph) {
        super(filterGraph);
        this.mLastPos = -1;
    }

    @Override // android.filterfw.core.Scheduler
    public void reset() {
        this.mLastPos = -1;
    }

    @Override // android.filterfw.core.Scheduler
    public Filter scheduleNextNode() {
        Set<Filter> filters = getGraph().getFilters();
        int i = -1;
        if (this.mLastPos >= filters.size()) {
            this.mLastPos = -1;
        }
        int i2 = 0;
        Filter filter = null;
        for (Filter filter2 : filters) {
            if (filter2.canProcess()) {
                if (i2 > this.mLastPos) {
                    this.mLastPos = i2;
                    return filter2;
                }
                if (filter == null) {
                    i = i2;
                    filter = filter2;
                }
            }
            i2++;
        }
        if (filter == null) {
            return null;
        }
        this.mLastPos = i;
        return filter;
    }
}
