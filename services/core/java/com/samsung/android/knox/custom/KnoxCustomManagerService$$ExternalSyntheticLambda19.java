package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda19
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda19(
            KnoxCustomManagerService knoxCustomManagerService, int i, int i2, boolean z, int i3) {
        this.$r8$classId = i3;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda19(
            KnoxCustomManagerService knoxCustomManagerService, int i, boolean z, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = i;
        this.f$3 = z;
        this.f$2 = i2;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                boolean z = this.f$3;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setStatusBarClockState$111(i, i2, z);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                boolean z2 = this.f$3;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setLockScreenHiddenItems$72(i3, i4, z2);
            case 2:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                int i5 = this.f$1;
                boolean z3 = this.f$3;
                int i6 = this.f$2;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setSettingsHiddenState$43(i5, z3, i6);
            case 3:
                KnoxCustomManagerService knoxCustomManagerService4 = this.f$0;
                int i7 = this.f$1;
                int i8 = this.f$2;
                boolean z4 = this.f$3;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$setAirGestureOptionState$30(i7, i8, z4);
            case 4:
                KnoxCustomManagerService knoxCustomManagerService5 = this.f$0;
                int i9 = this.f$1;
                int i10 = this.f$2;
                boolean z5 = this.f$3;
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService5.lambda$setStatusBarIconsState$112(i9, i10, z5);
            case 5:
                KnoxCustomManagerService knoxCustomManagerService6 = this.f$0;
                int i11 = this.f$1;
                boolean z6 = this.f$3;
                int i12 = this.f$2;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService6.lambda$setSensorDisabled$82(i11, z6, i12);
            case 6:
                KnoxCustomManagerService knoxCustomManagerService7 = this.f$0;
                int i13 = this.f$1;
                boolean z7 = this.f$3;
                int i14 = this.f$2;
                String str7 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService7.lambda$setSettingsEnabledItems$25(i13, z7, i14);
            default:
                KnoxCustomManagerService knoxCustomManagerService8 = this.f$0;
                int i15 = this.f$1;
                boolean z8 = this.f$3;
                int i16 = this.f$2;
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService8.lambda$setCallScreenDisabledItems$61(i15, z8, i16);
        }
    }
}
