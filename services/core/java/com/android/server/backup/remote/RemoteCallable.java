package com.android.server.backup.remote;

public interface RemoteCallable {
    void call(FutureBackupCallback futureBackupCallback);
}
