package com.samsung.android.server.packagefeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
