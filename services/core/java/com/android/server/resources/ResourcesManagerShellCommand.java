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
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class ResourcesManagerShellCommand extends ShellCommand {
    public final IResourcesManager mInterface;

    public ResourcesManagerShellCommand(IResourcesManager iResourcesManager) {
        this.mInterface = iResourcesManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023 A[Catch: RemoteException -> 0x002d, IllegalArgumentException -> 0x0043, TryCatch #2 {RemoteException -> 0x002d, IllegalArgumentException -> 0x0043, blocks: (B:7:0x000c, B:12:0x0023, B:14:0x0028, B:16:0x0016), top: B:6:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0028 A[Catch: RemoteException -> 0x002d, IllegalArgumentException -> 0x0043, TRY_LEAVE, TryCatch #2 {RemoteException -> 0x002d, IllegalArgumentException -> 0x0043, blocks: (B:7:0x000c, B:12:0x0023, B:14:0x0028, B:16:0x0016), top: B:6:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onCommand(String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            if (str.hashCode() == 3095028 && str.equals("dump")) {
                z = false;
                if (z) {
                    return dumpResources();
                }
                return handleDefaultCommands(str);
            }
            z = -1;
            if (z) {
            }
        } catch (RemoteException e) {
            errPrintWriter.println("Remote exception: " + e);
            return -1;
        } catch (IllegalArgumentException e2) {
            errPrintWriter.println("Error: " + e2.getMessage());
            return -1;
        }
    }

    public final int dumpResources() {
        String nextArgRequired = getNextArgRequired();
        try {
            ParcelFileDescriptor dup = ParcelFileDescriptor.dup(getOutFileDescriptor());
            try {
                final ConditionVariable conditionVariable = new ConditionVariable();
                if (!this.mInterface.dumpResources(nextArgRequired, dup, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.resources.ResourcesManagerShellCommand$$ExternalSyntheticLambda0
                    public final void onResult(Bundle bundle) {
                        conditionVariable.open();
                    }
                }, (Handler) null))) {
                    getErrPrintWriter().println("RESOURCES DUMP FAILED on process " + nextArgRequired);
                    if (dup != null) {
                        dup.close();
                    }
                    return -1;
                }
                conditionVariable.block(5000L);
                if (dup == null) {
                    return 0;
                }
                dup.close();
                return 0;
            } finally {
            }
        } catch (IOException e) {
            Slog.e("ResourcesManagerShellCommand", "Exception while dumping resources", e);
            getErrPrintWriter().println("Exception while dumping resources: " + e.getMessage());
            return -1;
        }
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Resources manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  dump <PROCESS>");
        outPrintWriter.println("    Dump the Resources objects in use as well as the history of Resources");
    }
}
