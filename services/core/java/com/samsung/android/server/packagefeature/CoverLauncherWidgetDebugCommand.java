package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

public final class CoverLauncherWidgetDebugCommand extends PackageFeatureDebugCommand {
    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final String adjustExtra(String str) {
        return str;
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public final boolean assertValidOptions(String str, String[] strArr, PrintWriter printWriter) {
        if (strArr.length == 2 && strArr[0] != null && strArr[1] != null) {
            return true;
        }
        PackageFeatureDebugCommand.printOptions(printWriter, str, "Properties");
        return false;
    }
}
