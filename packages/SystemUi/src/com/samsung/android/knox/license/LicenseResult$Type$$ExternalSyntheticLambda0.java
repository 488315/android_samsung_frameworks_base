package com.samsung.android.knox.license;

import com.samsung.android.knox.license.LicenseResult;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class LicenseResult$Type$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LicenseResult$Type$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return LicenseResult.Type.lambda$fromKlmStatus$1(this.f$0, (LicenseResult.Type) obj);
            default:
                return LicenseResult.Type.lambda$fromElmStatus$0(this.f$0, (LicenseResult.Type) obj);
        }
    }
}
