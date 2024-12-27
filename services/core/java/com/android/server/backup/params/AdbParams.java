package com.android.server.backup.params;

import android.app.backup.IFullBackupRestoreObserver;
import android.os.ParcelFileDescriptor;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AdbParams {
    public String curPassword;
    public String encryptPassword;
    public ParcelFileDescriptor fd;
    public IFullBackupRestoreObserver observer;
    public boolean typeMigration;
    public final AtomicBoolean latch = new AtomicBoolean(false);
    public boolean privilegeApp = false;
}
