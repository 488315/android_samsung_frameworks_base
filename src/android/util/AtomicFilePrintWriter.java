package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class AtomicFilePrintWriter extends PrintWriter {
    private final AtomicFileOutputStream mAtomicFileOutStream;

    public AtomicFilePrintWriter(AtomicFile atomicFile, Charset charset) throws IOException {
        this(new AtomicFileOutputStream(atomicFile), charset);
    }

    public AtomicFilePrintWriter(AtomicFileOutputStream atomicFileOutputStream, Charset charset) {
        super(new OutputStreamWriter(atomicFileOutputStream, charset));
        this.mAtomicFileOutStream = atomicFileOutputStream;
    }

    public void markSuccess() throws IOException {
        flush();
        this.mAtomicFileOutStream.markSuccess();
    }

    public String toString() {
        return "AtomicFilePrintWriter[" + this.mAtomicFileOutStream + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
