package com.android.systemui.util.kotlin;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.CombineKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class JavaAdapterKt {
    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(view, flow, consumer, null, null, 24, null);
    }

    public static /* synthetic */ DisposableHandle collectFlow$default(View view, Flow flow, Consumer consumer, CoroutineContext coroutineContext, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 16) != 0) {
            state = Lifecycle.State.CREATED;
        }
        return collectFlow(view, flow, consumer, coroutineContext, state);
    }

    public static final <A, B, R> Flow combineFlows(Flow flow, Flow flow2, Function2 function2) {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flow2, new JavaAdapterKt$combineFlows$1(function2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0(Function2 function2, Object obj, Object obj2, Continuation continuation) {
        return function2.invoke(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$0(Function3 function3, Object obj, Object obj2, Object obj3, Continuation continuation) {
        return function3.invoke(obj, obj2, obj3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$1(Function4 function4, Object obj, Object obj2, Object obj3, Object obj4, Continuation continuation) {
        return function4.invoke(obj, obj2, obj3, obj4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object combineFlows$suspendConversion0$2(Function5 function5, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Continuation continuation) {
        return function5.invoke(obj, obj2, obj3, obj4, obj5);
    }

    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext) {
        return collectFlow$default(view, flow, consumer, coroutineContext, null, 16, null);
    }

    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(lifecycle, flow, consumer, (Lifecycle.State) null, 8, (Object) null);
    }

    public static final <A, B, C, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Function3 function3) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, new JavaAdapterKt$combineFlows$2(function3));
    }

    public static final <T> Job collectFlow(CoroutineScope coroutineScope, Flow flow, Consumer<T> consumer) {
        return collectFlow$default(coroutineScope, (CoroutineContext) null, flow, consumer, 2, (Object) null);
    }

    public static /* synthetic */ Job collectFlow$default(Lifecycle lifecycle, Flow flow, Consumer consumer, Lifecycle.State state, int i, Object obj) {
        if ((i & 8) != 0) {
            state = Lifecycle.State.CREATED;
        }
        return collectFlow(lifecycle, flow, consumer, state);
    }

    public static final <T1, T2, T3, T4, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Function4 function4) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, flow4, new JavaAdapterKt$combineFlows$3(function4));
    }

    public static final <T> Job collectFlow(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow flow, Consumer<T> consumer) {
        return CoroutineTracingKt.launchTraced$default(new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext)), null, null, new JavaAdapterKt$collectFlow$3(flow, consumer, null), 7);
    }

    public static final <T1, T2, T3, T4, T5, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Function5 function5) {
        return kotlinx.coroutines.flow.FlowKt.combine(flow, flow2, flow3, flow4, flow5, new JavaAdapterKt$combineFlows$4(function5));
    }

    public static /* synthetic */ Job collectFlow$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, Flow flow, Consumer consumer, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = coroutineScope.getCoroutineContext();
        }
        return collectFlow(coroutineScope, coroutineContext, flow, consumer);
    }

    public static final <T1, T2, T3, T4, T5, T6, R> Flow combineFlows(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, final Function6 function6) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4, flow5, flow6};
        return new Flow() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ Function6 $callee$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, Function6 function6) {
                    super(3, continuation);
                    this.$callee$inlined = function6;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object invoke = this.$callee$inlined.invoke(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
                        this.label = 1;
                        if (flowCollector.emit(invoke, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(FlowCollector flowCollector, Object[] objArr, Continuation continuation) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation, this.$callee$inlined);
                    anonymousClass3.L$0 = flowCollector;
                    anonymousClass3.L$1 = objArr;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt$combineFlows$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object[] invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, function6), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    public static final <T> DisposableHandle collectFlow(View view, Flow flow, Consumer<T> consumer, CoroutineContext coroutineContext, Lifecycle.State state) {
        return RepeatWhenAttachedKt.repeatWhenAttached(view, coroutineContext, new JavaAdapterKt$collectFlow$1(state, flow, consumer, null));
    }

    public static final <T> Job collectFlow(Lifecycle lifecycle, Flow flow, Consumer<T> consumer, Lifecycle.State state) {
        return CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycle), null, null, new JavaAdapterKt$collectFlow$2(lifecycle, state, flow, consumer, null), 7);
    }
}
