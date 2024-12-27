package com.android.server.accounts;

import android.app.ActivityManager;
import android.os.ShellCommand;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AccountManagerServiceShellCommand extends ShellCommand {
    public final AccountManagerService mService;

    public AccountManagerServiceShellCommand(AccountManagerService accountManagerService) {
        this.mService = accountManagerService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        if (str.equals("get-bind-instant-service-allowed")) {
            Integer parseUserId = parseUserId();
            if (parseUserId == null) {
                return -1;
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            AccountManagerService accountManagerService = this.mService;
            outPrintWriter.println(
                    Boolean.toString(
                            accountManagerService.mAuthenticatorCache.getBindInstantServiceAllowed(
                                    parseUserId.intValue())));
            return 0;
        }
        if (!str.equals("set-bind-instant-service-allowed")) {
            return -1;
        }
        Integer parseUserId2 = parseUserId();
        if (parseUserId2 != null) {
            String nextArgRequired = getNextArgRequired();
            if (nextArgRequired != null) {
                AccountManagerService accountManagerService2 = this.mService;
                accountManagerService2.mAuthenticatorCache.setBindInstantServiceAllowed(
                        parseUserId2.intValue(), Boolean.parseBoolean(nextArgRequired));
                return 0;
            }
            getErrPrintWriter().println("Error: no true/false specified");
        }
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Account manager service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println(
                "  set-bind-instant-service-allowed [--user <USER_ID> (current user if not"
                    + " specified)] true|false ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Set whether binding to services provided by instant apps is allowed.",
                "  get-bind-instant-service-allowed [--user <USER_ID> (current user if not"
                    + " specified)]",
                "    Get whether binding to services provided by instant apps is allowed.");
    }

    public final Integer parseUserId() {
        String nextOption = getNextOption();
        if (nextOption == null) {
            return Integer.valueOf(ActivityManager.getCurrentUser());
        }
        if (!nextOption.equals("--user")) {
            getErrPrintWriter().println("Unknown option: ".concat(nextOption));
            return null;
        }
        int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
        if (parseUserArg == -2) {
            return Integer.valueOf(ActivityManager.getCurrentUser());
        }
        if (parseUserArg == -1) {
            getErrPrintWriter().println("USER_ALL not supported. Specify a user.");
            return null;
        }
        if (parseUserArg >= 0) {
            return Integer.valueOf(parseUserArg);
        }
        AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(
                getErrPrintWriter(), "Invalid user: ", parseUserArg);
        return null;
    }
}
