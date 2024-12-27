package com.android.server.apphibernation;

import android.os.ShellCommand;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class AppHibernationShellCommand extends ShellCommand {
    public final AppHibernationService mService;

    public AppHibernationShellCommand(AppHibernationService appHibernationService) {
        this.mService = appHibernationService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        int i = -2;
        if (!str.equals("set-state")) {
            if (!str.equals("get-state")) {
                return handleDefaultCommands(str);
            }
            boolean z = false;
            while (true) {
                String nextOption = getNextOption();
                if (nextOption == null) {
                    break;
                }
                if (nextOption.equals("--global")) {
                    z = true;
                } else if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption));
                }
            }
            String nextArgRequired = getNextArgRequired();
            if (nextArgRequired == null) {
                getErrPrintWriter().println("Error: No package specified");
                return -1;
            }
            getOutPrintWriter()
                    .println(
                            z
                                    ? this.mService.isHibernatingGlobally(nextArgRequired)
                                    : this.mService.isHibernatingForUser(nextArgRequired, i));
            return 0;
        }
        boolean z2 = false;
        while (true) {
            String nextOption2 = getNextOption();
            if (nextOption2 == null) {
                break;
            }
            if (nextOption2.equals("--global")) {
                z2 = true;
            } else if (nextOption2.equals("--user")) {
                i = UserHandle.parseUserArg(getNextArgRequired());
            } else {
                getErrPrintWriter().println("Error: Unknown option: ".concat(nextOption2));
            }
        }
        String nextArgRequired2 = getNextArgRequired();
        if (nextArgRequired2 == null) {
            getErrPrintWriter().println("Error: no package specified");
            return -1;
        }
        String nextArgRequired3 = getNextArgRequired();
        if (nextArgRequired3 == null) {
            getErrPrintWriter().println("Error: No state to set specified");
            return -1;
        }
        boolean parseBoolean = Boolean.parseBoolean(nextArgRequired3);
        if (z2) {
            this.mService.setHibernatingGlobally(nextArgRequired2, parseBoolean);
        } else {
            this.mService.setHibernatingForUser(nextArgRequired2, i, parseBoolean);
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("App hibernation (app_hibernation) commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  set-state [--user USER_ID] [--global] PACKAGE true|false",
                "    Sets the hibernation state of the package to value specified. Optionally",
                "    may specify a user id or set global hibernation state.",
                "");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-state [--user USER_ID] [--global] PACKAGE",
                "    Gets the hibernation state of the package. Optionally may specify a user",
                "    id or request global hibernation state.",
                "");
    }
}
