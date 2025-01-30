package com.android.systemui;

import android.os.BinderProxy;
import android.os.Build;
import android.util.SparseIntArray;
import com.android.internal.os.BinderInternal;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BinderProxyDumpHelper implements Dumpable {
    public BinderProxyDumpHelper(DumpManager dumpManager) {
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "BinderProxyDumpHelper", this);
    }

    public static String dumpPerUidProxyCounts() {
        StringBuilder sb = new StringBuilder();
        SparseIntArray nGetBinderProxyPerUidCounts = BinderInternal.nGetBinderProxyPerUidCounts();
        if (nGetBinderProxyPerUidCounts.size() == 0) {
            return sb.toString();
        }
        sb.append("    Per Uid Binder Proxy Counts:\n");
        for (int i = 0; i < nGetBinderProxyPerUidCounts.size(); i++) {
            int keyAt = nGetBinderProxyPerUidCounts.keyAt(i);
            int valueAt = nGetBinderProxyPerUidCounts.valueAt(i);
            sb.append("    UID : ");
            sb.append(keyAt);
            sb.append("  count = ");
            sb.append(valueAt);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String dumpProxyInterfaceCounts() {
        StringBuilder sb = new StringBuilder();
        if (Build.IS_DEBUGGABLE) {
            BinderProxy.InterfaceCount[] sortedInterfaceCounts = BinderProxy.getSortedInterfaceCounts(10);
            sb.append("    BinderProxy descriptor histogram (top 10):\n");
            int i = 0;
            while (i < sortedInterfaceCounts.length) {
                sb.append("     #");
                int i2 = i + 1;
                sb.append(i2);
                sb.append(": ");
                sb.append(sortedInterfaceCounts[i]);
                sb.append("\n");
                i = i2;
            }
        } else {
            sb.append("Only supported in debuggable binary");
        }
        return sb.toString();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(dumpProxyInterfaceCounts());
        printWriter.println(dumpPerUidProxyCounts());
    }
}
