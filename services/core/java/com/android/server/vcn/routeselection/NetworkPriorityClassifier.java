package com.android.server.vcn.routeselection;

import android.net.NetworkCapabilities;
import android.net.TelephonyNetworkSpecifier;
import android.net.vcn.VcnCellUnderlyingNetworkTemplate;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.net.vcn.VcnWifiUnderlyingNetworkTemplate;
import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.VcnManagementService;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.VcnContext;
import com.android.server.vcn.util.PersistableBundleUtils;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class NetworkPriorityClassifier {
    static final int PRIORITY_FALLBACK = Integer.MAX_VALUE;
    static final int WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT = -70;
    static final int WIFI_EXIT_RSSI_THRESHOLD_DEFAULT = -74;

    public static boolean checkMatchesCellPriorityRule(VcnContext vcnContext, VcnCellUnderlyingNetworkTemplate vcnCellUnderlyingNetworkTemplate, UnderlyingNetworkRecord underlyingNetworkRecord, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        boolean z;
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (!networkCapabilities.hasTransport(0)) {
            return false;
        }
        TelephonyNetworkSpecifier telephonyNetworkSpecifier = (TelephonyNetworkSpecifier) networkCapabilities.getNetworkSpecifier();
        if (telephonyNetworkSpecifier == null) {
            logWtf("Got null NetworkSpecifier");
            return false;
        }
        TelephonyManager createForSubscriptionId = ((TelephonyManager) vcnContext.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(telephonyNetworkSpecifier.getSubscriptionId());
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
        boolean z2 = !networkCapabilities.hasCapability(18);
        if ((roaming == 1 && !z2) || (roaming == 2 && z2)) {
            return false;
        }
        int opportunistic = vcnCellUnderlyingNetworkTemplate.getOpportunistic();
        Set<Integer> subscriptionIds = networkCapabilities.getSubscriptionIds();
        if (telephonySubscriptionSnapshot != null) {
            for (Integer num : subscriptionIds) {
                num.intValue();
                if (telephonySubscriptionSnapshot.mSubIdToInfoMap.containsKey(num) ? ((SubscriptionInfo) telephonySubscriptionSnapshot.mSubIdToInfoMap.get(num)).isOpportunistic() : false) {
                    z = true;
                    break;
                }
            }
        } else {
            logWtf("Got null snapshot");
        }
        z = false;
        if (opportunistic == 1) {
            if (!z) {
                return false;
            }
            if (((ArraySet) telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid)).contains(Integer.valueOf(SubscriptionManager.getActiveDataSubscriptionId())) && !networkCapabilities.getSubscriptionIds().contains(Integer.valueOf(SubscriptionManager.getActiveDataSubscriptionId()))) {
                return false;
            }
        } else if (opportunistic == 2 && !z) {
            return false;
        }
        return true;
    }

    public static boolean checkMatchesPriorityRule(VcnContext vcnContext, VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate, UnderlyingNetworkRecord underlyingNetworkRecord, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, boolean z, PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
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
            if (vcnContext.mIsInTestMode && networkCapabilities.hasTransport(7)) {
                return true;
            }
            if (vcnUnderlyingNetworkTemplate instanceof VcnWifiUnderlyingNetworkTemplate) {
                return checkMatchesWifiPriorityRule((VcnWifiUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, z, persistableBundleWrapper);
            }
            if (vcnUnderlyingNetworkTemplate instanceof VcnCellUnderlyingNetworkTemplate) {
                return checkMatchesCellPriorityRule(vcnContext, (VcnCellUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, parcelUuid, telephonySubscriptionSnapshot);
            }
            logWtf("Got unknown VcnUnderlyingNetworkTemplate class: ".concat(vcnUnderlyingNetworkTemplate.getClass().getSimpleName()));
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0020, code lost:
    
        if (r8 >= r2) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean checkMatchesWifiPriorityRule(android.net.vcn.VcnWifiUnderlyingNetworkTemplate r6, com.android.server.vcn.routeselection.UnderlyingNetworkRecord r7, boolean r8, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper r9) {
        /*
            android.net.NetworkCapabilities r0 = r7.networkCapabilities
            r1 = 1
            boolean r2 = r0.hasTransport(r1)
            r3 = 0
            if (r2 != 0) goto Lb
            return r3
        Lb:
            android.net.NetworkCapabilities r7 = r7.networkCapabilities
            if (r8 == 0) goto L23
            int r8 = r7.getSignalStrength()
            r2 = -74
            if (r9 == 0) goto L20
            android.os.PersistableBundle r4 = r9.mBundle
            java.lang.String r5 = "vcn_network_selection_wifi_exit_rssi_threshold"
            int r2 = r4.getInt(r5, r2)
        L20:
            if (r8 < r2) goto L23
            goto L36
        L23:
            int r7 = r7.getSignalStrength()
            r8 = -70
            if (r9 == 0) goto L34
            android.os.PersistableBundle r9 = r9.mBundle
            java.lang.String r2 = "vcn_network_selection_wifi_entry_rssi_threshold"
            int r8 = r9.getInt(r2, r8)
        L34:
            if (r7 < r8) goto L50
        L36:
            java.util.Set r7 = r6.getSsids()
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L4f
            java.util.Set r6 = r6.getSsids()
            java.lang.String r7 = r0.getSsid()
            boolean r6 = r6.contains(r7)
            if (r6 != 0) goto L4f
            return r3
        L4f:
            return r1
        L50:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vcn.routeselection.NetworkPriorityClassifier.checkMatchesWifiPriorityRule(android.net.vcn.VcnWifiUnderlyingNetworkTemplate, com.android.server.vcn.routeselection.UnderlyingNetworkRecord, boolean, com.android.server.vcn.util.PersistableBundleUtils$PersistableBundleWrapper):boolean");
    }

    public static void logWtf(String str) {
        Slog.wtf("NetworkPriorityClassifier", str);
        VcnManagementService.LOCAL_LOG.log("NetworkPriorityClassifier WTF: " + str);
    }
}
