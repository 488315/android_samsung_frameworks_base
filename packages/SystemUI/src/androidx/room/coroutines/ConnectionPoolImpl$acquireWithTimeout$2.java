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
    public final Object invokeSuspend(Object obj) throws Exception {
        Object objReceive;
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
            Object objMo3475tryReceivePtdJZtk = bufferedChannel.mo3475tryReceivePtdJZtk();
            ChannelResult.Companion companion = ChannelResult.Companion;
            boolean z = objMo3475tryReceivePtdJZtk instanceof ChannelResult.Failed;
            if (z) {
                pool.tryOpenNewConnection();
                objReceive = bufferedChannel.receive(this);
            } else {
                if (z) {
                    if (!(objMo3475tryReceivePtdJZtk instanceof ChannelResult.Closed)) {
                        throw new IllegalStateException("Trying to call 'getOrThrow' on a failed result of a non-closed channel");
                    }
                    Throwable th = ((ChannelResult.Closed) objMo3475tryReceivePtdJZtk).cause;
                    if (th != null) {
                        throw th;
                    }
                    throw new IllegalStateException("Trying to call 'getOrThrow' on a channel closed without a cause");
                }
                objReceive = (ConnectionWithLock) objMo3475tryReceivePtdJZtk;
            }
            if (objReceive == coroutineSingletons) {
                return coroutineSingletons;
            }
            t = objReceive;
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
