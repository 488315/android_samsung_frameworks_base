package android.media;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;

import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public abstract class Image implements AutoCloseable {
    private Rect mCropRect;
    protected boolean mIsImageValid = false;
    private int mDataSpace = 0;

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract int getFormat();

    public abstract int getHeight();

    public abstract Plane[] getPlanes();

    public abstract int getScalingMode();

    public abstract long getTimestamp();

    public abstract int getTransform();

    public abstract int getWidth();

    protected Image() {}

    protected void throwISEIfImageIsInvalid() {
        if (!this.mIsImageValid) {
            throw new IllegalStateException("Image is already closed");
        }
    }

    public SyncFence getFence() throws IOException {
        return SyncFence.createEmpty();
    }

    public int getPlaneCount() {
        return -1;
    }

    public HardwareBuffer getHardwareBuffer() {
        throwISEIfImageIsInvalid();
        return null;
    }

    public void setTimestamp(long timestamp) {
        throwISEIfImageIsInvalid();
    }

    public void setFence(SyncFence fence) throws IOException {
        throwISEIfImageIsInvalid();
    }

    public int getDataSpace() {
        throwISEIfImageIsInvalid();
        return this.mDataSpace;
    }

    public void setDataSpace(int dataSpace) {
        throwISEIfImageIsInvalid();
        this.mDataSpace = dataSpace;
    }

    public Rect getCropRect() {
        throwISEIfImageIsInvalid();
        if (this.mCropRect == null) {
            return new Rect(0, 0, getWidth(), getHeight());
        }
        return new Rect(this.mCropRect);
    }

    public void setCropRect(Rect cropRect) {
        throwISEIfImageIsInvalid();
        if (cropRect != null) {
            cropRect = new Rect(cropRect);
            if (!cropRect.intersect(0, 0, getWidth(), getHeight())) {
                cropRect.setEmpty();
            }
        }
        this.mCropRect = cropRect;
    }

    public boolean isAttachable() {
        throwISEIfImageIsInvalid();
        return false;
    }

    Object getOwner() {
        throwISEIfImageIsInvalid();
        return null;
    }

    long getNativeContext() {
        throwISEIfImageIsInvalid();
        return 0L;
    }

    public abstract static class Plane {
        public abstract ByteBuffer getBuffer();

        public abstract int getPixelStride();

        public abstract int getRowStride();

        protected Plane() {}
    }
}
