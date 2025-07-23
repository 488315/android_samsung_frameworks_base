package android.graphics;

import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class Picture {
    private static final int WORKING_STREAM_STORAGE = 16384;
    private long mNativePicture;
    private PictureCanvas mRecordingCanvas;
    private boolean mRequiresHwAcceleration;

    private static native long nativeBeginRecording(long j, int i, int i2);

    private static native long nativeConstructor(long j);

    private static native long nativeCreateFromStream(InputStream inputStream, byte[] bArr);

    private static native void nativeDestructor(long j);

    private static native void nativeDraw(long j, long j2);

    private static native void nativeEndRecording(long j);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeWriteToStream(long j, OutputStream outputStream, byte[] bArr);

    public Picture() {
        this(nativeConstructor(0L));
    }

    public Picture(Picture picture) {
        this(nativeConstructor(picture != null ? picture.mNativePicture : 0L));
    }

    public Picture(long j) {
        if (j == 0) {
            throw new IllegalArgumentException();
        }
        this.mNativePicture = j;
    }

    public void close() {
        long j = this.mNativePicture;
        if (j != 0) {
            nativeDestructor(j);
            this.mNativePicture = 0L;
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void verifyValid() {
        if (this.mNativePicture == 0) {
            throw new IllegalStateException("Picture is destroyed");
        }
    }

    public Canvas beginRecording(int i, int i2) {
        verifyValid();
        if (this.mRecordingCanvas != null) {
            throw new IllegalStateException("Picture already recording, must call #endRecording()");
        }
        PictureCanvas pictureCanvas = new PictureCanvas(this, nativeBeginRecording(this.mNativePicture, i, i2));
        this.mRecordingCanvas = pictureCanvas;
        this.mRequiresHwAcceleration = false;
        return pictureCanvas;
    }

    public void endRecording() {
        verifyValid();
        PictureCanvas pictureCanvas = this.mRecordingCanvas;
        if (pictureCanvas != null) {
            this.mRequiresHwAcceleration = pictureCanvas.mUsesHwFeature;
            this.mRecordingCanvas = null;
            nativeEndRecording(this.mNativePicture);
        }
    }

    public int getWidth() {
        verifyValid();
        return nativeGetWidth(this.mNativePicture);
    }

    public int getHeight() {
        verifyValid();
        return nativeGetHeight(this.mNativePicture);
    }

    public boolean requiresHardwareAcceleration() {
        verifyValid();
        return this.mRequiresHwAcceleration;
    }

    public void draw(Canvas canvas) {
        verifyValid();
        if (this.mRecordingCanvas != null) {
            endRecording();
        }
        if (this.mRequiresHwAcceleration && !canvas.isHardwareAccelerated() && canvas.onHwFeatureInSwMode()) {
            throw new IllegalArgumentException("Software rendering not supported for Pictures that require hardware acceleration");
        }
        nativeDraw(canvas.getNativeCanvasWrapper(), this.mNativePicture);
    }

    @Deprecated
    public static Picture createFromStream(InputStream inputStream) {
        return new Picture(nativeCreateFromStream(inputStream, new byte[16384]));
    }

    @Deprecated
    public void writeToStream(OutputStream outputStream) {
        verifyValid();
        if (outputStream == null) {
            throw new IllegalArgumentException("stream cannot be null");
        }
        if (!nativeWriteToStream(this.mNativePicture, outputStream, new byte[16384])) {
            throw new RuntimeException();
        }
    }

    private static class PictureCanvas extends Canvas {
        private final Picture mPicture;
        boolean mUsesHwFeature;

        public PictureCanvas(Picture picture, long j) {
            super(j);
            this.mPicture = picture;
            this.mDensity = 0;
        }

        @Override // android.graphics.Canvas
        public void setBitmap(Bitmap bitmap) {
            throw new RuntimeException("Cannot call setBitmap on a picture canvas");
        }

        @Override // android.graphics.Canvas
        public void drawPicture(Picture picture) {
            if (this.mPicture == picture) {
                throw new RuntimeException("Cannot draw a picture into its recording canvas");
            }
            super.drawPicture(picture);
        }

        @Override // android.graphics.BaseCanvas
        protected boolean onHwFeatureInSwMode() {
            this.mUsesHwFeature = true;
            return false;
        }
    }
}
