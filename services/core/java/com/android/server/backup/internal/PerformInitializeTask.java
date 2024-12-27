package com.android.server.backup.internal;

import android.app.backup.IBackupObserver;
import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PerformInitializeTask implements Runnable {
    public final UserBackupManagerService mBackupManagerService;
    public final File mBaseStateDir;
    public final OnTaskFinishedListener mListener;
    public IBackupObserver mObserver;
    public final String[] mQueue;
    public final TransportManager mTransportManager;

    public PerformInitializeTask(UserBackupManagerService userBackupManagerService, TransportManager transportManager, String[] strArr, IBackupObserver iBackupObserver, OnTaskFinishedListener onTaskFinishedListener, File file) {
        this.mBackupManagerService = userBackupManagerService;
        this.mTransportManager = transportManager;
        this.mQueue = strArr;
        this.mObserver = iBackupObserver;
        this.mListener = onTaskFinishedListener;
        this.mBaseStateDir = file;
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x01a5 A[LOOP:2: B:61:0x019f->B:63:0x01a5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b5 A[Catch: RemoteException -> 0x01b9, TRY_LEAVE, TryCatch #7 {RemoteException -> 0x01b9, blocks: (B:65:0x01b1, B:67:0x01b5), top: B:64:0x01b1 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.backup.internal.PerformInitializeTask.run():void");
    }
}
