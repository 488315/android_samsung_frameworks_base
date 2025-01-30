package com.android.systemui.statusbar.events;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.TimeoutCoroutine;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1", m277f = "SystemStatusAnimationSchedulerImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_stopProKioskMode}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1 */
/* loaded from: classes2.dex */
final class C2656xef35f446 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SystemStatusAnimationSchedulerImpl this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1$1", m277f = "SystemStatusAnimationSchedulerImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setAsoc}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SystemStatusAnimationSchedulerImpl this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1$1$1", m277f = "SystemStatusAnimationSchedulerImpl.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C48761 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;

            public C48761(Continuation<? super C48761> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C48761 c48761 = new C48761(continuation);
                c48761.I$0 = ((Number) obj).intValue();
                return c48761;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C48761) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(this.I$0 == 3);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = systemStatusAnimationSchedulerImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl = this.this$0.animationState;
                C48761 c48761 = new C48761(null);
                this.label = 1;
                if (FlowKt.first(stateFlowImpl, c48761, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            StandaloneCoroutine standaloneCoroutine = this.this$0.currentlyRunningAnimationJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            SystemStatusAnimationSchedulerImpl.access$runChipDisappearAnimation(this.this$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2656xef35f446(SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl, Continuation<? super C2656xef35f446> continuation) {
        super(2, continuation);
        this.this$0 = systemStatusAnimationSchedulerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C2656xef35f446(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2656xef35f446) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (TimeoutKt.setupTimeout(new TimeoutCoroutine(500L, this), anonymousClass1) == coroutineSingletons) {
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
