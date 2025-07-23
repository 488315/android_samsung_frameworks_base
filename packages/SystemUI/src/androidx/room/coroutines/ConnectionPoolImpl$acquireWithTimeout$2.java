package androidx.room.coroutines;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConnectionPoolImpl$acquireWithTimeout$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<ConnectionWithLock> $connection;
    final /* synthetic */ Pool $this_acquireWithTimeout;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectionPoolImpl$acquireWithTimeout$2(Ref$ObjectRef<ConnectionWithLock> ref$ObjectRef, Pool pool, Continuation continuation) {
        super(2, continuation);
        this.$connection = ref$ObjectRef;
        this.$this_acquireWithTimeout = pool;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConnectionPoolImpl$acquireWithTimeout$2(this.$connection, this.$this_acquireWithTimeout, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConnectionPoolImpl$acquireWithTimeout$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object receive;
        T t;
        Ref$ObjectRef<ConnectionWithLock> ref$ObjectRef;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef<ConnectionWithLock> ref$ObjectRef2 = this.$connection;
            Pool pool = this.$this_acquireWithTimeout;
            this.L$0 = ref$ObjectRef2;
            this.label = 1;
            BufferedChannel bufferedChannel = pool.channel;
            Object mo3455tryReceivePtdJZtk = bufferedChannel.mo3455tryReceivePtdJZtk();
            ChannelResult.Companion companion = ChannelResult.Companion;
            boolean z = mo3455tryReceivePtdJZtk instanceof ChannelResult.Failed;
            if (z) {
                pool.tryOpenNewConnection();
                receive = bufferedChannel.receive(this);
            } else {
                if (z) {
                    if (!(mo3455tryReceivePtdJZtk instanceof ChannelResult.Closed)) {
                        throw new IllegalStateException("Trying to call 'getOrThrow' on a failed result of a non-closed channel");
                    }
                    Throwable th = ((ChannelResult.Closed) mo3455tryReceivePtdJZtk).cause;
                    if (th != null) {
                        throw th;
                    }
                    throw new IllegalStateException("Trying to call 'getOrThrow' on a channel closed without a cause");
                }
                receive = (ConnectionWithLock) mo3455tryReceivePtdJZtk;
            }
            if (receive == coroutineSingletons) {
                return coroutineSingletons;
            }
            t = receive;
            ref$ObjectRef = ref$ObjectRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef = (Ref$ObjectRef) this.L$0;
            ResultKt.throwOnFailure(obj);
            t = obj;
        }
        ref$ObjectRef.element = t;
        return Unit.INSTANCE;
    }
}
