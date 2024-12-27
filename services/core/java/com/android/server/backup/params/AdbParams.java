package com.android.server.backup.params;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.ParcelFileDescriptor;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AdbParams {
    public String curPassword;
    public String encryptPassword;
    public ParcelFileDescriptor fd;
    public IFullBackupRestoreObserver observer;
    public boolean typeMigration;
    public final AtomicBoolean latch = new AtomicBoolean(false);
    public boolean privilegeApp = false;
}
