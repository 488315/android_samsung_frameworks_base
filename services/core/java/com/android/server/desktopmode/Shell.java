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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ae, code lost:

       if (r15.equals("clear") == false) goto L47;
    */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0309 A[Catch: IllegalArgumentException -> 0x0355, TryCatch #1 {IllegalArgumentException -> 0x0355, blocks: (B:112:0x0287, B:114:0x0291, B:115:0x029d, B:117:0x02a3, B:118:0x02a8, B:126:0x02d2, B:127:0x02d7, B:135:0x02fe, B:136:0x0303, B:137:0x0309, B:138:0x02e9, B:141:0x02f1, B:144:0x030f, B:146:0x032d, B:147:0x0332, B:151:0x034c, B:152:0x02b7, B:155:0x02c2, B:149:0x0337), top: B:111:0x0287, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x030f A[Catch: IllegalArgumentException -> 0x0355, TryCatch #1 {IllegalArgumentException -> 0x0355, blocks: (B:112:0x0287, B:114:0x0291, B:115:0x029d, B:117:0x02a3, B:118:0x02a8, B:126:0x02d2, B:127:0x02d7, B:135:0x02fe, B:136:0x0303, B:137:0x0309, B:138:0x02e9, B:141:0x02f1, B:144:0x030f, B:146:0x032d, B:147:0x0332, B:151:0x034c, B:152:0x02b7, B:155:0x02c2, B:149:0x0337), top: B:111:0x0287, inners: #2 }] */
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
