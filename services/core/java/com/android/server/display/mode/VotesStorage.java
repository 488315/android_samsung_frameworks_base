package com.android.server.display.mode;

import android.util.Slog;
import android.util.SparseArray;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VotesStorage {
    static final int GLOBAL_ID = -1;
    public final DisplayModeDirector$$ExternalSyntheticLambda0 mListener;
    public boolean mLoggingEnabled;
    public final Object mStorageLock = new Object();
    public final SparseArray mVotesByDisplay = new SparseArray();
    public final VotesStatsReporter mVotesStatsReporter;

    public VotesStorage(DisplayModeDirector$$ExternalSyntheticLambda0 displayModeDirector$$ExternalSyntheticLambda0, VotesStatsReporter votesStatsReporter) {
        this.mListener = displayModeDirector$$ExternalSyntheticLambda0;
        this.mVotesStatsReporter = votesStatsReporter;
    }

    public final void dump(PrintWriter printWriter) {
        int i;
        SparseArray sparseArray = new SparseArray();
        synchronized (this.mStorageLock) {
            for (int i2 = 0; i2 < this.mVotesByDisplay.size(); i2++) {
                try {
                    sparseArray.put(this.mVotesByDisplay.keyAt(i2), ((SparseArray) this.mVotesByDisplay.valueAt(i2)).clone());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        printWriter.println("  mVotesByDisplay:");
        for (i = 0; i < sparseArray.size(); i++) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(i);
            if (sparseArray2.size() != 0) {
                printWriter.println("    " + sparseArray.keyAt(i) + ":");
                for (int i3 = 24; i3 >= 0; i3--) {
                    Vote vote = (Vote) sparseArray2.get(i3);
                    if (vote != null) {
                        printWriter.println("      " + Vote.priorityToString(i3) + " -> " + vote);
                    }
                }
            }
        }
    }

    public final SparseArray getVotes(int i) {
        SparseArray clone;
        SparseArray clone2;
        synchronized (this.mStorageLock) {
            try {
                SparseArray sparseArray = (SparseArray) this.mVotesByDisplay.get(i);
                clone = sparseArray != null ? sparseArray.clone() : new SparseArray();
                SparseArray sparseArray2 = (SparseArray) this.mVotesByDisplay.get(-1);
                clone2 = sparseArray2 != null ? sparseArray2.clone() : new SparseArray();
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int i2 = 0; i2 < clone2.size(); i2++) {
            int keyAt = clone2.keyAt(i2);
            if (!clone.contains(keyAt)) {
                clone.put(keyAt, (Vote) clone2.valueAt(i2));
            }
        }
        return clone;
    }

    public void injectVotesByDisplay(SparseArray sparseArray) {
        synchronized (this.mStorageLock) {
            try {
                this.mVotesByDisplay.clear();
                for (int i = 0; i < sparseArray.size(); i++) {
                    this.mVotesByDisplay.put(sparseArray.keyAt(i), (SparseArray) sparseArray.valueAt(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateVote(int i, int i2, Vote vote) {
        SparseArray sparseArray;
        boolean z;
        if (this.mLoggingEnabled) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "updateVoteLocked(displayId=", ", priority=");
            m.append(Vote.priorityToString(i2));
            m.append(", vote=");
            m.append(vote);
            m.append(")");
            Slog.i("VotesStorage", m.toString());
        }
        if (i2 < 0 || i2 > 24) {
            Slog.w("VotesStorage", "Received a vote with an invalid priority, ignoring: priority=" + Vote.priorityToString(i2) + ", vote=" + vote);
            return;
        }
        synchronized (this.mStorageLock) {
            try {
                if (this.mVotesByDisplay.contains(i)) {
                    sparseArray = (SparseArray) this.mVotesByDisplay.get(i);
                } else {
                    sparseArray = new SparseArray();
                    this.mVotesByDisplay.put(i, sparseArray);
                }
                Vote vote2 = (Vote) sparseArray.get(i2);
                z = true;
                if (vote != null && !vote.equals(vote2)) {
                    sparseArray.put(i2, vote);
                } else if (vote != null || vote2 == null) {
                    z = false;
                } else {
                    sparseArray.remove(i2);
                    if (sparseArray.size() == 0) {
                        this.mVotesByDisplay.remove(i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mLoggingEnabled) {
            Slog.i("VotesStorage", "Updated votes for display=" + i + " votes=" + sparseArray);
        }
        if (z) {
            VotesStatsReporter votesStatsReporter = this.mVotesStatsReporter;
            if (votesStatsReporter != null) {
                votesStatsReporter.reportVoteChanged(i, i2, vote);
            }
            this.mListener.f$0.notifyDesiredDisplayModeSpecsChangedLocked();
        }
    }
}
