package com.android.server.tracing;

import android.os.IBinder;
import android.os.IMessenger;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TracingServiceProxy$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IMessenger.Stub.asInterface((IBinder) obj);
    }
}
