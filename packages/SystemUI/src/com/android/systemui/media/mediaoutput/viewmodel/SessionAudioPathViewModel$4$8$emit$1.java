package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$4$8$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SessionAudioPathViewModel.AnonymousClass4.AnonymousClass8 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$4$8$emit$1(SessionAudioPathViewModel.AnonymousClass4.AnonymousClass8 anonymousClass8, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((List) null, (Continuation) this);
    }
}
