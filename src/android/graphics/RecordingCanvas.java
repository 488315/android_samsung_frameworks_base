package android.graphics;

import android.graphics.Bitmap;
import android.os.SystemProperties;
import android.util.Pools;
import dalvik.annotation.optimization.CriticalNative;

/* loaded from: classes.dex */
public final class RecordingCanvas extends BaseRecordingCanvas {
    private static final int POOL_LIMIT = 25;
    private int mHeight;
    public RenderNode mNode;
    private int mWidth;
    public static final int MAX_BITMAP_SIZE = getPanelFrameSize();
    private static final Pools.SynchronizedPool<RecordingCanvas> sPool = new Pools.SynchronizedPool<>(25);

    @CriticalNative
    private static native long nCreateDisplayListCanvas(long j, int i, int i2);

    @CriticalNative
    private static native void nDrawCircle(long j, long j2, long j3, long j4, long j5);

    @CriticalNative
    private static native void nDrawRenderNode(long j, long j2);

    @CriticalNative
    private static native void nDrawRipple(long j, long j2, long j3, long j4, long j5, long j6, long j7, int i, long j8);

    @CriticalNative
    private static native void nDrawRoundRect(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8);

    @CriticalNative
    private static native void nDrawTextureLayer(long j, long j2);

    @CriticalNative
    private static native void nDrawWebViewFunctor(long j, int i);

    @CriticalNative
    private static native void nEnableZ(long j, boolean z);

    @CriticalNative
    private static native void nFinishRecording(long j, long j2);

    private static native int nGetMaximumTextureHeight();

    private static native int nGetMaximumTextureWidth();

    @CriticalNative
    private static native void nResetDisplayListCanvas(long j, long j2, int i, int i2);

    @Override // android.graphics.Canvas, android.graphics.BaseCanvas
    public boolean isHardwareAccelerated() {
        return true;
    }

    @Override // android.graphics.Canvas
    public boolean isOpaque() {
        return false;
    }

    @Override // android.graphics.Canvas
    public void setDensity(int i) {
    }

    private static int getPanelFrameSize() {
        return Math.max(SystemProperties.getInt("ro.hwui.max_texture_allocation_size", 157286400), 157286400);
    }

    static RecordingCanvas obtain(RenderNode renderNode, int i, int i2) {
        int i3;
        int i4;
        if (renderNode == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        RecordingCanvas recordingCanvasAcquire = sPool.acquire();
        if (recordingCanvasAcquire == null) {
            recordingCanvasAcquire = new RecordingCanvas(renderNode, i, i2);
            i3 = i;
            i4 = i2;
        } else {
            i3 = i;
            i4 = i2;
            nResetDisplayListCanvas(recordingCanvasAcquire.mNativeCanvasWrapper, renderNode.mNativeRenderNode, i3, i4);
        }
        recordingCanvasAcquire.mNode = renderNode;
        recordingCanvasAcquire.mWidth = i3;
        recordingCanvasAcquire.mHeight = i4;
        return recordingCanvasAcquire;
    }

    void recycle() {
        this.mNode = null;
        sPool.release(this);
    }

    void finishRecording(RenderNode renderNode) {
        nFinishRecording(this.mNativeCanvasWrapper, renderNode.mNativeRenderNode);
    }

    private RecordingCanvas(RenderNode renderNode, int i, int i2) {
        super(nCreateDisplayListCanvas(renderNode.mNativeRenderNode, i, i2));
        this.mDensity = 0;
    }

    @Override // android.graphics.Canvas
    public void setBitmap(Bitmap bitmap) {
        throw new UnsupportedOperationException();
    }

    @Override // android.graphics.Canvas
    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.Canvas
    public int getHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.Canvas
    public int getMaximumBitmapWidth() {
        return nGetMaximumTextureWidth();
    }

    @Override // android.graphics.Canvas
    public int getMaximumBitmapHeight() {
        return nGetMaximumTextureHeight();
    }

    @Override // android.graphics.Canvas
    public void enableZ() {
        nEnableZ(this.mNativeCanvasWrapper, true);
    }

    @Override // android.graphics.Canvas
    public void disableZ() {
        nEnableZ(this.mNativeCanvasWrapper, false);
    }

    public void drawWebViewFunctor(int i) {
        nDrawWebViewFunctor(this.mNativeCanvasWrapper, i);
    }

    @Override // android.graphics.Canvas
    public void drawRenderNode(RenderNode renderNode) {
        nDrawRenderNode(this.mNativeCanvasWrapper, renderNode.mNativeRenderNode);
    }

    public void drawTextureLayer(TextureLayer textureLayer) {
        nDrawTextureLayer(this.mNativeCanvasWrapper, textureLayer.getLayerHandle());
    }

    public void drawCircle(CanvasProperty<Float> canvasProperty, CanvasProperty<Float> canvasProperty2, CanvasProperty<Float> canvasProperty3, CanvasProperty<Paint> canvasProperty4) {
        nDrawCircle(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer());
    }

    public void drawRipple(CanvasProperty<Float> canvasProperty, CanvasProperty<Float> canvasProperty2, CanvasProperty<Float> canvasProperty3, CanvasProperty<Paint> canvasProperty4, CanvasProperty<Float> canvasProperty5, CanvasProperty<Float> canvasProperty6, int i, RuntimeShader runtimeShader) {
        nDrawRipple(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer(), canvasProperty5.getNativeContainer(), canvasProperty6.getNativeContainer(), i, runtimeShader.getNativeShaderBuilder());
    }

    public void drawRoundRect(CanvasProperty<Float> canvasProperty, CanvasProperty<Float> canvasProperty2, CanvasProperty<Float> canvasProperty3, CanvasProperty<Float> canvasProperty4, CanvasProperty<Float> canvasProperty5, CanvasProperty<Float> canvasProperty6, CanvasProperty<Paint> canvasProperty7) {
        nDrawRoundRect(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer(), canvasProperty5.getNativeContainer(), canvasProperty6.getNativeContainer(), canvasProperty7.getNativeContainer());
    }

    @Override // android.graphics.BaseCanvas
    protected void throwIfCannotDraw(Bitmap bitmap) {
        super.throwIfCannotDraw(bitmap);
        int byteCount = bitmap.getByteCount();
        if (bitmap.getConfig() == Bitmap.Config.HARDWARE || byteCount <= MAX_BITMAP_SIZE) {
            return;
        }
        throw new RuntimeException("Canvas: trying to draw too large(" + byteCount + "bytes) bitmap.");
    }
}
