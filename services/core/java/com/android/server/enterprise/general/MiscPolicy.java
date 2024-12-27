package com.android.server.enterprise.general;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.ProxyInfo;
import android.net.Uri;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.ILockSettings;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.general.font.FontWriter;
import com.android.server.enterprise.general.font.Typeface;
import com.android.server.enterprise.general.font.TypefaceFile;
import com.android.server.enterprise.general.font.TypefaceFinder;
import com.android.server.enterprise.proxy.LocalProxyManager;
import com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda0;
import com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda2;
import com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda5;
import com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda7;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.deviceinfo.SimChangeInfo;
import com.samsung.android.knox.deviceinfo.SimInfo;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MiscPolicy extends IMiscPolicy.Stub implements EnterpriseServiceCallback {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static PackageManager mPackageManager;
    public ArrayList mAdminsBlockingNfcStateChange;
    public ConnectivityManager mCon;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final LocalProxyManager mLocalProxyManager;
    public ILockSettings mLockSettingsService;
    public SystemFontChanger mSystemFontChanger;
    public EnterpriseDeviceManager mEDM = null;
    public float[] mFontSizes = null;
    public int credentialsFailsCount = 0;
    public int credentialsFailsState = 0;
    public final ArrayList mStartNFCHistoryList = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SIMCardUpdateMonitor {
        public final Context mCtxt;
        public final AnonymousClass1 mIntentReceiver;
        public final AnonymousClass1 mReceiver;
        public final TelephonyManager mTelephonyManager;

        /* renamed from: -$$Nest$msaveSimState, reason: not valid java name */
        public static void m517$$Nest$msaveSimState(SIMCardUpdateMonitor sIMCardUpdateMonitor) {
            sIMCardUpdateMonitor.getClass();
            String readPropertyValue = Utils.readPropertyValue("CurrentSimSerialNumber");
            if (readPropertyValue == null || sIMCardUpdateMonitor.mTelephonyManager.getSimSerialNumber() == null || !sIMCardUpdateMonitor.mTelephonyManager.getSimSerialNumber().equalsIgnoreCase(readPropertyValue)) {
                Utils.writePropertyValue("PreviousSimCountryIso", Utils.readPropertyValue("CurrentSimCountryIso"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimOperator", Utils.readPropertyValue("CurrentSimOperator"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimOperatorName", Utils.readPropertyValue("CurrentSimOperatorName"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimSerialNumber", Utils.readPropertyValue("CurrentSimSerialNumber"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimPhoneNumber", Utils.readPropertyValue("CurrentSimPhoneNumber"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimCountryIso", sIMCardUpdateMonitor.mTelephonyManager.getSimCountryIso(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimOperator", sIMCardUpdateMonitor.mTelephonyManager.getSimOperator(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimOperatorName", sIMCardUpdateMonitor.mTelephonyManager.getSimOperatorName(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimSerialNumber", sIMCardUpdateMonitor.mTelephonyManager.getSimSerialNumber(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimPhoneNumber", sIMCardUpdateMonitor.mTelephonyManager.getLine1Number(), "/data/system/SimCard.dat");
                FileUtils.setPermissions(new File("/data/system/SimCard.dat").getAbsolutePath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
            }
        }

        public SIMCardUpdateMonitor(Context context) {
            final int i = 1;
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.general.MiscPolicy.SIMCardUpdateMonitor.1
                public final /* synthetic */ SIMCardUpdateMonitor this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    switch (i) {
                        case 0:
                            if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                                int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                                MiscPolicy miscPolicy = MiscPolicy.this;
                                PackageManager packageManager = MiscPolicy.mPackageManager;
                                miscPolicy.updateSystemUIMonitor$2(intExtra);
                                break;
                            }
                            break;
                        default:
                            try {
                                String action = intent.getAction();
                                this.this$1.getClass();
                                Log.d("SIMCardUpdateMonitor ", " action is : " + action);
                                if (Constants.SIM_STATE_CHANGED.equals(action)) {
                                    String stringExtra = intent.getStringExtra("ss");
                                    this.this$1.getClass();
                                    Log.d("SIMCardUpdateMonitor ", " state Extra is : " + stringExtra);
                                    if (!"ABSENT".equals(stringExtra)) {
                                        if ("LOADED".equals(stringExtra)) {
                                            this.this$1.getClass();
                                            Log.d("SIMCardUpdateMonitor ", " SIM Card State : LOADED");
                                            SIMCardUpdateMonitor.m517$$Nest$msaveSimState(this.this$1);
                                            Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                                            String readPropertyValue = Utils.readPropertyValue("PreviousSimSerialNumber");
                                            String readPropertyValue2 = Utils.readPropertyValue("CurrentSimSerialNumber");
                                            if (readPropertyValue == null || readPropertyValue.equalsIgnoreCase(readPropertyValue2)) {
                                                Utils.writePropertyValue("SimChangeOperation", "3", "/data/system/SimCard.dat");
                                            } else {
                                                Utils.writePropertyValue("SimChangeOperation", "2", "/data/system/SimCard.dat");
                                            }
                                            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                                            this.this$1.mCtxt.sendBroadcast(intent2, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                                            break;
                                        }
                                    } else {
                                        this.this$1.getClass();
                                        Log.d("SIMCardUpdateMonitor ", " SIM Card State :" + IccCardConstants.State.ABSENT.name());
                                        Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                                        Utils.writePropertyValue("SimChangeOperation", "1", "/data/system/SimCard.dat");
                                        FileUtils.setPermissions(new File("/data/system/SimCard.dat").getAbsolutePath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
                                        Intent intent3 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                                        this.this$1.mCtxt.sendBroadcast(intent3, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                this.this$1.getClass();
                                Log.w("SIMCardUpdateMonitor ", "SIMCardBroadcastReceiver Ex:" + e);
                            }
                            break;
                    }
                }
            };
            final int i2 = 0;
            BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.general.MiscPolicy.SIMCardUpdateMonitor.1
                public final /* synthetic */ SIMCardUpdateMonitor this$1;

                {
                    this.this$1 = this;
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    switch (i2) {
                        case 0:
                            if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                                int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                                MiscPolicy miscPolicy = MiscPolicy.this;
                                PackageManager packageManager = MiscPolicy.mPackageManager;
                                miscPolicy.updateSystemUIMonitor$2(intExtra);
                                break;
                            }
                            break;
                        default:
                            try {
                                String action = intent.getAction();
                                this.this$1.getClass();
                                Log.d("SIMCardUpdateMonitor ", " action is : " + action);
                                if (Constants.SIM_STATE_CHANGED.equals(action)) {
                                    String stringExtra = intent.getStringExtra("ss");
                                    this.this$1.getClass();
                                    Log.d("SIMCardUpdateMonitor ", " state Extra is : " + stringExtra);
                                    if (!"ABSENT".equals(stringExtra)) {
                                        if ("LOADED".equals(stringExtra)) {
                                            this.this$1.getClass();
                                            Log.d("SIMCardUpdateMonitor ", " SIM Card State : LOADED");
                                            SIMCardUpdateMonitor.m517$$Nest$msaveSimState(this.this$1);
                                            Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                                            String readPropertyValue = Utils.readPropertyValue("PreviousSimSerialNumber");
                                            String readPropertyValue2 = Utils.readPropertyValue("CurrentSimSerialNumber");
                                            if (readPropertyValue == null || readPropertyValue.equalsIgnoreCase(readPropertyValue2)) {
                                                Utils.writePropertyValue("SimChangeOperation", "3", "/data/system/SimCard.dat");
                                            } else {
                                                Utils.writePropertyValue("SimChangeOperation", "2", "/data/system/SimCard.dat");
                                            }
                                            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                                            intent2.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                                            this.this$1.mCtxt.sendBroadcast(intent2, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                                            break;
                                        }
                                    } else {
                                        this.this$1.getClass();
                                        Log.d("SIMCardUpdateMonitor ", " SIM Card State :" + IccCardConstants.State.ABSENT.name());
                                        Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                                        Utils.writePropertyValue("SimChangeOperation", "1", "/data/system/SimCard.dat");
                                        FileUtils.setPermissions(new File("/data/system/SimCard.dat").getAbsolutePath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
                                        Intent intent3 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                                        intent3.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                                        this.this$1.mCtxt.sendBroadcast(intent3, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                this.this$1.getClass();
                                Log.w("SIMCardUpdateMonitor ", "SIMCardBroadcastReceiver Ex:" + e);
                            }
                            break;
                    }
                }
            };
            this.mCtxt = context;
            this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.SIM_STATE_CHANGED);
            context.registerReceiver(broadcastReceiver, intentFilter);
            context.registerReceiver(broadcastReceiver2, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"), 2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemFontChanger {
        public TypefaceFinder mTypefaceFinder = null;

        public SystemFontChanger() {
        }

        public final String[] getFontString() {
            this.mTypefaceFinder = null;
            this.mTypefaceFinder = new TypefaceFinder();
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            PackageManager packageManager = MiscPolicy.this.mContext.getPackageManager();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
                    for (int i = 0; i < installedApplications.size(); i++) {
                        String str = installedApplications.get(i).packageName;
                        if (!str.startsWith("com.monotype.android.font.droidserifitalic") && str.startsWith("com.monotype.android.font.")) {
                            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128);
                            applicationInfo.publicSourceDir = applicationInfo.sourceDir;
                            this.mTypefaceFinder.findTypefaces(packageManager.getResourcesForApplication(applicationInfo).getAssets(), str);
                        }
                    }
                    this.mTypefaceFinder.getSansEntries(packageManager, vector2, vector3, vector);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Exception e) {
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    vector2 = null;
                }
                if (vector2 == null) {
                    return null;
                }
                String[] strArr = new String[vector2.size()];
                for (int i2 = 0; i2 < vector2.size(); i2++) {
                    strArr[i2] = (String) vector2.get(i2);
                }
                return strArr;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void savePreferences(int i, String str) {
            Context context;
            MiscPolicy miscPolicy = MiscPolicy.this;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                context = miscPolicy.mContext.createPackageContext(KnoxCustomManagerService.SETTING_PKG_NAME, 2);
            } catch (Exception e) {
                e.printStackTrace();
                context = null;
            }
            if (context == null) {
                Log.e("MiscPolicy", "Setting Context is Null");
                return;
            }
            SharedPreferences.Editor edit = context.getSharedPreferences("prefs", 0).edit();
            edit.putBoolean("SelectDialogIsActive", false);
            edit.putInt("SavedClickedItem", i);
            edit.commit();
            Settings.Global.putInt(miscPolicy.mContext.getContentResolver(), "flip_font_style", i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            SharedPreferences.Editor edit2 = context.getSharedPreferences(context.getPackageName() + "_preferences", 0).edit();
            edit2.putString("MONOTYPE", str);
            edit2.commit();
        }
    }

    public MiscPolicy(Context context) {
        this.mLocalProxyManager = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        new SIMCardUpdateMonitor(context);
        LocalProxyManager localProxyManager = LocalProxyManager.getInstance(context);
        this.mLocalProxyManager = localProxyManager;
        mPackageManager = context.getPackageManager();
        localProxyManager.updateGlobalProxy(getGlobalProxy());
    }

    public static boolean supportBigFont(Context context) {
        return context.getPackageManager().hasSystemFeature("com.sec.feature.folder_type") && "CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO")) && !Build.PRODUCT.contains("elite");
    }

    public static boolean validateProxyProperties(ProxyProperties proxyProperties) {
        boolean z;
        if (!proxyProperties.isValid()) {
            return false;
        }
        if (!TextUtils.isEmpty(proxyProperties.getHostname())) {
            String hostname = proxyProperties.getHostname();
            int portNumber = proxyProperties.getPortNumber();
            if (portNumber >= 0 && portNumber <= 65535) {
                if (!(hostname != null ? Patterns.IP_ADDRESS.matcher(hostname).matches() : false)) {
                    if (hostname != null) {
                        if (!hostname.equals("*")) {
                            if (hostname.length() <= 255) {
                                String[] split = hostname.split("\\.");
                                for (int i = 0; i < split[0].length(); i++) {
                                    char charAt = split[0].charAt(i);
                                    if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                                        int i2 = 0;
                                        for (int i3 = 0; i3 < hostname.length(); i3++) {
                                            if (hostname.charAt(i3) == '.') {
                                                i2++;
                                            }
                                        }
                                        if (i2 < split.length) {
                                            int length = split.length;
                                            int i4 = 0;
                                            while (true) {
                                                if (i4 >= length) {
                                                    for (String str : split) {
                                                        if (str.matches("^[A-Za-z0-9-]+$") && str.charAt(0) != '-' && str.charAt(str.length() - 1) != '-') {
                                                        }
                                                    }
                                                } else {
                                                    if (split[i4].length() > 63) {
                                                        break;
                                                    }
                                                    i4++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        z = true;
                        if (z) {
                        }
                    }
                    z = false;
                    if (z) {
                    }
                }
            }
            return false;
        }
        if (TextUtils.isEmpty(proxyProperties.getPacFileUrl()) || !URLUtil.isNetworkUrl(proxyProperties.getPacFileUrl())) {
            return false;
        }
        return true;
    }

    public final boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission = getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("MISC", enforceOwnerOnlyAndActiveAdminPermission.mCallerUid, z, 0, "nfcStateChangeAllowed");
        updateAdminsBlockingNfcStateChange();
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logEventAsUser(UserHandle.getUserId(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid), 71, new Object[]{Integer.valueOf(enforceOwnerOnlyAndActiveAdminPermission.mCallerUid), Boolean.valueOf(z)});
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public final boolean changeLockScreenString(ContextInfo contextInfo, String str) {
        String str2;
        boolean z;
        boolean putInt;
        String str3;
        ContextInfo enforceOwnerOnlyAndSecurityPermission = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndSecurityPermission);
        Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, enforceOwnerOnlyAndSecurityPermission.mCallerUid);
        if (createContextAsUser == null) {
            Log.e("MiscPolicy", "Could not create context for current user!");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ContentResolver contentResolver = createContextAsUser.getContentResolver();
        int isAdminLockScreenStringSet = isAdminLockScreenStringSet(callingOrCurrentUserId);
        if (isAdminLockScreenStringSet != -1 && isAdminLockScreenStringSet != enforceOwnerOnlyAndSecurityPermission.mCallerUid) {
            Log.d("MiscPolicy", "changeLockScreenString():get AdminId failed!! ");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        if (str == null || str.length() < 0) {
            str2 = "";
            z = true;
        } else {
            str2 = str;
            z = false;
        }
        if (str2.length() > 256) {
            str2 = str2.substring(0, 256) + "...";
            Log.d("MiscPolicy", "changeLockScreenString():lock screen text is truncated ");
        }
        String str4 = str2;
        if (z) {
            Log.d("MiscPolicy", "changeLockScreenString(): revoke restriction");
            putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndSecurityPermission.mCallerUid, 0, 0, "MISC", "lockscreenstring");
            try {
                getLockSettings$1().setString("lock_screen_owner_info", str4, callingOrCurrentUserId);
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "MiscPolicy", String.format("Admin %d has cleared the lock screen string.", Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)), callingOrCurrentUserId);
            } catch (RemoteException e) {
                Log.e("MiscPolicy", "Couldn't write string " + str4 + e);
            }
        } else {
            Log.d("MiscPolicy", "changeLockScreenString(): apply restriction");
            putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndSecurityPermission.mCallerUid, 0, 1, "MISC", "lockscreenstring");
            if (putInt) {
                Log.d("MiscPolicy", "changeLockScreenString(): ret is true set value");
                try {
                    getLockSettings$1().setBoolean("lock_screen_owner_info_enabled", true, callingOrCurrentUserId);
                    getLockSettings$1().setString("lock_screen_owner_info", str4, callingOrCurrentUserId);
                    int myPid = Process.myPid();
                    String format = String.format("Admin %d has changed lock screen string to %s", Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid), str4);
                    str3 = "Couldn't write string ";
                    try {
                        AuditLog.logAsUser(5, 1, true, myPid, "MiscPolicy", format, callingOrCurrentUserId);
                    } catch (RemoteException e2) {
                        e = e2;
                        Log.e("MiscPolicy", str3 + str4 + e);
                        Settings.System.putIntForUser(contentResolver, "my_profile_enabled", 0, callingOrCurrentUserId);
                        boolean z2 = putInt;
                        Log.d("MiscPolicy", "changeLockScreenString():ret:" + z2 + " " + str4);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return z2;
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    str3 = "Couldn't write string ";
                }
                Settings.System.putIntForUser(contentResolver, "my_profile_enabled", 0, callingOrCurrentUserId);
            }
        }
        boolean z22 = putInt;
        Log.d("MiscPolicy", "changeLockScreenString():ret:" + z22 + " " + str4);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z22;
    }

    public final synchronized void clearAllGlobalProxy() {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("globalProxy", Boolean.FALSE);
        new EdmStorageProviderBase(this.mContext).update("RESTRICTION", contentValues, null);
    }

    public final synchronized int clearGlobalProxyEnable(ContextInfo contextInfo) {
        try {
            if (!this.mEdmStorageProvider.getBoolean(contextInfo.mCallerUid, contextInfo.mContainerId, "RESTRICTION", "globalProxy")) {
                return 0;
            }
            if (this.mCon == null) {
                this.mCon = (ConnectivityManager) this.mContext.getSystemService("connectivity");
            }
            if (this.mCon == null) {
                return 0;
            }
            this.mEdmStorageProvider.putBoolean("RESTRICTION", contextInfo.mCallerUid, false, contextInfo.mContainerId, "globalProxy");
            this.mEdmStorageProvider.delete("GlobalProxyAuthTable", null);
            this.mEdmStorageProvider.delete("GlobalProxyTable", null);
            this.mLocalProxyManager.updateGlobalProxy(null);
            this.mLocalProxyManager.clearProxyServerCache();
            ProxyInfo buildDirectProxy = ProxyInfo.buildDirectProxy("", 0);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mCon.setGlobalProxy(buildDirectProxy);
                return 1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (SettingNotFoundException unused) {
            Log.e("MiscPolicy", "clearGlobalProxyEnable.SettingNotFoundException");
            return 0;
        }
    }

    public final int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) {
        return clearGlobalProxyEnable(enforceOwnerOnlyAndFirewallPermission(contextInfo));
    }

    public final int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) {
        return clearGlobalProxyEnable(enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo));
    }

    public final void clearNotificationDialog() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || isCalledFromProxyHandler(callingUid)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LocalProxyManager localProxyManager = this.mLocalProxyManager;
                localProxyManager.getClass();
                Log.d("LocalProxyManager", "Clear notification dialog");
                if (localProxyManager.mNotificationManager != null) {
                    Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda2(localProxyManager, 7));
                } else {
                    Log.d("LocalProxyManager", "NotificationManager is null");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KioskModeService");
            return;
        }
        synchronized (this.mStartNFCHistoryList) {
            try {
                int size = this.mStartNFCHistoryList.size();
                printWriter.println("  mStartNFCHistoryList.size=" + size);
                for (int i = 0; i < size; i++) {
                    printWriter.println("    [" + i + "] " + ((String) this.mStartNFCHistoryList.get(i)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mLocalProxyManager.getClass();
        ProxyProperties globalProxy = LocalProxyManager.getGlobalProxy();
        if (globalProxy != null) {
            StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "\nEnforced GlobalProxy:", "Host: ");
            m$1.append(globalProxy.getHostname());
            printWriter.println(m$1.toString());
            printWriter.println("Port: " + globalProxy.getPortNumber());
            printWriter.println("PAC file url: " + globalProxy.getPacFileUrl());
            printWriter.println("\n");
        }
        ProxyProperties defaultProxy = LocalProxyManager.getDefaultProxy();
        if (defaultProxy != null) {
            StringBuilder m$12 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "Enforced WifiProxy:", "Host: ");
            m$12.append(defaultProxy.getHostname());
            printWriter.println(m$12.toString());
            printWriter.println("Port: " + defaultProxy.getPortNumber());
            printWriter.println("PAC file url: " + defaultProxy.getPacFileUrl());
            printWriter.println("\n\n");
        }
        if (LocalProxyManager.sWifiProxyInfoMapCache != null && !((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache).isEmpty()) {
            ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache).forEach(new BiConsumer() { // from class: com.android.server.enterprise.proxy.LocalProxyManager$$ExternalSyntheticLambda10
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PrintWriter printWriter2 = printWriter;
                    String str = (String) obj;
                    ProxyProperties proxyProperties = (ProxyProperties) obj2;
                    if (str == null || proxyProperties == null) {
                        return;
                    }
                    printWriter2.println("WifiProxy for ssid=".concat(str));
                    printWriter2.println("Host: " + proxyProperties.getHostname());
                    printWriter2.println("Port: " + proxyProperties.getPortNumber());
                    printWriter2.println("PAC file url: " + proxyProperties.getPacFileUrl());
                    printWriter2.println("\n");
                }
            });
        }
        if (this.mAdminsBlockingNfcStateChange.isEmpty()) {
            printWriter.println("  No admin blocking NFC State change");
            return;
        }
        printWriter.println("  NFC State change blocked by admin UID : " + this.mAdminsBlockingNfcStateChange);
    }

    public final void enforceMDMAppCaller() {
        if (this.mContext.checkCallingPermission("com.samsung.android.knox.permission.KNOX_SET_PROXY_CREDENTIAL_INTERNAL") != 0) {
            throw new SecurityException("Caller does not have required permission.");
        }
    }

    public final ContextInfo enforceOwnerOnlyAndFirewallPermission(ContextInfo contextInfo) {
        return getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public final ContextInfo enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(ContextInfo contextInfo) {
        return getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_GLOBALPROXY", "android.permission.NETWORK_STACK")));
    }

    public final ContextInfo enforceOwnerOnlyAndSecurityPermission(ContextInfo contextInfo) {
        return getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final int floatToIndex(float f) {
        float f2 = this.mFontSizes[0];
        int i = 1;
        while (true) {
            float[] fArr = this.mFontSizes;
            if (i >= fArr.length) {
                int length = fArr.length - 1;
                NetworkScoreService$$ExternalSyntheticOutline0.m(length, "floatToIndex(): ", "MiscPolicy");
                return length;
            }
            float f3 = fArr[i];
            if (f < ((f3 - f2) * 0.5f) + f2) {
                return i - 1;
            }
            i++;
            f2 = f3;
        }
    }

    public final List getAppUidBrowserList() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            LocalProxyManager localProxyManager = this.mLocalProxyManager;
            localProxyManager.getClass();
            ArrayList arrayList = new ArrayList();
            Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda0(localProxyManager, arrayList, 1));
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getAppUidFromSocketPortNumber(int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mLocalProxyManager.getClass();
            String appUidFromTcpFile = LocalProxyManager.getAppUidFromTcpFile(i, "/proc/net/tcp");
            if (appUidFromTcpFile == null) {
                appUidFromTcpFile = LocalProxyManager.getAppUidFromTcpFile(i, "/proc/net/tcp6");
            }
            return appUidFromTcpFile;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAuthConfigFromDb() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValues("GlobalProxyAuthTable", new String[]{"host", "port", "username", "password"}, null)).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("host");
            Integer asInteger = contentValues.getAsInteger("port");
            arrayList.add(new AuthConfig(asString, asInteger.intValue(), contentValues.getAsString("username"), contentValues.getAsString("password")));
        }
        return arrayList;
    }

    public final int getCredentialsFails(String str) {
        enforceMDMAppCaller();
        return "credentials_fails_count".equals(str) ? this.credentialsFailsCount : this.credentialsFailsState;
    }

    public final String getCurrentLockScreenString(ContextInfo contextInfo) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            contextInfo = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int isAdminLockScreenStringSet = isAdminLockScreenStringSet(callingOrCurrentUserId);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(isAdminLockScreenStringSet, "getCurrentLockScreenString : currentSetAdminId=", "MiscPolicy");
        String str = null;
        if (isAdminLockScreenStringSet != -1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    str = getLockSettings$1().getString("lock_screen_owner_info", (String) null, callingOrCurrentUserId);
                } catch (RemoteException e) {
                    Log.e("MiscPolicy", "Couldn't get string lock_screen_owner_info" + e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return str;
    }

    public final EnterpriseDeviceManager getEDM$16() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final synchronized ProxyProperties getGlobalProxy() {
        ProxyInfo buildDirectProxy;
        try {
            this.mLocalProxyManager.getClass();
            ProxyProperties globalProxy = LocalProxyManager.getGlobalProxy();
            if (globalProxy != null) {
                return globalProxy;
            }
            List authConfigFromDb = getAuthConfigFromDb();
            Iterator it = ((ArrayList) this.mEdmStorageProvider.getValues("GlobalProxyTable", new String[]{"host", "port", "pacfile", "exclusion"}, null)).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("host");
                Integer asInteger = contentValues.getAsInteger("port");
                int intValue = asInteger == null ? -1 : asInteger.intValue();
                String asString2 = contentValues.getAsString("exclusion");
                String asString3 = contentValues.getAsString("pacfile");
                if (TextUtils.isEmpty(asString) && TextUtils.isEmpty(asString3)) {
                }
                if (TextUtils.isEmpty(asString3)) {
                    List arrayList = new ArrayList();
                    if (!TextUtils.isEmpty(asString2)) {
                        arrayList = Arrays.asList(asString2.split(","));
                    }
                    buildDirectProxy = ProxyInfo.buildDirectProxy(asString, intValue, arrayList);
                } else {
                    buildDirectProxy = ProxyInfo.buildPacProxy(Uri.parse(asString3));
                }
                if (buildDirectProxy.isValid()) {
                    globalProxy = new ProxyProperties();
                    globalProxy.setHostname(asString);
                    globalProxy.setPortNumber(intValue);
                    if (!((ArrayList) authConfigFromDb).isEmpty()) {
                        globalProxy.setAuthConfigList(authConfigFromDb);
                    }
                    globalProxy.setExclusionList(Arrays.asList(buildDirectProxy.getExclusionList()));
                    globalProxy.setPacFileUrl(asString3);
                } else {
                    Log.d("MiscPolicy", "Invalid proxy properties, ignoring: " + buildDirectProxy.toString());
                }
            }
            return globalProxy;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final List getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) {
        enforceOwnerOnlyAndFirewallPermission(contextInfo);
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy == null || globalProxy.getHostname() == null || globalProxy.getHostname().toString().equals("")) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(globalProxy.getHostname().toString().trim() + ":" + Integer.valueOf(globalProxy.getPortNumber()).toString().trim());
        if (globalProxy.getExclusionList() != null) {
            Iterator it = globalProxy.getExclusionList().iterator();
            while (it.hasNext()) {
                arrayList.add((String) it.next());
            }
        }
        return arrayList;
    }

    public final ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) {
        enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo);
        return getGlobalProxy();
    }

    public final SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) {
        getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_INVENTORY")));
        try {
            SimChangeInfo simChangeInfo = new SimChangeInfo();
            String readPropertyValue = Utils.readPropertyValue("SimChangeTime");
            String str = "-1";
            if (readPropertyValue == null) {
                readPropertyValue = "-1";
            }
            simChangeInfo.changeTime = Long.parseLong(readPropertyValue);
            SimInfo simInfo = new SimInfo();
            simChangeInfo.previousSimInfo = simInfo;
            simInfo.countryIso = Utils.readPropertyValue("PreviousSimCountryIso");
            simChangeInfo.previousSimInfo.operatorName = Utils.readPropertyValue("PreviousSimOperatorName");
            simChangeInfo.previousSimInfo.operator = Utils.readPropertyValue("PreviousSimOperator");
            simChangeInfo.previousSimInfo.phoneNumber = Utils.readPropertyValue("PreviousSimPhoneNumber");
            simChangeInfo.previousSimInfo.serialNumber = Utils.readPropertyValue("PreviousSimSerialNumber");
            String readPropertyValue2 = Utils.readPropertyValue("SimChangeOperation");
            if (readPropertyValue2 != null) {
                str = readPropertyValue2;
            }
            simChangeInfo.changeOperation = Integer.parseInt(str);
            SimInfo simInfo2 = new SimInfo();
            simChangeInfo.currentSimInfo = simInfo2;
            simInfo2.countryIso = Utils.readPropertyValue("CurrentSimCountryIso");
            simChangeInfo.currentSimInfo.operatorName = Utils.readPropertyValue("CurrentSimOperatorName");
            simChangeInfo.currentSimInfo.operator = Utils.readPropertyValue("CurrentSimOperator");
            simChangeInfo.currentSimInfo.phoneNumber = Utils.readPropertyValue("CurrentSimPhoneNumber");
            simChangeInfo.currentSimInfo.serialNumber = Utils.readPropertyValue("CurrentSimSerialNumber");
            return simChangeInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return new SimChangeInfo();
        }
    }

    public final ILockSettings getLockSettings$1() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public final ProxyProperties getProxyForSsid(String str) {
        if (Binder.getCallingUid() != 1000) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mLocalProxyManager.getClass();
            return (ProxyProperties) ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache).get(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getSystemActiveFont(com.samsung.android.knox.ContextInfo r7) {
        /*
            r6 = this;
            com.android.server.enterprise.general.MiscPolicy$SystemFontChanger r7 = r6.mSystemFontChanger
            if (r7 != 0) goto Lb
            com.android.server.enterprise.general.MiscPolicy$SystemFontChanger r7 = new com.android.server.enterprise.general.MiscPolicy$SystemFontChanger
            r7.<init>()
            r6.mSystemFontChanger = r7
        Lb:
            com.android.server.enterprise.general.MiscPolicy$SystemFontChanger r6 = r6.mSystemFontChanger
            r6.getClass()
            java.lang.String r7 = "getSystemActiveFont():getting active system font:"
            java.lang.String r0 = "MiscPolicy"
            android.util.Log.i(r0, r7)
            long r1 = android.os.Binder.clearCallingIdentity()
            r7 = 0
            com.android.server.enterprise.general.MiscPolicy r3 = com.android.server.enterprise.general.MiscPolicy.this     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            android.content.Context r3 = r3.mContext     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            java.lang.String r4 = "com.android.settings"
            r5 = 2
            android.content.Context r3 = r3.createPackageContext(r4, r5)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            java.lang.String r4 = "prefs"
            java.io.File r3 = r3.getSharedPrefsFile(r4)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            boolean r4 = r3.canRead()     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            if (r4 == 0) goto L51
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            r3 = 16384(0x4000, float:2.2959E-41)
            r4.<init>(r5, r3)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
            java.util.HashMap r3 = com.android.internal.util.XmlUtils.readMapXml(r4)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            goto L53
        L47:
            r6 = move-exception
            r7 = r4
            goto L9a
        L4a:
            r3 = move-exception
            goto L5c
        L4c:
            r6 = move-exception
            goto L9a
        L4e:
            r3 = move-exception
            r4 = r7
            goto L5c
        L51:
            r3 = r7
            r4 = r3
        L53:
            if (r4 == 0) goto L58
            r4.close()     // Catch: java.io.IOException -> L68
        L58:
            android.os.Binder.restoreCallingIdentity(r1)     // Catch: java.io.IOException -> L68
            goto L68
        L5c:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r4 == 0) goto L64
            r4.close()     // Catch: java.io.IOException -> L67
        L64:
            android.os.Binder.restoreCallingIdentity(r1)     // Catch: java.io.IOException -> L67
        L67:
            r3 = r7
        L68:
            r1 = 0
            if (r3 == 0) goto L79
            java.lang.String r2 = "SavedClickedItem"
            java.lang.Object r2 = r3.get(r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            if (r2 == 0) goto L79
            int r1 = r2.intValue()
        L79:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "getActiveFontPosition():"
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.i(r0, r2)
            java.lang.String r2 = "getSystemFonts():getting all system fonts"
            android.util.Log.i(r0, r2)
            java.lang.String[] r6 = r6.getFontString()
            if (r6 == 0) goto L99
            r7 = r6[r1]
        L99:
            return r7
        L9a:
            if (r7 == 0) goto L9f
            r7.close()     // Catch: java.io.IOException -> La2
        L9f:
            android.os.Binder.restoreCallingIdentity(r1)     // Catch: java.io.IOException -> La2
        La2:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.general.MiscPolicy.getSystemActiveFont(com.samsung.android.knox.ContextInfo):java.lang.String");
    }

    public final float getSystemActiveFontSize(ContextInfo contextInfo) {
        Configuration configuration = new Configuration();
        configuration.fontScale = FullScreenMagnificationGestureHandler.MAX_SCALE;
        try {
            configuration.updateFrom(ActivityManagerNative.getDefault().getConfiguration());
            retrieveSystemFontSizes();
            if (this.mFontSizes != null) {
                floatToIndex(configuration.fontScale);
                int i = Settings.Global.getInt(this.mContext.getContentResolver(), "font_size", 0);
                if (i >= this.mFontSizes.length) {
                    StringBuilder sb = new StringBuilder("getSystemActiveFontSize() : resized font index value. ");
                    sb.append(i);
                    sb.append(" -> ");
                    sb.append(this.mFontSizes.length - 1);
                    Log.d("MiscPolicy", sb.toString());
                    i = this.mFontSizes.length - 1;
                }
                configuration.fontScale = this.mFontSizes[i];
            }
        } catch (Exception e) {
            Log.e("MiscPolicy", "getSystemActiveFontSize(): Unable to read font size", e);
        }
        return configuration.fontScale;
    }

    public final float[] getSystemFontSizes(ContextInfo contextInfo) {
        retrieveSystemFontSizes();
        return this.mFontSizes;
    }

    public final String[] getSystemFonts(ContextInfo contextInfo) {
        if (this.mSystemFontChanger == null) {
            this.mSystemFontChanger = new SystemFontChanger();
        }
        SystemFontChanger systemFontChanger = this.mSystemFontChanger;
        systemFontChanger.getClass();
        Log.i("MiscPolicy", "getSystemFonts():getting all system fonts");
        return systemFontChanger.getFontString();
    }

    public final int isAdminLockScreenStringSet(int i) {
        try {
            Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, i, "MISC", new String[]{"adminUid", "lockscreenstring"})).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                Integer asInteger = contentValues.getAsInteger("lockscreenstring");
                if (asInteger != null && asInteger.intValue() == 1) {
                    return (int) contentValues.getAsLong("adminUid").longValue();
                }
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public final boolean isCalledFromProxyHandler(int i) {
        for (String str : mPackageManager.getPackagesForUid(i)) {
            if (str.equals("com.android.proxyhandler")) {
                try {
                    if ((this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0) {
                        Log.d("MiscPolicy", "Allowing Proxy Handler access");
                        return true;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    continue;
                }
            }
        }
        return false;
    }

    public final boolean isGlobalProxyAllowed() {
        return !this.mEdmStorageProvider.getBooleanListAsUser(0, "RESTRICTION", "globalProxy").contains(Boolean.TRUE);
    }

    public final boolean isNFCStarted() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = NfcAdapter.getDefaultAdapter(this.mContext).isEnabled();
            } catch (Exception unused) {
                Log.w("MiscPolicy", "Error on isNFCStarted");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isNFCStateChangeAllowed() {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "MISC", "nfcStateChangeAllowed").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0069, code lost:
    
        android.util.Log.d("MiscPolicy", "isRingToneEntryExist : return " + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x007b, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
    
        if (r7 == null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long isRingToneEntryExist(android.net.Uri r16, java.lang.String r17) {
        /*
            r15 = this;
            java.lang.String r1 = "MiscPolicy"
            java.lang.String r0 = "isRingToneEntryExist : whereClause :"
            java.lang.String r2 = "_data='"
            long r3 = android.os.Binder.clearCallingIdentity()
            r5 = -1
            r7 = 0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r8.<init>(r2)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r2 = r17
            r8.append(r2)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.lang.String r2 = "'"
            r8.append(r2)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.lang.String r12 = r8.toString()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r2.append(r12)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            android.util.Log.d(r1, r0)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r0 = r15
            android.content.Context r0 = r0.mContext     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            android.content.ContentResolver r9 = r0.getContentResolver()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.lang.String r0 = "_id"
            java.lang.String[] r11 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r13 = 0
            r14 = 0
            r10 = r16
            android.database.Cursor r7 = r9.query(r10, r11, r12, r13, r14)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            if (r7 == 0) goto L5a
            int r0 = r7.getCount()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            if (r0 <= 0) goto L5a
            r7.moveToFirst()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r0 = 0
            long r5 = r7.getLong(r0)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            goto L5a
        L56:
            r0 = move-exception
            goto L7c
        L58:
            r0 = move-exception
            goto L63
        L5a:
            if (r7 == 0) goto L5f
        L5c:
            r7.close()
        L5f:
            android.os.Binder.restoreCallingIdentity(r3)
            goto L69
        L63:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L56
            if (r7 == 0) goto L5f
            goto L5c
        L69:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "isRingToneEntryExist : return "
            r0.<init>(r2)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
            return r5
        L7c:
            if (r7 == 0) goto L81
            r7.close()
        L81:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.general.MiscPolicy.isRingToneEntryExist(android.net.Uri, java.lang.String):long");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor$2(callingOrCurrentUserId);
        }
        updateAdminsBlockingNfcStateChange();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        if (isAdminLockScreenStringSet(UserHandle.getUserId(i)) == i) {
            try {
                getLockSettings$1().setString("lock_screen_owner_info", "", UserHandle.getUserId(i));
            } catch (RemoteException e) {
                NetdService$$ExternalSyntheticOutline0.m("Couldn't write string ", e, "MiscPolicy");
            }
        }
        if (UserHandle.getUserId(i) == 0) {
            clearGlobalProxyEnable(new ContextInfo(i, 0));
        }
    }

    public final void refreshCredentialsDialogFails() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Binder.getCallingUid() != 1000) {
                return;
            }
            LocalProxyManager localProxyManager = this.mLocalProxyManager;
            localProxyManager.getClass();
            Log.d("LocalProxyManager", "Refresh proxy credentials dialog");
            if (localProxyManager.mContext != null) {
                Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda2(localProxyManager, 5));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ProxyProperties retrieveExternalProxy() {
        int callingUid = Binder.getCallingUid();
        ProxyProperties proxyProperties = null;
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mLocalProxyManager.getClass();
            ProxyProperties defaultProxy = LocalProxyManager.getDefaultProxy();
            ProxyProperties globalProxy = LocalProxyManager.getGlobalProxy();
            if (globalProxy != null) {
                Log.d("LocalProxyManager", "Get current applied global proxy");
                proxyProperties = globalProxy;
            } else if (defaultProxy != null) {
                Log.d("LocalProxyManager", "Get current applied default proxy");
                proxyProperties = defaultProxy;
            }
            return proxyProperties;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String retrieveProxyCredentials(String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getProxyCredentials(i, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void retrieveSystemFontSizes() {
        Context createPackageContext;
        if (this.mFontSizes != null) {
            return;
        }
        Log.i("MiscPolicy", "retrieveSystemFontSizes(): start to retrieve system font sizes.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                createPackageContext = this.mContext.createPackageContext(KnoxCustomManagerService.SETTING_PKG_NAME, 2);
            } catch (Exception e) {
                Log.e("MiscPolicy", "retrieveSystemFontSizes() failed: unexpected exception. ", e);
            }
            if (createPackageContext == null) {
                Log.i("MiscPolicy", "retrieveSystemFontSizes(): failed because Setting Context is null");
                return;
            }
            Resources resources = createPackageContext.getResources();
            int identifier = supportBigFont(createPackageContext) ? resources.getIdentifier("sec_entryvalues_big_font_size", "array", KnoxCustomManagerService.SETTING_PKG_NAME) : resources.getIdentifier("sec_entryvalues_8_step_font_size", "array", KnoxCustomManagerService.SETTING_PKG_NAME);
            if (identifier == 0) {
                Log.i("MiscPolicy", "retrieveSystemFontSizes() : failed to get resource ID. ");
                return;
            }
            String[] stringArray = resources.getStringArray(identifier);
            this.mFontSizes = new float[stringArray.length];
            int i = 0;
            while (true) {
                float[] fArr = this.mFontSizes;
                if (i >= fArr.length) {
                    break;
                }
                fArr[i] = Float.parseFloat(stringArray[i]);
                i++;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean saveAuthConfigToDb(int i, ProxyProperties proxyProperties) {
        boolean z = true;
        if (proxyProperties.isAuthenticationConfigured()) {
            for (AuthConfig authConfig : proxyProperties.getAuthConfigList()) {
                EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
                ContentValues contentValues = new ContentValues();
                contentValues.put("adminUid", Integer.valueOf(i));
                contentValues.put("host", authConfig.getHost());
                contentValues.put("port", Integer.valueOf(authConfig.getPort()));
                contentValues.put("username", authConfig.getUsername());
                contentValues.put("password", authConfig.getPassword());
                if (edmStorageProvider.insert("GlobalProxyAuthTable", contentValues) == -1) {
                    z = false;
                }
            }
        }
        return z;
    }

    public final boolean saveProxyInfoToDb(int i, ProxyInfo proxyInfo) {
        String[] exclusionList;
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        ContentValues contentValues = new ContentValues();
        String str = "";
        String host = !TextUtils.isEmpty(proxyInfo.getHost()) ? proxyInfo.getHost() : "";
        String uri = proxyInfo.getPacFileUrl() != null ? proxyInfo.getPacFileUrl().toString() : "";
        if (proxyInfo.getExclusionList() != null && (exclusionList = proxyInfo.getExclusionList()) != null && exclusionList.length > 0) {
            str = String.join(",", exclusionList);
        }
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(i, contentValues, "adminUid", "host", host);
        contentValues.put("port", Integer.valueOf(proxyInfo.getPort()));
        contentValues.put("pacfile", uri);
        contentValues.put("exclusion", str);
        return edmStorageProvider.insert("GlobalProxyTable", contentValues) != -1;
    }

    public final void setCredentialsFails(String str, int i) {
        enforceMDMAppCaller();
        if ("credentials_fails_count".equals(str)) {
            this.credentialsFailsCount = i;
        } else {
            this.credentialsFailsState = i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
    
        if (r13.mEdmStorageProvider.getBoolean(r14.mCallerUid, r14.mContainerId, "RESTRICTION", "globalProxy") != false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int setGlobalProxy(com.samsung.android.knox.ContextInfo r14, com.samsung.android.knox.net.ProxyProperties r15) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.general.MiscPolicy.setGlobalProxy(com.samsung.android.knox.ContextInfo, com.samsung.android.knox.net.ProxyProperties):int");
    }

    public final int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List list) {
        ContextInfo enforceOwnerOnlyAndFirewallPermission = enforceOwnerOnlyAndFirewallPermission(contextInfo);
        ProxyProperties proxyProperties = new ProxyProperties();
        proxyProperties.setHostname(str);
        proxyProperties.setPortNumber(i);
        proxyProperties.setExclusionList(list);
        return setGlobalProxy(enforceOwnerOnlyAndFirewallPermission, proxyProperties);
    }

    public final int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) {
        return setGlobalProxy(enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo), proxyProperties);
    }

    public final void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) {
        if (Binder.getCallingUid() != 1000) {
            try {
                enforceMDMAppCaller();
            } catch (SecurityException unused) {
                return;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            LocalProxyManager localProxyManager = this.mLocalProxyManager;
            localProxyManager.getClass();
            Log.d("LocalProxyManager", "Set proxy credentials callback to proxy server");
            Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda7(localProxyManager, iProxyCredentialsCallback, bundle));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) {
        int callingUid = Binder.getCallingUid();
        if (Utils.isPlatformSignedApp(this.mContext, this.mEdmStorageProvider.getPackageNameForUid(callingUid), UserHandle.getUserId(callingUid))) {
            try {
                enforceOwnerOnlyAndSecurityPermission(contextInfo);
            } catch (SecurityException e) {
                Log.d("MiscPolicy", "MDM_SECURITY Permission is not granted - Check for WRITE_SETTINGS permission " + e.getMessage());
                getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, "android.permission.WRITE_SETTINGS");
            }
        } else {
            enforceOwnerOnlyAndSecurityPermission(contextInfo);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                long isRingToneEntryExist = isRingToneEntryExist(uri, str);
                if (-1 != isRingToneEntryExist) {
                    Log.d("MiscPolicy", "Ringtone entry exist deleting it :" + isRingToneEntryExist);
                    this.mContext.getContentResolver().delete(ContentUris.withAppendedId(uri, isRingToneEntryExist), null, null);
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("_data", str);
                contentValues.put(KnoxCustomManagerService.SHORTCUT_TITLE, "IT Admin tone");
                contentValues.put("_size", Long.valueOf(j));
                contentValues.put("mime_type", "audio/*");
                contentValues.put("artist", "artist");
                contentValues.put("is_ringtone", Boolean.TRUE);
                Boolean bool = Boolean.FALSE;
                contentValues.put("is_notification", bool);
                contentValues.put("is_alarm", bool);
                contentValues.put("is_music", bool);
                uri = this.mContext.getContentResolver().insert(uri, contentValues);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (uri != null) {
                long clearCallingIdentity2 = Binder.clearCallingIdentity();
                RingtoneManager.setActualDefaultRingtoneUri(this.mContext, 1, uri);
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) {
        Typeface typeface;
        long clearCallingIdentity;
        Process exec;
        String[] strArr;
        File file;
        enforceOwnerOnlyAndSecurityPermission(contextInfo);
        boolean z = false;
        if (str2 != null) {
            return false;
        }
        if (this.mSystemFontChanger == null) {
            this.mSystemFontChanger = new SystemFontChanger();
        }
        SystemFontChanger systemFontChanger = this.mSystemFontChanger;
        systemFontChanger.getClass();
        Log.i("MiscPolicy", "setSystemActiveFont():Start");
        if (str == null) {
            Log.i("MiscPolicy", "setSystemActiveFont():Invalid input");
            return false;
        }
        FontWriter fontWriter = new FontWriter();
        File file2 = null;
        fontWriter.fOut = null;
        fontWriter.osw = null;
        fontWriter.bos = null;
        MiscPolicy miscPolicy = MiscPolicy.this;
        PackageManager packageManager = miscPolicy.mContext.getPackageManager();
        String[] fontString = systemFontChanger.getFontString();
        if (fontString == null) {
            Log.i("MiscPolicy", "changeFont():Installed font list is null");
        } else {
            int i = 0;
            while (true) {
                if (i >= fontString.length) {
                    i = 0;
                    break;
                }
                if (fontString[i].equalsIgnoreCase(str)) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(i, LauncherConfigurationInternal.KEY_INDEX_INT, "MiscPolicy");
                    break;
                }
                i++;
            }
            if (str.equalsIgnoreCase("default")) {
                fontWriter.writeLoc("default#default");
                systemFontChanger.savePreferences(i, fontString[i]);
                Log.i("MiscPolicy", "default font is selected..." + i);
                z = true;
            } else {
                TypefaceFinder typefaceFinder = systemFontChanger.mTypefaceFinder;
                typefaceFinder.getClass();
                Log.i("TypefaceFinder", "findMatchingTypefaceByName:".concat(str));
                int i2 = 0;
                while (true) {
                    if (i2 >= ((ArrayList) typefaceFinder.mTypefaces).size()) {
                        typeface = null;
                        break;
                    }
                    typeface = (Typeface) ((ArrayList) typefaceFinder.mTypefaces).get(i2);
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("findMatchingTypeface:"), typeface.mName, "TypefaceFinder");
                    if (typeface.mName.equalsIgnoreCase(str)) {
                        break;
                    }
                    i2++;
                }
                if (typeface == null) {
                    Log.i("MiscPolicy", "change font failed");
                } else {
                    String str3 = typeface.mFontPackageName;
                    if (str3 == null || str3.startsWith("com.monotype.android.font.")) {
                        String replaceAll = typeface.mTypefaceFilename.replaceAll(".xml", "").replaceAll(" ", PackageManagerShellCommandDataLoader.STDIN_PATH);
                        Log.i("FontWriter", "createFontDirectory : Start");
                        File file3 = new File("/data/app_fonts/" + String.valueOf(UserHandle.myUserId()));
                        file3.mkdir();
                        file3.setReadable(true, false);
                        file3.setWritable(true, false);
                        file3.setExecutable(true, false);
                        File file4 = new File(file3, replaceAll);
                        String[] list = file3.list();
                        if (list != null) {
                            int i3 = 0;
                            while (i3 < list.length) {
                                File file5 = new File(file3, list[i3]);
                                String[] list2 = file5.list();
                                if (list2 != null) {
                                    strArr = list;
                                    int i4 = 0;
                                    while (i4 < list2.length) {
                                        new File(file5, list2[i4]).delete();
                                        i4++;
                                        file3 = file3;
                                    }
                                    file = file3;
                                    file5.delete();
                                } else {
                                    strArr = list;
                                    file = file3;
                                }
                                i3++;
                                list = strArr;
                                file3 = file;
                            }
                            if (file4.mkdir()) {
                                Log.i("FontWriter", "Font directory  : Created");
                            } else {
                                Log.i("FontWriter", "Font directory  : Not Created");
                            }
                            try {
                                exec = Runtime.getRuntime().exec("chmod 711 " + file4.getAbsolutePath());
                                exec.waitFor();
                            } catch (IOException unused) {
                                Log.i("FontWriter", "IOException : ");
                            } catch (InterruptedException unused2) {
                                Log.i("FontWriter", "InterruptedException : ");
                            }
                            if (exec.exitValue() != 0) {
                                throw new IOException("Cannot chmod");
                            }
                            file2 = file4;
                        }
                        if (file2 == null) {
                            Log.e("MiscPolicy", "create fontDir object is null ");
                        } else {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            for (int i5 = 0; i5 < ((ArrayList) typeface.mSansFonts).size(); i5++) {
                                TypefaceFile typefaceFile = (TypefaceFile) ((ArrayList) typeface.mSansFonts).get(i5);
                                try {
                                    fontWriter.copyFontFile(file2, packageManager.getResourcesForApplication(str3).getAssets().open("fonts/" + typefaceFile.fileName), typefaceFile.droidName);
                                } catch (Exception e) {
                                    Log.i("MiscPolicy", "changeFont():Exception");
                                    Log.i("MiscPolicy", "Exception" + e);
                                    e.printStackTrace();
                                }
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            fontWriter.writeLoc(file2.getAbsolutePath());
                            systemFontChanger.savePreferences(i, typeface.mTypefaceFilename);
                            Log.i("MiscPolicy", "change font:Done");
                            z = true;
                        }
                        z = false;
                    }
                }
            }
        }
        if (z) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                Configuration configuration = iActivityManager.getConfiguration();
                configuration.FlipFont = Math.abs(str.hashCode()) + 1;
                iActivityManager.updateConfiguration(configuration);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManager activityManager = (ActivityManager) miscPolicy.mContext.getSystemService("activity");
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(50);
                if (runningTasks != null) {
                    Iterator<ActivityManager.RunningTaskInfo> it = runningTasks.iterator();
                    while (it.hasNext()) {
                        activityManager.restartPackage(it.next().baseActivity.getPackageName());
                    }
                }
                return z;
            } catch (Exception unused3) {
                Log.i("MiscPolicy", "setSystemActiveFont():Exception");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public final boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) {
        enforceOwnerOnlyAndSecurityPermission(contextInfo);
        retrieveSystemFontSizes();
        if (this.mFontSizes == null) {
            Log.i("MiscPolicy", "setSystemActiveFontSize() : failed to retrieve font datas.");
            return false;
        }
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "font_size", 0);
        int floatToIndex = floatToIndex(f);
        int length = this.mFontSizes.length - 1;
        if (i == floatToIndex) {
            Log.w("MiscPolicy", "setSystemActiveFontSize() : same font size");
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "font_size", floatToIndex);
            if (i < length && floatToIndex == length) {
                Intent intent = new Intent("android.settings.FONT_SIZE_CHANGED");
                intent.addFlags(16777216);
                intent.putExtra("large_font", 1);
                this.mContext.sendBroadcast(intent);
            } else if (i == length && floatToIndex < length) {
                Intent intent2 = new Intent("android.settings.FONT_SIZE_CHANGED");
                intent2.addFlags(16777216);
                intent2.putExtra("large_font", 0);
                this.mContext.sendBroadcast(intent2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Configuration configuration = new Configuration();
            configuration.fontScale = this.mFontSizes[floatToIndex];
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityManagerNative.getDefault().updatePersistentConfiguration(configuration);
                Settings.Global.putInt(this.mContext.getContentResolver(), "font_size", floatToIndex);
                if (floatToIndex >= 7) {
                    Settings.Global.putInt(this.mContext.getContentResolver(), "accessiblity_font_switch", 1);
                }
                return true;
            } catch (Exception e) {
                Log.e("MiscPolicy", "setSystemActiveFontSize(): failed to set font size. ", e);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void showCredentialsDialogNotification(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || isCalledFromProxyHandler(callingUid)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LocalProxyManager localProxyManager = this.mLocalProxyManager;
                localProxyManager.getClass();
                Log.d("LocalProxyManager", "Show credentials dialog notification");
                if (localProxyManager.mContext != null) {
                    if (localProxyManager.mNotificationManager != null) {
                        Binder.withCleanCallingIdentity(new LocalProxyManager$$ExternalSyntheticLambda5(localProxyManager, str, 1));
                    } else {
                        Log.d("LocalProxyManager", "NotificationManager is null");
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean startNFC(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission = getEDM$16().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
        this.mStartNFCHistoryList.add(FORMAT.format(new Date(System.currentTimeMillis())) + " callerUid=" + enforceOwnerOnlyAndActiveAdminPermission.mCallerUid + " value=" + z);
        boolean z2 = false;
        if (this.mStartNFCHistoryList.size() > 10) {
            this.mStartNFCHistoryList.remove(0);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
                z2 = z ? defaultAdapter.enable() : defaultAdapter.disable();
            } catch (Exception unused) {
                Log.w("MiscPolicy", "Error on Start/Stop NFC");
            }
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        updateAdminsBlockingNfcStateChange();
    }

    public final void updateAdminsBlockingNfcStateChange() {
        Integer asInteger;
        this.mAdminsBlockingNfcStateChange = new ArrayList();
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, 0, "MISC", new String[]{"nfcStateChangeAllowed", "adminUid"})).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (!(contentValues.getAsBoolean("nfcStateChangeAllowed") == null ? true : contentValues.getAsBoolean("nfcStateChangeAllowed").booleanValue()) && (asInteger = contentValues.getAsInteger("adminUid")) != null) {
                this.mAdminsBlockingNfcStateChange.add(asInteger);
            }
        }
    }

    public final void updateSystemUIMonitor$2(int i) {
        boolean isNFCStateChangeAllowed = isNFCStateChangeAllowed();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setNFCStateChangeAllowedAsUser(i, isNFCStateChangeAllowed);
            } catch (Exception e) {
                Log.e("MiscPolicy", "setNFCStateChangeAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
