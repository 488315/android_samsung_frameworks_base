package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.BufferedOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class AtomicFileBufferedOutputStream extends BufferedOutputStream implements AutoCloseable {
    private static final String TAG = "AtomicFileBufferedOutputStream";
    private final AtomicFileOutputStream mAtomicFileOutputStream;

    public AtomicFileBufferedOutputStream(AtomicFile atomicFile) throws IOException {
        this(new AtomicFileOutputStream(atomicFile));
    }

    private AtomicFileBufferedOutputStream(AtomicFileOutputStream atomicFileOutputStream) {
        super(atomicFileOutputStream);
        this.mAtomicFileOutputStream = atomicFileOutputStream;
    }

    public AtomicFileBufferedOutputStream(AtomicFile atomicFile, int i) throws IOException {
        this(new AtomicFileOutputStream(atomicFile), i);
    }

    private AtomicFileBufferedOutputStream(AtomicFileOutputStream atomicFileOutputStream, int i) {
        super(atomicFileOutputStream, i);
        this.mAtomicFileOutputStream = atomicFileOutputStream;
    }

    public void markSuccess() throws IOException {
        flush();
        this.mAtomicFileOutputStream.markSuccess();
    }

    public String toString() {
        return "AtomicFileBufferedOutputStream[" + this.mAtomicFileOutputStream + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
