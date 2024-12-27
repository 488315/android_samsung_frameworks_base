package com.android.server.display.color;

import android.content.pm.PackageManagerInternal;
import android.os.Message;
import android.os.ShellCommand;

import com.android.server.LocalServices;

public final class ColorDisplayShellCommand extends ShellCommand {
    public final ColorDisplayService mService;

    public ColorDisplayShellCommand(ColorDisplayService colorDisplayService) {
        this.mService = colorDisplayService;
    }

    public final int getLevel() {
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: Required argument LEVEL is unspecified");
            return -1;
        }
        try {
            int parseInt = Integer.parseInt(nextArg);
            if (parseInt >= 0 && parseInt <= 100) {
                return parseInt;
            }
            getErrPrintWriter()
                    .println("Error: LEVEL argument must be an integer between 0 and 100");
            return -1;
        } catch (NumberFormatException unused) {
            getErrPrintWriter().println("Error: LEVEL argument is not an integer");
            return -1;
        }
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        if (!str.equals("set-layer-saturation")) {
            if (!str.equals("set-saturation")) {
                return handleDefaultCommands(str);
            }
            int level = getLevel();
            if (level == -1) {
                return -1;
            }
            ColorDisplayService colorDisplayService = this.mService;
            Message obtainMessage = colorDisplayService.mHandler.obtainMessage(4);
            obtainMessage.arg1 = level;
            colorDisplayService.mHandler.sendMessage(obtainMessage);
            return 0;
        }
        int level2 = getLevel();
        if (level2 != -1) {
            String nextArg = getNextArg();
            if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                            .getPackage(nextArg)
                    == null) {
                nextArg = null;
            }
            if (nextArg == null) {
                getErrPrintWriter()
                        .println("Error: CALLER_PACKAGE must be an installed package name");
            } else {
                String nextArg2 = getNextArg();
                String str2 =
                        ((PackageManagerInternal)
                                                        LocalServices.getService(
                                                                PackageManagerInternal.class))
                                                .getPackage(nextArg2)
                                        != null
                                ? nextArg2
                                : null;
                if (str2 != null) {
                    this.mService.setAppSaturationLevelInternal(level2, nextArg, str2);
                    return 0;
                }
                getErrPrintWriter()
                        .println("Error: TARGET_PACKAGE must be an installed package name");
            }
        }
        return -1;
    }

    public final void onHelp() {
        getOutPrintWriter()
                .print(
                        "usage: cmd color_display SUBCOMMAND [ARGS]\n"
                            + "    help\n"
                            + "      Shows this message.\n"
                            + "    set-saturation LEVEL\n"
                            + "      Sets the device saturation to the given LEVEL, 0-100"
                            + " inclusive.\n"
                            + "    set-layer-saturation LEVEL CALLER_PACKAGE TARGET_PACKAGE\n"
                            + "      Sets the saturation LEVEL for all layers of the"
                            + " TARGET_PACKAGE, attributed\n"
                            + "      to the CALLER_PACKAGE. The lowest LEVEL from any"
                            + " CALLER_PACKAGE is applied.\n");
    }
}
