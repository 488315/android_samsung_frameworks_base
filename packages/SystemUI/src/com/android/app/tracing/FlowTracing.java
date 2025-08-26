package com.android.app.tracing;

import android.os.Trace;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
public final class FlowTracing {
    public static final FlowTracing INSTANCE = new FlowTracing();
    public static final AtomicInteger counter = new AtomicInteger(0);

    /* renamed from: com.android.app.tracing.FlowTracing$tracedConflatedCallbackFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ String $name;
        int I$0;
        long J$0;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$name, this.$block, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:
        
            if (r4.invoke(r9, r8) == r0) goto L29;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            int i;
            long j;
            String str;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ProducerScope producerScope = (ProducerScope) this.L$0;
                String str2 = this.$name;
                Function2 function2 = this.$block;
                if (Trace.isEnabled()) {
                    int i3 = TraceUtils.$r8$clinit;
                    String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "#CallbackFlowBlock");
                    int iNextInt = ThreadLocalRandom.current().nextInt();
                    Trace.asyncTraceForTrackBegin(4096L, "FlowTracing", strM, iNextInt);
                    try {
                        this.L$0 = "FlowTracing";
                        this.J$0 = 4096L;
                        this.I$0 = iNextInt;
                        this.label = 1;
                        if (function2.invoke(producerScope, this) != coroutineSingletons) {
                            i = iNextInt;
                            j = 4096;
                            str = "FlowTracing";
                            Unit unit = Unit.INSTANCE;
                            Trace.asyncTraceForTrackEnd(j, str, i);
                        }
                    } catch (Throwable th) {
                        th = th;
                        i = iNextInt;
                        j = 4096;
                        str = "FlowTracing";
                        Trace.asyncTraceForTrackEnd(j, str, i);
                        throw th;
                    }
                } else {
                    this.label = 2;
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                i = this.I$0;
                j = this.J$0;
                str = (String) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    Unit unit2 = Unit.INSTANCE;
                    Trace.asyncTraceForTrackEnd(j, str, i);
                } catch (Throwable th2) {
                    th = th2;
                    Trace.asyncTraceForTrackEnd(j, str, i);
                    throw th;
                }
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private FlowTracing() {
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceAsCounter$default(FlowTracing flowTracing, Flow flow, String str, Function1 function1) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new FlowTracing$traceAsCounter$2(str, function1, null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceEmissionCount$default(FlowTracing flowTracing, Flow flow, String str) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new FlowTracing$traceEmissionCount$1(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new FlowTracing$$ExternalSyntheticLambda0(str, false)), null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 tracedConflatedCallbackFlow(String str, Function2 function2) {
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.buffer$default(FlowKt.callbackFlow(new AnonymousClass1(str, function2, null)), -1, 2), new FlowTracing$traceEmissionCount$1(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new FlowTracing$$ExternalSyntheticLambda0(str, true)), null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceEmissionCount$default(FlowTracing flowTracing, ChannelFlowTransformLatest channelFlowTransformLatest, final KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0 keyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(channelFlowTransformLatest, new FlowTracing$traceEmissionCount$2(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.app.tracing.FlowTracing$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FlowTracing flowTracing2 = FlowTracing.INSTANCE;
                return keyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0.invoke() + "#emissionCount";
            }
        }), null));
    }
}
