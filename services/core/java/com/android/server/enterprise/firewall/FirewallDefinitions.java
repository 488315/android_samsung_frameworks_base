package com.android.server.enterprise.firewall;

import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class FirewallDefinitions {
  public static final Map EXEMPT_PACKAGE_LIST;
  public static final Pattern PATTERN_NUMERAL_ENDING_SUFIX = Pattern.compile("[0-9]*$");

  public enum Table {
    FILTER,
    NAT
  }

  static {
    HashMap hashMap = new HashMap();
    hashMap.put(KnoxVpnFirewallHelper.NETD_SERVICE_NAME, 1051);
    EXEMPT_PACKAGE_LIST = Collections.unmodifiableMap(hashMap);
  }
}
