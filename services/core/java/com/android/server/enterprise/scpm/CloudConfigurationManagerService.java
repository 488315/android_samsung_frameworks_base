package com.android.server.enterprise.scpm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import com.android.server.LocalServices;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.localservice.CloudConfigurationManagerInternal;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class CloudConfigurationManagerService extends CloudConfigurationManagerInternal {
  public final Context mContext;
  public EdmStorageProvider mEdmStorageProvider;
  public final Injector mInjector;
  public Bundle mScpmBundle;
  public String mScpmToken = null;
  public int mRetryNumber = 3;

  public class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public EdmStorageProvider getStorageProvider() {
      return new EdmStorageProvider(this.mContext);
    }
  }

  public CloudConfigurationManagerService(Context context) {
    this.mEdmStorageProvider = null;
    Injector injector = new Injector(context);
    this.mInjector = injector;
    Context context2 = injector.mContext;
    this.mContext = context2;
    this.mEdmStorageProvider = injector.getStorageProvider();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
    intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
    intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_MAM_CONFIGURATION_BROADCAST);
    intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.knox-remotecontrol");
    context2.registerReceiver(
        new BroadcastReceiver() { // from class:
                                  // com.android.server.enterprise.scpm.CloudConfigurationManagerService.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context3, Intent intent) {
            if (intent.getAction().equals("com.samsung.intent.action.LAZY_BOOT_COMPLETE")) {
              CloudConfigurationManagerService.this.tryRegister();
              return;
            }
            if (intent.getAction().equals(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA)) {
              KnoxsdkFileLog.d(
                  "CloudConfigurationManagerService",
                  "receive ACTION_SCPM_POLICY_CLEAR_DATA intent");
              new Handler()
                  .postDelayed(
                      new Runnable() { // from class:
                                       // com.android.server.enterprise.scpm.CloudConfigurationManagerService.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                          CloudConfigurationManagerService.this.mScpmToken = null;
                          CloudConfigurationManagerService.this.tryRegister();
                        }
                      },
                      60000L);
            } else if (intent
                .getAction()
                .equals(KnoxCustomManagerService.ACTION_SCPM_MAM_CONFIGURATION_BROADCAST)) {
              KnoxsdkFileLog.d(
                  "CloudConfigurationManagerService", "receive intent: knox-sdk-mam-configuration");
              CloudConfigurationManagerService.this.updateConfiguration(
                  KnoxCustomManagerService.SCPM_MAM_CONFIGURATION, "KNOX_CUSTOM", "mamPackageName");
            } else if (intent
                .getAction()
                .equals("com.samsung.android.scpm.policy.UPDATE.knox-remotecontrol")) {
              KnoxsdkFileLog.d(
                  "CloudConfigurationManagerService", "receive intent: knox-remotecontrol");
              CloudConfigurationManagerService.this.updateConfiguration(
                  "knox-remotecontrol", "RESTRICTION", "allowRemoteControlAllowList");
            }
          }
        },
        intentFilter);
  }

  public static void addService(Context context) {
    LocalServices.addService(
        CloudConfigurationManagerInternal.class, new CloudConfigurationManagerService(context));
  }

  public final void tryRegister() {
    try {
      if (registerScpm()) {
        Log.d("CloudConfigurationManagerService", "tryRegister - register success");
        return;
      }
    } catch (Throwable th) {
      Log.e("CloudConfigurationManagerService", "Failed to tryRegister " + th.getMessage());
    }
    Log.d(
        "CloudConfigurationManagerService",
        "tryRegister - register fail, retryNumber is " + this.mRetryNumber);
    int i = this.mRetryNumber;
    this.mRetryNumber = i + (-1);
    if (i > 0) {
      new Handler()
          .postDelayed(
              new Runnable() { // from class:
                               // com.android.server.enterprise.scpm.CloudConfigurationManagerService.2
                @Override // java.lang.Runnable
                public void run() {
                  CloudConfigurationManagerService.this.tryRegister();
                }
              },
              30000L);
    }
  }

  public final boolean registerScpm() {
    if (this.mScpmToken != null) {
      Log.d("CloudConfigurationManagerService", "token is not null");
      return true;
    }
    if (!isScpmV2Available()) {
      KnoxsdkFileLog.d("CloudConfigurationManagerService", "scpm v2 is not available");
      return false;
    }
    try {
      Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/");
      Bundle bundle = new Bundle();
      bundle.putString("packageName", "android");
      bundle.putString("appId", KnoxCustomManagerService.SPCM_KNOX_CUSTOM_APP_ID);
      bundle.putString("version", KnoxCustomManagerService.SPCM_APP_VERSION);
      bundle.putString("receiverPackageName", "android");
      Bundle call =
          this.mContext
              .getContentResolver()
              .call(parse, "register", this.mContext.getPackageName(), bundle);
      this.mScpmBundle = call;
      if (call != null) {
        boolean z = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1) == 1;
        this.mScpmToken = this.mScpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
        StringBuilder sb = new StringBuilder();
        sb.append("trying to register package: android version:37 status: ");
        sb.append(z ? "registered" : "failed");
        KnoxsdkFileLog.d("CloudConfigurationManagerService", sb.toString());
        if (!z) {
          Pair resultCode = getResultCode(this.mScpmBundle);
          KnoxsdkFileLog.d(
              "CloudConfigurationManagerService",
              "register fail rCode:" + resultCode.first + "," + ((String) resultCode.second));
        }
      }
    } catch (Exception e) {
      KnoxsdkFileLog.e(
          "CloudConfigurationManagerService", "cannot register package : " + e.getMessage());
    }
    return this.mScpmToken != null;
  }

  public final boolean isScpmV2Available() {
    return this.mContext
            .getPackageManager()
            .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
        != null;
  }

  public final Pair getResultCode(Bundle bundle) {
    int i = bundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
    return Pair.create(
        Integer.valueOf(i), bundle.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, ""));
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r10v1 */
  /* JADX WARN: Type inference failed for: r10v7, types: [android.os.ParcelFileDescriptor] */
  public final void updateConfiguration(String str, String str2, String str3) {
    StringBuilder sb = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      try {
        str =
            this.mContext
                .getContentResolver()
                .openFileDescriptor(
                    Uri.parse(
                        "content://com.samsung.android.scpm.policy/" + this.mScpmToken + "/" + str),
                    "r");
        try {
          if (str == 0) {
            new Bundle();
            Pair resultCode =
                getResultCode(
                    this.mContext
                        .getContentResolver()
                        .call(
                            Uri.parse("content://com.samsung.android.scpm.policy/"),
                            "getLastError",
                            "android",
                            (Bundle) null));
            KnoxsdkFileLog.e(
                "CloudConfigurationManagerService",
                "It can't get the configuration data : "
                    + resultCode.first
                    + ","
                    + ((String) resultCode.second));
            if (str != 0) {
              try {
                str.close();
                return;
              } catch (Exception unused) {
                Log.e("CloudConfigurationManagerService", "pfd isn't closed");
                return;
              }
            }
            return;
          }
          BufferedReader bufferedReader2 =
              new BufferedReader(
                  new InputStreamReader(new FileInputStream(str.getFileDescriptor()), "UTF-8"));
          while (true) {
            try {
              String readLine = bufferedReader2.readLine();
              if (readLine == null) {
                break;
              } else {
                sb.append(readLine);
              }
            } catch (Exception e) {
              e = e;
              bufferedReader = bufferedReader2;
              Log.e("CloudConfigurationManagerService", "Unknown exception : " + e.getMessage());
              if (bufferedReader != null) {
                try {
                  bufferedReader.close();
                } catch (Exception unused2) {
                  Log.e("CloudConfigurationManagerService", "br isn't closed");
                }
              }
              if (str != 0) {
                try {
                  str.close();
                  return;
                } catch (Exception unused3) {
                  Log.e("CloudConfigurationManagerService", "pfd isn't closed");
                  return;
                }
              }
              return;
            } catch (Throwable th) {
              th = th;
              bufferedReader = bufferedReader2;
              if (bufferedReader != null) {
                try {
                  bufferedReader.close();
                } catch (Exception unused4) {
                  Log.e("CloudConfigurationManagerService", "br isn't closed");
                }
              }
              if (str == 0) {
                throw th;
              }
              try {
                str.close();
                throw th;
              } catch (Exception unused5) {
                Log.e("CloudConfigurationManagerService", "pfd isn't closed");
                throw th;
              }
            }
          }
          KnoxsdkFileLog.d(
              "CloudConfigurationManagerService", "updateConfiguration: " + sb.toString());
          this.mEdmStorageProvider.putString(1000, str2, str3, sb.toString());
          try {
            bufferedReader2.close();
          } catch (Exception unused6) {
            Log.e("CloudConfigurationManagerService", "br isn't closed");
          }
          try {
            str.close();
          } catch (Exception unused7) {
            Log.e("CloudConfigurationManagerService", "pfd isn't closed");
          }
        } catch (Exception e2) {
          e = e2;
        }
      } catch (Throwable th2) {
        th = th2;
      }
    } catch (Exception e3) {
      e = e3;
      str = 0;
    } catch (Throwable th3) {
      th = th3;
      str = 0;
    }
  }
}
