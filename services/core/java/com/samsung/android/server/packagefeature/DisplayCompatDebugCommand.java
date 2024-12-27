package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
