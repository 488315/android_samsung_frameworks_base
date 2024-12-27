package com.android.server.net;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.ShellCommand;
import android.os.SystemProperties;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NetworkPolicyManagerShellCommand extends ShellCommand {
    public final NetworkPolicyManagerService mInterface;
    public final WifiManager mWifiManager;

    public NetworkPolicyManagerShellCommand(Context context, NetworkPolicyManagerService networkPolicyManagerService) {
        this.mInterface = networkPolicyManagerService;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public final int getUidFromNextArg() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify UID");
            return -1;
        }
        try {
            return Integer.parseInt(nextArg);
        } catch (NumberFormatException unused) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: UID (", nextArg, ") should be a number");
            return -2;
        }
    }

    public final void listUidList(String str, int[] iArr) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print(str);
        outPrintWriter.print(": ");
        if (iArr.length == 0) {
            outPrintWriter.println("none");
        } else {
            for (int i : iArr) {
                outPrintWriter.print(i);
                outPrintWriter.print(' ');
            }
        }
        outPrintWriter.println();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b4, code lost:
    
        if (r6.equals("restrict-background-whitelist") == false) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.net.NetworkPolicyManagerShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Network policy manager (netpolicy) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  add restrict-background-whitelist UID", "    Adds a UID to the whitelist for restrict background usage.", "  add restrict-background-blacklist UID", "    Adds a UID to the blacklist for restrict background usage.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  add app-idle-whitelist UID", "    Adds a UID to the temporary app idle whitelist.", "  get restrict-background", "    Gets the global restrict background usage status.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  list wifi-networks [true|false]", "    Lists all saved wifi networks and whether they are metered or not.", "    If a boolean argument is passed, filters just the metered (or unmetered)", "    networks.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  list restrict-background-whitelist", "    Lists UIDs that are whitelisted for restrict background usage.", "  list restrict-background-blacklist", "    Lists UIDs that are blacklisted for restrict background usage.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  remove restrict-background-whitelist UID", "    Removes a UID from the whitelist for restrict background usage.", "  remove restrict-background-blacklist UID", "    Removes a UID from the blacklist for restrict background usage.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  remove app-idle-whitelist UID", "    Removes a UID from the temporary app idle whitelist.", "  set metered-network ID [undefined|true|false]", "    Toggles whether the given wi-fi network is metered.");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  set restrict-background BOOLEAN", "    Sets the global restrict background usage status.", "  set sub-plan-owner subId [packageName]", "    Sets the data plan owner package for subId.");
    }

    public final int resetUidPolicy(int i, String str) {
        int uidFromNextArg = getUidFromNextArg();
        if (uidFromNextArg < 0) {
            return uidFromNextArg;
        }
        if (this.mInterface.getUidPolicy(uidFromNextArg) == i) {
            this.mInterface.setUidPolicy(uidFromNextArg, 0);
            return 0;
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print("Error: UID ");
        outPrintWriter.print(uidFromNextArg);
        outPrintWriter.print(' ');
        outPrintWriter.println(str);
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runAdd() {
        char c;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify type of data to add");
            return -1;
        }
        switch (nextArg.hashCode()) {
            case -1683867974:
                if (nextArg.equals("app-idle-whitelist")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -668534353:
                if (nextArg.equals("restrict-background-blacklist")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 639570137:
                if (nextArg.equals("restrict-background-whitelist")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                int uidFromNextArg = getUidFromNextArg();
                if (uidFromNextArg >= 0) {
                    this.mInterface.setAppIdleWhitelist(uidFromNextArg, true);
                    break;
                } else {
                    break;
                }
            case 1:
                int uidFromNextArg2 = getUidFromNextArg();
                if (uidFromNextArg2 >= 0) {
                    this.mInterface.setUidPolicy(uidFromNextArg2, 1);
                    break;
                } else {
                    break;
                }
            case 2:
                int uidFromNextArg3 = getUidFromNextArg();
                if (uidFromNextArg3 >= 0) {
                    this.mInterface.setUidPolicy(uidFromNextArg3, 4);
                    break;
                } else {
                    break;
                }
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: unknown add type '", nextArg, "'");
                break;
        }
        return -1;
    }

    public final int runGet() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify type of data to get");
            return -1;
        }
        if (nextArg.equals("restrict-background")) {
            PrintWriter outPrintWriter2 = getOutPrintWriter();
            outPrintWriter2.print("Restrict background status: ");
            outPrintWriter2.println(this.mInterface.getRestrictBackground() ? "enabled" : "disabled");
            return 0;
        }
        if (!nextArg.equals("restricted-mode")) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: unknown get type '", nextArg, "'");
            return -1;
        }
        PrintWriter outPrintWriter3 = getOutPrintWriter();
        outPrintWriter3.print("Restricted mode status: ");
        outPrintWriter3.println(this.mInterface.isRestrictedModeEnabled() ? "enabled" : "disabled");
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runList() {
        char c;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify type of data to list");
            return -1;
        }
        switch (nextArg.hashCode()) {
            case -1683867974:
                if (nextArg.equals("app-idle-whitelist")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -668534353:
                if (nextArg.equals("restrict-background-blacklist")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -363534403:
                if (nextArg.equals("wifi-networks")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 639570137:
                if (nextArg.equals("restrict-background-whitelist")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                getOutPrintWriter();
                listUidList("App Idle whitelisted UIDs", this.mInterface.getAppIdleWhitelist());
                break;
            case 1:
                listUidList("Restrict background blacklisted UIDs", this.mInterface.getUidsWithPolicy(1));
                break;
            case 2:
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                String nextArg2 = getNextArg();
                int i = nextArg2 == null ? 0 : Boolean.parseBoolean(nextArg2) ? 1 : 2;
                for (WifiConfiguration wifiConfiguration : this.mWifiManager.getConfiguredNetworks()) {
                    if (nextArg2 == null || wifiConfiguration.meteredOverride == i) {
                        outPrintWriter2.print(NetworkPolicyManager.resolveNetworkId(wifiConfiguration));
                        outPrintWriter2.print(';');
                        int i2 = wifiConfiguration.meteredOverride;
                        outPrintWriter2.println(i2 != 1 ? i2 != 2 ? "none" : "false" : "true");
                    }
                }
                break;
            case 3:
                listUidList("Restrict background whitelisted UIDs", this.mInterface.getUidsWithPolicy(4));
                break;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: unknown list type '", nextArg, "'");
                break;
        }
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    public final int runSet() {
        boolean z;
        int i = 2;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        int i2 = -1;
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify type of data to set");
            return -1;
        }
        switch (nextArg.hashCode()) {
            case -983249079:
                if (nextArg.equals("metered-network")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -747095841:
                if (nextArg.equals("restrict-background")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1846940860:
                if (nextArg.equals("sub-plan-owner")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                String nextArg2 = getNextArg();
                if (nextArg2 != null) {
                    String nextArg3 = getNextArg();
                    if (nextArg3 != null) {
                        NetworkPolicyManagerService networkPolicyManagerService = this.mInterface;
                        String resolveNetworkId = NetworkPolicyManager.resolveNetworkId(nextArg2);
                        if (nextArg3.equals("true")) {
                            i = 1;
                        } else if (!nextArg3.equals("false")) {
                            i = 0;
                        }
                        networkPolicyManagerService.setWifiMeteredOverride(resolveNetworkId, i);
                        break;
                    } else {
                        outPrintWriter2.println("Error: didn't specify meteredOverride");
                        break;
                    }
                } else {
                    outPrintWriter2.println("Error: didn't specify networkId");
                    break;
                }
            case true:
                PrintWriter outPrintWriter3 = getOutPrintWriter();
                String nextArg4 = getNextArg();
                if (nextArg4 == null) {
                    outPrintWriter3.println("Error: didn't specify BOOLEAN");
                } else {
                    i2 = Boolean.valueOf(nextArg4).booleanValue();
                }
                if (i2 >= 0) {
                    this.mInterface.setRestrictBackground(i2 > 0);
                    break;
                } else {
                    break;
                }
            case true:
                int parseInt = Integer.parseInt(getNextArgRequired());
                String nextArg5 = getNextArg();
                this.mInterface.mContext.enforceCallingOrSelfPermission("android.permission.NETWORK_SETTINGS", "NetworkPolicy");
                SystemProperties.set("persist.sys.sub_plan_owner." + parseInt, nextArg5);
                break;
            default:
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(outPrintWriter, "Error: unknown set type '", nextArg, "'");
                break;
        }
        return -1;
    }
}
