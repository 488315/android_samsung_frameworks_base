package com.samsung.android.knox.custom;

import android.net.EthernetManager;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda22
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda22(
            KnoxCustomManagerService knoxCustomManagerService, int i, boolean z, String str) {
        this.f$0 = knoxCustomManagerService;
        this.f$3 = i;
        this.f$1 = z;
        this.f$2 = str;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda22(
            KnoxCustomManagerService knoxCustomManagerService,
            EthernetManager ethernetManager,
            boolean z,
            int i) {
        this.f$0 = knoxCustomManagerService;
        this.f$2 = ethernetManager;
        this.f$1 = z;
        this.f$3 = i;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda22(
            KnoxCustomManagerService knoxCustomManagerService, boolean z, String str, int i) {
        this.f$0 = knoxCustomManagerService;
        this.f$1 = z;
        this.f$2 = str;
        this.f$3 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                boolean z = this.f$1;
                String str = (String) this.f$2;
                int i = this.f$3;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setAutoRotationState$58(z, str, i);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                int i2 = this.f$3;
                boolean z2 = this.f$1;
                String str3 = (String) this.f$2;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setProKioskState$21(i2, z2, str3);
            default:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                EthernetManager ethernetManager = (EthernetManager) this.f$2;
                boolean z3 = this.f$1;
                int i3 = this.f$3;
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setEthernetState$63(
                        ethernetManager, z3, i3);
        }
    }
}
