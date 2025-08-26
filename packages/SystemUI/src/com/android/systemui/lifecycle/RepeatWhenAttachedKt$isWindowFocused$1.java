package com.android.systemui.lifecycle;

import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class RepeatWhenAttachedKt$isWindowFocused$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $this_isWindowFocused;
    /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$isWindowFocused$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $this_isWindowFocused;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, Continuation continuation) {
            super(2, continuation);
            this.$this_isWindowFocused = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_isWindowFocused, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Boolean boolValueOf = Boolean.valueOf(this.$this_isWindowFocused.hasWindowFocus());
                this.label = 1;
                if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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
    public RepeatWhenAttachedKt$isWindowFocused$1(View view, Continuation continuation) {
        super(2, continuation);
        this.$this_isWindowFocused = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RepeatWhenAttachedKt$isWindowFocused$1 repeatWhenAttachedKt$isWindowFocused$1 = new RepeatWhenAttachedKt$isWindowFocused$1(this.$this_isWindowFocused, continuation);
        repeatWhenAttachedKt$isWindowFocused$1.L$0 = obj;
        return repeatWhenAttachedKt$isWindowFocused$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatWhenAttachedKt$isWindowFocused$1) create((ViewTreeObserver) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.L$0;
        if (viewTreeObserver != null) {
            CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
            Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new RepeatWhenAttachedKt$isWindowFocused$2(viewTreeObserver, null));
            if (flowConflatedCallbackFlow != null) {
                return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(this.$this_isWindowFocused, null), flowConflatedCallbackFlow);
            }
        }
        return EmptyFlow.INSTANCE;
    }
}
