package com.android.server.display.mode;

import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class VotesStorage {
    public final Listener mListener;
    public boolean mLoggingEnabled;
    public final Object mStorageLock = new Object();
    public final SparseArray mVotesByDisplay = new SparseArray();

    public interface Listener {
        void onChanged();
    }

    public VotesStorage(Listener listener) {
        this.mListener = listener;
    }

    public void setLoggingEnabled(boolean z) {
        this.mLoggingEnabled = z;
    }

    public SparseArray getVotes(int i) {
        SparseArray clone;
        SparseArray clone2;
        synchronized (this.mStorageLock) {
            SparseArray sparseArray = (SparseArray) this.mVotesByDisplay.get(i);
            clone = sparseArray != null ? sparseArray.clone() : new SparseArray();
            SparseArray sparseArray2 = (SparseArray) this.mVotesByDisplay.get(-1);
            clone2 = sparseArray2 != null ? sparseArray2.clone() : new SparseArray();
        }
        for (int i2 = 0; i2 < clone2.size(); i2++) {
            int keyAt = clone2.keyAt(i2);
            if (!clone.contains(keyAt)) {
                clone.put(keyAt, (Vote) clone2.valueAt(i2));
            }
        }
        return clone;
    }

    public void updateGlobalVote(int i, Vote vote) {
        updateVote(-1, i, vote);
    }

    public void updateVote(int i, int i2, Vote vote) {
        SparseArray sparseArray;
        if (this.mLoggingEnabled) {
            Slog.i("VotesStorage", "updateVoteLocked(displayId=" + i + ", priority=" + Vote.priorityToString(i2) + ", vote=" + vote + ")");
        }
        if (i2 < 0 || i2 > 18) {
            Slog.w("VotesStorage", "Received a vote with an invalid priority, ignoring: priority=" + Vote.priorityToString(i2) + ", vote=" + vote);
            return;
        }
        synchronized (this.mStorageLock) {
            if (this.mVotesByDisplay.contains(i)) {
                sparseArray = (SparseArray) this.mVotesByDisplay.get(i);
            } else {
                sparseArray = new SparseArray();
                this.mVotesByDisplay.put(i, sparseArray);
            }
            if (vote != null) {
                sparseArray.put(i2, vote);
            } else {
                sparseArray.remove(i2);
            }
        }
        if (this.mLoggingEnabled) {
            Slog.i("VotesStorage", "Updated votes for display=" + i + " votes=" + sparseArray);
        }
        this.mListener.onChanged();
    }

    public void dump(PrintWriter printWriter) {
        int i;
        SparseArray sparseArray = new SparseArray();
        synchronized (this.mStorageLock) {
            for (int i2 = 0; i2 < this.mVotesByDisplay.size(); i2++) {
                sparseArray.put(this.mVotesByDisplay.keyAt(i2), ((SparseArray) this.mVotesByDisplay.valueAt(i2)).clone());
            }
        }
        printWriter.println("  mVotesByDisplay:");
        for (i = 0; i < sparseArray.size(); i++) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(i);
            if (sparseArray2.size() != 0) {
                printWriter.println("    " + sparseArray.keyAt(i) + XmlUtils.STRING_ARRAY_SEPARATOR);
                for (int i3 = 18; i3 >= 0; i3--) {
                    Vote vote = (Vote) sparseArray2.get(i3);
                    if (vote != null) {
                        printWriter.println("      " + Vote.priorityToString(i3) + " -> " + vote);
                    }
                }
            }
        }
    }

    public void injectVotesByDisplay(SparseArray sparseArray) {
        synchronized (this.mStorageLock) {
            this.mVotesByDisplay.clear();
            for (int i = 0; i < sparseArray.size(); i++) {
                this.mVotesByDisplay.put(sparseArray.keyAt(i), (SparseArray) sparseArray.valueAt(i));
            }
        }
    }
}
