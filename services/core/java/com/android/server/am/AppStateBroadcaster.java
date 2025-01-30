package com.android.server.am;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.ProcessMap;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class AppStateBroadcaster {
  public static Context mContext = null;
  public static volatile boolean mIsBroadcastEnabled = false;
  public static String mLastFocusAppName;
  public static Handler mObjHandler;
  public static String[] sPackages = {
    "com.tmobile.echolocate", "com.tmobile.pr.mytmobile", "com.sprint.ms.smf.services"
  };
  public static final String[] APP_TERM_REASONS = {
    "NORMAL_SYSTEM_HALT", "USER_HALTED", "ANR", "CRASH"
  };
  public static final HashMap sKnownRunningPackages = new HashMap();

  public static void enableIntentBroadcast(Context context, ProcessMap processMap) {
    mContext = context;
    mIsBroadcastEnabled = true;
    mLastFocusAppName = null;
    mObjHandler = new Handler();
    populateRunningProcesses(processMap);
    TrafficStateDatabaseController.enable(mContext);
  }

  public static void disableIntentBroadcast() {
    mIsBroadcastEnabled = false;
    mLastFocusAppName = null;
    mObjHandler = null;
    TrafficStateDatabaseController.disable();
    HashMap hashMap = sKnownRunningPackages;
    synchronized (hashMap) {
      hashMap.clear();
    }
  }

  public static void sendApplicationStart(String str, int i) {
    if (!mIsBroadcastEnabled || TextUtils.isEmpty(str)) {
      return;
    }
    packageRunning(str, i);
  }

  public static void sendApplicationFocusGain(String str) {
    logOriginFunction("sendApplicationFocusGain(" + str + ")");
    if (!mIsBroadcastEnabled || TextUtils.isEmpty(str)) {
      return;
    }
    String str2 = mLastFocusAppName;
    if (str2 == null || !str.equals(str2)) {
      if (!TextUtils.isEmpty(mLastFocusAppName)) {
        broadcastAppState(mLastFocusAppName, "FOCUS_LOSS");
        Slog.d(
            "AppStateBroadcaster",
            "sendApplicationFocusGain sent APP_STATE_FOCUS_LOSS for " + mLastFocusAppName);
      }
      broadcastAppState(str, "FOCUS_GAIN");
      Slog.d(
          "AppStateBroadcaster",
          "sendApplicationFocusGain sent APP_STATE_FOCUS_GAIN for "
              + str
              + ", old focus package was "
              + mLastFocusAppName);
      mLastFocusAppName = str;
    }
  }

  public static void sendAppKillReason(final String str, final int i, int i2) {
    final int i3 = 0;
    switch (i2) {
      case 4:
      case 5:
        i3 = 3;
        break;
      case 6:
      case 7:
        i3 = 2;
        break;
      case 10:
      case 11:
        i3 = 1;
        break;
    }
    Slog.d("AppStateBroadcaster", "TMO killProcesses");
    Handler handler = mObjHandler;
    if (handler != null) {
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.am.AppStateBroadcaster$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              AppStateBroadcaster.sendApplicationStop(str, i, i3);
            }
          });
    }
  }

  public static void sendApplicationFocusLoss(String str) {
    logOriginFunction("sendApplicationFocusLoss(" + str + ")");
    if (!mIsBroadcastEnabled || TextUtils.isEmpty(str) || TextUtils.isEmpty(mLastFocusAppName)) {
      return;
    }
    broadcastAppState(str, "FOCUS_LOSS");
    Slog.d("AppStateBroadcaster", "sendApplicationFocusLoss sent APP_STATE_FOCUS_LOSS for " + str);
    if (str.equals(mLastFocusAppName)) {
      Slog.d(
          "AppStateBroadcaster",
          "packageName is same as mLastFocusAppName: "
              + mLastFocusAppName
              + "; setting it to null");
      mLastFocusAppName = null;
    }
  }

  public static void sendApplicationStop(String str, int i, int i2) {
    logOriginFunction("sendApplicationStop(" + str + ", " + i + ", " + i2 + ")");
    if (!mIsBroadcastEnabled || TextUtils.isEmpty(str) || i2 < 0 || i2 >= APP_TERM_REASONS.length) {
      return;
    }
    packageStopped(str, i, i2);
  }

  public static void logOriginFunction(String str) {
    Throwable th = new Throwable();
    if (th.getStackTrace().length > 1) {
      Slog.d("AppStateBroadcaster", str + " called from " + th.getStackTrace()[2].toString());
    }
  }

  public static String stripPackageName(String str) {
    int indexOf = str.indexOf(58);
    return indexOf != -1 ? str.substring(0, indexOf) : str;
  }

  public static long intentTimestamp() {
    return System.currentTimeMillis();
  }

  public static void broadcastAppState(String str, String str2) {
    Slog.d("AppStateBroadcaster", " packageName : " + str + " appState : " + str2);
    if (mContext != null && str != null && str2 != null && !str2.equals("EXITED")) {
      for (String str3 : sPackages) {
        Intent intent = new Intent("diagandroid.app.ApplicationState");
        intent.setPackage(str3);
        intent.putExtra("ApplicationPackageName", stripPackageName(str));
        intent.putExtra("ApplicationState", str2);
        intent.putExtra("oemIntentTimestamp", intentTimestamp());
        mContext.sendBroadcastAsUser(
            intent, UserHandle.ALL, "diagandroid.app.receiveDetailedApplicationState");
      }
      return;
    }
    Slog.d("AppStateBroadcaster", "Can't send broadcast mContext is null");
  }

  public static void broadcastAppExit(String str, String str2) {
    Slog.d("AppStateBroadcaster", " packageName : " + str + " termReason : " + str2);
    if (mContext != null && str != null && str2 != null) {
      for (String str3 : sPackages) {
        Intent intent = new Intent("diagandroid.app.ApplicationState");
        intent.setPackage(str3);
        intent.putExtra("ApplicationPackageName", stripPackageName(str));
        intent.putExtra("ApplicationState", "EXITED");
        intent.putExtra("ApplicationTermReason", str2);
        intent.putExtra("oemIntentTimestamp", intentTimestamp());
        mContext.sendBroadcastAsUser(
            intent, UserHandle.ALL, "diagandroid.app.receiveDetailedApplicationState");
      }
      return;
    }
    Slog.d("AppStateBroadcaster", "Can't send broadcast mContext is null");
  }

  public static void populateRunningProcesses(ProcessMap processMap) {
    for (SparseArray sparseArray : processMap.getMap().values()) {
      for (int i = 0; i < sparseArray.size(); i++) {
        ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i);
        sendApplicationStart(processRecord.info.packageName, processRecord.mPid);
      }
    }
  }

  public static void packageRunning(String str, int i) {
    boolean z;
    HashMap hashMap = sKnownRunningPackages;
    synchronized (hashMap) {
      String stripPackageName = stripPackageName(str);
      Slog.d(
          "AppStateBroadcaster",
          "packageRunning for "
              + stripPackageName
              + " with processId "
              + i
              + " packageName "
              + str);
      ApplicationState applicationState = (ApplicationState) hashMap.get(stripPackageName);
      if (applicationState == null) {
        applicationState = new ApplicationState();
        hashMap.put(stripPackageName, applicationState);
        z = true;
      } else {
        z = false;
      }
      applicationState.addProcess(i);
    }
    if (z) {
      broadcastAppState(str, "START");
      Slog.d("AppStateBroadcaster", "packageRunning sent APP_STATE_START for " + str);
    }
  }

  public static void packageStopped(String str, int i, int i2) {
    boolean z;
    boolean z2;
    String str2 = "";
    String stripPackageName = stripPackageName(str);
    HashMap hashMap = sKnownRunningPackages;
    synchronized (hashMap) {
      Slog.d(
          "AppStateBroadcaster",
          "packageStopped for " + str + " with processId=" + i + " stopReason=" + i2);
      ApplicationState applicationState = (ApplicationState) hashMap.get(stripPackageName);
      z = true;
      z2 = false;
      if (applicationState != null) {
        Slog.d(
            "AppStateBroadcaster",
            "Removing process "
                + i
                + ", packageName "
                + str
                + " from rootPackage "
                + stripPackageName);
        if (applicationState.removeProcess(i, i2)) {
          Slog.d("AppStateBroadcaster", "Removing " + stripPackageName + " from running packages");
          String str3 = mLastFocusAppName;
          if (str3 != null && str.equals(str3)) {
            mLastFocusAppName = null;
            z2 = true;
          }
          hashMap.remove(stripPackageName);
          str2 = applicationState.getTermReason();
        } else if (i2 < 2) {
          hashMap.remove(stripPackageName);
          str2 = applicationState.getTermReason();
        } else {
          z = false;
        }
      } else {
        Slog.d(
            "AppStateBroadcaster",
            "packageStopped appState is null; send app exit with stopReason=" + i2);
        hashMap.remove(stripPackageName);
        str2 = APP_TERM_REASONS[i2];
      }
    }
    if (z2) {
      sendApplicationFocusLoss(str);
    }
    if (z) {
      broadcastAppExit(stripPackageName, str2);
    }
  }
}
