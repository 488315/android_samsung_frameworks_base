package com.android.server.backup.fullbackup;

public final class FullBackupEntry implements Comparable {
    public final long lastBackup;
    public final String packageName;

    public FullBackupEntry(long j, String str) {
        this.packageName = str;
        this.lastBackup = j;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        long j = this.lastBackup;
        long j2 = ((FullBackupEntry) obj).lastBackup;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }
}
