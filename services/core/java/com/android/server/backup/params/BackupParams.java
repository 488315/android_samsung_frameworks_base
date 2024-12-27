package com.android.server.backup.params;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;

import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.utils.BackupEligibilityRules;

import java.util.ArrayList;

public final class BackupParams {
    public String dirName;
    public ArrayList fullPackages;
    public ArrayList kvPackages;
    public OnTaskFinishedListener listener;
    public BackupEligibilityRules mBackupEligibilityRules;
    public TransportConnection mTransportConnection;
    public IBackupManagerMonitor monitor;
    public boolean nonIncrementalBackup;
    public IBackupObserver observer;
}
