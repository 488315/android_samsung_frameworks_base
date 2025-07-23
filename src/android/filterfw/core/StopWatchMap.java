package android.filterfw.core;

import java.util.HashMap;

/* loaded from: classes.dex */
public class StopWatchMap {
    public boolean LOG_MFF_RUNNING_TIMES = false;
    private HashMap<String, StopWatch> mStopWatches;

    public StopWatchMap() {
        this.mStopWatches = null;
        this.mStopWatches = new HashMap<>();
    }

    public void start(String str) {
        if (this.LOG_MFF_RUNNING_TIMES) {
            if (!this.mStopWatches.containsKey(str)) {
                this.mStopWatches.put(str, new StopWatch(str));
            }
            this.mStopWatches.get(str).start();
        }
    }

    public void stop(String str) {
        if (this.LOG_MFF_RUNNING_TIMES) {
            if (!this.mStopWatches.containsKey(str)) {
                throw new RuntimeException("Calling stop with unknown stopWatchName: " + str);
            }
            this.mStopWatches.get(str).stop();
        }
    }
}
