package kotlinx.coroutines.flow.internal;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SafeCollector<T> extends ContinuationImpl implements FlowCollector {
    public final CoroutineContext collectContext;
    public final int collectContextSize;
    public final FlowCollector collector;
    private Continuation completion_;
    private CoroutineContext lastEmissionContext;

    public SafeCollector(FlowCollector flowCollector, CoroutineContext coroutineContext) {
        super(NoOpContinuation.INSTANCE, EmptyCoroutineContext.INSTANCE);
        this.collector = flowCollector;
        this.collectContext = coroutineContext;
        this.collectContextSize = ((Number) coroutineContext.fold(0, new SafeCollector$$ExternalSyntheticLambda0())).intValue();
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
        Continuation continuation = this.completion_;
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
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(obj);
        if (m3422exceptionOrNullimpl != null) {
            this.lastEmissionContext = new DownstreamExceptionContext(m3422exceptionOrNullimpl, getContext());
        }
        Continuation continuation = this.completion_;
        if (continuation != null) {
            continuation.resumeWith(obj);
        }
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object emit(Continuation continuation, Object obj) {
        CoroutineContext context = continuation.getContext();
        JobKt.ensureActive(context);
        CoroutineContext coroutineContext = this.lastEmissionContext;
        if (coroutineContext != context) {
            if (!(coroutineContext instanceof DownstreamExceptionContext)) {
                if (((Number) context.fold(0, new Function2() { // from class: kotlinx.coroutines.flow.internal.SafeCollector_commonKt$$ExternalSyntheticLambda0
                    /* JADX WARN: Code restructure failed: missing block: B:26:0x0032, code lost:
                    
                        if (r2 == null) goto L17;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r2 = this;
                            java.lang.Integer r3 = (java.lang.Integer) r3
                            int r3 = r3.intValue()
                            kotlin.coroutines.CoroutineContext$Element r4 = (kotlin.coroutines.CoroutineContext.Element) r4
                            kotlin.coroutines.CoroutineContext$Key r0 = r4.getKey()
                            kotlinx.coroutines.flow.internal.SafeCollector r2 = kotlinx.coroutines.flow.internal.SafeCollector.this
                            kotlin.coroutines.CoroutineContext r2 = r2.collectContext
                            kotlin.coroutines.CoroutineContext$Element r2 = r2.get(r0)
                            kotlinx.coroutines.Job$Key r1 = kotlinx.coroutines.Job.Key
                            if (r0 == r1) goto L20
                            if (r4 == r2) goto L1d
                            r3 = -2147483648(0xffffffff80000000, float:-0.0)
                            goto L34
                        L1d:
                            int r3 = r3 + 1
                            goto L34
                        L20:
                            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2
                            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4
                        L24:
                            r0 = 0
                            if (r4 != 0) goto L29
                            r4 = r0
                            goto L30
                        L29:
                            if (r4 != r2) goto L2c
                            goto L30
                        L2c:
                            boolean r1 = r4 instanceof kotlinx.coroutines.internal.ScopeCoroutine
                            if (r1 != 0) goto L5e
                        L30:
                            if (r4 != r2) goto L39
                            if (r2 != 0) goto L1d
                        L34:
                            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
                            return r2
                        L39:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.StringBuilder r0 = new java.lang.StringBuilder
                            java.lang.String r1 = "Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of "
                            r0.<init>(r1)
                            r0.append(r4)
                            java.lang.String r4 = ", expected child of "
                            r0.append(r4)
                            r0.append(r2)
                            java.lang.String r2 = ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'"
                            r0.append(r2)
                            java.lang.String r2 = r0.toString()
                            java.lang.String r2 = r2.toString()
                            r3.<init>(r2)
                            throw r3
                        L5e:
                            kotlinx.coroutines.internal.ScopeCoroutine r4 = (kotlinx.coroutines.internal.ScopeCoroutine) r4
                            kotlinx.atomicfu.AtomicRef r4 = r4._parentHandle
                            java.lang.Object r4 = r4.value
                            kotlinx.coroutines.ChildHandle r4 = (kotlinx.coroutines.ChildHandle) r4
                            if (r4 == 0) goto L6d
                            kotlinx.coroutines.Job r4 = r4.getParent()
                            goto L24
                        L6d:
                            r4 = r0
                            goto L24
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.SafeCollector_commonKt$$ExternalSyntheticLambda0.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                })).intValue() == this.collectContextSize) {
                    this.lastEmissionContext = context;
                } else {
                    throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + this.collectContext + ",\n\t\tbut emission happened in " + context + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
                }
            } else {
                throw new IllegalStateException(StringsKt__IndentKt.trimIndent("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception " + ((DownstreamExceptionContext) coroutineContext).e + ", but then emission attempt of value '" + obj + "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ").toString());
            }
        }
        this.completion_ = continuation;
        Function3 function3 = SafeCollectorKt.emitFun;
        FlowCollector flowCollector = this.collector;
        ((SafeCollectorKt$emitFun$1) function3).getClass();
        Object emit = flowCollector.emit(obj, this);
        if (!Intrinsics.areEqual(emit, CoroutineSingletons.COROUTINE_SUSPENDED)) {
            this.completion_ = null;
        }
        return emit;
    }
}
