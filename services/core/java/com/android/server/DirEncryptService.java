package com.android.server;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IVoldTaskListener;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ServiceManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.security.DirEncryptionWrapper;
import com.samsung.android.security.IDirEncryptService;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public final class DirEncryptService extends IDirEncryptService.Stub {
    public static final File RECOVERY_DIR = new File("/cache/recovery");
    public final Context mContext;
    public final DirEncryptionWrapper mDew;
    public HandlerThread mHandlerThread;
    public final DirEncryptServiceHelper mHelper;
    public final Object mSync = new Object();
    public PackageManager mPm = null;
    public DirEncryptServiceHandler mHandler = null;
    public final AnonymousClass1 mListener =
            new IVoldTaskListener.Stub() { // from class: com.android.server.DirEncryptService.1
                @Override // android.os.IVoldTaskListener
                public final void onFinished(int i, PersistableBundle persistableBundle) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i, "onFinished ::", "DirEncryptService");
                }

                @Override // android.os.IVoldTaskListener
                public final void onStatus(final int i, final PersistableBundle persistableBundle) {
                    if (i == 686) {
                        AuditLog.log(
                                1,
                                1,
                                false,
                                Process.myPid(),
                                "DirEncryptService",
                                "Encrypting storage card failed");
                    } else if (i == 683) {
                        String string = persistableBundle.getString("description");
                        int i2 = persistableBundle.getInt(Constants.JSON_CLIENT_DATA_STATUS);
                        if (!"success".equals(string)) {
                            AuditLog.log(
                                    1,
                                    1,
                                    false,
                                    Process.myPid(),
                                    "DirEncryptService",
                                    "Encrypting storage card failed");
                        } else if (i2 == 1) {
                            AuditLog.log(
                                    1,
                                    1,
                                    true,
                                    Process.myPid(),
                                    "DirEncryptService",
                                    "Encrypting storage card succeeded");
                        } else {
                            AuditLog.log(
                                    1,
                                    1,
                                    true,
                                    Process.myPid(),
                                    "DirEncryptService",
                                    "Decrypting storage card succeeded");
                        }
                    }
                    DirEncryptServiceHandler dirEncryptServiceHandler =
                            DirEncryptService.this.mHandler;
                    if (dirEncryptServiceHandler != null) {
                        dirEncryptServiceHandler.post(
                                new Runnable() { // from class:
                                    // com.android.server.DirEncryptService.1.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i3;
                                        DirEncryptServiceHelper dirEncryptServiceHelper =
                                                DirEncryptService.this.mHelper;
                                        int i4 = i;
                                        PersistableBundle persistableBundle2 = persistableBundle;
                                        dirEncryptServiceHelper.getClass();
                                        synchronized (DirEncryptServiceHelper.mSync) {
                                            try {
                                                persistableBundle2.getString("path");
                                                int i5 =
                                                        persistableBundle2.getInt(
                                                                Constants.JSON_CLIENT_DATA_STATUS);
                                                String string2 =
                                                        persistableBundle2.getString("description");
                                                int i6 = persistableBundle2.getInt("arg1");
                                                int i7 = persistableBundle2.getInt("arg2");
                                                Log.i(
                                                        "DirEncryptServiceHelper",
                                                        "onEventInner resp_code: "
                                                                + i4
                                                                + " status : "
                                                                + i5);
                                                switch (i4) {
                                                    case FrameworkStatsLog
                                                            .KEYBOARD_SYSTEMS_EVENT_REPORTED /* 683 */:
                                                    case 691:
                                                        int i8 = 3;
                                                        if (!"success".equals(string2)) {
                                                            if (!"exception".equals(string2)) {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "RESP_ENCRYPT failed");
                                                                if (dirEncryptServiceHelper
                                                                                .mLastError
                                                                        == 0) {
                                                                    if (i5 == 1) {
                                                                        dirEncryptServiceHelper
                                                                                        .mLastError =
                                                                                5;
                                                                    } else {
                                                                        dirEncryptServiceHelper
                                                                                        .mLastError =
                                                                                6;
                                                                    }
                                                                }
                                                                dirEncryptServiceHelper
                                                                        .showNotification(
                                                                                2, i5, "failed");
                                                                dirEncryptServiceHelper.setStatus(
                                                                        0);
                                                                if (i5 != 0) {
                                                                    i8 = 2;
                                                                }
                                                                dirEncryptServiceHelper
                                                                        .notifyEncryptionStatusChanged(
                                                                                i8, 0, 0, "free");
                                                                break;
                                                            } else {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "RESP_ENCRYPT : encrypted"
                                                                            + " on other device");
                                                                dirEncryptServiceHelper.mLastError =
                                                                        8;
                                                                dirEncryptServiceHelper.setStatus(
                                                                        0);
                                                                dirEncryptServiceHelper
                                                                        .showNotification(
                                                                                2, i5, "exception");
                                                                if (i5 != 0) {
                                                                    i8 = 2;
                                                                }
                                                                dirEncryptServiceHelper
                                                                        .notifyEncryptionStatusChanged(
                                                                                i8, 0, 0, "Mount");
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "ENCRYPT Response admin : "
                                                                                + dirEncryptServiceHelper
                                                                                        .isAdminApplied());
                                                                if (i5 != 0) {
                                                                    Log.i(
                                                                            "DirEncryptServiceHelper",
                                                                            "Encrypt complete");
                                                                    dirEncryptServiceHelper
                                                                                    .mEncryptTimestamp =
                                                                            DirEncryptServiceHelper
                                                                                    .getCurrentTime();
                                                                    dirEncryptServiceHelper
                                                                                    .mDecryptTimestamp =
                                                                            "";
                                                                    break;
                                                                } else {
                                                                    Log.i(
                                                                            "DirEncryptServiceHelper",
                                                                            "Decrypt compelte");
                                                                    dirEncryptServiceHelper
                                                                                    .mEncryptTimestamp =
                                                                            "";
                                                                    dirEncryptServiceHelper
                                                                                    .mDecryptTimestamp =
                                                                            DirEncryptServiceHelper
                                                                                    .getCurrentTime();
                                                                    break;
                                                                }
                                                            }
                                                        } else {
                                                            Log.i(
                                                                    "DirEncryptServiceHelper",
                                                                    "RESP_ENCRYPT success");
                                                            dirEncryptServiceHelper.mLastError = 0;
                                                            dirEncryptServiceHelper.setStatus(0);
                                                            dirEncryptServiceHelper
                                                                    .showNotification(
                                                                            2, i5, "success");
                                                            if (i5 != 0) {
                                                                i8 = 2;
                                                            }
                                                            dirEncryptServiceHelper
                                                                    .notifyEncryptionStatusChanged(
                                                                            i8, 0, 0, "Mount");
                                                            Log.i(
                                                                    "DirEncryptServiceHelper",
                                                                    "ENCRYPT Response admin : "
                                                                            + dirEncryptServiceHelper
                                                                                    .isAdminApplied());
                                                            if (i5 != 0) {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "Encrypt complete");
                                                                dirEncryptServiceHelper
                                                                                .mEncryptTimestamp =
                                                                        DirEncryptServiceHelper
                                                                                .getCurrentTime();
                                                                dirEncryptServiceHelper
                                                                                .mDecryptTimestamp =
                                                                        "";
                                                                break;
                                                            } else {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "Decrypt compelte");
                                                                dirEncryptServiceHelper
                                                                                .mEncryptTimestamp =
                                                                        "";
                                                                dirEncryptServiceHelper
                                                                                .mDecryptTimestamp =
                                                                        DirEncryptServiceHelper
                                                                                .getCurrentTime();
                                                                break;
                                                            }
                                                        }
                                                    case FrameworkStatsLog
                                                            .MEDIA_CODEC_RENDERED /* 684 */:
                                                        Log.i(
                                                                "DirEncryptServiceHelper",
                                                                "DirEncryptServiceHelperVoldResponse.PROGRESS"
                                                                    + " percentage("
                                                                        + i6
                                                                        + "), storage("
                                                                        + i7
                                                                        + ")");
                                                        if (i5 == 1) {
                                                            dirEncryptServiceHelper.setStatus(2);
                                                            i3 = 3;
                                                        } else {
                                                            i3 = 3;
                                                            dirEncryptServiceHelper.setStatus(3);
                                                        }
                                                        if (i6
                                                                        - dirEncryptServiceHelper
                                                                                .mPrevPercent
                                                                >= 1) {
                                                            dirEncryptServiceHelper
                                                                    .showNotification(
                                                                            5, i5, "" + i6);
                                                            dirEncryptServiceHelper.mPrevPercent =
                                                                    i6;
                                                        }
                                                        if (i6 == 100) {
                                                            dirEncryptServiceHelper.mPrevPercent =
                                                                    -1;
                                                        }
                                                        dirEncryptServiceHelper
                                                                .notifyEncryptionStatusChanged(
                                                                        i5 == 0 ? i3 : 2,
                                                                        i6,
                                                                        i7,
                                                                        "busy");
                                                        break;
                                                    case FrameworkStatsLog
                                                            .INPUTDEVICE_USAGE_REPORTED /* 686 */:
                                                        dirEncryptServiceHelper
                                                                        .mErrAdditionalSpace =
                                                                i6;
                                                        dirEncryptServiceHelper.mLastError = 4;
                                                        dirEncryptServiceHelper.showNotification(
                                                                6, i5, "" + i6);
                                                        dirEncryptServiceHelper.setStatus(0);
                                                        dirEncryptServiceHelper
                                                                .notifyEncryptionStatusChanged(
                                                                        i5 == 0 ? 3 : 2,
                                                                        0,
                                                                        0,
                                                                        "free");
                                                        break;
                                                    case 687:
                                                        dirEncryptServiceHelper.mLastError = 7;
                                                        dirEncryptServiceHelper.setStatus(0);
                                                        dirEncryptServiceHelper
                                                                .notifyEncryptionStatusChanged(
                                                                        i5 == 0 ? 3 : 2,
                                                                        0,
                                                                        0,
                                                                        "free");
                                                        break;
                                                    case 692:
                                                        dirEncryptServiceHelper.mLastError = 0;
                                                        dirEncryptServiceHelper
                                                                        .mErrAdditionalSpace =
                                                                0;
                                                        dirEncryptServiceHelper.mAlreadyDecrypted =
                                                                false;
                                                        String volumeState =
                                                                dirEncryptServiceHelper.mDew
                                                                        .getVolumeState();
                                                        if (volumeState != null) {
                                                            if (i5 == 1) {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "ENC_META_CHECK :"
                                                                            + " Encryption State"
                                                                            + " Normal");
                                                                dirEncryptServiceHelper
                                                                                .mSelfSDMountRequested =
                                                                        true;
                                                                dirEncryptServiceHelper
                                                                        .doWorkForUSBState(
                                                                                volumeState);
                                                            } else if (dirEncryptServiceHelper
                                                                            .isAdminApplied()
                                                                    == 1) {
                                                                Log.d(
                                                                        "DirEncryptServiceHelper",
                                                                        "ENC_META_CHECK : EAS"
                                                                            + " Policy Set"
                                                                                + string2);
                                                                dirEncryptServiceHelper
                                                                        .startCryptSDCardSettingsActivity();
                                                            } else {
                                                                Log.i(
                                                                        "DirEncryptServiceHelper",
                                                                        "ENC_META_CHECK : Normal SD"
                                                                            + " Card");
                                                                dirEncryptServiceHelper
                                                                                .mSelfSDMountRequested =
                                                                        false;
                                                                dirEncryptServiceHelper
                                                                                .mAlreadyDecrypted =
                                                                        true;
                                                            }
                                                            DirEncryptServiceHelper
                                                                            .mMountSDcardToHelper =
                                                                    false;
                                                            break;
                                                        } else {
                                                            Log.i(
                                                                    "DirEncryptServiceHelper",
                                                                    "there is no SD card");
                                                            break;
                                                        }
                                                    case 693:
                                                        dirEncryptServiceHelper.mLastError = 11;
                                                        dirEncryptServiceHelper.showNotification(
                                                                11, i5, "");
                                                        dirEncryptServiceHelper.setStatus(0);
                                                        dirEncryptServiceHelper
                                                                .notifyEncryptionStatusChanged(
                                                                        i5 == 0 ? 3 : 2,
                                                                        0,
                                                                        0,
                                                                        "free");
                                                        break;
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                });
                    } else {
                        Log.i("DirEncryptService", "onStatus mHandler == null");
                    }
                }
            };
    public final AnonymousClass2 mBroadcastReceiver =
            new BroadcastReceiver() { // from class: com.android.server.DirEncryptService.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.i("DirEncryptService", "DirEncryptService received action: " + action);
                    if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                        Log.i(
                                "DirEncryptService",
                                "DirEncryptService received ACTION_BOOT_COMPLETED");
                        DirEncryptService.this.getClass();
                        DirEncryptService.moveDumpstate();
                        DirEncryptService.this.mHelper.mBootCompleted = true;
                    }
                }
            };

    public final class DirEncryptServiceHandler extends Handler {
        public DirEncryptServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            DirEncryptServiceHelper dirEncryptServiceHelper = DirEncryptService.this.mHelper;
            dirEncryptServiceHelper.getClass();
            synchronized (DirEncryptServiceHelper.mSync) {
                try {
                    int i = message.what;
                    if (i == 2) {
                        dirEncryptServiceHelper.mSelfSDMountRequested = true;
                        try {
                            dirEncryptServiceHelper.mDew.unmountVolume();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (i == 3) {
                        String str = (String) message.obj;
                        Log.i("DirEncryptServiceHelper", "Command.USB_STATE:: " + str);
                        dirEncryptServiceHelper.doWorkForUSBState(str);
                    } else if (i != 10) {
                        Log.e("DirEncryptServiceHelper", "Invalid command!!!");
                    } else {
                        dirEncryptServiceHelper.mDew.mountVolume();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public DirEncryptService(Context context) {
        this.mHelper = null;
        this.mDew = null;
        Log.i("DirEncryptService", "ctor DirEncryptService.....");
        this.mContext = context;
        this.mHelper = new DirEncryptServiceHelper(context);
        this.mDew = new DirEncryptionWrapper(context);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean moveDumpstate() {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.DirEncryptService.moveDumpstate():boolean");
    }

    public final boolean authByUid(int i) {
        return this.mPm.checkSignatures(Process.myUid(), i) == 0;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                dumpInternal(printWriter);
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        printWriter.println(
                "Permission Denial: can't dump DirEncryptService from from pid="
                        + Binder.getCallingPid()
                        + ", uid="
                        + Binder.getCallingUid()
                        + " without permission android.permission.DUMP");
    }

    public final void dumpInternal(PrintWriter printWriter) {
        try {
            JSONObject jSONObject = new JSONObject();
            DirEncryptServiceHelper dirEncryptServiceHelper = this.mHelper;
            if (dirEncryptServiceHelper != null) {
                jSONObject.put(
                        Constants.JSON_CLIENT_DATA_STATUS, dirEncryptServiceHelper.mServiceStatus);
                jSONObject.put("isSdCardEncryped", this.mHelper.isSdCardEncryped());
                jSONObject.put("lasterror", this.mHelper.mLastError);
                jSONObject.put("spacerequired", this.mHelper.mErrAdditionalSpace);
                jSONObject.put("isAdminApplied", this.mHelper.isAdminApplied());
                jSONObject.put("EncryptTimestamp", this.mHelper.mEncryptTimestamp);
                jSONObject.put("DecryptTimestamp", this.mHelper.mDecryptTimestamp);
            }
            printWriter.println(jSONObject);
        } catch (JSONException e) {
            Log.e("DirEncryptService", "dump formatting failure", e);
        } catch (Exception e2) {
            printWriter.println("dump ex =" + e2.getMessage());
        }
    }

    public final int encryptStorage(String str) {
        Log.i("DirEncryptService", "Check the state of external SD card mount path = " + str);
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        int callingUid = Binder.getCallingUid();
        if (authByUid(callingUid)) {
            if (callingUid == 1000) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                String externalSDvolState =
                        new DirEncryptionWrapper(this.mContext).getExternalSDvolState();
                int i = 13;
                if ("mounted".equals(externalSDvolState)) {
                    Log.i("DirEncryptService", "SD card mounted, need unmount...");
                    this.mHelper.setStatus(1);
                    this.mHandler.obtainMessage(2).sendToTarget();
                } else if ("HiddenMount".equals(externalSDvolState)) {
                    Log.i("DirEncryptService", "SD card ready, need encrypt cycle...");
                    this.mHelper.setStatus(1);
                    this.mHandler.obtainMessage(4).sendToTarget();
                } else {
                    Log.i(
                            "DirEncryptService",
                            "SD card not mounted, so not applying policies this time");
                    i = 202;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return i;
            }
            Log.e("DirEncryptService", "Invalid uid. Only SYSTEM can use DirEncryptService Call.");
        }
        Log.i("DirEncryptService", "Invalid requester");
        return 204;
    }

    public final String findRequester(int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        int myUid = Process.myUid();
        String nameForUid = this.mPm.getNameForUid(i);
        Log.i(
                "DirEncryptService",
                "Package name uidApp = " + this.mPm.getNameForUid(i) + " uid = " + i);
        Log.i(
                "DirEncryptService",
                "Package name myUID  = " + this.mPm.getNameForUid(myUid) + " uid = " + myUid);
        if (nameForUid == null || nameForUid.length() <= 0) {
            Log.i("DirEncryptService", "REQUESTER_APP...");
            return nameForUid;
        }
        if (this.mPm.checkSignatures(myUid, i) != 0) {
            Log.e("DirEncryptService", "REQUESTER_UNKNOWN...");
            return "";
        }
        if (myUid != i) {
            Log.i("DirEncryptService", "REQUESTER_APP...");
            return nameForUid;
        }
        int callingPid = Binder.getCallingPid();
        ActivityManagerService activityManagerService =
                (ActivityManagerService) ServiceManager.getService("activity");
        String str = null;
        if (activityManagerService != null
                && (runningAppProcesses = activityManagerService.getRunningAppProcesses())
                        != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                    runningAppProcesses) {
                if (runningAppProcessInfo.pid == callingPid) {
                    str = runningAppProcessInfo.processName;
                }
            }
        }
        Log.i("DirEncryptService", "calling pid = " + callingPid);
        Log.i("DirEncryptService", "calling pid package = " + str);
        if (str == null) {
            Log.e("DirEncryptService", "Calling package name is null...");
            return "";
        }
        if (str.equals(KnoxCustomManagerService.SETTING_PKG_NAME)) {
            Log.i("DirEncryptService", "REQUESTER_USER...");
            return "user";
        }
        Log.i("DirEncryptService", "REQUESTER_DM...");
        return "dm";
    }

    public final int getAdditionalSpaceRequired() {
        return this.mHelper.mErrAdditionalSpace;
    }

    public final int getCurrentStatus() {
        return this.mHelper.mServiceStatus;
    }

    public final int getLastError() {
        return this.mHelper.mLastError;
    }

    public final IVoldTaskListener getListener() {
        return this.mListener;
    }

    public final boolean isSdCardEncryped() {
        return this.mHelper.isSdCardEncryped();
    }

    public final int isStorageCardEncryptionPoliciesApplied() {
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.i("DirEncryptService", "isStorageCardEncryptionPoliciesApplied.....");
        int isAdminApplied = this.mHelper.isAdminApplied();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isAdminApplied;
    }

    public final void registerListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mHelper.mListeners.register(iDirEncryptServiceListener);
    }

    public final void setMountSDcardToHelper(boolean z) {
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (authByUid(callingUid)) {
                this.mHelper.getClass();
                DirEncryptServiceHelper.mMountSDcardToHelper = z;
            } else {
                Log.e("DirEncryptService", "setMountSDcardToHelper error: invalid uid");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setNeedToCreateKey(boolean z) {
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (authByUid(callingUid)) {
                if (callingUid == 1000) {
                    this.mHelper.getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
                Log.e(
                        "DirEncryptService",
                        "Invalid uid. Only SYSTEM can use DirEncryptService Call.");
            }
            Log.e("DirEncryptService", "setNeedToCreateKey error: invalid uid");
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setPassword(String str) {
        boolean z;
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String nameForUid = this.mPm.getNameForUid(callingUid);
        if (callingUid != 1000) {
            Log.e("DirEncryptService", "Invalid uid. Only SYSTEM can use DirEncryptService Call.");
            z = false;
        } else {
            z = true;
        }
        boolean z2 = z || nameForUid.matches(".*android.uid.systemui.*");
        Log.i("DirEncryptService", "setPassword.....");
        if (authByUid(callingUid) && z2) {
            this.mHandler.obtainMessage(1, str).sendToTarget();
        } else {
            Log.e("DirEncryptService", "setPassword error: invalid uid");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 13;
    }

    public final int setSdCardEncryptionPolicy(int i, int i2, String str) {
        int i3 = 200;
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        StringBuilder m =
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "setSdCardEncryptionPolicy: [", "][", str, "][");
        m.append(i2);
        m.append("]");
        Log.i("DirEncryptService", m.toString());
        synchronized (this.mSync) {
            try {
                String findRequester = findRequester(Binder.getCallingUid());
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (str == null && (str = this.mDew.getExternalSDvolFsUuid()) == null) {
                    str = "FFFF-FFFF";
                }
                Log.i("DirEncryptService", "setSdCardEncryptionPolicy : ".concat(str));
                if (this.mHelper.mServiceStatus != 0) {
                    Log.i(
                            "DirEncryptService",
                            "Cannot save the policy if SD card is being encrypted/decrypted");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 15;
                }
                Log.i("DirEncryptService", "Called by: " + findRequester);
                if (authByUid(callingUid)) {
                    if (!"user".equals(findRequester) && i2 != 0 && i2 != 1 && i != 1) {
                        if (i != 0) {
                            Log.i(
                                    "DirEncryptService",
                                    "Do not need to disable SD card encryption policy by EAS/MDM"
                                        + " requests");
                            moveDumpstate();
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return i3;
                        }
                    }
                    i3 = 8;
                    moveDumpstate();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return i3;
                }
                Log.e("DirEncryptService", "setSdCardEncryptionPolicy error: invalid uid");
                i3 = 9;
                moveDumpstate();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return i3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int setStorageCardEncryptionPolicy(int i, int i2, int i3) {
        int i4 = 200;
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            if (i == 2) {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Encrypting storage card failed");
            } else {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Decrypting storage card failed");
            }
            return 200;
        }
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "setStorageCardEncryption: [", "][", "][");
        m.append(i3);
        m.append("]");
        Log.i("DirEncryptService", m.toString());
        if (i != 2 && i != 3) {
            AuditLog.log(
                    1,
                    1,
                    false,
                    Process.myPid(),
                    "DirEncryptService",
                    "Decrypting storage card failed");
            return 200;
        }
        if (i2 != 4 && i2 != 5) {
            if (i == 2) {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Encrypting storage card failed");
            } else {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Decrypting storage card failed");
            }
            return 200;
        }
        if (i2 == 4 && i != 2) {
            AuditLog.log(
                    1,
                    1,
                    false,
                    Process.myPid(),
                    "DirEncryptService",
                    "Decrypting storage card failed");
            return 200;
        }
        if (i3 != 6 && i3 != 7) {
            if (i == 2) {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Encrypting storage card failed");
            } else {
                AuditLog.log(
                        1,
                        1,
                        false,
                        Process.myPid(),
                        "DirEncryptService",
                        "Decrypting storage card failed");
            }
            return 200;
        }
        synchronized (this.mSync) {
            try {
                String findRequester = findRequester(Binder.getCallingUid());
                int callingUid = Binder.getCallingUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                boolean semGetRequireStorageCardEncryption =
                        ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                                .semGetRequireStorageCardEncryption(null);
                Log.i(
                        "DirEncryptService",
                        "setStorageCardEncryptionPolicy : " + this.mDew.getExternalSDvolFsUuid());
                if (this.mHelper.mServiceStatus != 0) {
                    Log.i(
                            "DirEncryptService",
                            "Cannot save the policy if SD card is being encrypted/decrypted");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 15;
                }
                Log.i("DirEncryptService", "Called by: " + findRequester);
                if (authByUid(callingUid)) {
                    if (!"user".equals(findRequester) && !semGetRequireStorageCardEncryption) {
                        Log.i(
                                "DirEncryptService",
                                "Do not need to disable SD card encryption policy by EAS/MDM"
                                    + " requests");
                    }
                    i4 = 8;
                } else {
                    Log.e("DirEncryptService", "setStorageCardEncryptionPolicy error: invalid uid");
                    i4 = 9;
                }
                moveDumpstate();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return i4;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void systemReady() {
        Log.i("DirEncryptService", "Calling systemReady");
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            this.mPm = this.mContext.getPackageManager();
            this.mContext.registerReceiver(
                    this.mBroadcastReceiver,
                    new IntentFilter("android.intent.action.BOOT_COMPLETED"),
                    null,
                    null);
            try {
                HandlerThread handlerThread = new HandlerThread("DirEncryptService");
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                DirEncryptServiceHandler dirEncryptServiceHandler =
                        new DirEncryptServiceHandler(this.mHandlerThread.getLooper());
                this.mHandler = dirEncryptServiceHandler;
                this.mHelper.mHandler = dirEncryptServiceHandler;
            } catch (Exception e) {
                Log.e("DirEncryptService", "HandlerThread exception = " + e.toString());
            }
            DirEncryptServiceHelper dirEncryptServiceHelper = this.mHelper;
            if (dirEncryptServiceHelper.mDew.registerStorageEventListener(
                    dirEncryptServiceHelper.mStorageEventListener)) {
                Log.i("DirEncryptServiceHelper", "RegisterStorageEventListner success");
            } else {
                Log.i("DirEncryptServiceHelper", "RegisterStorageEventListner fail");
            }
        }
    }

    public final void unmountSDCardByAdmin() {
        DirEncryptServiceHelper dirEncryptServiceHelper = this.mHelper;
        String volumeState = dirEncryptServiceHelper.mDew.getVolumeState();
        if (volumeState != null) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "SD card  : State = ", volumeState, " / Admin policy : ");
            m.append(dirEncryptServiceHelper.isAdminApplied());
            m.append(" / Encrypted : ");
            m.append(dirEncryptServiceHelper.isSdCardEncryped());
            Log.i("DirEncryptServiceHelper", m.toString());
            if (dirEncryptServiceHelper.isAdminApplied() == 1
                    && !dirEncryptServiceHelper.isSdCardEncryped()
                    && "mounted".equals(volumeState)) {
                Log.i("DirEncryptServiceHelper", "Unmount SD Card By Admin");
                dirEncryptServiceHelper.mUnnmountRequested = true;
                try {
                    dirEncryptServiceHelper.mDew.unmountVolume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mHelper.mListeners.unregister(iDirEncryptServiceListener);
    }
}
