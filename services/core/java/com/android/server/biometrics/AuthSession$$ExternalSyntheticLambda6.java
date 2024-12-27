package com.android.server.biometrics;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthSession$$ExternalSyntheticLambda6 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthSession f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AuthSession$$ExternalSyntheticLambda6(AuthSession authSession, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = authSession;
        this.f$1 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i;
        switch (this.$r8$classId) {
            case 0:
                AuthSession authSession = this.f$0;
                int i2 = this.f$1;
                BiometricSensor biometricSensor = (BiometricSensor) obj;
                Iterator it = authSession.mPreAuthInfo.eligibleSensors.iterator();
                while (true) {
                    if (it.hasNext()) {
                        BiometricSensor biometricSensor2 = (BiometricSensor) it.next();
                        if (i2 == biometricSensor2.id) {
                            i = biometricSensor2.getCurrentStrength();
                        }
                    } else {
                        NandswapManager$$ExternalSyntheticOutline0.m(i2, "Unknown sensor: ", "BiometricService/AuthSession");
                        i = 4095;
                    }
                }
                return Boolean.valueOf(Utils.isAtLeastStrength(i, biometricSensor.getCurrentStrength()));
            default:
                AuthSession authSession2 = this.f$0;
                int i3 = this.f$1;
                authSession2.getClass();
                int i4 = ((BiometricSensor) obj).id;
                return Boolean.valueOf((i4 == i3 || authSession2.mSfpsSensorIds.contains(Integer.valueOf(i4))) ? false : true);
        }
    }
}
