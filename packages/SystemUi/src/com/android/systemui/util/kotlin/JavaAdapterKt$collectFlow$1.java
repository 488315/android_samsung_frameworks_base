package com.android.systemui.util.kotlin;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$1", m277f = "JavaAdapter.kt", m278l = {43}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class JavaAdapterKt$collectFlow$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Consumer<Object> $consumer;
    final /* synthetic */ Flow $flow;
    final /* synthetic */ Lifecycle.State $state;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$1$1", m277f = "JavaAdapter.kt", m278l = {43}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.util.kotlin.JavaAdapterKt$collectFlow$1$1 */
    final class C35881 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Object> $consumer;
        final /* synthetic */ Flow $flow;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C35881(Flow flow, Consumer<Object> consumer, Continuation<? super C35881> continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.$consumer = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C35881(this.$flow, this.$consumer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35881) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = this.$flow;
                final Consumer<Object> consumer = this.$consumer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.util.kotlin.JavaAdapterKt.collectFlow.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        consumer.accept(obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaAdapterKt$collectFlow$1(Lifecycle.State state, Flow flow, Consumer<Object> consumer, Continuation<? super JavaAdapterKt$collectFlow$1> continuation) {
        super(3, continuation);
        this.$state = state;
        this.$flow = flow;
        this.$consumer = consumer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        JavaAdapterKt$collectFlow$1 javaAdapterKt$collectFlow$1 = new JavaAdapterKt$collectFlow$1(this.$state, this.$flow, this.$consumer, (Continuation) obj3);
        javaAdapterKt$collectFlow$1.L$0 = (LifecycleOwner) obj;
        return javaAdapterKt$collectFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = this.$state;
            C35881 c35881 = new C35881(this.$flow, this.$consumer, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c35881, this) == coroutineSingletons) {
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
