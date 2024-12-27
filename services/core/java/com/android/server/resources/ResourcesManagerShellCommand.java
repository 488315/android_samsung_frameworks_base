package com.android.server.resources;

import android.content.res.IResourcesManager;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.util.Slog;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.io.PrintWriter;

public final class ResourcesManagerShellCommand extends ShellCommand {
    public final IResourcesManager mInterface;

    public ResourcesManagerShellCommand(IResourcesManager iResourcesManager) {
        this.mInterface = iResourcesManager;
    }

    public final int dumpResources() {
        String nextArgRequired = getNextArgRequired();
        try {
            ParcelFileDescriptor dup = ParcelFileDescriptor.dup(getOutFileDescriptor());
            try {
                final ConditionVariable conditionVariable = new ConditionVariable();
                if (this.mInterface.dumpResources(
                        nextArgRequired,
                        dup,
                        new RemoteCallback(
                                new RemoteCallback
                                        .OnResultListener() { // from class:
                                                              // com.android.server.resources.ResourcesManagerShellCommand$$ExternalSyntheticLambda0
                                    public final void onResult(Bundle bundle) {
                                        conditionVariable.open();
                                    }
                                },
                                (Handler) null))) {
                    conditionVariable.block(5000L);
                    if (dup == null) {
                        return 0;
                    }
                    dup.close();
                    return 0;
                }
                getErrPrintWriter().println("RESOURCES DUMP FAILED on process " + nextArgRequired);
                if (dup != null) {
                    dup.close();
                }
                return -1;
            } finally {
            }
        } catch (IOException e) {
            Slog.e("ResourcesManagerShellCommand", "Exception while dumping resources", e);
            getErrPrintWriter().println("Exception while dumping resources: " + e.getMessage());
            return -1;
        }
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            if (str.hashCode() == 3095028 && str.equals("dump")) {
                return dumpResources();
            }
            return handleDefaultCommands(str);
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, errPrintWriter);
            return -1;
        } catch (IllegalArgumentException e2) {
            errPrintWriter.println("Error: " + e2.getMessage());
            return -1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Resources manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  dump <PROCESS>");
        outPrintWriter.println(
                "    Dump the Resources objects in use as well as the history of Resources");
    }
}
