package kotlinx.coroutines.flow.internal;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SafeCollector<T> extends ContinuationImpl implements FlowCollector {
    public final CoroutineContext collectContext;
    public final int collectContextSize;
    public final FlowCollector collector;
    private Continuation<? super Unit> completion;
    private CoroutineContext lastEmissionContext;

    public SafeCollector(FlowCollector flowCollector, CoroutineContext coroutineContext) {
        super(NoOpContinuation.INSTANCE, EmptyCoroutineContext.INSTANCE);
        this.collector = flowCollector;
        this.collectContext = coroutineContext;
        this.collectContextSize = ((Number) coroutineContext.fold(0, new Function2() { // from class: kotlinx.coroutines.flow.internal.SafeCollector$collectContextSize$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((Number) obj).intValue() + 1);
            }
        })).intValue();
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        try {
            Object emit = emit(continuation, obj);
            return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
        } catch (Throwable th) {
            this.lastEmissionContext = new DownstreamExceptionContext(th, continuation.getContext());
            throw th;
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl, kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public final CoroutineStackFrame getCallerFrame() {
        Continuation<? super Unit> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.jvm.internal.ContinuationImpl, kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        CoroutineContext coroutineContext = this.lastEmissionContext;
        return coroutineContext == null ? EmptyCoroutineContext.INSTANCE : coroutineContext;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final StackTraceElement getStackTraceElement() {
        return null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
        if (m2859exceptionOrNullimpl != null) {
            this.lastEmissionContext = new DownstreamExceptionContext(m2859exceptionOrNullimpl, getContext());
        }
        Continuation<? super Unit> continuation = this.completion;
        if (continuation != null) {
            continuation.resumeWith(obj);
        }
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    @Override // kotlin.coroutines.jvm.internal.ContinuationImpl, kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final void releaseIntercepted() {
        super.releaseIntercepted();
    }

    public final Object emit(Continuation continuation, Object obj) {
        CoroutineContext context = continuation.getContext();
        JobKt.ensureActive(context);
        CoroutineContext coroutineContext = this.lastEmissionContext;
        if (coroutineContext != context) {
            if (!(coroutineContext instanceof DownstreamExceptionContext)) {
                if (((Number) context.fold(0, new Function2(this) { // from class: kotlinx.coroutines.flow.internal.SafeCollector_commonKt$checkContext$result$1
                    final /* synthetic */ SafeCollector<?> $this_checkContext;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                        this.$this_checkContext = this;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        int intValue = ((Number) obj2).intValue();
                        CoroutineContext.Element element = (CoroutineContext.Element) obj3;
                        CoroutineContext.Key key = element.getKey();
                        CoroutineContext.Element element2 = this.$this_checkContext.collectContext.get(key);
                        if (key != Job.Key) {
                            return Integer.valueOf(element != element2 ? VideoPlayer.MEDIA_ERROR_SYSTEM : intValue + 1);
                        }
                        Job job = (Job) element2;
                        Job job2 = (Job) element;
                        while (true) {
                            if (job2 != null) {
                                if (job2 == job || !(job2 instanceof ScopeCoroutine)) {
                                    break;
                                }
                                ChildHandle childHandle = (ChildHandle) ((ScopeCoroutine) job2)._parentHandle.value;
                                job2 = childHandle != null ? childHandle.getParent() : null;
                            } else {
                                job2 = null;
                                break;
                            }
                        }
                        if (job2 == job) {
                            if (job != null) {
                                intValue++;
                            }
                            return Integer.valueOf(intValue);
                        }
                        throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + job2 + ", expected child of " + job + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
                    }
                })).intValue() == this.collectContextSize) {
                    this.lastEmissionContext = context;
                } else {
                    throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + this.collectContext + ",\n\t\tbut emission happened in " + context + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
                }
            } else {
                throw new IllegalStateException(StringsKt__IndentKt.trimIndent("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception " + ((DownstreamExceptionContext) coroutineContext).f671e + ", but then emission attempt of value '" + obj + "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ").toString());
            }
        }
        this.completion = continuation;
        Object invoke = SafeCollectorKt.emitFun.invoke(this.collector, obj, this);
        if (!Intrinsics.areEqual(invoke, CoroutineSingletons.COROUTINE_SUSPENDED)) {
            this.completion = null;
        }
        return invoke;
    }
}
