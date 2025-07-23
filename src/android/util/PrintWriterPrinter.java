package android.util;

import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class PrintWriterPrinter implements Printer {
    private final PrintWriter mPW;

    public PrintWriterPrinter(PrintWriter printWriter) {
        this.mPW = printWriter;
    }

    @Override // android.util.Printer
    public void println(String str) {
        this.mPW.println(str);
    }
}
