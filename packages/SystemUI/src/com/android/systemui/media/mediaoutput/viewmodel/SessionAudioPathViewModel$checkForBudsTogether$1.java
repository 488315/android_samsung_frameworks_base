package com.android.systemui.media.mediaoutput.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$checkForBudsTogether$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$checkForBudsTogether$1(SessionAudioPathViewModel sessionAudioPathViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = sessionAudioPathViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return SessionAudioPathViewModel.access$checkForBudsTogether(this.this$0, null, false, this);
    }
}
