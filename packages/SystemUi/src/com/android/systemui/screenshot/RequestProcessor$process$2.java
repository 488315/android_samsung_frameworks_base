package com.android.systemui.screenshot;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.screenshot.RequestProcessor", m277f = "RequestProcessor.kt", m278l = {142, 153, 181}, m279m = "process")
/* loaded from: classes2.dex */
final class RequestProcessor$process$2 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ RequestProcessor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequestProcessor$process$2(RequestProcessor requestProcessor, Continuation<? super RequestProcessor$process$2> continuation) {
        super(continuation);
        this.this$0 = requestProcessor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.process(null, this);
    }
}
