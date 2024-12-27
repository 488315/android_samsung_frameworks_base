package com.android.systemui.statusbar.notification.row;

import android.util.IndentingPrintWriter;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BigPictureStatsManager implements Dumpable {
    public final List durations;
    public final LatencyTracker latencyTracker;
    public final Object lock;
    public final CoroutineDispatcher mainDispatcher;
    public final ConcurrentHashMap startTimes;

    public BigPictureStatsManager(LatencyTracker latencyTracker, CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("BigPictureStatsManager", this);
        new ConcurrentHashMap();
        this.durations = new ArrayList();
        this.lock = new Object();
    }

    public static int percentile(List list, double d) {
        return ((Number) CollectionsKt___CollectionsKt.sorted(list).get(MathKt__MathJVMKt.roundToInt((d / 100.0d) * list.size()) - 1)).intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (this.lock) {
            if (((ArrayList) this.durations).isEmpty()) {
                printWriter.println("No entries");
                return;
            }
            Iterator it = ((ArrayList) this.durations).iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            Comparable comparable = (Comparable) it.next();
            while (it.hasNext()) {
                Comparable comparable2 = (Comparable) it.next();
                if (comparable.compareTo(comparable2) < 0) {
                    comparable = comparable2;
                }
            }
            int intValue = ((Number) comparable).intValue();
            Iterator it2 = ((ArrayList) this.durations).iterator();
            double d = 0.0d;
            int i = 0;
            while (it2.hasNext()) {
                d += ((Number) it2.next()).intValue();
                i++;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                    throw null;
                }
            }
            int roundToInt = MathKt__MathJVMKt.roundToInt(i == 0 ? Double.NaN : d / i);
            int percentile = percentile(this.durations, 90.0d);
            int percentile2 = percentile(this.durations, 99.0d);
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            indentingPrintWriter.println("Lazy-loaded " + ((ArrayList) this.durations).size() + " images:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Avg: " + roundToInt + " ms");
            indentingPrintWriter.println("Max: " + intValue + " ms");
            indentingPrintWriter.println("P90: " + percentile + " ms");
            indentingPrintWriter.println("P99: " + percentile2 + " ms");
            Unit unit = Unit.INSTANCE;
        }
    }
}
