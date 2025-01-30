package kotlinx.coroutines.flow;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2", m277f = "Delay.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class FlowKt__DelayKt$debounceInternal$1$3$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FlowCollector $downstream;
    final /* synthetic */ Ref$ObjectRef<Object> $lastValue;
    /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$debounceInternal$1$3$2(Ref$ObjectRef<Object> ref$ObjectRef, FlowCollector flowCollector, Continuation<? super FlowKt__DelayKt$debounceInternal$1$3$2> continuation) {
        super(2, continuation);
        this.$lastValue = ref$ObjectRef;
        this.$downstream = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt__DelayKt$debounceInternal$1$3$2 flowKt__DelayKt$debounceInternal$1$3$2 = new FlowKt__DelayKt$debounceInternal$1$3$2(this.$lastValue, this.$downstream, continuation);
        flowKt__DelayKt$debounceInternal$1$3$2.L$0 = obj;
        return flowKt__DelayKt$debounceInternal$1$3$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$debounceInternal$1$3$2) create(ChannelResult.m2873boximpl(((ChannelResult) obj).holder), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [T, kotlinx.coroutines.internal.Symbol] */
    /* JADX WARN: Type inference failed for: r8v3, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref$ObjectRef<Object> ref$ObjectRef;
        Ref$ObjectRef<Object> ref$ObjectRef2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ?? r8 = ((ChannelResult) this.L$0).holder;
            ref$ObjectRef = this.$lastValue;
            boolean z = r8 instanceof ChannelResult.Failed;
            if (!z) {
                ref$ObjectRef.element = r8;
            }
            FlowCollector flowCollector = this.$downstream;
            if (z) {
                ChannelResult.Closed closed = r8 instanceof ChannelResult.Closed ? (ChannelResult.Closed) r8 : null;
                Throwable th = closed != null ? closed.cause : null;
                if (th != null) {
                    throw th;
                }
                Object obj2 = ref$ObjectRef.element;
                if (obj2 != null) {
                    Object obj3 = obj2 != NullSurrogateKt.NULL ? obj2 : null;
                    this.L$0 = r8;
                    this.L$1 = ref$ObjectRef;
                    this.label = 1;
                    if (flowCollector.emit(obj3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    ref$ObjectRef2 = ref$ObjectRef;
                }
                ref$ObjectRef.element = NullSurrogateKt.DONE;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ref$ObjectRef2 = (Ref$ObjectRef) this.L$1;
        ResultKt.throwOnFailure(obj);
        ref$ObjectRef = ref$ObjectRef2;
        ref$ObjectRef.element = NullSurrogateKt.DONE;
        return Unit.INSTANCE;
    }
}
