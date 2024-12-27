package com.android.server.pm;

import android.os.Handler;
import android.os.incremental.IncrementalManager;

import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.server.pm.lifecycle.PmCustomInjector;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda44
        implements PmCustomInjector.Producer, ApkChecksums.Injector.Producer {
    public final /* synthetic */ PackageManagerServiceInjector f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda44(
            PackageManagerServiceInjector packageManagerServiceInjector) {
        this.f$0 = packageManagerServiceInjector;
    }

    @Override // com.android.server.pm.ApkChecksums.Injector.Producer
    public Object produce() {
        PackageManagerServiceInjector packageManagerServiceInjector = this.f$0;
        return (IncrementalManager)
                packageManagerServiceInjector.mIncrementalManagerProducer.get(
                        packageManagerServiceInjector.mPackageManager,
                        packageManagerServiceInjector);
    }

    @Override // com.samsung.android.server.pm.lifecycle.PmCustomInjector.Producer
    public Object produce(
            PackageManagerService packageManagerService,
            PackageManagerServiceInjector packageManagerServiceInjector) {
        Handler handler = this.f$0.getHandler();
        Settings settings =
                (Settings)
                        packageManagerServiceInjector.mSettingsProducer.get(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        Objects.requireNonNull(settings);
        return new UnknownSourceAppManager(
                handler,
                new UnknownSourceAppManager.PackageSettingsDelegator(
                        new PackageManagerService$$ExternalSyntheticLambda55(1, settings)));
    }
}
