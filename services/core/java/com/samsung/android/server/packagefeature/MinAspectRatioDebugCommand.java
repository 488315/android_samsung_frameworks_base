package com.samsung.android.server.packagefeature;

import java.io.PrintWriter;

/* compiled from: PackageFeatureDebugCommand.java */
/* loaded from: classes2.dex */
public abstract class MinAspectRatioDebugCommand extends PackageFeatureDebugCommand {
    public MinAspectRatioDebugCommand() {
        super("-setFixedAspectRatio", "-ratio");
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureDebugCommand
    public boolean assertValidOptions(PrintWriter printWriter, String[] strArr, String str) {
        if ((strArr.length == 1 || strArr.length == 2) && strArr[0] != null) {
            return true;
        }
        printOptions(printWriter, str, "longSize:shortSize");
        return false;
    }
}
