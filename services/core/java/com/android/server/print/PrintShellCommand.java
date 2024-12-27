package com.android.server.print;

import android.os.RemoteException;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.print.IPrintManager;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PrintShellCommand extends ShellCommand {
    public final IPrintManager mService;

    public PrintShellCommand(IPrintManager iPrintManager) {
        this.mService = iPrintManager;
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
            try {
                getOutPrintWriter().println(Boolean.toString(this.mService.getBindInstantServiceAllowed(parseUserId.intValue())));
                return 0;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return 0;
            }
        }
        if (!str.equals("set-bind-instant-service-allowed")) {
            return -1;
        }
        Integer parseUserId2 = parseUserId();
        if (parseUserId2 != null) {
            String nextArgRequired = getNextArgRequired();
            if (nextArgRequired != null) {
                try {
                    this.mService.setBindInstantServiceAllowed(parseUserId2.intValue(), Boolean.parseBoolean(nextArgRequired));
                    return 0;
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                    return 0;
                }
            }
            getErrPrintWriter().println("Error: no true/false specified");
        }
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Print service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID>] true|false ");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Set whether binding to print services provided by instant apps is allowed.", "  get-bind-instant-service-allowed [--user <USER_ID>]", "    Get whether binding to print services provided by instant apps is allowed.");
    }

    public final Integer parseUserId() {
        String nextOption = getNextOption();
        if (nextOption == null) {
            return 0;
        }
        if (nextOption.equals("--user")) {
            return Integer.valueOf(UserHandle.parseUserArg(getNextArgRequired()));
        }
        getErrPrintWriter().println("Unknown option: ".concat(nextOption));
        return null;
    }
}
