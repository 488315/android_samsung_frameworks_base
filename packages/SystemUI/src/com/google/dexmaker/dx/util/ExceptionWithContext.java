package com.google.dexmaker.dx.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class ExceptionWithContext extends RuntimeException {
    private StringBuffer context;

    public ExceptionWithContext(String str) {
        this(str, null);
    }

    @Override // java.lang.Throwable
    public final void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        printStream.println(this.context);
    }

    public ExceptionWithContext(Throwable th) {
        this(null, th);
    }

    public ExceptionWithContext(String str, Throwable th) {
        super(str == null ? th != null ? th.getMessage() : null : str, th);
        if (th instanceof ExceptionWithContext) {
            String string = ((ExceptionWithContext) th).context.toString();
            StringBuffer stringBuffer = new StringBuffer(string.length() + 200);
            this.context = stringBuffer;
            stringBuffer.append(string);
            return;
        }
        this.context = new StringBuffer(200);
    }

    @Override // java.lang.Throwable
    public final void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        printWriter.println(this.context);
    }
}
