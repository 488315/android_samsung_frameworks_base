package com.android.systemui.media.mediaoutput.controller.media;

import android.media.session.PlaybackState;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaSessionController$update$5 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionController$update$5(MediaSessionController mediaSessionController, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaSessionController.access$update(this.this$0, (PlaybackState) null, this);
    }
}
