package com.android.server.pm;

import android.app.role.RoleManager;
import android.content.Context;

import com.android.internal.pm.parsing.PackageParser2;

import java.util.function.Supplier;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda55
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda55(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PackageManagerServiceInjector packageManagerServiceInjector =
                        (PackageManagerServiceInjector) obj;
                return (PackageParser2)
                        packageManagerServiceInjector.mScanningPackageParserProducer.produce(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
            case 1:
                return ((Settings) obj).mPackages;
            default:
                return (RoleManager) ((Context) obj).getSystemService(RoleManager.class);
        }
    }
}
