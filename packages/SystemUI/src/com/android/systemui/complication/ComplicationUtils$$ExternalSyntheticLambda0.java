package com.android.systemui.complication;

import java.util.function.ToIntFunction;

/* loaded from: classes2.dex */
public final /* synthetic */ class ComplicationUtils$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        switch (((Integer) obj).intValue()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 8;
            case 5:
                return 16;
            case 6:
                return 32;
            case 7:
                return 64;
            case 8:
                return 128;
            default:
                return 0;
        }
    }
}
