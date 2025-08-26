package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* loaded from: classes4.dex */
public final class SubscribedFlowCollector implements FlowCollector {
    public final Function2 action;
    public final FlowCollector collector;

    /* renamed from: kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SubscribedFlowCollector.this.onSubscription(this);
        }
    }

    public SubscribedFlowCollector(FlowCollector flowCollector, Function2 function2) {
        this.collector = flowCollector;
        this.action = function2;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        return this.collector.emit(obj, continuation);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:
    
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r7).onSubscription(r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onSubscription(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Throwable th;
        SafeCollector safeCollector;
        SubscribedFlowCollector subscribedFlowCollector;
        FlowCollector flowCollector;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SafeCollector safeCollector2 = new SafeCollector(this.collector, anonymousClass1.getContext());
            try {
                Function2 function2 = this.action;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = safeCollector2;
                anonymousClass1.label = 1;
                if (function2.invoke(safeCollector2, anonymousClass1) != coroutineSingletons) {
                    subscribedFlowCollector = this;
                    safeCollector = safeCollector2;
                    safeCollector.releaseIntercepted();
                    flowCollector = subscribedFlowCollector.collector;
                    if (flowCollector instanceof SubscribedFlowCollector) {
                    }
                }
                return coroutineSingletons;
            } catch (Throwable th2) {
                th = th2;
                safeCollector = safeCollector2;
                safeCollector.releaseIntercepted();
                throw th;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        safeCollector = (SafeCollector) anonymousClass1.L$1;
        subscribedFlowCollector = (SubscribedFlowCollector) anonymousClass1.L$0;
        try {
            ResultKt.throwOnFailure(obj);
            safeCollector.releaseIntercepted();
            flowCollector = subscribedFlowCollector.collector;
            if (flowCollector instanceof SubscribedFlowCollector) {
                return Unit.INSTANCE;
            }
            anonymousClass1.L$0 = null;
            anonymousClass1.L$1 = null;
            anonymousClass1.label = 2;
        } catch (Throwable th3) {
            th = th3;
            safeCollector.releaseIntercepted();
            throw th;
        }
    }
}
