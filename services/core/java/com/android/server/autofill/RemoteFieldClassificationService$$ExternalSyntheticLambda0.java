package com.android.server.autofill;

import android.os.IBinder;
import android.service.assist.classification.IFieldClassificationService;

import java.util.function.Function;

public final /* synthetic */ class RemoteFieldClassificationService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IFieldClassificationService.Stub.asInterface((IBinder) obj);
    }
}
