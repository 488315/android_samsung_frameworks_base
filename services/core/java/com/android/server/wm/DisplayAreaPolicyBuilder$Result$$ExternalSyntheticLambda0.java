package com.android.server.wm;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
