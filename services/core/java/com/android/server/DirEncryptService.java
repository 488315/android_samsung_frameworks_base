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
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.p005os.IVoldTaskListener;
import android.sec.enterprise.auditlog.AuditLog;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.security.DirEncryptionWrapper;
import com.samsung.android.security.IDirEncryptService;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class DirEncryptService extends IDirEncryptService.Stub {
    public static final File RECOVERY_DIR = new File("/cache/recovery");
    public Context mContext;
    public DirEncryptionWrapper mDew;
    public HandlerThread mHandlerThread;
    public DirEncryptServiceHelper mHelper;
    public boolean mReady = false;
    public Object mSync = new Object();
    public PackageManager mPm = null;
    public DirEncryptServiceHandler mHandler = null;
    public final int RESPONSE_BASE = 680;
    public final int ENCRYPT = 683;
    public final int PRESCAN_FULL_ERR = 686;
    public final IVoldTaskListener mListener = new IVoldTaskListener.Stub() { // from class: com.android.server.DirEncryptService.1
        @Override // android.p005os.IVoldTaskListener
        public void onStatus(final int i, final PersistableBundle persistableBundle) {
            if (i == 686) {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Encrypting storage card failed");
            } else if (i == 683) {
                String string = persistableBundle.getString("description");
                int i2 = persistableBundle.getInt("status");
                if (!"success".equals(string)) {
                    AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Encrypting storage card failed");
                } else if (i2 == 1) {
                    AuditLog.log(1, 1, true, Process.myPid(), "DirEncryptService", "Encrypting storage card succeeded");
                } else {
                    AuditLog.log(1, 1, true, Process.myPid(), "DirEncryptService", "Decrypting storage card succeeded");
                }
            }
            if (DirEncryptService.this.mHandler != null) {
                DirEncryptService.this.mHandler.post(new Runnable() { // from class: com.android.server.DirEncryptService.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DirEncryptService.this.mHelper.onEventInner(i, persistableBundle);
                    }
                });
            } else {
                Log.i("DirEncryptService", "onStatus mHandler == null");
            }
        }

        @Override // android.p005os.IVoldTaskListener
        public void onFinished(int i, PersistableBundle persistableBundle) {
            Log.i("DirEncryptService", "onFinished ::" + i);
        }
    };
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.DirEncryptService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("DirEncryptService", "DirEncryptService received action: " + action);
            if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                Log.i("DirEncryptService", "DirEncryptService received ACTION_BOOT_COMPLETED");
                DirEncryptService.this.moveDumpstate();
                DirEncryptService.this.mHelper.setBootComplted(true);
            }
        }
    };

    public class DirEncryptServiceHandler extends Handler {
        public DirEncryptServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DirEncryptService.this.mHelper.doHandleMessage(message);
        }
    }

    public IVoldTaskListener getListener() {
        return this.mListener;
    }

    public DirEncryptService(Context context) {
        this.mHelper = null;
        this.mDew = null;
        Log.i("DirEncryptService", "ctor DirEncryptService.....");
        this.mContext = context;
        this.mHelper = new DirEncryptServiceHelper(this.mContext);
        this.mDew = new DirEncryptionWrapper(this.mContext);
    }

    public void systemReady() {
        Log.i("DirEncryptService", "Calling systemReady");
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            this.mPm = this.mContext.getPackageManager();
            this.mContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"), null, null);
            try {
                HandlerThread handlerThread = new HandlerThread("DirEncryptService");
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                DirEncryptServiceHandler dirEncryptServiceHandler = new DirEncryptServiceHandler(this.mHandlerThread.getLooper());
                this.mHandler = dirEncryptServiceHandler;
                this.mHelper.setExecParams(dirEncryptServiceHandler);
            } catch (Exception e) {
                Log.e("DirEncryptService", "HandlerThread exception = " + e.toString());
                this.mReady = false;
            }
            this.mHelper.registerStorageEventListener();
        }
    }

    public final boolean moveDumpstate() {
        if ("trigger_restart_min_framework".equals(SystemProperties.get("vold.decrypt"))) {
            return true;
        }
        File file = RECOVERY_DIR;
        if (!file.exists()) {
            Log.e("DirEncryptService", "moveDumpstate - RECOVERY_DIR was not exist!!");
            return false;
        }
        String[] list = file.list();
        if (list == null || list.length <= 0) {
            Log.e("DirEncryptService", "moveDumpstate - fileList is null!!");
            return false;
        }
        for (int i = 0; i < list.length; i++) {
            String str = list[i];
            if (str != null && str.length() > 0 && list[i].startsWith("last_ode_dumpstate")) {
                File file2 = new File(RECOVERY_DIR, list[i]);
                copyFile(file2, new File("/data/log/" + list[i]));
                try {
                    file2.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public final void copyFile(File file, File file2) {
        FileChannel fileChannel;
        StringBuilder sb;
        FileChannel channel;
        FileChannel fileChannel2 = null;
        try {
            try {
                channel = new FileInputStream(file).getChannel();
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileChannel2 = new FileOutputStream(file2).getChannel();
                fileChannel2.transferFrom(channel, 0L, channel.size());
                Os.chmod(file2.getPath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED);
                try {
                    channel.close();
                    fileChannel2.close();
                } catch (IOException e) {
                    e = e;
                    sb = new StringBuilder();
                    sb.append("Error close FileChannel : ");
                    sb.append(e.getMessage());
                    Log.e("DirEncryptService", sb.toString());
                    Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
                }
            } catch (ErrnoException e2) {
                e = e2;
                fileChannel = fileChannel2;
                fileChannel2 = channel;
                Log.e("DirEncryptService", "Error chmod ode logs : " + e.getMessage());
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e3) {
                        e = e3;
                        sb = new StringBuilder();
                        sb.append("Error close FileChannel : ");
                        sb.append(e.getMessage());
                        Log.e("DirEncryptService", sb.toString());
                        Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
            } catch (IOException e4) {
                e = e4;
                fileChannel = fileChannel2;
                fileChannel2 = channel;
                Log.e("DirEncryptService", "Error copy ode logs : " + e.getMessage());
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e5) {
                        e = e5;
                        sb = new StringBuilder();
                        sb.append("Error close FileChannel : ");
                        sb.append(e.getMessage());
                        Log.e("DirEncryptService", sb.toString());
                        Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
            } catch (Throwable th2) {
                th = th2;
                fileChannel = fileChannel2;
                fileChannel2 = channel;
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e6) {
                        Log.e("DirEncryptService", "Error close FileChannel : " + e6.getMessage());
                        throw th;
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                throw th;
            }
        } catch (ErrnoException e7) {
            e = e7;
            fileChannel = null;
        } catch (IOException e8) {
            e = e8;
            fileChannel = null;
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
        }
        Log.i("DirEncryptService", "copyFile : " + file + " -> " + file2);
    }

    public final String getCallingProcessPkg(int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManagerService activityManagerService = (ActivityManagerService) ServiceManager.getService("activity");
        String str = null;
        if (activityManagerService != null && (runningAppProcesses = activityManagerService.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == i) {
                    str = runningAppProcessInfo.processName;
                }
            }
        }
        return str;
    }

    public final boolean checkSystemUid(int i) {
        if (i == 1000) {
            return true;
        }
        Log.e("DirEncryptService", "Invalid uid. Only SYSTEM can use DirEncryptService Call.");
        return false;
    }

    public final boolean authByUid(int i) {
        return this.mPm.checkSignatures(Process.myUid(), i) == 0;
    }

    public final String findRequester(int i) {
        int myUid = Process.myUid();
        String nameForUid = this.mPm.getNameForUid(i);
        Log.i("DirEncryptService", "Package name uidApp = " + this.mPm.getNameForUid(i) + " uid = " + i);
        Log.i("DirEncryptService", "Package name myUID  = " + this.mPm.getNameForUid(myUid) + " uid = " + myUid);
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
        String callingProcessPkg = getCallingProcessPkg(callingPid);
        Log.i("DirEncryptService", "calling pid = " + callingPid);
        Log.i("DirEncryptService", "calling pid package = " + callingProcessPkg);
        if (callingProcessPkg == null) {
            Log.e("DirEncryptService", "Calling package name is null...");
            return "";
        }
        if (callingProcessPkg.equals("com.android.settings")) {
            Log.i("DirEncryptService", "REQUESTER_USER...");
            return "user";
        }
        Log.i("DirEncryptService", "REQUESTER_DM...");
        return "dm";
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump DirEncryptService from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            dumpInternal(printWriter);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dumpInternal(PrintWriter printWriter) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (this.mHelper != null) {
                jSONObject.put("status", getCurrentStatus());
                jSONObject.put("isSdCardEncryped", isSdCardEncryped());
                jSONObject.put("lasterror", getLastError());
                jSONObject.put("spacerequired", getAdditionalSpaceRequired());
                jSONObject.put("isAdminApplied", this.mHelper.isStorageCardEncryptionPoliciesApplied());
                jSONObject.put("EncryptTimestamp", this.mHelper.getEncryptTimestamp());
                jSONObject.put("DecryptTimestamp", this.mHelper.getDecryptTimestamp());
            }
            printWriter.println(jSONObject);
        } catch (JSONException e) {
            Log.e("DirEncryptService", "dump formatting failure", e);
        } catch (Exception e2) {
            printWriter.println("dump ex =" + e2.getMessage());
        }
    }

    public void registerListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mHelper.registerListener(iDirEncryptServiceListener);
    }

    public void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mHelper.unregisterListener(iDirEncryptServiceListener);
    }

    public int setStorageCardEncryptionPolicy(int i, int i2, int i3) {
        int i4 = 200;
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            if (i == 2) {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Encrypting storage card failed");
            } else {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Decrypting storage card failed");
            }
            return 200;
        }
        Log.i("DirEncryptService", "setStorageCardEncryption: [" + i + "][" + i2 + "][" + i3 + "]");
        if (i != 2 && i != 3) {
            AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Decrypting storage card failed");
            return 200;
        }
        if (i2 != 4 && i2 != 5) {
            if (i == 2) {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Encrypting storage card failed");
            } else {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Decrypting storage card failed");
            }
            return 200;
        }
        if (i2 == 4 && i != 2) {
            AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Decrypting storage card failed");
            return 200;
        }
        if (i3 != 6 && i3 != 7) {
            if (i == 2) {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Encrypting storage card failed");
            } else {
                AuditLog.log(1, 1, false, Process.myPid(), "DirEncryptService", "Decrypting storage card failed");
            }
            return 200;
        }
        synchronized (this.mSync) {
            String findRequester = findRequester(Binder.getCallingUid());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean z = ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).semGetRequireStorageCardEncryption(null);
            Log.i("DirEncryptService", "setStorageCardEncryptionPolicy : " + this.mDew.getExternalSDvolFsUuid());
            if (this.mHelper.getCurrentStatus() != 0) {
                Log.i("DirEncryptService", "Cannot save the policy if SD card is being encrypted/decrypted");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 15;
            }
            Log.i("DirEncryptService", "Called by: " + findRequester);
            if (authByUid(callingUid)) {
                if (!"user".equals(findRequester) && !z) {
                    Log.i("DirEncryptService", "Do not need to disable SD card encryption policy by EAS/MDM requests");
                }
                i4 = 8;
            } else {
                Log.e("DirEncryptService", "setStorageCardEncryptionPolicy error: invalid uid");
                i4 = 9;
            }
            moveDumpstate();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i4;
        }
    }

    public int setSdCardEncryptionPolicy(int i, int i2, String str) {
        int i3 = 200;
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        Log.i("DirEncryptService", "setSdCardEncryptionPolicy: [" + i + "][" + str + "][" + i2 + "]");
        synchronized (this.mSync) {
            String findRequester = findRequester(Binder.getCallingUid());
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (str == null && (str = this.mDew.getExternalSDvolFsUuid()) == null) {
                str = "FFFF-FFFF";
            }
            Log.i("DirEncryptService", "setSdCardEncryptionPolicy : " + str);
            if (this.mHelper.getCurrentStatus() != 0) {
                Log.i("DirEncryptService", "Cannot save the policy if SD card is being encrypted/decrypted");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 15;
            }
            Log.i("DirEncryptService", "Called by: " + findRequester);
            if (authByUid(callingUid)) {
                if (!"user".equals(findRequester) && i2 != 0 && i2 != 1 && i != 1) {
                    if (i != 0) {
                        Log.i("DirEncryptService", "Do not need to disable SD card encryption policy by EAS/MDM requests");
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
        }
    }

    public int isStorageCardEncryptionPoliciesApplied() {
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Log.i("DirEncryptService", "isStorageCardEncryptionPoliciesApplied.....");
        int isStorageCardEncryptionPoliciesApplied = this.mHelper.isStorageCardEncryptionPoliciesApplied();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isStorageCardEncryptionPoliciesApplied;
    }

    public int setPassword(String str) {
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = checkSystemUid(callingUid) || this.mPm.getNameForUid(callingUid).matches(".*android.uid.systemui.*");
        Log.i("DirEncryptService", "setPassword.....");
        if (authByUid(callingUid) && z) {
            this.mHandler.obtainMessage(1, str).sendToTarget();
        } else {
            Log.e("DirEncryptService", "setPassword error: invalid uid");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 13;
    }

    public int getCurrentStatus() {
        return this.mHelper.getCurrentStatus();
    }

    public int getLastError() {
        return this.mHelper.getLastError();
    }

    public int getAdditionalSpaceRequired() {
        return this.mHelper.getAdditionalSpaceRequired();
    }

    public void unmountSDCardByAdmin() {
        this.mHelper.unmountSDCardByAdmin();
    }

    public int encryptStorage(String str) {
        Log.i("DirEncryptService", "Check the state of external SD card mount path = " + str);
        if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            return 200;
        }
        int callingUid = Binder.getCallingUid();
        if (!authByUid(callingUid) || !checkSystemUid(callingUid)) {
            Log.i("DirEncryptService", "Invalid requester");
            return 204;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String externalSDvolState = new DirEncryptionWrapper(this.mContext).getExternalSDvolState();
        int i = 13;
        if ("mounted".equals(externalSDvolState)) {
            Log.i("DirEncryptService", "SD card mounted, need unmount...");
            this.mHelper.ready();
            this.mHandler.obtainMessage(2).sendToTarget();
        } else if ("HiddenMount".equals(externalSDvolState)) {
            Log.i("DirEncryptService", "SD card ready, need encrypt cycle...");
            this.mHelper.ready();
            this.mHandler.obtainMessage(4).sendToTarget();
        } else {
            Log.i("DirEncryptService", "SD card not mounted, so not applying policies this time");
            i = 202;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return i;
    }

    public boolean isSdCardEncryped() {
        return this.mHelper.isSdCardEncryped();
    }

    public void setNeedToCreateKey(boolean z) {
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (authByUid(callingUid) && checkSystemUid(callingUid)) {
                this.mHelper.setNeedToCreateKey(z);
            } else {
                Log.e("DirEncryptService", "setNeedToCreateKey error: invalid uid");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setMountSDcardToHelper(boolean z) {
        if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (authByUid(callingUid)) {
                this.mHelper.setMountSDcardToHelper(z);
            } else {
                Log.e("DirEncryptService", "setMountSDcardToHelper error: invalid uid");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
