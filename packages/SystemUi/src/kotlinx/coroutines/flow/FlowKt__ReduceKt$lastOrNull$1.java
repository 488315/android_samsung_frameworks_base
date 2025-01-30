package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", m277f = "Reduce.kt", m278l = {167}, m279m = "lastOrNull")
/* loaded from: classes3.dex */
final class FlowKt__ReduceKt$lastOrNull$1<T> extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    public FlowKt__ReduceKt$lastOrNull$1(Continuation<? super FlowKt__ReduceKt$lastOrNull$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return FlowKt.lastOrNull(null, this);
    }
}
