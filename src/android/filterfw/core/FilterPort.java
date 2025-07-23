package android.filterfw.core;

import android.util.Log;

/* loaded from: classes.dex */
public abstract class FilterPort {
    private static final String TAG = "FilterPort";
    protected Filter mFilter;
    protected String mName;
    protected FrameFormat mPortFormat;
    protected boolean mIsBlocking = true;
    protected boolean mIsOpen = false;
    protected boolean mChecksType = false;
    private boolean mLogVerbose = Log.isLoggable(TAG, 2);

    public abstract void clear();

    public abstract boolean filterMustClose();

    public abstract boolean hasFrame();

    public abstract boolean isReady();

    public abstract Frame pullFrame();

    public abstract void pushFrame(Frame frame);

    public abstract void setFrame(Frame frame);

    public FilterPort(Filter filter, String str) {
        this.mName = str;
        this.mFilter = filter;
    }

    public boolean isAttached() {
        return this.mFilter != null;
    }

    public FrameFormat getPortFormat() {
        return this.mPortFormat;
    }

    public void setPortFormat(FrameFormat frameFormat) {
        this.mPortFormat = frameFormat;
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public String getName() {
        return this.mName;
    }

    public void setBlocking(boolean z) {
        this.mIsBlocking = z;
    }

    public void setChecksType(boolean z) {
        this.mChecksType = z;
    }

    public void open() {
        if (!this.mIsOpen && this.mLogVerbose) {
            Log.v(TAG, "Opening " + this);
        }
        this.mIsOpen = true;
    }

    public void close() {
        if (this.mIsOpen && this.mLogVerbose) {
            Log.v(TAG, "Closing " + this);
        }
        this.mIsOpen = false;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public boolean isBlocking() {
        return this.mIsBlocking;
    }

    public String toString() {
        return "port '" + this.mName + "' of " + this.mFilter;
    }

    protected void assertPortIsOpen() {
        if (isOpen()) {
            return;
        }
        throw new RuntimeException("Illegal operation on closed " + this + "!");
    }

    protected void checkFrameType(Frame frame, boolean z) {
        if ((!this.mChecksType && !z) || this.mPortFormat == null || frame.getFormat().isCompatibleWith(this.mPortFormat)) {
            return;
        }
        throw new RuntimeException("Frame passed to " + this + " is of incorrect type! Expected " + this.mPortFormat + " but got " + frame.getFormat());
    }

    protected void checkFrameManager(Frame frame, FilterContext filterContext) {
        if (frame.getFrameManager() == null || frame.getFrameManager() == filterContext.getFrameManager()) {
            return;
        }
        throw new RuntimeException("Frame " + frame + " is managed by foreign FrameManager! ");
    }
}
