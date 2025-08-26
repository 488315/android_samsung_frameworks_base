package android.filterfw.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class FilterContext {
    private FrameManager mFrameManager;
    private GLEnvironment mGLEnvironment;
    private HashMap<String, Frame> mStoredFrames = new HashMap<>();
    private Set<FilterGraph> mGraphs = new HashSet();

    public interface OnFrameReceivedListener {
        void onFrameReceived(Filter filter, Frame frame, Object obj);
    }

    public FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    public void setFrameManager(FrameManager frameManager) {
        if (frameManager == null) {
            throw new NullPointerException("Attempting to set null FrameManager!");
        }
        if (frameManager.getContext() != null) {
            throw new IllegalArgumentException("Attempting to set FrameManager which is already bound to another FilterContext!");
        }
        this.mFrameManager = frameManager;
        frameManager.setContext(this);
    }

    public GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    public void initGLEnvironment(GLEnvironment gLEnvironment) {
        if (this.mGLEnvironment == null) {
            this.mGLEnvironment = gLEnvironment;
            return;
        }
        throw new RuntimeException("Attempting to re-initialize GL Environment for FilterContext!");
    }

    public synchronized void storeFrame(String str, Frame frame) {
        Frame frameFetchFrame = fetchFrame(str);
        if (frameFetchFrame != null) {
            frameFetchFrame.release();
        }
        frame.onFrameStore();
        this.mStoredFrames.put(str, frame.retain());
    }

    public synchronized Frame fetchFrame(String str) {
        Frame frame;
        frame = this.mStoredFrames.get(str);
        if (frame != null) {
            frame.onFrameFetch();
        }
        return frame;
    }

    public synchronized void removeFrame(String str) {
        Frame frame = this.mStoredFrames.get(str);
        if (frame != null) {
            this.mStoredFrames.remove(str);
            frame.release();
        }
    }

    public synchronized void tearDown() {
        Iterator<Frame> it = this.mStoredFrames.values().iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.mStoredFrames.clear();
        Iterator<FilterGraph> it2 = this.mGraphs.iterator();
        while (it2.hasNext()) {
            it2.next().tearDown(this);
        }
        this.mGraphs.clear();
        FrameManager frameManager = this.mFrameManager;
        if (frameManager != null) {
            frameManager.tearDown();
            this.mFrameManager = null;
        }
        GLEnvironment gLEnvironment = this.mGLEnvironment;
        if (gLEnvironment != null) {
            gLEnvironment.tearDown();
            this.mGLEnvironment = null;
        }
    }

    final void addGraph(FilterGraph filterGraph) {
        this.mGraphs.add(filterGraph);
    }
}
