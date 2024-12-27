package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda17
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda17(
            KnoxCustomManagerService knoxCustomManagerService, int i, int i2, String str) {
        this.f$0 = knoxCustomManagerService;
        this.f$1 = i;
        this.f$3 = i2;
        this.f$2 = str;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda17(
            KnoxCustomManagerService knoxCustomManagerService, int i, String str, int i2) {
        this.f$0 = knoxCustomManagerService;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = i2;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                int i = this.f$1;
                String str = this.f$2;
                int i2 = this.f$3;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setKeyboardMode$70(i, str, i2);
            default:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$3;
                String str3 = this.f$2;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setProKioskString$22(i3, i4, str3);
        }
    }
}
