package com.android.internal.util;

import java.io.Writer;

@Deprecated
/* loaded from: classes4.dex */
public class IndentingPrintWriter extends android.util.IndentingPrintWriter {
    public IndentingPrintWriter(Writer writer, String str) {
        super(writer, str, -1);
    }

    public IndentingPrintWriter(Writer writer, String str, int i) {
        super(writer, str, i);
    }

    public IndentingPrintWriter(Writer writer, String str, String str2, int i) {
        super(writer, str, str2, i);
    }

    @Override // android.util.IndentingPrintWriter
    public IndentingPrintWriter setIndent(String str) {
        super.setIndent(str);
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public IndentingPrintWriter setIndent(int i) {
        super.setIndent(i);
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public IndentingPrintWriter increaseIndent() {
        super.increaseIndent();
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public IndentingPrintWriter decreaseIndent() {
        super.decreaseIndent();
        return this;
    }

    public IndentingPrintWriter printPair(String str, Object obj) {
        super.print(str, obj);
        return this;
    }

    public IndentingPrintWriter printPair(String str, Object[] objArr) {
        super.print(str, objArr);
        return this;
    }

    public IndentingPrintWriter printHexPair(String str, int i) {
        super.printHexInt(str, i);
        return this;
    }
}
