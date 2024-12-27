package com.android.server.recoverysystem;

import android.os.ShellCommand;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RecoverySystemShellCommand extends ShellCommand {
    public final RecoverySystemService mService;

    public RecoverySystemShellCommand(RecoverySystemService recoverySystemService) {
        this.mService = recoverySystemService;
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            boolean z = true;
            switch (str.hashCode()) {
                case -779212638:
                    if (str.equals("clear-lskf")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3649607:
                    if (str.equals("wipe")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1214227142:
                    if (str.equals("is-lskf-captured")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1256867232:
                    if (str.equals("request-lskf")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1405182928:
                    if (str.equals("reboot-and-apply")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                String nextArgRequired = getNextArgRequired();
                getOutPrintWriter()
                        .printf(
                                "Request LSKF for packageName: %s, status: %s\n",
                                nextArgRequired,
                                this.mService.requestLskf(nextArgRequired, null)
                                        ? "success"
                                        : "failure");
                return 0;
            }
            if (c == 1) {
                String nextArgRequired2 = getNextArgRequired();
                getOutPrintWriter()
                        .printf(
                                "Clear LSKF for packageName: %s, status: %s\n",
                                nextArgRequired2,
                                this.mService.clearLskf(nextArgRequired2) ? "success" : "failure");
                return 0;
            }
            if (c == 2) {
                String nextArgRequired3 = getNextArgRequired();
                getOutPrintWriter()
                        .printf(
                                "%s LSKF capture status: %s\n",
                                nextArgRequired3,
                                this.mService.isLskfCaptured(nextArgRequired3) ? "true" : "false");
                return 0;
            }
            if (c != 3) {
                if (c != 4) {
                    return handleDefaultCommands(str);
                }
                wipe();
                return 0;
            }
            String nextArgRequired4 = getNextArgRequired();
            if (this.mService.rebootWithLskf(nextArgRequired4, getNextArgRequired(), false) != 0) {
                z = false;
            }
            getOutPrintWriter()
                    .printf(
                            "%s Reboot and apply status: %s\n",
                            nextArgRequired4, z ? "success" : "failure");
            return 0;
        } catch (Exception e) {
            getErrPrintWriter().println("Error while executing command: ".concat(str));
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Recovery system commands:");
        outPrintWriter.println("  request-lskf <package_name>");
        outPrintWriter.println("  clear-lskf");
        outPrintWriter.println("  is-lskf-captured <package_name>");
        outPrintWriter.println("  reboot-and-apply <package_name> <reason>");
        outPrintWriter.println("  wipe <new filesystem type ext4/f2fs>");
    }

    public final void wipe() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        String nextArg = getNextArg();
        String concat =
                (nextArg == null || nextArg.isEmpty())
                        ? "--wipe_data"
                        : "--wipe_data\n--reformat_data=".concat(nextArg);
        outPrintWriter.println("Rebooting into recovery with " + concat.replaceAll("\n", " "));
        this.mService.rebootRecoveryWithCommand(concat);
    }
}
