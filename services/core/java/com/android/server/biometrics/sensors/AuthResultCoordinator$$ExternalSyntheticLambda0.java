package com.android.server.biometrics.sensors;

import java.util.function.IntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthResultCoordinator$$ExternalSyntheticLambda0 implements IntFunction {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(i | 1);
            case 1:
                return Integer.valueOf(i | 2);
            default:
                return Integer.valueOf(i | 4);
        }
    }
}
