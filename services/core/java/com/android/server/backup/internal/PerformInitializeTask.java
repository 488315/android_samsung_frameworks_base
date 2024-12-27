package com.android.server.backup.internal;

import android.app.backup.IBackupObserver;

import com.android.server.backup.TransportManager;
import com.android.server.backup.UserBackupManagerService;

import java.io.File;

public final class PerformInitializeTask implements Runnable {
    public final UserBackupManagerService mBackupManagerService;
    public final File mBaseStateDir;
    public final OnTaskFinishedListener mListener;
    public IBackupObserver mObserver;
    public final String[] mQueue;
    public final TransportManager mTransportManager;

    public PerformInitializeTask(
            UserBackupManagerService userBackupManagerService,
            TransportManager transportManager,
            String[] strArr,
            IBackupObserver iBackupObserver,
            OnTaskFinishedListener onTaskFinishedListener,
            File file) {
        this.mBackupManagerService = userBackupManagerService;
        this.mTransportManager = transportManager;
        this.mQueue = strArr;
        this.mObserver = iBackupObserver;
        this.mListener = onTaskFinishedListener;
        this.mBaseStateDir = file;
    }

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
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.internal.PerformInitializeTask.run():void");
    }
}
