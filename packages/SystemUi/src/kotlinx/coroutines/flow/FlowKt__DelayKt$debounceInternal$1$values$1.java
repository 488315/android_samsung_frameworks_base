package kotlinx.coroutines.flow;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1", m277f = "Delay.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class FlowKt__DelayKt$debounceInternal$1$values$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_debounceInternal;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1$1 */
    public final class C48411 implements FlowCollector {
        public final /* synthetic */ ProducerScope $$this$produce;

        public C48411(ProducerScope producerScope) {
            this.$$this$produce = producerScope;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            FlowKt__DelayKt$debounceInternal$1$values$1$1$emit$1 flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1;
            int i;
            if (continuation instanceof FlowKt__DelayKt$debounceInternal$1$values$1$1$emit$1) {
                flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1 = (FlowKt__DelayKt$debounceInternal$1$values$1$1$emit$1) continuation;
                int i2 = flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (obj == null) {
                            obj = NullSurrogateKt.NULL;
                        }
                        flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.label = 1;
                        if (((ChannelCoroutine) this.$$this$produce).send(obj, flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }
            flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1 = new FlowKt__DelayKt$debounceInternal$1$values$1$1$emit$1(this, continuation);
            Object obj22 = flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = flowKt__DelayKt$debounceInternal$1$values$1$1$emit$1.label;
            if (i != 0) {
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$debounceInternal$1$values$1(Flow flow, Continuation<? super FlowKt__DelayKt$debounceInternal$1$values$1> continuation) {
        super(2, continuation);
        this.$this_debounceInternal = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt__DelayKt$debounceInternal$1$values$1 flowKt__DelayKt$debounceInternal$1$values$1 = new FlowKt__DelayKt$debounceInternal$1$values$1(this.$this_debounceInternal, continuation);
        flowKt__DelayKt$debounceInternal$1$values$1.L$0 = obj;
        return flowKt__DelayKt$debounceInternal$1$values$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$debounceInternal$1$values$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            Flow flow = this.$this_debounceInternal;
            C48411 c48411 = new C48411(producerScope);
            this.label = 1;
            if (flow.collect(c48411, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
