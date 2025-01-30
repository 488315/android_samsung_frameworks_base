package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SubscribedFlowCollector implements FlowCollector {
    public final Function2 action;
    public final FlowCollector collector;

    public SubscribedFlowCollector(FlowCollector flowCollector, Function2 function2) {
        this.collector = flowCollector;
        this.action = function2;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        return this.collector.emit(obj, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onSubscription(Continuation continuation) {
        SubscribedFlowCollector$onSubscription$1 subscribedFlowCollector$onSubscription$1;
        int i;
        Throwable th;
        SafeCollector safeCollector;
        SubscribedFlowCollector subscribedFlowCollector;
        FlowCollector flowCollector;
        if (continuation instanceof SubscribedFlowCollector$onSubscription$1) {
            subscribedFlowCollector$onSubscription$1 = (SubscribedFlowCollector$onSubscription$1) continuation;
            int i2 = subscribedFlowCollector$onSubscription$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                subscribedFlowCollector$onSubscription$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = subscribedFlowCollector$onSubscription$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = subscribedFlowCollector$onSubscription$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    SafeCollector safeCollector2 = new SafeCollector(this.collector, subscribedFlowCollector$onSubscription$1.getContext());
                    try {
                        Function2 function2 = this.action;
                        subscribedFlowCollector$onSubscription$1.L$0 = this;
                        subscribedFlowCollector$onSubscription$1.L$1 = safeCollector2;
                        subscribedFlowCollector$onSubscription$1.label = 1;
                        if (function2.invoke(safeCollector2, subscribedFlowCollector$onSubscription$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        subscribedFlowCollector = this;
                        safeCollector = safeCollector2;
                    } catch (Throwable th2) {
                        th = th2;
                        safeCollector = safeCollector2;
                        safeCollector.releaseIntercepted();
                        throw th;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    safeCollector = (SafeCollector) subscribedFlowCollector$onSubscription$1.L$1;
                    subscribedFlowCollector = (SubscribedFlowCollector) subscribedFlowCollector$onSubscription$1.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (Throwable th3) {
                        th = th3;
                        safeCollector.releaseIntercepted();
                        throw th;
                    }
                }
                safeCollector.releaseIntercepted();
                flowCollector = subscribedFlowCollector.collector;
                if (flowCollector instanceof SubscribedFlowCollector) {
                    return Unit.INSTANCE;
                }
                subscribedFlowCollector$onSubscription$1.L$0 = null;
                subscribedFlowCollector$onSubscription$1.L$1 = null;
                subscribedFlowCollector$onSubscription$1.label = 2;
                if (((SubscribedFlowCollector) flowCollector).onSubscription(subscribedFlowCollector$onSubscription$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        subscribedFlowCollector$onSubscription$1 = new SubscribedFlowCollector$onSubscription$1(this, continuation);
        Object obj2 = subscribedFlowCollector$onSubscription$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = subscribedFlowCollector$onSubscription$1.label;
        if (i != 0) {
        }
        safeCollector.releaseIntercepted();
        flowCollector = subscribedFlowCollector.collector;
        if (flowCollector instanceof SubscribedFlowCollector) {
        }
    }
}
