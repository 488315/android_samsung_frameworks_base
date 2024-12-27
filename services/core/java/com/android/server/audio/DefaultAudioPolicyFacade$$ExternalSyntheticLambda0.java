package com.android.server.audio;

import android.media.IAudioPolicyService;
import android.os.IBinder;

import java.util.function.Function;

public final /* synthetic */ class DefaultAudioPolicyFacade$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAudioPolicyService.Stub.asInterface((IBinder) obj);
    }
}
