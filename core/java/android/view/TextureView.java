package android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.TextureLayer;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.flags.Flags;

public class TextureView extends View {
    private static final String LOG_TAG = "TextureView";
    private Canvas mCanvas;
    private boolean mHadSurface;
    private long mLastFrameTimeMillis;
    private TextureLayer mLayer;
    private SurfaceTextureListener mListener;
    private final Object[] mLock;
    private final Matrix mMatrix;
    private boolean mMatrixChanged;
    private long mMinusTwoFrameIntervalMillis;
    private long mNativeWindow;
    private final Object[] mNativeWindowLock;
    private boolean mOpaque;
    private int mSaveCount;
    private SurfaceTexture mSurface;
    private boolean mUpdateLayer;
    private final SurfaceTexture.OnFrameAvailableListener mUpdateListener;
    private boolean mUpdateSurface;

    public interface SurfaceTextureListener {
        void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2);

        boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture);

        void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2);

        void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture);
    }

    private native void nCreateNativeWindow(SurfaceTexture surfaceTexture);

    private native void nDestroyNativeWindow();

    private static native boolean nLockCanvas(long j, Canvas canvas, Rect rect);

    private static native void nUnlockCanvasAndPost(long j, Canvas canvas);

    public TextureView(Context context) {
        super(context);
        this.mOpaque = true;
        this.mMatrix = new Matrix();
        this.mLock = new Object[0];
        this.mNativeWindowLock = new Object[0];
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameTimeMillis = 0L;
        this.mUpdateListener =
                new SurfaceTexture
                        .OnFrameAvailableListener() { // from class:
                                                      // android.view.TextureView$$ExternalSyntheticLambda0
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        TextureView.this.lambda$new$1(surfaceTexture);
                    }
                };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOpaque = true;
        this.mMatrix = new Matrix();
        this.mLock = new Object[0];
        this.mNativeWindowLock = new Object[0];
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameTimeMillis = 0L;
        this.mUpdateListener =
                new SurfaceTexture
                        .OnFrameAvailableListener() { // from class:
                                                      // android.view.TextureView$$ExternalSyntheticLambda0
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        TextureView.this.lambda$new$1(surfaceTexture);
                    }
                };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mOpaque = true;
        this.mMatrix = new Matrix();
        this.mLock = new Object[0];
        this.mNativeWindowLock = new Object[0];
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameTimeMillis = 0L;
        this.mUpdateListener =
                new SurfaceTexture
                        .OnFrameAvailableListener() { // from class:
                                                      // android.view.TextureView$$ExternalSyntheticLambda0
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        TextureView.this.lambda$new$1(surfaceTexture);
                    }
                };
        this.mRenderNode.setIsTextureView();
    }

    public TextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mOpaque = true;
        this.mMatrix = new Matrix();
        this.mLock = new Object[0];
        this.mNativeWindowLock = new Object[0];
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameTimeMillis = 0L;
        this.mUpdateListener =
                new SurfaceTexture
                        .OnFrameAvailableListener() { // from class:
                                                      // android.view.TextureView$$ExternalSyntheticLambda0
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        TextureView.this.lambda$new$1(surfaceTexture);
                    }
                };
        this.mRenderNode.setIsTextureView();
    }

    @Override // android.view.View
    public boolean isOpaque() {
        return this.mOpaque;
    }

    public void setOpaque(boolean opaque) {
        if (opaque != this.mOpaque) {
            this.mOpaque = opaque;
            if (this.mLayer != null) {
                updateLayerAndInvalidate();
            }
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isHardwareAccelerated()) {
            Log.w(
                    LOG_TAG,
                    "A TextureView or a subclass can only be used with hardware acceleration"
                        + " enabled.");
        }
        if (this.mHadSurface) {
            invalidate(true);
            this.mHadSurface = false;
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        destroyHardwareLayer();
        releaseSurfaceTexture();
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        destroyHardwareLayer();
    }

    private void destroyHardwareLayer() {
        if (this.mLayer != null) {
            this.mLayer.detachSurfaceTexture();
            this.mLayer.close();
            this.mLayer = null;
            this.mMatrixChanged = true;
        }
    }

    private void releaseSurfaceTexture() {
        if (this.mSurface != null) {
            boolean shouldRelease = true;
            if (this.mListener != null) {
                shouldRelease = this.mListener.onSurfaceTextureDestroyed(this.mSurface);
            }
            synchronized (this.mNativeWindowLock) {
                nDestroyNativeWindow();
            }
            if (shouldRelease) {
                this.mSurface.release();
            }
            this.mSurface = null;
            this.mHadSurface = true;
        }
    }

    @Override // android.view.View
    public void setLayerType(int layerType, Paint paint) {
        setLayerPaint(paint);
    }

    @Override // android.view.View
    public void setLayerPaint(Paint paint) {
        if (paint != this.mLayerPaint) {
            this.mLayerPaint = paint;
            invalidate();
        }
    }

    @Override // android.view.View
    public int getLayerType() {
        return 2;
    }

    @Override // android.view.View
    public void buildLayer() {}

    @Override // android.view.View
    public void setForeground(Drawable foreground) {
        if (foreground != null && !sTextureViewIgnoresDrawableSetters) {
            throw new UnsupportedOperationException(
                    "TextureView doesn't support displaying a foreground drawable");
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable background) {
        if (background != null && !sTextureViewIgnoresDrawableSetters) {
            throw new UnsupportedOperationException(
                    "TextureView doesn't support displaying a background drawable");
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        this.mPrivateFlags = (this.mPrivateFlags & (-2097153)) | 32;
        if (canvas.isHardwareAccelerated()) {
            RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
            TextureLayer layer = getTextureLayer();
            if (layer != null) {
                Trace.traceBegin(8L, "TextureView#draw()");
                applyUpdate();
                applyTransformMatrix();
                this.mLayer.setLayerPaint(this.mLayerPaint);
                recordingCanvas.drawTextureLayer(layer);
                Trace.traceEnd(8L);
            }
        }
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {}

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mSurface != null) {
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
            updateLayer();
            if (this.mListener != null) {
                this.mListener.onSurfaceTextureSizeChanged(this.mSurface, getWidth(), getHeight());
            }
        }
    }

    TextureLayer getTextureLayer() {
        if (this.mLayer == null) {
            if (this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null) {
                return null;
            }
            this.mLayer = this.mAttachInfo.mThreadedRenderer.createTextureLayer();
            boolean createNewSurface = this.mSurface == null;
            if (createNewSurface) {
                this.mSurface = new SurfaceTexture(false);
                nCreateNativeWindow(this.mSurface);
            }
            this.mLayer.setSurfaceTexture(this.mSurface);
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
            this.mSurface.setOnFrameAvailableListener(
                    this.mUpdateListener, this.mAttachInfo.mHandler);
            if (Flags.toolkitSetFrameRateReadOnly()) {
                this.mSurface.setOnSetFrameRateListener(
                        new SurfaceTexture
                                .OnSetFrameRateListener() { // from class:
                                                            // android.view.TextureView$$ExternalSyntheticLambda1
                            @Override // android.graphics.SurfaceTexture.OnSetFrameRateListener
                            public final void onSetFrameRate(
                                    SurfaceTexture surfaceTexture, float f, int i, int i2) {
                                TextureView.this.lambda$getTextureLayer$0(surfaceTexture, f, i, i2);
                            }
                        },
                        this.mAttachInfo.mHandler);
            }
            if (this.mListener != null && createNewSurface) {
                this.mListener.onSurfaceTextureAvailable(this.mSurface, getWidth(), getHeight());
            }
            this.mLayer.setLayerPaint(this.mLayerPaint);
        }
        if (this.mUpdateSurface) {
            this.mUpdateSurface = false;
            updateLayer();
            this.mMatrixChanged = true;
            this.mLayer.setSurfaceTexture(this.mSurface);
            this.mSurface.setDefaultBufferSize(getWidth(), getHeight());
        }
        return this.mLayer;
    }

    public /* synthetic */ void lambda$getTextureLayer$0(
            SurfaceTexture surfaceTexture, float frameRate, int compatibility, int strategy) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instant(8L, "setFrameRate: " + frameRate);
        }
        setRequestedFrameRate(frameRate);
        this.mFrameRateCompatibility = compatibility;
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (this.mSurface != null) {
            if (visibility == 0) {
                if (this.mLayer != null) {
                    this.mSurface.setOnFrameAvailableListener(
                            this.mUpdateListener, this.mAttachInfo.mHandler);
                }
                updateLayerAndInvalidate();
                return;
            }
            this.mSurface.setOnFrameAvailableListener(null);
        }
    }

    private void updateLayer() {
        synchronized (this.mLock) {
            this.mUpdateLayer = true;
        }
    }

    private void updateLayerAndInvalidate() {
        synchronized (this.mLock) {
            this.mUpdateLayer = true;
        }
        invalidate();
    }

    private void applyUpdate() {
        if (this.mLayer == null) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mUpdateLayer) {
                this.mUpdateLayer = false;
                this.mLayer.prepare(getWidth(), getHeight(), this.mOpaque);
                this.mLayer.updateSurfaceTexture();
                if (this.mListener != null) {
                    this.mListener.onSurfaceTextureUpdated(this.mSurface);
                }
            }
        }
    }

    public void setTransform(Matrix transform) {
        this.mMatrix.set(transform);
        this.mMatrixChanged = true;
        invalidateParentIfNeeded();
    }

    public Matrix getTransform(Matrix transform) {
        if (transform == null) {
            transform = new Matrix();
        }
        transform.set(this.mMatrix);
        return transform;
    }

    private void applyTransformMatrix() {
        if (this.mMatrixChanged && this.mLayer != null) {
            this.mLayer.setTransform(this.mMatrix);
            this.mMatrixChanged = false;
        }
    }

    public Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }

    public Bitmap getBitmap(int width, int height) {
        if (isAvailable() && width > 0 && height > 0) {
            return getBitmap(
                    Bitmap.createBitmap(
                            getResources().getDisplayMetrics(),
                            width,
                            height,
                            Bitmap.Config.ARGB_8888));
        }
        return null;
    }

    public Bitmap getBitmap(Bitmap bitmap) {
        if (bitmap != null && isAvailable()) {
            applyUpdate();
            applyTransformMatrix();
            if (this.mLayer == null && this.mUpdateSurface) {
                getTextureLayer();
            }
            if (this.mLayer != null) {
                this.mLayer.copyInto(bitmap);
            }
        }
        return bitmap;
    }

    public boolean isAvailable() {
        return this.mSurface != null;
    }

    public Canvas lockCanvas() {
        return lockCanvas(null);
    }

    public Canvas lockCanvas(Rect dirty) {
        if (!isAvailable()) {
            return null;
        }
        if (this.mCanvas == null) {
            this.mCanvas = new Canvas();
        }
        synchronized (this.mNativeWindowLock) {
            if (!nLockCanvas(this.mNativeWindow, this.mCanvas, dirty)) {
                return null;
            }
            this.mSaveCount = this.mCanvas.save();
            return this.mCanvas;
        }
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        if (this.mCanvas != null && canvas == this.mCanvas) {
            canvas.restoreToCount(this.mSaveCount);
            this.mSaveCount = 0;
            synchronized (this.mNativeWindowLock) {
                nUnlockCanvasAndPost(this.mNativeWindow, this.mCanvas);
            }
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurface;
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new NullPointerException("surfaceTexture must not be null");
        }
        if (surfaceTexture == this.mSurface) {
            throw new IllegalArgumentException(
                    "Trying to setSurfaceTexture to the same SurfaceTexture that's already set.");
        }
        if (surfaceTexture.isReleased()) {
            throw new IllegalArgumentException(
                    "Cannot setSurfaceTexture to a released SurfaceTexture");
        }
        if (this.mSurface != null) {
            nDestroyNativeWindow();
            this.mSurface.release();
        }
        this.mSurface = surfaceTexture;
        nCreateNativeWindow(this.mSurface);
        if ((this.mViewFlags & 12) == 0 && this.mLayer != null) {
            this.mSurface.setOnFrameAvailableListener(
                    this.mUpdateListener, this.mAttachInfo.mHandler);
        }
        this.mUpdateSurface = true;
        invalidateParentIfNeeded();
    }

    public SurfaceTextureListener getSurfaceTextureListener() {
        return this.mListener;
    }

    public void setSurfaceTextureListener(SurfaceTextureListener listener) {
        this.mListener = listener;
    }

    @Override // android.view.View
    protected int calculateFrameRateCategory() {
        long now = getDrawingTime();
        if (this.mMinusTwoFrameIntervalMillis > 15 && now - this.mLastFrameTimeMillis > 15) {
            return 3;
        }
        return super.calculateFrameRateCategory();
    }

    @Override // android.view.View
    protected void votePreferredFrameRate() {
        super.votePreferredFrameRate();
        long now = getDrawingTime();
        this.mMinusTwoFrameIntervalMillis = now - this.mLastFrameTimeMillis;
        this.mLastFrameTimeMillis = now;
    }

    public /* synthetic */ void lambda$new$1(SurfaceTexture surfaceTexture) {
        updateLayer();
        invalidate();
    }
}
