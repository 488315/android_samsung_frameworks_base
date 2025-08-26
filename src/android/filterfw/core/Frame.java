package android.filterfw.core;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class Frame {
    public static final int NO_BINDING = 0;
    public static final long TIMESTAMP_NOT_SET = -2;
    public static final long TIMESTAMP_UNKNOWN = -1;
    private long mBindingId;
    private int mBindingType;
    private FrameFormat mFormat;
    private FrameManager mFrameManager;
    private boolean mReadOnly;
    private int mRefCount;
    private boolean mReusable;
    private long mTimestamp;

    public abstract Bitmap getBitmap();

    public abstract ByteBuffer getData();

    public abstract float[] getFloats();

    public abstract int[] getInts();

    public abstract Object getObjectValue();

    protected abstract boolean hasNativeAllocation();

    protected void onFrameFetch() {
    }

    protected void onFrameStore() {
    }

    protected abstract void releaseNativeAllocation();

    protected boolean requestResize(int[] iArr) {
        return false;
    }

    public abstract void setBitmap(Bitmap bitmap);

    public abstract void setData(ByteBuffer byteBuffer, int i, int i2);

    public abstract void setFloats(float[] fArr);

    public abstract void setInts(int[] iArr);

    Frame(FrameFormat frameFormat, FrameManager frameManager) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
    }

    Frame(FrameFormat frameFormat, FrameManager frameManager, int i, long j) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
        this.mBindingType = i;
        this.mBindingId = j;
    }

    public FrameFormat getFormat() {
        return this.mFormat;
    }

    public int getCapacity() {
        return getFormat().getSize();
    }

    public boolean isReadOnly() {
        return this.mReadOnly;
    }

    public int getBindingType() {
        return this.mBindingType;
    }

    public long getBindingId() {
        return this.mBindingId;
    }

    public void setObjectValue(Object obj) {
        assertFrameMutable();
        if (obj instanceof int[]) {
            setInts((int[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            setFloats((float[]) obj);
            return;
        }
        if (obj instanceof ByteBuffer) {
            setData((ByteBuffer) obj);
        } else if (obj instanceof Bitmap) {
            setBitmap((Bitmap) obj);
        } else {
            setGenericObjectValue(obj);
        }
    }

    public void setData(ByteBuffer byteBuffer) {
        setData(byteBuffer, 0, byteBuffer.limit());
    }

    public void setData(byte[] bArr, int i, int i2) {
        setData(ByteBuffer.wrap(bArr, i, i2));
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setDataFromFrame(Frame frame) {
        setData(frame.getData());
    }

    public int getRefCount() {
        return this.mRefCount;
    }

    public Frame release() {
        FrameManager frameManager = this.mFrameManager;
        return frameManager != null ? frameManager.releaseFrame(this) : this;
    }

    public Frame retain() {
        FrameManager frameManager = this.mFrameManager;
        return frameManager != null ? frameManager.retainFrame(this) : this;
    }

    public FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    protected void assertFrameMutable() {
        if (isReadOnly()) {
            throw new RuntimeException("Attempting to modify read-only frame!");
        }
    }

    protected void setReusable(boolean z) {
        this.mReusable = z;
    }

    protected void setFormat(FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
    }

    protected void setGenericObjectValue(Object obj) {
        throw new RuntimeException("Cannot set object value of unsupported type: " + obj.getClass());
    }

    protected static Bitmap convertBitmapToRGBA(Bitmap bitmap) {
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            return bitmap;
        }
        Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        if (bitmapCopy == null) {
            throw new RuntimeException("Error converting bitmap to RGBA!");
        }
        if (bitmapCopy.getRowBytes() == bitmapCopy.getWidth() * 4) {
            return bitmapCopy;
        }
        throw new RuntimeException("Unsupported row byte count in bitmap!");
    }

    protected void reset(FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
        this.mReadOnly = false;
        this.mRefCount = 1;
    }

    final int incRefCount() {
        int i = this.mRefCount + 1;
        this.mRefCount = i;
        return i;
    }

    final int decRefCount() {
        int i = this.mRefCount - 1;
        this.mRefCount = i;
        return i;
    }

    final boolean isReusable() {
        return this.mReusable;
    }

    final void markReadOnly() {
        this.mReadOnly = true;
    }
}
