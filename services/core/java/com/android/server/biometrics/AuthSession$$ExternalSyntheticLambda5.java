package com.android.server.biometrics;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthSession$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ AuthSession$$ExternalSyntheticLambda5(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        BiometricSensor biometricSensor = (BiometricSensor) obj;
        switch (i) {
            case 0:
                return Boolean.valueOf(biometricSensor.id == i2);
            default:
                return Boolean.valueOf(biometricSensor.id != i2);
        }
    }
}
