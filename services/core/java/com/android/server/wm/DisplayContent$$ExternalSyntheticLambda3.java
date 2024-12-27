package com.android.server.wm;

import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda3 implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        Task task = (Task) obj2;
        Task remove = ((TaskDisplayArea) obj).remove();
        return remove != null ? remove : task;
    }
}
