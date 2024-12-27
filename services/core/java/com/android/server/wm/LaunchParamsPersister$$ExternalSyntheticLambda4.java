package com.android.server.wm;

import android.os.Environment;

import java.util.function.IntFunction;

public final /* synthetic */ class LaunchParamsPersister$$ExternalSyntheticLambda4
        implements IntFunction {
    @Override // java.util.function.IntFunction
    public final Object apply(int i) {
        return Environment.getDataSystemCeDirectory(i);
    }
}
