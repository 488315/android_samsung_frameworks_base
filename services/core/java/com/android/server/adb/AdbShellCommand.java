package com.android.server.adb;

import com.android.modules.utils.BasicShellCommandHandler;

import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdbShellCommand extends BasicShellCommandHandler {
    public final AdbService mService;

    public AdbShellCommand(AdbService adbService) {
        Objects.requireNonNull(adbService);
        this.mService = adbService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (str.equals("is-wifi-qr-supported")) {
            outPrintWriter.println(Boolean.toString(this.mService.isAdbWifiQrSupported()));
            return 0;
        }
        if (!str.equals("is-wifi-supported")) {
            return handleDefaultCommands(str);
        }
        outPrintWriter.println(Boolean.toString(this.mService.isAdbWifiSupported()));
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Adb service commands:");
        outPrintWriter.println("  help or -h");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  is-wifi-supported");
        outPrintWriter.println("    Returns \"true\" if adb over wifi is supported.");
        outPrintWriter.println("  is-wifi-qr-supported");
        outPrintWriter.println("    Returns \"true\" if adb over wifi + QR pairing is supported.");
        outPrintWriter.println();
    }
}
