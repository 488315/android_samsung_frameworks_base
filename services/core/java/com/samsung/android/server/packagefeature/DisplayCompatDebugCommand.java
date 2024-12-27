package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

public final class DisplayCompatDebugCommand extends PackageFeatureDebugCommand {
    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final String adjustExtra(String str) {
        return "blocklist".equals(str) ? "b" : "w";
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final boolean assertValidOptions(String str, String[] strArr, PrintWriter printWriter) {
        String str2;
        if (strArr.length != 2 || strArr[0] == null || (str2 = strArr[1]) == null) {
            PackageFeatureDebugCommand.printOptions(printWriter, str, "blocklist|allowlist");
            return false;
        }
        if ("allowlist".equals(str2) || "blocklist".equals(str2)) {
            return true;
        }
        PackageFeatureDebugCommand.printOptions(printWriter, str, "blocklist|allowlist");
        return false;
    }
}
