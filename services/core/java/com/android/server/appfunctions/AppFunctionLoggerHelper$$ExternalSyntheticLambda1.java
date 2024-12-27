package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionExecutionRecord;

import java.util.function.Function;

public final /* synthetic */ class AppFunctionLoggerHelper$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((AppFunctionExecutionRecord) obj).getInvocationTime();
    }
}
