package com.android.server.audio;

import android.media.IAudioPolicyService;
import android.os.Binder;

import java.util.function.Consumer;

public final /* synthetic */ class DefaultAudioPolicyFacade$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Binder.allowBlocking(((IAudioPolicyService) obj).asBinder());
    }
}
