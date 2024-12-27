package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
