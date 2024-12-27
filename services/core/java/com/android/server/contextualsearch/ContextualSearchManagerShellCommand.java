package com.android.server.contextualsearch;

import android.os.Looper;
import android.os.ShellCommand;
import android.provider.Settings;

import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContextualSearchManagerShellCommand extends ShellCommand {
    public final ContextualSearchManagerService mService;

    public ContextualSearchManagerShellCommand(
            ContextualSearchManagerService contextualSearchManagerService) {
        this.mService = contextualSearchManagerService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (!str.equals("set")) {
            return handleDefaultCommands(str);
        }
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        if (nextArgRequired.equals("temporary-package")) {
            String nextArg = getNextArg();
            if (nextArg == null) {
                this.mService.resetTemporaryPackage();
                outPrintWriter.println("ContextualSearchManagerService reset.");
                return 0;
            }
            int parseInt = Integer.parseInt(getNextArgRequired());
            ContextualSearchManagerService contextualSearchManagerService = this.mService;
            synchronized (contextualSearchManagerService) {
                try {
                    ContextualSearchManagerService.enforceOverridingPermission(
                            "setTemporaryPackage");
                    if (parseInt > 120000) {
                        throw new IllegalArgumentException(
                                "Max duration is 120000 (called with " + parseInt + ")");
                    }
                    ContextualSearchManagerService.AnonymousClass2 anonymousClass2 =
                            contextualSearchManagerService.mTemporaryHandler;
                    if (anonymousClass2 == null) {
                        contextualSearchManagerService.mTemporaryHandler =
                                new ContextualSearchManagerService.AnonymousClass2(
                                        contextualSearchManagerService, Looper.getMainLooper(), 0);
                    } else {
                        anonymousClass2.removeMessages(0);
                    }
                    contextualSearchManagerService.mTemporaryPackage = nextArg;
                    Settings.Secure.putString(
                            contextualSearchManagerService.mContext.getContentResolver(),
                            "contextual_search_package",
                            contextualSearchManagerService.getContextualSearchPackageName());
                    contextualSearchManagerService.mTemporaryHandler.sendEmptyMessageDelayed(
                            0, parseInt);
                } catch (Throwable th) {
                    throw th;
                }
            }
            outPrintWriter.println(
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            parseInt,
                            "ContextualSearchManagerService temporarily set to ",
                            nextArg,
                            " for ",
                            "ms"));
        } else if (nextArgRequired.equals("token-duration")) {
            String nextArg2 = getNextArg();
            if (nextArg2 == null) {
                this.mService.setTokenValidDurationMs(600000);
                outPrintWriter.println("ContextualSearchManagerService token duration reset.");
                return 0;
            }
            int parseInt2 = Integer.parseInt(nextArg2);
            this.mService.setTokenValidDurationMs(parseInt2);
            outPrintWriter.println(
                    "ContextualSearchManagerService temporarily set token duration to "
                            + parseInt2
                            + "ms");
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("ContextualSearchService commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-package [PACKAGE_NAME DURATION]");
            outPrintWriter.println(
                    "    Temporarily (for DURATION ms) changes the Contextual Search"
                        + " implementation.");
            outPrintWriter.println("    To reset, call without any arguments.");
            outPrintWriter.println("  set token-duration [DURATION]");
            outPrintWriter.println(
                    "    Changes the Contextual Search token duration to DURATION ms.");
            outPrintWriter.println("    To reset, call without any arguments.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
