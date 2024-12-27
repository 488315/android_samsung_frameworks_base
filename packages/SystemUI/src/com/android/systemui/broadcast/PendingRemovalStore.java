package com.android.systemui.broadcast;

import android.util.IndentingPrintWriter;
import android.util.SparseSetArray;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PendingRemovalStore implements Dumpable {
    public final BroadcastDispatcherLogger logger;
    public final SparseSetArray pendingRemoval = new SparseSetArray();

    public PendingRemovalStore(BroadcastDispatcherLogger broadcastDispatcherLogger) {
        this.logger = broadcastDispatcherLogger;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (this.pendingRemoval) {
            try {
                if (printWriter instanceof IndentingPrintWriter) {
                    ((IndentingPrintWriter) printWriter).increaseIndent();
                }
                int size = this.pendingRemoval.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.pendingRemoval.keyAt(i);
                    printWriter.print(keyAt);
                    printWriter.print("->");
                    printWriter.println(this.pendingRemoval.get(keyAt));
                }
                if (printWriter instanceof IndentingPrintWriter) {
                    ((IndentingPrintWriter) printWriter).decreaseIndent();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
