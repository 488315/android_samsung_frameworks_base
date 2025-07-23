package android.filterfw.core;

import java.util.Random;
import java.util.Vector;

/* loaded from: classes.dex */
public class RandomScheduler extends Scheduler {
    private Random mRand;

    @Override // android.filterfw.core.Scheduler
    public void reset() {
    }

    public RandomScheduler(FilterGraph filterGraph) {
        super(filterGraph);
        this.mRand = new Random();
    }

    @Override // android.filterfw.core.Scheduler
    public Filter scheduleNextNode() {
        Vector vector = new Vector();
        for (Filter filter : getGraph().getFilters()) {
            if (filter.canProcess()) {
                vector.add(filter);
            }
        }
        if (vector.size() > 0) {
            return (Filter) vector.elementAt(this.mRand.nextInt(vector.size()));
        }
        return null;
    }
}
