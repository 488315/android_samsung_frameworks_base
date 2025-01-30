package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.StartedLazily$command$1", m277f = "SharingStarted.kt", m278l = {155}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class StartedLazily$command$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $subscriptionCount;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StartedLazily$command$1(StateFlow stateFlow, Continuation<? super StartedLazily$command$1> continuation) {
        super(2, continuation);
        this.$subscriptionCount = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StartedLazily$command$1 startedLazily$command$1 = new StartedLazily$command$1(this.$subscriptionCount, continuation);
        startedLazily$command$1.L$0 = obj;
        return startedLazily$command$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StartedLazily$command$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            StateFlow stateFlow = this.$subscriptionCount;
            C48541 c48541 = new C48541(ref$BooleanRef, flowCollector);
            this.label = 1;
            if (stateFlow.collect(c48541, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: kotlinx.coroutines.flow.StartedLazily$command$1$1 */
    public final class C48541 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ Ref$BooleanRef $started;

        public C48541(Ref$BooleanRef ref$BooleanRef, FlowCollector flowCollector) {
            this.$started = ref$BooleanRef;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(int i, Continuation continuation) {
            StartedLazily$command$1$1$emit$1 startedLazily$command$1$1$emit$1;
            int i2;
            if (continuation instanceof StartedLazily$command$1$1$emit$1) {
                startedLazily$command$1$1$emit$1 = (StartedLazily$command$1$1$emit$1) continuation;
                int i3 = startedLazily$command$1$1$emit$1.label;
                if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    startedLazily$command$1$1$emit$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj = startedLazily$command$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i2 = startedLazily$command$1$1$emit$1.label;
                    if (i2 != 0) {
                        ResultKt.throwOnFailure(obj);
                        if (i > 0) {
                            Ref$BooleanRef ref$BooleanRef = this.$started;
                            if (!ref$BooleanRef.element) {
                                ref$BooleanRef.element = true;
                                SharingCommand sharingCommand = SharingCommand.START;
                                startedLazily$command$1$1$emit$1.label = 1;
                                if (this.$$this$flow.emit(sharingCommand, startedLazily$command$1$1$emit$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
            }
            startedLazily$command$1$1$emit$1 = new StartedLazily$command$1$1$emit$1(this, continuation);
            Object obj2 = startedLazily$command$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i2 = startedLazily$command$1$1$emit$1.label;
            if (i2 != 0) {
            }
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Number) obj).intValue(), continuation);
        }
    }
}
