package com.android.server.pm;

import android.content.pm.IOnChecksumsReadyListener;
import android.os.Handler;

import java.security.cert.Certificate;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda53
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageManagerService f$0;
    public final /* synthetic */ Handler f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ String f$5;
    public final /* synthetic */ Certificate[] f$6;
    public final /* synthetic */ IOnChecksumsReadyListener f$7;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda53(
            PackageManagerService packageManagerService,
            Handler handler,
            List list,
            int i,
            int i2,
            String str,
            Certificate[] certificateArr,
            IOnChecksumsReadyListener iOnChecksumsReadyListener,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = packageManagerService;
        this.f$1 = handler;
        this.f$2 = list;
        this.f$3 = i;
        this.f$4 = i2;
        this.f$5 = str;
        this.f$6 = certificateArr;
        this.f$7 = iOnChecksumsReadyListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PackageManagerService packageManagerService = this.f$0;
                Handler handler = this.f$1;
                List list = this.f$2;
                int i = this.f$3;
                int i2 = this.f$4;
                String str = this.f$5;
                Certificate[] certificateArr = this.f$6;
                IOnChecksumsReadyListener iOnChecksumsReadyListener = this.f$7;
                packageManagerService.getClass();
                PackageManagerService$$ExternalSyntheticLambda57
                        packageManagerService$$ExternalSyntheticLambda57 =
                                new PackageManagerService$$ExternalSyntheticLambda57(
                                        packageManagerService, 0);
                PackageManagerService$$ExternalSyntheticLambda22
                        packageManagerService$$ExternalSyntheticLambda22 =
                                new PackageManagerService$$ExternalSyntheticLambda22(handler);
                PackageManagerServiceInjector packageManagerServiceInjector =
                        packageManagerService.mInjector;
                Objects.requireNonNull(packageManagerServiceInjector);
                ApkChecksums.getChecksums(
                        list,
                        i,
                        i2,
                        str,
                        certificateArr,
                        iOnChecksumsReadyListener,
                        new ApkChecksums.Injector(
                                packageManagerService$$ExternalSyntheticLambda57,
                                packageManagerService$$ExternalSyntheticLambda22,
                                new PackageManagerService$$ExternalSyntheticLambda44(
                                        packageManagerServiceInjector),
                                new PackageManagerService$$ExternalSyntheticLambda57(
                                        packageManagerService, 1)));
                break;
            default:
                PackageManagerService packageManagerService2 = this.f$0;
                Handler handler2 = this.f$1;
                List list2 = this.f$2;
                int i3 = this.f$3;
                int i4 = this.f$4;
                String str2 = this.f$5;
                Certificate[] certificateArr2 = this.f$6;
                IOnChecksumsReadyListener iOnChecksumsReadyListener2 = this.f$7;
                packageManagerService2.getClass();
                PackageManagerService$$ExternalSyntheticLambda57
                        packageManagerService$$ExternalSyntheticLambda572 =
                                new PackageManagerService$$ExternalSyntheticLambda57(
                                        packageManagerService2, 2);
                PackageManagerService$$ExternalSyntheticLambda22
                        packageManagerService$$ExternalSyntheticLambda222 =
                                new PackageManagerService$$ExternalSyntheticLambda22(handler2);
                PackageManagerServiceInjector packageManagerServiceInjector2 =
                        packageManagerService2.mInjector;
                Objects.requireNonNull(packageManagerServiceInjector2);
                ApkChecksums.getChecksums(
                        list2,
                        i3,
                        i4,
                        str2,
                        certificateArr2,
                        iOnChecksumsReadyListener2,
                        new ApkChecksums.Injector(
                                packageManagerService$$ExternalSyntheticLambda572,
                                packageManagerService$$ExternalSyntheticLambda222,
                                new PackageManagerService$$ExternalSyntheticLambda44(
                                        packageManagerServiceInjector2),
                                new PackageManagerService$$ExternalSyntheticLambda57(
                                        packageManagerService2, 3)));
                break;
        }
    }
}
