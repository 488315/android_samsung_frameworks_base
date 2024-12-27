package com.android.server.net;

import android.app.ActivityManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.NetworkPolicyManager;
import android.os.PowerExemptionManager;
import android.util.Log;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class NetworkPolicyLogger {
    public static final boolean LOGD = Log.isLoggable("NetworkPolicy", 3);
    public static final boolean LOGV = Log.isLoggable("NetworkPolicy", 2);
    public static final int MAX_LOG_SIZE;
    public static final int MAX_NETWORK_BLOCKED_LOG_SIZE;
    public int mDebugUid;
    public final LogBuffer mEventsBuffer;
    public final Object mLock;
    public final LogBuffer mNetworkBlockedBuffer = new LogBuffer(MAX_NETWORK_BLOCKED_LOG_SIZE);
    public final LogBuffer mUidStateChangeBuffer;

    public final class Data {
        public boolean bfield1;
        public boolean bfield2;
        public int ifield1;
        public int ifield2;
        public int ifield3;
        public int ifield4;
        public long lfield1;
        public String sfield1;
        public long timeStamp;
        public int type;
    }

    public final class LogBuffer {
        public final int mMaxSize;
        public static final SimpleDateFormat sFormatter =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");
        public static final Date sDate = new Date();
        public final ArrayList mDataList = new ArrayList();
        public long mCursor = 0;

        public LogBuffer(int i) {
            this.mMaxSize = i;
        }

        public final Data getNextSlot() {
            long j = this.mCursor;
            this.mCursor = 1 + j;
            int i = this.mMaxSize;
            int abs = (int) Math.abs(j % i);
            if (this.mDataList.size() <= i) {
                this.mDataList.add(new Data());
            }
            return (Data) this.mDataList.get(abs);
        }

        public final void reverseDump(IndentingPrintWriter indentingPrintWriter) {
            String str;
            for (int size = this.mDataList.size() - 1; size >= 0; size--) {
                if (this.mDataList.get(size) == null) {
                    indentingPrintWriter.println("NULL");
                } else {
                    long j = ((Data) this.mDataList.get(size)).timeStamp;
                    Date date = sDate;
                    date.setTime(j);
                    indentingPrintWriter.print(sFormatter.format(date));
                    indentingPrintWriter.print(" - ");
                    Data data = (Data) this.mDataList.get(size);
                    int i = data.type;
                    switch (i) {
                        case 0:
                            str = data.sfield1;
                            break;
                        case 1:
                            str =
                                    data.ifield1
                                            + PackageManagerShellCommandDataLoader.STDIN_PATH
                                            + NetworkPolicyManagerService.UidBlockedState.toString(
                                                    data.ifield2, data.ifield3, data.ifield4);
                            break;
                        case 2:
                            StringBuilder sb = new StringBuilder();
                            sb.append(data.ifield1);
                            sb.append(":");
                            int i2 = data.ifield2;
                            int i3 = ProcessList.PAGE_SIZE;
                            sb.append(ActivityManager.procStateToString(i2));
                            sb.append(":");
                            sb.append(ActivityManager.getCapabilitiesSummary(data.ifield3));
                            sb.append(":");
                            sb.append(data.lfield1);
                            str = sb.toString();
                            break;
                        case 3:
                            str =
                                    NetworkPolicyLogger.getPolicyChangedLog(
                                            data.ifield1, data.ifield2, data.ifield3);
                            break;
                        case 4:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "Meteredness of netId=",
                                                    " changed to ",
                                                    data.bfield1);
                            break;
                        case 5:
                            str =
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            data.ifield1, "Remove state for u");
                            break;
                        case 6:
                            str =
                                    "Changed restrictBackground: "
                                            + data.bfield1
                                            + "->"
                                            + data.bfield2;
                            break;
                        case 7:
                            str = "DeviceIdleMode enabled: " + data.bfield1;
                            break;
                        case 8:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "App idle state of uid ",
                                                    ": ",
                                                    data.bfield1);
                            break;
                        case 9:
                            str = "Parole state: " + data.bfield1;
                            break;
                        case 10:
                            str =
                                    NetworkPolicyLogger.getTempPowerSaveWlChangedLog(
                                            data.ifield1, data.ifield2, data.sfield1, data.bfield1);
                            break;
                        case 11:
                            str =
                                    NetworkPolicyLogger.getUidFirewallRuleChangedLog(
                                            data.ifield1, data.ifield2, data.ifield3);
                            break;
                        case 12:
                            int i4 = data.ifield1;
                            str =
                                    "Firewall chain "
                                            + NetworkPolicyLogger.getFirewallChainName(i4)
                                            + " state: "
                                            + data.bfield1;
                            break;
                        case 13:
                        default:
                            str = String.valueOf(i);
                            break;
                        case 14:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "App idle whitelist state of uid ",
                                                    ": ",
                                                    data.bfield1);
                            break;
                        case 15:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "metered-allowlist for ",
                                                    " changed to ",
                                                    data.bfield1);
                            break;
                        case 16:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "metered-denylist for ",
                                                    " changed to ",
                                                    data.bfield1);
                            break;
                        case 17:
                            str =
                                    AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0
                                            .m(
                                                    data.ifield1,
                                                    "Roaming of netId=",
                                                    " changed to ",
                                                    data.bfield1);
                            break;
                        case 18:
                            str =
                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                            data.ifield1,
                                            "Interfaces of netId=",
                                            " changed to ",
                                            data.sfield1);
                            break;
                    }
                    indentingPrintWriter.println(str);
                }
            }
        }
    }

    static {
        MAX_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
        MAX_NETWORK_BLOCKED_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 100 : 400;
    }

    public NetworkPolicyLogger() {
        int i = MAX_LOG_SIZE;
        this.mUidStateChangeBuffer = new LogBuffer(i);
        this.mEventsBuffer = new LogBuffer(i);
        this.mDebugUid = -1;
        this.mLock = new Object();
    }

    public static String getFirewallChainName(int i) {
        switch (i) {
            case 1:
                return "dozable";
            case 2:
                return "standby";
            case 3:
                return "powersave";
            case 4:
                return "restricted";
            case 5:
                return "low_power_standby";
            case 6:
                return "background";
            case 7:
                return "fw_oem_deny_1";
            case 8:
            case 9:
            default:
                return String.valueOf(i);
            case 10:
                return "metered_allow";
            case 11:
                return "metered_deny_user";
            case 12:
                return "metered_deny_admin";
        }
    }

    public static String getPolicyChangedLog(int i, int i2, int i3) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(i, "Policy for ", " changed from ");
        m.append(NetworkPolicyManager.uidPoliciesToString(i2));
        m.append(" to ");
        m.append(NetworkPolicyManager.uidPoliciesToString(i3));
        return m.toString();
    }

    public static String getTempPowerSaveWlChangedLog(int i, int i2, String str, boolean z) {
        return "temp-power-save whitelist for "
                + i
                + " changed to: "
                + z
                + "; reason="
                + PowerExemptionManager.reasonCodeToString(i2)
                + " <"
                + str
                + ">";
    }

    public static String getUidFirewallRuleChangedLog(int i, int i2, int i3) {
        return String.format(
                "Firewall rule changed: %d-%s-%s",
                Integer.valueOf(i2),
                getFirewallChainName(i),
                i3 != 0 ? i3 != 1 ? i3 != 2 ? String.valueOf(i3) : "deny" : "allow" : "default");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void appIdleStateChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r5 != r1) goto L29
            goto Le
        Lc:
            r4 = move-exception
            goto L45
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = "App idle state of uid "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r5)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = ": "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r6)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L29:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r4 != 0) goto L32
            goto L43
        L32:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 8
            r4.type = r1     // Catch: java.lang.Throwable -> Lc
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Lc
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Lc
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Lc
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.appIdleStateChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void appIdleWlChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r5 != r1) goto L29
            goto Le
        Lc:
            r4 = move-exception
            goto L45
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = "App idle whitelist state of uid "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r5)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = ": "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r6)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L29:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r4 != 0) goto L32
            goto L43
        L32:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 14
            r4.type = r1     // Catch: java.lang.Throwable -> Lc
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Lc
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Lc
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Lc
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.appIdleWlChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void deviceIdleModeEnabled(boolean r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L22
            goto Lf
        Ld:
            r4 = move-exception
            goto L3b
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "DeviceIdleMode enabled: "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r5)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L22:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L2b
            goto L39
        L2b:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 7
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r5     // Catch: java.lang.Throwable -> Ld
            long r1 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r1     // Catch: java.lang.Throwable -> Ld
        L39:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L3b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.deviceIdleModeEnabled(boolean):void");
    }

    public final void dumpLogs(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println();
            indentingPrintWriter.println("mEventLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mEventsBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("mNetworkBlockedLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mNetworkBlockedBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("mUidStateChangeLogs (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mUidStateChangeBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void firewallChainEnabled(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L2e
            goto Lf
        Ld:
            r4 = move-exception
            goto L4a
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "Firewall chain "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = getFirewallChainName(r5)     // Catch: java.lang.Throwable -> Ld
            r2.append(r3)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = " state: "
            r2.append(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r6)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L2e:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L37
            goto L48
        L37:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 12
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Ld
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Ld
        L48:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L4a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.firewallChainEnabled(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void firewallRulesChanged(int r4, int[] r5, int[] r6) {
        /*
            r3 = this;
            java.lang.String r0 = "Firewall rules changed for "
            java.lang.Object r1 = r3.mLock
            monitor-enter(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L37
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = getFirewallChainName(r4)     // Catch: java.lang.Throwable -> L37
            r2.append(r4)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = "; uids="
            r2.append(r4)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = java.util.Arrays.toString(r5)     // Catch: java.lang.Throwable -> L37
            r2.append(r4)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = "; rules="
            r2.append(r4)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = java.util.Arrays.toString(r6)     // Catch: java.lang.Throwable -> L37
            r2.append(r4)     // Catch: java.lang.Throwable -> L37
            java.lang.String r4 = r2.toString()     // Catch: java.lang.Throwable -> L37
            boolean r5 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> L37
            if (r5 != 0) goto L39
            int r5 = r3.mDebugUid     // Catch: java.lang.Throwable -> L37
            r6 = -1
            if (r5 == r6) goto L3e
            goto L39
        L37:
            r3 = move-exception
            goto L54
        L39:
            java.lang.String r5 = "NetworkPolicy"
            android.util.Slog.d(r5, r4)     // Catch: java.lang.Throwable -> L37
        L3e:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r3 = r3.mEventsBuffer     // Catch: java.lang.Throwable -> L37
            com.android.server.net.NetworkPolicyLogger$Data r3 = r3.getNextSlot()     // Catch: java.lang.Throwable -> L37
            if (r3 != 0) goto L47
            goto L52
        L47:
            r5 = 0
            r3.type = r5     // Catch: java.lang.Throwable -> L37
            r3.sfield1 = r4     // Catch: java.lang.Throwable -> L37
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L37
            r3.timeStamp = r4     // Catch: java.lang.Throwable -> L37
        L52:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L37
            return
        L54:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L37
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.firewallRulesChanged(int, int[],"
                    + " int[]):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void interfacesChanged(int r6, android.util.ArraySet r7) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r5.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L2e
            goto Lf
        Ld:
            r5 = move-exception
            goto L4b
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.String r2 = r7.toString()     // Catch: java.lang.Throwable -> Ld
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r4 = "Interfaces of netId="
            r3.<init>(r4)     // Catch: java.lang.Throwable -> Ld
            r3.append(r6)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r4 = " changed to "
            r3.append(r4)     // Catch: java.lang.Throwable -> Ld
            r3.append(r2)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L2e:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r5 = r5.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r5 = r5.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r5 != 0) goto L3b
            goto L49
        L3b:
            r1 = 18
            r5.type = r1     // Catch: java.lang.Throwable -> Ld
            r5.ifield1 = r6     // Catch: java.lang.Throwable -> Ld
            r5.sfield1 = r7     // Catch: java.lang.Throwable -> Ld
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r5.timeStamp = r6     // Catch: java.lang.Throwable -> Ld
        L49:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L4b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.interfacesChanged(int,"
                    + " android.util.ArraySet):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void meteredAllowlistChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r1 != r5) goto L2a
            goto Le
        Lc:
            r4 = move-exception
            goto L46
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = "metered-allowlist for "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r5)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = " changed to "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r6)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L2a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r4 != 0) goto L33
            goto L44
        L33:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 15
            r4.type = r1     // Catch: java.lang.Throwable -> Lc
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Lc
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Lc
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Lc
        L44:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L46:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.meteredAllowlistChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void meteredDenylistChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r1 != r5) goto L2a
            goto Le
        Lc:
            r4 = move-exception
            goto L46
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = "metered-denylist for "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r5)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = " changed to "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r6)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L2a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r4 != 0) goto L33
            goto L44
        L33:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 16
            r4.type = r1     // Catch: java.lang.Throwable -> Lc
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Lc
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Lc
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Lc
        L44:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L46:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.meteredDenylistChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void meteredRestrictedPkgsChanged(java.util.Set r5) {
        /*
            r4 = this;
            java.lang.String r0 = "Metered restricted uids: "
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1b
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L1b
            r2.append(r5)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L1b
            boolean r0 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> L1b
            if (r0 != 0) goto L1d
            int r0 = r4.mDebugUid     // Catch: java.lang.Throwable -> L1b
            r2 = -1
            if (r0 == r2) goto L22
            goto L1d
        L1b:
            r4 = move-exception
            goto L38
        L1d:
            java.lang.String r0 = "NetworkPolicy"
            android.util.Slog.d(r0, r5)     // Catch: java.lang.Throwable -> L1b
        L22:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> L1b
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> L1b
            if (r4 != 0) goto L2b
            goto L36
        L2b:
            r0 = 0
            r4.type = r0     // Catch: java.lang.Throwable -> L1b
            r4.sfield1 = r5     // Catch: java.lang.Throwable -> L1b
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L1b
            r4.timeStamp = r2     // Catch: java.lang.Throwable -> L1b
        L36:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1b
            return
        L38:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1b
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.meteredRestrictedPkgsChanged(java.util.Set):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void meterednessChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L2a
            goto Lf
        Ld:
            r4 = move-exception
            goto L45
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "Meteredness of netId="
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r5)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = " changed to "
            r2.append(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r6)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L2a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L33
            goto L43
        L33:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 4
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Ld
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Ld
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.meterednessChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void networkBlocked(
            int r6, com.android.server.net.NetworkPolicyManagerService.UidBlockedState r7) {
        /*
            r5 = this;
            java.lang.String r0 = "Blocked state of "
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            boolean r2 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Le
            if (r2 != 0) goto L10
            int r2 = r5.mDebugUid     // Catch: java.lang.Throwable -> Le
            if (r6 != r2) goto L29
            goto L10
        Le:
            r5 = move-exception
            goto L6d
        L10:
            java.lang.String r2 = "NetworkPolicy"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Le
            r3.<init>(r0)     // Catch: java.lang.Throwable -> Le
            r3.append(r6)     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = ": "
            r3.append(r0)     // Catch: java.lang.Throwable -> Le
            r3.append(r7)     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> Le
            android.util.Slog.d(r2, r0)     // Catch: java.lang.Throwable -> Le
        L29:
            r0 = 1
            r2 = 0
            if (r7 != 0) goto L4a
            com.android.server.net.NetworkPolicyLogger$LogBuffer r5 = r5.mNetworkBlockedBuffer     // Catch: java.lang.Throwable -> Le
            com.android.server.net.NetworkPolicyLogger$Data r5 = r5.getNextSlot()     // Catch: java.lang.Throwable -> Le
            if (r5 != 0) goto L36
            goto L6b
        L36:
            r5.sfield1 = r2     // Catch: java.lang.Throwable -> Le
            r5.type = r0     // Catch: java.lang.Throwable -> Le
            r5.ifield1 = r6     // Catch: java.lang.Throwable -> Le
            r6 = 0
            r5.ifield2 = r6     // Catch: java.lang.Throwable -> Le
            r5.ifield3 = r6     // Catch: java.lang.Throwable -> Le
            r5.ifield4 = r6     // Catch: java.lang.Throwable -> Le
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le
            r5.timeStamp = r6     // Catch: java.lang.Throwable -> Le
            goto L6b
        L4a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r5 = r5.mNetworkBlockedBuffer     // Catch: java.lang.Throwable -> Le
            int r3 = r7.blockedReasons     // Catch: java.lang.Throwable -> Le
            int r4 = r7.allowedReasons     // Catch: java.lang.Throwable -> Le
            int r7 = r7.effectiveBlockedReasons     // Catch: java.lang.Throwable -> Le
            com.android.server.net.NetworkPolicyLogger$Data r5 = r5.getNextSlot()     // Catch: java.lang.Throwable -> Le
            if (r5 != 0) goto L59
            goto L6b
        L59:
            r5.sfield1 = r2     // Catch: java.lang.Throwable -> Le
            r5.type = r0     // Catch: java.lang.Throwable -> Le
            r5.ifield1 = r6     // Catch: java.lang.Throwable -> Le
            r5.ifield2 = r3     // Catch: java.lang.Throwable -> Le
            r5.ifield3 = r4     // Catch: java.lang.Throwable -> Le
            r5.ifield4 = r7     // Catch: java.lang.Throwable -> Le
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le
            r5.timeStamp = r6     // Catch: java.lang.Throwable -> Le
        L6b:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Le
            return
        L6d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Le
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.networkBlocked(int,"
                    + " com.android.server.net.NetworkPolicyManagerService$UidBlockedState):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void paroleStateChanged(boolean r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L22
            goto Lf
        Ld:
            r4 = move-exception
            goto L3c
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "Parole state: "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r5)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L22:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L2b
            goto L3a
        L2b:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 9
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r5     // Catch: java.lang.Throwable -> Ld
            long r1 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r1     // Catch: java.lang.Throwable -> Ld
        L3a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L3c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.paroleStateChanged(boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restrictBackgroundChanged(boolean r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L2a
            goto Lf
        Ld:
            r4 = move-exception
            goto L45
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "Changed restrictBackground: "
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r5)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "->"
            r2.append(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r6)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L2a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L33
            goto L43
        L33:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 6
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r5     // Catch: java.lang.Throwable -> Ld
            r4.bfield2 = r6     // Catch: java.lang.Throwable -> Ld
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Ld
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.restrictBackgroundChanged(boolean,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void roamingChanged(int r5, boolean r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGD     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Ld
            r2 = -1
            if (r1 == r2) goto L2a
            goto Lf
        Ld:
            r4 = move-exception
            goto L46
        Lf:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = "Roaming of netId="
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r5)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r3 = " changed to "
            r2.append(r3)     // Catch: java.lang.Throwable -> Ld
            r2.append(r6)     // Catch: java.lang.Throwable -> Ld
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Ld
            android.util.Slog.d(r1, r2)     // Catch: java.lang.Throwable -> Ld
        L2a:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mEventsBuffer     // Catch: java.lang.Throwable -> Ld
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Ld
            if (r4 != 0) goto L33
            goto L44
        L33:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Ld
            r1 = 17
            r4.type = r1     // Catch: java.lang.Throwable -> Ld
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Ld
            r4.bfield1 = r6     // Catch: java.lang.Throwable -> Ld
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Ld
        L44:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            return
        L46:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.roamingChanged(int,"
                    + " boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void tempPowerSaveWlChanged(int r4, int r5, java.lang.String r6, boolean r7) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGV     // Catch: java.lang.Throwable -> L10
            if (r1 != 0) goto L12
            int r1 = r3.mDebugUid     // Catch: java.lang.Throwable -> L10
            int r1 = android.os.UserHandle.getAppId(r1)     // Catch: java.lang.Throwable -> L10
            if (r4 != r1) goto L1b
            goto L12
        L10:
            r3 = move-exception
            goto L38
        L12:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.String r2 = getTempPowerSaveWlChangedLog(r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L10
            android.util.Slog.v(r1, r2)     // Catch: java.lang.Throwable -> L10
        L1b:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r3 = r3.mEventsBuffer     // Catch: java.lang.Throwable -> L10
            com.android.server.net.NetworkPolicyLogger$Data r3 = r3.getNextSlot()     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L24
            goto L36
        L24:
            r1 = 10
            r3.type = r1     // Catch: java.lang.Throwable -> L10
            r3.ifield1 = r4     // Catch: java.lang.Throwable -> L10
            r3.ifield2 = r5     // Catch: java.lang.Throwable -> L10
            r3.bfield1 = r7     // Catch: java.lang.Throwable -> L10
            r3.sfield1 = r6     // Catch: java.lang.Throwable -> L10
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L10
            r3.timeStamp = r4     // Catch: java.lang.Throwable -> L10
        L36:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            return
        L38:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L10
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.tempPowerSaveWlChanged(int, int,"
                    + " java.lang.String, boolean):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void uidFirewallRuleChanged(int r4, int r5, int r6) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGV     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r3.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r5 != r1) goto L17
            goto Le
        Lc:
            r3 = move-exception
            goto L35
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.String r2 = getUidFirewallRuleChangedLog(r4, r5, r6)     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.v(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L17:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r3 = r3.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r3 = r3.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r3 != 0) goto L20
            goto L33
        L20:
            r1 = 0
            r3.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 11
            r3.type = r1     // Catch: java.lang.Throwable -> Lc
            r3.ifield1 = r4     // Catch: java.lang.Throwable -> Lc
            r3.ifield2 = r5     // Catch: java.lang.Throwable -> Lc
            r3.ifield3 = r6     // Catch: java.lang.Throwable -> Lc
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r3.timeStamp = r4     // Catch: java.lang.Throwable -> Lc
        L33:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L35:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.uidFirewallRuleChanged(int, int,"
                    + " int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void uidPolicyChanged(int r4, int r5, int r6) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGV     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r3.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r4 != r1) goto L17
            goto Le
        Lc:
            r3 = move-exception
            goto L34
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.String r2 = getPolicyChangedLog(r4, r5, r6)     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.v(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L17:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r3 = r3.mEventsBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r3 = r3.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r3 != 0) goto L20
            goto L32
        L20:
            r1 = 0
            r3.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 3
            r3.type = r1     // Catch: java.lang.Throwable -> Lc
            r3.ifield1 = r4     // Catch: java.lang.Throwable -> Lc
            r3.ifield2 = r5     // Catch: java.lang.Throwable -> Lc
            r3.ifield3 = r6     // Catch: java.lang.Throwable -> Lc
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r3.timeStamp = r4     // Catch: java.lang.Throwable -> Lc
        L32:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L34:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.uidPolicyChanged(int, int,"
                    + " int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void uidStateChanged(int r5, int r6, int r7, long r8) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            boolean r1 = com.android.server.net.NetworkPolicyLogger.LOGV     // Catch: java.lang.Throwable -> Lc
            if (r1 != 0) goto Le
            int r1 = r4.mDebugUid     // Catch: java.lang.Throwable -> Lc
            if (r5 != r1) goto L41
            goto Le
        Lc:
            r4 = move-exception
            goto L60
        Le:
            java.lang.String r1 = "NetworkPolicy"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc
            r2.<init>()     // Catch: java.lang.Throwable -> Lc
            r2.append(r5)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = " state changed to "
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            int r3 = com.android.server.am.ProcessList.PAGE_SIZE     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = android.app.ActivityManager.procStateToString(r6)     // Catch: java.lang.Throwable -> Lc
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = ",seq="
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            r2.append(r8)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = ",cap="
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r3 = android.app.ActivityManager.getCapabilitiesSummary(r7)     // Catch: java.lang.Throwable -> Lc
            r2.append(r3)     // Catch: java.lang.Throwable -> Lc
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc
            android.util.Slog.v(r1, r2)     // Catch: java.lang.Throwable -> Lc
        L41:
            com.android.server.net.NetworkPolicyLogger$LogBuffer r4 = r4.mUidStateChangeBuffer     // Catch: java.lang.Throwable -> Lc
            com.android.server.net.NetworkPolicyLogger$Data r4 = r4.getNextSlot()     // Catch: java.lang.Throwable -> Lc
            if (r4 != 0) goto L4a
            goto L5e
        L4a:
            r1 = 0
            r4.sfield1 = r1     // Catch: java.lang.Throwable -> Lc
            r1 = 2
            r4.type = r1     // Catch: java.lang.Throwable -> Lc
            r4.ifield1 = r5     // Catch: java.lang.Throwable -> Lc
            r4.ifield2 = r6     // Catch: java.lang.Throwable -> Lc
            r4.ifield3 = r7     // Catch: java.lang.Throwable -> Lc
            r4.lfield1 = r8     // Catch: java.lang.Throwable -> Lc
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lc
            r4.timeStamp = r5     // Catch: java.lang.Throwable -> Lc
        L5e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L60:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.net.NetworkPolicyLogger.uidStateChanged(int, int, int,"
                    + " long):void");
    }
}
