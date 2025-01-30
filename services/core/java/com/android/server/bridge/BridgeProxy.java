package com.android.server.bridge;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.IRCPInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Messenger;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.ServiceKeeper;
import com.samsung.android.knox.SemIRCPCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemRemoteContentManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class BridgeProxy {
  public static final String TAG = "BridgeProxy";
  public Context mContext;
  public UserHandle mDelegateUserHandle;
  public int mDelegateUserId;
  public final IRCPInterfaceCallBack mIRCPInterfaceCallBack = new IRCPInterfaceCallBack();
  public SemRemoteContentManager mSemRemoteContentManager = null;

  public final int getFilesPolicy(int i, int i2) {
    return 0;
  }

  public final int checkCallerPermissionFor(String str) {
    Log.d(TAG, "checkCallerPermissionFor, ServiceName :Proxy , MethodName : " + str);
    if (ServiceKeeper.isAuthorized(
            this.mContext, Binder.getCallingPid(), Binder.getCallingUid(), "Proxy", str)
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
                + "] in [Proxy] service");
    securityException.printStackTrace();
    throw securityException;
  }

  public BridgeProxy(int i) {
    this.mDelegateUserId = 0;
    this.mDelegateUserHandle = UserHandle.OWNER;
    this.mDelegateUserId = i;
    this.mDelegateUserHandle = new UserHandle(this.mDelegateUserId);
  }

  public void start(Context context) {
    this.mContext = context;
    Log.d(TAG, "onCreate BridgeProxy is starting for user " + this.mDelegateUserId + " " + this);
    SemRemoteContentManager semRemoteContentManager =
        (SemRemoteContentManager) this.mContext.getSystemService("rcp");
    this.mSemRemoteContentManager = semRemoteContentManager;
    semRemoteContentManager.registerRCPInterface(this.mIRCPInterfaceCallBack, this.mDelegateUserId);
  }

  public class IRCPInterfaceCallBack extends IRCPInterface.Stub {
    public IRCPInterfaceCallBack() {}

    public long copyFiles(int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback) {
      BridgeProxy.this.checkCallerPermissionFor("copyFiles");
      Log.d(
          BridgeProxy.TAG, "copyFiles() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
      Log.d(
          BridgeProxy.TAG,
          "copyFiles() destContainerId=" + i2 + "; destFilePaths=" + list2.toString());
      Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
      if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
        intent.setPackage("com.samsung.knox.securefolder");
      } else {
        intent.setPackage("com.samsung.android.knox.containercore");
      }
      ArrayList<String> arrayList = new ArrayList<>(list);
      ArrayList<String> arrayList2 = new ArrayList<>(list2);
      Bundle bundle = new Bundle();
      bundle.putString("task", "TASK_COPY_FILES");
      bundle.putInt("srcContainerId", i);
      bundle.putInt("destContainerId", i2);
      bundle.putStringArrayList("srcFilePaths", arrayList);
      bundle.putStringArrayList("destFilePaths", arrayList2);
      bundle.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
      Long valueOf = Long.valueOf((long) (Math.random() * 10000.0d));
      Log.d(BridgeProxy.TAG, "copyFiles() ,mSessionId :" + valueOf);
      bundle.putLong("sessionId", valueOf.longValue());
      intent.putExtras(bundle);
      if (i == 0) {
        i = i2;
      }
      Log.d(BridgeProxy.TAG, "copyFiles(), Starting FileOperationsHandler service TASK_COPY_FILES");
      try {
        BridgeProxy.this.mContext.startServiceAsUser(intent, new UserHandle(i));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return valueOf.longValue();
    }

    public long copyFiles2(
        int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback, String str) {
      BridgeProxy.this.checkCallerPermissionFor("copyFiles");
      Log.d(
          BridgeProxy.TAG,
          "copyFiles2() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
      Log.d(
          BridgeProxy.TAG,
          "copyFiles2() destContainerId="
              + i2
              + "; destFilePaths="
              + list2.toString()
              + " , SourceClassName : "
              + str);
      Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
      if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
        intent.setPackage("com.samsung.knox.securefolder");
      } else {
        intent.setPackage("com.samsung.android.knox.containercore");
      }
      ArrayList<String> arrayList = new ArrayList<>(list);
      ArrayList<String> arrayList2 = new ArrayList<>(list2);
      Bundle bundle = new Bundle();
      bundle.putString("task", "TASK_COPY_FILES");
      bundle.putInt("srcContainerId", i);
      bundle.putInt("destContainerId", i2);
      bundle.putStringArrayList("srcFilePaths", arrayList);
      bundle.putStringArrayList("destFilePaths", arrayList2);
      bundle.putString("sourceClassName", str);
      bundle.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
      Long valueOf = Long.valueOf((long) (Math.random() * 10000.0d));
      Log.d(BridgeProxy.TAG, "copyFiles2() ,mSessionId :" + valueOf);
      bundle.putLong("sessionId", valueOf.longValue());
      intent.putExtras(bundle);
      if (i == 0) {
        i = i2;
      }
      Log.d(
          BridgeProxy.TAG, "copyFiles2(), Starting FileOperationsHandler service TASK_COPY_FILES");
      try {
        BridgeProxy.this.mContext.startServiceAsUser(intent, new UserHandle(i));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return valueOf.longValue();
    }

    public long moveFiles(int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback) {
      BridgeProxy.this.checkCallerPermissionFor("moveFiles");
      Log.d(
          BridgeProxy.TAG, "moveFiles() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
      Log.d(
          BridgeProxy.TAG,
          "moveFiles() destContainerId=" + i2 + "; destFilePaths=" + list2.toString());
      Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
      if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
        intent.setPackage("com.samsung.knox.securefolder");
      } else {
        intent.setPackage("com.samsung.android.knox.containercore");
      }
      ArrayList<String> arrayList = new ArrayList<>(list);
      ArrayList<String> arrayList2 = new ArrayList<>(list2);
      Bundle bundle = new Bundle();
      bundle.putString("task", "TASK_MOVE_FILES");
      bundle.putInt("srcContainerId", i);
      bundle.putInt("destContainerId", i2);
      bundle.putStringArrayList("srcFilePaths", arrayList);
      bundle.putStringArrayList("destFilePaths", arrayList2);
      bundle.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
      Long valueOf = Long.valueOf((long) (Math.random() * 10000.0d));
      Log.d(BridgeProxy.TAG, "moveFiles ,mSessionId :" + valueOf);
      bundle.putLong("sessionId", valueOf.longValue());
      intent.putExtras(bundle);
      Log.d(BridgeProxy.TAG, "moveFiles(), Starting FileOperationsHandler service TASK_MOVE_FILES");
      try {
        BridgeProxy.this.mContext.startServiceAsUser(intent, BridgeProxy.this.mDelegateUserHandle);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return valueOf.longValue();
    }

    public long moveFiles2(
        int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback, String str) {
      BridgeProxy.this.checkCallerPermissionFor("moveFiles");
      Log.d(
          BridgeProxy.TAG,
          "moveFiles2() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
      Log.d(
          BridgeProxy.TAG,
          "moveFiles2() destContainerId="
              + i2
              + "; destFilePaths="
              + list2.toString()
              + " , SourceClassName : "
              + str);
      Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
      if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
        intent.setPackage("com.samsung.knox.securefolder");
      } else {
        intent.setPackage("com.samsung.android.knox.containercore");
      }
      ArrayList<String> arrayList = new ArrayList<>(list);
      ArrayList<String> arrayList2 = new ArrayList<>(list2);
      Bundle bundle = new Bundle();
      bundle.putString("task", "TASK_MOVE_FILES");
      bundle.putInt("srcContainerId", i);
      bundle.putInt("destContainerId", i2);
      bundle.putStringArrayList("srcFilePaths", arrayList);
      bundle.putStringArrayList("destFilePaths", arrayList2);
      bundle.putString("sourceClassName", str);
      bundle.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
      Long valueOf = Long.valueOf((long) (Math.random() * 10000.0d));
      Log.d(BridgeProxy.TAG, "moveFiles2 ,mSessionId :" + valueOf);
      bundle.putLong("sessionId", valueOf.longValue());
      intent.putExtras(bundle);
      Log.d(
          BridgeProxy.TAG, "moveFiles2(), Starting FileOperationsHandler service TASK_MOVE_FILES");
      try {
        BridgeProxy.this.mContext.startServiceAsUser(intent, BridgeProxy.this.mDelegateUserHandle);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return valueOf.longValue();
    }

    public long moveUnlimitedFiles(int i, int i2, Uri uri, SemIRCPCallback semIRCPCallback) {
      BridgeProxy.this.checkCallerPermissionFor("moveFiles");
      Log.d(BridgeProxy.TAG, "ERROR || Deprecated API level - move unlimited files");
      return -1L;
    }

    public long moveUnlimitedFiles2(
        int i, int i2, Uri uri, SemIRCPCallback semIRCPCallback, String str) {
      BridgeProxy.this.checkCallerPermissionFor("moveFiles");
      Log.d(BridgeProxy.TAG, "ERROR || Deprecated API level - move unlimited files2");
      return -1L;
    }

    public long moveFilesForApp(int i, List list, List list2) {
      BridgeProxy.this.checkCallerPermissionFor("moveFilesForApp");
      Log.d(BridgeProxy.TAG, "ERROR || Deprecated API level - move files for app");
      return -1L;
    }

    public long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) {
      Intent intent;
      BridgeProxy.this.checkCallerPermissionFor("moveFilesForApp");
      int i4 = BridgeProxy.this.mDelegateUserId;
      String str = BridgeProxy.TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("moveUnlimitedFilesForApp() srcContainerId=");
      sb.append(i4);
      sb.append("; destContainerId=");
      sb.append(i3);
      sb.append("; uri=");
      sb.append(uri != null ? uri.toString() : "null");
      sb.append("; fileCount=");
      sb.append(i2);
      sb.append("reqApp = ");
      sb.append(Integer.toString(i));
      Log.d(str, sb.toString());
      try {
        intent = new Intent("com.sec.knox.bridge.MOVE_TO_KNOX");
        Bundle bundle = new Bundle();
        bundle.putString("task", "TASK_MOVE_FILES");
        bundle.putInt("requestApp", i);
        bundle.putInt("srcContainerId", i4);
        bundle.putInt("destContainerId", i3);
        bundle.putInt("fileCount", i2);
        bundle.putString("moveToFor", "File");
        bundle.putBoolean("isUnlimitedSharing", true);
        intent.putExtra("unlimitedMoveUri", uri);
        intent.putExtras(bundle);
        if (i3 == -1000) {
          Log.d(BridgeProxy.TAG, "Need to Create secure Folder");
          BridgeProxy bridgeProxy = BridgeProxy.this;
          if (bridgeProxy.getSecureFolderId(bridgeProxy.mContext) <= 0) {
            bundle.putBoolean("isSilent", true);
            intent.putExtras(bundle);
            intent.setClassName(
                "com.samsung.knox.securefolder",
                "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
            BridgeProxy bridgeProxy2 = BridgeProxy.this;
            bridgeProxy2.clearIdentityAndStartActivityAsUser(
                bridgeProxy2.mContext, intent, new UserHandle(0));
            return 0L;
          }
        }
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
      }
      if (!SemPersonaManager.isSecureFolderId(i4) && !SemPersonaManager.isSecureFolderId(i3)) {
        if (!SemPersonaManager.isKnoxId(i4) && !SemPersonaManager.isKnoxId(i3)) {
          Log.d(BridgeProxy.TAG, "ERROR | move Files For App Ex | invalid container id is -1");
          return 0L;
        }
        intent.setClassName(
            "com.samsung.android.knox.containercore",
            "com.samsung.android.knox.containercore.rcpcomponents.move.activity.MoveToKnoxGateActivity");
        BridgeProxy bridgeProxy3 = BridgeProxy.this;
        bridgeProxy3.clearIdentityAndStartActivityAsUser(
            bridgeProxy3.mContext, intent, BridgeProxy.this.mDelegateUserHandle);
        return 0L;
      }
      intent.setClassName(
          "com.samsung.knox.securefolder",
          "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
      BridgeProxy bridgeProxy32 = BridgeProxy.this;
      bridgeProxy32.clearIdentityAndStartActivityAsUser(
          bridgeProxy32.mContext, intent, BridgeProxy.this.mDelegateUserHandle);
      return 0L;
    }

    public long moveFilesForAppEx(int i, List list, List list2, int i2) {
      BridgeProxy.this.checkCallerPermissionFor("moveFilesForApp");
      int i3 = BridgeProxy.this.mDelegateUserId;
      try {
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
      }
      if (list == null) {
        Log.d(
            BridgeProxy.TAG,
            "ERROR | move Files For App Ex | invalid source file Paths, paths are null");
        return 0L;
      }
      Log.d(
          BridgeProxy.TAG,
          "moveFilesForAppEx() srcContainerId=" + i3 + "; srcFilePaths.size()=" + list.size());
      Log.d(
          BridgeProxy.TAG,
          "moveFilesForAppEx() destContainerId=" + i2 + "; destFilePaths.size()=" + list2.size());
      String str = BridgeProxy.TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("moveFilesForAppEx() reqApp=");
      sb.append(Integer.toString(i));
      Log.d(str, sb.toString());
      ArrayList<String> arrayList = new ArrayList<>(list);
      ArrayList<String> arrayList2 = new ArrayList<>(list2);
      Intent intent = new Intent("com.sec.knox.bridge.MOVE_TO_KNOX");
      intent.addFlags(268435456);
      Bundle bundle = new Bundle();
      bundle.putString("task", "TASK_MOVE_FILES");
      bundle.putInt("requestApp", i);
      bundle.putInt("srcContainerId", i3);
      bundle.putInt("destContainerId", i2);
      bundle.putInt("fileCount", list.size());
      bundle.putStringArrayList("srcFilePaths", arrayList);
      bundle.putStringArrayList("destFilePaths", arrayList2);
      bundle.putString("moveToFor", "File");
      intent.putExtras(bundle);
      if (i2 == -1000) {
        Log.d(BridgeProxy.TAG, "Need to Create secure Folder");
        BridgeProxy bridgeProxy = BridgeProxy.this;
        if (bridgeProxy.getSecureFolderId(bridgeProxy.mContext) <= 0) {
          bundle.putBoolean("isSilent", true);
          intent.putExtras(bundle);
          intent.setClassName(
              "com.samsung.knox.securefolder",
              "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
          BridgeProxy bridgeProxy2 = BridgeProxy.this;
          bridgeProxy2.clearIdentityAndStartActivityAsUser(
              bridgeProxy2.mContext, intent, new UserHandle(0));
          return 0L;
        }
      }
      if (!SemPersonaManager.isSecureFolderId(i3) && !SemPersonaManager.isSecureFolderId(i2)) {
        if (!SemPersonaManager.isKnoxId(i3) && !SemPersonaManager.isKnoxId(i2)) {
          Log.d(BridgeProxy.TAG, "ERROR | move Files For App Ex | invalid container id is -1");
          return 0L;
        }
        intent.setClassName(
            "com.samsung.android.knox.containercore",
            "com.samsung.android.knox.containercore.rcpcomponents.move.activity.MoveToKnoxGateActivity");
        BridgeProxy bridgeProxy3 = BridgeProxy.this;
        bridgeProxy3.clearIdentityAndStartActivityAsUser(
            bridgeProxy3.mContext, intent, BridgeProxy.this.mDelegateUserHandle);
        return 0L;
      }
      intent.setClassName(
          "com.samsung.knox.securefolder",
          "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
      BridgeProxy bridgeProxy32 = BridgeProxy.this;
      bridgeProxy32.clearIdentityAndStartActivityAsUser(
          bridgeProxy32.mContext, intent, BridgeProxy.this.mDelegateUserHandle);
      return 0L;
    }

    public int copyFile(int i, String str, int i2, String str2) {
      BridgeProxy.this.checkCallerPermissionFor("copyFile");
      Log.d(BridgeProxy.TAG, "copyFile() srcContainerId=" + i + "; srcFilePath=" + str);
      Log.d(BridgeProxy.TAG, "copyFile() destContainerId=" + i2 + "; destFilePath=" + str2);
      int filesPolicy = BridgeProxy.this.getFilesPolicy(i, 1);
      if (filesPolicy != 0) {
        Log.d(
            BridgeProxy.TAG,
            "copyFile(): Permissions (POLICY_NOT_ALLOWED)  or error for srcContainerId="
                + i
                + "; exportCheck="
                + filesPolicy);
        return filesPolicy;
      }
      int filesPolicy2 = BridgeProxy.this.getFilesPolicy(i2, 2);
      if (filesPolicy2 != 0) {
        Log.d(
            BridgeProxy.TAG,
            "copyFile(): Permissions (POLICY_NOT_ALLOWED) or error for destContainerId="
                + i2
                + "; importCheck="
                + filesPolicy2);
        return filesPolicy2;
      }
      return BridgeProxy.this.mSemRemoteContentManager.copyFileInternal(
          i, str, i2, changeExtSdPath(str, str2));
    }

    public int moveFile(int i, String str, int i2, String str2) {
      BridgeProxy.this.checkCallerPermissionFor("moveFile");
      Log.d(BridgeProxy.TAG, "ERROR || Deprecated API level - move file");
      return -1;
    }

    public String getErrorMessage(int i) {
      BridgeProxy.this.checkCallerPermissionFor("getErrorMessage");
      Log.d(BridgeProxy.TAG, "getErrorMessage(): errorId : " + i);
      return "General error";
    }

    public boolean isFileExist(String str, int i) {
      BridgeProxy.this.checkCallerPermissionFor("isFileExist");
      Log.d(BridgeProxy.TAG, "file exist checking [" + i + "], " + str);
      return BridgeProxy.this.mSemRemoteContentManager.isFileExist(str, i);
    }

    public List getFiles(String str, int i) {
      BridgeProxy.this.checkCallerPermissionFor("getFiles");
      Log.d(BridgeProxy.TAG, "get filesg [" + i + "], " + str);
      return null;
    }

    public Bundle getFileInfo(String str, int i) {
      BridgeProxy.this.checkCallerPermissionFor("getFileInfo");
      Log.d(BridgeProxy.TAG, "get File Info : path=" + str + "; containerId=" + i);
      return BridgeProxy.this.mSemRemoteContentManager.getFileInfo(str, i);
    }

    public int copyChunks(
        int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) {
      Log.d(BridgeProxy.TAG, "Warning!!!!  copyChunks() is disabled!!!");
      return -3070355;
    }

    public void cancelCopyChunks(long j) {
      BridgeProxy.this.checkCallerPermissionFor("cancelCopyChunks");
      Log.d(BridgeProxy.TAG, "cancel CopyChunks() session Id=" + j);
    }

    public void cancel(long j) {
      BridgeProxy.this.checkCallerPermissionFor("cancel");
      Log.d(BridgeProxy.TAG, "cancel() session id =" + j);
    }

    public final String changeExtSdPath(String str, String str2) {
      String str3;
      try {
        if (!str.equals(str2)) {
          return str2;
        }
        if (str.contains("/mnt/extSdCard")) {
          str3 = str.replaceFirst("^/mnt/extSdCard", "/mnt/sdcard");
        } else if (str.contains("/storage/extSdCard")) {
          str3 = str.replaceFirst("^/storage/extSdCard", "/mnt/sdcard");
        } else {
          if (!str.contains("/storage/")) {
            return str2;
          }
          str3 = "/mnt/sdcard" + str.substring(str.indexOf("/", 9), str.length());
        }
        return str3;
      } catch (NullPointerException e) {
        e.printStackTrace();
        Log.d(BridgeProxy.TAG, "changeExtSdPath(): npe has occured");
        return str2;
      }
    }
  }

  public void stop() {
    Log.d(TAG, "----- stop called -----");
  }

  public final void clearIdentityAndStartActivityAsUser(
      Context context, Intent intent, UserHandle userHandle) {
    if (this.mContext == null || intent == null || userHandle == null) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      context.startActivityAsUser(intent, userHandle);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final int getSecureFolderId(Context context) {
    return SemPersonaManager.getSecureFolderId(context);
  }
}
