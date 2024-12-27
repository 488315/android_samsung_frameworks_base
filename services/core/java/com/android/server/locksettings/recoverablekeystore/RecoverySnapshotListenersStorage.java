package com.android.server.locksettings.recoverablekeystore;

import android.app.PendingIntent;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;

public final class RecoverySnapshotListenersStorage {
    public SparseArray mAgentIntents;
    public ArraySet mAgentsWithPendingSnapshots;

    public final synchronized void tryToSendIntent(int i, PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
            this.mAgentsWithPendingSnapshots.remove(Integer.valueOf(i));
            Log.d("RecoverySnapshotLstnrs", "Successfully notified listener.");
        } catch (PendingIntent.CanceledException e) {
            Log.e("RecoverySnapshotLstnrs", "Failed to trigger PendingIntent for " + i, e);
            this.mAgentsWithPendingSnapshots.add(Integer.valueOf(i));
        }
    }
}
