package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Pair;
import androidx.core.os.BuildCompat;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WcmUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class Utils {
    public static final List defaultSsidList = Arrays.asList("linksys", "netgear", "dlink", "wireless", "2wire", "iptime", "iptime5g");

    public static String getAppLabel(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(str, 0).loadLabel(context.getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    public static String getAutoConnectDescription(Context context, WifiEntry wifiEntry) {
        if (context == null || !wifiEntry.canSetAutoJoinEnabled()) {
            return "";
        }
        if (!SemWifiEntryFlags.isWepAllowed(context) && wifiEntry.getSecurity() == 1) {
            return context.getString(R.string.wifi_wep_networks_blocked_summary);
        }
        if (SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin == -1) {
            SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin = Settings.Secure.getInt(context.getContentResolver(), "rampart_blocked_unsecure_wifi_autojoin", 0);
        }
        return (SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin == 1 && (wifiEntry.getSecurity() == 0 || wifiEntry.getSecurity() == 1 || wifiEntry.getSecurity() == 4)) ? context.getString(R.string.wifi_auto_blocker_blocked_summary) : wifiEntry.isAutoJoinEnabled() ? "" : context.getString(R.string.wifi_auto_reconnect_disabled);
    }

    public static String getBandString(int i, Context context) {
        return i == 0 ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_24_ghz) : i == 1 ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_5_ghz) : i == 2 ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_6_ghz) : i == 3 ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_60_ghz) : context.getResources().getString(R.string.wifitrackerlib_wifi_band_unknown);
    }

    public static ScanResult getBestScanResultByLevel(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return (ScanResult) Collections.max(list, Comparator.comparingInt(new Utils$$ExternalSyntheticLambda0()));
    }

    public static String getCarrierNetworkOffloadDescription(Context context, WifiEntry wifiEntry, WifiManager wifiManager) {
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        return (context == null || wifiConfiguration == null || !wifiEntry.mSemFlags.isCarrierNetwork || wifiManager.isCarrierNetworkOffloadEnabled(wifiConfiguration.subscriptionId, false)) ? "" : context.getString(R.string.wifi_auto_reconnect_disabled);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0047 A[PHI: r14
      0x0047: PHI (r14v1 android.net.NetworkCapabilities) = (r14v0 android.net.NetworkCapabilities), (r14v18 android.net.NetworkCapabilities) binds: [B:3:0x0012, B:19:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getConnectedDescription(Context context, WifiInfo wifiInfo, WifiConfiguration wifiConfiguration, NetworkCapabilities networkCapabilities) {
        String string;
        TelephonyManager telephonyManager;
        TelephonyManager telephonyManagerCreateForSubscriptionId;
        StringJoiner stringJoiner = new StringJoiner(context.getString(R.string.wifitrackerlib_summary_separator));
        String simOperatorName = null;
        if (networkCapabilities == null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            for (Network network : connectivityManager.getAllNetworks()) {
                if (network != null && (networkCapabilities = connectivityManager.getNetworkCapabilities(network)) != null) {
                    if (networkCapabilities.hasTransport(1) && !networkCapabilities.hasTransport(6)) {
                        break;
                    }
                    networkCapabilities = null;
                }
            }
            if (networkCapabilities == null) {
                string = "";
            } else {
                SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                if (networkCapabilities.hasCapability(17)) {
                    string = context.getString(R.string.wifi_sign_in_to_the_network);
                } else {
                    if (WcmUtils.isWcmSupported == -1) {
                        WcmUtils.isWcmSupported = 1;
                    }
                    if ((WcmUtils.isWcmSupported == 1) || networkCapabilities.hasCapability(24)) {
                        if (WcmUtils.isWcmSupported == -1) {
                            WcmUtils.isWcmSupported = 1;
                        }
                        if (WcmUtils.isWcmSupported != 1) {
                            string = context.getString(R.string.wifi_internet_may_not_be_available);
                        } else if (semWifiManager == null || semWifiManager.getWcmEverQualityTested() == 1) {
                            int currentStatusMode = semWifiManager != null ? semWifiManager.getCurrentStatusMode() : 0;
                            if (currentStatusMode == 1 || currentStatusMode == 2) {
                                string = context.getString(R.string.wifi_internet_may_not_be_available);
                            } else if (currentStatusMode == 3) {
                                string = context.getString(R.string.wifi_reconnecting);
                            }
                        } else if (!networkCapabilities.hasCapability(16)) {
                            string = context.getString(R.string.wifi_connected_checking_quality);
                        }
                    } else if (!networkCapabilities.hasCapability(16) && !networkCapabilities.isPrivateDnsBroken()) {
                        string = context.getString(R.string.wifi_internet_may_not_be_available);
                    }
                }
            }
        }
        if (wifiConfiguration != null && (wifiConfiguration.fromWifiNetworkSuggestion || wifiConfiguration.fromWifiNetworkSpecifier)) {
            String requestingPackageName = wifiInfo != null ? wifiInfo.getRequestingPackageName() : null;
            if (!TextUtils.isEmpty(requestingPackageName)) {
                int subIdForConfig = getSubIdForConfig(context, wifiConfiguration);
                if (subIdForConfig != -1 && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && (telephonyManagerCreateForSubscriptionId = telephonyManager.createForSubscriptionId(subIdForConfig)) != null) {
                    simOperatorName = telephonyManagerCreateForSubscriptionId.getSimOperatorName();
                }
                String appLabel = TextUtils.isEmpty(simOperatorName) ? getAppLabel(context, requestingPackageName) : "";
                if (!TextUtils.isEmpty(string)) {
                    return string;
                }
                if (simOperatorName == null) {
                    simOperatorName = appLabel;
                }
                return context.getString(R.string.wifitrackerlib_connected_via_app, simOperatorName);
            }
        }
        if (!TextUtils.isEmpty(string)) {
            stringJoiner.add(string);
        }
        return stringJoiner.length() == 0 ? context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status)[NetworkInfo.DetailedState.CONNECTED.ordinal()] : stringJoiner.toString();
    }

    public static String getDisconnectedDescription(Context context, WifiConfiguration wifiConfiguration, SemWifiEntryFlags semWifiEntryFlags) {
        TelephonyManager telephonyManager;
        TelephonyManager telephonyManagerCreateForSubscriptionId;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (context == null || wifiConfiguration == null) {
            return "";
        }
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        String key = wifiConfiguration.getKey();
        List<SemWifiConfiguration> configuredNetworks = semWifiManager.getConfiguredNetworks();
        String appLabel = null;
        if (configuredNetworks == null || configuredNetworks.isEmpty()) {
            semWifiConfiguration = null;
        } else {
            for (SemWifiConfiguration semWifiConfiguration : configuredNetworks) {
                if (semWifiConfiguration.configKey.equals(key)) {
                    break;
                }
            }
            semWifiConfiguration = null;
        }
        if (semWifiEntryFlags.isOpenRoamingNetwork) {
            return context.getString(R.string.available_using_samsung_account);
        }
        if (wifiConfiguration.isPasspoint()) {
            return String.format(context.getString(R.string.wifitrackerlib_available_via_app), wifiConfiguration.providerFriendlyName);
        }
        if (wifiConfiguration.fromWifiNetworkSuggestion || wifiConfiguration.fromWifiNetworkSpecifier) {
            int subIdForConfig = getSubIdForConfig(context, wifiConfiguration);
            if (subIdForConfig != -1 && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && (telephonyManagerCreateForSubscriptionId = telephonyManager.createForSubscriptionId(subIdForConfig)) != null) {
                appLabel = telephonyManagerCreateForSubscriptionId.getSimOperatorName();
            }
            if (TextUtils.isEmpty(appLabel)) {
                appLabel = getAppLabel(context, wifiConfiguration.creatorName);
                if (TextUtils.isEmpty(appLabel)) {
                    appLabel = wifiConfiguration.creatorName;
                }
            }
            return !TextUtils.isEmpty(appLabel) ? context.getString(R.string.wifitrackerlib_available_via_app, appLabel) : "";
        }
        if (wifiConfiguration.getNetworkSelectionStatus().getNetworkSelectionStatus() != 0) {
            switch (wifiConfiguration.getNetworkSelectionStatus().getNetworkSelectionDisableReason()) {
                case 1:
                case 2:
                case 5:
                case 9:
                    return context.getString(R.string.wifi_tips_ap_guide_connection_failed);
                case 3:
                    return context.getString(R.string.wifi_tips_ap_guide_couldnt_get_ip_address);
                case 4:
                case 6:
                    return (semWifiConfiguration == null || semWifiConfiguration.networkDisableReason != 5) ? context.getString(R.string.wifi_disabled_no_internet) : context.getString(R.string.wifi_disabled_captive_portal);
                case 7:
                    if (semWifiConfiguration == null) {
                        return "";
                    }
                    int i = semWifiConfiguration.networkDisableReason;
                    return i == 1 ? context.getString(R.string.wifi_tips_ap_guide_connection_blocked_by_another_network) : i == 5 ? context.getString(R.string.wifi_disabled_captive_portal) : (i == 6 || i == 7) ? context.getString(R.string.wifi_disabled_no_internet) : (semWifiConfiguration.disableTimeByWcm == 0 && semWifiConfiguration.disableTimeByEle == 0) ? "" : context.getString(R.string.wifi_disabled_no_internet);
                case 8:
                    return context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
                case 10:
                case 11:
                default:
                    return "";
                case 12:
                    return context.getString(R.string.wifi_tips_ap_guide_connection_failed);
                case 13:
                    return context.getString(R.string.wifi_tips_ap_guide_disabled_transition_disable_indication);
            }
        }
        if (semWifiConfiguration != null && semWifiConfiguration.networkDisableReason != 0) {
            SemWifiConfiguration semWifiConfiguration2 = semWifiEntryFlags.semConfig;
            int i2 = semWifiConfiguration2 != null ? semWifiConfiguration2.networkDisableReason : 0;
            if (i2 != 2) {
                if (i2 == 3) {
                    return (!wifiConfiguration.allowedKeyManagement.get(8) || wifiConfiguration.getNetworkSelectionStatus().hasEverConnected()) ? context.getString(R.string.wifi_tips_ap_guide_connection_failed) : context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
                }
                if (i2 != 11) {
                    return "";
                }
            }
            return (wifiConfiguration.getNetworkSelectionStatus().hasEverConnected() || !((wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) == null || wifiEnterpriseConfig.getEapMethod() == -1)) ? context.getString(R.string.wifi_tips_ap_guide_connection_failed) : context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
        }
        int recentFailureReason = wifiConfiguration.getRecentFailureReason();
        if (recentFailureReason == 17) {
            return context.getString(R.string.wifitrackerlib_wifi_ap_unable_to_handle_new_sta);
        }
        if (recentFailureReason == 1003) {
            return context.getString(R.string.wifitrackerlib_wifi_poor_channel_conditions);
        }
        switch (recentFailureReason) {
            case 1005:
            case 1007:
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                return context.getString(R.string.wifitrackerlib_wifi_mbo_assoc_disallowed_cannot_connect);
            case 1006:
                return context.getString(R.string.wifitrackerlib_wifi_mbo_assoc_disallowed_max_num_sta_associated);
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
            case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                return context.getString(R.string.wifitrackerlib_wifi_mbo_oce_assoc_disallowed_insufficient_rssi);
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION /* 1011 */:
                return context.getString(R.string.wifitrackerlib_wifi_network_not_found);
            default:
                return "";
        }
    }

    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new IllegalArgumentException("IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (255 << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new IllegalArgumentException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }

    public static String getNetworkSelectionDescription(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        WifiConfiguration.NetworkSelectionStatus networkSelectionStatus = wifiConfiguration.getNetworkSelectionStatus();
        if (networkSelectionStatus.getNetworkSelectionStatus() != 0) {
            sb.append(" (" + networkSelectionStatus.getNetworkStatusString());
            if (networkSelectionStatus.getDisableTime() > 0) {
                sb.append(" " + DateUtils.formatElapsedTime((System.currentTimeMillis() - networkSelectionStatus.getDisableTime()) / 1000));
            }
            sb.append(")");
        }
        int maxNetworkSelectionDisableReason = WifiConfiguration.NetworkSelectionStatus.getMaxNetworkSelectionDisableReason();
        for (int i = 0; i <= maxNetworkSelectionDisableReason; i++) {
            int disableReasonCounter = networkSelectionStatus.getDisableReasonCounter(i);
            if (disableReasonCounter != 0) {
                sb.append(" ");
                sb.append(WifiConfiguration.NetworkSelectionStatus.getNetworkSelectionDisableReasonString(i));
                sb.append("=");
                sb.append(disableReasonCounter);
            }
        }
        return sb.toString();
    }

    public static String getSecurityString(Context context, List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.size() == 0) {
            return "";
        }
        if (arrayList.size() == 1) {
            int iIntValue = ((Integer) arrayList.get(0)).intValue();
            if (iIntValue == 9) {
                return context.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa3);
            }
            switch (iIntValue) {
                case 0:
                    return "";
                case 1:
                    return context.getString(R.string.wifitrackerlib_wifi_security_wep);
                case 2:
                    return context.getString(R.string.wifitrackerlib_wifi_security_short_wpa_wpa2);
                case 3:
                    return context.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2);
                case 4:
                    return context.getString(R.string.wifitrackerlib_wifi_security_short_sae);
                case 5:
                    return context.getString(R.string.wifitrackerlib_wifi_security_short_eap_suiteb);
                case 6:
                    return context.getString(R.string.wifitrackerlib_wifi_security_short_owe);
            }
        }
        if (arrayList.size() != 2) {
            return "";
        }
        if (!arrayList.contains(0) || !arrayList.contains(6)) {
            return (arrayList.contains(2) && arrayList.contains(4)) ? context.getString(R.string.wifitrackerlib_wifi_security_short_wpa_wpa2_wpa3) : (arrayList.contains(3) && arrayList.contains(9)) ? context.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2_wpa3) : "";
        }
        StringJoiner stringJoiner = new StringJoiner("/");
        stringJoiner.add(context.getString(R.string.wifitrackerlib_wifi_security_none));
        stringJoiner.add(context.getString(R.string.wifitrackerlib_wifi_security_short_owe));
        return stringJoiner.toString();
    }

    public static List getSecurityTypesFromWifiConfiguration(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(14)) {
            return Arrays.asList(8);
        }
        if (wifiConfiguration.allowedKeyManagement.get(13)) {
            return Arrays.asList(7);
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return Arrays.asList(5);
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return Arrays.asList(6);
        }
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return Arrays.asList(4);
        }
        if (wifiConfiguration.allowedKeyManagement.get(4)) {
            return Arrays.asList(2);
        }
        if (wifiConfiguration.allowedKeyManagement.get(2)) {
            return (wifiConfiguration.requirePmf && !wifiConfiguration.allowedPairwiseCiphers.get(1) && wifiConfiguration.allowedProtocols.get(1)) ? Arrays.asList(9) : Arrays.asList(3, 9);
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return Arrays.asList(2);
        }
        if (wifiConfiguration.allowedKeyManagement.get(0) && wifiConfiguration.wepKeys != null) {
            int i = 0;
            while (true) {
                String[] strArr = wifiConfiguration.wepKeys;
                if (i >= strArr.length) {
                    break;
                }
                if (strArr[i] != null) {
                    return Arrays.asList(1);
                }
                i++;
            }
        }
        return Arrays.asList(0);
    }

    public static int getSingleSecurityTypeFromMultipleSecurityTypes(List list) {
        if (list.size() == 0) {
            return -1;
        }
        if (list.size() == 1) {
            return ((Integer) list.get(0)).intValue();
        }
        if (list.size() == 2) {
            if (list.contains(0)) {
                return 0;
            }
            if (list.contains(2)) {
                return 2;
            }
            if (list.contains(3)) {
                return 3;
            }
        }
        return ((Integer) list.get(0)).intValue();
    }

    public static String getStandardString(int i, Context context) {
        if (i == 1) {
            return context.getString(R.string.wifitrackerlib_wifi_standard_legacy);
        }
        switch (i) {
            case 4:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11n);
            case 5:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ac);
            case 6:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ax);
            case 7:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ad);
            case 8:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11be);
            default:
                return context.getString(R.string.wifitrackerlib_wifi_standard_unknown);
        }
    }

    public static int getSubIdForConfig(Context context, WifiConfiguration wifiConfiguration) {
        SubscriptionManager subscriptionManager;
        int subscriptionId = -1;
        if (wifiConfiguration.carrierId == -1 || (subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service")) == null) {
            return -1;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null && !activeSubscriptionInfoList.isEmpty()) {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.getCarrierId() == wifiConfiguration.carrierId && (subscriptionId = subscriptionInfo.getSubscriptionId()) == defaultDataSubscriptionId) {
                    break;
                }
            }
        }
        return subscriptionId;
    }

    public static String getVerboseLoggingDescription(WifiEntry wifiEntry, SemWifiEntryFlags semWifiEntryFlags) {
        String string;
        if (!BaseWifiTracker.sVerboseLogging) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        StringBuilder sb = new StringBuilder();
        int i = semWifiEntryFlags.wifiStandard;
        if (i != 0) {
            sb.append(i);
            if (semWifiEntryFlags.has6EStandard) {
                sb.append(ImsProfile.TIMER_NAME_E);
            }
            sb.append(ImsProfile.TIMER_NAME_G);
        }
        if (semWifiEntryFlags.staCount >= 0) {
            sb.append(" STAs:");
            sb.append(semWifiEntryFlags.staCount);
        }
        if (semWifiEntryFlags.passpointConfiguration != null) {
            sb.append(" hs20");
        }
        if (semWifiEntryFlags.isSamsungMobileHotspot) {
            sb.append(" sec-mhs");
        }
        if (semWifiEntryFlags.isCarrierNetwork) {
            sb.append(" carrier");
        }
        if (semWifiEntryFlags.isOpenRoamingNetwork) {
            sb.append(" oauth");
        }
        String string2 = sb.toString();
        if (!TextUtils.isEmpty(string2)) {
            stringJoiner.add(string2);
        }
        synchronized (wifiEntry) {
            try {
                StringJoiner stringJoiner2 = new StringJoiner(" ");
                if (wifiEntry.getConnectedState() == 2 && wifiEntry.mWifiInfo != null) {
                    stringJoiner2.add("f = " + wifiEntry.mWifiInfo.getFrequency());
                    String bssid = wifiEntry.mWifiInfo.getBSSID();
                    if (bssid != null) {
                        stringJoiner2.add(bssid);
                    }
                    stringJoiner2.add("standard = " + wifiEntry.getStandardString());
                    stringJoiner2.add("rssi = " + wifiEntry.mWifiInfo.getRssi());
                    stringJoiner2.add("score = " + wifiEntry.mWifiInfo.getScore());
                    stringJoiner2.add(String.format(" tx=%.1f,", Double.valueOf(wifiEntry.mWifiInfo.getSuccessfulTxPacketsPerSecond())));
                    stringJoiner2.add(String.format("%.1f,", Double.valueOf(wifiEntry.mWifiInfo.getRetriedTxPacketsPerSecond())));
                    stringJoiner2.add(String.format("%.1f ", Double.valueOf(wifiEntry.mWifiInfo.getLostTxPacketsPerSecond())));
                    stringJoiner2.add(String.format("rx=%.1f", Double.valueOf(wifiEntry.mWifiInfo.getSuccessfulRxPacketsPerSecond())));
                    int i2 = BuildCompat.$r8$clinit;
                    if (wifiEntry.mWifiInfo.getApMldMacAddress() != null) {
                        stringJoiner2.add("mldMac = " + wifiEntry.mWifiInfo.getApMldMacAddress());
                        stringJoiner2.add("linkId = " + wifiEntry.mWifiInfo.getApMloLinkId());
                        stringJoiner2.add("affLinks = " + Arrays.toString(wifiEntry.mWifiInfo.getAffiliatedMloLinks().toArray()));
                    }
                }
                string = stringJoiner2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!TextUtils.isEmpty(string)) {
            stringJoiner.add(string);
        }
        StringBuilder sb2 = new StringBuilder();
        if (wifiEntry.getConnectedState() == 2) {
            sb2.append("hasInternet:");
            sb2.append(wifiEntry.hasInternetAccess());
            sb2.append(", isDefaultNetwork:");
            sb2.append(wifiEntry.isDefaultNetwork());
            sb2.append(", isLowQuality:");
            sb2.append(wifiEntry.isLowQuality());
        }
        String string3 = sb2.toString();
        if (!TextUtils.isEmpty(string3)) {
            stringJoiner.add(string3);
        }
        String scanResultDescription = wifiEntry.getScanResultDescription();
        if (!TextUtils.isEmpty(scanResultDescription)) {
            stringJoiner.add(scanResultDescription);
        }
        String networkSelectionDescription = wifiEntry.getNetworkSelectionDescription();
        if (!TextUtils.isEmpty(networkSelectionDescription)) {
            stringJoiner.add(networkSelectionDescription);
        }
        return stringJoiner.toString();
    }

    public static String getWarningDescription(Context context, WifiEntry wifiEntry) {
        if (context == null) {
            return "";
        }
        if (wifiEntry.getSecurity() == 0) {
            return context.getString(R.string.wifi_open_warning_summary);
        }
        if (wifiEntry.getSecurity() == 1) {
            return context.getString(R.string.wifi_wep_warning_summary);
        }
        return defaultSsidList.contains(wifiEntry.getTitle().toLowerCase()) ? context.getString(R.string.wifi_default_ssid_warning_summary) : "";
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isDeviceOrProfileOwner(Context context, String str, int i) {
        Context contextCreatePackageContextAsUser;
        boolean zIsProfileOwnerApp;
        Pair pair;
        if (str != null) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
            if (devicePolicyManager == null) {
                pair = null;
                if (pair != null || !((UserHandle) pair.first).equals(UserHandle.getUserHandleForUid(i)) || !((ComponentName) pair.second).getPackageName().equals(str)) {
                    if (str != null) {
                        zIsProfileOwnerApp = false;
                        if (zIsProfileOwnerApp) {
                            return false;
                        }
                    } else {
                        try {
                            contextCreatePackageContextAsUser = context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.getUserHandleForUid(i));
                        } catch (PackageManager.NameNotFoundException unused) {
                            contextCreatePackageContextAsUser = null;
                        }
                        DevicePolicyManager devicePolicyManager2 = contextCreatePackageContextAsUser != null ? (DevicePolicyManager) contextCreatePackageContextAsUser.getSystemService(DevicePolicyManager.class) : null;
                        if (devicePolicyManager2 != null) {
                            zIsProfileOwnerApp = devicePolicyManager2.isProfileOwnerApp(str);
                        }
                        if (zIsProfileOwnerApp) {
                        }
                    }
                }
            } else {
                try {
                    UserHandle deviceOwnerUser = devicePolicyManager.getDeviceOwnerUser();
                    ComponentName deviceOwnerComponentOnAnyUser = devicePolicyManager.getDeviceOwnerComponentOnAnyUser();
                    if (deviceOwnerUser != null && deviceOwnerComponentOnAnyUser != null && deviceOwnerComponentOnAnyUser.getPackageName() != null) {
                        pair = new Pair(deviceOwnerUser, deviceOwnerComponentOnAnyUser);
                    }
                    if (pair != null) {
                        if (str != null) {
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("getDeviceOwner error - " + e.toString());
                }
            }
        }
        return true;
    }

    public static boolean shouldSetHiddenSsid(String str, WifiManager wifiManager) {
        List<WifiConfiguration> configuredNetworks;
        if (!TextUtils.isEmpty(str) && wifiManager != null && (configuredNetworks = wifiManager.getConfiguredNetworks()) != null) {
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                if (wifiConfiguration.hiddenSSID && str.equals(wifiConfiguration.SSID)) {
                    return true;
                }
            }
        }
        return false;
    }
}
