package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class AtomicFileOutputStream extends FileOutputStream implements AutoCloseable {
    private static final String TAG = "AtomicFileOutputStream";
    private boolean mClosed;
    private final AtomicFile mFile;
    private final FileOutputStream mOutStream;
    private boolean mWritingSuccessful;

    public AtomicFileOutputStream(AtomicFile atomicFile) throws IOException {
        this(atomicFile, atomicFile.startWrite());
    }

    private AtomicFileOutputStream(AtomicFile atomicFile, FileOutputStream fileOutputStream) throws IOException {
        super(fileOutputStream.getFD());
        this.mFile = atomicFile;
        this.mOutStream = fileOutputStream;
    }

    public void markSuccess() {
        if (this.mWritingSuccessful) {
            throw new IllegalStateException("AtomicFileOutputStream success is already marked");
        }
        this.mWritingSuccessful = true;
    }

    @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        synchronized (this.mOutStream) {
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            if (this.mWritingSuccessful) {
                this.mFile.finishWrite(this.mOutStream);
            } else {
                this.mFile.failWrite(this.mOutStream);
            }
        }
    }

    public String toString() {
        return "AtomicFileOutputStream[mFile=" + this.mFile + ", mWritingSuccessful=" + this.mWritingSuccessful + ", mClosed=" + this.mClosed + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
