package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.function.ToIntFunction;

public final /* synthetic */ class ExtensionControllerImpl$ExtensionBuilder$$ExternalSyntheticLambda0 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((ExtensionControllerImpl.Item) obj).sortOrder();
    }
}
