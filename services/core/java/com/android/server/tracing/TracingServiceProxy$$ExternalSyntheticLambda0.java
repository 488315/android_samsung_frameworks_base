package com.android.server.tracing;

import android.os.IBinder;
import android.os.IMessenger;

import java.util.function.Function;

public final /* synthetic */ class TracingServiceProxy$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IMessenger.Stub.asInterface((IBinder) obj);
    }
}
