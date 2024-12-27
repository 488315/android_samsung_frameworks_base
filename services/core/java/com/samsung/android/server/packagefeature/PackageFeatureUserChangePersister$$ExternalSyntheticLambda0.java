package com.samsung.android.server.packagefeature;

public final /* synthetic */ class PackageFeatureUserChangePersister$$ExternalSyntheticLambda0
        implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        PackageFeatureUserChangePersister.deleteLegacyFile(
                PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY,
                "NoWaitRotationPackageSetting");
        String str = PackageFeatureUserChangePersister.ASPECT_RATIO_DIRECTORY;
        PackageFeatureUserChangePersister.deleteLegacyFile(str, "PackageMap");
        PackageFeatureUserChangePersister.deleteLegacyFile(str, "CustomAspectRatioPackageMap");
    }
}
