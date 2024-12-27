package com.android.server.blockchain;

import android.app.ActivityManager;
import android.blockchain.BlockchainTZServiceCommnInfo;
import android.blockchain.BlockchainTZServiceConfig;
import android.blockchain.IBlockchainManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class BlockchainTZService extends IBlockchainManager.Stub {
    public static final boolean DEBUG = !SystemProperties.getBoolean("ro.product_ship", true);
    public static Context mContext;
    public Map mRegisteredFWKClientMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FrameworkClient {
        public final ClientBinderDeathReceiver mBinderDeathReceiver;
        public final BlockchainTZServiceCommnInfo mCommnInfo;
        public final String mPackageName;
        public final int mPid;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ClientBinderDeathReceiver implements IBinder.DeathRecipient {
            public IBinder mReceiver;

            public ClientBinderDeathReceiver() {}

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                Log.e("BlockchainTZService", "Error: Framework App dead, unloading loaded TAs");
                deleteClient();
            }

            public final synchronized void deleteClient() {
                Log.e(
                        "BlockchainTZService",
                        "Error: Client stopped. Clearing Databstructures for "
                                + FrameworkClient.this.mPackageName);
                for (Integer num : FrameworkClient.this.mCommnInfo.mTAs.keySet()) {
                    TAController tAController =
                            (TAController) FrameworkClient.this.mCommnInfo.mTAs.get(num);
                    try {
                        if (num.intValue() == 257 && tAController.SET_QSEE_SECURE_UI) {
                            Utils.sendSecureUIAbortIntent(BlockchainTZService.mContext);
                            Log.d("BlockchainTZService", "sendSecureUIAbortIntent: true");
                            int i = 0;
                            while (true) {
                                if (i >= 10) {
                                    break;
                                }
                                if (!tAController.SET_QSEE_SECURE_UI) {
                                    Log.i("BlockchainTZService", "secureUI unsetted");
                                    break;
                                } else {
                                    try {
                                        Thread.sleep(100L);
                                    } catch (Exception unused) {
                                        Log.e(
                                                "BlockchainTZService",
                                                "Failed to put thread to sleep!");
                                    }
                                    i++;
                                }
                            }
                        }
                        tAController.unloadTA();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                FrameworkClient frameworkClient = FrameworkClient.this;
                ((HashMap) BlockchainTZService.this.mRegisteredFWKClientMap)
                        .remove(frameworkClient.mPackageName);
            }
        }

        public FrameworkClient(
                BlockchainTZServiceConfig blockchainTZServiceConfig,
                BlockchainTZServiceCommnInfo blockchainTZServiceCommnInfo,
                int i,
                String str) {
            this.mCommnInfo = blockchainTZServiceCommnInfo;
            this.mPid = i;
            this.mPackageName = str;
            ClientBinderDeathReceiver clientBinderDeathReceiver = new ClientBinderDeathReceiver();
            this.mBinderDeathReceiver = clientBinderDeathReceiver;
            IBinder iBinder = blockchainTZServiceConfig.mClient;
            clientBinderDeathReceiver.mReceiver = iBinder;
            try {
                iBinder.linkToDeath(clientBinderDeathReceiver, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        mContext,
                        "BlockchainTZService",
                        str)
                == 0) {
            Log.d("BlockchainTZService", "BlockchainTZService() - Valid Caller");
            return;
        }
        SecurityException securityException =
                new SecurityException(
                        "Security Exception Occurred while pid["
                                + Binder.getCallingPid()
                                + "] with uid["
                                + Binder.getCallingUid()
                                + "] trying to access methodName ["
                                + str
                                + "] in [BlockchainTZService] service");
        Log.d("BlockchainTZService", "BlockchainTZService() - Invalid Caller");
        throw securityException;
    }

    public final byte[] getCredential(int i) {
        if (DEBUG) {
            Log.d("BlockchainTZService", "getCredential");
        }
        checkCallerPermissionFor("getCredential");
        return nativeGetCredential(i);
    }

    public final byte[] getMeasurementFile() {
        byte[] bArr;
        FileInputStream fileInputStream;
        int length;
        checkCallerPermissionFor("getMeasurementFile");
        File file = new File("/system/tima_measurement_info");
        Log.d(
                "com.android.server.blockchain.Utils",
                "In readFile - Path /system/tima_measurement_info");
        FileInputStream fileInputStream2 = null;
        byte[] bArr2 = null;
        fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    Log.d(
                            "com.android.server.blockchain.Utils",
                            "File Read - Length = " + file.length());
                    length = (int) file.length();
                    bArr = new byte[length];
                } catch (Exception e) {
                    e = e;
                    bArr = null;
                }
                try {
                    if (fileInputStream.read(bArr) != length) {
                        Log.d("com.android.server.blockchain.Utils", "File Read Failed");
                    } else {
                        bArr2 = bArr;
                    }
                    try {
                        fileInputStream.close();
                        return bArr2;
                    } catch (IOException unused) {
                        Log.d("com.android.server.blockchain.Utils", "Error closing InputStream");
                        return bArr2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream2 = fileInputStream;
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException unused2) {
                            Log.d(
                                    "com.android.server.blockchain.Utils",
                                    "Error closing InputStream");
                        }
                    }
                    return bArr;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused3) {
                        Log.d("com.android.server.blockchain.Utils", "Error closing InputStream");
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            bArr = null;
        }
    }

    public native byte[] nativeGetCredential(int i);

    public native boolean nativePutCredential(int i, byte[] bArr);

    public native int nativeSspExit();

    public native int nativeSspInit();

    public final boolean putCredential(int i, byte[] bArr) {
        if (DEBUG) {
            Log.d("BlockchainTZService", "putCredential");
        }
        checkCallerPermissionFor("putCredential");
        return nativePutCredential(i, bArr);
    }

    public final BlockchainTZServiceCommnInfo registerBlockchainFW(
            BlockchainTZServiceConfig blockchainTZServiceConfig) {
        String str;
        Iterator it;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        String str2 = "BlockchainTZService";
        if (activityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                    activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == callingPid) {
                    str = runningAppProcessInfo.processName;
                    break;
                }
            }
        } else {
            Log.e("BlockchainTZService", "Error: am.getRunningAppProcesses() is null");
        }
        str = null;
        if (str == null) {
            Log.e("BlockchainTZService", "Error: can't find processname for PID");
            str = Integer.toString(callingUid);
        }
        String str3 = str;
        VpnManagerService$$ExternalSyntheticOutline0.m(
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        callingUid,
                        callingPid,
                        "Inside registerBlockchainFW, uid: ",
                        ", pid: ",
                        ", package: "),
                str3,
                "BlockchainTZService");
        if (((HashMap) this.mRegisteredFWKClientMap).containsKey(str3)) {
            FrameworkClient frameworkClient =
                    (FrameworkClient) ((HashMap) this.mRegisteredFWKClientMap).get(str3);
            if (callingPid == frameworkClient.mPid) {
                Log.e(
                        "BlockchainTZService",
                        "Error: Framework App is already registered. Re-Registration not allowed");
                return null;
            }
            Log.e("BlockchainTZService", "Registered Client Died. Need to Rebind");
            frameworkClient.mBinderDeathReceiver.deleteClient();
        }
        checkCallerPermissionFor("registerBlockchainFW");
        BlockchainTZServiceCommnInfo blockchainTZServiceCommnInfo =
                new BlockchainTZServiceCommnInfo();
        blockchainTZServiceCommnInfo.mServiceVersion = 1;
        Iterator it2 = blockchainTZServiceConfig.mTAConfigs.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            Context context = mContext;
            int intValue = ((Integer) entry.getKey()).intValue();
            BlockchainTZServiceConfig.TAConfig tAConfig =
                    (BlockchainTZServiceConfig.TAConfig) entry.getValue();
            TAController tAController = new TAController();
            tAController.SET_QSEE_SECURE_UI = false;
            if (TAController.DEBUG) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                intValue,
                                "TAController constructor: taId = ",
                                "; maxSendCmdSize = ");
                m.append(tAConfig.maxSendCmdSize);
                m.append("; maxRecvRespSize = ");
                GestureWakeup$$ExternalSyntheticOutline0.m(m, tAConfig.maxRecvRespSize, str2);
            }
            tAController.mContext = context;
            tAController.mTAId = intValue;
            String str4 = tAConfig.taTechnology;
            String str5 = tAConfig.rootName;
            String str6 = tAConfig.processName;
            int i = tAConfig.maxSendCmdSize;
            int i2 = tAConfig.maxRecvRespSize;
            BlockchainTZNative blockchainTZNative = new BlockchainTZNative();
            if (BlockchainTZNative.DEBUG) {
                it = it2;
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        intValue, "BlockchainTZNative constructor: taId = ", str2);
            } else {
                it = it2;
            }
            blockchainTZNative.mTAId = intValue;
            blockchainTZNative.mMOPTZNativePtr_ = 0L;
            blockchainTZNative.mSendBufSize = i;
            blockchainTZNative.mRecvBufSize = i2;
            blockchainTZNative.mTATechnology = str4;
            blockchainTZNative.mRootName = str5;
            blockchainTZNative.mProcessName = str6;
            blockchainTZNative.mIsLoaded = false;
            tAController.mNative = blockchainTZNative;
            blockchainTZServiceCommnInfo.mTAs.put((Integer) entry.getKey(), tAController);
            str2 = str2;
            it2 = it;
        }
        ((HashMap) this.mRegisteredFWKClientMap)
                .put(
                        str3,
                        new FrameworkClient(
                                blockchainTZServiceConfig,
                                blockchainTZServiceCommnInfo,
                                callingPid,
                                str3));
        Log.d(
                str2,
                "callingApp: "
                        + str3
                        + " is registered, current size: "
                        + ((HashMap) this.mRegisteredFWKClientMap).size());
        return blockchainTZServiceCommnInfo;
    }

    public final int sspExit() {
        if (DEBUG) {
            Log.d("BlockchainTZService", "sspExit");
        }
        checkCallerPermissionFor("sspExit");
        return nativeSspExit();
    }

    public final int sspInit() {
        if (DEBUG) {
            Log.d("BlockchainTZService", "sspInit");
        }
        checkCallerPermissionFor("sspInit");
        return nativeSspInit();
    }
}
