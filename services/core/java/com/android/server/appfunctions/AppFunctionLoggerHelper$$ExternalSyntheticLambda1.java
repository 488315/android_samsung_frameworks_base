package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionExecutionRecord;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppFunctionLoggerHelper$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((AppFunctionExecutionRecord) obj).getInvocationTime();
    }
}
