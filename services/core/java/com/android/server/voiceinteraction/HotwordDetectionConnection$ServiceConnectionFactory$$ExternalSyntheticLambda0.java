package com.android.server.voiceinteraction;

import android.os.IBinder;
import android.service.voice.ISandboxedDetectionService;

import java.util.function.Function;

public final /* synthetic */
class HotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ISandboxedDetectionService.Stub.asInterface((IBinder) obj);
    }
}
