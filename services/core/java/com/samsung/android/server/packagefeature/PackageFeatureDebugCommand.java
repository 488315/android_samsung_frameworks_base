package com.samsung.android.server.packagefeature;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class PackageFeatureDebugCommand {
    public final String[] mCommands;

    public PackageFeatureDebugCommand(String... strArr) {
        this.mCommands = strArr;
    }

    public static String getCommandOptions(String str, String str2, String str3) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", str, str2);
        if (str3 != null) {
            RCPManagerService$$ExternalSyntheticOutline0.m$1(m, " [", str3, "]");
        }
        return m.toString();
    }

    public static void printOptions(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str.concat(" options:"));
        printWriter.println(getCommandOptions(str, " [packageName]", str2));
        printWriter.println(getCommandOptions(str, " [packageName:packageName:...]", str2));
    }

    public String adjustExtra(String str) {
        return null;
    }

    public boolean assertValidOptions(String str, String[] strArr, PrintWriter printWriter) {
        boolean z = false;
        if (strArr.length == 1 && strArr[0] != null) {
            z = true;
        }
        if (!z) {
            printOptions(printWriter, str, null);
        }
        return z;
    }
}
