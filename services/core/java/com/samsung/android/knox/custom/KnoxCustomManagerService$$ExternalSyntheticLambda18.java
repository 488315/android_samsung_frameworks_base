package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda18
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda18(
            KnoxCustomManagerService knoxCustomManagerService, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = str;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                String str = this.f$1;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$removeWidget$127(str);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                String str3 = this.f$1;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$removeShortcut$125(str3);
            case 2:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                String str5 = this.f$1;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setBrowserHomepage$60(str5);
            default:
                KnoxCustomManagerService knoxCustomManagerService4 = this.f$0;
                String str7 = this.f$1;
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$dialEmergencyNumber$53(str7);
        }
    }
}
