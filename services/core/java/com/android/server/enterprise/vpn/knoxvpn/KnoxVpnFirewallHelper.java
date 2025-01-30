package com.android.server.enterprise.vpn.knoxvpn;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
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
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
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
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class KnoxVpnFirewallHelper {
  public static final int ACTION_IPTABLE_EXECUTE = 1;
  public static final int ACTION_IPTABLE_MULTI_EXECUTE = 2;
  public static final String ADD = " add ";
  public static final String BLACKHOLE_PRIORITY = " prio 50 ";
  public static final int BLOCK_DNS_QUERIES = 1;
  public static final String CMD = "command";
  public static final String COMMIT_CMD = "COMMIT\n";
  public static final String DEL = " del ";
  public static final String DELIMITER = ";";
  public static final String DELIMITER_IP_RESTORE = "\n";
  public static final int DESTROY_BLOCKED_NETWORK = 5;
  public static final int EXEMPT_DNS_QUERIES = 3;
  public static final String FORWARD_CHAIN = "FORWARD";
  public static final String INPUT_CHAIN = "INPUT";
  public static final int INVALID_UID = -1;
  public static final String IP4_ROUTE_CMD = "ip route";
  public static final String IP4_RULE_CMD = "ip rule";
  public static final String IP6_ROUTE_CMD = "ip -6 route";
  public static final String IP6_RULE_CMD = "ip -6 rule";
  public static final int IPV4 = 4;
  public static final int IPV4V6 = 46;
  public static final int IPV6 = 6;
  public static final String IP_RULE_ADDALL_PRIORITY = " prio 53 ";
  public static final String IP_RULE_CAPTIVE_PORTAL_PRIORITY = " prio 46";
  public static final String IP_RULE_DOWNLOAD_PRIORITY = " prio 47";
  public static final String IP_RULE_EXEMPT_UID_PRIORITY = " prio 51 ";
  public static final String IP_RULE_INCOMING_PRIORITY = " prio 45";
  public static final String IP_RULE_PER_APP_PRIORITY = " prio 52 ";
  public static final String IP_RULE_PRIORITY = " prio 48 ";
  public static final String IP_RULE_STRONGSWAN_P_PRIORITY = " prio 44";
  public static final String IP_RULE_USB_TETHERING_EXEMPT_PRIORITY = " prio 42";
  public static final String IP_RULE_USB_TETHERING_PRIORITY = " prio 43";
  public static final String KNOX_VPN_CHAINING = "knox_vpn_CHAINING";
  public static final String KNOX_VPN_EXEMPT = "knox_vpn_EXEMPT";
  public static final String KNOX_VPN_FILTER_FORWARD = "knox_vpn_filter_tether_fwd";
  public static final String KNOX_VPN_FILTER_INPUT_DROP = "knox_vpn_filter_input_drop";
  public static final String KNOX_VPN_FILTER_INPUT_TETHER = "knox_vpn_tether_exempt";
  public static final String KNOX_VPN_FILTER_OUTPUT_ACCEPT = "knox_vpn_filter_output_act";
  public static final String KNOX_VPN_FILTER_OUTPUT_DROP = "knox_vpn_filter_output_drop";
  public static final String KNOX_VPN_MANGLE_CAPTIVE_PORTAL_EXEMPT = "knox_vpn_mangle_exempt_cp";
  public static final String KNOX_VPN_MANGLE_OUTPUT_DOWNLOAD_EXEMPT = "knox_vpn_mangle_exempt_dl";
  public static final String KNOX_VPN_MANGLE_OUTPUT_TETHER = "knox_vpn_tether_exempt";
  public static final String KNOX_VPN_NAT_PREROUTING = "knox_vpn_nat_preroute";
  public static final String KNOX_VPN_NO_UID = "knox_vpn_no_uid";
  public static final String KNOX_VPN_OUTPUT = "knox_vpn_OUTPUT";
  public static final String KNOX_VPN_PROXY_ACCEPT = "knox_vpn_proxy_accept";
  public static final String LOCAL_NETWORK_TABLE_ID = "97";
  public static final String NETD_SERVICE_NAME = "netd";
  public static final String OUTPUT_CHAIN = "OUTPUT";
  public static final String PREROUTING_CHAIN = "PREROUTING";
  public static final int ROOT_ID = 0;
  public static final int RULE_APPEND = 0;
  public static final int RULE_DELETE = 1;
  public static final int SYS_ID = 1000;
  public static final String TABLE_FILTER = "*filter";
  public static final String TABLE_MANGLE = "*mangle";
  public static final String TABLE_NAT = "*nat";
  public static final int UNBLOCK_DNS_QUERIES = 2;
  public static final int UNEXEMPT_DNS_QUERIES = 4;
  public static final int VPN_UID = 1016;
  public static final String WAIT = " -w ";
  public static INetd mNetdService;
  public static IOemNetd mOemNetdService;
  public static final boolean DBG = Debug.semIsProductDev();
  public static String TAG = "FW-KnoxVpnFirewallHelper";
  public static String TETHER_TAG = "KnoxVpnTetherAuthentication";
  public static KnoxVpnFirewallHelper mInstance = null;
  public static final Map mangleChains = createMangleMapList();
  public static final Map filterChains = createFilterMap();
  public static final Map natChains = createNatMap();
  public final String mPattern = "[a-zA-Z0-9_]+";
  public INetworkManagementService mNetworkManagementService = null;
  public final VpnProfileConfig vpnConfig = VpnProfileConfig.getInstance();

  enum IpRestoreActionType {
    APPEND,
    INSERT,
    DELETE,
    REMOVE_CHAIN
  }

  public static native int jnigetIfaceIndexWitoutOffset(String str);

  public static native int jnigetInterfaceIndex(String str);

  public void addMangleNatRulesRange(int i, String str, int i2) {}

  public void removeMangleNatRulesRange(int i, String str, int i2) {}

  public static void connectNativeNetdService() {
    boolean z;
    INetd asInterface;
    try {
      asInterface = INetd.Stub.asInterface(ServiceManager.getService(NETD_SERVICE_NAME));
      mNetdService = asInterface;
    } catch (RemoteException unused) {
      mNetdService = null;
      z = false;
    }
    if (asInterface == null) {
      return;
    }
    z = asInterface.isAlive();
    if (z || !DBG) {
      return;
    }
    Log.e(TAG, "Can't connect to NativeNetdService netd");
  }

  public static IOemNetd getOemNetdService() {
    IOemNetd iOemNetd = mOemNetdService;
    if (iOemNetd != null) {
      return iOemNetd;
    }
    if (mNetdService == null) {
      connectNativeNetdService();
    }
    INetd iNetd = mNetdService;
    if (iNetd != null) {
      try {
        mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
      } catch (RemoteException e) {
        Log.e(TAG, "Failed to get OemNetd listener " + e.getMessage());
      }
    }
    return mOemNetdService;
  }

  public static UidRangeParcel[] toUidRangeStableParcels(Set set) {
    UidRangeParcel[] uidRangeParcelArr = new UidRangeParcel[set.size()];
    Iterator it = set.iterator();
    int i = 0;
    while (it.hasNext()) {
      UidRangeParcel uidRangeParcel = (UidRangeParcel) it.next();
      uidRangeParcelArr[i] = new UidRangeParcel(uidRangeParcel.start, uidRangeParcel.stop);
      i++;
    }
    return uidRangeParcelArr;
  }

  public final IVpnManager getVpnManagerService() {
    return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
  }

  public class IpRestoreParam {
    public String actionChain;
    public IpRestoreActionType actionType;
    public String firstParam;
    public String jumpChain;
    public String secondParam;

    public IpRestoreParam(
        String str,
        String str2,
        String str3,
        String str4,
        IpRestoreActionType ipRestoreActionType) {
      this.actionChain = str;
      this.firstParam = str2;
      this.jumpChain = str3;
      this.secondParam = str4;
      this.actionType = ipRestoreActionType;
    }
  }

  public static Map createMangleMapList() {
    HashMap hashMap = new HashMap();
    hashMap.put(
        OUTPUT_CHAIN,
        Arrays.asList(
            KNOX_VPN_NO_UID,
            KNOX_VPN_OUTPUT,
            KNOX_VPN_PROXY_ACCEPT,
            KNOX_VPN_MANGLE_OUTPUT_DOWNLOAD_EXEMPT,
            KNOX_VPN_MANGLE_CAPTIVE_PORTAL_EXEMPT,
            KNOX_VPN_EXEMPT,
            "knox_vpn_tether_exempt"));
    return hashMap;
  }

  public static Map createFilterMap() {
    HashMap hashMap = new HashMap();
    hashMap.put(INPUT_CHAIN, Arrays.asList(KNOX_VPN_FILTER_INPUT_DROP, "knox_vpn_tether_exempt"));
    hashMap.put(
        OUTPUT_CHAIN, Arrays.asList(KNOX_VPN_FILTER_OUTPUT_DROP, KNOX_VPN_FILTER_OUTPUT_ACCEPT));
    hashMap.put(FORWARD_CHAIN, Arrays.asList(KNOX_VPN_FILTER_FORWARD));
    return hashMap;
  }

  public static Map createNatMap() {
    HashMap hashMap = new HashMap();
    hashMap.put(PREROUTING_CHAIN, Arrays.asList(KNOX_VPN_NAT_PREROUTING));
    return hashMap;
  }

  public static synchronized KnoxVpnFirewallHelper getInstance() {
    KnoxVpnFirewallHelper knoxVpnFirewallHelper;
    synchronized (KnoxVpnFirewallHelper.class) {
      if (mInstance == null) {
        mInstance = new KnoxVpnFirewallHelper();
      }
      knoxVpnFirewallHelper = mInstance;
    }
    return knoxVpnFirewallHelper;
  }

  public final INetworkManagementService getNetworkManagementService() {
    boolean z = DBG;
    if (z) {
      Log.d(TAG, "getNetworkManagementService is reached initially");
    }
    if (this.mNetworkManagementService == null) {
      IBinder service = ServiceManager.getService("network_management");
      if (z) {
        Log.d(TAG, "getNetworkManagementService binder value is" + service);
      }
      if (service != null) {
        this.mNetworkManagementService = INetworkManagementService.Stub.asInterface(service);
        if (z) {
          Log.d(
              TAG,
              "getNetworkManagementService mNetworkManagementService value is"
                  + this.mNetworkManagementService);
        }
      }
    }
    return this.mNetworkManagementService;
  }

  public KnoxVpnFirewallHelper() {
    createIpTableChains();
  }

  public void addIpRouteAndPolicyRules(String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (i == 1) {
      insertIpRules(4, ADD, str);
      insertIpRoute(4, ADD, str);
      return;
    }
    if (i == 2) {
      insertIpRules(6, ADD, str);
      insertIpRoute(6, ADD, str);
      return;
    }
    if (i == 3) {
      insertIpRules(4, ADD, str);
      insertIpRoute(4, ADD, str);
      insertIpRules(6, ADD, str);
      insertIpRoute(6, ADD, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void removeIpRouteAndPolicyRules(String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (i == 1) {
      insertIpRules(4, DEL, str);
      insertIpRoute(4, DEL, str);
      return;
    }
    if (i == 2) {
      insertIpRules(6, DEL, str);
      insertIpRoute(6, DEL, str);
      return;
    }
    if (i == 3) {
      insertIpRules(4, DEL, str);
      insertIpRoute(4, DEL, str);
      insertIpRules(6, DEL, str);
      insertIpRoute(6, DEL, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void addIpRulesForExemptedUid(int i, String str, int i2) {
    if (str == null) {
      return;
    }
    if (i2 == 1) {
      deleteIpRulesForExemptedUid(4, DEL, i);
      insertIpRulesForExemptedUid(4, ADD, str, i);
      return;
    }
    if (i2 == 2) {
      deleteIpRulesForExemptedUid(6, DEL, i);
      insertIpRulesForExemptedUid(6, ADD, str, i);
      return;
    }
    if (i2 == 3) {
      deleteIpRulesForExemptedUid(4, DEL, i);
      insertIpRulesForExemptedUid(4, ADD, str, i);
      deleteIpRulesForExemptedUid(6, DEL, i);
      insertIpRulesForExemptedUid(6, ADD, str, i);
      return;
    }
    Log.e(
        TAG,
        "unknown interface type has been recieved for the method addIpRulesForExemptedUid" + i2);
  }

  public void removeIpRulesForExemptedUid(int i, int i2) {
    if (i2 == 1) {
      deleteIpRulesForExemptedUid(4, DEL, i);
      return;
    }
    if (i2 == 2) {
      deleteIpRulesForExemptedUid(6, DEL, i);
    } else if (i2 == 3) {
      deleteIpRulesForExemptedUid(4, DEL, i);
      deleteIpRulesForExemptedUid(6, DEL, i);
    } else {
      deleteIpRulesForExemptedUid(4, DEL, i);
      deleteIpRulesForExemptedUid(6, DEL, i);
    }
  }

  public void blockIncomingICMPPackets(boolean z, String str) {
    if (z) {
      String str2 = KNOX_VPN_FILTER_INPUT_DROP;
      IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
      insertRule(
          true,
          TABLE_FILTER,
          null,
          new IpRestoreParam(
              str2, " -p ICMP --icmp-type 8 -i " + str, "DROP", "", ipRestoreActionType),
          4);
      insertRule(
          true,
          TABLE_FILTER,
          null,
          new IpRestoreParam(
              KNOX_VPN_FILTER_INPUT_DROP,
              " -p ICMPV6 --icmpv6-type 8 -i " + str,
              "DROP",
              "",
              ipRestoreActionType),
          6);
      return;
    }
    String str3 = KNOX_VPN_FILTER_INPUT_DROP;
    IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            str3, " -p ICMP --icmp-type 8 -i " + str, "DROP", "", ipRestoreActionType2),
        4);
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            KNOX_VPN_FILTER_INPUT_DROP,
            " -p ICMPV6 --icmpv6-type 8 -i " + str,
            "DROP",
            "",
            ipRestoreActionType2),
        6);
  }

  public void addInputFilterDropRulesForInterface(String str, int i, List list, String str2) {
    int i2;
    if (str.equalsIgnoreCase("com.samsung.sVpn")) {
      return;
    }
    if (str2 != null) {
      i2 = jnigetIfaceIndexWitoutOffset("v4-" + str2);
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
      int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
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

  public void removeInputFilterDropRulesForInterface(int i, List list) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
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

  public void addMiscRules(List list, String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (str.equalsIgnoreCase(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      applyBlockingRulesForDns(list, 1);
    } else {
      applyBlockingRulesForDns(list, 2);
    }
    StringBuilder sb = new StringBuilder();
    if (i == 1) {
      insertIpRuleForUidList(4, list, str, sb);
      runSingleCommand(sb.toString());
      return;
    }
    if (i == 2) {
      insertIpRuleForUidList(6, list, str, sb);
      runSingleCommand(sb.toString());
    } else if (i == 3) {
      insertIpRuleForUidList(4, list, str, sb);
      insertIpRuleForUidList(6, list, str, sb);
      runSingleCommand(sb.toString());
    } else {
      Log.e(TAG, "unknown interface type has been recieved " + i);
    }
  }

  public void addMiscRulesRange(int i, String str, int i2) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    int i3 = (100000 * i) + 1;
    int i4 = (i3 - 1) + 99999;
    if (str.equalsIgnoreCase(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      applyBlockingRulesForDns(i3, i4, 1);
    } else {
      applyBlockingRulesForDns(i3, i4, 2);
    }
    List defaultRouteApps = getDefaultRouteApps(i);
    HashSet<UidRangeParcel> hashSet = new HashSet();
    Range createUidRangeForUser = createUidRangeForUser(i);
    int intValue = ((Integer) createUidRangeForUser.getLower()).intValue() + 1;
    Iterator it = defaultRouteApps.iterator();
    while (it.hasNext()) {
      int intValue2 = ((Integer) it.next()).intValue();
      if (intValue2 == intValue) {
        intValue++;
      } else {
        hashSet.add(new UidRangeParcel(intValue, intValue2 - 1));
        intValue = intValue2 + 1;
      }
    }
    if (intValue <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
      hashSet.add(
          new UidRangeParcel(intValue, ((Integer) createUidRangeForUser.getUpper()).intValue()));
    }
    for (UidRangeParcel uidRangeParcel : hashSet) {
      Log.d(TAG, "addMiscRulesRange " + uidRangeParcel.start + uidRangeParcel.stop + str);
      addMiscRulesRange(str, i2, uidRangeParcel.start, uidRangeParcel.stop);
    }
  }

  public final void addMiscRulesRange(String str, int i, int i2, int i3) {
    if (i == 1) {
      deleteIpRuleForuidRangeSourceSelection(4, DEL, i2, i3);
      insertIpRuleForuidRangeSourceSelection(4, ADD, str, i2, i3);
      return;
    }
    if (i == 2) {
      deleteIpRuleForuidRangeSourceSelection(6, DEL, i2, i3);
      insertIpRuleForuidRangeSourceSelection(6, ADD, str, i2, i3);
      return;
    }
    if (i == 3) {
      deleteIpRuleForuidRangeSourceSelection(4, DEL, i2, i3);
      insertIpRuleForuidRangeSourceSelection(4, ADD, str, i2, i3);
      deleteIpRuleForuidRangeSourceSelection(6, DEL, i2, i3);
      insertIpRuleForuidRangeSourceSelection(6, ADD, str, i2, i3);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void removeMiscRules(List list, String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    applyBlockingRulesForDns(list, 2);
    StringBuilder sb = new StringBuilder();
    if (i == 1) {
      deleteIpRuleForuidSourceSelection(4, list, str, sb);
      runSingleCommand(sb.toString());
      return;
    }
    if (i == 2) {
      deleteIpRuleForuidSourceSelection(6, list, str, sb);
      runSingleCommand(sb.toString());
    } else if (i == 3) {
      deleteIpRuleForuidSourceSelection(4, list, str, sb);
      deleteIpRuleForuidSourceSelection(6, list, str, sb);
      runSingleCommand(sb.toString());
    } else {
      Log.e(TAG, "unknown interface type has been recieved " + i);
    }
  }

  public void removeMiscRulesRange(int i, String str, int i2) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    int i3 = (100000 * i) + 1;
    applyBlockingRulesForDns(i3, (i3 - 1) + 99999, 2);
    List defaultRouteApps = getDefaultRouteApps(i);
    HashSet<UidRangeParcel> hashSet = new HashSet();
    Range createUidRangeForUser = createUidRangeForUser(i);
    int intValue = ((Integer) createUidRangeForUser.getLower()).intValue() + 1;
    Iterator it = defaultRouteApps.iterator();
    while (it.hasNext()) {
      int intValue2 = ((Integer) it.next()).intValue();
      if (intValue2 == intValue) {
        intValue++;
      } else {
        hashSet.add(new UidRangeParcel(intValue, intValue2 - 1));
        intValue = intValue2 + 1;
      }
    }
    if (intValue <= ((Integer) createUidRangeForUser.getUpper()).intValue()) {
      hashSet.add(
          new UidRangeParcel(intValue, ((Integer) createUidRangeForUser.getUpper()).intValue()));
    }
    for (UidRangeParcel uidRangeParcel : hashSet) {
      Log.d(TAG, "removeMiscRulesRange " + uidRangeParcel.start + uidRangeParcel.stop);
      removeMiscRulesRange(i2, uidRangeParcel.start, uidRangeParcel.stop);
    }
  }

  public final void removeMiscRulesRange(int i, int i2, int i3) {
    if (i == 1) {
      deleteIpRuleForuidRangeSourceSelection(4, DEL, i2, i3);
      return;
    }
    if (i == 2) {
      deleteIpRuleForuidRangeSourceSelection(6, DEL, i2, i3);
      return;
    }
    if (i == 3) {
      deleteIpRuleForuidRangeSourceSelection(4, DEL, i2, i3);
      deleteIpRuleForuidRangeSourceSelection(6, DEL, i2, i3);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void removeNatRules(String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (i == 1) {
      deleteNatRules(4, str);
      return;
    }
    if (i == 2) {
      deleteNatRules(6, str);
      return;
    }
    if (i == 3) {
      deleteNatRules(46, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void addNatRules(String str, int i) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (i == 1) {
      insertNatRules(4, str);
      return;
    }
    if (i == 2) {
      insertNatRules(6, str);
      return;
    }
    if (i == 3) {
      insertNatRules(46, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void addExemptRulesForUid(int i) {
    applyBlockingRulesForDns(i, i, 3);
    insertExemptRulesForUid(46, i);
  }

  public void removeExemptRulesForUid(int i) {
    applyBlockingRulesForDns(i, i, 4);
    deleteExemptRulesForUid(46, i);
  }

  public void addRulesToAllowAccessToLocalHostWithValidMark(String str, int i, int i2) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      return;
    }
    if (i2 == 1) {
      insertRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      insertRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
      insertRulesToAllowAccessToLocalHostWithValidMark(6, KnoxVpnConstants.BLOCK_APP_TRAFFIC, i);
    } else if (i2 == 2) {
      insertRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
      insertRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      insertRulesToAllowAccessToLocalHostWithValidMark(4, KnoxVpnConstants.BLOCK_APP_TRAFFIC, i);
    } else {
      if (i2 != 3) {
        return;
      }
      insertRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      insertRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
    }
  }

  public void removeRulesToAllowAccessToLocalHostWithValidMark(String str, int i, int i2) {
    if (!checknterface(str)) {
      Log.d(TAG, "not allowed name  : " + str);
      return;
    }
    if (str == null) {
      String str2 = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
      deleteRulesToAllowAccessToLocalHostWithValidMark(4, str2, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(6, str2, i);
    } else if (i2 == 1) {
      deleteRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(6, KnoxVpnConstants.BLOCK_APP_TRAFFIC, i);
    } else if (i2 == 2) {
      deleteRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(4, KnoxVpnConstants.BLOCK_APP_TRAFFIC, i);
    } else {
      if (i2 != 3) {
        return;
      }
      deleteRulesToAllowAccessToLocalHostWithValidMark(4, str, i);
      deleteRulesToAllowAccessToLocalHostWithValidMark(6, str, i);
    }
  }

  public void addRulesToDenyAccessToLocalHost(int i) {
    insertRulesToDenyAccessToLocalHost(46, i);
  }

  public void removeRulesToDenyAccessToLocalHost(int i) {
    deleteRulesToDenyAccessToLocalHost(46, i);
  }

  public void updateDropRulesForNoUidPackets(
      int i, String str, String str2, String str3, String str4) {
    if (isClatInterface(str3)) {
      insertDropRulesForNoUidPackets(4, i, str, str2, convertToClat(str3));
    } else {
      insertDropRulesForNoUidPackets(4, i, str, str2, str3);
    }
    insertDropRulesForNoUidPackets(6, i, str, str4, str3);
  }

  public void updateIpBlockingRule() {
    deleteIpBlockingRule();
    insertIpBlockingRule();
  }

  public void removeIpBlockingRule() {
    deleteIpBlockingRule();
  }

  public void addExemptRulesForStrongswan(int i) {
    applyBlockingRulesForDns(1016, 1016, 3);
    insertExemptRulesForStrongswan(46, i);
  }

  public void removeExemptRulesForStrongswan(int i) {
    applyBlockingRulesForDns(1016, 1016, 4);
    deleteExemptRulesForStrongswan(46, i);
  }

  public void addRulesToAcceptProxyPackets(int i, String str, int i2) {
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    if (i == 1) {
      insertRulesToAcceptProxyPackets(4, ADD, str, i2);
      return;
    }
    if (i == 2) {
      insertRulesToAcceptProxyPackets(6, ADD, str, i2);
      return;
    }
    if (i == 3) {
      insertRulesToAcceptProxyPackets(46, ADD, str, i2);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void removeRulesToAcceptProxyPackets(int i, String str, int i2) {
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    if (i == 1) {
      deleteRulesToAcceptProxyPackets(4, DEL, str, i2);
      return;
    }
    if (i == 2) {
      deleteRulesToAcceptProxyPackets(6, DEL, str, i2);
      return;
    }
    if (i == 3) {
      deleteRulesToAcceptProxyPackets(46, DEL, str, i2);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void addRulesToAcceptIncomingPackets(int i, String str) {
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    if (i == 1) {
      deleteRulesToAcceptIncomingPackets(4, DEL, str);
      insertRulesToAcceptIncomingPackets(4, ADD, str);
      return;
    }
    if (i == 2) {
      deleteRulesToAcceptIncomingPackets(6, DEL, str);
      insertRulesToAcceptIncomingPackets(6, ADD, str);
      return;
    }
    if (i == 3) {
      deleteRulesToAcceptIncomingPackets(4, DEL, str);
      insertRulesToAcceptIncomingPackets(4, ADD, str);
      deleteRulesToAcceptIncomingPackets(6, DEL, str);
      insertRulesToAcceptIncomingPackets(6, ADD, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void removeRulesToAcceptIncomingPackets(int i, String str) {
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    if (i == 1) {
      deleteRulesToAcceptIncomingPackets(4, DEL, str);
      return;
    }
    if (i == 2) {
      deleteRulesToAcceptIncomingPackets(6, DEL, str);
      return;
    }
    if (i == 3) {
      deleteRulesToAcceptIncomingPackets(4, DEL, str);
      deleteRulesToAcceptIncomingPackets(6, DEL, str);
      return;
    }
    Log.e(TAG, "unknown interface type has been recieved " + i);
  }

  public void addExemptRulesForDownloadManagerUid(int i, String str) {
    if (str == null || i == -1) {
      return;
    }
    insertExemptRulesForDownloadManagerUid(4, i, str);
    insertExemptRulesForDownloadManagerUid(6, i, str);
  }

  public void removeExemptRulesForDownloadManagerUid(int i) {
    deleteExemptRulesForDownloadManagerUid(4, i);
    deleteExemptRulesForDownloadManagerUid(6, i);
  }

  public void addRulesToExemptCaptivePortalQueries(String str, int i) {
    applyBlockingRulesForDns(i, i, 3);
    insertRulesToExemptCaptivePortalQueries(4, i, str);
    insertRulesToExemptCaptivePortalQueries(6, i, str);
  }

  public void removeRulesToExemptCaptivePortalQueries(int i) {
    applyBlockingRulesForDns(i, i, 4);
    deleteRulesToExemptCaptivePortalQueries(4, i);
    deleteRulesToExemptCaptivePortalQueries(6, i);
  }

  public void insertRulesToDropIpv6SystemQueries(int i) {
    insertRule(
        true,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            OUTPUT_CHAIN, " -m owner --uid-owner " + i, "DROP", "", IpRestoreActionType.INSERT),
        6);
  }

  public void removeRulesToDropIpv6SystemQueries(int i) {
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            OUTPUT_CHAIN, " -m owner --uid-owner " + i, "DROP", "", IpRestoreActionType.DELETE),
        6);
  }

  public void addRulesForNoUidPackets(String str, String str2, int i) {
    if (str2 == null) {
      return;
    }
    if (i == 1) {
      insertRulesForNoUidPackets(4, str2, str);
      return;
    }
    if (i == 2) {
      insertRulesForNoUidPackets(6, str2, str);
      return;
    }
    if (i == 3) {
      insertRulesForNoUidPackets(4, str2, str);
      insertRulesForNoUidPackets(6, str2, str);
      return;
    }
    Log.e(TAG, "addRulesForNoUidPackets: unknown interface type has been recieved " + i);
  }

  public void removeRulesForNoUidPackets(String str, int i, String str2) {
    if (str == null) {
      return;
    }
    if (i == 1) {
      deleteRulesForNoUidPackets(4, str, str2);
      return;
    }
    if (i == 2) {
      deleteRulesForNoUidPackets(6, str, str2);
      return;
    }
    if (i == 3) {
      deleteRulesForNoUidPackets(4, str, str2);
      deleteRulesForNoUidPackets(6, str, str2);
      return;
    }
    Log.e(TAG, "deleteRulesForNoUidPackets: unknown interface type has been recieved " + i);
  }

  public void applyBlockingRulesForDns(int i, int i2, int i3) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        ArraySet arraySet = new ArraySet();
        arraySet.add(new UidRangeParcel(i, i2));
        Log.d(
            TAG,
            "Action to be performed on the dns packet is "
                + i3
                + " for the start uid "
                + i
                + " and for the stop uid "
                + i2);
        if (i3 == 1) {
          getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
          getOemNetdService().knoxVpnBlockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i3 == 2) {
          getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i3 == 3) {
          getOemNetdService()
              .knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
          getOemNetdService().knoxVpnExemptDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i3 == 4) {
          getOemNetdService()
              .knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i3 == 5) {
          getOemNetdService().knoxVpnDestroyBlockedKnoxNetwork();
        }
      } catch (Exception unused) {
        Log.e(TAG, "error occured while trying to update rules for dns packets ");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void applyBlockingRulesForDns(List list, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        ArraySet arraySet = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          arraySet.add(new UidRangeParcel(intValue, intValue));
          Log.d(
              TAG,
              "Action to be performed on the dns packet is "
                  + i
                  + " for the start uid "
                  + intValue
                  + " and for the stop uid "
                  + intValue);
        }
        if (i == 1) {
          getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
          getOemNetdService().knoxVpnBlockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i == 2) {
          getOemNetdService().knoxVpnUnblockDnsQueriesForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i == 3) {
          getOemNetdService()
              .knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
          getOemNetdService().knoxVpnExemptDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i == 4) {
          getOemNetdService()
              .knoxVpnRemoveExemptedDnsQueryForUid(0, toUidRangeStableParcels(arraySet));
        } else if (i == 5) {
          getOemNetdService().knoxVpnDestroyBlockedKnoxNetwork();
        }
      } catch (Exception unused) {
        Log.e(TAG, "error occured while trying to update rules for dns packets ");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void createIpChainForProfile(String str) {
    if (str == null) {
      return;
    }
    insertRules(false, TABLE_MANGLE, Arrays.asList(str + "_uidlist", str + "_act"), null, 46);
  }

  public void removeIpChainForProfile(String str) {
    if (str == null) {
      return;
    }
    String str2 = str + "_uidlist";
    String str3 = str + "_act";
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(KNOX_VPN_OUTPUT, "", str2, "", ipRestoreActionType),
        46);
    IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.REMOVE_CHAIN;
    insertRule(
        false,
        TABLE_MANGLE,
        str2,
        new IpRestoreParam(str2, null, null, null, ipRestoreActionType2),
        46);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(KNOX_VPN_OUTPUT, "", str3, "", ipRestoreActionType),
        46);
    insertRule(
        false,
        TABLE_MANGLE,
        str3,
        new IpRestoreParam(str3, null, null, null, ipRestoreActionType2),
        46);
  }

  public void addRulesInOutputChain(String str) {
    if (str == null) {
      return;
    }
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(KNOX_VPN_OUTPUT, "", str + "_uidlist", "", IpRestoreActionType.APPEND),
        46);
  }

  public void addRulesForFilteredPackages(String str, String str2, List list, String str3) {
    int i;
    String str4 = str2 + "_uidlist";
    String str5 = str2 + "_act";
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      arrayList.add(
          new IpRestoreParam(
              str4,
              " -m owner --uid-owner " + Integer.toString(((Integer) it.next()).intValue()),
              str5,
              "",
              IpRestoreActionType.INSERT));
    }
    if (!arrayList.isEmpty()) {
      insertRules(false, TABLE_MANGLE, null, arrayList, 46);
    }
    if (str.contains("com.samsung.sVpn")) {
      return;
    }
    if (str3 != null) {
      i = jnigetIfaceIndexWitoutOffset("v4-" + str3);
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
      getNetworkManagementService()
          .updateInputFilterAppWideRules(
              list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray(), i, 1);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public void removeRulesForFilteredPackages(String str, String str2, List list) {
    String str3 = str2 + "_uidlist";
    String str4 = str2 + "_act";
    Iterator it = list.iterator();
    while (it.hasNext()) {
      insertRule(
          false,
          TABLE_MANGLE,
          null,
          new IpRestoreParam(
              str3,
              " -m owner --uid-owner " + Integer.toString(((Integer) it.next()).intValue()),
              str4,
              "",
              IpRestoreActionType.DELETE),
          46);
    }
    if (str.contains("com.samsung.sVpn")) {
      return;
    }
    int[] array = list.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
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

  public void addRangeRulesForFilteredPackages(String str, String str2, int i, String str3) {
    int i2;
    int i3 = (100000 * i) + 1;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            str2 + "_uidlist",
            " -m owner --uid-owner "
                + (i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + ((i3 - 1) + 99999)),
            str2 + "_act",
            "",
            IpRestoreActionType.APPEND),
        46);
    if (str.contains("com.samsung.sVpn")) {
      return;
    }
    if (str3 != null) {
      i2 = jnigetIfaceIndexWitoutOffset("v4-" + str3);
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
      getNetworkManagementService()
          .updateInputFilterUserWideRules(
              arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray(),
              i2,
              1);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public void removeRangeRulesForFilteredPackages(String str, String str2, int i) {
    int i2 = (100000 * i) + 1;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            str2 + "_uidlist",
            " -m owner --uid-owner "
                + (i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + ((i2 - 1) + 99999)),
            str2 + "_act",
            "",
            IpRestoreActionType.DELETE),
        46);
    if (str.contains("com.samsung.sVpn")) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ArrayList arrayList = new ArrayList();
      arrayList.add(Integer.valueOf(i));
      getNetworkManagementService()
          .updateInputFilterUserWideRules(
              arrayList.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray(),
              0,
              0);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public void addMarkingRulesForFilteredPackages(String str, String str2, int i) {
    if (str == null) {
      str = KnoxVpnConstants.BLOCK_APP_TRAFFIC;
    }
    if (i == 1) {
      insertMarkingRulesForFilteredPackages(4, str, str2);
      return;
    }
    if (i == 2) {
      insertMarkingRulesForFilteredPackages(6, str, str2);
      return;
    }
    if (i == 3) {
      insertMarkingRulesForFilteredPackages(4, str, str2);
      insertMarkingRulesForFilteredPackages(6, str, str2);
      return;
    }
    Log.e(TAG, "addMarkingRulesForFilteredPackages: unknown interface type has been recieved " + i);
  }

  public void flushMarkingRulesForFilteredPackages(String str) {
    insertRule(false, TABLE_MANGLE, str + "_act", null, 46);
  }

  public void addRulesForUsbTethering(String str, int i, String[] strArr, String str2, List list) {
    if (i == 1) {
      deleteRulesForDroppingTetherPackets();
      insertRulesForUsbTethering(4, str, strArr, str2, list);
      insertRulesForDroppingTetherPackets(6, str2);
      return;
    }
    if (i == 2) {
      deleteRulesForDroppingTetherPackets();
      insertRulesForUsbTethering(6, str, strArr, str2, list);
      insertRulesForDroppingTetherPackets(4, str2);
    } else if (i == 3) {
      deleteRulesForDroppingTetherPackets();
      insertRulesForUsbTethering(4, str, strArr, str2, list);
      insertRulesForUsbTethering(6, str, strArr, str2, list);
    } else {
      Log.e(TAG, "addRulesForUsbTethering: unknown interface type has been recieved " + i);
    }
  }

  public void removeRulesForUsbTethering(int i, String str) {
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
      return;
    }
    deleteRulesForUsbTethering(4, str);
    deleteRulesForUsbTethering(6, str);
    Log.e(TAG, "removeRulesForUsbTethering: unknown interface type has been recieved " + i);
  }

  public void addRulesForDroppingTetherPackets(String str) {
    insertRulesForDroppingTetherPackets(4, str);
    insertRulesForDroppingTetherPackets(6, str);
  }

  public void removeRulesForDroppingTethePackets() {
    deleteRulesForDroppingTetherPackets();
  }

  public void addRulesTetherAuth(int i, String str, List list, Bundle bundle) {
    String str2;
    int i2 = bundle.getInt("com.samsung.android.knox.intent.extra.DNS_REDIRECTION_PORT", -1);
    int i3 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTP_REDIRECTION_PORT", -1);
    int i4 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTPS_REDIRECTION_PORT", -1);
    int i5 = bundle.getInt("com.samsung.android.knox.intent.extra.HTTPS_REDIRECTION_AUTH_PORT", -1);
    if (i2 <= 0 || i3 <= 0 || i4 <= 0 || i5 <= 0) {
      Log.e(TETHER_TAG, "The ports received for usb tether mutual authentication is not valid");
      return;
    }
    if (str == null) {
      Log.e(
          TETHER_TAG,
          "The usb interface name received for usb tether mutual authentication is not valid");
      return;
    }
    if (list == null) {
      Log.e(
          TETHER_TAG,
          "The usb interface ip addresses received for usb tether mutual authentication is not"
              + " valid");
      return;
    }
    Iterator it = list.iterator();
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
      Log.e(
          TETHER_TAG,
          "The usb interface ipV4 address received for usb tether mutual authentication is not"
              + " valid");
      return;
    }
    if (i <= 0) {
      Log.e(TETHER_TAG, "applying firewall rules for tether auth uid failed");
    }
    ArrayList arrayList = new ArrayList();
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_NAT_PREROUTING,
            " -i " + str + " -p udp --dport 53",
            "DNAT --to " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + i2,
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_NAT_PREROUTING,
            " -i " + str + " -p tcp --dport 80",
            "DNAT --to " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + i3,
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_NAT_PREROUTING,
            " -i " + str + " -p tcp --dport " + i5,
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_NAT_PREROUTING,
            " -i " + str + " -p tcp --dport 443",
            "DNAT --to " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + i4,
            "",
            ipRestoreActionType));
    insertRules(true, TABLE_NAT, Collections.singletonList(KNOX_VPN_NAT_PREROUTING), arrayList, 4);
    ArrayList arrayList2 = new ArrayList();
    String str3 = KNOX_VPN_FILTER_FORWARD;
    IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.INSERT;
    arrayList2.add(new IpRestoreParam(str3, " -i " + str, "DROP", "", ipRestoreActionType2));
    arrayList2.add(
        new IpRestoreParam(
            KNOX_VPN_FILTER_FORWARD, " -o " + str, "DROP", "", ipRestoreActionType2));
    insertRules(
        true, TABLE_FILTER, Collections.singletonList(KNOX_VPN_FILTER_FORWARD), arrayList2, 46);
    ArrayList arrayList3 = new ArrayList();
    arrayList3.add(
        new IpRestoreParam(
            "knox_vpn_tether_exempt",
            " -i " + str + " -p udp --dport " + i2,
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList3.add(
        new IpRestoreParam(
            "knox_vpn_tether_exempt",
            " -i " + str + " -p tcp --dport " + i3,
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList3.add(
        new IpRestoreParam(
            "knox_vpn_tether_exempt",
            " -i " + str + " -p tcp --dport " + i5,
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList3.add(
        new IpRestoreParam(
            "knox_vpn_tether_exempt",
            " -i " + str + " -p tcp --dport " + i4,
            "ACCEPT",
            "",
            ipRestoreActionType));
    insertRules(
        true, TABLE_FILTER, Collections.singletonList("knox_vpn_tether_exempt"), arrayList3, 4);
    StringBuilder sb = new StringBuilder();
    sb.append(" -m owner --uid-owner ");
    sb.append(i);
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            "knox_vpn_tether_exempt", sb.toString(), "ACCEPT", "", ipRestoreActionType2),
        4);
    StringBuilder sb2 = new StringBuilder();
    sb2.append(IP4_RULE_CMD);
    sb2.append(DEL);
    sb2.append("from all uidrange ");
    sb2.append(i + PackageManagerShellCommandDataLoader.STDIN_PATH + i);
    sb2.append(" lookup ");
    sb2.append(LOCAL_NETWORK_TABLE_ID);
    sb2.append(IP_RULE_USB_TETHERING_EXEMPT_PRIORITY);
    sb2.append(DELIMITER);
    sb2.append(IP4_RULE_CMD);
    sb2.append(ADD);
    sb2.append("from all uidrange ");
    sb2.append(i + PackageManagerShellCommandDataLoader.STDIN_PATH + i);
    sb2.append(" lookup ");
    sb2.append(LOCAL_NETWORK_TABLE_ID);
    sb2.append(IP_RULE_USB_TETHERING_EXEMPT_PRIORITY);
    sb2.append(DELIMITER);
    runSingleCommand(sb2.toString());
  }

  public void removeRulesTetherAuth() {
    insertRule(false, TABLE_NAT, KNOX_VPN_NAT_PREROUTING, null, 4);
    insertRule(false, TABLE_FILTER, KNOX_VPN_FILTER_FORWARD, null, 46);
    insertRule(false, TABLE_FILTER, "knox_vpn_tether_exempt", null, 4);
    insertRule(false, TABLE_MANGLE, "knox_vpn_tether_exempt", null, 4);
    runSingleCommand(IP4_RULE_CMD + "del" + IP_RULE_USB_TETHERING_EXEMPT_PRIORITY + DELIMITER);
  }

  public void addRulesForStrongswanP(int i, int i2, String str) {
    if (i == 1) {
      insertRulesForStrongswanP(4, i2, str);
      return;
    }
    if (i == 2 || i == 3) {
      return;
    }
    Log.e(TAG, "addRulesForStrongswanP: unknown interface type has been recieved " + i);
  }

  public void removeRulesForStrongswanP(int i, int i2, String str) {
    if (i == 1) {
      deleteRulesForStrongswanP(4, i2, str);
      return;
    }
    if (i == 2 || i == 3) {
      return;
    }
    Log.e(TAG, "removeRulesForStrongswanP: unknown interface type has been recieved " + i);
  }

  public final void insertRulesForStrongswanP(int i, int i2, String str) {
    int jnigetInterfaceIndex;
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (i == 4) {
      jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str);
      if (jnigetInterfaceIndex <= 0) {
        jnigetInterfaceIndex = jnigetInterfaceIndex(str);
      }
    } else {
      jnigetInterfaceIndex = jnigetInterfaceIndex(str);
    }
    int i3 = jnigetInterfaceIndex;
    if (i3 <= 0) {
      return;
    }
    ArrayList arrayList = new ArrayList();
    String str3 = KNOX_VPN_EXEMPT;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
    arrayList.add(
        new IpRestoreParam(
            str3,
            " -p udp --dport 4500 -m mark --mark " + Integer.toString(i2),
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_EXEMPT,
            " -p udp --dport 4500 -m mark --mark 100",
            "MARK --set-mark ",
            Integer.toString(i2),
            ipRestoreActionType));
    insertRules(true, TABLE_MANGLE, null, arrayList, i);
    runSingleCommand(
        str2
            + DEL
            + IP_RULE_STRONGSWAN_P_PRIORITY
            + DELIMITER
            + str2
            + ADD
            + "from all fwmark "
            + i2
            + " lookup "
            + Integer.toString(i3)
            + IP_RULE_STRONGSWAN_P_PRIORITY
            + DELIMITER);
  }

  public final void deleteRulesForStrongswanP(int i, int i2, String str) {
    int jnigetInterfaceIndex;
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (i == 4) {
      jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str);
      if (jnigetInterfaceIndex <= 0) {
        jnigetInterfaceIndex = jnigetInterfaceIndex(str);
      }
    } else {
      jnigetInterfaceIndex = jnigetInterfaceIndex(str);
    }
    if (jnigetInterfaceIndex <= 0) {
      return;
    }
    String num = Integer.toString(i2);
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT,
            " -p udp --dport 4500 -m mark --mark 100",
            "MARK --set-mark ",
            num,
            ipRestoreActionType),
        i);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT,
            " -p udp --dport 4500 -m mark --mark " + Integer.toString(i2),
            "ACCEPT",
            "",
            ipRestoreActionType),
        i);
    runSingleCommand(str2 + DEL + IP_RULE_STRONGSWAN_P_PRIORITY + DELIMITER);
  }

  public final void insertIpRulesForExemptedUid(int i, String str, String str2, int i2) {
    int jnigetInterfaceIndex;
    if (str2.contains("rmnet") && i == 4) {
      jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str2);
      if (jnigetInterfaceIndex <= 0) {
        Log.d(TAG, "Non Clat Mobile interface is detected while applying ip rules");
        jnigetInterfaceIndex = jnigetInterfaceIndex(str2);
      } else {
        Log.d(TAG, "Clat interface is detected while applying ip rules");
      }
    } else {
      jnigetInterfaceIndex = jnigetInterfaceIndex(str2);
    }
    if (jnigetInterfaceIndex <= 0) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(" lookup ");
    sb.append(Integer.toString(jnigetInterfaceIndex));
    sb.append(IP_RULE_EXEMPT_UID_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final boolean isClatInterface(String str) {
    boolean z;
    if (str != null && str.contains("rmnet")) {
      if (jnigetInterfaceIndex("v4-" + str) > 0) {
        z = true;
        Log.d(TAG, "Check to see if the default interface is clat " + z);
        return z;
      }
    }
    z = false;
    Log.d(TAG, "Check to see if the default interface is clat " + z);
    return z;
  }

  public final String convertToClat(String str) {
    String str2;
    if (str != null) {
      str2 = "v4-" + str;
    } else {
      str2 = null;
    }
    Log.d(TAG, "The default interface is converted to clat and its new name is " + str2);
    return str2;
  }

  public final void deleteIpRulesForExemptedUid(int i, String str, int i2) {
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(IP_RULE_EXEMPT_UID_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void deleteIpRuleForuidSourceSelection(
      int i, List list, String str, StringBuilder sb) {
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String num = Integer.toString(((Integer) it.next()).intValue());
      sb.append(str2);
      sb.append(DEL);
      sb.append("from all uidrange ");
      sb.append(num + PackageManagerShellCommandDataLoader.STDIN_PATH + num);
      sb.append(IP_RULE_PER_APP_PRIORITY);
      sb.append(DELIMITER);
    }
  }

  public final void insertIpRuleForuidRangeSourceSelection(
      int i, String str, String str2, int i2, int i3) {
    if (str2 == null || str2.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    String forwardMark = getForwardMark(str2);
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3);
    sb.append(" lookup ");
    sb.append(forwardMark);
    sb.append(IP_RULE_ADDALL_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void deleteIpRuleForuidRangeSourceSelection(int i, String str, int i2, int i3) {
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3);
    sb.append(IP_RULE_ADDALL_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void insertRulesToAllowAccessToLocalHostWithValidMark(int i, String str, int i2) {
    String forwardMark =
        KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    ArrayList arrayList = new ArrayList();
    String str2 = KNOX_VPN_FILTER_OUTPUT_ACCEPT;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
    arrayList.add(
        new IpRestoreParam(
            str2,
            " -m mark --mark " + forwardMark + " -o lo -p tcp --dport " + i2,
            "ACCEPT",
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_FILTER_OUTPUT_ACCEPT,
            " -m mark --mark " + forwardMark + " -o lo -p udp --dport " + i2,
            "ACCEPT",
            "",
            ipRestoreActionType));
    insertRules(true, TABLE_FILTER, null, arrayList, i);
  }

  public final void deleteRulesToAllowAccessToLocalHostWithValidMark(int i, String str, int i2) {
    String forwardMark =
        KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    String str2 = KNOX_VPN_FILTER_OUTPUT_ACCEPT;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            str2,
            " -m mark --mark " + forwardMark + " -o lo -p tcp --dport " + i2,
            "ACCEPT",
            "",
            ipRestoreActionType),
        i);
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            KNOX_VPN_FILTER_OUTPUT_ACCEPT,
            " -m mark --mark " + forwardMark + " -o lo -p udp --dport " + i2,
            "ACCEPT",
            "",
            ipRestoreActionType),
        i);
  }

  public final void insertRulesToDenyAccessToLocalHost(int i, int i2) {
    ArrayList arrayList = new ArrayList();
    String str = KNOX_VPN_FILTER_OUTPUT_DROP;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
    arrayList.add(
        new IpRestoreParam(str, " -o lo -p tcp --dport " + i2, "DROP", "", ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_FILTER_OUTPUT_DROP,
            " -o lo -p udp --dport " + i2,
            "DROP",
            "",
            ipRestoreActionType));
    insertRules(true, TABLE_FILTER, null, arrayList, i);
  }

  public final void deleteRulesToDenyAccessToLocalHost(int i, int i2) {
    String str = KNOX_VPN_FILTER_OUTPUT_DROP;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(str, " -o lo -p tcp --dport " + i2, "DROP", "", ipRestoreActionType),
        i);
    insertRule(
        false,
        TABLE_FILTER,
        null,
        new IpRestoreParam(
            KNOX_VPN_FILTER_OUTPUT_DROP,
            " -o lo -p udp --dport " + i2,
            "DROP",
            "",
            ipRestoreActionType),
        i);
  }

  public final void insertDropRulesForNoUidPackets(
      int i, int i2, String str, String str2, String str3) {
    try {
      String forwardMark = getForwardMark(str);
      boolean z = DBG;
      if (z) {
        Log.d(TAG, "The current defaultInterface  is " + str3);
      }
      if (z) {
        Log.d(TAG, "The virtual interface value is " + str);
      }
      if (z) {
        Log.d(TAG, "The virtualAddress value is " + str2);
      }
      if (str3 == null || str == null) {
        return;
      }
      if (i2 == 0) {
        if (str2 != null) {
          insertRule(
              true,
              TABLE_MANGLE,
              null,
              new IpRestoreParam(
                  KNOX_VPN_NO_UID,
                  " -s " + str2 + " -p tcp --tcp-flags ALL RST ",
                  "MARK --set-mark " + forwardMark,
                  "",
                  IpRestoreActionType.APPEND),
              i);
          insertRule(
              true,
              TABLE_FILTER,
              null,
              new IpRestoreParam(
                  KNOX_VPN_FILTER_OUTPUT_DROP,
                  " -s " + str2 + " -o " + str3 + " -m mark ! --mark " + forwardMark,
                  "DROP",
                  "",
                  IpRestoreActionType.INSERT),
              i);
          return;
        }
        return;
      }
      if (i2 == 1 && str2 != null) {
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        insertRule(
            false,
            TABLE_MANGLE,
            null,
            new IpRestoreParam(
                KNOX_VPN_NO_UID,
                " -s " + str2 + " -p tcp --tcp-flags ALL RST ",
                "MARK --set-mark " + forwardMark,
                "",
                ipRestoreActionType),
            i);
        insertRule(
            false,
            TABLE_FILTER,
            null,
            new IpRestoreParam(
                KNOX_VPN_FILTER_OUTPUT_DROP,
                " -s " + str2 + " -o " + str3 + " -m mark ! --mark " + forwardMark,
                "DROP",
                "",
                ipRestoreActionType),
            i);
        insertRule(
            false,
            TABLE_FILTER,
            null,
            new IpRestoreParam(
                KNOX_VPN_FILTER_OUTPUT_DROP,
                " -s " + str2 + " -o v4-" + str3 + " -m mark ! --mark " + forwardMark,
                "DROP",
                "",
                ipRestoreActionType),
            i);
      }
    } catch (Exception unused) {
      Log.e(TAG, "Exception occured while trying to get apply dropping rules for knox vpn packets");
    }
  }

  public final void insertExemptRulesForStrongswan(int i, int i2) {
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT, " -m owner --gid-owner 1016", "ACCEPT", "", ipRestoreActionType),
        i);
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT, " -m owner --uid-owner 1016", "ACCEPT", "", ipRestoreActionType),
        i);
  }

  public final void deleteExemptRulesForStrongswan(int i, int i2) {
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT, " -m owner --gid-owner 1016", "ACCEPT", "", ipRestoreActionType),
        i);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT, " -m owner --uid-owner 1016", "ACCEPT", "", ipRestoreActionType),
        i);
  }

  public final void insertIpBlockingRule() {
    Log.d(TAG, "Adding blocking rules");
    runSingleCommand(
        "ip rule add blackhole fwmark 60"
            + BLACKHOLE_PRIORITY
            + DELIMITER
            + "ip -6 rule add blackhole fwmark 60"
            + BLACKHOLE_PRIORITY
            + DELIMITER);
  }

  public final void deleteIpBlockingRule() {
    Log.d(TAG, "deleting blocking rules");
    runSingleCommand(
        "ip rule del blackhole fwmark 60"
            + BLACKHOLE_PRIORITY
            + DELIMITER
            + "ip -6 rule del blackhole fwmark 60"
            + BLACKHOLE_PRIORITY
            + DELIMITER);
  }

  public final void insertExemptRulesForUid(int i, int i2) {
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT,
            " -m owner --uid-owner " + i2,
            "ACCEPT",
            "",
            IpRestoreActionType.INSERT),
        i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().updateInputFilterExemptRules(i2, 1);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void deleteExemptRulesForUid(int i, int i2) {
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_EXEMPT,
            " -m owner --uid-owner " + i2,
            "ACCEPT",
            "",
            IpRestoreActionType.DELETE),
        i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().updateInputFilterExemptRules(i2, 0);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void createIpTableChains() {
    createChainsInTable(TABLE_MANGLE, mangleChains);
    createChainsInTable(TABLE_FILTER, filterChains);
    createChainsInTable(TABLE_NAT, natChains);
  }

  public final void createChainsInTable(String str, Map map) {
    runIptablesRestoreCmd(removeChainsCmd(str, map), 46);
    runIptablesRestoreCmd(createChainsCmd(str, map), 46);
  }

  public final List createChainsCmd(String str, Map map) {
    ArrayList arrayList = new ArrayList();
    Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
    }
    List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
    for (Map.Entry entry : map.entrySet()) {
      Iterator it2 = ((List) entry.getValue()).iterator();
      while (it2.hasNext()) {
        createTableHeaderCmd.add(
            parseIptablesRestoreCmd(
                new IpRestoreParam(
                    (String) entry.getKey(),
                    "",
                    (String) it2.next(),
                    "",
                    IpRestoreActionType.INSERT)));
      }
    }
    createTableHeaderCmd.add(COMMIT_CMD);
    return createTableHeaderCmd;
  }

  public final List removeChainsCmd(String str, Map map) {
    ArrayList arrayList = new ArrayList();
    Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
    }
    List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
    for (Map.Entry entry : map.entrySet()) {
      Iterator it2 = ((List) entry.getValue()).iterator();
      while (it2.hasNext()) {
        createTableHeaderCmd.add(
            parseIptablesRestoreCmd(
                new IpRestoreParam(
                    (String) entry.getKey(),
                    "",
                    (String) it2.next(),
                    "",
                    IpRestoreActionType.DELETE)));
      }
    }
    Iterator it3 = map.entrySet().iterator();
    while (it3.hasNext()) {
      Iterator it4 = ((List) ((Map.Entry) it3.next()).getValue()).iterator();
      while (it4.hasNext()) {
        createTableHeaderCmd.add(
            parseIptablesRestoreCmd(
                new IpRestoreParam(
                    (String) it4.next(), null, null, null, IpRestoreActionType.REMOVE_CHAIN)));
      }
    }
    createTableHeaderCmd.add(COMMIT_CMD);
    return createTableHeaderCmd;
  }

  public final void runSingleCommand(String str) {
    String[] splitCmds;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (DBG) {
          Log.d(TAG, "Current time before applying the ip commands");
        }
        splitCmds = NetdHelper.splitCmds(str);
      } catch (RemoteException e) {
        Log.e(TAG, "runSingleCommand error " + e.getMessage());
      }
      if (splitCmds == null) {
        Log.e(TAG, "Error splitting commands");
        return;
      }
      for (String str2 : splitCmds) {
        getNetworkManagementService()
            .runKnoxRulesCommand(
                NetdHelper.getCmdNumber(str2).intValue(), NetdHelper.getCmdParams(str2));
      }
      if (DBG) {
        Log.d(TAG, "Current time after applying the ip commands");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void insertIpRules(int i, String str, String str2) {
    if (DBG) {
      Log.d(TAG, "add iptable rule : ");
    }
    if (str2 == null || str2.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      Log.e(TAG, "add iptable rule : Invalid interface name received:" + str2);
      return;
    }
    StringBuilder sb = new StringBuilder();
    String str3 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    String forwardMark = getForwardMark(str2);
    sb.append(str3);
    sb.append(str);
    sb.append(" fwmark ");
    sb.append(forwardMark);
    sb.append(" table ");
    sb.append(forwardMark);
    sb.append(IP_RULE_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void insertIpRoute(int i, String str, String str2) {
    if (DBG) {
      Log.d(TAG, "add iptable route : ");
    }
    if (str2 == null || str2.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      Log.e(TAG, "add iptable route : Invalid interface name received:" + str2);
      return;
    }
    StringBuilder sb = new StringBuilder();
    String str3 = i == 6 ? IP6_ROUTE_CMD : IP4_ROUTE_CMD;
    String forwardMark = getForwardMark(str2);
    if (str3 == IP4_ROUTE_CMD) {
      sb.append(str3);
      sb.append(str);
      sb.append(" 0.0.0.0/0 table ");
      sb.append(forwardMark);
      sb.append(" dev ");
      sb.append(str2);
      sb.append(" prio 1");
      sb.append(DELIMITER);
    } else if (str3 == IP6_ROUTE_CMD) {
      sb.append(IP6_ROUTE_CMD);
      sb.append(str);
      sb.append(" ::/0 table ");
      sb.append(forwardMark);
      sb.append(" dev ");
      sb.append(str2);
      sb.append(" prio 1");
      sb.append(DELIMITER);
    }
    runSingleCommand(sb.toString());
  }

  public final void insertIpRuleForUidList(int i, List list, String str, StringBuilder sb) {
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (str == null || str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    String forwardMark = getForwardMark(str);
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String num = Integer.toString(((Integer) it.next()).intValue());
      sb.append(str2);
      sb.append(ADD);
      sb.append("from all uidrange ");
      sb.append(num + PackageManagerShellCommandDataLoader.STDIN_PATH + num);
      sb.append(" lookup ");
      sb.append(forwardMark);
      sb.append(IP_RULE_PER_APP_PRIORITY);
      sb.append(DELIMITER);
    }
  }

  public final void deleteNatRules(int i, String str) {
    if (KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str)) {
      return;
    }
    insertRule(
        false,
        TABLE_NAT,
        null,
        new IpRestoreParam(
            "POSTROUTING", " -o " + str, "MASQUERADE", "", IpRestoreActionType.DELETE),
        i);
  }

  public final void insertNatRules(int i, String str) {
    if (KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str)) {
      return;
    }
    insertRule(
        true,
        TABLE_NAT,
        null,
        new IpRestoreParam(
            "POSTROUTING", " -o " + str, "MASQUERADE", "", IpRestoreActionType.APPEND),
        i);
  }

  public final void insertRulesToAcceptProxyPackets(int i, String str, String str2, int i2) {
    String forwardMark = getForwardMark(str2);
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_PROXY_ACCEPT,
            " -m owner --uid-owner " + Integer.toString(i2) + " -m mark --mark " + forwardMark,
            "ACCEPT",
            "",
            IpRestoreActionType.APPEND),
        i);
  }

  public final void deleteRulesToAcceptProxyPackets(int i, String str, String str2, int i2) {
    String forwardMark = getForwardMark(str2);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_PROXY_ACCEPT,
            " -m owner --uid-owner " + Integer.toString(i2) + " -m mark --mark " + forwardMark,
            "ACCEPT",
            "",
            IpRestoreActionType.DELETE),
        i);
  }

  public final void insertRulesToAcceptIncomingPackets(int i, String str, String str2) {
    if (str2 == null || str2.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    String forwardMark = getForwardMark(str2);
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all iif ");
    sb.append(str2);
    sb.append(" lookup ");
    sb.append(forwardMark);
    sb.append(IP_RULE_INCOMING_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void deleteRulesToAcceptIncomingPackets(int i, String str, String str2) {
    if (str2 == null || str2.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      return;
    }
    String forwardMark = getForwardMark(str2);
    StringBuilder sb = new StringBuilder();
    sb.append(i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD);
    sb.append(str);
    sb.append("from all iif ");
    sb.append(str2);
    sb.append(" lookup ");
    sb.append(forwardMark);
    sb.append(IP_RULE_INCOMING_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void insertExemptRulesForDownloadManagerUid(int i, int i2, String str) {
    int jnigetInterfaceIndex;
    int i3;
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (i == 4) {
      jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str);
      if (jnigetInterfaceIndex <= 0) {
        Log.d(TAG, "Non Clat interface is detected while applying ip rules for download uid");
        jnigetInterfaceIndex = jnigetInterfaceIndex(str);
      } else {
        Log.d(TAG, "Clat interface is detected while applying ip rules for download uid");
      }
    } else {
      jnigetInterfaceIndex = jnigetInterfaceIndex(str);
    }
    if (jnigetInterfaceIndex <= 0) {
      return;
    }
    try {
      i3 = getVpnManagerService().getNetIdforActiveDefaultInterface();
    } catch (RemoteException unused) {
      i3 = 0;
    }
    if (i3 <= 0) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(str2);
    sb.append(DEL);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(IP_RULE_DOWNLOAD_PRIORITY);
    sb.append(DELIMITER);
    sb.append(str2);
    sb.append(ADD);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(" fwmark ");
    sb.append(i3);
    sb.append(" lookup ");
    sb.append(Integer.toString(jnigetInterfaceIndex));
    sb.append(IP_RULE_DOWNLOAD_PRIORITY);
    sb.append(DELIMITER);
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_MANGLE_OUTPUT_DOWNLOAD_EXEMPT,
            " -m owner --uid-owner " + i2 + " -m mark --mark " + i3,
            "ACCEPT",
            "",
            IpRestoreActionType.INSERT),
        i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().updateInputFilterExemptRules(i2, 1);
    } catch (RemoteException unused2) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    runSingleCommand(sb.toString());
  }

  public final void deleteExemptRulesForDownloadManagerUid(int i, int i2) {
    String str = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    StringBuilder sb = new StringBuilder();
    insertRule(false, TABLE_MANGLE, KNOX_VPN_MANGLE_OUTPUT_DOWNLOAD_EXEMPT, null, i);
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
    sb.append(DEL);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(IP_RULE_DOWNLOAD_PRIORITY);
    sb.append(DELIMITER);
    runSingleCommand(sb.toString());
  }

  public final void insertRulesToExemptCaptivePortalQueries(int i, int i2, String str) {
    int jnigetInterfaceIndex;
    if (str == null || i2 == -1) {
      return;
    }
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (i == 4) {
      jnigetInterfaceIndex = jnigetInterfaceIndex("v4-" + str);
      if (jnigetInterfaceIndex <= 0) {
        Log.d(TAG, "Non Clat interface is detected while applying ip rules for download uid");
        jnigetInterfaceIndex = jnigetInterfaceIndex(str);
      } else {
        Log.d(TAG, "Clat interface is detected while applying ip rules for download uid");
      }
    } else {
      jnigetInterfaceIndex = jnigetInterfaceIndex(str);
    }
    if (jnigetInterfaceIndex <= 0) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(str2);
    sb.append(DEL);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(IP_RULE_CAPTIVE_PORTAL_PRIORITY);
    sb.append(DELIMITER);
    sb.append(str2);
    sb.append(ADD);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(" lookup ");
    sb.append(Integer.toString(jnigetInterfaceIndex));
    sb.append(IP_RULE_CAPTIVE_PORTAL_PRIORITY);
    sb.append(DELIMITER);
    insertRule(
        true,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_MANGLE_CAPTIVE_PORTAL_EXEMPT,
            " -m owner --uid-owner " + i2,
            "ACCEPT",
            "",
            IpRestoreActionType.INSERT),
        i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().updateInputFilterExemptRules(i2, 1);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    runSingleCommand(sb.toString());
  }

  public final void deleteRulesToExemptCaptivePortalQueries(int i, int i2) {
    String str = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append(DEL);
    sb.append("from all uidrange ");
    sb.append(i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2);
    sb.append(IP_RULE_CAPTIVE_PORTAL_PRIORITY);
    sb.append(DELIMITER);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_MANGLE_CAPTIVE_PORTAL_EXEMPT,
            " -m owner --uid-owner " + i2,
            "ACCEPT",
            "",
            IpRestoreActionType.DELETE),
        i);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().updateInputFilterExemptRules(i2, 0);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    runSingleCommand(sb.toString());
  }

  public final void insertRulesForNoUidPackets(int i, String str, String str2) {
    String str3 = str2 + "_act";
    String forwardMark =
        KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    ArrayList arrayList = new ArrayList();
    if (str.equalsIgnoreCase(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      String str4 = "knox-netId-" + forwardMark;
      insertRule(
          false,
          TABLE_MANGLE,
          str4,
          new IpRestoreParam(str4, "", "", "", IpRestoreActionType.REMOVE_CHAIN),
          i);
      IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
      arrayList.add(
          new IpRestoreParam(
              str4, "", "CONNMARK --set-mark " + forwardMark, "", ipRestoreActionType));
      arrayList.add(
          new IpRestoreParam(
              str4,
              " -m connmark --mark " + forwardMark,
              "CONNMARK --restore-mark",
              "",
              ipRestoreActionType));
      arrayList.add(
          new IpRestoreParam(
              str4, " -m connmark --mark " + forwardMark, "ACCEPT", "", ipRestoreActionType));
      insertRules(false, TABLE_MANGLE, Collections.singletonList(str4), arrayList, i);
      arrayList.clear();
      String str5 = KNOX_VPN_NO_UID;
      IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.INSERT;
      arrayList.add(
          new IpRestoreParam(
              str5, " -m connmark --mark " + forwardMark, str4, "", ipRestoreActionType2));
      arrayList.add(
          new IpRestoreParam(
              KNOX_VPN_NO_UID, " -m mark --mark " + forwardMark, str4, "", ipRestoreActionType2));
      insertRules(true, TABLE_MANGLE, null, arrayList, i);
      return;
    }
    String str6 = KNOX_VPN_NO_UID;
    IpRestoreActionType ipRestoreActionType3 = IpRestoreActionType.INSERT;
    arrayList.add(
        new IpRestoreParam(
            str6, " -m connmark --mark " + forwardMark, str3, "", ipRestoreActionType3));
    arrayList.add(
        new IpRestoreParam(
            KNOX_VPN_NO_UID, " -m mark --mark " + forwardMark, str3, "", ipRestoreActionType3));
    insertRules(true, TABLE_MANGLE, null, arrayList, i);
  }

  public final void deleteRulesForNoUidPackets(int i, String str, String str2) {
    String str3 = str2 + "_act";
    String forwardMark =
        KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    if (str.equalsIgnoreCase(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
      IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
      insertRule(
          false,
          TABLE_MANGLE,
          null,
          new IpRestoreParam(
              KNOX_VPN_NO_UID,
              " -m connmark --mark " + forwardMark,
              "knox-netId-" + forwardMark,
              "",
              ipRestoreActionType),
          i);
      insertRule(
          false,
          TABLE_MANGLE,
          null,
          new IpRestoreParam(
              KNOX_VPN_NO_UID,
              " -m mark --mark " + forwardMark,
              "knox-netId-" + forwardMark,
              "",
              ipRestoreActionType),
          i);
      insertRule(
          false,
          TABLE_MANGLE,
          "knox-netId-" + forwardMark,
          new IpRestoreParam(
              "knox-netId-" + forwardMark, null, null, null, IpRestoreActionType.REMOVE_CHAIN),
          i);
      return;
    }
    String str4 = KNOX_VPN_NO_UID;
    IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.DELETE;
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            str4, " -m connmark --mark " + forwardMark, str3, "", ipRestoreActionType2),
        i);
    insertRule(
        false,
        TABLE_MANGLE,
        null,
        new IpRestoreParam(
            KNOX_VPN_NO_UID, " -m mark --mark " + forwardMark, str3, "", ipRestoreActionType2),
        i);
  }

  public final void deleteIpRules(int i, List list, String str, StringBuilder sb) {
    String str2 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (!KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str)) {
      getForwardMark(str);
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String num = Integer.toString(((Integer) it.next()).intValue());
      if (str != null && !str.contains(KnoxVpnConstants.BLOCK_APP_TRAFFIC)) {
        sb.append(str2);
        sb.append(DEL);
        sb.append("from all uidrange ");
        sb.append(num + PackageManagerShellCommandDataLoader.STDIN_PATH + num);
        sb.append(IP_RULE_PER_APP_PRIORITY);
        sb.append(DELIMITER);
      }
    }
  }

  public final String getForwardMark(String str) {
    if (str == null || str.length() < 4) {
      return null;
    }
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      String interfaceName = vpnProfileInfo.getInterfaceName();
      if (interfaceName != null && interfaceName.equalsIgnoreCase(str)) {
        int netId = vpnProfileInfo.getNetId();
        if (netId != 0) {
          return Integer.toString(netId);
        }
        return null;
      }
    }
    return null;
  }

  public final void showLog(String str) {
    StringTokenizer stringTokenizer = new StringTokenizer(str, DELIMITER);
    while (stringTokenizer.hasMoreTokens()) {
      Log.d(TAG, stringTokenizer.nextToken());
    }
  }

  public final boolean checknterface(String str) {
    if (DBG) {
      Log.e(TAG, "checknterface() : interfaceName : " + str);
    }
    if (str == null || KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str)) {
      return true;
    }
    return str.matches("[a-zA-Z0-9_]+");
  }

  public final void insertMarkingRulesForFilteredPackages(int i, String str, String str2) {
    String str3 = str2 + "_act";
    String forwardMark =
        KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    ArrayList arrayList = new ArrayList();
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
    arrayList.add(
        new IpRestoreParam(
            str3, "", "CONNMARK --set-mark " + forwardMark, "", ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            str3,
            " -m connmark --mark " + forwardMark,
            "CONNMARK --restore-mark",
            "",
            ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(
            str3, " -m connmark --mark " + forwardMark, "ACCEPT", "", ipRestoreActionType));
    insertRules(false, TABLE_MANGLE, Collections.singletonList(str3), arrayList, i);
  }

  public final void insertRulesForUsbTethering(
      int i, String str, String[] strArr, String str2, List list) {
    String str3 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    String str4 = i == 6 ? IP6_ROUTE_CMD : IP4_ROUTE_CMD;
    String forwardMark = getForwardMark(str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        getNetworkManagementService()
            .setDnsForwardersForKnoxVpn(Integer.parseInt(forwardMark), strArr);
      } catch (Exception e) {
        Log.e(TAG, "setDnsForwardersForKnoxVpn: " + Log.getStackTraceString(e));
      }
      ArrayList arrayList = new ArrayList();
      String str5 = KNOX_VPN_FILTER_FORWARD;
      IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
      arrayList.add(new IpRestoreParam(str5, " -i " + str2, "ACCEPT", "", ipRestoreActionType));
      arrayList.add(
          new IpRestoreParam(
              KNOX_VPN_FILTER_FORWARD, " -o " + str2, "ACCEPT", "", ipRestoreActionType));
      insertRules(
          false, TABLE_FILTER, Collections.singletonList(KNOX_VPN_FILTER_FORWARD), arrayList, i);
      StringBuilder sb = new StringBuilder();
      sb.append(str3);
      sb.append(" del from all iif ");
      sb.append(str2);
      sb.append(" lookup ");
      sb.append(forwardMark);
      sb.append(IP_RULE_USB_TETHERING_PRIORITY);
      sb.append(DELIMITER);
      sb.append(str3);
      sb.append(" add from all iif ");
      sb.append(str2);
      sb.append(" lookup ");
      sb.append(forwardMark);
      sb.append(IP_RULE_USB_TETHERING_PRIORITY);
      sb.append(DELIMITER);
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str6 = (String) it.next();
        sb.append(str4);
        sb.append(DEL);
        sb.append(str6);
        sb.append(" dev ");
        sb.append(str2);
        sb.append(" scope link metric 1 ");
        sb.append("table " + forwardMark);
        sb.append(DELIMITER);
        sb.append(str4);
        sb.append(ADD);
        sb.append(str6);
        sb.append(" dev ");
        sb.append(str2);
        sb.append(" scope link metric 1 ");
        sb.append("table " + forwardMark);
        sb.append(DELIMITER);
      }
      runSingleCommand(sb.toString());
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void deleteRulesForUsbTethering(int i, String str) {
    String str2;
    String str3 = i == 6 ? IP6_RULE_CMD : IP4_RULE_CMD;
    if (str != null) {
      str2 = KnoxVpnConstants.BLOCK_APP_TRAFFIC.equals(str) ? "60" : getForwardMark(str);
    } else {
      str2 = null;
    }
    insertRule(false, TABLE_FILTER, KNOX_VPN_FILTER_FORWARD, null, 46);
    StringBuilder sb = new StringBuilder();
    if (str2 != null && str2 != "60") {
      sb.append(str3);
      sb.append(" del from all lookup ");
      sb.append(str2);
      sb.append(IP_RULE_USB_TETHERING_PRIORITY);
      sb.append(DELIMITER);
    }
    runSingleCommand(sb.toString());
  }

  public final void insertRulesForDroppingTetherPackets(int i, String str) {
    ArrayList arrayList = new ArrayList();
    String str2 = KNOX_VPN_FILTER_FORWARD;
    IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
    arrayList.add(new IpRestoreParam(str2, " -i " + str, "DROP", "", ipRestoreActionType));
    arrayList.add(
        new IpRestoreParam(KNOX_VPN_FILTER_FORWARD, " -o " + str, "DROP", "", ipRestoreActionType));
    insertRules(
        true, TABLE_FILTER, Collections.singletonList(KNOX_VPN_FILTER_FORWARD), arrayList, i);
  }

  public final void deleteRulesForDroppingTetherPackets() {
    insertRule(false, TABLE_FILTER, KNOX_VPN_FILTER_FORWARD, null, 46);
  }

  public final String parseIptablesRestoreCmd(IpRestoreParam ipRestoreParam) {
    StringBuilder sb = new StringBuilder();
    if (ipRestoreParam.actionType == null) {
      return "";
    }
    int i = AbstractC15061.f1698x9fa261c3[ipRestoreParam.actionType.ordinal()];
    if (i == 1) {
      sb.append("-A ");
      sb.append(ipRestoreParam.actionChain);
      sb.append(ipRestoreParam.firstParam);
      sb.append(" -j ");
      sb.append(ipRestoreParam.jumpChain);
      sb.append(ipRestoreParam.secondParam);
      sb.append(DELIMITER_IP_RESTORE);
    } else if (i == 2) {
      sb.append("-I ");
      sb.append(ipRestoreParam.actionChain);
      sb.append(ipRestoreParam.firstParam);
      sb.append(" -j ");
      sb.append(ipRestoreParam.jumpChain);
      sb.append(ipRestoreParam.secondParam);
      sb.append(DELIMITER_IP_RESTORE);
    } else if (i == 3) {
      sb.append("-D ");
      sb.append(ipRestoreParam.actionChain);
      sb.append(ipRestoreParam.firstParam);
      sb.append(" -j ");
      sb.append(ipRestoreParam.jumpChain);
      sb.append(ipRestoreParam.secondParam);
      sb.append(DELIMITER_IP_RESTORE);
    } else if (i != 4) {
      sb = null;
    } else {
      sb.append("-X ");
      sb.append(ipRestoreParam.actionChain);
      sb.append(DELIMITER_IP_RESTORE);
    }
    if (sb != null) {
      return sb.toString();
    }
    return null;
  }

  /* renamed from: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper$1 */
  public abstract /* synthetic */ class AbstractC15061 {

    /* renamed from: $SwitchMap$com$android$server$enterprise$vpn$knoxvpn$KnoxVpnFirewallHelper$IpRestoreActionType */
    public static final /* synthetic */ int[] f1698x9fa261c3;

    static {
      int[] iArr = new int[IpRestoreActionType.values().length];
      f1698x9fa261c3 = iArr;
      try {
        iArr[IpRestoreActionType.APPEND.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1698x9fa261c3[IpRestoreActionType.INSERT.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        f1698x9fa261c3[IpRestoreActionType.DELETE.ordinal()] = 3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        f1698x9fa261c3[IpRestoreActionType.REMOVE_CHAIN.ordinal()] = 4;
      } catch (NoSuchFieldError unused4) {
      }
    }
  }

  public final void insertRule(
      boolean z, String str, String str2, IpRestoreParam ipRestoreParam, int i) {
    insertRules(
        z, str, Collections.singletonList(str2), Collections.singletonList(ipRestoreParam), i);
  }

  public final void insertRules(boolean z, String str, List list, List list2, int i) {
    List arrayList = new ArrayList();
    arrayList.addAll(createTableHeaderCmd(str, list));
    if (z) {
      Iterator it = list2.iterator();
      while (it.hasNext()) {
        IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
        if (ipRestoreParam != null) {
          IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
          List parseRuleCmd = parseRuleCmd(true, arrayList, ipRestoreParam);
          parseRuleCmd.add(COMMIT_CMD);
          runIptablesRestoreCmd(parseRuleCmd, i);
          ipRestoreParam.actionType = ipRestoreActionType;
        }
      }
    }
    if (list2 != null) {
      arrayList = parseRuleCmd(arrayList, list2);
    }
    arrayList.add(COMMIT_CMD);
    runIptablesRestoreCmd(arrayList, i);
  }

  public final List parseRuleCmd(boolean z, List list, IpRestoreParam ipRestoreParam) {
    ArrayList arrayList = new ArrayList();
    if (z) {
      ipRestoreParam.actionType = IpRestoreActionType.DELETE;
    }
    arrayList.addAll(list);
    arrayList.add(parseIptablesRestoreCmd(ipRestoreParam));
    return arrayList;
  }

  public final List parseRuleCmd(List list, List list2) {
    ArrayList arrayList = new ArrayList();
    arrayList.addAll(list);
    Iterator it = list2.iterator();
    while (it.hasNext()) {
      IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
      if (ipRestoreParam != null) {
        arrayList.add(parseIptablesRestoreCmd(ipRestoreParam));
      }
    }
    return arrayList;
  }

  public final List createTableHeaderCmd(String str, List list) {
    StringBuilder sb = new StringBuilder();
    ArrayList arrayList = new ArrayList();
    sb.append(str);
    sb.append(DELIMITER_IP_RESTORE);
    arrayList.add(sb.toString());
    if (list != null) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str2 = (String) it.next();
        if (str2 != null) {
          sb.setLength(0);
          sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
          sb.append(str2);
          sb.append(" -");
          sb.append(DELIMITER_IP_RESTORE);
          arrayList.add(sb.toString());
        }
      }
    }
    return arrayList;
  }

  public void runIptablesRestoreCmd(List list, int i) {
    runShellCommand(i, String.join("", list));
  }

  public final synchronized boolean runShellCommand(int i, String str) {
    String runKnoxFirewallRulesCommand;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        runKnoxFirewallRulesCommand =
            getNetworkManagementService().runKnoxFirewallRulesCommand(i, str);
      } catch (Exception e) {
        Log.e(TAG, "Failed to run cmd: " + e.getMessage());
      }
      if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
        Log.e(
            TAG,
            "Failed to run command. Result=" + runKnoxFirewallRulesCommand + "\ncommand=" + str);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
      return true;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void clearEbpfMap(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      getNetworkManagementService().clearEbpfMap(i);
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final int getUIDForPackage(int i, String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int i2 = -1;
    try {
      try {
        ApplicationInfo applicationInfo =
            AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
        if (applicationInfo != null) {
          i2 = applicationInfo.uid;
        }
      } catch (Exception e) {
        Log.d(TAG, "Exception in getUIDForPackage : " + Log.getStackTraceString(e));
      }
      return i2;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final int getNonChainedVendoUid(int i) {
    for (VpnProfileInfo vpnProfileInfo : this.vpnConfig.getProfileEntries()) {
      if (vpnProfileInfo.getChainingEnabled() == 0
          && UserHandle.getUserId(i) == UserHandle.getUserId(vpnProfileInfo.getVendorUid())) {
        return vpnProfileInfo.getVendorUid();
      }
    }
    return -1;
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
          Iterator it = profileEntry.getExemptPackageList().iterator();
          while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (defaultRouteAppUidList.contains(Integer.valueOf(intValue))) {
              Log.d(TAG, "getDefaultRouteApps exempted uid " + intValue);
              arrayList.add(Integer.valueOf(intValue));
            }
          }
          int vendorUid = profileEntry.getVendorUid();
          if (profileEntry.getVendorPkgName().contains("com.samsung.sVpn")) {
            vendorUid = 1016;
          }
          Log.d(TAG, "getDefaultRouteApps: vpn uid " + vendorUid);
          arrayList.add(Integer.valueOf(vendorUid));
          if (profileEntry.getChainingEnabled() == 1
              && (nonChainedVendoUid2 = getNonChainedVendoUid(profileEntry.getVendorUid())) > 0
              && UserHandle.getUserId(nonChainedVendoUid2) == i) {
            Log.d(TAG, "getDefaultRouteApps: nonChainedVendorUid  " + nonChainedVendoUid2);
            arrayList.add(Integer.valueOf(nonChainedVendoUid2));
          }
          for (Integer num : KnoxVpnConstants.AID_EXEMPT_LIST) {
            arrayList.add(Integer.valueOf(num.intValue()));
          }
        } else {
          if (profileEntry.getVendorPkgName().contains("com.samsung.sVpn")) {
            return arrayList;
          }
          int vendorUid2 = profileEntry.getVendorUid();
          if (UserHandle.getUserId(vendorUid2) == i) {
            Log.d(TAG, "getDefaultRouteApps: vendor uid " + vendorUid2);
            arrayList.add(Integer.valueOf(vendorUid2));
          }
          if (profileEntry.getChainingEnabled() == 1
              && (nonChainedVendoUid = getNonChainedVendoUid(profileEntry.getVendorUid())) > 0
              && UserHandle.getUserId(nonChainedVendoUid) == i) {
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

  public final String getProfileOwningUserId(int i) {
    Collection<VpnProfileInfo> profileEntries = this.vpnConfig.getProfileEntries();
    if (profileEntries == null) {
      return null;
    }
    for (VpnProfileInfo vpnProfileInfo : profileEntries) {
      if (vpnProfileInfo != null) {
        for (VpnPackageInfo vpnPackageInfo : vpnProfileInfo.getPackageList()) {
          if (vpnPackageInfo.getUid() == -2 && i == vpnPackageInfo.getCid()) {
            return vpnProfileInfo.getProfileName();
          }
        }
      }
    }
    return null;
  }

  public List getDefaultRouteAppUidList() {
    ArrayList arrayList = new ArrayList();
    arrayList.add(1000);
    arrayList.add(1073);
    arrayList.add(1001);
    int uIDForPackage = getUIDForPackage(0, "com.samsung.android.messaging");
    if (uIDForPackage != -1) {
      arrayList.add(Integer.valueOf(uIDForPackage));
    }
    return arrayList;
  }

  public static Range createUidRangeForUser(int i) {
    return new Range(Integer.valueOf(i * 100000), Integer.valueOf(((i + 1) * 100000) - 1));
  }
}
