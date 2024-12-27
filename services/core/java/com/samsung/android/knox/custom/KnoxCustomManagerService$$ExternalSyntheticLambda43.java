package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda43
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda43(
            KnoxCustomManagerService knoxCustomManagerService, int i, int i2, int i3, String str) {
        this.f$0 = knoxCustomManagerService;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = i3;
        this.f$1 = str;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda43(
            KnoxCustomManagerService knoxCustomManagerService, String str, int i, int i2, int i3) {
        this.f$0 = knoxCustomManagerService;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = i3;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                int i3 = this.f$4;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$addShortcut$124(str, i, i2, i3);
            default:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i4 = this.f$2;
                int i5 = this.f$3;
                int i6 = this.f$4;
                String str3 = this.f$1;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setStatusBarText$83(i4, i5, i6, str3);
        }
    }
}
