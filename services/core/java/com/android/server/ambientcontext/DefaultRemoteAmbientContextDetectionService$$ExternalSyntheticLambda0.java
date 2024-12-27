package com.android.server.ambientcontext;

import android.os.IBinder;
import android.service.ambientcontext.IAmbientContextDetectionService;

import java.util.function.Function;

public final /* synthetic */
class DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAmbientContextDetectionService.Stub.asInterface((IBinder) obj);
    }
}
