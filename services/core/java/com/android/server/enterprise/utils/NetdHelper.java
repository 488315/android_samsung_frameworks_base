package com.android.server.enterprise.utils;

import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class NetdHelper {
  public static final Map allowedCommands = initMap();
  public static int CMD_INVALID = -1;
  public static String CMD_DELIMITER = KnoxVpnFirewallHelper.DELIMITER;
  public static String PARAM_DELIMITER = "\\s+";
  public static String CMD_PATH = "/system/bin/";

  public static Map initMap() {
    HashMap hashMap = new HashMap();
    hashMap.put(0, KnoxVpnFirewallHelper.IP4_RULE_CMD);
    hashMap.put(1, KnoxVpnFirewallHelper.IP6_RULE_CMD);
    hashMap.put(2, KnoxVpnFirewallHelper.IP4_ROUTE_CMD);
    hashMap.put(3, KnoxVpnFirewallHelper.IP6_ROUTE_CMD);
    return Collections.unmodifiableMap(hashMap);
  }

  public static String[] splitCmds(String str) {
    if (str == null || str.isEmpty()) {
      return null;
    }
    return str.trim().split(CMD_DELIMITER);
  }

  public static Integer getCmdNumber(String str) {
    if (str != null && !str.isEmpty()) {
      String trim = str.replace(CMD_PATH, "").trim();
      for (Map.Entry entry : allowedCommands.entrySet()) {
        if (trim.startsWith((String) entry.getValue())) {
          return (Integer) entry.getKey();
        }
      }
    }
    return Integer.valueOf(CMD_INVALID);
  }

  public static String[] getCmdParams(String str) {
    int intValue;
    if (str == null || str.isEmpty() || (intValue = getCmdNumber(str).intValue()) == CMD_INVALID) {
      return null;
    }
    return str.replace(CMD_PATH, "")
        .replace((CharSequence) allowedCommands.get(Integer.valueOf(intValue)), "")
        .trim()
        .split(PARAM_DELIMITER);
  }
}
