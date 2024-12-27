package com.android.server.pm;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;

import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PersonaServiceHelper$$ExternalSyntheticLambda2
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ PersonaServiceHelper$$ExternalSyntheticLambda2() {
        this.$r8$classId = 1;
        this.f$0 = 0;
    }

    public /* synthetic */ PersonaServiceHelper$$ExternalSyntheticLambda2(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i2,
                        "checkPackageStartable failed to acquire dualDARPolicy for user: ",
                        "PersonaServiceHelper");
                break;
            case 1:
                List list2 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i2,
                        "checkPackageStartable failed to acquire dualDARPolicy for user: ",
                        "PersonaServiceHelper");
                break;
            default:
                List list3 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i2,
                        "checkPackageStartable failed to acquire dualDARPolicy for user: ",
                        "PersonaServiceHelper");
                break;
        }
        return null;
    }
}
