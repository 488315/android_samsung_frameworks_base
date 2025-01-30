package com.android.server.connectivity;

import android.net.INetd;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes.dex */
public class VpnRules {
    public INetd mNetdService;
    public IOemNetd mOemNetd;

    public VpnRules() {
        if (this.mNetdService == null) {
            bindNetdNativeService();
        }
    }

    public void createVpnPostroutingChain() {
        Log.d("VpnRules", "createVpnPostroutingChain");
        runVpnRulesCommand(4, "*mangle\n-N vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-D POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-A POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
    }

    public void deleteVpnPostroutingChain() {
        Log.d("VpnRules", "deleteVpnPostroutingChain");
        runVpnRulesCommand(4, "*mangle\n-D POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-F vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-X vpn_POSTROUTING\nCOMMIT\n");
    }

    public void addTcpmssClampRule() {
        Log.d("VpnRules", "addTcpmssClampRule");
        runVpnRulesCommand(4, "*mangle\n-A vpn_POSTROUTING -p tcp --tcp-flag SYN,RST SYN -j TCPMSS --clamp-mss-to-pmtu\nCOMMIT\n");
    }

    public void addVpnRuleForTethering(String str) {
        runVpnRulesCommand(4, "*nat\n-I tetherctrl_nat_POSTROUTING -s " + str.substring(0, str.indexOf("/")) + " -j RETURN\nCOMMIT\n");
    }

    public void deleteTetheringRule(String str) {
        runVpnRulesCommand(4, "*nat\n-D tetherctrl_nat_POSTROUTING -s " + str.substring(0, str.indexOf("/")) + " -j RETURN\nCOMMIT\n");
    }

    public void setFirewallEnabled(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("*filter\n");
        sb.append(z ? "-A" : "-D");
        sb.append(" fw_INPUT");
        sb.append(" -j DROP\nCOMMIT\n");
        runVpnRulesCommand(46, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("*filter\n");
        sb2.append(z ? "-A" : "-D");
        sb2.append(" fw_OUTPUT");
        sb2.append(" -j REJECT\nCOMMIT\n");
        runVpnRulesCommand(46, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("*filter\n");
        sb3.append(z ? "-A" : "-D");
        sb3.append(" fw_FORWARD");
        sb3.append(" -j REJECT\nCOMMIT\n");
        runVpnRulesCommand(46, sb3.toString());
    }

    public void setFirewallInterfaceRule(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("*filter\n");
        sb.append(z ? "-I" : "-D");
        sb.append(" fw_INPUT");
        sb.append(" -i ");
        sb.append(str);
        sb.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(46, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("*filter\n");
        sb2.append(z ? "-I" : "-D");
        sb2.append(" fw_OUTPUT");
        sb2.append(" -o ");
        sb2.append(str);
        sb2.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(46, sb2.toString());
    }

    public void setFirewallEgressDestRule(String str, int i, boolean z) {
        if (str == null) {
            return;
        }
        int i2 = str.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? 6 : 4;
        StringBuilder sb = new StringBuilder();
        sb.append("*filter\n");
        sb.append(z ? "-I" : "-D");
        sb.append(" fw_INPUT");
        sb.append(" -s ");
        sb.append(str);
        sb.append(" -p ");
        sb.append("tcp");
        sb.append(" --sport ");
        sb.append(i);
        sb.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("*filter\n");
        sb2.append(z ? "-I" : "-D");
        sb2.append(" fw_OUTPUT");
        sb2.append(" -d ");
        sb2.append(str);
        sb2.append(" -p ");
        sb2.append("tcp");
        sb2.append(" --dport ");
        sb2.append(i);
        sb2.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("*filter\n");
        sb3.append(z ? "-I" : "-D");
        sb3.append(" fw_INPUT");
        sb3.append(" -s ");
        sb3.append(str);
        sb3.append(" -p ");
        sb3.append("udp");
        sb3.append(" --sport ");
        sb3.append(i);
        sb3.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("*filter\n");
        sb4.append(z ? "-I" : "-D");
        sb4.append(" fw_OUTPUT");
        sb4.append(" -d ");
        sb4.append(str);
        sb4.append(" -p ");
        sb4.append("udp");
        sb4.append(" --dport ");
        sb4.append(i);
        sb4.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb4.toString());
    }

    public void setFirewallEgressSourceRule(String str, boolean z) {
        if (str == null) {
            return;
        }
        int i = str.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? 6 : 4;
        StringBuilder sb = new StringBuilder();
        sb.append("*filter\n");
        sb.append(z ? "-I" : "-D");
        sb.append(" fw_INPUT");
        sb.append(" -d ");
        sb.append(str);
        sb.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("*filter\n");
        sb2.append(z ? "-I" : "-D");
        sb2.append(" fw_OUTPUT");
        sb2.append(" -s ");
        sb2.append(str);
        sb2.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i, sb2.toString());
    }

    public int setTcpPortBypassRule(boolean z, int i, int i2, int i3, int i4, String str, String str2) {
        if (i > 0 && i2 > 0 && i3 > 0 && i4 > 0 && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            int i5 = str2.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? 6 : 4;
            if (z) {
                String runShellCommand = runShellCommand(KnoxVpnFirewallHelper.IP4_RULE_CMD);
                if (runShellCommand == null) {
                    return -1;
                }
                boolean z2 = false;
                int i6 = 0;
                while (true) {
                    if (i6 > 3) {
                        break;
                    }
                    if (!runShellCommand.contains("fwmark 0x" + Integer.toHexString(i2))) {
                        z2 = true;
                        break;
                    }
                    i2++;
                    i6++;
                }
                if (!z2) {
                    Log.d("VpnRules", "Failed to run setTcpPortBypassRule: invalid fwmark");
                    return -1;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("ip -");
            sb.append(i5);
            sb.append(" rule ");
            sb.append(z ? "add" : "del");
            sb.append(" from all fwmark ");
            sb.append(i2);
            sb.append(" table ");
            sb.append(i4);
            sb.append(" prio ");
            sb.append(i3);
            runShellCommand(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("*mangle\n");
            sb2.append(z ? "-A" : "-D");
            sb2.append(" OUTPUT");
            sb2.append(" -p tcp --dport ");
            sb2.append(i);
            sb2.append(" -j MARK --set-mark ");
            sb2.append(i2);
            sb2.append("\nCOMMIT\n");
            runVpnRulesCommand(i5, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("*nat\n");
            sb3.append(z ? "-A" : "-D");
            sb3.append(" POSTROUTING");
            sb3.append(" -o ");
            sb3.append(str);
            sb3.append(" -j SNAT --to ");
            sb3.append(str2);
            sb3.append("\nCOMMIT\n");
            runVpnRulesCommand(i5, sb3.toString());
            return i2;
        }
        Log.d("VpnRules", "Failed to run setTcpPortBypassRule: invalid parameter");
        return -1;
    }

    public final boolean bindNetdNativeService() {
        try {
            INetd asInterface = INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
            this.mNetdService = asInterface;
            if (asInterface == null) {
                Log.e("VpnRules", "Failed to bind netd: null");
                return false;
            }
            if (!asInterface.isAlive()) {
                Log.e("VpnRules", "Failed to bind netd: not alive");
                return false;
            }
            try {
                IOemNetd asInterface2 = IOemNetd.Stub.asInterface(this.mNetdService.getOemNetd());
                this.mOemNetd = asInterface2;
                if (asInterface2 != null) {
                    return true;
                }
                Log.e("VpnRules", "Failed to get OemNetd listener: null");
                return false;
            } catch (Exception e) {
                Log.e("VpnRules", "Failed to get OemNetd listener: err = " + e);
                return false;
            }
        } catch (Exception e2) {
            Log.e("VpnRules", "Failed to bind netd: err = " + e2);
            return false;
        }
    }

    public final synchronized void runVpnRulesCommand(int i, String str) {
        if ((this.mNetdService == null || this.mOemNetd == null) && !bindNetdNativeService()) {
            Log.e("VpnRules", "runCmd: Failed to bind netd");
            return;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i2 = 0; i2 < str.length(); i2++) {
                stringBuffer.append((char) (str.charAt(i2) ^ "VPN_RULES".charAt(i2 % 9)));
            }
            String stringBuffer2 = stringBuffer.toString();
            Log.d("VpnRules", "runCmd: cmd = {" + str.replaceAll(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, "|") + "} param.len=" + stringBuffer2.length());
            String runVpnRulesCommand = this.mOemNetd.runVpnRulesCommand(i, str, stringBuffer2);
            StringBuilder sb = new StringBuilder();
            sb.append("runCmd: ret = {");
            if (TextUtils.isEmpty(runVpnRulesCommand)) {
                runVpnRulesCommand = "OK";
            }
            sb.append(runVpnRulesCommand);
            sb.append("}");
            Log.d("VpnRules", sb.toString());
        } catch (Exception e) {
            Log.e("VpnRules", "runCmd: err = " + e);
        }
    }

    public final synchronized String runShellCommand(String str) {
        if ((this.mNetdService == null || this.mOemNetd == null) && !bindNetdNativeService()) {
            Log.e("VpnRules", "runShellCmd: Failed to bind netd");
            return null;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                stringBuffer.append((char) (str.charAt(i) ^ "TCPMONITOR".charAt(i % 10)));
            }
            String stringBuffer2 = stringBuffer.toString();
            Log.d("VpnRules", "runShellCmd: cmd = {" + str + "} param.len=" + stringBuffer2.length());
            String runTcpMonitorShellCommand = this.mOemNetd.runTcpMonitorShellCommand(str, stringBuffer2);
            if (runTcpMonitorShellCommand != null) {
                if (runTcpMonitorShellCommand.startsWith(stringBuffer2 + " - ")) {
                    String substring = runTcpMonitorShellCommand.substring(stringBuffer2.length() + 3);
                    StringBuilder sb = new StringBuilder();
                    sb.append("runCmd: ret = {");
                    sb.append(TextUtils.isEmpty(substring) ? "OK" : Integer.valueOf(substring.length()));
                    sb.append("}");
                    Log.d("VpnRules", sb.toString());
                    return substring;
                }
            }
            Log.d("VpnRules", "runShellCmd: ret = {" + runTcpMonitorShellCommand + "}");
        } catch (Exception e) {
            Log.e("VpnRules", "runShellCmd: err = " + e);
        }
        return null;
    }
}
