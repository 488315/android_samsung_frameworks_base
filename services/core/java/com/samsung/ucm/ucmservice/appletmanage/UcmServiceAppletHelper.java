package com.samsung.ucm.ucmservice.appletmanage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;

import com.samsung.ucm.ucmservice.EFSProperties;
import com.samsung.ucm.ucmservice.UcmServiceODE;
import com.samsung.ucm.ucmservice.UcmServiceUtil;
import com.samsung.ucm.ucmservice.security.UcmSecurityHelper;
import com.skms.android.agent.CcmInterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public final class UcmServiceAppletHelper {
    public static final boolean DBG = UcmServiceUtil.isDebug();
    public final Context mContext;
    public int mLccmRetryCount;
    public final IPackageManager mPm;
    public final UcmSecurityHelper mSecurityHelper;
    public boolean mIsLccmScriptRunning = false;
    public final List mConfigAppletRequestIds = new ArrayList();
    public final AnonymousClass2 mOnLccmConnection =
            new ServiceConnection() { // from class:
                                      // com.samsung.ucm.ucmservice.appletmanage.UcmServiceAppletHelper.2
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    byte[] bArr;
                    int i;
                    if (!new File("/efs/sec_efs", "ucm_delete_applet_lccmscript").exists()) {
                        Log.e(
                                "UcmServiceAppletHelper",
                                "onServiceConnected, but file doesn't exist");
                        return;
                    }
                    EFSProperties.log("getAppletDeletionLccmScript");
                    IInterface iInterface = null;
                    if (new File("/efs/sec_efs", "ucm_delete_applet_lccmscript").exists()) {
                        int length =
                                (int)
                                        new File("/efs/sec_efs", "ucm_delete_applet_lccmscript")
                                                .length();
                        bArr = new byte[length];
                        try {
                            BufferedInputStream bufferedInputStream =
                                    new BufferedInputStream(
                                            new FileInputStream(
                                                    new File(
                                                            "/efs/sec_efs",
                                                            "ucm_delete_applet_lccmscript")));
                            try {
                                EFSProperties.log(
                                        "getByteArray read: "
                                                + bufferedInputStream.read(bArr, 0, length));
                                bufferedInputStream.close();
                            } finally {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        bArr = null;
                    }
                    if (bArr == null) {
                        Log.e("UcmServiceAppletHelper", "onServiceConnected, but script is null");
                        return;
                    }
                    try {
                        int i2 = CcmInterface.Stub.$r8$clinit;
                        if (iBinder != null) {
                            IInterface queryLocalInterface =
                                    iBinder.queryLocalInterface(
                                            "com.skms.android.agent.CcmInterface");
                            if (queryLocalInterface == null
                                    || !(queryLocalInterface instanceof CcmInterface)) {
                                CcmInterface.Stub.Proxy proxy = new CcmInterface.Stub.Proxy();
                                proxy.mRemote = iBinder;
                                iInterface = proxy;
                            } else {
                                iInterface = (CcmInterface) queryLocalInterface;
                            }
                        }
                        if (iInterface == null) {
                            Log.e(
                                    "UcmServiceAppletHelper",
                                    "onServiceConnected, but CcmInterface is null");
                            return;
                        }
                        try {
                            i = ((CcmInterface.Stub.Proxy) iInterface).handleCcm(bArr.length, bArr);
                        } catch (RemoteException e2) {
                            Log.e(
                                    "UcmServiceAppletHelper",
                                    "handleCcm: Exception " + e2.getMessage());
                            e2.printStackTrace();
                            i = -1;
                        }
                        UcmServiceAppletHelper.this.mContext.unbindService(this);
                        if (i != 0) {
                            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                                    i, "handleCcmRet. error [", "]", "UcmServiceAppletHelper");
                            UcmServiceAppletHelper.this.retryRunLccmAfterSleep();
                        } else {
                            Log.i("UcmServiceAppletHelper", "handleCcmRet: clearAppletInfo");
                            EFSProperties.clearAppletInfo();
                            UcmServiceAppletHelper.this.mIsLccmScriptRunning = false;
                            Log.i("UcmServiceAppletHelper", "Running Lccm Script Completed");
                        }
                    } finally {
                        UcmServiceAppletHelper.this.mContext.unbindService(this);
                        Log.e("UcmServiceAppletHelper", "handleCcmRet. error [-1]");
                        UcmServiceAppletHelper.this.retryRunLccmAfterSleep();
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {}
            };
    public final AnonymousClass1 mHandler =
            new Handler(
                    KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                    "UcmServiceAppletHelperThread")
                            .getLooper()) { // from class:
                                            // com.samsung.ucm.ucmservice.appletmanage.UcmServiceAppletHelper.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        Log.e("UcmServiceAppletHelper", "handleMessage. wrong msg");
                    } else {
                        UcmServiceAppletHelper.this.runLccmScript();
                    }
                }
            };

    public UcmServiceAppletHelper(
            Context context, IPackageManager iPackageManager, UcmSecurityHelper ucmSecurityHelper) {
        this.mContext = context;
        this.mPm = iPackageManager;
        this.mSecurityHelper = ucmSecurityHelper;
    }

    public static String getSignatureHash(PackageInfo packageInfo) {
        if (packageInfo == null) {
            Log.i("UcmServiceAppletHelper", "getSignatureHash pluginPkgInfo is null");
            return "";
        }
        try {
            Log.i("UcmServiceAppletHelper", "getSignatureHash start: " + packageInfo.packageName);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(packageInfo.signingInfo.getApkContentsSigners()[0].toByteArray());
            return Base64.encodeToString(messageDigest.digest(), 0);
        } catch (Exception e) {
            Log.e("UcmServiceAppletHelper", "getSignatureHash exception: ", e);
            return "";
        }
    }

    public final synchronized void checkToRunLccmScript() {
        try {
            if (!this.mSecurityHelper.isSystemCaller()) {
                this.mSecurityHelper.checkCallerPermissionFor("runLccmScript");
            }
            if (!this.mIsLccmScriptRunning
                    && !isAppletPluginExist()
                    && UcmServiceODE.getOdeStatus() <= 0
                    && new File("/efs/sec_efs", "ucm_delete_applet_lccmscript").exists()) {
                this.mLccmRetryCount = 0;
                runLccmScript();
                return;
            }
            Log.i("UcmServiceAppletHelper", "checkToRunLccmScript. skip");
        } catch (Throwable th) {
            throw th;
        }
    }

    public final PackageInfo getPackageInfo(int i, String str) {
        try {
            return this.mPm.getPackageInfo(str, 134217728L, i);
        } catch (RemoteException unused) {
            Log.e("UcmServiceAppletHelper", "application is not installed.");
            return null;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAppletPluginExist() {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.appletmanage.UcmServiceAppletHelper.isAppletPluginExist():boolean");
    }

    public final void retryRunLccmAfterSleep() {
        int i = this.mLccmRetryCount + 1;
        this.mLccmRetryCount = i;
        if (i >= 30) {
            Log.i("UcmServiceAppletHelper", "retryRunLccmAfterSleep. stop retry");
            return;
        }
        long j = i < 3 ? 1000L : i < 10 ? 5000L : 10000L;
        StringBuilder sb = new StringBuilder("retryRunLccmAfterSleep. retry [");
        sb.append(this.mLccmRetryCount);
        sb.append("] after [");
        sb.append(j);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "] ms", "UcmServiceAppletHelper");
        sendEmptyMessageDelayed(1, j);
    }

    public final void runLccmScript() {
        this.mIsLccmScriptRunning = true;
        boolean z = false;
        try {
            z =
                    this.mContext.bindService(
                            new Intent("com.skms.android.agent.CcmService")
                                    .setPackage("com.skms.android.agent"),
                            this.mOnLccmConnection,
                            1);
            Log.i("UcmServiceAppletHelper", "bindCcmService() isConnected : " + z);
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("bindCcmService() exception "), "UcmServiceAppletHelper");
        }
        if (z) {
            return;
        }
        retryRunLccmAfterSleep();
    }
}
