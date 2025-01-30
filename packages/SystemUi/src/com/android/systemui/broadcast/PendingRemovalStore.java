package com.android.systemui.broadcast;

import android.util.IndentingPrintWriter;
import android.util.SparseSetArray;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.io.PrintWriter;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        }
    }
}
