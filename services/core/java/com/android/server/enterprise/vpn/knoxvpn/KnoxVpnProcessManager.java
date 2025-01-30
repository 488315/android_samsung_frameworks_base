package com.android.server.enterprise.vpn.knoxvpn;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.IProcessObserver;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes2.dex */
public class KnoxVpnProcessManager {
  public static final boolean DBG = Debug.semIsProductDev();
  public static volatile KnoxVpnProcessManager instance = null;
  public static IActivityManager mActivityManagerNative = null;
  public Context mContext;
  public KnoxVpnEngineService mKnoxVpnEngineService;
  public KnoxVpnHelper mKnoxVpnHelper;
  public boolean mObserverStatus = false;
  public IProcessObserver.Stub mProcessObserver =
      new IProcessObserver
          .Stub() { // from class: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnProcessManager.1
        public void onForegroundServicesChanged(int i, int i2, int i3) {}

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
          KnoxVpnProcessManager.this.onApplicationStart(i, i2);
        }

        public void onProcessDied(int i, int i2) {
          KnoxVpnProcessManager.this.onApplicationStop(i, i2);
        }
      };
  public VpnProfileConfig mVpnConfig;

  public KnoxVpnProcessManager(KnoxVpnEngineService knoxVpnEngineService) {
    this.mContext = null;
    this.mVpnConfig = null;
    this.mKnoxVpnHelper = null;
    this.mKnoxVpnEngineService = null;
    this.mContext = knoxVpnEngineService.getContext();
    this.mKnoxVpnEngineService = knoxVpnEngineService;
    this.mVpnConfig = VpnProfileConfig.getInstance();
    this.mKnoxVpnHelper = KnoxVpnHelper.getInstance(this.mContext);
  }

  public static synchronized KnoxVpnProcessManager getInstance(
      KnoxVpnEngineService knoxVpnEngineService) {
    synchronized (KnoxVpnProcessManager.class) {
      if (knoxVpnEngineService == null) {
        return null;
      }
      if (instance == null) {
        instance = new KnoxVpnProcessManager(knoxVpnEngineService);
      }
      return instance;
    }
  }

  public final IProcessObserver.Stub getProcessObserver() {
    return this.mProcessObserver;
  }

  public final IActivityManager getNativeActivityManagerService() {
    if (mActivityManagerNative == null) {
      mActivityManagerNative = ActivityManagerNative.getDefault();
    }
    return mActivityManagerNative;
  }

  public void registerProcessObserver() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Log.d("KnoxVpnProcessManager", "registerProcessObserver");
        getNativeActivityManagerService().registerProcessObserver(getProcessObserver());
        this.mObserverStatus = true;
      } catch (Exception e) {
        Log.e(
            "KnoxVpnProcessManager",
            "Error occured while trying to register a process observer " + e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void unregisterProcessObserver() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Log.d("KnoxVpnProcessManager", "unregisterProcessObserver");
        getNativeActivityManagerService().unregisterProcessObserver(getProcessObserver());
        this.mObserverStatus = false;
      } catch (Exception e) {
        Log.e(
            "KnoxVpnProcessManager",
            "unregistering a process observer which is not registered yet " + e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isProcessObserverRegistered() {
    return this.mObserverStatus;
  }

  public final synchronized void onApplicationStart(int i, int i2) {
    KnoxVpnHelper knoxVpnHelper;
    if (this.mVpnConfig != null
        && (knoxVpnHelper = this.mKnoxVpnHelper) != null
        && this.mKnoxVpnEngineService != null) {
      String profileOwningTheUidFromCache = knoxVpnHelper.getProfileOwningTheUidFromCache(i2);
      if (profileOwningTheUidFromCache == null) {
        return;
      }
      int userId = UserHandle.getUserId(i2);
      VpnProfileInfo profileEntry = this.mVpnConfig.getProfileEntry(profileOwningTheUidFromCache);
      if (profileEntry == null) {
        return;
      }
      if (profileEntry.getVpnConnectionType() != 1) {
        return;
      }
      if (profileEntry.getActivateState() == 0) {
        return;
      }
      IKnoxVpnService binderInterfaceForProfile =
          this.mKnoxVpnEngineService.getBinderInterfaceForProfile(profileOwningTheUidFromCache);
      if (binderInterfaceForProfile == null) {
        return;
      }
      try {
        int state = binderInterfaceForProfile.getState(profileOwningTheUidFromCache);
        if (state != 1 && state != 5) {
          Log.d(
              "KnoxVpnProcessManager",
              "on-demand profile is not going to be restarted due to current state " + state);
          return;
        }
        for (String str : this.mContext.getPackageManager().getPackagesForUid(i2)) {
          if (profileEntry.getPackage(this.mKnoxVpnHelper.getPersonifiedName(userId, str))
              != null) {
            if (DBG) {
              Log.d(
                  "KnoxVpnProcessManager",
                  "onApplicationStart : profileName : "
                      + profileOwningTheUidFromCache
                      + " / packageName : "
                      + str
                      + "/uid: "
                      + i2);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i2));
            this.mKnoxVpnEngineService.startVpnForPerApplication(
                profileOwningTheUidFromCache, arrayList, true);
          }
        }
      } catch (RemoteException unused) {
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:55:0x00a0 A[Catch: Exception -> 0x00d6, all -> 0x0110, TryCatch #1 {Exception -> 0x00d6, blocks: (B:33:0x0052, B:35:0x005a, B:37:0x005e, B:40:0x0068, B:42:0x0070, B:53:0x009a, B:55:0x00a0, B:56:0x00bb, B:57:0x0095), top: B:32:0x0052, outer: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:56:0x00bb A[Catch: Exception -> 0x00d6, all -> 0x0110, TRY_LEAVE, TryCatch #1 {Exception -> 0x00d6, blocks: (B:33:0x0052, B:35:0x005a, B:37:0x005e, B:40:0x0068, B:42:0x0070, B:53:0x009a, B:55:0x00a0, B:56:0x00bb, B:57:0x0095), top: B:32:0x0052, outer: #0 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized void onApplicationStop(int i, int i2) {
    KnoxVpnHelper knoxVpnHelper;
    if (this.mVpnConfig != null
        && (knoxVpnHelper = this.mKnoxVpnHelper) != null
        && this.mKnoxVpnEngineService != null) {
      String profileOwningTheUidFromCache = knoxVpnHelper.getProfileOwningTheUidFromCache(i2);
      if (profileOwningTheUidFromCache == null) {
        return;
      }
      UserHandle.getUserId(i2);
      VpnProfileInfo profileEntry = this.mVpnConfig.getProfileEntry(profileOwningTheUidFromCache);
      if (profileEntry == null) {
        return;
      }
      if (profileEntry.getVpnConnectionType() != 1) {
        return;
      }
      if (profileEntry.getActivateState() == 0) {
        return;
      }
      boolean processRunCheck = processRunCheck(profileEntry);
      Log.d("KnoxVpnProcessManager", "onApplicationStop : isExistRunningApps : " + processRunCheck);
      if (!processRunCheck) {
        try {
          IKnoxVpnService binderInterfaceForProfile =
              this.mKnoxVpnEngineService.getBinderInterfaceForProfile(profileOwningTheUidFromCache);
          if (binderInterfaceForProfile == null) {
            if (DBG) {
              Log.d(
                  "KnoxVpnProcessManager",
                  "onApplicationStop : stopping vpn connection : Service is not started");
            }
            return;
          }
          int state = binderInterfaceForProfile.getState(profileOwningTheUidFromCache);
          if (DBG) {
            Log.d(
                "KnoxVpnProcessManager",
                "The current state of the vpn profile before calling stop connection for"
                    + " application on on-demand mode "
                    + state);
          }
          if (state != 1 && state != 2 && state != 3) {
            if (state != 4) {
              if (state != 5) {}
            }
            if (binderInterfaceForProfile.stopConnection(profileOwningTheUidFromCache) == 0) {
              AuditLog.logAsUser(
                  3,
                  5,
                  false,
                  Process.myPid(),
                  "KnoxVpnProcessManager",
                  String.format(
                      "Error occurred trying to stop vpn connection from profile %s",
                      profileOwningTheUidFromCache),
                  profileEntry.getPersonaId());
            } else {
              AuditLog.logAsUser(
                  5,
                  5,
                  true,
                  Process.myPid(),
                  "KnoxVpnProcessManager",
                  String.format(
                      "Connection with vpn vendor service stopped for profile %s",
                      profileOwningTheUidFromCache),
                  profileEntry.getPersonaId());
            }
          }
          this.mKnoxVpnEngineService.updateBlockingRules(profileOwningTheUidFromCache);
          if (binderInterfaceForProfile.stopConnection(profileOwningTheUidFromCache) == 0) {}
        } catch (Exception e) {
          Log.e(
              "KnoxVpnProcessManager",
              "stopping vpn connection : Failure at " + Log.getStackTraceString(e));
          AuditLog.logAsUser(
              3,
              5,
              false,
              Process.myPid(),
              "KnoxVpnProcessManager",
              String.format(
                  "Exception stopping connection for profile %s", profileOwningTheUidFromCache),
              profileEntry.getPersonaId());
        }
      }
    }
  }

  public boolean processRunCheck(VpnProfileInfo vpnProfileInfo) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
          ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
      HashSet hashSet = new HashSet();
      HashSet hashSet2 = new HashSet();
      if (runningAppProcesses != null) {
        for (int i = 0; i < runningAppProcesses.size(); i++) {
          if (DBG) {
            Log.d(
                "KnoxVpnProcessManager",
                "runningApplicationProcess uid being added is " + runningAppProcesses.get(i).uid);
          }
          hashSet.add(String.valueOf(runningAppProcesses.get(i).uid));
        }
      }
      for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
        Log.d(
            "KnoxVpnProcessManager",
            "runningApplicationProcess VpnPackageInfo uid being added is "
                + vpnPackageInfo.getUid());
        hashSet2.add(String.valueOf(vpnPackageInfo.getUid()));
      }
      hashSet.retainAll(hashSet2);
      if (hashSet.size() > 0) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
      }
    } catch (Exception unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return false;
  }
}
