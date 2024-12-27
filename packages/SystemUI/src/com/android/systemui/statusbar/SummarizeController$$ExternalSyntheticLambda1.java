package com.android.systemui.statusbar;

import android.os.Bundle;
import java.util.function.ToLongFunction;

public final /* synthetic */ class SummarizeController$$ExternalSyntheticLambda1 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((Bundle) obj).getLong("postedTime");
    }
}
