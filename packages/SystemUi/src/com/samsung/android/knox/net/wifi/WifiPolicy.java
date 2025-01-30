package com.samsung.android.knox.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WifiPolicy {
    public static final String ENGINE_ID_SECPKCS11 = "secpkcs11";
    public static final String ENGINE_ID_UCMENGINE = "ucsengine";
    public static final int SECURITY_LEVEL_EAP_AKA = 25;
    public static final int SECURITY_LEVEL_EAP_AKA_CCKM = 27;
    public static final int SECURITY_LEVEL_EAP_AKA_FT = 26;
    public static final int SECURITY_LEVEL_EAP_AKA_PRIME = 28;
    public static final int SECURITY_LEVEL_EAP_AKA_PRIME_CCKM = 30;
    public static final int SECURITY_LEVEL_EAP_AKA_PRIME_FT = 29;
    public static final int SECURITY_LEVEL_EAP_FAST = 4;
    public static final int SECURITY_LEVEL_EAP_FAST_CCKM = 18;
    public static final int SECURITY_LEVEL_EAP_FAST_FT = 17;
    public static final int SECURITY_LEVEL_EAP_LEAP = 3;
    public static final int SECURITY_LEVEL_EAP_LEAP_CCKM = 16;
    public static final int SECURITY_LEVEL_EAP_LEAP_FT = 15;
    public static final int SECURITY_LEVEL_EAP_PEAP = 5;
    public static final int SECURITY_LEVEL_EAP_PEAP_CCKM = 10;
    public static final int SECURITY_LEVEL_EAP_PEAP_FT = 9;
    public static final int SECURITY_LEVEL_EAP_PWD = 19;
    public static final int SECURITY_LEVEL_EAP_PWD_CCKM = 21;
    public static final int SECURITY_LEVEL_EAP_PWD_FT = 20;
    public static final int SECURITY_LEVEL_EAP_SIM = 22;
    public static final int SECURITY_LEVEL_EAP_SIM_CCKM = 24;
    public static final int SECURITY_LEVEL_EAP_SIM_FT = 23;
    public static final int SECURITY_LEVEL_EAP_TLS = 7;
    public static final int SECURITY_LEVEL_EAP_TLS_CCKM = 14;
    public static final int SECURITY_LEVEL_EAP_TLS_FT = 13;
    public static final int SECURITY_LEVEL_EAP_TTLS = 6;
    public static final int SECURITY_LEVEL_EAP_TTLS_CCKM = 12;
    public static final int SECURITY_LEVEL_EAP_TTLS_FT = 11;
    public static final int SECURITY_LEVEL_FT_PSK = 8;
    public static final int SECURITY_LEVEL_OPEN = 0;
    public static final int SECURITY_LEVEL_SAE = 31;
    public static final int SECURITY_LEVEL_WEP = 1;
    public static final int SECURITY_LEVEL_WPA = 2;
    public static final String SECURITY_TYPE_OPEN = "Open";
    public static final String SECURITY_TYPE_SAE = "SAE";
    public static final String SECURITY_TYPE_WPA2_PSK = "WPA2_PSK";
    public static final String SECURITY_TYPE_WPA_PSK = "WPA_PSK";
    public static String TAG = "WifiPolicy";
    public ContextInfo mContextInfo;
    public IWifiPolicy mService;

    public WifiPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean activateWifiSsidRestriction(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.activateWifiSsidRestriction");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.activateWifiSsidRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean addBlockedNetwork(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.addBlockedNetwork");
        return false;
    }

    public final int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.addNetworkWithRandomizationState");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addNetworkWithRandomizationState(wifiConfiguration, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return -1;
        }
    }

    public final boolean addWifiSsidsToBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.addWifiSsidsToBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addWifiSsidToBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean addWifiSsidsToWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.addWifiSsidsToWhiteList(List<String>)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addWifiSsidToWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi policy", e);
            return false;
        }
    }

    public final boolean allowOpenWifiAp(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.allowOpenWifiAp");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowOpenWifiAp(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean allowWifiApSettingUserModification(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.allowWifiApSettingUserModification");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowWifiApSettingUserModification(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean allowWifiScanning(boolean z, ContextInfo contextInfo) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowWifiScanning(contextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean clearWifiSsidsFromBlackList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.clearWifiSsidsFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearWifiSsidBlackList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean clearWifiSsidsFromList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.clearWifiSsidsFromList");
        return clearWifiSsidsFromWhiteList() || clearWifiSsidsFromBlackList();
    }

    public final boolean clearWifiSsidsFromWhiteList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.clearWifiSsidsFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.clearWifiSsidWhiteList(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean getAllowUserPolicyChanges() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAllowUserPolicyChanges(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getAllowUserPolicyChanges", e);
            return true;
        }
    }

    public final boolean getAllowUserProfiles(boolean z) {
        return getAllowUserProfilesInternal(z, -1);
    }

    public final boolean getAllowUserProfilesInternal(boolean z, int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAllowUserProfiles(this.mContextInfo, z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getAllowUserProfiles", e);
            return true;
        }
    }

    public final boolean getAutomaticConnectionToWifi() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAutomaticConnectionToWifi(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getAutomaticConnectionToWifi", e);
            return true;
        }
    }

    public final List<String> getBlockedNetworks() {
        if (getService() != null) {
            try {
                return this.mService.getBlockedNetworks(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "getBlockedNetworks - Failed talking with Wifi service", e);
            }
        }
        return new ArrayList(0);
    }

    public final int getMinimumRequiredSecurity() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMinimumRequiredSecurity(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getSupportedSecurity", e);
            return 0;
        }
    }

    public final List<String> getNetworkSSIDList() {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.getNetworkSSIDList", true);
        try {
            if (getService() != null) {
                return this.mService.getNetworkSSIDList(this.mContextInfo);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at WiFi policy API getNetworkSSID", e);
        }
        return new ArrayList(0);
    }

    public final boolean getPasswordHidden() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getPasswordHidden(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getPasswordHidden", e);
            return false;
        }
    }

    public final boolean getPromptCredentialsEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getPromptCredentialsEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with getPromptCredentialsEnabled", e);
            return true;
        }
    }

    public final IWifiPolicy getService() {
        if (this.mService == null) {
            this.mService = IWifiPolicy.Stub.asInterface(ServiceManager.getService("wifi_policy"));
        }
        return this.mService;
    }

    public final WifiConfiguration getWifiApSetting() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getWifiApSetting(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return null;
        }
    }

    public final WifiAdminProfile getWifiProfile(String str) {
        try {
            if (getService() != null) {
                return this.mService.getWifiProfile(this.mContextInfo, str);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at WiFi policy API getWifiProfile", e);
            return null;
        }
    }

    public final List<WifiControlInfo> getWifiSsidsFromBlackLists() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAllWifiSsidBlackLists(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi policy", e);
            return null;
        }
    }

    public final List<WifiControlInfo> getWifiSsidsFromWhiteLists() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAllWifiSsidWhiteLists(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return null;
        }
    }

    public final boolean isNetworkBlocked(String str, boolean z) {
        return false;
    }

    public final boolean isOpenWifiApAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isOpenWifiApAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return true;
        }
    }

    public final boolean isWifiAllowed(boolean z) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWifiAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with isWifiEnabled", e);
            return true;
        }
    }

    public final boolean isWifiApSettingUserModificationAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWifiApSettingUserModificationAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return true;
        }
    }

    public final boolean isWifiScanningAllowed() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isWifiScanningAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean isWifiSsidRestrictionActive() {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.isWifiSsidRestrictionActive", true);
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isWifiSsidRestrictionActive(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean isWifiStateChangeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isWifiStateChangeAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with isWifiEnabled", e);
            return true;
        }
    }

    public final boolean removeBlockedNetwork(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.removeBlockedNetwork");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeBlockedNetwork(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "removeBlockedNetwork - Failed talking with Wifi service", e);
            return false;
        }
    }

    public final boolean removeNetworkConfiguration(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.removeNetworkConfiguration");
        try {
            if (getService() != null) {
                return this.mService.removeNetworkConfiguration(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at WiFi policy API removeNetworkConfiguration", e);
            return false;
        }
    }

    public final boolean removeWifiSsidsFromBlackList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.removeWifiSsidsFromBlackList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeWifiSsidFromBlackList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean removeWifiSsidsFromWhiteList(List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.removeWifiSsidsFromWhiteList");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeWifiSsidFromWhiteList(this.mContextInfo, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with wifi policy", e);
            return false;
        }
    }

    public final boolean setAllowUserPolicyChanges(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setAllowUserPolicyChanges");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowUserPolicyChanges(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setAllowUserPolicyChanges", e);
            return false;
        }
    }

    public final boolean setAllowUserProfiles(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setAllowUserProfiles");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAllowUserProfiles(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setAllowUserProfiles", e);
            return false;
        }
    }

    public final boolean setAutomaticConnectionToWifi(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setAutomaticConnectionToWifi");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAutomaticConnectionToWifi(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setAutomaticConnectionToWifi", e);
            return false;
        }
    }

    public final boolean setMinimumRequiredSecurity(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setMinimumRequiredSecurity");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMinimumRequiredSecurity(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setMinimumRequiredSecurity", e);
            return false;
        }
    }

    public final boolean setPasswordHidden(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setPasswordHidden");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPasswordHidden(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setPasswordHidden", e);
            return false;
        }
    }

    public final boolean setPromptCredentialsEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setPromptCredentialsEnabled");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPromptCredentialsEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setPromptCredentialsEnabled", e);
            return false;
        }
    }

    public final boolean setWifi(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setWifi(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setWifiEnabled", e);
            return false;
        }
    }

    public final boolean setWifiAllowed(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setWifiAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setWifiEnabled", e);
            return false;
        }
    }

    public final boolean setWifiApSetting(String str, String str2, String str3) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setWifiApSetting");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setWifiApSetting(this.mContextInfo, str, str2, str3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with Wifi Policy", e);
            return false;
        }
    }

    public final boolean setWifiProfile(WifiAdminProfile wifiAdminProfile) {
        List<AuthConfig> list;
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setWifiProfile");
        int i = KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION;
        if (i < 16 && wifiAdminProfile != null && !TextUtils.isEmpty(wifiAdminProfile.mStorageName)) {
            return false;
        }
        if (i < 17 && wifiAdminProfile != null && (list = wifiAdminProfile.proxyAuthConfigList) != null && !list.isEmpty()) {
            return false;
        }
        try {
            if (getService() != null) {
                return this.mService.setWifiProfile(this.mContextInfo, wifiAdminProfile);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at WiFi policy API setWifiProfile", e);
        }
        return false;
    }

    public final boolean setWifiStateChangeAllowed(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.setWifiStateChangeAllowed");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setWifiStateChangeAllowed(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with setWifiEnabled", e);
            return false;
        }
    }

    public final boolean addWifiSsidsToWhiteList(List<String> list, boolean z) {
        boolean z2;
        EnterpriseLicenseManager.log(this.mContextInfo, "WifiPolicy.addWifiSsidsToWhiteList(List<String>, boolean)");
        ArrayList arrayList = new ArrayList();
        arrayList.add("*");
        if (!z || addWifiSsidsToBlackList(arrayList)) {
            z2 = true;
        } else {
            Log.d(TAG, "Failed to update wildCard in Blacklist, Wildcard may be already present!");
            z2 = false;
        }
        return addWifiSsidsToWhiteList(list) && z2;
    }
}
