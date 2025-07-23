package android.app.backup;

import android.os.IBinder;

/* loaded from: classes.dex */
public interface BackupManagerInternal {
    void agentConnectedForUser(String str, int i, IBinder iBinder);

    void agentDisconnectedForUser(String str, int i);
}
