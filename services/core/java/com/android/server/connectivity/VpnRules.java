package com.android.server.connectivity;

import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.net.IOemNetd;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

public final class VpnRules {
    public INetd mNetdService;
    public IOemNetd mOemNetd;

    public final void addVpnRuleForTethering(String str) {
        runVpnRulesCommand(
                4,
                "*nat\n-I tetherctrl_nat_POSTROUTING -s "
                        + str.substring(0, str.indexOf("/"))
                        + " -j RETURN\nCOMMIT\n");
    }

    public final boolean bindNetdNativeService() {
        try {
            INetd asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
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
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        e, "Failed to get OemNetd listener: err = ", "VpnRules");
                return false;
            }
        } catch (Exception e2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e2, "Failed to bind netd: err = ", "VpnRules");
            return false;
        }
    }

    public final void deleteTetheringRule(String str) {
        runVpnRulesCommand(
                4,
                "*nat\n-D tetherctrl_nat_POSTROUTING -s "
                        + str.substring(0, str.indexOf("/"))
                        + " -j RETURN\nCOMMIT\n");
    }

    public final void deleteVpnPostroutingChain() {
        Log.d("VpnRules", "deleteVpnPostroutingChain");
        runVpnRulesCommand(4, "*mangle\n-D POSTROUTING -j vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-F vpn_POSTROUTING\nCOMMIT\n");
        runVpnRulesCommand(4, "*mangle\n-X vpn_POSTROUTING\nCOMMIT\n");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:2|3|(11:5|(1:7)|8|9|(2:12|10)|13|14|(2:16|(5:18|(1:20)(1:25)|21|22|23))|26|27|28)|32|(3:34|35|36)|8|9|(1:10)|13|14|(0)|26|27|28) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x003e, code lost:

       r7 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00e0, code lost:

       android.util.Log.e("VpnRules", "runShellCmd: err = " + r7);
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002a A[Catch: all -> 0x000b, Exception -> 0x003e, LOOP:0: B:10:0x0024->B:12:0x002a, LOOP_END, TryCatch #0 {Exception -> 0x003e, blocks: (B:9:0x001e, B:10:0x0024, B:12:0x002a, B:14:0x0041, B:16:0x0071, B:18:0x0088, B:21:0x00b0, B:25:0x00a8, B:26:0x00c2), top: B:8:0x001e, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0071 A[Catch: all -> 0x000b, Exception -> 0x003e, TryCatch #0 {Exception -> 0x003e, blocks: (B:9:0x001e, B:10:0x0024, B:12:0x002a, B:14:0x0041, B:16:0x0071, B:18:0x0088, B:21:0x00b0, B:25:0x00a8, B:26:0x00c2), top: B:8:0x001e, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.lang.String runShellCommand(java.lang.String r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            android.net.INetd r0 = r6.mNetdService     // Catch: java.lang.Throwable -> Lb
            r1 = 0
            if (r0 == 0) goto Le
            com.android.internal.net.IOemNetd r0 = r6.mOemNetd     // Catch: java.lang.Throwable -> Lb
            if (r0 != 0) goto L1e
            goto Le
        Lb:
            r7 = move-exception
            goto Lf9
        Le:
            boolean r0 = r6.bindNetdNativeService()     // Catch: java.lang.Throwable -> Lb
            if (r0 != 0) goto L1e
            java.lang.String r7 = "VpnRules"
            java.lang.String r0 = "runShellCmd: Failed to bind netd"
            android.util.Log.e(r7, r0)     // Catch: java.lang.Throwable -> Lb
            monitor-exit(r6)
            return r1
        L1e:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r0.<init>()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2 = 0
        L24:
            int r3 = r7.length()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            if (r2 >= r3) goto L41
            char r3 = r7.charAt(r2)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r4 = "TCPMONITOR"
            int r5 = r2 % 10
            char r4 = r4.charAt(r5)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r3 = r3 ^ r4
            char r3 = (char) r3     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r0.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            int r2 = r2 + 1
            goto L24
        L3e:
            r7 = move-exception
            goto Le0
        L41:
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r2 = "VpnRules"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r3.<init>()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r4 = "runShellCmd: cmd = {"
            r3.append(r4)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r3.append(r7)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r4 = "} param.len="
            r3.append(r4)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            int r4 = r0.length()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r3.append(r4)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            android.util.Log.d(r2, r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            com.android.internal.net.IOemNetd r2 = r6.mOemNetd     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r7 = r2.runTcpMonitorShellCommand(r7, r0)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            if (r7 == 0) goto Lc2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2.<init>()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2.append(r0)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r3 = " - "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            boolean r2 = r7.startsWith(r2)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            if (r2 == 0) goto Lc2
            int r0 = r0.length()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            int r0 = r0 + 3
            java.lang.String r7 = r7.substring(r0)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r0 = "VpnRules"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2.<init>()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r3 = "runCmd: ret = {"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            if (r3 == 0) goto La8
            java.lang.String r3 = "OK"
            goto Lb0
        La8:
            int r3 = r7.length()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
        Lb0:
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r3 = "}"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            android.util.Log.d(r0, r2)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            monitor-exit(r6)
            return r7
        Lc2:
            java.lang.String r0 = "VpnRules"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2.<init>()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r3 = "runShellCmd: ret = {"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            r2.append(r7)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r7 = "}"
            r2.append(r7)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            android.util.Log.d(r0, r7)     // Catch: java.lang.Throwable -> Lb java.lang.Exception -> L3e
            goto Lf7
        Le0:
            java.lang.String r0 = "VpnRules"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb
            r2.<init>()     // Catch: java.lang.Throwable -> Lb
            java.lang.String r3 = "runShellCmd: err = "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb
            r2.append(r7)     // Catch: java.lang.Throwable -> Lb
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> Lb
            android.util.Log.e(r0, r7)     // Catch: java.lang.Throwable -> Lb
        Lf7:
            monitor-exit(r6)
            return r1
        Lf9:
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.connectivity.VpnRules.runShellCommand(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:2|3|(11:5|(1:7)|8|9|(2:12|10)|13|14|(1:16)|17|18|19)|25|(3:27|28|29)|8|9|(1:10)|13|14|(0)|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003d, code lost:

       r6 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x009c, code lost:

       android.util.Log.e("VpnRules", "runCmd: err = " + r6);
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0029 A[Catch: all -> 0x000a, Exception -> 0x003d, LOOP:0: B:10:0x0023->B:12:0x0029, LOOP_END, TryCatch #0 {Exception -> 0x003d, blocks: (B:9:0x001d, B:10:0x0023, B:12:0x0029, B:14:0x003f, B:17:0x008b), top: B:8:0x001d, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void runVpnRulesCommand(int r6, java.lang.String r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            android.net.INetd r0 = r5.mNetdService     // Catch: java.lang.Throwable -> La
            if (r0 == 0) goto Ld
            com.android.internal.net.IOemNetd r0 = r5.mOemNetd     // Catch: java.lang.Throwable -> La
            if (r0 != 0) goto L1d
            goto Ld
        La:
            r6 = move-exception
            goto Lb5
        Ld:
            boolean r0 = r5.bindNetdNativeService()     // Catch: java.lang.Throwable -> La
            if (r0 != 0) goto L1d
            java.lang.String r6 = "VpnRules"
            java.lang.String r7 = "runCmd: Failed to bind netd"
            android.util.Log.e(r6, r7)     // Catch: java.lang.Throwable -> La
            monitor-exit(r5)
            return
        L1d:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r0.<init>()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r1 = 0
        L23:
            int r2 = r7.length()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            if (r1 >= r2) goto L3f
            char r2 = r7.charAt(r1)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r3 = "VPN_RULES"
            int r4 = r1 % 9
            char r3 = r3.charAt(r4)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r2 = r2 ^ r3
            char r2 = (char) r2     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r0.append(r2)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            int r1 = r1 + 1
            goto L23
        L3d:
            r6 = move-exception
            goto L9c
        L3f:
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r1 = "VpnRules"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r2.<init>()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r3 = "runCmd: cmd = {"
            r2.append(r3)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r3 = "\n"
            java.lang.String r4 = "|"
            java.lang.String r3 = r7.replaceAll(r3, r4)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r2.append(r3)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r3 = "} param.len="
            r2.append(r3)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            int r3 = r0.length()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r2.append(r3)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            com.android.internal.net.IOemNetd r1 = r5.mOemNetd     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r6 = r1.runVpnRulesCommand(r6, r7, r0)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r7 = "VpnRules"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            r0.<init>()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r1 = "runCmd: ret = {"
            r0.append(r1)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            if (r1 == 0) goto L8b
            java.lang.String r6 = "OK"
        L8b:
            r0.append(r6)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r6 = "}"
            r0.append(r6)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            java.lang.String r6 = r0.toString()     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            android.util.Log.d(r7, r6)     // Catch: java.lang.Throwable -> La java.lang.Exception -> L3d
            goto Lb3
        L9c:
            java.lang.String r7 = "VpnRules"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La
            r0.<init>()     // Catch: java.lang.Throwable -> La
            java.lang.String r1 = "runCmd: err = "
            r0.append(r1)     // Catch: java.lang.Throwable -> La
            r0.append(r6)     // Catch: java.lang.Throwable -> La
            java.lang.String r6 = r0.toString()     // Catch: java.lang.Throwable -> La
            android.util.Log.e(r7, r6)     // Catch: java.lang.Throwable -> La
        Lb3:
            monitor-exit(r5)
            return
        Lb5:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.connectivity.VpnRules.runVpnRulesCommand(int,"
                    + " java.lang.String):void");
    }

    public final void setFirewallEgressDestRule(int i, String str, boolean z) {
        if (str == null) {
            return;
        }
        int i2 = str.contains(":") ? 6 : 4;
        StringBuilder sb = new StringBuilder("*filter\n");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb, z ? "-I" : "-D", " fw_INPUT -s ", str, " -p tcp --sport ");
        sb.append(i);
        sb.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb.toString());
        StringBuilder sb2 = new StringBuilder("*filter\n");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb2, z ? "-I" : "-D", " fw_OUTPUT -d ", str, " -p tcp --dport ");
        sb2.append(i);
        sb2.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb2.toString());
        StringBuilder sb3 = new StringBuilder("*filter\n");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb3, z ? "-I" : "-D", " fw_INPUT -s ", str, " -p udp --sport ");
        sb3.append(i);
        sb3.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb3.toString());
        StringBuilder sb4 = new StringBuilder("*filter\n");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb4, z ? "-I" : "-D", " fw_OUTPUT -d ", str, " -p udp --dport ");
        sb4.append(i);
        sb4.append(" -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(i2, sb4.toString());
    }

    public final void setFirewallEgressSourceRule(String str, boolean z) {
        if (str == null) {
            return;
        }
        int i = str.contains(":") ? 6 : 4;
        runVpnRulesCommand(
                i,
                OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("*filter\n"),
                        z ? "-I" : "-D",
                        " fw_INPUT -d ",
                        str,
                        " -j RETURN\nCOMMIT\n"));
        runVpnRulesCommand(
                i,
                OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("*filter\n"),
                        z ? "-I" : "-D",
                        " fw_OUTPUT -s ",
                        str,
                        " -j RETURN\nCOMMIT\n"));
    }

    public final void setFirewallEnabled(boolean z) {
        StringBuilder sb = new StringBuilder("*filter\n");
        sb.append(z ? "-A" : "-D");
        sb.append(" fw_INPUT -j DROP\nCOMMIT\n");
        runVpnRulesCommand(46, sb.toString());
        StringBuilder sb2 = new StringBuilder("*filter\n");
        sb2.append(z ? "-A" : "-D");
        sb2.append(" fw_OUTPUT -j REJECT\nCOMMIT\n");
        runVpnRulesCommand(46, sb2.toString());
        StringBuilder sb3 = new StringBuilder("*filter\n");
        sb3.append(z ? "-A" : "-D");
        sb3.append(" fw_FORWARD -j REJECT\nCOMMIT\n");
        runVpnRulesCommand(46, sb3.toString());
    }

    public final void setFirewallInterfaceRule(boolean z) {
        StringBuilder sb = new StringBuilder("*filter\n");
        sb.append(z ? "-I" : "-D");
        sb.append(" fw_INPUT -i ipsec0 -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(46, sb.toString());
        StringBuilder sb2 = new StringBuilder("*filter\n");
        sb2.append(z ? "-I" : "-D");
        sb2.append(" fw_OUTPUT -o ipsec0 -j RETURN\nCOMMIT\n");
        runVpnRulesCommand(46, sb2.toString());
    }

    public final int setTcpPortBypassRule(
            int i, int i2, int i3, int i4, String str, String str2, boolean z) {
        if (i <= 0
                || i2 <= 0
                || i3 <= 0
                || i4 <= 0
                || TextUtils.isEmpty(str)
                || TextUtils.isEmpty(str2)) {
            Log.d("VpnRules", "Failed to run setTcpPortBypassRule: invalid parameter");
            return -1;
        }
        int i5 = str2.contains(":") ? 6 : 4;
        if (z) {
            String runShellCommand = runShellCommand("ip rule");
            if (runShellCommand == null) {
                return -1;
            }
            for (int i6 = 0; i6 <= 3; i6++) {
                if (runShellCommand.contains("fwmark 0x" + Integer.toHexString(i2))) {
                    i2++;
                }
            }
            Log.d("VpnRules", "Failed to run setTcpPortBypassRule: invalid fwmark");
            return -1;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i5, "ip -", " rule ");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                i2, z ? "add" : "del", " from all fwmark ", " table ", m);
        m.append(i4);
        m.append(" prio ");
        m.append(i3);
        runShellCommand(m.toString());
        StringBuilder sb = new StringBuilder("*mangle\n");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                i, z ? "-A" : "-D", " OUTPUT -p tcp --dport ", " -j MARK --set-mark ", sb);
        sb.append(i2);
        sb.append("\nCOMMIT\n");
        runVpnRulesCommand(i5, sb.toString());
        StringBuilder sb2 = new StringBuilder("*nat\n");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb2, z ? "-A" : "-D", " POSTROUTING -o ", str, " -j SNAT --to ");
        sb2.append(str2);
        sb2.append("\nCOMMIT\n");
        runVpnRulesCommand(i5, sb2.toString());
        return i2;
    }
}
