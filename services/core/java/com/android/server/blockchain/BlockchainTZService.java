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
import com.android.server.ServiceKeeper;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class BlockchainTZService extends IBlockchainManager.Stub {
    public static final boolean DEBUG = !SystemProperties.getBoolean("ro.product_ship", true);
    public static Context mContext;
    public Map mRegisteredFWKClientMap;

    public native byte[] nativeGetCredential(int i);

    public native boolean nativePutCredential(int i, byte[] bArr);

    public native int nativeSspExit();

    public native int nativeSspInit();

    public static int checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(mContext, Binder.getCallingPid(), Binder.getCallingUid(), "BlockchainTZService", str) != 0) {
            SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [BlockchainTZService] service");
            Log.d("BlockchainTZService", "BlockchainTZService() - Invalid Caller");
            throw securityException;
        }
        Log.d("BlockchainTZService", "BlockchainTZService() - Valid Caller");
        return 0;
    }

    public class FrameworkClient {
        public ClientBinderDeathReceiver mBinderDeathReceiver;
        public BlockchainTZServiceCommnInfo mCommnInfo;
        public final String mPackageName;
        public final int mPid;
        public final int mUid;

        public FrameworkClient(BlockchainTZServiceConfig blockchainTZServiceConfig, BlockchainTZServiceCommnInfo blockchainTZServiceCommnInfo, int i, int i2, String str) {
            this.mBinderDeathReceiver = null;
            this.mCommnInfo = blockchainTZServiceCommnInfo;
            this.mUid = i;
            this.mPid = i2;
            this.mPackageName = str;
            ClientBinderDeathReceiver clientBinderDeathReceiver = new ClientBinderDeathReceiver();
            this.mBinderDeathReceiver = clientBinderDeathReceiver;
            clientBinderDeathReceiver.setReceiver(blockchainTZServiceConfig.mClient);
            try {
                blockchainTZServiceConfig.mClient.linkToDeath(this.mBinderDeathReceiver, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public class ClientBinderDeathReceiver implements IBinder.DeathRecipient {
            public IBinder mReceiver;

            public void setReceiver(IBinder iBinder) {
                this.mReceiver = iBinder;
            }

            public ClientBinderDeathReceiver() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                Log.e("BlockchainTZService", "Error: Framework App dead, unloading loaded TAs");
                deleteClient();
            }

            public final synchronized void deleteClient() {
                Log.e("BlockchainTZService", "Error: Client stopped. Clearing Databstructures for " + FrameworkClient.this.mPackageName);
                for (Integer num : FrameworkClient.this.mCommnInfo.mTAs.keySet()) {
                    TAController tAController = (TAController) FrameworkClient.this.mCommnInfo.mTAs.get(num);
                    try {
                        if (num.intValue() == 257 && tAController.SET_QSEE_SECURE_UI) {
                            Log.d("BlockchainTZService", "sendSecureUIAbortIntent: " + Utils.sendSecureUIAbortIntent(BlockchainTZService.mContext));
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
                                        Log.e("BlockchainTZService", "Failed to put thread to sleep!");
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
                BlockchainTZService.this.mRegisteredFWKClientMap.remove(FrameworkClient.this.mPackageName);
            }
        }
    }

    public BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig blockchainTZServiceConfig) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        String packageNameFromPid = getPackageNameFromPid(callingUid, callingPid);
        Log.d("BlockchainTZService", "Inside registerBlockchainFW, uid: " + callingUid + ", pid: " + callingPid + ", package: " + packageNameFromPid);
        if (this.mRegisteredFWKClientMap.containsKey(packageNameFromPid)) {
            FrameworkClient frameworkClient = (FrameworkClient) this.mRegisteredFWKClientMap.get(packageNameFromPid);
            if (callingPid != frameworkClient.mPid) {
                Log.e("BlockchainTZService", "Registered Client Died. Need to Rebind");
                frameworkClient.mBinderDeathReceiver.deleteClient();
            } else {
                Log.e("BlockchainTZService", "Error: Framework App is already registered. Re-Registration not allowed");
                return null;
            }
        }
        checkCallerPermissionFor("registerBlockchainFW");
        BlockchainTZServiceCommnInfo blockchainTZServiceCommnInfo = new BlockchainTZServiceCommnInfo();
        blockchainTZServiceCommnInfo.mServiceVersion = 1;
        for (Map.Entry entry : blockchainTZServiceConfig.mTAConfigs.entrySet()) {
            blockchainTZServiceCommnInfo.mTAs.put((Integer) entry.getKey(), new TAController(mContext, ((Integer) entry.getKey()).intValue(), (BlockchainTZServiceConfig.TAConfig) entry.getValue()));
        }
        this.mRegisteredFWKClientMap.put(packageNameFromPid, new FrameworkClient(blockchainTZServiceConfig, blockchainTZServiceCommnInfo, callingUid, callingPid, packageNameFromPid));
        Log.d("BlockchainTZService", "callingApp: " + packageNameFromPid + " is registered, current size: " + this.mRegisteredFWKClientMap.size());
        return blockchainTZServiceCommnInfo;
    }

    public byte[] getMeasurementFile() {
        checkCallerPermissionFor("getMeasurementFile");
        return Utils.readFile("/system/tima_measurement_info");
    }

    public byte[] getCredential(int i) {
        if (DEBUG) {
            Log.d("BlockchainTZService", "getCredential");
        }
        checkCallerPermissionFor("getCredential");
        return nativeGetCredential(i);
    }

    public boolean putCredential(int i, byte[] bArr) {
        if (DEBUG) {
            Log.d("BlockchainTZService", "putCredential");
        }
        checkCallerPermissionFor("putCredential");
        return nativePutCredential(i, bArr);
    }

    public int sspInit() {
        if (DEBUG) {
            Log.d("BlockchainTZService", "sspInit");
        }
        checkCallerPermissionFor("sspInit");
        return nativeSspInit();
    }

    public int sspExit() {
        if (DEBUG) {
            Log.d("BlockchainTZService", "sspExit");
        }
        checkCallerPermissionFor("sspExit");
        return nativeSspExit();
    }

    public final String getPackageNameFromPid(int i, int i2) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        String str = null;
        if (activityManager.getRunningAppProcesses() != null) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = activityManager.getRunningAppProcesses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (next.pid == i2) {
                    str = next.processName;
                    break;
                }
            }
        } else {
            Log.e("BlockchainTZService", "Error: am.getRunningAppProcesses() is null");
        }
        if (str != null) {
            return str;
        }
        Log.e("BlockchainTZService", "Error: can't find processname for PID");
        return Integer.toString(i);
    }
}
