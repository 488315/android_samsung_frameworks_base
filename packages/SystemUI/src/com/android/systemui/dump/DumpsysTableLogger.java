package com.android.systemui.dump;

import android.os.Trace;
import java.io.PrintWriter;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DumpsysTableLogger {
    public final List columns;
    public final List rows;
    public final String sectionName;

    public DumpsysTableLogger(String str, List<String> list, List<? extends List<String>> list2) {
        this.sectionName = str;
        this.columns = list;
        this.rows = list2;
    }

    public final void printTableData(PrintWriter printWriter) {
        Trace.beginSection("DumpsysTableLogger#printTableData");
        PrintWriter append = printWriter.append("SystemUI TableSection START: ");
        String str = this.sectionName;
        append.println(str);
        printWriter.append("version ").println("1");
        CollectionsKt___CollectionsKt.joinTo$default(this.columns, printWriter, "|", null, 124);
        printWriter.println();
        int size = this.columns.size();
        for (List list : this.rows) {
            if (list.size() == size) {
                CollectionsKt___CollectionsKt.joinTo$default(list, printWriter, "|", null, 124);
                printWriter.println();
            }
        }
        printWriter.append("SystemUI TableSection END: ").println(str);
        Trace.endSection();
    }
}
