package com.android.server.backup.fullbackup;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
