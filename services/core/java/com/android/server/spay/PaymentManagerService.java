package com.android.server.spay;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.spay.IPaymentManager;
import android.spay.PaymentTZServiceCommnInfo;
import android.spay.PaymentTZServiceConfig;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PaymentManagerService extends IPaymentManager.Stub {
    public static Context mContext;
    public final Map mRegisteredFWKClientMap = new HashMap();
    public static final boolean DEBUG = !SystemProperties.getBoolean("ro.product_ship", true);
    public static final String COUNTRYISO_CODE = SystemProperties.get("ro.csc.countryiso_code");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FrameworkClient {
        public final ClientBinderDeathReceiver mBinderDeathReceiver;
        public final PaymentTZServiceCommnInfo mCommnInfo;
        public final String mPackageName;
        public final int mPid;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ClientBinderDeathReceiver implements IBinder.DeathRecipient {
            public IBinder mReceiver;

            public ClientBinderDeathReceiver() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                Log.e("PaymentManagerService", "Error: Framework App dead, unloading loaded TAs");
                deleteClient();
            }

            public final synchronized void deleteClient() {
                Log.e("PaymentManagerService", "Error: Client stopped. Clearing Databstructures for " + FrameworkClient.this.mPackageName);
                for (Integer num : FrameworkClient.this.mCommnInfo.mTAs.keySet()) {
                    TAController tAController = (TAController) FrameworkClient.this.mCommnInfo.mTAs.get(num);
                    try {
                        if (num.intValue() == 257 && tAController.SET_QSEE_SECURE_UI) {
                            Utils.sendSecureUIAbortIntent(PaymentManagerService.mContext);
                            Log.d("PaymentManagerService", "sendSecureUIAbortIntent: true");
                            int i = 0;
                            while (true) {
                                if (i >= 10) {
                                    break;
                                }
                                if (!tAController.SET_QSEE_SECURE_UI) {
                                    Log.i("PaymentManagerService", "secureUI unsetted");
                                    break;
                                } else {
                                    try {
                                        Thread.sleep(100L);
                                    } catch (Exception unused) {
                                        Log.e("PaymentManagerService", "Failed to put thread to sleep!");
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
                ((HashMap) PaymentManagerService.this.mRegisteredFWKClientMap).remove(frameworkClient.mPackageName);
            }
        }

        public FrameworkClient(PaymentTZServiceConfig paymentTZServiceConfig, PaymentTZServiceCommnInfo paymentTZServiceCommnInfo, int i, String str) {
            this.mCommnInfo = paymentTZServiceCommnInfo;
            this.mPid = i;
            this.mPackageName = str;
            ClientBinderDeathReceiver clientBinderDeathReceiver = new ClientBinderDeathReceiver();
            this.mBinderDeathReceiver = clientBinderDeathReceiver;
            IBinder iBinder = paymentTZServiceConfig.mClient;
            clientBinderDeathReceiver.mReceiver = iBinder;
            try {
                iBinder.linkToDeath(clientBinderDeathReceiver, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public PaymentManagerService(Context context) {
        if (DEBUG) {
            Log.d("PaymentManagerService", "PaymentManagerService() called");
        }
        mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.spayfw", 0);
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.spay", 0);
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.spaymini", 0);
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.samsungpay.gear", 0);
        intentFilter.addDataSchemeSpecificPart("com.samsung.android.rajaampat", 0);
        intentFilter.setPriority(1000);
        mContext.registerReceiver(new UpdateReceiver(), intentFilter);
        PackageManager packageManager = context.getPackageManager();
        try {
            if (packageManager.getPackageInfo("com.samsung.android.app.stubupdater", 1) != null && packageManager.getPackageInfo("com.samsung.android.spay", 1) != null) {
                int applicationEnabledSetting = packageManager.getApplicationEnabledSetting("com.samsung.android.spay");
                int applicationEnabledSetting2 = packageManager.getApplicationEnabledSetting("com.samsung.android.app.stubupdater");
                Log.d("PaymentManagerService", "spay state = " + applicationEnabledSetting + ", updater state = " + applicationEnabledSetting2);
                if (applicationEnabledSetting2 != 2 && applicationEnabledSetting != 2) {
                    packageManager.setApplicationEnabledSetting("com.samsung.android.spay", 2, 0);
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("PaymentManagerService", "updater /spay app is not installed");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Utils.backgroundWhitelist(context, "com.samsung.android.spay", true);
        Utils.backgroundWhitelist(context, "com.samsung.android.spayfw", true);
        Utils.backgroundWhitelist(context, "com.samsung.android.spaymini", true);
        Utils.backgroundWhitelist(context, "com.samsung.android.samsungpay.gear", true);
        Utils.backgroundWhitelist(context, "com.samsung.android.rajaampat", true);
    }

    public static void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(Binder.getCallingPid(), Binder.getCallingUid(), mContext, "PaymentManagerService", str) == 0) {
            Log.d("PaymentManagerService", "PaymentManagerService() - Valid Caller");
            return;
        }
        SecurityException securityException = new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [PaymentManagerService] service");
        Log.d("PaymentManagerService", "PaymentManagerService() - Invalid Caller");
        throw securityException;
    }

    public final byte[] getMeasurementFile() {
        checkCallerPermissionFor("getMeasurementFile");
        return Utils.readFile("/system/tima_measurement_info");
    }

    public final PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig) {
        String str;
        Iterator it;
        checkCallerPermissionFor("registerSPayFW");
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        String str2 = "PaymentManagerService";
        if (activityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == callingPid) {
                    str = runningAppProcessInfo.processName;
                    break;
                }
            }
        } else {
            Log.e("PaymentManagerService", "Error: am.getRunningAppProcesses() is null");
        }
        str = null;
        if (str == null) {
            Log.e("PaymentManagerService", "Error: can't find processname for PID");
            str = Integer.toString(callingUid);
        }
        String str3 = str;
        VpnManagerService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "Inside registerSPayFW, uid: ", ", pid: ", ", package: "), str3, "PaymentManagerService");
        if (((HashMap) this.mRegisteredFWKClientMap).containsKey(str3)) {
            FrameworkClient frameworkClient = (FrameworkClient) ((HashMap) this.mRegisteredFWKClientMap).get(str3);
            if (callingPid == frameworkClient.mPid) {
                Log.e("PaymentManagerService", "Error: Framework App is already registered. Re-Registration not allowed");
                return null;
            }
            Log.e("PaymentManagerService", "Registered Client Died. Need to Rebind");
            frameworkClient.mBinderDeathReceiver.deleteClient();
        }
        PaymentTZServiceCommnInfo paymentTZServiceCommnInfo = new PaymentTZServiceCommnInfo();
        paymentTZServiceCommnInfo.mServiceVersion = 9;
        Iterator it2 = paymentTZServiceConfig.mTAConfigs.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            Context context = mContext;
            int intValue = ((Integer) entry.getKey()).intValue();
            PaymentTZServiceConfig.TAConfig tAConfig = (PaymentTZServiceConfig.TAConfig) entry.getValue();
            TAController tAController = new TAController();
            tAController.SET_QSEE_SECURE_UI = false;
            if (TAController.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intValue, "TAController constructor: taId = ", "; maxSendCmdSize = ");
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
            PaymentTZNative paymentTZNative = new PaymentTZNative();
            if (PaymentTZNative.DEBUG) {
                it = it2;
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intValue, "PaymentTZNative constructor: taId = ", str2);
            } else {
                it = it2;
            }
            paymentTZNative.mTAId = intValue;
            paymentTZNative.mMOPTZNativePtr_ = 0L;
            paymentTZNative.mSendBufSize = i;
            paymentTZNative.mRecvBufSize = i2;
            paymentTZNative.mTATechnology = str4;
            paymentTZNative.mRootName = str5;
            paymentTZNative.mProcessName = str6;
            paymentTZNative.mIsLoaded = false;
            tAController.mNative = paymentTZNative;
            paymentTZServiceCommnInfo.mTAs.put((Integer) entry.getKey(), tAController);
            str2 = str2;
            it2 = it;
        }
        String str7 = str2;
        if (!"KR".equalsIgnoreCase(COUNTRYISO_CODE)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            new EnterpriseDeviceManager(mContext).getApplicationPolicy().setApplicationUninstallationDisabled("com.samsung.android.spayfw");
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        ((HashMap) this.mRegisteredFWKClientMap).put(str3, new FrameworkClient(paymentTZServiceConfig, paymentTZServiceCommnInfo, callingPid, str3));
        Log.d(str7, "callingApp: " + str3 + " is registed, current size: " + ((HashMap) this.mRegisteredFWKClientMap).size());
        return paymentTZServiceCommnInfo;
    }
}
