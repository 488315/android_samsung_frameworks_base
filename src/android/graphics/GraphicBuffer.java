package android.graphics;

import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class GraphicBuffer implements Parcelable {
    public static final Parcelable.Creator<GraphicBuffer> CREATOR = new Parcelable.Creator<GraphicBuffer>() { // from class: android.graphics.GraphicBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GraphicBuffer createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            long nReadGraphicBufferFromParcel = GraphicBuffer.nReadGraphicBufferFromParcel(parcel);
            if (nReadGraphicBufferFromParcel != 0) {
                return new GraphicBuffer(readInt, readInt2, readInt3, readInt4, nReadGraphicBufferFromParcel);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GraphicBuffer[] newArray(int i) {
            return new GraphicBuffer[i];
        }
    };
    public static final int USAGE_HW_2D = 1024;
    public static final int USAGE_HW_COMPOSER = 2048;
    public static final int USAGE_HW_MASK = 466688;
    public static final int USAGE_HW_RENDER = 512;
    public static final int USAGE_HW_TEXTURE = 256;
    public static final int USAGE_HW_VIDEO_ENCODER = 65536;
    public static final int USAGE_PROTECTED = 16384;
    public static final int USAGE_SOFTWARE_MASK = 255;
    public static final int USAGE_SW_READ_MASK = 15;
    public static final int USAGE_SW_READ_NEVER = 0;
    public static final int USAGE_SW_READ_OFTEN = 3;
    public static final int USAGE_SW_READ_RARELY = 2;
    public static final int USAGE_SW_WRITE_MASK = 240;
    public static final int USAGE_SW_WRITE_NEVER = 0;
    public static final int USAGE_SW_WRITE_OFTEN = 48;
    public static final int USAGE_SW_WRITE_RARELY = 32;
    private Canvas mCanvas;
    private boolean mDestroyed;
    private final int mFormat;
    private final int mHeight;
    private long mNativeObject;
    private int mSaveCount;
    private final int mUsage;
    private final int mWidth;

    private static native GraphicBuffer nCreateFromHardwareBuffer(HardwareBuffer hardwareBuffer);

    private static native long nCreateGraphicBuffer(int i, int i2, int i3, int i4);

    private static native void nDestroyGraphicBuffer(long j);

    private static native boolean nLockCanvas(long j, Canvas canvas, Rect rect);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nReadGraphicBufferFromParcel(Parcel parcel);

    private static native boolean nUnlockCanvasAndPost(long j, Canvas canvas);

    private static native void nWriteGraphicBufferToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static GraphicBuffer create(int i, int i2, int i3, int i4) {
        long nCreateGraphicBuffer = nCreateGraphicBuffer(i, i2, i3, i4);
        if (nCreateGraphicBuffer != 0) {
            return new GraphicBuffer(i, i2, i3, i4, nCreateGraphicBuffer);
        }
        return null;
    }

    private GraphicBuffer(int i, int i2, int i3, int i4, long j) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mUsage = i4;
        this.mNativeObject = j;
    }

    public static final GraphicBuffer createFromHardwareBuffer(HardwareBuffer hardwareBuffer) {
        return nCreateFromHardwareBuffer(hardwareBuffer);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getUsage() {
        return this.mUsage;
    }

    public Canvas lockCanvas() {
        return lockCanvas(null);
    }

    public Canvas lockCanvas(Rect rect) {
        if (this.mDestroyed) {
            return null;
        }
        if (this.mCanvas == null) {
            this.mCanvas = new Canvas();
        }
        if (!nLockCanvas(this.mNativeObject, this.mCanvas, rect)) {
            return null;
        }
        this.mSaveCount = this.mCanvas.save();
        return this.mCanvas;
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        Canvas canvas2;
        if (this.mDestroyed || (canvas2 = this.mCanvas) == null || canvas != canvas2) {
            return;
        }
        canvas.restoreToCount(this.mSaveCount);
        this.mSaveCount = 0;
        nUnlockCanvasAndPost(this.mNativeObject, this.mCanvas);
    }

    public void destroy() {
        if (this.mDestroyed) {
            return;
        }
        this.mDestroyed = true;
        nDestroyGraphicBuffer(this.mNativeObject);
        this.mNativeObject = 0L;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    protected void finalize() throws Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mDestroyed) {
            throw new IllegalStateException("This GraphicBuffer has been destroyed and cannot be written to a parcel.");
        }
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mFormat);
        parcel.writeInt(this.mUsage);
        nWriteGraphicBufferToParcel(this.mNativeObject, parcel);
    }
}
