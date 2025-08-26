package com.android.apex;

import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public class XmlWriter implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private PrintWriter out;
    private StringBuilder outBuffer = new StringBuilder();
    private int indent = 0;
    private boolean startLine = true;

    public XmlWriter(PrintWriter printWriter) {
        this.out = printWriter;
    }

    private void printIndent() {
        for (int i = 0; i < this.indent; i++) {
            this.outBuffer.append("    ");
        }
        this.startLine = false;
    }

    void print(String str) {
        String[] strArrSplit = str.split(ShaderAssembler.NEWLINE, -1);
        int i = 0;
        while (i < strArrSplit.length) {
            if (this.startLine && !strArrSplit[i].isEmpty()) {
                printIndent();
            }
            this.outBuffer.append(strArrSplit[i]);
            i++;
            if (i < strArrSplit.length) {
                this.outBuffer.append(ShaderAssembler.NEWLINE);
                this.startLine = true;
            }
        }
    }

    void increaseIndent() {
        this.indent++;
    }

    void decreaseIndent() {
        this.indent--;
    }

    void printXml() {
        this.out.print(this.outBuffer.toString());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        PrintWriter printWriter = this.out;
        if (printWriter != null) {
            printWriter.close();
        }
    }

    public static void write(XmlWriter xmlWriter, ApexInfoList apexInfoList) throws IOException {
        xmlWriter.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        if (apexInfoList != null) {
            apexInfoList.write(xmlWriter, "apex-info-list");
        }
        xmlWriter.printXml();
    }
}
