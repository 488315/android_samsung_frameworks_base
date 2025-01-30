package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class PackageFeatureDebugCommand {
    public final String[] mCommands;

    public String adjustExtra(String str, String str2) {
        return null;
    }

    public PackageFeatureDebugCommand(String... strArr) {
        this.mCommands = strArr;
    }

    public boolean assertValidOptions(PrintWriter printWriter, String[] strArr, String str) {
        return assertPackageNamesOnly(printWriter, strArr, str);
    }

    public boolean assertPackageNamesOnly(PrintWriter printWriter, String[] strArr, String str) {
        boolean z = false;
        if (strArr.length == 1 && strArr[0] != null) {
            z = true;
        }
        if (!z) {
            printOptions(printWriter, str, null);
        }
        return z;
    }

    public void printOptions(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str + " options:");
        printWriter.println(getCommandOptions("  ", str, " [packageName]", str2));
        printWriter.println(getCommandOptions("  ", str, " [packageName:packageName:...]", str2));
    }

    public String getCommandOptions(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        if (str4 != null) {
            sb.append(" [");
            sb.append(str4);
            sb.append("]");
        }
        return sb.toString();
    }
}
