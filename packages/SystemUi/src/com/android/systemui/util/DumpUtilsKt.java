package com.android.systemui.util;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.IndentingPrintWriter;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DumpUtilsKt {
    public static final IndentingPrintWriter asIndenting(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = printWriter instanceof IndentingPrintWriter ? (IndentingPrintWriter) printWriter : null;
        return indentingPrintWriter == null ? new IndentingPrintWriter(printWriter) : indentingPrintWriter;
    }

    public static final void println(IndentingPrintWriter indentingPrintWriter, String str, Object obj) {
        indentingPrintWriter.append(str).append('=').println(obj);
    }

    public static final String visibilityString(int i) {
        return i != 0 ? i != 4 ? i != 8 ? AbstractC0000x2c234b15.m0m("unknown:", i) : "gone" : "invisible" : "visible";
    }

    public static final void withIncreasedIndent(IndentingPrintWriter indentingPrintWriter, Runnable runnable) {
        indentingPrintWriter.increaseIndent();
        try {
            runnable.run();
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }
}
