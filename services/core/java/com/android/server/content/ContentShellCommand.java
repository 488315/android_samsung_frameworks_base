package com.android.server.content;

import android.content.IContentService;
import android.os.RemoteException;
import android.os.ShellCommand;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class ContentShellCommand extends ShellCommand {
    public final IContentService mInterface;

    public ContentShellCommand(IContentService iContentService) {
        this.mInterface = iContentService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            if (str.hashCode() == -796331115 && str.equals("reset-today-stats")) {
                this.mInterface.resetTodayStats();
                return 0;
            }
            return handleDefaultCommands(str);
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, outPrintWriter);
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Content service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  reset-today-stats");
        outPrintWriter.println("    Reset 1-day sync stats.");
        outPrintWriter.println();
    }
}
