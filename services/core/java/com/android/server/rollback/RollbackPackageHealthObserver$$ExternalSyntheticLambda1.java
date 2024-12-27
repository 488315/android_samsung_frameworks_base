package com.android.server.rollback;

import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.util.Slog;

import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RollbackPackageHealthObserver$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RollbackPackageHealthObserver f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ RollbackPackageHealthObserver$$ExternalSyntheticLambda1(
            RollbackPackageHealthObserver rollbackPackageHealthObserver, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = rollbackPackageHealthObserver;
        this.f$1 = list;
        this.f$2 = 5;
    }

    public /* synthetic */ RollbackPackageHealthObserver$$ExternalSyntheticLambda1(
            RollbackPackageHealthObserver rollbackPackageHealthObserver, List list, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = rollbackPackageHealthObserver;
        this.f$1 = list;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.rollbackAllLowImpact(this.f$2, this.f$1);
                break;
            case 1:
                this.f$0.rollbackAllLowImpact(this.f$2, this.f$1);
                break;
            case 2:
                this.f$0.rollbackAllLowImpact(this.f$2, this.f$1);
                break;
            default:
                RollbackPackageHealthObserver rollbackPackageHealthObserver = this.f$0;
                List list = this.f$1;
                int i = this.f$2;
                rollbackPackageHealthObserver.assertInWorkerThread();
                List list2 =
                        RollbackPackageHealthObserver.getRollbacksAvailableForImpactLevel(1, list)
                                .stream()
                                .sorted(
                                        Comparator.comparing(
                                                new RollbackPackageHealthObserver$$ExternalSyntheticLambda8()))
                                .toList();
                VersionedPackage versionRolledBackFrom =
                        ((PackageRollbackInfo) ((RollbackInfo) list2.get(0)).getPackages().get(0))
                                .getVersionRolledBackFrom();
                Slog.i(
                        "RollbackPackageHealthObserver",
                        "Rolling back high impact rollback for package: "
                                + versionRolledBackFrom.getPackageName());
                rollbackPackageHealthObserver.rollbackPackage(
                        (RollbackInfo) list2.get(0), versionRolledBackFrom, i);
                break;
        }
    }
}
