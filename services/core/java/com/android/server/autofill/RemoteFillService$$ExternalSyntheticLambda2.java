package com.android.server.autofill;

import android.os.IBinder;
import android.service.autofill.IAutoFillService;

import java.util.function.Function;

public final /* synthetic */ class RemoteFillService$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAutoFillService.Stub.asInterface((IBinder) obj);
    }
}
