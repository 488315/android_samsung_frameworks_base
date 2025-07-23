package android.filterfw.core;

import android.util.Log;
import java.util.HashMap;

/* loaded from: classes.dex */
public class OneShotScheduler extends RoundRobinScheduler {
    private static final String TAG = "OneShotScheduler";
    private final boolean mLogVerbose;
    private HashMap<String, Integer> scheduled;

    public OneShotScheduler(FilterGraph filterGraph) {
        super(filterGraph);
        this.scheduled = new HashMap<>();
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.RoundRobinScheduler, android.filterfw.core.Scheduler
    public void reset() {
        super.reset();
        this.scheduled.clear();
    }

    @Override // android.filterfw.core.RoundRobinScheduler, android.filterfw.core.Scheduler
    public Filter scheduleNextNode() {
        Filter filter = null;
        while (true) {
            Filter scheduleNextNode = super.scheduleNextNode();
            if (scheduleNextNode == null) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "No filters available to run.");
                }
                return null;
            }
            if (!this.scheduled.containsKey(scheduleNextNode.getName())) {
                if (scheduleNextNode.getNumberOfConnectedInputs() == 0) {
                    this.scheduled.put(scheduleNextNode.getName(), 1);
                }
                if (this.mLogVerbose) {
                    Log.v(TAG, "Scheduling filter \"" + scheduleNextNode.getName() + "\" of type " + scheduleNextNode.getFilterClassName());
                }
                return scheduleNextNode;
            }
            if (filter == scheduleNextNode) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "One pass through graph completed.");
                }
                return null;
            }
            if (filter == null) {
                filter = scheduleNextNode;
            }
        }
    }
}
