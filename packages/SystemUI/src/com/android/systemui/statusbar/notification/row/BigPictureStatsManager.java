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

/* loaded from: classes3.dex */
public final class BigPictureStatsManager implements Dumpable {
    public final List durations;
    public final LatencyTracker latencyTracker;
    public final Object lock;
    public final CoroutineDispatcher mainDispatcher;
    public final ConcurrentHashMap startTimes;

    public BigPictureStatsManager(LatencyTracker latencyTracker, CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        this.latencyTracker = latencyTracker;
        this.mainDispatcher = coroutineDispatcher;
        dumpManager.registerNormalDumpable("BigPictureStatsManager", this);
        this.startTimes = new ConcurrentHashMap();
        this.durations = new ArrayList();
        this.lock = new Object();
    }

    public static int percentile(List list, double d) {
        return ((Number) CollectionsKt___CollectionsKt.sorted((ArrayList) list).get(MathKt__MathJVMKt.roundToInt((d / 100.0d) * r2.size()) - 1)).intValue();
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
            int iIntValue = ((Number) comparable).intValue();
            ArrayList arrayList = (ArrayList) this.durations;
            int size = arrayList.size();
            int i = 0;
            double dIntValue = 0.0d;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                dIntValue += ((Number) obj).intValue();
                i++;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                    throw null;
                }
            }
            int iRoundToInt = MathKt__MathJVMKt.roundToInt(i == 0 ? Double.NaN : dIntValue / i);
            int iPercentile = percentile(this.durations, 90.0d);
            int iPercentile2 = percentile(this.durations, 99.0d);
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            indentingPrintWriter.println("Lazy-loaded " + ((ArrayList) this.durations).size() + " images:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Avg: " + iRoundToInt + " ms");
            indentingPrintWriter.println("Max: " + iIntValue + " ms");
            indentingPrintWriter.println("P90: " + iPercentile + " ms");
            indentingPrintWriter.println("P99: " + iPercentile2 + " ms");
            Unit unit = Unit.INSTANCE;
        }
    }
}
