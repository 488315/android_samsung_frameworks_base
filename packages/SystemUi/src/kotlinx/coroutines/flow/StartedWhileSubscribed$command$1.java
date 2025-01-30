package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", m277f = "SharingStarted.kt", m278l = {178, 180, 182, 183, 185}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class StartedWhileSubscribed$command$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StartedWhileSubscribed this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StartedWhileSubscribed$command$1(StartedWhileSubscribed startedWhileSubscribed, Continuation<? super StartedWhileSubscribed$command$1> continuation) {
        super(3, continuation);
        this.this$0 = startedWhileSubscribed;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj2).intValue();
        StartedWhileSubscribed$command$1 startedWhileSubscribed$command$1 = new StartedWhileSubscribed$command$1(this.this$0, (Continuation) obj3);
        startedWhileSubscribed$command$1.L$0 = (FlowCollector) obj;
        startedWhileSubscribed$command$1.I$0 = intValue;
        return startedWhileSubscribed$command$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        long j;
        SharingCommand sharingCommand;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            if (this.I$0 > 0) {
                SharingCommand sharingCommand2 = SharingCommand.START;
                this.label = 1;
                if (flowCollector.emit(sharingCommand2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            long j2 = this.this$0.stopTimeout;
            this.L$0 = flowCollector;
            this.label = 2;
            if (DelayKt.delay(j2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            if (this.this$0.replayExpiration > 0) {
            }
            sharingCommand = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
            this.L$0 = null;
            this.label = 5;
            if (flowCollector.emit(sharingCommand, this) == coroutineSingletons) {
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i == 2) {
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                if (this.this$0.replayExpiration > 0) {
                    SharingCommand sharingCommand3 = SharingCommand.STOP;
                    this.L$0 = flowCollector;
                    this.label = 3;
                    if (flowCollector.emit(sharingCommand3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    j = this.this$0.replayExpiration;
                    this.L$0 = flowCollector;
                    this.label = 4;
                    if (DelayKt.delay(j, this) == coroutineSingletons) {
                    }
                }
                sharingCommand = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                this.L$0 = null;
                this.label = 5;
                if (flowCollector.emit(sharingCommand, this) == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            }
            if (i == 3) {
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                j = this.this$0.replayExpiration;
                this.L$0 = flowCollector;
                this.label = 4;
                if (DelayKt.delay(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                sharingCommand = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                this.L$0 = null;
                this.label = 5;
                if (flowCollector.emit(sharingCommand, this) == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            }
            if (i == 4) {
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                sharingCommand = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                this.L$0 = null;
                this.label = 5;
                if (flowCollector.emit(sharingCommand, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            if (i != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
        ResultKt.throwOnFailure(obj);
        return Unit.INSTANCE;
    }
}
