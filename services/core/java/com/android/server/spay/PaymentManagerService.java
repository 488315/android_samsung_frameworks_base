package com.android.server.spay;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.spay.IPaymentManager;
import android.spay.PaymentTZServiceCommnInfo;
import android.spay.PaymentTZServiceConfig;
import android.util.Log;
import com.android.server.ServiceKeeper;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class PaymentManagerService extends IPaymentManager.Stub {
  public static Context mContext;
  public Map mRegisteredFWKClientMap;
  public static final boolean DEBUG = !SystemProperties.getBoolean("ro.product_ship", true);
  public static final String COUNTRYISO_CODE = SystemProperties.get("ro.csc.countryiso_code");

  public static int checkCallerPermissionFor(String str) {
    if (ServiceKeeper.isAuthorized(
            mContext, Binder.getCallingPid(), Binder.getCallingUid(), "PaymentManagerService", str)
        != 0) {
      SecurityException securityException =
          new SecurityException(
              "Security Exception Occurred while pid["
                  + Binder.getCallingPid()
                  + "] with uid["
                  + Binder.getCallingUid()
                  + "] trying to access methodName ["
                  + str
                  + "] in [PaymentManagerService] service");
      Log.d("PaymentManagerService", "PaymentManagerService() - Invalid Caller");
      throw securityException;
    }
    Log.d("PaymentManagerService", "PaymentManagerService() - Valid Caller");
    return 0;
  }

  public class FrameworkClient {
    public ClientBinderDeathReceiver mBinderDeathReceiver;
    public PaymentTZServiceCommnInfo mCommnInfo;
    public final String mPackageName;
    public final int mPid;
    public final int mUid;

    public FrameworkClient(
        PaymentTZServiceConfig paymentTZServiceConfig,
        PaymentTZServiceCommnInfo paymentTZServiceCommnInfo,
        int i,
        int i2,
        String str) {
      this.mBinderDeathReceiver = null;
      this.mCommnInfo = paymentTZServiceCommnInfo;
      this.mUid = i;
      this.mPid = i2;
      this.mPackageName = str;
      ClientBinderDeathReceiver clientBinderDeathReceiver = new ClientBinderDeathReceiver();
      this.mBinderDeathReceiver = clientBinderDeathReceiver;
      clientBinderDeathReceiver.setReceiver(paymentTZServiceConfig.mClient);
      try {
        paymentTZServiceConfig.mClient.linkToDeath(this.mBinderDeathReceiver, 0);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    public class ClientBinderDeathReceiver implements IBinder.DeathRecipient {
      public IBinder mReceiver;

      public void setReceiver(IBinder iBinder) {
        this.mReceiver = iBinder;
      }

      public ClientBinderDeathReceiver() {}

      @Override // android.os.IBinder.DeathRecipient
      public void binderDied() {
        Log.e("PaymentManagerService", "Error: Framework App dead, unloading loaded TAs");
        deleteClient();
      }

      public final synchronized void deleteClient() {
        Log.e(
            "PaymentManagerService",
            "Error: Client stopped. Clearing Databstructures for "
                + FrameworkClient.this.mPackageName);
        for (Integer num : FrameworkClient.this.mCommnInfo.mTAs.keySet()) {
          TAController tAController = (TAController) FrameworkClient.this.mCommnInfo.mTAs.get(num);
          try {
            if (num.intValue() == 257 && tAController.SET_QSEE_SECURE_UI) {
              Log.d(
                  "PaymentManagerService",
                  "sendSecureUIAbortIntent: "
                      + Utils.sendSecureUIAbortIntent(PaymentManagerService.mContext));
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
        PaymentManagerService.this.mRegisteredFWKClientMap.remove(
            FrameworkClient.this.mPackageName);
      }
    }
  }

  public PaymentTZServiceCommnInfo registerSPayFW(PaymentTZServiceConfig paymentTZServiceConfig) {
    checkCallerPermissionFor("registerSPayFW");
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    String packageNameFromPid = getPackageNameFromPid(callingUid, callingPid);
    Log.d(
        "PaymentManagerService",
        "Inside registerSPayFW, uid: "
            + callingUid
            + ", pid: "
            + callingPid
            + ", package: "
            + packageNameFromPid);
    if (this.mRegisteredFWKClientMap.containsKey(packageNameFromPid)) {
      FrameworkClient frameworkClient =
          (FrameworkClient) this.mRegisteredFWKClientMap.get(packageNameFromPid);
      if (callingPid != frameworkClient.mPid) {
        Log.e("PaymentManagerService", "Registered Client Died. Need to Rebind");
        frameworkClient.mBinderDeathReceiver.deleteClient();
      } else {
        Log.e(
            "PaymentManagerService",
            "Error: Framework App is already registered. Re-Registration not allowed");
        return null;
      }
    }
    PaymentTZServiceCommnInfo paymentTZServiceCommnInfo = new PaymentTZServiceCommnInfo();
    paymentTZServiceCommnInfo.mServiceVersion = 9;
    for (Map.Entry entry : paymentTZServiceConfig.mTAConfigs.entrySet()) {
      paymentTZServiceCommnInfo.mTAs.put(
          (Integer) entry.getKey(),
          new TAController(
              mContext,
              ((Integer) entry.getKey()).intValue(),
              (PaymentTZServiceConfig.TAConfig) entry.getValue()));
    }
    disablePaymentFrameworkUpdateRemoval();
    this.mRegisteredFWKClientMap.put(
        packageNameFromPid,
        new FrameworkClient(
            paymentTZServiceConfig,
            paymentTZServiceCommnInfo,
            callingUid,
            callingPid,
            packageNameFromPid));
    Log.d(
        "PaymentManagerService",
        "callingApp: "
            + packageNameFromPid
            + " is registed, current size: "
            + this.mRegisteredFWKClientMap.size());
    return paymentTZServiceCommnInfo;
  }

  public byte[] getMeasurementFile() {
    checkCallerPermissionFor("getMeasurementFile");
    return Utils.readFile("/system/tima_measurement_info");
  }

  public final void disablePaymentFrameworkUpdateRemoval() {
    if ("KR".equalsIgnoreCase(COUNTRYISO_CODE)) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    new EnterpriseDeviceManager(mContext)
        .getApplicationPolicy()
        .setApplicationUninstallationDisabled("com.samsung.android.spayfw");
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final String getPackageNameFromPid(int i, int i2) {
    ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
    String str = null;
    if (activityManager.getRunningAppProcesses() != null) {
      Iterator<ActivityManager.RunningAppProcessInfo> it =
          activityManager.getRunningAppProcesses().iterator();
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
      Log.e("PaymentManagerService", "Error: am.getRunningAppProcesses() is null");
    }
    if (str != null) {
      return str;
    }
    Log.e("PaymentManagerService", "Error: can't find processname for PID");
    return Integer.toString(i);
  }
}
