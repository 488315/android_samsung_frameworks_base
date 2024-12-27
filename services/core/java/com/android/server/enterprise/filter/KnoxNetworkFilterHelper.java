package com.android.server.enterprise.filter;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IDeviceIdleController;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxNetworkFilterHelper {
    public static KnoxNetworkFilterHelper mKnoxNetworkFilterHelper;
    public final AppOpsManager mAppOpsManager;
    public final HashMap mBundleInfo = new HashMap();
    public final Context mContext;
    public final EdmStorageProvider mEDM;

    static {
        Debug.semIsProductDev();
    }

    public KnoxNetworkFilterHelper(Context context) {
        this.mContext = context;
        this.mEDM = new EdmStorageProvider(context);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
    }

    public static boolean checkIfPlatformSigned(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128L, i);
            if (packageManager.checkUidSignatures(
                            applicationInfo != null ? applicationInfo.uid : -1, 1000)
                    == 0) {
                z = true;
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public static String getConfiguredDestIpRange(int i, String str) {
        String str2;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        String str3 = "";
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)
                && (str2 = KnoxNetworkFilterProfileInfo.getProfileEntry(str).mSocketConfig)
                        != null) {
            try {
                JSONObject jSONObject4 = new JSONObject(str2);
                if (i == 2) {
                    JSONObject jSONObject5 = (JSONObject) jSONObject4.opt("ipv4");
                    if (jSONObject5 != null
                            && (jSONObject3 = (JSONObject) jSONObject5.opt("misc")) != null) {
                        str3 = jSONObject3.optString("ipRange", "");
                    }
                } else if (i == 10
                        && (jSONObject = (JSONObject) jSONObject4.opt("ipv6")) != null
                        && (jSONObject2 = (JSONObject) jSONObject.opt("misc")) != null) {
                    str3 = jSONObject2.optString("ipRange", "");
                }
            } catch (JSONException unused) {
            }
        }
        VpnManagerService$$ExternalSyntheticOutline0.m(
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "getConfiguredIpRange: profileName ", str, " ipVersion: ", " ipRange: "),
                str3,
                "knoxNwFilter-KnoxNetworkFilterHelper");
        return str3;
    }

    public static void getConfiguredProtocols(String str) {
        String str2;
        KnoxNetworkFilterProfileInfo profileEntry =
                KnoxNetworkFilterProfileInfo.getProfileEntry(str);
        if (profileEntry == null
                || (str2 = KnoxNetworkFilterProfileInfo.getProfileEntry(str).mSocketConfig)
                        == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            JSONObject jSONObject2 = (JSONObject) jSONObject.opt("ipv4");
            if (jSONObject2 != null) {
                JSONObject jSONObject3 = (JSONObject) jSONObject2.opt("dns");
                if (jSONObject3 != null) {
                    String optString = jSONObject3.optString("listenerAddress");
                    int optInt = jSONObject3.optInt("listenerPort");
                    if (optString != null && optInt > 0) {
                        profileEntry.mV4DnsConfigured = true;
                    }
                }
                JSONObject jSONObject4 = (JSONObject) jSONObject2.opt("tcp");
                if (jSONObject4 != null) {
                    String optString2 = jSONObject4.optString("listenerAddress");
                    int optInt2 = jSONObject4.optInt("listenerPort");
                    if (optString2 != null && optInt2 > 0) {
                        profileEntry.mV4TcpConfigured = true;
                    }
                }
                JSONObject jSONObject5 = (JSONObject) jSONObject2.opt("udp");
                if (jSONObject5 != null) {
                    String optString3 = jSONObject5.optString("listenerAddress");
                    int optInt3 = jSONObject5.optInt("listenerPort");
                    if (optString3 != null && optInt3 > 0) {
                        profileEntry.mV4UdpConfigured = true;
                    }
                }
            }
            JSONObject jSONObject6 = (JSONObject) jSONObject.opt("ipv6");
            if (jSONObject6 != null) {
                JSONObject jSONObject7 = (JSONObject) jSONObject6.opt("dns");
                if (jSONObject7 != null) {
                    jSONObject7.optString("listenerAddress");
                    jSONObject7.optInt("listenerPort");
                }
                JSONObject jSONObject8 = (JSONObject) jSONObject6.opt("tcp");
                if (jSONObject8 != null) {
                    String optString4 = jSONObject8.optString("listenerAddress");
                    int optInt4 = jSONObject8.optInt("listenerPort");
                    if (optString4 != null && optInt4 > 0) {
                        profileEntry.mV6TcpConfigured = true;
                    }
                }
                JSONObject jSONObject9 = (JSONObject) jSONObject6.opt("udp");
                if (jSONObject9 != null) {
                    String optString5 = jSONObject9.optString("listenerAddress");
                    int optInt5 = jSONObject9.optInt("listenerPort");
                    if (optString5 == null || optInt5 <= 0) {
                        return;
                    }
                    profileEntry.mV6UdpConfigured = true;
                }
            }
        } catch (JSONException e) {
            Log.e(
                    "knoxNwFilter-KnoxNetworkFilterHelper",
                    "Error parsing: getConfiguredProtocols " + Log.getStackTraceString(e));
        }
    }

    public static Set getInstalledVpnClientList(int i) {
        ServiceInfo[] serviceInfoArr;
        HashSet hashSet = new HashSet();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            for (ApplicationInfo applicationInfo :
                    packageManager.getInstalledApplications(0L, i).getList()) {
                String str = applicationInfo.packageName;
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 4L, i);
                if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
                    int length = serviceInfoArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        String str2 = serviceInfoArr[i2].permission;
                        if (str2 != null
                                && str2.equalsIgnoreCase("android.permission.BIND_VPN_SERVICE")) {
                            hashSet.add(Integer.valueOf(applicationInfo.uid));
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterHelper",
                                    "The following package "
                                            + str
                                            + " with uid "
                                            + applicationInfo.uid
                                            + " is identified as vpn client");
                            break;
                        }
                        i2++;
                    }
                }
            }
            if (i == 0) {
                hashSet.add(1016);
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return hashSet;
    }

    public static synchronized KnoxNetworkFilterHelper getInstance(Context context) {
        KnoxNetworkFilterHelper knoxNetworkFilterHelper;
        synchronized (KnoxNetworkFilterHelper.class) {
            try {
                if (mKnoxNetworkFilterHelper == null) {
                    mKnoxNetworkFilterHelper = new KnoxNetworkFilterHelper(context);
                }
                knoxNetworkFilterHelper = mKnoxNetworkFilterHelper;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxNetworkFilterHelper;
    }

    public static String getPackageNameForUid(int i) {
        String str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = AppGlobals.getPackageManager().getNameForUid(i);
            } catch (Exception e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterHelper",
                        "Exception in getPackageNameForUid : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                str = null;
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static List getProfileList() {
        ArrayList arrayList = new ArrayList();
        Collection values = KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            Iterator it = values.iterator();
            while (it.hasNext()) {
                arrayList.add(((KnoxNetworkFilterProfileInfo) it.next()).mProfileName);
            }
        }
        return arrayList;
    }

    public static List getProfileListByVendor(int i) {
        ArrayList arrayList = new ArrayList();
        Collection<KnoxNetworkFilterProfileInfo> values =
                KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : values) {
                if (knoxNetworkFilterProfileInfo.mPackageUid == i) {
                    arrayList.add(knoxNetworkFilterProfileInfo.mProfileName);
                }
            }
        }
        return arrayList;
    }

    public static String getProfilebyUserId(int i) {
        Collection<KnoxNetworkFilterProfileInfo> values =
                KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : values) {
                if (UserHandle.getUserId(knoxNetworkFilterProfileInfo.mPackageUid) == i) {
                    return knoxNetworkFilterProfileInfo.mProfileName;
                }
            }
        }
        return null;
    }

    public static String getRulesConfig(String str) {
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            return KnoxNetworkFilterProfileInfo.getProfileEntry(str).mRulesConfig;
        }
        return null;
    }

    public static int getUIDForPackage(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                ApplicationInfo applicationInfo =
                        AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
                if (applicationInfo != null) {
                    i2 = applicationInfo.uid;
                }
            } catch (Exception e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterHelper",
                        "Exception in getUIDForPackage : " + Log.getStackTraceString(e));
            }
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static List getUserIdList() {
        ArrayList arrayList = new ArrayList();
        Collection values = KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            Iterator it = values.iterator();
            while (it.hasNext()) {
                arrayList.add(
                        Integer.valueOf(
                                UserHandle.getUserId(
                                        ((KnoxNetworkFilterProfileInfo) it.next()).mPackageUid)));
            }
        }
        return arrayList;
    }

    public static int getVendorUidByProfile(String str) {
        Collection<KnoxNetworkFilterProfileInfo> values =
                KnoxNetworkFilterProfileInfo.mProfileInfomap.values();
        if (values != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : values) {
                if (knoxNetworkFilterProfileInfo.mProfileName.equalsIgnoreCase(str)) {
                    return knoxNetworkFilterProfileInfo.mPackageUid;
                }
            }
        }
        return -1;
    }

    public static boolean isPackageInstalled(int i, String str) {
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            ApplicationInfo applicationInfo =
                    AppGlobals.getPackageManager().getApplicationInfo(str, 0L, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return applicationInfo != null;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Error "), "knoxNwFilter-KnoxNetworkFilterHelper");
            return false;
        }
    }

    public static String retrieveListenersFromCache(String str) {
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            return KnoxNetworkFilterProfileInfo.getProfileEntry(str).mSocketConfig;
        }
        return null;
    }

    public final boolean addAuthorizedInfoToDatabase(int i, String str, Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                i, contentValues, "pkgUid", "pkgName", str);
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            obtain.writeBundle(bundle);
            byte[] marshall = obtain.marshall();
            obtain.recycle();
            contentValues.put("bundleInfo", marshall);
        }
        boolean putDataByFields =
                this.mEDM.putDataByFields("UnManagedNwFilterMgr", null, null, contentValues);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "addUnmanagedInfoToDatabase: result value is ",
                "knoxNwFilter-KnoxNetworkFilterHelper",
                putDataByFields);
        if (putDataByFields) {
            if (this.mBundleInfo.containsKey(Integer.valueOf(UserHandle.getUserId(i)))) {
                this.mBundleInfo.remove(Integer.valueOf(UserHandle.getUserId(i)));
            }
            this.mBundleInfo.put(Integer.valueOf(UserHandle.getUserId(i)), bundle);
        }
        return putDataByFields;
    }

    public final void addOrRemoveAppsFromBatteryOptimization(int i, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    if (!((PowerManager) this.mContext.getSystemService("power"))
                            .isIgnoringBatteryOptimizations(str)) {
                        IDeviceIdleController.Stub.asInterface(
                                        ServiceManager.getService("deviceidle"))
                                .addPowerSaveWhitelistApp(str);
                    }
                    this.mAppOpsManager.setMode(124, i, str, 0);
                    this.mAppOpsManager.setMode(128, i, str, 0);
                    this.mAppOpsManager.setMode(129, i, str, 0);
                } else {
                    if (((PowerManager) this.mContext.getSystemService("power"))
                            .isIgnoringBatteryOptimizations(str)) {
                        IDeviceIdleController.Stub.asInterface(
                                        ServiceManager.getService("deviceidle"))
                                .removePowerSaveWhitelistApp(str);
                    }
                    this.mAppOpsManager.setMode(124, i, str, 3);
                    this.mAppOpsManager.setMode(128, i, str, 3);
                    this.mAppOpsManager.setMode(129, i, str, 3);
                }
            } catch (Exception e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterHelper",
                        " addOrRemoveAppsFromBatteryOptimization error "
                                + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean addRegisterInfoToDatabase(int i, String str, String str2, Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", -1, "pkgUid");
        contentValues.put("pkgName", str);
        if (str2 != null) {
            contentValues.put("pkgSign", str2);
        }
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            obtain.writeBundle(bundle);
            byte[] marshall = obtain.marshall();
            obtain.recycle();
            contentValues.put("bundleInfo", marshall);
        }
        boolean putDataByFields =
                this.mEDM.putDataByFields("NwFilterMgr", null, null, contentValues);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "addRegisterInfoToDatabase: status is ",
                "knoxNwFilter-KnoxNetworkFilterHelper",
                putDataByFields);
        return putDataByFields;
    }

    public final boolean addVendorInfoToStorage(int i, String str, String str2, String str3) {
        boolean putDataByFields;
        if (this.mEDM
                        .getDataByFields(
                                "NwFilterService",
                                new String[] {"profileName"},
                                new String[] {str},
                                new String[] {"profileConfig"})
                        .size()
                > 0) {
            ContentValues m =
                    AccountManagerService$$ExternalSyntheticOutline0.m("profileConfig", str2);
            putDataByFields =
                    this.mEDM.putDataByFields(
                            "NwFilterService", new String[] {"profileName"}, new String[] {str}, m);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "addVendorInfoToStorage: update status is ",
                    "knoxNwFilter-KnoxNetworkFilterHelper",
                    putDataByFields);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("profileName", str);
            contentValues.put("profileConfig", str2);
            KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                    i, contentValues, "pkgUid", "pkgName", str3);
            putDataByFields =
                    this.mEDM.putDataByFields("NwFilterService", null, null, contentValues);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "addVendorInfoToStorage: new entry status is ",
                    "knoxNwFilter-KnoxNetworkFilterHelper",
                    putDataByFields);
        }
        if (putDataByFields) {
            if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
                KnoxNetworkFilterProfileInfo.getProfileEntry(str).mRulesConfig = str2;
            } else {
                KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo =
                        new KnoxNetworkFilterProfileInfo();
                knoxNetworkFilterProfileInfo.mProfileName = str;
                knoxNetworkFilterProfileInfo.mRulesConfig = str2;
                knoxNetworkFilterProfileInfo.mPackageName = str3;
                knoxNetworkFilterProfileInfo.mPackageUid = i;
                synchronized (KnoxNetworkFilterProfileInfo.class) {
                    if (str != null) {}
                }
            }
        }
        return putDataByFields;
    }

    public final void applyHttpProxyConfiguration(String str, String[] strArr, boolean z, int i) {
        KnoxNetworkFilterProfileInfo profileEntry =
                KnoxNetworkFilterProfileInfo.getProfileEntry(str);
        if (profileEntry == null || profileEntry.mDNSCacheStatus != 1) {
            return;
        }
        if (z) {
            for (String str2 : strArr) {
                profileEntry.mBrowserAppList.add(str2);
            }
        } else {
            profileEntry.mBrowserAppList.clear();
        }
        if (strArr != null) {
            for (String str3 : strArr) {
                try {
                    int uIDForPackage = getUIDForPackage(i, str3);
                    if (uIDForPackage != -1) {
                        ApplicationInfo applicationInfo =
                                this.mContext.getPackageManager().getApplicationInfo(str3, 0);
                        if (((ActivityManagerService) ServiceManager.getService("activity"))
                                .checkIfProcessIsRunning(
                                        uIDForPackage, applicationInfo.processName)) {
                            Log.d(
                                    "knoxNwFilter-KnoxNetworkFilterHelper",
                                    "Proxy config has been applied, going to restart the app "
                                            + str3
                                            + "whose uid is "
                                            + uIDForPackage);
                            ((ActivityManagerService) ServiceManager.getService("activity"))
                                    .killApplicationProcess(
                                            applicationInfo.processName, uIDForPackage);
                        }
                        ((ActivityManagerService) ServiceManager.getService("activity"))
                                .killBackgroundProcesses(str3, UserHandle.getUserId(uIDForPackage));
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
    }

    public final int getAdminIdForUser(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        int i2 = -1;
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                i2 = ((ContentValues) it.next()).getAsInteger("adminUid").intValue();
                if (UserHandle.getUserId(i2) == i) {
                    break;
                }
            }
        }
        return i2;
    }

    public final String getAuthorizedAppPackage(int i) {
        ArrayList dataByFields =
                this.mEDM.getDataByFields("UnManagedNwFilterMgr", null, null, null);
        if (dataByFields.size() <= 0) {
            return null;
        }
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (UserHandle.getUserId(contentValues.getAsInteger("pkgUid").intValue()) == i) {
                return contentValues.getAsString("pkgName");
            }
        }
        return null;
    }

    public final List getAuthorizedInfoFromDatabase() {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields =
                this.mEDM.getDataByFields("UnManagedNwFilterMgr", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                arrayList.add(contentValues.getAsString("pkgName"));
                arrayList.add(Integer.toString(contentValues.getAsInteger("pkgUid").intValue()));
            }
        }
        return arrayList;
    }

    public final List getRegisterInfoFromDatabase() {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                arrayList.add(Integer.toString(contentValues.getAsInteger("adminUid").intValue()));
                arrayList.add(contentValues.getAsString("pkgName"));
                arrayList.add(Integer.toString(contentValues.getAsInteger("pkgUid").intValue()));
                String asString = contentValues.getAsString("pkgSign");
                if (asString != null) {
                    arrayList.add(asString);
                }
            }
        }
        return arrayList;
    }

    public final String getRegisteredAppPackage(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields.size() <= 0) {
            return null;
        }
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (UserHandle.getUserId(contentValues.getAsInteger("adminUid").intValue()) == i) {
                return contentValues.getAsString("pkgName");
            }
        }
        return null;
    }

    public final List getRegisteredAppsByAdminId(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (i == contentValues.getAsInteger("adminUid").intValue()) {
                    arrayList.add(contentValues.getAsString("pkgName"));
                }
            }
        }
        return arrayList;
    }

    public final String getSignature(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                PackageManagerAdapter.getInstance(this.mContext).getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(64, i, str);
            } catch (Exception e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterHelper",
                        "In getPackageCertForPkgname: Exception",
                        e);
            }
            if (packageInfo == null) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterHelper",
                        "getPackageCertForPkgname: pkgInfo is null");
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            int length = signatureArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Signature signature = signatureArr[i2];
                if (signature != null) {
                    str2 = signature.toCharsString();
                    break;
                }
                i2++;
            }
            return str2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getVersionCode(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = 0;
        try {
            try {
                PackageManagerAdapter.getInstance(this.mContext).getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(0, i, str);
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "In getVersionCode: Exception", e);
            }
            if (packageInfo == null) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "getVersionCode: pkgInfo is null");
                return 0;
            }
            i2 = packageInfo.versionCode;
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getVersionName(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                PackageManagerAdapter.getInstance(this.mContext).getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(0, i, str);
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "In getVersionName: Exception", e);
            }
            if (packageInfo == null) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "getVersionName: pkgInfo is null");
                return null;
            }
            str2 = packageInfo.versionName;
            return str2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAppAuthorized(int i, String str) {
        ArrayList dataByFields =
                this.mEDM.getDataByFields(
                        "UnManagedNwFilterMgr",
                        new String[] {"pkgName"},
                        new String[] {str},
                        new String[] {"pkgUid"});
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                if (((ContentValues) it.next()).getAsInteger("pkgUid").intValue() == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isAppRegistered(int i, String str, String str2) {
        ArrayList dataByFields =
                this.mEDM.getDataByFields(
                        "NwFilterMgr",
                        new String[] {"pkgName"},
                        new String[] {str},
                        new String[] {"adminUid", "pkgSign"});
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                int intValue = contentValues.getAsInteger("adminUid").intValue();
                String asString = contentValues.getAsString("pkgSign");
                if (UserHandle.getUserId(intValue) == i
                        && (asString == null
                                || asString.isEmpty()
                                || asString.equalsIgnoreCase(str2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isRegisterDbEmpty() {
        return this.mEDM.getDataByFields("NwFilterMgr", null, null, null).size() <= 0;
    }

    public final boolean isUserIdAuthorized(int i) {
        ArrayList dataByFields =
                this.mEDM.getDataByFields("UnManagedNwFilterMgr", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                if (UserHandle.getUserId(
                                ((ContentValues) it.next()).getAsInteger("pkgUid").intValue())
                        == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isUserIdRegistered(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                if (UserHandle.getUserId(
                                ((ContentValues) it.next()).getAsInteger("adminUid").intValue())
                        == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean removeAuthorizedInfoFromDatabase(int i, String str) {
        boolean deleteDataByFields =
                this.mEDM.deleteDataByFields(
                        "UnManagedNwFilterMgr", new String[] {"pkgName"}, new String[] {str});
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "removeAuthorizedInfoFromDatabase: result value is ",
                "knoxNwFilter-KnoxNetworkFilterHelper",
                deleteDataByFields);
        if (this.mBundleInfo.containsKey(Integer.valueOf(UserHandle.getUserId(i)))) {
            this.mBundleInfo.remove(Integer.valueOf(UserHandle.getUserId(i)));
        }
        return deleteDataByFields;
    }

    public final void removeVendorInfoFromStorage(String str) {
        boolean deleteDataByFields =
                this.mEDM.deleteDataByFields(
                        "NwFilterService", new String[] {"profileName"}, new String[] {str});
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "removeVendorInfoFromStorage: status is ",
                "knoxNwFilter-KnoxNetworkFilterHelper",
                deleteDataByFields);
        if (deleteDataByFields && KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            synchronized (KnoxNetworkFilterProfileInfo.class) {
                if (str == null) {
                    return;
                }
                ConcurrentHashMap concurrentHashMap = KnoxNetworkFilterProfileInfo.mProfileInfomap;
                if (concurrentHashMap.containsKey(str)) {}
            }
        }
    }
}
