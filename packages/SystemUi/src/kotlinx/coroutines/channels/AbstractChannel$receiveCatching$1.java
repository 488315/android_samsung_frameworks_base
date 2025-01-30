package kotlinx.coroutines.channels;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.channels.AbstractChannel", m277f = "AbstractChannel.kt", m278l = {633}, m279m = "receiveCatching-JP2dKIU")
/* loaded from: classes3.dex */
final class AbstractChannel$receiveCatching$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AbstractChannel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractChannel$receiveCatching$1(AbstractChannel abstractChannel, Continuation<? super AbstractChannel$receiveCatching$1> continuation) {
        super(continuation);
        this.this$0 = abstractChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        Object mo2870receiveCatchingJP2dKIU = this.this$0.mo2870receiveCatchingJP2dKIU(this);
        return mo2870receiveCatchingJP2dKIU == CoroutineSingletons.COROUTINE_SUSPENDED ? mo2870receiveCatchingJP2dKIU : ChannelResult.m2873boximpl(mo2870receiveCatchingJP2dKIU);
    }
}
