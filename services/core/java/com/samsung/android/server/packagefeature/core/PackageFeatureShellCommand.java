package com.samsung.android.server.packagefeature.core;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureShellCommand {
    public final PackageFeatureController mImpl;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
