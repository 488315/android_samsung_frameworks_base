package com.samsung.android.knox.license;

import com.samsung.android.knox.license.LicenseResult;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean lambda$fromKlmStatus$1;
        boolean lambda$fromElmStatus$0;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        LicenseResult.Type type = (LicenseResult.Type) obj;
        switch (i) {
            case 0:
                lambda$fromKlmStatus$1 = LicenseResult.Type.lambda$fromKlmStatus$1(i2, type);
                return lambda$fromKlmStatus$1;
            default:
                lambda$fromElmStatus$0 = LicenseResult.Type.lambda$fromElmStatus$0(i2, type);
                return lambda$fromElmStatus$0;
        }
    }
}
