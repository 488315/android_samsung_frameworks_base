package com.android.server;

import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: ResetReasonCode.java */
/* loaded from: classes.dex */
public abstract class CommonNativeResetReasonCode extends ResetReasonCode {
  @Override // com.android.server.ResetReasonCode
  public String addSuffix() {
    return "sys_native";
  }

  @Override // com.android.server.ResetReasonCode
  public List addCauseStackFromContexts(List list) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    String str = "";
    String str2 = str;
    while (it.hasNext()) {
      String str3 = (String) it.next();
      if (str3.startsWith("signal")) {
        str = str + "," + str3.split(",")[0];
      } else if (str3.startsWith("Abort message") || str3.startsWith("#")) {
        str2 = str2 + str3 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
      } else if (str3.startsWith("pid: ") && str3.indexOf("  >>>") != -1) {
        String trim =
            str3.substring(str3.indexOf(">>>"), str3.length())
                .replace(">>>", "")
                .replace("<<<", "")
                .trim();
        String[] split =
            (str3.substring(0, str3.indexOf("  >>>")).replace(XmlUtils.STRING_ARRAY_SEPARATOR, ",")
                    + "("
                    + trim
                    + ")")
                .split(", ");
        if (split.length >= 6) {
          str = str + "," + split[5];
        }
      }
    }
    arrayList.add(str);
    arrayList.add(str2);
    return arrayList;
  }
}
