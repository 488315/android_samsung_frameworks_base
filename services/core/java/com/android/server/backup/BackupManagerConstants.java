package com.android.server.backup;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.KeyValueSettingObserver;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupManagerConstants extends KeyValueSettingObserver {
    public static final String BACKUP_FINISHED_NOTIFICATION_RECEIVERS =
            "backup_finished_notification_receivers";
    public static final String DEFAULT_BACKUP_FINISHED_NOTIFICATION_RECEIVERS = "";
    public static final long DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS = 86400000;
    public static final int DEFAULT_FULL_BACKUP_REQUIRED_NETWORK_TYPE = 2;
    public static final boolean DEFAULT_FULL_BACKUP_REQUIRE_CHARGING = true;
    public static final long DEFAULT_KEY_VALUE_BACKUP_FUZZ_MILLISECONDS = 600000;
    public static final long DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS = 14400000;
    public static final int DEFAULT_KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE = 1;
    public static final boolean DEFAULT_KEY_VALUE_BACKUP_REQUIRE_CHARGING = true;
    public static final String FULL_BACKUP_INTERVAL_MILLISECONDS =
            "full_backup_interval_milliseconds";
    public static final String FULL_BACKUP_REQUIRED_NETWORK_TYPE =
            "full_backup_required_network_type";
    public static final String FULL_BACKUP_REQUIRE_CHARGING = "full_backup_require_charging";
    public static final String KEY_VALUE_BACKUP_FUZZ_MILLISECONDS =
            "key_value_backup_fuzz_milliseconds";
    public static final String KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS =
            "key_value_backup_interval_milliseconds";
    public static final String KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE =
            "key_value_backup_required_network_type";
    public static final String KEY_VALUE_BACKUP_REQUIRE_CHARGING =
            "key_value_backup_require_charging";
    public String[] mBackupFinishedNotificationReceivers;
    public long mFullBackupIntervalMilliseconds;
    public boolean mFullBackupRequireCharging;
    public int mFullBackupRequiredNetworkType;
    public long mKeyValueBackupFuzzMilliseconds;
    public long mKeyValueBackupIntervalMilliseconds;
    public boolean mKeyValueBackupRequireCharging;
    public int mKeyValueBackupRequiredNetworkType;

    public final synchronized long getFullBackupIntervalMilliseconds() {
        Slog.v(
                "BackupManagerConstants",
                "getFullBackupIntervalMilliseconds(...) returns "
                        + this.mFullBackupIntervalMilliseconds);
        return this.mFullBackupIntervalMilliseconds;
    }

    public final synchronized long getKeyValueBackupIntervalMilliseconds() {
        Slog.v(
                "BackupManagerConstants",
                "getKeyValueBackupIntervalMilliseconds(...) returns "
                        + this.mKeyValueBackupIntervalMilliseconds);
        return this.mKeyValueBackupIntervalMilliseconds;
    }

    public final String getSettingValue(ContentResolver contentResolver) {
        return Settings.Secure.getStringForUser(
                contentResolver, "backup_manager_constants", contentResolver.getUserId());
    }

    public final synchronized void update(KeyValueListParser keyValueListParser) {
        try {
            this.mKeyValueBackupIntervalMilliseconds =
                    keyValueListParser.getLong(
                            KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS,
                            DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
            this.mKeyValueBackupFuzzMilliseconds =
                    keyValueListParser.getLong(KEY_VALUE_BACKUP_FUZZ_MILLISECONDS, 600000L);
            this.mKeyValueBackupRequireCharging =
                    keyValueListParser.getBoolean(KEY_VALUE_BACKUP_REQUIRE_CHARGING, true);
            this.mKeyValueBackupRequiredNetworkType =
                    keyValueListParser.getInt(KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE, 1);
            this.mFullBackupIntervalMilliseconds =
                    keyValueListParser.getLong(
                            FULL_BACKUP_INTERVAL_MILLISECONDS,
                            DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            this.mFullBackupRequireCharging =
                    keyValueListParser.getBoolean(FULL_BACKUP_REQUIRE_CHARGING, true);
            this.mFullBackupRequiredNetworkType =
                    keyValueListParser.getInt(FULL_BACKUP_REQUIRED_NETWORK_TYPE, 2);
            String string =
                    keyValueListParser.getString(BACKUP_FINISHED_NOTIFICATION_RECEIVERS, "");
            if (string.isEmpty()) {
                this.mBackupFinishedNotificationReceivers = new String[0];
            } else {
                this.mBackupFinishedNotificationReceivers = string.split(":");
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
