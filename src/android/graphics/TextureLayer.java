package android.graphics;

import com.android.internal.util.VirtualRefBasePtr;

/* loaded from: classes.dex */
public final class TextureLayer implements AutoCloseable {
    private VirtualRefBasePtr mFinalizer;
    private HardwareRenderer mRenderer;

    private static native boolean nPrepare(long j, int i, int i2, boolean z);

    private static native void nSetLayerPaint(long j, long j2);

    private static native void nSetSurfaceTexture(long j, SurfaceTexture surfaceTexture);

    private static native void nSetTransform(long j, long j2);

    private static native void nUpdateSurfaceTexture(long j);

    private TextureLayer(HardwareRenderer hardwareRenderer, long j) {
        if (hardwareRenderer == null || j == 0) {
            throw new IllegalArgumentException("Either hardware renderer: " + hardwareRenderer + " or deferredUpdater: " + j + " is invalid");
        }
        this.mRenderer = hardwareRenderer;
        this.mFinalizer = new VirtualRefBasePtr(j);
    }

    public void setLayerPaint(Paint paint) {
        nSetLayerPaint(this.mFinalizer.get(), paint != null ? paint.getNativeInstance() : 0L);
        this.mRenderer.pushLayerUpdate(this);
    }

    private boolean isValid() {
        VirtualRefBasePtr virtualRefBasePtr = this.mFinalizer;
        return (virtualRefBasePtr == null || virtualRefBasePtr.get() == 0) ? false : true;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (isValid()) {
            this.mRenderer.onLayerDestroyed(this);
            this.mRenderer = null;
            this.mFinalizer.release();
            this.mFinalizer = null;
        }
    }

    long getDeferredLayerUpdater() {
        return this.mFinalizer.get();
    }

    public boolean copyInto(Bitmap bitmap) {
        return this.mRenderer.copyLayerInto(this, bitmap);
    }

    public boolean prepare(int i, int i2, boolean z) {
        return nPrepare(this.mFinalizer.get(), i, i2, z);
    }

    public void setTransform(Matrix matrix) {
        nSetTransform(this.mFinalizer.get(), matrix.ni());
        this.mRenderer.pushLayerUpdate(this);
    }

    public void detachSurfaceTexture() {
        this.mRenderer.detachSurfaceTexture(this.mFinalizer.get());
    }

    long getLayerHandle() {
        return this.mFinalizer.get();
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        nSetSurfaceTexture(this.mFinalizer.get(), surfaceTexture);
        this.mRenderer.pushLayerUpdate(this);
    }

    public void updateSurfaceTexture() {
        nUpdateSurfaceTexture(this.mFinalizer.get());
        this.mRenderer.pushLayerUpdate(this);
    }

    static TextureLayer adoptTextureLayer(HardwareRenderer hardwareRenderer, long j) {
        return new TextureLayer(hardwareRenderer, j);
    }
}
