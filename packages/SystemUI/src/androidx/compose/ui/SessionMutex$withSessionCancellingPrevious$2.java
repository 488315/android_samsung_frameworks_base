package androidx.compose.ui;

import androidx.compose.ui.SessionMutex;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* loaded from: classes.dex */
final class SessionMutex$withSessionCancellingPrevious$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ AtomicReference<SessionMutex.Session<Object>> $arg0;
    final /* synthetic */ Function2 $session;
    final /* synthetic */ Function1 $sessionInitializer;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionMutex$withSessionCancellingPrevious$2(Function1 function1, AtomicReference<SessionMutex.Session<Object>> atomicReference, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$sessionInitializer = function1;
        this.$arg0 = atomicReference;
        this.$session = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SessionMutex$withSessionCancellingPrevious$2 sessionMutex$withSessionCancellingPrevious$2 = new SessionMutex$withSessionCancellingPrevious$2(this.$sessionInitializer, this.$arg0, this.$session, continuation);
        sessionMutex$withSessionCancellingPrevious$2.L$0 = obj;
        return sessionMutex$withSessionCancellingPrevious$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SessionMutex$withSessionCancellingPrevious$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        SessionMutex.Session<Object> session;
        Job job;
        SessionMutex.Session<Object> session2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                session = new SessionMutex.Session<>(JobKt.getJob(coroutineScope.getCoroutineContext()), this.$sessionInitializer.mo781invoke(coroutineScope));
                SessionMutex.Session<Object> andSet = this.$arg0.getAndSet(session);
                if (andSet != null && (job = andSet.job) != null) {
                    this.L$0 = session;
                    this.label = 1;
                    if (JobKt.cancelAndJoin(job, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    session2 = (SessionMutex.Session) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        this.$arg0.compareAndSet(session2, null);
                        return obj;
                    } catch (Throwable th) {
                        th = th;
                        this.$arg0.compareAndSet(session2, null);
                        throw th;
                    }
                }
                session = (SessionMutex.Session) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            Function2 function2 = this.$session;
            Object obj2 = session.value;
            this.L$0 = session;
            this.label = 2;
            obj = function2.invoke(obj2, this);
            if (obj != coroutineSingletons) {
                session2 = session;
                this.$arg0.compareAndSet(session2, null);
                return obj;
            }
            return coroutineSingletons;
        } catch (Throwable th2) {
            th = th2;
            session2 = session;
            this.$arg0.compareAndSet(session2, null);
            throw th;
        }
    }
}
