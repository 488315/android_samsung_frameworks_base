package com.android.server.locksettings.recoverablekeystore;

import android.app.PendingIntent;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;

/* loaded from: classes2.dex */
public class RecoverySnapshotListenersStorage {
  public SparseArray mAgentIntents = new SparseArray();
  public ArraySet mAgentsWithPendingSnapshots = new ArraySet();

  public synchronized void setSnapshotListener(int i, PendingIntent pendingIntent) {
    Log.i("RecoverySnapshotLstnrs", "Registered listener for agent with uid " + i);
    this.mAgentIntents.put(i, pendingIntent);
    if (this.mAgentsWithPendingSnapshots.contains(Integer.valueOf(i))) {
      Log.i(
          "RecoverySnapshotLstnrs",
          "Snapshot already created for agent. Immediately triggering intent.");
      tryToSendIntent(i, pendingIntent);
    }
  }

  public synchronized void recoverySnapshotAvailable(int i) {
    PendingIntent pendingIntent = (PendingIntent) this.mAgentIntents.get(i);
    if (pendingIntent == null) {
      Log.i(
          "RecoverySnapshotLstnrs",
          "Snapshot available for agent "
              + i
              + " but agent has not yet initialized. Will notify agent when it does.");
      this.mAgentsWithPendingSnapshots.add(Integer.valueOf(i));
      return;
    }
    tryToSendIntent(i, pendingIntent);
  }

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
