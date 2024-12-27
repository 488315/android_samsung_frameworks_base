package com.android.server.slice;

import android.content.ComponentName;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SliceManagerService$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SliceManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SliceManagerService$$ExternalSyntheticLambda0(
            SliceManagerService sliceManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = sliceManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                SliceManagerService sliceManagerService = this.f$0;
                ComponentName assistComponentForUser =
                        sliceManagerService.mAssistUtils.getAssistComponentForUser(this.f$1);
                if (assistComponentForUser == null) {
                    return null;
                }
                return assistComponentForUser.getPackageName();
            default:
                return this.f$0.getDefaultHome(this.f$1);
        }
    }
}
