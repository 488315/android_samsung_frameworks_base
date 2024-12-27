package com.android.server.smartspace;

import android.os.ShellCommand;

import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SmartspaceManagerServiceShellCommand extends ShellCommand {
    public final SmartspaceManagerService mService;

    public SmartspaceManagerServiceShellCommand(SmartspaceManagerService smartspaceManagerService) {
        this.mService = smartspaceManagerService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (!str.equals("set")) {
            return handleDefaultCommands(str);
        }
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        if (nextArgRequired.equals("temporary-service")) {
            int parseInt = Integer.parseInt(getNextArgRequired());
            String nextArg = getNextArg();
            if (nextArg == null) {
                this.mService.resetTemporaryService(parseInt);
                outPrintWriter.println("SmartspaceService temporarily reset. ");
                return 0;
            }
            int parseInt2 = Integer.parseInt(getNextArgRequired());
            this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
            outPrintWriter.println(
                    ActiveServices$$ExternalSyntheticOutline0.m(
                            parseInt2,
                            nextArg,
                            " for ",
                            "ms",
                            new StringBuilder("SmartspaceService temporarily set to ")));
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("SmartspaceManagerService commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println(
                    "    Temporarily (for DURATION ms) changes the service implemtation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
