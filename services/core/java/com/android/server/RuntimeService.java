package com.android.server;

import android.content.Context;
import android.os.Binder;
import android.util.proto.ProtoOutputStream;
import com.android.i18n.timezone.DebugInfo;
import com.android.i18n.timezone.I18nModuleDebug;
import com.android.internal.util.DumpUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RuntimeService extends Binder {
    public final Context mContext;

    public RuntimeService(Context context) {
        this.mContext = context;
    }

    @Override // android.os.Binder
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ProtoOutputStream protoOutputStream;
        if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, "RuntimeService", printWriter)) {
            int length = strArr.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if ("--proto".equals(strArr[i])) {
                    z = true;
                    break;
                }
                i++;
            }
            DebugInfo debugInfo = I18nModuleDebug.getDebugInfo();
            if (z) {
                protoOutputStream = new ProtoOutputStream(fileDescriptor);
                for (DebugInfo.DebugEntry debugEntry : debugInfo.getDebugEntries()) {
                    long start = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1138166333441L, debugEntry.getKey());
                    protoOutputStream.write(1138166333442L, debugEntry.getStringValue());
                    protoOutputStream.end(start);
                }
            } else {
                printWriter.println("Core Library Debug Info: ");
                for (DebugInfo.DebugEntry debugEntry2 : debugInfo.getDebugEntries()) {
                    printWriter.print(debugEntry2.getKey());
                    printWriter.print(": \"");
                    printWriter.print(debugEntry2.getStringValue());
                    printWriter.println("\"");
                }
                protoOutputStream = null;
            }
            if (z) {
                protoOutputStream.flush();
            }
        }
    }
}
