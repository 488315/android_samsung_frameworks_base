package com.android.systemui.util.kotlin;

import com.samsung.android.nexus.video.VideoPlayer;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.kotlin.FlowKt$sample$1", m277f = "Flow.kt", m278l = {153}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FlowKt$sample$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $other;
    final /* synthetic */ Flow $this_sample;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.util.kotlin.FlowKt$sample$1$1", m277f = "Flow.kt", m278l = {159}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1 */
    final class C35861 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$flow;
        final /* synthetic */ Flow $other;
        final /* synthetic */ Flow $this_sample;
        final /* synthetic */ Function3 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.util.kotlin.FlowKt$sample$1$1$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ FlowCollector $$this$flow;
            public final /* synthetic */ Object $noVal;
            public final /* synthetic */ AtomicReference $sampledRef;
            public final /* synthetic */ Function3 $transform;

            public AnonymousClass1(AtomicReference<Object> atomicReference, Object obj, FlowCollector flowCollector, Function3 function3) {
                this.$sampledRef = atomicReference;
                this.$noVal = obj;
                this.$$this$flow = flowCollector;
                this.$transform = function3;
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0066 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Object obj, Continuation continuation) {
                FlowKt$sample$1$1$1$emit$1 flowKt$sample$1$1$1$emit$1;
                Object obj2;
                CoroutineSingletons coroutineSingletons;
                int i;
                FlowCollector flowCollector;
                if (continuation instanceof FlowKt$sample$1$1$1$emit$1) {
                    flowKt$sample$1$1$1$emit$1 = (FlowKt$sample$1$1$1$emit$1) continuation;
                    int i2 = flowKt$sample$1$1$1$emit$1.label;
                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                        flowKt$sample$1$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                        obj2 = flowKt$sample$1$1$1$emit$1.result;
                        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = flowKt$sample$1$1$1$emit$1.label;
                        if (i != 0) {
                            ResultKt.throwOnFailure(obj2);
                            Object obj3 = this.$sampledRef.get();
                            if (Intrinsics.areEqual(obj3, this.$noVal)) {
                                return Unit.INSTANCE;
                            }
                            FlowCollector flowCollector2 = this.$$this$flow;
                            flowKt$sample$1$1$1$emit$1.L$0 = flowCollector2;
                            flowKt$sample$1$1$1$emit$1.label = 1;
                            obj2 = this.$transform.invoke(obj, obj3, flowKt$sample$1$1$1$emit$1);
                            if (obj2 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            flowCollector = flowCollector2;
                        } else {
                            if (i != 1) {
                                if (i != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                                return Unit.INSTANCE;
                            }
                            flowCollector = (FlowCollector) flowKt$sample$1$1$1$emit$1.L$0;
                            ResultKt.throwOnFailure(obj2);
                        }
                        flowKt$sample$1$1$1$emit$1.L$0 = null;
                        flowKt$sample$1$1$1$emit$1.label = 2;
                        if (flowCollector.emit(obj2, flowKt$sample$1$1$1$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                }
                flowKt$sample$1$1$1$emit$1 = new FlowKt$sample$1$1$1$emit$1(this, continuation);
                obj2 = flowKt$sample$1$1$1$emit$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt$sample$1$1$1$emit$1.label;
                if (i != 0) {
                }
                flowKt$sample$1$1$1$emit$1.L$0 = null;
                flowKt$sample$1$1$1$emit$1.label = 2;
                if (flowCollector.emit(obj2, flowKt$sample$1$1$1$emit$1) == coroutineSingletons) {
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C35861(Flow flow, Flow flow2, FlowCollector flowCollector, Function3 function3, Continuation<? super C35861> continuation) {
            super(2, continuation);
            this.$this_sample = flow;
            this.$other = flow2;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C35861 c35861 = new C35861(this.$this_sample, this.$other, this.$$this$flow, this.$transform, continuation);
            c35861.L$0 = obj;
            return c35861;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35861) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Job job;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Object obj2 = new Object();
                AtomicReference atomicReference = new AtomicReference(obj2);
                StandaloneCoroutine launch$default = BuildersKt.launch$default(coroutineScope, Dispatchers.Unconfined, null, new FlowKt$sample$1$1$job$1(this.$other, atomicReference, null), 2);
                Flow flow = this.$this_sample;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(atomicReference, obj2, this.$$this$flow, this.$transform);
                this.L$0 = launch$default;
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                job = launch$default;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                job = (Job) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            job.cancel(null);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$sample$1(Flow flow, Flow flow2, Function3 function3, Continuation<? super FlowKt$sample$1> continuation) {
        super(2, continuation);
        this.$this_sample = flow;
        this.$other = flow2;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$sample$1 flowKt$sample$1 = new FlowKt$sample$1(this.$this_sample, this.$other, this.$transform, continuation);
        flowKt$sample$1.L$0 = obj;
        return flowKt$sample$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt$sample$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            C35861 c35861 = new C35861(this.$this_sample, this.$other, (FlowCollector) this.L$0, this.$transform, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(c35861, this) == coroutineSingletons) {
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
}
