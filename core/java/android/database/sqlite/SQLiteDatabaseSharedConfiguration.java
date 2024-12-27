package android.database.sqlite;

public final class SQLiteDatabaseSharedConfiguration {
    private static final long MEDIA_STORE_AUTOCHECK_POINT = 256;
    private static final String MEDIA_STORE_EXTERNAL_DB =
            "providers.media.module/databases/external.db";
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
    private static final String[] DEFAULT_WAL_BLOCKLIST = {
        "/EmailProvider.db", "/EmailProviderBody.db", "/iwlansettings.db"
    };
    private static final String[] QUERY_COLLECT_PACKAGES = {
        "/com.samsung.", "/com.sec.", "/data/system/", "/com.google.", "/com.android.providers."
    };
    private static final String[] DEFAULT_SINGLE_CONNECTION_WAL_LIST = {
        "/data/system/notification_log.db"
    };
    private static final String[] DEFAULT_DUMP_CORRUPT_ALLOWLIST = {
        "contacts2.db", "SecureHealthData.db"
    };

    public SQLiteDatabaseSharedConfiguration(SQLiteDatabaseConfiguration config) {
        this.isSecureDb = (config.openFlags & 512) != 0;
        this.isMediaStoreDb = isMediaStoreDb(config.path);
        this.useSingleConnectionWal = isSingleConnectionWalDb(config.path);
        if (this.useSingleConnectionWal) {
            this.useWalModeByDefault = true;
        } else {
            this.useWalModeByDefault = isDefaultWalDb(config.path, config.openFlags);
        }
        this.useDumpCorruptByDefault = isDefaultDumpCorruptDb(config.path);
        this.useAssertionLog = this.useDumpCorruptByDefault;
    }

    private boolean isDefaultWalDb(String path, int openFlags) {
        if ((openFlags & 512) != 0 || (openFlags & 1024) != 0) {
            return false;
        }
        for (String s : DEFAULT_WAL_BLOCKLIST) {
            if (path.endsWith(s)) {
                return false;
            }
        }
        for (String s2 : DEFAULT_WAL_ALLOWLIST) {
            if (path.contains(s2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDefaultDumpCorruptDb(String path) {
        for (String s : DEFAULT_DUMP_CORRUPT_ALLOWLIST) {
            if (path.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMediaStoreDb(String path) {
        if (path != null && path.endsWith(MEDIA_STORE_EXTERNAL_DB)) {
            return true;
        }
        return false;
    }

    public boolean isQueryCollectDb(String path) {
        if (this.shouldSendQueryLog < 0) {
            for (String s : QUERY_COLLECT_PACKAGES) {
                if (path.contains(s)) {
                    this.shouldSendQueryLog = 1;
                    return true;
                }
            }
            this.shouldSendQueryLog = 0;
        }
        return this.shouldSendQueryLog == 1;
    }

    public boolean isSingleConnectionWalDb(String path) {
        for (String s : DEFAULT_SINGLE_CONNECTION_WAL_LIST) {
            if (path.equals(s)) {
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

    public void setUserDataRecovery(boolean use) {
        this.useUserDataRecovery = use;
    }
}
