package com.samsung.android.knox.license;

import com.samsung.android.knox.license.LicenseResult;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final /* synthetic */ class LicenseResult$Type$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LicenseResult$Type$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        LicenseResult.Type type = (LicenseResult.Type) obj;
        switch (i) {
            case 0:
                return LicenseResult.Type.lambda$fromKlmStatus$1(i2, type);
            default:
                return LicenseResult.Type.lambda$fromElmStatus$0(i2, type);
        }
    }
}
