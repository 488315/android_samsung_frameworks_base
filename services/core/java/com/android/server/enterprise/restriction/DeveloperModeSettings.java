package com.android.server.enterprise.restriction;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.sysprop.DisplayProperties;
import android.util.Log;
import android.view.IWindowManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeveloperModeSettings {
    public final Context mContext;
    public static final Map SYSTEM_SETTINGS_DEFAULT = new HashMap() { // from class: com.android.server.enterprise.restriction.DeveloperModeSettings.1
        private static final long serialVersionUID = 1;

        {
            put("show_touches", "0");
            put("pointer_location", "0");
        }
    };
    public static final Map SECURE_SETTINGS_DEFAULT = new HashMap() { // from class: com.android.server.enterprise.restriction.DeveloperModeSettings.2
        private static final long serialVersionUID = 1;

        {
            put("usb_audio_automatic_routing_disabled", "0");
            put("anr_show_background", "0");
            put("accessibility_display_daltonizer_enabled", "0");
        }
    };
    public static final Map GLOBAL_SETTINGS_DEFAULT = new HashMap() { // from class: com.android.server.enterprise.restriction.DeveloperModeSettings.3
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
    public static final Map SYSTEM_PROPERTIES_DEFAULT = new HashMap() { // from class: com.android.server.enterprise.restriction.DeveloperModeSettings.4
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemPropPoker extends AsyncTask {
        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                Log.d("DeveloperModeSettings", "Start System Poker - ServiceManager.listServices()");
                String[] listServices = ServiceManager.listServices();
                if (listServices == null || listServices.length == 0) {
                    Log.d("DeveloperModeSettings", " System Poker - Failed to get services");
                } else {
                    for (String str : listServices) {
                        if (str != null) {
                            try {
                                IBinder checkService = ServiceManager.checkService(str);
                                if (checkService != null) {
                                    Parcel obtain = Parcel.obtain();
                                    checkService.transact(1599295570, obtain, null, 0);
                                    obtain.recycle();
                                }
                            } catch (RemoteException unused) {
                            } catch (Exception e) {
                                Log.i("DeveloperModeSettings", "Someone wrote a bad service '" + str + "' that doesn't like to be poked: " + e);
                            }
                        }
                    }
                }
            } catch (Exception unused2) {
            }
            return null;
        }
    }

    public DeveloperModeSettings(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0053 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isShowingScreenUpdateAndReloadSurface() {
        /*
            r0 = 0
            java.lang.String r1 = "SurfaceFlinger"
            android.os.IBinder r1 = android.os.ServiceManager.getService(r1)     // Catch: android.os.RemoteException -> L34
            if (r1 == 0) goto L37
            android.os.Parcel r2 = android.os.Parcel.obtain()     // Catch: android.os.RemoteException -> L34
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: android.os.RemoteException -> L34
            java.lang.String r4 = "android.ui.ISurfaceComposer"
            r2.writeInterfaceToken(r4)     // Catch: android.os.RemoteException -> L34
            r4 = 1010(0x3f2, float:1.415E-42)
            r1.transact(r4, r2, r3, r0)     // Catch: android.os.RemoteException -> L34
            r3.readInt()     // Catch: android.os.RemoteException -> L34
            r3.readInt()     // Catch: android.os.RemoteException -> L34
            int r1 = r3.readInt()     // Catch: android.os.RemoteException -> L34
            r3.readInt()     // Catch: android.os.RemoteException -> L32
            r3.readInt()     // Catch: android.os.RemoteException -> L32
            r3.recycle()     // Catch: android.os.RemoteException -> L32
            r2.recycle()     // Catch: android.os.RemoteException -> L32
            goto L51
        L32:
            r2 = move-exception
            goto L39
        L34:
            r2 = move-exception
            r1 = r0
            goto L39
        L37:
            r1 = r0
            goto L51
        L39:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "updateFlingerOptions: RemoteException ex -> "
            r3.<init>(r4)
            java.lang.String r2 = r2.getMessage()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r3 = "DeveloperModeSettings"
            android.util.Log.w(r3, r2)
        L51:
            if (r1 == 0) goto L54
            r0 = 1
        L54:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.restriction.DeveloperModeSettings.isShowingScreenUpdateAndReloadSurface():boolean");
    }

    public static boolean resetDrawingOptions() {
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
            Log.w("DeveloperModeSettings", "resetShowUpdatesOption: RemoteException ex -> " + e.getMessage());
            return false;
        }
    }

    public static void resetSystemProperties() {
        for (Map.Entry entry : ((HashMap) SYSTEM_PROPERTIES_DEFAULT).entrySet()) {
            if (((String) entry.getKey()).equals("debug.layout")) {
                DisplayProperties.debug_layout(Boolean.valueOf(Boolean.getBoolean((String) entry.getValue())));
            } else {
                SystemProperties.set((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    public static boolean resetWindowManagerOptions() {
        try {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            asInterface.setAnimationScale(0, 1.0f);
            asInterface.setAnimationScale(1, 1.0f);
            asInterface.setAnimationScale(2, 1.0f);
            asInterface.setStrictModeVisualIndicatorPreference("");
            return true;
        } catch (RemoteException e) {
            Log.w("DeveloperModeSettings", "resetWindowManagerOptions: RemoteException ex -> " + e.getMessage());
            return false;
        }
    }

    public final boolean resetMockLocationApps() {
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
        List<AppOpsManager.PackageOps> packagesForOps = appOpsManager.getPackagesForOps(new int[]{58});
        if (packagesForOps == null) {
            return true;
        }
        for (AppOpsManager.PackageOps packageOps : packagesForOps) {
            if (((AppOpsManager.OpEntry) packageOps.getOps().get(0)).getMode() != 2) {
                String packageName = packageOps.getPackageName();
                try {
                    appOpsManager.setMode(58, this.mContext.getPackageManager().getApplicationInfo(packageName, 512).uid, packageName, 2);
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            }
        }
        return true;
    }
}
