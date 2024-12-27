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

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemIRCPCallback;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.SemRemoteContentManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BridgeProxy {
    public Context mContext;
    public final UserHandle mDelegateUserHandle;
    public final int mDelegateUserId;
    public final IRCPInterfaceCallBack mIRCPInterfaceCallBack = new IRCPInterfaceCallBack();
    public SemRemoteContentManager mSemRemoteContentManager = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IRCPInterfaceCallBack extends IRCPInterface.Stub {
        public IRCPInterfaceCallBack() {}

        public final void cancel(long j) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "cancel");
            Log.d("BridgeProxy", "cancel() session id =" + j);
        }

        public final int copyFile(int i, String str, int i2, String str2) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "copyFile");
            Log.d("BridgeProxy", "copyFile() srcContainerId=" + i + "; srcFilePath=" + str);
            StringBuilder sb = new StringBuilder("copyFile() destContainerId=");
            sb.append(i2);
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    sb, "; destFilePath=", str2, "BridgeProxy");
            BridgeProxy.this.getClass();
            BridgeProxy.this.getClass();
            try {
                if (str.equals(str2)) {
                    if (str.contains("/mnt/extSdCard")) {
                        str2 = str.replaceFirst("^/mnt/extSdCard", "/mnt/sdcard");
                    } else if (str.contains("/storage/extSdCard")) {
                        str2 = str.replaceFirst("^/storage/extSdCard", "/mnt/sdcard");
                    } else if (str.contains("/storage/")) {
                        str2 = "/mnt/sdcard" + str.substring(str.indexOf("/", 9), str.length());
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d("BridgeProxy", "changeExtSdPath(): npe has occured");
            }
            return BridgeProxy.this.mSemRemoteContentManager.copyFileInternal(i, str, i2, str2);
        }

        public final long copyFiles(
                int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "copyFiles");
            Log.d(
                    "BridgeProxy",
                    "copyFiles() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
            Log.d(
                    "BridgeProxy",
                    "copyFiles() destContainerId=" + i2 + "; destFilePaths=" + list2.toString());
            Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
            if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
                intent.setPackage("com.samsung.knox.securefolder");
            } else {
                intent.setPackage("com.samsung.android.knox.containercore");
            }
            ArrayList<String> arrayList = new ArrayList<>(list);
            ArrayList<String> arrayList2 = new ArrayList<>(list2);
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(
                            i, "task", "TASK_COPY_FILES", "srcContainerId");
            m.putInt("destContainerId", i2);
            m.putStringArrayList("srcFilePaths", arrayList);
            m.putStringArrayList("destFilePaths", arrayList2);
            m.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
            long random = (long) (Math.random() * 10000.0d);
            Log.d("BridgeProxy", "copyFiles() ,mSessionId :" + Long.valueOf(random));
            m.putLong("sessionId", random);
            intent.putExtras(m);
            if (i == 0) {
                i = i2;
            }
            Log.d(
                    "BridgeProxy",
                    "copyFiles(), Starting FileOperationsHandler service TASK_COPY_FILES");
            try {
                BridgeProxy.this.mContext.startServiceAsUser(intent, new UserHandle(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return random;
        }

        public final long copyFiles2(
                int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback, String str) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "copyFiles");
            Log.d(
                    "BridgeProxy",
                    "copyFiles2() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
            Log.d(
                    "BridgeProxy",
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
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(
                            i, "task", "TASK_COPY_FILES", "srcContainerId");
            m.putInt("destContainerId", i2);
            m.putStringArrayList("srcFilePaths", arrayList);
            m.putStringArrayList("destFilePaths", arrayList2);
            m.putString("sourceClassName", str);
            m.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
            long random = (long) (Math.random() * 10000.0d);
            Log.d("BridgeProxy", "copyFiles2() ,mSessionId :" + Long.valueOf(random));
            m.putLong("sessionId", random);
            intent.putExtras(m);
            if (i == 0) {
                i = i2;
            }
            Log.d(
                    "BridgeProxy",
                    "copyFiles2(), Starting FileOperationsHandler service TASK_COPY_FILES");
            try {
                BridgeProxy.this.mContext.startServiceAsUser(intent, new UserHandle(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return random;
        }

        public final String getErrorMessage(int i) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "getErrorMessage");
            Log.d("BridgeProxy", "getErrorMessage(): errorId : " + i);
            return "General error";
        }

        public final Bundle getFileInfo(String str, int i) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "getFileInfo");
            StringBuilder sb = new StringBuilder("get File Info : path=");
            sb.append(str);
            sb.append("; containerId=");
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, i, "BridgeProxy");
            return BridgeProxy.this.mSemRemoteContentManager.getFileInfo(str, i);
        }

        public final List getFiles(String str, int i) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "getFiles");
            StringBuilder sb = new StringBuilder("get filesg [");
            sb.append(i);
            sb.append("], ");
            VpnManagerService$$ExternalSyntheticOutline0.m(sb, str, "BridgeProxy");
            return null;
        }

        public final boolean isFileExist(String str, int i) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "isFileExist");
            StringBuilder sb = new StringBuilder("file exist checking [");
            sb.append(i);
            sb.append("], ");
            VpnManagerService$$ExternalSyntheticOutline0.m(sb, str, "BridgeProxy");
            return BridgeProxy.this.mSemRemoteContentManager.isFileExist(str, i);
        }

        public final int moveFile(int i, String str, int i2, String str2) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "moveFile");
            Log.d("BridgeProxy", "ERROR || Deprecated API level - move file");
            return -1;
        }

        public final long moveFiles(
                int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "moveFiles");
            Log.d(
                    "BridgeProxy",
                    "moveFiles() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
            Log.d(
                    "BridgeProxy",
                    "moveFiles() destContainerId=" + i2 + "; destFilePaths=" + list2.toString());
            Intent intent = new Intent("com.sec.knox.bridge.service.ACTION_FILE_OPERATIONS");
            if (SemPersonaManager.isSecureFolderId(i) || SemPersonaManager.isSecureFolderId(i2)) {
                intent.setPackage("com.samsung.knox.securefolder");
            } else {
                intent.setPackage("com.samsung.android.knox.containercore");
            }
            ArrayList<String> arrayList = new ArrayList<>(list);
            ArrayList<String> arrayList2 = new ArrayList<>(list2);
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(
                            i, "task", "TASK_MOVE_FILES", "srcContainerId");
            m.putInt("destContainerId", i2);
            m.putStringArrayList("srcFilePaths", arrayList);
            m.putStringArrayList("destFilePaths", arrayList2);
            m.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
            long random = (long) (Math.random() * 10000.0d);
            Log.d("BridgeProxy", "moveFiles ,mSessionId :" + Long.valueOf(random));
            m.putLong("sessionId", random);
            intent.putExtras(m);
            Log.d(
                    "BridgeProxy",
                    "moveFiles(), Starting FileOperationsHandler service TASK_MOVE_FILES");
            try {
                BridgeProxy bridgeProxy = BridgeProxy.this;
                bridgeProxy.mContext.startServiceAsUser(intent, bridgeProxy.mDelegateUserHandle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return random;
        }

        public final long moveFiles2(
                int i, List list, int i2, List list2, SemIRCPCallback semIRCPCallback, String str) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "moveFiles");
            Log.d(
                    "BridgeProxy",
                    "moveFiles2() srcContainerId=" + i + "; srcFilePaths=" + list.toString());
            Log.d(
                    "BridgeProxy",
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
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(
                            i, "task", "TASK_MOVE_FILES", "srcContainerId");
            m.putInt("destContainerId", i2);
            m.putStringArrayList("srcFilePaths", arrayList);
            m.putStringArrayList("destFilePaths", arrayList2);
            m.putString("sourceClassName", str);
            m.putParcelable("callBackMessenger", new Messenger(semIRCPCallback.asBinder()));
            long random = (long) (Math.random() * 10000.0d);
            Log.d("BridgeProxy", "moveFiles2 ,mSessionId :" + Long.valueOf(random));
            m.putLong("sessionId", random);
            intent.putExtras(m);
            Log.d(
                    "BridgeProxy",
                    "moveFiles2(), Starting FileOperationsHandler service TASK_MOVE_FILES");
            try {
                BridgeProxy bridgeProxy = BridgeProxy.this;
                bridgeProxy.mContext.startServiceAsUser(intent, bridgeProxy.mDelegateUserHandle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return random;
        }

        public final long moveFilesForAppEx(int i, List list, List list2, int i2) {
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "moveFilesForApp");
            int i3 = BridgeProxy.this.mDelegateUserId;
            try {
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            if (list == null) {
                Log.d(
                        "BridgeProxy",
                        "ERROR | move Files For App Ex | invalid source file Paths, paths are"
                            + " null");
                return 0L;
            }
            Log.d(
                    "BridgeProxy",
                    "moveFilesForAppEx() srcContainerId="
                            + i3
                            + "; srcFilePaths.size()="
                            + list.size());
            Log.d(
                    "BridgeProxy",
                    "moveFilesForAppEx() destContainerId="
                            + i2
                            + "; destFilePaths.size()="
                            + list2.size());
            StringBuilder sb = new StringBuilder("moveFilesForAppEx() reqApp=");
            sb.append(Integer.toString(i));
            Log.d("BridgeProxy", sb.toString());
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
                Log.d("BridgeProxy", "Need to Create secure Folder");
                if (SemPersonaManager.getSecureFolderId(BridgeProxy.this.mContext) <= 0) {
                    bundle.putBoolean("isSilent", true);
                    intent.putExtras(bundle);
                    intent.setClassName(
                            "com.samsung.knox.securefolder",
                            "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
                    BridgeProxy bridgeProxy = BridgeProxy.this;
                    BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                            bridgeProxy, bridgeProxy.mContext, intent, new UserHandle(0));
                    return 0L;
                }
            }
            if (!SemPersonaManager.isSecureFolderId(i3)
                    && !SemPersonaManager.isSecureFolderId(i2)) {
                if (!SemPersonaManager.isKnoxId(i3) && !SemPersonaManager.isKnoxId(i2)) {
                    Log.d(
                            "BridgeProxy",
                            "ERROR | move Files For App Ex | invalid container id is -1");
                    return 0L;
                }
                intent.setClassName(
                        "com.samsung.android.knox.containercore",
                        "com.samsung.android.knox.containercore.rcpcomponents.move.activity.MoveToKnoxGateActivity");
                BridgeProxy bridgeProxy2 = BridgeProxy.this;
                BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                        bridgeProxy2,
                        bridgeProxy2.mContext,
                        intent,
                        bridgeProxy2.mDelegateUserHandle);
                return 0L;
            }
            intent.setClassName(
                    "com.samsung.knox.securefolder",
                    "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
            BridgeProxy bridgeProxy22 = BridgeProxy.this;
            BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                    bridgeProxy22,
                    bridgeProxy22.mContext,
                    intent,
                    bridgeProxy22.mDelegateUserHandle);
            return 0L;
        }

        public final long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) {
            Intent intent;
            BridgeProxy.m324$$Nest$mcheckCallerPermissionFor(BridgeProxy.this, "moveFilesForApp");
            int i4 = BridgeProxy.this.mDelegateUserId;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i4,
                            i3,
                            "moveUnlimitedFilesForApp() srcContainerId=",
                            "; destContainerId=",
                            "; uri=");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i2, uri != null ? uri.toString() : "null", "; fileCount=", "reqApp = ", m);
            m.append(Integer.toString(i));
            Log.d("BridgeProxy", m.toString());
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
                    Log.d("BridgeProxy", "Need to Create secure Folder");
                    if (SemPersonaManager.getSecureFolderId(BridgeProxy.this.mContext) <= 0) {
                        bundle.putBoolean("isSilent", true);
                        intent.putExtras(bundle);
                        intent.setClassName(
                                "com.samsung.knox.securefolder",
                                "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
                        BridgeProxy bridgeProxy = BridgeProxy.this;
                        BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                                bridgeProxy, bridgeProxy.mContext, intent, new UserHandle(0));
                        return 0L;
                    }
                }
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            if (!SemPersonaManager.isSecureFolderId(i4)
                    && !SemPersonaManager.isSecureFolderId(i3)) {
                if (!SemPersonaManager.isKnoxId(i4) && !SemPersonaManager.isKnoxId(i3)) {
                    Log.d(
                            "BridgeProxy",
                            "ERROR | move Files For App Ex | invalid container id is -1");
                    return 0L;
                }
                intent.setClassName(
                        "com.samsung.android.knox.containercore",
                        "com.samsung.android.knox.containercore.rcpcomponents.move.activity.MoveToKnoxGateActivity");
                BridgeProxy bridgeProxy2 = BridgeProxy.this;
                BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                        bridgeProxy2,
                        bridgeProxy2.mContext,
                        intent,
                        bridgeProxy2.mDelegateUserHandle);
                return 0L;
            }
            intent.setClassName(
                    "com.samsung.knox.securefolder",
                    "com.samsung.knox.securefolder.switcher.SwitchAliasActivity");
            BridgeProxy bridgeProxy22 = BridgeProxy.this;
            BridgeProxy.m325$$Nest$mclearIdentityAndStartActivityAsUser(
                    bridgeProxy22,
                    bridgeProxy22.mContext,
                    intent,
                    bridgeProxy22.mDelegateUserHandle);
            return 0L;
        }
    }

    /* renamed from: -$$Nest$mcheckCallerPermissionFor, reason: not valid java name */
    public static void m324$$Nest$mcheckCallerPermissionFor(BridgeProxy bridgeProxy, String str) {
        bridgeProxy.getClass();
        Log.d(
                "BridgeProxy",
                "checkCallerPermissionFor, ServiceName :Proxy , MethodName : ".concat(str));
    }

    /* renamed from: -$$Nest$mclearIdentityAndStartActivityAsUser, reason: not valid java name */
    public static void m325$$Nest$mclearIdentityAndStartActivityAsUser(
            BridgeProxy bridgeProxy, Context context, Intent intent, UserHandle userHandle) {
        if (bridgeProxy.mContext == null || userHandle == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            context.startActivityAsUser(intent, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public BridgeProxy(int i) {
        this.mDelegateUserId = 0;
        this.mDelegateUserHandle = UserHandle.OWNER;
        this.mDelegateUserId = i;
        this.mDelegateUserHandle = new UserHandle(i);
    }

    public final void start(Context context) {
        this.mContext = context;
        StringBuilder sb = new StringBuilder("onCreate BridgeProxy is starting for user ");
        int i = this.mDelegateUserId;
        sb.append(i);
        sb.append(" ");
        sb.append(this);
        Log.d("BridgeProxy", sb.toString());
        SemRemoteContentManager semRemoteContentManager =
                (SemRemoteContentManager) this.mContext.getSystemService("rcp");
        this.mSemRemoteContentManager = semRemoteContentManager;
        semRemoteContentManager.registerRCPInterface(this.mIRCPInterfaceCallBack, i);
    }
}
