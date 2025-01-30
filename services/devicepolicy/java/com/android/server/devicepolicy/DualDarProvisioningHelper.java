package com.android.server.devicepolicy;

import android.app.admin.FullyManagedDeviceProvisioningParams;
import android.app.admin.ManagedProfileProvisioningParams;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes2.dex */
public class DualDarProvisioningHelper {
  public ServiceConnection connection = new ServiceConnection() { // from class:
        // com.android.server.devicepolicy.DualDarProvisioningHelper.5
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          Log.d("DualDarProvisioningHelper", "Service connected!! : " + componentName.toString());
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
          Log.d(
              "DualDarProvisioningHelper", "Service disconnected!! : " + componentName.toString());
        }
      };
  public final Context mContext;
  public final UserManagerInternal mUserManagerInternal;

  public DualDarProvisioningHelper(Context context, UserManagerInternal userManagerInternal) {
    this.mContext = context;
    this.mUserManagerInternal = userManagerInternal;
  }

  public void onCreateAndProvisioningManagedProfileStartedForDualDar(
      ManagedProfileProvisioningParams managedProfileProvisioningParams) {
    if (isDualDARConfigured(this.mContext, managedProfileProvisioningParams.getAdminExtras())) {
      final Object obj = new Object();
      final boolean[] zArr = {false};
      final boolean[] zArr2 = new boolean[1];
      final int[] iArr = new int[1];
      BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
            // com.android.server.devicepolicy.DualDarProvisioningHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Log.d(
                  "DualDarProvisioningHelper",
                  "DualDAR Managed Profile Started Service onReceived is called: " + action);
              DualDarProvisioningHelper.this.mContext.unregisterReceiver(this);
              DualDarProvisioningHelper.this.mContext.unbindService(
                  DualDarProvisioningHelper.this.connection);
              if ("com.android.dualdar.started.provisioning_success".equals(action)) {
                zArr2[0] = true;
              } else {
                zArr2[0] = false;
                iArr[0] = intent.getIntExtra("ERROR_CODE", 5);
              }
              synchronized (obj) {
                zArr[0] = true;
                obj.notify();
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      setFilterForDualDarStarted(intentFilter);
      this.mContext.registerReceiver(broadcastReceiver, intentFilter);
      synchronized (obj) {
        try {
          Log.d("DualDarProvisioningHelper", "Starting Knox DUAL DAR DualDar Started Service");
          Intent intent = new Intent();
          intent.setComponent(
              new ComponentName(
                  "com.samsung.android.knox.containercore",
                  "com.samsung.android.knox.containercore.provisioning.DualDarStartedService"));
          intent.putExtra(
              "DUAL_DAR_PARAMS",
              getDualDARConfigParams(
                  this.mContext, managedProfileProvisioningParams.getAdminExtras()));
          intent.putExtra("DualDARServiceEventFlag", 500);
          intent.putExtra(
              "DUAL_DAR_ADMIN_PACKAGE",
              managedProfileProvisioningParams.getProfileAdminComponentName().getPackageName());
          intent.putExtra(
              "DUAL_DAR_INTENT_PROVISIONING",
              isDualDARIntentProvisioned(
                  this.mContext, managedProfileProvisioningParams.getAdminExtras()));
          intent.putExtra(
              "DUAL_DAR_IS_WPCOD",
              managedProfileProvisioningParams.isOrganizationOwnedProvisioning());
          if (isDualDARNativeCrypto(
              this.mContext, managedProfileProvisioningParams.getAdminExtras())) {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 33554432);
          } else {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 67108864);
          }
          startProvisionService(intent);
          while (!zArr[0]) {
            obj.wait();
          }
        } catch (Exception e) {
          Log.d(
              "DualDarProvisioningHelper",
              "Error during onCreateAndProvisionManagedProfileStarted" + e);
        }
      }
      if (zArr2[0]) {
        return;
      }
      throw new ServiceSpecificException(
          1, "onCreateAndProvisionManagedProfileStarted failed with result: " + iArr[0]);
    }
  }

  public final void setFilterForDualDarStarted(IntentFilter intentFilter) {
    intentFilter.addAction("com.android.dualdar.started.provisioning_success");
    intentFilter.addAction("com.android.dualdar.started.cancelled");
    intentFilter.addAction("com.android.dualdar.started.error");
  }

  public void onCreateAndProvisioningManagedProfileCompletedForDualDar(
      ManagedProfileProvisioningParams managedProfileProvisioningParams, int i) {
    if (isDualDARConfigured(this.mContext, managedProfileProvisioningParams.getAdminExtras())) {
      final Object obj = new Object();
      final boolean[] zArr = {false};
      final boolean[] zArr2 = new boolean[1];
      final int[] iArr = new int[1];
      BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
            // com.android.server.devicepolicy.DualDarProvisioningHelper.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Log.d(
                  "DualDarProvisioningHelper",
                  "DualDAR Managed Profile Completed Service onReceived is called: " + action);
              DualDarProvisioningHelper.this.mContext.unregisterReceiver(this);
              DualDarProvisioningHelper.this.mContext.unbindService(
                  DualDarProvisioningHelper.this.connection);
              if ("com.android.dualdar.completed.provisioning_success".equals(action)) {
                zArr2[0] = true;
              } else if ("com.android.dualdar.completed.cancelled".equals(action)) {
                zArr2[0] = true;
                Log.d(
                    "DualDarProvisioningHelper",
                    "DualDAR is not enabled. dualdar completed provisinoing cacelled.");
              } else {
                zArr2[0] = false;
                iArr[0] = intent.getIntExtra("ERROR_CODE", 5);
              }
              synchronized (obj) {
                zArr[0] = true;
                obj.notify();
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      setFilterForDualDarCompleted(intentFilter);
      this.mContext.registerReceiver(broadcastReceiver, intentFilter);
      synchronized (obj) {
        try {
          Log.d("DualDarProvisioningHelper", "Starting Knox DUAL DAR DualDar Completed Service");
          Intent intent = new Intent();
          intent.setComponent(
              new ComponentName(
                  "com.samsung.android.knox.containercore",
                  "com.samsung.android.knox.containercore.provisioning.DualDarCompletedService"));
          intent.putExtra("android.intent.extra.user_handle", i);
          intent.putExtra(
              "DUAL_DAR_PARAMS",
              getDualDARConfigParams(
                  this.mContext, managedProfileProvisioningParams.getAdminExtras()));
          intent.putExtra("DualDARServiceEventFlag", 500);
          intent.putExtra(
              "DUAL_DAR_ADMIN_PACKAGE",
              managedProfileProvisioningParams.getProfileAdminComponentName().getPackageName());
          intent.putExtra(
              "DUAL_DAR_INTENT_PROVISIONING",
              isDualDARIntentProvisioned(
                  this.mContext, managedProfileProvisioningParams.getAdminExtras()));
          intent.putExtra(
              "DUAL_DAR_IS_WPCOD",
              managedProfileProvisioningParams.isOrganizationOwnedProvisioning());
          if (isDualDARNativeCrypto(
              this.mContext, managedProfileProvisioningParams.getAdminExtras())) {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 33554432);
          } else {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 67108864);
          }
          startProvisionService(intent);
          while (!zArr[0]) {
            obj.wait();
          }
        } catch (Exception e) {
          Log.d(
              "DualDarProvisioningHelper",
              "Error during onCreateAndProvisionManagedProfileCompleted" + e);
        }
      }
      if (zArr2[0]) {
        return;
      }
      throw new ServiceSpecificException(
          2, "onCreateAndProvisionManagedProfileCompleted failed with result: " + iArr[0]);
    }
  }

  public final void setFilterForDualDarCompleted(IntentFilter intentFilter) {
    intentFilter.addAction("com.android.dualdar.completed.provisioning_success");
    intentFilter.addAction("com.android.dualdar.completed.cancelled");
    intentFilter.addAction("com.android.dualdar.completed.error");
  }

  public void onProvisionFullyManagedDeviceStartedForDualDar(
      FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams) {
    int i;
    if (isDualDARConfigured(this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras())) {
      final Object obj = new Object();
      final boolean[] zArr = {false};
      final boolean[] zArr2 = new boolean[1];
      final int[] iArr = new int[1];
      BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
            // com.android.server.devicepolicy.DualDarProvisioningHelper.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Log.d(
                  "DualDarProvisioningHelper",
                  "DualDAR Managed Device Started Service onReceived is called: " + action);
              DualDarProvisioningHelper.this.mContext.unregisterReceiver(this);
              DualDarProvisioningHelper.this.mContext.unbindService(
                  DualDarProvisioningHelper.this.connection);
              if ("com.android.dualdar.started.provisioning_success".equals(action)) {
                zArr2[0] = true;
              } else {
                zArr2[0] = false;
                iArr[0] = intent.getIntExtra("ERROR_CODE", 5);
              }
              synchronized (obj) {
                zArr[0] = true;
                obj.notify();
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      setFilterForDualDarStarted(intentFilter);
      this.mContext.registerReceiver(broadcastReceiver, intentFilter);
      synchronized (obj) {
        try {
          Log.d("DualDarProvisioningHelper", "Starting Knox DUAL DAR DualDar Started Service");
          Intent intent = new Intent();
          intent.setComponent(
              new ComponentName(
                  "com.samsung.android.knox.containercore",
                  "com.samsung.android.knox.containercore.provisioning.DualDarStartedService"));
          intent.putExtra("android.intent.extra.user_handle", UserHandle.myUserId());
          intent.putExtra(
              "DUAL_DAR_PARAMS",
              getDualDARConfigParams(
                  this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras()));
          intent.putExtra("DualDARServiceEventFlag", 500);
          intent.putExtra(
              "DUAL_DAR_ADMIN_PACKAGE",
              fullyManagedDeviceProvisioningParams.getDeviceAdminComponentName().getPackageName());
          intent.putExtra(
              "DUAL_DAR_INTENT_PROVISIONING",
              isDualDARIntentProvisioned(
                  this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras()));
          intent.putExtra("DUAL_DAR_IS_MANAGED_DEVICE", true);
          if (isDualDARNativeCrypto(
              this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras())) {
            i = 33554432;
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 33554432);
          } else {
            i = 67108864;
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 67108864);
          }
          if (!this.mUserManagerInternal.setDualDarInfo(0, i)) {
            Log.e("DualDarProvisioningHelper", "Failed to set dualdar crypto type to UserInfo");
            zArr2[0] = false;
            iArr[0] = intent.getIntExtra("ERROR_CODE", 5);
          } else {
            startProvisionService(intent);
            while (!zArr[0]) {
              obj.wait();
            }
          }
        } catch (Exception e) {
          Log.d(
              "DualDarProvisioningHelper", "Error during onProvisionFullyManagedDeviceStarted" + e);
        }
      }
      if (zArr2[0]) {
        return;
      }
      throw new ServiceSpecificException(
          1, "onProvisionFullyManagedDeviceStarted failed with result: " + iArr[0]);
    }
  }

  public void onProvisionFullyManagedDeviceCompletedForDualDar(
      FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams) {
    if (isDualDARConfigured(this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras())) {
      final Object obj = new Object();
      final boolean[] zArr = {false};
      final boolean[] zArr2 = new boolean[1];
      final int[] iArr = new int[1];
      BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
            // com.android.server.devicepolicy.DualDarProvisioningHelper.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              Log.d(
                  "DualDarProvisioningHelper",
                  "DualDAR Managed Device Completed Service onReceived is called: " + action);
              DualDarProvisioningHelper.this.mContext.unregisterReceiver(this);
              DualDarProvisioningHelper.this.mContext.unbindService(
                  DualDarProvisioningHelper.this.connection);
              if ("com.android.dualdar.completed.provisioning_success".equals(action)) {
                zArr2[0] = true;
              } else {
                zArr2[0] = false;
                iArr[0] = intent.getIntExtra("ERROR_CODE", 5);
              }
              synchronized (obj) {
                zArr[0] = true;
                obj.notify();
              }
            }
          };
      IntentFilter intentFilter = new IntentFilter();
      setFilterForDualDarCompleted(intentFilter);
      this.mContext.registerReceiver(broadcastReceiver, intentFilter);
      synchronized (obj) {
        try {
          Log.d("DualDarProvisioningHelper", "Starting Knox DUAL DAR DualDar Completed Service");
          Intent intent = new Intent();
          intent.setComponent(
              new ComponentName(
                  "com.samsung.android.knox.containercore",
                  "com.samsung.android.knox.containercore.provisioning.DualDarCompletedService"));
          intent.putExtra("android.intent.extra.user_handle", UserHandle.myUserId());
          intent.putExtra(
              "DUAL_DAR_PARAMS",
              getDualDARConfigParams(
                  this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras()));
          intent.putExtra(
              "DUAL_DAR_ADMIN_PACKAGE",
              fullyManagedDeviceProvisioningParams.getDeviceAdminComponentName().getPackageName());
          intent.putExtra(
              "DUAL_DAR_INTENT_PROVISIONING",
              isDualDARIntentProvisioned(
                  this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras()));
          intent.putExtra("DUAL_DAR_IS_MANAGED_DEVICE", true);
          if (isDualDARNativeCrypto(
              this.mContext, fullyManagedDeviceProvisioningParams.getAdminExtras())) {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 33554432);
          } else {
            intent.putExtra("DUAL_DAR_CRYPTO_TYPE", 67108864);
          }
          startProvisionService(intent);
          while (!zArr[0]) {
            obj.wait();
          }
        } catch (Exception e) {
          Log.d(
              "DualDarProvisioningHelper",
              "Error during onProvisionFullyManagedDeviceCompleted" + e);
        }
      }
      if (zArr2[0]) {
        return;
      }
      throw new ServiceSpecificException(
          7, "onProvisionFullyManagedDeviceCompleted failed with result: " + iArr[0]);
    }
  }

  public static boolean isDualDARConfigured(Context context, PersistableBundle persistableBundle) {
    if (persistableBundle != null && persistableBundle.getBoolean("dualdar-config")) {
      Slogf.m86d("DualDarProvisioningHelper", "isDualDARConfigured from provisioning params");
      return true;
    }
    if (getDualDARProfile(context) == null) {
      return false;
    }
    Slogf.m86d("DualDarProvisioningHelper", "isDualDARConfigured from preset params");
    return true;
  }

  public static Bundle getDualDARConfigParams(
      Context context, PersistableBundle persistableBundle) {
    if (persistableBundle != null && isDualDARIntentProvisioned(context, persistableBundle)) {
      return new Bundle(persistableBundle).deepCopy();
    }
    Bundle dualDARProfile = getDualDARProfile(context);
    if (dualDARProfile == null) {
      return null;
    }
    String string = dualDARProfile.getString("dualdar-config-client-package");
    String string2 = dualDARProfile.getString("dualdar-config-client-signature");
    String string3 = dualDARProfile.getString("dualdar-config-client-location");
    Bundle bundle = new Bundle();
    bundle.putBoolean("dualdar-config", true);
    bundle.putString("dualdar-config-client-package", "default".equals(string) ? null : string);
    if ("default".equals(string)) {
      string2 = null;
    }
    bundle.putString("dualdar-config-client-signature", string2);
    bundle.putString("dualdar-config-client-location", "default".equals(string3) ? null : string3);
    return bundle;
  }

  public static boolean isDualDARIntentProvisioned(
      Context context, PersistableBundle persistableBundle) {
    if (persistableBundle == null || !persistableBundle.getBoolean("dualdar-config")) {
      return false;
    }
    Slogf.m86d("DualDarProvisioningHelper", "isDualDARIntentProvisioned from provisioning params");
    return true;
  }

  public static Bundle getDualDARProfile(Context context) {
    SemPersonaManager semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
    if (semPersonaManager != null) {
      return semPersonaManager.getDualDARProfile();
    }
    return null;
  }

  public final boolean isDualDARNativeCrypto(Context context, PersistableBundle persistableBundle) {
    if (persistableBundle != null
        && !TextUtils.isEmpty(persistableBundle.getString("dualdar-config-client-package"))) {
      Log.d("DualDarProvisioningHelper", "Custom crypto from provisioning params");
      return false;
    }
    if (persistableBundle != null && persistableBundle.getBoolean("dualdar-config")) {
      Log.d("DualDarProvisioningHelper", "native crypto from provisioning params");
      return true;
    }
    if (getDualDARProfile(context) == null
        || !"default"
            .equals(getDualDARProfile(context).getString("dualdar-config-client-package"))) {
      return false;
    }
    Log.d("DualDarProvisioningHelper", "isDualDARNativeCrypto from preset params");
    return true;
  }

  public int getDualDarProfileFlags(
      ManagedProfileProvisioningParams managedProfileProvisioningParams) {
    if (isDualDARConfigured(this.mContext, managedProfileProvisioningParams.getAdminExtras())) {
      return isDualDARNativeCrypto(this.mContext, managedProfileProvisioningParams.getAdminExtras())
          ? 33554496
          : 67108928;
    }
    return 64;
  }

  public final void startProvisionService(Intent intent) {
    if (!this.mContext.bindService(intent, this.connection, 65)) {
      throw new Exception("startProvisionService : failed to startService.");
    }
  }
}
