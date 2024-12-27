package com.android.server.flags;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class FlagsShellCommand {
    public final FlagOverrideStore mFlagStore;

    public FlagsShellCommand(FlagOverrideStore flagOverrideStore) {
        this.mFlagStore = flagOverrideStore;
    }

    public static void printHelp(PrintWriter printWriter) {
        printWriter.println("Feature Flags command, allowing listing, setting, getting, and erasing of");
        printWriter.println("local flag overrides on a device.");
        printWriter.println();
        printWriter.println("Commands:");
        printWriter.println("  list [namespace]");
        printWriter.println("    List all flag overrides. Namespace is optional.");
        printWriter.println();
        printWriter.println("  get <namespace> <name>");
        printWriter.println("    Return the string value of a specific flag, or <unset>");
        printWriter.println();
        printWriter.println("  set <namespace> <name> <value>");
        printWriter.println("    Set a specific flag");
        printWriter.println();
        printWriter.println("  erase <namespace> <name>");
        printWriter.println("    Unset a specific flag");
        printWriter.flush();
    }

    public static boolean validateNumArguments(String[] strArr, int i, int i2, String str, PrintWriter printWriter) {
        int length = strArr.length - 1;
        if (length < i) {
            printWriter.println(AccountManagerService$$ExternalSyntheticOutline0.m(i, "Less than ", " arguments provided for \"", str, "\" command."));
            return false;
        }
        if (length <= i2) {
            return true;
        }
        printWriter.println(AccountManagerService$$ExternalSyntheticOutline0.m(i2, "More than ", " arguments provided for \"", str, "\" command."));
        return false;
    }
}
