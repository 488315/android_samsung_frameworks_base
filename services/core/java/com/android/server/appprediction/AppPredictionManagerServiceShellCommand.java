package com.android.server.appprediction;

import android.os.ShellCommand;

import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppPredictionManagerServiceShellCommand extends ShellCommand {
    public final AppPredictionManagerService mService;

    public AppPredictionManagerServiceShellCommand(
            AppPredictionManagerService appPredictionManagerService) {
        this.mService = appPredictionManagerService;
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
                            new StringBuilder("AppPredictionService temporarily set to ")));
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("AppPredictionManagerService commands:");
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
