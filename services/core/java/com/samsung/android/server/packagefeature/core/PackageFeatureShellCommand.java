package com.samsung.android.server.packagefeature.core;

import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureDebugCommand;
import java.io.PrintWriter;

/* compiled from: PackageFeaturesShellCommand.java */
/* loaded from: classes2.dex */
public class PackageFeatureShellCommand {
    public final PackageFeatureControllerImpl mImpl;

    /* compiled from: PackageFeaturesShellCommand.java */
    enum Command {
        PACKAGE_FEATURE("-packagefeature"),
        PACKAGE_FEATURE_OPTION_FORCE_UPDATE("ForceUpdate"),
        PACKAGE_FEATURE_OPTION_POLICY_RESET("PolicyReset"),
        SET_POLICY_DISABLED_COMMAND("-setPolicyDisabled");

        String mCommand;

        Command(String str) {
            this.mCommand = str;
        }
    }

    public PackageFeatureShellCommand(PackageFeatureControllerImpl packageFeatureControllerImpl) {
        this.mImpl = packageFeatureControllerImpl;
    }

    public boolean executeShellCommand(PrintWriter printWriter, String[] strArr, String str) {
        PackageFeatureDebugCommand packageFeatureDebugCommand;
        if (Command.PACKAGE_FEATURE.mCommand.equals(str)) {
            this.mImpl.dump(printWriter);
            if (strArr.length != 1) {
                return true;
            }
            printWriter.println();
            String str2 = strArr[0];
            if (Command.PACKAGE_FEATURE_OPTION_FORCE_UPDATE.mCommand.equals(str2)) {
                printWriter.println("Started update.");
                this.mImpl.initializeGroups();
                this.mImpl.updateGroupData(null);
                return true;
            }
            if (Command.PACKAGE_FEATURE_OPTION_POLICY_RESET.mCommand.equals(str2)) {
                printWriter.println("Started reset.");
                printWriter.println(this.mImpl.deleteCacheFiles());
                this.mImpl.initializeGroups();
                this.mImpl.updateGroupData(null);
            }
            return true;
        }
        for (PackageFeature packageFeature : PackageFeature.values()) {
            if (packageFeature.mEnabled && (packageFeatureDebugCommand = packageFeature.mDebugCommand) != null) {
                for (String str3 : packageFeatureDebugCommand.mCommands) {
                    if (str3.equals(str)) {
                        if (!packageFeatureDebugCommand.assertValidOptions(printWriter, strArr, str)) {
                            return true;
                        }
                        printWriter.println(this.mImpl.executeDebugMode(packageFeature, strArr[0].split(XmlUtils.STRING_ARRAY_SEPARATOR), packageFeatureDebugCommand.adjustExtra(str, strArr.length > 1 ? strArr[1] : null)));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
