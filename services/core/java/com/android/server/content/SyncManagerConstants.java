package com.android.server.content;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;

public final class SyncManagerConstants extends ContentObserver {
    public final Context mContext;
    public int mInitialSyncRetryTimeInSeconds;
    public int mKeyExemptionTempWhitelistDurationInSeconds;
    public final Object mLock;
    public int mMaxRetriesWithAppStandbyExemption;
    public int mMaxSyncRetryTimeInSeconds;
    public float mRetryTimeIncreaseFactor;

    public SyncManagerConstants(Context context) {
        super(null);
        this.mLock = new Object();
        this.mInitialSyncRetryTimeInSeconds = 30;
        this.mRetryTimeIncreaseFactor = 2.0f;
        this.mMaxSyncRetryTimeInSeconds = 3600;
        this.mMaxRetriesWithAppStandbyExemption = 5;
        this.mKeyExemptionTempWhitelistDurationInSeconds = 600;
        this.mContext = context;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        refresh();
    }

    public final void refresh() {
        synchronized (this.mLock) {
            String string =
                    Settings.Global.getString(
                            this.mContext.getContentResolver(), "sync_manager_constants");
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            try {
                keyValueListParser.setString(string);
            } catch (IllegalArgumentException unused) {
                Slog.wtf("SyncManagerConfig", "Bad constants: " + string);
            }
            this.mInitialSyncRetryTimeInSeconds =
                    keyValueListParser.getInt("initial_sync_retry_time_in_seconds", 30);
            this.mMaxSyncRetryTimeInSeconds =
                    keyValueListParser.getInt("max_sync_retry_time_in_seconds", 3600);
            this.mRetryTimeIncreaseFactor =
                    keyValueListParser.getFloat("retry_time_increase_factor", 2.0f);
            this.mMaxRetriesWithAppStandbyExemption =
                    keyValueListParser.getInt("max_retries_with_app_standby_exemption", 5);
            this.mKeyExemptionTempWhitelistDurationInSeconds =
                    keyValueListParser.getInt("exemption_temp_whitelist_duration_in_seconds", 600);
        }
    }
}
