package com.android.server.devicepolicy;

import android.content.ComponentName;

import java.util.function.Function;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda90
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda90(
            int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((DevicePolicyManagerService) this.f$0)
                        .findAdmin(this.f$1, (ComponentName) obj, false);
            default:
                return ((DevicePolicyManagerService.DpmsUpgradeDataProvider) this.f$0)
                        .this$0.findAdmin(this.f$1, (ComponentName) obj, false);
        }
    }
}
