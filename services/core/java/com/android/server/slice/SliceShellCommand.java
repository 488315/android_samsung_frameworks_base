package com.android.server.slice;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ShellCommand;
import android.util.ArraySet;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.PrintWriter;

public final class SliceShellCommand extends ShellCommand {
    public final SliceManagerService mService;

    public SliceShellCommand(SliceManagerService sliceManagerService) {
        this.mService = sliceManagerService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        if (!str.equals("get-permissions")) {
            return 0;
        }
        String nextArgRequired = getNextArgRequired();
        if (Binder.getCallingUid() == 2000 || Binder.getCallingUid() == 0) {
            Context context = this.mService.mContext;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Uri build = new Uri.Builder().scheme("content").authority(nextArgRequired).build();
                if ("vnd.android.slice".equals(context.getContentResolver().getType(build))) {
                    Bundle call =
                            context.getContentResolver()
                                    .call(build, "get_permissions", (String) null, (Bundle) null);
                    if (call != null) {
                        String[] stringArray =
                                call.getStringArray(KnoxCustomManagerService.SPCM_KEY_RESULT);
                        PrintWriter outPrintWriter = getOutPrintWriter();
                        ArraySet arraySet = new ArraySet();
                        if (stringArray != null && stringArray.length != 0) {
                            for (PackageInfo packageInfo :
                                    context.getPackageManager()
                                            .getPackagesHoldingPermissions(stringArray, 0)) {
                                outPrintWriter.println(packageInfo.packageName);
                                arraySet.add(packageInfo.packageName);
                            }
                        }
                        for (String str2 : this.mService.getAllPackagesGranted(nextArgRequired)) {
                            if (!arraySet.contains(str2)) {
                                outPrintWriter.println(str2);
                                arraySet.add(str2);
                            }
                        }
                        return 0;
                    }
                    getOutPrintWriter().println("An error occurred getting permissions");
                } else {
                    getOutPrintWriter().println(nextArgRequired + " is not a slice provider");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            getOutPrintWriter().println("Only shell can get permissions");
        }
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Status bar commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-permissions <authority>",
                "    List the pkgs that have permission to an authority.",
                "");
    }
}
