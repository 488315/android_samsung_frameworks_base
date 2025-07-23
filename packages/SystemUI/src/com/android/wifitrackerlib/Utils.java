package com.android.wifitrackerlib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import androidx.core.os.BuildCompat;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        if (r14 == null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getConnectedDescription(android.content.Context r11, android.net.wifi.WifiInfo r12, android.net.wifi.WifiConfiguration r13, android.net.NetworkCapabilities r14) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.Utils.getConnectedDescription(android.content.Context, android.net.wifi.WifiInfo, android.net.wifi.WifiConfiguration, android.net.NetworkCapabilities):java.lang.String");
    }

    public static String getDisconnectedDescription(Context context, WifiConfiguration wifiConfiguration, SemWifiEntryFlags semWifiEntryFlags) {
        TelephonyManager telephonyManager;
        TelephonyManager createForSubscriptionId;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (context == null || wifiConfiguration == null) {
            return "";
        }
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        String key = wifiConfiguration.getKey();
        List<SemWifiConfiguration> configuredNetworks = semWifiManager.getConfiguredNetworks();
        String str = null;
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
        if (wifiConfiguration.fromWifiNetworkSuggestion || wifiConfiguration.fromWifiNetworkSpecifier) {
            int subIdForConfig = getSubIdForConfig(context, wifiConfiguration);
            if (subIdForConfig != -1 && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && (createForSubscriptionId = telephonyManager.createForSubscriptionId(subIdForConfig)) != null) {
                str = createForSubscriptionId.getSimOperatorName();
            }
            if (TextUtils.isEmpty(str)) {
                str = getAppLabel(context, wifiConfiguration.creatorName);
                if (TextUtils.isEmpty(str)) {
                    str = wifiConfiguration.creatorName;
                }
            }
            return !TextUtils.isEmpty(str) ? context.getString(R.string.wifitrackerlib_available_via_app, str) : "";
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
            int intValue = ((Integer) arrayList.get(0)).intValue();
            if (intValue == 9) {
                return context.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa3);
            }
            switch (intValue) {
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
        if (!BaseWifiTracker.sVerboseLogging) {
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
            try {
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
                    int i2 = BuildCompat.$r8$clinit;
                    if (wifiEntry.mWifiInfo.getApMldMacAddress() != null) {
                        stringJoiner3.add("mldMac = " + wifiEntry.mWifiInfo.getApMldMacAddress());
                        stringJoiner3.add("linkId = " + wifiEntry.mWifiInfo.getApMloLinkId());
                        stringJoiner3.add("affLinks = " + Arrays.toString(wifiEntry.mWifiInfo.getAffiliatedMloLinks().toArray()));
                    }
                }
                stringJoiner = stringJoiner3.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!TextUtils.isEmpty(stringJoiner)) {
            stringJoiner2.add(stringJoiner);
        }
        StringBuilder sb3 = new StringBuilder();
        if (wifiEntry.getConnectedState() == 2) {
            sb3.append("hasInternet:");
            sb3.append(wifiEntry.hasInternetAccess());
            sb3.append(", isDefaultNetwork:");
            sb3.append(wifiEntry.isDefaultNetwork());
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0046, code lost:
    
        if (((android.content.ComponentName) r3.second).getPackageName().equals(r5) != false) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0071 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isDeviceOrProfileOwner(android.content.Context r4, java.lang.String r5, int r6) {
        /*
            r0 = 0
            if (r5 != 0) goto L4
            goto L49
        L4:
            java.lang.Class<android.app.admin.DevicePolicyManager> r1 = android.app.admin.DevicePolicyManager.class
            java.lang.Object r1 = r4.getSystemService(r1)
            android.app.admin.DevicePolicyManager r1 = (android.app.admin.DevicePolicyManager) r1
            if (r1 != 0) goto L10
        Le:
            r3 = r0
            goto L29
        L10:
            android.os.UserHandle r2 = r1.getDeviceOwnerUser()     // Catch: java.lang.Exception -> L73
            android.content.ComponentName r1 = r1.getDeviceOwnerComponentOnAnyUser()     // Catch: java.lang.Exception -> L73
            if (r2 == 0) goto Le
            if (r1 != 0) goto L1d
            goto Le
        L1d:
            java.lang.String r3 = r1.getPackageName()
            if (r3 != 0) goto L24
            goto Le
        L24:
            android.util.Pair r3 = new android.util.Pair
            r3.<init>(r2, r1)
        L29:
            if (r3 != 0) goto L2c
            goto L49
        L2c:
            java.lang.Object r1 = r3.first
            android.os.UserHandle r1 = (android.os.UserHandle) r1
            android.os.UserHandle r2 = android.os.UserHandle.getUserHandleForUid(r6)
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L49
            java.lang.Object r1 = r3.second
            android.content.ComponentName r1 = (android.content.ComponentName) r1
            java.lang.String r1 = r1.getPackageName()
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L49
            goto L71
        L49:
            r1 = 0
            if (r5 != 0) goto L4e
        L4c:
            r4 = r1
            goto L6f
        L4e:
            java.lang.String r2 = r4.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.os.UserHandle r6 = android.os.UserHandle.getUserHandleForUid(r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.content.Context r4 = r4.createPackageContextAsUser(r2, r1, r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            goto L5c
        L5b:
            r4 = r0
        L5c:
            if (r4 != 0) goto L5f
            goto L68
        L5f:
            java.lang.Class<android.app.admin.DevicePolicyManager> r6 = android.app.admin.DevicePolicyManager.class
            java.lang.Object r4 = r4.getSystemService(r6)
            r0 = r4
            android.app.admin.DevicePolicyManager r0 = (android.app.admin.DevicePolicyManager) r0
        L68:
            if (r0 != 0) goto L6b
            goto L4c
        L6b:
            boolean r4 = r0.isProfileOwnerApp(r5)
        L6f:
            if (r4 == 0) goto L72
        L71:
            r1 = 1
        L72:
            return r1
        L73:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "getDeviceOwner error - "
            r6.<init>(r0)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.Utils.isDeviceOrProfileOwner(android.content.Context, java.lang.String, int):boolean");
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
