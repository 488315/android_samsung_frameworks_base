package kotlinx.coroutines.flow;

import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StartedWhileSubscribed implements SharingStarted {
    public final long replayExpiration;
    public final long stopTimeout;

    public StartedWhileSubscribed(long j, long j2) {
        this.stopTimeout = j;
        this.replayExpiration = j2;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("stopTimeout(" + j + " ms) cannot be negative").toString());
        }
        if (j2 >= 0) {
            return;
        }
        throw new IllegalArgumentException(("replayExpiration(" + j2 + " ms) cannot be negative").toString());
    }

    @Override // kotlinx.coroutines.flow.SharingStarted
    public final Flow command(SubscriptionCountStateFlow subscriptionCountStateFlow) {
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(subscriptionCountStateFlow, new StartedWhileSubscribed$command$1(this, null));
        final StartedWhileSubscribed$command$2 startedWhileSubscribed$command$2 = new StartedWhileSubscribed$command$2(null);
        return FlowKt.distinctUntilChanged(new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new FlowKt__LimitKt$dropWhile$1$1(new Ref$BooleanRef(), flowCollector, startedWhileSubscribed$command$2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public final boolean equals(Object obj) {
        if (obj instanceof StartedWhileSubscribed) {
            StartedWhileSubscribed startedWhileSubscribed = (StartedWhileSubscribed) obj;
            if (this.stopTimeout == startedWhileSubscribed.stopTimeout && this.replayExpiration == startedWhileSubscribed.replayExpiration) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.replayExpiration) + (Long.hashCode(this.stopTimeout) * 31);
    }

    public final String toString() {
        ListBuilder listBuilder = new ListBuilder(2);
        long j = this.stopTimeout;
        if (j > 0) {
            listBuilder.add("stopTimeout=" + j + "ms");
        }
        long j2 = this.replayExpiration;
        if (j2 < Long.MAX_VALUE) {
            listBuilder.add("replayExpiration=" + j2 + "ms");
        }
        listBuilder.build();
        return PathParser$$ExternalSyntheticOutline0.m29m("SharingStarted.WhileSubscribed(", CollectionsKt___CollectionsKt.joinToString$default(listBuilder, null, null, null, null, 63), ")");
    }
}
