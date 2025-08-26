package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.channels.BufferedChannel;

/* loaded from: classes.dex */
final class SimpleActor$offer$2 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ SimpleActor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleActor$offer$2(SimpleActor simpleActor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simpleActor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimpleActor$offer$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimpleActor$offer$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0054, code lost:
    
        if (r1.invoke(r6, r5) != r0) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004b A[PHI: r1 r6
      0x004b: PHI (r1v1 kotlin.jvm.functions.Function2) = (r1v2 kotlin.jvm.functions.Function2), (r1v4 kotlin.jvm.functions.Function2) binds: [B:13:0x0048, B:9:0x0018] A[DONT_GENERATE, DONT_INLINE]
      0x004b: PHI (r6v6 java.lang.Object) = (r6v13 java.lang.Object), (r6v0 java.lang.Object) binds: [B:13:0x0048, B:9:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0054 -> B:18:0x0057). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Function2 function2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.this$0.remainingMessages.delegate.get() <= 0) {
                throw new IllegalStateException("Check failed.");
            }
            JobKt.ensureActive(this.this$0.scope.getCoroutineContext());
            SimpleActor simpleActor = this.this$0;
            function2 = simpleActor.consumeMessage;
            BufferedChannel bufferedChannel = simpleActor.messageQueue;
            this.L$0 = function2;
            this.label = 1;
            obj = bufferedChannel.receive(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.this$0.remainingMessages.delegate.decrementAndGet() == 0) {
                return Unit.INSTANCE;
            }
            JobKt.ensureActive(this.this$0.scope.getCoroutineContext());
            SimpleActor simpleActor2 = this.this$0;
            function2 = simpleActor2.consumeMessage;
            BufferedChannel bufferedChannel2 = simpleActor2.messageQueue;
            this.L$0 = function2;
            this.label = 1;
            obj = bufferedChannel2.receive(this);
            if (obj != coroutineSingletons) {
                this.L$0 = null;
                this.label = 2;
            }
            return coroutineSingletons;
        }
        function2 = (Function2) this.L$0;
        ResultKt.throwOnFailure(obj);
        this.L$0 = null;
        this.label = 2;
    }
}
