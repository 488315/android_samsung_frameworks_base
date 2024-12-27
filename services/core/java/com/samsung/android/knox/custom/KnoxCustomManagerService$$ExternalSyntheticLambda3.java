package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda3(
            KnoxCustomManagerService knoxCustomManagerService, int i, String str, int i2) {
        this.$r8$classId = i2;
        this.f$0 = knoxCustomManagerService;
        this.f$2 = i;
        this.f$1 = str;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda3(
            KnoxCustomManagerService knoxCustomManagerService, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = str;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$startTcpDump$156(str, i);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                String str3 = this.f$1;
                int i2 = this.f$2;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setSystemRingtone$85(str3, i2);
            case 2:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                String str5 = this.f$1;
                int i3 = this.f$2;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setFavoriteApp$131(str5, i3);
            case 3:
                KnoxCustomManagerService knoxCustomManagerService4 = this.f$0;
                int i4 = this.f$2;
                String str7 = this.f$1;
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$setVolumeKeyAppsList$26(i4, str7);
            case 4:
                KnoxCustomManagerService knoxCustomManagerService5 = this.f$0;
                String str9 = this.f$1;
                int i5 = this.f$2;
                String str10 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService5.lambda$setUnlockSimPin$93(str9, i5);
            case 5:
                KnoxCustomManagerService knoxCustomManagerService6 = this.f$0;
                String str11 = this.f$1;
                int i6 = this.f$2;
                String str12 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService6.lambda$setHomeActivity$16(str11, i6);
            default:
                KnoxCustomManagerService knoxCustomManagerService7 = this.f$0;
                int i7 = this.f$2;
                String str13 = this.f$1;
                String str14 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService7.lambda$setAppBlockDownloadNamespaces$55(i7, str13);
        }
    }
}
