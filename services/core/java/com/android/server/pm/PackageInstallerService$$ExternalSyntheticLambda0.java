package com.android.server.pm;

import android.content.pm.PackageInstaller;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageInstallerService$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageInstallerService f$0;
    public final /* synthetic */ Computer f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PackageInstallerService$$ExternalSyntheticLambda0(PackageInstallerService packageInstallerService, Computer computer, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = packageInstallerService;
        this.f$1 = computer;
        this.f$2 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                break;
            case 1:
                this.f$0.getClass();
                break;
            default:
                this.f$0.getClass();
                break;
        }
        return PackageInstallerService.shouldFilterSession(this.f$1, this.f$2, (PackageInstaller.SessionInfo) obj);
    }
}
