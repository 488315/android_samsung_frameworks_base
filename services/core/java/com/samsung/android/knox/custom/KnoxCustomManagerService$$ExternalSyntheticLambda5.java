package com.samsung.android.knox.custom;

import android.content.ComponentName;

import com.android.internal.util.FunctionalUtils;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda5
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda5(
            KnoxCustomManagerService knoxCustomManagerService, int i, List list, int i2) {
        this.f$0 = knoxCustomManagerService;
        this.f$2 = i;
        this.f$1 = list;
        this.f$3 = i2;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda5(
            KnoxCustomManagerService knoxCustomManagerService,
            ComponentName componentName,
            int i,
            int i2) {
        this.f$0 = knoxCustomManagerService;
        this.f$1 = componentName;
        this.f$2 = i;
        this.f$3 = i2;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$addDexShortcut$3(componentName, i, i2);
            default:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i3 = this.f$2;
                List list = (List) this.f$1;
                int i4 = this.f$3;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setDexForegroundModePackageList$9(
                        i3, list, i4);
        }
    }
}
