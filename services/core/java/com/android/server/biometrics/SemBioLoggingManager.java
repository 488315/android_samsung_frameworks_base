package com.android.server.biometrics;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes.dex */
public class SemBioLoggingManager {
  public static final boolean DEBUG;
  public static final int LOG_ARRAY_SIZE;
  public static SemBioLoggingManager sInstance;
  public static final Object sInstanceLock;
  public boolean mIsFpBioStarEnabled;
  public String mLastFaceEnrollLog;
  public String mLastFaceRemoveLog;
  public String mLastFpEnrollLog;
  public String mLastFpRemoveLog;
  public String mLastFpAuthenticatorId = "Unknown";
  public final ArrayList mFpAuthLogList = new ArrayList();
  public final ArrayList mFaceAuthLogList = new ArrayList();
  public final ArrayList mBpAuthLogList = new ArrayList();
  public final SparseArray mFpLoggingInfo = new SparseArray();
  public final SparseArray mFaceLoggingInfo = new SparseArray();
  public final SparseArray mBpLoggingInfo = new SparseArray();

  static {
    boolean z = Utils.DEBUG;
    DEBUG = z;
    LOG_ARRAY_SIZE = z ? 50 : 20;
    sInstanceLock = new Object();
  }

  public class LoggingInfo {
    public int mBadQualityCount;
    public int mExtra;
    public int mFpAlphaMaskLvl;
    public int mFpScreenStatus;
    public long mLatency;
    public String mPackageName;
    public String mResult;
    public long mResultTime;
    public long mStartTime;
    public String mTouchMap;
    public String mType;

    public LoggingInfo() {
      this.mFpScreenStatus = -1;
      this.mFpAlphaMaskLvl = -1;
    }

    public String toString() {
      return "LoggingInfo{mType='"
          + this.mType
          + "', mPackageName='"
          + this.mPackageName
          + "', mStartTime="
          + this.mStartTime
          + ", mLatency="
          + this.mLatency
          + ", mResultTime="
          + this.mResultTime
          + ", mExtra="
          + this.mExtra
          + ", mBadQualityCount="
          + this.mBadQualityCount
          + '}';
    }

    public String toDumpFormat() {
      Locale locale = Locale.ENGLISH;
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss.SSS", locale);
      try {
        return String.format(
            locale,
            "%s, %s, %s, %s, %d, %d, %d, %s",
            this.mType,
            this.mPackageName,
            simpleDateFormat.format(new Date(this.mStartTime)),
            this.mResultTime == 0
                ? "-----------"
                : simpleDateFormat.format(new Date(this.mResultTime)),
            Long.valueOf(this.mLatency),
            Integer.valueOf(this.mExtra),
            Integer.valueOf(this.mBadQualityCount),
            this.mResult);
      } catch (Exception e) {
        Slog.w("BiometricStats", "toDumpFormat: " + e.getMessage());
        return "formatting error";
      }
    }

    public String getFpOpticalInfo() {
      return ", S:" + this.mFpScreenStatus + ", A:" + this.mFpAlphaMaskLvl;
    }

    public void updateTouchMapData() {
      byte[] readFile = Utils.readFile(new File("/sys/class/sec/tsp/fod_pos"));
      if (readFile == null || readFile.length == 0) {
        return;
      }
      this.mTouchMap = new String(readFile, StandardCharsets.UTF_8).trim();
    }

    public void sendCaptureFailedInfo(Context context, boolean z) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 3);
      intent.putExtra("quality_value", this.mExtra);
      sendBroadcast(context, intent, z);
    }

    public void sendMatchInfo(Context context, int i, boolean z) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 1);
      intent.putExtra("id", i);
      sendBroadcast(context, intent, z);
    }

    public void sendNoMatchInfo(Context context, int i, boolean z) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 2);
      intent.putExtra("no_match_reason", i);
      sendBroadcast(context, intent, z);
    }

    public final void sendBroadcast(Context context, Intent intent, boolean z) {
      String str;
      if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE
          && (str = this.mTouchMap) != null) {
        intent.putExtra("touch_map", str);
      }
      intent.putExtra("screen_status", z);
      intent.putExtra("pkg_name", this.mPackageName);
      try {
        context.sendBroadcastAsUser(
            intent, UserHandle.CURRENT, "android.permission.MANAGE_BIOMETRIC");
      } catch (Exception e) {
        Slog.w("BiometricStats", "sendBroadcast: " + e.getMessage());
      }
    }

    public final void sendFaceMatchInfo(Context context, int i, int i2) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 11);
      intent.putExtra("match_id", i);
      intent.putExtra("enrolled_position", i2);
      sendBroadcastFaceInfo(context, intent);
    }

    public final void sendFaceNoMatchInfo(Context context, int i) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 12);
      intent.putExtra("reason", i);
      sendBroadcastFaceInfo(context, intent);
    }

    public final void sendFaceTimeoutInfo(Context context, int i) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 13);
      intent.putExtra("reason", i);
      sendBroadcastFaceInfo(context, intent);
    }

    public final void sendFaceErrorInfo(Context context, int i, int i2) {
      Intent intent = new Intent("com.samsung.android.intent.action.BIOMETRIC_EXTRA_INFO");
      intent.putExtra("action_type", 14);
      intent.putExtra("error", i);
      intent.putExtra("vendor", i2);
      sendBroadcastFaceInfo(context, intent);
    }

    public final void sendBroadcastFaceInfo(Context context, Intent intent) {
      intent.putExtra("pkg_name", this.mPackageName);
      if (SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP
          || SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD) {
        if (Utils.isFolderOpened(context) || Utils.isFlipOpened(context)) {
          intent.putExtra("fold_state", 1);
        } else {
          intent.putExtra("fold_state", 2);
        }
      } else {
        intent.putExtra("fold_state", 0);
      }
      try {
        context.sendBroadcastAsUser(
            intent, UserHandle.CURRENT, "android.permission.MANAGE_BIOMETRIC");
      } catch (Exception e) {
        Slog.w("BiometricStats", "sendBroadcast: " + e.getMessage());
      }
    }
  }

  public static SemBioLoggingManager get() {
    synchronized (sInstanceLock) {
      if (sInstance == null) {
        sInstance = new SemBioLoggingManager();
      }
    }
    return sInstance;
  }

  public void setFpBioStar(boolean z) {
    if (Utils.DEBUG) {
      Slog.d("BiometricStats", "FP BIO STAR: " + z);
    }
    this.mIsFpBioStarEnabled = z;
  }

  public void fpStart(final int i, final String str, final String str2) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpStart$0(str, str2, i);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpStart$0(String str, String str2, int i) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = new LoggingInfo();
      loggingInfo.mStartTime = System.currentTimeMillis();
      loggingInfo.mType = str;
      loggingInfo.mPackageName = str2;
      this.mFpLoggingInfo.append(i, loggingInfo);
      if (this.mFpLoggingInfo.size() >= LOG_ARRAY_SIZE) {
        this.mFpLoggingInfo.removeAt(0);
      }
    }
  }

  public void fpMatch(
      final Context context,
      final int i,
      final long j,
      final int i2,
      final int i3,
      final int i4,
      final boolean z) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda6
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpMatch$1(i, j, i2, i3, context, i4, z);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpMatch$1(
      int i, long j, int i2, int i3, Context context, int i4, boolean z) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mResultTime = System.currentTimeMillis();
        loggingInfo.mLatency = j;
        loggingInfo.mResult = "M";
        loggingInfo.mExtra = i2;
        loggingInfo.mBadQualityCount = i3;
        fpAddLoggingInfo(loggingInfo);
        this.mFpLoggingInfo.delete(i);
        if (this.mIsFpBioStarEnabled) {
          loggingInfo.sendMatchInfo(context, i4, z);
        }
      }
    }
  }

  public void fpNoMatch(
      final Context context,
      final int i,
      final long j,
      final int i2,
      final int i3,
      final boolean z) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda9
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpNoMatch$2(i, j, i2, i3, context, z);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpNoMatch$2(
      int i, long j, int i2, int i3, Context context, boolean z) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mResultTime = System.currentTimeMillis();
        loggingInfo.mLatency = j;
        loggingInfo.mResult = "N";
        loggingInfo.mExtra = i2;
        loggingInfo.mBadQualityCount = i3;
        fpAddLoggingInfo(loggingInfo);
        if (this.mIsFpBioStarEnabled) {
          loggingInfo.sendNoMatchInfo(context, i2, z);
        }
      }
    }
  }

  public void fpUpdateUdfpsTouchMap(final int i) {
    if (this.mIsFpBioStarEnabled && SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
      SemBioFgThread.get()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                  SemBioLoggingManager.this.lambda$fpUpdateUdfpsTouchMap$3(i);
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpUpdateUdfpsTouchMap$3(int i) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.updateTouchMapData();
      }
    }
  }

  public void fpCaptureFailed(
      final Context context, final int i, final long j, final int i2, final boolean z) {
    if (this.mIsFpBioStarEnabled) {
      SemBioFgThread.get()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                  SemBioLoggingManager.this.lambda$fpCaptureFailed$4(i, j, i2, context, z);
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpCaptureFailed$4(
      int i, long j, int i2, Context context, boolean z) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mResultTime = System.currentTimeMillis();
        loggingInfo.mLatency = j;
        loggingInfo.mExtra = i2;
        loggingInfo.sendCaptureFailedInfo(context, z);
      }
    }
  }

  public void fpStop(final int i, final String str, final long j, final int i2, final int i3) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpStop$5(i, j, str, i2, i3);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpStop$5(int i, long j, String str, int i2, int i3) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mResultTime = System.currentTimeMillis();
        loggingInfo.mLatency = j;
        loggingInfo.mResult = str;
        loggingInfo.mExtra = i2;
        loggingInfo.mBadQualityCount = i3;
        fpAddLoggingInfo(loggingInfo);
        this.mFpLoggingInfo.delete(i);
      }
    }
  }

  public void fpRemoved(final String str, final int i) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpRemoved$6(str, i);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpRemoved$6(String str, int i) {
    LoggingInfo loggingInfo = new LoggingInfo();
    loggingInfo.mType = "R";
    long currentTimeMillis = System.currentTimeMillis();
    loggingInfo.mStartTime = currentTimeMillis;
    loggingInfo.mResultTime = currentTimeMillis;
    loggingInfo.mPackageName = str;
    loggingInfo.mExtra = i;
    fpAddLoggingInfo(loggingInfo);
  }

  public void fpSetOpticalInfo(final int i, final int i2, final int i3) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpSetOpticalInfo$7(i, i2, i3);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpSetOpticalInfo$7(int i, int i2, int i3) {
    synchronized (this.mFpLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mFpScreenStatus = i2;
        loggingInfo.mFpAlphaMaskLvl = i3;
      }
    }
  }

  public void fpSetAuthenticatorId(final long j) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda7
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$fpSetAuthenticatorId$8(j);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$fpSetAuthenticatorId$8(long j) {
    synchronized (this.mFpAuthLogList) {
      try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm:ss", Locale.ENGLISH);
        StringBuilder sb = new StringBuilder();
        sb.append(j == 0 ? "0L" : Integer.toString((int) j));
        sb.append(", ");
        sb.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        this.mLastFpAuthenticatorId = sb.toString();
      } catch (Exception e) {
        Slog.w("BiometricStats", "fpSetAuthenticatorId: " + e.getMessage());
      }
    }
  }

  public void fpDump(PrintWriter printWriter) {
    synchronized (this.mFpAuthLogList) {
      printWriter.println("AID: " + this.mLastFpAuthenticatorId);
      if (this.mLastFpEnrollLog != null) {
        printWriter.println("Enroll: " + this.mLastFpEnrollLog);
      }
      if (this.mLastFpRemoveLog != null) {
        printWriter.println("Remove: " + this.mLastFpRemoveLog);
      }
      Iterator it = this.mFpAuthLogList.iterator();
      while (it.hasNext()) {
        printWriter.println((String) it.next());
      }
    }
    synchronized (this.mFpLoggingInfo) {
      int size = this.mFpLoggingInfo.size();
      if (size > 0) {
        printWriter.println(" [ in progress client ]");
        for (int i = 0; i < size; i++) {
          LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.valueAt(i);
          if (loggingInfo != null) {
            printWriter.println(" - " + loggingInfo.toDumpFormat());
          }
        }
      }
    }
    printWriter.println();
  }

  public final void fpAddLoggingInfo(LoggingInfo loggingInfo) {
    synchronized (this.mFpAuthLogList) {
      String dumpFormat = loggingInfo.toDumpFormat();
      if ("E".equals(loggingInfo.mType)) {
        this.mLastFpEnrollLog = dumpFormat;
      } else if ("R".equals(loggingInfo.mType)) {
        this.mLastFpRemoveLog = dumpFormat;
      } else if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
        dumpFormat = dumpFormat + loggingInfo.getFpOpticalInfo();
      }
      this.mFpAuthLogList.add(dumpFormat);
      if (this.mFpAuthLogList.size() > LOG_ARRAY_SIZE) {
        this.mFpAuthLogList.remove(0);
      }
    }
  }

  public void faceStart(int i, String str, String str2) {
    synchronized (this.mFaceLoggingInfo) {
      LoggingInfo loggingInfo = new LoggingInfo();
      loggingInfo.mStartTime = System.currentTimeMillis();
      loggingInfo.mType = str;
      loggingInfo.mPackageName = str2;
      this.mFaceLoggingInfo.append(i, loggingInfo);
      if (this.mFaceLoggingInfo.size() >= LOG_ARRAY_SIZE) {
        this.mFaceLoggingInfo.removeAt(0);
      }
    }
  }

  public void faceMatch(Context context, int i, long j, int i2, int i3) {
    if (this.mIsFpBioStarEnabled) {
      synchronized (this.mFaceLoggingInfo) {
        LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
        Log.d("BiometricStats", "faceMatch id: " + i + ", extra: " + i3);
        if (loggingInfo != null) {
          loggingInfo.sendFaceMatchInfo(context, i2, i3);
        }
      }
    }
  }

  public void faceNoMatch(Context context, int i, long j, int i2, int i3) {
    if (this.mIsFpBioStarEnabled) {
      synchronized (this.mFaceLoggingInfo) {
        LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
        if (loggingInfo != null) {
          loggingInfo.sendFaceNoMatchInfo(context, i2);
        }
      }
    }
  }

  public void faceTimeout(Context context, int i, long j, int i2, int i3) {
    if (this.mIsFpBioStarEnabled) {
      synchronized (this.mFaceLoggingInfo) {
        LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
        if (loggingInfo != null) {
          loggingInfo.sendFaceTimeoutInfo(context, i2);
        }
      }
    }
  }

  public void faceError(Context context, int i, long j, int i2, int i3) {
    if (this.mIsFpBioStarEnabled) {
      synchronized (this.mFaceLoggingInfo) {
        LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
        if (loggingInfo != null) {
          loggingInfo.sendFaceErrorInfo(context, i2, i3);
        }
      }
    }
  }

  public void faceStop(final int i, final String str, final long j, final int i2) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$faceStop$9(i, j, str, i2);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$faceStop$9(int i, long j, String str, int i2) {
    synchronized (this.mFaceLoggingInfo) {
      LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
      if (loggingInfo != null) {
        loggingInfo.mResultTime = System.currentTimeMillis();
        loggingInfo.mLatency = j;
        loggingInfo.mResult = str;
        loggingInfo.mExtra = i2;
        faceAddLoggingInfo(loggingInfo);
        this.mFaceLoggingInfo.delete(i);
      }
    }
  }

  public void faceRemoved(final String str, final int i) {
    SemBioFgThread.get()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.SemBioLoggingManager$$ExternalSyntheticLambda4
              @Override // java.lang.Runnable
              public final void run() {
                SemBioLoggingManager.this.lambda$faceRemoved$10(str, i);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$faceRemoved$10(String str, int i) {
    LoggingInfo loggingInfo = new LoggingInfo();
    loggingInfo.mType = "R";
    long currentTimeMillis = System.currentTimeMillis();
    loggingInfo.mStartTime = currentTimeMillis;
    loggingInfo.mResultTime = currentTimeMillis;
    loggingInfo.mPackageName = str;
    loggingInfo.mExtra = i;
    faceAddLoggingInfo(loggingInfo);
  }

  public void faceDump(PrintWriter printWriter) {
    synchronized (this.mFaceAuthLogList) {
      if (this.mLastFaceEnrollLog != null) {
        printWriter.println("Enroll: " + this.mLastFaceEnrollLog);
      }
      if (this.mLastFaceRemoveLog != null) {
        printWriter.println("Remove: " + this.mLastFaceRemoveLog);
      }
      Iterator it = this.mFaceAuthLogList.iterator();
      while (it.hasNext()) {
        printWriter.println((String) it.next());
      }
    }
    synchronized (this.mFaceLoggingInfo) {
      int size = this.mFaceLoggingInfo.size();
      if (size > 0) {
        printWriter.println(" [ in progress client ]");
        for (int i = 0; i < size; i++) {
          LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.valueAt(i);
          if (loggingInfo != null) {
            printWriter.println(" - " + loggingInfo.toDumpFormat());
          }
        }
      }
    }
    printWriter.println();
  }

  public final void faceAddLoggingInfo(LoggingInfo loggingInfo) {
    synchronized (this.mFaceAuthLogList) {
      if ("E".equals(loggingInfo.mType)) {
        this.mLastFaceEnrollLog = loggingInfo.toDumpFormat();
      } else if ("R".equals(loggingInfo.mType)) {
        this.mLastFaceRemoveLog = loggingInfo.toDumpFormat();
      }
      this.mFaceAuthLogList.add(loggingInfo.toDumpFormat());
      if (this.mFaceAuthLogList.size() > LOG_ARRAY_SIZE) {
        this.mFaceAuthLogList.remove(0);
      }
    }
  }

  public void bpStart(int i, String str) {
    LoggingInfo loggingInfo = new LoggingInfo();
    loggingInfo.mStartTime = System.currentTimeMillis();
    loggingInfo.mType = "AP";
    loggingInfo.mPackageName = str;
    this.mBpLoggingInfo.append(i, loggingInfo);
    if (this.mBpLoggingInfo.size() >= LOG_ARRAY_SIZE) {
      this.mBpLoggingInfo.removeAt(0);
    }
  }

  public void bpStop(int i, int i2, int i3) {
    LoggingInfo loggingInfo = (LoggingInfo) this.mBpLoggingInfo.get(i);
    if (loggingInfo != null) {
      loggingInfo.mResultTime = System.currentTimeMillis();
      if (i2 == 0) {
        loggingInfo.mResult = "E";
        loggingInfo.mExtra = i3;
      } else {
        loggingInfo.mResult = "D";
        loggingInfo.mExtra = i2;
      }
      bpAddLoggingInfo(loggingInfo);
      this.mBpLoggingInfo.delete(i);
    }
  }

  public void bpDump(PrintWriter printWriter) {
    Iterator it = this.mBpAuthLogList.iterator();
    while (it.hasNext()) {
      printWriter.println((String) it.next());
    }
    int size = this.mBpLoggingInfo.size();
    if (size > 0) {
      printWriter.println(" [ in progress client ]");
      for (int i = 0; i < size; i++) {
        LoggingInfo loggingInfo = (LoggingInfo) this.mBpLoggingInfo.valueAt(i);
        if (loggingInfo != null) {
          printWriter.println(" - " + loggingInfo.toDumpFormat());
        }
      }
    }
    printWriter.println();
  }

  public final void bpAddLoggingInfo(LoggingInfo loggingInfo) {
    synchronized (this) {
      this.mBpAuthLogList.add(loggingInfo.toDumpFormat());
      if (this.mBpAuthLogList.size() > LOG_ARRAY_SIZE) {
        this.mBpAuthLogList.remove(0);
      }
    }
  }
}
