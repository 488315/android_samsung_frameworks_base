package com.android.server.audio;

import android.media.IAudioPolicyService;
import android.os.Binder;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DefaultAudioPolicyFacade$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Binder.allowBlocking(((IAudioPolicyService) obj).asBinder());
    }
}
