package com.android.server.coverage;

import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import org.jacoco.agent.rt.RT;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoverageService extends Binder {
    public static final boolean ENABLED;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverageCommand extends ShellCommand {
        public final int onCommand(String str) {
            if (!"dump".equals(str)) {
                if (!"reset".equals(str)) {
                    return handleDefaultCommands(str);
                }
                RT.getAgent().reset();
                getOutPrintWriter().println("Reset coverage data");
                return 0;
            }
            String nextArg = getNextArg();
            if (nextArg == null) {
                nextArg = "/data/local/tmp/coverage.ec";
            } else {
                File file = new File(nextArg);
                if (file.isDirectory()) {
                    nextArg = new File(file, "coverage.ec").getAbsolutePath();
                }
            }
            ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArg, "w");
            if (openFileForSystem != null) {
                try {
                    BufferedOutputStream bufferedOutputStream =
                            new BufferedOutputStream(
                                    new ParcelFileDescriptor.AutoCloseOutputStream(
                                            openFileForSystem));
                    try {
                        bufferedOutputStream.write(RT.getAgent().getExecutionData(false));
                        bufferedOutputStream.flush();
                        getOutPrintWriter().println("Dumped coverage data to " + nextArg);
                        bufferedOutputStream.close();
                        return 0;
                    } finally {
                    }
                } catch (IOException e) {
                    getErrPrintWriter().println("Failed to dump coverage data: " + e.getMessage());
                }
            }
            return -1;
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Coverage commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  dump [FILE]");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    Dump code coverage to FILE.",
                    "  reset",
                    "    Reset coverage information.");
        }
    }

    static {
        boolean z;
        try {
            Class.forName("org.jacoco.agent.rt.RT");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        ENABLED = z;
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new CoverageCommand()
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }
}
