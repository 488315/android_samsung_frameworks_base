package com.android.server.usage;

import android.app.usage.BroadcastResponseStats;
import android.util.ArrayMap;
import com.android.internal.util.IndentingPrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public class UserBroadcastResponseStats {
    public ArrayMap mResponseStats = new ArrayMap();

    public BroadcastResponseStats getOrCreateBroadcastResponseStats(BroadcastEvent broadcastEvent) {
        BroadcastResponseStats broadcastResponseStats = (BroadcastResponseStats) this.mResponseStats.get(broadcastEvent);
        if (broadcastResponseStats != null) {
            return broadcastResponseStats;
        }
        BroadcastResponseStats broadcastResponseStats2 = new BroadcastResponseStats(broadcastEvent.getTargetPackage(), broadcastEvent.getIdForResponseEvent());
        this.mResponseStats.put(broadcastEvent, broadcastResponseStats2);
        return broadcastResponseStats2;
    }

    public void populateAllBroadcastResponseStats(List list, String str, long j) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            BroadcastEvent broadcastEvent = (BroadcastEvent) this.mResponseStats.keyAt(size);
            if ((j == 0 || j == broadcastEvent.getIdForResponseEvent()) && (str == null || str.equals(broadcastEvent.getTargetPackage()))) {
                list.add((BroadcastResponseStats) this.mResponseStats.valueAt(size));
            }
        }
    }

    public void clearBroadcastResponseStats(String str, long j) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            BroadcastEvent broadcastEvent = (BroadcastEvent) this.mResponseStats.keyAt(size);
            if ((j == 0 || j == broadcastEvent.getIdForResponseEvent()) && (str == null || str.equals(broadcastEvent.getTargetPackage()))) {
                this.mResponseStats.removeAt(size);
            }
        }
    }

    public void onPackageRemoved(String str) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            if (((BroadcastEvent) this.mResponseStats.keyAt(size)).getTargetPackage().equals(str)) {
                this.mResponseStats.removeAt(size);
            }
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < this.mResponseStats.size(); i++) {
            BroadcastEvent broadcastEvent = (BroadcastEvent) this.mResponseStats.keyAt(i);
            BroadcastResponseStats broadcastResponseStats = (BroadcastResponseStats) this.mResponseStats.valueAt(i);
            indentingPrintWriter.print(broadcastEvent);
            indentingPrintWriter.print(" -> ");
            indentingPrintWriter.println(broadcastResponseStats);
        }
    }
}
