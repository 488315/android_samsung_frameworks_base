package com.android.server.enterprise.vpn.knoxvpn;

import android.app.AppGlobals;
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
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.connectivity.EnterpriseVpn$$ExternalSyntheticOutline0;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnHelper {
    public final KnoxVpnStorageProvider mVpnStorageProvider;
    public static final boolean DBG = Debug.semIsProductDev();
    public static KnoxVpnHelper mInstance = null;
    public static Context mContext = null;
    public static KnoxVpnCredentialHandler mKnoxVpnCredentialHandler = null;
    public INetworkManagementService mNetworkManagementService = null;
    public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();

    public KnoxVpnHelper(Context context) {
        this.mVpnStorageProvider = null;
        mContext = context;
        this.mVpnStorageProvider = KnoxVpnStorageProvider.getInstance(context);
        mKnoxVpnCredentialHandler = KnoxVpnCredentialHandler.getInstance();
    }

    public static boolean checkIfPlatformSigned(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.samsung.knox.vpn.tether.auth", 128L, i);
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
    
        android.util.Log.d("FW-KnoxVpnHelper", "checkIfUidIsBlackListed: mBlacklistedApplication value is " + r4[r3]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0013, code lost:
    
        if (r0 == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean checkIfUidIsBlackListed(int r7, int r8) {
        /*
            boolean r0 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.DBG
            java.lang.String r1 = "FW-KnoxVpnHelper"
            r2 = 0
            r3 = r2
        L6:
            java.lang.String[] r4 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnConstants.BLACK_LISTED_APPLICATION     // Catch: java.lang.Exception -> L2d
            r5 = 1
            if (r3 >= r5) goto L3e
            r6 = r4[r3]     // Catch: java.lang.Exception -> L2d
            int r6 = getUIDForPackage(r8, r6)     // Catch: java.lang.Exception -> L2d
            if (r6 != r7) goto L31
            if (r0 == 0) goto L2f
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L2d
            r7.<init>()     // Catch: java.lang.Exception -> L2d
            java.lang.String r8 = "checkIfUidIsBlackListed: mBlacklistedApplication value is "
            r7.append(r8)     // Catch: java.lang.Exception -> L2d
            r8 = r4[r3]     // Catch: java.lang.Exception -> L2d
            r7.append(r8)     // Catch: java.lang.Exception -> L2d
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> L2d
            android.util.Log.d(r1, r7)     // Catch: java.lang.Exception -> L2d
            goto L2f
        L2d:
            r7 = move-exception
            goto L34
        L2f:
            r2 = r5
            goto L3e
        L31:
            int r3 = r3 + 1
            goto L6
        L34:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r3 = "Exception at checkIfUidIsBlackListed API "
            r8.<init>(r3)
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r7, r8, r1)
        L3e:
            if (r0 == 0) goto L46
            java.lang.String r7 = "checkIfUidIsBlackListed: isUidBackListed value is "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r7, r1, r2)
        L46:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.checkIfUidIsBlackListed(int, int):boolean");
    }

    public static String getActiveNetworkInterface() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str = null;
        try {
            try {
                str = getVpnManagerService().getActiveDefaultInterface();
                Log.d("FW-KnoxVpnHelper", "The active interface name is " + str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable unused) {
            Log.e("FW-KnoxVpnHelper", "Exception occured while trying to get the active interface details");
        }
        return str;
    }

    public static int getContainerIdFromPackageName(String str) {
        if (str != null) {
            try {
                return Integer.parseInt(str.split("_")[0]);
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    public static String[] getInstalledPackages(int i) {
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
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
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception in getInstalledPackages : "), "FW-KnoxVpnHelper");
            return null;
        }
    }

    public static synchronized KnoxVpnHelper getInstance(Context context) {
        KnoxVpnHelper knoxVpnHelper;
        synchronized (KnoxVpnHelper.class) {
            try {
                if (mInstance == null) {
                    mInstance = new KnoxVpnHelper(context);
                }
                knoxVpnHelper = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxVpnHelper;
    }

    public static String getIpChainNameAfterUpgrade() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 10) {
            sb.append("STUVWXYZ1234567890".charAt((int) (random.nextFloat() * 18)));
        }
        return sb.toString();
    }

    public static KnoxVpnCredentialHandler getKnoxVpnCredentialHandler() {
        if (mKnoxVpnCredentialHandler == null) {
            mKnoxVpnCredentialHandler = KnoxVpnCredentialHandler.getInstance();
        }
        return mKnoxVpnCredentialHandler;
    }

    public static HashSet getListOfActiveUsers() {
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

    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new RuntimeException("IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new RuntimeException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }

    public static List getNetworkPartWithMask(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            NetworkInterface byName = NetworkInterface.getByName(str);
            if (byName != null) {
                List<InterfaceAddress> interfaceAddresses = byName.getInterfaceAddresses();
                ArrayList arrayList2 = new ArrayList(interfaceAddresses.size());
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    if (!interfaceAddress.getAddress().isLinkLocalAddress()) {
                        arrayList2.add(interfaceAddress);
                    }
                }
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    InterfaceAddress interfaceAddress2 = (InterfaceAddress) it.next();
                    arrayList.add(getNetworkPart(interfaceAddress2.getAddress(), interfaceAddress2.getNetworkPrefixLength()).getHostAddress() + "/" + ((int) interfaceAddress2.getNetworkPrefixLength()));
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder(" "), "FW-KnoxVpnHelper");
        }
        return arrayList;
    }

    public static String getPackageNameForUid(int i) {
        try {
            return AppGlobals.getPackageManager().getNameForUid(i);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception in getPackageNameForUid : "), "FW-KnoxVpnHelper");
            return null;
        }
    }

    public static String getPersonifiedName(int i, String str) {
        try {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "vpn getPersonifiedName : container id is regular device");
            }
            return i + "_" + str;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception getting getPersonifiedName : Error :  "), "FW-KnoxVpnHelper");
            return null;
        }
    }

    public static String getProfileNameFromJsonString(String str) {
        try {
            return new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS").getJSONObject("profile_attribute").getString("profileName");
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("get profilename from json : Error parsing JSON \n"), "FW-KnoxVpnHelper");
            return null;
        }
    }

    public static String getRegularPackageName(String str) {
        if (str != null) {
            return str.substring(str.indexOf("_") + 1);
        }
        return null;
    }

    public static int getUIDForPackage(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
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

    public static int getUidForPackageName(String str) {
        boolean z = DBG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("getUidForPackageName: transformedPackageName value is ", str, "FW-KnoxVpnHelper");
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
            if (z) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getUidForPackageName API "), "FW-KnoxVpnHelper");
            }
        }
        return i;
    }

    public static IVpnManager getVpnManagerService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
    }

    public static boolean isPackageForAddAllPackages(VpnPackageInfo vpnPackageInfo) {
        return vpnPackageInfo.getUid() == -2 && vpnPackageInfo.getPackageName().equalsIgnoreCase(getPersonifiedName(getContainerIdFromPackageName(vpnPackageInfo.getPackageName()), "ADD_ALL_PACKAGES"));
    }

    public static boolean isPackageInstalled(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (packageManager != null) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0L, i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (applicationInfo == null) {
                    Log.d("FW-KnoxVpnHelper", "package not present : " + str);
                    return false;
                }
                if (!DBG) {
                    return true;
                }
                Log.d("FW-KnoxVpnHelper", "package present. application uid for user 100 = " + applicationInfo.uid);
                return true;
            } catch (Exception unused) {
                Log.d("FW-KnoxVpnHelper", "Exception in isPackageInstalled");
            }
        }
        return false;
    }

    public static boolean isUsbTetheringConfigured(String str) {
        if (str == null) {
            return false;
        }
        try {
            return new JSONObject(str).getJSONObject("KNOX_VPN_PARAMETERS").getJSONObject("knox").optInt("allow_usb_over_vpn_tethering", 0) == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String retrieveProfileCredentials(String str, String str2) {
        try {
            String str3 = str + "_" + str2;
            getKnoxVpnCredentialHandler().getClass();
            return KnoxVpnCredentialHandler.retrieveCredentialsFromKeystore(str3);
        } catch (Exception e) {
            if (DBG) {
                EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured while Retrieve the profile credentials "), "FW-KnoxVpnHelper");
            }
            return null;
        }
    }

    public static boolean saveProfileCredentials(String str, String str2) {
        try {
            getKnoxVpnCredentialHandler().getClass();
            return KnoxVpnCredentialHandler.storeCredentialsInKeystore(str, str2);
        } catch (Exception e) {
            if (DBG) {
                EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception occured while saving the profile credentials "), "FW-KnoxVpnHelper");
            }
            return false;
        }
    }

    public static void sendProxyConfigBroadcast(int i, HashMap hashMap, HashSet hashSet) {
        Iterator it;
        Iterator it2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it3 = hashMap.keySet().iterator();
                while (it3.hasNext()) {
                    Integer num = (Integer) it3.next();
                    Intent intent = new Intent("android.intent.action.PROXY_CHANGE");
                    intent.addFlags(603979776);
                    if (num.intValue() == i) {
                        Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to vpn client for per-app use-case " + num);
                    } else {
                        boolean booleanValue = ((Boolean) hashMap.get(num)).booleanValue();
                        boolean z = DBG;
                        if (booleanValue) {
                            String packageNameForUid = getPackageNameForUid(i);
                            String[] installedPackages = getInstalledPackages(num.intValue());
                            if (installedPackages != null) {
                                int length = installedPackages.length;
                                int i2 = 0;
                                while (i2 < length) {
                                    String str = installedPackages[i2];
                                    if (packageNameForUid.equalsIgnoreCase(str)) {
                                        Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to vpn client for addAll use-case " + packageNameForUid);
                                        it2 = it3;
                                    } else {
                                        Iterator it4 = hashSet.iterator();
                                        boolean z2 = false;
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                it2 = it3;
                                                break;
                                            }
                                            int intValue = ((Integer) it4.next()).intValue();
                                            it2 = it3;
                                            if (UserHandle.getUserId(intValue) == num.intValue()) {
                                                String[] packagesForUid = AppGlobals.getPackageManager().getPackagesForUid(intValue);
                                                if (packagesForUid != null) {
                                                    int length2 = packagesForUid.length;
                                                    int i3 = 0;
                                                    while (true) {
                                                        if (i3 >= length2) {
                                                            break;
                                                        }
                                                        int i4 = length2;
                                                        String str2 = packagesForUid[i3];
                                                        if (str2.equalsIgnoreCase(str)) {
                                                            Log.d("FW-KnoxVpnHelper", "skipping sending the broadcast which contains proxy configuration to exempted uid for addAll use-case " + str2);
                                                            z2 = true;
                                                            break;
                                                        }
                                                        i3++;
                                                        length2 = i4;
                                                    }
                                                } else {
                                                    continue;
                                                    it3 = it2;
                                                }
                                            }
                                            if (z2) {
                                                break;
                                            } else {
                                                it3 = it2;
                                            }
                                        }
                                        if (!z2) {
                                            if (z) {
                                                Log.d("FW-KnoxVpnHelper", "sending proxy broadcast to the app added to knox vpn profile for addAll use-case " + str);
                                            }
                                            intent.setPackage(str);
                                            mContext.sendBroadcastAsUser(intent, new UserHandle(num.intValue()));
                                        }
                                    }
                                    i2++;
                                    it3 = it2;
                                }
                            }
                            it = it3;
                        } else {
                            it = it3;
                            String[] packagesForUid2 = AppGlobals.getPackageManager().getPackagesForUid(num.intValue());
                            if (packagesForUid2 != null) {
                                for (String str3 : packagesForUid2) {
                                    if (z) {
                                        Log.d("FW-KnoxVpnHelper", "sending proxy broadcast to the app added to knox vpn profile for per use-case " + str3);
                                    }
                                    intent.setPackage(str3);
                                    mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(num.intValue())));
                                }
                            }
                        }
                        it3 = it;
                    }
                }
            } catch (Exception e) {
                Log.e("FW-KnoxVpnHelper", "Exception occured while sending proxy broadcast to knox vpn apps " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void setProxyFlagForEmail(int i, boolean z) {
        if (z) {
            SystemProperties.set("net.vpn.proxy.email." + i, "1");
        } else {
            SystemProperties.set("net.vpn.proxy.email." + i, "0");
        }
    }

    public static int startUid(int i) {
        int i2 = (i * 100000) + 1;
        try {
            if (DBG) {
                Log.d("FW-KnoxVpnHelper", "startUid - The Start uid for the persona for which dns is going to be applied is" + i2);
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("startUidOfPersona Error"), "FW-KnoxVpnHelper");
        }
        return i2;
    }

    public static int stopUid(int i) {
        int i2 = i * 100000;
        int i3 = i2 + 1;
        int i4 = -1;
        try {
            boolean z = DBG;
            if (z) {
                Log.d("FW-KnoxVpnHelper", "stopUid - The Start uid for the persona for which dns is going to be applied is" + i3);
            }
            i4 = i2 + 99999;
            if (z) {
                Log.d("FW-KnoxVpnHelper", "stopUid - The last uid for the persona for which dns is going to be applied is" + i4);
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("stopUidOfPersona Error"), "FW-KnoxVpnHelper");
        }
        return i4;
    }

    public static HashMap updateProxyList(int i, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.valueOf(i), Boolean.valueOf(z));
        return hashMap;
    }

    public final boolean addExemptedListToDatabase(int i, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("profileName", str);
            contentValues.put("packageName", str2);
            contentValues.put("packageUid", Integer.valueOf(i));
            this.mVpnStorageProvider.getClass();
            return KnoxVpnStorageProvider.mEDM.putDataByFields("vpnExemptInfo", null, null, contentValues);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("addExemptedListToDatabase : Exception:"), "FW-KnoxVpnHelper");
            return false;
        }
    }

    public final void addOrRemoveAppsFromBatteryOptimization(String str, String str2, boolean z) {
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

    public final void addOrRemoveSystemAppFromBatteryOptimization(String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
        } catch (Exception unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (AppGlobals.getPackageManager().checkSignatures("android", "com.knox.vpn.proxyhandler", UserHandle.getCallingUserId()) != 0) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        if (z) {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                if (profileEntry.mProxyServer == null) {
                    if (profileEntry.mPacurl != Uri.EMPTY) {
                    }
                }
                if (!((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations("com.knox.vpn.proxyhandler")) {
                    IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).addPowerSaveWhitelistApp("com.knox.vpn.proxyhandler");
                }
            }
        } else if (((PowerManager) mContext.getSystemService("power")).isIgnoringBatteryOptimizations("com.knox.vpn.proxyhandler")) {
            IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")).removePowerSaveWhitelistApp("com.knox.vpn.proxyhandler");
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void addOrRemoveSystemAppFromDataSaverWhitelist(int i, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (z) {
                    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                    if (profileEntry != null && (profileEntry.mProxyServer != null || profileEntry.mPacurl != Uri.EMPTY)) {
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addVpnProfileToDatabase(com.samsung.android.knox.net.vpn.KnoxVpnContext r20, java.lang.String r21, int r22) {
        /*
            Method dump skipped, instructions count: 427
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.addVpnProfileToDatabase(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00f0 A[Catch: Exception -> 0x0054, TryCatch #2 {Exception -> 0x0054, blocks: (B:3:0x001a, B:5:0x0039, B:7:0x0044, B:8:0x0059, B:10:0x0062, B:11:0x0071, B:13:0x007a, B:14:0x0089, B:25:0x00e4, B:27:0x00f0, B:28:0x00ff, B:29:0x011d), top: B:2:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addVpnProfileToMap(com.samsung.android.knox.net.vpn.KnoxVpnContext r21, java.lang.String r22, int r23) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.addVpnProfileToMap(com.samsung.android.knox.net.vpn.KnoxVpnContext, java.lang.String, int):boolean");
    }

    public final void applyBlockingRulesToUidRange(String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return;
            }
            getVpnManagerService().applyBlockingRulesToUidRange(str, profileEntry.personaId, z, getRegularPackageName(profileEntry.mVendorPkgName));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean chainingForAddAll(int i, String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("chainingForAddAll: profileName value is ", str, "FW-KnoxVpnHelper");
        boolean z = false;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageCid"}, new String[]{Integer.toString(i)}, new String[]{"packageName"});
            if (dataByFields.size() > 0 && checkIfProfileHasChainingFeature(str) == 1) {
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
                        if (asString.equalsIgnoreCase(profileEntry.mVendorPkgName)) {
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
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at enableChainingForAddAll API "), "FW-KnoxVpnHelper");
        }
        return z;
    }

    public final boolean checKIfUidIsExempted(int i) {
        Collection<VpnProfileInfo> values;
        boolean z = false;
        try {
            values = this.vpnConfig.vpnProfileInfoMap.values();
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to check if the app Id is added to exempt list");
        }
        if (values == null) {
            return false;
        }
        for (VpnProfileInfo vpnProfileInfo : values) {
            if (vpnProfileInfo.activateState == 1) {
                Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
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

    public final int checkIfChainingEnabledForVendor(int i, boolean z) {
        int i2;
        int i3 = -2;
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.mVendorUid == i) {
                    if (!z) {
                        i2 = vpnProfileInfo.chainingEnabled;
                        if (i2 == -1) {
                            i3 = i2;
                            break;
                        }
                    } else {
                        i2 = vpnProfileInfo.chainingEnabled;
                        if (i2 != -1) {
                            i3 = i2;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (DBG) {
                VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfChainingEnabledForVendor "), "FW-KnoxVpnHelper");
            }
        }
        if (DBG) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i3, i, "is chaining feature enabled  ", "for vendor ", "FW-KnoxVpnHelper");
        }
        return i3;
    }

    public final int checkIfProfileHasChainingFeature(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return -1;
            }
            if (profileEntry.chainingEnabled == 1) {
                return 1;
            }
            return profileEntry.chainingEnabled == 0 ? 0 : -1;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfProfileHasChainingFeature"), "FW-KnoxVpnHelper");
            return -1;
        }
    }

    public final boolean checkIfProfileListEmpty() {
        boolean z = true;
        try {
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", null, null, null).size() > 0) {
                z = false;
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfProfileListEmpty API "), "FW-KnoxVpnHelper");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("checkIfProfileListEmpty:profileListEmpty value is ", "FW-KnoxVpnHelper", z);
        return z;
    }

    public final int checkIfUidHasInternetPermission(int i) {
        int i2 = 2;
        try {
            int checkUidPermission = AppGlobals.getPackageManager().checkUidPermission("android.permission.INTERNET", i);
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"packageUid"}).size() > 0) {
                if (checkUidPermission == 0) {
                    i2 = 0;
                }
            } else if (checkUidPermission != 0) {
                i2 = 1;
            }
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while checking if uid has been upgraded with internet permission");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, i2, "checkIfUidHasInternetPermission for uid ", " return value is ", "FW-KnoxVpnHelper");
        return i2;
    }

    public final boolean checkIfVpnProfileTableIsEmpty(String str) {
        boolean z = true;
        try {
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"vendorName"}, new String[]{str}, null).size() > 0) {
                z = false;
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at checkIfKnoxVpnDatabaseIsEmpty API: "), "FW-KnoxVpnHelper");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("checkIfKnoxVpnDatabaseIsEmpty: tableEmpty value is ", "FW-KnoxVpnHelper", z);
        return z;
    }

    public final void enableKnoxVpnFlagForTether(boolean z) {
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

    public final int getAdminIdForUserVpn(int i) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            Iterator it = vpnProfileInfo.mPackageMap.values().iterator();
            while (it.hasNext()) {
                if (((VpnPackageInfo) it.next()).getPackageName().equalsIgnoreCase(getPersonifiedName(i, "ADD_ALL_PACKAGES"))) {
                    return vpnProfileInfo.admin_id;
                }
            }
        }
        if (!DBG) {
            return -1;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "getAdminIdForUserVpn: admin id value is -1for user ", "FW-KnoxVpnHelper");
        return -1;
    }

    public final int getAdminIdFromPackageName(String str) {
        VpnProfileConfig vpnProfileConfig = this.vpnConfig;
        if (vpnProfileConfig != null && vpnProfileConfig.vpnProfileInfoMap.values().size() > 0) {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (str.equalsIgnoreCase(vpnProfileInfo.mVendorPkgName)) {
                    return vpnProfileInfo.admin_id;
                }
            }
        }
        return -1;
    }

    public final int getChainingValueForProfile(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry != null) {
            return profileEntry.chainingEnabled;
        }
        return -1;
    }

    public final int getConnectionType(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                return profileEntry.vpnConnectionType;
            }
            return 0;
        } catch (Exception unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Error occured while trying to fetch the profile list for the vpn client ", str, "FW-KnoxVpnHelper");
            return 0;
        }
    }

    public final String getDefaultNetworkInterface(String str) {
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null || profileEntry.activateState != 1) {
                return null;
            }
            return getActiveNetworkInterface();
        } catch (Exception unused) {
            return null;
        }
    }

    public final void getExemptedListFromDatabase(String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        if (profileEntry == null) {
            return;
        }
        HashSet hashSet = profileEntry.mExemptPackageList;
        try {
            this.mVpnStorageProvider.getClass();
            try {
                Iterator it = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid"}).iterator();
                while (it.hasNext()) {
                    Integer asInteger = ((ContentValues) it.next()).getAsInteger("packageUid");
                    asInteger.getClass();
                    hashSet.add(asInteger);
                }
            } catch (Exception e) {
                Log.e("FW-KnoxVpnHelper", "getExemptedListFromDatabase " + Log.getStackTraceString(e));
            }
        } catch (Exception e2) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e2, new StringBuilder("getExemptedListFromDatabase : Exception:"), "FW-KnoxVpnHelper");
        }
    }

    public final String getInterfaceNameForUsbtethering() {
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003b, code lost:
    
        if (r8.hasNext() == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003d, code lost:
    
        r3 = r8.next().getAddress();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:
    
        if (r3.isLinkLocalAddress() == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
    
        r0.add(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002b, code lost:
    
        r8 = java.net.NetworkInterface.getByName(r5).getInterfaceAddresses().iterator();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getIpAddressForUsbTetheringInterface() {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            long r1 = android.os.Binder.clearCallingIdentity()
            android.os.INetworkManagementService r8 = r8.getNetworkManagementService()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.lang.String[] r8 = r8.listInterfaces()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            int r3 = r8.length     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            r4 = 0
        L13:
            if (r4 >= r3) goto L57
            r5 = r8[r4]     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.lang.String[] r6 = com.android.server.enterprise.vpn.knoxvpn.KnoxVpnConstants.USB_TETHERING_IFACE     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.util.stream.Stream r6 = java.util.Arrays.stream(r6)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.util.Objects.requireNonNull(r5)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper$$ExternalSyntheticLambda0 r7 = new com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            r7.<init>(r5)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            boolean r6 = r6.anyMatch(r7)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            if (r6 == 0) goto L54
            java.net.NetworkInterface r8 = java.net.NetworkInterface.getByName(r5)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.util.List r8 = r8.getInterfaceAddresses()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
        L37:
            boolean r3 = r8.hasNext()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            if (r3 == 0) goto L57
            java.lang.Object r3 = r8.next()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.net.InterfaceAddress r3 = (java.net.InterfaceAddress) r3     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            java.net.InetAddress r3 = r3.getAddress()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            boolean r4 = r3.isLinkLocalAddress()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            if (r4 == 0) goto L4e
            goto L37
        L4e:
            r0.add(r3)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L5b
            goto L37
        L52:
            r8 = move-exception
            goto L64
        L54:
            int r4 = r4 + 1
            goto L13
        L57:
            android.os.Binder.restoreCallingIdentity(r1)
            goto L63
        L5b:
            java.lang.String r8 = "FW-KnoxVpnHelper"
            java.lang.String r3 = "Exception at getIpAddressForUsbTetheringInterface"
            android.util.Log.e(r8, r3)     // Catch: java.lang.Throwable -> L52
            goto L57
        L63:
            return r0
        L64:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnHelper.getIpAddressForUsbTetheringInterface():java.util.List");
    }

    public final HashMap getListOfUid(String str) {
        HashMap hashMap = null;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry == null) {
                return null;
            }
            HashMap hashMap2 = new HashMap();
            try {
                for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
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

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service = ServiceManager.getService("network_management");
        if (service != null && this.mNetworkManagementService == null) {
            this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkManagementService;
    }

    public final int getNonChainedVendoUid(int i) {
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            if (vpnProfileInfo.chainingEnabled == 0 && UserHandle.getUserId(i) == UserHandle.getUserId(vpnProfileInfo.mVendorUid)) {
                return vpnProfileInfo.mVendorUid;
            }
        }
        return -1;
    }

    public final String getProfileNameForExemptedUid(int i) {
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                if (vpnProfileInfo.activateState == 1 && vpnProfileInfo.mExemptPackageList.contains(Integer.valueOf(i))) {
                    return vpnProfileInfo.mProfileName;
                }
            }
            return null;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("getProfileNameForExemptedUid "), "FW-KnoxVpnHelper");
            return null;
        }
    }

    public final String getProfileNameForPermissionUpdatedApp(int i) {
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields.size() <= 0) {
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

    public final String getProfileOwningThePackage(String str) {
        String str2 = null;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{str}, new String[]{"profileName"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str2 = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getProfileOwningThePackage "), "FW-KnoxVpnHelper");
        }
        return str2;
    }

    public final String getProfileOwningTheUid(int i) {
        String str = null;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getProfileOwningTheUid "), "FW-KnoxVpnHelper");
        }
        return str;
    }

    public final String getProfileOwningTheUidFromCache(int i) {
        String str = null;
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                Iterator it = vpnProfileInfo.mPackageMap.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (((VpnPackageInfo) it.next()).getUid() == i) {
                        str = vpnProfileInfo.mProfileName;
                        break;
                    }
                }
                if (str != null) {
                    break;
                }
            }
        } catch (Exception unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Error occured while trying to fetch the profileName for uid for on-demand use-case", "FW-KnoxVpnHelper");
        }
        return str;
    }

    public final String getProfileOwningTheUidWithNoInternetPermission(int i) {
        String str = null;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, new String[]{Integer.toString(i)}, new String[]{"profileName"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    str = ((ContentValues) it.next()).getAsString("profileName");
                }
            }
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception at getProfileOwningTheUidWithNoInternetPermission "), "FW-KnoxVpnHelper");
        }
        return str;
    }

    public final Bundle getTetherAuthDetailsFromDatabase(String str) {
        Bundle bundle = new Bundle();
        this.mVpnStorageProvider.getClass();
        ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, null);
        if (dataByFields.size() > 0) {
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

    public final HashSet getUninsalledAppsFromExemptedList(int i, String str) {
        String asString;
        HashSet hashSet = new HashSet();
        try {
            this.mVpnStorageProvider.getClass();
            try {
                Iterator it = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid", "packageName"}).iterator();
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
        } catch (Exception e2) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e2, new StringBuilder("getExemptedListFromDatabase : Exception:"), "FW-KnoxVpnHelper");
        }
        return hashSet;
    }

    public final HashSet getUninsalledAppsFromExemptedList(String str) {
        String asString;
        HashSet hashSet = new HashSet();
        try {
            this.mVpnStorageProvider.getClass();
            Iterator it = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnExemptInfo", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid", "packageName"}).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues.getAsInteger("packageUid").intValue() == -1 && (asString = contentValues.getAsString("packageName")) != null) {
                    hashSet.add(asString);
                }
            }
        } catch (Exception unused) {
        }
        return hashSet;
    }

    public final boolean getUsbTetheringAuthConfig(int i, String str, String str2) {
        byte[] asByteArray;
        boolean z = false;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, new String[]{str2});
            if (dataByFields.size() > 0) {
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

    public final String[] getUserPackageListForProfile(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String[] strArr = null;
        try {
            try {
                VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                IPackageManager packageManager = AppGlobals.getPackageManager();
                ArrayList arrayList = new ArrayList();
                for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0L, i).getList()) {
                    if (profileEntry != null) {
                        Iterator it = profileEntry.mExemptPackageList.iterator();
                        while (it.hasNext()) {
                            if (((Integer) it.next()).intValue() == applicationInfo.uid) {
                                break;
                            }
                        }
                    }
                    arrayList.add(applicationInfo.packageName);
                }
                strArr = new String[arrayList.size()];
                Iterator it2 = arrayList.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    int i3 = i2 + 1;
                    strArr[i2] = (String) it2.next();
                    i2 = i3;
                }
            } catch (Exception e) {
                Log.d("FW-KnoxVpnHelper", "Exception in getInstalledPackages : " + Log.getStackTraceString(e));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return strArr;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int getpackageCountByUserId(int i, String str) {
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
        int i2 = 0;
        if (profileEntry != null) {
            for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                if (vpnPackageInfo.getCid() == i && vpnPackageInfo.getUid() > 0) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public final List getuidListForProfile(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                Iterator it = profileEntry.mPackageMap.values().iterator();
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

    public final HashSet getuserIdForExemptedUidByProfile(String str) {
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
        Iterator it = profileEntry.mExemptPackageList.iterator();
        while (it.hasNext()) {
            hashSet.add(Integer.valueOf(UserHandle.getUserId(((Integer) it.next()).intValue())));
        }
        return hashSet;
    }

    public final HashSet getuserIdForExemptedUids() {
        Collection<VpnProfileInfo> values;
        HashSet hashSet = new HashSet();
        try {
            values = this.vpnConfig.vpnProfileInfoMap.values();
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the list of userId for exempted uids");
        }
        if (values == null) {
            return hashSet;
        }
        for (VpnProfileInfo vpnProfileInfo : values) {
            if (vpnProfileInfo.activateState == 1) {
                Iterator it = vpnProfileInfo.mExemptPackageList.iterator();
                while (it.hasNext()) {
                    hashSet.add(Integer.valueOf(UserHandle.getUserId(((Integer) it.next()).intValue())));
                }
            }
        }
        return hashSet;
    }

    public final List getuserIdListForProfile(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                for (VpnPackageInfo vpnPackageInfo : profileEntry.mPackageMap.values()) {
                    if (vpnPackageInfo.getUid() == -2) {
                        arrayList.add(Integer.valueOf(vpnPackageInfo.getCid()));
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public final boolean isNativeVpnClient(String str) {
        boolean z = false;
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
            if (profileEntry != null) {
                int i = profileEntry.mVendorUid;
                String str2 = profileEntry.mVendorPkgName;
                if (UserHandle.getAppId(i) == 1000 && str2.contains("com.samsung.sVpn")) {
                    profileEntry.mVpnClientType = 1;
                    z = true;
                } else {
                    profileEntry.mVpnClientType = 0;
                }
            }
        } catch (Exception unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Error occured while trying to check if profile is configured by native vpn client ", str, "FW-KnoxVpnHelper");
        }
        return z;
    }

    public final boolean isUsingKnoxPackageExists(int i) {
        boolean z = false;
        try {
            this.mVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageCid"}, new String[]{Integer.toString(i)}, new String[]{"packageName"});
            if (dataByFields.size() <= 0) {
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
                    VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("isUsingKnoxPackageExists \n"), "FW-KnoxVpnHelper");
                    return z;
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final boolean isWideVpnExists(int i) {
        try {
            String personifiedName = getPersonifiedName(i, "ADD_ALL_PACKAGES");
            this.mVpnStorageProvider.getClass();
            return KnoxVpnStorageProvider.mEDM.getDataByFields("VpnPackageInfo", new String[]{"packageName"}, new String[]{personifiedName}, new String[]{"profileName"}).size() > 0;
        } catch (Exception e) {
            VpnManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("isPackageExists \n"), "FW-KnoxVpnHelper");
            return false;
        }
    }

    public final List profileListForClient() {
        ArrayList arrayList = new ArrayList();
        try {
            for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
                String regularPackageName = getRegularPackageName(vpnProfileInfo.mVendorPkgName);
                if (regularPackageName != null && regularPackageName.equalsIgnoreCase("com.samsung.sVpn")) {
                    arrayList.add(vpnProfileInfo.mProfileName);
                }
            }
        } catch (Exception unused) {
            Log.e("FW-KnoxVpnHelper", "Error occured while trying to fetch the profile list for the vpn client com.samsung.sVpn");
        }
        return arrayList;
    }

    public final void registerNetdTetherEventListener(boolean z) {
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
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void removeExemptedListToDatabase(String str) {
        try {
            this.mVpnStorageProvider.getClass();
            KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str});
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("removeExemptedListToDatabase : Exception:"), "FW-KnoxVpnHelper");
        }
    }

    public final boolean removeExemptedListToDatabase(int i) {
        try {
            KnoxVpnStorageProvider knoxVpnStorageProvider = this.mVpnStorageProvider;
            String[] strArr = {Integer.toString(i)};
            knoxVpnStorageProvider.getClass();
            return KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnExemptInfo", new String[]{"packageUid"}, strArr);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("removeExemptedListToDatabase : Exception:"), "FW-KnoxVpnHelper");
            return false;
        }
    }

    public final void removePackagesFromPermissionDb(int i, String str) {
        KnoxVpnStorageProvider knoxVpnStorageProvider = this.mVpnStorageProvider;
        ArrayList arrayList = new ArrayList();
        try {
            knoxVpnStorageProvider.getClass();
            ArrayList dataByFields = KnoxVpnStorageProvider.mEDM.getDataByFields("vpnNoInternetPermission", new String[]{"profileName"}, new String[]{str}, new String[]{"packageUid"});
            if (dataByFields.size() > 0) {
                Iterator it = dataByFields.iterator();
                while (it.hasNext()) {
                    Integer asInteger = ((ContentValues) it.next()).getAsInteger("packageUid");
                    int intValue = asInteger.intValue();
                    if (intValue != -1 && UserHandle.getUserId(intValue) == i) {
                        arrayList.add(asInteger);
                    }
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                String[] strArr = {Integer.toString(((Integer) it2.next()).intValue())};
                KnoxVpnStorageProvider.mEDM.deleteDataByFields("vpnNoInternetPermission", new String[]{"packageUid"}, strArr);
            }
        } catch (Exception unused) {
        }
    }

    public final String setRandomIpChainName(String str) {
        while (true) {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            while (sb.length() < 10) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".charAt((int) (random.nextFloat() * 36)));
            }
            String sb2 = sb.toString();
            Iterator it = this.vpnConfig.vpnProfileInfoMap.values().iterator();
            while (true) {
                if (it.hasNext()) {
                    VpnProfileInfo vpnProfileInfo = (VpnProfileInfo) it.next();
                    if (!vpnProfileInfo.mProfileName.equalsIgnoreCase(str) && vpnProfileInfo.mIpChainName.equalsIgnoreCase(sb2)) {
                        break;
                    }
                } else {
                    VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str);
                    if (profileEntry != null) {
                        profileEntry.mIpChainName = sb2;
                        updateIpChainNameForProfile(str, sb2);
                        return sb2;
                    }
                    StorageManagerService$$ExternalSyntheticOutline0.m("Error occured while trying to get the setRandomIpChainName for profile ", str, "FW-KnoxVpnHelper");
                }
            }
        }
    }

    public final boolean updateExemptedListToDatabase(int i, String str) {
        try {
            this.mVpnStorageProvider.getClass();
            if (KnoxVpnStorageProvider.mEDM.getDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str}, new String[]{"profileName", "packageUid"}).size() <= 0) {
                return false;
            }
            Log.d("FW-KnoxVpnHelper", "updating exempted application details after install-uninstall " + str + i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageUid", Integer.valueOf(i));
            return KnoxVpnStorageProvider.mEDM.putDataByFields("vpnExemptInfo", new String[]{"packageName"}, new String[]{str}, contentValues);
        } catch (Exception e) {
            EnterpriseVpn$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateExemptedListToDatabase : Exception:"), "FW-KnoxVpnHelper");
            return false;
        }
    }

    public final void updateIpChainNameForProfile(String str, String str2) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("vpnIpChainName", str2);
        this.mVpnStorageProvider.getClass();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("updateIpChainNameForProfile ", "FW-KnoxVpnHelper", KnoxVpnStorageProvider.mEDM.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, m));
    }

    public final void updateUidsToVpnUidRange(String str) {
        long clearCallingIdentity;
        Iterator it;
        int nonChainedVendoUid;
        int nonChainedVendoUid2;
        String str2 = str;
        VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(str2);
        if (profileEntry == null) {
            return;
        }
        int i = profileEntry.personaId;
        String regularPackageName = getRegularPackageName(profileEntry.mVendorPkgName);
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        getVpnManagerService().resetUidListInNetworkCapabilities(str2, i, regularPackageName);
        Binder.restoreCallingIdentity(clearCallingIdentity2);
        HashSet hashSet = new HashSet();
        for (Iterator it2 = profileEntry.mPackageMap.values().iterator(); it2.hasNext(); it2 = it) {
            VpnPackageInfo vpnPackageInfo = (VpnPackageInfo) it2.next();
            int uid = vpnPackageInfo.getUid();
            if (uid == -2) {
                int cid = vpnPackageInfo.getCid();
                VpnProfileInfo profileEntry2 = this.vpnConfig.getProfileEntry(str2);
                if (profileEntry2 == null) {
                    it = it2;
                } else {
                    HashSet hashSet2 = new HashSet();
                    int i2 = profileEntry2.mVendorUid;
                    int i3 = profileEntry2.personaId;
                    String regularPackageName2 = getRegularPackageName(profileEntry2.mVendorPkgName);
                    Iterator it3 = profileEntry2.mExemptPackageList.iterator();
                    while (it3.hasNext()) {
                        Integer num = (Integer) it3.next();
                        int intValue = num.intValue();
                        if (intValue > 0 && UserHandle.getUserId(intValue) == cid) {
                            hashSet2.add(num);
                        }
                    }
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    if (cid == 0) {
                        Integer[] numArr = KnoxVpnConstants.AID_EXEMPT_LIST;
                        it = it2;
                        for (int i4 = 0; i4 < 3; i4++) {
                            Integer num2 = numArr[i4];
                            num2.getClass();
                            hashSet2.add(num2);
                        }
                        if (profileEntry2.mVendorPkgName.contains("com.samsung.sVpn")) {
                            i2 = 1016;
                        }
                        if (profileEntry2.chainingEnabled == 1 && (nonChainedVendoUid2 = getNonChainedVendoUid(profileEntry2.mVendorUid)) > 0 && UserHandle.getUserId(nonChainedVendoUid2) == cid) {
                            hashSet2.add(Integer.valueOf(nonChainedVendoUid2));
                        }
                        if (i3 == cid) {
                            hashSet2.add(Integer.valueOf(i2));
                        }
                        getVpnManagerService().updateUidRangesToUserVpnWithBlackList(str, i3, cid, new ArrayList(hashSet2).stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray(), regularPackageName2);
                    } else {
                        it = it2;
                        if (profileEntry2.mVendorPkgName.contains("com.samsung.sVpn")) {
                            getVpnManagerService().updateUidRangesToUserVpn(str, i3, true, cid, regularPackageName2);
                        } else {
                            if (profileEntry2.chainingEnabled == 1 && (nonChainedVendoUid = getNonChainedVendoUid(profileEntry2.mVendorUid)) > 0 && UserHandle.getUserId(nonChainedVendoUid) == cid) {
                                hashSet2.add(Integer.valueOf(nonChainedVendoUid));
                            }
                            if (i3 == cid) {
                                hashSet2.add(Integer.valueOf(i2));
                            }
                            getVpnManagerService().updateUidRangesToUserVpnWithBlackList(str, i3, cid, new ArrayList(hashSet2).stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray(), regularPackageName2);
                        }
                    }
                }
            } else {
                it = it2;
                if (uid > 0) {
                    hashSet.add(Integer.valueOf(uid));
                }
            }
            str2 = str;
        }
        if (!hashSet.isEmpty()) {
            int[] array = new ArrayList(hashSet).stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                getVpnManagerService().updateUidRangesToPerAppVpn(str, i, true, array, regularPackageName);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (profileEntry.activateState == 1) {
            applyBlockingRulesToUidRange(str, true);
        } else {
            applyBlockingRulesToUidRange(str, false);
        }
    }

    public final void updateUsbTetherAuthDetails(String str, Bundle bundle, boolean z) {
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
        this.mVpnStorageProvider.getClass();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("update db with usbTetheringAuthConfig result is ", "FW-KnoxVpnHelper", KnoxVpnStorageProvider.mEDM.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues));
    }

    public final void updateUsbTetheringForProfileInDb(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("usbTethering", Integer.valueOf(i));
        this.mVpnStorageProvider.getClass();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("updateUsbTetheringForProfileInDb ", "FW-KnoxVpnHelper", KnoxVpnStorageProvider.mEDM.putDataByFields("VpnProfileInfo", new String[]{"profileName"}, new String[]{str}, contentValues));
    }
}
