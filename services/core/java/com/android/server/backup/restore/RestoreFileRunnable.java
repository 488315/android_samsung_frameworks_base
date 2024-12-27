package com.android.server.backup.restore;

import android.app.IBackupAgent;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.server.backup.FileMetadata;
import com.android.server.backup.UserBackupManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RestoreFileRunnable implements Runnable {
    public final IBackupAgent mAgent;
    public final UserBackupManagerService mBackupManagerService;
    public final FileMetadata mInfo;
    public final ParcelFileDescriptor mSocket;
    public final int mToken;

    public RestoreFileRunnable(UserBackupManagerService userBackupManagerService, IBackupAgent iBackupAgent, FileMetadata fileMetadata, ParcelFileDescriptor parcelFileDescriptor, int i) {
        this.mAgent = iBackupAgent;
        this.mInfo = fileMetadata;
        this.mToken = i;
        this.mSocket = ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
        this.mBackupManagerService = userBackupManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            IBackupAgent iBackupAgent = this.mAgent;
            ParcelFileDescriptor parcelFileDescriptor = this.mSocket;
            FileMetadata fileMetadata = this.mInfo;
            iBackupAgent.doRestoreFile(parcelFileDescriptor, fileMetadata.size, fileMetadata.type, fileMetadata.domain, fileMetadata.path, fileMetadata.mode, fileMetadata.mtime, this.mToken, this.mBackupManagerService.mBackupManagerBinder);
        } catch (RemoteException unused) {
        }
    }
}
