package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class MediaOutputInteractor$getApplicationLabel$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ MediaOutputInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputInteractor$getApplicationLabel$1(MediaOutputInteractor mediaOutputInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = mediaOutputInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        MediaOutputInteractor mediaOutputInteractor = this.this$0;
        int i = MediaOutputInteractor.$r8$clinit;
        return mediaOutputInteractor.getApplicationLabel(null, this);
    }
}
