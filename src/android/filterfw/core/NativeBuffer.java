package android.filterfw.core;

/* loaded from: classes.dex */
public class NativeBuffer {
    private Frame mAttachedFrame;
    private long mDataPointer;
    private boolean mOwnsData;
    private int mRefCount;
    private int mSize;

    private native boolean allocate(int i);

    private native boolean deallocate(boolean z);

    private native boolean nativeCopyTo(NativeBuffer nativeBuffer);

    public int getElementSize() {
        return 1;
    }

    public NativeBuffer() {
        this.mDataPointer = 0L;
        this.mSize = 0;
        this.mOwnsData = false;
        this.mRefCount = 1;
    }

    public NativeBuffer(int i) {
        this.mDataPointer = 0L;
        this.mSize = 0;
        this.mOwnsData = false;
        this.mRefCount = 1;
        allocate(i * getElementSize());
        this.mOwnsData = true;
    }

    public NativeBuffer mutableCopy() {
        try {
            NativeBuffer nativeBuffer = (NativeBuffer) getClass().newInstance();
            if (this.mSize <= 0 || nativeCopyTo(nativeBuffer)) {
                return nativeBuffer;
            }
            throw new RuntimeException("Failed to copy NativeBuffer to mutable instance!");
        } catch (Exception unused) {
            throw new RuntimeException("Unable to allocate a copy of " + getClass() + "! Make sure the class has a default constructor!");
        }
    }

    public int size() {
        return this.mSize;
    }

    public int count() {
        if (this.mDataPointer != 0) {
            return this.mSize / getElementSize();
        }
        return 0;
    }

    public NativeBuffer retain() {
        Frame frame = this.mAttachedFrame;
        if (frame != null) {
            frame.retain();
            return this;
        }
        if (this.mOwnsData) {
            this.mRefCount++;
        }
        return this;
    }

    public NativeBuffer release() {
        Frame frame = this.mAttachedFrame;
        if (frame != null) {
            if (frame.release() != null) {
                return this;
            }
        } else {
            if (!this.mOwnsData) {
                return this;
            }
            int i = this.mRefCount - 1;
            this.mRefCount = i;
            if (i != 0) {
                return this;
            }
        }
        deallocate(this.mOwnsData);
        return null;
    }

    public boolean isReadOnly() {
        Frame frame = this.mAttachedFrame;
        if (frame != null) {
            return frame.isReadOnly();
        }
        return false;
    }

    static {
        System.loadLibrary("filterfw");
    }

    void attachToFrame(Frame frame) {
        this.mAttachedFrame = frame;
    }

    protected void assertReadable() {
        Frame frame;
        if (this.mDataPointer == 0 || this.mSize == 0 || !((frame = this.mAttachedFrame) == null || frame.hasNativeAllocation())) {
            throw new NullPointerException("Attempting to read from null data frame!");
        }
    }

    protected void assertWritable() {
        if (isReadOnly()) {
            throw new RuntimeException("Attempting to modify read-only native (structured) data!");
        }
    }
}
