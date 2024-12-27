package com.samsung.android.server.packagefeature.core;

public final class PackageFeatureShellCommand {
    public final PackageFeatureController mImpl;

    enum Command {
        PACKAGE_FEATURE("-packagefeature"),
        PACKAGE_FEATURE_OPTION_FORCE_UPDATE("ForceUpdate"),
        PACKAGE_FEATURE_OPTION_POLICY_RESET("PolicyReset"),
        PACKAGE_FEATURE_OPTION_SHOW_PACKAGE_NAME("ShowPackageName"),
        /* JADX INFO: Fake field, exist only in values array */
        EF55("-setPolicyDisabled");

        String mCommand;

        Command(String str) {
            this.mCommand = str;
        }
    }

    public PackageFeatureShellCommand(PackageFeatureController packageFeatureController) {
        this.mImpl = packageFeatureController;
    }
}
