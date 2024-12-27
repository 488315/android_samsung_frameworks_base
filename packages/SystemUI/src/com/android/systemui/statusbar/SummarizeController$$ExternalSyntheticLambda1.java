package com.android.systemui.statusbar;

import android.os.Bundle;
import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SummarizeController$$ExternalSyntheticLambda1 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((Bundle) obj).getLong("postedTime");
    }
}
