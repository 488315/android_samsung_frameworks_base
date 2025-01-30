package com.android.server.grammaticalinflection;

import android.app.backup.BackupManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class GrammaticalInflectionBackupHelper {
  public static final Duration STAGE_DATA_RETENTION_PERIOD = Duration.ofDays(3);
  public static final String TAG = "GrammaticalInflectionBackupHelper";
  public final SparseArray mCache = new SparseArray();
  public final Object mCacheLock = new Object();
  public final Clock mClock = Clock.systemUTC();
  public final GrammaticalInflectionService mGrammaticalGenderService;
  public final PackageManager mPackageManager;

  public class StagedData {
    public final long mCreationTimeMillis;
    public final HashMap mPackageStates = new HashMap();

    public StagedData(long j) {
      this.mCreationTimeMillis = j;
    }
  }

  public GrammaticalInflectionBackupHelper(
      GrammaticalInflectionService grammaticalInflectionService, PackageManager packageManager) {
    this.mGrammaticalGenderService = grammaticalInflectionService;
    this.mPackageManager = packageManager;
  }

  public byte[] getBackupPayload(int i) {
    synchronized (this.mCacheLock) {
      cleanStagedDataForOldEntries();
    }
    HashMap hashMap = new HashMap();
    for (ApplicationInfo applicationInfo :
        this.mPackageManager.getInstalledApplicationsAsUser(
            PackageManager.ApplicationInfoFlags.of(0L), i)) {
      int applicationGrammaticalGender =
          this.mGrammaticalGenderService.getApplicationGrammaticalGender(
              applicationInfo.packageName, i);
      if (applicationGrammaticalGender != 0) {
        hashMap.put(applicationInfo.packageName, Integer.valueOf(applicationGrammaticalGender));
      }
    }
    if (hashMap.isEmpty()) {
      return null;
    }
    return convertToByteArray(hashMap);
  }

  public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
    synchronized (this.mCacheLock) {
      cleanStagedDataForOldEntries();
      HashMap readFromByteArray = readFromByteArray(bArr);
      if (readFromByteArray.isEmpty()) {
        return;
      }
      StagedData stagedData = new StagedData(this.mClock.millis());
      for (Map.Entry entry : readFromByteArray.entrySet()) {
        if (isPackageInstalledForUser((String) entry.getKey(), i)) {
          if (!hasSetBeforeRestoring((String) entry.getKey(), i)) {
            this.mGrammaticalGenderService.setRequestedApplicationGrammaticalGender(
                (String) entry.getKey(), i, ((Integer) entry.getValue()).intValue());
          }
        } else if (((Integer) entry.getValue()).intValue() != 0) {
          stagedData.mPackageStates.put((String) entry.getKey(), (Integer) entry.getValue());
        }
      }
      this.mCache.append(i, stagedData);
    }
  }

  public final boolean hasSetBeforeRestoring(String str, int i) {
    return this.mGrammaticalGenderService.getApplicationGrammaticalGender(str, i) != 0;
  }

  public void onPackageAdded(String str, int i) {
    int intValue;
    synchronized (this.mCacheLock) {
      int userId = UserHandle.getUserId(i);
      StagedData stagedData = (StagedData) this.mCache.get(userId);
      if (stagedData != null
          && stagedData.mPackageStates.containsKey(str)
          && (intValue = ((Integer) stagedData.mPackageStates.get(str)).intValue()) != 0) {
        this.mGrammaticalGenderService.setRequestedApplicationGrammaticalGender(
            str, userId, intValue);
      }
    }
  }

  public void onPackageDataCleared() {
    notifyBackupManager();
  }

  public void onPackageRemoved() {
    notifyBackupManager();
  }

  public static void notifyBackupManager() {
    BackupManager.dataChanged("android");
  }

  public final byte[] convertToByteArray(HashMap hashMap) {
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      try {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try {
          objectOutputStream.writeObject(hashMap);
          byte[] byteArray = byteArrayOutputStream.toByteArray();
          objectOutputStream.close();
          byteArrayOutputStream.close();
          return byteArray;
        } finally {
        }
      } finally {
      }
    } catch (IOException e) {
      Log.e(TAG, "cannot convert payload to byte array.", e);
      return null;
    }
  }

  public final HashMap readFromByteArray(byte[] bArr) {
    HashMap hashMap;
    Exception e;
    ByteArrayInputStream byteArrayInputStream;
    Throwable th;
    HashMap hashMap2 = new HashMap();
    try {
      try {
        byteArrayInputStream = new ByteArrayInputStream(bArr);
      } catch (IOException | ClassNotFoundException e2) {
        e = e2;
        Log.e(TAG, "cannot convert payload to HashMap.", e);
        e.printStackTrace();
        return hashMap;
      }
    } catch (IOException | ClassNotFoundException e3) {
      hashMap = hashMap2;
      e = e3;
      Log.e(TAG, "cannot convert payload to HashMap.", e);
      e.printStackTrace();
      return hashMap;
    }
    try {
      ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
      try {
        hashMap = (HashMap) objectInputStream.readObject();
        try {
          objectInputStream.close();
          byteArrayInputStream.close();
          return hashMap;
        } catch (Throwable th2) {
          th = th2;
          try {
            byteArrayInputStream.close();
          } catch (Throwable th3) {
            th.addSuppressed(th3);
          }
          throw th;
        }
      } finally {
      }
    } catch (Throwable th4) {
      th = th4;
      byteArrayInputStream.close();
      throw th;
    }
  }

  public final void cleanStagedDataForOldEntries() {
    for (int i = 0; i < this.mCache.size(); i++) {
      int keyAt = this.mCache.keyAt(i);
      if (((StagedData) this.mCache.get(keyAt)).mCreationTimeMillis
          < this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
        this.mCache.remove(keyAt);
      }
    }
  }

  public final boolean isPackageInstalledForUser(String str, int i) {
    PackageInfo packageInfo;
    try {
      packageInfo = this.mPackageManager.getPackageInfoAsUser(str, 0, i);
    } catch (PackageManager.NameNotFoundException unused) {
      packageInfo = null;
    }
    return packageInfo != null;
  }
}
