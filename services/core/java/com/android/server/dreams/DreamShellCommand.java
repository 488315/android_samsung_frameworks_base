package com.android.server.dreams;

import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class DreamShellCommand extends ShellCommand {
    public final DreamManagerService mService;

    public DreamShellCommand(DreamManagerService dreamManagerService) {
        this.mService = dreamManagerService;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "onCommand:"
            java.lang.String r1 = "DreamShellCommand"
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r0, r6, r1)
            r0 = -1
            int r1 = r6.hashCode()     // Catch: java.lang.SecurityException -> L25
            r2 = -183711126(0xfffffffff50cca6a, float:-1.7847339E32)
            r3 = 1
            r4 = 0
            if (r1 == r2) goto L27
            r2 = 1473640970(0x57d5fa0a, float:4.70539773E14)
            if (r1 == r2) goto L1a
            goto L32
        L1a:
            java.lang.String r1 = "start-dreaming"
            boolean r1 = r6.equals(r1)     // Catch: java.lang.SecurityException -> L25
            if (r1 == 0) goto L32
            r1 = r4
            goto L33
        L25:
            r6 = move-exception
            goto L65
        L27:
            java.lang.String r1 = "stop-dreaming"
            boolean r1 = r6.equals(r1)     // Catch: java.lang.SecurityException -> L25
            if (r1 == 0) goto L32
            r1 = r3
            goto L33
        L32:
            r1 = r0
        L33:
            java.lang.String r2 = "Must be root to call Dream shell commands"
            if (r1 == 0) goto L53
            if (r1 == r3) goto L3e
            int r5 = r5.handleDefaultCommands(r6)     // Catch: java.lang.SecurityException -> L25
            return r5
        L3e:
            int r6 = android.os.Binder.getCallingUid()     // Catch: java.lang.SecurityException -> L25
            if (r6 != 0) goto L4d
            com.android.server.dreams.DreamManagerService r6 = r5.mService     // Catch: java.lang.SecurityException -> L25
            java.lang.String r1 = "stopping dream from shell"
            r6.stopDreamInternal(r1, r4)     // Catch: java.lang.SecurityException -> L25
            return r4
        L4d:
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.SecurityException -> L25
            r6.<init>(r2)     // Catch: java.lang.SecurityException -> L25
            throw r6     // Catch: java.lang.SecurityException -> L25
        L53:
            int r6 = android.os.Binder.getCallingUid()     // Catch: java.lang.SecurityException -> L25
            if (r6 != 0) goto L5f
            com.android.server.dreams.DreamManagerService r6 = r5.mService     // Catch: java.lang.SecurityException -> L25
            r6.requestDreamInternal()     // Catch: java.lang.SecurityException -> L25
            return r4
        L5f:
            java.lang.SecurityException r6 = new java.lang.SecurityException     // Catch: java.lang.SecurityException -> L25
            r6.<init>(r2)     // Catch: java.lang.SecurityException -> L25
            throw r6     // Catch: java.lang.SecurityException -> L25
        L65:
            java.io.PrintWriter r5 = r5.getOutPrintWriter()
            r5.println(r6)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.dreams.DreamShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Dream manager (dreams) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  start-dreaming");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Start the currently configured dream.",
                "  stop-dreaming",
                "      Stops any active dream");
    }
}
