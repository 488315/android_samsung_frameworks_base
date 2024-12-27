package com.android.server.contentsuggestions;

import android.os.ShellCommand;

import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContentSuggestionsManagerServiceShellCommand extends ShellCommand {
    public final ContentSuggestionsManagerService mService;

    public ContentSuggestionsManagerServiceShellCommand(
            ContentSuggestionsManagerService contentSuggestionsManagerService) {
        this.mService = contentSuggestionsManagerService;
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        if (str.equals("get")) {
            String nextArgRequired = getNextArgRequired();
            nextArgRequired.getClass();
            if (!nextArgRequired.equals("default-service-enabled")) {
                outPrintWriter.println("Invalid get: ".concat(nextArgRequired));
                return -1;
            }
            outPrintWriter.println(
                    this.mService.isDefaultServiceEnabled(Integer.parseInt(getNextArgRequired())));
            return 0;
        }
        if (!str.equals("set")) {
            return handleDefaultCommands(str);
        }
        String nextArgRequired2 = getNextArgRequired();
        nextArgRequired2.getClass();
        if (nextArgRequired2.equals("default-service-enabled")) {
            this.mService.setDefaultServiceEnabled(
                    Integer.parseInt(getNextArgRequired()), Boolean.parseBoolean(getNextArg()));
        } else {
            if (!nextArgRequired2.equals("temporary-service")) {
                outPrintWriter.println("Invalid set: ".concat(nextArgRequired2));
                return -1;
            }
            int parseInt = Integer.parseInt(getNextArgRequired());
            String nextArg = getNextArg();
            if (nextArg == null) {
                this.mService.resetTemporaryService(parseInt);
            } else {
                int parseInt2 = Integer.parseInt(getNextArgRequired());
                this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
                outPrintWriter.println(
                        ActiveServices$$ExternalSyntheticOutline0.m(
                                parseInt2,
                                nextArg,
                                " for ",
                                "ms",
                                new StringBuilder(
                                        "ContentSuggestionsService temporarily set to ")));
            }
        }
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("ContentSuggestionsManagerService commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println(
                    "    Temporarily (for DURATION ms) changes the service implementation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-service-enabled USER_ID");
            outPrintWriter.println(
                    "    Checks whether the default service is enabled for the user.");
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
