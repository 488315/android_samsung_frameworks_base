package com.android.wifitrackerlib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TransportInfo;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Utils {
    public static final List defaultSsidList = Arrays.asList("linksys", "netgear", "dlink", "wireless", "2wire", "iptime", "iptime_5g", "sm_base_17f_5g-1");

    public static String getAutoConnectDescription(Context context, WifiEntry wifiEntry) {
        return (context == null || wifiEntry == null || !wifiEntry.canSetAutoJoinEnabled() || wifiEntry.isAutoJoinEnabled()) ? "" : context.getString(R.string.wifi_auto_reconnect_disabled);
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

    public static String getCarrierNameForSubId(int i, Context context) {
        TelephonyManager telephonyManager;
        TelephonyManager createForSubscriptionId;
        CharSequence simCarrierIdName;
        if (i == -1 || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null || (createForSubscriptionId = telephonyManager.createForSubscriptionId(i)) == null || (simCarrierIdName = createForSubscriptionId.getSimCarrierIdName()) == null) {
            return null;
        }
        return simCarrierIdName.toString();
    }

    public static String getCarrierNetworkOffloadDescription(Context context, WifiEntry wifiEntry, WifiManager wifiManager) {
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        return (context == null || wifiConfiguration == null || !wifiEntry.mSemFlags.isCarrierNetwork || wifiManager.isCarrierNetworkOffloadEnabled(wifiConfiguration.subscriptionId, false)) ? "" : context.getString(R.string.wifi_auto_reconnect_disabled);
    }

    public static String getConnectedDescription(Context context, WifiInfo wifiInfo, WifiConfiguration wifiConfiguration, NetworkCapabilities networkCapabilities) {
        StringJoiner stringJoiner = new StringJoiner(context.getString(R.string.wifitrackerlib_summary_separator));
        if (wifiConfiguration != null && (wifiConfiguration.fromWifiNetworkSuggestion || wifiConfiguration.fromWifiNetworkSpecifier)) {
            String requestingPackageName = wifiInfo != null ? wifiInfo.getRequestingPackageName() : null;
            if (!TextUtils.isEmpty(requestingPackageName)) {
                String carrierNameForSubId = getCarrierNameForSubId(getSubIdForConfig(context, wifiConfiguration), context);
                String str = "";
                if (TextUtils.isEmpty(carrierNameForSubId)) {
                    try {
                        str = context.getPackageManager().getApplicationInfo(requestingPackageName, 0).loadLabel(context.getPackageManager()).toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                String currentNetworkCapabilitiesInformation = getCurrentNetworkCapabilitiesInformation(context, networkCapabilities);
                if (currentNetworkCapabilitiesInformation != null && !TextUtils.isEmpty(currentNetworkCapabilitiesInformation)) {
                    return currentNetworkCapabilitiesInformation;
                }
                Object[] objArr = new Object[1];
                if (carrierNameForSubId == null) {
                    carrierNameForSubId = str;
                }
                objArr[0] = carrierNameForSubId;
                return context.getString(R.string.wifitrackerlib_connected_via_app, objArr);
            }
        }
        String currentNetworkCapabilitiesInformation2 = getCurrentNetworkCapabilitiesInformation(context, networkCapabilities);
        if (!TextUtils.isEmpty(currentNetworkCapabilitiesInformation2)) {
            stringJoiner.add(currentNetworkCapabilitiesInformation2);
        }
        return stringJoiner.length() == 0 ? context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status)[NetworkInfo.DetailedState.CONNECTED.ordinal()] : stringJoiner.toString();
    }

    public static String getConnectingDescription(Context context, NetworkInfo networkInfo) {
        if (context == null || networkInfo == null) {
            return "";
        }
        String[] stringArray = context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status);
        int ordinal = NetworkInfo.DetailedState.CONNECTING.ordinal();
        return ordinal >= stringArray.length ? "" : stringArray[ordinal];
    }

    public static String getCurrentNetworkCapabilitiesInformation(Context context, NetworkCapabilities networkCapabilities) {
        if (context == null) {
            return "";
        }
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
                return "";
            }
        }
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (networkCapabilities.hasCapability(17)) {
            return context.getString(R.string.wifi_sign_in_to_the_network);
        }
        if (WcmUtils.isWcmSupported == -1) {
            WcmUtils.isWcmSupported = 1;
        }
        if (!(WcmUtils.isWcmSupported == 1) && !networkCapabilities.hasCapability(24)) {
            return (networkCapabilities.hasCapability(16) || networkCapabilities.isPrivateDnsBroken()) ? "" : context.getString(R.string.wifi_internet_may_not_be_available);
        }
        if (WcmUtils.isWcmSupported == -1) {
            WcmUtils.isWcmSupported = 1;
        }
        if (!(WcmUtils.isWcmSupported == 1)) {
            return context.getString(R.string.wifi_internet_may_not_be_available);
        }
        if (!(semWifiManager == null || semWifiManager.getWcmEverQualityTested() == 1)) {
            return context.getString(R.string.wifi_connected_checking_quality);
        }
        int currentStatusMode = semWifiManager != null ? semWifiManager.getCurrentStatusMode() : 0;
        if (currentStatusMode == 1 || currentStatusMode == 2) {
            return context.getString(R.string.wifi_internet_may_not_be_available);
        }
        if (currentStatusMode == 3) {
            return context.getString(R.string.wifi_reconnecting);
        }
        return "";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0100, code lost:
    
        if (r9 != 11) goto L138;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getDisconnectedDescription(Context context, WifiConfiguration wifiConfiguration, SemWifiEntryFlags semWifiEntryFlags) {
        if (context != null && wifiConfiguration != null) {
            SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            String key = wifiConfiguration.getKey();
            List<SemWifiConfiguration> configuredNetworks = semWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null && !configuredNetworks.isEmpty()) {
                for (SemWifiConfiguration semWifiConfiguration : configuredNetworks) {
                    if (semWifiConfiguration.configKey.equals(key)) {
                        break;
                    }
                }
            }
            semWifiConfiguration = null;
            if (semWifiEntryFlags.isOpenRoamingNetwork) {
                return context.getString(R.string.available_using_samsung_account);
            }
            if (wifiConfiguration.isPasspoint()) {
                return String.format(context.getString(R.string.wifitrackerlib_available_via_app), wifiConfiguration.providerFriendlyName);
            }
            boolean z = false;
            if (wifiConfiguration.fromWifiNetworkSuggestion || wifiConfiguration.fromWifiNetworkSpecifier) {
                String carrierNameForSubId = getCarrierNameForSubId(getSubIdForConfig(context, wifiConfiguration), context);
                if (TextUtils.isEmpty(carrierNameForSubId)) {
                    try {
                        carrierNameForSubId = context.getPackageManager().getApplicationInfo(wifiConfiguration.creatorName, 0).loadLabel(context.getPackageManager()).toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                        carrierNameForSubId = "";
                    }
                    if (TextUtils.isEmpty(carrierNameForSubId)) {
                        carrierNameForSubId = wifiConfiguration.creatorName;
                    }
                }
                if (!TextUtils.isEmpty(carrierNameForSubId)) {
                    return context.getString(R.string.wifitrackerlib_available_via_app, carrierNameForSubId);
                }
            } else {
                if (wifiConfiguration.getNetworkSelectionStatus().getNetworkSelectionStatus() == 0) {
                    if (semWifiConfiguration == null || semWifiConfiguration.networkDisableReason == 0) {
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
                        }
                    }
                    SemWifiConfiguration semWifiConfiguration2 = semWifiEntryFlags.semConfig;
                    int i = semWifiConfiguration2 != null ? semWifiConfiguration2.networkDisableReason : 0;
                    if (i != 2) {
                        if (i == 3) {
                            return (!wifiConfiguration.allowedKeyManagement.get(8) || wifiConfiguration.getNetworkSelectionStatus().hasEverConnected()) ? context.getString(R.string.wifi_tips_ap_guide_connection_failed) : context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
                        }
                    }
                    if (!wifiConfiguration.getNetworkSelectionStatus().hasEverConnected()) {
                        WifiEnterpriseConfig wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig;
                        if (wifiEnterpriseConfig != null && wifiEnterpriseConfig.getEapMethod() != -1) {
                            z = true;
                        }
                        if (!z) {
                            return context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
                        }
                    }
                    return context.getString(R.string.wifi_tips_ap_guide_connection_failed);
                }
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
                        return context.getString(R.string.wifi_disabled_no_internet);
                    case 7:
                        if (semWifiConfiguration != null) {
                            int i2 = semWifiConfiguration.networkDisableReason;
                            if (i2 == 1) {
                                return context.getString(R.string.wifi_tips_ap_guide_connection_blocked_by_another_network);
                            }
                            if (i2 == 5) {
                                return context.getString(R.string.wifi_disabled_captive_portal);
                            }
                            if (i2 == 6 || i2 == 7) {
                                return context.getString(R.string.wifi_disabled_no_internet);
                            }
                            if (semWifiConfiguration.disableTimeByWcm != 0 || semWifiConfiguration.disableTimeByEle != 0) {
                                return context.getString(R.string.wifi_disabled_no_internet);
                            }
                        }
                        break;
                    case 8:
                        return context.getString(R.string.wifi_tips_ap_guide_incorrect_password);
                    case 12:
                        return context.getString(R.string.wifi_tips_ap_guide_connection_failed);
                    case 13:
                        return context.getString(R.string.wifi_tips_ap_guide_disabled_transition_disable_indication);
                }
            }
        }
        return "";
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
        int i = -1;
        if (wifiConfiguration.carrierId == -1 || (subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service")) == null) {
            return -1;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null && !activeSubscriptionInfoList.isEmpty()) {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.getCarrierId() == wifiConfiguration.carrierId && (i = subscriptionInfo.getSubscriptionId()) == defaultDataSubscriptionId) {
                    break;
                }
            }
        }
        return i;
    }

    public static String getVerboseLoggingDescription(WifiEntry wifiEntry, SemWifiEntryFlags semWifiEntryFlags) {
        String stringJoiner;
        if (!BaseWifiTracker.sVerboseLogging || wifiEntry == null) {
            return "";
        }
        StringJoiner stringJoiner2 = new StringJoiner(" ");
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
        if (semWifiEntryFlags.hasVHTVSICapabilities) {
            sb.append(" giga");
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
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(sb2)) {
            stringJoiner2.add(sb2);
        }
        synchronized (wifiEntry) {
            StringJoiner stringJoiner3 = new StringJoiner(" ");
            if (wifiEntry.getConnectedState() == 2 && wifiEntry.mWifiInfo != null) {
                stringJoiner3.add("f = " + wifiEntry.mWifiInfo.getFrequency());
                String bssid = wifiEntry.mWifiInfo.getBSSID();
                if (bssid != null) {
                    stringJoiner3.add(bssid);
                }
                stringJoiner3.add("standard = " + wifiEntry.getStandardString());
                stringJoiner3.add("rssi = " + wifiEntry.mWifiInfo.getRssi());
                stringJoiner3.add("score = " + wifiEntry.mWifiInfo.getScore());
                stringJoiner3.add(String.format(" tx=%.1f,", Double.valueOf(wifiEntry.mWifiInfo.getSuccessfulTxPacketsPerSecond())));
                stringJoiner3.add(String.format("%.1f,", Double.valueOf(wifiEntry.mWifiInfo.getRetriedTxPacketsPerSecond())));
                stringJoiner3.add(String.format("%.1f ", Double.valueOf(wifiEntry.mWifiInfo.getLostTxPacketsPerSecond())));
                stringJoiner3.add(String.format("rx=%.1f", Double.valueOf(wifiEntry.mWifiInfo.getSuccessfulRxPacketsPerSecond())));
                if (wifiEntry.mWifiInfo.getApMldMacAddress() != null) {
                    stringJoiner3.add("mldMac = " + wifiEntry.mWifiInfo.getApMldMacAddress());
                    stringJoiner3.add("linkId = " + wifiEntry.mWifiInfo.getApMloLinkId());
                    stringJoiner3.add("affLinks = " + Arrays.toString(wifiEntry.mWifiInfo.getAffiliatedMloLinks().toArray()));
                }
            }
            stringJoiner = stringJoiner3.toString();
        }
        if (!TextUtils.isEmpty(stringJoiner)) {
            stringJoiner2.add(stringJoiner);
        }
        StringBuilder sb3 = new StringBuilder();
        if (wifiEntry.getConnectedState() == 2) {
            sb3.append("hasInternet:");
            sb3.append(wifiEntry.hasInternetAccess());
            sb3.append(", isDefaultNetwork:");
            sb3.append(wifiEntry.mIsDefaultNetwork);
            sb3.append(", isLowQuality:");
            sb3.append(wifiEntry.isLowQuality());
        }
        String sb4 = sb3.toString();
        if (!TextUtils.isEmpty(sb4)) {
            stringJoiner2.add(sb4);
        }
        String scanResultDescription = wifiEntry.getScanResultDescription();
        if (!TextUtils.isEmpty(scanResultDescription)) {
            stringJoiner2.add(scanResultDescription);
        }
        String networkSelectionDescription = wifiEntry.getNetworkSelectionDescription();
        if (!TextUtils.isEmpty(networkSelectionDescription)) {
            stringJoiner2.add(networkSelectionDescription);
        }
        return stringJoiner2.toString();
    }

    public static String getWarningDescription(Context context, WifiEntry wifiEntry) {
        if (context != null && wifiEntry != null) {
            if (wifiEntry.getSecurity() == 0) {
                return context.getString(R.string.wifi_open_warning_summary);
            }
            if (wifiEntry.getSecurity() == 1) {
                return context.getString(R.string.wifi_wep_warning_summary);
            }
            if (defaultSsidList.contains(wifiEntry.getTitle().toLowerCase())) {
                return context.getString(R.string.wifi_default_ssid_warning_summary);
            }
        }
        return "";
    }

    public static WifiInfo getWifiInfo(NetworkCapabilities networkCapabilities) {
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        VcnTransportInfo transportInfo2 = networkCapabilities.getTransportInfo();
        if (transportInfo2 instanceof VcnTransportInfo) {
            return transportInfo2.getWifiInfo();
        }
        return null;
    }

    public static boolean shouldSetHiddenSsid(String str, WifiManager wifiManager) {
        List<WifiConfiguration> configuredNetworks;
        if (TextUtils.isEmpty(str) || wifiManager == null || (configuredNetworks = wifiManager.getConfiguredNetworks()) == null) {
            return false;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.hiddenSSID && str.equals(wifiConfiguration.SSID)) {
                return true;
            }
        }
        return false;
    }
}
