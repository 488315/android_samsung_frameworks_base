package com.android.server.enterprise.restriction;

import android.app.ActivityManagerNative;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.debug.IAdbManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.sysprop.DisplayProperties;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import com.android.internal.app.LocalePicker;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class DeveloperModeSettings {
  public Context mContext;
  public static final Map SYSTEM_SETTINGS_DEFAULT =
      new HashMap() { // from class:
                      // com.android.server.enterprise.restriction.DeveloperModeSettings.1
        private static final long serialVersionUID = 1;

        {
          put("show_touches", "0");
          put("pointer_location", "0");
        }
      };
  public static final Map SECURE_SETTINGS_DEFAULT =
      new HashMap() { // from class:
                      // com.android.server.enterprise.restriction.DeveloperModeSettings.2
        private static final long serialVersionUID = 1;

        {
          put("usb_audio_automatic_routing_disabled", "0");
          put("anr_show_background", "0");
          put("accessibility_display_daltonizer_enabled", "0");
        }
      };
  public static final Map GLOBAL_SETTINGS_DEFAULT =
      new HashMap() { // from class:
                      // com.android.server.enterprise.restriction.DeveloperModeSettings.3
        private static final long serialVersionUID = 1;

        {
          put("development_settings_enabled", "0");
          put("stay_on_while_plugged_in", "0");
          put("adb_enabled", "0");
          put("bugreport_in_power_menu", "0");
          put("debug_view_attributes", "0");
          put("wait_for_debugger", "0");
          put("verifier_verify_adb_installs", "0");
          put("wifi_display_certification_on", "0");
          put("legacy_dhcp_client", "0");
          put("mobile_data_always_on", "0");
          put("overlay_display_devices", "");
        }
      };
  public static final Map SYSTEM_PROPERTIES_DEFAULT =
      new HashMap() { // from class:
                      // com.android.server.enterprise.restriction.DeveloperModeSettings.4
        private static final long serialVersionUID = 1;

        {
          put("persist.sys.ui.hw", String.valueOf(false));
          put("persist.sys.debug.multi_window", String.valueOf(false));
          put("debug.hwui.show_dirty_regions", null);
          put("debug.hwui.show_layers_updates", null);
          put("debug.hwui.overdraw", "");
          put("debug.hwui.show_non_rect_clip", "");
          put("debug.egl.force_msaa", String.valueOf(false));
          put("debug.hwui.profile", "");
          put("debug.egl.trace", "");
          put("persist.sys.hdcp_checking", "drm-only");
          put("persist.logd.size", "262144");
          put("debug.layout", String.valueOf(false));
        }
      };

  public DeveloperModeSettings(Context context) {
    this.mContext = context;
  }

  public boolean resetDeveloperModeOptions() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      resetBluetoothHCILog();
      boolean resetMockLocationApps = resetMockLocationApps() & true & resetDebugApps();
      resetWifiManagerSettings();
      boolean resetUsbAuth = resetMockLocationApps & resetUsbAuth() & resetDrawingOptions();
      resetRtlOptions();
      boolean resetWindowManagerOptions = resetUsbAuth & resetWindowManagerOptions();
      resetCpuUsageOptions();
      boolean resetAppProcessLimitOptions =
          resetWindowManagerOptions
              & resetAppProcessLimitOptions()
              & resetImmediatelyDestroyActivitiesOptions()
              & resetSystemSettings()
              & resetGlobalSettings()
              & resetSecureSettings();
      resetSystemProperties();
      new SystemPropPoker().execute(new Void[0]);
      ApplicationRestrictionsManager applicationRestrictionsManager =
          ApplicationRestrictionsManager.getInstance(this.mContext);
      if (applicationRestrictionsManager != null
          && !applicationRestrictionsManager.isSettingPolicyApplied()) {
        try {
          ActivityManagerNative.getDefault()
              .forceStopPackage("com.android.settings", this.mContext.getUserId());
        } catch (RemoteException e) {
          Log.w("DeveloperModeSettings", "killSettings: RemoteException ex -> " + e.getMessage());
        }
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      Slog.d("DeveloperModeSettings", "allowDeveloperMode: false");
      return resetAppProcessLimitOptions;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final void resetBluetoothHCILog() {
    SystemProperties.set("persist.bluetooth.btsnoopenable", Boolean.toString(false));
  }

  public boolean resetMockLocationApps() {
    AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
    List<AppOpsManager.PackageOps> packagesForOps = appOpsManager.getPackagesForOps(new int[] {58});
    if (packagesForOps == null) {
      return true;
    }
    for (AppOpsManager.PackageOps packageOps : packagesForOps) {
      if (((AppOpsManager.OpEntry) packageOps.getOps().get(0)).getMode() != 2) {
        String packageName = packageOps.getPackageName();
        try {
          appOpsManager.setMode(
              58,
              this.mContext.getPackageManager().getApplicationInfo(packageName, 512).uid,
              packageName,
              2);
        } catch (PackageManager.NameNotFoundException unused) {
          return false;
        }
      }
    }
    return true;
  }

  public final boolean resetDebugApps() {
    try {
      ActivityManagerNative.getDefault().setDebugApp((String) null, false, true);
      return true;
    } catch (RemoteException unused) {
      return false;
    }
  }

  public final void resetWifiManagerSettings() {
    ((WifiManager) this.mContext.getSystemService("wifi")).setVerboseLoggingEnabled(false);
  }

  public final boolean resetUsbAuth() {
    try {
      IAdbManager asInterface = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
      boolean z = SystemProperties.getBoolean("ro.adb.secure", false);
      boolean equals = "trigger_restart_min_framework".equals(SystemProperties.get("vold.decrypt"));
      if (!z || equals) {
        return true;
      }
      asInterface.clearDebuggingKeys();
      return true;
    } catch (RemoteException e) {
      Log.e("DeveloperModeSettings", "Unable to clear adb keys", e);
      return false;
    }
  }

  public final boolean resetDrawingOptions() {
    try {
      IBinder service = ServiceManager.getService("SurfaceFlinger");
      if (service == null) {
        return true;
      }
      Parcel obtain = Parcel.obtain();
      obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
      obtain.writeInt(0);
      if (isShowingScreenUpdateAndReloadSurface()) {
        service.transact(1002, obtain, null, 0);
      }
      service.transact(1008, obtain, null, 0);
      obtain.recycle();
      isShowingScreenUpdateAndReloadSurface();
      return true;
    } catch (RemoteException e) {
      Log.w(
          "DeveloperModeSettings",
          "resetShowUpdatesOption: RemoteException ex -> " + e.getMessage());
      return false;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0055 A[ORIG_RETURN, RETURN] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean isShowingScreenUpdateAndReloadSurface() {
    int i;
    try {
      IBinder service = ServiceManager.getService("SurfaceFlinger");
      if (service != null) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        service.transact(1010, obtain, obtain2, 0);
        obtain2.readInt();
        obtain2.readInt();
        i = obtain2.readInt();
        try {
          obtain2.readInt();
          obtain2.readInt();
          obtain2.recycle();
          obtain.recycle();
        } catch (RemoteException e) {
          e = e;
          Log.w(
              "DeveloperModeSettings",
              "updateFlingerOptions: RemoteException ex -> " + e.getMessage());
          if (i == 0) {}
        }
      } else {
        i = 0;
      }
    } catch (RemoteException e2) {
      e = e2;
      i = 0;
    }
    return i == 0;
  }

  public final void resetRtlOptions() {
    Settings.Global.putInt(this.mContext.getContentResolver(), "debug.force_rtl", 0);
    SystemProperties.set("debug.force_rtl", "0");
    LocalePicker.updateLocales(this.mContext.getResources().getConfiguration().getLocales());
  }

  public final boolean resetWindowManagerOptions() {
    try {
      IWindowManager asInterface =
          IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
      asInterface.setAnimationScale(0, 1.0f);
      asInterface.setAnimationScale(1, 1.0f);
      asInterface.setAnimationScale(2, 1.0f);
      asInterface.setStrictModeVisualIndicatorPreference("");
      return true;
    } catch (RemoteException e) {
      Log.w(
          "DeveloperModeSettings",
          "resetWindowManagerOptions: RemoteException ex -> " + e.getMessage());
      return false;
    }
  }

  public final void resetCpuUsageOptions() {
    Settings.Global.putInt(this.mContext.getContentResolver(), "show_processes", 0);
    this.mContext.stopService(
        new Intent()
            .setClassName("com.android.systemui", "com.android.systemui.LoadAverageService"));
  }

  public final boolean resetAppProcessLimitOptions() {
    try {
      ActivityManagerNative.getDefault().setProcessLimit(-1);
      return true;
    } catch (RemoteException e) {
      Log.w(
          "DeveloperModeSettings",
          "resetAppProcessLimitOptions: RemoteException ex -> " + e.getMessage());
      return false;
    }
  }

  public final boolean resetImmediatelyDestroyActivitiesOptions() {
    try {
      ActivityManagerNative.getDefault().setAlwaysFinish(false);
      return true;
    } catch (RemoteException e) {
      Log.w(
          "DeveloperModeSettings",
          "resetImmediatelyDestroyActivitiesOptions: RemoteException ex -> " + e.getMessage());
      return false;
    }
  }

  public final boolean resetSystemSettings() {
    boolean z = true;
    for (Map.Entry entry : SYSTEM_SETTINGS_DEFAULT.entrySet()) {
      z &=
          Settings.System.putString(
              this.mContext.getContentResolver(),
              (String) entry.getKey(),
              (String) entry.getValue());
    }
    return z;
  }

  public final boolean resetGlobalSettings() {
    boolean z = true;
    for (Map.Entry entry : GLOBAL_SETTINGS_DEFAULT.entrySet()) {
      z &=
          Settings.Global.putString(
              this.mContext.getContentResolver(),
              (String) entry.getKey(),
              (String) entry.getValue());
    }
    return z;
  }

  public final boolean resetSecureSettings() {
    boolean z = true;
    for (Map.Entry entry : SECURE_SETTINGS_DEFAULT.entrySet()) {
      z &=
          Settings.Secure.putString(
              this.mContext.getContentResolver(),
              (String) entry.getKey(),
              (String) entry.getValue());
    }
    return z;
  }

  public final void resetSystemProperties() {
    for (Map.Entry entry : SYSTEM_PROPERTIES_DEFAULT.entrySet()) {
      if (((String) entry.getKey()).equals("debug.layout")) {
        DisplayProperties.debug_layout(
            Boolean.valueOf(Boolean.getBoolean((String) entry.getValue())));
      } else {
        SystemProperties.set((String) entry.getKey(), (String) entry.getValue());
      }
    }
  }

  public class SystemPropPoker extends AsyncTask {
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voidArr) {
      String[] listServices;
      try {
        Log.d("DeveloperModeSettings", "Start System Poker - ServiceManager.listServices()");
        listServices = ServiceManager.listServices();
      } catch (Exception unused) {
      }
      if (listServices == null || listServices.length == 0) {
        Log.d("DeveloperModeSettings", " System Poker - Failed to get services");
        return null;
      }
      for (String str : listServices) {
        if (str != null) {
          try {
            IBinder checkService = ServiceManager.checkService(str);
            if (checkService != null) {
              Parcel obtain = Parcel.obtain();
              checkService.transact(1599295570, obtain, null, 0);
              obtain.recycle();
            }
          } catch (RemoteException unused2) {
          } catch (Exception e) {
            Log.i(
                "DeveloperModeSettings",
                "Someone wrote a bad service '" + str + "' that doesn't like to be poked: " + e);
          }
        }
      }
      return null;
    }
  }
}
