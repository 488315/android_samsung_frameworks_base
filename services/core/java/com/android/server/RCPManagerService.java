package com.android.server;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IRCPInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.Looper;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.server.bridge.BridgeProxy;
import com.android.server.bridge.operations.RCPDumpState;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.samsung.android.knox.ISemRemoteContentManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class RCPManagerService extends ISemRemoteContentManager.Stub {
  public static String TAG = "RCPManagerService";
  public static Context sContext;
  public KnoxCaptureInputFilter knoxCaptureInputFilter;
  public Context mContext;
  public HashMap mRCPInterfaceMap = new HashMap();
  public HashMap mBridgeProxyAliveList = new HashMap();
  public final int OWNER_ID = 0;
  public final boolean KNOX_DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public UserManager mUm = null;
  public SemPersonaManager mPm = null;
  public boolean mIsInitialized = false;
  public InputMethodManagerService mIMMS = null;
  public InputManagerService mInputManagerService = null;
  public Handler mBridgeHandler = null;
  public final BroadcastReceiver mUserReceiver =
      new BroadcastReceiver() { // from class: com.android.server.RCPManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (action == null) {
            return;
          }
          if (action.equals("com.samsung.android.plugin.ACTION_PLUGIN_CHANGE_NOTI")) {
            Log.d(
                RCPManagerService.TAG,
                "Received com.samsung.android.plugin.ACTION_PLUGIN_CHANGE_NOTI");
            Log.d(
                RCPManagerService.TAG, "Connected BT Gear : sending Notification policy broadcast");
            RCPManagerService.this.sendRCPPolicyBroadcastToGearManager(context, -1);
          }
          if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(intent.getAction())) {
            intent.getIntExtra("android.intent.extra.user_handle", -1);
            UserHandle userHandle = (UserHandle) intent.getExtra("android.intent.extra.USER");
            if (userHandle == null || !SemPersonaManager.isKnoxId(userHandle.getIdentifier())) {
              return;
            }
            Log.d(
                RCPManagerService.TAG,
                "ACTION_MANAGED_PROFILE_ADDED : Starting RCP Proxy for user = "
                    + userHandle.getIdentifier());
            if (RCPManagerService.this.checkIfGearConnected()) {
              RCPManagerService.this.sendRCPPolicyBroadcastToGearManager(
                  context, userHandle.getIdentifier());
            }
          }
        }
      };
  public ContainerStateReceiver mContainerstateReceiver =
      new ContainerStateReceiver() { // from class: com.android.server.RCPManagerService.2
        public void onContainerCreated(Context context, int i, Bundle bundle) {
          try {
            RCPManagerService.this.scanAndStartBridgeProxy(i);
          } catch (Exception e) {
            Log.d(RCPManagerService.TAG, "Exception", e);
          }
        }

        public void onContainerRunning(Context context, int i, Bundle bundle) {
          RCPManagerService.checkCallerPermissionFor("onPersonaActive");
          Log.d(RCPManagerService.TAG, " onPersonaActive called for  " + i);
        }

        public void onContainerRemoved(Context context, int i, Bundle bundle) {
          RCPManagerService.checkCallerPermissionFor("onRemovePersona");
          Log.d(RCPManagerService.TAG, " onRemovePersona called for  " + i);
          RCPManagerService.this.deleteAllPersonaData(i);
        }

        public void onContainerReset(Context context, int i, Bundle bundle) {
          RCPManagerService.checkCallerPermissionFor("onResetPersona");
          Log.d(RCPManagerService.TAG, " onResetPersona called for  " + i);
          RCPManagerService.this.deleteAllPersonaData(i);
        }
      };
  public Runnable mBridgeRunnable =
      new Runnable() { // from class: com.android.server.RCPManagerService.3
        @Override // java.lang.Runnable
        public void run() {
          try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = elapsedRealtime / 1000;
            Log.d(
                RCPManagerService.TAG,
                " RCPManagerService elapsedRealtime in milliseconds: "
                    + elapsedRealtime
                    + " , inSeconds : "
                    + j
                    + " , inMinutes : "
                    + (j / 60));
            RCPManagerService.this.scanAndStartBridgeProxy(0);
            List<UserInfo> personas = RCPManagerService.this.getPersonas(true);
            if (personas != null && personas.size() > 0) {
              Log.d(
                  RCPManagerService.TAG, "RCPManagerService : No of Personas = " + personas.size());
              for (UserInfo userInfo : personas) {
                Log.d(
                    RCPManagerService.TAG,
                    "RCPManagerService : scanAndStartBridgeProxy called for PersonaId : "
                        + userInfo.id);
                RCPManagerService.this.scanAndStartBridgeProxy(userInfo.id);
              }
              return;
            }
            Log.d(RCPManagerService.TAG, "RCPManagerService :  PersonaInfoList is null or empty ");
          } catch (Exception e) {
            Log.e(
                RCPManagerService.TAG,
                " RCPManagerService : Exception while scanAndStartBridgeProxy() for users "
                    + e.getMessage());
          }
        }
      };
  public List mExchangeDataInfos = new ArrayList();

  public final boolean checkIfGearConnected() {
    return true;
  }

  public static int checkCallerPermissionFor(String str) {
    if (ServiceKeeper.isAuthorized(
            sContext, Binder.getCallingPid(), Binder.getCallingUid(), "RCPManagerService", str)
        == 0) {
      return 0;
    }
    SecurityException securityException =
        new SecurityException(
            "Security Exception Occurred while pid["
                + Binder.getCallingPid()
                + "] with uid["
                + Binder.getCallingUid()
                + "] trying to access methodName ["
                + str
                + "] in [RCPManagerService] service");
    securityException.printStackTrace();
    throw securityException;
  }

  public void registerRCPInterface(IRCPInterface iRCPInterface, int i) {
    if (!initService()) {
      Log.e(TAG, "failed to registerRCPInterface");
      return;
    }
    checkCallerPermissionFor("registerRCPInterface");
    if (this.KNOX_DEBUG) {
      Log.d(TAG, "registerRCPInterface My Context is " + this);
      Log.d(TAG, "registerRCPInterface  User calling is " + i);
    }
    this.mRCPInterfaceMap.put(Integer.valueOf(i), iRCPInterface);
  }

  public final List getPersonas(boolean z) {
    List<UserInfo> users = this.mUm.getUsers(z);
    if (users == null || users.size() <= 0) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    for (UserInfo userInfo : users) {
      if (userInfo.isManagedProfile()) {
        arrayList.add(userInfo);
      }
    }
    return arrayList;
  }

  /* JADX WARN: Removed duplicated region for block: B:37:0x0120  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean initService() {
    boolean z;
    long currentTimeMillis;
    String str;
    StringBuilder sb;
    Log.d(TAG, "initService Start");
    long currentTimeMillis2 = System.currentTimeMillis();
    if (this.mIsInitialized) {
      Log.d(TAG, "initService is already done");
      return true;
    }
    Log.d(TAG, "initService is doing ... ");
    try {
      Context context = this.mContext;
      if (context == null) {
        Log.e(TAG, "initService, failed to getContext");
        this.mIsInitialized = true;
        currentTimeMillis = System.currentTimeMillis();
        str = TAG;
        sb = new StringBuilder();
      } else {
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUm = userManager;
        if (userManager == null) {
          Log.e(TAG, "initService, failed to get USER_SERVICE");
          this.mIsInitialized = true;
          currentTimeMillis = System.currentTimeMillis();
          str = TAG;
          sb = new StringBuilder();
        } else {
          SemPersonaManager semPersonaManager =
              (SemPersonaManager) this.mContext.getSystemService("persona");
          this.mPm = semPersonaManager;
          if (semPersonaManager != null) {
            if (this.mBridgeHandler == null) {
              Handler handler = new Handler(Looper.getMainLooper());
              this.mBridgeHandler = handler;
              handler.postDelayed(this.mBridgeRunnable, 10000L);
            }
            IntentFilter intentFilter =
                new IntentFilter("com.samsung.android.plugin.ACTION_PLUGIN_CHANGE_NOTI");
            intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
            this.mContext.registerReceiver(this.mUserReceiver, intentFilter);
            ContainerStateReceiver.register(this.mContext, this.mContainerstateReceiver);
            this.knoxCaptureInputFilter = new KnoxCaptureInputFilter(this.mContext);
            this.mIsInitialized = true;
            long currentTimeMillis3 = System.currentTimeMillis();
            Log.d(
                TAG,
                "initService End, " + (currentTimeMillis3 - currentTimeMillis2) + "ms elapsed");
            return true;
          }
          Log.e(TAG, "initService, failed to get SEM_PERSONA_SERVICE");
          this.mIsInitialized = true;
          currentTimeMillis = System.currentTimeMillis();
          str = TAG;
          sb = new StringBuilder();
        }
      }
      sb.append("initService End, ");
      sb.append(currentTimeMillis - currentTimeMillis2);
      sb.append("ms elapsed");
      Log.d(str, sb.toString());
      return false;
    } catch (Exception e) {
      try {
        Log.e(TAG, "initService is not completed due to... ");
        e.printStackTrace();
        long currentTimeMillis4 = System.currentTimeMillis();
        Log.d(TAG, "initService End, " + (currentTimeMillis4 - currentTimeMillis2) + "ms elapsed");
        return false;
      } catch (Throwable th) {
        z = false;
        th = th;
        if (z) {
          this.mIsInitialized = true;
        }
        long currentTimeMillis5 = System.currentTimeMillis();
        Log.d(TAG, "initService End, " + (currentTimeMillis5 - currentTimeMillis2) + "ms elapsed");
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
      z = true;
      if (z) {}
      long currentTimeMillis52 = System.currentTimeMillis();
      Log.d(TAG, "initService End, " + (currentTimeMillis52 - currentTimeMillis2) + "ms elapsed");
      throw th;
    }
  }

  public RCPManagerService(Context context) {
    Log.d(TAG, "start ");
    if (context == null) {
      Log.d(TAG, "Context is null(). Failed to start service");
      return;
    }
    this.mContext = context;
    sContext = context;
    initService();
  }

  public final void sendRCPPolicyBroadcastToGearManager(Context context, int i) {
    if (((SemPersonaManager) context.getSystemService("persona")) == null || i != -1) {
      return;
    }
    this.mUm.getUsers(true);
  }

  public final void scanAndStartBridgeProxy(int i) {
    if (this.KNOX_DEBUG) {
      Log.d(TAG, " scanAndStartBridgeProxy called for " + i);
    }
    SemPersonaManager semPersonaManager = this.mPm;
    if (semPersonaManager != null && semPersonaManager.exists(i)) {
      if (this.KNOX_DEBUG) {
        Log.d(TAG, "scanAndStartBridgeProxy : starting BridgeProxy for persona - " + i);
      }
    } else {
      UserInfo profileParent = this.mUm.getProfileParent(i);
      if (profileParent != null && profileParent.id != i) {
        if (this.KNOX_DEBUG) {
          Log.d(TAG, "scanAndStartBridgeProxy : starting BridgeProxy for persona - " + i);
        }
      } else {
        List users = this.mUm.getUsers(true);
        if (users == null || users.size() == 0) {
          if (this.KNOX_DEBUG) {
            Log.d(
                TAG,
                "scanAndStartBridgeProxy: NOT starting Bridge Proxy for user = "
                    + i
                    + "; because it doesn't have personas or it is a guest!");
            return;
          }
          return;
        }
        if (this.KNOX_DEBUG) {
          Log.d(TAG, "scanAndStartBridgeProxy : starting BridgeProxy for owner - " + i);
        }
      }
    }
    try {
      synchronized (this.mBridgeProxyAliveList) {
        if (this.mBridgeProxyAliveList.containsKey(Integer.valueOf(i))) {
          if (this.KNOX_DEBUG) {
            Log.d(TAG, " Returning...BridgeProxy already active for user - " + i);
          }
          return;
        }
        BridgeProxy bridgeProxy = new BridgeProxy(i);
        bridgeProxy.start(this.mContext);
        this.mBridgeProxyAliveList.put(Integer.valueOf(i), bridgeProxy);
        if (this.KNOX_DEBUG) {
          Log.d(TAG, "bindToBridgeProxy : started BridgeProxy for user - " + i);
        }
      }
    } catch (Exception e) {
      Log.d(TAG, "No need to start BridgeProxy for user " + i);
      e.printStackTrace();
    }
  }

  public IRCPInterface getRCPInterface() {
    checkCallerPermissionFor("getRCPInterface");
    Log.d(TAG, "getRCPInterface My Context is " + this);
    Integer num = new Integer(UserHandle.getCallingUserId());
    Log.d(TAG, "getRCPInterface getting User Id : " + num.toString());
    if (this.mRCPInterfaceMap.get(num) != null) {
      return (IRCPInterface) this.mRCPInterfaceMap.get(num);
    }
    Log.d(TAG, "getRCPInterfaceMap.get(userId) is null. Calling scanAndStartBridgeProxy");
    try {
      scanAndStartBridgeProxy(num.intValue());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (IRCPInterface) this.mRCPInterfaceMap.get(num);
  }

  public void deleteAllPersonaData(int i) {
    unregisterBridgeProxy(i);
    this.mBridgeProxyAliveList.remove(Integer.valueOf(i));
  }

  public final void unregisterBridgeProxy(int i) {
    if (this.KNOX_DEBUG) {
      Log.d(TAG, "----- unregisterBridgeProxy : for user - " + i + " -----");
    }
    BridgeProxy bridgeProxy = (BridgeProxy) this.mBridgeProxyAliveList.get(Integer.valueOf(i));
    if (bridgeProxy != null) {
      bridgeProxy.stop();
    }
    this.mRCPInterfaceMap.remove(Integer.valueOf(i));
  }

  public int copyFileInternal(int i, String str, int i2, String str2) {
    if (!initService()) {
      Log.e(TAG, "failed to copyFileInternal");
      return -1;
    }
    checkCallerPermissionFor("copyFile");
    Log.d(
        TAG,
        "copyFile() srcContainerId="
            + i
            + "; srcFilePath="
            + str
            + "; destContainerId="
            + i2
            + "; destFilePath="
            + str2);
    if ("com.samsung.android.bbc.bbcagent".equals(getAppNameByPID(Binder.getCallingPid()))) {
      return copyPackageData(str, i, str2, i2, 3);
    }
    if (isPackageDataRelatedPath(str) || isPackageDataRelatedPath(str2)) {
      Log.d("TAG", "Package data related copy; calling proper delegation method");
      return copyPackageData(str, i, str2, i2, 3);
    }
    return EnterprisePartitionManager.getInstance(this.mContext).copy(str, i, str2, i2);
  }

  public final String getAppNameByPID(int i) {
    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
        ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
    if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
      return "";
    }
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
      if (runningAppProcessInfo.pid == i) {
        return runningAppProcessInfo.processName;
      }
    }
    return "";
  }

  public int moveFile(int i, String str, int i2, String str2) {
    if (!initService()) {
      Log.e(TAG, "failed to moveFile");
      return -1;
    }
    checkCallerPermissionFor("moveFile");
    Log.d(TAG, "P_OS_RCP moveFile");
    Log.d(
        TAG,
        "moveFile() srcContainerId="
            + i
            + "; srcFilePath="
            + str
            + "; destContainerId="
            + i2
            + "; destFilePath="
            + str2);
    return EnterprisePartitionManager.getInstance(this.mContext).move(str, i, str2, i2);
  }

  public boolean isFileExist(String str, int i) {
    if (!initService()) {
      Log.e(TAG, "failed to isFileExist");
      return false;
    }
    checkCallerPermissionFor("isFileExist");
    Log.d(TAG, "P_OS_RCP isFileExist");
    Log.d(TAG, "isFileExist() containerId=" + i + "; path=" + str);
    return EnterprisePartitionManager.getInstance(this.mContext).isFileExist(str, i);
  }

  public List getFiles(String str, int i) {
    if (!initService()) {
      Log.e(TAG, "failed to getFiles");
      return new ArrayList();
    }
    checkCallerPermissionFor("getFiles");
    Log.d(TAG, "P_OS_RCP getFiles");
    Log.d(TAG, "getFiles() containerId=" + i + "; path=" + str);
    return EnterprisePartitionManager.getInstance(this.mContext).getFiles(str, i);
  }

  public boolean deleteFile(String str, int i) {
    if (!initService()) {
      Log.e(TAG, "failed to deleteFile");
      return false;
    }
    checkCallerPermissionFor("deleteFile");
    Log.d(TAG, "deleteFile() containerId=" + i + "; path=" + str);
    return EnterprisePartitionManager.getInstance(this.mContext).deleteFile(str, i);
  }

  public Bundle getFileInfo(String str, int i) {
    if (!initService()) {
      Log.e(TAG, "failed to getFileInfo");
      return new Bundle();
    }
    checkCallerPermissionFor("getFileInfo");
    Log.d(TAG, "P_OS_RCP getFileInfo");
    Log.d(TAG, "getFileInfo() containerId=" + i + "; path=" + str);
    return EnterprisePartitionManager.getInstance(this.mContext).getFileInfo(str, i);
  }

  public int copyChunks(
      int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) {
    if (!initService()) {
      Log.e(TAG, "failed to copyChunks");
      return -1;
    }
    checkCallerPermissionFor("copyChunks");
    Log.d(TAG, "P_OS_RCP copyChunks");
    Log.d(
        TAG,
        "copyChunks() srcContainerId="
            + i
            + "; srcFilePath="
            + str
            + "; destContainerId="
            + i2
            + "; destFilePath="
            + str2
            + "; offset="
            + j
            + "; length="
            + i3
            + "; sessionId="
            + j2
            + "; deleteSrc="
            + z);
    return EnterprisePartitionManager.getInstance(this.mContext)
        .copyChunks(str, i, str2, i2, j, i3, j2, z);
  }

  public void cancelCopyChunks(long j) {
    if (!initService()) {
      Log.e(TAG, "failed to cancelCopyChunks");
      return;
    }
    checkCallerPermissionFor("cancelCopyChunks");
    Log.d(TAG, "P_OS_RCP cancelCopyChunks");
    Log.d(TAG, "cancelCopyChunks() sessionId=" + j);
    EnterprisePartitionManager.getInstance(this.mContext).cancelCopyChunks(j);
  }

  public final void processImeRequest(Bundle bundle) {
    try {
      if (this.mIMMS == null) {
        this.mIMMS = (InputMethodManagerService) ServiceManager.getService("input_method");
      }
      if (this.mIMMS == null) {
        Log.d(TAG, "mIMMS is null");
        return;
      }
      if (bundle.containsKey("commitText")) {
        this.mIMMS.sendInputText(bundle.getCharSequence("commitText"));
      }
      if (bundle.containsKey("keyEvent")) {
        this.mIMMS.sendKeyEvent((KeyEvent) bundle.getParcelable("keyEvent"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public synchronized Bundle exchangeData(String str, int i, Bundle bundle) {
    if (!initService()) {
      Log.e(TAG, "failed to exchangeData");
      return null;
    }
    checkCallerPermissionFor("exchangeData");
    if (str != null && i >= 0) {
      if (bundle != null && bundle.getString("action") != null) {
        String string = bundle.getString("action");
        if (Binder.getCallingUid() == 1000) {
          if ("ImeReq".equals(string)) {
            processImeRequest(bundle);
            return null;
          }
          if ("updateKnoxCaptureFilter".equals(string)) {
            this.mInputManagerService = (InputManagerService) ServiceManager.getService("input");
            if (bundle.getBoolean("add", false)) {
              this.mInputManagerService.setInputFilter(this.knoxCaptureInputFilter);
            } else {
              this.mInputManagerService.setInputFilter(null);
            }
            return null;
          }
          if ("updateInputDeviceId".equals(string)) {
            if (bundle.getBoolean("add", false)) {
              this.knoxCaptureInputFilter.markInputDeviceAsScanner(bundle.getInt("deviceId", 0));
            } else {
              this.knoxCaptureInputFilter.unmarkInputDeviceAsScanner(bundle.getInt("deviceId", 0));
            }
            return null;
          }
        }
      }
      Log.d(TAG, "ERROR | exchange Data | from " + str + ", to user id : " + i);
      return null;
    }
    Log.d(TAG, "exchangeData() return false for input param is not valid" + i);
    return null;
  }

  public int copyFile(int i, String str, int i2, String str2) {
    if (!initService()) {
      Log.e(TAG, "failed to copyFile");
      return -1;
    }
    checkCallerPermissionFor("copyFile");
    IRCPInterface rCPInterface = getRCPInterface();
    if (rCPInterface != null) {
      Log.d(TAG, "copyFile  getRCPInterface not NULL ");
      return rCPInterface.copyFile(i, str, i2, str2);
    }
    Log.d(TAG, "copyFile  getRCPInterface NULL ");
    return -1;
  }

  public long moveFilesForApp(int i, List list, List list2) {
    checkCallerPermissionFor("moveFilesForApp");
    Log.d(TAG, "ERROR | called move Files For App");
    return -1L;
  }

  public long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) {
    if (!initService()) {
      Log.e(TAG, "failed to moveUnlimitedFiles");
      return -1L;
    }
    IRCPInterface rCPInterface = getRCPInterface();
    if (rCPInterface != null) {
      Log.d(TAG, "moveFilesForAppEx moveFiles(>500) getRCPInterface not NULL ");
      return rCPInterface.moveUnlimitedFilesForApp(i, uri, i2, i3);
    }
    Log.d(TAG, "moveFiles(>500) getRCPInterface NULL ");
    return -1L;
  }

  public long moveFilesForAppEx(int i, List list, List list2, int i2) {
    if (!initService()) {
      Log.e(TAG, "failed to moveFilesForAppEx");
      return -1L;
    }
    checkCallerPermissionFor("moveFilesForApp");
    IRCPInterface rCPInterface = getRCPInterface();
    if (rCPInterface != null) {
      Log.d(TAG, "moveFilesForAppEx  getRCPInterface not NULL ");
      return rCPInterface.moveFilesForAppEx(i, list, list2, i2);
    }
    Log.d(TAG, "moveFilesForAppEx  getRCPInterface NULL ");
    return -1L;
  }

  public int copyPackageData(String str, int i, String str2, int i2, int i3) {
    if (!initService()) {
      Log.e(TAG, "failed to copyPackageData");
      return -1;
    }
    Log.d(TAG, "copyPackageData");
    return EnterprisePartitionManager.getInstance(this.mContext).copy(str, i, str2, i2, i3);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    Log.d(TAG, "RCP DumpState Started");
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println(
          "Permission Denial: can't dump RCPManagerService from from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " without permission android.permission.DUMP");
      return;
    }
    List<UserInfo> users = this.mUm.getUsers(false);
    if (users != null && !users.isEmpty()) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      printWriter.println("No of Personas : " + users.size());
      RCPDumpState rCPDumpState = RCPDumpState.getInstance(this.mContext, printWriter);
      for (UserInfo userInfo : users) {
        int i = userInfo.id;
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        boolean isUserRunning = this.mUm.isUserRunning(new UserHandle(i));
        printWriter.println(
            "PersonaId : "
                + i
                + " , isKnoxId : "
                + isKnoxId
                + " , isUserRunning : "
                + isUserRunning);
        if (isKnoxId && isUserRunning) {
          printWriter.println("++++++++++++++++FileOpsTable of " + i + "++++++++++++++++");
          rCPDumpState.dumpStateFileOpsTable(printWriter, i);
        }
        if (SemPersonaManager.isSecureFolderId(i)) {
          Log.i(TAG, "BNR logs");
          printWriter.println(
              "++++++++++++++++Start of BackupAndRestore dump for Persona ID : "
                  + userInfo.id
                  + "++++++++++++++++");
          rCPDumpState.dumpBackupAndRestoreHistory(printWriter, i);
        }
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return;
    }
    printWriter.println("No of Personas : Zero");
  }

  public final boolean isPackageDataRelatedPath(String str) {
    if (str == null) {
      return false;
    }
    if (str.startsWith("/data/data")
        || str.startsWith("/data/user")
        || str.startsWith("/data/user_de")) {
      Log.d(TAG, "package path detected: " + str);
      return true;
    }
    Log.d(TAG, "not a package path: " + str);
    return false;
  }
}
