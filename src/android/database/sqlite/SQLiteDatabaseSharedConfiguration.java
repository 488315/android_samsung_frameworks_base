package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteDatabaseSharedConfiguration {
    private static final long MEDIA_STORE_AUTOCHECK_POINT = 256;
    private static final String MEDIA_STORE_EXTERNAL_DB = "providers.media.module/databases/external.db";
    private static final long MEDIA_STORE_JOURNAL_SIZE_LIMIT = 1048576;
    public static final long MEDIA_STORE_WAL_RESERVE_SPACE = 1;
    public final boolean isMediaStoreDb;
    public final boolean isSecureDb;
    public int shouldSendQueryLog = -1;
    public boolean useAssertionLog;
    public final boolean useDumpCorruptByDefault;
    public boolean useSingleConnectionWal;
    public boolean useUserDataRecovery;
    public final boolean useWalModeByDefault;
    private static final String[] DEFAULT_WAL_ALLOWLIST = {"/com.samsung.", "/com.sec."};
    private static final String[] DEFAULT_WAL_BLOCKLIST = {"/EmailProvider.db", "/EmailProviderBody.db", "/iwlansettings.db"};
    private static final String[] QUERY_COLLECT_PACKAGES = {"/com.samsung.", "/com.sec.", "/data/system/", "/com.google.", "/com.android.providers."};
    private static final String[] DEFAULT_SINGLE_CONNECTION_WAL_LIST = {"/data/system/notification_log.db"};
    private static final String[] DEFAULT_DUMP_CORRUPT_ALLOWLIST = {"contacts2.db", "SecureHealthData.db"};

    public SQLiteDatabaseSharedConfiguration(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        this.isSecureDb = (sQLiteDatabaseConfiguration.openFlags & 512) != 0;
        this.isMediaStoreDb = isMediaStoreDb(sQLiteDatabaseConfiguration.path);
        boolean isSingleConnectionWalDb = isSingleConnectionWalDb(sQLiteDatabaseConfiguration.path);
        this.useSingleConnectionWal = isSingleConnectionWalDb;
        if (isSingleConnectionWalDb) {
            this.useWalModeByDefault = true;
        } else {
            this.useWalModeByDefault = isDefaultWalDb(sQLiteDatabaseConfiguration.path, sQLiteDatabaseConfiguration.openFlags);
        }
        boolean isDefaultDumpCorruptDb = isDefaultDumpCorruptDb(sQLiteDatabaseConfiguration.path);
        this.useDumpCorruptByDefault = isDefaultDumpCorruptDb;
        this.useAssertionLog = isDefaultDumpCorruptDb;
    }

    private boolean isDefaultWalDb(String str, int i) {
        if ((i & 512) != 0 || (i & 1024) != 0) {
            return false;
        }
        for (String str2 : DEFAULT_WAL_BLOCKLIST) {
            if (str.endsWith(str2)) {
                return false;
            }
        }
        for (String str3 : DEFAULT_WAL_ALLOWLIST) {
            if (str.contains(str3)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDefaultDumpCorruptDb(String str) {
        for (String str2 : DEFAULT_DUMP_CORRUPT_ALLOWLIST) {
            if (str.endsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMediaStoreDb(String str) {
        return str != null && str.endsWith(MEDIA_STORE_EXTERNAL_DB);
    }

    public boolean isQueryCollectDb(String str) {
        if (this.shouldSendQueryLog < 0) {
            for (String str2 : QUERY_COLLECT_PACKAGES) {
                if (str.contains(str2)) {
                    this.shouldSendQueryLog = 1;
                    return true;
                }
            }
            this.shouldSendQueryLog = 0;
        }
        return this.shouldSendQueryLog == 1;
    }

    public boolean isSingleConnectionWalDb(String str) {
        for (String str2 : DEFAULT_SINGLE_CONNECTION_WAL_LIST) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public long getAutoCheckpoint() {
        if (this.isMediaStoreDb) {
            return 256L;
        }
        return SQLiteGlobal.getWALAutoCheckpoint();
    }

    public long getJournalSizeLimit() {
        if (this.isMediaStoreDb) {
            return 1048576L;
        }
        return SQLiteGlobal.getJournalSizeLimit();
    }

    public String getDefaultSyncMode() {
        if (this.isSecureDb || this.useSingleConnectionWal) {
            return "FULL";
        }
        return SQLiteGlobal.getDefaultSyncMode();
    }

    public void setUserDataRecovery(boolean z) {
        this.useUserDataRecovery = z;
    }
}
