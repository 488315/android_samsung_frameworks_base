package com.android.server.enterprise.vpn.knoxvpn;

import android.app.AppGlobals;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.UserInfo;
import android.net.IVpnManager;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.INetworkManagementService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.vpn.KnoxVpnContext;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxVpnHelper {
    public KnoxVpnStorageProvider mVpnStorageProvider;
    public static final boolean DBG = Debug.semIsProductDev();
    public static KnoxVpnHelper mInstance = null;
    public static Context mContext = null;
    public static EnterpriseDeviceManager mEDM = null;
    public static NotificationManager mNotificationManager = null;
    public static KnoxVpnCredentialHandler mKnoxVpnCredentialHandler = null;
    public INetworkManagementService mNetworkManagementService = null;
    public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();

    public static synchronized KnoxVpnHelper getInstance(Context context) {
        KnoxVpnHelper knoxVpnHelper;
        synchronized (KnoxVpnHelper.class) {
            if (mInstance == null) {
                mInstance = new KnoxVpnHelper(context);
            }
            knoxVpnHelper = mInstance;
        }
        return knoxVpnHelper;
    }

    public static KnoxVpnCredentialHandler getKnoxVpnCredentialHandler() {
        if (mKnoxVpnCredentialHandler == null) {
            mKnoxVpnCredentialHandler = KnoxVpnCredentialHandler.getInstance(mContext);
        }
        return mKnoxVpnCredentialHandler;
    }

    public KnoxVpnHelper(Context context) {
        this.mVpnStorageProvider = null;
        mContext = context;
        this.mVpnStorageProvider = KnoxVpnStorageProvider.getInstance(context);
        mKnoxVpnCredentialHandler = KnoxVpnCredentialHandler.getInstance(mContext);
    }

    public final IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service = ServiceManager.getService("network_management");
        if (service != null && this.mNetworkManagementService == null) {
            this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkManagementService;
    }

    public static IPackageManager getPackageManager() {
        return AppGlobals.getPackageManager();
    }

    public boolean isPackageInstalled(String str, int i) {
        IPackageManager packageManager = getPackageManager();
        if (packageManager != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0L, i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (applicationInfo != null) {
                    if (!DBG) {
                        return true;
                    }
                    Log.d("FW-KnoxVpnHelper", "package present. application uid for user 100 = " + applicationInfo.uid);
                    return true;
                }
                Log.d("FW-KnoxVpnHelper", "package not present : " + str);
                return false;
            } catch (Exception unused) {
                Log.d("FW-KnoxVpnHelper", "Exception in isPackageInstalled");
            }
        }
        return false;
    }

    public String[] getInstalledPackages(int i) {
        try {
            IPackageManager packageManager = getPackageManager();
            ArrayList arrayList = new ArrayList();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            List list = packageManager.getInstalledApplications(0L, i).getList();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ApplicationInfo) it.next()).packageName);
            }
            String[] strArr = new String[arrayList.size()];
            Iterator it2 = arrayList.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                int i3 = i2 + 1;
                strArr[i2] = (String) it2.next();
                i2 = i3;
            }
            return strArr;
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "Exception in getInstalledPackages : " + Log.getStackTraceString(e));
            return null;
        }
    }

    public String[] getUserPackageListForProfile(String str, int i) {
        int i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String[] strArr = null;
        try {
            try {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                IPackageManager packageManager = getPackageManager();
                ArrayList arrayList = new ArrayList();
                Iterator it = packageManager.getInstalledApplications(0L, i).getList().iterator();
                while (true) {
                    i2 = 0;
                    if (!it.hasNext()) {
                        break;
                    }
                    ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
                    if (profileEntry != null) {
                        Iterator it2 = profileEntry.getExemptPackageList().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            if (((Integer) it2.next()).intValue() == applicationInfo.uid) {
                                i2 = 1;
                                break;
                            }
                        }
                    }
                    if (i2 == 0) {
                        arrayList.add(applicationInfo.packageName);
                    }
                }
                strArr = new String[arrayList.size()];
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    int i3 = i2 + 1;
                    strArr[i2] = (String) it3.next();
                    i2 = i3;
                }
            } catch (Exception e) {
                Log.d("FW-KnoxVpnHelper", "Exception in getInstalledPackages : " + Log.getStackTraceString(e));
            }
            return strArr;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getUIDForPackage(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 128L, i);
                boolean z = DBG;
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "application uid for info :  " + applicationInfo);
                }
                int i2 = applicationInfo != null ? applicationInfo.uid : -1;
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "application uid for user " + i + " = " + i2);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return i2;
            } catch (Exception e) {
                Log.d("FW-KnoxVpnHelper", "Exception in getUIDForPackage : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public String getPackageNameForUid(int i) {
        try {
            return getPackageManager().getNameForUid(i);
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "Exception in getPackageNameForUid : " + Log.getStackTraceString(e));
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean addVpnProfileToDatabase(KnoxVpnContext knoxVpnContext, String str, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        String str2;
        String str3;
        int i8;
        String string;
        boolean z = DBG;
        if (z) {
            Log.d("FW-KnoxVpnHelper", "jsonProfile value is <-->" + str);
        }
        int i9 = knoxVpnContext.adminId;
        String str4 = knoxVpnContext.vendorName;
        int i10 = knoxVpnContext.personaId;
        String personifiedName = getPersonifiedName(i10, str4);
        String str5 = KnoxVpnConstants.DEFAULT_PROXY_SERVER;
        String str6 = KnoxVpnConstants.DEFAULT_PAC_URL;
        JSONObject jSONObject = new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS");
        JSONObject jSONObject2 = jSONObject.getJSONObject("profile_attribute");
        JSONObject jSONObject3 = jSONObject.getJSONObject("knox");
        Log.d("FW-KnoxVpnHelper", "Inside addVpnProfileToDatabase <-->");
        if (jSONObject3 != null) {
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Inside knox specific AttrObj != null <-->");
            }
            i6 = jSONObject3.optInt("chaining_enabled", -1);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Value for chainingEnabled <-->" + i6);
            }
            String optString = jSONObject3.optString("proxy-server", str5);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Value for proxyServer <-->" + optString);
            }
            i4 = jSONObject3.optInt("proxy-port", -1);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Value for proxyPort <-->" + i4);
            }
            String optString2 = jSONObject3.optString("pac-url", str6);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Value for pacurl <-->" + optString2);
            }
            i5 = jSONObject3.optInt("proxy-auth", 0);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "Value for proxy_auth <-->" + i5);
            }
            int optInt = jSONObject3.optInt("uidpid_search_enabled", 0);
            if (z) {
                StringBuilder sb = new StringBuilder();
                str2 = optString;
                sb.append("Value for uidPidSearchEnabled <-->");
                sb.append(optInt);
                Log.d("FW-KnoxVpnHelper", sb.toString());
            } else {
                str2 = optString;
            }
            int optInt2 = jSONObject3.optInt("allow_usb_over_vpn_tethering", 0);
            if (z) {
                StringBuilder sb2 = new StringBuilder();
                str3 = optString2;
                sb2.append("Value for usb_tethering <-->");
                sb2.append(optInt2);
                Log.d("FW-KnoxVpnHelper", sb2.toString());
            } else {
                str3 = optString2;
            }
            try {
                string = jSONObject3.getString("connectionType");
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "connection type value = " + string);
                }
            } catch (Exception unused) {
            }
            if (!"keepon".equals(string.toLowerCase())) {
                i8 = "ondemand".equals(string.toLowerCase()) ? 1 : -1;
                if (DBG) {
                }
                i3 = optInt2;
                i2 = i8;
                str5 = str2;
                str6 = str3;
                i7 = optInt;
            } else {
                i8 = 0;
                if (DBG) {
                    Log.d("FW-KnoxVpnHelper", "Value for vpnConnectionType <-->" + i8);
                }
                i3 = optInt2;
                i2 = i8;
                str5 = str2;
                str6 = str3;
                i7 = optInt;
            }
        } else {
            i2 = -1;
            i3 = 0;
            i4 = -1;
            i5 = 0;
            i6 = -1;
            i7 = 0;
        }
        String string2 = jSONObject2.getString("profileName");
        String string3 = jSONObject2.getString("vpn_type");
        int i11 = jSONObject2.getInt("vpn_route_type");
        int i12 = i11 == 0 ? 1 : 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileName", string2);
        contentValues.put("adminUid", Integer.valueOf(i9));
        contentValues.put("personaId", Integer.valueOf(i10));
        contentValues.put("vendorName", personifiedName);
        contentValues.put("profileId", Integer.valueOf(i));
        contentValues.put("protocolType", string3);
        contentValues.put("defaultRoute", Integer.valueOf(i11));
        contentValues.put("activateState", Integer.valueOf(i12));
        contentValues.put("vpnServiceType", (Integer) (-1));
        contentValues.put("chainingEnabled", Integer.valueOf(i6));
        contentValues.put("uidPidSearch", Integer.valueOf(i7));
        contentValues.put("vpnConnectionType", Integer.valueOf(i2));
        contentValues.put("proxyServer", str5);
        contentValues.put("proxyPort", Integer.valueOf(i4));
        contentValues.put("pacurl", str6);
        contentValues.put("proxyAuth", Integer.valueOf(i5));
        contentValues.put("usbTethering", Integer.valueOf(i3));
        contentValues.put("profileJson", str);
        boolean putDataByFields = this.mVpnStorageProvider.putDataByFields("VpnProfileInfo", null, null, contentValues);
        if (DBG) {
            Log.d("FW-KnoxVpnHelper", "add profile in database : status is " + putDataByFields + "profile Name is" + string2);
        }
        return putDataByFields;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00f8 A[Catch: Exception -> 0x01af, TryCatch #0 {Exception -> 0x01af, blocks: (B:3:0x0017, B:5:0x0035, B:7:0x003f, B:8:0x0053, B:10:0x005c, B:11:0x0070, B:13:0x0079, B:14:0x008d, B:26:0x00ec, B:28:0x00f8, B:29:0x0111), top: B:2:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean addVpnProfileToMap(KnoxVpnContext knoxVpnContext, String str, int i) {
        String str2;
        JSONObject jSONObject;
        String str3;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        String str4;
        String str5;
        int i8;
        int i9;
        String string;
        int i10 = knoxVpnContext.adminId;
        String str6 = knoxVpnContext.vendorName;
        int i11 = knoxVpnContext.personaId;
        String personifiedName = getPersonifiedName(i11, str6);
        String str7 = KnoxVpnConstants.DEFAULT_PROXY_SERVER;
        String str8 = KnoxVpnConstants.DEFAULT_PROXY_CREDENTIALS;
        String str9 = KnoxVpnConstants.DEFAULT_PAC_URL;
        try {
            JSONObject jSONObject2 = new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS");
            jSONObject = jSONObject2.getJSONObject("profile_attribute");
            JSONObject optJSONObject = jSONObject2.optJSONObject("knox");
            if (optJSONObject != null) {
                i5 = optJSONObject.optInt("chaining_enabled", -1);
                boolean z = DBG;
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "Value for chainingEnabled <-->" + i5);
                }
                String optString = optJSONObject.optString("proxy-server", str7);
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "Value for proxyServer <-->" + optString);
                }
                i3 = optJSONObject.optInt("proxy-port", -1);
                if (z) {
                    Log.d("FW-KnoxVpnHelper", "Value for proxyPort <-->" + i3);
                }
                String optString2 = optJSONObject.optString("proxy-username", str8);
                String optString3 = optJSONObject.optString("proxy-password", str8);
                String optString4 = optJSONObject.optString("pac-url", str9);
                i7 = optJSONObject.optInt("proxy-auth", 0);
                try {
                    string = optJSONObject.getString("connectionType");
                    if (z) {
                        StringBuilder sb = new StringBuilder();
                        str5 = optString;
                        try {
                            sb.append("connection type value = ");
                            sb.append(string);
                            Log.d("FW-KnoxVpnHelper", sb.toString());
                        } catch (Exception unused) {
                        }
                    } else {
                        str5 = optString;
                    }
                } catch (Exception unused2) {
                    str5 = optString;
                }
                if (!"keepon".equals(string.toLowerCase())) {
                    i8 = "ondemand".equals(string.toLowerCase()) ? 1 : -1;
                    int optInt = optJSONObject.optInt("uidpid_search_enabled", 0);
                    if (DBG) {
                    }
                    i2 = optJSONObject.optInt("allow_usb_over_vpn_tethering", 0);
                    i6 = optInt;
                    str7 = str5;
                    str2 = "FW-KnoxVpnHelper";
                    str3 = optString4;
                    str4 = optString3;
                    str8 = optString2;
                    i4 = i9;
                } else {
                    i8 = 0;
                    int optInt2 = optJSONObject.optInt("uidpid_search_enabled", 0);
                    if (DBG) {
                        i9 = i8;
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        i9 = i8;
                        sb2.append("Admin has enabled id tracking feature in addVpnProfileToMap");
                        sb2.append(optInt2);
                        Log.d("FW-KnoxVpnHelper", sb2.toString());
                    }
                    i2 = optJSONObject.optInt("allow_usb_over_vpn_tethering", 0);
                    i6 = optInt2;
                    str7 = str5;
                    str2 = "FW-KnoxVpnHelper";
                    str3 = optString4;
                    str4 = optString3;
                    str8 = optString2;
                    i4 = i9;
                }
            } else {
                str2 = "FW-KnoxVpnHelper";
                str3 = str9;
                i2 = 0;
                i3 = -1;
                i4 = -1;
                i5 = -1;
                i6 = 0;
                i7 = 0;
                str4 = str8;
            }
        } catch (Exception e) {
            e = e;
            str2 = "FW-KnoxVpnHelper";
        }
        try {
            String string2 = jSONObject.getString("profileName");
            int i12 = i7;
            String string3 = jSONObject.getString("vpn_type");
            int i13 = i2;
            int i14 = jSONObject.getInt("vpn_route_type");
            int i15 = i14 == 0 ? 1 : 0;
            if (string2 == null) {
                return true;
            }
            String str10 = str3;
            VpnProfileInfo vpnProfileInfo = new VpnProfileInfo();
            vpnProfileInfo.setProfileName(string2);
            vpnProfileInfo.setAdminId(i10);
            vpnProfileInfo.setPersonaId(i11);
            vpnProfileInfo.setVendorPkgName(personifiedName);
            vpnProfileInfo.setVendorUid(getUidForPackageName(personifiedName));
            vpnProfileInfo.setProfileId(i);
            vpnProfileInfo.setProtocolType(string3);
            vpnProfileInfo.setActivateState(i15);
            vpnProfileInfo.setRouteType(i14);
            vpnProfileInfo.setMarkProfileForDeletion(false);
            vpnProfileInfo.setVpnConnectionType(i4);
            vpnProfileInfo.setChainingEnabled(i5);
            vpnProfileInfo.setUidPidSearchEnabled(i6);
            vpnProfileInfo.setProxyServer(str7);
            vpnProfileInfo.setProxyPort(i3);
            vpnProfileInfo.setProxyUsername(str8);
            vpnProfileInfo.setProxyPassword(str4);
            vpnProfileInfo.setPacurl(str10);
            vpnProfileInfo.setUsbTethering(i13);
            if (str8 != null && str4 != null) {
                vpnProfileInfo.proxyCredentialsPreDefined(true);
            }
            vpnProfileInfo.setPacAuthRequired(i12);
            this.vpnConfig.addProfileEntry(string2, vpnProfileInfo);
            return true;
        } catch (Exception e2) {
            e = e2;
            Log.e(str2, "get profilename from json : Error parsing JSON \n" + Log.getStackTraceString(e));
            return false;
        }
    }

    public String getProfileNameFromJsonString(String str) {
        try {
            return new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS").getJSONObject("profile_attribute").getString("profileName");
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "get profilename from json : Error parsing JSON \n" + Log.getStackTraceString(e));
            return null;
        }
    }

    public boolean isWideVpnExists(int i) {
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{getPersonifiedName(i, "ADD_ALL_PACKAGES")}, new String[]{"profileName"});
            if (dataByFields != null) {
                return dataByFields.size() > 0;
            }
            return false;
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "isPackageExists \n" + Log.getStackTraceString(e));
            return false;
        }
    }

    public int getAdminIdForUserVpn(int i) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
            Iterator it = vpnProfileInfo.getPackageList().iterator();
            while (it.hasNext()) {
                if (((VpnPackageInfo) it.next()).getPackageName().equalsIgnoreCase(getPersonifiedName(i, "ADD_ALL_PACKAGES"))) {
                    return vpnProfileInfo.getAdminId();
                }
            }
        }
        if (DBG) {
            Log.d("FW-KnoxVpnHelper", "getAdminIdForUserVpn: admin id value is -1for user " + i);
        }
        return -1;
    }

    public boolean isUsingKnoxPackageExists(int i) {
        boolean z = false;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", new String[]{"packageCid"}, new String[]{Integer.toString(i)}, new String[]{"packageName"});
            if (dataByFields == null || dataByFields.size() <= 0) {
                return false;
            }
            Iterator it = dataByFields.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                try {
                    if (((ContentValues) it.next()).getAsString("packageName").equalsIgnoreCase(getPersonifiedName(i, "ADD_ALL_PACKAGES"))) {
                        return false;
                    }
                    z2 = true;
                } catch (Exception e) {
                    e = e;
                    z = z2;
                    Log.e("FW-KnoxVpnHelper", "isUsingKnoxPackageExists \n" + Log.getStackTraceString(e));
                    return z;
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public String getPersonifiedName(int i, String str) {
        try {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "vpn getPersonifiedName : container id is regular device");
            }
            return i + "_" + str;
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception getting getPersonifiedName : Error :  " + Log.getStackTraceString(e));
            return null;
        }
    }

    public String getRegularPackageName(String str) {
        if (str != null) {
            return str.substring(str.indexOf("_") + 1);
        }
        return null;
    }

    public int getContainerIdFromPackageName(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str.split("_")[0]);
        } catch (Exception unused) {
            return -1;
        }
    }

    public boolean checkIfVpnProfileTableIsEmpty(String str) {
        boolean z = true;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", new String[]{"vendorName"}, new String[]{str}, null);
            if (dataByFields != null) {
                if (dataByFields.size() > 0) {
                    z = false;
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at checkIfKnoxVpnDatabaseIsEmpty API: " + Log.getStackTraceString(e));
        }
        Log.d("FW-KnoxVpnHelper", "checkIfKnoxVpnDatabaseIsEmpty: tableEmpty value is " + z);
        return z;
    }

    public int checkIfProfileHasChainingFeature(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return -1;
            }
            if (profileEntry.getChainingEnabled() == 1) {
                return 1;
            }
            return profileEntry.getChainingEnabled() == 0 ? 0 : -1;
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at checkIfProfileHasChainingFeature" + Log.getStackTraceString(e));
            return -1;
        }
    }

    public String getProfileOwningThePackage(String str) {
        String str2 = null;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{str}, new String[]{"profileName"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str2 = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at getProfileOwningThePackage " + Log.getStackTraceString(e));
        }
        return str2;
    }

    public String getProfileOwningTheUid(int i) {
        String str = null;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at getProfileOwningTheUid " + Log.getStackTraceString(e));
        }
        return str;
    }

    public String getProfileOwningTheUidWithNoInternetPermission(int i) {
        String str = null;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at getProfileOwningTheUidWithNoInternetPermission " + Log.getStackTraceString(e));
        }
        return str;
    }

    public boolean chainingForAddAll(String str, int i) {
        Log.d("FW-KnoxVpnHelper", "chainingForAddAll: profileName value is " + str);
        boolean z = false;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnPackageInfo", new String[]{"packageCid"}, new String[]{Integer.toString(i)}, new String[]{"packageName"});
            if (dataByFields != null && dataByFields.size() > 0 && checkIfProfileHasChainingFeature(str) == 1) {
                boolean z2 = DBG;
                if (z2) {
                    Log.d("FW-KnoxVpnHelper", "chainingForAddAll: checkIfProfileHasChainingFeature value is true");
                }
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry != null) {
                    Iterator it = dataByFields.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String asString = ((ContentValues) it.next()).getAsString("packageName");
                        if (DBG) {
                            Log.d("FW-KnoxVpnHelper", "chainingForAddAll: packageName value is " + asString);
                        }
                        if (asString.equalsIgnoreCase(profileEntry.getVendorPkgName())) {
                            Log.d("FW-KnoxVpnHelper", "chainingForAddAll: packageName and vendorName are same");
                            z = true;
                            break;
                        }
                    }
                } else {
                    if (z2) {
                        Log.d("FW-KnoxVpnHelper", "chainingForAddAll: profile hashmap value is " + profileEntry);
                    }
                    return false;
                }
            }
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "chainingForAddAll: profileName value is " + z);
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at enableChainingForAddAll API " + Log.getStackTraceString(e));
        }
        return z;
    }

    public int getUidForPackageName(String str) {
        boolean z = DBG;
        if (z) {
            Log.d("FW-KnoxVpnHelper", "getUidForPackageName: transformedPackageName value is " + str);
        }
        int i = -1;
        try {
            int containerIdFromPackageName = getContainerIdFromPackageName(str);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "getUidForPackageName: userIdOfVendor value is " + containerIdFromPackageName);
            }
            i = getUIDForPackage(containerIdFromPackageName, getRegularPackageName(str));
            if (z) {
                Log.d("FW-KnoxVpnHelper", "getUidForPackageName: uidOfVendor value is " + i);
            }
        } catch (Exception e) {
            if (DBG) {
                Log.e("FW-KnoxVpnHelper", "Exception at getUidForPackageName API " + Log.getStackTraceString(e));
            }
        }
        return i;
    }

    public int checkIfChainingEnabledForVendor(int i, boolean z) {
        int chainingEnabled;
        int i2 = -2;
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
                if (vpnProfileInfo.getVendorUid() == i) {
                    if (z) {
                        chainingEnabled = vpnProfileInfo.getChainingEnabled();
                        if (chainingEnabled != -1) {
                            i2 = chainingEnabled;
                            break;
                        }
                    } else {
                        chainingEnabled = vpnProfileInfo.getChainingEnabled();
                        if (chainingEnabled == -1) {
                            i2 = chainingEnabled;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                Log.e("FW-KnoxVpnHelper", "Exception at checkIfChainingEnabledForVendor " + Log.getStackTraceString(e));
            }
        }
        if (DBG) {
            Log.d("FW-KnoxVpnHelper", "is chaining feature enabled  " + i2 + "for vendor " + i);
        }
        return i2;
    }

    public boolean isPackageForAddAllPackages(VpnPackageInfo vpnPackageInfo) {
        return vpnPackageInfo.getUid() == -2 && vpnPackageInfo.getPackageName().equalsIgnoreCase(getPersonifiedName(getContainerIdFromPackageName(vpnPackageInfo.getPackageName()), "ADD_ALL_PACKAGES"));
    }

    public boolean checkIfProfileListEmpty() {
        boolean z = true;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", null, null, null);
            if (dataByFields != null) {
                if (dataByFields.size() > 0) {
                    z = false;
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "Exception at checkIfProfileListEmpty API " + Log.getStackTraceString(e));
        }
        Log.d("FW-KnoxVpnHelper", "checkIfProfileListEmpty:profileListEmpty value is " + z);
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0013, code lost:
    
        if (com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.DBG == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
    
        android.util.Log.d("FW-KnoxVpnHelper", "checkIfUidIsBlackListed: mBlacklistedApplication value is " + r3[r2]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
    
        r1 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean checkIfUidIsBlackListed(int i, int i2) {
        boolean z = false;
        int i3 = 0;
        while (true) {
            try {
                String[] strArr = KnoxVpnConstants.BLACK_LISTED_APPLICATION;
                if (i3 >= strArr.length) {
                    break;
                }
                if (getUIDForPackage(i2, strArr[i3]) == i) {
                    break;
                }
                i3++;
            } catch (Exception e) {
                Log.e("FW-KnoxVpnHelper", "Exception at checkIfUidIsBlackListed API " + Log.getStackTraceString(e));
            }
        }
        if (DBG) {
            Log.d("FW-KnoxVpnHelper", "checkIfUidIsBlackListed: isUidBackListed value is " + z);
        }
        return z;
    }

    public boolean checkIfAddAllPackage(String str) {
        return str.equalsIgnoreCase(getPersonifiedName(getContainerIdFromPackageName(str), "ADD_ALL_PACKAGES"));
    }

    public HashMap getListOfUid(String str) {
        HashMap hashMap = null;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return null;
            }
            HashMap hashMap2 = new HashMap();
            try {
                for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
                    if (vpnPackageInfo.getPackageName().contains("ADD_ALL_PACKAGES")) {
                        int containerIdFromPackageName = getContainerIdFromPackageName(vpnPackageInfo.getPackageName());
                        Log.d("FW-KnoxVpnHelper", "knox vpn proxy settings is going to be applied for the user " + containerIdFromPackageName);
                        hashMap2.put(Integer.valueOf(containerIdFromPackageName), Boolean.TRUE);
                    } else {
                        int uidForPackageName = getUidForPackageName(vpnPackageInfo.getPackageName());
                        Log.d("FW-KnoxVpnHelper", "knox vpn proxy settings is going to be applied for the app whose uid is " + uidForPackageName);
                        if (uidForPackageName != -1) {
                            hashMap2.put(Integer.valueOf(uidForPackageName), Boolean.FALSE);
                        }
                    }
                }
                return hashMap2;
            } catch (Exception unused) {
                hashMap = hashMap2;
                Log.e("FW-KnoxVpnHelper", "Exception occured while getting the list of uid added to the vpn profile");
                return hashMap;
            }
        } catch (Exception unused2) {
        }
    }

    public HashMap updateProxyList(int i, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.valueOf(i), Boolean.valueOf(z));
        return hashMap;
    }

    public void setProxyFlagForEmail(int i, boolean z) {
        if (z) {
            SystemProperties.set("net.vpn.proxy.email." + i, "1");
            return;
        }
        SystemProperties.set("net.vpn.proxy.email." + i, "0");
    }

    public void sendProxyConfigBroadcast(HashMap hashMap, int i, HashSet hashSet) {
        KnoxVpnHelper knoxVpnHelper = this;
        int i2 = i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (Integer num : hashMap.keySet()) {
                    Intent intent = new Intent("android.intent.action.PROXY_CHANGE");
                    intent.addFlags(603979776);
                    if (num.intValue() == i2) {
                        Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to vpn client for per-app use-case " + num);
                    } else {
                        if (((Boolean) hashMap.get(num)).booleanValue()) {
                            String packageNameForUid = knoxVpnHelper.getPackageNameForUid(i2);
                            String[] installedPackages = knoxVpnHelper.getInstalledPackages(num.intValue());
                            if (installedPackages != null) {
                                for (String str : installedPackages) {
                                    if (packageNameForUid.equalsIgnoreCase(str)) {
                                        Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to vpn client for addAll use-case " + packageNameForUid);
                                    } else {
                                        Iterator it = hashSet.iterator();
                                        boolean z = false;
                                        while (it.hasNext()) {
                                            int intValue = ((Integer) it.next()).intValue();
                                            if (UserHandle.getUserId(intValue) == num.intValue()) {
                                                String[] packagesForUid = getPackageManager().getPackagesForUid(intValue);
                                                if (packagesForUid != null) {
                                                    int length = packagesForUid.length;
                                                    int i3 = 0;
                                                    while (true) {
                                                        if (i3 >= length) {
                                                            break;
                                                        }
                                                        int i4 = length;
                                                        String str2 = packagesForUid[i3];
                                                        if (str2.equalsIgnoreCase(str)) {
                                                            Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to exempted uid for addAll use-case " + str2);
                                                            z = true;
                                                            break;
                                                        }
                                                        i3++;
                                                        length = i4;
                                                    }
                                                }
                                            }
                                            if (z) {
                                                break;
                                            }
                                        }
                                        if (!z) {
                                            if (DBG) {
                                                Log.d("FW-KnoxVpnHelper", "sending proxy broadcast to the app added to knox vpn profile for addAll use-case " + str);
                                            }
                                            intent.setPackage(str);
                                            mContext.sendBroadcastAsUser(intent, new UserHandle(num.intValue()));
                                        }
                                    }
                                }
                            }
                        } else {
                            String[] packagesForUid2 = getPackageManager().getPackagesForUid(num.intValue());
                            if (packagesForUid2 != null) {
                                for (String str3 : packagesForUid2) {
                                    if (DBG) {
                                        Log.d("FW-KnoxVpnHelper", "sending proxy broadcast to the app added to knox vpn profile for per use-case " + str3);
                                    }
                                    intent.setPackage(str3);
                                    mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num.intValue())));
                                }
                            }
                        }
                        knoxVpnHelper = this;
                        i2 = i;
                    }
                }
            } catch (Exception e) {
                Log.e("FW-KnoxVpnHelper", "Exception occured while sending proxy broadcast to knox vpn apps " + Log.getStackTraceString(e));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeProfileCredentials(String str) {
        try {
            getKnoxVpnCredentialHandler().deleteCredentialsFromKeystore(str + "_proxy-username");
            getKnoxVpnCredentialHandler().deleteCredentialsFromKeystore(str + "_proxy-password");
            getKnoxVpnCredentialHandler().deleteCredentialsFromKeystore(str + "_captivecert_pwd");
            getKnoxVpnCredentialHandler().deleteCredentialsFromKeystore(str + "_cacert_pwd");
            getKnoxVpnCredentialHandler().deleteCredentialsFromKeystore(str + "_servercert_pwd");
        } catch (Exception e) {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "Exception occured while removing the profile credentials " + Log.getStackTraceString(e));
            }
        }
    }

    public String retrieveProfileCredentials(String str, String str2) {
        try {
            return getKnoxVpnCredentialHandler().retrieveCredentialsFromKeystore(str + "_" + str2);
        } catch (Exception e) {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "Exception occured while Retrieve the profile credentials " + Log.getStackTraceString(e));
            }
            return null;
        }
    }

    public boolean saveProfileCredentials(String str, String str2) {
        try {
            return getKnoxVpnCredentialHandler().storeCredentialsInKeystore(str, str2);
        } catch (Exception e) {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "Exception occured while saving the profile credentials " + Log.getStackTraceString(e));
            }
            return false;
        }
    }

    public String getActiveNetworkInterface() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = null;
        try {
            try {
                str = getVpnManagerService().getActiveDefaultInterface();
                Log.d("FW-KnoxVpnHelper", "The active interface name is " + str);
            } catch (Throwable unused) {
                Log.e("FW-KnoxVpnHelper", "Exception occured while trying to get the active interface details");
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public HashSet getListOfActiveUsers() {
        HashSet hashSet = new HashSet();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (UserInfo userInfo : ((UserManager) mContext.getSystemService("user")).getUsers()) {
                    Log.d("FW-KnoxVpnHelper", "the list of active users are " + userInfo.id);
                    hashSet.add(Integer.valueOf(userInfo.id));
                }
            } catch (Exception e) {
                Log.e("FW-KnoxVpnHelper", "Exception occured while trying to get the list of active users " + Log.getStackTraceString(e));
            }
            return hashSet;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int startUid(int i) {
        int i2 = (i * 100000) + 1;
        try {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "startUid - The Start uid for the persona for which dns is going to be applied is" + i2);
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "startUidOfPersona Error" + Log.getStackTraceString(e));
        }
        return i2;
    }

    public int stopUid(int i) {
        int i2 = (i * 100000) + 1;
        int i3 = -1;
        try {
            boolean z = DBG;
            if (z) {
                Log.d("FW-KnoxVpnHelper", "stopUid - The Start uid for the persona for which dns is going to be applied is" + i2);
            }
            i3 = 99999 + (i2 - 1);
            if (z) {
                Log.d("FW-KnoxVpnHelper", "stopUid - The last uid for the persona for which dns is going to be applied is" + i3);
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "stopUidOfPersona Error" + Log.getStackTraceString(e));
        }
        return i3;
    }

    public boolean addExemptedListToDatabase(String str, String str2, int i) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("profileName", str);
            contentValues.put("packageName", str2);
            contentValues.put("packageUid", Integer.valueOf(i));
            return this.mVpnStorageProvider.putDataByFields("vpnExemptInfo", null, null, contentValues);
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "addExemptedListToDatabase : Exception:" + Log.getStackTraceString(e));
            return false;
        }
    }

    public boolean updateExemptedListToDatabase(String str, String str2, int i) {
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str2}, new String[]{"profileName", "packageUid"});
            if (dataByFields == null || dataByFields.size() <= 0) {
                return false;
            }
            Log.d("FW-KnoxVpnHelper", "updating exempted application details after install-uninstall " + str2 + i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageUid", Integer.valueOf(i));
            return this.mVpnStorageProvider.putDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str2}, contentValues);
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "updateExemptedListToDatabase : Exception:" + Log.getStackTraceString(e));
            return false;
        }
    }

    public boolean removeExemptedListToDatabase(String str, int i) {
        try {
            return this.mVpnStorageProvider.deleteDataByFields("vpnExemptInfo", new String[]{"packageUid"}, new String[]{Integer.toString(i)});
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "removeExemptedListToDatabase : Exception:" + Log.getStackTraceString(e));
            return false;
        }
    }

    public boolean removeExemptedListToDatabase(String str, String str2) {
        try {
            return this.mVpnStorageProvider.deleteDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str2});
        } catch (Exception e) {
            Log.d("FW-KnoxVpnHelper", "removeExemptedListToDatabase : Exception:" + Log.getStackTraceString(e));
            return false;
        }
    }

    public void getExemptedListFromDatabase(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        HashSet exemptPackageList = profileEntry.getExemptPackageList();
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid"});
            if (dataByFields != null) {
                try {
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        exemptPackageList.add(Integer.valueOf(((ContentValues) it.next()).getAsInteger("packageUid").intValue()));
                    }
                } catch (Exception e) {
                    Log.e("FW-KnoxVpnHelper", "getExemptedListFromDatabase " + Log.getStackTraceString(e));
                }
            }
        } catch (Exception e2) {
            Log.d("FW-KnoxVpnHelper", "getExemptedListFromDatabase : Exception:" + Log.getStackTraceString(e2));
        }
    }

    public HashSet getUninsalledAppsFromExemptedList(int i, String str) {
        String asString;
        HashSet hashSet = new HashSet();
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid", "packageName"});
            if (dataByFields != null) {
                try {
                    Iterator it = dataByFields.iterator();
                    while (it.hasNext()) {
                        ContentValues contentValues = (ContentValues) it.next();
                        if (contentValues.getAsInteger("packageUid").intValue() == -1 && (asString = contentValues.getAsString("packageName")) != null && getContainerIdFromPackageName(asString) == i) {
                            String regularPackageName = getRegularPackageName(asString);
                            Log.d("FW-KnoxVpnHelper", "adding uninstalledPackage to exempt list " + regularPackageName);
                            hashSet.add(regularPackageName);
                        }
                    }
                } catch (Exception e) {
                    Log.e("FW-KnoxVpnHelper", "getExemptedListFromDatabase " + Log.getStackTraceString(e));
                }
            }
        } catch (Exception e2) {
            Log.d("FW-KnoxVpnHelper", "getExemptedListFromDatabase : Exception:" + Log.getStackTraceString(e2));
        }
        return hashSet;
    }

    public HashSet getUninsalledAppsFromExemptedList(String str) {
        String asString;
        HashSet hashSet = new HashSet();
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid", "packageName"});
            if (dataByFields != null) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (contentValues.getAsInteger("packageUid").intValue() == -1 && (asString = contentValues.getAsString("packageName")) != null) {
                        hashSet.add(asString);
                    }
                }
            }
        } catch (Exception unused) {
        }
        return hashSet;
    }

    public int checkIfUidHasInternetPermission(int i) {
        int checkUidPermission;
        ArrayList dataByFields;
        int i2 = 2;
        try {
            checkUidPermission = getPackageManager().checkUidPermission("android.permission.INTERNET", i);
            dataByFields = this.mVpnStorageProvider.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"packageUid"});
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while checking if uid has been upgraded with internet permission");
        }
        if (dataByFields != null) {
            if (dataByFields.size() > 0) {
                if (checkUidPermission == 0) {
                    i2 = 0;
                }
                Log.d("FW-KnoxVpnHelper", "checkIfUidHasInternetPermission for uid " + i + " return value is " + i2);
                return i2;
            }
        }
        if (checkUidPermission != 0) {
            i2 = 1;
        }
        Log.d("FW-KnoxVpnHelper", "checkIfUidHasInternetPermission for uid " + i + " return value is " + i2);
        return i2;
    }

    public String getProfileNameForPermissionUpdatedApp(int i) {
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields == null || dataByFields.size() <= 0) {
                return null;
            }
            Iterator it = dataByFields.iterator();
            if (it.hasNext()) {
                return ((ContentValues) it.next()).getAsString("profileName");
            }
            return null;
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while checking if uid has been upgraded with internet permission");
            return null;
        }
    }

    public void updateIpChainNameForProfile(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("vpnIpChainName", str2);
        Log.d("FW-KnoxVpnHelper", "updateIpChainNameForProfile " + this.mVpnStorageProvider.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues));
    }

    public void removePackagesFromPermissionDb(String str, int i) {
        ArrayList arrayList = new ArrayList();
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("vpnNoInternetPermission", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid"});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    int intValue = ((ContentValues) it.next()).getAsInteger("packageUid").intValue();
                    if (intValue != -1 && UserHandle.getUserId(intValue) == i) {
                        arrayList.add(Integer.valueOf(intValue));
                    }
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mVpnStorageProvider.deleteDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(((Integer) it2.next()).intValue())});
            }
        } catch (Exception unused) {
        }
    }

    public void updateUsbTetheringForProfileInDb(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("usbTethering", Integer.valueOf(i));
        Log.d("FW-KnoxVpnHelper", "updateUsbTetheringForProfileInDb " + this.mVpnStorageProvider.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues));
    }

    public void updateUsbTetherAuthDetails(String str, Bundle bundle, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("usbTetheringAuth", (Integer) 1);
            if (bundle.containsKey("key-tether-auth-login-page")) {
                contentValues.put("tetherLoginpage", bundle.getString("key-tether-auth-login-page"));
            } else {
                contentValues.putNull("tetherLoginpage");
            }
            if (bundle.containsKey("key-tether-auth-response-page")) {
                contentValues.put("tetherResponsePage", bundle.getString("key-tether-auth-response-page"));
            } else {
                contentValues.putNull("tetherResponsePage");
            }
            if (bundle.containsKey("key-tether-client-certificate-issuer-cn")) {
                contentValues.put("tetherClientCertIssuerCN", bundle.getString("key-tether-client-certificate-issuer-cn"));
            } else {
                contentValues.putNull("tetherClientCertIssuerCN");
            }
            if (bundle.containsKey("key-tether-client-certificate-issued-cn")) {
                contentValues.put("tetherClientCertIssuedCN", bundle.getString("key-tether-client-certificate-issued-cn"));
            } else {
                contentValues.putNull("tetherClientCertIssuedCN");
            }
            if (bundle.containsKey("key-tether-captive-portal-alias")) {
                contentValues.put("tetherCaptivePortalAlias", bundle.getString("key-tether-captive-portal-alias"));
            } else {
                contentValues.putNull("tetherCaptivePortalAlias");
            }
            if (bundle.containsKey("key-tether-captive-portal-certificate")) {
                contentValues.put("tetherCaptivePortalCert", bundle.getByteArray("key-tether-captive-portal-certificate"));
            } else {
                contentValues.putNull("tetherCaptivePortalCert");
            }
            if (bundle.containsKey("key-tether-ca-alias")) {
                contentValues.put("tetherCAlias", bundle.getString("key-tether-ca-alias"));
            } else {
                contentValues.putNull("tetherCAlias");
            }
            if (bundle.containsKey("key-tether-ca-certificate")) {
                contentValues.put("tetherCACert", bundle.getByteArray("key-tether-ca-certificate"));
            } else {
                contentValues.putNull("tetherCACert");
            }
            if (bundle.containsKey("key-tether-user-certificate")) {
                contentValues.put("tetherServerCert", bundle.getByteArray("key-tether-user-certificate"));
            } else {
                contentValues.putNull("tetherServerCert");
            }
            if (bundle.containsKey("key-tether-user-alias")) {
                contentValues.put("tetherServerAlias", bundle.getString("key-tether-user-alias"));
            } else {
                contentValues.putNull("tetherServerAlias");
            }
        } else {
            contentValues.put("usbTetheringAuth", (Integer) 0);
            contentValues.putNull("tetherLoginpage");
            contentValues.putNull("tetherResponsePage");
            contentValues.putNull("tetherClientCertIssuerCN");
            contentValues.putNull("tetherClientCertIssuedCN");
            contentValues.putNull("tetherCaptivePortalCert");
            contentValues.putNull("tetherCaptivePortalAlias");
            contentValues.putNull("tetherCACert");
            contentValues.putNull("tetherCAlias");
            contentValues.putNull("tetherServerCert");
            contentValues.putNull("tetherServerAlias");
        }
        Log.d("FW-KnoxVpnHelper", "update db with usbTetheringAuthConfig result is " + this.mVpnStorageProvider.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues));
    }

    public boolean getUsbTetheringAuthConfig(String str, String str2, int i) {
        byte[] asByteArray;
        boolean z = false;
        try {
            ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, new String[]{str2});
            if (dataByFields != null && dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                if (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    if (i != 0 ? !(i != 1 || (asByteArray = contentValues.getAsByteArray(str2)) == null || asByteArray.length <= 0) : contentValues.getAsString(str2) != null) {
                        z = true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return z;
    }

    public Bundle getTetherAuthDetailsFromDatabase(String str) {
        Bundle bundle = new Bundle();
        ArrayList dataByFields = this.mVpnStorageProvider.getDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, null);
        if (dataByFields != null && dataByFields.size() > 0) {
            Iterator it = dataByFields.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues.containsKey("tetherLoginpage")) {
                    bundle.putString("key-tether-auth-login-page", contentValues.getAsString("tetherLoginpage"));
                }
                if (contentValues.containsKey("tetherResponsePage")) {
                    bundle.putString("key-tether-auth-response-page", contentValues.getAsString("tetherResponsePage"));
                }
                if (contentValues.containsKey("tetherClientCertIssuerCN")) {
                    bundle.putString("key-tether-client-certificate-issuer-cn", contentValues.getAsString("tetherClientCertIssuerCN"));
                }
                if (contentValues.containsKey("tetherClientCertIssuedCN")) {
                    bundle.putString("key-tether-client-certificate-issued-cn", contentValues.getAsString("tetherClientCertIssuedCN"));
                }
                if (contentValues.containsKey("tetherCaptivePortalAlias")) {
                    bundle.putString("key-tether-captive-portal-alias", contentValues.getAsString("tetherCaptivePortalAlias"));
                }
                if (contentValues.containsKey("tetherCaptivePortalCert")) {
                    bundle.putByteArray("key-tether-captive-portal-certificate", contentValues.getAsByteArray("tetherCaptivePortalCert"));
                }
                if (contentValues.containsKey("tetherCAlias")) {
                    bundle.putString("key-tether-ca-alias", contentValues.getAsString("tetherCAlias"));
                }
                if (contentValues.containsKey("tetherCACert")) {
                    bundle.putByteArray("key-tether-ca-certificate", contentValues.getAsByteArray("tetherCACert"));
                }
                if (contentValues.containsKey("tetherServerCert")) {
                    bundle.putByteArray("key-tether-user-certificate", contentValues.getAsByteArray("tetherServerCert"));
                }
                if (contentValues.containsKey("tetherServerAlias")) {
                    bundle.putString("key-tether-user-alias", contentValues.getAsString("tetherServerAlias"));
                }
            }
        }
        return bundle;
    }

    public String getProfileNameForExemptedUid(int i) {
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
                if (vpnProfileInfo.getActivateState() == 1 && vpnProfileInfo.getExemptPackageList().contains(Integer.valueOf(i))) {
                    return vpnProfileInfo.getProfileName();
                }
            }
            return null;
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", "getProfileNameForExemptedUid " + Log.getStackTraceString(e));
            return null;
        }
    }

    public HashSet getuserIdForExemptedUids() {
        Collection<VpnProfileInfo> profileEntries;
        HashSet hashSet = new HashSet();
        try {
            profileEntries = this.vpnConfig.getProfileEntries();
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the list of userId for exempted uids");
        }
        if (profileEntries == null) {
            return hashSet;
        }
        for (VpnProfileInfo vpnProfileInfo : profileEntries) {
            if (vpnProfileInfo.getActivateState() == 1) {
                Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
                while (it.hasNext()) {
                    hashSet.add(Integer.valueOf(UserHandle.getUserId(((Integer) it.next()).intValue())));
                }
            }
        }
        return hashSet;
    }

    public HashSet getuserIdForExemptedUidByProfile(String str) {
        VpnProfileInfo profileEntry;
        HashSet hashSet = new HashSet();
        try {
            profileEntry = this.vpnConfig.getProfileEntry(str);
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured at getuserIdForExemptedUidByProfile");
        }
        if (profileEntry == null) {
            return hashSet;
        }
        Iterator it = profileEntry.getExemptPackageList().iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(UserHandle.getUserId(((Integer) it.next()).intValue())));
        }
        return hashSet;
    }

    public boolean checKIfUidIsExempted(int i) {
        Collection<VpnProfileInfo> profileEntries;
        boolean z = false;
        try {
            profileEntries = this.vpnConfig.getProfileEntries();
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to check if the app Id is added to exempt list");
        }
        if (profileEntries == null) {
            return false;
        }
        for (VpnProfileInfo vpnProfileInfo : profileEntries) {
            if (vpnProfileInfo.getActivateState() == 1) {
                Iterator it = vpnProfileInfo.getExemptPackageList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (i == ((Integer) it.next()).intValue()) {
                        Log.d("FW-KnoxVpnHelper", "Check to see if Captive portal is being added to exempted list returns true");
                        z = true;
                        break;
                    }
                }
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public List profileListForClient(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
                String regularPackageName = getRegularPackageName(vpnProfileInfo.getVendorPkgName());
                if (regularPackageName != null && regularPackageName.equalsIgnoreCase(str)) {
                    arrayList.add(vpnProfileInfo.getProfileName());
                }
            }
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the profile list for the vpn client " + str);
        }
        return arrayList;
    }

    public int getAdminIdFromPackageName(String str) {
        VpnProfileConfig vpnProfileConfig = this.vpnConfig;
        if (vpnProfileConfig != null && vpnProfileConfig.getProfileEntries().size() > 0) {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
                if (str.equalsIgnoreCase(vpnProfileInfo.getVendorPkgName())) {
                    return vpnProfileInfo.getAdminId();
                }
            }
        }
        return -1;
    }

    public String getIpChainNameForProfile(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the profile list for the vpn client " + str);
            return null;
        }
        return profileEntry.getIpChainName();
    }

    public int getConnectionType(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                return profileEntry.getVpnConnectionType();
            }
            return 0;
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the profile list for the vpn client " + str);
            return 0;
        }
    }

    public String getProfileOwningTheUidFromCache(int i) {
        String str = null;
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
                Iterator it = vpnProfileInfo.getPackageList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (((VpnPackageInfo) it.next()).getUid() == i) {
                        str = vpnProfileInfo.getProfileName();
                        break;
                    }
                }
                if (str != null) {
                    break;
                }
            }
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the profileName for uid for on-demand use-case" + i);
        }
        return str;
    }

    public boolean isNativeVpnClient(String str) {
        boolean z = false;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                int vendorUid = profileEntry.getVendorUid();
                String vendorPkgName = profileEntry.getVendorPkgName();
                if (UserHandle.getAppId(vendorUid) == 1000 && vendorPkgName.contains("com.samsung.sVpn")) {
                    profileEntry.setVpnType(1);
                    z = true;
                } else {
                    profileEntry.setVpnType(0);
                }
            }
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to check if profile is configured by native vpn client " + str);
        }
        return z;
    }

    public boolean isKnoxVpnProfile(String str) {
        return this.vpnConfig.getProfileEntry(str) != null;
    }

    public boolean isUsbTetheringConfigured(String str) {
        if (str == null) {
            return false;
        }
        try {
            return new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS").getJSONObject("knox").optInt("allow_usb_over_vpn_tethering", 0) == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    public int getNonChainedVendoUid(int i) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
            if (vpnProfileInfo.getChainingEnabled() == 0 && UserHandle.getUserId(i) == UserHandle.getUserId(vpnProfileInfo.getVendorUid())) {
                return vpnProfileInfo.getVendorUid();
            }
        }
        return -1;
    }

    public String getDefaultNetworkInterface(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null || profileEntry.getActivateState() != 1) {
                return null;
            }
            return getActiveNetworkInterface();
        } catch (Exception unused) {
            return null;
        }
    }

    public List getuidListForProfile(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                Iterator it = profileEntry.getPackageList().iterator();
                while (it.hasNext()) {
                    int uid = ((VpnPackageInfo) it.next()).getUid();
                    if (uid > 0) {
                        arrayList.add(Integer.valueOf(uid));
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public List getuserIdListForProfile(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
                    if (vpnPackageInfo.getUid() == -2) {
                        arrayList.add(Integer.valueOf(vpnPackageInfo.getCid()));
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public int getpackageCountByUserId(String str, int i) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        int i2 = 0;
        if (profileEntry != null) {
            for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
                if (vpnPackageInfo.getCid() == i && vpnPackageInfo.getUid() > 0) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public int getChainingValueForProfile(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
            return profileEntry.getChainingEnabled();
        }
        return -1;
    }

    public void addOrRemoveSystemAppFromBatteryOptimization(String str, String str2, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (getPackageManager().checkSignatures("android", str2, UserHandle.getCallingUserId()) != 0) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        if (z) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null && ((profileEntry.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER || profileEntry.getPacurl() != Uri.EMPTY) && !((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str2))) {
                IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).addPowerSaveWhitelistApp(str2);
            }
        } else if (((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str2)) {
            IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(str2);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void addOrRemoveAppsFromBatteryOptimization(String str, String str2, boolean z) {
        if (str2 == null || !str2.equalsIgnoreCase("com.samsung.sVpn")) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (z) {
                    if (this.vpnConfig.getProfileEntry(str) != null && !((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str2)) {
                        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).addPowerSaveWhitelistApp(str2);
                    }
                } else if (((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations(str2)) {
                    IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp(str2);
                }
            } catch (Exception unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addOrRemoveSystemAppFromDataSaverWhitelist(String str, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                    if (profileEntry != null && (profileEntry.getProxyServer() != KnoxVpnConstants.DEFAULT_PROXY_SERVER || profileEntry.getPacurl() != Uri.EMPTY)) {
                        getNetworkManagementService().addOrRemoveSystemAppFromDataSaverWhitelist(z, i);
                    }
                } else {
                    getNetworkManagementService().addOrRemoveSystemAppFromDataSaverWhitelist(z, i);
                }
            } catch (Throwable unused) {
                Log.e("FW-KnoxVpnHelper", "No permission to update the data-saver list");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String setRandomIpChainName(String str) {
        boolean z;
        while (true) {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            while (sb.length() < 10) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".charAt((int) (random.nextFloat() * 36)));
            }
            String sb2 = sb.toString();
            Iterator it = this.vpnConfig.getProfileEntries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
                if (!vpnProfileInfo.getProfileName().equalsIgnoreCase(str) && vpnProfileInfo.getIpChainName().equalsIgnoreCase(sb2)) {
                    z = false;
                    break;
                }
            }
            if (z) {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                if (profileEntry != null) {
                    profileEntry.setIpChainName(sb2);
                    updateIpChainNameForProfile(str, sb2);
                    return sb2;
                }
                Log.e("FW-KnoxVpnHelper", "Error occured while trying to get the setRandomIpChainName for profile " + str);
            }
        }
    }

    public String getIpChainNameAfterUpgrade(String str) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 10) {
            sb.append("STUVWXYZ1234567890".charAt((int) (random.nextFloat() * 18)));
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003b, code lost:
    
        if (r8.hasNext() == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
    
        r3 = r8.next().getAddress();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:
    
        if (r3.isLinkLocalAddress() == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
    
        r0.add(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002b, code lost:
    
        r8 = java.net.NetworkInterface.getByName(r5).getInterfaceAddresses().iterator();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List getIpAddressForUsbTetheringInterface() {
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String[] listInterfaces = getNetworkManagementService().listInterfaces();
                int length = listInterfaces.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String str = listInterfaces[i];
                    Stream stream = Arrays.stream(KnoxVpnConstants.USB_TETHERING_IFACE);
                    Objects.requireNonNull(str);
                    if (stream.anyMatch(new KnoxVpnHelper$$ExternalSyntheticLambda0(str))) {
                        break;
                    }
                    i++;
                }
            } catch (Exception unused) {
                Log.e("FW-KnoxVpnHelper", "Exception at getIpAddressForUsbTetheringInterface");
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getInterfaceNameForUsbtethering() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = null;
        try {
            try {
                String[] listInterfaces = getNetworkManagementService().listInterfaces();
                int length = listInterfaces.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String str2 = listInterfaces[i];
                    Stream stream = Arrays.stream(KnoxVpnConstants.USB_TETHERING_IFACE);
                    Objects.requireNonNull(str2);
                    if (stream.anyMatch(new KnoxVpnHelper$$ExternalSyntheticLambda0(str2))) {
                        str = str2;
                        break;
                    }
                    i++;
                }
            } catch (Exception unused) {
                if (DBG) {
                    Log.e("FW-KnoxVpnHelper", "Exception at getInterfaceNameForUsbtethering");
                }
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List excludeLinkLocal(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InterfaceAddress interfaceAddress = (InterfaceAddress) it.next();
            if (!interfaceAddress.getAddress().isLinkLocalAddress()) {
                arrayList.add(interfaceAddress);
            }
        }
        return arrayList;
    }

    public List getNetworkPartWithMask(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            NetworkInterface byName = NetworkInterface.getByName(str);
            if (byName != null) {
                for (InterfaceAddress interfaceAddress : excludeLinkLocal(byName.getInterfaceAddresses())) {
                    arrayList.add(getNetworkPart(interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength()).getHostAddress() + "/" + ((int) interfaceAddress.getNetworkPrefixLength()));
                }
            }
        } catch (Exception e) {
            Log.e("FW-KnoxVpnHelper", " " + Log.getStackTraceString(e));
        }
        return arrayList;
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

    public void enableKnoxVpnFlagForTether(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                getNetworkManagementService().enableKnoxVpnFlagForTether(z);
            } catch (RemoteException unused) {
                Log.e("FW-KnoxVpnHelper", "Error at enableKnoxVpnFlagForTether " + z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerNetdTetherEventListener(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    getNetworkManagementService().registerNetdTetherEventListener();
                } else {
                    getNetworkManagementService().unregisterNetdTetherEventListener();
                }
            } catch (RemoteException unused) {
                Log.e("FW-KnoxVpnHelper", "Error at registerNetdTetherEventListener " + z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addUidsToVpnUidRange(String str, int i) {
        int nonChainedVendoUid;
        int nonChainedVendoUid2;
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        int vendorUid = profileEntry.getVendorUid();
        int personaId = profileEntry.getPersonaId();
        String regularPackageName = getRegularPackageName(profileEntry.getVendorPkgName());
        Iterator it = profileEntry.getExemptPackageList().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue > 0 && UserHandle.getUserId(intValue) == i) {
                hashSet.add(Integer.valueOf(intValue));
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i == 0) {
            for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
                hashSet.add(Integer.valueOf(num.intValue()));
            }
            if (profileEntry.getVendorPkgName().contains("com.samsung.sVpn")) {
                vendorUid = 1016;
            }
            if (profileEntry.getChainingEnabled() == 1 && (nonChainedVendoUid2 = getNonChainedVendoUid(profileEntry.getVendorUid())) > 0 && UserHandle.getUserId(nonChainedVendoUid2) == i) {
                hashSet.add(Integer.valueOf(nonChainedVendoUid2));
            }
            if (personaId == i) {
                hashSet.add(Integer.valueOf(vendorUid));
            }
            getVpnManagerService().updateUidRangesToUserVpnWithBlackList(str, personaId, i, new ArrayList(hashSet).stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray(), regularPackageName);
        } else if (profileEntry.getVendorPkgName().contains("com.samsung.sVpn")) {
            getVpnManagerService().updateUidRangesToUserVpn(str, personaId, true, i, regularPackageName);
        } else {
            if (profileEntry.getChainingEnabled() == 1 && (nonChainedVendoUid = getNonChainedVendoUid(profileEntry.getVendorUid())) > 0 && UserHandle.getUserId(nonChainedVendoUid) == i) {
                hashSet.add(Integer.valueOf(nonChainedVendoUid));
            }
            if (personaId == i) {
                hashSet.add(Integer.valueOf(vendorUid));
            }
            getVpnManagerService().updateUidRangesToUserVpnWithBlackList(str, personaId, i, new ArrayList(hashSet).stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray(), regularPackageName);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void updateUidsToVpnUidRange(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        int personaId = profileEntry.getPersonaId();
        String regularPackageName = getRegularPackageName(profileEntry.getVendorPkgName());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        getVpnManagerService().resetUidListInNetworkCapabilities(str, personaId, regularPackageName);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        HashSet hashSet = new HashSet();
        for (VpnPackageInfo vpnPackageInfo : profileEntry.getPackageList()) {
            int uid = vpnPackageInfo.getUid();
            if (uid == -2) {
                addUidsToVpnUidRange(str, vpnPackageInfo.getCid());
            } else if (uid > 0) {
                hashSet.add(Integer.valueOf(uid));
            }
        }
        if (!hashSet.isEmpty()) {
            int[] array = new ArrayList(hashSet).stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                getVpnManagerService().updateUidRangesToPerAppVpn(str, personaId, true, array, regularPackageName);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity2);
            }
        }
        if (profileEntry.getActivateState() == 1) {
            applyBlockingRulesToUidRange(str, true);
        } else {
            applyBlockingRulesToUidRange(str, false);
        }
    }

    public void applyBlockingRulesToUidRange(String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return;
            }
            getVpnManagerService().applyBlockingRulesToUidRange(str, profileEntry.getPersonaId(), z, getRegularPackageName(profileEntry.getVendorPkgName()));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        maskRawAddress(address, i);
        try {
            return InetAddress.getByAddress(address);
        } catch (UnknownHostException e) {
            throw new RuntimeException("getNetworkPart error - " + e.toString());
        }
    }

    public final void maskRawAddress(byte[] bArr, int i) {
        if (i < 0 || i > bArr.length * 8) {
            throw new RuntimeException("IP address with " + bArr.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT << (8 - (i % 8)));
        if (i2 < bArr.length) {
            bArr[i2] = (byte) (b & bArr[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= bArr.length) {
                return;
            } else {
                bArr[i2] = 0;
            }
        }
    }
}
