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
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.am.ActivityManagerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxNetworkFilterHelper {
    public static final boolean DBG = Debug.semIsProductDev();
    public static KnoxNetworkFilterHelper mKnoxNetworkFilterHelper;
    public final AppOpsManager mAppOpsManager;
    public Context mContext;
    public EdmStorageProvider mEDM;

    public void addOrRemoveSystemAppFromDataSaverWhitelist(int i, boolean z) {
    }

    public boolean checkIfValidListeners(String str) {
        return true;
    }

    public KnoxNetworkFilterHelper(Context context) {
        this.mContext = context;
        this.mEDM = new EdmStorageProvider(context);
        this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
    }

    public static synchronized KnoxNetworkFilterHelper getInstance(Context context) {
        KnoxNetworkFilterHelper knoxNetworkFilterHelper;
        synchronized (KnoxNetworkFilterHelper.class) {
            if (mKnoxNetworkFilterHelper == null) {
                mKnoxNetworkFilterHelper = new KnoxNetworkFilterHelper(context);
            }
            knoxNetworkFilterHelper = mKnoxNetworkFilterHelper;
        }
        return knoxNetworkFilterHelper;
    }

    public final IPackageManager getPackageManager() {
        return AppGlobals.getPackageManager();
    }

    public final ActivityManagerService getAMSInstance() {
        return (ActivityManagerService) ServiceManager.getService("activity");
    }

    public int getUIDForPackage(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = -1;
        try {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
                if (applicationInfo != null) {
                    i2 = applicationInfo.uid;
                }
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "Exception in getUIDForPackage : " + Log.getStackTraceString(e));
            }
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isPackageInstalled(int i, String str) {
        try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 0L, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return applicationInfo != null;
        } catch (Exception e) {
            Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "Error " + Log.getStackTraceString(e));
            return false;
        }
    }

    public String getSignature(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 64, i);
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "In getPackageCertForPkgname: Exception", e);
            }
            if (packageInfo == null) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "getPackageCertForPkgname: pkgInfo is null");
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

    public String getVersionName(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 0, i);
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

    public int getVersionCode(int i, String str) {
        PackageInfo packageInfo;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i2 = 0;
        try {
            try {
                packageInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 0, i);
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

    public String getPackageNameForUid(int i) {
        String str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = getPackageManager().getNameForUid(i);
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "Exception in getPackageNameForUid : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                str = null;
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean checkIfPlatformSigned(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            IPackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 128L, i);
            if (packageManager.checkUidSignatures(applicationInfo != null ? applicationInfo.uid : -1, 1000) == 0) {
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

    public int getRegisteredAppUid(String str) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", new String[]{"pkgName"}, new String[]{str}, new String[]{"adminUid"});
        if (dataByFields != null && dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            if (it.hasNext()) {
                return ((ContentValues) it.next()).getAsInteger("adminUid").intValue();
            }
        }
        return -1;
    }

    public String getRegisteredAppPackage(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields == null || dataByFields.size() <= 0) {
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

    public List getRegisteredAppsByAdminId(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields != null && dataByFields.size() > 0) {
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

    public int getAdminIdForUser(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        int i2 = -1;
        if (dataByFields != null && dataByFields.size() > 0) {
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

    public String getRegisteredAppSign(String str) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", new String[]{"pkgName"}, new String[]{str}, new String[]{"pkgSign"});
        if (dataByFields != null && dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            if (it.hasNext()) {
                return ((ContentValues) it.next()).getAsString("pkgSign");
            }
        }
        return null;
    }

    public boolean isAppRegistered(int i, String str, String str2) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", new String[]{"pkgName"}, new String[]{str}, new String[]{"adminUid", "pkgSign"});
        if (dataByFields != null && dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                int intValue = contentValues.getAsInteger("adminUid").intValue();
                String asString = contentValues.getAsString("pkgSign");
                if (UserHandle.getUserId(intValue) == i && (asString == null || asString.isEmpty() || asString.equalsIgnoreCase(str2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isRegisterDbEmpty() {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        return dataByFields == null || dataByFields.size() <= 0;
    }

    public boolean isUserIdRegistered(int i) {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields != null && dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                if (UserHandle.getUserId(((ContentValues) it.next()).getAsInteger("adminUid").intValue()) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addRegisterInfoToDatabase(int i, String str, String str2, Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("pkgUid", (Integer) (-1));
        contentValues.put("pkgName", str);
        if (str2 != null) {
            contentValues.put("pkgSign", str2);
        }
        if (bundle != null) {
            contentValues.put("bundleInfo", bundleToBytes(bundle));
        }
        boolean putDataByFields = this.mEDM.putDataByFields("NwFilterMgr", null, null, contentValues);
        Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "addRegisterInfoToDatabase: status is " + putDataByFields);
        return putDataByFields;
    }

    public boolean removeRegisteredInfoFromDatabase(String str) {
        return this.mEDM.deleteDataByFields("NwFilterMgr", new String[]{"pkgName"}, new String[]{str});
    }

    public List getRegisterInfoFromDatabase() {
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterMgr", null, null, null);
        if (dataByFields != null && dataByFields.size() > 0) {
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

    public boolean addVendorInfoToStorage(String str, String str2, int i, String str3) {
        boolean putDataByFields;
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterService", new String[]{"profileName"}, new String[]{str}, new String[]{"profileConfig"});
        if (dataByFields != null && dataByFields.size() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("profileConfig", str2);
            putDataByFields = this.mEDM.putDataByFields("NwFilterService", new String[]{"profileName"}, new String[]{str}, contentValues);
            Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "addVendorInfoToStorage: update status is " + putDataByFields);
        } else {
            ArrayList dataByFields2 = this.mEDM.getDataByFields("NwFilterService", null, null, null);
            if (dataByFields2 != null && dataByFields2.size() > 0) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "There seems to be an existing profile, delete it before creating a new one");
                putDataByFields = false;
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("profileName", str);
                contentValues2.put("profileConfig", str2);
                contentValues2.put("pkgUid", Integer.valueOf(i));
                contentValues2.put("pkgName", str3);
                putDataByFields = this.mEDM.putDataByFields("NwFilterService", null, null, contentValues2);
                Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "addVendorInfoToStorage: new entry status is " + putDataByFields);
            }
        }
        if (putDataByFields) {
            if (!KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
                KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo = new KnoxNetworkFilterProfileInfo();
                knoxNetworkFilterProfileInfo.setProfileName(str);
                knoxNetworkFilterProfileInfo.setRulesConfig(str2);
                knoxNetworkFilterProfileInfo.setPackageName(str3);
                knoxNetworkFilterProfileInfo.setPackageUid(i);
                KnoxNetworkFilterProfileInfo.addProfileEntry(str, knoxNetworkFilterProfileInfo);
            } else {
                KnoxNetworkFilterProfileInfo.getProfileEntry(str).setRulesConfig(str2);
            }
        }
        return putDataByFields;
    }

    public boolean removeVendorInfoFromStorage(String str) {
        boolean deleteDataByFields = this.mEDM.deleteDataByFields("NwFilterService", new String[]{"profileName"}, new String[]{str});
        Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "removeVendorInfoFromStorage: status is " + deleteDataByFields);
        if (deleteDataByFields && KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            KnoxNetworkFilterProfileInfo.removeProfileEntry(str);
        }
        return deleteDataByFields;
    }

    public void initializeVendorCacheData() {
        ArrayList dataByFields = this.mEDM.getDataByFields("NwFilterService", null, null, null);
        if (dataByFields == null || dataByFields.size() <= 0) {
            return;
        }
        Iterator it = dataByFields.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("profileName");
            String asString2 = contentValues.getAsString("profileConfig");
            String asString3 = contentValues.getAsString("pkgName");
            int intValue = contentValues.getAsInteger("pkgUid").intValue();
            if (!KnoxNetworkFilterProfileInfo.containsProfileEntry(asString)) {
                KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo = new KnoxNetworkFilterProfileInfo();
                knoxNetworkFilterProfileInfo.setProfileName(asString);
                knoxNetworkFilterProfileInfo.setRulesConfig(asString2);
                knoxNetworkFilterProfileInfo.setPackageName(asString3);
                knoxNetworkFilterProfileInfo.setPackageUid(intValue);
                KnoxNetworkFilterProfileInfo.addProfileEntry(asString, knoxNetworkFilterProfileInfo);
            }
        }
    }

    public final byte[] bundleToBytes(Bundle bundle) {
        Parcel obtain = Parcel.obtain();
        obtain.writeBundle(bundle);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public void insertListenersInCache(String str, String str2, int i) {
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            KnoxNetworkFilterProfileInfo.getProfileEntry(str).setSocketConfig(str2);
            KnoxNetworkFilterProfileInfo.getProfileEntry(str).setDnsCacheStatus(i);
        }
    }

    public String retrieveListenersFromCache(String str) {
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            return KnoxNetworkFilterProfileInfo.getProfileEntry(str).getSocketConfig();
        }
        return null;
    }

    public void updateProfileState(String str, int i) {
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(str);
        if (profileEntry != null) {
            profileEntry.setState(i);
        }
    }

    public int retrieveProfileState(String str) {
        return KnoxNetworkFilterProfileInfo.getProfileEntry(str).getState();
    }

    public String getRulesConfig(String str) {
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str)) {
            return KnoxNetworkFilterProfileInfo.getProfileEntry(str).getRulesConfig();
        }
        return null;
    }

    public boolean isVendorPkgInstalled(String str) {
        Collection profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            Iterator it = profileEntries.iterator();
            while (it.hasNext()) {
                if (((KnoxNetworkFilterProfileInfo) it.next()).getPackageName().equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List getUserIdList() {
        ArrayList arrayList = new ArrayList();
        Collection profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            Iterator it = profileEntries.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(UserHandle.getUserId(((KnoxNetworkFilterProfileInfo) it.next()).getPackageUid())));
            }
        }
        return arrayList;
    }

    public List getProfileListByVendor(int i) {
        ArrayList arrayList = new ArrayList();
        Collection<KnoxNetworkFilterProfileInfo> profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : profileEntries) {
                if (knoxNetworkFilterProfileInfo.getPackageUid() == i) {
                    arrayList.add(knoxNetworkFilterProfileInfo.getProfileName());
                }
            }
        }
        return arrayList;
    }

    public String getProfilebyUserId(int i) {
        Collection<KnoxNetworkFilterProfileInfo> profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : profileEntries) {
                if (UserHandle.getUserId(knoxNetworkFilterProfileInfo.getPackageUid()) == i) {
                    return knoxNetworkFilterProfileInfo.getProfileName();
                }
            }
        }
        return null;
    }

    public List getProfileList() {
        ArrayList arrayList = new ArrayList();
        Collection profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            Iterator it = profileEntries.iterator();
            while (it.hasNext()) {
                arrayList.add(((KnoxNetworkFilterProfileInfo) it.next()).getProfileName());
            }
        }
        return arrayList;
    }

    public int getVendorUidByProfile(String str) {
        Collection<KnoxNetworkFilterProfileInfo> profileEntries = KnoxNetworkFilterProfileInfo.getProfileEntries();
        if (profileEntries != null) {
            for (KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo : profileEntries) {
                if (knoxNetworkFilterProfileInfo.getProfileName().equalsIgnoreCase(str)) {
                    return knoxNetworkFilterProfileInfo.getPackageUid();
                }
            }
        }
        return -1;
    }

    public String getConfiguredDestIpRange(String str, int i) {
        String socketConfig;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        String str2 = "";
        if (KnoxNetworkFilterProfileInfo.containsProfileEntry(str) && (socketConfig = KnoxNetworkFilterProfileInfo.getProfileEntry(str).getSocketConfig()) != null) {
            try {
                JSONObject jSONObject4 = new JSONObject(socketConfig);
                if (i == 2) {
                    JSONObject jSONObject5 = (JSONObject) jSONObject4.opt("ipv4");
                    if (jSONObject5 != null && (jSONObject3 = (JSONObject) jSONObject5.opt("misc")) != null) {
                        str2 = jSONObject3.optString("ipRange", "");
                    }
                } else if (i == 10 && (jSONObject = (JSONObject) jSONObject4.opt("ipv6")) != null && (jSONObject2 = (JSONObject) jSONObject.opt("misc")) != null) {
                    str2 = jSONObject2.optString("ipRange", "");
                }
            } catch (JSONException unused) {
            }
        }
        Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "getConfiguredIpRange: profileName " + str + " ipVersion: " + i + " ipRange: " + str2);
        return str2;
    }

    public void getConfiguredProtocols(String str) {
        String socketConfig;
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(str);
        if (profileEntry == null || (socketConfig = KnoxNetworkFilterProfileInfo.getProfileEntry(str).getSocketConfig()) == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(socketConfig);
            JSONObject jSONObject2 = (JSONObject) jSONObject.opt("ipv4");
            if (jSONObject2 != null) {
                JSONObject jSONObject3 = (JSONObject) jSONObject2.opt("dns");
                if (jSONObject3 != null) {
                    String optString = jSONObject3.optString("listenerAddress");
                    int intValue = Integer.valueOf(jSONObject3.optInt("listenerPort")).intValue();
                    if (optString != null && intValue > 0) {
                        profileEntry.setV4DnsConfigured(true);
                    }
                }
                JSONObject jSONObject4 = (JSONObject) jSONObject2.opt("tcp");
                if (jSONObject4 != null) {
                    String optString2 = jSONObject4.optString("listenerAddress");
                    int intValue2 = Integer.valueOf(jSONObject4.optInt("listenerPort")).intValue();
                    if (optString2 != null && intValue2 > 0) {
                        profileEntry.setV4TcpConfigured(true);
                    }
                }
                JSONObject jSONObject5 = (JSONObject) jSONObject2.opt("udp");
                if (jSONObject5 != null) {
                    String optString3 = jSONObject5.optString("listenerAddress");
                    int intValue3 = Integer.valueOf(jSONObject5.optInt("listenerPort")).intValue();
                    if (optString3 != null && intValue3 > 0) {
                        profileEntry.setV4UdpConfigured(true);
                    }
                }
            }
            JSONObject jSONObject6 = (JSONObject) jSONObject.opt("ipv6");
            if (jSONObject6 != null) {
                JSONObject jSONObject7 = (JSONObject) jSONObject6.opt("dns");
                if (jSONObject7 != null) {
                    String optString4 = jSONObject7.optString("listenerAddress");
                    int intValue4 = Integer.valueOf(jSONObject7.optInt("listenerPort")).intValue();
                    if (optString4 != null && intValue4 > 0) {
                        profileEntry.setV6DnsConfigured(true);
                    }
                }
                JSONObject jSONObject8 = (JSONObject) jSONObject6.opt("tcp");
                if (jSONObject8 != null) {
                    String optString5 = jSONObject8.optString("listenerAddress");
                    int intValue5 = Integer.valueOf(jSONObject8.optInt("listenerPort")).intValue();
                    if (optString5 != null && intValue5 > 0) {
                        profileEntry.setV6TcpConfigured(true);
                    }
                }
                JSONObject jSONObject9 = (JSONObject) jSONObject6.opt("udp");
                if (jSONObject9 != null) {
                    String optString6 = jSONObject9.optString("listenerAddress");
                    int intValue6 = Integer.valueOf(jSONObject9.optInt("listenerPort")).intValue();
                    if (optString6 == null || intValue6 <= 0) {
                        return;
                    }
                    profileEntry.setV6UdpConfigured(true);
                }
            }
        } catch (JSONException e) {
            Log.e("knoxNwFilter-KnoxNetworkFilterHelper", "Error parsing: getConfiguredProtocols " + Log.getStackTraceString(e));
        }
    }

    public void addOrRemoveAppsFromBatteryOptimization(String str, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    if (!((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str)) {
                        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).addPowerSaveWhitelistApp(str);
                    }
                    this.mAppOpsManager.setMode(124, i, str, 0);
                    this.mAppOpsManager.setMode(128, i, str, 0);
                    this.mAppOpsManager.setMode(129, i, str, 0);
                } else {
                    if (((PowerManager) this.mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str)) {
                        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(str);
                    }
                    this.mAppOpsManager.setMode(124, i, str, 3);
                    this.mAppOpsManager.setMode(128, i, str, 3);
                    this.mAppOpsManager.setMode(129, i, str, 3);
                }
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterHelper", " addOrRemoveAppsFromBatteryOptimization error " + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isVpnClient(int i, String str) {
        ServiceInfo[] serviceInfoArr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(str, 4L, i);
            if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
                int length = serviceInfoArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    String str2 = serviceInfoArr[i2].permission;
                    if (str2 != null && str2.equalsIgnoreCase("android.permission.BIND_VPN_SERVICE")) {
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public Set getInstalledVpnClientList(int i) {
        ServiceInfo[] serviceInfoArr;
        HashSet hashSet = new HashSet();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IPackageManager packageManager = getPackageManager();
            for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0L, i).getList()) {
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
                        if (str2 != null && str2.equalsIgnoreCase("android.permission.BIND_VPN_SERVICE")) {
                            hashSet.add(Integer.valueOf(applicationInfo.uid));
                            Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "The following package " + str + " with uid " + applicationInfo.uid + " is identified as vpn client");
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

    public void applyHttpProxyConfiguration(String str, String[] strArr, boolean z, int i, int i2) {
        KnoxNetworkFilterProfileInfo profileEntry = KnoxNetworkFilterProfileInfo.getProfileEntry(str);
        if (profileEntry == null || profileEntry.getDnsCacheStatus() != 1) {
            return;
        }
        if (z) {
            profileEntry.addAppsToBrowserList(strArr);
        } else {
            profileEntry.clearBrowserAppList();
        }
        if (strArr != null) {
            for (String str2 : strArr) {
                try {
                    int uIDForPackage = getUIDForPackage(i, str2);
                    if (uIDForPackage != -1) {
                        ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str2, 0);
                        if (getAMSInstance().checkIfProcessIsRunning(applicationInfo.processName, uIDForPackage)) {
                            Log.d("knoxNwFilter-KnoxNetworkFilterHelper", "Proxy config has been applied, going to restart the app " + str2 + "whose uid is " + uIDForPackage);
                            getAMSInstance().killApplicationProcess(applicationInfo.processName, uIDForPackage);
                        }
                        getAMSInstance().killBackgroundProcesses(str2, UserHandle.getUserId(uIDForPackage));
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
    }

    public int retrieveDnsCacheStatus(String str) {
        try {
            JSONObject jSONObject = (JSONObject) new JSONObject(str).opt("cache");
            if (jSONObject != null) {
                return jSONObject.optInt("clearCache", 0);
            }
            return 0;
        } catch (JSONException unused) {
            return 0;
        }
    }
}
