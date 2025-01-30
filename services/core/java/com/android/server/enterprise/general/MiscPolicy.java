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
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.ProxyInfo;
import android.net.Uri;
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
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.internal.widget.ILockSettings;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.general.font.FontWriter;
import com.android.server.enterprise.general.font.Typeface;
import com.android.server.enterprise.general.font.TypefaceFile;
import com.android.server.enterprise.general.font.TypefaceFinder;
import com.android.server.enterprise.proxy.LocalProxyManager;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.NetworkUtils;
import com.android.server.enterprise.utils.Utils;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.deviceinfo.SimChangeInfo;
import com.samsung.android.knox.deviceinfo.SimInfo;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/* loaded from: classes2.dex */
public class MiscPolicy extends IMiscPolicy.Stub implements EnterpriseServiceCallback {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public static PackageManager mPackageManager;
    public final String ACTION_USER_ADDED;
    public final int MAX_PORT_NUMBER;
    public final int MIN_PORT_NUMBER;
    public int credentialsFailsCount;
    public int credentialsFailsState;
    public ArrayList mAdminsBlockingNfcStateChange;
    public ConnectivityManager mCon;
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public float[] mFontSizes;
    public LocalProxyManager mLocalProxyManager;
    public ILockSettings mLockSettingsService;
    public SIMCardUpdateMonitor mSIMInfo;
    public ArrayList mStartNFCHistoryList;
    public SystemFontChanger mSystemFontChanger;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }
    }

    public MiscPolicy(Context context) {
        this(new Injector(context));
    }

    public MiscPolicy(Injector injector) {
        this.mEDM = null;
        this.mFontSizes = null;
        this.MIN_PORT_NUMBER = 0;
        this.MAX_PORT_NUMBER = GnssNative.GNSS_AIDING_TYPE_ALL;
        this.mLocalProxyManager = null;
        this.ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
        this.credentialsFailsCount = 0;
        this.credentialsFailsState = 0;
        this.mStartNFCHistoryList = new ArrayList();
        this.mContext = injector.mContext;
        this.mEdmStorageProvider = injector.getStorageProvider();
        this.mSIMInfo = new SIMCardUpdateMonitor(this.mContext);
        this.mLocalProxyManager = LocalProxyManager.getInstance(this.mContext);
        mPackageManager = this.mContext.getPackageManager();
        initializeGlobalProxyCache();
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyAndWriteSettingsPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, "android.permission.WRITE_SETTINGS");
    }

    public final ContextInfo enforceOwnerOnlyAndHwPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_HW_CONTROL")));
    }

    public final ContextInfo enforceOwnerOnlyAndSecurityPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_SECURITY")));
    }

    public final ContextInfo enforceOwnerOnlyAndOldSecurityOrNewInventoryPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_INVENTORY")));
    }

    public final ContextInfo enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_GLOBALPROXY", "android.permission.NETWORK_STACK")));
    }

    public final ContextInfo enforceOwnerOnlyAndFirewallPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public void setCredentialsFails(String str, int i) {
        enforceMDMAppCaller();
        if ("credentials_fails_count".equals(str)) {
            this.credentialsFailsCount = i;
        } else {
            this.credentialsFailsState = i;
        }
    }

    public int getCredentialsFails(String str) {
        enforceMDMAppCaller();
        if ("credentials_fails_count".equals(str)) {
            return this.credentialsFailsCount;
        }
        return this.credentialsFailsState;
    }

    public void setRingerBytes(ContextInfo contextInfo, Uri uri, String str, long j, String str2) {
        ContextInfo enforceOwnerOnlyAndWriteSettingsPermission;
        int callingUid = Binder.getCallingUid();
        if (Utils.isPlatformSignedApp(this.mContext, this.mEdmStorageProvider.getPackageNameForUid(callingUid), UserHandle.getUserId(callingUid))) {
            try {
                enforceOwnerOnlyAndWriteSettingsPermission = enforceOwnerOnlyAndSecurityPermission(contextInfo);
            } catch (SecurityException e) {
                Log.d("MiscPolicy", "MDM_SECURITY Permission is not granted - Check for WRITE_SETTINGS permission " + e.getMessage());
                enforceOwnerOnlyAndWriteSettingsPermission = enforceOwnerOnlyAndWriteSettingsPermission(contextInfo);
            }
        } else {
            enforceOwnerOnlyAndWriteSettingsPermission = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        }
        updateDatabase(enforceOwnerOnlyAndWriteSettingsPermission, uri, str, j, str2);
    }

    public final void updateDatabase(ContextInfo contextInfo, Uri uri, String str, long j, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                long isRingToneEntryExist = isRingToneEntryExist(contextInfo, str, uri);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (uri != null) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                RingtoneManager.setActualDefaultRingtoneUri(this.mContext, 1, uri);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0058, code lost:
    
        if (r4 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0066, code lost:
    
        android.os.Binder.restoreCallingIdentity(r0);
        android.util.Log.d("MiscPolicy", "isRingToneEntryExist : return " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x007d, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0061, code lost:
    
        if (r4 == null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long isRingToneEntryExist(ContextInfo contextInfo, String str, Uri uri) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        long j = -1;
        Cursor cursor = null;
        try {
            try {
                String str2 = "_data='" + str + "'";
                Log.d("MiscPolicy", "isRingToneEntryExist : whereClause :" + str2);
                cursor = this.mContext.getContentResolver().query(uri, new String[]{KnoxCustomManagerService.f1773ID}, str2, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    j = cursor.getLong(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public SimChangeInfo getLastSimChangeInfo(ContextInfo contextInfo) {
        enforceOwnerOnlyAndOldSecurityOrNewInventoryPermission(contextInfo);
        try {
            SimChangeInfo simChangeInfo = new SimChangeInfo();
            String readPropertyValue = Utils.readPropertyValue("SimChangeTime", "/data/system/SimCard.dat");
            String str = "-1";
            if (readPropertyValue == null) {
                readPropertyValue = "-1";
            }
            simChangeInfo.changeTime = Long.parseLong(readPropertyValue);
            SimInfo simInfo = new SimInfo();
            simChangeInfo.previousSimInfo = simInfo;
            simInfo.countryIso = Utils.readPropertyValue("PreviousSimCountryIso", "/data/system/SimCard.dat");
            simChangeInfo.previousSimInfo.operatorName = Utils.readPropertyValue("PreviousSimOperatorName", "/data/system/SimCard.dat");
            simChangeInfo.previousSimInfo.operator = Utils.readPropertyValue("PreviousSimOperator", "/data/system/SimCard.dat");
            simChangeInfo.previousSimInfo.phoneNumber = Utils.readPropertyValue("PreviousSimPhoneNumber", "/data/system/SimCard.dat");
            simChangeInfo.previousSimInfo.serialNumber = Utils.readPropertyValue("PreviousSimSerialNumber", "/data/system/SimCard.dat");
            String readPropertyValue2 = Utils.readPropertyValue("SimChangeOperation", "/data/system/SimCard.dat");
            if (readPropertyValue2 != null) {
                str = readPropertyValue2;
            }
            simChangeInfo.changeOperation = Integer.parseInt(str);
            SimInfo simInfo2 = new SimInfo();
            simChangeInfo.currentSimInfo = simInfo2;
            simInfo2.countryIso = Utils.readPropertyValue("CurrentSimCountryIso", "/data/system/SimCard.dat");
            simChangeInfo.currentSimInfo.operatorName = Utils.readPropertyValue("CurrentSimOperatorName", "/data/system/SimCard.dat");
            simChangeInfo.currentSimInfo.operator = Utils.readPropertyValue("CurrentSimOperator", "/data/system/SimCard.dat");
            simChangeInfo.currentSimInfo.phoneNumber = Utils.readPropertyValue("CurrentSimPhoneNumber", "/data/system/SimCard.dat");
            simChangeInfo.currentSimInfo.serialNumber = Utils.readPropertyValue("CurrentSimSerialNumber", "/data/system/SimCard.dat");
            return simChangeInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return new SimChangeInfo();
        }
    }

    public class SIMCardUpdateMonitor {
        public final Context mCtxt;
        public final BroadcastReceiver mReceiver;
        public TelephonyManager mTelephonyManager;
        public String TAG = "SIMCardUpdateMonitor ";
        public final BroadcastReceiver mIntentReceiver = new SIMCardBroadcastReceiver();

        public SIMCardUpdateMonitor(Context context) {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.general.MiscPolicy.SIMCardUpdateMonitor.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                        MiscPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                    }
                }
            };
            this.mReceiver = broadcastReceiver;
            this.mCtxt = context;
            this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
            startSIMCardUpdates();
            context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"));
        }

        public final void saveSimState() {
            String readPropertyValue = Utils.readPropertyValue("CurrentSimSerialNumber", "/data/system/SimCard.dat");
            if (readPropertyValue == null || this.mTelephonyManager.getSimSerialNumber() == null || !this.mTelephonyManager.getSimSerialNumber().equalsIgnoreCase(readPropertyValue)) {
                Utils.writePropertyValue("PreviousSimCountryIso", Utils.readPropertyValue("CurrentSimCountryIso", "/data/system/SimCard.dat"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimOperator", Utils.readPropertyValue("CurrentSimOperator", "/data/system/SimCard.dat"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimOperatorName", Utils.readPropertyValue("CurrentSimOperatorName", "/data/system/SimCard.dat"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimSerialNumber", Utils.readPropertyValue("CurrentSimSerialNumber", "/data/system/SimCard.dat"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("PreviousSimPhoneNumber", Utils.readPropertyValue("CurrentSimPhoneNumber", "/data/system/SimCard.dat"), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimCountryIso", this.mTelephonyManager.getSimCountryIso(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimOperator", this.mTelephonyManager.getSimOperator(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimOperatorName", this.mTelephonyManager.getSimOperatorName(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimSerialNumber", this.mTelephonyManager.getSimSerialNumber(), "/data/system/SimCard.dat");
                Utils.writePropertyValue("CurrentSimPhoneNumber", this.mTelephonyManager.getLine1Number(), "/data/system/SimCard.dat");
                FileUtils.setPermissions(new File("/data/system/SimCard.dat").getAbsolutePath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
            }
        }

        public final void startSIMCardUpdates() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
            this.mCtxt.registerReceiver(this.mIntentReceiver, intentFilter);
        }

        public class SIMCardBroadcastReceiver extends BroadcastReceiver {
            public SIMCardBroadcastReceiver() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    String action = intent.getAction();
                    Log.d(SIMCardUpdateMonitor.this.TAG, " action is : " + action);
                    if ("android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                        String stringExtra = intent.getStringExtra("ss");
                        Log.d(SIMCardUpdateMonitor.this.TAG, " state Extra is : " + stringExtra);
                        if ("ABSENT".equals(stringExtra)) {
                            Log.d(SIMCardUpdateMonitor.this.TAG, " SIM Card State :" + IccCardConstants.State.ABSENT.name());
                            Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                            Utils.writePropertyValue("SimChangeOperation", "1", "/data/system/SimCard.dat");
                            FileUtils.setPermissions(new File("/data/system/SimCard.dat").getAbsolutePath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
                            Intent intent2 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                            intent2.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                            SIMCardUpdateMonitor.this.mCtxt.sendBroadcast(intent2, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                            return;
                        }
                        if ("LOADED".equals(stringExtra)) {
                            Log.d(SIMCardUpdateMonitor.this.TAG, " SIM Card State : LOADED");
                            SIMCardUpdateMonitor.this.saveSimState();
                            Utils.writePropertyValue("SimChangeTime", System.currentTimeMillis() + "", "/data/system/SimCard.dat");
                            String readPropertyValue = Utils.readPropertyValue("PreviousSimSerialNumber", "/data/system/SimCard.dat");
                            String readPropertyValue2 = Utils.readPropertyValue("CurrentSimSerialNumber", "/data/system/SimCard.dat");
                            if (readPropertyValue != null && !readPropertyValue.equalsIgnoreCase(readPropertyValue2)) {
                                Utils.writePropertyValue("SimChangeOperation", "2", "/data/system/SimCard.dat");
                            } else {
                                Utils.writePropertyValue("SimChangeOperation", "3", "/data/system/SimCard.dat");
                            }
                            Intent intent3 = new Intent("com.samsung.android.knox.intent.action.SIM_CARD_CHANGED");
                            intent3.putExtra("com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO", (Parcelable) MiscPolicy.this.getLastSimChangeInfo(new ContextInfo(Binder.getCallingUid())));
                            SIMCardUpdateMonitor.this.mCtxt.sendBroadcast(intent3, "com.samsung.android.knox.permission.KNOX_INVENTORY");
                        }
                    }
                } catch (Exception e) {
                    Log.w(SIMCardUpdateMonitor.this.TAG, "SIMCardBroadcastReceiver Ex:" + e);
                }
            }
        }
    }

    public final ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public String getCurrentLockScreenString(ContextInfo contextInfo) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            contextInfo = enforceOwnerOnlyAndSecurityPermission(contextInfo);
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int isAdminLockScreenStringSet = isAdminLockScreenStringSet(callingOrCurrentUserId);
        Log.e("MiscPolicy", "getCurrentLockScreenString : currentSetAdminId=" + isAdminLockScreenStringSet);
        String str = null;
        if (isAdminLockScreenStringSet != -1) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    str = getLockSettings().getString("lock_screen_owner_info", (String) null, callingOrCurrentUserId);
                } catch (RemoteException e) {
                    Log.e("MiscPolicy", "Couldn't get string lock_screen_owner_info" + e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return str;
    }

    public final int isAdminLockScreenStringSet(int i) {
        try {
            for (ContentValues contentValues : this.mEdmStorageProvider.getValuesListAsUser("MISC", new String[]{"adminUid", "lockscreenstring"}, i)) {
                Integer asInteger = contentValues.getAsInteger("lockscreenstring");
                if (asInteger != null && asInteger.intValue() == 1) {
                    return EdmStorageProviderBase.getAdminUidFromLUID(contentValues.getAsLong("adminUid").longValue());
                }
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public boolean changeLockScreenString(ContextInfo contextInfo, String str) {
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
        if (!z) {
            Log.d("MiscPolicy", "changeLockScreenString(): apply restriction");
            putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndSecurityPermission.mCallerUid, "MISC", "lockscreenstring", 1);
            if (putInt) {
                Log.d("MiscPolicy", "changeLockScreenString(): ret is true set value");
                try {
                    getLockSettings().setBoolean("lock_screen_owner_info_enabled", true, callingOrCurrentUserId);
                    getLockSettings().setString("lock_screen_owner_info", str4, callingOrCurrentUserId);
                    int myPid = Process.myPid();
                    Object[] objArr = {Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid), str4};
                    str3 = "Couldn't write string ";
                    try {
                        AuditLog.logAsUser(5, 1, true, myPid, "MiscPolicy", String.format("Admin %d has changed lock screen string to %s", objArr), callingOrCurrentUserId);
                    } catch (RemoteException e) {
                        e = e;
                        Log.e("MiscPolicy", str3 + str4 + e);
                        Settings.System.putIntForUser(contentResolver, "my_profile_enabled", 0, callingOrCurrentUserId);
                        boolean z2 = putInt;
                        Log.d("MiscPolicy", "changeLockScreenString():ret:" + z2 + " " + str4);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return z2;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    str3 = "Couldn't write string ";
                }
                Settings.System.putIntForUser(contentResolver, "my_profile_enabled", 0, callingOrCurrentUserId);
            }
        } else {
            Log.d("MiscPolicy", "changeLockScreenString(): revoke restriction");
            putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndSecurityPermission.mCallerUid, "MISC", "lockscreenstring", 0);
            try {
                getLockSettings().setString("lock_screen_owner_info", str4, callingOrCurrentUserId);
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "MiscPolicy", String.format("Admin %d has cleared the lock screen string.", Integer.valueOf(enforceOwnerOnlyAndSecurityPermission.mCallerUid)), callingOrCurrentUserId);
            } catch (RemoteException e3) {
                Log.e("MiscPolicy", "Couldn't write string " + str4 + e3);
            }
        }
        boolean z22 = putInt;
        Log.d("MiscPolicy", "changeLockScreenString():ret:" + z22 + " " + str4);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z22;
    }

    public final SystemFontChanger GetSystemFontChanger() {
        if (this.mSystemFontChanger == null) {
            this.mSystemFontChanger = new SystemFontChanger();
        }
        return this.mSystemFontChanger;
    }

    public boolean setSystemActiveFont(ContextInfo contextInfo, String str, String str2) {
        enforceOwnerOnlyAndSecurityPermission(contextInfo);
        if (str2 != null) {
            return false;
        }
        return GetSystemFontChanger().setSystemActiveFont(str, null);
    }

    public String getSystemActiveFont(ContextInfo contextInfo) {
        return GetSystemFontChanger().getSystemActiveFont();
    }

    public String[] getSystemFonts(ContextInfo contextInfo) {
        return GetSystemFontChanger().getSystemFonts();
    }

    public class SystemFontChanger {
        public TypefaceFinder mTypefaceFinder;

        public SystemFontChanger() {
            this.mTypefaceFinder = null;
        }

        public boolean setSystemActiveFont(String str, String str2) {
            Log.i("MiscPolicy", "setSystemActiveFont():Start");
            if (str == null) {
                Log.i("MiscPolicy", "setSystemActiveFont():Invalid input");
                return false;
            }
            boolean changeFont = changeFont(str, str2);
            if (!changeFont) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                Configuration configuration = iActivityManager.getConfiguration();
                configuration.FlipFont = Math.abs(str.hashCode()) + 1;
                iActivityManager.updateConfiguration(configuration);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                clearCallingIdentity = Binder.clearCallingIdentity();
                ActivityManager activityManager = (ActivityManager) MiscPolicy.this.mContext.getSystemService("activity");
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(50);
                if (runningTasks != null) {
                    Iterator<ActivityManager.RunningTaskInfo> it = runningTasks.iterator();
                    while (it.hasNext()) {
                        activityManager.restartPackage(it.next().baseActivity.getPackageName());
                    }
                }
                return changeFont;
            } catch (Exception unused) {
                Log.i("MiscPolicy", "setSystemActiveFont():Exception");
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean changeFont(String str, String str2) {
            FontWriter fontWriter = new FontWriter();
            PackageManager packageManager = MiscPolicy.this.mContext.getPackageManager();
            String[] fontString = getFontString();
            if (fontString == null) {
                Log.i("MiscPolicy", "changeFont():Installed font list is null");
                return false;
            }
            int i = 0;
            while (true) {
                if (i >= fontString.length) {
                    i = 0;
                    break;
                }
                if (fontString[i].equalsIgnoreCase(str)) {
                    Log.i("MiscPolicy", LauncherConfigurationInternal.KEY_INDEX_INT + i);
                    break;
                }
                i++;
            }
            if (str.equalsIgnoreCase("default")) {
                fontWriter.writeLoc(MiscPolicy.this.mContext, "sans.loc", "default#default");
                savePreferences(fontString[i], i);
                Log.i("MiscPolicy", "default font is selected..." + i);
                return true;
            }
            Typeface findMatchingTypefaceByName = this.mTypefaceFinder.findMatchingTypefaceByName(str);
            if (findMatchingTypefaceByName == null) {
                Log.i("MiscPolicy", "change font failed");
                return false;
            }
            String fontPackageName = findMatchingTypefaceByName.getFontPackageName();
            if (fontPackageName != null && !fontPackageName.startsWith("com.monotype.android.font.")) {
                return false;
            }
            File createFontDirectory = fontWriter.createFontDirectory(MiscPolicy.this.mContext, findMatchingTypefaceByName.getTypefaceFilename().replaceAll(".xml", "").replaceAll(" ", PackageManagerShellCommandDataLoader.STDIN_PATH));
            if (createFontDirectory == null) {
                Log.e("MiscPolicy", "create fontDir object is null ");
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            for (int i2 = 0; i2 < findMatchingTypefaceByName.mSansFonts.size(); i2++) {
                TypefaceFile typefaceFile = (TypefaceFile) findMatchingTypefaceByName.mSansFonts.get(i2);
                try {
                    fontWriter.copyFontFile(createFontDirectory, packageManager.getResourcesForApplication(fontPackageName).getAssets().open("fonts/" + typefaceFile.getFileName()), typefaceFile.getDroidName());
                } catch (Exception e) {
                    Log.i("MiscPolicy", "changeFont():Exception");
                    Log.i("MiscPolicy", "Exception" + e);
                    e.printStackTrace();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            fontWriter.writeLoc(MiscPolicy.this.mContext, "sans.loc", createFontDirectory.getAbsolutePath());
            savePreferences(findMatchingTypefaceByName.getTypefaceFilename(), i);
            Log.i("MiscPolicy", "change font:Done");
            return true;
        }

        public String getSystemActiveFont() {
            Log.i("MiscPolicy", "getSystemActiveFont():getting active system font:");
            int activeFontPosition = getActiveFontPosition();
            Log.i("MiscPolicy", "getActiveFontPosition():" + activeFontPosition);
            String[] systemFonts = getSystemFonts();
            if (systemFonts != null) {
                return systemFonts[activeFontPosition];
            }
            return null;
        }

        /* JADX WARN: Not initialized variable reg: 3, insn: 0x0060: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:37:0x0060 */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0063 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int getActiveFontPosition() {
            BufferedInputStream bufferedInputStream;
            BufferedInputStream bufferedInputStream2;
            Integer num;
            HashMap hashMap;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            BufferedInputStream bufferedInputStream3 = null;
            BufferedInputStream bufferedInputStream4 = null;
            r2 = null;
            HashMap hashMap2 = null;
            try {
                try {
                    File sharedPrefsFile = MiscPolicy.this.mContext.createPackageContext("com.android.settings", 2).getSharedPrefsFile("prefs");
                    if (sharedPrefsFile.canRead()) {
                        bufferedInputStream = new BufferedInputStream(new FileInputStream(sharedPrefsFile), 16384);
                        try {
                            hashMap = XmlUtils.readMapXml(bufferedInputStream);
                            bufferedInputStream4 = bufferedInputStream;
                        } catch (Exception e) {
                            e = e;
                            e.printStackTrace();
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return hashMap2 == null ? 0 : 0;
                        }
                    } else {
                        hashMap = null;
                    }
                    if (bufferedInputStream4 != null) {
                        try {
                            bufferedInputStream4.close();
                        } catch (IOException unused2) {
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    hashMap2 = hashMap;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream3 = bufferedInputStream2;
                    if (bufferedInputStream3 != null) {
                        try {
                            bufferedInputStream3.close();
                        } catch (IOException unused3) {
                            throw th;
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                bufferedInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedInputStream3 != null) {
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            if (hashMap2 == null && (num = (Integer) hashMap2.get("SavedClickedItem")) != null) {
                return num.intValue();
            }
        }

        public String[] getSystemFonts() {
            Log.i("MiscPolicy", "getSystemFonts():getting all system fonts");
            return getFontString();
        }

        public final String[] getFontString() {
            Vector fontsVector = getFontsVector();
            if (fontsVector == null) {
                return null;
            }
            String[] strArr = new String[fontsVector.size()];
            for (int i = 0; i < fontsVector.size(); i++) {
                strArr[i] = (String) fontsVector.get(i);
            }
            return strArr;
        }

        public final Vector getFontsVector() {
            this.mTypefaceFinder = null;
            this.mTypefaceFinder = new TypefaceFinder();
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            PackageManager packageManager = MiscPolicy.this.mContext.getPackageManager();
            long clearCallingIdentity = Binder.clearCallingIdentity();
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
                return vector2;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void savePreferences(String str, int i) {
            Context context;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                context = MiscPolicy.this.mContext.createPackageContext("com.android.settings", 2);
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
            Settings.Global.putInt(MiscPolicy.this.mContext.getContentResolver(), "flip_font_style", i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            SharedPreferences.Editor edit2 = context.getSharedPreferences(context.getPackageName() + "_preferences", 0).edit();
            edit2.putString("MONOTYPE", str);
            edit2.commit();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
        updateAdminsBlockingNfcStateChange();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        updateAdminsBlockingNfcStateChange();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        if (isAdminLockScreenStringSet(UserHandle.getUserId(i)) == i) {
            try {
                getLockSettings().setString("lock_screen_owner_info", "", UserHandle.getUserId(i));
            } catch (RemoteException e) {
                Log.e("MiscPolicy", "Couldn't write string " + e);
            }
        }
        if (UserHandle.getUserId(i) == 0) {
            clearGlobalProxyEnable(new ContextInfo(i, 0));
        }
    }

    public boolean setSystemActiveFontSize(ContextInfo contextInfo, float f) {
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
            }
        } finally {
        }
    }

    public float getSystemActiveFontSize(ContextInfo contextInfo) {
        Configuration configuration = new Configuration();
        configuration.fontScale = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        try {
            configuration.updateFrom(ActivityManagerNative.getDefault().getConfiguration());
            retrieveSystemFontSizes();
            if (this.mFontSizes != null) {
                floatToIndex(configuration.fontScale);
                int i = Settings.Global.getInt(this.mContext.getContentResolver(), "font_size", 0);
                if (i >= this.mFontSizes.length) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("getSystemActiveFontSize() : resized font index value. ");
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

    public float[] getSystemFontSizes(ContextInfo contextInfo) {
        retrieveSystemFontSizes();
        return this.mFontSizes;
    }

    public final int floatToIndex(float f) {
        float f2 = this.mFontSizes[0];
        int i = 1;
        while (true) {
            float[] fArr = this.mFontSizes;
            if (i < fArr.length) {
                float f3 = fArr[i];
                if (f < f2 + ((f3 - f2) * 0.5f)) {
                    return i - 1;
                }
                i++;
                f2 = f3;
            } else {
                int length = fArr.length - 1;
                Log.w("MiscPolicy", "floatToIndex(): " + length);
                return length;
            }
        }
    }

    public final void retrieveSystemFontSizes() {
        Context createPackageContext;
        int identifier;
        if (this.mFontSizes != null) {
            return;
        }
        Log.i("MiscPolicy", "retrieveSystemFontSizes(): start to retrieve system font sizes.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                createPackageContext = this.mContext.createPackageContext("com.android.settings", 2);
            } catch (Exception e) {
                Log.e("MiscPolicy", "retrieveSystemFontSizes() failed: unexpected exception. ", e);
            }
            if (createPackageContext == null) {
                Log.i("MiscPolicy", "retrieveSystemFontSizes(): failed because Setting Context is null");
                return;
            }
            Resources resources = createPackageContext.getResources();
            if (supportBigFont(createPackageContext)) {
                identifier = resources.getIdentifier("sec_entryvalues_big_font_size", "array", "com.android.settings");
            } else {
                identifier = resources.getIdentifier("sec_entryvalues_8_step_font_size", "array", "com.android.settings");
            }
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

    public boolean allowNFCStateChange(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndHwPermission = enforceOwnerOnlyAndHwPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndHwPermission.mCallerUid, "MISC", "nfcStateChangeAllowed", z);
        updateAdminsBlockingNfcStateChange();
        if (putBoolean) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "MiscPolicy", String.format("Admin %d has changed NFC state change to %s", Integer.valueOf(enforceOwnerOnlyAndHwPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndHwPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return putBoolean;
    }

    public boolean isNFCStateChangeAllowed() {
        Iterator it = this.mEdmStorageProvider.getBooleanList("MISC", "nfcStateChangeAllowed").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final void dumpNfcStateChangeAllowed(PrintWriter printWriter) {
        if (this.mAdminsBlockingNfcStateChange.isEmpty()) {
            printWriter.println("  No admin blocking NFC State change");
            return;
        }
        printWriter.println("  NFC State change blocked by admin UID : " + this.mAdminsBlockingNfcStateChange);
    }

    public final void updateAdminsBlockingNfcStateChange() {
        Integer asInteger;
        this.mAdminsBlockingNfcStateChange = new ArrayList();
        for (ContentValues contentValues : this.mEdmStorageProvider.getValuesList("MISC", new String[]{"nfcStateChangeAllowed", "adminUid"})) {
            if (!(contentValues.getAsBoolean("nfcStateChangeAllowed") == null ? true : contentValues.getAsBoolean("nfcStateChangeAllowed").booleanValue()) && (asInteger = contentValues.getAsInteger("adminUid")) != null) {
                this.mAdminsBlockingNfcStateChange.add(asInteger);
            }
        }
    }

    public boolean startNFC(ContextInfo contextInfo, boolean z) {
        boolean disable;
        makeStartNFCHistory(enforceOwnerOnlyAndHwPermission(contextInfo), z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
                if (z) {
                    disable = defaultAdapter.enable();
                } else {
                    disable = defaultAdapter.disable();
                }
                return disable;
            } catch (Exception unused) {
                Log.w("MiscPolicy", "Error on Start/Stop NFC");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isNFCStarted() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = NfcAdapter.getDefaultAdapter(this.mContext).isAllEnabled();
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

    public final void makeStartNFCHistory(ContextInfo contextInfo, boolean z) {
        this.mStartNFCHistoryList.add(makeTime() + " callerUid=" + contextInfo.mCallerUid + " value=" + z);
        if (this.mStartNFCHistoryList.size() > 10) {
            this.mStartNFCHistoryList.remove(0);
        }
    }

    public final String makeTime() {
        return FORMAT.format(new Date(System.currentTimeMillis()));
    }

    public final ConnectivityManager getConnectivityManagerService() {
        if (this.mCon == null) {
            this.mCon = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        }
        return this.mCon;
    }

    public int setGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo, String str, int i, List list) {
        ContextInfo enforceOwnerOnlyAndFirewallPermission = enforceOwnerOnlyAndFirewallPermission(contextInfo);
        ProxyProperties proxyProperties = new ProxyProperties();
        proxyProperties.setHostname(str);
        proxyProperties.setPortNumber(i);
        proxyProperties.setExclusionList(list);
        return setGlobalProxy(enforceOwnerOnlyAndFirewallPermission, proxyProperties);
    }

    public int setGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo, ProxyProperties proxyProperties) {
        return setGlobalProxy(enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo), proxyProperties);
    }

    public final boolean validateProxyProperties(ProxyProperties proxyProperties) {
        if (!proxyProperties.isValid()) {
            return false;
        }
        if (TextUtils.isEmpty(proxyProperties.getHostname())) {
            return !TextUtils.isEmpty(proxyProperties.getPacFileUrl()) && URLUtil.isNetworkUrl(proxyProperties.getPacFileUrl());
        }
        String hostname = proxyProperties.getHostname();
        int portNumber = proxyProperties.getPortNumber();
        return portNumber >= 0 && portNumber <= 65535 && (validateIp(hostname) || validateHostName(hostname));
    }

    public final boolean isGlobalProxySetForAdmin(int i, int i2) {
        try {
            return this.mEdmStorageProvider.getBoolean(i, i2, "RESTRICTION", "globalProxy");
        } catch (SettingNotFoundException unused) {
            Log.e("MiscPolicy", "setGlobalProxy.SettingNotFoundException");
            return false;
        }
    }

    public final ContentValues convertAuthConfigToContentValues(int i, AuthConfig authConfig) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("host", authConfig.getHost());
        contentValues.put("port", Integer.valueOf(authConfig.getPort()));
        contentValues.put("username", authConfig.getUsername());
        contentValues.put("password", authConfig.getPassword());
        return contentValues;
    }

    public final ContentValues convertProxyInfoToContentValues(int i, ProxyInfo proxyInfo) {
        ContentValues contentValues = new ContentValues();
        String host = !TextUtils.isEmpty(proxyInfo.getHost()) ? proxyInfo.getHost() : "";
        String uri = proxyInfo.getPacFileUrl() != null ? proxyInfo.getPacFileUrl().toString() : "";
        String exclusionListAsString = proxyInfo.getExclusionList() != null ? NetworkUtils.getExclusionListAsString(proxyInfo.getExclusionList()) : "";
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("host", host);
        contentValues.put("port", Integer.valueOf(proxyInfo.getPort()));
        contentValues.put("pacfile", uri);
        contentValues.put("exclusion", exclusionListAsString);
        return contentValues;
    }

    public final synchronized int setGlobalProxy(ContextInfo contextInfo, ProxyProperties proxyProperties) {
        ProxyInfo localProxyInfo;
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_SECURE_NETWORK", 1, "setGlobalProxy");
        knoxAnalyticsData.setProperty("proxyTp", !TextUtils.isEmpty(proxyProperties.getPacFileUrl()) ? 1 : 0);
        knoxAnalyticsData.setProperty("proxyAuth", proxyProperties.isAuthenticationConfigured() ? 0 : 1);
        KnoxAnalytics.log(knoxAnalyticsData);
        if (!validateProxyProperties(proxyProperties)) {
            return 0;
        }
        if (!isGlobalProxyAllowed() && !isGlobalProxySetForAdmin(contextInfo.mCallerUid, contextInfo.mContainerId)) {
            return 0;
        }
        if (getConnectivityManagerService() == null) {
            return 0;
        }
        ProxyInfo convertToProxyInfo = NetworkUtils.convertToProxyInfo(proxyProperties);
        if (!convertToProxyInfo.isValid()) {
            return 0;
        }
        if (!setGlobalProxyRestriction(contextInfo, false)) {
            return 0;
        }
        clearAuthConfigFromDb();
        clearProxyInfoFromDb();
        if (!saveAuthConfigToDb(contextInfo.mCallerUid, proxyProperties)) {
            return 0;
        }
        if (!saveProxyInfoToDb(contextInfo.mCallerUid, convertToProxyInfo)) {
            return 0;
        }
        if (TextUtils.isEmpty(proxyProperties.getPacFileUrl()) && (localProxyInfo = this.mLocalProxyManager.getLocalProxyInfo()) != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mCon.setGlobalProxy(localProxyInfo);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        this.mLocalProxyManager.updateGlobalProxy(proxyProperties);
        return setGlobalProxyRestriction(contextInfo, true) ? 1 : 0;
    }

    public final boolean setGlobalProxyRestriction(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, contextInfo.mContainerId, "RESTRICTION", "globalProxy", z);
    }

    public final boolean saveAuthConfigToDb(int i, ProxyProperties proxyProperties) {
        boolean z = true;
        if (proxyProperties.isAuthenticationConfigured()) {
            Iterator it = proxyProperties.getAuthConfigList().iterator();
            while (it.hasNext()) {
                if (this.mEdmStorageProvider.insert("GlobalProxyAuthTable", convertAuthConfigToContentValues(i, (AuthConfig) it.next())) == -1) {
                    z = false;
                }
            }
        }
        return z;
    }

    public final boolean saveProxyInfoToDb(int i, ProxyInfo proxyInfo) {
        return this.mEdmStorageProvider.insert("GlobalProxyTable", convertProxyInfoToContentValues(i, proxyInfo)) != -1;
    }

    public final void clearAuthConfigFromDb() {
        this.mEdmStorageProvider.delete("GlobalProxyAuthTable", null);
    }

    public final void clearProxyInfoFromDb() {
        this.mEdmStorageProvider.delete("GlobalProxyTable", null);
        this.mLocalProxyManager.updateGlobalProxy(null);
    }

    public final List getAuthConfigFromDb() {
        ArrayList arrayList = new ArrayList();
        List<ContentValues> values = this.mEdmStorageProvider.getValues("GlobalProxyAuthTable", new String[]{"host", "port", "username", "password"}, (ContentValues) null);
        if (values == null) {
            return arrayList;
        }
        for (ContentValues contentValues : values) {
            String asString = contentValues.getAsString("host");
            Integer asInteger = contentValues.getAsInteger("port");
            arrayList.add(new AuthConfig(asString, asInteger.intValue(), contentValues.getAsString("username"), contentValues.getAsString("password")));
        }
        return arrayList;
    }

    public String retrieveProxyCredentials(String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getProxyCredentials(str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enforceMDMAppCaller() {
        if (this.mContext.checkCallingPermission("com.samsung.android.knox.permission.KNOX_SET_PROXY_CREDENTIAL_INTERNAL") != 0) {
            throw new SecurityException("Caller does not have required permission.");
        }
    }

    public static boolean validateHostName(String str) {
        boolean z;
        if (str == null) {
            return false;
        }
        if (str.equals("*")) {
            return true;
        }
        if (str.length() > 255) {
            return false;
        }
        String[] split = str.split("\\.");
        for (int i = 0; i < split[0].length(); i++) {
            char charAt = split[0].charAt(i);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                z = true;
                break;
            }
        }
        z = false;
        if (!z) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) == '.') {
                i2++;
            }
        }
        if (i2 >= split.length) {
            return false;
        }
        for (String str2 : split) {
            if (str2.length() > 63) {
                return false;
            }
        }
        for (String str3 : split) {
            if (!str3.matches("^[A-Za-z0-9-]+$") || str3.charAt(0) == '-' || str3.charAt(str3.length() - 1) == '-') {
                return false;
            }
        }
        return true;
    }

    public boolean isGlobalProxyAllowed() {
        return !this.mEdmStorageProvider.getBooleanList("RESTRICTION", "globalProxy").contains(Boolean.TRUE);
    }

    public List getGlobalProxyEnforcingFirewallPermission(ContextInfo contextInfo) {
        enforceOwnerOnlyAndFirewallPermission(contextInfo);
        ProxyProperties globalProxy = getGlobalProxy();
        if (globalProxy == null || globalProxy.getHostname() == null || globalProxy.getHostname().toString().equals("")) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(globalProxy.getHostname().toString().trim() + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + Integer.valueOf(globalProxy.getPortNumber()).toString().trim());
        if (globalProxy.getExclusionList() != null) {
            Iterator it = globalProxy.getExclusionList().iterator();
            while (it.hasNext()) {
                arrayList.add((String) it.next());
            }
        }
        return arrayList;
    }

    public ProxyProperties getGlobalProxyEnforcingSecurityPermission(ContextInfo contextInfo) {
        enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo);
        return getGlobalProxy();
    }

    public final synchronized ProxyProperties getGlobalProxy() {
        ProxyInfo buildDirectProxy;
        ProxyProperties globalProxyCache = this.mLocalProxyManager.getGlobalProxyCache();
        if (globalProxyCache != null) {
            return globalProxyCache;
        }
        List authConfigFromDb = getAuthConfigFromDb();
        List<ContentValues> values = this.mEdmStorageProvider.getValues("GlobalProxyTable", new String[]{"host", "port", "pacfile", "exclusion"}, (ContentValues) null);
        if (values == null) {
            return globalProxyCache;
        }
        for (ContentValues contentValues : values) {
            String asString = contentValues.getAsString("host");
            Integer asInteger = contentValues.getAsInteger("port");
            int intValue = asInteger == null ? -1 : asInteger.intValue();
            String asString2 = contentValues.getAsString("exclusion");
            String asString3 = contentValues.getAsString("pacfile");
            if (!TextUtils.isEmpty(asString) || !TextUtils.isEmpty(asString3)) {
                if (!TextUtils.isEmpty(asString3)) {
                    buildDirectProxy = ProxyInfo.buildPacProxy(Uri.parse(asString3));
                } else {
                    buildDirectProxy = ProxyInfo.buildDirectProxy(asString, intValue, convertStringToList(asString2));
                }
                if (buildDirectProxy.isValid()) {
                    globalProxyCache = new ProxyProperties();
                    globalProxyCache.setHostname(asString);
                    globalProxyCache.setPortNumber(intValue);
                    if (!authConfigFromDb.isEmpty()) {
                        globalProxyCache.setAuthConfigList(authConfigFromDb);
                    }
                    globalProxyCache.setExclusionList(Arrays.asList(buildDirectProxy.getExclusionList()));
                    globalProxyCache.setPacFileUrl(asString3);
                } else {
                    Log.d("MiscPolicy", "Invalid proxy properties, ignoring: " + buildDirectProxy.toString());
                }
            }
        }
        return globalProxyCache;
    }

    public final List convertStringToList(String str) {
        return !TextUtils.isEmpty(str) ? Arrays.asList(str.split(",")) : new ArrayList();
    }

    public int clearGlobalProxyEnableEnforcingFirewallPermission(ContextInfo contextInfo) {
        return clearGlobalProxyEnable(enforceOwnerOnlyAndFirewallPermission(contextInfo));
    }

    public int clearGlobalProxyEnableEnforcingSecurityPermission(ContextInfo contextInfo) {
        return clearGlobalProxyEnable(enforceOwnerOnlyAndOldSecurityOrNewGlobalProxyPermission(contextInfo));
    }

    public synchronized void clearAllGlobalProxy() {
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
            if (getConnectivityManagerService() == null) {
                return 0;
            }
            setGlobalProxyRestriction(contextInfo, false);
            clearAuthConfigFromDb();
            clearProxyInfoFromDb();
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

    public final boolean validateIp(String str) {
        if (str != null) {
            return Patterns.IP_ADDRESS.matcher(str).matches();
        }
        return false;
    }

    public final void updateSystemUIMonitor(int i) {
        setNFCStateChangeAllowedSystemUI(i, isNFCStateChangeAllowed());
    }

    public final void setNFCStateChangeAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setNFCStateChangeAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("MiscPolicy", "setNFCStateChangeAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isFolderModel(Context context) {
        return context.getPackageManager().hasSystemFeature("com.sec.feature.folder_type");
    }

    public final boolean isChinaModel() {
        return "CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
    }

    public final boolean supportBigFont(Context context) {
        return isFolderModel(context) && isChinaModel() && !Build.PRODUCT.contains("elite");
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump KioskModeService");
            return;
        }
        synchronized (this.mStartNFCHistoryList) {
            int size = this.mStartNFCHistoryList.size();
            printWriter.println("  mStartNFCHistoryList.size=" + size);
            for (int i = 0; i < size; i++) {
                printWriter.println("    [" + i + "] " + ((String) this.mStartNFCHistoryList.get(i)));
            }
        }
        this.mLocalProxyManager.dumpEnterpriseProxyCache(printWriter);
        dumpNfcStateChangeAllowed(printWriter);
    }

    public final void initializeGlobalProxyCache() {
        this.mLocalProxyManager.updateGlobalProxy(getGlobalProxy());
    }

    public ProxyProperties retrieveExternalProxy() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getCurrentAppliedProxy();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void showCredentialsDialogNotification(String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || isCalledFromProxyHandler(callingUid)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mLocalProxyManager.showCredentialsDialogNotification(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void refreshCredentialsDialogFails() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Binder.getCallingUid() != 1000) {
                return;
            }
            this.mLocalProxyManager.refreshCredentialsDialogFails();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getAppUidBrowserList() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getAppUidBrowserList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearNotificationDialog() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || isCalledFromProxyHandler(callingUid)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mLocalProxyManager.clearNotificationDialog();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void setProxyCredentials(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) {
        if (Binder.getCallingUid() == 1000 || isCalledFromMDM()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mLocalProxyManager.setProxyCredentials(bundle, iProxyCredentialsCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public ProxyProperties getProxyForSsid(String str) {
        if (Binder.getCallingUid() != 1000) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getProxyForSsid(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getAppUidFromSocketPortNumber(int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && !isCalledFromProxyHandler(callingUid)) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mLocalProxyManager.getAppUidFromSocketPortNumber(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isCalledFromMDM() {
        try {
            enforceMDMAppCaller();
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public boolean isCalledFromProxyHandler(int i) {
        for (String str : mPackageManager.getPackagesForUid(i)) {
            if (str.equals("com.android.proxyhandler") && isSystemApp(str)) {
                Log.d("MiscPolicy", "Allowing Proxy Handler access");
                return true;
            }
        }
        return false;
    }

    public boolean isSystemApp(String str) {
        try {
            return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
