package com.android.server.appfunctions;

import android.app.appsearch.GenericDocument;

import java.util.function.Function;

public final /* synthetic */ class CallerValidatorImpl$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(
                !((GenericDocument) obj)
                        .getPropertyBoolean("restrictCallersWithExecuteAppFunctions"));
    }
}
