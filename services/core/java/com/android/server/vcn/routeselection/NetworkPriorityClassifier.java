package com.android.server.vcn.routeselection;

import android.net.NetworkCapabilities;
import android.net.TelephonyNetworkSpecifier;
import android.net.vcn.VcnCellUnderlyingNetworkTemplate;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.net.vcn.VcnWifiUnderlyingNetworkTemplate;
import android.os.ParcelUuid;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.server.VcnManagementService;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.util.PersistableBundleUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class NetworkPriorityClassifier {
    static final int PRIORITY_FALLBACK = Integer.MAX_VALUE;
    static final int PRIORITY_INVALID = -1;
    public static final String TAG = "NetworkPriorityClassifier";
    static final int WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT = -70;
    static final int WIFI_EXIT_RSSI_THRESHOLD_DEFAULT = -74;

    public static int calculatePriorityClass(VcnContext vcnContext, UnderlyingNetworkRecord underlyingNetworkRecord, List list, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, UnderlyingNetworkRecord underlyingNetworkRecord2, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        if (underlyingNetworkRecord.isBlocked) {
            logWtf("Network blocked for System Server: " + underlyingNetworkRecord.network);
            return -1;
        }
        if (telephonySubscriptionSnapshot == null) {
            logWtf("Got null snapshot");
            return -1;
        }
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (checkMatchesPriorityRule(vcnContext, (VcnUnderlyingNetworkTemplate) it.next(), underlyingNetworkRecord, parcelUuid, telephonySubscriptionSnapshot, underlyingNetworkRecord2, persistableBundleWrapper)) {
                return i;
            }
            i++;
        }
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (networkCapabilities.hasCapability(12)) {
            return Integer.MAX_VALUE;
        }
        return (vcnContext.isInTestMode() && networkCapabilities.hasTransport(7)) ? Integer.MAX_VALUE : -1;
    }

    public static boolean checkMatchesPriorityRule(VcnContext vcnContext, VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate, UnderlyingNetworkRecord underlyingNetworkRecord, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, UnderlyingNetworkRecord underlyingNetworkRecord2, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        boolean z = underlyingNetworkRecord2 != null && Objects.equals(underlyingNetworkRecord2.network, underlyingNetworkRecord.network);
        int metered = vcnUnderlyingNetworkTemplate.getMetered();
        boolean z2 = !networkCapabilities.hasCapability(11);
        if ((metered != 1 || z2) && ((metered != 2 || !z2) && networkCapabilities.getLinkUpstreamBandwidthKbps() >= vcnUnderlyingNetworkTemplate.getMinExitUpstreamBandwidthKbps() && ((networkCapabilities.getLinkUpstreamBandwidthKbps() >= vcnUnderlyingNetworkTemplate.getMinEntryUpstreamBandwidthKbps() || z) && networkCapabilities.getLinkDownstreamBandwidthKbps() >= vcnUnderlyingNetworkTemplate.getMinExitDownstreamBandwidthKbps() && (networkCapabilities.getLinkDownstreamBandwidthKbps() >= vcnUnderlyingNetworkTemplate.getMinEntryDownstreamBandwidthKbps() || z)))) {
            for (Map.Entry entry : vcnUnderlyingNetworkTemplate.getCapabilitiesMatchCriteria().entrySet()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                int intValue2 = ((Integer) entry.getValue()).intValue();
                if (intValue2 == 1 && !networkCapabilities.hasCapability(intValue)) {
                    return false;
                }
                if (intValue2 == 2 && networkCapabilities.hasCapability(intValue)) {
                    return false;
                }
            }
            if (vcnContext.isInTestMode() && networkCapabilities.hasTransport(7)) {
                return true;
            }
            if (vcnUnderlyingNetworkTemplate instanceof VcnWifiUnderlyingNetworkTemplate) {
                return checkMatchesWifiPriorityRule((VcnWifiUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, underlyingNetworkRecord2, persistableBundleWrapper);
            }
            if (vcnUnderlyingNetworkTemplate instanceof VcnCellUnderlyingNetworkTemplate) {
                return checkMatchesCellPriorityRule(vcnContext, (VcnCellUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, parcelUuid, telephonySubscriptionSnapshot);
            }
            logWtf("Got unknown VcnUnderlyingNetworkTemplate class: " + vcnUnderlyingNetworkTemplate.getClass().getSimpleName());
        }
        return false;
    }

    public static boolean checkMatchesWifiPriorityRule(VcnWifiUnderlyingNetworkTemplate vcnWifiUnderlyingNetworkTemplate, UnderlyingNetworkRecord underlyingNetworkRecord, UnderlyingNetworkRecord underlyingNetworkRecord2, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (networkCapabilities.hasTransport(1) && isWifiRssiAcceptable(underlyingNetworkRecord, underlyingNetworkRecord2, persistableBundleWrapper)) {
            return vcnWifiUnderlyingNetworkTemplate.getSsids().isEmpty() || vcnWifiUnderlyingNetworkTemplate.getSsids().contains(networkCapabilities.getSsid());
        }
        return false;
    }

    public static boolean isWifiRssiAcceptable(UnderlyingNetworkRecord underlyingNetworkRecord, UnderlyingNetworkRecord underlyingNetworkRecord2, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        return ((underlyingNetworkRecord2 != null && underlyingNetworkRecord.network.equals(underlyingNetworkRecord2.network)) && networkCapabilities.getSignalStrength() >= getWifiExitRssiThreshold(persistableBundleWrapper)) || networkCapabilities.getSignalStrength() >= getWifiEntryRssiThreshold(persistableBundleWrapper);
    }

    public static boolean checkMatchesCellPriorityRule(VcnContext vcnContext, VcnCellUnderlyingNetworkTemplate vcnCellUnderlyingNetworkTemplate, UnderlyingNetworkRecord underlyingNetworkRecord, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (!networkCapabilities.hasTransport(0)) {
            return false;
        }
        TelephonyNetworkSpecifier telephonyNetworkSpecifier = (TelephonyNetworkSpecifier) networkCapabilities.getNetworkSpecifier();
        if (telephonyNetworkSpecifier == null) {
            logWtf("Got null NetworkSpecifier");
            return false;
        }
        TelephonyManager createForSubscriptionId = ((TelephonyManager) vcnContext.getContext().getSystemService(TelephonyManager.class)).createForSubscriptionId(telephonyNetworkSpecifier.getSubscriptionId());
        if (!vcnCellUnderlyingNetworkTemplate.getOperatorPlmnIds().isEmpty()) {
            if (!vcnCellUnderlyingNetworkTemplate.getOperatorPlmnIds().contains(createForSubscriptionId.getNetworkOperator())) {
                return false;
            }
        }
        if (!vcnCellUnderlyingNetworkTemplate.getSimSpecificCarrierIds().isEmpty()) {
            if (!vcnCellUnderlyingNetworkTemplate.getSimSpecificCarrierIds().contains(Integer.valueOf(createForSubscriptionId.getSimSpecificCarrierId()))) {
                return false;
            }
        }
        int roaming = vcnCellUnderlyingNetworkTemplate.getRoaming();
        boolean z = !networkCapabilities.hasCapability(18);
        if ((roaming == 1 && !z) || (roaming == 2 && z)) {
            return false;
        }
        int opportunistic = vcnCellUnderlyingNetworkTemplate.getOpportunistic();
        boolean isOpportunistic = isOpportunistic(telephonySubscriptionSnapshot, networkCapabilities.getSubscriptionIds());
        if (opportunistic == 1) {
            if (!isOpportunistic) {
                return false;
            }
            if (telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid).contains(Integer.valueOf(SubscriptionManager.getActiveDataSubscriptionId())) && !networkCapabilities.getSubscriptionIds().contains(Integer.valueOf(SubscriptionManager.getActiveDataSubscriptionId()))) {
                return false;
            }
        } else if (opportunistic == 2 && !isOpportunistic) {
            return false;
        }
        return true;
    }

    public static boolean isOpportunistic(TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, Set set) {
        if (telephonySubscriptionSnapshot == null) {
            logWtf("Got null snapshot");
            return false;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (telephonySubscriptionSnapshot.isOpportunistic(((Integer) it.next()).intValue())) {
                return true;
            }
        }
        return false;
    }

    public static int getWifiEntryRssiThreshold(PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        return persistableBundleWrapper != null ? persistableBundleWrapper.getInt("vcn_network_selection_wifi_entry_rssi_threshold", WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT) : WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT;
    }

    public static int getWifiExitRssiThreshold(PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        return persistableBundleWrapper != null ? persistableBundleWrapper.getInt("vcn_network_selection_wifi_exit_rssi_threshold", WIFI_EXIT_RSSI_THRESHOLD_DEFAULT) : WIFI_EXIT_RSSI_THRESHOLD_DEFAULT;
    }

    public static void logWtf(String str) {
        String str2 = TAG;
        Slog.wtf(str2, str);
        VcnManagementService.LOCAL_LOG.log(str2 + " WTF: " + str);
    }
}
