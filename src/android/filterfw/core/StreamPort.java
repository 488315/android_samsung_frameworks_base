package android.filterfw.core;

/* loaded from: classes.dex */
public class StreamPort extends InputPort {
    private Frame mFrame;
    private boolean mPersistent;

    public StreamPort(Filter filter, String str) {
        super(filter, str);
    }

    @Override // android.filterfw.core.FilterPort
    public void clear() {
        Frame frame = this.mFrame;
        if (frame != null) {
            frame.release();
            this.mFrame = null;
        }
    }

    @Override // android.filterfw.core.FilterPort
    public void setFrame(Frame frame) {
        assignFrame(frame, true);
    }

    @Override // android.filterfw.core.FilterPort
    public void pushFrame(Frame frame) {
        assignFrame(frame, false);
    }

    protected synchronized void assignFrame(Frame frame, boolean z) {
        assertPortIsOpen();
        checkFrameType(frame, z);
        if (z) {
            Frame frame2 = this.mFrame;
            if (frame2 != null) {
                frame2.release();
            }
        } else if (this.mFrame != null) {
            throw new RuntimeException("Attempting to push more than one frame on port: " + this + "!");
        }
        Frame retain = frame.retain();
        this.mFrame = retain;
        retain.markReadOnly();
        this.mPersistent = z;
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized Frame pullFrame() {
        Frame frame;
        frame = this.mFrame;
        if (frame == null) {
            throw new RuntimeException("No frame available to pull on port: " + this + "!");
        }
        if (this.mPersistent) {
            frame.retain();
        } else {
            this.mFrame = null;
        }
        return frame;
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized boolean hasFrame() {
        return this.mFrame != null;
    }

    @Override // android.filterfw.core.FilterPort
    public String toString() {
        return "input " + super.toString();
    }

    @Override // android.filterfw.core.InputPort
    public synchronized void transfer(FilterContext filterContext) {
        Frame frame = this.mFrame;
        if (frame != null) {
            checkFrameManager(frame, filterContext);
        }
    }
}
