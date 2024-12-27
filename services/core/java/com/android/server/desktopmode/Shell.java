package com.android.server.desktopmode;

import android.content.ContentResolver;
import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class Shell extends ShellCommand {
    public HardwareManager mHwManager;
    public MultiResolutionManager mMultiResolutionManager;
    public ContentResolver mResolver;
    public UiManager mUiManager;

    public final void desktopModeSettingsPrintUsage() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("USAGE: settings get KEY");
        outPrintWriter.println("       settings put KEY VALUE");
        outPrintWriter.println("       settings delete KEY");
        outPrintWriter.println("       settings clear");
        outPrintWriter.println("       settings list");
    }

    public final void multiResolutionManagerPrintUsage() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("USAGE: resolution");
        outPrintWriter.println("       resolution set WIDTH HEIGHT DENSITY");
        outPrintWriter.println("       resolution supportAll [on|off]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ae, code lost:

       if (r15.equals("clear") == false) goto L47;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 1000
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.desktopmode.Shell.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("DesktopModeService commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter, "  on", "    Enable desktop mode.", "", "  off");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Disable desktop mode.",
                "",
                "  toggle",
                "    Toggle desktop mode.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter, "", "  settings", "    Manage desktop mode settings.", "");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter, "  resolution", "    Manage desktop mode resolution.", "", "  ui");
        outPrintWriter.println("    Manage desktop mode UI elements.");
    }

    public final void uiManagerPrintUsage() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("USAGE: ui showDialog DISPLAYID TYPE");
        outPrintWriter.println("       ui showNotification TYPE");
        outPrintWriter.println("       ui showOverlay WHERE TYPE");
        outPrintWriter.println("       ui startActivity DISPLAYID TYPE");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "       ui dismissDialog TYPE",
                "       ui dismissOverlay WHERE TYPE",
                "       ui removeNotification TYPE",
                "       ui finishActivity TYPE");
    }
}
