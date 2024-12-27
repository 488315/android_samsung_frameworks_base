package com.android.server.compat.overrides;

import com.android.server.compat.overrides.ChangeOverrides;
import java.io.Closeable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class XmlWriter implements Closeable {
    public int indent;
    public PrintWriter out;
    public StringBuilder outBuffer;
    public boolean startLine;

    public static void write(XmlWriter xmlWriter, Overrides overrides) {
        xmlWriter.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        xmlWriter.print("<overrides");
        xmlWriter.print(">\n");
        xmlWriter.indent++;
        for (ChangeOverrides changeOverrides : overrides.getChangeOverrides()) {
            changeOverrides.getClass();
            xmlWriter.print("<change-overrides");
            if (changeOverrides.changeId != null) {
                xmlWriter.print(" changeId=\"");
                Long l = changeOverrides.changeId;
                xmlWriter.print(Long.toString(l == null ? 0L : l.longValue()));
                xmlWriter.print("\"");
            }
            xmlWriter.print(">\n");
            xmlWriter.indent++;
            ChangeOverrides.Raw raw = changeOverrides.validated;
            if (raw != null) {
                xmlWriter.print("<validated");
                xmlWriter.print(">\n");
                xmlWriter.indent++;
                Iterator it = raw.getOverrideValue().iterator();
                while (it.hasNext()) {
                    ((OverrideValue) it.next()).write(xmlWriter);
                }
                xmlWriter.indent--;
                xmlWriter.print("</validated>\n");
            }
            ChangeOverrides.Raw raw2 = changeOverrides.deferred;
            if (raw2 != null) {
                xmlWriter.print("<deferred");
                xmlWriter.print(">\n");
                xmlWriter.indent++;
                if (raw2.rawOverrideValue == null) {
                    raw2.rawOverrideValue = new ArrayList();
                }
                Iterator it2 = ((ArrayList) raw2.rawOverrideValue).iterator();
                while (it2.hasNext()) {
                    ((OverrideValue) it2.next()).write(xmlWriter);
                }
                xmlWriter.indent--;
                xmlWriter.print("</deferred>\n");
            }
            ChangeOverrides.Raw raw3 = changeOverrides.raw;
            if (raw3 != null) {
                xmlWriter.print("<raw");
                xmlWriter.print(">\n");
                xmlWriter.indent++;
                for (RawOverrideValue rawOverrideValue : raw3.getRawOverrideValue()) {
                    rawOverrideValue.getClass();
                    xmlWriter.print("<raw-override-value");
                    if (rawOverrideValue.packageName != null) {
                        xmlWriter.print(" packageName=\"");
                        xmlWriter.print(rawOverrideValue.packageName);
                        xmlWriter.print("\"");
                    }
                    if (rawOverrideValue.minVersionCode != null) {
                        xmlWriter.print(" minVersionCode=\"");
                        Long l2 = rawOverrideValue.minVersionCode;
                        xmlWriter.print(Long.toString(l2 == null ? 0L : l2.longValue()));
                        xmlWriter.print("\"");
                    }
                    if (rawOverrideValue.maxVersionCode != null) {
                        xmlWriter.print(" maxVersionCode=\"");
                        Long l3 = rawOverrideValue.maxVersionCode;
                        xmlWriter.print(Long.toString(l3 == null ? 0L : l3.longValue()));
                        xmlWriter.print("\"");
                    }
                    if (rawOverrideValue.enabled != null) {
                        xmlWriter.print(" enabled=\"");
                        Boolean bool = rawOverrideValue.enabled;
                        xmlWriter.print(Boolean.toString(bool == null ? false : bool.booleanValue()));
                        xmlWriter.print("\"");
                    }
                    xmlWriter.print(">\n");
                    xmlWriter.print("</raw-override-value>\n");
                }
                xmlWriter.indent--;
                xmlWriter.print("</raw>\n");
            }
            xmlWriter.indent--;
            xmlWriter.print("</change-overrides>\n");
        }
        xmlWriter.indent--;
        xmlWriter.print("</overrides>\n");
        xmlWriter.out.print(xmlWriter.outBuffer.toString());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        PrintWriter printWriter = this.out;
        if (printWriter != null) {
            printWriter.close();
        }
    }

    public final void print(String str) {
        String[] split = str.split("\n", -1);
        int i = 0;
        while (i < split.length) {
            if (this.startLine && !split[i].isEmpty()) {
                for (int i2 = 0; i2 < this.indent; i2++) {
                    this.outBuffer.append("    ");
                }
                this.startLine = false;
            }
            this.outBuffer.append(split[i]);
            i++;
            if (i < split.length) {
                this.outBuffer.append("\n");
                this.startLine = true;
            }
        }
    }
}
