package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class AtomicFileBufferedPrintWriter extends PrintWriter {
    private final AtomicFileOutputStream mAtomicFileOutStream;

    public AtomicFileBufferedPrintWriter(AtomicFile atomicFile, Charset charset) throws IOException {
        this(new AtomicFileOutputStream(atomicFile), charset);
    }

    public AtomicFileBufferedPrintWriter(AtomicFileOutputStream atomicFileOutputStream, Charset charset) {
        super(new BufferedWriter(new OutputStreamWriter(atomicFileOutputStream, charset)));
        this.mAtomicFileOutStream = atomicFileOutputStream;
    }

    public AtomicFileBufferedPrintWriter(AtomicFile atomicFile, Charset charset, int i) throws IOException {
        this(new AtomicFileOutputStream(atomicFile), charset, i);
    }

    public AtomicFileBufferedPrintWriter(AtomicFileOutputStream atomicFileOutputStream, Charset charset, int i) {
        super(new BufferedWriter(new OutputStreamWriter(atomicFileOutputStream, charset), i));
        this.mAtomicFileOutStream = atomicFileOutputStream;
    }

    public void markSuccess() throws IOException {
        flush();
        this.mAtomicFileOutStream.markSuccess();
    }

    public String toString() {
        return "AtomicFileBufferedPrintWriter[" + this.mAtomicFileOutStream + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
