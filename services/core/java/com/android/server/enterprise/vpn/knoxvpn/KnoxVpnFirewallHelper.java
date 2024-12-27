package com.android.server.enterprise.vpn.knoxvpn;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.net.IVpnManager;
import android.net.UidRangeParcel;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;
import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.utils.NetdHelper;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnPackageInfo;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileConfig;
import com.android.server.enterprise.vpn.knoxvpn.profile.VpnProfileInfo;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class KnoxVpnFirewallHelper {
    public static final Map filterChains;
    public static INetd mNetdService;
    public static IOemNetd mOemNetdService;
    public static final Map mangleChains;
    public static final Map natChains;
    public INetworkManagementService mNetworkManagementService = null;
    public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String TAG = "FW-KnoxVpnFirewallHelper";
    public static final String TETHER_TAG = "KnoxVpnTetherAuthentication";
    public static KnoxVpnFirewallHelper mInstance = null;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class IpRestoreActionType {
        public static final /* synthetic */ IpRestoreActionType[] $VALUES;
        public static final IpRestoreActionType APPEND;
        public static final IpRestoreActionType DELETE;
        public static final IpRestoreActionType INSERT;
        public static final IpRestoreActionType REMOVE_CHAIN;

        static {
            IpRestoreActionType ipRestoreActionType = new IpRestoreActionType("APPEND", 0);
            APPEND = ipRestoreActionType;
            IpRestoreActionType ipRestoreActionType2 = new IpRestoreActionType("INSERT", 1);
            INSERT = ipRestoreActionType2;
            IpRestoreActionType ipRestoreActionType3 = new IpRestoreActionType("DELETE", 2);
            DELETE = ipRestoreActionType3;
            IpRestoreActionType ipRestoreActionType4 = new IpRestoreActionType("REMOVE_CHAIN", 3);
            REMOVE_CHAIN = ipRestoreActionType4;
            $VALUES = new IpRestoreActionType[]{ipRestoreActionType, ipRestoreActionType2, ipRestoreActionType3, ipRestoreActionType4};
        }

        public static IpRestoreActionType valueOf(String str) {
            return (IpRestoreActionType) Enum.valueOf(IpRestoreActionType.class, str);
        }

        public static IpRestoreActionType[] values() {
            return (IpRestoreActionType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IpRestoreParam {
        public final String actionChain;
        public IpRestoreActionType actionType;
        public final String firstParam;
        public final String jumpChain;
        public final String secondParam;

        public IpRestoreParam(String str, String str2, String str3, String str4, IpRestoreActionType ipRestoreActionType) {
            this.actionChain = str;
            this.firstParam = str2;
            this.jumpChain = str3;
            this.secondParam = str4;
            this.actionType = ipRestoreActionType;
        }
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("OUTPUT", Arrays.asList("knox_vpn_no_uid", "knox_vpn_OUTPUT", "knox_vpn_proxy_accept", "knox_vpn_mangle_exempt_dl", "knox_vpn_mangle_exempt_cp", "knox_vpn_EXEMPT", "knox_vpn_tether_exempt"));
        mangleChains = hashMap;
        HashMap hashMap2 = new HashMap();
        hashMap2.put("INPUT", Arrays.asList("knox_vpn_filter_input_drop", "knox_vpn_tether_exempt"));
        hashMap2.put("OUTPUT", Arrays.asList("knox_vpn_filter_output_drop", "knox_vpn_filter_output_act"));
        hashMap2.put("FORWARD", Arrays.asList("knox_vpn_filter_tether_fwd"));
        filterChains = hashMap2;
        HashMap hashMap3 = new HashMap();
        hashMap3.put("PREROUTING", Arrays.asList("knox_vpn_nat_preroute"));
        natChains = hashMap3;
    }

    public KnoxVpnFirewallHelper() {
        createChainsInTable("*mangle", mangleChains);
        createChainsInTable("*filter", filterChains);
        createChainsInTable("*nat", natChains);
    }

    public static void applyBlockingRulesForDns(int i, int i2, int i3) {
        String str = TAG;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArraySet arraySet = new ArraySet();
                arraySet.add(new UidRangeParcel(i, i2));
                Log.d(str, "Action to be performed on the dns packet is " + i3 + " for the start uid " + i + " and for the stop uid " + i2);
                if (i3 == 1) {
                    getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                    getOemNetdService().knoxVpnBlockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i3 == 2) {
                    getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i3 == 3) {
                    getOemNetdService().knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                    getOemNetdService().knoxVpnExemptDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i3 == 4) {
                    getOemNetdService().knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i3 == 5) {
                    getOemNetdService().knoxVpnDestroyBlockedKnoxNetwork();
                }
            } catch (Exception unused) {
                Log.e(str, "error occured while trying to update rules for dns packets ");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void applyBlockingRulesForDns(int i, List list) {
        String str = TAG;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ArraySet arraySet = new ArraySet();
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    arraySet.add(new UidRangeParcel(intValue, intValue));
                    Log.d(str, "Action to be performed on the dns packet is " + i + " for the start uid " + intValue + " and for the stop uid " + intValue);
                }
                if (i == 1) {
                    getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                    getOemNetdService().knoxVpnBlockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i == 2) {
                    getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i == 3) {
                    getOemNetdService().knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                    getOemNetdService().knoxVpnExemptDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i == 4) {
                    getOemNetdService().knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
                } else if (i == 5) {
                    getOemNetdService().knoxVpnDestroyBlockedKnoxNetwork();
                }
            } catch (Exception unused) {
                Log.e(str, "error occured while trying to update rules for dns packets ");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean checknterface(String str) {
        if (DBG) {
            Log.e(TAG, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("checknterface() : interfaceName : ", str));
        }
        if (str == null || "block_traffic".equals(str)) {
            return true;
        }
        return str.matches("[a-zA-Z0-9_]+");
    }

    public static List createTableHeaderCmd(String str, List list) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append(str);
        sb.append("\n");
        arrayList.add(sb.toString());
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2 != null) {
                    sb.setLength(0);
                    sb.append(":");
                    sb.append(str2);
                    sb.append(" -");
                    sb.append("\n");
                    arrayList.add(sb.toString());
                }
            }
        }
        return arrayList;
    }

    public static void deleteIpRuleForuidSourceSelection(int i, List list, String str, StringBuilder sb) {
        String str2 = i == 6 ? "ip -6 rule" : "ip rule";
        if (str.contains("block_traffic")) {
            return;
        }
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String num = Integer.toString(((Integer) it.next()).intValue());
            sb.append(str2);
            sb.append(" del ");
            sb.append("from all uidrange ");
            sb.append(num + PackageManagerShellCommandDataLoader.STDIN_PATH + num);
            sb.append(" prio 52 ");
            sb.append(";");
        }
    }

    public static List getDefaultRouteAppUidList() {
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add(1000);
        arrayList.add(1073);
        arrayList.add(1001);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo("com.samsung.android.messaging", 128L, 0);
                i = applicationInfo != null ? applicationInfo.uid : -1;
            } catch (Exception e) {
                Log.d(TAG, "Exception in getUIDForPackage : " + Log.getStackTraceString(e));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = -1;
            }
            if (i != -1) {
                arrayList.add(Integer.valueOf(i));
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static synchronized KnoxVpnFirewallHelper getInstance() {
        KnoxVpnFirewallHelper knoxVpnFirewallHelper;
        synchronized (KnoxVpnFirewallHelper.class) {
            try {
                if (mInstance == null) {
                    mInstance = new KnoxVpnFirewallHelper();
                }
                knoxVpnFirewallHelper = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxVpnFirewallHelper;
    }

    public static IOemNetd getOemNetdService() {
        boolean z;
        INetd asInterface;
        IOemNetd iOemNetd = mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        INetd iNetd = mNetdService;
        String str = TAG;
        if (iNetd == null) {
            try {
                asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
                mNetdService = asInterface;
            } catch (RemoteException unused) {
                mNetdService = null;
                z = false;
            }
            if (asInterface != null) {
                z = asInterface.isAlive();
                if (!z && DBG) {
                    Log.e(str, "Can't connect to NativeNetdService netd");
                }
            }
        }
        INetd iNetd2 = mNetdService;
        if (iNetd2 != null) {
            try {
                mOemNetdService = IOemNetd.Stub.asInterface(iNetd2.getOemNetd());
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to get OemNetd listener "), str);
            }
        }
        return mOemNetdService;
    }

    public static native int jnigetIfaceIndexWitoutOffset(String str);

    public static native int jnigetInterfaceIndex(String str);

    public static String parseIptablesRestoreCmd(IpRestoreParam ipRestoreParam) {
        StringBuilder sb = new StringBuilder();
        IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
        if (ipRestoreActionType == null) {
            return "";
        }
        int ordinal = ipRestoreActionType.ordinal();
        String str = ipRestoreParam.secondParam;
        String str2 = ipRestoreParam.jumpChain;
        String str3 = ipRestoreParam.firstParam;
        String str4 = ipRestoreParam.actionChain;
        if (ordinal == 0) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-A ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal == 1) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-I ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal == 2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-D ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal != 3) {
            sb = null;
        } else {
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, "-X ", str4, "\n");
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }

    public static UidRangeParcel[] toUidRangeStableParcels(Set set) {
        ArraySet arraySet = (ArraySet) set;
        UidRangeParcel[] uidRangeParcelArr = new UidRangeParcel[arraySet.size()];
        Iterator it = arraySet.iterator();
        int i = 0;
        while (it.hasNext()) {
            UidRangeParcel uidRangeParcel = (UidRangeParcel) it.next();
            uidRangeParcelArr[i] = new UidRangeParcel(uidRangeParcel.start, uidRangeParcel.stop);
            i++;
        }
        return uidRangeParcelArr;
    }

    public final void addExemptRulesForDownloadManagerUid(int i, String str) {
        if (str == null || i == -1) {
            return;
        }
        insertExemptRulesForDownloadManagerUid(4, i, str);
        insertExemptRulesForDownloadManagerUid(6, i, str);
    }

    public final void addExemptRulesForUid(int i) {
        applyBlockingRulesForDns(i, i, 3);
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_EXEMPT", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner "), "ACCEPT", "", IpRestoreActionType.INSERT), 46);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i, 1);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void addInputFilterDropRulesForInterface(String str, String str2, int i, List list) {
        int i2;
        if (str.equalsIgnoreCase("com.samsung.sVpn")) {
            return;
        }
        if (str2 != null) {
            i2 = jnigetIfaceIndexWitoutOffset("v4-".concat(str2));
            if (i2 <= 0) {
                i2 = jnigetIfaceIndexWitoutOffset(str2);
            }
        } else {
            i2 = -1;
        }
        if (i2 <= 0) {
            i2 = 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
            if (i == 1) {
                getNetworkManagementService().updateInputFilterUserWideRules(array, i2, 1);
            } else if (i == 0) {
                getNetworkManagementService().updateInputFilterAppWideRules(array, i2, 1);
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void addIpRouteAndPolicyRules(int i, String str) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        if (i == 1) {
            insertIpRules(4, " add ", str);
            insertIpRoute(4, " add ", str);
            return;
        }
        if (i == 2) {
            insertIpRules(6, " add ", str);
            insertIpRoute(6, " add ", str);
        } else {
            if (i != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved ", str2);
                return;
            }
            insertIpRules(4, " add ", str);
            insertIpRoute(4, " add ", str);
            insertIpRules(6, " add ", str);
            insertIpRoute(6, " add ", str);
        }
    }

    public final void addIpRulesForExemptedUid(int i, int i2, String str) {
        if (str == null) {
            return;
        }
        if (i2 == 1) {
            deleteIpRulesForExemptedUid(4, i);
            insertIpRulesForExemptedUid(4, i, str);
            return;
        }
        if (i2 == 2) {
            deleteIpRulesForExemptedUid(6, i);
            insertIpRulesForExemptedUid(6, i, str);
        } else if (i2 != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "unknown interface type has been recieved for the method addIpRulesForExemptedUid"));
        } else {
            deleteIpRulesForExemptedUid(4, i);
            insertIpRulesForExemptedUid(4, i, str);
            deleteIpRulesForExemptedUid(6, i);
            insertIpRulesForExemptedUid(6, i, str);
        }
    }

    public final void addMarkingRulesForFilteredPackages(int i, String str, String str2) {
        if (str == null) {
            str = "block_traffic";
        }
        if (i == 1) {
            insertMarkingRulesForFilteredPackages(4, str, str2);
            return;
        }
        if (i == 2) {
            insertMarkingRulesForFilteredPackages(6, str, str2);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "addMarkingRulesForFilteredPackages: unknown interface type has been recieved "));
        } else {
            insertMarkingRulesForFilteredPackages(4, str, str2);
            insertMarkingRulesForFilteredPackages(6, str, str2);
        }
    }

    public final void addMiscRules(int i, String str, List list) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        if (str.equalsIgnoreCase("block_traffic")) {
            applyBlockingRulesForDns(1, list);
        } else {
            applyBlockingRulesForDns(2, list);
        }
        StringBuilder sb = new StringBuilder();
        if (i == 1) {
            insertIpRuleForUidList(4, list, str, sb);
            runSingleCommand(sb.toString());
        } else if (i == 2) {
            insertIpRuleForUidList(6, list, str, sb);
            runSingleCommand(sb.toString());
        } else {
            if (i != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved ", str2);
                return;
            }
            insertIpRuleForUidList(4, list, str, sb);
            insertIpRuleForUidList(6, list, str, sb);
            runSingleCommand(sb.toString());
        }
    }

    public final void addMiscRulesRange(int i, int i2, String str) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        int i3 = i * 100000;
        int i4 = i3 + 1;
        int i5 = 99999 + i3;
        if (str.equalsIgnoreCase("block_traffic")) {
            applyBlockingRulesForDns(i4, i5, 1);
        } else {
            applyBlockingRulesForDns(i4, i5, 2);
        }
        List defaultRouteApps = getDefaultRouteApps(i);
        HashSet hashSet = new HashSet();
        Range range = new Range(Integer.valueOf(i3), Integer.valueOf(((i + 1) * 100000) - 1));
        int intValue = ((Integer) range.getLower()).intValue() + 1;
        Iterator it = ((ArrayList) defaultRouteApps).iterator();
        while (it.hasNext()) {
            int intValue2 = ((Integer) it.next()).intValue();
            if (intValue2 == intValue) {
                intValue++;
            } else {
                hashSet.add(new UidRangeParcel(intValue, intValue2 - 1));
                intValue = intValue2 + 1;
            }
        }
        if (intValue <= ((Integer) range.getUpper()).intValue()) {
            hashSet.add(new UidRangeParcel(intValue, ((Integer) range.getUpper()).intValue()));
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            UidRangeParcel uidRangeParcel = (UidRangeParcel) it2.next();
            StringBuilder sb = new StringBuilder("addMiscRulesRange ");
            sb.append(uidRangeParcel.start);
            AudioService$$ExternalSyntheticOutline0.m(sb, uidRangeParcel.stop, str, str2);
            int i6 = uidRangeParcel.start;
            int i7 = uidRangeParcel.stop;
            if (i2 == 1) {
                deleteIpRuleForuidRangeSourceSelection(4, i6, i7);
                insertIpRuleForuidRangeSourceSelection(4, i6, i7, str);
            } else if (i2 == 2) {
                deleteIpRuleForuidRangeSourceSelection(6, i6, i7);
                insertIpRuleForuidRangeSourceSelection(6, i6, i7, str);
            } else if (i2 != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i2, "unknown interface type has been recieved ", str2);
            } else {
                deleteIpRuleForuidRangeSourceSelection(4, i6, i7);
                insertIpRuleForuidRangeSourceSelection(4, i6, i7, str);
                deleteIpRuleForuidRangeSourceSelection(6, i6, i7);
                insertIpRuleForuidRangeSourceSelection(6, i6, i7, str);
            }
        }
    }

    public final void addRangeRulesForFilteredPackages(int i, String str, String str2, String str3) {
        int i2;
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_uidlist");
        String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        int i3 = 100000 * i;
        insertRule(false, "*mangle", null, new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m owner --uid-owner ", (i3 + 1) + PackageManagerShellCommandDataLoader.STDIN_PATH + (i3 + 99999)), m$12, "", IpRestoreActionType.APPEND), 46);
        if (str.contains("com.samsung.sVpn")) {
            return;
        }
        if (str3 != null) {
            i2 = jnigetIfaceIndexWitoutOffset("v4-".concat(str3));
            if (i2 <= 0) {
                i2 = jnigetIfaceIndexWitoutOffset(str3);
            }
        } else {
            i2 = -1;
        }
        if (i2 <= 0) {
            i2 = 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            getNetworkManagementService().updateInputFilterUserWideRules(arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray(), i2, 1);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void addRulesForDroppingTetherPackets(String str) {
        insertRulesForDroppingTetherPackets(4, str);
        insertRulesForDroppingTetherPackets(6, str);
    }

    public final void addRulesForFilteredPackages(String str, String str2, List list, String str3) {
        int i;
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_uidlist");
        String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            arrayList.add(new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m owner --uid-owner ", Integer.toString(((Integer) it.next()).intValue())), m$12, "", IpRestoreActionType.INSERT));
        }
        if (!arrayList.isEmpty()) {
            insertRules(46, "*mangle", null, arrayList, false);
        }
        if (str.contains("com.samsung.sVpn")) {
            return;
        }
        if (str3 != null) {
            i = jnigetIfaceIndexWitoutOffset("v4-".concat(str3));
            if (i <= 0) {
                i = jnigetIfaceIndexWitoutOffset(str3);
            }
        } else {
            i = -1;
        }
        if (i <= 0) {
            i = 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterAppWideRules(list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray(), i, 1);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void addRulesForNoUidPackets(int i, String str, String str2) {
        if (str2 == null) {
            return;
        }
        if (i == 1) {
            insertRulesForNoUidPackets(4, str2, str);
            return;
        }
        if (i == 2) {
            insertRulesForNoUidPackets(6, str2, str);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "addRulesForNoUidPackets: unknown interface type has been recieved "));
        } else {
            insertRulesForNoUidPackets(4, str2, str);
            insertRulesForNoUidPackets(6, str2, str);
        }
    }

    public final void addRulesForUsbTethering(int i, String str, String str2, List list, String[] strArr) {
        if (i == 1) {
            insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
            insertRulesForUsbTethering(4, str, str2, list, strArr);
            insertRulesForDroppingTetherPackets(6, str2);
        } else if (i == 2) {
            insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
            insertRulesForUsbTethering(6, str, str2, list, strArr);
            insertRulesForDroppingTetherPackets(4, str2);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "addRulesForUsbTethering: unknown interface type has been recieved "));
        } else {
            insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
            insertRulesForUsbTethering(4, str, str2, list, strArr);
            insertRulesForUsbTethering(6, str, str2, list, strArr);
        }
    }

    public final void addRulesInOutputChain(String str) {
        if (str == null) {
            return;
        }
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_OUTPUT", "", str.concat("_uidlist"), "", IpRestoreActionType.APPEND), 46);
    }

    public final void addRulesTetherAuth(int i, String str, List list, Bundle bundle) {
        String str2;
        int i2 = bundle.getInt("com.samsung.android.knox.intent.extra.DNS_REDIRECTION_PORT", -1);
        int i3 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTP_REDIRECTION_PORT", -1);
        int i4 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTPS_REDIRECTION_PORT", -1);
        int i5 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTPS_REDIRECTION_AUTH_PORT", -1);
        String str3 = TETHER_TAG;
        if (i2 <= 0 || i3 <= 0 || i4 <= 0 || i5 <= 0) {
            Log.e(str3, "The ports received for usb tether mutual authentication is not valid");
            return;
        }
        Iterator it = ((ArrayList) list).iterator();
        while (true) {
            if (!it.hasNext()) {
                str2 = null;
                break;
            }
            InetAddress inetAddress = (InetAddress) it.next();
            if (inetAddress instanceof Inet4Address) {
                str2 = inetAddress.getHostAddress();
                break;
            }
        }
        if (str2 == null) {
            Log.e(str3, "The usb interface ipV4 address received for usb tether mutual authentication is not valid");
            return;
        }
        if (i <= 0) {
            Log.e(str3, "applying firewall rules for tether auth uid failed");
        }
        ArrayList arrayList = new ArrayList();
        String m = XmlUtils$$ExternalSyntheticOutline0.m(" -i ", str, " -p udp --dport 53");
        String m2 = SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, "DNAT --to ", str2, ":");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam("knox_vpn_nat_preroute", m, m2, "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_nat_preroute", XmlUtils$$ExternalSyntheticOutline0.m(" -i ", str, " -p tcp --dport 80"), SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i3, "DNAT --to ", str2, ":"), "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_nat_preroute", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i5, " -i ", str, " -p tcp --dport "), "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_nat_preroute", XmlUtils$$ExternalSyntheticOutline0.m(" -i ", str, " -p tcp --dport 443"), SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i4, "DNAT --to ", str2, ":"), "", ipRestoreActionType));
        insertRules(4, "*nat", Collections.singletonList("knox_vpn_nat_preroute"), arrayList, true);
        ArrayList arrayList2 = new ArrayList();
        String concat = " -i ".concat(str);
        IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.INSERT;
        arrayList2.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", concat, "DROP", "", ipRestoreActionType2));
        arrayList2.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", " -o ".concat(str), "DROP", "", ipRestoreActionType2));
        insertRules(46, "*filter", Collections.singletonList("knox_vpn_filter_tether_fwd"), arrayList2, true);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new IpRestoreParam("knox_vpn_tether_exempt", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, " -i ", str, " -p udp --dport "), "ACCEPT", "", ipRestoreActionType));
        arrayList3.add(new IpRestoreParam("knox_vpn_tether_exempt", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i3, " -i ", str, " -p tcp --dport "), "ACCEPT", "", ipRestoreActionType));
        arrayList3.add(new IpRestoreParam("knox_vpn_tether_exempt", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i5, " -i ", str, " -p tcp --dport "), "ACCEPT", "", ipRestoreActionType));
        arrayList3.add(new IpRestoreParam("knox_vpn_tether_exempt", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i4, " -i ", str, " -p tcp --dport "), "ACCEPT", "", ipRestoreActionType));
        insertRules(4, "*filter", Collections.singletonList("knox_vpn_tether_exempt"), arrayList3, true);
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_tether_exempt", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner "), "ACCEPT", "", ipRestoreActionType2), 4);
        StringBuilder sb = new StringBuilder("ip rule del from all uidrange ");
        sb.append(i + PackageManagerShellCommandDataLoader.STDIN_PATH + i);
        sb.append(" lookup 97 prio 42;ip rule add from all uidrange ");
        sb.append(i + PackageManagerShellCommandDataLoader.STDIN_PATH + i);
        sb.append(" lookup 97 prio 42;");
        runSingleCommand(sb.toString());
    }

    public final void addRulesToAcceptIncomingPackets(int i, String str) {
        if (str == null || str.contains("block_traffic")) {
            return;
        }
        if (i == 1) {
            deleteRulesToAcceptIncomingPackets(4, str);
            insertRulesToAcceptIncomingPackets(4, str);
            return;
        }
        if (i == 2) {
            deleteRulesToAcceptIncomingPackets(6, str);
            insertRulesToAcceptIncomingPackets(6, str);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved "));
        } else {
            deleteRulesToAcceptIncomingPackets(4, str);
            insertRulesToAcceptIncomingPackets(4, str);
            deleteRulesToAcceptIncomingPackets(6, str);
            insertRulesToAcceptIncomingPackets(6, str);
        }
    }

    public final void addRulesToAllowAccessToLocalHostWithValidMark(int i, int i2, String str) {
        if (!checknterface(str)) {
            Log.d(TAG, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("not allowed name  : ", str));
            return;
        }
        if (str == null) {
            return;
        }
        if (i2 == 1) {
            insertRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            insertRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
            insertRulesToAllowAccessToLocalHostWithValidMark(6, i, "block_traffic");
        } else if (i2 == 2) {
            insertRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
            insertRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            insertRulesToAllowAccessToLocalHostWithValidMark(4, i, "block_traffic");
        } else {
            if (i2 != 3) {
                return;
            }
            insertRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            insertRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
        }
    }

    public final void addRulesToDenyAccessToLocalHost(int i) {
        ArrayList arrayList = new ArrayList();
        String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -o lo -p tcp --dport ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam("knox_vpn_filter_output_drop", m, "DROP", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_filter_output_drop", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -o lo -p udp --dport "), "DROP", "", ipRestoreActionType));
        insertRules(46, "*filter", null, arrayList, true);
    }

    public final void createChainsInTable(String str, Map map) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = (HashMap) map;
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
        for (Map.Entry entry : hashMap.entrySet()) {
            Iterator it2 = ((List) entry.getValue()).iterator();
            while (it2.hasNext()) {
                ((ArrayList) createTableHeaderCmd).add(parseIptablesRestoreCmd(new IpRestoreParam((String) entry.getKey(), "", (String) it2.next(), "", IpRestoreActionType.DELETE)));
            }
        }
        Iterator it3 = hashMap.entrySet().iterator();
        while (it3.hasNext()) {
            Iterator it4 = ((List) ((Map.Entry) it3.next()).getValue()).iterator();
            while (it4.hasNext()) {
                ((ArrayList) createTableHeaderCmd).add(parseIptablesRestoreCmd(new IpRestoreParam((String) it4.next(), null, null, null, IpRestoreActionType.REMOVE_CHAIN)));
            }
        }
        ((ArrayList) createTableHeaderCmd).add("COMMIT\n");
        runIptablesRestoreCmd(46, createTableHeaderCmd);
        ArrayList arrayList2 = new ArrayList();
        Iterator it5 = hashMap.entrySet().iterator();
        while (it5.hasNext()) {
            arrayList2.addAll((Collection) ((Map.Entry) it5.next()).getValue());
        }
        List createTableHeaderCmd2 = createTableHeaderCmd(str, arrayList2);
        for (Map.Entry entry2 : hashMap.entrySet()) {
            Iterator it6 = ((List) entry2.getValue()).iterator();
            while (it6.hasNext()) {
                ((ArrayList) createTableHeaderCmd2).add(parseIptablesRestoreCmd(new IpRestoreParam((String) entry2.getKey(), "", (String) it6.next(), "", IpRestoreActionType.INSERT)));
            }
        }
        ((ArrayList) createTableHeaderCmd2).add("COMMIT\n");
        runIptablesRestoreCmd(46, createTableHeaderCmd2);
    }

    public final void deleteExemptRulesForDownloadManagerUid(int i, int i2) {
        String str = i == 6 ? "ip -6 rule" : "ip rule";
        StringBuilder sb = new StringBuilder();
        insertRule(false, "*mangle", "knox_vpn_mangle_exempt_dl", null, i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i2, 0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        sb.append(str);
        sb.append(" del from all uidrange ");
        sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        sb.append(" prio 47;");
        runSingleCommand(sb.toString());
    }

    public final void deleteIpRuleForuidRangeSourceSelection(int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(i == 6 ? "ip -6 rule" : "ip rule");
        sb.append(" del from all uidrange ");
        sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3);
        sb.append(" prio 53 ;");
        runSingleCommand(sb.toString());
    }

    public final void deleteIpRulesForExemptedUid(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i == 6 ? "ip -6 rule" : "ip rule");
        sb.append(" del from all uidrange ");
        sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        sb.append(" prio 51 ;");
        runSingleCommand(sb.toString());
    }

    public final void deleteNatRules(int i, String str) {
        if ("block_traffic".equals(str)) {
            return;
        }
        insertRule(false, "*nat", null, new IpRestoreParam("POSTROUTING", " -o ".concat(str), "MASQUERADE", "", IpRestoreActionType.DELETE), i);
    }

    public final void deleteRulesForNoUidPackets(int i, String str, String str2) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        String forwardMark = "block_traffic".equals(str) ? "60" : getForwardMark(str);
        boolean equalsIgnoreCase = str.equalsIgnoreCase("block_traffic");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        if (!equalsIgnoreCase) {
            insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), m$1, "", ipRestoreActionType), i);
            insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m mark --mark ", forwardMark), m$1, "", ipRestoreActionType), i);
        } else {
            insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("knox-netId-", forwardMark), "", ipRestoreActionType), i);
            insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m mark --mark ", forwardMark), ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("knox-netId-", forwardMark), "", ipRestoreActionType), i);
            insertRule(false, "*mangle", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("knox-netId-", forwardMark), new IpRestoreParam(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("knox-netId-", forwardMark), null, null, null, IpRestoreActionType.REMOVE_CHAIN), i);
        }
    }

    public final void deleteRulesForUsbTethering(int i, String str) {
        String str2 = i == 6 ? "ip -6 rule" : "ip rule";
        String forwardMark = str != null ? "block_traffic".equals(str) ? "60" : getForwardMark(str) : null;
        insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
        StringBuilder sb = new StringBuilder();
        if (forwardMark != null && forwardMark != "60") {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str2, " del from all lookup ", forwardMark, " prio 43;");
        }
        runSingleCommand(sb.toString());
    }

    public final void deleteRulesToAcceptIncomingPackets(int i, String str) {
        if (str == null || str.contains("block_traffic")) {
            return;
        }
        String forwardMark = getForwardMark(str);
        StringBuilder sb = new StringBuilder();
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, i == 6 ? "ip -6 rule" : "ip rule", " del from all iif ", str, " lookup ");
        sb.append(forwardMark);
        sb.append(" prio 45;");
        runSingleCommand(sb.toString());
    }

    public final void deleteRulesToAcceptProxyPackets(int i, int i2, String str) {
        insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_proxy_accept", " -m owner --uid-owner " + Integer.toString(i2) + " -m mark --mark " + getForwardMark(str), "ACCEPT", "", IpRestoreActionType.DELETE), i);
    }

    public final void deleteRulesToAllowAccessToLocalHostWithValidMark(int i, int i2, String str) {
        String forwardMark = "block_traffic".equals(str) ? "60" : getForwardMark(str);
        String m = SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, " -m mark --mark ", forwardMark, " -o lo -p tcp --dport ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_act", m, "ACCEPT", "", ipRestoreActionType), i);
        insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_act", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, " -m mark --mark ", forwardMark, " -o lo -p udp --dport "), "ACCEPT", "", ipRestoreActionType), i);
    }

    public final void deleteRulesToExemptCaptivePortalQueries(int i, int i2) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(i == 6 ? "ip -6 rule" : "ip rule", " del from all uidrange ");
        m.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        m.append(" prio 46;");
        insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_mangle_exempt_cp", VibrationParam$1$$ExternalSyntheticOutline0.m(i2, " -m owner --uid-owner "), "ACCEPT", "", IpRestoreActionType.DELETE), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i2, 0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        runSingleCommand(m.toString());
    }

    public final List getDefaultRouteApps(int i) {
        int nonChainedVendoUid;
        int nonChainedVendoUid2;
        ArrayList arrayList = new ArrayList();
        try {
            VpnProfileInfo profileEntry = this.vpnConfig.getProfileEntry(getProfileOwningUserId(i));
            if (profileEntry != null) {
                if (i == 0) {
                    List defaultRouteAppUidList = getDefaultRouteAppUidList();
                    Iterator it = profileEntry.mExemptPackageList.iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        int intValue = num.intValue();
                        if (((ArrayList) defaultRouteAppUidList).contains(num)) {
                            Log.d(TAG, "getDefaultRouteApps exempted uid " + intValue);
                            arrayList.add(num);
                        }
                    }
                    int i2 = profileEntry.mVendorUid;
                    if (profileEntry.mVendorPkgName.contains("com.samsung.sVpn")) {
                        i2 = 1016;
                    }
                    String str = TAG;
                    Log.d(str, "getDefaultRouteApps: vpn uid " + i2);
                    arrayList.add(Integer.valueOf(i2));
                    if (profileEntry.chainingEnabled == 1 && (nonChainedVendoUid2 = getNonChainedVendoUid(profileEntry.mVendorUid)) > 0 && UserHandle.getUserId(nonChainedVendoUid2) == i) {
                        Log.d(str, "getDefaultRouteApps: nonChainedVendorUid  " + nonChainedVendoUid2);
                        arrayList.add(Integer.valueOf(nonChainedVendoUid2));
                    }
                    Integer[] numArr = KnoxVpnConstants.AID_EXEMPT_LIST;
                    for (int i3 = 0; i3 < 3; i3++) {
                        Integer num2 = numArr[i3];
                        num2.getClass();
                        arrayList.add(num2);
                    }
                } else {
                    if (profileEntry.mVendorPkgName.contains("com.samsung.sVpn")) {
                        return arrayList;
                    }
                    int i4 = profileEntry.mVendorUid;
                    if (UserHandle.getUserId(i4) == i) {
                        Log.d(TAG, "getDefaultRouteApps: vendor uid " + i4);
                        arrayList.add(Integer.valueOf(i4));
                    }
                    if (profileEntry.chainingEnabled == 1 && (nonChainedVendoUid = getNonChainedVendoUid(profileEntry.mVendorUid)) > 0 && UserHandle.getUserId(nonChainedVendoUid) == i) {
                        Log.d(TAG, "getDefaultRouteApps: nonChainedVendorUid  " + nonChainedVendoUid);
                        arrayList.add(Integer.valueOf(nonChainedVendoUid));
                    }
                }
            }
        } catch (Exception unused) {
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final String getForwardMark(String str) {
        if (str == null || str.length() < 4) {
            return null;
        }
        for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.vpnProfileInfoMap.values()) {
            String str2 = vpnProfileInfo.mInterfaceName;
            if (str2 != null && str2.equalsIgnoreCase(str)) {
                int i = vpnProfileInfo.mNetId;
                if (i != 0) {
                    return Integer.toString(i);
                }
                return null;
            }
        }
        return null;
    }

    public final INetworkManagementService getNetworkManagementService() {
        String str = TAG;
        boolean z = DBG;
        if (z) {
            Log.d(str, "getNetworkManagementService is reached initially");
        }
        if (this.mNetworkManagementService == null) {
            IBinder service = ServiceManager.getService("network_management");
            if (z) {
                Log.d(str, "getNetworkManagementService binder value is" + service);
            }
            if (service != null) {
                this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
                if (z) {
                    Log.d(str, "getNetworkManagementService mNetworkManagementService value is" + this.mNetworkManagementService);
                }
            }
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

    public final String getProfileOwningUserId(int i) {
        Collection<VpnProfileInfo> values = this.vpnConfig.vpnProfileInfoMap.values();
        if (values == null) {
            return null;
        }
        for (VpnProfileInfo vpnProfileInfo : values) {
            if (vpnProfileInfo != null) {
                for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.mPackageMap.values()) {
                    if (vpnPackageInfo.getUid() == -2 && i == vpnPackageInfo.getCid()) {
                        return vpnProfileInfo.mProfileName;
                    }
                }
            }
        }
        return null;
    }

    public final void insertDropRulesForNoUidPackets(int i, int i2, String str, String str2, String str3) {
        String str4 = TAG;
        try {
            String forwardMark = getForwardMark(str);
            boolean z = DBG;
            if (z) {
                Log.d(str4, "The current defaultInterface  is " + str3);
            }
            if (z) {
                Log.d(str4, "The virtual interface value is " + str);
            }
            if (z) {
                Log.d(str4, "The virtualAddress value is " + str2);
            }
            if (str3 == null || str == null) {
                return;
            }
            if (i2 == 0) {
                if (str2 != null) {
                    insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", " -s " + str2 + " -p tcp --tcp-flags ALL RST ", "MARK --set-mark " + forwardMark, "", IpRestoreActionType.APPEND), i);
                    insertRule(true, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_drop", " -s " + str2 + " -o " + str3 + " -m mark ! --mark " + forwardMark, "DROP", "", IpRestoreActionType.INSERT), i);
                    return;
                }
                return;
            }
            if (i2 == 1 && str2 != null) {
                IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
                insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_no_uid", " -s " + str2 + " -p tcp --tcp-flags ALL RST ", "MARK --set-mark " + forwardMark, "", ipRestoreActionType), i);
                insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_drop", " -s " + str2 + " -o " + str3 + " -m mark ! --mark " + forwardMark, "DROP", "", ipRestoreActionType), i);
                insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_drop", " -s " + str2 + " -o v4-" + str3 + " -m mark ! --mark " + forwardMark, "DROP", "", ipRestoreActionType), i);
            }
        } catch (Exception unused) {
            Log.e(str4, "Exception occured while trying to get apply dropping rules for knox vpn packets");
        }
    }

    public final void insertExemptRulesForDownloadManagerUid(int i, int i2, String str) {
        int jnigetInterfaceIndex;
        int i3;
        String str2 = i == 6 ? "ip -6 rule" : "ip rule";
        if (i == 4) {
            jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str);
            String str3 = TAG;
            if (jnigetInterfaceIndex <= 0) {
                Log.d(str3, "Non Clat interface is detected while applying ip rules for download uid");
                jnigetInterfaceIndex = jnigetInterfaceIndex(str);
            } else {
                Log.d(str3, "Clat interface is detected while applying ip rules for download uid");
            }
        } else {
            jnigetInterfaceIndex = jnigetInterfaceIndex(str);
        }
        if (jnigetInterfaceIndex <= 0) {
            return;
        }
        try {
            i3 = IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management")).getNetIdforActiveDefaultInterface();
        } catch (RemoteException unused) {
            i3 = 0;
        }
        if (i3 <= 0) {
            return;
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, " del from all uidrange ");
        m.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        m.append(" prio 47;");
        m.append(str2);
        m.append(" add from all uidrange ");
        m.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        m.append(" fwmark ");
        m.append(i3);
        m.append(" lookup ");
        m.append(Integer.toString(jnigetInterfaceIndex));
        m.append(" prio 47;");
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_mangle_exempt_dl", ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, " -m owner --uid-owner ", " -m mark --mark "), "ACCEPT", "", IpRestoreActionType.INSERT), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i2, 1);
        } catch (RemoteException unused2) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        runSingleCommand(m.toString());
    }

    public final void insertIpRoute(int i, String str, String str2) {
        String str3 = TAG;
        if (DBG) {
            Log.d(str3, "add iptable route : ");
        }
        if (str2.contains("block_traffic")) {
            Log.e(str3, "add iptable route : Invalid interface name received:".concat(str2));
            return;
        }
        StringBuilder sb = new StringBuilder();
        String str4 = i == 6 ? "ip -6 route" : "ip route";
        String forwardMark = getForwardMark(str2);
        if (str4 == "ip route") {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str4, str, " 0.0.0.0/0 table ", forwardMark);
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, " dev ", str2, " prio 1;");
        } else if (str4 == "ip -6 route") {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "ip -6 route", str, " ::/0 table ", forwardMark);
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, " dev ", str2, " prio 1;");
        }
        runSingleCommand(sb.toString());
    }

    public final void insertIpRuleForUidList(int i, List list, String str, StringBuilder sb) {
        String str2 = i == 6 ? "ip -6 rule" : "ip rule";
        if (str.contains("block_traffic")) {
            return;
        }
        String forwardMark = getForwardMark(str);
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String num = Integer.toString(((Integer) it.next()).intValue());
            sb.append(str2);
            sb.append(" add ");
            sb.append("from all uidrange ");
            sb.append(num + PackageManagerShellCommandDataLoader.STDIN_PATH + num);
            sb.append(" lookup ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, forwardMark, " prio 52 ", ";");
        }
    }

    public final void insertIpRuleForuidRangeSourceSelection(int i, int i2, int i3, String str) {
        if (str.contains("block_traffic")) {
            return;
        }
        String forwardMark = getForwardMark(str);
        StringBuilder sb = new StringBuilder();
        sb.append(i == 6 ? "ip -6 rule" : "ip rule");
        sb.append(" add from all uidrange ");
        sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3);
        sb.append(" lookup ");
        sb.append(forwardMark);
        sb.append(" prio 53 ;");
        runSingleCommand(sb.toString());
    }

    public final void insertIpRules(int i, String str, String str2) {
        String str3 = TAG;
        if (DBG) {
            Log.d(str3, "add iptable rule : ");
        }
        if (str2.contains("block_traffic")) {
            Log.e(str3, "add iptable rule : Invalid interface name received:".concat(str2));
            return;
        }
        StringBuilder sb = new StringBuilder();
        String str4 = i == 6 ? "ip -6 rule" : "ip rule";
        String forwardMark = getForwardMark(str2);
        sb.append(str4);
        sb.append(str);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, " fwmark ", forwardMark, " table ", forwardMark);
        sb.append(" prio 48 ;");
        runSingleCommand(sb.toString());
    }

    public final void insertIpRulesForExemptedUid(int i, int i2, String str) {
        int jnigetInterfaceIndex;
        if (str.contains("rmnet") && i == 4) {
            jnigetInterfaceIndex = jnigetInterfaceIndex("v4-".concat(str));
            String str2 = TAG;
            if (jnigetInterfaceIndex <= 0) {
                Log.d(str2, "Non Clat Mobile interface is detected while applying ip rules");
                jnigetInterfaceIndex = jnigetInterfaceIndex(str);
            } else {
                Log.d(str2, "Clat interface is detected while applying ip rules");
            }
        } else {
            jnigetInterfaceIndex = jnigetInterfaceIndex(str);
        }
        if (jnigetInterfaceIndex <= 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i == 6 ? "ip -6 rule" : "ip rule");
        sb.append(" add from all uidrange ");
        sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        sb.append(" lookup ");
        sb.append(Integer.toString(jnigetInterfaceIndex));
        sb.append(" prio 51 ;");
        runSingleCommand(sb.toString());
    }

    public final void insertMarkingRulesForFilteredPackages(int i, String str, String str2) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        String forwardMark = "block_traffic".equals(str) ? "60" : getForwardMark(str);
        ArrayList arrayList = new ArrayList();
        String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("CONNMARK --set-mark ", forwardMark);
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam(m$1, "", m, "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), "CONNMARK --restore-mark", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), "ACCEPT", "", ipRestoreActionType));
        insertRules(i, "*mangle", Collections.singletonList(m$1), arrayList, false);
    }

    public final void insertNatRules(int i, String str) {
        if ("block_traffic".equals(str)) {
            return;
        }
        insertRule(true, "*nat", null, new IpRestoreParam("POSTROUTING", " -o ".concat(str), "MASQUERADE", "", IpRestoreActionType.APPEND), i);
    }

    public final void insertRule(boolean z, String str, String str2, IpRestoreParam ipRestoreParam, int i) {
        insertRules(i, str, Collections.singletonList(str2), Collections.singletonList(ipRestoreParam), z);
    }

    public final void insertRules(int i, String str, List list, List list2, boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(createTableHeaderCmd(str, list));
        if (z) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
                if (ipRestoreParam != null) {
                    IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
                    ArrayList arrayList2 = new ArrayList();
                    ipRestoreParam.actionType = IpRestoreActionType.DELETE;
                    arrayList2.addAll(arrayList);
                    arrayList2.add(parseIptablesRestoreCmd(ipRestoreParam));
                    arrayList2.add("COMMIT\n");
                    runIptablesRestoreCmd(i, arrayList2);
                    ipRestoreParam.actionType = ipRestoreActionType;
                }
            }
        }
        if (list2 != null) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.addAll(arrayList);
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                IpRestoreParam ipRestoreParam2 = (IpRestoreParam) it2.next();
                if (ipRestoreParam2 != null) {
                    arrayList3.add(parseIptablesRestoreCmd(ipRestoreParam2));
                }
            }
            arrayList = arrayList3;
        }
        arrayList.add("COMMIT\n");
        runIptablesRestoreCmd(i, arrayList);
    }

    public final void insertRulesForDroppingTetherPackets(int i, String str) {
        ArrayList arrayList = new ArrayList();
        String concat = " -i ".concat(str);
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", concat, "DROP", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", " -o ".concat(str), "DROP", "", ipRestoreActionType));
        insertRules(i, "*filter", Collections.singletonList("knox_vpn_filter_tether_fwd"), arrayList, true);
    }

    public final void insertRulesForNoUidPackets(int i, String str, String str2) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        String forwardMark = "block_traffic".equals(str) ? "60" : getForwardMark(str);
        ArrayList arrayList = new ArrayList();
        boolean equalsIgnoreCase = str.equalsIgnoreCase("block_traffic");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        if (!equalsIgnoreCase) {
            arrayList.add(new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), m$1, "", ipRestoreActionType));
            arrayList.add(new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m mark --mark ", forwardMark), m$1, "", ipRestoreActionType));
            insertRules(i, "*mangle", null, arrayList, true);
            return;
        }
        String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("knox-netId-", forwardMark);
        insertRule(false, "*mangle", m, new IpRestoreParam(m, "", "", "", IpRestoreActionType.REMOVE_CHAIN), i);
        String m2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("CONNMARK --set-mark ", forwardMark);
        IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam(m, "", m2, "", ipRestoreActionType2));
        arrayList.add(new IpRestoreParam(m, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), "CONNMARK --restore-mark", "", ipRestoreActionType2));
        arrayList.add(new IpRestoreParam(m, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), "ACCEPT", "", ipRestoreActionType2));
        insertRules(i, "*mangle", Collections.singletonList(m), arrayList, false);
        arrayList.clear();
        arrayList.add(new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m connmark --mark ", forwardMark), m, "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_no_uid", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m mark --mark ", forwardMark), m, "", ipRestoreActionType));
        insertRules(i, "*mangle", null, arrayList, true);
    }

    public final void insertRulesForUsbTethering(int i, String str, String str2, List list, String[] strArr) {
        String str3 = i == 6 ? "ip -6 rule" : "ip rule";
        String str4 = i == 6 ? "ip -6 route" : "ip route";
        String forwardMark = getForwardMark(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                getNetworkManagementService().setDnsForwardersForKnoxVpn(Integer.parseInt(forwardMark), strArr);
            } catch (Exception e) {
                Log.e(TAG, "setDnsForwardersForKnoxVpn: " + Log.getStackTraceString(e));
            }
            ArrayList arrayList = new ArrayList();
            String concat = " -i ".concat(str2);
            IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
            arrayList.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", concat, "ACCEPT", "", ipRestoreActionType));
            arrayList.add(new IpRestoreParam("knox_vpn_filter_tether_fwd", " -o ".concat(str2), "ACCEPT", "", ipRestoreActionType));
            insertRules(i, "*filter", Collections.singletonList("knox_vpn_filter_tether_fwd"), arrayList, false);
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(" del from all iif ");
            sb.append(str2);
            sb.append(" lookup ");
            sb.append(forwardMark);
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, " prio 43;", str3, " add from all iif ", str2);
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, " lookup ", forwardMark, " prio 43;");
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                String str5 = (String) it.next();
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str4, " del ", str5, " dev ");
                sb.append(str2);
                sb.append(" scope link metric 1 ");
                sb.append("table " + forwardMark);
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, ";", str4, " add ", str5);
                sb.append(" dev ");
                sb.append(str2);
                sb.append(" scope link metric 1 ");
                sb.append("table " + forwardMark);
                sb.append(";");
            }
            runSingleCommand(sb.toString());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void insertRulesToAcceptIncomingPackets(int i, String str) {
        if (str == null || str.contains("block_traffic")) {
            return;
        }
        String forwardMark = getForwardMark(str);
        StringBuilder sb = new StringBuilder();
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, i == 6 ? "ip -6 rule" : "ip rule", " add from all iif ", str, " lookup ");
        sb.append(forwardMark);
        sb.append(" prio 45;");
        runSingleCommand(sb.toString());
    }

    public final void insertRulesToAcceptProxyPackets(int i, int i2, String str) {
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_proxy_accept", " -m owner --uid-owner " + Integer.toString(i2) + " -m mark --mark " + getForwardMark(str), "ACCEPT", "", IpRestoreActionType.APPEND), i);
    }

    public final void insertRulesToAllowAccessToLocalHostWithValidMark(int i, int i2, String str) {
        String forwardMark = "block_traffic".equals(str) ? "60" : getForwardMark(str);
        ArrayList arrayList = new ArrayList();
        String m = SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, " -m mark --mark ", forwardMark, " -o lo -p tcp --dport ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam("knox_vpn_filter_output_act", m, "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_vpn_filter_output_act", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i2, " -m mark --mark ", forwardMark, " -o lo -p udp --dport "), "ACCEPT", "", ipRestoreActionType));
        insertRules(i, "*filter", null, arrayList, true);
    }

    public final void insertRulesToDropIpv6SystemQueries(int i) {
        insertRule(true, "*filter", null, new IpRestoreParam("OUTPUT", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner "), "DROP", "", IpRestoreActionType.INSERT), 6);
    }

    public final void insertRulesToExemptCaptivePortalQueries(int i, int i2) {
        int jnigetInterfaceIndex;
        if (i2 == -1) {
            return;
        }
        String str = i == 6 ? "ip -6 rule" : "ip rule";
        if (i == 4) {
            jnigetInterfaceIndex = jnigetInterfaceIndex("v4-".concat("wlan0"));
            String str2 = TAG;
            if (jnigetInterfaceIndex <= 0) {
                Log.d(str2, "Non Clat interface is detected while applying ip rules for download uid");
                jnigetInterfaceIndex = jnigetInterfaceIndex("wlan0");
            } else {
                Log.d(str2, "Clat interface is detected while applying ip rules for download uid");
            }
        } else {
            jnigetInterfaceIndex = jnigetInterfaceIndex("wlan0");
        }
        if (jnigetInterfaceIndex <= 0) {
            return;
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " del from all uidrange ");
        m.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        m.append(" prio 46;");
        m.append(str);
        m.append(" add from all uidrange ");
        m.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
        m.append(" lookup ");
        m.append(Integer.toString(jnigetInterfaceIndex));
        m.append(" prio 46;");
        insertRule(true, "*mangle", null, new IpRestoreParam("knox_vpn_mangle_exempt_cp", VibrationParam$1$$ExternalSyntheticOutline0.m(i2, " -m owner --uid-owner "), "ACCEPT", "", IpRestoreActionType.INSERT), i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i2, 1);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        runSingleCommand(m.toString());
    }

    public final void removeExemptRulesForDownloadManagerUid(int i) {
        deleteExemptRulesForDownloadManagerUid(4, i);
        deleteExemptRulesForDownloadManagerUid(6, i);
    }

    public final void removeExemptRulesForUid(int i) {
        applyBlockingRulesForDns(i, i, 4);
        insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_EXEMPT", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner "), "ACCEPT", "", IpRestoreActionType.DELETE), 46);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterExemptRules(i, 0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void removeInputFilterDropRulesForInterface(int i, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
            if (i == 1) {
                getNetworkManagementService().updateInputFilterUserWideRules(array, 0, 1);
            } else if (i == 0) {
                getNetworkManagementService().updateInputFilterAppWideRules(array, 0, 1);
            }
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void removeIpChainForProfile(String str) {
        if (str == null) {
            return;
        }
        String concat = str.concat("_uidlist");
        String concat2 = str.concat("_act");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_OUTPUT", "", concat, "", ipRestoreActionType), 46);
        IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.REMOVE_CHAIN;
        insertRule(false, "*mangle", concat, new IpRestoreParam(concat, null, null, null, ipRestoreActionType2), 46);
        insertRule(false, "*mangle", null, new IpRestoreParam("knox_vpn_OUTPUT", "", concat2, "", ipRestoreActionType), 46);
        insertRule(false, "*mangle", concat2, new IpRestoreParam(concat2, null, null, null, ipRestoreActionType2), 46);
    }

    public final void removeIpRouteAndPolicyRules(int i, String str) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        if (i == 1) {
            insertIpRules(4, " del ", str);
            insertIpRoute(4, " del ", str);
            return;
        }
        if (i == 2) {
            insertIpRules(6, " del ", str);
            insertIpRoute(6, " del ", str);
        } else {
            if (i != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved ", str2);
                return;
            }
            insertIpRules(4, " del ", str);
            insertIpRoute(4, " del ", str);
            insertIpRules(6, " del ", str);
            insertIpRoute(6, " del ", str);
        }
    }

    public final void removeIpRulesForExemptedUid(int i, int i2) {
        if (i2 == 1) {
            deleteIpRulesForExemptedUid(4, i);
            return;
        }
        if (i2 == 2) {
            deleteIpRulesForExemptedUid(6, i);
        } else if (i2 != 3) {
            deleteIpRulesForExemptedUid(4, i);
            deleteIpRulesForExemptedUid(6, i);
        } else {
            deleteIpRulesForExemptedUid(4, i);
            deleteIpRulesForExemptedUid(6, i);
        }
    }

    public final void removeMiscRules(int i, String str, List list) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        applyBlockingRulesForDns(2, list);
        StringBuilder sb = new StringBuilder();
        if (i == 1) {
            deleteIpRuleForuidSourceSelection(4, list, str, sb);
            runSingleCommand(sb.toString());
        } else if (i == 2) {
            deleteIpRuleForuidSourceSelection(6, list, str, sb);
            runSingleCommand(sb.toString());
        } else {
            if (i != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved ", str2);
                return;
            }
            deleteIpRuleForuidSourceSelection(4, list, str, sb);
            deleteIpRuleForuidSourceSelection(6, list, str, sb);
            runSingleCommand(sb.toString());
        }
    }

    public final void removeMiscRulesRange(int i, int i2, String str) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        int i3 = i * 100000;
        applyBlockingRulesForDns(i3 + 1, 99999 + i3, 2);
        List defaultRouteApps = getDefaultRouteApps(i);
        HashSet hashSet = new HashSet();
        Range range = new Range(Integer.valueOf(i3), Integer.valueOf(((i + 1) * 100000) - 1));
        int intValue = ((Integer) range.getLower()).intValue() + 1;
        Iterator it = ((ArrayList) defaultRouteApps).iterator();
        while (it.hasNext()) {
            int intValue2 = ((Integer) it.next()).intValue();
            if (intValue2 == intValue) {
                intValue++;
            } else {
                hashSet.add(new UidRangeParcel(intValue, intValue2 - 1));
                intValue = intValue2 + 1;
            }
        }
        if (intValue <= ((Integer) range.getUpper()).intValue()) {
            hashSet.add(new UidRangeParcel(intValue, ((Integer) range.getUpper()).intValue()));
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            UidRangeParcel uidRangeParcel = (UidRangeParcel) it2.next();
            StringBuilder sb = new StringBuilder("removeMiscRulesRange ");
            sb.append(uidRangeParcel.start);
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, uidRangeParcel.stop, str2);
            int i4 = uidRangeParcel.start;
            int i5 = uidRangeParcel.stop;
            if (i2 == 1) {
                deleteIpRuleForuidRangeSourceSelection(4, i4, i5);
            } else if (i2 == 2) {
                deleteIpRuleForuidRangeSourceSelection(6, i4, i5);
            } else if (i2 != 3) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i2, "unknown interface type has been recieved ", str2);
            } else {
                deleteIpRuleForuidRangeSourceSelection(4, i4, i5);
                deleteIpRuleForuidRangeSourceSelection(6, i4, i5);
            }
        }
    }

    public final void removeNatRules(int i, String str) {
        boolean checknterface = checknterface(str);
        String str2 = TAG;
        if (!checknterface) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("not allowed name  : ", str, str2);
            return;
        }
        if (str == null) {
            str = "block_traffic";
        }
        if (i == 1) {
            deleteNatRules(4, str);
            return;
        }
        if (i == 2) {
            deleteNatRules(6, str);
        } else if (i != 3) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved ", str2);
        } else {
            deleteNatRules(46, str);
        }
    }

    public final void removeRangeRulesForFilteredPackages(int i, String str, String str2) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_uidlist");
        String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        int i2 = 100000 * i;
        insertRule(false, "*mangle", null, new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m owner --uid-owner ", (i2 + 1) + PackageManagerShellCommandDataLoader.STDIN_PATH + (i2 + 99999)), m$12, "", IpRestoreActionType.DELETE), 46);
        if (str.contains("com.samsung.sVpn")) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            getNetworkManagementService().updateInputFilterUserWideRules(arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray(), 0, 0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void removeRulesForDroppingTethePackets() {
        insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
    }

    public final void removeRulesForFilteredPackages(String str, String str2, List list) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_uidlist");
        String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "_act");
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            insertRule(false, "*mangle", null, new IpRestoreParam(m$1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" -m owner --uid-owner ", Integer.toString(((Integer) it.next()).intValue())), m$12, "", IpRestoreActionType.DELETE), 46);
        }
        if (str.contains("com.samsung.sVpn")) {
            return;
        }
        int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getNetworkManagementService().updateInputFilterAppWideRules(array, 0, 0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void removeRulesForNoUidPackets(int i, String str, String str2) {
        if (str == null) {
            return;
        }
        if (i == 1) {
            deleteRulesForNoUidPackets(4, str, str2);
            return;
        }
        if (i == 2) {
            deleteRulesForNoUidPackets(6, str, str2);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "deleteRulesForNoUidPackets: unknown interface type has been recieved "));
        } else {
            deleteRulesForNoUidPackets(4, str, str2);
            deleteRulesForNoUidPackets(6, str, str2);
        }
    }

    public final void removeRulesForUsbTethering(int i, String str) {
        if (i == 1) {
            deleteRulesForUsbTethering(4, str);
            return;
        }
        if (i == 2) {
            deleteRulesForUsbTethering(6, str);
            return;
        }
        if (i == 3) {
            deleteRulesForUsbTethering(4, str);
            deleteRulesForUsbTethering(6, str);
        } else {
            deleteRulesForUsbTethering(4, str);
            deleteRulesForUsbTethering(6, str);
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "removeRulesForUsbTethering: unknown interface type has been recieved "));
        }
    }

    public final void removeRulesTetherAuth() {
        insertRule(false, "*nat", "knox_vpn_nat_preroute", null, 4);
        insertRule(false, "*filter", "knox_vpn_filter_tether_fwd", null, 46);
        insertRule(false, "*filter", "knox_vpn_tether_exempt", null, 4);
        insertRule(false, "*mangle", "knox_vpn_tether_exempt", null, 4);
        runSingleCommand("ip ruledel prio 42;");
    }

    public final void removeRulesToAcceptIncomingPackets(int i, String str) {
        if (str == null || str.contains("block_traffic")) {
            return;
        }
        if (i == 1) {
            deleteRulesToAcceptIncomingPackets(4, str);
            return;
        }
        if (i == 2) {
            deleteRulesToAcceptIncomingPackets(6, str);
        } else if (i != 3) {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved "));
        } else {
            deleteRulesToAcceptIncomingPackets(4, str);
            deleteRulesToAcceptIncomingPackets(6, str);
        }
    }

    public final void removeRulesToAcceptProxyPackets(int i, int i2, String str) {
        if (str == null || str.contains("block_traffic")) {
            return;
        }
        if (i == 1) {
            deleteRulesToAcceptProxyPackets(4, i2, str);
            return;
        }
        if (i == 2) {
            deleteRulesToAcceptProxyPackets(6, i2, str);
        } else if (i == 3) {
            deleteRulesToAcceptProxyPackets(46, i2, str);
        } else {
            Log.e(TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown interface type has been recieved "));
        }
    }

    public final void removeRulesToAllowAccessToLocalHostWithValidMark(int i, int i2, String str) {
        if (!checknterface(str)) {
            Log.d(TAG, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("not allowed name  : ", str));
            return;
        }
        if (str == null) {
            deleteRulesToAllowAccessToLocalHostWithValidMark(4, i, "block_traffic");
            deleteRulesToAllowAccessToLocalHostWithValidMark(6, i, "block_traffic");
            return;
        }
        if (i2 == 1) {
            deleteRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            deleteRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
            deleteRulesToAllowAccessToLocalHostWithValidMark(6, i, "block_traffic");
        } else if (i2 == 2) {
            deleteRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
            deleteRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            deleteRulesToAllowAccessToLocalHostWithValidMark(4, i, "block_traffic");
        } else {
            if (i2 != 3) {
                return;
            }
            deleteRulesToAllowAccessToLocalHostWithValidMark(4, i, str);
            deleteRulesToAllowAccessToLocalHostWithValidMark(6, i, str);
        }
    }

    public final void removeRulesToDenyAccessToLocalHost(int i) {
        String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -o lo -p tcp --dport ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_drop", m, "DROP", "", ipRestoreActionType), 46);
        insertRule(false, "*filter", null, new IpRestoreParam("knox_vpn_filter_output_drop", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -o lo -p udp --dport "), "DROP", "", ipRestoreActionType), 46);
    }

    public final void removeRulesToDropIpv6SystemQueries(int i) {
        insertRule(false, "*filter", null, new IpRestoreParam("OUTPUT", VibrationParam$1$$ExternalSyntheticOutline0.m(i, " -m owner --uid-owner "), "DROP", "", IpRestoreActionType.DELETE), 6);
    }

    public final void runIptablesRestoreCmd(int i, List list) {
        String runKnoxFirewallRulesCommand;
        String join = String.join("", list);
        synchronized (this) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        runKnoxFirewallRulesCommand = getNetworkManagementService().runKnoxFirewallRulesCommand(i, join);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to run cmd: " + e.getMessage());
                    }
                    if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
                        Log.e(TAG, "Failed to run command. Result=" + runKnoxFirewallRulesCommand + "\ncommand=" + join);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void runSingleCommand(String str) {
        String str2 = TAG;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = DBG;
        try {
            if (z) {
                try {
                    Log.d(str2, "Current time before applying the ip commands");
                } catch (RemoteException e) {
                    Log.e(str2, "runSingleCommand error " + e.getMessage());
                }
            }
            Map map = NetdHelper.allowedCommands;
            String[] split = (str == null || str.isEmpty()) ? null : str.trim().split(NetdHelper.CMD_DELIMITER);
            if (split == null) {
                Log.e(str2, "Error splitting commands");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            for (String str3 : split) {
                getNetworkManagementService().runKnoxRulesCommand(NetdHelper.getCmdNumber(str3).intValue(), NetdHelper.getCmdParams(str3));
            }
            if (z) {
                Log.d(str2, "Current time after applying the ip commands");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateDropRulesForNoUidPackets(int i, String str, String str2, String str3, String str4) {
        boolean z = str3 != null && str3.contains("rmnet") && jnigetInterfaceIndex("v4-".concat(str3)) > 0;
        String str5 = TAG;
        Log.d(str5, "Check to see if the default interface is clat " + z);
        if (z) {
            String concat = str3 != null ? "v4-".concat(str3) : null;
            DualAppManagerService$$ExternalSyntheticOutline0.m("The default interface is converted to clat and its new name is ", concat, str5);
            insertDropRulesForNoUidPackets(4, i, str, str2, concat);
        } else {
            insertDropRulesForNoUidPackets(4, i, str, str2, str3);
        }
        insertDropRulesForNoUidPackets(6, i, str, str4, str3);
    }
}
