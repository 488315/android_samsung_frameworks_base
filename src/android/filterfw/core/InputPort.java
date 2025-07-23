package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class InputPort extends FilterPort {
    protected OutputPort mSourcePort;

    public Object getTarget() {
        return null;
    }

    public abstract void transfer(FilterContext filterContext);

    public InputPort(Filter filter, String str) {
        super(filter, str);
    }

    public void setSourcePort(OutputPort outputPort) {
        if (this.mSourcePort != null) {
            throw new RuntimeException(this + " already connected to " + this.mSourcePort + "!");
        }
        this.mSourcePort = outputPort;
    }

    public boolean isConnected() {
        return this.mSourcePort != null;
    }

    @Override // android.filterfw.core.FilterPort
    public void open() {
        super.open();
        OutputPort outputPort = this.mSourcePort;
        if (outputPort == null || outputPort.isOpen()) {
            return;
        }
        this.mSourcePort.open();
    }

    @Override // android.filterfw.core.FilterPort
    public void close() {
        OutputPort outputPort = this.mSourcePort;
        if (outputPort != null && outputPort.isOpen()) {
            this.mSourcePort.close();
        }
        super.close();
    }

    public OutputPort getSourcePort() {
        return this.mSourcePort;
    }

    public Filter getSourceFilter() {
        OutputPort outputPort = this.mSourcePort;
        if (outputPort == null) {
            return null;
        }
        return outputPort.getFilter();
    }

    public FrameFormat getSourceFormat() {
        OutputPort outputPort = this.mSourcePort;
        return outputPort != null ? outputPort.getPortFormat() : getPortFormat();
    }

    @Override // android.filterfw.core.FilterPort
    public boolean filterMustClose() {
        return (isOpen() || !isBlocking() || hasFrame()) ? false : true;
    }

    @Override // android.filterfw.core.FilterPort
    public boolean isReady() {
        return hasFrame() || !isBlocking();
    }

    public boolean acceptsFrame() {
        return !hasFrame();
    }
}
