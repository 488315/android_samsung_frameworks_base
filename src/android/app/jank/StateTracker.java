package android.app.jank;

import android.util.Pools;
import android.view.Choreographer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class StateTracker {
    protected static final int MAX_CONCURRENT_STATE_COUNT = 25;
    private static final int MAX_POOL_SIZE = 500;
    protected static final int MAX_PREVIOUSLY_ACTIVE_STATE_COUNT = 1000;
    private Choreographer mChoreographer;
    private final Object mLock = new Object();
    private final Pools.SimplePool<StateData> mStateDataObjectPool = new Pools.SimplePool<>(500);
    private ArrayList<StateData> mPreviousStates = new ArrayList<>();
    private ConcurrentHashMap<String, StateData> mActiveStates = new ConcurrentHashMap<>();

    public static class StateData {
        public boolean mProcessed;
        public String mStateDataKey;
        public long mVsyncIdEnd;
        public long mVsyncIdStart;
        public String mWidgetCategory;
        public String mWidgetId;
        public String mWidgetState;
    }

    public StateTracker(Choreographer choreographer) {
        this.mChoreographer = choreographer;
    }

    public void updateState(String str, String str2, String str3, String str4) {
        removeState(str, str2, str3);
        putState(str, str2, str4);
    }

    public void removeState(String str, String str2, String str3) {
        StateData remove = this.mActiveStates.remove(getStateKey(str, str2, str3));
        if (remove == null) {
            return;
        }
        synchronized (this.mLock) {
            remove.mVsyncIdEnd = this.mChoreographer.getVsyncId();
            if (this.mPreviousStates.size() < 1000) {
                this.mPreviousStates.add(remove);
            }
        }
    }

    public void putState(String str, String str2, String str3) {
        if (this.mActiveStates.size() >= 25) {
            return;
        }
        String stateKey = getStateKey(str, str2, str3);
        if (this.mActiveStates.containsKey(stateKey)) {
            return;
        }
        StateData acquire = this.mStateDataObjectPool.acquire();
        if (acquire == null) {
            acquire = new StateData();
        }
        acquire.mVsyncIdStart = this.mChoreographer.getVsyncId();
        acquire.mStateDataKey = stateKey;
        acquire.mWidgetState = str3;
        acquire.mWidgetCategory = str;
        acquire.mWidgetId = str2;
        acquire.mVsyncIdEnd = Long.MAX_VALUE;
        this.mActiveStates.put(stateKey, acquire);
    }

    public void retrieveAllStates(ArrayList<StateData> arrayList) {
        synchronized (this.mLock) {
            arrayList.addAll(this.mPreviousStates);
            arrayList.addAll(this.mActiveStates.values());
        }
    }

    public void stateProcessingComplete() {
        synchronized (this.mLock) {
            for (int size = this.mPreviousStates.size() - 1; size >= 0; size--) {
                StateData stateData = this.mPreviousStates.get(size);
                if (stateData.mProcessed) {
                    this.mPreviousStates.remove(stateData);
                    this.mStateDataObjectPool.release(stateData);
                }
            }
        }
    }

    public void addPendingStateData(List<StateData> list) {
        synchronized (this.mLock) {
            this.mPreviousStates.addAll(list);
        }
    }

    public String getStateKey(String str, String str2, String str3) {
        return str + str2 + str3;
    }
}
