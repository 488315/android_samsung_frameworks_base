package com.android.server.knox.dar.ddar.nativedaemon;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.DualDarDoPolicyChecker;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.dar.ddar.securesession.Wiper;
import com.samsung.android.knox.ddar.FileInfo;
import com.samsung.android.knox.ddar.Secret;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DualDARDaemonProxy extends IProxyAgentService
    implements INativeDaemonConnectorCallbacks {
  public static Context mContext;
  public static DualDARDaemonProxy mInstance;
  public DualDarDaemonConnector mConnector;
  public Thread mConnectorThread = null;
  public NativeDaemonEvent mEvent = null;
  public ActivityManager mAm = null;
  public final Object mLock = new Object();
  public boolean isDaemonConnectionFailed = false;
  public Handler mHandler = null;
  public final List mBlockedClearablePackages = new ArrayList();
  public final List mDualDARDOPolicyPackages = new ArrayList();

  public final boolean isZippedLocaleOverlay(String str) {
    return false;
  }

  @Override // com.android.server.knox.dar.ddar.nativedaemon.INativeDaemonConnectorCallbacks
  public boolean onDaemonDisconnected() {
    return false;
  }

  @Override // com.android.server.knox.dar.ddar.nativedaemon.INativeDaemonConnectorCallbacks
  public boolean onEvent(int i, String str, String[] strArr) {
    return false;
  }

  public static synchronized DualDARDaemonProxy getInstance(Context context) {
    DualDARDaemonProxy dualDARDaemonProxy;
    synchronized (DualDARDaemonProxy.class) {
      if (mInstance == null) {
        mInstance = new DualDARDaemonProxy(context);
      }
      dualDARDaemonProxy = mInstance;
    }
    return dualDARDaemonProxy;
  }

  public DualDARDaemonProxy(Context context) {
    DDLog.m33d("DualDARDaemonProxy", "DualDARDaemonProxy() called", new Object[0]);
    mContext = context;
    if (PersonaServiceHelper.isDualDAREnabled()) {
      startConnectorThread();
    }
  }

  public final void startConnectorThread() {
    DDLog.m33d("DualDARDaemonProxy", "startConnectorThread() ", new Object[0]);
    this.mConnector = new DualDarDaemonConnector(Looper.getMainLooper(), this);
    Thread thread = new Thread(this.mConnector, "DualDARDaemonProxy");
    this.mConnectorThread = thread;
    thread.start();
  }

  public final void stopConnectorThread() {
    DDLog.m33d("DualDARDaemonProxy", "stopConnectorThread() ", new Object[0]);
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector != null) {
      dualDarDaemonConnector.setIsListening(false);
      this.mConnectorThread.interrupt();
      this.mConnector = null;
    }
  }

  public final boolean fetchDumpStateInfo(FileInfo fileInfo) {
    DDLog.m33d("DualDARDaemonProxy", "fetchDumpStateInfo()", new Object[0]);
    fileCopy(fileInfo.fd, "/data/log/ddar_log.txt");
    DDLog.m33d(
        "DualDARDaemonProxy",
        "fetchDumpStateInfo() successfullly read the log file",
        new Object[0]);
    return true;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x01c7, code lost:

     return r7;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle onMessage(int i, String str, Bundle bundle) {
    char c;
    NativeDaemonEvent nativeDaemonEvent;
    NativeDaemonEvent nativeDaemonEvent2;
    NativeDaemonEvent nativeDaemonEvent3;
    NativeDaemonEvent nativeDaemonEvent4;
    NativeDaemonEvent nativeDaemonEvent5;
    NativeDaemonEvent nativeDaemonEvent6;
    NativeDaemonEvent nativeDaemonEvent7;
    enforceCallingUser(i);
    try {
      DDLog.m33d("DualDARDaemonProxy", "onMessage() " + str, new Object[0]);
      Bundle bundle2 = new Bundle();
      switch (str.hashCode()) {
        case -2016587526:
          if (str.equals("CLEAR_APP_DATA")) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case -1958949728:
          if (str.equals("LOAD_CLIENT_LIBRARY")) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        case -1092001439:
          if (str.equals("STOP_DAEMON")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case -971190072:
          if (str.equals("FETCH_DUMPSTATE_REQUEST")) {
            c = '\f';
            break;
          }
          c = 65535;
          break;
        case -965502524:
          if (str.equals("START_CLIENT_LIBRARY")) {
            c = '\b';
            break;
          }
          c = 65535;
          break;
        case 70449002:
          if (str.equals("GET_CLIENTLIB_VERSION")) {
            c = '\t';
            break;
          }
          c = 65535;
          break;
        case 222645537:
          if (str.equals("START_DAEMON")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 321737340:
          if (str.equals("SET_EVICTION")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 542984053:
          if (str.equals("PUSH_SECRET")) {
            c = '\n';
            break;
          }
          c = 65535;
          break;
        case 725375061:
          if (str.equals("SET_DUALDAR_POLICY")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        case 766354690:
          if (str.equals("CLEAR_SECRET")) {
            c = 11;
            break;
          }
          c = 65535;
          break;
        case 1297457511:
          if (str.equals("UNLOAD_CLIENT_LIBRARY")) {
            c = 7;
            break;
          }
          c = 65535;
          break;
        case 1776465251:
          if (str.equals("SET_DUALDAR_DO_POLICY")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
          startDualDARDaemon(bundle2);
          break;
        case 1:
          stopConnectorThread();
          setSystemPropertyBoolean("persist.sys.knox.dualdard", false);
          bundle2.putBoolean("dual_dar_response", true);
          break;
        case 2:
          boolean eviction =
              setEviction(bundle.getInt("user_id"), bundle.getBoolean("EVICT", false));
          bundle2.putBoolean("dual_dar_response", eviction);
          if (!eviction && (nativeDaemonEvent = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent.getMessage());
            break;
          }
          break;
        case 3:
          setDualDARPolicyForManagedProfile(bundle, bundle2);
          break;
        case 4:
          setDualDARPolicyForManagedDevice(bundle, bundle2);
          break;
        case 5:
          boolean clearApplicationUserDataForPackages =
              clearApplicationUserDataForPackages(
                  bundle.getInt("user_id"), this.mDualDARDOPolicyPackages);
          bundle2.putBoolean("dual_dar_response", clearApplicationUserDataForPackages);
          if (!clearApplicationUserDataForPackages && (nativeDaemonEvent2 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent2.getMessage());
            break;
          }
          break;
        case 6:
          boolean loadClientLibrary =
              loadClientLibrary(bundle.getInt("user_id"), bundle.getString("CRYPTO_PATH"));
          bundle2.putBoolean("dual_dar_response", loadClientLibrary);
          if (!loadClientLibrary && (nativeDaemonEvent3 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent3.getMessage());
            break;
          }
          break;
        case 7:
          boolean unloadClientLibrary = unloadClientLibrary(bundle.getInt("user_id"));
          bundle2.putBoolean("dual_dar_response", unloadClientLibrary);
          if (!unloadClientLibrary && (nativeDaemonEvent4 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent4.getMessage());
            break;
          }
          break;
        case '\b':
          boolean startClientLibrary = startClientLibrary(bundle.getInt("user_id"));
          bundle2.putBoolean("dual_dar_response", startClientLibrary);
          if (!startClientLibrary && (nativeDaemonEvent5 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent5.getMessage());
            break;
          }
          break;
        case '\t':
          bundle2.putString(
              "dual_dar_response", getClientLibraryVersion(bundle.getString("CRYPTO_PATH")));
          break;
        case '\n':
          boolean pushSecret =
              pushSecret(
                  bundle.getInt("user_id"),
                  bundle.getParcelableArrayList("INNER_LAYER_SECRET"),
                  bundle.getString("ORIGINATING_SECURE_CLIENT_ID"));
          bundle2.putBoolean("dual_dar_response", pushSecret);
          if (!pushSecret && (nativeDaemonEvent6 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent6.getMessage());
            break;
          }
          break;
        case 11:
          boolean clearSecret = clearSecret(bundle.getInt("user_id"));
          bundle2.putBoolean("dual_dar_response", clearSecret);
          if (!clearSecret && (nativeDaemonEvent7 = this.mEvent) != null) {
            bundle2.putString("dual_dar_response_message", nativeDaemonEvent7.getMessage());
            break;
          }
          break;
        case '\f':
          bundle2.putBoolean(
              "dual_dar_response",
              fetchDumpStateInfo((FileInfo) bundle.getParcelable("FSLOG_FILE_INFO")));
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final void setDualDARPolicyForManagedDevice(Bundle bundle, Bundle bundle2) {
    NativeDaemonEvent nativeDaemonEvent;
    boolean dualDARDOPolicy =
        setDualDARDOPolicy(bundle.getInt("user_id"), bundle.getInt("CRYPTO_TYPE"));
    bundle2.putBoolean("dual_dar_response", dualDARDOPolicy);
    if (dualDARDOPolicy || (nativeDaemonEvent = this.mEvent) == null) {
      return;
    }
    bundle2.putString("dual_dar_response_message", nativeDaemonEvent.getMessage());
  }

  public final void setDualDARPolicyForManagedProfile(Bundle bundle, Bundle bundle2) {
    boolean dualDARPOPolicy;
    NativeDaemonEvent nativeDaemonEvent;
    int i = bundle.getInt("user_id");
    int i2 = bundle.getInt("CRYPTO_TYPE");
    int i3 = bundle.getInt("STORAGE_TYPE");
    if (i3 == 1) {
      dualDARPOPolicy = setDualDARPOPolicy(i, 9);
    } else {
      dualDARPOPolicy = i3 == 2 ? setDualDARPOPolicy(i, i2) : false;
    }
    bundle2.putBoolean("dual_dar_response", dualDARPOPolicy);
    if (dualDARPOPolicy || (nativeDaemonEvent = this.mEvent) == null) {
      return;
    }
    bundle2.putString("dual_dar_response_message", nativeDaemonEvent.getMessage());
  }

  public final void startDualDARDaemon(Bundle bundle) {
    try {
      synchronized (this.mLock) {
        if (!DarUtil.isDaemonRunning("dualdard")) {
          DDLog.m35i("DualDARDaemonProxy", "start newly dualdard daemon ! ", new Object[0]);
          setSystemPropertyBoolean("persist.sys.knox.dualdard", true);
        }
        startConnectorThread();
        startTimer();
        this.mLock.wait();
        if (!this.isDaemonConnectionFailed) {
          bundle.putBoolean("dual_dar_response", true);
        } else {
          bundle.putBoolean("dual_dar_response", false);
        }
        cancelTimer();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void setSystemPropertyBoolean(String str, boolean z) {
    if (TextUtils.isEmpty(str)) {
      DDLog.m34e("DualDARDaemonProxy", "Invalid property", new Object[0]);
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        SystemProperties.set(str, Boolean.toString(z));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void startTimer() {
    Handler handler = new Handler(Looper.getMainLooper());
    this.mHandler = handler;
    handler.postDelayed(
        new Runnable() { // from class:
                         // com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy.1
          @Override // java.lang.Runnable
          public void run() {
            DDLog.m34e("DualDARDaemonProxy", "coudn't connect with daemon!", new Object[0]);
            DualDARDaemonProxy dualDARDaemonProxy = DualDARDaemonProxy.this;
            dualDARDaemonProxy.isDaemonConnectionFailed = true;
            dualDARDaemonProxy.mLock.notify();
          }
        },
        10000L);
  }

  public void cancelTimer() {
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
    this.isDaemonConnectionFailed = false;
  }

  public boolean setEviction(int i, boolean z) {
    if (EnterprisePartitionManager.getInstance(mContext).setEviction(i, z)) {
      return true;
    }
    DDLog.m34e("DualDARDaemonProxy", "setEviction failed!", new Object[0]);
    return false;
  }

  public final boolean setDualDARPOPolicy(int i, int i2) {
    if (EnterprisePartitionManager.getInstance(mContext).setDualDARPolicy(i, i2)) {
      return true;
    }
    DDLog.m34e("DualDARDaemonProxy", "setDualDARPolicy failed!", new Object[0]);
    return false;
  }

  public final boolean setDualDARDOPolicy(int i, int i2) {
    DDLog.m33d(
        "DualDARDaemonProxy", "setDualDARDOPolicy for user " + i + " type " + i2, new Object[0]);
    if (setDualDARPolicyToDirectories(i, i2)) {
      return false;
    }
    DarManagerService darManagerService = (DarManagerService) ServiceManager.getService("dar");
    if (darManagerService != null) {
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        List packageListForDualDarPolicy =
            darManagerService.getPackageListForDualDarPolicy("pkg_clearable_system");
        List packageListForDualDarPolicy2 =
            darManagerService.getPackageListForDualDarPolicy("pkg_not_system");
        List packageListForDualDarPolicy3 =
            darManagerService.getPackageListForDualDarPolicy("pkg_not_clearable_system");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        refreshNonClearablePackagesList(i);
        if (packageListForDualDarPolicy == null || packageListForDualDarPolicy.isEmpty()) {
          DDLog.m34e(
              "DualDARDaemonProxy", "Clearable system package list is Empty.", new Object[0]);
        } else {
          makeListUpWithClearableSystemPackages(packageListForDualDarPolicy, arrayList, arrayList2);
        }
        if (packageListForDualDarPolicy2 == null || packageListForDualDarPolicy2.isEmpty()) {
          DDLog.m34e("DualDARDaemonProxy", "Not system package list is Empty.", new Object[0]);
        } else {
          makeListUpWithNonSystemPackages(packageListForDualDarPolicy2, arrayList, arrayList2);
        }
        if (packageListForDualDarPolicy3 == null || packageListForDualDarPolicy3.isEmpty()) {
          DDLog.m34e(
              "DualDARDaemonProxy", "Not clearable system package list is Empty.", new Object[0]);
        } else {
          makeListUpWithNonClearableSystemPackages(packageListForDualDarPolicy3, arrayList);
        }
        if (!setDualDARPolicyForPackages(i, i2, arrayList2, arrayList)) {
          DDLog.m34e("DualDARDaemonProxy", "setDualDARPolicyForPackages failed!", new Object[0]);
          return false;
        }
        DualDarDoPolicyChecker.getInstance(mContext).saveSkippedPackages(arrayList);
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
    DDLog.m33d("DualDARDaemonProxy", "setDualDARDOPolicy res : true", new Object[0]);
    return true;
  }

  public final void makeListUpWithNonClearableSystemPackages(List list, List list2) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      DDLog.m34e("DualDARDaemonProxy", "3. Skip ddar policy for the pkg " + str, new Object[0]);
      list2.add(str);
    }
  }

  public final void makeListUpWithNonSystemPackages(List list, List list2, List list3) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      if (isNonClearablePackage(str)) {
        DDLog.m34e("DualDARDaemonProxy", "2. Skip ddar policy for the pkg " + str, new Object[0]);
        list2.add(str);
      } else {
        list3.add(str);
      }
    }
  }

  public final void makeListUpWithClearableSystemPackages(List list, List list2, List list3) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      if (isNonClearablePackage(str)) {
        DDLog.m33d("DualDARDaemonProxy", "1. Skip ddar policy for the pkg " + str, new Object[0]);
        list2.add(str);
      } else {
        list3.add(str);
      }
    }
  }

  public final boolean setDualDARPolicyToDirectories(int i, int i2) {
    if (!setDualDARPolicyForDir(i, i2, "/data/media/" + i)) {
      DDLog.m34e(
          "DualDARDaemonProxy", "Failed to set the policy to shared folders...", new Object[0]);
      return true;
    }
    if (setDualDARPolicyForUser(i, i2)) {
      return false;
    }
    DDLog.m34e(
        "DualDARDaemonProxy", "Failed to set the policy to data package folder...", new Object[0]);
    return true;
  }

  public final boolean setDualDARPolicyForUser(int i, int i2) {
    EnterprisePartitionManager enterprisePartitionManager =
        EnterprisePartitionManager.getInstance(mContext);
    String str = "/data/user/" + i;
    String str2 = "/data/user_de/" + i;
    if (!enterprisePartitionManager.setDualDARPolicyDir(i, i2, str)) {
      DDLog.m34e("DualDARDaemonProxy", "setDualDARPolicyDir user failed! : " + str, new Object[0]);
      return false;
    }
    if (enterprisePartitionManager.setDualDARPolicyDir(i, 9, str2)) {
      return true;
    }
    DDLog.m34e("DualDARDaemonProxy", "setDualDARPolicyDir user failed! : " + str2, new Object[0]);
    return true;
  }

  public final boolean setDualDARPolicyForPackages(int i, int i2, List list, List list2) {
    EnterprisePartitionManager enterprisePartitionManager =
        EnterprisePartitionManager.getInstance(mContext);
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      String str2 = "/data/user/" + i + "/" + str;
      String str3 = "/data/user_de/" + i + "/" + str;
      if (!enterprisePartitionManager.setDualDARPolicyDirRecursively(i, i2, str2)) {
        if (isSetPolicyFailureExceptional(str)) {
          DDLog.m34e(
              "DualDARDaemonProxy",
              "setDualDARPolicyDirRecursively failure exceptional! : " + str2,
              new Object[0]);
          list2.add(str);
        } else {
          DDLog.m34e(
              "DualDARDaemonProxy",
              "setDualDARPolicyDirRecursively failed! : " + str2,
              new Object[0]);
          return false;
        }
      } else if (!enterprisePartitionManager.setDualDARPolicyDirRecursively(i, 9, str3)) {
        DDLog.m34e(
            "DualDARDaemonProxy",
            "setDualDARPolicyDirRecursively failed! : " + str3,
            new Object[0]);
      }
      this.mDualDARDOPolicyPackages.add(str);
    }
    return true;
  }

  public final boolean isSetPolicyFailureExceptional(String str) {
    return isZippedLocaleOverlay(str);
  }

  public final boolean clearApplicationUserDataForPackages(int i, List list) {
    if (i != 0 || !SemPersonaManager.isDarDualEncryptionEnabled(i)) {
      DDLog.m33d(
          "DualDARDaemonProxy",
          "clearApplicationUserDataForPackages failed! : (reason) DualDAR at DO user",
          new Object[0]);
      return false;
    }
    if (this.mDualDARDOPolicyPackages.isEmpty()) {
      DDLog.m33d(
          "DualDARDaemonProxy",
          "clearApplicationUserDataForPackages - there is no package to clear",
          new Object[0]);
      return true;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Iterator it = list.iterator();
        while (it.hasNext()) {
          String str = (String) it.next();
          if (!clearUserData(str, i)) {
            DDLog.m34e(
                "DualDARDaemonProxy", "clearApplicationUserData failed! : " + str, new Object[0]);
          } else {
            DDLog.m33d(
                "DualDARDaemonProxy", "clearApplicationUserData success! : " + str, new Object[0]);
          }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        DDLog.m33d(
            "DualDARDaemonProxy",
            "clear app user data for initialize DualDAR at DO. success",
            new Object[0]);
        return true;
      } catch (Exception e) {
        DDLog.m34e(
            "DualDARDaemonProxy", "clearApplicationUserDataForPackages exception", new Object[0]);
        e.printStackTrace();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean clearUserData(String str, int i) {
    boolean z;
    if (str != null) {
      IPackageDataObserver clearUserDataObserver = new ClearUserDataObserver();
      try {
        if (this.mAm == null) {
          this.mAm = (ActivityManager) mContext.getSystemService("activity");
        }
        DDLog.m33d(
            "DualDARDaemonProxy", str + " try to clear application User Data", new Object[0]);
        if (!this.mAm.clearApplicationUserData(str, clearUserDataObserver)) {
          DDLog.m34e(
              "DualDARDaemonProxy",
              "Couldn't clear application user data for package: " + str,
              new Object[0]);
        } else {
          synchronized (clearUserDataObserver) {
            while (!clearUserDataObserver.finished) {
              try {
                clearUserDataObserver.wait(100L);
              } catch (InterruptedException e) {
                DDLog.m34e("DualDARDaemonProxy", "InterruptedException occur", new Object[0]);
                e.printStackTrace();
              }
            }
            z =
                clearUserDataObserver.success
                    && clearUserDataObserver.packageName.equalsIgnoreCase(str);
          }
          return z;
        }
      } catch (Exception e2) {
        DDLog.m34e("DualDARDaemonProxy", e2.getMessage(), new Object[0]);
      }
    }
    return false;
  }

  public class ClearUserDataObserver extends IPackageDataObserver.Stub {
    public boolean finished;
    public String packageName;
    public boolean success;

    public ClearUserDataObserver() {
      this.finished = false;
      this.success = false;
    }

    public void onRemoveCompleted(String str, boolean z) {
      synchronized (this) {
        this.finished = true;
        this.packageName = str;
        this.success = z;
        notifyAll();
      }
    }
  }

  public final boolean setDualDARPolicyForDir(int i, int i2, String str) {
    if (EnterprisePartitionManager.getInstance(mContext)
        .setDualDARPolicyDirRecursively(i, i2, str)) {
      return true;
    }
    DDLog.m34e(
        "DualDARDaemonProxy", "setDualDARPolicyDirRecursively failed! : " + str, new Object[0]);
    return false;
  }

  public final boolean isNonClearablePackage(String str) {
    return !this.mBlockedClearablePackages.isEmpty()
        && this.mBlockedClearablePackages.contains(str);
  }

  public final void refreshNonClearablePackagesList(int i) {
    DarManagerService darManagerService = (DarManagerService) ServiceManager.getService("dar");
    List blockedClearablePackages =
        darManagerService != null ? darManagerService.getBlockedClearablePackages(i) : null;
    if (blockedClearablePackages != null) {
      this.mBlockedClearablePackages.clear();
      this.mBlockedClearablePackages.addAll(blockedClearablePackages);
    }
    List list = this.mBlockedClearablePackages;
    if (list != null) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        DDLog.m33d(
            "DualDARDaemonProxy",
            "refreshNonClearablePackagesList = " + ((String) it.next()),
            new Object[0]);
      }
    }
  }

  public boolean loadClientLibrary(int i, String str) {
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "loadClientLibrary failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    try {
      NativeDaemonEvent executeSync =
          dualDarDaemonConnector.executeSync("vendor_lib", "load", Integer.valueOf(i), str);
      this.mEvent = executeSync;
      if (!executeSync.isClassOk()) {
        if (!this.mEvent.isClassContinue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean unloadClientLibrary(int i) {
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "unloadClientLibrary failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    try {
      NativeDaemonEvent executeSync =
          dualDarDaemonConnector.executeSync("vendor_lib", "unload", Integer.valueOf(i));
      this.mEvent = executeSync;
      if (!executeSync.isClassOk()) {
        if (!this.mEvent.isClassContinue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean startClientLibrary(int i) {
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "startClientLibrary failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    try {
      NativeDaemonEvent executeSync =
          dualDarDaemonConnector.executeSync("vendor_lib", "start", Integer.valueOf(i), 16);
      this.mEvent = executeSync;
      if (!executeSync.isClassOk()) {
        if (!this.mEvent.isClassContinue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public String getClientLibraryVersion(String str) {
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "startClientLibrary failed! Error: native interface not yet connected failed",
          new Object[0]);
      return "";
    }
    try {
      NativeDaemonEvent executeSync =
          dualDarDaemonConnector.executeSync("vendor_lib", "version", 0, str);
      DDLog.m33d(
          "DualDARDaemonProxy",
          "getClientLibraryVersion() got response from executeSync",
          new Object[0]);
      if (executeSync.getResponseCode() >= 0) {
        return executeSync.getMessage();
      }
      DDLog.m34e(
          "DualDARDaemonProxy",
          "startClientLibrary failed! Error code: "
              + executeSync.getCode()
              + " message: "
              + executeSync.getMessage(),
          new Object[0]);
      return "";
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public boolean pushSecret(int i, List list, String str) {
    if (this.mConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "pushSecret failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      Secret secret = (Secret) it.next();
      try {
        byte[] decryptMessageFrom =
            ((IProxyAgentService) this).mSecureClientForInAPI.decryptMessageFrom(str, secret.data);
        if (decryptMessageFrom == null) {
          DDLog.m34e("DualDARDaemonProxy", "pushSecret failed ! decData is null", new Object[0]);
          return false;
        }
        this.mEvent =
            this.mConnector.executeSync(
                "key",
                "install",
                Integer.valueOf(i),
                secret.alias,
                byteArrayToHex(decryptMessageFrom));
        Wiper.wipe(decryptMessageFrom);
        if (!this.mEvent.isClassOk()) {
          DDLog.m34e("DualDARDaemonProxy", "pushSecret failed !", new Object[0]);
          return false;
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static String byteArrayToHex(byte[] bArr) {
    if (bArr == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder(bArr.length * 2);
    for (byte b : bArr) {
      sb.append(String.format("%02x", Byte.valueOf(b)));
    }
    String sb2 = sb.toString();
    sb.delete(0, sb.length());
    return sb2;
  }

  public boolean clearSecret(int i) {
    DualDarDaemonConnector dualDarDaemonConnector = this.mConnector;
    if (dualDarDaemonConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "clearSecret failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    try {
      NativeDaemonEvent executeSync =
          dualDarDaemonConnector.executeSync("key", "evict", Integer.valueOf(i), "all");
      this.mEvent = executeSync;
      if (!executeSync.isClassOk()) {
        if (!this.mEvent.isClassContinue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean dumpSecret(int i, String str) {
    if (this.mConnector == null) {
      DDLog.m34e(
          "DualDARDaemonProxy",
          "dumpSecret failed! Error: native interface not yet connected failed",
          new Object[0]);
      return false;
    }
    DDLog.m33d(
        "DualDARDaemonProxy.DUALDAR",
        "dumpSecret() - userId : " + i + ", filePath : " + str,
        new Object[0]);
    try {
      NativeDaemonEvent executeSync =
          this.mConnector.executeSync("key", "key_dump", Integer.valueOf(i), str);
      this.mEvent = executeSync;
      if (!executeSync.isClassOk()) {
        if (!this.mEvent.isClassContinue()) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override // com.android.server.knox.dar.ddar.nativedaemon.INativeDaemonConnectorCallbacks
  public void onDaemonConnected() {
    try {
      DDLog.m33d("DualDARDaemonProxy", "onDaemonConnected()", new Object[0]);
      synchronized (this.mLock) {
        this.mLock.notify();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void enforceCallingUser(int i) {
    DDLog.m33d("DualDARDaemonProxy", "enforceCallingUser", new Object[0]);
    int callingUid = Binder.getCallingUid();
    if (UserHandle.getAppId(callingUid) != 5250
        && UserHandle.getAppId(callingUid) != Process.myUid()) {
      throw new SecurityException("Can only be called by system user");
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:54:0x00b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:61:? A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:62:0x00a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean fileCopy(ParcelFileDescriptor parcelFileDescriptor, String str) {
    FileInputStream fileInputStream;
    int i;
    FileOutputStream fileOutputStream = null;
    try {
      FileOutputStream fileOutputStream2 =
          new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
      try {
        FileOutputStream fileOutputStream3 =
            new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
        try {
          fileInputStream = new FileInputStream(new File(str));
          try {
            int available = fileInputStream.available();
            while (available != 0) {
              byte[] bArr = new byte[1024];
              if (available > 1024) {
                i = available - 1024;
                available = 1024;
              } else {
                i = 0;
              }
              fileOutputStream3.write(bArr, 0, fileInputStream.read(bArr, 0, available));
              available = i;
            }
            try {
              fileOutputStream3.close();
            } catch (Exception e) {
              DDLog.m34e("DualDARDaemonProxy", e.getMessage(), new Object[0]);
            }
            try {
              fileInputStream.close();
            } catch (Exception e2) {
              DDLog.m34e("DualDARDaemonProxy", e2.getMessage(), new Object[0]);
            }
            return true;
          } catch (Exception e3) {
            e = e3;
            fileOutputStream = fileOutputStream3;
            try {
              DDLog.m33d(
                  "DualDARDaemonProxy",
                  "fail to open inputstream from FileDescriptor",
                  new Object[0]);
              DDLog.m34e("DualDARDaemonProxy", e.getMessage(), new Object[0]);
              if (fileOutputStream != null) {
                try {
                  fileOutputStream.close();
                } catch (Exception e4) {
                  DDLog.m34e("DualDARDaemonProxy", e4.getMessage(), new Object[0]);
                }
              }
              if (fileInputStream == null) {
                return false;
              }
              try {
                fileInputStream.close();
                return false;
              } catch (Exception e5) {
                DDLog.m34e("DualDARDaemonProxy", e5.getMessage(), new Object[0]);
                return false;
              }
            } catch (Throwable th) {
              th = th;
              if (fileOutputStream != null) {
                try {
                  fileOutputStream.close();
                } catch (Exception e6) {
                  DDLog.m34e("DualDARDaemonProxy", e6.getMessage(), new Object[0]);
                }
              }
              if (fileInputStream == null) {
                try {
                  fileInputStream.close();
                  throw th;
                } catch (Exception e7) {
                  DDLog.m34e("DualDARDaemonProxy", e7.getMessage(), new Object[0]);
                  throw th;
                }
              }
              throw th;
            }
          } catch (Throwable th2) {
            th = th2;
            fileOutputStream = fileOutputStream3;
            if (fileOutputStream != null) {}
            if (fileInputStream == null) {}
          }
        } catch (Exception e8) {
          e = e8;
          fileInputStream = null;
        } catch (Throwable th3) {
          th = th3;
          fileInputStream = null;
        }
      } catch (Exception e9) {
        e = e9;
        fileInputStream = null;
        fileOutputStream = fileOutputStream2;
      } catch (Throwable th4) {
        th = th4;
        fileInputStream = null;
        fileOutputStream = fileOutputStream2;
      }
    } catch (Exception e10) {
      e = e10;
      fileInputStream = null;
    } catch (Throwable th5) {
      th = th5;
      fileInputStream = null;
    }
  }
}
