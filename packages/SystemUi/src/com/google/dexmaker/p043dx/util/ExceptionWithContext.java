package com.google.dexmaker.p043dx.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            String stringBuffer = ((ExceptionWithContext) th).context.toString();
            StringBuffer stringBuffer2 = new StringBuffer(stringBuffer.length() + 200);
            this.context = stringBuffer2;
            stringBuffer2.append(stringBuffer);
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
