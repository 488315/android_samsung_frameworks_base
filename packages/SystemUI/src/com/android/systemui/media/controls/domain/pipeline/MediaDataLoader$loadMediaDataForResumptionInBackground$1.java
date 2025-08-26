package com.android.systemui.media.controls.domain.pipeline;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class MediaDataLoader$loadMediaDataForResumptionInBackground$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaDataLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDataLoader$loadMediaDataForResumptionInBackground$1(MediaDataLoader mediaDataLoader, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaDataLoader;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MediaDataLoader.access$loadMediaDataForResumptionInBackground(this.this$0, 0, null, null, null, null, null, null, null, this);
    }
}
