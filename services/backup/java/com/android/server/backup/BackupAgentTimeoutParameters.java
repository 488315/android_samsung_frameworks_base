package com.android.server.backup;

import android.content.ContentResolver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.KeyValueSettingObserver;

/* loaded from: classes.dex */
public class BackupAgentTimeoutParameters extends KeyValueSettingObserver {
  public static final long DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS = 300000;
  public static final long DEFAULT_KV_BACKUP_AGENT_TIMEOUT_MILLIS = 30000;
  public static final long DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS = 3000;
  public static final long DEFAULT_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS = 30000;
  public static final long DEFAULT_RESTORE_AGENT_TIMEOUT_MILLIS = 60000;
  public static final long DEFAULT_RESTORE_SESSION_TIMEOUT_MILLIS = 60000;
  public static final long DEFAULT_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS = 180000;
  public static final long DEFAULT_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS = 1800000;
  public static final String SETTING = "backup_agent_timeout_parameters";
  public static final String SETTING_FULL_BACKUP_AGENT_TIMEOUT_MILLIS =
      "full_backup_agent_timeout_millis";
  public static final String SETTING_KV_BACKUP_AGENT_TIMEOUT_MILLIS =
      "kv_backup_agent_timeout_millis";
  public static final String SETTING_QUOTA_EXCEEDED_TIMEOUT_MILLIS =
      "quota_exceeded_timeout_millis";
  public static final String SETTING_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS =
      "restore_agent_finished_timeout_millis";
  public static final String SETTING_RESTORE_AGENT_TIMEOUT_MILLIS = "restore_agent_timeout_millis";
  public static final String SETTING_RESTORE_SESSION_TIMEOUT_MILLIS =
      "restore_session_timeout_millis";
  public static final String SETTING_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS =
      "restore_system_agent_timeout_millis";
  public static final String SETTING_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS =
      "shared_backup_agent_timeout_millis";
  public long mFullBackupAgentTimeoutMillis;
  public long mKvBackupAgentTimeoutMillis;
  public final Object mLock;
  public long mQuotaExceededTimeoutMillis;
  public long mRestoreAgentFinishedTimeoutMillis;
  public long mRestoreAgentTimeoutMillis;
  public long mRestoreSessionTimeoutMillis;
  public long mRestoreSystemAgentTimeoutMillis;
  public long mSharedBackupAgentTimeoutMillis;

  public BackupAgentTimeoutParameters(Handler handler, ContentResolver contentResolver) {
    super(handler, contentResolver, Settings.Global.getUriFor(SETTING));
    this.mLock = new Object();
  }

  public String getSettingValue(ContentResolver contentResolver) {
    return Settings.Global.getString(contentResolver, SETTING);
  }

  public void update(KeyValueListParser keyValueListParser) {
    synchronized (this.mLock) {
      this.mKvBackupAgentTimeoutMillis =
          keyValueListParser.getLong(SETTING_KV_BACKUP_AGENT_TIMEOUT_MILLIS, 30000L);
      this.mFullBackupAgentTimeoutMillis =
          keyValueListParser.getLong(
              SETTING_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
      this.mSharedBackupAgentTimeoutMillis =
          keyValueListParser.getLong(SETTING_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS, 1800000L);
      this.mRestoreAgentTimeoutMillis =
          keyValueListParser.getLong(SETTING_RESTORE_AGENT_TIMEOUT_MILLIS, 60000L);
      this.mRestoreSystemAgentTimeoutMillis =
          keyValueListParser.getLong(SETTING_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS, 180000L);
      this.mRestoreAgentFinishedTimeoutMillis =
          keyValueListParser.getLong(SETTING_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS, 30000L);
      this.mRestoreSessionTimeoutMillis =
          keyValueListParser.getLong(SETTING_RESTORE_SESSION_TIMEOUT_MILLIS, 60000L);
      this.mQuotaExceededTimeoutMillis =
          keyValueListParser.getLong(SETTING_QUOTA_EXCEEDED_TIMEOUT_MILLIS, 3000L);
    }
  }

  public long getKvBackupAgentTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mKvBackupAgentTimeoutMillis;
    }
    return j;
  }

  public long getFullBackupAgentTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mFullBackupAgentTimeoutMillis;
    }
    return j;
  }

  public long getSharedBackupAgentTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mSharedBackupAgentTimeoutMillis;
    }
    return j;
  }

  public long getRestoreAgentTimeoutMillis(int i) {
    long j;
    synchronized (this.mLock) {
      j =
          UserHandle.isCore(i)
              ? this.mRestoreSystemAgentTimeoutMillis
              : this.mRestoreAgentTimeoutMillis;
    }
    return j;
  }

  public long getRestoreSessionTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mRestoreSessionTimeoutMillis;
    }
    return j;
  }

  public long getRestoreAgentFinishedTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mRestoreAgentFinishedTimeoutMillis;
    }
    return j;
  }

  public long getQuotaExceededTimeoutMillis() {
    long j;
    synchronized (this.mLock) {
      j = this.mQuotaExceededTimeoutMillis;
    }
    return j;
  }
}
