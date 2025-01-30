package com.android.systemui.dump;

import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        StringBuilder sb = new StringBuilder("SystemUI TableSection START: ");
        String str = this.sectionName;
        sb.append(str);
        printWriter.println(sb.toString());
        printWriter.println("version 1");
        printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(this.columns, "|", null, null, null, 62));
        int size = this.columns.size();
        ArrayList arrayList = new ArrayList();
        for (Object obj : this.rows) {
            if (((List) obj).size() == size) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            printWriter.println(CollectionsKt___CollectionsKt.joinToString$default((List) it.next(), "|", null, null, null, 62));
        }
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("SystemUI TableSection END: ", str, printWriter);
    }
}
