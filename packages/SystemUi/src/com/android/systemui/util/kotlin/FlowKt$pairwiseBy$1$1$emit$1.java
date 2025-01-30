package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1", m277f = "Flow.kt", m278l = {48, 48}, m279m = "emit")
/* loaded from: classes2.dex */
final class FlowKt$pairwiseBy$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt$pairwiseBy$1.C35851 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$pairwiseBy$1$1$emit$1(FlowKt$pairwiseBy$1.C35851 c35851, Continuation<? super FlowKt$pairwiseBy$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = c35851;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.emit(null, this);
    }
}
