package com.android.server.power.stats;

import android.util.Slog;
import android.util.SparseIntArray;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatsUidResolver {
    public final SparseIntArray mIsolatedUids = new SparseIntArray();
    public final SparseIntArray mIsolatedUidRefCounts = new SparseIntArray();
    public volatile List mListeners = Collections.emptyList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {
        void onAfterIsolatedUidRemoved(int i, int i2);

        void onBeforeIsolatedUidRemoved(int i);

        void onIsolatedUidAdded(int i, int i2);
    }

    public final void addListener(Listener listener) {
        synchronized (this) {
            ArrayList arrayList = new ArrayList(this.mListeners);
            arrayList.add(listener);
            this.mListeners = Collections.unmodifiableList(arrayList);
        }
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("Currently mapped isolated uids:");
        synchronized (this) {
            try {
                int size = this.mIsolatedUids.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mIsolatedUids.keyAt(i);
                    printWriter.println(
                            "  "
                                    + keyAt
                                    + "->"
                                    + this.mIsolatedUids.valueAt(i)
                                    + " (ref count = "
                                    + this.mIsolatedUidRefCounts.get(keyAt)
                                    + ")");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int mapUid(int i) {
        int i2;
        synchronized (this) {
            i2 = this.mIsolatedUids.get(i, i);
        }
        return i2;
    }

    public final void releaseIsolatedUid(int i) {
        synchronized (this) {
            try {
                int i2 = this.mIsolatedUidRefCounts.get(i, 0) - 1;
                if (i2 > 0) {
                    this.mIsolatedUidRefCounts.put(i, i2);
                    return;
                }
                int indexOfKey = this.mIsolatedUids.indexOfKey(i);
                if (indexOfKey < 0) {
                    Slog.w(
                            "PowerStatsUidResolver",
                            "Attempted to remove untracked child uid (" + i + ")");
                    return;
                }
                int valueAt = this.mIsolatedUids.valueAt(indexOfKey);
                this.mIsolatedUids.removeAt(indexOfKey);
                this.mIsolatedUidRefCounts.delete(i);
                List list = this.mListeners;
                for (int size = list.size() - 1; size >= 0; size--) {
                    ((Listener) list.get(size)).onAfterIsolatedUidRemoved(i, valueAt);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void retainIsolatedUid(int i) {
        synchronized (this) {
            try {
                int i2 = this.mIsolatedUidRefCounts.get(i, 0);
                if (i2 > 0) {
                    this.mIsolatedUidRefCounts.put(i, i2 + 1);
                    return;
                }
                Slog.w(
                        "PowerStatsUidResolver",
                        "Attempted to increment ref counted of untracked isolated uid (" + i + ")");
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
