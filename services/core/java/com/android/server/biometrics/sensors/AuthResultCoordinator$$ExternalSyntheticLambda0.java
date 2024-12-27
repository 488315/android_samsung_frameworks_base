package com.android.server.biometrics.sensors;

import java.util.function.IntFunction;

public final /* synthetic */ class AuthResultCoordinator$$ExternalSyntheticLambda0
        implements IntFunction {
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
