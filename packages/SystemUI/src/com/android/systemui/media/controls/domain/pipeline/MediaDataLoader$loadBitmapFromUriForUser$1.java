package com.android.systemui.media.controls.domain.pipeline;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaDataLoader$loadBitmapFromUriForUser$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaDataLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDataLoader$loadBitmapFromUriForUser$1(MediaDataLoader mediaDataLoader, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaDataLoader;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        MediaDataLoader mediaDataLoader = this.this$0;
        String[] strArr = MediaDataLoader.ART_URIS;
        return mediaDataLoader.loadBitmapFromUriForUser(null, 0, 0, null, this);
    }
}
