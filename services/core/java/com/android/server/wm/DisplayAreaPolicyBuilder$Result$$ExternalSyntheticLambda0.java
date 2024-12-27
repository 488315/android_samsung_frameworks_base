package com.android.server.wm;

import java.util.function.Function;

public final /* synthetic */ class DisplayAreaPolicyBuilder$Result$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        TaskDisplayArea taskDisplayArea = (TaskDisplayArea) obj;
        if (taskDisplayArea.mFeatureId == 1) {
            return taskDisplayArea;
        }
        return null;
    }
}
