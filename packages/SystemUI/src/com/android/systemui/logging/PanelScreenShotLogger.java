package com.android.systemui.logging;

import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PanelScreenShotLogger implements Dumpable {
    public static final PanelScreenShotLogger INSTANCE = new PanelScreenShotLogger();
    public static final ArrayList assembledLogs = new ArrayList();
    public static final Map providers = new LinkedHashMap();
    public static final PanelScreenShotBufferLogger panelScreenShotBufferLogger = (PanelScreenShotBufferLogger) Dependency.sDependency.getDependencyInner(PanelScreenShotBufferLogger.class);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface LogProvider {
        ArrayList gatherState();
    }

    private PanelScreenShotLogger() {
    }

    public static void addHeaderLine(String str, ArrayList arrayList) {
        arrayList.add("\n\n\n############################################");
        arrayList.add("    ".concat(str));
        arrayList.add("############################################\n\n\n");
    }

    public static void addLogItem(ArrayList arrayList, String str, Object obj) {
        arrayList.add(str + " = " + obj);
    }

    public final synchronized void addLogProvider(String str, LogProvider logProvider) {
        providers.put(str, logProvider);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ArrayList arrayList = assembledLogs;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            printWriter.println((String) obj);
        }
    }
}
