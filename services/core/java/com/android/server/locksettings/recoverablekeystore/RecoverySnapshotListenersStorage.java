package com.android.server.locksettings.recoverablekeystore;

import android.app.PendingIntent;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
