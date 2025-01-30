package com.android.server.rollback;

import java.util.List;

/* loaded from: classes3.dex */
public interface RollbackManagerInternal {
    int notifyStagedSession(int i);

    void snapshotAndRestoreUserData(String str, List list, int i, long j, String str2, int i2);
}
