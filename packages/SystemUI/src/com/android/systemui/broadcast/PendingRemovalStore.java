package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.util.IndentingPrintWriter;
import android.util.SparseSetArray;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import java.io.PrintWriter;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class PendingRemovalStore implements Dumpable {
    public final BroadcastDispatcherLogger logger;
    public final SparseSetArray pendingRemoval = new SparseSetArray();

    public PendingRemovalStore(BroadcastDispatcherLogger broadcastDispatcherLogger) {
        this.logger = broadcastDispatcherLogger;
    }

    public final void clearPendingRemoval(BroadcastReceiver broadcastReceiver, int i) {
        synchronized (this.pendingRemoval) {
            this.pendingRemoval.remove(i, broadcastReceiver);
        }
        BroadcastDispatcherLogger broadcastDispatcherLogger = this.logger;
        broadcastDispatcherLogger.getClass();
        String string = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$$ExternalSyntheticLambda0 broadcastDispatcherLogger$$ExternalSyntheticLambda0 = new BroadcastDispatcherLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = broadcastDispatcherLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = string;
        logBuffer.commit(logMessageObtain);
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
                    int iKeyAt = this.pendingRemoval.keyAt(i);
                    printWriter.print(iKeyAt);
                    printWriter.print("->");
                    printWriter.println(this.pendingRemoval.get(iKeyAt));
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
