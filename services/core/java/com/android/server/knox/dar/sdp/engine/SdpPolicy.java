package com.android.server.knox.dar.sdp.engine;

import android.text.TextUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.sdp.core.SdpDomain;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SdpPolicy implements Serializable {
  private String mAlias;
  private SdpDomain mOwner;
  private List mPrivilegedApps;

  public SdpPolicy(SdpEngineInfo sdpEngineInfo, SdpDomain sdpDomain, List list) {
    this.mAlias = sdpEngineInfo.getAlias();
    this.mOwner = sdpDomain;
    this.mPrivilegedApps = list;
  }

  public SdpDomain getOwner() {
    return this.mOwner;
  }

  public List getPrivilegedApps() {
    return this.mPrivilegedApps;
  }

  public boolean addPrivilegedApp(SdpDomain sdpDomain) {
    String alias = sdpDomain.getAlias();
    String packageName = sdpDomain.getPackageName();
    if (TextUtils.isEmpty(alias) || !alias.equals(this.mAlias)) {
      return false;
    }
    try {
      Iterator it = this.mPrivilegedApps.iterator();
      while (it.hasNext()) {
        if (packageName.equals(((SdpDomain) it.next()).getPackageName())) {
          return false;
        }
      }
      return this.mPrivilegedApps.add(sdpDomain);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean removePrivilegedApp(SdpDomain sdpDomain) {
    String alias = sdpDomain.getAlias();
    String packageName = sdpDomain.getPackageName();
    if (TextUtils.isEmpty(alias) || !alias.equals(this.mAlias)) {
      return false;
    }
    try {
      for (SdpDomain sdpDomain2 : this.mPrivilegedApps) {
        if (packageName.equals(sdpDomain2.getPackageName())) {
          return this.mPrivilegedApps.remove(sdpDomain2);
        }
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("SdpPolicy {\n");
    sb.append("Alias:" + this.mAlias);
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    if (this.mOwner != null) {
      sb.append("mOwner:" + this.mOwner.toString());
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    Iterator it = this.mPrivilegedApps.iterator();
    while (it.hasNext()) {
      sb.append("mPrivilegedApps:" + ((SdpDomain) it.next()).toString());
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    sb.append("}");
    return sb.toString();
  }
}
