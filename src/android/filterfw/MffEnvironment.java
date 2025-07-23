package android.filterfw;

import android.filterfw.core.CachedFrameManager;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;

/* loaded from: classes.dex */
public class MffEnvironment {
    private FilterContext mContext;

    protected MffEnvironment(FrameManager frameManager) {
        frameManager = frameManager == null ? new CachedFrameManager() : frameManager;
        FilterContext filterContext = new FilterContext();
        this.mContext = filterContext;
        filterContext.setFrameManager(frameManager);
    }

    public FilterContext getContext() {
        return this.mContext;
    }

    public void setGLEnvironment(GLEnvironment gLEnvironment) {
        this.mContext.initGLEnvironment(gLEnvironment);
    }

    public void createGLEnvironment() {
        GLEnvironment gLEnvironment = new GLEnvironment();
        gLEnvironment.initWithNewContext();
        setGLEnvironment(gLEnvironment);
    }

    public void activateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().activate();
            return;
        }
        throw new NullPointerException("No GLEnvironment in place to activate!");
    }

    public void deactivateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().deactivate();
            return;
        }
        throw new NullPointerException("No GLEnvironment in place to deactivate!");
    }
}
