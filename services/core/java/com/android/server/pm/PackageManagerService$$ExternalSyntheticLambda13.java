package com.android.server.pm;

import android.content.Context;
import android.os.ServiceManager;
import android.util.ArrayMap;

import com.android.server.LocalServices;
import com.android.server.pm.permission.PermissionManagerService;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda13
        implements PackageManagerServiceInjector.Producer,
                PackageManagerServiceInjector.ServiceProducer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda13(
            Context context, int i) {
        this.$r8$classId = i;
        this.f$0 = context;
    }

    @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
    public Object produce(
            PackageManagerService packageManagerService,
            PackageManagerServiceInjector packageManagerServiceInjector) {
        switch (this.$r8$classId) {
            case 0:
                return new DefaultAppProvider(
                        new PackageManagerService$$ExternalSyntheticLambda55(2, this.f$0),
                        new PackageManagerService$$ExternalSyntheticLambda42());
            case 1:
                Context context = this.f$0;
                ArrayMap arrayMap =
                        packageManagerServiceInjector.getSystemConfig().mAvailableFeatures;
                ConcurrentHashMap concurrentHashMap =
                        PermissionManagerService.sRunningAttributionSources;
                PermissionManagerService.PermissionManagerServiceInternalImpl
                        permissionManagerServiceInternalImpl =
                                (PermissionManagerService.PermissionManagerServiceInternalImpl)
                                        LocalServices.getService(
                                                PermissionManagerService
                                                        .PermissionManagerServiceInternalImpl
                                                        .class);
                if (permissionManagerServiceInternalImpl != null) {
                    return permissionManagerServiceInternalImpl;
                }
                if (((PermissionManagerService) ServiceManager.getService("permissionmgr"))
                        == null) {
                    ServiceManager.addService(
                            "permissionmgr", new PermissionManagerService(context, arrayMap));
                    ServiceManager.addService(
                            "permission_checker",
                            new PermissionManagerService.PermissionCheckerService(context));
                }
                return (PermissionManagerService.PermissionManagerServiceInternalImpl)
                        LocalServices.getService(
                                PermissionManagerService.PermissionManagerServiceInternalImpl
                                        .class);
            default:
                Context context2 = this.f$0;
                return new CrossProfileIntentFilterHelper(
                        (Settings)
                                packageManagerServiceInjector.mSettingsProducer.get(
                                        packageManagerServiceInjector.mPackageManager,
                                        packageManagerServiceInjector),
                        packageManagerServiceInjector.getUserManagerService(),
                        packageManagerServiceInjector.mLock,
                        packageManagerServiceInjector.getUserManagerService().mLocalService,
                        context2);
        }
    }

    @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
    public Object produce(Class cls) {
        return this.f$0.getSystemService(cls);
    }
}
