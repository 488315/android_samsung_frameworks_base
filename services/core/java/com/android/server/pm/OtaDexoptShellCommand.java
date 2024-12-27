package com.android.server.pm;

import android.os.RemoteException;
import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OtaDexoptShellCommand extends ShellCommand {
    public final OtaDexoptService mInterface;

    public OtaDexoptShellCommand(OtaDexoptService otaDexoptService) {
        this.mInterface = otaDexoptService;
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1001078227:
                    if (str.equals("progress")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -318370553:
                    if (str.equals("prepare")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3089282:
                    if (str.equals("done")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3377907:
                    if (str.equals("next")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540684:
                    if (str.equals("step")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 856774308:
                    if (str.equals("cleanup")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                this.mInterface.prepare();
                getOutPrintWriter().println("Success");
                return 0;
            }
            if (c == 1) {
                this.mInterface.cleanup();
                return 0;
            }
            if (c == 2) {
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                if (this.mInterface.isDone()) {
                    outPrintWriter2.println("OTA complete.");
                } else {
                    outPrintWriter2.println("OTA incomplete.");
                }
                return 0;
            }
            if (c == 3) {
                this.mInterface.dexoptNextPackage();
                throw null;
            }
            if (c == 4) {
                getOutPrintWriter().println(this.mInterface.nextDexoptCommand());
                return 0;
            }
            if (c != 5) {
                return handleDefaultCommands(str);
            }
            getOutPrintWriter()
                    .format(Locale.ROOT, "%.2f", Float.valueOf(this.mInterface.getProgress()));
            return 0;
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, outPrintWriter);
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("OTA Dexopt (ota) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  prepare",
                "    Prepare an OTA dexopt pass, collecting all packages.",
                "  done",
                "    Replies whether the OTA is complete or not.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  step",
                "    OTA dexopt the next package.",
                "  next",
                "    Get parameters for OTA dexopt of the next package.");
        outPrintWriter.println("  cleanup");
        outPrintWriter.println("    Clean up internal states. Ends an OTA session.");
    }
}
