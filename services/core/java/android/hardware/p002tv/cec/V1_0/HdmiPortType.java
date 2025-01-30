package android.hardware.p002tv.cec.V1_0;

import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes.dex */
public abstract class HdmiPortType {
  public static final String toString(int i) {
    if (i == 0) {
      return KnoxVpnFirewallHelper.INPUT_CHAIN;
    }
    if (i == 1) {
      return KnoxVpnFirewallHelper.OUTPUT_CHAIN;
    }
    return "0x" + Integer.toHexString(i);
  }
}
