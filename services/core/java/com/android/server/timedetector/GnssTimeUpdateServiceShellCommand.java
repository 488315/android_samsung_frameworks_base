package com.android.server.timedetector;

import android.os.Binder;
import android.os.ShellCommand;

import java.io.PrintWriter;
import java.util.Objects;

public final class GnssTimeUpdateServiceShellCommand extends ShellCommand {
    public final GnssTimeUpdateService mGnssTimeUpdateService;

    public GnssTimeUpdateServiceShellCommand(GnssTimeUpdateService gnssTimeUpdateService) {
        Objects.requireNonNull(gnssTimeUpdateService);
        this.mGnssTimeUpdateService = gnssTimeUpdateService;
    }

    public final int onCommand(String str) {
        if (str != null && str.equals("start_gnss_listening")) {
            GnssTimeUpdateService gnssTimeUpdateService = this.mGnssTimeUpdateService;
            gnssTimeUpdateService.mContext.enforceCallingPermission(
                    "android.permission.SET_TIME", "Start GNSS listening");
            gnssTimeUpdateService.mLocalLog.log("startGnssListening() called");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean startGnssListeningInternal =
                        gnssTimeUpdateService.startGnssListeningInternal();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                getOutPrintWriter().println(startGnssListeningInternal);
                return 0;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return handleDefaultCommands(str);
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf(
                "Network Time Update Service (%s) commands:\n", "gnss_time_update_service");
        outPrintWriter.printf("  help\n", new Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "start_gnss_listening");
        outPrintWriter.printf(
                "    Forces the service in to GNSS listening mode (if it isn't already).\n",
                new Object[0]);
        outPrintWriter.printf(
                "    Prints true if the service is listening after this command.\n", new Object[0]);
        outPrintWriter.println();
    }
}
