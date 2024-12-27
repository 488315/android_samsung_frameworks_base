package com.android.server.wm;

import java.util.function.BiFunction;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda3 implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        Task task = (Task) obj2;
        Task remove = ((TaskDisplayArea) obj).remove();
        return remove != null ? remove : task;
    }
}
