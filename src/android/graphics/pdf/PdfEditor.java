package android.graphics.pdf;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import dalvik.system.CloseGuard;
import java.io.IOException;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class PdfEditor {
    private static final Object sPdfiumLock = new Object();
    private final CloseGuard mCloseGuard;
    private ParcelFileDescriptor mInput;
    private long mNativeDocument;
    private int mPageCount;

    private static native void nativeClose(long j);

    private static native int nativeGetPageCount(long j);

    private static native boolean nativeGetPageCropBox(long j, int i, Rect rect);

    private static native boolean nativeGetPageMediaBox(long j, int i, Rect rect);

    private static native void nativeGetPageSize(long j, int i, Point point);

    private static native long nativeOpen(int i, long j);

    private static native int nativeRemovePage(long j, int i);

    private static native boolean nativeScaleForPrinting(long j);

    private static native void nativeSetPageCropBox(long j, int i, Rect rect);

    private static native void nativeSetPageMediaBox(long j, int i, Rect rect);

    private static native void nativeSetTransformAndClip(long j, int i, long j2, int i2, int i3, int i4, int i5);

    private static native void nativeWrite(long j, int i);

    public PdfEditor(ParcelFileDescriptor parcelFileDescriptor) throws IOException, ErrnoException {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        if (parcelFileDescriptor == null) {
            throw new NullPointerException("input cannot be null");
        }
        try {
            Os.lseek(parcelFileDescriptor.getFileDescriptor(), 0L, OsConstants.SEEK_SET);
            long j = Os.fstat(parcelFileDescriptor.getFileDescriptor()).st_size;
            this.mInput = parcelFileDescriptor;
            synchronized (sPdfiumLock) {
                long jNativeOpen = nativeOpen(this.mInput.getFd(), j);
                this.mNativeDocument = jNativeOpen;
                try {
                    this.mPageCount = nativeGetPageCount(jNativeOpen);
                } catch (Throwable th) {
                    nativeClose(this.mNativeDocument);
                    this.mNativeDocument = 0L;
                    throw th;
                }
            }
            closeGuard.open("close");
        } catch (ErrnoException unused) {
            throw new IllegalArgumentException("file descriptor not seekable");
        }
    }

    public int getPageCount() {
        throwIfClosed();
        return this.mPageCount;
    }

    public void removePage(int i) {
        throwIfClosed();
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            this.mPageCount = nativeRemovePage(this.mNativeDocument, i);
        }
    }

    public void setTransformAndClip(int i, Matrix matrix, Rect rect) {
        throwIfClosed();
        throwIfPageNotInDocument(i);
        throwIfNotNullAndNotAfine(matrix);
        if (matrix == null) {
            matrix = Matrix.IDENTITY_MATRIX;
        }
        if (rect == null) {
            Point point = new Point();
            getPageSize(i, point);
            synchronized (sPdfiumLock) {
                nativeSetTransformAndClip(this.mNativeDocument, i, matrix.ni(), 0, 0, point.x, point.y);
            }
            return;
        }
        synchronized (sPdfiumLock) {
            nativeSetTransformAndClip(this.mNativeDocument, i, matrix.ni(), rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void getPageSize(int i, Point point) {
        throwIfClosed();
        throwIfOutSizeNull(point);
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            nativeGetPageSize(this.mNativeDocument, i, point);
        }
    }

    public boolean getPageMediaBox(int i, Rect rect) {
        boolean zNativeGetPageMediaBox;
        throwIfClosed();
        throwIfOutMediaBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            zNativeGetPageMediaBox = nativeGetPageMediaBox(this.mNativeDocument, i, rect);
        }
        return zNativeGetPageMediaBox;
    }

    public void setPageMediaBox(int i, Rect rect) {
        throwIfClosed();
        throwIfMediaBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            nativeSetPageMediaBox(this.mNativeDocument, i, rect);
        }
    }

    public boolean getPageCropBox(int i, Rect rect) {
        boolean zNativeGetPageCropBox;
        throwIfClosed();
        throwIfOutCropBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            zNativeGetPageCropBox = nativeGetPageCropBox(this.mNativeDocument, i, rect);
        }
        return zNativeGetPageCropBox;
    }

    public void setPageCropBox(int i, Rect rect) {
        throwIfClosed();
        throwIfCropBoxNull(rect);
        throwIfPageNotInDocument(i);
        synchronized (sPdfiumLock) {
            nativeSetPageCropBox(this.mNativeDocument, i, rect);
        }
    }

    public boolean shouldScaleForPrinting() {
        boolean zNativeScaleForPrinting;
        throwIfClosed();
        synchronized (sPdfiumLock) {
            zNativeScaleForPrinting = nativeScaleForPrinting(this.mNativeDocument);
        }
        return zNativeScaleForPrinting;
    }

    public void write(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        try {
            throwIfClosed();
            synchronized (sPdfiumLock) {
                nativeWrite(this.mNativeDocument, parcelFileDescriptor.getFd());
            }
        } finally {
            IoUtils.closeQuietly(parcelFileDescriptor);
        }
    }

    public void close() {
        throwIfClosed();
        doClose();
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            doClose();
        } finally {
            super.finalize();
        }
    }

    private void doClose() {
        if (this.mNativeDocument != 0) {
            synchronized (sPdfiumLock) {
                nativeClose(this.mNativeDocument);
            }
            this.mNativeDocument = 0L;
        }
        ParcelFileDescriptor parcelFileDescriptor = this.mInput;
        if (parcelFileDescriptor != null) {
            IoUtils.closeQuietly(parcelFileDescriptor);
            this.mInput = null;
        }
        this.mCloseGuard.close();
    }

    private void throwIfClosed() {
        if (this.mInput == null) {
            throw new IllegalStateException("Already closed");
        }
    }

    private void throwIfPageNotInDocument(int i) {
        if (i < 0 || i >= this.mPageCount) {
            throw new IllegalArgumentException("Invalid page index");
        }
    }

    private void throwIfNotNullAndNotAfine(Matrix matrix) {
        if (matrix != null && !matrix.isAffine()) {
            throw new IllegalStateException("Matrix must be afine");
        }
    }

    private void throwIfOutSizeNull(Point point) {
        if (point == null) {
            throw new NullPointerException("outSize cannot be null");
        }
    }

    private void throwIfOutMediaBoxNull(Rect rect) {
        if (rect == null) {
            throw new NullPointerException("outMediaBox cannot be null");
        }
    }

    private void throwIfMediaBoxNull(Rect rect) {
        if (rect == null) {
            throw new NullPointerException("mediaBox cannot be null");
        }
    }

    private void throwIfOutCropBoxNull(Rect rect) {
        if (rect == null) {
            throw new NullPointerException("outCropBox cannot be null");
        }
    }

    private void throwIfCropBoxNull(Rect rect) {
        if (rect == null) {
            throw new NullPointerException("cropBox cannot be null");
        }
    }
}
