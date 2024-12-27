package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda58 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda58(KnoxCustomManagerService knoxCustomManagerService, int i, String str, String str2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = str2;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                int i = this.f$1;
                String str = this.f$2;
                String str2 = this.f$3;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setExitUI$14(i, str, str2);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i2 = this.f$1;
                String str4 = this.f$2;
                String str5 = this.f$3;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setUsbNetAddressesLocal$150(i2, str4, str5);
            default:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                int i3 = this.f$1;
                String str7 = this.f$2;
                String str8 = this.f$3;
                String str9 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setPassCode$18(i3, str7, str8);
        }
    }
}
