package android.filterfw.core;

import java.util.Map;

/* loaded from: classes.dex */
public class FilterFunction {
    private Filter mFilter;
    private FilterContext mFilterContext;
    private boolean mFilterIsSetup = false;
    private FrameHolderPort[] mResultHolders;

    private class FrameHolderPort extends StreamPort {
        public FrameHolderPort(FilterFunction filterFunction) {
            super(null, "holder");
        }
    }

    public FilterFunction(FilterContext filterContext, Filter filter) {
        this.mFilterContext = filterContext;
        this.mFilter = filter;
    }

    public Frame execute(KeyValueMap keyValueMap) {
        boolean z;
        int numberOfOutputs = this.mFilter.getNumberOfOutputs();
        if (numberOfOutputs > 1) {
            throw new RuntimeException("Calling execute on filter " + this.mFilter + " with multiple outputs! Use executeMulti() instead!");
        }
        if (!this.mFilterIsSetup) {
            connectFilterOutputs();
            this.mFilterIsSetup = true;
        }
        GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment == null || gLEnvironment.isActive()) {
            z = false;
        } else {
            gLEnvironment.activate();
            z = true;
        }
        for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
            if (entry.getValue() instanceof Frame) {
                this.mFilter.pushInputFrame(entry.getKey(), (Frame) entry.getValue());
            } else {
                this.mFilter.pushInputValue(entry.getKey(), entry.getValue());
            }
        }
        if (this.mFilter.getStatus() != 3) {
            this.mFilter.openOutputs();
        }
        this.mFilter.performProcess(this.mFilterContext);
        Frame pullFrame = (numberOfOutputs == 1 && this.mResultHolders[0].hasFrame()) ? this.mResultHolders[0].pullFrame() : null;
        if (z) {
            gLEnvironment.deactivate();
        }
        return pullFrame;
    }

    public Frame executeWithArgList(Object... objArr) {
        return execute(KeyValueMap.fromKeyValues(objArr));
    }

    public void close() {
        this.mFilter.performClose(this.mFilterContext);
    }

    public FilterContext getContext() {
        return this.mFilterContext;
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public void setInputFrame(String str, Frame frame) {
        this.mFilter.setInputFrame(str, frame);
    }

    public void setInputValue(String str, Object obj) {
        this.mFilter.setInputValue(str, obj);
    }

    public void tearDown() {
        this.mFilter.performTearDown(this.mFilterContext);
        this.mFilter = null;
    }

    public String toString() {
        return this.mFilter.getName();
    }

    private void connectFilterOutputs() {
        this.mResultHolders = new FrameHolderPort[this.mFilter.getNumberOfOutputs()];
        int i = 0;
        for (OutputPort outputPort : this.mFilter.getOutputPorts()) {
            this.mResultHolders[i] = new FrameHolderPort(this);
            outputPort.connectTo(this.mResultHolders[i]);
            i++;
        }
    }
}
