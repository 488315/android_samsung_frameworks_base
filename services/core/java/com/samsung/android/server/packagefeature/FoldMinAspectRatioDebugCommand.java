package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

public final class FoldMinAspectRatioDebugCommand extends PackageFeatureDebugCommand {
    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final String adjustExtra(String str) {
        return str != null ? str : "16:9";
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final boolean assertValidOptions(String str, String[] strArr, PrintWriter printWriter) {
        if ((strArr.length == 1 || strArr.length == 2) && strArr[0] != null) {
            return true;
        }
        PackageFeatureDebugCommand.printOptions(printWriter, str, "longSize:shortSize");
        return false;
    }
}
